package com.aliat.alm.models;

import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class FiscalYearListView 
{

	private BigDecimal chkBox;
	
	private String yearName;
		
	private String yearStartDate;
	
	private String yearEndDate;
	
	private String status;

	private char is_ShortYear;
		
	private String lastModifiedDate;
	

	public FiscalYearListView() {
		super();
		// TODO Auto-generated constructor stub
	}


	public FiscalYearListView(BigDecimal chkBox, String yearName, String yearStartDate, String yearEndDate, String status,
			char is_ShortYear, String lastModifiedDate) {
		super();
		this.chkBox = chkBox;
		this.yearName = yearName;
		this.yearStartDate = yearStartDate;
		this.yearEndDate = yearEndDate;
		this.status = status;
		this.is_ShortYear = is_ShortYear;
		this.lastModifiedDate = lastModifiedDate;
	}	

	public BigDecimal getChkBox() {
		return chkBox;
	}


	public void setChkBox(BigDecimal chkBox) {
		this.chkBox = chkBox;
	}


	public String getYearName() {
		return yearName;
	}


	public void setYearName(String yearName) {
		this.yearName = yearName;
	}


	public String getYearStartDate() {
		return yearStartDate;
	}


	public void setYearStartDate(String yearStartDate) {
		this.yearStartDate = yearStartDate;
	}


	public String getYearEndDate() {
		return yearEndDate;
	}


	public void setYearEndDate(String yearEndDate) {
		this.yearEndDate = yearEndDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public char getIs_ShortYear() {
		return is_ShortYear;
	}


	public void setIs_ShortYear(char is_ShortYear) {
		this.is_ShortYear = is_ShortYear;
	}



	public String getLastModifiedDate() {
		return lastModifiedDate;
	}


	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	
}
