package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ScheduleReport")
public class ScheduleReport {
	
	@Id
	@Column(name = "RULE_ID", nullable = false)
	private String ruleID;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	
	@Column(name = "LAST_RUN_TIME")
	private String lastRunTime;
	
	public ScheduleReport(String ruleID, 
			String rName, String repTimes, String phoneNumber, String emailFrom, String emailTo, String emailSubject,
			Timestamp createdDate, Timestamp lastModifiedDate, String lastRunTime) {
		super();
		this.ruleID = ruleID;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.lastRunTime = lastRunTime;
		this.rName = rName;
		this.repTimes = repTimes;
		this.phoneNumber = phoneNumber;
		this.emailFrom = emailFrom;
		this.emailTo = emailTo;
		this.emailSubject = emailSubject;
	}

	public ScheduleReport() {
		super();
	}

	@Column(name = "RULE_NAME")
	private String rName;
	
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	
	public ScheduleReport(String ruleID, Timestamp createdDate,
			Timestamp lastModifiedDate, String lastRunTime, 
			String rName, String repTimes, String emailFrom,
			String emailTo, String emailSubject) {
		super();
		this.ruleID = ruleID;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.lastRunTime = lastRunTime;
		this.rName = rName;
		this.repTimes = repTimes;
		this.phoneNumber = " ";
		this.emailFrom = emailFrom;
		this.emailTo = emailTo;
		this.emailSubject = emailSubject;
	}
	
	public ScheduleReport(String ruleID,Timestamp createdDate,
			Timestamp lastModifiedDate, String lastRunTime,
			String rName, String repTimes, String phoneNumber) {
		super();
		this.ruleID = ruleID;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.lastRunTime = lastRunTime;
		this.rName = rName;
		this.repTimes = repTimes;
		this.phoneNumber = phoneNumber;
		this.emailFrom = " ";
		this.emailTo = " ";
		this.emailSubject = " ";
	}

	
	public String getRuleID() {
		return ruleID;
	}

	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}

	public String getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(String lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

	public String getrName() {
		return rName;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public String getRepTimes() {
		return repTimes;
	}

	public void setRepTimes(String repTimes) {
		this.repTimes = repTimes;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	@Column(name = "REPORT_TIMES")
	private String repTimes;
	
	
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name = "EMAIL_FROM")
	private String emailFrom;
	
	@Column(name = "EMAIL_To")
	private String emailTo;
	
	@Column(name = "EMAIL_SUBJECT")
	private String emailSubject;
	

	
	
	
	

}
