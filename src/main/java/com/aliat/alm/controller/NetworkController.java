package com.aliat.alm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.aliat.alm.models.Item;
import com.aliat.alm.models.Supplier;
import com.aliat.alm.models.Warehouse;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class NetworkController {
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(NetworkController.class);
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws JsonProcessingException
	 */

	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;
	
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Network_Index", method = RequestMethod.GET)
	public String Network_Index(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//			throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			double longitude = 0;
			double latitude = 0;			
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				// On-load Site-Node-Cells Arrays Selection
				try {
					List<Object[]> cellResult = new ArrayList<Object[]>();
					cellResult.addAll(session.createNativeQuery(
							"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
							.list());
					cellResult.addAll(session.createNativeQuery(
							"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
							.list());
					cellResult.addAll(session.createNativeQuery(
							"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
							.list());

					model.addAttribute("listCells", mapper.writeValueAsString(cellResult));
				} catch (Exception e) {
					logger.info("Error in retreiving Cells Data from database", e);
					model.addAttribute("listCells", "null");
				}
				// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				try {

					model.addAttribute("listNodes", mapper.writeValueAsString((List<Object[]>) session.createNativeQuery(
							"SELECT NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1') as countGCells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1') as countLCells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1') as countUCells,SUPPLIER_ID FROM NODE_ACTIVE a WHERE ACTIVE_RECORD = '1'")
							.list()));

				} catch (Exception e) {
					logger.info("Error in retreiving Nodes Data from database", e);
					model.addAttribute("listNodes", "null");
				}

				// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				try {

					model.addAttribute("listSites", mapper.writeValueAsString(
							(List<Object[]>) session.createNativeQuery("SELECT distinct b.SITE_ID,b.WARE_NAME,b.WARE_ID,"
									+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
									+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
									+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID  and w.ACTIVE_RECORD = '1') as countnodes,"
									+ "(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countGcells,"
									+ "(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countLcells,"
									+ "(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countUcells"
									+ " FROM WAREHOUSE b where b.WARE_ID!='0' and b.WARE_ID!='null'").list()));

				} catch (Exception e) {
					logger.info("Error in retreiving Sites Data from database", e);
					model.addAttribute("listSites", "null");

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
					logger.info("Error in retreiving BOQ Data from database", e);
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

			return "Network/Network_Index";
		}
	}
	
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
//			throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);
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

				List<String> listSites = new ArrayList<String>();
				String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
						+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
				
				List<String> listNodes = new ArrayList<String>();
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
					
				//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
				query = session.createNativeQuery(strSites);
				listSites = query.list();
				model.addAttribute("listSites", mapper.writeValueAsString(listSites));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));
				//System.out.println("arrayParam sites ==> "+mapper.writeValueAsString(arrayParam));
				}catch (Exception e) {
					logger.info("Error in retreiving Sites Data from database", e);
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
					
				//System.out.println("final query nodes"+mapper.writeValueAsString(strNodes));
				query = session.createNativeQuery(strNodes);
				listNodes = query.list();
				model.addAttribute("listNodes", mapper.writeValueAsString(listNodes));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list nodes ==> "+ mapper.writeValueAsString(listNodes));
				//System.out.println("arrayParam nodes ==> "+mapper.writeValueAsString(arrayParam));
				
				} catch (Exception e) {
				logger.info("Error in retreiving Nodes Data from database", e);
				model.addAttribute("listNodes", "null");
			}	
			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
			try {			
				strCells1= AppendQuery("a",arrayParam, strCells1);
				strCells2= AppendQuery("a",arrayParam, strCells2);
				strCells3= AppendQuery("a",arrayParam, strCells3);
			
				//System.out.println("final query cells 1"+mapper.writeValueAsString(strCells1));
				//System.out.println("final query cells 2"+mapper.writeValueAsString(strCells2));
				//System.out.println("final query cells 3"+mapper.writeValueAsString(strCells3));
				
				cellResult.addAll(session.createNativeQuery(strCells1).list());
				cellResult.addAll(session.createNativeQuery(strCells2).list());
				cellResult.addAll(session.createNativeQuery(strCells3).list());
				
				model.addAttribute("listCells", mapper.writeValueAsString(cellResult));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list cells ==> "+ mapper.writeValueAsString(cellResult));
				//System.out.println("arrayParam cells ==> "+mapper.writeValueAsString(arrayParam));
				
			} catch (Exception e) {
				logger.info("Error in retreiving Cells Data from database", e);
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
					logger.info("Error in retreiving BOQ Data from database", e);
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
//			throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			double longitude = 0;
			double latitude = 0;
			ObjectMapper mapper = new ObjectMapper();
			Session session = null;
			Transaction tx = null;
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
	        
	    	List<String> listSites = new ArrayList<String>();
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			List<String> listNodes = new ArrayList<String>();
			String strNodes ="SELECT NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,"
					+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";
			
			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' "; 
			String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' "; 
			String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' ";		
			
			List<Object[]> listNodesType = new ArrayList<Object[]>();
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
						
					//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
					query = session.createNativeQuery(strSites);
					listSites = query.list();
					model.addAttribute("listSites", mapper.writeValueAsString(listSites));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));
					//System.out.println("arrayParam sites ==> "+mapper.writeValueAsString(arrayParam));

			} catch (Exception e) {
				logger.info("Error in retreiving Sites Data from database", e);
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
					
				//System.out.println("final query nodes"+mapper.writeValueAsString(strNodes));
				query = session.createNativeQuery(strNodes);
				listNodes = query.list();
				model.addAttribute("listNodes", mapper.writeValueAsString(listNodes));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list nodes ==> "+ mapper.writeValueAsString(listNodes));
				//System.out.println("arrayParam nodes ==> "+mapper.writeValueAsString(arrayParam));
				
			} catch (Exception e) {
				logger.info("Error in retreiving Nodes Data from database", e);
				model.addAttribute("listNodes", "null");
			}

			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
			try {
				strCells1= AppendQuery("a",arrayParam, strCells1);
				strCells2= AppendQuery("a",arrayParam, strCells2);
				strCells3= AppendQuery("a",arrayParam, strCells3);
			
				//System.out.println("final query cells 1 "+mapper.writeValueAsString(strCells1));
				//System.out.println("final query cells 2 "+mapper.writeValueAsString(strCells2));
				//System.out.println("final query cells 3 "+mapper.writeValueAsString(strCells3));
							
				cellResult.addAll(session.createNativeQuery(strCells1).list());
				cellResult.addAll(session.createNativeQuery(strCells2).list());
				cellResult.addAll(session.createNativeQuery(strCells3).list());
				
				model.addAttribute("listCells", mapper.writeValueAsString(cellResult));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list cells ==> "+ mapper.writeValueAsString(cellResult));
				//System.out.println("arrayParam cells ==> "+mapper.writeValueAsString(arrayParam));
				
				} catch (Exception e) {
					logger.info("Error in retreiving Cells Data from database", e);
					model.addAttribute("listCells", "null");
				}
			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//			
				try {
					strNodesType =AppendQuery("a",arrayParam, strNodesType);
					
					//System.out.println("final query nodes type "+mapper.writeValueAsString(strNodesType));
					query = session.createNativeQuery(strNodesType);
					listNodesType = query.list();
					model.addAttribute("listNodesType", mapper.writeValueAsString(listNodesType));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					//System.out.println("list Nodes Type ==> "+ mapper.writeValueAsString(listNodesType));
					//System.out.println("arrayParam nodes ==> "+mapper.writeValueAsString(arrayParam));
					
				} catch (Exception e) {
					logger.info("Error in retreiving node type array Data from database", e);
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
					logger.info("Error in retreiving BOQ Data from database", e);
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
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		double longitude = 0;
		double latitude = 0;
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
	        
	        List<String> listSites = new ArrayList<String>();
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			List<String> listNodesType = new ArrayList<String>();
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
				query = session.createNativeQuery(strSites);
				listSites = query.list();
				model.addAttribute("listSites", mapper.writeValueAsString(listSites));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));					
			} catch (Exception e) {
				logger.info("Error in retreiving Sites Data from database", e);
				model.addAttribute("listSites", "null");
			}	
			
			try {
				strNodesType = AppendQuery("a",arrayParam, strNodesType);			
				strNodesType = strNodesType + ") as countNodes FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' ";
				strNodesType=  AppendQuery("b",arrayParam, strNodesType);
				
				//System.out.println("final query nodes type "+mapper.writeValueAsString(strNodesType));
				query = session.createNativeQuery(strNodesType);
				listNodesType = query.list();
				model.addAttribute("listNodesType", mapper.writeValueAsString(listNodesType));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));	
			} catch (Exception e) {
				logger.info("Error in retreiving node type array Data from database", e);
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
				logger.info("Error in retreiving BOQ Data from database", e);
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


@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_SupStNdCell", method = RequestMethod.GET)
public String Network_SupStNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
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
	        
	    	List<String> listSites = new ArrayList<String>();
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			List<String> listSupp = new ArrayList<String>();
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
						
					//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
					Query query = session.createSQLQuery(strSites);
					listSites = query.list();
					model.addAttribute("listSites", mapper.writeValueAsString(listSites));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));
					//System.out.println("arrayParam sites ==> "+mapper.writeValueAsString(arrayParam));

			} catch (Exception e) {
				logger.info("Error in retreiving Sites Data from database", e);
				model.addAttribute("listSites", "null");
			}
			
			try {
				strSup=  AppendQuery("a",arrayParam, strSup);	
				query = session.createNativeQuery(strSup);
				listSupp = query.list();
				model.addAttribute("listSupp", mapper.writeValueAsString(listSupp));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));				
			} catch (Exception e) {
				logger.info("Error in retreiving Suppliers Data from database", e);
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



@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_VnStNdCell", method = RequestMethod.GET)
public String Network_VnStNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
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
	        
	    	List<String> listSites = new ArrayList<String>();
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			List<String> listVen = new ArrayList<String>();
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
						
					//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
					Query query = session.createSQLQuery(strSites);
					listSites = query.list();
					model.addAttribute("listSites", mapper.writeValueAsString(listSites));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));
					//System.out.println("arrayParam sites ==> "+mapper.writeValueAsString(arrayParam));

			} catch (Exception e) {
				logger.info("Error in retreiving Sites Data from database", e);
				model.addAttribute("listSites", "null");
			}
			
			try {
				strVen=  AppendQuery("a",arrayParam, strVen);	
				//System.out.println("final query vendors"+mapper.writeValueAsString(strVen));
				
				query = session.createNativeQuery(strVen);
				listVen = query.list();
				model.addAttribute("listVen", mapper.writeValueAsString(listVen));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));				
			} catch (Exception e) {
				logger.info("Error in retreiving Vendors Data from database", e);
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


@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_VnStNdTypNdCell", method = RequestMethod.GET)
public String Network_VnStNdTypNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
	        
	    	List<String> listSites = new ArrayList<String>();
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			List<String> listVen = new ArrayList<String>();
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
						
					//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
					Query query = session.createSQLQuery(strSites);
					listSites = query.list();
					model.addAttribute("listSites", mapper.writeValueAsString(listSites));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));
					//System.out.println("arrayParam sites ==> "+mapper.writeValueAsString(arrayParam));

			} catch (Exception e) {
				logger.info("Error in retreiving Sites Data from database", e);
				model.addAttribute("listSites", "null");
			}
			
			try {
				strVen=  AppendQuery("a",arrayParam, strVen);	
				//System.out.println("final query vendors"+mapper.writeValueAsString(strVen));
				
				query = session.createNativeQuery(strVen);
				listVen = query.list();
				model.addAttribute("listVen", mapper.writeValueAsString(listVen));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));				
			} catch (Exception e) {
				logger.info("Error in retreiving Vendors Data from database", e);
				model.addAttribute("listVen", "null");
			}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
				}
			}
		}
		return "Network/Network_VnStNdTypNdCell";
	}
}	
	


@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_SupStNdTypNdCell", method = RequestMethod.GET)
public String Network_SupStNdTypNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
	        
	    	List<String> listSites = new ArrayList<String>();
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			List<String> listSupp = new ArrayList<String>();
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
						
					//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
					Query query = session.createSQLQuery(strSites);
					listSites = query.list();
					model.addAttribute("listSites", mapper.writeValueAsString(listSites));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));
					//System.out.println("arrayParam sites ==> "+mapper.writeValueAsString(arrayParam));

				} catch (Exception e) {
					logger.info("Error in retreiving Sites Data from database", e);
					model.addAttribute("listSites", "null");
				}	
				
				try {
					strSup=  AppendQuery("a",arrayParam, strSup);	
					
					//System.out.println("final query supp "+mapper.writeValueAsString(strSup));
					Query query = session.createSQLQuery(strSup);
					listSupp = query.list();
					model.addAttribute("listSupp", mapper.writeValueAsString(listSupp));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					//System.out.println("list supp ==> "+ mapper.writeValueAsString(listSupp));
					//System.out.println("arrayParam supp ==> "+mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					logger.info("Error in retreiving Suppliers Data from database", e);
					model.addAttribute("listSupp", "null");
				}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
				}
			}
		}
		return "Network/Network_SupStNdTypNdCell";
	}
}	
	

@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_SupNdTypStNdCell", method = RequestMethod.GET)
public String Network_SupNdTypStCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
	        
	    	List<String> listSites = new ArrayList<String>();
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			List<String> listSupp = new ArrayList<String>();
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
					
				//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
				Query query = session.createSQLQuery(strSites);
				listSites = query.list();
				model.addAttribute("listSites", mapper.writeValueAsString(listSites));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));
				//System.out.println("arrayParam sites ==> "+mapper.writeValueAsString(arrayParam));

			} catch (Exception e) {
				logger.info("Error in retreiving Sites Data from database", e);
				model.addAttribute("listSites", "null");
			}
			
		 try {
				strSup=  AppendQuery("a",arrayParam, strSup);	
				
				//System.out.println("final query supp "+mapper.writeValueAsString(strSup));
				Query query = session.createSQLQuery(strSup);
				listSupp = query.list();
				model.addAttribute("listSupp", mapper.writeValueAsString(listSupp));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list supp ==> "+ mapper.writeValueAsString(listSupp));
				//System.out.println("arrayParam supp ==> "+mapper.writeValueAsString(arrayParam));
				
				} catch (Exception e) {
					logger.info("Error in retreiving Suppliers Data from database", e);
					model.addAttribute("listSupp", "null");
				}
		 
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
				}
			}
		}
		return "Network/Network_SupNdTypStNdCell";
	}
}


@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_VnNdTypStNdCell", method = RequestMethod.GET)
public String Network_VnNdTypStCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
	        
	    	List<String> listSites = new ArrayList<String>();
			String strSites ="SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			List<String> listVen = new ArrayList<String>();
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
					
				//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
				Query query = session.createSQLQuery(strSites);
				listSites = query.list();
				model.addAttribute("listSites", mapper.writeValueAsString(listSites));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));
				//System.out.println("arrayParam sites ==> "+mapper.writeValueAsString(arrayParam));

			} catch (Exception e) {
				logger.info("Error in retreiving Sites Data from database", e);
				model.addAttribute("listSites", "null");
			}
			
		 try {
				strVen=  AppendQuery("a",arrayParam, strVen);	
				
				//System.out.println("final query supp "+mapper.writeValueAsString(strSup));
				Query query = session.createSQLQuery(strVen);
				listVen = query.list();
				model.addAttribute("listVen", mapper.writeValueAsString(listVen));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				
				} catch (Exception e) {
					logger.info("Error in retreiving Vendors Data from database", e);
					model.addAttribute("listVen", "null");
				}
		 
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
				}
			}
		}
		return "Network/Network_VnNdTypStNdCell";
	}
}

@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_PoSiteItem", method = RequestMethod.GET)
public String Network_PoSiteItem(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
	        
	    	List<String> listSites = new ArrayList<String>();
			String strSites ="SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,"
					+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
					+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			List<String> listPO = new ArrayList<String>();
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
					
				//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
				Query query = session.createSQLQuery(strSites);
				listSites = query.list();
				model.addAttribute("listSites", mapper.writeValueAsString(listSites));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));
				//System.out.println("arrayParam sites ==> "+mapper.writeValueAsString(arrayParam));

			} catch (Exception e) {
				logger.info("Error in retreiving Sites Data from database", e);
				model.addAttribute("listSites", "null");

			}

			try {
				strPO=  AppendQuery("a",arrayParam, strPO);	
				
				//System.out.println("final query PO "+mapper.writeValueAsString(strPO));
				Query query = session.createSQLQuery(strPO);
				listPO = query.list();
				model.addAttribute("listPO", mapper.writeValueAsString(listPO));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list PO ==> "+ mapper.writeValueAsString(listPO));
				//System.out.println("arrayParam PO ==> "+mapper.writeValueAsString(arrayParam));
							
			} catch (Exception e) {
				logger.info("Error in retreiving PO Data from database", e);
				model.addAttribute("listPO", "null");
				}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
				}
			}
		}
		return "Network/Network_PoSiteItem";
	}
}

@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_PoItemSite", method = RequestMethod.GET)
public String Network_PoItemSite(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
	        
	    	List<String> listSites = new ArrayList<String>();
			String strSites ="SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,"
					+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
					+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID and w.ACTIVE_RECORD = '1' ";
			
			List<String> listPO = new ArrayList<String>();
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
						
					//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
					Query query = session.createSQLQuery(strSites);
					listSites = query.list();
					model.addAttribute("listSites", mapper.writeValueAsString(listSites));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));
					//System.out.println("arrayParam sites ==> "+mapper.writeValueAsString(arrayParam));

				} catch (Exception e) {
					logger.info("Error in retreiving Sites Data from database", e);
					model.addAttribute("listSites", "null");
				}

				try {
					strPO=  AppendQuery("a",arrayParam, strPO);	
					
					//System.out.println("final query PO "+mapper.writeValueAsString(strPO));
					Query query = session.createSQLQuery(strPO);
					listPO = query.list();
					model.addAttribute("listPO", mapper.writeValueAsString(listPO));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					//System.out.println("list PO ==> "+ mapper.writeValueAsString(listPO));
					//System.out.println("arrayParam PO ==> "+mapper.writeValueAsString(arrayParam));
					
			} catch (Exception e) {
				logger.info("Error in retreiving PO Data from database", e);
				model.addAttribute("listPO", "null");
				}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
				}
			}
		}
		return "Network/Network_PoItemSite";
	}
}


@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_SitePoItem", method = RequestMethod.GET)
public String Network_SitePoItem(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
	        
	    	List<String> listSites = new ArrayList<String>();
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
					
				//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
				Query query = session.createSQLQuery(strSites);
				listSites = query.list();
				model.addAttribute("listSites", mapper.writeValueAsString(listSites));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));
				//System.out.println("arrayParam sites ==> "+mapper.writeValueAsString(arrayParam));

			} catch (Exception e) {
				logger.info("Error in retreiving Sites Data from database", e);
				model.addAttribute("listSites", "null");
			}

			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
				}
			}
		}
		return "Network/Network_SitePoItem";
	}
}


