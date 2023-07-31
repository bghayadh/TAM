package com.aliat.alm.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLE")
public class UserRole {
	@Id
	@Column(name = "USER_ROLE_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ROLE_ID")
	@SequenceGenerator(name = "USER_ROLE_ID", sequenceName = "USER_ROLE_ID", allocationSize = 1)
	private int id;

	@Column(name = "ROLE_NAME", nullable = false)
	private String rolename;

	@Column(name = "USER_NAME")
	private String username;

	public UserRole() {
	}

	public UserRole(int id, String rolename, String username) {
		super();
		this.id = id;
		this.rolename = rolename;
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
