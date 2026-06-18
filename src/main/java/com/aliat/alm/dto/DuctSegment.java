package com.aliat.alm.dto;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DuctSegment {
	
    @JsonIgnore
    private int sortSequence;
    private String drawingHtml;
    private Integer cableQty;
    private LinkedHashMap<String,String> cables;
	private String fromSequence;
    private String fromAuxId;
    private String fromAuxName;
    private String fromLongitude;
    private String fromLatitude;
    private String toSequence;
    private String toAuxId;
    private String toAuxName;
    private String toLongitude;
    private String toLatitude;
    private String panTo;
    private String showElement;
    
	public DuctSegment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DuctSegment(int sortSequence, String drawingHtml, Integer cableQty, LinkedHashMap<String, String> cables,
			String fromSequence, String fromAuxId, String fromAuxName, String fromLongitude, String fromLatitude,
			String toSequence, String toAuxId, String toAuxName, String toLongitude, String toLatitude, String panTo,
			String showElement) {
		super();
		this.sortSequence = sortSequence;
		this.drawingHtml = drawingHtml;
		this.cableQty = cableQty;
		this.cables = cables;
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
		this.panTo = panTo;
		this.showElement = showElement;
	}

	public int getSortSequence() {
		return sortSequence;
	}

	public void setSortSequence(int sortSequence) {
		this.sortSequence = sortSequence;
	}

	public String getDrawingHtml() {
		return drawingHtml;
	}

	public void setDrawingHtml(String drawingHtml) {
		this.drawingHtml = drawingHtml;
	}

	public Integer getCableQty() {
		return cableQty;
	}

	public void setCableQty(Integer cableQty) {
		this.cableQty = cableQty;
	}

	public LinkedHashMap<String, String> getCables() {
		return cables;
	}

	public void setCables(LinkedHashMap<String, String> cables) {
		this.cables = cables;
	}

	public String getFromSequence() {
		return fromSequence;
	}

	public void setFromSequence(String fromSequence) {
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

	public String getToSequence() {
		return toSequence;
	}

	public void setToSequence(String toSequence) {
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

	public String getPanTo() {
		return panTo;
	}

	public void setPanTo(String panTo) {
		this.panTo = panTo;
	}

	public String getShowElement() {
		return showElement;
	}

	public void setShowElement(String showElement) {
		this.showElement = showElement;
	}
    
	
}
