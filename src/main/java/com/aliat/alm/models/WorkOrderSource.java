package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



@Entity
@Table(name = "WORK_ORDER_BOQ_SOURCE")
public class WorkOrderSource {
	

	@Id
	@Column(name = "ID", nullable = false)
	private String Id;
	
	
	@Column(name = "CREATION_DATE")
	private Timestamp CreationDate;
		
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp LastModifieddate;
	
	
	@Column(name = "ITEM_CODE")
	private String itemCode;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "ITEM_MODEL")
	private String itemModel;
	
	@Column(name = "ITEM_PART_NUMBER")
	private String itemPartNumber;
	
	
	@Column(name = "CURRENT_QTY")
	private float currentQty;
	

	@Column(name = "QTY")
	private float qty;
	
	@Column(name = "FROM_WAREHOUSE")
	private String fromWare;
	
	@Column(name = "TO_WAREHOUSE")
	private String ToWare;
	
	@Column(name = "WO_ID")
	private String workOrdId;
	
	@Column(name = "NODE_ID")
	private String nodeId;
	
	@Column(name = "NODE_NAME")
	private String nodeName;
	
	@Column(name = "GR_ID")
	private String grId;
	
	@Column(name = "PR_ID")
	private String prId;
	
	@Column(name = "PO_ID")
	private String poId;
	
	@Column(name = "CIP_ID")
	private String cipId;
	
	
	@Column(name = "STATUS")
	private String status;
	
	
	@Column(name = "RECONCILED")
	private char reconciled;
	
	
	@Column(name = "BARCODE_SCANNED")
	private char barcodeScanned;

	@Column(name = "SERIAL_NO")
	private String serialNo;
	
	
	public  WorkOrderSource() {
		
	}

	public WorkOrderSource(String id, Timestamp creationDate, Timestamp lastModifieddate, String itemCode,
			String itemName, String itemModel, String itemPartNumber, String fromWare, String toWare, float currentQty,
			float qty, String workOrdId, String nodeId, String nodeName, String grId, String prId, String poId, String cipId,
			 String status, char reconciled, char barcodeScanned, String serialNo) {
		super();
		Id = id;
		CreationDate = creationDate;
		LastModifieddate = lastModifieddate;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemModel = itemModel;
		this.itemPartNumber = itemPartNumber;
		this.fromWare = fromWare;
		this.ToWare= toWare;
		this.currentQty = currentQty;
		this.qty = qty;
		this.workOrdId = workOrdId;
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.grId = grId;
		this.prId = prId;
		this.poId = poId;
		this.status = status;
		this.reconciled = reconciled;
		this.barcodeScanned = barcodeScanned;
		this.serialNo =serialNo;
	}



	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Timestamp getCreationDate() {
		return CreationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		CreationDate = creationDate;
	}

	public Timestamp getLastModifieddate() {
		return LastModifieddate;
	}

	public void setLastModifieddate(Timestamp lastModifieddate) {
		LastModifieddate = lastModifieddate;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemModel() {
		return itemModel;
	}

	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}

	public String getItemPartNumber() {
		return itemPartNumber;
	}

	public void setItemPartNumber(String itemPartNumber) {
		this.itemPartNumber = itemPartNumber;
	}

	public float getCurrentQty() {
		return currentQty;
	}

	public void setCurrentQty(float currentQty) {
		this.currentQty = currentQty;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public String getFromWare() {
		return fromWare;
	}

	public void setFromWare(String fromWare) {
		this.fromWare = fromWare;
	}

	public String getToWare() {
		return ToWare;
	}

	public void setToWare(String toWare) {
		ToWare = toWare;
	}

	public String getWorkOrdId() {
		return workOrdId;
	}

	public void setWorkOrdId(String workOrdId) {
		this.workOrdId = workOrdId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getGrId() {
		return grId;
	}

	public void setGrId(String grId) {
		this.grId = grId;
	}

	public String getPrId() {
		return prId;
	}

	public void setPrId(String prId) {
		this.prId = prId;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public String getCipId() {
		return cipId;
	}

	public void setCipId(String cipId) {
		this.cipId = cipId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public char getReconciled() {
		return reconciled;
	}

	public void setReconciled(char reconciled) {
		this.reconciled = reconciled;
	}

	public char getBarcodeScanned() {
		return barcodeScanned;
	}

	public void setBarcodeScanned(char barcodeScanned) {
		this.barcodeScanned = barcodeScanned;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}


	@Override
	public String toString () {
	  return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
	

	
	
}
