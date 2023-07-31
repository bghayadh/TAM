package com.aliat.alm.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name = "WORK_ORDER_DESTINATION_SERIAL")
public class WorkOrderDestinationSerial {

	@Id
	@Column(name = "ID", nullable = false)
	private String id; 

	@Column(name = "WOD_ID")
	private String wodID;
	
	@Column(name = "ITEM_CODE")
	private String itemCode;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;
	
	@Column(name = "ITEM_PART_NUMBER")
	private String itemPartNumber;
	
	@Column(name = "SERIAL_NO")
	private String serialNum;
	

	@Column(name = "RECONCILED")
	private char reconciled;
	
	@Column(name = "WO_ID")
	private String woId;

	public  WorkOrderDestinationSerial() {
		
		
	}


	public WorkOrderDestinationSerial(String wodID,String itemCode, String itemModel, String itemPartNumber, String serialNum,
			char reconciled, String woId, String id) {
		super();
		
		this.wodID = wodID;
		this.itemCode = itemCode;
		this.itemModel = itemModel;
		this.itemPartNumber = itemPartNumber;
		this.serialNum = serialNum;
		this.reconciled = reconciled;
		this.woId=woId;
		this.id = id;
	}

	public String getWodID() {
		return wodID;
	}


	public void setWodID(String wodID) {
		this.wodID = wodID;
	}


	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public char getReconciled() {
		return reconciled;
	}

	public void setReconciled(char reconciled) {
		this.reconciled = reconciled;
	}

	public String getWoId() {
		return woId;
	}


	public void setWoId(String woId) {
		this.woId = woId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
