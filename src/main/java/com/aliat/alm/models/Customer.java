package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
public class Customer {
	

	@Id
	@Column(name = "CUSTOMER_ID", nullable = false)
	private String customerId;
		
	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobile;
	
	@Column(name = "CUSTOMER_ACRONYMS")
	private String customerAcronyms;
	
	
	@Column(name = "REF_CUST_ID")
	private String refCustId;

	
	@Column(name = "TEL_NUMBER")
	private String telNumber;

	
	@Column(name = "CUSTOMER_TYPE")
	private String customerType;

	
	@Column(name = "CUSTOMER_CATEGORY")
	private String customerCategory;

	
	@Column(name = "LONGITUDE")
	private String longitude;

	
	@Column(name = "LATITUDE")
	private String lattitude;

	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "LOCATION_ID")
	private String locationId;

	
	@Column(name = "CITY")
	private String city;

	
	@Column(name = "REGION_ID")
	private String regionId;

	
	@Column(name = "REGION_NAME")
	private String regionName;

	
	@Column(name = "AREA_ID")
	private String areaId;

	
	@Column(name = "AREA_NAME")
	private String areaName;

	
	@Column(name = "POSTAL_ADDRESS")
	private String postalAddress;

	
	@Column(name = "NATIONALITY")
	private String nationality;
	
	
	@Column(name = "EMAIL")
	private String email;
	
	
	@Column(name = "WEBSITE")
	private String website;

	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	

	@Column(name = "STATUS")
	private String status;

	

	

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}



	
	public Customer(String customerId, String customerName, String mobile, String customerAcronyms, String refCustId,
			String telNumber, String customerType, String customerCategory, String longitude, String lattitude,
			String address, String locationId, String city, String regionId, String regionName, String areaId,
			String areaName, String postalAddress, String nationality, String email, String website,
			Timestamp createdDate, Timestamp lastModifiedDate, String status) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.mobile = mobile;
		this.customerAcronyms = customerAcronyms;
		this.refCustId = refCustId;
		this.telNumber = telNumber;
		this.customerType = customerType;
		this.customerCategory = customerCategory;
		this.longitude = longitude;
		this.lattitude = lattitude;
		this.address = address;
		this.locationId = locationId;
		this.city = city;
		this.regionId = regionId;
		this.regionName = regionName;
		this.areaId = areaId;
		this.areaName = areaName;
		this.postalAddress = postalAddress;
		this.nationality = nationality;
		this.email = email;
		this.website = website;
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





	public String getCustomerName() {
		return customerName;
	}





	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}





	public String getMobile() {
		return mobile;
	}





	public void setMobile(String mobile) {
		this.mobile = mobile;
	}





	public String getCustomerAcronyms() {
		return customerAcronyms;
	}





	public void setCustomerAcronyms(String customerAcronyms) {
		this.customerAcronyms = customerAcronyms;
	}





	public String getRefCustId() {
		return refCustId;
	}





	public void setRefCustId(String refCustId) {
		this.refCustId = refCustId;
	}





	public String getTelNumber() {
		return telNumber;
	}





	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}





	public String getCustomerType() {
		return customerType;
	}





	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}





	public String getCustomerCategory() {
		return customerCategory;
	}





	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}





	public String getLongitude() {
		return longitude;
	}





	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}





	public String getLattitude() {
		return lattitude;
	}





	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}





	public String getAddress() {
		return address;
	}





	public void setAddress(String address) {
		this.address = address;
	}





	public String getLocationId() {
		return locationId;
	}




	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}




	public String getCity() {
		return city;
	}





	public void setCity(String city) {
		this.city = city;
	}





	public String getRegionId() {
		return regionId;
	}





	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}





	public String getRegionName() {
		return regionName;
	}





	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}





	public String getAreaId() {
		return areaId;
	}





	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}





	public String getAreaName() {
		return areaName;
	}





	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}





	public String getPostalAddress() {
		return postalAddress;
	}





	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}





	public String getNationality() {
		return nationality;
	}





	public void setNationality(String nationality) {
		this.nationality = nationality;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public String getWebsite() {
		return website;
	}





	public void setWebsite(String website) {
		this.website = website;
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





	public String getStatus() {
		return status;
	}





	public void setStatus(String status) {
		this.status = status;
	}



	




	






	

	
	
}
