package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUPPLIER")
public class Supplier {
	
	@Id
	@Column(name = "SUPPLIER_ID", nullable = false)
	private String supplierID;
	
	@Column(name = "CREATION_DATE")
	private Timestamp screationDate;
		
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp slastModifieddate;
	
	@Column(name = "VENDOR_NUMBER")
	private String vendorNb;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name = "SUPPLIER_DESCRIPTION")
	private String supplierDescription;
	
	@Column(name = "SUPPLIER_CATEGORY")
	private String supplierCategory;
	
	@Column(name = "WEBSITE")
	private String sWebsite;
	
	@Column(name = "COUNTRY")
	private String sCountry;
	
	@Column(name = "TAX_ID")
	private String supplierTaxid;
	
	@Column(name = "CREDIT_LIMIT")
	private float supplierCreditlimit;
	
	@Column(name = "ADDRESS_1")
	private String supplierAddress1;
	
	@Column(name = "ADDRESS_2")
	private String supplierAddress2;
	
	@Column(name = "PHONE")
	private String sPhone;
	
	@Column(name = "MOBILE")
	private String sMobile;
	
	@Column(name = "EMAIL")
	private String sEmail;
	
	@Column(name = "CONTACT_PERSON")
	private String sContactperson;
	
	@Column(name = "DISABLED")
	private char sDisabled;
	
	public Supplier() {	
	}
	
	

	public Supplier(String supplierID, Timestamp screationDate, Timestamp slastModifieddate, String vendorNb,
			String supplierName, String supplierDescription, String supplierCategory, String sWebsite, String sCountry,
			String supplierTaxid, float supplierCreditlimit, String supplierAddress1, String supplierAddress2,
			String sPhone, String sMobile, String sEmail, String sContactperson, char sDisabled) {
		super();
		this.supplierID = supplierID;
		this.screationDate = screationDate;
		this.slastModifieddate = slastModifieddate;
		this.vendorNb = vendorNb;
		this.supplierName = supplierName;
		this.supplierDescription = supplierDescription;
		this.supplierCategory = supplierCategory;
		this.sWebsite = sWebsite;
		this.sCountry = sCountry;
		this.supplierTaxid = supplierTaxid;
		this.supplierCreditlimit = supplierCreditlimit;
		this.supplierAddress1 = supplierAddress1;
		this.supplierAddress2 = supplierAddress2;
		this.sPhone = sPhone;
		this.sMobile = sMobile;
		this.sEmail = sEmail;
		this.sContactperson = sContactperson;
		this.sDisabled = sDisabled;
	}



	public String getSupplierID() {
		return supplierID;
	}



	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}



	public Timestamp getScreationDate() {
		return screationDate;
	}



	public void setScreationDate(Timestamp screationDate) {
		this.screationDate = screationDate;
	}



	public Timestamp getSlastModifieddate() {
		return slastModifieddate;
	}



	public void setSlastModifieddate(Timestamp slastModifieddate) {
		this.slastModifieddate = slastModifieddate;
	}



	public String getVendorNb() {
		return vendorNb;
	}



	public void setVendorNb(String vendorNb) {
		this.vendorNb = vendorNb;
	}



	public String getSupplierName() {
		return supplierName;
	}



	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}



	public String getSupplierDescription() {
		return supplierDescription;
	}



	public void setSupplierDescription(String supplierDescription) {
		this.supplierDescription = supplierDescription;
	}



	public String getSupplierCategory() {
		return supplierCategory;
	}



	public void setSupplierCategory(String supplierCategory) {
		this.supplierCategory = supplierCategory;
	}



	public String getsWebsite() {
		return sWebsite;
	}



	public void setsWebsite(String sWebsite) {
		this.sWebsite = sWebsite;
	}



	public String getsCountry() {
		return sCountry;
	}



	public void setsCountry(String sCountry) {
		this.sCountry = sCountry;
	}



	public String getSupplierTaxid() {
		return supplierTaxid;
	}



	public void setSupplierTaxid(String supplierTaxid) {
		this.supplierTaxid = supplierTaxid;
	}



	public float getSupplierCreditlimit() {
		return supplierCreditlimit;
	}



	public void setSupplierCreditlimit(float supplierCreditlimit) {
		this.supplierCreditlimit = supplierCreditlimit;
	}



	public String getSupplierAddress1() {
		return supplierAddress1;
	}



	public void setSupplierAddress1(String supplierAddress1) {
		this.supplierAddress1 = supplierAddress1;
	}



	public String getSupplierAddress2() {
		return supplierAddress2;
	}



	public void setSupplierAddress2(String supplierAddress2) {
		this.supplierAddress2 = supplierAddress2;
	}



	public String getsPhone() {
		return sPhone;
	}



	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}



	public String getsMobile() {
		return sMobile;
	}



	public void setsMobile(String sMobile) {
		this.sMobile = sMobile;
	}



	public String getsEmail() {
		return sEmail;
	}



	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}



	public String getsContactperson() {
		return sContactperson;
	}



	public void setsContactperson(String sContactperson) {
		this.sContactperson = sContactperson;
	}



	public char getsDisabled() {
		return sDisabled;
	}

	public void setsDisabled(char sDisabled) {
		this.sDisabled = sDisabled;
	}


}
