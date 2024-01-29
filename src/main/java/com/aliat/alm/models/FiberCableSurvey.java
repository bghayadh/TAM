package com.aliat.alm.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIBER_CABLES_SURVEY")

public class FiberCableSurvey {
	
	
	@Id
	@Column(name = "FIBER_CABLE_SURVEY_ID", nullable = false)
	private String fiberCableSurvID;
	
	@Column(name = "FIBER_CABLE_ID")
	private String fiberCableID;
	
	@Column(name = "FIBER_CABLE_NAME")
	private String fiberCableName;
	
	@Column(name = "SOURCE")
	private String source;
	
	@Column(name = "DESTINATION")
	private String destination;
	
	@Column(name = "SURVEY_ID")
	private String surveyID;
	
	
	public FiberCableSurvey() {
		super();
	}


	public FiberCableSurvey(String fiberCableSurvID, String fiberCableID, String fiberCableName, String source, String destination,String surveyID) {
		super();
		this.fiberCableSurvID=fiberCableSurvID;
		this.fiberCableID=fiberCableID;
		this.fiberCableName=fiberCableName;
		this.source = source;
		this.destination = destination;
		this.surveyID = surveyID;
	}


	public String getFiberCableSurvID() {
		return fiberCableSurvID;
	}


	public void setFiberCableSurvID(String fiberCableSurvID) {
		this.fiberCableSurvID = fiberCableSurvID;
	}


	public String getFiberCableID() {
		return fiberCableID;
	}


	public void setFiberCableID(String fiberCableID) {
		this.fiberCableID = fiberCableID;
	}


	public String getFiberCableName() {
		return fiberCableName;
	}


	public void setFiberCableName(String fiberCableName) {
		this.fiberCableName = fiberCableName;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSurveyID() {
		return surveyID;
	}

	public void setSurveyID(String surveyID) {
		this.surveyID = surveyID;
	}
	
}

