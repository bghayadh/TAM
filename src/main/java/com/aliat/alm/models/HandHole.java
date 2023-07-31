package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="HANDHOLE")
public class HandHole {
	
	@Id
	@Column(name = "HANDHOLE_ID", nullable = false)
	private String ID;
	
	@Column(name = "HANDHOLE_NAME")
	private String HName;
	
	@Column(name = "HANDHOLE_MODEL")
	private String HModel;
	
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

	public HandHole() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HandHole(String iD, String hName, String hModel, Number longitude, Number latitude, String city,
			String projectID, String dmName) {
		super();
		ID = iD;
		HName = hName;
		HModel = hModel;
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

	public String getHName() {
		return HName;
	}

	public void setHName(String hName) {
		HName = hName;
	}

	public String getHModel() {
		return HModel;
	}

	public void setHModel(String hModel) {
		HModel = hModel;
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
