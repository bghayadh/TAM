package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISCOVERY_NEW")
public class DiscoveryNew {

	@Id
	@Column(name = "DN_ID", nullable = false)
	private String dnID;
	
	@Column(name = "CREATION_DATE")
	private Timestamp dncreationDate;
	
	
	@Column(name = "LAST_MODIF_DATE")
	private Timestamp dnlastmodifDate;
	
	@Column(name = "TOTAL_AMOUNT")
	private float dnTotalAmount;
	
	@Column(name = "STATUS")
	private String dnStatus;

	
	@Column(name = "TOTAL_QTY")
	private float dnTotalQty;
	
	public DiscoveryNew() {	
	}
	
	public DiscoveryNew(String dnID, Timestamp dncreationDate , Timestamp dnlastmodifDate, float dnTotalAmount, String dnStatus, float dnTotalQty) {
		super();
		this.dnID = dnID;
		this.dncreationDate = dncreationDate;
		this.dnlastmodifDate = dnlastmodifDate;
		this.dnTotalAmount = dnTotalAmount;
		this.dnStatus = dnStatus;
		this.dnTotalQty = dnTotalQty;
		}

	public String getDnID() {
		return dnID;
	}

	public void setDnID(String dnID) {
		this.dnID = dnID;
	}

	public Timestamp getDncreationDate() {
		return dncreationDate;
	}

	public void setDncreationDate(Timestamp dncreationDate) {
		this.dncreationDate = dncreationDate;
	}

	public Timestamp getdnlastmodifDate() {
		return dnlastmodifDate;
	}

	public void setdnlastmodifDate(Timestamp dnlastmodifDate) {
		this.dnlastmodifDate = dnlastmodifDate;
	}

	public float getDnTotalAmount() {
		return dnTotalAmount;
	}

	public void setDnTotalAmount(float dnTotalAmount) {
		this.dnTotalAmount = dnTotalAmount;
	}

	public String getDnStatus() {
		return dnStatus;
	}

	public void setDnStatus(String dnStatus) {
		this.dnStatus = dnStatus;
	}

	public float getDnTotalQty() {
		return dnTotalQty;
	}

	public void setDnTotalQty(float dnTotalQty) {
		this.dnTotalQty = dnTotalQty;
	}
	
	
	
}
