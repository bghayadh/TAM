package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "CLUSTERS")
public class Cluster {
	
	
	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
		
	@Column(name = "LAST_MODIFICATION_DATE")
	private Timestamp lastModifieddate;
	
	
	@Column(name = "CLUSTER_NAME")
	private String name;
	
	@Column(name = "LONGITUDE")
	private float longitude;
	
	@Column(name = "LATITUDE")
	private float latitude;	

	@Column(name = "AREA_ID")
	private String areaID;
	
	@Column(name = "AREA_NAME")
	private String areaName;
	
	@Column(name = "Status")
	private String clsStatus;

	
	public Cluster() {}

	
	public Cluster(String id, Timestamp creationDate, Timestamp lastModifieddate, String name, float longitude,
			float latitude, String areaID, String areaName,String clsStatus) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.areaID = areaID;
		this.areaName = areaName;
		this.clsStatus=clsStatus;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getStatus() {
		return clsStatus;
	}


	public void setStatus(String clsStatus) {
		this.clsStatus = clsStatus;
	}


	public float getLongitude() {
		return longitude;
	}


	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}


	public float getLatitude() {
		return latitude;
	}


	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}


	public String getAreaID() {
		return areaID;
	}


	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}


	public String getAreaName() {
		return areaName;
	}


	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	

}