package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WAREHOUSE")
public class WareHouseListView {
	
	@Id
	@Column(name = "WARE_ID", nullable = false)
	private String wareID;
	
	private String warehouseID;
	
	@Column(name = "WARE_NAME")
	private String warehouseName;
	
	@Column(name = "SITE_ID")
	private String wareSiteID;
	
	@Column(name = "LAST_MODIFY_DATE")
	private String wlastModifieddate;
	
	@Column(name = "AREA_NAME")
	private String areaName;
	
	@Column(name = "LONGITUDE")
	private String wareLong;
	
	@Column(name = "LATITUDE")
	private String wareLat;
	
	@Column(name = "CITY")
	private String wareCity;
	
	@Column(name = "REGION_NAME")
	private String regionName;
	
	


	public WareHouseListView(String wareID, String warehouseID, String wlastModifieddate, String warehouseName,
			String wareSiteID, String areaName, String wareLong, String wareLat, String wareCity, String regionName) {
		super();
		this.wareID = wareID;
		this.warehouseID = warehouseID;
		this.wlastModifieddate = wlastModifieddate;
		this.warehouseName = warehouseName;
		this.wareSiteID = wareSiteID;
		this.areaName = areaName;
		this.wareLong = wareLong;
		this.wareLat = wareLat;
		this.wareCity = wareCity;
		this.regionName = regionName;
	}


	public WareHouseListView() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getWareID() {
		return wareID;
	}


	public void setWareID(String wareID) {
		this.wareID = wareID;
	}


	public String getWarehouseID() {
		return warehouseID;
	}


	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}


	public String getWarehouseName() {
		return warehouseName;
	}


	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}


	public String getWareSiteID() {
		return wareSiteID;
	}


	public void setWareSiteID(String wareSiteID) {
		this.wareSiteID = wareSiteID;
	}


	public String getAreaName() {
		return areaName;
	}


	public void setAreaName(String areaName) {
		this.areaName = areaName;
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


	public String getWareCity() {
		return wareCity;
	}


	public void setWareCity(String wareCity) {
		this.wareCity = wareCity;
	}



	public String getWlastModifieddate() {
		return wlastModifieddate;
	}


	public void setWlastModifieddate(String wlastModifieddate) {
		this.wlastModifieddate = wlastModifieddate;
	}


	public String getRegionName() {
		return regionName;
	}


	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	

}
