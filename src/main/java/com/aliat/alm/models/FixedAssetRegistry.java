package com.aliat.alm.models;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIXED_ASSET_REGISTRY")
public class FixedAssetRegistry {
	
	@Id
	@Column(name = "FAR_ID", nullable = false)
	private String farID;
	
	@Column(name = "FAR_STATUS", nullable = false)
	private String FarStatus;
	
	@Column(name = "FR_SITE_ID", nullable = false)
	private String FarsiteId;
	
	@Column(name = "ITEM_CODE", nullable = false)
	private String faritemCode;
	
	
	@Column(name = "AR_ID")
	private String ARID;
	
	public String getARID() {
		return ARID;
	}

	public void setARID(String aRID) {
		ARID = aRID;
	}

	public String getWareID() {
		return wareID;
	}

	public void setWareID(String wareID) {
		this.wareID = wareID;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	@Column(name = "CREATED_DATE")
	private Timestamp farcreatedDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp farlastModifiedDate;
	
	@Column(name = "ITEM_NAME")
	private String faritemName;
	
	@Column(name = "DOMAIN")
	private String farDomain;
	
	@Column(name = "SUB_DOMAIN")
	private String farSubDomain;
	
	@Column(name = "VENDOR")
	private String farVendor;
	
	@Column(name = "SUB_DOMAIN_TYPE")
	private String farType;
	
	
	@Column(name = "INITIALCOST")
	private float iniCost;
	
	@Column(name = "DEPRECIATIONPER")
	private float deprPer;
	
	@Column(name = "DAILYDEPRECAMNT")
	private float dailyPercent;
	
	@Column(name = "ACCUMULDEPRECAMNT")
	private float accumPer;
	
	@Column(name = "NETCOST")
	private float netCost;
	
	@Column(name = "SALVAGEVALUE")
	private String salvageValue;
	
	@Column(name = "PO_ID")
	private String PoId;
	
	@Column(name = "ACCUMULDEPRECCODE")
	private String accumDeprCode;
	
	@Column(name = "DEPREC_CODE")
	private String deprCode;
	
	@Column(name = "USEFULLIFEMONTHS")
	private float usefulLifeMon;
	
	@Column(name = "SUPPLIER_ID")
	private String supplierID;
	
	@Column(name = "VENDOR_NUMBER")
	private String vendorNb;
	
	@Column(name = "PROJ_NUMBER")
	private String projNb;
/*	
	@Column(name = "SITE_ID")
	private String sideID;
*/	
		
	@Column(name = "ITEM_PART_NUMBER")
	private String itemPartNb;
	
	@Column(name = "ASSET_NUMBER")
	private String assetNb;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;
	
	@Column(name = "DNI_ID")
	private String dniID;
	
	@Column(name = "ITEM_NAME_REGISTER")
	private String itemNameReg;
	
	
	@Column(name = "OWNERSHIP")
	private String ownership;
	
	@Column(name = "SCRAPDATE")
	private Date scrapDate;
	
	@Column(name = "SCRAPSTATUS")
	private String scrapStatus;
	
	@Column(name = "NOTES")
	private String notes;
	
	@Column(name = "SHELTER_VENDOR")
	private String shelterVendor;
	
	@Column(name = "SHELTER_ROOM_ID")
	private String shelerRoomID;
	
	@Column(name = "LOCATION_SUB_TYPE")
	private String locSubType;
	
	@Column(name = "LOCATION_CLASSIFICATION")
	private String locClass;
	
	@Column(name = "LOCATION_ADDRESS")
	private String LocAddress;
	
	@Column(name = "LAST_MODIFIED_USER")
	private String lastModifiedUser;
	
	@Column(name = "OBJECT_ID")
	private String objectID;
	
	@Column(name = "ITEM_SN")
	private String itemSN; 
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name = "WARE_ID")
	private String wareID;
	
	@Column(name = "WARE_NAME")
	private String wareName;
	
	@Column(name = "MONTHLY_DEPR")
	private float monthlyDpr;
	
	@Column(name = "POSITION")
	private String position;
	
	@Column(name = "PO_ITEM_ID")
	private String PoItemId;
	
	@Column(name = "ITEM_ROOT_CODE")
	private String itemRootCode;
	
	@Column(name = "ITEM_CATCODE")
	private String itemCatCode;
	
	@Column(name = "ITEM_CATID")
	private String itemCatID;
	
	@Column(name = "ITEM_CATEGORY")
	private String itemCat;
	
	public FixedAssetRegistry() {	
	}

