package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISCOVERY_NEW_ITEM")
public class DiscoveryNewItem {
	
	@Id
	@Column(name = "DNI_ID", nullable = false)
	private String dniID;
	
	@Column(name = "CREATION_DATE")
	private Timestamp dnicreationDate;
		
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp dnilastModifieddate;
	
	@Column(name = "ITEM_CODE")
	private String dniItemcode;
	
	@Column(name = "ITEM_NAME")
	private String dniItemname;
	
	@Column(name = "RATE")
	private float dniRate;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private float dniDiscamount;
	
	@Column(name = "DISCOUNT_PERCENT")
	private float dniDiscpercent;
	
	@Column(name = "NET_RATE")
	private float dniNetrate;
	
	@Column(name = "TAX1")
	private float dniTax1;
	
	@Column(name = "TAX2")
	private float dniTax2;
	
	@Column(name = "TOTAL")
	private float dniTotal;
	
	@Column(name = "TOTAL_AT")
	private float dniTotalat;
	
	@Column(name = "FROM_SITE")
	private String dniSIte;
	
	@Column(name = "QTY")
	private float dniQty;
	
	@Column(name = "DN_ID")
	private String dniDNID;
	
	@Column(name = "FROM_SERIAL_NUMBER")
	private String dniSN;
	
	@Column(name = "PO_ID")
	private String dniPOID;
	
	@Column(name = "WO_ID")
	private String dniWOID;
	
	@Column(name = "WO_PURPOSE")
	private String purpose;
	
	@Column(name = "TRANS_TYPE")
	private String transType ;
	
	@Column(name = "APPROVAL")
	private String dniAPPROVAL;
	
