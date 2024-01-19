package com.aliat.alm.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NODE_SURVEY")

public class NodeSurvey {
	
	
	@Id
	@Column(name = "NODE_SURVEY_ID", nullable = false)
	private String nodeSurvID;
	
	@Column(name = "NODE_ID")
	private String nodeID;
	
	@Column(name = "NODE_NAME")
	private String nodeName;
	
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
	
	
	public NodeSurvey() {
		super();
	}


	public NodeSurvey(String nodeSurvID, String nodeID, String nodeName, String longitude, String latitude, double linearDistance,
			double drivingDistance,double geoDistance, String surveyID) {
		super();
		this.nodeSurvID=nodeSurvID;
		this.nodeID=nodeID;
		this.nodeName=nodeName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.linearDistance = linearDistance;
		this.drivingDistance=drivingDistance;
		this.geoDistance = geoDistance;
		this.surveyID = surveyID;
	}


	public String getNodeSurvID() {
		return nodeSurvID;
	}


	public void setNodeSurvID(String nodeSurvID) {
		this.nodeSurvID = nodeSurvID;
	}


	public String getNodeID() {
		return nodeID;
	}


	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}


	public String getNodeName() {
		return nodeName;
	}


	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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

