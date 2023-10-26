package com.aliat.alm.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import com.aliat.alm.models.FinancialReport;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;


	@Controller
	public class FinancialReportController {

		 @Autowired
		 ALMSessions almsessions;
		 
		 @Autowired
		 Notify notifications;
		 
		 private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
		 
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/FinancialReport", method = RequestMethod.GET)
		public String SimSales(Locale locale, Model model,HttpServletRequest request,HttpServletResponse response) {			
			ObjectMapper mapper = new ObjectMapper();			

			if(LoginServices.checkSession(request, response).equals("redirect:/")) {
				 return "redirect:/";
			}
			else {
			    Session sessionALM =null;
		        Transaction txALM=null ;
		        sessionALM = almsessions.getSession();
		        Query query;
				Object[] result;
				DecimalFormat df = new DecimalFormat("#,###.00");
				String totalInitialCost = "0.0";
				String totalAccumdepr = "0.0";
				String totalNetCost = "0.0";


		        if (sessionALM != null && sessionALM.isOpen()) {
		        	 
		        		  txALM = sessionALM.beginTransaction();
		 		       	  notifications.headerNotifications(sessionALM, model);
		 		         try { 
		 		        	query = sessionALM.createSQLQuery("SELECT farID, itemCode, itemName, lastModifiedDate,itemSN,itemNameRegister,poID,siteID,siteName,longitude,latitude,initCost,netCost,accuDepr FROM ( "
							           + " SELECT A.FAR_ID as farID, A.ITEM_CODE as itemCode, A.ITEM_NAME as itemName,TO_CHAR(A.LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate, A.ITEM_SN as itemSN,A.ITEM_NAME_REGISTER as itemNameRegister, A.PO_ID as poID, B.SITE_ID AS siteID, B.SITE_NAME AS siteName , C.LONGITUDE as longitude,C.LATITUDE as latitude, A.INITIALCOST as initCost, A.NETCOST as netCost , A.ACCUMULDEPRECAMNT as accuDepr , "
							           + " ROW_NUMBER() OVER (PARTITION BY A.FAR_ID ORDER BY B.SITE_ID DESC) AS rn FROM FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID "
							           + " WHERE A.CREATED_DATE >=  trunc(SYSDATE - INTERVAL '1' YEAR) AND A.created_date < (trunc(sysdate) ) + 1   ) WHERE rn = 1 ORDER BY lastModifiedDate DESC ");
		 		        	
		 		        	 
					         @SuppressWarnings("unchecked")
					     	List<FinancialReport> ListFAR = (List<FinancialReport>)  ((SQLQuery) query)
									.addScalar("farID").addScalar("itemCode").addScalar("itemName")
					       			.addScalar("lastModifiedDate").addScalar("itemSN")
					       			.addScalar("itemNameRegister").addScalar("poID").addScalar("siteID").addScalar("siteName").addScalar("longitude").addScalar("latitude").addScalar("initCost")
					       			.addScalar("netCost").addScalar("accuDepr")
					       			.setResultTransformer(Transformers.aliasToBean(FinancialReport.class)).list();
		 		          model.addAttribute("financialReportList",mapper.writeValueAsString(ListFAR));	
		 		         
		 		          
		 		       
		 		         query = sessionALM.createSQLQuery("Select SUM(INITIALCOST) as initialCost, SUM(ACCUMULDEPRECAMNT) as AccumDepr, SUM(NETCOST) as netCost from FIXED_ASSET_REGISTRY "
						           + " WHERE CREATED_DATE >=  trunc(SYSDATE - INTERVAL '1' YEAR) AND created_date < (trunc(sysdate) ) + 1    ");
						 
		 		        result = (Object[]) query.uniqueResult();						
						if(result[0] != null)
							totalInitialCost = df.format(new BigDecimal(result[0].toString()));
						if(result[1] != null)
							totalAccumdepr = df.format(new BigDecimal(result[1].toString()));
						if(result[2] != null)
							totalNetCost = df.format(new BigDecimal(result[2].toString()));
							
						 model.addAttribute("totalInitialCostFetched",totalInitialCost);
						 model.addAttribute("totalAccumdeprFetched",totalAccumdepr);
						 model.addAttribute("totalNetCostFetched",totalNetCost);
						 
						 
						//Get the total of initial,net cost and accumulated depr of all FAR records
						 query = sessionALM.createSQLQuery("Select SUM(INITIALCOST) as initialCost, SUM(ACCUMULDEPRECAMNT) as AccumDepr, SUM(NETCOST) as netCost from fixed_asset_registry t");
						 result = (Object[]) query.uniqueResult();						
						if(result[0] != null)
							totalInitialCost = df.format(new BigDecimal(result[0].toString()));
						if(result[1] != null)
							totalAccumdepr = df.format(new BigDecimal(result[1].toString()));
						if(result[2] != null)
							totalNetCost = df.format(new BigDecimal(result[2].toString()));
						
						 model.addAttribute("totalInitialCost",totalInitialCost);
						 model.addAttribute("totalAccumdepr",totalAccumdepr);
						 model.addAttribute("totalNetCost",totalNetCost);
		 		         
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
				return "Reports/FinancialReport";
		}

		
		
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/GenerateGridFinancialReport", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> GenerateGridFinancialReport(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) {

			Map<String, Object> rtn = new LinkedHashMap<>();
			String itemCode = request.getParameter("itemCode");
			String itemName = request.getParameter("itemName");
			String itemModel = request.getParameter("itemModel");
			String itemPartNo = request.getParameter("itemPartNo");
			String ignoreDate = request.getParameter("ignoreDate");
			String domainOptions = request.getParameter("Domain");
			String subDomainOptions = request.getParameter("subDomain");
			String vendorOptions = request.getParameter("Vendor");
			String typeOptions = request.getParameter("Type");
			String strtEndCheckbox = request.getParameter("strtEndCheckbox");

			String domain= "";
			String subDomain= "";
			String vendor= "";
			String type= "";
			String[] strList = null;
			String selectedOption;
			
			// Get domain options in a String format 
			if (StringUtils.equalsIgnoreCase(domainOptions, "") || StringUtils.equalsIgnoreCase(domainOptions, null)) {
				domain = "";
			} 
			else {
				selectedOption = domainOptions.concat(",");
				// Split the string  on comma to get each domain separately
				strList = selectedOption.split(",");
				StringBuilder allDomainOption = new StringBuilder();

				// Append to each element '' and comma to satisfy the format of IN parameters
				for (int i = 0; i < strList.length; i++) {

					allDomainOption.append("'" + strList[i] + "',");
				}

				domain = allDomainOption.toString();
				domain = domain.substring(0, domain.length() - 1);
			}
			
			// Get subdomain options in a String format 
			if (StringUtils.equalsIgnoreCase(subDomainOptions, "") || StringUtils.equalsIgnoreCase(subDomainOptions, null)) {
				subDomain = "";
			} 
			else {
				
				selectedOption = subDomainOptions.concat(",");
				// Split the string on comma to get each subdomain separately
				strList = selectedOption.split(",");
				StringBuilder allSubDomainOption = new StringBuilder();

				// Append to each element '' and comma to satisfy the format of IN parameters
				for (int i = 0; i < strList.length; i++) {

					allSubDomainOption.append("'" + strList[i] + "',");
				}
				subDomain = allSubDomainOption.toString();
				subDomain = subDomain.substring(0, subDomain.length() - 1);
			}
			
			// Get vendor options in a String format 
			if (StringUtils.equalsIgnoreCase(vendorOptions, "") || StringUtils.equalsIgnoreCase(vendorOptions, null)) {
				vendor = "";
			} 
			else {
				
				selectedOption = vendorOptions.concat(",");
				// Split the string  on comma to get each vendor separately
				strList = selectedOption.split(",");
				StringBuilder allVendorOption = new StringBuilder();

				// Append to each element '' and comma to satisfy the format of IN parameters
				for (int i = 0; i < strList.length; i++) {
					allVendorOption.append("'" + strList[i] + "',");
				}
				vendor = allVendorOption.toString();
				vendor = vendor.substring(0, vendor.length() - 1);
			}
			
			// Get type options in a String format 
			if (StringUtils.equalsIgnoreCase(typeOptions, "") || StringUtils.equalsIgnoreCase(typeOptions, null)) {
				type = "";
			} 
			else {
				
				selectedOption = typeOptions.concat(",");
				// Split the string  on comma to get each type separately
				strList = selectedOption.split(",");
				StringBuilder allTypeOption = new StringBuilder();

				// Append to each element '' and comma to satisfy the format of IN parameters
				for (int i = 0; i < strList.length; i++) {
					allTypeOption.append("'" + strList[i] + "',");
				}
				type = allTypeOption.toString();
				type = type.substring(0, type.length() - 1);
			}			
		

			String start_Date = request.getParameter("startDate");
			String end_Date = request.getParameter("endDate");
			
			
			 DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");			 
			 Timestamp startDate;
			 Timestamp endDate;
			 Query q;
			 DecimalFormat df = new DecimalFormat("#,###.00");
			 String totalInitialCost = "0.0";
			 String totalAccumdepr = "0.0";
			 String totalNetCost = "0.0";
			 Object[] result;
			 String str ="",totalStr="",strTemp="";
			 
			 String startLong = request.getParameter("startLong");
			 String startLat = request.getParameter("startLat");
			 String endLong = request.getParameter("endLong");
			 String endLat = request.getParameter("endLat");
			 String longitude = request.getParameter("longitude");
			 String latitude = request.getParameter("latitude");
			 String radius = request.getParameter("radius");
			 double distance = 0.0;			 
			
			List<Object[]> listFARTemp = new ArrayList<Object[]>();
			List<Object[]> listCircleRange = new ArrayList<Object[]>();
			List<Object[]> listFAR = new ArrayList<Object[]>();


			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", "redirect:/");
				return rtn;
			}
			else {

				Session session = null;
				Transaction tx = null;

				try {
					 startDate = new Timestamp(formatter.parse(start_Date).getTime());
					 endDate = new Timestamp(formatter.parse(end_Date).getTime());						
					 session = almsessions.getSession();

					if (session != null && session.isOpen()) {

						tx = session.beginTransaction();
							
							
						
							 str = "SELECT farID, itemCode, itemName, lastModifiedDate,itemSN,itemNameRegister,poID,siteID,siteName,longitude,latitude,initCost,netCost,accuDepr FROM ( "
							           + " SELECT A.FAR_ID as farID, A.ITEM_CODE as itemCode, A.ITEM_NAME as itemName,TO_CHAR(A.LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate, A.ITEM_SN as itemSN,A.ITEM_NAME_REGISTER as itemNameRegister, A.PO_ID as poID,B.SITE_ID AS siteID, B.SITE_NAME AS siteName ,C.LONGITUDE as longitude,C.LATITUDE as latitude, A.INITIALCOST as initCost, A.NETCOST as netCost , A.ACCUMULDEPRECAMNT as accuDepr , "
							           + " ROW_NUMBER() OVER (PARTITION BY A.FAR_ID ORDER BY B.SITE_ID DESC) AS rn FROM FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID "
							           + " WHERE ( upper(A.ITEM_CODE) LIKE upper('%" + itemCode + "%') AND upper(A.ITEM_NAME) LIKE upper('%" + itemName + "%') AND upper(A.ITEM_MODEL) LIKE upper('%" + itemModel + "%') AND upper(A.ITEM_PART_NUMBER) LIKE upper ('%" + itemPartNo + "%') )  ";

							 
							 totalStr = "Select SUM(INITIALCOST) as initialCost, SUM(ACCUMULDEPRECAMNT) as AccumDepr, SUM(NETCOST) as netCost from FIXED_ASSET_REGISTRY "
					           + " WHERE ( upper(ITEM_CODE) LIKE upper('%" + itemCode + "%') AND upper(ITEM_NAME) LIKE upper('%" + itemName + "%') AND upper(ITEM_MODEL) LIKE upper('%" + itemModel + "%') AND upper(ITEM_PART_NUMBER) LIKE upper ('%" + itemPartNo + "%') )  ";

							 if (StringUtils.equalsIgnoreCase(ignoreDate, "false")) {
									str = str + " AND A.CREATED_DATE between TO_DATE('" + start_Date
											+ "','MM/DD/YYYY HH:MI AM') and TO_DATE('" + end_Date + "','MM/DD/YYYY HH:MI AM')";
									
									totalStr = totalStr + " AND CREATED_DATE between TO_DATE('" + start_Date
											+ "','MM/DD/YYYY HH:MI AM') and TO_DATE('" + end_Date + "','MM/DD/YYYY HH:MI AM')";
								
							}
							
							 
							 if (!StringUtils.equalsIgnoreCase(vendor, "")) {
								 str = str + " AND A.VENDOR IN ( "+vendor+ " )";
								 totalStr = totalStr + " AND VENDOR IN ( "+vendor+ " )";
							 }
							 if (!StringUtils.equalsIgnoreCase(domain, "")) {
								 str = str + " AND A.DOMAIN IN ( "+domain+ " )";
								 totalStr = totalStr + " AND DOMAIN IN ( "+domain+ " )";
							 }
							 if (!StringUtils.equalsIgnoreCase(subDomain, "")) {
								 str = str + " AND A.SUB_DOMAIN IN ( "+subDomain+ " )";
								 totalStr = totalStr + " AND SUB_DOMAIN IN ( "+subDomain+ " )";
							 }
							 if (!StringUtils.equalsIgnoreCase(type, "")) {
								 str = str + " AND A.TYPE IN ( "+type+ " )";
								 totalStr = totalStr + " AND TYPE IN ( "+type+ " )";
							 }
							 
							
							 
					//Include the start/end long and lat in where condition in case of strt/end coordinate checkbox is checked 
						if (StringUtils.equalsIgnoreCase(strtEndCheckbox, "true")) {
								
							//start longitude is entered && end longitude is empty
								 if (startLong != null && !startLong.equalsIgnoreCase("") && (endLong == null || endLong.equalsIgnoreCase(""))) {
										str = str + " and to_number(SUBSTR(C.LONGITUDE,1,6)) > " + startLong;
								 }
								 
								 //End longitude is entered && start longitude is empty
								 else if (endLong != null && !endLong.equalsIgnoreCase("") && (startLong == null || startLong.equalsIgnoreCase(""))) {
										str = str + " and to_number(SUBSTR(C.LONGITUDE,1,6)) < " + endLong;
								}
								 
								 //Start and end longitude are both entered
								 else if (startLong != null && endLong != null) {
									 String startLng;
									 String endLng;

									if (startLong != null && startLong.length() > 0 && (endLong != null && endLong.length() > 0)) {
										if (Double.parseDouble(startLong) < Double.parseDouble(endLong)) {
											startLng = startLong;
											endLng = endLong;
										} 
										else {
											startLng = endLong;
											endLng = startLong;
										}
											str = str + " and to_number(SUBSTR(C.LONGITUDE,1,6)) > " + startLng;
											str = str + " and to_number(SUBSTR(C.LONGITUDE,1,6)) < " + endLng;
									}
								}
								 
							
							//start latitude is entered && end latitude is empty 
							if (startLat != null && !startLat.equalsIgnoreCase("") && (endLat == null || endLat.equalsIgnoreCase(""))) {
								str = str + " and to_number(SUBSTR(C.LATITUDE,1,6)) > " + startLat;
							}
							
							//start latitude is empty && end latitude is entered 
							else if (endLat != null && !endLat.equalsIgnoreCase("") && (startLat == null || startLat.equalsIgnoreCase(""))) {
								str = str + " and to_number(SUBSTR(C.LATITUDE,1,6)) < " + endLat;
							}
							// start && end latitude are both entered 
							else if (startLat != null && endLat != null) {
								String startlatitude;
								String endLatitude;

								if (startLat != null && startLat.length() > 0 && (endLat != null && endLat.length() > 0)) {
									if (Double.parseDouble(startLat) < Double.parseDouble(endLat)) {
										startlatitude = startLat;
										endLatitude = endLat;

									} else {
										startlatitude = endLat;
										endLatitude = startLat;

									}
									str = str + " and to_number(SUBSTR(C.LATITUDE,1,6)) > " + startlatitude;
									str = str + " and to_number(SUBSTR(C.LATITUDE,1,6)) < " + endLatitude;
								}
							}	 
							 
				}// end of checked strt/end coordinate checkbox
						
						str = str + "  ) WHERE rn = 1 ORDER BY lastModifiedDate DESC  ";

						Query query = session.createSQLQuery(str);
						
						listFARTemp = query.list(); // To use it in circle range calculation

						listFAR =  ((SQLQuery) query)
								.addScalar("farID").addScalar("itemCode").addScalar("itemName")
				       			.addScalar("lastModifiedDate").addScalar("itemSN")
				       			.addScalar("itemNameRegister").addScalar("poID").addScalar("siteID").addScalar("siteName").addScalar("longitude").addScalar("latitude").addScalar("initCost")
				       			.addScalar("netCost").addScalar("accuDepr")
				       			.setResultTransformer(Transformers.aliasToBean(FinancialReport.class)).list();
									

						//Get the total of initial,net cost and accumulated depr of fetched FAR records
						q=session.createSQLQuery(totalStr);
						result = (Object[]) q.uniqueResult();						
						if(result[0] != null)
							totalInitialCost = df.format(new BigDecimal(result[0].toString()));
						if(result[1] != null)
							totalAccumdepr = df.format(new BigDecimal(result[1].toString()));
						if(result[2] != null)
							totalNetCost = df.format(new BigDecimal(result[2].toString()));
						
						rtn.put("totalInitialCostFetched",totalInitialCost);
						rtn.put("totalAccumdeprFetched",totalAccumdepr);
						rtn.put("totalNetCostFetched",totalNetCost);
						
						
						
						
						//If circle range is checked 
						if (( radius != null && !radius.equalsIgnoreCase("") && Double.parseDouble(radius) > 0) && (longitude != null && !longitude.equalsIgnoreCase("")) && (latitude != null
								&& !latitude.equalsIgnoreCase(""))) {

							for (int i = 0; i < listFAR.size(); i++) {
							
								distance = haversine(Double.parseDouble(longitude), Double.parseDouble(latitude),
										Double.valueOf(listFARTemp.get(i)[10].toString()),Double.valueOf(listFARTemp.get(i)[9].toString()));
							
								if (distance <= Double.parseDouble(radius)) {
									listCircleRange.add(listFARTemp.get(i));
								}
							}
					
							rtn.put("financialReportList", listCircleRange);
						}
						
						else {
							rtn.put("financialReportList", listFAR);
						}
						
						
						//Get the total of initial,net cost and accumulated depr of all FAR records
						 q = session.createSQLQuery("Select SUM(INITIALCOST) as initialCost, SUM(ACCUMULDEPRECAMNT) as AccumDepr, SUM(NETCOST) as netCost from fixed_asset_registry t");
						 result = (Object[]) q.uniqueResult();						
						if(result[0] != null)
							totalInitialCost = df.format(new BigDecimal(result[0].toString()));
						if(result[1] != null)
							totalAccumdepr = df.format(new BigDecimal(result[1].toString()));
						if(result[2] != null)
							totalNetCost = df.format(new BigDecimal(result[2].toString()));
						
						rtn.put("totalInitialCost",totalInitialCost);
						rtn.put("totalAccumdepr",totalAccumdepr);
						rtn.put("totalNetCost",totalNetCost);
						
						
						


							
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
		
		static double haversine(double latitude, double longitude, double pointLat, double pointLong) {
			
			// distance between latitudes and longitudes
			double dLat = Math.toRadians(pointLat - latitude);
			double dLon = Math.toRadians(pointLong - longitude);

			// convert to radians
			latitude = Math.toRadians(latitude);
			pointLat = Math.toRadians(pointLat);

			// apply formulae
			double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(latitude) * Math.cos(pointLat);
			double rad = 6371;
			double c = 2 * Math.asin(Math.sqrt(a));
			return rad * c;

		}		
		
		
	}

		

