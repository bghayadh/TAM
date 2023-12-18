package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIBER_CABLES")
public class FiberCable {
	
	@Id
	@Column(name = "FIBER_CABLE_ID", nullable = false)
	private String fibercableID;

	@Column(name = "SOURCE_WARE_ID")
	private String sourceWareID;
	
	@Column(name = "SOURCE_ID")
	private String sourceID;
	
	@Column(name = "SOURCE_NAME")
	private String sourceName;
	
	@Column(name = "DESTINATION_WARE_ID")
	private String destinationWareID;
	
	@Column(name = "DESTINATION_ID")
	private String destinationID;
	
	@Column(name = "DESTINATION_NAME")
	private String destinationName;
	
	@Column(name = "ITEM_CODE")
	private String itemcode;
	
	@Column(name = "NUMBER_OF_STRANDS")
	private int nbofStrands;
	
	@Column(name = "NUMBER_OF_TUBES")
	private int nbofTubes;
	
	@Column(name = "LENGTH")
	private double fiberlength;
	
	@Column(name = "CONDUIT_ID")
	private String conduitID;
	
	@Column(name = "CONDUIT_NAME")
	private String conduitName;
	
	@Column(name = "SOURCE_LNG")
	private String srcLNG;
	
	@Column(name = "SOURCE_LAT")
	private String srcLAT;
	
	@Column(name = "DESTINATION_LNG")
	private String destLNG;
	
	@Column(name = "DESTINATION_LAT")
	private String destLAT;
	
	@Column(name = "CABLE_MODE")
	private String cableMode;
	
	@Column(name = "FIBER_CABLE_NAME")
	private String fibercableName;
	
	@Column(name = "SOURCE_CITY")
	private String srcCity;
	
	@Column(name = "DESTINATION_CITY")
	private String destCity;
	
	@Column(name = "PROJECT_ID")
	private String projectID;
	
	@Column(name = "FIBER_TYPE")
	private String fiberType;
	
	@Column(name = "FIBER_DEPLOYMENT")
	private String fiberDeployment;
	
	@Column(name = "FIBER_NETWORK_LEVEL")
	private String fiberNetLevel;
	
	@Column(name = "FIBER_OWNER")
	private String fiberOwner;
	
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
	
	@Column(name = "DRAWING_TYPE")
	private String drawingtype;
	
	@Column(name = "TOTAL_GEO_DISTANCE")
	private double totalGeoDist;
	
	@Column(name = "LAST_AUXILIARY_TO_DESTINATION_DISTANCE")
	private double lastAuxToDestDistance;
	
	@Column(name = "LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE")
	private double lastAuxToDestDrivDistance;
	
	//related cable 
	@Column(name = "RELATED_STRAND_NUMBER")
	private String relatedstrandnumber;
	
	@Column(name = "RELATED_STRAND_COLOR")
	private String relatedstrandcolor;
	
	@Column(name = "RELATED_STRAND_ID")
	private String relatedstrandID;
	
	@Column(name = "RELATED_STRAND_NAME")
	private String relatedstrandName;
	
	@Column(name = "RELATED_TUBE_NUMBER")
	private String relatedtubenumber;
	
	@Column(name = "RELATED_TUBE_COLOR")
	private String relatedtubecolor;
	
	@Column(name = "RELATED_TUBE_ID")
	private String relatedtubeID;
	
	@Column(name = "RELATED_TUBE_NAME")
	private String relatedtubeName;
	
	@Column(name = "RELATED_CABLE_ID")
	private String relatedcableID;
	
	@Column(name = "RELATED_CABLE_NAME")
	private String relatedcableName;
	
	@Column(name = "OTHERSIDE_LASTMILE_ID")
	private String othersideLastmileID;
	
	@Column(name = "OTHERSIDE_LASTMILE_NAME")
	private String othersideLastmileName;
	
	@Column(name = "OTHERSIDE_LOCATION_ID")
	private String othersideLocationID;
	
	@Column(name = "OTHERSIDE_LOCATION_NAME")
	private String othersideLocationName;
	
	@Column(name = "OTHERSIDE_LOCATION_CITY")
	private String othersideLocationCity;
	
	@Column(name = "OTHERSIDE_LOCATION_TYPE")
	private String othersideLocationType;
	
	@Column(name = "FIBER_INSTALLER")
	private String fiberInstaller;
	
	@Column(name = "FIBER_ENGINEER_NAME")
	private String fiberEngineerName;
	
	@Column(name = "FIBER_CABLE_SIZE")
	private String fiberCableSize;
	
