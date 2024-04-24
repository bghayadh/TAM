package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIXED_ASSET_REGISTRY")
public class FixedAssetRegisterListView {
	
	@Id
	@Column(name = "FAR_ID", nullable = false)
	private String farID;
	
	private String fixedassetID;
	
	@Column(name = "FAR_STATUS", nullable = false)
	private String farStatus;
	
	@Column(name = "ITEM_CODE", nullable = false)
	private String itemCode;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private String lastModifiedDate;
	
	@Column(name = "ITEM_SN")
	private String itemSN;
	
	@Column(name = "ITEM_NAME_REGISTER")
	private String itemNameRegister;
	
	@Column(name = "PO_ID")
	private String poID;
	
		
	@Column(name = "SITE_ID")
	private String siteID;

	@Column(name = "SITE_NAME")
	private String siteName;
				

	
				
	public FixedAssetRegisterListView() {	
	}


	public FixedAssetRegisterListView(String farID, String fixedassetID, String itemCode, String itemName,
			String lastModifiedDate, String itemSN, String itemNameRegister, String poID, String siteID, String siteName, String farStatus) {
		super();
		this.farID = farID;
		this.fixedassetID = fixedassetID;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.lastModifiedDate = lastModifiedDate;
		this.itemSN = itemSN;
		this.itemNameRegister = itemNameRegister;
		this.poID = poID;
		this.siteID = siteID;
		this.siteName=siteName;
		this.farStatus=farStatus;
	}


	public String getFarStatus() {
		return farStatus;
	}


	public void setFarStatus(String farStatus) {
		this.farStatus = farStatus;
	}


	public String getFarID() {
		return farID;
	}


	public void setFarID(String farID) {
		this.farID = farID;
	}


	public String getFixedassetID() {
		return fixedassetID;
	}


	public void setFixedassetID(String fixedassetID) {
		this.fixedassetID = fixedassetID;
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
