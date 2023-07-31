package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import oracle.sql.TIMESTAMP;

@Entity
@Table(name = "WAREHOUSE")

public class WarehouseAPI {

	@Id
	@Column(name = "WARE_ID", nullable = false)
    private String Wareid;
	
	@Column(name="CREATION_DATE")
	private Timestamp createdDate;
	
	@Column(name="LAST_MODIFY_DATE")
	private Timestamp lastModified;
	
	@Column(name="WARE_NAME")
    private String Warename;
	
	@Column(name="CITY")
    private String city;
	
	@Column(name="LONGITUDE")
    private String longitude;
	
	@Column(name="LATITUDE")
    private String lattitude;
	
	@Column(name="SITE_ID")
    private String Siteid;
	
	@Column(name="SITE")
    private String site;
	
	@Column(name="TECH_2G")
    private String tech2g;
	
	@Column(name="TECH_3G")
    private String tech3g;
	
	@Column(name="TECH_4G")
    private String tech4g;
	
	@Column(name="TECH_5G")
    private String tech5g;
    
	@Column(name="AREA_ID")
    private String Areaid;
	
	@Column(name="AREA_NAME")
    private String Areaname;
	
	@Column(name="ADDRESS")
    private String Address;
	
	@Column(name="CLUSTER_ID")
    private String Clusterid;
	
	@Column(name="CLUSTER_NAME")
    private String Clustername;
	
	@Column(name="STATUS")
    private String status;

	
	public WarehouseAPI() {
		super();
		// TODO Auto-generated constructor stub
	}


	public WarehouseAPI(String wareid, Timestamp createdDate, Timestamp lastModified, String warename, String city,
			String longitude, String lattitude, String siteid, String site, String tech2g, String tech3g, String tech4g,
			String tech5g, String areaid, String areaname, String address, String clusterid, String clustername,
			String status) {
		super();
		Wareid = wareid;
		this.createdDate = createdDate;
		this.lastModified = lastModified;
		Warename = warename;
		this.city = city;
		this.longitude = longitude;
		this.lattitude = lattitude;
		Siteid = siteid;
		this.site = site;
		this.tech2g = tech2g;
		this.tech3g = tech3g;
		this.tech4g = tech4g;
		this.tech5g = tech5g;
		Areaid = areaid;
		Areaname = areaname;
		Address = address;
		Clusterid = clusterid;
		Clustername = clustername;
		this.status = status;
	}


	public String getWareid() {
		return Wareid;
	}


	public void setWareid(String wareid) {
		Wareid = wareid;
	}


	public Timestamp getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}


	public Timestamp getLastModified() {
		return lastModified;
	}


	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}


	public String getWarename() {
		return Warename;
	}


	public void setWarename(String warename) {
		Warename = warename;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
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


	public String getSiteid() {
		return Siteid;
	}


	public void setSiteid(String siteid) {
		Siteid = siteid;
	}


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
	}


	public String getTech2g() {
		return tech2g;
	}


	public void setTech2g(String tech2g) {
		this.tech2g = tech2g;
	}


	public String getTech3g() {
		return tech3g;
	}


	public void setTech3g(String tech3g) {
		this.tech3g = tech3g;
	}


	public String getTech4g() {
		return tech4g;
	}


	public void setTech4g(String tech4g) {
		this.tech4g = tech4g;
	}


	public String getTech5g() {
		return tech5g;
	}


	public void setTech5g(String tech5g) {
		this.tech5g = tech5g;
	}


	public String getAreaid() {
		return Areaid;
	}


	public void setAreaid(String areaid) {
		Areaid = areaid;
	}


	public String getAreaname() {
		return Areaname;
	}


	public void setAreaname(String areaname) {
		Areaname = areaname;
	}


	public String getAddress() {
		return Address;
	}


	public void setAddress(String address) {
		Address = address;
	}


	public String getClusterid() {
		return Clusterid;
	}


	public void setClusterid(String clusterid) {
		Clusterid = clusterid;
	}


	public String getClustername() {
		return Clustername;
	}


	public void setClustername(String clustername) {
		Clustername = clustername;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
    
    
	
	
	
    

}
