package com.aliat.alm.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIBER_STRANDS_SURVEY")

public class FiberStrandsSurvey {
	
	
	@Id
	@Column(name = "STRAND_SURVEY_ID", nullable = false)
	private String strandSurvID;
	
	@Column(name = "STRAND_ID")
	private String strandID;
	
	@Column(name = "STRAND_NAME")
	private String strandName;
	
	@Column(name = "SOURCE")
	private String source;
	
	@Column(name = "DESTINATION")
	private String destination;
	
	@Column(name = "SURVEY_ID")
	private String surveyID;
	
	
	public FiberStrandsSurvey() {
		super();
	}


	public FiberStrandsSurvey(String strandSurvID, String strandID, String strandName, String source, String destination,String surveyID) {
		super();
		this.strandSurvID=strandSurvID;
		this.strandID=strandID;
		this.strandName=strandName;
		this.source = source;
		this.destination = destination;
		this.surveyID = surveyID;
	}




	public String getStrandSurvID() {
		return strandSurvID;
	}


	public void setStrandSurvID(String strandSurvID) {
		this.strandSurvID = strandSurvID;
	}


	public String getStrandID() {
		return strandID;
	}


	public void setStrandID(String strandID) {
		this.strandID = strandID;
	}


	public String getStrandName() {
		return strandName;
	}


	public void setStrandName(String strandName) {
		this.strandName = strandName;
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

