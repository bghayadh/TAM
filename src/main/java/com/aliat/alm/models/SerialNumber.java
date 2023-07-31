package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SERIAL_NUMBER")

public class SerialNumber {
	@Id
	@Column(name = "SERIAL_NUMBER", nullable = false)
	private String serialNumber;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
		
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifieddate;
	
	@Column(name = "SUPPLIER_ID")
	private String supplierId;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name = "ITEM_CODE")
	private String itemCode;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "PO_ID")
	private String poId;
	
	@Column(name = "GR_ID")
	private String grId;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;
	
	@Column(name = "ITEM_PARTNO")
	private String itemPart;
	
	@Column(name = "POITEM_ID")
	private String poItemId;
	
	@Column(name = "GRITEM_ID")
	private String grItemId;
	
	@Column(name = "SERIAL_NUM_ID")
	private String serialNumID;
	
	@Column(name = "STATUS")
	private String ordStatus;
	
	
	public SerialNumber() {	
	}
	public SerialNumber(String serialNumber, Timestamp creationDate,  Timestamp lastModifieddate, String supplierId, String supplierName, 
		String itemCode, String itemName, String poId, String grId, String itemModel, String itemPart, String poItemId, String grItemId, String serialNumID	, String ordStatus) {
		super();
		this.serialNumber = serialNumber;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.itemCode = itemCode ;
		this.itemName = itemName;
		this.poId = poId;
		this.grId = grId;
		this.itemModel = itemModel;
		this.itemPart = itemPart;
		this.poItemId = poItemId;
		this.grItemId = grItemId;
		this.serialNumID=serialNumID;
		this.ordStatus=ordStatus;
		
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public Timestamp getLastModifieddate() {
		return lastModifieddate;
	}
	public void setLastModifieddate(Timestamp lastModifieddate) {
		this.lastModifieddate = lastModifieddate;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getPoId() {
		return poId;
	}
	public void setPoId(String poId) {
		this.poId = poId;
	}
	public String getGrId() {
		return grId;
	}
	public void setGrId(String grId) {
		this.grId = grId;
	}
	public String getItemModel() {
		return itemModel;
	}
	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}
	public String getItemPart() {
		return itemPart;
	}
	public void setItemPart(String itemPart) {
		this.itemPart = itemPart;
	}
	public String getPoItemId() {
		return poItemId;
	}
	public void setPoItemId(String poItemId) {
		this.poItemId = poItemId;
	}
	public String getGrItemId() {
		return grItemId;
	}
	public void setGrItemId(String grItemId) {
		this.grItemId = grItemId;
	}
	public String getSerialNumID() {
		return serialNumID;
	}
	public void setSerialNumID(String serialNumID) {
		this.serialNumID = serialNumID;
	}
	public String getOrdStatus() {
		return ordStatus;
	}
	public void setOrdStatus(String ordStatus) {
		this.ordStatus = ordStatus;
	}
	
	
}
