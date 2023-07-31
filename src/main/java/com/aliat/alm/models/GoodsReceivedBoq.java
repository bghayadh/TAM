package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GOODS_RECEIVED_BOQ")
public class GoodsReceivedBoq {
	
	
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
	
	@Column(name = "WAREHOUSE")
	private String warehouse;
	
	@Column(name = "PO_QTY")
	private String poQty;
		
	@Column(name = "PR_QTY")
	private String prQty;
	
	@Column(name = "AR_QTY")
	private String arQty;
	
	@Column(name = "CIP_QTY")
	private String cipQty;
	
	@Column(name = "FAR_QTY")
	private String farQty;
	
	@Id
	@Column(name = "GR_RCV_ITEM_ID", nullable = false)
	private String grItemId;
	
	@Column(name = "GR_STATUS")
	private String grStatus;
	
	@Column(name = "SERIAL_NO")
	private String serialNo;
	
	@Column(name = "serial_obj")
	private String serial_obj;
	
	public GoodsReceivedBoq() {	
	}
	
	
	



	public GoodsReceivedBoq(String itemCode, String itemName, String itemModel, String itemPartNumber, float qty,
			float rate, float discountAmount, float tax1, float netRate, float total, float totalAt, String warehouse,
			String poQty, String prQty, String arQty, String cipQty, String farQty, String grItemId, String grStatus,String serialNo, String serial_obj) {
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
		this.warehouse = warehouse;
		this.poQty = poQty;
		this.prQty = prQty;
		this.arQty = arQty;
		this.cipQty = cipQty;
		this.farQty = farQty;
		this.grItemId = grItemId;
		this.grStatus = grStatus;
		this.serialNo = serialNo;
		this.serial_obj = serial_obj;
	}


	public String getGrStatus() {
		return grStatus;
	}


	public void setGrStatus(String grStatus) {
		this.grStatus = grStatus;
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


	public String getWarehouse() {
		return warehouse;
	}


	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}


	public String getPoQty() {
		return poQty;
	}


	public void setPoQty(String poQty) {
		this.poQty = poQty;
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
	
	public String getGrItemId() {
		return grItemId;
	}

	public void setGrItemId(String grItemId) {
		this.grItemId = grItemId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}


	public void setSerial_obj(String serial_obj) {
		this.serial_obj = serial_obj;
	}
	public String getSerial_obj() {
		return serial_obj;
	}





	
}


