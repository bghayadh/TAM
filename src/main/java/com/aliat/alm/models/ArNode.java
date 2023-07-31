package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AR_NODE")
public class ArNode {
	
	@Id
	@Column(name = "NODEAR_ID", nullable = false)
	private String nodearId;
	
	@Column(name = "NODE_ID")
	private String nodeID;
	
	@Column(name = "NODE_NAME")
	private String nodeName;
	
	@Column(name = "AR_ID")
	private String arID;
	

	public ArNode () {
		}
	
	public ArNode(String nodeID, String nodeName, String arID ,String nodearId) {
		super();
	
		this.nodearId = nodearId;
		this.nodeID = nodeID;
		this.nodeName = nodeName;
		this.arID = arID;
	}
	
	public String getNodearId() {
		return nodearId;
	}
	public void setNodearId(String nodearId) {
		this.nodearId = nodearId;
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
	
	public String getArID() {
		return arID;
	}
	public void setArID(String arID) {
		this.arID = arID;
		
	}
}
