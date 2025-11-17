package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISTRIBUTION_BOARD_MAPPING")
public class DistributionBoardMapping {
	
	@Id
	@Column(name = "DB_PORT_ID", nullable = false)
	private String db_Port_Id;
	
	@Column(name = "DB_ID")
	private String distributionBoardId;
	
	@Column(name = "ROW_COL_INDEX")
	private String rowColIndex;
	
	@Column(name = "ROW_NUMBER")
	private String rowNum;
	
	@Column(name = "COLUMN_NUMBER")
	private String colNum;
	
	@Column(name = "FP_STATUS")
	private String fP_Status;
	
	@Column(name = "FP_LOCATION_TYPE")
	private String fP_LocationType;
	
	@Column(name = "FP_LOCATION_ID")
	private String fP_LocationId;
	
	@Column(name = "FP_LOCATION_NAME")
	private String fP_LocationName;
	
	@Column(name = "FP_LOCATION")
	private String fP_Location;
	
	@Column(name = "FP_EQUIPMENT_TYPE")
	private String fP_EquipmentType;
	
	@Column(name = "FP_EQUIPMENT")
	private String fP_Equipment;
	
	@Column(name = "FP_EQUIPMENT_ID")
	private String fP_EquipmentId;
	
	@Column(name = "FP_EQUIPMENT_NAME")
	private String fP_EquipmentName;
	
	@Column(name = "FP_ADDRESS")
	private String fP_Address;
	
////added
	@Column(name = "FP_STRAND_ID")
	private String fP_StrandId;
	
	@Column(name = "FP_STRAND_NAME")
	private String fP_StrandName;
	
	@Column(name = "FP_TUBE_ID")
	private String fP_TubeId;
	
	@Column(name = "FP_TUBE_NAME")
	private String fP_TubeName;
	
	@Column(name = "FP_FIBER_ID")
	private String fP_FiberId;
	
	@Column(name = "FP_FIBER_NAME")
	private String fP_FiberName;
	
	/////
	
	@Column(name = "BP_STATUS")
	private String bP_Status;
	//added
	@Column(name = "BP_LOCATION_TYPE")
	private String bP_LocationType;
	
	@Column(name = "BP_LOCATION_ID")
	private String bP_LocationId;
	
	@Column(name = "BP_LOCATION_NAME")
	private String bP_LocationName;
	
	@Column(name = "BP_LOCATION")
	private String bP_Location;
	
	@Column(name = "BP_EQUIPMENT_TYPE")
	private String bP_EquipmentType;
	
	@Column(name = "BP_EQUIPMENT")
	private String bP_Equipment;
	
	@Column(name = "BP_EQUIPMENT_ID")
	private String bP_EquipmentId;
	
	@Column(name = "BP_EQUIPMENT_NAME")
	private String bP_EquipmentName;
	
	@Column(name = "BP_ADDRESS")
	private String bP_Address;
		
		///
	
	@Column(name = "BP_STRAND_ID")
	private String bP_StrandId;
	
	@Column(name = "BP_STRAND_NAME")
	private String bP_StrandName;
	
	@Column(name = "BP_TUBE_ID")
	private String bP_TubeId;
	
	@Column(name = "BP_TUBE_NAME")
	private String bP_TubeName;
	
	@Column(name = "BP_FIBER_ID")
	private String bP_FiberId;
	
	@Column(name = "BP_FIBER_NAME")
	private String bP_FiberName;

	@Column(name = "BP_STRAND_NB")
	private String bP_StrandNb;
	
	@Column(name = "FP_STRAND_NB")
	private String fP_StrandNb;
	
	@Column(name = "FP_TUBE_NB")
	private String fP_TubeNb;
	
	@Column(name = "BP_TUBE_NB")
	private String bP_TubeNb;
	
	@Column(name = "BP_STRAND_COLOR")
	private String bP_StrandColor;
	
	@Column(name = "FP_STRAND_COLOR")
	private String fP_StrandColor;
	
	@Column(name = "FP_TUBE_COLOR")
	private String fP_TubeColor;
	
	@Column(name = "BP_TUBE_COLOR")
	private String bP_TubeColor;
	
