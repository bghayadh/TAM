package com.aliat.alm.controller;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.logging.Logger;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
public class FinanceDashboardController {
	private static final Logger logger = Logger.getLogger(FinanceDashboardController.class.getName());
	
	@Autowired
	Notify notifications;
	
	private Session session = null;
	@SuppressWarnings("rawtypes")
	Query query;
	

	private static ObjectMapper mapper = new ObjectMapper();
	private static StringWriter sw;
	private static String exceptionAsString;

	
	@RequestMapping(value = "/FinanceDashboard", method = RequestMethod.GET)
	public String FinanceDashboard(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		else {
			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {
				notifications.headerNotifications(session, model);
			
				try {
				
					query = session.createNativeQuery("SELECT * FROM(SELECT COALESCE(SUM(initCost),0) as initCost,siteID,siteName,longitude,latitude FROM ( SELECT DISTINCT C.SITE_ID AS site,C.WARE_ID as wareID, C.SITE_ID AS siteID, C.WARE_NAME AS siteName , C.LONGITUDE as longitude, "
							+ " C.LATITUDE as latitude, A.INITIALCOST as initCost,A.FAR_ID AS FAR_ID   FROM FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID ) "
							+ " WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteID,siteName,longitude,latitude ) "
							+ " ORDER BY (initCost) DESC FETCH NEXT 10 ROWS ONLY ");
					model.addAttribute("topSitesInitCostAsset",mapper.writeValueAsString(query.list()));
					
					
					query = session.createNativeQuery("SELECT * FROM(SELECT COALESCE(SUM(initCost),0) as initCost,siteID,siteName,longitude,latitude FROM ( SELECT DISTINCT C.SITE_ID AS site,C.WARE_ID as wareID, C.SITE_ID AS siteID, C.WARE_NAME AS siteName , C.LONGITUDE as longitude, "
							+ " C.LATITUDE as latitude, A.INITIALCOST as initCost,A.FAR_ID AS FAR_ID   FROM FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID ) "
							+ " WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteID,siteName,longitude,latitude ) "
							+ " ORDER BY (initCost) ASC FETCH NEXT 10 ROWS ONLY ");					
					model.addAttribute("leastSitesInitCostAsset",mapper.writeValueAsString(query.list()));
								
			
					query = session.createNativeQuery("SELECT * FROM(SELECT COALESCE(SUM(netCost),0) as netCost,siteID,siteName,longitude,latitude FROM ( SELECT DISTINCT C.SITE_ID AS site,C.WARE_ID as wareID, C.SITE_ID AS siteID, C.WARE_NAME AS siteName , C.LONGITUDE as longitude, "
							+ " C.LATITUDE as latitude, A.NETCOST as netCost ,A.FAR_ID AS FAR_ID   FROM FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID ) "
							+ " WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteID,siteName,longitude,latitude ) "
							+ " ORDER BY (netCost) DESC FETCH NEXT 10 ROWS ONLY ");
					model.addAttribute("topSitesNetCostAsset",mapper.writeValueAsString(query.list()));
					
					query = session.createNativeQuery("SELECT * FROM(SELECT COALESCE(SUM(netCost),0) as netCost,siteID,siteName,longitude,latitude FROM ( SELECT DISTINCT C.SITE_ID AS site,C.WARE_ID as wareID, C.SITE_ID AS siteID, C.WARE_NAME AS siteName , C.LONGITUDE as longitude, "
							+ " C.LATITUDE as latitude, A.NETCOST as netCost ,A.FAR_ID AS FAR_ID   FROM FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID ) "
							+ " WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteID,siteName,longitude,latitude ) "
							+ " ORDER BY (netCost) ASC FETCH NEXT 10 ROWS ONLY ");
					model.addAttribute("leastSitesNetCostAsset",mapper.writeValueAsString(query.list()));
										
					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName, siteID as siteID, longitude as longitude, latitude as latitude, "
							+"case (SUM(initCost)) when 0 then -1 when null then -1 else round (COALESCE (SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) * 100 / SUM(initCost),3) end as revenueToAssetInit "
//							+"COALESCE( nullif(SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) / (nullif(SUM(initCost),0) * 100) ,0) as revenueToAssetInit,siteID as siteID,longitude as longitude, "
//							+" latitude as latitude FROM ( " + 
							+ "FROM ( " +
							"SELECT DISTINCT C.WARE_NAME AS siteName , C.SITE_ID AS siteID,C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude, A.INITIALCOST as initCost,A.FAR_ID AS FAR_ID, " + 
							"0 as voiceRevenue, 0 as smsRevenue, 0 as dataRevneue, 0 as vasRevenue " + 
							"FROM FIXED_ASSET_REGISTRY A " + 
							"LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID " + 
							"LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID " + 
							"UNION " + 
							"SELECT DISTINCT C.WARE_NAME AS siteName , C.SITE_ID AS siteID,C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude, 0 as initCost, '' AS FAR_ID, " + 
							"D.VOICE_REVENUE as voiceRevenue, D.SMS_REVENUE as smsRevenue, D.DATA_REVENUE as dataRevneue,D.VAS_REVENUE as vasRevenue " + 
							"FROM rpt_PREPAID_PAYG_REVENUE D " + 
							"LEFT JOIN WAREHOUSE C ON C.SITE_ID = D.SITE_ID " +
							"WHERE D.REVENUE_DATE >=  trunc(SYSDATE - INTERVAL '1' MONTH) AND D.REVENUE_DATE < (trunc(sysdate) ) + 1  " +
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteName,siteID,longitude,latitude"
							+ ")ORDER BY (revenueToAssetInit) DESC FETCH NEXT 10 ROWS ONLY"
							);
					
					
					model.addAttribute("topSitesRevToInitList", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName, siteID as siteID, longitude as longitude, latitude as latitude, "
							+"case (SUM(initCost)) when 0 then -1 when null then -1 else round (COALESCE (SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) * 100 / SUM(initCost),3) end as revenueToAssetInit "
//							+"COALESCE( nullif(SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) / (nullif(SUM(initCost),0) * 100) ,0) as revenueToAssetInit,siteID as siteID,longitude as longitude, "
//							+" latitude as latitude FROM ( " + 
							+ "FROM ( " +
							"SELECT DISTINCT C.WARE_NAME AS siteName , C.SITE_ID AS siteID,C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude, A.INITIALCOST as initCost,A.FAR_ID AS FAR_ID, " + 
							"0 as voiceRevenue, 0 as smsRevenue, 0 as dataRevneue, 0 as vasRevenue " + 
							"FROM FIXED_ASSET_REGISTRY A " + 
							"LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID " + 
							"LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID " + 
							"UNION " + 
							"SELECT DISTINCT C.WARE_NAME AS siteName , C.SITE_ID AS siteID,C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude, 0 as initCost, '' AS FAR_ID, " + 
							"D.VOICE_REVENUE as voiceRevenue, D.SMS_REVENUE as smsRevenue, D.DATA_REVENUE as dataRevneue,D.VAS_REVENUE as vasRevenue " + 
							"FROM rpt_PREPAID_PAYG_REVENUE D " + 
							"LEFT JOIN WAREHOUSE C ON C.SITE_ID = D.SITE_ID " +
							"WHERE D.REVENUE_DATE >=  trunc(SYSDATE - INTERVAL '1' MONTH) AND D.REVENUE_DATE < (trunc(sysdate) ) + 1  " +							
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteName,siteID,longitude,latitude"
							+ ") WHERE revenueToAssetInit >=0 ORDER BY (revenueToAssetInit) ASC FETCH NEXT 200 ROWS ONLY"
							);
					
					model.addAttribute("leastSitesRevToInitList", mapper.writeValueAsString(query.list()));
										
					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName, siteID as siteID, longitude as longitude, latitude as latitude, "
							+"case (SUM(netCost)) when 0 then -1 when null then -1 else round (COALESCE (SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) * 100 / SUM(netCost),3) end as revenueToAssetNet "
							+ "FROM ( " +
							"SELECT DISTINCT C.WARE_NAME AS siteName ,C.SITE_ID AS siteID, C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude, A.NETCOST as netCost ,A.FAR_ID AS FAR_ID, " + 
							"0 as voiceRevenue, 0 as smsRevenue, 0 as dataRevneue, 0 as vasRevenue " + 
							"FROM FIXED_ASSET_REGISTRY A " + 
							"LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID " + 
							"LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID " + 
							"UNION " + 
							"SELECT DISTINCT C.WARE_NAME AS siteName,C.SITE_ID AS siteID , C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude, 0 as netCost, '' AS FAR_ID, " + 
							"D.VOICE_REVENUE as voiceRevenue, D.SMS_REVENUE as smsRevenue, D.DATA_REVENUE as dataRevneue,D.VAS_REVENUE as vasRevenue " + 
							"FROM rpt_PREPAID_PAYG_REVENUE D " + 
							"LEFT JOIN WAREHOUSE C ON C.SITE_ID = D.SITE_ID " +
							"WHERE D.REVENUE_DATE >=  trunc(SYSDATE - INTERVAL '1' MONTH) AND D.REVENUE_DATE < (trunc(sysdate) ) + 1  " +
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteName,siteID,longitude,latitude"
							+ ")ORDER BY (revenueToAssetNet) DESC FETCH NEXT 10 ROWS ONLY"
							);
					
					model.addAttribute("topSitesRevToNetList", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName, siteID as siteID, longitude as longitude, latitude as latitude, "
							+"case (SUM(netCost)) when 0 then -1 when null then -1 else round (COALESCE (SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) * 100 / SUM(netCost),3) end as revenueToAssetNet "
							+ "FROM ( " +
							"SELECT DISTINCT C.WARE_NAME AS siteName ,C.SITE_ID AS siteID, C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude, A.NETCOST as netCost ,A.FAR_ID AS FAR_ID, " + 
							"0 as voiceRevenue, 0 as smsRevenue, 0 as dataRevneue, 0 as vasRevenue " + 
							"FROM FIXED_ASSET_REGISTRY A " + 
							"LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID " + 
							"LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID " + 
							"UNION " + 
							"SELECT DISTINCT C.WARE_NAME AS siteName,C.SITE_ID AS siteID , C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude, 0 as netCost, '' AS FAR_ID, " + 
							"D.VOICE_REVENUE as voiceRevenue, D.SMS_REVENUE as smsRevenue, D.DATA_REVENUE as dataRevneue,D.VAS_REVENUE as vasRevenue " + 
							"FROM rpt_PREPAID_PAYG_REVENUE D " + 
							"LEFT JOIN WAREHOUSE C ON C.SITE_ID = D.SITE_ID " +
							"WHERE D.REVENUE_DATE >=  trunc(SYSDATE - INTERVAL '1' MONTH) AND D.REVENUE_DATE < (trunc(sysdate) ) + 1  " +							
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteName,siteID,longitude,latitude"
							+ ") WHERE revenueToAssetNet >=0 ORDER BY (revenueToAssetNet) ASC FETCH NEXT 200 ROWS ONLY"
							);
					
					model.addAttribute("leastSitesRevToNetList", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName, siteID as siteID,longitude as longitude, latitude as latitude,"
							+ " case (SUM(initCost)) when 0 then -1 when null then -1 else round (COALESCE (population,0) * 100 / SUM(initCost),3) end as populationToAssetInit"						    
							+" FROM ( " + 
							"SELECT DISTINCT C.WARE_NAME AS siteName,C.SITE_ID AS siteID, C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude,C.POPULATION AS population, A.INITIALCOST as initCost,A.FAR_ID AS FAR_ID " + 
							"FROM FIXED_ASSET_REGISTRY A " + 
							"LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID " + 
							"LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID " + 
							"UNION " + 
							"SELECT DISTINCT C.WARE_NAME AS siteName,C.SITE_ID AS siteID , C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude,C.POPULATION AS population, 0 as initCost, '' AS FAR_ID " + 
							"FROM rpt_PREPAID_PAYG_REVENUE D " + 
							"LEFT JOIN WAREHOUSE C ON C.SITE_ID = D.SITE_ID " + 
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteName,siteID,longitude,latitude,population"
							+ ") WHERE populationToAssetInit > 0 ORDER BY (populationToAssetInit) DESC FETCH NEXT 10 ROWS ONLY"
							);
					model.addAttribute("topSitesPopulationToInitList", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName, siteID as siteID,longitude as longitude, latitude as latitude,"
							+ " case (SUM(initCost)) when 0 then -1 when null then -1 else round (COALESCE (population,0) * 100 / SUM(initCost),3) end as populationToAssetInit"						    
							+" FROM ( " + 
							"SELECT DISTINCT C.WARE_NAME AS siteName,C.SITE_ID AS siteID, C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude,C.POPULATION AS population, A.INITIALCOST as initCost,A.FAR_ID AS FAR_ID " + 
							"FROM FIXED_ASSET_REGISTRY A " + 
							"LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID " + 
							"LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID " + 
							"UNION " + 
							"SELECT DISTINCT C.WARE_NAME AS siteName,C.SITE_ID AS siteID , C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude,C.POPULATION AS population, 0 as initCost, '' AS FAR_ID " + 
							"FROM rpt_PREPAID_PAYG_REVENUE D " + 
							"LEFT JOIN WAREHOUSE C ON C.SITE_ID = D.SITE_ID " + 
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteName,siteID,longitude,latitude,population"
							+ ") WHERE populationToAssetInit > 0 ORDER BY (populationToAssetInit) ASC FETCH NEXT 10 ROWS ONLY"
							);
					
					model.addAttribute("leastSitesPopulationToInitList", mapper.writeValueAsString(query.list()));

				
					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName, siteID as siteID,longitude as longitude, latitude as latitude,"
							+ " case (SUM(netCost)) when 0 then -1 when null then -1 else round (COALESCE (population,0) * 100 / SUM(netCost),3) end as populationToAssetNet"						    
							+" FROM ( " + 
							"SELECT DISTINCT C.WARE_NAME AS siteName ,C.SITE_ID AS siteID, C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude,C.POPULATION AS population, A.NETCOST as netCost,A.FAR_ID AS FAR_ID " + 
							"FROM FIXED_ASSET_REGISTRY A " + 
							"LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID " + 
							"LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID " + 
							"UNION " + 
							"SELECT DISTINCT C.WARE_NAME AS siteName,C.SITE_ID AS siteID , C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude,C.POPULATION AS population, 0 as netCost, '' AS FAR_ID " + 
							"FROM rpt_PREPAID_PAYG_REVENUE D " + 
							"LEFT JOIN WAREHOUSE C ON C.SITE_ID = D.SITE_ID " + 
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteName,siteID,longitude,latitude,population"
							+ ")WHERE populationToAssetNet > 0 ORDER BY (populationToAssetNet) DESC FETCH NEXT 10 ROWS ONLY"
							);
					model.addAttribute("topSitesPopulationToNetList", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName, siteID as siteID,longitude as longitude, latitude as latitude,"
							+ " case (SUM(netCost)) when 0 then -1 when null then -1 else round (COALESCE (population,0) * 100 / SUM(netCost),3) end as populationToAssetNet"						    
							+" FROM ( " + 
							"SELECT DISTINCT C.WARE_NAME AS siteName ,C.SITE_ID AS siteID, C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude,C.POPULATION AS population, A.NETCOST as netCost,A.FAR_ID AS FAR_ID " + 
							"FROM FIXED_ASSET_REGISTRY A " + 
							"LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID " + 
							"LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID " + 
							"UNION " + 
							"SELECT DISTINCT C.WARE_NAME AS siteName,C.SITE_ID AS siteID , C.LONGITUDE as longitude, " + 
							"C.LATITUDE as latitude,C.POPULATION AS population, 0 as netCost, '' AS FAR_ID " + 
							"FROM rpt_PREPAID_PAYG_REVENUE D " + 
							"LEFT JOIN WAREHOUSE C ON C.SITE_ID = D.SITE_ID " + 
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteName,siteID,longitude,latitude,population"
							+ ")WHERE populationToAssetNet > 0 ORDER BY (populationToAssetNet) ASC FETCH NEXT 10 ROWS ONLY"
							);
					model.addAttribute("leastSitesPopulationToNetList", mapper.writeValueAsString(query.list()));					
					
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in RevenueToAssetRatioReport due to \n " + exceptionAsString);
					logger.info("Error in RevenueToAssetRatioReport due to \n " + exceptionAsString);
					e.printStackTrace();
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}			
			
			}// end open session condition		
	   }
		
		return "FinanceDashboard";
	}
	@SuppressWarnings({ "unchecked", "null" })
	private JSONArray generateRatioChartData(List<Object> defaultChartRevenueReport) {
		JSONArray siteNames = new JSONArray(), revToAssetRatio = new JSONArray(), popToAssetRatio = new JSONArray();
		JSONArray objRevenue = new JSONArray();
		JSONObject json;

		if (defaultChartRevenueReport != null && defaultChartRevenueReport.size() != 0) {
			List<Object> c = defaultChartRevenueReport;
			Object[] fof;
			for (int i = 0; i < c.size(); i++) {
				fof = (Object[]) c.get(i);

				if (fof[0] != null) {
					json = new JSONObject();
					json.put("label", StringUtils.equalsIgnoreCase((String) fof[0], "null") ? "No site name" : fof[0]);
					siteNames.add(json);
				} else {
					json = new JSONObject();
					json.put("label", "No Data");

					siteNames.add(json);
				}

				if (fof[1] != null) {
					json = new JSONObject();
					json.put("value", fof[1]);
					revToAssetRatio.add(json);

				} else {
					json = new JSONObject();
					json.put("value", 0);
					revToAssetRatio.add(json);

				}
				if (fof[2] != null) {
					json = new JSONObject();
					json.put("value", fof[2]);
					popToAssetRatio.add(json);

				} else {
					json = new JSONObject();
					json.put("value", 0);
					popToAssetRatio.add(json);

				}
				
			

			}
		} else {
			json = new JSONObject();
			json.put("label", "No Data");
			siteNames.add(json);
			json = new JSONObject();
			json.put("value", 0);
			revToAssetRatio.add(json);
			popToAssetRatio.add(json);
		}

		objRevenue.add(siteNames);
		objRevenue.add(revToAssetRatio);
		objRevenue.add(popToAssetRatio);
		return objRevenue;
	}

	
}
