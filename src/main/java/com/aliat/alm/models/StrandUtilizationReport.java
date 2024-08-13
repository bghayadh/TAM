package com.aliat.alm.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JUNCTION_MAPPING")
public class StrandUtilizationReport {
	

	@Column(name = "STRAND_NB_SIDE_A")
	private String strandNo;
	
	
	@Column(name = "TUBE_NB_SIDE_A")
	private String tubeNo;
	
	@Column(name = "LOCATION_TYPE_SIDE_A")
	private String locationType;
	
	@Id
	@Column(name = "LOCATION_ID_SIDE_A" , nullable = false)
	private String locationID;
	
	@Column(name = "LOCATION_NAME_SIDE_A")
	private String locationName;
	
	private String longitude;
	private String latitude;
	
	private String elementType;
	private String elementID;	
	private String frontBackPort;
	private String portIndex;
	private String portRow;
	private String portColumn;
	private String status;
	private String equipmentType;
	private String equipmentID;
	private String equipmentName;
	
	
	private String showLocation;
	private String showElement;


	
	public StrandUtilizationReport() {	
	}


	public StrandUtilizationReport(String strandNo, String tubeNo,String locationType,String locationID, String locationName,String longitude, String latitude,String elementType,String elementID,String frontBackPort,String portIndex,String portRow,String portColumn,String status,String equipmentType,String equipmentID,String equipmentName,
			
			String showLocation,String showElement) {
		super();
		this.strandNo=strandNo;
		this.tubeNo = tubeNo;
		this.locationType = locationType;
		this.locationID=locationID;
		this.locationName = locationName;
		this.longitude=longitude;
		this.latitude =latitude;
		this.elementType=elementType;
		this.elementID=elementID;
		this.frontBackPort=frontBackPort;
		this.portIndex=portIndex;
		this.portRow=portRow;
		this.portColumn=portColumn;
		this.status = status;
		this.equipmentType=equipmentType;
		this.equipmentID = equipmentID;
		this.equipmentName = equipmentName;
		this.showLocation=showLocation;
		this.showElement=showElement;
	}


	public String getStrandNo() {
		return strandNo;
	}


	public void setStrandNo(String strandNo) {
		this.strandNo = strandNo;
	}


	public String getTubeNo() {
		return tubeNo;
	}


	public void setTubeNo(String tubeNo) {
		this.tubeNo = tubeNo;
	}


	public String getElementType() {
		return elementType;
	}


	public void setElementType(String elementType) {
		this.elementType = elementType;
	}


	public String getElementID() {
		return elementID;
	}


	public void setElementID(String elementID) {
		this.elementID = elementID;
	}


	public String getFrontBackPort() {
		return frontBackPort;
	}


	public void setFrontBackPort(String frontBackPort) {
		this.frontBackPort = frontBackPort;
	}


	public String getPortIndex() {
		return portIndex;
	}


	public void setPortIndex(String portIndex) {
		this.portIndex = portIndex;
	}


	public String getPortRow() {
		return portRow;
	}


	public void setPortRow(String portRow) {
		this.portRow = portRow;
	}


	public String getPortColumn() {
		return portColumn;
	}


	public void setPortColumn(String portColumn) {
		this.portColumn = portColumn;
	}


	public String getEquipmentType() {
		return equipmentType;
	}


	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getEquipmentID() {
		return equipmentID;
	}


	public void setEquipmentID(String equipmentID) {
		this.equipmentID = equipmentID;
	}


	public String getEquipmentName() {
		return equipmentName;
	}


	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}


	public String getLocationType() {
		return locationType;
	}


	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}


	public String getLocationID() {
		return locationID;
	}


	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}


	public String getLocationName() {
		return locationName;
	}
	

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getShowLocation() {
		return showLocation;
	}


	public void setShowLocation(String showLocation) {
		this.showLocation = showLocation;
	}


	public String getShowElement() {
		return showElement;
	}


	public void setShowElement(String showElement) {
		this.showElement = showElement;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}



}