	@Column(name = "BP_JUNCTION_ID")
	private String bP_JunctionId;
	
	@Column(name = "BP_JUNCTION_NAME")
	private String bP_JunctionName;
	
	@Column(name = "FP_JUNCTION_ID")
	private String fP_JunctionId;
	
	@Column(name = "FP_JUNCTION_NAME")
	private String fP_JunctionName;
	
	@Column(name = "NEAR_MODULE")
	private String nearModule;

	@Column(name = "NEAR_PORT_NUM")
	private String nearPortNum;
	
	@Column(name = "NEAR_PATCH_TYPE")
	private String nearPatchType;
	
	@Column(name = "FAR_NEAR_KIT_SERIAL_NUM")
	private String farKitSerialNum;
	
	@Column(name = "FAR_NEAR_MODULE")
	private String farModule;
	
	@Column(name = "FAR_NEAR_PORT_NUM")
	private String farPortNum;
	
	@Column(name = "BACK_KIT_MODULE")
	private String backKitModule;
	
	@Column(name = "BACK_PORT_NUM")
	private String backportNum;
	
	
	
	
	
	public DistributionBoardMapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*public DistributionBoardMapping(String db_Port_Id, String distributionBoardId, String rowColIndex, String rowNum,
			String colNum, String fP_Status, String fP_LocationType, String fP_LocationId, String fP_LocationName,
			String fP_Location, String fP_EquipmentType, String fP_Equipment, String fP_EquipmentId,
			String fP_EquipmentName, String fP_Address, String bP_Status, String bP_StrandId, String bP_StrandName,
			String bP_TubeId, String bP_TubeName, String bP_FiberId, String bP_FiberName) {
		super();
		this.db_Port_Id = db_Port_Id;
		this.distributionBoardId = distributionBoardId;
		this.rowColIndex = rowColIndex;
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.fP_Status = fP_Status;
		this.fP_LocationType = fP_LocationType;
		this.fP_LocationId = fP_LocationId;
		this.fP_LocationName = fP_LocationName;
		this.fP_Location = fP_Location;
		this.fP_EquipmentType = fP_EquipmentType;
		this.fP_Equipment = fP_Equipment;
		this.fP_EquipmentId = fP_EquipmentId;
		this.fP_EquipmentName = fP_EquipmentName;
		this.fP_Address = fP_Address;
		this.bP_Status = bP_Status;
		this.bP_StrandId = bP_StrandId;
		this.bP_StrandName = bP_StrandName;
		this.bP_TubeId = bP_TubeId;
		this.bP_TubeName = bP_TubeName;
		this.bP_FiberId = bP_FiberId;
		this.bP_FiberName = bP_FiberName;
	}*/
	///added
	
			
	/*		
	public DistributionBoardMapping(String db_Port_Id, String distributionBoardId, String rowColIndex,
			String rowNum, String colNum, String fP_Status, String fP_LocationType, String fP_LocationId,
			String fP_LocationName, String fP_Location, String fP_EquipmentType, String fP_Equipment,
			String fP_EquipmentId, String fP_EquipmentName, String fP_Address, String fP_StrandId,
			String fP_StrandName, String fP_TubeId, String fP_TubeName, String fP_FiberId, String fP_FiberName,
			String bP_Status, String bP_LocationType, String bP_LocationId, String bP_LocationName,
			String bP_Location, String bP_EquipmentType, String bP_Equipment, String bP_EquipmentId,
			String bP_EquipmentName, String bP_Address, String bP_StrandId, String bP_StrandName,
			String bP_TubeId, String bP_TubeName, String bP_FiberId, String bP_FiberName, String bP_StrandNb,
			String fP_StrandNb, String fP_TubeNb, String bP_TubeNb, String bP_StrandColor,
			String fP_StrandColor, String fP_TubeColor, String bP_TubeColor) {
		super();
		this.db_Port_Id = db_Port_Id;
		this.distributionBoardId = distributionBoardId;
		this.rowColIndex = rowColIndex;
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.fP_Status = fP_Status;
		this.fP_LocationType = fP_LocationType;
		this.fP_LocationId = fP_LocationId;
		this.fP_LocationName = fP_LocationName;
		this.fP_Location = fP_Location;
		this.fP_EquipmentType = fP_EquipmentType;
		this.fP_Equipment = fP_Equipment;
		this.fP_EquipmentId = fP_EquipmentId;
		this.fP_EquipmentName = fP_EquipmentName;
		this.fP_Address = fP_Address;
		this.fP_StrandId = fP_StrandId;
		this.fP_StrandName = fP_StrandName;
		this.fP_TubeId = fP_TubeId;
		this.fP_TubeName = fP_TubeName;
		this.fP_FiberId = fP_FiberId;
		this.fP_FiberName = fP_FiberName;
		this.bP_Status = bP_Status;
		this.bP_LocationType = bP_LocationType;
		this.bP_LocationId = bP_LocationId;
		this.bP_LocationName = bP_LocationName;
		this.bP_Location = bP_Location;
		this.bP_EquipmentType = bP_EquipmentType;
		this.bP_Equipment = bP_Equipment;
		this.bP_EquipmentId = bP_EquipmentId;
		this.bP_EquipmentName = bP_EquipmentName;
		this.bP_Address = bP_Address;
		this.bP_StrandId = bP_StrandId;
		this.bP_StrandName = bP_StrandName;
		this.bP_TubeId = bP_TubeId;
		this.bP_TubeName = bP_TubeName;
		this.bP_FiberId = bP_FiberId;
		this.bP_FiberName = bP_FiberName;
		this.bP_StrandNb = bP_StrandNb;
		this.fP_StrandNb = fP_StrandNb;
		this.fP_TubeNb = fP_TubeNb;
		this.bP_TubeNb = bP_TubeNb;
		this.bP_StrandColor = bP_StrandColor;
		this.fP_StrandColor = fP_StrandColor;
		this.fP_TubeColor = fP_TubeColor;
		this.bP_TubeColor = bP_TubeColor;
	}*/
	
