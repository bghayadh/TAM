package com.aliat.alm.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISTRIBUTION_BOARD_SURVEY")

public class DistributionBoardSurvey {
	
	
	@Id
	@Column(name = "DB_SURVEY_ID", nullable = false)
	private String dbSurvID;
	
	@Column(name = "DB_ID")
	private String dbID;
	
	@Column(name = "DB_NAME")
	private String dbName;
	
	@Column(name = "LONGITUDE")
	private String longitude;
	
	@Column(name = "LATITUDE")
	private String latitude;
	
	@Column(name = "LINEAR_DISTANCE")
	private double linearDistance;
	
	@Column(name = "DRIVING_DISTANCE")
	private double drivingDistance;

	@Column(name = "GEO_DISTANCE")
	private double geoDistance;
	
	@Column(name = "SURVEY_ID")
	private String surveyID;
	
	
	public DistributionBoardSurvey() {
		super();
	}


	public DistributionBoardSurvey(String dbSurvID, String dbID, String dbName, String longitude, String latitude, double linearDistance,
			double drivingDistance,double geoDistance, String surveyID) {
		super();
		this.dbSurvID=dbSurvID;
		this.dbID=dbID;
		this.dbName=dbName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.linearDistance = linearDistance;
		this.drivingDistance=drivingDistance;
		this.geoDistance = geoDistance;
		this.surveyID = surveyID;
	}

	public String getDbSurvID() {
		return dbSurvID;
	}


	public void setDbSurvID(String dbSurvID) {
		this.dbSurvID = dbSurvID;
	}


	public String getDbID() {
		return dbID;
	}


	public void setDbID(String dbID) {
		this.dbID = dbID;
	}


	public String getDbName() {
		return dbName;
	}


	public void setDbName(String dbName) {
		this.dbName = dbName;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public double getLinearDistance() {
		return linearDistance;
	}


	public void setLinearDistance(double linearDistance) {
		this.linearDistance = linearDistance;
	}


	public double getDrivingDistance() {
		return drivingDistance;
	}


	public void setDrivingDistance(double drivingDistance) {
		this.drivingDistance = drivingDistance;
	}


	public double getGeoDistance() {
		return geoDistance;
	}


	public void setGeoDistance(double geoDistance) {
		this.geoDistance = geoDistance;
	}


	public String getSurveyID() {
		return surveyID;
	}


	public void setSurveyID(String surveyID) {
		this.surveyID = surveyID;
	}

}

