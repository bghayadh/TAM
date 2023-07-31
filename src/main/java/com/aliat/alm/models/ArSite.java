package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AR_SITE")
public class ArSite {
	
	@Id
	@Column(name = "ARSITE_ID", nullable = false)
	private String arsiteId;
	
	@Column(name = "SITE_ID", nullable = false)
	private String siteID;
	
	@Column(name = "SITE_NAME", nullable = false)
	private String siteName;
	
	@Column(name = "AR_ID", nullable = false)
	private String arID;
	
	@Column(name = "WARE_ID", nullable = false)
	private String wareID;

	public ArSite () {
		}
	
	public ArSite(String arsiteId, String siteID, String siteName ,String arID,String wareID) {
		super();
	
		this.arsiteId = arsiteId;
		this.siteID = siteID;
		this.siteName = siteName;
		this.arID = arID;
		this.wareID = wareID;

	}
	
	public String getArsiteId() {
		return arsiteId;
	}
	public void setArsiteId(String arsiteId) {
		this.arsiteId = arsiteId;
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
	
	public String getArID() {
		return arID;
	}
	public void setArID(String arID) {
		this.arID = arID;
		
	}
	public String getWareID() {
		return wareID;
	}
	public void setWareID(String wareID) {
		this.wareID = wareID;
		
	}
}
