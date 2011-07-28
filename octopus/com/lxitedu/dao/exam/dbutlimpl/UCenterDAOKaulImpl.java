package com.lxitedu.dao.exam.dbutlimpl;

import javax.sql.DataSource;

import com.lxitedu.bean.UCenterUser;
import com.lxitedu.dao.UCenterDAO;
import com.lxitedu.framework.dao.DataSourceFactory;
import com.lxitedu.student.dao.kauimpl.KauSuperDAO;

import fi.devtrain.kauklahti.Table;

public class UCenterDAOKaulImpl extends KauSuperDAO<Object> implements UCenterDAO {

  public void addUser(UCenterUser uCentUser) {
    String string = uCentUser.getName();
    String password = uCentUser.getPassword();
    String email = uCentUser.getEmail();
    String sqlInsert = "INSERT INTO pre_ucenter_members SET secques='', username='" + string + "', password='"
        + password + "', email='" + email + "', regip='unknown', regdate='1291782949', salt='5cad96'";
    String sql = sqlInsert.toString();
    jdbcTemplate.execute(sql);
  }

  @Override
  protected DataSource getDataSource() {
    return DataSourceFactory.getUCenterDataSource();
  }

  @Override
  public Table getKauTable() {
    return null;

  }

}
