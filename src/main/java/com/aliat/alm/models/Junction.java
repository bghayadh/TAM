package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="JUNCTION")
public class Junction {
	
	@Id
	@Column(name = "JUNCTION_ID", nullable = false)
	private String ID;
	
	@Column(name = "JUNCTION_NAME")
	private String junctionName;
	
	@Column(name = "PHYSICAL_LAYER_ID")
	private String physicalLayerID;
	
	@Column(name = "PHYSICAL_LAYER_NAME")
	private String physicalLayerName;
	
	@Column(name = "LONGITUDE")
	private Number longitude;
	
	@Column(name = "LATITUDE")
	private Number latitude;
	
	@Column(name = "CITY")
	private Number city;
	
	@Column(name = "PROJECT_ID")
	private Number projectID;
	
	
}
