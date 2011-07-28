package com.itdaoshi.dbsource;

public class SourceUser {
  private long id;
  private String studentName;
  private String className;
  private String cardID;
 private String loginName;
 private String password;
 private String email;
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "SourceUser [cardID=" + cardID + ", className=" + className
        + ", email=" + email + ", id=" + id + ", loginName=" + loginName
        + ", password=" + password + ", studentName=" + studentName + "]";
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getCardID() {
    return cardID;
  }

  public void setCardID(String cardID) {
    this.cardID = cardID;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public String getLoginName() {
    return loginName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}
