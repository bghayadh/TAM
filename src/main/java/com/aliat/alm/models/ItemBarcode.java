package com.aliat.alm.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ITEM_BARCODE")
public class ItemBarcode {
	
	@Column(name="ITEM_CODE",nullable=false)
	private String itemCode;
	
	@Id
	@Column(name="BARCODE_NUMBER",nullable=false)
	private String barcodeNb;
	
	@Column(name="ACTIVE",nullable=false)
	private String activeB_code;
	
   
	




public ItemBarcode() {
	   super();
   }
	
	public String getBarcodeNb() {
		return barcodeNb;
	}
	
	public String getActivebcode() {
		return activeB_code;
	}

	public void setActivebcode(String activeB_code) {
		this.activeB_code = activeB_code;
	}

	public void setBarcodeNb(String barcodeNb) {
		this.barcodeNb = barcodeNb;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public ItemBarcode(String barcodeNb, String itemCode,String activeB_code) {
		super();
		this.barcodeNb = barcodeNb;
		this.itemCode = itemCode;
		this.activeB_code = activeB_code;
		
	}
}
