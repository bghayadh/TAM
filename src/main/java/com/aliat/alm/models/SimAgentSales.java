package com.aliat.alm.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SIM_AGENT_SALES" , schema="alm")
public class SimAgentSales {


	@Id
	@Column(name = "AGENT_ID")
	private String agentId;
	
	@Column(name = "FULL_NAME")
	private String fullName;
	
	@Column(name = "MSISDN")
	private String msisdn;
	
	@Column(name = "CREATED_DATE")
	private String startDate;
	
	private String endDate;
	
	@Column(name = "REGION_NAME")
	private String regionName;
	
	
	private BigDecimal totalCount;
	private BigDecimal successCount;
	private BigDecimal failedCount;

	
	public SimAgentSales() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SimAgentSales(String agentId, String fullName , String msisdn, String startDate, String endDate, String regionName,
			BigDecimal totalCount,BigDecimal successCount,BigDecimal failedCount) {
		super();
		this.agentId = agentId;
		this.fullName = fullName;
		this.msisdn = msisdn;
		this.startDate = startDate;
		this.endDate = endDate;
		this.regionName = regionName;
		this.totalCount = totalCount;
		this.successCount = successCount;
		this.failedCount = failedCount;

	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public BigDecimal getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(BigDecimal totalCount) {
		this.totalCount = totalCount;
	}
	public BigDecimal getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(BigDecimal successCount) {
		this.successCount = successCount;
	}
	public BigDecimal getFailedCount() {
		return failedCount;
	}
	public void setFailedCount(BigDecimal failedCount) {
		this.failedCount = failedCount;
	}


}
