package com.lxitedu.dao;

import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.Test;

public class AskDataSourceTest {
  private AskDataSource askDataSource = new AskDataSource();

  @Test
  public void testDataSource() throws Exception {
    Assert.assertNotNull(askDataSource);
  }

  @Test
  public void testGetConnection() {
    try {
      Assert.assertNotNull(askDataSource.getConnection());
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}
