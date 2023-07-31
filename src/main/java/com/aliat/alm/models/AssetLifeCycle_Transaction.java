package com.aliat.alm.models;

public class AssetLifeCycle_Transaction {
	
	private String ID;
	private String creationDate;
	private String transID;
	private String transSource;
	private String transType;
	private String activeRecord;
	private String siteId;
	private String siteName;
	private String nodeId;
	private String nodeName;
	private String nodeSNo;
	private String nodeType;
	private String nodeTransType;
	private String cabinetPosition;
	private String cabinetModel;
	private String cabinetSNo;
	private String cabinetTransType;
	private String BoardPosition;
	private String BoardModel;
	private String BoardSNo;
	private String BoardTransType;
	private String antennaId;
	private String antennaModel;
	private String antennaSNo;
	private String antennaTransType;
	private String cellId;
	private String cellName;
	private String cellTransType;
	private String hostVersion;
	private String hostVersionTrans;
	
	
	public AssetLifeCycle_Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AssetLifeCycle_Transaction(String iD, String creationDate, String transID, String transSource,
			String transType, String activeRecord, String siteId, String siteName, String nodeId, String nodeName,
			String nodeSNo, String nodeType, String nodeTransType, String cabinetPosition, String cabinetModel,
			String cabinetSNo, String cabinetTransType, String boardPosition, String boardModel, String boardSNo,
			String boardTransType, String antennaId, String antennaModel, String antennaSNo, String antennaTransType,
			String cellId, String cellName, String cellTransType, String hostVersion, String hostVersionTrans) {
		super();
		ID = iD;
		this.creationDate = creationDate;
		this.transID = transID;
		this.transSource = transSource;
		this.transType = transType;
		this.activeRecord = activeRecord;
		this.siteId = siteId;
		this.siteName = siteName;
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeSNo = nodeSNo;
		this.nodeType = nodeType;
		this.nodeTransType = nodeTransType;
		this.cabinetPosition = cabinetPosition;
		this.cabinetModel = cabinetModel;
		this.cabinetSNo = cabinetSNo;
		this.cabinetTransType = cabinetTransType;
		BoardPosition = boardPosition;
		BoardModel = boardModel;
		BoardSNo = boardSNo;
		BoardTransType = boardTransType;
		this.antennaId = antennaId;
		this.antennaModel = antennaModel;
		this.antennaSNo = antennaSNo;
		this.antennaTransType = antennaTransType;
		this.cellId = cellId;
		this.cellName = cellName;
		this.cellTransType = cellTransType;
		this.hostVersion = hostVersion;
		this.hostVersionTrans = hostVersionTrans;
	}


	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public String getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}


	public String getTransID() {
		return transID;
	}


	public void setTransID(String transID) {
		this.transID = transID;
	}


	public String getTransSource() {
		return transSource;
	}


	public void setTransSource(String transSource) {
		this.transSource = transSource;
	}


	public String getTransType() {
		return transType;
	}


	public void setTransType(String transType) {
		this.transType = transType;
	}


	public String getActiveRecord() {
		return activeRecord;
	}


	public void setActiveRecord(String activeRecord) {
		this.activeRecord = activeRecord;
	}


	public String getSiteId() {
		return siteId;
	}


	public void setSiteId(String siteId) {
		this.siteId = siteId;
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


	public String getNodeSNo() {
		return nodeSNo;
	}


	public void setNodeSNo(String nodeSNo) {
		this.nodeSNo = nodeSNo;
	}


	public String getNodeType() {
		return nodeType;
	}


	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}


	public String getNodeTransType() {
		return nodeTransType;
	}


	public void setNodeTransType(String nodeTransType) {
		this.nodeTransType = nodeTransType;
	}


	public String getCabinetPosition() {
		return cabinetPosition;
	}


	public void setCabinetPosition(String cabinetPosition) {
		this.cabinetPosition = cabinetPosition;
	}


	public String getCabinetModel() {
		return cabinetModel;
	}


	public void setCabinetModel(String cabinetModel) {
		this.cabinetModel = cabinetModel;
	}


	public String getCabinetSNo() {
		return cabinetSNo;
	}


	public void setCabinetSNo(String cabinetSNo) {
		this.cabinetSNo = cabinetSNo;
	}


	public String getCabinetTransType() {
		return cabinetTransType;
	}


	public void setCabinetTransType(String cabinetTransType) {
		this.cabinetTransType = cabinetTransType;
	}


	public String getBoardPosition() {
		return BoardPosition;
	}


	public void setBoardPosition(String boardPosition) {
		BoardPosition = boardPosition;
	}


	public String getBoardModel() {
		return BoardModel;
	}


	public void setBoardModel(String boardModel) {
		BoardModel = boardModel;
	}


	public String getBoardSNo() {
		return BoardSNo;
	}


	public void setBoardSNo(String boardSNo) {
		BoardSNo = boardSNo;
	}


	public String getBoardTransType() {
		return BoardTransType;
	}


	public void setBoardTransType(String boardTransType) {
		BoardTransType = boardTransType;
	}


	public String getAntennaId() {
		return antennaId;
	}


	public void setAntennaId(String antennaId) {
		this.antennaId = antennaId;
	}


	public String getAntennaModel() {
		return antennaModel;
	}


	public void setAntennaModel(String antennaModel) {
		this.antennaModel = antennaModel;
	}


	public String getAntennaSNo() {
		return antennaSNo;
	}


	public void setAntennaSNo(String antennaSNo) {
		this.antennaSNo = antennaSNo;
	}


	public String getAntennaTransType() {
		return antennaTransType;
	}


	public void setAntennaTransType(String antennaTransType) {
		this.antennaTransType = antennaTransType;
	}


	public String getCellId() {
		return cellId;
	}


	public void setCellId(String cellId) {
		this.cellId = cellId;
	}


	public String getCellName() {
		return cellName;
	}


	public void setCellName(String cellName) {
		this.cellName = cellName;
	}


	public String getCellTransType() {
		return cellTransType;
	}


	public void setCellTransType(String cellTransType) {
		this.cellTransType = cellTransType;
	}


	public String getHostVersion() {
		return hostVersion;
	}


	public void setHostVersion(String hostVersion) {
		this.hostVersion = hostVersion;
	}


	public String getHostVersionTrans() {
		return hostVersionTrans;
	}


	public void setHostVersionTrans(String hostVersionTrans) {
		this.hostVersionTrans = hostVersionTrans;
	}

	

	
}
