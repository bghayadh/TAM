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

    public WorkOrderTask() {
    }

    public WorkOrderTask(
            String woTaskId,
            String patchingId,
            Timestamp creationDate,
            Timestamp lastModifiedDate,
            Timestamp completionDate,
            String taskType,
            String taskStatus,
            String dbId,
            String dbPortId,
            Integer rowColIndex,
            Integer rowNumber,
            Integer columnNumber,
            String nearModule,
            String nearPortNum,
            String nearPatchType,
            String fpLocationType,
            String fpLocationId,
            String fpLocationName,
            String fpLocation,
            String fpEquipmentType,
            String fpEquipment,
            String fpEquipmentId,
            String fpEquipmentName,
            String fpAddress,
            String fpTubeNb,
            String fpStrandColor,
            String fpTubeColor,
            String fpStrandName,
            String fpTubeId,
            String fpTubeName,
            String fpFiberId,
            String fpFiberName,
            String fpKitSerialNum,
            String fpModule,
            String fpPortNum,
            String fpJunctionId,
            String fpJunctionName) {

        this.woTaskId = woTaskId;
        this.patchingId = patchingId;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.completionDate = completionDate;
        this.taskType = taskType;
        this.taskStatus = taskStatus;
        this.dbId = dbId;
        this.dbPortId = dbPortId;
        this.rowColIndex = rowColIndex;
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
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
