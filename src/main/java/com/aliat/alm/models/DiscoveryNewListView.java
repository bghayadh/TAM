package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISCOVERY_NEW")
public class DiscoveryNewListView {
	
	@Id
	@Column(name = "DN_ID")
	private String dnID;
	
	private String dnewID;
	
	@Column(name = "TOTAL_AMOUNT")
	private String dnTotalAmount;
	
	@Column(name = "TOTAL_QTY")
	private String dnTotalQty;
	
	@Column(name = "STATUS")
	private String dnStatus;
	
	@Column(name = "LAST_MODIF_DATE")
	private String dnlastmodifDate;

	public DiscoveryNewListView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiscoveryNewListView(String dnID, String dnewID, String dnTotalAmount, String dnTotalQty, String dnStatus,
			String dnlastmodifDate) {
		super();
		this.dnID = dnID;
		this.dnewID = dnewID;
		this.dnTotalAmount = dnTotalAmount;
		this.dnTotalQty = dnTotalQty;
		this.dnStatus = dnStatus;
		this.dnlastmodifDate = dnlastmodifDate;
	}

	public String getDnID() {
		return dnID;
	}

	public void setDnID(String dnID) {
		this.dnID = dnID;
	}

	public String getDnewID() {
		return dnewID;
	}

	public void setDnewID(String dnewID) {
		this.dnewID = dnewID;
	}

	public String getDnTotalAmount() {
		return dnTotalAmount;
	}

	public void setDnTotalAmount(String dnTotalAmount) {
		this.dnTotalAmount = dnTotalAmount;
	}

	public String getDnTotalQty() {
		return dnTotalQty;
	}

	public void setDnTotalQty(String dnTotalQty) {
		this.dnTotalQty = dnTotalQty;
	}

	public String getDnStatus() {
		return dnStatus;
	}

	public void setDnStatus(String dnStatus) {
		this.dnStatus = dnStatus;
	}

	public String getDnlastmodifDate() {
		return dnlastmodifDate;
	}

	public void setDnlastmodifDate(String dnlastmodifDate) {
		this.dnlastmodifDate = dnlastmodifDate;
	}


	
	
	
}
