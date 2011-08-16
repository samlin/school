package com.lxit.util;

import java.util.Map;

import com.atlassian.jira.IssueUtil;
import com.atlassian.jira.rpc.soap.client.RemoteIssue;

public abstract class IssueConvert {
    public IssueConvert() {
        customMap = IssueUtil.getCustomMapIdToMap();
    }

    protected Map customMap;

    public abstract Object convertIssueToBean(RemoteIssue remoteIssue);

}
