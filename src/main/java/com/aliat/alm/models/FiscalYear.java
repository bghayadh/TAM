package com.aliat.alm.models;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FISCAL_YEAR")
public class FiscalYear {
	
	
	@Id
	@Column(name = "YEAR_NAME", nullable = false)
	private String yearName;
		
	@Column(name = "YEAR_START_DATE")
	private Date yearStartDate;
	
	@Column(name = "YEAR_END_DATE")
	private Date yearEndDate;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name = "IS_SHORT_YEAR")
	private char is_ShortYear;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Date lastModifiedDate;
	

	public FiscalYear() {
		super();
		// TODO Auto-generated constructor stub
	}


	public FiscalYear(String yearName, Date yearStartDate, Date yearEndDate, String status,
			char is_ShortYear) {
		super();
		this.yearName = yearName;
		this.yearStartDate = yearStartDate;
		this.yearEndDate = yearEndDate;
		this.status = status;
		this.is_ShortYear = is_ShortYear;
	}	

	public String getYearName() {
		return yearName;
	}


	public void setYearName(String yearName) {
		this.yearName = yearName;
	}


	public Date getYearStartDate() {
		return yearStartDate;
	}


	public void setYearStartDate(Date yearStartDate) {
		this.yearStartDate = yearStartDate;
	}


	public Date getYearEndDate() {
		return yearEndDate;
	}


	public void setYearEndDate(Date yearEndDate) {
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


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}


	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	
}
