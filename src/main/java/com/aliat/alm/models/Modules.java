package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="MODULES")


public class Modules {

	
	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "CATEGOREY")
	private String category;

	public Modules(String id, String name, String category) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
	}

	public Modules() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	
	
	
	
}
