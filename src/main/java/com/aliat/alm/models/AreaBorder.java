package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AREA_BORDER")
public class AreaBorder {
	
	@Id
	@Column(name = "ID", nullable = false)
	private String Id;

	@Column(name = "LONGTITUDE")
	private String lng;

	@Column(name = "LATITUDE")
	private String lat;
	
	@Column(name = "AREA_ID")
	private String areaId;

	@Column(name = "SEQ_SORTING")
	private String Sequence;

	public AreaBorder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AreaBorder(String id, String lng, String lat, String areaId, String sequence) {
		super();
		Id = id;
		this.lng = lng;
		this.lat = lat;
		this.areaId = areaId;
		Sequence = sequence;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getSequence() {
		return Sequence;
	}

	public void setSequence(String sequence) {
		Sequence = sequence;
	}


}
