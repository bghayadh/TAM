package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIBER_TUBES")
public class FiberTubes {

	@Id
	@Column(name = "TUBE_ID", nullable = false)
	private String tubeID;
	
	@Column(name = "FIBER_CABLE_ID")
	private String fibercableID;

	@Column(name = "SOURCE_LONGITUDE")
	private String srcLong;

	@Column(name = "SOURCE_LATITUDE")
	private String srcLat;
	
	@Column(name = "DESTINATION_LONGITUDE")
	private String destLong;

	@Column(name = "DESTINATION_LATITUDE")
	private String destLat;
	
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
	
	@Column(name = "TUBE_NAME")
	private String tubeName;
	
	@Column(name = "SOURCE_CITY")
	private String srcCity;
	
	@Column(name = "DESTINATION_CITY")
	private String destCity;
	
	@Column(name = "TUBE_TYPE")
	private String tubeType;
	
	@Column(name = "TUBE_DEPLOYMENT")
	private String tubeDeployment;
	
	@Column(name = "TUBE_NETWORK_LEVEL")
	private String tubefiberNetLevel;
	
	@Column(name = "TUBE_OWNER")
	private String tubeOwner;
	
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
	
	@Column(name = "DRAWING_TYPE")
	private String drawingtype;
	
	@Column(name = "LAST_AUXILIARY_TO_DESTINATION_DISTANCE")
	private double lastAuxToDestDistance;
	
	@Column(name = "LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE")
	private double lastAuxToDestDrivDistance;
	
	@Column(name = "LENGTH")
	private double tubelength;
	
	@Column(name = "TUBE_NUMBER")
	private String tubeNumber;
	
	@Column(name = "TUBE_COLOR")
	private String tubeColor;
	
	public FiberTubes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FiberTubes(String tubeID, String fibercableID, String srcLong, String srcLat, String destLong,
			String destLat, String sourceWareId, String sourceId, String sourceName, String destinationWareId, String destinationId, String destinationName, String tubeName, String srcCity, String destCity,
			String tubeType, String tubeDeployment, String tubefiberNetLevel, String tubeOwner, Timestamp creationDate,
			Timestamp lastModifieddate, String createdBy, String lastmodifiedBy,double totaldriving, double totalGeoDist,
			String drawingtype, double lastAuxToDestDistance,double lastAuxToDestDrivDistance,double tubelength,String tubeNumber,String tubeColor) {
		super();
		this.tubeID = tubeID;
		this.fibercableID = fibercableID;
		this.srcLong = srcLong;
		this.srcLat = srcLat;
		this.destLong = destLong;
		this.destLat = destLat;
		this.sourceWareId = sourceWareId;
		this.sourceId = sourceId;
		this.sourceName = sourceName;
		this.destinationWareId = destinationWareId;
		this.destinationId = destinationId;
		this.destinationName = destinationName;
		this.tubeName = tubeName;
		this.srcCity = srcCity;
		this.destCity = destCity;
		this.tubeType = tubeType;
		this.tubeDeployment = tubeDeployment;
		this.tubefiberNetLevel = tubefiberNetLevel;
		this.tubeOwner = tubeOwner;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.createdBy = createdBy;
		this.lastmodifiedBy = lastmodifiedBy;
		this.totaldriving = totaldriving;
		this.totalGeoDist = totalGeoDist;
		this.drawingtype = drawingtype;
		this.lastAuxToDestDistance = lastAuxToDestDistance;
		this.lastAuxToDestDrivDistance=lastAuxToDestDrivDistance;
		this.tubelength=tubelength;
		this.tubeNumber=tubeNumber;
		this.tubeColor=tubeColor;
	}

	public String getTubeID() {
		return tubeID;
	}

	public void setTubeID(String tubeID) {
		this.tubeID = tubeID;
	}

	public String getFibercableID() {
		return fibercableID;
	}

	public void setFibercableID(String fibercableID) {
		this.fibercableID = fibercableID;
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

	public String getTubeName() {
		return tubeName;
	}

	public void setTubeName(String tubeName) {
		this.tubeName = tubeName;
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

	public String getTubeType() {
		return tubeType;
	}

	public void setTubeType(String tubeType) {
		this.tubeType = tubeType;
	}

	public String getTubeDeployment() {
		return tubeDeployment;
	}

	public void setTubeDeployment(String tubeDeployment) {
		this.tubeDeployment = tubeDeployment;
	}

	public String getTubefiberNetLevel() {
		return tubefiberNetLevel;
	}

	public void setTubefiberNetLevel(String tubefiberNetLevel) {
		this.tubefiberNetLevel = tubefiberNetLevel;
	}

	public String getTubeOwner() {
		return tubeOwner;
	}

	public void setTubeOwner(String tubeOwner) {
		this.tubeOwner = tubeOwner;
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

	public String getDrawingtype() {
		return drawingtype;
	}

	public void setDrawingtype(String drawingtype) {
		this.drawingtype = drawingtype;
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

	public double getTubelength() {
		return tubelength;
	}

	public void setTubelength(double tubelength) {
		this.tubelength = tubelength;
	}

	public String getTubeNumber() {
		return tubeNumber;
	}

	public void setTubeNumber(String tubeNumber) {
		this.tubeNumber = tubeNumber;
	}

	public String getTubeColor() {
		return tubeColor;
	}

	public void setTubeColor(String tubeColor) {
		this.tubeColor = tubeColor;
	}
	
}
