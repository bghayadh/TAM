package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REPORT_INPUT_PARAMS")
public class ReportInputParams {
	
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getReportID() {
		return ReportID;
	}

	public void setReportID(String reportID) {
		ReportID = reportID;
	}

	public String getInputLabel() {
		return InputLabel;
	}

	public void setInputLabel(String inputLabel) {
		InputLabel = inputLabel;
	}

	public String getInputType() {
		return InputType;
	}

	public void setInputType(String inputType) {
		InputType = inputType;
	}

	public String getInputID() {
		return InputID;
	}

	public void setInputID(String inputID) {
		InputID = inputID;
	}

	public String getInputName() {
		return InputName;
	}

	public void setInputName(String inputName) {
		InputName = inputName;
	}

	public ReportInputParams() {
		super();
	}

	public ReportInputParams(String iD, String reportID, String inputLabel, String inputType, String inputID,
			String inputName, String inputValue) {
		super();
		ID = iD;
		ReportID = reportID;
		InputLabel = inputLabel;
		InputType = inputType;
		InputID = inputID;
		InputName = inputName;
		InputValue = inputValue;
	}

	@Id
	@Column(name = "ID", nullable = false)
	private String ID;
	
	@Column(name = "REPORT_ID")
	private String ReportID;
	
	@Column(name = "INPUT_LABEL")
	private String InputLabel;
	
	@Column(name = "INPUT_TYPE")
	private String InputType;
	
	@Column(name = "INPUT_ID")
	private String InputID;
	
	@Column(name = "INPUT_NAME")
	private String InputName;
	
	@Column(name = "INPUT_VALUE")
	private String InputValue;

	public String getInputValue() {
		return InputValue;
	}

	public void setInputValue(String inputValue) {
		InputValue = inputValue;
	}

}
