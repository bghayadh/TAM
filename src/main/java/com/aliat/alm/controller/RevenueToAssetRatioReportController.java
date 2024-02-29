package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.logging.Logger;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.RevenueToAssetRatioReport;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class RevenueToAssetRatioReportController {
	
	private static final Logger logger = Logger.getLogger(RevenueToAssetRatioReportController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	private Date date = new Date();
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private String startDate = null;	
	
	@Autowired
	Notify notifications;	

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/RevenueToAssetRatioReport", method = RequestMethod.GET)
	public String RevenueToAssetRatioReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			
			try {
				session = AlmDbSession.getInstance().getSession();

			    if (session != null && session.isOpen()) {
						
			    	notifications.headerNotifications(session, model);
					tx = session.beginTransaction();
										
					if (formatter.format(date).toString().equals("29/02/2024")) {
						startDate = "SYSDATE - 1 - INTERVAL '1' YEAR";
					}
					else {
						startDate = "SYSDATE - INTERVAL '1' YEAR";
					}

					query = session.createNativeQuery(
							"SELECT site as site,wareID as wareID,siteID as siteID,siteName as siteName,longitude as longitude,latitude as latitude,"
							+"case (SUM(initCost)) when 0 then -1 when null then -1 else round (COALESCE (SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) * 100 / SUM(initCost),3) end as revenueToAssetInit,"
//							+"COALESCE( nullif(SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) / (nullif(SUM(initCost),0) * 100) ,0) as revenueToAssetInit, "
							+"case (SUM(netCost)) when 0 then -1 when null then -1 else round (COALESCE (SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) * 100 / SUM(netCost),3) end as revenueToAssetNet,"							
//							 +" COALESCE( (nullif(SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0)) / (nullif(SUM(netCost),0) *100) ,0) as revenueToAssetNet,"
							+"case (SUM(initCost)) when 0 then -1 when null then -1 else round (COALESCE (population,0) * 100 / SUM(initCost),3) end as populationToAssetInit,"
							+"case (SUM(netCost)) when 0 then -1 when null then -1 else round (COALESCE (population,0) * 100 / SUM(netCost),3) end as populationToAssetNet,"
//							 + " COALESCE( (nullif(population,0)) / (nullif(SUM(initCost),0) *100) ,0)  as populationToAssetInit , COALESCE( (nullif(population,0)) / (nullif(SUM(netCost),0) *100) ,0)  as populationToAssetNet, " 
							+ "COALESCE(NULLIF(population, 0), 0) as population, " + 
							"COALESCE(SUM(voiceRevenue),0) as voiceRevenue,COALESCE(SUM(smsRevenue),0) as smsRevenue,COALESCE(SUM(dataRevneue),0) as dataRevneue,COALESCE(SUM(vasRevenue),0) as vasRevenue, " + 
							"COALESCE(SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) as totalRevenue, " + 
							"COALESCE(SUM(initCost),0) as initCost,COALESCE(SUM(accuDepr),0) as Depr,COALESCE((COALESCE(SUM(initCost),0) - COALESCE(SUM(accuDepr),0)),0) as netCost FROM ( " + 
							"SELECT DISTINCT C.SITE_ID AS site,C.WARE_ID as wareID, C.SITE_ID AS siteID, C.WARE_NAME AS siteName , C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude,C.POPULATION AS population, A.INITIALCOST as initCost, A.NETCOST as netCost , A.ACCUMULDEPRECAMNT as accuDepr,A.FAR_ID AS FAR_ID, " + 
							"0 as voiceRevenue, 0 as smsRevenue, 0 as dataRevneue, 0 as vasRevenue " + 
							"FROM FIXED_ASSET_REGISTRY A " + 
							"LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID " + 
							"LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID " + 
							//"WHERE A.CREATED_DATE >=  trunc(SYSDATE - INTERVAL '3' YEAR) AND A.created_date < (trunc(sysdate) ) + 1  " + 
							"UNION " + 
							"SELECT DISTINCT C.SITE_ID AS site,C.WARE_ID as wareID, C.SITE_ID AS siteID, C.WARE_NAME AS siteName , C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude,C.POPULATION AS population, 0 as initCost,0 as accuDepr, 0 as netCost , '' AS FAR_ID, " + 
							"D.VOICE_REVENUE as voiceRevenue, D.SMS_REVENUE as smsRevenue, D.DATA_REVENUE as dataRevneue,D.VAS_REVENUE as vasRevenue " + 
							"FROM rpt_PREPAID_PAYG_REVENUE D " + 
							"LEFT JOIN WAREHOUSE C ON C.SITE_ID = D.SITE_ID " + 
							//"WHERE D.REVENUE_DATE >=  trunc(SYSDATE - INTERVAL '6' MONTH) AND D.REVENUE_DATE < trunc(sysdate) " + 
							"WHERE D.REVENUE_DATE >= trunc(" +startDate+ ") AND D.REVENUE_DATE < trunc(sysdate) " +
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY site,wareID,siteID,siteName,longitude,latitude,population " +
							"order by revenueToAssetInit desc"
							);
					
					
				  List<RevenueToAssetRatioReport> RevenueToAssetRatioList = (List<RevenueToAssetRatioReport>) ((NativeQuery<RevenueToAssetRatioReport>) query)
							.addScalar("site").addScalar("wareID").addScalar("siteID").addScalar("siteName").addScalar("longitude")
							.addScalar("latitude").addScalar("revenueToAssetInit").addScalar("revenueToAssetNet").addScalar("populationToAssetInit").addScalar("populationToAssetNet").addScalar("population").addScalar("voiceRevenue").addScalar("smsRevenue").addScalar("dataRevneue").addScalar("vasRevenue")
							.addScalar("totalRevenue").addScalar("initCost").addScalar("Depr").addScalar("netCost")
							.setResultTransformer(Transformers.aliasToBean(RevenueToAssetRatioReport.class)).list();					
				
				 model.addAttribute("RevenueToAssetRatioReportList", mapper.writeValueAsString(RevenueToAssetRatioList));
 
					}
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in RevenueToAssetRatioReport due to \n " + exceptionAsString);
					logger.info("Error in RevenueToAssetRatioReport due to \n " + exceptionAsString);
					e.printStackTrace();
				}
				finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
		return "Reports/RevenueToAssetRatioReport";
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/GenerateGridRevenueToAssetRatioReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateGridRevenueToAssetRatioReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String wareID = request.getParameter("wareID");
		String siteId = request.getParameter("siteId");
		String siteName = request.getParameter("siteName");
		String ignoreDate = request.getParameter("ignoreDate");
		String strtEndCheckbox = request.getParameter("strtEndCheckbox");
		String circleRangeCheckbox = request.getParameter("circleRangeCheckbox");		

		String start_Date = request.getParameter("startDate");
		String end_Date = request.getParameter("endDate");
		String str = "", strRPT = "",strALM = "";

		String startLong = request.getParameter("startLong");
		String startLat = request.getParameter("startLat");
		String endLong = request.getParameter("endLong");
		String endLat = request.getParameter("endLat");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String radius = request.getParameter("radius");
		double distance = 0.0;

		List<Object[]> siteTempList = new ArrayList<Object[]>();
		List<RevenueToAssetRatioReport> listCircleRange = new ArrayList<RevenueToAssetRatioReport>();
		List<RevenueToAssetRatioReport> RevenueToAssetRatioList = new ArrayList<RevenueToAssetRatioReport>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
				session = AlmDbSession.getInstance().getSession();
				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();
					try {	
					strALM = "SELECT DISTINCT C.SITE_ID AS site,C.WARE_ID as wareID, C.SITE_ID AS siteID, C.WARE_NAME AS siteName , C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude, C.POPULATION AS population, A.INITIALCOST as initCost, A.NETCOST as netCost , A.ACCUMULDEPRECAMNT as accuDepr,A.FAR_ID AS FAR_ID, " + 
							"0 as voiceRevenue, 0 as smsRevenue, 0 as dataRevneue, 0 as vasRevenue " + 
							"FROM FIXED_ASSET_REGISTRY A " + 
							"LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID " + 
							"LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID " + 
							 " WHERE ( upper(C.WARE_ID) LIKE upper('%" + wareID + "%') AND upper(C.SITE_ID) LIKE upper('%" + siteId + "%') AND upper(C.WARE_NAME) LIKE upper('%" + siteName+ "%') )  ";
					strRPT = "SELECT DISTINCT C.SITE_ID AS site,C.WARE_ID as wareID, C.SITE_ID AS siteID, C.WARE_NAME AS siteName , C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude,C.POPULATION AS population, 0 as initCost,0 as accuDepr, 0 as netCost , '' AS FAR_ID, " + 
							"D.VOICE_REVENUE as voiceRevenue, D.SMS_REVENUE as smsRevenue, D.DATA_REVENUE as dataRevneue,D.VAS_REVENUE as vasRevenue " + 
							"FROM rpt_PREPAID_PAYG_REVENUE D " + 
							"LEFT JOIN WAREHOUSE C ON C.SITE_ID = D.SITE_ID " + 
							 " WHERE ( upper(C.WARE_ID) LIKE upper('%" + wareID + "%') AND upper(C.SITE_ID) LIKE upper('%" + siteId + "%') AND upper(C.WARE_NAME) LIKE upper('%" + siteName+ "%') )  ";
										
					
					if (StringUtils.equalsIgnoreCase(ignoreDate, "false")) {
						/* strALM = strALM + " AND A.CREATED_DATE between TO_DATE('" + start_Date
								+ "','MM/DD/YYYY HH:MI AM') and TO_DATE('" + end_Date + "','MM/DD/YYYY HH:MI AM')"; */
						
						strRPT = strRPT + " AND D.REVENUE_DATE between TO_DATE('" + start_Date
								+ "','MM/DD/YYYY HH:MI AM') and TO_DATE('" + end_Date + "','MM/DD/YYYY HH:MI AM')";

					}

					

					// Include the start/end long and lat in where condition in case of strt/end
					// coordinate checkbox is checked
					if (StringUtils.equalsIgnoreCase(strtEndCheckbox, "true")) {

						// start longitude is entered && end longitude is empty
						if (startLong != null && !startLong.equalsIgnoreCase("")
								&& (endLong == null || endLong.equalsIgnoreCase(""))) {
							strALM = strALM + " and to_number(SUBSTR(C.LONGITUDE,1,6)) > " + startLong;
							strRPT = strRPT + " and to_number(SUBSTR(C.LONGITUDE,1,6)) > " + startLong;
						}

						// End longitude is entered && start longitude is empty
						else if (endLong != null && !endLong.equalsIgnoreCase("")
								&& (startLong == null || startLong.equalsIgnoreCase(""))) {
							strALM = strALM + " and to_number(SUBSTR(C.LONGITUDE,1,6)) < " + endLong;
							strRPT = strRPT + " and to_number(SUBSTR(C.LONGITUDE,1,6)) < " + endLong;
						}

						// Start and end longitude are both entered
						else if (startLong != null && endLong != null) {
							String startLng;
							String endLng;

							if (startLong != null && startLong.length() > 0
									&& (endLong != null && endLong.length() > 0)) {
								if (Double.parseDouble(startLong) < Double.parseDouble(endLong)) {
									startLng = startLong;
									endLng = endLong;
								} else {
									startLng = endLong;
									endLng = startLong;
								}
								strALM = strALM + " and to_number(SUBSTR(C.LONGITUDE,1,6)) > " + startLng;
								strALM = strALM + " and to_number(SUBSTR(C.LONGITUDE,1,6)) < " + endLng;
								
								strRPT = strRPT + " and to_number(SUBSTR(C.LONGITUDE,1,6)) > " + startLng;
								strRPT = strRPT + " and to_number(SUBSTR(C.LONGITUDE,1,6)) < " + endLng;
								
							}
						}

						// start latitude is entered && end latitude is empty
						if (startLat != null && !startLat.equalsIgnoreCase("")
								&& (endLat == null || endLat.equalsIgnoreCase(""))) {
							strALM = strALM + " and to_number(SUBSTR(C.LATITUDE,1,6)) > " + startLat;
							strRPT = strRPT + " and to_number(SUBSTR(C.LATITUDE,1,6)) > " + startLat;

						}

						// start latitude is empty && end latitude is entered
						else if (endLat != null && !endLat.equalsIgnoreCase("")
								&& (startLat == null || startLat.equalsIgnoreCase(""))) {
							strALM = strALM + " and to_number(SUBSTR(C.LATITUDE,1,6)) < " + endLat;
							strRPT = strRPT + " and to_number(SUBSTR(C.LATITUDE,1,6)) < " + endLat;

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
								strALM = strALM + " and to_number(SUBSTR(C.LATITUDE,1,6)) > " + startlatitude;
								strALM = strALM + " and to_number(SUBSTR(C.LATITUDE,1,6)) < " + endLatitude;
								
								strRPT = strRPT + " and to_number(SUBSTR(C.LATITUDE,1,6)) > " + startlatitude;
								strRPT = strRPT + " and to_number(SUBSTR(C.LATITUDE,1,6)) < " + endLatitude;
								
							}
						}

					} // end of checked strt/end coordinate checkbox
					
					str =  "SELECT site as site,wareID as wareID,siteID as siteID,siteName as siteName,longitude as longitude,latitude as latitude,"
							+"case (SUM(initCost)) when 0 then -1 when null then -1 else round (COALESCE (SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) * 100 / SUM(initCost),3) end as revenueToAssetInit,"							
							//+"COALESCE( nullif(SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) / (nullif(SUM(initCost),0) * 100) ,0) as revenueToAssetInit,"
							+"case (SUM(netCost)) when 0 then -1 when null then -1 else round (COALESCE (SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) * 100 / SUM(netCost),3) end as revenueToAssetNet,"							
							//+ "COALESCE( (nullif(SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0)) / (nullif(SUM(netCost),0) *100) ,0) as revenueToAssetNet,"
							+"case (SUM(initCost)) when 0 then -1 when null then -1 else round (COALESCE (population,0) * 100 / SUM(initCost),3) end as populationToAssetInit,"
							+"case (SUM(netCost)) when 0 then -1 when null then -1 else round (COALESCE (population,0) * 100 / SUM(netCost),3) end as populationToAssetNet,"							
							//+ " COALESCE( (nullif(population,0)) / (nullif(SUM(initCost),0) *100) ,0)  as populationToAssetInit , COALESCE( (nullif(population,0)) / (nullif(SUM(netCost),0) *100) ,0)  as populationToAssetNet, " 
							+ "COALESCE(NULLIF(population, 0), 0) as population, " + 
							"COALESCE(SUM(voiceRevenue),0) as voiceRevenue,COALESCE(SUM(smsRevenue),0) as smsRevenue,COALESCE(SUM(dataRevneue),0) as dataRevneue,COALESCE(SUM(vasRevenue),0) as vasRevenue, " + 
							"COALESCE(SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) as totalRevenue, " + 
							"COALESCE(SUM(initCost),0) as initCost,COALESCE(SUM(accuDepr),0) as Depr, COALESCE((COALESCE(SUM(initCost),0) - COALESCE(SUM(accuDepr),0)),0) as netCost  FROM ( " + 
							strALM+ " UNION " + strRPT
						    + "  ) WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY site,wareID,siteID,siteName,longitude,latitude,population "
						    + "order by revenueToAssetInit desc";

					
					//System.out.println("the Str is " + str);
					query = session.createNativeQuery(str);
					siteTempList = query.list(); // To use it in circle range calculation

					RevenueToAssetRatioList = (List<RevenueToAssetRatioReport>) ((NativeQuery<RevenueToAssetRatioReport>) query)
							.addScalar("site").addScalar("wareID").addScalar("siteID").addScalar("siteName").addScalar("longitude")
							.addScalar("latitude").addScalar("revenueToAssetInit").addScalar("revenueToAssetNet").addScalar("populationToAssetInit").addScalar("populationToAssetNet").addScalar("population").addScalar("voiceRevenue").addScalar("smsRevenue").addScalar("dataRevneue").addScalar("vasRevenue")
							.addScalar("totalRevenue").addScalar("initCost").addScalar("Depr").addScalar("netCost")
							.setResultTransformer(Transformers.aliasToBean(RevenueToAssetRatioReport.class)).list();
					
					// If circle range is checked
					if (StringUtils.equalsIgnoreCase(circleRangeCheckbox, "true")) {

						if ((radius != null && !radius.equalsIgnoreCase("") && Double.parseDouble(radius) > 0)
								&& (longitude != null && !longitude.equalsIgnoreCase(""))
								&& (latitude != null && !latitude.equalsIgnoreCase(""))) {
	
							for (int i = 0; i < RevenueToAssetRatioList.size(); i++) {
								distance = haversineMethod(Double.parseDouble(latitude), Double.parseDouble(longitude),
										Double.valueOf(siteTempList.get(i)[6].toString()),
										Double.valueOf(siteTempList.get(i)[5].toString()));
								
								if (distance <= Double.parseDouble(radius)) {
									listCircleRange.add(RevenueToAssetRatioList.get(i));
								}
							}
							
							rtn.put("RevenueToAssetRatioList", listCircleRange);
						}

					}
					else {
						rtn.put("RevenueToAssetRatioList", RevenueToAssetRatioList);
					}

				
				session.flush();
				session.clear();
				tx.commit();

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GenerateGridRevenueToAssetRatioReport due to \n " + exceptionAsString);
				logger.info("Error in GenerateGridRevenueToAssetRatioReport due to \n " + exceptionAsString);
				rtn.put("RevenueToAssetRatioList", null);
			}

			finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
		}
	}

	static double haversineMethod(double latitude, double longitude, double pointLat, double pointLong) {

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
