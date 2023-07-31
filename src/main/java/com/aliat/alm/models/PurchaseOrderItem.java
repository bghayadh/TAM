package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PURCHASE_ORDER_ITEM")
public class PurchaseOrderItem {

	@Id
	@Column(name = "PO_ITEM_ID", nullable = false)
	private String pordItemId;
	
	@Column(name = "CREATION_DATE")
	private Timestamp CreationDate;
	
	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp ModifiedDate;
	
	@Column(name = "ITEM_CODE")
	private String ItemCode;
	
	@Column(name = "ITEM_NAME")
	private String ItemName;
	
	@Column(name = "ITEM_MODEL")
	private String ItemModel;
	
	@Column(name = "ITEM_PART_NUMBER")
	private String ItemPartNb;
	
	@Column(name = "RATE")
	private float Rate;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private float DiscAmnt;
	
	@Column(name = "DISCOUNT_PERCENT")
	private float DiscPercent;
	
	@Column(name = "NET_RATE")
	private float NetRate;
	
	@Column(name = "TAX1")
	private float Tax1;
	
	@Column(name = "TAX2")
	private float Tax2;
	
	@Column(name = "TOTAL")
	private float Total;
	
	@Column(name = "TOTAL_AT")
	private float TotalAt;
		
	@Column(name = "GR_QTY")
	private String GrNo;
		
	@Column(name = "PR_QTY")
	private String iPrNo;
	
	@Column(name = "AR_QTY")
	private String ArNo;
	
	@Column(name = "DAR_QTY")
	private String DarNo;
	
	@Column(name = "FAR_QTY")
	private String FarNo;
	
	@Column(name = "CIP_QTY")
	private String iCIPNo;
	
	@Column(name = "QTY")
	private float Qty;
	
	@Column(name = "PO_ID")
	private String POId;
	
	@Column(name = "PO_ITEM_STATUS")
	private String poItemStatus;
	
	@Column(name = "ITEM_BARCODE")
	private String iBarcode;

	public PurchaseOrderItem() {	
	}
	
	
	


	public PurchaseOrderItem(String pordItemId, Timestamp CreationDate, Timestamp ModifiedDate,
			String ItemCode, String ItemName, String ItemModel, String ItemPartNb,
			float Rate, float DiscAmnt, float DiscPercent, float NetRate, float Tax1,
			float Tax2, float Total, float TotalAt, String GrNo, String iPrNo, String ArNo,
			String DarNo, String FarNo, String iCIPNo, float Qty, String POId, String poItemStatus,String iBarcode) {
		super();
		this.pordItemId = pordItemId;
		this.CreationDate = CreationDate;
		this.ModifiedDate = ModifiedDate;
		this.ItemCode = ItemCode;
		this.ItemName = ItemName;
		this.ItemModel = ItemModel;
		this.ItemPartNb = ItemPartNb;
		this.Rate = Rate;
		this.DiscAmnt = DiscAmnt;
		this.DiscPercent = DiscPercent;
		this.NetRate = NetRate;
		this.Tax1 = Tax1;
		this.Tax2 = Tax2;
		this.Total = Total;
		this.TotalAt = TotalAt;
		this.GrNo = GrNo;
		this.iPrNo = iPrNo;
		this.ArNo = ArNo;
		this.DarNo = DarNo;
		this.FarNo = FarNo;
		this.iCIPNo = iCIPNo;
		this.Qty = Qty;
		this.POId = POId;
		this.poItemStatus = poItemStatus;
		this.iBarcode = iBarcode;
	}






	public String getPoItemStatus() {
		return poItemStatus;
	}





	public void setPoItemStatus(String poItemStatus) {
		this.poItemStatus = poItemStatus;
	}





	public String getPordItemId() {
		return pordItemId;
	}


	public void setPordItemId(String pordItemId) {
		this.pordItemId = pordItemId;
	}


	public Timestamp getPordiCreationDate() {
		return CreationDate;
	}


	public void setPordiCreationDate(Timestamp CreationDate) {
		this.CreationDate = CreationDate;
	}


	public Timestamp getPordiLastMOdifiedDate() {
		return ModifiedDate;
	}


	public void setPordiLastMOdifiedDate(Timestamp ModifiedDate) {
		this.ModifiedDate = ModifiedDate;
	}


	public String getPordiItemCode() {
		return ItemCode;
	}


	public void setPordiItemCode(String ItemCode) {
		this.ItemCode = ItemCode;
	}


	public String getPordiItemName() {
		return ItemName;
	}


	public void setPordiItemName(String ItemName) {
		this.ItemName = ItemName;
	}


	public String getPordiItemModel() {
		return ItemModel;
	}





	public void setPordiItemModel(String ItemModel) {
		this.ItemModel = ItemModel;
	}





	public String getPordiItemPartNumber() {
		return ItemPartNb;
	}





	public void setPordiItemPartNumber(String ItemPartNb) {
		this.ItemPartNb = ItemPartNb;
	}





	public float getPordiRate() {
		return Rate;
	}


	public void setPordiRate(float Rate) {
		this.Rate = Rate;
	}


	public float getPordiDiscountAmount() {
		return DiscAmnt;
	}


	public void setPordiDiscountAmount(float DiscAmnt) {
		this.DiscAmnt = DiscAmnt;
	}


	public float getPordiDiscountPercent() {
		return DiscPercent;
	}


	public void setPordiDiscountPercent(float DiscPercent) {
		this.DiscPercent = DiscPercent;
	}


	public float getPordiNetRate() {
		return NetRate;
	}


	public void setPordiNetRate(float NetRate) {
		this.NetRate = NetRate;
	}


	public float getPordiTax1() {
		return Tax1;
	}


	public void setPordiTax1(float Tax1) {
		this.Tax1 = Tax1;
	}


	public float getPordiTax2() {
		return Tax2;
	}


	public void setPordiTax2(float Tax2) {
		this.Tax2 = Tax2;
	}


	public float getPordiTotal() {
		return Total;
	}


	public void setPordiTotal(float Total) {
		this.Total = Total;
	}


	public float getPordiTotalAt() {
		return TotalAt;
	}


	public void setPordiTotalAt(float TotalAt) {
		this.TotalAt = TotalAt;
	}


	public String getPordiGrNo() {
		return GrNo;
	}


	public void setPordiGrNo(String GrNo) {
		this.GrNo = GrNo;
	}


	public String getPordiPrNo() {
		return iPrNo;
	}


	public void setPordiPrNo(String iPrNo) {
		this.iPrNo = iPrNo;
	}


	public String getPordiArNo() {
		return ArNo;
	}


	public void setPordiArNo(String ArNo) {
		this.ArNo = ArNo;
	}


	public String getPordiDarNo() {
		return DarNo;
	}


	public void setPordiDarNo(String DarNo) {
		this.DarNo = DarNo;
	}


	public String getPordiFarNo() {
		return FarNo;
	}


	public void setPordiFarNo(String FarNo) {
		this.FarNo = FarNo;
	}

	
	public String getPordiCIPNo() {
		return iCIPNo;
	}


	public void setPordiCIPNo(String iCIPNo) {
		this.iCIPNo = iCIPNo;
	}

	public float getPordiQty() {
		return Qty;
	}


	public void setPordiQty(float Qty) {
		this.Qty = Qty;
	}

	
	public String getPordiPrqId() {
		return POId;
	}


	public void setPordiPrqId(String POId) {
		this.POId = POId;
	}
	

	public String getPordiBarcode() {
		return iBarcode;
	}


	public void setPordiBarcode(String iBarcode) {
		this.iBarcode = iBarcode;
	}

	
}
