package com.aliat.alm.dto;

public class CableIntervalDTO {

	private String cableId;

	private Integer startSeq;
	private Integer endSeq;

	private String fromAuxId;
	private String toAuxId;

	public CableIntervalDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CableIntervalDTO(String cableId, Integer startSeq, Integer endSeq, String fromAuxId, String toAuxId) {
		super();
		this.cableId = cableId;
		this.startSeq = startSeq;
		this.endSeq = endSeq;
		this.fromAuxId = fromAuxId;
		this.toAuxId = toAuxId;
	}

	public String getCableId() {
		return cableId;
	}

	public void setCableId(String cableId) {
		this.cableId = cableId;
	}

	public Integer getStartSeq() {
		return startSeq;
	}

	public void setStartSeq(Integer startSeq) {
		this.startSeq = startSeq;
	}

	public Integer getEndSeq() {
		return endSeq;
	}

	public void setEndSeq(Integer endSeq) {
		this.endSeq = endSeq;
	}

	public String getFromAuxId() {
		return fromAuxId;
	}

	public void setFromAuxId(String fromAuxId) {
		this.fromAuxId = fromAuxId;
	}

	public String getToAuxId() {
		return toAuxId;
	}

	public void setToAuxId(String toAuxId) {
		this.toAuxId = toAuxId;
	}
}
