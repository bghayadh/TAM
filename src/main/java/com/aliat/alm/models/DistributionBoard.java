package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "DISTRIBUTION_BOARD")
public class DistributionBoard {
	@Id
	@Column(name = "DB_ID", nullable = false)
	private String DistributionBoardId;
	
	@Column(name = "DB_NAME")
	private String distributionBoardName;
	
	@Column(name = "TYPE")
	private String distributionBoardType;
	
	
	@Column(name = "DB_LONGITUDE")
	private String distributionBoardLong;
	
	
	@Column(name = "ROW_COUNTING")
	private String rowCounting;
	
	
	@Column(name = "ROW_PER_MODULE")
	private int rowPerModule;
	
	@Column(name = "TOTAL_NUM_MODULE")
	private int totalNumModule;
	
	@Column(name = "SERIAL_NUMB")
	private String distributionBoardSerialNum;
	
	@Column(name = "CONTROLLER_ID")
	private String distributionBoardControllerId;
	
	@Column(name = "CONTROLLER_NAME")
	private String distributionBoardControllerName;
	
	@Column(name = "DB_LATITUDE")
	private String distributionBoardLat;
	
	@Column(name = "CITY")
	private String distributionBoardCity;
	
	@Column(name = "SITE")
	private String distributionBoardSite;
	
	@Column(name = "SITE_NAME")
	private String distributionBoardSiteName;
	
	@Column(name = "WAREHOUSE")
	private String distributionBoardWarehouse;
	
	@Column(name = "MAX_CAPACITY")
	private Float DistributionBoardCapacity;
	
	@Column(name = "NUM_ROWS")
	private Float distributionBoardRowsNum;
	
	@Column(name = "NUM_COLUMNS")
	private Float distributionBoardColsNum;
	
	@Column(name = "FRONT_PORTS_CONNECTED")
	private Float distributionBoardFront;
	
	@Column(name = "BACK_PORTS_CONNECTED")
	private Float distributionBoardBack;
	
	@Column(name = "PROJECT_ID")
	private String distributionBoardProjectId;
	
	@Column(name = "CREATION_DATE")
	private Timestamp BoardCreationDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp BoardLastModifiedDate;
	
	@Column(name = "DB_NETWORK_LEVEL")
	private String dbNetLevel;
	
	@Column(name = "DB_ENGINEER_NAME")
	private String DBEngineerName;
	
	@Column(name = "DB_INSTALLER")
	private String DBInstaller;
	
	@Column(name = "DB_DEPLOYMENT_TYPE")
	private String DBDeploymentType;
	
	@Column(name = "DB_ADAPTOR_PANEL_TYPE")
	private String DBAdaptorPanelType;


	
	public DistributionBoard() {
		super();
		// TODO Auto-generated constructor stub
	}



	public DistributionBoard(String distributionBoardId, String distributionBoardName, String distributionBoardLong,
			String distributionBoardLat, String distributionBoardCity, String distributionBoardSite,
			String distributionBoardSiteName, String distributionBoardWarehouse, Float distributionBoardCapacity,
			Float distributionBoardRowsNum, Float distributionBoardColsNum, Float distributionBoardFront,
			Float distributionBoardBack, String distributionBoardProjectId, Timestamp boardCreationDate,
			Timestamp boardLastModifiedDate, String dbNetLevel, String dBEngineerName, String dBInstaller,
			String dBDeploymentType, String dBAdaptorPanelType, String distributionBoardSerialNum, String distributionBoardControllerName,String rowCounting, String distributionBoardControllerId, int rowPerModule, int totalNumModule) {
		super();
		DistributionBoardId = distributionBoardId;
		this.distributionBoardName = distributionBoardName;
		this.distributionBoardLong = distributionBoardLong;
		this.distributionBoardLat = distributionBoardLat;
		this.rowPerModule =rowPerModule;
		this.distributionBoardSerialNum=distributionBoardSerialNum;
		this.distributionBoardCity = distributionBoardCity;
		this.rowCounting=rowCounting;
		this.distributionBoardSite = distributionBoardSite;
		this.distributionBoardSiteName = distributionBoardSiteName;
		this.distributionBoardWarehouse = distributionBoardWarehouse;
		DistributionBoardCapacity = distributionBoardCapacity;
		this.totalNumModule=totalNumModule;
		this.distributionBoardControllerName=distributionBoardControllerName;
		this.distributionBoardControllerId=distributionBoardControllerId;
		this.distributionBoardRowsNum = distributionBoardRowsNum;
		this.distributionBoardColsNum = distributionBoardColsNum;
		this.distributionBoardFront = distributionBoardFront;
		this.distributionBoardBack = distributionBoardBack;
		this.distributionBoardProjectId = distributionBoardProjectId;
		BoardCreationDate = boardCreationDate;
		BoardLastModifiedDate = boardLastModifiedDate;
		this.dbNetLevel = dbNetLevel;
		this.distributionBoardType=distributionBoardType;
		DBEngineerName = dBEngineerName;
		DBInstaller = dBInstaller;
		DBDeploymentType = dBDeploymentType;
		DBAdaptorPanelType = dBAdaptorPanelType;
	}



	public int getTotalNumModule() {
		return totalNumModule;
	}



	public void setTotalNumModule(int totalNumModule) {
		this.totalNumModule = totalNumModule;
	}



	public int getRowPerModule() {
		return rowPerModule;
	}



	public void setRowPerModule(int rowPerModule) {
		this.rowPerModule = rowPerModule;
	}



	public String getRowCounting() {
		return rowCounting;
	}



