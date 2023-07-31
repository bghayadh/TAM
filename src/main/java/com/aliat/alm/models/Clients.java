package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "CLIENTS")
public class Clients {
	@Id
	@Column(name = "CLIENT_ID", nullable = false)
	private String clientId;
	
	@Column(name = "DISPLAY_NAME")
	private String displayName;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	

	@Column(name = "LAST_NAME")
	private String lastName;
	
	
	@Column(name = "MOBILE_NUMBER")
	private String mobile;
	
	@Column(name = "DEPARTMENT")
	private String department;
	
	@Column(name = "PHYSICAL_LOCATION")
	private String location;
	
	@Column(name = "DESCREPTION")
	private String descreption;
	
	@Column(name = "STATUS")
	private String clnStatus;

	@Column(name = "CLIENT_PHOTO")
	private String clientImage;

	@Column(name = "ID_FRONT_SIDE_PHOTO")
	private String clientFrontId;
	
	@Column(name = "ID_BACK_SIDE_PHOTO")
	private String clientBackId;
	
	@Column(name = "SIGNATURE")
	private String signature;

	@Column(name = "LONGITUDE")
	private String longitude;
	
	@Column(name = "LATITUDE")
	private String latitude;
	
	@Column(name = "SELLING_LONGITUDE")
	private String sellingLongitude;
	
	@Column(name = "SELLING_LATITUDE")
	private String sellingLatitude;

	@Column(name="SIGNATURE_STATUS")
	private String signatureStatus;
	
	@Column(name="BACK_SIDE_ID_STATUS")
	private String backIDStatus;
	
	@Column(name="FRONT_SIDE_ID_STATUS")
	private String frontIDStatus;
	
	@Column(name="CLIENT_PHOTO_STATUS")
	private String clientStatus;
	
	@Column(name="AGENT_NUMBER")
	private String agentNumber;
	
	@Column(name="CLIENT_ID_NUMBER")
	private String clientIdNumber;
	
	@Column(name="REGION_ID")
	private String regionID;

	@Column(name="REGION_NAME")
	private String regionname;
	
	@Column(name="AREA_ID")
	private String areaID;
	
	@Column(name="AREA_NAME")
	private String areaname;
	
	@Column(name="PHOTOS_APPROVAL_STATUS")
	private String photosapprovalstatus;
	
	public Clients() {
		super();
		// TODO Auto-generated constructor stub
	}





	public Clients(String clientId, String displayName, String firstName, Timestamp createdDate,
			Timestamp lastModifiedDate, String lastName, String mobile, String department, String location,
			String descreption, String clnStatus, String clientImage, String clientFrontId, String clientBackId,
			String signature, String longitude, String latitude, String sellingLongitude, String sellingLatitude,
			String signatureStatus, String backIDStatus, String frontIDStatus, String clientStatus, String agentNumber,
			String clientIdNumber, String regionID, String regionname, String areaID, String areaname,
			String photosapprovalstatus) {
		super();
		this.clientId = clientId;
		this.displayName = displayName;
		this.firstName = firstName;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.lastName = lastName;
		this.mobile = mobile;
		this.department = department;
		this.location = location;
		this.descreption = descreption;
		this.clnStatus = clnStatus;
		this.clientImage = clientImage;
		this.clientFrontId = clientFrontId;
		this.clientBackId = clientBackId;
		this.signature = signature;
		this.longitude = longitude;
		this.latitude = latitude;
		this.sellingLongitude = sellingLongitude;
		this.sellingLatitude = sellingLatitude;
		this.signatureStatus = signatureStatus;
		this.backIDStatus = backIDStatus;
		this.frontIDStatus = frontIDStatus;
		this.clientStatus = clientStatus;
		this.agentNumber = agentNumber;
		this.clientIdNumber = clientIdNumber;
		this.regionID = regionID;
		this.regionname = regionname;
		this.areaID = areaID;
		this.areaname = areaname;
		this.photosapprovalstatus = photosapprovalstatus;
	}





	public String getClientId() {
		return clientId;
	}



	public void setClientId(String clientId) {
		this.clientId = clientId;
	}



	public String getDisplayName() {
		return displayName;
	}



	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public Timestamp getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}



	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}



	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public String getDepartment() {
		return department;
	}



	public void setDepartment(String department) {
		this.department = department;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getDescreption() {
		return descreption;
	}



	public void setDescreption(String descreption) {
		this.descreption = descreption;
	}



	public String getStatus() {
		return clnStatus;
	}



	public void setStatus(String clnStatus) {
		this.clnStatus = clnStatus;
	}



	public String getClientImage() {
		return clientImage;
	}



	public void setClientImage(String clientImage) {
		this.clientImage = clientImage;
	}



	public String getClientFrontId() {
		return clientFrontId;
	}



	public void setClientFrontId(String clientFrontId) {
		this.clientFrontId = clientFrontId;
	}



	public String getClientBackId() {
		return clientBackId;
	}



	public void setClientBackId(String clientBackId) {
		this.clientBackId = clientBackId;
	}



	public String getSignature() {
		return signature;
	}



	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getSellingLongitude() {
		return sellingLongitude;
	}
	public void setSellingLongitude(String sellingLongitude) {
		this.sellingLongitude = sellingLongitude;
	}
	public String getSellingLatitude() {
		return sellingLatitude;
	}
	public void setsellingLatitude(String sellingLatitude) {
		this.sellingLatitude = sellingLatitude;
	}



	public String getSignatureStatus() {
		return signatureStatus;
	}



	public void setSignatureStatus(String signatureStatus) {
		this.signatureStatus = signatureStatus;
	}



	public String getBackIDStatus() {
		return backIDStatus;
	}



	public void setBackIDStatus(String backIDStatus) {
		this.backIDStatus = backIDStatus;
	}



	public String getFrontIDStatus() {
		return frontIDStatus;
	}



	public void setFrontIDStatus(String frontIDStatus) {
		this.frontIDStatus = frontIDStatus;
	}



	public String getClientStatus() {
		return clientStatus;
	}



	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}



	public String getAgentNumber() {
		return agentNumber;
	}



	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}



	public String getClientIdNumber() {
		return clientIdNumber;
	}



	public void setClientIdNumber(String clientIdNumber) {
		this.clientIdNumber = clientIdNumber;
	}


	public String getRegionID() {
		return regionID;
	}



	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}



	public String getRegionname() {
		return regionname;
	}



	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}



	public String getAreaID() {
		return areaID;
	}



	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}



	public String getAreaname() {
		return areaname;
	}



	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getPhotosapprovalstatus() {
		return photosapprovalstatus;
	}



	public void setPhotosapprovalstatus(String photosapprovalstatus) {
		this.photosapprovalstatus = photosapprovalstatus;
	}




	
	
	
	
	
}
