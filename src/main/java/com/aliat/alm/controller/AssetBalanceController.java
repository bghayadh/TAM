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

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.AssetLifeCycle_Balance;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class AssetBalanceController {
	
	private static final Logger logger = Logger.getLogger(AssetBalanceController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	
	
	
	@Autowired
	Notify notifications;
	
	/*@Autowired
	AppConfig appConfig;
	
	private static Session session = null;
	private static Transaction tx = null;
	//private static ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(AssetBalanceController.class);*/
	
	
@RequestMapping(value = "/AssetBalance", method = RequestMethod.GET)
	public String AssetLifeCycle(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) 
		 {
	
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		
		session = AlmDbSession.getInstance().getSession();
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
		return "AssetLifeCycle/AssetBalance";
	}

//Generate of the map
@RequestMapping(value ="/GetAlcDataGIS", method = RequestMethod.GET)
@ResponseBody public Map<String, Object> GetAlcDataGIS(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) { 
				
Map<String, Object> rtn = new LinkedHashMap<>(); 
if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	rtn.put("Login", "redirect:/");
	return rtn;
}	   
session = AlmDbSession.getInstance().getSession();

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
					     
		if (StringUtils.equalsIgnoreCase(checkedValue,"balance"))  {
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
		    		 //System.out.println("balanceValQuery !!! "+balanceValQuery);
		    	 }
	    	}
	    			    	
	    balanceValQuery.setParameter("param1", startDate);
 		balanceValQuery.setParameter("param2", endDate);
 		System.out.println("balanceValQuery.list() !!! "+balanceValQuery.list());
 		rtn.put("alcBalanceList",balanceValQuery.list());
		}// End balance condition
	} catch (Exception e) {
		sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		exceptionAsString = sw.toString();
		logger.finest("Error at AssetLifeCycle GenerateMap with a message: \n "+ exceptionAsString);
		logger.info("Error at AssetLifeCycle GenerateMap with a message: \n "+ exceptionAsString);
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

//Generate of the map
@RequestMapping(value ="/GenerateBalanceReport", method = RequestMethod.GET)
@ResponseBody public Map<String, Object> GenerateBalanceReport(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) { 
				
Map<String, Object> rtn = new LinkedHashMap<>(); 
if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	rtn.put("Login", "redirect:/");
	return rtn;
}	   
session = AlmDbSession.getInstance().getSession();

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
		   System.out.println("startDate !!!"+startDate);
		   System.out.println("endDate !!!"+endDate);
		   List<AssetLifeCycle_Balance> assetLifeCycleBalance = new ArrayList<AssetLifeCycle_Balance>();
		   
		   String startDateStrQuery = "select * from (select CREATION_DATE from ASSET_LIFE_CYCLE where IS_OPENING = 1 and CREATION_DATE <= :param2 and ITEM_CODE LIKE :param3 and WAREHOUSE LIKE :param4 and SUPPLIER_ID LIKE :param5 ORDER BY CREATION_DATE DESC ) FETCH FIRST 1 ROWS ONLY ";  
		    Query queryDate = session.createSQLQuery(startDateStrQuery);
		    queryDate.setParameter("param2", endDate);
		    queryDate.setParameter("param3", "%"+itemCode+"%");
		    queryDate.setParameter("param4", "%"+warehouseID+"%");
		    queryDate.setParameter("param5", "%"+supplierID+"%");
			List<Object[]> startDateQuery = queryDate.list();
		   // System.out.println("startDateQuery !!!"+startDateQuery.get(0));
		    if(startDateQuery.size() > 0 ) {
		    	startDate = Timestamp.valueOf(String.valueOf(startDateQuery.get(0)));
		    }
		    
		    String str = "select  distinct itemCode, warehouse, siteID, wareName, itemName, itemModel, UOM, supplierID, sum(balanceqty) as balanceqty, sum(balancevalue) as balancevalue, sum(valuationrate) as valuationrate, sum(openingqty) as openingqty,sum(openingValue) as openingValue,sum(inQty) as inQty,sum(outQty) as outQty,sum(inValue) as inValue,sum(outValue) as outValue from (" + 
		    		"Select a.ITEM_CODE as itemCode, a.WAREHOUSE as warehouse, a.SITE_ID as siteID, a.WARE_NAME as wareName, a.ITEM_NAME as itemName, a.ITEM_MODEL as itemModel, a.UOM as UOM, a.SUPPLIER_ID as supplierID, 0 as balanceQty,0 as balanceValue,0 as valuationRate, 0 as openingQty, 0 as openingValue, " + 
		    		"COALESCE(sum(a.ACTUAL_QTY),0) as inQty, 0 as outQty, COALESCE(sum(a.IN_VALUE),0) as inValue, 0 as outValue " + 
		    		"FROM ASSET_LIFE_CYCLE a WHERE (a.CREATION_DATE BETWEEN :param1 and :param2) and a.VOUCHER_TYPE IN ('GR') and a.IN_VALUE > 0 and a.ITEM_CODE LIKE :param3 and a.WAREHOUSE LIKE :param4 and a.SUPPLIER_ID LIKE :param5 " + 
		    		"group by a.ITEM_CODE, a.WAREHOUSE, a.SITE_ID, a.WARE_NAME, a.ITEM_NAME, a.ITEM_MODEL, a.UOM, a.SUPPLIER_ID " + 
		    		"UNION " + 
		    		"Select a.ITEM_CODE as itemCode, a.WAREHOUSE as warehouse, a.SITE_ID as siteID, a.WARE_NAME as wareName, a.ITEM_NAME as itemName, a.ITEM_MODEL as itemModel, a.UOM as UOM, a.SUPPLIER_ID as supplierID, 0 as balanceQty,0 as balanceValue,0 as valuationRate, 0 as openingQty, 0 as openingValue, " + 
		    		"0 as inQty, COALESCE(sum(a.ACTUAL_QTY),0) as outQty, 0 as inValue, COALESCE(sum(a.OUT_VALUE),0) as outValue " + 
		    		"FROM ASSET_LIFE_CYCLE a WHERE (a.CREATION_DATE BETWEEN :param1 and :param2) and a.VOUCHER_TYPE IN ('DN') and a.OUT_VALUE > 0  and a.ITEM_CODE LIKE :param3 and a.WAREHOUSE LIKE :param4 and a.SUPPLIER_ID LIKE :param5 " + 
		    		"group by a.ITEM_CODE, a.WAREHOUSE, a.SITE_ID, a.WARE_NAME, a.ITEM_NAME, a.ITEM_MODEL, a.UOM, a.SUPPLIER_ID " + 
		    		"UNION " + 
		    		"(Select  itemCode,  warehouse, siteID,  wareName, itemName, itemModel, UOM, supplierID, balanceQty, balanceValue, valuationRate, openingQty, openingValue, " + 
		    		" inQty, outQty, inValue, outValue  from (Select a.ITEM_CODE as itemCode, a.WAREHOUSE as warehouse, a.SITE_ID as siteID, a.WARE_NAME as wareName, a.ITEM_NAME as itemName, a.ITEM_MODEL as itemModel, a.UOM as UOM, a.SUPPLIER_ID as supplierID, " + 
		    		"0 as balanceQty,0 as balanceValue, 0 as valuationRate, COALESCE(a.BALANCE_QTY,0) as openingQty, COALESCE(a.BALANCE_VALUE,0) as openingValue, 0 as inQty,0 as outQty, 0 as inValue, 0 as outValue  " + 
		    		"FROM ASSET_LIFE_CYCLE a WHERE a.CREATION_DATE <= :param1 and a.ITEM_CODE LIKE :param3 and a.WAREHOUSE LIKE :param4 and a.SUPPLIER_ID LIKE :param5 ORDER BY a.CREATION_DATE DESC )FETCH FIRST 1 ROWS ONLY " + 
		    		") UNION " + 
		    		"(Select  itemCode,  warehouse, siteID,  wareName, itemName, itemModel, UOM, supplierID, balanceQty, balanceValue, valuationRate, openingQty, openingValue, " + 
		    		" inQty, outQty, inValue, outValue  from (Select a.ITEM_CODE as itemCode, a.WAREHOUSE as warehouse, a.SITE_ID as siteID, a.WARE_NAME as wareName, a.ITEM_NAME as itemName, a.ITEM_MODEL as itemModel, a.UOM as UOM, a.SUPPLIER_ID as supplierID, " + 
		    		"a.BALANCE_QTY as balanceQty,a.BALANCE_VALUE as balanceValue, a.VALUATION_RATE as valuationRate, 0 as openingQty, 0 as openingValue, 0 as inQty,0 as outQty, 0 as inValue, 0 as outValue " + 
		    		"FROM ASSET_LIFE_CYCLE a WHERE a.CREATION_DATE <= :param2 and a.ITEM_CODE LIKE :param3 and a.WAREHOUSE LIKE :param4 and a.SUPPLIER_ID LIKE :param5 ORDER BY a.CREATION_DATE DESC )FETCH FIRST 1 ROWS ONLY " + 
		    		") ) group by itemCode, warehouse, siteID, wareName, itemName, itemModel,UOM, supplierID";
			Query query = session.createSQLQuery(str);
			query.setParameter("param1", startDate);
			query.setParameter("param2", endDate);
			query.setParameter("param3", "%"+itemCode+"%");
			query.setParameter("param4", "%"+warehouseID+"%");
			query.setParameter("param5", "%"+supplierID+"%");
			
			//query.setFirstResult(0);
			//query.setMaxResults(100);
			
			assetLifeCycleBalance =  ((SQLQuery) query)
  			.addScalar("itemCode").addScalar("warehouse").addScalar("siteID").addScalar("wareName").addScalar("itemName").addScalar("itemModel").addScalar("UOM").addScalar("inQty",StandardBasicTypes.FLOAT).addScalar("outQty",StandardBasicTypes.FLOAT)
  			.addScalar("inValue",StandardBasicTypes.FLOAT).addScalar("outValue",StandardBasicTypes.FLOAT)
  			.addScalar("balanceQty",StandardBasicTypes.FLOAT).addScalar("balanceValue",StandardBasicTypes.FLOAT)
  			.addScalar("valuationRate",StandardBasicTypes.FLOAT).addScalar("openingQty",StandardBasicTypes.FLOAT).addScalar("openingValue",StandardBasicTypes.FLOAT)
  			.setResultTransformer(Transformers.aliasToBean(AssetLifeCycle_Balance.class)).list();
			
			
			System.out.println("After finishing Query !!!"+ assetLifeCycleBalance);
		    rtn.put("assetLifeCycleBalanceList",assetLifeCycleBalance);
		
	} catch (Exception e) {
		sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		exceptionAsString = sw.toString();
		logger.finest("Error at AssetLifeCycle GenerateBalanceReport with a message: \n "+ exceptionAsString);
		logger.info("Error at AssetLifeCycle GenerateBalanceReport with a message: \n "+ exceptionAsString);
				
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
