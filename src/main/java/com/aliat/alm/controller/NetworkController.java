package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Logger;

@Controller
public class NetworkController {
	private static final Logger logger =  Logger.getLogger(NetworkController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
//	private static final Logger logger = LoggerFactory.getLogger(NetworkController.class);
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws JsonProcessingException
	 */

	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;
	
		
	private static String AppendQuery(String a, int [] arrayParam, String str) {
	if(arrayParam[0] == 1 || arrayParam[1] == 1 || arrayParam[2] == 1 || arrayParam[3] == 1) {
		str = str +" and ( ";
		
		if (arrayParam[0] == 1) {
			//System.out.println("ent ");
			str= str +a+".DOMAIN ='Enterprise' ";
		}
		if (arrayParam[1] == 1) {
			if(arrayParam[0] == 0) {
				//System.out.println("trans");
				str= str + a +".DOMAIN ='Transmission' ";
			}else {
				str= str + " or " +a+".DOMAIN ='Transmission' ";
			}
		}
		if (arrayParam[2] == 1) {
			if(arrayParam[0] == 0 && arrayParam[1] == 0) {
				//System.out.println("acc ");
				str= str+ a+".DOMAIN ='Access' ";
			}else {
				str= str+ " or " +a+".DOMAIN ='Access' ";
			}
		}
		if (arrayParam[3] == 1) {
			if(arrayParam[0] == 0 && arrayParam[1] == 0 && arrayParam[2] == 0) {
				//System.out.println("core");
				str= str + a+ ".DOMAIN ='Core' ";
			}else {
				str= str + "or " +a+ ".DOMAIN ='Core' ";
			}					
		}	
		str = str + " ) ";
	}
	 return str;
}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Network_StNdCell", method = RequestMethod.GET)
	public String Network_StNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			double longitude = 0;
			double latitude = 0;
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String access = request.getParameter("access");
				String core = request.getParameter("core");

				int[] arrayParam = new int[4]; // Assuming you want to store 4 parameters
		        arrayParam[0] = 0; // enterprise
		        arrayParam[1] = 0; // transmission
		        arrayParam[2] = 0; // access
		        arrayParam[3] = 0; // core

				String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
						+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
				
				String strNodes ="SELECT NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,"
						+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";
				
				List<Object[]> cellResult = new ArrayList<Object[]>();
				String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' "; 
				String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' "; 
				String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' ";		
				
			try {
				if (enterprise != null && !enterprise.equals("null")) {
					arrayParam[0] = 1;	
					model.addAttribute("EnterpriseBtn", arrayParam[0]);
				}
				if (transmission != null && !transmission.equals("null")) {
					arrayParam[1] = 1;	
					model.addAttribute("transmBtn", arrayParam[1]);
				}
				if (access != null && !access.equals("null")) {
					arrayParam[2] = 1;	
					model.addAttribute("accessDBtn", arrayParam[2]);
				}
				if (core != null && !core.equals("null")) {
					arrayParam[3] = 1;	
					model.addAttribute("CoreBtn", arrayParam[3]);
				}
				strSites = AppendQuery("w",arrayParam, strSites);
				
				strSites = strSites + ") as countNodes,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("c",arrayParam, strSites);
				
				strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("d",arrayParam, strSites);	
				
				strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("e",arrayParam, strSites);
				
				strSites = strSites + ") as countUcells FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.ACTIVE_RECORD = '1' ";			
				strSites=  AppendQuery("b",arrayParam, strSites);
				
				model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				}catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites Data from database in method Network_StNdCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Sites Data from database in method Network_StNdCell due to \n " + exceptionAsString);															
					model.addAttribute("listSites", "null");
				}
				
			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
			try {
				strNodes= AppendQuery("b",arrayParam, strNodes);
			
				strNodes= strNodes + ") as countGcells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and c.ACTIVE_RECORD = '1' ";
				strNodes= AppendQuery("c",arrayParam, strNodes);
		
				strNodes= strNodes + ") as countLcells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and d.ACTIVE_RECORD = '1' ";
				strNodes= AppendQuery("d",arrayParam, strNodes);
			
				strNodes= strNodes + ") as countUcells,SUPPLIER_ID FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' ";
				strNodes= AppendQuery("a",arrayParam, strNodes);
					
				model.addAttribute("listNodes", mapper.writeValueAsString(session.createNativeQuery(strNodes).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Nodes Data from database in method Network_StNdCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Nodes Data from database in method Network_StNdCell due to \n " + exceptionAsString);															
					model.addAttribute("listNodes", "null");
			}	
			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
			try {			
				strCells1= AppendQuery("a",arrayParam, strCells1);
				strCells2= AppendQuery("a",arrayParam, strCells2);
				strCells3= AppendQuery("a",arrayParam, strCells3);
							
				cellResult.addAll(session.createNativeQuery(strCells1).list());
				cellResult.addAll(session.createNativeQuery(strCells2).list());
				cellResult.addAll(session.createNativeQuery(strCells3).list());
				
				model.addAttribute("listCells", mapper.writeValueAsString(cellResult));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));				
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Cells Data from database in method Network_StNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Cells Data from database in method Network_StNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listCells", "null");
			}
				try {
					List<Object[]> coordinates = new ArrayList<Object[]>();
					Object[] blankGisLocation = null;
					coordinates = session.createNativeQuery(
							"SELECT COALESCE((MAX(CAST(LONGITUDE as number))+MIN(CAST(LONGITUDE as number)))/2,0) ,"
									+ "COALESCE( (MAX(CAST(LATITUDE as number))+MIN(CAST(LATITUDE as number)))/2,0)FROM WAREHOUSE")
							.list();
					blankGisLocation = (Object[]) coordinates.toArray()[0];
					longitude = Double.valueOf(blankGisLocation[0].toString()).doubleValue();
					latitude = Double.valueOf(blankGisLocation[1].toString()).doubleValue();
					model.addAttribute("Long", longitude);
					model.addAttribute("Lat", latitude);
				} catch (Exception e) {					
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving BOQ Data from database in method Network_StNdCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving BOQ Data from database in method Network_StNdCell due to \n " + exceptionAsString);										
					model.addAttribute("Long", null);
					model.addAttribute("Lat", null);
				}
				
