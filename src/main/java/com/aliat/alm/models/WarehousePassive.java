package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WAREHOUSE_PASSIVE")
public class WarehousePassive {

	@Id
	@Column(name = "WARE_ID", nullable = false)
	private String wareID;
	
	@Column(name = "WARE_NAME")
	private String wareName;
	
	@Column(name = "SITE_ID")
	private String siteID;
	
	@Column(name = "SITE_ADDRESS")
	private String siteAddress;
	
	@Column(name = "CIRCLE_ID")
	private String circleID;
	
	@Column(name = "TOWN_NAME")
	private String townName;
	
	@Column(name = "PINCODE")
	private String pinCode;
	
	@Column(name = "SDCA")
	private String sdca;
	
	@Column(name = "DISTRICT_NAME")
	private String districtName;
	
	@Column(name = "EXISTING_NEW_TOWN")
	private String exiNewTown;
	
	@Column(name = "SHOWCASE_NON_SHOWCASE")
	private String showCase;
	
	@Column(name = "SITE_OWNER")
	private String siteOwner;
	
	@Column(name = "TOWER_CO_ID")
	private String towerCoID;
	
	@Column(name = "SITE_MODE")
	private String siteMode;
	
	@Column(name = "TOWER_TYPE")
	private String towerType;
	
	@Column(name = "TOWER_HEIGHT")
	private String towerHeight;
	
	@Column(name = "BUILDING_HEIGHT")
	private String buildheight;
	
	@Column(name = "SHARED_NON_SHARED")
	private String shared;
	
	@Column(name = "TRANSMISSION")
	private String transmission;
	
	@Column(name = "ICR_CATEGORY")
	private String icrCategory;
	
	@Column(name = "HVS_NON_HVS")
	private String hvs;

	@Column(name = "CLUSTER_ID")
	private String clusterID;
	
	@Column(name = "CLUSTER_NAME")
	private String clusterName;
	
	@Column(name = "STATUS")
	private char status;
	
	@Column(name = "NEP_SYNCH")
	private char nepSynch;
	
	@Column(name = "DISCOVERY_DATE")
	private Timestamp discoveryDate;
	
	@Column(name = "LAST_SHOWN_DATE")
	private Timestamp lastShownDate;
	
	@Column(name = "LAST_MODIFY_DATE")
	private Timestamp lastModifyDate;
	
	@Column(name = "HUB_SITE")
	private String hubSite;
	
	@Column(name = "LOCATION_NOTES")
	private String locationNotes;

	@Column(name = "SHELTER")
	private String shelter;

	@Column(name = "SHELTER_ID")
	private String shelterID;
	
	@Column(name = "SHELTER_TYPE")
	private String shelterType;
	
	@Column(name = "SHELTER_VENDOR")
	private String shelterVenfdor;
	
	@Column(name = "SHELTER_WITH_WITHOUT")
	private char shelterWithWithout;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	

	public WarehousePassive() {
		super();
		// TODO Auto-generated constructor stub
	}



	public WarehousePassive(String wareID, String wareName, String siteID, String siteAddress, String circleID,
			String townName, String pinCode, String sdca, String districtName, String exiNewTown, String showCase,
			String siteOwner, String towerCoID, String siteMode, String towerType, String towerHeight,
			String buildheight, String shared, String transmission, String icrCategory, String hvs, String clusterID,
			String clusterName, char status, char nepSynch, Timestamp discoveryDate, Timestamp lastShownDate,
			Timestamp lastModifyDate, String hubSite, String locationNotes, String shelter, String shelterID,
			String shelterType, String shelterVenfdor, char shelterWithWithout, Timestamp creationDate) {
		super();
		this.wareID = wareID;
		this.wareName = wareName;
		this.siteID = siteID;
		this.siteAddress = siteAddress;
		this.circleID = circleID;
		this.townName = townName;
		this.pinCode = pinCode;
		this.sdca = sdca;
		this.districtName = districtName;
		this.exiNewTown = exiNewTown;
		this.showCase = showCase;
		this.siteOwner = siteOwner;
		this.towerCoID = towerCoID;
		this.siteMode = siteMode;
		this.towerType = towerType;
		this.towerHeight = towerHeight;
		this.buildheight = buildheight;
		this.shared = shared;
		this.transmission = transmission;
		this.icrCategory = icrCategory;
		this.hvs = hvs;
		this.clusterID = clusterID;
		this.clusterName = clusterName;
		this.status = status;
		this.nepSynch = nepSynch;
		this.discoveryDate = discoveryDate;
		this.lastShownDate = lastShownDate;
		this.lastModifyDate = lastModifyDate;
		this.hubSite = hubSite;
		this.locationNotes = locationNotes;
		this.shelter = shelter;
		this.shelterID = shelterID;
		this.shelterType = shelterType;
		this.shelterVenfdor = shelterVenfdor;
		this.shelterWithWithout = shelterWithWithout;
		this.creationDate = creationDate;
	}



