package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NOTIFICATION_RECIPIENTS")
public class NotificationRecipients {


	@Id
	@Column(name = "RECIPIENTS_ID", nullable = false)
	private String ID;

	
	@Column(name = "NOTIFICATION_ID", nullable = false)
	private String notificationID;
	
	@Column(name = "JOB_TITLE")
	private String jobTitle;
	
	@Column(name = "EMAIL_TO")
	private String emailTo;
	
	@Column(name = "CC_EMAIL")
	private String ccEmail;
	
		

	@Column(name = "CONDITION")
	private String Condition;

	
	public NotificationRecipients(String iD, String notificationID, String jobTitle, String emailTo, String ccEmail,
			String condition) {
		super();
		ID = iD;
		this.notificationID = notificationID;
		this.jobTitle = jobTitle;
		this.emailTo = emailTo;
		this.ccEmail = ccEmail;
		
		Condition = condition;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(String notificationID) {
		this.notificationID = notificationID;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCcEmail() {
		return ccEmail;
	}

	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
	}

	public String getCondition() {
		return Condition;
	}

	public void setCondition(String condition) {
		Condition = condition;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public NotificationRecipients() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
