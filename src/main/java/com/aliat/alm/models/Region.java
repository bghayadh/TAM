package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REGION")
public class Region {

	@Id
	@Column(name = "REGION_ID", nullable = false)
	private String regionId;

	
	@Column(name = "REGION_NAME")
	private String regionName;

    @Column(name = "REGION_CODE")
	private String regionCode;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Column(name = "LAST_MODIFICATION_DATE")
    private Timestamp lastModifieddate;   
	
	@Column(name = "Status")
	private String rgnStatus;

	

	public Region() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Region(String regionId, String regionName, String regionCode, Timestamp creationDate,
			Timestamp lastModifieddate, String rgnStatus) {
		super();
		this.regionId = regionId;
		this.regionName = regionName;
		this.regionCode = regionCode;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.rgnStatus = rgnStatus;
		
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

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
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

	public String getStatus() {
		return rgnStatus;
	}

	public void setStatus(String rgnStatus) {
		this.rgnStatus = rgnStatus;
	}

	

	
	
}
