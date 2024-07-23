package com.aliat.alm.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WAREHOUSE")
public class CableBreakReport {
	
	@Id
	@Column(name = "SITE_ID")
	private String locationId;
	
	
	@Column(name = "WARE_name")
	private String locationName;
	
	private String locationType;
	
	
	@Column(name = "WARE_ID")
	private String wareId;
	
	@Column(name = "LONGITUDE")
	private String locationLongitude;
	
	@Column(name = "LATITUDE")
	private String locationLatitude;
	
	
	private String showLocation;
	private String showElement;


	
	public CableBreakReport() {	
	}



	public CableBreakReport(String locationId, String locationName, String locationType, String wareId,
			String locationLongitude, String locationLatitude, String showLocation, String showElement) {
		super();
		this.locationId = locationId;
		this.locationName = locationName;
		this.locationType = locationType;
		this.wareId = wareId;
		this.locationLongitude = locationLongitude;
		this.locationLatitude = locationLatitude;
		this.showLocation = showLocation;
		this.showElement = showElement;
	}



	public String getLocationId() {
		return locationId;
	}



	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}



	public String getLocationName() {
		return locationName;
	}



	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}



	public String getLocationType() {
		return locationType;
	}



	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}



	public String getWareId() {
		return wareId;
	}



	public void setWareId(String wareId) {
		this.wareId = wareId;
	}



	public String getLocationLongitude() {
		return locationLongitude;
	}



	public void setLocationLongitude(String locationLongitude) {
		this.locationLongitude = locationLongitude;
	}



	public String getLocationLatitude() {
		return locationLatitude;
	}



	public void setLocationLatitude(String locationLatitude) {
		this.locationLatitude = locationLatitude;
	}



	public String getShowLocation() {
		return showLocation;
	}



	public void setShowLocation(String showLocation) {
		this.showLocation = showLocation;
	}



	public String getShowElement() {
		return showElement;
	}



	public void setShowElement(String showElement) {
		this.showElement = showElement;
	}



}
