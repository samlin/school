package com.lxitedu.service.jira.project;

import com.atlassian.jira.rpc.soap.client.RemoteGroup;
import com.atlassian.jira.rpc.soap.client.RemotePermission;
import com.atlassian.jira.rpc.soap.client.RemotePermissionScheme;
import com.atlassian.jira.rpc.soap.client.RemoteProject;
import com.atlassian.jira.rpc.soap.client.RemoteUser;

public class HomeworkRemoteProjectBuilder extends RemoteProjectBuilder {

    @Override
    public RemotePermissionScheme buildPermissionScheme() {
        RemotePermissionScheme remotePermissionScheme = null;
        try {
            remotePermissionScheme = j.createPermissionScheme(a, getPermissionSchemeName(), getPermissionSchemeDesc());
            createDetailPermission(remotePermissionScheme);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return remotePermissionScheme;

    }

    public String getPermissionSchemeDesc() {
        return getClassName() + "Homework PermissionScheme Desc";
    }

    @Override
    public String getPermissionSchemeName() {
        return getClassName() + "-Homework-" + "PermisssionScheme";
    }

    @Override
    public void createDetailPermission(RemotePermissionScheme remotePermissionScheme) throws Exception {
        RemoteGroup classGroup = j.getGroup(a, getClassName());
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 10L), classGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 11L), classGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 12L), classGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 15L), classGroup);

        RemoteGroup teacherGroup = j.getGroup(a, TEACHER_GROUP_NAME);

        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 10L), teacherGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 11L), teacherGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 12L), teacherGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 13L), teacherGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 14L), teacherGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 15L), teacherGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 17L), teacherGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 35L), teacherGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 37L), teacherGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 39L), teacherGroup);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 42L), teacherGroup);

        RemoteUser adminUser = j.getUser(a, "admin");
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 10L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 11L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 12L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 13L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 14L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 15L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 16L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 17L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 18L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 19L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 20L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 21L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 23L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 25L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 26L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 28L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 29L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 30L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 31L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 32L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 34L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 35L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 36L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 37L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 38L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 39L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 40L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 41L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 42L), adminUser);
        j.addPermissionTo(a, remotePermissionScheme, new RemotePermission("", 43L), adminUser);
    }

    //    "23"= Administer Projects </option>
    //    "10"= Browse Projects </option>
    //    "29"= View Version Control </option>
    //    "11"= Create Issues </option>
    //    "12"= Edit Issues </option>
    //    "28"= Schedule Issues </option>
    //    "25"= Move Issues </option>
    //    "13"= Assign Issues </option>
    //    "17"= Assignable User </option>
    //    "14"= Resolve Issues </option>
    //    "18"= Close Issues </option>
    //    "30"= Modify Reporter </option>
    //    "16"= Delete Issues </option>
    //    "21"= Link Issues </option>
    //    "26"= Set Issue Security </option>
    //    "31"= View Voters and Watchers </option>
    //    "32"= Manage Watchers </option>
    //    "15"= Add Comments </option>
    //    "34"= Edit All Comments </option>
    //    "35"= Edit Own Comments </option>
    //    "36"= Delete All Comments </option>
    //    "37"= Delete Own Comments </option>
    //    "19"= Create Attachments </option>
    //    "38"= Delete All Attachments </option>
    //    "39"= Delete Own Attachments </option>
    //    "20"= Work On Issues </option>
    //    "40"= Edit Own Worklogs </option>
    //    "41"= Edit All Worklogs </option>
    //    "42"= Delete Own Worklogs </option>
    //    "43"= Delete All Worklogs </option>

    @Override
    public void deletePermissionSchemeName() {
        try {
            j.deletePermissionScheme(a, getPermissionSchemeName());
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public RemoteProject createRemoteProject() {
        RemoteProject project = new RemoteProject();
        project.setKey(getProjectKeyFromClassName());
        project.setName("Homework" + getClassName());
        project.setDescription("班级: " + getClassName() + " 的家庭作业项目");
        project.setProjectUrl("http://www.lxitedu.com");
        project.setLead("admin");

        project.setPermissionScheme(buildPermissionScheme());
        try {
            project = j.createProjectFromObject(a, project);
            System.out.println("createRemoteProject():" + project.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public String getProjectKeyFromClassName() {
        return "HW" + getClassName();
    }
}
