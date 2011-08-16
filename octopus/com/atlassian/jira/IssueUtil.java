package com.atlassian.jira;

import java.util.HashMap;
import java.util.Map;

import com.atlassian.jira.rpc.soap.client.RemoteField;
import com.atlassian.jira.rpc.soap.client.SuperJiraUtil;

public class IssueUtil extends SuperJiraUtil {
    public static RemoteField[] getCustomFilelds() {
        RemoteField[] customFields = null;
        try {
            customFields = j.getCustomFields(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customFields;
    }

    public static Map getCustomMapIdToMap() {
        Map map = new HashMap();
        RemoteField[] customFields = getCustomFilelds();
        for (int i = 0; i < customFields.length; i++) {
            map.put(customFields[i].getName(), customFields[i].getId());
        }
        return map;

    }
}
