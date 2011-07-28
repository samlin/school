package com.lxitedu.framework.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * suit the php
 * 
 * @author Administrator
 * 
 */
public class MD5 {
  private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
      "e", "f"                           };

  public static void main(String[] args) throws Exception {
    getMD5EncryptedString("");

  }

  public static String getMD5EncryptedString(String sourceString) {
    // 加密后的字符串
    // 创建具有指定算法名称的信息摘要
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
    byte[] results = md.digest(sourceString.getBytes());
    return byteArrayToHexString(results);
  }

  private static String byteArrayToHexString(byte[] b) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      resultSb.append(byteToHexString(b[i]));
    }
    return resultSb.toString();
  }

  /**
   * 将一个字节转化成十六进制形式的字符串
   */
  private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0)
      n = 256 + n;
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
  }
}
