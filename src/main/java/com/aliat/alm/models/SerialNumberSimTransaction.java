package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SERIAL_NUMBER_SIM_TRANSACTION")

public class SerialNumberSimTransaction {
	
	@Id
	@Column(name = "ID", nullable = false)
	private String ID;
	
	@Column(name = "SIM_ID")
	private String simID;
	
	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;
		
	@Column(name = "MSISDN")
	private String MSISDN;
	
	@Column(name = "SIM_TRANSACTION_ITEM_ID")
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
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifieddate;
	
	
	
	public SerialNumberSimTransaction() {	
	}
	public SerialNumberSimTransaction(String ID, String simID, String serialNumber, Timestamp creationDate,  Timestamp lastModifieddate, String simTransactionItemID, String MSISDN, 
		String sourceType, String source, String sourceID, String sourceMSISDN, String destinationType, String destination, String destinationID, String transactionID	) {
		super();
		this.ID=ID;
		this.simID = simID;
		this.serialNumber = serialNumber;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.simTransactionItemID = simTransactionItemID;
		this.MSISDN = MSISDN;
		this.sourceType = sourceType ;
		this.source = source;
		this.sourceID = sourceID;
		this.sourceMSISDN = sourceMSISDN;
		this.destinationType = destinationType;
		this.destination = destination;
		this.destinationID = destinationID;
		this.transactionID = transactionID;
		
	}
	public String getID() {
		return ID;
	}
	public void setID(String ID) {
		this.ID = ID;
	}
	public String getSimID() {
		return simID;
	}
	public void setSimID(String simID) {
		this.simID = simID;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSimTransactionItemID() {
		return simTransactionItemID;
	}
	public void setSimTransactionItemID(String simTransactionItemID) {
		this.simTransactionItemID = simTransactionItemID;
	}
	public String getMSISDN() {
		return MSISDN;
	}
	public void setMSISDN(String MSISDN) {
		this.MSISDN = MSISDN;
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
	public String getSourceID() {
		return sourceID;
	}
	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
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
	public String getDestinationID() {
		return destinationID;
	}
	public void setDestinationID(String destinationID) {
		this.destinationID = destinationID;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
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
