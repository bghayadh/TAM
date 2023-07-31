package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import oracle.sql.TIMESTAMP;

@Entity
@Table(name = "SHOPS")


public class ShopsAPI {

	@Id
	@Column(name = "SHOPS_ID", nullable = false)
    private String shopid;
	
	@Column(name="OWNER")
	private String owner;
	
	@Column(name="LONGTITUDE")
	private String longitude;
	
	@Column(name="LATITUDE")
	private String latitude;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="SHOP_NAME")
	private String shopName;
	
	@Column(name="AGENT_ID")
	private String agentId;
	
	@Column(name="AGENT_NAME")
	private String agentName;
	
	@Column(name="REGION_ID")
	private String regionId;
	
	@Column(name="REGION_NAME")
	private String regionName;
	
	@Column(name="REGION_CODE")
	private String regionCode;
		
	@Column(name="CREATE_DATE")
	private Timestamp createdDate;
	
	@Column(name="LAST_MODIFIED_DATE")
	private Timestamp lastModified;
	
	@Column(name="SITE_ID")
	private String siteID;
	
	@Column(name="SITE_NAME")
	private String siteName;
	
	@Column(name="WARE_ID")
	private String wareID;
	
	@Column(name="SALES_OUTLET_TYPE")
	private String salesOutletType;
	
	@Column(name="TOUCH_POINT_TYPE")
	private String touchPoint;
	
	@Column(name="TELKOM_CONTACT")
	private String telkomContact;
	
	@Column(name="ALTERNATIVE_CONTACT")
	private String altContact;
	
	@Column(name="AGENT_NUMBER")
	private String agentNumber;

	public ShopsAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShopsAPI(String shopid, String owner, String longitude, String latitude, String address, String shopName,
			String agentId, String agentName, String regionId, String regionName, String regionCode,
			Timestamp createdDate, Timestamp lastModified, String siteID, String siteName, String wareID,
			String salesOutletType, String touchPoint, String telkomContact, String altContact, String agentNumber) {
		super();
		this.shopid = shopid;
		this.owner = owner;
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
		this.shopName = shopName;
		this.agentId = agentId;
		this.agentName = agentName;
		this.regionId = regionId;
		this.regionName = regionName;
		this.regionCode = regionCode;
		this.createdDate = createdDate;
		this.lastModified = lastModified;
		this.siteID = siteID;
		this.siteName = siteName;
		this.wareID = wareID;
		this.salesOutletType = salesOutletType;
		this.touchPoint = touchPoint;
		this.telkomContact = telkomContact;
		this.altContact = altContact;
		this.agentNumber = agentNumber;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
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

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
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

	public String getWareID() {
		return wareID;
	}

	public void setWareID(String wareID) {
		this.wareID = wareID;
	}

	public String getSalesOutletType() {
		return salesOutletType;
	}

	public void setSalesOutletType(String salesOutletType) {
		this.salesOutletType = salesOutletType;
	}

	public String getTouchPoint() {
		return touchPoint;
	}

	public void setTouchPoint(String touchPoint) {
		this.touchPoint = touchPoint;
	}

	public String getTelkomContact() {
		return telkomContact;
	}

	public void setTelkomContact(String telkomContact) {
		this.telkomContact = telkomContact;
	}

	public String getAltContact() {
		return altContact;
	}

	public void setAltContact(String altContact) {
		this.altContact = altContact;
	}

	public String getAgentNumber() {
		return agentNumber;
	}
	
	public void setAgentNumber(String agentNumber) {
		this.agentNumber=agentNumber;
	}

	
}
