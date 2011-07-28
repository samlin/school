package com.lxitedu.service.dokeos;

import org.junit.Before;
import org.junit.Test;

import com.lxitedu.bean.Student;

//this is samlin add 2010-4-23
public class DokeosUserServiceTest {

  private DokeosUserService dokeosUserService;

  @Before
  public void setup() {
    dokeosUserService = new DokeosUserService();
  }

  // @Test
  public void testAdd() {
    Student student = new Student();
    student.setId("0901.pengliang1");
    student.setName("ºú½õÌÎ");
    student.setClassId("0904");

    dokeosUserService.add(student);
  }

  @Test
  public void testAddStudentByClassName() throws Exception {
    dokeosUserService.addStudentByClassName("lxit");
  }
}
