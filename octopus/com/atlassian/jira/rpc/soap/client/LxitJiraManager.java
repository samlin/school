package com.atlassian.jira.rpc.soap.client;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.atlassian.jira_soapclient.JiraTools;
import com.lxitedu.bean.LxitClass;
import com.lxitedu.bean.Student;
import com.lxitedu.dao.DBManager;
import com.lxitedu.framework.tools.ExtendUserTool;
import com.lxitedu.framework.tools.PinyinTools;

public class LxitJiraManager {
	private static final long PERMISSION_ASSIGNABLE = 17L;
	private static final long PERMISSION_BROWSE = 10L;
	private static final long PERMISSION_CREATE_ISSUE = 11L;
	private static final long PERMISSION_EDIT_ISSUE = 12L;
	private static final long PERMISSION_CLOSE_ISSUE = 18L;
	private static final Long PERMISSION_ADD_COMMENT = 15L;
	private static final Long PERMISSION_EDIT_OWN_COMMENT = 35L;
	private static final long PERMISSION_RESOLVE_ISSUE = 14L;

	private static final String TEACHER_GROUP_NAME = "lxit";
	private static JiraSoapService j = JiraTools.getJiraSoapService();
	private static String a = JiraTools.getAuth();;
	private static List<String> classList;

	public static void main(String[] args) {
		init();

		createAll();
	}

	private static void init() {
		// j = JiraTools.getJiraSoapService();
		// a = JiraTools.getAuth();

		// classList = DBManager.getAllClassName();
		updateTeacherDayProblemPermissionFromClassId("0902");

	}

	public static void createAll() {
		// createGroups();
		// createStudents();
		// createDayLogProjects();
		// createSinglePermissionScheme();
		// updateStudentDayLogPermissionFromClassId();
		// updateProjectPermissionScheme();

		// updateTeahcerDayLogPermissionFromClassId();
		// updateAdminDayLogPermissionFromClassId();
		// deleteIssue();
		createDayProblemProjects();
	}

