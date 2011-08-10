/*
 * @(#)Homework.java
 *
 * Copyright (c) 2003 Lxit Ltd
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * Ltd ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with DCIVision Ltd.
 */
package com.lxit.entity;

import javax.persistence.Entity;

/**
  Homework.java

  This class is the serializable bean reflecting business logic uses.

    @author           samlin
    @company          Lxit Limited
    @creation date    06/08/2011
    @version          $Revision: 1.20 $
*/

@Entity
public class Homework extends BaseEntity {

    private static final long serialVersionUID = -6899644323171369766L;
    private String classId = null;
    private String summary = null;
    private String description = null;

    public String getClassId() {
        return (this.classId);
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSummary() {
        return (this.summary);
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return (this.description);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
