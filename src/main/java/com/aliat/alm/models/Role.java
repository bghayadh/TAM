package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE")
public class Role {
	@Id
	@Column(name = "ROLE", nullable = false)
	private String role;

	@Column(name = "ACCESS_TYPE", nullable = true)
	private String accessType;

	@Column(name = "DESCRIPTION", nullable = true)
	private String description;

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(String role, String accessType, String description) {
		super();
		this.role = role;
		this.accessType = accessType;
		this.description = description;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
