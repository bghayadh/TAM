package com.aliat.alm.models;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "PROCESS_OPERATION")
public class ProcessOperation {

	@Id
	@Column(name = "ID", nullable = false)
	private String ID;

	@Column(name = "LINK_NAME")
	private String linkName;

	@Column(name = "OPERATION_NAME")
	private String operationName;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "CLASS_NAME")
	private String className;

	@Column(name = "START_DATE_TIME")
	private Timestamp startDateTime;

	@Column(name = "CRON_EXPRESSION")
	private String cronExpression;

	@Column(name = "CREATION_DATE", updatable = false)
	private Timestamp creationDate;

	@Column(name = "LAST_MODIFICATION_DATE")
	private Timestamp lastModificationDate;

	@Column(name = "LAST_EXECUTION_DATE")
	private Timestamp lastExecutionDate;

	public ProcessOperation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProcessOperation(String iD, String linkName, String operationName, String status, String className,
			Timestamp startDateTime, String cronExpression, Timestamp creationDate, Timestamp lastModificationDate,
			Timestamp lastExecutionDate) {
		super();
		ID = iD;
		this.linkName = linkName;
		this.operationName = operationName;
		this.status = status;
		this.className = className;
		this.startDateTime = startDateTime;
		this.cronExpression = cronExpression;
		this.creationDate = creationDate;
		this.lastModificationDate = lastModificationDate;
		this.lastExecutionDate = lastExecutionDate;
	}

	@PrePersist
	protected void onCreate() {
		creationDate = new Timestamp(System.currentTimeMillis());
		lastModificationDate = creationDate;
	}

	@PreUpdate
	protected void onUpdate() {
		lastModificationDate = new Timestamp(System.currentTimeMillis());
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

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Timestamp getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Timestamp startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Timestamp lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public Timestamp getLastExecutionDate() {
		return lastExecutionDate;
	}

	public void setLastExecutionDate(Timestamp lastExecutionDate) {
		this.lastExecutionDate = lastExecutionDate;
	}
}