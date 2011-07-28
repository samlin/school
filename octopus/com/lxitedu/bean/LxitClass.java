package com.lxitedu.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class LxitClass implements Serializable {
  private String id;
  private String name;
  private String descs;
  private String teacherId;
  public int hashCode(){
    return 32;
  }
}
