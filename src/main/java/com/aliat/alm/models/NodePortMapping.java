package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NODE_PORT_MAPPING")
public class NodePortMapping {
	
	@Id
	@Column(name = "PORT_MAPPING_ID", nullable = false)
	private String portMappingID;
	
	@Column(name = "NODE_ID", nullable = false)
	private String nodeID;
	
	
	@Column(name = "NODE_NAME")
	private String nodeName;
	
	@Column(name = "MAC_ADDRESS")
	private String MACAddr;
	
	@Column(name = "SERIAL_NUMBER")
	private String serialNB;
	
	@Column(name = "UNITPOSITION")
	private String unitPosition;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "PORT_NUMBER")
	private int portNB;
	
	@Column(name = "RECORD_TYPE")
	private String recordType;
	
	@Column(name = "LOCATION_TYPE")
	private String locationType;
	
	@Column(name = "LOCATION_ID")
	private String locationID;
	
	@Column(name = "LOCATION_NAME")
	private String locationName;
	
	@Column(name = "WARE_ID")
	private String wareID;
	
	@Column(name = "CABLE_TYPE")
	private String cableType;
	
	@Column(name = "FIBER_ID")
	private String fiberID;
	
	@Column(name = "FIBER_NAME")
	private String fiberName;
	
	@Column(name = "TX_STRAND_NB")
	private String txStrandNB;
	
	@Column(name = "RX_STRAND_NB")
	private String rxStrandNB;
	
	@Column(name = "TX_TUBE_NB")
	private String txTubeNB;
	
	@Column(name = "RX_TUBE_NB")
	private String rxTubeNB;
	
	@Column(name = "TX_STRAND_COLOR")
	private String txStrandColor;
	
	@Column(name = "RX_STRAND_COLOR")
	private String rxStrandColor;
	
	@Column(name = "TX_TUBE_COLOR")
	private String txTubeColor;
	
	@Column(name = "RX_TUBE_COLOR")
	private String rxTubeColor;
	
	@Column(name = "CABLE_LENGTH")
	private double cableLength;
	
	@Column(name = "CREATION_DATE")
	private Timestamp createdDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;
	
	@Column(name = "REF_STATUS")
	private String refStatus;
	
	

	public NodePortMapping() {
		super();
		// TODO Auto-generated constructor stub
	}


	public NodePortMapping(String portMappingID, String nodeID, String nodeName, String mACAddr, String serialNB,
			String unitPosition, String status, int portNB, String recordType, String locationType, String locationID,
			String locationName, String wareID, String cableType, String fiberID, String fiberName, String txStrandNB,
			String rxStrandNB, String txTubeNB, String rxTubeNB, String txStrandColor, String rxStrandColor,
			String txTubeColor, String rxTubeColor, double cableLength, Timestamp createdDate,
			Timestamp lastModifiedDate, String refStatus) {
		super();
		this.portMappingID = portMappingID;
		this.nodeID = nodeID;
		this.nodeName = nodeName;
		MACAddr = mACAddr;
		this.serialNB = serialNB;
		this.unitPosition = unitPosition;
		this.status = status;
		this.portNB = portNB;
		this.recordType = recordType;
		this.locationType = locationType;
		this.locationID = locationID;
		this.locationName = locationName;
		this.wareID = wareID;
		this.cableType = cableType;
		this.fiberID = fiberID;
		this.fiberName = fiberName;
		this.txStrandNB = txStrandNB;
		this.rxStrandNB = rxStrandNB;
		this.txTubeNB = txTubeNB;
		this.rxTubeNB = rxTubeNB;
		this.txStrandColor = txStrandColor;
		this.rxStrandColor = rxStrandColor;
		this.txTubeColor = txTubeColor;
		this.rxTubeColor = rxTubeColor;
		this.cableLength = cableLength;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.refStatus = refStatus;
	}



	public String getPortMappingID() {
		return portMappingID;
	}


	public void setPortMappingID(String portMappingID) {
		this.portMappingID = portMappingID;
	}



	public String getNodeID() {
		return nodeID;
	}



	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}



	public String getNodeName() {
		return nodeName;
	}



	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}



	public String getMACAddr() {
		return MACAddr;
	}



	public void setMACAddr(String mACAddr) {
		MACAddr = mACAddr;
	}



	public String getSerialNB() {
		return serialNB;
	}



	public void setSerialNB(String serialNB) {
		this.serialNB = serialNB;
	}



	public String getUnitPosition() {
		return unitPosition;
	}



	public void setUnitPosition(String unitPosition) {
		this.unitPosition = unitPosition;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getRecordType() {
		return recordType;
	}



	public void setRecordType(String recordType) {
		this.recordType = recordType;
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



	public String getWareID() {
		return wareID;
	}



	public void setWareID(String wareID) {
		this.wareID = wareID;
	}



	public String getFiberID() {
		return fiberID;
	}



	public void setFiberID(String fiberID) {
		this.fiberID = fiberID;
	}



	public String getFiberName() {
		return fiberName;
	}



	public void setFiberName(String fiberName) {
		this.fiberName = fiberName;
	}



	public String getTxStrandNB() {
		return txStrandNB;
	}



	public void setTxStrandNB(String txStrandNB) {
		this.txStrandNB = txStrandNB;
	}



	public String getRxStrandNB() {
		return rxStrandNB;
	}



	public void setRxStrandNB(String rxStrandNB) {
		this.rxStrandNB = rxStrandNB;
	}



	public String getTxTubeNB() {
		return txTubeNB;
	}



	public void setTxTubeNB(String txTubeNB) {
		this.txTubeNB = txTubeNB;
	}



	public String getRxTubeNB() {
		return rxTubeNB;
	}



	public void setRxTubeNB(String rxTubeNB) {
		this.rxTubeNB = rxTubeNB;
	}



	public String getTxStrandColor() {
		return txStrandColor;
	}



	public void setTxStrandColor(String txStrandColor) {
		this.txStrandColor = txStrandColor;
	}



	public String getRxStrandColor() {
		return rxStrandColor;
	}



	public void setRxStrandColor(String rxStrandColor) {
		this.rxStrandColor = rxStrandColor;
	}



	public String getTxTubeColor() {
		return txTubeColor;
	}



	public void setTxTubeColor(String txTubeColor) {
		this.txTubeColor = txTubeColor;
	}



	public String getRxTubeColor() {
		return rxTubeColor;
	}



	public void setRxTubeColor(String rxTubeColor) {
		this.rxTubeColor = rxTubeColor;
	}


	public Timestamp getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}


	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}


	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


	public int getPortNB() {
		return portNB;
	}







	public void setPortNB(int portNB) {
		this.portNB = portNB;
	}







	public double getCableLength() {
		return cableLength;
	}







	public void setCableLength(double cableLength) {
		this.cableLength = cableLength;
	}




	public String getCableType() {
		return cableType;
	}




	public void setCableType(String cableType) {
		this.cableType = cableType;
	}


	public String getRefStatus() {
		return refStatus;
	}


	public void setRefStatus(String refStatus) {
		this.refStatus = refStatus;
	}
	
	
	
	
	
	
	
}