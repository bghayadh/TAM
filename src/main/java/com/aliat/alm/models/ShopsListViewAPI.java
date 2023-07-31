package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SHOPS")

public class ShopsListViewAPI {

	@Id
	@Column(name = "SHOPS_ID", nullable = false)
	private String ShopsId;
    private int vfrom;
    private int vto;
    private String date;
    private String agentNumber;
    private String shopName;
    private String owner;
    
    
	public ShopsListViewAPI() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ShopsListViewAPI(String shopsId, int vfrom, int vto,String date,String agentNumber,String shopName,String owner) {
		super();
		ShopsId = shopsId;
		this.vfrom = vfrom;
		this.vto = vto;
		this.date=date;
		this.agentNumber=agentNumber;
		this.shopName=shopName;
		this.owner=owner;
	}


	public String getShopsId() {
		return ShopsId;
	}


	public void setShopsId(String shopsId) {
		ShopsId = shopsId;
	}


	public int getVfrom() {
		return vfrom;
	}


	public void setVfrom(int vfrom) {
		this.vfrom = vfrom;
	}


	public int getVto() {
		return vto;
	}


	public void setVto(int vto) {
		this.vto = vto;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getAgentNumber() {
		return agentNumber;
	}


	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}


	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}
    
	
	
    
}