	public void setRowCounting(String rowCounting) {
		this.rowCounting = rowCounting;
	}



	public String getDistributionBoardSerialNum() {
		return distributionBoardSerialNum;
	}



	public void setDistributionBoardSerialNum(String distributionBoardSerialNum) {
		this.distributionBoardSerialNum = distributionBoardSerialNum;
	}



	public String getDistributionBoardControllerId() {
		return distributionBoardControllerId;
	}



	public void setDistributionBoardControllerId(String distributionBoardControllerId) {
		this.distributionBoardControllerId = distributionBoardControllerId;
	}



	public String getDistributionBoardControllerName() {
		return distributionBoardControllerName;
	}



	public void setDistributionBoardControllerName(String distributionBoardControllerName) {
		this.distributionBoardControllerName = distributionBoardControllerName;
	}



	public String getDistributionBoardType() {
		return distributionBoardType;
	}



	public void setDistributionBoardType(String distributionBoardType) {
		this.distributionBoardType = distributionBoardType;
	}



	public String getDistributionBoardId() {
		return DistributionBoardId;
	}



	public void setDistributionBoardId(String distributionBoardId) {
		DistributionBoardId = distributionBoardId;
	}



	public String getDistributionBoardName() {
		return distributionBoardName;
	}



	public void setDistributionBoardName(String distributionBoardName) {
		this.distributionBoardName = distributionBoardName;
	}



	public String getDistributionBoardLong() {
		return distributionBoardLong;
	}



	public void setDistributionBoardLong(String distributionBoardLong) {
		this.distributionBoardLong = distributionBoardLong;
	}



	public String getDistributionBoardLat() {
		return distributionBoardLat;
	}



	public void setDistributionBoardLat(String distributionBoardLat) {
		this.distributionBoardLat = distributionBoardLat;
	}



	public String getDistributionBoardCity() {
		return distributionBoardCity;
	}



	public void setDistributionBoardCity(String distributionBoardCity) {
		this.distributionBoardCity = distributionBoardCity;
	}



	public String getDistributionBoardSite() {
		return distributionBoardSite;
	}



	public void setDistributionBoardSite(String distributionBoardSite) {
		this.distributionBoardSite = distributionBoardSite;
	}



	public String getDistributionBoardSiteName() {
		return distributionBoardSiteName;
	}



	public void setDistributionBoardSiteName(String distributionBoardSiteName) {
		this.distributionBoardSiteName = distributionBoardSiteName;
	}



	public String getDistributionBoardWarehouse() {
		return distributionBoardWarehouse;
	}



	public void setDistributionBoardWarehouse(String distributionBoardWarehouse) {
		this.distributionBoardWarehouse = distributionBoardWarehouse;
	}



	public Float getDistributionBoardCapacity() {
		return DistributionBoardCapacity;
	}



	public void setDistributionBoardCapacity(Float distributionBoardCapacity) {
		DistributionBoardCapacity = distributionBoardCapacity;
	}



	public Float getDistributionBoardRowsNum() {
		return distributionBoardRowsNum;
	}



	public void setDistributionBoardRowsNum(Float distributionBoardRowsNum) {
		this.distributionBoardRowsNum = distributionBoardRowsNum;
	}



	public Float getDistributionBoardColsNum() {
		return distributionBoardColsNum;
	}



	public void setDistributionBoardColsNum(Float distributionBoardColsNum) {
		this.distributionBoardColsNum = distributionBoardColsNum;
	}



	public Float getDistributionBoardFront() {
		return distributionBoardFront;
	}



	public void setDistributionBoardFront(Float distributionBoardFront) {
		this.distributionBoardFront = distributionBoardFront;
	}



	public Float getDistributionBoardBack() {
		return distributionBoardBack;
	}



	public void setDistributionBoardBack(Float distributionBoardBack) {
		this.distributionBoardBack = distributionBoardBack;
	}



	public String getDistributionBoardProjectId() {
		return distributionBoardProjectId;
	}



	public void setDistributionBoardProjectId(String distributionBoardProjectId) {
		this.distributionBoardProjectId = distributionBoardProjectId;
	}



	public Timestamp getBoardCreationDate() {
		return BoardCreationDate;
	}



	public void setBoardCreationDate(Timestamp boardCreationDate) {
		BoardCreationDate = boardCreationDate;
	}



	public Timestamp getBoardLastModifiedDate() {
		return BoardLastModifiedDate;
	}



	public void setBoardLastModifiedDate(Timestamp boardLastModifiedDate) {
		BoardLastModifiedDate = boardLastModifiedDate;
	}



	public String getDbNetLevel() {
		return dbNetLevel;
	}



	public void setDbNetLevel(String dbNetLevel) {
		this.dbNetLevel = dbNetLevel;
	}



	public String getDBEngineerName() {
		return DBEngineerName;
	}



	public void setDBEngineerName(String dBEngineerName) {
		DBEngineerName = dBEngineerName;
	}



	public String getDBInstaller() {
		return DBInstaller;
	}



	public void setDBInstaller(String dBInstaller) {
		DBInstaller = dBInstaller;
	}



	public String getDBDeploymentType() {
		return DBDeploymentType;
	}



	public void setDBDeploymentType(String dBDeploymentType) {
		DBDeploymentType = dBDeploymentType;
	}



	public String getDBAdaptorPanelType() {
		return DBAdaptorPanelType;
	}



	public void setDBAdaptorPanelType(String dBAdaptorPanelType) {
		DBAdaptorPanelType = dBAdaptorPanelType;
	}



	
}


