package com.lxitedu.dao;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class LxitDataSource extends BasicDataSource {

  public LxitDataSource() {
    this.setDriverClassName("com.mysql.jdbc.Driver");
    this.setUsername("root");
    this.setPassword("");
    this.setUrl("jdbc:mysql://localhost:3306/lxitdev?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true");
    // this.setDriverClassName("com.mysql.jdbc.Driver");
    // this.setUsername("samlin@192.168.5.249");
    // this.setPassword("samlinzhang");
    // this.setUrl("jdbc:mysql://192.168.1.236:3306/lxitdev?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true");
  }

}
