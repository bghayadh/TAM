package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GOODS_RECEIVED")
public class GoodsReceived {
	
	@Id
	@Column(name = "GR_ID", nullable = false)
	private String ID;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
		
	@Column(name = "LAST_MODIFICATION_DATE")
	private Timestamp lastmodifiedDate;
	
	@Column(name = "SUPPLIER")
	private String supplierID;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name = "SUPPLIER_ADDRESS")
	private String supplierAddress;
	
	@Column(name = "ORDERED_DATE")
	private Timestamp orderedDate;
	
	@Column(name = "DELIVERY_DATE")
	private Timestamp delivereyDate;
	
	@Column(name = "WAREHOUSE")
	private String warehouse;
	
	@Column(name = "WAREHOUSE_NAME")
	private String warehouseName;
	
	@Column(name = "SITE_ID")
	private String siteId;
	
	@Column(name = "TOTAL_AMOUNT")
	private float TotalAmount;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private float discAmount;
	
	@Column(name = "DISCOUNT_PERCENT")
	private float discPercent;
	
	@Column(name = "PAID_AMOUNT")
	private float paidAmount;
	
	@Column(name = "OUTSTANDING")
	private float grOutstanding;
	
	@Column(name = "STATUS")
	private String grStatus;
	
	@Column(name = "NET_TOTAL_AMOUNT")
	private float Nettotal;
	
	@Column(name = "NET_TOTAL_IN_WORDS")
	private String NettotalinWord;
	
	@Column(name = "TOTAL_QTY")
	private float TotalQty;
	
	@Column(name = "PO_ID")
	private String grPOid;
	
	@Column(name = "PRQ_ID")
	private String grPRQid;
	
	
	public GoodsReceived() {	
	}

	public GoodsReceived(String ID, Timestamp creationDate, Timestamp lastmodifiedDate,
			String supplierID, String supplierName, String supplierAddress, Timestamp orderedDate,
			Timestamp delivereyDate, String warehouse, String warehouseName, String siteId, float TotalAmount,
			float discAmount, float discPercent, float paidAmount, float grOutstanding, String grStatus,
			float Nettotal, String NettotalinWord, float TotalQty, String grPOid, String grPRQid, String grApproveFlag) {
		super();
		this.ID = ID;
		this.creationDate = creationDate;
		this.lastmodifiedDate = lastmodifiedDate;
		this.supplierID = supplierID;
		this.supplierName = supplierName;
		this.supplierAddress = supplierAddress;
		this.orderedDate = orderedDate;
		this.delivereyDate = delivereyDate;
		this.warehouse = warehouse;
		this.warehouseName = warehouseName;
		this.siteId = siteId;
		this.TotalAmount = TotalAmount;
		this.discAmount = discAmount;
		this.discPercent = discPercent;
		this.paidAmount = paidAmount;
		this.grOutstanding = grOutstanding;
		this.grStatus = grStatus;
		this.Nettotal = Nettotal;
		this.NettotalinWord = NettotalinWord;
		this.TotalQty = TotalQty;
		this.grPOid = grPOid;
		this.grPRQid = grPRQid;
	}

	public String getGoodsReceiveID() {
		return ID;
	}

	public void setGoodsReceiveID(String ID) {
		this.ID = ID;
	}

	public Timestamp getGrcreationDate() {
		return creationDate;
	}

	public void setGrcreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getGrlastModifieddate() {
		return lastmodifiedDate;
	}

	public void setGrlastModifieddate(Timestamp lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
	}

	public String getGrsupplierID() {
		return supplierID;
	}

	public void setGrsupplierID(String supplierID) {
		this.supplierID = supplierID;
	}

	public String getGrsupplierName() {
		return supplierName;
	}

	public void setGrsupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getGrsupplierAddress() {
		return supplierAddress;
	}

	public void setGrsupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public Timestamp getGrorderedDate() {
		return orderedDate;
	}

	public void setGrorderedDate(Timestamp orderedDate) {
		this.orderedDate = orderedDate;
	}

	public Timestamp getGrdeliveredDate() {
		return delivereyDate;
	}

	public void setGrdeliveredDate(Timestamp delivereyDate) {
		this.delivereyDate = delivereyDate;
	}

	public String getGrwarehouse() {
		return warehouse;
	}

	public void setGrwarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getGrwarehouseName() {
		return warehouseName;
	}

	public void setGrwarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getGrsiteId() {
		return siteId;
	}

	public void setGrsiteId(String siteId) {
		this.siteId = siteId;
	}

	public float getGrtotalAmount() {
		return TotalAmount;
	}

	public void setGrtotalAmount(float TotalAmount) {
		this.TotalAmount = TotalAmount;
	}

	public float getGrdiscountAmount() {
		return discAmount;
	}

	public void setGrdiscountAmount(float discAmount) {
		this.discAmount = discAmount;
	}

	public float getGrdiscountPercent() {
		return discPercent;
	}

	public void setGrdiscountPercent(float discPercent) {
		this.discPercent = discPercent;
	}

	public float getGrpaidAmount() {
		return paidAmount;
	}

	public void setGrpaidAmount(float paidAmount) {
		this.paidAmount = paidAmount;
	}

	public float getGrOutstanding() {
		return grOutstanding;
	}

	public void setGrOutstanding(float grOutstanding) {
		this.grOutstanding = grOutstanding;
	}

	public String getGrStatus() {
		return grStatus;
	}

	public void setGrStatus(String grStatus) {
		this.grStatus = grStatus;
	}

	public float getGrnettotal() {
		return Nettotal;
	}

	public void setGrnettotal(float Nettotal) {
		this.Nettotal = Nettotal;
	}

	public String getGrnettotalinWord() {
		return NettotalinWord;
	}

	public void setGrnettotalinWord(String NettotalinWord) {
		this.NettotalinWord = NettotalinWord;
	}

	public float getGrtotalQty() {
		return TotalQty;
	}

	public void setGrtotalQty(float TotalQty) {
		this.TotalQty = TotalQty;
	}

	public String getGrPOid() {
		return grPOid;
	}

	public void setGrPOid(String grPOid) {
		this.grPOid = grPOid;
	}

	public String getGrPRQid() {
		return grPRQid;
	}

	public void setGrPRQid(String grPRQid) {
		this.grPRQid = grPRQid;
	}
	


	
}
