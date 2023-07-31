package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TT_LINK_FAILURE_PLACES")
public class Mobile_TT_LINK_FAILURE {
	@Id
	@Column(name = "LFP_ID", nullable = false)
	private String linkfailure_id;
	
	@Column(name = "LINK_ID")
	private String link_id;
	
	@Column(name = "LINKNAME")
    private String link_name;
	
	@Column(name = "LONGG")
    private String longitude;
    
	@Column(name = "LATITUDE")
    private String latitude;
    
	@Column(name = "REASON")
    private String reason;
    
	@Column(name = "DESCRIPTION")
    private String description;
    
	@Column(name = "TICKET_ID")
    private String ticketID;
    
    
    
    
    
	public Mobile_TT_LINK_FAILURE() {
		super();
		// TODO Auto-generated constructor stub
	}





	public Mobile_TT_LINK_FAILURE(String linkfailure_id, String link_id, String link_name, String longitude,
			String latitude, String reason, String description, String ticketID) {
		super();
		this.linkfailure_id = linkfailure_id;
		this.link_id = link_id;
		this.link_name = link_name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.reason = reason;
		this.description = description;
		this.ticketID = ticketID;
	}





	public String getLinkfailure_id() {
		return linkfailure_id;
	}





	public void setLinkfailure_id(String linkfailure_id) {
		this.linkfailure_id = linkfailure_id;
	}





	public String getLink_id() {
		return link_id;
	}





	public void setLink_id(String link_id) {
		this.link_id = link_id;
	}





	public String getLink_name() {
		return link_name;
	}





	public void setLink_name(String link_name) {
		this.link_name = link_name;
	}





	public String getLongitude() {
		return longitude;
	}





	public void setLongitude(String longitude) {
		this.longitude = longitude;
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





	public String getTicketID() {
		return ticketID;
	}





	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}


    
	
	
    
}
