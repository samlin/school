/*
 * @(#)User.java
 *
 * Copyright (c) 2003 DCIVision Ltd
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of DCIVision
 * Ltd ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with DCIVision Ltd.
 */
package com.itdaoshi.dokeos.bean;

import java.sql.Timestamp;


/**
  User.java

  This class is the serializable bean reflecting business logic uses.

    @author           Samlin Zhang
    @company          Lxit Trainning
    @creation date    18/07/2009
    @version          $Revision: 1.20 $
*/

public class User  {

  public static final String REVISION = "$Revision: 1.20 $";

  private Integer userID = null;
  private String lastname = null;
  private String firstname = null;
  private String username = null;
  private String password = null;
  private String authSource = null;
  private String email = null;
  private String officialCode = null;
  private String phone = null;
  private String pictureUri = null;
  private Integer creatorID = null;
  private String competences = null;
  private String diplomas = null;
  private String openarea = null;
  private String teach = null;
  private String productions = null;
  private Integer chatcallUserID = null;
  private Timestamp chatcallDate = null;
  private String chatcallText = null;
  private String language = null;
  private Timestamp registrationDate = null;
  private Timestamp expirationDate = null;
  private String openid = null;
  private String theme = null;

  private int hrDeptID;

  private int active;

  private int status;

  public User() {
    super();
  }

  public Integer getUserID() { 
    return(this.userID);
  }

  public void setUserID(Integer userID) { 
    this.userID = userID;
  }

  public String getLastname() { 
    return(this.lastname);
  }

  public void setLastname(String lastname) { 
    this.lastname = lastname;
  }

  public String getFirstname() { 
    return(this.firstname);
  }

  public void setFirstname(String firstname) { 
    this.firstname = firstname;
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

  public String getAuthSource() { 
    return(this.authSource);
  }

  public void setAuthSource(String authSource) { 
    this.authSource = authSource;
  }

  public String getEmail() { 
    return(this.email);
  }

  public void setEmail(String email) { 
    this.email = email;
  }

  public int getStatus() { 
    return(this.status);
  }

  public void setStatus(int status) { 
    this.status = status;
  }

  public String getOfficialCode() { 
    return(this.officialCode);
  }

  public void setOfficialCode(String officialCode) { 
    this.officialCode = officialCode;
  }

  public String getPhone() { 
    return(this.phone);
  }

  public void setPhone(String phone) { 
    this.phone = phone;
  }

  public String getPictureUri() { 
    return(this.pictureUri);
  }

  public void setPictureUri(String pictureUri) { 
    this.pictureUri = pictureUri;
  }

  public Integer getCreatorID() { 
    return(this.creatorID);
  }

  public void setCreatorID(Integer creatorID) { 
    this.creatorID = creatorID;
  }

  public String getCompetences() { 
    return(this.competences);
  }

  public void setCompetences(String competences) { 
    this.competences = competences;
  }

  public String getDiplomas() { 
    return(this.diplomas);
  }

  public void setDiplomas(String diplomas) { 
    this.diplomas = diplomas;
  }

  public String getOpenarea() { 
    return(this.openarea);
  }

  public void setOpenarea(String openarea) { 
    this.openarea = openarea;
  }

  public String getTeach() { 
    return(this.teach);
  }

  public void setTeach(String teach) { 
    this.teach = teach;
  }

  public String getProductions() { 
    return(this.productions);
  }

  public void setProductions(String productions) { 
    this.productions = productions;
  }

  public Integer getChatcallUserID() { 
    return(this.chatcallUserID);
  }

  public void setChatcallUserID(Integer chatcallUserID) { 
    this.chatcallUserID = chatcallUserID;
  }

  public Timestamp getChatcallDate() { 
    return(this.chatcallDate);
  }

  public void setChatcallDate(Timestamp chatcallDate) { 
    this.chatcallDate = chatcallDate;
  }

  public String getChatcallText() { 
    return(this.chatcallText);
  }

  public void setChatcallText(String chatcallText) { 
    this.chatcallText = chatcallText;
  }

  public String getLanguage() { 
    return(this.language);
  }

  public void setLanguage(String language) { 
    this.language = language;
  }

  public Timestamp getRegistrationDate() { 
    return(this.registrationDate);
  }

  public void setRegistrationDate(Timestamp registrationDate) { 
    this.registrationDate = registrationDate;
  }

  public Timestamp getExpirationDate() { 
    return(this.expirationDate);
  }

  public void setExpirationDate(Timestamp expirationDate) { 
    this.expirationDate = expirationDate;
  }

  public int getActive() { 
    return(this.active);
  }

  public void setActive(int active) { 
    this.active = active;
  }

  public String getOpenid() { 
    return(this.openid);
  }

  public void setOpenid(String openid) { 
    this.openid = openid;
  }

  public String getTheme() { 
    return(this.theme);
  }

  public void setTheme(String theme) { 
    this.theme = theme;
  }

  public int getHrDeptID() { 
    return(this.hrDeptID);
  }

  public void setHrDeptID(int hrDeptID) { 
    this.hrDeptID = hrDeptID;
  }
}
