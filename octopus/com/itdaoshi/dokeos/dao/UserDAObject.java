/*
 * @(#)User.java
 *
 * Copyright (c) 2003 DCIVision Ltd
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of DCIVision
 * Ltd ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with DCIVision Ltd.
 */
package com.itdaoshi.dokeos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.itdaoshi.dokeos.bean.User;
import com.lxitedu.framework.dao.ConnectionFactory;
import com.lxitedu.framework.dao.SuperDAO;
import com.lxitedu.tools.generate.MD5;

/**
  UserDAObject.java

  This class is the data access bean for table "USER".

  @author      Samlin Zhang
  @company     DCIVision Limited
  @creation date   18/07/2009
  @version     $Revision: 1.38 $
*/

public class UserDAObject  extends SuperDAO {

  public static final String REVISION = "$Revision: 1.38 $";

  public static final String TABLE_NAME = "USER";


  public UserDAObject() {
    ;
  }

  protected synchronized User insert(User obj)   {
    PreparedStatement preStat = null;
    StringBuffer sqlStat = new StringBuffer();

    synchronized(conn) {
      try {
        Long nextID = getNextPrimaryID();
        sqlStat.append("INSERT ");
        sqlStat.append("INTO   USER(user_id, lastname, firstname, username, password, auth_source, email, status, official_code, phone, picture_uri, creator_id, competences, diplomas, openarea, teach, productions, chatcall_user_id, chatcall_date, chatcall_text, language, registration_date, expiration_date, active, openid, theme, hr_dept_id) ");
        sqlStat.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
        preStat = conn.prepareStatement(sqlStat.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        setPrepareStatement(preStat, 1, nextID);
        setPrepareStatement(preStat, 2,obj.getLastname());
        setPrepareStatement(preStat, 3,obj.getFirstname());
        setPrepareStatement(preStat, 4,obj.getUsername());
        setPrepareStatement(preStat, 5,obj.getPassword());
        setPrepareStatement(preStat, 6,obj.getAuthSource());
        setPrepareStatement(preStat, 7,obj.getEmail());
        setPrepareStatement(preStat, 8,obj.getStatus());
        setPrepareStatement(preStat, 9,obj.getOfficialCode());
        setPrepareStatement(preStat, 10,obj.getPhone());
        setPrepareStatement(preStat, 11,obj.getPictureUri());
        setPrepareStatement(preStat, 12,obj.getCreatorID());
        setPrepareStatement(preStat, 13,obj.getCompetences());
        setPrepareStatement(preStat, 14,obj.getDiplomas());
        setPrepareStatement(preStat, 15,obj.getOpenarea());
        setPrepareStatement(preStat, 16,obj.getTeach());
        setPrepareStatement(preStat, 17,obj.getProductions());
        setPrepareStatement(preStat, 18,obj.getChatcallUserID());
        setPrepareStatement(preStat, 19,obj.getChatcallDate());
        setPrepareStatement(preStat, 20,obj.getChatcallText());
        setPrepareStatement(preStat, 21,obj.getLanguage());
        setPrepareStatement(preStat, 22,obj.getRegistrationDate());
        setPrepareStatement(preStat, 23,obj.getExpirationDate());
        setPrepareStatement(preStat, 24,obj.getActive());
        setPrepareStatement(preStat, 25,obj.getOpenid());
        setPrepareStatement(preStat, 26,obj.getTheme());
        setPrepareStatement(preStat, 27,obj.getHrDeptID());
        preStat.executeUpdate();
        return obj;
      } catch (Exception Ex) {
        Ex.printStackTrace();
      } finally {
        try { preStat.close(); } catch (Exception ignore) {} finally { preStat = null; }
      }
    }
 return obj;
  }

  @Override
  public String getModelInsertSql() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Long getNextPrimaryID() {

    QueryRunner run = new QueryRunner();
    ResultSetHandler h = new ResultSetHandler() {
      public Object handle(ResultSet rs) throws SQLException {
          if (!rs.next()) {
              return null;
          }
      
          ResultSetMetaData meta = rs.getMetaData();
          int cols = meta.getColumnCount();
          Object[] result = new Object[cols];

          for (int i = 0; i < cols; i++) {
              result[i] = rs.getObject(i + 1);
          }

          return result;
      }
  };
    try{
      Object[] result = (Object[]) run.query( conn, "SELECT MAX(user_id) FROM USER ", h);
      return (Long)result[0]+1;
    // do something with the result
    }catch (Exception e) {
     e.printStackTrace();
    
  }  

    return null;
  
  }

  @Override
  protected void initConnection() {
    conn=ConnectionFactory.getDokeosConnection();
    
  }

  @Override
  protected void initUserSpeclAttr(Object obj) throws Exception {
    User user=(User)obj;
    user.setPassword(MD5.getMD5EncryptedString(user.getPassword()));
    user.setAuthSource("platform");
    user.setStatus(5);
    user.setCreatorID(1);
    user.setChatcallUserID(0);
    user.setLanguage("simpl_chinese");
    user.setActive(1);
    user.setHrDeptID(0);
    user.setChatcallText("");
    java.util.Date today = new java.util.Date();
    java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
    user.setChatcallDate(timestamp);
    user.setRegistrationDate(timestamp);
    java.sql.Timestamp timestamp1=new java.sql.Timestamp(today.getTime());
    timestamp1.setTime(25414645);
    user.setExpirationDate(timestamp1);
  }

  @Override
  protected Object insertDB(Object obj) {
    return insert((User)obj);
  }

}
