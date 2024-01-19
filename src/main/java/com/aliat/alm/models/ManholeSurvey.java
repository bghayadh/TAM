package com.aliat.alm.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MANHOLE_SURVEY")

public class ManholeSurvey {
	
	
	@Id
	@Column(name = "MANHOLE_SURVEY_ID", nullable = false)
	private String manholeSurvID;
	
	@Column(name = "MANHOLE_ID")
	private String manholeID;
	
	@Column(name = "MANHOLE_NAME")
	private String manholeName;
	
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
	
	
	public ManholeSurvey() {
		super();
	}


	public ManholeSurvey(String manholeSurvID, String manholeID, String manholeName, String longitude, String latitude, double linearDistance,
			double drivingDistance,double geoDistance, String surveyID) {
		super();
		this.manholeSurvID=manholeSurvID;
		this.manholeID=manholeID;
		this.manholeName=manholeName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.linearDistance = linearDistance;
		this.drivingDistance=drivingDistance;
		this.geoDistance = geoDistance;
		this.surveyID = surveyID;
	}


	public String getManholeSurvID() {
		return manholeSurvID;
	}


	public void setManholeSurvID(String manholeSurvID) {
		this.manholeSurvID = manholeSurvID;
	}


	public String getManholeID() {
		return manholeID;
	}


	public void setManholeID(String manholeID) {
		this.manholeID = manholeID;
	}


	public String getManholeName() {
		return manholeName;
	}


	public void setManholeName(String manholeName) {
		this.manholeName = manholeName;
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

