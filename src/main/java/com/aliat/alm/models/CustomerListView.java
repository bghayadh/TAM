package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
public class CustomerListView {
	

	@Id
	@Column(name = "CUSTOMER_ID", nullable = false)
	private String customerId;
	
	private String customerIdd; 
	
	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobile;
	
	@Column(name = "CUSTOMER_ACRONYMS")
	private String customerAcronyms;
	
	@Column(name = "CREATED_DATE")
	private String createdDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private String lastModifiedDate;
	

	@Column(name = "STATUS")
	private String status;

	

	

	public CustomerListView() {
		super();
		// TODO Auto-generated constructor stub
	}



	




	public CustomerListView(String customerId, String customerIdd, String customerName, String mobile,
			String customerAcronyms, String createdDate, String lastModifiedDate, String status) {
		super();
		this.customerId = customerId;
		this.customerIdd = customerIdd;
		this.customerName = customerName;
		this.mobile = mobile;
		this.customerAcronyms = customerAcronyms;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.status = status;
	}








	public String getCustomerId() {
		return customerId;
	}





	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}





	public String getCustomerIdd() {
		return customerIdd;
	}





	public void setCustomerIdd(String customerIdd) {
		this.customerIdd = customerIdd;
	}







	public String getMobile() {
		return mobile;
	}





	public void setMobile(String mobile) {
		this.mobile = mobile;
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



	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public String getCustomerAcronyms() {
		return customerAcronyms;
	}



	public void setCustomerAcronyms(String customerAcronyms) {
		this.customerAcronyms = customerAcronyms;
	}

	

	
	
}
