package com.aliat.alm.models;

import java.io.Serializable;
import java.math.BigDecimal;													  
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSET_LIFE_CYCLE")
public class AssetLifeCycle {
	@Id
	@Column(name = "ALC_ID", nullable = false)
	private String alcID;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "OWNER")
	private String owner;
	
	@Column(name = "DOCSTATUS")
	private String docstatus;
	
	@Column(name = "WAREHOUSE")
	private String warehouse;
	
	@Column(name = "WARE_NAME")
	private String wareName;
	
	@Column(name = "SITE_ID")
	private String siteId;
	
	@Column(name = "SUPPLIER_ID")
	private String Supplier;
	
	@Column(name = "SUPPLIER_NAME")
	private String SupplierName;	
	
	@Column(name = "VALUATION_RATE")
	private BigDecimal valuationRate;	
	
	@Column(name = "UOM")
	private String UOM;
	
	@Column(name = "PROJECT")
	private String project;
	
	@Column(name = "ACTUAL_QTY")
	private BigDecimal actualQty;
	
	@Column(name = "SERIAL_NB")
	private String serialNB;

	@Column(name = "INCOMING_RATE")
	private BigDecimal inComingRate;
	
	@Column(name = "VOUCHER_TYPE")
	private String voucherType;

	@Column(name = "VOUCHER_DETAIL")
	private String voucherDetails;
	
	@Column(name = "VOUCHER_NB")
	private String voucherNB;
	
	@Column(name = "ITEM_CODE")
	private String itemCode;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;
	
	@Column(name = "ITEM_PART_NO")
	private String itemPartNo;
	
	@Column(name = "ACCUMULATED_DEPRECIATION")
	private BigDecimal accumulatedDepreciation;
	
	@Column(name = "DAILY_DEPRECIATION")
	private BigDecimal dailyDepreciation;
	
	@Column(name = "NET_PRICE")
	private BigDecimal netPrice;


	@Column(name = "BALANCE_QTY")
	private BigDecimal balanceQty;
	
	@Column(name = "IN_VALUE")
	private BigDecimal inValue;
	
	@Column(name = "OUT_VALUE")
	private float outValue;
	
	@Column(name = "IS_OPENING" )
	private float isOpening;
	
	@Column(name = "NET_BALANCE_VALUE")
	private BigDecimal netBalanceValue;
	
	@Column(name = "BALANCE_VALUE")
	private BigDecimal balanceValue;
	
	@Column(name = "IN_NET_VALUE")
	private BigDecimal inNetValue;
	
	@Column(name = "OUTGOING_RATE")
	private BigDecimal outGoingRate;
	
	@Column(name = "TOTAL_ACCUMULATED_DEPRECIATION")
	private BigDecimal tAccumulatedDepreciation;
	
	
	
	public AssetLifeCycle() {
	}



	public AssetLifeCycle(String alcID, Timestamp creationDate, Timestamp lastModifiedDate, String modifiedBy,
			String owner, String docstatus, String warehouse, String wareName, String siteId, String supplier,
			String supplierName, BigDecimal valuationRate, String uOM, String project, BigDecimal actualQty, String serialNB,
			BigDecimal inComingRate, String voucherType, String voucherDetails, String voucherNB, String itemCode,
			String itemName, String itemModel, String itemPartNo, BigDecimal accumulatedDepreciation,
			BigDecimal dailyDepreciation, BigDecimal netPrice, BigDecimal balanceQty, BigDecimal inValue, float outValue, float isOpening,
			BigDecimal netBalanceValue, BigDecimal balanceValue, BigDecimal inNetValue, BigDecimal outGoingRate,
			BigDecimal tAccumulatedDepreciation) {
		super();
		this.alcID = alcID;
		this.creationDate = creationDate;
		this.lastModifiedDate = lastModifiedDate;
		this.modifiedBy = modifiedBy;
		this.owner = owner;
		this.docstatus = docstatus;
		this.warehouse = warehouse;
		this.wareName = wareName;
		this.siteId = siteId;
		Supplier = supplier;
		SupplierName = supplierName;
		this.valuationRate = valuationRate;
		UOM = uOM;
		this.project = project;
		this.actualQty = actualQty;
		this.serialNB = serialNB;
		this.inComingRate = inComingRate;
		this.voucherType = voucherType;
		this.voucherDetails = voucherDetails;
		this.voucherNB = voucherNB;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemModel = itemModel;
		this.itemPartNo = itemPartNo;
		this.accumulatedDepreciation = accumulatedDepreciation;
		this.dailyDepreciation = dailyDepreciation;
		this.netPrice = netPrice;
		this.balanceQty = balanceQty;
		this.inValue = inValue;
		this.outValue = outValue;
		this.isOpening = isOpening;
		this.netBalanceValue = netBalanceValue;
		this.balanceValue = balanceValue;
		this.inNetValue = inNetValue;
		this.outGoingRate = outGoingRate;
		this.tAccumulatedDepreciation = tAccumulatedDepreciation;
	}


	public String getAlcID() {
		return alcID;
	}



	public void setAlcID(String alcID) {
		this.alcID = alcID;
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



	public String getModifiedBy() {
		return modifiedBy;
	}



	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}



	public String getOwner() {
		return owner;
	}



	public void setOwner(String owner) {
		this.owner = owner;
	}



	public String getDocstatus() {
		return docstatus;
	}



	public void setDocstatus(String docstatus) {
		this.docstatus = docstatus;
	}



	public String getWarehouse() {
		return warehouse;
	}



	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}



	public String getWareName() {
		return wareName;
	}



	public void setWareName(String wareName) {
		this.wareName = wareName;
	}



	public String getSiteId() {
		return siteId;
	}



	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}



	public String getSupplier() {
		return Supplier;
	}



	public void setSupplier(String supplier) {
		Supplier = supplier;
	}



	public String getSupplierName() {
		return SupplierName;
	}



	public void setSupplierName(String supplierName) {
		SupplierName = supplierName;
	}



	public Serializable getValuationRate() {
		return valuationRate;
	}



	public void setValuationRate(BigDecimal valRate) {
		this.valuationRate = valRate;
	}



	public String getUOM() {
		return UOM;
	}



	public void setUOM(String uOM) {
		UOM = uOM;
	}



	public String getProject() {
		return project;
	}



	public void setProject(String project) {
		this.project = project;
	}



	public BigDecimal getActualQty() {
		return actualQty;
	}



	public void setActualQty(BigDecimal actQty) {
		this.actualQty = actQty;
	}



	public String getSerialNB() {
		return serialNB;
	}



	public void setSerialNB(String serialNB) {
		this.serialNB = serialNB;
	}



	public BigDecimal getInComingRate() {
		return inComingRate;
	}



	public void setInComingRate(BigDecimal inComRate) {
		this.inComingRate = inComRate;
	}



	public String getVoucherType() {
		return voucherType;
	}



	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}



	public String getVoucherDetails() {
		return voucherDetails;
	}



	public void setVoucherDetails(String voucherDetails) {
		this.voucherDetails = voucherDetails;
	}



	public String getVoucherNB() {
		return voucherNB;
	}



	public void setVoucherNB(String voucherNB) {
		this.voucherNB = voucherNB;
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



	public String getItemPartNo() {
		return itemPartNo;
	}



	public void setItemPartNo(String itemPartNo) {
		this.itemPartNo = itemPartNo;
	}



	public BigDecimal getAccumulatedDepreciation() {
		return accumulatedDepreciation;
	}



	public void setAccumulatedDepreciation(BigDecimal accumulatedDepreciation) {
		this.accumulatedDepreciation = accumulatedDepreciation;
	}



	public BigDecimal getDailyDepreciation() {
		return dailyDepreciation;
	}



	public void setDailyDepreciation(BigDecimal dailyDepreciation) {
		this.dailyDepreciation = dailyDepreciation;
	}



		public BigDecimal getNetPrice() {
		return netPrice;
	}



	public void setNetPrice(BigDecimal netPrice2) {
		this.netPrice = netPrice2;
	}



	public BigDecimal getBalanceQty() {
		return balanceQty;
	}


	public void setBalanceQty(BigDecimal balQty) {
		this.balanceQty = balQty;
	}



	public BigDecimal getInValue() {
		return inValue;
	}



	public void setInValue(BigDecimal inVal) {
		this.inValue = inVal;
	}



	public float getOutValue() {
		return outValue;
	}



	public void setOutValue(float outValue) {
		this.outValue = outValue;
	}



	public float getIsOpening() {
		return isOpening;
	}



	public void setIsOpening(float isOpening) {
		this.isOpening = isOpening;
	}



	public BigDecimal getNetBalanceValue() {
		return netBalanceValue;
	}



	public void setNetBalanceValue(BigDecimal netBalVal) {
		this.netBalanceValue = netBalVal;
	}



	public BigDecimal getBalanceValue() {
		return balanceValue;
	}



	public void setBalanceValue(BigDecimal balVal) {
		this.balanceValue = balVal;
	}



	public BigDecimal getInNetValue() {
		return inNetValue;
	}




	public void setInNetValue(BigDecimal inNetVal) {
		this.inNetValue = inNetVal;
	}



	public BigDecimal getOutGoingRate() {
		return outGoingRate;
	}



	public void setOutGoingRate(BigDecimal outGoingRate) {
		this.outGoingRate = outGoingRate;
	}



	public BigDecimal gettAccumulatedDepreciation() {
		return tAccumulatedDepreciation;
	}



	
	public void settAccumulatedDepreciation(BigDecimal totAccumDep) {
		this.tAccumulatedDepreciation = totAccumDep;
	}



	@Override
	public String toString() {
		return "AssetLifeCycle [alcID=" + alcID + ", creationDate=" + creationDate + ", lastModifiedDate="
				+ lastModifiedDate + ", modifiedBy=" + modifiedBy + ", owner=" + owner + ", docstatus=" + docstatus
				+ ", warehouse=" + warehouse + ", wareName=" + wareName + ", siteId=" + siteId + ", Supplier="
				+ Supplier + ", SupplierName=" + SupplierName + ", valuationRate=" + valuationRate + ", UOM=" + UOM
				+ ", project=" + project + ", actualQty=" + actualQty + ", serialNB=" + serialNB + ", inComingRate="
				+ inComingRate + ", voucherType=" + voucherType + ", voucherDetails=" + voucherDetails + ", voucherNB="
				+ voucherNB + ", itemCode=" + itemCode + ", itemName=" + itemName + ", itemModel=" + itemModel
				+ ", itemPartNo=" + itemPartNo + ", accumulatedDepreciation=" + accumulatedDepreciation
				+ ", dailyDepreciation=" + dailyDepreciation + ", netPrice=" + netPrice + ", balanceQty=" + balanceQty
				+ ", inValue=" + inValue + ", outValue=" + outValue + ", isOpening=" + isOpening + ", netBalanceValue="
				+ netBalanceValue + ", balanceValue=" + balanceValue + ", inNetValue=" + inNetValue + ", outGoingRate="
				+ outGoingRate + ", tAccumulatedDepreciation=" + tAccumulatedDepreciation + "]";
	}

	
}
