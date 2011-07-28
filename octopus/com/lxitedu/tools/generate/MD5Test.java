package com.lxitedu.tools.generate;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MD5Test {

  @Test
  public void testGetMD5EncryptedString() throws Exception {
    String encrptString = MD5.getMD5EncryptedString(MD5.getMD5EncryptedString("user1") + "11161a");
    assertEquals("f75a7fc0c92ac23ef341c75bf56bb460", encrptString);
    
    //test for dokeos
    String encrptString1 = MD5.getMD5EncryptedString("samlinzhang");
    assertEquals("7c08fdff47b77c2173839d172a4f3837", encrptString1);
    
  }
}
