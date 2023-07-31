package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSET_LIFE_CYCLE")
public class AssetLifeCycle_Balance {

	@Id
	@Column(name = "ALC_ID", nullable = false)
	private String alcID;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	
	
	@Column(name = "WAREHOUSE")
	private String warehouse;
	
	@Column(name = "SITE_ID")
	private String siteID;
	
	@Column(name = "WARE_NAME")
	private String wareName;
	
	@Column(name = "ITEM_CODE")
	private String itemCode;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;
	
	@Column(name = "ITEM_PART_NO")
	private String itemPartNO;
	
	@Column(name = "SUPPLIER_ID")
	private String Supplier;
	
	@Column(name = "VOUCHER_TYPE")
	private String voucherType;
	
	@Column(name = "VOUCHER_NB")
	private String voucherNB;
	
	@Column(name = "VALUATION_RATE")
	private Float valuationRate;
	
	@Column(name = "UOM")
	private String UOM;
	
	@Column(name = "ACCUMULATED_DEPRECIATION")
	private Float accumulatedDepreciation;
		
	@Column(name = "BALANCE_QTY")
	private Float balanceQty;
	
	@Column(name = "IN_VALUE")
	private Float inValue;
	
	@Column(name = "OUT_VALUE")
	private Float outValue;
	
	@Column(name = "NET_BALANCE_VALUE")
	private Float netBalanceVale;
	
	@Column(name = "BALANCE_VALUE")
	private Float balanceValue;
	
	@Column(name = "INCOMING_RATE")
	private Float inComingRate;
	
	private Float inQty;
	
	private Float outQty;
	
	private Float openingQty;
	
	private Float openingValue;

	public AssetLifeCycle_Balance() {
		
	}

	public AssetLifeCycle_Balance(String alcID, Timestamp creationDate, Timestamp lastModifiedDate, String warehouse, String siteID, String wareName,
			String itemCode, String itemName, String itemModel, String itemPartNO, String supplier, String voucherType,
			String voucherNB, Float valuationRate, String uOM, Float accumulatedDepreciation,
			Float balanceQty, Float inValue, Float outValue, Float netBalanceVale, Float balanceValue,
			Float inComingRate, Float inQty, Float outQty, Float openingQty, Float openingValue) {
		super();
		this.alcID = alcID;
		this.creationDate = creationDate;
		this.lastModifiedDate = lastModifiedDate;
		this.warehouse = warehouse;
		this.siteID = siteID;
		this.wareName = wareName;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemModel = itemModel;
		this.itemPartNO = itemPartNO;
		Supplier = supplier;
		this.voucherType = voucherType;
		this.voucherNB = voucherNB;
		this.valuationRate = valuationRate;
		UOM = uOM;
		this.accumulatedDepreciation = accumulatedDepreciation;
		this.balanceQty = balanceQty;
		this.inValue = inValue;
		this.outValue = outValue;
		this.netBalanceVale = netBalanceVale;
		this.balanceValue = balanceValue;
		this.inComingRate = inComingRate;
		this.inQty = inQty;
		this.outQty = outQty;
		this.openingQty = openingQty;
		this.openingValue = openingValue;
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

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
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

	public String getItemPartNO() {
		return itemPartNO;
	}

	public void setItemPartNO(String itemPartNO) {
		this.itemPartNO = itemPartNO;
	}

	public String getSupplier() {
		return Supplier;
	}

	public void setSupplier(String supplier) {
		Supplier = supplier;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public String getVoucherNB() {
		return voucherNB;
	}

	public void setVoucherNB(String voucherNB) {
		this.voucherNB = voucherNB;
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

	public Float getAccumulatedDepreciation() {
		return accumulatedDepreciation;
	}

	public void setAccumulatedDepreciation(Float accumulatedDepreciation) {
		this.accumulatedDepreciation = accumulatedDepreciation;
	}

	public Float getBalanceQty() {
		return balanceQty;
	}

	public void setBalanceQty(Float balanceQty) {
		this.balanceQty = balanceQty;
	}

	public Float getInValue() {
		return inValue;
	}

	public void setInValue(Float inValue) {
		this.inValue = inValue;
	}

	public Float getOutValue() {
		return outValue;
	}

	public void setOutValue(Float outValue) {
		this.outValue = outValue;
	}

	public Float getNetBalanceVale() {
		return netBalanceVale;
	}

	public void setNetBalanceVale(Float netBalanceVale) {
		this.netBalanceVale = netBalanceVale;
	}

	public Float getBalanceValue() {
		return balanceValue;
	}

	public void setBalanceValue(Float balanceValue) {
		this.balanceValue = balanceValue;
	}

	public Float getInComingRate() {
		return inComingRate;
	}

	public void setInComingRate(Float inComingRate) {
		this.inComingRate = inComingRate;
	}

	public Float getInQty() {
		return inQty;
	}

	public void setInQty(Float inQty) {
		this.inQty = inQty;
	}

	public Float getOutQty() {
		return outQty;
	}

	public void setOutQty(Float outQty) {
		this.outQty = outQty;
	}

	public Float getOpeningQty() {
		return openingQty;
	}

	public void setOpeningQty(Float openingQty) {
		this.openingQty = openingQty;
	}

	public Float getOpeningValue() {
		return openingValue;
	}

	public void setOpeningValue(Float openingValue) {
		this.openingValue = openingValue;
	}

	
	
}
