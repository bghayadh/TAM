package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TT_LINK_FAILURE_PLACES")
public class LinkFailurePlaces {
	@Id
	@Column(name = "LFP_ID", nullable = false)
	private String lfpId;
	
	@Column(name = "LINK_ID")
	private String linkId;
	
	@Column(name = "LINKNAME")
	private String linkName;
	
	@Column(name = "LONGG")
	private String longg;
	
	@Column(name = "LATITUDE")
	private String latitude;
	
	@Column(name = "REASON")
	private String reason;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TICKET_ID")
	private String ticketId;
	
	
	public LinkFailurePlaces(String lfpId, String linkId, String linkName, String longg, String latitude, String reason,
			String description, String ticketId) {
		super();
		this.lfpId = lfpId;
		this.linkId = linkId;
		this.linkName = linkName;
		this.longg = longg;
		this.latitude = latitude;
		this.reason = reason;
		this.description = description;
		this.ticketId = ticketId;
	}


	public LinkFailurePlaces() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getLfpId() {
		return lfpId;
	}


	public void setLfpId(String lfpId) {
		this.lfpId = lfpId;
	}


	public String getLinkId() {
		return linkId;
	}


	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}


	public String getLinkName() {
		return linkName;
	}


	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}


	public String getLongg() {
		return longg;
	}


	public void setLongg(String longg) {
		this.longg = longg;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getTicketId() {
		return ticketId;
	}


	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	

	
	
}
