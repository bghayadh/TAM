package com.aliat.alm.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AGENT" , schema="alm")
public class SimAgentCountChartsReport {
	
private BigDecimal totalCount;
	
private String Date;

public SimAgentCountChartsReport() {
	super();
	// TODO Auto-generated constructor stub
}

public SimAgentCountChartsReport(BigDecimal totalCount, String date) {
	super();
	this.totalCount = totalCount;
	Date = date;
}

public BigDecimal getTotalCount() {
	return totalCount;
}

public void setTotalCount(BigDecimal totalCount) {
	this.totalCount = totalCount;
}

public String getDate() {
	return Date;
}

public void setDate(String date) {
	Date = date;
}

}
