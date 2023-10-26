package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSET_REGISTRY")
public class AssetRegistry {
	
	@Id
	@Column(name = "AR_ID", nullable = false)
	private String arID;
	
	@Column(name = "AR_STATUS", nullable = false)
	private String ArStatus;

	@Column(name = "AR_SITE_ID", nullable = false)
	private String arsiteId;
	
	@Column(name = "ITEM_CODE", nullable = false)
	private String aritemCode;
	
	@Column(name = "CREATED_DATE")
	private Timestamp arcreatedDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp arlastModifiedDate;
	
	@Column(name = "ITEM_NAME")
	private String aritemName;
	
	@Column(name = "UNIT")
	private String arunit;
	
	@Column(name = "ITEM_DESCRIPTION")
	private String aritemDescription;
	
	@Column(name = "DOMAIN")
	private String arDomain;
	
	@Column(name = "ITEM_TYPE")
	private String aritemType;
	
	@Column(name = "CABLE_TYPE")
	private String arcableType;
	
	@Column(name = "WEIGHT")
	private String arweight;

	@Column(name = "WEIGHT_UOM")
	private String arweightUOM;
	
	@Column(name = "LENGTH")
	private String arlength;

	@Column(name = "WIDTH")
	private String arwidth;
	
	@Column(name = "HEIGHT")
	private String arheight;
	
	@Column(name = "SIZE_UOM")
	private String arsizeUOM;
	
	@Column(name = "SERVICE")
	private char arservice;

	@Column(name = "END_OF_LIFE")
	private Timestamp arendOfLife;
	
	@Column(name = "VALUATION_RATE")
	private float arvaluationRate;

	@Column(name = "DISABLED")
	private char ardisabled;
	
	@Column(name = "ITEM_IMAGE")
	private String aritemImage;
	
	@Column(name = "WARRANTY_PERIOD")
	private String arwarrantyPeriod;
	
	@Column(name = "TECH_2G")
	private char artech2G;
	
	@Column(name = "TECH_3G")
	private char artech3G;
	
	@Column(name = "TECH_4G")
	private char artech4G;
	
	@Column(name = "TECH_5G")
	private char artech5G;
	
	@Column(name = "ITEM_NAME_REGISTER")
	private String itemNameReg;
	
	@Column(name = "DNI_ID")
	private String ardniID;
	
	@Column(name = "PO_ID")
	private String poID;
	
	@Column(name = "NODE_ID")
	private String nodeID;
	
	@Column(name = "NODE_NAME")
	private String nodeName;
/*	
	@Column(name = "SITE_ID")
	private String siteID;
*/	
	@Column(name = "SUPPLIER_ID")
	private String supplierID;
	
	@Column(name = "OWNERSHIP")
	private String ownership;
	
	@Column(name = "SCRAPDATE")
	private Timestamp scrapDate;
	
	@Column(name = "SCRAPSTATUS")
	private String scrapStatus;
	
	@Column(name = "NOTES")
	private String notes;
	
	@Column(name = "SHELTER_VENDOR")
	private String shelterVendor;
	
	@Column(name = "SHELTER_ROOM_ID")
	private String shelterRoomID;
	
	@Column(name = "LOCATION_SUB_TYPE")
	private String locationSubType;
	
	@Column(name = "LOCATION_CLASSIFICATION")
	private String locationClass;
	
	@Column(name = "LOCATION_ADDRESS")
	private String locationAddress;
	
	@Column(name = "LAST_MODIFIED_USER")
	private String lastModUser;
	
	@Column(name = "OBJECT_ID")
	private String objectID;
	
	@Column(name = "ITEM_SN")
	private String itemSN;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;
		
	@Column(name = "ITEM_PART_NUMBER")
	private String itemPartNumber;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
/*	
	@Column(name = "WARE_ID")
	private String wareID;
	
	@Column(name = "WARE_NAME")
	private String wareName;
*/	
	@Column(name = "POSITION")
	private String position;
	
	@Column(name = "PO_ITEM_ID")
	private String PoItemId;
	
	@Column(name = "PRQ_ID")
	private String prID;
	
	@Column(name = "GR_ID")
	private String grID;
	
	@Column(name = "ITEM_ROOT_CODE")
	private String itemRootCode;
	
	@Column(name = "ITEM_CATCODE")
	private String itemCatCode;
	
	@Column(name = "ITEM_CATID")
	private String itemCatID;
	
	@Column(name = "ITEM_CATEGORY")
	private String itemCat;
	
	@Column(name = "SUB_DOMAIN")
	private String arSubDomain;
	
	@Column(name = "VENDOR")
	private String arVendor;
	
	@Column(name = "SUB_DOMAIN_TYPE")
	private String arType;
	
	
	public AssetRegistry() {
		
	}

	
	
