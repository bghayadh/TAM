package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AREA")
public class AreaListView {
	
	
	@Id
	@Column(name = "AREA_ID", nullable = false)
	private String id;

	private String areaID;

	@Column(name = "AREA_NAME")
	private String name;
	
	@Column(name = "REGION_NAME")
	private String regionName;

	@Column(name = "CREATION_DATE")
	private String creationDate;

	@Column(name = "LAST_MODIFICATION_DATE")
	private String lastModifieddate;

	public AreaListView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AreaListView(String id,String areaID, String name, String regionName, String creationDate, String lastModifieddate) {
		super();
		this.id = id;
		this.areaID=areaID;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.name = name;
		this.regionName = regionName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getAreaID() {
		return areaID;
	}

	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastModifieddate() {
		return lastModifieddate;
	}

	public void setLastModifieddate(String lastModifieddate) {
		this.lastModifieddate = lastModifieddate;
	}

	
}