	public FiberCable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FiberCable(String fibercableID, String sourceWareID, String sourceID, String sourceName,
			String destinationWareID, String destinationID, String destinationName, String itemcode, int nbofStrands,
			int nbofTubes, double fiberlength, String conduitID, String conduitName, String srcLNG, String srcLAT,
			String destLNG, String destLAT, String cableMode, String fibercableName, String srcCity, String destCity,
			String projectID, String fiberType, String fiberDeployment, String fiberNetLevel, String fiberOwner,
			Timestamp creationDate, Timestamp lastModifieddate, String createdBy, String lastmodifiedBy,
			double totaldriving, String drawingtype, double totalGeoDist, double lastAuxToDestDistance,
			double lastAuxToDestDrivDistance, String relatedstrandnumber, String relatedstrandcolor,
			String relatedstrandID, String relatedstrandName, String relatedtubenumber, String relatedtubecolor,
			String relatedtubeID, String relatedtubeName, String relatedcableID, String relatedcableName,
			String othersideLastmileID, String othersideLastmileName, String othersideLocationID,
			String othersideLocationName, String othersideLocationCity, String othersideLocationType,
			String fiberInstaller, String fiberEngineerName, String fiberCableSize) {
		super();
		this.fibercableID = fibercableID;
		this.sourceWareID = sourceWareID;
		this.sourceID = sourceID;
		this.sourceName = sourceName;
		this.destinationWareID = destinationWareID;
		this.destinationID = destinationID;
		this.destinationName = destinationName;
		this.itemcode = itemcode;
		this.nbofStrands = nbofStrands;
		this.nbofTubes = nbofTubes;
		this.fiberlength = fiberlength;
		this.conduitID = conduitID;
		this.conduitName = conduitName;
		this.srcLNG = srcLNG;
		this.srcLAT = srcLAT;
		this.destLNG = destLNG;
		this.destLAT = destLAT;
		this.cableMode = cableMode;
		this.fibercableName = fibercableName;
		this.srcCity = srcCity;
		this.destCity = destCity;
		this.projectID = projectID;
		this.fiberType = fiberType;
		this.fiberDeployment = fiberDeployment;
		this.fiberNetLevel = fiberNetLevel;
		this.fiberOwner = fiberOwner;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.createdBy = createdBy;
		this.lastmodifiedBy = lastmodifiedBy;
		this.totaldriving = totaldriving;
		this.drawingtype = drawingtype;
		this.totalGeoDist = totalGeoDist;
		this.lastAuxToDestDistance = lastAuxToDestDistance;
		this.lastAuxToDestDrivDistance = lastAuxToDestDrivDistance;
		this.relatedstrandnumber = relatedstrandnumber;
		this.relatedstrandcolor = relatedstrandcolor;
		this.relatedstrandID = relatedstrandID;
		this.relatedstrandName = relatedstrandName;
		this.relatedtubenumber = relatedtubenumber;
		this.relatedtubecolor = relatedtubecolor;
		this.relatedtubeID = relatedtubeID;
		this.relatedtubeName = relatedtubeName;
		this.relatedcableID = relatedcableID;
		this.relatedcableName = relatedcableName;
		this.othersideLastmileID = othersideLastmileID;
		this.othersideLastmileName = othersideLastmileName;
		this.othersideLocationID = othersideLocationID;
		this.othersideLocationName = othersideLocationName;
		this.othersideLocationCity = othersideLocationCity;
		this.othersideLocationType = othersideLocationType;
		this.fiberInstaller = fiberInstaller;
		this.fiberEngineerName = fiberEngineerName;
		this.fiberCableSize = fiberCableSize;
	}

	public String getFibercableID() {
		return fibercableID;
	}

	public void setFibercableID(String fibercableID) {
		this.fibercableID = fibercableID;
	}

	public String getSourceWareID() {
		return sourceWareID;
	}

	public void setSourceWareID(String sourceWareID) {
		this.sourceWareID = sourceWareID;
	}

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getDestinationWareID() {
		return destinationWareID;
	}

	public void setDestinationWareID(String destinationWareID) {
		this.destinationWareID = destinationWareID;
	}

	public String getDestinationID() {
		return destinationID;
	}