				finally {
					if (session != null && session.isOpen()) {
						logger.info("Session Closseeed");
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return "Network/Network_StNdCell";
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Network_StNdTypNdCell", method = RequestMethod.GET)
	public String Network_StNdTypNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			double longitude = 0;
			double latitude = 0;
			session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			String strNodes ="SELECT NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,"
					+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";
			
			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' "; 
			String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' "; 
			String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' ";		
						
			String strNodesType ="SELECT DISTINCT a.NODE_TYPE,a.WARE_ID FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' ";

				try {
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;	
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (access != null && !access.equals("null")) {
						arrayParam[2] = 1;	
						model.addAttribute("accessDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;	
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					strSites = AppendQuery("w",arrayParam, strSites);			
					strSites = strSites + ") as countNodes,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("c",arrayParam, strSites);
					strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("d",arrayParam, strSites);	
					strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("e",arrayParam, strSites);
					strSites = strSites + ") as countUcells FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.ACTIVE_RECORD = '1' ";			
					strSites=  AppendQuery("b",arrayParam, strSites);	
						
					model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method Network_StNdTypNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method Network_StNdTypNdCell due to \n " + exceptionAsString);																			
				model.addAttribute("listSites", "null");
			}
			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//			
			try {				
				strNodes= AppendQuery("b",arrayParam, strNodes);				
				strNodes= strNodes + ") as countGcells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and c.ACTIVE_RECORD = '1' ";
				strNodes= AppendQuery("c",arrayParam, strNodes);
				strNodes= strNodes + ") as countLcells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and d.ACTIVE_RECORD = '1' ";
				strNodes= AppendQuery("d",arrayParam, strNodes);
				strNodes= strNodes + ") as countUcells,SUPPLIER_ID FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' ";
				strNodes= AppendQuery("a",arrayParam, strNodes);
					
				model.addAttribute("listNodes", mapper.writeValueAsString(session.createNativeQuery(strNodes).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Nodes Data from database in method Network_StNdTypNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Nodes Data from database in method Network_StNdTypNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listNodes", "null");
			}

			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
			try {
				strCells1= AppendQuery("a",arrayParam, strCells1);
				strCells2= AppendQuery("a",arrayParam, strCells2);
				strCells3= AppendQuery("a",arrayParam, strCells3);
							
				cellResult.addAll(session.createNativeQuery(strCells1).list());
				cellResult.addAll(session.createNativeQuery(strCells2).list());
				cellResult.addAll(session.createNativeQuery(strCells3).list());
				
				model.addAttribute("listCells", mapper.writeValueAsString(cellResult));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Cells Data from database in method Network_StNdTypNdCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Cells Data from database in method Network_StNdTypNdCell due to \n " + exceptionAsString);															
					model.addAttribute("listCells", "null");
				}			
				try {
					strNodesType =AppendQuery("a",arrayParam, strNodesType);
					
					model.addAttribute("listNodesType", mapper.writeValueAsString(session.createNativeQuery(strNodesType).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Node Tyoe Data from database in method Network_StNdTypNdCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Node Type Data from database in method Network_StNdTypNdCell due to \n " + exceptionAsString);															
					model.addAttribute("listNodesType", null);
				}
	
				try {
					List<Object[]> coordinates = new ArrayList<Object[]>();
					Object[] blankGisLocation = null;
					coordinates = session.createNativeQuery(
							"SELECT COALESCE((MAX(CAST(LONGITUDE as number))+MIN(CAST(LONGITUDE as number)))/2,0) ,"
									+ "COALESCE( (MAX(CAST(LATITUDE as number))+MIN(CAST(LATITUDE as number)))/2,0)FROM WAREHOUSE")
							.list();
					blankGisLocation = (Object[]) coordinates.toArray()[0];
					longitude = Double.valueOf(blankGisLocation[0].toString()).doubleValue();
					latitude = Double.valueOf(blankGisLocation[1].toString()).doubleValue();
					model.addAttribute("Long", longitude);
					model.addAttribute("Lat", latitude);

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving BOQ Data from database in method Network_StNdTypNdCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving BOQ Data from database in method Network_StNdTypNdCell due to \n " + exceptionAsString);															
					model.addAttribute("Long", null);
					model.addAttribute("Lat", null);
				}

				finally {
					if (session != null && session.isOpen()) {
						logger.info("Session Closseeed");
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return "Network/Network_StNdTypNdCell";
		}
	}
	
	
	
@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_NdTypStNdCell", method = RequestMethod.GET)
public String Network_NdTypStNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		double longitude = 0;
		double latitude = 0;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
						
			String strNodesType ="SELECT DISTINCT NODE_TYPE,(select COUNT(*) from NODE_ACTIVE a  where a.NODE_TYPE=b.NODE_TYPE and a.ACTIVE_RECORD = '1' ";			
			
	        try {
	        	if (enterprise != null && !enterprise.equals("null")) {
					arrayParam[0] = 1;	
					model.addAttribute("EnterpriseBtn", arrayParam[0]);
				}
				if (transmission != null && !transmission.equals("null")) {
					arrayParam[1] = 1;	
					model.addAttribute("transmBtn", arrayParam[1]);
				}
				if (access != null && !access.equals("null")) {
					arrayParam[2] = 1;
					model.addAttribute("accessDBtn", arrayParam[2]);
				}
				if (core != null && !core.equals("null")) {
					arrayParam[3] = 1;	
					model.addAttribute("CoreBtn", arrayParam[3]);
				}
				strSites = AppendQuery("w",arrayParam, strSites);			
				strSites = strSites + ") as countNodes,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("c",arrayParam, strSites);
				strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("d",arrayParam, strSites);	
				strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("e",arrayParam, strSites);
				strSites = strSites + ") as countUcells FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.ACTIVE_RECORD = '1' ";			
				strSites=  AppendQuery("b",arrayParam, strSites);	
				model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));					
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method Network_NdTypStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method Network_NdTypStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listSites", "null");
			}	
			
			try {
				strNodesType = AppendQuery("a",arrayParam, strNodesType);			
				strNodesType = strNodesType + ") as countNodes FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' ";
				strNodesType=  AppendQuery("b",arrayParam, strNodesType);
				
				model.addAttribute("listNodesType", mapper.writeValueAsString(session.createNativeQuery(strNodesType).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));	
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Node Type Data from database in method Network_NdTypStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Node Type Data from database in method Network_NdTypStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listNodesType", null);
			}		
			try {
				List<Object[]> coordinates = new ArrayList<Object[]>();
				Object[] blankGisLocation = null;
				coordinates = session.createNativeQuery(
						"SELECT COALESCE((MAX(CAST(LONGITUDE as number))+MIN(CAST(LONGITUDE as number)))/2,0) ,"
								+ "COALESCE( (MAX(CAST(LATITUDE as number))+MIN(CAST(LATITUDE as number)))/2,0)FROM WAREHOUSE")
						.list();
				blankGisLocation = (Object[]) coordinates.toArray()[0];
				longitude = Double.valueOf(blankGisLocation[0].toString()).doubleValue();
				latitude = Double.valueOf(blankGisLocation[1].toString()).doubleValue();
				model.addAttribute("Long", longitude);
				model.addAttribute("Lat", latitude);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving BOQ Data from database in method Network_NdTypStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving BOQ Data from database in method Network_NdTypStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("Long", null);
				model.addAttribute("Lat", null);
			}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_NdTypStNdCell";
	}
}


@RequestMapping(value = "/Network_SupStNdCell", method = RequestMethod.GET)
public String Network_SupStNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			String strSup ="SELECT distinct a.SUPPLIER_ID,a.SUPPLIER_NAME FROM NODE_ACTIVE a where a.ACTIVE_RECORD = '1' "
					+ "and a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null and a.SUPPLIER_ID!='null' and a.SUPPLIER_ID is not null and a.SUPPLIER_ID!='0' ";

				try {
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;	
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;	
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (access != null && !access.equals("null")) {
						arrayParam[2] = 1;	
						model.addAttribute("accessDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;	
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					strSites = AppendQuery("w",arrayParam, strSites);			
					strSites = strSites + ") as countNodes,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("c",arrayParam, strSites);
					strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("d",arrayParam, strSites);	
					strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("e",arrayParam, strSites);
					strSites = strSites + ") as countUcells FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.ACTIVE_RECORD = '1' AND b.SUPPLIER_ID!='null' and b.SUPPLIER_ID!='0' and b.SUPPLIER_ID is not null  ";			
					strSites=  AppendQuery("b",arrayParam, strSites);	
						
					model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method Network_SupStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method Network_SupStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listSites", "null");
			}			
			try {
				strSup=  AppendQuery("a",arrayParam, strSup);	
				model.addAttribute("listSupp", mapper.writeValueAsString(session.createNativeQuery(strSup).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));				
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Suppliers Data from database in method Network_SupStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Suppliers Data from database in method Network_SupStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listSupp", "null");
			}	
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_SupStNdCell";
	}
}


@RequestMapping(value = "/Network_VnStNdCell", method = RequestMethod.GET)
public String Network_VnStNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			String strVen ="SELECT distinct a.VENDOR FROM NODE_ACTIVE a where a.ACTIVE_RECORD = '1' "
					+ "and a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null and a.VENDOR!='null' and a.VENDOR is not null and a.VENDOR!='0' ";
				try {
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;	
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;	
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (access != null && !access.equals("null")) {
						arrayParam[2] = 1;	
						model.addAttribute("accessDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;	
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					strSites = AppendQuery("w",arrayParam, strSites);			
					strSites = strSites + ") as countNodes,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("c",arrayParam, strSites);
					strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("d",arrayParam, strSites);	
					strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("e",arrayParam, strSites);
					strSites = strSites + ") as countUcells FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.ACTIVE_RECORD = '1' AND b.VENDOR!='null' and b.VENDOR!='0' and b.VENDOR is not null  ";			
					strSites=  AppendQuery("b",arrayParam, strSites);	
						
					model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method Network_VnStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method Network_VnStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listSites", "null");
			}
			
			try {
				strVen=  AppendQuery("a",arrayParam, strVen);					
				model.addAttribute("listVen", mapper.writeValueAsString(session.createNativeQuery(strVen).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));				
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Vendors Data from database in method Network_VnStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Vendors Data from database in method Network_VnStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listVen", "null");
			}	
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_VnStNdCell";
	}
}


@RequestMapping(value = "/Network_VnStNdTypNdCell", method = RequestMethod.GET)
public String Network_VnStNdTypNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			
			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			String strVen ="SELECT distinct a.VENDOR FROM NODE_ACTIVE a where a.ACTIVE_RECORD = '1' "
					+ "and a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null and a.VENDOR!='null' and a.VENDOR is not null and a.VENDOR!='0' ";

				try {
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;	
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;	
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (access != null && !access.equals("null")) {
						arrayParam[2] = 1;	
						model.addAttribute("accessDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;	
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					strSites = AppendQuery("w",arrayParam, strSites);			
					strSites = strSites + ") as countNodes,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("c",arrayParam, strSites);
					strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("d",arrayParam, strSites);	
					strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("e",arrayParam, strSites);
					strSites = strSites + ") as countUcells FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.ACTIVE_RECORD = '1' AND b.VENDOR!='null' and b.VENDOR!='0' and b.VENDOR is not null  ";			
					strSites=  AppendQuery("b",arrayParam, strSites);	
						
					model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method Network_VnStNdTypNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method Network_VnStNdTypNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listSites", "null");
			}			
			try {
				strVen=  AppendQuery("a",arrayParam, strVen);
				model.addAttribute("listVen", mapper.writeValueAsString(session.createNativeQuery(strVen).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));				
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Vendors Data from database in method Network_VnStNdTypNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Vendors Data from database in method Network_VnStNdTypNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listVen", "null");
			}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_VnStNdTypNdCell";
	}
}	
	

@RequestMapping(value = "/Network_SupStNdTypNdCell", method = RequestMethod.GET)
public String Network_SupStNdTypNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			
			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			String strSup ="SELECT distinct a.SUPPLIER_ID,a.SUPPLIER_NAME FROM NODE_ACTIVE a where a.ACTIVE_RECORD = '1' and "
					+ "a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null and a.SUPPLIER_ID!='null' and a.SUPPLIER_ID is not null and a.SUPPLIER_ID!='0' ";

				try {
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;	
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;	
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (access != null && !access.equals("null")) {
						arrayParam[2] = 1;	
						model.addAttribute("accessDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;	
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					strSites = AppendQuery("w",arrayParam, strSites);			
					strSites = strSites + ") as countNodes,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("c",arrayParam, strSites);
					strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("d",arrayParam, strSites);	
					strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("e",arrayParam, strSites);
					strSites = strSites + ") as countUcells FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.ACTIVE_RECORD = '1' AND b.SUPPLIER_ID!='null' and b.SUPPLIER_ID!='0' and b.SUPPLIER_ID is not null  ";			
					strSites=  AppendQuery("b",arrayParam, strSites);	
						
					model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites Data from database in method Network_SupStNdTypNdCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Sites Data from database in method Network_SupStNdTypNdCell due to \n " + exceptionAsString);															
					model.addAttribute("listSites", "null");
				}					
				try {
					strSup=  AppendQuery("a",arrayParam, strSup);											
					model.addAttribute("listSupp", mapper.writeValueAsString(session.createNativeQuery(strSup).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Suppliers Data from database in method Network_SupStNdTypNdCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Suppliers Data from database in method Network_SupStNdTypNdCell due to \n " + exceptionAsString);															
					model.addAttribute("listSupp", "null");
				}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_SupStNdTypNdCell";
	}
}	
	

@RequestMapping(value = "/Network_SupNdTypStNdCell", method = RequestMethod.GET)
public String Network_SupNdTypStCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			String strSup ="SELECT distinct a.SUPPLIER_ID,a.SUPPLIER_NAME FROM NODE_ACTIVE a where a.ACTIVE_RECORD = '1' and "
					+ "a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null and a.SUPPLIER_ID!='0' and a.SUPPLIER_ID is not null and a.SUPPLIER_ID!='null' ";
			
			try {
				if (enterprise != null && !enterprise.equals("null")) {
					arrayParam[0] = 1;	
					model.addAttribute("EnterpriseBtn", arrayParam[0]);
				}
				if (transmission != null && !transmission.equals("null")) {
					arrayParam[1] = 1;	
					model.addAttribute("transmBtn", arrayParam[1]);
				}
				if (access != null && !access.equals("null")) {
					arrayParam[2] = 1;
					model.addAttribute("accessDBtn", arrayParam[2]);
				}
				if (core != null && !core.equals("null")) {
					arrayParam[3] = 1;	
					model.addAttribute("CoreBtn", arrayParam[3]);
				}
				strSites = AppendQuery("w",arrayParam, strSites);			
				strSites = strSites + ") as countNodes,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("c",arrayParam, strSites);
				strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("d",arrayParam, strSites);	
				strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("e",arrayParam, strSites);
				strSites = strSites + ") as countUcells FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.ACTIVE_RECORD = '1' AND b.SUPPLIER_ID!='null' and b.SUPPLIER_ID!='0' and b.SUPPLIER_ID is not null  ";			
				strSites=  AppendQuery("b",arrayParam, strSites);	
					
				model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method Network_SupNdTypStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method Network_SupNdTypStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listSites", "null");
			}			
		 try {
				strSup=  AppendQuery("a",arrayParam, strSup);	
				model.addAttribute("listSupp", mapper.writeValueAsString(session.createNativeQuery(strSup).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Suppliers Data from database in method Network_SupNdTypStNdCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Suppliers Data from database in method Network_SupNdTypStNdCell due to \n " + exceptionAsString);															
					model.addAttribute("listSupp", "null");
				}
		 
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_SupNdTypStNdCell";
	}
}


@RequestMapping(value = "/Network_VnNdTypStNdCell", method = RequestMethod.GET)
public String Network_VnNdTypStCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			String strVen ="SELECT distinct a.VENDOR FROM NODE_ACTIVE a where a.ACTIVE_RECORD = '1' and "
					+ "a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null and a.VENDOR!='0' and a.VENDOR is not null and a.VENDOR!='null' ";
			
			try {
				if (enterprise != null && !enterprise.equals("null")) {
					arrayParam[0] = 1;	
					model.addAttribute("EnterpriseBtn", arrayParam[0]);
				}
				if (transmission != null && !transmission.equals("null")) {
					arrayParam[1] = 1;	
					model.addAttribute("transmBtn", arrayParam[1]);
				}
				if (access != null && !access.equals("null")) {
					arrayParam[2] = 1;
					model.addAttribute("accessDBtn", arrayParam[2]);
				}
				if (core != null && !core.equals("null")) {
					arrayParam[3] = 1;	
					model.addAttribute("CoreBtn", arrayParam[3]);
				}
				strSites = AppendQuery("w",arrayParam, strSites);			
				strSites = strSites + ") as countNodes,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("c",arrayParam, strSites);
				strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("d",arrayParam, strSites);	
				strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("e",arrayParam, strSites);
				strSites = strSites + ") as countUcells FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.ACTIVE_RECORD = '1' AND b.VENDOR!='null' and b.VENDOR!='0' and b.VENDOR is not null  ";			
				strSites=  AppendQuery("b",arrayParam, strSites);	
					
				model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method Network_VnNdTypStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method Network_VnNdTypStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listSites", "null");
			}			
		 try {
				strVen=  AppendQuery("a",arrayParam, strVen);	
				model.addAttribute("listVen", mapper.writeValueAsString(session.createNativeQuery(strVen).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));				
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Vendors Data from database in method Network_VnNdTypStNdCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Vendors Data from database in method Network_VnNdTypStNdCell due to \n " + exceptionAsString);															
					model.addAttribute("listVen", "null");
				}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_VnNdTypStNdCell";
	}
}

@RequestMapping(value = "/Network_PoSiteItem", method = RequestMethod.GET)
public String Network_PoSiteItem(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strSites ="SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,"
					+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
					+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			String strPO ="SELECT distinct a.PO_ID FROM ASSET_REGISTRY a WHERE a.PO_ID!='0' and a.PO_ID is not null and a.PO_ID!='null' ";
			
		 try {	
			 if (enterprise != null && !enterprise.equals("null")) {
					arrayParam[0] = 1;	
					model.addAttribute("EnterpriseBtn", arrayParam[0]);
				}
				if (transmission != null && !transmission.equals("null")) {
					arrayParam[1] = 1;	
					model.addAttribute("transmBtn", arrayParam[1]);
				}
				if (access != null && !access.equals("null")) {
					arrayParam[2] = 1;	
					model.addAttribute("accessDBtn", arrayParam[2]);
				}
				if (core != null && !core.equals("null")) {
					arrayParam[3] = 1;	
					model.addAttribute("CoreBtn", arrayParam[3]);
				}
				strSites = AppendQuery("w",arrayParam, strSites);			
				strSites = strSites + ") as countNodes,(select COUNT(*) from ASSET_REGISTRY j where  j.AR_ID=b.AR_ID and j.PO_ID!='null' ";
				strSites=  AppendQuery("j",arrayParam, strSites);
				strSites = strSites + ") as countItems,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";				
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("c",arrayParam, strSites);
				strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("d",arrayParam, strSites);	
				strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("e",arrayParam, strSites);
				strSites = strSites + ") as countUcells FROM AR_SITE b,ASSET_REGISTRY j where j.AR_ID=b.AR_ID and b.WARE_ID!='0' and b.WARE_ID is not null and b.WARE_ID!='null' ";			
				strSites=  AppendQuery("j",arrayParam, strSites);	
					
				model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method Network_PoSiteItem due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method Network_PoSiteItem due to \n " + exceptionAsString);															
				model.addAttribute("listSites", "null");
			}
			try {
				strPO=  AppendQuery("a",arrayParam, strPO);	
				model.addAttribute("listPO", mapper.writeValueAsString(session.createNativeQuery(strPO).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving PO Data from database in method Network_PoSiteItem due to \n " + exceptionAsString);
				logger.info("Error in retreiving PO Data from database in method Network_PoSiteItem due to \n " + exceptionAsString);															
				model.addAttribute("listPO", "null");
				}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_PoSiteItem";
	}
}

@RequestMapping(value = "/Network_PoItemSite", method = RequestMethod.GET)
public String Network_PoItemSite(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strSites ="SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,"
					+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
					+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			String strPO ="SELECT distinct a.PO_ID FROM ASSET_REGISTRY a WHERE a.PO_ID!='0' and a.PO_ID is not null and a.PO_ID!='null' ";
			
			try {	
				 if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;	
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;	
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (access != null && !access.equals("null")) {
						arrayParam[2] = 1;	
						model.addAttribute("accessDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;	
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					strSites = AppendQuery("w",arrayParam, strSites);			
					strSites = strSites + ") as countNodes,(select COUNT(*) from ASSET_REGISTRY j where  j.AR_ID=b.AR_ID and j.PO_ID!='null' ";
					strSites=  AppendQuery("j",arrayParam, strSites);
					strSites = strSites + ") as countItems,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";				
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("c",arrayParam, strSites);
					strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("d",arrayParam, strSites);	
					strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
					strSites=  AppendQuery("o",arrayParam, strSites);	
					strSites = strSites + " ) ";			
					strSites=  AppendQuery("e",arrayParam, strSites);
					strSites = strSites + ") as countUcells FROM AR_SITE b,ASSET_REGISTRY j where j.AR_ID=b.AR_ID AND b.WARE_ID!='0' and b.WARE_ID is not null and b.WARE_ID!='null' ";			
					strSites=  AppendQuery("j",arrayParam, strSites);	
						
					model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites Data from database in method Network_PoItemSite due to \n " + exceptionAsString);
					logger.info("Error in retreiving Sites Data from database in method Network_PoItemSite due to \n " + exceptionAsString);															
					model.addAttribute("listSites", "null");
				}
				try {
					strPO=  AppendQuery("a",arrayParam, strPO);	
					model.addAttribute("listPO", mapper.writeValueAsString(session.createNativeQuery(strPO).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving PO Data from database in method Network_PoItemSite due to \n " + exceptionAsString);
				logger.info("Error in retreiving PO Data from database in method Network_PoItemSite due to \n " + exceptionAsString);															
				model.addAttribute("listPO", "null");
				}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_PoItemSite";
	}
}


@RequestMapping(value = "/Network_SitePoItem", method = RequestMethod.GET)
public String Network_SitePoItem(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
		
			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strSites ="SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,"
					+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
					+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
		 try {	
			 if (enterprise != null && !enterprise.equals("null")) {
					arrayParam[0] = 1;	
					model.addAttribute("EnterpriseBtn", arrayParam[0]);
				}
				if (transmission != null && !transmission.equals("null")) {
					arrayParam[1] = 1;	
					model.addAttribute("transmBtn", arrayParam[1]);
				}
				if (access != null && !access.equals("null")) {
					arrayParam[2] = 1;	
					model.addAttribute("accessDBtn", arrayParam[2]);
				}
				if (core != null && !core.equals("null")) {
					arrayParam[3] = 1;	
					model.addAttribute("CoreBtn", arrayParam[3]);
				}
				strSites = AppendQuery("w",arrayParam, strSites);			
				strSites = strSites + ") as countNodes,(select COUNT(*) from ASSET_REGISTRY j where  j.AR_ID=b.AR_ID and j.PO_ID!='null' ";
				strSites=  AppendQuery("j",arrayParam, strSites);
				strSites = strSites + ") as countItems,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";				
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("c",arrayParam, strSites);
				strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("d",arrayParam, strSites);	
				strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("e",arrayParam, strSites);
				strSites = strSites + ") as countUcells FROM AR_SITE b,ASSET_REGISTRY j where j.AR_ID=b.AR_ID AND b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null ";			
				strSites=  AppendQuery("j",arrayParam, strSites);	

				model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method Network_SitePoItem due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method Network_SitePoItem due to \n " + exceptionAsString);															
				model.addAttribute("listSites", "null");
			}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_SitePoItem";
	}
}


@RequestMapping(value = "/Network_NdTypNdCell", method = RequestMethod.GET)
public String Network_NdTypNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strNodes ="SELECT b.SITE_ID,b.WARE_NAME,b.NODE_PK,b.LATITUDE,b.LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.ACTIVE_RECORD = '1' ";
			
			String strNodesType ="SELECT DISTINCT NODE_TYPE,(select COUNT(*) from NODE_ACTIVE a  where a.NODE_TYPE=b.NODE_TYPE and a.ACTIVE_RECORD = '1' ";
			
			try {
				if (enterprise != null && !enterprise.equals("null")) {
					arrayParam[0] = 1;	
					model.addAttribute("EnterpriseBtn", arrayParam[0]);
				}
				if (transmission != null && !transmission.equals("null")) {
					arrayParam[1] = 1;
					model.addAttribute("transmBtn", arrayParam[1]);
				}
				if (access != null && !access.equals("null")) {
					arrayParam[2] = 1;
					model.addAttribute("accessDBtn", arrayParam[2]);
				}
				if (core != null && !core.equals("null")) {
					arrayParam[3] = 1;	
					model.addAttribute("CoreBtn", arrayParam[3]);
				}
				
				strNodes = AppendQuery("w",arrayParam, strNodes);			
				strNodes = strNodes + ") as countNodes,(select count(*) from NODE_GCELL e  where b.NODE_PK = e.NODE_PK and ACTIVE_RECORD = '1' ";
				strNodes=  AppendQuery("e",arrayParam, strNodes);	
				strNodes = strNodes + ") as countGcells,(select count(*) from NODE_LCELL c  where b.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1' ";
				strNodes=  AppendQuery("c",arrayParam, strNodes);		
				strNodes = strNodes + ") as countLcells,(select count(*) from NODE_UCELL d  where b.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1' ";
				strNodes=  AppendQuery("d",arrayParam, strNodes);	
				strNodes = strNodes + ") as countUcells,b.WARE_ID,b.NODE_NAME,b.NODE_TYPE FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' ";			
				strNodes=  AppendQuery("b",arrayParam, strNodes);
				
				model.addAttribute("listNodes", mapper.writeValueAsString(session.createNativeQuery(strNodes).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			}catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Nodes Data from database in method Network_NdTypNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Nodes Data from database in method Network_NdTypNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listNodes", "null");
			}
						
			try {
				strNodesType = AppendQuery("a",arrayParam, strNodesType);			
				strNodesType = strNodesType + ") as countNodes FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' ";
				strNodesType=  AppendQuery("b",arrayParam, strNodesType);
				
				model.addAttribute("listNodesType", mapper.writeValueAsString(session.createNativeQuery(strNodesType).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Node Type Data from database in method Network_NdTypNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Node Type Data from database in method Network_NdTypNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listNodesType", "null");	
			}
		
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_NdTypNdCell";
	}
}

@RequestMapping(value = "/Network_Node", method = RequestMethod.GET)
public String Network_Node(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
			String strNodes ="SELECT b.SITE_ID,b.WARE_NAME,b.NODE_PK,b.LATITUDE,b.LONGITUDE,"
					+"(select COUNT(*) from NODE_ACTIVE w where ACTIVE_RECORD = '1' ";
	
			try {
				if (enterprise != null && !enterprise.equals("null")) {
					arrayParam[0] = 1;	
					model.addAttribute("EnterpriseBtn", arrayParam[0]);
				}
				if (transmission != null && !transmission.equals("null")) {
					arrayParam[1] = 1;
					model.addAttribute("transmBtn", arrayParam[1]);
				}
				if (access != null && !access.equals("null")) {
					arrayParam[2] = 1;	
					model.addAttribute("accessDBtn", arrayParam[2]);
				}
				if (core != null && !core.equals("null")) {
					arrayParam[3] = 1;	
					model.addAttribute("CoreBtn", arrayParam[3]);
				}
	
				strNodes = AppendQuery("w",arrayParam, strNodes);				
				strNodes = strNodes + ") as countNodes,(select count(*) from NODE_GCELL e  where b.NODE_PK = e.NODE_PK and e.ACTIVE_RECORD = '1' ";
				strNodes = AppendQuery("e",arrayParam, strNodes);
				strNodes = strNodes + ") as countGCells,(select count(*) from NODE_LCELL c  where b.NODE_PK = c.NODE_PK and c.ACTIVE_RECORD = '1' ";
				strNodes = AppendQuery("c",arrayParam, strNodes);
				strNodes = strNodes + ") as countLCells,(select count(*) from NODE_UCELL d  where b.NODE_PK = d.NODE_PK and d.ACTIVE_RECORD = '1' ";
				strNodes = AppendQuery("d",arrayParam, strNodes);
				strNodes = strNodes + ") as countUCells,b.WARE_ID,b.NODE_NAME,b.NODE_TYPE,b.SUPPLIER_ID FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' ";
				strNodes = AppendQuery("b",arrayParam, strNodes);
					
				model.addAttribute("listNodes", mapper.writeValueAsString(session.createNativeQuery(strNodes).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Nodes Data from database in method Network_Node due to \n " + exceptionAsString);
					logger.info("Error in retreiving Nodes Data from database in method Network_Node due to \n " + exceptionAsString);															
					model.addAttribute("listNodes", "null");
				}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_Node";
	}
}



@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_Cell", method = RequestMethod.GET)
public String Network_Cell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        		
			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,j.GCELL_ID,b.LATITUDE,b.LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.ACTIVE_RECORD = '1' "; 
			String strCells2 ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,i.LCELL_ID,b.LATITUDE,b.LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.ACTIVE_RECORD = '1' "; 
			String strCells3 ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,k.UCELL_ID,b.LATITUDE,b.LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.ACTIVE_RECORD = '1' ";		
			
			if (enterprise != null && !enterprise.equals("null")) {
				arrayParam[0] = 1;	
				model.addAttribute("EnterpriseBtn", arrayParam[0]);
			}
			if (transmission != null && !transmission.equals("null")) {
				arrayParam[1] = 1;	
				model.addAttribute("transmBtn", arrayParam[1]);
			}
			if (access != null && !access.equals("null")) {
				arrayParam[2] = 1;	
				model.addAttribute("accessDBtn", arrayParam[2]);
			}
			if (core != null && !core.equals("null")) {
				arrayParam[3] = 1;	
				model.addAttribute("CoreBtn", arrayParam[3]);
			}
				try {
					strCells1 = AppendQuery("w",arrayParam, strCells1);				
					strCells1 = strCells1 + ") as countNodes,(select count(*) from NODE_GCELL e  where b.NODE_PK = e.NODE_PK and e.ACTIVE_RECORD = '1' ";
					strCells1 = AppendQuery("e",arrayParam, strCells1);
					strCells1 = strCells1 + ") as countGCells,(select count(*) from NODE_LCELL c  where b.NODE_PK = c.NODE_PK and c.ACTIVE_RECORD = '1' ";
					strCells1 = AppendQuery("c",arrayParam, strCells1);
					strCells1 = strCells1 + ") as countLCells,(select count(*) from NODE_UCELL d  where b.NODE_PK = d.NODE_PK and d.ACTIVE_RECORD = '1' ";
					strCells1 = AppendQuery("d",arrayParam, strCells1);
					strCells1 = strCells1 + ") as countUCells,j.NODE_PK,j.CELLNAME,b.WARE_ID,b.NODE_NAME FROM NODE_ACTIVE b "
							+ "LEFT JOIN NODE_GCELL j ON b.NODE_PK = j.NODE_PK WHERE b.NODE_PK = j.NODE_PK AND j.ACTIVE_RECORD = '1' ";
					strCells1 = AppendQuery("j",arrayParam, strCells1);
					
					strCells2 = AppendQuery("w",arrayParam, strCells2);				
					strCells2 = strCells2 + ") as countNodes,(select count(*) from NODE_GCELL e  where b.NODE_PK = e.NODE_PK and e.ACTIVE_RECORD = '1' ";
					strCells2 = AppendQuery("e",arrayParam, strCells2);
					strCells2 = strCells2 + ") as countGCells,(select count(*) from NODE_LCELL c  where b.NODE_PK = c.NODE_PK and c.ACTIVE_RECORD = '1' ";
					strCells2 = AppendQuery("c",arrayParam, strCells2);
					strCells2 = strCells2 + ") as countLCells,(select count(*) from NODE_UCELL d  where b.NODE_PK = d.NODE_PK and d.ACTIVE_RECORD = '1' ";
					strCells2 = AppendQuery("d",arrayParam, strCells2);
					strCells2 = strCells2 + ") as countUCells,i.NODE_PK,i.CELLNAME,b.WARE_ID,b.NODE_NAME FROM NODE_ACTIVE b "
							+ "LEFT JOIN NODE_LCELL i ON b.NODE_PK = i.NODE_PK WHERE b.NODE_PK = i.NODE_PK AND i.ACTIVE_RECORD = '1' ";
					strCells2 = AppendQuery("i",arrayParam, strCells2);
					
					strCells3 = AppendQuery("w",arrayParam, strCells3);				
					strCells3 = strCells3 + ") as countNodes,(select count(*) from NODE_GCELL e  where b.NODE_PK = e.NODE_PK and e.ACTIVE_RECORD = '1' ";
					strCells3 = AppendQuery("e",arrayParam, strCells3);
					strCells3 = strCells3 + ") as countGCells,(select count(*) from NODE_LCELL c  where b.NODE_PK = c.NODE_PK and c.ACTIVE_RECORD = '1' ";
					strCells3 = AppendQuery("c",arrayParam, strCells3);
					strCells3 = strCells3 + ") as countLCells,(select count(*) from NODE_UCELL d  where b.NODE_PK = d.NODE_PK and d.ACTIVE_RECORD = '1' ";
					strCells3 = AppendQuery("d",arrayParam, strCells3);
					strCells3 = strCells3 + ") as countUCells,k.NODE_PK,k.CELLNAME,b.WARE_ID,b.NODE_NAME FROM NODE_ACTIVE b "
							+ "LEFT JOIN NODE_UCELL k ON b.NODE_PK = k.NODE_PK WHERE b.NODE_PK = k.NODE_PK AND k.ACTIVE_RECORD = '1' ";
					strCells3 = AppendQuery("k",arrayParam, strCells3);

					cellResult.addAll(session.createNativeQuery(strCells1).list());
					cellResult.addAll(session.createNativeQuery(strCells2).list());
					cellResult.addAll(session.createNativeQuery(strCells3).list());
					
					model.addAttribute("listCells", mapper.writeValueAsString(cellResult));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Cells Data from database in method Network_Cell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Cells Data from database in method Network_Cell due to \n " + exceptionAsString);															
					model.addAttribute("listCells", "null");
			}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_Cell";
	}
}

//retrieve sites/nodes/cells data when supplier is clicked in
	// Supplier-site-node-cell method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FindOnClick_VenSiteNodeCell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> Find_VenSiteNodeCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedVen = request.getParameter("selectedVen");
			String selectedItem = request.getParameter("selectedItem");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramAccess = request.getParameter("paramAccess");
			String paramCore = request.getParameter("paramCore");
				
			String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.VENDOR FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR='"+ selectedVen + "' ";
			
			String strNodes ="SELECT NODE_PK,NODE_TYPE,NODE_NAME,WARE_ID,VENDOR,"
					+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";
			
			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' "; 
			String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' "; 
			String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' ";		

			 try {
				if (selectedVen != null) {
					strSites= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);					
					
					rtn.put("listVenSites",session.createNativeQuery(strSites).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method FindOnClick_VenSiteNodeCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method FindOnClick_VenSiteNodeCell due to \n " + exceptionAsString);															
				rtn.put("listVenSites", null);
			}
				try {
					if (selectedItem != null) {
						strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
						strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
						strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
					
						cellResult.addAll(session.createNativeQuery(strCells1).list());
						cellResult.addAll(session.createNativeQuery(strCells2).list());
						cellResult.addAll(session.createNativeQuery(strCells3).list());
						
						rtn.put("listCells",cellResult);
					}
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Cells Data from database in method FindOnClick_VenSiteNodeCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Cells Data from database in method FindOnClick_VenSiteNodeCell due to \n " + exceptionAsString);															
					rtn.put("listCells", null);
				}			 
				try {
					if (selectedItem != null) {
						strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
						strNodes = strNodes + ") as countNodes,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1' ";
						strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);
						strNodes = strNodes + ") as countGcells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1' ";
						strNodes= boqDomainVar ("c",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);			
						strNodes = strNodes + ") as countLcells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1' ";				
						strNodes= boqDomainVar ("d",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);	
						strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.WARE_ID='"+ selectedItem + "' ";			
						strNodes= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);								
						rtn.put("listVenNodes", session.createNativeQuery(strNodes).list());
					}
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Nodes Data from database in method FindOnClick_VenSiteNodeCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Nodes Data from database in method FindOnClick_VenSiteNodeCell due to \n " + exceptionAsString);															
					rtn.put("listVenNodes", null);
				}
		 finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;
	}

//retrieve sites/nodes/cells data when supplier is clicked in
	// Supplier-site-node-cell method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FindOnClick_SuppSiteNodeCell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> Find_SuppSiteNodeCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedSupp = request.getParameter("selectedSupp");
			String selectedItem = request.getParameter("selectedItem");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramAccess = request.getParameter("paramAccess");
			String paramCore = request.getParameter("paramCore");
				
			String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.SUPPLIER_ID FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.SUPPLIER_ID='"+ selectedSupp + "' ";
			
			String strNodes ="SELECT NODE_PK,NODE_TYPE,NODE_NAME,WARE_ID,SUPPLIER_ID,"
					+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";
			
			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' "; 
			String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' "; 
			String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' ";		

			 try {
				if (selectedSupp != null) {
					strSites= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);					
					rtn.put("listSuppSites",session.createNativeQuery(strSites).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method FindOnClick_SuppSiteNodeCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method FindOnClick_SuppSiteNodeCell due to \n " + exceptionAsString);															
				rtn.put("listSuppSites", null);
			}
				try {
					if (selectedItem != null) {
						strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
						strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
						strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
					
						cellResult.addAll(session.createNativeQuery(strCells1).list());
						cellResult.addAll(session.createNativeQuery(strCells2).list());
						cellResult.addAll(session.createNativeQuery(strCells3).list());
						
						rtn.put("listCells",cellResult);
					}
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Cells Data from database in method FindOnClick_SuppSiteNodeCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Cells Data from database in method FindOnClick_SuppSiteNodeCell due to \n " + exceptionAsString);															
					rtn.put("listCells", null);
				}
			 
				try {
					if (selectedItem != null) {
						strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
						strNodes = strNodes + ") as countNodes,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1' ";
						strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);
						strNodes = strNodes + ") as countGcells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1' ";
						strNodes= boqDomainVar ("c",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);			
						strNodes = strNodes + ") as countLcells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1' ";				
						strNodes= boqDomainVar ("d",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);	
						strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.WARE_ID='"+ selectedItem + "' ";			
						strNodes= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
						
						rtn.put("listSuppNodes", session.createNativeQuery(strNodes).list());
					}
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Nodes Data from database in method FindOnClick_SuppSiteNodeCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Nodes Data from database in method FindOnClick_SuppSiteNodeCell due to \n " + exceptionAsString);															
					rtn.put("listSuppNodes", null);				
				}
		 finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;
	}
	

@RequestMapping(value = "/Network_NdTypSupStNdCell", method = RequestMethod.GET)
public String Network_NdTypSupStNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();

	if (session != null && session.isOpen()) {
		tx = session.beginTransaction();
		notifications.headerNotifications(session, model);

		String enterprise = request.getParameter("enterprise");
		String transmission = request.getParameter("transmission");
		String access = request.getParameter("access");
		String core = request.getParameter("core");

		int[] arrayParam = new int[4]; 
        arrayParam[0] = 0; // enterprise
        arrayParam[1] = 0; // transmission
        arrayParam[2] = 0; // access
        arrayParam[3] = 0; // core
        
    	String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
				+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";	
		
		String strNodesType ="SELECT DISTINCT NODE_TYPE,(select COUNT(*) from NODE_ACTIVE a  where a.NODE_TYPE=b.NODE_TYPE and a.ACTIVE_RECORD = '1' ";			
		
			try {
				if (enterprise != null && !enterprise.equals("null")) {
					arrayParam[0] = 1;	
					model.addAttribute("EnterpriseBtn", arrayParam[0]);
				}
				if (transmission != null && !transmission.equals("null")) {
					arrayParam[1] = 1;
					model.addAttribute("transmBtn", arrayParam[1]);
				}
				if (access != null && !access.equals("null")) {
					arrayParam[2] = 1;	
					model.addAttribute("accessDBtn", arrayParam[2]);
				}
				if (core != null && !core.equals("null")) {
					arrayParam[3] = 1;	
					model.addAttribute("CoreBtn", arrayParam[3]);
				}
				strSites = AppendQuery("w",arrayParam, strSites);			
				strSites = strSites + ") as countNodes,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("c",arrayParam, strSites);
				strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("d",arrayParam, strSites);	
				strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("e",arrayParam, strSites);
				strSites = strSites + ") as countUcells FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.ACTIVE_RECORD = '1' AND b.SUPPLIER_ID!='null' and b.SUPPLIER_ID!='0' and b.SUPPLIER_ID is not null ";			
				strSites=  AppendQuery("b",arrayParam, strSites);	
				
				model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method Network_NdTypSupStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method Network_NdTypSupStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listSites", "null");
		}
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//			
			try {
				strNodesType = AppendQuery("a",arrayParam, strNodesType);			
				strNodesType = strNodesType + ") as countNodes FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' ";
				strNodesType=  AppendQuery("b",arrayParam, strNodesType);
				
				model.addAttribute("listNodesType", mapper.writeValueAsString(session.createNativeQuery(strNodesType).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));	
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Node Type Data from database in method Network_NdTypSupStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Node Type Data from database in method Network_NdTypSupStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listNodesType", null);
			}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_NdTypSupStNdCell";
	}
}


@RequestMapping(value = "/Network_NdTypVenStNdCell", method = RequestMethod.GET)
public String Network_NdTypVenStNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		session = almsessions.getSession();

	if (session != null && session.isOpen()) {
		tx = session.beginTransaction();
		notifications.headerNotifications(session, model);

		String enterprise = request.getParameter("enterprise");
		String transmission = request.getParameter("transmission");
		String access = request.getParameter("access");
		String core = request.getParameter("core");

		int[] arrayParam = new int[4]; 
        arrayParam[0] = 0; // enterprise
        arrayParam[1] = 0; // transmission
        arrayParam[2] = 0; // access
        arrayParam[3] = 0; // core
        
    	String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
				+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";	
		
		String strNodesType ="SELECT DISTINCT NODE_TYPE,(select COUNT(*) from NODE_ACTIVE a  where a.NODE_TYPE=b.NODE_TYPE and a.ACTIVE_RECORD = '1' ";			
		
			try {
				if (enterprise != null && !enterprise.equals("null")) {
					arrayParam[0] = 1;	
					model.addAttribute("EnterpriseBtn", arrayParam[0]);
				}
				if (transmission != null && !transmission.equals("null")) {
					arrayParam[1] = 1;
					model.addAttribute("transmBtn", arrayParam[1]);
				}
				if (access != null && !access.equals("null")) {
					arrayParam[2] = 1;	
					model.addAttribute("accessDBtn", arrayParam[2]);
				}
				if (core != null && !core.equals("null")) {
					arrayParam[3] = 1;	
					model.addAttribute("CoreBtn", arrayParam[3]);
				}
				strSites = AppendQuery("w",arrayParam, strSites);			
				strSites = strSites + ") as countNodes,(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("c",arrayParam, strSites);
				strSites = strSites + ") as countGcells,(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("d",arrayParam, strSites);	
				strSites = strSites + ") as countLcells,(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1' ";
				strSites=  AppendQuery("o",arrayParam, strSites);	
				strSites = strSites + " ) ";			
				strSites=  AppendQuery("e",arrayParam, strSites);
				strSites = strSites + ") as countUcells FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.ACTIVE_RECORD = '1'AND b.VENDOR!='null' and b.VENDOR!='0' and b.VENDOR is not null ";			
				strSites=  AppendQuery("b",arrayParam, strSites);	
				
				model.addAttribute("listSites", mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites Data from database in method Network_NdTypVenStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites Data from database in method Network_NdTypVenStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listSites", "null");
		}
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//			
			try {
				strNodesType = AppendQuery("a",arrayParam, strNodesType);			
				strNodesType = strNodesType + ") as countNodes FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' ";
				strNodesType=  AppendQuery("b",arrayParam, strNodesType);
				
				model.addAttribute("listNodesType", mapper.writeValueAsString(session.createNativeQuery(strNodesType).list()));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));	
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Node Type Data from database in method Network_NdTypVenStNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Node Type Data from database in method Network_NdTypVenStNdCell due to \n " + exceptionAsString);															
				model.addAttribute("listNodesType", null);
			}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "Network/Network_NdTypVenStNdCell";
	}
}



	//retrieve suupliers data when supplier is clicked in
		// Node type -Supplier-site-node-cell method
		@RequestMapping(value = "/findNodeTypeSupSiteNodeCell_Sup", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> findNodeTypeSupSiteNodeCell_Sup(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			Map<String, Object> rtn = new LinkedHashMap<>();
			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				String selectedNodetType = request.getParameter("selectedNodetType");
				
				String paramEnterprise = request.getParameter("paramEnterprise");
				String paramTransmission = request.getParameter("paramTransmission");
				String paramAccess = request.getParameter("paramAccess");
				String paramCore = request.getParameter("paramCore");
					
				String strSupp ="SELECT distinct a.SUPPLIER_ID,a.SUPPLIER_NAME,a.NODE_TYPE FROM NODE_ACTIVE a where a.NODE_TYPE='"+ selectedNodetType +"' AND a.ACTIVE_RECORD = '1' and "
						+ "a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null and a.SUPPLIER_ID!='null' and a.SUPPLIER_ID is not null and a.SUPPLIER_ID!='0' ";
	 	
				try {
					if (selectedNodetType != null) {
						strSupp= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strSupp);			
						rtn.put("listSuppliers",session.createNativeQuery(strSupp).list());
					}
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites Data from database in method findNodeTypeVenSiteNodeCell_Ven due to \n " + exceptionAsString);
					logger.info("Error in retreiving Sites Data from database in method findNodeTypeVenSiteNodeCell_Ven due to \n " + exceptionAsString);															
					rtn.put("listSuppSites", null);
				}			
			 finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}

	//retrieve suppliers data when supplier is clicked in
		// Node type -Supplier-site-node-cell method
		@RequestMapping(value = "/findNodeTypeSupSiteNodeCell_Site", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> findNodeTypeSupSiteNodeCell_Site(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			Map<String, Object> rtn = new LinkedHashMap<>();
			Session session = null;
			Transaction tx = null;
			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				String SelectedNodeType = request.getParameter("SelectedNodeType");
				String selectedSupp = request.getParameter("selectedSupp");				
				
				String paramEnterprise = request.getParameter("paramEnterprise");
				String paramTransmission = request.getParameter("paramTransmission");
				String paramAccess = request.getParameter("paramAccess");
				String paramCore = request.getParameter("paramCore");
					
				String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.SUPPLIER_ID,b.NODE_TYPE FROM NODE_ACTIVE b "
						+ "where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.SUPPLIER_ID='"+ selectedSupp + "' AND b.NODE_TYPE='"+SelectedNodeType+"' ";
				
				 try {
					 	strSites= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
						rtn.put("listSuppSites",session.createNativeQuery(strSites).list());
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Sites Data from database in method findNodeTypeSupSiteNodeCell_Site due to \n " + exceptionAsString);
						logger.info("Error in retreiving Sites Data from database in method findNodeTypeSupSiteNodeCell_Site due to \n " + exceptionAsString);															
						rtn.put("listSuppSites", null);
					}		
			 finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}	
		

		//retrieve suupliers data when supplier is clicked in
			// Node type -Supplier-site-node-cell method
			@RequestMapping(value = "/findNodeTypeVenSiteNodeCell_Site", method = RequestMethod.GET)
			@ResponseBody
			public Map<String, Object> findNodeTypeVenSiteNodeCell_Site(Locale locale, Model model, HttpServletRequest request,
					HttpServletResponse response) throws JsonProcessingException {
				Map<String, Object> rtn = new LinkedHashMap<>();
				Session session = null;
				Transaction tx = null;
				session = almsessions.getSession();
				if (LoginServices.checkSession(request, response).equals("redirect:/")) {
					rtn.put("Login", LoginServices.checkSession(request, response));
					return rtn;
				}
				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();
					String SelectedNodeType = request.getParameter("SelectedNodeType");
					String selectedVen = request.getParameter("selectedVen");				
					
					String paramEnterprise = request.getParameter("paramEnterprise");
					String paramTransmission = request.getParameter("paramTransmission");
					String paramAccess = request.getParameter("paramAccess");
					String paramCore = request.getParameter("paramCore");
						
					String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.VENDOR,b.NODE_TYPE FROM NODE_ACTIVE b "
							+ "where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR='"+ selectedVen + "' AND b.NODE_TYPE='"+SelectedNodeType+"' ";
					
					 try {
						 	strSites= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
							rtn.put("listVenSites",session.createNativeQuery(strSites).list());
						} catch (Exception e) {
							sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							exceptionAsString = sw.toString();
							logger.finest("Error in retreiving Sites Data from database in method findNodeTypeVenSiteNodeCell_Site due to \n " + exceptionAsString);
							logger.info("Error in retreiving Sites Data from database in method findNodeTypeVenSiteNodeCell_Site due to \n " + exceptionAsString);															
							rtn.put("listVenSites", null);
						}		
				 finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
							session.getSessionFactory().close();
						}
					}
				}
				return rtn;
			}
			
		//retrieve vendors data when vendor is clicked in
		// Node type -vendors-site-node-cell method
		@RequestMapping(value = "/findNodeTypeVenSiteNodeCell_Ven", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> findNodeTypeVenSiteNodeCell_Ven(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			Map<String, Object> rtn = new LinkedHashMap<>();
			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				String selectedNodetType = request.getParameter("selectedNodetType");
				
				String paramEnterprise = request.getParameter("paramEnterprise");
				String paramTransmission = request.getParameter("paramTransmission");
				String paramAccess = request.getParameter("paramAccess");
				String paramCore = request.getParameter("paramCore");
					
				String strVen ="SELECT distinct a.VENDOR,a.NODE_TYPE FROM NODE_ACTIVE a where a.NODE_TYPE='"+ selectedNodetType +"' AND a.ACTIVE_RECORD = '1' and "
						+ "a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null and a.VENDOR!='null' and a.VENDOR is not null and a.VENDOR!='0' ";
	 	
				try {
					if (selectedNodetType != null) {
						strVen= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strVen);			
						rtn.put("listVendors",session.createNativeQuery(strVen).list());
					}
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites Data from database in method findNodeTypeSupSiteNodeCell_Sup due to \n " + exceptionAsString);
					logger.info("Error in retreiving Sites Data from database in method findNodeTypeSupSiteNodeCell_Sup due to \n " + exceptionAsString);															
					rtn.put("listVenSites", null);
				}			
			 finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}
		
		//retrieve sites/nodes/cells data when supplier is clicked in
		// Supplier-site-node-cell method
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/FindOnClick_NdTSupSiteNodeCell_NodeCell", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> Find_NdTSupSiteNodeCell_NodeCell(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			Map<String, Object> rtn = new LinkedHashMap<>();
			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				String selectedSupp = request.getParameter("selectedSupp");
				String selectedItem = request.getParameter("selectedItem");
				String SelectedNodeType = request.getParameter("SelectedNodeType");
				
				String paramEnterprise = request.getParameter("paramEnterprise");
				String paramTransmission = request.getParameter("paramTransmission");
				String paramAccess = request.getParameter("paramAccess");
				String paramCore = request.getParameter("paramCore");
					
				String strNodes ="SELECT NODE_PK,NODE_TYPE,NODE_NAME,WARE_ID,SUPPLIER_ID,"
						+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";
				
				List<Object[]> cellResult = new ArrayList<Object[]>();
				String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' and b.NODE_TYPE='"+ SelectedNodeType + "' "; 
				String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' and b.NODE_TYPE='"+ SelectedNodeType + "' "; 
				String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' and b.NODE_TYPE='"+ SelectedNodeType + "' ";		

				try {
					if (selectedItem != null) {
						strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
						strNodes = strNodes + ") as countNodes,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1' ";
						strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);
						strNodes = strNodes + ") as countGcells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1' ";
						strNodes= boqDomainVar ("c",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);			
						strNodes = strNodes + ") as countLcells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1' ";				
						strNodes= boqDomainVar ("d",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);	
						strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.WARE_ID='"+ selectedItem + "' and a.SUPPLIER_ID='"+ selectedSupp + "' and a.NODE_TYPE='"+ SelectedNodeType + "' ";			
						strNodes= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
						
						rtn.put("listSuppNodes", session.createNativeQuery(strNodes).list());
					}
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Nodes Data from database in method FindOnClick_NdTSupSiteNodeCell_NodeCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Nodes Data from database in method FindOnClick_NdTSupSiteNodeCell_NodeCell due to \n " + exceptionAsString);															
					rtn.put("listSuppNodes", null);
				}	
				
				try {
						if (selectedItem != null) {
							strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
							strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
							strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
												
							cellResult.addAll(session.createNativeQuery(strCells1).list());
							cellResult.addAll(session.createNativeQuery(strCells2).list());
							cellResult.addAll(session.createNativeQuery(strCells3).list());
							
							rtn.put("listCells",cellResult);
						}
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Sites Data from database in method FindOnClick_NdTSupSiteNodeCell_NodeCell due to \n " + exceptionAsString);
						logger.info("Error in retreiving Sites Data from database in method FindOnClick_NdTSupSiteNodeCell_NodeCell due to \n " + exceptionAsString);															
						rtn.put("listCells", null);
					}
		
			 finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}
		
		//retrieve sites/nodes/cells data when supplier is clicked in
		// Supplier-site-node-cell method
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/FindOnClick_NdTVenSiteNodeCell_NodeCell", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> Find_NdTVenSiteNodeCell_NodeCell(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			Map<String, Object> rtn = new LinkedHashMap<>();
			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				String selectedVen = request.getParameter("selectedVen");
				String selectedItem = request.getParameter("selectedItem");
				String SelectedNodeType = request.getParameter("SelectedNodeType");
				
				String paramEnterprise = request.getParameter("paramEnterprise");
				String paramTransmission = request.getParameter("paramTransmission");
				String paramAccess = request.getParameter("paramAccess");
				String paramCore = request.getParameter("paramCore");
					
				String strNodes ="SELECT NODE_PK,NODE_TYPE,NODE_NAME,WARE_ID,VENDOR,"
						+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";
				
				List<Object[]> cellResult = new ArrayList<Object[]>();
				String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' and b.NODE_TYPE='"+ SelectedNodeType + "' "; 
				String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' and b.NODE_TYPE='"+ SelectedNodeType + "' "; 
				String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' and b.NODE_TYPE='"+ SelectedNodeType + "' ";		

				try {
					if (selectedItem != null) {
						strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
						strNodes = strNodes + ") as countNodes,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1' ";
						strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);
						strNodes = strNodes + ") as countGcells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1' ";
						strNodes= boqDomainVar ("c",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);			
						strNodes = strNodes + ") as countLcells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1' ";				
						strNodes= boqDomainVar ("d",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);	
						strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.WARE_ID='"+ selectedItem + "' and a.VENDOR='"+ selectedVen + "' and a.NODE_TYPE='"+ SelectedNodeType + "' ";			
						strNodes= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
						
						rtn.put("listVenNodes", session.createNativeQuery(strNodes).list());
					}
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Nodes Data from database in method FindOnClick_NdTVenSiteNodeCell_NodeCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Nodes Data from database in method FindOnClick_NdTVenSiteNodeCell_NodeCell due to \n " + exceptionAsString);															
					rtn.put("listSuppNodes", null);
				}	
				
				try {
						if (selectedItem != null) {
							strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
							strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
							strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
												
							cellResult.addAll(session.createNativeQuery(strCells1).list());
							cellResult.addAll(session.createNativeQuery(strCells2).list());
							cellResult.addAll(session.createNativeQuery(strCells3).list());
							
							rtn.put("listCells",cellResult);
						}
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Sites Data from database in method FindOnClick_NdTVenSiteNodeCell_NodeCell due to \n " + exceptionAsString);
						logger.info("Error in retreiving Sites Data from database in method FindOnClick_NdTVenSiteNodeCell_NodeCell due to \n " + exceptionAsString);															
						rtn.put("listCells", null);
					}
		
			 finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}

		
	// Area controller
	@RequestMapping(value = "/area", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> area(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				List<?> areaList = session.createNativeQuery(
						"SELECT  distinct B.AREA_ID,B.AREA_NAME,A.LONGITUDE,A.LATITUDE FROM WAREHOUSE B,AREA A,NODE_ACTIVE C WHERE A.AREA_ID=B.AREA_ID AND B.WARE_ID=C.WARE_ID AND C.ACTIVE_RECORD='1'")
						.list();
				// (select count(*) from NODE_ACTIVE b where b.WARE_ID = a.WARE_ID and
				// b.ACTIVE_RECORD = '1') as countNode

				List<Object> area_Result = new ArrayList<>();

				if (areaList != null) {
					for (Object obj : areaList) {
						area_Result.add(obj);
					}
					rtn.put("areaList", area_Result);
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving AreaList Data from database in method area due to \n " + exceptionAsString);
				logger.info("Error in retreiving AreaList Data from database in method area due to \n " + exceptionAsString);															
				rtn.put("areaList", null);
			}
			try {
				List<?> site_AreaList = session.createNativeQuery(
						"SELECT  distinct b.WARE_NAME,b.WARE_ID,a.LATITUDE,a.LONGITUDE,a.AREA_ID,(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID  and w.ACTIVE_RECORD = '1') as countnodes"
								+ " FROM WAREHOUSE a,NODE_ACTIVE b where a.WARE_ID=b.WARE_ID and b.ACTIVE_RECORD = '1' and b.WARE_ID!='0' and b.WARE_ID!='null' and a.AREA_ID!='null'")
						.list();

				List<Object> site_AreaResult = new ArrayList<>();

				if (site_AreaList != null) {
					for (Object obj : site_AreaList) {
						site_AreaResult.add(obj);
					}
					rtn.put("listAreaSites", site_AreaResult);
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Area Sites Data from database in method area due to \n " + exceptionAsString);
				logger.info("Error in retreiving Area Sites Data from database in method area due to \n " + exceptionAsString);															
				rtn.put("listAreaSites", null);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;
	}
	
private static String boqDomain (String paramEnterprise,String paramTransmission,String paramAccess,String paramCore, String str) {
	if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
        String[] words = str.split(" ");    
        // Check if the last word is "WHERE" (case-insensitive)
        if (words.length > 0 && "WHERE".equalsIgnoreCase(words[words.length - 1])) {
        } else {
           str = str +" and ( ";
        }
		
		if (paramEnterprise.equals("true")) {
			str= str +"DOMAIN ='Enterprise' ";
		}
		if (paramTransmission.equals("true")) {
			if(paramEnterprise.equals("false")) {
				str= str +"DOMAIN ='Transmission' ";
			}else {
				str= str + " or DOMAIN ='Transmission' ";
			}
		}
		if (paramAccess.equals("true")) {
			if(paramEnterprise.equals("false") && paramTransmission.equals("false")) {
				str= str+ "DOMAIN ='Access' ";
			}else {
				str= str+ " or DOMAIN ='Access' ";
			}
		}
		if (paramCore.equals("true")) {
			if(paramEnterprise.equals("false") && paramTransmission.equals("false") && paramAccess.equals("false")) {
				str= str +"DOMAIN ='Core' ";
			}else {
				str= str+ "or DOMAIN ='Core' ";
			}					
		}	
        if (words.length > 0 && "WHERE".equalsIgnoreCase(words[words.length - 1])) {
            //System.out.println("The last word is 'WHERE'");
        } else {
            //System.out.println("The last word is not 'WHERE'");
    		str = str + " ) ";
        }
	}
	//System.out.println("str >> "+str);
	return str;
}

private static String boqDomainVar (String a,String paramEnterprise,String paramTransmission,String paramAccess,String paramCore, String str) {
	if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
        String[] words = str.split(" ");    
        // Check if the last word is "WHERE" (case-insensitive)
        if (words.length > 0 && "WHERE".equalsIgnoreCase(words[words.length - 1])) {
        } else {
            str = str +" and ( ";
        }
			
		if (paramEnterprise.equals("true")) {
			str= str +a+ ".DOMAIN ='Enterprise' ";
		}
		if (paramTransmission.equals("true")) {
			if(paramEnterprise.equals("false")) {
				str= str +a+ ".DOMAIN ='Transmission' ";
			}else {
				str= str + " or " +a+ ".DOMAIN ='Transmission' ";
			}
		}
		if (paramAccess.equals("true")) {
			if(paramEnterprise.equals("false") && paramTransmission.equals("false")) {
				str= str+ a+ ".DOMAIN ='Access' ";
			}else {
				str= str+ " or " +a+ ".DOMAIN ='Access' ";
			}
		}
		if (paramCore.equals("true")) {
			if(paramEnterprise.equals("false") && paramTransmission.equals("false") && paramAccess.equals("false")) {
				str= str +a+ ".DOMAIN ='Core' ";
			}else {
				str= str+ " or " +a+ ".DOMAIN ='Core' ";
			}					
		}	
		 if (words.length > 0 && "WHERE".equalsIgnoreCase(words[words.length - 1])) {
	            //System.out.println("The last word is 'WHERE'");
	        } else {
	            //System.out.println("The last word is not 'WHERE'");
	    		str = str + " ) ";
	        }
	}
	return str;
}

//Sites BOQ data retrieving
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/GetBoqList", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody

		public LinkedHashMap<String, String> GetBoqList(@RequestParam String SiteId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		
			try {
				String strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
				String strExist="Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' ";								
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				String Site_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Site_Query);
				Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
				strEmpty="";
				strExist="";				
		////////////////////////////
				strEmpty="SELECT COUNT(NODE_PK) FROM NODE_ACTIVE where Active_record='1' ";
				strExist= "SELECT COUNT(NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='" + SiteId + "' ";	
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Node_Active_Query);
				Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
				strEmpty="";
				strExist="";
		////////////////////////////			
				strEmpty="SELECT COUNT(g.GCELL_ID) FROM NODE_GCELL g, NODE_ACTIVE a  where g.node_pk = a.node_pk ";
				strExist= "select count(ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"+ SiteId +"' ";	
				strEmpty= boqDomainVar ("g",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Node_GCell_Query);
				Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
				strEmpty="";
				strExist="";
		/////////////////////////////
				strEmpty="SELECT COUNT(l.LCELL_ID) FROM NODE_LCELL l, NODE_ACTIVE a  where l.node_pk = a.node_pk ";
				strExist= "select count(nlc.lcell_id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"+ SiteId +"' ";	
				strEmpty= boqDomainVar ("l",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Node_LCell_Query);
				Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
				strEmpty="";
				strExist="";
	/////////////////////////////
				strEmpty="SELECT COUNT(u.UCELL_ID) FROM NODE_UCELL u, NODE_ACTIVE a  where u.node_pk = a.node_pk ";
				strExist= "select count(nuc.ucell_id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"+ SiteId +"' ";	
				strEmpty= boqDomainVar ("u",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Node_UCell_Query);
				Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
				strEmpty="";
				strExist="";			
	/////////////////////////////
				BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
				BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

				if (SiteId == "") {
					strEmpty="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' ";
					strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
					String Node_Type_Count = strEmpty;
					//System.out.println(Node_Type_Count);
					Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
					BoqHM.put("Node Type", String.valueOf(CountNodesType));
				}else {
					strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' ";
					strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
					String Node_Type_Count = strExist;
					//System.out.println(Node_Type_Count);
					Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
					BoqHM.put("Node Type", String.valueOf(CountNodesType));
					strExist="";
				////////////////////////////////
					strExist="SELECT distinct NODE_TYPE,COUNT(NODE_TYPE) from node_active where Active_record='1' and Ware_Id = '"+SiteId+"' ";
					strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
					strExist = strExist +" GROUP BY NODE_TYPE";
					//System.out.println(strExist);
					List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
					List<Object[]> result = new ArrayList<>();
					for (Object[] obj : CountNodesteach_Active) {
						result.add(obj);
					}
					for (Object[] object : result) {
						BoqHM.put(object[0].toString(), object[1].toString());
					}
				}
				BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
				BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
				BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
				return BoqHM;
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Sites BOQ from database in method GetBoqList due to \n " + exceptionAsString);
			logger.info("Error in retreiving Sites BOQ from database in method GetBoqList due to \n " + exceptionAsString);															
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
				session.getSessionFactory().close();
			}
		}
		//return BoqHM;
	}

		// Sites BOQ data retrieving
			@SuppressWarnings("unchecked")
			@RequestMapping(value = "/GetSiteVenBoqList", method = RequestMethod.GET, produces = "application/json")
			@ResponseBody

			public LinkedHashMap<String, String> GetSiteVenBoqList(@RequestParam String SiteId,@RequestParam String VenId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

				Session session = almsessions.getSession();
				Transaction tx = session.beginTransaction();
				LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
				try {
						String strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null ";
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String Site_Query = SiteId == "" ? strEmpty : "Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' and vendor='"+VenId+"' ";
						//System.out.println(Site_Query);
						Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
						strEmpty="";					
			////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null ";
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						String strExist= "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='" + SiteId + "' and vendor='"+VenId+"'  AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_Active_Query);
						Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
						strEmpty="";
						strExist="";
			////////////////////////////			
						strEmpty="SELECT COUNT(DISTINCT GCELL_ID) FROM NODE_GCELL ";
						if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
							strEmpty= strEmpty + "WHERE ";
						}				
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "select count(DISTINCT ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"+ SiteId +"' and na.vendor='"+VenId+"' ";	
						strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_GCell_Query);
						Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
			/////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT LCELL_ID) FROM NODE_LCELL ";
						if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
							strEmpty= strEmpty + "WHERE ";
						}
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "select count(DISTINCT nlc.lcell_id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"+ SiteId +"' and na.vendor='"+VenId+"' ";	
						strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_LCell_Query);
						Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
			/////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT UCELL_ID) FROM NODE_UCELL ";
						if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
							strEmpty= strEmpty + "WHERE ";
						}
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "select count(DISTINCT nuc.ucell_id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"+ SiteId +"' and na.vendor='"+VenId+"' ";
						strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_UCell_Query);
						Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
						strEmpty="";
						strExist="";			
			/////////////////////////////
						BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
						BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

						if (SiteId == "") {
							strEmpty="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' ";
							strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							String Node_Type_Count = strEmpty;
							//System.out.println(Node_Type_Count);
							Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
							BoqHM.put("Node Type", String.valueOf(CountNodesType));
						}else {
							strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' and vendor='"+VenId+"' ";
							strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_Type_Count = strExist;
							//System.out.println(Node_Type_Count);
							Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
							BoqHM.put("Node Type", String.valueOf(CountNodesType));
							strExist="";
						////////////////////////////////
							strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and Ware_Id = '"+SiteId+"' and vendor='"+VenId+"' ";
							strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							strExist = strExist +" GROUP BY NODE_TYPE";
							//System.out.println(strExist);
							List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
							List<Object[]> result = new ArrayList<>();
							for (Object[] obj : CountNodesteach_Active) {
								result.add(obj);
							}
							for (Object[] object : result) {
								BoqHM.put(object[0].toString(), object[1].toString());
							}
						}
						BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
						BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
						BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
						return BoqHM;
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites BOQ from database in method GetSiteVenBoqList due to \n " + exceptionAsString);
					logger.info("Error in retreiving Sites BOQ from database in method GetSiteVenBoqList due to \n " + exceptionAsString);															
					return null;
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			
			// Sites BOQ data retrieving
				@SuppressWarnings("unchecked")
				@RequestMapping(value = "/GetSiteVenNTBoqList", method = RequestMethod.GET, produces = "application/json")
				@ResponseBody

				public LinkedHashMap<String, String> GetSiteVenNTBoqList(@RequestParam String SiteId,@RequestParam String VenId,@RequestParam String SelectedNodeType,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

					Session session = almsessions.getSession();
					Transaction tx = session.beginTransaction();
					LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
					try {
							String strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null ";
							strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
							String Site_Query = SiteId == "" ? strEmpty : "Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' and vendor='"+VenId+"' and NODE_TYPE='"+SelectedNodeType+"' ";
							//System.out.println(Site_Query);
							Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
							strEmpty="";					
				////////////////////////////
							strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null ";
							strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							String strExist= "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='" + SiteId + "' and vendor='"+VenId+"' and NODE_TYPE='"+SelectedNodeType+"' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
							strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
							//System.out.println(Node_Active_Query);
							Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
							strEmpty="";
							strExist="";
				////////////////////////////			
							strEmpty="SELECT COUNT(DISTINCT GCELL_ID) FROM NODE_GCELL ";
							if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
								strEmpty= strEmpty + "WHERE ";
							}				
							strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							strExist= "select count(DISTINCT ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"+ SiteId +"' and na.vendor='"+VenId+"' and na.NODE_TYPE='"+SelectedNodeType+"' ";	
							strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
							//System.out.println(Node_GCell_Query);
							Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
							strEmpty="";
							strExist="";
				/////////////////////////////
							strEmpty="SELECT COUNT(DISTINCT LCELL_ID) FROM NODE_LCELL ";
							if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
								strEmpty= strEmpty + "WHERE ";
							}
							strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							strExist= "select count(DISTINCT nlc.lcell_id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"+ SiteId +"' and na.vendor='"+VenId+"' and na.NODE_TYPE='"+SelectedNodeType+"' ";	
							strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
							//System.out.println(Node_LCell_Query);
							Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
							strEmpty="";
							strExist="";
				/////////////////////////////
							strEmpty="SELECT COUNT(DISTINCT UCELL_ID) FROM NODE_UCELL ";
							if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
								strEmpty= strEmpty + "WHERE ";
							}
							strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							strExist= "select count(DISTINCT nuc.ucell_id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"+ SiteId +"' and na.vendor='"+VenId+"' and na.NODE_TYPE='"+SelectedNodeType+"' ";
							strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
							//System.out.println(Node_UCell_Query);
							Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
							strEmpty="";
							strExist="";			
				/////////////////////////////
							BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
							BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

							if (SiteId == "") {
								strEmpty="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' ";
								strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
								String Node_Type_Count = strEmpty;
								//System.out.println(Node_Type_Count);
								Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
								BoqHM.put("Node Type", String.valueOf(CountNodesType));
							}else {
								strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' and vendor='"+VenId+"' and NODE_TYPE='"+SelectedNodeType+"' ";
								strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
								String Node_Type_Count = strExist;
								//System.out.println(Node_Type_Count);
								Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
								BoqHM.put("Node Type", String.valueOf(CountNodesType));
								strExist="";
							////////////////////////////////
								strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and Ware_Id = '"+SiteId+"' and vendor='"+VenId+"' and NODE_TYPE='"+SelectedNodeType+"' ";
								strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
								strExist = strExist +" GROUP BY NODE_TYPE";
								//System.out.println(strExist);
								List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
								List<Object[]> result = new ArrayList<>();
								for (Object[] obj : CountNodesteach_Active) {
									result.add(obj);
								}
								for (Object[] object : result) {
									BoqHM.put(object[0].toString(), object[1].toString());
								}
							}
							BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
							BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
							BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
							return BoqHM;
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Sites BOQ from database in method GetSiteVenNTBoqList due to \n " + exceptionAsString);
						logger.info("Error in retreiving Sites BOQ from database in method GetSiteVenNTBoqList due to \n " + exceptionAsString);															
						return null;
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
							session.getSessionFactory().close();
						}
					}
				}

			
