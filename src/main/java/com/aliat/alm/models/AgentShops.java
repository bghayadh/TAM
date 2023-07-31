package com.aliat.alm.models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AGENT_SHOPS")
public class AgentShops {


	@Id
	@Column(name = "AGENT_SHOPS_ID", nullable = false)
	private String agentShopsId;
	
	@Column(name = "SHOPS_ID")
	private String shopsId;
	

	@Column(name = "SHOPS_NAME")
	private String Shopsname;
	
	@Column(name = "SHOPS_Long")
	private String Longtitude;
	
	@Column(name = "SHOPS_Lat")
	private String Latitude;
	

	@Column(name = "AGENT_ID")
	private String agentId;
	

	@Column(name = "CREATION_DATE")
	private Timestamp createDate;
	

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;


	public AgentShops() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AgentShops(String agentShopsId, String shopsId, String shopsname, String longtitude, String latitude,
			String agentId, Timestamp createDate, Timestamp lastModifiedDate) {
		super();
		this.agentShopsId = agentShopsId;
		this.shopsId = shopsId;
		Shopsname = shopsname;
		Longtitude = longtitude;
		Latitude = latitude;
		this.agentId = agentId;
		this.createDate = createDate;
		this.lastModifiedDate = lastModifiedDate;
	}


	public String getAgentShopsId() {
		return agentShopsId;
	}


	public void setAgentShopsId(String agentShopsId) {
		this.agentShopsId = agentShopsId;
	}


	public String getShopsId() {
		return shopsId;
	}


	public void setShopsId(String shopsId) {
		this.shopsId = shopsId;
	}


	public String getShopsname() {
		return Shopsname;
	}


	public void setShopsname(String shopsname) {
		Shopsname = shopsname;
	}


	public String getLongtitude() {
		return Longtitude;
	}


	public void setLongtitude(String longtitude) {
		Longtitude = longtitude;
	}


	public String getLatitude() {
		return Latitude;
	}


	public void setLatitude(String latitude) {
		Latitude = latitude;
	}


	public String getAgentId() {
		return agentId;
	}


	public void setAgentId(String agentId) {
		this.agentId = agentId;
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



}