	public AssetRegistry(String arID, String arStatus, String arsiteId, String aritemCode, Timestamp arcreatedDate,
			Timestamp arlastModifiedDate, String aritemName, String arunit, String aritemDescription, String arDomain,
			String aritemType, String arcableType, String arweight, String arweightUOM, String arlength, String arwidth,
			String arheight, String arsizeUOM, char arservice, Timestamp arendOfLife, float arvaluationRate,
			char ardisabled, String aritemImage, String arwarrantyPeriod, char artech2g, char artech3g, char artech4g,
			char artech5g, String itemNameReg, String ardniID, String poID, String nodeID, String nodeName,
			String siteID, String supplierID, String ownership, Timestamp scrapDate, String scrapStatus, String notes,
			String shelterVendor, String shelterRoomID, String locationSubType, String locationClass,
			String locationAddress, String lastModUser, String objectID, String itemSN, String itemModel,
			String itemPartNumber, String supplierName, String wareID, String wareName, String position,
			String poItemId, String prID, String grID, String itemRootCode, String itemCatCode, String itemCatID,
			String itemCat,String arSubDomain, String arVendor, String arType) {
		super();
		this.arID = arID;
		ArStatus = arStatus;
		this.arsiteId = arsiteId;
		this.aritemCode = aritemCode;
		this.arcreatedDate = arcreatedDate;
		this.arlastModifiedDate = arlastModifiedDate;
		this.aritemName = aritemName;
		this.arunit = arunit;
		this.aritemDescription = aritemDescription;
		this.arDomain = arDomain;
		this.aritemType = aritemType;
		this.arcableType = arcableType;
		this.arweight = arweight;
		this.arweightUOM = arweightUOM;
		this.arlength = arlength;
		this.arwidth = arwidth;
		this.arheight = arheight;
		this.arsizeUOM = arsizeUOM;
		this.arservice = arservice;
		this.arendOfLife = arendOfLife;
		this.arvaluationRate = arvaluationRate;
		this.ardisabled = ardisabled;
		this.aritemImage = aritemImage;
		this.arwarrantyPeriod = arwarrantyPeriod;
		artech2G = artech2g;
		artech3G = artech3g;
		artech4G = artech4g;
		artech5G = artech5g;
		this.itemNameReg = itemNameReg;
		this.ardniID = ardniID;
		this.poID = poID;
		this.nodeID = nodeID;
		this.nodeName = nodeName;
//		this.siteID = siteID;
		this.supplierID = supplierID;
		this.ownership = ownership;
		this.scrapDate = scrapDate;
		this.scrapStatus = scrapStatus;
		this.notes = notes;
		this.shelterVendor = shelterVendor;
		this.shelterRoomID = shelterRoomID;
		this.locationSubType = locationSubType;
		this.locationClass = locationClass;
		this.locationAddress = locationAddress;
		this.lastModUser = lastModUser;
		this.objectID = objectID;
		this.itemSN = itemSN;
		this.itemModel = itemModel;
		this.itemPartNumber = itemPartNumber;
		this.supplierName = supplierName;
//		this.wareID = wareID;
//		this.wareName = wareName;
		this.position = position;
		PoItemId = poItemId;
		this.prID = prID;
		this.grID = grID;
		this.itemRootCode = itemRootCode;
		this.itemCatCode = itemCatCode;
		this.itemCatID = itemCatID;
		this.itemCat = itemCat;
		this.arSubDomain = arSubDomain;
		this.arVendor = arVendor;
		this.arType = arType;
	}

	public String getArID() {
		return arID;
	}

	public void setArID(String arID) {
		this.arID = arID;
	}

	public String getArStatus() {
		return ArStatus;
	}

	public void setArStatus(String arStatus) {
		ArStatus = arStatus;
	}

	public String getAritemCode() {
		return aritemCode;
	}

	public void setAritemCode(String aritemCode) {
		this.aritemCode = aritemCode;
	}

	public Timestamp getArcreatedDate() {
		return arcreatedDate;
	}

	public void setArcreatedDate(Timestamp arcreatedDate) {
		this.arcreatedDate = arcreatedDate;
	}

	public Timestamp getArlastModifiedDate() {
		return arlastModifiedDate;
	}

	public void setArlastModifiedDate(Timestamp arlastModifiedDate) {
		this.arlastModifiedDate = arlastModifiedDate;
	}

	public String getAritemName() {
		return aritemName;
	}

	public void setAritemName(String aritemName) {
		this.aritemName = aritemName;
	}

	public String getArunit() {
		return arunit;
	}

	public void setArunit(String arunit) {
		this.arunit = arunit;
	}

	public String getAritemDescription() {
		return aritemDescription;
	}

	public void setAritemDescription(String aritemDescription) {
		this.aritemDescription = aritemDescription;
	}

	public String getAritemType() {
		return aritemType;
	}

	public void setAritemType(String aritemType) {
		this.aritemType = aritemType;
	}

