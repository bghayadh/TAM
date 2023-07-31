package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TT_DEVICES_CAUSED_PROBLEM")
public class Mobile_TT_DCP {

	@Id
	@Column(name = "ID", nullable = false)
	private String dcpID;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "SITE_ID")
	private String siteid;
	
	@Column(name = "SITE_NAME")
	private String sitename;
	
	@Column(name = "NODE_ID")
	private String nodeid;
	
	@Column(name = "NODE_NAME")
	private String nodename;
	
	@Column(name = "CABINET")
	private String cabinet;
	
	@Column(name = "SLOT")
	private String slot;
	
	@Column(name = "BOARD")
	private String board;
	
	@Column(name = "ANTENNA")
	private String antenna;
	
	@Column(name = "VERSION")
	private String version;
	
	@Column(name = "NOTE")
	private String note;
	
	@Column(name = "TICKET_ID")
	private String ticketID;

	public Mobile_TT_DCP(String dcpID, String type, String siteid, String sitename, String nodeid, String nodename,
			String cabinet, String slot, String board, String antenna, String version, String note, String ticketID) {
		super();
		this.dcpID = dcpID;
		this.type = type;
		this.siteid = siteid;
		this.sitename = sitename;
		this.nodeid = nodeid;
		this.nodename = nodename;
		this.cabinet = cabinet;
		this.slot = slot;
		this.board = board;
		this.antenna = antenna;
		this.version = version;
		this.note = note;
		this.ticketID = ticketID;
	}

	public Mobile_TT_DCP() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDcpID() {
		return dcpID;
	}

	public void setDcpID(String dcpID) {
		this.dcpID = dcpID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getCabinet() {
		return cabinet;
	}

	public void setCabinet(String cabinet) {
		this.cabinet = cabinet;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getAntenna() {
		return antenna;
	}

	public void setAntenna(String antenna) {
		this.antenna = antenna;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTicketID() {
		return ticketID;
	}

	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}
	
	
	
	
}
