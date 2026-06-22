package com.aliat.alm.dto;

import java.sql.Timestamp;

public class FiberCableDetailsDTO {

	private String fiberCableName;
	private String numberOfTubes;
	private String numberOfStrands;
	private String fiberNetworkLevel;
	private String fiberType;
	private String fiberDeployment;
	private Timestamp creationDate;
	private String fiberOwner;
	private String srcWareID;
	private String srcId;
	private String srcName;
	private String srcLng;
	private String srcLat;
	private String destWare;
	private String destId;
	private String destName;
	private String destLng;
	private String destLat;
	private int sorting;

	public FiberCableDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FiberCableDetailsDTO(String fiberCableName, String numberOfTubes, String numberOfStrands,
			String fiberNetworkLevel, String fiberType, String fiberDeployment, Timestamp creationDate,
			String fiberOwner, String srcWareID, String srcId, String srcName, String srcLng, String srcLat,
			String destWare, String destId, String destName, String destLng, String destLat, int sorting) {
		super();
		this.fiberCableName = fiberCableName;
		this.numberOfTubes = numberOfTubes;
		this.numberOfStrands = numberOfStrands;
		this.fiberNetworkLevel = fiberNetworkLevel;
		this.fiberType = fiberType;
		this.fiberDeployment = fiberDeployment;
		this.creationDate = creationDate;
		this.fiberOwner = fiberOwner;
		this.srcWareID = srcWareID;
		this.srcId = srcId;
		this.srcName = srcName;
		this.srcLng = srcLng;
		this.srcLat = srcLat;
		this.destWare = destWare;
		this.destId = destId;
		this.destName = destName;
		this.destLng = destLng;
		this.destLat = destLat;
		this.sorting = sorting;
	}

	public String getFiberCableName() {
		return fiberCableName;
	}

	public void setFiberCableName(String fiberCableName) {
		this.fiberCableName = fiberCableName;
	}

	public String getNumberOfTubes() {
		return numberOfTubes;
	}

	public void setNumberOfTubes(String numberOfTubes) {
		this.numberOfTubes = numberOfTubes;
	}

	public String getNumberOfStrands() {
		return numberOfStrands;
	}

	public void setNumberOfStrands(String numberOfStrands) {
		this.numberOfStrands = numberOfStrands;
	}

	public String getFiberNetworkLevel() {
		return fiberNetworkLevel;
	}

	public void setFiberNetworkLevel(String fiberNetworkLevel) {
		this.fiberNetworkLevel = fiberNetworkLevel;
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

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getFiberOwner() {
		return fiberOwner;
	}

	public void setFiberOwner(String fiberOwner) {
		this.fiberOwner = fiberOwner;
	}

	public String getSrcWareID() {
		return srcWareID;
	}

	public void setSrcWareID(String srcWareID) {
		this.srcWareID = srcWareID;
	}

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public String getSrcName() {
		return srcName;
	}

	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}

	public String getSrcLng() {
		return srcLng;
	}

	public void setSrcLng(String srcLng) {
		this.srcLng = srcLng;
	}

	public String getSrcLat() {
		return srcLat;
	}

	public void setSrcLat(String srcLat) {
		this.srcLat = srcLat;
	}

	public String getDestWare() {
		return destWare;
	}

	public void setDestWare(String destWare) {
		this.destWare = destWare;
	}

	public String getDestId() {
		return destId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}

	public String getDestLng() {
		return destLng;
	}

	public void setDestLng(String destLng) {
		this.destLng = destLng;
	}

	public String getDestLat() {
		return destLat;
	}

	public void setDestLat(String destLat) {
		this.destLat = destLat;
	}

	public int getSorting() {
		return sorting;
	}

	public void setSorting(int sorting) {
		this.sorting = sorting;
	}
}