package com.lxitedu.course.dao;

import java.util.List;

import com.lxitedu.gcalendar.Course;

//this is samlin add 2010-5-13
public interface CourseDAO {

  List<Course> getList();

  void add(Course bean);

  void update(Course course);

  List<Course> getListFromBeginSeq(String beginEventSeq);

}
