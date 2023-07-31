package com.aliat.alm.models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AGENT")
public class AgentsListView {

	@Id
	@Column(name = "AGENT_ID", nullable = false)
	private String agentId;
	
	@Column(name = "FIRST_NAME")
	private String agfirstName;
	
	@Column(name = "LAST_NAME")
	private String aglastName;
	
	@Column(name = "ADDRESS")
	private String agaddress;
	
	@Column(name = "MSISDN")
	private String agMSISDN;
	
	@Column(name = "STATUS")
	private String agStatus;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private String lastModifiedDate;

	public AgentsListView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgentsListView(String agentId, String agfirstName, String aglastName, String lastModifiedDate,String agaddress, String agMSISDN,
			String agStatus) {
		super();
		this.agentId = agentId;
		this.agfirstName = agfirstName;
		this.aglastName = aglastName;
		this.lastModifiedDate=lastModifiedDate;
		this.agaddress = agaddress;
		this.agMSISDN = agMSISDN;
		this.agStatus = agStatus;
	
	}

	
	
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgfirstName() {
		return agfirstName;
	}

	public void setAgfirstName(String agfirstName) {
		this.agfirstName = agfirstName;
	}

	public String getAglastName() {
		return aglastName;
	}

	public void setAglastName(String aglastName) {
		this.aglastName = aglastName;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getAgaddress() {
		return agaddress;
	}

	public void setAgaddress(String agaddress) {
		this.agaddress = agaddress;
	}

	public String getAgMSISDN() {
		return agMSISDN;
	}

	public void setAgMSISDN(String agMSISDN) {
		this.agMSISDN = agMSISDN;
	}

	public String getAgStatus() {
		return agStatus;
	}

	public void setAgStatus(String agStatus) {
		this.agStatus = agStatus;
	}

	
	
}