package com.lxitedu.service.jira;

import org.junit.Test;

import com.lxitedu.bean.LxitClass;

//this is samlin add 2010-8-9
public class LxitJiraServiceTest {
	private LxitJiraService lxitJiraService = new LxitJiraService();

	@Test
	public void testCreateDayProblem() {
		// fail("Not yet implemented");
	}

	@Test
	public void testDeleteIssue() {
		// lxitJiraService.deleteIssueFromClassId("1001");
	}

	public void testCreateDayLog() {
		// LxitClass lxitClass = new LxitClass();
		// lxitClass.setId("1002");
		// lxitJiraService.createDayLog(lxitClass);

	}

	@Test
	public void testCreateStudentFromLxitClass() {
		LxitClass lxitClass = new LxitClass();
		lxitClass.setId("1101");
		lxitJiraService.createGroup(lxitClass.getId());
		lxitJiraService.createStudnetAndGroup(lxitClass);

	}

	// @Test
	public void testCreateDayLogPermission() throws Exception {
		LxitClass lxitClass = new LxitClass();
		lxitClass.setId("1002");
		// lxitJiraService.createGroup(lxitClass.getId());
		// lxitJiraService.createDayLogPermissionsAndScheme(lxitClass.getId());
	}

	// @Test
	public void testCreateDayLogProject() {
		LxitClass lxitClass = new LxitClass();
		lxitClass.setId("1102");
//		lxitJiraService.createGroup(lxitClass.getId());
		lxitJiraService.createDayLogPermissionsAndScheme(lxitClass.getId());
		lxitJiraService.createDayLogProject(lxitClass.getId());

	}

	public void testModifyIssue() throws Exception {
		lxitJiraService.fixIssue("DLTHREE-4131");
	}

}
