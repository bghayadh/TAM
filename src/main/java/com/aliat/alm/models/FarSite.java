package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FAR_SITE")
public class FarSite {
	
	@Id
	@Column(name = "FARSITE_ID", nullable = false)
	private String farsiteId;
	
	@Column(name = "SITE_ID", nullable = false)
	private String siteID;
	
	@Column(name = "SITE_NAME", nullable = false)
	private String siteName;
	
	@Column(name = "FAR_ID", nullable = false)
	private String farID;
	
	@Column(name = "WARE_ID", nullable = false)
	private String wareID;

	public FarSite () {
		}
	
	public FarSite(String farsiteId, String siteID, String siteName ,String farID,String wareID) {
		super();
	
		this.farsiteId = farsiteId;
		this.siteID = siteID;
		this.siteName = siteName;
		this.farID = farID;
		this.wareID = wareID;

	}
	
	public String getFarsiteId() {
		return farsiteId;
	}
	public void setFarsiteId(String farsiteId) {
		this.farsiteId = farsiteId;
	}
		
	
	public String getSiteID() {
		return siteID;
	}
	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	public String getFarID() {
		return farID;
	}
	public void setFarID(String farID) {
		this.farID = farID;
		
	}
	public String getWareID() {
		return wareID;
	}
	public void setWareID(String wareID) {
		this.wareID = wareID;
		
	}
}
