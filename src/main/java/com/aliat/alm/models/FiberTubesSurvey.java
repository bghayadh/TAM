package com.aliat.alm.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIBER_TUBES_SURVEY")

public class FiberTubesSurvey {
	
	
	@Id
	@Column(name = "TUBE_SURVEY_ID", nullable = false)
	private String tubeSurvID;
	
	@Column(name = "TUBE_ID")
	private String tubeID;
	
	@Column(name = "TUBE_NAME")
	private String tubeName;
	
	@Column(name = "SOURCE")
	private String source;
	
	@Column(name = "DESTINATION")
	private String destination;
	
	@Column(name = "SURVEY_ID")
	private String surveyID;
	
	
	public FiberTubesSurvey() {
		super();
	}


	public FiberTubesSurvey(String tubeSurvID, String tubeID, String tubeName, String source, String destination,String surveyID) {
		super();
		this.tubeSurvID=tubeSurvID;
		this.tubeID=tubeID;
		this.tubeName=tubeName;
		this.source = source;
		this.destination = destination;
		this.surveyID = surveyID;
	}


	public String getTubeSurvID() {
		return tubeSurvID;
	}


	public void setTubeSurvID(String tubeSurvID) {
		this.tubeSurvID = tubeSurvID;
	}


	public String getTubeID() {
		return tubeID;
	}


	public void setTubeID(String tubeID) {
		this.tubeID = tubeID;
	}


	public String getTubeName() {
		return tubeName;
	}


	public void setTubeName(String tubeName) {
		this.tubeName = tubeName;
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

