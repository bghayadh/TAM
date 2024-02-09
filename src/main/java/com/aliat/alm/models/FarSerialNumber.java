package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FAR_SERIAL_NUMBER")
public class FarSerialNumber {

	@Id
	@Column(name = "SERIAL_ID", nullable = false)
	private String serialId;
	
	@Column(name = "SERIAL_NUMBER", nullable = false)
	private String inputSerialNb;
	
	@Column(name = "MODEL", nullable = false)
	private String inputModel;
	
	@Column(name = "PART_NUMBER", nullable = false)
	private String inputpartNumber;
	

	
	@Column(name = "SITE", nullable = false)
	private String inputsite;
	
	@Column(name = "FAR_ID", nullable = false)
	private String farID;
	
	@Column(name = "POSITION")
	private String inputPosition;
	
	@Column(name = "MAC_ADDRESS")
	private String macAddress;
	
	
	public String getSerialId() {
		return serialId;
	}



	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}



	public String getInputSerialNb() {
		return inputSerialNb;
	}



	public void setInputSerialNb(String inputSerialNb) {
		this.inputSerialNb = inputSerialNb;
	}



	public String getInputModel() {
		return inputModel;
	}



	public void setInputModel(String inputModel) {
		this.inputModel = inputModel;
	}



	public String getInputpartNumber() {
		return inputpartNumber;
	}



	public void setInputpartNumber(String inputpartNumber) {
		this.inputpartNumber = inputpartNumber;
	}



	



	public String getInputsite() {
		return inputsite;
	}



	public void setInputsite(String inputsite) {
		this.inputsite = inputsite;
	}



	public String getFarID() {
		return farID;
	}



	public void setFarID(String farID) {
		this.farID = farID;
	}



	public String getInputPosition() {
		return inputPosition;
	}



	public void setInputPosition(String inputPosition) {
		this.inputPosition = inputPosition;
	}



	public FarSerialNumber(String serialId, String inputSerialNb, String inputModel, String inputpartNumber,
			 String inputsite, String farID, String inputPosition, String macAddress) {
		super();
		this.serialId = serialId;
		this.inputSerialNb = inputSerialNb;
		this.inputModel = inputModel;
		this.inputpartNumber = inputpartNumber;
		this.inputsite = inputsite;
		this.farID = farID;
		this.inputPosition = inputPosition;
		this.macAddress=macAddress;
	}



	public String getMacAddress() {
		return macAddress;
	}



	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}



	public FarSerialNumber() {
		// TODO Auto-generated constructor stub
	}


	
	}



	
	
	

