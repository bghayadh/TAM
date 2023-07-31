package com.aliat.alm.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class WorkOrderSourceParams {
	List<Map<String, Object>> Parameter1 = new ArrayList<Map<String, Object>>();
	
public WorkOrderSourceParams () {
		
	}

public WorkOrderSourceParams(List<Map<String, Object>> parameter1) {
	super();
	Parameter1 = parameter1;
}

public List<Map<String, Object>> getParameter1() {
	return Parameter1;
}

public void setParameter1(List<Map<String, Object>> parameter1) {
	Parameter1 = parameter1;
}




}