	public DistributionBoardMapping(String db_Port_Id, String distributionBoardId, String rowColIndex, String rowNum,
			String colNum, String fP_Status, String fP_LocationType, String fP_LocationId, String fP_LocationName,
			String fP_Location, String fP_EquipmentType, String fP_Equipment, String fP_EquipmentId,
			String fP_EquipmentName, String fP_Address, String fP_StrandId, String fP_StrandName, String fP_TubeId,
			String fP_TubeName, String fP_FiberId, String fP_FiberName, String bP_Status, String bP_LocationType,
			String bP_LocationId, String bP_LocationName, String bP_Location, String bP_EquipmentType,
			String bP_Equipment, String bP_EquipmentId, String bP_EquipmentName, String bP_Address, String bP_StrandId,
			String bP_StrandName, String bP_TubeId, String bP_TubeName, String bP_FiberId, String bP_FiberName,
			String bP_StrandNb, String fP_StrandNb, String fP_TubeNb, String bP_TubeNb, String bP_StrandColor,
			String fP_StrandColor, String fP_TubeColor, String bP_TubeColor, String bP_JunctionId,
			String bP_JunctionName, String fP_JunctionId, String fP_JunctionName, 
			String nearModule, String nearPortNum, String nearPatchType, String farKitSerialNum, String farModule, 
			String farPortNum, String backKitModule, String backportNum) {
		super();
		this.db_Port_Id = db_Port_Id;
		this.distributionBoardId = distributionBoardId;
		this.rowColIndex = rowColIndex;
		this.rowNum = rowNum;
		this.nearModule=nearModule;
		this.nearPortNum=nearPortNum;
		this.nearPatchType=nearPatchType;
		this.farKitSerialNum=farKitSerialNum;
		this.farModule=farModule;
		this.farPortNum=farPortNum;
		this.backKitModule=backKitModule;
		this.backportNum=backportNum;
		this.colNum = colNum;
		this.fP_Status = fP_Status;
		this.fP_LocationType = fP_LocationType;
		this.fP_LocationId = fP_LocationId;
		this.fP_LocationName = fP_LocationName;
		this.fP_Location = fP_Location;
		this.fP_EquipmentType = fP_EquipmentType;
		this.fP_Equipment = fP_Equipment;
		this.fP_EquipmentId = fP_EquipmentId;
		this.fP_EquipmentName = fP_EquipmentName;
		this.fP_Address = fP_Address;
		this.fP_StrandId = fP_StrandId;
		this.fP_StrandName = fP_StrandName;
		this.fP_TubeId = fP_TubeId;
		this.fP_TubeName = fP_TubeName;
		this.fP_FiberId = fP_FiberId;
		this.fP_FiberName = fP_FiberName;
		this.bP_Status = bP_Status;
		this.bP_LocationType = bP_LocationType;
		this.bP_LocationId = bP_LocationId;
		this.bP_LocationName = bP_LocationName;
		this.bP_Location = bP_Location;
		this.bP_EquipmentType = bP_EquipmentType;
		this.bP_Equipment = bP_Equipment;
		this.bP_EquipmentId = bP_EquipmentId;
		this.bP_EquipmentName = bP_EquipmentName;
		this.bP_Address = bP_Address;
		this.bP_StrandId = bP_StrandId;
		this.bP_StrandName = bP_StrandName;
		this.bP_TubeId = bP_TubeId;
		this.bP_TubeName = bP_TubeName;
		this.bP_FiberId = bP_FiberId;
		this.bP_FiberName = bP_FiberName;
		this.bP_StrandNb = bP_StrandNb;
		this.fP_StrandNb = fP_StrandNb;
		this.fP_TubeNb = fP_TubeNb;
		this.bP_TubeNb = bP_TubeNb;
		this.bP_StrandColor = bP_StrandColor;
		this.fP_StrandColor = fP_StrandColor;
		this.fP_TubeColor = fP_TubeColor;
		this.bP_TubeColor = bP_TubeColor;
		this.bP_JunctionId = bP_JunctionId;
		this.bP_JunctionName = bP_JunctionName;
		this.fP_JunctionId = fP_JunctionId;
		this.fP_JunctionName = fP_JunctionName;
	}
			
