package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PURCHASE_REQUEST")

public class PurchaseRequest {
	
	@Id
	@Column(name = "PRQ_ID", nullable = false)
	private String ID;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
		
	@Column(name = "LAST_MODIFICATION_DATE")
	private Timestamp lastmodifiedDate;
	
	@Column(name = "SUPPLIER")
	private String supplier;
	
	@Column(name = "SUPPLIER_ADDRESS")
	private String supplierAddress;
	
	@Column(name = "ORDERED_DATE")
	private Timestamp orderedDate;
	
	@Column(name = "DELIVERY_DATE")
	private Timestamp delivereyDate;
	
	@Column(name = "WAREHOUSE")
	private String WareHouse;
	
	@Column(name = "TOTAL_AMOUNT")
	private float TotalAmount;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private float discAmnt;
	
	@Column(name = "DISCOUNT_PERCENT")
	private float discountPercent;
	
	@Column(name = "PAID_AMOUNT")
	private float paidAmount;
	
	@Column(name = "OUTSTANDING")
	private float prOutstanding;
	
	@Column(name = "STATUS")
	private String prStatus;
	
	@Column(name = "NET_TOTAL_AMOUNT")
	private float netTotal;
	
	@Column(name = "NET_TOTAL_IN_WORDS")
	private String netTotalinWord;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name = "TOTAL_QTY")
	private float TotalQty;
	
	@Column(name = "WAREHOUSE_NAME")
	private String wareName;
	
	@Column(name = "SITE_ID")
	private String siteID;
						  
	public PurchaseRequest() {	
	}
	
	public PurchaseRequest(String ID, Timestamp creationDate , Timestamp lastmodifiedDate, String supplier, String supplierAddress, Timestamp orderedDate, Timestamp delivereyDate, String WareHouse, float TotalAmount, float discAmnt, float discountPercent, float paidAmount, float prOutstanding, String prStatus, float netTotal, String netTotalinWord, String supplierName, float TotalQty, String wareName, String siteID) {
		super();
		this.ID = ID;
		this.creationDate = creationDate;
		this.lastmodifiedDate = lastmodifiedDate;
		this.supplier = supplier;
		this.supplierAddress = supplierAddress;
		this.orderedDate = orderedDate;
		this.delivereyDate = delivereyDate;
		this.WareHouse = WareHouse;
		this.TotalAmount = TotalAmount;
		this.discAmnt = discAmnt;
		this.discountPercent = discountPercent;
		this.paidAmount = paidAmount;
		this.prOutstanding = prOutstanding;
		this.prStatus = prStatus;
		this.netTotal = netTotal;
		this.netTotalinWord = netTotalinWord;
		this.supplierName = supplierName;
		this.TotalQty = TotalQty;
		this.wareName = wareName;
		this.siteID = siteID;
		
	}

	public String getPurchaseReqID() {
		return ID;
	}

	public void setPurchaseReqID(String ID) {
		this.ID = ID;
	}

	public Timestamp getPrcreationDate() {
		return creationDate;
	}

	public void setPrcreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getPrlastModifieddate() {
		return lastmodifiedDate;
	}

	public void setPrlastModifieddate(Timestamp lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
	}

	public String getPrsupplierName() {
		return supplier;
	}

	public void setPrsupplierName(String supplier) {
		this.supplier = supplier;
	}

	public String getPrsupplierAddress() {
		return supplierAddress;
	}

	public void setPrsupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public Timestamp getProrderedDate() {
		return orderedDate;
	}

	public void setProrderedDate(Timestamp orderedDate) {
		this.orderedDate = orderedDate;
	}

	public Timestamp getPrdeliveredDate() {
		return delivereyDate;
	}

	public void setPrdeliveredDate(Timestamp delivereyDate) {
		this.delivereyDate = delivereyDate;
	}

	public String getPrwarehouse() {
		return WareHouse;
	}

	public void setPrwarehouse(String WareHouse) {
		this.WareHouse = WareHouse;
	}

	public float getPrtotalAmount() {
		return TotalAmount;
	}

	public void setPrtotalAmount(float TotalAmount) {
		this.TotalAmount = TotalAmount;
	}

	public float getPrdiscountAmount() {
		return discAmnt;
	}

	public void setPrdiscountAmount(float discAmnt) {
		this.discAmnt = discAmnt;
	}

	public float getPrdiscountPercent() {
		return discountPercent;
	}

	public void setPrdiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}

	public float getPrpaidAmount() {
		return paidAmount;
	}

	public void setPrpaidAmount(float paidAmount) {
		this.paidAmount = paidAmount;
	}

	public float getPrOutstanding() {
		return prOutstanding;
	}

	public void setPrOutstanding(float prOutstanding) {
		this.prOutstanding = prOutstanding;
	}

	public String getPrStatus() {
		return prStatus;
	}

	public void setPrStatus(String prStatus) {
		this.prStatus = prStatus;
	}

	public float getnTotal() {
		return netTotal;
	}

	public void setnTotal(float netTotal) {
		this.netTotal = netTotal;
	}

	public String getPrnettotalinWord() {
		return netTotalinWord;
	}

	public void setPrnettotalinWord(String netTotalinWord) {
		this.netTotalinWord = netTotalinWord;
	}

	public float getPrtotalQty() {
		return TotalQty;
	}

	public void setPrtotalQty(float TotalQty) {
		this.TotalQty = TotalQty;
	}
	public String getPrsuppname() {
		return supplierName;
	}
	
	public void setPrsuppname(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getWareName() {
		return wareName;	
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
		
	}
	public String getSiteID() {
		return siteID;
		
	}
	public void setSiteID(String siteID)
	{
		this.siteID = siteID;
	}
}
