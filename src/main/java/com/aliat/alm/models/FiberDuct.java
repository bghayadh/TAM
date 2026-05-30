package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIBER_DUCT")
public class FiberDuct {

	@Id
	@Column(name = "FIBER_DUCT_ID")
	private String fiberDuctId;

	@Column(name = "FIBER_PATH_ID")
	private String fiberPathId;

	@Column(name = "SEQUENCE_NO")
	private Integer sequenceNo;

	@Column(name = "DUCT_ID")
	private String ductId;

	@Column(name = "DUCT_NAME")
	private String ductName;

	@Column(name = "FROM_SEQUENCE")
	private String fromSequence;

	@Column(name = "FROM_AUX_ID")
	private String fromAuxId;

	@Column(name = "FROM_AUX_NAME")
	private String fromAuxName;

	@Column(name = "FROM_LONGITUDE")
	private String fromLongitude;

	@Column(name = "FROM_LATITUDE")
	private String fromLatitude;

	@Column(name = "TO_SEQUENCE")
	private String toSequence;

	@Column(name = "TO_AUX_ID")
	private String toAuxId;

	@Column(name = "TO_AUX_NAME")
	private String toAuxName;

	@Column(name = "TO_LONGITUDE")
	private String toLongitude;

	@Column(name = "TO_LATITUDE")
	private String toLatitude;

	@Column(name = "DISTANCE_KM")
	private Double distanceKm;

	@Column(name = "GEO_DISTANCE_KM")
	private Double geoDistanceKm;

	public FiberDuct() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FiberDuct(String fiberDuctId, String fiberPathId, Integer sequenceNo, String ductId, String ductName,
			String fromSequence, String fromAuxId, String fromAuxName, String fromLongitude, String fromLatitude,
			String toSequence, String toAuxId, String toAuxName, String toLongitude, String toLatitude,
			Double distanceKm, Double geoDistanceKm) {
		super();
		this.fiberDuctId = fiberDuctId;
		this.fiberPathId = fiberPathId;
		this.sequenceNo = sequenceNo;
		this.ductId = ductId;
		this.ductName = ductName;
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
		this.distanceKm = distanceKm;
		this.geoDistanceKm = geoDistanceKm;
	}

	public String getFiberDuctId() {
		return fiberDuctId;
	}

	public void setFiberDuctId(String fiberDuctId) {
		this.fiberDuctId = fiberDuctId;
	}

	public String getFiberPathId() {
		return fiberPathId;
	}

	public void setFiberPathId(String fiberPathId) {
		this.fiberPathId = fiberPathId;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getDuctId() {
		return ductId;
	}

	public void setDuctId(String ductId) {
		this.ductId = ductId;
	}

	public String getDuctName() {
		return ductName;
	}

	public void setDuctName(String ductName) {
		this.ductName = ductName;
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

	public Double getDistanceKm() {
		return distanceKm;
	}

	public void setDistanceKm(Double distanceKm) {
		this.distanceKm = distanceKm;
	}

	public Double getGeoDistanceKm() {
		return geoDistanceKm;
	}

	public void setGeoDistanceKm(Double geoDistanceKm) {
		this.geoDistanceKm = geoDistanceKm;
	}
}