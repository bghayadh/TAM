package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "WORK_ORDER_TASK")
public class WorkOrderTask {


	     @Id
	    @Column(name = "WO_TASK_ID", nullable = false)
	    private String woTaskId;

	    @Column(name = "PATCHING_ID")
	    private String patchingId;

	    @Column(name = "CREATION_DATE")
	    private Timestamp creationDate;

	    @Column(name = "LAST_MODIFIED_DATE")
	    private Timestamp lastModifiedDate;

	    @Column(name = "COMPLETION_DATE")
	    private Timestamp completionDate;

	    // EXISTING FIELDS (unchanged)
	    @Column(name = "TASK_TYPE")
	    private String taskType; 

	 

	    @Column(name = "TASK_STATUS")
	    private String taskStatus;
	    
	    @Column(name = "DB_ID")
	    private String dbId;

	    @Column(name = "DB_PORT_ID")
	    private String dbPortId;

	    @Column(name = "ROW_COL_INDEX")
	    private Integer rowColIndex;

	    @Column(name = "ROW_NUMBER")
	    private Integer rowNumber;

	    @Column(name = "COLUMN_NUMBER")
	    private Integer columnNumber;

	    @Column(name = "NEAR_MODULE")
	    private String nearModule;

	    @Column(name = "NEAR_PORT_NUM")
	    private String nearPortNum;

	    @Column(name = "NEAR_PATCH_TYPE")
	    private String nearPatchType;

	    @Column(name = "FP_LOCATION_TYPE")
	    private String fpLocationType;

	    @Column(name = "FP_LOCATION_ID")
	    private String fpLocationId;

	    @Column(name = "FP_LOCATION_NAME")
	    private String fpLocationName;

	    @Column(name = "FP_LOCATION")
	    private String fpLocation;

	    @Column(name = "FP_EQUIPMENT_TYPE")
	    private String fpEquipmentType;

	    @Column(name = "FP_EQUIPMENT")
	    private String fpEquipment;

	    @Column(name = "FP_EQUIPMENT_ID")
	    private String fpEquipmentId;

	    @Column(name = "FP_EQUIPMENT_NAME")
	    private String fpEquipmentName;

	    @Column(name = "FP_ADDRESS")
	    private String fpAddress;

	    @Column(name = "FP_TUBE_NB")
	    private String fpTubeNb;

	    @Column(name = "FP_STRAND_NB")
	    private String fpStrandNb;
	    
	    @Column(name = "FP_STRAND_ID")
	    private String fpStrandId;
	    
	    @Column(name = "FP_STRAND_COLOR")
	    private String fpStrandColor;

	    @Column(name = "FP_TUBE_COLOR")
	    private String fpTubeColor;

	    @Column(name = "FP_STRAND_NAME")
	    private String fpStrandName;

	    @Column(name = "FP_TUBE_ID")
	    private String fpTubeId;

	    @Column(name = "FP_TUBE_NAME")
	    private String fpTubeName;

	    @Column(name = "FP_FIBER_ID")
	    private String fpFiberId;

	    @Column(name = "FP_FIBER_NAME")
	    private String fpFiberName;

	    @Column(name = "FP_KIT_SERIAL_NUM")
	    private String fpKitSerialNum;

	    @Column(name = "FP_MODULE")
	    private String fpModule;

	    @Column(name = "FP_PORT_NUM")
	    private String fpPortNum;

	    @Column(name = "FP_JUNCTION_ID")
	    private String fpJunctionId;

	    @Column(name = "FP_JUNCTION_NAME")
	    private String fpJunctionName;

	    // NEW BP FIELDS (25 added)
	    @Column(name = "BP_STRAND_COLOR")
	    private String bpStrandColor;

	    @Column(name = "BP_TUBE_COLOR")
	    private String bpTubeColor;

	    @Column(name = "BP_LOCATION_TYPE")
	    private String bpLocationType;

	    @Column(name = "BP_LOCATION_ID")
	    private String bpLocationId;

	    @Column(name = "BP_LOCATION_NAME")
	    private String bpLocationName;

	    @Column(name = "BP_LOCATION")
	    private String bpLocation;

