package com.lxitedu.service.jira;

import junit.framework.Assert;

import org.junit.Test;

import com.atlassian.jira.rpc.soap.client.RemoteProject;

public class LxitJiraProjectServiceTest {
  @Test
  public void testGetProjectByKey() {
    LxitJiraService jiraService = new LxitJiraService();
    RemoteProject project = jiraService.getProjectByKey("HOMEWORK");
    System.out.println("LxitJiraProjectServiceTest.testGetProjectByKey()" + project);
    Assert.assertNotNull(project);

  }
}
