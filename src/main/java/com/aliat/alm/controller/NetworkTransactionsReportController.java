package com.aliat.alm.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.RevenueToAssetRatioReport;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class NetworkTransactionsReportController {
	private static final Logger logger = Logger.getLogger(NetworkTransactionsReportController.class.getName());
	private static ObjectMapper mapper = new ObjectMapper();
	private static Session session = null;
	@SuppressWarnings("rawtypes")
	private static Query query = null;

	@Autowired
	Notify notifications;

	@RequestMapping(value = "/NetworkTransactionsReport", method = RequestMethod.GET)
	public String NetworkTransactionsReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {

			try {
				session = AlmDbSession.getInstance().getSession();
				notifications.headerNotifications(session, model);
				/*query = session.createNativeQuery(
						"SELECT a.ELEMENT_ID,a.ELEMENT,a.ALM_TRANS_TYPE,a.DISCOVERED_TRANS_TYPE,TO_CHAR(a.PARSING_DATE,'DD-MM-YYYY HH:mm:ss') as startdate,a.FROM_SITE,a.TO_SITE," + 
						" b.FROM_NODE_ID,b.TO_NODE_ID,b.FROM_NODE_TYPE,b.TO_NODE_TYPE,MODEL,a.MAC_ADDRESS,a.SERIAL_NUMBER,a.FROM_CIRCLE,a.TO_CIRCLE,a.APPROVED_BY,a.MODIFIED_BY,a.SENT_TO_ALM,a.ALM_APPROVAL_STATUS" + 
						" FROM NETWORK_TRANSACTION a INNER JOIN NODE_TRANSACTIONS b on a.NODE_TRANS_ID=b.NODE_TRANS_ID" + 
						" WHERE a.PARSING_DATE between systimestamp - INTERVAL '7' DAY and systimestamp" + 
						" ORDER BY a.element_id DESC");*/
				
				/*query = session.createNativeQuery(
						"SELECT c.WARE_ID as wareID,c.SITE_ID as siteID,c.WARE_NAME as siteName,c.LONGITUDE as longitude,c.LATITUDE as latitude,c.site as site, " + 
						"a.ELEMENT_ID,a.ELEMENT,a.ALM_TRANS_TYPE,a.DISCOVERED_TRANS_TYPE,TO_CHAR(a.PARSING_DATE,'DD-MM-YYYY HH:mm:ss') as startdate,a.FROM_SITE,a.TO_SITE, " + 
						" b.FROM_NODE_ID,b.TO_NODE_ID,b.FROM_NODE_TYPE,b.TO_NODE_TYPE,MODEL,a.MAC_ADDRESS,a.SERIAL_NUMBER,a.FROM_CIRCLE,a.TO_CIRCLE,a.APPROVED_BY,a.MODIFIED_BY,a.SENT_TO_ALM,a.ALM_APPROVAL_STATUS " + 
						" FROM NETWORK_TRANSACTION a INNER JOIN NODE_TRANSACTIONS b on a.NODE_TRANS_ID=b.NODE_TRANS_ID " + 
						" LEFT JOIN WAREHOUSE c ON c.SITE_ID = a.TO_SITE " + 
						" WHERE a.PARSING_DATE between systimestamp - INTERVAL '7' DAY and systimestamp " + 
						" ORDER BY a.element_id DESC ");*/
				
				
				
				query = session.createNativeQuery("SELECT COALESCE(c_to.site, 'no site') AS site,a.ELEMENT_ID AS elementID,a.ELEMENT AS element,a.ALM_TRANS_TYPE AS TransType,a.DISCOVERED_TRANS_TYPE AS discoveredTransType,TO_CHAR(a.PARSING_DATE,'DD-MM-YYYY HH:mm:ss') AS startdate, " +
		                                          "c_from.WARE_ID AS fromWareID,c_from.WARE_NAME AS fromWareName, a.FROM_SITE AS fromSiteID, c_from.LONGITUDE AS fromSiteLongitude,c_from.LATITUDE AS fromSiteLatitude, c_to.WARE_ID AS toWareID, c_to.WARE_NAME AS toWareName, " +
		                                          "a.TO_SITE AS toSiteID,c_to.LONGITUDE AS toSiteLongitude,c_to.LATITUDE AS toSiteLatitude,b.FROM_NODE_ID AS fromNodeId,b.TO_NODE_ID AS toNodeId,b.FROM_NODE_TYPE AS fromNodeType,b.TO_NODE_TYPE AS toNodeType, MODEL AS Model, " +
		                                          "a.MAC_ADDRESS AS macAddress,a.SERIAL_NUMBER AS serialNumber, a.FROM_CIRCLE AS fromCircle,a.TO_CIRCLE AS toCircle,a.APPROVED_BY AS approvedBy,a.MODIFIED_BY AS modifiedBy,a.SENT_TO_ALM AS sentToAlm,a.ALM_APPROVAL_STATUS AS almApprovalStatus " +
		                                          " FROM NETWORK_TRANSACTION a  " +
		                                          " INNER JOIN NODE_TRANSACTIONS b ON a.NODE_TRANS_ID = b.NODE_TRANS_ID  " +
		                                          " LEFT JOIN WAREHOUSE c_from ON c_from.SITE_ID = a.FROM_SITE " +
		                                          " LEFT JOIN WAREHOUSE c_to ON c_to.SITE_ID = a.TO_SITE " +
		                                          " WHERE a.PARSING_DATE BETWEEN SYSTIMESTAMP - INTERVAL '7' DAY AND SYSTIMESTAMP " +
		                                          " ORDER BY a.element_id DESC ");
		       
				System.out.println(mapper.writeValueAsString(query.getResultList()));
				model.addAttribute("TransactionsGrid", mapper.writeValueAsString(query.getResultList()));
				
				query = session.createNativeQuery("select nvl(sum (case when DISCOVERED_TRANS_TYPE LIKE '%NEW%' then 1 else 0 end),0) as newelements," + 
						"nvl(sum (case when (DISCOVERED_TRANS_TYPE LIKE'%DISAPPEARED%') then 1 else 0 end),0) as disappeared," + 
						"nvl(sum (case when DISCOVERED_TRANS_TYPE LIKE '%Transfer%' then 1 else 0 end),0) as transferred," + 
						"nvl(sum (case when DISCOVERED_TRANS_TYPE LIKE '%Dismantle%' then 1 else 0 end),0) as dismantled," + 
						"count (DISCOVERED_TRANS_TYPE) as total" + 
						" from NETWORK_TRANSACTION" + 
						" WHERE PARSING_DATE between systimestamp - INTERVAL '7' DAY and systimestamp");
				Object[] statisticalResult = (Object[]) query.getResultList().get(0);
				//System.out.println(mapper.writeValueAsString(statisticalResult));
				model.addAttribute("totalbnrTrans", statisticalResult[4]);
				model.addAttribute("newElements", statisticalResult[0]);
				model.addAttribute("disappearedElements", statisticalResult[1]);
				model.addAttribute("transferredElements", statisticalResult[2]);
				model.addAttribute("dismantledElements", statisticalResult[3]);
				
				
				query = session.createNativeQuery("Select nvl(TO_CHAR(PARSING_DATE,'DD-MM-YYYY HH:mm:ss'), 'N/A')"
						+ " FROM NETWORK_TRANSACTION "
						+ " WHERE PARSING_DATE between systimestamp - INTERVAL '7' DAY and systimestamp "
						+ " order by parsing_date desc FETCH FIRST 1 ROW ONLY");
				//System.out.println(mapper.writeValueAsString(query.uniqueResult()));
				if(query.uniqueResult() == null) {
					model.addAttribute("lastscanDate", "N/A");
				}else {
					model.addAttribute("lastscanDate", query.uniqueResult());	
				}
				
			} catch (Exception e) {
				logger.info("Error on NetworkTransactionsReport with a message : " + e);
				e.printStackTrace();
				model.addAttribute("TransactionsGrid", "");
				model.addAttribute("totalbnrTrans", "0");
				model.addAttribute("newElements", "0");
				model.addAttribute("disappearedElements", "0");
				model.addAttribute("transferredElements", "0");
				model.addAttribute("dismantledElements", "0");
				model.addAttribute("lastscanDate", "N/A");
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		return "Reports/NetworkTransactionsReport";

	}

	@RequestMapping(value = "/GenerateNetworkTransactionsReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateNetworkTransactionsReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return rtn;
		} else {
			/// GRID
			System.out.println("GenerateNetworkTransactionsReport");
			String StartDate = request.getParameter("startDate");
			if (StartDate.contains("PM")) {
				StartDate = StartDate.replace(" PM", "").trim();

			} else {
				StartDate = StartDate.replace(" AM", "").trim();
			}

			String EndDate = request.getParameter("endDate");
			if (EndDate.contains("PM")) {
				EndDate = EndDate.replace(" PM", "").trim();

			} else {
				EndDate = EndDate.replace(" AM", "").trim();
			}
			
			String strtEndCheckbox = request.getParameter("strtEndCheckbox");
			String circleRangeCheckbox = request.getParameter("circleRangeCheckbox");
			String startLong = request.getParameter("startLong");
			String startLat = request.getParameter("startLat");
			String endLong = request.getParameter("endLong");
			String endLat = request.getParameter("endLat");
			String longitude = request.getParameter("longitude");
			String latitude = request.getParameter("latitude");
			String radius = request.getParameter("radius");
			double distance = 0.0;
			
			List<Object[]> siteTempList = new ArrayList<Object[]>();
			List<Object[]> listCircleRange = new ArrayList<Object[]>();


			try {
				session = AlmDbSession.getInstance().getSession();
				notifications.headerNotifications(session, model);
				/*String str = "SELECT c.WARE_ID as wareID,c.SITE_ID as siteID,c.WARE_NAME as siteName,c.LONGITUDE as longitude,c.LATITUDE as latitude,c.site as site,"
						+ " a.ELEMENT_ID,a.ELEMENT,a.ALM_TRANS_TYPE,a.DISCOVERED_TRANS_TYPE,TO_CHAR(a.PARSING_DATE,'DD-MM-YYYY HH:mm:ss') as startdate,a.FROM_SITE,a.TO_SITE," + 
						" b.FROM_NODE_ID,b.TO_NODE_ID,b.FROM_NODE_TYPE,b.TO_NODE_TYPE,MODEL,a.MAC_ADDRESS,a.SERIAL_NUMBER,a.FROM_CIRCLE,a.TO_CIRCLE,a.APPROVED_BY,a.MODIFIED_BY,a.SENT_TO_ALM,a.ALM_APPROVAL_STATUS" + 
						" FROM NETWORK_TRANSACTION a LEFT JOIN NODE_TRANSACTIONS b on a.node_trans_id=b.node_trans_id "
						+" LEFT JOIN WAREHOUSE c ON c.SITE_ID = a.TO_SITE " 
						+ "WHERE a.PARSING_DATE between TO_DATE('" + StartDate+ "','MM/DD/YYYY HH24:MI:SS') " 
						+ "and TO_DATE('" + EndDate + "','MM/DD/YYYY HH24:MI:SS')" ;*/
				
				String str = "SELECT COALESCE(c_to.site, 'no site') AS site,a.ELEMENT_ID AS elementID,a.ELEMENT AS element,a.ALM_TRANS_TYPE AS TransType,a.DISCOVERED_TRANS_TYPE AS discoveredTransType,TO_CHAR(a.PARSING_DATE,'DD-MM-YYYY HH:mm:ss') AS startdate, " +
                             "c_from.WARE_ID AS fromWareID,c_from.WARE_NAME AS fromWareName, a.FROM_SITE AS fromSiteID, c_from.LONGITUDE AS fromSiteLongitude,c_from.LATITUDE AS fromSiteLatitude, c_to.WARE_ID AS toWareID, c_to.WARE_NAME AS toWareName, " +
                             "a.TO_SITE AS toSiteID,c_to.LONGITUDE AS toSiteLongitude,c_to.LATITUDE AS toSiteLatitude,b.FROM_NODE_ID AS fromNodeId,b.TO_NODE_ID AS toNodeId,b.FROM_NODE_TYPE AS fromNodeType,b.TO_NODE_TYPE AS toNodeType, MODEL AS Model, " +
                             "a.MAC_ADDRESS AS macAddress,a.SERIAL_NUMBER AS serialNumber, a.FROM_CIRCLE AS fromCircle,a.TO_CIRCLE AS toCircle,a.APPROVED_BY AS approvedBy,a.MODIFIED_BY AS modifiedBy,a.SENT_TO_ALM AS sentToAlm,a.ALM_APPROVAL_STATUS AS almApprovalStatus " +
                             " FROM NETWORK_TRANSACTION a  " +
                             " INNER JOIN NODE_TRANSACTIONS b ON a.NODE_TRANS_ID = b.NODE_TRANS_ID  " +
                             " LEFT JOIN WAREHOUSE c_from ON c_from.SITE_ID = a.FROM_SITE " +
                             " LEFT JOIN WAREHOUSE c_to ON c_to.SITE_ID = a.TO_SITE " +
                             "WHERE a.PARSING_DATE between TO_DATE('" + StartDate+ "','MM/DD/YYYY HH24:MI:SS') " 
     						 + "and TO_DATE('" + EndDate + "','MM/DD/YYYY HH24:MI:SS')" ;
                            
				// Include the start/end long and lat in where condition in case of strt/end
				// coordinate checkbox is checked
				if (StringUtils.equalsIgnoreCase(strtEndCheckbox, "true")) {

					// start longitude is entered && end longitude is empty
					if (startLong != null && !startLong.equalsIgnoreCase("")
							&& (endLong == null || endLong.equalsIgnoreCase(""))) {
						str = str + " and to_number(SUBSTR(c_from.LONGITUDE,1,6)) > " + startLong;
						str = str + " and to_number(SUBSTR(c_to.LONGITUDE,1,6)) > " + startLong;
					}

					// End longitude is entered && start longitude is empty
					else if (endLong != null && !endLong.equalsIgnoreCase("")
							&& (startLong == null || startLong.equalsIgnoreCase(""))) {
						str = str + " and to_number(SUBSTR(c_from.LONGITUDE,1,6)) < " + endLong;
						str = str + " and to_number(SUBSTR(c_to.LONGITUDE,1,6)) < " + endLong;
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
							str = str + " and to_number(SUBSTR(c_from.LONGITUDE,1,6)) > " + startLng;
							str = str + " and to_number(SUBSTR(c_from.LONGITUDE,1,6)) < " + endLng;
							
							str = str + " and to_number(SUBSTR(c_to.LONGITUDE,1,6)) > " + startLng;
							str = str + " and to_number(SUBSTR(c_to.LONGITUDE,1,6)) < " + endLng;
							
						}
					}

					// start latitude is entered && end latitude is empty
					if (startLat != null && !startLat.equalsIgnoreCase("")
							&& (endLat == null || endLat.equalsIgnoreCase(""))) {
						str = str + " and to_number(SUBSTR(c_from.LATITUDE,1,6)) > " + startLat;
						str = str + " and to_number(SUBSTR(c_to.LATITUDE,1,6)) > " + startLat;

					}

					// start latitude is empty && end latitude is entered
					else if (endLat != null && !endLat.equalsIgnoreCase("")
							&& (startLat == null || startLat.equalsIgnoreCase(""))) {
						str = str + " and to_number(SUBSTR(c_from.LATITUDE,1,6)) < " + endLat;
						str = str + " and to_number(SUBSTR(c_to.LATITUDE,1,6)) < " + endLat;

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
							str = str + " and to_number(SUBSTR(c_from.LATITUDE,1,6)) > " + startlatitude;
							str = str + " and to_number(SUBSTR(c_from.LATITUDE,1,6)) < " + endLatitude;
							
							str = str + " and to_number(SUBSTR(c_to.LATITUDE,1,6)) > " + startlatitude;
							str = str + " and to_number(SUBSTR(c_to.LATITUDE,1,6)) < " + endLatitude;
							
							
						}
					}

				} // end of checked strt/end coordinate checkbox
				str +=" ORDER BY a.PARSING_DATE DESC ";
				query = session.createNativeQuery(str);
				siteTempList = query.list(); // To use it in circle range calculation

				System.out.println(" siteTempList "+mapper.writeValueAsString(siteTempList));

				// If circle range is checked
				if (StringUtils.equalsIgnoreCase(circleRangeCheckbox, "true")) {

					if ((radius != null && !radius.equalsIgnoreCase("") && Double.parseDouble(radius) > 0)
							&& (longitude != null && !longitude.equalsIgnoreCase(""))
							&& (latitude != null && !latitude.equalsIgnoreCase(""))) {

						for (int i = 0; i < siteTempList.size(); i++) {
							/*distance = haversineMethod(Double.parseDouble(latitude), Double.parseDouble(longitude),
									Double.valueOf(siteTempList.get(i)[6].toString()),
									Double.valueOf(siteTempList.get(i)[5].toString()));
									
							  if (distance <= Double.parseDouble(radius)) {
								listCircleRange.add(RevenueToAssetRatioList.get(i));
							}*/
							double distance1 = 0.0, distance2 = 0.0;
							if (siteTempList.get(i)[10] != null || siteTempList.get(i)[9] != null ) {
								 distance1 = haversineMethod(Double.parseDouble(latitude), Double.parseDouble(longitude),
					                    Double.valueOf(siteTempList.get(i)[10].toString()),
					                    Double.valueOf(siteTempList.get(i)[9].toString()));
							}
							
							if (siteTempList.get(i)[15] != null || siteTempList.get(i)[14] != null ) {
								 distance2 = haversineMethod(Double.parseDouble(latitude), Double.parseDouble(longitude),
					                    Double.valueOf(siteTempList.get(i)[15].toString()),
					                    Double.valueOf(siteTempList.get(i)[14].toString()));
							}
							

				            if (distance1 <= Double.parseDouble(radius) || distance2 <= Double.parseDouble(radius)) {
				                listCircleRange.add(siteTempList.get(i));
							
				            }
						}
						
						rtn.put("TransactionsGrid", listCircleRange);
					}

				}
				else {
					rtn.put("TransactionsGrid", query.getResultList());
				}
				//System.out.println(" TransactionsGrid "+mapper.writeValueAsString(query.getResultList()));
				
				/*query = session.createNativeQuery(
						"SELECT a.ELEMENT_ID,a.ELEMENT,a.ALM_TRANS_TYPE,a.DISCOVERED_TRANS_TYPE,TO_CHAR(a.PARSING_DATE,'DD-MM-YYYY HH:mm:ss') as startdate,a.FROM_SITE,a.TO_SITE," + 
						" b.FROM_NODE_ID,b.TO_NODE_ID,b.FROM_NODE_TYPE,b.TO_NODE_TYPE,MODEL,a.MAC_ADDRESS,a.SERIAL_NUMBER,a.FROM_CIRCLE,a.TO_CIRCLE,a.APPROVED_BY,a.MODIFIED_BY,a.SENT_TO_ALM,a.ALM_APPROVAL_STATUS" + 
						" FROM NETWORK_TRANSACTION a LEFT JOIN NODE_TRANSACTIONS b on a.node_trans_id=b.node_trans_id "
						+ "WHERE a.PARSING_DATE between TO_DATE('" + StartDate+ "','MM/DD/YYYY HH24:MI:SS')" 
						+ "and TO_DATE('" + EndDate + "','MM/DD/YYYY HH24:MI:SS')" + " ORDER BY a.PARSING_DATE DESC");
				System.out.println(mapper.writeValueAsString(query.getResultList()));
				rtn.put("TransactionsGrid", query.getResultList());
				*/
				
				query = session.createNativeQuery("select nvl(sum (case when DISCOVERED_TRANS_TYPE LIKE '%NEW%' then 1 else 0 end),0) as newelements," + 
						"nvl(sum (case when (DISCOVERED_TRANS_TYPE LIKE'%DISAPPEARED%') then 1 else 0 end),0) as disappeared," + 
						"nvl(sum (case when DISCOVERED_TRANS_TYPE LIKE '%Transfer%' then 1 else 0 end),0) as transferred," + 
						"nvl(sum (case when DISCOVERED_TRANS_TYPE LIKE '%Dismantle%' then 1 else 0 end),0) as dismantled," + 
						"count (DISCOVERED_TRANS_TYPE) as total" + 
						" from NETWORK_TRANSACTION" + 
						" WHERE PARSING_DATE between TO_DATE('" + StartDate+ "','MM/DD/YYYY HH24:MI:SS')" + 
						" and TO_DATE('" + EndDate + "','MM/DD/YYYY HH24:MI:SS')");
				
				Object[] statisticalResult = (Object[]) query.getResultList().get(0);
				//System.out.println(mapper.writeValueAsString(statisticalResult));
				rtn.put("totalbnrTrans", statisticalResult[4]);
				rtn.put("newElements", statisticalResult[0]);
				rtn.put("disappearedElements", statisticalResult[1]);
				rtn.put("transferredElements", statisticalResult[2]);
				rtn.put("dismantledElements", statisticalResult[3]);
				
				
				query = session.createNativeQuery("Select nvl(TO_CHAR(PARSING_DATE,'MM/DD/YYYY HH:mm:ss'), 'N/A')"
						+ " FROM NETWORK_TRANSACTION "
						+ " WHERE PARSING_DATE between TO_DATE('" + StartDate+ "','MM/DD/YYYY HH24:MI:SS')"  
						+ " and TO_DATE('" + EndDate + "','MM/DD/YYYY HH24:MI:SS')"
						+ " order by parsing_date desc FETCH FIRST 1 ROW ONLY");
				rtn.put("lastscanDate", query.uniqueResult());
				//System.out.println(mapper.writeValueAsString(query.uniqueResult()));
				
			} catch (Exception e) {
				logger.info("Error on NetworkTransactionsReport with a message : " + e);
				e.printStackTrace();
				rtn.put("TransactionsGrid", "");
				rtn.put("totalbnrTrans", "0");
				rtn.put("newElements", "0");
				rtn.put("disappearedElements", "0");
				rtn.put("transferredElements", "0");
				rtn.put("dismantledElements", "0");
				rtn.put("lastscanDate", "N/A");
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		return rtn;

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