			////
	

	public String getNearModule() {
		return nearModule;
	}

	public void setNearModule(String nearModule) {
		this.nearModule = nearModule;
	}

	public String getNearPortNum() {
		return nearPortNum;
	}

	public void setNearPortNum(String nearPortNum) {
		this.nearPortNum = nearPortNum;
	}

	public String getNearPatchType() {
		return nearPatchType;
	}

	public void setNearPatchType(String nearPatchType) {
		this.nearPatchType = nearPatchType;
	}

	public String getFarKitSerialNum() {
		return farKitSerialNum;
	}

	public void setFarKitSerialNum(String farKitSerialNum) {
		this.farKitSerialNum = farKitSerialNum;
	}

	public String getFarModule() {
		return farModule;
	}

	public void setFarModule(String farModule) {
		this.farModule = farModule;
	}

	public String getFarPortNum() {
		return farPortNum;
	}

	public void setFarPortNum(String farPortNum) {
		this.farPortNum = farPortNum;
	}

	public String getBackKitModule() {
		return backKitModule;
	}

	public void setBackKitModule(String backKitModule) {
		this.backKitModule = backKitModule;
	}

	public String getBackportNum() {
		return backportNum;
	}

	public void setBackportNum(String backportNum) {
		this.backportNum = backportNum;
	}

	public String getDb_Port_Id() {
		return db_Port_Id;
	}

	public void setDb_Port_Id(String db_Port_Id) {
		this.db_Port_Id = db_Port_Id;
	}

	public String getDistributionBoardId() {
		return distributionBoardId;
	}

	public void setDistributionBoardId(String distributionBoardId) {
		this.distributionBoardId = distributionBoardId;
	}

	public String getRowColIndex() {
		return rowColIndex;
	}

