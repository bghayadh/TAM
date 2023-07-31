package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SIM_CARD")
public class SimCard {

	@Id
	@Column(name = "ID" , nullable = false)
	private String simCardID;
	
	@Column(name = "SERIAL_NUM")
	private String serialNum;
	
	@Column(name = "MSISDN")
	private String MSISDN;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifieddate;

	@Column(name = "LOCATION_ID")
	private String locationID;
	
	@Column(name = "LOCATION_NAME")
	private String locationName;
	
	@Column(name = "LOCATION_TYPE")
	private String locationType;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "LONGTITUDE", nullable = false)
	private String longtitude;
	

	@Column(name = "LATITUDE", nullable = false)
	private String latitude;
	

	public SimCard() {
	}

	public SimCard(String simCardID, String serialNum, String MSISDN, String locationID,
			String locationName,String locationType, String status, String longtitude, String latitude,
			Timestamp creationDate, Timestamp lastModifieddate) {
		super();
		this.simCardID = simCardID;
		this.serialNum = serialNum;
		this.MSISDN=MSISDN;
		this.locationID=locationID;
		this.locationName=locationName;
		this.locationType=locationType;
		this.status=status;
		this.longtitude = longtitude;
		this.latitude = latitude;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
	}

	public String getSimCardID() {
		return simCardID;
	}

	public void setSimCardID(String simCardID) {
		this.simCardID = simCardID;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getMSISDN() {
		return MSISDN;
	}

	public void setMSISDN(String MSISDN) {
		this.MSISDN = MSISDN;
	}
	
	public String getLongtitude() {
		return longtitude;
	}


	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocationID() {
		return locationID;
	}

	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	
	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastModifieddate() {
		return lastModifieddate;
	}

	public void setLastModifieddate(Timestamp lastModifieddate) {
		this.lastModifieddate = lastModifieddate;
	}
	
}
