package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TT_DEVICES_CAUSED_PROBLEM")
public class DevicesCausedProblem {
	@Id
	@Column(name = "ID", nullable = false)
	private String Id;
	
	@Column(name = "SITE_ID")
	private String siteId;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "SITE_NAME")
	private String siteName;

	@Column(name = "NODE_ID")
	private String nodeId;
	
	@Column(name = "NODE_NAME")
	private String nodeName;
	
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
	private String ticketId;

	public DevicesCausedProblem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DevicesCausedProblem(String id, String siteId, String type, String siteName, String nodeId, String nodeName,
			String cabinet, String slot, String board, String antenna, String version, String note, String ticketId) {
		super();
		Id = id;
		this.siteId = siteId;
		this.type = type;
		this.siteName = siteName;
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.cabinet = cabinet;
		this.slot = slot;
		this.board = board;
		this.antenna = antenna;
		this.version = version;
		this.note = note;
		this.ticketId = ticketId;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	

}
