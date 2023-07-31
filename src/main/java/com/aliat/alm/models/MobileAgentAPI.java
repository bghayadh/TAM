package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AGENT")
public class MobileAgentAPI {

	@Id
	@Column(name = "AGENT_ID", nullable = false)
	private String agentId;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "DISPLAY_NAME")
	private String displayName;
	
	
	@Column(name = "ADDRESS")
	private String Address;
	
	@Column(name = "EMAIL")
	private String Email;
	
	@Column(name = "MSISDN")
	private String Msisdn;
	
	@Column(name = "CREATE_DATE")
	private Timestamp createDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	
	@Column(name = "LONGITUDE")
	private String Longitude;
	
	@Column(name = "LATITUDE")
	private String Latitude;
	
	@Column(name = "REGION_NAME")
	private String regionName;
	
	@Column(name = "REGION_ID")
	private String regionID;
	
	@Column(name = "STATUS")
	private String Status;
	
	@Column(name = "PIN_CODE")
	private String pinCode;
	
	@Column(name = "VERIFICATION_CODE")
	private String verificationCode;
	
	@Column(name = "AGENT_IMAGE")
	private String agentImage;
	
	@Column(name = "AGENT_FRONT_ID")
	private String agentfrontID;
	
	@Column(name = "AGENT_BACK_ID")
	private String agentbackID;
	
	@Column(name = "FULL_NAME")
	private String fullName;
	
	@Column(name = "AGENT_IMAGE_STATUS")
	private String agentImagestatus;
	
	@Column(name = "FRONT_SIDE_ID_STATUS")
	private String agentfrontIDstatus;
	
	@Column(name = "BACK_SIDE_ID_STATUS")
	private String agentbackIDstatus;
	
	
	public MobileAgentAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	public MobileAgentAPI(String agentId,String firstName, String lastName, String displayName, String address,
			String email, String msisdn, String longitude, String latitude, String regionName, String regionID,
			String status, String pinCode, String agentImage, String agentfrontID, String agentbackID, String verificationCode, Timestamp creationdate , Timestamp lastmodifieddate,
			String fullName, String agentImagestatus , String agentfrontIDstatus, String agentbackIDstatus ) {
		super();
		
		this.agentId = agentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.displayName = displayName;
		this.Address = address;
		this.Email = email;
		this.Msisdn = msisdn;
		this.Longitude = longitude;
		this.Latitude = latitude;
		this.regionName = regionName;
		this.regionID = regionID;
		this.Status = status;
		this.pinCode = pinCode;
		this.agentImage = agentImage;
		this.agentfrontID = agentfrontID;
		this.agentbackID = agentbackID;
		this.verificationCode=verificationCode;
		this.createDate=creationdate;
		this.lastModifiedDate=lastmodifieddate;
		this.fullName=fullName;
		this.agentImagestatus=agentImagestatus;
		this.agentfrontIDstatus=agentfrontIDstatus;
		this.agentbackIDstatus=agentbackIDstatus;
	}

	public String getAgentId() {
		return agentId;
	}




	public void setAgentId(String agentId) {
		this.agentId = agentId;
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


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	public String getAddress() {
		return Address;
	}


	public void setAddress(String address) {
		Address = address;
	}


	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
	}


	public String getMsisdn() {
		return Msisdn;
	}


	public void setMsisdn(String msisdn) {
		Msisdn = msisdn;
	}


	public String getLongitude() {
		return Longitude;
	}


	public void setLongitude(String longitude) {
		Longitude = longitude;
	}


	public String getLatitude() {
		return Latitude;
	}


	public void setLatitude(String latitude) {
		Latitude = latitude;
	}


	public String getRegionName() {
		return regionName;
	}


	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}


	public String getRegionID() {
		return regionID;
	}


	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}


	public String getStatus() {
		return Status;
	}


	public void setStatus(String status) {
		Status = status;
	}
	public String getPinCode() {
		return pinCode;
	}


	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}



	public String getAgentImage() {
		return agentImage;
	}


	public void setAgentImage(String agentImage) {
		this.agentImage = agentImage;
	}


	public String getAgentfrontID() {
		return agentfrontID;
	}


	public void setAgentfrontID(String agentfrontID) {
		this.agentfrontID = agentfrontID;
	}


	public String getAgentbackID() {
		return agentbackID;
	}


	public void setAgentbackID(String agentbackID) {
		this.agentbackID = agentbackID;
	}
	
	
	public String getVerificationCode() {
		return verificationCode;
	}



	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}




	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}




	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}




	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getFullName() {
		return fullName;
	}




	public void setFullName(String fullName) {
		this.fullName = fullName;
	}




	public String getAgentImagestatus() {
		return agentImagestatus;
	}




	public void setAgentImagestatus(String agentImagestatus) {
		this.agentImagestatus = agentImagestatus;
	}




	public String getAgentfrontIDstatus() {
		return agentfrontIDstatus;
	}




	public void setAgentfrontIDstatus(String agentfrontIDstatus) {
		this.agentfrontIDstatus = agentfrontIDstatus;
	}




	public String getAgentbackIDstatus() {
		return agentbackIDstatus;
	}




	public void setAgentbackIDstatus(String agentbackIDstatus) {
		this.agentbackIDstatus = agentbackIDstatus;
	}
	
	
}
