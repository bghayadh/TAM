package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISCOVERY_NEW_BOQ")

public class DiscoveryNewBoq {
	
	@Id
	@Column(name = "DNI_ID", nullable = false)
	private String discItemID;
	
	@Column(name = "ITEM_CODE")
	private String discItemCode;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "TRANS_TYPE")
	private String transType ;
	
	@Column(name = "APPROVAL")
	private String dniAPPROVAL;
	
	@Column(name = "PO_ID")
	private String dniPOID;
	
	
	@Column(name = "WO_ID")
	private String dniWOID;
	
	@Column(name = "WO_PURPOSE")
	private String purpose;

	
	@Column(name = "SUPPLIER_ID")
	private String supplierID;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name = "TOTAL_AMOUNT")
	private float totalAmount;
	
	@Column(name = "QTY")
	private float dniQty;
	
	@Column(name = "RATE")
	private float dniRate;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private float dniDiscamount;
	
	@Column(name = "TAX1")
	private float dniTax1;
	
	@Column(name = "NET_RATE")
	private float dniNetrate;
	
	@Column(name = "TOTAL")
	private float dniTotal;
	
	@Column(name = "TOTAL_AT")
	private float dniTotalat;
	
	@Column(name = "FROM_SITE")
	private String dniSIte;
	
	@Column(name = "WARE_ID")
	private String wareID;
	
	@Column(name = "WARE_NAME")
	private String wareName;
	
	@Column(name = "FROM_SERIAL_NUMBER")
	private String dniSN;
	
	@Column(name = "DN_ID")
	private String dniDNID;
	
	@Column(name = "ITEM_PART_NUMBER")
	private String itemPartNb;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;
	
	@Column(name = "APPROVAL_STATUS")
	private String approvalStatus;
	
	@Column(name = "FROM_NODE_ID")
	private String nodeID;
	
	@Column(name = "FROM_NODE_NAME")
	private String nodeName;
	
	@Column(name = "POSITION")
	private String position ;
	
	@Column(name = "ELEMENT_NAME")
	private String elementName ;
	
	@Column(name = "NOTES")
	private String notes ;

	@Column(name = "FROM_SLOT")
	private String fromSlot ;

	@Column(name = "TO_SLOT")
	private String toSlot ;
	
	@Column(name = "TO_NODE_ID")
	private String toNodeId ;
	
	@Column(name = "TO_SITE")
	private String toSite ;
	
	@Column(name = "TO_SERIAL_NUMBER")
	private String toSerialNumber ;
	
	@Column(name = "DESCRIPTION")
	private String description ;
	
	@Column(name = "TO_NODE_NAME")
	private String toNodeName ;
	
	@Column(name = "NODE_PK")
	private String nodePK ;
	
	@Column(name = "ALCFLG")
	private String alcFlg ;
	
	@Column(name = "TO_WARE_NAME")
	private String toWareName ;
	
	@Column(name = "TO_WARE_ID")
	private String toWareId ;
	
	private String toNodeArray;
	
	private String fromNodeArray;
	
	public DiscoveryNewBoq() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DiscoveryNewBoq(String discItemID, String discItemCode, String itemName, String transType,
			String dniAPPROVAL, String dniPOID, String dniWOID, String purpose, String supplierID, String supplierName,
			float totalAmount, float dniQty, float dniRate, float dniDiscamount, float dniTax1, float dniNetrate,
			float dniTotal, float dniTotalat, String dniSIte, String wareID, String wareName, String dniSN,
			String dniDNID, String itemPartNb, String itemModel, String approvalStatus, String nodeID, String nodeName,
			String position, String elementName, String notes, String fromSlot, String toSlot, String toNodeId, 
			String toSite, String toSerialNumber, String description, String toNodeName, String nodePK, String alcFlg, String toWareName, String toWareId, String toNodeArray,
			String fromNodeArray) {
		super();
		this.discItemID = discItemID;
		this.discItemCode = discItemCode;
		this.itemName = itemName;
		this.transType = transType;
		this.dniAPPROVAL = dniAPPROVAL;
		this.dniPOID = dniPOID;
		this.dniWOID = dniWOID;
		this.purpose = purpose;
		this.supplierID = supplierID;
		this.supplierName = supplierName;
		this.totalAmount = totalAmount;
		this.dniQty = dniQty;
		this.dniRate = dniRate;
		this.dniDiscamount = dniDiscamount;
		this.dniTax1 = dniTax1;
		this.dniNetrate = dniNetrate;
		this.dniTotal = dniTotal;
		this.dniTotalat = dniTotalat;
		this.dniSIte = dniSIte;
		this.wareID = wareID;
		this.wareName = wareName;
		this.dniSN = dniSN;
		this.dniDNID = dniDNID;
		this.itemPartNb = itemPartNb;
		this.itemModel = itemModel;
		this.approvalStatus = approvalStatus;
		this.nodeID = nodeID;
		this.nodeName = nodeName;
		this.position = position;
		this.elementName = elementName;
		this.notes = notes;
		this.fromSlot = fromSlot;
		this.toSlot = toSlot;
		this.toNodeId = toNodeId;
		this.toSite = toSite;
		this.toSerialNumber = toSerialNumber;
		this.description = description;
		this.toNodeName = toNodeName;
		this.nodePK = nodePK;
		this.alcFlg = alcFlg;
		this.toWareName = toWareName;
		this.toWareId = toWareId;
		this.toNodeArray=toNodeArray;
		this.fromNodeArray=fromNodeArray;
	
	}


	public String getFromNodeArray() {
		return fromNodeArray;
	}


	public void setFromNodeArray(String fromNodeArray) {
		this.fromNodeArray = fromNodeArray;
	}


	public String getToNodeArray() {
		return toNodeArray;
	}


	public void setToNodeArray(String toNodeArray) {
		this.toNodeArray = toNodeArray;
	}


	public String getToWareName() {
		return toWareName;
	}



	public void setToWareName(String toWareName) {
		this.toWareName = toWareName;
	}



	public String getToWareId() {
		return toWareId;
	}



	public void setToWareId(String toWareId) {
		this.toWareId = toWareId;
	}
	
	
	public String getNodePK() {
		return nodePK;
	}


	public void setNodePK(String nodePK) {
		this.nodePK = nodePK;
	}


	public String getToNodeName() {
		return toNodeName;
	}


	public void setToNodeName(String toNodeName) {
		this.toNodeName = toNodeName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getToSlot() {
		return toSlot;
	}


	public void setToSlot(String toSlot) {
		this.toSlot = toSlot;
	}


	public String getToNodeId() {
		return toNodeId;
	}


	public void setToNodeId(String toNodeID) {
		this.toNodeId = toNodeID;
	}


	public String getToSite() {
		return toSite;
	}


	public void setToSite(String toSite) {
		this.toSite = toSite;
	}


	public String getToSerialNumber() {
		return toSerialNumber;
	}


	public void setToSerialNumber(String toSerialNumber) {
		this.toSerialNumber = toSerialNumber;
	}


	public String getFromSlot() {
		return fromSlot;
	}


	public void setFromSlot(String fromSlot) {
		this.fromSlot = fromSlot;
	}


	public String getDiscItemID() {
		return discItemID;
	}


	public void setDiscItemID(String discItemID) {
		this.discItemID = discItemID;
	}


	public String getDiscItemCode() {
		return discItemCode;
	}


	public void setDiscItemCode(String discItemCode) {
		this.discItemCode = discItemCode;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getTransType() {
		return transType;
	}


	public void setTransType(String transType) {
		this.transType = transType;
	}


	public String getDniAPPROVAL() {
		return dniAPPROVAL;
	}


	public void setDniAPPROVAL(String dniAPPROVAL) {
		this.dniAPPROVAL = dniAPPROVAL;
	}


	public String getDniPOID() {
		return dniPOID;
	}


	public void setDniPOID(String dniPOID) {
		this.dniPOID = dniPOID;
	}


	public String getDniWOID() {
		return dniWOID;
	}


	public void setDniWOID(String dniWOID) {
		this.dniWOID = dniWOID;
	}


	public String getPurpose() {
		return purpose;
	}


	public void setPurpose(String purpose) {
		this.purpose = purpose;
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


	public float getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}


	public float getDniQty() {
		return dniQty;
	}


	public void setDniQty(float dniQty) {
		this.dniQty = dniQty;
	}


	public float getDniRate() {
		return dniRate;
	}


	public void setDniRate(float dniRate) {
		this.dniRate = dniRate;
	}


	public float getDniDiscamount() {
		return dniDiscamount;
	}


	public void setDniDiscamount(float dniDiscamount) {
		this.dniDiscamount = dniDiscamount;
	}


	public float getDniTax1() {
		return dniTax1;
	}


	public void setDniTax1(float dniTax1) {
		this.dniTax1 = dniTax1;
	}


	public float getDniNetrate() {
		return dniNetrate;
	}


	public void setDniNetrate(float dniNetrate) {
		this.dniNetrate = dniNetrate;
	}


	public float getDniTotal() {
		return dniTotal;
	}


	public void setDniTotal(float dniTotal) {
		this.dniTotal = dniTotal;
	}


	public float getDniTotalat() {
		return dniTotalat;
	}


	public void setDniTotalat(float dniTotalat) {
		this.dniTotalat = dniTotalat;
	}


	public String getDniSIte() {
		return dniSIte;
	}


	public void setDniSIte(String dniSIte) {
		this.dniSIte = dniSIte;
	}


	public String getWareID() {
		return wareID;
	}


	public void setWareID(String wareID) {
		this.wareID = wareID;
	}


	public String getWareName() {
		return wareName;
	}


	public void setWareName(String wareName) {
		this.wareName = wareName;
	}


	public String getDniSN() {
		return dniSN;
	}


	public void setDniSN(String dniSN) {
		this.dniSN = dniSN;
	}


	public String getDniDNID() {
		return dniDNID;
	}


	public void setDniDNID(String dniDNID) {
		this.dniDNID = dniDNID;
	}


	public String getItemPartNb() {
		return itemPartNb;
	}


	public void setItemPartNb(String itemPartNb) {
		this.itemPartNb = itemPartNb;
	}


	public String getItemModel() {
		return itemModel;
	}


	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}


	public String getApprovalStatus() {
		return approvalStatus;
	}


	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}


	public String getNodeID() {
		return nodeID;
	}


	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}


	public String getNodeName() {
		return nodeName;
	}


	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getElementName() {
		return elementName;
	}


	public void setElementName(String elementName) {
		this.elementName = elementName;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getAlcFlg() {
		return alcFlg;
	}


	public void setAlcFlg(String alcFlg) {
		this.alcFlg = alcFlg;
	}


	
}
