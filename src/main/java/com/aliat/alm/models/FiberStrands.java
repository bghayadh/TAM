package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIBER_STRANDS")
public class FiberStrands {

	@Id
	@Column(name = "STRAND_ID", nullable = false)
	private String strandID;
	
	@Column(name = "FIBER_CABLE_ID")
	private String fibercableID;

	@Column(name = "TUBE_ID", nullable = false)
	private String tubeID;
	
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
	
	@Column(name = "STRAND_NAME")
	private String strandName;
	
	@Column(name = "SOURCE_CITY")
	private String srcCity;
	
	@Column(name = "DESTINATION_CITY")
	private String destCity;
	
	@Column(name = "STRAND_TYPE")
	private String strandType;
	
	@Column(name = "STRAND_DEPLOYMENT")
	private String strandDeployment;
	
	@Column(name = "STRAND_NETWORK_LEVEL")
	private String strandfiberNetLevel;
	
	@Column(name = "STRAND_OWNER")
	private String strandOwner;
	
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
	
	@Column(name = "LENGTH")
	private double strandlength;
	
	@Column(name = "DRAWING_TYPE")
	private String drawingtype;
	
	@Column(name = "LAST_AUXILIARY_TO_DESTINATION_DISTANCE")
	private double lastAuxToDestDistance;
	
	@Column(name = "LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE")
	private double lastAuxToDestDrivDistance;
	
	@Column(name = "STRAND_NUMBER")
	private String strandNumber;
	
	@Column(name = "STRAND_COLOR")
	private String strandColor;
	
	public FiberStrands() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FiberStrands(String strandID, String fibercableID, String tubeID, String srcLong, String srcLat,
			String destLong, String destLat, String sourceWareId, String sourceId, String sourceName, String destinationWareId, String destinationId, String destinationName, String strandName, String srcCity,
			String destCity, String strandType, String strandDeployment, String strandfiberNetLevel, String strandOwner,
			Timestamp creationDate, Timestamp lastModifieddate, String createdBy, String lastmodifiedBy,
			double totaldriving, double totalGeoDist,double strandlength,String drawingtype,double lastAuxToDestDistance,double lastAuxToDestDrivDistance,String strandNumber,String strandColor) {
		super();
		this.strandID = strandID;
		this.fibercableID = fibercableID;
		this.tubeID = tubeID;
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
		this.strandName = strandName;
		this.srcCity = srcCity;
		this.destCity = destCity;
		this.strandType = strandType;
		this.strandDeployment = strandDeployment;
		this.strandfiberNetLevel = strandfiberNetLevel;
		this.strandOwner = strandOwner;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.createdBy = createdBy;
		this.lastmodifiedBy = lastmodifiedBy;
		this.totaldriving = totaldriving;
		this.totalGeoDist = totalGeoDist;
		this.strandlength=strandlength;
		this.drawingtype = drawingtype;
		this.lastAuxToDestDistance = lastAuxToDestDistance;
		this.lastAuxToDestDrivDistance=lastAuxToDestDrivDistance;
		this.strandNumber=strandNumber;
		this.strandColor=strandColor;

	}

	public String getStrandID() {
		return strandID;
	}

	public void setStrandID(String strandID) {
		this.strandID = strandID;
	}

	public String getFibercableID() {
		return fibercableID;
	}

	public void setFibercableID(String fibercableID) {
		this.fibercableID = fibercableID;
	}

	public String getTubeID() {
		return tubeID;
	}

	public void setTubeID(String tubeID) {
		this.tubeID = tubeID;
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

	public String getStrandName() {
		return strandName;
	}

	public void setStrandName(String strandName) {
		this.strandName = strandName;
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

	public String getStrandType() {
		return strandType;
	}

	public void setStrandType(String strandType) {
		this.strandType = strandType;
	}

	public String getStrandDeployment() {
		return strandDeployment;
	}

	public void setStrandDeployment(String strandDeployment) {
		this.strandDeployment = strandDeployment;
	}

	public String getStrandfiberNetLevel() {
		return strandfiberNetLevel;
	}

	public void setStrandfiberNetLevel(String strandfiberNetLevel) {
		this.strandfiberNetLevel = strandfiberNetLevel;
	}

	public String getStrandOwner() {
		return strandOwner;
	}

	public void setStrandOwner(String strandOwner) {
		this.strandOwner = strandOwner;
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

	public double getStrandlength() {
		return strandlength;
	}

	public void setStrandlength(double strandlength) {
		this.strandlength = strandlength;
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

	public String getStrandNumber() {
		return strandNumber;
	}

	public void setStrandNumber(String strandNumber) {
		this.strandNumber = strandNumber;
	}

	public String getStrandColor() {
		return strandColor;
	}

	public void setStrandColor(String strandColor) {
		this.strandColor = strandColor;
	}
	
	
}
