package com.aliat.alm.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VWPURCHASE_REQUEST_ITEM")


public class VWPurchaseRequestItem {
	@Id
	@Column(name = "PRQ_ID")
	private String PRQId;
	
	@Column(name = "ITEM_CODE")
	private String prItemCode;
	
	@Column(name = "ITEM_Name")
	private String prItemName;
	
	@Column(name = "ITEM_MODEL")
	private String prItemModel;
	
	@Column(name = "ITEM_PART_NUMBER")
	private String prItemPartNumber;
	
	@Column(name = "QTY")
	private float prQty;
	 
	@Column(name = "RATE")
	private float prRate;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private float prDiscountAmount;
	 
	@Column(name = "TAX1")
	private float prTax1;
	
	@Column(name = "NET_RATE")
	private float prNetRate;
	
	@Column(name = "TOTAL")
	private float prTotal;
	
	@Column(name = "TOTAL_AT")
	private float prTotalAt;
		
	@Column(name = "PO_QTY")
	private String poQty;
		
	@Column(name = "GR_QTY")
	private String grQty;
	
	@Column(name = "AR_QTY")
	private String arQty;
	
	@Column(name = "CIP_QTY")
	private String cipQty;
	
	@Column(name = "FAR_QTY")
	private String farQty;

	@Column(name = "PRQ_PO_STATUS")
	private String prqPoStatus;
	

	@Column(name = "PRQ_ITEM_ID", nullable = false)
	private String prqItemId;

	public VWPurchaseRequestItem() {	
	}

	
	public VWPurchaseRequestItem( String PRQId, String prItemCode, String prItemName, String prItemModel, String prItemPartNumber,
			float prQty, float prRate, float prDiscountAmount, float prTax1, float prNetRate, float prTotal,
			float prTotalAt, String poQty, String grQty, String arQty, String cipQty, String farQty, String prqPoStatus, String prqItemId) {
		super();
		this.PRQId = PRQId;
		this.prItemCode = prItemCode;
		this.prItemName = prItemName;
		this.prItemModel = prItemModel;
		this.prItemPartNumber = prItemPartNumber;
		this.prQty = prQty;
		this.prRate = prRate;
		this.prDiscountAmount = prDiscountAmount;
		this.prTax1 = prTax1;
		this.prNetRate = prNetRate;
		this.prTotal = prTotal;
		this.prTotalAt = prTotalAt;
		this.poQty = poQty;
		this.grQty = grQty;
		this.arQty = arQty;
		this.cipQty = cipQty;
		this.farQty = farQty;
		this.prqPoStatus = prqPoStatus;
		this.prqItemId = prqItemId;
	}

	public String getPrqiPrqId() {
		return PRQId;
	}

	public void setPrqiPrqId(String PRQId) {
		this.PRQId = PRQId;
	}
	
	public String getPrqPoStatus() {
		return prqPoStatus;
	}


	public void setPrqPoStatus(String prqPoStatus) {
		this.prqPoStatus = prqPoStatus;
	}


	


	public String getPrItemCode() {
		return prItemCode;
	}

	public void setPrItemCode(String prItemCode) {
		this.prItemCode = prItemCode;
	}

	public String getPrItemName() {
		return prItemName;
	}

	public void setPrItemName(String prItemName) {
		this.prItemName = prItemName;
	}
	
	
	

	public String getPrItemModel() {
		return prItemModel;
	}


	public void setPrItemModel(String prItemModel) {
		this.prItemModel = prItemModel;
	}


	public String getPrItemPartNumber() {
		return prItemPartNumber;
	}


	public void setPrItemPartNumber(String prItemPartNumber) {
		this.prItemPartNumber = prItemPartNumber;
	}


	public float getPrQty() {
		return prQty;
	}

	public void setPrQty(float prQty) {
		this.prQty = prQty;
	}

	public float getPrRate() {
		return prRate;
	}

	public void setPrRate(float prRate) {
		this.prRate = prRate;
	}

	public float getPrDiscountAmount() {
		return prDiscountAmount;
	}

	public void setPrDiscountAmount(float prDiscountAmount) {
		this.prDiscountAmount = prDiscountAmount;
	}

	public float getPrTax1() {
		return prTax1;
	}

	public void setPrTax1(float prTax1) {
		this.prTax1 = prTax1;
	}

	public float getPrNetRate() {
		return prNetRate;
	}

	public void setPrNetRate(float prNetRate) {
		this.prNetRate = prNetRate;
	}

	public float getPrTotal() {
		return prTotal;
	}

	public void setPrTotal(float prTotal) {
		this.prTotal = prTotal;
	}

	public float getPrTotalAt() {
		return prTotalAt;
	}

	public void setPrTotalAt(float prTotalAt) {
		this.prTotalAt = prTotalAt;
	}

	public String getPoQty() {
		return poQty;
	}

	public void setPoQty(String poQty) {
		this.poQty = poQty;
	}

	public String getGrQty() {
		return grQty;
	}

	public void setGrQty(String grQty) {
		this.grQty = grQty;
	}

	public String getArQty() {
		return arQty;
	}

	public void setArQty(String arQty) {
		this.arQty = arQty;
	}

	public String getCipQty() {
		return cipQty;
	}

	public void setCipQty(String cipQty) {
		this.cipQty = cipQty;
	}
	
	public String getFarQty() {
		return farQty;
	}

	public void setFarQty(String farQty) {
		this.farQty = farQty;
	}	

	public String getPrqItemId() {
		return prqItemId;
	}

	public void setPrqItemId(String prqItemId) {
		this.prqItemId = prqItemId;
	}
	
	
	}

