package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="JUNCTION_MAPPING")

public class JunctionMapping {
	
	@Id
	@Column(name = "JCT_MAPPING_ID", nullable = false)
	private String ID;
	
	@Column(name = "JCT_ID")
	private String jctID;

	@Column(name = "SEQUENCE_NUMBER")
	private String seqNum;

	@Column(name = "LOCATION_TYPE_SIDE_A")
	private String locationTypeSideA; 
				
	@Column(name = "LOCATION_ID_SIDE_A")
	private String locationIdSideA; 
	
	@Column(name = "LOCATION_NAME_SIDE_A")
	private String locationNameSideA; 

	@Column(name = "WAREHOUSE_ID_SIDE_A")
	private String warehouseIdSideA; 
	
	@Column(name = "PHYSICAL_LAYER_ID")
	private String physicalLyerID; 

	@Column(name = "STRAND_NB_SIDE_A")
	private String strandNbSideA; 

	@Column(name = "TUBE_NB_SIDE_A")
	private String tubeNbSideA; 
	
	@Column(name = "NETWORK_LEVEL_SIDE_A")
	private String networkLevelSideA;
	
	@Column(name = "STRAND_NB_SIDE_B")
	private String strandNbSideB;

	@Column(name = "TUBE_NB_SIDE_B")
	private String tubeNbSideB;

	@Column(name = "NETWORK_LEVEL_SIDE_B")
	private String networkLevelSideB;
	
	@Column(name = "LOCATION_TYPE_SIDE_B")
	private String locationTypeSideB;
	
	@Column(name = "LOCATION_ID_SIDE_B")
	private String locationIdSideB;
	
	@Column(name = "LOCATION_NAME_SIDE_B")
	private String locationNameSideB;

	@Column(name = "WAREHOUSE_ID_SIDE_B")
	private String warehouseIdSideB;

	@Column(name = "STRAND_ID_SIDE_A")
	private String strandIdSideA;
	
	@Column(name = "STRAND_NAME_SIDE_A")
	private String strandNameSideA;
	
	@Column(name = "TUBE_ID_SIDE_A")
	private String tubeIdSideA;
	
	@Column(name = "TUBE_NAME_SIDE_A")
	private String tubeNameSideA;
	
	@Column(name = "FIBER_ID_SIDE_A")
	private String fiberIdSideA;
	
	@Column(name = "FIBER_NAME_SIDE_A")
	private String fiberNameSideA;

	@Column(name = "STRAND_ID_SIDE_B")
	private String strandIdSideB;

	@Column(name = "STRAND_NAME_SIDE_B")
	private String strandNameSideB;
	
	@Column(name = "TUBE_ID_SIDE_B")
	private String tubeIdSideB;
	
	@Column(name = "TUBE_NAME_SIDE_B")
	private String tubeNameSideB;
	
	@Column(name = "FIBER_ID_SIDE_B")
	private String fiberIdSideB;

	@Column(name = "FIBER_NAME_SIDE_B")
	private String fiberNameSideB;

