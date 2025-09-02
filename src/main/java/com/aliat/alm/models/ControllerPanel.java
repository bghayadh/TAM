package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONTROLLER")
public class ControllerPanel {


	    @Id
	    @Column(name = "CONTROLLER_ID", nullable = false)
	    private String controllerId;

	    @Column(name = "CONTROLLER_NAME")
	    private String controllerName;

	    
	    @Column(name = "CITY")
	    private String controllerCity;
	    
	    @Column(name = "SERIAL_NUMB")
	    private String serialNumber;

	    @Column(name = "MAC_ADDRESS")
	    private String macAddress;

	    @Column(name = "IP_ADDRESS")
	    private String ipAddress;

	    @Column(name = "SUBNET_MASK")
	    private String subnetMask;

	    @Column(name = "DEFAULT_GATEWAY")
	    private String defaultGateway;

	    @Column(name = "USER_NAME")
	    private String userName;

	    @Column(name = "PASSWORD")
	    private String password;

	    @Column(name = "NUMB_OF_PANNELS")
	    private String numberOfPanels;

	    @Column(name = "NUMB_OF_PORTS")
	    private String numberOfPorts;

	    @Column(name = "NETWORK_LAYER")
	    private String networkLayer;

	    @Column(name = "LONGITUDE")
	    private String longitude;

	    @Column(name = "LATITUDE")
	    private String latitude;

	    @Column(name = "SITE")
	    private String site;

	    @Column(name = "SITE_NAME")
	    private String siteName;

	    @Column(name = "WAREHOUSE")
	    private String warehouse;

	    @Column(name = "CREATION_DATE")
	    private Timestamp creationDate;

	    @Column(name = "LAST_MODIFIED_DATE")
	    private Timestamp lastModifiedDate;

	    // Constructors
	    public ControllerPanel() {}

	    public ControllerPanel(String controllerId, String controllerName, String serialNumber, String macAddress,
	                           String ipAddress, String subnetMask, String defaultGateway, String userName, String password,
	                           String numberOfPanels, String numberOfPorts, String networkLayer, String longitude,
	                           String latitude, String site, String siteName, String warehouse,
	                           Timestamp creationDate, Timestamp lastModifiedDate, String controllerCity) {
	        this.controllerId = controllerId;
	        this.controllerName = controllerName;
	        this.serialNumber = serialNumber;
	        this.macAddress = macAddress;
	        this.ipAddress = ipAddress;
	        this.controllerCity= controllerCity;
	        this.subnetMask = subnetMask;
	        this.defaultGateway = defaultGateway;
	        this.userName = userName;
	        this.password = password;
	        this.numberOfPanels = numberOfPanels;
	        this.numberOfPorts = numberOfPorts;
	        this.networkLayer = networkLayer;
	        this.longitude = longitude;
	        this.latitude = latitude;
	        this.site = site;
	        this.siteName = siteName;
	        this.warehouse = warehouse;
	        this.creationDate = creationDate;
	        this.lastModifiedDate = lastModifiedDate;
	    }

	    public String getControllerCity() {
			return controllerCity;
		}

		public void setControllerCity(String controllerCity) {
			this.controllerCity = controllerCity;
		}

		// Getters and Setters
	    public String getSite() {
	        return site;
	    }

	    public void setSite(String site) {
	        this.site = site;
	    }

	    public String getSiteName() {
	        return siteName;
	    }

	    public void setSiteName(String siteName) {
	        this.siteName = siteName;
	    }

	    public String getWarehouse() {
	        return warehouse;
	    }

	    public void setWarehouse(String warehouse) {
	        this.warehouse = warehouse;
	    }

		public String getControllerId() {
			return controllerId;
		}

		public void setControllerId(String controllerId) {
			this.controllerId = controllerId;
		}

		public String getControllerName() {
			return controllerName;
		}

		public void setControllerName(String controllerName) {
			this.controllerName = controllerName;
		}

		public String getSerialNumber() {
			return serialNumber;
		}

		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}

		public String getMacAddress() {
			return macAddress;
		}

		public void setMacAddress(String macAddress) {
			this.macAddress = macAddress;
		}

		public String getIpAddress() {
			return ipAddress;
		}

		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}

		public String getSubnetMask() {
			return subnetMask;
		}

		public void setSubnetMask(String subnetMask) {
			this.subnetMask = subnetMask;
		}

		public String getDefaultGateway() {
			return defaultGateway;
		}

		public void setDefaultGateway(String defaultGateway) {
			this.defaultGateway = defaultGateway;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getNumberOfPanels() {
			return numberOfPanels;
		}

		public void setNumberOfPanels(String numberOfPanels) {
			this.numberOfPanels = numberOfPanels;
		}

		public String getNumberOfPorts() {
			return numberOfPorts;
		}

		public void setNumberOfPorts(String numberOfPorts) {
			this.numberOfPorts = numberOfPorts;
		}

		public String getNetworkLayer() {
			return networkLayer;
		}

		public void setNetworkLayer(String networkLayer) {
			this.networkLayer = networkLayer;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public Timestamp getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Timestamp creationDate) {
			this.creationDate = creationDate;
		}

		public Timestamp getLastModifiedDate() {
			return lastModifiedDate;
		}

		public void setLastModifiedDate(Timestamp lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
		}
	
}