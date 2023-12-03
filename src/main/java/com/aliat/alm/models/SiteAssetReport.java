package com.aliat.alm.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIXED_ASSET_REGISTRY")
public class SiteAssetReport {
	
	private String site;

	@Id
	@Column(name = "WARE_ID")
	private String wareID;
	
	@Column(name = "SITE_ID")
	private String siteID;

	@Column(name = "SITE_NAME")
	private String siteName;
	
	private String longitude;
	private String latitude;
	
	@Column(name = "INITIALCOST")
	private BigDecimal initCost;
	
	@Column(name = "NETCOST")
	private BigDecimal netCost;
	
	@Column(name = "ACCUMULDEPRECAMNT")
	private BigDecimal accuDepr;
				
	
	public SiteAssetReport() {	
	}


	public SiteAssetReport(String site,String wareID,String siteID, String siteName,String longitude, String latitude,BigDecimal initCost, BigDecimal netCost,BigDecimal accuDepr) {
		super();
		this.site=site;
		this.wareID = wareID;
		this.siteID = siteID;
		this.siteName=siteName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.initCost=initCost;
		this.netCost=netCost;
		this.accuDepr=accuDepr;
	}


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
	}


	public String getWareID() {
		return wareID;
	}


	public void setWareID(String wareID) {
		this.wareID = wareID;
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


	public BigDecimal getInitCost() {
		return initCost;
	}


	public void setInitCost(BigDecimal initCost) {
		this.initCost = initCost;
	}


	public BigDecimal getNetCost() {
		return netCost;
	}


	public void setNetCost(BigDecimal netCost) {
		this.netCost = netCost;
	}


	public BigDecimal getAccuDepr() {
		return accuDepr;
	}


	public void setAccuDepr(BigDecimal accuDepr) {
		this.accuDepr = accuDepr;
	}



}
