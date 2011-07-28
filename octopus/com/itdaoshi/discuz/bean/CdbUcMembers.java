/*
 * @(#)CdbUcMembers.java
 *
 * Copyright (c) 2003 DCIVision Ltd
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of DCIVision
 * Ltd ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with DCIVision Ltd.
 */
package com.itdaoshi.discuz.bean;


/**
  CdbUcMembers.java

  This class is the serializable bean reflecting business logic uses.

    @author           Samlin Zhang
    @company          Lxit Trainning
    @creation date    26/06/2009
    @version          $Revision: 1.20 $
*/

public class CdbUcMembers  {

  public static final String REVISION = "$Revision: 1.20 $";

  private Integer uid = null;
  private String username = null;
  private String password = null;
  private String email = null;
  private String myid = null;
  private String myidkey = null;
  private String regip = null;
  private Integer regdate = null;
  private Integer lastloginip = null;
  private Integer lastlogintime = null;
  private String salt = null;
  private String secques = null;

  public CdbUcMembers() {
    super();
  }

  public Integer getUid() { 
    return(this.uid);
  }

  public void setUid(Integer uid) { 
    this.uid = uid;
  }

  public String getUsername() { 
    return(this.username);
  }

  public void setUsername(String username) { 
    this.username = username;
  }

  public String getPassword() { 
    return(this.password);
  }

  public void setPassword(String password) { 
    this.password = password;
  }

  public String getEmail() { 
    return(this.email);
  }

  public void setEmail(String email) { 
    this.email = email;
  }

  public String getMyid() { 
    return(this.myid);
  }

  public void setMyid(String myid) { 
    this.myid = myid;
  }

  public String getMyidkey() { 
    return(this.myidkey);
  }

  public void setMyidkey(String myidkey) { 
    this.myidkey = myidkey;
  }

  public String getRegip() { 
    return(this.regip);
  }

  public void setRegip(String regip) { 
    this.regip = regip;
  }

  public Integer getRegdate() { 
    return(this.regdate);
  }

  public void setRegdate(Integer regdate) { 
    this.regdate = regdate;
  }

  public Integer getLastloginip() { 
    return(this.lastloginip);
  }

  public void setLastloginip(Integer lastloginip) { 
    this.lastloginip = lastloginip;
  }

  public Integer getLastlogintime() { 
    return(this.lastlogintime);
  }

  public void setLastlogintime(Integer lastlogintime) { 
    this.lastlogintime = lastlogintime;
  }

  public String getSalt() { 
    return(this.salt);
  }

  public void setSalt(String salt) { 
    this.salt = salt;
  }

  public String getSecques() { 
    return(this.secques);
  }

  public void setSecques(String secques) { 
    this.secques = secques;
  }
}
