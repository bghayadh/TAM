package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEST")
public class Test {

	@Id
	@Column(name = "COLUMN1")
	private String col1;
	
	@Column(name = "COLUM21")
	private String col2;
	
	@Column(name = "COLUMN3")
	private String col3;
	
	@Column(name = "ID")
	private String tid;
	
	public Test() {	
	}
	
	public Test(String col1, String col2 , String col3,  String tid) {
		super();
		this.col1 = col1;
		this.col2 = col2;
		this.col3 = col3;
		this.tid = tid;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}
	
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
	
}
