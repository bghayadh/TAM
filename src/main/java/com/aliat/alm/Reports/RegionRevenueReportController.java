package com.aliat.alm.Reports;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.models.RegionRevenueReport;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
public class RegionRevenueReportController {

	 
	 @Autowired
	 ALMSessions almsessions;
	 
	 // Audited by yara 
	 private static final Logger logger = LoggerFactory.getLogger(RegionRevenueReportController.class);
	 //On Load
	 @SuppressWarnings("unchecked")
	@RequestMapping(value = "/RegionRevenueReport", method = RequestMethod.GET)
		public String RevenueReport(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException, org.json.simple.parser.ParseException {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> rtn = new LinkedHashMap<>();
			//logger.info("Welcome home! The client locale is {}.", locale);
    
			    Query gridData;
				String gridQuery = "", regChrtQry = "", revPieQuery;
				String defaultRegions = "", defaultChartSites = "", regionPool = "";
				Query defRegs, defRegChrt, revPieReg, periodicChrtReg,mapRegPools;
				String periodicDataQuery;
				List<Object[]> defaultRegionsList = new ArrayList<Object[]>();
				List<Object[]> RegPools = new ArrayList<Object[]>();
				List<Object[]> defaultPieRev = new ArrayList<Object[]>();
				List<Object[]> perDataChrtLst = new ArrayList<Object[]>();
				List<Object> defaultChartRevenueReport = new ArrayList<Object>();
				List<Object> defPeriodicData = new ArrayList<Object>();
				ArrayList<Float> chartArray;
				JSONArray chartJSNArr;
		        Session session = null;
		        Transaction tx = null;
		        
			 if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			 return LoginServices.checkSession(request, response);
			 }
			 else {
			 
				 try {
					 
			         session = almsessions.getSessionRPT();
			         tx = session.beginTransaction();
			        
			        
			     //gridQuery:Query for the grid
		          gridQuery =   "select t.REGION_NAME AS \"regionName\",t.REGION_ID as  \"regionID\",t.revenue_date as  \"startDate\",t.revenue_date as  \"endDate\",t.voice_revenue as  \"voiceRevenue\",t.sms_revenue as  \"smsRevenue\",t.data_revenue as  \"dataRevenue\",t.vas_revenue as  \"vasRevenue\"  from ( SELECT DISTINCT (CASE WHEN b.REGION_NAME = 'null' THEN 'No region name' WHEN NVL(b.REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE b.REGION_NAME END) REGION_NAME,(CASE WHEN b.REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(b.REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE b.REGION_ID END) REGION_ID,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date," + 
			          		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
			          		"FROM PREPAID_PAYG_REVENUE b WHERE b.REVENUE_DATE >= (trunc(sysdate) - 2) AND b.REVENUE_DATE < ((trunc(sysdate) - 2) + 1) " + 
			          		"GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS')) t order by t.REGION_NAME asc, t.revenue_date desc";
			        gridData = session.createSQLQuery(gridQuery);
			
			        
     		        @SuppressWarnings("unchecked")
	           		
	           		List<RegionRevenueReport> revenueReportResults = (List<RegionRevenueReport>) ((SQLQuery) gridData)
	           		.addScalar("regionName").addScalar("regionID").addScalar("startDate").addScalar("endDate").addScalar("voiceRevenue")
	           		.addScalar("smsRevenue").addScalar("dataRevenue").addScalar("vasRevenue")
	           		.setResultTransformer(Transformers.aliasToBean(RegionRevenueReport.class)).list();
	           		
	           	   // System.out.println("****The RegionRevenueReport is:  "+mapper.writeValueAsString(revenueReportResults));
			    	
	           		  model.addAttribute("revenueReportList",mapper.writeValueAsString(revenueReportResults));
			        
			      /*  defaultRegions = "SELECT distinct t.REGION_NAME,t.region_id,t.Total_revenue FROM   " + 
			        		"(SELECT b.REGION_NAME,b.REGION_ID,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE FROM  " + 
			        		" PREPAID_PAYG_REVENUE b  " + 
			        		"WHERE b.REVENUE_DATE >=  (trunc(sysdate) - 2) AND b.REVENUE_DATE < ((trunc(sysdate) - 2) + 1)    " + 
			        		"GROUP BY b.REGION_NAME, b.REGION_ID  " + 
			        		"order by Total_revenue desc) t";
			        */
	           	
	           		// region Pool:Google map Query 
				    	regionPool="SELECT distinct t.id,t.regName,t.regID,Lat,Lng from (select b.id as id,a.region_name as regName,b.region_id as regID,json_object('Region' VALUE a.region_name ,'Lat' VALUE b.latitude) as Lat,json_object('Region' VALUE a.region_name,'Lng' VALUE b.longtitude) as Lng " + 
				    			"from alm_regionborder b " + 
				    			"INNER JOIN alm_region a ON b.region_id = a.region_id " + 
				    			"group by b.id,a.region_name,b.region_id,b.latitude,b.longtitude " + 
				    			"order by b.id asc) t " + 
				    			"INNER JOIN PREPAID_PAYG_REVENUE c ON t.regID = c.region_id  " + 
				    			"WHERE c.REVENUE_DATE >=  trunc(sysdate) - 2 AND c.REVENUE_DATE < ((trunc(sysdate) - 2) + 1)  " + 
				    			"group by t.id, t.regName, t.regID, t.Lat,t.Lng " + 
				    			"order by t.id asc";
				    	
						mapRegPools = session.createSQLQuery(regionPool);
						RegPools = mapRegPools.list();
						
					//taking an array list of region and its data :
					

						model.addAttribute("LatLngReg", mapper.writeValueAsString(RegPools));	
					//DefaultRegions:Revenues for each region	  
	           		  
			        defaultRegions = "SELECT (CASE WHEN t.REGION_NAME = 'null' THEN 'No region name' WHEN NVL(t.REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE t.REGION_NAME END) REGION_NAME,(CASE WHEN t.REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(t.REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE t.REGION_ID END) REGION_ID,t.Total_revenue,(select SUM(sms_revenue+data_revenue+voice_revenue+vas_revenue) " + 
			        		"from prepaid_payg_revenue where REVENUE_DATE >=  (trunc(sysdate) - 2) AND REVENUE_DATE < ((trunc(sysdate) - 2) + 1)) as allTotal, (CASE WHEN t.Total_REVENUE < 500 THEN '0-500$' WHEN t.Total_REVENUE BETWEEN 500 AND 2000 THEN '500-2000$' ELSE 'MoreThn2000$' END) as LVL " + 
			        		"FROM (SELECT b.REGION_NAME,b.REGION_ID,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE FROM  " + 
			        		"PREPAID_PAYG_REVENUE b  " + 
			        		"WHERE b.REVENUE_DATE >= (trunc(sysdate) - 2) AND b.REVENUE_DATE < ((trunc(sysdate) - 2) + 1)  " + 
			        		"GROUP BY b.REGION_NAME, b.REGION_ID  order by b.REGION_NAME asc) t group by t.REGION_NAME, t.region_id, t.Total_revenue";
			        		
			        		
			        		
			        	/*	"SELECT (CASE WHEN t.REGION_NAME = 'null' THEN 'No region name' WHEN NVL(t.REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE t.REGION_NAME END) REGION_NAME,(CASE WHEN t.REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(t.REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE t.REGION_ID END) REGION_ID,t.Total_revenue,(select SUM(sms_revenue+data_revenue+voice_revenue+vas_revenue)  " + 
			        		"from prepaid_payg_revenue where REVENUE_DATE >=  (trunc(sysdate) - 2) AND REVENUE_DATE < ((trunc(sysdate) - 2) + 1)) as allTotal " + 
			        		"FROM  " + 
			        		"(SELECT b.REGION_NAME,b.REGION_ID,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE FROM " + 
			        		" PREPAID_PAYG_REVENUE b " + 
			        		"WHERE b.REVENUE_DATE >=  (trunc(sysdate) - 2) AND b.REVENUE_DATE < ((trunc(sysdate) - 2) + 1) " + 
			        		"GROUP BY b.REGION_NAME, b.REGION_ID " + 
			        		"order by b.REGION_NAME asc) t group by t.REGION_NAME, t.region_id, t.Total_revenue";
			        */
			        
			        defRegs = session.createSQLQuery(defaultRegions);
			        defaultRegionsList=defRegs.list();
					model.addAttribute("listRegions",mapper.writeValueAsString(defaultRegionsList));
			        
			        //System.out.println("defaultRegionsList are "+mapper.writeValueAsString(defaultRegionsList));
			        
			    	// chartssss
					// 1st chart
			    	regChrtQry = "select (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME,(CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,  " + 
				    			"(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue,revenue_date from prepaid_payg_revenue  " + 
				    			"where REVENUE_DATE >= (trunc(sysdate) - 2) AND REVENUE_DATE < ((trunc(sysdate) - 2) + 1) group by region_name, region_id, revenue_date order by region_name asc,REVENUE_DATE asc";

					defRegChrt = session.createSQLQuery(regChrtQry);
					defaultChartRevenueReport = defRegChrt.list();
					// 2nd chart and 3rd Chart
					periodicDataQuery = " select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,  coalesce(sum(voice_revenue),0) as voice_revenue,  coalesce(sum(sms_revenue),0) as sms_revenue, coalesce(sum(data_revenue),0) as data_revenue,  coalesce(sum(vas_revenue),0) as vas_revenue,  coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue  where REVENUE_DATE >= trunc(sysdate) - 2 AND REVENUE_DATE < (trunc(sysdate) - 2) + 1 group by revenue_date order by REVENUE_DATE desc";
					periodicChrtReg = session.createSQLQuery(periodicDataQuery);
					perDataChrtLst = periodicChrtReg.list();
					//Piecharts
					revPieQuery = "select  coalesce(sum(voice_revenue),0) as voice_revenue,  coalesce(sum(sms_revenue),0) as sms_revenue, coalesce(sum(data_revenue),0) as data_revenue,  coalesce(sum(vas_revenue),0) as vas_revenue,  coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= (trunc(sysdate) - 2) AND REVENUE_DATE < ((trunc(sysdate) - 2) + 1)";
					revPieReg = session.createSQLQuery(revPieQuery);
					defaultPieRev = revPieReg.list();
					
					
                    // 1st and 2nd 2 charts
					if (defaultChartRevenueReport.size()>0) {
						chartJSNArr = chartRevenues(defaultChartRevenueReport);
						model.addAttribute("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));	
					} else {
						
						model.addAttribute("msColumnChartObj", mapper.writeValueAsString(""));						
					}
					
					if (perDataChrtLst.size()>0 ) {
						defPeriodicData = getStackedChartData(perDataChrtLst);
						model.addAttribute("StackedandLineRevenue", mapper.writeValueAsString(defPeriodicData));
					}else {
						model.addAttribute("StackedandLineRevenue", mapper.writeValueAsString(""));
					}
					
					// 2nd piechart
					model.addAttribute("pieRevenue", mapper.writeValueAsString(defaultPieRev)); 
						
								
					
					/*
					if (defaultChartTechList.size()>0) {
						model.addAttribute("listChartSites", mapper.writeValueAsString(defaultChartTechList));
						chartArray = new ArrayList<Float>(PieRevPerTech(defaultChartTechList));	
						System.out.println("The chartArray is:  " + chartArray);
						model.addAttribute("chartData", mapper.writeValueAsString(chartArray));
						
					}
					else {
						model.addAttribute("listChartSites", mapper.writeValueAsString(""));
						model.addAttribute("chartData", mapper.writeValueAsString(""));
					}
					*/
					
			        }	 
				  	 
			        catch (Exception e) {
			        	logger.info("Error in creating session with the DataBase " +e.getMessage());


			        	} 
			        
			        
			        finally {
			        	if (session != null && session.isOpen()) {
			        	tx.commit();
			        	session.close();
			        	} }
				        
				       
			 }
			
				
				
			  return "Reports/RegionRevenueReport";	
			
		}
		
	 
	 
	 
	 
	 
	/* @SuppressWarnings("unchecked")
	private JSONObject regionSizing(List<Object[]> regPools) {
		 
		 ArrayList<Object> regionNames = new ArrayList<Object>(),regionsFnl = new ArrayList<Object>(), longtitude = new ArrayList<Object>(),latitude = new ArrayList<Object>();
		 JSONObject jso = new JSONObject();
		 for (Object[] regCoord : regPools) {
			 regionNames.add(regCoord[1]);
			 
		 }
		 
		 regionsFnl = getDuplicates(regionNames);
		 
		 for(int i =0; i <regionsFnl.size(); i++) {
		 for (Object[] regLatLng : regPools) {
			 
			 while(regLatLng[1] == regionsFnl.get(i)) {
				 
				 longtitude.add(regLatLng[3]);
				 latitude.add(regLatLng[4]);
			 }
			 jso.put(regionsFnl.get(i), longtitude);
			 jso.put(regionsFnl.get(i), latitude);

		 }
	 }
		 
		 return jso;
	}
*/



//Method for the generate of the Grid

	@SuppressWarnings("unchecked")
	 @RequestMapping(value = "/GenerateRegionRevenueReport", method = RequestMethod.GET)
	 @ResponseBody
	 public Map<String, Object> GenerateRegionRevenueReport(Locale locale, Model model, HttpServletRequest request)throws Exception {

	        Map<String, Object> rtn = new LinkedHashMap<>();
	        //LinkedHashMap<String, Object> rtn = new LinkedHashMap<String, Object>();
	        ObjectMapper mapper = new ObjectMapper();
	        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

	        // get date
	        String start_Date = request.getParameter("startDate");
	        Date startDate = formatter.parse(start_Date);
	        Timestamp StartDate = new Timestamp(startDate.getTime());
	        String end_Date = request.getParameter("endDate");
	        Date endDate = formatter.parse(end_Date);
	        Timestamp EndDate = new Timestamp(endDate.getTime());
	        String site = request.getParameter("site");

	        String region = request.getParameter("region");
	        String area = request.getParameter("area");
	        String technologyRegions = request.getParameter("technologyRegions");
	        String Payment_Method = request.getParameter("Payment Method");
	        System.out.println("The region is:  " +region);
	        System.out.println("The area is:  " +area);
	        System.out.println("The region is:  " +technologyRegions);
	        System.out.println("The Payment_Method is:  " +Payment_Method);
	        String technologies = request.getParameter("technologies");
	        System.out.println("The technologies is:  " +technologies);
	        String period = request.getParameter("period");
	        System.out.println("The period is:  " +period);
	        String max = request.getParameter("Max");
			System.out.println("The Max is:  " +max);
			String min = request.getParameter("Min");
			System.out.println("The Min is:  " +min);
	        String revenueOption = request.getParameter("revenueOption");
	        System.out.println("The revenueOption is:  " +revenueOption);
	        String selectedValue = request.getParameter("selectedValue");
	        System.out.println("The selectedValue is:  " +selectedValue);
	        String val1 = request.getParameter("val1");
	        String val2 = request.getParameter("val2");
	        String queryRange;

	        //List<Object[]> revenueReportList = new ArrayList<Object[]>();
	       // List<String[]> revenueSiteList = new ArrayList<String[]>();
	        	
	         
	        if(selectedValue != null) {
	        	queryRange = " and "+revenueOption+" >= DECODE("+val1+",null,0,"+val1+") AND "+revenueOption+" <= DECODE('"+val2+"','null',(select SUM("+revenueOption+") from prepaid_payg_revenue),"+val2+")";
	        }
	        else queryRange = "";
	        

	        int i = 0;
	        Query gnrlQryExec;
	        String gnrlCondQuery = "";
	        //String regionQuery = "";
	        String paramTechnologies = ""; 
	        LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
	        List<RegionRevenueReport> RegionRevenueReportFinal = new ArrayList<RegionRevenueReport>();
	        List<RegionRevenueReport> RegionRevenueReportFinalResult = new ArrayList<RegionRevenueReport>();
	        RegionRevenueReport RegionRevenueReportMax = null,RegionRevenueReportMin =  null;
	       // JSONArray revenueReportResults = new JSONArray();
	       // JSONArray finalRevenueReportResults = new JSONArray();
	        
	        Session session = null ;
	        Transaction tx= null;
	        
	    try {
	    	
	         session =almsessions.getSessionRPT();
	         tx = session.beginTransaction();
	         
	        // if region is note selected
	    	 if(StringUtils.equalsIgnoreCase(region,"null") || region == null || region == "") {
	    		    
			   
			    	 // period is daily
					  if (StringUtils.equalsIgnoreCase(period,"Daily")) {
						
						// System.out.println("/*/*Here");
				    	    // no technology is selected

				                   // IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
				                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
				                	   gnrlCondQuery  = "select t.REGION_NAME as \"regionName\",t.REGION_ID as \"regionID\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\" from (  " + 
							                	   		"select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from  " + 
							                	   		"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
							                	   		"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS')  " + 
							                	   		" order by rev_sum asc) ) t where row_nb = 1 " + 
							                	   		"UNION" + 
							                	   		"(select t.REGION_NAME as \"regionName\",t.REGION_ID as \"regionID\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\" from (  " + 
							                	   		"select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from  " + 
							                	   		"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
							                	   		"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') order by rev_sum desc) " + 
							                	   		") t where row_nb = 1) order by 1 asc,3 desc";
				                   }
				                   // IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
				                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
				                	   gnrlCondQuery =  "select t.REGION_NAME as \"regionName\",t.REGION_ID as \"regionID\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\" from (  " + 
							                	   		" select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from  " + 
							                	   		" (SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
							                	   		" FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') order by rev_sum desc) " + 
							                	   		" ) t where row_nb = 1 order by t.REGION_NAME asc,t.revenue_date desc";
				                   } 
				                   // IF  MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
				                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
				                	   gnrlCondQuery =  "select t.REGION_NAME as \"regionName\",t.REGION_ID as \"regionID\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\" from (  " + 
							                	   		" select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from  " + 
							                	   		" (SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
							                	   		" FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') order by rev_sum asc) " + 
							                	   		" ) t where row_nb = 1 order by t.REGION_NAME asc,t.revenue_date desc";
				                   }
				                   // IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
				                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
				                	   
				                	   gnrlCondQuery =  "select t.REGION_NAME AS \"regionName\",t.REGION_ID as  \"regionID\",t.revenue_date as  \"startDate\",t.revenue_date as  \"endDate\",t.voice_revenue as  \"voiceRevenue\",t.sms_revenue as  \"smsRevenue\",t.data_revenue as  \"dataRevenue\",t.vas_revenue as  \"vasRevenue\"  from ( SELECT DISTINCT (CASE WHEN b.REGION_NAME = 'null' THEN 'No region name' WHEN NVL(b.REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE b.REGION_NAME END) REGION_NAME, (CASE WHEN b.REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(b.REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE b.REGION_ID END) REGION_ID,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date," + 
							   			          		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
							   			          		"FROM PREPAID_PAYG_REVENUE b WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 " + 
							   			          		"GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS')) t order by t.REGION_NAME asc, t.revenue_date desc";
				                	  
                                       		
				                   }
				                  
						    
						 
			                 
				            gnrlQryExec = session.createSQLQuery(gnrlCondQuery);
			                gnrlQryExec.setParameter("param1", StartDate);
				    	    gnrlQryExec.setParameter("param2", EndDate);
				    	    @SuppressWarnings("unchecked")
			           		
			           		List<RegionRevenueReport> RegionRevenueReport = (List<RegionRevenueReport>) ((SQLQuery) gnrlQryExec)
			           		.addScalar("regionName").addScalar("regionID").addScalar("startDate").addScalar("endDate").addScalar("voiceRevenue")
			           		.addScalar("smsRevenue").addScalar("dataRevenue").addScalar("vasRevenue")
			           		.setResultTransformer(Transformers.aliasToBean(RegionRevenueReport.class)).list();
			                      
			                //System.out.println("****The RegionRevenueReport is:  "+mapper.writeValueAsString(RegionRevenueReport));
			                
				    	    rtn.put("revenueReportList", RegionRevenueReport); 
			 }// end daily
					  
