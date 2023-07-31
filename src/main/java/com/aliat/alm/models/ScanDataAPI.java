package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import oracle.sql.TIMESTAMP;

@Entity
@Table(name = "WAREHOUSE_SCAN_ONSITE")


public class ScanDataAPI {

	@Id
	@Column(name="SCAN_ID")
	private int scanId;
	
	@Column(name = "WARE_ID", nullable = false)
	private String wareId;
	
	@Column(name="ITEM_CODE")
    private String itemCode;
	
	@Column(name="BARCODE")
	private String barcode;
	
	@Column(name="SERIAL_NUMBER")
    private String serialNumber;
	
	@Column(name="SERIAL_CHECK")
    private String serialCheck;
	
	@Column(name="QUANTITY")
	private int quantity;
	
	@Column(name="PROCESS_STATUS")
	private String processStatus;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="SCAN_DATE")
	private Timestamp scanDate;
	
	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;
	
	@Column(name="USER_MOB_PHONE")
	private String userMobPhone;
	
	@Column(name="ITEM_MODEL")
	private String itemModel;
	
	@Column(name="ITEM_PART_NUMBER")
	private String itemPartNumber;
	
	@Column(name="ITEM_NAME")
	private String itemNAME;

	public ScanDataAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScanDataAPI(String wareId, String itemCode, String barcode, String serialNumber, String serialCheck,
			int quantity, String processStatus, String userId, Timestamp scanDate, Timestamp updateDate,
			String userMobPhone, String itemModel, String itemPartNumber, int scanId, String itemNAME) {
		super();
		this.wareId = wareId;
		this.itemCode = itemCode;
		this.barcode = barcode;
		this.serialNumber = serialNumber;
		this.serialCheck = serialCheck;
		this.quantity = quantity;
		this.processStatus = processStatus;
		this.userId = userId;
		this.scanDate = scanDate;
		this.updateDate = updateDate;
		this.userMobPhone = userMobPhone;
		this.itemModel = itemModel;
		this.itemPartNumber = itemPartNumber;
		this.scanId = scanId;
		this.itemNAME = itemNAME;
	}

	public String getWareId() {
		return wareId;
	}

	public void setWareId(String wareId) {
		this.wareId = wareId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSerialCheck() {
		return serialCheck;
	}

	public void setSerialCheck(String serialCheck) {
		this.serialCheck = serialCheck;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getScanDate() {
		return scanDate;
	}

	public void setScanDate(Timestamp scanDate) {
		this.scanDate = scanDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserMobPhone() {
		return userMobPhone;
	}

	public void setUserMobPhone(String userMobPhone) {
		this.userMobPhone = userMobPhone;
	}

	public String getItemModel() {
		return itemModel;
	}

	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}

	public String getItemPartNumber() {
		return itemPartNumber;
	}

	public void setItemPartNumber(String itemPartNumber) {
		this.itemPartNumber = itemPartNumber;
	}

	public int getScanId() {
		return scanId;
	}

	public void setScanId(int scanId) {
		this.scanId = scanId;
	}

	public String getItemNAME() {
		return itemNAME;
	}

	public void setItemNAME(String itemNAME) {
		this.itemNAME = itemNAME;
	}

	
	
	
}
