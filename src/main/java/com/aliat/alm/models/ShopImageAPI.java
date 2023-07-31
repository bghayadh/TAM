package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import oracle.sql.TIMESTAMP;

@Entity
@Table(name = "SHOPS_IMAGE")

public class ShopImageAPI {

	@Id
	@Column(name = "SHOP_IMAGE_ID", nullable = false)
	private String shopimgID;
	
	@Column(name = "SHOP_ID", nullable = false)
    private String shopid;
	
	@Column(name="IMAGE_NAME")
    private String imageName;
	
	@Column(name="UPLOAD_DATE")
	private Timestamp uploadDate;

	public ShopImageAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShopImageAPI(String shopimgID, String shopid, String imageName, Timestamp uploadDate) {
		super();
		this.shopimgID = shopimgID;
		this.shopid = shopid;
		this.imageName = imageName;
		this.uploadDate = uploadDate;
	}

	public String getShopimgID() {
		return shopimgID;
	}

	public void setShopimgID(String shopimgID) {
		this.shopimgID = shopimgID;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Timestamp getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}

	
	
	

}