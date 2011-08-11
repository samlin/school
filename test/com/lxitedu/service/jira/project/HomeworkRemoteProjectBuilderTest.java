package com.lxitedu.service.jira.project;

import junit.framework.Assert;

import org.junit.Test;

import com.atlassian.jira.rpc.soap.client.RemotePermissionScheme;
import com.atlassian.jira.rpc.soap.client.RemoteProject;

public class HomeworkRemoteProjectBuilderTest {

    //    @Test
    public void testBuildPermissionScheme() {
        HomeworkRemoteProjectBuilder builder = new HomeworkRemoteProjectBuilder();
        builder.setClassName("1001");
        RemotePermissionScheme buildPermissionScheme = builder.buildPermissionScheme();
        Assert.assertNotNull(buildPermissionScheme);
    }

    //    @Test
    public void testDeletePermissionScheme() {
        HomeworkRemoteProjectBuilder builder = new HomeworkRemoteProjectBuilder();
        builder.setClassName("1001");
        builder.deletePermissionSchemeName();
    }

    @Test
    public void testCreateRemoteProject() {
        RemoteProjectBuilder builder = new HomeworkRemoteProjectBuilder();
        builder.setClassName("1001");
        RemoteProject createRemoteProject = builder.createRemoteProject();
        Assert.assertNotNull(createRemoteProject);
    }
}
