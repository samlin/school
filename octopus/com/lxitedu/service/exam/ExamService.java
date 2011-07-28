package com.lxitedu.service.exam;

import java.util.List;

import com.lxitedu.bean.ExamUser;
import com.lxitedu.bean.Student;
import com.lxitedu.dao.DBManager;
import com.lxitedu.dao.exam.ExamDAO;
import com.lxitedu.dao.exam.dbutlimpl.ExamDAOKaulImpl;
import com.lxitedu.framework.tools.ExtendUserTool;
import com.lxitedu.framework.tools.MD5;

public class ExamService {
  private ExamDAO examDAO = new ExamDAOKaulImpl();

  public void addUser(Student student) {
    ExamUser examUser = getExamUserFromStudent(student);
    examDAO.addUser(examUser);

  }

  private ExamUser getExamUserFromStudent(Student student) {
    ExamUser examUser = new ExamUser();
    examUser.setName(ExtendUserTool.getLoginName(student));
    examUser.setRealName(student.getName());
    examUser.setPassword(MD5.getMD5EncryptedString(ExtendUserTool.getLoginName(student)));
    return examUser;
  }

  public void addUsersByClassId(String classId) {
    List<Student> students = DBManager.getStudentListFromClassId(classId);
    System.out.println("ExamService.addUsersByClassId() student:" + students.size());
    for (Student student : students) {
      examDAO.addUser(getExamUserFromStudent(student));
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void addGroup(String string) {
    examDAO.addGroup(string);
  }

  public void AddUsersAndGroupFromClassId(String classId) {
    addUsersByClassId(classId);
    addGroup(classId);
  }
}
