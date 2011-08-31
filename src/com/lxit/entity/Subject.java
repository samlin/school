package com.lxit.entity;

import javax.persistence.Entity;

@Entity
public class Subject extends BaseEntity {
	String incId;
	String issue;
	String solution;

	public String getIncId() {
		return incId;
	}

	public void setIncId(String incId) {
		this.incId = incId;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

}
