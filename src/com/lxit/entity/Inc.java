package com.lxit.entity;

import javax.persistence.Entity;

@Entity
public class Inc extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Inc [id=" + super.getId() + ", name=" + name + ", address="
				+ address + ", phone=" + phone + "]";
	}

	String name;
	String address;
	String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
