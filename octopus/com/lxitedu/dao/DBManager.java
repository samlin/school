package com.lxitedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.lxitedu.bean.Student;

public class DBManager {
  private static String STUDENT_TABLE = "studentdev";
  private static Connection connect = DBConnection.getConnection();
  private static String querySql = "select id,classid,name,sex,native,nation,education,birth,hometelephone,mobilephone,idcard,homeaddress,dateofenrollment from "
      + STUDENT_TABLE + " order by id";
  private static String querySingleSql = "select id,classid,name,sex,nativePlace,nation,education,birth,telephone,mobilephone,idcard,homeaddress,dateofenrollment from "
      + STUDENT_TABLE + " where id=";
  private static String queryStudnetSql = "select id,classid,name,sex,nativePlace,nation,education,birth,telephone,mobilephone,idcard,homeaddress,dateofenrollment from "
      + STUDENT_TABLE + " ";

  static {
    connect = DBConnection.getConnection();
  }

  public static void addSingleStudent(Student student) throws SQLException {
    PreparedStatement preparedStatement = connect.prepareStatement("insert into " + STUDENT_TABLE
        + " values(id,?,?,?,?,?,?,?,?,?,?,?)");
    int index = 1;
    preparedStatement.setString(index++, student.getName());
    preparedStatement.setString(index++, student.getSex());
    preparedStatement.setString(index++, student.getNativePlace());
    preparedStatement.setString(index++, student.getNation());
    preparedStatement.setString(index++, student.getEducation());
    preparedStatement.setString(index++, student.getBirth());
    preparedStatement.setString(index++, student.getTelephone() + "");
    preparedStatement.setString(index++, student.getMobilePhone() + "");
    preparedStatement.setString(index++, student.getIdCard() + "");
    preparedStatement.setString(index++, student.getHomeAddress());
    preparedStatement.setString(index++, student.getDateOfEnrollment());
    preparedStatement.execute();
  }

  public static void deleteSingleStudent(String studentId) throws SQLException {
    PreparedStatement preparedStatement = connect.prepareStatement("delete from " + STUDENT_TABLE + " where id=?");
    preparedStatement.setString(1, studentId);
    preparedStatement.execute();
  }

  public static List<Student> getSingleStudent(String studentId) throws SQLException {
    List<Student> studentList = new ArrayList<Student>();
    PreparedStatement preparedStatement = connect.prepareStatement(querySingleSql + " where id=?");
    preparedStatement.setString(1, studentId);
    preparedStatement.execute();
    ResultSet rst = preparedStatement.getResultSet();
    Student student = null;
    while (rst.next()) {
      student = new Student();
      student.setId(rst.getString("id"));
      student.setName(rst.getString("name"));
      student.setSex(rst.getString("sex"));
      student.setNativePlace(rst.getString("native"));
      student.setNation(rst.getString("nation"));
      student.setEducation(rst.getString("education"));
      student.setBirth(rst.getString("birth"));
      student.setTelephone(rst.getString("hometelephone"));
      student.setMobilePhone(rst.getString("mobilephone"));
      student.setIdCard(rst.getString("idcard"));
      student.setHomeAddress(rst.getString("homeaddress"));
      student.setDateOfEnrollment(rst.getString("dateofenrollment"));
      studentList.add(student);

    }
    return studentList;
  }

