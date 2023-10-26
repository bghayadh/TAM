package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



	@Entity
	@Table(name = "CELL_PASSIVE_4G")
	public class LCellPassive{

	    @Id
	    @Column(name = "ID", nullable = false)
	    private String id;

	    @Column(name = "LTE_CELL_ID")
	    private String lteCellId;

	    @Column(name = "DATE_ON_AIR")
	    private String dateOnAir;

	    @Column(name = "CELLNAME")
	    private String cellName;

	    @Column(name = "SITE_SUB_TYPE")
	    private String siteSubType;

	    @Column(name = "ANTENNA_SHARED_WITH_2G")
	    private String antennaSharedWith2G;

	    @Column(name = "ANTENNA_MANUFACTURER")
	    private String antennaManufacturer;

	    @Column(name = "ANTENNA_MODEL")
	    private String antennaModel;

	    @Column(name = "ANTENNA_GAIN")
	    private String antennaGain;

	    @Column(name = "BEAM_WIDTH")
	    private String beamWidth;

	    @Column(name = "AZIMUTH")
	    private String azimuth;

	    @Column(name = "ANTENNA_HEIGHT_AGL")
	    private String antennaHeightAGL;

	    @Column(name = "ELECTRICAL_TILT")
	    private String electricalTilt;

	    @Column(name = "MECHANICAL_TILT")
	    private String mechanicalTilt;

	    @Column(name = "RET")
	    private String ret;

	    @Column(name = "FEEDER_SIZE")
	    private String feederSize;

	    @Column(name = "APPROXIMATE_FEEDER_LENGTH")
	    private String approximateFeederLength;

	    @Column(name = "TMA_MHA")
	    private String tmaMha;

	    @Column(name = "SHARED_SITE")
	    private String sharedSite;

	    @Column(name = "REMARKS")
	    private String remarks;

	    @Column(name = "SECTOR_STATUS")
	    private String sectorStatus;

	    @Column(name = "SECTOR_LOCKED_DATE")
	    private String sectorLockedDate;

	    @Column(name = "LOCKED_REASON")
	    private String lockedReason;

	    @Column(name = "DIPLEXER")
	    private String diplexer;

	    @Column(name = "DIPLEXER_PURPOSE")
	    private String diplexerPurpose;

	    @Column(name = "MASTER_SECTOR_ID")
	    private String masterSectorId;

	    @Column(name = "AT")
	    private String at;

	    @Column(name = "FLAG")
	    private String flag;

	    @Column(name = "STATUS", columnDefinition = "CHAR(1) DEFAULT '0'")
	    private String status;

	    @Column(name = "NEP_SYNCH", columnDefinition = "CHAR(1)")
	    private String nepSynch;

	    @Column(name = "CIRCLE_ID", length = 30)
	    private String circleId;

	    public LCellPassive() {
	    }
	    
	    public LCellPassive(String id, String lteCellId, String dateOnAir, String cellName, String siteSubType, String antennaSharedWith2G, String antennaManufacturer, String antennaModel, String antennaGain, String beamWidth, String azimuth, String antennaHeightAGL, String electricalTilt, String mechanicalTilt, String ret, String feederSize, String approximateFeederLength, String tmaMha, String sharedSite, String remarks, String sectorStatus, String sectorLockedDate, String lockedReason, String diplexer, String diplexerPurpose, String masterSectorId, String at, String flag, String status, String nepSynch, String circleId) {
	        this.id = id;
	        this.lteCellId = lteCellId;
	        this.dateOnAir = dateOnAir;
	        this.cellName = cellName;
	        this.siteSubType = siteSubType;
	        this.antennaSharedWith2G = antennaSharedWith2G;
	        this.antennaManufacturer = antennaManufacturer;
	        this.antennaModel = antennaModel;
	        this.antennaGain = antennaGain;
	        this.beamWidth = beamWidth;
	        this.azimuth = azimuth;
	        this.antennaHeightAGL = antennaHeightAGL;
	        this.electricalTilt = electricalTilt;
	        this.mechanicalTilt = mechanicalTilt;
	        this.ret = ret;
	        this.feederSize = feederSize;
	        this.approximateFeederLength = approximateFeederLength;
	        this.tmaMha = tmaMha;
	        this.sharedSite = sharedSite;
	        this.remarks = remarks;
	        this.sectorStatus = sectorStatus;
	        this.sectorLockedDate = sectorLockedDate;
	        this.lockedReason = lockedReason;
	        this.diplexer = diplexer;
	        this.diplexerPurpose = diplexerPurpose;
	        this.masterSectorId = masterSectorId;
	        this.at = at;
	        this.flag = flag;
	        this.status = status;
	        this.nepSynch = nepSynch;
	        this.circleId = circleId;
	    }

		public String getId() {
			return id;
		}

		public void setId(String id ) {
			this.id = id;
		}

		public String getLteCellId() {
			return lteCellId;
		}

		public void setLteCellId(String lteCellId) {
			this.lteCellId = lteCellId;
		}

		public String getDateOnAir() {
			return dateOnAir;
		}

		public void setDateOnAir(String dateOnAir) {
			this.dateOnAir = dateOnAir;
		}

		public String getCellName() {
			return cellName;
		}

		public void setCellName(String cellName) {
			this.cellName = cellName;
		}

		public String getSiteSubType() {
			return siteSubType;
		}

		public void setSiteSubType(String siteSubType) {
			this.siteSubType = siteSubType;
		}

		public String getAntennaSharedWith2G() {
			return antennaSharedWith2G;
		}

		public void setAntennaSharedWith2G(String antennaSharedWith2G) {
			this.antennaSharedWith2G = antennaSharedWith2G;
		}

		public String getAntennaManufacturer() {
			return antennaManufacturer;
		}

		public void setAntennaManufacturer(String antennaManufacturer) {
			this.antennaManufacturer = antennaManufacturer;
		}

		public String getAntennaModel() {
			return antennaModel;
		}

		public void setAntennaModel(String antennaModel) {
			this.antennaModel = antennaModel;
		}

		public String getAntennaGain() {
			return antennaGain;
		}

		public void setAntennaGain(String antennaGain) {
			this.antennaGain = antennaGain;
		}

		public String getBeamWidth() {
			return beamWidth;
		}

		public void setBeamWidth(String beamWidth) {
			this.beamWidth = beamWidth;
		}

		public String getAzimuth() {
			return azimuth;
		}

		public void setAzimuth(String azimuth) {
			this.azimuth = azimuth;
		}

		public String getAntennaHeightAGL() {
			return antennaHeightAGL;
		}

		public void setAntennaHeightAGL(String antennaHeightAGL) {
			this.antennaHeightAGL = antennaHeightAGL;
		}

		public String getElectricalTilt() {
			return electricalTilt;
		}

		public void setElectricalTilt(String electricalTilt) {
			this.electricalTilt = electricalTilt;
		}

		public String getMechanicalTilt() {
			return mechanicalTilt;
		}

		public void setMechanicalTilt(String mechanicalTilt) {
			this.mechanicalTilt = mechanicalTilt;
		}

		public String getRet() {
			return ret;
		}

		public void setRet(String ret) {
			this.ret = ret;
		}

		public String getFeederSize() {
			return feederSize;
		}

		public void setFeederSize(String feederSize) {
			this.feederSize = feederSize;
		}

		public String getApproximateFeederLength() {
			return approximateFeederLength;
		}

		public void setApproximateFeederLength(String approximateFeederLength) {
			this.approximateFeederLength = approximateFeederLength;
		}

		public String getTmaMha() {
			return tmaMha;
		}

		public void setTmaMha(String tmaMha) {
			this.tmaMha = tmaMha;
		}

		public String getSharedSite() {
			return sharedSite;
		}

		public void setSharedSite(String sharedSite) {
			this.sharedSite = sharedSite;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public String getSectorStatus() {
			return sectorStatus;
		}

		public void setSectorStatus(String sectorStatus) {
			this.sectorStatus = sectorStatus;
		}

		public String getSectorLockedDate() {
			return sectorLockedDate;
		}

		public void setSectorLockedDate(String sectorLockedDate) {
			this.sectorLockedDate = sectorLockedDate;
		}

		public String getLockedReason() {
			return lockedReason;
		}

		public void setLockedReason(String lockedReason) {
			this.lockedReason = lockedReason;
		}

		public String getDiplexer() {
			return diplexer;
		}

		public void setDiplexer(String diplexer) {
			this.diplexer = diplexer;
		}

		public String getDiplexerPurpose() {
			return diplexerPurpose;
		}

		public void setDiplexerPurpose(String diplexerPurpose) {
			this.diplexerPurpose = diplexerPurpose;
		}

		public String getMasterSectorId() {
			return masterSectorId;
		}

		public void setMasterSectorId(String masterSectorId) {
			this.masterSectorId = masterSectorId;
		}

		public String getAt() {
			return at;
		}

		public void setAt(String at) {
			this.at = at;
		}

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getNepSynch() {
			return nepSynch;
		}

		public void setNepSynch(String nepSynch) {
			this.nepSynch = nepSynch;
		}

		public String getCircleId() {
			return circleId;
		}

		public void setCircleId(String circleId) {
			this.circleId = circleId;
		}

	}