			 // period is weekly		  
			 if (StringUtils.equalsIgnoreCase(period,"Weekly")) {
				 //System.out.println("region is not selectedd");
				 
				 //System.out.println("the start date is "+ StartDate);
   	 	         double diff =(EndDate.getTime() - StartDate.getTime()) / 1000.0;
   	 	         diff /= (60 * 60 * 24 * 7);
   	 	         //System.out.println("the week difference is before "+ diff);
   	 	         diff =  Math.abs(Math.ceil(diff));
   	 	         //System.out.println("the week difference is after "+ diff);
   	 	         Date weeklyDate;
	 	         Timestamp weeklyEndDate = StartDate;
	 	         Calendar cal = Calendar.getInstance();  
	 	     
	 	         for(i = 0; i< diff; i++) {
	 	        	 weeklyEndDate = StartDate ;
	 	        	 cal.setTime(weeklyEndDate);  
			 	     cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract   
			 	     weeklyDate = cal.getTime();   
			 	     //System.out.println(weeklyDate);
			 	     weeklyEndDate = new Timestamp(weeklyDate.getTime());
			 	     //System.out.println("for week "+i+":"+weeklyEndDate);
			 	      
	 	         
	 	    	  //the weeklyEndDate is greater than set enddate
			 	   if((weeklyEndDate).compareTo(EndDate)> 0) {
			 	    	//System.out.println("the weeklyEndDate is greater than enddate");
				 	    weeklyEndDate =  EndDate;
				   }else {
				        //System.out.println("the weeklyEndDate is less than enddate");
				   }
   	 	    

	                   // IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+weeklyEndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
		                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
			                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
			                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
			                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
			                	  ") t where row_nb = 1 "
	                			  + " UNION ("+
	                			  "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+weeklyEndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
		                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
			                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
			                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
			                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
			                	  ") t  where row_nb = 1 ) order by 1,9 asc";
	                   }
	                   // IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
	                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+weeklyEndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
		                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
			                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
			                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
			                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
			                	  ") t where row_nb = 1 order by  t.site_name,t.combination_tech asc";
	                   }
	                   // IF  MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+weeklyEndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
	                			  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
		                	   	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
		                	   	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
		                	   	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
		                	   	  ") t where row_nb = 1 order by t.site_name,t.combination_tech asc";
	                   }
	                   // IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
	                	    gnrlCondQuery = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+weeklyEndDate+"' as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID,a.COMBINATION_TECHNOLOGY as combination_tech," + 
                       		"b.site_name as site_name,b.area_id as AREA_ID , " + 
                       		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
                       		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
                       		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' " + 
                       		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id," + 
                       		"a.COMBINATION_TECHNOLOGY) t order by t.site_name,t.combination_tech asc";
                       		
	                   }
	                  
			    
			    
             
	    	    gnrlQryExec = session.createSQLQuery(gnrlCondQuery);
                gnrlQryExec.setParameter("param1", StartDate);
	    	    gnrlQryExec.setParameter("param2", weeklyEndDate);
       		    @SuppressWarnings("unchecked")
       		    List<RegionRevenueReport> RegionRevenueReport = (List<RegionRevenueReport>) ((SQLQuery) gnrlQryExec)
       			.addScalar("siteName").addScalar("areaId").addScalar("startDate").addScalar("endDate").addScalar("voiceRevenue")
       			.addScalar("smsRevenue").addScalar("dataRevenue").addScalar("vasRevenue").addScalar("combination_technology")
       			.setResultTransformer(Transformers.aliasToBean(RegionRevenueReport.class)).list();
                  
                //System.out.println("****The RegionRevenueReport is:  "+mapper.writeValueAsString(RegionRevenueReport));
                RegionRevenueReportFinal.addAll(RegionRevenueReport);
                
                StartDate = weeklyEndDate;
			    cal.setTime(StartDate);  
			 	cal.add(Calendar.DAY_OF_MONTH, 1); // 1 is the days you want to add or subtract   
			 	weeklyDate = cal.getTime();   
			 	StartDate = new Timestamp(weeklyDate.getTime());
			    //System.out.println("after start date "+StartDate);
	 	    	  
	 	        }
	    	  rtn.put("revenueReportList", RegionRevenueReportFinal); 
			 }// end if of weekly
			 
			 //start of monthly
			 if (StringUtils.equalsIgnoreCase(period,"Monthly")) {
				 //System.out.println("region is not selectedd and it is Monthly");
				 //System.out.println("the start date is "+ StartDate);
				 Calendar m =Calendar.getInstance();
				 m.setTime(StartDate);
				 int nMonth1=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
				 m.setTime(EndDate);
				 int nMonth2=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
				 int diff = Math.abs(nMonth2-nMonth1);
				 	
   	 	         //System.out.println("the Month difference is "+ diff);
   	 	         Timestamp month = StartDate;
   	 	         
   	 	         for(i = 0; i<= diff; i++) {
	 	        	
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
			        //System.out.println("MONTH " + monthofDate);
			        //System.out.println("LAST DAY OF MONTH" + endOfMonth);
	 	       
					//the endOfMonth is greater than set enddate 
			        if((endOfMonth).compareTo(EndDate)> 0) {
			    	    //System.out.println("the endOfMonth is greater than enddate");
				 	    endOfMonth =  EndDate;
				   }else {
				      //System.out.println("the endOfMonth is less than enddate");
				   }
   	 	    
			      // no technology is selected  

	                   // IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+monthofDate+"' as \"startDate\",'"+endOfMonth+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
		                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
			                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
			                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
			                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
			                	  ") t where row_nb = 1 "
	                			  + " UNION ("+
	                			  "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+monthofDate+"' as \"startDate\",'"+endOfMonth+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
		                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
			                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
			                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
			                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
			                	  ") t  where row_nb = 1 ) order by 1,9 asc";
	                   }
	                   // IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
	                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+monthofDate+"' as \"startDate\",'"+endOfMonth+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
		                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
			                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
			                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
			                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
			                	  ") t where row_nb = 1 order by  t.site_name,t.combination_tech asc";
	                   }
	                   // IF  MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+monthofDate+"' as \"startDate\",'"+endOfMonth+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
	                			  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
		                	   	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
		                	   	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
		                	   	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
		                	   	  ") t where row_nb = 1 order by t.site_name,t.combination_tech asc";
	                   }
	                   // IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
	                	    gnrlCondQuery = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",'"+monthofDate+"' as \"startDate\",'"+endOfMonth+"' as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID,a.COMBINATION_TECHNOLOGY as combination_tech," + 
                       		"b.site_name as site_name,b.area_id as AREA_ID , " + 
                       		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
                       		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
                       		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' " + 
                       		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id," + 
                       		"a.COMBINATION_TECHNOLOGY) t order by t.site_name,t.combination_tech asc";
                       		
	                   }
	                  
			    
			 
             
	                   gnrlQryExec = session.createSQLQuery(gnrlCondQuery);
                gnrlQryExec.setParameter("param1", monthofDate);
	    	    gnrlQryExec.setParameter("param2", endOfMonth);
       		    @SuppressWarnings("unchecked")
       		    List<RegionRevenueReport> RegionRevenueReport = (List<RegionRevenueReport>) ((SQLQuery) gnrlQryExec)
       			.addScalar("siteName").addScalar("areaId").addScalar("startDate").addScalar("endDate").addScalar("voiceRevenue")
       			.addScalar("smsRevenue").addScalar("dataRevenue").addScalar("vasRevenue").addScalar("combination_technology")
       			.setResultTransformer(Transformers.aliasToBean(RegionRevenueReport.class)).list();
                  
                //System.out.println("****The RegionRevenueReport is:  "+mapper.writeValueAsString(RegionRevenueReport));
                RegionRevenueReportFinal.addAll(RegionRevenueReport);
                
                calendar.setTime(endOfMonth);  
	    	    calendar.add(Calendar.DATE, +1);  
	    	    Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
                month = NextDayOfMonth;
              
	 	        }
	    	  rtn.put("revenueReportList", RegionRevenueReportFinal); 
				 
			 }// end if of monthly
			 
			 // start of acc
			 
			 if (StringUtils.equalsIgnoreCase(period,"Accu")) {
					
				 //System.out.println("/*/*period is acc and region is not selected");

				 // no technology is selected
				 if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") {

	                   // IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
		                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
			                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
			                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
			                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
			                	  ") t where row_nb = 1 "
	                			  + " UNION ("+
	                			  "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
		                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
			                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
			                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
			                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
			                	  ") t  where row_nb = 1 ) order by 1,9 asc";
	                   }
	                   // IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
	                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
		                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
			                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
			                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
			                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
			                	  ") t where row_nb = 1 order by  t.site_name,t.combination_tech asc";
	                   }
	                   // IF  MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
	                			  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
		                	   	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
		                	   	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
		                	   	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
		                	   	  ") t where row_nb = 1 order by t.site_name,t.combination_tech asc";
	                   }
	                   // IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
	                	    gnrlCondQuery = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID,a.COMBINATION_TECHNOLOGY as combination_tech," + 
                         		"b.site_name as site_name,b.area_id as AREA_ID , " + 
                         		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
                         		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
                         		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' " + 
                         		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id," + 
                         		"a.COMBINATION_TECHNOLOGY) t order by t.site_name,t.combination_tech asc";
                         		
	                   }
	                  
			    }
			 
               
				 gnrlQryExec = session.createSQLQuery(gnrlCondQuery);
                gnrlQryExec.setParameter("param1", StartDate);
	    	    gnrlQryExec.setParameter("param2", EndDate);
         		@SuppressWarnings("unchecked")
         		List<RegionRevenueReport> RegionRevenueReport = (List<RegionRevenueReport>) ((SQLQuery) gnrlQryExec)
         			.addScalar("siteName").addScalar("areaId").addScalar("startDate").addScalar("endDate").addScalar("voiceRevenue")
         			.addScalar("smsRevenue").addScalar("dataRevenue").addScalar("vasRevenue").addScalar("combination_technology")
         			.setResultTransformer(Transformers.aliasToBean(RegionRevenueReport.class)).list();
                    
              //System.out.println("****The RegionRevenueReport is:  "+mapper.writeValueAsString(RegionRevenueReport));
              
	    	    rtn.put("revenueReportList", RegionRevenueReport); 
		      }///end of accumulated

			  
	     }// end if region is null
			
	     // if region is selected	 
	     else {
	    	
	    	// period method is daily
			  if (StringUtils.equalsIgnoreCase(period,"Daily")) {
				
				 //System.out.println("/*/*Here with region");
				  
		    	 // no technology is selected
				 if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") {

	                   // IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	   gnrlCondQuery= "select t.REGION_NAME as \"regionName\",t.REGION_ID as \"regionID\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\" from (  " + 
	                	   		"select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from  " + 
	                	   		"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
	                	   		"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS')  " + 
	                	   		" order by rev_sum asc) ) t where row_nb = 1 " + 
	                	   		"UNION" + 
	                	   		"(select t.REGION_NAME as \"regionName\",t.REGION_ID as \"regionID\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\" from (  " + 
	                	   		"select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from  " + 
	                	   		"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
	                	   		"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') order by rev_sum desc) " + 
	                	   		") t where row_nb = 1) order by 1 asc,3 desc";
	                   }
	                   // IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
	                	   gnrlCondQuery =  "select t.REGION_NAME as \"regionName\",t.REGION_ID as \"regionID\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\" from (  " + 
				                	   		" select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from  " + 
				                	   		" (SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
				                	   		" FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') order by rev_sum desc) " + 
				                	   		" ) t where row_nb = 1 order by t.REGION_NAME asc,t.revenue_date desc";
	                   } 
	                   // IF  MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	   gnrlCondQuery =  "select t.REGION_NAME as \"regionName\",t.REGION_ID as \"regionID\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\" from (  " + 
				                	   		" select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from  " + 
				                	   		" (SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
				                	   		" FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') order by rev_sum asc) " + 
				                	   		" ) t where row_nb = 1 order by t.REGION_NAME asc,t.revenue_date desc";
	                   }
	                   // IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
	                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
			                gnrlCondQuery = "select t.REGION_NAME AS \"regionName\",t.REGION_ID as  \"regionID\",t.revenue_date as  \"startDate\",t.revenue_date as  \"endDate\",t.voice_revenue as  \"voiceRevenue\",t.sms_revenue as  \"smsRevenue\",t.data_revenue as  \"dataRevenue\",t.vas_revenue as  \"vasRevenue\"  from ( SELECT DISTINCT (CASE WHEN b.REGION_NAME = 'null' THEN 'No region name' WHEN NVL(b.REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE b.REGION_NAME END) REGION_NAME, (CASE WHEN b.REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(b.REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE b.REGION_ID END) REGION_ID,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date," + 
				   			          		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
				   			          		"FROM PREPAID_PAYG_REVENUE b WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.region_name LIKE '%"+region+"%'" + 
				   			          		"GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS')) t order by t.REGION_NAME asc, t.revenue_date desc";
		                	   
                         		
	                   }
	                  
			    }
			 
               
				gnrlQryExec = session.createSQLQuery(gnrlCondQuery);
                gnrlQryExec.setParameter("param1", StartDate);
	    	    gnrlQryExec.setParameter("param2", EndDate);
         		@SuppressWarnings("unchecked")
         		List<RegionRevenueReport> RegionRevenueReport = (List<RegionRevenueReport>) ((SQLQuery) gnrlQryExec)
           		.addScalar("regionName").addScalar("regionID").addScalar("startDate").addScalar("endDate").addScalar("voiceRevenue")
           		.addScalar("smsRevenue").addScalar("dataRevenue").addScalar("vasRevenue")
           		.setResultTransformer(Transformers.aliasToBean(RegionRevenueReport.class)).list();
                    
              //System.out.println("****The RegionRevenueReport is:  "+mapper.writeValueAsString(RegionRevenueReport));
	    	        
	    	    rtn.put("revenueReportList", RegionRevenueReport); 
  
	    	 
          }// DAILY ENDED
			  
			// WEEKLY STARTED WITH SITE  
			  if (StringUtils.equalsIgnoreCase(period,"Weekly")) {
		          //System.out.println("region is selectedd");
		          
		          //System.out.println("the start date is "+ StartDate);
		 	         double diff =(EndDate.getTime() - StartDate.getTime()) / 1000.0;
		 	         diff /= (60 * 60 * 24 * 7);
		 	         //System.out.println("the week difference is before "+ diff);
		 	         diff =  Math.abs(Math.ceil(diff));
		 	        // System.out.println("the week difference is after "+ diff);
		 	         Date weeklyDate;
		 	         Timestamp weeklyEndDate = StartDate;
		 	         Calendar cal = Calendar.getInstance();  
		 	     
		 	         for(i = 0; i< diff; i++) {
		 	        	 weeklyEndDate = StartDate ;
		 	        	 cal.setTime(weeklyEndDate);  
				 	     cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract   
				 	     weeklyDate = cal.getTime();   
				 	    // System.out.println(weeklyDate);
				 	     //weeklyEndDate = formatter.parse(weeklyEndDate);
				 	     weeklyEndDate = new Timestamp(weeklyDate.getTime());
				 	     //System.out.println("for week "+i+":"+weeklyEndDate);
				 	      
		 	         
		 	    	  
				       if((weeklyEndDate).compareTo(EndDate)> 0) {
				 	       System.out.println("the weeklyEndDate is greater than enddate");
				 	       weeklyEndDate =  EndDate;
				       }else {
				    	   System.out.println("the weeklyEndDate is less than enddate");
				       }
		 	    

		                   // IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
		                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
		                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+weeklyEndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
			                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
				                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
				                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
				                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
				                	  ") t where row_nb = 1 "
		                			  + " UNION ("+
		                			  "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+weeklyEndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
			                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
				                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
				                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
				                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
				                	  ") t  where row_nb = 1 ) order by 1,9 asc";
		                   }
		                   // IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
		                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
		                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+weeklyEndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
			                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
				                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
				                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
				                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
				                	  ") t where row_nb = 1 order by  t.site_name,t.combination_tech asc";
		                   }
		                   // IF  MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
		                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
		                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+weeklyEndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
		                			  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
			                	   	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
			                	   	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
			                	   	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
			                	   	  ") t where row_nb = 1 order by t.site_name,t.combination_tech asc";
		                   }
		                   // IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
		                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
		                	    gnrlCondQuery = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+weeklyEndDate+"' as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID,a.COMBINATION_TECHNOLOGY as combination_tech," + 
	                    		"b.site_name as site_name,b.area_id as AREA_ID , " + 
	                    		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
	                    		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
	                    		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' " + 
	                    		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id," + 
	                    		"a.COMBINATION_TECHNOLOGY) t order by t.site_name,t.combination_tech asc";
	                    		
		                   }
		                  
				    
				 
	          
		                   gnrlQryExec = session.createSQLQuery(gnrlCondQuery);
	                gnrlQryExec.setParameter("param1", StartDate);
		    	    gnrlQryExec.setParameter("param2", weeklyEndDate);
	    		    @SuppressWarnings("unchecked")
	    		    List<RegionRevenueReport> RegionRevenueReport = (List<RegionRevenueReport>) ((SQLQuery) gnrlQryExec)
	    			.addScalar("siteName").addScalar("areaId").addScalar("startDate").addScalar("endDate").addScalar("voiceRevenue")
	    			.addScalar("smsRevenue").addScalar("dataRevenue").addScalar("vasRevenue").addScalar("combination_technology")
	    			.setResultTransformer(Transformers.aliasToBean(RegionRevenueReport.class)).list();
	               
	                 System.out.println("****The RegionRevenueReport is:  "+mapper.writeValueAsString(RegionRevenueReport));
	                 //RegionRevenueReport tempRev = RegionRevenueReportMax =  RegionRevenueReportMin = RegionRevenueReportFinal.get(0);
	                 RegionRevenueReportFinal.addAll(RegionRevenueReport);
	             
	                StartDate = weeklyEndDate;
				    cal.setTime(StartDate);  
				 	cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract   
				 	weeklyDate = cal.getTime();   
				 	StartDate = new Timestamp(weeklyDate.getTime());
				    System.out.println("after start date "+StartDate);
		 	    	  
		 	        }
		 	         
		 	        /// max or min week for site
		 	        if(StringUtils.equalsIgnoreCase(max,"Max") || StringUtils.equalsIgnoreCase(min,"Min")) {
		 	        	int TempSumRevMax ,TempSumRevMin = 0;
		 	        	if(RegionRevenueReportFinal.size()>0) {
		 	        	RegionRevenueReport tempRev = RegionRevenueReportMax =  RegionRevenueReportMin = RegionRevenueReportFinal.get(0);
		                System.out.println("****The RegionRevenueReportFinal.get(0) is:  "+mapper.writeValueAsString(RegionRevenueReportFinal.get(0)));

		                 BigDecimal voice = tempRev.getVoiceRevenue();
		                 BigDecimal sms = tempRev.getSmsRevenue();
		                 BigDecimal data = tempRev.getdataRevenue();
		                 BigDecimal vas = tempRev.getVasRevenue();
		                 TempSumRevMax = TempSumRevMin = voice.intValue()  + sms.intValue()  + data.intValue()  + vas.intValue() ;
		 	        	  for(int j = 1; j< RegionRevenueReportFinal.size(); j++) {
		 	        		 RegionRevenueReport siteRevenue =  RegionRevenueReportFinal.get(j);
		 	                 System.out.println("****The RegionRevenueReportFinal.get("+j+") is:  "+mapper.writeValueAsString(RegionRevenueReportFinal.get(j)));
		 	               
			                 int sumRev = siteRevenue.getVoiceRevenue().intValue()  + siteRevenue.getSmsRevenue().intValue()  + siteRevenue.getdataRevenue().intValue()  + siteRevenue.getVasRevenue().intValue() ;

			                 if(TempSumRevMax < sumRev){
			                	 TempSumRevMax = sumRev;
		             		     RegionRevenueReportMax = RegionRevenueReportFinal.get(j);
		             			 System.out.println("************the RegionRevenueReportMax is "+mapper.writeValueAsString(RegionRevenueReportMax));
		             				
		             		}
		             		if(TempSumRevMin > sumRev){
		             			TempSumRevMin = sumRev;
		             			RegionRevenueReportMin = RegionRevenueReportFinal.get(j); 
		             		    System.out.println("************the RegionRevenueReportMin is "+mapper.writeValueAsString(RegionRevenueReportMin));
		             	   }
		 	        		 
			 	           
		 	        	 } 
		 	        	 
		 	        	 if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
	                        RegionRevenueReportFinalResult.add(RegionRevenueReportMin);
		 	        		RegionRevenueReportFinalResult.add(RegionRevenueReportMax);
		        		 }
		        		if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
		        			System.out.println("##########the RegionRevenueReportMax is "+RegionRevenueReportMax);
		        			RegionRevenueReportFinalResult.add(RegionRevenueReportMax);
		        			
		        		}
		        		if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
		        			System.out.println("##########the RegionRevenueReportMin is "+RegionRevenueReportMin);
		        			RegionRevenueReportFinalResult.add(RegionRevenueReportMin);
		        		}
		 	        	
		 	        }
		 	       } 
		 	       if(StringUtils.equalsIgnoreCase(max,"Max") || StringUtils.equalsIgnoreCase(min,"Min")) {
		    	      rtn.put("revenueReportList", RegionRevenueReportFinalResult); 
		 	       }else {
		 	    	  rtn.put("revenueReportList", RegionRevenueReportFinal); 
		 	       }
		 	         
		 	        //rtn.put("revenueReportList", RegionRevenueReportFinal); 


			 		   
			 
		  }// end if of weekly
		 		    
		 //start of monthly
			  if (StringUtils.equalsIgnoreCase(period,"Monthly")) {
					 //System.out.println("site is selectedd and it is Monthly");
					// System.out.println("the start date is "+ StartDate);
					 Calendar m =Calendar.getInstance();
					 m.setTime(StartDate);
					 int nMonth1=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
					 m.setTime(EndDate);
					 int nMonth2=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
					 int diff = Math.abs(nMonth2-nMonth1);
					 //YearMonthBetweenDates diffmon =new YearMonthBetweenDates();
				     //List<Timestamp> diff = diffmon.datesBetween(StartDate, EndDate);	
	   	 	        // System.out.println("the Month difference is "+ diff);
	   	 	         Timestamp month = StartDate;
	 	         
	 	            for(i = 0; i<= diff; i++) {
		 	        	
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
				        //System.out.println("MONTH " + monthofDate);
				        //System.out.println("LAST DAY OF MONTH" + endOfMonth);
		 	       
						//the endOfMonth is greater than set enddate 
				        if((endOfMonth).compareTo(EndDate)> 0) {
				    	    //System.out.println("the endOfMonth is greater than enddate");
					 	    endOfMonth =  EndDate;
					   }else {
					      //System.out.println("the endOfMonth is less than enddate");
					   }
	   	 	    

		                   // IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
		                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
		                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+monthofDate+"' as \"startDate\",'"+endOfMonth+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
			                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
				                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
				                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
				                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
				                	  ") t where row_nb = 1 "
		                			  + " UNION ("+
		                			  "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+monthofDate+"' as \"startDate\",'"+endOfMonth+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
			                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
				                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
				                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
				                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
				                	  ") t  where row_nb = 1 ) order by 1,9 asc";
		                   }
		                   // IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
		                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
		                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+monthofDate+"' as \"startDate\",'"+endOfMonth+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
			                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
				                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
				                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
				                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
				                	  ") t where row_nb = 1 order by  t.site_name,t.combination_tech asc";
		                   }
		                   // IF  MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
		                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
		                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+monthofDate+"' as \"startDate\",'"+endOfMonth+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
		                			  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
			                	   	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
			                	   	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
			                	   	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
			                	   	  ") t where row_nb = 1 order by t.site_name,t.combination_tech asc";
		                   }
		                   // IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
		                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
		                	    gnrlCondQuery = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",'"+monthofDate+"' as \"startDate\",'"+endOfMonth+"' as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID,a.COMBINATION_TECHNOLOGY as combination_tech," + 
	                       		"b.site_name as site_name,b.area_id as AREA_ID , " + 
	                       		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
	                       		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
	                       		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' " + 
	                       		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id," + 
	                       		"a.COMBINATION_TECHNOLOGY) t order by t.site_name,t.combination_tech asc";
	                       		
		                   }
		                  
				    
				 
	             
		                   gnrlQryExec = session.createSQLQuery(gnrlCondQuery);
	                gnrlQryExec.setParameter("param1", monthofDate);
		    	    gnrlQryExec.setParameter("param2", endOfMonth);
	       		    @SuppressWarnings("unchecked")
	       		    List<RegionRevenueReport> RegionRevenueReport = (List<RegionRevenueReport>) ((SQLQuery) gnrlQryExec)
	       			.addScalar("siteName").addScalar("areaId").addScalar("startDate").addScalar("endDate").addScalar("voiceRevenue")
	       			.addScalar("smsRevenue").addScalar("dataRevenue").addScalar("vasRevenue").addScalar("combination_technology")
	       			.setResultTransformer(Transformers.aliasToBean(RegionRevenueReport.class)).list();
	                  
	                //System.out.println("****The RegionRevenueReport is:  "+mapper.writeValueAsString(RegionRevenueReport));
	                RegionRevenueReportFinal.addAll(RegionRevenueReport);
	                
	                calendar.setTime(endOfMonth);  
		    	    calendar.add(Calendar.DATE, +1);  
		    	    Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
	                month = NextDayOfMonth;
	              
		 	        }
	 	            
	 	            /// max or min month for site
		 	        if(StringUtils.equalsIgnoreCase(max,"Max") || StringUtils.equalsIgnoreCase(min,"Min")) {
		 	        	int TempSumRevMax ,TempSumRevMin = 0;
		 	        	if(RegionRevenueReportFinal.size()>0) {
		 	        	RegionRevenueReport tempRev = RegionRevenueReportMax =  RegionRevenueReportMin = RegionRevenueReportFinal.get(0);
		                System.out.println("****The RegionRevenueReportFinal.get(0) is:  "+mapper.writeValueAsString(RegionRevenueReportFinal.get(0)));

		                 BigDecimal voice = tempRev.getVoiceRevenue();
		                 BigDecimal sms = tempRev.getSmsRevenue();
		                 BigDecimal data = tempRev.getdataRevenue();
		                 BigDecimal vas = tempRev.getVasRevenue();
		                 TempSumRevMax = TempSumRevMin = voice.intValue()  + sms.intValue()  + data.intValue()  + vas.intValue() ;
		 	        	  for(int j = 1; j< RegionRevenueReportFinal.size(); j++) {
		 	        		 RegionRevenueReport siteRevenue =  RegionRevenueReportFinal.get(j);
		 	                 System.out.println("****The RegionRevenueReportFinal.get("+j+") is:  "+mapper.writeValueAsString(RegionRevenueReportFinal.get(j)));
		 	               
			                 int sumRev = siteRevenue.getVoiceRevenue().intValue()  + siteRevenue.getSmsRevenue().intValue()  + siteRevenue.getdataRevenue().intValue()  + siteRevenue.getVasRevenue().intValue() ;

			                 if(TempSumRevMax < sumRev){
			                	 TempSumRevMax = sumRev;
		             		     RegionRevenueReportMax = RegionRevenueReportFinal.get(j);
		             			 System.out.println("************the RegionRevenueReportMax is "+mapper.writeValueAsString(RegionRevenueReportMax));
		             				
		             		}
		             		if(TempSumRevMin > sumRev){
		             			TempSumRevMin = sumRev;
		             			RegionRevenueReportMin = RegionRevenueReportFinal.get(j); 
		             		    System.out.println("************the RegionRevenueReportMin is "+mapper.writeValueAsString(RegionRevenueReportMin));
		             	   }
		 	        		 
			 	           
		 	        	 } 
		 	        	 
		 	        	 if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
	                        RegionRevenueReportFinalResult.add(RegionRevenueReportMin);
		 	        		RegionRevenueReportFinalResult.add(RegionRevenueReportMax);
		        		 }
		        		if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
		        			System.out.println("##########the RegionRevenueReportMax is "+RegionRevenueReportMax);
		        			RegionRevenueReportFinalResult.add(RegionRevenueReportMax);
		        			
		        		}
		        		if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
		        			System.out.println("##########the RegionRevenueReportMin is "+RegionRevenueReportMin);
		        			RegionRevenueReportFinalResult.add(RegionRevenueReportMin);
		        		}
		 	        	
		 	        }
		 	       } 
		 	       if(StringUtils.equalsIgnoreCase(max,"Max") || StringUtils.equalsIgnoreCase(min,"Min")) {
		    	      rtn.put("revenueReportList", RegionRevenueReportFinalResult); 
		 	       }else {
		 	    	  rtn.put("revenueReportList", RegionRevenueReportFinal); 
		 	       }
	 	           //rtn.put("revenueReportList", RegionRevenueReportFinal); 
	 	            
				 }// end if of monthly
		 
					 // start of acc 
					 if (StringUtils.equalsIgnoreCase(period,"Accu")) {
							
						 //System.out.println("/*/*period is acc and site is selected");

						 // no technology is selected
						 if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") {

			                   // IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
			                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
			                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
				                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
					                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
					                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
					                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
					                	  ") t where row_nb = 1 "
			                			  + " UNION ("+
			                			  "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
				                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
					                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
					                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
					                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
					                	  ") t  where row_nb = 1 ) order by 1,9 asc";
			                   }
			                   // IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
			                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
			                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
				                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
					                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
					                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
					                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
					                	  ") t where row_nb = 1 order by  t.site_name,t.combination_tech asc";
			                   }
			                   // IF  MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
			                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
			                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
			                			  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
				                	   	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
				                	   	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' "+
				                	   	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
				                	   	  ") t where row_nb = 1 order by t.site_name,t.combination_tech asc";
			                   }
			                   // IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
			                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
			                	    gnrlCondQuery = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID,a.COMBINATION_TECHNOLOGY as combination_tech," + 
		                         		"b.site_name as site_name,b.area_id as AREA_ID , " + 
		                         		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
		                         		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
		                         		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' " + 
		                         		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id," + 
		                         		"a.COMBINATION_TECHNOLOGY) t order by t.site_name,t.combination_tech asc";
		                         		
			                   }
			                  
					    }
					    // if there is a technology	
			    	    else if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g5g")){
					    	
					    	if(StringUtils.equalsIgnoreCase(technologies,"2g")){
					    		 System.out.println("****The techology is 2g  ");
					    		 paramTechnologies = " a.tech_2g='1'and a.tech_3g='0' and a.tech_4g='0' ";
					    	}
					    	if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
					    		 System.out.println("****The techology is 2g3g  ");
					    		 paramTechnologies = " a.tech_2g='1'and a.tech_3g='1' and a.tech_4g='0' ";						    	}
					    	if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
				                 System.out.println("****The techology is 2g3g4g  ");
				                 paramTechnologies = " a.tech_2g='1'and a.tech_3g='1' and a.tech_4g='1' ";
				            }     
				                   // IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
				                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
				                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
						                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
							                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
							                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
							                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
							                  ") t where row_nb = 1 "
					                		  + " UNION ("+
					                	      "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
						                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
							                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
							                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
							                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
							                  ") t  where row_nb = 1 ) order by 1,9 asc";

				                   }
				                   // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
				                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
				                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
						                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
							                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
							                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
							                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
							                  ") t where row_nb = 1 order by  t.site_name,t.combination_tech asc";
				                   }
				                   // IF  MIN IS ONLY CHECKED WITH TECHNOLOGY
				                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
				                	   gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
					                		  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
						                	  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
						                	  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
						                	  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
						                	  ") t where row_nb = 1 order by t.site_name,t.combination_tech asc";
				                   }
				                   // IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
				                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
				                	   gnrlCondQuery = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID,a.COMBINATION_TECHNOLOGY as combination_tech," + 
				                         		"b.site_name as site_name,b.area_id as AREA_ID , " + 
				                         		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
				                         		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
				                         		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+ 
				                         		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id," + 
				                         		"a.COMBINATION_TECHNOLOGY) t order by t.site_name,t.combination_tech asc";
				                         
						           }
			    	    }
					 
		               
						 gnrlQryExec = session.createSQLQuery(gnrlCondQuery);
		                gnrlQryExec.setParameter("param1", StartDate);
			    	    gnrlQryExec.setParameter("param2", EndDate);
		         		@SuppressWarnings("unchecked")
		         		List<RegionRevenueReport> RegionRevenueReport = (List<RegionRevenueReport>) ((SQLQuery) gnrlQryExec)
		         			.addScalar("siteName").addScalar("areaId").addScalar("startDate").addScalar("endDate").addScalar("voiceRevenue")
		         			.addScalar("smsRevenue").addScalar("dataRevenue").addScalar("vasRevenue").addScalar("combination_technology")
		         			.setResultTransformer(Transformers.aliasToBean(RegionRevenueReport.class)).list();
		                    
		              //System.out.println("****The RegionRevenueReport is:  "+mapper.writeValueAsString(RegionRevenueReport));
		              
			    	  rtn.put("revenueReportList", RegionRevenueReport); 
				      }///end of accumulated
            
		  
}			 
	
      //maximum exist
     //minimum null
    // site null
   // no technology is selected
	   if(StringUtils.equalsIgnoreCase(period,"null") ) {
			if ( StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null) ){
	               System.out.println("Max");
	               gnrlCondQuery = "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
             	           "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
	                       "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
	                       "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "+
	                       "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
	                       ") t where row_nb = 1 order by  t.site_name,t.combination_tech asc";
	         }
	             
	             
	         //maximum null
	         //minimum exist
	       // site null
	      // no technology is selected
	      
	          if ( StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min") ) {
	        	  gnrlCondQuery = "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
            	          "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
	                      "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
	                      "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "+
	                      "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
	                       ") t where row_nb = 1 order by  t.site_name,t.combination_tech asc";
	       
	                }    
	     //maximum exist
	     //minimum exist
	    //no technology 
	    //no site 



	      if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min") ){
	     
	    	  gnrlCondQuery= "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
	                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
	                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "+
	                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum asc)" + 
	                  ") t where row_nb = 1 "
            		  + " UNION ("+
            	      "select t.site_name as \"siteName\",t.area_id as \"areaId\",'"+StartDate+"' as \"startDate\",'"+EndDate+"' as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevenue\", t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( " + 
                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
	                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id,a.COMBINATION_TECHNOLOGY as combination_tech, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
	                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2  "+
	                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id,a.COMBINATION_TECHNOLOGY order by rev_sum desc)" + 
	                  ") t  where row_nb = 1 ) order by 1,9 asc";
	            }        
	
			
	      gnrlQryExec = session.createSQLQuery(gnrlCondQuery);
          gnrlQryExec.setParameter("param1", StartDate);
  	      gnrlQryExec.setParameter("param2", EndDate);
   		  @SuppressWarnings("unchecked")
   		  List<RegionRevenueReport> RegionRevenueReport = (List<RegionRevenueReport>) ((SQLQuery) gnrlQryExec)
   		  .addScalar("siteName").addScalar("areaId").addScalar("startDate").addScalar("endDate").addScalar("voiceRevenue")
   		  .addScalar("smsRevenue").addScalar("dataRevenue").addScalar("vasRevenue").addScalar("combination_technology")
   	      .setResultTransformer(Transformers.aliasToBean(RegionRevenueReport.class)).list();
              
        
  	      rtn.put("revenueReportList", RegionRevenueReport); 
			
			
		}
		
	     	} catch (Exception e) {
	        	logger.info("Error in creating session with Database", e);
	        	}
		        finally {
			      if (session != null && session.isOpen()) {
			         tx.commit();
			         session.close();
			      }
		        }

				
	



	        return rtn;
	}
	  
	 @SuppressWarnings("unchecked")
		@RequestMapping(value = "/GenerateRegionRevenueCharts", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> GenerateRegionRevenueCharts(Locale locale, Model model, HttpServletRequest request)
				throws Exception {

			Map<String, Object> rtn = new LinkedHashMap<>();
			// LinkedHashMap<String, Object> rtn = new LinkedHashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			List<Object> ChartRevenueReport = new ArrayList<Object>();
			List<Object[]> perDataChrtLst = new ArrayList<Object[]>();
			List<Object> periodicData = new ArrayList<Object>();
			List<Object> pieRev = new ArrayList<Object>();
			List<Object> pieRevChart = new ArrayList<Object>();
			List<Object[]> regionResult = new ArrayList<Object[]>();
			ArrayList<Object> chartArray;

			JSONArray chartJSNArr;

			// get date
			String start_Date = request.getParameter("startDate");
			Date startDate = formatter.parse(start_Date);
			Timestamp StartDate = new Timestamp(startDate.getTime());
			String end_Date = request.getParameter("endDate");
			Date endDate = formatter.parse(end_Date);
			Timestamp EndDate = new Timestamp(endDate.getTime());
			String site = request.getParameter("site");
			String region = request.getParameter("region");

			System.out.println("the reegion is "+region);
			String Payment_Method = request.getParameter("Payment Method");

			String period = request.getParameter("period");
			String max = request.getParameter("Max");
			String min = request.getParameter("Min");
			String revenueOption = request.getParameter("revenueOption");
			String selectedValue = request.getParameter("selectedValue");
			String minVal = request.getParameter("minVal");
			String maxVal = request.getParameter("maxVal");
			String queryRange;
			
			JSONArray preparePeriodicDataJsn = new JSONArray();
			List<Object[]> revenueChartsList = new ArrayList<Object[]>();
			List<String[]> revenueSiteList = new ArrayList<String[]>();

			if (selectedValue != null) {
				queryRange = " and " + revenueOption + " >= DECODE(" + minVal + ",null,0," + minVal + ") AND "
						+ revenueOption + " <= DECODE('" + maxVal + "','null',(select SUM(" + revenueOption
						+ ") from prepaid_payg_revenue)," + maxVal + ")";
			} else
				queryRange = "";

			int i = 0;
			Query msData, periodData, pieRevData, pieRegData;
			String msChrtQry = "", stackChrtQry = "", pieRevQry = "", pieRegQry = "";
			LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
			JSONArray revenueReportResults = new JSONArray();
			JSONArray finalRevenueReportResults = new JSONArray();
			
			Session session = almsessions.getSessionRPT();
			Transaction tx = session.beginTransaction();
			try {

				if (StringUtils.equalsIgnoreCase(region, "null") || region == null || region == "") {
					
					Session session1 = almsessions.getSessionRPT();
					Transaction tx1 = session1.beginTransaction();
					try {
						// payment method is daily
						if (StringUtils.equalsIgnoreCase(period, "Daily")) {

							//System.out.println("/*/*Here");

								// IF MAX AND MIN IS CHECKED 
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, "Min")) {

									
									 msChrtQry = "select t.REGION_NAME,t.REGION_ID, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
										 		"select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
										 		"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
										 		"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >=  :param1 and b.REVENUE_DATE <=  :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')    " + 
										 		" order by rev_sum asc) ) t where row_nb = 1 group by t.REGION_NAME, t.REGION_ID  " + 
										 		"UNION  " + 
										 		"(select t.REGION_NAME,t.REGION_ID, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
										 		"select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
										 		"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
										 		"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >=  :param1 and b.REVENUE_DATE <=  :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)   " + 
										 		") t where row_nb = 1 group by t.REGION_NAME, t.REGION_ID) order by 1 asc";

									 stackChrtQry = "select revenue_date, sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from("+
									 			 	"select t.REVENUE_DATE as revenue_date, coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
										 			"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
										 			"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
										 			"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')    " + 
										 			"order by rev_sum asc) ) t where row_nb = 1  group by t.REVENUE_DATE " + 
										 			" UNION " + 
										 			"(select t.REVENUE_DATE as revenue_date, coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
										 			"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
										 			"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
										 			"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)   " + 
										 			") t where row_nb = 1  group by t.REVENUE_DATE) order by 1 asc) group by revenue_date  order by revenue_date desc";

									 	pieRevQry = "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from(select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
									 				"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
									 				"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
									 				"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')    " + 
									 				" order by rev_sum asc) ) t where row_nb = 1 " + 
									 				"UNION  " + 
									 				"(select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
									 				"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
									 				"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
									 				"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)   " + 
									 				") t where row_nb = 1))";
											

								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, null)) {
									msChrtQry = "select t.REGION_NAME,t.REGION_ID, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (  " + 
				                	   		"select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from  " + 
				                	   		"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
				                	   		"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc) " + 
				                	   		") t where row_nb = 1 group by t.REGION_NAME, t.REGION_ID order by t.REGION_NAME asc";

									stackChrtQry = "select t.REVENUE_DATE as revenue_date, coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from ( " + 
											"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
											"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
											"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)   " + 
											") t where row_nb = 1  group by t.REVENUE_DATE order by t.REVENUE_DATE desc";

									pieRevQry = "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
							 				"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
							 				"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
							 				"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)   " + 
							 				") t where row_nb = 1";
								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, "Min")) {
									msChrtQry = "select t.REGION_NAME,t.REGION_ID, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (  " + 
				                	   		"select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from  " + 
				                	   		"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
				                	   		"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc) " + 
				                	   		") t where row_nb = 1 group by t.REGION_NAME, t.REGION_ID order by t.REGION_NAME asc";

									stackChrtQry = "select t.REVENUE_DATE as revenue_date, coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from ( " + 
											"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
											"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
											"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)   " + 
											") t where row_nb = 1  group by t.REVENUE_DATE order by t.REVENUE_DATE desc";

									pieRevQry = "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
							 				"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
							 				"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
							 				"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)   " + 
							 				") t where row_nb = 1";
								}
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
									msChrtQry = "select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME,region_ID, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
											+ region + "%' group by region_name, region_id order by region_name asc";
									stackChrtQry = "select DISTINCT TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
											+ region + "%' group by TO_CHAR(revenue_date, 'DD/MM/YYYY') order by revenue_date desc";
									pieRevQry = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
											+ region + "%' ";
									
								}

						

							
							
								msData = session.createSQLQuery(msChrtQry);
								msData.setParameter("param1", StartDate);
								msData.setParameter("param2", EndDate);
								ChartRevenueReport = msData.list();
								

								periodData = session.createSQLQuery(stackChrtQry);
								periodData.setParameter("param1", StartDate);
								periodData.setParameter("param2", EndDate);
								perDataChrtLst = periodData.list();
								
								pieRegData = session.createSQLQuery(pieRevQry);
								pieRegData.setParameter("param1", StartDate);
								pieRegData.setParameter("param2", EndDate);
								pieRev = pieRegData.list();

								if (ChartRevenueReport.size()>0) {
									chartJSNArr = chartRevenues(ChartRevenueReport);

									rtn.put("msColumnChartObj",  mapper.writeValueAsString(chartJSNArr));
								} else {
									rtn.put("msColumnChartObj",  mapper.writeValueAsString(""));
								}
								if (pieRev.size()>0) {
									rtn.put("pieRevenue", mapper.writeValueAsString(pieRev));
								} else {
									rtn.put("pieRevenue",  mapper.writeValueAsString(""));
								}
								if (perDataChrtLst.size()>0) {
									periodicData = getStackedChartData(perDataChrtLst);

									rtn.put("StackedandLineRevenue",  mapper.writeValueAsString(periodicData));
								} else {
									rtn.put("StackedandLineRevenue",  mapper.writeValueAsString(""));
								}
								

						}
						// payment method is weekly
						if (StringUtils.equalsIgnoreCase(period, "Weekly")) {

							System.out.println("region is not selectedd");
							
							if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
							//System.out.println("the start date is " + StartDate);
							double diff = (EndDate.getTime() - StartDate.getTime()) / 1000.0;
							diff /= (60 * 60 * 24 * 7);
							//System.out.println("the week difference is before " + diff);
							diff = Math.abs(Math.ceil(diff));
							//System.out.println("the week difference is after " + diff);

							Date weeklyDate;
							Timestamp weeklyEndDate = StartDate;
							Calendar cal = Calendar.getInstance();
							
							
							

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
						/*		if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, "Min")) {
									query = "select site_name,AREA_ID,sum(voice_rev),sum(sms_rev),sum(data_rev),sum(vas_rev),sum(rev_sum) from("
											+ "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "" + "UNION ("
											+ "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + ")) group by site_name,AREA_ID order by site_name asc";

									query2 = "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from, ("
											+ "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce((sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "" + "UNION ("
											+ "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce((sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + "))";

									query3 = "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from ( select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "" + "UNION ("
											+ "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + "))";
								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, null)) {
									query = "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,coalesce(rev_sum,0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + " ";
									
									query2 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce((sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query3 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + " ";
								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, "Min")) {
									query = "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,coalesce((sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query2 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce((sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query3 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + " ";
								}*/
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
									msChrtQry = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%'  " + queryRange + " group by site_name, area_id";
									stackChrtQry = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%'  " + queryRange + "";
									pieRevQry = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%'  " + queryRange + "";

								}

							
							
							
							
								msData = session.createSQLQuery(msChrtQry);
								msData.setParameter("param1", StartDate);
								msData.setParameter("param2", EndDate);

								ChartRevenueReport = msData.list();

							
								
								pieRegData = session.createSQLQuery(pieRevQry);
								pieRegData.setParameter("param1", StartDate);
								pieRegData.setParameter("param2", EndDate);
								pieRev = pieRegData.list();

								if (ChartRevenueReport.size()>0) {
									chartJSNArr = chartRevenues(ChartRevenueReport);

									rtn.put("msColumnChartObj",   mapper.writeValueAsString(chartJSNArr));
								} else {
									rtn.put("msColumnChartObj",  mapper.writeValueAsString(""));

								}
								if (pieRev.size()>0) {
									rtn.put("pieRevenue",  mapper.writeValueAsString(pieRev));

								} else {
									rtn.put("pieRevenue",  mapper.writeValueAsString(""));
								}

								
								Timestamp weeklyStartDate = StartDate;
								for (i = 0; i < diff; i++) {
									
									weeklyEndDate = weeklyStartDate;
									cal.setTime(weeklyEndDate);
									cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									System.out.println(weeklyDate);
									// weeklyEndDate = formatter.parse(weeklyEndDate);
									weeklyEndDate = new Timestamp(weeklyDate.getTime());
									System.out.println("for week " + i + ":" + weeklyEndDate);
									periodData = session.createSQLQuery(stackChrtQry);
									periodData.setParameter("param1", weeklyStartDate);
									
									if ((weeklyEndDate).compareTo(EndDate) > 0) {
										System.out.println("the weeklyEndDate is greater than enddate");
										periodData.setParameter("param2", EndDate);
										revenueChartsList = periodData.list();
										System.out.println("the revenueChartsList"+ mapper.writeValueAsString(revenueChartsList));
										preparePeriodicDataJsn.add(preparePeriodicData(String.valueOf(weeklyStartDate),String.valueOf(EndDate), revenueChartsList));

									} else {
										System.out.println("the weeklyEndDate is less than enddate");
										periodData.setParameter("param2", weeklyEndDate);
										revenueChartsList = periodData.list();
										System.out.println("the revenueChartsList"+ mapper.writeValueAsString(revenueChartsList));
										preparePeriodicDataJsn.add(preparePeriodicData(String.valueOf(weeklyStartDate),String.valueOf(weeklyEndDate), revenueChartsList));

									}

									
									weeklyStartDate = weeklyEndDate;
									cal.setTime(weeklyStartDate);
									cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									weeklyStartDate = new Timestamp(weeklyDate.getTime());

									System.out.println("after start date " + weeklyStartDate);

								}
								
								//System.out.println("after preparePeriodicDataJsn " + preparePeriodicDataJsn);
								finalRevenueReportResults.add(getPeriodicChartData(preparePeriodicDataJsn));
								rtn.put("StackedandLineRevenue",  mapper.writeValueAsString(finalRevenueReportResults));
								
								
						}
						else {
							//System.out.println("else max null");
							rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
							rtn.put("pieRevenue", mapper.writeValueAsString(""));
							rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));	
						}
						
						
							
						}// ended weekly 
						
						//start of monthly
						  if (StringUtils.equalsIgnoreCase(period,"Monthly")) {
							//  System.out.println("Monthlyyyyy");
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("pieRevenue", mapper.writeValueAsString(""));
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));	
						  }
						  // start of acc 
							 if (StringUtils.equalsIgnoreCase(period,"Accu")) {
								 System.out.println("acccccc");
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));	
							 }
						
						

					} catch (Exception e) {
						logger.info("Error in creating session with Database", e);
					} finally {
						if (session1 != null && session1.isOpen()) {
							tx1.commit();
							session1.close();
						}
					}

				} // end if region is null

				else {
					// if site is exist
					Session session2 = almsessions.getSessionRPT();
					Transaction tx2 = session2.beginTransaction();
					try {
						// payment method is daily
						if (StringUtils.equalsIgnoreCase(period, "Daily")) {

							//System.out.println("/*/*Here with region");
							// no technology is selected

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
							if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, "Min")) {

								
								 msChrtQry = "select t.REGION_NAME,t.REGION_ID, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
									 		"select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
									 		"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
									 		"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >=  :param1 and b.REVENUE_DATE <=  :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')    " + 
									 		" order by rev_sum asc) ) t where row_nb = 1 group by t.REGION_NAME, t.REGION_ID  " + 
									 		"UNION  " + 
									 		"(select t.REGION_NAME,t.REGION_ID, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
									 		"select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
									 		"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
									 		"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >=  :param1 and b.REVENUE_DATE <=  :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)   " + 
									 		") t where row_nb = 1 group by t.REGION_NAME, t.REGION_ID) order by 1 asc";

								 stackChrtQry = "select revenue_date, sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from("+
								 			 	"select t.REVENUE_DATE as revenue_date, coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
									 			"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
									 			"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
									 			"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')    " + 
									 			"order by rev_sum asc) ) t where row_nb = 1  group by t.REVENUE_DATE " + 
									 			" UNION " + 
									 			"(select t.REVENUE_DATE as revenue_date, coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
									 			"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
									 			"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
									 			"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)   " + 
									 			") t where row_nb = 1  group by t.REVENUE_DATE) order by 1 asc) group by revenue_date  order by revenue_date desc";

								 	pieRevQry = "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from(select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
								 				"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
								 				"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
								 				"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')    " + 
								 				" order by rev_sum asc) ) t where row_nb = 1 " + 
								 				"UNION  " + 
								 				"(select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
								 				"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
								 				"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
								 				"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)   " + 
								 				") t where row_nb = 1))";
										

							}
							// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
							if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, null)) {
								msChrtQry = "select t.REGION_NAME,t.REGION_ID, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (  " + 
			                	   		"select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from  " + 
			                	   		"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
			                	   		"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc) " + 
			                	   		") t where row_nb = 1 group by t.REGION_NAME, t.REGION_ID order by t.REGION_NAME asc";

								stackChrtQry = "select t.REVENUE_DATE as revenue_date, coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from ( " + 
										"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
										"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)   " + 
										") t where row_nb = 1  group by t.REVENUE_DATE order by t.REVENUE_DATE desc";

								pieRevQry = "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
						 				"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
						 				"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
						 				"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)   " + 
						 				") t where row_nb = 1";
							}
							// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, "Min")) {
								msChrtQry = "select t.REGION_NAME,t.REGION_ID, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (  " + 
			                	   		"select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME, (CASE WHEN REGION_ID = 'null' THEN 'No REGION_ID' WHEN NVL(REGION_ID,'No REGION_ID') = 'No REGION_ID' THEN 'No REGION_ID' ELSE REGION_ID END) REGION_ID,revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from  " + 
			                	   		"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
			                	   		"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc) " + 
			                	   		") t where row_nb = 1 group by t.REGION_NAME, t.REGION_ID order by t.REGION_NAME asc";

								stackChrtQry = "select t.REVENUE_DATE as revenue_date, coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from ( " + 
										"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
										"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)   " + 
										") t where row_nb = 1  group by t.REVENUE_DATE order by t.REVENUE_DATE desc";

								pieRevQry = "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas,coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) as tot_revenue from (    " + 
						 				"select DISTINCT revenue_date ,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from    " + 
						 				"(SELECT DISTINCT b.REGION_NAME,b.REGION_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue    " + 
						 				"FROM  PREPAID_PAYG_REVENUE b  WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.REGION_NAME LIKE '%"+region+"%' GROUP BY b.REGION_NAME, b.REGION_ID, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)   " + 
						 				") t where row_nb = 1";
							}
							// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
								msChrtQry = "select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME,region_ID, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
										+ region + "%' group by region_name, region_id order by region_name asc";
								stackChrtQry = "select DISTINCT TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
										+ region + "%' group by TO_CHAR(revenue_date, 'DD/MM/YYYY') order by revenue_date desc";
								pieRevQry = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
										+ region + "%' ";
								
							}

							
								msData = session.createSQLQuery(msChrtQry);
							msData.setParameter("param1", StartDate);
							msData.setParameter("param2", EndDate);
							ChartRevenueReport = msData.list();

							periodData = session.createSQLQuery(stackChrtQry);
							periodData.setParameter("param1", StartDate);
							periodData.setParameter("param2", EndDate);
							perDataChrtLst = periodData.list();

							pieRegData = session.createSQLQuery(pieRevQry);
							pieRegData.setParameter("param1", StartDate);
							pieRegData.setParameter("param2", EndDate);
							pieRev= pieRegData.list();

							if (ChartRevenueReport.size()>0) {
								chartJSNArr = chartRevenues(ChartRevenueReport);

								rtn.put("msColumnChartObj",  mapper.writeValueAsString(chartJSNArr));
							} else {
								rtn.put("msColumnChartObj",  mapper.writeValueAsString(""));
							}
							if (pieRev.size()>0) {
								rtn.put("pieRevenue",  mapper.writeValueAsString(pieRev));
							} else {
								rtn.put("pieRevenue",  mapper.writeValueAsString(""));
							}
							if (perDataChrtLst.size()>0) {
								periodicData = getStackedChartData(perDataChrtLst);

								rtn.put("StackedandLineRevenue", mapper.writeValueAsString( periodicData));
							} else {
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
							}
							

						}// end of daily
						// payment method is weekly
						if (StringUtils.equalsIgnoreCase(period, "Weekly")) {


							System.out.println("site is selectedd");
							if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
							
							System.out.println("the start date is " + StartDate);
							double diff = (EndDate.getTime() - StartDate.getTime()) / 1000.0;
							diff /= (60 * 60 * 24 * 7);
							System.out.println("the week difference is before " + diff);
							diff = Math.abs(Math.ceil(diff));
							System.out.println("the week difference is after " + diff);

							Date weeklyDate;
							Timestamp weeklyStartDate = StartDate;
							Timestamp weeklyEndDate = StartDate;
							Calendar cal = Calendar.getInstance();
							
							

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
								/*if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, "Min")) {
									query = "select site_name,AREA_ID,sum(voice_rev),sum(sms_rev),sum(data_rev),sum(vas_rev),sum(rev_sum) from("
											+ "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "" + "UNION ("
											+ "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + ")) group by site_name,AREA_ID order by site_name asc";

									query2 = "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from ("
											+ "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,(sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "" + "UNION ("
											+ "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,(sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + "))";

									query3 = "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from ( select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "" + "UNION ("
											+ "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + "))";
								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, null)) {
									query = "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query2 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),(sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query3 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + " ";
								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, "Min")) {
									query = "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query2 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),(sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query3 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + " ";
								}*/
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
									msChrtQry = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%'  " + queryRange + " group by site_name, area_id";
									stackChrtQry = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%'  " + queryRange + "";
									pieRevQry = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%'  " + queryRange + "";

								}

							
							
							
							
								msData = session.createSQLQuery(msChrtQry);
								msData.setParameter("param1", StartDate);
								msData.setParameter("param2", EndDate);

								ChartRevenueReport = msData.list();

							
								
								pieRegData = session.createSQLQuery(pieRevQry);
								pieRegData.setParameter("param1", StartDate);
								pieRegData.setParameter("param2", EndDate);
								pieRev = pieRegData.list();

								if (ChartRevenueReport.size()>0) {
									chartJSNArr = chartRevenues(ChartRevenueReport);

									rtn.put("msColumnChartObj",  mapper.writeValueAsString(chartJSNArr));
								} else {
									rtn.put("msColumnChartObj",  mapper.writeValueAsString(""));
								}
								System.out.println("the pie rev "+mapper.writeValueAsString(pieRev));
								if (pieRev.size()>0) {
									
									rtn.put("pieRevenue",  mapper.writeValueAsString(pieRev));
								} else {
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
								}

								

								for (i = 0; i < diff; i++) {
									
									weeklyEndDate = weeklyStartDate;
									cal.setTime(weeklyEndDate);
									cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									System.out.println(weeklyDate);
									// weeklyEndDate = formatter.parse(weeklyEndDate);
									weeklyEndDate = new Timestamp(weeklyDate.getTime());
									System.out.println("for week " + i + ":" + weeklyEndDate);
									periodData = session.createSQLQuery(stackChrtQry);
									periodData.setParameter("param1", weeklyStartDate);
									
									if ((weeklyEndDate).compareTo(EndDate) > 0) {
										System.out.println("the weeklyEndDate is greater than enddate");
										periodData.setParameter("param2", EndDate);
										revenueChartsList = periodData.list();
										preparePeriodicDataJsn.add(preparePeriodicData(String.valueOf(weeklyStartDate),String.valueOf(EndDate), revenueChartsList));

									} else {
										System.out.println("the weeklyEndDate is less than enddate");
										periodData.setParameter("param2", weeklyEndDate);
										revenueChartsList = periodData.list();
										preparePeriodicDataJsn.add(preparePeriodicData(String.valueOf(weeklyStartDate),String.valueOf(weeklyEndDate), revenueChartsList));

									}

									
									weeklyStartDate = weeklyEndDate;
									cal.setTime(weeklyStartDate);
									cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									weeklyStartDate = new Timestamp(weeklyDate.getTime());

									System.out.println("after start date " + weeklyStartDate);

								}
								
								
								finalRevenueReportResults.add(getPeriodicChartData(preparePeriodicDataJsn));
								rtn.put("StackedandLineRevenue",  mapper.writeValueAsString(finalRevenueReportResults));
								
							
						
							}
							else {
								System.out.println("else max null with site");
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("pieRevenue", mapper.writeValueAsString(""));
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));	
							}
						
							
						}//ended weekly
						
						//start of monthly
						  if (StringUtils.equalsIgnoreCase(period,"Monthly")) {
							  System.out.println("Monthlyyyyy");
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("pieRevenue", mapper.writeValueAsString(""));
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));	
						  }
						  // start of acc 
							 if (StringUtils.equalsIgnoreCase(period,"Accu")) {
								 System.out.println("acccccc");
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));	
							 }
						
					} catch (Exception e) {
						logger.info("Error in creating session with Database", e);
					} finally {
						if (session2 != null && session2.isOpen()) {
							tx2.commit();
							session2.close();
						}
					}
				}
				
				// last chart
				// no technology is selected
				     // IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
	                if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	pieRegQry = "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
	                              +"UNION "+
	                              "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1";
	                              
	                }
	                // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
	                if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
	                	//pieRegQry = "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,date_rev,ROW_NUMBER() over(PARTITION by date_rev order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc))where row_nb = 1 group by technology";
	                    pieRegQry = "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1";

	                }
	                // IF MIN IS ONLY CHECKED WITH TECHNOLOGY
	                if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	//pieRegQry = "select tech as technology, sum(sites) as sum_total,sum(Total_REVENUE) as totalRevSum from (select tech , sites ,Total_REVENUE ,ROW_NUMBER() over(order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech,b.REVENUE_DATE,b.SITE_NAME,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G') group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc)) where row_nb IN (1,2) group by tech";
	                	//pieRegQry = "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from( select tech as technology, sites as sum_total,MIN(Total_REVENUE) as totalRevSum,date_rev from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev,b.SITE_NAME,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology  In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc) group by tech, sites, date_rev) group by technology";

	                	//pieRegQry = "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,date_rev,ROW_NUMBER() over(PARTITION by date_rev order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc))where row_nb = 1 group by technology";
	                      pieRegQry = "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1";
	                }
	                // NO MAX AND MIN ONLY CHECKED WITH TECHNOLOGY
	                if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
	    				//pieRegQry = "select a.combination_technology as tech,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology order by a.combination_technology asc";
	                	pieRegQry = "SELECT t.REGION_NAME,t.region_id,t.Total_revenue, (select SUM(sms_revenue+data_revenue+voice_revenue+vas_revenue) " + 
	                			" from prepaid_payg_revenue where REVENUE_DATE >= :param1 AND REVENUE_DATE <= :param2 and region_name LIKE '%"+region+"%' ) as allTotal , (CASE WHEN t.Total_REVENUE < 500 THEN '0-500$' WHEN t.Total_REVENUE BETWEEN 500 AND 2000 THEN '500-2000$' ELSE 'MoreThn2000$' END) as LVL " + 
				        		"FROM " + 
				        		"(SELECT (CASE WHEN b.REGION_NAME = 'null' THEN 'No region name' WHEN NVL(b.REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ElSE b.REGION_NAME END) REGION_NAME,b.REGION_ID,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE FROM " + 
				        		"PREPAID_PAYG_REVENUE b " + 
				        		"WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2  and b.region_name LIKE '%"+region+"%'" + 
				        		"GROUP BY b.REGION_NAME, b.REGION_ID " + 
				        		"order by b.REGION_NAME asc) t group by t.REGION_NAME, t.region_id, t.Total_revenue"; 
				   
				        
				 
	                }

				
				pieRegData = session.createSQLQuery(pieRegQry);
				pieRegData.setParameter("param1", StartDate);
				pieRegData.setParameter("param2", EndDate);
				regionResult = pieRegData.list();
				//System.out.println("The regionResult is:  " + mapper.writeValueAsString(regionResult));
				//chartArray = new ArrayList<Object>(PieRevPerTech(regionResult));
				//System.out.println("#########The chartArray is:  " + chartArray);
				if(regionResult.size()>0) {
					rtn.put("listRegions", mapper.writeValueAsString(regionResult));
					//rtn.put("chartData", chartArray);
				}else {
					rtn.put("listRegions", mapper.writeValueAsString(""));
					//rtn.put("chartData", "");
				}
				

			} catch (Exception e) {
				logger.info("Error in creating session with Database", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}

			return rtn;
		}

	 @SuppressWarnings("unchecked")
		@RequestMapping(value = "/FilteringRegionMethod", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> FilteringRegionMethod(Locale locale, Model model, HttpServletRequest request)
				throws Exception {

			Map<String, Object> rtn = new LinkedHashMap<>();
			// LinkedHashMap<String, Object> rtn = new LinkedHashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			List<Object> ChartRevenueReport = new ArrayList<Object>();
			List<Object[]> perDataChrtLst = new ArrayList<Object[]>();
			List<Object> periodicData = new ArrayList<Object>();
			List<Object> pieRev = new ArrayList<Object>();
			List<Object[]> regionResult = new ArrayList<Object[]>();

			JSONArray chartJSNArr;

			// get date
			String start_Date = request.getParameter("startDate");
			Date startDate = formatter.parse(start_Date);
			Timestamp StartDate = new Timestamp(startDate.getTime());
			String end_Date = request.getParameter("endDate");
			Date endDate = formatter.parse(end_Date);
			Timestamp EndDate = new Timestamp(endDate.getTime());
			String region = request.getParameter("region");

			//String site = request.getParameter("site");
			//String area = request.getParameter("area");
			//String technologyRegions = request.getParameter("technologyRegions");
			String Payment_Method = request.getParameter("Payment Method");

			String technologies = request.getParameter("technologies");
			String period = request.getParameter("period");
			String max = request.getParameter("Max");
			String min = request.getParameter("Min");
			String revenueOption = request.getParameter("revenueOption");
			String selectedValue = request.getParameter("selectedValue");
			String minVal = request.getParameter("minVal");
			String maxVal = request.getParameter("maxVal");
			String filterID = request.getParameter("filterID");
			String queryRange;

			List<Object[]> revenueChartsList = new ArrayList<Object[]>();
			List<String[]> revenueSiteList = new ArrayList<String[]>();

			if (selectedValue != null) {
				queryRange = " and " + revenueOption + " >= DECODE(" + minVal + ",null,0," + minVal + ") AND "
						+ revenueOption + " <= DECODE('" + maxVal + "','null',(select SUM(" + revenueOption
						+ ") from prepaid_payg_revenue)," + maxVal + ")";
			} else
				queryRange = "";

			int i = 0;
			Query msQry, periodicQry, pieRevQryStrng, pieRegQryString;
			String regChrtQry = "", regperiodicQry = "", pieRevQry = "", pieRegQry = "";
			String siteQuery = "";
			LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
			JSONArray revenueReportResults = new JSONArray();
			JSONArray preparePeriodicDataJsn = new JSONArray();
			JSONArray finalRevenueReportResults = new JSONArray();
			String paramTechnologies = ""; 
			Session session=null;
			Transaction tx=null;
			try {
				 session = almsessions.getSessionRPT();
				 tx = session.beginTransaction();
				if (StringUtils.equalsIgnoreCase(region, "null") || region == null || region == "") {
					
						// payment method is daily
						//if (StringUtils.equalsIgnoreCase(period, "Daily")) {

							System.out.println("/*/*Here");
							// no technology is selected
							if (StringUtils.equalsIgnoreCase(technologies, "null") || technologies == null
									|| technologies == "") {

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, "Min")) {

								    switch (filterID) {
							        case "msChrtApply":
							        	regChrtQry = "select site_name,AREA_ID,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),sum(rev_sum) from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where row_nb = 1 " + queryRange + " group by site_name, AREA_ID  "
												+ " UNION ("
												+ "select site_name,AREA_ID,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),,sum(rev_sum) from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by site_name, AREA_ID  ) order by site_name asc";
							        	
							        			break;
							        			
							        case "stackedChrtApply":

							        	regperiodicQry = "select revenue_date, sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(total_rev) from("
												+ "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,SUM(voice_revenue) as tot_voice,SUM(sms_revenue) as tot_sms,SUM(data_revenue) as tot_data,SUM(vas_revenue) as tot_vas,sum(rev_sum) as total_rev from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY')" + " UNION ("
												+ "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,SUM(voice_revenue) as tot_voice,SUM(sms_revenue) as tot_sms,SUM(data_revenue) as tot_data,SUM(vas_revenue) as tot_vas,sum(rev_sum) as total_rev from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY')) order by revenue_date asc)"
												+ "group by revenue_date order by revenue_Date desc";
							        	
							        	break;
							        	
							        case "lineChrtApply":
							        	regperiodicQry = "select revenue_date, sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(total_rev) from("
												+ "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,SUM(voice_revenue) as tot_voice,SUM(sms_revenue) as tot_sms,SUM(data_revenue) as tot_data,SUM(vas_revenue) as tot_vas,sum(rev_sum) as total_rev from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY')" + " UNION ("
												+ "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,SUM(voice_revenue) as tot_voice,SUM(sms_revenue) as tot_sms,SUM(data_revenue) as tot_data,SUM(vas_revenue) as tot_vas,sum(rev_sum) as total_rev from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY')) order by revenue_date asc)"
												+ "group by revenue_date order by revenue_Date desc";
							        	break;
							        	
							        case "revPieApply":
							        	pieRevQry = "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from("
												+ "select SUM(voice_revenue) as tot_voice,SUM(sms_revenue) as tot_sms,SUM(data_revenue) as tot_data,SUM(vas_revenue) as tot_vas,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where row_nb = 1 " + queryRange + "" + " UNION ("
												+ "select SUM(voice_revenue) as tot_voice,SUM(sms_revenue) as tot_sms,SUM(data_revenue) as tot_data,SUM(vas_revenue) as tot_vas,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where row_nb = 1 " + queryRange + " ))";
							        	break;
							        	
								    }
									
									

									

								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, null)) {
									
								    switch (filterID) {
								    case "msChrtApply":
								    	regChrtQry = "select site_name,AREA_ID,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),sum(rev_sum) as total_rev from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange
											+ " group by site_name, AREA_ID  order by site_name asc";
									break;
								    case "stackedChrtApply":
								    	regperiodicQry = "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,SUM(voice_revenue),SUM(sms_revenue),SUM(data_revenue),SUM(vas_revenue),sum(rev_sum) as total_rev from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange
											+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY') order by revenue_date desc";
								    break;
								    case "lineChrtApply":
								    	regperiodicQry = "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,SUM(voice_revenue),SUM(sms_revenue),SUM(data_revenue),SUM(vas_revenue),sum(rev_sum) as total_rev from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY') order by revenue_date desc";
									    break;
								    case "revPieApply":
								    	pieRevQry = "select SUM(voice_revenue),SUM(sms_revenue),SUM(data_revenue),SUM(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "";
									break;
								    }
								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, "Min")) {
									
								    switch (filterID) {
								    case "msChrtApply":
								    	regChrtQry = "select site_name,AREA_ID,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),sum(rev_sum) as total_rev from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange
											+ " group by site_name, AREA_ID  order by site_name asc";
									break;
								    case "stackedChrtApply":
								    	regperiodicQry = "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),sum(rev_sum) as total_rev from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange
											+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY')  order by revenue_date desc";
									break;
								    case "lineChrtApply":
								    	regperiodicQry = "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),sum(rev_sum) as total_rev from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY')  order by revenue_date desc";
								    	break;
								    case "revPieApply":
								    	pieRevQry = "select sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + "";
									break;
								    }
								}
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
									
									
								    switch (filterID) {
								    case "msChrtApply":
								    	regChrtQry = "select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME,region_ID, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
											+ region + "%' " + queryRange
											+ " group by region_name, region_id order by region_name asc";
									break;
								    case "stackedChrtApply":
								    	regperiodicQry = "select DISTINCT TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
											+ region + "%'  " + queryRange
											+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY') order by revenue_date desc";
									break;
								    case "lineChrtApply":
								    	regperiodicQry = "select DISTINCT TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
												+ region + "%'  " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY') order by revenue_date desc";
										break;
								    case "revPieApply":
								    	pieRevQry = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
											+ region + "%' " + queryRange + " ";
									break;
									
								    case "regPieApply":
								    	pieRegQry = "SELECT t.REGION_NAME,t.region_id,t.Total_revenue, (select SUM(sms_revenue+data_revenue+voice_revenue+vas_revenue) " + 
								    		" from prepaid_payg_revenue where REVENUE_DATE >= :param1 AND REVENUE_DATE <= :param2 and region_name LIKE '%"+region+"%' ) as allTotal , (CASE WHEN t.Total_REVENUE < 500 THEN '0-500$' WHEN t.Total_REVENUE BETWEEN 500 AND 2000 THEN '500-2000$' ELSE 'MoreThn2000$' END) as LVL " +
							        		"FROM " + 
							        		"(SELECT (CASE WHEN b.REGION_NAME = 'null' THEN 'No region name' WHEN NVL(b.REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ElSE b.REGION_NAME END) REGION_NAME,b.REGION_ID,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE FROM " + 
							        		"PREPAID_PAYG_REVENUE b " + 
							        		"WHERE b.REVENUE_DATE >=  :param1 AND b.REVENUE_DATE <= :param2  and b.region_name LIKE '%"+region+"%' " + 
							        		"GROUP BY b.REGION_NAME, b.REGION_ID " + 
							        		"order by b.REGION_NAME asc) t group by t.REGION_NAME, t.region_id, t.Total_revenue";
									break;
								    }

								}

				
							}
							// if there is a technology
						/*	else if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g5g")){
						    	
						    	if(StringUtils.equalsIgnoreCase(technologies,"2g")){
						    		 System.out.println("****The techology is 2g  ");
						    		 paramTechnologies = " a.tech_2g='1'and a.tech_3g='0' and a.tech_4g='0' ";
						    	}
						    	if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
						    		 System.out.println("****The techology is 2g3g  ");
						    		 paramTechnologies = " a.tech_2g='1'and a.tech_3g='1' and a.tech_4g='0' ";						    	}
						    	if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
					                 System.out.println("****The techology is 2g3g4g  ");
					                 paramTechnologies = " a.tech_2g='1'and a.tech_3g='1' and a.tech_4g='1' ";
					            }     
					                   // IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
										    switch (filterID) {
										    case "msChrtApply":
					                	   query="select t.site_name,t.area_id,sum(t.voice_revenue), sum(t.sms_revenue),sum(t.data_revenue), sum(t.vas_revenue),sum(t.rev_sum) from ( " + 
							                	 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
								                 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
								                 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
								                 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
								                 ") t where row_nb = 1 " + queryRange + " group by t.site_name,t.area_id"
					                			 + "UNION ("+
					                			 "select t.site_name,t.area_id,sum(t.voice_revenue), sum(t.sms_revenue),sum(t.data_revenue), sum(t.vas_revenue),sum(t.rev_sum) from ( " + 
						                		 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
							                	 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
							                	 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
							                	 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
							                	 ") t where row_nb = 1 " + queryRange + " group by t.site_name,t.area_id) order by 1 asc";
					                	   break;
										    case "stackedChrtApply":
										    
					                	   query2="select revenue_date, sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(total_sum) from ("
					                	   		+ "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms,sum(t.data_revenue) as tot_data, sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum from ( " + 
								                	 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
									                 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
									                 ") t where row_nb = 1  " + queryRange + ""
						                			 + "UNION ("+
						                			 "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms,sum(t.data_revenue) as tot_data, sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum from ( " + 
							                		 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
								                	 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
								                	 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
								                	 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
								                	 ") t where row_nb = 1 " + queryRange + ") order by 3 asc)  group by revenue_date order by revenue_date desc";
					                	   break;
										    case "lineChrtApply":
										    	query2="select revenue_date, sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(total_sum) from ("
							                	   		+ "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms,sum(t.data_revenue) as tot_data, sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum from ( " + 
										                	 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
											                 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
											                 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
											                 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
											                 ") t where row_nb = 1  " + queryRange + ""
								                			 + "UNION ("+
								                			 "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms,sum(t.data_revenue) as tot_data, sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum from ( " + 
									                		 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
										                	 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                	 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                	 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
										                	 ") t where row_nb = 1 " + queryRange + ") order by 3 asc)  group by revenue_date order by revenue_date desc";
							                	   break;
										    case "revPieApply":
										    	query3="select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0),coalesce(sum(tot_data),0),coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from (select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms,sum(t.data_revenue) as tot_data, sum(t.vas_revenue) as tot_vas from ( " + 
									                	 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
										                 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
										                 ") t where row_nb = 1  " + queryRange + ""
							                			 + "UNION ("+
							                			 "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms,sum(t.data_revenue) as tot_data, sum(t.vas_revenue) as tot_vas from ( " + 
								                		 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
									                	 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                	 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                	 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
									                	 ") t where row_nb = 1 " + queryRange + ") order by 3 asc)";
										    	break;
					                	   
										    }

					                   }
					                   // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
										    switch (filterID) {
										    case "msChrtApply":
					                	   query= "select t.site_name,t.area_id,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum  from ( " + 
							                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
								                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
								                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
								                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
								                  ") t where row_nb = 1 " + queryRange + " group by t.site_name, t.area_id order by t.site_name asc";
					                	   break;
										    case "stackedChrtApply":
										    	query2= "select t.revenue_date , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum  from ( " + 
									                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
										                  ") t where row_nb = 1 " + queryRange + " group by t.revenue_date order by t.revenue_date desc";
										    	break;
					                	   
					                	   
										    case "lineChrtApply":
										    	query2= "select t.revenue_date , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum  from ( " + 
									                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
										                  ") t where row_nb = 1 " + queryRange + " group by t.revenue_date order by t.revenue_date desc";
										    	break;
										    case "revPieApply":
										    	query3= "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) "
							                	   		+ "from (select t.revenue_date , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas  from ( " + 
										                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
											                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
											                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
											                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
											                  ") t where row_nb = 1 " + queryRange + " group by t.revenue_date order by t.revenue_date asc)";
										    	break;
					                	   
										    }
					                   }
					                   // IF  MIN IS ONLY CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
										    switch (filterID) {
										    case "msChrtApply":
										    	query= "select t.site_name,t.area_id,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum  from ( " + 
									                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
										                  ") t where row_nb = 1 " + queryRange + " group by t.site_name, t.area_id order by t.site_name asc";
										    	break;
					                	   
										    case "stackedChrtApply":
										    	query2= "select t.revenue_date , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum  from ( " + 
									                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
										                  ") t where row_nb = 1 " + queryRange + " group by t.revenue_date order by t.revenue_date desc";
										    	break;
										    case "lineChrtApply":
										    	query2= "select t.revenue_date , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum  from ( " + 
									                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
										                  ") t where row_nb = 1 " + queryRange + " group by t.revenue_date order by t.revenue_date desc";
										    	break;
										    case "revPieApply":
										    	query3= "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) "
							                	   		+ "from (select t.revenue_date , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas  from ( " + 
										                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
											                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
											                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
											                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
											                  ") t where row_nb = 1 " + queryRange + " group by t.revenue_date order by t.revenue_date asc)";
										    	break;
										    } 
					                   }
					                   // IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
										    switch (filterID) {
										    case "msChrtApply":
										    	query = "select t.site_name,t.area_id ,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
			                                       		"b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, " + 
			                                       		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
			                                       		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
			                                       		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and " + paramTechnologies +" " + queryRange + "" + 
			                                       		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')" + 
			                                       		")  t group by t.site_name, t.area_id order by t.site_name asc";
										    	break;
							                  
										    case "stackedChrtApply":

							                  query2 = "select t.revenue_date ,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
			                                       		"b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, " + 
			                                       		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
			                                       		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
			                                       		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and " + paramTechnologies +" " + queryRange + "" + 
			                                       		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')" + 
			                                       		") t group by t.revenue_date order by t.revenue_date desc";
							                  break;
										    case "lineChrtApply":

								                  query2 = "select t.revenue_date ,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
				                                       		"b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, " + 
				                                       		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
				                                       		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
				                                       		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and " + paramTechnologies +" " + queryRange + "" + 
				                                       		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')" + 
				                                       		") t group by t.revenue_date order by t.revenue_date desc";
								                  break;
										    case "revPieApply":
										    	query3 = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0)"
								                  		+ "from (select t.revenue_date ,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas  from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
				                                       		"b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, " + 
				                                       		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
				                                       		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
				                                       		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and " + paramTechnologies +" " + queryRange + "" + 
				                                       		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')" + 
				                                       		") t group by t.revenue_date order by t.revenue_date asc)";
										    	break;

							                  
					                   }
							           }
					                  
				    	    }
							*/
							
							
						    switch (filterID) {
						    case "msChrtApply":
						    	msQry = session.createSQLQuery(regChrtQry);
							 msQry.setParameter("param1", StartDate);
							 msQry.setParameter("param2", EndDate);
								ChartRevenueReport = msQry.list();
								if (ChartRevenueReport.size()>0) {
									chartJSNArr = chartRevenues(ChartRevenueReport);

									rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
									}
								else {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								}
								break;
						    case "stackedChrtApply":
						    	periodicQry = session.createSQLQuery(regperiodicQry);
						    	periodicQry.setParameter("param1", StartDate);
								periodicQry.setParameter("param2", EndDate);
								perDataChrtLst = periodicQry.list();
								System.out.println("######StackedandLineRevenue "+ periodicData);
								if (perDataChrtLst.size()>0) {

								periodicData = getStackedChartData(perDataChrtLst);
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(periodicData));

								}
								else {
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));

								}
								break;
						    case "lineChrtApply":
						    	periodicQry = session.createSQLQuery(regperiodicQry);
						    	periodicQry.setParameter("param1", StartDate);
						    	periodicQry.setParameter("param2", EndDate);
								perDataChrtLst = periodicQry.list();
								System.out.println("######StackedandLineRevenue "+ periodicData);
								if (perDataChrtLst.size()>0) {

									periodicData = getStackedChartData(perDataChrtLst);
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(periodicData));

								}
								else {
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));

								}
								break;
						    case "revPieApply":
						    	pieRevQryStrng = session.createSQLQuery(pieRevQry);
								pieRevQryStrng.setParameter("param1", StartDate);
								pieRevQryStrng.setParameter("param2", EndDate);
								pieRev = pieRevQryStrng.list();

								if (pieRev.size()>0) {
									rtn.put("pieRevenue", mapper.writeValueAsString(pieRev));

								} else {
									rtn.put("pieRevenue", mapper.writeValueAsString(""));

								}
						    	break;
								
						    case "regPieApply":

						    	pieRegQryString = session.createSQLQuery(pieRegQry);
								pieRegQryString.setParameter("param1", StartDate);
								pieRegQryString.setParameter("param2", EndDate);
								regionResult = pieRegQryString.list();
								//System.out.println("The regionResult is:  " + mapper.writeValueAsString(regionResult));
								//chartArray = new ArrayList<Object>(PieRevPerTech(regionResult));
								//System.out.println("#########The chartArray is:  " + chartArray);
								if(regionResult.size()>0) {
									rtn.put("listRegions", mapper.writeValueAsString(regionResult));
									//rtn.put("chartData", chartArray);
								}else {
									rtn.put("listRegions", mapper.writeValueAsString(""));
									//rtn.put("chartData", "");
								}
								break;
						}
								
						//}
					/*	if (StringUtils.equalsIgnoreCase(period, "Weekly")) {
							System.out.println("site is not selectedd");
							
							
							System.out.println("the start date is " + StartDate);
							double diff = (EndDate.getTime() - StartDate.getTime()) / 1000.0;
							diff /= (60 * 60 * 24 * 7);
							System.out.println("the week difference is before " + diff);
							diff = Math.abs(Math.ceil(diff));
							System.out.println("the week difference is after " + diff);

							Date weeklyDate;
							Timestamp weeklyEndDate = StartDate;
							Calendar cal = Calendar.getInstance();
							
							
							if (StringUtils.equalsIgnoreCase(technologies, "null") || technologies == null
									|| technologies == "") {

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, "Min")) {
									query = "select site_name,AREA_ID,sum(voice_rev),sum(sms_rev),sum(data_rev),sum(vas_rev) from("
											+ "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "" + "UNION ("
											+ "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + ")) group by site_name,AREA_ID order by site_name asc";

									query2 = "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas) from ("
											+ "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "" + "UNION ("
											+ "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + "))";

									query3 = "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from ( select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "" + "UNION ("
											+ "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + "))";
								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, null)) {
									query = "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query2 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0) from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query3 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + " ";
								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, "Min")) {
									query = "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query2 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0) from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query3 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + " ";
								}
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
									query = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' " + queryRange + " group by site_name, area_id";
									query2 = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' " + queryRange + "";
									query3 = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' " + queryRange + "";

								}

							}
							// if there is a technology
							else if (StringUtils.equalsIgnoreCase(technologies, "2g") || StringUtils.equalsIgnoreCase(technologies, "2g3g")	|| StringUtils.equalsIgnoreCase(technologies, "2g3g4g") || StringUtils.equalsIgnoreCase(technologies, "2g3g4g5g")) {
								
								if(StringUtils.equalsIgnoreCase(technologies,"2g")){
						    		 System.out.println("****The techology is 2g  ");
						    		 paramTechnologies = " a.tech_2g='1'and a.tech_3g='0' and a.tech_4g='0' ";
						    	}
						    	if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
						    		 System.out.println("****The techology is 2g3g  ");
						    		 paramTechnologies = " a.tech_2g='1'and a.tech_3g='1' and a.tech_4g='0' ";
						    		 }
						    	if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
					                 System.out.println("****The techology is 2g3g4g  ");
					                 paramTechnologies = " a.tech_2g='1'and a.tech_3g='1' and a.tech_4g='1' ";
					            }     
					                   // IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
					                	   query= "select site_name,area_id,coalesce(sum(voice_revenue),0),coalesce(sum(sms_revenue),0),coalesce(sum(data_revenue),0),coalesce(sum(vas_revenue),0) from ("+ 
					                	   		"select t.site_name,t.area_id, t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( " + 
							                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
								                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
								                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
								                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)" + 
								                  ") t where row_nb = 1 "
						                		  + " UNION ("+
						                	      "select t.site_name,t.area_id, t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( " + 
							                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
								                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
								                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
								                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)" + 
								                  ") t  where row_nb = 1 ) order by 1 asc) group by site_name,AREA_ID order by site_name";
					                	   
					                	   query2= "  select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0),coalesce(sum(tot_data),0),coalesce(sum(tot_vas),0) from ("
					                	   			+ "	select  t.voice_revenue as tot_voice, t.sms_revenue as tot_sms,t.data_revenue as tot_data, t.vas_revenue as tot_vas from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)" + 
									                  ") t where row_nb = 1 "
							                		  + " UNION ("+
							                	      "select  t.voice_revenue as tot_voice, t.sms_revenue as tot_sms,t.data_revenue as tot_data, t.vas_revenue as tot_vas from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)" + 
									                  ") t  where row_nb = 1 ))";
					                	   
					                	   query3= "  select coalesce(sum(voice_revenue),0) as tot_voice,coalesce(sum(sms_revenue),0) as tot_sms,coalesce(sum(data_revenue),0) as tot_data,coalesce(sum(vas_revenue),0) as tot_vas,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_rev from ("
					                	   			+ "	select  t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)" + 
									                  ") t where row_nb = 1 "
							                		  + " UNION ("+
							                	      "select  t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)" + 
									                  ") t  where row_nb = 1 ))";

					                   }
					                   // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {      //'"+StartDate+"','"+weeklyEndDate+"',
					                	   
					                	   query= "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0) from ( " + 
							                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
								                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
								                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
								                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)" + 
								                  ") t where row_nb = 1 group by t.site_name,t.area_id order by  t.site_name asc";
					                	   
					                	   query2= "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0) from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)" + 
									                  ") t where row_nb = 1 order by t.site_name asc";
					                	   
					                	   query3= "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)" + 
									                  ") t where row_nb = 1 order by t.site_name asc";
					                	   
					                   }
					                   // IF  MIN IS ONLY CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
					                	   query= "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0) from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)" + 
									                  ") t where row_nb = 1 group by t.site_name,t.area_id order by  t.site_name asc";
						                	   
						                	   query2= "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0) from ( " + 
									                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)" + 
										                  ") t where row_nb = 1 order by t.site_name asc";
						                	   
						                	   query3= "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) from ( " + 
									                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)" + 
										                  ") t where row_nb = 1 order by t.site_name asc";
					                   }
					                   // IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
					                	   query = "select t.site_name,t.area_id,coalesce(sum(t.voice_revenue),0),coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0),coalesce(sum(t.vas_revenue),0) from ("
					                	   		+ " SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
					                         		"b.site_name as site_name,b.area_id as AREA_ID , " + 
					                         		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
					                         		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
					                         		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+ 
					                         		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id) t group by t.site_name,t.area_id order by t.site_name asc";
					                	   
					                	   query2 = "select coalesce(sum(t.voice_revenue),0),coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0),coalesce(sum(t.vas_revenue),0) from ("
					                	   		+ " SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
					                         		"b.site_name as site_name,b.area_id as AREA_ID , " + 
					                         		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
					                         		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
					                         		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+ 
					                         		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id) t order by t.site_name asc";
					                         
					                	   query3 = "select coalesce(sum(t.voice_revenue),0),coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0),coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) from ("
						                	   		+ " SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
						                         		"b.site_name as site_name,b.area_id as AREA_ID , " + 
						                         		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
						                         		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
						                         		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+ 
						                         		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id) t order by t.site_name asc";
							           }
								
								}
							
							
							
								q = session.createSQLQuery(query);
								q.setParameter("param1", StartDate);
								q.setParameter("param2", EndDate);

								ChartRevenueReport = q.list();
								chartJSNArr = chartRevenues(ChartRevenueReport);

							
								
								pieRevQryStrng = session.createSQLQuery(query3);
								pieRevQryStrng.setParameter("param1", StartDate);
								pieRevQryStrng.setParameter("param2", EndDate);
								pieRev = pieRevQryStrng.list();

								if (ChartRevenueReport != null) {
									rtn.put("msColumnChartObj", chartJSNArr);
								} else {
									rtn.put("msColumnChartObj", "");

								}
								if (pieRev != null) {
									rtn.put("pieRevenue", mapper.writeValueAsString(pieRev));

								} else {
									rtn.put("pieRevenue", "");

								}

								

								for (i = 0; i < diff; i++) {
									
									weeklyEndDate = StartDate;
									cal.setTime(weeklyEndDate);
									cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									System.out.println(weeklyDate);
									// weeklyEndDate = formatter.parse(weeklyEndDate);
									weeklyEndDate = new Timestamp(weeklyDate.getTime());
									System.out.println("for week " + i + ":" + weeklyEndDate);
									q2 = session.createSQLQuery(query2);
									q2.setParameter("param1", StartDate);
									
									if ((weeklyEndDate).compareTo(EndDate) > 0) {
										System.out.println("the weeklyEndDate is greater than enddate");
										q2.setParameter("param2", EndDate);
										revenueChartsList = q2.list();
										preparePeriodicDataJsn.add(preparePeriodicData(String.valueOf(StartDate),String.valueOf(EndDate), revenueChartsList));

									} else {
										System.out.println("the weeklyEndDate is less than enddate");
										q2.setParameter("param2", weeklyEndDate);
										revenueChartsList = q2.list();
										preparePeriodicDataJsn.add(preparePeriodicData(String.valueOf(StartDate),String.valueOf(weeklyEndDate), revenueChartsList));

									}

									
									StartDate = weeklyEndDate;
									cal.setTime(StartDate);
									cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									StartDate = new Timestamp(weeklyDate.getTime());

									System.out.println("after start date " + StartDate);

								}
								
								
								finalRevenueReportResults.add(getPeriodicChartData(preparePeriodicDataJsn));
								rtn.put("StackedandLineRevenue", finalRevenueReportResults);
								
							
						
						} // end if of weekly*/

					

				} // end if site is null

				else {
					// if site is exist
					
					
						// payment method is daily
						//if (StringUtils.equalsIgnoreCase(period, "Daily")) {

							System.out.println("/*/*Here");
							// no technology is selected
							if (StringUtils.equalsIgnoreCase(technologies, "null") || technologies == null
									|| technologies == "") {

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, "Min")) {

								    switch (filterID) {
							        case "msChrtApply":
							        	regChrtQry = "select site_name,AREA_ID,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),sum(rev_sum) from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%'  group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where row_nb = 1 " + queryRange + " group by site_name, AREA_ID  "
												+ " UNION ("
												+ "select site_name,AREA_ID,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),,sum(rev_sum) from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%'  group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by site_name, AREA_ID  ) order by site_name asc";
							        	
							        			break;
							        			
							        case "stackedChrtApply":

							        	regperiodicQry = "select revenue_date, sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(total_rev) from("
												+ "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,SUM(voice_revenue) as tot_voice,SUM(sms_revenue) as tot_sms,SUM(data_revenue) as tot_data,SUM(vas_revenue) as tot_vas,sum(rev_sum) as total_rev from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%'  group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY')" + " UNION ("
												+ "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,SUM(voice_revenue) as tot_voice,SUM(sms_revenue) as tot_sms,SUM(data_revenue) as tot_data,SUM(vas_revenue) as tot_vas,sum(rev_sum) as total_rev from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%'  group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY')) order by revenue_date asc)"
												+ "group by revenue_date order by revenue_Date desc";
							        	
							        	break;
							        	
							        case "lineChrtApply":
							        	regperiodicQry = "select revenue_date, sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(total_rev) from("
												+ "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,SUM(voice_revenue) as tot_voice,SUM(sms_revenue) as tot_sms,SUM(data_revenue) as tot_data,SUM(vas_revenue) as tot_vas,sum(rev_sum) as total_rev from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%'  group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY')" + " UNION ("
												+ "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,SUM(voice_revenue) as tot_voice,SUM(sms_revenue) as tot_sms,SUM(data_revenue) as tot_data,SUM(vas_revenue) as tot_vas,sum(rev_sum) as total_rev from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%'  group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY')) order by revenue_date asc)"
												+ "group by revenue_date order by revenue_Date desc";
							        	break;
							        	
							        case "revPieApply":
							        	pieRevQry = "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from("
												+ "select SUM(voice_revenue) as tot_voice,SUM(sms_revenue) as tot_sms,SUM(data_revenue) as tot_data,SUM(vas_revenue) as tot_vas,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%'  group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where row_nb = 1 " + queryRange + "" + " UNION ("
												+ "select SUM(voice_revenue) as tot_voice,SUM(sms_revenue) as tot_sms,SUM(data_revenue) as tot_data,SUM(vas_revenue) as tot_vas,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where row_nb = 1 " + queryRange + " ))";
							        	break;
							        	
								    }
									
									

									

								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, null)) {
									
								    switch (filterID) {
								    case "msChrtApply":
								    	regChrtQry = "select site_name,AREA_ID,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),sum(rev_sum) as total_rev from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange
											+ " group by site_name, AREA_ID  order by site_name asc";
									break;
								    case "stackedChrtApply":
								    	regperiodicQry = "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,SUM(voice_revenue),SUM(sms_revenue),SUM(data_revenue),SUM(vas_revenue),sum(rev_sum) as total_rev from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange
											+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY') order by revenue_date desc";
								    break;
								    case "lineChrtApply":
								    	regperiodicQry = "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,SUM(voice_revenue),SUM(sms_revenue),SUM(data_revenue),SUM(vas_revenue),sum(rev_sum) as total_rev from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%'  group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY') order by revenue_date desc";
									    break;
								    case "revPieApply":
								    	pieRevQry = "select SUM(voice_revenue),SUM(sms_revenue),SUM(data_revenue),SUM(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "";
									break;
								    }
								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, "Min")) {
									
								    switch (filterID) {
								    case "msChrtApply":
								    	regChrtQry = "select site_name,AREA_ID,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),sum(rev_sum) as total_rev from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange
											+ " group by site_name, AREA_ID  order by site_name asc";
									break;
								    case "stackedChrtApply":
								    	regperiodicQry = "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),sum(rev_sum) as total_rev from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange
											+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY')  order by revenue_date desc";
									break;
								    case "lineChrtApply":
								    	regperiodicQry = "select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),sum(rev_sum) as total_rev from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
												+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where row_nb = 1 " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY')  order by revenue_date desc";
								    	break;
								    case "revPieApply":
								    	pieRevQry = "select sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ region + "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + "";
									break;
								    }
								}
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {
								    case "msChrtApply":
								    	regChrtQry = "select DISTINCT (CASE WHEN REGION_NAME = 'null' THEN 'No region name' WHEN NVL(REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ELSE REGION_NAME END) REGION_NAME,region_ID, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
											+ region + "%' " + queryRange
											+ " group by region_name, region_id order by region_name asc";
									break;
								    case "stackedChrtApply":
								    	regperiodicQry = "select DISTINCT TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
											+ region + "%'  " + queryRange
											+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY') order by revenue_date desc";
									break;
								    case "lineChrtApply":
								    	regperiodicQry = "select DISTINCT TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
												+ region + "%'  " + queryRange
												+ " group by TO_CHAR(revenue_date, 'DD/MM/YYYY') order by revenue_date desc";
										break;
								    case "revPieApply":
								    	pieRevQry = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and region_name LIKE '%"
											+ region + "%' " + queryRange + " ";
									break;
								    case "regPieApply":
								    	pieRegQry = "SELECT t.REGION_NAME,t.region_id,t.Total_revenue,(select SUM(sms_revenue+data_revenue+voice_revenue+vas_revenue) " + 
								    		" from prepaid_payg_revenue where REVENUE_DATE >= :param1 AND REVENUE_DATE <= :param2 and region_name LIKE '%"+region+"%' ) as allTotal , (CASE WHEN t.Total_REVENUE < 500 THEN '0-500$' WHEN t.Total_REVENUE BETWEEN 500 AND 2000 THEN '500-2000$' ELSE 'MoreThn2000$' END) as LVL " +
							        		"FROM " + 
							        		"(SELECT (CASE WHEN b.REGION_NAME = 'null' THEN 'No region name' WHEN NVL(b.REGION_NAME,'No region name') = 'No region name' THEN 'No region name' ElSE b.REGION_NAME END) REGION_NAME,b.REGION_ID,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE FROM " + 
							        		"PREPAID_PAYG_REVENUE b " + 
							        		"WHERE b.REVENUE_DATE >=  :param1 AND b.REVENUE_DATE <= :param2  and b.region_name LIKE '%"+region+"%' " + 
							        		"GROUP BY b.REGION_NAME, b.REGION_ID " + 
							        		"order by b.REGION_NAME asc) t group by t.REGION_NAME, t.region_id, t.Total_revenue";
									break;
								    }

								}

				
							}
							// if there is a technology
						/*	else if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g5g")){
						    	
						    	if(StringUtils.equalsIgnoreCase(technologies,"2g")){
						    		 System.out.println("****The techology is 2g  ");
						    		 paramTechnologies = " a.tech_2g='1'and a.tech_3g='0' and a.tech_4g='0' ";
						    	}
						    	if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
						    		 System.out.println("****The techology is 2g3g  ");
						    		 paramTechnologies = " a.tech_2g='1'and a.tech_3g='1' and a.tech_4g='0' ";						    	}
						    	if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
					                 System.out.println("****The techology is 2g3g4g  ");
					                 paramTechnologies = " a.tech_2g='1'and a.tech_3g='1' and a.tech_4g='1' ";
					            }     
					                   // IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
										    switch (filterID) {
										    case "msChrtApply":
					                	   query="select t.site_name,t.area_id,sum(t.voice_revenue), sum(t.sms_revenue),sum(t.data_revenue), sum(t.vas_revenue),sum(t.rev_sum) from ( " + 
							                	 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
								                 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
								                 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
								                 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
								                 ") t where row_nb = 1 " + queryRange + " group by t.site_name,t.area_id"
					                			 + "UNION ("+
					                			 "select t.site_name,t.area_id,sum(t.voice_revenue), sum(t.sms_revenue),sum(t.data_revenue), sum(t.vas_revenue),sum(t.rev_sum) from ( " + 
						                		 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
							                	 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
							                	 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
							                	 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
							                	 ") t where row_nb = 1 " + queryRange + " group by t.site_name,t.area_id) order by 1 asc";
					                	   break;
										    case "stackedChrtApply":
										    
					                	   query2="select revenue_date, sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(total_sum) from ("
					                	   		+ "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms,sum(t.data_revenue) as tot_data, sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum from ( " + 
								                	 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
									                 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
									                 ") t where row_nb = 1  " + queryRange + ""
						                			 + "UNION ("+
						                			 "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms,sum(t.data_revenue) as tot_data, sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum from ( " + 
							                		 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
								                	 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
								                	 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
								                	 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
								                	 ") t where row_nb = 1 " + queryRange + ") order by 3 asc)  group by revenue_date order by revenue_date desc";
					                	   break;
										    case "lineChrtApply":
										    	query2="select revenue_date, sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(total_sum) from ("
							                	   		+ "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms,sum(t.data_revenue) as tot_data, sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum from ( " + 
										                	 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
											                 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
											                 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
											                 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
											                 ") t where row_nb = 1  " + queryRange + ""
								                			 + "UNION ("+
								                			 "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms,sum(t.data_revenue) as tot_data, sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum from ( " + 
									                		 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
										                	 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                	 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                	 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
										                	 ") t where row_nb = 1 " + queryRange + ") order by 3 asc)  group by revenue_date order by revenue_date desc";
							                	   break;
										    case "revPieApply":
										    	query3="select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0),coalesce(sum(tot_data),0),coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from (select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms,sum(t.data_revenue) as tot_data, sum(t.vas_revenue) as tot_vas from ( " + 
									                	 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
										                 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
										                 ") t where row_nb = 1  " + queryRange + ""
							                			 + "UNION ("+
							                			 "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms,sum(t.data_revenue) as tot_data, sum(t.vas_revenue) as tot_vas from ( " + 
								                		 "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
									                	 "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                	 "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                	 "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
									                	 ") t where row_nb = 1 " + queryRange + ") order by 3 asc)";
										    	break;
					                	   
										    }

					                   }
					                   // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
										    switch (filterID) {
										    case "msChrtApply":
					                	   query= "select t.site_name,t.area_id,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum  from ( " + 
							                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
								                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
								                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
								                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
								                  ") t where row_nb = 1 " + queryRange + " group by t.site_name, t.area_id order by t.site_name asc";
					                	   break;
										    case "stackedChrtApply":
										    	query2= "select t.revenue_date , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum  from ( " + 
									                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
										                  ") t where row_nb = 1 " + queryRange + " group by t.revenue_date order by t.revenue_date desc";
										    	break;
					                	   
					                	   
										    case "lineChrtApply":
										    	query2= "select t.revenue_date , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum  from ( " + 
									                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
										                  ") t where row_nb = 1 " + queryRange + " group by t.revenue_date order by t.revenue_date desc";
										    	break;
										    case "revPieApply":
										    	query3= "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) "
							                	   		+ "from (select t.revenue_date , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas  from ( " + 
										                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
											                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
											                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
											                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum desc)" + 
											                  ") t where row_nb = 1 " + queryRange + " group by t.revenue_date order by t.revenue_date asc)";
										    	break;
					                	   
										    }
					                   }
					                   // IF  MIN IS ONLY CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
										    switch (filterID) {
										    case "msChrtApply":
										    	query= "select t.site_name,t.area_id,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum  from ( " + 
									                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
										                  ") t where row_nb = 1 " + queryRange + " group by t.site_name, t.area_id order by t.site_name asc";
										    	break;
					                	   
										    case "stackedChrtApply":
										    	query2= "select t.revenue_date , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum  from ( " + 
									                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
										                  ") t where row_nb = 1 " + queryRange + " group by t.revenue_date order by t.revenue_date desc";
										    	break;
										    case "lineChrtApply":
										    	query2= "select t.revenue_date , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,sum(t.rev_sum) as total_sum  from ( " + 
									                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(PARTITION by REVENUE_DATE order by REVENUE_DATE) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
										                  ") t where row_nb = 1 " + queryRange + " group by t.revenue_date order by t.revenue_date desc";
										    	break;
										    case "revPieApply":
										    	query3= "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) "
							                	   		+ "from (select t.revenue_date , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas  from ( " + 
										                	  "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
											                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
											                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
											                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY') order by rev_sum asc)" + 
											                  ") t where row_nb = 1 " + queryRange + " group by t.revenue_date order by t.revenue_date asc)";
										    	break;
										    } 
					                   }
					                   // IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
										    switch (filterID) {
										    case "msChrtApply":
										    	query = "select t.site_name,t.area_id ,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
			                                       		"b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, " + 
			                                       		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
			                                       		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
			                                       		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and " + paramTechnologies +" " + queryRange + "" + 
			                                       		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')" + 
			                                       		")  t group by t.site_name, t.area_id order by t.site_name asc";
										    	break;
							                  
										    case "stackedChrtApply":

							                  query2 = "select t.revenue_date ,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
			                                       		"b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, " + 
			                                       		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
			                                       		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
			                                       		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and " + paramTechnologies +" " + queryRange + "" + 
			                                       		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')" + 
			                                       		") t group by t.revenue_date order by t.revenue_date desc";
							                  break;
										    case "lineChrtApply":

								                  query2 = "select t.revenue_date ,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
				                                       		"b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, " + 
				                                       		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
				                                       		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
				                                       		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and " + paramTechnologies +" " + queryRange + "" + 
				                                       		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')" + 
				                                       		") t group by t.revenue_date order by t.revenue_date desc";
								                  break;
										    case "revPieApply":
										    	query3 = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0)"
								                  		+ "from (select t.revenue_date ,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas  from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
				                                       		"b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, " + 
				                                       		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
				                                       		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
				                                       		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and " + paramTechnologies +" " + queryRange + "" + 
				                                       		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')" + 
				                                       		") t group by t.revenue_date order by t.revenue_date asc)";
										    	break;

							                  
					                   }
							           }
					                  
				    	    }
							*/
							
							
						    switch (filterID) {
						    case "msChrtApply":
						    	msQry = session.createSQLQuery(regChrtQry);
						    	msQry.setParameter("param1", StartDate);
						    	msQry.setParameter("param2", EndDate);
								ChartRevenueReport = msQry.list();
								if (ChartRevenueReport.size()>0) {
									chartJSNArr = chartRevenues(ChartRevenueReport);

									rtn.put("msColumnChartObj",  mapper.writeValueAsString(chartJSNArr));
									}
								else {
									rtn.put("msColumnChartObj",  mapper.writeValueAsString(""));
								}
								break;
						    case "stackedChrtApply":
						    	periodicQry = session.createSQLQuery(regperiodicQry);
								periodicQry.setParameter("param1", StartDate);
								periodicQry.setParameter("param2", EndDate);
								perDataChrtLst = periodicQry.list();
								if (perDataChrtLst.size()>0) {

								periodicData = getStackedChartData(perDataChrtLst);
								rtn.put("StackedandLineRevenue",  mapper.writeValueAsString(periodicData));

								}
								else {
									rtn.put("StackedandLineRevenue",  mapper.writeValueAsString(""));

								}
								break;
						    case "lineChrtApply":
						    	periodicQry = session.createSQLQuery(regperiodicQry);
						    	periodicQry.setParameter("param1", StartDate);
						    	periodicQry.setParameter("param2", EndDate);
								perDataChrtLst = periodicQry.list();
								rtn.put("StackedandLineRevenue",  mapper.writeValueAsString(periodicData));
								if (perDataChrtLst.size()>0) {

									periodicData = getStackedChartData(perDataChrtLst);
									rtn.put("StackedandLineRevenue",  mapper.writeValueAsString(periodicData));

								}
								else {
									rtn.put("StackedandLineRevenue",  mapper.writeValueAsString(""));

							    }
								break;
						    case "revPieApply":
						    	pieRevQryStrng = session.createSQLQuery(pieRevQry);
								pieRevQryStrng.setParameter("param1", StartDate);
								pieRevQryStrng.setParameter("param2", EndDate);
								pieRev = pieRevQryStrng.list();

								if (pieRev.size()>0) {
									rtn.put("pieRevenue",  mapper.writeValueAsString(pieRev));

								} else {
									rtn.put("pieRevenue",  mapper.writeValueAsString(""));

								}
						    	break;
						    case "regPieApply":

						    	pieRegQryString = session.createSQLQuery(pieRegQry);
								pieRegQryString.setParameter("param1", StartDate);
								pieRegQryString.setParameter("param2", EndDate);
								regionResult = pieRegQryString.list();
								//System.out.println("The regionResult is:  " + mapper.writeValueAsString(regionResult));
								//chartArray = new ArrayList<Object>(PieRevPerTech(regionResult));
								//System.out.println("#########The chartArray is:  " + chartArray);
								if(regionResult.size()>0) {
									rtn.put("listRegions", mapper.writeValueAsString(regionResult));
									//rtn.put("chartData", chartArray);
								}else {
									rtn.put("listRegions", mapper.writeValueAsString(""));
									//rtn.put("chartData", "");
								}
								break;
								
						}
								
						//} // DAILY ENDED
						/*if (StringUtils.equalsIgnoreCase(period, "Weekly")) {

							System.out.println("site is selectedd");
							
							
							System.out.println("the start date is " + StartDate);
							double diff = (EndDate.getTime() - StartDate.getTime()) / 1000.0;
							diff /= (60 * 60 * 24 * 7);
							System.out.println("the week difference is before " + diff);
							diff = Math.abs(Math.ceil(diff));
							System.out.println("the week difference is after " + diff);

							Date weeklyDate;
							Timestamp weeklyEndDate = StartDate;
							Calendar cal = Calendar.getInstance();
							
							
							if (StringUtils.equalsIgnoreCase(technologies, "null") || technologies == null
									|| technologies == "") {

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, "Min")) {
									query = "select site_name,AREA_ID,sum(voice_rev),sum(sms_rev),sum(data_rev),sum(vas_rev) from("
											+ "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "" + "UNION ("
											+ "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + ")) group by site_name,AREA_ID order by site_name asc";

									query2 = "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas) from ("
											+ "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "" + "UNION ("
											+ "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + "))";

									query3 = "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from ( select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + "" + "UNION ("
											+ "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + "))";
								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, null)) {
									query = "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query2 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0) from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query3 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum desc)"
											+ " ) where row_nb = 1 " + queryRange + " ";
								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, "Min")) {
									query = "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query2 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0) from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + " ";

									query3 = "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
											+ "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' group by site_name,area_id order by rev_sum asc)"
											+ " ) where row_nb = 1 " + queryRange + " ";
								}
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
									query = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' " + queryRange + " group by site_name, area_id";
									query2 = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' " + queryRange + "";
									query3 = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
											+ site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' " + queryRange + "";

								}

							}
							// if there is a technology
							else if (StringUtils.equalsIgnoreCase(technologies, "2g") || StringUtils.equalsIgnoreCase(technologies, "2g3g")	|| StringUtils.equalsIgnoreCase(technologies, "2g3g4g") || StringUtils.equalsIgnoreCase(technologies, "2g3g4g5g")) {
								
								if(StringUtils.equalsIgnoreCase(technologies,"2g")){
						    		 System.out.println("****The techology is 2g  ");
						    		 paramTechnologies = " a.tech_2g='1'and a.tech_3g='0' and a.tech_4g='0' ";
						    	}
						    	if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
						    		 System.out.println("****The techology is 2g3g  ");
						    		 paramTechnologies = " a.tech_2g='1'and a.tech_3g='1' and a.tech_4g='0' ";
						    		 }
						    	if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
					                 System.out.println("****The techology is 2g3g4g  ");
					                 paramTechnologies = " a.tech_2g='1'and a.tech_3g='1' and a.tech_4g='1' ";
					            }     
					                   // IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
					                	   query= "select site_name,area_id,coalesce(sum(voice_revenue),0),coalesce(sum(sms_revenue),0),coalesce(sum(data_revenue),0),coalesce(sum(vas_revenue),0) from ("+ 
					                	   		"select t.site_name,t.area_id, t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( " + 
							                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
								                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
								                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
								                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)" + 
								                  ") t where row_nb = 1 "
						                		  + " UNION ("+
						                	      "select t.site_name,t.area_id, t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( " + 
							                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
								                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
								                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
								                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)" + 
								                  ") t  where row_nb = 1 ) order by 1 asc) group by site_name,AREA_ID order by site_name";
					                	   
					                	   query2= "  select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0),coalesce(sum(tot_data),0),coalesce(sum(tot_vas),0) from ("
					                	   			+ "	select  t.voice_revenue as tot_voice, t.sms_revenue as tot_sms,t.data_revenue as tot_data, t.vas_revenue as tot_vas from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)" + 
									                  ") t where row_nb = 1 "
							                		  + " UNION ("+
							                	      "select  t.voice_revenue as tot_voice, t.sms_revenue as tot_sms,t.data_revenue as tot_data, t.vas_revenue as tot_vas from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)" + 
									                  ") t  where row_nb = 1 ))";
					                	   
					                	   query3= "  select coalesce(sum(voice_revenue),0) as tot_voice,coalesce(sum(sms_revenue),0) as tot_sms,coalesce(sum(data_revenue),0) as tot_data,coalesce(sum(vas_revenue),0) as tot_vas,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_rev from ("
					                	   			+ "	select  t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)" + 
									                  ") t where row_nb = 1 "
							                		  + " UNION ("+
							                	      "select  t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)" + 
									                  ") t  where row_nb = 1 ))";

					                   }
					                   // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {      //'"+StartDate+"','"+weeklyEndDate+"',
					                	   
					                	   query= "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0) from ( " + 
							                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
								                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
								                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
								                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)" + 
								                  ") t where row_nb = 1 group by t.site_name,t.area_id order by  t.site_name asc";
					                	   
					                	   query2= "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0) from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)" + 
									                  ") t where row_nb = 1 order by t.site_name asc";
					                	   
					                	   query3= "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)" + 
									                  ") t where row_nb = 1 order by t.site_name asc";
					                	   
					                   }
					                   // IF  MIN IS ONLY CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
					                	   query= "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0) from ( " + 
								                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
									                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
									                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
									                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)" + 
									                  ") t where row_nb = 1 group by t.site_name,t.area_id order by  t.site_name asc";
						                	   
						                	   query2= "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0) from ( " + 
									                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)" + 
										                  ") t where row_nb = 1 order by t.site_name asc";
						                	   
						                	   query3= "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) from ( " + 
									                	  "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from " + 
										                  "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue " + 
										                  "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+
										                  "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)" + 
										                  ") t where row_nb = 1 order by t.site_name asc";
					                   }
					                   // IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
					                   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
					                	   query = "select t.site_name,t.area_id,coalesce(sum(t.voice_revenue),0),coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0),coalesce(sum(t.vas_revenue),0) from ("
					                	   		+ " SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
					                         		"b.site_name as site_name,b.area_id as AREA_ID , " + 
					                         		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
					                         		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
					                         		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+ 
					                         		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id) t group by t.site_name,t.area_id order by t.site_name asc";
					                	   
					                	   query2 = "select coalesce(sum(t.voice_revenue),0),coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0),coalesce(sum(t.vas_revenue),0) from ("
					                	   		+ " SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
					                         		"b.site_name as site_name,b.area_id as AREA_ID , " + 
					                         		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
					                         		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
					                         		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+ 
					                         		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id) t order by t.site_name asc";
					                         
					                	   query3 = "select coalesce(sum(t.voice_revenue),0),coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0),coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) from ("
						                	   		+ " SELECT DISTINCT a.WARE_NAME,a.SITE_ID," + 
						                         		"b.site_name as site_name,b.area_id as AREA_ID , " + 
						                         		"sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  " + 
						                         		"FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 " + 
						                         		"and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+ paramTechnologies+ " "+ 
						                         		"GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id) t order by t.site_name asc";
							           }
								
								}
							
							
							
								q = session.createSQLQuery(query);
								q.setParameter("param1", StartDate);
								q.setParameter("param2", EndDate);

								ChartRevenueReport = q.list();
								chartJSNArr = chartRevenues(ChartRevenueReport);

							
								
								pieRevQryStrng = session.createSQLQuery(query3);
								pieRevQryStrng.setParameter("param1", StartDate);
								pieRevQryStrng.setParameter("param2", EndDate);
								pieRev = pieRevQryStrng.list();

								if (ChartRevenueReport != null) {
									rtn.put("msColumnChartObj", chartJSNArr);
								} else {
									rtn.put("msColumnChartObj", "");

								}
								if (pieRev != null) {
									rtn.put("pieRevenue", mapper.writeValueAsString(pieRev));

								} else {
									rtn.put("pieRevenue", "");

								}

								

								for (i = 0; i < diff; i++) {
									
									weeklyEndDate = StartDate;
									cal.setTime(weeklyEndDate);
									cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									System.out.println(weeklyDate);
									// weeklyEndDate = formatter.parse(weeklyEndDate);
									weeklyEndDate = new Timestamp(weeklyDate.getTime());
									System.out.println("for week " + i + ":" + weeklyEndDate);
									q2 = session.createSQLQuery(query2);
									q2.setParameter("param1", StartDate);
									
									if ((weeklyEndDate).compareTo(EndDate) > 0) {
										System.out.println("the weeklyEndDate is greater than enddate");
										q2.setParameter("param2", EndDate);
										revenueChartsList = q2.list();
										preparePeriodicDataJsn.add(preparePeriodicData(String.valueOf(StartDate),String.valueOf(EndDate), revenueChartsList));

									} else {
										System.out.println("the weeklyEndDate is less than enddate");
										q2.setParameter("param2", weeklyEndDate);
										revenueChartsList = q2.list();
										preparePeriodicDataJsn.add(preparePeriodicData(String.valueOf(StartDate),String.valueOf(weeklyEndDate), revenueChartsList));

									}

									
									StartDate = weeklyEndDate;
									cal.setTime(StartDate);
									cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									StartDate = new Timestamp(weeklyDate.getTime());

									System.out.println("after start date " + StartDate);

								}
								
								
								finalRevenueReportResults.add(getPeriodicChartData(preparePeriodicDataJsn));
								rtn.put("StackedandLineRevenue", finalRevenueReportResults);
						
						}*/
						
					
				}

			
			} catch (Exception e) {
				logger.info("Error in creating session with Database", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}

			return rtn;
		}
	//Generate Charts 
	 @SuppressWarnings("unchecked")
		@RequestMapping(value = "/GetRegionRevCharts", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> GetRegionRevCharts(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException, ParseException {
			logger.info("Welcome home! The client locale is {}.", locale);

			
			
			String site = request.getParameter("siteName");
			System.out.println("the site is "+site);
			String area = request.getParameter("area");
			String technologyRegions = request.getParameter("technologyRegions");
			String Payment_Method = request.getParameter("Payment Method");

			String technologies = request.getParameter("technologies");
			String period = request.getParameter("period");
			String max = request.getParameter("Max");
			String min = request.getParameter("Min");

			Map<String, Object> rtn = new LinkedHashMap<>();
			ObjectMapper mapper = new ObjectMapper();

			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

			String start_Date = request.getParameter("startDate");
			Date startDate = formatter.parse(start_Date);
			Timestamp StartDate = new Timestamp(startDate.getTime());
			String end_Date = request.getParameter("endDate");
			Date endDate = formatter.parse(end_Date);
			Timestamp EndDate = new Timestamp(endDate.getTime());

			String query4 = "",paramTechnologies = "";
			Query q4 = null;
			ArrayList<Object> chartArray;
			 List<Object[]> siteResult = new ArrayList<Object[]>();
			
			Session session =null;
			Transaction tx = null;
			try {
				    session = almsessions.getSessionRPT();
				    tx = session.beginTransaction();
				// no technology is selected
				if (StringUtils.equalsIgnoreCase(technologies, "null") || technologies == null || technologies == "") {
				     // IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
	                if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	query4 = "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
	                              +"UNION "+
	                              "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1";
	                              
	                }
	                // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
	                if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
	                	//query4 = "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,date_rev,ROW_NUMBER() over(PARTITION by date_rev order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc))where row_nb = 1 group by technology";
	                    query4 = "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1";

	                }
	                // IF MIN IS ONLY CHECKED WITH TECHNOLOGY
	                if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	//query4 = "select tech as technology, sum(sites) as sum_total,sum(Total_REVENUE) as totalRevSum from (select tech , sites ,Total_REVENUE ,ROW_NUMBER() over(order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech,b.REVENUE_DATE,b.SITE_NAME,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G') group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc)) where row_nb IN (1,2) group by tech";
	                	//query4 = "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from( select tech as technology, sites as sum_total,MIN(Total_REVENUE) as totalRevSum,date_rev from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev,b.SITE_NAME,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology  In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc) group by tech, sites, date_rev) group by technology";

	                	//query4 = "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,date_rev,ROW_NUMBER() over(PARTITION by date_rev order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc))where row_nb = 1 group by technology";
	                      query4 = "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1";
	                }
	                // NO MAX AND MIN ONLY CHECKED WITH TECHNOLOGY
	                if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
	    				query4 = "select a.combination_technology as tech,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology order by a.combination_technology asc";
	                }

				}
				// if there is a technology
				else if(StringUtils.equalsIgnoreCase(technologies,"2g") || StringUtils.equalsIgnoreCase(technologies,"2g3g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g") || StringUtils.equalsIgnoreCase(technologies,"2g3g4g5g")){
			    	
			    	if(StringUtils.equalsIgnoreCase(technologies,"2g")){
			    		 System.out.println("****The techology is 2g  ");
			    		 paramTechnologies = " In ('2G') ";
			    	}
			    	if(StringUtils.equalsIgnoreCase(technologies,"2g3g")){
			    		 System.out.println("****The techology is 2g3g  ");
			    		 paramTechnologies = "In ('2G_3G') ";						  
			    	}
			    	if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")){
		                 System.out.println("****The techology is 2g3g4g  ");
		                 paramTechnologies = "In ('2G_3G_4G') ";
		            }
			    	 // IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
	                if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	query4 = "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology " + paramTechnologies+" group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
	                          	+" UNION "+
	                			"select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology " + paramTechnologies+" group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1";
	                    
	                }
	                // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
	                if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
	                	//query4 = "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from( select tech as technology, sites as sum_total,Max(Total_REVENUE) as totalRevSum,date_rev from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev,b.SITE_NAME,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology " + paramTechnologies+" group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc) group by tech, sites, date_rev) group by technology";
	                	query4 = "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology " + paramTechnologies+" group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1";
	                }
	                // IF MIN IS ONLY CHECKED WITH TECHNOLOGY
	                if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
	                	//query4 = "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from( select tech as technology, sites as sum_total,Min(Total_REVENUE) as totalRevSum,date_rev from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev,b.SITE_NAME,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology " + paramTechnologies+" group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc) group by tech, sites, date_rev) group by technology";
	                	query4 = "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology " + paramTechnologies+" group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1";
	                }
	                // NO MAX AND MIN ONLY CHECKED WITH TECHNOLOGY
	                if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
	    				query4 = "select a.combination_technology as tech,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"+ site + "%'  and a.combination_technology " + paramTechnologies+" group by a.combination_technology order by a.combination_technology asc";
	                }

				}
				
				q4 = session.createSQLQuery(query4);
				q4.setParameter("param1", StartDate);
				q4.setParameter("param2", EndDate);
				siteResult = q4.list();
				System.out.println("The siteResult is:  " + mapper.writeValueAsString(siteResult));
				//chartArray = new ArrayList<Object>(PieRevPerTech(siteResult));
				//System.out.println("#########The chartArray is:  " + chartArray);
				if(siteResult.size()>0) {
					rtn.put("listChartSites",  mapper.writeValueAsString(siteResult));
					//rtn.put("chartData",  chartArray);
				}else {
					rtn.put("listChartSites",  mapper.writeValueAsString(""));
					//rtn.put("chartData", "");
				}
				
			}

			catch (Exception e) {
				logger.info("Error at <T>  creating session with the DataBase " + e.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
			return rtn;

		}
	 
	 

		//for Generate map
		@SuppressWarnings("unchecked")
		@RequestMapping(value ="/GetRevenuePerRegion", method = RequestMethod.GET)
		@ResponseBody public Map<String, Object> GetRevenuePerRegion(Locale locale, Model model,
		   HttpServletRequest request, HttpServletResponse response) throws
		   JsonProcessingException, ParseException { 
		   logger.info("Welcome home! The client locale is {}.", locale);

		   String region = request.getParameter("region");

		   String area = request.getParameter("area");
		   String  technologies = request.getParameter("technologies");
		   String period = request.getParameter("period");
		   
		   Map<String, Object> rtn = new LinkedHashMap<>(); 
		   ObjectMapper mapper = new ObjectMapper();
		   
		   SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			
			
			String start_Date = request.getParameter("startDate");
			Date startDate = formatter.parse(start_Date);
			Timestamp StartDate = new Timestamp(startDate.getTime());
			String end_Date = request.getParameter("endDate");
			Date endDate = formatter.parse(end_Date);
			Timestamp EndDate = new Timestamp(endDate.getTime());
		   
		   String regMapJsnObj ="";
		   Query regMapJsnObjQry = null, regData = null;
		
		   Session session = null;
		   Transaction tx = null ;
		   
		   List<Object[]> regionList = new ArrayList<Object[]>();
		   String RegionsQuery;
		   List generatedRegionsList;

		   try {
			    session = almsessions.getSessionRPT();
			    tx = session.beginTransaction();
			    
		   if (region != "") {

				  
			 
			   regMapJsnObj = "SELECT distinct t.id,t.regName,t.regID,Lat,Lng from (select b.id as id,a.region_name as regName,b.region_id as regID,json_object('Region' VALUE a.region_name ,'Lat' VALUE b.latitude) as Lat,json_object('Region' VALUE a.region_name,'Lng' VALUE b.longtitude) as Lng " + 
		    			"from alm_regionborder b " + 
		    			"INNER JOIN alm_region a ON b.region_id = a.region_id " + 
		    			"group by b.id,a.region_name,b.region_id,b.latitude,b.longtitude " + 
		    			"order by b.id asc) t " +  
		    			"WHERE  t.regName LIKE '%"+region+"%' " +  
		    			"group by t.id, t.regName, t.regID, t.Lat,t.Lng " + 
		    			"order by t.id asc";
			   
			   regMapJsnObjQry=session.createSQLQuery(regMapJsnObj);
			   
				//q.setParameter("param1", StartDate);
			    //q.setParameter("param2", EndDate);	
			    regionList = regMapJsnObjQry.list();
			   // System.out.println("defaultRegionsList Yaraaa are "+mapper.writeValueAsString(regionList));
			    if (regionList != null  ) {
			    	rtn.put("LatLngReg",regionList);
				   }
			    else {
			    	rtn.put("LatLngReg","");

			    }
			    
			       RegionsQuery = "SELECT t.REGION_NAME,t.region_id,t.Total_revenue, (select SUM(sms_revenue+data_revenue+voice_revenue+vas_revenue) " + 
			       			"from prepaid_payg_revenue where REVENUE_DATE >=  :param1 AND REVENUE_DATE <= :param2 AND region_name LIKE '%"+region+"%') as allTotal , (CASE WHEN t.Total_REVENUE < 500 THEN '0-500$' WHEN t.Total_REVENUE BETWEEN 500 AND 2000 THEN '500-2000$' ELSE 'MoreThn2000$' END) as LVL " +
			        		"FROM  " + 
			        		"(SELECT b.REGION_NAME,b.REGION_ID,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE FROM " + 
			        		"PREPAID_PAYG_REVENUE b " + 
			        		"WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2  AND b.region_name LIKE '%"+region+"%' " + 
			        		"GROUP BY b.REGION_NAME, b.REGION_ID " + 
			        		"order by b.REGION_NAME asc) t group by t.REGION_NAME, t.region_id, t.Total_revenue";
			        
			       regData = session.createSQLQuery(RegionsQuery);
			       regData.setParameter("param1", StartDate);
			       regData.setParameter("param2", EndDate);
			         generatedRegionsList = regData.list();
			        
			        
			        System.out.println("defaultRegionsList are "+mapper.writeValueAsString(generatedRegionsList));
			        
			        if (generatedRegionsList != null  ) {
			    		
				        rtn.put("listRegions",mapper.writeValueAsString(generatedRegionsList));

						   
					   }
			    	else {
						rtn.put("listRegions", mapper.writeValueAsString(""));
					}
				  }

		   else if (region == "" ) {
			   
			   if(StringUtils.equalsIgnoreCase(period,"Daily") ||StringUtils.equalsIgnoreCase(period,"null") ||StringUtils.equalsIgnoreCase(period,"Weekly") ||StringUtils.equalsIgnoreCase(period,"Monthly") ||StringUtils.equalsIgnoreCase(period,"Accu")) {
				   if(StringUtils.equalsIgnoreCase(technologies,"null") || technologies == null || technologies == "") {
					
					   //query =	"SELECT DISTINCT a.WARE_NAME,a.LONGITUDE,a.LATITUDE,a.TECH_2G,a.TECH_3G,a.TECH_4G,a.TECH_5G,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE,a.SITE_ID FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.area_id like '%"+area+"%' GROUP BY a.WARE_NAME,a.LONGITUDE, a.LATITUDE, a.TECH_2G,a.TECH_3G, a.TECH_4G, a.TECH_5G, a.SITE_ID";  
					   regMapJsnObj = "SELECT distinct t.id,t.regName,t.regID,Lat,Lng from (select b.id as id,a.region_name as regName,b.region_id as regID,json_object('Region' VALUE a.region_name ,'Lat' VALUE b.latitude) as Lat,json_object('Region' VALUE a.region_name,'Lng' VALUE b.longtitude) as Lng " + 
				    			"from alm_regionborder b " + 
				    			"INNER JOIN alm_region a ON b.region_id = a.region_id " + 
				    			"group by b.id,a.region_name,b.region_id,b.latitude,b.longtitude " + 
				    			"order by b.id asc) t " + 
				    			"INNER JOIN PREPAID_PAYG_REVENUE c ON t.regID = c.region_id  " + 
				    			"WHERE c.REVENUE_DATE >=  :param1 AND c.REVENUE_DATE <= :param2 " + 
				    			"group by t.id, t.regName, t.regID, t.Lat,t.Lng " + 
				    			"order by t.id asc";
					   
					   regMapJsnObjQry=session.createSQLQuery(regMapJsnObj);
					   regMapJsnObjQry.setParameter("param1", StartDate);
					   regMapJsnObjQry.setParameter("param2", EndDate);	
					    regionList = regMapJsnObjQry.list();
					    
					    if (regionList != null  ) {
					    	rtn.put("LatLngReg",regionList);
						   }
					    else {
					    	rtn.put("LatLngReg","");

					    }
					    
					       RegionsQuery = "SELECT t.REGION_NAME,t.region_id,t.Total_revenue,  (select SUM(sms_revenue+data_revenue+voice_revenue+vas_revenue) " + 
					       		 	"from prepaid_payg_revenue where REVENUE_DATE >=  :param1 AND REVENUE_DATE <= :param2) as allTotal , (CASE WHEN t.Total_REVENUE < 500 THEN '0-500$' WHEN t.Total_REVENUE BETWEEN 500 AND 2000 THEN '500-2000$' ELSE 'MoreThn2000$' END) as LVL " + 
					       		 	"FROM " + 
					        		"(SELECT b.REGION_NAME,b.REGION_ID,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE FROM " + 
					        		"PREPAID_PAYG_REVENUE b " + 
					        		"WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2   " + 
					        		"GROUP BY b.REGION_NAME, b.REGION_ID " + 
					        		"order by b.REGION_NAME asc) t";
					        
					       regData = session.createSQLQuery(RegionsQuery);
					       regData.setParameter("param1", StartDate);
					       regData.setParameter("param2", EndDate);
					         generatedRegionsList = regData.list();
					        
					        
					        System.out.println("defaultRegionsList are "+mapper.writeValueAsString(generatedRegionsList));
					        
					        if (generatedRegionsList != null  ) {
					    		
						        rtn.put("listRegions",mapper.writeValueAsString(generatedRegionsList));

								   
							   }
					    	else {
								rtn.put("listRegions", mapper.writeValueAsString(""));
							}
				   }							
				 /*  if(StringUtils.equalsIgnoreCase(technologies,"2g")) {
					   
					   query =	"SELECT DISTINCT a.WARE_NAME,a.LONGITUDE,a.LATITUDE,a.TECH_2G,a.TECH_3G,a.TECH_4G,a.TECH_5G,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE,a.SITE_ID FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and a.tech_2g ='1' and a.tech_3g='0' and a.tech_4g='0' and a.tech_5g='0'  and b.area_id like '%"+area+"%' GROUP BY a.WARE_NAME,a.LONGITUDE, a.LATITUDE, a.TECH_2G,a.TECH_3G, a.TECH_4G, a.TECH_5G, a.SITE_ID";  

				
				   }
				   if(StringUtils.equalsIgnoreCase(technologies,"2g3g")) {
					   query =	"SELECT DISTINCT a.WARE_NAME,a.LONGITUDE,a.LATITUDE,a.TECH_2G,a.TECH_3G,a.TECH_4G,a.TECH_5G,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE,a.SITE_ID FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and a.tech_2g ='1' and a.tech_3g='1' and a.tech_4g='0' and a.tech_5g='0'  and b.area_id like '%"+area+"%' GROUP BY a.WARE_NAME,a.LONGITUDE, a.LATITUDE, a.TECH_2G,a.TECH_3G, a.TECH_4G, a.TECH_5G, a.SITE_ID";  

				   }
				   
				   if(StringUtils.equalsIgnoreCase(technologies,"2g3g4g")) {
					   query =	"SELECT DISTINCT a.WARE_NAME,a.LONGITUDE,a.LATITUDE,a.TECH_2G,a.TECH_3G,a.TECH_4G,a.TECH_5G,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE,a.SITE_ID FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and a.tech_2g ='1' and a.tech_3g='1' and a.tech_4g='1' and a.tech_5g='0'  and b.area_id like '%"+area+"%' GROUP BY a.WARE_NAME,a.LONGITUDE, a.LATITUDE, a.TECH_2G,a.TECH_3G, a.TECH_4G, a.TECH_5G, a.SITE_ID";  
	
				   }
				   */
				   
				   		
								    
								    
			  
			   } // end daily condition
		   }
		   
		   }
		   
		   
		   catch (Exception e) {
			   logger.info("Error at <T>  creating session with the DataBase " +e.getMessage() );


			   } finally {
			   if (session != null && session.isOpen()) {
			   tx.commit();
			   session.close();
			   }
			   }
		   return rtn;
		   
		}

		
		//Revenue Report auto complete for sites
		
		// Needs try and catch
		
		@SuppressWarnings("unchecked")
		@RequestMapping(value ="/GetAllRegionAutocomplete", method = RequestMethod.GET)
		@ResponseBody public Map<String, Object> GetAllRegionAutocomplete(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) throws
			JsonProcessingException { 
			logger.info("Welcome home! The client locale is {}.", locale);
			
			Map<String, Object> rtn = new LinkedHashMap<>(); 
		    ObjectMapper mapper = new ObjectMapper();
			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			//String siteId = "%" + request.getParameter("SiteId") + "%";
			String region = "%" + request.getParameter("region") + "%";
			
			Query regAutoCmplt = session.createSQLQuery("SELECT  distinct (region_name),region_id,region_code FROM region where UPPER(region_name) like UPPER(:param1) or UPPER(REGION_CODE) like UPPER(:param1) OR UPPER(region_id) like UPPER(:param1)");
			
			regAutoCmplt.setParameter("param1", region);
			//q.setParameter("param2", Area);
			regAutoCmplt.setFirstResult(0);
			regAutoCmplt.setMaxResults(40);
			
			 
			List<String> listRegion = new ArrayList<String>();
			
			
			
			listRegion = regAutoCmplt.list();
			
			
			 tx.commit();
		      session.close(); 
				  rtn.put("listRegions", listRegion); 
				
			
			
			
			
				  
				  
				  return rtn; 
				 
				 }
		
		
		

		@SuppressWarnings("unchecked")
		@RequestMapping(value ="/GetAllRegionsAutocomplete", method = RequestMethod.GET)
		@ResponseBody public Map<String, Object> GetAllRegionsAutoComplete(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) throws
			JsonProcessingException { 
			logger.info("Welcome home! The client locale is {}.", locale);
			//System.out.println("Here");
			Map<String, Object> rtn = new LinkedHashMap<>(); 
		    ObjectMapper mapper = new ObjectMapper(); 
			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			String region= "%" + request.getParameter("Region") + "%";	  
			Query regAutoCmplt = session.createSQLQuery("SELECT  distinct REGION_NAME,REGION_ID,REGION_CODE FROM REGION where UPPER(REGION_NAME)like UPPER(:param1) OR UPPER(REGION_CODE)like UPPER(:param1) OR UPPER(REGION_ID)like UPPER(:param1)");
			regAutoCmplt.setParameter("param1", region);
			List<String> listregions = new ArrayList<String>();
			regAutoCmplt.setFirstResult(0);
			regAutoCmplt.setMaxResults(40);
			 
			listregions = regAutoCmplt.list();
			
			 tx.commit();
		      session.close(); 
				  rtn.put("listRegions", listregions); 
				   
				
				  return rtn; 
				 
		}

		
		@SuppressWarnings({ "unchecked", "null" })
		private JSONArray chartRevenues(List<Object> defaultChartRevenueReport) {
			JSONArray regionNames = new JSONArray(),voiceRevenue = new JSONArray(), smsRevenue = new JSONArray(),	dataRevenue = new JSONArray(), vasRevenue = new JSONArray(),totalRevenue = new JSONArray();
			JSONArray objRevenue = new JSONArray();
			JSONObject  json;
			
			if (defaultChartRevenueReport != null && defaultChartRevenueReport.size() != 0) {
				List<Object> c = defaultChartRevenueReport;
				Object[] fof;// what is fof???
				for (int i = 0; i < c.size(); i++) {
					fof = (Object[]) c.get(i);
					
					if (fof[0] != null) {
						json = new JSONObject();
						json.put("label",StringUtils.equalsIgnoreCase((String) fof[0], "null") ? "No region name" :fof[0]);
						regionNames.add(json);
					} else {
						json = new JSONObject();
						json.put("label","No region name");
						
						regionNames.add(json);
					}

					if (fof[2] != null) {
						json = new JSONObject();
						json.put("value",fof[2]);
						voiceRevenue.add(json);

					} else {
						json = new JSONObject();
						json.put("value",0);
						voiceRevenue.add(json);

					}
					if (fof[3] != null) {
						json = new JSONObject();
						json.put("value",fof[3]);
						smsRevenue.add(json);

					} else {
						json = new JSONObject();
						json.put("value",0);
						smsRevenue.add(json);

					}
					if (fof[4] != null) {
						json = new JSONObject();
						json.put("value",fof[4]);
						dataRevenue.add(json);

					} else {
						json = new JSONObject();
						json.put("value",0);
						dataRevenue.add(json);

					}
					if (fof[5] != null) {
						json = new JSONObject();
						json.put("value",fof[5]);
						vasRevenue.add(json);

					} else {
						json = new JSONObject();
						json.put("value",0);
						vasRevenue.add(json);

					}
					if (fof[5] != null) {
						json = new JSONObject();
						json.put("value",fof[6]);
						totalRevenue.add(json);

					} else {
						json = new JSONObject();
						json.put("value",0);
						totalRevenue.add(json);

					}

				}
			} else {
				json = new JSONObject();
				json.put("label","No Data");
				regionNames.add(json);
				json = new JSONObject();
				json.put("value",0);
				voiceRevenue.add(json);
				smsRevenue.add(json);
				dataRevenue.add(json);
				vasRevenue.add(json);
				totalRevenue.add(json);
			}

			objRevenue.add(regionNames);
			objRevenue.add(voiceRevenue);
			objRevenue.add(smsRevenue);
			objRevenue.add(dataRevenue);
			objRevenue.add(vasRevenue);
			objRevenue.add(totalRevenue);

			return objRevenue;
		}
	 
		@SuppressWarnings("unused")
		private static Boolean check(HashSet<?> Duplicates, Object x) {
			// check if the specified element
			// is present in the array or not
			// using Linear Search method
			boolean test = false;
			for (Object element : Duplicates) {
				if (element == x) {
					test = true;
					break;
				}
			}

			// Print the result
			System.out.println("Is " + x + " present in the array: " + test);
			return test;
		}

		@SuppressWarnings("unchecked")
		public static <T extends Comparable<T>> ArrayList<Object> getDuplicates(ArrayList<?> siteArr) {
			HashSet<T> dupes = new HashSet<T>();
			ArrayList<Object> siteDuplicates = new ArrayList<Object>();
			for (Object i : siteArr) {
				if (!dupes.add((T) i)) {
					siteDuplicates.add((T) i);
				}
				/*
				 * if (!dupes.add(i)) { System.out.println("Duplicate element in array is : " +
				 * i); }
				 */
			}

			return siteDuplicates;
		}



		@SuppressWarnings("unchecked")
		private JSONArray splittingFuncSpare(ArrayList<?> Array, String c) {
			// split any ArrayList using this method ///// Created By: HAjouz ________
			// 25.8.2021
			JSONArray FinalArray = new JSONArray();
			String[] elmnt;
			if (c == "-") {

				for (Object Element : Array) {
					elmnt = ((String) Element).split(c);
					FinalArray.add(elmnt[1] + "/" + elmnt[2] + "/" + elmnt[0]);
				}
			} else {
				for (Object Element : Array) {

					elmnt = ((String) Element).split(c);
					FinalArray.add(elmnt[0].toString());
				}
			}

			return FinalArray;
		}

		@SuppressWarnings({ "unchecked" })
		private JSONArray getStackedChartData(List<Object[]> perDataChrtLst) {
			JSONObject json;
			JSONArray voiceArrFnl = new JSONArray(), smsArrFnl = new JSONArray(),dataArrFnl = new JSONArray(), vasArrFnl = new JSONArray(),totalRevArrFnl = new JSONArray();
			JSONArray obj = new JSONArray();
			JSONArray startDateArrOriginal = new JSONArray();
			
			Object[] getObj;

			if (!perDataChrtLst.isEmpty()) {
				for (int i = perDataChrtLst.size() - 1; i >= 0; i--) {
					
					getObj = perDataChrtLst.get(i);
					
					json = new JSONObject();
					json.put("label",getObj[0]);
					startDateArrOriginal.add(json);
					
					json = new JSONObject();
					json.put("value",getObj[1]);
					voiceArrFnl.add(json);
					
					json = new JSONObject();
					json.put("value",getObj[2]);
					smsArrFnl.add(json);
					
					json = new JSONObject();
					json.put("value",getObj[3]);
					dataArrFnl.add(json);
					
					json = new JSONObject();
					json.put("value",getObj[4]);
					vasArrFnl.add(json);
					
					json = new JSONObject();
					json.put("value",getObj[5]);
					totalRevArrFnl.add(json);
					

				}
				startDateArrOriginal = (((ArrayList<?>) startDateArrOriginal).isEmpty()) == true ? null
						: startDateArrOriginal;
			}

			obj.add(startDateArrOriginal);
			obj.add(voiceArrFnl);
			obj.add(smsArrFnl);
			obj.add(dataArrFnl);
			obj.add(vasArrFnl);
			obj.add(totalRevArrFnl);

			return obj;

		}

		@SuppressWarnings("unchecked")
		private JSONArray splittingFunc(ArrayList<?> Array, ArrayList<?> Array2, String c) {
			// split any ArrayList using this method ///// Created By: HAjouz ________
			// 25.8.2021
			ArrayList<String> Arr1 = new ArrayList<>(), Arr2 = new ArrayList<>();

			JSONArray FinalArray = new JSONArray();
			String[] elmnt, elmnt2;
			if (c == "-") {

				for (Object Element : Array) {
					elmnt = ((String) Element).split(c);
					Arr1.add(elmnt[2]+ "/" + elmnt[1]);// + "/" + elmnt[1]
				}
				for (Object Element : Array2) {
					elmnt2 = ((String) Element).split(c);
					Arr2.add(elmnt2[2] + "/" + elmnt2[1]);
				}
				FinalArray.add(Arr1.get(0) + "-" + Arr2.get(0));


			} else {
				for (Object Element : Array) {

					elmnt = ((String) Element).split(c);
					// FinalArray.add(elmnt[0].toString());
				}
				for (Object Element : Array2) {

					elmnt2 = ((String) Element).split(c);
					// FinalArray.add(elmnt2[0].toString());
				}
			}

			return FinalArray;
		}
		@SuppressWarnings({ "unchecked" })
		private JSONArray preparePeriodicData(String startDate, String endDate, List<Object[]> perDataChrtLst) {

			ArrayList<String> voiceArrFnl = new ArrayList<String>(), smsArrFnl = new ArrayList<String>(),
					dataArrFnl = new ArrayList<String>(), vasArrFnl = new ArrayList<String>(),totalRevArrFnl = new ArrayList<String>();
			JSONArray obj = new JSONArray();
			ArrayList<String> startDateArrOriginal = new ArrayList<String>();
			ArrayList<String> endDateArrOriginal = new ArrayList<String>();

			ArrayList<String> DateArrFnl = new ArrayList<String>();

			startDateArrOriginal.add(startDate);
			endDateArrOriginal.add(endDate);
			startDateArrOriginal = splittingFuncSpare(startDateArrOriginal, " ");
			endDateArrOriginal = splittingFuncSpare(endDateArrOriginal, " ");
			DateArrFnl = splittingFunc(startDateArrOriginal, endDateArrOriginal, "-");
			Object[] getObj;

			if (!perDataChrtLst.isEmpty()) {
				for (int i = perDataChrtLst.size() - 1; i >= 0; i--) {

					getObj = perDataChrtLst.get(i);
					// startDateArrOriginal.add((String) getObj[0]);
					voiceArrFnl.add((getObj[0]).toString());
					smsArrFnl.add(getObj[1].toString());
					dataArrFnl.add(getObj[2].toString());
					vasArrFnl.add(getObj[3].toString());
					totalRevArrFnl.add(getObj[4].toString());

				}
				DateArrFnl = (((ArrayList<?>) DateArrFnl).isEmpty()) == true ? null : DateArrFnl;
			}

			obj.add(DateArrFnl.get(0));
			obj.add(voiceArrFnl.get(0).isEmpty() ? 0 : voiceArrFnl);
			obj.add(smsArrFnl.get(0).isEmpty() ? 0 : smsArrFnl);
			obj.add(dataArrFnl.get(0).isEmpty() ? 0 : dataArrFnl);
			obj.add(vasArrFnl.get(0).isEmpty() ? 0 : vasArrFnl);
			obj.add(totalRevArrFnl.get(0).isEmpty() ? 0 : totalRevArrFnl);

			return obj;

		}
		@SuppressWarnings({ "unchecked" })
		private JSONArray getPeriodicChartData(JSONArray data) { //List<Object[]> 

			JSONArray voiceArrFnl = new JSONArray(), smsArrFnl = new JSONArray(),dataArrFnl = new JSONArray(), vasArrFnl = new JSONArray(),totalRevArrFnl = new JSONArray();
			JSONArray obj = new JSONArray(),startDateArrOriginal = new JSONArray(),endDateArrOriginal = new JSONArray(),DateArrFnl = new JSONArray();
			JSONObject json;
			//startDateArrOriginal.add(startDate);
			//endDateArrOriginal.add(endDate);
			
			json = new JSONObject();
			//startDateArrOriginal = splittingFuncSpare(startDateArrOriginal, " ");
			//endDateArrOriginal = splittingFuncSpare(endDateArrOriginal, " ");
			
			//DateArrFnl = splittingFunc(startDateArrOriginal, endDateArrOriginal, "-");
			
			ArrayList<?> getObj;
			if (!data.isEmpty()) {
				for (int i = 0; i<data.size(); i++) {

					getObj = (ArrayList<?>) data.get(i);
					//for (Object coa : getObj) {
					json = new JSONObject();
					
					json.put("label",getObj.get(0));
					//DateArrFnl.remove(0);
					DateArrFnl.add(json);
					
					json = new JSONObject();
					json.put("value",getObj.get(1));
					voiceArrFnl.add(json);
					
					json = new JSONObject();
					json.put("value",getObj.get(2));
					smsArrFnl.add(json);
					
					json = new JSONObject();
					json.put("value",getObj.get(3));
					dataArrFnl.add(json);
					
					json = new JSONObject();
					json.put("value",getObj.get(4));
					vasArrFnl.add(json);
					
					json = new JSONObject();
					json.put("value",getObj.get(5));
					totalRevArrFnl.add(json);
				//}
				}
				DateArrFnl = (((ArrayList<?>) DateArrFnl).isEmpty()) == true ? null : DateArrFnl;
			}

			obj.add(DateArrFnl);
			obj.add(voiceArrFnl.isEmpty() ? 0 : voiceArrFnl);
			obj.add(smsArrFnl.isEmpty() ? 0 : smsArrFnl);
			obj.add(dataArrFnl.isEmpty() ? 0 : dataArrFnl);
			obj.add(vasArrFnl.isEmpty() ? 0 : vasArrFnl);
			obj.add(totalRevArrFnl.isEmpty() ? 0 : totalRevArrFnl);
			
			return obj;

		}

	/*	private List<Object> PieRevPerTech(List<Object[]> regionResult) {
			String regionName = null;
			float regionTotal = 0;

			List<Object> arrlist = new ArrayList<>();
			if (regionResult != null && regionResult.size() != 0) {
				
			
				
				for (Object[] obj : regionResult) {
					regionName = (String) obj[0];
					regionTotal =   ((BigDecimal) obj[2]).floatValue();
					arrlist.add(regionName);
					arrlist.add(regionTotal);
				}
				
				
			} else {

				regionName = "";
				regionTotal = 100;
				arrlist.add(regionName);
				arrlist.add(regionTotal);
			}
			

			return arrlist;
		}
		
	 */
}
