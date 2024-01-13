package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.SiteAssetReport;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SiteAssetReportController {

	private static final Logger logger = Logger.getLogger(SiteAssetReportController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	Object[] result;
	private static StringWriter sw;
	private static String exceptionAsString;
	
	@Autowired
	Notify notifications;

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/SiteAssetReport", method = RequestMethod.GET)
	public String SiteAssetReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ObjectMapper mapper = new ObjectMapper();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {

			session = AlmDbSession.getInstance().getSession();
			DecimalFormat df = new DecimalFormat("#,###.00");
			String totalInitialCost = "0.0";
			String totalAccumdepr = "0.0";
			String totalNetCost = "0.0";

			if (session != null && session.isOpen()) {

				notifications.headerNotifications(session, model);
				try {
				
					query = session.createNativeQuery(
							"SELECT site as site,wareID as wareID,siteID as siteID,siteName as siteName,longitude as longitude,latitude as latitude,COALESCE(SUM(initCost),0) as initCost,COALESCE(SUM(netCost),0) as netCost , COALESCE(SUM(accuDepr),0) as accuDepr FROM (  "
							+ " SELECT DISTINCT C.SITE_ID AS site,C.WARE_ID as wareID, C.SITE_ID AS siteID, C.WARE_NAME AS siteName , C.LONGITUDE as longitude, C.LATITUDE as latitude, A.INITIALCOST as initCost, A.NETCOST as netCost , A.ACCUMULDEPRECAMNT as accuDepr,A.FAR_ID AS FAR_ID "
							+ " FROM FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID WHERE A.CREATED_DATE >=  trunc(SYSDATE - INTERVAL '1' YEAR) AND A.created_date < (trunc(sysdate) ) + 1   ) "
							+ " WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY site,wareID,siteID,siteName,longitude,latitude ");

					
				  List<SiteAssetReport> siteAssetList = (List<SiteAssetReport>) ((NativeQuery<SiteAssetReport>) query)
							.addScalar("site").addScalar("wareID").addScalar("siteID").addScalar("siteName").addScalar("longitude")
							.addScalar("latitude").addScalar("initCost").addScalar("netCost").addScalar("accuDepr")
							.setResultTransformer(Transformers.aliasToBean(SiteAssetReport.class)).list();
				   model.addAttribute("siteAssetReportList", mapper.writeValueAsString(siteAssetList));
				   
				   query = session.createNativeQuery("Select COALESCE(SUM(initialCost),0) , COALESCE(SUM(AccumDepr),0) , COALESCE(SUM(netCost),0) FROM ( SELECT DISTINCT A.FAR_ID AS FAR_ID,B.SITE_ID AS SITE_ID, A.INITIALCOST as initialCost,A.ACCUMULDEPRECAMNT as AccumDepr, A.NETCOST as netCost from FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID "
							+ " LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID WHERE A.CREATED_DATE >=  trunc(SYSDATE - INTERVAL '1' YEAR) AND A.created_date < (trunc(sysdate) ) + 1  "
							+ " AND (C.longitude is not null and C.longitude != '0' AND C.longitude != 'null' and C.latitude is not null and C.latitude != '0' and C.latitude != 'null')   ) ");


				   result = (Object[]) query.uniqueResult();
					if (result[0] != null) {
					    BigDecimal value = new BigDecimal(result[0].toString());
					    if (value.compareTo(BigDecimal.ZERO) == 0) {
					        totalInitialCost = "0"; // Set as '0' if value is zero
					    } else {
					        totalInitialCost = df.format(value); // Format with decimal places otherwise
					    }
					}
					if (result[1] != null) {
					    BigDecimal value = new BigDecimal(result[1].toString());
					    if (value.compareTo(BigDecimal.ZERO) == 0) {
					    	totalAccumdepr = "0"; // Set as '0' if value is zero
					    } else {
					    	totalAccumdepr = df.format(value); // Format with decimal places otherwise
					    }
					}
					
					if (result[2] != null) {
						BigDecimal value = new BigDecimal(result[2].toString());
					    if (value.compareTo(BigDecimal.ZERO) == 0) {
					    	totalNetCost = "0"; // Set as '0' if value is zero
					    } else {
					    	totalNetCost = df.format(value); // Format with decimal places otherwise
					    }
					}
					
					
					model.addAttribute("totalInitialCostFetched", totalInitialCost);
					model.addAttribute("totalAccumdeprFetched", totalAccumdepr);
					model.addAttribute("totalNetCostFetched", totalNetCost);

					// Get the total of initial,net cost and accumulated depr of all FAR records
					query = session.createNativeQuery("Select COALESCE(SUM(INITIALCOST),0) , COALESCE(SUM(ACCUMULDEPRECAMNT),0) , COALESCE(SUM(NETCOST),0) FROM FIXED_ASSET_REGISTRY  ");

					result = (Object[]) query.uniqueResult();
					if (result[0] != null) {
					    BigDecimal value = new BigDecimal(result[0].toString());
					    if (value.compareTo(BigDecimal.ZERO) == 0) {
					        totalInitialCost = "0"; // Set as '0' if value is zero
					    } else {
					        totalInitialCost = df.format(value); // Format with decimal places otherwise
					    }
					}
					
					if (result[1] != null) {
					    BigDecimal value = new BigDecimal(result[1].toString());
					    if (value.compareTo(BigDecimal.ZERO) == 0) {
					    	totalAccumdepr = "0"; // Set as '0' if value is zero
					    } else {
					    	totalAccumdepr = df.format(value); // Format with decimal places otherwise
					    }
					}
					
					if (result[2] != null) {
						BigDecimal value = new BigDecimal(result[2].toString());
					    if (value.compareTo(BigDecimal.ZERO) == 0) {
					    	totalNetCost = "0"; // Set as '0' if value is zero
					    } else {
					    	totalNetCost = df.format(value); // Format with decimal places otherwise
					    }
					}
					
					model.addAttribute("totalInitialCost", totalInitialCost);
					model.addAttribute("totalAccumdepr", totalAccumdepr);
					model.addAttribute("totalNetCost", totalNetCost);
					
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in SiteAssetReport due to \n " + exceptionAsString);
					logger.info("Error in SiteAssetReport due to \n " + exceptionAsString);
					
					e.printStackTrace();
				}finally {
					if (session != null && session.isOpen()) {
						session.close();

					}
				}
			}
		}
		return "Reports/SiteAssetReport";
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/GenerateGridSiteAssetReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateGridSiteAssetReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String wareID = request.getParameter("wareID");
		String siteId = request.getParameter("siteId");
		String siteName = request.getParameter("siteName");
		String ignoreDate = request.getParameter("ignoreDate");
		String strtEndCheckbox = request.getParameter("strtEndCheckbox");
		String circleRangeCheckbox = request.getParameter("circleRangeCheckbox");		
		double initCost=0,netCost=0,accuDepr=0;

		String start_Date = request.getParameter("startDate");
		String end_Date = request.getParameter("endDate");

		DecimalFormat df = new DecimalFormat("#,###.00");
		String totalInitialCost = "0.0";
		String totalAccumdepr = "0.0";
		String totalNetCost = "0.0";
		Object[] result;
		String str = "", totalStr = "";

		String startLong = request.getParameter("startLong");
		String startLat = request.getParameter("startLat");
		String endLong = request.getParameter("endLong");
		String endLat = request.getParameter("endLat");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String radius = request.getParameter("radius");
		double distance = 0.0;

		List<Object[]> siteTempList = new ArrayList<Object[]>();
		List<SiteAssetReport> listCircleRange = new ArrayList<SiteAssetReport>();
		List<SiteAssetReport> siteAssetList = new ArrayList<SiteAssetReport>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
				session = AlmDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();
					try {	
					 str = "SELECT site as site,wareID as wareID,siteID as siteID,siteName as siteName,longitude as longitude,latitude as latitude,COALESCE(SUM(initCost),0) as initCost,COALESCE(SUM(netCost),0) as netCost , COALESCE(SUM(accuDepr),0) as accuDepr FROM (  "
					           + " SELECT DISTINCT C.SITE_ID AS site,C.WARE_ID as wareID, C.SITE_ID AS siteID, C.WARE_NAME AS siteName , C.LONGITUDE as longitude, C.LATITUDE as latitude, A.INITIALCOST as initCost, A.NETCOST as netCost , A.ACCUMULDEPRECAMNT as accuDepr,A.FAR_ID AS FAR_ID   "
					           + " FROM FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID "
					           + " WHERE ( upper(C.WARE_ID) LIKE upper('%" + wareID
								+ "%') AND upper(C.SITE_ID) LIKE upper('%" + siteId
								+ "%') AND upper(C.WARE_NAME) LIKE upper('%" + siteName
								+ "%') )  ";
						
					   totalStr = " Select COALESCE(SUM(initialCost),0) , COALESCE(SUM(AccumDepr),0) , COALESCE(SUM(netCost),0) FROM ( "
					 			+ " SELECT DISTINCT A.FAR_ID AS FAR_ID,B.SITE_ID AS SITE_ID, A.INITIALCOST as initialCost,A.ACCUMULDEPRECAMNT as AccumDepr, A.NETCOST as netCost from FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID "
								+ " WHERE ( upper(C.WARE_ID) LIKE upper('%" + wareID
								+ "%') AND upper(C.SITE_ID) LIKE upper('%" + siteId
								+ "%') AND upper(C.WARE_NAME) LIKE upper('%" + siteName
								+ "%') )  ";

					if (StringUtils.equalsIgnoreCase(ignoreDate, "false")) {
						str = str + " AND A.CREATED_DATE between TO_DATE('" + start_Date
								+ "','MM/DD/YYYY HH:MI AM') and TO_DATE('" + end_Date + "','MM/DD/YYYY HH:MI AM')";

						totalStr = totalStr + " AND A.CREATED_DATE between TO_DATE('" + start_Date
								+ "','MM/DD/YYYY HH:MI AM') and TO_DATE('" + end_Date + "','MM/DD/YYYY HH:MI AM')";

					}

					

					// Include the start/end long and lat in where condition in case of strt/end
					// coordinate checkbox is checked
					if (StringUtils.equalsIgnoreCase(strtEndCheckbox, "true")) {

						// start longitude is entered && end longitude is empty
						if (startLong != null && !startLong.equalsIgnoreCase("")
								&& (endLong == null || endLong.equalsIgnoreCase(""))) {
							str = str + " and to_number(SUBSTR(C.LONGITUDE,1,6)) > " + startLong;
							totalStr = totalStr + " and to_number(SUBSTR(C.LONGITUDE,1,6)) > " + startLong;

						}

						// End longitude is entered && start longitude is empty
						else if (endLong != null && !endLong.equalsIgnoreCase("")
								&& (startLong == null || startLong.equalsIgnoreCase(""))) {
							str = str + " and to_number(SUBSTR(C.LONGITUDE,1,6)) < " + endLong;
							totalStr = totalStr + " and to_number(SUBSTR(C.LONGITUDE,1,6)) < " + endLong;

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
								str = str + " and to_number(SUBSTR(C.LONGITUDE,1,6)) > " + startLng;
								str = str + " and to_number(SUBSTR(C.LONGITUDE,1,6)) < " + endLng;
								
								totalStr = totalStr +" and to_number(SUBSTR(C.LONGITUDE,1,6)) > " + startLng;
								totalStr = totalStr +" and to_number(SUBSTR(C.LONGITUDE,1,6)) < " + endLng;

							}
						}

						// start latitude is entered && end latitude is empty
						if (startLat != null && !startLat.equalsIgnoreCase("")
								&& (endLat == null || endLat.equalsIgnoreCase(""))) {
							str = str + " and to_number(SUBSTR(C.LATITUDE,1,6)) > " + startLat;
							totalStr = totalStr +" and to_number(SUBSTR(C.LATITUDE,1,6)) > " + startLat;

						}

						// start latitude is empty && end latitude is entered
						else if (endLat != null && !endLat.equalsIgnoreCase("")
								&& (startLat == null || startLat.equalsIgnoreCase(""))) {
							str = str + " and to_number(SUBSTR(C.LATITUDE,1,6)) < " + endLat;
							totalStr = totalStr +" and to_number(SUBSTR(C.LATITUDE,1,6)) < " + endLat;

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
								
								totalStr = totalStr + " and to_number(SUBSTR(C.LATITUDE,1,6)) > " + startlatitude;
								totalStr = totalStr +" and to_number(SUBSTR(C.LATITUDE,1,6)) < " + endLatitude;

							}
						}

					} // end of checked strt/end coordinate checkbox

					str = str + "  ) WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY site,wareID,siteID,siteName,longitude,latitude ";
					totalStr = totalStr +" AND (C.longitude is not null and C.longitude != '0' and C.longitude != 'null' and C.latitude is not null and C.latitude != '0' and C.latitude != 'null'))";

					//System.out.println("the totalStr is " + totalStr);

					query = session.createNativeQuery(str);
					siteTempList = query.list(); // To use it in circle range calculation

					siteAssetList = (List<SiteAssetReport>) ((NativeQuery<SiteAssetReport>) query)
								.addScalar("site").addScalar("wareID").addScalar("siteID").addScalar("siteName").addScalar("longitude")
								.addScalar("latitude").addScalar("initCost").addScalar("netCost").addScalar("accuDepr")
								.setResultTransformer(Transformers.aliasToBean(SiteAssetReport.class)).list();
					
					// If circle range is checked
					if (StringUtils.equalsIgnoreCase(circleRangeCheckbox, "true")) {

						if ((radius != null && !radius.equalsIgnoreCase("") && Double.parseDouble(radius) > 0)
								&& (longitude != null && !longitude.equalsIgnoreCase(""))
								&& (latitude != null && !latitude.equalsIgnoreCase(""))) {
	
							for (int i = 0; i < siteAssetList.size(); i++) {
								distance = haversineMethod(Double.parseDouble(latitude), Double.parseDouble(longitude),
										Double.valueOf(siteTempList.get(i)[6].toString()),
										Double.valueOf(siteTempList.get(i)[5].toString()));
								
								if (distance <= Double.parseDouble(radius)) {
									initCost += Double.valueOf(siteTempList.get(i)[7].toString());
									netCost += Double.valueOf(siteTempList.get(i)[8].toString());
									accuDepr += Double.valueOf(siteTempList.get(i)[9].toString());
									
									listCircleRange.add(siteAssetList.get(i));
								}
							}
							
							
							rtn.put("totalInitialCostFetched", df.format(initCost));
							rtn.put("totalAccumdeprFetched", df.format(accuDepr));
							rtn.put("totalNetCostFetched", df.format(netCost));

							rtn.put("siteAssetReportList", listCircleRange);
						}

					}
					else {
						// Get the total of initial,net cost and accumulated depr of fetched FAR records
						query = session.createNativeQuery(totalStr);
						result = (Object[]) query.uniqueResult();
						
						
						if (result[0] != null) {
						    BigDecimal value = new BigDecimal(result[0].toString());
						    if (value.compareTo(BigDecimal.ZERO) == 0) {
						        totalInitialCost = "0"; // Set as '0' if value is zero
						    } else {
						        totalInitialCost = df.format(value); // Format with decimal places otherwise
						    }
						}
						if (result[1] != null) {
						    BigDecimal value = new BigDecimal(result[1].toString());
						    if (value.compareTo(BigDecimal.ZERO) == 0) {
						    	totalAccumdepr = "0"; // Set as '0' if value is zero
						    } else {
						    	totalAccumdepr = df.format(value); // Format with decimal places otherwise
						    }
						}
						
						if (result[2] != null) {
							BigDecimal value = new BigDecimal(result[2].toString());
						    if (value.compareTo(BigDecimal.ZERO) == 0) {
						    	totalNetCost = "0"; // Set as '0' if value is zero
						    } else {
						    	totalNetCost = df.format(value); // Format with decimal places otherwise
						    }
						}
						rtn.put("totalInitialCostFetched", totalInitialCost);
						rtn.put("totalAccumdeprFetched", totalAccumdepr);
						rtn.put("totalNetCostFetched", totalNetCost);
						
						rtn.put("siteAssetReportList", siteAssetList);
					}

					
					// Get the total of initial,net cost and accumulated depr of all FAR records
					query = session.createNativeQuery("Select COALESCE(SUM(INITIALCOST),0) , COALESCE(SUM(ACCUMULDEPRECAMNT),0) , COALESCE(SUM(NETCOST),0) FROM FIXED_ASSET_REGISTRY  ");

					result = (Object[]) query.uniqueResult();
					if (result[0] != null) {
					    BigDecimal value = new BigDecimal(result[0].toString());
					    if (value.compareTo(BigDecimal.ZERO) == 0) {
					        totalInitialCost = "0"; // Set as '0' if value is zero
					    } else {
					        totalInitialCost = df.format(value); // Format with decimal places otherwise
					    }
					}
					
					if (result[1] != null) {
					    BigDecimal value = new BigDecimal(result[1].toString());
					    if (value.compareTo(BigDecimal.ZERO) == 0) {
					    	totalAccumdepr = "0"; // Set as '0' if value is zero
					    } else {
					    	totalAccumdepr = df.format(value); // Format with decimal places otherwise
					    }
					}
					
					if (result[2] != null) {
						BigDecimal value = new BigDecimal(result[2].toString());
					    if (value.compareTo(BigDecimal.ZERO) == 0) {
					    	totalNetCost = "0"; // Set as '0' if value is zero
					    } else {
					    	totalNetCost = df.format(value); // Format with decimal places otherwise
					    }
					}
					
					rtn.put("totalInitialCost", totalInitialCost);
					rtn.put("totalAccumdepr", totalAccumdepr);
					rtn.put("totalNetCost", totalNetCost);
				
				session.flush();
				session.clear();
				tx.commit();

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GenerateGridSiteAssetReport due to \n " + exceptionAsString);
				logger.info("Error in GenerateGridSiteAssetReport due to \n " + exceptionAsString);
				
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