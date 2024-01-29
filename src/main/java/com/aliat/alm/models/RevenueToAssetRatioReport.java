package com.aliat.alm.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name = "PREPAID_PAYG_REVENUE" , schema="almrpt")
	public class RevenueToAssetRatioReport {

		private String site;
		
		@Id
		private String wareID;
		
		@Column(name = "SITE_ID")
		private String siteID;

		@Column(name = "SITE_NAME")
		private String siteName;
		
		@Column(name = "LONGITUDE")
		private String longitude;
		
		@Column(name = "LATITUDE")
		private String latitude;
		
		private BigDecimal revenueToAssetInit;

		private BigDecimal revenueToAssetNet;
		
		private BigDecimal populationToAssetInit;
		
		private BigDecimal populationToAssetNet;
		
		private BigDecimal population;

		@Column(name = "VOICE_REVENUE")
		private BigDecimal voiceRevenue;
		
		@Column(name = "SMS_REVENUE")
		private BigDecimal smsRevenue;
		
		@Column(name = "DATA_REVENUE")
		private BigDecimal dataRevneue;
		
		@Column(name = "VAS_REVENUE")
		private BigDecimal vasRevenue;
		
		private BigDecimal totalRevenue;
		
		//@Column(name = "INITIALCOST")
		private BigDecimal initCost;
		
		//@Column(name = "DEPRECAMNT")
		private BigDecimal depr;
				
		//@Column(name = "NETCOST")
		private BigDecimal netCost;
		
		

		public RevenueToAssetRatioReport() {
			super();
		}

		public RevenueToAssetRatioReport(String site, String wareID, String siteID, String siteName, String longitude,
				String latitude,BigDecimal revenueToAssetInit, BigDecimal revenueToAssetNet,BigDecimal populationToAssetInit,BigDecimal populationToAssetNet,BigDecimal population, BigDecimal voiceRevenue, BigDecimal smsRevenue, BigDecimal dataRevneue,
				BigDecimal vasRevenue, BigDecimal totalRevenue, BigDecimal initCost, BigDecimal depr,BigDecimal netCost) {
			super();
			this.site = site;
			this.wareID = wareID;
			this.siteID = siteID;
			this.siteName = siteName;
			this.longitude = longitude;
			this.latitude = latitude;
			this.revenueToAssetInit = revenueToAssetInit;
			this.revenueToAssetNet = revenueToAssetNet;
			this.populationToAssetInit=populationToAssetInit;
			this.populationToAssetNet = populationToAssetNet;
			this.population = population;
			this.voiceRevenue = voiceRevenue;
			this.smsRevenue = smsRevenue;
			this.dataRevneue = dataRevneue;
			this.vasRevenue = vasRevenue;
			this.totalRevenue = totalRevenue;
			this.initCost = initCost;
			this.depr = depr;
			this.netCost = netCost;
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
		
		
	

		public BigDecimal getPopulation() {
			return population;
		}

		public void setPopulation(BigDecimal population) {
			this.population = population;
		}

		public BigDecimal getVoiceRevenue() {
			return voiceRevenue;
		}

		public void setVoiceRevenue(BigDecimal voiceRevenue) {
			this.voiceRevenue = voiceRevenue;
		}

		public BigDecimal getSmsRevenue() {
			return smsRevenue;
		}

		public void setSmsRevenue(BigDecimal smsRevenue) {
			this.smsRevenue = smsRevenue;
		}

		public BigDecimal getDataRevneue() {
			return dataRevneue;
		}

		public void setDataRevneue(BigDecimal dataRevneue) {
			this.dataRevneue = dataRevneue;
		}

		public BigDecimal getVasRevenue() {
			return vasRevenue;
		}

		public void setVasRevenue(BigDecimal vasRevenue) {
			this.vasRevenue = vasRevenue;
		}

		public BigDecimal getTotalRevenue() {
			return totalRevenue;
		}

		public void setTotalRevenue(BigDecimal totalRevenue) {
			this.totalRevenue = totalRevenue;
		}

		public BigDecimal getInitCost() {
			return initCost;
		}

		public void setInitCost(BigDecimal initCost) {
			this.initCost = initCost;
		}

		public BigDecimal getDepr() {
			return depr;
		}

		public void setDepr(BigDecimal depr) {
			this.depr = depr;
		}

		public BigDecimal getNetCost() {
			return netCost;
		}

		public void setNetCost(BigDecimal netCost) {
			this.netCost = netCost;
		}

		public BigDecimal getRevenueToAssetInit() {
			return revenueToAssetInit;
		}

		public void setRevenueToAssetInit(BigDecimal revenueToAssetInit) {
			this.revenueToAssetInit = revenueToAssetInit;
		}

		public BigDecimal getRevenueToAssetNet() {
			return revenueToAssetNet;
		}

		public void setRevenueToAssetNet(BigDecimal revenueToAssetNet) {
			this.revenueToAssetNet = revenueToAssetNet;
		}

		public BigDecimal getPopulationToAssetInit() {
			return populationToAssetInit;
		}

		public void setPopulationToAssetInit(BigDecimal populationToAssetInit) {
			this.populationToAssetInit = populationToAssetInit;
		}

		public BigDecimal getPopulationToAssetNet() {
			return populationToAssetNet;
		}

		public void setPopulationToAssetNet(BigDecimal populationToAssetNet) {
			this.populationToAssetNet = populationToAssetNet;
		}
				
}
