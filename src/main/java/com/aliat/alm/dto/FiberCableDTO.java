package com.aliat.alm.dto;

public class FiberCableDTO {
	
	String fiberCableId;
	String fiberCableName;
	
	public FiberCableDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FiberCableDTO(String fiberCableId, String fiberCableName) {
		super();
		this.fiberCableId = fiberCableId;
		this.fiberCableName = fiberCableName;
	}

	public String getFiberCableId() {
		return fiberCableId;
	}

	public void setFiberCableId(String fiberCableId) {
		this.fiberCableId = fiberCableId;
	}

	public String getFiberCableName() {
		return fiberCableName;
	}

	public void setFiberCableName(String fiberCableName) {
		this.fiberCableName = fiberCableName;
	}
}
