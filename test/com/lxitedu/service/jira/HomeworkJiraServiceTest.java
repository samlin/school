package com.lxitedu.service.jira;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.atlassian.jira.rpc.soap.client.RemoteProject;
import com.atlassian.jira.rpc.soap.client.RemoteUser;
import com.lxit.entity.Homework;
import com.lxitedu.jira.http.HomewrokJiraService;

public class HomeworkJiraServiceTest {

    private final HomewrokJiraService service = new HomewrokJiraService();

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

    //    @Test
    public void testCreateTeacherIssueByHomework() throws IOException, Exception {
        Homework homework = new Homework();
        homework.setClassId("1001");
        homework.setSummary("完成三个模式的结合的源代码");
        homework.setDescription("细节部分要看那个三个模式的面试");
        RemoteIssue createTeacherIssueByHomework = service.createTeacherIssueByHomework(homework);
        service.createSubIssueFromTeacherIssue(createTeacherIssueByHomework, "");
    }

    // @Test
    public void testCreateSubIssueFromTeacherIssue() throws Exception {
        RemoteIssue issue = service.getIssueByKey("HOMEWORK1001-1");
        Assert.assertNotNull(issue);
        System.out.println("HomeworkJiraServiceTest.testCreateSubIssueFromTeacherIssue()" + issue);
        service.createSubIssueFromTeacherIssue(issue, "");
    }

    //    @Test
    public void testGetProjectIdFromKey() throws Exception {
        RemoteProject tt = service.getProjectIdFromKey("HOMEWORK1001");
        Assert.assertEquals("10000", tt.getId());
    }

    //    @Test
    public void testCreateAllIssueByHomework() throws Exception {
        Homework homework = new Homework();
        homework.setClassId("1001");
        homework.setSummary("完成三个模式的结合的源代码");
        homework.setDescription("细节部分要看那个三个模式的面试");
        service.createAllIssueByHomework(homework);
    }

    @Test
    public void testgetUsersFromClassIdAndNo() throws Exception {
        RemoteUser[] list = service.getUsersFromClassIdAndNo("1001", 1);
        Assert.assertEquals(5, list.length);
        System.out.println("HomeworkJiraServiceTest.testgetUsersFromClassIdAndNo()" + list);
    }

    public void testCreateLinkedIssueFromIssueKeyAndUser() throws Exception {
        String subIssueKey = "HOMEWORK1001-5";
        RemoteUser remoteUser = new RemoteUser();
        RemoteIssue teacherIssue = null;
        service.createLinkedIssueFromIssueKeyAndUser(subIssueKey, teacherIssue, remoteUser);
    }
}
