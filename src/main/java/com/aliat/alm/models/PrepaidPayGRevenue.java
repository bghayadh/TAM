package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "PREPAID_PAYG_REVENUE" , schema="almrpt")
public class PrepaidPayGRevenue {

	@Id
	@Column(name = "ID", nullable = false)
	private String ID;
	
	@Column(name = "REVENUE_DATE")
	private Timestamp revenueDate;
	
	@Column(name = "CELL_ID")
	private String cellId;
	
	@Column(name = "CELL_NAME")
	private String cellName;
	
	@Column(name = "GSM_CELL_ID")
	private String gsmCellId;
	
	@Column(name = "NODE_ID")
	private String nodeId;
	
	@Column(name = "SITE_ID")
	private String siteId;
	
	@Column(name = "AREA_ID")
	private String areaId;
	@Column(name = "REGION_ID")
	private String regionId;
	@Column(name = "REGION_NAME")
	private String regionName;
	@Column(name = "AREA_NAME")
	private String areaName;
	

	
	@Column(name = "VOICE_REVENUE")
	private float voiceRevenue;
	
	@Column(name = "SMS_REVENUE")
	private float smsRevenue;
	
	@Column(name = "DATA_REVENUE")
	private float dataRevneue;
	
	@Column(name = "VAS_REVENUE")
	private float vasRevenue;
	
	@Column(name = "TECH_2G")
	private char tech2G;

	@Column(name = "TECH_3G")
	private char tech3G;

	@Column(name = "TECH_4G")
	private char tech4G;
	
	@Column(name = "TECH_5G")
	private char tech5G;
	
	@Column(name = "COMMERCIAL_REGION")
	private String commercialRegion;
	
	
	
	@Column(name = "SALES_TERRITORY")
	private String salesterritory;

	
	
	
	@Column(name = "LONGITUDE")
	private String longitude;
	
	@Column(name = "LATITUDE")
	private String Latitude;
	
	@Column(name = "TECHNOLOGY_REGIONS")
	private String technologyRegions;
	  
	@Column(name = "KENYA_PROVINCES")
	private String kenyaProvinces;
	
	
	
	@Column(name = "SITE_CLUSTER")
	private String siteCluster;
	
	@Column(name = "CLUSTER_TYPE")
	private String clusterType;
	
	
	
	@Column(name = "RURAL_SITE_TYPE")
	private String ruralSiteType;
	
	@Column(name = "URBAN_RURAL")
	private String urbanRural;
	
	@Column(name = "METRO_CLUSTERS")
	private String metroClusters;
	

	
	
	@Column(name = "SUBLOCATIONS")
	private String sublocations;
	
	@Column(name = "INDOOR_OUTDOOR")
	private String indoor_outdoor;
	
	@Column(name = "SITE_NAME")
	private String siteName;
	
	public PrepaidPayGRevenue() {
		super();
		// TODO Auto-generated constructor stub
	}



	

