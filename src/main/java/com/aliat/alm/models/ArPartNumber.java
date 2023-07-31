package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AR_MODEL_PARTNUMBER")
public class ArPartNumber {
	
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
	
	@Column(name = "AR_ID", nullable = false)
	private String arID;
	
	public ArPartNumber() {	
	}

	public String getItmId() {
		return itmId;
	}

	public void setItmId(String itmId) {
		this.itmId = itmId;
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

	public String getArID() {
		return arID;
	}

	public void setArID(String arID) {
		this.arID = arID;
	}

	public ArPartNumber(String itmId, String itemCode, String itemPartNb, float qtyPartNb, String itemModel,
			String primary, String arID) {
		super();
		this.itmId = itmId;
		this.itemCode = itemCode;
		this.itemPartNb = itemPartNb;
		this.qtyPartNb = qtyPartNb;
		this.itemModel = itemModel;
		this.primary = primary;
		this.arID = arID;
	}
	
}
