package com.lxitedu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

  public synchronized static Connection getConnection() {
    Connection connect = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      // connect =
      // DriverManager.getConnection("jdbc:mysql://192.168.1.236:3306/lxitdev",
      // "samlin@192.168.1.249",
      // "samlinzhang");
      connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/shopxx", "root", "");
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return connect;
  }
}