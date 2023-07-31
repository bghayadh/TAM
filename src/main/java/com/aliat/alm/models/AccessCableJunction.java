package com.aliat.alm.models;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCESS_CABLES_JUNCTIONS")
public class AccessCableJunction {
	
	@Id
	@Column(name = "JUNCTION_ROW_ID", nullable = false)
	private String junctionRowID;
	
	@Column(name = "FIBER_CABLE_ID")
	private String fibercableID;
	
	@Column(name = "JUNCTION_ID")
	private String junctionID;
	
	@Column(name = "JUNCTION_NAME")
	private String junctionName;

	

	public AccessCableJunction(String fibercableID, String junctionID, String junctionName, String junctionRowID) {
		super();
		this.fibercableID = fibercableID;
		this.junctionID = junctionID;
		this.junctionName = junctionName;
		this.junctionRowID = junctionRowID;
	}

	public AccessCableJunction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFibercableID() {
		return fibercableID;
	}

	public void setFibercableID(String fibercableID) {
		this.fibercableID = fibercableID;
	}

	public String getJunctionID() {
		return junctionID;
	}

	public void setJunctionID(String junctionID) {
		this.junctionID = junctionID;
	}

	public String getJunctionName() {
		return junctionName;
	}

	public void setJunctionName(String junctionName) {
		this.junctionName = junctionName;
	}

	public String getJunctionRowID() {
		return junctionRowID;
	}

	public void setJunctionRowID(String junctionRowID) {
		this.junctionRowID = junctionRowID;
	}
	
	


	



	
	
}
