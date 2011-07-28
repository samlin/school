package com.lxitedu.dao;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

//this is samlin add 2010-4-22
public class AskDataSource extends BasicDataSource {
  public AskDataSource() {
    // this.setDriverClassName("com.mysql.jdbc.Driver");
    // this.setUsername("root");
    // this.setPassword("root");
    // this.setUrl("jdbc:mysql://localhost:3306/tcexam?useUnicode=true&characterEncoding=UTF-8");
    this.setDriverClassName("com.mysql.jdbc.Driver");
    this.setUsername("root");
    this.setPassword("root");
    this.setUrl("jdbc:mysql://192.168.1.252:3306/ask?useUnicode=true&characterEncoding=UTF-8");
  }
}
