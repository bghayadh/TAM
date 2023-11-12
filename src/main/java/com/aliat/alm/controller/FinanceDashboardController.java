package com.aliat.alm.controller;


import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
public class FinanceDashboardController {
	
	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;
	
	private Session session = null;
	private Transaction tx = null;

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
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
			
				try {
					query = session.createNativeQuery("SELECT SUM(A.INITIALCOST),B.SITE_ID, B.SITE_NAME "
							+ " FROM fixed_asset_registry A  LEFT JOIN far_site B ON A.FAR_ID = B.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID "
							+ " where (C.longitude is not null and C.longitude != '0' and C.longitude != 'null' and C.latitude is not null and C.latitude != '0' and C.latitude != 'null') GROUP BY B.SITE_ID,B.SITE_NAME  "
							+ " ORDER BY SUM(A.INITIALCOST) DESC FETCH NEXT 10 ROWS ONLY  ");
	                 
					model.addAttribute("sitesMaxInitCostAsset",mapper.writeValueAsString(query.list()));
					
					query = session.createNativeQuery("SELECT SUM(A.INITIALCOST),B.SITE_ID, B.SITE_NAME "
							+ " FROM fixed_asset_registry A  LEFT JOIN far_site B ON A.FAR_ID = B.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID "
							+ " where (C.longitude is not null and C.longitude != '0' and C.longitude != 'null' and C.latitude is not null and C.latitude != '0' and C.latitude != 'null') GROUP BY B.SITE_ID,B.SITE_NAME  "
							+ " ORDER BY SUM(A.INITIALCOST) ASC FETCH NEXT 10 ROWS ONLY  ");
	                 
					model.addAttribute("sitesMinInitCostAsset",mapper.writeValueAsString(query.list()));
								
			
					query = session.createNativeQuery("SELECT SUM(A.NETCOST),B.SITE_ID, B.SITE_NAME "
							+ " FROM fixed_asset_registry A  LEFT JOIN far_site B ON A.FAR_ID = B.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID "
							+ " where (C.longitude is not null and C.longitude != '0' and C.longitude != 'null' and C.latitude is not null and C.latitude != '0' and C.latitude != 'null') GROUP BY B.SITE_ID,B.SITE_NAME  "
							+ " ORDER BY SUM(A.NETCOST) DESC FETCH NEXT 10 ROWS ONLY  ");
	                 
					model.addAttribute("sitesMaxNetCostAsset",mapper.writeValueAsString(query.list()));
					
					query = session.createNativeQuery("SELECT SUM(A.NETCOST),B.SITE_ID, B.SITE_NAME "
							+ " FROM fixed_asset_registry A  LEFT JOIN far_site B ON A.FAR_ID = B.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID "
							+ " where (C.longitude is not null and C.longitude != '0' and C.longitude != 'null' and C.latitude is not null and C.latitude != '0' and C.latitude != 'null') GROUP BY B.SITE_ID,B.SITE_NAME  "
							+ " ORDER BY SUM(A.NETCOST) ASC FETCH NEXT 10 ROWS ONLY  ");
	                 
					model.addAttribute("sitesMinNetCostAsset",mapper.writeValueAsString(query.list()));
								
			
							
				
				} catch (Exception e) {
					logger.info("Error in creating session with the DataBase " + e.getMessage());
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}			
			
			}// end open session condition		
	   }
		
		return "FinanceDashboard";
	}

	
}
