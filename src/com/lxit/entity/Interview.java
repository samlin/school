/*
 * @(#)Interview.java
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
  Interview.java

  This class is the serializable bean reflecting business logic uses.

    @author           samlin
    @company          Lxit Limited
    @creation date    16/08/2011
    @version          $Revision: 1.20 $
*/

@Entity
public class Interview extends BaseEntity  {

  private String assignee = null;
  private String issueKey = null;
  private String project = null;
  private String reporter = null;
  private String status = null;
  private String summary = null;
  private String type = null;
  private String description = null;
  private String company = null;
  private String epiboly = null;
  private String epibolyCompany = null;
  private String survey = null;
  private String good = null;
  private String bad = null;
  private String question = null;

  public Interview() {
    super();
  }

  public String getAssignee() { 
    return(this.assignee);
  }

  public void setAssignee(String assignee) { 
    this.assignee = assignee;
  }

  public String getIssueKey() { 
    return(this.issueKey);
  }

  public void setIssueKey(String issueKey) { 
    this.issueKey = issueKey;
  }

  public String getProject() { 
    return(this.project);
  }

  public void setProject(String project) { 
    this.project = project;
  }

  public String getReporter() { 
    return(this.reporter);
  }

  public void setReporter(String reporter) { 
    this.reporter = reporter;
  }

  public String getStatus() { 
    return(this.status);
  }

  public void setStatus(String status) { 
    this.status = status;
  }

  public String getSummary() { 
    return(this.summary);
  }

  public void setSummary(String summary) { 
    this.summary = summary;
  }

  public String getType() { 
    return(this.type);
  }

  public void setType(String type) { 
    this.type = type;
  }

  public String getDescription() { 
    return(this.description);
  }

  public void setDescription(String description) { 
    this.description = description;
  }

  public String getCompany() { 
    return(this.company);
  }

  public void setCompany(String company) { 
    this.company = company;
  }

  public String getEpiboly() { 
    return(this.epiboly);
  }

  public void setEpiboly(String epiboly) { 
    this.epiboly = epiboly;
  }

  public String getEpibolyCompany() { 
    return(this.epibolyCompany);
  }

  public void setEpibolyCompany(String epibolyCompany) { 
    this.epibolyCompany = epibolyCompany;
  }

  public String getSurvey() { 
    return(this.survey);
  }

  public void setSurvey(String survey) { 
    this.survey = survey;
  }

  public String getGood() { 
    return(this.good);
  }

  public void setGood(String good) { 
    this.good = good;
  }

  public String getBad() { 
    return(this.bad);
  }

  public void setBad(String bad) { 
    this.bad = bad;
  }

  public String getQuestion() { 
    return(this.question);
  }

  public void setQuestion(String question) { 
    this.question = question;
  }
}
