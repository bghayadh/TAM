package com.aliat.alm.controller;


import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
public class FinanceDashboardController {

	
	@Autowired
	Notify notifications;
	
	private Session session = null;
	@SuppressWarnings("rawtypes")
	Query query;
	

	private static ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(FinanceDashboardController.class);

	
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
							"SELECT * FROM (SELECT siteName as siteName,"
							+"COALESCE( nullif(SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) / (nullif(SUM(initCost),0) * 100) ,0) as revenueToAssetInit,siteID as siteID,longitude as longitude, "
							+" latitude as latitude FROM ( " + 
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
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteName,siteID,longitude,latitude"
							+ ")ORDER BY (revenueToAssetInit) DESC FETCH NEXT 10 ROWS ONLY"
							);
					
					
					model.addAttribute("topSitesRevToInitList", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName,"
							+"COALESCE( nullif(SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) / (nullif(SUM(initCost),0) * 100) ,0) as revenueToAssetInit,siteID as siteID,longitude as longitude, "
							+" latitude as latitude FROM ( " + 
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
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteName,siteID,longitude,latitude"
							+ ")ORDER BY (revenueToAssetInit) ASC FETCH NEXT 10 ROWS ONLY"
							);
					
					model.addAttribute("leastSitesRevToInitList", mapper.writeValueAsString(query.list()));
										
					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName,"
							+"COALESCE( nullif(SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) / (nullif(SUM(netCost),0) * 100) ,0) as revenueToAssetNet,siteID as siteID,longitude as longitude,  "
							+" latitude as latitude FROM ( " + 
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
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteName,siteID,longitude,latitude"
							+ ")ORDER BY (revenueToAssetNet) DESC FETCH NEXT 10 ROWS ONLY"
							);
					
					model.addAttribute("topSitesRevToNetList", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName,"
							+"COALESCE( nullif(SUM(voiceRevenue)+ SUM(smsRevenue)+ SUM(dataRevneue)+ SUM(vasRevenue),0) / (nullif(SUM(netCost),0) * 100) ,0) as revenueToAssetNet,siteID as siteID,longitude as longitude,  "
							+" latitude as latitude FROM ( " + 
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
							") WHERE (longitude is not null and longitude != '0' and longitude != 'null' and latitude is not null and latitude != '0' and latitude != 'null') GROUP BY siteName,siteID,longitude,latitude"
							+ ")ORDER BY (revenueToAssetNet) ASC FETCH NEXT 10 ROWS ONLY"
							);
					
					model.addAttribute("leastSitesRevToNetList", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName,"
						    + " COALESCE( (nullif(population,0)) / (nullif(SUM(initCost),0) *100) ,0)  as populationToAssetInit,siteID as siteID,longitude as longitude,  " 
							+" latitude as latitude FROM ( " + 
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
							+ ")ORDER BY (populationToAssetInit) DESC FETCH NEXT 10 ROWS ONLY"
							);
					model.addAttribute("topSitesPopulationToInitList", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName,"
						    + " COALESCE( (nullif(population,0)) / (nullif(SUM(initCost),0) *100) ,0)  as populationToAssetInit,siteID as siteID,longitude as longitude,  " 
							+" latitude as latitude FROM ( " + 
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
							+ ")ORDER BY (populationToAssetInit) ASC FETCH NEXT 10 ROWS ONLY"
							);
					
					model.addAttribute("leastSitesPopulationToInitList", mapper.writeValueAsString(query.list()));

				
					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName,"
						    + " COALESCE( (nullif(population,0)) / (nullif(SUM(netCost),0) *100) ,0)  as populationToAssetNet, siteID as siteID, longitude as longitude,  " 
							+" latitude as latitude FROM ( " + 
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
							+ ")ORDER BY (populationToAssetNet) DESC FETCH NEXT 10 ROWS ONLY"
							);
					model.addAttribute("topSitesPopulationToNetList", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT * FROM (SELECT siteName as siteName,"
						    + " COALESCE( (nullif(population,0)) / (nullif(SUM(netCost),0) *100) ,0)  as populationToAssetNet, siteID as siteID, longitude as longitude,  " 
							+" latitude as latitude FROM ( " + 
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
							+ ")ORDER BY (populationToAssetNet) ASC FETCH NEXT 10 ROWS ONLY"
							);
					model.addAttribute("leastSitesPopulationToNetList", mapper.writeValueAsString(query.list()));
					
					
				} catch (Exception e) {
					logger.info("Error in creating session with the DataBase " + e.getMessage());
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
