package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ASSET_DEPRECIATION_12MONTH")
public class AssetDepreciation12Month {
	@Id
	@Column(name = "ASSET_DEPRECIATION_12MONTH_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ASSET_DEPRECIATION_12MONTH_ID")
	@SequenceGenerator(name = "ASSET_DEPRECIATION_12MONTH_ID", sequenceName = "ASSET_DEPRECIATION_12MONTH_ID", allocationSize = 1)
	private int assetDep12MonthId;

	@Column(name = "INITIALCOST", nullable = true)
	private float initCost;
	
	@Column(name = "ACCUMULDEPRECAMNT", nullable = true)
	private float accDeprecamnt;
	
	@Column(name = "NETCOST", nullable = true)
	private float netCost;

	@Column(name = "CREATED_DATE", nullable = true)
	private String createdDate;

	public AssetDepreciation12Month() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssetDepreciation12Month( float initCost, float accDeprecamnt, float netCost,
			String createdDate) {
		super();
		this.initCost = initCost;
		this.accDeprecamnt = accDeprecamnt;
		this.netCost = netCost;
		this.createdDate = createdDate;
	}

	public int getLine12MonthId() {
		return assetDep12MonthId;
	}
	

	public float getInitCost() {
		return initCost;
	}

	public void setInitCost(float initCost) {
		this.initCost = initCost;
	}

	public float getAccDeprecamnt() {
		return accDeprecamnt;
	}

	public void setAccDeprecamnt(float accDeprecamnt) {
		this.accDeprecamnt = accDeprecamnt;
	}

	public float getNetCost() {
		return netCost;
	}

	public void setNetCost(float netCost) {
		this.netCost = netCost;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	}
