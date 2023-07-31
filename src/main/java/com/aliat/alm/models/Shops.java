package com.aliat.alm.models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SHOPS")
public class Shops {
	@Id
	@Column(name = "SHOPS_ID", nullable = false)
	private String shopId;
	
	@Column(name = "OWNER", nullable = false)
	private String owner;
	

	@Column(name = "LONGTITUDE", nullable = false)
	private String longtitude;
	

	@Column(name = "LATITUDE", nullable = false)
	private String latitude;
	

	@Column(name = "ADDRESS", nullable = false)
	private String address;
	

	@Column(name = "SHOP_NAME", nullable = false)
	private String shopName;
	

	@Column(name = "SALES_MANAGER", nullable = false)
	private String salesman;
	

	@Column(name = "AGENT_ID", nullable = false)
	private String agent;

	@Column(name = "REGION_ID")
	private String region;
	
	@Column(name = "REGION_NAME")
	private String regionName;

	@Column(name = "REGION_CODE")
	private String regionCode;
	
	@Column(name = "REGION_MANAGER")
	private String regionManager;
	
	@Column(name = "STATUS")
	private String shStatus;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;
	

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	
	@Column(name = "AGENT_NAME", nullable = false)
	private String agentName;

	@Column(name = "AREA_ID")
	private String areaId;

	@Column(name = "AREA_NAME")
	private String areaName;

	
	public Shops() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Shops(String shopId, String owner, String longtitude, String latitude, String address, String shopName,
			String salesman, String agent, String region, String regionName, String regionCode, String regionManager,
			String shStatus, Timestamp createDate, Timestamp lastModifiedDate, String agentName, String areaId,
			String areaName) {
		super();
		this.shopId = shopId;
		this.owner = owner;
		this.longtitude = longtitude;
		this.latitude = latitude;
		this.address = address;
		this.shopName = shopName;
		this.salesman = salesman;
		this.agent = agent;
		this.region = region;
		this.regionName = regionName;
		this.regionCode = regionCode;
		this.regionManager = regionManager;
		this.shStatus = shStatus;
		this.createDate = createDate;
		this.lastModifiedDate = lastModifiedDate;
		this.agentName = agentName;
		this.areaId = areaId;
		this.areaName = areaName;
	}


	public String getShopId() {
		return shopId;
	}


	public void setShopId(String shopId) {
		this.shopId = shopId;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
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


	public String getSalesman() {
		return salesman;
	}


	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}


	public String getAgent() {
		return agent;
	}


	public void setAgent(String agent) {
		this.agent = agent;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
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


	public String getRegionManager() {
		return regionManager;
	}


	public void setRegionManager(String regionManager) {
		this.regionManager = regionManager;
	}


	public String getStatus() {
		return shStatus;
	}


	public void setStatus(String shStatus) {
		this.shStatus = shStatus;
	}


	public Timestamp getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}


	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}


	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


	public String getAgentName() {
		return agentName;
	}


	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}


	public String getAreaId() {
		return areaId;
	}


	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}


	public String getAreaName() {
		return areaName;
	}


	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}



	
}