@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_NdTypNdCell", method = RequestMethod.GET)
public String Network_NdTypNdCell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
	        
	    	List<String> listNodes = new ArrayList<String>();
			String strNodes ="SELECT b.SITE_ID,b.WARE_NAME,b.NODE_PK,b.LATITUDE,b.LONGITUDE,"
					+ "(select COUNT(*) from NODE_ACTIVE w where w.ACTIVE_RECORD = '1' ";
			
			List<String> listNodesType = new ArrayList<String>();
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
			
				//System.out.println("final query nodes"+mapper.writeValueAsString(strNodes));
				Query query = session.createSQLQuery(strNodes);
				listNodes = query.list();
				model.addAttribute("listNodes", mapper.writeValueAsString(listNodes));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list nodes ==> "+ mapper.writeValueAsString(listNodes));
				//System.out.println("arrayParam nodes ==> "+mapper.writeValueAsString(arrayParam));
			
			}catch (Exception e) {
				logger.info("Error in retreiving nodes Data from database", e);
				model.addAttribute("listNodes", "null");
			}
						
			try {
				strNodesType = AppendQuery("a",arrayParam, strNodesType);			
				strNodesType = strNodesType + ") as countNodes FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' ";
				strNodesType=  AppendQuery("b",arrayParam, strNodesType);
				
				//System.out.println("final query nodes type "+mapper.writeValueAsString(strNodesType));
				Query query = session.createSQLQuery(strNodesType);
				listNodesType = query.list();
				model.addAttribute("listNodesType", mapper.writeValueAsString(listNodesType));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list nodes type ==> "+ mapper.writeValueAsString(listNodesType));
				//System.out.println("arrayParam nodes type ==> "+mapper.writeValueAsString(arrayParam));		
				
			} catch (Exception e) {
				logger.info("Error in retreiving sites Data from database", e);
				model.addAttribute("listNodesType", "null");	
			}
		
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
				}
			}
		}
		return "Network/Network_NdTypNdCell";
	}
}

@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_Node", method = RequestMethod.GET)
public String Network_Node(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			//System.out.println("NODE SERVER");

			String enterprise = request.getParameter("enterprise");
			String transmission = request.getParameter("transmission");
			String access = request.getParameter("access");
			String core = request.getParameter("core");

			int[] arrayParam = new int[4]; 
	        arrayParam[0] = 0; // enterprise
	        arrayParam[1] = 0; // transmission
	        arrayParam[2] = 0; // access
	        arrayParam[3] = 0; // core
	        
	        List<String> listNodes = new ArrayList<String>();
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
					
				//System.out.println("final query nodes"+mapper.writeValueAsString(strNodes));
				Query query = session.createSQLQuery(strNodes);
				listNodes = query.list();
				model.addAttribute("listNodes", mapper.writeValueAsString(listNodes));
				model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				//System.out.println("list nodes ==> "+ mapper.writeValueAsString(listNodes));
				//System.out.println("arrayParam nodes ==> "+mapper.writeValueAsString(arrayParam));
				
				} catch (Exception e) {
					logger.info("Error in retreiving Sites Data from database", e);
					model.addAttribute("listNodes", "null");
				}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
				}
			}
		}
		return "Network/Network_Node";
	}
}



@SuppressWarnings("unchecked")
@RequestMapping(value = "/Network_Cell", method = RequestMethod.GET)
public String Network_Cell(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
//		throws JsonProcessingException {
	// logger.info("Welcome home! The client locale is {}.", locale);
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	} else {
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			//System.out.println("CELL SERVER");
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

					//System.out.println("final query cells 1"+mapper.writeValueAsString(strCells1));
					//System.out.println("final query cells 2"+mapper.writeValueAsString(strCells2));
					//System.out.println("final query cells 3"+mapper.writeValueAsString(strCells3));
					
					Query query1 = session.createSQLQuery(strCells1);
					Query query2 = session.createSQLQuery(strCells2);
					Query query3 = session.createSQLQuery(strCells3);
					cellResult.addAll(query1.list());
					cellResult.addAll(query2.list());
					cellResult.addAll(query3.list());
					
					model.addAttribute("listCells", mapper.writeValueAsString(cellResult));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					//System.out.println("list cells ==> "+ mapper.writeValueAsString(cellResult));
					//System.out.println("arrayParam cells ==> "+mapper.writeValueAsString(arrayParam));
					
				} catch (Exception e) {
				logger.info("Error in retreiving cells array Data from database", e);
				model.addAttribute("listCells", "null");
			}
			finally {
				if (session != null && session.isOpen()) {
					logger.info("Session Closseeed");
					tx.commit();
					session.close();
				}
			}
		}
		return "Network/Network_Cell";
	}
}
	/*
	// retrieve sites/nodes/cells data when supplier is clicked in
	// Supplier-site-node-cell method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "resources/js/Network/NetworkTree.js/FindOnClick_SuppSiteNodeCell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> Find_SuppSiteNodeCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedNode = request.getParameter("selectedNode");
			try {
				if (selectedNode != null) {
					List<Object[]> result = session.createSQLQuery(
							"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
									+ selectedNode + "'")
							.list();
					List<Object[]> result1 = session.createSQLQuery(
							"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
									+ selectedNode + "'")
							.list();
					List<Object[]> result2 = session.createSQLQuery(
							"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
									+ selectedNode + "'")
							.list();

					List<Object[]> cellResult = new ArrayList<Object[]>();

					if (!result.isEmpty()) {

						cellResult.addAll(result);

					}
					if (!result1.isEmpty()) {
						cellResult.addAll(result1);

					}
					if (!result2.isEmpty()) {

						cellResult.addAll(result2);

					}
					rtn.put("listCells", cellResult);
				}
			} catch (Exception e) {
				logger.info("Error in retreiving cells array Data from database", e);
				rtn.put("listCells", null);
			}
			String selectedSupp = request.getParameter("selectedSupp");
			try {
				if (selectedSupp != null) {
					List<?> site_SuppList = session.createSQLQuery(
							"SELECT distinct a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,b.SUPPLIER_ID,"
									+ "(select COUNT(*) from NODE_ACTIVE b where a.WARE_ID=b.WARE_ID) as countnodes,"
									+ "(select COUNT(*) FROM NODE_GCELL c where b.NODE_PK=c.NODE_PK and a.WARE_ID=b.WARE_ID and b.ACTIVE_RECORD='1') as countGcells,"
									+ "(select COUNT(*) FROM NODE_LCELL c where b.NODE_PK=c.NODE_PK and a.WARE_ID=b.WARE_ID and b.ACTIVE_RECORD='1') as countLcells,"
									+ "(select COUNT(*) FROM NODE_UCELL c where b.NODE_PK=c.NODE_PK and a.WARE_ID=b.WARE_ID and b.ACTIVE_RECORD='1') as countUcells"
									+ " FROM WAREHOUSE a,NODE_ACTIVE b where a.WARE_ID=b.WARE_ID and b.WARE_ID!='0' and b.WARE_ID!='null' and b.SUPPLIER_ID='"
									+ selectedSupp + "'")
							.list();

					rtn.put("listSuppSites", site_SuppList);
				}
			} catch (Exception e) {
				logger.info("Error in retreiving sites array Data from database", e);
				rtn.put("listSuppSites", null);
			}
			String selectedItem = request.getParameter("selectedItem");
			try {
				if (selectedItem != null) {
					List<Object[]> nodeSuppList = (List<Object[]>) session.createSQLQuery(
							"SELECT NODE_PK,NODE_TYPE,NODE_NAME,WARE_ID,SUPPLIER_ID,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1') as countGCells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1') as countLCells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1') as countUCells FROM NODE_ACTIVE a WHERE ACTIVE_RECORD = '1' and WARE_ID='"
									+ selectedItem + "'")
							.list();

					rtn.put("listSuppNodes", nodeSuppList);
				}
			} catch (Exception e) {
				logger.info("Error in retreiving nodes array Data from database", e);
				rtn.put("listSuppNodes", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}
*/

