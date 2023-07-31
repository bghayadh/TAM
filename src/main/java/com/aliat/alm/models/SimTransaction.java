package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SIM_TRANSACTION")
public class SimTransaction {

	@Id
	@Column(name = "TRANSACTION_ID" , nullable = false)
	private String transactionID;
	
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;
	
	@Column(name = "TOTAL_QTY")
	private int totalQty;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifieddate;


	public SimTransaction() {
	}

	public SimTransaction(String transactionID,String transactionType,int totalQty,String status, Timestamp creationDate, Timestamp lastModifieddate ) {
		super();
		this.transactionID = transactionID;
		this.transactionType = transactionType;
		this.totalQty = totalQty;
		this.status = status;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;


	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public int getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastModifieddate() {
		return lastModifieddate;
	}

	public void setLastModifieddate(Timestamp lastModifieddate) {
		this.lastModifieddate = lastModifieddate;
	}


	
}
