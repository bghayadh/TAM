package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSET_REGISTRY")
public class AssetRegistryListView {
	
	@Id
	@Column(name = "AR_ID", nullable = false)
	private String id;
	
	@Column(name = "ITEM_CODE", nullable = false)
	private String itemCode;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;
		
	@Column(name = "ITEM_PART_NUMBER")
	private String itemPartNumber;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private String lastModifiedDate;

	@Column(name = "ITEM_SN")
	private String itemSN;
		
	@Column(name = "PO_ID")
	private String poID;

	@Column(name = "ITEM_NAME_REGISTER")
	private String itemNameRegister;
	
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

	public AssetRegistryListView() {
		
	}

	public AssetRegistryListView(String id, String itemCode, String itemName, String itemModel, String itemPartNumber,
			String lastModifiedDate, String itemSN, String poID, String itemNameRegister, String nodeID,
			String nodeName, String supplierID) {
		super();
		this.id = id;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemModel = itemModel;
		this.itemPartNumber = itemPartNumber;
		this.lastModifiedDate = lastModifiedDate;
		this.itemSN = itemSN;
		this.poID = poID;
		this.itemNameRegister = itemNameRegister;
		this.nodeID = nodeID;
		this.nodeName = nodeName;
//		this.siteID = siteID;
		this.supplierID = supplierID;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
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

	public String getItemPartNumber() {
		return itemPartNumber;
	}

	public void setItemPartNumber(String itemPartNumber) {
		this.itemPartNumber = itemPartNumber;
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

	public String getPoID() {
		return poID;
	}

	public void setPoID(String poID) {
		this.poID = poID;
	}

	public String getItemNameRegister() {
		return itemNameRegister;
	}

	public void setItemNameRegister(String itemNameRegister) {
		this.itemNameRegister = itemNameRegister;
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
	
}
