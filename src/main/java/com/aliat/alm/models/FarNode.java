package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FAR_NODE")
public class FarNode {
	
	@Id
	@Column(name = "NODEFAR_ID", nullable = false)
	private String nodefarId;
	
	@Column(name = "NODE_ID", nullable = false)
	private String nodeID;
	
	@Column(name = "NODE_NAME", nullable = false)
	private String nodeName;
	
	@Column(name = "FAR_ID", nullable = false)
	private String farID;
	

	public FarNode () {
		}
	
	public FarNode(String nodeID, String nodeName, String farID , String farnodeId , String nodefarId) {
		super();
	
		this.nodefarId = nodefarId;
		this.nodeID = nodeID;
		this.nodeName = nodeName;
		this.farID = farID;
	}
	
	public String getNodefarId() {
		return nodefarId;
	}
	public void setNodefarId(String nodefarId) {
		this.nodefarId = nodefarId;
	}
	
	
	
	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public String getFarID() {
		return farID;
	}
	public void setFarID(String farID) {
		this.farID = farID;
		
	}
}
	
