package com.lxitedu.jira.http;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;

public class AbstractJiraIssueService {

	private static final String password = "admin";
	protected static final String userName = "admin";
	private static final boolean TEST_MODE = false;
	private static MultiThreadedHttpConnectionManager connectionManager;
	private static IdleConnectionTimeoutThread idleConnectionTimeoutThread;
	private static final long CONNECTION_TIMEOUT_INTERVAL = 30 * 1000;
	protected static HostConfiguration hostConfiguration;
	private static final int MAX_REDIRECTS = 3;
	// protected static String baseUrl = "http://localhost:2990/jira";
	protected static String baseUrl = "http://192.168.1.107:8080";
	private static final int SOCKET_TIMEOUT = 3 * 60 * 1000;
	private static final Object SESSION_ID_COOKIE = "JSESSIONID";
	protected static HttpClient httpClient;
	protected static final int CONNNECT_TIMEOUT = 60 * 1000;

	protected static HttpClient getHttpClient() {
		HttpConnectionManager connectionManager = getConnectionManager();
		HttpClient httpClient = new HttpClient(connectionManager);

		configureHttpClient(httpClient, "JiraConnector");

		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.getParams().setParameter(HttpMethodParams.SINGLE_COOKIE_HEADER, Boolean.TRUE);
		return httpClient;
	}

	protected static String getContentType() {
		// TODO Auto-generated method stub
		return "application/x-www-form-urlencoded; charset=utf-8";
	}

	protected static void prepareSecurityToken(HttpMethod method) {
		method.setRequestHeader("X-Atlassian-Token", "no-check"); //$NON-NLS-1$//$NON-NLS-2$
	}

	public static synchronized HttpConnectionManager getConnectionManager() {
		if (connectionManager == null) {
			connectionManager = new MultiThreadedHttpConnectionManager();
			addConnectionManager(connectionManager);
		}
		return connectionManager;
	}

	public static synchronized void addConnectionManager(HttpConnectionManager connectionManager) {
		if (idleConnectionTimeoutThread == null) {
			idleConnectionTimeoutThread = new IdleConnectionTimeoutThread();
			idleConnectionTimeoutThread.setTimeoutInterval(CONNECTION_TIMEOUT_INTERVAL);
			idleConnectionTimeoutThread.setConnectionTimeout(CONNNECT_TIMEOUT);
			idleConnectionTimeoutThread.start();
		}
		idleConnectionTimeoutThread.addConnectionManager(connectionManager);
	}

	public static void configureHttpClient(HttpClient client, String userAgent) {
		client.getParams().setBooleanParameter(HttpClientParams.ALLOW_CIRCULAR_REDIRECTS, true);
		client.getParams().setParameter(HttpMethodParams.USER_AGENT, getUserAgent(userAgent));
		client.getParams().setConnectionManagerTimeout(CONNECTION_TIMEOUT_INTERVAL);
		configureHttpClientConnectionManager(client);
	}

	private static Object getUserAgent(String userAgent) {
		return "getSamlinUseragent";
	}

