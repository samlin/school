package com.lxitedu.dao.dokeos.kauimpl;

import javax.sql.DataSource;

import com.lxitedu.bean.dokeos.DokeosUser;
import com.lxitedu.dao.dokeos.DokeosUserDAO;
import com.lxitedu.framework.dao.DataSourceFactory;
import com.lxitedu.student.dao.kauimpl.KauSuperDAO;

import fi.devtrain.kauklahti.Column;
import fi.devtrain.kauklahti.Table;

//this is samlin add 2010-4-22
public class DokeosUserDAOKauImpl extends KauSuperDAO<DokeosUser> implements DokeosUserDAO {

  @Override
  public Table getKauTable() {
    Table table = configurator.config(DokeosUser.class).tableName("user");
    table.columns(new Column("lastname").mappedField("lastname"), new Column("firstname").mappedField("firstname"),
        new Column("username").mappedField("username"), new Column("password").mappedField("password"), new Column(
            "auth_source").mappedField("auth_Source"), new Column("email").mappedField("email"), new Column("status")
            .mappedField("status"), new Column("official_code").mappedField("officialCode"), new Column("phone")
            .mappedField("phone"), new Column("picture_uri").mappedField("pictureUri"), new Column("creator_id")
            .mappedField("creatorID"), new Column("competences").mappedField("competences"), new Column("diplomas")
            .mappedField("diplomas"), new Column("openarea").mappedField("openarea"), new Column("teach")
            .mappedField("teach"), new Column("productions").mappedField("productions"), new Column("chatcall_user_id")
            .mappedField("chatcallUserID"), new Column("chatcall_date").mappedField("chatcallDate"), new Column(
            "chatcall_text").mappedField("chatcallText"), new Column("language").mappedField("language"), new Column(
            "registration_date").mappedField("registrationDate"), new Column("expiration_date")
            .mappedField("expirationDate"), new Column("active").mappedField("active"), new Column("openid")
            .mappedField("openid"), new Column("theme").mappedField("theme"), new Column("hr_dept_id")
            .mappedField("hrDeptID"));
    return table;
  }

  @Override
  protected DataSource getDataSource() {
    return DataSourceFactory.getDokeosDataSource();
  }

  public void addUser(DokeosUser user) {
    kauAdd(user);

  }

}
