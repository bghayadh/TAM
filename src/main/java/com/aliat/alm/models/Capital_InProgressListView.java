package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CAPITAL_IN_PROGRESS")
public class Capital_InProgressListView {

	@Id
	@Column(name = "CIP_ID", nullable = false)
	private String cipID;
	
	private String capitalID;
	
	@Column(name = "ITEM_CODE", nullable = false)
	private String cipItemCode;
	
	@Column(name = "ITEM_NAME")
	private String cipItemName;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private String cipLastModifiedDate;
	
	@Column(name = "PO_ID")
	private String PoID;
	
	@Column(name = "SUPPLIER_ID")
	private String supplierID;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;

	public Capital_InProgressListView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Capital_InProgressListView(String cipID, String capitalID, String cipItemCode, String cipItemName,
			String cipLastModifiedDate, String poID, String supplierID, String supplierName) {
		super();
		this.cipID = cipID;
		this.capitalID = capitalID;
		this.cipItemCode = cipItemCode;
		this.cipItemName = cipItemName;
		this.cipLastModifiedDate = cipLastModifiedDate;
		this.PoID = poID;
		this.supplierID = supplierID;
		this.supplierName = supplierName;
	}

	public String getCipID() {
		return cipID;
	}

	public void setCipID(String cipID) {
		this.cipID = cipID;
	}

	public String getCapitalID() {
		return capitalID;
	}

	public void setCapitalID(String capitalID) {
		this.capitalID = capitalID;
	}

	public String getCipItemCode() {
		return cipItemCode;
	}

	public void setCipItemCode(String cipItemCode) {
		this.cipItemCode = cipItemCode;
	}

	public String getCipItemName() {
		return cipItemName;
	}

	public void setCipItemName(String cipItemName) {
		this.cipItemName = cipItemName;
	}

	public String getCipLastModifiedDate() {
		return cipLastModifiedDate;
	}

	public void setCipLastModifiedDate(String cipLastModifiedDate) {
		this.cipLastModifiedDate = cipLastModifiedDate;
	}

	public String getPoID() {
		return PoID;
	}

	public void setPoID(String poID) {
		PoID = poID;
	}

	public String getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
		

}