	    @Column(name = "BP_EQUIPMENT_TYPE")
	    private String bpEquipmentType;

	    @Column(name = "BP_EQUIPMENT")
	    private String bpEquipment;

	    @Column(name = "BP_STRAND_NB")
	    private String bpStrandNb;

	    @Column(name = "BP_TUBE_NB")
	    private String bpTubeNb;

	    @Column(name = "BP_EQUIPMENT_ID")
	    private String bpEquipmentId;

	    @Column(name = "BP_EQUIPMENT_NAME")
	    private String bpEquipmentName;

	    @Column(name = "BP_ADDRESS")
	    private String bpAddress;

	    @Column(name = "BP_STATUS")
	    private String bpStatus;

	    @Column(name = "BP_STRAND_ID")
	    private String bpStrandId;

	    @Column(name = "BP_STRAND_NAME")
	    private String bpStrandName;

	    @Column(name = "BP_TUBE_ID")
	    private String bpTubeId;

	    @Column(name = "BP_TUBE_NAME")
	    private String bpTubeName;

	    @Column(name = "BP_FIBER_ID")
	    private String bpFiberId;

	    @Column(name = "BP_FIBER_NAME")
	    private String bpFiberName;

	    @Column(name = "BP_JUNCTION_ID")
	    private String bpJunctionId;

	    @Column(name = "BP_JUNCTION_NAME")
	    private String bpJunctionName;

	    @Column(name = "BACK_MODULE")
	    private String backModule;

	    @Column(name = "BACK_KIT_SERIAL_NUM")
	    private String backKitSerialNum;

	    @Column(name = "BACK_PORT_NUM")
	    private String backPortNum;
	    
	    @Column(name = "FAR_NEAR_KIT_SERIAL_NUM")
	    private String farNearKitSerialNum;

	    @Column(name = "FAR_NEAR_MODULE")
	    private String farNearModule;

	    @Column(name = "FAR_NEAR_PORT_NUM")
	    private String farNearPortNum; 
	    
    public String getWoTaskId() {
		return woTaskId;
	}

	public void setWoTaskId(String woTaskId) {
		this.woTaskId = woTaskId;
	}

	public String getPatchingId() {
		return patchingId;
	}

