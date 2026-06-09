package com.aliat.alm.dto;

import java.util.LinkedHashMap;
import java.util.List;

public class DuctSegmentDTO {
	
    Double fromSequence;
    String fromAuxId;
    String fromAuxName;
    String fromLongitude;
    String fromLatitude;

    Double toSequence;
    String toAuxId;
    String toAuxName;
    String toLongitude;
    String toLatitude;
    
    LinkedHashMap<String,String> cables;

    Integer cableQty;

    String drawingHtml;

	public DuctSegmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DuctSegmentDTO(Double fromSequence, String fromAuxId, String fromAuxName, String fromLongitude,
			String fromLatitude, Double toSequence, String toAuxId, String toAuxName, String toLongitude,
			String toLatitude, LinkedHashMap<String, String> cables, Integer cableQty, String drawingHtml) {
		super();
		this.fromSequence = fromSequence;
		this.fromAuxId = fromAuxId;
		this.fromAuxName = fromAuxName;
		this.fromLongitude = fromLongitude;
		this.fromLatitude = fromLatitude;
		this.toSequence = toSequence;
		this.toAuxId = toAuxId;
		this.toAuxName = toAuxName;
		this.toLongitude = toLongitude;
		this.toLatitude = toLatitude;
		this.cables = cables;
		this.cableQty = cableQty;
		this.drawingHtml = drawingHtml;
	}

	public Double getFromSequence() {
		return fromSequence;
	}

	public void setFromSequence(Double fromSequence) {
		this.fromSequence = fromSequence;
	}

	public String getFromAuxId() {
		return fromAuxId;
	}

	public void setFromAuxId(String fromAuxId) {
		this.fromAuxId = fromAuxId;
	}

	public String getFromAuxName() {
		return fromAuxName;
	}

	public void setFromAuxName(String fromAuxName) {
		this.fromAuxName = fromAuxName;
	}

	public String getFromLongitude() {
		return fromLongitude;
	}

	public void setFromLongitude(String fromLongitude) {
		this.fromLongitude = fromLongitude;
	}

	public String getFromLatitude() {
		return fromLatitude;
	}

	public void setFromLatitude(String fromLatitude) {
		this.fromLatitude = fromLatitude;
	}

	public Double getToSequence() {
		return toSequence;
	}

	public void setToSequence(Double toSequence) {
		this.toSequence = toSequence;
	}

	public String getToAuxId() {
		return toAuxId;
	}

	public void setToAuxId(String toAuxId) {
		this.toAuxId = toAuxId;
	}

	public String getToAuxName() {
		return toAuxName;
	}

	public void setToAuxName(String toAuxName) {
		this.toAuxName = toAuxName;
	}

	public String getToLongitude() {
		return toLongitude;
	}

	public void setToLongitude(String toLongitude) {
		this.toLongitude = toLongitude;
	}

	public String getToLatitude() {
		return toLatitude;
	}

	public void setToLatitude(String toLatitude) {
		this.toLatitude = toLatitude;
	}

	public LinkedHashMap<String, String> getCables() {
		return cables;
	}

	public void setCables(LinkedHashMap<String, String> cables) {
		this.cables = cables;
	}

	public Integer getCableQty() {
		return cableQty;
	}

	public void setCableQty(Integer cableQty) {
		this.cableQty = cableQty;
	}

	public String getDrawingHtml() {
		return drawingHtml;
	}

	public void setDrawingHtml(String drawingHtml) {
		this.drawingHtml = drawingHtml;
	}

}
