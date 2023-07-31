package com.aliat.alm.models;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_CATEGORY")
public class ItemCategory {

	@Id
	@Column(name = "ITEM_CAT_ID", nullable = false)
	private String itemcatid;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

		
	@Column(name = "LAST_MODIFED_DATE")
	private Timestamp lastModifieddate;
	
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	
	@Column(name = "CAT_ABR")
	private String categoryABR;
	
	
	
	
	public ItemCategory() {	
	}
	
	public ItemCategory(String itemcatid, Timestamp creationDate , Timestamp lastModifieddate, String categoryName, String categoryABR) {
		super();
		this.itemcatid = itemcatid;
		this.creationDate = creationDate;
		this.lastModifieddate = lastModifieddate;
		this.categoryName = categoryName;
		this.categoryABR = categoryABR;
	}

	public String getItemcategoryid() {
		return itemcatid;
	}

	public void setItemcategoryid(String itemcatid) {
		this.itemcatid = itemcatid;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastModifieddate() {
		return lastModifieddate;
	}

	public void setLastModifieddate(Timestamp lastModifieddate) {
		this.lastModifieddate = lastModifieddate;
	}
	
	public String getcategoryName() {
		return categoryName;
	}

	public void setcategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getcategoryABR() {
		return categoryABR;
	}

	public void setgroupABR(String categoryABR) {
		this.categoryABR = categoryABR;
	}

	
}
