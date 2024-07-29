package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYSTEM_SETTINGS")

public class SystemSettings {
	
	
	@Id
	@Column(name = "SYSTEM_SETTING_ID", nullable = false)
	private String ID;
	
	@Column(name = "SYSTEM_LANGUAGE")
	private String sysLangauge;
	
	@Column(name = "SYSTEM_COUNTRY")
	private String sysCountry;
	
	@Column(name = "SYSTEM_CURRENCY")
	private String sysCurrency;
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "PATH")
	private String path;
	
	@Column(name = "IP_ADDRESS")
	private String ipAddress;
	
	@Column(name = "CREATION_DATE")
	private Timestamp createdDate;

	@Column(name = "LAST_MODIFICATION_DATE")
	private Timestamp lastModifiedDate;
	
	
	
	public SystemSettings() {
		super();
	}

	public SystemSettings(String iD, String sysLangauge, String sysCountry,String sysCurrency, String username, String password, String path, String ipAddress, Timestamp createdDate, Timestamp lastModifiedDate) {
		super();
		this.ID = iD;
		this.sysLangauge = sysLangauge;
		this.sysCountry = sysCountry;
		this.sysCurrency = sysCurrency;
		this.username = username;
		this.password = password;
		this.path = path;
		this.ipAddress = ipAddress;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getSysLangauge() {
		return sysLangauge;
	}

	public void setSysLangauge(String sysLangauge) {
		this.sysLangauge = sysLangauge;
	}

	public String getSysCountry() {
		return sysCountry;
	}

	public void setSysCountry(String sysCountry) {
		this.sysCountry = sysCountry;
	}

	public String getSysCurrency() {
		return sysCurrency;
	}

	public void setSysCurrency(String sysCurrency) {
		this.sysCurrency = sysCurrency;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


}

