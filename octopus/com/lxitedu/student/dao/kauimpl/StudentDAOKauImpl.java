package com.lxitedu.student.dao.kauimpl;

import java.util.List;

import javax.sql.DataSource;

import com.lxitedu.bean.Student;
import com.lxitedu.dao.StudentDAO;
import com.lxitedu.framework.dao.DataSourceFactory;

import fi.devtrain.kauklahti.Table;

public class StudentDAOKauImpl extends KauSuperDAO<Student> implements StudentDAO {
  public void add(Student student) {
    kauAdd(student);
  }

  @Override
  public List<Student> kauGetList() {
    return super.kauGetList();
  }

  public void update(Student student) {
    kauUpdate(student);
  }

  @Override
  public Table getKauTable() {
    final Table table = configurator.config(Student.class).tableName("studentdev");
    return table;
  }

  public void delete(Student student) {
    kauDelete(student);
  }

  @Override
  protected DataSource getDataSource() {
    return DataSourceFactory.getLxitDataSource();
  }

  public List<Student> getListByClassName(String className) {
    return kauGetListByWhere(" classId='" + className + "'");

  }

  public boolean isClassNameExist(String className) {
    jdbcTemplate.queryForInt("select * from user");
    return false;
  }
}
