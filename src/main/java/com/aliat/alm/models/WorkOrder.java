package com.aliat.alm.models;
import java.sql.Timestamp;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@Entity
@Table(name = "WORK_ORDER")
public class WorkOrder {


	@Id
	@Column(name = "ID", nullable = false)
	private String workOrdId;
	
	@Column(name = "CREATION_DATE")
	private Timestamp woCreationDate;
		
	@Column(name = "LAST_MODIFICATION_DATE")
	private Timestamp woLastModifieddate;
	
	@Column(name = "PURPOSE")
	private String purpose;
	
	@Column(name = "EXECUTION_DATE")
	private Timestamp executionDate;
	
	@Column(name = "FROM_WAREHOUSE")
	private String fromWare;
	
	
	@Column(name = "WAREHOUSE_NAME_SOURCE")
	private String warehouseSourceName;
	
	@Column(name = "SITE_ID_SOURCE")
	private String siteIdSource;
	
	
	@Column(name = "TO_WAREHOUSE")
	private String toWare;
	
	
	@Column(name = "WAREHOUSE_NAME_DEST")
	private String warehouseNameDest;
	
	
	@Column(name = "SITE_ID_DEST")
	private String siteIdDest;
	
	
	
	@Column(name = "TOTAL_QTY_SOURCE")
	private float wostotalQty;
	
	@Column(name = "TOTAL_QTY_DESTINATION")
	private float wodtotalQty;
	
	@Column(name = "STATUS")
	private String woStatus;
	
	
	
	public WorkOrder() {	
	}



	
	



	public WorkOrder(String workOrdId, Timestamp woCreationDate, Timestamp woLastModifieddate, String purpose,
			Timestamp executionDate, String fromWare, String warehouseSourceName, String siteIdSource, String toWare,
			String warehouseNameDest, String siteIdDest, float wostotalQty, float wodtotalQty, String woStatus) {
		super();
		this.workOrdId = workOrdId;
		this.woCreationDate = woCreationDate;
		this.woLastModifieddate = woLastModifieddate;
		this.purpose = purpose;
		this.executionDate = executionDate;
		this.fromWare = fromWare;
		this.warehouseSourceName = warehouseSourceName;
		this.siteIdSource = siteIdSource;
		this.toWare = toWare;
		this.warehouseNameDest = warehouseNameDest;
		this.siteIdDest = siteIdDest;
		this.wostotalQty = wostotalQty;
		this.wodtotalQty = wodtotalQty;
		this.woStatus = woStatus;
	}








	public String getWorkOrdId() {
		return workOrdId;
	}








	public void setWorkOrdId(String workOrdId) {
		this.workOrdId = workOrdId;
	}








	public Timestamp getWoCreationDate() {
		return woCreationDate;
	}








	public void setWoCreationDate(Timestamp woCreationDate) {
		this.woCreationDate = woCreationDate;
	}








	public Timestamp getWoLastModifieddate() {
		return woLastModifieddate;
	}








	public void setWoLastModifieddate(Timestamp woLastModifieddate) {
		this.woLastModifieddate = woLastModifieddate;
	}








	public String getPurpose() {
		return purpose;
	}








	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}








	public Timestamp getExecutionDate() {
		return executionDate;
	}








	public void setExecutionDate(Timestamp executionDate) {
		this.executionDate = executionDate;
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








	public float getWostotalQty() {
		return wostotalQty;
	}








	public void setWostotalQty(float wostotalQty) {
		this.wostotalQty = wostotalQty;
	}








	public float getWodtotalQty() {
		return wodtotalQty;
	}








	public void setWodtotalQty(float wodtotalQty) {
		this.wodtotalQty = wodtotalQty;
	}








	public String getWoStatus() {
		return woStatus;
	}








	public void setWoStatus(String woStatus) {
		this.woStatus = woStatus;
	}






	public String getWarehouseSourceName() {
		return warehouseSourceName;
	}








	public void setWarehouseSourceName(String warehouseSourceName) {
		this.warehouseSourceName = warehouseSourceName;
	}








	public String getSiteIdSource() {
		return siteIdSource;
	}








	public void setSiteIdSource(String siteIdSource) {
		this.siteIdSource = siteIdSource;
	}








	public String getWarehouseNameDest() {
		return warehouseNameDest;
	}








	public void setWarehouseNameDest(String warehouseNameDest) {
		this.warehouseNameDest = warehouseNameDest;
	}








	public String getSiteIdDest() {
		return siteIdDest;
	}








	public void setSiteIdDest(String siteIdDest) {
		this.siteIdDest = siteIdDest;
	}








	@Override
	public String toString () {
	  return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
