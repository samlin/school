package com.itdaoshi.dbsource;

import static org.junit.Assert.*;

import org.junit.Test;


public class PinyinToolsTest {
@Test
public void testGetStringPinYin() throws Exception {
  assertEquals("zhangyoulin", PinyinTools.getStringPinYin("уеспау"));
}
}
