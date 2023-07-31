package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TT_ASSIGN")
public class Mobile_Trouble_Tickets_Assign {
	
	@Id
	@Column(name = "ASSIGN_ID", nullable = false)
	private String assignId;
	
	@Column(name = "ASSIGN_DATE")
	private Timestamp assign_date;

	@Column(name = "ASSIGNTO" )
	private String assignTo;

	@Column(name = "ASSIGNBY")
	private String assignBy;

	@Column(name = "ACTION")
	private String requiredAction;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;	

	@Column(name = "CREATION_DATE")
	private Timestamp createDate;	

	@Column(name = "TICKET_ID", nullable = false)
	private String ticketId;
	
	public Mobile_Trouble_Tickets_Assign() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Mobile_Trouble_Tickets_Assign(String assignId, Timestamp assign_date, String assignTo, String assignBy,
			String requiredAction, Timestamp lastModifiedDate, Timestamp createDate, String ticketId) {
		super();
		this.assignId = assignId;
		this.assign_date = assign_date;
		this.assignTo = assignTo;
		this.assignBy = assignBy;
		this.requiredAction = requiredAction;
		this.lastModifiedDate = lastModifiedDate;
		this.createDate = createDate;
		this.ticketId = ticketId;
	}

	public String getAssignId() {
		return assignId;
	}

	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}

	public Timestamp getAssign_date() {
		return assign_date;
	}

	public void setAssign_date(Timestamp assign_date) {
		this.assign_date = assign_date;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public String getAssignBy() {
		return assignBy;
	}

	public void setAssignBy(String assignBy) {
		this.assignBy = assignBy;
	}

	public String getRequiredAction() {
		return requiredAction;
	}

	public void setRequiredAction(String requiredAction) {
		this.requiredAction = requiredAction;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	
}
