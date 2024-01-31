package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "AR_SERIAL_NUMBER")
public class ArSerialNumber {

	@Id
	@Column(name = "SERIAL_ID", nullable = false)
	private String serialId;
	
	@Column(name = "SERIAL_NUMBER", nullable = false)
	private String serialNumber;
	
	@Column(name = "MODEL", nullable = false)
	private String Model;
	
	@Column(name = "PART_NUMBER", nullable = false)
	private String partNumber;
	

	
	@Column(name = "SITE", nullable = false)
	private String site;
	
	@Column(name = "POSITION", nullable = false)
	private String position;
	
	@Column(name = "AR_ID", nullable = false)
	private String arID;
	
	public ArSerialNumber() {	
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getModel() {
		return Model;
	}

	public void setModel(String model) {
		Model = model;
	}
	
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getArID() {
		return arID;
	}

	public void setArID(String arID) {
		this.arID = arID;
	}
	
	public ArSerialNumber(String serialId, String serialNumber, String Model, String partNumber, String site,String position, String arID) {
		super();
		this.serialId = serialId;
		this.serialNumber = serialNumber;
		this.Model = Model;
		this.partNumber = partNumber;
	this.site = site;
		this.position = position;
		this.arID = arID;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	
	
	

}