	@Column(name = "SUPPLIER_ID")
	private String supplierID;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name = "TOTAL_AMOUNT")
	private float totalAmount;
	
	@Column(name = "WARE_ID")
	private String wareID;
	
	@Column(name = "WARE_NAME")
	private String wareName;
	
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
	
	@Column(name = "NOTES")
	private String notes ;
	
	@Column(name = "TRANS_ID")
	private String transID ;
	
	@Column(name = "TRANS_SOURCE")
	private String transSrc ;
	
	@Column(name = "AR_ID")
	private String arID ;
	
	@Column(name = "FAR_ID")
	private String farID ;
	
	@Column(name = "POSITION")
	private String position ;
	
	@Column(name = "ELEMENT_NAME")
	private String elementName ;
	
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

	@Column(name = "AR_SITE_ID", nullable = false)
	private String arsiteId;

	@Column(name = "FR_SITE_ID", nullable = false)
	private String FarsiteId;
	
	@Column(name = "OLD_MAC", nullable = false)
	private String oldMacAddress;
	
	@Column(name = "MAC_ADDRESS", nullable = false)
	private String macAddress;

	public DiscoveryNewItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiscoveryNewItem(String dniID, Timestamp dnicreationDate, Timestamp dnilastModifieddate, String dniItemcode,
			String dniItemname, float dniRate, float dniDiscamount, float dniDiscpercent, float dniNetrate,
			float dniTax1, float dniTax2, float dniTotal, float dniTotalat, String dniSIte, float dniQty,
			String dniDNID, String dniSN, String dniPOID, String dniWOID, String purpose, String transType,
			String dniAPPROVAL, String supplierID, String supplierName, float totalAmount, String wareID,
			String wareName, String itemPartNb, String itemModel, String approvalStatus, String nodeID, String nodeName,
			String notes, String transID, String transSrc, String arID, String farID, String position,
			String elementName, String fromSlot, String toSlot, String toNodeId, String toSite, String toSerialNumber,
			String description, String toNodeName, String nodePK, String alcFlg, String toWareName, String toWareId,
			String arsiteId, String farsiteId, String oldMacAddress, String macAddress) {
		super();
		this.dniID = dniID;
		this.dnicreationDate = dnicreationDate;
		this.dnilastModifieddate = dnilastModifieddate;
		this.dniItemcode = dniItemcode;
		this.dniItemname = dniItemname;
		this.dniRate = dniRate;
		this.dniDiscamount = dniDiscamount;
		this.dniDiscpercent = dniDiscpercent;
		this.dniNetrate = dniNetrate;
		this.dniTax1 = dniTax1;
		this.dniTax2 = dniTax2;
		this.dniTotal = dniTotal;
		this.dniTotalat = dniTotalat;
		this.dniSIte = dniSIte;
		this.dniQty = dniQty;
		this.dniDNID = dniDNID;
		this.dniSN = dniSN;
		this.dniPOID = dniPOID;
		this.dniWOID = dniWOID;
		this.purpose = purpose;
		this.transType = transType;
		this.dniAPPROVAL = dniAPPROVAL;
		this.supplierID = supplierID;
		this.supplierName = supplierName;
		this.totalAmount = totalAmount;
		this.wareID = wareID;
		this.wareName = wareName;
		this.itemPartNb = itemPartNb;
		this.itemModel = itemModel;
		this.approvalStatus = approvalStatus;
		this.nodeID = nodeID;
		this.nodeName = nodeName;
		this.notes = notes;
		this.transID = transID;
		this.transSrc = transSrc;
		this.arID = arID;
		this.farID = farID;
		this.position = position;
		this.elementName = elementName;
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
		this.arsiteId = arsiteId;
		FarsiteId = farsiteId;
		this.oldMacAddress = oldMacAddress;
		this.macAddress = macAddress;
	}

	public String getDniID() {
		return dniID;
	}

	public void setDniID(String dniID) {
		this.dniID = dniID;
	}

	public Timestamp getDnicreationDate() {
		return dnicreationDate;
	}

	public void setDnicreationDate(Timestamp dnicreationDate) {
		this.dnicreationDate = dnicreationDate;
	}

	public Timestamp getDnilastModifieddate() {
		return dnilastModifieddate;
	}

	public void setDnilastModifieddate(Timestamp dnilastModifieddate) {
		this.dnilastModifieddate = dnilastModifieddate;
	}

	public String getDniItemcode() {
		return dniItemcode;
	}

	public void setDniItemcode(String dniItemcode) {
		this.dniItemcode = dniItemcode;
	}

	public String getDniItemname() {
		return dniItemname;
	}

	public void setDniItemname(String dniItemname) {
		this.dniItemname = dniItemname;
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

	public float getDniDiscpercent() {
		return dniDiscpercent;
	}

	public void setDniDiscpercent(float dniDiscpercent) {
		this.dniDiscpercent = dniDiscpercent;
	}

	public float getDniNetrate() {
		return dniNetrate;
	}

	public void setDniNetrate(float dniNetrate) {
		this.dniNetrate = dniNetrate;
	}

	public float getDniTax1() {
		return dniTax1;
	}

	public void setDniTax1(float dniTax1) {
		this.dniTax1 = dniTax1;
	}

	public float getDniTax2() {
		return dniTax2;
	}

	public void setDniTax2(float dniTax2) {
		this.dniTax2 = dniTax2;
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

	public float getDniQty() {
		return dniQty;
	}

	public void setDniQty(float dniQty) {
		this.dniQty = dniQty;
	}

	public String getDniDNID() {
		return dniDNID;
	}

	public void setDniDNID(String dniDNID) {
		this.dniDNID = dniDNID;
	}

	public String getDniSN() {
		return dniSN;
	}

	public void setDniSN(String dniSN) {
		this.dniSN = dniSN;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTransID() {
		return transID;
	}

	public void setTransID(String transID) {
		this.transID = transID;
	}

	public String getTransSrc() {
		return transSrc;
	}

	public void setTransSrc(String transSrc) {
		this.transSrc = transSrc;
	}

	public String getArID() {
		return arID;
	}

	public void setArID(String arID) {
		this.arID = arID;
	}

	public String getFarID() {
		return farID;
	}

	public void setFarID(String farID) {
		this.farID = farID;
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

	public String getFromSlot() {
		return fromSlot;
	}

	public void setFromSlot(String fromSlot) {
		this.fromSlot = fromSlot;
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

	public void setToNodeId(String toNodeId) {
		this.toNodeId = toNodeId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getToNodeName() {
		return toNodeName;
	}

	public void setToNodeName(String toNodeName) {
		this.toNodeName = toNodeName;
	}

	public String getNodePK() {
		return nodePK;
	}

	public void setNodePK(String nodePK) {
		this.nodePK = nodePK;
	}

	public String getAlcFlg() {
		return alcFlg;
	}

	public void setAlcFlg(String alcFlg) {
		this.alcFlg = alcFlg;
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

	public String getArsiteId() {
		return arsiteId;
	}

	public void setArsiteId(String arsiteId) {
		this.arsiteId = arsiteId;
	}

	public String getFarsiteId() {
		return FarsiteId;
	}

	public void setFarsiteId(String farsiteId) {
		FarsiteId = farsiteId;
	}

	public String getOldMacAddress() {
		return oldMacAddress;
	}

	public void setOldMacAddress(String oldMacAddress) {
		this.oldMacAddress = oldMacAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	
	
	
}
