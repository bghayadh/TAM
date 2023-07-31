package com.aliat.alm.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
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
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.SimAgentSales;
import com.aliat.alm.models.SimAgentCountChartsReport;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


	@Controller
	public class SimAgentSalesController {

		 @Autowired
		 ALMSessions almsessions;
		 
		 @Autowired
		 Notify notifications;
		// Audited by Yara Mezher 
		 
		 private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
		 
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/SimAgentSalesReport", method = RequestMethod.GET)
		public String SimSales(Locale locale, Model model,HttpServletRequest request,HttpServletResponse response) {
			//logger.info("Welcome home! The client locale is {}.", locale);
			
			ObjectMapper mapper = new ObjectMapper();
			
			//Defining  Variables 
	        Query agentsCountChartQuery,periodicDataQuery,registrationStatusQuery;
	        
			List<Object> defPeriodicData = new ArrayList<Object>();
			ArrayList<Float> chartArray;
			ArrayList<Float> chartArrayRegistrationStatus;
			JSONArray chartJSNArr;

			if(LoginServices.checkSession(request, response).equals("redirect:/")) {
				 return "redirect:/";
				 }
			
			else {
			     Session sessionALM =null;
		        Transaction txALM=null ;
		        sessionALM = almsessions.getSession();
		        
		        if (sessionALM != null && sessionALM.isOpen()) {
		        	 
		        		  txALM = sessionALM.beginTransaction();
		 		       	  notifications.headerNotifications(sessionALM, model);
		 		         try { 
		 		        
		 		        //Google maps query
			        	 model.addAttribute("agentSalesCountList",mapper.writeValueAsString(sessionALM.createSQLQuery("select count(*),a.agent_id, a.full_name,a.latitude,a.longitude,a.msisdn from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >=  trunc(sysdate) AND b.created_date < (trunc(sysdate) ) + 1 and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success'  GROUP BY a.agent_id,a.full_name,a.latitude,a.longitude,a.msisdn").list()));

				         // Grid Query 				        	
				         @SuppressWarnings("unchecked")
			           		List<SimAgentSales> agentReportResults = (List<SimAgentSales>) ((SQLQuery) sessionALM.createSQLQuery("SELECT agent_id as \"agentId\" ,full_name as \"fullName\" ,msisdn as \"msisdn\",createdDate as \"startDate\",endDate as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\",coalesce(successCount,0) as \"successCount\" ,coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.createdDate as createdDate,A.endDate as endDate,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as totalCount from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC( b.created_date) >= trunc(sysdate) AND TRUNC(b.created_date) < (trunc(sysdate)) + 1 and b.status !='DEACTIVATED'  AND b.status !='CANCELLED' group by to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn )A LEFT JOIN (  SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as secondMSISDN ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC( b.created_date) >= trunc(sysdate) AND TRUNC(b.created_date) < (trunc(sysdate)) + 1 and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn  )B ON A.createdDate = B.createdDate and a.msisdn = b.secondMSISDN  )D LEFT JOIN ( SELECT a.agent_id as agent_id1,a.full_name as full_name1  ,a.msisdn as secondMSISDN1 ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate1 ,to_char(b.created_date, 'YYYY-MM-DD') as endDate1,a.region_name as region_name1 ,count(*) as failedCount from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC( b.created_date) >= trunc(sysdate) AND TRUNC(b.created_date) < (trunc(sysdate)) + 1  and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn )C ON D.createdDate = C.createdDate1 and D.msisdn = C.secondMSISDN1 )" ))
			           		.addScalar("agentId").addScalar("fullName").addScalar("msisdn").addScalar("startDate")
			           		.addScalar("endDate").addScalar("regionName").addScalar("totalCount").addScalar("successCount").addScalar("failedCount")
			           		.setResultTransformer(Transformers.aliasToBean(SimAgentSales.class)).list();
				         
				         model.addAttribute("agentsSalesCountList",mapper.writeValueAsString(agentReportResults)); 	
					
				              
				         // charts
				         // 1st chart and 1st piechart
					 
					     agentsCountChartQuery = sessionALM.createSQLQuery("select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >=  trunc(sysdate) AND b.created_date < (trunc(sysdate)) + 1 and b.status !='DEACTIVATED' and b.status !='CANCELLED'  GROUP BY a.full_name order by a.full_name");
					   
						// 2nd Chart and 3rd chart :x-axis time
						
						periodicDataQuery = sessionALM.createSQLQuery("select TO_CHAR(b.created_date, 'YYYY/MM/DD') as created_date,  count(*) as totalSimSales from agent a inner join clients b on a.msisdn = b.agent_number  where b.created_date >=  trunc(sysdate) AND b.created_date < (trunc(sysdate)) + 1 and b.status !='DEACTIVATED' and b.status !='CANCELLED' group by TO_CHAR(created_date, 'YYYY/MM/DD') order by created_date desc");
							
						// 3-pie chart
						registrationStatusQuery = sessionALM.createSQLQuery("SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >=  trunc(sysdate) AND b.created_date < (trunc(sysdate)) + 1  group by b.ussd_status");
						

							if (agentsCountChartQuery.list().size()>0) {
								chartJSNArr = chartSales(agentsCountChartQuery.list());
								model.addAttribute("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));	
							} else {
								model.addAttribute("msColumnChartObj", mapper.writeValueAsString(""));						
							}
							
							if (periodicDataQuery.list().size()>0 ) {
								defPeriodicData = getStackedChartData(periodicDataQuery.list());
								model.addAttribute("StackedandLineCount", mapper.writeValueAsString(defPeriodicData));
							}else {	
								model.addAttribute("StackedandLineCount", mapper.writeValueAsString(""));
							}
							
							model.addAttribute("listChartAgents", mapper.writeValueAsString(agentsCountChartQuery.list()));
							chartArray = new ArrayList<Float>(PieSalesPerAgent(agentsCountChartQuery.list()));	
							model.addAttribute("chartData", mapper.writeValueAsString(chartArray));
								
							chartArrayRegistrationStatus = new ArrayList<Float>(PieStatusPerClient(registrationStatusQuery.list()));	
							model.addAttribute("regStatusList", mapper.writeValueAsString(chartArrayRegistrationStatus)); 
		 		         }
		        		
				
			
				        catch (Exception e) {
				        	logger.info("Error in creating session with the DataBase " +e.getMessage());


				        	} 
				        
				        
				        finally {				        
				        	if (sessionALM != null && sessionALM.isOpen()) {
				        	
				        	 	 txALM.commit();
						       	 sessionALM.close();
				        	} 
				        }
		        }	        
			}
				return "Reports/SimAgentSalesReport";
		}
		
		// Generate of the map
		
		@SuppressWarnings("unchecked")
		@RequestMapping(value ="/GetSimCardSalesPerAgent", method = RequestMethod.GET)
		@ResponseBody public Map<String, Object> GetSimCardSalesPerAgent(Locale locale, Model model,
		   HttpServletRequest request, HttpServletResponse response){ 
		   
		   Map<String, Object> rtn = new LinkedHashMap<>(); 
		   String start_Date = request.getParameter("startDate");
		   String end_Date = request.getParameter("endDate");		   
		   String period = request.getParameter("period");
		   String region = request.getParameter("region"); 
		   String agentsName = request.getParameter("allAgentsName");
		  
		   Session session =null;
		   Transaction tx = null;
		   Query agentSalesCountQuery = null;		   
		   List<Object[]> finalAgentsList = new ArrayList<Object[]>();
		   String finalAgentsNameList = "";
		   String agentName = "";
		   String[] agentsNameList = null;	   			
		   DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");			 
		 
		 //  Date enddate = null;
		   Timestamp startDate;
		   Timestamp endDate;
		   
		   if (StringUtils.equalsIgnoreCase(agentsName,"") || StringUtils.equalsIgnoreCase(agentsName,null)) {
			   finalAgentsNameList ="";
	        }
		   else {
				  String allAgentsName = agentsName.concat(",");
				  //Split the string of sites on space-comma to get each agent separately
				  agentsNameList = allAgentsName.split("  ,");
				  StringBuilder allAgentsNames = new StringBuilder();
				 //Append to each element '' and comma to satisfy the format of IN parameters
				   for(int i=0;i<agentsNameList.length;i++){  
					   allAgentsNames.append( "'"+agentsNameList[i]+"'," );
					  }
				   finalAgentsNameList = allAgentsNames.toString();
				   finalAgentsNameList = finalAgentsNameList.substring(0, finalAgentsNameList.length()-1);
				   
		   }
		   if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", "redirect:/");
				return rtn;
			}
		 else {	 
			 try {
				 startDate = new Timestamp(formatter.parse(start_Date).getTime());
				 endDate = new Timestamp(formatter.parse(end_Date).getTime());
			     session = almsessions.getSession();
			    
			    if (session != null && session.isOpen()) {
			    	tx = session.beginTransaction();
			    	
			    	// if agent is selected 
					if (finalAgentsNameList != "" ) {
			    	
						 for (int i=0 ; i<agentsNameList.length; i++) {
						
							 agentName = finalAgentsNameList.split(",")[i];						  
						     
						     agentSalesCountQuery =session.createSQLQuery("SELECT (SELECT coalesce(count(a.agent_id),0) FROM agent a inner join clients b on a.msisdn = b.agent_number " + 
							   		 "where b.created_date >= :param1 and b.created_date <= :param2 and a.full_name ="+agentName+" and a.region_name LIKE '%"+region+"%' AND b.REGISTRATION_STATUS ='Success' )total_count, "+ 
								   		" agent_id,full_name,latitude,longitude,MSISDN FROM agent where full_name ="+agentName+" ");
						     agentSalesCountQuery.setParameter("param1", startDate);
						     agentSalesCountQuery.setParameter("param2", endDate);	
						  
							 finalAgentsList.addAll(agentSalesCountQuery.list());
							 } // end loop
						 
						 if (finalAgentsList != null  ) {
							 rtn.put("agentSalesCountList",finalAgentsList);
						 }				   
					   }
					
					else{
						
						if(StringUtils.equalsIgnoreCase(period,"Daily") ||StringUtils.equalsIgnoreCase(period,"null") ||StringUtils.equalsIgnoreCase(period,"Weekly") ||StringUtils.equalsIgnoreCase(period,"Monthly") ||StringUtils.equalsIgnoreCase(period,"Accu")) {
						
							
							agentSalesCountQuery=session.createSQLQuery("select count(*),a.agent_id,a.full_name,a.latitude,a.longitude,a.msisdn from agent a inner join " + 
							   		"clients b on a.msisdn = b.agent_number where b.created_date >= :param1 and b.created_date <= :param2 and a.region_name LIKE '%"+region+"%' AND b.REGISTRATION_STATUS ='Success' " + 
							   		"GROUP BY a.agent_id,a.full_name,a.latitude,a.longitude,a.msisdn ");
							agentSalesCountQuery.setParameter("param1", startDate);
							agentSalesCountQuery.setParameter("param2", endDate);	
														   
							rtn.put("agentSalesCountList",agentSalesCountQuery.list());
						
						   } // end period conditions
					}
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
		 }
		   return rtn;
		   
		}
		
		// for the Grid
		@SuppressWarnings("unchecked")
		@RequestMapping(value ="/GenerateAgentSalesReport", method = RequestMethod.GET)
		@ResponseBody public Map<String, Object> GenerateAgentSalesReport(Locale locale, Model model,
		   HttpServletRequest request, HttpServletResponse response) {
		   //logger.info("Welcome home! The client locale is {}.", locale);

		   Map<String, Object> rtn = new LinkedHashMap<>(); 
		   String period = request.getParameter("period");
		   String max = request.getParameter("Max");
		   String min = request.getParameter("Min");
		   String region = request.getParameter("region");		   
		   String agentsName = request.getParameter("allAgentsName");
		   String finalAgentsNameList = "";
		   String[] agentsNameList = null;
		    
			Session session = null;
			Transaction tx =null;
			String start_Date = request.getParameter("startDate");
			String end_Date = request.getParameter("endDate");
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			Timestamp startDate;
			Timestamp endDate;
			
			String agentSalesCount ="";
			Query agentSalesCountQuery = null;
			List<SimAgentSales> finalAgentSalesList = new ArrayList<SimAgentSales>();
		  		       
		    if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", "redirect:/");
				return rtn;
			}
		    else {
		    	
		 	   if (StringUtils.equalsIgnoreCase(agentsName,"") || StringUtils.equalsIgnoreCase(agentsName,null)) {
				   finalAgentsNameList ="";
		        }
			   else {
				   String allAgentsName = agentsName.concat(",");
				   //Split the string of sites on space-comma to get each agent separately
				   agentsNameList = allAgentsName.split("  ,");
				   StringBuilder allAgentsNames = new StringBuilder();
				   //Append to each element '' and comma to satisfy the format of IN parameters
				   for(int i=0;i<agentsNameList.length;i++){  
					   allAgentsNames.append( "'"+agentsNameList[i]+"'," );
				   }
				   finalAgentsNameList = allAgentsNames.toString();
				   finalAgentsNameList = finalAgentsNameList.substring(0, finalAgentsNameList.length()-1);
				 }
		    	
				try {
				
					startDate = new Timestamp(formatter.parse(start_Date).getTime());
					endDate = new Timestamp(formatter.parse(end_Date).getTime());
					
					System.out.println("startDate is  "+startDate);
					System.out.println("endDate is  "+endDate);

				    session = almsessions.getSession();
				    
				    if (session != null && session.isOpen()) {	
			    
				    	tx = session.beginTransaction();
			   
				   // if agent is not selected
			    	 if(finalAgentsNameList == "") {
			    		 
			    		 // period is daily
						 if (StringUtils.equalsIgnoreCase(period,"Daily")) {
							 
						 
						 //Null Max - Null Min
			             if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
			            	 //agentSalesCount ="select a.agent_id as \"agentId\" ,a.full_name as \"fullName\" ,a.msisdn as \"msisdn\" ,to_char(b.created_date, 'YYYY-MM-DD')  as \"startDate\" ,to_char(b.created_date, 'YYYY-MM-DD')  as \"endDate\" ,a.region_name as \"regionName\" ,count(*) as \"totalCount\",(SELECT successCount FROM(SELECT count(*) as successCount,a.agent_id from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.agent_id )) as \"successCount\",(SELECT failedCount FROM(SELECT count(*) as failedCount,a.agent_id from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC(b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.agent_id)) as \"failedCount\" from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
			            		//	 " and a.REGION_NAME LIKE '%"+region+"%' group by to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn order by to_char(b.created_date, 'YYYY-MM-DD') DESC";
			            
			            	 agentSalesCount =" SELECT agent_id as \"agentId\" ,full_name as \"fullName\" ,msisdn as \"msisdn\",createdDate as \"startDate\",endDate as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\",coalesce(successCount,0) as \"successCount\" ,coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.createdDate as createdDate,A.endDate as endDate,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount FROM( "
			            	 				+ "  SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as totalCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED'  AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  group by to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn "
			            	 				+ ")A LEFT JOIN (  SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as secondMSISDN ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn "
			            	 				+ " )B ON A.createdDate = B.createdDate and a.msisdn = b.secondMSISDN  )D LEFT JOIN ( SELECT a.agent_id as agent_id1,a.full_name as full_name1  ,a.msisdn as secondMSISDN1 ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate1 ,to_char(b.created_date, 'YYYY-MM-DD') as endDate1,a.region_name as region_name1 ,count(*) as failedCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%'  and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn )C ON D.createdDate = C.createdDate1 and D.msisdn = C.secondMSISDN1 ) ORDER BY full_name ASC,createdDate DESC";
			            	 				
			            	 				
			           }
			             // Daily-Max condition
			             else if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {			            	 
			            	 agentSalesCount="SELECT agent_id as \"agentId\",full_name as \"fullName\" ,msisdn as \"msisdn\",createdDate as \"startDate\",endDate as \"endDate\" ,region_name as \"regionName\" ,totalCount as \"totalCount\",coalesce(successCount,0) as \"successCount\" ,coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.createdDate as createdDate,A.endDate as endDate,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount FROM ( SELECT agent_id as agent_id , FULL_NAME as full_name ,MSISDN  as msisdn ,created_date  as createdDate ,created_date as endDate ,region_name as region_name ,dailyCount as totalCount "
			            	 		+ " FROM( SELECT a.agent_id as agent_id, a.full_name as FULL_NAME, a.MSISDN as MSISDN,TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, a.region_name as region_name,COUNT(a.msisdn)as dailyCount,RANK() OVER(PARTITION BY TO_CHAR(b.created_date, 'YYYY-MM-DD') ORDER BY COUNT(a.msisdn) DESC) Rank from agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY to_char(b.created_date, 'YYYY-MM-DD'), a.agent_id,a.full_name,a.msisdn,a.region_name  ORDER BY FULL_NAME ASC,TO_CHAR(b.created_date, 'YYYY-MM-DD') ,Rank) WHERE RANK=1 )A LEFT JOIN ( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as secondMSISDN ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' "
			            	 		+ " GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn)B ON A.createdDate = B.createdDate and a.msisdn = b.secondMSISDN )D LEFT JOIN (  SELECT a.agent_id as agent_id1,a.full_name as full_name1  ,a.msisdn as secondMSISDN1 ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate1 ,to_char(b.created_date, 'YYYY-MM-DD') as endDate1,a.region_name as region_name1 ,count(*) as failedCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn )C ON D.createdDate = C.createdDate1 and D.msisdn = C.secondMSISDN1 ) ORDER BY full_name ASC,createdDate DESC";
			             
			             
			             }
			             // Daily-Min condition
			             else if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
			            	 
			            	 
			            	 agentSalesCount="SELECT agent_id as \"agentId\",full_name as \"fullName\" ,msisdn as \"msisdn\",createdDate as \"startDate\",endDate as \"endDate\" ,region_name as \"regionName\" ,totalCount as \"totalCount\",coalesce(successCount,0) as \"successCount\" ,coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.createdDate as createdDate,A.endDate as endDate,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount FROM ( SELECT agent_id as agent_id , FULL_NAME as full_name ,MSISDN  as msisdn ,created_date  as createdDate ,created_date as endDate ,region_name as region_name ,dailyCount as totalCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id, a.full_name as FULL_NAME, a.MSISDN as MSISDN,TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, a.region_name as region_name,COUNT(a.msisdn)as dailyCount,RANK() OVER(PARTITION BY TO_CHAR(b.created_date, 'YYYY-MM-DD') ORDER BY COUNT(a.msisdn) ASC) Rank from agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY to_char(b.created_date, 'YYYY-MM-DD'), a.agent_id,a.full_name,a.msisdn,a.region_name  ORDER BY FULL_NAME ASC,TO_CHAR(b.created_date, 'YYYY-MM-DD') ,Rank) WHERE RANK=1 )A LEFT JOIN ( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as secondMSISDN ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' "
				            	 		+ " GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn)B ON A.createdDate = B.createdDate and a.msisdn = b.secondMSISDN )D LEFT JOIN (  SELECT a.agent_id as agent_id1,a.full_name as full_name1  ,a.msisdn as secondMSISDN1 ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate1 ,to_char(b.created_date, 'YYYY-MM-DD') as endDate1,a.region_name as region_name1 ,count(*) as failedCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn )C ON D.createdDate = C.createdDate1 and D.msisdn = C.secondMSISDN1 ) ORDER BY full_name ASC,createdDate DESC";
			
			             }
			             // Daily-Max-Min
			             else if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
			            	
			            	 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\" ,msisdn as \"msisdn\",createdDate as \"startDate\",endDate as \"endDate\" ,region_name as \"regionName\" ,totalCount as \"totalCount\",coalesce(successCount,0) as \"successCount\" ,coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.createdDate as createdDate,A.endDate as endDate,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount FROM ( SELECT agent_id as agent_id , FULL_NAME as full_name ,MSISDN  as msisdn ,created_date  as createdDate ,created_date as endDate ,region_name as region_name ,dailyCount as totalCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id, a.full_name as FULL_NAME, a.MSISDN as MSISDN,TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, a.region_name as region_name,COUNT(a.msisdn)as dailyCount,RANK() OVER(PARTITION BY TO_CHAR(b.created_date, 'YYYY-MM-DD') ORDER BY COUNT(a.msisdn) DESC) Rank from agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY to_char(b.created_date, 'YYYY-MM-DD'), a.agent_id,a.full_name,a.msisdn,a.region_name  ORDER BY FULL_NAME ASC,TO_CHAR(b.created_date, 'YYYY-MM-DD') ,Rank) WHERE RANK=1 )A LEFT JOIN ( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as secondMSISDN ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' "
				            	 		+ " GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn)B ON A.createdDate = B.createdDate and a.msisdn = b.secondMSISDN )D LEFT JOIN (  SELECT a.agent_id as agent_id1,a.full_name as full_name1  ,a.msisdn as secondMSISDN1 ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate1 ,to_char(b.created_date, 'YYYY-MM-DD') as endDate1,a.region_name as region_name1 ,count(*) as failedCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn )C ON D.createdDate = C.createdDate1 and D.msisdn = C.secondMSISDN1 ) ORDER BY full_name ASC,createdDate DESC"
				            	 		+" UNION ( "
				            	 		+ " SELECT agent_id as \"agentId\",full_name as \"fullName\" ,msisdn as \"msisdn\",createdDate as \"startDate\",endDate as \"endDate\" ,region_name as \"regionName\" ,totalCount as \"totalCount\",coalesce(successCount,0) as \"successCount\" ,coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.createdDate as createdDate,A.endDate as endDate,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount FROM ( SELECT agent_id as agent_id , FULL_NAME as full_name ,MSISDN  as msisdn ,created_date  as createdDate ,created_date as endDate ,region_name as region_name ,dailyCount as totalCount " + 
				            	 		" FROM( SELECT a.agent_id as agent_id, a.full_name as FULL_NAME, a.MSISDN as MSISDN,TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, a.region_name as region_name,COUNT(a.msisdn)as dailyCount,RANK() OVER(PARTITION BY TO_CHAR(b.created_date, 'YYYY-MM-DD') ORDER BY COUNT(a.msisdn) ASC) Rank from agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY to_char(b.created_date, 'YYYY-MM-DD'), a.agent_id,a.full_name,a.msisdn,a.region_name  ORDER BY FULL_NAME ASC,TO_CHAR(b.created_date, 'YYYY-MM-DD') ,Rank) WHERE RANK=1 )A LEFT JOIN ( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as secondMSISDN ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' " + 
				            	 		" GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn)B ON A.createdDate = B.createdDate and a.msisdn = b.secondMSISDN )D LEFT JOIN (  SELECT a.agent_id as agent_id1,a.full_name as full_name1  ,a.msisdn as secondMSISDN1 ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate1 ,to_char(b.created_date, 'YYYY-MM-DD') as endDate1,a.region_name as region_name1 ,count(*) as failedCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn )C ON D.createdDate = C.createdDate1 and D.msisdn = C.secondMSISDN1 ) ORDER BY full_name ASC,createdDate DESC" + 
				            	 		" ) ORDER BY  \"startDate\"  ASC, \"totalCount\" DESC";
			            
			            }
			             
			             agentSalesCountQuery =session.createSQLQuery(agentSalesCount);
			             agentSalesCountQuery.setParameter("param1", startDate);
			             agentSalesCountQuery.setParameter("param2", endDate);
			             
			             @SuppressWarnings("unchecked")
			             List<SimAgentSales> agentReportResults = (List<SimAgentSales>) ((SQLQuery) agentSalesCountQuery)
			             	.addScalar("agentId").addScalar("fullName").addScalar("msisdn").addScalar("startDate")
			           		.addScalar("endDate").addScalar("regionName").addScalar("totalCount").addScalar("successCount").addScalar("failedCount")
			           		.setResultTransformer(Transformers.aliasToBean(SimAgentSales.class)).list();
			        
			           ObjectMapper mapper = new ObjectMapper();
			  		   System.out.println("enter"+mapper.writeValueAsString(agentReportResults));

			             rtn.put("agentsSalesCountList", agentReportResults);
					
					 } // end of daily condition with null agent 
					 
					 // period is accumulated and agent is not selected
					 if (StringUtils.equalsIgnoreCase(period,"Accu")) {
						 
						 //Null Max - Null Minimum
						 if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
							// agentSalesCount ="select a.agent_id as \"agentId\" ,a.full_name as \"fullName\" ,a.msisdn as \"msisdn\" ,'"+startDate+"' as \"startDate\" ,'"+endDate+"' as \"endDate\" ,a.region_name as \"regionName\" ,count(*) as \"totalCount\" from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
									// " and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn order by \"fullName\", \"startDate\" DESC";
					
							 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn ) A "
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\" DESC ";

						 }
						 // Accumulated-Max condition
						 else if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
							 
							 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MAX(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
				            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\" DESC ";

							
							}
						 // Accumulated-Min condition
						 else  if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
							 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MIN(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
				            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\" DESC ";

						}
						 // Accumulated-Max-Min condition
						 else  if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
							 
							 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MAX(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
				            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN )"
									 	+" UNION ( "
					            		+" SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MIN(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
				            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN )"
					            		+ " ) ORDER BY \"totalCount\" DESC " ;
							 }
						 agentSalesCountQuery =session.createSQLQuery(agentSalesCount);
			             agentSalesCountQuery.setParameter("param1", startDate);
			             agentSalesCountQuery.setParameter("param2", endDate);	

						 
						  @SuppressWarnings("unchecked")
			           		List<SimAgentSales> agentReportResults = (List<SimAgentSales>) ((SQLQuery) agentSalesCountQuery)
			           		.addScalar("agentId").addScalar("fullName").addScalar("msisdn").addScalar("startDate")
			           		.addScalar("endDate").addScalar("regionName").addScalar("totalCount").addScalar("successCount").addScalar("failedCount")
			           		.setResultTransformer(Transformers.aliasToBean(SimAgentSales.class)).list();
						  
						  rtn.put("agentsSalesCountList", agentReportResults); 
					    	
							
					}// end of accumulated condition with null agent
					 
					// period is weekly	and agent is not selected	  
					 if (StringUtils.equalsIgnoreCase(period,"Weekly")) {
						
		   	 	         double diff =(endDate.getTime() - startDate.getTime()) / 1000.0;
		   	 	         diff /= (60 * 60 * 24 * 7);
		   	 	         diff =  Math.abs(Math.ceil(diff));
		   	 	         Date weeklyDate;
			 	         Timestamp weeklyEndDate = startDate;
			 	         Calendar cal = Calendar.getInstance();  
			 	     
			 	         for(int i = 0; i< diff; i++) {
			 	        	 weeklyEndDate = startDate ;
			 	        	 cal.setTime(weeklyEndDate);  
					 	     cal.add(Calendar.DAY_OF_MONTH, 6); // 6 is the days to add  
					 	     weeklyDate = cal.getTime(); 
					 	     weeklyEndDate = new Timestamp(weeklyDate.getTime());
					 	     					 	      
			 	         
			 	    	  //the weeklyEndDate is greater than set enddate
					 	   if((weeklyEndDate).compareTo(endDate)> 0) {
						 	    weeklyEndDate =  endDate;
						   }
					 	   //Null Max - Null Min
					 	   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
					 		 
					 		   agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+weeklyEndDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn ) A "
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\" DESC ";

					 		  // agentSalesCount ="select a.agent_id as \"agentId\" ,a.full_name as \"fullName\" ,a.msisdn as \"msisdn\" , '"+startDate+"' as \"startDate\" ,'"+weeklyEndDate+"' as \"endDate\" ,a.region_name as \"regionName\" ,count(*) as \"totalCount\" from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
					 			//	   " and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn order by \"fullName\" ASC, \"startDate\" DESC";
					 		   }
					 	   // Weekly-Max condition
					 	   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
					 		 
					 		   
					 		  agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+weeklyEndDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MAX(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
				            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\" DESC ";

					 		   }
					 	   // Weekly-Min condition
					 	   if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
					 		 
					 		   
					 		  agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+weeklyEndDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MIN(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
				            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\" DESC ";

					 		  }
					 	   //Weekly-Max-Min condition
					 	   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
					 		   agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+weeklyEndDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MAX(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
				            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) "+
						            	" UNION ( "
						            	+"SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+weeklyEndDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MIN(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
				            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) "
						            	+ " ) ORDER BY \"totalCount\" DESC " ;	 						               
					 		   }
					       
				             agentSalesCountQuery =session.createSQLQuery(agentSalesCount);
				             agentSalesCountQuery.setParameter("param1", startDate);
				             agentSalesCountQuery.setParameter("param2", weeklyEndDate);	

				             
							  @SuppressWarnings("unchecked")
				           		List<SimAgentSales> agentReportResults = (List<SimAgentSales>) ((SQLQuery) agentSalesCountQuery)
				           		.addScalar("agentId").addScalar("fullName").addScalar("msisdn").addScalar("startDate")
				           		.addScalar("endDate").addScalar("regionName").addScalar("totalCount").addScalar("successCount").addScalar("failedCount")
				           		.setResultTransformer(Transformers.aliasToBean(SimAgentSales.class)).list();
				           	   
							  
							  finalAgentSalesList.addAll(agentReportResults);
				           	 
				           	 	startDate = weeklyEndDate;
							    cal.setTime(startDate);  
							 	cal.add(Calendar.DAY_OF_MONTH, 1); // 1 is the day to add    
							 	weeklyDate = cal.getTime();   
							 	startDate = new Timestamp(weeklyDate.getTime());
				           	   				             
			 	         } // end loop

			           	   rtn.put("agentsSalesCountList", finalAgentSalesList); 

			 	    } // end of weekly period with null agent
					 
					   //Monthly period with agent not selected
						if (StringUtils.equalsIgnoreCase(period,"Monthly")) {
						
							Calendar m =Calendar.getInstance();
							 m.setTime(startDate);
							 int nMonth1=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
							 m.setTime(endDate);
							 int nMonth2=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
							 int diff = Math.abs(nMonth2-nMonth1);		   	 	         
			   	 	         Timestamp month = startDate;
			   	 	         
			   	 	     for(int i = 0; i<= diff; i++) {
				 	        	Timestamp monthofDate = new Timestamp(formatter.parse(formatter.format(month)).getTime());
							
					    		Calendar calendar = Calendar.getInstance(); 
					    		calendar.setTime(monthofDate); 
					    		calendar.add(Calendar.MONTH, 1);  
					    	    calendar.set(Calendar.DAY_OF_MONTH, 1);  
					    	    calendar.add(Calendar.DATE, -1);  
					    	    Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());  
				    	     
								Timestamp endOfMonth = new Timestamp(formatter.parse(formatter.format(lastDayOfMonth)).getTime());
								//the endOfMonth is greater than set enddate 
						        if((endOfMonth).compareTo(endDate)> 0) {
							 	    endOfMonth =  endDate;
							   }
						        //Null Max - Null Min  
						        if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
						        /*	agentSalesCount ="select a.agent_id as \"agentId\" ,a.full_name as \"fullName\" ,a.msisdn as \"msisdn\" ,'"+monthofDate+"' as \"startDate\" ,'"+endOfMonth+"' as \"endDate\" ,a.region_name as \"regionName\" ,count(*) as \"totalCount\" from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
						        			" and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn order by \"fullName\", \"startDate\" DESC";
						        */
						        	agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+monthofDate+"' as \"startDate\", '"+endOfMonth+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
						            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn ) A "
						            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
						            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN )order by \"fullName\", \"startDate\" DESC ";

						        }
						        
						        //Monthly-Max condition
						        if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
						        	
						        	
						        	 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+monthofDate+"' as \"startDate\", '"+endOfMonth+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
						            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MAX(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
						            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
						            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
						            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\" DESC ";

						        }
						        //Monthly-Min condition
						       if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
						    	
						    	   agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+monthofDate+"' as \"startDate\", '"+endOfMonth+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
					            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MIN(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
					            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
					            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
					            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\" DESC ";

								}
						       //Monthly-Max-Min condition
						       if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
					            	
						    	   agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+monthofDate+"' as \"startDate\", '"+endOfMonth+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
					            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MAX(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
					            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
					            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
					            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) "+
							            	" UNION ( "
							            	+"SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+monthofDate+"' as \"startDate\", '"+endOfMonth+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
					            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MIN(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
					            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
					            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
					            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) "
							            	+ " ) ORDER BY \"totalCount\" DESC " ;	 
					           }// end monthly min-max 
						       
						        agentSalesCountQuery =session.createSQLQuery(agentSalesCount);
					             agentSalesCountQuery.setParameter("param1", monthofDate);
					             agentSalesCountQuery.setParameter("param2", endOfMonth);	

								 
								  @SuppressWarnings("unchecked")
					           		List<SimAgentSales> agentReportResults = (List<SimAgentSales>) ((SQLQuery) agentSalesCountQuery)
					           		.addScalar("agentId").addScalar("fullName").addScalar("msisdn").addScalar("startDate")
					           		.addScalar("endDate").addScalar("regionName").addScalar("totalCount").addScalar("successCount").addScalar("failedCount")
					           		.setResultTransformer(Transformers.aliasToBean(SimAgentSales.class)).list();
								 
								  finalAgentSalesList.addAll(agentReportResults);
						           	 
						           	 calendar.setTime(endOfMonth);  
							    	 calendar.add(Calendar.DATE, +1);  
							    	 Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
						             month = NextDayOfMonth;
		
						        
			   	 	     } // end of loop 
			   	 	     	rtn.put("agentsSalesCountList", finalAgentSalesList); 
			   	 	         
						}// end of monthy period with agent null 
						
						 // period is null and agent is not selected
						 if (StringUtils.equalsIgnoreCase(period,"null")) {
							
				             // Absolutely Maximum
				             if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
				            	 
				            	
				            	 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
					            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MAX(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
					            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
					            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
					            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\" DESC ";

				             }
				             // Absolutely Minimum
				             else  if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
			                	   
				            	
				            	 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
					            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MIN(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
					            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
					            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
					            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\" DESC ";

				             }
				             // Absolutely Maximum/Minimum
				             else  if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
				            	 
				            	
				            	 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
					            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MAX(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
					            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
					            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
					            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) "+
							            	" UNION ( "
							            	+"SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\",'"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\", coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
					            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn HAVING COUNT(a.msisdn)=(SELECT MIN(salesCount) FROM (SELECT a.agent_id,a.full_name,a.msisdn,a.region_name, COUNT(a.msisdn) as salesCount"
					            	 		+ " FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name))  ) A "
					            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' GROUP BY a.MSISDN) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
					            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) "
							            	+ " ) ORDER BY \"totalCount\" DESC " ;
				           }
				             
				             agentSalesCountQuery =session.createSQLQuery(agentSalesCount);
				             agentSalesCountQuery.setParameter("param1", startDate);
				             agentSalesCountQuery.setParameter("param2", endDate);	

							 
							  @SuppressWarnings("unchecked")
				           		List<SimAgentSales> agentReportResults = (List<SimAgentSales>) ((SQLQuery) agentSalesCountQuery)
				           		.addScalar("agentId").addScalar("fullName").addScalar("msisdn").addScalar("startDate")
				           		.addScalar("endDate").addScalar("regionName").addScalar("totalCount").addScalar("successCount").addScalar("failedCount")
				           		.setResultTransformer(Transformers.aliasToBean(SimAgentSales.class)).list();
							
							  rtn.put("agentsSalesCountList", agentReportResults); 
						    	
								
						}// end of null period condition with null agent
					
				 }// end null agent condition
		    	 
		    	 
		    	// if AGENT is selected	 
			     else {			    	 
			    
			    	 // period is daily and agent is selected
					 if (StringUtils.equalsIgnoreCase(period,"Daily")) {
						 
						 //Null Max - Null Min  
			             if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
			            	 
			            	/* agentSalesCount ="select a.agent_id as \"agentId\" ,a.full_name as \"fullName\" ,a.msisdn as \"msisdn\" ,to_char(b.created_date, 'YYYY-MM-DD') as \"startDate\" ,to_char(b.created_date, 'YYYY-MM-DD') as \"endDate\" ,a.region_name as \"regionName\" ,count(*) as \"totalCount\" from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
			                	" and a.full_name IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' group by to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn order by to_char(b.created_date, 'YYYY-MM-DD') DESC";
			            	*/
			            	 agentSalesCount =" SELECT agent_id as \"agentId\" ,full_name as \"fullName\" ,msisdn as \"msisdn\",createdDate as \"startDate\",endDate as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\",coalesce(successCount,0) as \"successCount\" ,coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.createdDate as createdDate,A.endDate as endDate,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount FROM( "
		            	 				+ "  SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as totalCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED'  AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' and a.full_name IN ("+finalAgentsNameList+")   group by to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn "
		            	 				+ " )A LEFT JOIN (  SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as secondMSISDN ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success'  and a.full_name IN ("+finalAgentsNameList+")  GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn "
		            	 				+ " )B ON A.createdDate = B.createdDate and a.msisdn = b.secondMSISDN  )D LEFT JOIN ( SELECT a.agent_id as agent_id1,a.full_name as full_name1  ,a.msisdn as secondMSISDN1 ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate1 ,to_char(b.created_date, 'YYYY-MM-DD') as endDate1,a.region_name as region_name1 ,count(*) as failedCount from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%'  and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed'  and a.full_name IN ("+finalAgentsNameList+")  GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn )C ON D.createdDate = C.createdDate1 and D.msisdn = C.secondMSISDN1 ) ";
		            	 	
			             }
			             //Daily-Max condition
			             else if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
			            	
			            	
			            	 agentSalesCount="SELECT agent_id as \"agentId\" ,full_name as \"fullName\" ,msisdn as \"msisdn\" ,createdDate as \"startDate\" ,endDate as \"endDate\" ,region_name as \"regionName\" ,totalCount \"totalCount\" ,coalesce(successCount,0) as \"successCount\" ,coalesce(failedCount,0) as \"failedCount\" FROM ( "
			            	 		+ " (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.createdDate as createdDate,A.endDate as endDate,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount FROM( SELECT agent_id as agent_id , FULL_NAME as full_name ,MSISDN  as msisdn ,created_date  as createdDate ,created_date as endDate ,region_name as region_name ,dailyCount as totalCount FROM( SELECT a.agent_id as agent_id, a.full_name as FULL_NAME, a.MSISDN as MSISDN,TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, a.region_name as region_name,COUNT(a.msisdn)as dailyCount,RANK() OVER(PARTITION BY TO_CHAR(b.created_date, 'YYYY-MM-DD') ORDER BY COUNT(a.msisdn) DESC) Rank from agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number"
			            	 		+ " WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' and a.full_name IN ("+finalAgentsNameList+") GROUP BY to_char(b.created_date, 'YYYY-MM-DD'), a.agent_id,a.full_name,a.msisdn,a.region_name  ORDER BY FULL_NAME ASC,TO_CHAR(b.created_date, 'YYYY-MM-DD') ,Rank) WHERE RANK=1 )A LEFT JOIN( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as secondMSISDN ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' "
			            	 		+ " and a.FULL_NAME IN ("+finalAgentsNameList+") GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn )B ON A.createdDate = B.createdDate and a.msisdn = b.secondMSISDN )D LEFT JOIN (  SELECT a.agent_id as agent_id1,a.full_name as full_name1  ,a.msisdn as secondMSISDN1 ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate1 ,to_char(b.created_date, 'YYYY-MM-DD') as endDate1,a.region_name as region_name1 ,count(*) as failedCount from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.FULL_NAME IN ("+finalAgentsNameList+")  GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn  )C ON D.createdDate = C.createdDate1 and D.msisdn = C.secondMSISDN1 ) order by  \"startDate\" DESC ";
		 
			            	
			            	 }
			             //Daily-Min condition
			             else if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
			            	
			            	 
			            	 agentSalesCount="SELECT agent_id as \"agentId\" ,full_name as \"fullName\" ,msisdn as \"msisdn\" ,createdDate as \"startDate\" ,endDate as \"endDate\" ,region_name as \"regionName\" ,totalCount \"totalCount\" ,coalesce(successCount,0) as \"successCount\" ,coalesce(failedCount,0) as \"failedCount\" FROM ( "
				            	 		+ " (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.createdDate as createdDate,A.endDate as endDate,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount FROM( SELECT agent_id as agent_id , FULL_NAME as full_name ,MSISDN  as msisdn ,created_date  as createdDate ,created_date as endDate ,region_name as region_name ,dailyCount as totalCount FROM( SELECT a.agent_id as agent_id, a.full_name as FULL_NAME, a.MSISDN as MSISDN,TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, a.region_name as region_name,COUNT(a.msisdn)as dailyCount,RANK() OVER(PARTITION BY TO_CHAR(b.created_date, 'YYYY-MM-DD') ORDER BY COUNT(a.msisdn) ASC) Rank from agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number"
				            	 		+ " WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' and a.full_name IN ("+finalAgentsNameList+") GROUP BY to_char(b.created_date, 'YYYY-MM-DD'), a.agent_id,a.full_name,a.msisdn,a.region_name  ORDER BY FULL_NAME ASC,TO_CHAR(b.created_date, 'YYYY-MM-DD') ,Rank) WHERE RANK=1 )A LEFT JOIN( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as secondMSISDN ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' "
				            	 		+ " and a.FULL_NAME IN ("+finalAgentsNameList+") GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn )B ON A.createdDate = B.createdDate and a.msisdn = b.secondMSISDN )D LEFT JOIN (  SELECT a.agent_id as agent_id1,a.full_name as full_name1  ,a.msisdn as secondMSISDN1 ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate1 ,to_char(b.created_date, 'YYYY-MM-DD') as endDate1,a.region_name as region_name1 ,count(*) as failedCount from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.FULL_NAME IN ("+finalAgentsNameList+")  GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn  )C ON D.createdDate = C.createdDate1 and D.msisdn = C.secondMSISDN1 ) order by  \"startDate\" DESC ";
			 
		 
			             }
			             //Daily-Max-Min condition
			             else if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
			            	
			            	 agentSalesCount="SELECT agent_id as \"agentId\" ,full_name as \"fullName\" ,msisdn as \"msisdn\" ,createdDate as \"startDate\" ,endDate as \"endDate\" ,region_name as \"regionName\" ,totalCount \"totalCount\" ,coalesce(successCount,0) as \"successCount\" ,coalesce(failedCount,0) as \"failedCount\" FROM ( "
				            	 		+ " (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.createdDate as createdDate,A.endDate as endDate,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount FROM( SELECT agent_id as agent_id , FULL_NAME as full_name ,MSISDN  as msisdn ,created_date  as createdDate ,created_date as endDate ,region_name as region_name ,dailyCount as totalCount FROM( SELECT a.agent_id as agent_id, a.full_name as FULL_NAME, a.MSISDN as MSISDN,TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, a.region_name as region_name,COUNT(a.msisdn)as dailyCount,RANK() OVER(PARTITION BY TO_CHAR(b.created_date, 'YYYY-MM-DD') ORDER BY COUNT(a.msisdn) DESC) Rank from agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number"
				            	 		+ " WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' and a.full_name IN ("+finalAgentsNameList+") GROUP BY to_char(b.created_date, 'YYYY-MM-DD'), a.agent_id,a.full_name,a.msisdn,a.region_name  ORDER BY FULL_NAME ASC,TO_CHAR(b.created_date, 'YYYY-MM-DD') ,Rank) WHERE RANK=1 )A LEFT JOIN( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as secondMSISDN ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' "
				            	 		+ " and a.FULL_NAME IN ("+finalAgentsNameList+") GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn )B ON A.createdDate = B.createdDate and a.msisdn = b.secondMSISDN )D LEFT JOIN (  SELECT a.agent_id as agent_id1,a.full_name as full_name1  ,a.msisdn as secondMSISDN1 ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate1 ,to_char(b.created_date, 'YYYY-MM-DD') as endDate1,a.region_name as region_name1 ,count(*) as failedCount from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.FULL_NAME IN ("+finalAgentsNameList+")  GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn  )C ON D.createdDate = C.createdDate1 and D.msisdn = C.secondMSISDN1 ) "
			 
		            			 		+ " UNION ( "
		            			 		+" SELECT agent_id as \"agentId\" ,full_name as \"fullName\" ,msisdn as \"msisdn\" ,createdDate as \"startDate\" ,endDate as \"endDate\" ,region_name as \"regionName\" ,totalCount \"totalCount\" ,coalesce(successCount,0) as \"successCount\" ,coalesce(failedCount,0) as \"failedCount\" FROM ( "
				            	 		+ " (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.createdDate as createdDate,A.endDate as endDate,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount FROM( SELECT agent_id as agent_id , FULL_NAME as full_name ,MSISDN  as msisdn ,created_date  as createdDate ,created_date as endDate ,region_name as region_name ,dailyCount as totalCount FROM( SELECT a.agent_id as agent_id, a.full_name as FULL_NAME, a.MSISDN as MSISDN,TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, a.region_name as region_name,COUNT(a.msisdn)as dailyCount,RANK() OVER(PARTITION BY TO_CHAR(b.created_date, 'YYYY-MM-DD') ORDER BY COUNT(a.msisdn) ASC) Rank from agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number"
				            	 		+ " WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' and a.full_name IN ("+finalAgentsNameList+") GROUP BY to_char(b.created_date, 'YYYY-MM-DD'), a.agent_id,a.full_name,a.msisdn,a.region_name  ORDER BY FULL_NAME ASC,TO_CHAR(b.created_date, 'YYYY-MM-DD') ,Rank) WHERE RANK=1 )A LEFT JOIN( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as secondMSISDN ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate ,to_char(b.created_date, 'YYYY-MM-DD') as endDate,a.region_name as region_name ,count(*) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' "
				            	 		+ " and a.FULL_NAME IN ("+finalAgentsNameList+") GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn )B ON A.createdDate = B.createdDate and a.msisdn = b.secondMSISDN )D LEFT JOIN (  SELECT a.agent_id as agent_id1,a.full_name as full_name1  ,a.msisdn as secondMSISDN1 ,to_char(b.created_date, 'YYYY-MM-DD') as createdDate1 ,to_char(b.created_date, 'YYYY-MM-DD') as endDate1,a.region_name as region_name1 ,count(*) as failedCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.FULL_NAME IN ("+finalAgentsNameList+")  GROUP BY to_char(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.full_name,a.region_name,a.msisdn  )C ON D.createdDate = C.createdDate1 and D.msisdn = C.secondMSISDN1 ) "
			 
		            			 		+ " ) ORDER BY \"startDate\" DESC";
			            	 
			                	   
			           }
			             agentSalesCountQuery =session.createSQLQuery(agentSalesCount);
			             agentSalesCountQuery.setParameter("param1", startDate);
			             agentSalesCountQuery.setParameter("param2", endDate);	
			             
			             @SuppressWarnings("unchecked")
			           	 List<SimAgentSales> agentReportResults = (List<SimAgentSales>) ((SQLQuery) agentSalesCountQuery)
			           		.addScalar("agentId").addScalar("fullName").addScalar("msisdn").addScalar("startDate")
			           		.addScalar("endDate").addScalar("regionName").addScalar("totalCount").addScalar("successCount").addScalar("failedCount")
			           		.setResultTransformer(Transformers.aliasToBean(SimAgentSales.class)).list();
						  
			    	      rtn.put("agentsSalesCountList", agentReportResults); 

						
					 } // end of daily condition with selected agent
					 
					 
					 // period is accumulated
					 if (StringUtils.equalsIgnoreCase(period,"Accu")) {
						 
						// Null Max - Null Min
			             if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
			            	 
			            	/* agentSalesCount ="select a.agent_id as \"agentId\" ,a.full_name as \"fullName\" ,a.msisdn as \"msisdn\" ,'"+startDate+"' as \"startDate\" ,'"+endDate+"' as \"endDate\" ,a.region_name as \"regionName\" ,count(*) as \"totalCount\" from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
			                	" and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn order by \"fullName\", \"startDate\" DESC";
							*/
			            	
						 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
			            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn ) A "
			            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
			            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\", \"startDate\" DESC ";
	 
					      }
			             // Accumulated-Max condition
			             if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
			            	 
			            	
			            	 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
				            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount desc) as RANK from " + 
					                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
					                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
					                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount desc)" + 
					                	  " ) t where RANK = 1 ) A"
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\"  ASC ";
			            	 
			            	 	            	
			             }
			             // Accumulated-Min condition
			             if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
		                	
			            	 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
				            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount ASC) as RANK from " + 
					                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
					                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
					                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount ASC)" + 
					                	  " ) t where RANK = 1 ) A"
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\"  ASC ";
			            	 
			             }
			             //Accumulated-Max-Min condition
			             if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
			            	 
			            	 
			            	 agentSalesCount="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
				            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount desc) as RANK from " + 
					                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
					                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE   b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
					                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount desc)" + 
					                	  " ) t where RANK = 1 ) A"
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN )"
		                			  + " UNION ( "
		                			  +"SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
				            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount ASC) as RANK from " + 
					                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
					                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
					                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount ASC)" + 
					                	  " ) t where RANK = 1 ) A"
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN )"
		                			  + ") ORDER BY \"fullName\" DESC , \"totalCount\" DESC";
		                	   
			            	 
			            	    }
			             
			             agentSalesCountQuery =session.createSQLQuery(agentSalesCount);
			             agentSalesCountQuery.setParameter("param1", startDate);
			             agentSalesCountQuery.setParameter("param2", endDate);	

						 
						  @SuppressWarnings("unchecked")
			           		List<SimAgentSales> agentReportResults = (List<SimAgentSales>) ((SQLQuery) agentSalesCountQuery)
			           		.addScalar("agentId").addScalar("fullName").addScalar("msisdn").addScalar("startDate")
			           		.addScalar("endDate").addScalar("regionName").addScalar("totalCount").addScalar("successCount").addScalar("failedCount")
			           		.setResultTransformer(Transformers.aliasToBean(SimAgentSales.class)).list();

						  rtn.put("agentsSalesCountList", agentReportResults); 
					    	
							
					}// end accumulated condition with selected agent
					 
					// period is weekly and agent is selected
					 if (StringUtils.equalsIgnoreCase(period,"Weekly")) {
						 
						 double diff =(endDate.getTime() - startDate.getTime()) / 1000.0;
		   	 	         diff /= (60 * 60 * 24 * 7);
		   	 	         diff =  Math.abs(Math.ceil(diff));
		   	 	         Date weeklyDate;
			 	         Timestamp weeklyEndDate = startDate;
			 	         Calendar cal = Calendar.getInstance();  
			 	     
			 	         for(int i = 0; i< diff; i++) {
			 	        	 weeklyEndDate = startDate ;
			 	        	 cal.setTime(weeklyEndDate);  
					 	     cal.add(Calendar.DAY_OF_MONTH, 6); // 6 is the days you want to add    
					 	     weeklyDate = cal.getTime(); 
					 	     weeklyEndDate = new Timestamp(weeklyDate.getTime());
					 	     
						 	   //the weeklyEndDate is greater than set enddate
						 	   if((weeklyEndDate).compareTo(endDate)> 0) {
							 	    weeklyEndDate =  endDate;
							   }
					 	   	//Weekly-Selected Agent- Null Max & Null Minimum 
				            if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null) ){
				            	
				            	/*agentSalesCount ="select a.agent_id as \"agentId\" ,a.full_name as \"fullName\" ,a.msisdn as \"msisdn\" , '"+startDate+"' as \"startDate\" ,'"+weeklyEndDate+"' as \"endDate\" ,a.region_name as \"regionName\" ,count(*) as \"totalCount\" from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
					                	" and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn order by \"fullName\" ASC, \"startDate\" DESC";
							     */       
				            	agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+weeklyEndDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn ) A "
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\", \"startDate\" DESC ";
		 
				            }
				            //Weekly-Selected Agent-Maximum  
				            else if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
				            	
				            	agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+weeklyEndDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
					            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
					            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount desc) as RANK from " + 
						                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
						                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
						                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount desc)" + 
						                	  " ) t where RANK = 1 ) A"
					            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
					            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\"  ASC ";
				            	 
				            	 	     
								    		
							} 
				            
				            //Weekly-Selected Agent-Minimum  
				            else if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
						          
						    	
				            	agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+weeklyEndDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
				            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount ASC) as RANK from " + 
					                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
					                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
					                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount ASC)" + 
					                	  " ) t where RANK = 1 ) A"
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\"  ASC ";
			            	 
							} 
				            //Weekly-Selected Agent-Minimum-Maximum
				            else if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
				            
				            	 agentSalesCount="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+weeklyEndDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
					            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
					            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount desc) as RANK from " + 
						                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
						                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
						                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount desc)" + 
						                	  " ) t where RANK = 1 ) A"
					            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
					            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN )"
			                			  + " UNION ( "
			                			  +"SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+weeklyEndDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
					            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
					            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount ASC) as RANK from " + 
						                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
						                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
						                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount ASC)" + 
						                	  " ) t where RANK = 1 ) A"
					            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
					            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN )"
			                			  + ") ORDER BY \"fullName\" DESC , \"totalCount\" DESC";
			                	   
				                }// end weekly min-max 
				                 agentSalesCountQuery =session.createSQLQuery(agentSalesCount);
					             agentSalesCountQuery.setParameter("param1", startDate);
					             agentSalesCountQuery.setParameter("param2", weeklyEndDate);	

					             
								@SuppressWarnings("unchecked")
					           	List<SimAgentSales> agentReportResults = (List<SimAgentSales>) ((SQLQuery) agentSalesCountQuery)
					           		.addScalar("agentId").addScalar("fullName").addScalar("msisdn").addScalar("startDate")
					           		.addScalar("endDate").addScalar("regionName").addScalar("totalCount").addScalar("successCount").addScalar("failedCount")
					           		.setResultTransformer(Transformers.aliasToBean(SimAgentSales.class)).list();
					           	   
								  
								finalAgentSalesList.addAll(agentReportResults);
					           	 
					           	 	startDate = weeklyEndDate;
								    cal.setTime(startDate);  
								 	cal.add(Calendar.DAY_OF_MONTH, 1); // 1 is the days you want to add or subtract   
								 	weeklyDate = cal.getTime();   
								 	startDate = new Timestamp(weeklyDate.getTime());
				 	         }       
					 	         
					 	     rtn.put("agentsSalesCountList", finalAgentSalesList);       
					 	      
					 }	// end of weekly period
					 

					 //monthly period
						if (StringUtils.equalsIgnoreCase(period,"Monthly")) {
							
							Calendar m =Calendar.getInstance();
							m.setTime(startDate);
							int nMonth1=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
							m.setTime(endDate);
							int nMonth2=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
							int diff = Math.abs(nMonth2-nMonth1);		   	 	         
			   	 	        Timestamp month = startDate;
			   	 	        
			   	 	        for(int i = 0; i<= diff; i++) {
			   	 	        	
							
								Timestamp monthofDate = new Timestamp(formatter.parse(formatter.format(month)).getTime());
					    		Calendar calendar = Calendar.getInstance(); 
					    		calendar.setTime(monthofDate); 
					    		calendar.add(Calendar.MONTH, 1);  
					    	    calendar.set(Calendar.DAY_OF_MONTH, 1);  
					    	    calendar.add(Calendar.DATE, -1);  
					    	    Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());  
				    	        Timestamp endOfMonth = new Timestamp( formatter.parse(formatter.format(lastDayOfMonth)).getTime());
						        
								//the endOfMonth is greater than set enddate 
						        if((endOfMonth).compareTo(endDate)> 0) {
							 	    endOfMonth =  endDate;
							   }	
							
							
							// Monthly-Selected Agent- Null Maximum & Null Minimum 
						    if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
						    	
							        
			                	/* agentSalesCount ="select a.agent_id as \"agentId\" ,a.full_name as \"fullName\" ,a.msisdn as \"msisdn\" ,'"+monthofDate+"' as \"startDate\" ,'"+endOfMonth+"' as \"endDate\" ,a.region_name as \"regionName\" ,count(*) as \"totalCount\" from agent a inner join clients b on a.msisdn = b.agent_number where TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
						               " and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn order by \"fullName\", \"startDate\" DESC";
			                	 */
						    	agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+monthofDate+"' as \"startDate\", '"+endOfMonth+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT a.agent_id as agent_id,a.full_name as full_name  ,a.msisdn as msisdn ,a.region_name as region_name ,count(*) as totalCount  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id,a.full_name,a.region_name,a.msisdn ) A "
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\", \"startDate\" DESC ";
		 
						    }
						 // Monthly-Selected Agent-  Maximum  
						    else if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
							   
						    	
						    	agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+monthofDate+"' as \"startDate\", '"+endOfMonth+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
				            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount desc) as RANK from " + 
					                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
					                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
					                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount desc)" + 
					                	  " ) t where RANK = 1 ) A"
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\"  ASC ";
			            	 
			            	 
						     } 
						    // Monthly-Selected Agent-Minimum  
					        else if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,"Min")) {
					        	   
					        
					        	agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+monthofDate+"' as \"startDate\", '"+endOfMonth+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
				            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
				            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount ASC) as RANK from " + 
					                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
					                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
					                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount ASC)" + 
					                	  " ) t where RANK = 1 ) A"
				            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
				            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\"  ASC ";
			            	 
					        	
							  }
						 // Monthly-Selected Agent-Minimum-Maximum
					        else if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
					        
					        	agentSalesCount="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+monthofDate+"' as \"startDate\", '"+endOfMonth+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
					            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
					            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount desc) as RANK from " + 
						                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
						                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
						                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount desc)" + 
						                	  " ) t where RANK = 1 ) A"
					            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
					            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN )"
			                			  + " UNION ( "
			                			  +"SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+monthofDate+"' as \"startDate\", '"+endOfMonth+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
					            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
					            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount ASC) as RANK from " + 
						                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
						                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
						                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount ASC)" + 
						                	  " ) t where RANK = 1 ) A"
					            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
					            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN )"
			                			  + ") ORDER BY \"fullName\" DESC , \"totalCount\" DESC";
					        	}
						    
			                	 agentSalesCountQuery =session.createSQLQuery(agentSalesCount);
					             agentSalesCountQuery.setParameter("param1", monthofDate);
					             agentSalesCountQuery.setParameter("param2", endOfMonth);	
								
					             @SuppressWarnings("unchecked")
					           		List<SimAgentSales> agentReportResults = (List<SimAgentSales>) ((SQLQuery) agentSalesCountQuery)
					           		.addScalar("agentId").addScalar("fullName").addScalar("msisdn").addScalar("startDate")
					           		.addScalar("endDate").addScalar("regionName").addScalar("totalCount").addScalar("successCount").addScalar("failedCount")
					           		.setResultTransformer(Transformers.aliasToBean(SimAgentSales.class)).list();
					             
					             finalAgentSalesList.addAll(agentReportResults);
					             calendar.setTime(endOfMonth);  
							     calendar.add(Calendar.DATE, +1);  
							     Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
						         month = NextDayOfMonth;
				   	 	        
				   	 	        }// end diff loop
				   	 	    
			   	 	        rtn.put("agentsSalesCountList", finalAgentSalesList); 	        
				   	 	        
			             
				}// end of monthy period
						
			// period is null with selected agent
			if (StringUtils.equalsIgnoreCase(period,"null")) {
				             
				 if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
					 
					 
					 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
		            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
		            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount desc) as RANK from " + 
			                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
			                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
			                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount desc)" + 
			                	  " ) t where RANK = 1 ) A"
		            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
		            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\"  ASC ";
	            	 
	            	 
			    	   
				  }
				 
				 else  if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
					 
					 agentSalesCount ="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
		            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
		            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount ASC) as RANK from " + 
			                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
			                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
			                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount ASC)" + 
			                	  " ) t where RANK = 1 ) A"
		            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
		            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN ) ORDER BY \"fullName\"  ASC ";
	            	 				          	
				  }
				 else if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
				   
					 agentSalesCount="SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
		            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
		            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount desc) as RANK from " + 
			                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
			                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
			                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount desc)" + 
			                	  " ) t where RANK = 1 ) A"
		            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
		            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN )"
             			  + " UNION ( "
             			  +"SELECT agent_id as \"agentId\",full_name as \"fullName\",msisdn as \"msisdn\", '"+startDate+"' as \"startDate\", '"+endDate+"' as \"endDate\",region_name as \"regionName\",totalCount as \"totalCount\" ,coalesce(successCount,0) as \"successCount\",coalesce(failedCount,0) as \"failedCount\" FROM ( (SELECT A.agent_id as agent_id , A.full_name as full_name ,A.msisdn as msisdn ,A.region_name as region_name,A.totalCount as totalCount, coalesce(B.successCount,0) as successCount "
		            	 		+ " FROM( SELECT t.agent_id as agent_id, t.FULL_NAME as full_name, t.MSISDN as msisdn, t.region_name as region_name, t.totalSalesCount as totalCount FROM( "
		            	 		+" select agent_id,FULL_NAME,MSISDN ,region_name,totalSalesCount, RANK() over( order by totalSalesCount ASC) as RANK from " + 
			                	  "(SELECT DISTINCT a.agent_id as agent_id,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, a.region_name as region_name,COUNT(a.msisdn) as totalSalesCount " + 
			                	  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' "+
			                	  "GROUP BY a.agent_id,a.full_name,a.msisdn,a.region_name order by totalSalesCount ASC)" + 
			                	  " ) t where RANK = 1 ) A"
		            	 		+ " LEFT JOIN ( SELECT a.MSISDN as secondMSISDN,coalesce(count(*),0) as successCount from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN ) B ON A.msisdn = B.secondMSISDN )D LEFT JOIN( "
		            	 		+ " SELECT coalesce(count(*),0) as failedCount,a.MSISDN as secondMSISDN from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and a.REGION_NAME LIKE '%"+region+"%' and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Failed' and a.full_name IN ("+finalAgentsNameList+") GROUP BY a.MSISDN )C ON D.msisdn = C.secondMSISDN )"
             			  + ") ORDER BY \"fullName\" DESC , \"totalCount\" DESC";
				 }
				 agentSalesCountQuery =session.createSQLQuery(agentSalesCount);
	             agentSalesCountQuery.setParameter("param1", startDate);
	             agentSalesCountQuery.setParameter("param2", endDate);

				  @SuppressWarnings("unchecked")
	           		List<SimAgentSales> agentReportResults = (List<SimAgentSales>) ((SQLQuery) agentSalesCountQuery)
	           		.addScalar("agentId").addScalar("fullName").addScalar("msisdn").addScalar("startDate")
	           		.addScalar("endDate").addScalar("regionName").addScalar("totalCount").addScalar("successCount").addScalar("failedCount")
	           		.setResultTransformer(Transformers.aliasToBean(SimAgentSales.class)).list();
				 
				  rtn.put("agentsSalesCountList", agentReportResults); 
			    	
			}// end of null period condition
						 
			     } // end else of selected agent

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
				}
		   return rtn;   
		}
		
		// Charts
		@SuppressWarnings("unchecked")
		@RequestMapping(value ="/GenerateCountCharts", method = RequestMethod.GET)
		@ResponseBody public Map<String, Object> GenerateCountCharts(Locale locale, Model model,
		   HttpServletRequest request, HttpServletResponse response)  { 
		   //logger.info("Welcome home! The client locale is {}.", locale);

		   Map<String, Object> rtn = new LinkedHashMap<>(); 
		   ObjectMapper mapper = new ObjectMapper();
		   String period = request.getParameter("period");
		   String max = request.getParameter("Max");
		   String min = request.getParameter("Min");
		   String region = request.getParameter("region");
		   String start_Date = request.getParameter("startDate");
		   String end_Date = request.getParameter("endDate");
		   DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		   Date startdate = null;
		   Date enddate = null;
		   Timestamp startDate;
		   Timestamp endDate;	
						
		   String agentsName = request.getParameter("allAgentsName");
		   String finalAgentsNameList = "";
		   String[] agentsNameList = null;
		   
		   if (StringUtils.equalsIgnoreCase(agentsName,"") || StringUtils.equalsIgnoreCase(agentsName,null)) {
			   finalAgentsNameList ="";
	        }
		   else {
				  String allAgentsName = agentsName.concat(",");
				  //Split the string of sites on space-comma to get each site separately
				  agentsNameList = allAgentsName.split("  ,");
				  StringBuilder allAgentsNames = new StringBuilder();
				 //Append to each element '' and comma to satisfy the format of IN parameters
				   for(int i=0;i<agentsNameList.length;i++){  
					   allAgentsNames.append( "'"+agentsNameList[i]+"'," );
					  }
				   finalAgentsNameList = allAgentsNames.toString();
				   finalAgentsNameList = finalAgentsNameList.substring(0, finalAgentsNameList.length()-1);
				   
		   }
			String periodicDataChart="",agentSalesCountChart="",registrationStatus="",maxStackedLineChartCount="",minStackedLineChartCount="";
		    Query agentSalesCountQuery,periodicDataQuery,registrationStatusQuery,maxStackedLineChartQuery,minStackedLineChartQuery;
		    
		   
			List<Object> defPeriodicData = new ArrayList<Object>();
		
				
			List<SimAgentCountChartsReport> salesCountLineCharts = new ArrayList<SimAgentCountChartsReport>();
			List<SimAgentCountChartsReport> countLineChartList = new ArrayList<SimAgentCountChartsReport>();
			List<SimAgentCountChartsReport> maxSalesStackedLineCharts = new ArrayList<SimAgentCountChartsReport>();
			List<SimAgentCountChartsReport> minSalesStackedLineCharts = new ArrayList<SimAgentCountChartsReport>();
			List<SimAgentCountChartsReport> maxCountStackedLineChartList = new ArrayList<SimAgentCountChartsReport>();
			List<SimAgentCountChartsReport> minCountStackedLineChartList = new ArrayList<SimAgentCountChartsReport>();
			
			List<Object> maxdefPeriodicData = new ArrayList<Object>();
			List<Object> mindefPeriodicData = new ArrayList<Object>();
		        			
		
			ArrayList<Float> chartArray;
			ArrayList<Float> chartArrayRegistrationStatus;
			JSONArray chartJSNArr;
		   		
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", "redirect:/");
				return rtn;
				
			}
		 else {
			  Session session = null;
			  Transaction tx = null;	

		   try {
			    startdate = formatter.parse(start_Date);
				startDate = new Timestamp(startdate.getTime());
				enddate = formatter.parse(end_Date);
				endDate = new Timestamp(enddate.getTime());
				
			    session = almsessions.getSession();
			    
			    if (session != null && session.isOpen()) {
			    	tx = session.beginTransaction();
			   
			    	// if agent is not selected
			    	if(finalAgentsNameList == "") {
					
							 // period is daily with null agent
							if (StringUtils.equalsIgnoreCase(period,"Daily")) {
								
								//Null Max- Null Min 
					            if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
					            	agentSalesCountChart ="select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name order by a.full_name ";
										
									periodicDataChart = "select * from(select TO_CHAR(b.created_date, 'YYYY/MM/DD') as created_date,  count(*) as totalSimSales from agent a inner join clients b on a.msisdn = b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED'  and a.REGION_NAME LIKE '%"+region+"%' group by TO_CHAR(created_date, 'YYYY/MM/DD')) order by created_date DESC";
										
									registrationStatus= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 group by ussd_status";
										
								
									agentSalesCountQuery = session.createSQLQuery(agentSalesCountChart);
									agentSalesCountQuery.setParameter("param1", startDate);
									agentSalesCountQuery.setParameter("param2", endDate);
									
									periodicDataQuery = session.createSQLQuery(periodicDataChart);
									periodicDataQuery.setParameter("param1", startDate);
									periodicDataQuery.setParameter("param2", endDate);
									
									 
									 registrationStatusQuery = session.createSQLQuery(registrationStatus);
									 registrationStatusQuery.setParameter("param1", startDate);
									 registrationStatusQuery.setParameter("param2", endDate);
									
									 if (agentSalesCountQuery.list().size()>0) {
										 chartJSNArr = chartSales(agentSalesCountQuery.list());
										 rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));	
									 }
									 else{
										 rtn.put("msColumnChartObj", mapper.writeValueAsString(""));						
									 }
									 
									 if (periodicDataQuery.list().size()>0 ) {
										 defPeriodicData = getStackedChartData(periodicDataQuery.list());
										 rtn.put("StackedandLineCount", mapper.writeValueAsString(defPeriodicData));
									 }
									 else{
										 rtn.put("StackedandLineCount", mapper.writeValueAsString(""));
									 }
										
									
								  chartArray = new ArrayList<Float>(PieSalesPerAgent(agentSalesCountQuery.list()));	
								  rtn.put("chartData", mapper.writeValueAsString(chartArray));
									
								  chartArrayRegistrationStatus = new ArrayList<Float>(PieStatusPerClient(registrationStatusQuery.list()));	
								  rtn.put("regStatusList", mapper.writeValueAsString(chartArrayRegistrationStatus)); 
																										
							   } // end null max-null min condition 
					             
					         //MAX OR MIN ARE CHECKED
					         else {
					        	 
					        // Daily-Max-Min condition
						    if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
						    	
						    	maxStackedLineChartCount=  "SELECT created_date,sum(salesCount) from( select t.created_date AS created_date ,t.salesCount AS salesCount from ( " + 
					                	  "select salesCount,created_date,full_name, RANK() over(PARTITION BY created_date order by salesCount desc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
						                  "GROUP BY TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.full_name order by salesCount desc)" + 
						                  ") t where RANK = 1) group by created_date order by created_date desc";	   			            	 	 				        	 		
						     	
						    	minStackedLineChartCount=  "SELECT created_date,sum(salesCount) from( select t.created_date AS created_date ,t.salesCount AS salesCount from ( " + 
					                	  "select salesCount,created_date,full_name, RANK() over(PARTITION BY created_date order by salesCount asc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
						                  "GROUP BY TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.full_name order by salesCount asc)" + 
						                  ") t where RANK = 1) group by created_date order by created_date desc";	   			            	 	 				        	 		
				            	
						            	          	 						            	 
						    /*   maxStackedLineChartCount = "select created_date,MAX(dailySalesCount) from(SELECT DISTINCT a.agent_id as agent_id ,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as dailySalesCount FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  " + 
						           " GROUP BY  TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.FULL_NAME ,a.MSISDN order by TO_CHAR(b.created_date, 'YYYY-MM-DD') ASC ) GROUP BY created_date ORDER BY created_date DESC " ;
						            	 		
						       minStackedLineChartCount = "select created_date,MIN(dailySalesCount) from(SELECT DISTINCT a.agent_id as agent_id ,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as dailySalesCount FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' " + 
							       " GROUP BY  TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.FULL_NAME ,a.MSISDN order by TO_CHAR(b.created_date, 'YYYY-MM-DD') ASC ) GROUP BY created_date ORDER BY created_date DESC " ;
							 */  
						       maxStackedLineChartQuery = session.createSQLQuery(maxStackedLineChartCount);
						       maxStackedLineChartQuery.setParameter("param1", startDate);
						       maxStackedLineChartQuery.setParameter("param2", endDate);
											
						       minStackedLineChartQuery = session.createSQLQuery(minStackedLineChartCount);
							   minStackedLineChartQuery.setParameter("param1", startDate);
							   minStackedLineChartQuery.setParameter("param2", endDate);
																										
								if (maxStackedLineChartQuery.list().size()>0 ) {
									maxdefPeriodicData = getStackedChartData(maxStackedLineChartQuery.list());
									rtn.put("maxStackedandLineCount", mapper.writeValueAsString(maxdefPeriodicData));
								}
								else {
									rtn.put("maxStackedandLineCount", mapper.writeValueAsString(""));
								}
								
								if (minStackedLineChartQuery.list().size()>0 ) {
									mindefPeriodicData = getStackedChartData(minStackedLineChartQuery.list());
									rtn.put("minStackedandLineCount", mapper.writeValueAsString(mindefPeriodicData));
								}
								else {
									rtn.put("minStackedandLineCount", mapper.writeValueAsString(""));
								}	
											 
								rtn.put("regStatusList", mapper.writeValueAsString(""));
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));	
								rtn.put("chartData", mapper.writeValueAsString(""));


						  }// end of Daily-Max-Min condition
						    
						  // Daily-Max OR Daily-Min condition
						  else {
							  // Daily-Max condition
							  if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
					            
								  periodicDataChart=  "SELECT created_date,sum(salesCount) from( select t.created_date AS created_date ,t.salesCount AS salesCount from ( " + 
					                	  "select salesCount,created_date,full_name, RANK() over(PARTITION BY created_date order by salesCount desc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
						                  "GROUP BY TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.full_name order by salesCount desc)" + 
						                  ") t where RANK = 1) group by created_date order by created_date desc";	   			            	 	 				        	 		
				            	
								//  periodicDataChart = "select created_date as created_date,MAX(dailySalesCount) AS maxCount from(SELECT DISTINCT a.agent_id as agent_id ,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as dailySalesCount FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  " + 
						            //	" GROUP BY  TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.FULL_NAME ,a.MSISDN order by TO_CHAR(b.created_date, 'YYYY-MM-DD') ASC ) GROUP BY created_date ORDER BY created_date DESC " ;
						        	                 	
					            }
							  // Daily-Min condition
							  else if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
					            	
								  periodicDataChart=  "SELECT created_date,sum(salesCount) from( select t.created_date AS created_date ,t.salesCount AS salesCount from ( " + 
					                	  "select salesCount,created_date,full_name, RANK() over(PARTITION BY created_date order by salesCount asc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
						                  "GROUP BY TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.full_name order by salesCount asc)" + 
						                  ") t where RANK = 1) group by created_date order by created_date desc";	   			            	 	 				        	 		
				            	
								 // periodicDataChart = "select created_date,MIN(dailySalesCount) from(SELECT DISTINCT a.agent_id as agent_id ,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as dailySalesCount FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  " + 
						          // " GROUP BY  TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.FULL_NAME ,a.MSISDN order by TO_CHAR(b.created_date, 'YYYY-MM-DD') ASC ) GROUP BY created_date ORDER BY created_date DESC " ;
						        	                	
					           }
							  
							  periodicDataQuery = session.createSQLQuery(periodicDataChart);
							  periodicDataQuery.setParameter("param1", startDate);
							  periodicDataQuery.setParameter("param2", endDate);
								 
							if (periodicDataQuery.list().size()>0 ) {
								defPeriodicData = getStackedChartData(periodicDataQuery.list());
								rtn.put("StackedandLineCount", mapper.writeValueAsString(defPeriodicData));
							}
							else {
								rtn.put("StackedandLineCount", mapper.writeValueAsString(""));
							}
								
							rtn.put("chartData", mapper.writeValueAsString(""));
							rtn.put("regStatusList", mapper.writeValueAsString(""));
							rtn.put("msColumnChartObj", mapper.writeValueAsString(""));						

						}
						    
					} // end else max or min checked	
					             
				} // end of daily condition with null agent
					 
					 // accumulated period
					 if (StringUtils.equalsIgnoreCase(period,"Accu")) {
						 if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
							 
				            	agentSalesCountChart ="select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name order by a.full_name ";
																	
								registrationStatus= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 group by ussd_status";
									
							
								agentSalesCountQuery = session.createSQLQuery(agentSalesCountChart);
								agentSalesCountQuery.setParameter("param1", startDate);
								agentSalesCountQuery.setParameter("param2", endDate);
																						 
								 registrationStatusQuery = session.createSQLQuery(registrationStatus);
								 registrationStatusQuery.setParameter("param1", startDate);
								 registrationStatusQuery.setParameter("param2", endDate);
								
								 if (agentSalesCountQuery.list().size()>0) {
									 chartJSNArr = chartSales(agentSalesCountQuery.list());
									 rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));	
								 }
								 else{
									 rtn.put("msColumnChartObj", mapper.writeValueAsString(""));						
								 }
								 
								 
							    chartArray = new ArrayList<Float>(PieSalesPerAgent(agentSalesCountQuery.list()));	
							    rtn.put("chartData", mapper.writeValueAsString(chartArray));
								
								chartArrayRegistrationStatus = new ArrayList<Float>(PieStatusPerClient(registrationStatusQuery.list()));	
								rtn.put("regStatusList", mapper.writeValueAsString(chartArrayRegistrationStatus)); 
										
								 
								rtn.put("StackedandLineCount", mapper.writeValueAsString(""));	
								rtn.put("maxStackedandLineCount", mapper.writeValueAsString(""));
								rtn.put("minStackedandLineCount", mapper.writeValueAsString(""));
						 }
						 else {							 
							rtn.put("chartData", mapper.writeValueAsString(""));
							rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
							rtn.put("StackedandLineCount", mapper.writeValueAsString(""));	
							rtn.put("regStatusList", mapper.writeValueAsString(""));
							rtn.put("maxStackedandLineCount", mapper.writeValueAsString(""));
							rtn.put("minStackedandLineCount", mapper.writeValueAsString(""));

							 }
					 } // end accu period

					// weekly period with null agent 
						if (StringUtils.equalsIgnoreCase(period, "Weekly")) {
							double diff =(endDate.getTime() - startDate.getTime()) / 1000.0;
					   	 	diff /= (60 * 60 * 24 * 7);
					   	 	diff =  Math.abs(Math.ceil(diff));
							
					   	 	String date = "";
					   	 	Date weeklyDate;
						 	Timestamp weeklyEndDate = startDate;
						 	Calendar cal = Calendar.getInstance();
						 	
						 	if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
						 		agentSalesCountChart ="select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name order by a.full_name ";
										
						 		periodicDataChart = "select t.totalCount as \"totalCount\",'"+date+"' as \"Date\" from ( SELECT count(a.msisdn) as totalCount from agent a inner join clients b on a.msisdn = b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' )t ";
											
						 		registrationStatus= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where  b.created_date >= :param1 AND b.created_date <= :param2 group by ussd_status";					 	    	   
						 	       
						 		agentSalesCountQuery = session.createSQLQuery(agentSalesCountChart);
								agentSalesCountQuery.setParameter("param1", startDate);
								agentSalesCountQuery.setParameter("param2", endDate);
									
								registrationStatusQuery = session.createSQLQuery(registrationStatus);
								registrationStatusQuery.setParameter("param1", startDate);
								registrationStatusQuery.setParameter("param2", endDate);
								
								if (agentSalesCountQuery.list().size()>0) {
									chartJSNArr = chartSales(agentSalesCountQuery.list());
									rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));	
								}
								else{
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));						
								}
								
								chartArray = new ArrayList<Float>(PieSalesPerAgent(agentSalesCountQuery.list()));	
								rtn.put("chartData", mapper.writeValueAsString(chartArray));
									 
								chartArrayRegistrationStatus = new ArrayList<Float>(PieStatusPerClient(registrationStatusQuery.list()));	
								rtn.put("regStatusList", mapper.writeValueAsString(chartArrayRegistrationStatus)); 
									
								Timestamp weeklyStartDate = startDate;
								for( int i = 0; i< diff; i++) {
									weeklyEndDate = weeklyStartDate;
									cal.setTime(weeklyEndDate); 
									cal.add(Calendar.DAY_OF_MONTH, 6); // 6 is the days to add 
									weeklyDate = cal.getTime();
								 	weeklyEndDate = new Timestamp(weeklyDate.getTime());
								 	
								 	if ((weeklyEndDate).compareTo(endDate) > 0) {
								 		weeklyEndDate = endDate;
								 	}
								 	
								 	date =   (weeklyStartDate.getYear()+1900)+"/"+ (weeklyStartDate.getMonth() +1)+"/"+(weeklyStartDate.getDate())+"-"+  (weeklyEndDate.getYear()+1900)+"/"+ (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getDate());
								 	
								 	periodicDataQuery = session.createSQLQuery(periodicDataChart);
									periodicDataQuery.setParameter("param1", weeklyStartDate);
									periodicDataQuery.setParameter("param2", weeklyEndDate);
								
									salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) periodicDataQuery)
							         	.addScalar("totalCount").addScalar("Date")
							         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
										
									salesCountLineCharts.get(0).setDate(date);		
									
									countLineChartList.addAll(salesCountLineCharts);
									weeklyStartDate = weeklyEndDate;
									cal.setTime(weeklyStartDate);
									cal.add(Calendar.DAY_OF_MONTH, 1); // 1 is the day to add
									weeklyDate = cal.getTime();
									weeklyStartDate = new Timestamp(weeklyDate.getTime());
									
								}
															
								defPeriodicData = getPeriodicStackedChartData(countLineChartList);
								rtn.put("StackedandLineCount",  mapper.writeValueAsString(defPeriodicData));
								}
						 	
						 	// Weekly with MAX-MIN
						 	else {
						 		// Weekly-MAX-MIN
								if(StringUtils.equalsIgnoreCase(max,"Max") || StringUtils.equalsIgnoreCase(min,"Min")) {
								            	          	 						            	 
									
									maxStackedLineChartCount=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
						                	  " select salesCount,full_name, RANK() over(order by salesCount desc) as RANK from " + 
							                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
							                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
							                  "GROUP BY a.full_name order by salesCount desc)" + 
							                  ") t where RANK = 1 order by '"+date+"' desc";	   			            	 	 				        	 		
					            	
									minStackedLineChartCount=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
						                	  " select salesCount,full_name, RANK() over(order by salesCount asc) as RANK from " + 
							                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
							                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
							                  "GROUP BY a.full_name order by salesCount asc)" + 
							                  ") t where RANK = 1 order by '"+date+"' desc";	   			            	 	 				        	 		
					            	
									Timestamp weeklyStartDate = startDate;
									for( int i = 0; i< diff; i++) {
										weeklyEndDate = weeklyStartDate;
										cal.setTime(weeklyEndDate); 
										cal.add(Calendar.DAY_OF_MONTH, 6); // 6 is the days to add 
										weeklyDate = cal.getTime();
									 	weeklyEndDate = new Timestamp(weeklyDate.getTime());
									 	
									 	if ((weeklyEndDate).compareTo(endDate) > 0) {
									 		weeklyEndDate = endDate;
									 	}
									 	
									 	date =   (weeklyStartDate.getYear()+1900)+"/"+ (weeklyStartDate.getMonth() +1)+"/"+(weeklyStartDate.getDate())+"-"+  (weeklyEndDate.getYear()+1900)+"/"+ (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getDate());
									 	
									 	maxStackedLineChartQuery = session.createSQLQuery(maxStackedLineChartCount);
									 	maxStackedLineChartQuery.setParameter("param1", weeklyStartDate);
									 	maxStackedLineChartQuery.setParameter("param2", weeklyEndDate);
									 	
									 	minStackedLineChartQuery = session.createSQLQuery(minStackedLineChartCount);
									 	minStackedLineChartQuery.setParameter("param1", weeklyStartDate);
									 	minStackedLineChartQuery.setParameter("param2", weeklyEndDate);
									
									
									 	maxSalesStackedLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) maxStackedLineChartQuery)
								         	.addScalar("totalCount").addScalar("Date")
								         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
											
									 	maxSalesStackedLineCharts.get(0).setDate(date);		
									 	
									 	
									 	minSalesStackedLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) minStackedLineChartQuery)
									         	.addScalar("totalCount").addScalar("Date")
									         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
												
										minSalesStackedLineCharts.get(0).setDate(date);	
									
										
										
										maxCountStackedLineChartList.addAll(maxSalesStackedLineCharts);
										minCountStackedLineChartList.addAll(minSalesStackedLineCharts);
										
										weeklyStartDate = weeklyEndDate;
										cal.setTime(weeklyStartDate);
										cal.add(Calendar.DAY_OF_MONTH, 1); // 1 is the day to add
										weeklyDate = cal.getTime();
										weeklyStartDate = new Timestamp(weeklyDate.getTime());
										
									}
									
									maxdefPeriodicData = getPeriodicStackedChartData(maxCountStackedLineChartList);
									mindefPeriodicData = getPeriodicStackedChartData(minCountStackedLineChartList);
									
									if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
										rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(maxdefPeriodicData));
										rtn.put("minStackedandLineCount",  mapper.writeValueAsString(mindefPeriodicData));
									}
									else if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
										rtn.put("StackedandLineCount",  mapper.writeValueAsString(maxdefPeriodicData));
									}
									else if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
										rtn.put("StackedandLineCount",  mapper.writeValueAsString(mindefPeriodicData));
									}
									
									}
							rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
							rtn.put("chartData", mapper.writeValueAsString(""));
							rtn.put("regStatusList", mapper.writeValueAsString(""));

						}// end of else weekly-MAX-MIN condition
							    
						}// end weekly period		
						
						
						// monthly period with null agent 
						if (StringUtils.equalsIgnoreCase(period, "Monthly")) {

							Calendar m =Calendar.getInstance();
							 m.setTime(startDate);
							 int nMonth1=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
							 m.setTime(endDate);
							 int nMonth2=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
							 int diff = Math.abs(nMonth2-nMonth1);		   	 	         

							 Timestamp month = startDate;
			   	 	         String date = "";
							
						 	if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
						 	   agentSalesCountChart ="select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name order by a.full_name ";
										
						       periodicDataChart = "select t.totalCount as \"totalCount\",'"+date+"' as \"Date\" from ( SELECT count(a.msisdn) as totalCount from agent a inner join clients b on a.msisdn = b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' )t ";
											
							   registrationStatus= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 group by ussd_status";					 	    	   
								   
						 	       
						 	        agentSalesCountQuery = session.createSQLQuery(agentSalesCountChart);
									agentSalesCountQuery.setParameter("param1", startDate);
									agentSalesCountQuery.setParameter("param2", endDate);
									
									registrationStatusQuery = session.createSQLQuery(registrationStatus);
									registrationStatusQuery.setParameter("param1", startDate);
									registrationStatusQuery.setParameter("param2", endDate);
									
									
									 if (agentSalesCountQuery.list().size()>0) {
										chartJSNArr = chartSales(agentSalesCountQuery.list());
										rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));	
									 }
									 else{
										 rtn.put("msColumnChartObj", mapper.writeValueAsString(""));						
									 }		
									 
									 chartArray = new ArrayList<Float>(PieSalesPerAgent(agentSalesCountQuery.list()));	
									 rtn.put("chartData", mapper.writeValueAsString(chartArray));
									
									 chartArrayRegistrationStatus = new ArrayList<Float>(PieStatusPerClient(registrationStatusQuery.list()));	
									 rtn.put("regStatusList", mapper.writeValueAsString(chartArrayRegistrationStatus)); 
									
									 for( int i = 0; i<=diff; i++) {
										
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
											
											
											if ((endOfMonth).compareTo(endDate) > 0) {
												endOfMonth =  endDate;
												
											}
										//date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+ (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+ (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);

									   date = (monthofDate.getYear()+1900) +"/"+ (monthofDate.getMonth() +1) +"/"+ (monthofDate.getDate())+"-" + (monthofDate.getYear()+1900)+"/"+ (endOfMonth.getMonth() +1) +"/"+(endOfMonth.getDate());

								 		periodicDataQuery = session.createSQLQuery(periodicDataChart);
										periodicDataQuery.setParameter("param1", monthofDate);
										periodicDataQuery.setParameter("param2", endOfMonth);
										
										salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) periodicDataQuery)
							         	.addScalar("totalCount").addScalar("Date")
							         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
										
										salesCountLineCharts.get(0).setDate(date);		

										
										countLineChartList.addAll(salesCountLineCharts);
										
										
										calendar.setTime(endOfMonth);  
							    	    calendar.add(Calendar.DATE, +1);  
							    	    Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
						                month = NextDayOfMonth;

							}

									 defPeriodicData = getPeriodicStackedChartData(countLineChartList);

								   rtn.put("StackedandLineCount",  mapper.writeValueAsString(defPeriodicData));

						}
						// Monthly with MAX-MIN
						else {
							// Monthly-MAX-MIN
							if(StringUtils.equalsIgnoreCase(max,"Max") || StringUtils.equalsIgnoreCase(min,"Min")) {
								
								maxStackedLineChartCount=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
					                	  " select salesCount,full_name, RANK() over(order by salesCount desc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
						                  "GROUP BY a.full_name order by salesCount desc)" + 
						                  ") t where RANK = 1 order by '"+date+"' desc";	   			            	 	 				        	 		
				            	
								minStackedLineChartCount=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
					                	  " select salesCount,full_name, RANK() over(order by salesCount asc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
						                  "GROUP BY a.full_name order by salesCount asc)" + 
						                  ") t where RANK = 1 order by '"+date+"' desc";	   	
							
								 for( int i = 0; i<=diff; i++) {
										
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
										
										
										if ((endOfMonth).compareTo(endDate) > 0) {
											endOfMonth =  endDate;
											
										}
								   date = (monthofDate.getYear()+1900) +"/"+ (monthofDate.getMonth() +1) +"/"+ (monthofDate.getDate())+"-" + (monthofDate.getYear()+1900)+"/"+ (endOfMonth.getMonth() +1) +"/"+(endOfMonth.getDate());

								   	maxStackedLineChartQuery = session.createSQLQuery(maxStackedLineChartCount);
								 	maxStackedLineChartQuery.setParameter("param1", monthofDate);
								 	maxStackedLineChartQuery.setParameter("param2", endOfMonth);
								 	
								 	minStackedLineChartQuery = session.createSQLQuery(minStackedLineChartCount);
								 	minStackedLineChartQuery.setParameter("param1", monthofDate);
								 	minStackedLineChartQuery.setParameter("param2", endOfMonth);
								 	
									maxSalesStackedLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) maxStackedLineChartQuery)
								         	.addScalar("totalCount").addScalar("Date")
								         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
											
									 maxSalesStackedLineCharts.get(0).setDate(date);		
									 	
									 	
									 minSalesStackedLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) minStackedLineChartQuery)
									         	.addScalar("totalCount").addScalar("Date")
									         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
												
									minSalesStackedLineCharts.get(0).setDate(date);	
									
									maxCountStackedLineChartList.addAll(maxSalesStackedLineCharts);
									minCountStackedLineChartList.addAll(minSalesStackedLineCharts);
										
									calendar.setTime(endOfMonth);  
						    	    calendar.add(Calendar.DATE, +1);  
						    	    Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
					                month = NextDayOfMonth;

						} // end diff loop
								 
						
						maxdefPeriodicData = getPeriodicStackedChartData(maxCountStackedLineChartList);
						mindefPeriodicData = getPeriodicStackedChartData(minCountStackedLineChartList);
									
						if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
							rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(maxdefPeriodicData));
							rtn.put("minStackedandLineCount",  mapper.writeValueAsString(mindefPeriodicData));
						}
						else if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
							rtn.put("StackedandLineCount",  mapper.writeValueAsString(maxdefPeriodicData));
						}
						else if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
							rtn.put("StackedandLineCount",  mapper.writeValueAsString(mindefPeriodicData));
						}
						
					} // end if max or min condition 
							rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
							rtn.put("chartData", mapper.writeValueAsString(""));
							rtn.put("regStatusList", mapper.writeValueAsString(""));

					}// end of else monthly-MAX-MIN condition
						 	
						}// end monthly period		
						
						// Null period
						 if (StringUtils.equalsIgnoreCase(period,"null")) {
							
								rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(""));
								rtn.put("minStackedandLineCount",  mapper.writeValueAsString(""));
								rtn.put("chartData", mapper.writeValueAsString(""));
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("StackedandLineCount", mapper.writeValueAsString(""));	
								rtn.put("regStatusList", mapper.writeValueAsString(""));

								 
						 } // end null period
						
					
				 }// end null agent condition
		    	 
		    	// if AGENT is selected	 
			     else {
			    	//Daily with selected agent
					 if (StringUtils.equalsIgnoreCase(period,"Daily")) {						 
						 
						 //Daily Null Maximum - Null Minimum with selected agent 
			             if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
			            	 
			            	 agentSalesCountChart ="select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.full_name IN ("+finalAgentsNameList+" )  and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name order by a.full_name ";
			            	 
			            	 periodicDataChart = " select TO_CHAR(b.created_date, 'YYYY/MM/DD') as created_date,  count(*) as totalSimSales from agent a inner join clients b on a.msisdn = b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED' and a.full_name IN ("+finalAgentsNameList+" ) and a.REGION_NAME LIKE '%"+region+"%' group by TO_CHAR(created_date, 'YYYY/MM/DD') order by created_date desc";
							
			            	 registrationStatus= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%'  group by ussd_status";
							
							 agentSalesCountQuery = session.createSQLQuery(agentSalesCountChart);
							 agentSalesCountQuery.setParameter("param1", startDate);
							 agentSalesCountQuery.setParameter("param2", endDate);
							 
							 periodicDataQuery = session.createSQLQuery(periodicDataChart);
							 periodicDataQuery.setParameter("param1", startDate);
							 periodicDataQuery.setParameter("param2", endDate);
							 
							 
							 registrationStatusQuery = session.createSQLQuery(registrationStatus);
							 registrationStatusQuery.setParameter("param1", startDate);
							 registrationStatusQuery.setParameter("param2", endDate);
							
							 if (agentSalesCountQuery.list().size()>0) {
								 chartJSNArr = chartSales(agentSalesCountQuery.list());							 
								 rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));	
							} 
							 else {
								 rtn.put("msColumnChartObj", mapper.writeValueAsString(""));						
							}
							 
							 if (periodicDataQuery.list().size()>0 ) {
								 defPeriodicData = getStackedChartData(periodicDataQuery.list());
								 rtn.put("StackedandLineCount", mapper.writeValueAsString(defPeriodicData));
							}
							 else {
								 rtn.put("StackedandLineCount", mapper.writeValueAsString(""));
							 }
							 
							
							 chartArray = new ArrayList<Float>(PieSalesPerAgent(agentSalesCountQuery.list()));										
							 rtn.put("chartData", mapper.writeValueAsString(chartArray));
							
							chartArrayRegistrationStatus = new ArrayList<Float>(PieStatusPerClient(registrationStatusQuery.list()));	
							rtn.put("regStatusList", mapper.writeValueAsString(chartArrayRegistrationStatus)); 
							 
			             } // end null max-min condition 
			             
			             // max / min checked
			             else {
			            	 
			            	 // Daily-Max-Min
			            	 if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,"Min")) {
					                        
			            	
			            	 minStackedLineChartCount=  "select t.created_date AS created_date ,sum(t.salesCount) AS salesCount from ( " + 
					                	  "select salesCount,created_date,full_name, RANK() over(PARTITION BY created_date order by salesCount asc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%'  "+
						                  "GROUP BY TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.full_name order by salesCount asc)" + 
						                  ") t where RANK = 1 group by t.created_date order by created_date desc";	   
			            	 
			            		 
			            		 maxStackedLineChartCount=  "select t.created_date AS created_date ,sum(t.salesCount) AS salesCount from ( " + 
					                	  "select salesCount,created_date,full_name, RANK() over(PARTITION BY created_date order by salesCount desc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%'  "+
						                  "GROUP BY TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.full_name order by salesCount desc)" + 
						                  ") t where RANK = 1 group by t.created_date order by created_date desc";	   			            	 	 				        	 		
				            	
							     maxStackedLineChartQuery = session.createSQLQuery(maxStackedLineChartCount);
				            	 maxStackedLineChartQuery.setParameter("param1", startDate);
				            	 maxStackedLineChartQuery.setParameter("param2", endDate);
								
								 minStackedLineChartQuery = session.createSQLQuery(minStackedLineChartCount);
								 minStackedLineChartQuery.setParameter("param1", startDate);
								 minStackedLineChartQuery.setParameter("param2", endDate);
								
				          							
					            if (maxStackedLineChartQuery.list().size()>0 ) {
					            	maxdefPeriodicData = getStackedChartData(maxStackedLineChartQuery.list());
					            	rtn.put("maxStackedandLineCount", mapper.writeValueAsString(maxdefPeriodicData));
					            }
					            else {
					            	rtn.put("maxStackedandLineCount", mapper.writeValueAsString(""));
					            }

					            if (minStackedLineChartQuery.list().size()>0 ) {
					            	mindefPeriodicData = getStackedChartData(minStackedLineChartQuery.list());
					            	rtn.put("minStackedandLineCount", mapper.writeValueAsString(mindefPeriodicData));
					            }
					            else {
					            	rtn.put("minStackedandLineCount", mapper.writeValueAsString(""));
					            }
							
							rtn.put("chartData", mapper.writeValueAsString(""));
							rtn.put("regStatusList", mapper.writeValueAsString(""));
							rtn.put("msColumnChartObj", mapper.writeValueAsString(""));						
	 
			            }// end of Daily-Max-Min condition
			            
			            //max OR min is checked
			            else {
			            	
			            //Daily-Max condition
			             if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
			            	 
			            	
			            	 periodicDataChart=  "SELECT created_date,sum(salesCount) from( select t.created_date AS created_date ,t.salesCount AS salesCount from ( " + 
				                	  "select salesCount,created_date,full_name, RANK() over(PARTITION BY created_date order by salesCount desc) as RANK from " + 
					                  "(SELECT DISTINCT a.FULL_NAME as full_name, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as salesCount  " + 
					                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%'  "+
					                  "GROUP BY TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.full_name order by salesCount desc)" + 
					                  ") t where RANK = 1) group by created_date order by created_date desc";	   			            	 	 				        	 		
			                           	
			             }
			             //Daily-Min condition
			             if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
			            	
			            	 periodicDataChart=  "SELECT created_date,sum(salesCount) from( select t.created_date AS created_date ,t.salesCount AS salesCount from ( " + 
				                	  "select salesCount,created_date,full_name, RANK() over(PARTITION BY created_date order by salesCount asc) as RANK from " + 
					                  "(SELECT DISTINCT a.FULL_NAME as full_name, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as salesCount  " + 
					                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%'  "+
					                  "GROUP BY TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.full_name order by salesCount asc)" + 
					                  ") t where RANK = 1) group by created_date order by created_date desc";	   			            	 	 				        	 		
			            		           		                	
			             }
			            
						 periodicDataQuery = session.createSQLQuery(periodicDataChart);
						 periodicDataQuery.setParameter("param1", startDate);
						 periodicDataQuery.setParameter("param2", endDate);
												 
							if (periodicDataQuery.list().size()>0 ) {
								defPeriodicData = getStackedChartData(periodicDataQuery.list());
								rtn.put("StackedandLineCount", mapper.writeValueAsString(defPeriodicData));
							}else {
								rtn.put("StackedandLineCount", mapper.writeValueAsString(""));
							}
							
							rtn.put("chartData", mapper.writeValueAsString(""));
							rtn.put("regStatusList", mapper.writeValueAsString(""));
							rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
				             
					 } // max/min checked
				
			       }// end else of max OR min is checked
		       } // end daily condition with selected agent
					// Null period
					 if (StringUtils.equalsIgnoreCase(period,"null")) {
						
							rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(""));
							rtn.put("minStackedandLineCount",  mapper.writeValueAsString(""));
							rtn.put("chartData", mapper.writeValueAsString(""));
							rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
							rtn.put("StackedandLineCount", mapper.writeValueAsString(""));	
							rtn.put("regStatusList", mapper.writeValueAsString(""));

							 
					 } // end null period
					 
					 // accumulated period
					 if (StringUtils.equalsIgnoreCase(period,"Accu")) {
						 if(StringUtils.equalsIgnoreCase(max,null) && StringUtils.equalsIgnoreCase(min,null)) {
				            	agentSalesCountChart ="select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name order by a.full_name ";
																	
								registrationStatus= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by ussd_status";
									
							
								agentSalesCountQuery = session.createSQLQuery(agentSalesCountChart);
								agentSalesCountQuery.setParameter("param1", startDate);
								agentSalesCountQuery.setParameter("param2", endDate);
								
								 registrationStatusQuery = session.createSQLQuery(registrationStatus);
								 registrationStatusQuery.setParameter("param1", startDate);
								 registrationStatusQuery.setParameter("param2", endDate);
								
								 if (agentSalesCountQuery.list().size()>0) {
									 chartJSNArr = chartSales(agentSalesCountQuery.list());
									 rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));	
								 }
								 else{
									 rtn.put("msColumnChartObj", mapper.writeValueAsString(""));						
								 }
								 
								 
								 chartArray = new ArrayList<Float>(PieSalesPerAgent(agentSalesCountQuery.list()));	
								 rtn.put("chartData", mapper.writeValueAsString(chartArray));
								
								 
								 chartArrayRegistrationStatus = new ArrayList<Float>(PieStatusPerClient(registrationStatusQuery.list()));	
								 rtn.put("regStatusList", mapper.writeValueAsString(chartArrayRegistrationStatus)); 
										
								
								 rtn.put("StackedandLineCount", mapper.writeValueAsString(""));	

						 }
						 else if (StringUtils.equalsIgnoreCase(min,"Min") || StringUtils.equalsIgnoreCase(max,"Max")) {
							 
							 	rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(""));
								rtn.put("minStackedandLineCount",  mapper.writeValueAsString(""));
								rtn.put("chartData", mapper.writeValueAsString(""));
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("StackedandLineCount", mapper.writeValueAsString(""));	
								rtn.put("regStatusList", mapper.writeValueAsString(""));
 
						 }
						
						 
					 } // end accu period
					 
						// weekly period with selected agent 
						if (StringUtils.equalsIgnoreCase(period, "Weekly")) {
							
					 	   //Weekly-Selected agent - Null Maximum & Null Minimum
					 		if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
					 			
					 			double diff =(endDate.getTime() - startDate.getTime()) / 1000.0;
					   	 	   	diff /= (60 * 60 * 24 * 7);
					   	 		diff =  Math.abs(Math.ceil(diff));
					   	 	 	String date = "";
					   	 	 	Date weeklyDate;
						 	 	Timestamp weeklyEndDate = startDate;
								Timestamp weeklyStartDate = startDate;
						 		Calendar cal = Calendar.getInstance();   
						 		
					 			agentSalesCountChart ="select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name order by a.full_name ";
										
					 			periodicDataChart = "select t.totalCount as \"totalCount\",'"+date+"' as \"Date\" from ( SELECT count(a.msisdn) as totalCount from agent a inner join clients b on a.msisdn = b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' )t ";
											
								registrationStatus= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where  b.created_date >= :param1 AND b.created_date <= :param2  and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by ussd_status";					 	    	   
								   
					 		
					 		 	agentSalesCountQuery = session.createSQLQuery(agentSalesCountChart);
								agentSalesCountQuery.setParameter("param1", startDate);
								agentSalesCountQuery.setParameter("param2", endDate);
								
								registrationStatusQuery = session.createSQLQuery(registrationStatus);
								registrationStatusQuery.setParameter("param1", startDate);
								registrationStatusQuery.setParameter("param2", endDate);
								
								if (agentSalesCountQuery.list().size()>0) {
									 chartJSNArr = chartSales(agentSalesCountQuery.list());
									 rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));	
								 }
								 else{
									 rtn.put("msColumnChartObj", mapper.writeValueAsString(""));						
								 }	
					 		
								chartArray = new ArrayList<Float>(PieSalesPerAgent(agentSalesCountQuery.list()));	
								rtn.put("chartData", mapper.writeValueAsString(chartArray));
								 
								chartArrayRegistrationStatus = new ArrayList<Float>(PieStatusPerClient(registrationStatusQuery.list()));	
								rtn.put("regStatusList", mapper.writeValueAsString(chartArrayRegistrationStatus)); 
									
								 for( int i = 0; i< diff; i++) {
										
									 weeklyEndDate = weeklyStartDate;
 
					 	        	 cal.setTime(weeklyEndDate); 
							 	     cal.add(Calendar.DAY_OF_MONTH, 6); // 6 is the days you want to add or subtract   
							 	     weeklyDate = cal.getTime();
							 	     weeklyEndDate = new Timestamp(weeklyDate.getTime());
									 
							 	   					 	         
							 	    if ((weeklyEndDate).compareTo(endDate) > 0) {
										weeklyEndDate = endDate;
										
									}
								//	date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth() +1)+"/"+ (weeklyStartDate.getYear()+1900)+"-"+ (weeklyEndDate.getDate())+"/"+ (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getYear()+1900);
									date =   (weeklyStartDate.getYear()+1900)+"/"+ (weeklyStartDate.getMonth() +1)+"/"+(weeklyStartDate.getDate())+"-"+  (weeklyEndDate.getYear()+1900)+"/"+ (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getDate());

							 		periodicDataQuery = session.createSQLQuery(periodicDataChart);
									periodicDataQuery.setParameter("param1", weeklyStartDate);
									periodicDataQuery.setParameter("param2", weeklyEndDate);
									
									salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) periodicDataQuery)
						         	.addScalar("totalCount").addScalar("Date")
						         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
									
									salesCountLineCharts.get(0).setDate(date);		

									
									countLineChartList.addAll(salesCountLineCharts);
									weeklyStartDate = weeklyEndDate;
									cal.setTime(weeklyStartDate);
									cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									weeklyStartDate = new Timestamp(weeklyDate.getTime());

						}

								 defPeriodicData = getPeriodicStackedChartData(countLineChartList);

							   rtn.put("StackedandLineCount",  mapper.writeValueAsString(defPeriodicData));
	
									
						}
					 	//Weekly-Selected Agent-Maximum-Minimum
						else if (StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max")) {
							 
							double diff =(endDate.getTime() - startDate.getTime()) / 1000.0;
			   	 	         diff /= (60 * 60 * 24 * 7);
			   	 	         diff =  Math.abs(Math.ceil(diff));
			   	 	         String date = "";
			   	 	         Date weeklyDate;
				 	         Timestamp weeklyEndDate = startDate;
						     Timestamp weeklyStartDate = startDate;
				 	         Calendar cal = Calendar.getInstance();  
				 	         
				 	        for( int i = 0; i< diff; i++) {
								weeklyEndDate = weeklyStartDate;
								cal.setTime(weeklyEndDate); 
							 	cal.add(Calendar.DAY_OF_MONTH, 6);    
							 	weeklyDate = cal.getTime();
							 	weeklyEndDate = new Timestamp(weeklyDate.getTime());
								
							 	if ((weeklyEndDate).compareTo(endDate) > 0) {
							 		weeklyEndDate = endDate;
							 }	 
									
							 	maxStackedLineChartCount = "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
						                	  " select salesCount,full_name, RANK() over(order by salesCount desc) as RANK from " + 
							                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
							                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%'  "+
							                  "GROUP BY a.full_name order by salesCount desc)" + 
							                  ") t where RANK = 1 order by '"+date+"' desc";	   			            	 	 				        	 		
					            		
								
							 	minStackedLineChartCount=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
						                	  " select salesCount,full_name, RANK() over(order by salesCount asc) as RANK from " + 
							                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
							                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%'  "+
							                  "GROUP BY a.full_name order by salesCount asc)" + 
							                  ") t where RANK = 1 order by '"+date+"' desc";	
								
								

								 date =   (weeklyStartDate.getYear()+1900)+"/"+ (weeklyStartDate.getMonth() +1)+"/"+(weeklyStartDate.getDate())+"-"+  (weeklyEndDate.getYear()+1900)+"/"+ (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getDate());
								 	
								 maxStackedLineChartQuery = session.createSQLQuery(maxStackedLineChartCount);
								 maxStackedLineChartQuery.setParameter("param1", weeklyStartDate);
								 maxStackedLineChartQuery.setParameter("param2", weeklyEndDate);
								 
								 minStackedLineChartQuery = session.createSQLQuery(minStackedLineChartCount);
								 minStackedLineChartQuery.setParameter("param1", weeklyStartDate);
								 minStackedLineChartQuery.setParameter("param2", weeklyEndDate);
										
								 maxSalesStackedLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) maxStackedLineChartQuery)
							         	.addScalar("totalCount").addScalar("Date")
							         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
								
								 minSalesStackedLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) minStackedLineChartQuery)
								         	.addScalar("totalCount").addScalar("Date")
								         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
											
								 
								 maxSalesStackedLineCharts.get(0).setDate(date);	
								 minSalesStackedLineCharts.get(0).setDate(date);
									
								 maxCountStackedLineChartList.addAll(maxSalesStackedLineCharts);
								 minCountStackedLineChartList.addAll(minSalesStackedLineCharts);
								 
									weeklyStartDate = weeklyEndDate;
									cal.setTime(weeklyStartDate);
									cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									weeklyStartDate = new Timestamp(weeklyDate.getTime());
									
							 	    }// end diff loop
								
				 	        	

								maxdefPeriodicData = getPeriodicStackedChartData(maxCountStackedLineChartList);
								mindefPeriodicData = getPeriodicStackedChartData(minCountStackedLineChartList);
								rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(maxdefPeriodicData));
								rtn.put("minStackedandLineCount",  mapper.writeValueAsString(mindefPeriodicData));
					 	        	
						 	    rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("chartData", mapper.writeValueAsString(""));
								rtn.put("regStatusList", mapper.writeValueAsString(""));									
								
						     	
						 	        	

					   }// end of else weekly-MAX-MIN condition
					 		
					 		
						//Weekly-Selected Agent-Maximum OR Minimum
						else {
							 
					 				 double diff =(endDate.getTime() - startDate.getTime()) / 1000.0;
					   	 	         diff /= (60 * 60 * 24 * 7);
					   	 	         diff =  Math.abs(Math.ceil(diff));
					   	 	         String date = "";
					   	 	         Date weeklyDate;
						 	         Timestamp weeklyEndDate = startDate;
								     Timestamp weeklyStartDate = startDate;
						 	         Calendar cal = Calendar.getInstance();  
						 	         
						 	        for( int i = 0; i< diff; i++) {
										weeklyEndDate = weeklyStartDate;
										cal.setTime(weeklyEndDate); 
									 	cal.add(Calendar.DAY_OF_MONTH, 6);    
									 	weeklyDate = cal.getTime();
									 	weeklyEndDate = new Timestamp(weeklyDate.getTime());
										
									 	if ((weeklyEndDate).compareTo(endDate) > 0) {
									 		weeklyEndDate = endDate;
									 }
						 	         
							if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null) ) {
								
								periodicDataChart=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
					                	  " select salesCount,full_name, RANK() over(order by salesCount desc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%'  "+
						                  "GROUP BY a.full_name order by salesCount desc)" + 
						                  ") t where RANK = 1 order by '"+date+"' desc";	   			            	 	 				        	 		
				            		
							//	periodicDataChart = "select t.totalCount as \"totalCount\",'"+date+"' as \"Date\" from ( SELECT count(a.msisdn) as totalCount from agent a inner join clients b on a.msisdn = b.agent_number  where TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED'  and a.FULL_NAME ="+agentFullName+" and a.REGION_NAME LIKE '%"+region+"%' )t ";
							
							} 
							
							if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
								
								
								//periodicDataChart = "select t.totalCount as \"totalCount\",'"+date+"' as \"Date\" from ( SELECT count(a.msisdn) as totalCount from agent a inner join clients b on a.msisdn = b.agent_number  where TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED'  and a.FULL_NAME ="+agentFullName+" and a.REGION_NAME LIKE '%"+region+"%' )t ";
								periodicDataChart=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
					                	  " select salesCount,full_name, RANK() over(order by salesCount asc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%'  "+
						                  "GROUP BY a.full_name order by salesCount asc)" + 
						                  ") t where RANK = 1 order by '"+date+"' desc";	   			            	 	 				        	 		
							
							} 
						


							 date =   (weeklyStartDate.getYear()+1900)+"/"+ (weeklyStartDate.getMonth() +1)+"/"+(weeklyStartDate.getDate())+"-"+  (weeklyEndDate.getYear()+1900)+"/"+ (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getDate());
							 	
							 	periodicDataQuery = session.createSQLQuery(periodicDataChart);
								periodicDataQuery.setParameter("param1", weeklyStartDate);
								periodicDataQuery.setParameter("param2", weeklyEndDate);
									
								salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) periodicDataQuery)
						         	.addScalar("totalCount").addScalar("Date")
						         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
									
								salesCountLineCharts.get(0).setDate(date);		
								
								countLineChartList.addAll(salesCountLineCharts);
								weeklyStartDate = weeklyEndDate;
								cal.setTime(weeklyStartDate);
								cal.add(Calendar.DAY_OF_MONTH, 1); 
								weeklyDate = cal.getTime();
								weeklyStartDate = new Timestamp(weeklyDate.getTime());
								
						 	    }// end diff loop
							
							defPeriodicData = getPeriodicStackedChartData(countLineChartList);
							 	  	  
						     	if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
				 	        		rtn.put("StackedandLineCount",  mapper.writeValueAsString(defPeriodicData));
				 	        		
				 	        	}
				 	        	else if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
				 	        		rtn.put("StackedandLineCount",  mapper.writeValueAsString(defPeriodicData));
				 	        		
						 	     }
						     	rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("chartData", mapper.writeValueAsString(""));
								rtn.put("regStatusList", mapper.writeValueAsString(""));									
					 	        	

							}// end of else weekly-MAX-MIN condition
						 	      						 	       
						}// end of weekly period

						// monthly period
						if (StringUtils.equalsIgnoreCase(period, "Monthly")) {
							
						 	if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
						 		
						 		Calendar m =Calendar.getInstance();
								 m.setTime(startDate);
								 int nMonth1=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
								 m.setTime(endDate);
								 int nMonth2=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
								 int diff = Math.abs(nMonth2-nMonth1);
								
				   	 	         Timestamp month = startDate;
				   	 	         String date = "";
				   	 	         
						 	   agentSalesCountChart ="select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name order by a.full_name ";
										
						       periodicDataChart = "select t.totalCount as \"totalCount\",'"+date+"' as \"Date\" from ( SELECT count(a.msisdn) as totalCount from agent a inner join clients b on a.msisdn = b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' )t ";
											
							   registrationStatus= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and a.FULL_NAME IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' group by ussd_status";					 	    	   
								   
						 	       
						 	        agentSalesCountQuery = session.createSQLQuery(agentSalesCountChart);
									agentSalesCountQuery.setParameter("param1", startDate);
									agentSalesCountQuery.setParameter("param2", endDate);
									
									registrationStatusQuery = session.createSQLQuery(registrationStatus);
									registrationStatusQuery.setParameter("param1", startDate);
									registrationStatusQuery.setParameter("param2", endDate);
									
									
									
									 if (agentSalesCountQuery.list().size()>0) {
										 chartJSNArr = chartSales(agentSalesCountQuery.list());
										 rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));	
									 }
									 else{
										 rtn.put("msColumnChartObj", mapper.writeValueAsString(""));						
									 }		
									 
									 chartArray = new ArrayList<Float>(PieSalesPerAgent(agentSalesCountQuery.list()));	
									 rtn.put("chartData", mapper.writeValueAsString(chartArray));
								
									
									 chartArrayRegistrationStatus = new ArrayList<Float>(PieStatusPerClient(registrationStatusQuery.list()));	
									 rtn.put("regStatusList", mapper.writeValueAsString(chartArrayRegistrationStatus)); 
											
									
									
									 for( int i = 0; i<= diff; i++) {
										
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
											
											
											if ((endOfMonth).compareTo(endDate) > 0) {
												endOfMonth =  endDate;
												
											}
											   date = (monthofDate.getYear()+1900) +"/"+ (monthofDate.getMonth() +1) +"/"+ (monthofDate.getDate())+"-" + (monthofDate.getYear()+1900)+"/"+ (endOfMonth.getMonth() +1) +"/"+(endOfMonth.getDate());

											
								 		periodicDataQuery = session.createSQLQuery(periodicDataChart);
										periodicDataQuery.setParameter("param1", monthofDate);
										periodicDataQuery.setParameter("param2", endOfMonth);
										
										salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) periodicDataQuery)
							         	.addScalar("totalCount").addScalar("Date")
							         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
										
										salesCountLineCharts.get(0).setDate(date);		

										
										countLineChartList.addAll(salesCountLineCharts);
										
										
										calendar.setTime(endOfMonth);  
							    	    calendar.add(Calendar.DATE, +1);  
							    	    Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
						                month = NextDayOfMonth;

							}

									 defPeriodicData = getPeriodicStackedChartData(countLineChartList);

								   rtn.put("StackedandLineCount",  mapper.writeValueAsString(defPeriodicData));

						}
						 	
						//Monthly-Selected Agent-Maximum-Minimum
						else if (StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,"Max")) {

								Calendar m =Calendar.getInstance();
								 m.setTime(startDate);
								 int nMonth1=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
								 m.setTime(endDate);
								 int nMonth2=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
								 int diff = Math.abs(nMonth2-nMonth1);
								
				   	 	         Timestamp month = startDate;
				   	 	         String date = "";
				   	 	         
				   	 	     for( int i = 0; i<= diff; i++) {
									
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
									
									
									if ((endOfMonth).compareTo(endDate) > 0) {
										endOfMonth =  endDate;
										
									}
				   	 	         
		
									 	maxStackedLineChartCount = "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
								                	  " select salesCount,full_name, RANK() over(order by salesCount desc) as RANK from " + 
									                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
									                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%'  "+
									                  "GROUP BY a.full_name order by salesCount desc)" + 
									                  ") t where RANK = 1 order by '"+date+"' desc";	   			            	 	 				        	 		
							            		
										
									 	minStackedLineChartCount=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
								                	  " select salesCount,full_name, RANK() over(order by salesCount asc) as RANK from " + 
									                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
									                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%'  "+
									                  "GROUP BY a.full_name order by salesCount asc)" + 
									                  ") t where RANK = 1 order by '"+date+"' desc";	
										
										

										   date = (monthofDate.getYear()+1900) +"/"+ (monthofDate.getMonth() +1) +"/"+ (monthofDate.getDate())+"-" + (monthofDate.getYear()+1900)+"/"+ (endOfMonth.getMonth() +1) +"/"+(endOfMonth.getDate());
										 	
										 maxStackedLineChartQuery = session.createSQLQuery(maxStackedLineChartCount);
										 maxStackedLineChartQuery.setParameter("param1", monthofDate);
										 maxStackedLineChartQuery.setParameter("param2", endOfMonth);
										 
										 minStackedLineChartQuery = session.createSQLQuery(minStackedLineChartCount);
										 minStackedLineChartQuery.setParameter("param1", monthofDate);
										 minStackedLineChartQuery.setParameter("param2", endOfMonth);
												
										 maxSalesStackedLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) maxStackedLineChartQuery)
									         	.addScalar("totalCount").addScalar("Date")
									         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
										
										 minSalesStackedLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) minStackedLineChartQuery)
										         	.addScalar("totalCount").addScalar("Date")
										         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
													
										 
										 maxSalesStackedLineCharts.get(0).setDate(date);	
										 minSalesStackedLineCharts.get(0).setDate(date);
											
										 maxCountStackedLineChartList.addAll(maxSalesStackedLineCharts);
										 minCountStackedLineChartList.addAll(minSalesStackedLineCharts);
										 
										 calendar.setTime(endOfMonth);  
								    	 calendar.add(Calendar.DATE, +1);  
								    	 Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
							             month = NextDayOfMonth;	
									 	}// end diff loop
										

										maxdefPeriodicData = getPeriodicStackedChartData(maxCountStackedLineChartList);
										mindefPeriodicData = getPeriodicStackedChartData(minCountStackedLineChartList);
										rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(maxdefPeriodicData));
										rtn.put("minStackedandLineCount",  mapper.writeValueAsString(mindefPeriodicData));
							 	        	
								 	    rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
										rtn.put("chartData", mapper.writeValueAsString(""));
										rtn.put("regStatusList", mapper.writeValueAsString(""));									
												
							}
						 //Monthly-Selected Agent - Maximum or Minimum
						 else {
							 
									 Calendar m =Calendar.getInstance();
									 m.setTime(startDate);
									 int nMonth1=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
									 m.setTime(endDate);
									 int nMonth2=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
									 int diff = Math.abs(nMonth2-nMonth1);
					   	 	         Timestamp month = startDate;
					   	 	         String date = "";
					   	 	         
					   	 	     for( int i = 0; i<= diff; i++) {
									 
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
										
						    	     if ((endOfMonth).compareTo(endDate) > 0) {
						    	    	 endOfMonth =  endDate;
											
						    	     }
						    	     
						    	     
						    	     if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null) ) {
											
											periodicDataChart=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
									                	  " select salesCount,full_name, RANK() over(order by salesCount desc) as RANK from " + 
										                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
										                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%'  "+
										                  "GROUP BY a.full_name order by salesCount desc)" + 
										                  ") t where RANK = 1 ";	   			            	 	 				        	 													
											} 
											 	         
						    	 	if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
										
										//periodicDataChart = "select t.totalCount as \"totalCount\",'"+date+"' as \"Date\" from ( SELECT count(a.msisdn) as totalCount from agent a inner join clients b on a.msisdn = b.agent_number  where TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED'  and a.FULL_NAME ="+agentFullName+" and a.REGION_NAME LIKE '%"+region+"%' )t ";
										periodicDataChart=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
							                	  " select salesCount,full_name, RANK() over(order by salesCount asc) as RANK from " + 
								                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
								                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.FULL_NAME IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%'  "+
								                  "GROUP BY a.full_name order by salesCount asc)" + 
								                  ") t where RANK = 1 ";	
									
									}  	     
							

		    	    date = (monthofDate.getYear()+1900) +"/"+ (monthofDate.getMonth() +1) +"/"+ (monthofDate.getDate())+"-" + (monthofDate.getYear()+1900)+"/"+ (endOfMonth.getMonth() +1) +"/"+(endOfMonth.getDate());

		    	     	periodicDataQuery = session.createSQLQuery(periodicDataChart);
						periodicDataQuery.setParameter("param1", monthofDate);
						periodicDataQuery.setParameter("param2", endOfMonth);
						
						salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) periodicDataQuery)
					         	.addScalar("totalCount").addScalar("Date")
					         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
							
						salesCountLineCharts.get(0).setDate(date);										
						countLineChartList.addAll(salesCountLineCharts);

						
						calendar.setTime(endOfMonth);  
			    	    calendar.add(Calendar.DATE, +1);  
			    	    Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
		                month = NextDayOfMonth;
		                
					 }// end diff loop
					   	 	 
					   defPeriodicData = getPeriodicStackedChartData(countLineChartList);
								 	  
					   if(StringUtils.equalsIgnoreCase(max,"Max") && StringUtils.equalsIgnoreCase(min,null)) {
		 	        		rtn.put("StackedandLineCount",  mapper.writeValueAsString(defPeriodicData));
		 	        		
		 	        	}
					   else if(StringUtils.equalsIgnoreCase(min,"Min") && StringUtils.equalsIgnoreCase(max,null)) {
						   rtn.put("StackedandLineCount",  mapper.writeValueAsString(defPeriodicData));
						}

		 	        	rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
						rtn.put("chartData", mapper.writeValueAsString(""));
						rtn.put("regStatusList", mapper.writeValueAsString(""));									
					 	        
						 }	
						 	
						
			}// end monthly period	
						
			     }// end selected agent
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
		 }
		   return rtn;
		   
		}
		
		@SuppressWarnings("unchecked")
		@RequestMapping(value ="/GetAllAgentIDAutocomplete", method = RequestMethod.GET)
		@ResponseBody public Map<String, Object>GetAllAgentIDAutocomplete(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) { 
			//logger.info("Welcome home! The client locale is {}.", locale);
			
			Map<String, Object> rtn = new LinkedHashMap<>(); 
		   
		    String agentID = "%" + request.getParameter("AgentID") + "%";
			String areaID = request.getParameter("areaID") ;
			String regionID = request.getParameter("regionID") ;
			Query agentDataQuery = null;
			 
			 if (LoginServices.checkSession(request, response).equals("redirect:/")) {
					rtn.put("Login", "redirect:/");
					return rtn;
				}
			 else {
				 
				Session session = null;
				Transaction tx = null;
			
			
			 try {
					session = almsessions.getSession();
					
					if (session != null && session.isOpen()) {
						tx = session.beginTransaction();			

					if( areaID == "" && regionID == "" ) {
						
					 agentDataQuery = session.createSQLQuery("SELECT  distinct (agent_id),full_name,msisdn FROM agent where UPPER(agent_id) like UPPER(:param1) or UPPER(full_name) like UPPER(:param1) ");
				
					}
					else if (areaID != "" &&  regionID == "" ){
						
					 agentDataQuery = session.createSQLQuery("SELECT * from ( SELECT distinct a.agent_id as agent_id,a.full_name as full_name,a.msisdn as msisdn, b.AREA_ID as AREA_ID FROM agent a inner join AGENT_AREAS b on a.agent_id = b.agent_id where UPPER(a.agent_id) like UPPER(:param1) or UPPER(a.full_name) like UPPER(:param1) ) WHERE AREA_ID ='"+areaID+"' ");
		
				 	
					}
					else if (regionID != "" &&  areaID == "" ) {
						 agentDataQuery = session.createSQLQuery("SELECT * from ( SELECT distinct a.agent_id as agent_id,a.full_name as full_name,a.msisdn as msisdn, b.REGION_ID as REGION_ID FROM agent a inner join AGENT_REGIONS b on a.agent_id = b.agent_id where UPPER(a.agent_id) like UPPER(:param1) or UPPER(a.full_name) like UPPER(:param1) ) WHERE REGION_ID ='"+regionID+"' ");
			
					}
					else if (areaID != "" && regionID != "") {
						 agentDataQuery = session.createSQLQuery("SELECT * from ( SELECT distinct a.agent_id as agent_id,a.full_name as full_name,a.msisdn as msisdn, b.REGION_ID as REGION_ID,c.AREA_ID as AREA_ID FROM agent a inner join AGENT_REGIONS b on a.agent_id = b.agent_id inner join AGENT_AREAS c on  b.agent_id = c.agent_id where UPPER(a.agent_id) like UPPER(:param1) or UPPER(a.full_name) like UPPER(:param1) ) WHERE REGION_ID ='"+regionID+"' and AREA_ID ='"+areaID+"'  ");
			
					}
					agentDataQuery.setParameter("param1", agentID);
					agentDataQuery.setFirstResult(0);
					agentDataQuery.setMaxResults(40);
					
					rtn.put("listAgents", agentDataQuery.list()); 
					
			 }
			 }
			 catch (Exception e) {
				   logger.info("Error at <T>  creating session with the DataBase " +e.getMessage() );

				   }
		    
		    finally {
				   if (session != null && session.isOpen()) {
				   tx.commit();
				   session.close();
				   }
				   }
			 }	  
				  return rtn; 
				 
				 }	 
		
		@SuppressWarnings("unchecked")
		@RequestMapping(value ="/FilteringMethodSIM", method = RequestMethod.GET)
		@ResponseBody public Map<String, Object> FilteringMethodSIM(Locale locale, Model model,
		   HttpServletRequest request, HttpServletResponse response) { 
		   
			
			Map<String, Object> rtn = new LinkedHashMap<>();
			ObjectMapper mapper = new ObjectMapper();
			
			List<Object> periodicData = new ArrayList<Object>();
			JSONArray chartJSNArr;
			List<SimAgentCountChartsReport> salesCountLineCharts = new ArrayList<SimAgentCountChartsReport>();
			List<SimAgentCountChartsReport> countLineChartList = new ArrayList<SimAgentCountChartsReport>();
			List<Object> defPeriodicData = new ArrayList<Object>();
			List<SimAgentCountChartsReport> maxSalesStackedLineCharts = new ArrayList<SimAgentCountChartsReport>();
			List<SimAgentCountChartsReport> minSalesStackedLineCharts = new ArrayList<SimAgentCountChartsReport>();
			List<SimAgentCountChartsReport> maxCountStackedLineChartList = new ArrayList<SimAgentCountChartsReport>();
			List<SimAgentCountChartsReport> minCountStackedLineChartList = new ArrayList<SimAgentCountChartsReport>();
			List<Object> maxdefPeriodicData = new ArrayList<Object>();
			List<Object> mindefPeriodicData = new ArrayList<Object>();
			ArrayList<Float> chartArray;
			
			
			//All agents name from popup
			String agentsName = request.getParameter("allAgentsNameChart");
			String finalAgentsNameList = "";
			String[] agentsNameList = null;
			if (StringUtils.equalsIgnoreCase(agentsName,"") || StringUtils.equalsIgnoreCase(agentsName,null)) {
		        finalAgentsNameList ="";
		     }
			else {
				String allAgentsName = agentsName.concat(",");
				//Split the string of sites on space-comma to get each agent separately
				agentsNameList = allAgentsName.split("  ,");
				StringBuilder allAgentsNames = new StringBuilder();
				//Append to each element '' and comma to satisfy the format of IN parameters
				 for(int x=0;x<agentsNameList.length;x++){
					 allAgentsNames.append( "'"+agentsNameList[x]+"'," );
				}
				 finalAgentsNameList = allAgentsNames.toString();
				 finalAgentsNameList = finalAgentsNameList.substring(0, finalAgentsNameList.length()-1);
				 //  System.out.println("finalAgentsNameList" +finalAgentsNameList);
				 } 
			
			
			String region = request.getParameter("region");
			String period = request.getParameter("period");
			String max = request.getParameter("Max");
			String min = request.getParameter("Min");			
			String countOption = request.getParameter("countOption");
			String selectedValue = request.getParameter("selectedValue");
			String minVal = request.getParameter("minVal");
			String maxVal = request.getParameter("maxVal");
			String filterID = request.getParameter("filterID");
			
			String minMaxDecodeCondition;
			String decodeCondition;
			String dailyDecodeCondition;
			String stackedLineChartCaseCondition="";		
				
			if (StringUtils.equalsIgnoreCase(filterID, "lineChartApplyFilter") || StringUtils.equalsIgnoreCase(filterID, "stackedChartApplyFilter") )  {
					   
				if( minVal ==null && maxVal==null  ) {

					stackedLineChartCaseCondition = " total_count as total_count ";
				   }
				  else if( ((StringUtils.equalsIgnoreCase(minVal, "null")) || minVal==null ) && maxVal != null  ) {
		

					  stackedLineChartCaseCondition = "(CASE WHEN total_count >= '"+maxVal+"' THEN total_count ELSE 0 END) total_count ";
			  }
				else if( minVal !=null && ((StringUtils.equalsIgnoreCase(maxVal, "null")) || maxVal==null ) ) {

					stackedLineChartCaseCondition = "(CASE WHEN total_count >= '"+minVal+"' THEN total_count ELSE 0 END) total_count ";
				}
				
				else if( minVal !=null && maxVal !=null  ) {

					stackedLineChartCaseCondition = "(CASE WHEN total_count >= '"+minVal+"' AND total_count <= '"+maxVal+"' THEN total_count ELSE 0 END) total_count ";
						
					  }
					  
				   }
				   
			
			countOption = "total_count";
			

			if (selectedValue != null) {
				minMaxDecodeCondition = " and " + countOption + " >= DECODE(" + minVal + ",null,0," + minVal + ") AND "
						+ countOption + " <= DECODE('" + maxVal + "','null',(select count(a.agent_id) from agent a inner join clients b on a.msisdn= b.agent_number)," + maxVal + ")";
			
				decodeCondition = " where " + countOption + " >= DECODE(" + minVal + ",null,0," + minVal + ") AND "
						+ countOption + " <= DECODE('" + maxVal + "','null',(select count(a.agent_id) from agent a inner join clients b on a.msisdn= b.agent_number)," + maxVal + ")";
			
				dailyDecodeCondition = " where dailySalesCount  >= DECODE(" + minVal + ",null,0," + minVal + ") AND "
					+ " dailySalesCount <= DECODE('" + maxVal + "','null',(select count(a.agent_id) from agent a inner join clients b on a.msisdn= b.agent_number)," + maxVal + ")";
			
				
			} else {
				minMaxDecodeCondition = "";
				decodeCondition = "";
				dailyDecodeCondition="";
			}
			
			int i = 0;
			
			String applyFilterCount="",maxStackedLineChartCount="",minStackedLineChartCount="";
			Query applyFilterQuery,maxStackedLineChartQuery,minStackedLineChartQuery;
			
	       
			String start_Date = request.getParameter("startDate");
			String end_Date = request.getParameter("endDate");
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			Date createdDate = null;
			Date enddate = null;
			Timestamp startDate;
			Timestamp endDate;
							
				
			String regType = request.getParameter("regType");				
			String regStatusWhereCondition ="";
			
			if(StringUtils.equalsIgnoreCase(regType,"USSD")) {
				
				regStatusWhereCondition = "and ussd_status = 'USSD' ";
			}
			else if(StringUtils.equalsIgnoreCase(regType,"SENT_USSD")) {
				regStatusWhereCondition = "and ussd_status = 'SENT_USSD' ";
			}
			else {
				
				regStatusWhereCondition = "and (ussd_status = 'USSD' OR ussd_status = 'SENT_USSD') ";
			}
			
			
		    
	      		 
	        if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", "redirect:/");
				return rtn;
			}
	        else {	
	 		   Session session = null;
	 		   Transaction tx = null;
	       

		   try { 
			   	createdDate = formatter.parse(start_Date);
				startDate = new Timestamp(createdDate.getTime());
				enddate = formatter.parse(end_Date);
				endDate = new Timestamp(enddate.getTime());
				
			    session = almsessions.getSession();
			    
			    if (session != null && session.isOpen()) {
			    	tx = session.beginTransaction();
			   
			    	if (finalAgentsNameList == "") {
					
						// Daily period and agent is not selected 
						if (StringUtils.equalsIgnoreCase(period, "Daily")) {
				 	        
							// Null Max - Null Min
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
							    switch (filterID) {
							    
							    case "countChartApplyFilter":
							    	applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
					                	" and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
					                	"  group by full_name,total_count order by full_name ";
								
								break;
								
							    case "stackedChartApplyFilter":
									
							    	applyFilterCount = "SELECT * FROM (select TO_CHAR(b.created_date, 'YYYY/MM/DD') as created_date, count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'\r\n" + 
						        " and a.REGION_NAME LIKE '%"+region+"%' group by TO_CHAR(b.created_date, 'YYYY/MM/DD'))" + decodeCondition  + 
						        " group by created_date,total_count order by created_date desc ";
						       
						        break;
						        
							    case "lineChartApplyFilter":
									
							    	applyFilterCount = "SELECT * FROM (select TO_CHAR(b.created_date, 'YYYY/MM/DD') as created_date, count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'\r\n" + 
							        " and a.REGION_NAME LIKE '%"+region+"%' group by TO_CHAR(b.created_date, 'YYYY/MM/DD'))" + decodeCondition  + 
							        " group by created_date,total_count order by created_date desc";
							       
							        break;
							        
							    case "countPieApplyFilter":
							    	applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name ) group by full_name,total_count ";
																		       	 
								break;
								
							    case "regStatPieApplyFilter":
							    	
							    	applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where  b.created_date >= :param1 AND b.created_date <= :param2 " +regStatusWhereCondition+ "group by ussd_status";

								break;
							  
										 
							}
							    
							    switch (filterID) {
							    case "countChartApplyFilter":
							    
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
							    	
									
									if (applyFilterQuery.list().size()>0) {
										chartJSNArr = chartSales(applyFilterQuery.list());
										rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
										}
									else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									}
									break;
									
									
							    case "stackedChartApplyFilter":
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
									
									if (applyFilterQuery.list().size()>0) {

									periodicData = getStackedChartData(applyFilterQuery.list());
									rtn.put("StackedandLineCount", mapper.writeValueAsString(periodicData));

									}
									else {
										rtn.put("StackedandLineCount", mapper.writeValueAsString(""));

									}
									break;
									
							    case "lineChartApplyFilter":
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
									

									if (applyFilterQuery.list().size()>0) {

										periodicData = getStackedChartData(applyFilterQuery.list());
										rtn.put("StackedandLineCount",  mapper.writeValueAsString(periodicData));

									}
									else {
										rtn.put("StackedandLineCount",  mapper.writeValueAsString(""));

								    }
									break;
									
							    case "countPieApplyFilter":
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
									
									

									chartArray = new ArrayList<Float>(PieSalesPerAgent(applyFilterQuery.list()));
									
									rtn.put("listChartAgents",  mapper.writeValueAsString(applyFilterQuery.list()));
									rtn.put("chartData",  chartArray);
									
									break;
									
									
							    case "regStatPieApplyFilter":
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
									
									
									chartArray = new ArrayList<Float>(PieStatusPerClient(applyFilterQuery.list()));										
									rtn.put("regStatusList", mapper.writeValueAsString(chartArray));
									
									break;	
							    
									
							}
							    
			    	    } 
							else {
							
								//Daily-Maximum-Minimum and agent is not selected
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, "Min")) {

								    switch (filterID) {
								    
								    
							        case "stackedChartApplyFilter": case "lineChartApplyFilter":
							        	
							        	maxStackedLineChartCount=  "SELECT created_date,sum(salesCount) from( select t.created_date AS created_date ,t.salesCount AS salesCount from ( " + 
							                	  "select salesCount,created_date,full_name, RANK() over(PARTITION BY created_date order by salesCount desc) as RANK from " + 
								                  "(SELECT DISTINCT a.FULL_NAME as full_name, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as salesCount  " + 
								                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
								                  "GROUP BY TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.full_name order by salesCount desc)" + 
								                  ") t where RANK = 1) group by created_date order by created_date desc";
							        	
							        	minStackedLineChartCount=  "SELECT created_date,sum(salesCount) from( select t.created_date AS created_date ,t.salesCount AS salesCount from ( " + 
							                	  "select salesCount,created_date,full_name, RANK() over(PARTITION BY created_date order by salesCount asc) as RANK from " + 
								                  "(SELECT DISTINCT a.FULL_NAME as full_name, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as salesCount  " + 
								                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
								                  "GROUP BY TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.full_name order by salesCount asc)" + 
								                  ") t where RANK = 1) group by created_date order by created_date desc";	   			            	 	 				        	 		
						            	
						               	
							        	
								  break;
									
							        
							      case "countPieApplyFilter":
							        
							    	
							    	  applyFilterCount= "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
							                	"  and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
							                	"  group by full_name,total_count order by full_name ";
										
								 break;
								 
								 
							      case "countChartApplyFilter":
							    	  
							    	  applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
							                	" and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
							                	"  group by full_name,total_count order by full_name ";
										
								break;  
								
							    case "regStatPieApplyFilter":
								    	
							    	applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 " +regStatusWhereCondition+ "group by ussd_status";

								break;
							} // end switch filter 
								    
								    switch (filterID) {
									   
								    case "stackedChartApplyFilter":case "lineChartApplyFilter":
								    	
								   	 maxStackedLineChartQuery = session.createSQLQuery(maxStackedLineChartCount);
					            	 maxStackedLineChartQuery.setParameter("param1", startDate);
					            	 maxStackedLineChartQuery.setParameter("param2", endDate);
									
										
									 minStackedLineChartQuery = session.createSQLQuery(minStackedLineChartCount);
									 minStackedLineChartQuery.setParameter("param1", startDate);
									 minStackedLineChartQuery.setParameter("param2", endDate);
									
									if (maxStackedLineChartQuery.list().size()>0 ) {
										maxdefPeriodicData = getStackedChartData(maxStackedLineChartQuery.list());
										rtn.put("maxStackedandLineCount", mapper.writeValueAsString(maxdefPeriodicData));
									}
									else {
										rtn.put("maxStackedandLineCount", mapper.writeValueAsString(""));
									}
									if (minStackedLineChartQuery.list().size()>0 ) {
										mindefPeriodicData = getStackedChartData(minStackedLineChartQuery.list());
										rtn.put("minStackedandLineCount", mapper.writeValueAsString(mindefPeriodicData));
									}
									else {
										rtn.put("minStackedandLineCount", mapper.writeValueAsString(""));
									}	
										 
								
										 
								break;
								
								    case "countPieApplyFilter":
								    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
								    	applyFilterQuery.setParameter("param1", startDate);
								    	applyFilterQuery.setParameter("param2", endDate);

										

										chartArray = new ArrayList<Float>(PieSalesPerAgent(applyFilterQuery.list()));
										
										rtn.put("listChartAgents",  mapper.writeValueAsString(applyFilterQuery.list()));
										rtn.put("chartData",  chartArray);
										
										break;
										  
								   case "countChartApplyFilter":
									    
									   applyFilterQuery = session.createSQLQuery(applyFilterCount);
									   applyFilterQuery.setParameter("param1", startDate);
									   applyFilterQuery.setParameter("param2", endDate);
								    	
										
										if (applyFilterQuery.list().size()>0) {
											chartJSNArr = chartSales(applyFilterQuery.list());
											rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
											}
										else {
											rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
										}
								  break;
								   case "regStatPieApplyFilter":
									   
									   applyFilterQuery = session.createSQLQuery(applyFilterCount);
									   applyFilterQuery.setParameter("param1", startDate);
									   applyFilterQuery.setParameter("param2", endDate);
										
									  
										
										chartArray = new ArrayList<Float>(PieStatusPerClient(applyFilterQuery.list()));										
										rtn.put("regStatusList", mapper.writeValueAsString(chartArray));
										
										break;			
										
								}
									
								}// end daily-maximum-minimum condition
								
								// if maximum OR minimum is checked
								else {
								//Daily-Maximum with agent null 
								if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, null)) {
									
								    switch (filterID) {
								    
								    
								    case "stackedChartApplyFilter":case "lineChartApplyFilter":
										
								    	applyFilterCount=  "SELECT created_date,sum(salesCount) from( select t.created_date AS created_date ,t.salesCount AS salesCount from ( " + 
							                	  "select salesCount,created_date,full_name, RANK() over(PARTITION BY created_date order by salesCount desc) as RANK from " + 
								                  "(SELECT DISTINCT a.FULL_NAME as full_name, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as salesCount  " + 
								                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
								                  "GROUP BY TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.full_name order by salesCount desc)" + 
								                  ") t where RANK = 1) group by created_date order by created_date desc";	   			            	 	 				        	 		
						            	
									 	  
									  break;
									  
								 
									  
								    case "countPieApplyFilter":
								    	
								    	
								    	applyFilterCount= "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
							                	"  and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
							                	"  group by full_name,total_count order by full_name ";
											
								   	
									break;
									
								case "countChartApplyFilter":
									applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
							                	" and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
							                	"  group by full_name,total_count order by full_name ";
										
								break;   
								
								  case "regStatPieApplyFilter":
								    	
									  applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 " +regStatusWhereCondition+ "group by ussd_status";

									break;
								   
								    }
								}// end max-condition
								
								//Daily-Minimum with agent null 
								if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, "Min")) {
									
								    switch (filterID) {
								  
								    case "stackedChartApplyFilter":case "lineChartApplyFilter":
										
								    	//  periodicSalesCount= "select created_date,MIN(dailySalesCount) from(SELECT DISTINCT a.agent_id as agent_id ,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as dailySalesCount FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  " + 
										   //     " GROUP BY  TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.FULL_NAME ,a.MSISDN order by TO_CHAR(b.created_date, 'YYYY-MM-DD') ASC ) "+dailyDecodeCondition+" GROUP BY created_date ORDER BY created_date DESC " ;
								    	applyFilterCount=  "SELECT created_date,sum(salesCount) from( select t.created_date AS created_date ,t.salesCount AS salesCount from ( " + 
							                	  "select salesCount,created_date,full_name, RANK() over(PARTITION BY created_date order by salesCount asc) as RANK from " + 
								                  "(SELECT DISTINCT a.FULL_NAME as full_name, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as salesCount  " + 
								                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
								                  "GROUP BY TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.full_name order by salesCount asc)" + 
								                  ") t where RANK = 1) group by created_date order by created_date desc";	   			            	 	 				        	 		
						            		         	
							       
							        break;
					
									
								    case "countPieApplyFilter":

								    
								    	applyFilterCount= "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
							                	" and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
							                	"  group by full_name,total_count order by full_name ";
										
									break;
									
								    case "countChartApplyFilter":
								    	applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
								                	" and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
								                	"  group by full_name,total_count order by full_name ";
											
									break;   
									
								    case "regStatPieApplyFilter":
								    	
								    	applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 " +regStatusWhereCondition+ "group by ussd_status";

									break;
								    }
								}// end min condition

							
							
						    switch (filterID) {
						   
						    case "stackedChartApplyFilter": case "lineChartApplyFilter":
						    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
						    	applyFilterQuery.setParameter("param1", startDate);
						    	applyFilterQuery.setParameter("param2", endDate);
								
								
								
								if (applyFilterQuery.list().size()>0) {

								periodicData = getStackedChartData(applyFilterQuery.list());
								rtn.put("StackedandLineCount", mapper.writeValueAsString(periodicData));

								}
								else {
									rtn.put("StackedandLineCount", mapper.writeValueAsString(""));

								}
								break;
								
						   
								
						    case "countPieApplyFilter":
						    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
						    	applyFilterQuery.setParameter("param1", startDate);
						    	applyFilterQuery.setParameter("param2", endDate);
								

								chartArray = new ArrayList<Float>(PieSalesPerAgent(applyFilterQuery.list()));
								
								rtn.put("listChartAgents",  mapper.writeValueAsString(applyFilterQuery.list()));
								rtn.put("chartData",  chartArray);
								
								break;
						    case "countChartApplyFilter":
							    
						    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
						    	applyFilterQuery.setParameter("param1", startDate);
						    	applyFilterQuery.setParameter("param2", endDate);
						    	
								
								if (applyFilterQuery.list().size()>0) {
									chartJSNArr = chartSales(applyFilterQuery.list());
									rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
									}
								else {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								}
							break;
						    case "regStatPieApplyFilter":
						    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
						    	applyFilterQuery.setParameter("param1", startDate);
						    	applyFilterQuery.setParameter("param2", endDate);
								
								chartArray = new ArrayList<Float>(PieStatusPerClient(applyFilterQuery.list()));										
								rtn.put("regStatusList", mapper.writeValueAsString(chartArray));
								
								break;	
	
								
						}
						  
							}
							} // end else of max or min is checked 
						}// end daily period
						
						if (StringUtils.equalsIgnoreCase(period,"Accu")) {
							
							//Null Maximum - Null Minimum 
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
							    switch (filterID) {
							    
							    case "countChartApplyFilter":
							    	
							    	applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
					                	" and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
					                	"  group by full_name,total_count order by full_name ";
								
								break;
								
							   
							        
							    case "countPieApplyFilter":
							    	applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name ) group by full_name,total_count ";
																		       	 
								break;
								
							    case "regStatPieApplyFilter":
							    	
							    	applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 " +regStatusWhereCondition+ "group by ussd_status";

								break;
							  
										 
							}
							    
							    switch (filterID) {
							    case "countChartApplyFilter":
							    
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
							    	
									
									if (applyFilterQuery.list().size()>0) {
										chartJSNArr = chartSales(applyFilterQuery.list());
										rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
										}
									else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									}
									break;
									
									
							    case "countPieApplyFilter":
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
									
									chartArray = new ArrayList<Float>(PieSalesPerAgent(applyFilterQuery.list()));
									
									rtn.put("listChartAgents",  mapper.writeValueAsString(applyFilterQuery.list()));
									rtn.put("chartData",  chartArray);
									
									break;
									
									
							    case "regStatPieApplyFilter":
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
									
									chartArray = new ArrayList<Float>(PieStatusPerClient(applyFilterQuery.list()));	
									rtn.put("regStatusList", mapper.writeValueAsString(chartArray));
									
							break;	
			
							}
							rtn.put("StackedandLineCount", mapper.writeValueAsString(""));	

			    	    } // end nullMax- nullMin condition
							
							// Accumulated with Maximum - Minimum and agent is null 
							else {
							
							
								rtn.put("chartData", 0);
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("StackedandLineCount", mapper.writeValueAsString(""));	
								rtn.put("regStatusList", mapper.writeValueAsString(""));
								rtn.put("maxStackedandLineCount", mapper.writeValueAsString(""));
								rtn.put("minStackedandLineCount", mapper.writeValueAsString(""));

								
								
							}// end max-min condition
									
						} // end Accu period 
						
						// weekly period with agent null 
						if (StringUtils.equalsIgnoreCase(period, "Weekly")) {

							double diff =(endDate.getTime() - startDate.getTime()) / 1000.0;
					   	 	diff /= (60 * 60 * 24 * 7);
					   	 	diff =  Math.abs(Math.ceil(diff));
					   	 	
					   	 	String date = "";
					   	 	Date weeklyDate;
					   	 	Timestamp weeklyEndDate = startDate;
						 	Calendar cal = Calendar.getInstance();  
							Timestamp weeklyStartDate = startDate;
							
							//Null Maximum - Null Minimum with agent null 
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
								switch (filterID) {  
								case "countChartApplyFilter":
						 	    	  
									applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
						                	" and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
						                	"  group by full_name,total_count order by full_name ";
									
								break;  
								
								case "stackedChartApplyFilter":case "lineChartApplyFilter":
						 	    	  
									applyFilterCount = "select t.total_count as \"totalCount\",'"+date+"' as \"Date\" from (select " +stackedLineChartCaseCondition+ " from(SELECT coalesce(count(a.msisdn),0) as total_count from agent a inner join clients b on a.msisdn = b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'))t "  ;
									
								break;  
							
								
								case "countPieApplyFilter":
						 	    	  
									applyFilterCount ="select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name order by a.full_name ";

								break; 
								
								case "regStatPieApplyFilter":
						 	    	  
									applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 " +regStatusWhereCondition+ "group by ussd_status";

								break; 
								
								}
								
								switch (filterID) {
								 case "countChartApplyFilter":
									 applyFilterQuery = session.createSQLQuery(applyFilterCount);
									 applyFilterQuery.setParameter("param1", startDate);
									 applyFilterQuery.setParameter("param2", endDate);
								   
								     
								     if (applyFilterQuery.list().size()>0) {
								    	 chartJSNArr = chartSales(applyFilterQuery.list());
								    	 rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
								     }
									else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									}
								     break; 
								     
								 case "countPieApplyFilter":
									 applyFilterQuery = session.createSQLQuery(applyFilterCount);
									 applyFilterQuery.setParameter("param1", startDate);
									 applyFilterQuery.setParameter("param2", endDate);
									
									 chartArray = new ArrayList<Float>(PieSalesPerAgent(applyFilterQuery.list()));
									 rtn.put("chartData",  chartArray);
										
										break;
										
								 case "regStatPieApplyFilter":
									 applyFilterQuery = session.createSQLQuery(applyFilterCount);
									 applyFilterQuery.setParameter("param1", startDate);
									 applyFilterQuery.setParameter("param2", endDate);
									 
									 
									 chartArray = new ArrayList<Float>(PieStatusPerClient(applyFilterQuery.list()));											
									 rtn.put("regStatusList", mapper.writeValueAsString(chartArray));
									
									 break;	
								 case "stackedChartApplyFilter":case "lineChartApplyFilter":
									 for( i = 0; i< diff; i++) {
											
										 weeklyEndDate = weeklyStartDate;
						 	        	 cal.setTime(weeklyEndDate); 
								 	     cal.add(Calendar.DAY_OF_MONTH, 6); // 6 is the days to add   
								 	     weeklyDate = cal.getTime();
								 	     weeklyEndDate = new Timestamp(weeklyDate.getTime());
										 
								 	   					 	         
								 	    if ((weeklyEndDate).compareTo(endDate) > 0) {
											weeklyEndDate = endDate;
											
											
										}
										date =   (weeklyStartDate.getYear()+1900)+"/"+ (weeklyStartDate.getMonth() +1)+"/"+(weeklyStartDate.getDate())+"-"+  (weeklyEndDate.getYear()+1900)+"/"+ (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getDate());

										applyFilterQuery = session.createSQLQuery(applyFilterCount);
										applyFilterQuery.setParameter("param1", weeklyStartDate);
										applyFilterQuery.setParameter("param2", weeklyEndDate);
										
								 		
										salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) applyFilterQuery)
							         	.addScalar("totalCount").addScalar("Date")
							         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
										

										salesCountLineCharts.get(0).setDate(date);		


										countLineChartList.addAll(salesCountLineCharts);
										weeklyStartDate = weeklyEndDate;
										cal.setTime(weeklyStartDate);
										cal.add(Calendar.DAY_OF_MONTH, 1); // 1 is the day to add
										weeklyDate = cal.getTime();
										weeklyStartDate = new Timestamp(weeklyDate.getTime());

							} 
									 defPeriodicData = getPeriodicStackedChartData(countLineChartList);
								    rtn.put("StackedandLineCount",  mapper.writeValueAsString(defPeriodicData));

									 
									 break;
									 
							}		
									 
						}
						 // Weekly period with Maximum or Minimum and agent is null 
						else {
							
							//Weekly-Maximum-Minimum with agent null 
							if (StringUtils.equalsIgnoreCase(max, "Max") || StringUtils.equalsIgnoreCase(min, "Min")) {
								
							    switch (filterID) {
							    
							    case "stackedChartApplyFilter": case "lineChartApplyFilter":
							    	
							    	maxStackedLineChartCount=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
						                	  " select salesCount,full_name, RANK() over(order by salesCount desc) as RANK from " + 
							                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
							                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
							                  "GROUP BY a.full_name order by salesCount desc)" + 
							                  ") t where RANK = 1 order by '"+date+"' desc";	  
							    	
							    	minStackedLineChartCount=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
						                	  " select salesCount,full_name, RANK() over(order by salesCount asc) as RANK from " + 
							                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
							                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
							                  "GROUP BY a.full_name order by salesCount asc)" + 
							                  ") t where RANK = 1 order by '"+date+"' desc";	
							    	break;
							   
							    	
							      case "countPieApplyFilter":
								       
							    	  applyFilterCount= "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
								                	" and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
								                	"  group by full_name,total_count order by full_name ";
											
									 break;	
									 
							   case "countChartApplyFilter":
								   applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
						                	" and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
						                	"  group by full_name,total_count order by full_name ";
									
									break;  
							
							   case "regStatPieApplyFilter":
							    	
								   applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 " +regStatusWhereCondition+ "group by ussd_status";

							    	break;
							    	
							    }// end switch filter
							    
							    switch (filterID) {
							    
							    case "stackedChartApplyFilter":case "lineChartApplyFilter":
							    	
							    	 weeklyStartDate = startDate;
									for(  i = 0; i< diff; i++) {
										weeklyEndDate = weeklyStartDate;
										cal.setTime(weeklyEndDate); 
										cal.add(Calendar.DAY_OF_MONTH, 6); // 6 is the days to add 
										weeklyDate = cal.getTime();
									 	weeklyEndDate = new Timestamp(weeklyDate.getTime());
									 	
									 	if ((weeklyEndDate).compareTo(endDate) > 0) {
									 		weeklyEndDate = endDate;
									 	}
									 	
									 	date =   (weeklyStartDate.getYear()+1900)+"/"+ (weeklyStartDate.getMonth() +1)+"/"+(weeklyStartDate.getDate())+"-"+  (weeklyEndDate.getYear()+1900)+"/"+ (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getDate());
									 	
									 	maxStackedLineChartQuery = session.createSQLQuery(maxStackedLineChartCount);
									 	maxStackedLineChartQuery.setParameter("param1", weeklyStartDate);
									 	maxStackedLineChartQuery.setParameter("param2", weeklyEndDate);
									 	
							    	
									 	minStackedLineChartQuery = session.createSQLQuery(minStackedLineChartCount);
									 	minStackedLineChartQuery.setParameter("param1", weeklyStartDate);
									 	minStackedLineChartQuery.setParameter("param2", weeklyEndDate);
									
									
									 	maxSalesStackedLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) maxStackedLineChartQuery)
								         	.addScalar("totalCount").addScalar("Date")
								         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
											
									 	maxSalesStackedLineCharts.get(0).setDate(date);		
									 	
									 	
									 	minSalesStackedLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) minStackedLineChartQuery)
									         	.addScalar("totalCount").addScalar("Date")
									         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
												
										minSalesStackedLineCharts.get(0).setDate(date);	
									
										
										
										maxCountStackedLineChartList.addAll(maxSalesStackedLineCharts);
										minCountStackedLineChartList.addAll(minSalesStackedLineCharts);
										
										weeklyStartDate = weeklyEndDate;
										cal.setTime(weeklyStartDate);
										cal.add(Calendar.DAY_OF_MONTH, 1); // 1 is the day to add
										weeklyDate = cal.getTime();
										weeklyStartDate = new Timestamp(weeklyDate.getTime());
										
									}
									
								
									maxdefPeriodicData = getPeriodicStackedChartData(maxCountStackedLineChartList);
									mindefPeriodicData = getPeriodicStackedChartData(minCountStackedLineChartList);
									
									if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, null)) {
										rtn.put("StackedandLineCount",  mapper.writeValueAsString(maxdefPeriodicData));
									}
									else if (StringUtils.equalsIgnoreCase(min, "Min") && StringUtils.equalsIgnoreCase(max, null)) {
										rtn.put("StackedandLineCount",  mapper.writeValueAsString(mindefPeriodicData));
									}
									else if (StringUtils.equalsIgnoreCase(min, "Min") && StringUtils.equalsIgnoreCase(max, "Max")) {
										rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(maxdefPeriodicData));
										rtn.put("minStackedandLineCount",  mapper.writeValueAsString(mindefPeriodicData));

									}
									
								break;
									
							   
										
								    case "countPieApplyFilter":
								    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
								    	applyFilterQuery.setParameter("param1", startDate);
								    	applyFilterQuery.setParameter("param2", endDate);
										

										chartArray = new ArrayList<Float>(PieSalesPerAgent(applyFilterQuery.list()));
										
										rtn.put("listChartAgents",  mapper.writeValueAsString(applyFilterQuery.list()));
										rtn.put("chartData",  chartArray);
										
										break;
										
								   case "countChartApplyFilter":
									    
									   applyFilterQuery = session.createSQLQuery(applyFilterCount);
									   applyFilterQuery.setParameter("param1", startDate);
									   applyFilterQuery.setParameter("param2", endDate);
								    	
										
										if (applyFilterQuery.list().size()>0) {
											chartJSNArr = chartSales(applyFilterQuery.list());
											rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
											}
										else {
											rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
										}
								  break;
								   case "regStatPieApplyFilter":
									   applyFilterQuery = session.createSQLQuery(applyFilterCount);
									   applyFilterQuery.setParameter("param1", startDate);
									   applyFilterQuery.setParameter("param2", endDate);
										
									   chartArray = new ArrayList<Float>(PieStatusPerClient(applyFilterQuery.list()));										
									   rtn.put("regStatusList", mapper.writeValueAsString(chartArray));
										
										break;
									
							    }// end switch filter
							    
							  
							}// end max-min condition 
							
						}
						}// end weekly period with null agent
						
						
						// monthly period with null agent
						if (StringUtils.equalsIgnoreCase(period, "Monthly")) {

							 Calendar m =Calendar.getInstance();
							 m.setTime(startDate);
							 int nMonth1=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
							 m.setTime(endDate);
							 int nMonth2=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
							 int diff = Math.abs(nMonth2-nMonth1);
							 Timestamp month = startDate;
			   	 	         String date = "";
							
							//Null Maximum - Null Minimum with null agent
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
								switch (filterID) {  
								case "countChartApplyFilter":
						 	    	  
									applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
						                	" and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
						                	"  group by full_name,total_count order by full_name ";
									
								break;  
								
								case "stackedChartApplyFilter":case "lineChartApplyFilter":
						 	    	  
									applyFilterCount = "select t.total_count as \"totalCount\",'"+date+"' as \"Date\" from (select " +stackedLineChartCaseCondition+ " from(SELECT coalesce(count(a.msisdn),0) as total_count from agent a inner join clients b on a.msisdn = b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'))t "  ;
									
								break;  
								
								
								
								case "countPieApplyFilter":
						 	    	  
									applyFilterCount ="select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name order by a.full_name ";

								break; 
								
								case "regStatPieApplyFilter":
						 	    	  
									applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 " +regStatusWhereCondition+ "group by ussd_status";

								break; 
								
								}
								
								switch (filterID) {
								 case "countChartApplyFilter":
									 applyFilterQuery = session.createSQLQuery(applyFilterCount);
									 applyFilterQuery.setParameter("param1", startDate);
									 applyFilterQuery.setParameter("param2", endDate);
								    
								    
								     if (applyFilterQuery.list().size()>0) {
								    	 chartJSNArr = chartSales(applyFilterQuery.list());
								    	 rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
								     }
									else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									}
								     break; 
								     
								 case "countPieApplyFilter":
									 	applyFilterQuery = session.createSQLQuery(applyFilterCount);
								    	applyFilterQuery.setParameter("param1", startDate);
								    	applyFilterQuery.setParameter("param2", endDate);
										
										

										chartArray = new ArrayList<Float>(PieSalesPerAgent(applyFilterQuery.list()));
										rtn.put("chartData",  chartArray);
										
										break;
										
								 case "regStatPieApplyFilter":
									 applyFilterQuery = session.createSQLQuery(applyFilterCount);
									 applyFilterQuery.setParameter("param1", startDate);
									 applyFilterQuery.setParameter("param2", endDate);
									
									 chartArray = new ArrayList<Float>(PieStatusPerClient(applyFilterQuery.list()));											
									 rtn.put("regStatusList", mapper.writeValueAsString(chartArray));
										
									 break;	
								 case "stackedChartApplyFilter":case "lineChartApplyFilter":
									 for( i = 0; i<=diff; i++) {
											
											
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
											
											if ((endOfMonth).compareTo(endDate) > 0) {
												endOfMonth =  endDate;
												
											}										
											date = (monthofDate.getYear()+1900) +"/"+ (monthofDate.getMonth() +1) +"/"+(monthofDate.getDate())+"-" + (monthofDate.getYear()+1900)+"/"+ (endOfMonth.getMonth() +1) +"/"+ (endOfMonth.getDate());

											applyFilterQuery = session.createSQLQuery(applyFilterCount);
											applyFilterQuery.setParameter("param1", monthofDate);
											applyFilterQuery.setParameter("param2", endOfMonth);
										
								 		
											salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) applyFilterQuery)
								         	.addScalar("totalCount").addScalar("Date")
								         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
											
	
											salesCountLineCharts.get(0).setDate(date);		
	
	
											countLineChartList.addAll(salesCountLineCharts);
											
											calendar.setTime(endOfMonth);  
								    	    calendar.add(Calendar.DATE, +1);  
								    	    Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
							                month = NextDayOfMonth;

							} 
									
									 defPeriodicData = getPeriodicStackedChartData(countLineChartList);
								    rtn.put("StackedandLineCount",  mapper.writeValueAsString(defPeriodicData));

									 
									 break;
								

							}		
											
									 
						}
					
						// Monthly period with Maximum or Minimum and agent is null 
						else {
							//Monthly-Maximum-Minimum with agent null 
							if (StringUtils.equalsIgnoreCase(max, "Max") || StringUtils.equalsIgnoreCase(min, "Min")) {
								 switch (filterID) {
								    
								    case "stackedChartApplyFilter":case "lineChartApplyFilter":
								    	
								    	maxStackedLineChartCount = "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
							                	  " select salesCount,full_name, RANK() over(order by salesCount desc) as RANK from " + 
								                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
								                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
								                  "GROUP BY a.full_name order by salesCount desc)" + 
								                  ") t where RANK = 1 order by '"+date+"' desc";	
								    	
								    	minStackedLineChartCount = "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
							                	  " select salesCount,full_name, RANK() over(order by salesCount asc) as RANK from " + 
								                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
								                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.REGION_NAME LIKE '%"+region+"%'  "+
								                  "GROUP BY a.full_name order by salesCount asc)" + 
								                  ") t where RANK = 1 order by '"+date+"' desc";
						            		
								    
								    	break;
								    	
								    
								    case "countPieApplyFilter":
									       
								    	applyFilterCount= "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
									        " and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
									         "  group by full_name,total_count order by full_name ";
												
										 break;	
										 
								   case "countChartApplyFilter":
										
									   applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
							                	" and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
							                	"  group by full_name,total_count order by full_name ";
										
										break;  
								
								   case "regStatPieApplyFilter":
								    	
									   applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 " +regStatusWhereCondition+ "group by ussd_status";

								    	break;
								    	
								 }// end switch filter
								 
								 switch (filterID) {
								 case "stackedChartApplyFilter": case "lineChartApplyFilter":
								    	
									 for( i = 0; i<= diff; i++) {
											
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
											
											if ((endOfMonth).compareTo(endDate) > 0) {
												endOfMonth =  endDate;
												
											} 
											date = (monthofDate.getYear()+1900) +"/"+ (monthofDate.getMonth() +1) +"/"+(monthofDate.getDate())+"-" + (monthofDate.getYear()+1900)+"/"+ (endOfMonth.getMonth() +1) +"/"+ (endOfMonth.getDate());

										 	maxStackedLineChartQuery = session.createSQLQuery(maxStackedLineChartCount);
										 	maxStackedLineChartQuery.setParameter("param1", monthofDate);
										 	maxStackedLineChartQuery.setParameter("param2", endOfMonth);
										 
										 	minStackedLineChartQuery = session.createSQLQuery(minStackedLineChartCount);
										 	minStackedLineChartQuery.setParameter("param1", monthofDate);
										 	minStackedLineChartQuery.setParameter("param2", endOfMonth);
										
										
										 	maxSalesStackedLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) maxStackedLineChartQuery)
									         	.addScalar("totalCount").addScalar("Date")
									         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
												
										 	maxSalesStackedLineCharts.get(0).setDate(date);		
										 	
										 	
										 	minSalesStackedLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) minStackedLineChartQuery)
										         	.addScalar("totalCount").addScalar("Date")
										         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
													
											minSalesStackedLineCharts.get(0).setDate(date);	
										
											
											
											maxCountStackedLineChartList.addAll(maxSalesStackedLineCharts);
											minCountStackedLineChartList.addAll(minSalesStackedLineCharts);
											
											calendar.setTime(endOfMonth);  
								    	    calendar.add(Calendar.DATE, +1);  
								    	    Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
							                month = NextDayOfMonth;	
										}
										
										
										maxdefPeriodicData = getPeriodicStackedChartData(maxCountStackedLineChartList);
										mindefPeriodicData = getPeriodicStackedChartData(minCountStackedLineChartList);
										
										if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, null)) {
											rtn.put("StackedandLineCount",  mapper.writeValueAsString(maxdefPeriodicData));
										}
										else if (StringUtils.equalsIgnoreCase(min, "Min") && StringUtils.equalsIgnoreCase(max, null)) {
											rtn.put("StackedandLineCount",  mapper.writeValueAsString(mindefPeriodicData));
										}
										else if (StringUtils.equalsIgnoreCase(min, "Min") && StringUtils.equalsIgnoreCase(max, "Max")) {
											rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(maxdefPeriodicData));
											rtn.put("minStackedandLineCount",  mapper.writeValueAsString(mindefPeriodicData));

										}
										
									break;
									
								 case "countPieApplyFilter":
									 applyFilterQuery = session.createSQLQuery(applyFilterCount);
									 applyFilterQuery.setParameter("param1", startDate);
									 applyFilterQuery.setParameter("param2", endDate);
										
									chartArray = new ArrayList<Float>(PieSalesPerAgent(applyFilterQuery.list()));
										
									rtn.put("listChartAgents",  mapper.writeValueAsString(applyFilterQuery.list()));
									rtn.put("chartData",  chartArray);
										
									break;
										
								   case "countChartApplyFilter":
									    
									   applyFilterQuery = session.createSQLQuery(applyFilterCount);
									   applyFilterQuery.setParameter("param1", startDate);
									   applyFilterQuery.setParameter("param2", endDate);
								    	
										if (applyFilterQuery.list().size()>0) {
											chartJSNArr = chartSales(applyFilterQuery.list());
											rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
											}
										else {
											rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
										}
								  break;
								  
								   case "regStatPieApplyFilter":
									   applyFilterQuery = session.createSQLQuery(applyFilterCount);
									   applyFilterQuery.setParameter("param1", startDate);
									   applyFilterQuery.setParameter("param2", endDate);
										
										chartArray = new ArrayList<Float>(PieStatusPerClient(applyFilterQuery.list()));										
										rtn.put("regStatusList", mapper.writeValueAsString(chartArray));
										
										break;	
								 }// end switch filter
								    
							}
						}// end else of monthly-maximum OR minimum
						}// end monthly period
						
						// Absolutely Maximum - Minimum 
						if (StringUtils.equalsIgnoreCase(period,"null")) {
							
								rtn.put("chartData", 0);
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("StackedandLineCount", mapper.writeValueAsString(""));	
								rtn.put("regStatusList", mapper.writeValueAsString(""));								
								rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(""));
								rtn.put("minStackedandLineCount",  mapper.writeValueAsString(""));

						} // end null period 

				} // end if agent is null

			// if agent is selected
				else {
					if (StringUtils.equalsIgnoreCase(period, "Daily")) {
					
						// Daily Null Maximum - Null Minimum with agent selected 
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
							    switch (filterID) {
							    
							    case "countChartApplyFilter":
							    	applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
					                	" and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
					                	"  group by full_name,total_count order by full_name ";
								
								break;
								
							    case "stackedChartApplyFilter":case "lineChartApplyFilter":
	
							    	applyFilterCount = "SELECT * FROM (select TO_CHAR(b.created_date, 'YYYY/MM/DD') as created_date, count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'\r\n" + 
							        " and a.full_name IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' group by TO_CHAR(b.created_date, 'YYYY/MM/DD'))" + decodeCondition  + 
							        " group by created_date,total_count ORDER BY created_date DESC ";
							       
							        break;
							        							
							    case "countPieApplyFilter":
							    	applyFilterCount = "SELECT * FROM (select a.agent_id,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.full_name IN ("+finalAgentsNameList+")   and a.REGION_NAME LIKE '%"+region+"%' group by a.agent_id ) group by agent_id,total_count ";
																		       	 
								break;
								
							    case "regStatPieApplyFilter":
							    	
							    	applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 AND b.status !='CANCELLED' and a.full_name IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%' " +regStatusWhereCondition+ "group by ussd_status";
							    	
								break;
								
							
							}					                  
							    switch (filterID) {
							    case "countChartApplyFilter":
							    	
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
									
										if (applyFilterQuery.list().size()>0) {
											chartJSNArr = chartSales(applyFilterQuery.list());
											rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
										}
										else {
											rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
										}
									break;
								
							    case "stackedChartApplyFilter": case "lineChartApplyFilter":
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
									
									if (applyFilterQuery.list().size()>0) {

									periodicData = getStackedChartData(applyFilterQuery.list());
									rtn.put("StackedandLineCount", mapper.writeValueAsString(periodicData));

									}
									else {
										rtn.put("StackedandLineCount", mapper.writeValueAsString(""));

									}
									break;
									
							   
							    case "countPieApplyFilter":
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);

									chartArray = new ArrayList<Float>(PieSalesPerAgent(applyFilterQuery.list()));
									
									rtn.put("listChartAgents",  mapper.writeValueAsString(applyFilterQuery.list()));
									rtn.put("chartData",  chartArray);
									
									break;
									
							    case "regStatPieApplyFilter":
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
									
									
									chartArray = new ArrayList<Float>(PieStatusPerClient(applyFilterQuery.list()));										
									rtn.put("regStatusList", mapper.writeValueAsString(chartArray));
									
									break;	
									
							    }
							}
							
				} // end of daily period condition 
						
					if (StringUtils.equalsIgnoreCase(period,"Accu")) {
							
							// No max no min are checked
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
							    switch (filterID) {
							    
							    case "countChartApplyFilter":
							    	
							    	applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
					                	" and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
					                	"  group by full_name,total_count order by full_name ";
								
								break;
								
							    
							        
							    case "countPieApplyFilter":
							    	applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name ) group by full_name,total_count ";
																		       	 
								break;
								
							    case "regStatPieApplyFilter":
							    	
							    	applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' " +regStatusWhereCondition+ "group by ussd_status";

								break;
							  
										 
							}
							    
							    switch (filterID) {
							    case "countChartApplyFilter":
							    
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
							    	
									
									if (applyFilterQuery.list().size()>0) {
										chartJSNArr = chartSales(applyFilterQuery.list());
										rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
										}
									else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									}
									break;
									
									
							    case "countPieApplyFilter":
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
									

									chartArray = new ArrayList<Float>(PieSalesPerAgent(applyFilterQuery.list()));
									
									rtn.put("listChartAgents",  mapper.writeValueAsString(applyFilterQuery.list()));
									rtn.put("chartData",  chartArray);
									
									break;
									
									
							    case "regStatPieApplyFilter":
							    	applyFilterQuery = session.createSQLQuery(applyFilterCount);
							    	applyFilterQuery.setParameter("param1", startDate);
							    	applyFilterQuery.setParameter("param2", endDate);
								
									
									chartArray = new ArrayList<Float>(PieStatusPerClient(applyFilterQuery.list()));	
									rtn.put("regStatusList", mapper.writeValueAsString(chartArray));
									
							break;	
			
							}
							rtn.put("StackedandLineCount", mapper.writeValueAsString(""));	

			    	    } 
						else {
					             
								rtn.put("chartData",  0);
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("StackedandLineCount", mapper.writeValueAsString(""));	
								rtn.put("regStatusList", mapper.writeValueAsString(""));

								
							}// end max-min condition
									
						} // end Accu period 
					
					// weekly period with selected agent   
					if (StringUtils.equalsIgnoreCase(period, "Weekly")) {

						double diff =(endDate.getTime() - startDate.getTime()) / 1000.0;
				   	 	diff /= (60 * 60 * 24 * 7);
				   	 	diff =  Math.abs(Math.ceil(diff));
				   	 	
				   	 	String date = "";
				   	 	Date weeklyDate;
				   	 	Timestamp weeklyEndDate = startDate;
					 	Calendar cal = Calendar.getInstance();  
						Timestamp weeklyStartDate = startDate;
						
						//Null Maximum - Null Minimum 
						if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
							switch (filterID) {  
							case "countChartApplyFilter":
					 	    	  
								applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
					                	" and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
					                	"  group by full_name,total_count order by full_name ";
								
							break;  
							
							case "stackedChartApplyFilter":case "lineChartApplyFilter":
					 	    	  
								applyFilterCount = "select t.total_count as \"totalCount\",'"+date+"' as \"Date\" from (select " +stackedLineChartCaseCondition+ " from(SELECT coalesce(count(a.msisdn),0) as total_count from agent a inner join clients b on a.msisdn = b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED' and a.full_name IN ("+finalAgentsNameList+")  and a.REGION_NAME LIKE '%"+region+"%'))t "  ;
								
							break;  
							
							
							case "countPieApplyFilter":
					 	    	  
								applyFilterCount ="select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name order by a.full_name ";

							break; 
							
							case "regStatPieApplyFilter":
					 	    	  
								applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' " +regStatusWhereCondition+ "group by ussd_status";

							break; 
							
							}
							
							switch (filterID) {
							 case "countChartApplyFilter":
								 applyFilterQuery = session.createSQLQuery(applyFilterCount);
								 applyFilterQuery.setParameter("param1", startDate);
								 applyFilterQuery.setParameter("param2", endDate);
							     
							    
							     if (applyFilterQuery.list().size()>0) {
							    	 chartJSNArr = chartSales(applyFilterQuery.list());
							    	 rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
							     }
								else {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								}
							     break; 
							     
							 case "countPieApplyFilter":
								 applyFilterQuery = session.createSQLQuery(applyFilterCount);
								 applyFilterQuery.setParameter("param1", startDate);
								 applyFilterQuery.setParameter("param2", endDate);
								
								chartArray = new ArrayList<Float>(PieSalesPerAgent(applyFilterQuery.list()));
									
								rtn.put("chartData",  chartArray);
									
							break;
									
							 case "regStatPieApplyFilter":
								 applyFilterQuery = session.createSQLQuery(applyFilterCount);
								 applyFilterQuery.setParameter("param1", startDate);
								 applyFilterQuery.setParameter("param2", endDate);
								 
								 
								 chartArray = new ArrayList<Float>(PieStatusPerClient(applyFilterQuery.list()));											
								 rtn.put("regStatusList", mapper.writeValueAsString(chartArray));
								
								 break;	
								 
							 case "stackedChartApplyFilter": case "lineChartApplyFilter":
								 for( i = 0; i< diff; i++) {
										
									 weeklyEndDate = weeklyStartDate;
					 	        	 cal.setTime(weeklyEndDate); 
							 	     cal.add(Calendar.DAY_OF_MONTH, 6); // 6 is the days  to add  
							 	     weeklyDate = cal.getTime();
							 	     weeklyEndDate = new Timestamp(weeklyDate.getTime());
									 
							 	   					 	         
							 	    if ((weeklyEndDate).compareTo(endDate) > 0) {
										weeklyEndDate = endDate;										
										
									} 
									date =   (weeklyStartDate.getYear()+1900)+"/"+ (weeklyStartDate.getMonth() +1)+"/"+(weeklyStartDate.getDate())+"-"+  (weeklyEndDate.getYear()+1900)+"/"+ (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getDate());

									applyFilterQuery = session.createSQLQuery(applyFilterCount);
									applyFilterQuery.setParameter("param1", weeklyStartDate);
									applyFilterQuery.setParameter("param2", weeklyEndDate);
									
									salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) applyFilterQuery)
						         	.addScalar("totalCount").addScalar("Date")
						         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
									

									salesCountLineCharts.get(0).setDate(date);		


									countLineChartList.addAll(salesCountLineCharts);
									weeklyStartDate = weeklyEndDate;
									cal.setTime(weeklyStartDate);
									cal.add(Calendar.DAY_OF_MONTH, 1); // 1 is the day to add 
									weeklyDate = cal.getTime();
									weeklyStartDate = new Timestamp(weeklyDate.getTime());

						} 
								 defPeriodicData = getPeriodicStackedChartData(countLineChartList);
							    rtn.put("StackedandLineCount",  mapper.writeValueAsString(defPeriodicData));

								 
								 break;
							


						}															 
					}

					}// end of weekly 
					
					
					// monthly period with  agent
					if (StringUtils.equalsIgnoreCase(period, "Monthly")) {

						 Calendar m =Calendar.getInstance();
						 m.setTime(startDate);
						 int nMonth1=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
						 m.setTime(endDate);
						 int nMonth2=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
						 int diff = Math.abs(nMonth2-nMonth1);
						 Timestamp month = startDate;
		   	 	         String date = "";
						
						
						if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
							switch (filterID) {  
							case "countChartApplyFilter":
					 	    	  
								applyFilterCount = "SELECT * FROM (select a.full_name,count(*) as total_count  from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' "+
					                	" and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name)" + decodeCondition  + 
					                	"  group by full_name,total_count order by full_name ";
								
							break;  
							
							case "stackedChartApplyFilter":case "lineChartApplyFilter":
  
								applyFilterCount = "select t.total_count as \"totalCount\",'"+date+"' as \"Date\" from (select " +stackedLineChartCaseCondition+ " from(SELECT coalesce(count(a.msisdn),0) as total_count from agent a inner join clients b on a.msisdn = b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' and b.status !='CANCELLED' and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%'))t "  ;
								
							break;  
						
							case "countPieApplyFilter":
					 	    	  
								applyFilterCount ="select a.full_name,count(*) from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED' and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' group by a.full_name order by a.full_name ";

							break; 
							
							case "regStatPieApplyFilter":
					 	    	  
								applyFilterCount= "SELECT b.ussd_status, COUNT(b.ussd_status) from agent a inner join clients b on a.msisdn=b.agent_number  where b.created_date >= :param1 AND b.created_date <= :param2 and a.full_name IN ("+finalAgentsNameList+") and a.REGION_NAME LIKE '%"+region+"%' " +regStatusWhereCondition+ "group by ussd_status";

							break; 
							
							}
							
							switch (filterID) {
							 case "countChartApplyFilter":
								 applyFilterQuery = session.createSQLQuery(applyFilterCount);
								 applyFilterQuery.setParameter("param1", startDate);
								 applyFilterQuery.setParameter("param2", endDate);
							    
							     
							     if (applyFilterQuery.list().size()>0) {
							    	 chartJSNArr = chartSales(applyFilterQuery.list());
							    	 rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
							     }
								else {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								}
							     break; 
							     
							 case "countPieApplyFilter":
								 applyFilterQuery = session.createSQLQuery(applyFilterCount);
								 applyFilterQuery.setParameter("param1", startDate);
								 applyFilterQuery.setParameter("param2", endDate);
								
								 chartArray = new ArrayList<Float>(PieSalesPerAgent(applyFilterQuery.list()));
								 rtn.put("chartData",  chartArray);
									
									break;
									
							 case "regStatPieApplyFilter":
								 applyFilterQuery = session.createSQLQuery(applyFilterCount);
								 applyFilterQuery.setParameter("param1", startDate);
								 applyFilterQuery.setParameter("param2", endDate);
								
								 
								 chartArray = new ArrayList<Float>(PieStatusPerClient(applyFilterQuery.list()));											
								 rtn.put("regStatusList", mapper.writeValueAsString(chartArray));
									
								 break;	
							 case "stackedChartApplyFilter":case "lineChartApplyFilter":
								 
								 for( i = 0; i<= diff; i++) {										
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
										
										if ((endOfMonth).compareTo(endDate) > 0) {
											endOfMonth =  endDate;
											
										} 
										date = (monthofDate.getYear()+1900) +"/"+ (monthofDate.getMonth() +1) +"/"+ (monthofDate.getDate())+"-" + (endOfMonth.getYear()+1900)+"/"+ (endOfMonth.getMonth() +1) +"/"+(monthofDate.getDate());


										applyFilterQuery = session.createSQLQuery(applyFilterCount);
										applyFilterQuery.setParameter("param1", monthofDate);
										applyFilterQuery.setParameter("param2", endOfMonth);
									
							 		
									salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) applyFilterQuery)
						         	.addScalar("totalCount").addScalar("Date")
						         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
									

									salesCountLineCharts.get(0).setDate(date);		


									countLineChartList.addAll(salesCountLineCharts);
									
									calendar.setTime(endOfMonth);  
						    	    calendar.add(Calendar.DATE, +1);  
						    	    Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
					                month = NextDayOfMonth;

						} 
								
								 defPeriodicData = getPeriodicStackedChartData(countLineChartList);
							    rtn.put("StackedandLineCount",  mapper.writeValueAsString(defPeriodicData));

								 
								 break;
						}		
								 
					}
					
					}// end monthly period
					
					// Abs. max/min
					if (StringUtils.equalsIgnoreCase(period,"null")) {						
						
							rtn.put("chartData",  0);
							rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
							rtn.put("StackedandLineCount", mapper.writeValueAsString(""));	
							rtn.put("regStatusList", mapper.writeValueAsString(""));							
							
								
					} // end null period 		
					
				} // end of selected agent

			    }
			} catch (Exception e) {
				logger.info("Error in creating session with Database", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
	        }
			return rtn;
	}
	 
		
		@SuppressWarnings("unchecked")
		@RequestMapping(value ="/FilteringMaxMinChart", method = RequestMethod.GET)
		@ResponseBody public Map<String, Object> FilteringMaxMinChart(Locale locale, Model model,
		   HttpServletRequest request, HttpServletResponse response) { 
		   //logger.info("Welcome home! The client locale is {}.", locale);
			
			Map<String, Object> rtn = new LinkedHashMap<>();
			ObjectMapper mapper = new ObjectMapper();
			
			LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
			List<Object> periodicData = new ArrayList<Object>();
			List<SimAgentCountChartsReport> salesCountLineCharts = new ArrayList<SimAgentCountChartsReport>();
			List<SimAgentCountChartsReport> countLineChartList = new ArrayList<SimAgentCountChartsReport>();
			Query periodicSalesCountQuery;
			String periodicSalesCount = "";
			
			String period = request.getParameter("period");
			String filterID = request.getParameter("filterID");
			String start_Date = request.getParameter("startDate");
			String end_Date = request.getParameter("endDate");						
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			Date createdDate = null;
			Date enddate = null;
			Timestamp startDate;
			Timestamp endDate;
										
				   
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", "redirect:/");
				return rtn;
			}
		 else {   
		   Session session = null;
		   Transaction tx = null;
		   
		   try { 
			   	createdDate = formatter.parse(start_Date);
				startDate = new Timestamp(createdDate.getTime());
				enddate = formatter.parse(end_Date);
				endDate = new Timestamp(enddate.getTime());
		
			    session =almsessions.getSession();
			    
			    if (session != null && session.isOpen()) {
			    	tx = session.beginTransaction();					
				
			    	if (StringUtils.equalsIgnoreCase(period, "Daily")) {
							
							switch (filterID) {
							
							 case "maxStackedChartApplyFilter": case "maxLineChartApplyFilter":
								 
								 periodicSalesCount = "select created_date,MAX(dailySalesCount) from(SELECT DISTINCT a.agent_id as agent_id ,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as dailySalesCount FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  " + 
								          " GROUP BY  TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.FULL_NAME ,a.MSISDN order by TO_CHAR(b.created_date, 'YYYY-MM-DD') ASC ) GROUP BY created_date ORDER BY created_date DESC " ;
								       
							 break;
							 
							 case "minStackedChartApplyFilter":case "minLineChartApplyFilter":
								 
								 periodicSalesCount = "select created_date,MIN(dailySalesCount) from(SELECT DISTINCT a.agent_id as agent_id ,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, TO_CHAR(b.created_date, 'YYYY-MM-DD') as created_date, COUNT(a.msisdn) as dailySalesCount FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  " + 
								          " GROUP BY  TO_CHAR(b.created_date, 'YYYY-MM-DD'),a.agent_id,a.FULL_NAME ,a.MSISDN order by TO_CHAR(b.created_date, 'YYYY-MM-DD') ASC ) GROUP BY created_date ORDER BY created_date DESC " ;
							break;
							
							}// end switch
							
						  switch (filterID) {

						  case "maxStackedChartApplyFilter": case "maxLineChartApplyFilter":
						    	periodicSalesCountQuery = session.createSQLQuery(periodicSalesCount);
						    	periodicSalesCountQuery.setParameter("param1", startDate);
						    	periodicSalesCountQuery.setParameter("param2", endDate);
								
								
								if (periodicSalesCountQuery.list().size()>0) {
									periodicData = getStackedChartData(periodicSalesCountQuery.list());
									rtn.put("maxStackedandLineCount", mapper.writeValueAsString(periodicData));
								}
								else{
									rtn.put("maxStackedandLineCount", mapper.writeValueAsString(""));
								}
						break;
						
						  case "minStackedChartApplyFilter":case "minLineChartApplyFilter":
						    	periodicSalesCountQuery = session.createSQLQuery(periodicSalesCount);
						    	periodicSalesCountQuery.setParameter("param1", startDate);
						    	periodicSalesCountQuery.setParameter("param2", endDate);
								
								
								if (periodicSalesCountQuery.list().size()>0) {
									periodicData = getStackedChartData(periodicSalesCountQuery.list());
									rtn.put("minStackedandLineCount", mapper.writeValueAsString(periodicData));
								}
								else{
									rtn.put("minStackedandLineCount", mapper.writeValueAsString(""));
								}
						break;
						 
						
						    
						}// end switch 	
					}// end Daily period 
						
						// Weekly period
			    	else if (StringUtils.equalsIgnoreCase(period, "Weekly")) {
							
							double diff =(endDate.getTime() - startDate.getTime()) / 1000.0;
					   	 	diff /= (60 * 60 * 24 * 7);
					   	 	diff =  Math.abs(Math.ceil(diff));
					   	 	
					   	 	String date = "";
					   	 	Date weeklyDate;
					   	 	Timestamp weeklyEndDate = startDate;
						 	Calendar cal = Calendar.getInstance();  
							Timestamp weeklyStartDate = startDate;

							switch (filterID) {
							
							 case "maxStackedChartApplyFilter": case "maxLineChartApplyFilter":
								 
								 periodicSalesCount=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
					                	  " select salesCount,full_name, RANK() over(order by salesCount desc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE  b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'   "+
						                  "GROUP BY a.full_name order by salesCount desc)" + 
						                  ") t where RANK = 1 order by '"+date+"' desc";	   			            	 	 				        	 		
				            	
								
							//	 periodicSalesCount = "select coalesce(MAX(dailySalesCount),0) as \"totalCount\", '"+date+"' as \"Date\"  from(SELECT DISTINCT a.agent_id as agent_id ,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, COUNT(a.msisdn) as dailySalesCount FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  " + 
								//		     " GROUP BY a.agent_id,a.FULL_NAME ,a.MSISDN ) ORDER BY \"Date\" DESC " ;
								 
								  		  
							 break;
							 
							 case "minStackedChartApplyFilter": case "minLineChartApplyFilter":
								 
								 periodicSalesCount=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
					                	  " select salesCount,full_name, RANK() over(order by salesCount asc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'   "+
						                  "GROUP BY a.full_name order by salesCount asc)" + 
						                  ") t where RANK = 1 order by '"+date+"' desc";
								 
								// periodicSalesCount = "select coalesce(MIN(dailySalesCount),0) as \"totalCount\", '"+date+"' as \"Date\"  from(SELECT DISTINCT a.agent_id as agent_id ,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, COUNT(a.msisdn) as dailySalesCount FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  " + 
									//	     " GROUP BY a.agent_id,a.FULL_NAME ,a.MSISDN )  ORDER BY \"Date\" DESC " ;
						
								 break;
						
							}// end switch
							switch (filterID) {
						    
					 	   	
						    case "maxStackedChartApplyFilter":case "maxLineChartApplyFilter":
							    
						    	 weeklyStartDate = startDate;
									for( int i = 0; i< diff; i++) {
										weeklyEndDate = weeklyStartDate;
										cal.setTime(weeklyEndDate); 
										cal.add(Calendar.DAY_OF_MONTH, 6); // 6 is the days to add 
										weeklyDate = cal.getTime();
									 	weeklyEndDate = new Timestamp(weeklyDate.getTime());
									 	
									 	if ((weeklyEndDate).compareTo(endDate) > 0) {
									 		weeklyEndDate = endDate;
									 	}
									 	
									 	date =   (weeklyStartDate.getYear()+1900)+"/"+ (weeklyStartDate.getMonth() +1)+"/"+(weeklyStartDate.getDate())+"-"+  (weeklyEndDate.getYear()+1900)+"/"+ (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getDate());
									 	
									 	periodicSalesCountQuery = session.createSQLQuery(periodicSalesCount);
									 	periodicSalesCountQuery.setParameter("param1", weeklyStartDate);
									 	periodicSalesCountQuery.setParameter("param2", weeklyEndDate);
									 	
							    	
									 	salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) periodicSalesCountQuery)
								         	.addScalar("totalCount").addScalar("Date")
								         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
											
									 	salesCountLineCharts.get(0).setDate(date);		
									 	countLineChartList.addAll(salesCountLineCharts);
										
										weeklyStartDate = weeklyEndDate;
										cal.setTime(weeklyStartDate);
										cal.add(Calendar.DAY_OF_MONTH, 1); // 1 is the day to add
										weeklyDate = cal.getTime();
										weeklyStartDate = new Timestamp(weeklyDate.getTime());
										
									}
									
									periodicData = getPeriodicStackedChartData(countLineChartList);
									
									rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(periodicData));
									
						 	   	
						 	   	break;
						 	   	
						    case "minStackedChartApplyFilter":case "minLineChartApplyFilter":
							    
						    	weeklyStartDate = startDate;
								for( int i = 0; i< diff; i++) {
									weeklyEndDate = weeklyStartDate;
									cal.setTime(weeklyEndDate); 
									cal.add(Calendar.DAY_OF_MONTH, 6); // 6 is the days to add 
									weeklyDate = cal.getTime();
								 	weeklyEndDate = new Timestamp(weeklyDate.getTime());
								 	
								 	if ((weeklyEndDate).compareTo(endDate) > 0) {
								 		weeklyEndDate = endDate;
								 	}
								 	
								 	date =   (weeklyStartDate.getYear()+1900)+"/"+ (weeklyStartDate.getMonth() +1)+"/"+(weeklyStartDate.getDate())+"-"+  (weeklyEndDate.getYear()+1900)+"/"+ (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getDate());
								 	
								 	periodicSalesCountQuery = session.createSQLQuery(periodicSalesCount);
								 	periodicSalesCountQuery.setParameter("param1", weeklyStartDate);
								 	periodicSalesCountQuery.setParameter("param2", weeklyEndDate);
								 	
						    	
								 	salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) periodicSalesCountQuery)
							         	.addScalar("totalCount").addScalar("Date")
							         	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
										
								 	salesCountLineCharts.get(0).setDate(date);										 	
								 	countLineChartList.addAll(salesCountLineCharts);
									
									weeklyStartDate = weeklyEndDate;
									cal.setTime(weeklyStartDate);
									cal.add(Calendar.DAY_OF_MONTH, 1); // 1 is the day to add
									weeklyDate = cal.getTime();
									weeklyStartDate = new Timestamp(weeklyDate.getTime());
									
								}
								
								periodicData = getPeriodicStackedChartData(countLineChartList);
								
								rtn.put("minStackedandLineCount",  mapper.writeValueAsString(periodicData));
					 	   	    		
							break;
							}// end switch
						
						}// end weekly period
						
						// Monthly period
			    	else if (StringUtils.equalsIgnoreCase(period, "Monthly")) {
							Calendar m =Calendar.getInstance();
							m.setTime(startDate);
							int nMonth1=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
							m.setTime(endDate);
							int nMonth2=12*m.get(Calendar.YEAR)+m.get(Calendar.MONTH);
							int diff = Math.abs(nMonth2-nMonth1);
							Timestamp month = startDate;
						    String date = "";
							
							switch (filterID) {
							
							case "maxStackedChartApplyFilter": case "maxLineChartApplyFilter":
							
								periodicSalesCount=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
					                	  " select salesCount,full_name, RANK() over(order by salesCount desc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'   "+
						                  "GROUP BY a.full_name order by salesCount desc)" + 
						                  ") t where RANK = 1 order by '"+date+"' desc";	   
								
							//	periodicSalesCount = "select coalesce(MAX(dailySalesCount),0) as \"totalCount\", '"+date+"' as \"Date\"  from(SELECT DISTINCT a.agent_id as agent_id ,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, COUNT(a.msisdn) as dailySalesCount FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  " + 
										  //   " GROUP BY a.agent_id,a.FULL_NAME ,a.MSISDN ) ORDER BY \"Date\" DESC " ;
								break;
								
							case "minStackedChartApplyFilter":case "minLineChartApplyFilter":
								
								periodicSalesCount=  "select '"+date+"' as \"Date\" ,coalesce(sum(t.salesCount),0)  as \"totalCount\" from ( " + 
					                	  " select salesCount,full_name, RANK() over(order by salesCount asc) as RANK from " + 
						                  "(SELECT DISTINCT a.FULL_NAME as full_name, COUNT(a.msisdn) as salesCount  " + 
						                  "FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE b.created_date >= :param1 AND b.created_date <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'   "+
						                  "GROUP BY a.full_name order by salesCount asc)" + 
						                  ") t where RANK = 1 order by '"+date+"' desc";	   
								
							//	periodicSalesCount = "select coalesce(MIN(dailySalesCount),0) as \"totalCount\", '"+date+"' as \"Date\"  from(SELECT DISTINCT a.agent_id as agent_id ,a.FULL_NAME as FULL_NAME,a.MSISDN as MSISDN, COUNT(a.msisdn) as dailySalesCount FROM agent a INNER JOIN CLIENTS b ON a.msisdn =b.agent_number WHERE TRUNC( b.created_date) >= :param1 AND TRUNC(b.created_date) <= :param2 and b.status !='DEACTIVATED' AND b.status !='CANCELLED'  " + 
											//" GROUP BY a.agent_id,a.FULL_NAME ,a.MSISDN ) ORDER BY \"Date\" DESC " ;
						
								 break;
							}// end switch
							switch (filterID) {
							
						    case "maxStackedChartApplyFilter":case "maxLineChartApplyFilter":
						    	
						    	for( int i = 0; i<= diff; i++) {
						    		
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
										
									if ((endOfMonth).compareTo(endDate) > 0) {
										endOfMonth =  endDate;
										}
									date = (monthofDate.getYear()+1900) +"/"+ (monthofDate.getMonth() +1) +"/"+(monthofDate.getDate())+"-" + (monthofDate.getYear()+1900)+"/"+ (endOfMonth.getMonth() +1) +"/"+ (endOfMonth.getDate());

									periodicSalesCountQuery = session.createSQLQuery(periodicSalesCount);
								 	periodicSalesCountQuery.setParameter("param1", monthofDate);
								 	periodicSalesCountQuery.setParameter("param2", endOfMonth);
										
										
									salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) periodicSalesCountQuery)
								  	.addScalar("totalCount").addScalar("Date")
								  	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
										
									salesCountLineCharts.get(0).setDate(date);		
									countLineChartList.addAll(salesCountLineCharts);
									
									calendar.setTime(endOfMonth);  
								    calendar.add(Calendar.DATE, +1);  
								    Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
								    month = NextDayOfMonth;										
									}
									
																		
						    	periodicData = getPeriodicStackedChartData(countLineChartList);
									
									rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(periodicData));						 	   	
									
									break;
						 	   	
						    case "minStackedChartApplyFilter":case "minLineChartApplyFilter":
						    	
						    	for( int i = 0; i<= diff; i++) {
						    		
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
										
									if ((endOfMonth).compareTo(endDate) > 0) {
										endOfMonth =  endDate;
										}
									date = (monthofDate.getYear()+1900) +"/"+ (monthofDate.getMonth() +1) +"/"+(monthofDate.getDate())+"-" + (monthofDate.getYear()+1900)+"/"+ (endOfMonth.getMonth() +1) +"/"+ (endOfMonth.getDate());

									periodicSalesCountQuery = session.createSQLQuery(periodicSalesCount);
								 	periodicSalesCountQuery.setParameter("param1", monthofDate);
								 	periodicSalesCountQuery.setParameter("param2", endOfMonth);
										
										
									salesCountLineCharts = (List<SimAgentCountChartsReport>) ((SQLQuery) periodicSalesCountQuery)
								  	.addScalar("totalCount").addScalar("Date")
								  	.setResultTransformer(Transformers.aliasToBean(SimAgentCountChartsReport.class)).list();
										
									salesCountLineCharts.get(0).setDate(date);		
									countLineChartList.addAll(salesCountLineCharts);
									
									calendar.setTime(endOfMonth);  
								    calendar.add(Calendar.DATE, +1);  
								    Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); 
								    month = NextDayOfMonth;										
									}  
								
															
						    	periodicData = getPeriodicStackedChartData(countLineChartList);
								
								rtn.put("minStackedandLineCount",  mapper.writeValueAsString(periodicData));
								
					 	   	    		
						 	   	
							break;
							}// end switch
						
						}// end Monthly period
			    	else if (StringUtils.equalsIgnoreCase(period,"null") || StringUtils.equalsIgnoreCase(period,"Accu")) {						
						
			    		rtn.put("minStackedandLineCount",  mapper.writeValueAsString(""));
			    		rtn.put("maxStackedandLineCount",  mapper.writeValueAsString(""));
	
				} // end null period 	
						
					
			    }
			} catch (Exception e) {
				logger.info("Error in creating session with Database", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		   }
			return rtn;
	}
		// Area AutoComplete
				@SuppressWarnings("unchecked")
				@RequestMapping(value ="/GetAgentAreaAutocomplete", method = RequestMethod.GET)
				@ResponseBody public Map<String, Object>GetAgentAreaAutocomplete(Locale locale, Model model,
					HttpServletRequest request, HttpServletResponse response) {
					
					
					Map<String, Object> rtn = new LinkedHashMap<>(); 
					String areaid= "%" + request.getParameter("areaID") + "%";	
					String agentID= request.getParameter("agentID") ;
					String regionID= request.getParameter("regionID") ;	
					Query areaListQuery =null;
					ObjectMapper mapper = new ObjectMapper();
					    
					if (LoginServices.checkSession(request, response).equals("redirect:/")) {
						rtn.put("Login", "redirect:/");
						return rtn;
					}
				 else {
			        Session session = null;
			        Transaction tx = null;
			       
				    try { 
					 session = almsessions.getSession();
					 
					 if (session != null && session.isOpen()) {
						 tx = session.beginTransaction();					
					
						if ( agentID == "" && regionID =="" ) {
							 areaListQuery = session.createSQLQuery("SELECT  distinct AREA_ID,AREA_NAME FROM area where UPPER(AREA_NAME)like UPPER(:param1) ");
						}
						
						else if ( agentID == "" && regionID !="" ) {
							 areaListQuery = session.createSQLQuery("SELECT  distinct AREA_ID,AREA_NAME FROM area where UPPER(AREA_NAME)like UPPER(:param1) and REGION_ID = '"+regionID+"' ");
						}
						
						else if ( agentID != "" && regionID =="" ) {
							 areaListQuery = session.createSQLQuery("SELECT distinct AREA_ID,AREA_NAME FROM AGENT_AREAS where UPPER(AREA_NAME)like UPPER(:param1) and AGENT_ID = '"+agentID+"'  ");
						}
						
						// agentID and regionID are both selected
						else {
							areaListQuery= session.createSQLQuery("SELECT * FROM (SELECT distinct a.AREA_ID as AREA_ID,a.AREA_NAME as AREA_NAME,b.REGION_ID as REGION_ID,c.AGENT_ID as AGENT_ID FROM AGENT_AREAS a inner join AGENT_REGIONS b on a.agent_id = b.agent_id inner join AGENT C  on b.agent_id = c.agent_id where UPPER(a.AREA_NAME)like UPPER(:param1)) WHERE REGION_ID = '"+regionID+"' AND AGENT_ID = '"+agentID+"' ");
		
						}
						
					
						areaListQuery.setParameter("param1", areaid);
						areaListQuery.setFirstResult(0);
						areaListQuery.setMaxResults(40);
						
						rtn.put("listAreas", areaListQuery.list()); 

					
				    }
				    }
				    catch (Exception e) {
						   logger.info("Error at <T>  creating session with the DataBase " +e.getMessage() );

						   }
				    
				    finally {
						   if (session != null && session.isOpen()) {
						   tx.commit();
						   session.close();
						   }
						   }
				 }	  
						  return rtn; 
						 
						 }	 
				// Region AutoComplete
				@SuppressWarnings("unchecked")
				@RequestMapping(value ="/GetAgentRegionAutocomplete", method = RequestMethod.GET)
				@ResponseBody public Map<String, Object>GetAgentRegionAutocomplete(Locale locale, Model model,
					HttpServletRequest request, HttpServletResponse response){
					
					//logger.info("Welcome home! The client locale is {}.", locale);
					Query regionListQuery =null;
					Map<String, Object> rtn = new LinkedHashMap<>(); 
				    ObjectMapper mapper = new ObjectMapper();
				    String regionID= "%" + request.getParameter("Region") + "%";	
					String agentID= request.getParameter("agentID") ;
					String areaID= request.getParameter("areaID") ;	
					
					
					 if (LoginServices.checkSession(request, response).equals("redirect:/")) {
							rtn.put("Login", "redirect:/");
							return rtn;
						}
					 else {
				        Session session = null;
				        Transaction tx = null;
				    
				    try { 
						session = almsessions.getSession();
						
						if (session != null && session.isOpen()) {
							
							tx = session.beginTransaction();					
							
							if ( agentID == "" && areaID =="" ) {
								regionListQuery = session.createSQLQuery("SELECT distinct REGION_ID,REGION_NAME,REGION_CODE FROM REGION where UPPER(REGION_NAME)like UPPER(:param1) ");
							}
							
							else if ( agentID == "" && areaID !="" ) {
								regionListQuery = session.createSQLQuery("SELECT distinct a.REGION_ID,a.REGION_NAME,a.REGION_CODE FROM region a inner join area b on a.region_id = b.region_id  where UPPER(a.REGION_NAME)like UPPER(:param1) and b.AREA_ID = '"+areaID+"' ");
							}
							
							else if ( agentID != "" && areaID =="" ) {
								
								regionListQuery = session.createSQLQuery("SELECT distinct REGION_ID,REGION_NAME,REGION_CODE FROM AGENT_REGIONS where UPPER(REGION_NAME)like UPPER(:param1) and AGENT_ID = '"+agentID+"' ");
							}
							// agentID and areaID are both selected
							else {
		
								regionListQuery= session.createSQLQuery("SELECT * FROM (SELECT distinct a.REGION_ID as REGION_ID,a.REGION_NAME as REGION_NAME,a.REGION_CODE as REGION_CODE,b.AREA_ID as AREA_ID,c.AGENT_ID as AGENT_ID FROM AGENT_AREAS b inner join AGENT_REGIONS a on b.agent_id = a.agent_id inner join AGENT C  on a.agent_id = c.agent_id where UPPER(a.REGION_NAME)like UPPER(:param1)) WHERE AREA_ID = '"+areaID+"' AND AGENT_ID = '"+agentID+"' ");
			
							}
							
							regionListQuery.setParameter("param1", regionID);
							regionListQuery.setFirstResult(0);
							regionListQuery.setMaxResults(40);
		
							rtn.put("listRegions", regionListQuery.list()); 
				    }
				    }
				    catch (Exception e) {
						   logger.info("Error at <T>  creating session with the DataBase " +e.getMessage() );

						   }
				    
				    finally {
						   if (session != null && session.isOpen()) {
						   tx.commit();
						   session.close();
						   }
						   }
					 }	  
						  return rtn; 
						 
						 }	 
	 
		// Method for the line and StackChart weekly and monthly
		@SuppressWarnings({ "unchecked" })
		private JSONArray getPeriodicStackedChartData(List<SimAgentCountChartsReport> countChartList) throws JsonProcessingException { //List<Object[]> 

			JSONArray totalCountArrFnl = new JSONArray();
			JSONArray obj = new JSONArray(),startDateArrOriginal = new JSONArray(),endDateArrOriginal = new JSONArray(),DateArrFnl = new JSONArray();
			JSONObject json;
			
			json = new JSONObject();
		    ObjectMapper mapper = new ObjectMapper();
			

		    SimAgentCountChartsReport getObj;
			if (!countChartList.isEmpty()) {
				for (int i = 0; i<countChartList.size(); i++) {
					getObj = (SimAgentCountChartsReport) countChartList.get(i);
					json = new JSONObject();
					
					json.put("label",getObj.getDate());
					DateArrFnl.add(json);
					
					json = new JSONObject();
					json.put("value",getObj.getTotalCount());
					totalCountArrFnl.add(json);
												
				//}
				}
				DateArrFnl = (((ArrayList<?>) DateArrFnl).isEmpty()) == true ? null : DateArrFnl;
			}

			obj.add(DateArrFnl);
			obj.add(totalCountArrFnl.isEmpty() ? 0 : totalCountArrFnl);
			
			return obj;

		}	
		
		
		@SuppressWarnings({ "unchecked", "null" })
		private JSONArray chartSales(List<Object> defaultChartAgentSalesReport) {
			JSONArray simSalesCount = new JSONArray(),agentsID = new JSONArray();
			JSONArray objSalesCount = new JSONArray();
			JSONObject  json;
			
			if (defaultChartAgentSalesReport != null && defaultChartAgentSalesReport.size() != 0) {
				List<Object> c = defaultChartAgentSalesReport;
				Object[] fof;
				for (int i = 0; i < c.size(); i++) {
					fof = (Object[]) c.get(i);
					
					if (fof[0] != null) {
						json = new JSONObject();
						json.put("label",StringUtils.equalsIgnoreCase((String) fof[0], "null") ? "No AGENT" :fof[0]);
						agentsID.add(json);
					} else {
						json = new JSONObject();
						json.put("label","No Data");
						
						agentsID.add(json);
					}

					if (fof[1] != null) {
						json = new JSONObject();
						json.put("value",fof[1]);
						simSalesCount.add(json);

					} else {
						json = new JSONObject();
						json.put("value",0);
						simSalesCount.add(json);

					}
										
				}
			} else {
				json = new JSONObject();
				json.put("label","No Data");
				agentsID.add(json);
				json = new JSONObject();
				json.put("value",0);
				simSalesCount.add(json);
				
			}

			objSalesCount.add(agentsID);
			objSalesCount.add(simSalesCount);
			
			return objSalesCount;
		}
	 
		@SuppressWarnings({ "unchecked" })
		private JSONArray getStackedChartData(List<Object[]> perDataChrtLst) {
			JSONObject json;
			JSONArray createdDate = new JSONArray(), totalSimSales = new JSONArray();
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
					totalSimSales.add(json);
					
					

				}
				startDateArrOriginal = (((ArrayList<?>) startDateArrOriginal).isEmpty()) == true ? null
						: startDateArrOriginal;
			}

			obj.add(startDateArrOriginal);
			obj.add(totalSimSales);
			
			return obj;

		}
		
		// need optimization
		private List<Float> PieSalesPerAgent(List<Object[]> siteResult) {
			
			float firstRangeCount = 0, secondRangeCount = 0, thirdRangeCount = 0, fourthRangeCount = 0,fifthRangeCount=0;

			float totCount;
			List<Float> ranges = new ArrayList<Float>();
			if (siteResult != null && siteResult.size() != 0) {
				
				
				
				for (Object[] obj : siteResult) {
					

					if(((BigDecimal) obj[1]).intValue()>= 0 && ((BigDecimal) obj[1]).intValue() <= 25) {
						firstRangeCount +=1.00;
							//	((BigDecimal)obj[1]).floatValue();
					}
				
					else if(((BigDecimal) obj[1]).intValue() >= 26 && ((BigDecimal) obj[1]).intValue()  <=50 ) {
						secondRangeCount += 1.00;
						//((BigDecimal)obj[1]).floatValue();
					}
					else if(((BigDecimal) obj[1]).intValue() >=51 && ((BigDecimal) obj[1]).intValue() <=75 ) {
						thirdRangeCount += 1.00;
						//((BigDecimal)obj[1]).floatValue();
						}
					else if(((BigDecimal) obj[1]).intValue() >=76 && ((BigDecimal) obj[1]).intValue() <=100 ) {
						fourthRangeCount += 1.00;
						//((BigDecimal)obj[1]).floatValue();
						}
					else if(((BigDecimal) obj[1]).intValue()>=101 ) {
						fifthRangeCount +=1.00;
								//((BigDecimal)obj[1]).floatValue();
						}
					
				}
				totCount = firstRangeCount + secondRangeCount + thirdRangeCount +fourthRangeCount+fifthRangeCount;

				if (totCount == 0) {
					firstRangeCount = 100;
					secondRangeCount = 0;
					thirdRangeCount = 0;
					fourthRangeCount=0;
					fifthRangeCount=0;

				} else {

					firstRangeCount = (firstRangeCount / totCount) * 100;
					secondRangeCount = (secondRangeCount / totCount) * 100;
					thirdRangeCount = (thirdRangeCount / totCount) * 100;
					fourthRangeCount = (fourthRangeCount / totCount) * 100;
					fifthRangeCount = (fifthRangeCount / totCount) * 100;


				}
			} else {

				firstRangeCount = 100;
				secondRangeCount = 0;
				thirdRangeCount = 0;
				fourthRangeCount=0;
				fifthRangeCount=0;
				totCount = 0;
			}
			
			ranges.add(firstRangeCount);
			ranges.add(secondRangeCount);
			ranges.add(thirdRangeCount);
			ranges.add(fourthRangeCount);
			ranges.add(fifthRangeCount);
			ranges.add(totCount);

			return ranges;
		}
	// need optimization can be opmitized	
	private List<Float> PieStatusPerClient(List<Object[]> siteResult) {
			
			float ussdReg = 0, ipReg = 0,count=0;

			List<Float> registration = new ArrayList<Float>();
			if (siteResult != null && siteResult.size() != 0) {
				
				
				for (Object[] obj : siteResult) {
					
					
					if (StringUtils.equalsIgnoreCase((CharSequence) obj[0], "USSD") ) {
						
						ipReg =((BigDecimal)obj[1]).floatValue();
					}
					else if(StringUtils.equalsIgnoreCase((CharSequence) obj[0], "SENT_USSD") ) {
						ussdReg =((BigDecimal)obj[1]).floatValue();
					}
					
				}
				
				count = ussdReg +ipReg;

				if (count == 0) {
					ussdReg = 100;
					ipReg = 0;
				

				} else {

					ussdReg = (ussdReg / count) * 100;
					ipReg = (ipReg / count) * 100;
					

				}
			} else {

				ussdReg = 100;
				ipReg = 0;
				count = 0;
			}
			
			
			registration.add(ussdReg);
			registration.add(ipReg);
			registration.add(count);

			return registration;
		}

	
	}

		

