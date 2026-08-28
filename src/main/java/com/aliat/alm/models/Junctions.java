package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JUNCTION", schema = "DEMO")
public class Junctions {

	@Id
	@Column(name = "JUNCTION_ID", nullable = false, length = 200)
	private String junctionId;

	@Column(name = "JUNCTION_NAME", length = 200)
	private String junctionName;

	@Column(name = "PHYSICAL_LAYER_ID", length = 200)
	private String physicalLayerId;

	@Column(name = "PHYSICAL_LAYER_NAME", length = 200)
	private String physicalLayerName;

	@Column(name = "LONGITUDE", length = 200)
	private String longitude;

	@Column(name = "OWNER", length = 100)
	private String owner;

	@Column(name = "JUNC_INSTALLER", length = 100)
	private String juncInstaller;

	@Column(name = "JUNC_ENGINEER_NAME", length = 100)
	private String juncEngineerName;

	@Column(name = "LATITUDE", length = 200)
	private String latitude;

	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;

	@Column(name = "CAPACITY")
	private Integer capacity;

	@Column(name = "JUNCTION_NUMBER")
	private Integer junctionNumber;

	@Column(name = "CITY", length = 200)
	private String city;

	@Column(name = "PROJECT_ID", nullable = false, length = 100)
	private String projectId;

	@Column(name = "JUNCTION_TYPE", length = 100)
	private String junctionType;

	public Junctions() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Junctions(String junctionId, String junctionName, String physicalLayerId, String physicalLayerName,
			String longitude, String owner, String juncInstaller, String juncEngineerName, String latitude,
			Timestamp creationDate, Timestamp lastModifiedDate, Integer capacity, Integer junctionNumber, String city,
			String projectId, String junctionType) {
		super();
		this.junctionId = junctionId;
		this.junctionName = junctionName;
		this.physicalLayerId = physicalLayerId;
		this.physicalLayerName = physicalLayerName;
		this.longitude = longitude;
		this.owner = owner;
		this.juncInstaller = juncInstaller;
		this.juncEngineerName = juncEngineerName;
		this.latitude = latitude;
		this.creationDate = creationDate;
		this.lastModifiedDate = lastModifiedDate;
		this.capacity = capacity;
		this.junctionNumber = junctionNumber;
		this.city = city;
		this.projectId = projectId;
		this.junctionType = junctionType;
	}

	// =========================
	// Getters and Setters
	// =========================

	public String getJunctionId() {
		return junctionId;
	}

	public void setJunctionId(String junctionId) {
		this.junctionId = junctionId;
	}

	public String getJunctionName() {
		return junctionName;
	}

	public void setJunctionName(String junctionName) {
		this.junctionName = junctionName;
	}

	public String getPhysicalLayerId() {
		return physicalLayerId;
	}

	public void setPhysicalLayerId(String physicalLayerId) {
		this.physicalLayerId = physicalLayerId;
	}

	public String getPhysicalLayerName() {
		return physicalLayerName;
	}

	public void setPhysicalLayerName(String physicalLayerName) {
		this.physicalLayerName = physicalLayerName;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getJuncInstaller() {
		return juncInstaller;
	}

	public void setJuncInstaller(String juncInstaller) {
		this.juncInstaller = juncInstaller;
	}

	public String getJuncEngineerName() {
		return juncEngineerName;
	}

	public void setJuncEngineerName(String juncEngineerName) {
		this.juncEngineerName = juncEngineerName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getJunctionNumber() {
		return junctionNumber;
	}

	public void setJunctionNumber(Integer junctionNumber) {
		this.junctionNumber = junctionNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getJunctionType() {
		return junctionType;
	}

	public void setJunctionType(String junctionType) {
		this.junctionType = junctionType;
	}
}