package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COMPANY")
public class CompanyListView {
	

	@Id
	@Column(name = "COMPANY_ID", nullable = false)
	private String companyId;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobile;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "CREATED_DATE")
	private String createdDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private String lastModifiedDate;
	

	

	@Column(name = "STATUS")
	private String status;

	@Column(name = "REGISTRATION_STATUS")
	private String regStatus;

	

	public CompanyListView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompanyListView(String clientId, String mobile, String firstName, String lastName, String createdDate,
			String lastModifiedDate,  String status, String regStatus
			) {
		super();
		this.companyId = clientId;
		this.mobile = mobile;
		this.firstName = firstName;
		this.lastName = lastName;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	
		this.status = status;
		this.regStatus = regStatus;
		
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String clientId) {
		this.companyId = clientId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}

	
	
}
