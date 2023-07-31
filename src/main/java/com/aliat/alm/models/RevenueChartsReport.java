package com.aliat.alm.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PREPAID_PAYG_REVENUE" , schema="almrpt")
public class RevenueChartsReport {
	
	@Column(name = "VOICE_REVENUE")
	private BigDecimal voiceRevenue;
	
	@Column(name = "SMS_REVENUE")
	private BigDecimal smsRevenue;
	
	@Column(name = "DATA_REVENUE")
	private BigDecimal dataRevneue;
	
	@Column(name = "VAS_REVENUE")
	private BigDecimal vasRevenue;
	
	private BigDecimal totalRevenue;
	
	private String Date;

	public RevenueChartsReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RevenueChartsReport(BigDecimal voiceRevenue, BigDecimal smsRevenue, BigDecimal dataRevneue,
			BigDecimal vasRevenue, BigDecimal totalRevenue, String date) {
		super();
		this.voiceRevenue = voiceRevenue;
		this.smsRevenue = smsRevenue;
		this.dataRevneue = dataRevneue;
		this.vasRevenue = vasRevenue;
		this.totalRevenue = totalRevenue;
		Date = date;
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

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	
	
}
