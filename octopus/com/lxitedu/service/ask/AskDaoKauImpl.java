package com.lxitedu.service.ask;

import javax.sql.DataSource;

import com.lxitedu.bean.AskUser;
import com.lxitedu.dao.AskDataSource;
import com.lxitedu.dao.ask.AskDAO;
import com.lxitedu.student.dao.kauimpl.KauSuperDAO;

import fi.devtrain.kauklahti.Table;

public class AskDaoKauImpl extends KauSuperDAO<AskUser> implements AskDAO {

  @Override
  protected DataSource getDataSource() {
    return new AskDataSource();
  }

  @Override
  public Table getKauTable() {
    return null;
  }

  public void addUser(AskUser askUser) {
    String sql = "insert into ask_user(username,password,email) values('" + askUser.getName() + "','"
        + askUser.getPassword() + "','" + askUser.getEmail() + "')";
    jdbcTemplate.execute(sql);
  }

}
