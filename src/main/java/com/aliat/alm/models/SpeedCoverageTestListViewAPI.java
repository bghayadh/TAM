package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SPEEDCOVERAGETEST")

public class SpeedCoverageTestListViewAPI {

	@Id
	@Column(name = "SPEEDCOVERAGEID", nullable = false)
	private String SpeedCoverageId;
   
    private int vfrom;
    private int vto;
    private String date;
    
    
	public SpeedCoverageTestListViewAPI() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SpeedCoverageTestListViewAPI(String speedCoverageId, int vfrom, int vto, String date) {
		super();
		SpeedCoverageId = speedCoverageId;
		this.vfrom = vfrom;
		this.vto = vto;
		this.date = date;
	}


	public String getSpeedCoverageId() {
		return SpeedCoverageId;
	}


	public void setSpeedCoverageId(String speedCoverageId) {
		SpeedCoverageId = speedCoverageId;
	}


	public int getVfrom() {
		return vfrom;
	}


	public void setVfrom(int vfrom) {
		this.vfrom = vfrom;
	}


	public int getVto() {
		return vto;
	}


	public void setVto(int vto) {
		this.vto = vto;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	
	
	
	

    
}
