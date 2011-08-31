package com.lxit.entity;

import javax.persistence.Entity;

@Entity
public class Groupes extends BaseEntity {
	String className;
	String name;
	String depict;

	@Override
	public String toString() {
		return "{className:'" + className + "', name:'" + name + "', depict:'"
				+ depict + "'}";
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepict() {
		return depict;
	}

	public void setDepict(String depict) {
		this.depict = depict;
	}
}