	public JunctionMapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JunctionMapping(String iD, String jctID, String seqNum, String locationTypeSideA, String locationIdSideA,
			String locationNameSideA, String warehouseIdSideA, String physicalLyerID, String strandNbSideA,
			String tubeNbSideA, String networkLevelSideA, String strandNbSideB, String tubeNbSideB,
			String networkLevelSideB, String locationTypeSideB, String locationIdSideB, String locationNameSideB,
			String warehouseIdSideB, String strandIdSideA, String strandNameSideA, String tubeIdSideA,
			String tubeNameSideA, String fiberIdSideA, String fiberNameSideA, String strandIdSideB,
			String strandNameSideB, String tubeIdSideB, String tubeNameSideB, String fiberIdSideB,
			String fiberNameSideB) {
		super();
		ID = iD;
		this.jctID = jctID;
		this.seqNum = seqNum;
		this.locationTypeSideA = locationTypeSideA;
		this.locationIdSideA = locationIdSideA;
		this.locationNameSideA = locationNameSideA;
		this.warehouseIdSideA = warehouseIdSideA;
		this.physicalLyerID = physicalLyerID;
		this.strandNbSideA = strandNbSideA;
		this.tubeNbSideA = tubeNbSideA;
		this.networkLevelSideA = networkLevelSideA;
		this.strandNbSideB = strandNbSideB;
		this.tubeNbSideB = tubeNbSideB;
		this.networkLevelSideB = networkLevelSideB;
		this.locationTypeSideB = locationTypeSideB;
		this.locationIdSideB = locationIdSideB;
		this.locationNameSideB = locationNameSideB;
		this.warehouseIdSideB = warehouseIdSideB;
		this.strandIdSideA = strandIdSideA;
		this.strandNameSideA = strandNameSideA;
		this.tubeIdSideA = tubeIdSideA;
		this.tubeNameSideA = tubeNameSideA;
		this.fiberIdSideA = fiberIdSideA;
		this.fiberNameSideA = fiberNameSideA;
		this.strandIdSideB = strandIdSideB;
		this.strandNameSideB = strandNameSideB;
		this.tubeIdSideB = tubeIdSideB;
		this.tubeNameSideB = tubeNameSideB;
		this.fiberIdSideB = fiberIdSideB;
		this.fiberNameSideB = fiberNameSideB;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getJctID() {
		return jctID;
	}

	public void setJctID(String jctID) {
		this.jctID = jctID;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	public String getLocationTypeSideA() {
		return locationTypeSideA;
	}

	public void setLocationTypeSideA(String locationTypeSideA) {
		this.locationTypeSideA = locationTypeSideA;
	}

	public String getLocationIdSideA() {
		return locationIdSideA;
	}

	public void setLocationIdSideA(String locationIdSideA) {
		this.locationIdSideA = locationIdSideA;
	}

	public String getLocationNameSideA() {
		return locationNameSideA;
	}

	public void setLocationNameSideA(String locationNameSideA) {
		this.locationNameSideA = locationNameSideA;
	}

	public String getWarehouseIdSideA() {
		return warehouseIdSideA;
	}

	public void setWarehouseIdSideA(String warehouseIdSideA) {
		this.warehouseIdSideA = warehouseIdSideA;
	}

	public String getPhysicalLyerID() {
		return physicalLyerID;
	}

	public void setPhysicalLyerID(String physicalLyerID) {
		this.physicalLyerID = physicalLyerID;
	}

	public String getStrandNbSideA() {
		return strandNbSideA;
	}

	public void setStrandNbSideA(String strandNbSideA) {
		this.strandNbSideA = strandNbSideA;
	}

	public String getTubeNbSideA() {
		return tubeNbSideA;
	}

	public void setTubeNbSideA(String tubeNbSideA) {
		this.tubeNbSideA = tubeNbSideA;
	}

	public String getNetworkLevelSideA() {
		return networkLevelSideA;
	}

	public void setNetworkLevelSideA(String networkLevelSideA) {
		this.networkLevelSideA = networkLevelSideA;
	}

	public String getStrandNbSideB() {
		return strandNbSideB;
	}

	public void setStrandNbSideB(String strandNbSideB) {
		this.strandNbSideB = strandNbSideB;
	}

	public String getTubeNbSideB() {
		return tubeNbSideB;
	}

	public void setTubeNbSideB(String tubeNbSideB) {
		this.tubeNbSideB = tubeNbSideB;
	}

	public String getNetworkLevelSideB() {
		return networkLevelSideB;
	}

	public void setNetworkLevelSideB(String networkLevelSideB) {
		this.networkLevelSideB = networkLevelSideB;
	}

	public String getLocationTypeSideB() {
		return locationTypeSideB;
	}

	public void setLocationTypeSideB(String locationTypeSideB) {
		this.locationTypeSideB = locationTypeSideB;
	}

	public String getLocationIdSideB() {
		return locationIdSideB;
	}

	public void setLocationIdSideB(String locationIdSideB) {
		this.locationIdSideB = locationIdSideB;
	}

	public String getLocationNameSideB() {
		return locationNameSideB;
	}

	public void setLocationNameSideB(String locationNameSideB) {
		this.locationNameSideB = locationNameSideB;
	}

	public String getWarehouseIdSideB() {
		return warehouseIdSideB;
	}

	public void setWarehouseIdSideB(String warehouseIdSideB) {
		this.warehouseIdSideB = warehouseIdSideB;
	}

	public String getStrandIdSideA() {
		return strandIdSideA;
	}

	public void setStrandIdSideA(String strandIdSideA) {
		this.strandIdSideA = strandIdSideA;
	}

	public String getStrandNameSideA() {
		return strandNameSideA;
	}

	public void setStrandNameSideA(String strandNameSideA) {
		this.strandNameSideA = strandNameSideA;
	}

	public String getTubeIdSideA() {
		return tubeIdSideA;
	}

	public void setTubeIdSideA(String tubeIdSideA) {
		this.tubeIdSideA = tubeIdSideA;
	}

	public String getTubeNameSideA() {
		return tubeNameSideA;
	}

	public void setTubeNameSideA(String tubeNameSideA) {
		this.tubeNameSideA = tubeNameSideA;
	}

	public String getFiberIdSideA() {
		return fiberIdSideA;
	}

	public void setFiberIdSideA(String fiberIdSideA) {
		this.fiberIdSideA = fiberIdSideA;
	}

	public String getFiberNameSideA() {
		return fiberNameSideA;
	}

	public void setFiberNameSideA(String fiberNameSideA) {
		this.fiberNameSideA = fiberNameSideA;
	}

	public String getStrandIdSideB() {
		return strandIdSideB;
	}

	public void setStrandIdSideB(String strandIdSideB) {
		this.strandIdSideB = strandIdSideB;
	}

	public String getStrandNameSideB() {
		return strandNameSideB;
	}

	public void setStrandNameSideB(String strandNameSideB) {
		this.strandNameSideB = strandNameSideB;
	}

	public String getTubeIdSideB() {
		return tubeIdSideB;
	}

	public void setTubeIdSideB(String tubeIdSideB) {
		this.tubeIdSideB = tubeIdSideB;
	}

	public String getTubeNameSideB() {
		return tubeNameSideB;
	}

	public void setTubeNameSideB(String tubeNameSideB) {
		this.tubeNameSideB = tubeNameSideB;
	}

	public String getFiberIdSideB() {
		return fiberIdSideB;
	}

	public void setFiberIdSideB(String fiberIdSideB) {
		this.fiberIdSideB = fiberIdSideB;
	}

	public String getFiberNameSideB() {
		return fiberNameSideB;
	}

	public void setFiberNameSideB(String fiberNameSideB) {
		this.fiberNameSideB = fiberNameSideB;
	}
}