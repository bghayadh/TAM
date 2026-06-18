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
    private int sorting;
    
	public FiberCableDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FiberCableDetailsDTO(String fiberCableName, String numberOfTubes, String numberOfStrands,
			String fiberNetworkLevel, String fiberType, String fiberDeployment, Timestamp creationDate, int sorting) {
		super();
		this.fiberCableName = fiberCableName;
		this.numberOfTubes = numberOfTubes;
		this.numberOfStrands = numberOfStrands;
		this.fiberNetworkLevel = fiberNetworkLevel;
		this.fiberType = fiberType;
		this.fiberDeployment = fiberDeployment;
		this.creationDate = creationDate;
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

	public int getSorting() {
		return sorting;
	}

	public void setSorting(int sorting) {
		this.sorting = sorting;
	}
    
    }
