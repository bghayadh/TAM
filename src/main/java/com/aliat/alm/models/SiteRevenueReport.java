    package com.aliat.alm.models;
	import java.math.BigDecimal;
    import java.sql.Timestamp;

	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.Id;
	import javax.persistence.Table;


	@Entity
	@Table(name = "PREPAID_PAYG_REVENUE" , schema="almrpt")
	public class SiteRevenueReport {

		
		@Id
		@Column(name = "SITE_NAME")
		private String siteName;
		
		@Column(name = "AREA_ID")
		private String areaId;
		
		@Column(name = "REVENUE_DATE")
		private String startDate;
		
		private String endDate;
		
		@Column(name = "VOICE_REVENUE")
		private BigDecimal voiceRevenue;
		
		@Column(name = "SMS_REVENUE")
		private BigDecimal smsRevenue;
		
		@Column(name = "DATA_REVENUE")
		private BigDecimal dataRevneue;
		
		@Column(name = "VAS_REVENUE")
		private BigDecimal vasRevenue;
		
		private String combination_technology;
		
		
	/*	@Column(name = "CELL_ID")
		private String cellId;
		
		@Column(name = "CELL_NAME")
		private String cellName;
		
		@Column(name = "GSM_CELL_ID")
		private String gsmCellId;
		
		@Column(name = "NODE_ID")
		private String nodeId;
		
		@Column(name = "SITE_ID")
		private String siteId;
		
		
		
		//@Column(name = "REGION_ID")
		//private String regionId;
		
		
		
		@Column(name = "REGION_NAME")
		private String regionName;
		
		@Column(name = "AREA_NAME")
		private String areaName;
		

		
		
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
		
		*/
		
		public SiteRevenueReport() {
			super();
			// TODO Auto-generated constructor stub
		}


	public SiteRevenueReport(String siteName, String areaId, String startDate, String endDate, BigDecimal voiceRevenue,
			BigDecimal smsRevenue, BigDecimal dataRevneue, BigDecimal vasRevenue, String combination_technology) {
		super();
		this.siteName = siteName;
		this.areaId = areaId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.voiceRevenue = voiceRevenue;
		this.smsRevenue = smsRevenue;
		this.dataRevneue = dataRevneue;
		this.vasRevenue = vasRevenue;
		this.combination_technology = combination_technology;
	}


	public String getSiteName() {
		return siteName;
	}


	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}


	public String getAreaId() {
		return areaId;
	}


	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
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


	public String getCombination_technology() {
		return combination_technology;
	}


	public void setCombination_technology(String combination_technology) {
		this.combination_technology = combination_technology;
	}


	
	
}
