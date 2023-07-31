package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import oracle.sql.TIMESTAMP;

@Entity
@Table(name = "CLIENTS")
public class MobileClientAPI {

	@Id
	@Column(name= "CLIENT_ID", nullable = false)
	private String clientid;
	
	@Column(name="DISPLAY_NAME")
	private String displayName;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name= "MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name="DEPARTMENT")
	private String department;
	
	@Column(name="PHYSICAL_LOCATION")
	private String physicalLocation;
	
	@Column(name="CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name="LAST_MODIFIED_DATE")
	private Timestamp lastModified;
	
	@Column(name="DESCREPTION")
	private String description;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;
	
	@Column(name="POSTAL_ADDRESS")
	private String postalAddress;
	
	@Column(name="NATIONALITY")
	private String nationality;
	
	@Column(name="MIDDLE_NAME")
	private String middleName;
	
	@Column(name="DATE_OF_BIRTH")
	private Timestamp dateofBirth;
	
	@Column(name="ALTERNATIVE_NUMBER")
	private String alternativeNumber;
	
	@Column(name="SIGNATURE")
	private String signature;
	
	@Column(name="CLIENT_ID_NUMBER")
	private String clientIDNumber;
	
	@Column(name = "AGENT_NUMBER")
	private String agentNumber;
	
	@Column(name="ID_FRONT_SIDE_PHOTO")
	private String frontID;
	
	@Column(name="ID_BACK_SIDE_PHOTO")
	private String backID;
	
	
	@Column(name="SIGNATURE_STATUS")
	private String signatureStatus;
	
	@Column(name="BACK_SIDE_ID_STATUS")
	private String backIDStatus;
	
	@Column(name="FRONT_SIDE_ID_STATUS")
	private String frontIDStatus;
	
	@Column(name="USSD_STATUS")
	private String ussdStatus;
	
	@Column(name="CLIENT_PHOTO")
	private String clientPhoto;
	
	@Column(name="CLIENT_PHOTO_STATUS")
	private String clientStatus;
	
	@Column(name="LONGITUDE")
	private String longitude;
	
	@Column(name="LATITUDE")
	private String latitude;
	
	@Column(name="SELLING_LONGITUDE")
	private String sellingLongitude;
	
	@Column(name="SELLING_LATITUDE")
	private String sellingLatitude;
	
	@Column(name ="ID_TYPE")
	private String idType;
	
	@Column(name = "PASSPORT_NUMBER")
	private String passNumber;
	
	@Column(name = "AREA_ID")
	private String areaID;
	
	@Column(name="AREA_NAME")
	private String areaName;
	
	@Column(name="REGION_ID")
	private String regionID;
	
	@Column (name = "REGION_NAME")
	private String regionName;

	@Column (name = "CID")
	private String cid;
	
	@Column (name = "LAC")
	private String lac;
	
	
	public MobileClientAPI() {
		super();
		// TODO Auto-generated constructor stub
	}



	public MobileClientAPI(String clientid, String displayName, String firstName, String lastName, String mobileNumber,
			String department, String physicalLocation, Timestamp createdDate, Timestamp lastModified,
			String description, String status, String gender, String emailAddress, String postalAddress,
			String nationality, String middleName, Timestamp dateofBirth, String alternativeNumber, String signature,
			String clientIDNumber, String agentNumber, String frontID, String backID, String signatureStatus,
			String backIDStatus, String frontIDStatus, String ussdStatus, String clientPhoto, String clientStatus,
			String longitude, String latitude, String sellingLongitude, String sellingLatitude, String idType,
			String passNumber, String areaID, String areaName, String regionID, String regionName,String cid,String lac) {
		super();
		this.clientid = clientid;
		this.displayName = displayName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.department = department;
		this.physicalLocation = physicalLocation;
		this.createdDate = createdDate;
		this.lastModified = lastModified;
		this.description = description;
		this.status = status;
		this.gender = gender;
		this.emailAddress = emailAddress;
		this.postalAddress = postalAddress;
		this.nationality = nationality;
		this.middleName = middleName;
		this.dateofBirth = dateofBirth;
		this.alternativeNumber = alternativeNumber;
		this.signature = signature;
		this.clientIDNumber = clientIDNumber;
		this.agentNumber = agentNumber;
		this.frontID = frontID;
		this.backID = backID;
		this.signatureStatus = signatureStatus;
		this.backIDStatus = backIDStatus;
		this.frontIDStatus = frontIDStatus;
		this.ussdStatus = ussdStatus;
		this.clientPhoto = clientPhoto;
		this.clientStatus = clientStatus;
		this.longitude = longitude;
		this.latitude = latitude;
		this.sellingLongitude = sellingLongitude;
		this.sellingLatitude = sellingLatitude;
		this.idType = idType;
		this.passNumber = passNumber;
		this.areaID = areaID;
		this.areaName = areaName;
		this.regionID = regionID;
		this.regionName = regionName;
		this.cid = cid;
		this.lac = lac;
	}



	public String getClientid() {
		return clientid;
	}



	public void setClientid(String clientid) {
		this.clientid = clientid;
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



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getMobileNumber() {
		return mobileNumber;
	}



	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}



	public String getDepartment() {
		return department;
	}



	public void setDepartment(String department) {
		this.department = department;
	}



	public String getPhysicalLocation() {
		return physicalLocation;
	}



	public void setPhysicalLocation(String physicalLocation) {
		this.physicalLocation = physicalLocation;
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



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public String getEmailAddress() {
		return emailAddress;
	}



	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}



	public String getPostalAddress() {
		return postalAddress;
	}



	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}



