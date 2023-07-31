package com.aliat.alm.models;
import java.sql.Timestamp;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;





@Entity
@Table(name = "WORK_ORDER_DT")
public class WorkOrderDT {
	
	@Id
	@Column(name = "ID", nullable = false)
	private String ID;
	
	
	@Column(name = "FROM_WAREHOUSE")
	private String warehouseSrc;
	
	
	
	@Column(name = "WAREHOUSE_NAME_SOURCE")
	private String warehouseSourceNameDT;
	
	
	@Column(name = "SITE_ID_SOURCE")
	private String siteIdSourceDT;
	

	
	
	@Column(name = "TO_WAREHOUSE")
	private String warehouseDest ;
	
	@Column(name = "WAREHOUSE_NAME_DEST")
	private String warehouseNameDestDT;
	
	
	@Column(name = "SITE_ID_DEST")
	private String siteIdDestDT;
	
	
	@Column(name = "EXECUTION_DATE")
	private String execDate;
	
	@Column(name = "LAST_MODIFICATION_DATE")
	private Timestamp LastModifieddate;
	
	
	@Column(name = "PURPOSE")
	private String purp;
	
	
	
	public WorkOrderDT() {	
	}



	

	public WorkOrderDT(String iD, String warehouseSrc, String warehouseSourceNameDT, String siteIdSourceDT,
			String warehouseDest, String warehouseNameDestDT, String siteIdDestDT, String execDate,
			Timestamp lastModifieddate, String purp) {
		super();
		ID = iD;
		this.warehouseSrc = warehouseSrc;
		this.warehouseSourceNameDT = warehouseSourceNameDT;
		this.siteIdSourceDT = siteIdSourceDT;
		this.warehouseDest = warehouseDest;
		this.warehouseNameDestDT = warehouseNameDestDT;
		this.siteIdDestDT = siteIdDestDT;
		this.execDate = execDate;
		LastModifieddate = lastModifieddate;
		this.purp = purp;
	}





	public String getWarehouseSourceNameDT() {
		return warehouseSourceNameDT;
	}





	public void setWarehouseSourceNameDT(String warehouseSourceNameDT) {
		this.warehouseSourceNameDT = warehouseSourceNameDT;
	}





	public String getSiteIdSourceDT() {
		return siteIdSourceDT;
	}





	public void setSiteIdSourceDT(String siteIdSourceDT) {
		this.siteIdSourceDT = siteIdSourceDT;
	}





	public String getWarehouseNameDestDT() {
		return warehouseNameDestDT;
	}





	public void setWarehouseNameDestDT(String warehouseNameDestDT) {
		this.warehouseNameDestDT = warehouseNameDestDT;
	}





	public String getSiteIdDestDT() {
		return siteIdDestDT;
	}





	public void setSiteIdDestDT(String siteIdDestDT) {
		this.siteIdDestDT = siteIdDestDT;
	}





	public Timestamp getLastModifieddate() {
		return LastModifieddate;
	}





	public void setLastModifieddate(Timestamp lastModifieddate) {
		LastModifieddate = lastModifieddate;
	}





	public String getID() {
		return ID;
	}



	public void setID(String iD) {
		ID = iD;
	}



	public String getWarehouseSrc() {
		return warehouseSrc;
	}



	public void setWarehouseSrc(String warehouseSrc) {
		this.warehouseSrc = warehouseSrc;
	}



	public String getWarehouseDest() {
		return warehouseDest;
	}



	public void setWarehouseDest(String warehouseDest) {
		this.warehouseDest = warehouseDest;
	}



	public String getExecDate() {
		return execDate;
	}



	public void setExecDate(String execDate) {
		this.execDate = execDate;
	}



	public String getPurp() {
		return purp;
	}



	public void setPurp(String purp) {
		this.purp = purp;
	}
	
	
	

}
