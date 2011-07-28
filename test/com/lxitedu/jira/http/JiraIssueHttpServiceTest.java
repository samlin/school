package com.lxitedu.jira.http;

import java.io.IOException;

import org.junit.Test;

import com.atlassian.connector.eclipse.internal.jira.core.model.IssueLink;
import com.atlassian.connector.eclipse.internal.jira.core.model.IssueType;
import com.atlassian.connector.eclipse.internal.jira.core.model.JiraIssue;
import com.atlassian.connector.eclipse.internal.jira.core.model.Project;

/**
 * The class <code>JiraIssueServiceTest</code> contains tests for the class
 * {@link <code>JiraIssueService</code>}
 * 
 * @pattern JUnit Test Case
 * 
 * @generatedBy CodePro at 11-6-16 H7:36
 * 
 * @author Administrator
 * 
 * @version $Revision$
 */
public class JiraIssueHttpServiceTest {

	/**
	 * Construct new test instance
	 * 
	 * @param name
	 *            the test name
	 */

	/**
	 * Run the void createIssue(JiraIssue) method test
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	public void TestCreateIssue() throws IOException, Exception {
		JiraIssue issue = new JiraIssue();
		issue.setSummary("this id create by eclipse test");
		issue.setDescription("this is dectiaon by eclipse and hg");
		JiraIssueHttpService.createIssue(issue);
	}

	public void testLinkIssue() throws Exception {
		IssueLink isuseLink = new IssueLink();
		isuseLink.setIssueId("10001");
		isuseLink.setOutwardDescription("teacher");
		isuseLink.setIssueKey("DEMO-1");
		JiraIssueHttpService.createIssueLink(isuseLink);
	}

	@Test
	public void testCreateSubIssue() throws Exception {
		JiraIssue issue = new JiraIssue();
		issue.setSummary("this id create by eclipse test");
		issue.setDescription("this is dectiaon by eclipse and hg");
		issue.setProject(new Project("10000"));
		issue.setType(new IssueType("5", true));
		issue.setParentId("10000");
		JiraIssueHttpService.createSubIssue(issue);
	}
}
