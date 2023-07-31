package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
@Entity
@Table(name = "GOODS_RECEIVED_ITEM")
public class GoodsReceivedItem {
	
	@Id
	@Column(name = "GR_RCV_ITEM_ID", nullable = false)
	private String grItemId;
	
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
	private String iPoNo;
		
	@Column(name = "PR_QTY")
	private String iPrNo;
	
	@Column(name = "AR_QTY")
	private String ArNo;
	
	@Column(name = "DAR_QTY")
	private String DarNo;
	
	@Column(name = "FAR_QTY")
	private String FarNo;
	
	@Column(name = "CIP_QTY")
	private String iCIPQty;
	
	@Column(name = "QTY")
	private float Qty;
	
	@Column(name = "GR_ID")
	private String GRid;
	
	@Column(name = "GR_STATUS")
	private String grStatus;
	

	public GoodsReceivedItem() {	
	}
	
	

	public GoodsReceivedItem(String grItemId, Timestamp CreationDate, Timestamp ModifiedDate,
			String ItemCode, String ItemName, String ItemModel, String ItemPartNb, float Rate,
			float DiscAmnt, float DiscPercent, float NetRate, float Tax1, float Tax2,
			float Total, float TotalAt, String iPoNo, String iPrNo, String ArNo, String DarNo,
			String FarNo, String iCIPQty, float Qty, String GRid, String grStatus) {
		super();
		this.grItemId = grItemId;
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
		this.iPoNo = iPoNo;
		this.iPrNo = iPrNo;
		this.ArNo = ArNo;
		this.DarNo = DarNo;
		this.FarNo = FarNo;
		this.iCIPQty = iCIPQty;
		this.Qty = Qty;
		this.GRid = GRid;
		this.grStatus = grStatus;
	}



	public String getGrStatus() {
		return grStatus;
	}



	public void setGrStatus(String grStatus) {
		this.grStatus = grStatus;
	}



	public String getGrItemId() {
		return grItemId;
	}

	public void setGrItemId(String grItemId) {
		this.grItemId = grItemId;
	}

	public Timestamp getGriCreationDate() {
		return CreationDate;
	}

	public void setGriCreationDate(Timestamp CreationDate) {
		this.CreationDate = CreationDate;
	}

	public Timestamp getGriLastMOdifiedDate() {
		return ModifiedDate;
	}

	public void setGriLastMOdifiedDate(Timestamp ModifiedDate) {
		this.ModifiedDate = ModifiedDate;
	}

	public String getGrdiItemCode() {
		return ItemCode;
	}

	public void setGrdiItemCode(String ItemCode) {
		this.ItemCode = ItemCode;
	}

	public String getGrdiItemName() {
		return ItemName;
	}

	public void setGrdiItemName(String ItemName) {
		this.ItemName = ItemName;
	}

	public String getGrdiItemModel() {
		return ItemModel;
	}



	public void setGrdiItemModel(String ItemModel) {
		this.ItemModel = ItemModel;
	}



	public String getGrdiItemPartNumber() {
		return ItemPartNb;
	}



	public void setGrdiItemPartNumber(String ItemPartNb) {
		this.ItemPartNb = ItemPartNb;
	}



	public float getGriRate() {
		return Rate;
	}

	public void setGriRate(float Rate) {
		this.Rate = Rate;
	}

	public float getGriDiscountAmount() {
		return DiscAmnt;
	}

	public void setGriDiscountAmount(float DiscAmnt) {
		this.DiscAmnt = DiscAmnt;
	}

	public float getGriDiscountPercent() {
		return DiscPercent;
	}

	public void setGriDiscountPercent(float DiscPercent) {
		this.DiscPercent = DiscPercent;
	}

	public float getGriNetRate() {
		return NetRate;
	}

	public void setGriNetRate(float NetRate) {
		this.NetRate = NetRate;
	}

	public float getGriTax1() {
		return Tax1;
	}

	public void setGriTax1(float Tax1) {
		this.Tax1 = Tax1;
	}

	public float getGriTax2() {
		return Tax2;
	}

	public void setGriTax2(float Tax2) {
		this.Tax2 = Tax2;
	}

	public float getGriTotal() {
		return Total;
	}

	public void setGriTotal(float Total) {
		this.Total = Total;
	}

	public float getGriTotalAt() {
		return TotalAt;
	}

	public void setGriTotalAt(float TotalAt) {
		this.TotalAt = TotalAt;
	}

	public String getGriPoNo() {
		return iPoNo;
	}

	public void setGriPoNo(String iPoNo) {
		this.iPoNo = iPoNo;
	}

	public String getGriPrNo() {
		return iPrNo;
	}

	public void setGriPrNo(String iPrNo) {
		this.iPrNo = iPrNo;
	}

	public String getGriArNo() {
		return ArNo;
	}

	public void setGriArNo(String ArNo) {
		this.ArNo = ArNo;
	}

	public String getGriDarNo() {
		return DarNo;
	}

	public void setGriDarNo(String DarNo) {
		this.DarNo = DarNo;
	}

	public String getGriFarNo() {
		return FarNo;
	}

	public void setGriFarNo(String FarNo) {
		this.FarNo = FarNo;
	}

	public String getGriCIPNo() {
		return iCIPQty;
	}

	public void setGriCIPNo(String iCIPQty) {
		this.iCIPQty = iCIPQty;
	}

	public float getGriQty() {
		return Qty;
	}

	public void setGriQty(float Qty) {
		this.Qty = Qty;
	}

	public String getGriGRId() {
		return GRid;
	}

	public void setGriGRId(String GRid) {
		this.GRid = GRid;
	}
	
	 @Override
	  public String toString () {
	    return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	  }

}