	public String getWareID() {
		return wareID;
	}



	public void setWareID(String wareID) {
		this.wareID = wareID;
	}



	public String getWareName() {
		return wareName;
	}



	public void setWareName(String wareName) {
		this.wareName = wareName;
	}



	public String getSiteID() {
		return siteID;
	}



	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}



	public String getSiteAddress() {
		return siteAddress;
	}



	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}



	public String getCircleID() {
		return circleID;
	}



	public void setCircleID(String circleID) {
		this.circleID = circleID;
	}



	public String getTownName() {
		return townName;
	}



	public void setTownName(String townName) {
		this.townName = townName;
	}



	public String getPinCode() {
		return pinCode;
	}



	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}



	public String getSdca() {
		return sdca;
	}



	public void setSdca(String sdca) {
		this.sdca = sdca;
	}



	public String getDistrictName() {
		return districtName;
	}



	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}



	public String getExiNewTown() {
		return exiNewTown;
	}



	public void setExiNewTown(String exiNewTown) {
		this.exiNewTown = exiNewTown;
	}



	public String getShowCase() {
		return showCase;
	}



	public void setShowCase(String showCase) {
		this.showCase = showCase;
	}



	public String getSiteOwner() {
		return siteOwner;
	}



	public void setSiteOwner(String siteOwner) {
		this.siteOwner = siteOwner;
	}



	public String getTowerCoID() {
		return towerCoID;
	}



	public void setTowerCoID(String towerCoID) {
		this.towerCoID = towerCoID;
	}



	public String getSiteMode() {
		return siteMode;
	}



	public void setSiteMode(String siteMode) {
		this.siteMode = siteMode;
	}



	public String getTowerType() {
		return towerType;
	}



	public void setTowerType(String towerType) {
		this.towerType = towerType;
	}



	public String getTowerHeight() {
		return towerHeight;
	}



	public void setTowerHeight(String towerHeight) {
		this.towerHeight = towerHeight;
	}



	public String getBuildheight() {
		return buildheight;
	}



	public void setBuildheight(String buildheight) {
		this.buildheight = buildheight;
	}



	public String getShared() {
		return shared;
	}



	public void setShared(String shared) {
		this.shared = shared;
	}



	public String getTransmission() {
		return transmission;
	}



	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}



	public String getIcrCategory() {
		return icrCategory;
	}



	public void setIcrCategory(String icrCategory) {
		this.icrCategory = icrCategory;
	}



	public String getHvs() {
		return hvs;
	}



	public void setHvs(String hvs) {
		this.hvs = hvs;
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



	public char getStatus() {
		return status;
	}



	public void setStatus(char status) {
		this.status = status;
	}



	public char getNepSynch() {
		return nepSynch;
	}



	public void setNepSynch(char nepSynch) {
		this.nepSynch = nepSynch;
	}



	public Timestamp getDiscoveryDate() {
		return discoveryDate;
	}



	public void setDiscoveryDate(Timestamp discoveryDate) {
		this.discoveryDate = discoveryDate;
	}



	public Timestamp getLastShownDate() {
		return lastShownDate;
	}



	public void setLastShownDate(Timestamp lastShownDate) {
		this.lastShownDate = lastShownDate;
	}



	public Timestamp getLastModifyDate() {
		return lastModifyDate;
	}



	public void setLastModifyDate(Timestamp lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}



	public String getHubSite() {
		return hubSite;
	}



	public void setHubSite(String hubSite) {
		this.hubSite = hubSite;
	}



	public String getLocationNotes() {
		return locationNotes;
	}



	public void setLocationNotes(String locationNotes) {
		this.locationNotes = locationNotes;
	}



	public String getShelter() {
		return shelter;
	}



	public void setShelter(String shelter) {
		this.shelter = shelter;
	}



	public String getShelterID() {
		return shelterID;
	}



	public void setShelterID(String shelterID) {
		this.shelterID = shelterID;
	}



	public String getShelterType() {
		return shelterType;
	}



	public void setShelterType(String shelterType) {
		this.shelterType = shelterType;
	}



	public String getShelterVenfdor() {
		return shelterVenfdor;
	}



	public void setShelterVenfdor(String shelterVenfdor) {
		this.shelterVenfdor = shelterVenfdor;
	}



	public char getShelterWithWithout() {
		return shelterWithWithout;
	}



	public void setShelterWithWithout(char shelterWithWithout) {
		this.shelterWithWithout = shelterWithWithout;
	}



	public Timestamp getCreationDate() {
		return creationDate;
	}



	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}


}
