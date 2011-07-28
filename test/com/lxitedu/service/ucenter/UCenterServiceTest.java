package com.lxitedu.service.ucenter;

import org.junit.Test;

import com.lxitedu.bean.Student;

public class UCenterServiceTest {
  private UCenterService uCenterService = new UCenterService();

  @Test
  public void testAddUser() {
    Student student = new Student();
    student.setName("÷£∆º¡·");
    student.setClassId("0904");
    uCenterService.addUser(student);

  }

  // @Test
  public void testAddUserByClass() throws Exception {
    uCenterService.addUsersByClassId("1006");
  }

  // @Test
  public void testAddGroup() throws Exception {
    uCenterService.addGroup("1006");
  }

}
