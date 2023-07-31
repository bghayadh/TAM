package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.aliat.alm.securepassword.EncryptDecryptPassword;

@Entity
@Table(name = "USERS_TABLE")
public class User {
	
	@Id
	@Column(name = "USER_NAME", nullable = false)
	private String userName;
	
	@Column(name = "USER_PASSWORD")
	private String userpass;
	
	@Column(name = "USER_FIRST_NAME")
	private String fname;
	
	@Column(name = "USER_MIDDLE_NAME")
	private String mname;
	
	@Column(name = "USER_LAST_NAME")
	private String lname;
	
	@Column(name = "USER_EMAIL_ADDRESS")
	private String emailAddress;
	
	@Column(name = "USER_MOBILE_NUMBER")
	private String mobileNb;
	
	@Column(name = "USER_TYPE")
	private String userType;
	

	@Column(name = "USER_CIRCLE")
	private String userCircle;
	
	
	@Column(name = "USER_CIRCLE_CITY")
	private String userCirclecity;
	

	@Column(name = "USER_CIRCLE_STATE")
	private String userCirclestate;
	

	@Column(name = "USER_CIRCLE_COUNTRY")
	private String userCirclecountry;
	

	@Column(name = "USER_ROLE")
	private String user_role;
	

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	

	@Column(name = "MODIFIED_DATE")
	private Timestamp modified_date;

	
	@Column(name = "ADDRESS")
	private String address;
	

	@Column(name = "LOGOUT")
	private char logout;
	

	@Column(name = "UPDATE_PASSWORD")
	private char updatePass;
	
	@Column(name = "USER_JOB_TITLE")
	private String userJobTitle;

		//constructors
	
	public User(){
		}

		public User(String userName, String userpass, String fname, String mname, String lname, String emailAddress,
				String mobileNb, String userType, String userCircle, String userJobTitle, String userCirclecity, String userCirclestate,
				String userCirclecountry, String user_role, Timestamp createdDate, Timestamp modified_date,
				String address, char logout, char updatePass) {
			super();
			this.userName = userName;
			this.userpass = userpass;
			this.fname = fname;
			this.mname = mname;
			this.lname = lname;
			this.emailAddress = emailAddress;
			this.mobileNb = mobileNb;
			this.userType = userType;
			this.userCircle = userCircle;
			this.userJobTitle = userJobTitle;
			this.userCirclecity = userCirclecity;
			this.userCirclestate = userCirclestate;
			this.userCirclecountry = userCirclecountry;
			this.user_role = user_role;
			this.createdDate = createdDate;
			this.modified_date = modified_date;
			this.address = address;
			this.logout = logout;
			this.updatePass = updatePass;
			
		}

		public String getUserName() {
			return userName;
		}

		public String getUserpass() {
			return userpass;
		}

		public String getFname() {
			return fname;
		}

		public String getMname() {
			return mname;
		}

		public String getLname() {
			return lname;
		}

		public String getEmailAddress() {
			return emailAddress;
		}

		public String getMobileNb() {
			return mobileNb;
		}

		public String getUserType() {
			return userType;
		}

		public String getUserCircle() {
			return userCircle;
		}
		
		//userJobTitle
		public String getUserJobTitle() {
			return userJobTitle;
		}

		public String getUserCirclecity() {
			return userCirclecity;
		}

		public String getUserCirclestate() {
			return userCirclestate;
		}

		public String getUserCirclecountry() {
			return userCirclecountry;
		}

		public String getUser_role() {
			return user_role;
		}

		public Timestamp getCreatedDate() {
			return createdDate;
		}

		public Timestamp getModified_date() {
			return modified_date;
		}

		public String getAddress() {
			return address;
		}

		public char getLogout() {
			return logout;
		}

		public char getUpdatePass() {
			return updatePass;
		}

	


		public void setUserName(String userName) {
			this.userName = userName;
		}

		public void setUserpass(String userpass) {
			this.userpass = userpass;
		}

		public void setFname(String fname) {
			this.fname = fname;
		}

		public void setMname(String mname) {
			this.mname = mname;
		}

		public void setLname(String lname) {
			this.lname = lname;
		}

		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}

		public void setMobileNb(String mobileNb) {
			this.mobileNb = mobileNb;
		}

		public void setUserType(String userType) {
			this.userType = userType;
		}

		public void setUserCircle(String userCircle) {
			this.userCircle = userCircle;
		}

		public void setUserJobTitle(String userJobTitle) {
			this.userJobTitle = userJobTitle;
		}

		
		public void setUserCirclecity(String userCirclecity) {
			this.userCirclecity = userCirclecity;
		}

		public void setUserCirclestate(String userCirclestate) {
			this.userCirclestate = userCirclestate;
		}

		public void setUserCirclecountry(String userCirclecountry) {
			this.userCirclecountry = userCirclecountry;
		}

		public void setUser_role(String user_role) {
			this.user_role = user_role;
		}

		public void setCreatedDate(Timestamp createdDate) {
			this.createdDate = createdDate;
		}

		public void setModified_date(Timestamp modified_date) {
			this.modified_date = modified_date;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setLogout(char logout) {
			this.logout = logout;
		}

		public void setUpdatePass(char updatePass) {
			this.updatePass = updatePass;
		}


		}