	public String getNationality() {
		return nationality;
	}



	public void setNationality(String nationality) {
		this.nationality = nationality;
	}



	public String getMiddleName() {
		return middleName;
	}



	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}



	public Timestamp getDateofBirth() {
		return dateofBirth;
	}



	public void setDateofBirth(Timestamp dateofBirth) {
		this.dateofBirth = dateofBirth;
	}



	public String getAlternativeNumber() {
		return alternativeNumber;
	}



	public void setAlternativeNumber(String alternativeNumber) {
		this.alternativeNumber = alternativeNumber;
	}



	public String getSignature() {
		return signature;
	}



	public void setSignature(String signature) {
		this.signature = signature;
	}



	public String getClientIDNumber() {
		return clientIDNumber;
	}



	public void setClientIDNumber(String clientIDNumber) {
		this.clientIDNumber = clientIDNumber;
	}



	public String getAgentNumber() {
		return agentNumber;
	}



	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}



	public String getFrontID() {
		return frontID;
	}



	public void setFrontID(String frontID) {
		this.frontID = frontID;
	}



	public String getBackID() {
		return backID;
	}



	public void setBackID(String backID) {
		this.backID = backID;
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



	public String getUssdStatus() {
		return ussdStatus;
	}



	public void setUssdStatus(String ussdStatus) {
		this.ussdStatus = ussdStatus;
	}



	public String getClientPhoto() {
		return clientPhoto;
	}



	public void setClientPhoto(String clientPhoto) {
		this.clientPhoto = clientPhoto;
	}



	public String getClientStatus() {
		return clientStatus;
	}



	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
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



	public void setSellingLatitude(String sellingLatitude) {
		this.sellingLatitude = sellingLatitude;
	}



	public String getIdType() {
		return idType;
	}



	public void setIdType(String idType) {
		this.idType = idType;
	}



	public String getPassNumber() {
		return passNumber;
	}



	public void setPassNumber(String passNumber) {
		this.passNumber = passNumber;
	}



	public String getAreaID() {
		return areaID;
	}



	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}



	public String getAreaName() {
		return areaName;
	}



	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}



	public String getRegionID() {
		return regionID;
	}



	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}



	public String getRegionName() {
		return regionName;
	}



	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}



	public String getCid() {
		return cid;
	}



	public void setCid(String cid) {
		this.cid = cid;
	}



	public String getLac() {
		return lac;
	}



	public void setLac(String lac) {
		this.lac = lac;
	}
	
	
	
}