// Sites BOQ data retrieving
			@SuppressWarnings("unchecked")
			@RequestMapping(value = "/GetSiteSupBoqList", method = RequestMethod.GET, produces = "application/json")
			@ResponseBody

			public LinkedHashMap<String, String> GetSiteSupBoqList(@RequestParam String SiteId,@RequestParam String SuppId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

				Session session = almsessions.getSession();
				Transaction tx = session.beginTransaction();

				// if Site_id !=null --> an ajax request received
				LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
			
				try {
						String strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null ";
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String Site_Query = SiteId == "" ? strEmpty : "Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' and SUPPLIER_ID='"+SuppId+"' ";
						//System.out.println(Site_Query);
						Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
						strEmpty="";					
			////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null ";
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						String strExist= "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='" + SiteId + "' and SUPPLIER_ID='"+SuppId+"' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_Active_Query);
						Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
						strEmpty="";
						strExist="";
			////////////////////////////			
						strEmpty="SELECT COUNT(DISTINCT GCELL_ID) FROM NODE_GCELL ";
						if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
							strEmpty= strEmpty + "WHERE ";
						}				
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "select count(DISTINCT ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"+ SiteId +"' and na.SUPPLIER_ID='"+SuppId+"' ";	
						strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_GCell_Query);
						Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
			/////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT LCELL_ID) FROM NODE_LCELL ";
						if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
							strEmpty= strEmpty + "WHERE ";
						}
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "select count(DISTINCT nlc.lcell_id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"+ SiteId +"' and na.SUPPLIER_ID='"+SuppId+"' ";	
						strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_LCell_Query);
						Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
			/////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT UCELL_ID) FROM NODE_UCELL ";
						if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
							strEmpty= strEmpty + "WHERE ";
						}
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "select count(DISTINCT nuc.ucell_id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"+ SiteId +"' and na.SUPPLIER_ID='"+SuppId+"' ";
						strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_UCell_Query);
						Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
						strEmpty="";
						strExist="";			
			/////////////////////////////
						BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
						BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

						if (SiteId == "") {
							strEmpty="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' ";
							strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							String Node_Type_Count = strEmpty;
							//System.out.println(Node_Type_Count);
							Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
							BoqHM.put("Node Type", String.valueOf(CountNodesType));
						}else {
							strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' and SUPPLIER_ID='"+SuppId+"' ";
							strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_Type_Count = strExist;
							//System.out.println(Node_Type_Count);
							Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
							BoqHM.put("Node Type", String.valueOf(CountNodesType));
							strExist="";
						////////////////////////////////
							strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and Ware_Id = '"+SiteId+"' and SUPPLIER_ID='"+SuppId+"' ";
							strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							strExist = strExist +" GROUP BY NODE_TYPE";
							//System.out.println(strExist);
							List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
							List<Object[]> result = new ArrayList<>();
							for (Object[] obj : CountNodesteach_Active) {
								result.add(obj);
							}
							for (Object[] object : result) {
								BoqHM.put(object[0].toString(), object[1].toString());
							}
						}
						BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
						BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
						BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
						return BoqHM;
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites BOQ from database in method GetSiteSupBoqList due to \n " + exceptionAsString);
					logger.info("Error in retreiving Sites BOQ from database in method GetSiteSupBoqList due to \n " + exceptionAsString);															
					return null;
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
		

			@SuppressWarnings("unchecked")
			@RequestMapping(value = "/GetSiteSupNTBoqList", method = RequestMethod.GET, produces = "application/json")
			@ResponseBody
			public LinkedHashMap<String, String> GetSiteSupNTBoqList(@RequestParam String SiteId,@RequestParam String SuppId,@RequestParam String SelectedNodeType,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

				Session session = almsessions.getSession();
				Transaction tx = session.beginTransaction();
				LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
			
				try {
						String strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null ";
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String Site_Query = SiteId == "" ? strEmpty : "Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' and SUPPLIER_ID='"+SuppId+"' AND NODE_TYPE='"+SelectedNodeType+"' ";
						//System.out.println(Site_Query);
						Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
						strEmpty="";					
			////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null ";
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						String strExist= "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='" + SiteId + "' and SUPPLIER_ID='"+SuppId+"' AND NODE_TYPE='"+SelectedNodeType+"' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_Active_Query);
						Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
						strEmpty="";
						strExist="";
			////////////////////////////			
						strEmpty="SELECT COUNT(DISTINCT GCELL_ID) FROM NODE_GCELL ";
						if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
							strEmpty= strEmpty + "WHERE ";
						}				
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "select count(DISTINCT ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"+ SiteId +"' and na.SUPPLIER_ID='"+SuppId+"' AND na.NODE_TYPE='"+SelectedNodeType+"' ";	
						strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_GCell_Query);
						Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
			/////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT LCELL_ID) FROM NODE_LCELL ";
						if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
							strEmpty= strEmpty + "WHERE ";
						}
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "select count(DISTINCT nlc.lcell_id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"+ SiteId +"' and na.SUPPLIER_ID='"+SuppId+"' AND na.NODE_TYPE='"+SelectedNodeType+"' ";	
						strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_LCell_Query);
						Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
			/////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT UCELL_ID) FROM NODE_UCELL ";
						if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
							strEmpty= strEmpty + "WHERE ";
						}
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "select count(DISTINCT nuc.ucell_id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"+ SiteId +"' and na.SUPPLIER_ID='"+SuppId+"' AND na.NODE_TYPE='"+SelectedNodeType+"' ";
						strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_UCell_Query);
						Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
						strEmpty="";
						strExist="";			
			/////////////////////////////
						BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
						BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

						if (SiteId == "") {
							strEmpty="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' ";
							strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							String Node_Type_Count = strEmpty;
							//System.out.println(Node_Type_Count);
							Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
							BoqHM.put("Node Type", String.valueOf(CountNodesType));
						}else {
							strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' and SUPPLIER_ID='"+SuppId+"' AND NODE_TYPE='"+SelectedNodeType+"' ";
							strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_Type_Count = strExist;
							//System.out.println(Node_Type_Count);
							Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
							BoqHM.put("Node Type", String.valueOf(CountNodesType));
							strExist="";
						////////////////////////////////
							strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and Ware_Id = '"+SiteId+"' and SUPPLIER_ID='"+SuppId+"' AND NODE_TYPE='"+SelectedNodeType+"' ";
							strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							strExist = strExist +" GROUP BY NODE_TYPE";
							//System.out.println(strExist);
							List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
							List<Object[]> result = new ArrayList<>();
							for (Object[] obj : CountNodesteach_Active) {
								result.add(obj);
							}
							for (Object[] object : result) {
								BoqHM.put(object[0].toString(), object[1].toString());
							}
						}
						BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
						BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
						BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
						return BoqHM;
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites BOQ from database in method GetSiteSupNTBoqList due to \n " + exceptionAsString);
					logger.info("Error in retreiving Sites BOQ from database in method GetSiteSupNTBoqList due to \n " + exceptionAsString);															
					return null;
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}

			
		// Sites BOQ data retrieving
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/GetVenBoqList", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody

		public LinkedHashMap<String, String> GetVenBoqList(@RequestParam String VenId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
			try {
					
				String strEmpty= "SELECT COUNT(DISTINCT VENDOR) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null  ";	
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				String Vendor_Query = VenId == "" ? strEmpty : "Select distinct VENDOR From NODE_ACTIVE where VENDOR='" + VenId + "' ";
				//System.out.println(Vendor_Query);
				Object Vendors = session.createNativeQuery(Vendor_Query).uniqueResult();
				strEmpty="";
		////////////////////////////
				strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null  ";	
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				String strExist ="Select distinct COUNT(DISTINCT WARE_ID) From NODE_ACTIVE where VENDOR='" + VenId + "' AND WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null ";
				strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);					
				String Site_Query = VenId == "" ? strEmpty : strExist;
				//System.out.println(Site_Query);
				Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
				strEmpty="";
				strExist="";
		////////////////////////////
				strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null ";	
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and VENDOR='" + VenId + "' AND WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null ";	
				strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Node_Active_Query = VenId == "" ? strEmpty : strExist;
				//System.out.println(Node_Active_Query);
				Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
				strEmpty="";
				strExist="";					
		////////////////////////////			
				strEmpty="SELECT COUNT(DISTINCT GCELL_ID) FROM NODE_GCELL ";
				if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
					strEmpty= strEmpty + "WHERE ";
				}				
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "select co"
						+ "unt(DISTINCT ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.VENDOR = '"+ VenId +"' ";	
				strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Node_GCell_Query = VenId == "" ? strEmpty : strExist;
				//System.out.println(Node_GCell_Query);
				Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
				strEmpty="";
				strExist="";
		/////////////////////////////
				strEmpty="SELECT COUNT(DISTINCT LCELL_ID) FROM NODE_LCELL ";
				if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
					strEmpty= strEmpty + "WHERE ";
				}
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "select count(DISTINCT nlc.lcell_id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.VENDOR = '"+ VenId +"' ";	
				strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Node_LCell_Query = VenId == "" ? strEmpty : strExist;
				//System.out.println(Node_LCell_Query);
				Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
				strEmpty="";
				strExist="";
		/////////////////////////////
				strEmpty="SELECT COUNT(DISTINCT UCELL_ID) FROM NODE_UCELL ";
				if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
					strEmpty= strEmpty + "WHERE ";
				}
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "select count(DISTINCT nuc.ucell_id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.VENDOR = '"+ VenId +"' ";	
				strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Node_UCell_Query = VenId == "" ? strEmpty : strExist;
				//System.out.println(Node_UCell_Query);
				Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
				strEmpty="";
				strExist="";			
		/////////////////////////////
				BoqHM.put(VenId == "" ? "Vendors" : "Vendor Name", String.valueOf(Vendors));
				BoqHM.put(VenId == "" ? "Sites" : "Sites", String.valueOf(Sites));
				BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

				if (VenId == "") {
					strEmpty="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' ";
					strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
					String Node_Type_Count = strEmpty;
					//System.out.println(Node_Type_Count);
					Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
					BoqHM.put("Node Type", String.valueOf(CountNodesType));
				}else {
					strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and VENDOR='"+ VenId + "' ";
					strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
					String Node_Type_Count = strExist;
					//System.out.println(Node_Type_Count);
					Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
					BoqHM.put("Node Type", String.valueOf(CountNodesType));
					strExist="";
				////////////////////////////////
					strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and VENDOR = '"+VenId+"' ";
					strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
					strExist = strExist +" GROUP BY NODE_TYPE";
					//System.out.println(strExist);
					List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
					List<Object[]> result = new ArrayList<>();
					for (Object[] obj : CountNodesteach_Active) {
						result.add(obj);
					}
					for (Object[] object : result) {
						BoqHM.put(object[0].toString(), object[1].toString());
					}
				}
				BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
				BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
				BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
				return BoqHM;
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites BOQ from database in method GetVenBoqList due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites BOQ from database in method GetVenBoqList due to \n " + exceptionAsString);															
				return null;
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
			//return BoqHM;
		}
		
		// Sites BOQ data retrieving
				@SuppressWarnings("unchecked")
				@RequestMapping(value = "/GetSupBoqList", method = RequestMethod.GET, produces = "application/json")
				@ResponseBody

				public LinkedHashMap<String, String> GetSupBoqList(@RequestParam String SuppId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

					Session session = almsessions.getSession();
					Transaction tx = session.beginTransaction();
					LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
				try {
						String strEmpty= "SELECT COUNT(DISTINCT Supplier_Id) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null  ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String Supplier_Query = SuppId == "" ? strEmpty : "Select distinct Supplier_Name From NODE_ACTIVE where Supplier_Id='" + SuppId + "' ";
						//System.out.println(Site_Query);
						Object Suppliers = session.createNativeQuery(Supplier_Query).uniqueResult();
						strEmpty="";	
				////////////////////////////
						strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null  ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String strExist= "Select distinct COUNT(DISTINCT WARE_ID) From NODE_ACTIVE where Supplier_Id='" + SuppId + "' AND WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null ";							
						String Site_Query = SuppId == "" ? strEmpty : strExist;
						//System.out.println(Site_Query);
						Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
						strEmpty="";	
						strExist="";
				////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Supplier_Id='" + SuppId + "' and WARE_ID!='null' and WARE_ID!='0' and WARE_ID is not null ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_Active_Query = SuppId == "" ? strEmpty : strExist;
						//System.out.println(Node_Active_Query);
						Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
						strEmpty="";
						strExist="";					
				////////////////////////////			
							strEmpty="SELECT COUNT(DISTINCT GCELL_ID) FROM NODE_GCELL ";
							if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
								strEmpty= strEmpty + "WHERE ";
							}				
							strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							strExist= "select count(DISTINCT ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Supplier_Id = '"+ SuppId +"' ";	
							strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_GCell_Query = SuppId == "" ? strEmpty : strExist;
							//System.out.println(Node_GCell_Query);
							Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
							strEmpty="";
							strExist="";
				/////////////////////////////
							strEmpty="SELECT COUNT(DISTINCT LCELL_ID) FROM NODE_LCELL ";
							if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
								strEmpty= strEmpty + "WHERE ";
							}
							strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							strExist= "select count(DISTINCT nlc.lcell_id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Supplier_Id = '"+ SuppId +"' ";	
							strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_LCell_Query = SuppId == "" ? strEmpty : strExist;
							//System.out.println(Node_LCell_Query);
							Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
							strEmpty="";
							strExist="";
				/////////////////////////////
							strEmpty="SELECT COUNT(DISTINCT UCELL_ID) FROM NODE_UCELL ";
							if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
								strEmpty= strEmpty + "WHERE ";
							}
							strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							strExist= "select count(DISTINCT nuc.ucell_id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Supplier_Id = '"+ SuppId +"' ";	
							strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_UCell_Query = SuppId == "" ? strEmpty : strExist;
							//System.out.println(Node_UCell_Query);
							Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
							strEmpty="";
							strExist="";			
				/////////////////////////////
							BoqHM.put(SuppId == "" ? "Suppliers" : "Supplier Name", String.valueOf(Suppliers));
							BoqHM.put(SuppId == "" ? "Sites" : "Sites", String.valueOf(Sites));
							BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

							if (SuppId == "") {
								strEmpty="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' ";
								strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
								String Node_Type_Count = strEmpty;
								//System.out.println(Node_Type_Count);
								Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
								BoqHM.put("Node Type", String.valueOf(CountNodesType));
							}else {
								strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Supplier_Id='"+ SuppId + "' ";
								strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
								String Node_Type_Count = strExist;
								//System.out.println(Node_Type_Count);
								Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
								BoqHM.put("Node Type", String.valueOf(CountNodesType));
								strExist="";
							////////////////////////////////
								strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and Supplier_Id = '"+SuppId+"' ";
								strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
								strExist = strExist +" GROUP BY NODE_TYPE";
								//System.out.println(strExist);
								List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
								List<Object[]> result = new ArrayList<>();
								for (Object[] obj : CountNodesteach_Active) {
									result.add(obj);
								}
								for (Object[] object : result) {
									BoqHM.put(object[0].toString(), object[1].toString());
								}
							}
							BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
							BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
							BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
							return BoqHM;
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Sites Boq from database in method GetSupBoqList due to \n " + exceptionAsString);
						logger.info("Error in retreiving Sites Boq from database in method GetSupBoqList due to \n " + exceptionAsString);															
						return null;
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
							session.getSessionFactory().close();
						}
					}
				}
		
				
				// Vendors BOQ data retrieving
				@SuppressWarnings("unchecked")
				@RequestMapping(value = "/GetVenNtypeBoqList", method = RequestMethod.GET, produces = "application/json")
				@ResponseBody

				public LinkedHashMap<String, String> GetVenNtypeBoqList(@RequestParam String VenId,@RequestParam String NodeTId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

					Session session = almsessions.getSession();
					Transaction tx = session.beginTransaction();
					LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
				try {
						String strEmpty= "SELECT COUNT(DISTINCT VENDOR) FROM NODE_ACTIVE WHERE NODE_TYPE='"+NodeTId+"' AND WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null  ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String Vendor_Query = VenId == "" ? strEmpty : "Select distinct VENDOR From NODE_ACTIVE where VENDOR='" + VenId + "' AND NODE_TYPE='"+NodeTId+"' ";
						//System.out.println(Vendor_Query);
						Object Vendors = session.createNativeQuery(Vendor_Query).uniqueResult();
						strEmpty="";	
				////////////////////////////
						strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE NODE_TYPE='"+NodeTId+"' AND WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null  ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String strExist= "Select distinct COUNT(DISTINCT WARE_ID) From NODE_ACTIVE where VENDOR='" + VenId + "' AND NODE_TYPE='"+NodeTId+"' AND WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null ";							
						String Site_Query = VenId == "" ? strEmpty : strExist;
						//System.out.println(Site_Query);
						Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
						strEmpty="";	
						strExist="";
				////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null AND NODE_TYPE='"+NodeTId+"' ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and VENDOR='" + VenId + "' AND NODE_TYPE='"+NodeTId+"' and WARE_ID!='null' and WARE_ID!='0' and WARE_ID is not null ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_Active_Query = VenId == "" ? strEmpty : strExist;
						//System.out.println(Node_Active_Query);
						Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
						strEmpty="";
						strExist="";					
				////////////////////////////			
						strEmpty="SELECT COUNT(DISTINCT ngc.GCELL_ID) FROM NODE_GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.NODE_TYPE='"+NodeTId+"' ";
						strEmpty= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "select count(DISTINCT ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.VENDOR = '"+ VenId +"' and na.NODE_TYPE='"+NodeTId+"' ";	
						strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_GCell_Query = VenId == "" ? strEmpty : strExist;
						//System.out.println(Node_GCell_Query);
						Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
				/////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT nlc.LCELL_ID) FROM NODE_LCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.NODE_TYPE='"+NodeTId+"' ";
						strEmpty= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "select count(DISTINCT nlc.lcell_id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.VENDOR = '"+ VenId +"' and na.NODE_TYPE='"+NodeTId+"' ";	
						strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_LCell_Query = VenId == "" ? strEmpty : strExist;
						//System.out.println(Node_LCell_Query);
						Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
				/////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT nuc.UCELL_ID) FROM NODE_UCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.NODE_TYPE='"+NodeTId+"'";
						strEmpty= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "select count(DISTINCT nuc.ucell_id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.VENDOR = '"+ VenId +"' and na.NODE_TYPE='"+NodeTId+"' ";	
						strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_UCell_Query = VenId == "" ? strEmpty : strExist;
						//System.out.println(Node_UCell_Query);
						Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
						strEmpty="";
						strExist="";			
				/////////////////////////////
							BoqHM.put(VenId == "" ? "Vendors" : "Vendor Name", String.valueOf(Vendors));
							BoqHM.put(VenId == "" ? "Sites" : "Sites", String.valueOf(Sites));
							BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

							if (VenId == "") {
								strEmpty="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and NODE_TYPE='"+NodeTId+"' ";
								strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
								String Node_Type_Count = strEmpty;
								//System.out.println(Node_Type_Count);
								Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
								BoqHM.put("Node Type", String.valueOf(CountNodesType));
							}else {
								strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and VENDOR='"+ VenId + "' and NODE_TYPE='"+NodeTId+"' ";
								strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
								String Node_Type_Count = strExist;
								//System.out.println(Node_Type_Count);
								Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
								BoqHM.put("Node Type", String.valueOf(CountNodesType));
								strExist="";
							////////////////////////////////
								strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and VENDOR = '"+VenId+"' and NODE_TYPE='"+NodeTId+"' ";
								strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
								strExist = strExist +" GROUP BY NODE_TYPE";
								//System.out.println(strExist);
								List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
								List<Object[]> result = new ArrayList<>();
								for (Object[] obj : CountNodesteach_Active) {
									result.add(obj);
								}
								for (Object[] object : result) {
									BoqHM.put(object[0].toString(), object[1].toString());
								}
							}
							BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
							BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
							BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
							return BoqHM;
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Vendors Boq from database in method GetVenNtypeBoqList due to \n " + exceptionAsString);
						logger.info("Error in retreiving Vendors Boq from database in method GetVenNtypeBoqList due to \n " + exceptionAsString);															
						return null;
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
							session.getSessionFactory().close();
						}
					}
				}
				
				// Suppliers BOQ data retrieving
				@SuppressWarnings("unchecked")
				@RequestMapping(value = "/GetSuppNtypeBoqList", method = RequestMethod.GET, produces = "application/json")
				@ResponseBody

				public LinkedHashMap<String, String> GetSuppNtypeBoqList(@RequestParam String SuppId,@RequestParam String NodeTId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

					Session session = almsessions.getSession();
					Transaction tx = session.beginTransaction();
					LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
				try {
						String strEmpty= "SELECT COUNT(DISTINCT Supplier_Id) FROM NODE_ACTIVE WHERE NODE_TYPE='"+NodeTId+"' AND WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null  ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String Supplier_Query = SuppId == "" ? strEmpty : "Select distinct Supplier_Name From NODE_ACTIVE where Supplier_Id='" + SuppId + "' AND NODE_TYPE='"+NodeTId+"' ";
						//System.out.println(Site_Query);
						Object Suppliers = session.createNativeQuery(Supplier_Query).uniqueResult();
						strEmpty="";	
				////////////////////////////
						strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE NODE_TYPE='"+NodeTId+"' AND WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null  ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String strExist= "Select distinct COUNT(DISTINCT WARE_ID) From NODE_ACTIVE where Supplier_Id='" + SuppId + "' AND NODE_TYPE='"+NodeTId+"' AND WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null ";							
						String Site_Query = SuppId == "" ? strEmpty : strExist;
						//System.out.println(Site_Query);
						Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
						strEmpty="";	
						strExist="";
				////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null AND NODE_TYPE='"+NodeTId+"' ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Supplier_Id='" + SuppId + "' AND NODE_TYPE='"+NodeTId+"' and WARE_ID!='null' and WARE_ID!='0' and WARE_ID is not null ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_Active_Query = SuppId == "" ? strEmpty : strExist;
						//System.out.println(Node_Active_Query);
						Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
						strEmpty="";
						strExist="";					
				////////////////////////////			
							strEmpty="SELECT COUNT(DISTINCT ngc.GCELL_ID) FROM NODE_GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.NODE_TYPE='"+NodeTId+"' ";
							strEmpty= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							strExist= "select count(DISTINCT ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Supplier_Id = '"+ SuppId +"' and na.NODE_TYPE='"+NodeTId+"' ";	
							strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_GCell_Query = SuppId == "" ? strEmpty : strExist;
							//System.out.println(Node_GCell_Query);
							Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
							strEmpty="";
							strExist="";
				/////////////////////////////
							strEmpty="SELECT COUNT(DISTINCT nlc.LCELL_ID) FROM NODE_LCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.NODE_TYPE='"+NodeTId+"' ";
							strEmpty= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							strExist= "select count(DISTINCT nlc.lcell_id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Supplier_Id = '"+ SuppId +"' and na.NODE_TYPE='"+NodeTId+"' ";	
							strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_LCell_Query = SuppId == "" ? strEmpty : strExist;
							//System.out.println(Node_LCell_Query);
							Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
							strEmpty="";
							strExist="";
				/////////////////////////////
							strEmpty="SELECT COUNT(DISTINCT nuc.UCELL_ID) FROM NODE_UCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.NODE_TYPE='"+NodeTId+"'";
							strEmpty= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
							strExist= "select count(DISTINCT nuc.ucell_id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Supplier_Id = '"+ SuppId +"' and na.NODE_TYPE='"+NodeTId+"' ";	
							strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_UCell_Query = SuppId == "" ? strEmpty : strExist;
							//System.out.println(Node_UCell_Query);
							Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
							strEmpty="";
							strExist="";			
				/////////////////////////////
							BoqHM.put(SuppId == "" ? "Suppliers" : "Supplier Name", String.valueOf(Suppliers));
							BoqHM.put(SuppId == "" ? "Sites" : "Sites", String.valueOf(Sites));
							BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

							if (SuppId == "") {
								strEmpty="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and NODE_TYPE='"+NodeTId+"' ";
								strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
								String Node_Type_Count = strEmpty;
								//System.out.println(Node_Type_Count);
								Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
								BoqHM.put("Node Type", String.valueOf(CountNodesType));
							}else {
								strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Supplier_Id='"+ SuppId + "' and NODE_TYPE='"+NodeTId+"' ";
								strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
								String Node_Type_Count = strExist;
								//System.out.println(Node_Type_Count);
								Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
								BoqHM.put("Node Type", String.valueOf(CountNodesType));
								strExist="";
							////////////////////////////////
								strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and Supplier_Id = '"+SuppId+"' and NODE_TYPE='"+NodeTId+"' ";
								strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
								strExist = strExist +" GROUP BY NODE_TYPE";
								//System.out.println(strExist);
								List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
								List<Object[]> result = new ArrayList<>();
								for (Object[] obj : CountNodesteach_Active) {
									result.add(obj);
								}
								for (Object[] object : result) {
									BoqHM.put(object[0].toString(), object[1].toString());
								}
							}
							BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
							BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
							BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
							return BoqHM;
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Suppliers Boq from database in method GetSuppNtypeBoqList due to \n " + exceptionAsString);
						logger.info("Error in retreiving Suppliers Boq from database in method GetSuppNtypeBoqList due to \n " + exceptionAsString);															
						return null;
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
							session.getSessionFactory().close();
						}
					}
				}
				
		// Sites BOQ data retrieving
		@RequestMapping(value = "/GetBoqSitePoList", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody

		public LinkedHashMap<String, String> GetBoqSitePoList(@RequestParam String SiteId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {
			
			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
			try {
				String strEmpty="SELECT COUNT(distinct a.WARE_ID) FROM AR_SITE a, ASSET_REGISTRY b WHERE a.AR_ID=b.AR_ID ";
				strEmpty= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				String strExist= "Select DISTINCT SITE_NAME From AR_SITE where  WARE_ID='"+ SiteId +"' ";	
				//strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Site_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Site_Query);
				Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
				strEmpty="";
				strExist="";	
				
				strEmpty="SELECT COUNT(distinct a.PO_ID) FROM ASSET_REGISTRY a, AR_SITE b where b.AR_ID=a.AR_ID ";
				strEmpty= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "SELECT COUNT(DISTINCT a.PO_ID) FROM ASSET_REGISTRY a, AR_SITE b where b.AR_ID=a.AR_ID and b.WARE_ID='" + SiteId+ "' ";	
				strExist= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Po_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Po_Query);
				Object CountPo = session.createNativeQuery(Po_Query).uniqueResult();
				strEmpty="";
				strExist="";
			
				BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
				BoqHM.put("PO", String.valueOf(CountPo));
					
				strEmpty="SELECT ROUND(SUM(INITIALCOST), 2) FROM FIXED_ASSET_REGISTRY ";
				if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
					strEmpty= strEmpty + "WHERE ";
				}
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "SELECT ROUND(SUM(INITIALCOST), 2) FROM FIXED_ASSET_REGISTRY WHERE WARE_ID='" + SiteId + "' ";	
				strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String PO_Amount_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(PO_Amount_Query);
				Object PO_Amount = session.createNativeQuery(PO_Amount_Query).uniqueResult();
				BoqHM.put(SiteId == "" ? "PO Cost" : "PO Total Cost", String.valueOf(PO_Amount));
				strEmpty="";
				strExist="";
				
				strEmpty="SELECT ROUND(SUM(NETCOST), 2) FROM FIXED_ASSET_REGISTRY ";
				if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
					strEmpty= strEmpty + "WHERE ";
				}
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "SELECT ROUND(SUM(NETCOST), 2) FROM FIXED_ASSET_REGISTRY WHERE WARE_ID='" + SiteId + "' ";	
				strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String PO_NET_Amount_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(PO_NET_Amount_Query);
				Object PO_NET_Amount = session.createNativeQuery(PO_NET_Amount_Query).uniqueResult();
				BoqHM.put(SiteId == "" ? "PO Net Cost" : "PO Total Net Cost", String.valueOf(PO_NET_Amount));
				strEmpty="";
				strExist="";
														
				if(SiteId!= "") {
					String Item_Query= "Select COUNT(DISTINCT a.ITEM_CODE) from ASSET_REGISTRY a, AR_SITE b where a.AR_ID = b.AR_ID and b.WARE_ID='" + SiteId + "' ";	
					Item_Query= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,Item_Query);
					//System.out.println(Item_Query);
					Object Item = session.createNativeQuery(Item_Query).uniqueResult();
					BoqHM.put("Items", String.valueOf(Item));
				}
			
				return BoqHM;
			 
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Sites BOQ from database in method GetBoqSitePoList due to \n " + exceptionAsString);
				logger.info("Error in retreiving Sites BOQ from database in method GetBoqSitePoList due to \n " + exceptionAsString);															
				return null;
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}

		// Nodes BOQ data retrieving
				@RequestMapping(value = "/GetNodeBoqList", method = RequestMethod.GET, produces = "application/json")
				@ResponseBody
				public LinkedHashMap<String, String> GetNodeBoqList(@RequestParam String WareId,@RequestParam String NodeId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

					Session session = almsessions.getSession();
					Transaction tx = session.beginTransaction();
					LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
					//System.out.println("WareId: "+WareId);
					try {			
						String Site_Query= "Select DISTINCT Ware_Name From NODE_ACTIVE where Ware_Id='" + WareId + "' ";	
						Site_Query= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,Site_Query);		
						//System.out.println(Site_Query);
						Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
						
						if (WareId.equals("null") || WareId == null || WareId.equals("0") || WareId.equals("")) {	
							//System.out.println("IF WARE ID NULL");
							String Node_GCell_Query="select count(ngc.gcell_id) from node_gcell ngc , node_active na where na.Active_record='1' and na.node_pk = ngc.node_pk and na.NODE_PK = '" + NodeId +"' and (na.Ware_Id is null or na.Ware_Id='0') ";
							Node_GCell_Query= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_GCell_Query);
							//System.out.println(Node_GCell_Query);
				 			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();			
				    ///////////////////////////
							String Node_LCell_Query = "select count(nlc.LCell_Id) from node_lcell nlc , node_active na where na.Active_record='1' and na.node_pk = nlc.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
									+ " and na.NODE_PK = '" + NodeId + "' ";
							Node_LCell_Query= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_LCell_Query);
							//System.out.println(Node_LCell_Query);
							Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
					///////////////////////////
							String Node_UCell_Query = "select count(nuc.UCell_Id) from node_ucell nuc , node_active na where na.Active_record='1' and na.node_pk = nuc.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
									+ " and na.NODE_PK = '" + NodeId + "' ";
							Node_UCell_Query= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_UCell_Query);
							//System.out.println(Node_UCell_Query);
							Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
					///////////////////////////
							String Node_Board_Query = "select count(nlc.BOARD_ID) from NODE_BOARD nlc , node_active na where na.Active_record='1' and na.node_pk = nlc.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
									+ " and na.NODE_PK = '" + NodeId + "' ";
							Node_Board_Query= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_Board_Query);
							//System.out.println(Node_Board_Query);
							Object CountNodesBoard = session.createNativeQuery(Node_Board_Query).uniqueResult();
					///////////////////////////
							String Node_Cabinet_Query = "select count(nlc.CABINET_ID) from NODE_CABINET nlc , node_active na where na.Active_record='1' and na.node_pk = nlc.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
									+ " and na.NODE_PK = '" + NodeId + "' ";
							Node_Cabinet_Query= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_Cabinet_Query);
							//System.out.println(Node_Cabinet_Query);
							Object CountNodesCabinet = session.createNativeQuery(Node_Cabinet_Query).uniqueResult();
					///////////////////////////		
							String NodesType_Query = "Select NODE_TYPE From NODE_ACTIVE where Active_record='1' and NODE_PK = '" + NodeId + "' and (Ware_Id is null or Ware_Id='0') ";
							NodesType_Query= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,NodesType_Query);	
							//System.out.println(NodesType_Query);
							Object CountNodes_NodeType = session.createNativeQuery(NodesType_Query).uniqueResult();
							
							BoqHM.put("Site Name", String.valueOf(Sites));
							BoqHM.put("Node Type", String.valueOf(CountNodes_NodeType));
							BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
							BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
							BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
							BoqHM.put("Boards", String.valueOf(CountNodesBoard));
							BoqHM.put("Cabinets", String.valueOf(CountNodesCabinet));

						}else {
							String Node_GCell_Query = "select count(ngc.gcell_id) from node_gcell ngc , node_active na where na.Active_record='1' and na.node_pk = ngc.node_pk and na.Ware_Id = '"
									+ WareId + "' and na.NODE_PK = '" + NodeId + "' ";
							Node_GCell_Query= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_GCell_Query);
							//System.out.println(Node_GCell_Query);
							Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
					///////////////////////////	
							String Node_LCell_Query = "select count(nlc.LCell_Id) from node_lcell nlc , node_active na where na.Active_record='1' and na.node_pk = nlc.node_pk and na.Ware_Id = '"
									+ WareId + "' and na.NODE_PK = '" + NodeId + "' ";
							Node_LCell_Query= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_LCell_Query);
							//System.out.println(Node_LCell_Query);
							Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
					///////////////////////////	
							String Node_UCell_Query = "select count(nuc.UCell_Id) from node_ucell nuc , node_active na where na.Active_record='1' and na.node_pk = nuc.node_pk and na.Ware_Id = '"
									+ WareId + "' and na.NODE_PK = '" + NodeId + "' ";
							Node_UCell_Query= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_UCell_Query);
							//System.out.println(Node_UCell_Query);
							Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
					///////////////////////////	
							String Node_Board_Query = "select count(nlc.BOARD_ID) from NODE_BOARD nlc , node_active na where na.Active_record='1' and na.node_pk = nlc.node_pk and na.Ware_Id = '"
									+ WareId + "' and na.NODE_PK = '" + NodeId + "' ";
							Node_Board_Query= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_Board_Query);
							//System.out.println(Node_Board_Query);
							Object CountNodesBoard = session.createNativeQuery(Node_Board_Query).uniqueResult();
					///////////////////////////	
							String Node_Cabinet_Query = "select count(nlc.CABINET_ID) from NODE_CABINET nlc , node_active na where na.Active_record='1' and na.node_pk = nlc.node_pk and na.Ware_Id = '"
									+ WareId + "' and na.NODE_PK = '" + NodeId + "' ";
							Node_Cabinet_Query= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_Cabinet_Query);
							//System.out.println(Node_Cabinet_Query);
							Object CountNodesCabinet = session.createNativeQuery(Node_Cabinet_Query).uniqueResult();
					///////////////////////////		
							String NodesType_Query = "Select DISTINCT NODE_TYPE From NODE_ACTIVE where Active_record='1' and Ware_Id = '"+ WareId +"' and NODE_PK = '" + NodeId + "' ";
							NodesType_Query= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,NodesType_Query);
							//System.out.println(NodesType_Query);
							Object CountNodes_NodeType = session.createNativeQuery(NodesType_Query).uniqueResult();					
							
							BoqHM.put("Site Name", String.valueOf(Sites));
							BoqHM.put("Node Type", String.valueOf(CountNodes_NodeType));
							BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
							BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
							BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
							BoqHM.put("Boards", String.valueOf(CountNodesBoard));
							BoqHM.put("Cabinets", String.valueOf(CountNodesCabinet));
						}						
						return BoqHM;				
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Nodes BOQ from database in method GetNodeBoqList due to \n " + exceptionAsString);
						logger.info("Error in retreiving Nodes BOQ Data from database in method GetNodeBoqList due to \n " + exceptionAsString);															
						return null;
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
							session.getSessionFactory().close();
						}
					}
				}

		// Node Type BOQ data retrieving
		@RequestMapping(value = "/GetNtypeBoqList", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody

		public LinkedHashMap<String, String> GetNtypeBoqList(@RequestParam String SiteId, @RequestParam String NodeTId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

			try {
				String strEmpty= "SELECT COUNT(distinct WARE_ID) FROM NODE_ACTIVE WHERE NODE_TYPE='" + NodeTId + "' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				String strExist= "Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' and NODE_TYPE='"+ NodeTId + "' ";	
				strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
				String Site_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Site_Query);
				Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
				strEmpty="";
				strExist="";
		////////////////////////////
				strEmpty= "SELECT COUNT(distinct NODE_PK) FROM NODE_ACTIVE where Active_record='1' and NODE_TYPE='"+ NodeTId + "' ";
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				strExist= "SELECT COUNT(distinct NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' and NODE_TYPE='" + NodeTId + "' ";	
				strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
				String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Node_Active_Query);
				Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
				strEmpty="";
				strExist="";
		////////////////////////////
				strEmpty= "select count(distinct ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' ";
				strEmpty= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				strExist= "select count(distinct ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' ";	
				strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
				String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Node_GCell_Query);
				Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
				strEmpty="";
				strExist="";
		////////////////////////////
				strEmpty= "select count(distinct nlc.LCell_Id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' ";
				strEmpty= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				strExist= "select count(distinct nlc.LCell_Id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' ";	
				strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
				String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Node_LCell_Query);
				Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
				strEmpty="";
				strExist="";
		////////////////////////////
				strEmpty= "select count(distinct nuc.UCell_Id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' ";
				strEmpty= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				strExist= "select count(distinct nuc.UCell_Id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' ";	
				strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
				String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Node_UCell_Query);
				Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
				strEmpty="";
				strExist="";

				BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
				BoqHM.put("Nodes", String.valueOf(CountNodes_Active));
				BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
				BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
				BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
				return BoqHM;				
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Node Type BOQ from database in method GetNtypeBoqList due to \n " + exceptionAsString);
				logger.info("Error in retreiving Node Type BOQ from database in method GetNtypeBoqList due to \n " + exceptionAsString);															
				return null;
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		
				
		///node type boq for vendor 
		// Node Type BOQ data retrieving
				@RequestMapping(value = "/GetNtypeVenBoqList", method = RequestMethod.GET, produces = "application/json")
				@ResponseBody

				public LinkedHashMap<String, String> GetNtypeVenBoqList(@RequestParam String SiteId, @RequestParam String NodeTId, @RequestParam String VendorId, @RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

					Session session = almsessions.getSession();
					Transaction tx = session.beginTransaction();
					LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

					try {
						String strEmpty= "SELECT COUNT(distinct WARE_ID) FROM NODE_ACTIVE WHERE NODE_TYPE='" + NodeTId + "' AND VENDOR = '"+VendorId+"' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String strExist= "Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' and NODE_TYPE='"+ NodeTId + "' AND VENDOR = '"+VendorId+"' ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Site_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Site_Query);
						Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
						strEmpty="";
						strExist="";
				////////////////////////////
						strEmpty= "SELECT COUNT(distinct NODE_PK) FROM NODE_ACTIVE where Active_record='1' and NODE_TYPE='"+ NodeTId + "' AND VENDOR = '"+VendorId+"' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						strExist= "SELECT COUNT(distinct NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' and NODE_TYPE='" + NodeTId + "' AND VENDOR = '"+VendorId+"' ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_Active_Query);
						Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
						strEmpty="";
						strExist="";
				////////////////////////////
						strEmpty= "select count(distinct ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' AND na.VENDOR = '"+VendorId+"' ";
						strEmpty= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						strExist= "select count(distinct ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.VENDOR = '"+VendorId+"' ";	
						strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_GCell_Query);
						Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
				////////////////////////////
						strEmpty= "select count(distinct nlc.LCell_Id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' AND na.VENDOR = '"+VendorId+"' ";
						strEmpty= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						strExist= "select count(distinct nlc.LCell_Id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.VENDOR = '"+VendorId+"' ";	
						strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_LCell_Query);
						Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
				////////////////////////////
						strEmpty= "select count(distinct nuc.UCell_Id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' AND na.VENDOR = '"+VendorId+"' ";
						strEmpty= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						strExist= "select count(distinct nuc.UCell_Id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.VENDOR = '"+VendorId+"' ";	
						strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_UCell_Query);
						Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
						strEmpty="";
						strExist="";

						BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
						BoqHM.put("Nodes", String.valueOf(CountNodes_Active));
						BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
						BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
						BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
						return BoqHM;				
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Node Type BOQ from database in method GetNtypeVenBoqList due to \n " + exceptionAsString);
						logger.info("Error in retreiving Node Type BOQ from database in method GetNtypeVenBoqList due to \n " + exceptionAsString);															
						return null;
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
							session.getSessionFactory().close();
						}
					}
				}
				
