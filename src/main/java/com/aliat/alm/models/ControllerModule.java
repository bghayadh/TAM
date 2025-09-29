package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONTROLLER_MODULE")
public class ControllerModule {


	    @Id
	    @Column(name = "MODULE_ID", nullable = false)
	    private String moduleId;

	    @Column(name = "MODULE_POSITION")
	    private String modulePosition;

	    
	    @Column(name = "KIT_SERIAL_NUM")
	    private String kitSerialNum;
	    
	    
	    @Column(name = "SENSORS_PER_PORT_NUM")
	    private String sesorsPerPortNum;
	   
	    @Column(name = "DB_ID")
	    private String dbId;
	   
	    
	    
	    @Column(name = "LOWEST_PORT_NUM")
	    private String lowestPortNum;
	    
	    @Column(name = "SENSOR_COUNT")
	    private String sensorCount;
	    
	    
	    @Column(name = "OCCUPIED_SENSOR_MASK")
	    private String occupiedSensorMask;
	    
	    @Column(name = "ORIENTATION")
	    private String orientation;
	    
	    @Column(name = "CREATED_DATE")
		private Timestamp createDate;
		

		@Column(name = "LAST_MODIFIED_DATE")
		private Timestamp lastModifiedDate;

	  
	    // Constructors
	    public ControllerModule() {}

	    public ControllerModule(String moduleId, String modulePosition, String kitSerialNum, String sesorsPerPortNum, 
	    		 String lowestPortNum, String sensorCount, String occupiedSensorMask, String dbId, Timestamp createDate, Timestamp lastModifiedDate) {
	        this.moduleId = moduleId;
	        this.modulePosition = modulePosition;
	        this.kitSerialNum = kitSerialNum;
	        this.sesorsPerPortNum = sesorsPerPortNum;
	        this.lowestPortNum = lowestPortNum;
	        this.sensorCount = sensorCount;
	        this.dbId= dbId;
	        this.occupiedSensorMask = occupiedSensorMask;
	        this.createDate=createDate;
	        this.lastModifiedDate= lastModifiedDate;
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

		public String getModuleId() {
			return moduleId;
		}

		public void setModuleId(String moduleId) {
			this.moduleId = moduleId;
		}

		public String getModulePosition() {
			return modulePosition;
		}

		public void setModulePosition(String modulePosition) {
			this.modulePosition = modulePosition;
		}

		public String getKitSerialNum() {
			return kitSerialNum;
		}

		public void setKitSerialNum(String kitSerialNum) {
			this.kitSerialNum = kitSerialNum;
		}

		public String getSensorsPerPortNum() {
			return sesorsPerPortNum;
		}

		public void setSensorsPerPortNum(String sesorsPerPortNum) {
			this.sesorsPerPortNum = sesorsPerPortNum;
		}

		public String getLowestPortNum() {
			return lowestPortNum;
		}

		public void setLowestPortNum(String lowestPortNum) {
			this.lowestPortNum = lowestPortNum;
		}

		public String getSensorCount() {
			return sensorCount;
		}

		public void setSensorCount(String sensorCount) {
			this.sensorCount = sensorCount;
		}

		public String getOccupiedSensorMask() {
			return occupiedSensorMask;
		}

		public void setOccupiedSensorMask(String occupiedSensorMask) {
			this.occupiedSensorMask = occupiedSensorMask;
		}

		public String getOrientation() {
			return orientation;
		}

		public String getDbId() {
			return dbId;
		}

		public void setDbId(String dbId) {
			this.dbId = dbId;
		}

		public void setOrientation(String orientation) {
			this.orientation = orientation;
		}

}