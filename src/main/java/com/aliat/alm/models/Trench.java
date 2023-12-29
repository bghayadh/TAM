package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRENCH")
public class Trench {
	
	@Id
	@Column(name = "TRENCH_ID", nullable = false)
	private String trenchID;
	
	@Column(name = "TRENCH_NAME")
	private String trenchName;
	
	@Column(name = "SOURCE_WARE_ID")
	private String sourceWareId;
	
	@Column(name = "SOURCE_ID")
	private String sourceId;
	
	@Column(name = "SOURCE_NAME")
	private String sourceName;
	
	@Column(name = "DESTINATION_WARE_ID")
	private String destinationWareId;
	
	@Column(name = "DESTINATION_ID")
	private String destinationId;
	
	@Column(name = "DESTINATION_NAME")
	private String destinationName;
	
	@Column(name = "SOURCE_LONGITUDE")
	private String srcLong;

	@Column(name = "SOURCE_LATITUDE")
	private String srcLat;
	
	@Column(name = "DESTINATION_LONGITUDE")
	private String destLong;

	@Column(name = "DESTINATION_LATITUDE")
	private String destLat;
	
	@Column(name = "SOURCE_CITY")
	private String srcCity;
	
	@Column(name = "DESTINATION_CITY")
	private String destCity;
	
	@Column(name = "NUM_DUCTS")
	private double numDucts;
	
	@Column(name = "MAX_CAPACITY")
	private double maxCapacity;
	
	@Column(name = "LENGTH")
	private double length;
	
	@Column(name = "PROJECT_ID")
	private String projectId;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifieddate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_MODIFIED_BY")
	private String lastmodifiedBy;
	
	@Column(name = "TOTAL_DRIVING_DISTANCE")
	private double totaldriving;
	
	@Column(name = "TOTAL_GEO_DISTANCE")
	private double totalGeoDist;
	
	@Column(name = "LAST_AUXILIARY_TO_DESTINATION_DISTANCE")
	private double lastAuxToDestDistance;
	
	@Column(name = "LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE")
	private double lastAuxToDestDrivDistance;
	
	@Column(name = "DRAWING_TYPE")
	private String drawingtype;
	
	@Column(name = "OWNER")
	private String trenchOwner;
	
	@Column(name = "TRENCH_INSTALLER")
	private String trenchEngineerName;
	
	@Column(name = "TRENCH_ENGINEER_NAME")
	private String trenchInstaller;
	
	
	
	
	
	
	public Trench() {
		super();
	}






	public Trench(String trenchID, String trenchName, String sourceWareId, String sourceId, String sourceName,
			String destinationWareId, String destinationId, String destinationName, String srcLong, String srcLat,
			String destLong, String destLat, String srcCity, String destCity, double numDucts, double maxCapacity,
			double length, String projectId, Timestamp creationDate, Timestamp lastModifieddate, String createdBy,
			String lastmodifiedBy, double totaldriving, double totalGeoDist, double lastAuxToDestDistance,
			double lastAuxToDestDrivDistance, String drawingtype, String trenchOwner, String trenchEngineerName,
			String trenchInstaller) {
		super();
		this.trenchID = trenchID;
		this.trenchName = trenchName;
		this.sourceWareId = sourceWareId;
		this.sourceId = sourceId;
		this.sourceName = sourceName;
		this.destinationWareId = destinationWareId;
		this.destinationId = destinationId;
		this.destinationName = destinationName;
		this.srcLong = srcLong;
		this.srcLat = srcLat;
		this.destLong = destLong;
		this.destLat = destLat;
		this.srcCity = srcCity;
		this.destCity = destCity;
		this.numDucts = numDucts;
		this.maxCapacity = maxCapacity;
		this.length = length;
		this.projectId = projectId;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.createdBy = createdBy;
		this.lastmodifiedBy = lastmodifiedBy;
		this.totaldriving = totaldriving;
		this.totalGeoDist = totalGeoDist;
		this.lastAuxToDestDistance = lastAuxToDestDistance;
		this.lastAuxToDestDrivDistance = lastAuxToDestDrivDistance;
		this.drawingtype = drawingtype;
		this.trenchOwner = trenchOwner;
		this.trenchEngineerName = trenchEngineerName;
		this.trenchInstaller = trenchInstaller;
	}






	public String getTrenchID() {
		return trenchID;
	}