//retrieve sites and items data on PO click
	@RequestMapping(value = "/findPOSt_Items", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findPOSt_Items(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
				String selectedItem = request.getParameter("selectedItem");
				String POAlreadyCreated = request.getParameter("POAlreadyCreated");
				String selectedSite = request.getParameter("selectedSite");
				
				String paramEnterprise = request.getParameter("paramEnterprise");
				String paramTransmission = request.getParameter("paramTransmission");
				String paramAccess = request.getParameter("paramAccess");
				String paramCore = request.getParameter("paramCore");
				
				String strSites ="SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,j.PO_ID FROM AR_SITE b,ASSET_REGISTRY j WHERE j.AR_ID=b.AR_ID AND b.WARE_ID!='0' AND b.WARE_ID!='null' AND b.WARE_ID is not null AND j.PO_ID='"+selectedItem+"' ";
				
				String strItems ="SELECT distinct a.ITEM_CODE, a.ITEM_NAME, a.PO_ID, b.WARE_ID, a.ITEM_MODEL FROM ASSET_REGISTRY a, AR_SITE b WHERE a.AR_ID=b.AR_ID AND b.WARE_ID='"+selectedSite+"' AND a.PO_ID='"+selectedItem+"' ";						
				
			try {
				if (POAlreadyCreated.equals("false")) {
					strSites= boqDomainVar ("j",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
					rtn.put("listSites",session.createNativeQuery(strSites).list());
					
				}else {
					strItems= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strItems);
					rtn.put("itemList", session.createNativeQuery(strItems).list());				
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Items and Sites Data from database in method findPOSt_Items due to \n " + exceptionAsString);
				logger.info("Error in retreiving Items and Sites Data from database in method findPOSt_Items due to \n " + exceptionAsString);															
				rtn.put("itemList", null);
				rtn.put("listSites", null);
			} 
			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;
	}

	// retrieve items on sites click
	@RequestMapping(value = "/findSitePO_Items", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findSitePO_Items(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
				String selectedSite = request.getParameter("selectedSite");
				String POAlreadyCreated = request.getParameter("POAlreadyCreated");
				String selectedPO = request.getParameter("selectedPO");
				
				String paramEnterprise = request.getParameter("paramEnterprise");
				String paramTransmission = request.getParameter("paramTransmission");
				String paramAccess = request.getParameter("paramAccess");
				String paramCore = request.getParameter("paramCore");	
				
				String strPO ="SELECT distinct a.PO_ID ,b.WARE_ID,b.SITE_NAME,b.SITE_ID,"
						+ "(select count(*) from ASSET_REGISTRY a where a.PO_ID is not null and b.WARE_ID is not null) as countItems "
						+ "FROM ASSET_REGISTRY a, AR_SITE b where a.AR_ID=b.AR_ID and b.WARE_ID='"+ selectedSite + "' ";
				
				String strItems ="SELECT distinct a.ITEM_CODE, a.ITEM_NAME, a.PO_ID, b.WARE_ID, a.ITEM_MODEL FROM ASSET_REGISTRY a, AR_SITE b WHERE a.AR_ID=b.AR_ID AND b.WARE_ID='"+selectedSite+"' AND a.PO_ID='"+selectedPO+"' ";			
			
			try {				
				if (POAlreadyCreated.equals("false")) {
					strPO=  boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strPO);
					rtn.put("listPO", session.createNativeQuery(strPO).list());
				}
				else {
					strItems= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strItems);
					rtn.put("itemList", session.createNativeQuery(strItems).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving PO and Items Data from database in method findSitePO_Items due to \n " + exceptionAsString);
				logger.info("Error in retreiving PO and Items Data from database in method findSitePO_Items due to \n " + exceptionAsString);															
				rtn.put("listPO", null);
				rtn.put("itemList", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;
	}

	
	

	// find cells data in NODE-CELL method
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/findNode_Cells", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> findNode_Cells(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			Map<String, Object> rtn = new LinkedHashMap<>();
			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				
				String paramEnterprise = request.getParameter("paramEnterprise");
				String paramTransmission = request.getParameter("paramTransmission");
				String paramAccess = request.getParameter("paramAccess");
				String paramCore = request.getParameter("paramCore");
				
				String selectedNode = request.getParameter("selectedNode");
				String selectedNdTyp = request.getParameter("selectedNdTyp");
		
			try {
				
				if (selectedNdTyp == null) {
					List<Object[]> cellResult = new ArrayList<Object[]>();
					String strCells1 ="SELECT g.GCELL_ID,g.CELLNAME,g.NODE_PK FROM NODE_GCELL g WHERE g.ACTIVE_RECORD = '1' and g.NODE_PK='"+ selectedNode + "' "; 
					String strCells2 ="SELECT l.LCELL_ID,l.CELLNAME,l.NODE_PK FROM NODE_LCELL l WHERE l.ACTIVE_RECORD = '1' and l.NODE_PK='"+ selectedNode + "' "; 
					String strCells3 ="SELECT u.UCELL_ID,u.CELLNAME,u.NODE_PK FROM NODE_UCELL u WHERE u.ACTIVE_RECORD = '1' and u.NODE_PK='"+ selectedNode + "' ";		
				
					strCells1= boqDomainVar ("g",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
					strCells2= boqDomainVar ("l",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
					strCells3= boqDomainVar ("u",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
				
					Query query1 = session.createNativeQuery(strCells1);
					Query query2 = session.createNativeQuery(strCells2);
					Query query3 = session.createNativeQuery(strCells3);
					cellResult.addAll(query1.list());
					cellResult.addAll(query2.list());
					cellResult.addAll(query3.list());
					
					rtn.put("listCells", cellResult);
					} else {
						System.out.println("selectedNdTyp !NULL");
				        List<Object[]> cellResult = new ArrayList<Object[]>();
						String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and a.NODE_PK=b.NODE_PK and a.NODE_PK='"
								+ selectedNode + "' and b.NODE_TYPE='" + selectedNdTyp + "' ";
						String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and a.NODE_PK=b.NODE_PK and a.NODE_PK='"
								+ selectedNode + "' and b.NODE_TYPE='" + selectedNdTyp + "' "; 
						String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and a.NODE_PK=b.NODE_PK and a.NODE_PK='"
								+ selectedNode + "' and b.NODE_TYPE='" + selectedNdTyp + "' ";		
						
						strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
						strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
						strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);
						
						cellResult.addAll(session.createNativeQuery(strCells1).list());
						cellResult.addAll(session.createNativeQuery(strCells2).list());
						cellResult.addAll(session.createNativeQuery(strCells3).list());
						
						rtn.put("listCells",cellResult);
					}	
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Cells Data from database in method findNode_Cells due to \n " + exceptionAsString);
					logger.info("Error in retreiving Cells Data from database in method findNode_Cells due to \n " + exceptionAsString);															
					rtn.put("listCells", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}


// retrieve site/nodes/cells data in node type-site-node-cells method
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/findNodeTypeSiteNode_Cells", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> findNodeTypeSiteNode_Cells(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			Map<String, Object> rtn = new LinkedHashMap<>();
			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				String selectedNdTyp = request.getParameter("selectedNodetType");
				String selectedItem = request.getParameter("selectedItem");
				
				String paramEnterprise = request.getParameter("paramEnterprise");
				String paramTransmission = request.getParameter("paramTransmission");
				String paramAccess = request.getParameter("paramAccess");
				String paramCore = request.getParameter("paramCore");
					
				String strSites ="SELECT distinct SITE_ID,WARE_NAME,WARE_ID,NODE_TYPE,LONGITUDE,LATITUDE FROM NODE_ACTIVE "
						+ "where ACTIVE_RECORD = '1' and WARE_ID!= 'null' and WARE_ID is not null and WARE_ID!= '0' and NODE_TYPE='"+ selectedNdTyp + "' ";
				
				String strNodes ="SELECT a.NODE_PK,a.SITE_ID,a.NODE_NAME,a.WARE_ID,a.NODE_TYPE,"
						+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";
			
				List<Object[]> cellResult = new ArrayList<Object[]>();
				String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.NODE_TYPE='"+ selectedNdTyp + "' and b.WARE_ID='"+ selectedItem + "' "; 
				String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.NODE_TYPE='"+ selectedNdTyp + "' and b.WARE_ID='"+ selectedItem + "' "; 
				String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.NODE_TYPE='"+ selectedNdTyp + "' and b.WARE_ID='"+ selectedItem + "' ";		
				
				try {
					  if (selectedNdTyp != null) {
						try {
							strSites= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);	
							rtn.put("listSites",session.createNativeQuery(strSites).list());
						} catch (Exception e) {
							sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							exceptionAsString = sw.toString();
							logger.finest("Error in retreiving Sites Data from database in method findNodeTypeSiteNode_Cells due to \n " + exceptionAsString);
							logger.info("Error in retreiving Sites Data from database in method findNodeTypeSiteNode_Cells due to \n " + exceptionAsString);															
							rtn.put("listSites", null);
						}
					}

					if (selectedNdTyp != null && selectedItem != null) {
						try {
							strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);					
							strNodes= strNodes + ") as countGcells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and c.ACTIVE_RECORD = '1' ";
							strNodes= boqDomainVar ("c",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);	
							strNodes= strNodes + ") as countLcells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and d.ACTIVE_RECORD = '1' ";
							strNodes= boqDomainVar ("d",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);	
							strNodes= strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.WARE_ID='"+ selectedItem + "' and a.NODE_TYPE='" + selectedNdTyp + "' ";
							strNodes= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);	
							
							rtn.put("listNodes", session.createNativeQuery(strNodes).list());
						} catch (Exception e) {
							sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							exceptionAsString = sw.toString();
							logger.finest("Error in retreiving Nodes Data from database in method Network_StNdCell due to \n " + exceptionAsString);
							logger.info("Error in retreiving Nodes Data from database in method Network_StNdCell due to \n " + exceptionAsString);															
							rtn.put("listNodes", null);
						}
					}		
					
					if (selectedItem != null) {
						try {
							strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);		
							strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);		
							strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);		
						
							cellResult.addAll(session.createNativeQuery(strCells1).list());
							cellResult.addAll(session.createNativeQuery(strCells2).list());
							cellResult.addAll(session.createNativeQuery(strCells3).list());
							
							rtn.put("listCells", cellResult);
						} catch (Exception e) {
							sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							exceptionAsString = sw.toString();
							logger.finest("Error in retreiving Cells Data from database in method findNodeTypeSiteNode_Cells due to \n " + exceptionAsString);
							logger.info("Error in retreiving Cells Data from database in method findNodeTypeSiteNode_Cells due to \n " + exceptionAsString);															
							rtn.put("listCells", null);
						}
					}
				} 
				catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Data from database in method findNodeTypeSiteNode_Cells due to \n " + exceptionAsString);
					logger.info("Error in retreiving Data from database in method findNodeTypeSiteNode_Cells due to \n " + exceptionAsString);															
					
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}


	// retrieve po/items/sites data in PO-Items-Sites method
	@RequestMapping(value = "/findPOItems_sites", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findPOItems_sites(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
		  try {

			String selectedPo = request.getParameter("selectedPo");
			String POAlreadyCreated = request.getParameter("POAlreadyCreated");
			String selectedItem = request.getParameter("selectedItem");
			
			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramAccess = request.getParameter("paramAccess");
			String paramCore = request.getParameter("paramCore");
			
			String strItems ="SELECT distinct a.ITEM_CODE, a.ITEM_NAME, a.PO_ID, a.ITEM_MODEL FROM ASSET_REGISTRY a WHERE a.PO_ID='"+selectedPo+"' ";			
		
			String strSites ="SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,j.ITEM_CODE,j.PO_ID FROM AR_SITE b,ASSET_REGISTRY j where j.AR_ID=b.AR_ID AND b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and j.PO_ID='" + selectedPo + "' and j.ITEM_CODE='" + selectedItem + "' ";		
			
			
			if (POAlreadyCreated.equals("false")) {
				try {
					strItems= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strItems);
					rtn.put("listItem", session.createNativeQuery(strItems).list());
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Items Data from database in method findPOItems_sites due to \n " + exceptionAsString);
					logger.info("Error in retreiving Items Data from database in method findPOItems_sites due to \n " + exceptionAsString);															
					rtn.put("listItem", null);
				}
			}
			else {
				try {
					strSites= boqDomainVar ("j",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
					rtn.put("listSites", session.createNativeQuery(strSites).list());
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites Data from database in method findPOItems_sites due to \n " + exceptionAsString);
					logger.info("Error in retreiving Sites Data from database in method findPOItems_sites due to \n " + exceptionAsString);															
					rtn.put("listSites", null);
				}
/*
					if (SelectedSite != null) {
						try {
							List<Object[]> listNodesType = (List<Object[]>) session.createNativeQuery(
									"SELECT DISTINCT a.NODE_TYPE,a.WARE_ID,b.ITEM_CODE FROM NODE_ACTIVE a,ASSET_REGISTRY b "
									+ "WHERE a.ACTIVE_RECORD = '1' and b.DOMAIN='Enterprise' and a.WARE_ID=b.WARE_ID and b.PO_ID='" + selectedPo + "' and b.ITEM_CODE='" + selectedItem + "' and b.WARE_ID='"
											+ SelectedSite + "'")
									.list();

							rtn.put("listNodesType", listNodesType);
						} catch (Exception e) {
							logger.info("Error in retreiving list node type Data from database" + exceptionAsString);
							rtn.put("listNodesType", null);
						}
					}
					String selectedNodetType = request.getParameter("selectedNodetType");
					if (selectedNodetType != null) {
						try {
							List<Object[]> listNodes = (List<Object[]>) session.createNativeQuery(
									"SELECT DISTINCT a.NODE_NAME,a.NODE_TYPE,a.NODE_PK,a.WARE_ID FROM NODE_ACTIVE a,ASSET_REGISTRY b WHERE b.DOMAIN='Enterprise' AND a.ACTIVE_RECORD = '1' and a.WARE_ID=b.WARE_ID and b.PO_ID='"
										+ selectedPo + "' and b.ITEM_CODE='" + selectedItem + "' and b.WARE_ID='"
										+ SelectedSite + "' and NODE_TYPE='" + selectedNodetType + "'")
									.list();

							rtn.put("listNodes", listNodes);
						} catch (Exception e) {
							logger.info("Error in retreiving nodes Data from database" + exceptionAsString);
							rtn.put("listNodes", null);
						}
					}
*/					
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Data from database in method findPOItems_sites due to \n " + exceptionAsString);
				logger.info("Error in retreiving Data from database in method findPOItems_sites due to \n " + exceptionAsString);															
				
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;
	}

	// retrieve supp/sites/nodetype/node/cells data in
	// Supp-site-nodetype-nodes-cells method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FindOnClick_SuppStNdTypNdCell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FindOnClick_SuppStNdTypNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
			String selectedSupp = request.getParameter("selectedSupp");
			String selectedNodetType = request.getParameter("selectedNodetType");
			String selectedItem = request.getParameter("selectedItem");
			String selectedNode = request.getParameter("selectedNode");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramAccess = request.getParameter("paramAccess");
			String paramCore = request.getParameter("paramCore");
			
			String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.SUPPLIER_ID FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.SUPPLIER_ID='"+ selectedSupp + "' ";
			
			String strNodesType ="SELECT DISTINCT b.NODE_TYPE,b.WARE_ID,b.SUPPLIER_ID FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' and b.WARE_ID='"+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' ";			
			
			String strNodes ="SELECT NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,"
					+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";			
			
			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' and b.NODE_TYPE='"+ selectedNodetType +"' "; 
			String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' and b.NODE_TYPE='"+ selectedNodetType +"' "; 
			String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' and b.NODE_TYPE='"+ selectedNodetType +"' ";		

			try {
				if (selectedSupp != null && selectedItem == null) {
					try {
						strSites= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
						rtn.put("listSuppSites",session.createNativeQuery(strSites).list());
						
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Sites Data from database in method FindOnClick_SuppStNdTypNdCell due to \n " + exceptionAsString);
						logger.info("Error in retreiving Sites Data from database in method FindOnClick_SuppStNdTypNdCell due to \n " + exceptionAsString);															
						rtn.put("listSuppSites", null);
					}
				}
				if (selectedItem != null) {
					try {
						strNodesType= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodesType);
						rtn.put("listNodesType", session.createNativeQuery(strNodesType).list());	
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Node Type Data from database in method FindOnClick_SuppStNdTypNdCell due to \n " + exceptionAsString);
						logger.info("Error in retreiving Node Type Data from database in method FindOnClick_SuppStNdTypNdCell due to \n " + exceptionAsString);															
						rtn.put("listNodesType", null);
					}
				}
				if (selectedNodetType != null) {
					try {
						strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
						strNodes = strNodes + ") as countNodes,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1' ";
						strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);
						strNodes = strNodes + ") as countGcells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1' ";
						strNodes= boqDomainVar ("c",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);			
						strNodes = strNodes + ") as countLcells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1' ";				
						strNodes= boqDomainVar ("d",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);	
						strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.WARE_ID='"+ selectedItem + "' and a.NODE_TYPE='" + selectedNodetType + "' and a.SUPPLIER_ID='"+ selectedSupp +"' ";			
						strNodes= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
						
						rtn.put("listNodes", session.createNativeQuery(strNodes).list());
						} catch (Exception e) {
							sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							exceptionAsString = sw.toString();
							logger.finest("Error in retreiving Nodes Data from database in method FindOnClick_SuppStNdTypNdCell due to \n " + exceptionAsString);
							logger.info("Error in retreiving Nodes Data from database in method FindOnClick_SuppStNdTypNdCell due to \n " + exceptionAsString);															
							rtn.put("listNodes", null);
					}
				}
				if (selectedNodetType != null) {
					try {
						strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
						strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
						strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
					
						cellResult.addAll(session.createNativeQuery(strCells1).list());
						cellResult.addAll(session.createNativeQuery(strCells2).list());
						cellResult.addAll(session.createNativeQuery(strCells3).list());
							
						rtn.put("listCells",cellResult);
						} catch (Exception e) {
							sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							exceptionAsString = sw.toString();
							logger.finest("Error in retreiving Cells Data from database in method FindOnClick_SuppStNdTypNdCell due to \n " + exceptionAsString);
							logger.info("Error in retreiving Cells Data from database in method FindOnClick_SuppStNdTypNdCell due to \n " + exceptionAsString);															
							rtn.put("listCells", null);
					}
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Data from database in method FindOnClick_SuppStNdTypNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Data from database in method FindOnClick_SuppStNdTypNdCell due to \n " + exceptionAsString);															
				
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FindOnClick_VenStNdTypNdCell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FindOnClick_VenStNdTypNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
			String selectedVen = request.getParameter("selectedVen");
			String selectedNodetType = request.getParameter("selectedNodetType");
			String selectedItem = request.getParameter("selectedItem");
			String selectedNode = request.getParameter("selectedNode");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramAccess = request.getParameter("paramAccess");
			String paramCore = request.getParameter("paramCore");
			
			String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.VENDOR FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR='"+ selectedVen + "' ";
			
			String strNodesType ="SELECT DISTINCT b.NODE_TYPE,b.WARE_ID,b.VENDOR FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' and b.WARE_ID='"+ selectedItem + "' and b.VENDOR='" + selectedVen + "' ";			
			
			String strNodes ="SELECT NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,"
					+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";			
			
			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' and b.NODE_TYPE='"+ selectedNodetType +"' "; 
			String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' and b.NODE_TYPE='"+ selectedNodetType +"' "; 
			String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' and b.NODE_TYPE='"+ selectedNodetType +"' ";		

			try {
				if (selectedVen != null && selectedItem == null) {
					try {
						strSites= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
						rtn.put("listVenSites",session.createNativeQuery(strSites).list());
						
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Sites Data from database in method FindOnClick_VenStNdTypNdCell due to \n " + exceptionAsString);
						logger.info("Error in retreiving Sites Data from database in method FindOnClick_VenStNdTypNdCell due to \n " + exceptionAsString);															
						rtn.put("listVenSites", null);
					}
				}
				if (selectedItem != null) {
					try {
						strNodesType= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodesType);
						rtn.put("listNodesType", session.createNativeQuery(strNodesType).list());	
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in retreiving Node Type Data from database in method FindOnClick_VenStNdTypNdCell due to \n " + exceptionAsString);
						logger.info("Error in retreiving Node Type Data from database in method FindOnClick_VenStNdTypNdCell due to \n " + exceptionAsString);															
						rtn.put("listNodesType", null);
					}
				}
				if (selectedNodetType != null) {
					try {
						strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
						strNodes = strNodes + ") as countNodes,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1' ";
						strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);
						strNodes = strNodes + ") as countGcells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1' ";
						strNodes= boqDomainVar ("c",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);			
						strNodes = strNodes + ") as countLcells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1' ";				
						strNodes= boqDomainVar ("d",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);	
						strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.WARE_ID='"+ selectedItem + "' and a.NODE_TYPE='" + selectedNodetType + "' and a.VENDOR='"+ selectedVen +"' ";			
						strNodes= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
						
						rtn.put("listNodes", session.createNativeQuery(strNodes).list());
						} catch (Exception e) {
							sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							exceptionAsString = sw.toString();
							logger.finest("Error in retreiving Nodes Data from database in method Network_StNdCell due to \n " + exceptionAsString);
							logger.info("Error in retreiving Nodes Data from database in method Network_StNdCell due to \n " + exceptionAsString);															
							rtn.put("listNodes", null);
					}
				}
				if (selectedNodetType != null) {
					try {
						strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
						strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
						strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
					
						cellResult.addAll(session.createNativeQuery(strCells1).list());
						cellResult.addAll(session.createNativeQuery(strCells2).list());
						cellResult.addAll(session.createNativeQuery(strCells3).list());
						
						rtn.put("listCells",cellResult);
					} catch (Exception e) {
							sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							exceptionAsString = sw.toString();
							logger.finest("Error in retreiving Nodes Data from database in method FindOnClick_VenStNdTypNdCell due to \n " + exceptionAsString);
							logger.info("Error in retreiving Nodes Data from database in method FindOnClick_VenStNdTypNdCell due to \n " + exceptionAsString);															
							rtn.put("listCells", null);
					}
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Data from database in method FindOnClick_VenStNdTypNdCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Data from database in method FindOnClick_VenStNdTypNdCell due to \n " + exceptionAsString);															
				
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;
	}
	
	// retrieve supp/sites/nodetype/node/cells data in
	// Supp-nodetype-site-nodes-cells method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FindOnClick_SuppNdTSiteNodeCell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FindOnClick_SuppNdTSiteNodeCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
					
				String selectedItem = request.getParameter("selectedItem");
				String selectedSupp = request.getParameter("selectedSupp");
				String SelectedNodeType = request.getParameter("SelectedNodeType");
				
				String paramEnterprise = request.getParameter("paramEnterprise");
				String paramTransmission = request.getParameter("paramTransmission");
				String paramAccess = request.getParameter("paramAccess");
				String paramCore = request.getParameter("paramCore");
				
				String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.SUPPLIER_ID,b.NODE_TYPE FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.SUPPLIER_ID='"+ selectedSupp + "' AND b.NODE_TYPE='" + SelectedNodeType + "' ";
				
				List<String> listNodeType = new ArrayList<String>();
				String strNodesType ="SELECT DISTINCT b.NODE_TYPE,b.SUPPLIER_ID FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' and b.SUPPLIER_ID='" + selectedSupp + "' ";			
				
				String strNodes ="SELECT NODE_PK,SITE_ID,NODE_NAME,WARE_ID,NODE_TYPE,"
						+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";			
				
				List<Object[]> cellResult = new ArrayList<Object[]>();
				String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' and b.NODE_TYPE='"+ SelectedNodeType +"' "; 
				String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' and b.NODE_TYPE='"+ SelectedNodeType +"' "; 
				String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' and b.NODE_TYPE='"+ SelectedNodeType +"' ";		

				
				try {
					if (selectedSupp != null) {
						try {						
							strNodesType= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodesType);
							Query query = session.createNativeQuery(strNodesType);
							listNodeType = query.list();
					
							if (listNodeType != null) {
								rtn.put("listNodeType", listNodeType);			
							}
						} catch (Exception e) {
							sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							exceptionAsString = sw.toString();
							logger.finest("Error in retreiving Node Type Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n " + exceptionAsString);
							logger.info("Error in retreiving Node Type Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n " + exceptionAsString);															
							rtn.put("listNodeType", null);
						}
					}
	////////////////////////////////	
					if (selectedSupp != null && SelectedNodeType != null) {					
						try {
							strSites= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
							rtn.put("listSuppSites",session.createNativeQuery(strSites).list());
							
						} catch (Exception e) {
							sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							exceptionAsString = sw.toString();
							logger.finest("Error in retreiving Sites Data from database in method Network_StNdCell due to \n " + exceptionAsString);
							logger.info("Error in retreiving Sites Data from database in method Network_StNdCell due to \n " + exceptionAsString);															
							rtn.put("listSuppSites", null);
						}
					}
	/////////////////////////////////
					if (selectedItem != null) {
						try {
							strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
							strNodes = strNodes + ") as countNodes,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1' ";
							strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);
							strNodes = strNodes + ") as countGcells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1' ";
							strNodes= boqDomainVar ("c",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);			
							strNodes = strNodes + ") as countLcells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1' ";				
							strNodes= boqDomainVar ("d",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);	
							strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.WARE_ID='"+ selectedItem + "' and a.NODE_TYPE='" + SelectedNodeType + "' and a.SUPPLIER_ID='"+ selectedSupp +"' ";			
							strNodes= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
							
							rtn.put("listSuppNodes", session.createNativeQuery(strNodes).list());
							//System.out.println("list nodes ==> "+ mapper.writeValueAsString(nodeSuppList));
				
						} catch (Exception e) {
							sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							exceptionAsString = sw.toString();
							logger.finest("Error in retreiving Nodes Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n " + exceptionAsString);
							logger.info("Error in retreiving Nodes Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n " + exceptionAsString);															
							rtn.put("listSuppNodes", null);
						}
					}
	/////////////////////////////////
					if (selectedItem != null) {
						try {
							strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
							strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
							strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
						
							cellResult.addAll(session.createNativeQuery(strCells1).list());
							cellResult.addAll(session.createNativeQuery(strCells2).list());
							cellResult.addAll(session.createNativeQuery(strCells3).list());
								
							rtn.put("listCells",cellResult);
						} catch (Exception e) {
							sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							exceptionAsString = sw.toString();
							logger.finest("Error in retreiving Cells Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n " + exceptionAsString);
							logger.info("Error in retreiving Cells Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n " + exceptionAsString);															
							rtn.put("listCells", null);
						}
					}
//////////////////////////////////					

				//String selectedNode = request.getParameter("selectedNode");
/*
				if (selectedNode != null) {
					try {
						List<Object[]> result = session.createNativeQuery(
								"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
										+ selectedNode + "'")
								.list();
						List<Object[]> result1 = session.createNativeQuery(
								"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
										+ selectedNode + "'")
								.list();
						List<Object[]> result2 = session.createNativeQuery(
								"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
										+ selectedNode + "'")
								.list();
*/

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n " + exceptionAsString);
				logger.info("Error in retreiving Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n " + exceptionAsString);															
				
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;
	}

	// retrieve vendor/sites/nodetype/node/cells data in
		// Vendor-nodetype-site-nodes-cells method
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/FindOnClick_VenNdTSiteNodeCell", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> FindOnClick_VenNdTSiteNodeCell(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			Map<String, Object> rtn = new LinkedHashMap<>();
			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
						
					String selectedItem = request.getParameter("selectedItem");
					String selectedVen = request.getParameter("selectedVen");
					String SelectedNodeType = request.getParameter("SelectedNodeType");
					
					String paramEnterprise = request.getParameter("paramEnterprise");
					String paramTransmission = request.getParameter("paramTransmission");
					String paramAccess = request.getParameter("paramAccess");
					String paramCore = request.getParameter("paramCore");
					
					String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.VENDOR,b.NODE_TYPE FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR='"+ selectedVen + "' AND b.NODE_TYPE='" + SelectedNodeType + "' ";
					
					List<String> listNodeType = new ArrayList<String>();
					String strNodesType ="SELECT DISTINCT b.NODE_TYPE,b.VENDOR FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' and b.VENDOR='" + selectedVen + "' ";			
					
					String strNodes ="SELECT NODE_PK,SITE_ID,NODE_NAME,WARE_ID,NODE_TYPE,"
							+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";			
					
					List<Object[]> cellResult = new ArrayList<Object[]>();
					String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' and b.NODE_TYPE='"+ SelectedNodeType +"' "; 
					String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' and b.NODE_TYPE='"+ SelectedNodeType +"' "; 
					String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' and b.NODE_TYPE='"+ SelectedNodeType +"' ";		

					
					try {
						if (selectedVen != null) {
							try {						
								strNodesType= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodesType);
								Query query = session.createNativeQuery(strNodesType);
								listNodeType = query.list();
						
								if (listNodeType != null) {
									rtn.put("listNodeType", listNodeType);		
								}
							} catch (Exception e) {
								sw = new StringWriter();
								e.printStackTrace(new PrintWriter(sw));
								exceptionAsString = sw.toString();
								logger.finest("Error in retreiving Node Type Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n " + exceptionAsString);
								logger.info("Error in retreiving Node Type Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n " + exceptionAsString);															
								rtn.put("listNodeType", null);
							}
						}
		////////////////////////////////	
						if (selectedVen != null && SelectedNodeType != null) {					
							try {
								strSites= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
								rtn.put("listVenSites",session.createNativeQuery(strSites).list());
							
							} catch (Exception e) {
								sw = new StringWriter();
								e.printStackTrace(new PrintWriter(sw));
								exceptionAsString = sw.toString();
								logger.finest("Error in retreiving Sites Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n " + exceptionAsString);
								logger.info("Error in retreiving Sites Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n " + exceptionAsString);															
								rtn.put("listVenSites", null);
							}
						}
		/////////////////////////////////
						if (selectedItem != null) {
							try {
								strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
								strNodes = strNodes + ") as countNodes,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1' ";
								strNodes= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);
								strNodes = strNodes + ") as countGcells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1' ";
								strNodes= boqDomainVar ("c",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);			
								strNodes = strNodes + ") as countLcells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1' ";				
								strNodes= boqDomainVar ("d",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);	
								strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.WARE_ID='"+ selectedItem + "' and a.NODE_TYPE='" + SelectedNodeType + "' and a.VENDOR='"+ selectedVen +"' ";			
								strNodes= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodes);		
								
								rtn.put("listVenNodes", session.createNativeQuery(strNodes).list());
							} catch (Exception e) {
								sw = new StringWriter();
								e.printStackTrace(new PrintWriter(sw));
								exceptionAsString = sw.toString();
								logger.finest("Error in retreiving Node Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n " + exceptionAsString);
								logger.info("Error in retreiving Node Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n " + exceptionAsString);															
								rtn.put("listVenNodes", null);
							}
						}
		/////////////////////////////////
						if (selectedItem != null) {
							try {
								strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
								strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
								strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
							
								cellResult.addAll(session.createNativeQuery(strCells1).list());
								cellResult.addAll(session.createNativeQuery(strCells2).list());
								cellResult.addAll(session.createNativeQuery(strCells3).list());
										
								rtn.put("listCells",cellResult);
							} catch (Exception e) {
								sw = new StringWriter();
								e.printStackTrace(new PrintWriter(sw));
								exceptionAsString = sw.toString();
								logger.finest("Error in retreiving Cells Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n " + exceptionAsString);
								logger.info("Error in retreiving Cells Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n " + exceptionAsString);															
								rtn.put("listCells", null);
							}
						}
