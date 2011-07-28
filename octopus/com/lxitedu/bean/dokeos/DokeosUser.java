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
package com.lxitedu.bean.dokeos;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DokeosUser {

  private Integer   userID           = null;
  private String    lastname         = null;
  private String    firstname        = null;
  private String    username         = null;
  private String    password         = null;
  private String    auth_Source      = null;
  private String    email            = null;
  private String    officialCode     = null;
  private String    phone            = null;
  private String    pictureUri       = null;
  private Integer   creatorID        = null;
  private String    competences      = null;
  private String    diplomas         = null;
  private String    openarea         = null;
  private String    teach            = null;
  private String    productions      = null;
  private Integer   chatcallUserID   = null;
  private Timestamp chatcallDate     = null;
  private String    chatcallText     = null;
  private String    language         = null;
  private Timestamp registrationDate = null;
  private Timestamp expirationDate   = null;
  private String    openid           = null;
  private String    theme            = null;
  private int       hrDeptID;
  private int       active;
  private int       status;

}
