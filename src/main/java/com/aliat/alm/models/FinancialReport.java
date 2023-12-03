package com.aliat.alm.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIXED_ASSET_REGISTRY")
public class FinancialReport {
	
	private String site;

	@Id
	@Column(name = "FAR_ID", nullable = false)
	private String farID;
	

	@Column(name = "ITEM_CODE", nullable = false)
	private String itemCode;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	private String itemModel;
	private String itemPartNo;

	@Column(name = "LAST_MODIFIED_DATE")
	private String lastModifiedDate;
	
	@Column(name = "ITEM_SN")
	private String itemSN;
	
	@Column(name = "ITEM_NAME_REGISTER")
	private String itemNameRegister;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplier;
	
	@Column(name = "PO_ID")
	private String poID;
	
	@Column(name = "SITE_ID")
	private String siteID;

	@Column(name = "SITE_NAME")
	private String siteName;
	
	private String longitude;
	private String latitude;
	
	@Column(name = "VENDOR")
	private String vendor;
	
	@Column(name = "INITIALCOST")
	private BigDecimal initCost;
	
	@Column(name = "NETCOST")
	private BigDecimal netCost;
	
	@Column(name = "ACCUMULDEPRECAMNT")
	private BigDecimal accuDepr;
				
	
	public FinancialReport() {	
	}


	public FinancialReport(String site,String farID, String itemCode, String itemName,String itemModel,String itemPartNo,
			String lastModifiedDate, String itemSN, String itemNameRegister,String supplier, String poID,String siteID, 
			String longitude, String latitude,String vendor,String siteName,BigDecimal initCost, BigDecimal netCost,BigDecimal accuDepr) {
		super();
		this.site=site;
		this.farID = farID;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemModel=itemModel;
		this.itemPartNo=itemPartNo;
		this.lastModifiedDate = lastModifiedDate;
		this.itemSN = itemSN;
		this.itemNameRegister = itemNameRegister;
		this.supplier=supplier;
		this.poID = poID;
		this.siteID = siteID;
		this.siteName=siteName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.vendor = vendor;
		this.initCost=initCost;
		this.netCost=netCost;
		this.accuDepr=accuDepr;
	}


	public String getFarID() {
		return farID;
	}


	public void setFarID(String farID) {
		this.farID = farID;
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


	public String getLastModifiedDate() {
		return lastModifiedDate;
	}


	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


	public String getItemSN() {
		return itemSN;
	}


	public void setItemSN(String itemSN) {
		this.itemSN = itemSN;
	}


	public String getItemNameRegister() {
		return itemNameRegister;
	}


	public void setItemNameRegister(String itemNameRegister) {
		this.itemNameRegister = itemNameRegister;
	}


	public String getSupplier() {
		return supplier;
	}


	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}


	public String getPoID() {
		return poID;
	}


	public void setPoID(String poID) {
		this.poID = poID;
	}


	public String getSiteID() {
		return siteID;
	}


	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}


	public String getSiteName() {
		return siteName;
	}


	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getVendor() {
		return vendor;
	}


	public void setVendor(String vendor) {
		this.vendor = vendor;
	}


	public BigDecimal getInitCost() {
		return initCost;
	}


	public void setInitCost(BigDecimal initCost) {
		this.initCost = initCost;
	}


	public BigDecimal getNetCost() {
		return netCost;
	}


	public void setNetCost(BigDecimal netCost) {
		this.netCost = netCost;
	}


	public BigDecimal getAccuDepr() {
		return accuDepr;
	}


	public void setAccuDepr(BigDecimal accuDepr) {
		this.accuDepr = accuDepr;
	}


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
	}



}
