package com.lxitedu.jira.http;

import java.io.IOException;

import com.atlassian.connector.eclipse.internal.jira.core.model.IssueType;
import com.atlassian.connector.eclipse.internal.jira.core.model.JiraIssue;
import com.atlassian.connector.eclipse.internal.jira.core.model.Project;
import com.atlassian.jira.rpc.soap.client.LxitJiraManager;
import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.atlassian.jira.rpc.soap.client.RemoteProject;
import com.lxit.entity.Homework;

public class HomewrokJiraService {

    private static final int MAX_TEAM_COUNT_FROM_CLASS = 5;
    private static final String ISSUE_HOMEWORK_TYPE = "6";
    private static final String HORMWORK_PROJECT_PREFIX = "HOMEWORK";
    public static final String SUBTASK_ISSUE_TYPE = "5";

    public RemoteIssue createCommonIssue(RemoteIssue issue) {
        return LxitJiraManager.createIssue(issue);

    }

    public void createSubIssueFromTeacherIssue(RemoteIssue teacherIssue) throws IOException, Exception {

        JiraIssue is = null;
        String projectId = getProjectIdFromKey(teacherIssue.getProject()).getId();
        for (int i = 0; i < MAX_TEAM_COUNT_FROM_CLASS; i++) {
            is = new JiraIssue();
            is.setProject(new Project(projectId));
            is.setType(new IssueType(SUBTASK_ISSUE_TYPE, true));
            is.setDescription(teacherIssue.getDescription());
            //fixme 完成班级名的抽取
            is.setSummary("team" + teacherIssue.getProject() + "0" + i + "---" + teacherIssue.getSummary());
            is.setParentId(teacherIssue.getId());
            is.setAssignee("admin");
            String subIssueKey = JiraIssueHttpService.createSubIssue(is);
            System.out.println("HomewrokJiraService.createSubIssueFromTeacherIssue()" + subIssueKey);

        }

    }

    public RemoteIssue getIssueByKey(String string) {
        return LxitJiraManager.getIssueByKey(string);
    }

    public RemoteProject getProjectIdFromKey(String string) {
        return LxitJiraManager.getProjectByKey(string);
    }

    public void testCreateStudentIssueFromTeamLeaderIssue() throws Exception {

    }

    public RemoteIssue createTeacherIssueByHomework(Homework homework) {
        String projectKey = getProjectKeyFromClassId(homework.getClassId());
        RemoteIssue issue = new RemoteIssue();
        issue.setProject(projectKey);
        issue.setDescription(homework.getDescription());
        issue.setReporter("admin");
        issue.setSummary(homework.getSummary());
        issue.setType(ISSUE_HOMEWORK_TYPE);
        RemoteIssue createCommonIssue = createCommonIssue(issue);
        return createCommonIssue;

    }

    private String getProjectKeyFromClassId(String classId) {
        return HORMWORK_PROJECT_PREFIX + classId;
    }
}
