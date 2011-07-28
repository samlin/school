package com.lxitedu.discuz.dao;


import com.itdaoshi.discuz.bean.CdbUcMembers;
import com.lxitedu.framework.dao.ConnectionFactory;
import com.lxitedu.framework.dao.SuperDAO;
import com.lxitedu.tools.generate.MD5;

public abstract class DiscuzDAO extends SuperDAO {

  @Override
  protected void initConnection() {
    conn=ConnectionFactory.getDiscuzConnection();
  }

  @Override
  public String getModelInsertSql() {
    //lxtodo  的认识和实践
    return null;
  }

  @Override
  protected void initUserSpeclAttr(Object obj1) throws Exception {
    CdbUcMembers cdbUcMembers = (CdbUcMembers) obj1;
    String encrptString = MD5.getMD5EncryptedString(MD5
        .getMD5EncryptedString(cdbUcMembers.getPassword())
        + cdbUcMembers.getSalt());
    cdbUcMembers.setPassword(encrptString);
  }
}