	public String getArcableType() {
		return arcableType;
	}

	public void setArcableType(String arcableType) {
		this.arcableType = arcableType;
	}

	public String getArweight() {
		return arweight;
	}

	public void setArweight(String arweight) {
		this.arweight = arweight;
	}

	public String getArweightUOM() {
		return arweightUOM;
	}

	public void setArweightUOM(String arweightUOM) {
		this.arweightUOM = arweightUOM;
	}

	public String getArlength() {
		return arlength;
	}

	public void setArlength(String arlength) {
		this.arlength = arlength;
	}

	public String getArwidth() {
		return arwidth;
	}

	public void setArwidth(String arwidth) {
		this.arwidth = arwidth;
	}

	public String getArheight() {
		return arheight;
	}

	public void setArheight(String arheight) {
		this.arheight = arheight;
	}

	public String getArsizeUOM() {
		return arsizeUOM;
	}

	public void setArsizeUOM(String arsizeUOM) {
		this.arsizeUOM = arsizeUOM;
	}

	public char getArservice() {
		return arservice;
	}

	public void setArservice(char arservice) {
		this.arservice = arservice;
	}

	public Timestamp getArendOfLife() {
		return arendOfLife;
	}

	public void setArendOfLife(Timestamp arendOfLife) {
		this.arendOfLife = arendOfLife;
	}

	public float getArvaluationRate() {
		return arvaluationRate;
	}

	public void setArvaluationRate(float arvaluationRate) {
		this.arvaluationRate = arvaluationRate;
	}

	public char getArdisabled() {
		return ardisabled;
	}

	public void setArdisabled(char ardisabled) {
		this.ardisabled = ardisabled;
	}

	public String getAritemImage() {
		return aritemImage;
	}

	public void setAritemImage(String aritemImage) {
		this.aritemImage = aritemImage;
	}

	public String getArwarrantyPeriod() {
		return arwarrantyPeriod;
	}

	public void setArwarrantyPeriod(String arwarrantyPeriod) {
		this.arwarrantyPeriod = arwarrantyPeriod;
	}

	public char getArtech2G() {
		return artech2G;
	}

	public void setArtech2G(char artech2g) {
		artech2G = artech2g;
	}

	public char getArtech3G() {
		return artech3G;
	}

	public void setArtech3G(char artech3g) {
		artech3G = artech3g;
	}

	public char getArtech4G() {
		return artech4G;
	}

	public void setArtech4G(char artech4g) {
		artech4G = artech4g;
	}

	public char getArtech5G() {
		return artech5G;
	}

	public void setArtech5G(char artech5g) {
		artech5G = artech5g;
	}

	public String getItemNameReg() {
		return itemNameReg;
	}

	public void setItemNameReg(String itemNameReg) {
		this.itemNameReg = itemNameReg;
	}

	public String getArdniID() {
		return ardniID;
	}

	public void setArdniID(String ardniID) {
		this.ardniID = ardniID;
	}

	public String getPoID() {
		return poID;
	}

	public void setPoID(String poID) {
		this.poID = poID;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
/*
	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}
*/
	public String getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public Timestamp getScrapDate() {
		return scrapDate;
	}

	public void setScrapDate(Timestamp scrapDate) {
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

	public String getShelterRoomID() {
		return shelterRoomID;
	}

	public void setShelterRoomID(String shelterRoomID) {
		this.shelterRoomID = shelterRoomID;
	}

	public String getLocationSubType() {
		return locationSubType;
	}

	public void setLocationSubType(String locationSubType) {
		this.locationSubType = locationSubType;
	}

	public String getLocationClass() {
		return locationClass;
	}

	public void setLocationClass(String locationClass) {
		this.locationClass = locationClass;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	public String getLastModUser() {
		return lastModUser;
	}

	public void setLastModUser(String lastModUser) {
		this.lastModUser = lastModUser;
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

	public String getPrID() {
		return prID;
	}

	public void setPrID(String prID) {
		this.prID = prID;
	}

	public String getGrID() {
		return grID;
	}

	public void setGrID(String grID) {
		this.grID = grID;
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

	public String getArsiteId() {
		return arsiteId;
	}

	public void setArsiteId(String arsiteId) {
		this.arsiteId = arsiteId;
	}



	public String getArDomain() {
		return arDomain;
	}



	public void setArDomain(String arDomain) {
		this.arDomain = arDomain;
	}



	public String getArSubDomain() {
		return arSubDomain;
	}



	public void setArSubDomain(String arSubDomain) {
		this.arSubDomain = arSubDomain;
	}



	public String getArVendor() {
		return arVendor;
	}



	public void setArVendor(String arVendor) {
		this.arVendor = arVendor;
	}



	public String getArType() {
		return arType;
	}



	public void setArType(String arType) {
		this.arType = arType;
	}

	
	
}
