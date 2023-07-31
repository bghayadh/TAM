package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NOTIFICATION")
public class Notification {

	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	@Column(name = "NAME")
	private String notificationName;
	
	
	@Column(name = "SUBJECT")
	private String subject;
	
	
	@Column(name = "MODULE_NAME")
	private String moduleName;
	
	@Column(name = "CHANNEL")
	private String channel;
	
	@Column(name = "SEND_ALERT_ON")
	private String sendAlertOn;
	
	@Column(name = "SENDER")
	private String sender;
	
	@Column(name = "CONDITION")
	private String condition;
	
	@Column(name = "MESSAGE")
	private String message;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	
	@Column(name = "ENABLED")
	private String enabled;
	
	
	public Notification() {
			}
	public Notification(String id,String notificationName,String subject, String moduleName,String channel,String sendAlertOn,String sender,String condition,String message,Timestamp creationDate,Timestamp lastModifiedDate,String enabled) {
		
	
		this.id =  id;
		this.notificationName =  notificationName;
		this.subject =  subject;
		this.moduleName =  moduleName;
		this.channel =  channel;
		this.sendAlertOn =  sendAlertOn;
		this.sender =  sender;
		this.condition =  condition;
		this.message =  message;
		this.creationDate =  creationDate;
		this.lastModifiedDate = lastModifiedDate;
		this.enabled = enabled;
		
		
	}
	public String getNotificationID() {
		return id;
	}
	public void setNotificationID(String id) {
		this.id = id;
	}
	public String getNotificationName() {
		return notificationName;
	}
	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getSendAlertOn() {
		return sendAlertOn;
	}
	public void setSendAlertOn(String sendAlertOn) {
		this.sendAlertOn = sendAlertOn;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	

}
