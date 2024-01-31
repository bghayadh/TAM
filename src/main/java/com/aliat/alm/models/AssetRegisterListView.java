package com.aliat.alm.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSET_REGISTRY")
public class AssetRegisterListView {
	
	@Id
	@Column(name = "AR_ID", nullable = false)
	private String arID;
	
	private String assetID;
	
	@Column(name = "ITEM_CODE", nullable = false)
	private String aritemCode;
	
	@Column(name = "ITEM_NAME")
	private String aritemName;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;
		
	@Column(name = "ITEM_PART_NUMBER")
	private String itemPartNumber;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private String arlastModifiedDate;
	
	@Column(name = "ITEM_SN")
	private String itemSN;
	
	@Column(name = "ITEM_NAME_REGISTER")
	private String itemNameReg;
	
	@Column(name = "PO_ID")
	private String poID;
	
	
	@Column(name = "SITE_ID")
	private String siteID;
	
	@Column(name = "SITE_NAME")
	private String siteName;
	

	public AssetRegisterListView() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AssetRegisterListView(String arID, String assetID, String aritemCode, String aritemName, String itemModel,
			String itemPartNumber, String arlastModifiedDate, String itemSN, String itemNameReg, String poID,
			 String siteID, String siteName) {
		super();
		this.arID = arID;
		this.assetID = assetID;
		this.aritemCode = aritemCode;
		this.aritemName = aritemName;
		this.itemModel = itemModel;
		this.itemPartNumber = itemPartNumber;
		this.arlastModifiedDate = arlastModifiedDate;
		this.itemSN = itemSN;
		this.itemNameReg = itemNameReg;
		this.poID = poID;
		this.siteID = siteID;
		this.siteName=siteName;
	}


	public String getArID() {
		return arID;
	}


	public void setArID(String arID) {
		this.arID = arID;
	}


	public String getAssetID() {
		return assetID;
	}


	public void setAssetID(String assetID) {
		this.assetID = assetID;
	}


	public String getAritemCode() {
		return aritemCode;
	}


	public void setAritemCode(String aritemCode) {
		this.aritemCode = aritemCode;
	}


	public String getAritemName() {
		return aritemName;
	}


	public void setAritemName(String aritemName) {
		this.aritemName = aritemName;
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


	public String getArlastModifiedDate() {
		return arlastModifiedDate;
	}


	public void setArlastModifiedDate(String arlastModifiedDate) {
		this.arlastModifiedDate = arlastModifiedDate;
	}


	public String getItemSN() {
		return itemSN;
	}


	public void setItemSN(String itemSN) {
		this.itemSN = itemSN;
	}


	public String getItemNameReg() {
		return itemNameReg;
	}


	public void setItemNameReg(String itemNameReg) {
		this.itemNameReg = itemNameReg;
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



}
