package com.lxitedu.service.dokeos;

import java.util.List;

import com.lxitedu.bean.Student;
import com.lxitedu.bean.dokeos.DokeosUser;
import com.lxitedu.dao.DAOFactory;
import com.lxitedu.dao.dokeos.DokeosUserDAO;
import com.lxitedu.framework.tools.ExtendUserTool;
import com.lxitedu.framework.tools.MD5;

//this is samlin add 2010-4-22
public class DokeosUserService {
  private DokeosUserDAO dokeosUserDAO = DAOFactory.getDokeosUserDAO();

  public void add(Student student) {
    DokeosUser initDokeosUser = getInitDokeosUser();
    initDokeosUser.setUsername(ExtendUserTool.getLoginName(student));
    initDokeosUser.setFirstname(ExtendUserTool.getPinYinName(student));
    initDokeosUser.setLastname(student.getClassId());
    initDokeosUser.setOfficialCode(student.getId());
    initDokeosUser.setPassword(MD5.getMD5EncryptedString(ExtendUserTool.getLoginName(student)));
    dokeosUserDAO.addUser(initDokeosUser);
  }

  private DokeosUser getInitDokeosUser() {
    DokeosUser user = new DokeosUser();

    user.setAuth_Source("platform");
    user.setStatus(5);
    user.setCreatorID(1);
    user.setChatcallUserID(0);
    user.setLanguage("simpl_chinese");
    user.setActive(1);
    user.setHrDeptID(0);
    user.setChatcallText("");
    java.util.Date today = new java.util.Date();
    java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
    user.setChatcallDate(timestamp);
    user.setRegistrationDate(timestamp);
    java.sql.Timestamp timestamp1 = new java.sql.Timestamp(today.getTime());
    timestamp1.setTime(25414645);
    // user.setExpirationDate(timestamp1);
    return user;
  }

  public void addStudentByClassName(String className) {
    List<Student> list = DAOFactory.getStudentDAO().getListByClassName(className);
    System.out.println("DokeosUserService.addStudentByClassName()" + list);
    // for (Iterator iterator = list.iterator(); iterator.hasNext();) {
    // Student student = (Student) iterator.next();
    // add(student);
    //
    // }
    for (Student student : list) {
      add(student);
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }

  public boolean isClassNameExist(String name) {
    // TODO Auto-generated method stub
    return DAOFactory.getStudentDAO().isClassNameExist(name);
  }
}
