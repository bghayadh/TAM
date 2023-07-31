package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_CAT_TREE")
public class ItemCategoryTree {

	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	@Column(name = "PARENT")
	private String parent;

	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "LFT")
	private int lft;
	
	@Column(name = "RGT")
	private int rgt;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "PARENTCODE")
	private String parentcode;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationdate;
	
	@Column(name="LAST_MODIFIED_DATE")
	private Timestamp lastmodified;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name = "LVL")
	private int lvl;
	
	@Column(name = "PARENTID")
	private String parentid;
	
	@Column(name = "CAT1")
	private String cat1;
	
	@Column(name = "CAT2")
	private String cat2;
	
	@Column(name = "CAT3")
	private String cat3;
	
	@Column(name = "CAT4")
	private String cat4;

	public ItemCategoryTree() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemCategoryTree(String id, String parent, String title, int lft, int rgt, String code, String parentcode,Timestamp creationdate,Timestamp lastmodified,String description,int lvl,String parentid
			,String cat1, String cat2, String cat3, String cat4) {
		super();
		this.id = id;
		this.parent = parent;
		this.title = title;
		this.lft = lft;
		this.rgt = rgt;
		this.code = code;
		this.parentcode = parentcode;
		this.creationdate=creationdate;
		this.lastmodified=lastmodified;
		this.description=description;
		this.lvl = lvl;
		this.parentid = parentid;
		this.cat1 = cat1;
		this.cat2 = cat2;
		this.cat3 = cat3;
		this.cat4 = cat4;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLft() {
		return lft;
	}

	public void setLft(int lft) {
		this.lft = lft;
	}

	public int getRgt() {
		return rgt;
	}

	public void setRgt(int rgt) {
		this.rgt = rgt;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getParentCode() {
		return parentcode;
	}

	public void setParentCode(String parentcode) {
		this.parentcode = parentcode;
	}
	
	public Timestamp getCreationDate()
	{
	return creationdate;
	}
	
	public void setCreationDate(Timestamp creationdate)
	{
		this.creationdate=creationdate;
	}
	
	public Timestamp getLastModified()
	{
		return lastmodified;
	}
	
	public void setLastModified(Timestamp lastmodified)
	{
		this.lastmodified=lastmodified;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description=description;
	}
	
	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	
	public String getParentId() {
		return parentid;
	}

	public void setParentId(String parentid) {
		this.parentid = parentid;
	}

	public String getCat1() {
		return cat1;
	}

	public void setCat1(String cat1) {
		this.cat1 = cat1;
	}

	public String getCat2() {
		return cat2;
	}

	public void setCat2(String cat2) {
		this.cat2 = cat2;
	}

	public String getCat3() {
		return cat3;
	}

	public void setCat3(String cat3) {
		this.cat3 = cat3;
	}

	public String getCat4() {
		return cat4;
	}

	public void setCat4(String cat4) {
		this.cat4 = cat4;
	}
}
