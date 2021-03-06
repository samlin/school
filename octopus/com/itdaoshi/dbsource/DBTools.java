package com.itdaoshi.dbsource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import net.sourceforge.pinyin4j.PinyinHelper;

public class DBTools {
  private static Connection con;

  public static synchronized Connection getSourceConnection() {
    String url = "jdbc:mysql://192.168.1.250:3306/lxitsm";
//    String url = "jdbc:jtds:sqlserver://192.168.1.249:1433/samlin";
    if (con != null) {
      return con;
    }
    try {
//      Class.forName("net.sourceforge.jtds.jdbc.Driver");
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection(url, "root", "root");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return con;

  }

  private static List<SourceUser> getAllUsers() {
    ResultSetHandler h = new BeanListHandler(SourceUser.class);
    QueryRunner run = new QueryRunner();
    // Execute the SQL statement with one replacement parameter and
    // return the results in a new Person object generated by the BeanHandler.
    List<SourceUser> persons = new LinkedList<SourceUser>();
    try {
      String sql = "SELECT id,studentName,className,cardID FROM sheet1";
//      String sql = "SELECT tkeys.id as id ,tkeys.studentName as studentName,tkeys.className as className, tuser.password as password,tuser.email as email FROM tkeys,tuser where tkeys.cardId=tuser.idCard";
      persons = (List<SourceUser>) run.query(getSourceConnection(),
          sql, h);

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return persons;

  }

  public static List<SourceUser> getFinalUserInfo()  {
    List<SourceUser> list = getAllUsers();
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      SourceUser sourceUser = (SourceUser) iterator.next();
      sourceUser.setLoginName(sourceUser.getClassName()+"."+PinyinTools.getStringPinYin(sourceUser.getStudentName()));
      sourceUser.setPassword(sourceUser.getLoginName());
      
    }
    System.out.println(list);
    return list;

  }
  
  public static void main(String[] args) {
    List<SourceUser> list = getFinalUserInfo();
    System.out.println("密码和用户名一样,可以进去修改");
    for (SourceUser sourceUser : list) {
      System.out.println("姓名:"+sourceUser.getStudentName() + "   登录名:"+sourceUser.getClassName()+"."
          + PinyinTools.getStringPinYin(sourceUser.getStudentName()));
    }
  }
}
