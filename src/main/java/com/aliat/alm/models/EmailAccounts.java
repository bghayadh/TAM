package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name ="EMAIL_ACCOUNTS")

public class EmailAccounts {
	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	
	@Column(name = "EMAIL_ADDRESS")
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;

	
	
	public EmailAccounts() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailAccounts(String id, String email, String password) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
