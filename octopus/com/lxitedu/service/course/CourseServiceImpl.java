package com.lxitedu.service.course;

import java.util.List;

import com.lxitedu.course.dao.CourseDAO;
import com.lxitedu.dao.DAOFactory;
import com.lxitedu.gcalendar.Course;

//this is samlin add 2010-5-14
public class CourseServiceImpl {
  private CourseDAO courseDAO = DAOFactory.getCourseDAO();

  public List<Course> getList() {

    return courseDAO.getList();
  }

  public void add(Course bean) {
    courseDAO.add(bean);

  }

  public void update(Course course) {
    courseDAO.update(course);

  }

}
