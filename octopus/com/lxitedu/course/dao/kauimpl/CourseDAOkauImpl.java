package com.lxitedu.course.dao.kauimpl;

import java.util.List;

import javax.sql.DataSource;

import com.lxitedu.course.dao.CourseDAO;
import com.lxitedu.framework.dao.DataSourceFactory;
import com.lxitedu.gcalendar.Course;
import com.lxitedu.student.dao.kauimpl.KauSuperDAO;

import fi.devtrain.kauklahti.Table;

//this is samlin add 2010-5-13
public class CourseDAOkauImpl extends KauSuperDAO<Course> implements CourseDAO {

  @Override
  protected DataSource getDataSource() {
    return DataSourceFactory.getLxitDataSource();
  }

  @Override
  public Table getKauTable() {
    Table table = configurator.config(Course.class).tableName("course");
    return table;
  }

  public List<Course> getList() {
    return kauGetList();
  }

  public void add(Course course) {
    kauAdd(course);
  }

  public void update(Course course) {
    kauUpdate(course);
  }

  public List<Course> getListFromBeginSeq(String beginEventSeq) {

    return null;
  }

}
