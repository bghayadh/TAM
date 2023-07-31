package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SIM_TRANSACTION_ITEM")
public class SimTransactionItem {

	@Id
	@Column(name = "SIM_TRANSACTION_ITEM_ID" , nullable = false)
	private String simTransactionItemID;
	
	@Column(name = "SOURCE_TYPE")
	private String sourceType;

	@Column(name = "SOURCE")
	private String source;
	
	@Column(name = "SOURCE_ID")
	private String sourceID;
	
	@Column(name = "SOURCE_MSISDN")
	private String sourceMSISDN;
	
	@Column(name = "DESTINATION_TYPE")
	private String destinationType;
	
	@Column(name = "DESTINATION")
	private String destination;
	
	@Column(name = "DESTINATION_ID")
	private String destinationID;
	
	@Column(name = "TRANSACTION_ID")
	private String transactionID;
	
	@Column(name = "TOTAL_QTY")
	private int totalQty;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifieddate;


	public SimTransactionItem() {
	}

	public SimTransactionItem(String simTransactionItemID,String serMSI,
			String sourceType,String source,String sourceID,String sourceMSISDN,String destinationType,
			String destination,String destinationID,String transactionID,int totalQty, Timestamp creationDate, Timestamp lastModifieddate ) {
		super();
		this.simTransactionItemID = simTransactionItemID;
		this.sourceType = sourceType;
		this.source = source;
		this.sourceID=sourceID;
		this.sourceMSISDN = sourceMSISDN;
		this.destinationType = destinationType;
		this.destination = destination;
		this.destinationID=destinationID;
		this.transactionID = transactionID;
		this.totalQty = totalQty;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		
	}


	public String getSimTransactionItemID() {
		return simTransactionItemID;
	}

	public void setSimTransactionItemID(String simTransactionItemID) {
		this.simTransactionItemID = simTransactionItemID;
	}


	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceMSISDN() {
		return sourceMSISDN;
	}

	public void setSourceMSISDN(String sourceMSISDN) {
		this.sourceMSISDN = sourceMSISDN;
	}

	public String getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	
	public int getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
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

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public String getDestinationID() {
		return destinationID;
	}

	public void setDestinationID(String destinationID) {
		this.destinationID = destinationID;
	}

	
}
