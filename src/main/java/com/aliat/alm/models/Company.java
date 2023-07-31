package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "COMPANY")
public class Company {
	@Id
	@Column(name = "COMPANY_ID", nullable = false)
	private String companyId;
	
	@Column(name = "DISPLAY_NAME")
	private String displayName;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	

	@Column(name = "LAST_NAME")
	private String lastName;
	
	
	@Column(name = "MOBILE_NUMBER")
	private String mobile;
	
	
	
	@Column(name = "PHYSICAL_LOCATION")
	private String location;
	
	
	
	@Column(name = "STATUS")
	private String clnStatus;

	

	

	



	
	
	


	
	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Company(String clientId, String displayName, String firstName, Timestamp createdDate,
			Timestamp lastModifiedDate, String lastName, String mobile, String location,
			 String clnStatus
		 ) {
		super();
		this.companyId = clientId;
		this.displayName = displayName;
		this.firstName = firstName;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.lastName = lastName;
		this.mobile = mobile;
	
		this.location = location;
	
		this.clnStatus = clnStatus;
	
	
	}



	public String getCompanyId() {
		return companyId;
	}



	public void setCompanyId(String clientId) {
		this.companyId = clientId;
	}



	public String getDisplayName() {
		return displayName;
	}



	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public Timestamp getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}



	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}



	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	


	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	



	public String getStatus() {
		return clnStatus;
	}



	public void setStatus(String clnStatus) {
		this.clnStatus = clnStatus;
	}



	






	



	
	
	




	



	



	



	



	
	
	
	
}