	public void setRowColIndex(String rowColIndex) {
		this.rowColIndex = rowColIndex;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getColNum() {
		return colNum;
	}

	public void setColNum(String colNum) {
		this.colNum = colNum;
	}

	public String getfP_Status() {
		return fP_Status;
	}

	public void setfP_Status(String fP_Status) {
		this.fP_Status = fP_Status;
	}

	public String getfP_LocationType() {
		return fP_LocationType;
	}

	public void setfP_LocationType(String fP_LocationType) {
		this.fP_LocationType = fP_LocationType;
	}

	public String getfP_LocationId() {
		return fP_LocationId;
	}

	public void setfP_LocationId(String fP_LocationId) {
		this.fP_LocationId = fP_LocationId;
	}

	public String getfP_LocationName() {
		return fP_LocationName;
	}

	public void setfP_LocationName(String fP_LocationName) {
		this.fP_LocationName = fP_LocationName;
	}

	public String getfP_Location() {
		return fP_Location;
	}

	public void setfP_Location(String fP_Location) {
		this.fP_Location = fP_Location;
	}

	public String getfP_EquipmentType() {
		return fP_EquipmentType;
	}

	public void setfP_EquipmentType(String fP_EquipmentType) {
		this.fP_EquipmentType = fP_EquipmentType;
	}

	public String getfP_Equipment() {
		return fP_Equipment;
	}

	public void setfP_Equipment(String fP_Equipment) {
		this.fP_Equipment = fP_Equipment;
	}

	public String getfP_EquipmentId() {
		return fP_EquipmentId;
	}

	public void setfP_EquipmentId(String fP_EquipmentId) {
		this.fP_EquipmentId = fP_EquipmentId;
	}

	public String getfP_EquipmentName() {
		return fP_EquipmentName;
	}

	public void setfP_EquipmentName(String fP_EquipmentName) {
		this.fP_EquipmentName = fP_EquipmentName;
	}

	public String getfP_Address() {
		return fP_Address;
	}

	public void setfP_Address(String fP_Address) {
		this.fP_Address = fP_Address;
	}

	public String getbP_Status() {
		return bP_Status;
	}

	public void setbP_Status(String bP_Status) {
		this.bP_Status = bP_Status;
	}

	public String getbP_StrandId() {
		return bP_StrandId;
	}

	public void setbP_StrandId(String bP_StrandId) {
		this.bP_StrandId = bP_StrandId;
	}

	public String getbP_StrandName() {
		return bP_StrandName;
	}

	public void setbP_StrandName(String bP_StrandName) {
		this.bP_StrandName = bP_StrandName;
	}

	public String getbP_TubeId() {
		return bP_TubeId;
	}

	public void setbP_TubeId(String bP_TubeId) {
		this.bP_TubeId = bP_TubeId;
	}

	public String getbP_TubeName() {
		return bP_TubeName;
	}

	public void setbP_TubeName(String bP_TubeName) {
		this.bP_TubeName = bP_TubeName;
	}

	public String getbP_FiberId() {
		return bP_FiberId;
	}

	public void setbP_FiberId(String bP_FiberId) {
		this.bP_FiberId = bP_FiberId;
	}

	public String getbP_FiberName() {
		return bP_FiberName;
	}

	public void setbP_FiberName(String bP_FiberName) {
		this.bP_FiberName = bP_FiberName;
	}

	public String getfP_StrandId() {
		return fP_StrandId;
	}

	public void setfP_StrandId(String fP_StrandId) {
		this.fP_StrandId = fP_StrandId;
	}

	public String getfP_StrandName() {
		return fP_StrandName;
	}

	public void setfP_StrandName(String fP_StrandName) {
		this.fP_StrandName = fP_StrandName;
	}

	public String getfP_TubeId() {
		return fP_TubeId;
	}

	public void setfP_TubeId(String fP_TubeId) {
		this.fP_TubeId = fP_TubeId;
	}

	public String getfP_TubeName() {
		return fP_TubeName;
	}

	public void setfP_TubeName(String fP_TubeName) {
		this.fP_TubeName = fP_TubeName;
	}

	public String getfP_FiberId() {
		return fP_FiberId;
	}

	public void setfP_FiberId(String fP_FiberId) {
		this.fP_FiberId = fP_FiberId;
	}

	public String getfP_FiberName() {
		return fP_FiberName;
	}

	public void setfP_FiberName(String fP_FiberName) {
		this.fP_FiberName = fP_FiberName;
	}

	public String getbP_LocationType() {
		return bP_LocationType;
	}

	public void setbP_LocationType(String bP_LocationType) {
		this.bP_LocationType = bP_LocationType;
	}

	public String getbP_LocationId() {
		return bP_LocationId;
	}

	public void setbP_LocationId(String bP_LocationId) {
		this.bP_LocationId = bP_LocationId;
	}

	public String getbP_LocationName() {
		return bP_LocationName;
	}

	public void setbP_LocationName(String bP_LocationName) {
		this.bP_LocationName = bP_LocationName;
	}

	public String getbP_Location() {
		return bP_Location;
	}

	public void setbP_Location(String bP_Location) {
		this.bP_Location = bP_Location;
	}

	public String getbP_EquipmentType() {
		return bP_EquipmentType;
	}

	public void setbP_EquipmentType(String bP_EquipmentType) {
		this.bP_EquipmentType = bP_EquipmentType;
	}

	public String getbP_Equipment() {
		return bP_Equipment;
	}

	public void setbP_Equipment(String bP_Equipment) {
		this.bP_Equipment = bP_Equipment;
	}

	public String getbP_EquipmentId() {
		return bP_EquipmentId;
	}

	public void setbP_EquipmentId(String bP_EquipmentId) {
		this.bP_EquipmentId = bP_EquipmentId;
	}

	public String getbP_EquipmentName() {
		return bP_EquipmentName;
	}

	public void setbP_EquipmentName(String bP_EquipmentName) {
		this.bP_EquipmentName = bP_EquipmentName;
	}

	public String getbP_Address() {
		return bP_Address;
	}

	public void setbP_Address(String bP_Address) {
		this.bP_Address = bP_Address;
	}

	public String getbP_StrandNb() {
		return bP_StrandNb;
	}

	public void setbP_StrandNb(String bP_StrandNb) {
		this.bP_StrandNb = bP_StrandNb;
	}

	public String getfP_StrandNb() {
		return fP_StrandNb;
	}

	public void setfP_StrandNb(String fP_StrandNb) {
		this.fP_StrandNb = fP_StrandNb;
	}

	public String getfP_TubeNb() {
		return fP_TubeNb;
	}

	public void setfP_TubeNb(String fP_TubeNb) {
		this.fP_TubeNb = fP_TubeNb;
	}

	public String getbP_TubeNb() {
		return bP_TubeNb;
	}

	public void setbP_TubeNb(String bP_TubeNb) {
		this.bP_TubeNb = bP_TubeNb;
	}
	
	public String getbP_StrandColor() {
		return bP_StrandColor;
	}

	public void setbP_StrandColor(String bP_StrandColor) {
		this.bP_StrandColor = bP_StrandColor;
	}

	public String getfP_StrandColor() {
		return fP_StrandColor;
	}

	public void setfP_StrandColor(String fP_StrandColor) {
		this.fP_StrandColor = fP_StrandColor;
	}

	public String getfP_TubeColor() {
		return fP_TubeColor;
	}

	public void setfP_TubeColor(String fP_TubeColor) {
		this.fP_TubeColor = fP_TubeColor;
	}

	public String getbP_TubeColor() {
		return bP_TubeColor;
	}

	public void setbP_TubeColor(String bP_TubeColor) {
		this.bP_TubeColor = bP_TubeColor;
	}
	
	public String getbP_JunctionId() {
		return bP_JunctionId;
	}

	public void setbP_JunctionId(String bP_JunctionId) {
		this.bP_JunctionId = bP_JunctionId;
	}

	public String getbP_JunctionName() {
		return bP_JunctionName;
	}

	public void setbP_JunctionName(String bP_JunctionName) {
		this.bP_JunctionName = bP_JunctionName;
	}

	public String getfP_JunctionId() {
		return fP_JunctionId;
	}

	public void setfP_JunctionId(String fP_JunctionId) {
		this.fP_JunctionId = fP_JunctionId;
	}

	public String getfP_JunctionName() {
		return fP_JunctionName;
	}

	public void setfP_JunctionName(String fP_JunctionName) {
		this.fP_JunctionName = fP_JunctionName;
	}
	
	

}
