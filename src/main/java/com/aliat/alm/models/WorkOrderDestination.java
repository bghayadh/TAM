package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
  
	 
@Entity
@Table(name = "WORK_ORDER_BOQ_DESTINATION")
public class WorkOrderDestination {


	@Id
	@Column(name = "ID", nullable = false)
	private String Id;
	
	
	@Column(name = "CREATION_DATE")
	private Timestamp wodCreationDate;
		
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp wodLastModifieddate;
		
	@Column(name = "ITEM_CODE")
	private String itemCode;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "FROM_WAREHOUSE")
	private String fromWare;
		
	@Column(name = "TO_WAREHOUSE")
	private String toWare;
		
	@Column(name = "CURRENT_QTY")
	private float currentQty;
	
	@Column(name = "QTY")
	private float qty;
	
	@Column(name = "WO_ID" )
	private String workOrdId;
		
	@Column(name = "WO_ORD_SRC_ID")
	private String workOrdSrcId;
		
	@Column(name = "NODE_ID")
	private String nodeId;
	
	@Column(name = "NODE_NAME")
	private String nodeName;
	
	@Column(name ="ITEM_MODEL_DEST")
	private String itemModel;
	
	@Column(name = "ITEM_PART_NUMBER")
	private String itemPartNumber;
	
	@Column(name = "GR_ID")
	private String grId;
	
	@Column(name = "PR_ID")
	private String prId;
	
	@Column(name = "PO_ID")
	private String poId;
	
	@Column(name = "CIP_ID")
	private String cipId;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "RECONCILED")
	private char reconciled;
	
	@Column(name = "BARCODE_SCANNED")
	private char barcodeScanned;

	@Column(name = "SERIAL_NO")
	private String serialNo;
	


	public  WorkOrderDestination()	{
	}


	public WorkOrderDestination(String id, Timestamp wodCreationDate, Timestamp wodLastModifieddate, String itemCode,
			String itemName, String fromWare, String toWare, float currentQty, float qty, String workOrdId,
			String workOrdSrcId, String nodeId, String nodeName, String itemModel, String itemPartNumber, String grId, String prId,
			String poId, String cipId, String status, char reconciled,
			char barcodeScanned, String serialNo) {
		super();
		Id = id;
		this.wodCreationDate = wodCreationDate;
		this.wodLastModifieddate = wodLastModifieddate;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.fromWare = fromWare;
		this.toWare = toWare;
		this.currentQty = currentQty;
		this.qty = qty;
		this.workOrdId = workOrdId;
		this.workOrdSrcId = workOrdSrcId;
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.itemModel = itemModel;
		this.itemPartNumber = itemPartNumber;
		this.grId= grId;
		this.prId = prId;
		this.poId = poId;
		this.cipId = cipId;
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


	public Timestamp getWodCreationDate() {
		return wodCreationDate;
	}


	public void setWodCreationDate(Timestamp wodCreationDate) {
		this.wodCreationDate = wodCreationDate;
	}


	public Timestamp getWodLastModifieddate() {
		return wodLastModifieddate;
	}


	public void setWodLastModifieddate(Timestamp wodLastModifieddate) {
		this.wodLastModifieddate = wodLastModifieddate;
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


	public String getFromWare() {
		return fromWare;
	}


	public void setFromWare(String fromWare) {
		this.fromWare = fromWare;
	}


	public String getToWare() {
		return toWare;
	}


	public void setToWare(String toWare) {
		this.toWare = toWare;
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


	public String getWorkOrdId() {
		return workOrdId;
	}


	public void setWorkOrdId(String workOrdId) {
		this.workOrdId = workOrdId;
	}


	public String getWorkOrdSrcId() {
		return workOrdSrcId;
	}


	public void setWorkOrdSrcId(String workOrdSrcId) {
		this.workOrdSrcId = workOrdSrcId;
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

	