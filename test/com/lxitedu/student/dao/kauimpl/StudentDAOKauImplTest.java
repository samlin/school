package com.lxitedu.student.dao.kauimpl;

import java.util.List;

import org.junit.Test;

import com.lxitedu.bean.Student;
import com.lxitedu.dao.StudentDAO;
import com.lxitedu.service.jira.LxitJiraService;

//this is samlin add 2010-4-19
public class StudentDAOKauImplTest {
  @Test
  public void testAdd() throws Exception {
    StudentDAO daoKauImpl = new StudentDAOKauImpl();
    System.out.println("StudentDAOKauImplTest.testAdd()" + daoKauImpl.getListByClassName("1001"));
    ;
    List<Student> list = daoKauImpl.getListByClassName("1001");
    for (Student student : list) {
      LxitJiraService.createSingleUser(student);
      Thread.sleep(100);
      System.out.println("Sutdent:" + student);
    }
  }

}