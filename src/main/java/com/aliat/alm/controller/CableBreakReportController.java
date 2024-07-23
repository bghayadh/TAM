package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Logger;
import com.aliat.alm.models.CableBreakReport;
import java.util.stream.Collectors;


@Controller
public class CableBreakReportController {
	private static final Logger logger = Logger.getLogger(CableBreakReportController.class.getName());

	@Autowired
	Notify notifications;
	private Session session = null;
	@SuppressWarnings("rawtypes")
	Query query;
	Object[] result;
	private static StringWriter sw;
	private static String exceptionAsString;
	private static ObjectMapper mapper = new ObjectMapper();



	@RequestMapping(value = "/CableBreakReport", method = RequestMethod.GET)
	public String CableBreakReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {

			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {

				notifications.headerNotifications(session, model);
				try {
					
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in CableBreakReport due to \n " + exceptionAsString);
					logger.info("Error in CableBreakReport due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						
						session.close();
						
					}
				}
			}
		}
		return "Reports/CableBreakReport";
	}

	@RequestMapping(value = "/getBreakFiberCable", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBreakFiberCable(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			try {
				String requestValue = request.getParameter("requestValue");

				String str = "select distinct fibercableID,fibercableName from FiberCable where LOWER(fibercableID) like LOWER(:param1) or LOWER(fibercableName) like LOWER(:param1) ";

				query = session.createQuery(str);
				query.setParameter("param1", "%" + requestValue + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("FiberCableList", query.list());
			} catch (Exception e) {
				logger.info("Error in getBreakFiberCable while using autocomplete with error message: " + e);
				e.printStackTrace();

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
					
				}
			}
		}

		return rtn;
	}

	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/GenerateBreakPointReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateBreakPointReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String fiberCableID = request.getParameter("cableID");
		String pointLong = request.getParameter("pointLong");
		String pointLat= request.getParameter("pointLat");
		
		String sourceID="";
		String DestID="";
		int totalaffectd = 0;
	    int totalaffectdClients = 0;
	    int totalaffectdSites = 0;
		
		double breakPointLong = Double.parseDouble(pointLong);
		double breakPointLat = Double.parseDouble(pointLat);

		List<CableBreakReport> listAffectedClientSite = new ArrayList<CableBreakReport>();
		//List<Object[]> fiberAuxDataRelatedPath = new ArrayList<Object[]>();
		//List<Object[]> relatedPathCablesID = new ArrayList<Object[]>();
		//List<Object[]> relatedPathCablesList = new ArrayList<Object[]>();
		
		 List<String> srcDBs = new ArrayList<>();
		 List<String> dstDBs = new ArrayList<>();
		 List<String> junctionBeforeBreakingPt = new ArrayList<>();
		 List<String> junctionAfterBreakingPt = new ArrayList<>();
		 
		 List<String> affectedClients = new ArrayList<>();
		 List<String> affectedSites = new ArrayList<>();


		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {

			try {
				session = AlmDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {

					List<Object[]> fiberList = session.createNativeQuery(
							"SELECT A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR,A.NUMBER_OF_STRANDS,A.NUMBER_OF_TUBES FROM FIBER_CABLES A WHERE A.FIBER_CABLE_ID ='"+fiberCableID+ "' ").getResultList();
					rtn.put("fiberList", fiberList);
					
					
					List<Object[]> fiberAuxData = session.createNativeQuery(
							"SELECT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID,SEQ_SORTING FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID ='"
									+ fiberCableID + "' ORDER BY B.SEQ_SORTING ASC").getResultList();
					rtn.put("fiberAuxData", fiberAuxData);
					
					
					
					
					
					
				
					// This block aim to get the nearest auxiliary pt to the break pt 
					Object[] nearestPoint = null;
					int nearestAuxPtSeq=0;
					int breakingPtSeq=0;
					double minDistance = Double.MAX_VALUE;
					double nearestPointLong = 0; 
					double nearestPointLat = 0; 
					
					
					/*iterate over the auxiliary pt and get the distance between the break pt and each auxiliary pt
					then determine the nearest pt (nearestPoint) */
					for (Object[] auxPoint : fiberAuxData) {
					    // Assuming index 0 is longitude, index 1 is latitude in your Object[] array
					    String auxPointLongStr = (String) auxPoint[0];
					    String auxPointLatStr = (String) auxPoint[1];

					    // Parse the string values to double
					    double auxPointLong = Double.parseDouble(auxPointLongStr);
					    double auxPointLat = Double.parseDouble(auxPointLatStr);

					    // Calculate distance
					    double distance = haversine(auxPointLat, auxPointLong, breakPointLat, breakPointLong);

					    // Check if this point is nearest
					    if (distance < minDistance) {
					        minDistance = distance;
					        nearestPoint = auxPoint;
					        //get the seq of the nearest pt as int 
					        Object nearestPointSeq = nearestPoint[7];
					        BigDecimal nearestPointSeqDecimal = (BigDecimal) nearestPointSeq;
					        nearestAuxPtSeq = nearestPointSeqDecimal.intValue();
					        
					        nearestPointLong = auxPointLong;
					        nearestPointLat = auxPointLat;
					    }
					}
					
				
					
					//get the auxiliary pt that is just before the nearest pt based on SEQ_SORTING
					Object[]  previousAuxPt = (Object[]) session.createNativeQuery(
							"SELECT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.AUXILIARY_ID,B.SEQ_SORTING FROM FIBER_AUXILIARY_POINTS B "
							+ "WHERE B.FIBER_CABLE_ID ='"+ fiberCableID + "' AND B.SEQ_SORTING ='"+(nearestAuxPtSeq-1)+"'").getSingleResult();
					
					  
					   
					    String prAuxPointLongStr = (String) previousAuxPt[0];
					    String prAuxuxPointLatStr = (String) previousAuxPt[1];

					    // Parse the string values to double
					    double prAuxPointLong = Double.parseDouble(prAuxPointLongStr);
					    double prAuxPointLat = Double.parseDouble(prAuxuxPointLatStr);
					    
					    //Calculate distance between previous Aux pt and break pt 
					    double prvToBreakdistance = haversine(prAuxPointLat, prAuxPointLong, breakPointLat, breakPointLong);
					    //Calculate distance between previous Aux pt and nearest Aux pt
					    double prvTonearestdistance = haversine(prAuxPointLat, prAuxPointLong, nearestPointLat, nearestPointLong);
					   
					    
					    /*give the breaking pt Sequence as following:
					     * after we the get the distance between the previous auxiliary pt and breaking pt as 'prvToBreakdistance'
					     * and between the previous auxiliary pt and  the nearest auxiliary pt as 'prvTonearestdistance'.
					     * 
					     * if prvToBreakdistance is less than prvTonearestdistance this means that the breaking pt is just 
					     * 		before the nearest auxiliary pt and thus the seq of the breaking pt wil be the seq of previous auxiliary pt 
					     * else if prvToBreakdistance is greater than or equal prvTonearestdistance this mean the breaking pt is located 
					     * 		after the nearest auxiliary pt and thus the seq of the breaking pt wil be the seq of nearest auxiliary pt 
					     * */
					    
					    if(prvToBreakdistance<prvTonearestdistance ) {
					    	//seq of breaking pt is previous aux pt seq
					    	breakingPtSeq=nearestAuxPtSeq-1;
					    	
					    }
					    else {
					    	//seq of breaking pt is nearest aux pt seq
					    	breakingPtSeq=nearestAuxPtSeq;
					    	
					    }
					    
					   //get the source and destination of the cable
					    Object[]  fiberCableSrcDestID = (Object[]) session.createNativeQuery(
					    		 "SELECT SOURCE_ID,SOURCE_NAME,SOURCE_WARE_ID,DESTINATION_ID,DESTINATION_NAME,DESTINATION_WARE_ID FROM FIBER_CABLES "
							    + "WHERE "
							    + "FIBER_CABLE_ID ='"+fiberCableID+"'").getSingleResult();
					    
					    // this block aims to get all DBs elated to the source and destination 
					    if(fiberCableSrcDestID != null) {
					    
					    	 sourceID = (String) fiberCableSrcDestID[0];
					    	 DestID = (String) fiberCableSrcDestID[3];
					    	
					    	 //get the DBs related to the source
					    	if((!"null".equals(fiberCableSrcDestID[2]) && fiberCableSrcDestID[2] != null) || sourceID.startsWith("CUST_") ) {
					    		
					    		List<String> TempDBList = session.createNativeQuery(
										"SELECT DB_ID FROM DISTRIBUTION_BOARD "
										+ "WHERE "
										+ "SITE ='"+sourceID+"'").getResultList();
					    		
					    		for (String dbId  : TempDBList) {
					    		    srcDBs.add(dbId);    					    		
					    		}
								
					    	}
					    	else if(sourceID.toString().startsWith("DB_")) {
					    		
					    		srcDBs.add(sourceID);
					    		
					    	}
					    	
					    	
					    	 //get the DBs related to the destination 
					    	if((!"null".equals(fiberCableSrcDestID[5]) && fiberCableSrcDestID[5] != null) || DestID.startsWith("CUST_") ) {
					    		
					    		List<String> TempDBList = session.createNativeQuery(
										"SELECT DB_ID FROM DISTRIBUTION_BOARD "
										+ "WHERE "
										+ "SITE ='"+DestID+"'").getResultList();
					    		
					    		for (String dbId : TempDBList) {
					    			dstDBs.add(dbId);    					    		
					    		}
								
					    	}
					    	else if(DestID.toString().startsWith("DB_")) {
					    		dstDBs.add(DestID);
					    		
					    	}
					    	
					    }
					    
					    //get junctions from auxiliary pt table related to that cable as lists before and after break point 
					    List<Object[]> junctionListBeforBreakingPt = session.createNativeQuery(
								"SELECT AUXILIARY_POINT_ID FROM FIBER_AUXILIARY_POINTS "
								+ "WHERE "
								+ "FIBER_CABLE_ID ='"+fiberCableID+"' AND (AUXILIARY_POINT_NAME LIKE '%_J' OR AUXILIARY_POINT_ID like 'JCT%' OR AUXILIARY_POINT_ID like 'MH%' OR AUXILIARY_POINT_ID like 'HH%') AND SEQ_SORTING <= "+breakingPtSeq+"").getResultList();
					
					    
					    for (int i=0;i<junctionListBeforBreakingPt.size();i++) {
					    	String tempjJunctionId = String.valueOf(junctionListBeforBreakingPt.get(i));
					    	junctionBeforeBreakingPt.add(tempjJunctionId);
					    					    		
			    		}
					  
					    
					    List<Object[]> junctionListAfterBreakingPt = session.createNativeQuery(
								"SELECT AUXILIARY_POINT_ID FROM FIBER_AUXILIARY_POINTS "
								+ "WHERE "
								+ "FIBER_CABLE_ID ='"+fiberCableID+"' AND (AUXILIARY_POINT_NAME LIKE '%_J' OR AUXILIARY_POINT_ID like 'JCT%' OR AUXILIARY_POINT_ID like 'MH%' OR AUXILIARY_POINT_ID like 'HH%') AND SEQ_SORTING > "+breakingPtSeq+"").getResultList();
					
					    
					    for (int i=0;i<junctionListAfterBreakingPt.size();i++) {
					    	String tempjJunctionId = String.valueOf(junctionListAfterBreakingPt.get(i));
					    	junctionAfterBreakingPt.add(tempjJunctionId);
					    					    		
			    		}
					   
					    //get affected sites and client
					    String str = "SELECT DISTINCT BP_LOCATION_ID FROM DISTRIBUTION_BOARD_MAPPING "
					    		+ "where "
					    		+ "(BP_LOCATION_TYPE ='Customer' OR BP_LOCATION_TYPE ='Site') AND (BP_FIBER_ID='"+fiberCableID+"' OR FP_FIBER_ID='"+fiberCableID+"') "
					    		+ "AND (DB_ID IN (:param1)) "
					    		+ "AND  (FP_LOCATION_ID ='"+DestID+"' OR BP_LOCATION_ID ='"+DestID+"' OR FP_EQUIPMENT_ID ='"+DestID+"' OR BP_EQUIPMENT_ID ='"+DestID+"' OR FP_JUNCTION_ID IN (:param2) OR BP_JUNCTION_ID IN (:param2) ) "
					    		+ "UNION "
			    				+ "SELECT DISTINCT FP_LOCATION_ID FROM DISTRIBUTION_BOARD_MAPPING "
			    				+ "where "
			    				+ "(FP_LOCATION_TYPE ='Customer' OR FP_LOCATION_TYPE ='Site') AND (BP_FIBER_ID='"+fiberCableID+"' OR FP_FIBER_ID='"+fiberCableID+"') "
			    				+ "AND (DB_ID IN (:param1)) "
			    				+ "AND  (FP_LOCATION_ID ='"+DestID+"' OR BP_LOCATION_ID ='"+DestID+"' OR FP_EQUIPMENT_ID ='"+DestID+"' OR BP_EQUIPMENT_ID ='"+DestID+"' OR FP_JUNCTION_ID IN (:param2) OR BP_JUNCTION_ID IN (:param2) ) "
					  
					    
							    + "UNION "
							    +"SELECT DISTINCT BP_LOCATION_ID FROM DISTRIBUTION_BOARD_MAPPING "
					    		+ "where "
					    		+ "(BP_LOCATION_TYPE ='Customer' OR BP_LOCATION_TYPE ='Site') AND (BP_FIBER_ID='"+fiberCableID+"' OR FP_FIBER_ID='"+fiberCableID+"') "
					    		+ "AND (DB_ID IN (:param3)) "
								+ "AND  (FP_LOCATION_ID ='"+sourceID+"' OR BP_LOCATION_ID ='"+sourceID+"' OR FP_EQUIPMENT_ID ='"+sourceID+"' OR BP_EQUIPMENT_ID ='"+sourceID+"' OR FP_JUNCTION_ID IN (:param2) OR BP_JUNCTION_ID IN (:param4) ) "
					    		+ "UNION "
			    				+ "SELECT DISTINCT FP_LOCATION_ID FROM DISTRIBUTION_BOARD_MAPPING "
			    				+ "where "
			    				+ "(FP_LOCATION_TYPE ='Customer' OR FP_LOCATION_TYPE ='Site') AND (BP_FIBER_ID='"+fiberCableID+"' OR FP_FIBER_ID='"+fiberCableID+"') "
			    				+ "AND (DB_ID IN (:param3)) "
			    				+ "AND  (FP_LOCATION_ID ='"+sourceID+"' OR BP_LOCATION_ID ='"+sourceID+"' OR FP_EQUIPMENT_ID ='"+sourceID+"' OR BP_EQUIPMENT_ID ='"+sourceID+"' OR FP_JUNCTION_ID IN (:param2) OR BP_JUNCTION_ID IN (:param4) ) "
					    
					    
					    
					    
							    + "UNION "
							    +"SELECT DISTINCT LOCATION_ID_SIDE_A FROM JUNCTION_MAPPING "
			    				+ "where "
			    				+ "(LOCATION_TYPE_SIDE_A ='Customer' OR LOCATION_TYPE_SIDE_A ='Site') AND (FIBER_ID_SIDE_A='"+fiberCableID+"' OR FIBER_ID_SIDE_B='"+fiberCableID+"') "
			    				+ "AND (JCT_ID IN (:param4) OR PHYSICAL_LAYER_ID IN(:param4)) "
			    				+ "AND  (LOCATION_ID_SIDE_A ='"+DestID+"' OR LOCATION_ID_SIDE_B ='"+DestID+"' OR LOCATION_ID_SIDE_A IN (:param2) OR LOCATION_ID_SIDE_B IN (:param2)) "
			    				+ "UNION "
							    +"SELECT DISTINCT LOCATION_ID_SIDE_B FROM JUNCTION_MAPPING "
			    				+ "where "
			    				+ "(LOCATION_TYPE_SIDE_B ='Customer' OR LOCATION_TYPE_SIDE_B ='Site') AND (FIBER_ID_SIDE_A='"+fiberCableID+"' OR FIBER_ID_SIDE_B='"+fiberCableID+"') "
			    				+ "AND (JCT_ID IN (:param4) OR PHYSICAL_LAYER_ID IN(:param4)) "
			    				+ "AND  (LOCATION_ID_SIDE_A ='"+DestID+"' OR LOCATION_ID_SIDE_B ='"+DestID+"' OR LOCATION_ID_SIDE_A IN (:param2) OR LOCATION_ID_SIDE_B IN (:param2)) "
			    				
			    				
			    				
			    				 + "UNION "
							    +"SELECT DISTINCT LOCATION_ID_SIDE_A FROM JUNCTION_MAPPING "
			    				+ "where "
			    				+ "(LOCATION_TYPE_SIDE_A ='Customer' OR LOCATION_TYPE_SIDE_A ='Site') AND (FIBER_ID_SIDE_A='"+fiberCableID+"' OR FIBER_ID_SIDE_B='"+fiberCableID+"') "
			    				+ "AND (JCT_ID IN (:param2) OR PHYSICAL_LAYER_ID IN(:param2)) "
			    				+ "AND  (LOCATION_ID_SIDE_A ='"+sourceID+"' OR LOCATION_ID_SIDE_B ='"+sourceID+"' OR LOCATION_ID_SIDE_A IN (:param4) OR LOCATION_ID_SIDE_B IN (:param4)) "
			    				+ "UNION "
							    +"SELECT DISTINCT LOCATION_ID_SIDE_B FROM JUNCTION_MAPPING "
			    				+ "where "
			    				+ "(LOCATION_TYPE_SIDE_B ='Customer' OR LOCATION_TYPE_SIDE_B ='Site') AND (FIBER_ID_SIDE_A='"+fiberCableID+"' OR FIBER_ID_SIDE_B='"+fiberCableID+"') "
			    				+ "AND (JCT_ID IN (:param2) OR PHYSICAL_LAYER_ID IN(:param2)) "
			    				+ "AND  (LOCATION_ID_SIDE_A ='"+sourceID+"' OR LOCATION_ID_SIDE_B ='"+sourceID+"' OR LOCATION_ID_SIDE_A IN (:param4) OR LOCATION_ID_SIDE_B IN (:param4)) ";
					    
						    Query query = session.createNativeQuery(str);
						    query.setParameterList("param1", srcDBs);
						    query.setParameterList("param2", junctionAfterBreakingPt);
						    query.setParameterList("param3", dstDBs);
						    query.setParameterList("param4", junctionBeforeBreakingPt);
						    
						    List<String> affectedClientSite = query.getResultList();
					    
					    
					    //this query will be used in the next version to get all affected client and sites and all related info 
					    /*
					    String str11111 = "SELECT DISTINCT A.BP_LOCATION_ID as locationId,B.CUSTOMER_NAME as locationName,a.BP_LOCATION_Type as locationType,'' as wareId,B.LONGITUDE as locationLongitude,B.LATITUDE as locationLatitude,B.CUSTOMER_ID as showLocation,A.DB_ID || ':' || C.DB_LONGITUDE || ':' || C.DB_LATITUDE || ':' || C.DB_NAME as showElement FROM DISTRIBUTION_BOARD_MAPPING  LEFT JOIN CUSTOMER b ON A.BP_LOCATION_ID = B.CUSTOMER_ID LEFT JOIN DISTRIBUTION_BOARD C ON  A.DB_ID = C.DB_ID "
					    		+ "where "
					    		+ "(A.BP_LOCATION_TYPE ='Customer') AND (A.BP_FIBER_ID='"+fiberCableID+"' OR A.FP_FIBER_ID='"+fiberCableID+"') "
					    		+ "AND (A.DB_ID IN (:param1)) "
					    		+ "AND  (A.FP_LOCATION_ID ='"+DestID+"' OR A.BP_LOCATION_ID ='"+DestID+"' OR A.FP_EQUIPMENT_ID ='"+DestID+"' OR A.BP_EQUIPMENT_ID ='"+DestID+"' OR A.FP_JUNCTION_ID IN (:param2) OR A.BP_JUNCTION_ID IN (:param2) ) "
					    		+ "UNION "
					    		+"SELECT DISTINCT A.FP_LOCATION_ID as locationId,B.CUSTOMER_NAME as locationName,a.FP_LOCATION_Type as locationType,'' as wareId,B.LONGITUDE as locationLongitude,B.LATITUDE as locationLatitude,B.CUSTOMER_ID as showLocation,A.DB_ID || ':' || C.DB_LONGITUDE || ':' || C.DB_LATITUDE || ':' || C.DB_NAME as showElement FROM DISTRIBUTION_BOARD_MAPPING  LEFT JOIN CUSTOMER b ON A.FP_LOCATION_ID = B.CUSTOMER_ID LEFT JOIN DISTRIBUTION_BOARD C ON  A.DB_ID = C.DB_ID "
			    				//+ "SELECT DISTINCT FP_LOCATION_ID FROM DISTRIBUTION_BOARD_MAPPING "
			    				+ "where "
			    				+ "(A.FP_LOCATION_TYPE ='Customer') AND (A.BP_FIBER_ID='"+fiberCableID+"' OR A.FP_FIBER_ID='"+fiberCableID+"') "
			    				+ "AND (A.DB_ID IN (:param1)) "
			    				+ "AND  (A.FP_LOCATION_ID ='"+DestID+"' OR A.BP_LOCATION_ID ='"+DestID+"' OR A.FP_EQUIPMENT_ID ='"+DestID+"' OR A.BP_EQUIPMENT_ID ='"+DestID+"' OR A.FP_JUNCTION_ID IN (:param2) OR A.BP_JUNCTION_ID IN (:param2) ) "
					  
					    
							    + "UNION "
							    +"SELECT DISTINCT A.BP_LOCATION_ID as locationId,B.CUSTOMER_NAME as locationName,a.BP_LOCATION_Type as locationType,'' as wareId,B.LONGITUDE as locationLongitude,B.LATITUDE as locationLatitude,B.CUSTOMER_ID as showLocation,A.DB_ID || ':' || C.DB_LONGITUDE || ':' || C.DB_LATITUDE || ':' || C.DB_NAME as showElement FROM DISTRIBUTION_BOARD_MAPPING  LEFT JOIN CUSTOMER b ON A.BP_LOCATION_ID = B.CUSTOMER_ID LEFT JOIN DISTRIBUTION_BOARD C ON  A.DB_ID = C.DB_ID "
							    //+"SELECT DISTINCT BP_LOCATION_ID FROM DISTRIBUTION_BOARD_MAPPING "
					    		+ "where "
					    		+ "(A.BP_LOCATION_TYPE ='Customer') AND (A.BP_FIBER_ID='"+fiberCableID+"' OR A.FP_FIBER_ID='"+fiberCableID+"') "
					    		+ "AND (A.DB_ID IN (:param3)) "
								+ "AND  (A.FP_LOCATION_ID ='"+sourceID+"' OR A.BP_LOCATION_ID ='"+sourceID+"' OR A.FP_EQUIPMENT_ID ='"+sourceID+"' OR A.BP_EQUIPMENT_ID ='"+sourceID+"' OR A.FP_JUNCTION_ID IN (:param2) OR A.BP_JUNCTION_ID IN (:param4) ) "
					    		+ "UNION "
					    		+"SELECT DISTINCT A.FP_LOCATION_ID as locationId,B.CUSTOMER_NAME as locationName,a.FP_LOCATION_Type as locationType,'' as wareId,B.LONGITUDE as locationLongitude,B.LATITUDE as locationLatitude,B.CUSTOMER_ID as showLocation,A.DB_ID || ':' || C.DB_LONGITUDE || ':' || C.DB_LATITUDE || ':' || C.DB_NAME as showElement FROM DISTRIBUTION_BOARD_MAPPING  LEFT JOIN CUSTOMER b ON A.FP_LOCATION_ID = B.CUSTOMER_ID LEFT JOIN DISTRIBUTION_BOARD C ON  A.DB_ID = C.DB_ID "
			    				//+ "SELECT DISTINCT FP_LOCATION_ID FROM DISTRIBUTION_BOARD_MAPPING "
			    				+ "where "
			    				+ "(A.FP_LOCATION_TYPE ='Customer') AND (A.BP_FIBER_ID='"+fiberCableID+"' OR A.FP_FIBER_ID='"+fiberCableID+"') "
			    				+ "AND (A.DB_ID IN (:param3)) "
			    				+ "AND  (A.FP_LOCATION_ID ='"+sourceID+"' OR A.BP_LOCATION_ID ='"+sourceID+"' OR A.FP_EQUIPMENT_ID ='"+sourceID+"' OR A.BP_EQUIPMENT_ID ='"+sourceID+"' OR A.FP_JUNCTION_ID IN (:param2) OR A.BP_JUNCTION_ID IN (:param4) ) "
					    //////////////////////////////////////////////////////77
			    				
			    				+ "SELECT DISTINCT A.BP_LOCATION_ID as locationId,B.WARE_NAME as locationName,a.BP_LOCATION_Type as locationType,B.WARE_ID as wareId,B.LONGITUDE as locationLongitude,B.LATITUDE as locationLatitude,B.WARE_ID as showLocation,A.DB_ID || ':' || C.DB_LONGITUDE || ':' || C.DB_LATITUDE || ':' || C.DB_NAME as showElement FROM DISTRIBUTION_BOARD_MAPPING  LEFT JOIN WAREHOUSE b ON A.BP_LOCATION_ID = B.SITE_ID LEFT JOIN DISTRIBUTION_BOARD C ON  A.DB_ID = C.DB_ID "
					    		+ "where "
					    		+ "(A.BP_LOCATION_TYPE ='Customer') AND (A.BP_FIBER_ID='"+fiberCableID+"' OR A.FP_FIBER_ID='"+fiberCableID+"') "
					    		+ "AND (A.DB_ID IN (:param1)) "
					    		+ "AND  (A.FP_LOCATION_ID ='"+DestID+"' OR A.BP_LOCATION_ID ='"+DestID+"' OR A.FP_EQUIPMENT_ID ='"+DestID+"' OR A.BP_EQUIPMENT_ID ='"+DestID+"' OR A.FP_JUNCTION_ID IN (:param2) OR A.BP_JUNCTION_ID IN (:param2) ) "
					    		+ "UNION "
					    		+ "SELECT DISTINCT A.FP_LOCATION_ID as locationId,B.WARE_NAME as locationName,a.FP_LOCATION_Type as locationType,B.WARE_ID as wareId,B.LONGITUDE as locationLongitude,B.LATITUDE as locationLatitude,B.WARE_ID as showLocation,A.DB_ID || ':' || C.DB_LONGITUDE || ':' || C.DB_LATITUDE || ':' || C.DB_NAME as showElement FROM DISTRIBUTION_BOARD_MAPPING  LEFT JOIN WAREHOUSE b ON A.FP_LOCATION_ID = B.SITE_ID LEFT JOIN DISTRIBUTION_BOARD C ON  A.DB_ID = C.DB_ID "
			    				//+ "SELECT DISTINCT FP_LOCATION_ID FROM DISTRIBUTION_BOARD_MAPPING "
			    				+ "where "
			    				+ "(A.FP_LOCATION_TYPE ='Customer') AND (A.BP_FIBER_ID='"+fiberCableID+"' OR A.FP_FIBER_ID='"+fiberCableID+"') "
			    				+ "AND (A.DB_ID IN (:param1)) "
			    				+ "AND  (A.FP_LOCATION_ID ='"+DestID+"' OR A.BP_LOCATION_ID ='"+DestID+"' OR A.FP_EQUIPMENT_ID ='"+DestID+"' OR A.BP_EQUIPMENT_ID ='"+DestID+"' OR A.FP_JUNCTION_ID IN (:param2) OR A.BP_JUNCTION_ID IN (:param2) ) "
					  
					    
							    + "UNION "
							    + "SELECT DISTINCT A.BP_LOCATION_ID as locationId,B.WARE_NAME as locationName,a.BP_LOCATION_Type as locationType,B.WARE_ID as wareId,B.LONGITUDE as locationLongitude,B.LATITUDE as locationLatitude,B.WARE_ID as showLocation,A.DB_ID || ':' || C.DB_LONGITUDE || ':' || C.DB_LATITUDE || ':' || C.DB_NAME as showElement FROM DISTRIBUTION_BOARD_MAPPING  LEFT JOIN WAREHOUSE b ON A.BP_LOCATION_ID = B.SITE_ID LEFT JOIN DISTRIBUTION_BOARD C ON  A.DB_ID = C.DB_ID "							    
							  //+"SELECT DISTINCT BP_LOCATION_ID FROM DISTRIBUTION_BOARD_MAPPING "
							    + "where "
					    		+ "(A.BP_LOCATION_TYPE ='Customer') AND (A.BP_FIBER_ID='"+fiberCableID+"' OR A.FP_FIBER_ID='"+fiberCableID+"') "
					    		+ "AND (A.DB_ID IN (:param3)) "
								+ "AND  (A.FP_LOCATION_ID ='"+sourceID+"' OR A.BP_LOCATION_ID ='"+sourceID+"' OR A.FP_EQUIPMENT_ID ='"+sourceID+"' OR A.BP_EQUIPMENT_ID ='"+sourceID+"' OR A.FP_JUNCTION_ID IN (:param2) OR A.BP_JUNCTION_ID IN (:param4) ) "
					    		+ "UNION "
					    		+ "SELECT DISTINCT A.FP_LOCATION_ID as locationId,B.WARE_NAME as locationName,a.FP_LOCATION_Type as locationType,B.WARE_ID as wareId,B.LONGITUDE as locationLongitude,B.LATITUDE as locationLatitude,B.WARE_ID as showLocation,A.DB_ID || ':' || C.DB_LONGITUDE || ':' || C.DB_LATITUDE || ':' || C.DB_NAME as showElement FROM DISTRIBUTION_BOARD_MAPPING  LEFT JOIN WAREHOUSE b ON A.FP_LOCATION_ID = B.SITE_ID LEFT JOIN DISTRIBUTION_BOARD C ON  A.DB_ID = C.DB_ID "
			    				//+ "SELECT DISTINCT FP_LOCATION_ID FROM DISTRIBUTION_BOARD_MAPPING "
			    				+ "where "
			    				+ "(A.FP_LOCATION_TYPE ='Customer') AND (A.BP_FIBER_ID='"+fiberCableID+"' OR A.FP_FIBER_ID='"+fiberCableID+"') "
			    				+ "AND (A.DB_ID IN (:param3)) "
			    				+ "AND  (A.FP_LOCATION_ID ='"+sourceID+"' OR A.BP_LOCATION_ID ='"+sourceID+"' OR A.FP_EQUIPMENT_ID ='"+sourceID+"' OR A.BP_EQUIPMENT_ID ='"+sourceID+"' OR A.FP_JUNCTION_ID IN (:param2) OR A.BP_JUNCTION_ID IN (:param4) ) "
			    				
			    				
			    				
			    				////////////////////////////////////////////////////////
					    
					    
					    
							    + "UNION "
							    +"SELECT DISTINCT LOCATION_ID_SIDE_A FROM JUNCTION_MAPPING "
			    				+ "where "
			    				+ "(LOCATION_TYPE_SIDE_A ='Customer' OR LOCATION_TYPE_SIDE_A ='Site') AND (FIBER_ID_SIDE_A='"+fiberCableID+"' OR FIBER_ID_SIDE_B='"+fiberCableID+"') "
			    				+ "AND (JCT_ID IN (:param4) OR PHYSICAL_LAYER_ID IN(:param4)) "
			    				+ "AND  (LOCATION_ID_SIDE_A ='"+DestID+"' OR LOCATION_ID_SIDE_B ='"+DestID+"' OR LOCATION_ID_SIDE_A IN (:param2) OR LOCATION_ID_SIDE_B IN (:param2)) "
			    				+ "UNION "
							    +"SELECT DISTINCT LOCATION_ID_SIDE_B FROM JUNCTION_MAPPING "
			    				+ "where "
			    				+ "(LOCATION_TYPE_SIDE_B ='Customer' OR LOCATION_TYPE_SIDE_B ='Site') AND (FIBER_ID_SIDE_A='"+fiberCableID+"' OR FIBER_ID_SIDE_B='"+fiberCableID+"') "
			    				+ "AND (JCT_ID IN (:param4) OR PHYSICAL_LAYER_ID IN(:param4)) "
			    				+ "AND  (LOCATION_ID_SIDE_A ='"+DestID+"' OR LOCATION_ID_SIDE_B ='"+DestID+"' OR LOCATION_ID_SIDE_A IN (:param2) OR LOCATION_ID_SIDE_B IN (:param2)) "
			    				
			    				
			    				
			    				 + "UNION "
							    +"SELECT DISTINCT LOCATION_ID_SIDE_A FROM JUNCTION_MAPPING "
			    				+ "where "
			    				+ "(LOCATION_TYPE_SIDE_A ='Customer' OR LOCATION_TYPE_SIDE_A ='Site') AND (FIBER_ID_SIDE_A='"+fiberCableID+"' OR FIBER_ID_SIDE_B='"+fiberCableID+"') "
			    				+ "AND (JCT_ID IN (:param2) OR PHYSICAL_LAYER_ID IN(:param2)) "
			    				+ "AND  (LOCATION_ID_SIDE_A ='"+sourceID+"' OR LOCATION_ID_SIDE_B ='"+sourceID+"' OR LOCATION_ID_SIDE_A IN (:param4) OR LOCATION_ID_SIDE_B IN (:param4)) "
			    				+ "UNION "
							    +"SELECT DISTINCT LOCATION_ID_SIDE_B FROM JUNCTION_MAPPING "
			    				+ "where "
			    				+ "(LOCATION_TYPE_SIDE_B ='Customer' OR LOCATION_TYPE_SIDE_B ='Site') AND (FIBER_ID_SIDE_A='"+fiberCableID+"' OR FIBER_ID_SIDE_B='"+fiberCableID+"') "
			    				+ "AND (JCT_ID IN (:param2) OR PHYSICAL_LAYER_ID IN(:param2)) "
			    				+ "AND  (LOCATION_ID_SIDE_A ='"+sourceID+"' OR LOCATION_ID_SIDE_B ='"+sourceID+"' OR LOCATION_ID_SIDE_A IN (:param4) OR LOCATION_ID_SIDE_B IN (:param4)) ";
					    */
					    
					    
					    
					    
					    
					    
					    for (String  row : affectedClientSite) {
					        String tempAfftectedLocation = (String) row; // Assuming BP_LOCATION_ID is the first column
					        if(tempAfftectedLocation.startsWith("CUST_")) {
					        	affectedClients.add(tempAfftectedLocation);
						    	
						    }
					        else {
					        	affectedSites.add(tempAfftectedLocation);
					        }
					      
					    }
					    
					    //get all needed data of the affected site and client
					    String  affectedstr = "SELECT CUSTOMER_ID as locationId, CUSTOMER_NAME as locationName, 'Customer' as locationType, '' as wareId,LONGITUDE as locationLongitude,LATITUDE as locationLatitude,CUSTOMER_ID as showLocation,'' as showElement " +
					             "FROM CUSTOMER " +
					             "WHERE CUSTOMER_ID IN (:Param5) " +
					             "UNION " +
					             "SELECT SITE_ID as locationId, WARE_NAME as locationName, 'Site' as locationType, WARE_ID as wareId, LONGITUDE as locationLongitude,LATITUDE as locationLatitude,SITE_ID as showLocation,'' as showElement " +
					             "FROM WAREHOUSE " +
					             "WHERE SITE_ID IN (:Param6)";

					    query = session.createNativeQuery(affectedstr);
					    query.setParameter("Param5", affectedClients);
						query.setParameter("Param6", affectedSites);
						
					    listAffectedClientSite = ((NativeQuery<CableBreakReport>) query).addScalar("locationId").addScalar("locationName").addScalar("locationType").addScalar("wareId")
								.addScalar("locationLongitude").addScalar("locationLatitude").addScalar("showLocation").addScalar("showElement").setResultTransformer(Transformers.aliasToBean(CableBreakReport.class))
								.list();
					    
					     
					     totalaffectdClients = affectedClients.size();
					     totalaffectdSites = affectedSites.size();
					     totalaffectd = totalaffectdClients+totalaffectdSites;
						
						rtn.put("listAffectedClientSite", listAffectedClientSite);
						rtn.put("totalAffectd", totalaffectd);
						rtn.put("totalAffectdClients", totalaffectdClients);
						rtn.put("totalAffectdSites", totalaffectdSites);

					   
					    
					    
					    
					    
					    
					    
					    
					    /*for testing purpose -to be deleted-
					     * 
					     
					      if (previousAuxPt != null) {
						   for (int i = 0; i < previousAuxPt.length; i++) {
						        System.out.println("Elementttttttt " + i + ": " + previousAuxPt[i]);
						    }

					    }
					     
					     if (nearestPoint != null) {
						    System.out.println("Nearest Point Details:");
						    for (int i = 0; i < nearestPoint.length; i++) {
						        System.out.println("Element " + i + ": " + nearestPoint[i]);
						    }
						} else {
						    System.out.println("Nearest point not found.");
						}
					
					     
					     for (String  row : affectedClientSite) {
					        String bpLocationId = (String) row; // Assuming BP_LOCATION_ID is the first column
					       // Assuming FP_LOCATION_ID is the second column
					        
					        // Print or process each row's data
					        System.out.println("BP_LOCATION_ID: " + bpLocationId);
					    }
					    
					     for (int i=0;i<junctionBeforeBreakingPt.size();i++) {
					    	
					    	System.out.println("junctionBeforeBreakingPt "+ junctionBeforeBreakingPt.get(i)); 					    		
			    		}
			    		
			    		
					     for (int i=0;i<junctionAfterBreakingPt.size();i++) {
					    	
					    	//System.out.println("junctionAfterBreakingPt "+ junctionAfterBreakingPt.get(i)); 					    		
			    		}
					     
					     for (int i=0;i<dstDBs.size();i++) {
						    	
						    	System.out.println("dstDBs "+ dstDBs.get(i)); 					    		
				    		}
					     
					     for (int i=0;i<srcDBs.size();i++) {
						    	
						    	System.out.println("srcDBs "+ srcDBs.get(i)); 					    		
				    		}
					   
					     System.out.println("srcId "+sourceID);
					     System.out.println("DestID "+DestID);*/
					    
					  
				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GenerateBreakPointReport due to \n " + exceptionAsString);
				logger.info("Error in GenerateBreakPointReport due to \n " + exceptionAsString);
			} 
			finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}
	
	
	static double haversine(double latitude, double longitude, double pointLat, double pointLong) {

		// distance between latitudes and longitudes
		double dLat = Math.toRadians(pointLat - latitude);
		double dLon = Math.toRadians(pointLong - longitude);

		// convert to radians
		latitude = Math.toRadians(latitude);
		pointLat = Math.toRadians(pointLat);

		// apply formulae
		double a = Math.pow(Math.sin(dLat / 2), 2)
						+ Math.pow(Math.sin(dLon / 2), 2) * Math.cos(latitude) * Math.cos(pointLat);
		double rad = 6371;
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;

	}
	
	

}