//retrieve sites/nodes/cells data when supplier is clicked in
	// Supplier-site-node-cell method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FindOnClick_VenSiteNodeCell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> Find_VenSiteNodeCell(Locale locale, Model model, HttpServletRequest request,
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
			String selectedVen = request.getParameter("selectedVen");
			String selectedItem = request.getParameter("selectedItem");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramAccess = request.getParameter("paramAccess");
			String paramCore = request.getParameter("paramCore");
				
			List<String> site_VenList = new ArrayList<String>();
			String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.VENDOR FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR='"+ selectedVen + "' ";
			
			List<String> nodeVenList = new ArrayList<String>();
			String strNodes ="SELECT NODE_PK,NODE_TYPE,NODE_NAME,WARE_ID,VENDOR,"
					+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";
			
			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' "; 
			String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' "; 
			String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.VENDOR='"+ selectedVen + "' ";		

			 try {
				if (selectedVen != null) {
					strSites= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
					
					//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
					Query query = session.createSQLQuery(strSites);
					site_VenList = query.list();
					rtn.put("listVenSites",site_VenList);
					//System.out.println("list sites ==> "+ mapper.writeValueAsString(site_VenList));
				}
			} catch (Exception e) {
				logger.info("Error in retreiving sites array Data from database", e);
				rtn.put("listVenSites", null);
			}
				try {
					if (selectedItem != null) {
						strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
						strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
						strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
					
						//System.out.println("final query cells 1 "+mapper.writeValueAsString(strCells1));
						//System.out.println("final query cells 2 "+mapper.writeValueAsString(strCells2));
						//System.out.println("final query cells 3 "+mapper.writeValueAsString(strCells3));
						
						Query query1 = session.createSQLQuery(strCells1);
						Query query2 = session.createSQLQuery(strCells2);
						Query query3 = session.createSQLQuery(strCells3);
						cellResult.addAll(query1.list());
						cellResult.addAll(query2.list());
						cellResult.addAll(query3.list());
						
						rtn.put("listCells",cellResult);
						//System.out.println("list cells ==> "+ mapper.writeValueAsString(cellResult));
					}
				} catch (Exception e) {
					logger.info("Error in retreiving cells array Data from database", e);
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
						
						//System.out.println("final query nodes"+mapper.writeValueAsString(strNodes));
						Query query = session.createSQLQuery(strNodes);
						nodeVenList = query.list();
						rtn.put("listVenNodes", nodeVenList);
						//System.out.println("list nodes ==> "+ mapper.writeValueAsString(nodeVenList));
					}
				} catch (Exception e) {
					logger.info("Error in retreiving nodes array Data from database", e);
					rtn.put("listVenNodes", null);
				}
				
	
		 finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
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
		Session session = null;
		Transaction tx = null;
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
				
			List<String> site_SuppList = new ArrayList<String>();
			String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.SUPPLIER_ID FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.SUPPLIER_ID='"+ selectedSupp + "' ";
			
			List<String> nodeSuppList = new ArrayList<String>();
			String strNodes ="SELECT NODE_PK,NODE_TYPE,NODE_NAME,WARE_ID,SUPPLIER_ID,"
					+"(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and b.ACTIVE_RECORD = '1' ";
			
			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 ="SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' "; 
			String strCells2 ="SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' "; 
			String strCells3 ="SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID= '"+selectedItem+"' and b.SUPPLIER_ID='"+ selectedSupp + "' ";		

			 try {
				if (selectedSupp != null) {
					strSites= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
					
					//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
					Query query = session.createSQLQuery(strSites);
					site_SuppList = query.list();
					rtn.put("listSuppSites",site_SuppList);
					//System.out.println("list sites ==> "+ mapper.writeValueAsString(site_SuppList));
				}
			} catch (Exception e) {
				logger.info("Error in retreiving sites array Data from database", e);
				rtn.put("listSuppSites", null);
			}
				try {
					if (selectedItem != null) {
						strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
						strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
						strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
					
						//System.out.println("final query cells 1 "+mapper.writeValueAsString(strCells1));
						//System.out.println("final query cells 2 "+mapper.writeValueAsString(strCells2));
						//System.out.println("final query cells 3 "+mapper.writeValueAsString(strCells3));
						
						Query query1 = session.createSQLQuery(strCells1);
						Query query2 = session.createSQLQuery(strCells2);
						Query query3 = session.createSQLQuery(strCells3);
						cellResult.addAll(query1.list());
						cellResult.addAll(query2.list());
						cellResult.addAll(query3.list());
						
						rtn.put("listCells",cellResult);
						//System.out.println("list cells ==> "+ mapper.writeValueAsString(cellResult));
					}
				} catch (Exception e) {
					logger.info("Error in retreiving cells array Data from database", e);
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
						
						//System.out.println("final query nodes"+mapper.writeValueAsString(strNodes));
						Query query = session.createSQLQuery(strNodes);
						nodeSuppList = query.list();
						rtn.put("listSuppNodes", nodeSuppList);
						//System.out.println("list nodes ==> "+ mapper.writeValueAsString(nodeSuppList));
					}
				} catch (Exception e) {
					logger.info("Error in retreiving nodes array Data from database", e);
					rtn.put("listSuppNodes", null);
				}
				
	
		 finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	// retreive site, supplier, node, and cells data in Site-Supplier-node-cell
	// method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/StSupNdCell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> StSupNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				List<Object[]> result = session.createSQLQuery(
						"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
						.list();
				List<Object[]> result1 = session.createSQLQuery(
						"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
						.list();
				List<Object[]> result2 = session.createSQLQuery(
						"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
						.list();

				List<Object[]> cellResult = new ArrayList<Object[]>();

				cellResult.addAll(result);

				cellResult.addAll(result1);

				cellResult.addAll(result2);

				rtn.put("listCells", cellResult);
			} catch (Exception e) {
				logger.info("Error in retreiving cells array Data from database", e);
				rtn.put("listCells", null);
			}
			try {
				List<Object[]> site_SuppList = (List<Object[]>) session.createSQLQuery(

						"SELECT distinct b.SITE_ID,b.WARE_NAME,b.WARE_ID,"
								+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
								+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
								+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID  and w.ACTIVE_RECORD = '1') as countnodes,"
								+ "(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countGcells,"
								+ "(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countLcells,"
								+ "(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countUcells"
								+ " FROM WAREHOUSE b where b.WARE_ID!='0' and b.WARE_ID!='null'")
						.list();

				rtn.put("listSuppSites", site_SuppList);

			} catch (Exception e) {
				logger.info("Error in retreiving sites array Data from database", e);
				rtn.put("listSuppSites", null);
			}

			try {
				List<Object[]> nodeList = (List<Object[]>) session.createSQLQuery(
						"SELECT NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1') as countGCells,"
								+ "(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1') as countLCells,"
								+ "(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1') as countUCells,SUPPLIER_ID FROM NODE_ACTIVE a WHERE ACTIVE_RECORD = '1'")
						.list();

				rtn.put("listNodes", nodeList);
			} catch (Exception e) {
				logger.info("Error in retreiving nodes array Data from database", e);
			}
			try {
				List<?> SiteSupplist = session.createSQLQuery(
						"SELECT DISTINCT a.WARE_ID,a.SUPPLIER_ID,(select b.SUPPLIER_NAME from SUPPLIER b where a.SUPPLIER_ID=b.SUPPLIER_ID) FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.WARE_ID!='0'")
						.list();

				rtn.put("SiteSuppList", SiteSupplist);
			} catch (Exception e) {
				logger.info("Error in retreiving supplier array Data from database", e);
				rtn.put("SiteSuppList", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	// retrieve node type/nodes/cells when site is clicked in Site-node
		// type-node-cells method
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "resources/js/Network/NetworkTree.js/findSiteNdTypeNodes_Cells", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> findSiteNdTyNodesCells(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			// logger.info("Welcome home! The client locale is {}.", locale);

			Map<String, Object> rtn = new LinkedHashMap<>();
			// ObjectMapper mapper = new ObjectMapper();
			Session session = null;
			Transaction tx = null;
			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				String selectedItem = request.getParameter("selectedItem");

				try {

					List<?> nodeTypeList = session.createSQLQuery(
							"SELECT DISTINCT NODE_TYPE,WARE_ID FROM NODE_ACTIVE WHERE ACTIVE_RECORD = '1' and WARE_ID='"
									+ selectedItem + "'")
							.list();

					rtn.put("listNodesType", nodeTypeList);
				} catch (Exception e) {
					logger.info("Error in retreiving node type array Data from database", e);
					rtn.put("listNodesType", null);
				}
				try {
					String selectedNodetType = request.getParameter("selectedNodetType");
					if (selectedNodetType != null) {

						List<Object[]> nodeList = (List<Object[]>) session.createSQLQuery(
								"SELECT NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1') as countGCells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1') as countLCells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1') as countUCells FROM NODE_ACTIVE a WHERE ACTIVE_RECORD = '1' and WARE_ID='"
										+ selectedItem + "' and NODE_TYPE='" + selectedNodetType + "'")
								.list();

						rtn.put("listNodes", nodeList);
					}
				} catch (Exception e) {
					logger.info("Error in retreiving nodes array Data from database", e);
					rtn.put("listNodes", null);
				}
				try {
					String selectedNode = request.getParameter("selectedNode");

					List<Object[]> result = session.createSQLQuery(
							"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.NODE_PK='"
									+ selectedNode + "'")
							.list();
					List<Object[]> result1 = session.createSQLQuery(
							"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.NODE_PK='"
									+ selectedNode + "'")
							.list();
					List<Object[]> result2 = session.createSQLQuery(
							"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.NODE_PK='"
									+ selectedNode + "'")
							.list();

					List<Object[]> cellResult = new ArrayList<Object[]>();

					cellResult.addAll(result);

					cellResult.addAll(result1);

					cellResult.addAll(result2);

					rtn.put("listCells", cellResult);
				} catch (Exception e) {
					logger.info("Error in retreiving cells array Data from database", e);
					rtn.put("listCells", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return rtn;
		}

		// retrieve nodes and cells data when a site is clicked in site-node-cells
		// method

		@SuppressWarnings("unchecked")
		@RequestMapping(value = "resources/js/Network/NetworkTree.js/findSiteNodes_Cells", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> findSiteNodes(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			// logger.info("Welcome home! The client locale is {}.", locale);

			Map<String, Object> rtn = new LinkedHashMap<>();
			// ObjectMapper mapper = new ObjectMapper();
			Session session = null;
			Transaction tx = null;
			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					String selectedItem = request.getParameter("selectedItem");
					if (selectedItem != null) {
						List<Object[]> nodeList = (List<Object[]>) session.createSQLQuery(
								"SELECT Distinct NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,(select count(*) from NODE_GCELL b where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD ='1') as countGCells,(select count(*) from NODE_LCELL c where a.NODE_PK= c.NODE_PK and ACTIVE_RECORD ='1') as countLCells,(select count(*) from NODE_UCELL d where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD ='1') as countUCells FROM NODE_ACTIVE a WHERE ACTIVE_RECORD = '1' and WARE_ID='"
										+ selectedItem + "'")
								.list();

						rtn.put("listNodes", nodeList);
					}

				} catch (Exception e) {
					logger.info("Error in retreiving nodes Data from database", e);
					rtn.put("listNodes", null);
				}
				try {
					String selectedNode = request.getParameter("selectedNode");
					if (selectedNode != null) {

						List<Object[]> result = session.createSQLQuery(
								"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.NODE_PK='"
										+ selectedNode + "'")
								.list();
						List<Object[]> result1 = session.createSQLQuery(
								"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.NODE_PK='"
										+ selectedNode + "'")
								.list();
						List<Object[]> result2 = session.createSQLQuery(
								"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.NODE_PK='"
										+ selectedNode + "'")
								.list();

						List<Object[]> cellResult = new ArrayList<Object[]>();

						cellResult.addAll(result);

						cellResult.addAll(result1);

						cellResult.addAll(result2);

						rtn.put("listCells", cellResult);
						//System.out.println("list cells ========> "+ mapper.writeValueAsString(cellResult));
					}
				} catch (Exception e) {
					logger.info("Error in retreiving cells Data from database", e);
					rtn.put("listCells", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return rtn;
		}

	// Retrieve site/supplier/nodetype/node/cell data in Site-Supplier-Node Type-
	// Node - Cell method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/StSupNdTyNdCell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> StSupNdTyNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				List<Object[]> result = session.createSQLQuery(
						"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
						.list();
				List<Object[]> result1 = session.createSQLQuery(
						"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
						.list();
				List<Object[]> result2 = session.createSQLQuery(
						"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
						.list();

				List<Object[]> cellResult = new ArrayList<Object[]>();

				if (!result.isEmpty()) {

					cellResult.addAll(result);

				}
				if (!result1.isEmpty()) {
					cellResult.addAll(result1);

				}
				if (!result2.isEmpty()) {

					cellResult.addAll(result2);

				}

				rtn.put("listCells", cellResult);
			} catch (Exception e) {
				logger.info("Error in retreiving cells Data from database", e);
				rtn.put("listCells", null);
			}
			try {
				List<Object[]> site_SuppList = (List<Object[]>) session.createSQLQuery(

						"SELECT distinct b.SITE_ID,b.WARE_NAME,b.WARE_ID,"
								+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
								+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
								+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID  and w.ACTIVE_RECORD = '1') as countnodes,"
								+ "(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countGcells,"
								+ "(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countLcells,"
								+ "(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countUcells"
								+ " FROM WAREHOUSE b where b.WARE_ID!='0' and b.WARE_ID!='null'")
						.list();

				rtn.put("listSuppSites", site_SuppList);
			} catch (Exception e) {
				logger.info("Error in retreiving sites Data from database", e);
				rtn.put("listSuppSites", null);
			}

			try {
				List<Object[]> nodeList = (List<Object[]>) session.createSQLQuery(
						"SELECT NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1') as countGCells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1') as countLCells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1') as countUCells,SUPPLIER_ID FROM NODE_ACTIVE a WHERE ACTIVE_RECORD = '1'")
						.list();

				rtn.put("listNodes", nodeList);
			} catch (Exception e) {
				logger.info("Error in retreiving Nodes Data from database", e);
				rtn.put("listNodes", null);

			}

			try {
				List<?> SiteSupplist = session.createSQLQuery(
						"SELECT DISTINCT a.WARE_ID,a.SUPPLIER_ID,(select b.SUPPLIER_NAME from SUPPLIER b where a.SUPPLIER_ID=b.SUPPLIER_ID) FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.WARE_ID!='0'")
						.list();

				rtn.put("SiteSuppList", SiteSupplist);
			} catch (Exception e) {
				logger.info("Error in retreiving Supplier Data from database", e);
				rtn.put("SiteSuppList", null);

			}

			try {
				List<?> nodeTypeStSuppList = session.createSQLQuery(
						"SELECT DISTINCT NODE_TYPE,WARE_ID,SUPPLIER_ID FROM NODE_ACTIVE WHERE ACTIVE_RECORD = '1' and WARE_ID!='0'")
						.list();

				rtn.put("nodeTypeStSuppResult", nodeTypeStSuppList);
			} catch (Exception e) {
				logger.info("Error in retreiving nodes Data from database", e);
				rtn.put("nodeTypeStSuppResult", null);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	// Retrieve site/nodetype/supplier/node/cell data in Site-Node
	// Type-Supplier-Node - Cell method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SiteNdTySuppNodeCell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SiteNdTySuppNodeCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				List<Object[]> result = session.createSQLQuery(
						"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
						.list();
				List<Object[]> result1 = session.createSQLQuery(
						"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
						.list();
				List<Object[]> result2 = session.createSQLQuery(
						"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
						.list();

				List<Object[]> cellResult = new ArrayList<Object[]>();

				if (!result.isEmpty()) {

					cellResult.addAll(result);

				}
				if (!result1.isEmpty()) {
					cellResult.addAll(result1);

				}
				if (!result2.isEmpty()) {

					cellResult.addAll(result2);

				}

				rtn.put("listCells", cellResult);
			} catch (Exception e) {
				logger.info("Error in retreiving cells Data from database", e);
				rtn.put("listCells", null);
			}
			try {
				rtn.put("listNodes", ((List<Object[]>) session.createSQLQuery(
						"SELECT distinct NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1') as countGCells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1') as countLCells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1') as countUCells,SUPPLIER_ID FROM NODE_ACTIVE a WHERE ACTIVE_RECORD = '1'")
						.list()));

			} catch (Exception e) {
				logger.info("Error in retreiving nodes Data from database", e);
				rtn.put("listNodes", null);
			}

			try {
				List<?> sup_ListSupp = session.createSQLQuery(
						"SELECT distinct a.SUPPLIER_ID,a.SUPPLIER_NAME,b.NODE_TYPE,b.WARE_ID FROM SUPPLIER a,NODE_ACTIVE b WHERE a.SUPPLIER_ID=b.SUPPLIER_ID and b.ACTIVE_RECORD = '1' and b.WARE_ID!='null'")
						.list();

				rtn.put("sup_ListSupp", sup_ListSupp);
			} catch (Exception e) {
				logger.info("Error in retreiving supplier Data from database", e);
				rtn.put("sup_ListSupp", null);
			}

			try {
				List<?> sup_NodeTypeList = session.createSQLQuery(
						"SELECT DISTINCT NODE_TYPE,WARE_ID FROM NODE_ACTIVE WHERE ACTIVE_RECORD = '1' and WARE_ID!='null' and WARE_ID!='0' and SUPPLIER_ID!='null' and SUPPLIER_ID!='0' ")
						.list();

				rtn.put("sup_NodeTypeResult", sup_NodeTypeList);
			} catch (Exception e) {
				logger.info("Error in retreiving Node type Data from database", e);
				rtn.put("sup_NodeTypeResult", null);
			}

			try {
				List<Object[]> siteList_Sup = (List<Object[]>) session.createSQLQuery(

						"SELECT distinct b.SITE_ID,b.WARE_NAME,b.WARE_ID,"
								+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
								+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
								+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID  and w.ACTIVE_RECORD = '1') as countnodes,"
								+ "(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countGcells,"
								+ "(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countLcells,"
								+ "(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countUcells"
								+ " FROM WAREHOUSE b where b.WARE_ID!='0' and b.WARE_ID!='null'")
						.list();

				rtn.put("suplistSites", siteList_Sup);
			} catch (Exception e) {
				logger.info("Error in retreiving Sites Data from database", e);
				rtn.put("suplistSites", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	// Retrieve supplier/site/Node type/node/cell data in Supplier-Site-Node
	// type-Node - Cell method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Suppliers", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> Suppliers(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			/*
			 * try {
			 * 
			 * List<Object[]> result = session.createSQLQuery(
			 * "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'"
			 * ) .list(); List<Object[]> result1 = session.createSQLQuery(
			 * "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'"
			 * ) .list(); List<Object[]> result2 = session.createSQLQuery(
			 * "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'"
			 * ) .list();
			 * 
			 * List<Object[]> cellResult = new ArrayList<Object[]>();
			 * 
			 * if (!result.isEmpty()) {
			 * 
			 * cellResult.addAll(result);
			 * 
			 * } if (!result1.isEmpty()) { cellResult.addAll(result1);
			 * 
			 * } if (!result2.isEmpty()) {
			 * 
			 * cellResult.addAll(result2);
			 * 
			 * }
			 * 
			 * rtn.put("listCells", cellResult); } catch (Exception e) {
			 * logger.info("Error in retreiving cells Data from database",e);
			 * rtn.put("listCells", null); }
			 */
			try {
				List<?> suppList = session.createSQLQuery(
						"SELECT distinct SUPPLIER_ID,SUPPLIER_NAME FROM NODE_ACTIVE where ACTIVE_RECORD = '1' and WARE_ID!='0' and WARE_ID!='null'")
						.list();

				rtn.put("listSupp", suppList);
			} catch (Exception e) {
				logger.info("Error in retreiving supplier Data from database", e);
				rtn.put("listSupp", null);
			}
			/*
			 * try { List<?> site_SuppList = session
			 * .createSQLQuery("SELECT a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,b.SUPPLIER_ID,"
			 * +
			 * "(select COUNT(*) from NODE_ACTIVE b where a.WARE_ID=b.WARE_ID and b.ACTIVE_RECORD='1') as countnodes,"
			 * +
			 * "(select COUNT(*) FROM NODE_GCELL c where b.NODE_PK=c.NODE_PK and a.WARE_ID=b.WARE_ID and b.ACTIVE_RECORD='1') as countGcells,"
			 * +
			 * "(select COUNT(*) FROM NODE_LCELL c where b.NODE_PK=c.NODE_PK and a.WARE_ID=b.WARE_ID and b.ACTIVE_RECORD='1') as countLcells,"
			 * +
			 * "(select COUNT(*) FROM NODE_UCELL c where b.NODE_PK=c.NODE_PK and a.WARE_ID=b.WARE_ID and b.ACTIVE_RECORD='1') as countUcells"
			 * +
			 * " FROM WAREHOUSE a,NODE_ACTIVE b where a.WARE_ID=b.WARE_ID and b.WARE_ID!='0' and b.WARE_ID!='null'"
			 * ) .list();
			 * 
			 * rtn.put("listSuppSites", site_SuppList); } catch (Exception e) {
			 * logger.info("Error in retreiving sites Data from database",e);
			 * rtn.put("listSuppSites", null);
			 * 
			 * } try { List<?> nodeTypeSuppList = session.createSQLQuery(
			 * "SELECT DISTINCT NODE_TYPE,WARE_ID,SUPPLIER_ID FROM NODE_ACTIVE WHERE ACTIVE_RECORD = '1' and WARE_ID!='0'"
			 * ) .list();
			 * 
			 * rtn.put("nodeTypeSuppResult", nodeTypeSuppList); } catch (Exception e) {
			 * logger.info("Error in retreiving node type Data from database",e);
			 * rtn.put("nodeTypeSuppResult", null); } try { List<Object[]> nodeSuppList =
			 * (List<Object[]>) session.createSQLQuery(
			 * "SELECT NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1') as countGCells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1') as countLCells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1') as countUCells,SUPPLIER_ID FROM NODE_ACTIVE a WHERE ACTIVE_RECORD = '1'"
			 * ) .list();
			 * 
			 * rtn.put("listSuppNodes", nodeSuppList); } catch (Exception e) {
			 * logger.info("Error in retreiving nodes Data from database",e);
			 * rtn.put("listSuppNodes", null); } finally { if (session != null &&
			 * session.isOpen()) { tx.commit(); session.close(); } }
			 */
		}
		return rtn;
	}

	// Retrieve site node type node cells data in site-node type-node-cells method
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/SiteNdTypNdCell", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> SiteNdTypNdCell(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			// logger.info("Welcome home! The client locale is {}.", locale);

			Map<String, Object> rtn = new LinkedHashMap<>();
			// ObjectMapper mapper = new ObjectMapper();
			Session session = null;
			Transaction tx = null;
			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					List<Object[]> result = session
							.createSQLQuery("SELECT GCELL_ID,CELLNAME,NODE_PK FROM NODE_GCELL a WHERE ACTIVE_RECORD = '1'")
							.list();
					List<Object[]> result1 = session
							.createSQLQuery("SELECT LCELL_ID,CELLNAME,NODE_PK FROM NODE_LCELL a WHERE ACTIVE_RECORD = '1'")
							.list();
					List<Object[]> result2 = session
							.createSQLQuery("SELECT UCELL_ID,CELLNAME,NODE_PK FROM NODE_UCELL a WHERE ACTIVE_RECORD = '1'")
							.list();

					List<Object[]> cellResult = new ArrayList<Object[]>();

					cellResult.addAll(result);

					cellResult.addAll(result1);

					cellResult.addAll(result2);

					rtn.put("listCells", cellResult);
				} catch (Exception e) {
					logger.info("Error in retreiving Cells Data from database", e);
					rtn.put("listCells", null);
				}
				try {
					List<?> nodeTypeList = session
							.createSQLQuery("SELECT DISTINCT NODE_TYPE,WARE_ID FROM NODE_ACTIVE WHERE ACTIVE_RECORD = '1'")
							.list();
					rtn.put("listNodesType", nodeTypeList);
				} catch (Exception e) {
					logger.info("Error in retreiving Node Type Data from database", e);
					rtn.put("listNodesType", null);
				}
				try {
					List<Object[]> nodeList = (List<Object[]>) session.createSQLQuery(
							"SELECT NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1') as countGCells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1') as countLCells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1') as countUCells FROM NODE_ACTIVE a WHERE ACTIVE_RECORD = '1'")
							.list();

					rtn.put("listNodes", nodeList);
				} catch (Exception e) {
					logger.info("Error in retreiving Nodes Data from database", e);
					rtn.put("listNodes", null);
				}

				try {
					List<Object[]> siteList = (List<Object[]>) session.createSQLQuery(

							"SELECT distinct b.SITE_ID,b.WARE_NAME,b.WARE_ID,"
									+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
									+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
									+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID  and w.ACTIVE_RECORD = '1') as countnodes,"
									+ "(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countGcells,"
									+ "(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countLcells,"
									+ "(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countUcells"
									+ " FROM WAREHOUSE b where b.WARE_ID!='0' and b.WARE_ID!='null'")
							.list();

					rtn.put("listSites", siteList);
				} catch (Exception e) {
					logger.info("Error in retreiving sites Data from database", e);
					rtn.put("listSites", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return rtn;
		}

	// Area controller
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/area", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> area(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				List<?> areaList = session.createSQLQuery(
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
				logger.info("Error in retreiving arealist data Data from database", e);
				rtn.put("areaList", null);
			}
			try {
				List<?> site_AreaList = session.createSQLQuery(
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
				logger.info("Error in retreiving area sites data Data from database", e);
				rtn.put("listAreaSites", null);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}
	
private static String boqDomain (String paramEnterprise,String paramTransmission,String paramAccess,String paramCore, String str) {
//	System.out.println("boqDomain");
	if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
        String[] words = str.split(" ");    
        // Check if the last word is "WHERE" (case-insensitive)
        if (words.length > 0 && "WHERE".equalsIgnoreCase(words[words.length - 1])) {
            //System.out.println("The last word is 'WHERE'");
        } else {
            //System.out.println("The last word is not 'WHERE'");
            str = str +" and ( ";
        }
		
		if (paramEnterprise.equals("true")) {
			//System.out.println("ent ");
			str= str +"DOMAIN ='Enterprise' ";
			//System.out.println("str >>>> "+str);
		}
		if (paramTransmission.equals("true")) {
			if(paramEnterprise.equals("false")) {
				//System.out.println("trans");
				str= str +"DOMAIN ='Transmission' ";
			}else {
				str= str + " or DOMAIN ='Transmission' ";
			}
		}
		if (paramAccess.equals("true")) {
			if(paramEnterprise.equals("false") && paramTransmission.equals("false")) {
				//System.out.println("acc ");
				str= str+ "DOMAIN ='Access' ";
			}else {
				str= str+ " or DOMAIN ='Access' ";
			}
		}
		if (paramCore.equals("true")) {
			if(paramEnterprise.equals("false") && paramTransmission.equals("false") && paramAccess.equals("false")) {
				//System.out.println("core");
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
	//System.out.println("boqDomainVar");
	if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
        String[] words = str.split(" ");    
        // Check if the last word is "WHERE" (case-insensitive)
        if (words.length > 0 && "WHERE".equalsIgnoreCase(words[words.length - 1])) {
            //System.out.println("The last word is 'WHERE'");
        } else {
            //System.out.println("The last word is not 'WHERE'");
            str = str +" and ( ";
        }
			
		if (paramEnterprise.equals("true")) {
			//System.out.println("ent ");
			str= str +a+ ".DOMAIN ='Enterprise' ";
			//System.out.println("str >> "+str);
		}
		if (paramTransmission.equals("true")) {
			if(paramEnterprise.equals("false")) {
				//System.out.println("trans");
				str= str +a+ ".DOMAIN ='Transmission' ";
			}else {
				str= str + " or " +a+ ".DOMAIN ='Transmission' ";
			}
		}
		if (paramAccess.equals("true")) {
			if(paramEnterprise.equals("false") && paramTransmission.equals("false")) {
				//System.out.println("acc ");
				str= str+ a+ ".DOMAIN ='Access' ";
			}else {
				str= str+ " or " +a+ ".DOMAIN ='Access' ";
			}
		}
		if (paramCore.equals("true")) {
			if(paramEnterprise.equals("false") && paramTransmission.equals("false") && paramAccess.equals("false")) {
				//System.out.println("core");
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
	//System.out.println("str >> "+str);
	return str;
}

	// Sites BOQ data retrieving
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/GetBoqList", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody

		public LinkedHashMap<String, String> GetBoqList(@RequestParam String SiteId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();

			// if Site_id !=null --> an ajax request received
			LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		
			try {
					String strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
					strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
					String Site_Query = SiteId == "" ? strEmpty : "Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' ";
					//System.out.println(Site_Query);
					Object Sites = session.createSQLQuery(Site_Query).uniqueResult();
					strEmpty="";					
		////////////////////////////
					strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' ";
					strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
					String strExist= "SELECT COUNT(NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='" + SiteId + "' ";	
					strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
					String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
					//System.out.println(Node_Active_Query);
					Object CountNodes_Active = session.createSQLQuery(Node_Active_Query).uniqueResult();
					strEmpty="";
					strExist="";
		////////////////////////////			
					strEmpty="SELECT COUNT(DISTINCT GCELL_ID) FROM NODE_GCELL ";
					if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
						strEmpty= strEmpty + "WHERE ";
					}				
					strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
					strExist= "select count(distinct ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"+ SiteId +"' ";	
					strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
					String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
					//System.out.println(Node_GCell_Query);
					Object CountNodes_G_CELL = session.createSQLQuery(Node_GCell_Query).uniqueResult();
					strEmpty="";
					strExist="";
		/////////////////////////////
					strEmpty="SELECT COUNT(DISTINCT LCELL_ID) FROM NODE_LCELL ";
					if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
						strEmpty= strEmpty + "WHERE ";
					}
					strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
					strExist= "select count(distinct nlc.lcell_id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"+ SiteId +"' ";	
					strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
					String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
					//System.out.println(Node_LCell_Query);
					Object CountNodes_L_CELL = session.createSQLQuery(Node_LCell_Query).uniqueResult();
					strEmpty="";
					strExist="";
		/////////////////////////////
					strEmpty="SELECT COUNT(DISTINCT UCELL_ID) FROM NODE_UCELL ";
					if(paramEnterprise.equals("true") || paramTransmission.equals("true") || paramAccess.equals("true") || paramCore.equals("true")) {
						strEmpty= strEmpty + "WHERE ";
					}
					strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
					strExist= "select count(distinct nuc.ucell_id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"+ SiteId +"' ";	
					strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
					String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
					//System.out.println(Node_UCell_Query);
					Object CountNodes_U_CELL = session.createSQLQuery(Node_UCell_Query).uniqueResult();
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
						Object CountNodesType = session.createSQLQuery(Node_Type_Count).uniqueResult();
						BoqHM.put("Node Type", String.valueOf(CountNodesType));
					}else {
						strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' ";
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_Type_Count = strExist;
						//System.out.println(Node_Type_Count);
						Object CountNodesType = session.createSQLQuery(Node_Type_Count).uniqueResult();
						BoqHM.put("Node Type", String.valueOf(CountNodesType));
						strExist="";
					////////////////////////////////
						strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and Ware_Id = '"+SiteId+"' ";
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						strExist = strExist +" GROUP BY NODE_TYPE";
						//System.out.println(strExist);
						List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createSQLQuery(strExist).list();
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
				logger.info("Error in retreiving Site BOQ data Data from database", e);
				return null;
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
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

				// if Site_id !=null --> an ajax request received
				LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
			
				try {
						String strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null ";
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String Site_Query = SiteId == "" ? strEmpty : "Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' and vendor='"+VenId+"' ";
						//System.out.println(Site_Query);
						Object Sites = session.createSQLQuery(Site_Query).uniqueResult();
						strEmpty="";					
			////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null ";
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						String strExist= "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='" + SiteId + "' and vendor='"+VenId+"'  AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_Active_Query);
						Object CountNodes_Active = session.createSQLQuery(Node_Active_Query).uniqueResult();
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
						Object CountNodes_G_CELL = session.createSQLQuery(Node_GCell_Query).uniqueResult();
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
						Object CountNodes_L_CELL = session.createSQLQuery(Node_LCell_Query).uniqueResult();
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
						Object CountNodes_U_CELL = session.createSQLQuery(Node_UCell_Query).uniqueResult();
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
							Object CountNodesType = session.createSQLQuery(Node_Type_Count).uniqueResult();
							BoqHM.put("Node Type", String.valueOf(CountNodesType));
						}else {
							strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' and vendor='"+VenId+"' ";
							strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_Type_Count = strExist;
							//System.out.println(Node_Type_Count);
							Object CountNodesType = session.createSQLQuery(Node_Type_Count).uniqueResult();
							BoqHM.put("Node Type", String.valueOf(CountNodesType));
							strExist="";
						////////////////////////////////
							strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and Ware_Id = '"+SiteId+"' and vendor='"+VenId+"' ";
							strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							strExist = strExist +" GROUP BY NODE_TYPE";
							//System.out.println(strExist);
							List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createSQLQuery(strExist).list();
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
					logger.info("Error in retreiving Site BOQ data Data from database", e);
					return null;
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
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
						Object Sites = session.createSQLQuery(Site_Query).uniqueResult();
						strEmpty="";					
			////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null ";
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						String strExist= "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='" + SiteId + "' and SUPPLIER_ID='"+SuppId+"' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_Active_Query);
						Object CountNodes_Active = session.createSQLQuery(Node_Active_Query).uniqueResult();
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
						Object CountNodes_G_CELL = session.createSQLQuery(Node_GCell_Query).uniqueResult();
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
						Object CountNodes_L_CELL = session.createSQLQuery(Node_LCell_Query).uniqueResult();
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
						Object CountNodes_U_CELL = session.createSQLQuery(Node_UCell_Query).uniqueResult();
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
							Object CountNodesType = session.createSQLQuery(Node_Type_Count).uniqueResult();
							BoqHM.put("Node Type", String.valueOf(CountNodesType));
						}else {
							strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' and SUPPLIER_ID='"+SuppId+"' ";
							strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							String Node_Type_Count = strExist;
							//System.out.println(Node_Type_Count);
							Object CountNodesType = session.createSQLQuery(Node_Type_Count).uniqueResult();
							BoqHM.put("Node Type", String.valueOf(CountNodesType));
							strExist="";
						////////////////////////////////
							strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and Ware_Id = '"+SiteId+"' and SUPPLIER_ID='"+SuppId+"' ";
							strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
							strExist = strExist +" GROUP BY NODE_TYPE";
							//System.out.println(strExist);
							List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createSQLQuery(strExist).list();
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
					logger.info("Error in retreiving Site BOQ data Data from database", e);
					return null;
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
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

			// if Site_id !=null --> an ajax request received
			LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		//System.out.println("id : : : "+VenId);
			try {
					
				String strEmpty= "SELECT COUNT(DISTINCT VENDOR) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null  ";	
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				String Vendor_Query = VenId == "" ? strEmpty : "Select distinct VENDOR From NODE_ACTIVE where VENDOR='" + VenId + "' ";
				//System.out.println(Vendor_Query);
				Object Vendors = session.createSQLQuery(Vendor_Query).uniqueResult();
				strEmpty="";
		////////////////////////////
				strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null  ";	
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				String strExist ="Select distinct COUNT(DISTINCT WARE_ID) From NODE_ACTIVE where VENDOR='" + VenId + "' AND WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null ";
				strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);					
				String Site_Query = VenId == "" ? strEmpty : strExist;
				//System.out.println(Site_Query);
				Object Sites = session.createSQLQuery(Site_Query).uniqueResult();
				strEmpty="";
				strExist="";
		////////////////////////////
				strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND VENDOR!='null' AND VENDOR!='0' AND VENDOR is not null ";	
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and VENDOR='" + VenId + "' AND WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null ";	
				strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Node_Active_Query = VenId == "" ? strEmpty : strExist;
				//System.out.println(Node_Active_Query);
				Object CountNodes_Active = session.createSQLQuery(Node_Active_Query).uniqueResult();
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
				Object CountNodes_G_CELL = session.createSQLQuery(Node_GCell_Query).uniqueResult();
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
				Object CountNodes_L_CELL = session.createSQLQuery(Node_LCell_Query).uniqueResult();
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
				Object CountNodes_U_CELL = session.createSQLQuery(Node_UCell_Query).uniqueResult();
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
					Object CountNodesType = session.createSQLQuery(Node_Type_Count).uniqueResult();
					BoqHM.put("Node Type", String.valueOf(CountNodesType));
				}else {
					strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and VENDOR='"+ VenId + "' ";
					strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
					String Node_Type_Count = strExist;
					//System.out.println(Node_Type_Count);
					Object CountNodesType = session.createSQLQuery(Node_Type_Count).uniqueResult();
					BoqHM.put("Node Type", String.valueOf(CountNodesType));
					strExist="";
				////////////////////////////////
					strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and VENDOR = '"+VenId+"' ";
					strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
					strExist = strExist +" GROUP BY NODE_TYPE";
					//System.out.println(strExist);
					List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createSQLQuery(strExist).list();
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
				logger.info("Error in retreiving Site BOQ data Data from database", e);
				return null;
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
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

					// if Site_id !=null --> an ajax request received
					LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
				//System.out.println("id : : : "+SuppId);
					try {
						String strEmpty= "SELECT COUNT(DISTINCT Supplier_Id) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null  ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String Supplier_Query = SuppId == "" ? strEmpty : "Select distinct Supplier_Name From NODE_ACTIVE where Supplier_Id='" + SuppId + "' ";
						//System.out.println(Site_Query);
						Object Suppliers = session.createSQLQuery(Supplier_Query).uniqueResult();
						strEmpty="";	
				////////////////////////////
						strEmpty= "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null  ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String strExist= "Select distinct COUNT(DISTINCT WARE_ID) From NODE_ACTIVE where Supplier_Id='" + SuppId + "' AND WARE_ID!='null' AND WARE_ID!='0' AND WARE_ID is not null ";							
						String Site_Query = SuppId == "" ? strEmpty : strExist;
						//System.out.println(Site_Query);
						Object Sites = session.createSQLQuery(Site_Query).uniqueResult();
						strEmpty="";	
						strExist="";
				////////////////////////////
						strEmpty="SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
						strExist= "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Supplier_Id='" + SuppId + "' and WARE_ID!='null' and WARE_ID!='0' and WARE_ID is not null ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
						String Node_Active_Query = SuppId == "" ? strEmpty : strExist;
						//System.out.println(Node_Active_Query);
						Object CountNodes_Active = session.createSQLQuery(Node_Active_Query).uniqueResult();
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
							Object CountNodes_G_CELL = session.createSQLQuery(Node_GCell_Query).uniqueResult();
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
							Object CountNodes_L_CELL = session.createSQLQuery(Node_LCell_Query).uniqueResult();
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
							Object CountNodes_U_CELL = session.createSQLQuery(Node_UCell_Query).uniqueResult();
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
								Object CountNodesType = session.createSQLQuery(Node_Type_Count).uniqueResult();
								BoqHM.put("Node Type", String.valueOf(CountNodesType));
							}else {
								strExist="SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Supplier_Id='"+ SuppId + "' ";
								strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
								String Node_Type_Count = strExist;
								//System.out.println(Node_Type_Count);
								Object CountNodesType = session.createSQLQuery(Node_Type_Count).uniqueResult();
								BoqHM.put("Node Type", String.valueOf(CountNodesType));
								strExist="";
							////////////////////////////////
								strExist="SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and Supplier_Id = '"+SuppId+"' ";
								strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
								strExist = strExist +" GROUP BY NODE_TYPE";
								//System.out.println(strExist);
								List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createSQLQuery(strExist).list();
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
						logger.info("Error in retreiving Site BOQ data Data from database", e);
						return null;
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
						}
					}
					//return BoqHM;
				}
		
		// Sites BOQ data retrieving
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/GetBoqSitePoList", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody

		public LinkedHashMap<String, String> GetBoqSitePoList(@RequestParam String SiteId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {
			
			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

			// if Site_id !=null --> an ajax request received
			try {
				String strEmpty="SELECT COUNT(distinct a.WARE_ID) FROM AR_SITE a, ASSET_REGISTRY b WHERE a.AR_ID=b.AR_ID ";
				strEmpty= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				String strExist= "Select DISTINCT SITE_NAME From AR_SITE where  WARE_ID='"+ SiteId +"' ";	
				//strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Site_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Site_Query);
				Object Sites = session.createSQLQuery(Site_Query).uniqueResult();
				strEmpty="";
				strExist="";	
				
				strEmpty="SELECT COUNT(distinct a.PO_ID) FROM ASSET_REGISTRY a, AR_SITE b where b.AR_ID=a.AR_ID ";
				strEmpty= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "SELECT COUNT(DISTINCT a.PO_ID) FROM ASSET_REGISTRY a, AR_SITE b where b.AR_ID=a.AR_ID and b.WARE_ID='" + SiteId+ "' ";	
				strExist= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Po_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Po_Query);
				Object CountPo = session.createSQLQuery(Po_Query).uniqueResult();
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
				Object PO_Amount = session.createSQLQuery(PO_Amount_Query).uniqueResult();
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
				Object PO_NET_Amount = session.createSQLQuery(PO_NET_Amount_Query).uniqueResult();
				BoqHM.put(SiteId == "" ? "PO Net Cost" : "PO Total Net Cost", String.valueOf(PO_NET_Amount));
				strEmpty="";
				strExist="";
														
				if(SiteId!= "") {
					String Item_Query= "Select COUNT(DISTINCT a.ITEM_CODE) from ASSET_REGISTRY a, AR_SITE b where a.AR_ID = b.AR_ID and b.WARE_ID='" + SiteId + "' ";	
					Item_Query= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,Item_Query);
					//System.out.println(Item_Query);
					Object Item = session.createSQLQuery(Item_Query).uniqueResult();
					BoqHM.put("Items", String.valueOf(Item));
				}
			
				return BoqHM;
			 
			} catch (Exception e) {
				logger.info("Error in retreiving Site BOQ data Data from database", e);
				return null;
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		// Nodes BOQ data retrieving
				@SuppressWarnings("unchecked")
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
						Object Sites = session.createSQLQuery(Site_Query).uniqueResult();
						
						if (WareId.equals("null") || WareId == null || WareId.equals("0") || WareId.equals("")) {	
							//System.out.println("IF WARE ID NULL");
							String Node_GCell_Query="select count(ngc.gcell_id) from node_gcell ngc , node_active na where na.Active_record='1' and na.node_pk = ngc.node_pk and na.NODE_PK = '" + NodeId +"' and (na.Ware_Id is null or na.Ware_Id='0') ";
							Node_GCell_Query= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_GCell_Query);
							//System.out.println(Node_GCell_Query);
				 			Object CountNodes_G_CELL = session.createSQLQuery(Node_GCell_Query).uniqueResult();			
				    ///////////////////////////
							String Node_LCell_Query = "select count(nlc.LCell_Id) from node_lcell nlc , node_active na where na.Active_record='1' and na.node_pk = nlc.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
									+ " and na.NODE_PK = '" + NodeId + "' ";
							Node_LCell_Query= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_LCell_Query);
							//System.out.println(Node_LCell_Query);
							Object CountNodes_L_CELL = session.createSQLQuery(Node_LCell_Query).uniqueResult();
					///////////////////////////
							String Node_UCell_Query = "select count(nuc.UCell_Id) from node_ucell nuc , node_active na where na.Active_record='1' and na.node_pk = nuc.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
									+ " and na.NODE_PK = '" + NodeId + "' ";
							Node_UCell_Query= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_UCell_Query);
							//System.out.println(Node_UCell_Query);
							Object CountNodes_U_CELL = session.createSQLQuery(Node_UCell_Query).uniqueResult();
					///////////////////////////
							String Node_Board_Query = "select count(nlc.BOARD_ID) from NODE_BOARD nlc , node_active na where na.Active_record='1' and na.node_pk = nlc.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
									+ " and na.NODE_PK = '" + NodeId + "' ";
							Node_Board_Query= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_Board_Query);
							//System.out.println(Node_Board_Query);
							Object CountNodesBoard = session.createSQLQuery(Node_Board_Query).uniqueResult();
					///////////////////////////
							String Node_Cabinet_Query = "select count(nlc.CABINET_ID) from NODE_CABINET nlc , node_active na where na.Active_record='1' and na.node_pk = nlc.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
									+ " and na.NODE_PK = '" + NodeId + "' ";
							Node_Cabinet_Query= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_Cabinet_Query);
							//System.out.println(Node_Cabinet_Query);
							Object CountNodesCabinet = session.createSQLQuery(Node_Cabinet_Query).uniqueResult();
					///////////////////////////		
							String NodesType_Query = "Select NODE_TYPE From NODE_ACTIVE where Active_record='1' and NODE_PK = '" + NodeId + "' and (Ware_Id is null or Ware_Id='0') ";
							NodesType_Query= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,NodesType_Query);	
							//System.out.println(NodesType_Query);
							Object CountNodes_NodeType = session.createSQLQuery(NodesType_Query).uniqueResult();
							
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
							Object CountNodes_G_CELL = session.createSQLQuery(Node_GCell_Query).uniqueResult();
					///////////////////////////	
							String Node_LCell_Query = "select count(nlc.LCell_Id) from node_lcell nlc , node_active na where na.Active_record='1' and na.node_pk = nlc.node_pk and na.Ware_Id = '"
									+ WareId + "' and na.NODE_PK = '" + NodeId + "' ";
							Node_LCell_Query= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_LCell_Query);
							//System.out.println(Node_LCell_Query);
							Object CountNodes_L_CELL = session.createSQLQuery(Node_LCell_Query).uniqueResult();
					///////////////////////////	
							String Node_UCell_Query = "select count(nuc.UCell_Id) from node_ucell nuc , node_active na where na.Active_record='1' and na.node_pk = nuc.node_pk and na.Ware_Id = '"
									+ WareId + "' and na.NODE_PK = '" + NodeId + "' ";
							Node_UCell_Query= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_UCell_Query);
							//System.out.println(Node_UCell_Query);
							Object CountNodes_U_CELL = session.createSQLQuery(Node_UCell_Query).uniqueResult();
					///////////////////////////	
							String Node_Board_Query = "select count(nlc.BOARD_ID) from NODE_BOARD nlc , node_active na where na.Active_record='1' and na.node_pk = nlc.node_pk and na.Ware_Id = '"
									+ WareId + "' and na.NODE_PK = '" + NodeId + "' ";
							Node_Board_Query= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_Board_Query);
							//System.out.println(Node_Board_Query);
							Object CountNodesBoard = session.createSQLQuery(Node_Board_Query).uniqueResult();
					///////////////////////////	
							String Node_Cabinet_Query = "select count(nlc.CABINET_ID) from NODE_CABINET nlc , node_active na where na.Active_record='1' and na.node_pk = nlc.node_pk and na.Ware_Id = '"
									+ WareId + "' and na.NODE_PK = '" + NodeId + "' ";
							Node_Cabinet_Query= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,Node_Cabinet_Query);
							//System.out.println(Node_Cabinet_Query);
							Object CountNodesCabinet = session.createSQLQuery(Node_Cabinet_Query).uniqueResult();
					///////////////////////////		
							String NodesType_Query = "Select DISTINCT NODE_TYPE From NODE_ACTIVE where Active_record='1' and Ware_Id = '"+ WareId +"' and NODE_PK = '" + NodeId + "' ";
							NodesType_Query= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,NodesType_Query);
							//System.out.println(NodesType_Query);
							Object CountNodes_NodeType = session.createSQLQuery(NodesType_Query).uniqueResult();					
							
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
						logger.info("Error in retreiving Node BOQ data Data from database", e);
						return null;
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
						}
					}
				}

		// Node Type BOQ data retrieving
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/GetNtypeBoqList", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody

		public LinkedHashMap<String, String> GetNtypeBoqList(@RequestParam String SiteId, @RequestParam String NodeTId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			// if Site_id !=null --> an ajax request received
			LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

			try {
				String strEmpty= "SELECT COUNT(distinct WARE_ID) FROM NODE_ACTIVE WHERE NODE_TYPE='" + NodeTId + "' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				String strExist= "Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' and NODE_TYPE='"+ NodeTId + "' ";	
				strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
				String Site_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Site_Query);
				Object Sites = session.createSQLQuery(Site_Query).uniqueResult();
				strEmpty="";
				strExist="";
		////////////////////////////
				strEmpty= "SELECT COUNT(distinct NODE_PK) FROM NODE_ACTIVE where Active_record='1' and NODE_TYPE='"+ NodeTId + "' ";
				strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				strExist= "SELECT COUNT(distinct NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' and NODE_TYPE='" + NodeTId + "' ";	
				strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
				String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
				System.out.println(Node_Active_Query);
				Object CountNodes_Active = session.createSQLQuery(Node_Active_Query).uniqueResult();
				strEmpty="";
				strExist="";
		////////////////////////////
				strEmpty= "select count(distinct ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' ";
				strEmpty= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				strExist= "select count(distinct ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' ";	
				strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
				String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Node_GCell_Query);
				Object CountNodes_G_CELL = session.createSQLQuery(Node_GCell_Query).uniqueResult();
				strEmpty="";
				strExist="";
		////////////////////////////
				strEmpty= "select count(distinct nlc.LCell_Id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' ";
				strEmpty= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				strExist= "select count(distinct nlc.LCell_Id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' ";	
				strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
				String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Node_LCell_Query);
				Object CountNodes_L_CELL = session.createSQLQuery(Node_LCell_Query).uniqueResult();
				strEmpty="";
				strExist="";
		////////////////////////////
				strEmpty= "select count(distinct nuc.UCell_Id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' ";
				strEmpty= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
				strExist= "select count(distinct nuc.UCell_Id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' ";	
				strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
				String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
				//System.out.println(Node_UCell_Query);
				Object CountNodes_U_CELL = session.createSQLQuery(Node_UCell_Query).uniqueResult();
				strEmpty="";
				strExist="";

				BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
				BoqHM.put("Nodes", String.valueOf(CountNodes_Active));
				BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
				BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
				BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
				return BoqHM;				
			} catch (Exception e) {
				logger.info("Error in retreiving Node type BOQ data Data from database", e);
				return null;
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		
		////node type boq for supplier
		// Node Type BOQ data retrieving
				@SuppressWarnings("unchecked")
				@RequestMapping(value = "/GetNtypeSupBoqList", method = RequestMethod.GET, produces = "application/json")
				@ResponseBody

				public LinkedHashMap<String, String> GetNtypeSupBoqList(@RequestParam String SiteId, @RequestParam String NodeTId, @RequestParam String SuppId,@RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

					Session session = almsessions.getSession();
					Transaction tx = session.beginTransaction();
					// if Site_id !=null --> an ajax request received
					LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

					try {
						String strEmpty= "SELECT COUNT(distinct WARE_ID) FROM NODE_ACTIVE WHERE NODE_TYPE='" + NodeTId + "' AND SUPPLIER_ID = '"+SuppId+"' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String strExist= "Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' and NODE_TYPE='"+ NodeTId + "' AND SUPPLIER_ID = '"+SuppId+"' ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Site_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Site_Query);
						Object Sites = session.createSQLQuery(Site_Query).uniqueResult();
						strEmpty="";
						strExist="";
				////////////////////////////
						strEmpty= "SELECT COUNT(distinct NODE_PK) FROM NODE_ACTIVE where Active_record='1' and NODE_TYPE='"+ NodeTId + "' AND SUPPLIER_ID = '"+SuppId+"' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						strExist= "SELECT COUNT(distinct NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' and NODE_TYPE='" + NodeTId + "' AND SUPPLIER_ID = '"+SuppId+"' ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
						System.out.println(Node_Active_Query);
						Object CountNodes_Active = session.createSQLQuery(Node_Active_Query).uniqueResult();
						strEmpty="";
						strExist="";
				////////////////////////////
						strEmpty= "select count(distinct ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' AND na.SUPPLIER_ID = '"+SuppId+"' ";
						strEmpty= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						strExist= "select count(distinct ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.SUPPLIER_ID = '"+SuppId+"' ";	
						strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_GCell_Query);
						Object CountNodes_G_CELL = session.createSQLQuery(Node_GCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
				////////////////////////////
						strEmpty= "select count(distinct nlc.LCell_Id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' AND na.SUPPLIER_ID = '"+SuppId+"' ";
						strEmpty= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						strExist= "select count(distinct nlc.LCell_Id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.SUPPLIER_ID = '"+SuppId+"' ";	
						strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_LCell_Query);
						Object CountNodes_L_CELL = session.createSQLQuery(Node_LCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
				////////////////////////////
						strEmpty= "select count(distinct nuc.UCell_Id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' AND na.SUPPLIER_ID = '"+SuppId+"' ";
						strEmpty= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						strExist= "select count(distinct nuc.UCell_Id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.SUPPLIER_ID = '"+SuppId+"' ";	
						strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_UCell_Query);
						Object CountNodes_U_CELL = session.createSQLQuery(Node_UCell_Query).uniqueResult();
						strEmpty="";
						strExist="";

						BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
						BoqHM.put("Nodes", String.valueOf(CountNodes_Active));
						BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
						BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
						BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
						return BoqHM;				
					} catch (Exception e) {
						logger.info("Error in retreiving Node type BOQ data Data from database", e);
						return null;
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
						}
					}
				}
				
		///node type boq for vendor 
		// Node Type BOQ data retrieving
				@SuppressWarnings("unchecked")
				@RequestMapping(value = "/GetNtypeVenBoqList", method = RequestMethod.GET, produces = "application/json")
				@ResponseBody

				public LinkedHashMap<String, String> GetNtypeVenBoqList(@RequestParam String SiteId, @RequestParam String NodeTId, @RequestParam String VendorId, @RequestParam String paramEnterprise,@RequestParam String paramTransmission,@RequestParam String paramAccess,@RequestParam String paramCore) {

					Session session = almsessions.getSession();
					Transaction tx = session.beginTransaction();
					// if Site_id !=null --> an ajax request received
					LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

					try {
						String strEmpty= "SELECT COUNT(distinct WARE_ID) FROM NODE_ACTIVE WHERE NODE_TYPE='" + NodeTId + "' AND VENDOR = '"+VendorId+"' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";	
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						String strExist= "Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' and NODE_TYPE='"+ NodeTId + "' AND VENDOR = '"+VendorId+"' ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Site_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Site_Query);
						Object Sites = session.createSQLQuery(Site_Query).uniqueResult();
						strEmpty="";
						strExist="";
				////////////////////////////
						strEmpty= "SELECT COUNT(distinct NODE_PK) FROM NODE_ACTIVE where Active_record='1' and NODE_TYPE='"+ NodeTId + "' AND VENDOR = '"+VendorId+"' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";
						strEmpty= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						strExist= "SELECT COUNT(distinct NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"+ SiteId + "' and NODE_TYPE='" + NodeTId + "' AND VENDOR = '"+VendorId+"' ";	
						strExist= boqDomain (paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_Active_Query);
						Object CountNodes_Active = session.createSQLQuery(Node_Active_Query).uniqueResult();
						strEmpty="";
						strExist="";
				////////////////////////////
						strEmpty= "select count(distinct ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' AND na.VENDOR = '"+VendorId+"' ";
						strEmpty= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						strExist= "select count(distinct ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.VENDOR = '"+VendorId+"' ";	
						strExist= boqDomainVar ("ngc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_GCell_Query);
						Object CountNodes_G_CELL = session.createSQLQuery(Node_GCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
				////////////////////////////
						strEmpty= "select count(distinct nlc.LCell_Id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' AND na.VENDOR = '"+VendorId+"' ";
						strEmpty= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						strExist= "select count(distinct nlc.LCell_Id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.VENDOR = '"+VendorId+"' ";	
						strExist= boqDomainVar ("nlc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_LCell_Query);
						Object CountNodes_L_CELL = session.createSQLQuery(Node_LCell_Query).uniqueResult();
						strEmpty="";
						strExist="";
				////////////////////////////
						strEmpty= "select count(distinct nuc.UCell_Id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.NODE_TYPE = '"+ NodeTId + "' AND na.VENDOR = '"+VendorId+"' ";
						strEmpty= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);	
						strExist= "select count(distinct nuc.UCell_Id) from node_ucell nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.VENDOR = '"+VendorId+"' ";	
						strExist= boqDomainVar ("nuc",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);			
						String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
						//System.out.println(Node_UCell_Query);
						Object CountNodes_U_CELL = session.createSQLQuery(Node_UCell_Query).uniqueResult();
						strEmpty="";
						strExist="";

						BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
						BoqHM.put("Nodes", String.valueOf(CountNodes_Active));
						BoqHM.put("G-cells", String.valueOf(CountNodes_G_CELL));
						BoqHM.put("L-cells", String.valueOf(CountNodes_L_CELL));
						BoqHM.put("U-cells", String.valueOf(CountNodes_U_CELL));
						return BoqHM;				
					} catch (Exception e) {
						logger.info("Error in retreiving Node type BOQ data Data from database", e);
						return null;
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
						}
					}
				}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/NetworkTree", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> Networks(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			String[] selArray = request.getParameterValues("selArray[]");
			try {
				List<Object[]> result = session
						.createSQLQuery("SELECT GCELL_ID,CELLNAME,NODE_PK FROM NODE_GCELL WHERE ACTIVE_RECORD = '1'")
						.list();
				List<Object[]> result1 = session
						.createSQLQuery("SELECT LCELL_ID,CELLNAME,NODE_PK FROM NODE_LCELL WHERE ACTIVE_RECORD = '1'")
						.list();
				List<Object[]> result2 = session
						.createSQLQuery("SELECT UCELL_ID,CELLNAME,NODE_PK FROM NODE_UCELL WHERE ACTIVE_RECORD = '1'")
						.list();

				List<Object[]> cellResult = new ArrayList<Object[]>();

				if (!result.isEmpty()) {

					for (Object[] obj : result) {

						cellResult.add(obj);

					}

				}
				if (!result1.isEmpty()) {
					for (Object[] obj : result1) {

						cellResult.add(obj);

					}

				}
				if (!result2.isEmpty()) {
					for (Object[] obj : result2) {

						cellResult.add(obj);

					}

				}

				model.addAttribute("listCells", mapper.writeValueAsString(cellResult));
			} catch (Exception e) {
				logger.info("Error in retreiving cells data Data from database", e);
				model.addAttribute("listCells", mapper.writeValueAsString(null));
			}
			try {
				model.addAttribute("listNodes", ((List<Object[]>) session.createSQLQuery(
						"SELECT NODE_PK,SITE_ID,NODE_NAME,NODE_TYPE,WARE_ID FROM NODE_ACTIVE WHERE ACTIVE_RECORD = '1'")
						.list()));

			} catch (Exception e) {
				logger.info("Error in retreiving nodes data Data from database", e);
				model.addAttribute("listNodes", mapper.writeValueAsString(null));
			}
			try {
				model.addAttribute("listSites", ((List<Object[]>) session
						.createSQLQuery("SELECT SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM WAREHOUSE").list()));

			} catch (Exception e) {
				logger.info("Error in retreiving sites data Data from database", e);
				model.addAttribute("listSites", mapper.writeValueAsString(null));
			}
			try {
				model.addAttribute("listNodesType",
						((List<Object[]>) session
								.createSQLQuery(
										"SELECT DISTINCT NODE_TYPE,WARE_ID FROM NODE_ACTIVE WHERE ACTIVE_RECORD = '1'")
								.list()));

			} catch (Exception e) {
				logger.info("Error in retreiving node type  data Data from database", e);
				model.addAttribute("listNodesType", mapper.writeValueAsString(null));
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "resources/js/Network/NetworkTree.js/pTree", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> pTree(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				String selectedItem = request.getParameter("selectedItem");
				String Ptype = request.getParameter("Ptype");

				if (Ptype.equals("Supp")) {
					//System.out.println(selectedItem);
					rtn.put("listSites", ((List<Object[]>) session.createSQLQuery(
							"SELECT distinct WARE_ID FROM NODE_ACTIVE where WARE_ID!='0' and WARE_ID!='null' and Supplier_id='"
									+ selectedItem + "'")
							.list()));
				}

				if (Ptype.equals("Nodetype")) {
					rtn.put("listSites", ((List<Object[]>) session.createSQLQuery(
							"SELECT distinct WARE_ID FROM NODE_ACTIVE where WARE_ID!='0' and WARE_ID!='null' and NODE_TYPE='"
									+ selectedItem + "'")
							.list()));
				}
				if (Ptype.equals("PO")) {
					rtn.put("listSites", ((List<Object[]>) session.createSQLQuery(
							"SELECT distinct WARE_ID FROM ASSET_REGISTRY where WARE_ID!='0' and WARE_ID!='null' and PO_ID='"
									+ selectedItem + "'")
							.list()));
				}

			} catch (Exception e) {
				logger.info("Error in retreiving area sites data Data from database", e);
				rtn.put("listSites", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/NodeType", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> NodeType(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				// List<?> nodeTypeList = session.createSQLQuery(
				rtn.put("listNodesType", ((List<Object[]>) session.createSQLQuery(
						"SELECT DISTINCT NODE_TYPE,(select COUNT(*) from NODE_ACTIVE a  where a.NODE_TYPE=b.NODE_TYPE and a.ACTIVE_RECORD = '1') as countnodes FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1'")
						.list()));

			} catch (Exception e) {
				logger.info("Error in retreiving sites Data from database", e);
				rtn.put("listNodesType", null);
				// rtn.put("listSites", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/POStItem", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> POStItem(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				rtn.put("listPO",
						//((List<Object[]>) session.createSQLQuery("SELECT distinct a.PO_ID,"
								//+ "(select count(*) from WAREHOUSE b where b.WARE_ID = a.WARE_ID) as countSite"
							//	+ "(select count(*) from WAREHOUSE b where b.NODE_ID = a.NODE_ID) as countSite"
							//	+ " FROM ASSET_REGISTRY a").list()));
								((List<Object[]>) session.createSQLQuery("SELECT distinct PO_ID FROM ASSET_REGISTRY").list()));
				rtn.put("listSites", ((List<Object[]>) session.createSQLQuery(
						//"SELECT distinct WARE_ID FROM ASSET_REGISTRY where WARE_ID!='0' and WARE_ID!='null' and PO_ID!='null'")
						"SELECT distinct WARE_ID FROM NODE_ACTIVE a, ASSET_REGISTRY b WHERE a.NODE_ID= b.NODE_ID and b.PO_ID!='null'")
						.list()));

			} catch (Exception e) {
				logger.info("Error in retreiving PO Data from database", e);
				rtn.put("listPO", null);
			}
			/*
			 * try { rtn.put("listsite", ((List<Object[]>) session.createSQLQuery(
			 * "SELECT distinct b.SITE_ID,b.WARE_NAME,b.WARE_ID,j.PO_ID," +
			 * "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
			 * +
			 * "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
			 * +
			 * "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID  and w.ACTIVE_RECORD = '1') as countnodes,"
			 * +
			 * "(select COUNT(*) from ASSET_REGISTRY j where j.WARE_ID=b.WARE_ID and j.PO_ID!='null') as countPO,"
			 * +
			 * "(select COUNT(*) from ASSET_REGISTRY j where j.WARE_ID=b.WARE_ID and j.PO_ID!='null' and j.WARE_ID!='null') as countItems,"
			 * +
			 * "(select COUNT(*) from NODE_GCELL c where c.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countGcells,"
			 * +
			 * "(select COUNT(*) from NODE_LCELL d where d.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countLcells,"
			 * +
			 * "(select COUNT(*) from NODE_UCELL e where e.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countUcells"
			 * +
			 * " from WAREHOUSE b,ASSET_REGISTRY j where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID=j.WARE_ID and j.PO_ID!='null' "
			 * ) .list()));
			 * 
			 * } catch (Exception e) {
			 * logger.info("Error in retreiving sites Data from database",e);
			 * rtn.put("listsite", null); } try { rtn.put("itemList", ((List<Object[]>)
			 * session.createSQLQuery(
			 * "SELECT distinct ITEM_CODE, ITEM_NAME, WARE_ID,PO_ID, ITEM_MODEL  FROM ASSET_REGISTRY where PO_ID is not null and WARE_ID is not null and ITEM_CODE!='null'"
			 * ) .list()));
			 * 
			 * } catch (Exception e) {
			 * logger.info("Error in retreiving items Data from database",e);
			 * rtn.put("itemList", null); }
			 */ finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

//retrieve sites and items data on PO click
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findPOSt_Items", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findPOSt_Items(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
				
				List<String> listSites = new ArrayList<String>();
				String strSites ="SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,j.PO_ID FROM AR_SITE b,ASSET_REGISTRY j WHERE j.AR_ID=b.AR_ID AND b.WARE_ID!='0' AND b.WARE_ID!='null' AND b.WARE_ID is not null AND j.PO_ID='"+selectedItem+"' ";
				
				List<String> itemList = new ArrayList<String>();
				String strItems ="SELECT distinct a.ITEM_CODE, a.ITEM_NAME, a.PO_ID, b.WARE_ID, a.ITEM_MODEL FROM ASSET_REGISTRY a, AR_SITE b WHERE a.AR_ID=b.AR_ID AND b.WARE_ID='"+selectedSite+"' AND a.PO_ID='"+selectedItem+"' ";			
				
				
			try {
				if (POAlreadyCreated.equals("false")) {
					strSites= boqDomainVar ("j",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
					
					//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
					Query query = session.createSQLQuery(strSites);
					listSites = query.list();
					rtn.put("listSites",listSites);
					//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));											
					
				}else {
					strItems= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strItems);
					
					//System.out.println("final query items"+mapper.writeValueAsString(strItems));
					Query query = session.createSQLQuery(strItems);
					itemList = query.list();
					rtn.put("itemList", itemList);
					//System.out.println("list items ==> "+ mapper.writeValueAsString(itemList));											

				}
			} catch (Exception e) {
				logger.info("Error in retreiving items and sites Data from database", e);
				rtn.put("itemList", null);
				rtn.put("listSites", null);
			} 
			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	// retrieve sites and PO data on items click
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/StPOItem", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> StPOItem(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				/*
				 * List<Object[]> siteList = (List<Object[]>) session.createSQLQuery(
				 * 
				 * "SELECT distinct b.SITE_ID,b.WARE_NAME,b.WARE_ID," +
				 * "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
				 * +
				 * "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
				 * +
				 * "(select COUNT(*) from ASSET_REGISTRY j where j.WARE_ID=b.WARE_ID and j.PO_ID!='null' and j.WARE_ID!='null') as countItems,"
				 * +
				 * "(select COUNT(*) from NODE_GCELL c where c.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.ACTIVE_RECORD = '1' and o.WARE_ID=b.WARE_ID)) as countGcells,"
				 * +
				 * "(select COUNT(*) from NODE_LCELL d where d.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.ACTIVE_RECORD = '1' and o.WARE_ID=b.WARE_ID)) as countLcells,"
				 * +
				 * "(select COUNT(*) from NODE_UCELL e where e.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.ACTIVE_RECORD = '1' and o.WARE_ID=b.WARE_ID)) as countUcells,"
				 * +
				 * "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID  and w.ACTIVE_RECORD = '1') as countnodes,"
				 * +
				 * "(select COUNT(*) from ASSET_REGISTRY j where j.WARE_ID=b.WARE_ID and j.PO_ID!='null') as countPO"
				 * +
				 * " from WAREHOUSE b,ASSET_REGISTRY t where b.WARE_ID=t.WARE_ID and t.PO_ID!='null' and b.WARE_ID!='0' and b.WARE_ID!='null'"
				 * ) .list();
				 * 
				 * // List<Object[]> siteResult = new ArrayList<Object[]>();
				 * 
				 * rtn.put("listsite", siteList);
				 */

				rtn.put("listSites", ((List<Object[]>) session.createSQLQuery(
						"SELECT distinct WARE_ID FROM ASSET_REGISTRY where WARE_ID!='0' and WARE_ID!='null' and PO_ID!='null'")
						.list()));

			} catch (Exception e) {
				logger.info("Error in retreiving sites Data from database", e);
				rtn.put("listSites", null);
			}
			try {
				rtn.put("listPO", ((List<Object[]>) session.createSQLQuery(
						"SELECT distinct a.PO_ID ,a.WARE_ID,a.WARE_NAME,a.SITE_ID FROM ASSET_REGISTRY a,NODE_ACTIVE b where b.ACTIVE_RECORD = '1' and b.WARE_ID = a.WARE_ID ")
						.list()));
			} catch (Exception e) {
				logger.info("Error in retreiving PO Data from database", e);
				rtn.put("listPO", null);
			}
			try {
				rtn.put("itemList", ((List<Object[]>) session.createSQLQuery(
						"SELECT distinct ITEM_CODE, ITEM_NAME, PO_ID, WARE_ID, ITEM_MODEL FROM ASSET_REGISTRY where PO_ID is not null and WARE_ID is not null")
						.list()));

			} catch (Exception e) {
				logger.info("Error in retreiving items Data from database", e);
				rtn.put("itemList", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	// retrieve items on sites click
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findSitePO_Items", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findSitePO_Items(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
				
				List<String> listPO = new ArrayList<String>();
				String strPO ="SELECT distinct a.PO_ID ,b.WARE_ID,b.SITE_NAME,b.SITE_ID,"
						+ "(select count(*) from ASSET_REGISTRY a where a.PO_ID is not null and b.WARE_ID is not null) as countItems "
						+ "FROM ASSET_REGISTRY a, AR_SITE b where a.AR_ID=b.AR_ID and b.WARE_ID='"+ selectedSite + "' ";
				
				List<String> itemList = new ArrayList<String>();
				String strItems ="SELECT distinct a.ITEM_CODE, a.ITEM_NAME, a.PO_ID, b.WARE_ID, a.ITEM_MODEL FROM ASSET_REGISTRY a, AR_SITE b WHERE a.AR_ID=b.AR_ID AND b.WARE_ID='"+selectedSite+"' AND a.PO_ID='"+selectedPO+"' ";			
			
			try {				
				if (POAlreadyCreated.equals("false")) {
					strPO=  boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strPO);
					
					//System.out.println("final query PO "+mapper.writeValueAsString(strPO));
					Query query = session.createSQLQuery(strPO);
					listPO = query.list();
					rtn.put("listPO", listPO);
					//System.out.println("list PO ==> "+ mapper.writeValueAsString(listPO));
				}
				else {
					strItems= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strItems);
					
					//System.out.println("final query items"+mapper.writeValueAsString(strItems));
					Query query = session.createSQLQuery(strItems);
					itemList = query.list();
					rtn.put("itemList", itemList);
					//System.out.println("list items ==> "+ mapper.writeValueAsString(itemList));											
				}
			} catch (Exception e) {
				logger.info("Error in retreiving PO and items Data from database", e);
				rtn.put("listPO", null);
				rtn.put("itemList", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	// retrieve PO and items data
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/POItemSt", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> POItemSt(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				rtn.put("listPO",
						((List<Object[]>) session.createSQLQuery("SELECT distinct a.PO_ID,"
								+ "(select count(*) from ASSET_REGISTRY b where b.PO_ID = a.PO_ID) as countItem"
								+ " FROM ASSET_REGISTRY a").list()));

				rtn.put("itemList", ((List<Object[]>) session.createSQLQuery(
						"SELECT distinct ITEM_CODE, ITEM_NAME, PO_ID,ITEM_MODEL FROM ASSET_REGISTRY where PO_ID is not null and ITEM_CODE is not null")
						.list()));

				rtn.put("listsite", ((List<Object[]>) session
						.createSQLQuery("SELECT distinct b.SITE_ID,b.WARE_NAME,b.WARE_ID,j.ITEM_CODE,j.PO_ID,"
								+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
								+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
								+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID  and w.ACTIVE_RECORD = '1') as countnodes,"
								+ "(select COUNT(*) from ASSET_REGISTRY j where j.WARE_ID=b.WARE_ID  and j.PO_ID!='null' and j.WARE_ID!='null') as countItems,"
								+ "(select COUNT(*) from NODE_GCELL c where c.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countGcells,"
								+ "(select COUNT(*) from NODE_LCELL d where d.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countLcells,"
								+ "(select COUNT(*) from NODE_UCELL e where e.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countUcells"
								+ " from WAREHOUSE b,ASSET_REGISTRY j where j.WARE_ID=b.WARE_ID and j.PO_ID is not null and ITEM_CODE is not null ")
						.list()));
			} catch (Exception e) {
				logger.info("Error in retreiving PO and items Data from database", e);
				rtn.put("listPO", null);
				rtn.put("itemList", null);
				rtn.put("listsite", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Cell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> Cell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				List<Object[]> result = session.createSQLQuery(
						"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
						.list();
				List<Object[]> result1 = session.createSQLQuery(
						"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
						.list();
				List<Object[]> result2 = session.createSQLQuery(
						"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0'")
						.list();

				List<Object[]> cellResult = new ArrayList<Object[]>();

				if (!result.isEmpty()) {

					cellResult.addAll(result);

				}
				if (!result1.isEmpty()) {
					cellResult.addAll(result1);

				}
				if (!result2.isEmpty()) {

					cellResult.addAll(result2);

				}

				rtn.put("listCells", cellResult);
			} catch (Exception e) {
				logger.info("Error in retreiving Data from database", e);
				rtn.put("listCells", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/POItStNdtpNd", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> POItStNdtpNd(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				rtn.put("listPO",
						((List<Object[]>) session.createSQLQuery("SELECT distinct a.PO_ID,"
								+ "(select count(*) from ASSET_REGISTRY b where b.PO_ID = a.PO_ID) as countItem"
								+ " FROM ASSET_REGISTRY a").list()));

			} catch (Exception e) {
				logger.info("Error in retreiving Data from database", e);
				rtn.put("listPO", null);
			}
			try {
				rtn.put("itemList", ((List<Object[]>) session.createSQLQuery(
						"SELECT distinct ITEM_CODE, ITEM_NAME, PO_ID,ITEM_MODEL FROM ASSET_REGISTRY where PO_ID is not null and ITEM_CODE is not null")
						.list()));
			} catch (Exception e) {
				logger.info("Error in retreiving items Data from database", e);
				rtn.put("itemList", null);
			}
			try {
				rtn.put("listsite", ((List<Object[]>) session
						.createSQLQuery("SELECT distinct b.SITE_ID,b.WARE_NAME,b.WARE_ID,j.ITEM_CODE,j.PO_ID,"
								+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
								+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
								+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID  and w.ACTIVE_RECORD = '1') as countnodes,"
								+ "(select COUNT(*) from ASSET_REGISTRY j where j.WARE_ID=b.WARE_ID and b.ACTIVE_RECORD = '1' and j.PO_ID!='null' and j.WARE_ID!='null') as countItems,"
								+ "(select COUNT(*) from NODE_GCELL c where c.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countGcells,"
								+ "(select COUNT(*) from NODE_LCELL d where d.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countLcells,"
								+ "(select COUNT(*) from NODE_UCELL e where e.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countUcells")
						.list()));

			} catch (Exception e) {
				logger.info("Error in retreiving sites Data from database", e);
				rtn.put("listsite", null);
			}
			try {
				rtn.put("listNodesType", ((List<Object[]>) session.createSQLQuery(
						"SELECT DISTINCT a.NODE_TYPE,a.WARE_ID,b.ITEM_CODE FROM NODE_ACTIVE a,ASSET_REGISTRY b WHERE a.ACTIVE_RECORD = '1' and a.WARE_ID=b.WARE_ID")
						.list()));

			} catch (Exception e) {
				logger.info("Error in retreiving node type Data from database", e);
				rtn.put("listNodesType", null);
			}
			try {
				rtn.put("listNodes", ((List<Object[]>) session.createSQLQuery(
						"SELECT NODE_NAME,NODE_TYPE,NODE_PK,WARE_ID,SITE_ID FROM NODE_ACTIVE a WHERE ACTIVE_RECORD = '1' and NODE_PK!='0'")
						.list()));
			} catch (Exception e) {
				logger.info("Error in retreiving nodes Data from database", e);
				rtn.put("listNodes", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Node", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> Node(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);
		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				List<Object[]> listNodes = (List<Object[]>) session.createSQLQuery(
						"SELECT a.NODE_PK,a.NODE_NAME,a.NODE_TYPE,(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1') as countGCells,(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1') as countLCells,(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1') as countUCells FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1'")
						.list();
				rtn.put("listNodes", listNodes);
			} catch (Exception e) {
				logger.info("Error in retreiving nodes Data from database", e);
				rtn.put("listNodes", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findNodeType_Nodes", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findNodeType_Nodes(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String paramEnterprise = request.getParameter("paramEnterprise");
			if (paramEnterprise.equals("true")) {
			//System.out.println("parm true...node type..."+ paramEnterprise);
			
			try {
				String selectedItem = request.getParameter("selectedItem");	
			
				if (selectedItem != null) {
					try {
						List<Object[]> nodeList = (List<Object[]>) session.createSQLQuery(
								"SELECT a.NODE_PK,a.SITE_ID,a.NODE_NAME,a.NODE_TYPE,a.WARE_ID,"
								+ "(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1' and b.DOMAIN='Enterprise') as countGCells,"
								+ "(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1' and c.DOMAIN='Enterprise') as countLCells,"
								+ "(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1' and d.DOMAIN='Enterprise') as countUCells "
								+ "FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.NODE_TYPE='"
										+ selectedItem + "' AND a.DOMAIN='Enterprise'")
								.list();
						//System.out.println("===>nodeList	" + nodeList);
						rtn.put("listNodes", nodeList);
					} catch (Exception e) {
						logger.info("Error in retreiving nodes Data from database", e);
						rtn.put("listNodes", null);
					}
				}else {
					String selectedNode = request.getParameter("selectedNode");
					try {
						List<Object[]> result = session.createSQLQuery(
								"SELECT GCELL_ID,CELLNAME,NODE_PK FROM NODE_GCELL WHERE ACTIVE_RECORD = '1' and NODE_PK='"
										+ selectedNode + "' AND DOMAIN='Enterprise'")
								.list();
						List<Object[]> result1 = session.createSQLQuery(
								"SELECT LCELL_ID,CELLNAME,NODE_PK FROM NODE_LCELL WHERE ACTIVE_RECORD = '1'  and NODE_PK='"
										+ selectedNode + "' AND DOMAIN='Enterprise'")
								.list();
						List<Object[]> result2 = session.createSQLQuery(
								"SELECT UCELL_ID,CELLNAME,NODE_PK FROM NODE_UCELL WHERE ACTIVE_RECORD = '1' and NODE_PK='"
										+ selectedNode + "' AND DOMAIN='Enterprise'")
								.list();

						List<Object[]> cellResult = new ArrayList<Object[]>();

						if (!result.isEmpty()) {
							cellResult.addAll(result);
						}
						if (!result1.isEmpty()) {
							cellResult.addAll(result1);
						}
						if (!result2.isEmpty()) {
							cellResult.addAll(result2);
						}
						
						//System.out.println("selected node : " + selectedNode);
						rtn.put("listCells", cellResult);
					} catch (Exception e) {
						logger.info("Error in retreiving cells Data from database", e);
						rtn.put("listCells", null);
					}
				}
			} catch (Exception e) {
				logger.info("Error in retreiving cells Data from database", e);
			} 
		}else{
			//System.out.println("parm false...node type..."+ paramEnterprise);
			
			try {
				String selectedItem = request.getParameter("selectedItem");	
			
				if (selectedItem != null) {
					try {
						List<Object[]> nodeList = (List<Object[]>) session.createSQLQuery(
								"SELECT a.NODE_PK,a.SITE_ID,a.NODE_NAME,a.NODE_TYPE,a.WARE_ID,"
								+ "(select count(*) from NODE_GCELL b  where a.NODE_PK = b.NODE_PK and ACTIVE_RECORD = '1') as countGCells,"
								+ "(select count(*) from NODE_LCELL c  where a.NODE_PK = c.NODE_PK and ACTIVE_RECORD = '1') as countLCells,"
								+ "(select count(*) from NODE_UCELL d  where a.NODE_PK = d.NODE_PK and ACTIVE_RECORD = '1') as countUCells "
								+ "FROM NODE_ACTIVE a WHERE a.ACTIVE_RECORD = '1' and a.NODE_TYPE='"
										+ selectedItem + "'")
								
								.list();
						//System.out.println("===>nodeList	" + mapper.writeValueAsString(nodeList));
						rtn.put("listNodes", nodeList);
					} catch (Exception e) {
						logger.info("Error in retreiving nodes Data from database", e);
						rtn.put("listNodes", null);
					}
				}

				else {
					String selectedNode = request.getParameter("selectedNode");
					try {
						List<Object[]> result = session.createSQLQuery(
								"SELECT GCELL_ID,CELLNAME,NODE_PK FROM NODE_GCELL WHERE ACTIVE_RECORD = '1' and NODE_PK='"
										+ selectedNode + "'")
								.list();
						List<Object[]> result1 = session.createSQLQuery(
								"SELECT LCELL_ID,CELLNAME,NODE_PK FROM NODE_LCELL WHERE ACTIVE_RECORD = '1'  and NODE_PK='"
										+ selectedNode + "'")
								.list();
						List<Object[]> result2 = session.createSQLQuery(
								"SELECT UCELL_ID,CELLNAME,NODE_PK FROM NODE_UCELL WHERE ACTIVE_RECORD = '1' and NODE_PK='"
										+ selectedNode + "'")
								.list();

						List<Object[]> cellResult = new ArrayList<Object[]>();

						if (!result.isEmpty()) {
							cellResult.addAll(result);
						}
						if (!result1.isEmpty()) {
							cellResult.addAll(result1);
						}
						if (!result2.isEmpty()) {
							cellResult.addAll(result2);
						}
						
						//System.out.println("selected node : " + selectedNode);
						rtn.put("listCells", cellResult);
					} catch (Exception e) {
						logger.info("Error in retreiving cells Data from database", e);
						rtn.put("listCells", null);
					}
				}
			} catch (Exception e) {
				logger.info("Error in retreiving cells Data from database", e);
			} 
		}
		//finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		//}
		return rtn;
	}
	// find cells data in NODE-CELL method
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/findNode_Cells", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> findNode_Cells(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			// logger.info("Welcome home! The client locale is {}.", locale);

			Map<String, Object> rtn = new LinkedHashMap<>();
			// ObjectMapper mapper = new ObjectMapper();
			Session session = null;
			Transaction tx = null;
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
					//System.out.println("selectedNdTyp NULL");
			        List<Object[]> cellResult = new ArrayList<Object[]>();
					String strCells1 ="SELECT g.GCELL_ID,g.CELLNAME,g.NODE_PK FROM NODE_GCELL g WHERE g.ACTIVE_RECORD = '1' and g.NODE_PK='"+ selectedNode + "' "; 
					String strCells2 ="SELECT l.LCELL_ID,l.CELLNAME,l.NODE_PK FROM NODE_LCELL l WHERE l.ACTIVE_RECORD = '1' and l.NODE_PK='"+ selectedNode + "' "; 
					String strCells3 ="SELECT u.UCELL_ID,u.CELLNAME,u.NODE_PK FROM NODE_UCELL u WHERE u.ACTIVE_RECORD = '1' and u.NODE_PK='"+ selectedNode + "' ";		
				
					strCells1= boqDomainVar ("g",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
					strCells2= boqDomainVar ("l",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
					strCells3= boqDomainVar ("u",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
				
					//System.out.println("final query cells 1 "+mapper.writeValueAsString(strCells1));
					//System.out.println("final query cells 2 "+mapper.writeValueAsString(strCells2));
					//System.out.println("final query cells 3 "+mapper.writeValueAsString(strCells3));
					
					Query query1 = session.createSQLQuery(strCells1);
					Query query2 = session.createSQLQuery(strCells2);
					Query query3 = session.createSQLQuery(strCells3);
					cellResult.addAll(query1.list());
					cellResult.addAll(query2.list());
					cellResult.addAll(query3.list());
					
					rtn.put("listCells", cellResult);
					//System.out.println("list cells ==> "+ mapper.writeValueAsString(cellResult));
					
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
					
						//System.out.println("final query cells 1 "+mapper.writeValueAsString(strCells1));
						//System.out.println("final query cells 2 "+mapper.writeValueAsString(strCells2));
						//System.out.println("final query cells 3 "+mapper.writeValueAsString(strCells3));
						
						Query query1 = session.createSQLQuery(strCells1);
						Query query2 = session.createSQLQuery(strCells2);
						Query query3 = session.createSQLQuery(strCells3);
						cellResult.addAll(query1.list());
						cellResult.addAll(query2.list());
						cellResult.addAll(query3.list());
						
						rtn.put("listCells",cellResult);
						//System.out.println("list cells ==> "+ mapper.writeValueAsString(cellResult));
					}	
				} catch (Exception e) {
					logger.info("Error in retreiving cells Data from database", e);
					rtn.put("listCells", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
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
			// logger.info("Welcome home! The client locale is {}.", locale);

			Map<String, Object> rtn = new LinkedHashMap<>();
			ObjectMapper mapper = new ObjectMapper();
			Session session = null;
			Transaction tx = null;
			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				//System.out.println("HEY HEY");
				//String selectedNode = request.getParameter("selectedNode");
				String selectedNdTyp = request.getParameter("selectedNodetType");
				String selectedItem = request.getParameter("selectedItem");
				
				String paramEnterprise = request.getParameter("paramEnterprise");
				String paramTransmission = request.getParameter("paramTransmission");
				String paramAccess = request.getParameter("paramAccess");
				String paramCore = request.getParameter("paramCore");
					
				List<Object[]> listSites = new ArrayList<Object[]>();
				String strSites ="SELECT distinct SITE_ID,WARE_NAME,WARE_ID,NODE_TYPE,LONGITUDE,LATITUDE FROM NODE_ACTIVE "
						+ "where ACTIVE_RECORD = '1' and WARE_ID!= 'null' and WARE_ID is not null and WARE_ID!= '0' and NODE_TYPE='"+ selectedNdTyp + "' ";
				
				List<Object[]> listNodes = new ArrayList<Object[]>();
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
							//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
							Query query = session.createSQLQuery(strSites);
							listSites = query.list();
							rtn.put("listSites",listSites);
							//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));
						} catch (Exception e) {
							logger.info("Error in retreiving sites Data from database", e);
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
								
							//System.out.println("final query nodes"+mapper.writeValueAsString(strNodes));
							Query query = session.createSQLQuery(strNodes);
							listNodes = query.list();
							rtn.put("listNodes", listNodes);
							//System.out.println("list nodes ==> "+ mapper.writeValueAsString(listNodes));
						} catch (Exception e) {
							logger.info("Error in retreiving nodes Data from database", e);
							rtn.put("listNodes", null);
						}
					}		
					
					if (selectedItem != null) {
						try {
							strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);		
							strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);		
							strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);		
						
							//System.out.println("final query cells 1 "+mapper.writeValueAsString(strCells1));
							//System.out.println("final query cells 2 "+mapper.writeValueAsString(strCells2));
							//System.out.println("final query cells 3 "+mapper.writeValueAsString(strCells3));
							
							Query query1 = session.createSQLQuery(strCells1);
							Query query2 = session.createSQLQuery(strCells2);
							Query query3 = session.createSQLQuery(strCells3);
							cellResult.addAll(query1.list());
							cellResult.addAll(query2.list());
							cellResult.addAll(query3.list());
							
							rtn.put("listCells", cellResult);
							//System.out.println("list cells ==> "+ mapper.writeValueAsString(cellResult));
							
						} catch (Exception e) {
							logger.info("Error in retreiving nodes Data from database", e);
							rtn.put("listCells", null);
						}
					}
				} 
				catch (Exception e) {
					logger.info("Error in retreiving Data from database", e);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return rtn;
		}


	// retrieve po/items/sites data in PO-Items-Sites method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findPOItems_sites", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findPOItems_sites(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
			
			List<String> itemList = new ArrayList<String>();
			String strItems ="SELECT distinct a.ITEM_CODE, a.ITEM_NAME, a.PO_ID, a.ITEM_MODEL FROM ASSET_REGISTRY a WHERE a.PO_ID='"+selectedPo+"' ";			
		
			List<String> listSites = new ArrayList<String>();
			String strSites ="SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,j.ITEM_CODE,j.PO_ID FROM AR_SITE b,ASSET_REGISTRY j where j.AR_ID=b.AR_ID AND b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and j.PO_ID='" + selectedPo + "' and j.ITEM_CODE='" + selectedItem + "' ";		
			
			
			if (POAlreadyCreated.equals("false")) {
				try {
					strItems= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strItems);
					
					//System.out.println("final query items"+mapper.writeValueAsString(strItems));
					Query query = session.createSQLQuery(strItems);
					itemList = query.list();
					rtn.put("listItem", itemList);
					//System.out.println("list items ==> "+ mapper.writeValueAsString(itemList));											

				} catch (Exception e) {
					logger.info("Error in retreiving items Data from database", e);
					rtn.put("listItem", null);
				}
			}
			else {
				try {
					strSites= boqDomainVar ("j",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
					
					//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
					Query query = session.createSQLQuery(strSites);
					listSites = query.list();
					rtn.put("listSites", listSites);
					//System.out.println("list sites ==> "+ mapper.writeValueAsString(listSites));											

				} catch (Exception e) {
					logger.info("Error in retreiving sites Data from database", e);
					rtn.put("listSites", null);
				}
/*
					if (SelectedSite != null) {
						try {
							List<Object[]> listNodesType = (List<Object[]>) session.createSQLQuery(
									"SELECT DISTINCT a.NODE_TYPE,a.WARE_ID,b.ITEM_CODE FROM NODE_ACTIVE a,ASSET_REGISTRY b "
									+ "WHERE a.ACTIVE_RECORD = '1' and b.DOMAIN='Enterprise' and a.WARE_ID=b.WARE_ID and b.PO_ID='" + selectedPo + "' and b.ITEM_CODE='" + selectedItem + "' and b.WARE_ID='"
											+ SelectedSite + "'")
									.list();

							rtn.put("listNodesType", listNodesType);
						} catch (Exception e) {
							logger.info("Error in retreiving list node type Data from database", e);
							rtn.put("listNodesType", null);
						}
					}
					String selectedNodetType = request.getParameter("selectedNodetType");
					if (selectedNodetType != null) {
						try {
							List<Object[]> listNodes = (List<Object[]>) session.createSQLQuery(
									"SELECT DISTINCT a.NODE_NAME,a.NODE_TYPE,a.NODE_PK,a.WARE_ID FROM NODE_ACTIVE a,ASSET_REGISTRY b WHERE b.DOMAIN='Enterprise' AND a.ACTIVE_RECORD = '1' and a.WARE_ID=b.WARE_ID and b.PO_ID='"
										+ selectedPo + "' and b.ITEM_CODE='" + selectedItem + "' and b.WARE_ID='"
										+ SelectedSite + "' and NODE_TYPE='" + selectedNodetType + "'")
									.list();

							rtn.put("listNodes", listNodes);
						} catch (Exception e) {
							logger.info("Error in retreiving nodes Data from database", e);
							rtn.put("listNodes", null);
						}
					}
*/					
				}
			} catch (Exception e) {
				logger.info("Error in retreiving Data from database", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
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
		// logger.info("Welcome home! The client locale is {}.", locale);
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
			
			List<String> site_SuppList = new ArrayList<String>();
			String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.SUPPLIER_ID FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.SUPPLIER_ID='"+ selectedSupp + "' ";
			
			List<String> nodeTypeList = new ArrayList<String>();
			String strNodesType ="SELECT DISTINCT b.NODE_TYPE,b.WARE_ID,b.SUPPLIER_ID FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' and b.WARE_ID='"+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' ";			
			
			List<String> nodeSuppList = new ArrayList<String>();
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
						
						//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
						Query query = session.createSQLQuery(strSites);
						site_SuppList = query.list();
						rtn.put("listSuppSites",site_SuppList);
						//System.out.println("list sites ==> "+ mapper.writeValueAsString(site_SuppList));										
						rtn.put("listSuppSites", site_SuppList);		
						
					} catch (Exception e) {
						logger.info("Error in retreiving sites Data from database", e);
						rtn.put("listSuppSites", null);
					}
				}
				if (selectedItem != null) {
					try {
						strNodesType= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodesType);

						//System.out.println("final query nodes type"+mapper.writeValueAsString(strNodesType));
						Query query = session.createSQLQuery(strNodesType);
						nodeTypeList = query.list();
						rtn.put("listNodesType", nodeTypeList);	
						//System.out.println("list nodes type ==> "+ mapper.writeValueAsString(nodeTypeList));												
							
					} catch (Exception e) {
						logger.info("Error in retreiving node type Data from database", e);
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
						
						//System.out.println("final query nodes"+mapper.writeValueAsString(strNodes));
						Query query = session.createSQLQuery(strNodes);
						nodeSuppList = query.list();
						rtn.put("listNodes", nodeSuppList);
						//System.out.println("list nodes ==> "+ mapper.writeValueAsString(nodeSuppList));
						
						} catch (Exception e) {
						logger.info("Error in retreiving nodes Data from database", e);
						rtn.put("listNodes", null);
					}
				}
				if (selectedNodetType != null) {
					try {
						strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
						strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
						strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
					
						//System.out.println("final query cells 1 "+mapper.writeValueAsString(strCells1));
						//System.out.println("final query cells 2 "+mapper.writeValueAsString(strCells2));
						//System.out.println("final query cells 3 "+mapper.writeValueAsString(strCells3));
						
						Query query1 = session.createSQLQuery(strCells1);
						Query query2 = session.createSQLQuery(strCells2);
						Query query3 = session.createSQLQuery(strCells3);
						cellResult.addAll(query1.list());
						cellResult.addAll(query2.list());
						cellResult.addAll(query3.list());
						
						rtn.put("listCells",cellResult);
						//System.out.println("list cells ==> "+ mapper.writeValueAsString(cellResult));

						} catch (Exception e) {
						logger.info("Error in retreiving nodes Data from database", e);
						rtn.put("listCells", null);
					}
				}
			} catch (Exception e) {
				logger.info("Error in retreiving Data from database", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
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
		// logger.info("Welcome home! The client locale is {}.", locale);
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
			
			List<String> site_VenList = new ArrayList<String>();
			String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.VENDOR FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR='"+ selectedVen + "' ";
			
			List<String> nodeTypeList = new ArrayList<String>();
			String strNodesType ="SELECT DISTINCT b.NODE_TYPE,b.WARE_ID,b.VENDOR FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' and b.WARE_ID='"+ selectedItem + "' and b.VENDOR='" + selectedVen + "' ";			
			
			List<String> nodeVenList = new ArrayList<String>();
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
						
						//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
						Query query = session.createSQLQuery(strSites);
						site_VenList = query.list();
						rtn.put("listVenSites",site_VenList);
						//System.out.println("list sites ==> "+ mapper.writeValueAsString(site_SuppList));										
						rtn.put("listVenSites", site_VenList);		
						
					} catch (Exception e) {
						logger.info("Error in retreiving sites Data from database", e);
						rtn.put("listVenSites", null);
					}
				}
				if (selectedItem != null) {
					try {
						strNodesType= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strNodesType);

						//System.out.println("final query nodes type"+mapper.writeValueAsString(strNodesType));
						Query query = session.createSQLQuery(strNodesType);
						nodeTypeList = query.list();
						rtn.put("listNodesType", nodeTypeList);	
						//System.out.println("list nodes type ==> "+ mapper.writeValueAsString(nodeTypeList));												
							
					} catch (Exception e) {
						logger.info("Error in retreiving node type Data from database", e);
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
						
						//System.out.println("final query nodes"+mapper.writeValueAsString(strNodes));
						Query query = session.createSQLQuery(strNodes);
						nodeVenList = query.list();
						rtn.put("listNodes", nodeVenList);
						//System.out.println("list nodes ==> "+ mapper.writeValueAsString(nodeSuppList));
						
						} catch (Exception e) {
						logger.info("Error in retreiving nodes Data from database", e);
						rtn.put("listNodes", null);
					}
				}
				if (selectedNodetType != null) {
					try {
						strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
						strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
						strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
					
						//System.out.println("final query cells 1 "+mapper.writeValueAsString(strCells1));
						//System.out.println("final query cells 2 "+mapper.writeValueAsString(strCells2));
						//System.out.println("final query cells 3 "+mapper.writeValueAsString(strCells3));
						
						Query query1 = session.createSQLQuery(strCells1);
						Query query2 = session.createSQLQuery(strCells2);
						Query query3 = session.createSQLQuery(strCells3);
						cellResult.addAll(query1.list());
						cellResult.addAll(query2.list());
						cellResult.addAll(query3.list());
						
						rtn.put("listCells",cellResult);
						//System.out.println("list cells ==> "+ mapper.writeValueAsString(cellResult));

						} catch (Exception e) {
						logger.info("Error in retreiving nodes Data from database", e);
						rtn.put("listCells", null);
					}
				}
			} catch (Exception e) {
				logger.info("Error in retreiving Data from database", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
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
		// logger.info("Welcome home! The client locale is {}.", locale);
		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
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
				
				List<String> site_SuppList = new ArrayList<String>();
				String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.SUPPLIER_ID,b.NODE_TYPE FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.SUPPLIER_ID='"+ selectedSupp + "' AND b.NODE_TYPE='" + SelectedNodeType + "' ";
				
				List<String> listNodeType = new ArrayList<String>();
				String strNodesType ="SELECT DISTINCT b.NODE_TYPE,b.SUPPLIER_ID FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' and b.SUPPLIER_ID='" + selectedSupp + "' ";			
				
				List<String> nodeSuppList = new ArrayList<String>();
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

							//System.out.println("final query nodes type"+mapper.writeValueAsString(strNodesType));
							Query query = session.createSQLQuery(strNodesType);
							listNodeType = query.list();
					
							if (listNodeType != null) {
								rtn.put("listNodeType", listNodeType);	
								//System.out.println("list nodes type ==> "+ mapper.writeValueAsString(listNodeType));												
									
							}
						} catch (Exception e) {
							logger.info("Error in retreiving nodes type Data from database", e);
							rtn.put("listNodeType", null);
						}
					}
	////////////////////////////////	
					if (selectedSupp != null && SelectedNodeType != null) {					
						try {
							strSites= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
							
							//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
							Query query = session.createSQLQuery(strSites);
							site_SuppList = query.list();
							rtn.put("listSuppSites",site_SuppList);
							//System.out.println("list sites ==> "+ mapper.writeValueAsString(site_SuppList));										
							rtn.put("listSuppSites", site_SuppList);		
		
						} catch (Exception e) {
							logger.info("Error in retreiving sites Data from database", e);
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
							
							//System.out.println("final query nodes"+mapper.writeValueAsString(strNodes));
							Query query = session.createSQLQuery(strNodes);
							nodeSuppList = query.list();
							rtn.put("listSuppNodes", nodeSuppList);
							//System.out.println("list nodes ==> "+ mapper.writeValueAsString(nodeSuppList));
				
						} catch (Exception e) {
							logger.info("Error in retreiving nodes Data from database", e);
							rtn.put("listSuppNodes", null);
						}
					}
	/////////////////////////////////
					if (selectedItem != null) {
						try {
							strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
							strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
							strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
						
							//System.out.println("final query cells 1 "+mapper.writeValueAsString(strCells1));
							//System.out.println("final query cells 2 "+mapper.writeValueAsString(strCells2));
							//System.out.println("final query cells 3 "+mapper.writeValueAsString(strCells3));
							
							Query query1 = session.createSQLQuery(strCells1);
							Query query2 = session.createSQLQuery(strCells2);
							Query query3 = session.createSQLQuery(strCells3);
							cellResult.addAll(query1.list());
							cellResult.addAll(query2.list());
							cellResult.addAll(query3.list());
							
							rtn.put("listCells",cellResult);
							//System.out.println("list cells ==> "+ mapper.writeValueAsString(cellResult));

						} catch (Exception e) {
							logger.info("Error in retreiving cells Data from database", e);
							rtn.put("listCells", null);
						}
					}
