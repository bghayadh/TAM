package com.aliat.alm.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

@Controller
public class LoadersController {

	@Autowired
	ALMSessions almsessions;
	@Autowired
	Notify notifications;
	private static final Logger logger = LoggerFactory.getLogger(LoadersController.class);
	private static Session session = null;
	private static Transaction tx = null;
	
	@SuppressWarnings("rawtypes")
	Query query;

	
	@RequestMapping(value = "/Loaders", method = RequestMethod.GET)
	public String Loaders(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Welcome home! The client locale is {}.", locale);
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		} else {
			session = almsessions.getSession(); 
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
			return "Loaders";
		}
	}
	
	@RequestMapping(value = "/ManholeLoader", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ManholeLoader(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {

			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {				
				tx = session.beginTransaction();
				
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(new Date());
				int year = calendar.get(Calendar.YEAR);
				
				int seq=0,junctionSeq=0,loadedManholeCounter=0,loadedJunctionCounter=0;
				double longitude,latitude;
				String nameDM="",city="",ID="",name="",junctionID="",junctionName="",junctionNameTemp="";
				try {	
				
					seq= Integer.parseInt(
							session.createNativeQuery("SELECT MANHOLE FROM SEQ_TABLE").uniqueResult().toString());
					
					junctionSeq= Integer.parseInt(
							session.createNativeQuery("SELECT JUNCTION FROM SEQ_TABLE").uniqueResult().toString());
					
					if (itemParameters.getDictParameter().size() > 0) {
						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
							if(itemParameters.getDictParameter().get(i).get("nameDM") !=null) {	
							
								longitude=Double.parseDouble(itemParameters.getDictParameter().get(i).get("longitude"));
								latitude=Double.parseDouble(itemParameters.getDictParameter().get(i).get("latitude"));
								nameDM = itemParameters.getDictParameter().get(i).get("nameDM");
								city =	getCity(latitude, longitude);
								ID = (String) ("MH_" + year + "_" +seq);
								
								if(nameDM.contains("J") == true){
									name = "MH_"+city+"_"+year+"_"+seq+"_J";
								 
									junctionID = "JCT_"+year +"_" +junctionSeq;									
									junctionNameTemp = nameDM;
									String[] arrJctName=null ;
									 //Get the junction name from DM name in excel sheet
									 if(nameDM.contains("(") == true){
										 arrJctName = junctionNameTemp.split("\\("); 
										 if(nameDM.charAt(0) =='J' ){
											 junctionName=junctionNameTemp;
										 }
										 else {
											 junctionName = arrJctName[1].replaceAll("[)]", "");
										}
									 }
									 else if(nameDM.contains("-") == true){
										 arrJctName = junctionNameTemp.split("\\-"); 
										 junctionName = arrJctName[1];
									 }
									 else if(nameDM.contains("&") == true){
										junctionName = "JCT_"+city+"_"+year+"_"+junctionSeq;
									 }
									 else {
										junctionName=junctionNameTemp;
									 }
								 
								query = session.createNativeQuery("INSERT INTO JUNCTION(JUNCTION_ID, JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME, LONGITUDE, LATITUDE,CAPACITY,JUNCTION_NUMBER, CITY, "
											+ " CREATION_DATE,LAST_MODIFIED_DATE, PROJECT_ID ) "
											+ " VALUES ('" + junctionID + "','" + junctionName + "','" + ID + "','" + name + "','" + longitude + "','" + latitude + "','24','24','" +city
											+ "',sysdate,sysdate,'CurrentPhysicalLayer')");
								query.executeUpdate();
								junctionSeq++;
								loadedJunctionCounter++;
								 
							}
								
							else {
								name = "MH_"+city+"_"+year+"_"+seq;				
							}
							
							query = session
									.createNativeQuery("INSERT INTO MANHOLE(MANHOLE_ID, MANHOLE_NAME, LONGITUDE, LATITUDE, CITY, PROJECT_ID,"
											+ " CREATION_DATE,LAST_MODIFIED_DATE,DM_NAME )"
											+ " VALUES ('" + ID + "','" + name + "','" + longitude + "','" + latitude + "','" +city
											+ "','CurrentPhysicalLayer',sysdate,sysdate,'" +nameDM+ "')");
							query.executeUpdate();
							seq++;
							loadedManholeCounter++;
							
							
						}
					}
						
						synchronized (this) {
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET MANHOLE = '" + seq +"' ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET JUNCTION = '" + junctionSeq +"' ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
					}
					rtn.put("loadedManholeCounter", loadedManholeCounter);
					rtn.put("loadedJunctionCounter", loadedJunctionCounter);	
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("Error in loading the manhole with a message : " + e);
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
	
	
	@RequestMapping(value = "/HandholeLoader", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> HandholeLoader(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {

			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {				
				tx = session.beginTransaction();
				
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(new Date());
				int year = calendar.get(Calendar.YEAR);
				
				int seq=0,junctionSeq=0,loadedHandholeCounter=0,loadedJunctionCounter=0;
				double longitude,latitude;
				String nameDM="",city="",ID="",name="",junctionID="",junctionName="",junctionNameTemp="";
				try {	
					
					seq= Integer.parseInt(
							session.createNativeQuery("SELECT HANDHOLE FROM SEQ_TABLE").uniqueResult().toString());
					
					junctionSeq= Integer.parseInt(
							session.createNativeQuery("SELECT JUNCTION FROM SEQ_TABLE").uniqueResult().toString());
							
					
					if (itemParameters.getDictParameter().size() > 0) {
						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
							
						  if(itemParameters.getDictParameter().get(i).get("nameDM") !=null) {
							  
							longitude=Double.parseDouble(itemParameters.getDictParameter().get(i).get("longitude"));
							latitude=Double.parseDouble(itemParameters.getDictParameter().get(i).get("latitude"));
							nameDM = itemParameters.getDictParameter().get(i).get("nameDM");
							city =	getCity(latitude, longitude);
							ID = (String) ("HH_" + year + "_" +seq);
							
						
							if(nameDM.contains("J") == true){
								
							 name = "HH_"+city+"_"+year+"_"+seq+"_J";
								
							 //Insert The junction
								 junctionID = "JCT_"+year +"_" +junctionSeq;									
								 junctionNameTemp = nameDM;
								 String[] arrJctName=null ;
								 
							//Get the junction name from DM name in excel sheet
								 if(nameDM.contains("(") == true){
									 arrJctName = junctionNameTemp.split("\\("); 
									 if(nameDM.charAt(0) =='J' ){
										 junctionName=junctionNameTemp;
									 }
									 else {
										 junctionName = arrJctName[1].replaceAll("[)]", "");
									}
								}
								else if(nameDM.contains("-") == true){
									 arrJctName = junctionNameTemp.split("\\-"); 
									 junctionName = arrJctName[1];
								}
								else if(nameDM.contains("&") == true){
									junctionName = "JCT_"+city+"_"+year+"_"+junctionSeq;
								}
								else {
									junctionName=junctionNameTemp;
								}
								 
								query = session.createNativeQuery("INSERT INTO JUNCTION(JUNCTION_ID, JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME, LONGITUDE, LATITUDE,CAPACITY,JUNCTION_NUMBER, CITY, "
											+ " CREATION_DATE,LAST_MODIFIED_DATE, PROJECT_ID ) "
											+ " VALUES ('" + junctionID + "','" + junctionName + "','" + ID + "','" + name + "','" + longitude + "','" + latitude + "','24','24','" +city
											+ "',sysdate,sysdate,'CurrentPhysicalLayer')");
								query.executeUpdate();
								junctionSeq++;
								loadedJunctionCounter++;
							} 
							
							else {
								name = "HH_"+city+"_"+year+"_"+seq;				
							}
							
							query = session
									.createNativeQuery("INSERT INTO HANDHOLE(HANDHOLE_ID, HANDHOLE_NAME, LONGITUDE, LATITUDE, CITY, PROJECT_ID,"
											+ " CREATION_DATE,LAST_MODIFIED_DATE,DM_NAME )"
											+ " VALUES ('" + ID + "','" + name + "','" + longitude + "','" + latitude + "','" +city
											+ "','CurrentPhysicalLayer',sysdate,sysdate,'" +nameDM+ "')");
							query.executeUpdate();
							
							seq++;
							loadedHandholeCounter++;
							
						}
						}
					
						synchronized (this) {
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET HANDHOLE = '" + seq +"' ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET JUNCTION = '" + junctionSeq +"' ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
					}
					rtn.put("loadedHandholeCounter", loadedHandholeCounter);
					rtn.put("loadedJunctionCounter", loadedJunctionCounter);	
					
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("Error in loading the handhole with a message : " + e);
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

	private String getCity(Double latitude, Double longitude) {
		
		String city = "";
		GeoApiContext context = new GeoApiContext.Builder()
			    .apiKey("AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo")
			    .build();
			GeocodingResult[] results = null;
			try {
				results =  GeocodingApi.newRequest(context).latlng(new LatLng(latitude, longitude)).language("en").resultType(AddressType.COUNTRY, AddressType.ADMINISTRATIVE_AREA_LEVEL_4).await();
				if(results.length>0) {
					city = (results[0].formattedAddress.split(","))[0];
				}
				if(city.contains("'")) city = (city.split("'"))[0];
			} catch (ApiException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			context.shutdown();
		return city;
	
	}	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(1500);
	}

}
