package com.itdaoshi.dokeos.dao;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.itdaoshi.dbsource.DBTools;
import com.itdaoshi.dbsource.SourceUser;
import com.itdaoshi.dokeos.bean.User;

public class UserDAObjectTest {

  @Test
  public void testAddUser() {
    List<SourceUser> list=DBTools.getFinalUserInfo();
    User user=new User();
    UserDAObject dao=new UserDAObject();
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      SourceUser sourceUser = (SourceUser) iterator.next();
       user=new User();
       user.setFirstname(sourceUser.getClassName());
       user.setLastname(sourceUser.getStudentName());
       user.setPassword(sourceUser.getPassword());
       user.setUsername(sourceUser.getLoginName());
       
       try {
         dao.addUser(user);
        Thread.sleep(100);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
       System.out.println("ÐÕÃû:"+sourceUser.getStudentName()+" µÇÂ¼Ãû³Æ:"+sourceUser.getLoginName());
    }
  }
  

}
