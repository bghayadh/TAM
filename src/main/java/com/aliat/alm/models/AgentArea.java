package com.aliat.alm.models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "AGENT_AREAS")
public class AgentArea {


	@Id
	@Column(name = "AGENT_AREA_ID", nullable = false)
	private String agentAreaId;
	
	@Column(name = "AREA_ID")
	private String areaId;
	

	@Column(name = "AREA_NAME")
	private String Areaname;
	
	@Column(name = "AREA_LONG")
	private String Arealong;
	
	@Column(name = "AREA_LAT")
	private String Arealat;
	
	@Column(name = "AGENT_ID")
	private String agentId;
	

	public AgentArea() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AgentArea(String agentAreaId, String areaId, String areaname, String arealong, String arealat,
			String agentId) {
		super();
		this.agentAreaId = agentAreaId;
		this.areaId = areaId;
		Areaname = areaname;
		Arealong = arealong;
		Arealat = arealat;
		this.agentId = agentId;
	}


	public String getAgentAreaId() {
		return agentAreaId;
	}


	public void setAgentAreaId(String agentAreaId) {
		this.agentAreaId = agentAreaId;
	}


	public String getAreaId() {
		return areaId;
	}


	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}


	public String getAreaname() {
		return Areaname;
	}


	public void setAreaname(String areaname) {
		Areaname = areaname;
	}


	public String getArealong() {
		return Arealong;
	}


	public void setArealong(String arealong) {
		Arealong = arealong;
	}


	public String getArealat() {
		return Arealat;
	}


	public void setArealat(String arealat) {
		Arealat = arealat;
	}


	public String getAgentId() {
		return agentId;
	}


	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}


	
	
	


}