//////////////////////////////////					

				//String selectedNode = request.getParameter("selectedNode");
/*
				if (selectedNode != null) {
					try {
						List<Object[]> result = session.createSQLQuery(
								"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
										+ selectedNode + "'")
								.list();
						List<Object[]> result1 = session.createSQLQuery(
								"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
										+ selectedNode + "'")
								.list();
						List<Object[]> result2 = session.createSQLQuery(
								"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
										+ selectedNode + "'")
								.list();
*/

			} catch (Exception e) {
				logger.info("Error in retreiving Data from database", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
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
			// logger.info("Welcome home! The client locale is {}.", locale);
			Map<String, Object> rtn = new LinkedHashMap<>();
			ObjectMapper mapper = new ObjectMapper();
			Session session = null;
			Transaction tx = null;
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
					
					List<String> site_VenList = new ArrayList<String>();
					String strSites ="SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.VENDOR,b.NODE_TYPE FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR='"+ selectedVen + "' AND b.NODE_TYPE='" + SelectedNodeType + "' ";
					
					List<String> listNodeType = new ArrayList<String>();
					String strNodesType ="SELECT DISTINCT b.NODE_TYPE,b.VENDOR FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' and b.VENDOR='" + selectedVen + "' ";			
					
					List<String> nodeVenList = new ArrayList<String>();
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

								//System.out.println("final query nodes type"+mapper.writeValueAsString(strNodesType));
								Query query = session.createSQLQuery(strNodesType);
								listNodeType = query.list();
						
								if (listNodeType != null) {
									rtn.put("listNodeType", listNodeType);	
									//System.out.println("list nodes type ==> "+ mapper.writeValueAsString(listNodeType));												
										
								}
							} catch (Exception e) {
								logger.info("Error in retreiving nodes type Data from database", e);
								rtn.put("listNodeType", null);
							}
						}
		////////////////////////////////	
						if (selectedVen != null && SelectedNodeType != null) {					
							try {
								strSites= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strSites);
								
								//System.out.println("final query sites"+mapper.writeValueAsString(strSites));
								Query query = session.createSQLQuery(strSites);
								site_VenList = query.list();
								rtn.put("listVenSites",site_VenList);
								//System.out.println("list sites ==> "+ mapper.writeValueAsString(site_SuppList));										
								rtn.put("listVenSites", site_VenList);		
			
							} catch (Exception e) {
								logger.info("Error in retreiving sites Data from database", e);
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
								
								//System.out.println("final query nodes"+mapper.writeValueAsString(strNodes));
								Query query = session.createSQLQuery(strNodes);
								nodeVenList = query.list();
								rtn.put("listVenNodes", nodeVenList);
								//System.out.println("list nodes ==> "+ mapper.writeValueAsString(nodeSuppList));
					
							} catch (Exception e) {
								logger.info("Error in retreiving nodes Data from database", e);
								rtn.put("listVenNodes", null);
							}
						}
		/////////////////////////////////
						if (selectedItem != null) {
							try {
								strCells1= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells1);	
								strCells2= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells2);						
								strCells3= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strCells3);	
							
								//System.out.println("final query cells 1 "+mapper.writeValueAsString(strCells1));
								//System.out.println("final query cells 2 "+mapper.writeValueAsString(strCells2));
								//System.out.println("final query cells 3 "+mapper.writeValueAsString(strCells3));
								
								Query query1 = session.createSQLQuery(strCells1);
								Query query2 = session.createSQLQuery(strCells2);
								Query query3 = session.createSQLQuery(strCells3);
								cellResult.addAll(query1.list());
								cellResult.addAll(query2.list());
								cellResult.addAll(query3.list());
								
								rtn.put("listCells",cellResult);
								//System.out.println("list cells ==> "+ mapper.writeValueAsString(cellResult));

							} catch (Exception e) {
								logger.info("Error in retreiving cells Data from database", e);
								rtn.put("listCells", null);
							}
						}
