package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import oracle.sql.TIMESTAMP;

@Entity
@Table(name = "WAREHOUSE_IMAGE")


public class WarehouseImageAPI {

	@Id
	@Column(name = "PATH_NBR", nullable = false)
	private String pathNbr;
	
	@Column(name = "WARE_ID", nullable = false)
    private String Wareid;
	
	@Column(name="IMAGE_PATH")
    private String ImagePath;
	
	@Column(name="UPLOAD_DATE")
	private Timestamp UploadDate;

	
	public WarehouseImageAPI() {
		super();
		// TODO Auto-generated constructor stub
	}


	public WarehouseImageAPI(String wareid, String pathNbr, String imagePath, Timestamp uploadDate) {
		super();
		Wareid = wareid;
		this.pathNbr = pathNbr;
		ImagePath = imagePath;
		UploadDate = uploadDate;
	}


	public String getWareid() {
		return Wareid;
	}


	public void setWareid(String wareid) {
		Wareid = wareid;
	}


	public String getPathNbr() {
		return pathNbr;
	}


	public void setPathNbr(String pathNbr) {
		this.pathNbr = pathNbr;
	}


	public String getImagePath() {
		return ImagePath;
	}


	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}


	public Timestamp getUploadDate() {
		return UploadDate;
	}


	public void setUploadDate(Timestamp uploadDate) {
		UploadDate = uploadDate;
	}

	
	

}