	public void setDestinationID(String destinationID) {
		this.destinationID = destinationID;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public int getNbofStrands() {
		return nbofStrands;
	}

	public void setNbofStrands(int nbofStrands) {
		this.nbofStrands = nbofStrands;
	}

	public int getNbofTubes() {
		return nbofTubes;
	}

	public void setNbofTubes(int nbofTubes) {
		this.nbofTubes = nbofTubes;
	}

	public double getFiberlength() {
		return fiberlength;
	}

	public void setFiberlength(double fiberlength) {
		this.fiberlength = fiberlength;
	}

	public String getConduitID() {
		return conduitID;
	}

	public void setConduitID(String conduitID) {
		this.conduitID = conduitID;
	}

	public String getConduitName() {
		return conduitName;
	}

	public void setConduitName(String conduitName) {
		this.conduitName = conduitName;
	}

	public String getSrcLNG() {
		return srcLNG;
	}

	public void setSrcLNG(String srcLNG) {
		this.srcLNG = srcLNG;
	}

	public String getSrcLAT() {
		return srcLAT;
	}

	public void setSrcLAT(String srcLAT) {
		this.srcLAT = srcLAT;
	}

	public String getDestLNG() {
		return destLNG;
	}

	public void setDestLNG(String destLNG) {
		this.destLNG = destLNG;
	}

	public String getDestLAT() {
		return destLAT;
	}

	public void setDestLAT(String destLAT) {
		this.destLAT = destLAT;
	}

	public String getCableMode() {
		return cableMode;
	}

	public void setCableMode(String cableMode) {
		this.cableMode = cableMode;
	}

	public String getFibercableName() {
		return fibercableName;
	}

	public void setFibercableName(String fibercableName) {
		this.fibercableName = fibercableName;
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

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getFiberType() {
		return fiberType;
	}

	public void setFiberType(String fiberType) {
		this.fiberType = fiberType;
	}

	public String getFiberDeployment() {
		return fiberDeployment;
	}

	public void setFiberDeployment(String fiberDeployment) {
		this.fiberDeployment = fiberDeployment;
	}

	public String getFiberNetLevel() {
		return fiberNetLevel;
	}

	public void setFiberNetLevel(String fiberNetLevel) {
		this.fiberNetLevel = fiberNetLevel;
	}

	public String getFiberOwner() {
		return fiberOwner;
	}

	public void setFiberOwner(String fiberOwner) {
		this.fiberOwner = fiberOwner;
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

	public String getDrawingtype() {
		return drawingtype;
	}

	public void setDrawingtype(String drawingtype) {
		this.drawingtype = drawingtype;
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

	public String getRelatedstrandnumber() {
		return relatedstrandnumber;
	}

	public void setRelatedstrandnumber(String relatedstrandnumber) {
		this.relatedstrandnumber = relatedstrandnumber;
	}

	public String getRelatedstrandcolor() {
		return relatedstrandcolor;
	}

	public void setRelatedstrandcolor(String relatedstrandcolor) {
		this.relatedstrandcolor = relatedstrandcolor;
	}

	public String getRelatedstrandID() {
		return relatedstrandID;
	}

	public void setRelatedstrandID(String relatedstrandID) {
		this.relatedstrandID = relatedstrandID;
	}

	public String getRelatedstrandName() {
		return relatedstrandName;
	}

	public void setRelatedstrandName(String relatedstrandName) {
		this.relatedstrandName = relatedstrandName;
	}

	public String getRelatedtubenumber() {
		return relatedtubenumber;
	}

	public void setRelatedtubenumber(String relatedtubenumber) {
		this.relatedtubenumber = relatedtubenumber;
	}

	public String getRelatedtubecolor() {
		return relatedtubecolor;
	}

	public void setRelatedtubecolor(String relatedtubecolor) {
		this.relatedtubecolor = relatedtubecolor;
	}

	public String getRelatedtubeID() {
		return relatedtubeID;
	}

	public void setRelatedtubeID(String relatedtubeID) {
		this.relatedtubeID = relatedtubeID;
	}

	public String getRelatedtubeName() {
		return relatedtubeName;
	}

	public void setRelatedtubeName(String relatedtubeName) {
		this.relatedtubeName = relatedtubeName;
	}

	public String getRelatedcableID() {
		return relatedcableID;
	}

	public void setRelatedcableID(String relatedcableID) {
		this.relatedcableID = relatedcableID;
	}

	public String getRelatedcableName() {
		return relatedcableName;
	}

	public void setRelatedcableName(String relatedcableName) {
		this.relatedcableName = relatedcableName;
	}

	public String getOthersideLastmileID() {
		return othersideLastmileID;
	}

	public void setOthersideLastmileID(String othersideLastmileID) {
		this.othersideLastmileID = othersideLastmileID;
	}

	public String getOthersideLastmileName() {
		return othersideLastmileName;
	}

	public void setOthersideLastmileName(String othersideLastmileName) {
		this.othersideLastmileName = othersideLastmileName;
	}

	public String getOthersideLocationID() {
		return othersideLocationID;
	}

	public void setOthersideLocationID(String othersideLocationID) {
		this.othersideLocationID = othersideLocationID;
	}

	public String getOthersideLocationName() {
		return othersideLocationName;
	}

	public void setOthersideLocationName(String othersideLocationName) {
		this.othersideLocationName = othersideLocationName;
	}

	public String getOthersideLocationCity() {
		return othersideLocationCity;
	}

	public void setOthersideLocationCity(String othersideLocationCity) {
		this.othersideLocationCity = othersideLocationCity;
	}

	public String getOthersideLocationType() {
		return othersideLocationType;
	}

	public void setOthersideLocationType(String othersideLocationType) {
		this.othersideLocationType = othersideLocationType;
	}

	public String getFiberInstaller() {
		return fiberInstaller;
	}

	public void setFiberInstaller(String fiberInstaller) {
		this.fiberInstaller = fiberInstaller;
	}

	public String getFiberEngineerName() {
		return fiberEngineerName;
	}

	public void setFiberEngineerName(String fiberEngineerName) {
		this.fiberEngineerName = fiberEngineerName;
	}

	public String getFiberCableSize() {
		return fiberCableSize;
	}

	public void setFiberCableSize(String fiberCableSize) {
		this.fiberCableSize = fiberCableSize;
	}

/*	public FiberCable(String fibercableID, String sourceWareID,String sourceID,String sourceName,String destinationWareID, String destinationID,String destinationName, String itemcode, int nbofStrands,
			int nbofTubes, double fiberlength, String conduitID, String conduitName, String srcLNG, String srcLAT,
			String destLNG, String destLAT, String cableMode, String fibercableName, String srcCity, String destCity,
			String projectID, String fiberType, String fiberDeployment, String fiberNetLevel, String fiberOwner,
			Timestamp creationDate, Timestamp lastModifieddate, String createdBy, String lastmodifiedBy,
			double totaldriving,String drawingtype, double totalGeoDist, double lastAuxToDestDistance,double lastAuxToDestDrivDistance) {
		super();
		this.fibercableID = fibercableID;
		this.sourceWareID = sourceWareID;
		this.sourceID=sourceID;
		this.sourceName=sourceName;
		this.destinationWareID = destinationWareID;
		this.destinationID=destinationID;
		this.destinationName=destinationName;
		this.itemcode = itemcode;
		this.nbofStrands = nbofStrands;
		this.nbofTubes = nbofTubes;
		this.fiberlength = fiberlength;
		this.conduitID = conduitID;
		this.conduitName = conduitName;
		this.srcLNG = srcLNG;
		this.srcLAT = srcLAT;
		this.destLNG = destLNG;
		this.destLAT = destLAT;
		this.cableMode = cableMode;
		this.fibercableName = fibercableName;
		this.srcCity = srcCity;
		this.destCity = destCity;
		this.projectID = projectID;
		this.fiberType = fiberType;
		this.fiberDeployment = fiberDeployment;
		this.fiberNetLevel = fiberNetLevel;
		this.fiberOwner = fiberOwner;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.createdBy = createdBy;
		this.lastmodifiedBy = lastmodifiedBy;
		this.totaldriving = totaldriving;
		this.drawingtype = drawingtype;
		this.totalGeoDist = totalGeoDist;
		this.lastAuxToDestDistance = lastAuxToDestDistance;
		this.lastAuxToDestDrivDistance=lastAuxToDestDrivDistance;
	}  */
	
	
	
	
	/*
	@Override
	public String toString() {
		return "FiberCable [fibercableID=" + fibercableID + ", sourceWareID=" + sourceWareID + ", sourceID=" + sourceID + " , sourceName=" + sourceName 
				+ ", destinationWareID =" + destinationWareID +" , destinationID=" + destinationID + ", destinationName=" + destinationName + " , itemcode=" + itemcode + ", nbofStrands=" + nbofStrands + ", nbofTubes=" + nbofTubes
				+ ", fiberlength=" + fiberlength + ", conduitID=" + conduitID + ", conduitName=" + conduitName
				+ ", srcLNG=" + srcLNG + ", srcLAT=" + srcLAT + ", destLNG=" + destLNG + ", destLAT=" + destLAT
				+ ", cableMode=" + cableMode + ", fibercableName=" + fibercableName + ", srcCity=" + srcCity
				+ ", destCity=" + destCity + ", projectID=" + projectID + ", fiberType=" + fiberType
				+ ", fiberDeployment=" + fiberDeployment + ", fiberNetLevel=" + fiberNetLevel + ", fiberOwner="
				+ fiberOwner + ", creationDate=" + creationDate + ", lastModifieddate=" + lastModifieddate
				+ ", createdBy=" + createdBy + ", lastmodifiedBy=" + lastmodifiedBy + ", totaldriving=" + totaldriving
				+ ", drawingtype=" + drawingtype + ", totalGeoDist=" + totalGeoDist + ", lastAuxToDestDistance =" +lastAuxToDestDistance+",lastAuxToDestDrivDistance="+lastAuxToDestDrivDistance+"]";
	}

	*/

	



	
	
}