	public void setTrenchID(String trenchID) {
		this.trenchID = trenchID;
	}






	public String getTrenchName() {
		return trenchName;
	}






	public void setTrenchName(String trenchName) {
		this.trenchName = trenchName;
	}






	public String getSourceWareId() {
		return sourceWareId;
	}






	public void setSourceWareId(String sourceWareId) {
		this.sourceWareId = sourceWareId;
	}






	public String getSourceId() {
		return sourceId;
	}






	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}






	public String getSourceName() {
		return sourceName;
	}






	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}






	public String getDestinationWareId() {
		return destinationWareId;
	}






	public void setDestinationWareId(String destinationWareId) {
		this.destinationWareId = destinationWareId;
	}






	public String getDestinationId() {
		return destinationId;
	}






	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}






	public String getDestinationName() {
		return destinationName;
	}






	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}






	public String getSrcLong() {
		return srcLong;
	}






	public void setSrcLong(String srcLong) {
		this.srcLong = srcLong;
	}






	public String getSrcLat() {
		return srcLat;
	}






	public void setSrcLat(String srcLat) {
		this.srcLat = srcLat;
	}






	public String getDestLong() {
		return destLong;
	}






	public void setDestLong(String destLong) {
		this.destLong = destLong;
	}






	public String getDestLat() {
		return destLat;
	}






	public void setDestLat(String destLat) {
		this.destLat = destLat;
	}






	public String getSrcCity() {
		return srcCity;
	}






	public void setSrcCity(String srcCity) {
		this.srcCity = srcCity;
	}






	public String getDestCity() {
		return destCity;
	}






	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}






	public double getNumDucts() {
		return numDucts;
	}






	public void setNumDucts(double numDucts) {
		this.numDucts = numDucts;
	}






	public double getMaxCapacity() {
		return maxCapacity;
	}






	public void setMaxCapacity(double maxCapacity) {
		this.maxCapacity = maxCapacity;
	}






	public double getLength() {
		return length;
	}






	public void setLength(double length) {
		this.length = length;
	}






	public String getProjectId() {
		return projectId;
	}






	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}






	public Timestamp getCreationDate() {
		return creationDate;
	}






	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}






	public Timestamp getLastModifieddate() {
		return lastModifieddate;
	}






	public void setLastModifieddate(Timestamp lastModifieddate) {
		this.lastModifieddate = lastModifieddate;
	}






	public String getCreatedBy() {
		return createdBy;
	}






	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}






	public String getLastmodifiedBy() {
		return lastmodifiedBy;
	}






	public void setLastmodifiedBy(String lastmodifiedBy) {
		this.lastmodifiedBy = lastmodifiedBy;
	}






	public double getTotaldriving() {
		return totaldriving;
	}






	public void setTotaldriving(double totaldriving) {
		this.totaldriving = totaldriving;
	}






	public double getTotalGeoDist() {
		return totalGeoDist;
	}






	public void setTotalGeoDist(double totalGeoDist) {
		this.totalGeoDist = totalGeoDist;
	}






	public double getLastAuxToDestDistance() {
		return lastAuxToDestDistance;
	}






	public void setLastAuxToDestDistance(double lastAuxToDestDistance) {
		this.lastAuxToDestDistance = lastAuxToDestDistance;
	}






	public double getLastAuxToDestDrivDistance() {
		return lastAuxToDestDrivDistance;
	}






	public void setLastAuxToDestDrivDistance(double lastAuxToDestDrivDistance) {
		this.lastAuxToDestDrivDistance = lastAuxToDestDrivDistance;
	}






	public String getDrawingtype() {
		return drawingtype;
	}






	public void setDrawingtype(String drawingtype) {
		this.drawingtype = drawingtype;
	}






	public String getTrenchOwner() {
		return trenchOwner;
	}






	public void setTrenchOwner(String trenchOwner) {
		this.trenchOwner = trenchOwner;
	}






	public String getTrenchEngineerName() {
		return trenchEngineerName;
	}






	public void setTrenchEngineerName(String trenchEngineerName) {
		this.trenchEngineerName = trenchEngineerName;
	}






	public String getTrenchInstaller() {
		return trenchInstaller;
	}






	public void setTrenchInstaller(String trenchInstaller) {
		this.trenchInstaller = trenchInstaller;
	}
	

}
