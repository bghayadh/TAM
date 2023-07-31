package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WAREHOUSE")

public class SitesListViewAPI {

	@Id
	@Column(name = "WARE_ID", nullable = false)
	private String WareId;
    private int vfrom;
    private int vto;
    
    
	public SitesListViewAPI() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SitesListViewAPI(String wareId, int vfrom, int vto) {
		super();
		WareId = wareId;
		this.vfrom = vfrom;
		this.vto = vto;
	}


	public String getWareId() {
		return WareId;
	}


	public void setWareId(String wareId) {
		WareId = wareId;
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
    
	
	
    

}