//////////////////////////////////						

					//String selectedNode = request.getParameter("selectedNode");
	/*
					if (selectedNode != null) {
						try {
							List<Object[]> result = session.createNativeQuery(
									"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
											+ selectedNode + "'")
									.list();
							List<Object[]> result1 = session.createNativeQuery(
									"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
											+ selectedNode + "'")
									.list();
							List<Object[]> result2 = session.createNativeQuery(
									"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
											+ selectedNode + "'")
									.list();
	*/

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n " + exceptionAsString);
					logger.info("Error in retreiving Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n " + exceptionAsString);															
					
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}


	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.setAutoGrowCollectionLimit(1024);
	}
	
	// PO BOQ data retrieving
	@RequestMapping(value = "/GetPOBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public LinkedHashMap<String, String> GetPOBoqList(@RequestParam String POID,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		
		try {	
			String strEmpty="SELECT COUNT(DISTINCT PO_ID) FROM ASSET_REGISTRY WHERE PO_ID!='null' ";
			strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
			String strExist= "Select DISTINCT PO_ID From ASSET_REGISTRY where PO_ID='" + POID + "' ";	
			strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
			String PO_Query = POID == "" ? strEmpty : strExist;
			//System.out.println(PO_Query);
			Object PoC = session.createNativeQuery(PO_Query).uniqueResult();
			//System.out.println("po :::"+PoC);
			BoqHM.put("PO", String.valueOf(PoC));
			strEmpty="";
			strExist="";	
			
			strEmpty="SELECT ROUND(SUM(INITIALCOST), 2) FROM FIXED_ASSET_REGISTRY ";
			if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
				strEmpty= strEmpty + "WHERE ";
			}
			strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
			strExist= "Select TOTAL_AMOUNT from PURCHASE_ORDER where PO_ID='" + POID +"' ";	
			//strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
			String PO_Amount_Query = POID == "" ? strEmpty : strExist;
			//System.out.println(PO_Amount_Query);
			Object PO_Amount = session.createNativeQuery(PO_Amount_Query).uniqueResult();
			BoqHM.put(POID == "" ? "PO Cost" : "PO Amount", String.valueOf(PO_Amount));		
			strEmpty="";
			strExist="";
			
			strEmpty="SELECT ROUND(SUM(NETCOST), 2) FROM FIXED_ASSET_REGISTRY ";
			if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
				strEmpty= strEmpty + "WHERE ";
			}
			strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
			strExist= "Select NET_TOTAL_AMOUNT from PURCHASE_ORDER where PO_ID='" + POID  +"' ";	
			//strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
			String PO_NET_Amount_Query = POID == "" ? strEmpty : strExist;
			//System.out.println(PO_NET_Amount_Query);
			Object PO_NET_Amount = session.createNativeQuery(PO_NET_Amount_Query).uniqueResult();
			BoqHM.put(POID == "" ? "PO Net Cost" : "PO Net Amount", String.valueOf(PO_NET_Amount));
			strEmpty="";
			strExist="";
			
			strEmpty="SELECT COUNT(DISTINCT a.WARE_ID) FROM AR_SITE a, ASSET_REGISTRY b where a.AR_ID = b.AR_ID ";
			strEmpty= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
			strExist= "Select COUNT(DISTINCT WARE_ID) FROM AR_SITE a, ASSET_REGISTRY b where a.AR_ID = b.AR_ID and b.PO_ID='" + POID + "' ";	
			strExist= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
			String Site_Query = POID == "" ? strEmpty : strExist;
			//System.out.println(Site_Query);
			Object SiteC = session.createNativeQuery(Site_Query).uniqueResult();
			//System.out.println("SiteC :::"+SiteC);
			strEmpty="";
			strExist="";
			
			strEmpty="SELECT COUNT(DISTINCT a.ITEM_CODE) FROM ASSET_REGISTRY a, AR_SITE b where a.AR_ID = b.AR_ID ";
			strEmpty= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
			strExist= "Select COUNT(DISTINCT a.ITEM_CODE) from ASSET_REGISTRY a, AR_SITE b where a.AR_ID = b.AR_ID and PO_ID='" + POID + "' ";	
			strExist= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
			String Item_Query = POID == "" ? strEmpty : strExist;
			//System.out.println(Item_Query);
			Object ItemC = session.createNativeQuery(Item_Query).uniqueResult();
			//System.out.println("ItemC :::"+ItemC);	
			strEmpty="";
			strExist="";
		
			BoqHM.put("Sites", String.valueOf(SiteC));
			BoqHM.put("Items", String.valueOf(ItemC));

			return BoqHM;
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving PO BOQ from database in method GetPOBoqList due to \n " + exceptionAsString);
			logger.info("Error in retreiving PO BOQ from database in method GetPOBoqList due to \n " + exceptionAsString);															
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
				session.getSessionFactory().close();
			}
		}
	}
	
	
	// PO BOQ data retrieving
		@RequestMapping(value = "/GetPOSiteBoqList", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody
		public LinkedHashMap<String, String> GetPOSiteBoqList(@RequestParam String POID,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
			
			try {				
				String strEmpty="SELECT COUNT(DISTINCT PO_ID) FROM ASSET_REGISTRY ";
				if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
					strEmpty= strEmpty + "WHERE ";
				}
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				String strExist= "Select DISTINCT PO_ID From ASSET_REGISTRY where PO_ID='" + POID + "' ";	
				strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String PO_Query = POID == "" ? strEmpty : strExist;
				//System.out.println(PO_Query);
				Object Po = session.createNativeQuery(PO_Query).uniqueResult();
				strEmpty="";
				strExist="";	
			
				strEmpty="SELECT COUNT(DISTINCT a.WARE_ID) FROM AR_SITE a, ASSET_REGISTRY b where a.AR_ID = b.AR_ID ";
				strEmpty= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "Select SITE_NAME FROM AR_SITE a, ASSET_REGISTRY b where a.AR_ID = b.AR_ID and b.PO_ID='" + POID +"' ";	
				strExist= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Site_Query = POID == "" ? strEmpty : strExist;
				//System.out.println(Site_Query);
				Object Site = session.createNativeQuery(Site_Query).uniqueResult();
				strEmpty="";
				strExist="";
				
				strEmpty="SELECT COUNT(DISTINCT a.ITEM_CODE) FROM ASSET_REGISTRY a, AR_SITE b where a.AR_ID = b.AR_ID ";
				strEmpty= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "Select COUNT(DISTINCT a.ITEM_CODE) from ASSET_REGISTRY a, AR_SITE b where a.AR_ID = b.AR_ID and a.PO_ID='" + POID + "' ";	
				strExist= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Item_Query = POID == "" ? strEmpty : strExist;
				//System.out.println(Item_Query);
				Object Item = session.createNativeQuery(Item_Query).uniqueResult();
				strEmpty="";
				strExist="";

				BoqHM.put("PO", String.valueOf(Po));
				
				if(POID!="") {
					String PO_Amount_Query= "Select TOTAL_AMOUNT from PURCHASE_ORDER where PO_ID='" + POID  + "' ";
					//System.out.println(PO_Amount_Query);
					Object PO_Amount = session.createNativeQuery(PO_Amount_Query).uniqueResult();
					BoqHM.put("PO Amount", String.valueOf(PO_Amount));
	
	
					String PO_Net_Amount_Query= "Select NET_TOTAL_AMOUNT from PURCHASE_ORDER where PO_ID='" + POID  + "' ";
					//System.out.println(PO_Net_Amount_Query);
					Object PO_Net_Amount = session.createNativeQuery(PO_Net_Amount_Query).uniqueResult();
					BoqHM.put("PO Net Amount", String.valueOf(PO_Net_Amount));
				}						
				
				BoqHM.put("Site", String.valueOf(Site));
				BoqHM.put("Items", String.valueOf(Item));			
				return BoqHM;

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving PO BOQ from database in method GetPOSiteBoqList due to \n " + exceptionAsString);
				logger.info("Error in retreiving PO BOQ from database in method GetPOSiteBoqList due to \n " + exceptionAsString);															
				return null;
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
}