	private static void configureHttpClientConnectionManager(HttpClient client) {
		client.getHttpConnectionManager().getParams().setSoTimeout(SOCKET_TIMEOUT);
		client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNNECT_TIMEOUT);
		if (TEST_MODE) {
			client.getHttpConnectionManager().getParams()
					.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, 2);
		} else {
			client.getHttpConnectionManager().getParams()
					.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, 100);
			client.getHttpConnectionManager().getParams().setMaxTotalConnections(1000);
		}
	}

	protected static HostConfiguration login(HttpClient httpClient) throws Exception {

		final String restLogin = "/rest/gadget/1.0/login"; //$NON-NLS-1$ // JIRA 4.x has additional endpoint for login that tells if CAPTCHA limit was hit
		final String loginAction = "/secure/Dashboard.jspa"; //$NON-NLS-1$;
		boolean jira4x = true;
		// String baseUrl = "http://127.0.0.1:8080";
		for (int i = 0; i <= MAX_REDIRECTS; i++) {

			String url = baseUrl + (jira4x ? restLogin : loginAction);

			PostMethod login = new PostMethod(url);
			login.setFollowRedirects(false);
			login.setRequestHeader("Content-Type", getContentType()); //$NON-NLS-1$
			login.addParameter("os_username", userName); //$NON-NLS-1$
			login.addParameter("os_password", password); //$NON-NLS-1$
			login.addParameter("os_destination", "/success"); //$NON-NLS-1$ //$NON-NLS-2$

			try {
				HostConfiguration hostConfiguration = createHostConfiguration(httpClient);
				int statusCode = execute(httpClient, hostConfiguration, login, null);

				if (statusCode == HttpStatus.SC_NOT_FOUND) {
					jira4x = false;
					continue;
				}

				if (needsReauthentication(httpClient, login)) {
					continue;
				} else if (statusCode != HttpStatus.SC_MOVED_TEMPORARILY
						&& statusCode != HttpStatus.SC_MOVED_PERMANENTLY) {
					throw new Exception("Unexpected status code during login: " + statusCode); //$NON-NLS-1$
				}

				login.getResponseCharSet();

				Header locationHeader = login.getResponseHeader("location"); //$NON-NLS-1$
				if (locationHeader == null) {
					throw new Exception("Invalid redirect, missing location"); //$NON-NLS-1$
				}
				url = locationHeader.getValue();
				if (url.endsWith("/success")) { //$NON-NLS-1$
					String newBaseUrl = url.substring(0, url.lastIndexOf("/success")); //$NON-NLS-1$
					if (baseUrl.equals(newBaseUrl)
					/* || !client.getLocalConfiguration().getFollowRedirects() */) {
						// success
						addAuthenticationCookie(httpClient, login);
						return hostConfiguration;
					} else {
						// need to login to make sure HttpClient picks up the
						// session cookie
						baseUrl = newBaseUrl;
					}
				}
			} catch (IOException e) {
				throw new Exception(e);
			} finally {
				login.releaseConnection();
			}
		}
		return null;
	}

	public static HostConfiguration createHostConfiguration(HttpClient client) {
		configureHttpClientConnectionManager(client);

		HostConfiguration hostConfiguration = new CloneableHostConfiguration();
		return hostConfiguration;
	}

	public static int execute(final HttpClient client, final HostConfiguration hostConfiguration,
			final HttpMethod method, final HttpState state) throws IOException {
		return client.executeMethod(hostConfiguration, method, state);

	}

	private static boolean needsReauthentication(HttpClient httpClient, PostMethod method) throws Exception {
		if (!getContentType().endsWith(method.getResponseCharSet())) {
			method.getResponseCharSet();
			return true;
		}

		if (method.getResponseHeader("Content-Type").getValue().startsWith("application/json")) { //$NON-NLS-1$//$NON-NLS-2$
			// we're talking to JIRA 4.x
			String json = method.getResponseBodyAsString(1024);
			if (json.contains("\"captchaFailure\":true")) { //$NON-NLS-1$
				throw new Exception(":true");
			}
			if (json.contains("\"loginFailedByPermissions\":true")) { //$NON-NLS-1$
				throw new Exception("You don't have permission to login"); //$NON-NLS-1$
			}
		}
		return true;
	}

	private static void addAuthenticationCookie(HttpClient httpClient, PostMethod method) {
		Cookie[] cookies = httpClient.getState().getCookies();
		for (Cookie cookie : cookies) {
			if (SESSION_ID_COOKIE.equals(cookie.getName())) {
				// already have cookie
				return;
			}
		}

		for (Header header : method.getResponseHeaders()) {
			if (header.getName().equalsIgnoreCase("Set-Cookie")) {
				String cookie = header.getValue();
				// chop of path
				int index = cookie.indexOf(';');
				if (index != -1) {
					cookie = cookie.substring(0, index);
				}
				// get session id
				int i = cookie.indexOf("="); //$NON-NLS-1$
				String key = (i != -1) ? cookie.substring(0, i) : cookie;
				cookie = (i != -1) ? cookie.substring(i + 1) : ""; //$NON-NLS-1$
				httpClient.getState().addCookie(
						new Cookie(getHost(baseUrl), key, cookie, getRequestPath(baseUrl), null, isSecure(baseUrl)));
			}
		}
	}

	public static String getHost(String repositoryUrl) {
		String result = repositoryUrl;
		int colonSlashSlash = repositoryUrl.indexOf("://"); //$NON-NLS-1$

		if (colonSlashSlash >= 0) {
			result = repositoryUrl.substring(colonSlashSlash + 3);
		}

		int colonPort = result.indexOf(':');
		int requestPath = result.indexOf('/');

		int substringEnd;

		// minimum positive, or string length
		if (colonPort > 0 && requestPath > 0) {
			substringEnd = Math.min(colonPort, requestPath);
		} else if (colonPort > 0) {
			substringEnd = colonPort;
		} else if (requestPath > 0) {
			substringEnd = requestPath;
		} else {
			substringEnd = result.length();
		}
		return result.substring(0, substringEnd);
	}

	public static String getRequestPath(String repositoryUrl) {
		int colonSlashSlash = repositoryUrl.indexOf("://"); //$NON-NLS-1$
		int requestPath = repositoryUrl.indexOf('/', colonSlashSlash + 3);

		if (requestPath < 0) {
			return ""; //$NON-NLS-1$
		} else {
			return repositoryUrl.substring(requestPath);
		}
	}

	private static boolean isSecure(String repositoryUrl) {
		return repositoryUrl.matches("https.*"); //$NON-NLS-1$
	}

	protected static boolean expectRedirect(HttpMethodBase method, String page, boolean fullMatch) {
		if (method.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY) {
			return false;
		}

		return true;
	}

	public AbstractJiraIssueService() {
		super();
	}

}