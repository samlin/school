package com.itdaoshi.confluence;

import org.junit.Test;

import com.itdaoshi.dbsource.SourceUser;

/**
 * The class <code>ConfluenceInsertServiceTest</code> contains tests for the
 * class {@link <code>ConfluenceInsertService</code>}
 * 
 * @pattern JUnit Test Case
 * 
 * @generatedBy CodePro at 11-4-19 H10:19
 * 
 * @author Administrator
 * 
 * @version $Revision$
 */
public class ConfluenceInsertServiceTest {

  /**
   * Construct new test instance
   * 
   * @param name
   *          the test name
   */

  /**
   * Launch the test.
   * 
   * @param args
   *          String[]
   */

  /**
   * Run the void postNewUser(SourceUser) method test
   */
  public void testPostNewUser() {
    // add test code here
    SourceUser next = new SourceUser();
    next.setCardID("dsfsfsafsafsd");
    next.setClassName("1101");
    next.setLoginName("1101.zhangyoulin");
    next.setStudentName("уеспау");
    ConfluenceInsertService.postNewUser(next);
  }

  @Test
  public void testPostUserFromClassId() {

    ConfluenceInsertService.createUsersFromClassId("1003");
  }
}
