package com.lxitedu.service.jira.project;

import com.atlassian.jira.rpc.soap.client.JiraSoapService;
import com.atlassian.jira.rpc.soap.client.RemotePermissionScheme;
import com.atlassian.jira.rpc.soap.client.RemoteProject;
import com.atlassian.jira_soapclient.JiraTools;

public abstract class RemoteProjectBuilder {
    protected static final String TEACHER_GROUP_NAME = "lxit";
    protected RemoteProject project;
    private String className;
    protected static JiraSoapService j = JiraTools.getJiraSoapService();
    protected static String a = JiraTools.getAuth();;

    public RemoteProject getPizza() {
        return new RemoteProject();
    }

    public void createNewPizzaProduct() {
        project = new RemoteProject();
    }

    public abstract RemotePermissionScheme buildPermissionScheme();

    public abstract String getPermissionSchemeName();

    public abstract void deletePermissionSchemeName();

    public abstract String getProjectKeyFromClassName();

    public abstract void createDetailPermission(RemotePermissionScheme remotePermissionScheme) throws Exception;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public abstract RemoteProject createRemoteProject();

}