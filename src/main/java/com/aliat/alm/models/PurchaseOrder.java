package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PURCHASE_ORDER")
public class PurchaseOrder {

	private static PurchaseOrder instance = null;

	@Id
	@Column(name = "PO_ID", nullable = false)
	private String ID;

	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@Column(name = "LAST_MODIFICATION_DATE")
	private Timestamp lastmodifiedDate;

	@Column(name = "SUPPLIER")
	private String supplier;

	@Column(name = "SUPPLIER_NAME")
	private String supplierName;

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
	private float discAmount;

	@Column(name = "DISCOUNT_PERCENT")
	private float discountPercent;

	@Column(name = "PAID_AMOUNT")
	private float paidAmount;

	@Column(name = "OUTSTANDING")
	private float ordOutstanding;

	@Column(name = "STATUS")
	private String ordStatus;

	@Column(name = "NET_TOTAL_AMOUNT")
	private float netTotal;

	@Column(name = "PONUMBER")
	private String PoNo;

	@Column(name = "TOTAL_QTY")
	private float TotalQty;

	@Column(name = "PRQ_ID")
	private String ordPRQid;

	@Column(name = "WAREHOUSE_NAME")
	private String wareName;

	@Column(name = "SITE_ID")
	private String siteID;

	public PurchaseOrder() {
	}


	public PurchaseOrder(String ID, Timestamp creationDate, Timestamp lastmodifiedDate, String supplier,
			String supplierName, String supplierAddress, Timestamp orderedDate, Timestamp delivereyDate,
			String WareHouse, float TotalAmount, float discAmount, float discountPercent, float paidAmount,
			float ordOutstanding, String ordStatus, float netTotal, String PoNo, float TotalQty, String ordPRQid,
			String wareName, String siteID) {
		super();
		this.ID = ID;
		this.creationDate = creationDate;
		this.lastmodifiedDate = lastmodifiedDate;
		this.supplier = supplier;
		this.supplierName = supplierName;
		this.supplierAddress = supplierAddress;
		this.orderedDate = orderedDate;
		this.delivereyDate = delivereyDate;
		this.WareHouse = WareHouse;
		this.TotalAmount = TotalAmount;
		this.discAmount = discAmount;
		this.discountPercent = discountPercent;
		this.paidAmount = paidAmount;
		this.ordOutstanding = ordOutstanding;
		this.ordStatus = ordStatus;
		this.netTotal = netTotal;
		this.PoNo = PoNo;
		this.TotalQty = TotalQty;
		this.ordPRQid = ordPRQid;
		this.wareName = wareName;
		this.siteID = siteID;
	}

	public String getPurchaseOrdID() {
		return ID;
	}

	public void setPurchaseOrdID(String ID) {
		this.ID = ID;
	}

	public Timestamp getOrdcreationDate() {
		return creationDate;
	}

	public void setOrdcreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getOrdlastModifieddate() {
		return lastmodifiedDate;
	}

	public void setOrdlastModifieddate(Timestamp lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
	}

	public String getOrdsupplierId() {
		return supplier;
	}

	public void setOrdsupplierId(String supplier) {
		this.supplier = supplier;
	}

	public String getOrdSuppName() {
		return supplierName;
	}

	public void setOrdSuppName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getOrdsupplierAddress() {
		return supplierAddress;
	}

	public void setOrdsupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public Timestamp getOrdorderedDate() {
		return orderedDate;
	}

	public void setOrdorderedDate(Timestamp orderedDate) {
		this.orderedDate = orderedDate;
	}

	public Timestamp getOrddeliveredDate() {
		return delivereyDate;
	}

	public void setOrddeliveredDate(Timestamp delivereyDate) {
		this.delivereyDate = delivereyDate;
	}

	public String getOrdwarehouse() {
		return WareHouse;
	}

	public void setOrdwarehouse(String WareHouse) {
		this.WareHouse = WareHouse;
	}

	public float getOrdtotalAmount() {
		return TotalAmount;
	}

	public void setOrdtotalAmount(float TotalAmount) {
		this.TotalAmount = TotalAmount;
	}

	public float getOrddiscountAmount() {
		return discAmount;
	}

	public void setOrddiscountAmount(float discAmount) {
		this.discAmount = discAmount;
	}

	public float getOrddiscountPercent() {
		return discountPercent;
	}

	public void setOrddiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}

	public float getOrdpaidAmount() {
		return paidAmount;
	}

	public void setOrdpaidAmount(float paidAmount) {
		this.paidAmount = paidAmount;
	}

	public float getOrdOutstanding() {
		return ordOutstanding;
	}

	public void setOrdOutstanding(float ordOutstanding) {
		this.ordOutstanding = ordOutstanding;
	}

	public String getOrdStatus() {
		return ordStatus;
	}

	public void setOrdStatus(String ordStatus) {
		this.ordStatus = ordStatus;
	}

	public float getOrdnettotal() {
		return netTotal;
	}

	public void setOrdnettotal(float netTotal) {
		this.netTotal = netTotal;
	}

	public String getOrdPonumber() {
		return PoNo;
	}

	public void setOrdPonumber(String PoNo) {
		this.PoNo = PoNo;
	}

	public float getOrdtotalQty() {
		return TotalQty;
	}

	public void setOrdtotalQty(float TotalQty) {
		this.TotalQty = TotalQty;
	}

	public String getOrdPRQid() {
		return ordPRQid;
	}

	public void setOrdPRQid(String ordPRQid) {
		this.ordPRQid = ordPRQid;
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

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}
}
