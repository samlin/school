package com.lxit.entity;

import javax.persistence.Entity;

@Entity
public class Ip extends BaseEntity{
	String netID;    
	String userName;  
	String classes;
	public String getNetID() {
		return netID;
	}
	public void setNetID(String netID) {
		this.netID = netID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}


}
