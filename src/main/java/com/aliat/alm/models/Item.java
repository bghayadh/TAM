package com.aliat.alm.models;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "ITEM")
public class Item {


	@Id
	@Column(name = "ITEM_CODE", nullable = false)
	private String itemCode;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "UNIT")
	private String unit;
	
	@Column(name = "ITEM_DESCRIPTION")
	private String itemDescription;
	
	@Column(name = "DOMAIN")
	private String domain;
	
	@Column(name = "ITEM_CATEGORY")
	private String itemCategory;
	
	@Column(name = "ITEM_TYPE")
	private String itemType;
	
	@Column(name = "CABLE_TYPE")
	private String cableType;
	
	@Column(name = "WEIGHT")
	private String weight;

	@Column(name = "WEIGHT_UOM")
	private String weightUOM;
	
	@Column(name = "LENGTH")
	private String length;

	@Column(name = "WIDTH")
	private String width;
	
	@Column(name = "HEIGHT")
	private String height;
	
	@Column(name = "SIZE_UOM")
	private String sizeUOM;
	
	@Column(name = "SERVICE")
	private char service;

	@Column(name = "END_OF_LIFE")
	private Timestamp endOfLife;
	
	@Column(name = "VALUATION_RATE")
	private Float valuationRate;

	@Column(name = "DISABLED")
	private char disabled;
	
	@Column(name = "ITEM_IMAGE")
	private String itemImage;
	
	@Column(name = "WARRANTY_PERIOD")
	private String warrantyPeriod;
	
	@Column(name = "TECH_2G")
	private char tech2G;
	
	@Column(name = "TECH_3G")
	private char tech3G;
	
	@Column(name = "TECH_4G")
	private char tech4G;
	
	@Column(name = "TECH_5G")
	private char tech5G;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;

	@Column(name = "ITEM_PART_NUMBER")
	private String itemPartNumber;
	
	@Column(name = "ITEM_MANUFACTURER")
	private String itemManufacturer;
	
	@Column(name = "ITEM_MODE")
	private String itemMode;

	@Column(name = "ITEM_ROOT_CODE")
	private String itemRootCode;

	@Column(name = "DEPREC_CODE")
	private String deprec_Code;

	@Column(name = "ACCUM_DEPREC_CODE")
	private String accumDeprec_Code;

	@Column(name = "USEFULL_LIFE_MONTHS")
	private String usefull_LifeMonths;
  
	@Column(name = "ITEM_KIND")
	private String itemKind;

	@Column(name = "OS")
	private String os;

	@Column(name = "PHYSICAL_RAM")
	private String physicalRam;
	
	@Column(name = "PROCESSOR_TYPE")
	private String processorType;

	@Column(name = "PROCESSOR_VENDOR")
	private String processorVendor;

	@Column(name = "SKU_NUMBER")
	private String skuNumber;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "ITEM_CATCODE")
	private String itemCatCode;
	
	@Column(name = "ITEM_CATID")
	private String itemCatID;
	
	
	public Item() {	
	}


	public Item(String itemCode, Timestamp createdDate, Timestamp lastModifiedDate, String itemName, String unit,
			String itemDescription, String domain, String itemCategory, String itemType, String cableType,
			String weight, String weightUOM, String length, String width, String height, String sizeUOM, char service,
			Timestamp endOfLife, Float valuationRate, char disabled, String itemImage, String warrantyPeriod,
			char tech2g, char tech3g, char tech4g, char tech5g, String itemModel, String itemPartNumber,
			String itemManufacturer, String itemMode, String itemRootCode, String deprec_Code, String accumDeprec_Code,
			String usefull_LifeMonths, String itemKind, String os, String physicalRam, String processorType,
			String processorVendor, String skuNumber, String uuid, String itemCatCode, String itemCatID) {
		super();
		this.itemCode = itemCode;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.itemName = itemName;
		this.unit = unit;
		this.itemDescription = itemDescription;
		this.domain = domain;
		this.itemCategory = itemCategory;
		this.itemType = itemType;
		this.cableType = cableType;
		this.weight = weight;
		this.weightUOM = weightUOM;
		this.length = length;
		this.width = width;
		this.height = height;
		this.sizeUOM = sizeUOM;
		this.service = service;
		this.endOfLife = endOfLife;
		this.valuationRate = valuationRate;
		this.disabled = disabled;
		this.itemImage = itemImage;
		this.warrantyPeriod = warrantyPeriod;
		tech2G = tech2g;
		tech3G = tech3g;
		tech4G = tech4g;
		tech5G = tech5g;
		this.itemModel = itemModel;
		this.itemPartNumber = itemPartNumber;
		this.itemManufacturer = itemManufacturer;
		this.itemMode = itemMode;
		this.itemRootCode = itemRootCode;
		this.deprec_Code = deprec_Code;
		this.accumDeprec_Code = accumDeprec_Code;
		this.usefull_LifeMonths = usefull_LifeMonths;
		this.itemKind = itemKind;
		this.os = os;
		this.physicalRam = physicalRam;
		this.processorType = processorType;
		this.processorVendor = processorVendor;
		this.skuNumber = skuNumber;
		this.uuid = uuid;
		this.itemCatCode = itemCatCode;
		this.itemCatID = itemCatID;
	}


	public String getItemCode() {
		return itemCode;
	}


	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}


	public Timestamp getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}


	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}


	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getItemDescription() {
		return itemDescription;
	}


	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public String getItemCategory() {
		return itemCategory;
	}


	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}


	public String getItemType() {
		return itemType;
	}


	public void setItemType(String itemType) {
		this.itemType = itemType;
	}


	public String getCableType() {
		return cableType;
	}


	public void setCableType(String cableType) {
		this.cableType = cableType;
	}


	public String getWeight() {
		return weight;
	}


	public void setWeight(String weight) {
		this.weight = weight;
	}


	public String getWeightUOM() {
		return weightUOM;
	}


	public void setWeightUOM(String weightUOM) {
		this.weightUOM = weightUOM;
	}


	public String getLength() {
		return length;
	}


	public void setLength(String length) {
		this.length = length;
	}


	public String getWidth() {
		return width;
	}


	public void setWidth(String width) {
		this.width = width;
	}


	public String getHeight() {
		return height;
	}


	public void setHeight(String height) {
		this.height = height;
	}


	public String getSizeUOM() {
		return sizeUOM;
	}


	public void setSizeUOM(String sizeUOM) {
		this.sizeUOM = sizeUOM;
	}


	public char getService() {
		return service;
	}


	public void setService(char service) {
		this.service = service;
	}


	public Timestamp getEndOfLife() {
		return endOfLife;
	}


	public void setEndOfLife(Timestamp endOfLife) {
		this.endOfLife = endOfLife;
	}


	public Float getValuationRate() {
		return valuationRate;
	}


	public void setValuationRate(Float valuationRate) {
		this.valuationRate = valuationRate;
	}


	public char getDisabled() {
		return disabled;
	}


	public void setDisabled(char disabled) {
		this.disabled = disabled;
	}


	public String getItemImage() {
		return itemImage;
	}


	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}


	public String getWarrantyPeriod() {
		return warrantyPeriod;
	}


	public void setWarrantyPeriod(String warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}


	public char getTech2G() {
		return tech2G;
	}


	public void setTech2G(char tech2g) {
		tech2G = tech2g;
	}


	public char getTech3G() {
		return tech3G;
	}


	public void setTech3G(char tech3g) {
		tech3G = tech3g;
	}


	public char getTech4G() {
		return tech4G;
	}


	public void setTech4G(char tech4g) {
		tech4G = tech4g;
	}


	public char getTech5G() {
		return tech5G;
	}


	public void setTech5G(char tech5g) {
		tech5G = tech5g;
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


	public String getItemManufacturer() {
		return itemManufacturer;
	}


	public void setItemManufacturer(String itemManufacturer) {
		this.itemManufacturer = itemManufacturer;
	}


	public String getItemMode() {
		return itemMode;
	}


	public void setItemMode(String itemMode) {
		this.itemMode = itemMode;
	}


	public String getItemRootCode() {
		return itemRootCode;
	}


	public void setItemRootCode(String itemRootCode) {
		this.itemRootCode = itemRootCode;
	}


	public String getDeprec_Code() {
		return deprec_Code;
	}


	public void setDeprec_Code(String deprec_Code) {
		this.deprec_Code = deprec_Code;
	}


	public String getAccumDeprec_Code() {
		return accumDeprec_Code;
	}


	public void setAccumDeprec_Code(String accumDeprec_Code) {
		this.accumDeprec_Code = accumDeprec_Code;
	}


	public String getUsefull_LifeMonths() {
		return usefull_LifeMonths;
	}


	public void setUsefull_LifeMonths(String usefull_LifeMonths) {
		this.usefull_LifeMonths = usefull_LifeMonths;
	}


	public String getItemKind() {
		return itemKind;
	}


	public void setItemKind(String itemKind) {
		this.itemKind = itemKind;
	}


	public String getOs() {
		return os;
	}


	public void setOs(String os) {
		this.os = os;
	}


	public String getPhysicalRam() {
		return physicalRam;
	}


	public void setPhysicalRam(String physicalRam) {
		this.physicalRam = physicalRam;
	}


	public String getProcessorType() {
		return processorType;
	}


	public void setProcessorType(String processorType) {
		this.processorType = processorType;
	}


	public String getProcessorVendor() {
		return processorVendor;
	}


	public void setProcessorVendor(String processorVendor) {
		this.processorVendor = processorVendor;
	}


	public String getSkuNumber() {
		return skuNumber;
	}


	public void setSkuNumber(String skuNumber) {
		this.skuNumber = skuNumber;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	
}
