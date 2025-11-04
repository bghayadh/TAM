package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROCESS")
public class Process {

	@Id
	@Column(name = "ID", nullable = false)
	private String ID;
	
	@Column(name = "LINK_NAME")
	private String linkName;
	
	@Column(name = "PROCESS_NAME")
	private String processName;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name = "LAST_RUNNING_TIME")
	private Timestamp lastRunningTime;

	@Column(name = "NEXT_RUNNING_TIME")
	private Timestamp nextRunningTime;
	
	public Process() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Process(String iD, String linkName, String processName, String status, Timestamp lastRunningTime,
			Timestamp nextRunningTime) {
		super();
		ID = iD;
		this.linkName = linkName;
		this.processName = processName;
		this.status = status;
		this.lastRunningTime = lastRunningTime;
		this.nextRunningTime = nextRunningTime;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getLastRunningTime() {
		return lastRunningTime;
	}

	public void setLastRunningTime(Timestamp lastRunningTime) {
		this.lastRunningTime = lastRunningTime;
	}

	public Timestamp getNextRunningTime() {
		return nextRunningTime;
	}

	public void setNextRunningTime(Timestamp nextRunningTime) {
		this.nextRunningTime = nextRunningTime;
	}	
}
