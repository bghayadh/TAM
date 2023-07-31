package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.opencensus.common.Timestamp;

@Entity
@Table(name = "SHOPS")
public class ShopsListView {
	
	@Id
	@Column(name = "SHOPS_ID", nullable = false)
	private String shopId;
	
	@Column(name = "OWNER", nullable = false)
	private String owner;
	
	@Column(name = "SHOP_NAME", nullable = false)
	private String shopName;
	
	@Column(name = "ADDRESS", nullable = false)
	private String address;
	
	@Column(name = "REGION_NAME")
	private String regionName;
	
	@Column(name = "LAST_MODIFIED_DATE")
    private String lastModifieddate;

	public ShopsListView(String shopId, String owner, String shopName, String address, String regionName,
			String lastModifieddate) {
		super();
		this.shopId = shopId;
		this.owner = owner;
		this.shopName = shopName;
		this.address = address;
		this.regionName = regionName;
		this.lastModifieddate = lastModifieddate;
	}

	public ShopsListView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getLastModifieddate() {
		return lastModifieddate;
	}

	public void setLastModifieddate(String lastModifieddate) {
		this.lastModifieddate = lastModifieddate;
	}
	
}
