package com.lxitedu.jira.http;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpHost;
import org.apache.commons.httpclient.protocol.Protocol;

public class CloneableHostConfiguration extends HostConfiguration {

	public CloneableHostConfiguration() {
	}

	public CloneableHostConfiguration(CloneableHostConfiguration hostConfiguration) {
		super(hostConfiguration);
	}

	@Override
	public Object clone() {
		return new CloneableHostConfiguration(this);
	}

	@Override
	public synchronized void setHost(String host, int port, String scheme) {
		setHost(new HttpHost(host, port, getProtocol(host, port, scheme)));
	}

	/**
	 * Keeps the previous {@link Protocol} if the <code>scheme</code> matches the previous protocol scheme.
	 */
	private Protocol getProtocol(String host, int port, String scheme) {
		final Protocol oldProtocol = getProtocol();
		if (oldProtocol != null) {
			final String oldScheme = oldProtocol.getScheme();
			if (oldScheme == scheme || (oldScheme != null && oldScheme.equalsIgnoreCase(scheme))) {
				return oldProtocol;
			}
		}
		return Protocol.getProtocol(scheme);
	}

}
