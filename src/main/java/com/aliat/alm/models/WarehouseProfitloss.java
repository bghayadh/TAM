package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WAREHOUSE_PROFIT_LOSS")
public class WarehouseProfitloss {

	@Id
	@Column(name = "ID", nullable = false)
	private String id;

	@Column(name = "START_DATE")
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "WARE_ID")
	private String wareID;

	@Column(name = "WARE_NAME")
	private String wareName;

	@Column(name = "AREA_ID")
	private String areaID;
	
	@Column(name = "AREA_NAME")
	private String areaName;
	
	@Column(name = "SITE_ID")
	private String siteID;

	@Column(name = "POPULATION")
	private String population;

	@Column(name = "TECH_2G")
	private char tech2G;

	@Column(name = "TECH_3G")
	private char tech3G;

	@Column(name = "TECH_4G")
	private char tech4G;
	
	@Column(name = "TECH_5G")
	private char tech5G;

	@Column(name = "TRANSMISSION")
	private String transmission;

	@Column(name = "SITE_OWNER")
	private String siteOwner;

	@Column(name = "TOWER_TYPE")
	private String towerType;

	@Column(name = "TOWER_HEIGHT")
	private String towerHeight;

	@Column(name = "UTILIZATION2G")
	private float utilization2G;

	@Column(name = "UTILIZATION3G")
	private float utilization3G;

	@Column(name = "UTILIZATION4G")
	private float utilization4G;
	
	@Column(name = "UTILIZATION5G")
	private float utilization5G;

	@Column(name = "AVAILABILITY_2G")
	private float availability2G;

	@Column(name = "AVAILABILITY_3G")
	private float availability3G;

	@Column(name = "AVAILABILITY_4G")
	private float availability4G;
	
	@Column(name = "AVAILABILITY_5G")
	private float availability5G;

	@Column(name = "GROSS_ADS")
	private float grossADS;

	@Column(name = "COUNT_OF_SSO")
	private float countOfSSO;

	@Column(name = "CUSTOMER_BASE")
	private float custBase;

	@Column(name = "DATA")
	private float data;

	@Column(name = "VOICE_REVENU")
	private float voice;

	@Column(name = "SMS_REVENU")
	private float smsRevenu;

	@Column(name = "GROSS_REVENU")
	private float grossRevenu;

	@Column(name = "DATA_TRAFFIC")
	private float datatraffic;

	@Column(name = "TOTAL_SMS_TRAFFIC_OG")
	private float totalSmsTraOG;

	@Column(name = "TOTAL_VOICE_TRAFFIC_OG")
	private float totalVoiceTraOG;

	@Column(name = "TOTAL_SMS_TRAFFIC_IC")
	private float totalSmsTraIC;

	@Column(name = "TOTAL_VOICE_TRAFFIC_IC")
	private float totalVoiceTraIC;

	@Column(name = "TOTAL_OPEX_MONTHLY")
	private float totalOpexMon;

	@Column(name = "PROFIT_AND_LOSS")
	private float profitAndLoss;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
	
	@Column(name = "LAST_MODIFY_DATE")
	private Timestamp lastModifyDate;
	
	@Column(name = "CLUSTER_ID")
	private String clusterID;
	
	@Column(name = "CLUSTER_NAME")
	private String clusterName;

	public WarehouseProfitloss() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WarehouseProfitloss(String id, Timestamp startDate, Timestamp endDate, String wareID, String wareName,
			String areaID, String areaName, String siteID, String population, char tech2g, char tech3g, char tech4g,
			String transmission, String siteOwner, String towerType, String towerHeight, float utilization2g,
			char tech5g, float utilization3g, float utilization4g, float utilization5g, float availability2g, float availability3g,
			float availability4g, float availability5g, float grossADS, float countOfSSO, float custBase, float data,
			float voice, float smsRevenu, float grossRevenu, float datatraffic, float totalSmsTraOG,
			float totalVoiceTraOG, float totalSmsTraIC, float totalVoiceTraIC, float totalOpexMon, float profitAndLoss,
			Timestamp creationDate, Timestamp lastModifyDate, String clusterID, String clusterName) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.wareID = wareID;
		this.wareName = wareName;
		this.areaID = areaID;
		this.areaName = areaName;
		this.siteID = siteID;
		this.population = population;
		tech2G = tech2g;
		tech3G = tech3g;
		tech4G = tech4g;
		tech5G = tech5g;
		this.transmission = transmission;
		this.siteOwner = siteOwner;
		this.towerType = towerType;
		this.towerHeight = towerHeight;
		utilization2G = utilization2g;
		utilization3G = utilization3g;
		utilization4G = utilization4g;
		utilization5G = utilization5g;
		availability2G = availability2g;
		availability3G = availability3g;
		availability4G = availability4g;
		availability5G = availability5g;
		this.grossADS = grossADS;
		this.countOfSSO = countOfSSO;
		this.custBase = custBase;
		this.data = data;
		this.voice = voice;
		this.smsRevenu = smsRevenu;
		this.grossRevenu = grossRevenu;
		this.datatraffic = datatraffic;
		this.totalSmsTraOG = totalSmsTraOG;
		this.totalVoiceTraOG = totalVoiceTraOG;
		this.totalSmsTraIC = totalSmsTraIC;
		this.totalVoiceTraIC = totalVoiceTraIC;
		this.totalOpexMon = totalOpexMon;
		this.profitAndLoss = profitAndLoss;
		this.creationDate = creationDate;
		this.lastModifyDate = lastModifyDate;
		this.clusterID = clusterID;
		this.clusterName = clusterName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
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

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
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

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getSiteOwner() {
		return siteOwner;
	}

	public void setSiteOwner(String siteOwner) {
		this.siteOwner = siteOwner;
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

	public float getUtilization2G() {
		return utilization2G;
	}

	public void setUtilization2G(float utilization2g) {
		utilization2G = utilization2g;
	}

	public float getUtilization3G() {
		return utilization3G;
	}

	public void setUtilization3G(float utilization3g) {
		utilization3G = utilization3g;
	}

	public float getUtilization4G() {
		return utilization4G;
	}

	public void setUtilization4G(float utilization4g) {
		utilization4G = utilization4g;
	}

	public float getUtilization5G() {
		return utilization5G;
	}

	public void setUtilization5G(float utilization5g) {
		utilization5G = utilization5g;
	}

	public float getAvailability2G() {
		return availability2G;
	}

	public void setAvailability2G(float availability2g) {
		availability2G = availability2g;
	}

	public float getAvailability3G() {
		return availability3G;
	}

	public void setAvailability3G(float availability3g) {
		availability3G = availability3g;
	}

	public float getAvailability4G() {
		return availability4G;
	}

	public void setAvailability4G(float availability4g) {
		availability4G = availability4g;
	}

	public float getAvailability5G() {
		return availability5G;
	}

	public void setAvailability5G(float availability5g) {
		availability5G = availability5g;
	}

	public float getGrossADS() {
		return grossADS;
	}

	public void setGrossADS(float grossADS) {
		this.grossADS = grossADS;
	}

	public float getCountOfSSO() {
		return countOfSSO;
	}

	public void setCountOfSSO(float countOfSSO) {
		this.countOfSSO = countOfSSO;
	}

	public float getCustBase() {
		return custBase;
	}

	public void setCustBase(float custBase) {
		this.custBase = custBase;
	}

	public float getData() {
		return data;
	}

	public void setData(float data) {
		this.data = data;
	}

	public float getVoice() {
		return voice;
	}

	public void setVoice(float voice) {
		this.voice = voice;
	}

	public float getSmsRevenu() {
		return smsRevenu;
	}

	public void setSmsRevenu(float smsRevenu) {
		this.smsRevenu = smsRevenu;
	}

	public float getGrossRevenu() {
		return grossRevenu;
	}

	public void setGrossRevenu(float grossRevenu) {
		this.grossRevenu = grossRevenu;
	}

	public float getDatatraffic() {
		return datatraffic;
	}

	public void setDatatraffic(float datatraffic) {
		this.datatraffic = datatraffic;
	}

	public float getTotalSmsTraOG() {
		return totalSmsTraOG;
	}

	public void setTotalSmsTraOG(float totalSmsTraOG) {
		this.totalSmsTraOG = totalSmsTraOG;
	}

	public float getTotalVoiceTraOG() {
		return totalVoiceTraOG;
	}

	public void setTotalVoiceTraOG(float totalVoiceTraOG) {
		this.totalVoiceTraOG = totalVoiceTraOG;
	}

	public float getTotalSmsTraIC() {
		return totalSmsTraIC;
	}

	public void setTotalSmsTraIC(float totalSmsTraIC) {
		this.totalSmsTraIC = totalSmsTraIC;
	}

	public float getTotalVoiceTraIC() {
		return totalVoiceTraIC;
	}

	public void setTotalVoiceTraIC(float totalVoiceTraIC) {
		this.totalVoiceTraIC = totalVoiceTraIC;
	}

	public float getTotalOpexMon() {
		return totalOpexMon;
	}

	public void setTotalOpexMon(float totalOpexMon) {
		this.totalOpexMon = totalOpexMon;
	}

	public float getProfitAndLoss() {
		return profitAndLoss;
	}

	public void setProfitAndLoss(float profitAndLoss) {
		this.profitAndLoss = profitAndLoss;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Timestamp lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
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

	public char getTech5G() {
		return tech5G;
	}

	public void setTech5G(char tech5g) {
		tech5G = tech5g;
	}
	
}
