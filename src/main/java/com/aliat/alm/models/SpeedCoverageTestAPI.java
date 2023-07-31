package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SPEED_COVERAGE_TEST")
public class SpeedCoverageTestAPI {

	@Id
	@Column(name = "SPEEDCOVERAGEID", nullable = false)
	private String SpeedCoverageId;
	
	@Column(name = "SPEEDCOVERAGE_DATE")
	private Timestamp speedCoverageDate;
	
	@Column(name = "SPEEDCOVERAGE_LAT")
	private String speedCoverageLat;
	
	@Column(name = "SPEEDCOVERAGE_LNG")
	private String speedCoverageLong; 
	
	@Column(name = "COVERAGE_SIGNAL")
	private String coverageSignal; 
	
	@Column(name = "SPEED_DOWNLOAD")
	private String speedDown; 
	
	@Column(name = "SPEED_UPLOAD")
	private String speedUp;
	
	@Column(name = "AGENT_NUMBER")
	private String agentNumber;
	
	@Column(name = "AGENT_NAME")
	private String agentName;
	
	@Column(name = "cid")
	private String cid;
	
	@Column(name = "lac")
	private String lac;
	
	@Column(name="TECHNOLOGY")
	private String technology;
	
	@Column(name="MMC")
	private String mmc;
	
	@Column(name="MNC")
	private String mnc;

	@Column(name="APP_VERSION")
	private String appVersion;
	
	
	public SpeedCoverageTestAPI() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SpeedCoverageTestAPI(String speedCoverageId, Timestamp speedCoverageDate, String speedCoverageLat,
			String speedCoverageLong, String coverageSignal, String speedDown, String speedUp,String agentNumber,String agentName,String cid,String lac,String technology,String mmc,String mnc,String appVersion) {
		super();
		SpeedCoverageId = speedCoverageId;
		this.speedCoverageDate = speedCoverageDate;
		this.speedCoverageLat = speedCoverageLat;
		this.speedCoverageLong = speedCoverageLong;
		this.coverageSignal = coverageSignal;
		this.speedDown = speedDown;
		this.speedUp = speedUp;
		this.agentNumber=agentNumber;
		this.agentName=agentName;
		this.cid=cid;
		this.lac=lac;
		this.technology=technology;
		this.mmc=mmc;
		this.mnc=mnc;
		this.appVersion=appVersion;
	}

	

	public String getSpeedCoverageId() {
		return SpeedCoverageId;
	}


	public void setSpeedCoverageId(String speedCoverageId) {
		SpeedCoverageId = speedCoverageId;
	}


	public Timestamp getSpeedCoverageDate() {
		return speedCoverageDate;
	}


	public void setSpeedCoverageDate(Timestamp speedCoverageDate) {
		this.speedCoverageDate = speedCoverageDate;
	}


	public String getSpeedCoverageLat() {
		return speedCoverageLat;
	}


	public void setSpeedCoverageLat(String speedCoverageLat) {
		this.speedCoverageLat = speedCoverageLat;
	}


	public String getSpeedCoverageLong() {
		return speedCoverageLong;
	}


	public void setSpeedCoverageLong(String speedCoverageLong) {
		this.speedCoverageLong = speedCoverageLong;
	}


	public String getCoverageSignal() {
		return coverageSignal;
	}


	public void setCoverageSignal(String coverageSignal) {
		this.coverageSignal = coverageSignal;
	}


	public String getSpeedDown() {
		return speedDown;
	}


	public void setSpeedDown(String speedDown) {
		this.speedDown = speedDown;
	}


	public String getSpeedUp() {
		return speedUp;
	}


	public void setSpeedUp(String speedUp) {
		this.speedUp = speedUp;
	}


	public String getAgentNumber() {
		return agentNumber;
	}


	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}


	public String getAgentName() {
		return agentName;
	}


	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	public String getcid() {
		return cid;
	}


	public void setcid(String cid) {
		this.cid = cid;
	}
	
	public String getlac() {
		return lac;
	}


	public void setlac(String lac) {
		this.lac = lac;
	}


	public String getTechnology() {
		return technology;
	}


	public void setTechnology(String technology) {
		this.technology = technology;
	}


	public String getMmc() {
		return mmc;
	}


	public void setMmc(String mmc) {
		this.mmc = mmc;
	}


	public String getMnc() {
		return mnc;
	}


	public void setMnc(String mnc) {
		this.mnc = mnc;
	}


	public String getAppVersion() {
		return appVersion;
	}


	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
	
	

}
