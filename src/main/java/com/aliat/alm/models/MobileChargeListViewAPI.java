package com.aliat.alm.models;

public class MobileChargeListViewAPI {
	
    private String agentNumber;
    private String date;
    private int vfrom;
    private int vto;
    
    
	public MobileChargeListViewAPI() {
		super();
		// TODO Auto-generated constructor stub
	}


	public MobileChargeListViewAPI(String agentNumber, String date, int vfrom, int vto) {
		super();
		this.agentNumber = agentNumber;
		this.date = date;
		this.vfrom = vfrom;
		this.vto = vto;
	}


	public String getAgentNumber() {
		return agentNumber;
	}


	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public int getVfrom() {
		return vfrom;
	}


	public void setVfrom(int vfrom) {
		this.vfrom = vfrom;
	}


	public int getVto() {
		return vto;
	}


	public void setVto(int vto) {
		this.vto = vto;
	}
    
    
    
}
