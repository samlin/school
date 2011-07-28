package com.itdaoshi.discuz.dao;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.itdaoshi.dbsource.DBTools;
import com.itdaoshi.dbsource.SourceUser;
import com.itdaoshi.discuz.bean.CdbUcMembers;

public class CdbUcMembersDAObjectTest {

  @Test
  public void testAddUser() throws Exception {
    CdbUcMembers cum=new CdbUcMembers();
//    cum.setUsername("samlinzhang2");
//    cum.setEmail("samlinzhang@gmail.com");
//    cum.setMyidkey("zhang");
//    cum.setPassword("zhang2");
//    cum.setLastlogintime(11111);
//    cum.setRegip("192.168.1.249");
//    cum.setSecques("");
//    cum.setLastloginip(5);
//    cum.setRegdate(3);
//    cum.setMyid("01");
//    cum.setSalt("012012");
    CdbUcMembersDAObject dao=new CdbUcMembersDAObject();
//    dao.addUser(cum);
    
    List<SourceUser> list=DBTools.getFinalUserInfo();
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      SourceUser sourceUser = (SourceUser) iterator.next();
       cum=new CdbUcMembers();
       cum.setUsername(sourceUser.getLoginName());
       cum.setMyidkey("zhang");
       cum.setPassword(sourceUser.getPassword());
       cum.setSecques("");
       cum.setLastlogintime(111111);
       cum.setEmail(sourceUser.getLoginName()+"@lxitedu.com");
       cum.setSalt("samlin");
       cum.setRegip("192.168.1.253");
       cum.setMyid("01");
       cum.setLastloginip(249);
       cum.setRegdate(3);
//       dao.addUser(cum);
       Thread.sleep(100);
       System.out.println("ÐÕÃû:"+sourceUser.getStudentName()+" µÇÂ¼Ãû³Æ:"+sourceUser.getLoginName());
    }
  }

}
