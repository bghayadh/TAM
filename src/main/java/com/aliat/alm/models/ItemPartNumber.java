package com.aliat.alm.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ITEM_MODEL_PARTNUMBER")
public class ItemPartNumber {


	@Column(name="ITEM_CODE",nullable=false)
	private String itemCode;
	@Id
	@Column(name="ITM_ID",nullable=false)
	private String itmId;
	
	@Column(name="ITEM_PART_NUMBER")
	private String itemPartNum;
	
	@Column(name="PRIMARY")
	private String primary;
	
	@Column(name="ITEM_MODEL")
	private String itemModel;
	
	@Column(name="QUANTITY")
	private float qtyPartNum;

	public ItemPartNumber(String itmId,String itemCode, String itemPartNum, String primary, String itemModel, float qtyPartNum) {
		super();
		this.itmId=itmId;
		this.itemCode = itemCode;
		this.itemPartNum = itemPartNum;
		this.primary = primary;
		this.itemModel = itemModel;
		this.qtyPartNum = qtyPartNum;
	}

	public String getItmId() {
		return itmId;
	}

	public void setItmId(String itmId) {
		this.itmId = itmId;
	}

	public ItemPartNumber() {
		// TODO Auto-generated constructor stub
		super();
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemPartNum() {
		return itemPartNum;
	}

	public void setItemPartNum(String itemPartNum) {
		this.itemPartNum = itemPartNum;
	}

	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}

	public String getItemModel() {
		return itemModel;
	}

	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}

	public float getQtyPartNum() {
		return qtyPartNum;
	}

	public void setQtyPartNum(float qtyPartNum) {
		this.qtyPartNum = qtyPartNum;
	}
	
}
