package com.lxitedu.jira.http;

import java.io.IOException;

import com.atlassian.connector.eclipse.internal.jira.core.model.IssueLink;
import com.atlassian.connector.eclipse.internal.jira.core.model.IssueType;
import com.atlassian.connector.eclipse.internal.jira.core.model.JiraIssue;
import com.atlassian.connector.eclipse.internal.jira.core.model.Project;
import com.atlassian.jira.rpc.soap.client.LxitJiraManager;
import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.atlassian.jira.rpc.soap.client.RemoteProject;
import com.atlassian.jira.rpc.soap.client.RemoteUser;
import com.lxit.entity.Homework;

public class HomeworkJiraService {

    private static final String GROUP_TEAM_PREFIX = "team";
    private static final int MAX_TEAM_COUNT_FROM_CLASS = 5;
    private static final String ISSUE_HOMEWORK_TYPE = "6";
    private static final String HORMWORK_PROJECT_PREFIX = "HOMEWORK";
    public static final String SUBTASK_ISSUE_TYPE = "5";

    public RemoteIssue createCommonIssue(RemoteIssue issue) {
        return LxitJiraManager.createIssue(issue);
    }

    public void createSubIssueFromTeacherIssue(RemoteIssue teacherIssue, String className) throws IOException,
            Exception {
        String projectId = getProjectIdFromKey(teacherIssue.getProject()).getId();
        for (int i = 0; i < MAX_TEAM_COUNT_FROM_CLASS; i++) {
            String subIssueKey = createSubIssue(teacherIssue, projectId, i + 1);
            RemoteUser[] users = getUsersFromClassIdAndNo(className, i + 1);
            System.out.println("create Team Homemork: TeacherKey:" + teacherIssue.getKey() + "subIssueKey:"
                    + subIssueKey);
            for (int j = 0; j < users.length; j++) {
                createLinkedIssueFromIssueKeyAndUser(subIssueKey, teacherIssue, users[i]);
            }
            Thread.sleep(100);
        }

    }

    public void createLinkedIssueFromIssueKeyAndUser(String subIssueKey, RemoteIssue teacherIssue, RemoteUser remoteUser) {
        RemoteIssue issue = new RemoteIssue();
        issue.setProject(teacherIssue.getProject());
        issue.setDescription(teacherIssue.getDescription());
        issue.setReporter("admin");
        issue.setAssignee(remoteUser.getName());
        issue.setSummary(teacherIssue.getSummary());
        issue.setType(ISSUE_HOMEWORK_TYPE);
        RemoteIssue createCommonIssue = createCommonIssue(issue);

        RemoteIssue getIssue = getIssueByKey(subIssueKey);

        IssueLink isuseLink = new IssueLink();
        isuseLink.setIssueId(getIssue.getId());
        isuseLink.setOutwardDescription("homework");
        isuseLink.setIssueKey(createCommonIssue.getKey());
        System.out.println("person key--------------------:" + createCommonIssue.getKey());
        JiraIssueHttpService.createIssueLink(isuseLink);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String createSubIssue(RemoteIssue teacherIssue, String projectId, int i) throws IOException, Exception {
        JiraIssue is;
        is = new JiraIssue();
        is.setProject(new Project(projectId));
        is.setType(new IssueType(SUBTASK_ISSUE_TYPE, true));
        is.setDescription(teacherIssue.getDescription());
        //fixme 完成班级名的抽取
        is.setSummary("team--" + teacherIssue.getProject() + "--0" + i + "---" + teacherIssue.getSummary());
        is.setParentId(teacherIssue.getId());
        is.setAssignee("admin");
        String subIssueKey = JiraIssueHttpService.createSubIssue(is);
        return subIssueKey;
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
        System.out.println("create TeahcerKey=" + createCommonIssue.getKey());
        return createCommonIssue;

    }

    private String getProjectKeyFromClassId(String classId) {
        return HORMWORK_PROJECT_PREFIX + classId;
    }

    public void createAllIssueByHomework(Homework homework) {
        RemoteIssue createTeacherIssueByHomework = createTeacherIssueByHomework(homework);
        try {
            createSubIssueFromTeacherIssue(createTeacherIssueByHomework, homework.getClassId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public RemoteUser[] getUsersFromClassIdAndNo(String className, int i) {
        String groupName = getTeamNameFromClassNameAndNo(className, i);
        return LxitJiraManager.getUsersfromGroupName(groupName);
    }

    private String getTeamNameFromClassNameAndNo(String className, int i) {
        return GROUP_TEAM_PREFIX + "_" + className + "_0" + i;
    }

    public void createTeamsFromClassName(String className) {
        for (int i = 0; i < MAX_TEAM_COUNT_FROM_CLASS; i++) {
            LxitJiraManager.createGroup(getTeamNameFromClassNameAndNo(className, i + 1));
        }
    }
}
