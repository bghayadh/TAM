package com.aliat.alm.models;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RULE_PARAMETERS")
public class RuleParameters {
	
	@Id
	@Column(name = "ID" , nullable = false)
	private String ParameterID;
	
	@Column(name = "RULE_ID")
	private String RuleID;
	
	@Column(name = "REPORT_ID")
	private String ReportID;
	
	@Column(name = "FIELD_ID")
	private String FieldID;
	
	@Column(name = "ENABLE")
	private char Enable;
	
	@Column(name = "VALUE")
	private String Value;
	
	@Column(name = "TYPE")
	private String Type;
	
	@Column(name = "OPTIONS")
	private String Options;

	

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getOptions() {
		return Options;
	}

	public void setOptions(String options) {
		Options = options;
	}

	public RuleParameters(String parameterID, String ruleID, String reportID, String fieldID, char enable, String value,
			String type, String options) {
		super();
		ParameterID = parameterID;
		RuleID = ruleID;
		ReportID = reportID;
		FieldID = fieldID;
		Enable = enable;
		Value = value;
		Type = type;
		Options = options;
	}

	public RuleParameters() {
		super();
	}

	public String getParameterID() {
		return ParameterID;
	}

	public void setParameterID(String parameterID) {
		ParameterID = parameterID;
	}

	public String getRuleID() {
		return RuleID;
	}

	public void setRuleID(String ruleID) {
		RuleID = ruleID;
	}

	public String getReportID() {
		return ReportID;
	}

	public void setReportID(String reportID) {
		ReportID = reportID;
	}

	public String getFieldID() {
		return FieldID;
	}

	public void setFieldID(String fieldID) {
		FieldID = fieldID;
	}

	public char getEnable() {
		return Enable;
	}

	public void setEnable(char enable) {
		Enable = enable;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}
	
	

}
