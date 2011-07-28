package com.lxitedu.gcalendar;

import java.io.Serializable;

import lombok.Data;

@Data
public class Course implements Serializable{
  private String id;
  private String name;
  private int    seq;
  private String content;
  private String goal;
  private int    days;
  public int hashCode() {
    return 31;

  }
}
