package com.aliat.alm.controller;


import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
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

	
}
