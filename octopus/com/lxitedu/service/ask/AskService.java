package com.lxitedu.service.ask;

import java.util.List;

import com.lxitedu.bean.AskUser;
import com.lxitedu.bean.Student;
import com.lxitedu.dao.DBManager;
import com.lxitedu.dao.ask.AskDAO;
import com.lxitedu.framework.tools.ExtendUserTool;
import com.lxitedu.framework.tools.MD5;

public class AskService {
  AskDAO askDAO = new AskDaoKauImpl();

  public void addUser(Student student) {

    AskUser askUser = getAskUserFromStudent(student);
    askDAO.addUser(askUser);
  }

  private AskUser getAskUserFromStudent(Student student) {
    AskUser askUser = new AskUser();
    askUser.setName(ExtendUserTool.getLoginName(student));
    askUser.setPassword(MD5.getMD5EncryptedString(ExtendUserTool.getLoginName(student)));
    askUser.setEmail(ExtendUserTool.getLoginName(student) + "@gmail.com");
    return askUser;
  }

  public void addUsersByClassName(String string) {
    List<Student> lists = DBManager.getStudentListFromClassId(string);
    for (Student student : lists) {
      askDAO.addUser(getAskUserFromStudent(student));
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

}
