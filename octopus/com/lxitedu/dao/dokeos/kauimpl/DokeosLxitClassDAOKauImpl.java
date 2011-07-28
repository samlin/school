package com.lxitedu.dao.dokeos.kauimpl;

import javax.sql.DataSource;

import com.lxitedu.bean.dokeos.DokeosLxitClass;
import com.lxitedu.dao.dokeos.DokeosLxitClassDAO;
import com.lxitedu.framework.dao.DataSourceFactory;
import com.lxitedu.student.dao.kauimpl.KauSuperDAO;

import fi.devtrain.kauklahti.Table;

//this is samlin add 2010-5-5
public class DokeosLxitClassDAOKauImpl extends KauSuperDAO<DokeosLxitClass> implements DokeosLxitClassDAO {

  @Override
  protected DataSource getDataSource() {
    return DataSourceFactory.getDokeosDataSource();
  }

  @Override
  public Table getKauTable() {
    return configurator.config(DokeosLxitClass.class).tableName("class");
  }

  public boolean isClassNameExist(String name) {
    int queryForInt = jdbcTemplate.queryForInt("select count(id) from `class` where name='" + name + "'");
    return queryForInt >= 1;
  }

  public void add(DokeosLxitClass dokeosLxitClass) {
    kauAdd(dokeosLxitClass);

  }

}
