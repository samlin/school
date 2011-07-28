package com.lxitedu.dao.exam.dbutlimpl;

import org.junit.Test;

import com.lxitedu.bean.ExamUser;
import com.lxitedu.bean.Student;

public class ExamUserDAODbutilImplTest {

  @Test
  public void testAddUser() {
    Student student = new Student();
    student.setName("уеспау");
    ExamDAOKaulImpl daoDbutilImpl = new ExamDAOKaulImpl();
    daoDbutilImpl.addUser(new ExamUser());
  }

}
