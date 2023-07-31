package com.aliat.alm.models;

public class SimRegistrationAPI {
	private String clientpkid;
	private String msisdn;
	private String fname;
	private String mname;
	private String lname;
	private String idType;
	private String idNumber;
	private String dob;
	private String gender;
	private String email;
	private String altnumber;
	private String address1;
	private String state;
	private String agentmsisdn;
	
	
	public SimRegistrationAPI() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SimRegistrationAPI(String clientpkid,String msisdn, String fname, String mname, String lname, String idType, String idNumber,
			String dob, String gender, String email, String altnumber, String address1, String state,
			String agentmsisdn) {
		super();
		
		this.clientpkid = clientpkid;
		this.msisdn = msisdn;
		this.fname = fname;
		this.mname = mname;
		this.lname = lname;
		this.idType = idType;
		this.idNumber = idNumber;
		this.dob = dob;
		this.gender = gender;
		this.email = email;
		this.altnumber = altnumber;
		this.address1 = address1;
		this.state = state;
		this.agentmsisdn = agentmsisdn;
	}

	

	public String getClientpkid() {
		return clientpkid;
	}


	public void setClientpkid(String clientpkid) {
		this.clientpkid = clientpkid;
	}
	
	public String getMsisdn() {
		return msisdn;
	}


	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}


	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getMname() {
		return mname;
	}


	public void setMname(String mname) {
		this.mname = mname;
	}


	public String getLname() {
		return lname;
	}


	public void setLname(String lname) {
		this.lname = lname;
	}


	public String getIdType() {
		return idType;
	}


	public void setIdType(String idType) {
		this.idType = idType;
	}


	public String getIdNumber() {
		return idNumber;
	}


	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAltnumber() {
		return altnumber;
	}


	public void setAltnumber(String altnumber) {
		this.altnumber = altnumber;
	}


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getAgentmsisdn() {
		return agentmsisdn;
	}


	public void setAgentmsisdn(String agentmsisdn) {
		this.agentmsisdn = agentmsisdn;
	}


	

}
