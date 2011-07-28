/*
 * @(#)CdbUcMembers.java
 *
 * Copyright (c) 2003 DCIVision Ltd
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of DCIVision
 * Ltd ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with DCIVision Ltd.
 */
package com.itdaoshi.discuz.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.itdaoshi.discuz.bean.CdbUcMembers;
import com.lxitedu.discuz.dao.DiscuzDAO;

/**
 * CdbUcMembersDAObject.java
 * 
 * This class is the data access bean for table "CDB_UC_MEMBERS".
 * 
 * @author Samlin Zhang
 * @company DCIVision Limited
 * @creation date 26/06/2009
 * @version $Revision: 1.38 $
 */

public class CdbUcMembersDAObject extends DiscuzDAO {

  public static final String REVISION = "$Revision: 1.38 $";

  public static final String TABLE_NAME = "CDB_UC_MEMBERS";


  public CdbUcMembersDAObject() {

  }

  protected synchronized Object insertDB(Object obj1) {
    PreparedStatement preStat = null;
    StringBuffer sqlStat = new StringBuffer();
    CdbUcMembers obj = (CdbUcMembers) obj1;
    synchronized (conn) {
      try {
         Long nextID = getNextPrimaryID();
//        Integer nextID = 2;
        sqlStat.append("INSERT ");
        sqlStat
            .append("INTO   CDB_UC_MEMBERS(uid, username, password, email, myid, myidkey, regip, regdate, lastloginip, lastlogintime, salt, secques) ");
        sqlStat.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
        preStat = conn.prepareStatement(sqlStat.toString(),
            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        setPrepareStatement(preStat, 1, nextID);
        setPrepareStatement(preStat, 2, obj.getUsername());
        setPrepareStatement(preStat, 3, obj.getPassword());
        setPrepareStatement(preStat, 4, obj.getEmail());
        setPrepareStatement(preStat, 5, obj.getMyid());
        setPrepareStatement(preStat, 6, obj.getMyidkey());
        setPrepareStatement(preStat, 7, obj.getRegip());
        setPrepareStatement(preStat, 8, obj.getRegdate());
        setPrepareStatement(preStat, 9, obj.getLastloginip());
        setPrepareStatement(preStat, 10, obj.getLastlogintime());
        setPrepareStatement(preStat, 11, obj.getSalt());
        setPrepareStatement(preStat, 12, obj.getSecques());
        preStat.executeUpdate();
        return obj;
      } catch (Exception Ex) {
        Ex.printStackTrace();
      } finally {
        try {
          preStat.close();
        } catch (Exception ignore) {
        } finally {
          preStat = null;
        }
      }
    }
    return obj;
  }

  @Override
  protected Long getNextPrimaryID()   {
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
      Object[] result = (Object[]) run.query( conn, "SELECT MAX(uid) FROM CDB_UC_MEMBERS ", h);
      return (Long)result[0]+1;
    // do something with the result
    }catch (Exception e) {
     e.printStackTrace();
    
  }  

    return null;
  }



 

}
