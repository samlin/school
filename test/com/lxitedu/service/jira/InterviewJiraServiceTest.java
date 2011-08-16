package com.lxitedu.service.jira;

import org.junit.Test;

public class InterviewJiraServiceTest {

    @Test
    public void testImport() {
        InterviewJiraService service = new InterviewJiraService();
        service.getInterviewIssues();
    }

}
