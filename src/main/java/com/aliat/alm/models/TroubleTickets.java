package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TROUBLE_TICKETS")
public class TroubleTickets {
	@Id
	@Column(name = "TICKET_ID", nullable = false)
	private String ticketId;
	
	@Column(name = "DEPARTMENT")
	private String tkdepartment;
	
	@Column(name = "SUBJECT")
	private String tksubject;
	
	@Column(name = "DESCRIPTION")
	private String tkdescription;
	
	@Column(name = "CLIENT_ID")
	private String tkclientId;
	
	@Column(name = "CLIENT_NAME")
	private String tkclientName;
	
	@Column(name = "SITE_ID")
	private String tksiteId;
	
	@Column(name = "SITE_NAME")
	private String tksiteName;
	
	@Column(name = "SERVICE")
	private String tkservice;
	
	@Column(name = "SERVICE_ISSUE")
	private String tkserviceIssue;
	
	@Column(name = "PRIORITY")
	private String tkpriority;
	
	@Column(name = "STATUS")
	private String tkstatus;
	
	@Column(name = "ISSUE_APPEARED")
	private Timestamp tkissueAppeared;
	
	@Column(name = "CREATION_DATE")
	private Timestamp tkcreationDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp tklastModifiedDate;
	
	@Column(name = "REGIONID")
	private String tkregionId;
	
	@Column(name = "REGIONNAME")
	private String tkregionName;
	
	@Column(name = "REGIONCODE")
	private String tkregionCode;
	
	@Column(name = "RESOLUTION_DATE")
	private String tkresolutionDate;

	@Column(name = "RESOLUTION_ACTION")
	private String tkresolutionAction;
	
	@Column(name = "RESOLUTION_DESCRIPTION")
	private String tkresolutionDescription;
	
	@Column(name = "REASON_FOR_PROBLEM")
	private String reasonForProblemn;
	
	@Column(name = "RCP_DESCRIPTION")
	private String rcaDescription;
	
	
	public TroubleTickets() {
		super();
		// TODO Auto-generated constructor stub
	}


	public TroubleTickets(String ticketId, String tkdepartment, String tksubject, String tkdescription,
			String tkclientId, String tkclientName, String tksiteId, String tksiteName, String tkservice,
			String tkserviceIssue, String tkpriority, String tkstatus, Timestamp tkissueAppeared,
			Timestamp tkcreationDate, Timestamp tklastModifiedDate, String tkregionId, String tkregionName,
			String tkregionCode, String tkresolutionDate, String tkresolutionAction, String tkresolutionDescription,
			String reasonForProblemn, String rcaDescription) {
		super();
		this.ticketId = ticketId;
		this.tkdepartment = tkdepartment;
		this.tksubject = tksubject;
		this.tkdescription = tkdescription;
		this.tkclientId = tkclientId;
		this.tkclientName = tkclientName;
		this.tksiteId = tksiteId;
		this.tksiteName = tksiteName;
		this.tkservice = tkservice;
		this.tkserviceIssue = tkserviceIssue;
		this.tkpriority = tkpriority;
		this.tkstatus = tkstatus;
		this.tkissueAppeared = tkissueAppeared;
		this.tkcreationDate = tkcreationDate;
		this.tklastModifiedDate = tklastModifiedDate;
		this.tkregionId = tkregionId;
		this.tkregionName = tkregionName;
		this.tkregionCode = tkregionCode;
		this.tkresolutionDate = tkresolutionDate;
		this.tkresolutionAction = tkresolutionAction;
		this.tkresolutionDescription = tkresolutionDescription;
		this.reasonForProblemn = reasonForProblemn;
		this.rcaDescription = rcaDescription;
	}


	public String getTicketId() {
		return ticketId;
	}


	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}


	public String getTkdepartment() {
		return tkdepartment;
	}


	public void setTkdepartment(String tkdepartment) {
		this.tkdepartment = tkdepartment;
	}


	public String getTksubject() {
		return tksubject;
	}


	public void setTksubject(String tksubject) {
		this.tksubject = tksubject;
	}


	public String getTkdescription() {
		return tkdescription;
	}


	public void setTkdescription(String tkdescription) {
		this.tkdescription = tkdescription;
	}


	public String getTkclientId() {
		return tkclientId;
	}


	public void setTkclientId(String tkclientId) {
		this.tkclientId = tkclientId;
	}


	public String getTkclientName() {
		return tkclientName;
	}


	public void setTkclientName(String tkclientName) {
		this.tkclientName = tkclientName;
	}


	public String getTksiteId() {
		return tksiteId;
	}


	public void setTksiteId(String tksiteId) {
		this.tksiteId = tksiteId;
	}


	public String getTksiteName() {
		return tksiteName;
	}


	public void setTksiteName(String tksiteName) {
		this.tksiteName = tksiteName;
	}


	public String getTkservice() {
		return tkservice;
	}


	public void setTkservice(String tkservice) {
		this.tkservice = tkservice;
	}


	public String getTkserviceIssue() {
		return tkserviceIssue;
	}


	public void setTkserviceIssue(String tkserviceIssue) {
		this.tkserviceIssue = tkserviceIssue;
	}


	public String getTkpriority() {
		return tkpriority;
	}


	public void setTkpriority(String tkpriority) {
		this.tkpriority = tkpriority;
	}


	public String getTkstatus() {
		return tkstatus;
	}


	public void setTkstatus(String tkstatus) {
		this.tkstatus = tkstatus;
	}


	public Timestamp getTkissueAppeared() {
		return tkissueAppeared;
	}


	public void setTkissueAppeared(Timestamp tkissueAppeared) {
		this.tkissueAppeared = tkissueAppeared;
	}


	public Timestamp getTkcreationDate() {
		return tkcreationDate;
	}


	public void setTkcreationDate(Timestamp tkcreationDate) {
		this.tkcreationDate = tkcreationDate;
	}


	public Timestamp getTklastModifiedDate() {
		return tklastModifiedDate;
	}


	public void setTklastModifiedDate(Timestamp tklastModifiedDate) {
		this.tklastModifiedDate = tklastModifiedDate;
	}


	public String getTkregionId() {
		return tkregionId;
	}


	public void setTkregionId(String tkregionId) {
		this.tkregionId = tkregionId;
	}


	public String getTkregionName() {
		return tkregionName;
	}


	public void setTkregionName(String tkregionName) {
		this.tkregionName = tkregionName;
	}


	public String getTkregionCode() {
		return tkregionCode;
	}


	public void setTkregionCode(String tkregionCode) {
		this.tkregionCode = tkregionCode;
	}


	public String getTkresolutionDate() {
		return tkresolutionDate;
	}


	public void setTkresolutionDate(String tkresolutionDate) {
		this.tkresolutionDate = tkresolutionDate;
	}


	public String getTkresolutionAction() {
		return tkresolutionAction;
	}


	public void setTkresolutionAction(String tkresolutionAction) {
		this.tkresolutionAction = tkresolutionAction;
	}


	public String getTkresolutionDescription() {
		return tkresolutionDescription;
	}


	public void setTkresolutionDescription(String tkresolutionDescription) {
		this.tkresolutionDescription = tkresolutionDescription;
	}


	public String getReasonForProblemn() {
		return reasonForProblemn;
	}


	public void setReasonForProblemn(String reasonForProblemn) {
		this.reasonForProblemn = reasonForProblemn;
	}


	public String getRcaDescription() {
		return rcaDescription;
	}


	public void setRcaDescription(String rcaDescription) {
		this.rcaDescription = rcaDescription;
	}


	
}
