package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLUSTER_FINANCE")
public class ClusterFinance {

	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
		
	@Column(name = "LAST_MODIFY_DATE")
	private Timestamp lastModifyDate;	
	
	@Column(name = "START_DATE")
	private Timestamp startDate;
		
	@Column(name = "END_DATE")
	private Timestamp endDate;
	
	
	@Column(name = "CLUSTER_ID")
    private String clusterID;
	
	@Column(name = "CLUSTER_NAME")
	private String clusterName;
	
	@Column(name = "NO_2G_SITES")
	private float number2gSites;
	
	@Column(name = "NO_2G_3G_SITES")
	private float number2g3gSites;
	
	@Column(name = "NO_2G_3G_4G_SITES")
	private float number2g3g4gSites;
	
	
	@Column(name ="PROFIT_AND_LOSS_2G")
	private float pl2g;
	
	
	@Column(name ="PROFIT_AND_LOSS_2G_3G")
	private float pl2g3g;
	
	@Column(name ="PROFIT_AND_LOSS_2G_3G_4G")
	private float pl2g3g4g;
	
	@Column(name ="TOTAL_NO_SITES")
	private float totalSites;
	
	@Column(name ="SUM_PROFIT_LOSS")
	private float sumProfitLoss;
	
	public ClusterFinance() {
		
		
	}

	public ClusterFinance(String id, Timestamp creationDate, Timestamp lastModifyDate, Timestamp startDate, Timestamp endDate, String clusterID, String clusterName,
			float number2gSites, float number2g3gSites, float number2g3g4gSites, float pl2g, float pl2g3g,
			float pl2g3g4g, float totalSites, float sumProfitLoss) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.lastModifyDate = lastModifyDate; 
		this.startDate = startDate;
		this.endDate = endDate;
		this.clusterID = clusterID;
		this.clusterName = clusterName;
		this.number2gSites = number2gSites;
		this.number2g3gSites = number2g3gSites;
		this.number2g3g4gSites = number2g3g4gSites;
		this.pl2g = pl2g;
		this.pl2g3g = pl2g3g;
		this.pl2g3g4g = pl2g3g4g;
		this.totalSites = totalSites;
		this.sumProfitLoss = sumProfitLoss;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Timestamp lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getClusterID() {
		return clusterID;
	}

	public void setClusterID(String clusterID) {
		this.clusterID = clusterID;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public float getNumber2gSites() {
		return number2gSites;
	}

	public void setNumber2gSites(float number2gSites) {
		this.number2gSites = number2gSites;
	}

	public float getNumber2g3gSites() {
		return number2g3gSites;
	}

	public void setNumber2g3gSites(float number2g3gSites) {
		this.number2g3gSites = number2g3gSites;
	}

	public float getNumber2g3g4gSites() {
		return number2g3g4gSites;
	}

	public void setNumber2g3g4gSites(float number2g3g4gSites) {
		this.number2g3g4gSites = number2g3g4gSites;
	}

	public float getPl2g() {
		return pl2g;
	}

	public void setPl2g(float pl2g) {
		this.pl2g = pl2g;
	}

	public float getPl2g3g() {
		return pl2g3g;
	}

	public void setPl2g3g(float pl2g3g) {
		this.pl2g3g = pl2g3g;
	}

	public float getPl2g3g4g() {
		return pl2g3g4g;
	}

	public void setPl2g3g4g(float pl2g3g4g) {
		this.pl2g3g4g = pl2g3g4g;
	}

	public float getTotalSites() {
		return totalSites;
	}

	public void setTotalSites(float totalSites) {
		this.totalSites = totalSites;
	}

	public float getSumProfitLoss() {
		return sumProfitLoss;
	}

	public void setSumProfitLoss(float sumProfitLoss) {
		this.sumProfitLoss = sumProfitLoss;
	}
		
}
