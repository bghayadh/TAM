package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NODE_ACTIVE")
public class NodePortMappingListView {
	
	@Id
	@Column(name = "NODE_ID", nullable = false)
	private String nodeID;
	
	private String nodeId;

	
	@Column(name = "NODE_NAME")
	private String nodeName;
	
	@Column(name = "NODE_TYPE")
	private String nodeType;
	
	@Column(name = "NODE_MODEL")
	private String nodeModel;
	
	@Column(name = "SITE_ID")
	private String siteID;
	
	@Column(name = "WARE_NAME")
	private String wareName;
	
	@Column(name = "CREATION_DATE")
	private String createdDate;
	
	@Column(name = "UPDATE_DATE")
	private String updateDate;
	
	

	public NodePortMappingListView() {
		super();
		// TODO Auto-generated constructor stub
	}



	public NodePortMappingListView(String nodeID, String nodeId2, String nodeName, String nodeType, String nodeModel,
			String siteID, String wareName, String createdDate, String updateDate) {
		super();
		this.nodeID = nodeID;
		nodeId = nodeId2;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.nodeModel = nodeModel;
		this.siteID = siteID;
		this.wareName = wareName;
		this.createdDate = createdDate;
		this.updateDate = updateDate;
	}



	public String getNodeID() {
		return nodeID;
	}



	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
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

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}	

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}




	
	
	
	
}