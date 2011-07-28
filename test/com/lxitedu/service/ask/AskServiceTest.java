package com.lxitedu.service.ask;

import org.junit.Test;

import com.lxitedu.bean.Student;

public class AskServiceTest {
  private AskService askService = new AskService();

  // @Test
  public void testAddUser() throws Exception {
    Student student = getStudentModel();
    askService.addUser(student);
  }

  private Student getStudentModel() {
    Student student = new Student();
    student.setName("уелн");
    student.setClassId("0904");
    return student;
  }

  @Test
  public void testAddUsersByClassId() throws Exception {
    askService.addUsersByClassName("0906");
  }
}
