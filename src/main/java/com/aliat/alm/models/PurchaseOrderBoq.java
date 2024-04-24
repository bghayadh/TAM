package com.aliat.alm.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "PURCHASE_ORDER_Item")
public class PurchaseOrderBoq {
	
	@Column(name = "ITEM_CODE")
	private String itemCode;
	
	@Column(name = "ITEM_Name")
	private String itemName;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;
	
	@Column(name = "ITEM_PART_NUMBER")
	private String itemPartNumber;
	
	@Column(name = "QTY")
	private float qty;
	
	@Column(name = "ITEM_BARCODE")
	private String poBarCode;
	 
	@Column(name = "RATE")
	private float rate;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private float discountAmount;
	 
	@Column(name = "TAX1")
	private float tax1;
	
	@Column(name = "NET_RATE")
	private float netRate;
	
	@Column(name = "TOTAL")
	private float total;
	
	@Column(name = "TOTAL_AT")
	private float totalAt;
	
	@Column(name = "GR_QTY")
	private String grQty;
		
	@Column(name = "PR_QTY")
	private String prQty;
	
	@Column(name = "AR_QTY")
	private String arQty;
	
	@Column(name = "CIP_QTY")
	private String cipQty;
	
	@Column(name = "FAR_QTY")
	private String farQty;
	
	@Id
	@Column(name = "PR_ORD_ITEM_ID", nullable = false)
	private String pordItemId;
	
	@Column(name = "PO_ITEM_STATUS")
	private String poItemStatus;
	
	@Column(name = "SERIAL_NO")
	private String serialNo;
	
	@Column(name = "serial_obj")
	private String serial_obj;
	
public PurchaseOrderBoq() {	
	
}

public PurchaseOrderBoq(String itemCode, String itemName, String itemModel, String itemPartNumber, float qty,
		float rate, float discountAmount, float tax1, float netRate, float total, float totalAt, String grQty,
		String prQty, String arQty, String cipQty, String farQty, String pordItemId, String poItemStatus, String serialNo, String prBarCode,
		String serial_obj) {
	super();
	this.itemCode = itemCode;
	this.itemName = itemName;
	this.itemModel = itemModel;
	this.itemPartNumber = itemPartNumber;
	this.qty = qty;
	this.rate = rate;
	this.discountAmount = discountAmount;
	this.tax1 = tax1;
	this.netRate = netRate;
	this.total = total;
	this.totalAt = totalAt;
	this.grQty = grQty;
	this.prQty = prQty;
	this.arQty = arQty;
	this.cipQty = cipQty;
	this.farQty = farQty;
	this.pordItemId = pordItemId;
	this.poItemStatus = poItemStatus;
	this.serialNo = serialNo;
	this.serial_obj = serial_obj;
	
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


public String getPoItemStatus() {
	return poItemStatus;
}

public void setpoItemStatus(String poItemStatus) {
	this.poItemStatus = poItemStatus;
}

public void setItemName(String itemName) {
	this.itemName = itemName;
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


public float getQty() {
	return qty;
}


public void setQty(float qty) {
	this.qty = qty;
}


public float getRate() {
	return rate;
}


public void setRate(float rate) {
	this.rate = rate;
}


public float getDiscountAmount() {
	return discountAmount;
}


public void setDiscountAmount(float discountAmount) {
	this.discountAmount = discountAmount;
}


public float getTax1() {
	return tax1;
}


public void setTax1(float tax1) {
	this.tax1 = tax1;
}


public float getNetRate() {
	return netRate;
}


public void setNetRate(float netRate) {
	this.netRate = netRate;
}


public float getTotal() {
	return total;
}


public void setTotal(float total) {
	this.total = total;
}


public float getTotalAt() {
	return totalAt;
}


public void setTotalAt(float totalAt) {
	this.totalAt = totalAt;
}


public String getGrQty() {
	return grQty;
}


public void setGrQty(String grQty) {
	this.grQty = grQty;
}


public String getPrQty() {
	return prQty;
}


public void setPrQty(String prQty) {
	this.prQty = prQty;
}


public String getArQty() {
	return arQty;
}


public void setArQty(String arQty) {
	this.arQty = arQty;
}


public String getCipQty() {
	return cipQty;
}


public void setCipQty(String cipQty) {
	this.cipQty = cipQty;
}


public String getFarQty() {
	return farQty;
}


public void setFarQty(String farQty) {
	this.farQty = farQty;
}


public String getPordItemId() {
	return pordItemId;
}


public void setPordItemId(String pordItemId) {
	this.pordItemId = pordItemId;
}


public String getSerialNo() {
	return serialNo;
}

public void setSerialNo(String serialNo) {
	this.serialNo = serialNo;
}

public String getpoBarCode() {
	return poBarCode;
}


public void setpoBarCode(String poBarCode) {
	this.poBarCode = poBarCode;
}

public void setSerial_obj(String serial_obj) {
	this.serial_obj = serial_obj;
}
public String getSerial_obj() {
	return serial_obj;
}
}