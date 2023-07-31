package com.aliat.alm.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ANTENNA_MAKE_MODEL")
public class AntennaMakeModel {
	
	@Id
	@Column(name = "ID", nullable = false)
	private String antenaID;
	
	@Column(name = "ANTENNA_MAKE")
	private String antennaMake;
	
	@Column(name = "ANTENNA_MODEL")
	private String antennaModel;
	
	@Column(name = "ANTENNA_GAIN")
	private String antennaGain;
	
	@Column(name = "ANTENNA_BEAM_WIDTH")
	private String antennaBeamWidth;
	
	public AntennaMakeModel() {	
	}
	
	public AntennaMakeModel(String antenaID, String antennaMake , String antennaModel , String antennaGain , String antennaBeamWidth ) {
		super();
		this.antenaID = antenaID;
		this.antennaMake = antennaMake;
		this.antennaModel = antennaModel;
		this.antennaGain = antennaGain;
		this.antennaBeamWidth = antennaBeamWidth;
		}

	public String getAntenaID() {
		return antenaID;
	}

	public void setAntenaID(String antenaID) {
		this.antenaID = antenaID;
	}

	public String getAntennaMake() {
		return antennaMake;
	}

	public void setAntennaMake(String antennaMake) {
		this.antennaMake = antennaMake;
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

	public String getAntennaBeamWidth() {
		return antennaBeamWidth;
	}

	public void setAntennaBeamWidth(String antennaBeamWidth) {
		this.antennaBeamWidth = antennaBeamWidth;
	}
	
	
}