	public FixedAssetRegistry(String farID, String farStatus, String FarsiteId, String faritemCode, Timestamp farcreatedDate,
			Timestamp farlastModifiedDate, String faritemName, String farDomain, String farSubDomain, String farVendor, String farType,
			float iniCost, float deprPer,
			float dailyPercent, float accumPer, float netCost, String salvageValue, String poId, String accumDeprCode,
			String deprCode, float usefulLifeMon, String supplierID, String vendorNb, String projNb,
			String itemPartNb, String assetNb, String itemModel, String dniID, String itemNameReg, String ownership, Date scrapDate, String scrapStatus, String notes, String shelterVendor,
			String shelerRoomID, String locSubType, String locClass, String locAddress, String lastModifiedUser,
			String objectID, String itemSN, String supplierName, float monthlyDpr,
			String position, String poItemId, String itemRootCode, String itemCatCode, String itemCatID,
			String itemCat, String ARID) {
		super();
		this.farID = farID;
		this.ARID=ARID;
		FarStatus = farStatus;
		this.FarsiteId = FarsiteId;
		this.faritemCode = faritemCode;
		this.farcreatedDate = farcreatedDate;
		this.farlastModifiedDate = farlastModifiedDate;
		this.faritemName = faritemName;
		this.farDomain = farDomain;
		this.farSubDomain = farSubDomain;
		this.farVendor = farVendor;
		this.farType = farType;
		this.iniCost = iniCost;
		this.deprPer = deprPer;
		this.dailyPercent = dailyPercent;
		this.accumPer = accumPer;
		this.netCost = netCost;
		this.salvageValue = salvageValue;
		PoId = poId;
		this.accumDeprCode = accumDeprCode;
		this.deprCode = deprCode;
		this.usefulLifeMon = usefulLifeMon;
		this.supplierID = supplierID;
		this.vendorNb = vendorNb;
		this.projNb = projNb;
		//this.sideID = sideID;
		this.itemPartNb = itemPartNb;
		this.assetNb = assetNb;
		this.itemModel = itemModel;
		this.dniID = dniID;
		this.itemNameReg = itemNameReg;
		this.ownership = ownership;
		this.scrapDate = scrapDate;
		this.scrapStatus = scrapStatus;
		this.notes = notes;
		this.shelterVendor = shelterVendor;
		this.shelerRoomID = shelerRoomID;
		this.locSubType = locSubType;
		this.locClass = locClass;
		LocAddress = locAddress;
		this.lastModifiedUser = lastModifiedUser;
		this.objectID = objectID;
		this.itemSN = itemSN;
		this.supplierName = supplierName;
		//this.wareID = wareID;
		//this.wareName = wareName;
		this.monthlyDpr = monthlyDpr;
		this.position = position;
		PoItemId = poItemId;
		this.itemRootCode = itemRootCode;
		this.itemCatCode = itemCatCode;
		this.itemCatID = itemCatID;
		this.itemCat = itemCat;
	}

	public String getFarID() {
		return farID;
	}

	public void setFarID(String farID) {
		this.farID = farID;
	}

	public String getFarStatus() {
		return FarStatus;
	}

	public void setFarStatus(String farStatus) {
		FarStatus = farStatus;
	}

	public String getFarsiteId() {
		return FarsiteId;
	}

	public void setFarsiteId(String farsiteId) {
		FarsiteId = farsiteId;
	}

	public String getFaritemCode() {
		return faritemCode;
	}

	public void setFaritemCode(String faritemCode) {
		this.faritemCode = faritemCode;
	}

	public Timestamp getFarcreatedDate() {
		return farcreatedDate;
	}

	public void setFarcreatedDate(Timestamp farcreatedDate) {
		this.farcreatedDate = farcreatedDate;
	}

	public Timestamp getFarlastModifiedDate() {
		return farlastModifiedDate;
	}

	public void setFarlastModifiedDate(Timestamp farlastModifiedDate) {
		this.farlastModifiedDate = farlastModifiedDate;
	}

	public String getFaritemName() {
		return faritemName;
	}

	public void setFaritemName(String faritemName) {
		this.faritemName = faritemName;
	}


	public String getFarDomain() {
		return farDomain;
	}

	public void setFarDomain(String farDomain) {
		this.farDomain = farDomain;
	}

	public String getFarSubDomain() {
		return farSubDomain;
	}

	public void setFarSubDomain(String farSubDomain) {
		this.farSubDomain = farSubDomain;
	}

	public String getFarVendor() {
		return farVendor;
	}

	public void setFarVendor(String farVendor) {
		this.farVendor = farVendor;
	}

	public String getFarType() {
		return farType;
	}

	public void setFarType(String farType) {
		this.farType = farType;
	}

	public float getIniCost() {
		return iniCost;
	}

	public void setIniCost(float iniCost) {
		this.iniCost = iniCost;
	}

	public float getDeprPer() {
		return deprPer;
	}

	public void setDeprPer(float deprPer) {
		this.deprPer = deprPer;
	}

