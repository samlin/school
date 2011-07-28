package com.lxitedu.framework.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

  public static synchronized Connection getDiscuzConnection() {
    String url = "jdbc:mysql://192.168.1.250:3306/discuz";
    Connection con = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection(url, "root", "root");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return con;
    
  }
  public static synchronized Connection getDokeosConnection() {
    String url = "jdbc:mysql://192.168.1.246:3306/dokeos_main";
    Connection con = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection(url, "root", "root");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return con;

  }
}
