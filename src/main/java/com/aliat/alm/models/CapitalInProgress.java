package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CAPITAL_IN_PROGRESS")
public class CapitalInProgress {

	@Id
	@Column(name = "CIP_ID", nullable = false)
	private String cipID;
	
	@Column(name = "ITEM_CODE", nullable = false)
	private String cipitemCode;
	
	@Column(name = "CREATED_DATE")
	private Timestamp cipcreatedDate;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp ciplastModifiedDate;
	
	@Column(name = "ITEM_NAME")
	private String cipitemName;
	
	@Column(name = "UNIT")
	private String cipunit;
	
	@Column(name = "ITEM_DESCRIPTION")
	private String cipitemDescription;
	
	@Column(name = "DOMAIN")
	private String cipdomain;
	
	@Column(name = "ITEM_CATEGORY")
	private String cipitemCategory;
	
	@Column(name = "ITEM_TYPE")
	private String cipitemType;
	
	@Column(name = "CABLE_TYPE")
	private String cipcableType;
	
	@Column(name = "WEIGHT")
	private String cipweight;

	@Column(name = "WEIGHT_UOM")
	private String cipweightUOM;
	
	@Column(name = "LENGTH")
	private String ciplength;

	@Column(name = "WIDTH")
	private String cipwidth;
	
	@Column(name = "HEIGHT")
	private String cipheight;
	
	@Column(name = "SIZE_UOM")
	private String cipsizeUOM;
	
	@Column(name = "SERVICE")
	private char cipservice;

	@Column(name = "END_OF_LIFE")
	private Timestamp cipendOfLife;
	
	@Column(name = "VALUATION_RATE")
	private float cipvaluationRate;

	@Column(name = "DISABLED")
	private char cipdisabled;
	
	@Column(name = "ITEM_IMAGE")
	private String cipitemImage;
	
	@Column(name = "WARRANTY_PERIOD")
	private String cipwarrantyPeriod;
	
	@Column(name = "TECH_2G")
	private char ciptech2G;
	
	@Column(name = "TECH_3G")
	private char ciptech3G;
	
	@Column(name = "TECH_4G")
	private char ciptech4G;
	
	@Column(name = "PO_ITEM_ID")
	private String PoItemId;
	
	@Column(name = "PO_ID")
	private String PoId;
	
	@Column(name = "TOTALQTY")
	private float TOTALQTY;
	
