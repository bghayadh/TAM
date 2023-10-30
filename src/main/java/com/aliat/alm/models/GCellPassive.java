package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CELL_PASSIVE_2G")
public class GCellPassive {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "CELL_ID", nullable = false)
    private String cellId;

    @Column(name = "DATE_ON_AIR")
    private String dateOnAir;

    @Column(name = "CELLNAME")
    private String cellName;

    @Column(name = "SITE_SUB_TYPE")
    private String siteSubType;

    @Column(name = "MODE_OF_OPERATION")
    private String modeOfOperation;

    @Column(name = "ANTENNA_SHARED_WITH_4G")
    private String antennaSharedWith4G;

    @Column(name = "GSM_ANTENNA_1_MANUFACTURER")
    private String gsmAntenna1Manufacturer;

    @Column(name = "GSM_ANTENNA_MODEL_1")
    private String gsmAntennaModel1;

    @Column(name = "GSM_ANTENNA_2_MANUFACTURER")
    private String gsmAntenna2Manufacturer;

    @Column(name = "GSM_ANTENNA_MODEL_2")
    private String gsmAntennaModel2;

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

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "SECTOR_STATUS")
    private String sectorStatus;

    @Column(name = "SECTOR_LOCKED_DATE")
    private String sectorLockedDate;

    @Column(name = "LOCKED_REASON")
    private String lockedReason;

    @Column(name = "AT")
    private String at;

    @Column(name = "MASTER_SECTOR_ID")
    private String masterSectorId;

    @Column(name = "FLAG")
    private String flag;

    @Column(name = "STATUS", columnDefinition = "CHAR(1) DEFAULT '0'")
    private String status;

    @Column(name = "NEP_SYNCH", columnDefinition = "CHAR(1)")
    private String nepSynch;

    @Column(name = "CIRCLE_ID", length = 30)
    private String circleId;

    public GCellPassive() {
    }

    public GCellPassive(String id, String cellId, String dateOnAir, String cellName, String siteSubType,
            String modeOfOperation, String antennaSharedWith4G, String gsmAntenna1Manufacturer,
            String gsmAntennaModel1, String gsmAntenna2Manufacturer, String gsmAntennaModel2, String antennaGain,
            String beamWidth, String azimuth, String antennaHeightAGL, String electricalTilt, String mechanicalTilt,
            String ret, String feederSize, String approximateFeederLength, String tmaMha, String remarks,
            String sectorStatus, String sectorLockedDate, String lockedReason, String at, String masterSectorId,
            String flag, String status, String nepSynch, String circleId) {
        this.id = id;
        this.cellId = cellId;
        this.dateOnAir = dateOnAir;
        this.cellName = cellName;
        this.siteSubType = siteSubType;
        this.modeOfOperation = modeOfOperation;
        this.antennaSharedWith4G = antennaSharedWith4G;
        this.gsmAntenna1Manufacturer = gsmAntenna1Manufacturer;
        this.gsmAntennaModel1 = gsmAntennaModel1;
        this.gsmAntenna2Manufacturer = gsmAntenna2Manufacturer;
        this.gsmAntennaModel2 = gsmAntennaModel2;
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
        this.remarks = remarks;
        this.sectorStatus = sectorStatus;
        this.sectorLockedDate = sectorLockedDate;
        this.lockedReason = lockedReason;
        this.at = at;
        this.masterSectorId = masterSectorId;
        this.flag = flag;
        this.status = status;
        this.nepSynch = nepSynch;
        this.circleId = circleId;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
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

	public String getModeOfOperation() {
		return modeOfOperation;
	}

	public void setModeOfOperation(String modeOfOperation) {
		this.modeOfOperation = modeOfOperation;
	}

	public String getAntennaSharedWith4G() {
		return antennaSharedWith4G;
	}

	public void setAntennaSharedWith4G(String antennaSharedWith4G) {
		this.antennaSharedWith4G = antennaSharedWith4G;
	}

	public String getGsmAntenna1Manufacturer() {
		return gsmAntenna1Manufacturer;
	}

	public void setGsmAntenna1Manufacturer(String gsmAntenna1Manufacturer) {
		this.gsmAntenna1Manufacturer = gsmAntenna1Manufacturer;
	}

	public String getGsmAntennaModel1() {
		return gsmAntennaModel1;
	}

	public void setGsmAntennaModel1(String gsmAntennaModel1) {
		this.gsmAntennaModel1 = gsmAntennaModel1;
	}

	public String getGsmAntenna2Manufacturer() {
		return gsmAntenna2Manufacturer;
	}

	public void setGsmAntenna2Manufacturer(String gsmAntenna2Manufacturer) {
		this.gsmAntenna2Manufacturer = gsmAntenna2Manufacturer;
	}

	public String getGsmAntennaModel2() {
		return gsmAntennaModel2;
	}

	public void setGsmAntennaModel2(String gsmAntennaModel2) {
		this.gsmAntennaModel2 = gsmAntennaModel2;
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

	public String getAt() {
		return at;
	}

	public void setAt(String at) {
		this.at = at;
	}

	public String getMasterSectorId() {
		return masterSectorId;
	}

	public void setMasterSectorId(String masterSectorId) {
		this.masterSectorId = masterSectorId;
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
