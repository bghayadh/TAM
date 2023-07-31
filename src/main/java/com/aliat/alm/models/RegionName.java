package com.aliat.alm.models;

public class RegionName {
	
	private String regionName;
	private String agentNumber;
	private String PinCode;
	private String versionNbr;
	
	
	public String getAgentNumber() {
		return agentNumber;
	}
	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}
	public String getPinCode() {
		return PinCode;
	}
	public void setPinCode(String pinCode) {
		PinCode = pinCode;
	}
	public String getRegionName() {
        return regionName;
		}
	public void setRegionName(String regionName) {
        this.regionName = regionName;
		}
	
 
	public String getVersionNbr() {
		return versionNbr;
	}
	public void setVersionNbr(String versionNbr) {
		this.versionNbr = versionNbr;
	}
	
	public RegionName(String regionName,String agentNumber,String PinCode,String versionNbr) {
		super();
		this.regionName=regionName;
		this.agentNumber=agentNumber;
		this.PinCode=PinCode;
		this.versionNbr=versionNbr;
		}
	
    public RegionName() {
        super();
        // TODO Auto-generated constructor stub
 }


}
