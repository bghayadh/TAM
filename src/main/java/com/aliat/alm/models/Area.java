package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AREA")
public class Area {

	@Id
	@Column(name = "AREA_ID", nullable = false)
	private String id;

	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@Column(name = "LAST_MODIFICATION_DATE")
	private Timestamp lastModifieddate;

	@Column(name = "AREA_NAME")
	private String name;
	
	@Column(name = "REGION_ID")
	private String regionID;
	
	@Column(name = "REGION_NAME")
	private String regionName;
	
	
	
	@Column(name = "Status")
	private String arStatus;

	public Area() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Area(String id, Timestamp creationDate, Timestamp lastModifieddate, String name, String regionID,
			String regionName, String arStatus) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.name = name;
		this.regionID = regionID;
		this.regionName = regionName;
		this.arStatus = arStatus;
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

	public String getRegionID() {
		return regionID;
	}

	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	

	public String getStatus() {
		return arStatus;
	}

	public void setStatus(String arStatus) {
		this.arStatus = arStatus;
	}

	
	


}
