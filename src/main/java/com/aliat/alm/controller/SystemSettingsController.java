package com.aliat.alm.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Transaction;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.SystemSettings;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SystemSettingsController {
	private Session session = null;
	private static final Logger logger = LoggerFactory.getLogger(SystemSettingsController.class);
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	
	private static Transaction tx = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	
	
	@Autowired
	Form form;

	@Autowired
	Notify notifications;

	@RequestMapping(value = "/systemSettings", method = RequestMethod.GET)
	public String systemSettings(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		} else {
			session = AlmDbSession.getInstance().getSession();
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

			if (session != null && session.isOpen()) {
				
				try {
					notifications.headerNotifications(session, model);
	
					query = session.createNativeQuery(
							"select SYSTEM_SETTING_ID as sysSettingID, SYSTEM_COUNTRY, LATITUDE, LONGITUDE, SYSTEM_LANGUAGE, "
						  + "SYSTEM_CURRENCY,USERNAME,PASSWORD,PATH,IP_ADDRESS,TO_CHAR(CREATION_DATE,'MM/DD/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFICATION_DATE,'MM/DD/YYYY HH:MI AM') "
						  + "FROM SYSTEM_SETTINGS");
				
					List<Object[]> results = query.getResultList();
					if(results.size()>0) {
						for (Object[] row : results) {
						    model.addAttribute("sysSettingID", row[0]);
						    model.addAttribute("country", row[1]);
						    model.addAttribute("lat", row[2]);
						    model.addAttribute("longitude", row[3]);
						    model.addAttribute("language", row[4]);						    
						    model.addAttribute("currency", row[5]);
						    model.addAttribute("username", row[6]);
						    model.addAttribute("pass", row[7]);
						    model.addAttribute("path", row[8]);
						    model.addAttribute("ipAddress", row[9]);
						    model.addAttribute("createdDate", row[10]); 
						    model.addAttribute("lastModifiedDate", row[11]); 
						}
						model.addAttribute("status","saved");
					}
					
					else {
						model.addAttribute("status","New");
						model.addAttribute("createdDate",formatter.format(new Timestamp(System.currentTimeMillis())).toString());
						model.addAttribute("lastModifiedDate",formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					}
					
	
	
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.info("Error in systemSettings due to \n " + exceptionAsString);
					} finally {
						if (session != null && session.isOpen()) {
							session.close();
						}
						
					}
				}
			
			return "systemSettings";
		}
}

		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/sysSettingSave", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> sysSettingSave(HttpServletRequest request, HttpServletResponse response) {


			Map<String, Object> rtn = new LinkedHashMap<>();
			session = AlmDbSession.getInstance().getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", "redirect:/");
				return rtn;
			}
			else {
				
				System.out.println("Welcome to System Settings");				
				SystemSettings sysSetting=new SystemSettings();
				DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				String createdDate, sysSettingID = "";
					
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(new Date());				
				
				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();
					
					try {				
						sysSettingID = "SYS_SETTING_2025" + "_1";
						sysSetting.setID(sysSettingID);
						sysSetting.setSysLangauge(request.getParameter("language"));
						sysSetting.setSysCountry(request.getParameter("country"));						
						sysSetting.setLat(request.getParameter("lat"));
						System.out.println("The latitude is " +request.getParameter("lat"));
						sysSetting.setLongitude(request.getParameter("longitude"));
						System.out.println("The longitude is " +request.getParameter("longitude"));
						sysSetting.setSysCurrency(request.getParameter("currency"));
						sysSetting.setUsername(request.getParameter("username"));
						sysSetting.setPassword(request.getParameter("pass"));
						sysSetting.setPath(request.getParameter("path"));
						sysSetting.setIpAddress(request.getParameter("ipAddress"));
						createdDate = request.getParameter("creationDate");
						sysSetting.setCreatedDate((new Timestamp(formatter1.parse(createdDate).getTime())));
						sysSetting.setLastModifiedDate(new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));						
						session.saveOrUpdate(sysSetting);
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.info("Error in sysSettingSave due to \n " + exceptionAsString);
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
						}
					}
				}
				return rtn;
			}	
	}
}