package com.lxitedu.jira.http;

import java.io.IOException;

import com.atlassian.connector.eclipse.internal.jira.core.model.IssueType;
import com.atlassian.connector.eclipse.internal.jira.core.model.JiraIssue;
import com.atlassian.connector.eclipse.internal.jira.core.model.Project;
import com.atlassian.jira.rpc.soap.client.LxitJiraManager;
import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.atlassian.jira.rpc.soap.client.RemoteProject;

public class HomewrokJiraService {

	public static final String SUBTASK_ISSUE_TYPE = "5";

	public RemoteIssue createCommonIssue(RemoteIssue issue) {
		return LxitJiraManager.createIssue(issue);

	}

	public void createSubIssueFromTeacherIssue(RemoteIssue teacherIssue) throws IOException, Exception {
		JiraIssue is = new JiraIssue();
		String projectId = getProjectIdFromKey(teacherIssue.getProject()).getId();
		is.setProject(new Project(projectId));
		is.setType(new IssueType(SUBTASK_ISSUE_TYPE, true));
		is.setDescription(teacherIssue.getDescription());
		is.setSummary("this is sub" + teacherIssue.getSummary());
		is.setParentId(teacherIssue.getId());
		is.setAssignee("admin");
		String subIssueKey = JiraIssueHttpService.createSubIssue(is);
		System.out.println("HomewrokJiraService.createSubIssueFromTeacherIssue()" + subIssueKey);

	}

	public RemoteIssue getIssueByKey(String string) {
		return LxitJiraManager.getIssueByKey(string);
	}

	public RemoteProject getProjectIdFromKey(String string) {
		return LxitJiraManager.getProjectByKey(string);
	}

	public void testCreateStudentIssueFromTeamLeaderIssue() throws Exception {

	}
}
