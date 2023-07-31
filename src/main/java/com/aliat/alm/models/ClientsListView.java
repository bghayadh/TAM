package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENTS")
public class ClientsListView {
	

	@Id
	@Column(name = "CLIENT_ID", nullable = false)
	private String clientId;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobile;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "CLIENT_ID_NUMBER")
	private String clientIdNumber;
	
	@Column(name = "CREATED_DATE")
	private String createdDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private String lastModifiedDate;
	
	@Column(name = "AGENT_NUMBER")
	private String agentNumber;

	private String agentFullName;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "REGISTRATION_STATUS")
	private String regStatus;

	@Column(name = "TKASH_REGISTRATION_STATUS")
	private String tkashregstatus;

	public ClientsListView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClientsListView(String clientId, String mobile, String firstName, String lastName, String createdDate,
			String lastModifiedDate, String agentNumber, String agentFullName, String status, String regStatus,
			String tkashregstatus,String clientIdNumber) {
		super();
		this.clientId = clientId;
		this.mobile = mobile;
		this.firstName = firstName;
		this.lastName = lastName;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.agentNumber = agentNumber;
		this.agentFullName = agentFullName;
		this.status = status;
		this.regStatus = regStatus;
		this.tkashregstatus = tkashregstatus;
		this.clientIdNumber=clientIdNumber;
	}

	public String getClientIdNumber() {
		return clientIdNumber;
	}

	public void setClientIdNumber(String clientIdNumber) {
		this.clientIdNumber = clientIdNumber;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getAgentNumber() {
		return agentNumber;
	}

	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}

	public String getAgentFullName() {
		return agentFullName;
	}

	public void setAgentFullName(String agentFullName) {
		this.agentFullName = agentFullName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}

	public String getTkashregstatus() {
		return tkashregstatus;
	}

	public void setTkashregstatus(String tkashregstatus) {
		this.tkashregstatus = tkashregstatus;
	}
	
	
}
