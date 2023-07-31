package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.AssetLifeCycle_Ledger;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AssetLedgerController {

	private static final Logger logger = Logger.getLogger(AssetLedgerController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	
	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;
	
	/*@Autowired
	AppConfig appConfig;
	
	private static Session session = null;
	private static Transaction tx = null;
	//private static ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(AssetLedgerController.class);
	private static StringWriter sw;
	private static String exceptionAsString;*/
	
@SuppressWarnings("unchecked")	
@RequestMapping(value = "/AssetLedger", method = RequestMethod.GET)
	public String AssetLifeCycle(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) 
		 {
	
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {

				List<String> ALCEmptyArray = new ArrayList<String>();
				model.addAttribute("ALCArrayList", ALCEmptyArray);
				
			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "AssetLifeCycle/AssetLedger";
	}

//Generate of the map
@RequestMapping(value ="/GetAlcLedgerDataGIS", method = RequestMethod.GET)
@ResponseBody public Map<String, Object> GetAlcLedgerDataGIS(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) { 
				
Map<String, Object> rtn = new LinkedHashMap<>(); 
if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	rtn.put("Login", "redirect:/");
	return rtn;
}	   
session = almsessions.getSession();

if (session != null && session.isOpen()) {
	tx = session.beginTransaction();
	
	try {			
					
		   String start_Date = request.getParameter("startDate");
		   String end_Date = request.getParameter("endDate");	
		   String itemCode = request.getParameter("itemCode");	
		   String warehouseID = request.getParameter("warehouse");	
		   String supplierID = request.getParameter("supplier");	
		   String voucherType = request.getParameter("voucherType");	
		   String voucherNb = request.getParameter("voucherNb");	
		   String siteId = request.getParameter("siteId");	
		   String siteName = request.getParameter("siteName");	
		   String itemPartNo = request.getParameter("itemPartNo");	
		   String itemModel = request.getParameter("itemModel");	
		   String checkedValue = request.getParameter("checkedValue");
		   Query balanceValQuery = null;		   
		   DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");			 
		   Timestamp startDate;
		   Timestamp endDate;
		   	 
		   startDate = new Timestamp(formatter.parse(start_Date).getTime());
		   endDate = new Timestamp(formatter.parse(end_Date).getTime());
		   	
		if (StringUtils.equalsIgnoreCase(voucherType,null)||StringUtils.equalsIgnoreCase(voucherType,"null"))  {
			 voucherType="";
			 voucherNb="";			   
					  
		}
		startDate = new Timestamp(formatter.parse(start_Date).getTime());
		endDate = new Timestamp(formatter.parse(end_Date).getTime());
					     
		if (StringUtils.equalsIgnoreCase(checkedValue,"ledger"))  {
			//Show all sites
	    	if (StringUtils.equalsIgnoreCase(siteId,"") && StringUtils.equalsIgnoreCase(itemCode,"") && StringUtils.equalsIgnoreCase(warehouseID,"")&& StringUtils.equalsIgnoreCase(supplierID,"") 
	    		&& StringUtils.equalsIgnoreCase(siteName,"") && StringUtils.equalsIgnoreCase(itemPartNo,"") && StringUtils.equalsIgnoreCase(itemModel,"") ) {
	    		
	    		balanceValQuery=session.createSQLQuery("SELECT DISTINCT A.SITE_ID,A.WARE_NAME,sum(A.BALANCE_QTY),sum(A.BALANCE_VALUE),B.LONGITUDE,B.LATITUDE FROM asset_life_cycle A inner join warehouse B on A.SITE_ID=B.SITE_ID "
	    				+ " WHERE A.CREATION_DATE >= :param1 and A.CREATION_DATE <= :param2 AND COALESCE(A.VOUCHER_TYPE,' ') like '%"+voucherType+"%' AND COALESCE(A.VOUCHER_NB,' ') like '%"+voucherNb+"%' GROUP BY A.SITE_ID,A.WARE_NAME,B.LONGITUDE,B.LATITUDE ");
				    	
	    	}		    	
	    	//Site only is not empty
	    	else if ( (!StringUtils.equalsIgnoreCase(siteId,"") || !StringUtils.equalsIgnoreCase(warehouseID,"") || !StringUtils.equalsIgnoreCase(siteName,"") ) && StringUtils.equalsIgnoreCase(itemCode,"") && StringUtils.equalsIgnoreCase(supplierID,"") 
		    		 && StringUtils.equalsIgnoreCase(itemPartNo,"") && StringUtils.equalsIgnoreCase(itemModel,"") ) {
		    					    	
	    		 balanceValQuery=session.createSQLQuery("SELECT DISTINCT A.SITE_ID,A.WARE_NAME,SUM(A.BALANCE_QTY),SUM(A.BALANCE_VALUE),B.LONGITUDE,B.LATITUDE FROM asset_life_cycle A inner join warehouse B on A.SITE_ID=B.SITE_ID"
					    	+ " WHERE A.CREATION_DATE >= :param1 and A.CREATION_DATE <= :param2 AND (A.SITE_ID like '%"+siteId+"%' or A.WARE_NAME like '%"+siteName+"%' or A.WAREHOUSE like '%"+warehouseID+"%') AND A.SITE_ID=B.SITE_ID  "
					    			+ " AND COALESCE(A.VOUCHER_TYPE,' ') like '%"+voucherType+"%' AND COALESCE(A.VOUCHER_NB,' ') like '%"+voucherNb+"%' GROUP BY A.SITE_ID,A.WARE_NAME,B.LONGITUDE,B.LATITUDE ");

	    	}		    	
	    	//Item only is not empty
	    	else if ( !StringUtils.equalsIgnoreCase(itemCode,"")  && StringUtils.equalsIgnoreCase(siteId,"") && StringUtils.equalsIgnoreCase(warehouseID,"")&& StringUtils.equalsIgnoreCase(supplierID,"")
	    			&& StringUtils.equalsIgnoreCase(siteName,"") ) {
	    		
	    		balanceValQuery=session.createSQLQuery("SELECT DISTINCT A.SITE_ID,A.WARE_NAME,SUM(A.BALANCE_QTY),SUM(A.BALANCE_VALUE),B.LONGITUDE,B.LATITUDE FROM asset_life_cycle A inner join warehouse B on A.SITE_ID=B.SITE_ID"
	    				+ " WHERE A.CREATION_DATE >= :param1 and A.CREATION_DATE <= :param2 AND A.ITEM_CODE like '%"+itemCode+"%' "
	    						+ " AND COALESCE(A.VOUCHER_TYPE,' ') like '%"+voucherType+"%' AND COALESCE(A.VOUCHER_NB,' ') like '%"+voucherNb+"%' GROUP BY A.SITE_ID,A.WARE_NAME,B.LONGITUDE,B.LATITUDE");

	    	}
	    	//Supplier only is not empty
	    	else if ( !StringUtils.equalsIgnoreCase(supplierID,"") && StringUtils.equalsIgnoreCase(siteId,"") && StringUtils.equalsIgnoreCase(itemCode,"") && StringUtils.equalsIgnoreCase(warehouseID,"") 
		    		&& StringUtils.equalsIgnoreCase(siteName,"") && StringUtils.equalsIgnoreCase(itemPartNo,"") && StringUtils.equalsIgnoreCase(itemModel,"") ) {
	    	
	    		 balanceValQuery=session.createSQLQuery("SELECT DISTINCT A.SITE_ID,A.WARE_NAME,sum(A.BALANCE_QTY),sum(A.BALANCE_VALUE),B.LONGITUDE,B.LATITUDE FROM asset_life_cycle A inner join warehouse B on A.SITE_ID=B.SITE_ID"
					    	+ " WHERE A.CREATION_DATE >= :param1 and A.CREATION_DATE <= :param2 AND A.SUPPLIER_ID like '%"+supplierID+"%' "
					    			+ " AND COALESCE(A.VOUCHER_TYPE,' ') like '%"+voucherType+"%' AND COALESCE(A.VOUCHER_NB,' ') like '%"+voucherNb+"%' GROUP BY A.SITE_ID,A.WARE_NAME,B.LONGITUDE,B.LATITUDE ");

	    	}
	    	else {
	    		//Item && Site not empty
		    	 if ( (!StringUtils.equalsIgnoreCase(itemCode,"") || !StringUtils.equalsIgnoreCase(itemPartNo,"") || !StringUtils.equalsIgnoreCase(itemModel,"")) && (!StringUtils.equalsIgnoreCase(siteId,"")  
		    			 || !StringUtils.equalsIgnoreCase(warehouseID,"") || !StringUtils.equalsIgnoreCase(siteName,"") ) && StringUtils.equalsIgnoreCase(supplierID,"") ) {
		    	 
		    		  balanceValQuery=session.createSQLQuery("SELECT DISTINCT A.SITE_ID,A.WARE_NAME,sum(A.BALANCE_QTY),sum(A.BALANCE_VALUE),B.LONGITUDE,B.LATITUDE FROM asset_life_cycle A inner join warehouse B on A.SITE_ID=B.SITE_ID"
						    	+ " WHERE A.CREATION_DATE >= :param1 and A.CREATION_DATE <= :param2 AND A.ITEM_CODE like '%"+itemCode+"%' AND (A.SITE_ID like '%"+siteId+"%' or A.WARE_NAME like '%"+siteName+"%' or A.WAREHOUSE like '%"+warehouseID+"%')"
						    			+ " AND COALESCE(A.VOUCHER_TYPE,' ') like '%"+voucherType+"%' AND COALESCE(A.VOUCHER_NB,' ') like '%"+voucherNb+"%' GROUP BY A.SITE_ID,A.WARE_NAME,B.LONGITUDE,B.LATITUDE ");

		    	 }
		    	//Item && Supplier not empty
		    	 else if ( (!StringUtils.equalsIgnoreCase(itemCode,"") || !StringUtils.equalsIgnoreCase(itemPartNo,"") || !StringUtils.equalsIgnoreCase(itemModel,"") ) && StringUtils.equalsIgnoreCase(siteId,"")  && StringUtils.equalsIgnoreCase(warehouseID,"") 
		    			 && StringUtils.equalsIgnoreCase(siteName,"") && !StringUtils.equalsIgnoreCase(supplierID,"") ) {
		    	 
		    		 balanceValQuery=session.createSQLQuery("SELECT DISTINCT A.SITE_ID,A.WARE_NAME,sum(A.BALANCE_QTY),sum(A.BALANCE_VALUE),B.LONGITUDE,B.LATITUDE FROM asset_life_cycle A inner join warehouse B on A.SITE_ID=B.SITE_ID"
						    	+ " WHERE A.CREATION_DATE >= :param1 and A.CREATION_DATE <= :param2 AND A.ITEM_CODE like '%"+itemCode+"%'  AND A.SUPPLIER_ID like '%"+supplierID+"%' "
						    			+ " AND COALESCE(A.VOUCHER_TYPE,' ') like '%"+voucherType+"%' AND COALESCE(A.VOUCHER_NB,' ') like '%"+voucherNb+"%' GROUP BY A.SITE_ID,A.WARE_NAME,B.LONGITUDE,B.LATITUDE ");

		    	 }
		    	 //Site and Supplier not empty
		    	 else if ( (!StringUtils.equalsIgnoreCase(siteId,"")  || !StringUtils.equalsIgnoreCase(warehouseID,"") || !StringUtils.equalsIgnoreCase(siteName,"") ) && !StringUtils.equalsIgnoreCase(supplierID,"") && StringUtils.equalsIgnoreCase(itemCode,"") 
		    			 && StringUtils.equalsIgnoreCase(itemPartNo,"") && StringUtils.equalsIgnoreCase(itemModel,"") ) {
		    	 
		    		 balanceValQuery=session.createSQLQuery("SELECT DISTINCT A.SITE_ID,A.WARE_NAME,sum(A.BALANCE_QTY),sum(A.BALANCE_VALUE),B.LONGITUDE,B.LATITUDE FROM asset_life_cycle A inner join warehouse B on A.SITE_ID=B.SITE_ID"
						    	+ " WHERE A.CREATION_DATE >= :param1 and A.CREATION_DATE <= :param2  AND (A.SITE_ID like '%"+siteId+"%' or A.WARE_NAME like '%"+siteName+"%' or A.WAREHOUSE like '%"+warehouseID+"%')"
						    	+ " AND A.SUPPLIER_ID like '%"+supplierID+"%' AND COALESCE(A.VOUCHER_TYPE,' ') like '%"+voucherType+"%' AND COALESCE(A.VOUCHER_NB,' ') like '%"+voucherNb+"%'  GROUP BY A.SITE_ID,A.WARE_NAME,B.LONGITUDE,B.LATITUDE ");
		    	 }
		    	 //Item,Site and Supplier are not empty
		    	 else {
		    		 balanceValQuery=session.createSQLQuery("SELECT DISTINCT A.SITE_ID,A.WARE_NAME,sum(A.BALANCE_QTY),sum(A.BALANCE_VALUE),B.LONGITUDE,B.LATITUDE FROM asset_life_cycle A inner join warehouse B on A.SITE_ID=B.SITE_ID"
						    	+ " WHERE A.CREATION_DATE >= :param1 and A.CREATION_DATE <= :param2 AND A.ITEM_CODE like '%"+itemCode+"%' AND (A.SITE_ID like '%"+siteId+"%' or A. WARE_NAME like '%"+siteName+"%' or A.WAREHOUSE like '%"+warehouseID+"%')"
						    	+ " AND A.SUPPLIER_ID like '%"+supplierID+"%' AND COALESCE(A.VOUCHER_TYPE,' ') like '%"+voucherType+"%' AND COALESCE(A.VOUCHER_NB,' ') like '%"+voucherNb+"%' GROUP BY A.SITE_ID,A.WARE_NAME,B.LONGITUDE,B.LATITUDE ");
		    		// System.out.println("balanceValQuery !!! "+balanceValQuery);
		    	 }
	    	}
	    			    	
	    balanceValQuery.setParameter("param1", startDate);
 		balanceValQuery.setParameter("param2", endDate);
 		//System.out.println("balanceValQuery.list() !!! "+balanceValQuery.list());
 		rtn.put("alcBalanceList",balanceValQuery.list());
		}// End balance condition
	} catch (Exception e) {
		sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		exceptionAsString = sw.toString();
		logger.finest("Error in assetLifeCycleLedgerGIS due to \n "+ exceptionAsString);
		logger.info("Error in assetLifeCycleLedgerGIS due to \n "+ exceptionAsString);
		model.addAttribute("ListGridTable","");
	} finally {
		if (session != null && session.isOpen()) {
			tx.commit();
			session.close();
		}
	}
}
	return rtn;
	}

//Generate of the grid
@RequestMapping(value ="/GenerateLedgerReport", method = RequestMethod.GET)
@ResponseBody public Map<String, Object> GenerateLedgerReport(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) { 
				
Map<String, Object> rtn = new LinkedHashMap<>(); 
if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	rtn.put("Login", "redirect:/");
	return rtn;
}	   
session = almsessions.getSession();

if (session != null && session.isOpen()) {
	tx = session.beginTransaction();
	
	try {			
					
		   String start_Date = request.getParameter("startDate");
		   String end_Date = request.getParameter("endDate");	
		   String itemCode = request.getParameter("itemCode");
		   System.out.println("itemCode !!!"+itemCode);
		   String warehouseID = request.getParameter("warehouse");
		   System.out.println("warehouseID !!!"+warehouseID);
		   String supplierID = request.getParameter("supplier");
		   System.out.println("supplierID !!!"+supplierID);
		  // String voucherType = request.getParameter("voucherType");	
		  // String voucherNb = request.getParameter("voucherNb");	
		   String siteId = request.getParameter("siteId");	
		   String siteName = request.getParameter("siteName");	
		  // String itemPartNo = request.getParameter("itemPartNo");	
		 //  String itemModel = request.getParameter("itemModel");	
		 //  String checkedValue = request.getParameter("checkedValue");
			   
		   DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");			 
		   Timestamp startDate;
		   Timestamp endDate;
		   	 
		   startDate = new Timestamp(formatter.parse(start_Date).getTime());
		   endDate = new Timestamp(formatter.parse(end_Date).getTime());
		   System.out.println("startDate !!"+startDate);
		   System.out.println("endDate !!"+endDate);
		   
		   List<AssetLifeCycle_Ledger> assetLifeCycleLedger = new ArrayList<AssetLifeCycle_Ledger>();
		    
			Query query = session.createSQLQuery("Select TO_CHAR(t.CREATION_DATE, 'YYYY-MM-DD') as creationDate, t.WAREHOUSE as warehouse,t.ITEM_CODE as itemCode,t.ITEM_MODEL as itemModel ,t.ITEM_NAME as itemName,t.UOM as UOM, t.ACTUAL_QTY as actualQty, t.VOUCHER_TYPE as voucherType, t.VOUCHER_NB as voucherNB, t.INCOMING_RATE as inComingRate, t.OUTGOING_RATE as outGoingRate, t.VALUATION_RATE as valuationRate, t.ACCUMULATED_DEPRECIATION as accumulatedDepreciation, t.BALANCE_QTY as balanceQty,t.NET_BALANCE_VALUE as netBalanceValue, t.BALANCE_VALUE as balanceValue, t.NET_PRICE as netPrice from ASSET_LIFE_CYCLE t where t.ITEM_CODE like :param3 and t.SUPPLIER_ID like :param5 and (t.CREATION_DATE >= :param1 and t.CREATION_DATE <= :param2) and  t.WAREHOUSE like :param4 order by t.CREATION_DATE desc " );
			query.setParameter("param1", startDate);
			query.setParameter("param2", endDate);
			query.setParameter("param3", "%"+itemCode+"%");
			query.setParameter("param4", "%"+warehouseID+"%");
			query.setParameter("param5", "%"+supplierID+"%");
			//System.out.println("After finishing Query !!!"+ query.list());
			assetLifeCycleLedger = ((SQLQuery) query)
					.addScalar("creationDate").addScalar("itemCode").addScalar("warehouse").addScalar("itemModel").addScalar("UOM").addScalar("actualQty",StandardBasicTypes.FLOAT).addScalar("voucherType").addScalar("voucherNB")
					.addScalar("inComingRate",StandardBasicTypes.FLOAT).addScalar("outGoingRate",StandardBasicTypes.FLOAT).addScalar("accumulatedDepreciation",StandardBasicTypes.FLOAT)
		  			.addScalar("balanceQty",StandardBasicTypes.FLOAT).addScalar("balanceValue",StandardBasicTypes.FLOAT)
		  			.addScalar("valuationRate",StandardBasicTypes.FLOAT).addScalar("netBalanceValue",StandardBasicTypes.FLOAT).addScalar("netPrice",StandardBasicTypes.FLOAT)
					.setResultTransformer(Transformers.aliasToBean(AssetLifeCycle_Ledger.class)).list();
			//query.setFirstResult(0);
			//query.setMaxResults(100);	
			
			System.out.println("After finishing Query !!!"+ assetLifeCycleLedger);
		    rtn.put("assetLifeCycleLedgerList",assetLifeCycleLedger);
		
	} catch (Exception e) {
		sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		exceptionAsString = sw.toString();
	    logger.finest("Error in assetLifeCycleLedger due to \n "+ exceptionAsString);
		logger.info("Error in assetLifeCycleLedger due to \n "+ exceptionAsString);
				
	} finally {
		if (session != null && session.isOpen()) {
			tx.commit();
			session.close();
		}
	}
}
	return rtn;
	}



}
