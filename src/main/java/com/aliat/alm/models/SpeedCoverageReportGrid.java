package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SPEEDCOVERAGETEST")
public class SpeedCoverageReportGrid {
	
	@Id	
	@Column(name = "cid")
	private String cid;
	
	@Column(name = "lac")
	private String lac;
	
	private String startDate;
	private String endDate;

	@Column(name = "COVERAGE_SIGNAL")
	private String av_coverageSignal; 
	
	@Column(name = "SPEED_DOWNLOAD")
	private String av_speedDown; 
	
	@Column(name = "SPEED_UPLOAD")
	private String av_speedUp;

	public SpeedCoverageReportGrid() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SpeedCoverageReportGrid(String cid, String lac, String startDate, String endDate, 
			String speedDown, String speedUp,String coverageSignal) {
		super();
		this.cid = cid;
		this.lac = lac;
		this.startDate = startDate;
		this.endDate = endDate;
		this.av_coverageSignal = coverageSignal;
		this.av_speedDown = speedDown;
		this.av_speedUp = speedUp;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCoverageSignal() {
		return av_coverageSignal;
	}

	public void setCoverageSignal(String coverageSignal) {
		this.av_coverageSignal = coverageSignal;
	}

	public String getSpeedDown() {
		return av_speedDown;
	}

	public void setSpeedDown(String speedDown) {
		this.av_speedDown = speedDown;
	}

	public String getSpeedUp() {
		return av_speedUp;
	}

	public void setSpeedUp(String speedUp) {
		this.av_speedUp = speedUp;
	}

	
	
}
