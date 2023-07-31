package com.aliat.alm.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;





@Entity
@Table(name = "WORK_ORDER_DEST_BOQ")
public class WorkOrderDestinationBoq {

	
	
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
	
	
	
	
	
	@Column(name = "NODE_ID")
	private String nodeId;
	
	@Column(name = "NODE_NAME")
	private String nodeName;
	
	@Column(name = "PR_ID")
	private String pr;
	
	@Column(name = "PO_ID")
	private String po;
	
	@Column(name = "CIP_ID")
	private String cip;
	
	@Column(name = "DN_ID")
	private String dn;
	
	@Column(name = "AR_ID")
	private String ar;
	
	@Column(name = "FAR_ID")
	private String far;
	
	@Column(name = "Status")
	private String status;
	
	
	@Column(name = "RECONCILED")
	private char reconciled;
	
	@Column(name = "BARCODE_SCANNED")
	private char barcodescanned;
	
	
	
	
	@Id
	@Column(name = "ID", nullable = false)
	private String id; 
	
	public  WorkOrderDestinationBoq() {
		
		
	}

	public WorkOrderDestinationBoq(String itemCode, String itemName, String itemModel, String itemPartNumber,
			float currentQty, float qty, String nodeId, String nodeName, String pr,
			String po, String cip, String dn, String ar, String far, String status, char reconciled,
			char barcodescanned, String id) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemModel = itemModel;
		this.itemPartNumber = itemPartNumber;
		this.currentQty = currentQty;
		this.qty = qty;
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.pr = pr;
		this.po = po;
		this.cip = cip;
		this.dn = dn;
		this.ar = ar;
		this.far = far;
		this.status = status;
		this.reconciled = reconciled;
		this.barcodescanned = barcodescanned;
		this.id = id;
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

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public String getPo() {
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getAr() {
		return ar;
	}

	public void setAr(String ar) {
		this.ar = ar;
	}

	public String getFar() {
		return far;
	}

	public void setFar(String far) {
		this.far = far;
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

	public char getBarcodescanned() {
		return barcodescanned;
	}

	public void setBarcodescanned(char barcodescanned) {
		this.barcodescanned = barcodescanned;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	
	
	
	
	
		
}