//////////////////////////////////						

					//String selectedNode = request.getParameter("selectedNode");
	/*
					if (selectedNode != null) {
						try {
							List<Object[]> result = session.createSQLQuery(
									"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
											+ selectedNode + "'")
									.list();
							List<Object[]> result1 = session.createSQLQuery(
									"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
											+ selectedNode + "'")
									.list();
							List<Object[]> result2 = session.createSQLQuery(
									"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' and b.NODE_PK='"
											+ selectedNode + "'")
									.list();
	*/

				} catch (Exception e) {
					logger.info("Error in retreiving Data from database", e);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return rtn;
		}





	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.setAutoGrowCollectionLimit(1024);
	}
	
	


	
	

	

	

	
	@RequestMapping(value = "/GetSearchEngine", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSearch(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);
		Map<String, Object> map = new HashMap<String, Object>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
		}
		System.out.println("get search");
		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String param = request.getParameter("searchkey").toLowerCase();
			System.out.println("parameter " + param);
			Query q = session.createQuery(
					"from Supplier where lower(SUPPLIER_ID) like :param or upper(SUPPLIER_ID) like :param or lower(SUPPLIER_NAME) like :param or upper(SUPPLIER_NAME) like :param");
			q.setParameter("param", "%" + param + "%");
			q.setMaxResults(20);
			List<Supplier> supList = (List<Supplier>) q.list();
			List<String> globalList = new ArrayList<String>();
			if (param != null) {

				if (supList != null && supList.size() != 0) {
					System.out.println("not null");
					if (supList.size() > 15) {
						for (int i = 0; i < 10; i++) {
							System.out.println("for");
							globalList.add("Supplier id: " + supList.get(i).getSupplierID() + " Supplier name: "
									+ supList.get(i).getSupplierName());
						}
					} else {
						for (int i = 0; i < supList.size(); i++) {
							System.out.println("for");
							globalList.add("Supplier id: " + supList.get(i).getSupplierID() + " Supplier name: "
									+ supList.get(i).getSupplierName());
						}
					}

				}
				Query q2 = session.createQuery(
						"from Warehouse where lower(WARE_ID) like :param or upper(WARE_ID) like :param or lower(WARE_NAME) like :param or upper(WARE_NAME) like :param");
				q2.setParameter("param", "%" + param + "%");
				q2.setMaxResults(20);
				List<Warehouse> wareList = (List<Warehouse>) q2.list();
				if (wareList != null && wareList.size() != 0) {
					System.out.println("warehouse");
					if (wareList.size() > 15) {
						for (int i = 0; i < 10; i++) {
							System.out.println("for");
							globalList.add("Warehouse id: " + wareList.get(i).getWareID() + " Warehouse name: "
									+ wareList.get(i).getWarehouseName());
						}
					} else {
						for (int i = 0; i < wareList.size(); i++) {
							System.out.println("for");
							globalList.add("Warehouse ID: " + wareList.get(i).getWareID() + " Warehouse Name: "
									+ wareList.get(i).getWarehouseName());
						}
					}
				}

				Query q3 = session.createQuery(
						"from Item where lower(ITEM_CODE) like :param or upper(ITEM_CODE) like :param or lower(ITEM_NAME) like :param or upper(ITEM_NAME) like :param");
				q3.setParameter("param", "%" + param + "%");
				q3.setMaxResults(20);
				List<Item> itemList = (List<Item>) q3.list();
				if (itemList != null && itemList.size() != 0) {

					if (itemList.size() > 15) {
						for (int i = 0; i < 10; i++) {
							System.out.println("for");
							globalList.add("Item Code: " + itemList.get(i).getItemCode() + " Item Name: "
									+ itemList.get(i).getItemName());

						}
					} else {
						for (int i = 0; i < itemList.size(); i++) {
							System.out.println("for");
							globalList.add("Item code: " + itemList.get(i).getItemCode() + " Item name: "
									+ itemList.get(i).getItemName());

						}
					}

				}

				Query q4 = session
						.createQuery("from Warehouse where lower(SITE_ID) like :param or upper(SITE_ID) like :param");
				q4.setParameter("param", "%" + param + "%");
				q4.setMaxResults(20);
				List<Warehouse> siteList = (List<Warehouse>) q4.list();
				if (siteList != null && siteList.size() != 0) {
					if (siteList.size() > 15) {
						for (int i = 0; i < 10; i++) {
							System.out.println("for");
							globalList.add("Site ID: " + siteList.get(i).getWareSiteID());

						}
					} else {
						for (int i = 0; i < siteList.size(); i++) {
							System.out.println("for");
							globalList.add("Site ID: " + siteList.get(i).getWareSiteID());

						}
					}
				}
			}

			map.put("listSearch", globalList);

		} catch (Exception e) {
			logger.info("Error in retreiving  Data from database", e);
			map.put("listSearch", null);
		}

		finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}

		return map;
	}



	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SearchTree", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SearchTree(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				String searchTerm = request.getParameter("searchTerm");
				System.out.println("===>searchTerm	" + searchTerm);

				if (searchTerm != null) {

					Query AllSites = session.createSQLQuery("SELECT distinct b.SITE_ID,b.WARE_NAME,b.WARE_ID,"
							+ "(SELECT a.LATITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LATITUDE,"
							+ "(SELECT a.LONGITUDE from WAREHOUSE a where a.WARE_ID=b.WARE_ID) as LONGITUDE,"
							+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID  and w.ACTIVE_RECORD = '1') as countnodes,"
							+ "(select COUNT(*) FROM NODE_GCELL c where c.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countGcells,"
							+ "(select COUNT(*) FROM NODE_LCELL d where d.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countLcells, "
							+ "(select COUNT(*) FROM NODE_UCELL e where e.NODE_PK IN(select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID and o.ACTIVE_RECORD = '1')) as countUcells"
							+ " FROM WAREHOUSE b where b.WARE_ID!='0' and b.WARE_ID!='null'");
					List<Object[]> allsiteList = AllSites.list();
					System.out.println("SSSSSSSSSSSSSSSSSSSSSSSS/n" + allsiteList);
					rtn.put("listAllSites", allsiteList);
					//////
					Query Sites = session.createSQLQuery(
							"SELECT distinct SITE_ID,WARE_NAME,WARE_ID,LATITUDE,LONGITUDE FROM WAREHOUSE where LOWER(WARE_NAME) LIKE :searchTerm OR UPPER(WARE_NAME) LIKE :searchTerm");

					Sites.setParameter("searchTerm", searchTerm + "%");
					Sites.setMaxResults(20);
					List<Object[]> siteList = Sites.list();
					System.out.println("SSSSSSSSSSSSSSSSSSSSSSSS/n" + siteList);
					rtn.put("listSites", siteList);
					/////////
					Query Nodes = session.createSQLQuery(
							"SELECT Distinct NODE_PK,NODE_NAME,WARE_ID FROM NODE_ACTIVE WHERE ACTIVE_RECORD = '1' and LOWER(NODE_NAME) LIKE :searchTerm OR UPPER(NODE_NAME) LIKE :searchTerm ");
					Nodes.setParameter("searchTerm", searchTerm + "%");
					Nodes.setMaxResults(20);
					List<Object[]> nodeList = Nodes.list();
					System.out.println("NNNNNNNNNNNNNNN/n" + nodeList);
					rtn.put("listNodes", nodeList);
