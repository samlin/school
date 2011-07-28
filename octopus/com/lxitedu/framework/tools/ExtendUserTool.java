package com.lxitedu.framework.tools;

import com.lxitedu.bean.Student;

//this is samlin add 2010-4-24
public class ExtendUserTool {

  public static String getLoginName(Student student) {
    return student.getClassId() + "." + PinyinTools.getStringPinYin(student.getName());
  }

  public static String getPinYinName(Student student) {
    return PinyinTools.getStringPinYin(student.getName());
  }

}
