package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SHOPS")

public class DisplayShopsAPI {

	@Id
	@Column(name = "SHOPS_ID", nullable = false)
	private String ShopsId;
	private String ShopsName;
	private String Owner;
	private String Address;
	private String longitude;
	private String lattitude;
	
	
	public DisplayShopsAPI() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DisplayShopsAPI(String shopsId, String shopsName, String owner, String address, String longitude,
			String lattitude) {
		super();
		ShopsId = shopsId;
		ShopsName = shopsName;
		Owner = owner;
		Address = address;
		this.longitude = longitude;
		this.lattitude = lattitude;
	}


	public String getShopsId() {
		return ShopsId;
	}


	public void setShopsId(String shopsId) {
		ShopsId = shopsId;
	}


	public String getShopsName() {
		return ShopsName;
	}


	public void setShopsName(String shopsName) {
		ShopsName = shopsName;
	}


	public String getOwner() {
		return Owner;
	}


	public void setOwner(String owner) {
		Owner = owner;
	}


	public String getAddress() {
		return Address;
	}


	public void setAddress(String address) {
		Address = address;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getLattitude() {
		return lattitude;
	}


	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}
	
	
	
	

}