//////////////////
					Query query1 = session.createSQLQuery(
							"SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_GCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and LOWER(a.CELLNAME) LIKE :searchTerm OR UPPER(a.CELLNAME) LIKE :searchTerm");
					Query query2 = session.createSQLQuery(
							"SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_LCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and LOWER(a.CELLNAME) LIKE :searchTerm OR UPPER(a.CELLNAME) LIKE :searchTerm");
					Query query3 = session.createSQLQuery(
							"SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_UCELL a,NODE_ACTIVE b WHERE a.ACTIVE_RECORD = '1' and LOWER(a.CELLNAME) LIKE :searchTerm OR UPPER(a.CELLNAME) LIKE :searchTerm");
					List<Object[]> result = query1.setParameter("searchTerm", searchTerm + "%").list();
					List<Object[]> result1 = query2.setParameter("searchTerm", searchTerm + "%").list();
					List<Object[]> result2 = query3.setParameter("searchTerm", searchTerm + "%").list();
//result.setMaxResults(20);
//result1.setMaxResults(20);
//result2.setMaxResults(20);

					List<Object[]> cellResult = new ArrayList<Object[]>();

					cellResult.addAll(result);

					cellResult.addAll(result1);

					cellResult.addAll(result2);
					System.out.println("CCCCCCCCCCCCCCCCCCCCCCC/n" + cellResult);
					rtn.put("listCells", cellResult);
