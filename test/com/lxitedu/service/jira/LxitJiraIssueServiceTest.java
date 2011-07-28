package com.lxitedu.service.jira;

import org.junit.Test;

import com.atlassian.jira.rpc.soap.client.RemoteIssue;

public class LxitJiraIssueServiceTest {
	private LxitJiraService lxitJiraService = new LxitJiraService();

	// @Test
	public void testfixIssue() {
		LxitJiraService.fixIssue("DLTHREE-4130");
	}

	@Test
	// mmm 自动批阅各个班级的日志
	public void testGetIssueByJql() {
		RemoteIssue[] issueByJql = lxitJiraService.getIssueByJql("project = DL1103  AND status = Open");
		for (RemoteIssue remoteIssue : issueByJql) {
			LxitJiraService.fixIssue(remoteIssue.getKey());
			System.out.println("LxitJiraIssueServiceTest.testGetIssueByJql() key:" + remoteIssue.getKey());
		}

	}

	@Test
	public void testFixDayLogBy1003() {
		RemoteIssue[] issueByJql = lxitJiraService.getIssueByJql("project = DL1003  AND status = Open");
		for (RemoteIssue remoteIssue : issueByJql) {
			LxitJiraService.fixIssueBy1003(remoteIssue.getKey());
			System.out.println("LxitJiraIssueServiceTest.testGetIssueByJql() key:" + remoteIssue.getKey());
		}

	}

	// @Test
	public void testGetIssueByKey() throws Exception {
		RemoteIssue remoteIssue = lxitJiraService.getIssueByKey("HOMEWORK-3");
		System.out.println("LxitJiraIssueServiceTest.testGetIssueByKey()" + remoteIssue);
	}

	public static void main(String[] args) {
		LxitJiraService jirairaService = new LxitJiraService();
		RemoteIssue[] issueByJql = jirairaService.getIssueByJql("project = DLSIX  AND status = Open");
		for (RemoteIssue remoteIssue : issueByJql) {
			LxitJiraService.fixIssue(remoteIssue.getKey());
			System.out.println("LxitJiraIssueServiceTest.testGetIssueByJql() key:" + remoteIssue.getKey());
		}
	}
}
