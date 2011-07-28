/*******************************************************************************
 * Copyright (c) 2004, 2008 Brock Janiczak and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Brock Janiczak - initial API and implementation
 *     Eugene Kuleshov - improvements
 *******************************************************************************/

package com.atlassian.connector.eclipse.internal.jira.core.model;

import java.io.Serializable;

/**
 * @author Brock Janiczak
 * @author Eugene Kuleshov
 */
public class IssueLink implements Serializable {
	private static final long serialVersionUID = 1L;

	public void setIssueId(String issueId) {
    this.issueId = issueId;
  }
  public void setIssueKey(String issueKey) {
    this.issueKey = issueKey;
  }
  public void setLinkTypeId(String linkTypeId) {
    this.linkTypeId = linkTypeId;
  }
  public void setLinkName(String linkName) {
    this.linkName = linkName;
  }
  public void setInwardDescription(String inwardDescription) {
    this.inwardDescription = inwardDescription;
  }
  public void setOutwardDescription(String outwardDescription) {
    this.outwardDescription = outwardDescription;
  }

  private  String issueId;

	private  String issueKey;

	private  String linkTypeId;

	private  String linkName;

	private  String inwardDescription;

	private  String outwardDescription;

	public IssueLink(String issueId, String issueKey, String linkTypeId, //
			String linkName, String inwardDescription, String outwardDescription) {
		this.issueId = issueId;
		this.issueKey = issueKey;
		this.linkTypeId = linkTypeId;
		this.linkName = linkName;
		this.inwardDescription = inwardDescription;
		this.outwardDescription = outwardDescription;
	}
public IssueLink() {
  // TODO Auto-generated constructor stub
}
	public String getIssueId() {
		return issueId;
	}

	public String getIssueKey() {
		return this.issueKey;
	}

	public String getLinkTypeId() {
		return this.linkTypeId;
	}

	public String getLinkName() {
		return linkName;
	}

	public String getInwardDescription() {
		return inwardDescription;
	}

	public String getOutwardDescription() {
		return outwardDescription;
	}

}