  public static List<Student> getStudentListFromClassId(String classId) {
    List<Student> studentList = new ArrayList<Student>();
    try {
      PreparedStatement preparedStatement = connect.prepareStatement(queryStudnetSql + " where classid=?");
      preparedStatement.setString(1, classId);
      preparedStatement.execute();
      ResultSet rst = preparedStatement.getResultSet();
      Student student = null;
      while (rst.next()) {
        student = new Student();
        student.setId(rst.getString("id"));
        student.setClassId(rst.getString("classid"));
        student.setName(rst.getString("name"));
        student.setSex(rst.getString("sex"));
        student.setNativePlace(rst.getString("nativePlace"));
        student.setNation(rst.getString("nation"));
        student.setEducation(rst.getString("education"));
        student.setBirth(rst.getString("birth"));
        student.setTelephone(rst.getString("telephone"));
        student.setMobilePhone(rst.getString("mobilephone"));
        student.setIdCard(rst.getString("idcard"));
        student.setHomeAddress(rst.getString("homeaddress"));
        student.setDateOfEnrollment(rst.getString("dateofenrollment"));
        studentList.add(student);

      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return studentList;
  }

  public static List<String> getAllClassName() {
    PreparedStatement preparedStatement;
    List<String> studentList = new LinkedList<String>();
    try {
      preparedStatement = connect.prepareStatement("select distinct classId from " + STUDENT_TABLE
          + " order by classId");
      preparedStatement.execute();
      ResultSet rst = preparedStatement.getResultSet();
      while (rst.next()) {
        studentList.add(rst.getString("classId"));
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return studentList;
  }

  public static List<Student> getStudentList() {
    List<Student> studentList = new LinkedList<Student>();
    try {
      PreparedStatement preparedStatement = connect.prepareStatement(querySql);
      preparedStatement.execute();
      ResultSet rst = preparedStatement.getResultSet();
      studentList = new LinkedList<Student>();
      while (rst.next()) {
        Student student = new Student();
        student.setId(rst.getString("id"));
        student.setName(rst.getString("name"));
        student.setClassId(rst.getString("classId"));
        student.setSex(rst.getString("sex"));
        student.setNativePlace(rst.getString("native"));
        student.setNation(rst.getString("nation"));
        student.setEducation(rst.getString("education"));
        student.setBirth(rst.getString("birth"));
        student.setTelephone(rst.getString("hometelephone"));
        student.setMobilePhone(rst.getString("mobilephone"));
        student.setIdCard(rst.getString("idcard"));
        student.setHomeAddress(rst.getString("homeaddress"));
        student.setDateOfEnrollment(rst.getString("dateofenrollment"));
        studentList.add(student);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return studentList;
  }

  public static List<Student> getStudentById(String Id) throws SQLException {
    PreparedStatement preparedStatement = connect.prepareStatement(querySingleSql + Id);
    preparedStatement.execute();
    ResultSet rst = preparedStatement.getResultSet();
    List<Student> studentList = new LinkedList<Student>();
    while (rst.next()) {
      Student student = new Student();
      student.setId(rst.getString("id"));
      student.setName(rst.getString("name"));
      student.setClassId(rst.getString("classId"));
      student.setSex(rst.getString("sex"));
      student.setNativePlace(rst.getString("native"));
      student.setNation(rst.getString("nation"));
      student.setEducation(rst.getString("education"));
      student.setBirth(rst.getString("birth"));
      student.setTelephone(rst.getString("hometelephone"));
      student.setMobilePhone(rst.getString("mobilephone"));
      student.setIdCard(rst.getString("idcard"));
      student.setHomeAddress(rst.getString("homeaddress"));
      student.setDateOfEnrollment(rst.getString("dateofenrollment"));
      studentList.add(student);
    }
    return studentList;
  }

  public static void updateSingleStudent(Student student) {
    try {
      PreparedStatement preparedStatement = connect
          .prepareStatement(" update "
              + STUDENT_TABLE
              + " set classId=?,name=?,sex=?,native=?,nation=?,education=?,birth=?,hometelephone=?,mobilephone=?,idcard=?,homeaddress=?,dateofenrollment=? where id=?");
      int index = 1;
      preparedStatement.setString(index++, student.getClassId());
      preparedStatement.setString(index++, student.getName());
      preparedStatement.setString(index++, student.getSex());
      preparedStatement.setString(index++, student.getNativePlace());
      preparedStatement.setString(index++, student.getNation());
      preparedStatement.setString(index++, student.getEducation());
      preparedStatement.setString(index++, student.getBirth());
      preparedStatement.setString(index++, student.getTelephone() + "");
      preparedStatement.setString(index++, student.getMobilePhone() + "");
      preparedStatement.setString(index++, student.getIdCard() + "");
      preparedStatement.setString(index++, student.getHomeAddress());
      preparedStatement.setString(index++, student.getDateOfEnrollment());
      preparedStatement.setString(index++, student.getId() + "");
      preparedStatement.execute();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
