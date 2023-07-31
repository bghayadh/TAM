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
	
	@Column(name = "DB_LONGITUDE")
	private String distributionBoardLong;
	
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


	
	public DistributionBoard() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	public DistributionBoard(String distributionBoardId, String distributionBoardName, String distributionBoardLong,
			String distributionBoardLat, String distributionBoardCity, String distributionBoardSite,
			String distributionBoardSiteName, String distributionBoardWarehouse, Float distributionBoardCapacity,
			Float distributionBoardRowsNum, Float distributionBoardColsNum, Float distributionBoardFront,
			Float distributionBoardBack, String distributionBoardProjectId) {
		super();
		DistributionBoardId = distributionBoardId;
		this.distributionBoardName = distributionBoardName;
		this.distributionBoardLong = distributionBoardLong;
		this.distributionBoardLat = distributionBoardLat;
		this.distributionBoardCity = distributionBoardCity;
		this.distributionBoardSite = distributionBoardSite;
		this.distributionBoardSiteName = distributionBoardSiteName;
		this.distributionBoardWarehouse = distributionBoardWarehouse;
		DistributionBoardCapacity = distributionBoardCapacity;
		this.distributionBoardRowsNum = distributionBoardRowsNum;
		this.distributionBoardColsNum = distributionBoardColsNum;
		this.distributionBoardFront = distributionBoardFront;
		this.distributionBoardBack = distributionBoardBack;
		this.distributionBoardProjectId = distributionBoardProjectId;
	}*/
	
	public DistributionBoard(String distributionBoardId, String distributionBoardName, String distributionBoardLong,
			String distributionBoardLat, String distributionBoardCity, String distributionBoardSite,
			String distributionBoardSiteName, String distributionBoardWarehouse, Float distributionBoardCapacity,
			Float distributionBoardRowsNum, Float distributionBoardColsNum, Float distributionBoardFront,
			Float distributionBoardBack, String distributionBoardProjectId, Timestamp boardCreationDate,
			Timestamp boardLastModifiedDate,String dbNetLevel) {
		super();
		DistributionBoardId = distributionBoardId;
		this.distributionBoardName = distributionBoardName;
		this.distributionBoardLong = distributionBoardLong;
		this.distributionBoardLat = distributionBoardLat;
		this.distributionBoardCity = distributionBoardCity;
		this.distributionBoardSite = distributionBoardSite;
		this.distributionBoardSiteName = distributionBoardSiteName;
		this.distributionBoardWarehouse = distributionBoardWarehouse;
		DistributionBoardCapacity = distributionBoardCapacity;
		this.distributionBoardRowsNum = distributionBoardRowsNum;
		this.distributionBoardColsNum = distributionBoardColsNum;
		this.distributionBoardFront = distributionBoardFront;
		this.distributionBoardBack = distributionBoardBack;
		this.distributionBoardProjectId = distributionBoardProjectId;
		BoardCreationDate = boardCreationDate;
		BoardLastModifiedDate = boardLastModifiedDate;
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
		this.BoardCreationDate = boardCreationDate;
	}


	public Timestamp getBoardLastModifiedDate() {
		return BoardLastModifiedDate;
	}


	public void setBoardLastModifiedDate(Timestamp boardLastModifiedDate) {
		this.BoardLastModifiedDate = boardLastModifiedDate;
	}
	
	public String getdbNetLevel() {
		return dbNetLevel;
	}

	public void setdbNetLevel(String dbNetLevel) {
		this.dbNetLevel = dbNetLevel;
	}



}


