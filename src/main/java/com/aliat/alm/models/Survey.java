package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SURVEY")

public class Survey {
	
	
	@Id
	@Column(name = "SURVEY_ID", nullable = false)
	private String surveyID;
	
	@Column(name = "CUSTOMER_ID")
	private String customerID;
	
	@Column(name = "LONGITUDE")
	private String longitude;
	
	@Column(name = "LATITUDE")
	private String latitude;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@Column(name = "SERVICE_REFERENCE")
	private String serviceReference;
	
	@Column(name = "SERVICE_REQUEST")
	private String serviceRequest;
	
	
	public Survey() {
		super();
	}


	public Survey(String surveyID, String customerID, String longitude, String latitude, Timestamp creationDate,
			String serviceReference, String serviceRequest) {
		super();
		this.surveyID=surveyID;
		this.customerID=customerID;
		this.longitude = longitude;
		this.latitude = latitude;
		this.creationDate = creationDate;
		this.serviceReference = serviceReference;
		this.serviceRequest = serviceRequest;
	}


	public String getSurveyID() {
		return surveyID;
	}


	public void setSurveyID(String surveyID) {
		this.surveyID = surveyID;
	}


	public String getCustomerID() {
		return customerID;
	}


	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public Timestamp getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}


	public String getServiceReference() {
		return serviceReference;
	}


	public void setServiceReference(String serviceReference) {
		this.serviceReference = serviceReference;
	}


	public String getServiceRequest() {
		return serviceRequest;
	}


	public void setServiceRequest(String serviceRequest) {
		this.serviceRequest = serviceRequest;
	}


}

