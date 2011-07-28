/*******************************************************************************
 * Copyright (c) 2004, 2008 Eugene Kuleshov and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eugene Kuleshov - initial API and implementation
 *     Tasktop Technologies - improvements
 *******************************************************************************/

package com.atlassian.connector.eclipse.internal.jira.core.model;

import java.io.Serializable;

/**
 * TODO merge with IssueLink?
 * 
 * @author Eugene Kuleshov
 */
public class Subtask implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String issueId;

	private final String issueKey;

	public Subtask(String issueId, String issueKey) {
		this.issueId = issueId;
		this.issueKey = issueKey;
	}

	public String getIssueId() {
		return issueId;
	}

	public String getIssueKey() {
		return this.issueKey;
	}

}
