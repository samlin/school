package com.lxitedu.framework.tools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import com.lxitedu.bean.Student;

public class PinyinTools {
  public static String getStringPinYin(String string) {
    System.out.println("PinyinTools.getStringPinYin() _" + string + "_");
    char[] singleChar = string.trim().toCharArray();
    String result = "";
    HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    for (int i = 0; i < singleChar.length; i++) {

      try {
        String tt[] = PinyinHelper.toHanyuPinyinStringArray(singleChar[i], format);
        result = result + tt[0];
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    System.out.println("PinyinTools.getStringPinYin() after _" + result + "_");
    return result;

  }

  public static String getLoginNameFromStudent(Student student) {
    return student.getClassId().toLowerCase() + "." + PinyinTools.getStringPinYin(student.getName());
  }

public static String getLoginNameFromStudent(com.lxit.entity.Student student) {
	  return student.getClassId().toLowerCase() + "." + PinyinTools.getStringPinYin(student.getName());
}
}
