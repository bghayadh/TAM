package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER_SERVICE")
public class CustomerService {

	@Id
	@Column(name = "CUST_SERV_ID", nullable = false)
	private String id;

	@Column(name = "SERV_TYPE")
	private String serviceType;
	
	@Column(name = "BILLING_CODE")
	private String billingCode;
	
	@Column(name = "CIRCUIT_NO")
	private String circuitNo;
	
	@Column(name = "LONGITUDE")
	private String longitude;
	
	@Column(name = "LATITUDE")
	private String latitude;
	
	@Column(name = "CUSTOMER_ID")
	private String customerID;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModfDate;

	@Column(name = "CATEGORY")
	private String cat;
	
	@Column(name = "CIRCUIT_ID")
	private String circuitID;

	@Column(name = "REF_ID")
	private String refID;

	@Column(name = "NUM")
	private String num;

	@Column(name = "MEDIA_TYPE")
	private String mediaType;

	@Column(name = "TX_MEDIA")
	private String txMedia;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CAPACITY_MB")
	private String capacityMB;

	@Column(name = "REGION_ID")
	private String regionID;

	@Column(name = "LINK_NAME")
	private String linkName;

	@Column(name = "RADIO_ID")
	private String radioID;

	@Column(name = "RADIO_NAME")
	private String radioName;

	@Column(name = "NE_PORT_DESC")
	private String portDesc;

	@Column(name = "Customer_Name")
	private String customerName;
	
	public CustomerService() {
		super();
	}

	public CustomerService(String id,String serviceType,String billingCode,String circuitNo,String longitude,String latitude, String customerID, Timestamp createdDate,Timestamp lastModfDate, String cat, 
			String circuitID, String refID, 
			String num, String mediaType, String txMedia, String status, String capacityMB,String regionID,String linkName,String radioID,String radioName,String portDesc, String customerName) {
		super();
		this.id = id;
		this.serviceType = serviceType;
		this.billingCode = billingCode;
		this.circuitNo = circuitNo;
		this.longitude=longitude;
		this.latitude=latitude;
		this.customerName=customerName;
		this.customerID = customerID;
		this.createdDate = createdDate;
		this.lastModfDate = lastModfDate;
		this.cat = cat;
		this.circuitID = circuitID;
		this.refID = refID;
		this.num = num;
		this.mediaType = mediaType;
		this.txMedia = txMedia;	
		this.status = status;
		this.capacityMB = capacityMB;
		this.regionID = regionID;
		this.linkName = linkName;
		this.radioID = radioID;
		this.radioName = radioName;
		this.portDesc = portDesc;
		
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLastModfDate() {
		return lastModfDate;
	}

	public void setLastModfDate(Timestamp lastModfDate) {
		this.lastModfDate = lastModfDate;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getBillingCode() {
		return billingCode;
	}

	public void setBillingCode(String billingCode) {
		this.billingCode = billingCode;
	}

	public String getCircuitNo() {
		return circuitNo;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setCircuitNo(String circuitNo) {
		this.circuitNo = circuitNo;
	}

	public String getCircuitID() {
		return circuitID;
	}

	public void setCircuitID(String circuitID) {
		this.circuitID = circuitID;
	}

	public String getRefID() {
		return refID;
	}

	public void setRefID(String refID) {
		this.refID = refID;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getTxMedia() {
		return txMedia;
	}

	public void setTxMedia(String txMedia) {
		this.txMedia = txMedia;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCapacityMB() {
		return capacityMB;
	}

	public void setCapacityMB(String capacityMB) {
		this.capacityMB = capacityMB;
	}

	public String getRegionID() {
		return regionID;
	}

	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getRadioID() {
		return radioID;
	}

	public void setRadioID(String radioID) {
		this.radioID = radioID;
	}

	public String getRadioName() {
		return radioName;
	}

	public void setRadioName(String radioName) {
		this.radioName = radioName;
	}

	public String getPortDesc() {
		return portDesc;
	}

	public void setPortDesc(String portDesc) {
		this.portDesc = portDesc;
	}


}
