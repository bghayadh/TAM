package com.aliat.alm.models;

import javax.persistence.Id;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "REPORTS_LIST")
public class ReportsList {
	
	@Id
	@Column(name = "ID" , nullable = false)
	private String ReportID;
	
	@Column(name = "NAME")
	private String ReportName;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@Column(name = "LAST_MODIFICATION_DATE")
	private Timestamp lastModifieddate;

	public ReportsList() {
	}

	public ReportsList(String ReportID, Timestamp creationDate, Timestamp lastModifieddate, String ReportName, String description) {
		super();
		this.ReportID = ReportID;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.ReportName = ReportName;
		this.description=description;

	}
	
	
	public  String getReportID() {
		return ReportID;
	}

	public void setReportID(String ReportID) {
		this.ReportID = ReportID;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastModifieddate() {
		return lastModifieddate;
	}

	public void setLastModifieddate(Timestamp lastModifieddate) {
		this.lastModifieddate = lastModifieddate;
	}

	public  String getReportName() {
		return ReportName;
	}

	public void setReportName(String ReportName) {
		this.ReportName = ReportName;
	}
	
	public  String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