	public String getSiteName() {
		return siteName;
	}





	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}





	




	public PrepaidPayGRevenue(String iD, Timestamp revenueDate, String cellId, String cellName, String gsmCellId,
			String nodeId, String siteId, String areaId, String regionId, String regionName, String areaName,
			float voiceRevenue, float smsRevenue, float dataRevneue, float vasRevenue, char tech2g, char tech3g,
			char tech4g, char tech5g, String commercialRegion, String salesterritory, String longitude, String latitude,
			String technologyRegions, String kenyaProvinces, String siteCluster, String clusterType,
			String ruralSiteType, String urbanRural, String metroClusters, String sublocations, String indoor_outdoor,
			String siteName) {
		super();
		ID = iD;
		this.revenueDate = revenueDate;
		this.cellId = cellId;
		this.cellName = cellName;
		this.gsmCellId = gsmCellId;
		this.nodeId = nodeId;
		this.siteId = siteId;
		this.areaId = areaId;
		this.regionId = regionId;
		this.regionName = regionName;
		this.areaName = areaName;
		this.voiceRevenue = voiceRevenue;
		this.smsRevenue = smsRevenue;
		this.dataRevneue = dataRevneue;
		this.vasRevenue = vasRevenue;
		tech2G = tech2g;
		tech3G = tech3g;
		tech4G = tech4g;
		tech5G = tech5g;
		this.commercialRegion = commercialRegion;
		this.salesterritory = salesterritory;
		this.longitude = longitude;
		Latitude = latitude;
		this.technologyRegions = technologyRegions;
		this.kenyaProvinces = kenyaProvinces;
		this.siteCluster = siteCluster;
		this.clusterType = clusterType;
		this.ruralSiteType = ruralSiteType;
		this.urbanRural = urbanRural;
		this.metroClusters = metroClusters;
		this.sublocations = sublocations;
		this.indoor_outdoor = indoor_outdoor;
		this.siteName = siteName;
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





	public String getAreaName() {
		return areaName;
	}





	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}





	public String getID() {
		return ID;
	}



	public void setID(String iD) {
		ID = iD;
	}



	public Timestamp getRevenueDate() {
		return revenueDate;
	}



	public void setRevenueDate(Timestamp revenueDate) {
		this.revenueDate = revenueDate;
	}



	public String getCellId() {
		return cellId;
	}



	public void setCellId(String cellId) {
		this.cellId = cellId;
	}



	public String getCellName() {
		return cellName;
	}



	public void setCellName(String cellName) {
		this.cellName = cellName;
	}



	public String getGsmCellId() {
		return gsmCellId;
	}



	public void setGsmCellId(String gsmCellId) {
		this.gsmCellId = gsmCellId;
	}



	public String getNodeId() {
		return nodeId;
	}



	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}



	public String getSiteId() {
		return siteId;
	}



	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}



	public String getAreaId() {
		return areaId;
	}



	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}



	public float getVoiceRevenue() {
		return voiceRevenue;
	}



	public void setVoiceRevenue(float voiceRevenue) {
		this.voiceRevenue = voiceRevenue;
	}



	public float getSmsRevenue() {
		return smsRevenue;
	}



	public void setSmsRevenue(float smsRevenue) {
		this.smsRevenue = smsRevenue;
	}



	public float getDataRevneue() {
		return dataRevneue;
	}



	public void setDataRevneue(float dataRevneue) {
		this.dataRevneue = dataRevneue;
	}



	public float getVasRevenue() {
		return vasRevenue;
	}



	public void setVasRevenue(float vasRevenue) {
		this.vasRevenue = vasRevenue;
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



	public String getCommercialRegion() {
		return commercialRegion;
	}



	public void setCommercialRegion(String commercialRegion) {
		this.commercialRegion = commercialRegion;
	}



	public String getSalesterritory() {
		return salesterritory;
	}



	public void setSalesterritory(String salesterritory) {
		this.salesterritory = salesterritory;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	public String getLatitude() {
		return Latitude;
	}



	public void setLatitude(String latitude) {
		Latitude = latitude;
	}



	public String getTechnologyRegions() {
		return technologyRegions;
	}



	public void setTechnologyRegions(String technologyRegions) {
		this.technologyRegions = technologyRegions;
	}



	public String getKenyaProvinces() {
		return kenyaProvinces;
	}



	public void setKenyaProvinces(String kenyaProvinces) {
		this.kenyaProvinces = kenyaProvinces;
	}



	public String getSiteCluster() {
		return siteCluster;
	}



	public void setSiteCluster(String siteCluster) {
		this.siteCluster = siteCluster;
	}



	public String getClusterType() {
		return clusterType;
	}



	public void setClusterType(String clusterType) {
		this.clusterType = clusterType;
	}



	public String getRuralSiteType() {
		return ruralSiteType;
	}



	public void setRuralSiteType(String ruralSiteType) {
		this.ruralSiteType = ruralSiteType;
	}



	public String getUrbanRural() {
		return urbanRural;
	}



	public void setUrbanRural(String urbanRural) {
		this.urbanRural = urbanRural;
	}



	public String getMetroClusters() {
		return metroClusters;
	}



	public void setMetroClusters(String metroClusters) {
		this.metroClusters = metroClusters;
	}



	public String getSublocations() {
		return sublocations;
	}



	public void setSublocations(String sublocations) {
		this.sublocations = sublocations;
	}



	public String getIndoor_outdoor() {
		return indoor_outdoor;
	}



	public void setIndoor_outdoor(String indoor_outdoor) {
		this.indoor_outdoor = indoor_outdoor;
	}

	
	
	
}
