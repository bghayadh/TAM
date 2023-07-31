package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Scheduler_Rules")
public class SchedulerRules {
	
	@Id
	@Column(name = "RULE_ID", nullable = false)
	private String RuleID;
	
	@Column(name = "REPORT_ID")
	private String ReportID;
	
	@Column(name = "REPORT_NAME")
	private String ReportName;
	
	@Column(name = "REPORT_TIMES")
	private String ReportTimes;
	
	public SchedulerRules() {
		super();
	}

	@Column(name = "REPORT_WEEKDAYS")
	private String ReportWeekdays;
	
	@Column(name = "REPORT_MONTHDAYS")
	private String ReportMonthdays;
	
	@Column(name = "REPORT_DESTINATION")
	private String ReportDestination;
	
	@Column(name = "STATUS")
	private String Status;
	
	@Column(name = "CREATION_DATE")
	private Timestamp createdDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	
	@Column(name = "LAST_RUN_TIME")
	private Timestamp LastRunTime;
	
	@Column(name = "TRIGGER_TIME")
	private Timestamp TriggerTime;
	
	public SchedulerRules(String ruleID, String reportID, String reportName, String reportTimes, String reportWeekdays,
			String reportMonthdays, String reportDestination, String status, Timestamp createdDate,
			Timestamp lastModifiedDate, Timestamp lastRunTime, Timestamp triggerTime, String ruleName, String phoneNB,
			String emailFrom, String emailTo, String emailSubject) {
		super();
		RuleID = ruleID;
		ReportID = reportID;
		ReportName = reportName;
		ReportTimes = reportTimes;
		ReportWeekdays = reportWeekdays;
		ReportMonthdays = reportMonthdays;
		ReportDestination = reportDestination;
		Status = status;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		LastRunTime = lastRunTime;
		TriggerTime = triggerTime;
		RuleName = ruleName;
		PhoneNB = phoneNB;
		EmailFrom = emailFrom;
		EmailTo = emailTo;
		EmailSubject = emailSubject;
	}

	public String getRuleID() {
		return RuleID;
	}

	public void setRuleID(String ruleID) {
		RuleID = ruleID;
	}

	public String getReportID() {
		return ReportID;
	}

	public void setReportID(String reportID) {
		ReportID = reportID;
	}

	public String getReportName() {
		return ReportName;
	}

	public void setReportName(String reportName) {
		ReportName = reportName;
	}

	public String getReportTimes() {
		return ReportTimes;
	}

	public void setReportTimes(String reportTimes) {
		ReportTimes = reportTimes;
	}

	public String getReportWeekdays() {
		return ReportWeekdays;
	}

	public void setReportWeekdays(String reportWeekdays) {
		ReportWeekdays = reportWeekdays;
	}

	public String getReportMonthdays() {
		return ReportMonthdays;
	}

	public void setReportMonthdays(String reportMonthdays) {
		ReportMonthdays = reportMonthdays;
	}

	public String getReportDestination() {
		return ReportDestination;
	}

	public void setReportDestination(String reportDestination) {
		ReportDestination = reportDestination;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Timestamp getLastRunTime() {
		return LastRunTime;
	}

	public void setLastRunTime(Timestamp lastRunTime) {
		LastRunTime = lastRunTime;
	}

	public Timestamp getTriggerTime() {
		return TriggerTime;
	}

	public void setTriggerTime(Timestamp triggerTime) {
		TriggerTime = triggerTime;
	}

	public String getRuleName() {
		return RuleName;
	}

	public void setRuleName(String ruleName) {
		RuleName = ruleName;
	}

	public String getPhoneNB() {
		return PhoneNB;
	}

	public void setPhoneNB(String phoneNB) {
		PhoneNB = phoneNB;
	}

	public String getEmailFrom() {
		return EmailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		EmailFrom = emailFrom;
	}

	public String getEmailTo() {
		return EmailTo;
	}

	public void setEmailTo(String emailTo) {
		EmailTo = emailTo;
	}

	public String getEmailSubject() {
		return EmailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		EmailSubject = emailSubject;
	}

	@Column(name = "RULE_NAME")
	private String RuleName;
	
	@Column(name = "PHONE_NB")
	private String PhoneNB;

	@Column(name = "EMAIL_FROM")
	private String EmailFrom;
	
	@Column(name = "EMAIL_To")
	private String EmailTo;
	
	@Column(name = "EMAIL_SUBJECT")
	private String EmailSubject;
}