	private static void createDayLogProjects() {
		for (Iterator<String> iterator = classList.iterator(); iterator
				.hasNext();) {
			try {
				String classId = iterator.next();
				RemoteProject project = getDayLogRemoteProject(classId);
				j.updateProject(a, project);
				System.out.println("keys:" + createProjectKey(classId, "")
						+ "  name:" + "DayLog" + classId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void createDayProblemProjects() {
		for (Iterator<String> iterator = classList.iterator(); iterator
				.hasNext();) {
			try {
				String classId = iterator.next();
				RemoteProject project = getDayProblemRemoteProject(classId);
				j.updateProject(a, project);
				System.out.println("keys:" + createProjectKey(classId, "")
						+ "  name:" + "DayLog" + classId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static RemoteProject getDayLogRemoteProject(String classId)
			throws RemoteException, RemotePermissionException,
			RemoteValidationException, RemoteAuthenticationException,
			com.atlassian.jira.rpc.soap.client.RemoteException {
		RemoteProject project = new RemoteProject();
		project.setKey(createProjectKey(classId, "DL"));
		project.setName("DayLog" + classId);
		project.setDescription("Class " + classId + " desc");
		project.setProjectUrl("http://www.lxitedu.com");
		project.setLead("admin");
		RemotePermissionScheme permissionSchem = new RemotePermissionScheme();
		permissionSchem.setId(new Long(0));
		project.setPermissionScheme(permissionSchem);
		j.createProjectFromObject(a, project);
		RemotePermissionScheme defaultPermScheme = getPermissionSchemeFromClassId(classId);
		project.setPermissionScheme(defaultPermScheme);
		return project;
	}

	private static RemoteProject getDayProblemRemoteProject(String classId)
			throws RemoteException, RemotePermissionException,
			RemoteValidationException, RemoteAuthenticationException,
			com.atlassian.jira.rpc.soap.client.RemoteException {
		RemoteProject project = new RemoteProject();
		project.setKey(createProjectKey(classId, "DP"));
		project.setName("DayProblem" + classId);
		project.setDescription("���� " + classId + "���ÿ���������Ŀ desc");
		project.setProjectUrl("http://www.lxitedu.com");
		project.setLead("admin");
		RemotePermissionScheme permissionSchem = new RemotePermissionScheme();
		permissionSchem.setId(new Long(0));
		project.setPermissionScheme(permissionSchem);
		j.createProjectFromObject(a, project);
		RemotePermissionScheme defaultPermScheme = getDayProblemPermissionSchemeFromClassId(classId);
		project.setPermissionScheme(defaultPermScheme);
		return project;
	}

	private static RemotePermissionScheme getPermissionSchemeFromClassId(
			String classId) throws RemoteException, RemotePermissionException,
			RemoteValidationException, RemoteAuthenticationException,
			com.atlassian.jira.rpc.soap.client.RemoteException {
		RemotePermissionScheme defaultPermScheme = new RemotePermissionScheme();
		defaultPermScheme.setId(new Long(0));
		j.createPermissionScheme(a,
				getRemotePermissionSchemeNameByClassId(classId),
				"this is permissioScheme for " + classId);
		return defaultPermScheme;
	}

	private static RemotePermissionScheme getDayProblemPermissionSchemeFromClassId(
			String classId) throws RemoteException, RemotePermissionException,
			RemoteValidationException, RemoteAuthenticationException,
			com.atlassian.jira.rpc.soap.client.RemoteException {
		RemotePermissionScheme defaultPermScheme = new RemotePermissionScheme();
		defaultPermScheme.setId(new Long(0));
		j.createPermissionScheme(a,
				getDayProblemRemotePermissionSchemeNameByClassId(classId),
				"this is permissioScheme for " + classId);
		return defaultPermScheme;
	}

	private static String getRemotePermissionSchemeNameByClassId(String classId) {
		return classId + " PersmissionScheme";
	}

	private static String getDayProblemRemotePermissionSchemeNameByClassId(
			String classId) {
		return "DayProblem" + classId + " PersmissionScheme";
	}

	private static String createProjectKey(String type, String prefex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("0901", prefex + "ONE");
		map.put("0902", prefex + "TWO");
		map.put("0903", prefex + "THREE");
		map.put("0905", prefex + "FIVE");
		map.put("0906", prefex + "SIX");
		map.put("1001", prefex + "1001");
		map.put("1002", prefex + "1002");
		map.put("1003", prefex + "1003");
		map.put("1005", prefex + "1005");
		map.put("1006", prefex + "1006");
		map.put("1101", prefex + "1101");
		map.put("1102", prefex + "1102");
		map.put("1103", prefex + "1103");
		map.put("1105", prefex + "1105");
		map.put("1106", prefex + "1106");
		map.put("1107", prefex + "1107");
		map.put("1108", prefex + "1108");
		return map.get(type);
	}

	private static void createStudents() {
		List<Student> studentList = new LinkedList<Student>();
		try {
			studentList = DBManager.getStudentById("100123");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String pinyinName;
		RemoteUser user = null;
		for (Iterator<Student> iterator = studentList.iterator(); iterator
				.hasNext();) {
			Student student = iterator.next();
			pinyinName = PinyinTools.getLoginNameFromStudent(student);
			try {
				// j.deleteUser(a, pinyinName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				user = j.createUser(a, pinyinName, pinyinName,
						student.getName(), student.getId() + "@gmail.com");
			} catch (Exception e) {
				e.printStackTrace();
			}
			RemoteGroup group;
			try {
				group = j.getGroup(a, student.getClassId().toLowerCase());
				j.addUserToGroup(a, group, user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("�ɹ������û�:" + student.getName() + "��¼��Ϊ:"
					+ pinyinName);
		}
		// jiraSoapService.createUser(authToken, "username", "password",
		// "fullname",
		// "email@gmail.com");
		// jiraSoapService.addUserToGroup(in0, in1, in2)

	}

	private static void createGroups() {

		for (Iterator<String> iterator = classList.iterator(); iterator
				.hasNext();) {
			String className = iterator.next();
			try {
				JiraTools.getJiraSoapService().deleteGroup(JiraTools.getAuth(),
						getGroupNameFromClassName(className), null);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		for (Iterator<String> iterator = classList.iterator(); iterator
				.hasNext();) {
			String className = iterator.next();
			try {
				JiraTools.getJiraSoapService().createGroup(JiraTools.getAuth(),
						getGroupNameFromClassName(className), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private static String getGroupNameFromClassName(String className) {
		return className.toLowerCase();
	}

	private static void createSinglePermissionScheme() {
		/**
		 * RemotePermission:name=Administer Projects, permission=23
		 * RemotePermission:name=Browse Projects, permission=10
		 * RemotePermission:name=Create Issues, permission=11
		 * RemotePermission:name=Edit Issues, permission=12
		 * RemotePermission:name=Assign Issues, permission=13
		 * RemotePermission:name=Resolve Issues, permission=14
		 * RemotePermission:name=Add Comments, permission=15
		 * RemotePermission:name=View Version Control, permission=29
		 * RemotePermission:name=Schedule Issues, permission=28
		 * RemotePermission:name=Move Issues, permission=25
		 * RemotePermission:name=Assignable User, permission=17
		 * RemotePermission:name=Close Issues, permission=18
		 * RemotePermission:name=Modify Reporter, permission=30
		 * RemotePermission:name=Delete Issues, permission=16
		 * RemotePermission:name=Link Issues, permission=21
		 * RemotePermission:name=Set Issue Security, permission=26
		 * RemotePermission:name=View Voters and Watchers, permission=31
		 * RemotePermission:name=Manage Watchers, permission=32
		 * RemotePermission:name=Edit All Comments, permission=34
		 * RemotePermission:name=Edit Own Comments, permission=35
		 * RemotePermission:name=Delete All Comments, permission=36
		 * RemotePermission:name=Delete Own Comments, permission=37
		 * RemotePermission:name=Create Attachments, permission=19
		 * RemotePermission:name=Delete All Attachments, permission=38
		 * RemotePermission:name=Delete Own Attachments, permission=39
		 * RemotePermission:name=Work On Issues, permission=20
		 * RemotePermission:name=Edit Own Worklogs, permission=40
		 * RemotePermission:name=Edit All Worklogs, permission=41
		 * RemotePermission:name=Delete Own Worklogs, permission=42
		 * RemotePermission:name=Delete All Worklogs, permission=43
		 **/
		RemotePermissionScheme defaultPermScheme = null;
		try {
			// RemotePermission[] allPermissions = j.getAllPermissions(a);
			// for (int i = 0; i < allPermissions.length; i++) {
			// System.out.println(allPermissions[i]);
			// }
			// System.out.println("Main.createSinglePermissionScheme()");
			// defaultPermScheme = j.createPermissionScheme(a,
			// "createPersmissionScheme", "createPermission Desc");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		RemotePermission permission = new RemotePermission("", 23L);
		try {
			// RemoteEntity remoteEntity = j.getUser(a, "admin");
			RemoteEntity remoteEntity = j.getGroup(a, "0901");
			// j.addPermissionTo(a, defaultPermScheme, permission,
			// remoteEntity);
			System.out.println("Main.createSinglePermissionScheme() success");
			getPermissionSchemeFromName("FirstPermissionScheme");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void updateStudentDayLogPermissionFromClassId() {
		List<String> list = classList;
		for (String classId : list) {
			try {
				RemotePermissionScheme remotePermissionScheme = getPermissionSchemeFromName(getRemotePermissionSchemeNameByClassId(classId));

				if (remotePermissionScheme != null) {
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 10L),
							j.getGroup(a, getGroupNameFromClassName(classId)));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 11L),
							j.getGroup(a, getGroupNameFromClassName(classId)));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 12L),
							j.getGroup(a, getGroupNameFromClassName(classId)));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 15L),
							j.getGroup(a, getGroupNameFromClassName(classId)));
				}
				System.out.println("update permission success :"
						+ getGroupNameFromClassName(classId));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private static void updateTeahcerDayLogPermissionFromClassId() {
		List<String> list = classList;
		for (String classId : list) {
			try {
				RemoteGroup teacherGroup = j.getGroup(a, TEACHER_GROUP_NAME);
				RemotePermissionScheme remotePermissionScheme = getPermissionSchemeFromName(getRemotePermissionSchemeNameByClassId(classId));

				if (remotePermissionScheme != null) {
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 10L), teacherGroup);
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 11L), teacherGroup);
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 12L), teacherGroup);
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 13L), teacherGroup);
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 14L), teacherGroup);
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 15L), teacherGroup);
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 17L), teacherGroup);
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 35L), teacherGroup);
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 37L), teacherGroup);
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 39L), teacherGroup);
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 42L), teacherGroup);
				}
				System.out.println("update permission success :"
						+ getGroupNameFromClassName(classId));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private static void updateAdminDayLogPermissionFromClassId() {
		List<String> list = classList;
		for (String classId : list) {
			try {
				RemotePermissionScheme remotePermissionScheme = getPermissionSchemeFromName(getRemotePermissionSchemeNameByClassId(classId));

				if (remotePermissionScheme != null) {
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 10L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 11L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 12L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 13L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 14L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 15L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 17L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 18L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 23L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 35L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 36L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 37L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 38L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 39L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 40L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 41L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 42L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 43L),
							j.getUser(a, "admin"));
					j.addPermissionTo(a, remotePermissionScheme,
							new RemotePermission("", 16L),
							j.getUser(a, "admin"));
				}
				System.out.println("update permission success :"
						+ getGroupNameFromClassName(classId));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private static void updateProjectPermissionScheme() {
		List<String> list = classList;
		for (String classId : list) {
			try {
				RemoteProject projectByKey = j.getProjectByKey(a,
						createProjectKey(classId, "DL"));
				projectByKey
						.setPermissionScheme(getPermissionSchemeFromClassId(getRemotePermissionSchemeNameByClassId(classId)));
				j.updateProject(a, projectByKey);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static RemotePermissionScheme getPermissionSchemeFromName(
			String permissionSchemeName) throws RemoteException,
			RemotePermissionException, RemoteAuthenticationException,
			com.atlassian.jira.rpc.soap.client.RemoteException,
			RemoteValidationException {
		RemotePermissionScheme[] permissionSchemes = j.getPermissionSchemes(a);

		for (int i = 0; i < permissionSchemes.length; i++) {
			if (permissionSchemes[i].getName().equals(permissionSchemeName)) {
				return permissionSchemes[i];
			}
		}
		return null;
	}

	public static void deleteIssue() {
		try {
			RemoteIssue[] remoteIssues = j.getIssuesFromJqlSearch(a,
					"project != DLTWO ", 1000);
			for (int i = 0; i < remoteIssues.length; i++) {
				System.out.println("Main.deleteIssue()" + remoteIssues[i]);
				j.deleteIssue(a, remoteIssues[i].getKey());
				System.out.println("Main.deleteIssue() key:" + remoteIssues[i]);

			}
		} catch (com.atlassian.jira.rpc.soap.client.RemoteException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public static void createDayProblemProject(LxitClass lxitClass) {
		String classId = lxitClass.getName();
		RemoteProject project;
		try {
			project = getDayProblemRemoteProject(classId);
			updateStudentDayProblemPermissionFromClassId(classId);
			updateTeacherDayProblemPermissionFromClassId(classId);
			j.updateProject(a, project);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void updateStudentDayProblemPermissionFromClassId(
			String classId) {
		try {
			RemotePermissionScheme permission = getPermissionSchemeFromName(getDayProblemRemotePermissionSchemeNameByClassId(classId));

			if (permission != null) {
				j.addPermissionTo(a, permission, new RemotePermission("",
						PERMISSION_BROWSE), j.getGroup(a,
						getGroupNameFromClassName(classId)));
				j.addPermissionTo(a, permission, new RemotePermission("",
						PERMISSION_CREATE_ISSUE), j.getGroup(a,
						getGroupNameFromClassName(classId)));
				j.addPermissionTo(a, permission, new RemotePermission("",
						PERMISSION_EDIT_ISSUE), j.getGroup(a,
						getGroupNameFromClassName(classId)));
				j.addPermissionTo(a, permission, new RemotePermission("",
						PERMISSION_CLOSE_ISSUE), j.getGroup(a,
						getGroupNameFromClassName(classId)));
				j.addPermissionTo(a, permission, new RemotePermission("",
						PERMISSION_ADD_COMMENT), j.getGroup(a,
						getGroupNameFromClassName(classId)));
				j.addPermissionTo(a, permission, new RemotePermission("",
						PERMISSION_EDIT_OWN_COMMENT), j.getGroup(a,
						getGroupNameFromClassName(classId)));
			}
			System.out.println("update permission success :"
					+ getGroupNameFromClassName(classId));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void updateTeacherDayProblemPermissionFromClassId(
			String classId) {
		try {
			RemoteGroup teacherGroup = j.getGroup(a, TEACHER_GROUP_NAME);
			RemotePermissionScheme permission = getPermissionSchemeFromName(getDayProblemRemotePermissionSchemeNameByClassId(classId));

			if (permission != null) {
				j.addPermissionTo(a, permission, new RemotePermission("",
						PERMISSION_BROWSE), teacherGroup);
				j.addPermissionTo(a, permission, new RemotePermission("",
						PERMISSION_EDIT_ISSUE), teacherGroup);
				j.addPermissionTo(a, permission, new RemotePermission("",
						PERMISSION_RESOLVE_ISSUE), teacherGroup);
				j.addPermissionTo(a, permission, new RemotePermission("",
						PERMISSION_ADD_COMMENT), teacherGroup);
				j.addPermissionTo(a, permission, new RemotePermission("",
						PERMISSION_EDIT_OWN_COMMENT), teacherGroup);
				j.addPermissionTo(a, permission, new RemotePermission("",
						PERMISSION_ASSIGNABLE), teacherGroup);
			}
			System.out.println("update permission success :"
					+ getGroupNameFromClassName(classId));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void createSingleUser(Student student) {
		String loginName = ExtendUserTool.getLoginName(student);
		RemoteUser user = null;
		try {
			// j.deleteUser(a, pinyinName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			user = j.createUser(a, loginName, loginName, student.getName(),
					student.getId() + "@gmail.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
		RemoteGroup group;
		try {
			group = j.getGroup(a, student.getClassId().toLowerCase());
			j.addUserToGroup(a, group, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("�ɹ������û�:" + student.getName() + "��¼��Ϊ:"
				+ loginName);

	}

	public static void createDayLogProject(String classId) {
		RemoteProject project;
		try {
			project = getDayLogRemoteProject(classId);
			j.updateProject(a, project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("keys:" + createProjectKey(classId, "") + "  name:"
				+ "DayLog" + classId);
	}

	public static void createStudnetAndGroup(LxitClass lxitClass) {
		List<Student> studentList = new LinkedList<Student>();
		studentList = DBManager.getStudentListFromClassId(lxitClass.getId());
		String pinyinName;
		RemoteUser user = null;
		for (Iterator<Student> iterator = studentList.iterator(); iterator
				.hasNext();) {
			Student student = iterator.next();
			pinyinName = PinyinTools.getLoginNameFromStudent(student);
			try {
				j.deleteUser(a, pinyinName);
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("����������û�,ɾ��ɹ�");
			}
			try {
				user = j.createUser(a, pinyinName, pinyinName,
						student.getName(), student.getId() + "@gmail.com");
			} catch (Exception e) {
				e.printStackTrace();
			}
			RemoteGroup group;
			try {
				group = j.getGroup(a, student.getClassId().toLowerCase());
				j.addUserToGroup(a, group, user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("�ɹ������û�:" + student.getName() + "��¼��Ϊ:"
					+ pinyinName);
		}

	}

	public static void createGroup(String id) {
		try {
			if (j.getGroup(a, id) == null) {
				j.createGroup(a, id, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void batchDeleteIssueFromClassId(String id) {
		try { // Fixme
			RemoteIssue[] remoteIssues = j.getIssuesFromJqlSearch(a,
					"project = " + createProjectKey("1001", "DL") + "", 2000);
			for (int i = 0; i < remoteIssues.length; i++) {
				System.out.println("Main.deleteIssue()" + remoteIssues[i]);
				j.deleteIssue(a, remoteIssues[i].getKey());
				System.out.println("Main.deleteIssue() key:" + remoteIssues[i]);

			}
		} catch (com.atlassian.jira.rpc.soap.client.RemoteException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public static void createDayLogPermissionsAndScheme(String classId) {
		RemotePermissionScheme remotePermissionScheme = null;
		try {
			remotePermissionScheme = j.createPermissionScheme(a,
					getDayLogPermissionSchemeNameFromClassId(classId), classId
							+ " PermissionScheme Desc");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		System.out.println("����Ȩ��<"
				+ getDayLogPermissionSchemeNameFromClassId(classId) + ">�ɹ�");
		try {

			addStudentTeacherAdminPermissionToCurrentPermissionScheme(classId,
					remotePermissionScheme);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addStudentTeacherAdminPermissionToCurrentPermissionScheme(
			String classId, RemotePermissionScheme remotePermissionScheme)
			throws RemoteException, RemotePermissionException,
			RemoteValidationException, RemoteAuthenticationException,
			com.atlassian.jira.rpc.soap.client.RemoteException {
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				10L), j.getGroup(a, getGroupNameFromClassName(classId)));
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				11L), j.getGroup(a, getGroupNameFromClassName(classId)));
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				12L), j.getGroup(a, getGroupNameFromClassName(classId)));
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				15L), j.getGroup(a, getGroupNameFromClassName(classId)));

		RemoteGroup teacherGroup = j.getGroup(a, TEACHER_GROUP_NAME);

		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				10L), teacherGroup);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				11L), teacherGroup);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				12L), teacherGroup);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				13L), teacherGroup);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				14L), teacherGroup);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				15L), teacherGroup);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				17L), teacherGroup);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				35L), teacherGroup);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				37L), teacherGroup);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				39L), teacherGroup);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				42L), teacherGroup);

		RemoteUser adminUser = j.getUser(a, "admin");
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				10L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				11L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				12L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				13L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				14L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				15L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				16L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				17L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				18L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				19L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				20L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				21L), adminUser);
		// j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
		// 22L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				23L), adminUser);
		// j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
		// 24L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				25L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				26L), adminUser);
		// j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
		// 27L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				28L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				29L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				30L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				31L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				32L), adminUser);
		// j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
		// 33L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				34L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				35L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				36L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				37L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				38L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				39L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				40L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				41L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				42L), adminUser);
		j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("",
				43L), adminUser);
	}

	private static String getDayLogPermissionSchemeNameFromClassId(
			String classId) {
		return classId + "DayLogPermissionScheme";
	}

	public static void fixIssue(String issueKey) throws Exception, Throwable,
			com.atlassian.jira.rpc.soap.client.RemoteException, RemoteException {
		RemoteFieldValue[] map = null;
		j.progressWorkflowAction(a, issueKey, "5", map);
	}

	public static RemoteIssue[] getIssuesByJql(String jql) throws Exception,
			Throwable {
		return j.getIssuesFromJqlSearch(a, jql, 800);
	}

	public static void fixIssueBy1003(String key) throws Exception,
			RemoteException {
		RemoteFieldValue[] map = null;
		j.progressWorkflowAction(a, key, "11", map);

	}

	public static RemoteIssue getIssueByKey(String string) {

		try {
			return j.getIssue(a, string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static RemoteProject getProjectByKey(String string) {
		// TODO Auto-generated method stub
		try {
			return j.getProjectByKey(a, string);
		} catch (RemotePermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteAuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (com.atlassian.jira.rpc.soap.client.RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static RemoteIssue createIssue(RemoteIssue issue) {
		RemoteIssue createIssue = null;
		try {
			createIssue = j.createIssue(a, issue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return createIssue;

	}

	public static void createJiraStudentsToGroup(
			List<com.lxit.entity.Student> classStudentList, String className)
			throws Exception, RemoteValidationException,
			RemoteAuthenticationException,
			com.atlassian.jira.rpc.soap.client.RemoteException, RemoteException {
		String pinyinName;
		RemoteUser user = null;
		RemoteGroup group;
		group = j.getGroup(a, className);
		for (Iterator<com.lxit.entity.Student> iterator = classStudentList
				.iterator(); iterator.hasNext();) {
			com.lxit.entity.Student student = iterator.next();
			pinyinName = PinyinTools.getLoginNameFromStudent(student);
			try {
				user = j.createUser(a, pinyinName, pinyinName,
						student.getName(), student.getId() + "@gmail.com");
			} catch (Exception e) {
				e.printStackTrace();
			}
			j.addUserToGroup(a, group, user);
			System.out.println("创建学生:" + student.getName() + "拼音名为:"
					+ pinyinName);
		}

	}
}
