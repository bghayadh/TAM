package com.aliat.alm.controller;

import java.text.SimpleDateFormat;

import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.models.Area;
import com.aliat.alm.models.AreaFinance;
import com.aliat.alm.models.AssetLifeCycle;
import com.aliat.alm.models.AssetLifeCycle_Balance;
import com.aliat.alm.models.DiscoveryNewItem;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.models.PrepaidPayGRevenue;
import com.aliat.alm.models.PurchaseRequest;

import com.aliat.alm.models.Warehouse;
import com.aliat.alm.models.WarehouseProfitloss;
import com.aliat.alm.services.YearMonthBetweenDates;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ReportController {

	 @Autowired
	 ALMSessions almsessions;

private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/Report", method = RequestMethod.GET)
	public String Report(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}else {
			
			List<String[]> report = new ArrayList<String[]>();
			model.addAttribute("AreaReportList", report);
			
			/*List<String[]> plPerSiteData = new ArrayList<String[]>();
			model.addAttribute("plPerSiteTableData", plPerSiteData);
			
			*/
			
			
		return "Report";
		}
	}
	
	@RequestMapping(value = "/SiteProfitLossReport", method = RequestMethod.GET)
	public String SiteProfitLossReport(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}else {
			
			
			List<String[]> plPerSiteData = new ArrayList<String[]>();
			model.addAttribute("plPerSiteTableData", plPerSiteData);
			
			
			
			
		return "Reports/SiteProfitLossReport";
		}
	}
	
	
	@RequestMapping(value = "/AssetAccountingVoucher", method = RequestMethod.GET)
	public String AssetAccountingVoucher(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}else {
			
			
						
			List<String[]> assetReport = new ArrayList<String[]>();
			model.addAttribute("AssetReportList", assetReport);
			
			
		return "Reports/AssetAccountingVoucher";
		}
	}
	
	
	
	
	
	@RequestMapping(value = "/GenerateAccountingVoucher", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateAccountingVoucher(Locale locale, Model model, HttpServletRequest request)throws Exception {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd");

		double monthlyDepreciationBef=0.0 ;
		String monthlyDeprQueryBef;		
		//List<FixedAssetRegistry> monthlyDepreciationInMonth ;
		double monthlyDepreciationInMonth = 0.0;
		
		String monthlyDeprQueryInMonth;
		String newAssetQuery;
		//List<FixedAssetRegistry> newAssetValue;
		double newAssetValue = 0.0;
		String retirmentQuery;
	//	List<DsicoveryNewItem> retirmentValue;
		double retirmentValue = 0.0;

		double finalMonthlyDepr=0.0 ;

		// get date
		String start_Date = request.getParameter("fromdate");
		System.out.println("the start date code is before " +start_Date);
		Date startDate = formatter.parse(start_Date);
		System.out.println("the start date code is after " +startDate);
		Timestamp StartDate = new Timestamp(startDate.getTime());
		System.out.println("the start date code is after time stamp " +StartDate);
		String end_Date = request.getParameter("todate");
		System.out.println("the end date is before " +end_Date);
		Date endDate = formatter.parse(end_Date);
		System.out.println("the end date is after " +endDate);
		Timestamp EndDate = new Timestamp(endDate.getTime());

		List<String[]> assetAccountingVoucherList = new ArrayList<String[]>();		
				
		Session session =almsessions.getSession();
		Transaction tx = session.beginTransaction();
	    
		         	       
	         YearMonthBetweenDates diffmon =new YearMonthBetweenDates();
		     List<Timestamp> list = diffmon.datesBetween(StartDate, EndDate);	     	     
		     
		     System.out.println("The LIST OF DATE " + list);
		     
		     if (list.size()>0) {
		    	 
			    	 for (Timestamp month : list) {	
						List<Object> params= new ArrayList<Object>();
	
						String startmonth = formatter.format(month);	
						Date monthDate = formatter.parse(startmonth);
						Timestamp monthofDate = new Timestamp(monthDate.getTime());
					
			    		 Calendar calendar = Calendar.getInstance(); 
			    		 calendar.setTime(monthofDate); 
			    		 calendar.add(Calendar.MONTH, 1);  
			    	     calendar.set(Calendar.DAY_OF_MONTH, 1);  
			    	     calendar.add(Calendar.DATE, -1);  
			    	     Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());  
		    	     
		    	        String endMonth = formatter.format(lastDayOfMonth);	
						Date endOfDate = formatter.parse(endMonth);
						Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
				        System.out.println("MONTH" + monthofDate);
				        System.out.println("LAST DAY OF MONTH" + endOfMonth);
				      
				        params.add(monthofDate);
                    /////////////depreciation before start date/////////////
		    			monthlyDeprQueryBef = "select sum(t.monthlyDpr)as monthlyDpr from FixedAssetRegistry t where t.farcreatedDate < to_char(:param_1,'dd-MON-yy')";		    			
		    			monthlyDepreciationBef =findAllByQueryParamsAsDouble(session,monthlyDeprQueryBef,params);
						 params.add(endOfMonth);
		    			
				  /////////////////depreciation from start to end month//////////////////////     
					     monthlyDeprQueryInMonth="select sum((trunc(:param_2,'DD') - trunc(farcreatedDate,'DD'))*dailyPercent) as daysDeprecation from FixedAssetRegistry where farcreatedDate >= to_char(:param_1,'dd-MON-yy') and farcreatedDate < to_char(:param_2,'dd-MON-yy')";
			    	     monthlyDepreciationInMonth = findAllByQueryParamsAsDouble(session,monthlyDeprQueryInMonth,params);

					   //  monthlyDepreciationInMonth.addAll(monthlyDepreciationBef);	
					     // finalMonthlyDepr=monthlyDepreciationInMonth+monthlyDepreciationBef;5225+
			    	     
			    	    finalMonthlyDepr= monthlyDepreciationInMonth+monthlyDepreciationBef;
			    	   //  monthlyDepreciationBef+=monthlyDepreciationInMonth;


		     	 //////////////////////new asset////////////////
					     newAssetQuery="select sum(t.iniCost) as iniCost from FixedAssetRegistry t where t.farcreatedDate >= to_char(:param_1,'dd-MON-yy') and t.farcreatedDate < to_char(:param_2,'dd-MON-yy')";
			    	     newAssetValue = findAllByQueryParamsAsDouble(session,newAssetQuery,params);
					     
			    	  //   String newAssetValueInMo=newAssetValue.toString().replaceAll("[\\[\\](){}]","");
			    	    // String newAssetValueMonth=newAssetValueInMo.toString().replaceAll("null","");
			    	    // System.out.println("FINALYYYYYYY NEW ASSETTTT" + newAssetValue);
                /////////Retirment/////////////////////
					     
					     retirmentQuery="select sum(t.dniRate) as dniRate from DsicoveryNewItem t where t.transType='Retirement' and t.dniAPPROVAL='Finance' and t.dnilastModifieddate >= to_char(:param_1,'dd-MON-yy') and t.dnilastModifieddate < to_char(:param_2,'dd-MON-yy')";
			    	     retirmentValue = findAllByQueryParamsAsDouble(session,retirmentQuery,params);
			    	   //  String retirmentValueMo=retirmentValue.toString().replaceAll("[\\[\\](){}]","");
			    	   //  String retirmentValueMonth=retirmentValueMo.toString().replaceAll("null","");
			    	    // System.out.println("FINALYYYYYYY RETIRMENT" + retirmentValueMonth);	
			    	///////////added the data in array list///////
					     System.out.println("NEW ASSET VALUE" + newAssetValue);
   	    	    	     System.out.println("MONTHLY DEPRECIATION " + monthlyDepreciationInMonth);
			    	    System.out.println("RETIRMENTT" + retirmentValue);

			    	     String[] arrayData = {"1",String.valueOf(month),String.valueOf(lastDayOfMonth),String.valueOf(newAssetValue),String.valueOf(finalMonthlyDepr),String.valueOf(retirmentValue)};
			        assetAccountingVoucherList.add(arrayData);
			        System.out.println("FINALYYYYYYY DATAAAA" + mapper.writeValueAsString(arrayData));	


		    	 }

		     }	 
		    
	      
		    tx.commit();
			session.close();
					
			rtn.put("AssetReportList", assetAccountingVoucherList);
			return rtn;
			
	  }	
	
	
	@RequestMapping(value = "/GenerateAreaSitesReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateAreaSitesReport(Locale locale, Model model, HttpServletRequest request)throws Exception {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		Date date;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		//SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		//AssetLifeCycle_Balance assetLifeWareItemBalance = new AssetLifeCycle_Balance();
		
		// get item
		// get date
		String start_Date = request.getParameter("startDate");
		System.out.println("the start date code is before " +start_Date);
		Date startDate = formatter.parse(start_Date);
		System.out.println("the start date code is after " +startDate);
		System.out.println("the start date code is after " +formatter1.format(startDate));
		//Date startDate1 = formatter1.parse(startDate);
		Timestamp StartDate = new Timestamp(startDate.getTime());
		System.out.println("the start date code is after time stamp " +StartDate);
		String end_Date = request.getParameter("endDate");
		System.out.println("the end date is before " +end_Date);
		Date endDate = formatter.parse(end_Date);
		System.out.println("the end date is after " +endDate);
		//Date endDate1 = formatter1.parse(endDate);
		Timestamp EndDate = new Timestamp(endDate.getTime());
		String area=request.getParameter("Area");
		// get warehouse
		
		
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		
		if(area!="") {
			
		
			String queryStmt = "select distinct t.areaName as areaName, t.wareID as wareID ,t.wareName as wareName , t.siteID as siteID from WarehouseProfitloss t where  (t.startDate >= :param1 and t.endDate <= :param2) and t.areaName = :param3  ";
			Query result = session.createQuery(queryStmt);
			result.setParameter("param1", startDate);
			result.setParameter("param2", endDate);
			result.setParameter("param3", area);
			List<WarehouseProfitloss> listAreaSites = (List<WarehouseProfitloss>) result.setResultTransformer(Transformers.aliasToBean(WarehouseProfitloss.class)).list();
			

			System.out.println("The size of the list is " + listAreaSites.size());
			System.out.println("The list is " + mapper.writeValueAsString(listAreaSites));

			
			  rtn.put("AreaSitesReportArray", listAreaSites);
			
			
			
		}
		
		
		
		else {
			
			String queryStmt = "select distinct t.areaName as areaName, t.wareID as wareID ,t.wareName as wareName , t.siteID as siteID from WarehouseProfitloss t where  (t.startDate >= :param1 and t.endDate <= :param2)";
			Query result = session.createQuery(queryStmt);
			result.setParameter("param1", startDate);
			result.setParameter("param2", endDate);
			
			List<WarehouseProfitloss> listAreaSites = (List<WarehouseProfitloss>) result.setResultTransformer(Transformers.aliasToBean(WarehouseProfitloss.class)).list();
			

			System.out.println("The size of the list is " + listAreaSites.size());
			System.out.println("The list is " + mapper.writeValueAsString(listAreaSites));

			  rtn.put("AreaSitesReportArray", listAreaSites);
			
			
		}
		
		
		

		
		
		
		  
		   tx.commit();
		   session.close();
		   
		
		  // rtn.put("Type", "generateReport");
		   return rtn;
	}
	
	
	
	@RequestMapping(value = "/ProfitLossAreaReport", method = RequestMethod.GET)
	public String ProfitLossAreaReport(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}else {
			
			List<String[]> report = new ArrayList<String[]>();
			model.addAttribute("AreaReportList", report);
			
			
		return "Reports/ProfitLossAreaReport";
		}
	}
	
	
	
	@RequestMapping(value = "/AreaSiteReport", method = RequestMethod.GET)
	public String AreaSiteReport(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}else {
			
			/*List<String[]> report = new ArrayList<String[]>();
			model.addAttribute("AreaReportList", report);*/
			
			
		return "Reports/AreaSiteReport";
		}
	}
	
	
	
	
	
	@RequestMapping(value = "/GenerateAreaReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateAreaReport(Locale locale, Model model, HttpServletRequest request)throws Exception {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		Date date;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		
		// get date
		String start_Date = request.getParameter("startDate");
		System.out.println("the start date code is before " +start_Date);
		Date startDate = formatter.parse(start_Date);
		System.out.println("the start date code is after " +startDate);
		Timestamp StartDate = new Timestamp(startDate.getTime());
		System.out.println("the start date code is after time stamp " +StartDate);
		String end_Date = request.getParameter("endDate");
		System.out.println("the end date is before " +end_Date);
		Date endDate = formatter.parse(end_Date);
		System.out.println("the end date is after " +endDate);
		Timestamp EndDate = new Timestamp(endDate.getTime());
		
		String  checkVal = request.getParameter("checkValType");
		
		
		String  area = request.getParameter("areaName");
		System.out.println("the area is " +area);
		String  profitable = request.getParameter("Profitable");
		String  loss = request.getParameter("Loss");
		String  max = request.getParameter("Max");
		String  min = request.getParameter("Min");
		System.out.println("the max is " +max);
		System.out.println("the min is " +min);
		String  technologies = request.getParameter("technologies");
		System.out.println("/*/*/*the technology is " +technologies);
		//AreaFinanceReport areaReport = new AreaFinanceReport();
		//List<AreaFinanceReport> AreaReportList = new ArrayList<AreaFinanceReport>();
		List<Object[]> AreaReportList,AreaReportList1  = new ArrayList<Object[]>();
		//Object[]AreaReportList = null;
		List<String[]> data = new ArrayList<String[]>();
		Object[] AreaReportArray = null;
		Object[] totalAreaReportArray = null;
		String[] arrayData1 = null;
		 Query query,q,q1,q2,q3;
		 
		 String query1="";
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		 
		 double totalPL = 0 ,totalPLF = 0, totalnumberof2g = 0,totalnumberof2g3g = 0,totalnumberof2g3g4g = 0, sumProfitLoss = 0,totalSites = 0,countT = 0;
			double sitePL1 = 0, sitePL2 = 0,sitePL3 = 0, sumSitePL1 = 0, sumSitePL2 = 0,sumSitePL3 = 0, totalSumNumberof2g = 0,totalSumNumberof2g3g = 0,totalSumNumberof2g3g4g = 0;
			String areas="";
		 // IF ACCUMLATED IS CHECKED 
		if(StringUtils.equalsIgnoreCase(checkVal,"Accumulated")) {
		//if (checkVal == "Accumulated") {
			 System.out.println("the checkbx1 is "+checkVal);
			// Map<String, Double> ProfitableMap = new HashMap<String, Double>();
			 //Map<String, Double> LossMap = new HashMap<String, Double>();
				// IF AREA IS SELCTED 
		    if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
		    	System.out.println("/*/*hereeeee");
			//if (area == "all") {
				System.out.println("Area is null and accumulated type report is selected");
			    if((StringUtils.equalsIgnoreCase(profitable,"null") || profitable == null || profitable == "") && (StringUtils.equalsIgnoreCase(loss,"null") || loss == null || loss == "")&& (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") &&(StringUtils.equalsIgnoreCase(max,"null") || max == null || max == "") && (StringUtils.equalsIgnoreCase(min,"null") || min == null || min == "")) {

				//System.out.println("The main query is " +mapper.writeValueAsString(query.list()));
			
				
				q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from AreaFinance t where t.startDate >= :param1 order by t.startDate asc");
			    q1.setParameter("param1", StartDate);
			    q1.setFirstResult(0);
			    q1.setMaxResults(1);
				String start = (String) q1.uniqueResult();
				System.out.println("The start date is " +start);
				
				q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from AreaFinance t where t.endDate <= :param1 order by t.endDate desc");
			    q2.setParameter("param1", EndDate);
			    q2.setFirstResult(0);
			    q2.setMaxResults(1);
				String end = (String) q2.uniqueResult();
				System.out.println("The end date is " +end);
			
			   
			      String Query="Select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ( select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>= :param1 and end_date<= :param2 group by area_name) jointable on stable.area_name = jointable.area_name";
			       q = session.createSQLQuery(Query);

				  
			       q.setParameter("param1", StartDate);
			       q.setParameter("param2", EndDate);
			
			       
			       
			       
			      
			       
			       
			       AreaReportList=q.list();
			       
			       countT=AreaReportList.size();
			       
			      if(AreaReportList.size()>0) {
			    	  for(int i=0;i<AreaReportList.size();i++) {
			    		  
			    		  AreaReportArray = (Object[]) AreaReportList.toArray()[i];
		            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
			    		  
			    	  
			    	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
			    		   AreaReportArray[0] ="";
			    		   System.out.println("passsssss");
			    	 }
			    	 else {
			           areas =String.valueOf(AreaReportArray[0])  ;
			    	 }
			    	  
			    	  
			    	  
			    	  
			    	   if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
			    		   AreaReportArray[1] = 0.0;
			    		   System.out.println("passsssss");
			    	 }
			    	 else {
			    		 
			    		 
			    		BigDecimal a= (BigDecimal) AreaReportArray[1];
			           
			           
			           System.out.println(a);
			           totalnumberof2g=Math.round(a.doubleValue());
			           System.out.println(totalnumberof2g);
			           
			           
			    	 }
			         if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == ""){ 
			    		   AreaReportArray[2] = 0.0;
		             }
		             else{ 
		            	 
		            	 BigDecimal a= (BigDecimal) AreaReportArray[2];
				           
				           
				           System.out.println(a);
				           sitePL1=Math.round(a.doubleValue());
				           System.out.println(sitePL1);
				           
		            	 
		            	 
		             }
			         if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == ""){ 
		    		       AreaReportArray[3] = 0.0;
			         }
			         else {
			        	 
			        	 BigDecimal a= (BigDecimal) AreaReportArray[3];
				           
				           
				           System.out.println(a);
				           totalnumberof2g3g=Math.round(a.doubleValue());
				           System.out.println(totalnumberof2g3g);
				        
			        	 
			        	 
			           
			         } 
			    	 if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == "") { 
			    		   AreaReportArray[4] = 0.0;
			    	 }
			    	 else {
			    		 

			        	 BigDecimal a= (BigDecimal) AreaReportArray[4];
				           
				           
				           System.out.println(a);
				           sitePL2=Math.round(a.doubleValue());
				           System.out.println(sitePL2);
				        
			    		 
			    		 
			    		
			    	 }
			    	 if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == "") { 
			    		   AreaReportArray[5] = 0;
			    	 }  
			    	 else{
			    		 
			    		 BigDecimal a= (BigDecimal) AreaReportArray[5];
				           
				           
				           System.out.println(a);
				           totalnumberof2g3g4g=Math.round(a.doubleValue());
				           System.out.println(totalnumberof2g3g4g);
				        
			    		
			    	 }
			    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
			    		   AreaReportArray[6] = 0;
			    		   
			    	 }
			    	 else{
			    		 
			    		 BigDecimal a= (BigDecimal) AreaReportArray[6];
				           
				           
				           System.out.println(a);
				           sitePL3=Math.round(a.doubleValue());
				           System.out.println(sitePL3);
				    	
			    	 }
			    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
			    		   AreaReportArray[7] = 0;
			    		   
			    	 }
			    	 else{
			    		 
			    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
				           
				           
				           System.out.println(a);
				           totalSites=Math.round(a.doubleValue());
				           System.out.println(totalSites);
				        
			    		 
			    	 }
			    		 
			    		
			    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
			    		   AreaReportArray[8] = 0;
			    		   
			    	 }
			    	 else{
			    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
				         
				           sumProfitLoss=Math.round(a.doubleValue());
				           System.out.println(sumProfitLoss);
				         
			    	 }
			       
			           System.out.println("The Profit and Loss of site1 is " +sitePL1);
		               System.out.println("The Profit and Loss of site2 is " +sitePL2);
		               System.out.println("The Profit and Loss of site1 is " +sitePL3);
		               System.out.println("The total count of site1 is " +totalnumberof2g);
		               System.out.println("The total count of site2 is " +totalnumberof2g3g);
		               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
		           
		               String[] arrayData = {"1",start,end,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
		               System.out.println("The array" +mapper.writeValueAsString(arrayData));
		               arrayData1 = arrayData;
		               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
		               data.add(arrayData);
		               
		               totalPLF += (sitePL1+sitePL2 +sitePL3);
					    totalSumNumberof2g += totalnumberof2g;
					    totalSumNumberof2g3g += totalnumberof2g3g;
					    totalSumNumberof2g3g4g += totalnumberof2g3g4g;
			       }// End for for loop
			       
			        
			  System.out.println("totalPLF" +totalPLF);
			  System.out.println("TotalNumber of 2g" +totalSumNumberof2g);
			  System.out.println("totalnumberof2g3g" +totalSumNumberof2g3g);
			  System.out.println("totalnumberof2g3g4g" +totalSumNumberof2g3g4g);
			  
				
				
				
			    
		    }// end for if
			      
			      rtn.put("totalProfitLoss",totalPLF );
				    rtn.put("totalnumberof2g",totalSumNumberof2g );
				    rtn.put("totalnumberof2g3g",totalSumNumberof2g3g);
					rtn.put("totalnumberof2g3g4g",totalSumNumberof2g3g4g );
					rtn.put("totalAreas", countT);
			    
		    }// end for if it is accumlated but not profitable nor loss nor technology is used
			    
		
			    
			  // IF MAX ONLY IS CHECKED
			     
			    if(StringUtils.equalsIgnoreCase(min,"MIN") && profitable == null && loss == null){
			    	if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") {
			    		
			    		
			    		
			    	// no technology is selected
			    	 query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(SUM_PROFIT_LOSS)ASC) where rownum=1))jointable on stable.area_name = jointable.area_name";	
			    		
			    		
			    	
			    		
			    	
			    	}
			    	
			    	
			    	
			    	if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g")){
							
							 query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G)ASC) where rownum=1))jointable on stable.area_name = jointable.area_name";
							
							
						}
						
                    if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
							
							 query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G_3G)ASC) where rownum=1))jointable on stable.area_name = jointable.area_name";
							
							
						}
						
                    if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
						
						 query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G_3G_4G)ASC) where rownum=1))jointable on stable.area_name = jointable.area_name";
						
						
					}
                    
                    	
						
			    	}// End the condition of technologies if selected any
			    	
			    	q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from AreaFinance t where t.startDate >= :param1 order by t.startDate asc");
				    q1.setParameter("param1", StartDate);
				    q1.setFirstResult(0);
				    q1.setMaxResults(1);
					String start = (String) q1.uniqueResult();
					System.out.println("The start date is " +start);
					
					q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from AreaFinance t where t.endDate <= :param1 order by t.endDate desc");
				    q2.setParameter("param1", EndDate);
				    q2.setFirstResult(0);
				    q2.setMaxResults(1);
					String end = (String) q2.uniqueResult();
					System.out.println("The end date is " +end);
				
			    	System.out.println(query1);
			    	q=session.createSQLQuery(query1);
					q.setParameter("param1", StartDate);
				    q.setParameter("param2", EndDate);
				    
				    AreaReportList=q.list();
				       
				       countT=AreaReportList.size();
				       
				      if(AreaReportList.size()>0) {
				    	  for(int i=0;i<AreaReportList.size();i++) {
				    		  
				    		  AreaReportArray = (Object[]) AreaReportList.toArray()[i];
			            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
				    		  
				    	  
				    	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
				    		   AreaReportArray[0] ="";
				    		   System.out.println("passsssss");
				    	 }
				    	 else {
				           areas =String.valueOf(AreaReportArray[0])  ;
				    	 }
				    	  
				    	  
				    	  
				    	  
				    	   if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
				    		   AreaReportArray[1] = 0.0;
				    		   System.out.println("passsssss");
				    	 }
				    	 else {
				    		BigDecimal a= (BigDecimal) AreaReportArray[1];
				          
				           System.out.println(a);
				           totalnumberof2g=Math.round(a.doubleValue());
				           System.out.println(totalnumberof2g);
				           
				           
				    	 }
				         if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == ""){ 
				    		   AreaReportArray[2] = 0.0;
			             }
			             else{ 
			            	 
			            	 BigDecimal a= (BigDecimal) AreaReportArray[2];
					           
					           
					           System.out.println(a);
					           sitePL1=Math.round(a.doubleValue());
					           System.out.println(sitePL1);
					           
			            	 
			            	 
			             }
				         if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == ""){ 
			    		       AreaReportArray[3] = 0.0;
				         }
				         else {
				        	 
				        	 BigDecimal a= (BigDecimal) AreaReportArray[3];
					           
					           
					           System.out.println(a);
					           totalnumberof2g3g=Math.round(a.doubleValue());
					           System.out.println(totalnumberof2g3g);
					        
				        	 
				        	 
				           
				         } 
				    	 if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == "") { 
				    		   AreaReportArray[4] = 0.0;
				    	 }
				    	 else {
				    		 

				        	 BigDecimal a= (BigDecimal) AreaReportArray[4];
					           
					           
					           System.out.println(a);
					           sitePL2=Math.round(a.doubleValue());
					           System.out.println(sitePL2);
					        
				    		 
				    		 
				    		
				    	 }
				    	 if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == "") { 
				    		   AreaReportArray[5] = 0;
				    	 }  
				    	 else{
				    		 
				    		 BigDecimal a= (BigDecimal) AreaReportArray[5];
					           
					           
					           System.out.println(a);
					           totalnumberof2g3g4g=Math.round(a.doubleValue());
					           System.out.println(totalnumberof2g3g4g);
					        
				    		 
				    		 
				    		 
				    		 
				    		
				    	 }
				    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
				    		   AreaReportArray[6] = 0;
				    		   
				    	 }
				    	 else{
				    		 
				    		 BigDecimal a= (BigDecimal) AreaReportArray[6];
					           
					           
					           System.out.println(a);
					           sitePL3=Math.round(a.doubleValue());
					           System.out.println(sitePL3);
					        
				    		 
				    		 
				    		
				    	 }
				    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
				    		   AreaReportArray[7] = 0;
				    		   
				    	 }
				    	 else{
				    		 
				    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
					           
					           
					           System.out.println(a);
					           totalSites=Math.round(a.doubleValue());
					           System.out.println(totalSites);
					        
				    		 
				    	 }
				    		 
				    		
				    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
				    		   AreaReportArray[8] = 0;
				    		   
				    	 }
				    	 else{
				    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
					           
					           
					           System.out.println(a);
					           sumProfitLoss=Math.round(a.doubleValue());
					           System.out.println(sumProfitLoss);
					         
				    	 }
				       
				           System.out.println("The Profit and Loss of site1 is " +sitePL1);
			               System.out.println("The Profit and Loss of site2 is " +sitePL2);
			               System.out.println("The Profit and Loss of site1 is " +sitePL3);
			               System.out.println("The total count of site1 is " +totalnumberof2g);
			               System.out.println("The total count of site2 is " +totalnumberof2g3g);
			               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
			           
			               String[] arrayData = {"1",start,end,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
			               System.out.println("The array" +mapper.writeValueAsString(arrayData));
			               arrayData1 = arrayData;
			               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
			               data.add(arrayData);
				    	  }
				      }
			    	
			    
			    }// END OF THE CONDITION IF MAX ONLY CHECKED 
			    
			    
			    if(StringUtils.equalsIgnoreCase(max,"Max") && profitable == null && loss == null){
			    	if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") {
			    	// no technology is selected
			    	 query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(SUM_PROFIT_LOSS)DESC) where rownum=1))jointable on stable.area_name = jointable.area_name";	
			    		
			    	 
			    	
			    		
			    	
			    	}
			    	
			    	
			    	
			    	if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g")){
							
							 query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G)DESC) where rownum=1))jointable on stable.area_name = jointable.area_name";
							
							
						}
						
                    if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
							
							 query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G_3G)DESC) where rownum=1))jointable on stable.area_name = jointable.area_name";
							
							
						}
						
                    if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
						
						 query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G_3G_4G)DESC) where rownum=1))jointable on stable.area_name = jointable.area_name";
						
						
					}
                    
                    	
						
			    	}// End the condition of technologies if selected any
			    	
			    	q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from AreaFinance t where t.startDate >= :param1 order by t.startDate asc");
				    q1.setParameter("param1", StartDate);
				    q1.setFirstResult(0);
				    q1.setMaxResults(1);
					String start = (String) q1.uniqueResult();
					System.out.println("The start date is " +start);
					
					q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from AreaFinance t where t.endDate <= :param1 order by t.endDate desc");
				    q2.setParameter("param1", EndDate);
				    q2.setFirstResult(0);
				    q2.setMaxResults(1);
					String end = (String) q2.uniqueResult();
					System.out.println("The end date is " +end);
				
			    	System.out.println(query1);
			    	q=session.createSQLQuery(query1);
					q.setParameter("param1", StartDate);
				    q.setParameter("param2", EndDate);
				    
				    AreaReportList=q.list();
				       
				       countT=AreaReportList.size();
				       
				      if(AreaReportList.size()>0) {
				    	  for(int i=0;i<AreaReportList.size();i++) {
				    		  
				    		  AreaReportArray = (Object[]) AreaReportList.toArray()[i];
			            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
				    		  
				    	  
				    	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
				    		   AreaReportArray[0] ="";
				    		   System.out.println("passsssss");
				    	 }
				    	 else {
				           areas =String.valueOf(AreaReportArray[0])  ;
				    	 }
				    	  
				    	  
				    	  
				    	  
				    	   if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
				    		   AreaReportArray[1] = 0.0;
				    		   System.out.println("passsssss");
				    	 }
				    	 else {
				    		 
				    		 
				    		BigDecimal a= (BigDecimal) AreaReportArray[1];
				           
				           
				           System.out.println(a);
				           totalnumberof2g=Math.round(a.doubleValue());
				           System.out.println(totalnumberof2g);
				           
				           
				    	 }
				         if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == ""){ 
				    		   AreaReportArray[2] = 0.0;
			             }
			             else{ 
			            	 
			            	 BigDecimal a= (BigDecimal) AreaReportArray[2];
					           
					           
					           System.out.println(a);
					           sitePL1=Math.round(a.doubleValue());
					           System.out.println(sitePL1);
					           
			            	 
			            	 
			             }
				         if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == ""){ 
			    		       AreaReportArray[3] = 0.0;
				         }
				         else {
				        	 
				        	 BigDecimal a= (BigDecimal) AreaReportArray[3];
					           
					           
					           System.out.println(a);
					           totalnumberof2g3g=Math.round(a.doubleValue());
					           System.out.println(totalnumberof2g3g);
					        
				        	 
				        	 
				           
				         } 
				    	 if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == "") { 
				    		   AreaReportArray[4] = 0.0;
				    	 }
				    	 else {
				    		 

				        	 BigDecimal a= (BigDecimal) AreaReportArray[4];
					           
					           
					           System.out.println(a);
					           sitePL2=Math.round(a.doubleValue());
					           System.out.println(sitePL2);
					        
				    		 
				    		 
				    		
				    	 }
				    	 if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == "") { 
				    		   AreaReportArray[5] = 0;
				    	 }  
				    	 else{
				    		 
				    		 BigDecimal a= (BigDecimal) AreaReportArray[5];
					           
					           
					           System.out.println(a);
					           totalnumberof2g3g4g=Math.round(a.doubleValue());
					           System.out.println(totalnumberof2g3g4g);
					        
				    		 
				    		 
				    		 
				    		 
				    		
				    	 }
				    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
				    		   AreaReportArray[6] = 0;
				    		   
				    	 }
				    	 else{
				    		 
				    		 BigDecimal a= (BigDecimal) AreaReportArray[6];
					           
					           
					           System.out.println(a);
					           sitePL3=Math.round(a.doubleValue());
					           System.out.println(sitePL3);
					        
				    		 
				    		 
				    		
				    	 }
				    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
				    		   AreaReportArray[7] = 0;
				    		   
				    	 }
				    	 else{
				    		 
				    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
					           
					           
					           System.out.println(a);
					           totalSites=Math.round(a.doubleValue());
					           System.out.println(totalSites);
					        
				    		 
				    	 }
				    		 
				    		
				    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
				    		   AreaReportArray[8] = 0;
				    		   
				    	 }
				    	 else{
				    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
					           
					           
					           System.out.println(a);
					           sumProfitLoss=Math.round(a.doubleValue());
					           System.out.println(sumProfitLoss);
					         
				    	 }
				       
				           System.out.println("The Profit and Loss of site1 is " +sitePL1);
			               System.out.println("The Profit and Loss of site2 is " +sitePL2);
			               System.out.println("The Profit and Loss of site1 is " +sitePL3);
			               System.out.println("The total count of site1 is " +totalnumberof2g);
			               System.out.println("The total count of site2 is " +totalnumberof2g3g);
			               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
			           
			               String[] arrayData = {"1",start,end,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
			               System.out.println("The array" +mapper.writeValueAsString(arrayData));
			               arrayData1 = arrayData;
			               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
			               data.add(arrayData);
				    	  }
				      }
			    	
			    	
			    	
			    	
			    	
			    
			    }// END OF THE CONDITION IF Min ONLY CHECKED 
			    
			    
			    
			    // Begin the condition of profitable
			    
			    if(StringUtils.equalsIgnoreCase(profitable,"Profitable")) {
			    	
			    	
			    	System.out.println("1st one is here");
			    
			    	if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
					{
			    	
			    		query1="Select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ( select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>= :param1 and end_date<= :param2 group by area_name) jointable on stable.area_name = jointable.area_name  where spl>=0";
					      
			    	
			    	
			    	
					}
			    	// IF PROFITABLE AND ONLY Max IS CHOOSEN
			    	if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null")){
						System.out.println("Yara is here andddd");
						query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(SUM_PROFIT_LOSS)DESC) where spl >=0 and rownum=1))jointable on stable.area_name = jointable.area_name";						
						//System.out.println(Query);
				    	
						
					}
					// IF PROFITABLE AND ONLY MIN IS CHOOSEN
                   if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null")){
                	   System.out.println("Yara isNOT HEREEEEEEEandddd");
			    		query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(SUM_PROFIT_LOSS)ASC) where spl >=0 and rownum=1))jointable on stable.area_name = jointable.area_name";						
						//System.out.println(Query);

					}
					
			    	// IF PROFITABLE AND ONLY TECHNOLOGY IS CHOOSEN 
                   if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && min == null && max == null){
			    	
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
                		query1="Select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ( select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>= :param1 and end_date<= :param2 group by area_name) jointable on stable.area_name = jointable.area_name  where pl2g>=0";
					      
                	   
                	   }
                	   
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
                		   query1="Select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ( select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>= :param1 and end_date<= :param2 group by area_name) jointable on stable.area_name = jointable.area_name  where pl2g3g>=0";
					       
			    	
			    	
                	   
                	   }
                	   
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
                		   query1="Select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ( select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>= :param1 and end_date<= :param2 group by area_name) jointable on stable.area_name = jointable.area_name  where pl2g3g4g>=0";
					       
					      
                	   
                	   }
                	   
                	   
			    	
                   }
                   
                
                   // IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null")) {
                   
                   query1="select distinct  stable.area_name,jointable.n2g, jointable.pl2g, jointable.n2g3g, jointable.pl2g3g, jointable.n2g3g4g, jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date >=:param1 and end_date <=:param2 group by area_name order by spl DESC) where spl>=0 and rownum=1 ) UNION (select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl  from area_finance  where start_date >=:param1  and end_date <=:param2 group by area_name order by spl ASC) where spl>=0 and rownum=1)) jointable on stable.area_name = jointable.area_name";
                   
                   }
                   
                   // IF MAX AND MIN WITH TECHNOLOGY 
                   
                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
                   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
                	   
                		   query1="select distinct  stable.area_name,jointable.n2g, jointable.pl2g, jointable.n2g3g, jointable.pl2g3g, jointable.n2g3g4g, jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date >=:param1 and end_date <=:param2 group by area_name order by pl2g DESC) where pl2g>=0 and rownum=1 ) UNION (select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl  from area_finance  where start_date >=:param1  and end_date <=:param2 group by area_name order by pl2g ASC) where pl2g>=0 and rownum=1)) jointable on stable.area_name = jointable.area_name";
                	   
                	   }
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
                    	   
                		   query1="select distinct  stable.area_name,jointable.n2g, jointable.pl2g, jointable.n2g3g, jointable.pl2g3g, jointable.n2g3g4g, jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date >=:param1 and end_date <=:param2 group by area_name order by pl2g3g DESC) where pl2g3g>=0 and rownum=1 ) UNION (select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl  from area_finance  where start_date >=:param1  and end_date <=:param2 group by area_name order by pl2g3g ASC) where pl2g3g>=0 and rownum=1)) jointable on stable.area_name = jointable.area_name";
                    	   
                	   }
                	   
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
                    	   
                		   query1="select distinct  stable.area_name,jointable.n2g, jointable.pl2g, jointable.n2g3g, jointable.pl2g3g, jointable.n2g3g4g, jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date >=:param1 and end_date <=:param2 group by area_name order by pl2g3g4g DESC) where pl2g3g4g>=0 and rownum=1 ) UNION (select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl  from area_finance  where start_date >=:param1  and end_date <=:param2 group by area_name order by pl2g3g4g ASC) where pl2g3g4g>=0 and rownum=1)) jointable on stable.area_name = jointable.area_name";
                    	   
                	   }
                	   
                	   
                	   
                   
                   }
                   
                   
                   
                   
                   
                   
                   //IF MAX IS CHECKED WITH TECHNOLOGY 
                   
                   
                   
                   
                   if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
                   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
                		   query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G)DESC) where pl2g >=0 and rownum=1))jointable on stable.area_name = jointable.area_name";
                	   
                	   
                	   }
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
                		   query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G_3G)DESC) where pl2g3g >=0 and rownum=1))jointable on stable.area_name = jointable.area_name";
                    	   
                    	   
                	   }
                	   
                	   
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
                    	   
                		   query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G_3G_4G)DESC) where pl2g3g4g >=0 and rownum=1))jointable on stable.area_name = jointable.area_name"; 
                    	   
                	   }
                	   
                	   
                	   
                	   
                   
                   }
                   
                   
                   // IF MIN IS CHECKED WITH TECHNOLOGY
                   if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
                       
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
                		   query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G)ASC) where pl2g >=0 and rownum=1))jointable on stable.area_name = jointable.area_name";
                		   
                	   
                	   }
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
                		   query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G_3G)ASC) where pl2g3g >=0 and rownum=1))jointable on stable.area_name = jointable.area_name";
                    	   
                    	   
                	   }
                	   
                	   
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
                    	   
                		   query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G_3G_4G)ASC) where pl2g3g4g >=0 and rownum=1))jointable on stable.area_name = jointable.area_name"; 
                    	   
                	   }
                	   
                	   
                	   
                	   
                   
                   }
                   
                   
                   
                   
                   q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from AreaFinance t where t.startDate >= :param1 order by t.startDate asc");
   			    q1.setParameter("param1", StartDate);
   			    q1.setFirstResult(0);
   			    q1.setMaxResults(1);
   				String start = (String) q1.uniqueResult();
   				System.out.println("The start date is " +start);
   				
   				q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from AreaFinance t where t.endDate <= :param1 order by t.endDate desc");
   			    q2.setParameter("param1", EndDate);
   			    q2.setFirstResult(0);
   			    q2.setMaxResults(1);
   				String end = (String) q2.uniqueResult();
   				System.out.println("The end date is " +end);
   			
   			   
   				
		    	q=session.createSQLQuery(query1);
				q.setParameter("param1", StartDate);
			    q.setParameter("param2", EndDate);
			    
			    AreaReportList=q.list();
			     
   			
   			    
   			   
   			    
   			    System.out.println("The size is "+AreaReportList.size());  
				       
				      if(AreaReportList.size()>0) {
				    	  for(int i=0;i<AreaReportList.size();i++) {
				    		  
				    		  AreaReportArray = (Object[]) AreaReportList.toArray()[i];
			            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
				    		  
				    	  
				    	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
				    		   AreaReportArray[0] ="";
				    		   System.out.println("passsssss");
				    	 }
				    	 else {
				           areas =String.valueOf(AreaReportArray[0])  ;
				    	 }
				    	  
				    	  
				    	  
				    	  
				    	   if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
				    		   AreaReportArray[1] = 0.0;
				    		   System.out.println("passsssss");
				    	 }
				    	 else {
				    		 
				    		 
				    		BigDecimal a= (BigDecimal) AreaReportArray[1];
				           
				           
				           System.out.println(a);
				           totalnumberof2g=Math.round(a.doubleValue());
				           System.out.println(totalnumberof2g);
				           
				           
				    	 }
				         if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == ""){ 
				    		   AreaReportArray[2] = 0.0;
			             }
			             else{ 
			            	 
			            	 BigDecimal a= (BigDecimal) AreaReportArray[2];
					           
					           
					           System.out.println(a);
					           sitePL1=Math.round(a.doubleValue());
					           System.out.println(sitePL1);
					           
			            	 
			            	 
			             }
				         if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == ""){ 
			    		       AreaReportArray[3] = 0.0;
				         }
				         else {
				        	 
				        	 BigDecimal a= (BigDecimal) AreaReportArray[3];
					           
					           
					           System.out.println(a);
					           totalnumberof2g3g=Math.round(a.doubleValue());
					           System.out.println(totalnumberof2g3g);
					        
				        	 
				        	 
				           
				         } 
				    	 if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == "") { 
				    		   AreaReportArray[4] = 0.0;
				    	 }
				    	 else {
				    		 

				        	 BigDecimal a= (BigDecimal) AreaReportArray[4];
					           
					           
					           System.out.println(a);
					           sitePL2=Math.round(a.doubleValue());
					           System.out.println(sitePL2);
					        
				    		 
				    		 
				    		
				    	 }
				    	 if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == "") { 
				    		   AreaReportArray[5] = 0;
				    	 }  
				    	 else{
				    		 
				    		 BigDecimal a= (BigDecimal) AreaReportArray[5];
					           
					           
					           System.out.println(a);
					           totalnumberof2g3g4g=Math.round(a.doubleValue());
					           System.out.println(totalnumberof2g3g4g);
					        
				    		 
				    		 
				    		 
				    		 
				    		
				    	 }
				    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
				    		   AreaReportArray[6] = 0;
				    		   
				    	 }
				    	 else{
				    		 
				    		 BigDecimal a= (BigDecimal) AreaReportArray[6];
					           
					           
					           System.out.println(a);
					           sitePL3=Math.round(a.doubleValue());
					           System.out.println(sitePL3);
					        
				    		 
				    		 
				    		
				    	 }
				    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
				    		   AreaReportArray[7] = 0;
				    		   
				    	 }
				    	 else{
				    		 
				    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
					           
					           
					           System.out.println(a);
					           totalSites=Math.round(a.doubleValue());
					           System.out.println(totalSites);
					        
				    		 
				    	 }
				    		 
				    		
				    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
				    		   AreaReportArray[8] = 0;
				    		   
				    	 }
				    	 else{
				    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
					           
					           
					           System.out.println(a);
					           sumProfitLoss=Math.round(a.doubleValue());
					           System.out.println(sumProfitLoss);
					         
				    	 }
				       
				           System.out.println("The Profit and Loss of site1 is " +sitePL1);
			               System.out.println("The Profit and Loss of site2 is " +sitePL2);
			               System.out.println("The Profit and Loss of site1 is " +sitePL3);
			               System.out.println("The total count of site1 is " +totalnumberof2g);
			               System.out.println("The total count of site2 is " +totalnumberof2g3g);
			               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
			           
			               String[] arrayData = {"1",start,end,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
			               System.out.println("The array" +mapper.writeValueAsString(arrayData));
			               arrayData1 = arrayData;
			               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
			               data.add(arrayData);
				    	  }
				      }
   			       
   			       
                   
			    
			    }// End of profitable check
			    
			    
			    
			    
			    
// Begin the condition of LOSS
			    
			    if(StringUtils.equalsIgnoreCase(loss,"Loss")) {
			    	
			    	
			    	System.out.println("1st one is here");
			    
			    	if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
					{
			    	
			    		query1="Select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ( select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>= :param1 and end_date<= :param2 group by area_name) jointable on stable.area_name = jointable.area_name  where spl<=0";
					      
			    	
			    	
			    	
					}
			    	// IF LOSS AND ONLY Max IS CHOOSEN
			    	if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null")){
						System.out.println("Yara is here andddd");
						query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(SUM_PROFIT_LOSS)ASC) where spl <=0 and rownum=1))jointable on stable.area_name = jointable.area_name";						
						//System.out.println(Query);
				    	
						
					}
					// IF LOSS AND ONLY MIN IS CHOOSEN
                   if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null")){
                	   System.out.println("Yara isNOT HEREEEEEEEandddd");
			    		query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(SUM_PROFIT_LOSS)DESC) where spl <=0 and rownum=1))jointable on stable.area_name = jointable.area_name";						
						//System.out.println(Query);

					}
					
			    	// IF LOSS AND ONLY TECHNOLOGY IS CHOOSEN 
                   if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && min == null && max == null){
			    	
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
                		query1="Select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ( select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>= :param1 and end_date<= :param2 group by area_name) jointable on stable.area_name = jointable.area_name  where pl2g<=0";
					      
                	   
                	   }
                	   
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
                		   query1="Select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ( select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>= :param1 and end_date<= :param2 group by area_name) jointable on stable.area_name = jointable.area_name  where pl2g3g<=0";
					       
			    	
			    	
                	   
                	   }
                	   
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
                		   query1="Select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ( select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>= :param1 and end_date<= :param2 group by area_name) jointable on stable.area_name = jointable.area_name  where pl2g3g4g<=0";
					       
					      
                	   
                	   }
                	   
                	   
			    	
                   }
                   
                
                   // IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null")) {
                   
                   query1="select distinct  stable.area_name,jointable.n2g, jointable.pl2g, jointable.n2g3g, jointable.pl2g3g, jointable.n2g3g4g, jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date >=:param1 and end_date <=:param2 group by area_name order by spl DESC) where spl<=0 and rownum=1 ) UNION (select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl  from area_finance  where start_date >=:param1  and end_date <=:param2 group by area_name order by spl ASC) where spl<=0 and rownum=1)) jointable on stable.area_name = jointable.area_name";
                   
                   }
                   
                   // IF MAX AND MIN WITH TECHNOLOGY 
                   
                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
                   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
                	   
                		   query1="select distinct  stable.area_name,jointable.n2g, jointable.pl2g, jointable.n2g3g, jointable.pl2g3g, jointable.n2g3g4g, jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date >=:param1 and end_date <=:param2 group by area_name order by pl2g DESC) where pl2g<=0 and rownum=1 ) UNION (select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl  from area_finance  where start_date >=:param1  and end_date <=:param2 group by area_name order by pl2g ASC) where pl2g<=0 and rownum=1)) jointable on stable.area_name = jointable.area_name";
                	   
                	   }
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
                    	   
                		   query1="select distinct  stable.area_name,jointable.n2g, jointable.pl2g, jointable.n2g3g, jointable.pl2g3g, jointable.n2g3g4g, jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date >=:param1 and end_date <=:param2 group by area_name order by pl2g3g DESC) where pl2g3g<=0 and rownum=1 ) UNION (select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl  from area_finance  where start_date >=:param1  and end_date <=:param2 group by area_name order by pl2g3g ASC) where pl2g3g<=0 and rownum=1)) jointable on stable.area_name = jointable.area_name";
                    	   
                	   }
                	   
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
                    	   
                		   query1="select distinct  stable.area_name,jointable.n2g, jointable.pl2g, jointable.n2g3g, jointable.pl2g3g, jointable.n2g3g4g, jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date >=:param1 and end_date <=:param2 group by area_name order by pl2g3g4g DESC) where pl2g3g4g<=0 and rownum=1 ) UNION (select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name ,AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G) as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G) as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl  from area_finance  where start_date >=:param1  and end_date <=:param2 group by area_name order by pl2g3g4g ASC) where pl2g3g4g<=0 and rownum=1)) jointable on stable.area_name = jointable.area_name";
                    	   
                	   }
                	   
                	   
                	   
                   
                   }
                   
                   
                   
                   
                   
                   
                   //IF MAX IS CHECKED WITH TECHNOLOGY 
                   
                   
                   
                   
                   if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
                   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
                		   query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G)ASC) where pl2g <=0 and rownum=1))jointable on stable.area_name = jointable.area_name";
                	   
                	   
                	   }
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
                		   query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G_3G)ASC) where pl2g3g <=0 and rownum=1))jointable on stable.area_name = jointable.area_name";
                    	   
                    	   
                	   }
                	   
                	   
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
                    	   
                		   query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G_3G_4G)ASC) where pl2g3g4g <=0 and rownum=1))jointable on stable.area_name = jointable.area_name"; 
                    	   
                	   }
                	   
                	   
                	   
                	   
                   
                   }
                   
                   
                   // IF MIN IS CHECKED WITH TECHNOLOGY
                   if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
                       
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
                		   query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G)DESC) where pl2g <=0 and rownum=1))jointable on stable.area_name = jointable.area_name";
                		   
                	   
                	   }
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
                		   query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G_3G)DESC) where pl2g3g <=0 and rownum=1))jointable on stable.area_name = jointable.area_name";
                    	   
                    	   
                	   }
                	   
                	   
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
                    	   
                		   query1="select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join((select area_name,n2g,pl2g,n2g3g,pl2g3g,n2g3g4g,pl2g3g4g,totalsites,spl from(select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>=:param1 and end_date<=:param2 group by area_name order by sum(PROFIT_AND_LOSS_2G_3G_4G)DESC) where pl2g3g4g <=0 and rownum=1))jointable on stable.area_name = jointable.area_name"; 
                    	   
                	   }
                	   
                	   
                	   
                	   
                   
                   }
                   
                   
                   
                   
                   q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from AreaFinance t where t.startDate >= :param1 order by t.startDate asc");
   			    q1.setParameter("param1", StartDate);
   			    q1.setFirstResult(0);
   			    q1.setMaxResults(1);
   				String start = (String) q1.uniqueResult();
   				System.out.println("The start date is " +start);
   				
   				q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from AreaFinance t where t.endDate <= :param1 order by t.endDate desc");
   			    q2.setParameter("param1", EndDate);
   			    q2.setFirstResult(0);
   			    q2.setMaxResults(1);
   				String end = (String) q2.uniqueResult();
   				System.out.println("The end date is " +end);
   			
   			   
   				
		    	q=session.createSQLQuery(query1);
				q.setParameter("param1", StartDate);
			    q.setParameter("param2", EndDate);
			    
			    AreaReportList=q.list();
			     
   			
   			    
   			   
   			    
   			    System.out.println("The size is "+AreaReportList.size());  
				       
				      if(AreaReportList.size()>0) {
				    	  for(int i=0;i<AreaReportList.size();i++) {
				    		  
				    		  AreaReportArray = (Object[]) AreaReportList.toArray()[i];
			            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
				    		  
				    	  
				    	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
				    		   AreaReportArray[0] ="";
				    		   System.out.println("passsssss");
				    	 }
				    	 else {
				           areas =String.valueOf(AreaReportArray[0])  ;
				    	 }
				    	  
				    	  
				    	  
				    	  
				    	   if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
				    		   AreaReportArray[1] = 0.0;
				    		   System.out.println("passsssss");
				    	 }
				    	 else {
				    		 
				    		 
				    		BigDecimal a= (BigDecimal) AreaReportArray[1];
				           
				           
				           System.out.println(a);
				           totalnumberof2g=Math.round(a.doubleValue());
				           System.out.println(totalnumberof2g);
				           
				           
				    	 }
				         if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == ""){ 
				    		   AreaReportArray[2] = 0.0;
			             }
			             else{ 
			            	 
			            	 BigDecimal a= (BigDecimal) AreaReportArray[2];
					           
					           
					           System.out.println(a);
					           sitePL1=Math.round(a.doubleValue());
					           System.out.println(sitePL1);
					           
			            	 
			            	 
			             }
				         if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == ""){ 
			    		       AreaReportArray[3] = 0.0;
				         }
				         else {
				        	 
				        	 BigDecimal a= (BigDecimal) AreaReportArray[3];
					           
					           
					           System.out.println(a);
					           totalnumberof2g3g=Math.round(a.doubleValue());
					           System.out.println(totalnumberof2g3g);
					        
				        	 
				        	 
				           
				         } 
				    	 if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == "") { 
				    		   AreaReportArray[4] = 0.0;
				    	 }
				    	 else {
				    		 

				        	 BigDecimal a= (BigDecimal) AreaReportArray[4];
					           
					           
					           System.out.println(a);
					           sitePL2=Math.round(a.doubleValue());
					           System.out.println(sitePL2);
					        
				    		 
				    		 
				    		
				    	 }
				    	 if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == "") { 
				    		   AreaReportArray[5] = 0;
				    	 }  
				    	 else{
				    		 
				    		 BigDecimal a= (BigDecimal) AreaReportArray[5];
					           
					           
					           System.out.println(a);
					           totalnumberof2g3g4g=Math.round(a.doubleValue());
					           System.out.println(totalnumberof2g3g4g);
					        
				    		 
				    		 
				    		 
				    		 
				    		
				    	 }
				    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
				    		   AreaReportArray[6] = 0;
				    		   
				    	 }
				    	 else{
				    		 
				    		 BigDecimal a= (BigDecimal) AreaReportArray[6];
					           
					           
					           System.out.println(a);
					           sitePL3=Math.round(a.doubleValue());
					           System.out.println(sitePL3);
					        
				    		 
				    		 
				    		
				    	 }
				    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
				    		   AreaReportArray[7] = 0;
				    		   
				    	 }
				    	 else{
				    		 
				    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
					           
					           
					           System.out.println(a);
					           totalSites=Math.round(a.doubleValue());
					           System.out.println(totalSites);
					        
				    		 
				    	 }
				    		 
				    		
				    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
				    		   AreaReportArray[8] = 0;
				    		   
				    	 }
				    	 else{
				    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
					           
					           
					           System.out.println(a);
					           sumProfitLoss=Math.round(a.doubleValue());
					           System.out.println(sumProfitLoss);
					         
				    	 }
				       
				           System.out.println("The Profit and Loss of site1 is " +sitePL1);
			               System.out.println("The Profit and Loss of site2 is " +sitePL2);
			               System.out.println("The Profit and Loss of site1 is " +sitePL3);
			               System.out.println("The total count of site1 is " +totalnumberof2g);
			               System.out.println("The total count of site2 is " +totalnumberof2g3g);
			               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
			           
			               String[] arrayData = {"1",start,end,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
			               System.out.println("The array" +mapper.writeValueAsString(arrayData));
			               arrayData1 = arrayData;
			               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
			               data.add(arrayData);
				    	  }
				      }
   			       
   			       
                   
			    
			    }// End of LOSS check
			    
			    
			    
			    
			    
			    
			    
			    
			
		    }// End if the area is not selected 
		    
		    
		   // if the area is selected  
		    
		    else {
		    	
		    	
		    	
		    	
		    	q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from AreaFinance t where t.startDate >= :param1 order by t.startDate asc");
			    q1.setParameter("param1", StartDate);
			    q1.setFirstResult(0);
			    q1.setMaxResults(1);
				String start = (String) q1.uniqueResult();
				System.out.println("The start date is " +start);
				
				q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from AreaFinance t where t.endDate <= :param1 order by t.endDate desc");
			    q2.setParameter("param1", EndDate);
			    q2.setFirstResult(0);
			    q2.setMaxResults(1);
				String end = (String) q2.uniqueResult();
				System.out.println("The end date is " +end);
			
			   
			      String Query="Select distinct stable.area_name,jointable.n2g,jointable.pl2g,jointable.n2g3g,jointable.pl2g3g,jointable.n2g3g4g,jointable.pl2g3g4g,jointable.totalsites,jointable.spl from area_finance stable right join ( select area_name, AVG(NO_2G_SITES)as n2g,SUM(PROFIT_AND_LOSS_2G)as pl2g,AVG(NO_2G_3G_SITES)as n2g3g,SUM(PROFIT_AND_LOSS_2G_3G)as pl2g3g,AVG(NO_2G_3G_4G_SITES)as n2g3g4g,SUM(PROFIT_AND_LOSS_2G_3G_4G)as pl2g3g4g,AVG(TOTAL_NO_SITES)as totalsites,SUM(SUM_PROFIT_LOSS)as spl from area_finance where start_date>= :param1 and end_date<= :param2 and area_name= :param3 group by area_name) jointable on stable.area_name = jointable.area_name";
			       q = session.createSQLQuery(Query);

				  
			       q.setParameter("param1", StartDate);
			       q.setParameter("param2", EndDate);
			       q.setParameter("param3", area);
			       
			       
			       
			      
			       
			       
			       AreaReportList=q.list();
			       
			       countT=AreaReportList.size();
			       
			      if(AreaReportList.size()>0) {
			    	  
			    		  
			    		  AreaReportArray = (Object[]) AreaReportList.toArray()[0];
		            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
			    		  
			    	  
			    	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
			    		   AreaReportArray[0] ="";
			    		   System.out.println("passsssss");
			    	 }
			    	 else {
			           areas =String.valueOf(AreaReportArray[0])  ;
			    	 }
			    	  
			    	  
			    	  
			    	  
			    	   if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
			    		   AreaReportArray[1] = 0.0;
			    		   System.out.println("passsssss");
			    	 }
			    	 else {
			    		 
			    		 
			    		BigDecimal a= (BigDecimal) AreaReportArray[1];
			           
			           
			           System.out.println(a);
			           totalnumberof2g=Math.round(a.doubleValue());
			           System.out.println(totalnumberof2g);
			           
			           
			    	 }
			         if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == ""){ 
			    		   AreaReportArray[2] = 0.0;
		             }
		             else{ 
		            	 
		            	 BigDecimal a= (BigDecimal) AreaReportArray[2];
				           
				           
				           System.out.println(a);
				           sitePL1=Math.round(a.doubleValue());
				           System.out.println(sitePL1);
				           
		            	 
		            	 
		             }
			         if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == ""){ 
		    		       AreaReportArray[3] = 0.0;
			         }
			         else {
			        	 
			        	 BigDecimal a= (BigDecimal) AreaReportArray[3];
				           
				           
				           System.out.println(a);
				           totalnumberof2g3g=Math.round(a.doubleValue());
				           System.out.println(totalnumberof2g3g);
				        
			        	 
			        	 
			           
			         } 
			    	 if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == "") { 
			    		   AreaReportArray[4] = 0.0;
			    	 }
			    	 else {
			    		 

			        	 BigDecimal a= (BigDecimal) AreaReportArray[4];
				           
				           
				           System.out.println(a);
				           sitePL2=Math.round(a.doubleValue());
				           System.out.println(sitePL2);
				        
			    		 
			    		 
			    		
			    	 }
			    	 if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == "") { 
			    		   AreaReportArray[5] = 0;
			    	 }  
			    	 else{
			    		 
			    		 BigDecimal a= (BigDecimal) AreaReportArray[5];
				           
				           
				           System.out.println(a);
				           totalnumberof2g3g4g=Math.round(a.doubleValue());
				           System.out.println(totalnumberof2g3g4g);
				        
			    		 
			    		 
			    		 
			    		 
			    		
			    	 }
			    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
			    		   AreaReportArray[6] = 0;
			    		   
			    	 }
			    	 else{
			    		 
			    		 BigDecimal a= (BigDecimal) AreaReportArray[6];
				           
				           
				           System.out.println(a);
				           sitePL3=Math.round(a.doubleValue());
				           System.out.println(sitePL3);
				        
			    		 
			    		 
			    		
			    	 }
			    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
			    		   AreaReportArray[7] = 0;
			    		   
			    	 }
			    	 else{
			    		 
			    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
				           
				           
				           System.out.println(a);
				           totalSites=Math.round(a.doubleValue());
				           System.out.println(totalSites);
				        
			    		 
			    	 }
			    		 
			    		
			    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
			    		   AreaReportArray[8] = 0;
			    		   
			    	 }
			    	 else{
			    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
				           
				           
				           System.out.println(a);
				           sumProfitLoss=Math.round(a.doubleValue());
				           System.out.println(sumProfitLoss);
				         
			    	 }
			       
			           System.out.println("The Profit and Loss of site1 is " +sitePL1);
		               System.out.println("The Profit and Loss of site2 is " +sitePL2);
		               System.out.println("The Profit and Loss of site1 is " +sitePL3);
		               System.out.println("The total count of site1 is " +totalnumberof2g);
		               System.out.println("The total count of site2 is " +totalnumberof2g3g);
		               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
		           
		               String[] arrayData = {"1",start,end,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
		               System.out.println("The array" +mapper.writeValueAsString(arrayData));
		               arrayData1 = arrayData;
		               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
		               data.add(arrayData);
		               
		              
			   
			       
			        
			  
				
				
				
			    
		    }// end for if
			     
		    	
		    	
		    }// End of the else 
		System.out.println("the AreaReportList is "+mapper.writeValueAsString(data));
 	
		
		}///  End of Accumlated
		
		// Start of Monthly Condition 
		if(StringUtils.equalsIgnoreCase(checkVal,"Monthly")) {
			
			String queryMonthly="";
			
			String startdate="";
			String enddate="";
			// if area is not selected 
			
			// if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
			// Monthly is only checked without Technologies
				 if((StringUtils.equalsIgnoreCase(profitable,"null") || profitable == null || profitable == "") && (StringUtils.equalsIgnoreCase(loss,"null") || loss == null || loss == "")&& (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") &&(StringUtils.equalsIgnoreCase(max,"null") || max == null || max == "") && (StringUtils.equalsIgnoreCase(min,"null") || min == null || min == "")) {
					 System.out.println("*** before queryMonthly "+ queryMonthly);
					 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						 System.out.println("Fatimah if");
						 queryMonthly="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where start_date >= :param1 and end_date <= :param2 order by start_date DESC" ;

					 }
					 else {
						 System.out.println("Fatimah else");
						 queryMonthly="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 order by start_date DESC" ;
					 }
					 System.out.println("*** after queryMonthly "+ queryMonthly);
					 q = session.createSQLQuery(queryMonthly);


				
				       q.setParameter("param1", StartDate);
				       q.setParameter("param2", EndDate);
				       if(area != null) {
							q.setParameter("param3", area);
						}


				       AreaReportList=q.list();
				       
				       countT=AreaReportList.size();
				       System.out.println("countT is "+ countT);
				       
				       if(AreaReportList.size()>0) {
					    	  for(int i=0;i<AreaReportList.size();i++) {
					    		  
					    		  AreaReportArray = (Object[]) AreaReportList.toArray()[i];
				            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
					    		  
					    	  
				            	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
						    		   AreaReportArray[0] ="";
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						           startdate =String.valueOf(AreaReportArray[0])  ;
						    	 }
						    	  
				            	  
				            	  if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
						    		   AreaReportArray[1] ="";
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						           enddate =String.valueOf(AreaReportArray[1])  ;
						    	 }
						    	  
				            	  
				            	  
				            	  
				            	  
					    	  if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
					    		   AreaReportArray[2] ="";
					    		   System.out.println("passsssss");
					    	 }
					    	 else {
					           areas =String.valueOf(AreaReportArray[2])  ;
					    	 }
					    	  
					    	  
					    	  
					    	  
					    	   if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
					    		   AreaReportArray[3] = 0.0;
					    		   System.out.println("passsssss");
					    	 }
					    	 else {
					    		 
					    		 
					    		BigDecimal a= (BigDecimal) AreaReportArray[3];
					           
					           
					           System.out.println(a);
					           totalnumberof2g=Math.round(a.doubleValue());
					           System.out.println(totalnumberof2g);
					           
					           
					    	 }
					         if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
					    		   AreaReportArray[4] = 0.0;
				             }
				             else{ 
				            	 
				            	 BigDecimal a= (BigDecimal) AreaReportArray[4];
						           
						           
						           System.out.println(a);
						           sitePL1=Math.round(a.doubleValue());
						           System.out.println(sitePL1);
						           
				            	 
				            	 
				             }
					         if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
				    		       AreaReportArray[5] = 0.0;
					         }
					         else {
					        	 
					        	 BigDecimal a= (BigDecimal) AreaReportArray[5];
						           
						           
						           System.out.println(a);
						           totalnumberof2g3g=Math.round(a.doubleValue());
						           System.out.println(totalnumberof2g3g);
						        
					        	 
					        	 
					           
					         } 
					    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
					    		   AreaReportArray[6] = 0.0;
					    	 }
					    	 else {
					    		 

					        	 BigDecimal a= (BigDecimal) AreaReportArray[6];
						           
						           
						           System.out.println(a);
						           sitePL2=Math.round(a.doubleValue());
						           System.out.println(sitePL2);
						        
					    		 
					    		 
					    		
					    	 }
					    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
					    		   AreaReportArray[7] = 0;
					    	 }  
					    	 else{
					    		 
					    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
						           
						           
						           System.out.println(a);
						           totalnumberof2g3g4g=Math.round(a.doubleValue());
						           System.out.println(totalnumberof2g3g4g);
						        
					    		 
					    		 
					    		 
					    		 
					    		
					    	 }
					    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
					    		   AreaReportArray[8] = 0;
					    		   
					    	 }
					    	 else{
					    		 
					    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
						           
						           
						           System.out.println(a);
						           sitePL3=Math.round(a.doubleValue());
						           System.out.println(sitePL3);
						        
					    		 
					    		 
					    		
					    	 }
					    	 if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
					    		   AreaReportArray[9] = 0;
					    		   
					    	 }
					    	 else{
					    		 
					    		 BigDecimal a= (BigDecimal) AreaReportArray[9];
						           
						           
						           System.out.println(a);
						           totalSites=Math.round(a.doubleValue());
						           System.out.println(totalSites);
						        
					    		 
					    	 }
					    		 
					    		
					    	 if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
					    		   AreaReportArray[10] = 0;
					    		   
					    	 }
					    	 else{
					    		 BigDecimal a= (BigDecimal) AreaReportArray[10];
						           
						           
						           System.out.println(a);
						           sumProfitLoss=Math.round(a.doubleValue());
						           System.out.println(sumProfitLoss);
						         
					    	 }
					     
					           System.out.println("The Profit and Loss of site1 is " +sitePL1);
				               System.out.println("The Profit and Loss of site2 is " +sitePL2);
				               System.out.println("The Profit and Loss of site1 is " +sitePL3);
				               System.out.println("The total count of site1 is " +totalnumberof2g);
				               System.out.println("The total count of site2 is " +totalnumberof2g3g);
				               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
				           
				               String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
				               System.out.println("The array" +mapper.writeValueAsString(arrayData));
				               arrayData1 = arrayData;
				               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				               data.add(arrayData);
				               
				               totalPLF += (sitePL1+sitePL2 +sitePL3);
							    totalSumNumberof2g += totalnumberof2g;
							    totalSumNumberof2g3g += totalnumberof2g3g;
							    totalSumNumberof2g3g4g += totalnumberof2g3g4g;
					       }// End for for loop
					       
					        
					  System.out.println("totalPLF" +totalPLF);
					  System.out.println("TotalNumber of 2g" +totalSumNumberof2g);
					  System.out.println("totalnumberof2g3g" +totalSumNumberof2g3g);
					  System.out.println("totalnumberof2g3g4g" +totalSumNumberof2g3g4g);
					  
						
						
						
					    
				    }// end for if
					      
					      rtn.put("totalProfitLoss",totalPLF );
						    rtn.put("totalnumberof2g",totalSumNumberof2g );
						    rtn.put("totalnumberof2g3g",totalSumNumberof2g3g);
							rtn.put("totalnumberof2g3g4g",totalSumNumberof2g3g4g );
							rtn.put("totalAreas", countT);
					    
				       
				       
				 
				 
				 }// end of the condition if monthly only selcted
				 


/////////////////////////////////////////////////////////////////////////////////// ahmad ////////////////////////////////////////////////////////////////////////////////



				//BEGIN MAX CONDITION 


				if(StringUtils.equalsIgnoreCase(max,"Max") && profitable == null && loss == null)

				{

				if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")
				{
					System.out.println("Passes here          Yaraaaaa iss hereee");
					 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G, "
									+"NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where SUM_PROFIT_LOSS IN(Select max(SUM_PROFIT_LOSS)" 
									+"from area_finance where start_date >=:param1 and end_date <= :param2 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

					 }
					 else {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G, "
									+"NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where SUM_PROFIT_LOSS IN(Select max(SUM_PROFIT_LOSS)" 
									+"from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

					 }
				
				}


				if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {


				if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
				{ 
					 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {

							query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
							+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select max(PROFIT_AND_LOSS_2G)"
							+ " from area_finance where start_date >= :param1 and end_date <= :param2 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

					 }
					 else {

							query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
							+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select max(PROFIT_AND_LOSS_2G)"
							+ " from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

					 }

				   
				} 

				if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
				{ 
					 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
							query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
									+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select max(PROFIT_AND_LOSS_2G_3G)"
									+ " from area_finance where start_date >= :param1 and end_date <= :param2 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 
								
					 }
					 else {
							query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
									+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select max(PROFIT_AND_LOSS_2G_3G)"
									+ " from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 
								
					 }

				
				} 

				if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
				{ 
					 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
									+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select max(PROFIT_AND_LOSS_2G_3G_4G)"
									+ " from area_finance where start_date >= :param1 and end_date <= :param2 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
					 }
					 else {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
									+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select max(PROFIT_AND_LOSS_2G_3G_4G)"
									+ " from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
					 }
					} 


				}	                	   
				q = session.createSQLQuery(query1);
            	q.setParameter("param1", StartDate);
				q.setParameter("param2", EndDate);
				if( area != null) {
					q.setParameter("param3", area);
				}

				AreaReportList=q.list();

				countT=AreaReportList.size();

				if(AreaReportList.size()>0) {
					for(int i=0;i<AreaReportList.size();i++) {

				AreaReportArray = (Object[]) AreaReportList.toArray()[i];
				System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));


				if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
				AreaReportArray[0] ="";
				System.out.println("passsssss");
				}
				else {
				startdate =String.valueOf(AreaReportArray[0])  ;
				}


				if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
				AreaReportArray[1] ="";
				System.out.println("passsssss");
				}
				else {
				enddate =String.valueOf(AreaReportArray[1])  ;
				}





				if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
				AreaReportArray[2] ="";
				System.out.println("passsssss");
				}
				else {
				areas =String.valueOf(AreaReportArray[2])  ;
				}




				if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
				AreaReportArray[3] = 0.0;
				System.out.println("passsssss");
				}
				else {


				BigDecimal a= (BigDecimal) AreaReportArray[3];


				System.out.println(a);
				totalnumberof2g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g);


				}
				if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
				AreaReportArray[4] = 0.0;
				}
				else{ 

				BigDecimal a= (BigDecimal) AreaReportArray[4];


				System.out.println(a);
				sitePL1=Math.round(a.doubleValue());
				System.out.println(sitePL1);



				}
				if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
				AreaReportArray[5] = 0.0;
				}
				else {

				BigDecimal a= (BigDecimal) AreaReportArray[5];


				System.out.println(a);
				totalnumberof2g3g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g3g);




				} 
				if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
				AreaReportArray[6] = 0.0;
				}
				else {


				BigDecimal a= (BigDecimal) AreaReportArray[6];


				System.out.println(a);
				sitePL2=Math.round(a.doubleValue());
				System.out.println(sitePL2);




				}
				if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
				AreaReportArray[7] = 0;
				}  
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[7];


				System.out.println(a);
				totalnumberof2g3g4g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g3g4g);






				}
				if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
				AreaReportArray[8] = 0;

				}
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[8];


				System.out.println(a);
				sitePL3=Math.round(a.doubleValue());
				System.out.println(sitePL3);




				}
				if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
				AreaReportArray[9] = 0;

				}
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[9];


				System.out.println(a);
				totalSites=Math.round(a.doubleValue());
				System.out.println(totalSites);


				}


				if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
				AreaReportArray[10] = 0;

				}
				else{
				BigDecimal a= (BigDecimal) AreaReportArray[10];


				System.out.println(a);
				sumProfitLoss=Math.round(a.doubleValue());
				System.out.println(sumProfitLoss);

				}

				System.out.println("The Profit and Loss of site1 is " +sitePL1);
				System.out.println("The Profit and Loss of site2 is " +sitePL2);
				System.out.println("The Profit and Loss of site1 is " +sitePL3);
				System.out.println("The total count of site1 is " +totalnumberof2g);
				System.out.println("The total count of site2 is " +totalnumberof2g3g);
				System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
				
				String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
				System.out.println("The array" +mapper.writeValueAsString(arrayData));
				arrayData1 = arrayData;
				System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				data.add(arrayData);


				} // end for loop

				}// end for if





				}// end of condition







				//BEGIN MIN CONDITION 

				if(StringUtils.equalsIgnoreCase(min,"Min") && profitable == null && loss == null)

				{

				if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")
				{
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select min(PROFIT_AND_LOSS_2G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

					}
					else {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select min(PROFIT_AND_LOSS_2G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2  and area_name=:param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

					}
			
				}


				if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {


				if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select min(PROFIT_AND_LOSS_2G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2  group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 
					}
					else {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select min(PROFIT_AND_LOSS_2G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 
					}

				
				} 

				if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select min(PROFIT_AND_LOSS_2G_3G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2  group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 
								
					}
					else {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select min(PROFIT_AND_LOSS_2G_3G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 
								
					}

				} 

				if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select min(PROFIT_AND_LOSS_2G_3G_4G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2 and  group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 
							
					}
					else {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select min(PROFIT_AND_LOSS_2G_3G_4G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2 area_name=:param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 
							
					}
				
				
				} 


				}
				
				q=session.createSQLQuery(query1);
				q.setParameter("param1", StartDate);
			    q.setParameter("param2", EndDate);
			    if( area != null ) {
					q.setParameter("param3", area);
				}
			    AreaReportList=q.list();
				
					                	    
				

				countT=AreaReportList.size();

				if(AreaReportList.size()>0) {
					for(int i=0;i<AreaReportList.size();i++) {

				AreaReportArray = (Object[]) AreaReportList.toArray()[i];
				System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));


				if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
				AreaReportArray[0] ="";
				System.out.println("passsssss");
				}
				else {
				startdate =String.valueOf(AreaReportArray[0])  ;
				}


				if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
				AreaReportArray[1] ="";
				System.out.println("passsssss");
				}
				else {
				enddate =String.valueOf(AreaReportArray[1])  ;
				}





				if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
				AreaReportArray[2] ="";
				System.out.println("passsssss");
				}
				else {
				areas =String.valueOf(AreaReportArray[2])  ;
				}




				if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
				AreaReportArray[3] = 0.0;
				System.out.println("passsssss");
				}
				else {


				BigDecimal a= (BigDecimal) AreaReportArray[3];


				System.out.println(a);
				totalnumberof2g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g);


				}
				if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
				AreaReportArray[4] = 0.0;
				}
				else{ 

				BigDecimal a= (BigDecimal) AreaReportArray[4];


				System.out.println(a);
				sitePL1=Math.round(a.doubleValue());
				System.out.println(sitePL1);



				}
				if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
				AreaReportArray[5] = 0.0;
				}
				else {

				BigDecimal a= (BigDecimal) AreaReportArray[5];


				System.out.println(a);
				totalnumberof2g3g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g3g);




				} 
				if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
				AreaReportArray[6] = 0.0;
				}
				else {


				BigDecimal a= (BigDecimal) AreaReportArray[6];


				System.out.println(a);
				sitePL2=Math.round(a.doubleValue());
				System.out.println(sitePL2);




				}
				if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
				AreaReportArray[7] = 0;
				}  
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[7];


				System.out.println(a);
				totalnumberof2g3g4g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g3g4g);






				}
				if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
				AreaReportArray[8] = 0;

				}
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[8];


				System.out.println(a);
				sitePL3=Math.round(a.doubleValue());
				System.out.println(sitePL3);




				}
				if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
				AreaReportArray[9] = 0;

				}
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[9];


				System.out.println(a);
				totalSites=Math.round(a.doubleValue());
				System.out.println(totalSites);


				}


				if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
				AreaReportArray[10] = 0;

				}
				else{
				BigDecimal a= (BigDecimal) AreaReportArray[10];


				System.out.println(a);
				sumProfitLoss=Math.round(a.doubleValue());
				System.out.println(sumProfitLoss);

				}

				System.out.println("The Profit and Loss of site1 is " +sitePL1);
				System.out.println("The Profit and Loss of site2 is " +sitePL2);
				System.out.println("The Profit and Loss of site1 is " +sitePL3);
				System.out.println("The total count of site1 is " +totalnumberof2g);
				System.out.println("The total count of site2 is " +totalnumberof2g3g);
				System.out.println("The total count of site3 is " +totalnumberof2g3g4g);

				String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
				System.out.println("The array" +mapper.writeValueAsString(arrayData));
				arrayData1 = arrayData;
				System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				data.add(arrayData);

					}


				}// end for if





				}// end of condition




				//BEGIN TECHNOLOGY CONDITION 		



				if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g")||StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && profitable == null && loss == null && min == null && max == null)
				{

				if(StringUtils.equalsIgnoreCase(technologies,"2g"))
				{
					 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G IN ( select (PROFIT_AND_LOSS_2G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2) order by start_date DESC " ;
					 }
					 else {
						 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G IN ( select (PROFIT_AND_LOSS_2G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3) order by start_date DESC " ;
					 }

				
				                    
			
				}

				if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
					            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
					            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
					            +" from area_finance where PROFIT_AND_LOSS_2G_3G IN ( select (PROFIT_AND_LOSS_2G_3G) from area_finance"
					            +" where start_date >= :param1 and end_date <= :param2) order by start_date DESC " ;
					}
					 else {
						 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G_3G IN ( select (PROFIT_AND_LOSS_2G_3G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3) order by start_date DESC " ;
					 }

					
				                   
			
				}

				if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
					            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
					            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
					            +" from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN ( select (PROFIT_AND_LOSS_2G_3G_4G) from area_finance"
					            +" where start_date >= :param1 and end_date <= :param2) order by start_date DESC " ;
					}
					 else {
						 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN ( select (PROFIT_AND_LOSS_2G_3G_4G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3) order by start_date DESC " ;
					 }

					
				                   
				}


				q = session.createSQLQuery(query1);



				q.setParameter("param1", StartDate);
				q.setParameter("param2", EndDate);
				if( area != null) {
					q.setParameter("param3", area);
				}
				





				AreaReportList=q.list();

				countT=AreaReportList.size();

				if(AreaReportList.size()>0) {
					for(int i=0;i<AreaReportList.size();i++) {

				AreaReportArray = (Object[]) AreaReportList.toArray()[i];
				System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));


				if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
				AreaReportArray[0] ="";
				System.out.println("passsssss");
				}
				else {
				startdate =String.valueOf(AreaReportArray[0])  ;
				}


				if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
				AreaReportArray[1] ="";
				System.out.println("passsssss");
				}
				else {
				enddate =String.valueOf(AreaReportArray[1])  ;
				}





				if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
				AreaReportArray[2] ="";
				System.out.println("passsssss");
				}
				else {
				areas =String.valueOf(AreaReportArray[2])  ;
				}




				if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
				AreaReportArray[3] = 0.0;
				System.out.println("passsssss");
				}
				else {


				BigDecimal a= (BigDecimal) AreaReportArray[3];


				System.out.println(a);
				totalnumberof2g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g);


				}
				if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
				AreaReportArray[4] = 0.0;
				}
				else{ 

				BigDecimal a= (BigDecimal) AreaReportArray[4];


				System.out.println(a);
				sitePL1=Math.round(a.doubleValue());
				System.out.println(sitePL1);



				}
				if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
				AreaReportArray[5] = 0.0;
				}
				else {

				BigDecimal a= (BigDecimal) AreaReportArray[5];


				System.out.println(a);
				totalnumberof2g3g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g3g);




				} 
				if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
				AreaReportArray[6] = 0.0;
				}
				else {


				BigDecimal a= (BigDecimal) AreaReportArray[6];


				System.out.println(a);
				sitePL2=Math.round(a.doubleValue());
				System.out.println(sitePL2);




				}
				if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
				AreaReportArray[7] = 0;
				}  
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[7];


				System.out.println(a);
				totalnumberof2g3g4g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g3g4g);






				}
				if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
				AreaReportArray[8] = 0;

				}
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[8];


				System.out.println(a);
				sitePL3=Math.round(a.doubleValue());
				System.out.println(sitePL3);




				}
				if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
				AreaReportArray[9] = 0;

				}
				else{
					
				BigDecimal a= (BigDecimal) AreaReportArray[9];


				System.out.println(a);
				totalSites=Math.round(a.doubleValue());
				System.out.println(totalSites);


				}


				if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
				AreaReportArray[10] = 0;

				}
				else{
				BigDecimal a= (BigDecimal) AreaReportArray[10];


				System.out.println(a);
				sumProfitLoss=Math.round(a.doubleValue());
				System.out.println(sumProfitLoss);

				}

				System.out.println("The Profit and Loss of site1 is " +sitePL1);
				System.out.println("The Profit and Loss of site2 is " +sitePL2);
				System.out.println("The Profit and Loss of site1 is " +sitePL3);
				System.out.println("The total count of site1 is " +totalnumberof2g);
				System.out.println("The total count of site2 is " +totalnumberof2g3g);
				System.out.println("The total count of site3 is " +totalnumberof2g3g4g);

				String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
				System.out.println("The array" +mapper.writeValueAsString(arrayData));
				arrayData1 = arrayData;
				System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				data.add(arrayData);


					}

				}// end for if





				}// end of condition





				//BEGIN LOSS CONDITION

				if(StringUtils.equalsIgnoreCase(loss,"Loss")) 
				{ 
				if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
				{ 
					 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
									+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where start_date >= :param1 and end_date <= :param2 and SUM_PROFIT_LOSS <=0 order by start_date DESC";
								
					 }
					 else {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
									+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS <=0 order by start_date DESC";
								
					 }
					}



				//IF MAX ONLY IS CHECKED


				if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(Select max(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and SUM_PROFIT_LOSS <=0  group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
					}
					 else {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
									+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(Select max(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS <=0  group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
					 }
				 
				}




				//IF MIN ONLY IS CHECKED


				if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(Select min(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and SUM_PROFIT_LOSS <=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 
						
					}
					 else {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
									+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(Select min(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS <=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 
							
					 }
				}





				//IF TECHNOLOGY ONLY IS CHECKED


				if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
				&& min == null && max == null) 
				{ 
				if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G IN (select (PROFIT_AND_LOSS_2G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G <=0 ) order by start_date DESC  " ;
					}
					 else {
						 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G IN (select (PROFIT_AND_LOSS_2G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G <=0 ) order by start_date DESC  " ;
					 }

				}
				if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G_3G IN (select (PROFIT_AND_LOSS_2G_3G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G <=0 ) order by start_date DESC  " ;
					}
					 else {
						 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G_3G IN (select (PROFIT_AND_LOSS_2G_3G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G <=0 ) order by start_date DESC  " ;
					 }

				}

				if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select (PROFIT_AND_LOSS_2G_3G_4G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G_4G <=0 ) order by start_date DESC  " ;
					}
					 else {
						 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select (PROFIT_AND_LOSS_2G_3G_4G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G_4G <=0 ) order by start_date DESC  " ;
					 }

				}
				}



				//IF MAX AND MIN WITHOUT TECHNOLOGY


				if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
					query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(select max(SUM_PROFIT_LOSS) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and SUM_PROFIT_LOSS <=0 group by TO_CHAR(start_date, 'YYYY-MM')))"
								+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where "
								+" SUM_PROFIT_LOSS IN (select min(SUM_PROFIT_LOSS) from area_finance where start_date >= :param1 and end_date <= :param2 and SUM_PROFIT_LOSS<=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC ";
							
					}
					 else {
						 query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
									+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(select max(SUM_PROFIT_LOSS) "
									+" from area_finance where start_date >= :param1 and end_date <= :param2  and area_name=:param3  and SUM_PROFIT_LOSS <=0 group by TO_CHAR(start_date, 'YYYY-MM')))"
									+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where "
									+" SUM_PROFIT_LOSS IN (select min(SUM_PROFIT_LOSS) from area_finance where start_date >= :param1 and end_date <= :param2  and area_name=:param3  and SUM_PROFIT_LOSS<=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC ";
								
						}

					} 





				//IF ONLY MAX WITH TECHNOLOGY


				if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
				&& min == null && StringUtils.equalsIgnoreCase(max,"Max")) 
				{ 
				if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select max(PROFIT_AND_LOSS_2G) from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G<=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

					}
					 else {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select max(PROFIT_AND_LOSS_2G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G<=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

					 }

				
				} 

				if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select max(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G<=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

					}
					 else {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select max(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G<=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

					 }

				} 

				if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select max(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G_4G<=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

					}
					 else {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select max(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G_4G<=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

					 }
				

				} 

				}




				//IF MIN WITH TECHNOLOGY



				if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
				&& StringUtils.equalsIgnoreCase(min,"Min") && max == null) 
				{ 
				if(StringUtils.equalsIgnoreCase(technologies,"2g"))
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select min(PROFIT_AND_LOSS_2G) from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G<=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

					}
					 else {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select min(PROFIT_AND_LOSS_2G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G<=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

					 }

				
				} 

				if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select min(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G<=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

					}
					 else {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select min(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G<=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

					 }

				

				} 

				if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
				{
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select min(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G_4G<=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

					}
					 else {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select min(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G_4G<=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

					 }
				

				} 

				}


				//IF MAX AND MIN WITH TECHNOLOGY



				if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || 
				StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) 
				{  

				if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G IN (select max(PROFIT_AND_LOSS_2G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G<=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
								+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G IN (select min(PROFIT_AND_LOSS_2G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G<=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, PROFIT_AND_LOSS_2G DESC";

					}
					 else {
						 System.out.println("************** 2g with area");
						 query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G IN (select max(PROFIT_AND_LOSS_2G) "
									+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G<=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
									+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G IN (select min(PROFIT_AND_LOSS_2G) "
									+" from area_finance where start_date >= :param1 and end_date <= :param2 and  area_name=:param3 and PROFIT_AND_LOSS_2G<=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC";

					 }

					
				}


				if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G IN (select max(PROFIT_AND_LOSS_2G_3G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G<=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
								+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G IN (select min(PROFIT_AND_LOSS_2G_3G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G<=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, PROFIT_AND_LOSS_2G_3G DESC";

					}
					 else {
						 query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G IN (select max(PROFIT_AND_LOSS_2G_3G) "
									+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G<=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
									+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G IN (select min(PROFIT_AND_LOSS_2G_3G) "
									+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G<=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, PROFIT_AND_LOSS_2G_3G DESC";

					 }

				
				}

				if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
				{ 
					if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select max(PROFIT_AND_LOSS_2G_3G_4G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G_4G<=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
								+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select min(PROFIT_AND_LOSS_2G_3G_4G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G_4G<=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, PROFIT_AND_LOSS_2G_3G_4G DESC";

					}
					 else {
						 query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select max(PROFIT_AND_LOSS_2G_3G_4G) "
									+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G_4G<=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
									+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select min(PROFIT_AND_LOSS_2G_3G_4G) "
									+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G_4G<=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, PROFIT_AND_LOSS_2G_3G_4G DESC";

					 }

					
				}
				}





				q=session.createSQLQuery(query1);

				q.setParameter("param1", StartDate);
				q.setParameter("param2", EndDate);
				if( area != null) {
					System.out.println("**************Passing area param");
					q.setParameter("param3", area);
				}

				AreaReportList=q.list();

				countT=AreaReportList.size();

				if(AreaReportList.size()>0) {
					for(int i=0;i<AreaReportList.size();i++) {

				AreaReportArray = (Object[]) AreaReportList.toArray()[i];
				System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));


				if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
				AreaReportArray[0] ="";
				System.out.println("passsssss");
				}
				else {
				startdate =String.valueOf(AreaReportArray[0])  ;
				}


				if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
				AreaReportArray[1] ="";
				System.out.println("passsssss");
				}
				else {
				enddate =String.valueOf(AreaReportArray[1])  ;
				}





				if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
				AreaReportArray[2] ="";
				System.out.println("passsssss");
				}
				else {
				areas =String.valueOf(AreaReportArray[2])  ;
				}




				if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
				AreaReportArray[3] = 0.0;
				System.out.println("passsssss");
				}
				else {


				BigDecimal a= (BigDecimal) AreaReportArray[3];


				System.out.println(a);
				totalnumberof2g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g);


				}
				if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
				AreaReportArray[4] = 0.0;
				}
				else{ 

				BigDecimal a= (BigDecimal) AreaReportArray[4];


				System.out.println(a);
				sitePL1=Math.round(a.doubleValue());
				System.out.println(sitePL1);



				}
				if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
				AreaReportArray[5] = 0.0;
				}
				else {

				BigDecimal a= (BigDecimal) AreaReportArray[5];


				System.out.println(a);
				totalnumberof2g3g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g3g);




				} 
				if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
				AreaReportArray[6] = 0.0;
				}
				else {


				BigDecimal a= (BigDecimal) AreaReportArray[6];


				System.out.println(a);
				sitePL2=Math.round(a.doubleValue());
				System.out.println(sitePL2);




				}
				if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
				AreaReportArray[7] = 0;
				}  
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[7];


				System.out.println(a);
				totalnumberof2g3g4g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g3g4g);






				}
				if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
				AreaReportArray[8] = 0;

				}
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[8];


				System.out.println(a);
				sitePL3=Math.round(a.doubleValue());
				System.out.println(sitePL3);




				}
				if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
				AreaReportArray[9] = 0;

				}
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[9];


				System.out.println(a);
				totalSites=Math.round(a.doubleValue());
				System.out.println(totalSites);


				}


				if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
				AreaReportArray[10] = 0;

				}
				else{
				BigDecimal a= (BigDecimal) AreaReportArray[10];


				System.out.println(a);
				sumProfitLoss=Math.round(a.doubleValue());
				System.out.println(sumProfitLoss);

				}

				System.out.println("The Profit and Loss of site1 is " +sitePL1);
				System.out.println("The Profit and Loss of site2 is " +sitePL2);
				System.out.println("The Profit and Loss of site1 is " +sitePL3);
				System.out.println("The total count of site1 is " +totalnumberof2g);
				System.out.println("The total count of site2 is " +totalnumberof2g3g);
				System.out.println("The total count of site3 is " +totalnumberof2g3g4g);

				String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
				System.out.println("The array" +mapper.writeValueAsString(arrayData));
				arrayData1 = arrayData;
				System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				data.add(arrayData);

					}
				}

				} // END LOSS CONDITION 






				//BEGIN PROFITABLE CONDITION



				if(StringUtils.equalsIgnoreCase(profitable,"Profitable")) 
				{ 
				if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
				{ 
					 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
									+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where start_date >= :param1 and end_date <= :param2 and SUM_PROFIT_LOSS >=0 order by start_date DESC";
					 
					 }
					 else {
						 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
									+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 order by start_date DESC";
					 
					 }
			   }



							//IF MAX ONLY IS CHECKED


							if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null")) 
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
												+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(Select max(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
												
								 }
								 else {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
												+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(Select max(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
												
								 }
							}




							//IF MIN ONLY IS CHECKED


							if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null")) 
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
												+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(Select min(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
										
								 }
								 else {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
												+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(Select min(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
										
								 }
							}





							//IF TECHNOLOGY ONLY IS CHECKED


							if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
							&& min == null && max == null) 
							{ 
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
									 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
									            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
									            +" from area_finance where PROFIT_AND_LOSS_2G IN ( select (PROFIT_AND_LOSS_2G) from area_finance"
									            +" where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G >=0 ) order by start_date DESC  " ;
								 }
								 else {
									 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
									            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
									            +" from area_finance where PROFIT_AND_LOSS_2G IN ( select (PROFIT_AND_LOSS_2G) from area_finance"
									            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G >=0 ) order by start_date DESC  " ;
								 }
								
								}
								if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
								{ 
									 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
										 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
										            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
										            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
										            +" from area_finance where PROFIT_AND_LOSS_2G_3G IN ( select (PROFIT_AND_LOSS_2G_3G) from area_finance"
										            +" where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G >=0 ) order by start_date DESC  " ;
									 }
									 else {
										 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
										            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
										            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
										            +" from area_finance where PROFIT_AND_LOSS_2G_3G IN ( select (PROFIT_AND_LOSS_2G_3G) from area_finance"
										            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G >=0 ) order by start_date DESC  " ;
									 }

					                  
								}

								if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
								{ 
									 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
										 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
										            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
										            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
										            +" from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN ( select (PROFIT_AND_LOSS_2G_3G_4G) from area_finance"
										            +" where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G_4G >=0  ) order by start_date DESC  " ;
									 }
									 else {
										 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
										            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
										            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
										            +" from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN ( select (PROFIT_AND_LOSS_2G_3G_4G) from area_finance"
										            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G_4G >=0  ) order by start_date DESC  " ;
									 }

					                   
								}
								}



							//IF MAX AND MIN WITHOUT TECHNOLOGY


							if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null")) 
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
									 query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
												+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(select max(SUM_PROFIT_LOSS) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM')))"
												+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where "
												+" SUM_PROFIT_LOSS IN (select min(SUM_PROFIT_LOSS) from area_finance where start_date >= :param1 and end_date <= :param2 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC , SUM_PROFIT_LOSS DESC ";
										
								 }
								 else {
									 query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
												+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(select max(SUM_PROFIT_LOSS) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM')))"
												+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where "
												+" SUM_PROFIT_LOSS IN (select min(SUM_PROFIT_LOSS) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC , SUM_PROFIT_LOSS DESC ";
										
								 }

									
							
							 } 



							//IF ONLY MAX WITH TECHNOLOGY


							if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
							&& min == null &&StringUtils.equalsIgnoreCase(max,"Max")) 
							{ 
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select max(PROFIT_AND_LOSS_2G) from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

								 }
								 else {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select max(PROFIT_AND_LOSS_2G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

								 }

							
							} 

							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select max(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

								 }
								 else {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select max(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

								 }

							
							} 

							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select max(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G_4G >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

								 }
								 else {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select max(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G_4G >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

								 }
							

							} 

							}




							//IF MIN WITH TECHNOLOGY



							if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
							&& StringUtils.equalsIgnoreCase(min,"Min") && max == null) 
							{ 
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select min(PROFIT_AND_LOSS_2G) from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

								 }
								 else {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select min(PROFIT_AND_LOSS_2G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

								 }

							
							} 

							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select min(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

								 }
								 else {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select min(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC"; 

								 }

							

							} 

							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select min(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G_4G >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								 }
								 else {
									 query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select min(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G_4G >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";

								 }
							

							} 

							}


							//IF MAX AND MIN WITH TECHNOLOGY



							if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || 
							StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) 
							{  

							if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
									 
									 query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G IN (select max(PROFIT_AND_LOSS_2G) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G >=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
												+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G IN (select min(PROFIT_AND_LOSS_2G) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G >=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, PROFIT_AND_LOSS_2G DESC";

									
								 }
								 else {
									 query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G IN (select max(PROFIT_AND_LOSS_2G) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G >=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
												+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G IN (select min(PROFIT_AND_LOSS_2G) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G >=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, PROFIT_AND_LOSS_2G DESC";

								 }

							
							}


							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
										query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G IN (select max(PROFIT_AND_LOSS_2G_3G) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G >=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
												+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G IN (select min(PROFIT_AND_LOSS_2G_3G) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G >=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, PROFIT_AND_LOSS_2G_3G DESC";

								 }
								 else {
										query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G IN (select max(PROFIT_AND_LOSS_2G_3G) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G >=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
												+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G IN (select min(PROFIT_AND_LOSS_2G_3G) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G >=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, PROFIT_AND_LOSS_2G_3G DESC";

								 }

						
							}

							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
							{ 
								 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
									 query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select max(PROFIT_AND_LOSS_2G_3G_4G) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G_4G >=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
												+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select min(PROFIT_AND_LOSS_2G_3G_4G) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and PROFIT_AND_LOSS_2G_3G_4G >=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, PROFIT_AND_LOSS_2G_3G_4G DESC";

								 }
								 else {
									 query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select max(PROFIT_AND_LOSS_2G_3G_4G) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G_4G >=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
												+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
												+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select min(PROFIT_AND_LOSS_2G_3G_4G) "
												+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G_4G >=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, PROFIT_AND_LOSS_2G_3G_4G DESC";

								 }

							
							}
							}




				q=session.createSQLQuery(query1);

				q.setParameter("param1", StartDate);
				q.setParameter("param2", EndDate);
				if(area != null) {
					q.setParameter("param3", area);
				}

				AreaReportList=q.list();

				countT=AreaReportList.size();

				if(AreaReportList.size()>0) {
					for(int i=0;i<AreaReportList.size();i++) {

				AreaReportArray = (Object[]) AreaReportList.toArray()[i];
				System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));


				if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
				AreaReportArray[0] ="";
				System.out.println("passsssss");
				}
				else {
				startdate =String.valueOf(AreaReportArray[0])  ;
				}


				if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
				AreaReportArray[1] ="";
				System.out.println("passsssss");
				}
				else {
				enddate =String.valueOf(AreaReportArray[1])  ;
				}





				if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
				AreaReportArray[2] ="";
				System.out.println("passsssss");
				}
				else {
				areas =String.valueOf(AreaReportArray[2])  ;
				}




				if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
				AreaReportArray[3] = 0.0;
				System.out.println("passsssss");
				}
				else {


				BigDecimal a= (BigDecimal) AreaReportArray[3];


				System.out.println(a);
				totalnumberof2g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g);


				}
				if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
				AreaReportArray[4] = 0.0;
				}
				else{ 

				BigDecimal a= (BigDecimal) AreaReportArray[4];


				System.out.println(a);
				sitePL1=Math.round(a.doubleValue());
				System.out.println(sitePL1);



				}
				if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
				AreaReportArray[5] = 0.0;
				}
				else {

				BigDecimal a= (BigDecimal) AreaReportArray[5];


				System.out.println(a);
				totalnumberof2g3g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g3g);




				} 
				if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
				AreaReportArray[6] = 0.0;
				}
				else {


				BigDecimal a= (BigDecimal) AreaReportArray[6];


				System.out.println(a);
				sitePL2=Math.round(a.doubleValue());
				System.out.println(sitePL2);




				}
				if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
				AreaReportArray[7] = 0;
				}  
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[7];


				System.out.println(a);
				totalnumberof2g3g4g=Math.round(a.doubleValue());
				System.out.println(totalnumberof2g3g4g);






				}
				if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
				AreaReportArray[8] = 0;

				}
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[8];


				System.out.println(a);
				sitePL3=Math.round(a.doubleValue());
				System.out.println(sitePL3);




				}
				if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
				AreaReportArray[9] = 0;

				}
				else{

				BigDecimal a= (BigDecimal) AreaReportArray[9];


				System.out.println(a);
				totalSites=Math.round(a.doubleValue());
				System.out.println(totalSites);


				}


				if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
				AreaReportArray[10] = 0;

				}
				else{
				BigDecimal a= (BigDecimal) AreaReportArray[10];


				System.out.println(a);
				sumProfitLoss=Math.round(a.doubleValue());
				System.out.println(sumProfitLoss);

				}

				System.out.println("The Profit and Loss of site1 is " +sitePL1);
				System.out.println("The Profit and Loss of site2 is " +sitePL2);
				System.out.println("The Profit and Loss of site1 is " +sitePL3);
				System.out.println("The total count of site1 is " +totalnumberof2g);
				System.out.println("The total count of site2 is " +totalnumberof2g3g);
				System.out.println("The total count of site3 is " +totalnumberof2g3g4g);

				String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
				System.out.println("The array" +mapper.writeValueAsString(arrayData));
				arrayData1 = arrayData;
				System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				data.add(arrayData);
					}

				}

				} // END PROFITABLE CONDITION 




				 
				 


/////////////////////////////////////////////////////////////////////////////////// ahmad ////////////////////////////////////////////////////////////////////////////////


			
/*			 } // end of area is not selected
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 // if area selected
			 
			 else {
				 
				 if((StringUtils.equalsIgnoreCase(profitable,"null") || profitable == null || profitable == "") && (StringUtils.equalsIgnoreCase(loss,"null") || loss == null || loss == "")&& (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") &&(StringUtils.equalsIgnoreCase(max,"null") || max == null || max == "") && (StringUtils.equalsIgnoreCase(min,"null") || min == null || min == "")) {
					 
					 queryMonthly="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 " ;
				 
					 q = session.createSQLQuery(queryMonthly);


				
				       q.setParameter("param1", StartDate);
				       q.setParameter("param2", EndDate);
				       q.setParameter("param3", area);

				       AreaReportList=q.list();
				       
				       countT=AreaReportList.size();
				       
				       if(AreaReportList.size()>0) {
					    	  for(int i=0;i<AreaReportList.size();i++) {
					    		  
					    		  AreaReportArray = (Object[]) AreaReportList.toArray()[i];
				            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
					    		  
					    	  
				            	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
						    		   AreaReportArray[0] ="";
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						           startdate =String.valueOf(AreaReportArray[0])  ;
						    	 }
						    	  
				            	  
				            	  if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
						    		   AreaReportArray[1] ="";
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						           enddate =String.valueOf(AreaReportArray[1])  ;
						    	 }
						    	  
				            	  
				            	  
				            	  
				            	  
					    	  if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
					    		   AreaReportArray[2] ="";
					    		   System.out.println("passsssss");
					    	 }
					    	 else {
					           areas =String.valueOf(AreaReportArray[2])  ;
					    	 }
					    	  
					    	  
					    	  
					    	  
					    	   if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
					    		   AreaReportArray[3] = 0.0;
					    		   System.out.println("passsssss");
					    	 }
					    	 else {
					    		 
					    		 
					    		BigDecimal a= (BigDecimal) AreaReportArray[3];
					           
					           
					           System.out.println(a);
					           totalnumberof2g=Math.round(a.doubleValue());
					           System.out.println(totalnumberof2g);
					           
					           
					    	 }
					         if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
					    		   AreaReportArray[4] = 0.0;
				             }
				             else{ 
				            	 
				            	 BigDecimal a= (BigDecimal) AreaReportArray[4];
						           
						           
						           System.out.println(a);
						           sitePL1=Math.round(a.doubleValue());
						           System.out.println(sitePL1);
						           
				            	 
				            	 
				             }
					         if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
				    		       AreaReportArray[5] = 0.0;
					         }
					         else {
					        	 
					        	 BigDecimal a= (BigDecimal) AreaReportArray[5];
						           
						           
						           System.out.println(a);
						           totalnumberof2g3g=Math.round(a.doubleValue());
						           System.out.println(totalnumberof2g3g);
						        
					        	 
					        	 
					           
					         } 
					    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
					    		   AreaReportArray[6] = 0.0;
					    	 }
					    	 else {
					    		 

					        	 BigDecimal a= (BigDecimal) AreaReportArray[6];
						           
						           
						           System.out.println(a);
						           sitePL2=Math.round(a.doubleValue());
						           System.out.println(sitePL2);
						        
					    		 
					    		 
					    		
					    	 }
					    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
					    		   AreaReportArray[7] = 0;
					    	 }  
					    	 else{
					    		 
					    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
						           
						           
						           System.out.println(a);
						           totalnumberof2g3g4g=Math.round(a.doubleValue());
						           System.out.println(totalnumberof2g3g4g);
						        
					    		 
					    		 
					    		 
					    		 
					    		
					    	 }
					    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
					    		   AreaReportArray[8] = 0;
					    		   
					    	 }
					    	 else{
					    		 
					    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
						           
						           
						           System.out.println(a);
						           sitePL3=Math.round(a.doubleValue());
						           System.out.println(sitePL3);
						        
					    		 
					    		 
					    		
					    	 }
					    	 if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
					    		   AreaReportArray[9] = 0;
					    		   
					    	 }
					    	 else{
					    		 
					    		 BigDecimal a= (BigDecimal) AreaReportArray[9];
						           
						           
						           System.out.println(a);
						           totalSites=Math.round(a.doubleValue());
						           System.out.println(totalSites);
						        
					    		 
					    	 }
					    		 
					    		
					    	 if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
					    		   AreaReportArray[10] = 0;
					    		   
					    	 }
					    	 else{
					    		 BigDecimal a= (BigDecimal) AreaReportArray[10];
						           
						           
						           System.out.println(a);
						           sumProfitLoss=Math.round(a.doubleValue());
						           System.out.println(sumProfitLoss);
						         
					    	 }
					     
					          System.out.println("The Profit and Loss of site1 is " +sitePL1);
				               System.out.println("The Profit and Loss of site2 is " +sitePL2);
				               System.out.println("The Profit and Loss of site1 is " +sitePL3);
				               System.out.println("The total count of site1 is " +totalnumberof2g);
				               System.out.println("The total count of site2 is " +totalnumberof2g3g);
				               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
				           
				               String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
				               System.out.println("The array" +mapper.writeValueAsString(arrayData));
				               arrayData1 = arrayData;
				               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				               data.add(arrayData);
				               
				               totalPLF += (sitePL1+sitePL2 +sitePL3);
							    totalSumNumberof2g += totalnumberof2g;
							    totalSumNumberof2g3g += totalnumberof2g3g;
							    totalSumNumberof2g3g4g += totalnumberof2g3g4g;
					       }// End for for loop
					       
					        
					  System.out.println("totalPLF" +totalPLF);
					  System.out.println("TotalNumber of 2g" +totalSumNumberof2g);
					  System.out.println("totalnumberof2g3g" +totalSumNumberof2g3g);
					  System.out.println("totalnumberof2g3g4g" +totalSumNumberof2g3g4g);
					  
						
						
						
					    
				    }// end for if
					      
					      rtn.put("totalProfitLoss",totalPLF );
						    rtn.put("totalnumberof2g",totalSumNumberof2g );
						    rtn.put("totalnumberof2g3g",totalSumNumberof2g3g);
							rtn.put("totalnumberof2g3g4g",totalSumNumberof2g3g4g );
							rtn.put("totalAreas", countT);
					    
				       
				       
				 
				 
				 } 
				 
/////////////////////////////////////////////////////////////////////////////////// ahmad ////////////////////////////////////////////////////////////////////////////////


		 

					//BEGIN MAX CONDITION 


					if(StringUtils.equalsIgnoreCase(max,"Max") && profitable == null && loss == null)

					{

					if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")
					{
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G, "
								+"NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where SUM_PROFIT_LOSS IN(Select max(SUM_PROFIT_LOSS)" 
								+"from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 )";
					
					 q = session.createSQLQuery(query1);
                     q.setParameter("param1", StartDate);
                     q.setParameter("param2", EndDate);
                     q.setParameter("param3", area);
                     AreaReportList=q.list();
					 
					}


					if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {


					if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
					{ 

					query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
							+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select max(PROFIT_AND_LOSS_2G)"
							+ " from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 )"; 

					
					
					q = session.createSQLQuery(query1);
                    q.setParameter("param1", StartDate);
                    q.setParameter("param2", EndDate);
                    q.setParameter("param3", area);
                    AreaReportList=q.list();
					
					} 

					if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
					{ 

						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select max(PROFIT_AND_LOSS_2G_3G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 )";
								
						
						
					q = session.createSQLQuery(query1);
                    q.setParameter("param1", StartDate);
                    q.setParameter("param2", EndDate);
                    q.setParameter("param3", area);
                    AreaReportList=q.list();
					
					} 

					if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
					{ 
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select max(PROFIT_AND_LOSS_2G_3G_4G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 )";
								
						
					} 


					}	                	   
					q = session.createSQLQuery(query1);



					q.setParameter("param1", StartDate);
					q.setParameter("param2", EndDate);
					q.setParameter("param3", area);



					AreaReportList=q.list();

					countT=AreaReportList.size();

					if(AreaReportList.size()>0) {
						for(int i=0;i<AreaReportList.size();i++) {

					AreaReportArray = (Object[]) AreaReportList.toArray()[0];
					System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));


					if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
					AreaReportArray[0] ="";
					System.out.println("passsssss");
					}
					else {
					startdate =String.valueOf(AreaReportArray[0])  ;
					}


					if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
					AreaReportArray[1] ="";
					System.out.println("passsssss");
					}
					else {
					enddate =String.valueOf(AreaReportArray[1])  ;
					}





					if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
					AreaReportArray[2] ="";
					System.out.println("passsssss");
					}
					else {
					areas =String.valueOf(AreaReportArray[2])  ;
					}




					if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
					AreaReportArray[3] = 0.0;
					System.out.println("passsssss");
					}
					else {


					BigDecimal a= (BigDecimal) AreaReportArray[3];


					System.out.println(a);
					totalnumberof2g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g);


					}
					if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
					AreaReportArray[4] = 0.0;
					}
					else{ 

					BigDecimal a= (BigDecimal) AreaReportArray[4];


					System.out.println(a);
					sitePL1=Math.round(a.doubleValue());
					System.out.println(sitePL1);



					}
					if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
					AreaReportArray[5] = 0.0;
					}
					else {

					BigDecimal a= (BigDecimal) AreaReportArray[5];


					System.out.println(a);
					totalnumberof2g3g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g3g);




					} 
					if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
					AreaReportArray[6] = 0.0;
					}
					else {


					BigDecimal a= (BigDecimal) AreaReportArray[6];


					System.out.println(a);
					sitePL2=Math.round(a.doubleValue());
					System.out.println(sitePL2);




					}
					if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
					AreaReportArray[7] = 0;
					}  
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[7];


					System.out.println(a);
					totalnumberof2g3g4g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g3g4g);






					}
					if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
					AreaReportArray[8] = 0;

					}
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[8];


					System.out.println(a);
					sitePL3=Math.round(a.doubleValue());
					System.out.println(sitePL3);




					}
					if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
					AreaReportArray[9] = 0;

					}
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[9];


					System.out.println(a);
					totalSites=Math.round(a.doubleValue());
					System.out.println(totalSites);


					}


					if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
					AreaReportArray[10] = 0;

					}
					else{
					BigDecimal a= (BigDecimal) AreaReportArray[10];


					System.out.println(a);
					sumProfitLoss=Math.round(a.doubleValue());
					System.out.println(sumProfitLoss);

					}

					System.out.println("The Profit and Loss of site1 is " +sitePL1);
					System.out.println("The Profit and Loss of site2 is " +sitePL2);
					System.out.println("The Profit and Loss of site1 is " +sitePL3);
					System.out.println("The total count of site1 is " +totalnumberof2g);
					System.out.println("The total count of site2 is " +totalnumberof2g3g);
					System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
					
					String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
					System.out.println("The array" +mapper.writeValueAsString(arrayData));
					arrayData1 = arrayData;
					System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
					data.add(arrayData);


					} // end for loop

					}// end for if





					}// end of condition





					//BEGIN MIN CONDITION 


					if(StringUtils.equalsIgnoreCase(min,"Min") && profitable == null && loss == null)

					{

					if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")
					{
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G, "
								+"NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where SUM_PROFIT_LOSS IN(Select min(SUM_PROFIT_LOSS)" 
								+"from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 ) "; 

                        q = session.createSQLQuery(query1);
                        q.setParameter("param1", StartDate);
                        q.setParameter("param2", EndDate);
                        q.setParameter("param3", area);
                        AreaReportList=q.list();
                        
					}


					if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {


					if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
					{ 


						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select min(PROFIT_AND_LOSS_2G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3)"; 

						
						
						q = session.createSQLQuery(query1);
                        q.setParameter("param1", StartDate);
                        q.setParameter("param2", EndDate);
                        q.setParameter("param3", area);
                        AreaReportList=q.list();
						
					} 

					if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
					{ 

						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select min(PROFIT_AND_LOSS_2G_3G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 )";
					
					q = session.createSQLQuery(query1);
                    q.setParameter("param1", StartDate);
                    q.setParameter("param2", EndDate);
                    q.setParameter("param3", area);
                    AreaReportList=q.list();
					
					} 

					if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
					{ 
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select min(PROFIT_AND_LOSS_2G_3G)"
								+ " from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 )";
					} 


					}	                	   
					q = session.createSQLQuery(query1);



					q.setParameter("param1", StartDate);
					q.setParameter("param2", EndDate);
					q.setParameter("param3", area);



					AreaReportList=q.list();

					countT=AreaReportList.size();

					if(AreaReportList.size()>0) {
						for(int i=0;i<AreaReportList.size();i++) {

					AreaReportArray = (Object[]) AreaReportList.toArray()[0];
					System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));


					if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
					AreaReportArray[0] ="";
					System.out.println("passsssss");
					}
					else {
					startdate =String.valueOf(AreaReportArray[0])  ;
					}


					if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
					AreaReportArray[1] ="";
					System.out.println("passsssss");
					}
					else {
					enddate =String.valueOf(AreaReportArray[1])  ;
					}





					if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
					AreaReportArray[2] ="";
					System.out.println("passsssss");
					}
					else {
					areas =String.valueOf(AreaReportArray[2])  ;
					}




					if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
					AreaReportArray[3] = 0.0;
					System.out.println("passsssss");
					}
					else {


					BigDecimal a= (BigDecimal) AreaReportArray[3];


					System.out.println(a);
					totalnumberof2g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g);


					}
					if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
					AreaReportArray[4] = 0.0;
					}
					else{ 

					BigDecimal a= (BigDecimal) AreaReportArray[4];


					System.out.println(a);
					sitePL1=Math.round(a.doubleValue());
					System.out.println(sitePL1);



					}
					if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
					AreaReportArray[5] = 0.0;
					}
					else {

					BigDecimal a= (BigDecimal) AreaReportArray[5];


					System.out.println(a);
					totalnumberof2g3g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g3g);




					} 
					if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
					AreaReportArray[6] = 0.0;
					}
					else {


					BigDecimal a= (BigDecimal) AreaReportArray[6];


					System.out.println(a);
					sitePL2=Math.round(a.doubleValue());
					System.out.println(sitePL2);




					}
					if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
					AreaReportArray[7] = 0;
					}  
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[7];


					System.out.println(a);
					totalnumberof2g3g4g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g3g4g);






					}
					if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
					AreaReportArray[8] = 0;

					}
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[8];


					System.out.println(a);
					sitePL3=Math.round(a.doubleValue());
					System.out.println(sitePL3);




					}
					if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
					AreaReportArray[9] = 0;

					}
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[9];


					System.out.println(a);
					totalSites=Math.round(a.doubleValue());
					System.out.println(totalSites);


					}


					if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
					AreaReportArray[10] = 0;

					}
					else{
					BigDecimal a= (BigDecimal) AreaReportArray[10];


					System.out.println(a);
					sumProfitLoss=Math.round(a.doubleValue());
					System.out.println(sumProfitLoss);

					}

					System.out.println("The Profit and Loss of site1 is " +sitePL1);
					System.out.println("The Profit and Loss of site2 is " +sitePL2);
					System.out.println("The Profit and Loss of site1 is " +sitePL3);
					System.out.println("The total count of site1 is " +totalnumberof2g);
					System.out.println("The total count of site2 is " +totalnumberof2g3g);
					System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
					
					String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
					System.out.println("The array" +mapper.writeValueAsString(arrayData));
					arrayData1 = arrayData;
					System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
					data.add(arrayData);


					} // end for loop

					}// end for if





					}// end of condition
					
					



					//BEGIN TECHNOLOGY CONDITION 		



					if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g")||StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && profitable == null && loss == null && min == null && max == null)
					{

					if(StringUtils.equalsIgnoreCase(technologies,"2g"))
					{
                           
						query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
					            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
					            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
					            +" from area_finance where PROFIT_AND_LOSS_2G IN ( select (PROFIT_AND_LOSS_2G) from area_finance"
					            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3) order by start_date DESC " ;
					                
						
						
					q = session.createSQLQuery(query1);
					q.setParameter("param1", StartDate);
					q.setParameter("param2", EndDate);
					q.setParameter("param3", area);
					AreaReportList=q.list();



					}

					if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
					{ 
						
						query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
					            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
					            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
					            +" from area_finance where PROFIT_AND_LOSS_2G_3G IN ( select (PROFIT_AND_LOSS_2G_3G) from area_finance"
					            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3) order by start_date DESC " ;
					               
						
					q = session.createSQLQuery(query1);
					q.setParameter("param1", StartDate);
					q.setParameter("param2", EndDate);
					q.setParameter("param3", area);
					AreaReportList=q.list();


					}

					if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
					{ 
						query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
					            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
					            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
					            +" from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN ( select (PROFIT_AND_LOSS_2G_3G_4G) from area_finance"
					            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3) order by start_date DESC " ;
					               
					}	


					q = session.createSQLQuery(query1);



					q.setParameter("param1", StartDate);
					q.setParameter("param2", EndDate);
					q.setParameter("param3", area);



					AreaReportList=q.list();

					countT=AreaReportList.size();

					if(AreaReportList.size()>0) {
						for(int i=0;i<AreaReportList.size();i++) {

					AreaReportArray = (Object[]) AreaReportList.toArray()[0];
					System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));


					if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
					AreaReportArray[0] ="";
					System.out.println("passsssss");
					}
					else {
					startdate =String.valueOf(AreaReportArray[0])  ;
					}


					if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
					AreaReportArray[1] ="";
					System.out.println("passsssss");
					}
					else {
					enddate =String.valueOf(AreaReportArray[1])  ;
					}





					if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
					AreaReportArray[2] ="";
					System.out.println("passsssss");
					}
					else {
					areas =String.valueOf(AreaReportArray[2])  ;
					}




					if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
					AreaReportArray[3] = 0.0;
					System.out.println("passsssss");
					}
					else {


					BigDecimal a= (BigDecimal) AreaReportArray[3];


					System.out.println(a);
					totalnumberof2g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g);


					}
					if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
					AreaReportArray[4] = 0.0;
					}
					else{ 

					BigDecimal a= (BigDecimal) AreaReportArray[4];


					System.out.println(a);
					sitePL1=Math.round(a.doubleValue());
					System.out.println(sitePL1);



					}
					if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
					AreaReportArray[5] = 0.0;
					}
					else {

					BigDecimal a= (BigDecimal) AreaReportArray[5];


					System.out.println(a);
					totalnumberof2g3g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g3g);




					} 
					if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
					AreaReportArray[6] = 0.0;
					}
					else {


					BigDecimal a= (BigDecimal) AreaReportArray[6];


					System.out.println(a);
					sitePL2=Math.round(a.doubleValue());
					System.out.println(sitePL2);




					}
					if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
					AreaReportArray[7] = 0;
					}  
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[7];


					System.out.println(a);
					totalnumberof2g3g4g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g3g4g);






					}
					if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
					AreaReportArray[8] = 0;

					}
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[8];


					System.out.println(a);
					sitePL3=Math.round(a.doubleValue());
					System.out.println(sitePL3);




					}
					if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
					AreaReportArray[9] = 0;

					}
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[9];


					System.out.println(a);
					totalSites=Math.round(a.doubleValue());
					System.out.println(totalSites);


					}


					if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
					AreaReportArray[10] = 0;

					}
					else{
					BigDecimal a= (BigDecimal) AreaReportArray[10];


					System.out.println(a);
					sumProfitLoss=Math.round(a.doubleValue());
					System.out.println(sumProfitLoss);

					}

					System.out.println("The Profit and Loss of site1 is " +sitePL1);
					System.out.println("The Profit and Loss of site2 is " +sitePL2);
					System.out.println("The Profit and Loss of site1 is " +sitePL3);
					System.out.println("The total count of site1 is " +totalnumberof2g);
					System.out.println("The total count of site2 is " +totalnumberof2g3g);
					System.out.println("The total count of site3 is " +totalnumberof2g3g4g);

					String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
					System.out.println("The array" +mapper.writeValueAsString(arrayData));
					arrayData1 = arrayData;
					System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
					data.add(arrayData);


						}

					}// end for if





					}// end of condition






					//BEGIN LOSS CONDITION

					if(StringUtils.equalsIgnoreCase(loss,"Loss")) 
					{ 
					if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
					{ 
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS <=0 order by start_date DESC";
					}



					//IF MAX ONLY IS CHECKED


					if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null")) 
					{ 
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(Select max(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS <=0 ) order by start_date DESC ";
					}




					//IF MIN ONLY IS CHECKED


					if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null")) 
					{ 
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(Select min(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS <=0) order by start_date DESC ";
					}





					//IF TECHNOLOGY ONLY IS CHECKED


					if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
					&& min == null && max == null) 
					{ 
					if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
					{ 
						 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G IN ( select (PROFIT_AND_LOSS_2G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2 and and area_name=:param3 PROFIT_AND_LOSS_2G <=0) order by start_date DESC  " ;
					}
					
				
                    if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
					{ 
                    	 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G_3G IN ( select (PROFIT_AND_LOSS_2G_3G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2 and and area_name=:param3 PROFIT_AND_LOSS_2G <=0) order by start_date DESC  " ;
					}

					if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
					{ 
						 query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
						            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
						            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
						            +" from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN ( select (PROFIT_AND_LOSS_2G_3G_4G) from area_finance"
						            +" where start_date >= :param1 and end_date <= :param2 and and area_name=:param3 PROFIT_AND_LOSS_2G <=0 )order by start_date DESC  " ;
					}
					}




					//IF MAX AND MIN WITHOUT TECHNOLOGY


					if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null")) 
					{ 

						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(select max(SUM_PROFIT_LOSS) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS <=0 group by TO_CHAR(start_date, 'YYYY-MM')))"
								+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where "
								+" SUM_PROFIT_LOSS IN (select min(SUM_PROFIT_LOSS) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 group by TO_CHAR(start_date, 'YYYY-MM'))) order by start_date DESC ";
					
					} 





					//IF ONLY MAX WITH TECHNOLOGY


					if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
					&& min == null && max == null) 
					{ 
					if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
					{ 

						       query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select max(PROFIT_AND_LOSS_2G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 ) order by start_date DESC"; 

								} 

								if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
								{ 

								query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select max(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 ) order by start_date DESC"; 


								} 

								if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
								{ 
								query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select max(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 ) order by start_date DESC"; 

                                 } 

					 }




					//IF MIN WITH TECHNOLOGY



					if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
					&& min == null && max == null) 
					{ 
					if(StringUtils.equalsIgnoreCase(technologies,"2g"))
					           { 

						            query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select min(PROFIT_AND_LOSS_2G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 ) order by start_date DESC"; 

									} 

									if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
									{ 

									query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select min(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 ) order by start_date DESC"; 


									} 

									if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
									{ 
									query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
									+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select min(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 ) order by start_date DESC"; 

	                                 } 

						 }
					


					//IF MAX AND MIN WITH TECHNOLOGY



					if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || 
					StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) 
					{  

					if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
					{ 

						query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G IN (select max(PROFIT_AND_LOSS_2G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
								+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS, from area_finance where PROFIT_AND_LOSS_2G IN (select min(PROFIT_AND_LOSS_2G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, SUM_PROFIT_LOSS DESC";

					}


					if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
					{ 

						query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G IN (select max(PROFIT_AND_LOSS_2G_3G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
								+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS, from area_finance where PROFIT_AND_LOSS_2G_3G IN (select min(PROFIT_AND_LOSS_2G_3G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, SUM_PROFIT_LOSS DESC";

					}

					if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
					{ 

						query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select max(PROFIT_AND_LOSS_2G_3G_4G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
								+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS, from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select min(PROFIT_AND_LOSS_2G_3G_4G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS<=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, SUM_PROFIT_LOSS DESC";

					}
					}





					q=session.createSQLQuery(query1);

					q.setParameter("param1", StartDate);
					q.setParameter("param2", EndDate);
					q.setParameter("param3", area);
					AreaReportList=q.list();

					countT=AreaReportList.size();

					if(AreaReportList.size()>0) {
						for(int i=0;i<AreaReportList.size();i++) {

					AreaReportArray = (Object[]) AreaReportList.toArray()[0];
					System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));


					if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
					AreaReportArray[0] ="";
					System.out.println("passsssss");
					}
					else {
					startdate =String.valueOf(AreaReportArray[0])  ;
					}


					if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
					AreaReportArray[1] ="";
					System.out.println("passsssss");
					}
					else {
					enddate =String.valueOf(AreaReportArray[1])  ;
					}





					if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
					AreaReportArray[2] ="";
					System.out.println("passsssss");
					}
					else {
					areas =String.valueOf(AreaReportArray[2])  ;
					}




					if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
					AreaReportArray[3] = 0.0;
					System.out.println("passsssss");
					}
					else {


					BigDecimal a= (BigDecimal) AreaReportArray[3];


					System.out.println(a);
					totalnumberof2g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g);


					}
					if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
					AreaReportArray[4] = 0.0;
					}
					else{ 

					BigDecimal a= (BigDecimal) AreaReportArray[4];


					System.out.println(a);
					sitePL1=Math.round(a.doubleValue());
					System.out.println(sitePL1);



					}
					if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
					AreaReportArray[5] = 0.0;
					}
					else {

					BigDecimal a= (BigDecimal) AreaReportArray[5];


					System.out.println(a);
					totalnumberof2g3g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g3g);




					} 
					if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
					AreaReportArray[6] = 0.0;
					}
					else {


					BigDecimal a= (BigDecimal) AreaReportArray[6];


					System.out.println(a);
					sitePL2=Math.round(a.doubleValue());
					System.out.println(sitePL2);




					}
					if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
					AreaReportArray[7] = 0;
					}  
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[7];


					System.out.println(a);
					totalnumberof2g3g4g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g3g4g);






					}
					if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
					AreaReportArray[8] = 0;

					}
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[8];


					System.out.println(a);
					sitePL3=Math.round(a.doubleValue());
					System.out.println(sitePL3);




					}
					if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
					AreaReportArray[9] = 0;

					}
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[9];


					System.out.println(a);
					totalSites=Math.round(a.doubleValue());
					System.out.println(totalSites);


					}


					if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
					AreaReportArray[10] = 0;

					}
					else{
					BigDecimal a= (BigDecimal) AreaReportArray[10];


					System.out.println(a);
					sumProfitLoss=Math.round(a.doubleValue());
					System.out.println(sumProfitLoss);

					}

					System.out.println("The Profit and Loss of site1 is " +sitePL1);
					System.out.println("The Profit and Loss of site2 is " +sitePL2);
					System.out.println("The Profit and Loss of site1 is " +sitePL3);
					System.out.println("The total count of site1 is " +totalnumberof2g);
					System.out.println("The total count of site2 is " +totalnumberof2g3g);
					System.out.println("The total count of site3 is " +totalnumberof2g3g4g);

					String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
					System.out.println("The array" +mapper.writeValueAsString(arrayData));
					arrayData1 = arrayData;
					System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
					data.add(arrayData);

						}
					}

					} // END LOSS CONDITION 



					//BEGIN Profitable CONDITION

					if(StringUtils.equalsIgnoreCase(profitable,"Profitable")) 
					{ 
					if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
					{ 
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+ "TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 order by start_date DESC";
				    }




					//IF MAX ONLY IS CHECKED


					if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null")) 
					{ 
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(Select max(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 ) order by start_date DESC ";
					}




					//IF MIN ONLY IS CHECKED


					if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null")) 
					{ 
						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(Select min(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0) order by start_date DESC ";
					}





					//IF TECHNOLOGY ONLY IS CHECKED


					if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
					&& min == null && max == null) 
					{ 
					          if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
					           { 
					        	  query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
								            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
								            +" from area_finance where PROFIT_AND_LOSS_2G IN ( select (PROFIT_AND_LOSS_2G) from area_finance"
								            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G >=0 ) order by start_date DESC  " ;
								}

								if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
								{ 
									query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
								            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
								            +" from area_finance where PROFIT_AND_LOSS_2G_3G IN ( select (PROFIT_AND_LOSS_2G_3G) from area_finance"
								            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G >=0 ) order by start_date DESC  " ;
								}

								if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
								{ 
									query1 = " select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date,'YYYY-MM-DD HH24:MI:SS'), "
								            +" area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								            +" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS " 
								            +" from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN ( select (PROFIT_AND_LOSS_2G_3G_4G) from area_finance"
								            +" where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and PROFIT_AND_LOSS_2G_3G_4G >=0 ) order by start_date DESC  " ;
								}
					}



					//IF MAX AND MIN WITHOUT TECHNOLOGY


					if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null")) 
					{ 

						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G, "
								+" TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where SUM_PROFIT_LOSS IN(select max(SUM_PROFIT_LOSS) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM')))"
								+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where "
								+" SUM_PROFIT_LOSS IN (select min(SUM_PROFIT_LOSS) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS>=0 group by TO_CHAR(start_date, 'YYYY-MM'))) ";
							
					}





					//IF ONLY MAX WITH TECHNOLOGY


					if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
					&& min == null && max == null) 
					{ 
					           if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
					            { 

						        query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select max(PROFIT_AND_LOSS_2G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 ) order by start_date DESC"; 

								} 

								if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
								{ 

								query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select max(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 ) order by start_date DESC"; 


								} 

								if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
								{ 
								query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select max(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 ) order by start_date DESC"; 

                                } 

					}




					//IF MIN WITH TECHNOLOGY



					if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
					&& min == null && max == null) 
					{ 
					if(StringUtils.equalsIgnoreCase(technologies,"2g"))
					{ 

						query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G  IN(select min(PROFIT_AND_LOSS_2G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 ) order by start_date DESC"; 

								} 

								if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
								{ 

								query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G  IN(select min(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 ) order by start_date DESC"; 


								} 

								if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
								{ 
								query1 = "select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G  IN(select min(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0) order by start_date DESC"; 

					            } 

					}


					//IF MAX AND MIN WITH TECHNOLOGY



					if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || 
					StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) 
					{  

					if(StringUtils.equalsIgnoreCase(technologies,"2g")) 
					{ 

						query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G IN (select max(PROFIT_AND_LOSS_2G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
								+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS, from area_finance where PROFIT_AND_LOSS_2G IN (select min(PROFIT_AND_LOSS_2G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, SUM_PROFIT_LOSS DESC";

								}


								if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) 
								{ 

								query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G IN (select max(PROFIT_AND_LOSS_2G_3G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
								+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS, from area_finance where PROFIT_AND_LOSS_2G_3G IN (select min(PROFIT_AND_LOSS_2G_3G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, SUM_PROFIT_LOSS DESC";

								}

								if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) 
								{ 

								query1 = "select * from ((select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date , TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES, PROFIT_AND_LOSS_2G,NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select max(PROFIT_AND_LOSS_2G_3G_4G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM'))) "
								+" UNION (select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G, NO_2G_3G_SITES, PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES, "
								+" PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS, from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN (select min(PROFIT_AND_LOSS_2G_3G_4G) "
								+" from area_finance where start_date >= :param1 and end_date <= :param2 and area_name=:param3 and SUM_PROFIT_LOSS >=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, SUM_PROFIT_LOSS DESC";

					}
					}





					q=session.createSQLQuery(query1);

					q.setParameter("param1", StartDate);
					q.setParameter("param2", EndDate);
					q.setParameter("param3", area);
					AreaReportList=q.list();

					countT=AreaReportList.size();

					if(AreaReportList.size()>0) {
						for(int i=0;i<AreaReportList.size();i++) {

					AreaReportArray = (Object[]) AreaReportList.toArray()[0];
					System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));


					if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
					AreaReportArray[0] ="";
					System.out.println("passsssss");
					}
					else {
					startdate =String.valueOf(AreaReportArray[0])  ;
					}


					if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
					AreaReportArray[1] ="";
					System.out.println("passsssss");
					}
					else {
					enddate =String.valueOf(AreaReportArray[1])  ;
					}





					if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
					AreaReportArray[2] ="";
					System.out.println("passsssss");
					}
					else {
					areas =String.valueOf(AreaReportArray[2])  ;
					}




					if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
					AreaReportArray[3] = 0.0;
					System.out.println("passsssss");
					}
					else {


					BigDecimal a= (BigDecimal) AreaReportArray[3];


					System.out.println(a);
					totalnumberof2g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g);


					}
					if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
					AreaReportArray[4] = 0.0;
					}
					else{ 

					BigDecimal a= (BigDecimal) AreaReportArray[4];


					System.out.println(a);
					sitePL1=Math.round(a.doubleValue());
					System.out.println(sitePL1);



					}
					if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
					AreaReportArray[5] = 0.0;
					}
					else {

					BigDecimal a= (BigDecimal) AreaReportArray[5];


					System.out.println(a);
					totalnumberof2g3g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g3g);




					} 
					if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
					AreaReportArray[6] = 0.0;
					}
					else {


					BigDecimal a= (BigDecimal) AreaReportArray[6];


					System.out.println(a);
					sitePL2=Math.round(a.doubleValue());
					System.out.println(sitePL2);




					}
					if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
					AreaReportArray[7] = 0;
					}  
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[7];


					System.out.println(a);
					totalnumberof2g3g4g=Math.round(a.doubleValue());
					System.out.println(totalnumberof2g3g4g);






					}
					if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
					AreaReportArray[8] = 0;

					}
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[8];


					System.out.println(a);
					sitePL3=Math.round(a.doubleValue());
					System.out.println(sitePL3);




					}
					if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
					AreaReportArray[9] = 0;

					}
					else{

					BigDecimal a= (BigDecimal) AreaReportArray[9];


					System.out.println(a);
					totalSites=Math.round(a.doubleValue());
					System.out.println(totalSites);


					}


					if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
					AreaReportArray[10] = 0;

					}
					else{
					BigDecimal a= (BigDecimal) AreaReportArray[10];


					System.out.println(a);
					sumProfitLoss=Math.round(a.doubleValue());
					System.out.println(sumProfitLoss);

					}

					System.out.println("The Profit and Loss of site1 is " +sitePL1);
					System.out.println("The Profit and Loss of site2 is " +sitePL2);
					System.out.println("The Profit and Loss of site1 is " +sitePL3);
					System.out.println("The total count of site1 is " +totalnumberof2g);
					System.out.println("The total count of site2 is " +totalnumberof2g3g);
					System.out.println("The total count of site3 is " +totalnumberof2g3g4g);

					String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
					System.out.println("The array" +mapper.writeValueAsString(arrayData));
					arrayData1 = arrayData;
					System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
					data.add(arrayData);

						}
					}

					} // END PROFITABLE CONDITION 







/////////////////////////////////////////////////////////////////////////////////// ahmad ////////////////////////////////////////////////////////////////////////////////



				 
				 
				 
			 }// END OF AREA SELECTED
			
	*/		
			
			
			
		}// END OF MONTHLY choice
		
		
		
		// begining of max only Selected
		
		
		
		if(StringUtils.equalsIgnoreCase(max,"Max") &&(StringUtils.equalsIgnoreCase(checkVal,"null") || checkVal == null || checkVal == "")) {
			
			
			// if area is not selected
			 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
				 String  startdate="";
				String enddate=""; 
				
				//if (area == "all") {
					System.out.println("Area is null and maximim type report is selected");
				    if((StringUtils.equalsIgnoreCase(profitable,"null") || profitable == null || profitable == "") && (StringUtils.equalsIgnoreCase(loss,"null") || loss == null || loss == "")&& (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")  ) {
				    	System.out.println("Yaradgdhtrhyrnth");
				    	String Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where SUM_PROFIT_LOSS IN(Select max(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 )";
				    	q = session.createSQLQuery(Query);


						
					       q.setParameter("param1", StartDate);
					       q.setParameter("param2", EndDate);
					       
					       
					       
					       
					       AreaReportList=q.list();
					       
					       countT=AreaReportList.size();
					       
					       if(AreaReportList.size()>0) {
						    	 
						    		  
						    		  AreaReportArray = (Object[]) AreaReportList.toArray()[0];
					            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
						    		  
						    	  
					            	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
							    		   AreaReportArray[0] ="";
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							         startdate =String.valueOf(AreaReportArray[0])  ;
							    	 }
							    	  
					            	  
					            	  if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
							    		   AreaReportArray[1] ="";
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							         enddate =String.valueOf(AreaReportArray[1])  ;
							    	 }
							    	  
					            	  
					            	  
					            	  
					            	  
						    	  if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
						    		   AreaReportArray[2] ="";
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						           areas =String.valueOf(AreaReportArray[2])  ;
						    	 }
						    	  
						    	  
						    	  
						    	  
						    	   if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
						    		   AreaReportArray[3] = 0.0;
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						    		 
						    		 
						    		BigDecimal a= (BigDecimal) AreaReportArray[3];
						           
						           
						           System.out.println(a);
						           totalnumberof2g=Math.round(a.doubleValue());
						           System.out.println(totalnumberof2g);
						           
						           
						    	 }
						         if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
						    		   AreaReportArray[4] = 0.0;
					             }
					             else{ 
					            	 
					            	 BigDecimal a= (BigDecimal) AreaReportArray[4];
							           
							           
							           System.out.println(a);
							           sitePL1=Math.round(a.doubleValue());
							           System.out.println(sitePL1);
							           
					            	 
					            	 
					             }
						         if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
					    		       AreaReportArray[5] = 0.0;
						         }
						         else {
						        	 
						        	 BigDecimal a= (BigDecimal) AreaReportArray[5];
							           
							           
							           System.out.println(a);
							           totalnumberof2g3g=Math.round(a.doubleValue());
							           System.out.println(totalnumberof2g3g);
							        
						        	 
						        	 
						           
						         } 
						    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
						    		   AreaReportArray[6] = 0.0;
						    	 }
						    	 else {
						    		 

						        	 BigDecimal a= (BigDecimal) AreaReportArray[6];
							           
							           
							           System.out.println(a);
							           sitePL2=Math.round(a.doubleValue());
							           System.out.println(sitePL2);
							        
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
						    		   AreaReportArray[7] = 0;
						    	 }  
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
							           
							           
							           System.out.println(a);
							           totalnumberof2g3g4g=Math.round(a.doubleValue());
							           System.out.println(totalnumberof2g3g4g);
							        
						    		 
						    		 
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
						    		   AreaReportArray[8] = 0;
						    		   
						    	 }
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
							           
							           
							           System.out.println(a);
							           sitePL3=Math.round(a.doubleValue());
							           System.out.println(sitePL3);
							        
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
						    		   AreaReportArray[9] = 0;
						    		   
						    	 }
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[9];
							           
							           
							           System.out.println(a);
							           totalSites=Math.round(a.doubleValue());
							           System.out.println(totalSites);
							        
						    		 
						    	 }
						    		 
						    		
						    	 if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
						    		   AreaReportArray[10] = 0;
						    		   
						    	 }
						    	 else{
						    		 BigDecimal a= (BigDecimal) AreaReportArray[10];
							           
							           
							           System.out.println(a);
							           sumProfitLoss=Math.round(a.doubleValue());
							           System.out.println(sumProfitLoss);
							         
						    	 }
						     
						          System.out.println("The Profit and Loss of site1 is " +sitePL1);
					               System.out.println("The Profit and Loss of site2 is " +sitePL2);
					               System.out.println("The Profit and Loss of site1 is " +sitePL3);
					               System.out.println("The total count of site1 is " +totalnumberof2g);
					               System.out.println("The total count of site2 is " +totalnumberof2g3g);
					               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
					           
					               String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
					               System.out.println("The array" +mapper.writeValueAsString(arrayData));
					               arrayData1 = arrayData;
					               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
					               data.add(arrayData);
					               
					               
							
						    
					    }// end for if
					       
					   	
				    }// end for the condition 
				    
			 // Maximum with technology ONLY 
				    
				    
				    
				    if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && profitable == null && loss == null){
				    	 String Query="";
	                	   
	                	   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
	                		
	                		   Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where PROFIT_AND_LOSS_2G IN(Select MAX(PROFIT_AND_LOSS_2G) from area_finance where start_date >=:param1 and end_date <= :param2 )";
	                	   
	                	   }
	                	   
	                	   
	                	   
	                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
	                		   
	                		  Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where PROFIT_AND_LOSS_2G_3G IN(Select MAX(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >=:param1 and end_date <= :param2 )";
	                		  
				    	
	                	   
	                	   }
	                	   
	                	   
	                	   
	                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
	                		   
	                	 Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN(Select MAX(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >=:param1 and end_date <= :param2 )";
	                	   
	                	   }
	                	   
	                	   
	                	   q = session.createSQLQuery(Query);


							
					       q.setParameter("param1", StartDate);
					       q.setParameter("param2", EndDate);
					       
					       
					       
					       
					       AreaReportList=q.list();
					       
					       countT=AreaReportList.size();
					       
					       if(AreaReportList.size()>0) {
						    	 
						    		  
						    		  AreaReportArray = (Object[]) AreaReportList.toArray()[0];
					            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
						    		  
						    	  
					            	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
							    		   AreaReportArray[0] ="";
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							         startdate =String.valueOf(AreaReportArray[0])  ;
							    	 }
							    	  
					            	  
					            	  if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
							    		   AreaReportArray[1] ="";
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							         enddate =String.valueOf(AreaReportArray[1])  ;
							    	 }
							    	  
					            	  
					            	  
					            	  
					            	  
						    	  if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
						    		   AreaReportArray[2] ="";
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						           areas =String.valueOf(AreaReportArray[2])  ;
						    	 }
						    	  
						    	  
						    	  
						    	  
						    	   if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
						    		   AreaReportArray[3] = 0.0;
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						    		 
						    		 
						    		BigDecimal a= (BigDecimal) AreaReportArray[3];
						           
						           
						           System.out.println(a);
						           totalnumberof2g=Math.round(a.doubleValue());
						           System.out.println(totalnumberof2g);
						           
						           
						    	 }
						         if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
						    		   AreaReportArray[4] = 0.0;
					             }
					             else{ 
					            	 
					            	 BigDecimal a= (BigDecimal) AreaReportArray[4];
							           
							           
							           System.out.println(a);
							           sitePL1=Math.round(a.doubleValue());
							           System.out.println(sitePL1);
							           
					            	 
					            	 
					             }
						         if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
					    		       AreaReportArray[5] = 0.0;
						         }
						         else {
						        	 
						        	 BigDecimal a= (BigDecimal) AreaReportArray[5];
							           
							           
							           System.out.println(a);
							           totalnumberof2g3g=Math.round(a.doubleValue());
							           System.out.println(totalnumberof2g3g);
							        
						        	 
						        	 
						           
						         } 
						    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
						    		   AreaReportArray[6] = 0.0;
						    	 }
						    	 else {
						    		 

						        	 BigDecimal a= (BigDecimal) AreaReportArray[6];
							           
							           
							           System.out.println(a);
							           sitePL2=Math.round(a.doubleValue());
							           System.out.println(sitePL2);
							        
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
						    		   AreaReportArray[7] = 0;
						    	 }  
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
							           
							           
							           System.out.println(a);
							           totalnumberof2g3g4g=Math.round(a.doubleValue());
							           System.out.println(totalnumberof2g3g4g);
							        
						    		 
						    		 
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
						    		   AreaReportArray[8] = 0;
						    		   
						    	 }
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
							           
							           
							           System.out.println(a);
							           sitePL3=Math.round(a.doubleValue());
							           System.out.println(sitePL3);
							        
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
						    		   AreaReportArray[9] = 0;
						    		   
						    	 }
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[9];
							           
							           
							           System.out.println(a);
							           totalSites=Math.round(a.doubleValue());
							           System.out.println(totalSites);
							        
						    		 
						    	 }
						    		 
						    		
						    	 if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
						    		   AreaReportArray[10] = 0;
						    		   
						    	 }
						    	 else{
						    		 BigDecimal a= (BigDecimal) AreaReportArray[10];
							           
							           
							           System.out.println(a);
							           sumProfitLoss=Math.round(a.doubleValue());
							           System.out.println(sumProfitLoss);
							         
						    	 }
						     
						          System.out.println("The Profit and Loss of site1 is " +sitePL1);
					               System.out.println("The Profit and Loss of site2 is " +sitePL2);
					               System.out.println("The Profit and Loss of site1 is " +sitePL3);
					               System.out.println("The total count of site1 is " +totalnumberof2g);
					               System.out.println("The total count of site2 is " +totalnumberof2g3g);
					               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
					           
					               String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
					               System.out.println("The array" +mapper.writeValueAsString(arrayData));
					               arrayData1 = arrayData;
					               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
					               data.add(arrayData);
					               
					               
							
						    
					    }// end for if
				    
	                	   
	                	   
	                	   
				    	
	                   }// end of condition
				    
			 
		
			 }// END OF THE AREA  SELCTED
				
			 
			 
			 
			
			
			 // The area is selected
			 else {
				 
				 String  startdate="";
					String enddate=""; 
					
					//if (area == "all") {
						System.out.println("Area is null and maximim type report is selected");
					    if((StringUtils.equalsIgnoreCase(profitable,"null") || profitable == null || profitable == "") && (StringUtils.equalsIgnoreCase(loss,"null") || loss == null || loss == "")&& (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")  ) {
					    	String Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where SUM_PROFIT_LOSS IN(Select max(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 )";
					    	q = session.createSQLQuery(Query);


							
						       q.setParameter("param1", StartDate);
						       q.setParameter("param2", EndDate);
						       q.setParameter("param3", area);
						       
						       
						       
						       AreaReportList=q.list();
						       
						       countT=AreaReportList.size();
						       
						       if(AreaReportList.size()>0) {
							    	 
							    		  
							    		  AreaReportArray = (Object[]) AreaReportList.toArray()[0];
						            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
							    		  
							    	  
						            	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
								    		   AreaReportArray[0] ="";
								    		   System.out.println("passsssss");
								    	 }
								    	 else {
								         startdate =String.valueOf(AreaReportArray[0])  ;
								    	 }
								    	  
						            	  
						            	  if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
								    		   AreaReportArray[1] ="";
								    		   System.out.println("passsssss");
								    	 }
								    	 else {
								         enddate =String.valueOf(AreaReportArray[1])  ;
								    	 }
								    	  
						            	  
						            	  
						            	  
						            	  
							    	  if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
							    		   AreaReportArray[2] ="";
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							           areas =String.valueOf(AreaReportArray[2])  ;
							    	 }
							    	  
							    	  
							    	  
							    	  
							    	   if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
							    		   AreaReportArray[3] = 0.0;
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							    		 
							    		 
							    		BigDecimal a= (BigDecimal) AreaReportArray[3];
							           
							           
							           System.out.println(a);
							           totalnumberof2g=Math.round(a.doubleValue());
							           System.out.println(totalnumberof2g);
							           
							           
							    	 }
							         if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
							    		   AreaReportArray[4] = 0.0;
						             }
						             else{ 
						            	 
						            	 BigDecimal a= (BigDecimal) AreaReportArray[4];
								           
								           
								           System.out.println(a);
								           sitePL1=Math.round(a.doubleValue());
								           System.out.println(sitePL1);
								           
						            	 
						            	 
						             }
							         if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
						    		       AreaReportArray[5] = 0.0;
							         }
							         else {
							        	 
							        	 BigDecimal a= (BigDecimal) AreaReportArray[5];
								           
								           
								           System.out.println(a);
								           totalnumberof2g3g=Math.round(a.doubleValue());
								           System.out.println(totalnumberof2g3g);
								        
							        	 
							        	 
							           
							         } 
							    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
							    		   AreaReportArray[6] = 0.0;
							    	 }
							    	 else {
							    		 

							        	 BigDecimal a= (BigDecimal) AreaReportArray[6];
								           
								           
								           System.out.println(a);
								           sitePL2=Math.round(a.doubleValue());
								           System.out.println(sitePL2);
								        
							    		 
							    		 
							    		
							    	 }
							    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
							    		   AreaReportArray[7] = 0;
							    	 }  
							    	 else{
							    		 
							    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
								           
								           
								           System.out.println(a);
								           totalnumberof2g3g4g=Math.round(a.doubleValue());
								           System.out.println(totalnumberof2g3g4g);
								        
							    		 
							    		 
							    		 
							    		 
							    		
							    	 }
							    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
							    		   AreaReportArray[8] = 0;
							    		   
							    	 }
							    	 else{
							    		 
							    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
								           
								           
								           System.out.println(a);
								           sitePL3=Math.round(a.doubleValue());
								           System.out.println(sitePL3);
								        
							    		 
							    		 
							    		
							    	 }
							    	 if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
							    		   AreaReportArray[9] = 0;
							    		   
							    	 }
							    	 else{
							    		 
							    		 BigDecimal a= (BigDecimal) AreaReportArray[9];
								           
								           
								           System.out.println(a);
								           totalSites=Math.round(a.doubleValue());
								           System.out.println(totalSites);
								        
							    		 
							    	 }
							    		 
							    		
							    	 if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
							    		   AreaReportArray[10] = 0;
							    		   
							    	 }
							    	 else{
							    		 BigDecimal a= (BigDecimal) AreaReportArray[10];
								           
								           
								           System.out.println(a);
								           sumProfitLoss=Math.round(a.doubleValue());
								           System.out.println(sumProfitLoss);
								         
							    	 }
							     
							          System.out.println("The Profit and Loss of site1 is " +sitePL1);
						               System.out.println("The Profit and Loss of site2 is " +sitePL2);
						               System.out.println("The Profit and Loss of site1 is " +sitePL3);
						               System.out.println("The total count of site1 is " +totalnumberof2g);
						               System.out.println("The total count of site2 is " +totalnumberof2g3g);
						               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
						           
						               String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
						               System.out.println("The array" +mapper.writeValueAsString(arrayData));
						               arrayData1 = arrayData;
						               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
						               data.add(arrayData);
						               
						               
								
							    
						    }// end for if
						       
						       
						       
						       
						       
						   	
					    }// end for the condition 
					    
				 
					    if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && profitable == null && loss == null){
					    	 String Query="";
		                	   
		                	   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
		                		
		                		   Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where PROFIT_AND_LOSS_2G IN(Select MAX(PROFIT_AND_LOSS_2G) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 )";
		                	   
		                	   }
		                	   
		                	   
		                	   
		                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
		                		   
		                		  Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where PROFIT_AND_LOSS_2G_3G IN(Select MAX(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 )";
		                		  
					    	
		                	   
		                	   }
		                	   
		                	   
		                	   
		                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
		                		   
		                	 Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN(Select MAX(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >=:param1 and end_date <= :param2  and area_name=:param3)";
		                	   
		                	   }
		                	   
		                	   
		                	   q = session.createSQLQuery(Query);


								
						       q.setParameter("param1", StartDate);
						       q.setParameter("param2", EndDate);
						       q.setParameter("param3", area);
						       
						       
						       
						       AreaReportList=q.list();
						       
						       countT=AreaReportList.size();
						       
						       if(AreaReportList.size()>0) {
							    	 
							    		  
							    		  AreaReportArray = (Object[]) AreaReportList.toArray()[0];
						            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
							    		  
							    	  
						            	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
								    		   AreaReportArray[0] ="";
								    		   System.out.println("passsssss");
								    	 }
								    	 else {
								         startdate =String.valueOf(AreaReportArray[0])  ;
								    	 }
								    	  
						            	  
						            	  if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
								    		   AreaReportArray[1] ="";
								    		   System.out.println("passsssss");
								    	 }
								    	 else {
								         enddate =String.valueOf(AreaReportArray[1])  ;
								    	 }
								    	  
						            	  
						            	  
						            	  
						            	  
							    	  if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
							    		   AreaReportArray[2] ="";
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							           areas =String.valueOf(AreaReportArray[2])  ;
							    	 }
							    	  
							    	  
							    	  
							    	  
							    	   if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
							    		   AreaReportArray[3] = 0.0;
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							    		 
							    		 
							    		BigDecimal a= (BigDecimal) AreaReportArray[3];
							           
							           
							           System.out.println(a);
							           totalnumberof2g=Math.round(a.doubleValue());
							           System.out.println(totalnumberof2g);
							           
							           
							    	 }
							         if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
							    		   AreaReportArray[4] = 0.0;
						             }
						             else{ 
						            	 
						            	 BigDecimal a= (BigDecimal) AreaReportArray[4];
								           
								           
								           System.out.println(a);
								           sitePL1=Math.round(a.doubleValue());
								           System.out.println(sitePL1);
								           
						            	 
						            	 
						             }
							         if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
						    		       AreaReportArray[5] = 0.0;
							         }
							         else {
							        	 
							        	 BigDecimal a= (BigDecimal) AreaReportArray[5];
								           
								           
								           System.out.println(a);
								           totalnumberof2g3g=Math.round(a.doubleValue());
								           System.out.println(totalnumberof2g3g);
								        
							        	 
							        	 
							           
							         } 
							    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
							    		   AreaReportArray[6] = 0.0;
							    	 }
							    	 else {
							    		 

							        	 BigDecimal a= (BigDecimal) AreaReportArray[6];
								           
								           
								           System.out.println(a);
								           sitePL2=Math.round(a.doubleValue());
								           System.out.println(sitePL2);
								        
							    		 
							    		 
							    		
							    	 }
							    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
							    		   AreaReportArray[7] = 0;
							    	 }  
							    	 else{
							    		 
							    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
								           
								           
								           System.out.println(a);
								           totalnumberof2g3g4g=Math.round(a.doubleValue());
								           System.out.println(totalnumberof2g3g4g);
								        
							    		 
							    		 
							    		 
							    		 
							    		
							    	 }
							    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
							    		   AreaReportArray[8] = 0;
							    		   
							    	 }
							    	 else{
							    		 
							    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
								           
								           
								           System.out.println(a);
								           sitePL3=Math.round(a.doubleValue());
								           System.out.println(sitePL3);
								        
							    		 
							    		 
							    		
							    	 }
							    	 if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
							    		   AreaReportArray[9] = 0;
							    		   
							    	 }
							    	 else{
							    		 
							    		 BigDecimal a= (BigDecimal) AreaReportArray[9];
								           
								           
								           System.out.println(a);
								           totalSites=Math.round(a.doubleValue());
								           System.out.println(totalSites);
								        
							    		 
							    	 }
							    		 
							    		
							    	 if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
							    		   AreaReportArray[10] = 0;
							    		   
							    	 }
							    	 else{
							    		 BigDecimal a= (BigDecimal) AreaReportArray[10];
								           
								           
								           System.out.println(a);
								           sumProfitLoss=Math.round(a.doubleValue());
								           System.out.println(sumProfitLoss);
								         
							    	 }
							     
							          System.out.println("The Profit and Loss of site1 is " +sitePL1);
						               System.out.println("The Profit and Loss of site2 is " +sitePL2);
						               System.out.println("The Profit and Loss of site1 is " +sitePL3);
						               System.out.println("The total count of site1 is " +totalnumberof2g);
						               System.out.println("The total count of site2 is " +totalnumberof2g3g);
						               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
						           
						               String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
						               System.out.println("The array" +mapper.writeValueAsString(arrayData));
						               arrayData1 = arrayData;
						               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
						               data.add(arrayData);
						               
						               
								
							    
						    }// end for if
					    
		                	   
		                	   
		                	   
					    	
		                   }// end of condition
					    
				 
				 
				 
				 
					       
					   	
				    }
				 
				 
			
			 
			 
			 
			
	}// END OF MAXIMUM CHOICE
			
			
if(StringUtils.equalsIgnoreCase(min,"Min")&&(StringUtils.equalsIgnoreCase(checkVal,"null") || checkVal == null || checkVal == "") ) {
			System.out.println("Yara is here ");
			String startdate="";
			String enddate="";
			// if area is not selected
			 if(StringUtils.equalsIgnoreCase(area,"null") || area == null || area == "") {
			    	
				//if (area == "all") {
					
				    if((StringUtils.equalsIgnoreCase(profitable,"null") || profitable == null || profitable == "") && (StringUtils.equalsIgnoreCase(loss,"null") || loss == null || loss == "")&& (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") ) {
				    	
				    	
				    	String Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where SUM_PROFIT_LOSS IN(Select MIN(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 )";
				    	q = session.createSQLQuery(Query);


						
					       q.setParameter("param1", StartDate);
					       q.setParameter("param2", EndDate);
					       
					       
					       
					       
					       AreaReportList=q.list();
					       
					       countT=AreaReportList.size();
					       
					       if(AreaReportList.size()>0) {
						    	 
						    		  
						    		  AreaReportArray = (Object[]) AreaReportList.toArray()[0];
					            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
						    		  
						    	  
					            	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
							    		   AreaReportArray[0] ="";
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							         startdate =String.valueOf(AreaReportArray[0])  ;
							    	 }
							    	  
					            	  
					            	  if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
							    		   AreaReportArray[1] ="";
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							         enddate =String.valueOf(AreaReportArray[1])  ;
							    	 }
							    	  
					            	  
					            	  
					            	  
					            	  
						    	  if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
						    		   AreaReportArray[2] ="";
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						           areas =String.valueOf(AreaReportArray[2])  ;
						    	 }
						    	  
						    	  
						    	  
						    	  
						    	   if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
						    		   AreaReportArray[3] = 0.0;
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						    		 
						    		 
						    		BigDecimal a= (BigDecimal) AreaReportArray[3];
						           
						           
						           System.out.println(a);
						           totalnumberof2g=Math.round(a.doubleValue());
						           System.out.println(totalnumberof2g);
						           
						           
						    	 }
						         if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
						    		   AreaReportArray[4] = 0.0;
					             }
					             else{ 
					            	 
					            	 BigDecimal a= (BigDecimal) AreaReportArray[4];
							           
							           
							           System.out.println(a);
							           sitePL1=Math.round(a.doubleValue());
							           System.out.println(sitePL1);
							           
					            	 
					            	 
					             }
						         if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
					    		       AreaReportArray[5] = 0.0;
						         }
						         else {
						        	 
						        	 BigDecimal a= (BigDecimal) AreaReportArray[5];
							           
							           
							           System.out.println(a);
							           totalnumberof2g3g=Math.round(a.doubleValue());
							           System.out.println(totalnumberof2g3g);
							        
						        	 
						        	 
						           
						         } 
						    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
						    		   AreaReportArray[6] = 0.0;
						    	 }
						    	 else {
						    		 

						        	 BigDecimal a= (BigDecimal) AreaReportArray[6];
							           
							           
							           System.out.println(a);
							           sitePL2=Math.round(a.doubleValue());
							           System.out.println(sitePL2);
							        
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
						    		   AreaReportArray[7] = 0;
						    	 }  
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
							           
							           
							           System.out.println(a);
							           totalnumberof2g3g4g=Math.round(a.doubleValue());
							           System.out.println(totalnumberof2g3g4g);
							        
						    		 
						    		 
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
						    		   AreaReportArray[8] = 0;
						    		   
						    	 }
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
							           
							           
							           System.out.println(a);
							           sitePL3=Math.round(a.doubleValue());
							           System.out.println(sitePL3);
							        
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
						    		   AreaReportArray[9] = 0;
						    		   
						    	 }
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[9];
							           
							           
							           System.out.println(a);
							           totalSites=Math.round(a.doubleValue());
							           System.out.println(totalSites);
							        
						    		 
						    	 }
						    		 
						    		
						    	 if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
						    		   AreaReportArray[10] = 0;
						    		   
						    	 }
						    	 else{
						    		 BigDecimal a= (BigDecimal) AreaReportArray[10];
							           
							           
							           System.out.println(a);
							           sumProfitLoss=Math.round(a.doubleValue());
							           System.out.println(sumProfitLoss);
							         
						    	 }
						     
						          System.out.println("The Profit and Loss of site1 is " +sitePL1);
					               System.out.println("The Profit and Loss of site2 is " +sitePL2);
					               System.out.println("The Profit and Loss of site1 is " +sitePL3);
					               System.out.println("The total count of site1 is " +totalnumberof2g);
					               System.out.println("The total count of site2 is " +totalnumberof2g3g);
					               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
					           
					               String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
					               System.out.println("The array" +mapper.writeValueAsString(arrayData));
					               arrayData1 = arrayData;
					               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
					               data.add(arrayData);
					               
					               
							
						    
					    }// end for if
				    
				    	
				    }// end for the condition 
				    
			 
			 // Minimum based on technology
				    
				    
				    if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && profitable == null && loss == null){
				    	 String Query="";
	                	   
	                	   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
	                		
	                		   Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where PROFIT_AND_LOSS_2G IN(Select MIN(PROFIT_AND_LOSS_2G) from area_finance where start_date >=:param1 and end_date <= :param2 )";
	                	   
	                	   }
	                	   
	                	   
	                	   
	                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
	                		   
	                		  Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where PROFIT_AND_LOSS_2G_3G IN(Select MIN(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >=:param1 and end_date <= :param2 )";
	                		  
				    	
	                	   
	                	   }
	                	   
	                	   
	                	   
	                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
	                		   
	                	 Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN(Select MIN(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >=:param1 and end_date <= :param2 )";
	                	   
	                	   }
	                	   
	                	   
	                	   q = session.createSQLQuery(Query);


							
					       q.setParameter("param1", StartDate);
					       q.setParameter("param2", EndDate);
					       
					       
					       
					       
					       AreaReportList=q.list();
					       
					       countT=AreaReportList.size();
					       
					       if(AreaReportList.size()>0) {
						    	 
						    		  
						    		  AreaReportArray = (Object[]) AreaReportList.toArray()[0];
					            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
						    		  
						    	  
					            	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
							    		   AreaReportArray[0] ="";
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							         startdate =String.valueOf(AreaReportArray[0])  ;
							    	 }
							    	  
					            	  
					            	  if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
							    		   AreaReportArray[1] ="";
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							         enddate =String.valueOf(AreaReportArray[1])  ;
							    	 }
							    	  
					            	  
					            	  
					            	  
					            	  
						    	  if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
						    		   AreaReportArray[2] ="";
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						           areas =String.valueOf(AreaReportArray[2])  ;
						    	 }
						    	  
						    	  
						    	  
						    	  
						    	   if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
						    		   AreaReportArray[3] = 0.0;
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						    		 
						    		 
						    		BigDecimal a= (BigDecimal) AreaReportArray[3];
						           
						           
						           System.out.println(a);
						           totalnumberof2g=Math.round(a.doubleValue());
						           System.out.println(totalnumberof2g);
						           
						           
						    	 }
						         if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
						    		   AreaReportArray[4] = 0.0;
					             }
					             else{ 
					            	 
					            	 BigDecimal a= (BigDecimal) AreaReportArray[4];
							           
							           
							           System.out.println(a);
							           sitePL1=Math.round(a.doubleValue());
							           System.out.println(sitePL1);
							           
					            	 
					            	 
					             }
						         if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
					    		       AreaReportArray[5] = 0.0;
						         }
						         else {
						        	 
						        	 BigDecimal a= (BigDecimal) AreaReportArray[5];
							           
							           
							           System.out.println(a);
							           totalnumberof2g3g=Math.round(a.doubleValue());
							           System.out.println(totalnumberof2g3g);
							        
						        	 
						        	 
						           
						         } 
						    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
						    		   AreaReportArray[6] = 0.0;
						    	 }
						    	 else {
						    		 

						        	 BigDecimal a= (BigDecimal) AreaReportArray[6];
							           
							           
							           System.out.println(a);
							           sitePL2=Math.round(a.doubleValue());
							           System.out.println(sitePL2);
							        
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
						    		   AreaReportArray[7] = 0;
						    	 }  
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
							           
							           
							           System.out.println(a);
							           totalnumberof2g3g4g=Math.round(a.doubleValue());
							           System.out.println(totalnumberof2g3g4g);
							        
						    		 
						    		 
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
						    		   AreaReportArray[8] = 0;
						    		   
						    	 }
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
							           
							           
							           System.out.println(a);
							           sitePL3=Math.round(a.doubleValue());
							           System.out.println(sitePL3);
							        
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
						    		   AreaReportArray[9] = 0;
						    		   
						    	 }
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[9];
							           
							           
							           System.out.println(a);
							           totalSites=Math.round(a.doubleValue());
							           System.out.println(totalSites);
							        
						    		 
						    	 }
						    		 
						    		
						    	 if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
						    		   AreaReportArray[10] = 0;
						    		   
						    	 }
						    	 else{
						    		 BigDecimal a= (BigDecimal) AreaReportArray[10];
							           
							           
							           System.out.println(a);
							           sumProfitLoss=Math.round(a.doubleValue());
							           System.out.println(sumProfitLoss);
							         
						    	 }
						     
						          System.out.println("The Profit and Loss of site1 is " +sitePL1);
					               System.out.println("The Profit and Loss of site2 is " +sitePL2);
					               System.out.println("The Profit and Loss of site1 is " +sitePL3);
					               System.out.println("The total count of site1 is " +totalnumberof2g);
					               System.out.println("The total count of site2 is " +totalnumberof2g3g);
					               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
					           
					               String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
					               System.out.println("The array" +mapper.writeValueAsString(arrayData));
					               arrayData1 = arrayData;
					               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
					               data.add(arrayData);
					               
					               
							
						    
					    }// end for if
				    
	                	   
	                	   
	                	   
				    	
	                   }// end of condition
				    
				    
				   
				    
				   
				    
			 
			 
			 
			 }
			 
			 
			 
		// END OF THE AREA  SELCTED
			
			
			 // The area is selected
			 else {
				 
				 if((StringUtils.equalsIgnoreCase(profitable,"null") || profitable == null || profitable == "") && (StringUtils.equalsIgnoreCase(loss,"null") || loss == null || loss == "")&& (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")  ) {
				    
				    	
				    	String Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where SUM_PROFIT_LOSS IN(Select MIN(SUM_PROFIT_LOSS) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 )";
				    	q = session.createSQLQuery(Query);


						
					       q.setParameter("param1", StartDate);
					       q.setParameter("param2", EndDate);
					       q.setParameter("param3", area);
					       
					       
					       
					       AreaReportList=q.list();
					       
					       countT=AreaReportList.size();
					       
					       if(AreaReportList.size()>0) {
						    	 
						    		  
						    		  AreaReportArray = (Object[]) AreaReportList.toArray()[0];
					            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
						    		  
						    	  
					            	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
							    		   AreaReportArray[0] ="";
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							         startdate =String.valueOf(AreaReportArray[0])  ;
							    	 }
							    	  
					            	  
					            	  if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
							    		   AreaReportArray[1] ="";
							    		   System.out.println("passsssss");
							    	 }
							    	 else {
							         enddate =String.valueOf(AreaReportArray[1])  ;
							    	 }
							    	  
					            	  
					            	  
					            	  
					            	  
						    	  if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
						    		   AreaReportArray[2] ="";
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						           areas =String.valueOf(AreaReportArray[2])  ;
						    	 }
						    	  
						    	  
						    	  
						    	  
						    	   if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
						    		   AreaReportArray[3] = 0.0;
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						    		 
						    		 
						    		BigDecimal a= (BigDecimal) AreaReportArray[3];
						           
						           
						           System.out.println(a);
						           totalnumberof2g=Math.round(a.doubleValue());
						           System.out.println(totalnumberof2g);
						           
						           
						    	 }
						         if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
						    		   AreaReportArray[4] = 0.0;
					             }
					             else{ 
					            	 
					            	 BigDecimal a= (BigDecimal) AreaReportArray[4];
							           
							           
							           System.out.println(a);
							           sitePL1=Math.round(a.doubleValue());
							           System.out.println(sitePL1);
							           
					            	 
					            	 
					             }
						         if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
					    		       AreaReportArray[5] = 0.0;
						         }
						         else {
						        	 
						        	 BigDecimal a= (BigDecimal) AreaReportArray[5];
							           
							           
							           System.out.println(a);
							           totalnumberof2g3g=Math.round(a.doubleValue());
							           System.out.println(totalnumberof2g3g);
							        
						        	 
						        	 
						           
						         } 
						    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
						    		   AreaReportArray[6] = 0.0;
						    	 }
						    	 else {
						    		 

						        	 BigDecimal a= (BigDecimal) AreaReportArray[6];
							           
							           
							           System.out.println(a);
							           sitePL2=Math.round(a.doubleValue());
							           System.out.println(sitePL2);
							        
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
						    		   AreaReportArray[7] = 0;
						    	 }  
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
							           
							           
							           System.out.println(a);
							           totalnumberof2g3g4g=Math.round(a.doubleValue());
							           System.out.println(totalnumberof2g3g4g);
							        
						    		 
						    		 
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
						    		   AreaReportArray[8] = 0;
						    		   
						    	 }
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
							           
							           
							           System.out.println(a);
							           sitePL3=Math.round(a.doubleValue());
							           System.out.println(sitePL3);
							        
						    		 
						    		 
						    		
						    	 }
						    	 if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
						    		   AreaReportArray[9] = 0;
						    		   
						    	 }
						    	 else{
						    		 
						    		 BigDecimal a= (BigDecimal) AreaReportArray[9];
							           
							           
							           System.out.println(a);
							           totalSites=Math.round(a.doubleValue());
							           System.out.println(totalSites);
							        
						    		 
						    	 }
						    		 
						    		
						    	 if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
						    		   AreaReportArray[10] = 0;
						    		   
						    	 }
						    	 else{
						    		 BigDecimal a= (BigDecimal) AreaReportArray[10];
							           
							           
							           System.out.println(a);
							           sumProfitLoss=Math.round(a.doubleValue());
							           System.out.println(sumProfitLoss);
							         
						    	 }
						     
						          System.out.println("The Profit and Loss of site1 is " +sitePL1);
					               System.out.println("The Profit and Loss of site2 is " +sitePL2);
					               System.out.println("The Profit and Loss of site1 is " +sitePL3);
					               System.out.println("The total count of site1 is " +totalnumberof2g);
					               System.out.println("The total count of site2 is " +totalnumberof2g3g);
					               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
					           
					               String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
					               System.out.println("The array" +mapper.writeValueAsString(arrayData));
					               arrayData1 = arrayData;
					               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
					               data.add(arrayData);
					               
					               
							
						    
					    }// end for if
				    	
				    	
				    	
				    	
				    	
				    }// end for the condition 
				    
			 
				 if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && profitable == null && loss == null){
			    	 String Query="";
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
                		
                		   Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where PROFIT_AND_LOSS_2G IN(Select MIN(PROFIT_AND_LOSS_2G) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 )";
                	   
                	   }
                	   
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
                		   
                		  Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where PROFIT_AND_LOSS_2G_3G IN(Select MIN(PROFIT_AND_LOSS_2G_3G) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 )";
                		  
			    	
                	   
                	   }
                	   
                	   
                	   
                	   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
                		   
                	 Query="select TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),area_name,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS  from area_finance where PROFIT_AND_LOSS_2G_3G_4G IN(Select MIN(PROFIT_AND_LOSS_2G_3G_4G) from area_finance where start_date >=:param1 and end_date <= :param2 and area_name=:param3 )";
                	   
                	   }
                	   
                	   
                	   q = session.createSQLQuery(Query);


						
				       q.setParameter("param1", StartDate);
				       q.setParameter("param2", EndDate);
				       q.setParameter("param3", area);
				       
				       
				       
				       AreaReportList=q.list();
				       
				       countT=AreaReportList.size();
				       
				       if(AreaReportList.size()>0) {
					    	 
					    		  
					    		  AreaReportArray = (Object[]) AreaReportList.toArray()[0];
				            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(AreaReportArray));
					    		  
					    	  
				            	  if(AreaReportArray[0] == "null" || AreaReportArray[0] == null || AreaReportArray[0] == "") { 
						    		   AreaReportArray[0] ="";
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						         startdate =String.valueOf(AreaReportArray[0])  ;
						    	 }
						    	  
				            	  
				            	  if(AreaReportArray[1] == "null" || AreaReportArray[1] == null || AreaReportArray[1] == "") { 
						    		   AreaReportArray[1] ="";
						    		   System.out.println("passsssss");
						    	 }
						    	 else {
						         enddate =String.valueOf(AreaReportArray[1])  ;
						    	 }
						    	  
				            	  
				            	  
				            	  
				            	  
					    	  if(AreaReportArray[2] == "null" || AreaReportArray[2] == null || AreaReportArray[2] == "") { 
					    		   AreaReportArray[2] ="";
					    		   System.out.println("passsssss");
					    	 }
					    	 else {
					           areas =String.valueOf(AreaReportArray[2])  ;
					    	 }
					    	  
					    	  
					    	  
					    	  
					    	   if(AreaReportArray[3] == "null" || AreaReportArray[3] == null || AreaReportArray[3] == "") { 
					    		   AreaReportArray[3] = 0.0;
					    		   System.out.println("passsssss");
					    	 }
					    	 else {
					    		 
					    		 
					    		BigDecimal a= (BigDecimal) AreaReportArray[3];
					           
					           
					           System.out.println(a);
					           totalnumberof2g=Math.round(a.doubleValue());
					           System.out.println(totalnumberof2g);
					           
					           
					    	 }
					         if(AreaReportArray[4] == "null" || AreaReportArray[4] == null || AreaReportArray[4] == ""){ 
					    		   AreaReportArray[4] = 0.0;
				             }
				             else{ 
				            	 
				            	 BigDecimal a= (BigDecimal) AreaReportArray[4];
						           
						           
						           System.out.println(a);
						           sitePL1=Math.round(a.doubleValue());
						           System.out.println(sitePL1);
						           
				            	 
				            	 
				             }
					         if(AreaReportArray[5] == "null" || AreaReportArray[5] == null || AreaReportArray[5] == ""){ 
				    		       AreaReportArray[5] = 0.0;
					         }
					         else {
					        	 
					        	 BigDecimal a= (BigDecimal) AreaReportArray[5];
						           
						           
						           System.out.println(a);
						           totalnumberof2g3g=Math.round(a.doubleValue());
						           System.out.println(totalnumberof2g3g);
						        
					        	 
					        	 
					           
					         } 
					    	 if(AreaReportArray[6] == "null" || AreaReportArray[6] == null || AreaReportArray[6] == "") { 
					    		   AreaReportArray[6] = 0.0;
					    	 }
					    	 else {
					    		 

					        	 BigDecimal a= (BigDecimal) AreaReportArray[6];
						           
						           
						           System.out.println(a);
						           sitePL2=Math.round(a.doubleValue());
						           System.out.println(sitePL2);
						        
					    		 
					    		 
					    		
					    	 }
					    	 if(AreaReportArray[7] == "null" || AreaReportArray[7] == null || AreaReportArray[7] == "") { 
					    		   AreaReportArray[7] = 0;
					    	 }  
					    	 else{
					    		 
					    		 BigDecimal a= (BigDecimal) AreaReportArray[7];
						           
						           
						           System.out.println(a);
						           totalnumberof2g3g4g=Math.round(a.doubleValue());
						           System.out.println(totalnumberof2g3g4g);
						        
					    		 
					    		 
					    		 
					    		 
					    		
					    	 }
					    	 if(AreaReportArray[8] == "null" || AreaReportArray[8] == null || AreaReportArray[8] == "") { 
					    		   AreaReportArray[8] = 0;
					    		   
					    	 }
					    	 else{
					    		 
					    		 BigDecimal a= (BigDecimal) AreaReportArray[8];
						           
						           
						           System.out.println(a);
						           sitePL3=Math.round(a.doubleValue());
						           System.out.println(sitePL3);
						        
					    		 
					    		 
					    		
					    	 }
					    	 if(AreaReportArray[9] == "null" || AreaReportArray[9] == null || AreaReportArray[9] == "") { 
					    		   AreaReportArray[9] = 0;
					    		   
					    	 }
					    	 else{
					    		 
					    		 BigDecimal a= (BigDecimal) AreaReportArray[9];
						           
						           
						           System.out.println(a);
						           totalSites=Math.round(a.doubleValue());
						           System.out.println(totalSites);
						        
					    		 
					    	 }
					    		 
					    		
					    	 if(AreaReportArray[10] == "null" || AreaReportArray[10] == null || AreaReportArray[10] == "") { 
					    		   AreaReportArray[10] = 0;
					    		   
					    	 }
					    	 else{
					    		 BigDecimal a= (BigDecimal) AreaReportArray[10];
						           
						           
						           System.out.println(a);
						           sumProfitLoss=Math.round(a.doubleValue());
						           System.out.println(sumProfitLoss);
						         
					    	 }
					     
					          System.out.println("The Profit and Loss of site1 is " +sitePL1);
				               System.out.println("The Profit and Loss of site2 is " +sitePL2);
				               System.out.println("The Profit and Loss of site1 is " +sitePL3);
				               System.out.println("The total count of site1 is " +totalnumberof2g);
				               System.out.println("The total count of site2 is " +totalnumberof2g3g);
				               System.out.println("The total count of site3 is " +totalnumberof2g3g4g);
				           
				               String[] arrayData = {"1",startdate,enddate,areas,String.valueOf(totalnumberof2g),String.valueOf(sitePL1),String.valueOf(totalnumberof2g3g),String.valueOf(sitePL2),String.valueOf(totalnumberof2g3g4g),String.valueOf(sitePL3),String.valueOf(totalSites),String.valueOf(sumProfitLoss)};
				               System.out.println("The array" +mapper.writeValueAsString(arrayData));
				               arrayData1 = arrayData;
				               System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				               data.add(arrayData);
				               
				               
						
					    
				    }// end for if
			    
                	   
                	   
                	   
			    	
                   }// end of condition
			    
				 
			 }
			
	}// END OF minimum CHOICE
				
		

tx.commit();
session.close();


		
		rtn.put("AreaReportList", data);
		return rtn;
		
		
		
}


	
	@RequestMapping(value = "/ProfitAndLossPerSite", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ProfitAndLossPerSite(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException, JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();

		Object startDate = request.getParameter("startDate");
		System.out.println("/*/*/*The StartDate is "+startDate);
		Object endDate = request.getParameter("endDate");
		String wareHouse = request.getParameter("wareHouse");
		String siteName = request.getParameter("siteName");
		String siteId = request.getParameter("siteId");
		Integer accumulated = Integer.parseInt(request.getParameter("accumulated"));
		Integer monthly = Integer.parseInt(request.getParameter("monthly"));
		String  profitable = request.getParameter("Profitable");
		String  loss = request.getParameter("Loss");
		String  max = request.getParameter("Max");
		String  min = request.getParameter("Min");
		String  technologies = request.getParameter("technologies");
		System.out.println("the technology is " +technologies);
		
		Query q,q1,q2,q3;
		List<String[]> Data = new ArrayList<String[]>();
		List<Object[]> SiteReportList = new ArrayList<Object[]>();
		Object[] SiteReportArray = null;
		List<Object[]> SiteReportListInfo = new ArrayList<Object[]>();
		Object[] SiteReportArrayInfo = null;
		String[] arrayData1 = null;
		List<Object[]> tableData = new ArrayList<Object[]>();
		
		Object  dataRev1; Object voiceRev1;  Object smsRev1 ;
		Object grossRev1 ; Object dataTraf1 ; Object smsTrafOg1 ;
		Object voiceTrafOg1 ; Object smsTrafIc1 ; Object voiceTrafIc1 ;
		Object opex1 ; Object profLoss1;

		
		  String SiteID = ""; String SiteName="";String siteOwner = ""; String towerType = ""; String areaName="";String suppTech="";
     		Object dataRev; Object voiceRev = 0; Object smsRev = 0;
     		Object grossRev = 0; Object dataTraf = 0; Object smsTrafOg = 0;
     		Object voiceTrafOg = 0; Object smsTrafIc = 0; Object voiceTrafIc = 0;
     		Object opex = 0; Object profLoss = 0;

		// SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		SimpleDateFormat sdformat2 = new SimpleDateFormat("dd/MM/yyyy");
		ObjectMapper mapper = new ObjectMapper();

		Date start = sdformat.parse(startDate.toString());
		System.out.println("****The startdate before timestamp is "+start);
		Timestamp StartDate = new Timestamp(start.getTime());
		System.out.println("****The date after timestamp is "+StartDate);
		Date end = sdformat.parse(endDate.toString());
		Timestamp EndDate = new Timestamp(end.getTime());
		

		System.out.println("startDate: " + startDate + " endDate: " + endDate + " wareHouse: " + wareHouse
				+ " siteName: " + siteName + " siteId: " + siteId);
		/*System.out
				.println("accumulated: " + accumulated + " monthly: " + monthly + " start: " + start + " end: " + end);*/
		// System.out.println("wareID: "+wareID);

		String query1 = "";
		
		if (siteId != "") {

			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();

			String wareID = wareHouse;
			

			String qcheck = "select wareSite from Warehouse where wareID =:param";
			Query qcheckResult = session.createQuery(qcheck);
			qcheckResult.setParameter("param", wareID);
			Object siteCheck = qcheckResult.uniqueResult();
			
			
			System.out.println("Site ID is ************** " + siteId);
			
			// Check if the wareheoise is a site
			if (Integer.parseInt(String.valueOf((siteCheck))) == 1) {
				// check if the accumaltaed checkbox is checked
				if(accumulated==1) {
					
					System.out.println("Site Chosen, Only Accumulated is checked !!!");
					
					query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
					        +"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
					        +"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
					        +"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
					        +"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
					        +"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
					        +"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 group by site_id) "
					        +"jointable on stable.site_id = jointable.site_id";
	
					q=session.createSQLQuery(query1);
					q.setParameter("param1", StartDate);
				    q.setParameter("param2", EndDate);
				    q.setParameter("param3",siteId );
				    
				    SiteReportList =  q.list();
				    System.out.println("The Query Result is: " +mapper.writeValueAsString(SiteReportList));
				    
				    System.out.println("The zise is "+SiteReportList.size());
				      
				       q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.startDate >= :param1 order by t.startDate asc");
		                q1.setParameter("param1", StartDate);
		                q1.setFirstResult(0);
		                q1.setMaxResults(1);
		                String STARTDATE = (String) q1.uniqueResult();
		                System.out.println("///*/*The start date is " +STARTDATE);
		   
		                q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.endDate <= :param1 order by t.endDate desc");
		                q2.setParameter("param1", EndDate);
		                q2.setFirstResult(0);
		                q2.setMaxResults(1);
		                String ENDDATE = (String) q2.uniqueResult();
		                System.out.println("///*/*The start date is " +ENDDATE);
		                
					      
		               if( SiteReportList.size()>0) {
		            
		            	  for(int i =0; i< SiteReportList.size(); i++)
		            	  {
		            		  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(SiteReportArray));
			            	  String Start=STARTDATE;
			            	  String End=ENDDATE;
			            	  String dateRange = "From " + Start+ " to " + End;
			            	  System.out.println("dateRange is "+dateRange);
			            	
			            	  SiteID = (String)SiteReportArray[0];
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	  System.out.println("/*//The wareName is "+areaName);
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  System.out.println("/*//The areaName is "+areaName);
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  System.out.println("/*//The owner is "+siteOwner);
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  System.out.println("/*//The site Id is "+towerType);
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
			            	  
			            	  
				            	
			            	if(SiteReportArray[5]!=null) {
			            	  dataRev= SiteReportArray[5];
		            	  System.out.println("/*//The Data  Rev is "+dataRev);}
			            	
			            	
			            	else {
			            		 System.out.println("Passes here");
			            		
			            	dataRev="0";	
			            		
			            	}
			            	
			          	  System.out.println("/*//The Data  Rev  after is "+dataRev);
			            	
			          	if(SiteReportArray[6]!=null) {
			            	  voiceRev= SiteReportArray[6];
		            	  System.out.println("/*//The Data  Rev is "+voiceRev);}
			            	
			            	
			            	else {
			            		 System.out.println("Passes here");
			            		
			            		 voiceRev=0;	
			            		
			            	}
			            	
			          	  System.out.println("/*//The Voice Rev  after is "+voiceRev);
			            	
			          	if(SiteReportArray[7]!=null) {
			            	  smsRev= SiteReportArray[7];
		            	  System.out.println("/*//The Data  Rev is "+smsRev);}
			            	
			            	
			            	else {
			            		 System.out.println("Passes here");
			            		
			            		 smsRev=0;	
			            		
			            	}
			            	
			          	  System.out.println("/*//The Sms Rev  after is "+smsRev);
			          	  
			          	if(SiteReportArray[8]!=null) {
			            	  grossRev= SiteReportArray[8];
		            	  System.out.println("/*//The Data  Rev is "+grossRev);}
			            	
			            	
			            	else {
			            		 System.out.println("Passes here");
			            		
			            		 grossRev=0;	
			            		
			            	}
			            	
			          	  System.out.println("/*//The Sms Rev  after is "+grossRev);
			          	  
			          	if(SiteReportArray[9]!=null) {
			          		dataTraf= SiteReportArray[9];
		            	  System.out.println("/*//The Data  Rev is "+dataTraf);}
			            	
			            	
			            	else {
			            		 System.out.println("Passes here");
			            		
			            		 dataTraf=0;	
			            		
			            	}
			            	
			          	  System.out.println("/*//The Sms Rev  after is "+dataTraf);
			          	  
			          	  
			          	  
			          	if(SiteReportArray[10]!=null) {
			          		smsTrafOg= SiteReportArray[10];
		            	  System.out.println("/*//The Data  Rev is "+smsTrafOg);}
			            	
			            	
			            	else {
			            		 System.out.println("Passes here");
			            		
			            		 smsTrafOg=0;	
			            		
			            	}
			            	
			          	  System.out.println("/*//The Sms Rev  after is "+smsTrafOg);
			          	  
			          	if(SiteReportArray[11]!=null) {
			          		voiceTrafOg= SiteReportArray[11];
		            	  System.out.println("/*//The Data  Rev is "+voiceTrafOg);}
			            	
			            	
			            	else {
			            		 System.out.println("Passes here");
			            		
			            		 voiceTrafOg=0;	
			            		
			            	}
			            	
			          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafOg);
			          	    
			          	  
			          	  
			          	if(SiteReportArray[12]!=null) {
			          		smsTrafIc= SiteReportArray[12];
		            	  System.out.println("/*//The Data  Rev is "+smsTrafIc);}
			            	
			            	
			            	else {
			            		 System.out.println("Passes here");
			            		
			            		 smsTrafIc=0;	
			            		
			            	}
			            	
			          	  System.out.println("/*//The Sms Rev  after is "+smsTrafIc);
			          	  
			          	  
			          	if(SiteReportArray[13]!=null) {
			          		voiceTrafIc= SiteReportArray[13];
		            	  System.out.println("/*//The Data  Rev is "+voiceTrafIc);}
			            	
			            	
			            	else {
			            		 System.out.println("Passes here");
			            		
			            		 voiceTrafIc=0;	
			            		
			            	}
			            	
			          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafIc);
			          	  
			          	if(SiteReportArray[14]!=null) {
			          		opex= SiteReportArray[14];
		            	  System.out.println("/*//The Data  Rev is "+opex);}
			            	
			            	
			            	else {
			            		 System.out.println("Passes here");
			            		
			            		 opex=0;	
			            		
			            	}
			            	
			          	  System.out.println("/*//The Sms Rev  after is "+opex);
			          	  
			          	  
			          	  
			          	  
			          	if(SiteReportArray[15]!=null) {
			          		profLoss= SiteReportArray[15];
		            	  System.out.println("/*//The Data  Rev is "+profLoss);}
			            	
			            	
			            	else {
			            		 System.out.println("Passes here");
			            		
			            		 profLoss=0;	
			            		
			            	}
			       
			          	  System.out.println("/*//The Sms Rev  after is "+profLoss);
			          	String[] arrayData= {"1",SiteID,SiteName,STARTDATE,ENDDATE,areaName,siteOwner,towerType,String.valueOf(dataRev),String.valueOf(voiceRev),String.valueOf(smsRev),String.valueOf(grossRev),String.valueOf(dataTraf),String.valueOf(smsTrafOg),String.valueOf(voiceTrafOg),String.valueOf(voiceTrafIc),String.valueOf(smsTrafIc),String.valueOf(opex),String.valueOf(profLoss)};
			          
			          	arrayData1 = arrayData;
			
			          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
			          	Data.add(arrayData1);
			          	
		            	  }
		               }
				
				}
				
				
				else if(monthly == 1) {
					
					System.out.println("Start Condition Monthly");
					
					if(profitable == null && loss == null && max == null && min == null && (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")){
						
						System.out.println("Only Monthly is checked !!!");
						query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),  "
								+ "data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, "
								+"total_sms_traffic_ic, total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss "
								+"where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 order by start_date DESC";
						
						q=session.createSQLQuery(query1);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);
					    q.setParameter("param3",siteId );
					    SiteReportList =  q.list();
					    
					    
					    System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
					    System.out.println("The zise is "+SiteReportList.size());
					    
					    if(SiteReportList.size()>0) {
					    	
					    	for (int i = 0; i < SiteReportList.size(); i++) {
					    	
			            	  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  
			            	  if(SiteReportArray[0]!=null) {
			            	  SiteID=(String)SiteReportArray[0];
			            	  
			            	  }
			            	  
			            	  else {
			            		  SiteID="" ;
			            	  }
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	 
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
				            	  
			            	  if(SiteReportArray[5]!=null) {
				            	  startDate=(String)SiteReportArray[5];
				            	  }
			            	  else {
			            		  startDate="" ;
			            	  }
			            	  
			            	  
			            	  if(SiteReportArray[6]!=null) {
				            	  endDate=SiteReportArray[6];
				            	  
				            	  }
			            	  else {
			            		  endDate="" ;
			            	  }
			            	
			            	  if(SiteReportArray[7]!=null) {
				            	  dataRev1= SiteReportArray[7];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  voiceRev1=  SiteReportArray[8];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[9]!=null) {
				            	  smsRev1= SiteReportArray[9];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[10]!=null) {
				            	  grossRev1= SiteReportArray[10];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[11]!=null) {
				          		dataTraf1= SiteReportArray[11];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafOg1= SiteReportArray[12];
				          		
				          	}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafOg1= SiteReportArray[13];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		smsTrafIc1= SiteReportArray[14];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		voiceTrafIc1= SiteReportArray[15];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[16]!=null) {
				          		opex1= SiteReportArray[16];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[17]!=null) {
				          		profLoss1= SiteReportArray[17];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss1="0";	
				            		
				            	}
				            	
				          	  
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};
					          
				          	arrayData1 = arrayData;
				          	
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
					    	}  
					    } 
					}
					
					if(StringUtils.equalsIgnoreCase(max,"Max") && profitable == null && loss == null)
					{
						if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
				 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							
							q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    q.setParameter("param3",siteId );
						    SiteReportList =  q.list();
						}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
								q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    q.setParameter("param3",siteId );
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
								q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    q.setParameter("param3",siteId );
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
								q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    q.setParameter("param3",siteId );
							    SiteReportList =  q.list();
							}
						}
					    
						System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
					    System.out.println("The zise is "+SiteReportList.size());
					    
					    if(SiteReportList.size()>0) {
					    	
					    	for (int i = 0; i < SiteReportList.size(); i++) {
					    	
			            	  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  
			            	  if(SiteReportArray[0]!=null) {
			            	  SiteID=(String)SiteReportArray[0];
			            	  
			            	  }
			            	  
			            	  else {
			            		  SiteID="" ;
			            	  }
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	 
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
				            	  
			            	  if(SiteReportArray[5]!=null) {
				            	  startDate=(String)SiteReportArray[5];
				            	  }
			            	  else {
			            		  startDate="" ;
			            	  }
			            	  
			            	  
			            	  if(SiteReportArray[6]!=null) {
				            	  endDate=SiteReportArray[6];
				            	  
				            	  }
			            	  else {
			            		  endDate="" ;
			            	  }
			            	
			            	  if(SiteReportArray[7]!=null) {
				            	  dataRev1= SiteReportArray[7];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  voiceRev1=  SiteReportArray[8];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[9]!=null) {
				            	  smsRev1= SiteReportArray[9];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[10]!=null) {
				            	  grossRev1= SiteReportArray[10];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[11]!=null) {
				          		dataTraf1= SiteReportArray[11];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafOg1= SiteReportArray[12];
				          		
				          	}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafOg1= SiteReportArray[13];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		smsTrafIc1= SiteReportArray[14];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		voiceTrafIc1= SiteReportArray[15];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[16]!=null) {
				          		opex1= SiteReportArray[16];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[17]!=null) {
				          		profLoss1= SiteReportArray[17];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss1="0";	
				            		
				            	}
				            	
				          	  
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};
					          
				          	arrayData1 = arrayData;
				          	
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
					    	}  
					    } 
					}
					
					if(StringUtils.equalsIgnoreCase(min,"Min") && profitable == null && loss == null)
					{
						if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
				 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							
							q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    q.setParameter("param3",siteId );
						    SiteReportList =  q.list();
						}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
								q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    q.setParameter("param3",siteId );
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
								q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    q.setParameter("param3",siteId );
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
								q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    q.setParameter("param3",siteId );
							    SiteReportList =  q.list();
							}
						}
					    
						System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
					    System.out.println("The zise is "+SiteReportList.size());
					    
					    if(SiteReportList.size()>0) {
					    	
					    	for (int i = 0; i < SiteReportList.size(); i++) {
					    	
			            	  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  
			            	  if(SiteReportArray[0]!=null) {
			            	  SiteID=(String)SiteReportArray[0];
			            	  
			            	  }
			            	  
			            	  else {
			            		  SiteID="" ;
			            	  }
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	 
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
				            	  
			            	  if(SiteReportArray[5]!=null) {
				            	  startDate=(String)SiteReportArray[5];
				            	  }
			            	  else {
			            		  startDate="" ;
			            	  }
			            	  
			            	  
			            	  if(SiteReportArray[6]!=null) {
				            	  endDate=SiteReportArray[6];
				            	  
				            	  }
			            	  else {
			            		  endDate="" ;
			            	  }
			            	
			            	  if(SiteReportArray[7]!=null) {
				            	  dataRev1= SiteReportArray[7];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  voiceRev1=  SiteReportArray[8];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[9]!=null) {
				            	  smsRev1= SiteReportArray[9];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[10]!=null) {
				            	  grossRev1= SiteReportArray[10];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[11]!=null) {
				          		dataTraf1= SiteReportArray[11];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafOg1= SiteReportArray[12];
				          		
				          	}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafOg1= SiteReportArray[13];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		smsTrafIc1= SiteReportArray[14];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		voiceTrafIc1= SiteReportArray[15];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[16]!=null) {
				          		opex1= SiteReportArray[16];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[17]!=null) {
				          		profLoss1= SiteReportArray[17];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss1="0";	
				            		
				            	}
				            	
				          	  
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};
					          
				          	arrayData1 = arrayData;
				          	
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
					    	}  
					    } 
					}
					
					if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g")||StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && profitable == null && loss == null && min == null && max == null)
					{
						if(StringUtils.equalsIgnoreCase(technologies,"2g"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 order by start_date DESC";
							
							q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    q.setParameter("param3",siteId );
						    SiteReportList =  q.list();
						}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 order by start_date DESC";
							
							q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    q.setParameter("param3",siteId );
						    SiteReportList =  q.list();
						}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 order by start_date DESC";
							
							q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    q.setParameter("param3",siteId );
						    SiteReportList =  q.list();
						}
						System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
					    System.out.println("The zise is "+SiteReportList.size());
					    
					    if(SiteReportList.size()>0) {
					    	
					    	for (int i = 0; i < SiteReportList.size(); i++) {
					    	
			            	  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  
			            	  if(SiteReportArray[0]!=null) {
			            	  SiteID=(String)SiteReportArray[0];
			            	  
			            	  }
			            	  
			            	  else {
			            		  SiteID="" ;
			            	  }
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	 
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
				            	  
			            	  if(SiteReportArray[5]!=null) {
				            	  startDate=(String)SiteReportArray[5];
				            	  }
			            	  else {
			            		  startDate="" ;
			            	  }
			            	  
			            	  
			            	  if(SiteReportArray[6]!=null) {
				            	  endDate=SiteReportArray[6];
				            	  
				            	  }
			            	  else {
			            		  endDate="" ;
			            	  }
			            	
			            	  if(SiteReportArray[7]!=null) {
				            	  dataRev1= SiteReportArray[7];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  voiceRev1=  SiteReportArray[8];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[9]!=null) {
				            	  smsRev1= SiteReportArray[9];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[10]!=null) {
				            	  grossRev1= SiteReportArray[10];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[11]!=null) {
				          		dataTraf1= SiteReportArray[11];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafOg1= SiteReportArray[12];
				          		
				          	}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafOg1= SiteReportArray[13];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		smsTrafIc1= SiteReportArray[14];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		voiceTrafIc1= SiteReportArray[15];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[16]!=null) {
				          		opex1= SiteReportArray[16];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[17]!=null) {
				          		profLoss1= SiteReportArray[17];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss1="0";	
				            		
				            	}
				            	
				          	  
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};
					          
				          	arrayData1 = arrayData;
				          	
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
					    	}  
					    } 
					}
					
					if(StringUtils.equalsIgnoreCase(profitable,"Profitable"))
					{
						
						if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),  "
									+ "data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, "
									+"total_sms_traffic_ic, total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss "
									+"where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 order by start_date DESC";
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
				 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
				 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
						}
						
						if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && min == null && max == null)
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 order by start_date DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 order by start_date DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 order by start_date DESC";
							}
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
									+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
									+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
									+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 group by TO_CHAR(start_date, 'YYYY-MM')))"
									+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
									+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
									+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
									+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
						}
						if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))"
										+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))"
										+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')))"
										+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
							}
						}
					    
				    	q=session.createSQLQuery(query1);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);
					    q.setParameter("param3",siteId );
					    SiteReportList =  q.list();
					    
					    System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
					    System.out.println("The zise is "+SiteReportList.size());
					    
					    if(SiteReportList.size()>0) {
					    	
					    	for (int i = 0; i < SiteReportList.size(); i++) {
					    	
			            	  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  
			            	  if(SiteReportArray[0]!=null) {
			            	  SiteID=(String)SiteReportArray[0];
			            	  
			            	  }
			            	  
			            	  else {
			            		  SiteID="" ;
			            	  }
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	 
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
				            	  
			            	  if(SiteReportArray[5]!=null) {
				            	  startDate=(String)SiteReportArray[5];
				            	  }
			            	  else {
			            		  startDate="" ;
			            	  }
			            	  
			            	  
			            	  if(SiteReportArray[6]!=null) {
				            	  endDate=SiteReportArray[6];
				            	  
				            	  }
			            	  else {
			            		  endDate="" ;
			            	  }
			            	
			            	  if(SiteReportArray[7]!=null) {
				            	  dataRev1= SiteReportArray[7];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  voiceRev1=  SiteReportArray[8];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[9]!=null) {
				            	  smsRev1= SiteReportArray[9];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[10]!=null) {
				            	  grossRev1= SiteReportArray[10];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[11]!=null) {
				          		dataTraf1= SiteReportArray[11];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafOg1= SiteReportArray[12];
				          		
				          	}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafOg1= SiteReportArray[13];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		smsTrafIc1= SiteReportArray[14];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		voiceTrafIc1= SiteReportArray[15];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[16]!=null) {
				          		opex1= SiteReportArray[16];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[17]!=null) {
				          		profLoss1= SiteReportArray[17];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss1="0";	
				            		
				            	}
				            	
				          	  
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};
					          
				          	arrayData1 = arrayData;
				          	
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
					    	}  
					    } 
					}
					
					if(StringUtils.equalsIgnoreCase(loss,"Loss"))
					{
						
						if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),  "
									+ "data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, "
									+"total_sms_traffic_ic, total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss "
									+"where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 order by start_date DESC";
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
				 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
				 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
						}
						
						if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && min == null && max == null)
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 order by start_date DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 order by start_date DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 order by start_date DESC";
							}
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
									+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
									+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
									+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 group by TO_CHAR(start_date, 'YYYY-MM')))"
									+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
									+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
									+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
									+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and  site_id = :param3 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and  site_id = :param3 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and  site_id = :param3 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
						}
						if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))"
										+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))"
										+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')))"
										+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and  site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
							}
						}
					    
				    	q=session.createSQLQuery(query1);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);
					    q.setParameter("param3",siteId );
					    SiteReportList =  q.list();
					    
					    System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
					    System.out.println("The zise is "+SiteReportList.size());
					    
					    if(SiteReportList.size()>0) {
					    	
					    	for (int i = 0; i < SiteReportList.size(); i++) {
					    	
			            	  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  
			            	  if(SiteReportArray[0]!=null) {
			            	  SiteID=(String)SiteReportArray[0];
			            	  
			            	  }
			            	  
			            	  else {
			            		  SiteID="" ;
			            	  }
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	 
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
				            	  
			            	  if(SiteReportArray[5]!=null) {
				            	  startDate=(String)SiteReportArray[5];
				            	  }
			            	  else {
			            		  startDate="" ;
			            	  }
			            	  
			            	  
			            	  if(SiteReportArray[6]!=null) {
				            	  endDate=SiteReportArray[6];
				            	  
				            	  }
			            	  else {
			            		  endDate="" ;
			            	  }
			            	
			            	  if(SiteReportArray[7]!=null) {
				            	  dataRev1= SiteReportArray[7];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  voiceRev1=  SiteReportArray[8];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[9]!=null) {
				            	  smsRev1= SiteReportArray[9];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[10]!=null) {
				            	  grossRev1= SiteReportArray[10];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[11]!=null) {
				          		dataTraf1= SiteReportArray[11];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafOg1= SiteReportArray[12];
				          		
				          	}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafOg1= SiteReportArray[13];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		smsTrafIc1= SiteReportArray[14];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		voiceTrafIc1= SiteReportArray[15];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[16]!=null) {
				          		opex1= SiteReportArray[16];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[17]!=null) {
				          		profLoss1= SiteReportArray[17];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss1="0";	
				            		
				            	}
				            	
				          	  
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};
					          
				          	arrayData1 = arrayData;
				          	
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
					    	}  
					    } 
					} ////  monthly option with site chosen
					
				}
				
				
				
				
				
			}//end of the if of checked if it is a site or no
			
			
			
			else {
				
				System.out.println("Warehouse: " + wareID + " is not a site.");	
				
				
				
			}
			
			
		

		}// end of the first if the site is selected
		else if (siteId == "") {
			
			System.out.println("Passes here in the else ");

			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();

				Query query = session.createQuery("select distinct t.wareID from Warehouse t where t.wareSite=:param1");
				query.setParameter("param1", '1');
				System.out.println("The main query is " +mapper.writeValueAsString(query.list()));	
				
				 Map<String, Double> ProfitableMap = new HashMap<String, Double>();
				 Map<String, Double> LossMap = new HashMap<String, Double>();
				
				if(accumulated==1) {
					
					System.out.println("Start Condition Accumulated");
					
					if(profitable == null && loss == null && max == null && min == null && (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")){
						
						System.out.println("Only Accumulated is checked !!!");
						query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
								+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
								+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
								+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
								+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
								+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
								+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 group by site_id) "
								+"jointable on stable.site_id = jointable.site_id";
						
						System.out.println(query1);
				    	q=session.createSQLQuery(query1);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);
					    
						SiteReportList =  q.list();
					    System.out.println("The Query Result is: " +mapper.writeValueAsString(SiteReportList));
					    
					    System.out.println("The zise is "+SiteReportList.size());
					      
					       q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.startDate >= :param1 order by t.startDate asc");
			                q1.setParameter("param1", StartDate);
			                q1.setFirstResult(0);
			                q1.setMaxResults(1);
			                String STARTDATE = (String) q1.uniqueResult();
			                System.out.println("///*/*The start date is " +STARTDATE);
			   
			                q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.endDate <= :param1 order by t.endDate desc");
			                q2.setParameter("param1", EndDate);
			                q2.setFirstResult(0);
			                q2.setMaxResults(1);
			                String ENDDATE = (String) q2.uniqueResult();
			                System.out.println("///*/*The start date is " +ENDDATE);
			                
						      
			               if( SiteReportList.size()>0) {
			            
			            	  for(int i =0; i< SiteReportList.size(); i++)
			            	  {
			            		  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
				            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(SiteReportArray));
				            	  String Start=STARTDATE;
				            	  String End=ENDDATE;
				            	  String dateRange = "From " + Start+ " to " + End;
				            	  System.out.println("dateRange is "+dateRange);
				            	
				            	  SiteID = (String)SiteReportArray[0];
				            	  
				               	  if(SiteReportArray[1]!=null) {
						            	 SiteName=(String)SiteReportArray[1];
						            	  System.out.println("/*//The wareName is "+areaName);
						            	  }
					            	  else {
					            		  SiteName="" ;
					            	  } 
					            	  
					            	  if(SiteReportArray[2]!=null) {
						            	  areaName=(String)SiteReportArray[2];
						            	  System.out.println("/*//The areaName is "+areaName);
						            	  }
					            	  else {
					            		  areaName="" ;
					            	  } 
					            	  
					            	  if(SiteReportArray[3]!=null) {
						            	  siteOwner=(String)SiteReportArray[3];
						            	  System.out.println("/*//The owner is "+siteOwner);
						            	  }
					            	  else {
					            		  siteOwner="" ;
					            	  } 
					            	  
					            	  
					            	  if(SiteReportArray[4]!=null) {
						            	  towerType=(String)SiteReportArray[4];
						            	  System.out.println("/*//The site Id is "+towerType);
						            	  }
					            	  else {
					            		  towerType="" ;
					            	  } 
				            	  
				            	  
					            	
				            	if(SiteReportArray[5]!=null) {
				            	  dataRev= SiteReportArray[5];
			            	  System.out.println("/*//The Data  Rev is "+dataRev);}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev="0";	
				            		
				            	}
				            	
				          	  System.out.println("/*//The Data  Rev  after is "+dataRev);
				            	
				          	if(SiteReportArray[6]!=null) {
				            	  voiceRev= SiteReportArray[6];
			            	  System.out.println("/*//The Data  Rev is "+voiceRev);}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev=0;	
				            		
				            	}
				            	
				          	  System.out.println("/*//The Voice Rev  after is "+voiceRev);
				            	
				          	if(SiteReportArray[7]!=null) {
				            	  smsRev= SiteReportArray[7];
			            	  System.out.println("/*//The Data  Rev is "+smsRev);}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev=0;	
				            		
				            	}
				            	
				          	  System.out.println("/*//The Sms Rev  after is "+smsRev);
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  grossRev= SiteReportArray[8];
			            	  System.out.println("/*//The Data  Rev is "+grossRev);}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev=0;	
				            		
				            	}
				            	
				          	  System.out.println("/*//The Sms Rev  after is "+grossRev);
				          	  
				          	if(SiteReportArray[9]!=null) {
				          		dataTraf= SiteReportArray[9];
			            	  System.out.println("/*//The Data  Rev is "+dataTraf);}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf=0;	
				            		
				            	}
				            	
				          	  System.out.println("/*//The Sms Rev  after is "+dataTraf);
				          	  
				          	  
				          	  
				          	if(SiteReportArray[10]!=null) {
				          		smsTrafOg= SiteReportArray[10];
			            	  System.out.println("/*//The Data  Rev is "+smsTrafOg);}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg=0;	
				            		
				            	}
				            	
				          	  System.out.println("/*//The Sms Rev  after is "+smsTrafOg);
				          	  
				          	if(SiteReportArray[11]!=null) {
				          		voiceTrafOg= SiteReportArray[11];
			            	  System.out.println("/*//The Data  Rev is "+voiceTrafOg);}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg=0;	
				            		
				            	}
				            	
				          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafOg);
				          	    
				          	  
				          	  
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafIc= SiteReportArray[12];
			            	  System.out.println("/*//The Data  Rev is "+smsTrafIc);}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc=0;	
				            		
				            	}
				            	
				          	  System.out.println("/*//The Sms Rev  after is "+smsTrafIc);
				          	  
				          	  
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafIc= SiteReportArray[13];
			            	  System.out.println("/*//The Data  Rev is "+voiceTrafIc);}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc=0;	
				            		
				            	}
				            	
				          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafIc);
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		opex= SiteReportArray[14];
			            	  System.out.println("/*//The Data  Rev is "+opex);}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex=0;	
				            		
				            	}
				            	
				          	  System.out.println("/*//The Sms Rev  after is "+opex);
				          	  
				          	  
				          	  
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		profLoss= SiteReportArray[15];
			            	  System.out.println("/*//The Data  Rev is "+profLoss);}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss=0;	
				            		
				            	}
				       
				          	  System.out.println("/*//The Sms Rev  after is "+profLoss);
				          	String[] arrayData= {"1",SiteID,SiteName,STARTDATE,ENDDATE,areaName,siteOwner,towerType,String.valueOf(dataRev),String.valueOf(voiceRev),String.valueOf(smsRev),String.valueOf(grossRev),String.valueOf(dataTraf),String.valueOf(smsTrafOg),String.valueOf(voiceTrafOg),String.valueOf(voiceTrafIc),String.valueOf(smsTrafIc),String.valueOf(opex),String.valueOf(profLoss)};
				          
				          	arrayData1 = arrayData;
				
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
				          	
			            	  }
			               }
					}
					
					if(StringUtils.equalsIgnoreCase(max,"Max") && profitable == null && loss == null){
						
						System.out.println("Options are: Accumulate is Checked, Profitable Not Checked, Loss Not Checked, Max is Checked");
						
						if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") {	
					    	 System.out.println("MAX is CHECKED BUT NO TOCHNOLOGY IS SELECTED ");
					    	 
					    	 query1 = "select * from (select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.dataSum, jointable.voiceSum, jointable.smsSum, "
										+"jointable.grossSum, jointable.dataTrafficSum, jointable.smsogSum, jointable.voiceogSum, jointable.smsicSum, "
										+"jointable.voiceicSum, jointable.opexSum, jointable.ProfitLossSum from warehouse_profit_loss stable right join "
										+"(select site_id, sum(data) as dataSum, sum(voice_revenu) as voiceSum, "
										+"sum(sms_revenu) as smsSum, sum(gross_revenu) as grossSum, sum(data_traffic) as dataTrafficSum, "
										+"sum(total_sms_traffic_og) as smsogSum, sum(total_voice_traffic_og) as voiceogSum, sum(total_sms_traffic_ic) "
										+"as smsicSum, sum(total_voice_traffic_og) as voiceicSum, sum(total_opex_monthly) as opexSum, sum(profit_and_loss) "
										+"as ProfitLossSum from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id order by ProfitLossSum DESC) where rownum = 1 ";
					    	 
						}
						// condition
						if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g")){
							
								query1 = "select * from (select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.dataSum, jointable.voiceSum, jointable.smsSum, "
										+"jointable.grossSum, jointable.dataTrafficSum, jointable.smsogSum, jointable.voiceogSum, jointable.smsicSum, "
										+"jointable.voiceicSum, jointable.opexSum, jointable.ProfitLossSum from warehouse_profit_loss stable right join "
										+"(select site_id, sum(data) as dataSum, sum(voice_revenu) as voiceSum, "
										+"sum(sms_revenu) as smsSum, sum(gross_revenu) as grossSum, sum(data_traffic) as dataTrafficSum, "
										+"sum(total_sms_traffic_og) as smsogSum, sum(total_voice_traffic_og) as voiceogSum, sum(total_sms_traffic_ic) "
										+"as smsicSum, sum(total_voice_traffic_og) as voiceicSum, sum(total_opex_monthly) as opexSum, sum(profit_and_loss) "
										+"as ProfitLossSum from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id order by ProfitLossSum DESC) where rownum = 1 ";
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
								
								query1 = "select * from (select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.dataSum, jointable.voiceSum, jointable.smsSum, "
										+"jointable.grossSum, jointable.dataTrafficSum, jointable.smsogSum, jointable.voiceogSum, jointable.smsicSum, "
										+"jointable.voiceicSum, jointable.opexSum, jointable.ProfitLossSum from warehouse_profit_loss stable right join "
										+"(select site_id, sum(data) as dataSum, sum(voice_revenu) as voiceSum, "
										+"sum(sms_revenu) as smsSum, sum(gross_revenu) as grossSum, sum(data_traffic) as dataTrafficSum, "
										+"sum(total_sms_traffic_og) as smsogSum, sum(total_voice_traffic_og) as voiceogSum, sum(total_sms_traffic_ic) "
										+"as smsicSum, sum(total_voice_traffic_og) as voiceicSum, sum(total_opex_monthly) as opexSum, sum(profit_and_loss) "
										+"as ProfitLossSum from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id order by ProfitLossSum DESC) where rownum = 1 ";
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
								query1 = "select * from (select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.dataSum, jointable.voiceSum, jointable.smsSum, "
										+"jointable.grossSum, jointable.dataTrafficSum, jointable.smsogSum, jointable.voiceogSum, jointable.smsicSum, "
										+"jointable.voiceicSum, jointable.opexSum, jointable.ProfitLossSum from warehouse_profit_loss stable right join "
										+"(select site_id, sum(data) as dataSum, sum(voice_revenu) as voiceSum, "
										+"sum(sms_revenu) as smsSum, sum(gross_revenu) as grossSum, sum(data_traffic) as dataTrafficSum, "
										+"sum(total_sms_traffic_og) as smsogSum, sum(total_voice_traffic_og) as voiceogSum, sum(total_sms_traffic_ic) "
										+"as smsicSum, sum(total_voice_traffic_og) as voiceicSum, sum(total_opex_monthly) as opexSum, sum(profit_and_loss) "
										+"as ProfitLossSum from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id order by ProfitLossSum DESC) where rownum = 1 ";
							}
							
						}
						
						System.out.println(query1);
				    	q=session.createSQLQuery(query1);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);
					    
						SiteReportList =  q.list();
					    System.out.println("The Query Result is: " +mapper.writeValueAsString(SiteReportList));
					    
					    System.out.println("The zise is "+SiteReportList.size());
					      
					       q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.startDate >= :param1 order by t.startDate asc");
			                q1.setParameter("param1", StartDate);
			                q1.setFirstResult(0);
			                q1.setMaxResults(1);
			                String STARTDATE = (String) q1.uniqueResult();
			                System.out.println("///*/*The start date is " +STARTDATE);
			   
			                q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.endDate <= :param1 order by t.endDate desc");
			                q2.setParameter("param1", EndDate);
			                q2.setFirstResult(0);
			                q2.setMaxResults(1);
			                String ENDDATE = (String) q2.uniqueResult();
			                System.out.println("///*/*The start date is " +ENDDATE);
			                
						      
			               if( SiteReportList.size()>0) {
			            
			            	  for(int i =0; i< SiteReportList.size(); i++)
			            	  {
			            		  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
				            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(SiteReportArray));
				            	  String Start=STARTDATE;
				            	  String End=ENDDATE;
				            	  String dateRange = "From " + Start+ " to " + End;
				            	  System.out.println("dateRange is "+dateRange);
				            	
				            	  SiteID = (String)SiteReportArray[0];
				            	  
				               	  if(SiteReportArray[1]!=null) {
						            	 SiteName=(String)SiteReportArray[1];
						            	  System.out.println("/*//The wareName is "+areaName);
						            	  }
					            	  else {
					            		  SiteName="" ;
					            	  } 
					            	  
					            	  if(SiteReportArray[2]!=null) {
						            	  areaName=(String)SiteReportArray[2];
						            	  System.out.println("/*//The areaName is "+areaName);
						            	  }
					            	  else {
					            		  areaName="" ;
					            	  } 
					            	  
					            	  if(SiteReportArray[3]!=null) {
						            	  siteOwner=(String)SiteReportArray[3];
						            	  System.out.println("/*//The owner is "+siteOwner);
						            	  }
					            	  else {
					            		  siteOwner="" ;
					            	  } 
					            	  
					            	  
					            	  if(SiteReportArray[4]!=null) {
						            	  towerType=(String)SiteReportArray[4];
						            	  System.out.println("/*//The site Id is "+towerType);
						            	  }
					            	  else {
					            		  towerType="" ;
					            	  } 
				            	  
				            	  
					            	
					            	  if(SiteReportArray[5]!=null) {
						            	  dataRev= SiteReportArray[5];
					            	  System.out.println("/*//The Data  Rev is "+dataRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            	dataRev="0";	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Data  Rev  after is "+dataRev);
						            	
						          	if(SiteReportArray[6]!=null) {
						            	  voiceRev= SiteReportArray[6];
					            	  System.out.println("/*//The Data  Rev is "+voiceRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Voice Rev  after is "+voiceRev);
						            	
						          	if(SiteReportArray[7]!=null) {
						            	  smsRev= SiteReportArray[7];
					            	  System.out.println("/*//The Data  Rev is "+smsRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsRev);
						          	  
						          	if(SiteReportArray[8]!=null) {
						            	  grossRev= SiteReportArray[8];
					            	  System.out.println("/*//The Data  Rev is "+grossRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 grossRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+grossRev);
						          	  
						          	if(SiteReportArray[9]!=null) {
						          		dataTraf= SiteReportArray[9];
					            	  System.out.println("/*//The Data  Rev is "+dataTraf);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 dataTraf=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+dataTraf);
						          	  
						          	  
						          	  
						          	if(SiteReportArray[10]!=null) {
						          		smsTrafOg= SiteReportArray[10];
					            	  System.out.println("/*//The Data  Rev is "+smsTrafOg);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsTrafOg=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsTrafOg);
						          	  
						          	if(SiteReportArray[11]!=null) {
						          		voiceTrafOg= SiteReportArray[11];
					            	  System.out.println("/*//The Data  Rev is "+voiceTrafOg);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceTrafOg=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafOg);
						          	    
						          	  
						          	  
						          	if(SiteReportArray[12]!=null) {
						          		smsTrafIc= SiteReportArray[12];
					            	  System.out.println("/*//The Data  Rev is "+smsTrafIc);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsTrafIc=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsTrafIc);
						          	  
						          	  
						          	if(SiteReportArray[13]!=null) {
						          		voiceTrafIc= SiteReportArray[13];
					            	  System.out.println("/*//The Data  Rev is "+voiceTrafIc);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceTrafIc=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafIc);
						          	  
						          	if(SiteReportArray[14]!=null) {
						          		opex= SiteReportArray[14];
					            	  System.out.println("/*//The Data  Rev is "+opex);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 opex=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+opex);
						          	  
						          	  
						          	  
						          	  
						          	if(SiteReportArray[15]!=null) {
						          		profLoss= SiteReportArray[15];
					            	  System.out.println("/*//The Data  Rev is "+profLoss);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 profLoss=0;	
						            		
						            	}
						          	
				          	  System.out.println("/*//The Sms Rev  after is "+profLoss);
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,STARTDATE,ENDDATE,areaName,siteOwner,towerType,String.valueOf(dataRev),String.valueOf(voiceRev),String.valueOf(smsRev),String.valueOf(grossRev),String.valueOf(dataTraf),String.valueOf(smsTrafOg),String.valueOf(voiceTrafOg),String.valueOf(voiceTrafIc),String.valueOf(smsTrafIc),String.valueOf(opex),String.valueOf(profLoss)};
				          
				          	arrayData1 = arrayData;
				
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
				          	
			            	  }
			               }
						
					}
				
					if(StringUtils.equalsIgnoreCase(min,"Min") && profitable == null && loss == null){
						
						System.out.println("Options are: Accumulate is Checked, Profitable Not Checked, Loss Not Checked, Min is Checked");
						
						if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") {	
					    	 System.out.println("NO TOCHNOLOGY IS SELECTED ");
					    	 
					    	 query1 = "select * from (select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.dataSum, jointable.voiceSum, jointable.smsSum, "
										+"jointable.grossSum, jointable.dataTrafficSum, jointable.smsogSum, jointable.voiceogSum, jointable.smsicSum, "
										+"jointable.voiceicSum, jointable.opexSum, jointable.ProfitLossSum from warehouse_profit_loss stable right join "
										+"(select site_id, sum(data) as dataSum, sum(voice_revenu) as voiceSum, "
										+"sum(sms_revenu) as smsSum, sum(gross_revenu) as grossSum, sum(data_traffic) as dataTrafficSum, "
										+"sum(total_sms_traffic_og) as smsogSum, sum(total_voice_traffic_og) as voiceogSum, sum(total_sms_traffic_ic) "
										+"as smsicSum, sum(total_voice_traffic_og) as voiceicSum, sum(total_opex_monthly) as opexSum, sum(profit_and_loss) "
										+"as ProfitLossSum from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id order by ProfitLossSum ASC) where rownum = 1 ";
						}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g")){
							
							 query1 = "select * from (select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.dataSum, jointable.voiceSum, jointable.smsSum, "
										+"jointable.grossSum, jointable.dataTrafficSum, jointable.smsogSum, jointable.voiceogSum, jointable.smsicSum, "
										+"jointable.voiceicSum, jointable.opexSum, jointable.ProfitLossSum from warehouse_profit_loss stable right join "
										+"(select site_id, sum(data) as dataSum, sum(voice_revenu) as voiceSum, "
										+"sum(sms_revenu) as smsSum, sum(gross_revenu) as grossSum, sum(data_traffic) as dataTrafficSum, "
										+"sum(total_sms_traffic_og) as smsogSum, sum(total_voice_traffic_og) as voiceogSum, sum(total_sms_traffic_ic) "
										+"as smsicSum, sum(total_voice_traffic_og) as voiceicSum, sum(total_opex_monthly) as opexSum, sum(profit_and_loss) "
										+"as ProfitLossSum from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id order by ProfitLossSum ASC) where rownum = 1 ";
							
						}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
							
							 query1 = "select * from (select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.dataSum, jointable.voiceSum, jointable.smsSum, "
										+"jointable.grossSum, jointable.dataTrafficSum, jointable.smsogSum, jointable.voiceogSum, jointable.smsicSum, "
										+"jointable.voiceicSum, jointable.opexSum, jointable.ProfitLossSum from warehouse_profit_loss stable right join "
										+"(select site_id, sum(data) as dataSum, sum(voice_revenu) as voiceSum, "
										+"sum(sms_revenu) as smsSum, sum(gross_revenu) as grossSum, sum(data_traffic) as dataTrafficSum, "
										+"sum(total_sms_traffic_og) as smsogSum, sum(total_voice_traffic_og) as voiceogSum, sum(total_sms_traffic_ic) "
										+"as smsicSum, sum(total_voice_traffic_og) as voiceicSum, sum(total_opex_monthly) as opexSum, sum(profit_and_loss) "
										+"as ProfitLossSum from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id order by ProfitLossSum ASC) where rownum = 1 ";
							
						}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
							
							 query1 = "select * from (select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.dataSum, jointable.voiceSum, jointable.smsSum, "
										+"jointable.grossSum, jointable.dataTrafficSum, jointable.smsogSum, jointable.voiceogSum, jointable.smsicSum, "
										+"jointable.voiceicSum, jointable.opexSum, jointable.ProfitLossSum from warehouse_profit_loss stable right join "
										+"(select site_id, sum(data) as dataSum, sum(voice_revenu) as voiceSum, "
										+"sum(sms_revenu) as smsSum, sum(gross_revenu) as grossSum, sum(data_traffic) as dataTrafficSum, "
										+"sum(total_sms_traffic_og) as smsogSum, sum(total_voice_traffic_og) as voiceogSum, sum(total_sms_traffic_ic) "
										+"as smsicSum, sum(total_voice_traffic_og) as voiceicSum, sum(total_opex_monthly) as opexSum, sum(profit_and_loss) "
										+"as ProfitLossSum from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id order by ProfitLossSum ASC) where rownum = 1 ";
							
						}
						
						
						System.out.println(query1);
				    	q=session.createSQLQuery(query1);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);
					    
						SiteReportList =  q.list();
					    System.out.println("The Query Result is: " +mapper.writeValueAsString(SiteReportList));
					    
					    System.out.println("The zise is "+SiteReportList.size());
					      
					       q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.startDate >= :param1 order by t.startDate asc");
			                q1.setParameter("param1", StartDate);
			                q1.setFirstResult(0);
			                q1.setMaxResults(1);
			                String STARTDATE = (String) q1.uniqueResult();
			                System.out.println("///*/*The start date is " +STARTDATE);
			   
			                q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.endDate <= :param1 order by t.endDate desc");
			                q2.setParameter("param1", EndDate);
			                q2.setFirstResult(0);
			                q2.setMaxResults(1);
			                String ENDDATE = (String) q2.uniqueResult();
			                System.out.println("///*/*The start date is " +ENDDATE);
			                
						      
			               if( SiteReportList.size()>0) {
			            
			            	  for(int i =0; i< SiteReportList.size(); i++)
			            	  {
			            		  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
				            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(SiteReportArray));
				            	  String Start=STARTDATE;
				            	  String End=ENDDATE;
				            	  String dateRange = "From " + Start+ " to " + End;
				            	  System.out.println("dateRange is "+dateRange);
				            	
				            	  SiteID = (String)SiteReportArray[0];
				            	  
				               	  if(SiteReportArray[1]!=null) {
						            	 SiteName=(String)SiteReportArray[1];
						            	  System.out.println("/*//The wareName is "+areaName);
						            	  }
					            	  else {
					            		  SiteName="" ;
					            	  } 
					            	  
					            	  if(SiteReportArray[2]!=null) {
						            	  areaName=(String)SiteReportArray[2];
						            	  System.out.println("/*//The areaName is "+areaName);
						            	  }
					            	  else {
					            		  areaName="" ;
					            	  } 
					            	  
					            	  if(SiteReportArray[3]!=null) {
						            	  siteOwner=(String)SiteReportArray[3];
						            	  System.out.println("/*//The owner is "+siteOwner);
						            	  }
					            	  else {
					            		  siteOwner="" ;
					            	  } 
					            	  
					            	  
					            	  if(SiteReportArray[4]!=null) {
						            	  towerType=(String)SiteReportArray[4];
						            	  System.out.println("/*//The site Id is "+towerType);
						            	  }
					            	  else {
					            		  towerType="" ;
					            	  } 
				            	  
				            	  
					            	
					            	  if(SiteReportArray[5]!=null) {
						            	  dataRev= SiteReportArray[5];
					            	  System.out.println("/*//The Data  Rev is "+dataRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            	dataRev="0";	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Data  Rev  after is "+dataRev);
						            	
						          	if(SiteReportArray[6]!=null) {
						            	  voiceRev= SiteReportArray[6];
					            	  System.out.println("/*//The Data  Rev is "+voiceRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Voice Rev  after is "+voiceRev);
						            	
						          	if(SiteReportArray[7]!=null) {
						            	  smsRev= SiteReportArray[7];
					            	  System.out.println("/*//The Data  Rev is "+smsRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsRev);
						          	  
						          	if(SiteReportArray[8]!=null) {
						            	  grossRev= SiteReportArray[8];
					            	  System.out.println("/*//The Data  Rev is "+grossRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 grossRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+grossRev);
						          	  
						          	if(SiteReportArray[9]!=null) {
						          		dataTraf= SiteReportArray[9];
					            	  System.out.println("/*//The Data  Rev is "+dataTraf);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 dataTraf=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+dataTraf);
						          	  
						          	  
						          	  
						          	if(SiteReportArray[10]!=null) {
						          		smsTrafOg= SiteReportArray[10];
					            	  System.out.println("/*//The Data  Rev is "+smsTrafOg);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsTrafOg=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsTrafOg);
						          	  
						          	if(SiteReportArray[11]!=null) {
						          		voiceTrafOg= SiteReportArray[11];
					            	  System.out.println("/*//The Data  Rev is "+voiceTrafOg);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceTrafOg=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafOg);
						          	    
						          	  
						          	  
						          	if(SiteReportArray[12]!=null) {
						          		smsTrafIc= SiteReportArray[12];
					            	  System.out.println("/*//The Data  Rev is "+smsTrafIc);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsTrafIc=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsTrafIc);
						          	  
						          	  
						          	if(SiteReportArray[13]!=null) {
						          		voiceTrafIc= SiteReportArray[13];
					            	  System.out.println("/*//The Data  Rev is "+voiceTrafIc);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceTrafIc=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafIc);
						          	  
						          	if(SiteReportArray[14]!=null) {
						          		opex= SiteReportArray[14];
					            	  System.out.println("/*//The Data  Rev is "+opex);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 opex=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+opex);
						          	  
						          	  
						          	  
						          	  
						          	if(SiteReportArray[15]!=null) {
						          		profLoss= SiteReportArray[15];
					            	  System.out.println("/*//The Data  Rev is "+profLoss);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 profLoss=0;	
						            		
						            	}
						          	
				          	  System.out.println("/*//The Sms Rev  after is "+profLoss);
				          	String[] arrayData= {"1",SiteID,SiteName,STARTDATE,ENDDATE,areaName,siteOwner,towerType,String.valueOf(dataRev),String.valueOf(voiceRev),String.valueOf(smsRev),String.valueOf(grossRev),String.valueOf(dataTraf),String.valueOf(smsTrafOg),String.valueOf(voiceTrafOg),String.valueOf(voiceTrafIc),String.valueOf(smsTrafIc),String.valueOf(opex),String.valueOf(profLoss)};
				          
				          	arrayData1 = arrayData;
				
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
				          	
			            	  }
			               }
					}
					if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g")||StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && profitable == null && loss == null && min == null && max == null){
						
						System.out.println("Technology option is checked");
						if(StringUtils.equalsIgnoreCase(technologies,"2g")){
							
							query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
									+"jointable.dataSum, jointable.voiceSum, jointable.smsSum, "
									+"jointable.grossSum, jointable.dataTrafficSum, jointable.smsogSum, jointable.voiceogSum, jointable.smsicSum, "
									+"jointable.voiceicSum, jointable.opexSum, jointable.ProfitLossSum from warehouse_profit_loss stable right join "
									+"(select site_id, sum(data) as dataSum, sum(voice_revenu) as voiceSum, "
									+"sum(sms_revenu) as smsSum, sum(gross_revenu) as grossSum, sum(data_traffic) as dataTrafficSum, "
									+"sum(total_sms_traffic_og) as smsogSum, sum(total_voice_traffic_og) as voiceogSum, sum(total_sms_traffic_ic) "
									+"as smsicSum, sum(total_voice_traffic_og) as voiceicSum, sum(total_opex_monthly) as opexSum, sum(profit_and_loss) "
									+"as ProfitLossSum from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
									+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0  group by site_id) "
									+"jointable on stable.site_id = jointable.site_id";
							
						}
						
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
							
								query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.dataSum, jointable.voiceSum, jointable.smsSum, "
										+"jointable.grossSum, jointable.dataTrafficSum, jointable.smsogSum, jointable.voiceogSum, jointable.smsicSum, "
										+"jointable.voiceicSum, jointable.opexSum, jointable.ProfitLossSum from warehouse_profit_loss stable right join "
										+"(select site_id, sum(data) as dataSum, sum(voice_revenu) as voiceSum, "
										+"sum(sms_revenu) as smsSum, sum(gross_revenu) as grossSum, sum(data_traffic) as dataTrafficSum, "
										+"sum(total_sms_traffic_og) as smsogSum, sum(total_voice_traffic_og) as voiceogSum, sum(total_sms_traffic_ic) "
										+"as smsicSum, sum(total_voice_traffic_og) as voiceicSum, sum(total_opex_monthly) as opexSum, sum(profit_and_loss) "
										+"as ProfitLossSum from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0  group by site_id) "
										+"jointable on stable.site_id = jointable.site_id";
							
						}
							
						if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
							
							query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
									+"jointable.dataSum, jointable.voiceSum, jointable.smsSum, "
									+"jointable.grossSum, jointable.dataTrafficSum, jointable.smsogSum, jointable.voiceogSum, jointable.smsicSum, "
									+"jointable.voiceicSum, jointable.opexSum, jointable.ProfitLossSum from warehouse_profit_loss stable right join "
									+"(select site_id, sum(data) as dataSum, sum(voice_revenu) as voiceSum, "
									+"sum(sms_revenu) as smsSum, sum(gross_revenu) as grossSum, sum(data_traffic) as dataTrafficSum, "
									+"sum(total_sms_traffic_og) as smsogSum, sum(total_voice_traffic_og) as voiceogSum, sum(total_sms_traffic_ic) "
									+"as smsicSum, sum(total_voice_traffic_og) as voiceicSum, sum(total_opex_monthly) as opexSum, sum(profit_and_loss) "
									+"as ProfitLossSum from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
									+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1  group by site_id) "
									+"jointable on stable.site_id = jointable.site_id";
							
						}
						
						System.out.println(query1);
				    	q=session.createSQLQuery(query1);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);
					    
						SiteReportList =  q.list();
					    System.out.println("The Query Result is: " +mapper.writeValueAsString(SiteReportList));
					    
					    System.out.println("The zise is "+SiteReportList.size());
					      
					       q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.startDate >= :param1 order by t.startDate asc");
			                q1.setParameter("param1", StartDate);
			                q1.setFirstResult(0);
			                q1.setMaxResults(1);
			                String STARTDATE = (String) q1.uniqueResult();
			                System.out.println("///*/*The start date is " +STARTDATE);
			   
			                q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.endDate <= :param1 order by t.endDate desc");
			                q2.setParameter("param1", EndDate);
			                q2.setFirstResult(0);
			                q2.setMaxResults(1);
			                String ENDDATE = (String) q2.uniqueResult();
			                System.out.println("///*/*The start date is " +ENDDATE);
			                
						      
			               if( SiteReportList.size()>0) {
			            
			            	  for(int i =0; i< SiteReportList.size(); i++)
			            	  {
			            		  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
				            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(SiteReportArray));
				            	  String Start=STARTDATE;
				            	  String End=ENDDATE;
				            	  String dateRange = "From " + Start+ " to " + End;
				            	  System.out.println("dateRange is "+dateRange);
				            	
				            	  SiteID = (String)SiteReportArray[0];
				            	  
				               	  if(SiteReportArray[1]!=null) {
						            	 SiteName=(String)SiteReportArray[1];
						            	  System.out.println("/*//The wareName is "+areaName);
						            	  }
					            	  else {
					            		  SiteName="" ;
					            	  } 
					            	  
					            	  if(SiteReportArray[2]!=null) {
						            	  areaName=(String)SiteReportArray[2];
						            	  System.out.println("/*//The areaName is "+areaName);
						            	  }
					            	  else {
					            		  areaName="" ;
					            	  } 
					            	  
					            	  if(SiteReportArray[3]!=null) {
						            	  siteOwner=(String)SiteReportArray[3];
						            	  System.out.println("/*//The owner is "+siteOwner);
						            	  }
					            	  else {
					            		  siteOwner="" ;
					            	  } 
					            	  
					            	  
					            	  if(SiteReportArray[4]!=null) {
						            	  towerType=(String)SiteReportArray[4];
						            	  System.out.println("/*//The site Id is "+towerType);
						            	  }
					            	  else {
					            		  towerType="" ;
					            	  } 
				            	  
				            	  
					            	
					            	  if(SiteReportArray[5]!=null) {
						            	  dataRev= SiteReportArray[5];
					            	  System.out.println("/*//The Data  Rev is "+dataRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            	dataRev="0";	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Data  Rev  after is "+dataRev);
						            	
						          	if(SiteReportArray[6]!=null) {
						            	  voiceRev= SiteReportArray[6];
					            	  System.out.println("/*//The Data  Rev is "+voiceRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Voice Rev  after is "+voiceRev);
						            	
						          	if(SiteReportArray[7]!=null) {
						            	  smsRev= SiteReportArray[7];
					            	  System.out.println("/*//The Data  Rev is "+smsRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsRev);
						          	  
						          	if(SiteReportArray[8]!=null) {
						            	  grossRev= SiteReportArray[8];
					            	  System.out.println("/*//The Data  Rev is "+grossRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 grossRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+grossRev);
						          	  
						          	if(SiteReportArray[9]!=null) {
						          		dataTraf= SiteReportArray[9];
					            	  System.out.println("/*//The Data  Rev is "+dataTraf);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 dataTraf=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+dataTraf);
						          	  
						          	  
						          	  
						          	if(SiteReportArray[10]!=null) {
						          		smsTrafOg= SiteReportArray[10];
					            	  System.out.println("/*//The Data  Rev is "+smsTrafOg);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsTrafOg=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsTrafOg);
						          	  
						          	if(SiteReportArray[11]!=null) {
						          		voiceTrafOg= SiteReportArray[11];
					            	  System.out.println("/*//The Data  Rev is "+voiceTrafOg);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceTrafOg=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafOg);
						          	    
						          	  
						          	  
						          	if(SiteReportArray[12]!=null) {
						          		smsTrafIc= SiteReportArray[12];
					            	  System.out.println("/*//The Data  Rev is "+smsTrafIc);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsTrafIc=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsTrafIc);
						          	  
						          	  
						          	if(SiteReportArray[13]!=null) {
						          		voiceTrafIc= SiteReportArray[13];
					            	  System.out.println("/*//The Data  Rev is "+voiceTrafIc);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceTrafIc=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafIc);
						          	  
						          	if(SiteReportArray[14]!=null) {
						          		opex= SiteReportArray[14];
					            	  System.out.println("/*//The Data  Rev is "+opex);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 opex=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+opex);
						          	  
						          	  
						          	  
						          	  
						          	if(SiteReportArray[15]!=null) {
						          		profLoss= SiteReportArray[15];
					            	  System.out.println("/*//The Data  Rev is "+profLoss);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 profLoss=0;	
						            		
						            	}
						          	
				          	  System.out.println("/*//The Sms Rev  after is "+profLoss);
				          	String[] arrayData= {"1",SiteID,SiteName,STARTDATE,ENDDATE,areaName,siteOwner,towerType,String.valueOf(dataRev),String.valueOf(voiceRev),String.valueOf(smsRev),String.valueOf(grossRev),String.valueOf(dataTraf),String.valueOf(smsTrafOg),String.valueOf(voiceTrafOg),String.valueOf(voiceTrafIc),String.valueOf(smsTrafIc),String.valueOf(opex),String.valueOf(profLoss)};
				          
				          	arrayData1 = arrayData;
				
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
				          	
			            	  }
			               }
					}
					if(StringUtils.equalsIgnoreCase(profitable,"Profitable")) {
						
						if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
						{
							System.out.println("Max, Min, Technologies are Unchecked!!");
							
							query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
									+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
									+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
									+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
									+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
									+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
									+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 group by site_id) "
									+"jointable on stable.site_id = jointable.site_id where profitAndLoss >=0";
					    	
					    	
							System.out.println(query1);
					    	q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null")){
							
							query1 = "select stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
									+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
									+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
									+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
									+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
									+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
									+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 group by site_id order by profitAndLoss DESC) "
									+"jointable on stable.site_id = jointable.site_id where profitAndLoss >=0 and rownum = 1";
							
							System.out.println(query1);
					    	q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
							
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null")){
							query1 = "select stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
									+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
									+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
									+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
									+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
									+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
									+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 group by site_id order by profitAndLoss ASC) "
									+"jointable on stable.site_id = jointable.site_id where profitAndLoss >=0 and rownum = 1";
							
							System.out.println(query1);
					    	q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						
						if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && min == null && max == null){
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								
								query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type,  "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
										+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
										+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
										+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
										+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
										+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id where profitAndLoss >=0";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							    
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								System.out.println("asdas32432");
								query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
										+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
										+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
										+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
										+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
										+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id where profitAndLoss >=0";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								System.out.println("asdas");
								query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
										+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
										+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
										+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
										+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
										+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id where profitAndLoss >=0";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
						}
						if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null")) {
							
							query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
									+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
									+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
									+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
									+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
									+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
									+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 group by site_id order by profitAndLoss DESC) "
									+"where profitAndLoss >=0 and rownum = 1) UNION (select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
									+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, "
									+"SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss "
									+"where start_date >= :param1 and end_date <= :param2 group by site_id order by profitAndLoss ASC) where profitAndLoss >=0 and rownum = 1))  jointable on stable.site_id = jointable.site_id";
							
							System.out.println(query1);
					    	q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by profitAndLoss DESC) "
										+"where profitAndLoss >=0 and rownum = 1)) jointable on stable.site_id = jointable.site_id";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0 group by site_id order by profitAndLoss DESC) "
										+"where profitAndLoss >=0 and rownum = 1)) jointable on stable.site_id = jointable.site_id";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id order by profitAndLoss DESC) "
										+"where profitAndLoss >=0 and rownum = 1)) jointable on stable.site_id = jointable.site_id";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by profitAndLoss ASC) "
										+"where profitAndLoss >=0 and rownum = 1)) jointable on stable.site_id = jointable.site_id"; 
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0 group by site_id order by profitAndLoss ASC) "
										+"where profitAndLoss >=0 and rownum = 1)) jointable on stable.site_id = jointable.site_id";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id order by profitAndLoss ASC) "
										+"where profitAndLoss >=0 and rownum = 1)) jointable on stable.site_id = jointable.site_id";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by profitAndLoss DESC) "
										+"where profitAndLoss >=0 and rownum = 1) UNION (select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, "
										+"SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss "
										+"where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by profitAndLoss ASC) where profitAndLoss >=0 and rownum = 1))  jointable on stable.site_id = jointable.site_id";
								
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0 group by site_id order by profitAndLoss DESC) "
										+"where profitAndLoss >=0 and rownum = 1) UNION (select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, "
										+"SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss "
										+"where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0 group by site_id order by profitAndLoss ASC) where profitAndLoss >=0 and rownum = 1))  jointable on stable.site_id = jointable.site_id";
								
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id order by profitAndLoss DESC) "
										+"where profitAndLoss >=0 and rownum = 1) UNION (select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, "
										+"SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss "
										+"where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id order by profitAndLoss ASC) where profitAndLoss >=0 and rownum = 1))  jointable on stable.site_id = jointable.site_id";
								
							}
							
							
							System.out.println(query1);
					    	q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						
						
					    System.out.println("The Query Result is: " +mapper.writeValueAsString(SiteReportList));
					    
					    System.out.println("The zise is "+SiteReportList.size());
					      
					       q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.startDate >= :param1 order by t.startDate asc");
			                q1.setParameter("param1", StartDate);
			                q1.setFirstResult(0);
			                q1.setMaxResults(1);
			                String STARTDATE = (String) q1.uniqueResult();
			                System.out.println("///*/*The start date is " +STARTDATE);
			   
			                q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.endDate <= :param1 order by t.endDate desc");
			                q2.setParameter("param1", EndDate);
			                q2.setFirstResult(0);
			                q2.setMaxResults(1);
			                String ENDDATE = (String) q2.uniqueResult();
			                System.out.println("///*/*The start date is " +ENDDATE);
			                
						      
			               if( SiteReportList.size()>0) {
			            
			            	  for(int i =0; i< SiteReportList.size(); i++)
			            	  {
			            		  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
				            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(SiteReportArray));
				            	  String Start=STARTDATE;
				            	  String End=ENDDATE;
				            	  String dateRange = "From " + Start+ " to " + End;
				            	  System.out.println("dateRange is "+dateRange);
				            	
				            	  SiteID = (String)SiteReportArray[0];
				            	  
				               	  if(SiteReportArray[1]!=null) {
						            	 SiteName=(String)SiteReportArray[1];
						            	  System.out.println("/*//The wareName is "+areaName);
						            	  }
					            	  else {
					            		  SiteName="" ;
					            	  } 
					            	  
					            	  if(SiteReportArray[2]!=null) {
						            	  areaName=(String)SiteReportArray[2];
						            	  System.out.println("/*//The areaName is "+areaName);
						            	  }
					            	  else {
					            		  areaName="" ;
					            	  } 
					            	  
					            	  if(SiteReportArray[3]!=null) {
						            	  siteOwner=(String)SiteReportArray[3];
						            	  System.out.println("/*//The owner is "+siteOwner);
						            	  }
					            	  else {
					            		  siteOwner="" ;
					            	  } 
					            	  
					            	  
					            	  if(SiteReportArray[4]!=null) {
						            	  towerType=(String)SiteReportArray[4];
						            	  System.out.println("/*//The site Id is "+towerType);
						            	  }
					            	  else {
					            		  towerType="" ;
					            	  } 
				            	  
				            	  
					            	
					            	  if(SiteReportArray[5]!=null) {
						            	  dataRev= SiteReportArray[5];
					            	  System.out.println("/*//The Data  Rev is "+dataRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            	dataRev="0";	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Data  Rev  after is "+dataRev);
						            	
						          	if(SiteReportArray[6]!=null) {
						            	  voiceRev= SiteReportArray[6];
					            	  System.out.println("/*//The Data  Rev is "+voiceRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Voice Rev  after is "+voiceRev);
						            	
						          	if(SiteReportArray[7]!=null) {
						            	  smsRev= SiteReportArray[7];
					            	  System.out.println("/*//The Data  Rev is "+smsRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsRev);
						          	  
						          	if(SiteReportArray[8]!=null) {
						            	  grossRev= SiteReportArray[8];
					            	  System.out.println("/*//The Data  Rev is "+grossRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 grossRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+grossRev);
						          	  
						          	if(SiteReportArray[9]!=null) {
						          		dataTraf= SiteReportArray[9];
					            	  System.out.println("/*//The Data  Rev is "+dataTraf);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 dataTraf=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+dataTraf);
						          	  
						          	  
						          	  
						          	if(SiteReportArray[10]!=null) {
						          		smsTrafOg= SiteReportArray[10];
					            	  System.out.println("/*//The Data  Rev is "+smsTrafOg);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsTrafOg=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsTrafOg);
						          	  
						          	if(SiteReportArray[11]!=null) {
						          		voiceTrafOg= SiteReportArray[11];
					            	  System.out.println("/*//The Data  Rev is "+voiceTrafOg);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceTrafOg=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafOg);
						          	    
						          	  
						          	  
						          	if(SiteReportArray[12]!=null) {
						          		smsTrafIc= SiteReportArray[12];
					            	  System.out.println("/*//The Data  Rev is "+smsTrafIc);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsTrafIc=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsTrafIc);
						          	  
						          	  
						          	if(SiteReportArray[13]!=null) {
						          		voiceTrafIc= SiteReportArray[13];
					            	  System.out.println("/*//The Data  Rev is "+voiceTrafIc);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceTrafIc=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafIc);
						          	  
						          	if(SiteReportArray[14]!=null) {
						          		opex= SiteReportArray[14];
					            	  System.out.println("/*//The Data  Rev is "+opex);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 opex=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+opex);
						          	  
						          	  
						          	  
						          	  
						          	if(SiteReportArray[15]!=null) {
						          		profLoss= SiteReportArray[15];
					            	  System.out.println("/*//The Data  Rev is "+profLoss);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 profLoss=0;	
						            		
						            	}
						          	
				          	  System.out.println("/*//The Sms Rev  after is "+profLoss);
				          	String[] arrayData= {"1",SiteID,SiteName,STARTDATE,ENDDATE,areaName,siteOwner,towerType,String.valueOf(dataRev),String.valueOf(voiceRev),String.valueOf(smsRev),String.valueOf(grossRev),String.valueOf(dataTraf),String.valueOf(smsTrafOg),String.valueOf(voiceTrafOg),String.valueOf(voiceTrafIc),String.valueOf(smsTrafIc),String.valueOf(opex),String.valueOf(profLoss)};
				          
				          	arrayData1 = arrayData;
				
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
				          	
			            	  }
			               }
						
					}
					
					if(StringUtils.equalsIgnoreCase(loss,"Loss")) {
						
						System.out.println("Loss Case !");
						if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
						{
							System.out.println("Max, Min, Technologies are Unchecked!!");
							
							query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
									+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
									+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
									+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
									+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
									+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
									+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 group by site_id) "
									+"jointable on stable.site_id = jointable.site_id where profitAndLoss <=0";
					    	
					    	
							System.out.println(query1);
					    	q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null")){
							
							query1 = "select stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
									+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
									+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
									+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
									+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
									+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
									+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 group by site_id order by profitAndLoss DESC) "
									+"jointable on stable.site_id = jointable.site_id where profitAndLoss <=0 and rownum = 1";
							
							System.out.println(query1);
					    	q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
							
						}
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null")){
							query1 = "select stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
									+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
									+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
									+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
									+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
									+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
									+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 group by site_id order by profitAndLoss ASC) "
									+"jointable on stable.site_id = jointable.site_id where profitAndLoss <=0 and rownum = 1";
							
							System.out.println(query1);
					    	q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						
						if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && min == null && max == null){
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								
								query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
										+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
										+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
										+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
										+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
										+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id where profitAndLoss <=0";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							    
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								System.out.println("asdas32432");
								query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type,"
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
										+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
										+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
										+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
										+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
										+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id where profitAndLoss <=0";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								System.out.println("asdas");
								query1 = "select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type,"
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, "
										+"jointable.totalSmsTraOG, jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, "
										+"jointable.profitAndLoss from warehouse_profit_loss stable right join (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, "
										+"SUM(sms_revenu) as smsRevenu, SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) "
										+"as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, "
										+"SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id) "
										+"jointable on stable.site_id = jointable.site_id where profitAndLoss <=0";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
						}
						if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null")) {
							
							query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
									+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
									+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
									+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
									+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
									+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
									+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 group by site_id order by profitAndLoss DESC) "
									+"where profitAndLoss <=0 and rownum = 1) UNION (select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
									+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, "
									+"SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss "
									+"where start_date >= :param1 and end_date <= :param2 group by site_id order by profitAndLoss ASC) where profitAndLoss <=0 and rownum = 1))  jointable on stable.site_id = jointable.site_id";
							
							System.out.println(query1);
					    	q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by profitAndLoss DESC) "
										+"where profitAndLoss <=0 and rownum = 1)) jointable on stable.site_id = jointable.site_id";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0 group by site_id order by profitAndLoss DESC) "
										+"where profitAndLoss <=0 and rownum = 1)) jointable on stable.site_id = jointable.site_id";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id order by profitAndLoss DESC) "
										+"where profitAndLoss <=0 and rownum = 1)) jointable on stable.site_id = jointable.site_id";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by profitAndLoss ASC) "
										+"where profitAndLoss <=0 and rownum = 1)) jointable on stable.site_id = jointable.site_id";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0 group by site_id order by profitAndLoss ASC) "
										+"where profitAndLoss <=0 and rownum = 1)) jointable on stable.site_id = jointable.site_id";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id order by profitAndLoss ASC) "
										+"where profitAndLoss <=0 and rownum = 1)) jointable on stable.site_id = jointable.site_id";
								
								System.out.println(query1);
						    	q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by profitAndLoss DESC) "
										+"where profitAndLoss <=0 and rownum = 1) UNION (select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, "
										+"SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss "
										+"where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by profitAndLoss ASC) where profitAndLoss <=0 and rownum = 1))  jointable on stable.site_id = jointable.site_id";
								
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0 group by site_id order by profitAndLoss DESC) "
										+"where profitAndLoss <=0 and rownum = 1) UNION (select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, "
										+"SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss "
										+"where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0 group by site_id order by profitAndLoss ASC) where profitAndLoss <=0 and rownum = 1))  jointable on stable.site_id = jointable.site_id";
								
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								query1 ="select distinct stable.site_id, stable.ware_name, stable.area_name, stable.site_owner, stable.tower_type, "
										+"jointable.data, jointable.voice, jointable.smsRevenu, jointable.grossRevenu, jointable.datatraffic, jointable.totalSmsTraOG, "
										+"jointable.totalVoiceTraOG, jointable.totalSmsTraIC, jointable.totalVoiceTraIC, jointable.totalOpexMon, jointable.profitAndLoss "
										+"from warehouse_profit_loss stable right join ((select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) "
										+"as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) "
										+"as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id order by profitAndLoss DESC) "
										+"where profitAndLoss <=0 and rownum = 1) UNION (select * from (select site_id, SUM(data) as data, SUM(voice_revenu) as voice, SUM(sms_revenu) as smsRevenu, "
										+"SUM(gross_revenu) as grossRevenu, SUM(data_traffic) as datatraffic, SUM(total_sms_traffic_og) as totalSmsTraOG, SUM(total_voice_traffic_og) as totalVoiceTraOG, SUM(total_sms_traffic_ic) as totalSmsTraIC, "
										+"SUM(total_voice_traffic_ic) as totalVoiceTraIC, SUM(total_opex_monthly) as totalOpexMon, SUM(profit_and_loss) as profitAndLoss from warehouse_profit_loss "
										+"where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id order by profitAndLoss ASC) where profitAndLoss <=0 and rownum = 1))  jointable on stable.site_id = jointable.site_id";
								
							}
							
							
							System.out.println(query1);
					    	q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						
						System.out.println("The Query Result is: " +mapper.writeValueAsString(SiteReportList));
					    
					    System.out.println("The zise is "+SiteReportList.size());
					      
					       q1 = session.createQuery("select TO_CHAR(t.startDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.startDate >= :param1 order by t.startDate asc");
			                q1.setParameter("param1", StartDate);
			                q1.setFirstResult(0);
			                q1.setMaxResults(1);
			                String STARTDATE = (String) q1.uniqueResult();
			                System.out.println("///*/*The start date is " +STARTDATE);
			   
			                q2 = session.createQuery("select TO_CHAR(t.endDate, 'YYYY-MM-DD HH24:MI:SS') from WarehouseProfitloss t where t.endDate <= :param1 order by t.endDate desc");
			                q2.setParameter("param1", EndDate);
			                q2.setFirstResult(0);
			                q2.setMaxResults(1);
			                String ENDDATE = (String) q2.uniqueResult();
			                System.out.println("///*/*The start date is " +ENDDATE);
			                
						      
			               if( SiteReportList.size()>0) {
			            
			            	  for(int i =0; i< SiteReportList.size(); i++)
			            	  {
			            		  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
				            	  System.out.println("The SiteReportArray is "+mapper.writeValueAsString(SiteReportArray));
				            	  String Start=STARTDATE;
				            	  String End=ENDDATE;
				            	  String dateRange = "From " + Start+ " to " + End;
				            	  System.out.println("dateRange is "+dateRange);
				            	
				            	  SiteID = (String)SiteReportArray[0];
				            	  
				               	  if(SiteReportArray[1]!=null) {
						            	 SiteName=(String)SiteReportArray[1];
						            	  System.out.println("/*//The wareName is "+areaName);
						            	  }
					            	  else {
					            		  SiteName="" ;
					            	  } 
					            	  
					            	  if(SiteReportArray[2]!=null) {
						            	  areaName=(String)SiteReportArray[2];
						            	  System.out.println("/*//The areaName is "+areaName);
						            	  }
					            	  else {
					            		  areaName="" ;
					            	  } 
					            	  
					            	  if(SiteReportArray[3]!=null) {
						            	  siteOwner=(String)SiteReportArray[3];
						            	  System.out.println("/*//The owner is "+siteOwner);
						            	  }
					            	  else {
					            		  siteOwner="" ;
					            	  } 
					            	  
					            	  
					            	  if(SiteReportArray[5]!=null) {
						            	  dataRev= SiteReportArray[5];
					            	  System.out.println("/*//The Data  Rev is "+dataRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            	dataRev="0";	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Data  Rev  after is "+dataRev);
						            	
						          	if(SiteReportArray[6]!=null) {
						            	  voiceRev= SiteReportArray[6];
					            	  System.out.println("/*//The Data  Rev is "+voiceRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Voice Rev  after is "+voiceRev);
						            	
						          	if(SiteReportArray[7]!=null) {
						            	  smsRev= SiteReportArray[7];
					            	  System.out.println("/*//The Data  Rev is "+smsRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsRev);
						          	  
						          	if(SiteReportArray[8]!=null) {
						            	  grossRev= SiteReportArray[8];
					            	  System.out.println("/*//The Data  Rev is "+grossRev);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 grossRev=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+grossRev);
						          	  
						          	if(SiteReportArray[9]!=null) {
						          		dataTraf= SiteReportArray[9];
					            	  System.out.println("/*//The Data  Rev is "+dataTraf);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 dataTraf=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+dataTraf);
						          	  
						          	  
						          	  
						          	if(SiteReportArray[10]!=null) {
						          		smsTrafOg= SiteReportArray[10];
					            	  System.out.println("/*//The Data  Rev is "+smsTrafOg);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsTrafOg=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsTrafOg);
						          	  
						          	if(SiteReportArray[11]!=null) {
						          		voiceTrafOg= SiteReportArray[11];
					            	  System.out.println("/*//The Data  Rev is "+voiceTrafOg);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceTrafOg=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafOg);
						          	    
						          	  
						          	  
						          	if(SiteReportArray[12]!=null) {
						          		smsTrafIc= SiteReportArray[12];
					            	  System.out.println("/*//The Data  Rev is "+smsTrafIc);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 smsTrafIc=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+smsTrafIc);
						          	  
						          	  
						          	if(SiteReportArray[13]!=null) {
						          		voiceTrafIc= SiteReportArray[13];
					            	  System.out.println("/*//The Data  Rev is "+voiceTrafIc);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 voiceTrafIc=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+voiceTrafIc);
						          	  
						          	if(SiteReportArray[14]!=null) {
						          		opex= SiteReportArray[14];
					            	  System.out.println("/*//The Data  Rev is "+opex);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 opex=0;	
						            		
						            	}
						            	
						          	  System.out.println("/*//The Sms Rev  after is "+opex);
						          	  
						          	  
						          	  
						          	  
						          	if(SiteReportArray[15]!=null) {
						          		profLoss= SiteReportArray[15];
					            	  System.out.println("/*//The Data  Rev is "+profLoss);}
						            	
						            	
						            	else {
						            		 System.out.println("Passes here");
						            		
						            		 profLoss=0;	
						            		
						            	}
						          	
				          	  System.out.println("/*//The Sms Rev  after is "+profLoss);
				          	String[] arrayData= {"1",SiteID,SiteName,STARTDATE,ENDDATE,areaName,siteOwner,towerType,String.valueOf(dataRev),String.valueOf(voiceRev),String.valueOf(smsRev),String.valueOf(grossRev),String.valueOf(dataTraf),String.valueOf(smsTrafOg),String.valueOf(voiceTrafOg),String.valueOf(voiceTrafIc),String.valueOf(smsTrafIc),String.valueOf(opex),String.valueOf(profLoss)};
				          
				          	arrayData1 = arrayData;
				
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
				          	
			            	  }
			               }
						
					}
					
					System.out.println("Data after is: "+mapper.writeValueAsString(Data)); 
				 }
				
				
				//monthly2
				else if(monthly==1)	{
					
					System.out.println("Start Condition Monthly");
					
					if(profitable == null && loss == null && max == null && min == null && (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")){
						
						System.out.println("Only Monthly is checked !!!");
						query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),  "
								+ "data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, "
								+"total_sms_traffic_ic, total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss "
								+"where start_date >= :param1 and end_date <= :param2 order by start_date DESC";
						q=session.createSQLQuery(query1);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);
					    SiteReportList =  q.list();
					    
					    System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
					    System.out.println("The zise is "+SiteReportList.size());
					    
					    if(SiteReportList.size()>0) {
					    	
					    	for (int i = 0; i < SiteReportList.size(); i++) {
					    	
			            	  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  
			            	  if(SiteReportArray[0]!=null) {
			            	  SiteID=(String)SiteReportArray[0];
			            	  
			            	  }
			            	  
			            	  else {
			            		  SiteID="" ;
			            	  }
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	 
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
				            	  
			            	  if(SiteReportArray[5]!=null) {
				            	  startDate=(String)SiteReportArray[5];
				            	  }
			            	  else {
			            		  startDate="" ;
			            	  }
			            	  
			            	  
			            	  if(SiteReportArray[6]!=null) {
				            	  endDate=SiteReportArray[6];
				            	  
				            	  }
			            	  else {
			            		  endDate="" ;
			            	  }
			            	
			            	  if(SiteReportArray[7]!=null) {
				            	  dataRev1= SiteReportArray[7];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  voiceRev1=  SiteReportArray[8];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[9]!=null) {
				            	  smsRev1= SiteReportArray[9];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[10]!=null) {
				            	  grossRev1= SiteReportArray[10];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[11]!=null) {
				          		dataTraf1= SiteReportArray[11];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafOg1= SiteReportArray[12];
				          		
				          	}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafOg1= SiteReportArray[13];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		smsTrafIc1= SiteReportArray[14];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		voiceTrafIc1= SiteReportArray[15];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[16]!=null) {
				          		opex1= SiteReportArray[16];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[17]!=null) {
				          		profLoss1= SiteReportArray[17];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss1="0";	
				            		
				            	}
				            	
				          	  
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};
					          
				          	arrayData1 = arrayData;
				          	
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
					    	}  
					    } 
					}
					
					if(StringUtils.equalsIgnoreCase(max,"Max") && profitable == null && loss == null)
					{
						if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
				 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							
							q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
								q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
								q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
								q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
						}
					    
						System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
					    System.out.println("The zise is "+SiteReportList.size());
					    
					    if(SiteReportList.size()>0) {
					    	
					    	for (int i = 0; i < SiteReportList.size(); i++) {
					    	
			            	  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  
			            	  if(SiteReportArray[0]!=null) {
			            	  SiteID=(String)SiteReportArray[0];
			            	  
			            	  }
			            	  
			            	  else {
			            		  SiteID="" ;
			            	  }
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	 
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
				            	  
			            	  if(SiteReportArray[5]!=null) {
				            	  startDate=(String)SiteReportArray[5];
				            	  }
			            	  else {
			            		  startDate="" ;
			            	  }
			            	  
			            	  
			            	  if(SiteReportArray[6]!=null) {
				            	  endDate=SiteReportArray[6];
				            	  
				            	  }
			            	  else {
			            		  endDate="" ;
			            	  }
			            	
			            	  if(SiteReportArray[7]!=null) {
				            	  dataRev1= SiteReportArray[7];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  voiceRev1=  SiteReportArray[8];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[9]!=null) {
				            	  smsRev1= SiteReportArray[9];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[10]!=null) {
				            	  grossRev1= SiteReportArray[10];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[11]!=null) {
				          		dataTraf1= SiteReportArray[11];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafOg1= SiteReportArray[12];
				          		
				          	}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafOg1= SiteReportArray[13];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		smsTrafIc1= SiteReportArray[14];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		voiceTrafIc1= SiteReportArray[15];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[16]!=null) {
				          		opex1= SiteReportArray[16];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[17]!=null) {
				          		profLoss1= SiteReportArray[17];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss1="0";	
				            		
				            	}
				            	
				          	  
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};
					          
				          	arrayData1 = arrayData;
				          	
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
					    	}  
					    } 
					}
					
					if(StringUtils.equalsIgnoreCase(min,"Min") && profitable == null && loss == null)
					{
						if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
				 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							
							q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
								q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
								q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
								q=session.createSQLQuery(query1);
								q.setParameter("param1", StartDate);
							    q.setParameter("param2", EndDate);
							    SiteReportList =  q.list();
							}
						}
					    
						System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
					    System.out.println("The zise is "+SiteReportList.size());
					    
					    if(SiteReportList.size()>0) {
					    	
					    	for (int i = 0; i < SiteReportList.size(); i++) {
					    	
			            	  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  
			            	  if(SiteReportArray[0]!=null) {
			            	  SiteID=(String)SiteReportArray[0];
			            	  
			            	  }
			            	  
			            	  else {
			            		  SiteID="" ;
			            	  }
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	 
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
				            	  
			            	  if(SiteReportArray[5]!=null) {
				            	  startDate=(String)SiteReportArray[5];
				            	  }
			            	  else {
			            		  startDate="" ;
			            	  }
			            	  
			            	  
			            	  if(SiteReportArray[6]!=null) {
				            	  endDate=SiteReportArray[6];
				            	  
				            	  }
			            	  else {
			            		  endDate="" ;
			            	  }
			            	
			            	  if(SiteReportArray[7]!=null) {
				            	  dataRev1= SiteReportArray[7];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  voiceRev1=  SiteReportArray[8];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[9]!=null) {
				            	  smsRev1= SiteReportArray[9];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[10]!=null) {
				            	  grossRev1= SiteReportArray[10];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[11]!=null) {
				          		dataTraf1= SiteReportArray[11];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafOg1= SiteReportArray[12];
				          		
				          	}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafOg1= SiteReportArray[13];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		smsTrafIc1= SiteReportArray[14];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		voiceTrafIc1= SiteReportArray[15];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[16]!=null) {
				          		opex1= SiteReportArray[16];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[17]!=null) {
				          		profLoss1= SiteReportArray[17];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss1="0";	
				            		
				            	}
				            	
				          	  
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};
					          
				          	arrayData1 = arrayData;
				          	
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
					    	}  
					    } 
					}
					
					if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g")||StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && profitable == null && loss == null && min == null && max == null)
					{
						if(StringUtils.equalsIgnoreCase(technologies,"2g"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 order by start_date DESC";
							
							q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 order by start_date DESC";
							
							q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 order by start_date DESC";
							
							q=session.createSQLQuery(query1);
							q.setParameter("param1", StartDate);
						    q.setParameter("param2", EndDate);
						    SiteReportList =  q.list();
						}
						System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
					    System.out.println("The zise is "+SiteReportList.size());
					    
					    if(SiteReportList.size()>0) {
					    	
					    	for (int i = 0; i < SiteReportList.size(); i++) {
					    	
			            	  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  
			            	  if(SiteReportArray[0]!=null) {
			            	  SiteID=(String)SiteReportArray[0];
			            	  
			            	  }
			            	  
			            	  else {
			            		  SiteID="" ;
			            	  }
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	 
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
				            	  
			            	  if(SiteReportArray[5]!=null) {
				            	  startDate=(String)SiteReportArray[5];
				            	  }
			            	  else {
			            		  startDate="" ;
			            	  }
			            	  
			            	  
			            	  if(SiteReportArray[6]!=null) {
				            	  endDate=SiteReportArray[6];
				            	  
				            	  }
			            	  else {
			            		  endDate="" ;
			            	  }
			            	
			            	  if(SiteReportArray[7]!=null) {
				            	  dataRev1= SiteReportArray[7];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  voiceRev1=  SiteReportArray[8];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[9]!=null) {
				            	  smsRev1= SiteReportArray[9];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[10]!=null) {
				            	  grossRev1= SiteReportArray[10];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[11]!=null) {
				          		dataTraf1= SiteReportArray[11];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafOg1= SiteReportArray[12];
				          		
				          	}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafOg1= SiteReportArray[13];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		smsTrafIc1= SiteReportArray[14];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		voiceTrafIc1= SiteReportArray[15];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[16]!=null) {
				          		opex1= SiteReportArray[16];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[17]!=null) {
				          		profLoss1= SiteReportArray[17];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss1="0";	
				            		
				            	}
				            	
				          	  
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};
					          
				          	arrayData1 = arrayData;
				          	
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
					    	}  
					    } 
					}
					
					if(StringUtils.equalsIgnoreCase(profitable,"Profitable"))
					{
						
						if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),  "
									+ "data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, "
									+"total_sms_traffic_ic, total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss "
									+"where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 order by start_date DESC";
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
				 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
				 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
						}
						
						if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && min == null && max == null)
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 order by start_date DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 order by start_date DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 order by start_date DESC";
							}
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
									+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
									+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
									+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 group by TO_CHAR(start_date, 'YYYY-MM')))"
									+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
									+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
									+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
									+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
						}
						if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))"
										+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))"
										+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')))"
										+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
							}
						}
					    
				    	q=session.createSQLQuery(query1);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);
					    SiteReportList =  q.list();
					    
					    System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
					    System.out.println("The zise is "+SiteReportList.size());
					    
					    if(SiteReportList.size()>0) {
					    	
					    	for (int i = 0; i < SiteReportList.size(); i++) {
					    	
			            	  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  
			            	  if(SiteReportArray[0]!=null) {
			            	  SiteID=(String)SiteReportArray[0];
			            	  
			            	  }
			            	  
			            	  else {
			            		  SiteID="" ;
			            	  }
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	 
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
				            	  
			            	  if(SiteReportArray[5]!=null) {
				            	  startDate=(String)SiteReportArray[5];
				            	  }
			            	  else {
			            		  startDate="" ;
			            	  }
			            	  
			            	  
			            	  if(SiteReportArray[6]!=null) {
				            	  endDate=SiteReportArray[6];
				            	  
				            	  }
			            	  else {
			            		  endDate="" ;
			            	  }
			            	
			            	  if(SiteReportArray[7]!=null) {
				            	  dataRev1= SiteReportArray[7];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  voiceRev1=  SiteReportArray[8];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[9]!=null) {
				            	  smsRev1= SiteReportArray[9];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[10]!=null) {
				            	  grossRev1= SiteReportArray[10];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[11]!=null) {
				          		dataTraf1= SiteReportArray[11];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafOg1= SiteReportArray[12];
				          		
				          	}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafOg1= SiteReportArray[13];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		smsTrafIc1= SiteReportArray[14];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		voiceTrafIc1= SiteReportArray[15];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[16]!=null) {
				          		opex1= SiteReportArray[16];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[17]!=null) {
				          		profLoss1= SiteReportArray[17];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss1="0";	
				            		
				            	}
				            	
				          	  
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};
					          
				          	arrayData1 = arrayData;
				          	
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
					    	}  
					    } 
					}
					
					if(StringUtils.equalsIgnoreCase(loss,"Loss"))
					{
						
						if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS'),  "
									+ "data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, "
									+"total_sms_traffic_ic, total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss "
									+"where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 order by start_date DESC";
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
				 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
				 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
				 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
						}
						
						if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && min == null && max == null)
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 order by start_date DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 order by start_date DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 order by start_date DESC";
							}
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
									+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
									+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
									+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 group by TO_CHAR(start_date, 'YYYY-MM')))"
									+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
									+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
									+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
									+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
						}
						
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
					 					+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
					 					+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')) order by start_date DESC";
							}
						}
						if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))"
										+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))"
										+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query1 = "select * from ((select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, " 
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, " 
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select min(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')))"
										+"UNION (select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS') as start_date, "
										+"TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic, "
										+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN ("
										+"select max(profit_and_loss) from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1 group by TO_CHAR(start_date, 'YYYY-MM')))) order by start_date DESC, profit_and_loss DESC";
							}
						}
					    
				    	q=session.createSQLQuery(query1);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);
					    SiteReportList =  q.list();
					    
					    System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
					    System.out.println("The zise is "+SiteReportList.size());
					    
					    if(SiteReportList.size()>0) {
					    	
					    	for (int i = 0; i < SiteReportList.size(); i++) {
					    	
			            	  SiteReportArray = (Object[])  SiteReportList.toArray()[i];
			            	  
			            	  if(SiteReportArray[0]!=null) {
			            	  SiteID=(String)SiteReportArray[0];
			            	  
			            	  }
			            	  
			            	  else {
			            		  SiteID="" ;
			            	  }
			            	  
			               	  if(SiteReportArray[1]!=null) {
					            	 SiteName=(String)SiteReportArray[1];
					            	 
					            	  }
				            	  else {
				            		  SiteName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[2]!=null) {
					            	  areaName=(String)SiteReportArray[2];
					            	  }
				            	  else {
				            		  areaName="" ;
				            	  } 
				            	  
				            	  if(SiteReportArray[3]!=null) {
					            	  siteOwner=(String)SiteReportArray[3];
					            	  }
				            	  else {
				            		  siteOwner="" ;
				            	  } 
				            	  
				            	  
				            	  if(SiteReportArray[4]!=null) {
					            	  towerType=(String)SiteReportArray[4];
					            	  }
				            	  else {
				            		  towerType="" ;
				            	  } 
				            	  
			            	  if(SiteReportArray[5]!=null) {
				            	  startDate=(String)SiteReportArray[5];
				            	  }
			            	  else {
			            		  startDate="" ;
			            	  }
			            	  
			            	  
			            	  if(SiteReportArray[6]!=null) {
				            	  endDate=SiteReportArray[6];
				            	  
				            	  }
			            	  else {
			            		  endDate="" ;
			            	  }
			            	
			            	  if(SiteReportArray[7]!=null) {
				            	  dataRev1= SiteReportArray[7];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            	dataRev1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[8]!=null) {
				            	  voiceRev1=  SiteReportArray[8];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[9]!=null) {
				            	  smsRev1= SiteReportArray[9];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[10]!=null) {
				            	  grossRev1= SiteReportArray[10];
				            	  }
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 grossRev1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[11]!=null) {
				          		dataTraf1= SiteReportArray[11];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 dataTraf1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[12]!=null) {
				          		smsTrafOg1= SiteReportArray[12];
				          		
				          	}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafOg1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[13]!=null) {
				          		voiceTrafOg1= SiteReportArray[13];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafOg1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[14]!=null) {
				          		smsTrafIc1= SiteReportArray[14];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 smsTrafIc1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[15]!=null) {
				          		voiceTrafIc1= SiteReportArray[15];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 voiceTrafIc1="0";	
				            		
				            	}
				            	
				          	if(SiteReportArray[16]!=null) {
				          		opex1= SiteReportArray[16];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 opex1="0";	
				            		
				            	}
				            	
				          	  
				          	if(SiteReportArray[17]!=null) {
				          		profLoss1= SiteReportArray[17];
				          		}
				            	
				            	
				            	else {
				            		 System.out.println("Passes here");
				            		
				            		 profLoss1="0";	
				            		
				            	}
				            	
				          	  
				          	  
				          	String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};
					          
				          	arrayData1 = arrayData;
				          	
				          	System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
				          	Data.add(arrayData1);
					    	}  
					    } 
					}
					
					
				}  /// end  of monthly option(site null case)
			
				


/////////////////////////////////////////////////////////////////////////////////// ahmad ////////////////////////////////////////////////////////////////////////////////




if(StringUtils.equalsIgnoreCase(max,"Max") && profitable == null && loss == null&&monthly==0 &&accumulated==0) {
System.out.println("Paasses heree yara test");
	if (siteId == "") {

String  startdate="";
String enddate="";
System.out.println ("maximim selected for siteId null");

if((StringUtils.equalsIgnoreCase(profitable,"null") || profitable == null || profitable == "") && (StringUtils.equalsIgnoreCase(loss,"null") || loss == null || loss == "")&& (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")  ) { 
System.out.println("AHMADISTESTINGGG"); 

query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2)";

q=session.createSQLQuery(query1);

q.setParameter("param1", StartDate);
q.setParameter("param2", EndDate);


SiteReportList =  q.list();
}



System.out.println("/*/*The value of q is " +mapper.writeValueAsString(SiteReportList));
System.out.println("/*/*/*/*The zise is "+SiteReportList.size());

if(SiteReportList.size()>0) {

for (int i = 0; i < SiteReportList.size(); i++) {
System.out.println("Passes here on the loop");
SiteReportArray = (Object[])  SiteReportList.toArray()[i];

if(SiteReportArray[0]!=null) {
SiteID=(String)SiteReportArray[0];

}

else {
SiteID="" ;
}

if(SiteReportArray[1]!=null) {
SiteName=(String)SiteReportArray[1];

}
else {
SiteName="" ;
} 

if(SiteReportArray[2]!=null) {
areaName=(String)SiteReportArray[2];
}
else {
areaName="" ;
} 

if(SiteReportArray[3]!=null) {
siteOwner=(String)SiteReportArray[3];
}
else {
siteOwner="" ;
} 


if(SiteReportArray[4]!=null) {
towerType=(String)SiteReportArray[4];
}
else {
towerType="" ;
} 

if(SiteReportArray[5]!=null) {
startDate=(String)SiteReportArray[5];
}
else {
startDate="" ;
}


if(SiteReportArray[6]!=null) {
endDate=SiteReportArray[6];

}
else {
endDate="" ;
}

if(SiteReportArray[7]!=null) {
dataRev1= SiteReportArray[7];
}


else {
System.out.println("Passes here");

dataRev1="0";	

}


if(SiteReportArray[8]!=null) {
voiceRev1=  SiteReportArray[8];
}


else {
System.out.println("Passes here");

voiceRev1="0";	

}

if(SiteReportArray[9]!=null) {
smsRev1= SiteReportArray[9];
}


else {
System.out.println("Passes here");

smsRev1="0";	

}

if(SiteReportArray[10]!=null) {
grossRev1= SiteReportArray[10];
}


else {
System.out.println("Passes here");

grossRev1="0";	

}

if(SiteReportArray[11]!=null) {
dataTraf1= SiteReportArray[11];
}


else {
System.out.println("Passes here");

dataTraf1="0";	

}

if(SiteReportArray[12]!=null) {
smsTrafOg1= SiteReportArray[12];

}


else {
System.out.println("Passes here");

smsTrafOg1="0";	

}

if(SiteReportArray[13]!=null) {
voiceTrafOg1= SiteReportArray[13];
}


else {
System.out.println("Passes here");

voiceTrafOg1="0";	

}


if(SiteReportArray[14]!=null) {
smsTrafIc1= SiteReportArray[14];
}


else {
System.out.println("Passes here");

smsTrafIc1="0";	

}


if(SiteReportArray[15]!=null) {
voiceTrafIc1= SiteReportArray[15];
}


else {
System.out.println("Passes here");

voiceTrafIc1="0";	

}

if(SiteReportArray[16]!=null) {
opex1= SiteReportArray[16];
}


else {
System.out.println("Passes here");

opex1="0";	

}


if(SiteReportArray[17]!=null) {
profLoss1= SiteReportArray[17];
}


else {
System.out.println("Passes here");

profLoss1="0";	

}



String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};

arrayData1 = arrayData;

System.out.println("/*/*/*/*/*The array1" +mapper.writeValueAsString(arrayData1));
Data.add(arrayData1);
}  
} 


if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
{
if(StringUtils.equalsIgnoreCase(technologies,"2g"))
{
query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 )";

q=session.createSQLQuery(query1);
q.setParameter("param1", StartDate);
q.setParameter("param2", EndDate);
SiteReportList =  q.list();
}

if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
{
query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 )";

q=session.createSQLQuery(query1);
q.setParameter("param1", StartDate);
q.setParameter("param2", EndDate);
SiteReportList =  q.list();
}

if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
{
query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1)";

q=session.createSQLQuery(query1);
q.setParameter("param1", StartDate);
q.setParameter("param2", EndDate);
SiteReportList =  q.list();
}


System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
System.out.println("The zise is "+SiteReportList.size());

if(SiteReportList.size()>0) {

for (int i = 0; i < SiteReportList.size(); i++) {

SiteReportArray = (Object[])  SiteReportList.toArray()[i];

if(SiteReportArray[0]!=null) {
SiteID=(String)SiteReportArray[0];

}

else {
SiteID="" ;
}

if(SiteReportArray[1]!=null) {
SiteName=(String)SiteReportArray[1];

}
else {
SiteName="" ;
} 

if(SiteReportArray[2]!=null) {
areaName=(String)SiteReportArray[2];
}
else {
areaName="" ;
} 

if(SiteReportArray[3]!=null) {
siteOwner=(String)SiteReportArray[3];
}
else {
siteOwner="" ;
} 


if(SiteReportArray[4]!=null) {
towerType=(String)SiteReportArray[4];
}
else {
towerType="" ;
} 

if(SiteReportArray[5]!=null) {
startDate=(String)SiteReportArray[5];
}
else {
startDate="" ;
}


if(SiteReportArray[6]!=null) {
endDate=SiteReportArray[6];

}
else {
endDate="" ;
}

if(SiteReportArray[7]!=null) {
dataRev1= SiteReportArray[7];
}


else {
System.out.println("Passes here");

dataRev1="0";	

}


if(SiteReportArray[8]!=null) {
voiceRev1=  SiteReportArray[8];
}


else {
System.out.println("Passes here");

voiceRev1="0";	

}

if(SiteReportArray[9]!=null) {
smsRev1= SiteReportArray[9];
}


else {
System.out.println("Passes here");

smsRev1="0";	

}

if(SiteReportArray[10]!=null) {
grossRev1= SiteReportArray[10];
}


else {
System.out.println("Passes here");

grossRev1="0";	

}

if(SiteReportArray[11]!=null) {
dataTraf1= SiteReportArray[11];
}


else {
System.out.println("Passes here");

dataTraf1="0";	

}

if(SiteReportArray[12]!=null) {
smsTrafOg1= SiteReportArray[12];

}


else {
System.out.println("Passes here");

smsTrafOg1="0";	

}

if(SiteReportArray[13]!=null) {
voiceTrafOg1= SiteReportArray[13];
}


else {
System.out.println("Passes here");

voiceTrafOg1="0";	

}


if(SiteReportArray[14]!=null) {
smsTrafIc1= SiteReportArray[14];
}


else {
System.out.println("Passes here");

smsTrafIc1="0";	

}


if(SiteReportArray[15]!=null) {
voiceTrafIc1= SiteReportArray[15];
}


else {
System.out.println("Passes here");

voiceTrafIc1="0";	

}

if(SiteReportArray[16]!=null) {
opex1= SiteReportArray[16];
}


else {
System.out.println("Passes here");

opex1="0";	

}


if(SiteReportArray[17]!=null) {
profLoss1= SiteReportArray[17];
}


else {
System.out.println("Passes here");

profLoss1="0";	

}



String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};

arrayData1 = arrayData;

System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
Data.add(arrayData1);
  }  
  } 
	}

} // END THE SITEID NULL CONDITION 

	
	
else {
	
	String  startdate="";
	String enddate="";
	System.out.println ("maximim selected for siteId null");

	if((StringUtils.equalsIgnoreCase(profitable,"null") || profitable == null || profitable == "") && (StringUtils.equalsIgnoreCase(loss,"null") || loss == null || loss == "")&& (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")  ) { 
	System.out.println("AHMADISTESTINGGG"); 

	query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
			+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
			+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and site_id = :param3)";

			q=session.createSQLQuery(query1);

			q.setParameter("param1", StartDate);
			q.setParameter("param2", EndDate);
			q.setParameter("param3",siteId );

			SiteReportList =  q.list();
            }
	

	       System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
	       System.out.println("The zise is "+SiteReportList.size());
	
	
	       if(SiteReportList.size()>0) {

	   		for (int i = 0; i < SiteReportList.size(); i++) {

	   		SiteReportArray = (Object[])  SiteReportList.toArray()[i];

	   		if(SiteReportArray[0]!=null) {
	   		SiteID=(String)SiteReportArray[0];

	   		}

	   		else {
	   		SiteID="" ;
	   		}

	   		if(SiteReportArray[1]!=null) {
	   		SiteName=(String)SiteReportArray[1];

	   		}
	   		else {
	   		SiteName="" ;
	   		} 

	   		if(SiteReportArray[2]!=null) {
	   		areaName=(String)SiteReportArray[2];
	   		}
	   		else {
	   		areaName="" ;
	   		} 

	   		if(SiteReportArray[3]!=null) {
	   		siteOwner=(String)SiteReportArray[3];
	   		}
	   		else {
	   		siteOwner="" ;
	   		} 


	   		if(SiteReportArray[4]!=null) {
	   		towerType=(String)SiteReportArray[4];
	   		}
	   		else {
	   		towerType="" ;
	   		} 

	   		if(SiteReportArray[5]!=null) {
	   		startDate=(String)SiteReportArray[5];
	   		}
	   		else {
	   		startDate="" ;
	   		}


	   		if(SiteReportArray[6]!=null) {
	   		endDate=SiteReportArray[6];

	   		}
	   		else {
	   		endDate="" ;
	   		}

	   		if(SiteReportArray[7]!=null) {
	   		dataRev1= SiteReportArray[7];
	   		}


	   		else {
	   		System.out.println("Passes here");

	   		dataRev1="0";	

	   		}


	   		if(SiteReportArray[8]!=null) {
	   		voiceRev1=  SiteReportArray[8];
	   		}


	   		else {
	   		System.out.println("Passes here");

	   		voiceRev1="0";	

	   		}

	   		if(SiteReportArray[9]!=null) {
	   		smsRev1= SiteReportArray[9];
	   		}


	   		else {
	   		System.out.println("Passes here");

	   		smsRev1="0";	

	   		}

	   		if(SiteReportArray[10]!=null) {
	   		grossRev1= SiteReportArray[10];
	   		}


	   		else {
	   		System.out.println("Passes here");

	   		grossRev1="0";	

	   		}

	   		if(SiteReportArray[11]!=null) {
	   		dataTraf1= SiteReportArray[11];
	   		}


	   		else {
	   		System.out.println("Passes here");

	   		dataTraf1="0";	

	   		}

	   		if(SiteReportArray[12]!=null) {
	   		smsTrafOg1= SiteReportArray[12];

	   		}


	   		else {
	   		System.out.println("Passes here");

	   		smsTrafOg1="0";	

	   		}

	   		if(SiteReportArray[13]!=null) {
	   		voiceTrafOg1= SiteReportArray[13];
	   		}


	   		else {
	   		System.out.println("Passes here");

	   		voiceTrafOg1="0";	

	   		}


	   		if(SiteReportArray[14]!=null) {
	   		smsTrafIc1= SiteReportArray[14];
	   		}


	   		else {
	   		System.out.println("Passes here");

	   		smsTrafIc1="0";	

	   		}


	   		if(SiteReportArray[15]!=null) {
	   		voiceTrafIc1= SiteReportArray[15];
	   		}


	   		else {
	   		System.out.println("Passes here");

	   		voiceTrafIc1="0";	

	   		}

	   		if(SiteReportArray[16]!=null) {
	   		opex1= SiteReportArray[16];
	   		}


	   		else {
	   		System.out.println("Passes here");

	   		opex1="0";	

	   		}


	   		if(SiteReportArray[17]!=null) {
	   		profLoss1= SiteReportArray[17];
	   		}


	   		else {
	   		System.out.println("Passes here");

	   		profLoss1="0";	

	   		}


	   		String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};

			arrayData1 = arrayData;

			System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
			Data.add(arrayData1);
	   		}
	       }
 
	       
	       if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
			{
			if(StringUtils.equalsIgnoreCase(technologies,"2g"))
			{
			query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
			+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
			+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 )";

			q=session.createSQLQuery(query1);
			q.setParameter("param1", StartDate);
			q.setParameter("param2", EndDate);
			q.setParameter("param3",siteId );
			SiteReportList =  q.list();
			}

			if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
			{
			query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
			+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
			+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 )";

			q=session.createSQLQuery(query1);
			q.setParameter("param1", StartDate);
			q.setParameter("param2", EndDate);
			q.setParameter("param3",siteId );
			SiteReportList =  q.list();
			}

			if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
			{
			query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
			+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select max(profit_and_loss) "
			+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1)";

			q=session.createSQLQuery(query1);
			q.setParameter("param1", StartDate);
			q.setParameter("param2", EndDate);
			q.setParameter("param3",siteId );
			SiteReportList =  q.list();
			}
			}

			System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
			System.out.println("The zise is "+SiteReportList.size());

			if(SiteReportList.size()>0) {

			for (int i = 0; i < SiteReportList.size(); i++) {

			SiteReportArray = (Object[])  SiteReportList.toArray()[i];

			if(SiteReportArray[0]!=null) {
			SiteID=(String)SiteReportArray[0];

			}

			else {
			SiteID="" ;
			}

			if(SiteReportArray[1]!=null) {
			SiteName=(String)SiteReportArray[1];

			}
			else {
			SiteName="" ;
			} 

			if(SiteReportArray[2]!=null) {
			areaName=(String)SiteReportArray[2];
			}
			else {
			areaName="" ;
			} 

			if(SiteReportArray[3]!=null) {
			siteOwner=(String)SiteReportArray[3];
			}
			else {
			siteOwner="" ;
			} 


			if(SiteReportArray[4]!=null) {
			towerType=(String)SiteReportArray[4];
			}
			else {
			towerType="" ;
			} 

			if(SiteReportArray[5]!=null) {
			startDate=(String)SiteReportArray[5];
			}
			else {
			startDate="" ;
			}


			if(SiteReportArray[6]!=null) {
			endDate=SiteReportArray[6];

			}
			else {
			endDate="" ;
			}

			if(SiteReportArray[7]!=null) {
			dataRev1= SiteReportArray[7];
			}


			else {
			System.out.println("Passes here");

			dataRev1="0";	

			}


			if(SiteReportArray[8]!=null) {
			voiceRev1=  SiteReportArray[8];
			}


			else {
			System.out.println("Passes here");

			voiceRev1="0";	

			}

			if(SiteReportArray[9]!=null) {
			smsRev1= SiteReportArray[9];
			}


			else {
			System.out.println("Passes here");

			smsRev1="0";	

			}

			if(SiteReportArray[10]!=null) {
			grossRev1= SiteReportArray[10];
			}


			else {
			System.out.println("Passes here");

			grossRev1="0";	

			}

			if(SiteReportArray[11]!=null) {
			dataTraf1= SiteReportArray[11];
			}


			else {
			System.out.println("Passes here");

			dataTraf1="0";	

			}

			if(SiteReportArray[12]!=null) {
			smsTrafOg1= SiteReportArray[12];

			}


			else {
			System.out.println("Passes here");

			smsTrafOg1="0";	

			}

			if(SiteReportArray[13]!=null) {
			voiceTrafOg1= SiteReportArray[13];
			}


			else {
			System.out.println("Passes here");

			voiceTrafOg1="0";	

			}


			if(SiteReportArray[14]!=null) {
			smsTrafIc1= SiteReportArray[14];
			}


			else {
			System.out.println("Passes here");

			smsTrafIc1="0";	

			}


			if(SiteReportArray[15]!=null) {
			voiceTrafIc1= SiteReportArray[15];
			}


			else {
			System.out.println("Passes here");

			voiceTrafIc1="0";	

			}

			if(SiteReportArray[16]!=null) {
			opex1= SiteReportArray[16];
			}


			else {
			System.out.println("Passes here");

			opex1="0";	

			}


			if(SiteReportArray[17]!=null) {
			profLoss1= SiteReportArray[17];
			}


			else {
			System.out.println("Passes here");

			profLoss1="0";	

			}



			String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};

			arrayData1 = arrayData;

			System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
			Data.add(arrayData1);
			}  
			} 
	
   } // END THE SITEID SELECTED CONDITION 

} // END MAX CONDITION




/////////////////////////////////////////////////////////////////////////////////// ahmad ////////////////////////////////////////////////////////////////////////////////




if(StringUtils.equalsIgnoreCase(min,"Min") && profitable == null && loss == null) {

if (siteId == "") {

String  startdate="";
String enddate="";
System.out.println ("minimum selected for siteId null");

if((StringUtils.equalsIgnoreCase(profitable,"null") || profitable == null || profitable == "") && (StringUtils.equalsIgnoreCase(loss,"null") || loss == null || loss == "")&& (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")  ) { 
System.out.println("AHMADISTESTINGGG"); 

query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2)";

q=session.createSQLQuery(query1);

q.setParameter("param1", StartDate);
q.setParameter("param2", EndDate);


SiteReportList =  q.list();
}



System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
System.out.println("The zise is "+SiteReportList.size());

if(SiteReportList.size()>0) {

for (int i = 0; i < SiteReportList.size(); i++) {

SiteReportArray = (Object[])  SiteReportList.toArray()[i];

if(SiteReportArray[0]!=null) {
SiteID=(String)SiteReportArray[0];

}

else {
SiteID="" ;
}

if(SiteReportArray[1]!=null) {
SiteName=(String)SiteReportArray[1];

}
else {
SiteName="" ;
} 

if(SiteReportArray[2]!=null) {
areaName=(String)SiteReportArray[2];
}
else {
areaName="" ;
} 

if(SiteReportArray[3]!=null) {
siteOwner=(String)SiteReportArray[3];
}
else {
siteOwner="" ;
} 


if(SiteReportArray[4]!=null) {
towerType=(String)SiteReportArray[4];
}
else {
towerType="" ;
} 

if(SiteReportArray[5]!=null) {
startDate=(String)SiteReportArray[5];
}
else {
startDate="" ;
}


if(SiteReportArray[6]!=null) {
endDate=SiteReportArray[6];

}
else {
endDate="" ;
}

if(SiteReportArray[7]!=null) {
dataRev1= SiteReportArray[7];
}


else {
System.out.println("Passes here");

dataRev1="0";	

}


if(SiteReportArray[8]!=null) {
voiceRev1=  SiteReportArray[8];
}


else {
System.out.println("Passes here");

voiceRev1="0";	

}

if(SiteReportArray[9]!=null) {
smsRev1= SiteReportArray[9];
}


else {
System.out.println("Passes here");

smsRev1="0";	

}

if(SiteReportArray[10]!=null) {
grossRev1= SiteReportArray[10];
}


else {
System.out.println("Passes here");

grossRev1="0";	

}

if(SiteReportArray[11]!=null) {
dataTraf1= SiteReportArray[11];
}


else {
System.out.println("Passes here");

dataTraf1="0";	

}

if(SiteReportArray[12]!=null) {
smsTrafOg1= SiteReportArray[12];

}


else {
System.out.println("Passes here");

smsTrafOg1="0";	

}

if(SiteReportArray[13]!=null) {
voiceTrafOg1= SiteReportArray[13];
}


else {
System.out.println("Passes here");

voiceTrafOg1="0";	

}


if(SiteReportArray[14]!=null) {
smsTrafIc1= SiteReportArray[14];
}


else {
System.out.println("Passes here");

smsTrafIc1="0";	

}


if(SiteReportArray[15]!=null) {
voiceTrafIc1= SiteReportArray[15];
}


else {
System.out.println("Passes here");

voiceTrafIc1="0";	

}

if(SiteReportArray[16]!=null) {
opex1= SiteReportArray[16];
}


else {
System.out.println("Passes here");

opex1="0";	

}


if(SiteReportArray[17]!=null) {
profLoss1= SiteReportArray[17];
}


else {
System.out.println("Passes here");

profLoss1="0";	

}



String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};

arrayData1 = arrayData;

System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
Data.add(arrayData1);
}  
} 


if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
{
if(StringUtils.equalsIgnoreCase(technologies,"2g"))
{
query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 )";

q=session.createSQLQuery(query1);
q.setParameter("param1", StartDate);
q.setParameter("param2", EndDate);
SiteReportList =  q.list();
}

if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
{
query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 )";

q=session.createSQLQuery(query1);
q.setParameter("param1", StartDate);
q.setParameter("param2", EndDate);
SiteReportList =  q.list();
}

if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
{
query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1)";

q=session.createSQLQuery(query1);
q.setParameter("param1", StartDate);
q.setParameter("param2", EndDate);
SiteReportList =  q.list();
}
}

System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
System.out.println("The zise is "+SiteReportList.size());

if(SiteReportList.size()>0) {

for (int i = 0; i < SiteReportList.size(); i++) {

SiteReportArray = (Object[])  SiteReportList.toArray()[i];

if(SiteReportArray[0]!=null) {
SiteID=(String)SiteReportArray[0];

}

else {
SiteID="" ;
}

if(SiteReportArray[1]!=null) {
SiteName=(String)SiteReportArray[1];

}
else {
SiteName="" ;
} 

if(SiteReportArray[2]!=null) {
areaName=(String)SiteReportArray[2];
}
else {
areaName="" ;
} 

if(SiteReportArray[3]!=null) {
siteOwner=(String)SiteReportArray[3];
}
else {
siteOwner="" ;
} 


if(SiteReportArray[4]!=null) {
towerType=(String)SiteReportArray[4];
}
else {
towerType="" ;
} 

if(SiteReportArray[5]!=null) {
startDate=(String)SiteReportArray[5];
}
else {
startDate="" ;
}


if(SiteReportArray[6]!=null) {
endDate=SiteReportArray[6];

}
else {
endDate="" ;
}

if(SiteReportArray[7]!=null) {
dataRev1= SiteReportArray[7];
}


else {
System.out.println("Passes here");

dataRev1="0";	

}


if(SiteReportArray[8]!=null) {
voiceRev1=  SiteReportArray[8];
}


else {
System.out.println("Passes here");

voiceRev1="0";	

}

if(SiteReportArray[9]!=null) {
smsRev1= SiteReportArray[9];
}


else {
System.out.println("Passes here");

smsRev1="0";	

}

if(SiteReportArray[10]!=null) {
grossRev1= SiteReportArray[10];
}


else {
System.out.println("Passes here");

grossRev1="0";	

}

if(SiteReportArray[11]!=null) {
dataTraf1= SiteReportArray[11];
}


else {
System.out.println("Passes here");

dataTraf1="0";	

}

if(SiteReportArray[12]!=null) {
smsTrafOg1= SiteReportArray[12];

}


else {
System.out.println("Passes here");

smsTrafOg1="0";	

}

if(SiteReportArray[13]!=null) {
voiceTrafOg1= SiteReportArray[13];
}


else {
System.out.println("Passes here");

voiceTrafOg1="0";	

}


if(SiteReportArray[14]!=null) {
smsTrafIc1= SiteReportArray[14];
}


else {
System.out.println("Passes here");

smsTrafIc1="0";	

}


if(SiteReportArray[15]!=null) {
voiceTrafIc1= SiteReportArray[15];
}


else {
System.out.println("Passes here");

voiceTrafIc1="0";	

}

if(SiteReportArray[16]!=null) {
opex1= SiteReportArray[16];
}


else {
System.out.println("Passes here");

opex1="0";	

}


if(SiteReportArray[17]!=null) {
profLoss1= SiteReportArray[17];
}


else {
System.out.println("Passes here");

profLoss1="0";	

}



String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};

arrayData1 = arrayData;

System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
Data.add(arrayData1);
}  
} 


} // END THE SITEID NULL CONDITION 



else {

String  startdate="";
String enddate="";
System.out.println ("minimum selected for siteId null");

if((StringUtils.equalsIgnoreCase(profitable,"null") || profitable == null || profitable == "") && (StringUtils.equalsIgnoreCase(loss,"null") || loss == null || loss == "")&& (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")  ) { 
System.out.println("AHMADISTESTINGGG"); 

query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and site_id = :param3)";

q=session.createSQLQuery(query1);

q.setParameter("param1", StartDate);
q.setParameter("param2", EndDate);
q.setParameter("param3",siteId );

SiteReportList =  q.list();
}


System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
System.out.println("The zise is "+SiteReportList.size());


if(SiteReportList.size()>0) {

for (int i = 0; i < SiteReportList.size(); i++) {

SiteReportArray = (Object[])  SiteReportList.toArray()[i];

if(SiteReportArray[0]!=null) {
SiteID=(String)SiteReportArray[0];

}

else {
SiteID="" ;
}

if(SiteReportArray[1]!=null) {
SiteName=(String)SiteReportArray[1];

}
else {
SiteName="" ;
} 

if(SiteReportArray[2]!=null) {
areaName=(String)SiteReportArray[2];
}
else {
areaName="" ;
} 

if(SiteReportArray[3]!=null) {
siteOwner=(String)SiteReportArray[3];
}
else {
siteOwner="" ;
} 


if(SiteReportArray[4]!=null) {
towerType=(String)SiteReportArray[4];
}
else {
towerType="" ;
} 

if(SiteReportArray[5]!=null) {
startDate=(String)SiteReportArray[5];
}
else {
startDate="" ;
}


if(SiteReportArray[6]!=null) {
endDate=SiteReportArray[6];

}
else {
endDate="" ;
}

if(SiteReportArray[7]!=null) {
dataRev1= SiteReportArray[7];
}


else {
System.out.println("Passes here");

dataRev1="0";	

}


if(SiteReportArray[8]!=null) {
voiceRev1=  SiteReportArray[8];
}


else {
System.out.println("Passes here");

voiceRev1="0";	

}

if(SiteReportArray[9]!=null) {
smsRev1= SiteReportArray[9];
}


else {
System.out.println("Passes here");

smsRev1="0";	

}

if(SiteReportArray[10]!=null) {
grossRev1= SiteReportArray[10];
}


else {
System.out.println("Passes here");

grossRev1="0";	

}

if(SiteReportArray[11]!=null) {
dataTraf1= SiteReportArray[11];
}


else {
System.out.println("Passes here");

dataTraf1="0";	

}

if(SiteReportArray[12]!=null) {
smsTrafOg1= SiteReportArray[12];

}


else {
System.out.println("Passes here");

smsTrafOg1="0";	

}

if(SiteReportArray[13]!=null) {
voiceTrafOg1= SiteReportArray[13];
}


else {
System.out.println("Passes here");

voiceTrafOg1="0";	

}


if(SiteReportArray[14]!=null) {
smsTrafIc1= SiteReportArray[14];
}


else {
System.out.println("Passes here");

smsTrafIc1="0";	

}


if(SiteReportArray[15]!=null) {
voiceTrafIc1= SiteReportArray[15];
}


else {
System.out.println("Passes here");

voiceTrafIc1="0";	

}

if(SiteReportArray[16]!=null) {
opex1= SiteReportArray[16];
}


else {
System.out.println("Passes here");

opex1="0";	

}


if(SiteReportArray[17]!=null) {
profLoss1= SiteReportArray[17];
}


else {
System.out.println("Passes here");

profLoss1="0";	

}


String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};

arrayData1 = arrayData;

System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
Data.add(arrayData1);
}
}


if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
{
if(StringUtils.equalsIgnoreCase(technologies,"2g"))
{
query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and site_id = :param3 and tech_2g= 1 and tech_3g= 0 and tech_4g= 0 )";

q=session.createSQLQuery(query1);
q.setParameter("param1", StartDate);
q.setParameter("param2", EndDate);
q.setParameter("param3",siteId );
SiteReportList =  q.list();
}

if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
{
query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 0 )";

q=session.createSQLQuery(query1);
q.setParameter("param1", StartDate);
q.setParameter("param2", EndDate);
q.setParameter("param3",siteId );
SiteReportList =  q.list();
}

if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
{
query1 = "select site_id, ware_name, area_name, site_owner, tower_type, TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(end_date, 'YYYY-MM-DD HH24:MI:SS') , data, voice_revenu, sms_revenu, gross_revenu, data_traffic, total_sms_traffic_og, total_voice_traffic_og, total_sms_traffic_ic,"
+"total_voice_traffic_ic, total_opex_monthly, profit_and_loss from warehouse_profit_loss where profit_and_loss IN(select min(profit_and_loss) "
+"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and site_id = :param3 and tech_2g= 1 and tech_3g= 1 and tech_4g= 1)";

q=session.createSQLQuery(query1);
q.setParameter("param1", StartDate);
q.setParameter("param2", EndDate);
q.setParameter("param3",siteId );
SiteReportList =  q.list();
}
}

System.out.println("The value of q is " +mapper.writeValueAsString(SiteReportList));
System.out.println("The zise is "+SiteReportList.size());

if(SiteReportList.size()>0) {

for (int i = 0; i < SiteReportList.size(); i++) {

SiteReportArray = (Object[])  SiteReportList.toArray()[i];

if(SiteReportArray[0]!=null) {
SiteID=(String)SiteReportArray[0];

}

else {
SiteID="" ;
}

if(SiteReportArray[1]!=null) {
SiteName=(String)SiteReportArray[1];

}
else {
SiteName="" ;
} 

if(SiteReportArray[2]!=null) {
areaName=(String)SiteReportArray[2];
}
else {
areaName="" ;
} 

if(SiteReportArray[3]!=null) {
siteOwner=(String)SiteReportArray[3];
}
else {
siteOwner="" ;
} 


if(SiteReportArray[4]!=null) {
towerType=(String)SiteReportArray[4];
}
else {
towerType="" ;
} 

if(SiteReportArray[5]!=null) {
startDate=(String)SiteReportArray[5];
}
else {
startDate="" ;
}


if(SiteReportArray[6]!=null) {
endDate=SiteReportArray[6];

}
else {
endDate="" ;
}

if(SiteReportArray[7]!=null) {
dataRev1= SiteReportArray[7];
}


else {
System.out.println("Passes here");

dataRev1="0";	

}


if(SiteReportArray[8]!=null) {
voiceRev1=  SiteReportArray[8];
}


else {
System.out.println("Passes here");

voiceRev1="0";	

}

if(SiteReportArray[9]!=null) {
smsRev1= SiteReportArray[9];
}


else {
System.out.println("Passes here");

smsRev1="0";	

}

if(SiteReportArray[10]!=null) {
grossRev1= SiteReportArray[10];
}


else {
System.out.println("Passes here");

grossRev1="0";	

}

if(SiteReportArray[11]!=null) {
dataTraf1= SiteReportArray[11];
}


else {
System.out.println("Passes here");

dataTraf1="0";	

}

if(SiteReportArray[12]!=null) {
smsTrafOg1= SiteReportArray[12];

}


else {
System.out.println("Passes here");

smsTrafOg1="0";	

}

if(SiteReportArray[13]!=null) {
voiceTrafOg1= SiteReportArray[13];
}


else {
System.out.println("Passes here");

voiceTrafOg1="0";	

}


if(SiteReportArray[14]!=null) {
smsTrafIc1= SiteReportArray[14];
}


else {
System.out.println("Passes here");

smsTrafIc1="0";	

}


if(SiteReportArray[15]!=null) {
voiceTrafIc1= SiteReportArray[15];
}


else {
System.out.println("Passes here");

voiceTrafIc1="0";	

}

if(SiteReportArray[16]!=null) {
opex1= SiteReportArray[16];
}


else {
System.out.println("Passes here");

opex1="0";	

}


if(SiteReportArray[17]!=null) {
profLoss1= SiteReportArray[17];
}


else {
System.out.println("Passes here");

profLoss1="0";	

}



String[] arrayData= {"1",SiteID,SiteName,String.valueOf(startDate),String.valueOf(endDate),areaName,siteOwner,towerType,String.valueOf(dataRev1),String.valueOf(voiceRev1),String.valueOf(smsRev1),String.valueOf(grossRev1),String.valueOf(dataTraf1),String.valueOf(smsTrafOg1),String.valueOf(voiceTrafOg1),String.valueOf(voiceTrafIc1),String.valueOf(smsTrafIc1),String.valueOf(opex1),String.valueOf(profLoss1)};

arrayData1 = arrayData;

System.out.println("The array1" +mapper.writeValueAsString(arrayData1));
Data.add(arrayData1);
}  
} 

} // END THE SITEID SELECTED CONDITION 

} // END MIN CONDITION


/////////////////////////////////////////////////////////////////////////////////// ahmad ////////////////////////////////////////////////////////////////////////////////



			tx.commit();
			session.close();

		}		
		
		rtn.put("plPerSiteTableData", Data);
		return rtn;
	}

	private String String(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@RequestMapping(value = "/GetAllAreasID", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllAreasID(Locale locale, Model model, HttpServletRequest request)
	throws JsonGenerationException, JsonMappingException, IOException {
	//logger.info("Welcome home! The client locale is {}.", locale);
	

	Map<String, Object> rtn = new LinkedHashMap<>();

	List<AreaFinance> areaList = new ArrayList<AreaFinance>();

	Session session =almsessions.getSession();
	Transaction tx = session.beginTransaction();
	String requestName = "%" + request.getParameter("areaID") + "%";
	Query q = session.createQuery("select id, name from Area where id like :param1");
	q.setString("param1", requestName);
	q.setFirstResult(0);
	q.setMaxResults(40);
	areaList = q.list();


	tx.commit();
	session.close();
	ObjectMapper mapper = new ObjectMapper();
	
    System.out.println("end good " + mapper.writeValueAsString(areaList));
	rtn.put("areaIDList",  areaList);

	return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/GetAreasByDefault", method = RequestMethod.GET)
	@ResponseBody public Map<String, Object> GetAreasByDefault(Locale locale, Model model,
		HttpServletRequest request, HttpServletResponse response) throws
		JsonProcessingException { 
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Map<String, Object> rtn = new LinkedHashMap<>(); 
	    ObjectMapper mapper = new ObjectMapper();
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
			  
		List<Object[]> areasList = (List<Object[]>) session.createSQLQuery("Select distinct AREA.area_name, AREA.AREA_ID, AREA.longitude, AREA.latitude FROM AREA, area_finance WHERE area.area_id = area_finance.area_id ").list();
			  String area = null;
			  String[] lng = null, lat = null;
			  List<Object[]> areasResult = new ArrayList<Object[]>();
			  Object[] arrangedObj = null;
			  List<String> lnglat = new ArrayList<String>();
			  if (areasList != null) {
				  for (Object[] obj : areasList) {
					System.out.println("the obj is  " +mapper.writeValueAsString(obj));
					area = obj[1]+";"+obj[0];
					System.out.println("the area is  " +mapper.writeValueAsString(area));
					lng =  ((String) obj[2]).split(";");
					System.out.println("the lng is  " +mapper.writeValueAsString(lng));
					lat =  ((String) obj[3]).split(";");
					System.out.println("the lat is  " +mapper.writeValueAsString(lat));
					lnglat = new ArrayList<String>();
					 for(int i=0;i<lng.length;i++){
						// lnglat.add("{lat: "+ lat[i]+",lng: "+lng[i]+"}");
						 lnglat.add(lat[i]);
						 lnglat.add(lng[i]);
						 System.out.println("the lnglat is  " +mapper.writeValueAsString(lnglat));
			    	    
				     }
					 
					 arrangedObj = new Object[]{area,lnglat};	
					
					 areasResult.add(arrangedObj);
								  
			  
			  		  
			  } 
			  System.out.println("Area List:" +mapper.writeValueAsString(areasResult)); 
			  rtn.put("listAreas", areasResult); 
			  }
			  
			  return rtn; 
			 
			 }



	
/*	
	// to get all warehouse Ids in the form view
		@RequestMapping(value = "/GetAllWarehousesbySites", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> GetAllWarehouse(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
				throws JsonGenerationException, JsonMappingException, IOException {
			
	        //logger.info("Welcome home! The client locale is {}.", locale);
	       //System.out.println("Welcome to GetAllSupplier");
	       System.out.println("Welcome to this method");
			Map<String, Object> rtn = new LinkedHashMap<>();
			if(LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			List<Warehouse> listWarehouse = new ArrayList<Warehouse>();

			AppConfig appConfig = new AppConfig();
			Session session = appConfig.getSession();
			Transaction tx = session.beginTransaction();
			
			String WarehouseName = "%"+ request.getParameter("warehouseName")+"%" ;
			String WareName= request.getParameter("WareName") ;
			String siteId =  request.getParameter("SiteId") ;
		       
			Query q = session.createQuery("select wareID, warehouseName, wareSiteID, wareLong, wareLat from Warehouse where (wareID like :param1 or warehouseName like :param1 or wareSiteID like :param1) and wareSite = 1 ");
			q.setString("param1","%"+WarehouseName+"%" );
			System.out.println("query is  "+WarehouseName);
			q.setFirstResult(0);
			q.setMaxResults(40);
			listWarehouse = q.list();

			tx.commit();
			session.close();
			ObjectMapper mapper = new ObjectMapper();
			model.addAttribute("ListItemWarehouse", mapper.writeValueAsString(listWarehouse));
			
			rtn.put("ListItemWarehouse", listWarehouse);
				

			
		    
			return rtn;
		}
*/		
		@RequestMapping(value = "/AlmGridView", method = RequestMethod.GET)
		public String AlmGridView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
			//logger.info("Welcome home! The client locale is {}.", locale);
			if(LoginServices.checkSession(request, response).equals("redirect:/")) {
				return LoginServices.checkSession(request, response);
			}else {
				
				List<String[]> report = new ArrayList<String[]>();
				model.addAttribute("AlmGridView", report);
				
				/*List<String[]> plPerSiteData = new ArrayList<String[]>();
				model.addAttribute("plPerSiteTableData", plPerSiteData);
				
				*/
				
				
			return "AlmGridView";
			}
		}

		
		
		
		

		@SuppressWarnings("unchecked")
		@RequestMapping(value ="/GetSitesByDefault", method = RequestMethod.GET)
		@ResponseBody public Map<String, Object> Site(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) throws
			JsonProcessingException { 
			logger.info("Welcome home! The client locale is {}.", locale);
			
			Map<String, Object> rtn = new LinkedHashMap<>(); 
		    ObjectMapper mapper = new ObjectMapper();
			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
				  
			List<Object[]> siteList = (List<Object[]>) session.createSQLQuery(					
							
				//"SELECT distinct b.WARE_ID,b.WARE_NAME,b.SITE_ID,b.LATITUDE,b.LONGITUDE,b.SITE FROM WAREHOUSE b "
				"SELECT * FROM(SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "+
				" b.ProfitLossSum FROM WAREHOUSE a right join (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "+
				" FROM WAREHOUSE_PROFIT_LOSS group by site_id) b "+
				" on a.site_id = b.site_id order by ProfitLossSum DESC) ").list();
				  
				  List<Object[]> siteResult = new ArrayList<Object[]>();
				  
				 if (siteList != null) {
					  for (Object[] obj : siteList) {
						  
						  if (Integer.parseInt(String.valueOf((obj[5]))) == 1) {
								System.out.println("WAREHOUSE " +obj[0]+" is also a SITE");
								siteResult.add(obj);
							}			  
				  
				  		  
				  } 
				  System.out.println("Site List:" +mapper.writeValueAsString(siteResult)); 
				  rtn.put("listSites", siteResult); 
				  }
				
				  return rtn; 
				 
				 }

		@SuppressWarnings("unchecked")
		@RequestMapping(value ="/GetSelectedSite", method = RequestMethod.GET)
		@ResponseBody public Map<String, Object> GetSelectedSite(Locale locale, Model model,
		   HttpServletRequest request, HttpServletResponse response) throws
		   JsonProcessingException, ParseException { 
		   logger.info("Welcome home! The client locale is {}.", locale);

		   String wareHouse = request.getParameter("wareHouse");
		   String siteId = request.getParameter("siteId");
		   Integer accumulated = Integer.parseInt(request.getParameter("accumulated"));
		   Integer monthly = Integer.parseInt(request.getParameter("monthly"));
		   String  profitable = request.getParameter("Profitable");
		   String  loss = request.getParameter("Loss");
		   String  max = request.getParameter("Max");
		   String  min = request.getParameter("Min");
		   String  technologies = request.getParameter("technologies");
		   
		   Map<String, Object> rtn = new LinkedHashMap<>(); 
		   ObjectMapper mapper = new ObjectMapper();
		   
		   Object startDate = request.getParameter("startDate");
		   SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		   Date start = sdformat.parse(startDate.toString());
		   Timestamp StartDate = new Timestamp(start.getTime());
		   Object endDate = request.getParameter("endDate");
		   Date end = sdformat.parse(endDate.toString());
		   Timestamp EndDate = new Timestamp(end.getTime());
		   
		   String query ="";
		   Query q = null;
		   
		   String peakMonthly ="";
		   String peakMinMonthly ="";
		   String peakLossMonthly ="";
		   String peakMinLossMonthly ="";
		   Query peakQuery = null;
		   Query peakLossQuery = null;
		   Query peakMinQuery = null;
		   Query peakMinLossQuery = null;


		   Session session = almsessions.getSession();
		   Transaction tx = session.beginTransaction();
		   
		   if (siteId != "") {
			   List<Object[]> siteList = (List<Object[]>) session.createSQLQuery(
					   "SELECT SITE,WARE_NAME,SITE_ID,LATITUDE,LONGITUDE from WAREHOUSE where WARE_ID ='"+wareHouse+"' ").list();
			   
			   List<Object[]> siteResult = new ArrayList<Object[]>();
			   
			   if (siteList != null) {
				   for (Object[] obj : siteList) {
							
							if (Integer.parseInt(String.valueOf((obj[0]))) == 1) {
								System.out.println("WAREHOUSE is also a SITE");
								siteResult.add(obj);
							}			  
					}
					 System.out.println("Selected Site List:" +mapper.writeValueAsString(siteResult)); 
					 rtn.put("listSites2", siteResult); 
				  }
				  
				    
				  }
		   

		   else if (siteId == "" ) {
			   
			   if(monthly ==1) {
				   System.out.println("<--------------Monthly CONDITION-------------->");
				   
				   
				   if(profitable == null && loss == null && max == null && min == null && (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")){
					   System.out.println("<--------------Only Monthly is Checked-------------->");
					 
					  
					   query = "select * from (SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE, "
								+"b.ProfitLossSum from warehouse a right join "
								+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
								+" from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
								+"and end_date <= :param2 group by site_id) b "
								+"on a.site_id = b.site_id  order by ProfitLossSum) ";
					   
					   peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
								+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
								" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
						        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
								"  GROUP BY a.WARE_NAME)) ";
					   
					   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
								+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
								" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
						        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
								"  GROUP BY a.WARE_NAME)) ";
						 
					   
						  peakQuery=session.createSQLQuery(peakMonthly);
						  peakQuery.setParameter("param1", StartDate);
						  peakQuery.setParameter("param2", EndDate);   
						    
						    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
							rtn.put("peakListSites", peakQuery.list());
							
							
							peakLossQuery=session.createSQLQuery(peakLossMonthly);
							peakLossQuery.setParameter("param1", StartDate);
							peakLossQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
								rtn.put("peakMaxLossList", peakLossQuery.list());
						  
						 
			    	 
								   
								  q = session.createSQLQuery(query);
								  q.setParameter("param1", StartDate);
								  q.setParameter("param2", EndDate);
								    
								  List<Object[]> siteList = (List<Object[]>) (q).list();				
								  List<Object[]> siteResult = new ArrayList<Object[]>();
								  
								  if (siteList != null) {
									  for (Object[] obj : siteList) {
										
											  siteResult.add(obj);
											 
										  } 
										  System.out.println("Site List:" +mapper.writeValueAsString(siteResult)); 
										  rtn.put("listSites", siteResult);
								  }
								  
				   } // End All Null conditions
				   
				 
				   if(StringUtils.equalsIgnoreCase(max,"max") && profitable == null && loss == null && min ==null)
					{
					   if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") {
						   
						   
						   query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
									+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
							        " where b.profit_and_loss IN(select max(profit_and_loss) "
							        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
							        + " and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
							
							
							peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
							
							peakQuery=session.createSQLQuery(peakMonthly);
							  peakQuery.setParameter("param1", StartDate);
							  peakQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
								rtn.put("peakListSites", peakQuery.list());
								
								
						   
					   } // end Max with Null Tech
					   
						if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								
								
								 query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
											+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
									        " where b.profit_and_loss IN(select max(profit_and_loss) "
									        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
									        + " AND tech_2g =1 and tech_3g=0 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
									
									
									peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g =1 and b.tech_3g=0 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									
									peakQuery=session.createSQLQuery(peakMonthly);
									  peakQuery.setParameter("param1", StartDate);
									  peakQuery.setParameter("param2", EndDate);  
									  
										rtn.put("peakListSites", peakQuery.list());

								/*
								query = "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM(SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,"+
										" a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " +
										" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
										" WHERE a.site=1 AND b.PROFIT_AND_LOSS IN (select max(DISTINCT b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b where b.START_DATE >= :param1  AND b.END_DATE <= :param2 "+
										" AND b.tech_2g =1 and b.tech_3g=0 and b.tech_4g=0 "+
										"GROUP BY b.start_date)) ";
										*/
								
							}// end max with 2g condition
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select max(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND tech_2g =1 and tech_3g=1 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g =1 and b.tech_3g=1 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								
								peakQuery=session.createSQLQuery(peakMonthly);
								  peakQuery.setParameter("param1", StartDate);
								  peakQuery.setParameter("param2", EndDate);  
								  
									rtn.put("peakListSites", peakQuery.list());
								
							}// end max with 2g3g condition
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select max(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND tech_2g =1 and tech_3g=1 and tech_4g=1 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g =1 and b.tech_3g=1 and b.tech_4g=1 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								
								peakQuery=session.createSQLQuery(peakMonthly);
								  peakQuery.setParameter("param1", StartDate);
								  peakQuery.setParameter("param2", EndDate);  
								  
									rtn.put("peakListSites", peakQuery.list());
								
							}// end max with 2g3g4g condition
							
					} // end max with tech condition
						q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);
					    
						System.out.println("HELLO From MAX-TECH COND! Site List:" +mapper.writeValueAsString(q.list())); 
						rtn.put("listSites", q.list());

				}// End max condition
				   
				   if(profitable == null && loss == null && StringUtils.equalsIgnoreCase(min,"Min") ) {
					   
					   if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") {
						   
						   
						   
							query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
									+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
							        " where b.profit_and_loss IN(select min(profit_and_loss) "
							        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
							        + " and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
							
							
							peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
							
							  peakMinQuery=session.createSQLQuery(peakMinMonthly);
							  peakMinQuery.setParameter("param1", StartDate);
							  peakMinQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
								rtn.put("peakMinProfitList", peakMinQuery.list());
							  
						   /*
						   query = "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM(SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,"+
								   " a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " +
									" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
									" WHERE a.site=1 AND b.PROFIT_AND_LOSS IN (select MIN(DISTINCT b.PROFIT_AND_LOSS)as minProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b where b.START_DATE >= :param1  AND b.END_DATE <= :param2 "+
							   		"GROUP BY b.start_date)) ";
							   		*/
					   }// end min with null tech
					   
					   if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
						   
						   if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							 {		
								 query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
											+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
									        " where b.profit_and_loss IN(select min(profit_and_loss) "
									        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
									        + " AND tech_2g =1 and tech_3g=0 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
									
								 
								 peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2   AND tech_2g =1 and tech_3g=0 and tech_4g=0 and b.profit_and_loss >=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									
									 
						
							 } // end 2G condition	
						   
						   
						   if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							 {		
							   query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select min(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND tech_2g =1 and tech_3g=1 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
							   peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g =1 and b.tech_3g=1 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							 
								
						
							 } // end 2G3G condition
						   
						   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							 {		
							   query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select min(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND tech_2g =1 and tech_3g=1 and tech_4g=1 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
							 
							 peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND tech_2g =1 and tech_3g=1 and tech_4g=1 and b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								
							 } // end 2G3G4G condition	
						   
						}// end min with TECH condition
					 	q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);
					    System.out.println("HeLLo From MIN-TECH CONDITION, The Site List:" +mapper.writeValueAsString(q.list())); 
						rtn.put("listSites", q.list());
						
						 peakMinQuery=session.createSQLQuery(peakMinMonthly);
						  peakMinQuery.setParameter("param1", StartDate);
						  peakMinQuery.setParameter("param2", EndDate);   
						    
						    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
							rtn.put("peakMinProfitList", peakMinQuery.list());
					   
				   }// end MIN condition
				   
				   if(StringUtils.equalsIgnoreCase(loss,"Loss") && profitable ==null){
					   if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") ) {
						  /*
						   query = "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM(SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,"+
								   " a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " +
							        " FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
									" WHERE a.site=1 AND b.START_DATE >= :param1  AND b.END_DATE <= :param2  AND b.profit_and_loss <=0 )";
						*/
						   
						   query = "select * from (SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE,"+
								   " b.ProfitLossSum from warehouse a right join (select SITE_ID,SUM(profit_and_loss) as ProfitLossSum " +
							        " from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 and profit_and_loss <=0 group by site_id) b "+
									" on a.site_id = b.site_id  order by ProfitLossSum desc) ";
						   
						   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
									"  GROUP BY a.WARE_NAME)) ";
							 
						   
							
								peakLossQuery=session.createSQLQuery(peakLossMonthly);
								peakLossQuery.setParameter("param1", StartDate);
								peakLossQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
									rtn.put("peakMaxLossList", peakLossQuery.list());
						
					   } // end all null conditions
						if(StringUtils.equalsIgnoreCase(max,"max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null")) {
						/*
							query = "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,PROFIT_AND_LOSS FROM( SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,"+
									"a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),b.PROFIT_AND_LOSS " +
							        " FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
									" WHERE a.site=1 AND b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b where b.START_DATE >= :param1  AND b.END_DATE <= :param2 "+
							        " AND b.profit_and_loss <=0 "+
									"GROUP BY b.start_date))";
									*/
							
							query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
									+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
							        " where b.profit_and_loss IN(select max(profit_and_loss) "
							        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
							        + " and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
							
							
							 peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								 
							   
								
									peakLossQuery=session.createSQLQuery(peakLossMonthly);
									peakLossQuery.setParameter("param1", StartDate);
									peakLossQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
										rtn.put("peakMaxLossList", peakLossQuery.list());
						   
						}// end max-loss condition

						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null")) {
							/*
							query = "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,PROFIT_AND_LOSS FROM(SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID, "+
									" a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),b.PROFIT_AND_LOSS " +
							        " FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
									" WHERE a.site=1 AND b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as minProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b where b.START_DATE >= :param1  AND b.END_DATE <= :param2 "+
							        " AND b.profit_and_loss <=0 "+
									"GROUP BY b.start_date))";
									*/
							
							query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
									+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
							        " where b.profit_and_loss IN(select min(profit_and_loss) "
							        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
							        + " and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
							
							
							 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								 
							   
								
									peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
									peakMinLossQuery.setParameter("param1", StartDate);
									peakMinLossQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
										rtn.put("peakMinLossList", peakMinLossQuery.list());
						   
					
						}// end min-loss condition
						
						if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && min == null && max == null) {
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								
								
								/*
								
								 query ="SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM( SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,"+
										 "a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " +
									        " FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
											" WHERE a.site=1 AND b.START_DATE >= :param1  AND b.END_DATE <= :param2  AND b.profit_and_loss <=0 "+
									        " AND b.tech_2g=1 and b.tech_3g=0 and b.tech_4g=0) ";
									        */
								 query = "select * from (SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE,"+
										   " b.ProfitLossSum from warehouse a right join (select SITE_ID,SUM(profit_and_loss) as ProfitLossSum " +
									        " from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=0 and tech_4g=0 and profit_and_loss <=0 group by site_id) b "+
											" on a.site_id = b.site_id  order by ProfitLossSum desc) ";
								   
								   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=0 and tech_4g=0 AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								   
									
										peakLossQuery=session.createSQLQuery(peakLossMonthly);
										peakLossQuery.setParameter("param1", StartDate);
										peakLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
											rtn.put("peakMaxLossList", peakLossQuery.list());
							
								
							}// end loss-2g
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								 query = "select * from (SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE,"+
										   " b.ProfitLossSum from warehouse a right join (select SITE_ID,SUM(profit_and_loss) as ProfitLossSum " +
									        " from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=0 and profit_and_loss <=0 group by site_id) b "+
											" on a.site_id = b.site_id  order by ProfitLossSum desc) ";
								   
								   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=0 AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									peakLossQuery=session.createSQLQuery(peakLossMonthly);
									peakLossQuery.setParameter("param1", StartDate);
									peakLossQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
										rtn.put("peakMaxLossList", peakLossQuery.list());
						
							
									 
							}// end loss-2g3g
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								query = "select * from (SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE,"+
										   " b.ProfitLossSum from warehouse a right join (select SITE_ID,SUM(profit_and_loss) as ProfitLossSum " +
									        " from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=1 and profit_and_loss <=0 group by site_id) b "+
											" on a.site_id = b.site_id  order by ProfitLossSum desc) ";
								   
								   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=1 AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									peakLossQuery=session.createSQLQuery(peakLossMonthly);
									peakLossQuery.setParameter("param1", StartDate);
									peakLossQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
										rtn.put("peakMaxLossList", peakLossQuery.list());
						
							
							}// end loss-2g3g4g
							
							
							
						}// end loss-tech condition
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
							
								/*
								query = "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM( SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,"+
										"a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " +
								        " FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
										" WHERE a.site=1 AND b.PROFIT_AND_LOSS IN (select min(DISTINCT b.PROFIT_AND_LOSS)as minProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b where b.START_DATE >= :param1  AND b.END_DATE <= :param2 "+
								        " AND b.profit_and_loss <=0 AND b.tech_2g=1 and b.tech_3g=0 and b.tech_4g=0 "+
										"GROUP BY b.start_date))";
							*/
								
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select min(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND tech_2g=1 and tech_3g=0 and tech_4g=0  and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=0 and b.tech_4g=0 AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								   
									
										peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
										peakMinLossQuery.setParameter("param1", StartDate);
										peakMinLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
											rtn.put("peakMinLossList", peakMinLossQuery.list());
							   
							}// end loss-min-2g
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select min(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND tech_2g=1 and tech_3g=1 and tech_4g=0  and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=0 AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								   
									
										peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
										peakMinLossQuery.setParameter("param1", StartDate);
										peakMinLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
											rtn.put("peakMinLossList", peakMinLossQuery.list());
						
							}// end loss-min-2g3g
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select min(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=1  and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=1 AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								   
									
										peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
										peakMinLossQuery.setParameter("param1", StartDate);
										peakMinLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
											rtn.put("peakMinLossList", peakMinLossQuery.list());
							
							}// end loss-min-2g3g4g
							
							
						}// end loss-min-tech condition
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								/*
								query =  "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM("+
										 "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM( SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,"+
										" a.LATITUDE,a.LONGITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " + 
										" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
										" WHERE a.site=1 AND b.profit_and_loss IN (select MAX(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss <=0 " + 
										" AND b.tech_2g= 1 and b.tech_3g= 0 and b.tech_4g= 0 " + 
										" GROUP BY b.start_date))"+
										" UNION "+
										 "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM( SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,"+
										" a.LATITUDE,a.LONGITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " + 
										" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
										" WHERE b.profit_and_loss IN (select MIN(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss <=0 " + 
										" AND b.tech_2g= 1 and b.tech_3g= 0 and b.tech_4g= 0 " + 
										" GROUP BY b.start_date,b.profit_and_loss)))";
										*/
								query =" select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
										"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
										"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
										"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=0 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
										"UNION \r\n" + 
										"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
										"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select MAX(profit_and_loss)\r\n" + 
										"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=0 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
										")\r\n" + 
										"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE\r\n" 
										;
						 
								
								 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=0 and b.tech_4g=0 and b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								   
									
										peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
										peakMinLossQuery.setParameter("param1", StartDate);
										peakMinLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
											rtn.put("peakMinLossList", peakMinLossQuery.list());
											
											 peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
														+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
														" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
												        " where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=0 and tech_4g=0 AND b.profit_and_loss <=0 "+
														"  GROUP BY a.WARE_NAME)) ";
												 
											   
												
													peakLossQuery=session.createSQLQuery(peakLossMonthly);
													peakLossQuery.setParameter("param1", StartDate);
													peakLossQuery.setParameter("param2", EndDate);   
													    
													    System.out.println("PEAK LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
														rtn.put("peakMaxLossList", peakLossQuery.list());
								
								
								
						
							}//end loss-min-max-2g
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								query =" select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
										"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
										"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
										"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
										"UNION \r\n" + 
										"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
										"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select MAX(profit_and_loss)\r\n" + 
										"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
										")\r\n" + 
										"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE\r\n" 
										;
						 
								
								 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=0 and b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								   
									
										peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
										peakMinLossQuery.setParameter("param1", StartDate);
										peakMinLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
											rtn.put("peakMinLossList", peakMinLossQuery.list());
											
											 peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
														+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
														" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
												        " where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=0 AND b.profit_and_loss <=0 "+
														"  GROUP BY a.WARE_NAME)) ";
												 
											   
												
													peakLossQuery=session.createSQLQuery(peakLossMonthly);
													peakLossQuery.setParameter("param1", StartDate);
													peakLossQuery.setParameter("param2", EndDate);   
													    
													    System.out.println("PEAK LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
														rtn.put("peakMaxLossList", peakLossQuery.list());
								
								
								
							}//end loss-min-max-2g3g
							
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								query =" select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
										"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
										"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
										"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=1 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
										"UNION \r\n" + 
										"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
										"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select MAX(profit_and_loss)\r\n" + 
										"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=1 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
										")\r\n" + 
										"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE\r\n" 
										;
						 
								
								 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=1 and b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								   
									
										peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
										peakMinLossQuery.setParameter("param1", StartDate);
										peakMinLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
											rtn.put("peakMinLossList", peakMinLossQuery.list());
											
											 peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
														+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
														" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
												        " where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=1 AND b.profit_and_loss <=0 "+
														"  GROUP BY a.WARE_NAME)) ";
												 
											   
												
													peakLossQuery=session.createSQLQuery(peakLossMonthly);
													peakLossQuery.setParameter("param1", StartDate);
													peakLossQuery.setParameter("param2", EndDate);   
													    
													    System.out.println("PEAK LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
														rtn.put("peakMaxLossList", peakLossQuery.list());
								
								
								
							}//end loss-min-max-2g3g4g
							
						}// end loss-min-max-tech
						
						if(StringUtils.equalsIgnoreCase(max,"max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							/*
							query =  "SELECT DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM("+
									 "SELECT DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM( SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,"+
									" a.LATITUDE,a.LONGITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),a.SITE " + 
									" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
									" WHERE a.site=1 and b.profit_and_loss IN (select MIN(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss <=0 " + 
									" GROUP BY b.start_date))"+
									" UNION "+
									 "SELECT  DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM( SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,"+
									" a.LATITUDE,a.LONGITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),a.site " + 
									" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
									" WHERE a.site=1 and b.profit_and_loss IN (select MAX(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss <=0 " + 
									" GROUP BY b.start_date,b.profit_and_loss)))";
									*/
							
							query =" select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
									"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
									"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
									"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
									"UNION \r\n" + 
									"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
									"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select MAX(profit_and_loss)\r\n" + 
									"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
									")\r\n" + 
									"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE\r\n" 
									;
					 
							
							 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								 
							   
								
									peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
									peakMinLossQuery.setParameter("param1", StartDate);
									peakMinLossQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
										rtn.put("peakMinLossList", peakMinLossQuery.list());
										
										 peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
													+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
													" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
											        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
													"  GROUP BY a.WARE_NAME)) ";
											 
										   
											
												peakLossQuery=session.createSQLQuery(peakLossMonthly);
												peakLossQuery.setParameter("param1", StartDate);
												peakLossQuery.setParameter("param2", EndDate);   
												    
												    System.out.println("PEAK LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
													rtn.put("peakMaxLossList", peakLossQuery.list());
				
						} // end loss-max-min
						
						if(StringUtils.equalsIgnoreCase(max,"max") && min == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select max(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND tech_2g=1 and tech_3g=0 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								 peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=0 and b.tech_4g=0 AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								   
									
										peakLossQuery=session.createSQLQuery(peakLossMonthly);
										peakLossQuery.setParameter("param1", StartDate);
										peakLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
											rtn.put("peakMaxLossList", peakLossQuery.list());
											
								
								
							}// end max-tech with 2g
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select max(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND tech_2g=1 and tech_3g=1 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								 peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=0 AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								   
									
										peakLossQuery=session.createSQLQuery(peakLossMonthly);
										peakLossQuery.setParameter("param1", StartDate);
										peakLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
											rtn.put("peakMaxLossList", peakLossQuery.list());
											
							
							}// end max-tech with 2g3g
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select max(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND tech_2g=1 and tech_3g=1 and tech_4g=1 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								 peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=1 AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								   
									
										peakLossQuery=session.createSQLQuery(peakLossMonthly);
										peakLossQuery.setParameter("param1", StartDate);
										peakLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
											rtn.put("peakMaxLossList", peakLossQuery.list());
											
							
							}// end max-tech with 2g3g4g
							
						}//End max-tech with null MIN
						q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);   
					    
					    System.out.println("HeLLo From LOSS CONDITION, The Site List:" +mapper.writeValueAsString(q.list())); 
						  rtn.put("listSites", q.list());
				   }// end loss condition
						
				   if(StringUtils.equalsIgnoreCase(profitable,"Profitable") && loss==null)
					{
						if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
						{
							/*
							query = "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM("
									+ "SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " +
							        " FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
									" WHERE a.site=1 AND b.START_DATE >= :param1  AND b.END_DATE <= :param2  AND b.profit_and_loss >=0) ";
							*/
							
							query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE,"
									+ "b.ProfitLossSum from warehouse a right join (select SITE_ID,SUM(profit_and_loss) as ProfitLossSum " +
							        " from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 and profit_and_loss >=0 group by site_id) b "+
									" on a.site_id = b.site_id  order by ProfitLossSum ASC ";
							
							
					peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
					peakQuery=session.createSQLQuery(peakMonthly);
					  peakQuery.setParameter("param1", StartDate);
					  peakQuery.setParameter("param2", EndDate);   
					    
					    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
						rtn.put("peakListSites", peakQuery.list());
					  
					  
							
							 
							
							
						} // end all null conditions with prof
						
						if(StringUtils.equalsIgnoreCase(max,"max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							/*
							query = "SELECT * FROM(SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, " + 
									"b.ProfitLossSum FROM WAREHOUSE a right join " + 
									"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum " + 
									"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 " + 
									"and end_date <= :param2 group by site_id) b " + 
									"on a.site_id = b.site_id order by ProfitLossSum DESC) " + 
									"WHERE ProfitLossSum >=0 ";
							
							query = "SELECT * FROM("
									+ " SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),b.PROFIT_AND_LOSS " +
							        " FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
									" WHERE a.site=1 AND b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b where b.START_DATE >= :param1  AND b.END_DATE <= :param2 "+
							        " AND b.profit_and_loss >=0 "+
									"GROUP BY b.start_date))";
									*/
							
							query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
									+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
							        " where b.profit_and_loss IN(select max(profit_and_loss) "
							        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
							        + " and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
							
							
							peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
							
							peakQuery=session.createSQLQuery(peakMonthly);
							  peakQuery.setParameter("param1", StartDate);
							  peakQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
								rtn.put("peakListSites", peakQuery.list());
							  
							  
							 
							
							
						} // end prof-max condition
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
							
							/*
							query = "SELECT * FROM(SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, " + 
									"b.ProfitLossSum FROM WAREHOUSE a right join " + 
									"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum " + 
									"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 " + 
									"and end_date <= :param2 group by site_id) b " + 
									"on a.site_id = b.site_id order by ProfitLossSum ASC) " + 
									"WHERE ProfitLossSum >=0 ";
							*/
							
							query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
									+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
							        " where b.profit_and_loss IN(select min(profit_and_loss) "
							        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
							        + " and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
							
							
							peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
							
							  peakMinQuery=session.createSQLQuery(peakMinMonthly);
							  peakMinQuery.setParameter("param1", StartDate);
							  peakMinQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
								rtn.put("peakMinProfitList", peakMinQuery.list());
							  
							 
						} // end prof-min condition
						
						if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && min == null && max == null)
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								/*
								query ="SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM("+
										" SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " +
								        " FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
										" WHERE a.site=1 and b.START_DATE >= :param1  AND b.END_DATE <= :param2  AND b.profit_and_loss >=0 "+
								        " AND b.tech_2g=1 and b.tech_3g=0 and b.tech_4g=0) ";
								        
								        */
								query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE,"
										+ "b.ProfitLossSum from warehouse a right join (select SITE_ID,SUM(profit_and_loss) as ProfitLossSum " +
								        " from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=0 and tech_4g=0 and profit_and_loss >=0 group by site_id) b "+
										" on a.site_id = b.site_id  order by ProfitLossSum ASC ";
								
								
						peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g=1 and b.tech_3g=0 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
						peakQuery=session.createSQLQuery(peakMonthly);
						  peakQuery.setParameter("param1", StartDate);
						  peakQuery.setParameter("param2", EndDate);   
						    
						    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
							rtn.put("peakListSites", peakQuery.list());
						  
						  
							}//prof-2g cond
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE,"
										+ "b.ProfitLossSum from warehouse a right join (select SITE_ID,SUM(profit_and_loss) as ProfitLossSum " +
								        " from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=0 and profit_and_loss >=0 group by site_id) b "+
										" on a.site_id = b.site_id  order by ProfitLossSum ASC ";
								
								
						peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
						peakQuery=session.createSQLQuery(peakMonthly);
						  peakQuery.setParameter("param1", StartDate);
						  peakQuery.setParameter("param2", EndDate);   
						    
						    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
							rtn.put("peakListSites", peakQuery.list());
						  
								
							}//prof-2g3g cond
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE,"
										+ "b.ProfitLossSum from warehouse a right join (select SITE_ID,SUM(profit_and_loss) as ProfitLossSum " +
								        " from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=1 and profit_and_loss >=0 group by site_id) b "+
										" on a.site_id = b.site_id  order by ProfitLossSum ASC ";
								
								
						peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=1 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
						peakQuery=session.createSQLQuery(peakMonthly);
						  peakQuery.setParameter("param1", StartDate);
						  peakQuery.setParameter("param2", EndDate);   
						    
						    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
							rtn.put("peakListSites", peakQuery.list());
						  
							 
							}//prof-2g3g4g cond
							
						}//prof-tech condition
						
						if(StringUtils.equalsIgnoreCase(max,"max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null"))
						{
						/*
							
							query =  "SELECT DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM("+
									 "SELECT DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM( SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,"+
									" a.LATITUDE,a.LONGITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),a.SITE " + 
									" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
									" WHERE a.site=1 and b.profit_and_loss IN (select MIN(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss >=0 " + 
									" GROUP BY b.start_date))"+
									" UNION "+
									 "SELECT  DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM( SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,"+
									" a.LATITUDE,a.LONGITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),a.site " + 
									" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
									" WHERE a.site=1 and b.profit_and_loss IN (select MAX(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss >=0 " + 
									" GROUP BY b.start_date)))";
						
							query =	"SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
									+"b.ProfitLossSum FROM WAREHOUSE a right join "
									+"((select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum  "
									+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum DESC)"
									+"where ProfitLossSum >=0) UNION (select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum ASC)  "
									+" where ProfitLossSum >=0 ))  b on a.site_id = b.site_id";    
									*/
							
							
									
									query =" select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
											"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
											"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
											"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
											"UNION \r\n" + 
											"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
											"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select MAX(profit_and_loss)\r\n" + 
											"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
											")\r\n" + 
											"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE\r\n" 
											;
							 
							
							
							peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
							
							peakQuery=session.createSQLQuery(peakMonthly);
							  peakQuery.setParameter("param1", StartDate);
							  peakQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
								rtn.put("peakListSites", peakQuery.list());
								
								peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								
								  peakMinQuery=session.createSQLQuery(peakMinMonthly);
								  peakMinQuery.setParameter("param1", StartDate);
								  peakMinQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
									rtn.put("peakMinProfitList", peakMinQuery.list());
							  
						
						}// end prof-max-min
						if(StringUtils.equalsIgnoreCase(max,"max") && min == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								/*
								query = "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM("+
										"SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " + 
										" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max(DISTINCT b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b where b.START_DATE >= :param1  AND b.END_DATE <= :param2" + 
										" AND b.profit_and_loss >=0   AND b.tech_2g=1 and b.tech_3g=0 and b.tech_4g=0 " + 
										" GROUP BY b.start_date))";
										*/
								
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select max(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND tech_2g=1 and tech_3g=0 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=0 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								
								peakQuery=session.createSQLQuery(peakMonthly);
								  peakQuery.setParameter("param1", StartDate);
								  peakQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
									rtn.put("peakListSites", peakQuery.list());
								  
								  
								 
								
							}// end 2g condition
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select max(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND tech_2g=1 and tech_3g=1 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								
								peakQuery=session.createSQLQuery(peakMonthly);
								  peakQuery.setParameter("param1", StartDate);
								  peakQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
									rtn.put("peakListSites", peakQuery.list());
								  
								  
								 
							
							}// end 2g3g condition
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select max(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
								        + " AND tech_2g=1 and tech_3g=1 and tech_4g=1 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=1 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								
								peakQuery=session.createSQLQuery(peakMonthly);
								  peakQuery.setParameter("param1", StartDate);
								  peakQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
									rtn.put("peakListSites", peakQuery.list());
								  
								  
								 
						
							}// end 2g3g4g condition
							
						}// end prof-max-tech
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g"))
							{
								/*
								query = "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM("+
										" SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " +
								        " FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
										" WHERE a.site=1 and  b.PROFIT_AND_LOSS IN (select min(DISTINCT b.PROFIT_AND_LOSS)as minProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b where b.START_DATE >= :param1  AND b.END_DATE <= :param2 "+
								        " AND b.profit_and_loss  >=0 AND b.tech_2g=1 and b.tech_3g=0 and b.tech_4g=0 "+
										"GROUP BY b.start_date))";
										*/
								
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select min(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=0 and tech_4g=0 "
								        + " and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=0 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								
								  peakMinQuery=session.createSQLQuery(peakMinMonthly);
								  peakMinQuery.setParameter("param1", StartDate);
								  peakMinQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
									rtn.put("peakMinProfitList", peakMinQuery.list());
								  
							
							}// end 2g condition
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
							{
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select min(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=0 "
								        + " and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								
								  peakMinQuery=session.createSQLQuery(peakMinMonthly);
								  peakMinQuery.setParameter("param1", StartDate);
								  peakMinQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
									rtn.put("peakMinProfitList", peakMinQuery.list());
						
							}// end 2g3g condition
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
							{
								query = " select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE, a.SITE, "
										+ " SUM(b.profit_and_loss ) FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID " +
								        " where b.profit_and_loss IN(select min(profit_and_loss) "
								        + " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g=1 and tech_3g=1 and tech_4g=1 "
								        + " and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) GROUP by a.site_id, a.ware_name,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE ";
								
								
								peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g=1 and b.tech_3g=1 and b.tech_4g=1 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								
								  peakMinQuery=session.createSQLQuery(peakMinMonthly);
								  peakMinQuery.setParameter("param1", StartDate);
								  peakMinQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
									rtn.put("peakMinProfitList", peakMinQuery.list());
							
							}// end 2g3g4g condition
							
						}// end prof-min-tech condition
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								/*
								query = "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM("+
										"SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM("+
										" SELECT DISTINCT a.SITE,a.WARE_ID,a.WARE_NAME,a.LONGITUDE,a.LATITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " + 
										" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
										" WHERE a.site=1 and b.profit_and_loss IN (select min(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss >=0 " + 
										" AND b.tech_2g= 1 and b.tech_3g= 0 and b.tech_4g= 0 " + 
										" GROUP BY b.start_date))"+
										" UNION "+
										"SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM("+
										" SELECT DISTINCT a.SITE,a.WARE_ID,a.WARE_NAME,a.LONGITUDE,a.LATITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " + 
										" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
										" WHERE a.site=1 and b.profit_and_loss IN (select max(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss >=0 " + 
										" AND b.tech_2g= 1 and b.tech_3g= 0 and b.tech_4g= 0 " + 
										" GROUP BY b.start_date,b.profit_and_loss)))";
								*/
								
								query =" select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
										"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
										"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
										"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g= 0 and tech_4g= 0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
										"UNION \r\n" + 
										"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
										"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select MAX(profit_and_loss)\r\n" + 
										"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g= 0 and tech_4g= 0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
										")\r\n" + 
										"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE\r\n" 
										;
						 
						
						
						peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
								+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
								" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
						        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g= 0 and b.tech_4g= 0 AND b.profit_and_loss >=0 "+
								"  GROUP BY a.WARE_NAME)) ";
						
						peakQuery=session.createSQLQuery(peakMonthly);
						  peakQuery.setParameter("param1", StartDate);
						  peakQuery.setParameter("param2", EndDate);   
						    
						    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
							rtn.put("peakListSites", peakQuery.list());
							
							peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g= 0 and b.tech_4g= 0 AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
							
							  peakMinQuery=session.createSQLQuery(peakMinMonthly);
							  peakMinQuery.setParameter("param1", StartDate);
							  peakMinQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
								rtn.put("peakMinProfitList", peakMinQuery.list());
						  
								
							}//end loss-min-max-2g
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								query =" select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
										"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
										"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
										"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g= 0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
										"UNION \r\n" + 
										"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
										"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select MAX(profit_and_loss)\r\n" + 
										"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g= 0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
										")\r\n" + 
										"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE\r\n" 
										;
						 
						
						
						peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
								+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
								" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
						        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g= 0 AND b.profit_and_loss >=0 "+
								"  GROUP BY a.WARE_NAME)) ";
						
						peakQuery=session.createSQLQuery(peakMonthly);
						  peakQuery.setParameter("param1", StartDate);
						  peakQuery.setParameter("param2", EndDate);   
						    
						    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
							rtn.put("peakListSites", peakQuery.list());
							
							peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g= 0 AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
							
							  peakMinQuery=session.createSQLQuery(peakMinMonthly);
							  peakMinQuery.setParameter("param1", StartDate);
							  peakMinQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
								rtn.put("peakMinProfitList", peakMinQuery.list());
						  
							}//end loss-min-max-2g3g
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								query =" select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
										"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
										"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
										"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=1 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
										"UNION \r\n" + 
										"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
										"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select MAX(profit_and_loss)\r\n" + 
										"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=1 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
										")\r\n" + 
										"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE\r\n" 
										;
						 
						
						
						peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
								+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
								" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
						        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=1 AND b.profit_and_loss >=0 "+
								"  GROUP BY a.WARE_NAME)) ";
						
						peakQuery=session.createSQLQuery(peakMonthly);
						  peakQuery.setParameter("param1", StartDate);
						  peakQuery.setParameter("param2", EndDate);   
						    
						    System.out.println("PEAK MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
							rtn.put("peakListSites", peakQuery.list());
							
							peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=1 AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
							
							  peakMinQuery=session.createSQLQuery(peakMinMonthly);
							  peakMinQuery.setParameter("param1", StartDate);
							  peakMinQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
								rtn.put("peakMinProfitList", peakMinQuery.list());
						  
							
								
							}//end loss-min-max-2g3g4g
						}//end min-max-tech condition
						q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);   
					    
					    System.out.println("HeLLo From PROFITABLE CONDITION, The Site List:" +mapper.writeValueAsString(q.list())); 
						  rtn.put("listSites", q.list());
						  
						  
					} // end profitable condition
				   
				   
				   if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g")||StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && profitable == null && loss == null && min == null && max == null)
					{
					   if(StringUtils.equalsIgnoreCase(technologies,"2g"))
						{
						   query = "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM("+
								   " SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " +
							        " FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
									" WHERE a.site=1 and b.START_DATE >= :param1  AND b.END_DATE <= :param2  AND b.tech_2g= 1 and b.tech_3g= 0 and b.tech_4g= 0 )";
						  
						}//end 2g condition
					   
					   if(StringUtils.equalsIgnoreCase(technologies,"2g3g"))
						{
						   query ="SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM("+
								   " SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " +
							        " FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
									" WHERE a.site=1 and b.START_DATE >= :param1  AND b.END_DATE <= :param2  AND b.tech_2g= 1 and b.tech_3g= 1 and b.tech_4g= 0 )";
							
						}//end 2g3g condition
					   
					   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))
						{
						   query = "SELECT DISTINCT SITE,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM("+
								   " SELECT DISTINCT a.SITE,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') " +
							        " FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "+
									" WHERE a.site=1 and b.START_DATE >= :param1  AND b.END_DATE <= :param2  AND b.tech_2g= 1 and b.tech_3g= 1 and b.tech_4g=1) ";
							
						}//end 2g3g4g condition
					   q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);   
					    
					    System.out.println("HeLLo From TECH CONDITION, The Site List:" +mapper.writeValueAsString(q.list())); 
						  rtn.put("listSites", q.list()); 
					}
				   
				   
				   if(StringUtils.equalsIgnoreCase(loss,"Loss") && (StringUtils.equalsIgnoreCase(profitable,"Profitable")) ) {
					   
					   
					   if(max==null && min==null && StringUtils.equalsIgnoreCase(technologies,"null")) {
							query =	"SELECT * FROM(SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
									+" b.ProfitLossSum FROM WAREHOUSE a right join (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id) b "
									+" on a.site_id = b.site_id order by ProfitLossSum)";
							
							 peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							 
							 
							  peakQuery=session.createSQLQuery(peakMonthly);
							  peakQuery.setParameter("param1", StartDate);
							  peakQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
								rtn.put("peakListSites", peakQuery.list());
								
							   
							   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								 
							  
									
									peakLossQuery=session.createSQLQuery(peakLossMonthly);
									peakLossQuery.setParameter("param1", StartDate);
									peakLossQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
										rtn.put("peakMaxLossList", peakLossQuery.list());
							
								  
						} 
					   
					   if(max==null && min==null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
							query =	"SELECT * FROM(SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
									+" b.ProfitLossSum FROM WAREHOUSE a right join (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2  AND tech_2g= 1 and tech_3g= 0 and tech_4g=0 group by site_id) b "
									+" on a.site_id = b.site_id order by ProfitLossSum)";
							
							 peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g= 0 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							 
							 
							  peakQuery=session.createSQLQuery(peakMonthly);
							  peakQuery.setParameter("param1", StartDate);
							  peakQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
								rtn.put("peakListSites", peakQuery.list());
								
							   
							   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g= 0 and b.tech_4g=0 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								 
							  
									
									peakLossQuery=session.createSQLQuery(peakLossMonthly);
									peakLossQuery.setParameter("param1", StartDate);
									peakLossQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
										rtn.put("peakMaxLossList", peakLossQuery.list());
							}
						
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								query =	"SELECT * FROM(SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
										+" b.ProfitLossSum FROM WAREHOUSE a right join (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2  AND tech_2g= 1 and tech_3g= 1 and tech_4g=0 group by site_id) b "
										+" on a.site_id = b.site_id order by ProfitLossSum)";
								
								 peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g= 1 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
											"  GROUP BY a.WARE_NAME)) ";
								 
								 
								  peakQuery=session.createSQLQuery(peakMonthly);
								  peakQuery.setParameter("param1", StartDate);
								  peakQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
									rtn.put("peakListSites", peakQuery.list());
									
								   
								   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=0  AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								  
										
										peakLossQuery=session.createSQLQuery(peakLossMonthly);
										peakLossQuery.setParameter("param1", StartDate);
										peakLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
											rtn.put("peakMaxLossList", peakLossQuery.list());
								}
							
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								query =	"SELECT * FROM(SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
										+" b.ProfitLossSum FROM WAREHOUSE a right join (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2  AND tech_2g= 1 and tech_3g= 1 and tech_4g=1 group by site_id) b "
										+" on a.site_id = b.site_id order by ProfitLossSum)";
								
								 peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g= 1 and b.tech_4g=1 AND b.profit_and_loss >=0 "+
											"  GROUP BY a.WARE_NAME)) ";
								 
								 
								  peakQuery=session.createSQLQuery(peakMonthly);
								  peakQuery.setParameter("param1", StartDate);
								  peakQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
									rtn.put("peakListSites", peakQuery.list());
									
								   
								   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=1  AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								  
										
										peakLossQuery=session.createSQLQuery(peakLossMonthly);
										peakLossQuery.setParameter("param1", StartDate);
										peakLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
											rtn.put("peakMaxLossList", peakLossQuery.list());
								}
							
							
								  
						} 
					   
					   if(StringUtils.equalsIgnoreCase(max,"max") &&  min==null && StringUtils.equalsIgnoreCase(technologies,"null")) {
							
						   /*
							   query =	
										 "SELECT DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM("+
												 "SELECT DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM( SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,"+
												" a.LATITUDE,a.LONGITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),a.SITE " + 
												" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
												" WHERE a.site=1 and b.profit_and_loss IN (select MAX(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss <=0 " + 
												" GROUP BY b.start_date))"+
												" UNION "+
												 "SELECT  DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM( SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,"+
												" a.LATITUDE,a.LONGITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),a.site " + 
												" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
												" WHERE a.site=1 and b.profit_and_loss IN (select MAX(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss >=0 " + 
												" GROUP BY b.start_date))"
												+ ")";
												*/
						   
						   query = "select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
						   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
						   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select max(profit_and_loss)\r\n" + 
						   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
						   		"UNION \r\n" + 
						   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
						   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select MAX(profit_and_loss)\r\n" + 
						   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
						   		")\r\n" + 
						   		"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE";
												
								
							    System.out.println("HeLLo From MON-PROF-LOSS-MAX CONDITION, The Site List:" ); 
							    
							    
							    peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							   
							   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								 
							   
								  peakQuery=session.createSQLQuery(peakMonthly);
								  peakQuery.setParameter("param1", StartDate);
								  peakQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
									rtn.put("peakListSites", peakQuery.list());
									
									
									peakLossQuery=session.createSQLQuery(peakLossMonthly);
									peakLossQuery.setParameter("param1", StartDate);
									peakLossQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
										rtn.put("peakMaxLossList", peakLossQuery.list());
								  
									
							
							}  
					   
					   if(StringUtils.equalsIgnoreCase(max,"max") &&  min==null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
						   
						   query = "select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
						   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
						   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select max(profit_and_loss)\r\n" + 
						   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2  AND tech_2g= 1 and tech_3g=0 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
						   		"UNION \r\n" + 
						   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
						   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select MAX(profit_and_loss)\r\n" + 
						   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2  AND tech_2g= 1 and tech_3g=0 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
						   		")\r\n" + 
						   		"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE";
												
								
							    System.out.println("HeLLo From MON-PROF-LOSS-MAX CONDITION, The Site List:" ); 
							    
							    
							    peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g=0 and b.tech_4g=0  AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							   
							   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g=0 and b.tech_4g=0  AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								 
							   
								  peakQuery=session.createSQLQuery(peakMonthly);
								  peakQuery.setParameter("param1", StartDate);
								  peakQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
									rtn.put("peakListSites", peakQuery.list());
									
									
									peakLossQuery=session.createSQLQuery(peakLossMonthly);
									peakLossQuery.setParameter("param1", StartDate);
									peakLossQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
										rtn.put("peakMaxLossList", peakLossQuery.list());
								  
							}	
							
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								   
								   query = "select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
								   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
								   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select max(profit_and_loss)\r\n" + 
								   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2  AND tech_2g= 1 and tech_3g=1 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
								   		"UNION \r\n" + 
								   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
								   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select MAX(profit_and_loss)\r\n" + 
								   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2  AND tech_2g= 1 and tech_3g=1 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
								   		")\r\n" + 
								   		"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE";
														
										
									    System.out.println("HeLLo From MON-PROF-LOSS-MAX CONDITION, The Site List:" ); 
									    
									    
									    peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
												+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
												" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
										        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=0  AND b.profit_and_loss >=0 "+
												"  GROUP BY a.WARE_NAME)) ";
									   
									   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
												+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
												" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
										        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=0  AND b.profit_and_loss <=0 "+
												"  GROUP BY a.WARE_NAME)) ";
										 
									   
										  peakQuery=session.createSQLQuery(peakMonthly);
										  peakQuery.setParameter("param1", StartDate);
										  peakQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
											rtn.put("peakListSites", peakQuery.list());
											
											
											peakLossQuery=session.createSQLQuery(peakLossMonthly);
											peakLossQuery.setParameter("param1", StartDate);
											peakLossQuery.setParameter("param2", EndDate);   
											    
											    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
												rtn.put("peakMaxLossList", peakLossQuery.list());
										  
									}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								   
								   query = "select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
								   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
								   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select max(profit_and_loss)\r\n" + 
								   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2  AND tech_2g= 1 and tech_3g=1 and tech_4g=1 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
								   		"UNION \r\n" + 
								   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
								   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select MAX(profit_and_loss)\r\n" + 
								   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2  AND tech_2g= 1 and tech_3g=1 and tech_4g=1 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
								   		")\r\n" + 
								   		"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE";
														
										
									    System.out.println("HeLLo From MON-PROF-LOSS-MAX CONDITION, The Site List:" ); 
									    
									    
									    peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
												+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
												" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
										        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=1  AND b.profit_and_loss >=0 "+
												"  GROUP BY a.WARE_NAME)) ";
									   
									   peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
												+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
												" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
										        " where start_date >= :param1 and end_date <= :param2  AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=1  AND b.profit_and_loss <=0 "+
												"  GROUP BY a.WARE_NAME)) ";
										 
									   
										  peakQuery=session.createSQLQuery(peakMonthly);
										  peakQuery.setParameter("param1", StartDate);
										  peakQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
											rtn.put("peakListSites", peakQuery.list());
											
											
											peakLossQuery=session.createSQLQuery(peakLossMonthly);
											peakLossQuery.setParameter("param1", StartDate);
											peakLossQuery.setParameter("param2", EndDate);   
											    
											    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
												rtn.put("peakMaxLossList", peakLossQuery.list());
										  
									}
							
							}  
					   if(StringUtils.equalsIgnoreCase(min,"Min") &&  max==null && StringUtils.equalsIgnoreCase(technologies,"null")) {
							/*
						   query =	
									 "SELECT DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM("+
											 "SELECT DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM( SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,"+
											" a.LATITUDE,a.LONGITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),a.SITE " + 
											" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
											" WHERE a.site=1 and b.profit_and_loss IN (select MIN(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss <=0 " + 
											" GROUP BY b.start_date))"+
											" UNION "+
											 "SELECT  DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM( SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,"+
											" a.LATITUDE,a.LONGITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),a.site " + 
											" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
											" WHERE a.site=1 and b.profit_and_loss IN (select MIN(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss >=0 " + 
											" GROUP BY b.start_date))"
											+ ")";
											*/
											
											
						   query = "select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
							   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
							   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
							   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
							   		"UNION \r\n" + 
							   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
							   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
							   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
							   		")\r\n" + 
							   		"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE";
													
									
						    System.out.println("HeLLo From MON-PROF-LOSS-MAX CONDITION, The Site List:" ); 
						    
						    peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
							
							  peakMinQuery=session.createSQLQuery(peakMinMonthly);
							  peakMinQuery.setParameter("param1", StartDate);
							  peakMinQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
								rtn.put("peakMinProfitList", peakMinQuery.list());
								
								 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								   
									
										peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
										peakMinLossQuery.setParameter("param1", StartDate);
										peakMinLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
											rtn.put("peakMinLossList", peakMinLossQuery.list());
								
								
								
											
											
						
						}  
					   
					   if(StringUtils.equalsIgnoreCase(min,"Min") &&  max==null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
											
											
						   query = "select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
							   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
							   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
							   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=0 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
							   		"UNION \r\n" + 
							   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
							   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
							   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=0 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
							   		")\r\n" + 
							   		"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE";
													
									
						    System.out.println("HeLLo From MON-PROF-LOSS-MAX CONDITION, The Site List:" ); 
						    
						    peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=0 and b.tech_4g=0  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
							
							  peakMinQuery=session.createSQLQuery(peakMinMonthly);
							  peakMinQuery.setParameter("param1", StartDate);
							  peakMinQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
								rtn.put("peakMinProfitList", peakMinQuery.list());
								
								 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=0 and b.tech_4g=0  AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								   
									
										peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
										peakMinLossQuery.setParameter("param1", StartDate);
										peakMinLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
											rtn.put("peakMinLossList", peakMinLossQuery.list());
								
								
								
							}	
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								
								   query = "select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
									   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
									   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
									   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
									   		"UNION \r\n" + 
									   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
									   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
									   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
									   		")\r\n" + 
									   		"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE";
															
											
								    System.out.println("HeLLo From MON-PROF-LOSS-MAX CONDITION, The Site List:" ); 
								    
								    peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=0  AND b.profit_and_loss >=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									
									  peakMinQuery=session.createSQLQuery(peakMinMonthly);
									  peakMinQuery.setParameter("param1", StartDate);
									  peakMinQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
										rtn.put("peakMinProfitList", peakMinQuery.list());
										
										 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
													+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
													" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
											        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=0  AND b.profit_and_loss <=0 "+
													"  GROUP BY a.WARE_NAME)) ";
											 
										   
											
												peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
												peakMinLossQuery.setParameter("param1", StartDate);
												peakMinLossQuery.setParameter("param2", EndDate);   
												    
												    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
													rtn.put("peakMinLossList", peakMinLossQuery.list());
										
										
										
									}	
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								
								   query = "select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
									   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
									   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
									   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=1 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
									   		"UNION \r\n" + 
									   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
									   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
									   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=1 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
									   		")\r\n" + 
									   		"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE";
															
											
								    System.out.println("HeLLo From MON-PROF-LOSS-MAX CONDITION, The Site List:" ); 
								    
								    peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=1  AND b.profit_and_loss >=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									
									  peakMinQuery=session.createSQLQuery(peakMinMonthly);
									  peakMinQuery.setParameter("param1", StartDate);
									  peakMinQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
										rtn.put("peakMinProfitList", peakMinQuery.list());
										
										 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
													+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
													" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
											        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=1  AND b.profit_and_loss <=0 "+
													"  GROUP BY a.WARE_NAME)) ";
											 
										   
											
												peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
												peakMinLossQuery.setParameter("param1", StartDate);
												peakMinLossQuery.setParameter("param2", EndDate);   
												    
												    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
													rtn.put("peakMinLossList", peakMinLossQuery.list());
										
										
										
									}	
											
						
						}  
					   
					   if(StringUtils.equalsIgnoreCase(max,"max") && StringUtils.equalsIgnoreCase(min,"Min") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
							   query = "select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
								   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
								   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
								   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=0 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
								   		"UNION \r\n" + 
								   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
								   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
								   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=0 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
								   		"UNION \r\n" + 
								   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
								   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select max(profit_and_loss)\r\n" + 
								   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=0 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
								   		"UNION \r\n" + 
								   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
								   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select max(profit_and_loss)\r\n" + 
								   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=0 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
								   		")\r\n" + 
								   		"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE";
							   
							   
							    peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=0 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							   
							 
							   
								  peakQuery=session.createSQLQuery(peakMonthly);
								  peakQuery.setParameter("param1", StartDate);
								  peakQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
									rtn.put("peakListSites", peakQuery.list());
									
									  peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
												+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
												" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
										        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=0 and b.tech_4g=0 AND b.profit_and_loss <=0 "+
												"  GROUP BY a.WARE_NAME)) ";
										 
									peakLossQuery=session.createSQLQuery(peakLossMonthly);
									peakLossQuery.setParameter("param1", StartDate);
									peakLossQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
										rtn.put("peakMaxLossList", peakLossQuery.list());
										
										
										 peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
													+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
													" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
											        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=0 and b.tech_4g=0  AND b.profit_and_loss >=0 "+
													"  GROUP BY a.WARE_NAME)) ";
											
											  peakMinQuery=session.createSQLQuery(peakMinMonthly);
											  peakMinQuery.setParameter("param1", StartDate);
											  peakMinQuery.setParameter("param2", EndDate);   
											    
											    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
												rtn.put("peakMinProfitList", peakMinQuery.list());
												
												 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
															+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
															" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
													        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=0 and b.tech_4g=0 AND b.profit_and_loss <=0 "+
															"  GROUP BY a.WARE_NAME)) ";
													 
												   
													
														peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
														peakMinLossQuery.setParameter("param1", StartDate);
														peakMinLossQuery.setParameter("param2", EndDate);   
														    
														    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
															rtn.put("peakMinLossList", peakMinLossQuery.list());
									
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								   query = "select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
									   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
									   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
									   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
									   		"UNION \r\n" + 
									   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
									   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
									   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
									   		"UNION \r\n" + 
									   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
									   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select max(profit_and_loss)\r\n" + 
									   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=0 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
									   		"UNION \r\n" + 
									   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
									   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select max(profit_and_loss)\r\n" + 
									   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=0 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
									   		")\r\n" + 
									   		"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE";
								   
								   
								    peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=0 AND b.profit_and_loss >=0 "+
											"  GROUP BY a.WARE_NAME)) ";
								   
								 
								   
									  peakQuery=session.createSQLQuery(peakMonthly);
									  peakQuery.setParameter("param1", StartDate);
									  peakQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
										rtn.put("peakListSites", peakQuery.list());
										
										  peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
													+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
													" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
											        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=0 AND b.profit_and_loss <=0 "+
													"  GROUP BY a.WARE_NAME)) ";
											 
										peakLossQuery=session.createSQLQuery(peakLossMonthly);
										peakLossQuery.setParameter("param1", StartDate);
										peakLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
											rtn.put("peakMaxLossList", peakLossQuery.list());
											
											
											 peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
														+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
														" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
												        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=0  AND b.profit_and_loss >=0 "+
														"  GROUP BY a.WARE_NAME)) ";
												
												  peakMinQuery=session.createSQLQuery(peakMinMonthly);
												  peakMinQuery.setParameter("param1", StartDate);
												  peakMinQuery.setParameter("param2", EndDate);   
												    
												    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
													rtn.put("peakMinProfitList", peakMinQuery.list());
													
													 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
																+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
																" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
														        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=0 AND b.profit_and_loss <=0 "+
																"  GROUP BY a.WARE_NAME)) ";
														 
													   
														
															peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
															peakMinLossQuery.setParameter("param1", StartDate);
															peakMinLossQuery.setParameter("param2", EndDate);   
															    
															    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
																rtn.put("peakMinLossList", peakMinLossQuery.list());
										
								}
							
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								   query = "select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
									   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
									   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
									   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=1 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
									   		"UNION \r\n" + 
									   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
									   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
									   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=1 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
									   		"UNION \r\n" + 
									   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
									   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select max(profit_and_loss)\r\n" + 
									   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=1 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
									   		"UNION \r\n" + 
									   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
									   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select max(profit_and_loss)\r\n" + 
									   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 AND tech_2g= 1 and tech_3g=1 and tech_4g=1 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
									   		")\r\n" + 
									   		"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE";
								   
								   
								    peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=1 AND b.profit_and_loss >=0 "+
											"  GROUP BY a.WARE_NAME)) ";
								   
								 
								   
									  peakQuery=session.createSQLQuery(peakMonthly);
									  peakQuery.setParameter("param1", StartDate);
									  peakQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
										rtn.put("peakListSites", peakQuery.list());
										
										  peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
													+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
													" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
											        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=1 AND b.profit_and_loss <=0 "+
													"  GROUP BY a.WARE_NAME)) ";
											 
										peakLossQuery=session.createSQLQuery(peakLossMonthly);
										peakLossQuery.setParameter("param1", StartDate);
										peakLossQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
											rtn.put("peakMaxLossList", peakLossQuery.list());
											
											
											 peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
														+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
														" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
												        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=1  AND b.profit_and_loss >=0 "+
														"  GROUP BY a.WARE_NAME)) ";
												
												  peakMinQuery=session.createSQLQuery(peakMinMonthly);
												  peakMinQuery.setParameter("param1", StartDate);
												  peakMinQuery.setParameter("param2", EndDate);   
												    
												    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
													rtn.put("peakMinProfitList", peakMinQuery.list());
													
													 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
																+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
																" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
														        " where start_date >= :param1 and end_date <= :param2 AND b.tech_2g= 1 and b.tech_3g=1 and b.tech_4g=1 AND b.profit_and_loss <=0 "+
																"  GROUP BY a.WARE_NAME)) ";
														 
													   
														
															peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
															peakMinLossQuery.setParameter("param1", StartDate);
															peakMinLossQuery.setParameter("param2", EndDate);   
															    
															    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
																rtn.put("peakMinLossList", peakMinLossQuery.list());
										
								}
							}
					   
					   if(StringUtils.equalsIgnoreCase(max,"max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null")) {
						/*
						   query =	
									 "SELECT DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM("+
											 "SELECT DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM( SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,"+
											" a.LATITUDE,a.LONGITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),a.SITE " + 
											" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
											" WHERE a.site=1 and b.profit_and_loss IN (select MIN(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss >=0 " + 
											" GROUP BY b.start_date))"+
											" UNION "+
											 "SELECT  DISTINCT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE,SITE,PROFIT_AND_LOSS FROM( SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,"+
											" a.LATITUDE,a.LONGITUDE,b.profit_and_loss,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS'),a.site " + 
											" FROM WAREHOUSE a INNER JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " + 
											" WHERE a.site=1 and b.profit_and_loss IN (select MAX(b.profit_and_loss) FROM WAREHOUSE_PROFIT_LOSS b WHERE b.START_DATE >= :param1 AND b.END_DATE <= :param2 AND b.profit_and_loss >=0 " + 
											" GROUP BY b.start_date))"
											+ ")";
											
											
											
									
						   
						   query =	"SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
									+"b.ProfitLossSum FROM WAREHOUSE a right join "
									+"((select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum  "
									+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum DESC)"
									+"where ProfitLossSum <=0 ) UNION (select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum ASC)  "
									+" where ProfitLossSum <=0 )UNION (select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum " + 
									"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum DESC)" + 
									" where ProfitLossSum >=0 )UNION (select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum" + 
									" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum ASC)" + 
									" where ProfitLossSum >=0 ))  b on a.site_id = b.site_id";
									*/
						   
						   query = "select site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE,SUM(profit_and_loss) from (\r\n" + 
							   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss  \r\n" + 
							   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
							   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM')) \r\n" + 
							   		"UNION \r\n" + 
							   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
							   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select min(profit_and_loss)\r\n" + 
							   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
							   		"UNION \r\n" + 
							   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
							   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select max(profit_and_loss)\r\n" + 
							   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss<=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
							   		"UNION \r\n" + 
							   		"select a.site_id, a.ware_name ,a.WARE_ID, a.LATITUDE,a.LONGITUDE,  a.SITE,b.profit_and_loss\r\n" + 
							   		"FROM WAREHOUSE a right join  warehouse_profit_loss b on a.WARE_ID = b.WARE_ID where b.profit_and_loss IN(select max(profit_and_loss)\r\n" + 
							   		"from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 and profit_and_loss>=0 group by TO_CHAR(start_date, 'YYYY-MM'))\r\n" + 
							   		")\r\n" + 
							   		"GROUP BY site_id,ware_name ,WARE_ID,LATITUDE,LONGITUDE,SITE";
						   
						   
						    peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						   
						 
						   
							  peakQuery=session.createSQLQuery(peakMonthly);
							  peakQuery.setParameter("param1", StartDate);
							  peakQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
								rtn.put("peakListSites", peakQuery.list());
								
								  peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									 
								peakLossQuery=session.createSQLQuery(peakLossMonthly);
								peakLossQuery.setParameter("param1", StartDate);
								peakLossQuery.setParameter("param2", EndDate);   
								    
								    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
									rtn.put("peakMaxLossList", peakLossQuery.list());
									
									
									 peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
												+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
												" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
										        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
												"  GROUP BY a.WARE_NAME)) ";
										
										  peakMinQuery=session.createSQLQuery(peakMinMonthly);
										  peakMinQuery.setParameter("param1", StartDate);
										  peakMinQuery.setParameter("param2", EndDate);   
										    
										    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
											rtn.put("peakMinProfitList", peakMinQuery.list());
											
											 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
														+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
														" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
												        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
														"  GROUP BY a.WARE_NAME)) ";
												 
											   
												
													peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
													peakMinLossQuery.setParameter("param1", StartDate);
													peakMinLossQuery.setParameter("param2", EndDate);   
													    
													    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
														rtn.put("peakMinLossList", peakMinLossQuery.list());
											
											
						
						}   
					   q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);   
					    
					    System.out.println("HeLLo From MON-PROF-LOSS CONDITION, The Site List:" +mapper.writeValueAsString(q.list())); 
						  rtn.put("listSites", q.list()); 
					   
				   }
				   
			   }// end monthly condition
			   
			   if(accumulated==1) {
				   System.out.println("<--------------ACCUMULATED CONDITION-------------->");
				   if(profitable == null && loss == null && max == null && min == null && (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "")){
					 
					   query = "select * from (SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE, "
								+"b.ProfitLossSum from warehouse a right join "
								+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
								+" from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
								+"and end_date <= :param2 group by site_id) b "
								+"on a.site_id = b.site_id  order by ProfitLossSum) ";
			    	 
								   
					   
					    q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);   
					    
					    System.out.println("HeLLo From ONLY ACCUMULATED CONDITION, The Site List:" +mapper.writeValueAsString(q.list())); 
						rtn.put("listSites", q.list()); 
				   }// end all null conditions
				   
				   if(StringUtils.equalsIgnoreCase(max,"Max") && profitable == null && loss == null){
					   if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") {	
						/*
						   query = "select * from (SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE, "
									+"b.ProfitLossSum from warehouse a right join "
									+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+" from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
									+"and end_date <= :param2 group by site_id) b "
									+"on a.site_id = b.site_id  order by ProfitLossSum DESC)";
				    	 */
						   
						   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						   
					   }// end max-accumulated-NULL Tech condition
					   if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
						   if(StringUtils.equalsIgnoreCase(technologies,"2g")){
							   
							   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							   
							   
							   
						   }//end 2g condition
						   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
							   
							   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g =1 and tech_4g = 0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
					    	 
						   }// end 2G3G condition
						   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
							   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g =1 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
						   }// end 2G3G4G condition
					   }//end tech condition

					    q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);	
					    
					    List<Object[]> siteList = (List<Object[]>) (q).list();
					    List<Object[]> maxSiteResult = new ArrayList<Object[]>();
						   if (siteList != null) {
							   for (Object[] obj : siteList) {
								   if (Integer.parseInt(String.valueOf((obj[5]))) == 1) {
									   System.out.println("WAREHOUSE is also a SITE");
									   maxSiteResult.add(obj);
									}
								   }
						   }
							   System.out.println("HeLLo From Acc-Max CONDITION,Site List:" +mapper.writeValueAsString(maxSiteResult)); 
							   rtn.put("listSites", maxSiteResult); 
					    	
					   
				   }// End max-accumulated condition
				   
				   if(StringUtils.equalsIgnoreCase(min,"Min") && profitable == null && loss == null){
					   if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") {	
						   
						   /*
						   query = "select * from (SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,a.SITE, "
									+"b.ProfitLossSum from warehouse a right join "
									+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+" from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
									+"and end_date <= :param2 group by site_id) b "
									+"on a.site_id = b.site_id  order by ProfitLossSum ASC) where rownum = 1 ";
									*/
						   
						   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						   
					   } //end Null TECH

					   if(StringUtils.equalsIgnoreCase(technologies,"2g")){
						   
						   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 0 and b.tech_4g = 0 AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						   
						   
					   }//end 2g condition
					   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
						   
						   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 1 and b.tech_4g = 0 AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						   
					   }// end 2G3G condition
					   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
						   
						   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 1 and b.tech_4g = 1 AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						   
					   }// end 2G3G4G condition
					  
					   q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);	
					    
					    List<Object[]> siteList = (List<Object[]>) (q).list();
					    List<Object[]> minSiteResult = new ArrayList<Object[]>();
						   if (siteList != null) {
							   for (Object[] obj : siteList) {
								   if (Integer.parseInt(String.valueOf((obj[5]))) == 1) {
									   System.out.println("WAREHOUSE is also a SITE");
									   minSiteResult.add(obj);
									}
								   }
						   }
							   System.out.println("HeLLo From Acc-Min CONDITION,Site List:" +mapper.writeValueAsString(minSiteResult)); 
							   rtn.put("listSites", minSiteResult); 
					
				   }// end min-accumulated condition
				   
				   if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g")||StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && profitable == null && loss == null && min == null && max == null){
					   if(StringUtils.equalsIgnoreCase(technologies,"2g")){
						   
						   query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
									+"b.ProfitLossSum from warehouse a right join "
									+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+" from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
									+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id) b "
									+"on a.site_id = b.site_id";
					   }// end 2G condition
					   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
						   
						   query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
									+"b.ProfitLossSum from warehouse a right join "
									+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+" from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
									+"and end_date <= :param2 and tech_2g = 1 and tech_3g =1 and tech_4g = 0 group by site_id) b "
									+"on a.site_id = b.site_id";
					   }// end 2G3G condition
					   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
						   
						   query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
									+"b.ProfitLossSum from warehouse a right join "
									+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+" from WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
									+"and end_date <= :param2 and tech_2g = 1 and tech_3g =1 and tech_4g =1 group by site_id) b "
									+"on a.site_id = b.site_id";
					   }// end 2G3G condition
				   
					   q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);	
					    
					    List<Object[]> siteList = (List<Object[]>) (q).list();
					    List<Object[]> techSiteResult = new ArrayList<Object[]>();
						   if (siteList != null) {
							   for (Object[] obj : siteList) {
								   if (Integer.parseInt(String.valueOf((obj[5]))) == 1) {
									   System.out.println("WAREHOUSE is also a SITE");
									   techSiteResult.add(obj);
									}
								   }
						   }
							   System.out.println("HeLLo From TECH-ACC CONDITION,Site List:" +mapper.writeValueAsString(techSiteResult)); 
							   rtn.put("listSites", techSiteResult);   
				   }// End TECH-Accumulated condition
					
				   //ZZ
				   if(StringUtils.equalsIgnoreCase(profitable,"Profitable") && loss == null) {
					   if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
						{
						   query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
									+"b.ProfitLossSum FROM WAREHOUSE a right join "
									+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
									+"and end_date <= :param2 group by site_id) b "
									+"on a.site_id = b.site_id WHERE ProfitLossSum >=0";
						   
						}
					   if(StringUtils.equalsIgnoreCase(max,"max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null")){
						 
						   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						   
						    
					   }
					   if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null")){
						   /*
						   query = "SELECT * FROM(SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
									+"b.ProfitLossSum FROM WAREHOUSE a right join "
									+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
									+"and end_date <= :param2 group by site_id) b "
									+"on a.site_id = b.site_id order by ProfitLossSum ASC)"
									+ "WHERE ProfitLossSum >=0 ";
									*/
						   
						   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						   
					   }
					   if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && min == null && max == null){
						   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
							   
							   query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
										+"b.ProfitLossSum FROM WAREHOUSE a right join "
										+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0  group by site_id) b "
										+"on a.site_id = b.site_id WHERE ProfitLossSum >=0";
							   
						   }
						   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
							   
							   query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
										+"b.ProfitLossSum FROM WAREHOUSE a right join "
										+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g =1 and tech_4g = 0  group by site_id) b "
										+"on a.site_id = b.site_id WHERE ProfitLossSum >=0";
							   
						   }
						   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
							   
							   query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
										+"b.ProfitLossSum FROM WAREHOUSE a right join "
										+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g =1 and tech_4g = 1  group by site_id) b "
										+"on a.site_id = b.site_id WHERE ProfitLossSum >=0";
							   
						   }
					   }// End tech condition 
					   
						if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null")) {
							
						
							    
							/*
							query =	"SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
									+"b.ProfitLossSum FROM WAREHOUSE a right join "
									+"((select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum  "
									+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum DESC)"
									+"where ProfitLossSum >=0) UNION (select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum ASC)  "
									+" where ProfitLossSum >=0 ))  b on a.site_id = b.site_id";
							*/
							
							query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) "+
							        " UNION "+
							        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						
						}
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								
								  query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 0 and b.tech_4g = 0   AND b.profit_and_loss >=0 "+
											"  GROUP BY a.WARE_NAME)) ";
								   
								    /*
								  query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
											+"b.ProfitLossSum FROM WAREHOUSE a right join "
											+"((select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
											+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
											+" and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by ProfitLossSum DESC )"
											+" WHERE ProfitLossSum >=0 and rownum = 1))"
											+" b on a.site_id = b.site_id ";
											*/
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g = 0   AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							   
											
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1   AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							   
											
							}
						}//end max-tech condition
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {

							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 0 and b.tech_4g = 0   AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							   
											
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 1 and b.tech_4g = 0   AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							   
											
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1   AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							   
											
							}
						}//end min-tech condition
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								/* ="SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
									
										+"b.ProfitLossSum FROM WAREHOUSE a right join((select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by ProfitLossSum DESC) "
										+"where ProfitLossSum >=0 and rownum = 1) UNION (select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by ProfitLossSum ASC)"
										+"where ProfitLossSum >=0 and rownum = 1)) b on a.site_id = b.site_id";
								*/
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 0 and b.tech_4g =0  AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) "+
								        " UNION "+
								        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 0 and b.tech_4g =0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 1 and b.tech_4g =0  AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) "+
								        " UNION "+
								        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1  AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) "+
								        " UNION "+
								        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							}
						}//end prof-min-max-tech condition
						
							
					   
					   q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);	
					    
					    List<Object[]> siteList = (List<Object[]>) (q).list();
					    List<Object[]> profSiteResult = new ArrayList<Object[]>();
						   if (siteList != null) {
							   for (Object[] obj : siteList) {
								   if (Integer.parseInt(String.valueOf((obj[5]))) == 1) {
									   System.out.println("WAREHOUSE is also a SITE");
									   profSiteResult.add(obj);
									}
								   }
						   }
							   System.out.println("HeLLo From PROF-ACC CONDITION,Site List:" +mapper.writeValueAsString(profSiteResult)); 
							   rtn.put("listSites", profSiteResult); 
					   
				   }// end Prof-Acc condition
				   
				   if(StringUtils.equalsIgnoreCase(loss,"Loss") && profitable == null) {
					   if(max == null && min == null && StringUtils.equalsIgnoreCase(technologies,"null") )
						{
						   query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
									+"b.ProfitLossSum FROM WAREHOUSE a right join "
									+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
									+"and end_date <= :param2 group by site_id) b "
									+"on a.site_id = b.site_id WHERE ProfitLossSum <=0";
						}
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && StringUtils.equalsIgnoreCase(technologies,"null")){
							/*
							
							query = "SELECT * FROM(SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
										+"b.ProfitLossSum FROM WAREHOUSE a right join "
										+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 group by site_id) b "
										+"on a.site_id = b.site_id order by ProfitLossSum DESC)"
										+ "WHERE ProfitLossSum <=0 and rownum = 1";
										*/
							 query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							   
						}
					   if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && StringUtils.equalsIgnoreCase(technologies,"null")){
						   
						   
						   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						   
					   }
					   if((StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) && min == null && max == null){
						   if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
							   query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
										+"b.ProfitLossSum FROM WAREHOUSE a right join "
										+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0  group by site_id) b "
										+"on a.site_id = b.site_id WHERE ProfitLossSum <=0";
							   
						   }
						   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
							   query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
										+"b.ProfitLossSum FROM WAREHOUSE a right join "
										+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 0  group by site_id) b "
										+"on a.site_id = b.site_id WHERE ProfitLossSum <=0";
							   
						   }
						   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
							   query = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE,"
										+"b.ProfitLossSum FROM WAREHOUSE a right join "
										+"(select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 "
										+"and end_date <= :param2 and tech_2g = 1 and tech_3g = 1 and tech_4g = 1 group by site_id) b "
										+"on a.site_id = b.site_id WHERE ProfitLossSum <=0";
							   
						   }
					   }
					   
						if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null")) {
							query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
									"  GROUP BY a.WARE_NAME)) "+
							        " UNION "+
							        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						}
						if(StringUtils.equalsIgnoreCase(max,"Max") && min == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								
								 query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 0 and b.tech_4g = 0 AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
								  
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 1 and b.tech_4g = 0 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							  					
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 1 and b.tech_4g = 1 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							  
							}
						}
						if(StringUtils.equalsIgnoreCase(min,"Min") && max == null && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g"))) {
						
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 0 and b.tech_4g = 0 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							  
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g = 0 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							  	
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							  
							}
						}
						
						if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max") && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
								
								/*
								query ="SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
										+"b.ProfitLossSum FROM WAREHOUSE a right join((select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by ProfitLossSum DESC) "
										+"where ProfitLossSum <=0 and rownum = 1) UNION (select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
										+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 and tech_2g = 1 and tech_3g = 0 and tech_4g = 0 group by site_id order by ProfitLossSum ASC)"
										+"where ProfitLossSum <=0 and rownum = 1)) b on a.site_id = b.site_id";
										*/
								
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 0 and b.tech_4g = 0 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) "+
								        " UNION "+
								        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g = 0 and b.tech_4g = 0 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g = 0 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) "+
								        " UNION "+
								        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g = 0 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) "+
								        " UNION "+
								        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							}
							
						}
					   q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);	
					    
					    List<Object[]> siteList = (List<Object[]>) (q).list();
					    List<Object[]> lossSiteResult = new ArrayList<Object[]>();
						   if (siteList != null) {
							   for (Object[] obj : siteList) {
								   if (Integer.parseInt(String.valueOf((obj[5]))) == 1) {
									   System.out.println("WAREHOUSE is also a SITE");
									   lossSiteResult.add(obj);
									}
								   }
						   }
							   System.out.println("HeLLo From LOSS-ACC CONDITION,Site List:" +mapper.writeValueAsString(lossSiteResult)); 
							   rtn.put("listSites", lossSiteResult);
				   }//end loss-Acc condition
				   
				   
				   
		if(StringUtils.equalsIgnoreCase(loss,"Loss") && (StringUtils.equalsIgnoreCase(profitable,"Profitable")) ) {
			
			  if(max==null && min==null && StringUtils.equalsIgnoreCase(technologies,"null")) {
					query =	"SELECT * FROM(SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
							+" b.ProfitLossSum FROM WAREHOUSE a right join (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
							+" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id) b "
							+" on a.site_id = b.site_id order by ProfitLossSum)";
				}  
			   
			 
						
						
						
			
					
					
					
					if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")  && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
					{
						if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
							
						   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =0 and b.tech_4g =0 AND b.profit_and_loss <=0 "+
									"  GROUP BY a.WARE_NAME)) "+
							        " UNION "+
							        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =0 and b.tech_4g =0 AND b.profit_and_loss <=0 "+
									"  GROUP BY a.WARE_NAME)) "+
							        " UNION "+
							        "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =0 and b.tech_4g =0  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) "+
							        " UNION "+
							        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =0 and b.tech_4g =0 AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
							
							   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =0 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) "+
								        " UNION "+
								        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =0 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) "+
								        " UNION "+
								        "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =0  AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) "+
								        " UNION "+
								        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =0 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							}
						
						if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
							
							   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) "+
								        " UNION "+
								        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1 AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) "+
								        " UNION "+
								        "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1  AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) "+
								        " UNION "+
								        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1 AND b.profit_and_loss >=0 "+
										"  GROUP BY a.WARE_NAME)) ";
							}
						}   
					   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(technologies,"null")) {
							
						   /*
						   query =	"SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
									+"b.ProfitLossSum FROM WAREHOUSE a right join "
									+"((select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum  "
									+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum DESC)"
									+"where ProfitLossSum <=0 and rownum = 1) UNION (select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum "
									+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum ASC)  "
									+" where ProfitLossSum <=0 and rownum = 1)UNION (select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum " + 
									"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum DESC)" + 
									" where ProfitLossSum >=0 and rownum = 1)UNION (select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum" + 
									" FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum ASC)" + 
									" where ProfitLossSum >=0 and rownum = 1))  b on a.site_id = b.site_id";
									*/
						   
						   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
									"  GROUP BY a.WARE_NAME)) "+
							        " UNION "+
							        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
									"  GROUP BY a.WARE_NAME)) "+
							        " UNION "+
							        "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) "+
							        " UNION "+
							        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						}   
					   
					   if(StringUtils.equalsIgnoreCase(max,"Max") && min==null && StringUtils.equalsIgnoreCase(technologies,"null")) {
							
						   
						   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max(b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) "+
							        " UNION "+
							        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						   /*
						   query =	"SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
									+"b.ProfitLossSum FROM WAREHOUSE a right join "
									+"((select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum  "
									+"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum DESC)"
									+"where ProfitLossSum <=0 and rownum = 1) UNION (select * from (select SITE_ID,sum(profit_and_loss) as ProfitLossSum " + 
									"FROM WAREHOUSE_PROFIT_LOSS where start_date >= :param1 and end_date <= :param2 group by site_id order by ProfitLossSum DESC)" + 
									" where ProfitLossSum >=0 and rownum = 1))  b on a.site_id = b.site_id";
									*/
						} 
					   
					   
					   if(StringUtils.equalsIgnoreCase(max,"Max") && min==null  && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
						{
							if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
							
									   
									   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
												+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
												" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max(b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
										        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =0 and b.tech_4g =0 AND b.profit_and_loss >=0 "+
												"  GROUP BY a.WARE_NAME)) "+
										        " UNION "+
										        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
												+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
												" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
										        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =0 and b.tech_4g =0  AND b.profit_and_loss <=0 "+
												"  GROUP BY a.WARE_NAME)) ";
									   
							}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
								
								   
								   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max(b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =0 AND b.profit_and_loss >=0 "+
											"  GROUP BY a.WARE_NAME)) "+
									        " UNION "+
									        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =0  AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
								   
						}
							if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
								
								   
								   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max(b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1 AND b.profit_and_loss >=0 "+
											"  GROUP BY a.WARE_NAME)) "+
									        " UNION "+
									        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1  AND b.profit_and_loss <=0 "+
											"  GROUP BY a.WARE_NAME)) ";
								   
						}
									  
									}  
					   
					   if(StringUtils.equalsIgnoreCase(min,"Min") && max==null && StringUtils.equalsIgnoreCase(technologies,"null")) {
						   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min(b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
									"  GROUP BY a.WARE_NAME)) "+
							        " UNION "+
							        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
									+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
									" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
							        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
									"  GROUP BY a.WARE_NAME)) ";
						}  
					   q=session.createSQLQuery(query);
						q.setParameter("param1", StartDate);
					    q.setParameter("param2", EndDate);	
					    
					    List<Object[]> siteList = (List<Object[]>) (q).list();
					    List<Object[]> lossSiteResult = new ArrayList<Object[]>();
						   if (siteList != null) {
							   for (Object[] obj : siteList) {
								   if (Integer.parseInt(String.valueOf((obj[5]))) == 1) {
									   System.out.println("WAREHOUSE is also a SITE");
									   lossSiteResult.add(obj);
									}
								   }
						   }
							   System.out.println("HeLLo From PROF-LOSS-ACC CONDITION,Site List:" +mapper.writeValueAsString(lossSiteResult)); 
							   rtn.put("listSites", lossSiteResult);
				   }
		
		if(StringUtils.equalsIgnoreCase(min,"Min") && max==null  && (StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g")))
		{
			if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
			
					   
					   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
								+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
								" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min(b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
						        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =0 and b.tech_4g =0 AND b.profit_and_loss >=0 "+
								"  GROUP BY a.WARE_NAME)) "+
						        " UNION "+
						        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
								+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
								" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
						        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =0 and b.tech_4g =0  AND b.profit_and_loss <=0 "+
								"  GROUP BY a.WARE_NAME)) ";
					   
			}
			if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
				
				   
				   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
							+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
							" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min(b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
					        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =0 AND b.profit_and_loss >=0 "+
							"  GROUP BY a.WARE_NAME)) "+
					        " UNION "+
					        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
							+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
							" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
					        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =0  AND b.profit_and_loss <=0 "+
							"  GROUP BY a.WARE_NAME)) ";
				   
		}
			if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
				
				   
				   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
							+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
							" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min(b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
					        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1 AND b.profit_and_loss >=0 "+
							"  GROUP BY a.WARE_NAME)) "+
					        " UNION "+
					        " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
							+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
							" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
					        " where start_date >= :param1 and end_date <= :param2 and b.tech_2g = 1 and b.tech_3g =1 and b.tech_4g =1  AND b.profit_and_loss <=0 "+
							"  GROUP BY a.WARE_NAME)) ";
				   
		}
					  
					}  
		  
				   
				   
			   }//END accumulated condition
			   if (monthly ==0 && accumulated ==0) {
			   
			   if((StringUtils.equalsIgnoreCase(profitable,"null") || profitable == null || profitable == "") && (StringUtils.equalsIgnoreCase(loss,"null") || loss == null || loss == "")&& (StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") ) { 
				   if(StringUtils.equalsIgnoreCase(max,"max") && min==null){
					
				   System.out.println("ONLY MAX IS SELECTED"); 
					/*
					query =	"SELECT * FROM(SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE, a.SITE, "
							+" b.PROFIT_AND_LOSS,TO_CHAR(b.start_date, 'YYYY-MM-DD HH24:MI:SS') FROM WAREHOUSE a inner join WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID "
							+ " where a.site=1 AND b.PROFIT_AND_LOSS IN ( select max(profit_and_loss) "
							+ " from warehouse_profit_loss where start_date >= :param1 and end_date <= :param2 "
							+ "GROUP BY b.start_date))";
						   */
				   
				   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
							+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
							" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
					        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
							"  GROUP BY a.WARE_NAME))"
					        +" UNION"
							+ " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
							+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
							" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
					        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
							"  GROUP BY a.WARE_NAME)) ";
				   
				   q=session.createSQLQuery(query);
					q.setParameter("param1", StartDate);
				    q.setParameter("param2", EndDate);	
				    
				    List<Object[]> siteList = (List<Object[]>) (q).list();
				    List<Object[]> lossSiteResult = new ArrayList<Object[]>();
					   if (siteList != null) {
						   for (Object[] obj : siteList) {
							  
								   lossSiteResult.add(obj);
								
							   }
					   }
						   System.out.println("HeLLo From ONLY MAX CONDITION,Site List:" +mapper.writeValueAsString(lossSiteResult)); 
						   rtn.put("listSites", lossSiteResult);
					 
				 
					 
		    	 
			   }
				   if(StringUtils.equalsIgnoreCase(min,"min") && max==null){
						
					   System.out.println("ONLY min IS SELECTED"); 
						
					   query = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
								+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
								" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
						        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
								"  GROUP BY a.WARE_NAME))";
					   
					   peakLossMonthly =  " SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
								+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
								" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
						        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
								"  GROUP BY a.WARE_NAME)) ";
								
						 
					
							peakLossQuery=session.createSQLQuery(peakLossMonthly);
							peakLossQuery.setParameter("param1", StartDate);
							peakLossQuery.setParameter("param2", EndDate);   
							    
								rtn.put("peakMaxLossList", peakLossQuery.list());
						  
						 
								 q=session.createSQLQuery(query);
									q.setParameter("param1", StartDate);
								    q.setParameter("param2", EndDate);	
								    
								    List<Object[]> siteList = (List<Object[]>) (q).list();
								    List<Object[]> lossSiteResult = new ArrayList<Object[]>();
									   if (siteList != null) {
										   for (Object[] obj : siteList) {
											  
												   lossSiteResult.add(obj);
												
											   }
									   }
										   System.out.println("HeLLo From ONLY MAX CONDITION,Site List:" +mapper.writeValueAsString(lossSiteResult)); 
										   rtn.put("listSites", lossSiteResult);
					   

				   }
				   
				   if(StringUtils.equalsIgnoreCase(min,"min") && StringUtils.equalsIgnoreCase(max,"max")){
					   
					   
					   peakMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
								+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
								" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
						        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
								"  GROUP BY a.WARE_NAME)) ";
					   
					 
					   
						  peakQuery=session.createSQLQuery(peakMonthly);
						  peakQuery.setParameter("param1", StartDate);
						  peakQuery.setParameter("param2", EndDate);   
						    
						    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakQuery.list())); 
							rtn.put("peakListSites", peakQuery.list());
							
							  peakLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
										+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
										" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
								        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
										"  GROUP BY a.WARE_NAME)) ";
								 
							peakLossQuery=session.createSQLQuery(peakLossMonthly);
							peakLossQuery.setParameter("param1", StartDate);
							peakLossQuery.setParameter("param2", EndDate);   
							    
							    System.out.println("PEAK MONTHLY , The Site List:" +mapper.writeValueAsString(peakLossQuery.list())); 
								rtn.put("peakMaxLossList", peakLossQuery.list());
								
								
								 peakMinMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
											+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
											" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select min( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
									        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss >=0 "+
											"  GROUP BY a.WARE_NAME)) ";
									
									  peakMinQuery=session.createSQLQuery(peakMinMonthly);
									  peakMinQuery.setParameter("param1", StartDate);
									  peakMinQuery.setParameter("param2", EndDate);   
									    
									    System.out.println("PEAK Min MONTHLY PROFITABLE, The Site List:" +mapper.writeValueAsString(peakMinQuery.list())); 
										rtn.put("peakMinProfitList", peakMinQuery.list());
										
										 peakMinLossMonthly = "SELECT * FROM( SELECT DISTINCT  a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,"
													+ "a.LONGITUDE,a.SITE,b.PROFIT_AND_LOSS FROM WAREHOUSE a RIGHT JOIN WAREHOUSE_PROFIT_LOSS b on a.WARE_ID = b.WARE_ID " +
													" WHERE a.site=1 and b.PROFIT_AND_LOSS IN (select max( b.PROFIT_AND_LOSS)as maxProfAndLoss FROM WAREHOUSE_PROFIT_LOSS b "+
											        " where start_date >= :param1 and end_date <= :param2  AND b.profit_and_loss <=0 "+
													"  GROUP BY a.WARE_NAME)) ";
											 
										   
											
												peakMinLossQuery=session.createSQLQuery(peakMinLossMonthly);
												peakMinLossQuery.setParameter("param1", StartDate);
												peakMinLossQuery.setParameter("param2", EndDate);   
												    
												    System.out.println("PEAK min LOSS MONTHLY , The Site List:" +mapper.writeValueAsString(peakMinLossQuery.list())); 
													rtn.put("peakMinLossList", peakMinLossQuery.list());
										
										
						  
						 
				   }
				  
				   }
			   }
		   }
				   tx.commit();
					session.close(); 
					
					
				  return rtn; 
				 
		 }
		
		
		
		
		
		//Revenue Report
		
		
		//Revenue Report
		

		
	/*	
		@SuppressWarnings("unchecked")
		@RequestMapping(value ="/GetAllSitesGrid", method = RequestMethod.GET)
		@ResponseBody public Map<String, Object> GetAllSitesGrid(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) throws
			JsonProcessingException { 
			logger.info("Welcome home! The client locale is {}.", locale);
			
			Map<String, Object> rtn = new LinkedHashMap<>(); 
		    ObjectMapper mapper = new
			ObjectMapper();
		    Configuration cfg = new Configuration().configure("/almrpt.cfg.xml");
	        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
	        SessionFactory sf = cfg.buildSessionFactory(builder.build());
	        Session session = sf.openSession();
	        Transaction tx = session.beginTransaction();
	        Query q;
	        String query = "";
	        List<Object[]> revenueReportList = new ArrayList<Object[]>();
	        
	         
            
	        
           
	        query="select site_name,area_id,  TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from prepaid_payg_revenue  where REVENUE_DATE >= trunc(sysdate) - 2 AND REVENUE_DATE < (trunc(sysdate) - 2) + 1 group by site_name, area_id, revenue_date order by REVENUE_DATE asc";
	        		                                        
	        q = session.createSQLQuery(query);
	       
	        revenueReportList=q.list();

	 
	        
	        tx.commit();
	        session.close();
	        System.out.println("/***The Whole data is:  " +mapper.writeValueAsString(revenueReportList));

	        rtn.put("revenuedefaultlist", revenueReportList);
	 	                                       
	        		                                        
	        		                                      
				
				
				  return rtn; 
				 
				 }
		
		
		
		*/
		
		public double findAllByQueryParamsAsDouble(Session session, String queryString, List<Object> params) {
			
			double value = 0;
			int i;
			

			Query query = session.createQuery(queryString);
			for (Object param : params) {
			i = params.indexOf(param) + 1;
			query.setParameter("param_" + i,param );
			}

			if (query.uniqueResult() != null)
				value = (double) query.uniqueResult();
			else
			{
				value = 0.0;
				System.out.println("There is not any record related to the query: " +query + " in the method: AppConfig.findAllByQueryParamsAsDouble");
			}
			
			return  value;
			}	
}
