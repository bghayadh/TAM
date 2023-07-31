package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NODE_ACTIVE")
public class NodeActive {
	
	@Id
	@Column(name = "NODE_PK", nullable = false)
	private String nodePK;
	
	@Column(name = "UNIQUE_NODE_ID")
	private String uniNodeID;
	
	@Column(name = "NODE_ID")
	private String nodeID;
	
	@Column(name = "NODE_NAME")
	private String nodeName;
	
	@Column(name = "NODE_TYPE")
	private String nodeType;
	
	@Column(name = "DOMAIN")
	private String domain;
	
	@Column(name = "NODE_SOURCE")
	private String nodeSrc;
	
	@Column(name = "NODE_MODEL")
	private String nodeModel;
	
	@Column(name = "TECH_2G")
	private char tech2g;
	
	@Column(name = "TECH_3G")
	private char tech3g;
	
	@Column(name = "TECH_4G")
	private char tech4g;
	
	@Column(name = "TECH_5G")
	private char tech5g;
	
	@Column(name = "SITE_ID")
	private String siteID;
	
	@Column(name = "CIRCLE_ID")
	private String circleID;
	
	@Column(name = "CREATION_DATE")
	private Timestamp createdDate;
	
	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	@Column(name = "FILENAME")
	private String fileName;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "FROM_TRANS_SOURCE")
	private String fromTransSrc;
	
	@Column(name = "FROM_TRANS_ID")
	private String fromTransID;
	
	@Column(name = "TO_TRANS_ID")
	private String toTransID;
	
	@Column(name = "TRANS_TYPE")
	private String transType;
	
	@Column(name = "ACTIVE_RECORD")
	private String activeRec;
	
	@Column(name = "LINE")
	private String line;
	
	@Column(name = "WARE_ID")
	private String wareID;
	
	@Column(name = "VENDOR")
	private String vendor;
	
	@Column(name = "SUPPLIER_ID")
	private String suppID;
	
	@Column(name = "WARE_NAME")
	private String wareName;
	
	@Column(name = "SUPPLIER_NAME")
	private String suppName;
	
	@Column(name = "TO_TRANS_SOURCE")
	private String toTransSrc;

	public NodeActive() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NodeActive(String nodePK, String uniNodeID, String nodeID, String nodeName, String nodeType, String domain,
			String nodeSrc, String nodeModel, char tech2g, char tech3g, char tech4g, char tech5g, String siteID,
			String circleID, Timestamp createdDate, Timestamp updateDate, String fileType, String fileName,
			String status, String fromTransSrc, String fromTransID, String toTransID, String transType,
			String activeRec, String line, String wareID, String vendor, String suppID, String wareName,
			String suppName, String toTransSrc) {
		super();
		this.nodePK = nodePK;
		this.uniNodeID = uniNodeID;
		this.nodeID = nodeID;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.domain = domain;
		this.nodeSrc = nodeSrc;
		this.nodeModel = nodeModel;
		this.tech2g = tech2g;
		this.tech3g = tech3g;
		this.tech4g = tech4g;
		this.tech5g = tech5g;
		this.siteID = siteID;
		this.circleID = circleID;
		this.createdDate = createdDate;
		this.updateDate = updateDate;
		this.fileType = fileType;
		this.fileName = fileName;
		this.status = status;
		this.fromTransSrc = fromTransSrc;
		this.fromTransID = fromTransID;
		this.toTransID = toTransID;
		this.transType = transType;
		this.activeRec = activeRec;
		this.line = line;
		this.wareID = wareID;
		this.vendor = vendor;
		this.suppID = suppID;
		this.wareName = wareName;
		this.suppName = suppName;
		this.toTransSrc = toTransSrc;
	}

	public String getNodePK() {
		return nodePK;
	}

	public void setNodePK(String nodePK) {
		this.nodePK = nodePK;
	}

	public String getUniNodeID() {
		return uniNodeID;
	}

	public void setUniNodeID(String uniNodeID) {
		this.uniNodeID = uniNodeID;
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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getNodeSrc() {
		return nodeSrc;
	}

	public void setNodeSrc(String nodeSrc) {
		this.nodeSrc = nodeSrc;
	}

	public String getNodeModel() {
		return nodeModel;
	}

	public void setNodeModel(String nodeModel) {
		this.nodeModel = nodeModel;
	}

	public char getTech2g() {
		return tech2g;
	}

	public void setTech2g(char tech2g) {
		this.tech2g = tech2g;
	}

	public char getTech3g() {
		return tech3g;
	}

	public void setTech3g(char tech3g) {
		this.tech3g = tech3g;
	}

	public char getTech4g() {
		return tech4g;
	}

	public void setTech4g(char tech4g) {
		this.tech4g = tech4g;
	}

	public char getTech5g() {
		return tech5g;
	}

	public void setTech5g(char tech5g) {
		this.tech5g = tech5g;
	}

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	public String getCircleID() {
		return circleID;
	}

	public void setCircleID(String circleID) {
		this.circleID = circleID;
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

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFromTransSrc() {
		return fromTransSrc;
	}

	public void setFromTransSrc(String fromTransSrc) {
		this.fromTransSrc = fromTransSrc;
	}

	public String getFromTransID() {
		return fromTransID;
	}

	public void setFromTransID(String fromTransID) {
		this.fromTransID = fromTransID;
	}

	public String getToTransID() {
		return toTransID;
	}

	public void setToTransID(String toTransID) {
		this.toTransID = toTransID;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getActiveRec() {
		return activeRec;
	}

	public void setActiveRec(String activeRec) {
		this.activeRec = activeRec;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getWareID() {
		return wareID;
	}

	public void setWareID(String wareID) {
		this.wareID = wareID;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getSuppID() {
		return suppID;
	}

	public void setSuppID(String suppID) {
		this.suppID = suppID;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	public String getSuppName() {
		return suppName;
	}

	public void setSuppName(String suppName) {
		this.suppName = suppName;
	}

	public String getToTransSrc() {
		return toTransSrc;
	}

	public void setToTransSrc(String toTransSrc) {
		this.toTransSrc = toTransSrc;
	}
	   
	

}
