package com.lxitedu.student.dao.kauimpl;

import java.util.List;

import javax.sql.DataSource;

import com.lxitedu.bean.LxitClass;
import com.lxitedu.dao.LxitClassDAO;
import com.lxitedu.framework.dao.DataSourceFactory;

import fi.devtrain.kauklahti.Table;

//this is samlin add 2010-4-20
public class LxitClassDAOKauImpl extends KauSuperDAO<LxitClass> implements LxitClassDAO {

  @Override
  public Table getKauTable() {
    return configurator.config(LxitClass.class).tableName("lxitclass");
  }

  public List<LxitClass> getList() {

    return kauGetList();
  }

  @Override
  protected DataSource getDataSource() {
    // TODO Auto-generated method stub
    return DataSourceFactory.getLxitDataSource();
  }

}
