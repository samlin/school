package com.lxitedu.framework.dao;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.dcivision.framework.TextUtility;

public abstract class SuperDAO {
  protected static Connection conn;

  public SuperDAO() {
    super();
  }


  public void addUser(Object obj) throws Exception{
    initConnection();
    initUserSpeclAttr(obj);
    insertDB(obj);
  }
  
  protected abstract Object insertDB(Object obj);
  protected abstract void initUserSpeclAttr(Object obj) throws Exception;
  protected abstract void initConnection();

  public abstract String getModelInsertSql();
  // Protected methods for preparing statment for DB operation with NULL values.
  public void setPrepareStatement(java.sql.PreparedStatement preStat, int idx, Integer val)
      throws java.sql.SQLException {
    if (val == null) {
      preStat.setNull(idx, java.sql.Types.NUMERIC);
    } else {
      preStat.setInt(idx, val.intValue());
    }
  }

  public void setPrepareStatement(PreparedStatement preStat, int idx, Long val) throws SQLException {
    if (val == null) {
      preStat.setNull(idx, java.sql.Types.NUMERIC);
    } else {
      preStat.setLong(idx, val.longValue());
    }
  }

  public void setPrepareStatement(java.sql.PreparedStatement preStat, int idx, Float val) throws java.sql.SQLException {
    if (val == null) {
      preStat.setNull(idx, java.sql.Types.NUMERIC);
    } else {
      preStat.setFloat(idx, val.floatValue());
    }
  }

  public void setPrepareStatement(java.sql.PreparedStatement preStat, int idx, Double val) throws java.sql.SQLException {
    if (val == null) {
      preStat.setNull(idx, java.sql.Types.NUMERIC);
    } else {
      preStat.setDouble(idx, val.doubleValue());
    }
  }

  public void setPrepareStatement(java.sql.PreparedStatement preStat, int idx, java.sql.Date val)
      throws java.sql.SQLException {
    if (val == null) {
      preStat.setNull(idx, java.sql.Types.DATE);
    } else {
      preStat.setDate(idx, val);
    }
  }

  public void setPrepareStatement(java.sql.PreparedStatement preStat, int idx, java.sql.Timestamp val)
      throws java.sql.SQLException {
    if (val == null) {
      preStat.setNull(idx, java.sql.Types.TIMESTAMP);
    } else {
      // Due to the ABNORMAL behavior of the JDBC-ODBC bridge, the
      // Timestamp cannot be updated correctly. The "second" of timestamp
      // will be truncated. Thus, need to use "setString()" instead of
      // "setTimestamp()"
      // preStat.setString(idx, Utility.formatDate(val, "yyyy-MM-dd HH:mm:ss"));
      preStat.setTimestamp(idx, val);
    }
  }

  public void setPrepareStatement(java.sql.PreparedStatement preStat, int idx, String val) throws java.sql.SQLException {
    if (val == null) {
      preStat.setNull(idx, java.sql.Types.VARCHAR);
    } else {
      preStat.setString(idx, TextUtility.noNull(val).trim());
    }
  }

  public void setPrepareStatement(java.sql.PreparedStatement preStat, int idx, Object val) throws java.sql.SQLException {
    if (val == null) {
      preStat.setNull(idx, java.sql.Types.BINARY);
    } else {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      baos = new ByteArrayOutputStream();
      try {
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(val);
        out.flush();
      } catch (java.io.IOException ioe) {
      ioe.printStackTrace();
        new java.sql.SQLException("Cannot write object stream");
      }
      preStat.setBytes(idx, baos.toByteArray());
    }
  }

  /**
   * Add by TC for SubQuery
   * 
   * @param preStat
   * @param idx
   * @param val
   * @param type
   * @throws SQLException
   */
  public void setPrepareStatement(java.sql.PreparedStatement preStat, int idx, Object val, String type)
      throws SQLException {
    if (type.equals(Integer.class.getName())) {
      this.setPrepareStatement(preStat, idx, (Integer) val);
    }
    if (Float.class.getName().equals(type)) {
      this.setPrepareStatement(preStat, idx, (Float) val);
    }
    if (Double.class.getName().equals(type)) {
      this.setPrepareStatement(preStat, idx, (Double) val);
    }
    if (java.sql.Date.class.getName().equals(type)) {
      this.setPrepareStatement(preStat, idx, (java.sql.Date) val);
    }
    if (java.sql.Timestamp.class.getName().equals(type)) {
      this.setPrepareStatement(preStat, idx, (java.sql.Timestamp) val);
    }
    if (String.class.getName().equals(type)) {
      this.setPrepareStatement(preStat, idx, (String) val);
    }

  }

  public int setPreparedStatement(PreparedStatement stat, int startIndex, List params) throws SQLException {
    for (Iterator it = params.iterator(); it.hasNext();) {
      Object obj = it.next();
      setPrepareStatement(stat, startIndex++, obj, obj.getClass().getName());
    }
    return startIndex;
  }

  /**
   * call setPrepareStatment by a list of Interger[]
   * 
   * @return the next index number
   */
  public int setPrepareStatement(java.sql.PreparedStatement preStat, int idx, Integer[] array)
      throws java.sql.SQLException {
    if (array != null && array.length > 0) {
      for (int j = 0; j < array.length; j++) {
        this.setPrepareStatement(preStat, idx++, array[j]);
      }
    }
    return idx;
  }

  /**
   * call setPrepareStatment by a list of String[]
   * 
   * @return the next index number
   */
  public int setPrepareStatement(java.sql.PreparedStatement preStat, int idx, String[] array)
      throws java.sql.SQLException {
    if (array != null && array.length > 0) {
      for (int j = 0; j < array.length; j++) {
        this.setPrepareStatement(preStat, idx++, array[j]);
      }
    }
    return idx;
  }

  /**
   * call setPrepareStatment by a list of Float[]
   * 
   * @return the next index number
   */
  public int setPrepareStatement(java.sql.PreparedStatement preStat, int idx, Float[] array)
      throws java.sql.SQLException {
    if (array != null && array.length > 0) {
      for (int j = 0; j < array.length; j++) {
        this.setPrepareStatement(preStat, idx++, array[j]);
      }
    }
    return idx;
  }

  /**
   * call setPrepareStatment by a list of Double[]
   * 
   * @return the next index number
   */
  public int setPrepareStatement(java.sql.PreparedStatement preStat, int idx, Double[] array)
      throws java.sql.SQLException {
    if (array != null && array.length > 0) {
      for (int j = 0; j < array.length; j++) {
        this.setPrepareStatement(preStat, idx++, array[j]);
      }
    }
    return idx;
  }

  // Protected methods for getting values from resultset.
  protected Integer getResultSetInteger(java.sql.ResultSet rs, int idx) throws java.sql.SQLException {
    int result = rs.getInt(idx);
    if (rs.wasNull()) {
      return (null);
    } else {
      return (new Integer(result));
    }
  }
  protected abstract Long getNextPrimaryID() ;
}