package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PURCHASE_REQUEST_ITEM")


public class PurchaseRequestItem {
	
	@Id
	@Column(name = "PRQ_ITEM_ID", nullable = false)
	private String prqItemId;
	
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
		
	@Column(name = "PO_QTY")
	private String PqNo;
		
	@Column(name = "GR_QTY")
	private String GrNo;
	
	@Column(name = "AR_QTY")
	private String ArNo;
	
	@Column(name = "DAR_QTY")
	private String DarNo;
	
	@Column(name = "CIP_QTY")
	private String CipNo;
	
	@Column(name = "FAR_QTY")
	private String FarNo;
	
	@Column(name = "QTY")
	private float Qty;
	
	@Column(name = "PRQ_ID")
	private String PRQId;
	
	@Column(name = "PRQ_PO_STATUS")
	private String prqPoStatus;
	
	@Column(name = "ITEM_BARCODE")
	private String Barcode;


	public PurchaseRequestItem() {	
	}
	
	

	public PurchaseRequestItem(String prqItemId, Timestamp CreationDate, Timestamp ModifiedDate,
			String ItemCode, String ItemName, String ItemModel, String ItemPartNb, float Rate,
			float DiscAmnt, float DiscPercent, float NetRate, float Tax1, float Tax2,
			float Total, float TotalAt, String PqNo, String GrNo, String ArNo, String DarNo,
			String CipNo, String FarNo, float Qty, String PRQId, String prqPoStatus,String Barcode) {
		super();
		this.prqItemId = prqItemId;
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
		this.PqNo = PqNo;
		this.GrNo = GrNo;
		this.ArNo = ArNo;
		this.DarNo = DarNo;
		this.CipNo = CipNo;
		this.FarNo = FarNo;
		this.Qty = Qty;
		this.PRQId = PRQId;
		this.prqPoStatus = prqPoStatus;
		this.Barcode = Barcode;
	}



	public String getPrqItemId() {
		return prqItemId;
	}
	
	public String getPrqPoStatus() {
		return prqPoStatus;
	}



	public void setPrqPoStatus(String prqPoStatus) {
		this.prqPoStatus = prqPoStatus;
	}

	public void setPrqItemId(String prqItemId) {
		this.prqItemId = prqItemId;
	}

	public Timestamp getPrqiCreationDate() {
		return CreationDate;
	}

	public void setPrqiCreationDate(Timestamp CreationDate) {
		this.CreationDate = CreationDate;
	}

	public Timestamp getPrqiLastMOdifiedDate() {
		return ModifiedDate;
	}

	public void setPrqiLastMOdifiedDate(Timestamp ModifiedDate) {
		this.ModifiedDate = ModifiedDate;
	}

	public String getPrqiItemCode() {
		return ItemCode;
	}

	public void setPrqiItemCode(String ItemCode) {
		this.ItemCode = ItemCode;
	}

	public String getPrqiItemName() {
		return ItemName;
	}

	public void setPrqiItemName(String ItemName) {
		this.ItemName = ItemName;
	}
	
	
	

	public String getPrqiBarcode() {
		return Barcode;
	}


	public void setPrqiBarcode(String Barcode) {
		this.Barcode = Barcode;
	}


	
	public String getPrqiItemModel() {
		return ItemModel;
	}



	public void setPrqiItemModel(String ItemModel) {
		this.ItemModel = ItemModel;
	}

	public String getPrqiItemPartNumber() {
		return ItemPartNb;
	}



	public void setPrqiItemPartNumber(String ItemPartNb) {
		this.ItemPartNb = ItemPartNb;
	}



	public String getPrqiDarNo() {
		return DarNo;
	}



	public void setPrqiDarNo(String DarNo) {
		this.DarNo = DarNo;
	}



	public float getPrqiRate() {
		return Rate;
	}

	public void setPrqiRate(float Rate) {
		this.Rate = Rate;
	}

	public float getPrqiDiscountAmount() {
		return DiscAmnt;
	}

	public void setPrqiDiscountAmount(float DiscAmnt) {
		this.DiscAmnt = DiscAmnt;
	}

	public float getPrqiDiscountPercent() {
		return DiscPercent;
	}

	public void setPrqiDiscountPercent(float DiscPercent) {
		this.DiscPercent = DiscPercent;
	}

	public float getPrqiNetRate() {
		return NetRate;
	}

	public void setPrqiNetRate(float NetRate) {
		this.NetRate = NetRate;
	}

	public float getPrqiTax1() {
		return Tax1;
	}

	public void setPrqiTax1(float Tax1) {
		this.Tax1 = Tax1;
	}

	public float getPrqiTax2() {
		return Tax2;
	}

	public void setPrqiTax2(float Tax2) {
		this.Tax2 = Tax2;
	}

	public float getPrqiTotal() {
		return Total;
	}

	public void setPrqiTotal(float Total) {
		this.Total = Total;
	}

	public float getPrqiTotalAt() {
		return TotalAt;
	}

	public void setPrqiTotalAt(float TotalAt) {
		this.TotalAt = TotalAt;
	}

	public String getPrqiPqNo() {
		return PqNo;
	}

	public void setPrqiPqNo(String PqNo) {
		this.PqNo = PqNo;
	}

	public String getPrqiGrNo() {
		return GrNo;
	}

	public void setPrqiGrNo(String GrNo) {
		this.GrNo = GrNo;
	}

	public String getPrqiArNo() {
		return ArNo;
	}

	public void setPrqiArNo(String ArNo) {
		this.ArNo = ArNo;
	}
	
	public String getiDarNo() {
		return DarNo;
	}
	
	public void setiDarNo(String DarNo) {
		this.DarNo = DarNo;
	}

	public String getPrqiFarNo() {
		return FarNo;
	}

	public void setPrqiFarNo(String FarNo) {
		this.FarNo = FarNo;
	}
	
	public String getPrqiCipNo() {
		return CipNo;
	}

	public void setPrqiCipNo(String CipNo) {
		this.CipNo = CipNo;
	}

	public float getPrqiQty() {
		return Qty;
	}

	public void setPrqiQty(float Qty) {
		this.Qty = Qty;
	}

	public String getPrqiPrqId() {
		return PRQId;
	}

	public void setPrqiPrqId(String PRQId) {
		this.PRQId = PRQId;
	}
	
}
