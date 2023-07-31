package com.aliat.alm.models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AGENT")
public class Agent {

	@Id
	@Column(name = "AGENT_ID", nullable = false)
	private String agentId;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	

	@Column(name = "LAST_NAME")
	private String lastName;
	

	@Column(name = "ADDRESS")
	private String address;
	

	@Column(name = "EMAIL")
	private String email;
	

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;
	

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	
	@Column(name = "DISPLAY_NAME")
	private String displayName;
	
	@Column(name = "STATUS")
	private String agnStatus;
	
	@Column(name = "REGION_ID")
	private String regionid;
	
	@Column(name = "REGION_NAME")
	private String regionname;
	
	@Column(name = "MSISDN")
	private String MSISDN;
	
	@Column(name = "FULL_NAME")
	private String fullName;
	
	@Column(name = "PIN_CODE")
	private String PIN;
	
	@Column(name = "AGENT_IMAGE")
	private String AgentImage;
	
	@Column(name = "AGENT_FRONT_ID")
	private String agentFrontId;
	
	@Column(name = "AGENT_BACK_ID")
	private String agentBackId;
	
	@Column(name = "LONGITUDE")
	private String agentLng;
	
	@Column(name = "LATITUDE")
	private String agentLat;
	
	@Column(name = "FRONT_SIDE_ID_STATUS")
	private String frontsideStatus;
	
	@Column(name = "BACK_SIDE_ID_STATUS")
	private String backsideStatus;
	

	@Column(name = "AGENT_IMAGE_STATUS")
	private String imagestatus;
	
	@Column(name = "SUPERAGENT ")
	private String superAgent;
	
	@Column(name="CAPTURE_SPEED")
	private char captureSpeed;
	
	@Column(name="SITE_ENGINEER")
	private char siteEngineer;
	
	@Column(name="RUNNING_INTERVAL")
	private String runningInterval;
	
	@Column(name="CAPTURE_COVERAGE")
	private char captureCoverage;
	
	@Column(name="COVERAGE_RUNNING_INTERVAL")
	private String coverageRunningInterval;
	
	public Agent() {
		super();
		// TODO Auto-generated constructor stub
	}


	


	public Agent(String agentId, String firstName, String lastName, String address, String email, Timestamp createDate,
			Timestamp lastModifiedDate, String displayName, String agnStatus, String regionid, String regionname,
			String mSISDN, String fullName, String pIN, String agentImage, String agentFrontId, String agentBackId,
			String agentLng, String agentLat, String frontsideStatus, String backsideStatus, String imagestatus,
			String superAgent, char captureSpeed, char siteEngineer, String runningInterval, char captureCoverage,
			String coverageRunningInterval) {
		super();
		this.agentId = agentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.createDate = createDate;
		this.lastModifiedDate = lastModifiedDate;
		this.displayName = displayName;
		this.agnStatus = agnStatus;
		this.regionid = regionid;
		this.regionname = regionname;
		MSISDN = mSISDN;
		this.fullName = fullName;
		PIN = pIN;
		AgentImage = agentImage;
		this.agentFrontId = agentFrontId;
		this.agentBackId = agentBackId;
		this.agentLng = agentLng;
		this.agentLat = agentLat;
		this.frontsideStatus = frontsideStatus;
		this.backsideStatus = backsideStatus;
		this.imagestatus = imagestatus;
		this.superAgent = superAgent;
		this.captureSpeed = captureSpeed;
		this.siteEngineer = siteEngineer;
		this.runningInterval = runningInterval;
		this.captureCoverage = captureCoverage;
		this.coverageRunningInterval = coverageRunningInterval;
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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	public String getStatus() {
		return agnStatus;
	}


	public void setStatus(String agnStatus) {
		this.agnStatus = agnStatus;
	}


	public String getMSISDN() {
		return MSISDN;
	}


	public void setMSISDN(String mSISDN) {
		MSISDN = mSISDN;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getPIN() {
		return PIN;
	}


	public void setPIN(String pIN) {
		PIN = pIN;
	}


	public String getAgentImage() {
		return AgentImage;
	}


	public void setAgentImage(String agentImage) {
		AgentImage = agentImage;
	}


	public String getAgentFrontId() {
		return agentFrontId;
	}


	public void setAgentFrontId(String agentFrontId) {
		this.agentFrontId = agentFrontId;
	}


	public String getAgentBackId() {
		return agentBackId;
	}


	public void setAgentBackId(String agentBackId) {
		this.agentBackId = agentBackId;
	}


	public String getAgentLng() {
		return agentLng;
	}


	public void setAgentLng(String agentLng) {
		this.agentLng = agentLng;
	}


	public String getAgentLat() {
		return agentLat;
	}


	public void setAgentLat(String agentLat) {
		this.agentLat = agentLat;
	}


	public String getFrontsideStatus() {
		return frontsideStatus;
	}


	public void setFrontsideStatus(String frontsideStatus) {
		this.frontsideStatus = frontsideStatus;
	}


	public String getBacksideStatus() {
		return backsideStatus;
	}

	public void setBacksideStatus(String backsideStatus) {
		this.backsideStatus = backsideStatus;
	}


	public String getSuperAgent() {
		return superAgent;
	}


	public void setSuperAgent(String superAgent) {
		this.superAgent = superAgent;
	}


	public char getCaptureSpeed() {
		return captureSpeed;
	}


	public void setCaptureSpeed(char captureSpeed) {
		this.captureSpeed = captureSpeed;
	}


	public char getSiteEngineer() {
		return siteEngineer;
	}


	public void setSiteEngineer(char siteEngineer) {
		this.siteEngineer = siteEngineer;
	}


	public String getRunningInterval() {
		return runningInterval;
	}


	public void setRunningInterval(String runningInterval) {
		this.runningInterval = runningInterval;
	}


	public char getCaptureCoverage() {
		return captureCoverage;
	}


	public void setCaptureCoverage(char captureCoverage) {
		this.captureCoverage = captureCoverage;
	}


	public String getCoverageRunningInterval() {
		return coverageRunningInterval;
	}


	public void setCoverageRunningInterval(String coverageRunningInterval) {
		this.coverageRunningInterval = coverageRunningInterval;
	}


	public String getImagestatus() {
		return imagestatus;
	}


	public void setImagestatus(String imagestatus) {
		this.imagestatus = imagestatus;
	}



	public String getRegionid() {
		return regionid;
	}




	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}




	public String getRegionname() {
		return regionname;
	}




	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}



	
	
}
