package com.atlassian.jira.rpc.soap.client;

import com.atlassian.jira_soapclient.JiraTools;

public class SuperJiraUtil {

    protected static JiraSoapService j = JiraTools.getJiraSoapService();
    protected static String a = JiraTools.getAuth();

    public SuperJiraUtil() {
        super();
    }

}