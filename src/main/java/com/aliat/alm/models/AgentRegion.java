package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AGENT_REGIONS")
public class AgentRegion {


	@Id
	@Column(name = "AGENT_REGION_ID", nullable = false)
	private String agentRegionId;
	
	@Column(name = "REGION_ID")
	private String regionId;
	

	@Column(name = "REGION_NAME")
	private String Regionname;
	
	@Column(name = "REGION_CODE")
	private String Regioncode;


	@Column(name = "AGENT_ID")
	private String agentId;
	

	@Column(name = "REGION_LONG")
	private String Regionlong;
	
	@Column(name = "REGION_LAT")
	private String Regionlat;

public AgentRegion() {
	super();
	// TODO Auto-generated constructor stub
}

public AgentRegion(String agentRegionId, String regionId, String regionname, String regioncode, String agentId,
		String regionlong, String regionlat) {
	super();
	this.agentRegionId = agentRegionId;
	this.regionId = regionId;
	Regionname = regionname;
	Regioncode = regioncode;
	this.agentId = agentId;
	Regionlong = regionlong;
	Regionlat = regionlat;
}

public String getAgentRegionId() {
	return agentRegionId;
}

public void setAgentRegionId(String agentRegionId) {
	this.agentRegionId = agentRegionId;
}

public String getRegionId() {
	return regionId;
}

public void setRegionId(String regionId) {
	this.regionId = regionId;
}

public String getRegionname() {
	return Regionname;
}

public void setRegionname(String regionname) {
	Regionname = regionname;
}

public String getRegioncode() {
	return Regioncode;
}

public void setRegioncode(String regioncode) {
	Regioncode = regioncode;
}

public String getAgentId() {
	return agentId;
}

public void setAgentId(String agentId) {
	this.agentId = agentId;
}

public String getRegionlong() {
	return Regionlong;
}

public void setRegionlong(String regionlong) {
	Regionlong = regionlong;
}

public String getRegionlat() {
	return Regionlat;
}

public void setRegionlat(String regionlat) {
	Regionlat = regionlat;
}










}
