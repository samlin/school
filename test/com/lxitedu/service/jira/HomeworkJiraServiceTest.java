package com.lxitedu.service.jira;

import junit.framework.Assert;

import org.junit.Test;

import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.atlassian.jira.rpc.soap.client.RemoteProject;
import com.lxitedu.jira.http.HomewrokJiraService;

public class HomeworkJiraServiceTest {

	private HomewrokJiraService service = new HomewrokJiraService();

	public void testCreateTeacherIssue() {
		RemoteIssue issue = new RemoteIssue();
		issue.setProject("LXHOMEWORK");
		issue.setDescription("create by eclipse");
		issue.setReporter("admin");
		issue.setSummary("summy create by samlin");
		issue.setType("6");
		RemoteIssue createCommonIssue = service.createCommonIssue(issue);
		System.out.println("HomeworkJiraServiceTest.test():" + createCommonIssue);
		RemoteProject tt;

	}

	@Test
	public void testCreateSubIssueFromTeacherIssue() throws Exception {
		RemoteIssue issue = service.getIssueByKey("HOMEWORK1001-1");
		Assert.assertNotNull(issue);
		System.out.println("HomeworkJiraServiceTest.testCreateSubIssueFromTeacherIssue()" + issue);
		service.createSubIssueFromTeacherIssue(issue);
	}

	@Test
	public void testGetProjectIdFromKey() throws Exception {
		RemoteProject tt = service.getProjectIdFromKey("HOMEWORK1001");
		Assert.assertEquals("10000", tt.getId());
	}
}
