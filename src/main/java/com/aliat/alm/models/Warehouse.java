package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WAREHOUSE")
public class Warehouse {
	
	@Id
	@Column(name = "WARE_ID", nullable = false)
	private String wareID;
	
	@Column(name = "CREATION_DATE")
	private Timestamp wcreationDate;
		
	@Column(name = "LAST_MODIFY_DATE")
	private Timestamp wlastModifieddate;
	
	@Column(name = "WARE_NAME")
	private String warehouseName;
	
	@Column(name = "CITY")
	private String wareCity;
	
	@Column(name = "LONGITUDE")
	private String wareLong;
	
	@Column(name = "LATITUDE")
	private String wareLat;
	
	@Column(name = "SITE")
	private char wareSite;
	
	@Column(name = "SITE_ID")
	private String wareSiteID;
	
	@Column(name = "TECH_2G")
	private char tech2G;

	@Column(name = "TECH_3G")
	private char tech3G;

	@Column(name = "TECH_4G")
	private char tech4G;
	
	@Column(name = "TECH_5G")
	private char tech5G;
	
	@Column(name = "AREA_ID")
	private String areaID;
	
	@Column(name = "AREA_NAME")
	private String areaName;	

	@Column(name = "CLUSTER_ID")
	private String clusterID;
	
	@Column(name = "CLUSTER_NAME")
	private String clusterName;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "STATUS")
	private String whStatus;

	@Column(name = "COMBINATION_TECHNOLOGY")
	private String combtech;
	
	
	@Column(name = "REGION_ID")
	private String regionID;
	
	@Column(name = "REGION_NAME")
	private String regionName;
	
	@Column(name = "POPULATION")
	private Integer population;
	
	
	

	public Warehouse() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	


	




	



	public Warehouse(String wareID, Timestamp wcreationDate, Timestamp wlastModifieddate, String warehouseName,
			String wareCity, String wareLong, String wareLat, char wareSite, String wareSiteID, char tech2g, char tech3g,
			char tech4g, char tech5g, String areaID, String areaName, String clusterID, String clusterName,
			String address, String whStatus, String combtech, String regionID, String regionName, Integer population) {
		super();
		this.wareID = wareID;
		this.wcreationDate = wcreationDate;
		this.wlastModifieddate = wlastModifieddate;
		this.warehouseName = warehouseName;
		this.wareCity = wareCity;
		this.wareLong = wareLong;
		this.wareLat = wareLat;
		this.wareSite = wareSite;
		this.wareSiteID = wareSiteID;
		tech2G = tech2g;
		tech3G = tech3g;
		tech4G = tech4g;
		tech5G = tech5g;
		this.areaID = areaID;
		this.areaName = areaName;
		this.clusterID = clusterID;
		this.clusterName = clusterName;
		this.address = address;
		this.whStatus = whStatus;
		this.combtech = combtech;
		this.regionID = regionID;
		this.regionName = regionName;
		this.population = population;
	}














	public String getWareID() {
		return wareID;
	}


	public void setWareID(String wareID) {
		this.wareID = wareID;
	}


	public Timestamp getWcreationDate() {
		return wcreationDate;
	}


	public void setWcreationDate(Timestamp wcreationDate) {
		this.wcreationDate = wcreationDate;
	}


	public Timestamp getWlastModifieddate() {
		return wlastModifieddate;
	}


	public void setWlastModifieddate(Timestamp wlastModifieddate) {
		this.wlastModifieddate = wlastModifieddate;
	}


	public String getWarehouseName() {
		return warehouseName;
	}


	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}


	public String getWareCity() {
		return wareCity;
	}


	public void setWareCity(String wareCity) {
		this.wareCity = wareCity;
	}


	public String getWareLong() {
		return wareLong;
	}


	public void setWareLong(String wareLong) {
		this.wareLong = wareLong;
	}


	public String getWareLat() {
		return wareLat;
	}


	public void setWareLat(String wareLat) {
		this.wareLat = wareLat;
	}


	public char getWareSite() {
		return wareSite;
	}


	public void setWareSite(char wareSite) {
		this.wareSite = wareSite;
	}


	public String getWareSiteID() {
		return wareSiteID;
	}


	public void setWareSiteID(String wareSiteID) {
		this.wareSiteID = wareSiteID;
	}


	public char getTech2G() {
		return tech2G;
	}


	public void setTech2G(char tech2g) {
		tech2G = tech2g;
	}


	public char getTech3G() {
		return tech3G;
	}


	public void setTech3G(char tech3g) {
		tech3G = tech3g;
	}


	public char getTech4G() {
		return tech4G;
	}


	public void setTech4G(char tech4g) {
		tech4G = tech4g;
	}


	public char getTech5G() {
		return tech5G;
	}


	public void setTech5G(char tech5g) {
		tech5G = tech5g;
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


	public String getClusterID() {
		return clusterID;
	}


	public void setClusterID(String clusterID) {
		this.clusterID = clusterID;
	}


	public String getClusterName() {
		return clusterName;
	}


	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getWhStatus() {
		return whStatus;
	}


	public void setWhStatus(String whStatus) {
		this.whStatus = whStatus;
	}
	public String getCombtech() {
		return combtech;
	}





	public void setCombtech(String combtech) {
		this.combtech = combtech;
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














	public Integer getPopulation() {
		return population;
	}














	public void setPopulation(Integer population) {
		this.population = population;
	}




}
