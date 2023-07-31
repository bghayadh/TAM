package com.aliat.alm.models;

import java.sql.Timestamp;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSET_LIFE_CYCLE")
public class AssetLifeCycle_Ledger {
	
	@Id
	@Column(name = "ALC_ID", nullable = false)
	private String alcID;
	
	@Column(name = "CREATION_DATE")
	private String creationDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private String lastModifiedDate;

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
	private Float valuationRate;
	
	@Column(name = "UOM")
	private String UOM;
	
	@Column(name = "PROJECT")
	private String project;
	
	@Column(name = "ACTUAL_QTY")
//	private float actualQty;
	private Float actualQty;
	
	
	@Column(name = "SERIAL_NB")
	private String serialNB;

	@Column(name = "INCOMING_RATE")
	private Float inComingRate;
	
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
	private Float accumulatedDepreciation;
	
	@Column(name = "DAILY_DEPRECIATION")
	private String dailyDepreciation;
	
	@Column(name = "NET_PRICE")
	private Float netPrice;

	@Column(name = "BALANCE_QTY")
	private Float balanceQty;
	
	@Column(name = "IN_VALUE")
	private float inValue;
	
	@Column(name = "OUT_VALUE")
	private float outValue;
	
	@Column(name = "IS_OPENING" )
	private float isOpening;
	
	@Column(name = "NET_BALANCE_VALUE")
	private Float netBalanceValue;
	
	@Column(name = "BALANCE_VALUE")
	private Float balanceValue;
	
	@Column(name = "IN_NET_VALUE")
	private float inNetValue;
	
	@Column(name = "OUTGOING_RATE")
	private Float outGoingRate;
	
	@Column(name = "TOTAL_ACCUMULATED_DEPRECIATION")
	private float tAccumulatedDepreciation;

	public AssetLifeCycle_Ledger() {
		
		
	}

	public AssetLifeCycle_Ledger(String alcID, String creationDate, String lastModifiedDate, String modifiedBy,
			String owner, String docstatus, String warehouse, String wareName, String siteId, String supplier,
			String supplierName, Float valuationRate, String uOM, String project, Float actualQty, String serialNB,
			Float inComingRate, String voucherType, String voucherDetails, String voucherNB, String itemCode,
			String itemName, String itemModel, String itemPartNo, Float accumulatedDepreciation,
			String dailyDepreciation, Float netPrice, Float balanceQty, float inValue, float outValue, float isOpening,
			Float netBalanceValue, Float balanceValue, float inNetValue, Float outGoingRate,
			float tAccumulatedDepreciation) {
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

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
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

	public Float getValuationRate() {
		return valuationRate;
	}

	public void setValuationRate(Float valuationRate) {
		this.valuationRate = valuationRate;
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

	public Float getActualQty() {
		return actualQty;
	}

	public void setActualQty(Float actualQty) {
		this.actualQty = actualQty;
	}

	public String getSerialNB() {
		return serialNB;
	}

	public void setSerialNB(String serialNB) {
		this.serialNB = serialNB;
	}

	public Float getInComingRate() {
		return inComingRate;
	}

	public void setInComingRate(Float inComingRate) {
		this.inComingRate = inComingRate;
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

	public Float getAccumulatedDepreciation() {
		return accumulatedDepreciation;
	}

	public void setAccumulatedDepreciation(Float accumulatedDepreciation) {
		this.accumulatedDepreciation = accumulatedDepreciation;
	}

	public String getDailyDepreciation() {
		return dailyDepreciation;
	}

	public void setDailyDepreciation(String dailyDepreciation) {
		this.dailyDepreciation = dailyDepreciation;
	}

	public Float getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(Float netPrice) {
		this.netPrice = netPrice;
	}

	public Float getBalanceQty() {
		return balanceQty;
	}

	public void setBalanceQty(Float balanceQty) {
		this.balanceQty = balanceQty;
	}

	public float getInValue() {
		return inValue;
	}

	public void setInValue(float inValue) {
		this.inValue = inValue;
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

	public Float getNetBalanceValue() {
		return netBalanceValue;
	}

	public void setNetBalanceValue(Float netBalanceValue) {
		this.netBalanceValue = netBalanceValue;
	}

	public Float getBalanceValue() {
		return balanceValue;
	}

	public void setBalanceValue(Float balanceValue) {
		this.balanceValue = balanceValue;
	}

	public float getInNetValue() {
		return inNetValue;
	}

	public void setInNetValue(float inNetValue) {
		this.inNetValue = inNetValue;
	}

	public Float getOutGoingRate() {
		return outGoingRate;
	}

	public void setOutGoingRate(Float outGoingRate) {
		this.outGoingRate = outGoingRate;
	}

	public float gettAccumulatedDepreciation() {
		return tAccumulatedDepreciation;
	}

	public void settAccumulatedDepreciation(float tAccumulatedDepreciation) {
		this.tAccumulatedDepreciation = tAccumulatedDepreciation;
	}

	

}
