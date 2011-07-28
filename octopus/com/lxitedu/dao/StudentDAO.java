package com.lxitedu.dao;

import java.util.List;

import com.lxitedu.bean.Student;

//this is samlin add 2010-4-19
public interface StudentDAO {
  void add(Student student);

  void update(Student student);

  List<Student> kauGetList();

  void delete(Student student);

  List<Student> getListByClassName(String className);

  boolean isClassNameExist(String className);

}
