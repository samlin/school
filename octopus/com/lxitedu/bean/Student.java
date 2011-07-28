package com.lxitedu.bean;

import java.io.Serializable;

import lombok.Data;
@Data
public class  Student implements Serializable {
  private String id;
  private String classId;
  private String name;
  private String sex;
  private String nativePlace;
  private String education;
  private String nation;
  private String birth;
  private String telephone;
  private String mobilePhone;
  private String idCard;
  private String homeAddress;
  private String dateOfEnrollment;
  
  public int hashCode() {
    return 31;

  }

}
