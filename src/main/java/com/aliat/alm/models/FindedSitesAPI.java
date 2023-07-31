package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WAREHOUSE")

public class FindedSitesAPI {

	@Id
	@Column(name = "WARE_ID", nullable = false)
    private String Wareid;
    private String Siteid;
    private String Warename;
    private String Wareaddress;
    private String longitude;
    private String lattitude;
    
    
	public FindedSitesAPI() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	public FindedSitesAPI(String wareid, String siteid, String warename, String wareaddress, String longitude,
			String lattitude) {
		super();
		Wareid = wareid;
		Siteid = siteid;
		Warename = warename;
		Wareaddress = wareaddress;
		this.longitude = longitude;
		this.lattitude = lattitude;
	}



	public String getWareid() {
		return Wareid;
	}



	public void setWareid(String wareid) {
		Wareid = wareid;
	}



	public String getSiteid() {
		return Siteid;
	}



	public void setSiteid(String siteid) {
		Siteid = siteid;
	}



	public String getWarename() {
		return Warename;
	}



	public void setWarename(String warename) {
		Warename = warename;
	}



	public String getWareaddress() {
		return Wareaddress;
	}



	public void setWareaddress(String wareaddress) {
		Wareaddress = wareaddress;
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
