package com.lxit.action.admin;

import com.atlassian.jira.rpc.soap.client.RemoteCustomFieldValue;
import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.lxit.entity.Interview;
import com.lxit.util.IssueConvert;

public class InterviewIssueConvert extends IssueConvert {

    @Override
    public Interview convertIssueToBean(RemoteIssue issue) {
        Interview interview = new Interview();
        interview.setAssignee(issue.getAssignee());
        interview.setIssueKey(issue.getKey());
        interview.setSummary(issue.getSummary());
        interview.setProject(issue.getProject());
        interview.setReporter(issue.getReporter());
        RemoteCustomFieldValue[] customFieldValues = issue.getCustomFieldValues();
        interview.setCompany(getRealValue(customFieldValues, "company"));
        interview.setEpiboly(getRealValue(customFieldValues, "epiboly"));
        interview.setEpibolyCompany(getRealValue(customFieldValues, "epibolyCompany"));
        interview.setSurvey(getRealValue(customFieldValues, "survey"));
        interview.setGood(getRealValue(customFieldValues, "good"));
        interview.setBad(getRealValue(customFieldValues, "bad"));
        interview.setQuestion(getRealValue(customFieldValues, "quersion"));
        interview.setSurvey(getRealValue(customFieldValues, "survey"));

        return interview;
    }

    private String getRealValue(RemoteCustomFieldValue[] customFieldValues, String string) {
        String result = "";
        for (int i = 0; i < customFieldValues.length; i++) {
            if (customFieldValues[i].getCustomfieldId().equals(customMap.get(string))) {
                String[] values = customFieldValues[i].getValues();
                for (int j = 0; j < values.length; j++) {
                    if (values.length == 1) {
                        result = values[j];
                    } else {
                        result = result + "," + values[j];
                    }
                }

                return result;

            }
        }
        return null;
    }
}
