package com.lxitedu.jira.http;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.PostMethod;

import com.atlassian.connector.eclipse.internal.jira.core.model.IssueLink;
import com.atlassian.connector.eclipse.internal.jira.core.model.JiraIssue;

public class JiraIssueHttpService extends AbstractJiraIssueService {

    public static String createIssue(JiraIssue issue, String createUrl) throws IOException, Exception {
        if (hostConfiguration == null) {
            httpClient = getHttpClient();
            hostConfiguration = login(httpClient);
        }

        String url = baseUrl + createUrl;

        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type", getContentType()); //$NON-NLS-1$
        prepareSecurityToken(post);
        // TODO �����10000��issue���ڵ�project��id,����Ҫ�������
        post.addParameter("pid", issue.getProject().getId());
        post.addParameter("issuetype", issue.getType().getId()); //$NON-NLS-1$
        post.addParameter("summary", issue.getSummary()); //$NON-NLS-1$
        if (issue.getPriority() != null) {
            post.addParameter("priority", issue.getPriority().getId()); //$NON-NLS-1$
        }
        //		addFields(client, issue, post, new String[] { "duedate" }); //$NON-NLS-1$
        //		post.addParameter("timetracking", Long.toString(issue.getEstimate() / 60) + "m"); //$NON-NLS-1$ //$NON-NLS-2$

        if (issue.getComponents() != null) {
            for (int i = 0; i < issue.getComponents().length; i++) {
                post.addParameter("components", issue.getComponents()[i].getId()); //$NON-NLS-1$
            }
        } else {
            post.addParameter("components", "-1"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (issue.getReportedVersions() != null) {
            for (int i = 0; i < issue.getReportedVersions().length; i++) {
                post.addParameter("versions", issue.getReportedVersions()[i].getId()); //$NON-NLS-1$
            }
        } else {
            post.addParameter("versions", "-1"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (issue.getFixVersions() != null) {
            for (int i = 0; i < issue.getFixVersions().length; i++) {
                post.addParameter("fixVersions", issue.getFixVersions()[i].getId()); //$NON-NLS-1$
            }
        } else {
            post.addParameter("fixVersions", "-1"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (issue.getAssignee() == null) {
            post.addParameter("assignee", "-1"); // Default assignee //$NON-NLS-1$ //$NON-NLS-2$
        } else if (issue.getAssignee().length() == 0) {
            post.addParameter("assignee", ""); // nobody //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            post.addParameter("assignee", issue.getAssignee()); //$NON-NLS-1$
        }

        post.addParameter("reporter", userName); //$NON-NLS-1$

        post.addParameter("environment", issue.getEnvironment() != null ? issue.getEnvironment() : ""); //$NON-NLS-1$ //$NON-NLS-2$
        post.addParameter("description", issue.getDescription() != null ? issue.getDescription() : ""); //$NON-NLS-1$ //$NON-NLS-2$

        if (issue.getParentId() != null) {
            post.addParameter("parentIssueId", issue.getParentId()); //$NON-NLS-1$
        }

        if (issue.getSecurityLevel() != null) {
            post.addParameter("security", issue.getSecurityLevel().getId()); //$NON-NLS-1$
        }

        // addCustomFields(client, issue, post);

        String issueKey = null;
        try {
            execute(httpClient, hostConfiguration, post, null);
            System.out.println("JiraIssueHttpService.createIssue()");
            Header locationHeader = post.getResponseHeader("location"); //$NON-NLS-1$
            if (!expectRedirect(post, "/browse/", false)) { //$NON-NLS-1$
                System.out.println("handleErrorMessage(post) ");
            } else {
                //				final Header locationHeader = post.getResponseHeader("location"); //$NON-NLS-1$
                // parse issue key from issue URL
                String location = locationHeader.getValue();
                int i = location.lastIndexOf("/"); //$NON-NLS-1$
                if (i != -1) {
                    issueKey = location.substring(i + 1);
                } else {
                    throw new Exception("The server redirected to an unexpected location while creating an issue: " //$NON-NLS-1$
                            + location);
                }
            }
        } finally {
            post.releaseConnection();
        }
        return issueKey;
    }

    public static String createIssue(JiraIssue issue) throws IOException, Exception {
        return createIssue(issue, "/secure/CreateIssueDetails.jspa");
    }

    public static void createIssueLink(IssueLink isuseLink) {
        if (hostConfiguration == null) {
            httpClient = getHttpClient();
            try {
                hostConfiguration = login(httpClient);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        String createUrl = "/LinkExistingIssue.jspa";
        String url = baseUrl + createUrl;

        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type", getContentType()); //$NON-NLS-1$
        prepareSecurityToken(post);
        // TODO �����10000��issue���ڵ�project��id,����Ҫ�������
        post.addParameter("id", isuseLink.getIssueId());
        post.addParameter("linkDesc", isuseLink.getOutwardDescription());
        post.addParameter("linkKey", isuseLink.getIssueKey());
        post.addParameter("comment", "this is add by eclipse"); //$NON-NLS-1$

        // addCustomFields(client, issue, post);

        String[] issueKey = null;
        try {
            execute(httpClient, hostConfiguration, post, null);
            if (!expectRedirect(post, "/browse/", false)) { //$NON-NLS-1$
                System.out.println("handleErrorMessage(post) ");
            } else {
                final Header locationHeader = post.getResponseHeader("location"); //$NON-NLS-1$
                // parse issue key from issue URL
                String location = locationHeader.getValue();
                int i = location.lastIndexOf("/"); //$NON-NLS-1$
                if (i != -1) {
                    // issueKey[0] = location.substring(i + 1);
                } else {
                    throw new Exception("The server redirected to an unexpected location while creating an issue: " //$NON-NLS-1$
                            + location);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        assert issueKey[0] != null;

    }

    public static String createSubIssue(JiraIssue issue) throws IOException, Exception {
        return createIssue(issue, "/secure/CreateSubTaskIssueDetails.jspa");
    }
}
