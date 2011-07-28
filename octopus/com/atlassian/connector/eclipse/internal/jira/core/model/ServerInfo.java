/*******************************************************************************
 * Copyright (c) 2004, 2009 Brock Janiczak and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Brock Janiczak - initial API and implementation
 *     Tasktop Technologies - improvements
 *******************************************************************************/

package com.atlassian.connector.eclipse.internal.jira.core.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * @author Brock Janiczak
 */
public class ServerInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String baseUrl;

	private Date buildDate;

	private String buildNumber;

	private String edition;

	private String version;

	private String characterEncoding;

	private String webBaseUrl;

	private transient boolean insecureRedirect;

//	private transient Statistics statistics;

	public ServerInfo() {
	}

	public String getBaseUrl() {
		return this.baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Date getBuildDate() {
		return this.buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public String getBuildNumber() {
		return this.buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getEdition() {
		return this.edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

	public String getWebBaseUrl() {
		return webBaseUrl;
	}

	public void setWebBaseUrl(String webBaseUrl) {
		this.webBaseUrl = webBaseUrl;
	}

	public boolean isInsecureRedirect() {
		return insecureRedirect;
	}

	public void setInsecureRedirect(boolean insecureRedirect) {
		this.insecureRedirect = insecureRedirect;
	}

//	public synchronized Statistics getStatistics() {
//		if (statistics == null) {
//			statistics = new Statistics();
//		}
//		return statistics;
//	}

	@Override
	public String toString() {
		return this.baseUrl + " - Jira " + this.edition + " " + this.version + "#" + this.buildNumber + " (" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ DateFormat.getDateInstance(DateFormat.SHORT).format(this.buildDate) + ")"; //$NON-NLS-1$
	}

}
