package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NODE_ACTIVE")
public class NodeListView {
	
	@Id
	@Column(name = "NODE_PK", nullable = false)
	private String nodePK;
	
	@Column(name = "NODE_ID")
	private String nodeID;
	
	@Column(name = "NODE_NAME")
	private String nodeName;
	
	@Column(name = "NODE_TYPE")
	private String nodeType;
	
	@Column(name = "NODE_MODEL")
	private String nodeModel;
	
	@Column(name = "SITE_ID")
	private String siteID;
	
	@Column(name = "CREATION_DATE")
	private Timestamp createdDate;
	
	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;
	
	@Column(name = "WARE_NAME")
	private String wareName;
	


	public NodeListView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NodeListView(String nodePK, String nodeID, String nodeName, String nodeType, 
			String nodeModel,  String siteID,
			 Timestamp createdDate, Timestamp updateDate,  String wareName
			) {
		super();
		this.nodePK = nodePK;
		this.nodeID = nodeID;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.nodeModel = nodeModel;
	    this.siteID = siteID;
		this.createdDate = createdDate;
		this.updateDate = updateDate;
		this.wareName = wareName;
			}

	public String getNodePK() {
		return nodePK;
	}

	public void setNodePK(String nodePK) {
		this.nodePK = nodePK;
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

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}



	public String getNodeModel() {
		return nodeModel;
	}

	public void setNodeModel(String nodeModel) {
		this.nodeModel = nodeModel;
	}

	
	

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	

}
