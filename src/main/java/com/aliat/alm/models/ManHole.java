package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MANHOLE")

public class ManHole {
	
	
	@Id
	@Column(name = "MANHOLE_ID", nullable = false)
	private String ID;
	
	@Column(name = "MANHOLE_NAME")
	private String MHName;
	
	@Column(name = "MANHOLE_MODEL")
	private String MHModel;
	
	@Column(name = "LONGITUDE")
	private Number longitude;
	
	@Column(name = "LATITUDE")
	private Number latitude;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "PROJECT_ID")
	private String projectID;
	
	@Column(name = "DM_NAME")
	private String dmName;
	

	public ManHole() {
		
	}


	public ManHole(String iD, String mHName, String mHModel, Number longitude, Number latitude, String city,
			String projectID, String dmName) {
		super();
		ID = iD;
		MHName = mHName;
		MHModel = mHModel;
		this.longitude = longitude;
		this.latitude = latitude;
		this.city = city;
		this.projectID = projectID;
		this.dmName = dmName;
	}


	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public String getMHName() {
		return MHName;
	}


	public void setMHName(String mHName) {
		MHName = mHName;
	}


	public String getMHModel() {
		return MHModel;
	}


	public void setMHModel(String mHModel) {
		MHModel = mHModel;
	}


	public Number getLongitude() {
		return longitude;
	}


	public void setLongitude(Number longitude) {
		this.longitude = longitude;
	}


	public Number getLatitude() {
		return latitude;
	}


	public void setLatitude(Number latitude) {
		this.latitude = latitude;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getProjectID() {
		return projectID;
	}


	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}


	public String getDmName() {
		return dmName;
	}


	public void setDmName(String dmName) {
		this.dmName = dmName;
	}


}

