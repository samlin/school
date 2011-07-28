package com.lxitedu.dao;

import com.lxitedu.course.dao.CourseDAO;
import com.lxitedu.course.dao.kauimpl.CourseDAOkauImpl;
import com.lxitedu.dao.dokeos.DokeosLxitClassDAO;
import com.lxitedu.dao.dokeos.DokeosUserDAO;
import com.lxitedu.dao.dokeos.kauimpl.DokeosLxitClassDAOKauImpl;
import com.lxitedu.dao.dokeos.kauimpl.DokeosUserDAOKauImpl;
import com.lxitedu.student.dao.kauimpl.LxitClassDAOKauImpl;
import com.lxitedu.student.dao.kauimpl.StudentDAOKauImpl;

//this is samlin add 2010-4-19
public class DAOFactory {
  public static StudentDAO getStudentDAO() {
    return new StudentDAOKauImpl();

  }

  public static LxitClassDAO getLxitClassDAO() {
    return new LxitClassDAOKauImpl();
  }

  public static DokeosUserDAO getDokeosUserDAO() {
    return new DokeosUserDAOKauImpl();
  }

  public static DokeosLxitClassDAO getDokeosLxitClassDAO() {
    return new DokeosLxitClassDAOKauImpl();
  }

  public static CourseDAO getCourseDAO() {
    return new CourseDAOkauImpl();
  }
}