	@Column(name = "SUPPLIER_ID")
	private String supplierID;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name = "PRQ_ID")
	private String prId;
	
	@Column(name = "GR_ID")
	private String grId;
	
	public CapitalInProgress() {	
	}
	
	public CapitalInProgress(String cipID, String cipitemCode, Timestamp cipcreatedDate,Timestamp ciplastModifiedDate,String cipitemName,String cipunit,String cipitemDescription,String cipdomain,String cipitemCategory,String cipitemType,String cipcableType,String cipweight,String cipweightUOM,String ciplength,String cipwidth,String cipheight,String cipsizeUOM,char cipservice,Timestamp cipendOfLife,float cipvaluationRate,char cipdisabled,String cipitemImage,String cipwarrantyPeriod, char ciptech2G ,char ciptech3G,char ciptech4G, String PoItemId,String PoId, float TOTALQTY,String supplierID, String supplierName,String prId,String grId) {
		super();
		this.cipID = cipID;
		this.cipitemCode = cipitemCode;
		this.cipcreatedDate = cipcreatedDate;
		this.ciplastModifiedDate= ciplastModifiedDate;
		this.cipitemName = cipitemName;
		this.cipunit = cipunit;
		this.cipitemDescription = cipitemDescription;
		this.cipdomain = cipdomain;
		this.cipitemCategory = cipitemCategory;
		this.cipitemType = cipitemCategory;
		this.cipcableType = cipcableType;
		this.cipweight = cipweight;
		this.cipweightUOM = cipweightUOM;
		this.ciplength = ciplength;
		this.cipwidth = cipwidth;
		this.cipheight = cipheight;
		this.cipsizeUOM = cipsizeUOM;
		this.cipservice = cipservice;
		this.cipendOfLife = cipendOfLife;
		this.cipvaluationRate = cipvaluationRate;
		this.cipdisabled = cipdisabled;
		this.cipitemImage = cipitemImage;
		this.cipwarrantyPeriod = cipwarrantyPeriod;
		this.ciptech2G = ciptech2G;
		this.ciptech3G = ciptech2G;
		this.ciptech4G = ciptech4G;
		this.PoItemId = PoItemId;
		this.PoId = PoId;
		this.TOTALQTY =  TOTALQTY;
		this.supplierID =  supplierID;
		this.supplierName =  supplierName;
		this.prId=prId;
		this.grId=grId;
	}

	public String getCipID() {
		return cipID;
	}

	public void setCipID(String cipID) {
		this.cipID = cipID;
	}

	public String getPoItemId() {
		return PoItemId;
	}

	public void setPoItemId(String poItemId) {
		PoItemId = poItemId;
	}

	public String getCipitemCode() {
		return cipitemCode;
	}

	public void setCipitemCode(String cipitemCode) {
		this.cipitemCode = cipitemCode;
	}

	public Timestamp getCipcreatedDate() {
		return cipcreatedDate;
	}

	public void setCipcreatedDate(Timestamp cipcreatedDate) {
		this.cipcreatedDate = cipcreatedDate;
	}

	public Timestamp getCiplastModifiedDate() {
		return ciplastModifiedDate;
	}

	public void setCiplastModifiedDate(Timestamp ciplastModifiedDate) {
		this.ciplastModifiedDate = ciplastModifiedDate;
	}

	public String getCipitemName() {
		return cipitemName;
	}

	public void setCipitemName(String cipitemName) {
		this.cipitemName = cipitemName;
	}

	public String getCipunit() {
		return cipunit;
	}

	public void setCipunit(String cipunit) {
		this.cipunit = cipunit;
	}

	public String getCipitemDescription() {
		return cipitemDescription;
	}

	public void setCipitemDescription(String cipitemDescription) {
		this.cipitemDescription = cipitemDescription;
	}

	public String getCipdomain() {
		return cipdomain;
	}

	public void setCipdomain(String cipdomain) {
		this.cipdomain = cipdomain;
	}

	public String getCipitemCategory() {
		return cipitemCategory;
	}

	public void setCipitemCategory(String cipitemCategory) {
		this.cipitemCategory = cipitemCategory;
	}

	public String getCipitemType() {
		return cipitemType;
	}

	public void setCipitemType(String cipitemType) {
		this.cipitemType = cipitemType;
	}

	public String getCipcableType() {
		return cipcableType;
	}

	public void setCipcableType(String cipcableType) {
		this.cipcableType = cipcableType;
	}

	public String getCipweight() {
		return cipweight;
	}

	public void setCipweight(String cipweight) {
		this.cipweight = cipweight;
	}

	public String getCipweightUOM() {
		return cipweightUOM;
	}

	public void setCipweightUOM(String cipweightUOM) {
		this.cipweightUOM = cipweightUOM;
	}

	public String getCiplength() {
		return ciplength;
	}

	public void setCiplength(String ciplength) {
		this.ciplength = ciplength;
	}

	public String getCipwidth() {
		return cipwidth;
	}

	public void setCipwidth(String cipwidth) {
		this.cipwidth = cipwidth;
	}

	public String getCipheight() {
		return cipheight;
	}

	public void setCipheight(String cipheight) {
		this.cipheight = cipheight;
	}

	public String getCipsizeUOM() {
		return cipsizeUOM;
	}

	public void setCipsizeUOM(String cipsizeUOM) {
		this.cipsizeUOM = cipsizeUOM;
	}

	public char getCipservice() {
		return cipservice;
	}

	public void setCipservice(char cipservice) {
		this.cipservice = cipservice;
	}

	public Timestamp getCipendOfLife() {
		return cipendOfLife;
	}

	public void setCipendOfLife(Timestamp cipendOfLife) {
		this.cipendOfLife = cipendOfLife;
	}

	public float getCipvaluationRate() {
		return cipvaluationRate;
	}

	public void setCipvaluationRate(float cipvaluationRate) {
		this.cipvaluationRate = cipvaluationRate;
	}

	public char getCipdisabled() {
		return cipdisabled;
	}

	public void setCipdisabled(char cipdisabled) {
		this.cipdisabled = cipdisabled;
	}

	public String getCipitemImage() {
		return cipitemImage;
	}

	public void setCipitemImage(String cipitemImage) {
		this.cipitemImage = cipitemImage;
	}

	public String getCipwarrantyPeriod() {
		return cipwarrantyPeriod;
	}

	public void setCipwarrantyPeriod(String cipwarrantyPeriod) {
		this.cipwarrantyPeriod = cipwarrantyPeriod;
	}

	public char getCiptech2G() {
		return ciptech2G;
	}

	public void setCiptech2G(char ciptech2g) {
		ciptech2G = ciptech2g;
	}

	public char getCiptech3G() {
		return ciptech3G;
	}

	public void setCiptech3G(char ciptech3g) {
		ciptech3G = ciptech3g;
	}

	public char getCiptech4G() {
		return ciptech4G;
	}

	public void setCiptech4G(char ciptech4g) {
		ciptech4G = ciptech4g;
	}

	public String getPoId() {
		return PoId;
	}

	public void setPoId(String poId) {
		PoId = poId;
	}

	public float getTOTALQTY() {
		return TOTALQTY;
	}

	public void setTOTALQTY(float tOTALQTY) {
		TOTALQTY = tOTALQTY;
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
	public String getPrId() {
		return prId;
	}

	public void setPrId(String prId) {
		this.prId = prId;
	}
	public String getGrId() {
		return grId;
	}

	public void setGrId(String grId) {
		this.grId = grId;
	}
	
}
