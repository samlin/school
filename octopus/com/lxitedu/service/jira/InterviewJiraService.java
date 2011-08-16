package com.lxitedu.service.jira;

import com.atlassian.jira.rpc.soap.client.LxitJiraManager;
import com.atlassian.jira.rpc.soap.client.RemoteIssue;

public class InterviewJiraService {

    public RemoteIssue[] getInterviewIssues() {
        RemoteIssue[] issuesByJql = null;
        try {
            issuesByJql = LxitJiraManager.getIssuesByJql("project = INTERVIEW");
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return issuesByJql;

    }

}
