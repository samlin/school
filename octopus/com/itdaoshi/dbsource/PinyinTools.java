package com.itdaoshi.dbsource;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class PinyinTools {
public static String getStringPinYin(String string) {
  // TODO Auto-generated method stub
  String str1=null;
  char[] singleChar=string.toCharArray();
  String result="";
  HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
  format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
  for (int i = 0; i < singleChar.length; i++) {
    
    try {
      String tt[]=PinyinHelper.toHanyuPinyinStringArray(singleChar[i], format);
      result = result + tt[0];
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
  
  return result;

}
}