	public float getDailyPercent() {
		return dailyPercent;
	}

	public void setDailyPercent(float dailyPercent) {
		this.dailyPercent = dailyPercent;
	}

	public float getAccumPer() {
		return accumPer;
	}

	public void setAccumPer(float accumPer) {
		this.accumPer = accumPer;
	}

	public float getNetCost() {
		return netCost;
	}

	public void setNetCost(float netCost) {
		this.netCost = netCost;
	}

	public String getSalvageValue() {
		return salvageValue;
	}

	public void setSalvageValue(String salvageValue) {
		this.salvageValue = salvageValue;
	}

	public String getPoId() {
		return PoId;
	}

	public void setPoId(String poId) {
		PoId = poId;
	}

	public String getAccumDeprCode() {
		return accumDeprCode;
	}

	public void setAccumDeprCode(String accumDeprCode) {
		this.accumDeprCode = accumDeprCode;
	}

	public String getDeprCode() {
		return deprCode;
	}

	public void setDeprCode(String deprCode) {
		this.deprCode = deprCode;
	}

	public float getUsefulLifeMon() {
		return usefulLifeMon;
	}

	public void setUsefulLifeMon(float usefulLifeMon) {
		this.usefulLifeMon = usefulLifeMon;
	}

	public String getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}

	public String getVendorNb() {
		return vendorNb;
	}

	public void setVendorNb(String vendorNb) {
		this.vendorNb = vendorNb;
	}

	public String getProjNb() {
		return projNb;
	}

	public void setProjNb(String projNb) {
		this.projNb = projNb;
	}
/*
	public String getSideID() {
		return sideID;
	}

	public void setSideID(String sideID) {
		this.sideID = sideID;
	}
*/
	public String getItemPartNb() {
		return itemPartNb;
	}

	public void setItemPartNb(String itemPartNb) {
		this.itemPartNb = itemPartNb;
	}

	public String getAssetNb() {
		return assetNb;
	}

	public void setAssetNb(String assetNb) {
		this.assetNb = assetNb;
	}

	public String getItemModel() {
		return itemModel;
	}

	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}

	public String getDniID() {
		return dniID;
	}

	public void setDniID(String dniID) {
		this.dniID = dniID;
	}

	public String getItemNameReg() {
		return itemNameReg;
	}

	public void setItemNameReg(String itemNameReg) {
		this.itemNameReg = itemNameReg;
	}

	

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public Date getScrapDate() {
		return scrapDate;
	}

	public void setScrapDate(Date scrapDate) {
		this.scrapDate = scrapDate;
	}

	public String getScrapStatus() {
		return scrapStatus;
	}

	public void setScrapStatus(String scrapStatus) {
		this.scrapStatus = scrapStatus;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getShelterVendor() {
		return shelterVendor;
	}

	public void setShelterVendor(String shelterVendor) {
		this.shelterVendor = shelterVendor;
	}

	public String getShelerRoomID() {
		return shelerRoomID;
	}

	public void setShelerRoomID(String shelerRoomID) {
		this.shelerRoomID = shelerRoomID;
	}

	public String getLocSubType() {
		return locSubType;
	}

	public void setLocSubType(String locSubType) {
		this.locSubType = locSubType;
	}

	public String getLocClass() {
		return locClass;
	}

	public void setLocClass(String locClass) {
		this.locClass = locClass;
	}

	public String getLocAddress() {
		return LocAddress;
	}

	public void setLocAddress(String locAddress) {
		LocAddress = locAddress;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	public String getItemSN() {
		return itemSN;
	}

	public void setItemSN(String itemSN) {
		this.itemSN = itemSN;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
/*
	public String getWareID() {
		return wareID;
	}

	public void setWareID(String wareID) {
		this.wareID = wareID;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
*/
	public float getMonthlyDpr() {
		return monthlyDpr;
	}

	public void setMonthlyDpr(float monthlyDpr) {
		this.monthlyDpr = monthlyDpr;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPoItemId() {
		return PoItemId;
	}

	public void setPoItemId(String poItemId) {
		PoItemId = poItemId;
	}

	public String getItemRootCode() {
		return itemRootCode;
	}

	public void setItemRootCode(String itemRootCode) {
		this.itemRootCode = itemRootCode;
	}

	public String getItemCatCode() {
		return itemCatCode;
	}

	public void setItemCatCode(String itemCatCode) {
		this.itemCatCode = itemCatCode;
	}

	public String getItemCatID() {
		return itemCatID;
	}

	public void setItemCatID(String itemCatID) {
		this.itemCatID = itemCatID;
	}

	public String getItemCat() {
		return itemCat;
	}

	public void setItemCat(String itemCat) {
		this.itemCat = itemCat;
	}

	
}
