package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONTROLLER_KIT")
public class ControllerKit {


	    @Id
	    @Column(name = "KIT_ID", nullable = false)
	    private String kitId;

	    @Column(name = "KIT_SERIAL_NUM")
	    private String kitSerialNum;

	    
	    @Column(name = "KIT_TYPE")
	    private String kitType;
	    
	    
	    
	    @Column(name = "DB_ID")
	    private String dbId; 
	    
	    @Column(name = "CREATED_DATE")
		private Timestamp createDate;
		

		@Column(name = "LAST_MODIFIED_DATE")
		private Timestamp lastModifiedDate;

	  
	    // Constructors
	    public ControllerKit() {}

	    public ControllerKit(String kitId, String kitSerialNum, String kitType,String dbId, Timestamp createDate, Timestamp lastModifiedDate) {
	        this.kitId = kitId;
	        this.kitSerialNum = kitSerialNum;
	        this.kitType = kitType;
	        this.dbId = dbId;
	        this.createDate=createDate;
	        this.lastModifiedDate= lastModifiedDate;
	           }

		public String getDbId() {
			return dbId;
		}

		public void setDbId(String dbId) {
			this.dbId = dbId;
		}

		public Timestamp getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Timestamp createDate) {
			this.createDate = createDate;
		}

		public Timestamp getLastModifiedDate() {
			return lastModifiedDate;
		}

		public void setLastModifiedDate(Timestamp lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
		}

		public String getKitId() {
			return kitId;
		}

		public void setKitId(String kitId) {
			this.kitId = kitId;
		}

		public String getKitSerialNum() {
			return kitSerialNum;
		}

		public void setKitSerialNum(String kitSerialNum) {
			this.kitSerialNum = kitSerialNum;
		}

		public String getKitType() {
			return kitType;
		}

		public void setKitType(String kitType) {
			this.kitType = kitType;
		}

	  }