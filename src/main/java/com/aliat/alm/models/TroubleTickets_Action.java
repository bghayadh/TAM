package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TROUBLE_TICKETS_ACTIONS")

public class TroubleTickets_Action {
	@Id
	@Column(name = "ACTION_ID", nullable = false)
	private String actionId;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@Column(name = "TICKET_ID", nullable = false)
	private String ticketId;
	
	
	@Column(name = "TEAM", nullable = false)
	private String team;
	
	@Column(name = "EMPLOYEE", nullable = false)
	private String employee;
	
	@Column(name = "ACTION", nullable = false)
	private String action;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@Column(name = "STATUS", nullable = false)
	private String status;

	public TroubleTickets_Action() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setTkacreationDate(Timestamp tkcreationDate) {
		this.creationDate = tkcreationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
		
	
	
	
}
