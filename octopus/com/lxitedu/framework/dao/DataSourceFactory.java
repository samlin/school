package com.lxitedu.framework.dao;

import javax.sql.DataSource;

import com.lxitedu.dao.DokeosDataSource;
import com.lxitedu.dao.ExamDataSource;
import com.lxitedu.dao.LxitDataSource;
import com.lxitedu.dao.UCenterDataSource;

//this is samlin add 2010-4-22
public class DataSourceFactory {

  public static DataSource getDokeosDataSource() {
    return new DokeosDataSource();
  }

  public static DataSource getLxitDataSource() {
    return new LxitDataSource();
  }

  public static DataSource getExamDataSource() {
    return new ExamDataSource();
  }

  public static DataSource getUCenterDataSource() {
    // TODO Auto-generated method stub
    return new UCenterDataSource();
  }

}
