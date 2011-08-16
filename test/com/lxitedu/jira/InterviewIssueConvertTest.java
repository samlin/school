package com.lxitedu.jira;

import junit.framework.Assert;

import org.junit.Test;

import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.lxit.action.admin.InterviewIssueConvert;
import com.lxit.entity.Interview;
import com.lxitedu.service.jira.LxitJiraService;

public class InterviewIssueConvertTest {

    @Test
    public void testConvertIssueToBeanRemoteIssue() {
        InterviewIssueConvert convert = new InterviewIssueConvert();
        LxitJiraService jiraService = new LxitJiraService();
        RemoteIssue issueByKey = jiraService.getIssueByKey("INTERVIEW-27");
        Interview interview = convert.convertIssueToBean(issueByKey);
        Assert.assertEquals("讯程科技", interview.getCompany());

    }

}
