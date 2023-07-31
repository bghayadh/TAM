package com.aliat.alm.models;

public class SimRegReportAPI {
	
	private String fromdate;
	private String todate;
	private String status;
	private String msisdn;
	private String idNumber;
	private String fname;
	private String lname;
	private String agentnumber;
	
	public SimRegReportAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SimRegReportAPI(String fromdate, String todate, String status, String msisdn, String idNumber, String fname,
			String lname, String agentnumber) {
		super();
		this.fromdate = fromdate;
		this.todate = todate;
		this.status = status;
		this.msisdn = msisdn;
		this.idNumber = idNumber;
		this.fname = fname;
		this.lname = lname;
		this.agentnumber = agentnumber;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAgentnumber() {
		return agentnumber;
	}

	public void setAgentnumber(String agentnumber) {
		this.agentnumber = agentnumber;
	}

}
