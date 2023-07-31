package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FAR_MODEL_PARTNUMBER")
public class FarPartNumber {
	
	@Id
	@Column(name = "ITM_ID", nullable = false)
	private String itmId;
	
	@Column(name = "ITEM_CODE", nullable = false)
	private String itemCode;
	
	
	@Column(name = "ITEM_PART_NUMBER")
	private String itemPartNb;

	@Column(name = "QUANTITY")
	private float qtyPartNb;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;
	
	@Column(name = "PRIMARY")
	private String primary;
	
	@Column(name = "FAR_ID", nullable = false)
	private String farID;

	public FarPartNumber() {	
	}
	public FarPartNumber(String itmId, String itemCode, String itemPartNb, float qtyPartNb, String itemModel, String primary, String farID) {
		super();
		this.itmId = itmId;
		this.itemCode = itemCode;
		this.itemPartNb = itemPartNb;
		this.qtyPartNb = qtyPartNb;
		this.itemModel = itemModel;
		this.primary = primary;
		this.farID = farID;
	}
	
	public String getItmId() {
		return itmId;
	}
	public void setItmId(String itmId) {
		this.itmId = itmId;
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

	public String getItemPartNb() {
		return itemPartNb;
	}

	public void setItemPartNb(String itemPartNb) {
		this.itemPartNb = itemPartNb;
	}

	public float getQtyPartNb() {
		return qtyPartNb;
	}

	public void setQtyPartNb(float qtyPartNb) {
		this.qtyPartNb = qtyPartNb;
	}

	public String getItemModel() {
		return itemModel;
	}

	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}

	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}

	

}
