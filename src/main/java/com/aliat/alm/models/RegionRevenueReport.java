    package com.aliat.alm.models;
	import java.math.BigDecimal;
    import java.sql.Timestamp;

	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.Id;
	import javax.persistence.Table;


	@Entity
	@Table(name = "PREPAID_PAYG_REVENUE" , schema="almrpt")
	public class RegionRevenueReport {

		
		@Id
		@Column(name = "REGION_NAME")
		private String regionName;
		
		@Column(name = "REGION_ID")
		private String regionID;
		
		@Column(name = "REVENUE_DATE")
		private String startDate;
		
		private String endDate;
		
		@Column(name = "VOICE_REVENUE")
		private BigDecimal voiceRevenue;
		
		@Column(name = "SMS_REVENUE")
		private BigDecimal smsRevenue;
		
		@Column(name = "DATA_REVENUE")
		private BigDecimal dataRevenue;
		
		@Column(name = "VAS_REVENUE")
		private BigDecimal vasRevenue;
		
	
		
		public RegionRevenueReport() {
			super();
			// TODO Auto-generated constructor stub
		}


	public RegionRevenueReport(String regionName, String regionID, String startDate, String endDate, BigDecimal voiceRevenue,
			BigDecimal smsRevenue, BigDecimal dataRevenue, BigDecimal vasRevenue, String combination_technology) {
		super();
		this.regionName = regionName;
		this.regionID = regionID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.voiceRevenue = voiceRevenue;
		this.smsRevenue = smsRevenue;
		this.dataRevenue = dataRevenue;
		this.vasRevenue = vasRevenue;
	}


	public String getRegionName() {
		return regionName;
	}


	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}


	public String getRegionID() {
		return regionID;
	}


	public void setRegionID(String regionID) {
		this.regionID = regionID;
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


	public BigDecimal getdataRevenue() {
		return dataRevenue;
	}


	public void setdataRevenue(BigDecimal dataRevenue) {
		this.dataRevenue = dataRevenue;
	}


	public BigDecimal getVasRevenue() {
		return vasRevenue;
	}


	public void setVasRevenue(BigDecimal vasRevenue) {
		this.vasRevenue = vasRevenue;
	}





	
	
}
