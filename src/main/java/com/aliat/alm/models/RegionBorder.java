package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REGION_BORDER")
public class RegionBorder {

	@Id
	@Column(name = "ID", nullable = false)
	private String Id;

	@Column(name = "LONGTITUDE")
	private String lng;

	@Column(name = "LATITUDE")
	private String lat;
	
	@Column(name = "REGION_ID")
	private String regionId;

	@Column(name = "SEQ_SORTING")
	private String sequence;

	public RegionBorder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegionBorder(String id, String lng, String lat, String regionId, String sequence) {
		super();
		Id = id;
		this.lng = lng;
		this.lat = lat;
		this.regionId = regionId;
		this.sequence = sequence;
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

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	
}
