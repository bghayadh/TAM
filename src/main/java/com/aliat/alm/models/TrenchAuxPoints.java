package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TRENCH_AUXILIARY_POINTS")
public class TrenchAuxPoints {

	@Id
	@Column(name = "AUXILIARY_ID", nullable = false)
	private String auxID;
	
	@Column(name = "TRENCH_ID")
	private String trenchID;

	@Column(name = "LONGITUDE")
	private double Long;
	
	@Column(name = "LATITUDE")
	private double lat;
	
	@Column(name = "WARE_ID")
	private String wareID;
	
	@Column(name = "AUXILIARY_POINT_NAME")
	private String auxPointName;
	
	@Column(name = "AUXILIARY_POINT_ID")
	private String auxPointID;
	
	@Column(name = "DISTANCE_FROM_SOURCE")
	private double distancefromsource;
	
	@Column(name = "SEQ_SORTING")
	private String seqSorting;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifieddate;
		
	@Column(name = "DRIVING_DISTANCE")
	private double drivingDist;
	
	@Column(name = "GEO_DISTANCE")
	private double geoDist;
	
	public TrenchAuxPoints() {
		super();
	}
	
	public TrenchAuxPoints(String auxID, String trenchID, double l, double lat,
			double distancefromsource, String wareID, String auxPointName, String auxPointID, Timestamp creationDate, Timestamp lastModifieddate,
			String seqSorting,double drivingDist,double geoDist) {
		super();
		this.auxID = auxID;
		this.trenchID = trenchID;
		this.Long = l;
		this.lat = lat;
		this.distancefromsource = distancefromsource;
		this.wareID=wareID;
		this.auxPointName=auxPointName;
		this.auxPointID=auxPointID;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.seqSorting = seqSorting;
		this.drivingDist = drivingDist;
		this.geoDist = geoDist;
	}

	public String getAuxID() {
		return auxID;
	}

	public void setAuxID(String auxID) {
		this.auxID = auxID;
	}

	public String getTrenchID() {
		return trenchID;
	}

	public void setTrenchID(String trenchID) {
		this.trenchID = trenchID;
	}

	public double getLong() {
		return Long;
	}

	public void setLong(double l) {
		Long = l;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getWareID() {
		return wareID;
	}

	public void setWareID(String wareID) {
		this.wareID = wareID;
	}

	public String getAuxPointName() {
		return auxPointName;
	}

	public void setAuxPointName(String auxPointName) {
		this.auxPointName = auxPointName;
	}

	public String getAuxPointID() {
		return auxPointID;
	}

	public void setAuxPointID(String auxPointID) {
		this.auxPointID = auxPointID;
	}

	public double getDistancefromsource() {
		return distancefromsource;
	}

	public void setDistancefromsource(double distancefromsource) {
		this.distancefromsource = distancefromsource;
	}

	public String getSeqSorting() {
		return seqSorting;
	}

	public void setSeqSorting(String seqSorting) {
		this.seqSorting = seqSorting;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastModifieddate() {
		return lastModifieddate;
	}

	public void setLastModifieddate(Timestamp lastModifieddate) {
		this.lastModifieddate = lastModifieddate;
	}

	public double getDrivingDist() {
		return drivingDist;
	}

	public void setDrivingDist(double drivingDist) {
		this.drivingDist = drivingDist;
	}

	public double getGeoDist() {
		return geoDist;
	}

	public void setGeoDist(double geoDist) {
		this.geoDist = geoDist;
	}



}