	public void setPatchingId(String patchingId) {
		this.patchingId = patchingId;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Timestamp getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Timestamp completionDate) {
		this.completionDate = completionDate;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getDbId() {
		return dbId;
	}

	public void setDbId(String dbId) {
		this.dbId = dbId;
	}

	public String getDbPortId() {
		return dbPortId;
	}

	public void setDbPortId(String dbPortId) {
		this.dbPortId = dbPortId;
	}

	public Integer getRowColIndex() {
		return rowColIndex;
	}

	public void setRowColIndex(Integer rowColIndex) {
		this.rowColIndex = rowColIndex;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Integer getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(Integer columnNumber) {
		this.columnNumber = columnNumber;
	}

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

	public String getFpLocationType() {
		return fpLocationType;
	}

	public void setFpLocationType(String fpLocationType) {
		this.fpLocationType = fpLocationType;
	}

	public String getFpLocationId() {
		return fpLocationId;
	}

	public void setFpLocationId(String fpLocationId) {
		this.fpLocationId = fpLocationId;
	}

	public String getFpLocationName() {
		return fpLocationName;
	}

	public void setFpLocationName(String fpLocationName) {
		this.fpLocationName = fpLocationName;
	}

	public String getFpLocation() {
		return fpLocation;
	}

	public void setFpLocation(String fpLocation) {
		this.fpLocation = fpLocation;
	}

	public String getFpEquipmentType() {
		return fpEquipmentType;
	}

	public void setFpEquipmentType(String fpEquipmentType) {
		this.fpEquipmentType = fpEquipmentType;
	}

	public String getFpEquipment() {
		return fpEquipment;
	}

	public void setFpEquipment(String fpEquipment) {
		this.fpEquipment = fpEquipment;
	}

	public String getFpEquipmentId() {
		return fpEquipmentId;
	}

	public void setFpEquipmentId(String fpEquipmentId) {
		this.fpEquipmentId = fpEquipmentId;
	}

	public String getFpEquipmentName() {
		return fpEquipmentName;
	}

	public void setFpEquipmentName(String fpEquipmentName) {
		this.fpEquipmentName = fpEquipmentName;
	}

	public String getFpAddress() {
		return fpAddress;
	}

	public void setFpAddress(String fpAddress) {
		this.fpAddress = fpAddress;
	}

	public String getFpTubeNb() {
		return fpTubeNb;
	}

	public void setFpTubeNb(String fpTubeNb) {
		this.fpTubeNb = fpTubeNb;
	}

	public String getFpStrandColor() {
		return fpStrandColor;
	}

	public void setFpStrandColor(String fpStrandColor) {
		this.fpStrandColor = fpStrandColor;
	}

	public String getFpTubeColor() {
		return fpTubeColor;
	}

	public void setFpTubeColor(String fpTubeColor) {
		this.fpTubeColor = fpTubeColor;
	}

	public String getFpStrandName() {
		return fpStrandName;
	}

	public void setFpStrandName(String fpStrandName) {
		this.fpStrandName = fpStrandName;
	}

	public String getFpTubeId() {
		return fpTubeId;
	}

	public void setFpTubeId(String fpTubeId) {
		this.fpTubeId = fpTubeId;
	}

	public String getFpTubeName() {
		return fpTubeName;
	}

	public void setFpTubeName(String fpTubeName) {
		this.fpTubeName = fpTubeName;
	}

	public String getFpFiberId() {
		return fpFiberId;
	}

	public void setFpFiberId(String fpFiberId) {
		this.fpFiberId = fpFiberId;
	}

	public String getFpFiberName() {
		return fpFiberName;
	}

	public void setFpFiberName(String fpFiberName) {
		this.fpFiberName = fpFiberName;
	}

	public String getFpKitSerialNum() {
		return fpKitSerialNum;
	}

	public void setFpKitSerialNum(String fpKitSerialNum) {
		this.fpKitSerialNum = fpKitSerialNum;
	}

	public String getFpModule() {
		return fpModule;
	}

	public void setFpModule(String fpModule) {
		this.fpModule = fpModule;
	}

	public String getFpPortNum() {
		return fpPortNum;
	}

	public void setFpPortNum(String fpPortNum) {
		this.fpPortNum = fpPortNum;
	}

	public String getFpJunctionId() {
		return fpJunctionId;
	}

	public void setFpJunctionId(String fpJunctionId) {
		this.fpJunctionId = fpJunctionId;
	}

	public String getFpJunctionName() {
		return fpJunctionName;
	}

	public void setFpJunctionName(String fpJunctionName) {
		this.fpJunctionName = fpJunctionName;
	}



    public WorkOrderTask() {
    }

    public WorkOrderTask(
            String woTaskId, String patchingId, Timestamp creationDate, Timestamp lastModifiedDate, 
            Timestamp completionDate, String taskType, String taskStatus, String dbId, String dbPortId,
            Integer rowColIndex, Integer rowNumber, Integer columnNumber, String nearModule,
            String nearPortNum, String nearPatchType, String fpLocationType, String fpLocationId,
            String fpLocationName, String fpLocation, String fpEquipmentType, String fpEquipment,
            String fpEquipmentId, String fpEquipmentName, String fpAddress, String fpTubeNb,
            String fpStrandColor, String fpTubeColor, String fpStrandName, String fpTubeId,
            String fpTubeName, String fpFiberId, String fpFiberName, String fpKitSerialNum,
            String fpModule, String fpPortNum, String fpJunctionId, String fpJunctionName,
            // NEW BP/BACK PARAMS
            String bpStrandColor, String bpTubeColor, String bpLocationType, String bpLocationId,
            String bpLocationName, String bpLocation, String bpEquipmentType, String bpEquipment,
            String bpStrandNb, String bpTubeNb, String bpEquipmentId, String bpEquipmentName,
            String bpAddress, String bpStatus, String bpStrandId, String bpStrandName,
            String bpTubeId, String bpTubeName, String bpFiberId, String bpFiberName,
            String bpJunctionId, String bpJunctionName, String backModule, String backKitSerialNum,
            String backPortNum, String farNearKitSerialNum, String farNearModule, String farNearPortNum, String fpStrandNb, String fpStrandId) {
        
        this.woTaskId = woTaskId;
        this.patchingId = patchingId;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.completionDate = completionDate;
        this.taskType = taskType;
        this.fpStrandNb=fpStrandNb;
        this.taskStatus = taskStatus;
        this.dbId = dbId;
        this.dbPortId = dbPortId;
        this.rowColIndex = rowColIndex;
        this.fpStrandId=fpStrandId;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.nearModule = nearModule;
        this.nearPortNum = nearPortNum;
        this.nearPatchType = nearPatchType;
        this.fpLocationType = fpLocationType;
        this.fpLocationId = fpLocationId;
        this.fpLocationName = fpLocationName;
        this.fpLocation = fpLocation;
        this.fpEquipmentType = fpEquipmentType;
        this.fpEquipment = fpEquipment;
        this.fpEquipmentId = fpEquipmentId;
        this.fpEquipmentName = fpEquipmentName;
        this.fpAddress = fpAddress;
        this.fpTubeNb = fpTubeNb;
        this.farNearKitSerialNum = farNearKitSerialNum;
        this.farNearModule = farNearModule;
        this.farNearPortNum = farNearPortNum;
        this.fpStrandColor = fpStrandColor;
        this.fpTubeColor = fpTubeColor;
        this.fpStrandName = fpStrandName;
        this.fpTubeId = fpTubeId;
        this.fpTubeName = fpTubeName;
        this.fpFiberId = fpFiberId;
        this.fpFiberName = fpFiberName;
        this.fpKitSerialNum = fpKitSerialNum;
        this.fpModule = fpModule;
        this.fpPortNum = fpPortNum;
        this.fpJunctionId = fpJunctionId;
        this.fpJunctionName = fpJunctionName;
        // NEW ASSIGNMENTS
        this.bpStrandColor = bpStrandColor;
        this.bpTubeColor = bpTubeColor;
        this.bpLocationType = bpLocationType;
        this.bpLocationId = bpLocationId;
        this.bpLocationName = bpLocationName;
        this.bpLocation = bpLocation;
        this.bpEquipmentType = bpEquipmentType;
        this.bpEquipment = bpEquipment;
        this.bpStrandNb = bpStrandNb;
        this.bpTubeNb = bpTubeNb;
        this.bpEquipmentId = bpEquipmentId;
        this.bpEquipmentName = bpEquipmentName;
        this.bpAddress = bpAddress;
        this.bpStatus = bpStatus;
        this.bpStrandId = bpStrandId;
        this.bpStrandName = bpStrandName;
        this.bpTubeId = bpTubeId;
        this.bpTubeName = bpTubeName;
        this.bpFiberId = bpFiberId;
        this.bpFiberName = bpFiberName;
        this.bpJunctionId = bpJunctionId;
        this.bpJunctionName = bpJunctionName;
        this.backModule = backModule;
        this.backKitSerialNum = backKitSerialNum;
        this.backPortNum = backPortNum;
    }

    public String getFpStrandId() {
		return fpStrandId;
	}

	public void setFpStrandId(String fpStrandId) {
		this.fpStrandId = fpStrandId;
	}

	public String getFpStrandNb() {
		return fpStrandNb;
	}

	public void setFpStrandNb(String fpStrandNb) {
		this.fpStrandNb = fpStrandNb;
	}

	public String getFarNearKitSerialNum() {
		return farNearKitSerialNum;
	}

	public void setFarNearKitSerialNum(String farNearKitSerialNum) {
		this.farNearKitSerialNum = farNearKitSerialNum;
	}

	public String getFarNearModule() {
		return farNearModule;
	}

	public void setFarNearModule(String farNearModule) {
		this.farNearModule = farNearModule;
	}

	public String getFarNearPortNum() {
		return farNearPortNum;
	}

	public void setFarNearPortNum(String farNearPortNum) {
		this.farNearPortNum = farNearPortNum;
	}

	public String getBpStrandColor() {
		return bpStrandColor;
	}

	public void setBpStrandColor(String bpStrandColor) {
		this.bpStrandColor = bpStrandColor;
	}

	public String getBpTubeColor() {
		return bpTubeColor;
	}

	public void setBpTubeColor(String bpTubeColor) {
		this.bpTubeColor = bpTubeColor;
	}

	public String getBpLocationType() {
		return bpLocationType;
	}

	public void setBpLocationType(String bpLocationType) {
		this.bpLocationType = bpLocationType;
	}

	public String getBpLocationId() {
		return bpLocationId;
	}

	public void setBpLocationId(String bpLocationId) {
		this.bpLocationId = bpLocationId;
	}

	public String getBpLocationName() {
		return bpLocationName;
	}

	public void setBpLocationName(String bpLocationName) {
		this.bpLocationName = bpLocationName;
	}

	public String getBpLocation() {
		return bpLocation;
	}

	public void setBpLocation(String bpLocation) {
		this.bpLocation = bpLocation;
	}

	public String getBpEquipmentType() {
		return bpEquipmentType;
	}

	public void setBpEquipmentType(String bpEquipmentType) {
		this.bpEquipmentType = bpEquipmentType;
	}

	public String getBpEquipment() {
		return bpEquipment;
	}

	public void setBpEquipment(String bpEquipment) {
		this.bpEquipment = bpEquipment;
	}

	public String getBpStrandNb() {
		return bpStrandNb;
	}

	public void setBpStrandNb(String bpStrandNb) {
		this.bpStrandNb = bpStrandNb;
	}

	public String getBpTubeNb() {
		return bpTubeNb;
	}

	public void setBpTubeNb(String bpTubeNb) {
		this.bpTubeNb = bpTubeNb;
	}

	public String getBpEquipmentId() {
		return bpEquipmentId;
	}

	public void setBpEquipmentId(String bpEquipmentId) {
		this.bpEquipmentId = bpEquipmentId;
	}

	public String getBpEquipmentName() {
		return bpEquipmentName;
	}

	public void setBpEquipmentName(String bpEquipmentName) {
		this.bpEquipmentName = bpEquipmentName;
	}

	public String getBpAddress() {
		return bpAddress;
	}

	public void setBpAddress(String bpAddress) {
		this.bpAddress = bpAddress;
	}

	public String getBpStatus() {
		return bpStatus;
	}

	public void setBpStatus(String bpStatus) {
		this.bpStatus = bpStatus;
	}

	public String getBpStrandId() {
		return bpStrandId;
	}

	public void setBpStrandId(String bpStrandId) {
		this.bpStrandId = bpStrandId;
	}

	public String getBpStrandName() {
		return bpStrandName;
	}

	public void setBpStrandName(String bpStrandName) {
		this.bpStrandName = bpStrandName;
	}

	public String getBpTubeId() {
		return bpTubeId;
	}

	public void setBpTubeId(String bpTubeId) {
		this.bpTubeId = bpTubeId;
	}

	public String getBpTubeName() {
		return bpTubeName;
	}

	public void setBpTubeName(String bpTubeName) {
		this.bpTubeName = bpTubeName;
	}

	public String getBpFiberId() {
		return bpFiberId;
	}

	public void setBpFiberId(String bpFiberId) {
		this.bpFiberId = bpFiberId;
	}

	public String getBpFiberName() {
		return bpFiberName;
	}

	public void setBpFiberName(String bpFiberName) {
		this.bpFiberName = bpFiberName;
	}

	public String getBpJunctionId() {
		return bpJunctionId;
	}

	public void setBpJunctionId(String bpJunctionId) {
		this.bpJunctionId = bpJunctionId;
	}

	public String getBpJunctionName() {
		return bpJunctionName;
	}

	public void setBpJunctionName(String bpJunctionName) {
		this.bpJunctionName = bpJunctionName;
	}

	public String getBackModule() {
		return backModule;
	}

	public void setBackModule(String backModule) {
		this.backModule = backModule;
	}

	public String getBackKitSerialNum() {
		return backKitSerialNum;
	}

	public void setBackKitSerialNum(String backKitSerialNum) {
		this.backKitSerialNum = backKitSerialNum;
	}

	public String getBackPortNum() {
		return backPortNum;
	}

	public void setBackPortNum(String backPortNum) {
		this.backPortNum = backPortNum;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
