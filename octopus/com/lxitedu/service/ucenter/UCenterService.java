package com.lxitedu.service.ucenter;

import java.util.List;

import com.lxitedu.bean.Student;
import com.lxitedu.bean.UCenterUser;
import com.lxitedu.dao.DBManager;
import com.lxitedu.dao.UCenterDAO;
import com.lxitedu.dao.exam.dbutlimpl.UCenterDAOKaulImpl;
import com.lxitedu.framework.tools.ExtendUserTool;
import com.lxitedu.framework.tools.MD5;

public class UCenterService {
  private UCenterDAO uCenterDAO = new UCenterDAOKaulImpl();

  public void addUser(Student student) {
    UCenterUser uCenterUser = getUCenterUserByStudent(student);
    uCenterDAO.addUser(uCenterUser);

  }

  private UCenterUser getUCenterUserByStudent(Student student) {
    UCenterUser uCenterUser = new UCenterUser();
    String loginName = ExtendUserTool.getLoginName(student);
    uCenterUser.setName(loginName);
    uCenterUser.setPassword(MD5.getMD5EncryptedString(MD5.getMD5EncryptedString(loginName) + "5cad96"));
    uCenterUser.setEmail(loginName + "@gmail.com");
    return uCenterUser;
  }

  public void addUsersByClassId(String classId) {
    List<Student> list = DBManager.getStudentListFromClassId(classId);
    for (Student student : list) {
      uCenterDAO.addUser(getUCenterUserByStudent(student));
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }

  public void addGroup(String string) {
    // TODO Auto-generated method stub

  }

}