////////////////////////	

				}

			} catch (Exception e) {
				logger.info("Error in retreiving Data from database", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	
	


	
	// PO BOQ data retrieving
	@SuppressWarnings("unchecked")
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
			Object PoC = session.createSQLQuery(PO_Query).uniqueResult();
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
			Object PO_Amount = session.createSQLQuery(PO_Amount_Query).uniqueResult();
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
			Object PO_NET_Amount = session.createSQLQuery(PO_NET_Amount_Query).uniqueResult();
			BoqHM.put(POID == "" ? "PO Net Cost" : "PO Net Amount", String.valueOf(PO_NET_Amount));
			strEmpty="";
			strExist="";
			
			strEmpty="SELECT COUNT(DISTINCT a.WARE_ID) FROM AR_SITE a, ASSET_REGISTRY b where a.AR_ID = b.AR_ID ";
			strEmpty= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
			strExist= "Select COUNT(DISTINCT WARE_ID) FROM AR_SITE a, ASSET_REGISTRY b where a.AR_ID = b.AR_ID and b.PO_ID='" + POID + "' ";	
			strExist= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
			String Site_Query = POID == "" ? strEmpty : strExist;
			//System.out.println(Site_Query);
			Object SiteC = session.createSQLQuery(Site_Query).uniqueResult();
			//System.out.println("SiteC :::"+SiteC);
			strEmpty="";
			strExist="";
			
			strEmpty="SELECT COUNT(DISTINCT a.ITEM_CODE) FROM ASSET_REGISTRY a, AR_SITE b where a.AR_ID = b.AR_ID ";
			strEmpty= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
			strExist= "Select COUNT(DISTINCT a.ITEM_CODE) from ASSET_REGISTRY a, AR_SITE b where a.AR_ID = b.AR_ID and PO_ID='" + POID + "' ";	
			strExist= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
			String Item_Query = POID == "" ? strEmpty : strExist;
			//System.out.println(Item_Query);
			Object ItemC = session.createSQLQuery(Item_Query).uniqueResult();
			//System.out.println("ItemC :::"+ItemC);	
			strEmpty="";
			strExist="";
		
			BoqHM.put("Sites", String.valueOf(SiteC));
			BoqHM.put("Items", String.valueOf(ItemC));

			return BoqHM;
		} catch (Exception e) {
			logger.info("Error in retreiving PO BOQ data Data from database", e);
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
	}
	
	
	// PO BOQ data retrieving
		@SuppressWarnings("unchecked")
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
				Object Po = session.createSQLQuery(PO_Query).uniqueResult();
				strEmpty="";
				strExist="";	
			
				strEmpty="SELECT COUNT(DISTINCT a.WARE_ID) FROM AR_SITE a, ASSET_REGISTRY b where a.AR_ID = b.AR_ID ";
				strEmpty= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "Select SITE_NAME FROM AR_SITE a, ASSET_REGISTRY b where a.AR_ID = b.AR_ID and b.PO_ID='" + POID +"' ";	
				strExist= boqDomainVar ("b",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Site_Query = POID == "" ? strEmpty : strExist;
				//System.out.println(Site_Query);
				Object Site = session.createSQLQuery(Site_Query).uniqueResult();
				strEmpty="";
				strExist="";
				
				strEmpty="SELECT COUNT(DISTINCT a.ITEM_CODE) FROM ASSET_REGISTRY a, AR_SITE b where a.AR_ID = b.AR_ID ";
				strEmpty= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strEmpty);
				strExist= "Select COUNT(DISTINCT a.ITEM_CODE) from ASSET_REGISTRY a, AR_SITE b where a.AR_ID = b.AR_ID and a.PO_ID='" + POID + "' ";	
				strExist= boqDomainVar ("a",paramEnterprise,paramTransmission,paramAccess,paramCore,strExist);
				String Item_Query = POID == "" ? strEmpty : strExist;
				//System.out.println(Item_Query);
				Object Item = session.createSQLQuery(Item_Query).uniqueResult();
				strEmpty="";
				strExist="";

				BoqHM.put("PO", String.valueOf(Po));
				
				if(POID!="") {
					String PO_Amount_Query= "Select TOTAL_AMOUNT from PURCHASE_ORDER where PO_ID='" + POID  + "' ";
					//System.out.println(PO_Amount_Query);
					Object PO_Amount = session.createSQLQuery(PO_Amount_Query).uniqueResult();
					BoqHM.put("PO Amount", String.valueOf(PO_Amount));
	
	
					String PO_Net_Amount_Query= "Select NET_TOTAL_AMOUNT from PURCHASE_ORDER where PO_ID='" + POID  + "' ";
					//System.out.println(PO_Net_Amount_Query);
					Object PO_Net_Amount = session.createSQLQuery(PO_Net_Amount_Query).uniqueResult();
					BoqHM.put("PO Net Amount", String.valueOf(PO_Net_Amount));
				}						
				
				BoqHM.put("Site", String.valueOf(Site));
				BoqHM.put("Items", String.valueOf(Item));			
				return BoqHM;

			} catch (Exception e) {
				logger.info("Error in retreiving PO BOQ data Data from database", e);
				return null;
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
	
	
/*
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FindNearestPoints", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FindNearestPoints(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
	    ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getALMSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				//System.out.println("Inside FindNearestPoints");
				String noOfPoints = request.getParameter("noOfPoints");
				String closestDisRange = request.getParameter("closestDisRange");
				String closestLatPoint = request.getParameter("closestLatPoint");
				String closestLongPoint = request.getParameter("closestLongPoint");
				List<?>  manholeList= session.createSQLQuery("SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),DM_NAME FROM MANHOLE  ").list();
				List<Object[]> nearstPointsManhole = new ArrayList<Object[]>();
				//System.out.println("manholeList "+mapper.writeValueAsString(manholeList));
				nearstPointsManhole = findNearestArray(manholeList,Double.valueOf(closestLatPoint),Double.valueOf(closestLongPoint),Double.valueOf(closestDisRange),"Manhole",noOfPoints);
				
				List<?> handholeList = session.createSQLQuery("SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),DM_NAME FROM HANDHOLE").list();
				List<Object[]> nearstPointsHandhole = new ArrayList<Object[]>();
				nearstPointsHandhole = findNearestArray(handholeList,Double.valueOf(closestLatPoint),Double.valueOf(closestLongPoint),Double.valueOf(closestDisRange),"Handhole",noOfPoints);
				
				List<?> distribBoardList = session.createSQLQuery("SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY FROM DISTRIBUTION_BOARD")
							.list();
				List<Object[]> nearstPointsDistribBoard= new ArrayList<Object[]>();
				nearstPointsDistribBoard = findNearestArray(distribBoardList,Double.valueOf(closestLatPoint),Double.valueOf(closestLongPoint),Double.valueOf(closestDisRange),"DistribBoard",noOfPoints);
				
				List<Object[]> nearstPoints=new ArrayList<Object[]>();
				nearstPoints.addAll(nearstPointsManhole);
				nearstPoints.addAll(nearstPointsHandhole);
				nearstPoints.addAll(nearstPointsDistribBoard);
                
                String[] idsArray = findListId(nearstPoints,"all");
               // System.out.println("listIds "+mapper.writeValueAsString(idsArray));
				//Query fiberQuery=  session.createSQLQuery("SELECT SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,FIBER_CABLE_ID,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,FIBER_CABLE_NAME,PROJECT_ID,SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_TUBES,NUMBER_OF_STRANDS,LENGTH,DRAWING_TYPE,FIBER_NETWORK_LEVEL FROM FIBER_CABLES A WHERE SOURCE_ID IN (:param1) OR DESTINATION_ID IN (:param1)");
				Query fiberQuery=  session.createSQLQuery("SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL FROM FIBER_CABLES A LEFT  JOIN FIBER_AUXILIARY_POINTS D ON A.FIBER_CABLE_ID=D.FIBER_CABLE_ID WHERE A.SOURCE_ID IN (:param1) OR A.DESTINATION_ID IN (:param1) OR D.AUXILIARY_POINT_ID IN (:param1)");
                fiberQuery.setParameterList("param1",idsArray);
				List<Object> fiberList = fiberQuery.list();
				//System.out.println("fiberList "+mapper.writeValueAsString(fiberList));
				
				
				Query fiberAuxiliaryQuery = session.createSQLQuery("SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID IN (:param1) ORDER BY B.SEQ_SORTING ASC");
				fiberAuxiliaryQuery.setParameterList("param1",findListId(fiberList,"FiberCable"));
				List<Object> fiberAuxiliary_Data = fiberAuxiliaryQuery.list();
				//System.out.println("fiberAuxiliary_Data "+mapper.writeValueAsString(fiberAuxiliary_Data));
				
				Query fiberTubesQuery = session.createSQLQuery("SELECT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME FROM FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID AND b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1)");
				fiberTubesQuery.setParameterList("param1",idsArray);
				List<Object> fiberTubes = fiberTubesQuery.list();


				Query tubesAuxiliariesQuery = session.createSQLQuery("SELECT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID FROM TUBE_AUXILIARY_POINTS c,FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.TUBE_ID=c.TUBE_ID AND AUXILIARY_POINT_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC");
				tubesAuxiliariesQuery.setParameterList("param1",idsArray);
				List<Object> tubesAuxiliaries = tubesAuxiliariesQuery.list();
		
				Query fiberStrandsQuery = session.createSQLQuery("SELECT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE,b.DESTINATION,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID AND b.SOURCE IN (:param1) OR b.DESTINATION IN (:param1)");
				fiberStrandsQuery.setParameterList("param1",idsArray);
				List<Object> fiberStrands = fiberStrandsQuery.list();
				
				//Query strandsAuxiliariesQuery = session.createSQLQuery("SELECT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.AUXILIARY_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID AND AUXILIARY_POINT_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC ");
				//strandsAuxiliariesQuery.setParameterList("param1",idsArray);
				//List<Object> strandsAuxiliaries = strandsAuxiliariesQuery.list();
				
				rtn.put("nearstPointsManhole",nearstPointsManhole);	
				rtn.put("nearstPointsHandhole",nearstPointsHandhole);
				rtn.put("nearstPointsDistribBoard",nearstPointsDistribBoard);
				rtn.put("nearstPointsfiberCable",fiberList);
				rtn.put("nearstPointsfiberCableAuxiliaries",fiberAuxiliary_Data);
			} catch (Exception e) {
				logger.info("Error in retreiving  Data from database", e);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}
*/	


}
