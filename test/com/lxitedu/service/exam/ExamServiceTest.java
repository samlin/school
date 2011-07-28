package com.lxitedu.service.exam;

import java.util.List;

import org.junit.Test;

import com.lxitedu.bean.Student;
import com.lxitedu.dao.DBManager;

public class ExamServiceTest {
  private ExamService examService = new ExamService();

  // @Test
  public void testAddUser() {
    Student student = new Student();
    student.setName("罗涛");
    student.setClassId("0901");
    examService.addUser(student);

  }

  // @Test
  public void testAddUserByClass() throws Exception {
    examService.addUsersByClassId("1006");
  }

  // @Test
  public void testAddGroup() throws Exception {
    examService.addGroup("1006");
  }

  // fixme 完成用户和组的关联关系 SELECT LAST_INSERT_ID() FROM
  @Test
  public void testAddUsersAndGroupFromClassId() throws Exception {
    List<String> list = DBManager.getAllClassName();
    for (String string : list) {
      examService.AddUsersAndGroupFromClassId(string);
      Thread.sleep(200);
      System.out.println("ExamServiceTest.testAddUsersAndGroupFromClassId()" + string);
    }

  }
}
