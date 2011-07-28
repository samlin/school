package com.lxitedu.dao.exam.dbutlimpl;

import javax.sql.DataSource;

import com.lxitedu.bean.ExamUser;
import com.lxitedu.dao.exam.ExamDAO;
import com.lxitedu.framework.dao.DataSourceFactory;
import com.lxitedu.student.dao.kauimpl.KauSuperDAO;

import fi.devtrain.kauklahti.Table;

public class ExamDAOKaulImpl extends KauSuperDAO<Object> implements ExamDAO {

  public void addUser(ExamUser examUser) {
    String userName = examUser.getName();
    String userMail = examUser.getName() + "@gmail.com";
    String userPassword = examUser.getPassword();
    String regdateDate = "2010-12-04 15:28:15";
    StringBuilder addSql = new StringBuilder();
    addSql
        .append("INSERT INTO tce_users ( user_regdate, user_ip, user_name, user_email, user_password, user_regnumber, ");
    addSql.append("user_firstname");
    addSql.append(", user_lastname, user_birthdate, user_birthplace, user_ssn, user_level ) VALUES ( '" + regdateDate
        + "', '0000:0000:0000:0000:0000:ffff:7f00:0001', '");
    addSql.append(userName);
    addSql.append("', '");
    addSql.append(userMail);
    addSql.append("', '");
    addSql.append(userPassword);
    addSql.append("', NULL, '" + examUser.getRealName() + "', NULL, NULL, NULL, NULL, '10' )");
    String sql = addSql.toString();
    jdbcTemplate.execute(sql);
  }

  @Override
  protected DataSource getDataSource() {
    return DataSourceFactory.getExamDataSource();
  }

  @Override
  public Table getKauTable() {
    return null;

  }

  public void addGroup(String groupName) {
    String sql = "INSERT INTO tce_user_groups ( group_name ) VALUES ( '" + groupName + "')";
    jdbcTemplate.execute(sql);
  }
}
