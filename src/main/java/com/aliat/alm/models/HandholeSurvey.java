package com.aliat.alm.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HANDHOLE_SURVEY")

public class HandholeSurvey {
	
	
	@Id
	@Column(name = "HANDHOLE_SURVEY_ID", nullable = false)
	private String handholeSurvID;
	
	@Column(name = "HANDHOLE_ID")
	private String handholeID;
	
	@Column(name = "HANDHOLE_NAME")
	private String handholeName;
	
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
	
	
	public HandholeSurvey() {
		super();
	}


	public HandholeSurvey(String handholeSurvID, String handholeID, String handholeName, String longitude, String latitude, double linearDistance,
			double drivingDistance,double geoDistance, String surveyID) {
		super();
		this.handholeSurvID=handholeSurvID;
		this.handholeID=handholeID;
		this.handholeName=handholeName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.linearDistance = linearDistance;
		this.drivingDistance=drivingDistance;
		this.geoDistance = geoDistance;
		this.surveyID = surveyID;
	}


	public String getHandholeSurvID() {
		return handholeSurvID;
	}


	public void setHandholeSurvID(String handholeSurvID) {
		this.handholeSurvID = handholeSurvID;
	}


	public String getHandholeID() {
		return handholeID;
	}


	public void setHandholeID(String handholeID) {
		this.handholeID = handholeID;
	}


	public String getHandholeName() {
		return handholeName;
	}


	public void setHandholeName(String handholeName) {
		this.handholeName = handholeName;
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

