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

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
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
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Logger;

@Controller
public class NetworkController {
	private static final Logger logger = Logger.getLogger(NetworkController.class.getName());
	private static ObjectMapper mapper = new ObjectMapper();
	private static StringWriter sw;
	private static String exceptionAsString;

	private static Session session = null;

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws JsonProcessingException
	 */

	@Autowired
	ALMSessions almsessions;

	@Autowired
	Notify notifications;

	private static String AppendQuery(String a, int[] arrayParam, String str) {
		if (arrayParam[0] == 1 || arrayParam[1] == 1 || arrayParam[2] == 1 || arrayParam[3] == 1) {
			str = str + " and ( ";

			if (arrayParam[0] == 1) {
				// System.out.println("ent ");
				str = str + a + ".DOMAIN ='Enterprise' ";
			}
			if (arrayParam[1] == 1) {
				if (arrayParam[0] == 0) {
					// System.out.println("trans");
					str = str + a + ".DOMAIN ='Transmission' ";
				} else {
					str = str + " or " + a + ".DOMAIN ='Transmission' ";
				}
			}
			if (arrayParam[2] == 1) {
				if (arrayParam[0] == 0 && arrayParam[1] == 0) {
					// System.out.println("acc ");
					str = str + a + ".DOMAIN ='RAN' ";
				} else {
					str = str + " or " + a + ".DOMAIN ='RAN' ";
				}
			}
			if (arrayParam[3] == 1) {
				if (arrayParam[0] == 0 && arrayParam[1] == 0 && arrayParam[2] == 0) {
					// System.out.println("core");
					str = str + a + ".DOMAIN ='Core' ";
				} else {
					str = str + "or " + a + ".DOMAIN ='Core' ";
				}
			}
			str = str + " ) ";
		}
		return str;
	}

	private String generateDateCondition(String parsingDate, String a) {
		if (parsingDate == null || parsingDate.equals("")) {
			return " AND " + a + ".ACTIVE_RECORD = '1' ";
		} else {
			return " AND TO_DATE(TO_CHAR(" + a
					+ ".PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('" + parsingDate
					+ "','YYYY-MM-DD HH:MI:SS AM') ";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Network_StNdCell", method = RequestMethod.GET)
	public String Network_StNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			double longitude = 0;
			double latitude = 0;
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				System.out.println("Site Node CEll Network***************");

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");
				String parsingDate = request.getParameter("parsingDate");

				int[] arrayParam = new int[4]; // Assuming you want to store 4 parameters
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE ";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID "
						+ generateDateCondition(parsingDate, "w");*/
				String strNodes = "SELECT a.NODE_PK,a.SITE_ID,a.NODE_NAME,a.NODE_TYPE,a.WARE_ID,"
						+ "(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
						+ generateDateCondition(parsingDate, "a");

				List<Object[]> cellResult = new ArrayList<Object[]>();
				String strCells1 = "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_2GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' "
						+ generateDateCondition(parsingDate, "b");
				String strCells2 = "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_4GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' "
						+ generateDateCondition(parsingDate, "b");
				String strCells3 = "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_3GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' "
						+ generateDateCondition(parsingDate, "b");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*strSites = AppendQuery("w", arrayParam, strSites);

					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);

					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);

					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/

					strSites = strSites
							+ "FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null "
							+ generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					//System.out.println("strSites "+strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites Data from database in method Network_StNdCell due to \n "
							+ exceptionAsString);
					logger.info("Error in retreiving Sites Data from database in method Network_StNdCell due to \n "
							+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}

				// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				try {
					strNodes = AppendQuery("b", arrayParam, strNodes);

					strNodes = strNodes
							+ ") as countGcells,(select count(*) from NODE_4GCELL c  where a.NODE_PK = c.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = AppendQuery("c", arrayParam, strNodes);

					strNodes = strNodes
							+ ") as countLcells,(select count(*) from NODE_3GCELL d  where a.NODE_PK = d.NODE_PK  "
							+ generateDateCondition(parsingDate, "a");
					strNodes = AppendQuery("d", arrayParam, strNodes);

					strNodes = strNodes + ") as countUcells,a.SUPPLIER_ID FROM NODE_ACTIVE a WHERE  ";
					if (parsingDate == null || parsingDate.equals("")) {
						strNodes = strNodes + " a.ACTIVE_RECORD = '1' ";
					} else {
						strNodes = strNodes
								+ " TO_DATE(TO_CHAR(a.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
								+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
					}
					strNodes = AppendQuery("a", arrayParam, strNodes);

					model.addAttribute("listNodes",
							mapper.writeValueAsString(session.createNativeQuery(strNodes).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Nodes Data from database in method Network_StNdCell due to \n "
							+ exceptionAsString);
					logger.info("Error in retreiving Nodes Data from database in method Network_StNdCell due to \n "
							+ exceptionAsString);
					model.addAttribute("listNodes", "null");
				}
				// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				try {
					strCells1 = AppendQuery("a", arrayParam, strCells1);
					strCells2 = AppendQuery("a", arrayParam, strCells2);
					strCells3 = AppendQuery("a", arrayParam, strCells3);

					cellResult.addAll(session.createNativeQuery(strCells1).list());
					cellResult.addAll(session.createNativeQuery(strCells2).list());
					cellResult.addAll(session.createNativeQuery(strCells3).list());

					model.addAttribute("listCells", mapper.writeValueAsString(cellResult));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Cells Data from database in method Network_StNdCell due to \n "
							+ exceptionAsString);
					logger.info("Error in retreiving Cells Data from database in method Network_StNdCell due to \n "
							+ exceptionAsString);
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
					logger.finest("Error in retreiving BOQ Data from database in method Network_StNdCell due to \n "
							+ exceptionAsString);
					logger.info("Error in retreiving BOQ Data from database in method Network_StNdCell due to \n "
							+ exceptionAsString);
					model.addAttribute("Long", null);
					model.addAttribute("Lat", null);
				}

				finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_StNdCell";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Network_StNdTypNdCell", method = RequestMethod.GET)
	public String Network_StNdTypNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			double longitude = 0;
			double latitude = 0;
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE ";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID "
						+ generateDateCondition(parsingDate, "w");*/

				String strNodes = "SELECT a.NODE_PK,a.SITE_ID,a.NODE_NAME,a.NODE_TYPE,a.WARE_ID,"
						+ "(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
						+ generateDateCondition(parsingDate, "a");

				List<Object[]> cellResult = new ArrayList<Object[]>();
				String strCells1 = "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_2GCELL a, NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' "
						+ generateDateCondition(parsingDate, "b");
				String strCells2 = "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_4GCELL a, NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' "
						+ generateDateCondition(parsingDate, "b");
				String strCells3 = "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_3GCELL a, NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID!= '0' "
						+ generateDateCondition(parsingDate, "b");

				String strNodesType = "SELECT DISTINCT a.NODE_TYPE,a.WARE_ID FROM NODE_ACTIVE a WHERE ";
				if (parsingDate == null || parsingDate.equals("")) {
					strNodesType = strNodesType + " a.ACTIVE_RECORD = '1' ";
				} else {
					strNodesType = strNodesType
							+ " TO_DATE(TO_CHAR(a.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
							+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
				}
				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*strSites = AppendQuery("w", arrayParam, strSites);
					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);
					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);
					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/
					strSites = strSites
							+ "FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null "
							+ generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					//System.out.println("strSites "+strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Sites Data from database in method Network_StNdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Sites Data from database in method Network_StNdTypNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}
				// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				try {
					strNodes = AppendQuery("b", arrayParam, strNodes);
					strNodes = strNodes
							+ ") as countGcells,(select count(*) from NODE_4GCELL c  where a.NODE_PK = c.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = AppendQuery("c", arrayParam, strNodes);
					strNodes = strNodes
							+ ") as countLcells,(select count(*) from NODE_3GCELL d  where a.NODE_PK = d.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = AppendQuery("d", arrayParam, strNodes);
					strNodes = strNodes + ") as countUcells,a.SUPPLIER_ID FROM NODE_ACTIVE a WHERE ";
					if (parsingDate == null || parsingDate.equals("")) {
						strNodes = strNodes + " a.ACTIVE_RECORD = '1' ";
					} else {
						strNodes = strNodes
								+ " TO_DATE(TO_CHAR(a.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
								+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
					}
					strNodes = AppendQuery("a", arrayParam, strNodes);

					model.addAttribute("listNodes",
							mapper.writeValueAsString(session.createNativeQuery(strNodes).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Nodes Data from database in method Network_StNdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Nodes Data from database in method Network_StNdTypNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listNodes", "null");
				}

				// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				try {
					strCells1 = AppendQuery("a", arrayParam, strCells1);
					strCells2 = AppendQuery("a", arrayParam, strCells2);
					strCells3 = AppendQuery("a", arrayParam, strCells3);

					cellResult.addAll(session.createNativeQuery(strCells1).list());
					cellResult.addAll(session.createNativeQuery(strCells2).list());
					cellResult.addAll(session.createNativeQuery(strCells3).list());

					model.addAttribute("listCells", mapper.writeValueAsString(cellResult));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Cells Data from database in method Network_StNdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Cells Data from database in method Network_StNdTypNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listCells", "null");
				}
				try {
					strNodesType = AppendQuery("a", arrayParam, strNodesType);

					model.addAttribute("listNodesType",
							mapper.writeValueAsString(session.createNativeQuery(strNodesType).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Node Tyoe Data from database in method Network_StNdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Node Type Data from database in method Network_StNdTypNdCell due to \n "
									+ exceptionAsString);
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
					logger.finest(
							"Error in retreiving BOQ Data from database in method Network_StNdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info("Error in retreiving BOQ Data from database in method Network_StNdTypNdCell due to \n "
							+ exceptionAsString);
					model.addAttribute("Long", null);
					model.addAttribute("Lat", null);
				}

				finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_StNdTypNdCell";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Network_NdTypStNdCell", method = RequestMethod.GET)
	public String Network_NdTypStNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			double longitude = 0;
			double latitude = 0;
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID "
						+ generateDateCondition(parsingDate, "w");*/

				String strNodesType = "SELECT DISTINCT b.NODE_TYPE,(select COUNT(*) from NODE_ACTIVE a  where a.NODE_TYPE=b.NODE_TYPE "
						+ generateDateCondition(parsingDate, "b");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*
					strSites = AppendQuery("w", arrayParam, strSites);
					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);
					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);
					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/
					strSites = strSites
							+ " FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null "
							+ generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					System.out.println("strSites "+strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Sites Data from database in method Network_NdTypStNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Sites Data from database in method Network_NdTypStNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}

				try {
					strNodesType = AppendQuery("a", arrayParam, strNodesType);
					strNodesType = strNodesType + ") as countNodes FROM NODE_ACTIVE b WHERE ";
					if (parsingDate == null || parsingDate.equals("")) {
						strNodesType = strNodesType + " b.ACTIVE_RECORD = '1' ";
					} else {
						strNodesType = strNodesType
								+ " TO_DATE(TO_CHAR(b.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
								+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
					}
					strNodesType = AppendQuery("b", arrayParam, strNodesType);

					model.addAttribute("listNodesType",
							mapper.writeValueAsString(session.createNativeQuery(strNodesType).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Node Type Data from database in method Network_NdTypStNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Node Type Data from database in method Network_NdTypStNdCell due to \n "
									+ exceptionAsString);
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
					logger.finest(
							"Error in retreiving BOQ Data from database in method Network_NdTypStNdCell due to \n "
									+ exceptionAsString);
					logger.info("Error in retreiving BOQ Data from database in method Network_NdTypStNdCell due to \n "
							+ exceptionAsString);
					model.addAttribute("Long", null);
					model.addAttribute("Lat", null);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_NdTypStNdCell";
		}
	}

	@RequestMapping(value = "/Network_SupStNdCell", method = RequestMethod.GET)
	public String Network_SupStNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE ";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID "
						+ generateDateCondition(parsingDate, "w");*/

				String strSup = "SELECT distinct a.SUPPLIER_ID,a.SUPPLIER_NAME FROM NODE_ACTIVE a where a.WARE_ID!='0' and a.WARE_ID!='null' "
						+ "and a.WARE_ID is not null and a.SUPPLIER_ID!='null' and a.SUPPLIER_ID is not null and a.SUPPLIER_ID!='0' "
						+ generateDateCondition(parsingDate, "a");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*strSites = AppendQuery("w", arrayParam, strSites);
					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);
					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);
					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/
					strSites = strSites
							+ "FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null AND b.SUPPLIER_ID!='null' and b.SUPPLIER_ID!='0' and b.SUPPLIER_ID is not null  "
							+ generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					//System.out.println("strSites "+strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Sites Data from database in method Network_SupStNdCell due to \n "
									+ exceptionAsString);
					logger.info("Error in retreiving Sites Data from database in method Network_SupStNdCell due to \n "
							+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}
				try {
					strSup = AppendQuery("a", arrayParam, strSup);
					model.addAttribute("listSupp", mapper.writeValueAsString(session.createNativeQuery(strSup).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Suppliers Data from database in method Network_SupStNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Suppliers Data from database in method Network_SupStNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSupp", "null");
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_SupStNdCell";
		}
	}

	@RequestMapping(value = "/Network_VnStNdCell", method = RequestMethod.GET)
	public String Network_VnStNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE ";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID "
						+ generateDateCondition(parsingDate, "w");*/

				String strVen = "SELECT distinct a.VENDOR FROM NODE_ACTIVE a where a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null "
						+ "and a.VENDOR!='null' and a.VENDOR is not null and a.VENDOR!='0' "
						+ generateDateCondition(parsingDate, "a");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*strSites = AppendQuery("w", arrayParam, strSites);
					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);
					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);
					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/
					strSites = strSites
							+ "FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null AND b.VENDOR!='null' and b.VENDOR!='0' and b.VENDOR is not null  "
							+ generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					System.out.println("strSites "+strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites Data from database in method Network_VnStNdCell due to \n "
							+ exceptionAsString);
					logger.info("Error in retreiving Sites Data from database in method Network_VnStNdCell due to \n "
							+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}

				try {
					strVen = AppendQuery("a", arrayParam, strVen);

					model.addAttribute("listVen", mapper.writeValueAsString(session.createNativeQuery(strVen).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Vendors Data from database in method Network_VnStNdCell due to \n "
									+ exceptionAsString);
					logger.info("Error in retreiving Vendors Data from database in method Network_VnStNdCell due to \n "
							+ exceptionAsString);
					model.addAttribute("listVen", "null");
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_VnStNdCell";
		}
	}

	@RequestMapping(value = "/Network_VnStNdTypNdCell", method = RequestMethod.GET)
	public String Network_VnStNdTypNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE ";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID "
						+ generateDateCondition(parsingDate, "w");*/

				String strVen = "SELECT distinct a.VENDOR FROM NODE_ACTIVE a where a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null and "
						+ "a.VENDOR!='null' and a.VENDOR is not null and a.VENDOR!='0' "
						+ generateDateCondition(parsingDate, "a");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*strSites = AppendQuery("w", arrayParam, strSites);
					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);
					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);
					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/
					strSites = strSites
							+ "FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR!='null' and b.VENDOR!='0' and b.VENDOR is not null  "
							+ generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					//System.out.println("strSites "+strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Sites Data from database in method Network_VnStNdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Sites Data from database in method Network_VnStNdTypNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}
				try {
					strVen = AppendQuery("a", arrayParam, strVen);
					model.addAttribute("listVen", mapper.writeValueAsString(session.createNativeQuery(strVen).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Vendors Data from database in method Network_VnStNdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Vendors Data from database in method Network_VnStNdTypNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listVen", "null");
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_VnStNdTypNdCell";
		} //
	}

	@RequestMapping(value = "/Network_SupStNdTypNdCell", method = RequestMethod.GET)
	public String Network_SupStNdTypNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE ";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID "
						+ generateDateCondition(parsingDate, "w");*/

				String strSup = "SELECT distinct a.SUPPLIER_ID,a.SUPPLIER_NAME FROM NODE_ACTIVE a where a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null "
						+ "and a.SUPPLIER_ID!='null' and a.SUPPLIER_ID is not null and a.SUPPLIER_ID!='0' "
						+ generateDateCondition(parsingDate, "a");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*strSites = AppendQuery("w", arrayParam, strSites);
					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);
					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);
					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/
					strSites = strSites
							+ "FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null "
							+ "AND b.SUPPLIER_ID!='null' and b.SUPPLIER_ID!='0' and b.SUPPLIER_ID is not null  "
							+ generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					//System.out.println("strSites "+strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Sites Data from database in method Network_SupStNdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Sites Data from database in method Network_SupStNdTypNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}
				try {
					strSup = AppendQuery("a", arrayParam, strSup);
					model.addAttribute("listSupp", mapper.writeValueAsString(session.createNativeQuery(strSup).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Suppliers Data from database in method Network_SupStNdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Suppliers Data from database in method Network_SupStNdTypNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSupp", "null");
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_SupStNdTypNdCell";
		}
	}

	@RequestMapping(value = "/Network_SupNdTypStNdCell", method = RequestMethod.GET)
	public String Network_SupNdTypStCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE ";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID "
						+ generateDateCondition(parsingDate, "w");*/

				String strSup = "SELECT distinct a.SUPPLIER_ID,a.SUPPLIER_NAME FROM NODE_ACTIVE a where a.WARE_ID!='0' and a.WARE_ID!='null' and "
						+ "a.WARE_ID is not null and a.SUPPLIER_ID!='0' and a.SUPPLIER_ID is not null and a.SUPPLIER_ID!='null' "
						+ generateDateCondition(parsingDate, "a");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*strSites = AppendQuery("w", arrayParam, strSites);
					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);
					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);
					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/
					strSites = strSites
							+ "FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null AND b.SUPPLIER_ID!='null' and b.SUPPLIER_ID!='0' "
							+ "and b.SUPPLIER_ID is not null  " + generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					//System.out.println("strSites "+strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Sites Data from database in method Network_SupNdTypStNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Sites Data from database in method Network_SupNdTypStNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}
				try {
					strSup = AppendQuery("a", arrayParam, strSup);
					model.addAttribute("listSupp", mapper.writeValueAsString(session.createNativeQuery(strSup).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Suppliers Data from database in method Network_SupNdTypStNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Suppliers Data from database in method Network_SupNdTypStNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSupp", "null");
				}

				finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_SupNdTypStNdCell";
		}
	}

	@RequestMapping(value = "/Network_VnNdTypStNdCell", method = RequestMethod.GET)
	public String Network_VnNdTypStCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE ";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID "
						+ generateDateCondition(parsingDate, "w");*/

				String strVen = "SELECT distinct a.VENDOR FROM NODE_ACTIVE a where a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null and "
						+ "a.VENDOR!='0' and a.VENDOR is not null and a.VENDOR!='null' "
						+ generateDateCondition(parsingDate, "a");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*strSites = AppendQuery("w", arrayParam, strSites);
					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);
					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);
					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/
					strSites = strSites
							+ " FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR!='null' and b.VENDOR!='0' and b.VENDOR is not null "
							+ generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					//System.out.println("strSites "+strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Sites Data from database in method Network_VnNdTypStNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Sites Data from database in method Network_VnNdTypStNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}
				try {
					strVen = AppendQuery("a", arrayParam, strVen);
					model.addAttribute("listVen", mapper.writeValueAsString(session.createNativeQuery(strVen).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Vendors Data from database in method Network_VnNdTypStNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Vendors Data from database in method Network_VnNdTypStNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listVen", "null");
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_VnNdTypStNdCell";
		}
	}

	@RequestMapping(value = "/Network_PoSiteItem", method = RequestMethod.GET)
	public String Network_PoSiteItem(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,a.LATITUDE,a.LONGITUDE ";

				String strPO = "SELECT distinct a.PO_ID FROM FIXED_ASSET_REGISTRY a,NODE_ACTIVE b, FAR_NODE c WHERE a.PO_ID!='0' and a.PO_ID is not null and a.PO_ID!='null' AND b.NODE_ID=c.NODE_ID and a.FAR_ID = c.FAR_ID "
						+ generateDateCondition(parsingDate, "b");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					
					/*strSites = strSites
							+ ") as countNodes,(select COUNT(*) from ASSET_REGISTRY j,NODE_ACTIVE a where j.AR_ID=b.AR_ID and j.PO_ID!='null' and j.PO_ID!='0' and j.PO_ID is not null AND j.NODE_ID=a.NODE_ID "
							+ generateDateCondition(parsingDate, "a");*/
					//strSites = AppendQuery("j", arrayParam, strSites);
					
					strSites = strSites
							+ "FROM FAR_SITE b,NODE_ACTIVE a where b.WARE_ID!='0' and b.WARE_ID is not null and b.WARE_ID!='null' "
							+ "AND a.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "a");
					strSites = AppendQuery("a", arrayParam, strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					System.out.println("after query ");
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites Data from database in method Network_PoSiteItem due to \n "
							+ exceptionAsString);
					logger.info("Error in retreiving Sites Data from database in method Network_PoSiteItem due to \n "
							+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}
				try {
					strPO = AppendQuery("a", arrayParam, strPO);
					model.addAttribute("listPO", mapper.writeValueAsString(session.createNativeQuery(strPO).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving PO Data from database in method Network_PoSiteItem due to \n "
							+ exceptionAsString);
					logger.info("Error in retreiving PO Data from database in method Network_PoSiteItem due to \n "
							+ exceptionAsString);
					model.addAttribute("listPO", "null");
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_PoSiteItem";
		}
	}
	
	@RequestMapping(value = "/Network_PoItemSite", method = RequestMethod.GET)
	public String Network_PoItemSite(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,a.LATITUDE,a.LONGITUDE";
						
				String strPO = "SELECT distinct a.PO_ID FROM FIXED_ASSET_REGISTRY a,NODE_ACTIVE b, FAR_NODE c WHERE a.PO_ID!='0' and a.PO_ID is not null and a.PO_ID!='null' AND b.NODE_ID=c.NODE_ID and a.FAR_ID = c.FAR_ID "
						+ generateDateCondition(parsingDate, "b");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					
					/*strSites = strSites
							+ ") as countNodes,(select COUNT(*) from ASSET_REGISTRY j,NODE_ACTIVE a where j.AR_ID=b.AR_ID and j.PO_ID!='null' and j.PO_ID!='0' and j.PO_ID is not null AND j.NODE_ID=a.NODE_ID "
							+ generateDateCondition(parsingDate, "a");
					strSites = AppendQuery("j", arrayParam, strSites);
					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);
					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);
					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/
					strSites = strSites
							+ " FROM FAR_SITE b,NODE_ACTIVE a where b.WARE_ID!='0' and b.WARE_ID is not null and b.WARE_ID!='null' "
							+ "AND a.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "a");
					strSites = AppendQuery("a", arrayParam, strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites Data from database in method Network_PoItemSite due to \n "
							+ exceptionAsString);
					logger.info("Error in retreiving Sites Data from database in method Network_PoItemSite due to \n "
							+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}
				try {
					strPO = AppendQuery("a", arrayParam, strPO);
					model.addAttribute("listPO", mapper.writeValueAsString(session.createNativeQuery(strPO).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving PO Data from database in method Network_PoItemSite due to \n "
							+ exceptionAsString);
					logger.info("Error in retreiving PO Data from database in method Network_PoItemSite due to \n "
							+ exceptionAsString);
					model.addAttribute("listPO", "null");
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_PoItemSite";
		}
	}
	@RequestMapping(value = "/Network_SitePoItem", method = RequestMethod.GET)
	public String Network_SitePoItem(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,a.LATITUDE,a.LONGITUDE";

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*strSites = strSites
							+ ") as countNodes,(select COUNT(*) from ASSET_REGISTRY j,NODE_ACTIVE a where j.AR_ID=b.AR_ID and j.PO_ID!='null' and j.PO_ID!='0' and j.PO_ID is not null AND j.NODE_ID=a.NODE_ID "
							+ generateDateCondition(parsingDate, "a");
					strSites = AppendQuery("j", arrayParam, strSites);
					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);
					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);
					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/
					strSites = strSites
							+ " FROM FAR_SITE b,NODE_ACTIVE a where b.WARE_ID!='0' and b.WARE_ID is not null and b.WARE_ID!='null' "
							+ "AND a.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "a");
					strSites = AppendQuery("a", arrayParam, strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Sites Data from database in method Network_SitePoItem due to \n "
							+ exceptionAsString);
					logger.info("Error in retreiving Sites Data from database in method Network_SitePoItem due to \n "
							+ exceptionAsString);
					model.addAttribute("listSites", "null");
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_SitePoItem";
		}
	}

	@RequestMapping(value = "/Network_NdTypNdCell", method = RequestMethod.GET)
	public String Network_NdTypNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strNodes = "SELECT b.SITE_ID,b.WARE_NAME,b.NODE_PK,b.LATITUDE,b.LONGITUDE,";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where ";
				if (parsingDate == null || parsingDate.equals("")) {
					strNodes = strNodes + " w.ACTIVE_RECORD = '1' ";
				} else {
					strNodes = strNodes
							+ " TO_DATE(TO_CHAR(w.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
							+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
				}*/

				String strNodesType = "SELECT DISTINCT b.NODE_TYPE,(select COUNT(*) from NODE_ACTIVE a  where a.NODE_TYPE=b.NODE_TYPE "
						+ generateDateCondition(parsingDate, "a");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*
					strNodes = AppendQuery("w", arrayParam, strNodes);
					strNodes = strNodes
							+ ") as countNodes,(select count(*) from NODE_2GCELL e  where b.NODE_PK = e.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strNodes = AppendQuery("e", arrayParam, strNodes);
					strNodes = strNodes
							+ ") as countGcells,(select count(*) from NODE_4GCELL c  where b.NODE_PK = c.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strNodes = AppendQuery("c", arrayParam, strNodes);
					strNodes = strNodes
							+ ") as countLcells,(select count(*) from NODE_3GCELL d  where b.NODE_PK = d.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strNodes = AppendQuery("d", arrayParam, strNodes);*/
					strNodes = strNodes
							+ "b.WARE_ID,b.NODE_NAME,b.NODE_TYPE FROM NODE_ACTIVE b WHERE ";
					if (parsingDate == null || parsingDate.equals("")) {
						strNodes = strNodes + " b.ACTIVE_RECORD = '1' ";
					} else {
						strNodes = strNodes
								+ " TO_DATE(TO_CHAR(b.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
								+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
					}
					strNodes = AppendQuery("b", arrayParam, strNodes);
					System.out.println("strNodes "+strNodes);
					model.addAttribute("listNodes",
							mapper.writeValueAsString(session.createNativeQuery(strNodes).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Nodes Data from database in method Network_NdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info("Error in retreiving Nodes Data from database in method Network_NdTypNdCell due to \n "
							+ exceptionAsString);
					model.addAttribute("listNodes", "null");
				}

				try {
					strNodesType = AppendQuery("a", arrayParam, strNodesType);
					strNodesType = strNodesType + ") as countNodes FROM NODE_ACTIVE b WHERE ";
					if (parsingDate == null || parsingDate.equals("")) {
						strNodesType = strNodesType + " b.ACTIVE_RECORD = '1' ";
					} else {
						strNodesType = strNodesType
								+ " TO_DATE(TO_CHAR(b.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
								+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
					}
					strNodesType = AppendQuery("b", arrayParam, strNodesType);

					model.addAttribute("listNodesType",
							mapper.writeValueAsString(session.createNativeQuery(strNodesType).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Node Type Data from database in method Network_NdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Node Type Data from database in method Network_NdTypNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listNodesType", "null");
				}

				finally {
					if (session != null && session.isOpen()) {
						session.close();
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
			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strNodes = "SELECT b.SITE_ID,b.WARE_NAME,b.NODE_PK,b.LATITUDE,b.LONGITUDE,"
						+ "(select COUNT(*) from NODE_ACTIVE w where ";
				if (parsingDate == null || parsingDate.equals("")) {
					strNodes = strNodes + " w.ACTIVE_RECORD = '1' ";
				} else {
					strNodes = strNodes
							+ " TO_DATE(TO_CHAR(w.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
							+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
				}

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}

					strNodes = AppendQuery("w", arrayParam, strNodes);
					strNodes = strNodes
							+ ") as countNodes,(select count(*) from NODE_2GCELL e  where b.NODE_PK = e.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strNodes = AppendQuery("e", arrayParam, strNodes);
					strNodes = strNodes
							+ ") as countGCells,(select count(*) from NODE_4GCELL c  where b.NODE_PK = c.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strNodes = AppendQuery("c", arrayParam, strNodes);
					strNodes = strNodes
							+ ") as countLCells,(select count(*) from NODE_3GCELL d  where b.NODE_PK = d.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strNodes = AppendQuery("d", arrayParam, strNodes);
					strNodes = strNodes
							+ ") as countUCells,b.WARE_ID,b.NODE_NAME,b.NODE_TYPE,b.SUPPLIER_ID FROM NODE_ACTIVE b WHERE ";
					if (parsingDate == null || parsingDate.equals("")) {
						strNodes = strNodes + " b.ACTIVE_RECORD = '1' ";
					} else {
						strNodes = strNodes
								+ " TO_DATE(TO_CHAR(b.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
								+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
					}
					strNodes = AppendQuery("b", arrayParam, strNodes);

					model.addAttribute("listNodes",
							mapper.writeValueAsString(session.createNativeQuery(strNodes).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Nodes Data from database in method Network_Node due to \n "
							+ exceptionAsString);
					logger.info("Error in retreiving Nodes Data from database in method Network_Node due to \n "
							+ exceptionAsString);
					model.addAttribute("listNodes", "null");
				} finally {
					if (session != null && session.isOpen()) {
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
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {
				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				List<Object[]> cellResult = new ArrayList<Object[]>();
				String strCells1 = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,j.GCELL_ID,b.LATITUDE,b.LONGITUDE,"
						+ "(select COUNT(*) from NODE_ACTIVE w where ";
				if (parsingDate == null || parsingDate.equals("")) {
					strCells1 = strCells1 + " w.ACTIVE_RECORD = '1' ";
				} else {
					strCells1 = strCells1
							+ " TO_DATE(TO_CHAR(w.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
							+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
				}

				String strCells2 = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,i.LCELL_ID,b.LATITUDE,b.LONGITUDE,"
						+ "(select COUNT(*) from NODE_ACTIVE w where ";
				if (parsingDate == null || parsingDate.equals("")) {
					strCells2 = strCells2 + " w.ACTIVE_RECORD = '1' ";
				} else {
					strCells2 = strCells2
							+ " TO_DATE(TO_CHAR(w.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
							+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
				}

				String strCells3 = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,k.UCELL_ID,b.LATITUDE,b.LONGITUDE,"
						+ "(select COUNT(*) from NODE_ACTIVE w where ";
				if (parsingDate == null || parsingDate.equals("")) {
					strCells3 = strCells3 + " w.ACTIVE_RECORD = '1' ";
				} else {
					strCells3 = strCells3
							+ " TO_DATE(TO_CHAR(w.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
							+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
				}

				if (enterprise != null && !enterprise.equals("null")) {
					arrayParam[0] = 1;
					model.addAttribute("EnterpriseBtn", arrayParam[0]);
				}
				if (transmission != null && !transmission.equals("null")) {
					arrayParam[1] = 1;
					model.addAttribute("transmBtn", arrayParam[1]);
				}
				if (RAN != null && !RAN.equals("null")) {
					arrayParam[2] = 1;
					model.addAttribute("RANDBtn", arrayParam[2]);
				}
				if (core != null && !core.equals("null")) {
					arrayParam[3] = 1;
					model.addAttribute("CoreBtn", arrayParam[3]);
				}
				try {
					notifications.headerNotifications(session, model);
					strCells1 = AppendQuery("w", arrayParam, strCells1);
					strCells1 = strCells1
							+ ") as countNodes,(select count(*) from NODE_2GCELL e  where b.NODE_PK = e.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strCells1 = AppendQuery("e", arrayParam, strCells1);
					strCells1 = strCells1
							+ ") as countGCells,(select count(*) from NODE_4GCELL c  where b.NODE_PK = c.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strCells1 = AppendQuery("c", arrayParam, strCells1);
					strCells1 = strCells1
							+ ") as countLCells,(select count(*) from NODE_3GCELL d  where b.NODE_PK = d.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strCells1 = AppendQuery("d", arrayParam, strCells1);
					strCells1 = strCells1
							+ ") as countUCells,j.NODE_PK,j.CELLNAME,b.WARE_ID,b.NODE_NAME FROM NODE_ACTIVE b "
							+ "LEFT JOIN NODE_2GCELL j ON b.NODE_PK = j.NODE_PK WHERE b.NODE_PK = j.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strCells1 = AppendQuery("j", arrayParam, strCells1);

					strCells2 = AppendQuery("w", arrayParam, strCells2);
					strCells2 = strCells2
							+ ") as countNodes,(select count(*) from NODE_2GCELL e  where b.NODE_PK = e.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strCells2 = AppendQuery("e", arrayParam, strCells2);
					strCells2 = strCells2
							+ ") as countGCells,(select count(*) from NODE_4GCELL c  where b.NODE_PK = c.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strCells2 = AppendQuery("c", arrayParam, strCells2);
					strCells2 = strCells2
							+ ") as countLCells,(select count(*) from NODE_3GCELL d  where b.NODE_PK = d.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strCells2 = AppendQuery("d", arrayParam, strCells2);
					strCells2 = strCells2
							+ ") as countUCells,i.NODE_PK,i.CELLNAME,b.WARE_ID,b.NODE_NAME FROM NODE_ACTIVE b "
							+ "LEFT JOIN NODE_4GCELL i ON b.NODE_PK = i.NODE_PK WHERE b.NODE_PK = i.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strCells2 = AppendQuery("i", arrayParam, strCells2);

					strCells3 = AppendQuery("w", arrayParam, strCells3);
					strCells3 = strCells3
							+ ") as countNodes,(select count(*) from NODE_2GCELL e  where b.NODE_PK = e.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strCells3 = AppendQuery("e", arrayParam, strCells3);
					strCells3 = strCells3
							+ ") as countGCells,(select count(*) from NODE_4GCELL c  where b.NODE_PK = c.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strCells3 = AppendQuery("c", arrayParam, strCells3);
					strCells3 = strCells3
							+ ") as countLCells,(select count(*) from NODE_3GCELL d  where b.NODE_PK = d.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strCells3 = AppendQuery("d", arrayParam, strCells3);
					strCells3 = strCells3
							+ ") as countUCells,k.NODE_PK,k.CELLNAME,b.WARE_ID,b.NODE_NAME FROM NODE_ACTIVE b "
							+ "LEFT JOIN NODE_3GCELL k ON b.NODE_PK = k.NODE_PK WHERE b.NODE_PK = k.NODE_PK "
							+ generateDateCondition(parsingDate, "b");
					strCells3 = AppendQuery("k", arrayParam, strCells3);
					
					cellResult.addAll(session.createNativeQuery(strCells1).list());
					cellResult.addAll(session.createNativeQuery(strCells2).list());
					cellResult.addAll(session.createNativeQuery(strCells3).list());
					
					
					model.addAttribute("listCells", mapper.writeValueAsString(cellResult));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in retreiving Cells Data from database in method Network_Cell due to \n "
							+ exceptionAsString);
					logger.info("Error in retreiving Cells Data from database in method Network_Cell due to \n "
							+ exceptionAsString);
					model.addAttribute("listCells", "null");
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
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
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			String selectedVen = request.getParameter("selectedVen");
			String selectedItem = request.getParameter("selectedItem");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("date......"+parsingDate);

			String strSites = "SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.VENDOR FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR='"
					+ selectedVen + "' " + generateDateCondition(parsingDate, "b");

			String strNodes = "SELECT a.NODE_PK,a.NODE_TYPE,a.NODE_NAME,a.WARE_ID,a.VENDOR,"
					+ "(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
					+ generateDateCondition(parsingDate, "a");

			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 = "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_2GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.VENDOR='" + selectedVen + "' " + generateDateCondition(parsingDate, "b");
			String strCells2 = "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_4GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.VENDOR='" + selectedVen + "' " + generateDateCondition(parsingDate, "b");
			String strCells3 = "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_3GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.VENDOR='" + selectedVen + "' " + generateDateCondition(parsingDate, "b");

			try {
				notifications.headerNotifications(session, model);
				if (selectedVen != null) {
					strSites = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strSites);

					rtn.put("listVenSites", session.createNativeQuery(strSites).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Sites Data from database in method FindOnClick_VenSiteNodeCell due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Sites Data from database in method FindOnClick_VenSiteNodeCell due to \n "
								+ exceptionAsString);
				rtn.put("listVenSites", null);
			}
			try {
				if (selectedItem != null) {
					strCells1 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells1);
					strCells2 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells2);
					strCells3 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells3);

					cellResult.addAll(session.createNativeQuery(strCells1).list());
					cellResult.addAll(session.createNativeQuery(strCells2).list());
					cellResult.addAll(session.createNativeQuery(strCells3).list());

					rtn.put("listCells", cellResult);
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Cells Data from database in method FindOnClick_VenSiteNodeCell due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Cells Data from database in method FindOnClick_VenSiteNodeCell due to \n "
								+ exceptionAsString);
				rtn.put("listCells", null);
			}
			try {
				if (selectedItem != null) {
					strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes
							+ ") as countNodes,(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes
							+ ") as countGcells,(select count(*) from NODE_4GCELL c  where a.NODE_PK = c.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("c", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes
							+ ") as countLcells,(select count(*) from NODE_3GCELL d  where a.NODE_PK = d.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("d", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.WARE_ID='" + selectedItem + "' "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					rtn.put("listVenNodes", session.createNativeQuery(strNodes).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Nodes Data from database in method FindOnClick_VenSiteNodeCell due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Nodes Data from database in method FindOnClick_VenSiteNodeCell due to \n "
								+ exceptionAsString);
				rtn.put("listVenNodes", null);
			} finally {
				if (session != null && session.isOpen()) {
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
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			String selectedSupp = request.getParameter("selectedSupp");
			String selectedItem = request.getParameter("selectedItem");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");

			String strSites = "SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.SUPPLIER_ID FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.SUPPLIER_ID='"
					+ selectedSupp + "' " + generateDateCondition(parsingDate, "b");
			String strNodes = "SELECT a.NODE_PK,a.NODE_TYPE,a.NODE_NAME,a.WARE_ID,a.SUPPLIER_ID,"
					+ "(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
					+ generateDateCondition(parsingDate, "a");

			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 = "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_2GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' "
					+ generateDateCondition(parsingDate, "b");
			String strCells2 = "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_4GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' "
					+ generateDateCondition(parsingDate, "b");
			String strCells3 = "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_3GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' "
					+ generateDateCondition(parsingDate, "b");

			try {
				notifications.headerNotifications(session, model);
				if (selectedSupp != null) {
					strSites = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strSites);
					rtn.put("listSuppSites", session.createNativeQuery(strSites).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Sites Data from database in method FindOnClick_SuppSiteNodeCell due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Sites Data from database in method FindOnClick_SuppSiteNodeCell due to \n "
								+ exceptionAsString);
				rtn.put("listSuppSites", null);
			}
			try {
				if (selectedItem != null) {
					strCells1 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells1);
					strCells2 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells2);
					strCells3 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells3);

					cellResult.addAll(session.createNativeQuery(strCells1).list());
					cellResult.addAll(session.createNativeQuery(strCells2).list());
					cellResult.addAll(session.createNativeQuery(strCells3).list());

					rtn.put("listCells", cellResult);
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Cells Data from database in method FindOnClick_SuppSiteNodeCell due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Cells Data from database in method FindOnClick_SuppSiteNodeCell due to \n "
								+ exceptionAsString);
				rtn.put("listCells", null);
			}

			try {
				if (selectedItem != null) {
					strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes
							+ ") as countNodes,(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes
							+ ") as countGcells,(select count(*) from NODE_4GCELL c  where a.NODE_PK = c.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("c", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes
							+ ") as countLcells,(select count(*) from NODE_3GCELL d  where a.NODE_PK = d.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("d", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.WARE_ID='" + selectedItem + "' "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);

					rtn.put("listSuppNodes", session.createNativeQuery(strNodes).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Nodes Data from database in method FindOnClick_SuppSiteNodeCell due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Nodes Data from database in method FindOnClick_SuppSiteNodeCell due to \n "
								+ exceptionAsString);
				rtn.put("listSuppNodes", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/Network_NdTypSupStNdCell", method = RequestMethod.GET)
	public String Network_NdTypSupStNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE ";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID "
						+ generateDateCondition(parsingDate, "w");*/

				String strNodesType = "SELECT DISTINCT NODE_TYPE,(select COUNT(*) from NODE_ACTIVE a  where a.NODE_TYPE=b.NODE_TYPE "
						+ generateDateCondition(parsingDate, "a");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*strSites = AppendQuery("w", arrayParam, strSites);
					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);
					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);
					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/
					strSites = strSites
							+ "FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null AND b.SUPPLIER_ID!='null' and b.SUPPLIER_ID!='0' and b.SUPPLIER_ID is not null "
							+ generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					System.out.println("strSites "+strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Sites Data from database in method Network_NdTypSupStNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Sites Data from database in method Network_NdTypSupStNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}
				// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				try {
					strNodesType = AppendQuery("a", arrayParam, strNodesType);
					strNodesType = strNodesType + ") as countNodes FROM NODE_ACTIVE b WHERE b.ACTIVE_RECORD = '1' ";
					strNodesType = AppendQuery("b", arrayParam, strNodesType);

					model.addAttribute("listNodesType",
							mapper.writeValueAsString(session.createNativeQuery(strNodesType).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Node Type Data from database in method Network_NdTypSupStNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Node Type Data from database in method Network_NdTypSupStNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listNodesType", null);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_NdTypSupStNdCell";
		}
	}

	@RequestMapping(value = "/Network_NdTypVenStNdCell", method = RequestMethod.GET)
	public String Network_NdTypVenStNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {

				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				System.out.println("parsingDate..." + parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,LATITUDE,LONGITUDE ";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID "
						+ generateDateCondition(parsingDate, "w");*/

				String strNodesType = "SELECT DISTINCT NODE_TYPE,(select COUNT(*) from NODE_ACTIVE a  where a.NODE_TYPE=b.NODE_TYPE "
						+ generateDateCondition(parsingDate, "a");

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					/*
					strSites = AppendQuery("w", arrayParam, strSites);
					strSites = strSites
							+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("c", arrayParam, strSites);
					strSites = strSites
							+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("d", arrayParam, strSites);
					strSites = strSites
							+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK  from NODE_ACTIVE o where o.WARE_ID=b.WARE_ID "
							+ generateDateCondition(parsingDate, "o");
					strSites = AppendQuery("o", arrayParam, strSites);
					strSites = strSites + " ) ";
					strSites = AppendQuery("e", arrayParam, strSites);*/
					strSites = strSites
							+ "FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR!='null' and b.VENDOR!='0' and b.VENDOR is not null "
							+ generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					
					System.out.println("strSites "+strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Sites Data from database in method Network_NdTypVenStNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Sites Data from database in method Network_NdTypVenStNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}
				// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				try {
					strNodesType = AppendQuery("a", arrayParam, strNodesType);
					strNodesType = strNodesType + ") as countNodes FROM NODE_ACTIVE b WHERE ";
					if (parsingDate == null || parsingDate.equals("")) {
						strNodesType = strNodesType + " b.ACTIVE_RECORD = '1' ";
					} else {
						strNodesType = strNodesType
								+ " TO_DATE(TO_CHAR(b.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('"
								+ parsingDate + "','YYYY-MM-DD HH:MI:SS AM') ";
					}
					strNodesType = AppendQuery("b", arrayParam, strNodesType);

					model.addAttribute("listNodesType",
							mapper.writeValueAsString(session.createNativeQuery(strNodesType).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Node Type Data from database in method Network_NdTypVenStNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Node Type Data from database in method Network_NdTypVenStNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listNodesType", null);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_NdTypVenStNdCell";
		}
	}

	// retrieve suupliers data when supplier is clicked in
	// Node type -Supplier-site-node-cell method
	@RequestMapping(value = "/findNodeTypeSupSiteNodeCell_Sup", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findNodeTypeSupSiteNodeCell_Sup(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			String selectedNodetType = request.getParameter("selectedNodetType");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strSupp = "SELECT distinct a.SUPPLIER_ID,a.SUPPLIER_NAME,a.NODE_TYPE FROM NODE_ACTIVE a where a.NODE_TYPE='"
					+ selectedNodetType + "' and "
					+ "a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null and a.SUPPLIER_ID!='null' and a.SUPPLIER_ID is not null and a.SUPPLIER_ID!='0' "
					+ generateDateCondition(parsingDate, "a");

			try {
				notifications.headerNotifications(session, model);
				if (selectedNodetType != null) {
					strSupp = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strSupp);
					rtn.put("listSuppliers", session.createNativeQuery(strSupp).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Sites Data from database in method findNodeTypeSupSiteNodeCell_Sup due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Sites Data from database in method findNodeTypeSupSiteNodeCell_Sup due to \n "
								+ exceptionAsString);
				rtn.put("listSuppSites", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	// retrieve suppliers data when supplier is clicked in
	// Node type -Supplier-site-node-cell method
	@RequestMapping(value = "/findNodeTypeSupSiteNodeCell_Site", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findNodeTypeSupSiteNodeCell_Site(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			String SelectedNodeType = request.getParameter("SelectedNodeType");
			String selectedSupp = request.getParameter("selectedSupp");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strSites = "SELECT distinct b.WARE_NAME,b.WARE_ID,b.SUPPLIER_ID,b.NODE_TYPE FROM NODE_ACTIVE b "
					+ "where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.SUPPLIER_ID='"
					+ selectedSupp + "' AND b.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateCondition(parsingDate, "b");

			try {
				notifications.headerNotifications(session, model);
				strSites = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strSites);
				rtn.put("listSuppSites", session.createNativeQuery(strSites).list());
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Sites Data from database in method findNodeTypeSupSiteNodeCell_Site due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Sites Data from database in method findNodeTypeSupSiteNodeCell_Site due to \n "
								+ exceptionAsString);
				rtn.put("listSuppSites", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	// retrieve suupliers data when supplier is clicked in
	// Node type -Supplier-site-node-cell method
	@RequestMapping(value = "/findNodeTypeVenSiteNodeCell_Site", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findNodeTypeVenSiteNodeCell_Site(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			String SelectedNodeType = request.getParameter("SelectedNodeType");
			String selectedVen = request.getParameter("selectedVen");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strSites = "SELECT distinct b.WARE_NAME,b.WARE_ID,b.VENDOR,b.NODE_TYPE FROM NODE_ACTIVE b "
					+ "where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR='"
					+ selectedVen + "' AND b.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateCondition(parsingDate, "b");

			try {
				notifications.headerNotifications(session, model);
				strSites = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strSites);
				rtn.put("listVenSites", session.createNativeQuery(strSites).list());
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Sites Data from database in method findNodeTypeVenSiteNodeCell_Site due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Sites Data from database in method findNodeTypeVenSiteNodeCell_Site due to \n "
								+ exceptionAsString);
				rtn.put("listVenSites", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	// retrieve vendors data when vendor is clicked in
	// Node type -vendors-site-node-cell method
	@RequestMapping(value = "/findNodeTypeVenSiteNodeCell_Ven", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findNodeTypeVenSiteNodeCell_Ven(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			String selectedNodetType = request.getParameter("selectedNodetType");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strVen = "SELECT distinct a.VENDOR,a.NODE_TYPE FROM NODE_ACTIVE a where a.NODE_TYPE='"
					+ selectedNodetType + "' and "
					+ "a.WARE_ID!='0' and a.WARE_ID!='null' and a.WARE_ID is not null and a.VENDOR!='null' and a.VENDOR is not null and a.VENDOR!='0' "
					+ generateDateCondition(parsingDate, "a");

			try {
				notifications.headerNotifications(session, model);
				if (selectedNodetType != null) {
					strVen = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strVen);
					rtn.put("listVendors", session.createNativeQuery(strVen).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Sites Data from database in method findNodeTypeSupSiteNodeCell_Sup due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Sites Data from database in method findNodeTypeSupSiteNodeCell_Sup due to \n "
								+ exceptionAsString);
				rtn.put("listVenSites", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	// retrieve vendors data when vendor is clicked in
	// Node type -vendors-site-node-cell method
	@RequestMapping(value = "/findStSupNdTypNdCell_Ven", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findStSupNdTypNdCell_Ven(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			String siteId = request.getParameter("siteId");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strSup = "SELECT distinct a.SUPPLIER_ID,a.SUPPLIER_NAME,a.WARE_ID FROM NODE_ACTIVE a where a.WARE_ID='"
					+ siteId + "' AND a.WARE_ID!='0' and a.WARE_ID!='null' and "
					+ "a.WARE_ID is not null and a.SUPPLIER_ID!='null' and a.SUPPLIER_ID is not null and a.SUPPLIER_ID!='0' "
					+ generateDateCondition(parsingDate, "a");

			try {
				notifications.headerNotifications(session, model);
				if (siteId != null) {
					strSup = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strSup);
					rtn.put("listSuppliers", session.createNativeQuery(strSup).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Suppliers Data from database in method findStSupNdTypNdCell_Ven due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Suppliers Data from database in method findStSupNdTypNdCell_Ven due to \n "
								+ exceptionAsString);
				rtn.put("listVenSites", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	// retrieve vendors data when vendor is clicked in
	// Node type -vendors-site-node-cell method
	@RequestMapping(value = "/findStVenNdTypNdCell_Ven", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findStVenNdTypNdCell_Ven(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			String siteId = request.getParameter("siteId");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strVen = "SELECT distinct a.VENDOR,a.WARE_ID FROM NODE_ACTIVE a where a.WARE_ID='" + siteId
					+ "' and a.WARE_ID!='0' and a.WARE_ID!='null' "
					+ "and a.WARE_ID is not null and a.VENDOR!='null' and a.VENDOR is not null and a.VENDOR!='0' "
					+ generateDateCondition(parsingDate, "a");

			try {
				notifications.headerNotifications(session, model);
				if (siteId != null) {
					strVen = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strVen);
					rtn.put("listVendors", session.createNativeQuery(strVen).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Vendors Data from database in method findStVenNdTypNdCell_Ven due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Vendors Data from database in method findStVenNdTypNdCell_Ven due to \n "
								+ exceptionAsString);
				rtn.put("listVenSites", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	// retrieve sites/nodes/cells data when supplier is clicked in
	// Supplier-site-node-cell method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FindOnClick_NdTSupSiteNodeCell_NodeCell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> Find_NdTSupSiteNodeCell_NodeCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			String selectedSupp = request.getParameter("selectedSupp");
			String selectedItem = request.getParameter("selectedItem");
			String SelectedNodeType = request.getParameter("SelectedNodeType");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strNodes = "SELECT a.NODE_PK,a.NODE_TYPE,a.NODE_NAME,a.WARE_ID,a.SUPPLIER_ID,"
					+ "(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
					+ generateDateCondition(parsingDate, "a");

			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 = "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_2GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' and b.NODE_TYPE='" + SelectedNodeType
					+ "' " + generateDateCondition(parsingDate, "b");
			String strCells2 = "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_4GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' and b.NODE_TYPE='" + SelectedNodeType
					+ "' " + generateDateCondition(parsingDate, "b");
			String strCells3 = "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_3GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' and b.NODE_TYPE='" + SelectedNodeType
					+ "' " + generateDateCondition(parsingDate, "b");

			try {
				notifications.headerNotifications(session, model);
				if (selectedItem != null) {
					strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes
							+ ") as countNodes,(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes
							+ ") as countGcells,(select count(*) from NODE_4GCELL c  where a.NODE_PK = c.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("c", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes
							+ ") as countLcells,(select count(*) from NODE_3GCELL d  where a.NODE_PK = d.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("d", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.WARE_ID='" + selectedItem
							+ "' and a.SUPPLIER_ID='" + selectedSupp + "' and a.NODE_TYPE='" + SelectedNodeType + "' "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);

					rtn.put("listSuppNodes", session.createNativeQuery(strNodes).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Nodes Data from database in method FindOnClick_NdTSupSiteNodeCell_NodeCell due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Nodes Data from database in method FindOnClick_NdTSupSiteNodeCell_NodeCell due to \n "
								+ exceptionAsString);
				rtn.put("listSuppNodes", null);
			}

			try {
				if (selectedItem != null) {
					strCells1 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells1);
					strCells2 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells2);
					strCells3 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells3);

					cellResult.addAll(session.createNativeQuery(strCells1).list());
					cellResult.addAll(session.createNativeQuery(strCells2).list());
					cellResult.addAll(session.createNativeQuery(strCells3).list());

					rtn.put("listCells", cellResult);
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Sites Data from database in method FindOnClick_NdTSupSiteNodeCell_NodeCell due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Sites Data from database in method FindOnClick_NdTSupSiteNodeCell_NodeCell due to \n "
								+ exceptionAsString);
				rtn.put("listCells", null);
			}

			finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	// retrieve sites/nodes/cells data when supplier is clicked in
	// Supplier-site-node-cell method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FindOnClick_NdTVenSiteNodeCell_NodeCell", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> Find_NdTVenSiteNodeCell_NodeCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			String selectedVen = request.getParameter("selectedVen");
			String selectedItem = request.getParameter("selectedItem");
			String SelectedNodeType = request.getParameter("SelectedNodeType");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			System.out.println("parsingDate..." + parsingDate);

			String strNodes = "SELECT a.NODE_PK,a.NODE_TYPE,a.NODE_NAME,a.WARE_ID,a.VENDOR,"
					+ "(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
					+ generateDateCondition(parsingDate, "a");

			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 = "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_2GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.VENDOR='" + selectedVen + "' and b.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateCondition(parsingDate, "b");
			String strCells2 = "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_4GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.VENDOR='" + selectedVen + "' and b.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateCondition(parsingDate, "b");
			String strCells3 = "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_3GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.VENDOR='" + selectedVen + "' and b.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateCondition(parsingDate, "b");

			try {
				notifications.headerNotifications(session, model);
				if (selectedItem != null) {
					strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes
							+ ") as countNodes,(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes
							+ ") as countGcells,(select count(*) from NODE_4GCELL c  where a.NODE_PK = c.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("c", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes
							+ ") as countLcells,(select count(*) from NODE_3GCELL d  where a.NODE_PK = d.NODE_PK "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("d", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
					strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.WARE_ID='" + selectedItem
							+ "' and a.VENDOR='" + selectedVen + "' and a.NODE_TYPE='" + SelectedNodeType + "' "
							+ generateDateCondition(parsingDate, "a");
					strNodes = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);

					rtn.put("listVenNodes", session.createNativeQuery(strNodes).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Nodes Data from database in method FindOnClick_NdTVenSiteNodeCell_NodeCell due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Nodes Data from database in method FindOnClick_NdTVenSiteNodeCell_NodeCell due to \n "
								+ exceptionAsString);
				rtn.put("listVenNodes", null);
			}

			try {
				if (selectedItem != null) {
					strCells1 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells1);
					strCells2 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells2);
					strCells3 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells3);

					cellResult.addAll(session.createNativeQuery(strCells1).list());
					cellResult.addAll(session.createNativeQuery(strCells2).list());
					cellResult.addAll(session.createNativeQuery(strCells3).list());

					rtn.put("listCells", cellResult);
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Sites Data from database in method FindOnClick_NdTVenSiteNodeCell_NodeCell due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Sites Data from database in method FindOnClick_NdTVenSiteNodeCell_NodeCell due to \n "
								+ exceptionAsString);
				rtn.put("listCells", null);
			}

			finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/Network_StVenNdTypNdCell", method = RequestMethod.GET)
	public String Network_StVenNdTypNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			System.out.println("Welcome to Network Site Vendor Node Type Node Cell");
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE ";
						/*+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID=b.WARE_ID "
						+ generateDateCondition(parsingDate, "w");*/

				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					strSites = strSites
							+ "FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR!='null' and b.VENDOR!='0' and b.VENDOR is not null  "
							+ generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					System.out.println("strSites is " +strSites);
					//System.out.println("strSites "+strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Sites Data from database in method Network_StVenNdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Sites Data from database in method Network_StVenNdTypNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}

				finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_StVenNdTypNdCell";
		}
	}

	@RequestMapping(value = "/Network_StSupNdTypNdCell", method = RequestMethod.GET)
	public String Network_StSupNdTypNdCell(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				String enterprise = request.getParameter("enterprise");
				String transmission = request.getParameter("transmission");
				String RAN = request.getParameter("RAN");
				String core = request.getParameter("core");

				String parsingDate = request.getParameter("parsingDate");
				// System.out.println("parsingDate..."+parsingDate);

				int[] arrayParam = new int[4];
				arrayParam[0] = 0; // enterprise
				arrayParam[1] = 0; // transmission
				arrayParam[2] = 0; // RAN
				arrayParam[3] = 0; // core

				String strSites = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE ";
				try {
					notifications.headerNotifications(session, model);
					if (enterprise != null && !enterprise.equals("null")) {
						arrayParam[0] = 1;
						model.addAttribute("EnterpriseBtn", arrayParam[0]);
					}
					if (transmission != null && !transmission.equals("null")) {
						arrayParam[1] = 1;
						model.addAttribute("transmBtn", arrayParam[1]);
					}
					if (RAN != null && !RAN.equals("null")) {
						arrayParam[2] = 1;
						model.addAttribute("RANDBtn", arrayParam[2]);
					}
					if (core != null && !core.equals("null")) {
						arrayParam[3] = 1;
						model.addAttribute("CoreBtn", arrayParam[3]);
					}
					strSites = strSites
							+ "FROM NODE_ACTIVE b WHERE b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR!='null' and b.VENDOR!='0' and b.VENDOR is not null "
							+ generateDateCondition(parsingDate, "b");
					strSites = AppendQuery("b", arrayParam, strSites);
					model.addAttribute("listSites",
							mapper.writeValueAsString(session.createNativeQuery(strSites).list()));
					model.addAttribute("arrayParam", mapper.writeValueAsString(arrayParam));
					model.addAttribute("parsingDate", parsingDate);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest(
							"Error in retreiving Sites Data from database in method Network_StSupNdTypNdCell due to \n "
									+ exceptionAsString);
					logger.info(
							"Error in retreiving Sites Data from database in method Network_StSupNdTypNdCell due to \n "
									+ exceptionAsString);
					model.addAttribute("listSites", "null");
				}

				finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "Network/Network_StSupNdTypNdCell";
		}
	}

	// Area controller
	@RequestMapping(value = "/area", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> area(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			try {
				notifications.headerNotifications(session, model);
				List<?> areaList = session.createNativeQuery(
						"SELECT  distinct B.AREA_ID,B.AREA_NAME,A.LONGITUDE,A.LATITUDE FROM WAREHOUSE B,AREA A,NODE_ACTIVE C WHERE A.AREA_ID=B.AREA_ID AND B.WARE_ID=C.WARE_ID AND C.ACTIVE_RECORD='1'")
						.list();

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
				logger.finest("Error in retreiving AreaList Data from database in method area due to \n "
						+ exceptionAsString);
				logger.info("Error in retreiving AreaList Data from database in method area due to \n "
						+ exceptionAsString);
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
				logger.finest("Error in retreiving Area Sites Data from database in method area due to \n "
						+ exceptionAsString);
				logger.info("Error in retreiving Area Sites Data from database in method area due to \n "
						+ exceptionAsString);
				rtn.put("listAreaSites", null);
			}

			finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	private static String boqDomain(String paramEnterprise, String paramTransmission, String paramRAN, String paramCore,
			String str) {
		if (paramEnterprise.equals("true") || paramTransmission.equals("true") || paramRAN.equals("true")
				|| paramCore.equals("true")) {
			String[] words = str.split(" ");
			if (words.length > 0 && "WHERE".equalsIgnoreCase(words[words.length - 1])) {
			} else {
				str = str + " and ( ";
			}

			if (paramEnterprise.equals("true")) {
				str = str + "DOMAIN ='Enterprise' ";
			}
			if (paramTransmission.equals("true")) {
				if (paramEnterprise.equals("false")) {
					str = str + "DOMAIN ='Transmission' ";
				} else {
					str = str + " or DOMAIN ='Transmission' ";
				}
			}
			if (paramRAN.equals("true")) {
				if (paramEnterprise.equals("false") && paramTransmission.equals("false")) {
					str = str + "DOMAIN ='RAN' ";
				} else {
					str = str + " or DOMAIN ='RAN' ";
				}
			}
			if (paramCore.equals("true")) {
				if (paramEnterprise.equals("false") && paramTransmission.equals("false") && paramRAN.equals("false")) {
					str = str + "DOMAIN ='Core' ";
				} else {
					str = str + "or DOMAIN ='Core' ";
				}
			}
			if (words.length > 0 && "WHERE".equalsIgnoreCase(words[words.length - 1])) {
			} else {
				str = str + " ) ";
			}
		}
		return str;
	}

	private static String boqDomainVar(String a, String paramEnterprise, String paramTransmission, String paramRAN,
			String paramCore, String str) {
		if (paramEnterprise.equals("true") || paramTransmission.equals("true") || paramRAN.equals("true")
				|| paramCore.equals("true")) {
			String[] words = str.split(" ");
			if (words.length > 0 && "WHERE".equalsIgnoreCase(words[words.length - 1])) {
			} else {
				str = str + " and ( ";
			}

			if (paramEnterprise.equals("true")) {
				str = str + a + ".DOMAIN ='Enterprise' ";
			}
			if (paramTransmission.equals("true")) {
				if (paramEnterprise.equals("false")) {
					str = str + a + ".DOMAIN ='Transmission' ";
				} else {
					str = str + " or " + a + ".DOMAIN ='Transmission' ";
				}
			}
			if (paramRAN.equals("true")) {
				if (paramEnterprise.equals("false") && paramTransmission.equals("false")) {
					str = str + a + ".DOMAIN ='RAN' ";
				} else {
					str = str + " or " + a + ".DOMAIN ='RAN' ";
				}
			}
			if (paramCore.equals("true")) {
				if (paramEnterprise.equals("false") && paramTransmission.equals("false") && paramRAN.equals("false")) {
					str = str + a + ".DOMAIN ='Core' ";
				} else {
					str = str + " or " + a + ".DOMAIN ='Core' ";
				}
			}
			if (words.length > 0 && "WHERE".equalsIgnoreCase(words[words.length - 1])) {
			} else {
				str = str + " ) ";
			}
		}
		return str;
	}

	private String generateDateConditionBoq(String date, String a) {
		if (date != "" && !date.equals("")) {
			return " AND TO_DATE(TO_CHAR(" + a
					+ ".PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') = TO_DATE('" + date
					+ "','YYYY-MM-DD HH:MI:SS AM') ";
		} else {
			return " AND " + a + ".ACTIVE_RECORD = '1' ";
		}
	}

//Sites BOQ data retrieving
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetBoqList(@RequestParam String SiteId, @RequestParam String paramEnterprise,
			@RequestParam String paramTransmission, @RequestParam String paramRAN, @RequestParam String paramCore,
			@RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		List<Object[]> countEachNodeTybe= new ArrayList<Object[]>();

		try {
			String strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			String strExist = "Select distinct a.Ware_Name From NODE_ACTIVE a where a.Ware_Id='" + SiteId + "' "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String Site_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(a.NODE_PK) FROM NODE_ACTIVE a where";
			strEmpty = strEmpty + generateDateConditionBoq(date, "a").substring(4);
			
			strExist = "SELECT COUNT(a.NODE_PK) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId + "' "
					+ generateDateConditionBoq(date, "a");
			//strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			//strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(g.GCELL_ID) FROM NODE_2GCELL g, NODE_ACTIVE a  where g.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strExist = "select count(ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"
					+ SiteId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("g", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(u.UCELL_ID) FROM NODE_3GCELL u, NODE_ACTIVE a  where u.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strExist = "select count(nuc.ucell_id) from NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"
					+ SiteId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("u", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.LCELL_ID) FROM NODE_4GCELL l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strExist = "select count(nlc.lcell_id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
					+ SiteId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.BOARD_ID) FROM NODE_BOARD l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strExist = "select count(b.BOARD_ID) from NODE_BOARD b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Board_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_Board = session.createNativeQuery(Node_Board_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.CABINET_ID) FROM NODE_CABINET l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strExist = "select count(b.CABINET_ID) from NODE_CABINET b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Cabinet_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_Cabinet = session.createNativeQuery(Node_Cabinet_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.MODULE_ID) FROM NODE_MODULE l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strExist = "select count(b.MODULE_ID) from NODE_MODULE b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Module_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_Module = session.createNativeQuery(Node_Module_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.PORT_ID) FROM NODE_PORT l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strExist = "select count(b.PORT_ID) from NODE_PORT b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Port_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_Port = session.createNativeQuery(Node_Port_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.ANTENNA_ID) FROM NODE_ANTENNA l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strExist = "select count(b.ANTENNA_ID) from NODE_ANTENNA b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Antenna_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_Antenna = session.createNativeQuery(Node_Antenna_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

			if (SiteId == "") {
				strEmpty = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where";
				strEmpty = strEmpty + generateDateConditionBoq(date, "a").substring(4);
				//strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				String Node_Type_Count = strEmpty;
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				
				strEmpty = "SELECT distinct a.NODE_TYPE,COUNT(a.NODE_TYPE) from node_active a where";
				strEmpty = strEmpty + generateDateConditionBoq(date, "a").substring(4);
				strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				strEmpty = strEmpty + " GROUP BY NODE_TYPE";

				countEachNodeTybe = (List<Object[]>) session.createNativeQuery(strEmpty).list();
				for (Object[] obj : countEachNodeTybe) {
					BoqHM.put(obj[0].toString(), obj[1].toString());
				}				
			} else {
				strExist = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId + "' "
						+ generateDateConditionBoq(date, "a");
				//strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				String Node_Type_Count = strExist;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				strExist = "";
				////////////////////////////////
				strExist = "SELECT distinct a.NODE_TYPE,COUNT(a.NODE_TYPE) from node_active a where a.Ware_Id = '"
						+ SiteId + "' " + generateDateConditionBoq(date, "a");
				strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				strExist = strExist + " GROUP BY NODE_TYPE";
				countEachNodeTybe = (List<Object[]>) session.createNativeQuery(strExist).list();
				for (Object[] obj : countEachNodeTybe) {
					BoqHM.put(obj[0].toString(), obj[1].toString());
				}
			}
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));			
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
			BoqHM.put("Board", String.valueOf(CountNodes_Board));
			BoqHM.put("Cabinet", String.valueOf(CountNodes_Cabinet));
			BoqHM.put("Module", String.valueOf(CountNodes_Module));
			BoqHM.put("Port", String.valueOf(CountNodes_Port));
			BoqHM.put("Antenna", String.valueOf(CountNodes_Antenna));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest(
					"Error in retreiving Sites BOQ from database in method GetBoqList due to \n " + exceptionAsString);
			logger.info(
					"Error in retreiving Sites BOQ from database in method GetBoqList due to \n " + exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	// Sites BOQ data retrieving
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetSiteNdtypeBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetSiteNdtypeBoqList(@RequestParam String SiteId, @RequestParam String NodeTId,
			@RequestParam String paramEnterprise, @RequestParam String paramTransmission, @RequestParam String paramRAN,
			@RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

		try {
			String strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null and a.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "a");
			String strExist = "Select distinct a.Ware_Name From NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "' and a.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String Site_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(a.NODE_PK) FROM NODE_ACTIVE a where "
					+ generateDateConditionBoq(date, "a");
			strExist = "SELECT COUNT(a.NODE_PK) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId + "' "
					+ generateDateConditionBoq(date, "a");
			//strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			//strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(g.GCELL_ID) FROM NODE_2GCELL g, NODE_ACTIVE a  where g.node_pk = a.node_pk and a.NODE_TYPE="
					+ NodeTId + "' " + generateDateConditionBoq(date, "a");
			strExist = "select count(ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("g", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(u.UCELL_ID) FROM NODE_3GCELL u, NODE_ACTIVE a  where u.node_pk = a.node_pk and a.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "a");
			strExist = "select count(nuc.ucell_id) from NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("u", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.LCELL_ID) FROM NODE_4GCELL l, NODE_ACTIVE a  where l.node_pk = a.node_pk and a.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "a");
			strExist = "select count(nlc.lcell_id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.BOARD_ID) FROM NODE_BOARD l, NODE_ACTIVE a  where l.node_pk = a.node_pk and a.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "a");
			strExist = "select count(b.BOARD_ID) from NODE_BOARD b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Board_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Board = session.createNativeQuery(Node_Board_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.CABINET_ID) FROM NODE_CABINET l, NODE_ACTIVE a  where l.node_pk = a.node_pk and a.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "a");
			strExist = "select count(b.CABINET_ID) from NODE_CABINET b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Cabinet_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Cabinet = session.createNativeQuery(Node_Cabinet_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.MODULE_ID) FROM NODE_MODULE l, NODE_ACTIVE a  where l.node_pk = a.node_pk and a.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "a");
			strExist = "select count(b.MODULE_ID) from NODE_MODULE b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Module_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Module = session.createNativeQuery(Node_Module_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.PORT_ID) FROM NODE_PORT l, NODE_ACTIVE a  where l.node_pk = a.node_pk and a.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "a");
			strExist = "select count(b.PORT_ID) from NODE_PORT b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Port_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Port = session.createNativeQuery(Node_Port_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.ANTENNA_ID) FROM NODE_ANTENNA l, NODE_ACTIVE a  where l.node_pk = a.node_pk and a.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "a");
			strExist = "select count(b.ANTENNA_ID) from NODE_ANTENNA b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Antenna_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Antenna = session.createNativeQuery(Node_Antenna_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			
			BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

			if (SiteId == "") {
				strEmpty = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where "
						+ generateDateConditionBoq(date, "a");
				//strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				String Node_Type_Count = strEmpty;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
			} else {
				strExist = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId+"' "
						 + generateDateConditionBoq(date, "a");
				//strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				String Node_Type_Count = strExist;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				strExist = "";
				////////////////////////////////
				strExist = "SELECT distinct a.NODE_TYPE,COUNT(a.NODE_TYPE) from node_active a where a.Ware_Id = '"
						+ SiteId + "' and a.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "a");
				strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				strExist = strExist + " GROUP BY NODE_TYPE";
				// System.out.println(strExist);
				List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
				List<Object[]> result = new ArrayList<>();
				for (Object[] obj : CountNodesteach_Active) {
					result.add(obj);
				}
				for (Object[] object : result) {
					BoqHM.put(object[0].toString(), object[1].toString());
				}
			}
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
			BoqHM.put("Board", String.valueOf(CountNodes_Board));
			BoqHM.put("Cabinet", String.valueOf(CountNodes_Cabinet));
			BoqHM.put("Module", String.valueOf(CountNodes_Module));
			BoqHM.put("Port", String.valueOf(CountNodes_Port));
			BoqHM.put("Antenna", String.valueOf(CountNodes_Antenna));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Sites BOQ from database in method GetSiteNdtypeBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Sites BOQ from database in method GetSiteNdtypeBoqList due to \n "
					+ exceptionAsString);			
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	// Sites BOQ data retrieving
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetSiteVenBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetSiteVenBoqList(@RequestParam String SiteId, @RequestParam String VenId,
			@RequestParam String paramEnterprise, @RequestParam String paramTransmission, @RequestParam String paramRAN,
			@RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		try {
			String strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null AND a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null "
					+ generateDateConditionBoq(date, "a");
			String strExist = "Select distinct a.Ware_Name From NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "' and a.vendor='" + VenId + "' " + generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Site_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null "
					+ generateDateConditionBoq(date, "a");
			strExist = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "'  AND a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			//strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			//strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT g.GCELL_ID) FROM NODE_2GCELL g, NODE_ACTIVE a  where g.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("g", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT u.UCELL_ID) FROM NODE_3GCELL u, NODE_ACTIVE a  where u.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("u", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nuc.ucell_id) from NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.LCELL_ID) FROM NODE_4GCELL l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nlc.lcell_id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.BOARD_ID) FROM NODE_BOARD l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT b.BOARD_ID) from NODE_BOARD b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Board_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_Board = session.createNativeQuery(Node_Board_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.CABINET_ID) FROM NODE_CABINET l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT b.CABINET_ID) from NODE_CABINET b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Cabinet_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_Cabinet = session.createNativeQuery(Node_Cabinet_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.MODULE_ID) FROM NODE_MODULE l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT b.MODULE_ID) from NODE_MODULE b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Module_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_Module = session.createNativeQuery(Node_Module_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.PORT_ID) FROM NODE_PORT l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT b.PORT_ID) from NODE_PORT b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Port_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_Port = session.createNativeQuery(Node_Port_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.ANTENNA_ID) FROM NODE_ANTENNA l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT b.ANTENNA_ID) from NODE_ANTENNA b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Antenna_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_Antenna = session.createNativeQuery(Node_Antenna_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			
			BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

			if (SiteId == "") {
				strEmpty = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where ";
				if (date != "" && !date.equals("")) {
					strEmpty = strEmpty
							+ " TO_DATE(TO_CHAR(a.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') =TO_DATE('"
							+ date + "','YYYY-MM-DD HH:MI:SS AM') ";
				} else {
					strEmpty = strEmpty + "a.Active_record='1' ";
				}
				//strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				String Node_Type_Count = strEmpty;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
			} else {
				strExist = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId+"' "
						+ generateDateConditionBoq(date, "a");
				//strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				String Node_Type_Count = strExist;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				strExist = "";
				////////////////////////////////
				strExist = "SELECT distinct a.NODE_TYPE,COUNT( a.NODE_TYPE) from node_active a where a.Ware_Id = '"
						+ SiteId + "' and a.vendor='" + VenId + "' " + generateDateConditionBoq(date, "a");
				strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				strExist = strExist + " GROUP BY NODE_TYPE";
				// System.out.println(strExist);
				List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
				List<Object[]> result = new ArrayList<>();
				for (Object[] obj : CountNodesteach_Active) {
					result.add(obj);
				}
				for (Object[] object : result) {
					BoqHM.put(object[0].toString(), object[1].toString());
				}
			}
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
			BoqHM.put("Board", String.valueOf(CountNodes_Board));
			BoqHM.put("Cabinet", String.valueOf(CountNodes_Cabinet));
			BoqHM.put("Module", String.valueOf(CountNodes_Module));
			BoqHM.put("Port", String.valueOf(CountNodes_Port));
			BoqHM.put("Antenna", String.valueOf(CountNodes_Antenna));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Sites BOQ from database in method GetSiteVenBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Sites BOQ from database in method GetSiteVenBoqList due to \n "
					+ exceptionAsString);			
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	// Sites BOQ data retrieving
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetSiteVenNTBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetSiteVenNTBoqList(@RequestParam String SiteId, @RequestParam String VenId,
			@RequestParam String SelectedNodeType, @RequestParam String paramEnterprise,
			@RequestParam String paramTransmission, @RequestParam String paramRAN, @RequestParam String paramCore,
			@RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		try {
			String strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null AND a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String strExist = "Select distinct a.Ware_Name From NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "' and a.vendor='" + VenId + "' and a.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateConditionBoq(date, "a");
			String Site_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null "
					+ generateDateConditionBoq(date, "a");
			//strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			//strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT g.GCELL_ID) FROM NODE_2GCELL g, NODE_ACTIVE a  where g.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("g", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strEmpty = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' and na.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT u.UCELL_ID) FROM NODE_3GCELL u, NODE_ACTIVE a  where u.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("u", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nuc.ucell_id) from NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' and na.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.LCELL_ID) FROM NODE_4GCELL l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nlc.lcell_id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' and na.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.BOARD_ID) FROM NODE_BOARD l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT b.BOARD_ID) from NODE_BOARD b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' and na.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Board_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Board = session.createNativeQuery(Node_Board_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.CABINET_ID) FROM NODE_CABINET l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT b.CABINET_ID) from NODE_CABINET b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' and na.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Cabinet_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Cabinet = session.createNativeQuery(Node_Cabinet_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.MODULE_ID) FROM NODE_MODULE l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT b.MODULE_ID) from NODE_MODULE b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' and na.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Module_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Module = session.createNativeQuery(Node_Module_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.PORT_ID) FROM NODE_PORT l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT b.PORT_ID) from NODE_PORT b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' and na.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Port_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Port = session.createNativeQuery(Node_Port_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.ANTENNA_ID) FROM NODE_ANTENNA l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT b.ANTENNA_ID) from NODE_ANTENNA b , node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.vendor='" + VenId + "' and na.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Antenna_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Antenna = session.createNativeQuery(Node_Antenna_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			
			BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

			if (SiteId == "") {
				strEmpty = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where ";
				if (date != "" && !date.equals("")) {
					strEmpty = strEmpty
							+ " TO_DATE(TO_CHAR(a.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') =TO_DATE('"
							+ date + "','YYYY-MM-DD HH:MI:SS AM') ";
				} else {
					strEmpty = strEmpty + "a.Active_record='1' ";
				}
				//strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				String Node_Type_Count = strEmpty;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
			} else {
				strExist = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId+ "' "
						+ generateDateConditionBoq(date, "a");
				//strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				String Node_Type_Count = strExist;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				strExist = "";
				////////////////////////////////
				strExist = "SELECT distinct a.NODE_TYPE,COUNT(DISTINCT a.NODE_TYPE) from node_active a where a.Ware_Id = '"
						+ SiteId + "' and a.vendor='" + VenId + "' and a.NODE_TYPE='" + SelectedNodeType + "' "
						+ generateDateConditionBoq(date, "a");
				strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				strExist = strExist + " GROUP BY NODE_TYPE";
				// System.out.println(strExist);
				List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
				List<Object[]> result = new ArrayList<>();
				for (Object[] obj : CountNodesteach_Active) {
					result.add(obj);
				}
				for (Object[] object : result) {
					BoqHM.put(object[0].toString(), object[1].toString());
				}
			}
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));	
			BoqHM.put("Board", String.valueOf(CountNodes_Board));
			BoqHM.put("Cabinet", String.valueOf(CountNodes_Cabinet));
			BoqHM.put("Module", String.valueOf(CountNodes_Module));
			BoqHM.put("Port", String.valueOf(CountNodes_Port));
			BoqHM.put("Antenna", String.valueOf(CountNodes_Antenna));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Sites BOQ from database in method GetSiteVenNTBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Sites BOQ from database in method GetSiteVenNTBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

// Sites BOQ data retrieving
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetSiteSupBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetSiteSupBoqList(@RequestParam String SiteId, @RequestParam String SuppId,
			@RequestParam String paramEnterprise, @RequestParam String paramTransmission, @RequestParam String paramRAN,
			@RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

		try {
			String strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null AND a.SUPPLIER_ID!='null' AND a.SUPPLIER_ID!='0' AND a.SUPPLIER_ID is not null "
					+ generateDateConditionBoq(date, "a");
			String strExist = "Select distinct a.Ware_Name From NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "' and a.SUPPLIER_ID='" + SuppId + "' " + generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Site_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.SUPPLIER_ID!='null' AND a.SUPPLIER_ID!='0' AND a.SUPPLIER_ID is not null "
					+ generateDateConditionBoq(date, "a");
			strExist = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			//strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			//strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "select count(distinct ngc.gcell_id) from NODE_2GCELL ngc, node_active na where na.node_pk = ngc.node_pk "
					+ generateDateConditionBoq(date, "na");
			strExist = "select count(distinct ngc.gcell_id) from NODE_2GCELL ngc, node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.SUPPLIER_ID = '" + SuppId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "select count(distinct nuc.ucell_id) from NODE_3GCELL nuc, node_active na where na.node_pk = nuc.node_pk "
					+ generateDateConditionBoq(date, "na");
			strExist = "select count(distinct nuc.ucell_id) from NODE_3GCELL nuc, node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.SUPPLIER_ID = '" + SuppId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "select count(distinct nlc.lcell_id) from NODE_4GCELL nlc, node_active na where na.node_pk = nlc.node_pk "
					+ generateDateConditionBoq(date, "na");
			strExist = "select count(distinct nlc.lcell_id) from NODE_4GCELL nlc, node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.SUPPLIER_ID = '" + SuppId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "select count(distinct b.BOARD_ID) from NODE_BOARD b, node_active na where na.node_pk = b.node_pk "
					+ generateDateConditionBoq(date, "na");
			strExist = "select count(distinct b.BOARD_ID) from NODE_BOARD b, node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.SUPPLIER_ID = '" + SuppId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Board_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Board = session.createNativeQuery(Node_Board_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "select count(distinct b.CABINET_ID) from NODE_CABINET b, node_active na where na.node_pk = b.node_pk "
					+ generateDateConditionBoq(date, "na");
			strExist = "select count(distinct b.CABINET_ID) from NODE_CABINET b, node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.SUPPLIER_ID = '" + SuppId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Cabinet_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Cabinet = session.createNativeQuery(Node_Cabinet_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "select count(distinct b.MODULE_ID) from NODE_MODULE b, node_active na where na.node_pk = b.node_pk "
					+ generateDateConditionBoq(date, "na");
			strExist = "select count(distinct b.MODULE_ID) from NODE_MODULE b, node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.SUPPLIER_ID = '" + SuppId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Module_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Module = session.createNativeQuery(Node_Module_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "select count(distinct b.PORT_ID) from NODE_PORT b, node_active na where na.node_pk = b.node_pk "
					+ generateDateConditionBoq(date, "na");
			strExist = "select count(distinct b.PORT_ID) from NODE_PORT b, node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.SUPPLIER_ID = '" + SuppId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Port_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Port = session.createNativeQuery(Node_Port_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "select count(distinct b.ANTENNA_ID) from NODE_ANTENNA b, node_active na where na.node_pk = b.node_pk "
					+ generateDateConditionBoq(date, "na");
			strExist = "select count(distinct b.ANTENNA_ID) from NODE_ANTENNA b, node_active na where na.node_pk = b.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.SUPPLIER_ID = '" + SuppId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Antenna_Query = SiteId == "" ? strEmpty : strExist;
			Object CountNodes_Antenna = session.createNativeQuery(Node_Antenna_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

			if (SiteId == "") {
				strEmpty = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where ";
				if (date != "" && !date.equals("")) {
					strEmpty = strEmpty
							+ " TO_DATE(TO_CHAR(a.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') =TO_DATE('"
							+ date + "','YYYY-MM-DD HH:MI:SS AM') ";
				} else {
					strEmpty = strEmpty + "a.Active_record='1' ";
				}
				//strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				String Node_Type_Count = strEmpty;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
			} else {
				strExist = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId
						+ "' " + generateDateConditionBoq(date, "a");
				//strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				String Node_Type_Count = strExist;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				strExist = "";
				////////////////////////////////
				strExist = "SELECT distinct a.NODE_TYPE,COUNT(DISTINCT a.NODE_TYPE) from node_active a where a.Ware_Id = '"
						+ SiteId + "' and a.SUPPLIER_ID='" + SuppId + "' " + generateDateConditionBoq(date, "a");
				strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				strExist = strExist + " GROUP BY NODE_TYPE";
				// System.out.println(strExist);
				List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
				List<Object[]> result = new ArrayList<>();
				for (Object[] obj : CountNodesteach_Active) {
					result.add(obj);
				}
				for (Object[] object : result) {
					BoqHM.put(object[0].toString(), object[1].toString());
				}
			}
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));		
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
			BoqHM.put("Board", String.valueOf(CountNodes_Board));
			BoqHM.put("Cabinet", String.valueOf(CountNodes_Cabinet));
			BoqHM.put("Module", String.valueOf(CountNodes_Module));
			BoqHM.put("Port", String.valueOf(CountNodes_Port));
			BoqHM.put("Antenna", String.valueOf(CountNodes_Antenna));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Sites BOQ from database in method GetSiteSupBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Sites BOQ from database in method GetSiteSupBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetSiteSupNTBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public LinkedHashMap<String, String> GetSiteSupNTBoqList(@RequestParam String SiteId, @RequestParam String SuppId,
			@RequestParam String SelectedNodeType, @RequestParam String paramEnterprise,
			@RequestParam String paramTransmission, @RequestParam String paramRAN, @RequestParam String paramCore) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

		try {
			String strEmpty = "SELECT COUNT(DISTINCT WARE_ID) FROM NODE_ACTIVE WHERE WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null ";
			strEmpty = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String Site_Query = SiteId == "" ? strEmpty
					: "Select distinct Ware_Name From NODE_ACTIVE where Ware_Id='" + SiteId + "' and SUPPLIER_ID='"
							+ SuppId + "' AND NODE_TYPE='" + SelectedNodeType + "' ";
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' AND SUPPLIER_ID!='null' AND SUPPLIER_ID!='0' AND SUPPLIER_ID is not null ";
			strEmpty = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String strExist = "SELECT COUNT(DISTINCT NODE_PK) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"
					+ SiteId + "' and SUPPLIER_ID='" + SuppId + "' AND NODE_TYPE='" + SelectedNodeType
					+ "' AND WARE_ID!='null' AND WARE_ID!='0' and WARE_ID is not null ";
			strExist = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT GCELL_ID) FROM NODE_2GCELL ";
			if (paramEnterprise.equals("true") || paramTransmission.equals("true") || paramRAN.equals("true")
					|| paramCore.equals("true")) {
				strEmpty = strEmpty + "WHERE ";
			}
			strEmpty = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.SUPPLIER_ID='" + SuppId + "' AND na.NODE_TYPE='" + SelectedNodeType + "' ";
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT UCELL_ID) FROM NODE_3GCELL ";
			if (paramEnterprise.equals("true") || paramTransmission.equals("true") || paramRAN.equals("true")
					|| paramCore.equals("true")) {
				strEmpty = strEmpty + "WHERE ";
			}
			strEmpty = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nuc.ucell_id) from NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.SUPPLIER_ID='" + SuppId + "' AND na.NODE_TYPE='" + SelectedNodeType + "' ";
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT LCELL_ID) FROM NODE_4GCELL ";
			if (paramEnterprise.equals("true") || paramTransmission.equals("true") || paramRAN.equals("true")
					|| paramCore.equals("true")) {
				strEmpty = strEmpty + "WHERE ";
			}
			strEmpty = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nlc.lcell_id) from NODE_3GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.SUPPLIER_ID='" + SuppId + "' AND na.NODE_TYPE='" + SelectedNodeType + "' ";
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			
			BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

			if (SiteId == "") {
				strEmpty = "SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' ";
				strEmpty = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				String Node_Type_Count = strEmpty;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
			} else {
				strExist = "SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"
						+ SiteId + "' and SUPPLIER_ID='" + SuppId + "' AND NODE_TYPE='" + SelectedNodeType + "' ";
				strExist = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				String Node_Type_Count = strExist;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				strExist = "";
				////////////////////////////////
				strExist = "SELECT distinct NODE_TYPE,COUNT(DISTINCT NODE_TYPE) from node_active where Active_record='1' and Ware_Id = '"
						+ SiteId + "' and SUPPLIER_ID='" + SuppId + "' AND NODE_TYPE='" + SelectedNodeType + "' ";
				strExist = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				strExist = strExist + " GROUP BY NODE_TYPE";
				// System.out.println(strExist);
				List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
				List<Object[]> result = new ArrayList<>();
				for (Object[] obj : CountNodesteach_Active) {
					result.add(obj);
				}
				for (Object[] object : result) {
					BoqHM.put(object[0].toString(), object[1].toString());
				}
			}
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));			
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Sites BOQ from database in method GetSiteSupNTBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Sites BOQ from database in method GetSiteSupNTBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	// Sites BOQ data retrieving: Getting the BoQ based on the vendor (Vendor BoQ).
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetVenBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetVenBoqList(@RequestParam String VenId, @RequestParam String paramEnterprise,
			@RequestParam String paramTransmission, @RequestParam String paramRAN, @RequestParam String paramCore,
			@RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		try {

			String strEmpty = "SELECT COUNT(DISTINCT a.VENDOR) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null AND a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null  "
					+ generateDateCondition(date, "a");
			String strExist = "Select distinct a.VENDOR From NODE_ACTIVE a where a.VENDOR='" + VenId + "' "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Vendor_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Vendor_Query);
			Object Vendors = session.createNativeQuery(Vendor_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null AND a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null  "
					+ generateDateCondition(date, "a");
			strExist = "Select distinct COUNT(DISTINCT a.WARE_ID) From NODE_ACTIVE a where a.VENDOR='" + VenId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Site_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null "
					+ generateDateCondition(date, "a");
			strExist = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.VENDOR='" + VenId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT g.GCELL_ID) FROM NODE_2GCELL g, NODE_ACTIVE a  where g.node_pk = a.node_pk "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomainVar("g", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.VENDOR = '"
					+ VenId + "' " + generateDateCondition(date, "na");
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT u.UCELL_ID) FROM NODE_3GCELL u, NODE_ACTIVE a  where u.node_pk = a.node_pk "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomainVar("u", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nuc.ucell_id) from NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.VENDOR = '"
					+ VenId + "' " + generateDateCondition(date, "na");
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.LCELL_ID) FROM NODE_4GCELL l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nlc.lcell_id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.VENDOR = '"
					+ VenId + "' " + generateDateCondition(date, "na");
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			
			BoqHM.put(VenId == "" ? "Vendors" : "Vendor Name", String.valueOf(Vendors));
			BoqHM.put(VenId == "" ? "Sites" : "Sites", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

			if (VenId == "") {
				strEmpty = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where ";
				if (date != "" && !date.equals("")) {
					strEmpty = strEmpty
							+ " TO_DATE(TO_CHAR(a.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') =TO_DATE('"
							+ date + "','YYYY-MM-DD HH:MI:SS AM') ";
				} else {
					strEmpty = strEmpty + "a.Active_record='1' ";
				}
				strEmpty = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				String Node_Type_Count = strEmpty;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
			} else {
				strExist = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.VENDOR='" + VenId + "' "
						+ generateDateCondition(date, "a");
				strExist = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				String Node_Type_Count = strExist;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				strExist = "";
				////////////////////////////////
				strExist = "SELECT distinct a.NODE_TYPE,COUNT(a.NODE_TYPE) from node_active a where a.VENDOR = '"
						+ VenId + "' AND a.WARE_ID !='null' AND a.WARE_ID != '0' AND a.WARE_ID is not null" 
						+ generateDateCondition(date, "a");
				strExist = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				strExist = strExist + " GROUP BY NODE_TYPE";
				// System.out.println(strExist);
				List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
				System.out.println("CountNodesteach_Active is " +mapper.writeValueAsString(CountNodesteach_Active));
				List<Object[]> result = new ArrayList<>();
				for (Object[] obj : CountNodesteach_Active) {
					result.add(obj);
				}
				for (Object[] object : result) {
					BoqHM.put(object[0].toString(), object[1].toString());
				}
			}
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Sites BOQ from database in method GetVenBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Sites BOQ from database in method GetVenBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	// Sites BOQ data retrieving
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetVenSiteBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetVenSiteBoqList(@RequestParam String VenId, @RequestParam String siteId,
			@RequestParam String paramEnterprise, @RequestParam String paramTransmission, @RequestParam String paramRAN,
			@RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		try {

			String strEmpty = "SELECT COUNT(DISTINCT a.VENDOR) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null AND a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null  "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String Vendor_Query = VenId == "" ? strEmpty
					: "Select distinct a.VENDOR From NODE_ACTIVE a where a.VENDOR='" + VenId + "' and a.ware_id='"
							+ siteId + "' " + generateDateCondition(date, "a");
			// System.out.println(Vendor_Query);
			Object Vendors = session.createNativeQuery(Vendor_Query).uniqueResult();
			strEmpty = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null AND a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null  "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String strExist = "Select distinct COUNT(DISTINCT a.WARE_ID) From NODE_ACTIVE a where a.VENDOR='" + VenId
					+ "' and a.ware_id='" + siteId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null "
					+ generateDateCondition(date, "a");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Site_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.VENDOR='" + VenId
					+ "' and a.ware_id='" + siteId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null "
					+ generateDateCondition(date, "a");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT g.GCELL_ID) FROM NODE_2GCELL g, NODE_ACTIVE a  where g.node_pk = a.node_pk "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.VENDOR = '"
					+ VenId + "' and ware_id='" + siteId + "' " + generateDateCondition(date, "na");
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT u.UCELL_ID) FROM NODE_3GCELL u, NODE_ACTIVE a  where u.node_pk = a.node_pk "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nuc.ucell_id) from NODE_3GCELL nuc, node_active na where na.node_pk = nuc.node_pk and na.VENDOR = '"
					+ VenId + "' and ware_id='" + siteId + "' " + generateDateCondition(date, "na");
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.LCELL_ID) FROM NODE_4GCELL l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nlc.lcell_id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.VENDOR = '"
					+ VenId + "' and ware_id='" + siteId + "' " + generateDateCondition(date, "na");
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			
			BoqHM.put(VenId == "" ? "Vendors" : "Vendor Name", String.valueOf(Vendors));
			BoqHM.put(VenId == "" ? "Sites" : "Sites", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

			if (VenId == "") {
				strEmpty = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where ";
				if (date != "" && !date.equals("")) {
					strEmpty = strEmpty
							+ " TO_DATE(TO_CHAR(a.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') =TO_DATE('"
							+ date + "','YYYY-MM-DD HH:MI:SS AM') ";
				} else {
					strEmpty = strEmpty + "a.Active_record='1' ";
				}
				strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				String Node_Type_Count = strEmpty;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
			} else {
				strExist = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.VENDOR='" + VenId
						+ "' and a.ware_id='" + siteId + "' " + generateDateCondition(date, "a");
				strExist = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				String Node_Type_Count = strExist;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				strExist = "";
				////////////////////////////////
				strExist = "SELECT distinct a.NODE_TYPE,COUNT(DISTINCT a.NODE_TYPE) from node_active a where a.VENDOR = '"
						+ VenId + "' and a.ware_id='" + siteId + "' " + generateDateCondition(date, "a");
				strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				strExist = strExist + " GROUP BY NODE_TYPE";
				// System.out.println(strExist);
				List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
				List<Object[]> result = new ArrayList<>();
				for (Object[] obj : CountNodesteach_Active) {
					result.add(obj);
				}
				for (Object[] object : result) {
					BoqHM.put(object[0].toString(), object[1].toString());
				}
			}
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Sites BOQ from database in method GetVenSiteBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Sites BOQ from database in method GetVenSiteBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	// Sites BOQ data retrieving
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetSupSiteBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetSupSiteBoqList(@RequestParam String SupId, @RequestParam String siteId,
			@RequestParam String paramEnterprise, @RequestParam String paramTransmission, @RequestParam String paramRAN,
			@RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		try {

			String strEmpty = "SELECT COUNT(DISTINCT a.SUPPLIER_ID) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null AND a.SUPPLIER_ID!='null' AND a.SUPPLIER_ID!='0' AND a.SUPPLIER_ID is not null  "
					+ generateDateConditionBoq(date, "a");
			String strExist = "Select distinct a.SUPPLIER_NAME From NODE_ACTIVE a where a.SUPPLIER_ID='" + SupId
					+ "' and a.ware_id='" + siteId + "' " + generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Supplier_Query = SupId == "" ? strEmpty : strExist;
			// System.out.println(Supplier_Query);
			Object Suppliers = session.createNativeQuery(Supplier_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null AND a.SUPPLIER_ID!='null' AND a.SUPPLIER_ID!='0' AND a.SUPPLIER_ID is not null  "
					+ generateDateConditionBoq(date, "a");
			strExist = "Select distinct COUNT(DISTINCT a.WARE_ID) From NODE_ACTIVE a where a.SUPPLIER_ID='" + SupId
					+ "' and a.ware_id='" + siteId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Site_Query = SupId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.SUPPLIER_ID!='null' AND a.SUPPLIER_ID!='0' AND a.SUPPLIER_ID is not null "
					+ generateDateConditionBoq(date, "a");
			strExist = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.SUPPLIER_ID='" + SupId
					+ "' and a.ware_id='" + siteId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = SupId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(g.GCELL_ID) FROM NODE_2GCELL g, NODE_ACTIVE a  where g.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("g", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.SUPPLIER_ID = '"
					+ SupId + "' and na.ware_id='" + siteId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = SupId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(u.UCELL_ID) FROM NODE_3GCELL l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nuc.ucell_id) from NODE_3GCELL nuc, node_active na where na.node_pk = nuc.node_pk and na.SUPPLIER_ID = '"
					+ SupId + "' and na.ware_id='" + siteId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = SupId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(l.LCELL_ID) FROM NODE_4GCELL l, NODE_ACTIVE a  where l.node_pk = a.node_pk "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nlc.lcell_id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.SUPPLIER_ID = '"
					+ SupId + "' and na.ware_id='" + siteId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = SupId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			
			BoqHM.put(SupId == "" ? "Suppliers" : "Supplier Name", String.valueOf(Suppliers));
			BoqHM.put(SupId == "" ? "Sites" : "Sites", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

			if (SupId == "") {
				strEmpty = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where ";
				if (date != "" && !date.equals("")) {
					strEmpty = strEmpty
							+ " TO_DATE(TO_CHAR(a.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') =TO_DATE('"
							+ date + "','YYYY-MM-DD HH:MI:SS AM') ";
				} else {
					strEmpty = strEmpty + "a.Active_record='1' ";
				}
				strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				String Node_Type_Count = strEmpty;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
			} else {
				strExist = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.SUPPLIER_ID='" + SupId
						+ "' and a.ware_id='" + siteId + "' " + generateDateConditionBoq(date, "a");
				strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				String Node_Type_Count = strExist;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				strExist = "";
				////////////////////////////////
				strExist = "SELECT distinct a.NODE_TYPE,COUNT(DISTINCT a.NODE_TYPE) from node_active a where a.SUPPLIER_ID = '"
						+ SupId + "' and a.ware_id='" + siteId + "' " + generateDateConditionBoq(date, "a");
				strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				strExist = strExist + " GROUP BY NODE_TYPE";
				// System.out.println(strExist);
				List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
				List<Object[]> result = new ArrayList<>();
				for (Object[] obj : CountNodesteach_Active) {
					result.add(obj);
				}
				for (Object[] object : result) {
					BoqHM.put(object[0].toString(), object[1].toString());
				}
			}
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Suppliers BOQ from database in method GetSupSiteBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Suppliers BOQ from database in method GetSupSiteBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	// Sites BOQ data retrieving
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetSupBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetSupBoqList(@RequestParam String SuppId,
			@RequestParam String paramEnterprise, @RequestParam String paramTransmission, @RequestParam String paramRAN,
			@RequestParam String paramCore, @RequestParam String date) {
		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		try {
			String strEmpty = "SELECT COUNT(DISTINCT a.Supplier_Id) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null AND a.SUPPLIER_ID!='null' AND a.SUPPLIER_ID!='0' AND a.SUPPLIER_ID is not null "
					+ generateDateConditionBoq(date, "a");
			String strExist = "Select distinct a.Supplier_Name From NODE_ACTIVE a where a.Supplier_Id='" + SuppId + "' "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String Supplier_Query = SuppId == "" ? strEmpty : strExist;
			// System.out.println(Supplier_Query);
			Object Suppliers = session.createNativeQuery(Supplier_Query).uniqueResult();
			strEmpty = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM NODE_ACTIVE a WHERE a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null AND a.SUPPLIER_ID!='null' AND a.SUPPLIER_ID!='0' AND a.SUPPLIER_ID is not null "
					+ generateDateConditionBoq(date, "a");
			strExist = "Select distinct COUNT(DISTINCT a.WARE_ID) From NODE_ACTIVE a where a.Supplier_Id='" + SuppId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Site_Query = SuppId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.SUPPLIER_ID!='null' AND a.SUPPLIER_ID!='0' AND a.SUPPLIER_ID is not null "
					+ generateDateConditionBoq(date, "a");
			strExist = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.Supplier_Id='" + SuppId
					+ "' and a.WARE_ID!='null' and a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = SuppId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT g.GCELL_ID) FROM NODE_2GCELL g, NODE_ACTIVE a WHERE g.NODE_PK=a.NODE_PK "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("g", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.Supplier_Id = '"
					+ SuppId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = SuppId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT u.UCELL_ID) FROM NODE_3GCELL u, NODE_ACTIVE a WHERE u.NODE_PK=a.NODE_PK "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("u", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nuc.ucell_id) from NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.Supplier_Id = '"
					+ SuppId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = SuppId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT l.LCELL_ID) FROM NODE_4GCELL l, NODE_ACTIVE a WHERE l.NODE_PK=a.NODE_PK "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nlc.lcell_id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.Supplier_Id = '"
					+ SuppId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = SuppId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			
			BoqHM.put(SuppId == "" ? "Suppliers" : "Supplier Name", String.valueOf(Suppliers));
			BoqHM.put(SuppId == "" ? "Sites" : "Sites", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

			if (SuppId == "") {
				strEmpty = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where ";
				if (date != "" && !date.equals("")) {
					strEmpty = strEmpty
							+ " TO_DATE(TO_CHAR(a.PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM'),'YYYY-MM-DD HH:MI:SS AM') =TO_DATE('"
							+ date + "','YYYY-MM-DD HH:MI:SS AM') ";
				} else {
					strEmpty = strEmpty + "a.Active_record='1' ";
				}
				strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				String Node_Type_Count = strEmpty;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
			} else {
				strExist = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.Supplier_Id='" + SuppId + "' "
						+ generateDateConditionBoq(date, "a");
				strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				String Node_Type_Count = strExist;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				strExist = "";
				////////////////////////////////
				strExist = "SELECT distinct a.NODE_TYPE,COUNT(DISTINCT a.NODE_TYPE) from node_active a where a.Supplier_Id = '"
						+ SuppId + "' " + generateDateConditionBoq(date, "a");
				strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				strExist = strExist + " GROUP BY NODE_TYPE";
				// System.out.println(strExist);
				List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
				List<Object[]> result = new ArrayList<>();
				for (Object[] obj : CountNodesteach_Active) {
					result.add(obj);
				}
				for (Object[] object : result) {
					BoqHM.put(object[0].toString(), object[1].toString());
				}
			}
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Sites Boq from database in method GetSupBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Sites Boq from database in method GetSupBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	// Vendors BOQ data retrieving
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetVenNtypeBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetVenNtypeBoqList(@RequestParam String VenId, @RequestParam String NodeTId,
			@RequestParam String paramEnterprise, @RequestParam String paramTransmission, @RequestParam String paramRAN,
			@RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		try {
			String strEmpty = "SELECT COUNT(DISTINCT a.VENDOR) FROM NODE_ACTIVE a WHERE a.NODE_TYPE='" + NodeTId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null AND a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null  "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String strExist = "Select distinct a.VENDOR From NODE_ACTIVE a where a.VENDOR='" + VenId
					+ "' AND a.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "a");
			String Vendor_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Vendor_Query);
			Object Vendors = session.createNativeQuery(Vendor_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM NODE_ACTIVE a WHERE a.NODE_TYPE='" + NodeTId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null AND a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null  "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "Select distinct COUNT(DISTINCT a.WARE_ID) From NODE_ACTIVE a where a.VENDOR='" + VenId
					+ "' AND a.NODE_TYPE='" + NodeTId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			String Site_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.VENDOR!='null' AND a.VENDOR!='0' AND a.VENDOR is not null AND a.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.VENDOR='" + VenId
					+ "' AND a.NODE_TYPE='" + NodeTId
					+ "' and a.WARE_ID!='null' and a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT ngc.GCELL_ID) FROM NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.VENDOR = '"
					+ VenId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT nuc.UCELL_ID) FROM NODE_3GCELL nuc, node_active na where na.node_pk = nuc.node_pk and na.NODE_TYPE='"
					+ NodeTId + "'" + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nuc.ucell_id) from NODE_3GCELL nuc, node_active na where na.node_pk = nuc.node_pk and na.VENDOR = '"
					+ VenId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT nlc.LCELL_ID) FROM NODE_4GCELL nlc, node_active na where na.node_pk = nlc.node_pk and na.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nlc.lcell_id) from NODE_4GCELL nlc, node_active na where na.node_pk = nlc.node_pk and na.VENDOR = '"
					+ VenId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = VenId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			
			BoqHM.put(VenId == "" ? "Vendors" : "Vendor Name", String.valueOf(Vendors));
			BoqHM.put(VenId == "" ? "Sites" : "Sites", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

			if (VenId == "") {
				strEmpty = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.NODE_TYPE='" + NodeTId + "' "
						+ generateDateConditionBoq(date, "a");
				strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				String Node_Type_Count = strEmpty;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
			} else {
				strExist = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.VENDOR='" + VenId
						+ "' and a.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "a");
				strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				String Node_Type_Count = strExist;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				strExist = "";
				////////////////////////////////
				strExist = "SELECT distinct a.NODE_TYPE,COUNT(DISTINCT a.NODE_TYPE) from node_active a where a.VENDOR = '"
						+ VenId + "' and a.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "a");
				strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				strExist = strExist + " GROUP BY NODE_TYPE";
				// System.out.println(strExist);
				List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
				List<Object[]> result = new ArrayList<>();
				for (Object[] obj : CountNodesteach_Active) {
					result.add(obj);
				}
				for (Object[] object : result) {
					BoqHM.put(object[0].toString(), object[1].toString());
				}
			}
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));			
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Vendors Boq from database in method GetVenNtypeBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Vendors Boq from database in method GetVenNtypeBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	// Suppliers BOQ data retrieving
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetSuppNtypeBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetSuppNtypeBoqList(@RequestParam String SuppId, @RequestParam String NodeTId,
			@RequestParam String paramEnterprise, @RequestParam String paramTransmission, @RequestParam String paramRAN,
			@RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		try {
			String strEmpty = "SELECT COUNT(DISTINCT a.Supplier_Id) FROM NODE_ACTIVE a WHERE a.NODE_TYPE='" + NodeTId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null AND a.SUPPLIER_ID!='null' AND a.SUPPLIER_ID!='0' AND a.SUPPLIER_ID is not null  "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String strExist = "Select distinct a.Supplier_Name From NODE_ACTIVE a where a.Supplier_Id='" + SuppId
					+ "' AND a.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "a");
			String Supplier_Query = SuppId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Suppliers = session.createNativeQuery(Supplier_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM NODE_ACTIVE a WHERE a.NODE_TYPE='" + NodeTId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null AND a.SUPPLIER_ID!='null' AND a.SUPPLIER_ID!='0' AND a.SUPPLIER_ID is not null  "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "Select distinct COUNT(DISTINCT a.WARE_ID) From NODE_ACTIVE a where a.Supplier_Id='" + SuppId
					+ "' AND a.NODE_TYPE='" + NodeTId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' AND a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			String Site_Query = SuppId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.SUPPLIER_ID!='null' AND a.SUPPLIER_ID!='0' AND a.SUPPLIER_ID is not null AND a.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "SELECT COUNT(DISTINCT a.NODE_PK) FROM NODE_ACTIVE a where a.Supplier_Id='" + SuppId
					+ "' AND a.NODE_TYPE='" + NodeTId
					+ "' and a.WARE_ID!='null' and a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = SuppId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT ngc.GCELL_ID) FROM NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.Supplier_Id = '"
					+ SuppId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = SuppId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT nuc.UCELL_ID) FROM NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.NODE_TYPE='"
					+ NodeTId + "'" + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nuc.ucell_id) from NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.Supplier_Id = '"
					+ SuppId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = SuppId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			strEmpty = "SELECT COUNT(DISTINCT nlc.LCELL_ID) FROM NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.NODE_TYPE='"
					+ NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(DISTINCT nlc.lcell_id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.Supplier_Id = '"
					+ SuppId + "' and na.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = SuppId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			/////////////////////////////
			
			BoqHM.put(SuppId == "" ? "Suppliers" : "Supplier Name", String.valueOf(Suppliers));
			BoqHM.put(SuppId == "" ? "Sites" : "Sites", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));

			if (SuppId == "") {
				strEmpty = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.NODE_TYPE='" + NodeTId + "' "
						+ generateDateConditionBoq(date, "a");
				strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
				String Node_Type_Count = strEmpty;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
			} else {
				strExist = "SELECT COUNT(distinct a.NODE_TYPE) FROM NODE_ACTIVE a where a.Supplier_Id='" + SuppId
						+ "' and a.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "a");
				strExist = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				String Node_Type_Count = strExist;
				// System.out.println(Node_Type_Count);
				Object CountNodesType = session.createNativeQuery(Node_Type_Count).uniqueResult();
				BoqHM.put("Node Type", String.valueOf(CountNodesType));
				strExist = "";
				////////////////////////////////
				strExist = "SELECT distinct a.NODE_TYPE,COUNT(DISTINCT a.NODE_TYPE) from node_active a where a.Supplier_Id = '"
						+ SuppId + "' and a.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "a");
				strExist = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
				strExist = strExist + " GROUP BY NODE_TYPE";
				// System.out.println(strExist);
				List<Object[]> CountNodesteach_Active = (List<Object[]>) session.createNativeQuery(strExist).list();
				List<Object[]> result = new ArrayList<>();
				for (Object[] obj : CountNodesteach_Active) {
					result.add(obj);
				}
				for (Object[] object : result) {
					BoqHM.put(object[0].toString(), object[1].toString());
				}
			}
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Suppliers Boq from database in method GetSuppNtypeBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Suppliers Boq from database in method GetSuppNtypeBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	// Sites BOQ data retrieving
	@RequestMapping(value = "/GetBoqSitePoList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetBoqSitePoList(@RequestParam String SiteId,
			@RequestParam String paramEnterprise, @RequestParam String paramTransmission, @RequestParam String paramRAN,
			@RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		try {
			String strEmpty = "SELECT COUNT(distinct a.WARE_ID) FROM FAR_SITE a, FIXED_ASSET_REGISTRY b,NODE_ACTIVE c WHERE a.FAR_ID=b.FAR_ID AND a.WARE_ID=c.WARE_ID and a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "c");
			strEmpty = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String strExist = "Select DISTINCT SITE_NAME From FAR_SITE a,NODE_ACTIVE c where  a.WARE_ID='" + SiteId
					+ "' and a.WARE_ID=c.WARE_ID " + generateDateConditionBoq(date, "c");
			String Site_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";

			strEmpty = "SELECT COUNT(distinct a.PO_ID) FROM FIXED_ASSET_REGISTRY a, FAR_SITE b, NODE_ACTIVE c where b.FAR_ID=a.FAR_ID and b.WARE_ID=c.WARE_ID and b.WARE_ID!='null' AND b.WARE_ID!='0' and b.WARE_ID is not null  "
					+ generateDateConditionBoq(date, "c");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "SELECT COUNT(DISTINCT a.PO_ID) FROM FIXED_ASSET_REGISTRY a, FAR_SITE b, NODE_ACTIVE c where b.FAR_ID=a.FAR_ID and b.WARE_ID='"
					+ SiteId + "' and b.WARE_ID=c.WARE_ID " + generateDateConditionBoq(date, "c");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Po_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Po_Query);
			Object CountPo = session.createNativeQuery(Po_Query).uniqueResult();
			strEmpty = "";
			strExist = "";

			BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
			BoqHM.put("PO", String.valueOf(CountPo));

			strEmpty = "SELECT ROUND(SUM(a.INITIALCOST), 2) FROM FIXED_ASSET_REGISTRY a, NODE_ACTIVE c WHERE a.WARE_ID=c.WARE_ID "
					+ generateDateConditionBoq(date, "c");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "SELECT ROUND(SUM(a.INITIALCOST), 2) FROM FIXED_ASSET_REGISTRY a, NODE_ACTIVE c WHERE a.WARE_ID='"
					+ SiteId + "' AND a.WARE_ID=c.WARE_ID " + generateDateConditionBoq(date, "c");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String PO_Amount_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(PO_Amount_Query);
			Object PO_Amount = session.createNativeQuery(PO_Amount_Query).uniqueResult();
			BoqHM.put(SiteId == "" ? "PO Cost" : "PO Total Cost", String.valueOf(PO_Amount));
			strEmpty = "";
			strExist = "";

			strEmpty = "SELECT ROUND(SUM(a.NETCOST), 2) FROM FIXED_ASSET_REGISTRY a, NODE_ACTIVE c WHERE a.WARE_ID=c.WARE_ID "
					+ generateDateConditionBoq(date, "c");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "SELECT ROUND(SUM(a.NETCOST), 2) FROM FIXED_ASSET_REGISTRY a, NODE_ACTIVE c WHERE a.WARE_ID='"
					+ SiteId + "' AND a.WARE_ID=c.WARE_ID " + generateDateConditionBoq(date, "c");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String PO_NET_Amount_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(PO_NET_Amount_Query);
			Object PO_NET_Amount = session.createNativeQuery(PO_NET_Amount_Query).uniqueResult();
			BoqHM.put(SiteId == "" ? "PO Net Cost" : "PO Total Net Cost", String.valueOf(PO_NET_Amount));
			strEmpty = "";
			strExist = "";

			if (SiteId != "") {
				String Item_Query = "Select COUNT(DISTINCT a.ITEM_CODE) from FIXED_ASSET_REGISTRY a, FAR_SITE b, NODE_ACTIVE c where a.FAR_ID = b.FAR_ID and b.WARE_ID='"
						+ SiteId + "' AND b.WARE_ID=c.WARE_ID " + generateDateConditionBoq(date, "c");
				Item_Query = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, Item_Query);
				// System.out.println(Item_Query);
				Object Item = session.createNativeQuery(Item_Query).uniqueResult();
				BoqHM.put("Items", String.valueOf(Item));
			}

			return BoqHM;

		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Sites BOQ from database in method GetBoqSitePoList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Sites BOQ from database in method GetBoqSitePoList due to \n "
					+ exceptionAsString);
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

// Nodes BOQ data retrieving
	@RequestMapping(value = "/GetNodeBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public LinkedHashMap<String, String> GetNodeBoqList(@RequestParam String WareId, @RequestParam String NodeId,
			@RequestParam String paramEnterprise, @RequestParam String paramTransmission, @RequestParam String paramRAN,
			@RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		// System.out.println("WareId: "+WareId);
		try {
			String Site_Query = "Select DISTINCT Ware_Name From NODE_ACTIVE where Ware_Id='" + WareId + "' ";
			Site_Query = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, Site_Query);
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();

			if (WareId.equals("null") || WareId == null || WareId.equals("0") || WareId.equals("")) {
				System.out.println("warehouse  null "+WareId);
				// System.out.println("IF WARE ID NULL");
				String Node_GCell_Query = "select count(ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.NODE_PK = '"
						+ NodeId + "' and (na.Ware_Id is null or na.Ware_Id='0') " + generateDateCondition(date, "na");
				Node_GCell_Query = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_GCell_Query);
				// System.out.println(Node_GCell_Query);
				Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
				///////////////////////////
				String Node_LCell_Query = "select count(nlc.LCell_Id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
						+ " and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_LCell_Query = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_LCell_Query);
				// System.out.println(Node_LCell_Query);
				Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
				///////////////////////////
				String Node_UCell_Query = "select count(nuc.UCell_Id) from NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
						+ " and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_UCell_Query = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_UCell_Query);
				// System.out.println(Node_UCell_Query);
				Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
				///////////////////////////
				String Node_Board_Query = "select count(nlc.BOARD_ID) from NODE_BOARD nlc , node_active na where na.node_pk = nlc.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
						+ " and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_Board_Query = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_Board_Query);
				// System.out.println(Node_Board_Query);
				Object CountNodesBoard = session.createNativeQuery(Node_Board_Query).uniqueResult();
				///////////////////////////
				String Node_Cabinet_Query = "select count(nlc.CABINET_ID) from NODE_CABINET nlc , node_active na where na.node_pk = nlc.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
						+ " and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_Cabinet_Query = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_Cabinet_Query);
				// System.out.println(Node_Cabinet_Query);
				Object CountNodesCabinet = session.createNativeQuery(Node_Cabinet_Query).uniqueResult();
				///////////////////////////
				String NodesType_Query = "Select a.NODE_TYPE From NODE_ACTIVE a where a.NODE_PK = '" + NodeId
						+ "' and (a.Ware_Id is null or a.Ware_Id='0') " + generateDateCondition(date, "a");
				NodesType_Query = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, NodesType_Query);
				// System.out.println(NodesType_Query);
				Object CountNodes_NodeType = session.createNativeQuery(NodesType_Query).uniqueResult();
				////////////////////////////////////////
				String Node_Module_Query = "select count(m.MODULE_ID) from NODE_MODULE m , node_active na where na.node_pk = m.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
						+ " and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_Module_Query = boqDomainVar("m", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_Module_Query);
				Object CountNodesModule = session.createNativeQuery(Node_Module_Query).uniqueResult();
				/////////////////////////////////////////
				String Node_Port_Query ="";
				String Node_Connected_Port_Query="";
				String Node_Disconnected_Port_Query="";
				 Node_Port_Query = "select count(p.PORT_MAPPING_ID) from NODE_PORT_MAPPING p , node_active na where na.NODE_ID = p.NODE_ID and (na.Ware_Id is null or na.Ware_Id='0') "
						+ " and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_Port_Query = boqDomainVar("p", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_Port_Query);
				
				Object CountNodesPort = session.createNativeQuery(Node_Port_Query).uniqueResult();
				 int nodePortCount = ((Number) CountNodesPort).intValue();
				 
				if(nodePortCount >0) {
				 Node_Connected_Port_Query = "select count(p.PORT_MAPPING_ID) from NODE_PORT_MAPPING p , node_active na where na.NODE_ID = p.NODE_ID and (na.Ware_Id is null or na.Ware_Id='0') "
						+ " and p.REF_STATUS ='Up' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_Connected_Port_Query = boqDomainVar("p", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_Connected_Port_Query);
				
				 Node_Disconnected_Port_Query = "select count(p.PORT_MAPPING_ID) from NODE_PORT_MAPPING p , node_active na where na.NODE_ID = p.NODE_ID and (na.Ware_Id is null or na.Ware_Id='0') "
						+ " and p.REF_STATUS ='Down' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_Disconnected_Port_Query = boqDomainVar("p", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_Disconnected_Port_Query);
				}
				else if(nodePortCount==0) {
					Node_Port_Query = "select count(p.PORT_ID) from NODE_PORT p , node_active na where na.node_pk = p.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
							+ " and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
					Node_Port_Query = boqDomainVar("p", paramEnterprise, paramTransmission, paramRAN, paramCore,
							Node_Port_Query);
					
					Node_Connected_Port_Query = "select count(p.PORT_ID) from NODE_PORT p , node_active na where na.node_pk = p.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
							+ " and (LOWER(p.STATUS) = 'up' OR LOWER(p.STATUS) = 'active' OR LOWER(p.STATUS) = 'connected' OR LOWER(p.STATUS) = '1') and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
					Node_Connected_Port_Query = boqDomainVar("p", paramEnterprise, paramTransmission, paramRAN, paramCore,
							Node_Connected_Port_Query);
					
					Node_Disconnected_Port_Query = "select count(p.PORT_ID) from NODE_PORT p , node_active na where na.node_pk = p.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
							+ " and (LOWER(p.STATUS) = 'down' OR LOWER(p.STATUS) = 'inactive' OR LOWER(p.STATUS) = 'disconnected' OR LOWER(p.STATUS) = 'unknown' OR LOWER(p.STATUS) = '0') and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
					Node_Disconnected_Port_Query = boqDomainVar("p", paramEnterprise, paramTransmission, paramRAN, paramCore,
							Node_Disconnected_Port_Query);
				}
				
				CountNodesPort = session.createNativeQuery(Node_Port_Query).uniqueResult();
				Object CountConnectedNodesPort = session.createNativeQuery(Node_Connected_Port_Query).uniqueResult();
				Object CountDisconnectedNodesPort = session.createNativeQuery(Node_Disconnected_Port_Query).uniqueResult();
				/////////////////////////////////////////
				String Node_Antenna_Query = "select count(a.ANTENNA_ID) from NODE_ANTENNA a , node_active na where na.node_pk = a.node_pk and (na.Ware_Id is null or na.Ware_Id='0') "
						+ " and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_Antenna_Query = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_Antenna_Query);
				Object CountNodesAntenna = session.createNativeQuery(Node_Antenna_Query).uniqueResult();
				/////////////////////////////////////////
				BoqHM.put("Site Name", String.valueOf(Sites));
				BoqHM.put("Node Type", String.valueOf(CountNodes_NodeType));
				BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
				BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
				BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
				BoqHM.put("Board", String.valueOf(CountNodesBoard));
				BoqHM.put("Cabinet", String.valueOf(CountNodesCabinet));
				BoqHM.put("Module", String.valueOf(CountNodesModule));
				BoqHM.put("Port", String.valueOf(CountNodesPort));
				BoqHM.put("Connected Port", String.valueOf(CountConnectedNodesPort));
				BoqHM.put("Disconnected Port", String.valueOf(CountDisconnectedNodesPort));
				BoqHM.put("Antenna", String.valueOf(CountNodesAntenna));
			} else {
				System.out.println("warehouse not null "+WareId);
				String Node_GCell_Query = "select count(ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"
						+ WareId + "' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_GCell_Query = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_GCell_Query);
				// System.out.println(Node_GCell_Query);
				Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
				///////////////////////////
				String Node_LCell_Query = "select count(nlc.LCell_Id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
						+ WareId + "' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_LCell_Query = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_LCell_Query);
				// System.out.println(Node_LCell_Query);
				Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
				///////////////////////////
				String Node_UCell_Query = "select count(nuc.UCell_Id) from NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"
						+ WareId + "' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_UCell_Query = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_UCell_Query);
				// System.out.println(Node_UCell_Query);
				Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
				///////////////////////////
				String Node_Board_Query = "select count(nlc.BOARD_ID) from NODE_BOARD nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
						+ WareId + "' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_Board_Query = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_Board_Query);
				// System.out.println(Node_Board_Query);
				Object CountNodesBoard = session.createNativeQuery(Node_Board_Query).uniqueResult();
				///////////////////////////
				String Node_Cabinet_Query = "select count(nlc.CABINET_ID) from NODE_CABINET nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
						+ WareId + "' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_Cabinet_Query = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_Cabinet_Query);
				// System.out.println(Node_Cabinet_Query);
				Object CountNodesCabinet = session.createNativeQuery(Node_Cabinet_Query).uniqueResult();
				///////////////////////////
				String NodesType_Query = "Select DISTINCT a.NODE_TYPE From NODE_ACTIVE a where a.Ware_Id = '" + WareId
						+ "' and a.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "a");
				NodesType_Query = boqDomain(paramEnterprise, paramTransmission, paramRAN, paramCore, NodesType_Query);
				// System.out.println(NodesType_Query);
				Object CountNodes_NodeType = session.createNativeQuery(NodesType_Query).uniqueResult();
				///////////////////////////////////
				String Node_Module_Query = "select count(m.MODULE_ID) from NODE_MODULE m , node_active na where na.node_pk = m.node_pk and na.Ware_Id = '"
						+ WareId + "' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_Module_Query = boqDomainVar("m", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_Module_Query);
				Object CountNodesModule = session.createNativeQuery(Node_Module_Query).uniqueResult();
				///////////////////////////////////
				String Node_Port_Query= "";
				String Node_Connected_Port_Query="";
				String Node_Disconnected_Port_Query="";
				 Node_Port_Query = "select count(p.PORT_MAPPING_ID) from NODE_PORT_MAPPING p , node_active na where na.NODE_ID = p.NODE_ID and na.Ware_Id = '"
						 + WareId + "' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_Port_Query = boqDomainVar("p", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_Port_Query);
				
				Object CountNodesPort = session.createNativeQuery(Node_Port_Query).uniqueResult();
				 int nodePortCount = ((Number) CountNodesPort).intValue();
				 if(nodePortCount >0) {
					 Node_Connected_Port_Query = "select count(p.PORT_MAPPING_ID) from NODE_PORT_MAPPING p , node_active na where na.NODE_ID = p.NODE_ID and na.Ware_Id = '"
							 + WareId + "' and p.REF_STATUS ='Up' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
					 Node_Connected_Port_Query = boqDomainVar("p", paramEnterprise, paramTransmission, paramRAN, paramCore,
							 Node_Connected_Port_Query);
					
					 Node_Disconnected_Port_Query = "select count(p.PORT_MAPPING_ID) from NODE_PORT_MAPPING p , node_active na where na.NODE_ID = p.NODE_ID and na.Ware_Id = '"
							 + WareId + "' and p.REF_STATUS ='Down' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
					 Node_Disconnected_Port_Query = boqDomainVar("p", paramEnterprise, paramTransmission, paramRAN, paramCore,
							 Node_Disconnected_Port_Query);
					 
				 }
				 else if(nodePortCount ==0) {
					 Node_Port_Query = "select count(p.PORT_ID) from NODE_PORT p , node_active na where na.node_pk = p.node_pk and na.Ware_Id = '"
								+ WareId + "' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
						Node_Port_Query = boqDomainVar("p", paramEnterprise, paramTransmission, paramRAN, paramCore,
								Node_Port_Query);
						
						Node_Connected_Port_Query = "select count(p.PORT_ID) from NODE_PORT p , node_active na where na.node_pk = p.node_pk and na.Ware_Id = '"
									+ WareId + "' and (LOWER(p.STATUS) = 'up' OR LOWER(p.STATUS) = 'active' OR LOWER(p.STATUS) = 'connected' OR LOWER(p.STATUS) = '1') and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
						Node_Connected_Port_Query = boqDomainVar("p", paramEnterprise, paramTransmission, paramRAN, paramCore,
								Node_Connected_Port_Query);
						
						Node_Disconnected_Port_Query = "select count(p.PORT_ID) from NODE_PORT p , node_active na where na.node_pk = p.node_pk and na.Ware_Id = '"
								+ WareId + "' and (LOWER(p.STATUS) = 'down' OR LOWER(p.STATUS) = 'inactive' OR LOWER(p.STATUS) = 'disconnected' OR LOWER(p.STATUS) = 'unknown' OR LOWER(p.STATUS) = '0') and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
						Node_Disconnected_Port_Query = boqDomainVar("p", paramEnterprise, paramTransmission, paramRAN, paramCore,
								Node_Disconnected_Port_Query);
				 }
				
				
				
				
				 CountNodesPort = session.createNativeQuery(Node_Port_Query).uniqueResult();
				 Object CountConnectedNodesPort = session.createNativeQuery(Node_Connected_Port_Query).uniqueResult();
				 Object CountDisconnectedNodesPort = session.createNativeQuery(Node_Disconnected_Port_Query).uniqueResult();
				///////////////////////////////////
				String Node_Antenna_Query = "select count(a.ANTENNA_ID) from NODE_ANTENNA a , node_active na where na.node_pk = a.node_pk and na.Ware_Id = '"
						+ WareId + "' and na.NODE_PK = '" + NodeId + "' " + generateDateCondition(date, "na");
				Node_Antenna_Query = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
						Node_Antenna_Query);
				Object CountNodesAntenna = session.createNativeQuery(Node_Antenna_Query).uniqueResult();
				///////////////////////////////////
				
				BoqHM.put("Site Name", String.valueOf(Sites));
				BoqHM.put("Node Type", String.valueOf(CountNodes_NodeType));
				BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
				BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
				BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
				BoqHM.put("Board", String.valueOf(CountNodesBoard));
				BoqHM.put("Cabinet", String.valueOf(CountNodesCabinet));
				BoqHM.put("Module", String.valueOf(CountNodesModule));
				BoqHM.put("Port", String.valueOf(CountNodesPort));
				BoqHM.put("Connected Port", String.valueOf(CountConnectedNodesPort));
				BoqHM.put("Disconnected Port", String.valueOf(CountDisconnectedNodesPort));
				BoqHM.put("Antenna", String.valueOf(CountNodesAntenna));
			}			
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Nodes BOQ from database in method GetNodeBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Nodes BOQ Data from database in method GetNodeBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	// Node Type BOQ data retrieving
	@RequestMapping(value = "/GetNtypeBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetNtypeBoqList(@RequestParam String SiteId, @RequestParam String NodeTId,
			@RequestParam String paramEnterprise, @RequestParam String paramTransmission, @RequestParam String paramRAN,
			@RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

		try {
			String strEmpty = "SELECT COUNT(distinct a.WARE_ID) FROM NODE_ACTIVE a WHERE NODE_TYPE='" + NodeTId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			String strExist = "Select distinct a.Ware_Name From NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "' and a.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Site_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(distinct a.NODE_PK) FROM NODE_ACTIVE a where a.NODE_TYPE='" + NodeTId + "' "
					+ generateDateConditionBoq(date, "a");
			strExist = "SELECT COUNT(distinct a.NODE_PK) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "' and a.NODE_TYPE='" + NodeTId + "' " + generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "select count(distinct ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.NODE_TYPE = '"
					+ NodeTId + "' " + generateDateConditionBoq(date, "na");
			strExist = "select count(distinct ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "select count(distinct nuc.UCell_Id) from NODE_3GCELL nuc, node_active na where na.node_pk = nuc.node_pk and na.NODE_TYPE = '"
					+ NodeTId + "' " + generateDateConditionBoq(date, "na");
			strExist = "select count(distinct nuc.UCell_Id) from NODE_3GCELL nuc, node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			///////////////////////////
			strEmpty = "select count(distinct nlc.LCell_Id) from NODE_4GCELL nlc, node_active na where na.node_pk = nlc.node_pk and na.NODE_TYPE = '"
					+ NodeTId + "' " + generateDateConditionBoq(date, "na");
			strExist = "select count(distinct nlc.LCell_Id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////

			BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));			
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Node Type BOQ from database in method GetNtypeBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Node Type BOQ from database in method GetNtypeBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	/// node type boq for vendor
	// Node Type BOQ data retrieving
	@RequestMapping(value = "/GetNtypeVenBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetNtypeVenBoqList(@RequestParam String SiteId, @RequestParam String NodeTId,
			@RequestParam String VendorId, @RequestParam String paramEnterprise, @RequestParam String paramTransmission,
			@RequestParam String paramRAN, @RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();
		
		try {
			String strEmpty = "SELECT COUNT(distinct a.WARE_ID) FROM NODE_ACTIVE a WHERE a.NODE_TYPE='" + NodeTId
					+ "' AND a.VENDOR = '" + VendorId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String strExist = "Select distinct a.Ware_Name From NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "' and a.NODE_TYPE='" + NodeTId + "' AND a.VENDOR = '" + VendorId + "' "
					+ generateDateCondition(date, "a");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Site_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			System.out.println("Sites are " +Sites.toString());
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(distinct a.NODE_PK) FROM NODE_ACTIVE a where a.NODE_TYPE='" + NodeTId
					+ "' AND a.VENDOR = '" + VendorId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateCondition(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "SELECT COUNT(distinct a.NODE_PK) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "' and a.NODE_TYPE='" + NodeTId + "' AND a.VENDOR = '" + VendorId + "' "
					+ generateDateCondition(date, "a");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			System.out.println("CountNodes_Active is " +CountNodes_Active.toString());
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "select count(distinct ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.NODE_TYPE = '"
					+ NodeTId + "' AND na.VENDOR = '" + VendorId + "' " + generateDateCondition(date, "na");
			strEmpty = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(distinct ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.VENDOR = '" + VendorId + "' "
					+ generateDateCondition(date, "na");
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "select count(distinct nuc.UCell_Id) from NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.NODE_TYPE = '"
					+ NodeTId + "' AND na.VENDOR = '" + VendorId + "' " + generateDateCondition(date, "na");
			strEmpty = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(distinct nuc.UCell_Id) from NODE_3GCELL nuc , node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.VENDOR = '" + VendorId + "' "
					+ generateDateCondition(date, "na");
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			///////////////////////////
			strEmpty = "select count(distinct nlc.LCell_Id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.NODE_TYPE = '"
					+ NodeTId + "' AND na.VENDOR = '" + VendorId + "' " + generateDateCondition(date, "na");
			strEmpty = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(distinct nlc.LCell_Id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.VENDOR = '" + VendorId + "' "
					+ generateDateCondition(date, "na");
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			

			BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Node Type BOQ from database in method GetNtypeVenBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Node Type BOQ from database in method GetNtypeVenBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	/// node type boq for vendor
	// Node Type BOQ data retrieving
	@RequestMapping(value = "/GetNtypeSupBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody

	public LinkedHashMap<String, String> GetNtypeSupBoqList(@RequestParam String SiteId, @RequestParam String NodeTId,
			@RequestParam String SuppId, @RequestParam String paramEnterprise, @RequestParam String paramTransmission,
			@RequestParam String paramRAN, @RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

		try {
			String strEmpty = "SELECT COUNT(distinct a.WARE_ID) FROM NODE_ACTIVE a WHERE a.NODE_TYPE='" + NodeTId
					+ "' AND a.SUPPLIER_ID = '" + SuppId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			String strExist = "Select distinct a.Ware_Name From NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "' and a.NODE_TYPE='" + NodeTId + "' AND a.SUPPLIER_ID = '" + SuppId + "' "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Site_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object Sites = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "SELECT COUNT(distinct a.NODE_PK) FROM NODE_ACTIVE a where a.NODE_TYPE='" + NodeTId
					+ "' AND a.SUPPLIER_ID = '" + SuppId
					+ "' AND a.WARE_ID!='null' AND a.WARE_ID!='0' and a.WARE_ID is not null "
					+ generateDateConditionBoq(date, "a");
			strExist = "SELECT COUNT(distinct a.NODE_PK) FROM NODE_ACTIVE a where a.Ware_Id='" + SiteId
					+ "' and a.NODE_TYPE='" + NodeTId + "' AND a.SUPPLIER_ID = '" + SuppId + "' "
					+ generateDateConditionBoq(date, "a");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_Active_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_Active_Query);
			Object CountNodes_Active = session.createNativeQuery(Node_Active_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "select count(distinct ngc.gcell_id) from NODE_2GCELL ngc, node_active na where na.node_pk = ngc.node_pk and na.NODE_TYPE = '"
					+ NodeTId + "' AND na.SUPPLIER_ID = '" + SuppId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(distinct ngc.gcell_id) from NODE_2GCELL ngc, node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.SUPPLIER_ID = '" + SuppId + "' "
					+ generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("ngc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_GCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_GCell_Query);
			Object CountNodes_G_CELL = session.createNativeQuery(Node_GCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			strEmpty = "select count(distinct nuc.UCell_Id) from NODE_3GCELL nuc, node_active na where na.node_pk = nuc.node_pk and na.NODE_TYPE = '"
					+ NodeTId + "' AND na.SUPPLIER_ID = '" + SuppId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(distinct nuc.UCell_Id) from NODE_3GCELL nuc, node_active na where na.node_pk = nuc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.SUPPLIER_ID = '" + SuppId + "' "
					+ generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nuc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_UCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_UCell_Query);
			Object CountNodes_U_CELL = session.createNativeQuery(Node_UCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			///////////////////////////
			strEmpty = "select count(distinct nlc.LCell_Id) from NODE_4GCELL nlc, node_active na where na.node_pk = nlc.node_pk and na.NODE_TYPE = '"
					+ NodeTId + "' AND na.SUPPLIER_ID = '" + SuppId + "' " + generateDateConditionBoq(date, "na");
			strEmpty = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "select count(distinct nlc.LCell_Id) from NODE_4GCELL nlc, node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
					+ SiteId + "' and na.NODE_TYPE = '" + NodeTId + "' AND na.SUPPLIER_ID = '" + SuppId + "' "
					+ generateDateConditionBoq(date, "na");
			strExist = boqDomainVar("nlc", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Node_LCell_Query = SiteId == "" ? strEmpty : strExist;
			// System.out.println(Node_LCell_Query);
			Object CountNodes_L_CELL = session.createNativeQuery(Node_LCell_Query).uniqueResult();
			strEmpty = "";
			strExist = "";
			////////////////////////////
			
			BoqHM.put(SiteId == "" ? "Sites" : "Site Name", String.valueOf(Sites));
			BoqHM.put("Nodes", String.valueOf(CountNodes_Active));
			BoqHM.put("2G Cell", String.valueOf(CountNodes_G_CELL));
			BoqHM.put("3G Cell", String.valueOf(CountNodes_U_CELL));
			BoqHM.put("4G Cell", String.valueOf(CountNodes_L_CELL));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in retreiving Node Type BOQ from database in method GetNtypeSupBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving Node Type BOQ from database in method GetNtypeSupBoqList due to \n "
					+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

//retrieve sites and items data on PO click
	@RequestMapping(value = "/findPOSt_Items", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findPOSt_Items(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {

			String selectedItem = request.getParameter("selectedItem");
			String POAlreadyCreated = request.getParameter("POAlreadyCreated");
			String selectedSite = request.getParameter("selectedSite");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strSites = "SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,j.PO_ID FROM FAR_SITE b,FIXED_ASSET_REGISTRY j,NODE_ACTIVE a WHERE j.FAR_ID=b.FAR_ID "
					+ "AND b.WARE_ID!='0' AND b.WARE_ID!='null' AND b.WARE_ID is not null AND b.WARE_ID=a.WARE_ID AND j.PO_ID='"
					+ selectedItem + "' " + generateDateCondition(parsingDate, "a");

			String strItems = "SELECT distinct a.ITEM_CODE, a.ITEM_NAME, a.PO_ID, b.WARE_ID, a.ITEM_MODEL FROM FIXED_ASSET_REGISTRY a, FAR_SITE b, NODE_ACTIVE c "
					+ "WHERE a.FAR_ID=b.FAR_ID AND b.WARE_ID=c.WARE_ID AND b.WARE_ID='" + selectedSite + "' AND a.PO_ID='"
					+ selectedItem + "' " + generateDateCondition(parsingDate, "c");
			try {
				notifications.headerNotifications(session, model);
				if (POAlreadyCreated.equals("false")) {
					strSites = boqDomainVar("j", paramEnterprise, paramTransmission, paramRAN, paramCore, strSites);
					rtn.put("listSites", session.createNativeQuery(strSites).list());

				} else {
					strItems = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strItems);
					rtn.put("itemList", session.createNativeQuery(strItems).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Items and Sites Data from database in method findPOSt_Items due to \n "
								+ exceptionAsString);
				logger.info("Error in retreiving Items and Sites Data from database in method findPOSt_Items due to \n "
						+ exceptionAsString);
				rtn.put("itemList", null);
				rtn.put("listSites", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
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
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {

			String selectedSite = request.getParameter("selectedSite");
			String POAlreadyCreated = request.getParameter("POAlreadyCreated");
			String selectedPO = request.getParameter("selectedPO");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strPO = "SELECT distinct a.PO_ID ,b.WARE_ID,b.SITE_NAME,b.SITE_ID,"
					+ "(select count(*) from FIXED_ASSET_REGISTRY a where a.PO_ID is not null and b.WARE_ID is not null) as countItems "
					+ "FROM FIXED_ASSET_REGISTRY a, FAR_SITE b, NODE_ACTIVE c where a.FAR_ID=b.FAR_ID and c.WARE_ID=b.WARE_ID and b.WARE_ID='"
					+ selectedSite + "' " + generateDateCondition(parsingDate, "c");

			String strItems = "SELECT distinct a.ITEM_CODE, a.ITEM_NAME, a.PO_ID, b.WARE_ID, a.ITEM_MODEL FROM FIXED_ASSET_REGISTRY a, FAR_SITE b, NODE_ACTIVE c "
					+ "WHERE a.FAR_ID=b.FAR_ID AND b.WARE_ID='" + selectedSite + "' AND a.PO_ID='" + selectedPO + "' "
					+ generateDateCondition(parsingDate, "c");

			try {
				notifications.headerNotifications(session, model);
				if (POAlreadyCreated.equals("false")) {
					strPO = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strPO);
					rtn.put("listPO", session.createNativeQuery(strPO).list());
				} else {
					strItems = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strItems);
					rtn.put("itemList", session.createNativeQuery(strItems).list());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving PO and Items Data from database in method findSitePO_Items due to \n "
								+ exceptionAsString);
				logger.info("Error in retreiving PO and Items Data from database in method findSitePO_Items due to \n "
						+ exceptionAsString);
				rtn.put("listPO", null);
				rtn.put("itemList", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
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
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String selectedNode = request.getParameter("selectedNode");
			String selectedNdTyp = request.getParameter("selectedNdTyp");

			String parsingDate = request.getParameter("date");
			// System.out.println("date: "+parsingDate);

			try {
				notifications.headerNotifications(session, model);
				if (selectedNdTyp == null) {
					List<Object[]> cellResult = new ArrayList<Object[]>();
					String strCells1 = "SELECT g.GCELL_ID,g.CELLNAME,g.NODE_PK FROM NODE_2GCELL g, NODE_ACTIVE b WHERE b.NODE_PK=g.NODE_PK and g.NODE_PK='"
							+ selectedNode + "' " + generateDateCondition(parsingDate, "b");
					String strCells2 = "SELECT l.LCELL_ID,l.CELLNAME,l.NODE_PK FROM NODE_4GCELL l, NODE_ACTIVE b WHERE b.NODE_PK=l.NODE_PK and l.NODE_PK='"
							+ selectedNode + "' " + generateDateCondition(parsingDate, "b");
					String strCells3 = "SELECT u.UCELL_ID,u.CELLNAME,u.NODE_PK FROM NODE_3GCELL u, NODE_ACTIVE b WHERE b.NODE_PK=u.NODE_PK and u.NODE_PK='"
							+ selectedNode + "' " + generateDateCondition(parsingDate, "b");

					strCells1 = boqDomainVar("g", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells1);
					strCells2 = boqDomainVar("l", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells2);
					strCells3 = boqDomainVar("u", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells3);

					System.out.println("strCells1: " + strCells1);
					cellResult.addAll(session.createNativeQuery(strCells1).list());
					cellResult.addAll(session.createNativeQuery(strCells2).list());
					cellResult.addAll(session.createNativeQuery(strCells3).list());

					rtn.put("listCells", cellResult);
				} else {
					System.out.println("selectedNdTyp !NULL");
					List<Object[]> cellResult = new ArrayList<Object[]>();
					String strCells1 = "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_2GCELL a,NODE_ACTIVE b WHERE a.NODE_PK=b.NODE_PK and a.NODE_PK='"
							+ selectedNode + "' and b.NODE_TYPE='" + selectedNdTyp + "' "
							+ generateDateCondition(parsingDate, "b");
					String strCells2 = "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_4GCELL a,NODE_ACTIVE b WHERE a.NODE_PK=b.NODE_PK and a.NODE_PK='"
							+ selectedNode + "' and b.NODE_TYPE='" + selectedNdTyp + "' "
							+ generateDateCondition(parsingDate, "b");
					String strCells3 = "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_3GCELL a,NODE_ACTIVE b WHERE a.NODE_PK=b.NODE_PK and a.NODE_PK='"
							+ selectedNode + "' and b.NODE_TYPE='" + selectedNdTyp + "' "
							+ generateDateCondition(parsingDate, "b");

					strCells1 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells1);
					strCells2 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells2);
					strCells3 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strCells3);

					System.out.println("strCells1: " + strCells1);
					cellResult.addAll(session.createNativeQuery(strCells1).list());
					cellResult.addAll(session.createNativeQuery(strCells2).list());
					cellResult.addAll(session.createNativeQuery(strCells3).list());

					rtn.put("listCells", cellResult);
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Cells Data from database in method findNode_Cells due to \n "
						+ exceptionAsString);
				logger.info("Error in retreiving Cells Data from database in method findNode_Cells due to \n "
						+ exceptionAsString);
				rtn.put("listCells", null);
			} finally {
				if (session != null && session.isOpen()) {
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
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			String selectedNdTyp = request.getParameter("selectedNodetType");
			String selectedItem = request.getParameter("selectedItem");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsing date: "+parsingDate);

			String strSites = "SELECT distinct a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.NODE_TYPE,a.LONGITUDE,a.LATITUDE FROM NODE_ACTIVE a "
					+ "where a.WARE_ID!= 'null' and a.WARE_ID is not null and a.WARE_ID!= '0' and a.NODE_TYPE='"
					+ selectedNdTyp + "' " + generateDateCondition(parsingDate, "a");

			String strNodes = "SELECT a.NODE_PK,a.SITE_ID,a.NODE_NAME,a.WARE_ID,a.NODE_TYPE,"
					+ "(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
					+ generateDateCondition(parsingDate, "a");

			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 = "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_2GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.NODE_TYPE='"
					+ selectedNdTyp + "' and b.WARE_ID='" + selectedItem + "' "
					+ generateDateCondition(parsingDate, "b");
			String strCells2 = "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_4GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.NODE_TYPE='"
					+ selectedNdTyp + "' and b.WARE_ID='" + selectedItem + "' "
					+ generateDateCondition(parsingDate, "b");
			String strCells3 = "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK FROM NODE_3GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.NODE_TYPE='"
					+ selectedNdTyp + "' and b.WARE_ID='" + selectedItem + "' "
					+ generateDateCondition(parsingDate, "b");

			try {
				notifications.headerNotifications(session, model);
				if (selectedNdTyp != null) {
					try {
						strSites = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strSites);

						rtn.put("listSites", session.createNativeQuery(strSites).list());
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Sites Data from database in method findNodeTypeSiteNode_Cells due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Sites Data from database in method findNodeTypeSiteNode_Cells due to \n "
										+ exceptionAsString);
						rtn.put("listSites", null);
					}
				}

				if (selectedNdTyp != null && selectedItem != null) {
					try {
						strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countGcells,(select count(*) from NODE_4GCELL c  where a.NODE_PK = c.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("c", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countLcells,(select count(*) from NODE_3GCELL d  where a.NODE_PK = d.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("d", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.WARE_ID='" + selectedItem
								+ "' and a.NODE_TYPE='" + selectedNdTyp + "' "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);

						rtn.put("listNodes", session.createNativeQuery(strNodes).list());
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Nodes Data from database in method Network_StNdCell due to \n "
										+ exceptionAsString);
						logger.info("Error in retreiving Nodes Data from database in method Network_StNdCell due to \n "
								+ exceptionAsString);
						rtn.put("listNodes", null);
					}
				}

				if (selectedItem != null) {
					try {
						strCells1 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells1);
						strCells2 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells2);
						strCells3 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells3);

						cellResult.addAll(session.createNativeQuery(strCells1).list());
						cellResult.addAll(session.createNativeQuery(strCells2).list());
						cellResult.addAll(session.createNativeQuery(strCells3).list());

						rtn.put("listCells", cellResult);
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Cells Data from database in method findNodeTypeSiteNode_Cells due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Cells Data from database in method findNodeTypeSiteNode_Cells due to \n "
										+ exceptionAsString);
						rtn.put("listCells", null);
					}
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Data from database in method findNodeTypeSiteNode_Cells due to \n "
						+ exceptionAsString);
				logger.info("Error in retreiving Data from database in method findNodeTypeSiteNode_Cells due to \n "
						+ exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
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
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			try {

				String selectedPo = request.getParameter("selectedPo");
				String POAlreadyCreated = request.getParameter("POAlreadyCreated");
				String selectedItem = request.getParameter("selectedItem");

				String paramEnterprise = request.getParameter("paramEnterprise");
				String paramTransmission = request.getParameter("paramTransmission");
				String paramRAN = request.getParameter("paramRAN");
				String paramCore = request.getParameter("paramCore");

				String parsingDate = request.getParameter("date");
				// System.out.println("parsingDate..."+parsingDate);

				String strItems = "SELECT distinct a.ITEM_CODE, a.ITEM_NAME, a.PO_ID, a.ITEM_MODEL FROM FIXED_ASSET_REGISTRY a, FAR_NODE b, NODE_ACTIVE c WHERE b.NODE_ID=c.NODE_ID and a.FAR_ID = b.FAR_ID and a.PO_ID='"
						+ selectedPo + "' " + generateDateCondition(parsingDate, "c");

				String strSites = "SELECT DISTINCT b.SITE_ID,b.SITE_NAME,b.WARE_ID,j.ITEM_CODE,j.PO_ID FROM FAR_SITE b,FIXED_ASSET_REGISTRY j,NODE_ACTIVE a where j.FAR_ID=b.FAR_ID AND "
						+ "b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null AND b.WARE_ID=a.WARE_ID AND j.PO_ID='"
						+ selectedPo + "' and j.ITEM_CODE='" + selectedItem + "' "
						+ generateDateCondition(parsingDate, "a");

				if (POAlreadyCreated.equals("false")) {
					try {
						notifications.headerNotifications(session, model);
						strItems = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strItems);
						rtn.put("listItem", session.createNativeQuery(strItems).list());
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Items Data from database in method findPOItems_sites due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Items Data from database in method findPOItems_sites due to \n "
										+ exceptionAsString);
						rtn.put("listItem", null);
					}
				} else {
					try {
						strSites = boqDomainVar("j", paramEnterprise, paramTransmission, paramRAN, paramCore, strSites);
						rtn.put("listSites", session.createNativeQuery(strSites).list());
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Sites Data from database in method findPOItems_sites due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Sites Data from database in method findPOItems_sites due to \n "
										+ exceptionAsString);
						rtn.put("listSites", null);
					}
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Data from database in method findPOItems_sites due to \n "
						+ exceptionAsString);
				logger.info("Error in retreiving Data from database in method findPOItems_sites due to \n "
						+ exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
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
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {

			String selectedSupp = request.getParameter("selectedSupp");
			String selectedNodetType = request.getParameter("selectedNodetType");
			String selectedItem = request.getParameter("selectedItem");
			// String selectedNode = request.getParameter("selectedNode");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strSites = "SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.SUPPLIER_ID FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.SUPPLIER_ID='"
					+ selectedSupp + "' " + generateDateCondition(parsingDate, "b");

			String strNodesType = "SELECT DISTINCT b.NODE_TYPE,b.WARE_ID,b.SUPPLIER_ID FROM NODE_ACTIVE b WHERE b.WARE_ID='"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' "
					+ generateDateCondition(parsingDate, "b");

			String strNodes = "SELECT a.NODE_PK,a.SITE_ID,a.NODE_NAME,a.NODE_TYPE,a.WARE_ID,"
					+ "(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
					+ generateDateCondition(parsingDate, "a");

			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 = "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_2GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' and b.NODE_TYPE='" + selectedNodetType
					+ "' " + generateDateCondition(parsingDate, "b");
			String strCells2 = "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_4GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' and b.NODE_TYPE='" + selectedNodetType
					+ "' " + generateDateCondition(parsingDate, "b");
			String strCells3 = "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_3GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' and b.NODE_TYPE='" + selectedNodetType
					+ "' " + generateDateCondition(parsingDate, "b");

			try {
				notifications.headerNotifications(session, model);
				if (selectedSupp != null && selectedItem == null) {
					try {
						strSites = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strSites);
						rtn.put("listSuppSites", session.createNativeQuery(strSites).list());

					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Sites Data from database in method FindOnClick_SuppStNdTypNdCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Sites Data from database in method FindOnClick_SuppStNdTypNdCell due to \n "
										+ exceptionAsString);
						rtn.put("listSuppSites", null);
					}
				}
				if (selectedItem != null) {
					try {
						strNodesType = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strNodesType);
						rtn.put("listNodesType", session.createNativeQuery(strNodesType).list());
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Node Type Data from database in method FindOnClick_SuppStNdTypNdCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Node Type Data from database in method FindOnClick_SuppStNdTypNdCell due to \n "
										+ exceptionAsString);
						rtn.put("listNodesType", null);
					}
				}
				if (selectedNodetType != null) {
					try {
						strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countNodes,(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countGcells,(select count(*) from NODE_4GCELL c  where a.NODE_PK = c.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("c", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countLcells,(select count(*) from NODE_3GCELL d  where a.NODE_PK = d.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("d", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.WARE_ID='" + selectedItem
								+ "' and a.NODE_TYPE='" + selectedNodetType + "' and a.SUPPLIER_ID='" + selectedSupp
								+ "' " + generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);

						rtn.put("listNodes", session.createNativeQuery(strNodes).list());
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Nodes Data from database in method FindOnClick_SuppStNdTypNdCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Nodes Data from database in method FindOnClick_SuppStNdTypNdCell due to \n "
										+ exceptionAsString);
						rtn.put("listNodes", null);
					}
				}
				if (selectedNodetType != null) {
					try {
						strCells1 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells1);
						strCells2 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells2);
						strCells3 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells3);

						cellResult.addAll(session.createNativeQuery(strCells1).list());
						cellResult.addAll(session.createNativeQuery(strCells2).list());
						cellResult.addAll(session.createNativeQuery(strCells3).list());

						rtn.put("listCells", cellResult);
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Cells Data from database in method FindOnClick_SuppStNdTypNdCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Cells Data from database in method FindOnClick_SuppStNdTypNdCell due to \n "
										+ exceptionAsString);
						rtn.put("listCells", null);
					}
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Data from database in method FindOnClick_SuppStNdTypNdCell due to \n "
								+ exceptionAsString);
				logger.info("Error in retreiving Data from database in method FindOnClick_SuppStNdTypNdCell due to \n "
						+ exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
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
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {

			String selectedVen = request.getParameter("selectedVen");
			String selectedNodetType = request.getParameter("selectedNodetType");
			String selectedItem = request.getParameter("selectedItem");
			// String selectedNode = request.getParameter("selectedNode");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strSites = "SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.VENDOR FROM NODE_ACTIVE b "
					+ "where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR='"
					+ selectedVen + "' " + generateDateCondition(parsingDate, "b");

			String strNodesType = "SELECT DISTINCT b.NODE_TYPE,b.WARE_ID,b.VENDOR FROM NODE_ACTIVE b WHERE "
					+ "b.WARE_ID='" + selectedItem + "' and b.VENDOR='" + selectedVen + "' "
					+ generateDateCondition(parsingDate, "b");

			String strNodes = "SELECT a.NODE_PK,a.SITE_ID,a.NODE_NAME,a.NODE_TYPE,a.WARE_ID,"
					+ "(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
					+ generateDateCondition(parsingDate, "a");

			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 = "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_2GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.VENDOR='" + selectedVen + "' and b.NODE_TYPE='" + selectedNodetType + "' "
					+ generateDateCondition(parsingDate, "b");
			String strCells2 = "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_4GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.VENDOR='" + selectedVen + "' and b.NODE_TYPE='" + selectedNodetType + "' "
					+ generateDateCondition(parsingDate, "b");
			String strCells3 = "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_3GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.VENDOR='" + selectedVen + "' and b.NODE_TYPE='" + selectedNodetType + "' "
					+ generateDateCondition(parsingDate, "b");

			try {
				notifications.headerNotifications(session, model);
				if (selectedVen != null && selectedItem == null) {
					try {
						strSites = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strSites);
						rtn.put("listVenSites", session.createNativeQuery(strSites).list());

					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Sites Data from database in method FindOnClick_VenStNdTypNdCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Sites Data from database in method FindOnClick_VenStNdTypNdCell due to \n "
										+ exceptionAsString);
						rtn.put("listVenSites", null);
					}
				}
				if (selectedItem != null) {
					try {
						strNodesType = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strNodesType);
						rtn.put("listNodesType", session.createNativeQuery(strNodesType).list());
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Node Type Data from database in method FindOnClick_VenStNdTypNdCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Node Type Data from database in method FindOnClick_VenStNdTypNdCell due to \n "
										+ exceptionAsString);
						rtn.put("listNodesType", null);
					}
				}
				if (selectedNodetType != null) {
					try {
						strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countNodes,(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countGcells,(select count(*) from NODE_4GCELL c  where a.NODE_PK = c.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("c", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countLcells,(select count(*) from NODE_3GCELL d  where a.NODE_PK = d.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("d", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.WARE_ID='" + selectedItem
								+ "' and " + "a.NODE_TYPE='" + selectedNodetType + "' and a.VENDOR='" + selectedVen
								+ "' " + generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);

						rtn.put("listNodes", session.createNativeQuery(strNodes).list());
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Nodes Data from database in method Network_StNdCell due to \n "
										+ exceptionAsString);
						logger.info("Error in retreiving Nodes Data from database in method Network_StNdCell due to \n "
								+ exceptionAsString);
						rtn.put("listNodes", null);
					}
				}
				if (selectedNodetType != null) {
					try {
						strCells1 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells1);
						strCells2 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells2);
						strCells3 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells3);

						cellResult.addAll(session.createNativeQuery(strCells1).list());
						cellResult.addAll(session.createNativeQuery(strCells2).list());
						cellResult.addAll(session.createNativeQuery(strCells3).list());

						rtn.put("listCells", cellResult);
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Nodes Data from database in method FindOnClick_VenStNdTypNdCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Nodes Data from database in method FindOnClick_VenStNdTypNdCell due to \n "
										+ exceptionAsString);
						rtn.put("listCells", null);
					}
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving Data from database in method FindOnClick_VenStNdTypNdCell due to \n "
						+ exceptionAsString);
				logger.info("Error in retreiving Data from database in method FindOnClick_VenStNdTypNdCell due to \n "
						+ exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
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
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {

			String selectedItem = request.getParameter("selectedItem");
			String selectedSupp = request.getParameter("selectedSupp");
			String SelectedNodeType = request.getParameter("SelectedNodeType");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strSites = "SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.SUPPLIER_ID,b.NODE_TYPE "
					+ "FROM NODE_ACTIVE b where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and "
					+ "b.SUPPLIER_ID='" + selectedSupp + "' AND b.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateCondition(parsingDate, "b");

			List<String> listNodeType = new ArrayList<String>();
			String strNodesType = "SELECT DISTINCT b.NODE_TYPE,b.SUPPLIER_ID FROM NODE_ACTIVE b WHERE b.SUPPLIER_ID='"
					+ selectedSupp + "' " + generateDateCondition(parsingDate, "b");

			String strNodes = "SELECT a.NODE_PK,a.SITE_ID,a.NODE_NAME,a.WARE_ID,a.NODE_TYPE,"
					+ "(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
					+ generateDateCondition(parsingDate, "a");

			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 = "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_2GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' and b.NODE_TYPE='" + SelectedNodeType
					+ "' " + generateDateCondition(parsingDate, "b");
			String strCells2 = "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_4GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' and b.NODE_TYPE='" + SelectedNodeType
					+ "' " + generateDateCondition(parsingDate, "b");
			String strCells3 = "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_3GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.SUPPLIER_ID='" + selectedSupp + "' and b.NODE_TYPE='" + SelectedNodeType
					+ "' " + generateDateCondition(parsingDate, "b");

			try {
				notifications.headerNotifications(session, model);
				if (selectedSupp != null) {
					try {
						strNodesType = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strNodesType);
						listNodeType = session.createNativeQuery(strNodesType).list();

						if (listNodeType != null) {
							rtn.put("listNodeType", listNodeType);
						}
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Node Type Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Node Type Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						rtn.put("listNodeType", null);
					}
				}
				////////////////////////////////
				if (selectedSupp != null && SelectedNodeType != null) {
					try {
						strSites = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strSites);
						rtn.put("listSuppSites", session.createNativeQuery(strSites).list());

					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Sites Data from database in method Network_StNdCell due to \n "
										+ exceptionAsString);
						logger.info("Error in retreiving Sites Data from database in method Network_StNdCell due to \n "
								+ exceptionAsString);
						rtn.put("listSuppSites", null);
					}
				}
				/////////////////////////////////
				if (selectedItem != null) {
					try {
						strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countNodes,(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countGcells,(select count(*) from NODE_4GCELL c  where a.NODE_PK = c.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("c", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countLcells,(select count(*) from NODE_3GCELL d  where a.NODE_PK = d.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("d", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.WARE_ID='" + selectedItem
								+ "' and a.NODE_TYPE='" + SelectedNodeType + "' and " + "a.SUPPLIER_ID='" + selectedSupp
								+ "' " + generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);

						rtn.put("listSuppNodes", session.createNativeQuery(strNodes).list());
						// System.out.println("list nodes ==> "+
						// mapper.writeValueAsString(nodeSuppList));

					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Nodes Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Nodes Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						rtn.put("listSuppNodes", null);
					}
				}
				/////////////////////////////////
				if (selectedItem != null) {
					try {
						strCells1 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells1);
						strCells2 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells2);
						strCells3 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells3);

						cellResult.addAll(session.createNativeQuery(strCells1).list());
						cellResult.addAll(session.createNativeQuery(strCells2).list());
						cellResult.addAll(session.createNativeQuery(strCells3).list());

						rtn.put("listCells", cellResult);
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Cells Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Cells Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						rtn.put("listCells", null);
					}
				}
//////////////////////////////////					

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n "
								+ exceptionAsString);
				logger.info(
						"Error in retreiving Data from database in method FindOnClick_SuppNdTSiteNodeCell due to \n "
								+ exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
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
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {

			String selectedItem = request.getParameter("selectedItem");
			String selectedVen = request.getParameter("selectedVen");
			String SelectedNodeType = request.getParameter("SelectedNodeType");

			String paramEnterprise = request.getParameter("paramEnterprise");
			String paramTransmission = request.getParameter("paramTransmission");
			String paramRAN = request.getParameter("paramRAN");
			String paramCore = request.getParameter("paramCore");

			String parsingDate = request.getParameter("date");
			// System.out.println("parsingDate..."+parsingDate);

			String strSites = "SELECT distinct b.WARE_NAME,b.WARE_ID,b.LATITUDE,b.LONGITUDE,b.VENDOR,b.NODE_TYPE FROM NODE_ACTIVE b "
					+ "where b.WARE_ID!='0' and b.WARE_ID!='null' and b.WARE_ID is not null and b.VENDOR='"
					+ selectedVen + "' AND b.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateCondition(parsingDate, "b");

			List<String> listNodeType = new ArrayList<String>();
			String strNodesType = "SELECT DISTINCT b.NODE_TYPE,b.VENDOR FROM NODE_ACTIVE b WHERE b.VENDOR='"
					+ selectedVen + "' " + generateDateCondition(parsingDate, "b");

			String strNodes = "SELECT a.NODE_PK,a.SITE_ID,a.NODE_NAME,a.WARE_ID,a.NODE_TYPE,"
					+ "(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
					+ generateDateCondition(parsingDate, "a");

			List<Object[]> cellResult = new ArrayList<Object[]>();
			String strCells1 = "SELECT a.GCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_2GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.VENDOR='" + selectedVen + "' and b.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateCondition(parsingDate, "b");
			String strCells2 = "SELECT a.LCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_4GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.VENDOR='" + selectedVen + "' and b.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateCondition(parsingDate, "b");
			String strCells3 = "SELECT a.UCELL_ID,a.CELLNAME,a.NODE_PK,b.WARE_ID FROM NODE_3GCELL a,NODE_ACTIVE b WHERE b.NODE_PK=a.NODE_PK and b.WARE_ID= '"
					+ selectedItem + "' and b.VENDOR='" + selectedVen + "' and b.NODE_TYPE='" + SelectedNodeType + "' "
					+ generateDateCondition(parsingDate, "b");

			try {
				notifications.headerNotifications(session, model);
				if (selectedVen != null) {
					try {
						strNodesType = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strNodesType);
						listNodeType = session.createNativeQuery(strNodesType).list();

						if (listNodeType != null) {
							rtn.put("listNodeType", listNodeType);
						}
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Node Type Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Node Type Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						rtn.put("listNodeType", null);
					}
				}
				////////////////////////////////
				if (selectedVen != null && SelectedNodeType != null) {
					try {
						strSites = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strSites);
						rtn.put("listVenSites", session.createNativeQuery(strSites).list());

					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Sites Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Sites Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						rtn.put("listVenSites", null);
					}
				}
				/////////////////////////////////
				if (selectedItem != null) {
					try {
						strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countNodes,(select count(*) from NODE_2GCELL b  where a.NODE_PK = b.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countGcells,(select count(*) from NODE_4GCELL c  where a.NODE_PK = c.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("c", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes
								+ ") as countLcells,(select count(*) from NODE_3GCELL d  where a.NODE_PK = d.NODE_PK "
								+ generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("d", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);
						strNodes = strNodes + ") as countUcells FROM NODE_ACTIVE a WHERE a.WARE_ID='" + selectedItem
								+ "' and a.NODE_TYPE='" + SelectedNodeType + "' and " + "a.VENDOR='" + selectedVen
								+ "' " + generateDateCondition(parsingDate, "a");
						strNodes = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strNodes);

						rtn.put("listVenNodes", session.createNativeQuery(strNodes).list());
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Node Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Node Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						rtn.put("listVenNodes", null);
					}
				}
				/////////////////////////////////
				if (selectedItem != null) {
					try {
						strCells1 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells1);
						strCells2 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells2);
						strCells3 = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore,
								strCells3);

						cellResult.addAll(session.createNativeQuery(strCells1).list());
						cellResult.addAll(session.createNativeQuery(strCells2).list());
						cellResult.addAll(session.createNativeQuery(strCells3).list());

						rtn.put("listCells", cellResult);
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest(
								"Error in retreiving Cells Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						logger.info(
								"Error in retreiving Cells Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n "
										+ exceptionAsString);
						rtn.put("listCells", null);
					}
				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n "
								+ exceptionAsString);
				logger.info("Error in retreiving Data from database in method FindOnClick_VenNdTSiteNodeCell due to \n "
						+ exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
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

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/GetAllParsingDate", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllParsingDate(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		if (session != null && session.isOpen()) {
			try {
				notifications.headerNotifications(session, model);
				NativeQuery query = session.createNativeQuery(
						"SELECT DISTINCT TO_CHAR(PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM') FROM NODE_ACTIVE WHERE TO_CHAR(PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM') like UPPER(:param1) ORDER BY TO_CHAR(PARSING_DATE,'YYYY-MM-DD HH:MI:SS AM') DESC");
				query.setParameter("param1", "%" + request.getParameter("parsingDate") + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("datesList", query.list());
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in retreiving ParsingDate from database in method GetAllParsingDate due to \n "
						+ exceptionAsString);
				logger.info("Error in retreiving ParsingDate from database in method GetAllParsingDate due to \n "
						+ exceptionAsString);
				rtn.put("datesList", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	// PO BOQ data retrieving
	@RequestMapping(value = "/GetPOBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public LinkedHashMap<String, String> GetPOBoqList(@RequestParam String POID, @RequestParam String paramEnterprise,
			@RequestParam String paramTransmission, @RequestParam String paramRAN, @RequestParam String paramCore,
			@RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

		try {
			String strEmpty = "SELECT COUNT(DISTINCT a.PO_ID) FROM FIXED_ASSET_REGISTRY a, NODE_ACTIVE b, FAR_NODE c WHERE a.PO_ID!='null' AND a.PO_ID!='0' and a.PO_ID is not null AND b.NODE_ID=c.NODE_ID and a.FAR_ID = c.FAR_ID "
					+ generateDateConditionBoq(date, "b");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String strExist = "Select DISTINCT a.PO_ID From FIXED_ASSET_REGISTRY a, NODE_ACTIVE b, FAR_NODE c where a.PO_ID='" + POID
					+ "' and b.NODE_ID=c.NODE_ID and a.FAR_ID = c.FAR_ID " + generateDateConditionBoq(date, "b");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String PO_Query = POID == "" ? strEmpty : strExist;
			// System.out.println(PO_Query);
			Object PoC = session.createNativeQuery(PO_Query).uniqueResult();
			// System.out.println("po :::"+PoC);
			BoqHM.put("PO", String.valueOf(PoC));
			strEmpty = "";
			strExist = "";

			strEmpty = "SELECT ROUND(SUM(a.INITIALCOST), 2) FROM FIXED_ASSET_REGISTRY a, NODE_ACTIVE c WHERE a.WARE_ID=c.WARE_ID "
					+ generateDateConditionBoq(date, "c");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "Select a.TOTAL_AMOUNT from PURCHASE_ORDER a where a.PO_ID='" + POID+"'";					
			String PO_Amount_Query = POID == "" ? strEmpty : strExist;
			//System.out.println(PO_Amount_Query);
			Object PO_Amount = session.createNativeQuery(PO_Amount_Query).uniqueResult();
			BoqHM.put(POID == "" ? "PO Cost" : "PO Amount", String.valueOf(PO_Amount));
			strEmpty = "";
			strExist = "";

			strEmpty = "SELECT ROUND(SUM(a.NETCOST), 2) FROM FIXED_ASSET_REGISTRY a, NODE_ACTIVE c WHERE a.WARE_ID=c.WARE_ID "
					+ generateDateConditionBoq(date, "c");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "Select a.NET_TOTAL_AMOUNT from PURCHASE_ORDER a where a.PO_ID='" + POID +"'";
			String PO_NET_Amount_Query = POID == "" ? strEmpty : strExist;
			// System.out.println(PO_NET_Amount_Query);
			Object PO_NET_Amount = session.createNativeQuery(PO_NET_Amount_Query).uniqueResult();
			BoqHM.put(POID == "" ? "PO Net Cost" : "PO Net Amount", String.valueOf(PO_NET_Amount));
			strEmpty = "";
			strExist = "";

			strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM FAR_SITE a, FIXED_ASSET_REGISTRY b, NODE_ACTIVE c where a.FAR_ID = b.FAR_ID AND a.WARE_ID=c.WARE_ID "
					+ generateDateConditionBoq(date, "c");
			strEmpty = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "Select COUNT(DISTINCT a.WARE_ID) FROM FAR_SITE a, FIXED_ASSET_REGISTRY b, NODE_ACTIVE c where a.FAR_ID = b.FAR_ID and b.PO_ID='"
					+ POID + "' AND a.WARE_ID=c.WARE_ID " + generateDateConditionBoq(date, "c");
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Site_Query = POID == "" ? strEmpty : strExist;
			// System.out.println(Site_Query);
			Object SiteC = session.createNativeQuery(Site_Query).uniqueResult();
			// System.out.println("SiteC :::"+SiteC);
			strEmpty = "";
			strExist = "";

			strEmpty = "SELECT COUNT(DISTINCT a.ITEM_CODE) FROM FIXED_ASSET_REGISTRY a, FAR_SITE b, NODE_ACTIVE c where a.FAR_ID = b.FAR_ID AND b.WARE_ID=c.WARE_ID "
					+ generateDateConditionBoq(date, "c");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "Select COUNT(DISTINCT a.ITEM_CODE) from FIXED_ASSET_REGISTRY a, FAR_SITE b, NODE_ACTIVE c where a.FAR_ID = b.FAR_ID and PO_ID='"
					+ POID + "' AND b.WARE_ID=c.WARE_ID " + generateDateConditionBoq(date, "c");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Item_Query = POID == "" ? strEmpty : strExist;
			// System.out.println(Item_Query);
			Object ItemC = session.createNativeQuery(Item_Query).uniqueResult();
			// System.out.println("ItemC :::"+ItemC);
			strEmpty = "";
			strExist = "";

			BoqHM.put("Sites", String.valueOf(SiteC));
			BoqHM.put("Items", String.valueOf(ItemC));
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest(
					"Error in retreiving PO BOQ from database in method GetPOBoqList due to \n " + exceptionAsString);
			logger.info(
					"Error in retreiving PO BOQ from database in method GetPOBoqList due to \n " + exceptionAsString);
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return BoqHM;
	}

	@RequestMapping(value = "/findMarkersInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findMarkersInfo(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {

			String selectedItem = request.getParameter("selectedItem");
			String enterprise = request.getParameter("paramEnterprise");
			String transmission = request.getParameter("paramTransmission");
			String RAN = request.getParameter("paramRAN");
			String core = request.getParameter("paramCore");
			String parsingDate = request.getParameter("date");
			String strquery ="";

			int[] arrayParam = new int[4];
			arrayParam[0] = 0; // enterprise
			arrayParam[1] = 0; // transmission
			arrayParam[2] = 0; // RAN
			arrayParam[3] = 0; // core
			
			if (enterprise.equalsIgnoreCase("true")) {
				arrayParam[0] = 1;
			}
			if (transmission.equalsIgnoreCase("true")) {
				arrayParam[1] = 1;
			}
			if (RAN.equalsIgnoreCase("true")) {
				arrayParam[2] = 1;
			}
			if (core.equalsIgnoreCase("true")) {
				arrayParam[3] = 1;
			}
			
			
			try {	
				System.out.println("selectedItem "+selectedItem);
				notifications.headerNotifications(session, model);

			if(selectedItem.contains("WARE")) {
			 	strquery = "SELECT DISTINCT a.SITE_ID,a.WARE_NAME,a.WARE_ID,a.LATITUDE,a.LONGITUDE,"
				+ "(select COUNT(*) from NODE_ACTIVE w where w.WARE_ID='"
				+ selectedItem + "' " + generateDateCondition(parsingDate, "w");
			
				strquery = AppendQuery("w", arrayParam, strquery);
				
				strquery = strquery
						+ ") as countNodes,(select COUNT(*) FROM NODE_2GCELL c where c.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID='"
						+ selectedItem + "' " + generateDateCondition(parsingDate, "o");
				strquery = AppendQuery("o", arrayParam, strquery);
				strquery = strquery + " ) ";
				strquery = AppendQuery("c", arrayParam, strquery);
				strquery = strquery
						+ ") as countGcells,(select COUNT(*) FROM NODE_4GCELL d where d.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID='"
						+ selectedItem + "' " + generateDateCondition(parsingDate, "o");
				strquery = AppendQuery("o", arrayParam, strquery);
				strquery = strquery + " ) ";
				strquery = AppendQuery("d", arrayParam, strquery);
				strquery = strquery
						+ ") as countLcells,(select COUNT(*) FROM NODE_3GCELL e where e.NODE_PK IN (select NODE_PK from NODE_ACTIVE o where o.WARE_ID='"
						+ selectedItem + "' " + generateDateCondition(parsingDate, "o");
				strquery = AppendQuery("o", arrayParam, strquery);
				strquery = strquery + " ) ";
				strquery = AppendQuery("e", arrayParam, strquery);
				strquery = strquery
						+ ") as countUcells FROM NODE_ACTIVE a where a.WARE_ID='"
						+ selectedItem + "' " + generateDateCondition(parsingDate, "a");
				strquery = AppendQuery("a", arrayParam, strquery);
				
			}
			else if(selectedItem.contains("NODE") && !selectedItem.contains("CELL") ) {
				strquery = "SELECT b.SITE_ID,b.WARE_NAME,b.NODE_PK,b.LATITUDE,b.LONGITUDE,"
						+ "(select COUNT(*) from NODE_ACTIVE w where b.NODE_PK=w.NODE_PK"
				 + generateDateCondition(parsingDate, "w");
				strquery = AppendQuery("w", arrayParam, strquery);
				
				strquery = strquery
						+ ") as countNodes,(select count(*) from NODE_2GCELL e  where b.NODE_PK = e.NODE_PK "
						+ generateDateCondition(parsingDate, "b");
				strquery = AppendQuery("e", arrayParam, strquery);
				strquery = strquery
						+ ") as countGcells,(select count(*) from NODE_4GCELL c  where b.NODE_PK = c.NODE_PK "
						+ generateDateCondition(parsingDate, "b");
				strquery = AppendQuery("c", arrayParam, strquery);
				strquery = strquery
						+ ") as countLcells,(select count(*) from NODE_3GCELL d  where b.NODE_PK = d.NODE_PK "
						+ generateDateCondition(parsingDate, "b");
				strquery = AppendQuery("d", arrayParam, strquery);
				strquery = strquery
						+ ") as countUcells,b.WARE_ID,b.NODE_NAME,b.NODE_TYPE FROM NODE_ACTIVE b WHERE b.NODE_PK='"
						+ selectedItem + "'" + generateDateCondition(parsingDate, "b");
				strquery = AppendQuery("b", arrayParam, strquery);

			}
			else if(selectedItem.contains("CELL")) {
				strquery = "SELECT DISTINCT b.SITE_ID,b.WARE_NAME,j.GCELL_ID,b.LATITUDE,b.LONGITUDE,j.NODE_PK,j.CELLNAME,b.WARE_ID,b.NODE_NAME FROM NODE_ACTIVE b LEFT JOIN NODE_2GCELL j ON b.NODE_PK = j.NODE_PK WHERE j.GCELL_ID='"
						+ selectedItem + "'" + generateDateCondition(parsingDate, "b");
				strquery = AppendQuery("b", arrayParam, strquery);
			
				strquery =strquery
						+" union SELECT DISTINCT b.SITE_ID,b.WARE_NAME,j.LCELL_ID,b.LATITUDE,b.LONGITUDE,j.NODE_PK,j.CELLNAME,b.WARE_ID,b.NODE_NAME FROM NODE_ACTIVE b LEFT JOIN NODE_4GCELL j ON b.NODE_PK = j.NODE_PK WHERE j.LCELL_ID='"																		
						+ selectedItem + "'" + generateDateCondition(parsingDate, "b");
				strquery = AppendQuery("b", arrayParam, strquery);
			
				strquery = strquery
						+" union SELECT DISTINCT b.SITE_ID,b.WARE_NAME,j.UCELL_ID,b.LATITUDE,b.LONGITUDE,j.NODE_PK,j.CELLNAME,b.WARE_ID,b.NODE_NAME FROM NODE_ACTIVE b LEFT JOIN NODE_3GCELL j ON b.NODE_PK = j.NODE_PK WHERE j.UCELL_ID='"
						+ selectedItem + "'" + generateDateCondition(parsingDate, "b");
				strquery = AppendQuery("b", arrayParam, strquery);
			}
				//System.out.println("query "+strquery);
				rtn.put("siteInfo", session.createNativeQuery(strquery).list());
			
			}catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest(
						"Error in retreiving Info Data from database in method findMarkersInfo due to \n "
								+ exceptionAsString);
				logger.info("Error in retreiving Info Data from database in method findMarkersInfo due to \n "
						+ exceptionAsString);
				rtn.put("siteInfo", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}
	
	// PO BOQ data retrieving
	@RequestMapping(value = "/GetPOSiteBoqList", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public LinkedHashMap<String, String> GetPOSiteBoqList(@RequestParam String POID,
			@RequestParam String paramEnterprise, @RequestParam String paramTransmission, @RequestParam String paramRAN,
			@RequestParam String paramCore, @RequestParam String date) {

		session = AlmDbSession.getInstance().getSession();
		LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

		try {
			String strEmpty = "SELECT COUNT(DISTINCT a.PO_ID) FROM FIXED_ASSET_REGISTRY a, FAR_NODE b, NODE_ACTIVE c WHERE b.NODE_ID=c.NODE_ID and a.FAR_ID = b.FAR_ID "
					+ generateDateConditionBoq(date, "c");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			String strExist = "Select DISTINCT a.PO_ID From FIXED_ASSET_REGISTRY a, FAR_NODE b, NODE_ACTIVE c where a.PO_ID='" + POID
					+ "' AND b.NODE_ID=c.NODE_ID and a.FAR_ID = b.FAR_ID " + generateDateConditionBoq(date, "c");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String PO_Query = POID == "" ? strEmpty : strExist;
			// System.out.println(PO_Query);
			Object Po = session.createNativeQuery(PO_Query).uniqueResult();
			strEmpty = "";
			strExist = "";

			strEmpty = "SELECT COUNT(DISTINCT a.WARE_ID) FROM FAR_SITE a, FIXED_ASSET_REGISTRY b,NODE_ACTIVE c where a.FAR_ID = b.FAR_ID AND a.WARE_ID=c.WARE_ID "
					+ generateDateConditionBoq(date, "c");
			strEmpty = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "Select COUNT(DISTINCT a.SITE_NAME) FROM FAR_SITE a, FIXED_ASSET_REGISTRY b,NODE_ACTIVE c where a.FAR_ID = b.FAR_ID and b.PO_ID='"
					+ POID + "' AND a.WARE_ID=c.WARE_ID " + generateDateConditionBoq(date, "c");
			strExist = boqDomainVar("b", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Site_Query = POID == "" ? strEmpty : strExist;
			//System.out.println(Site_Query);
			Object Site = session.createNativeQuery(Site_Query).uniqueResult();
			strEmpty = "";
			strExist = "";

			strEmpty = "SELECT COUNT(DISTINCT a.ITEM_CODE) FROM FIXED_ASSET_REGISTRY a, FAR_SITE b,NODE_ACTIVE c where a.FAR_ID = b.FAR_ID AND b.WARE_ID=c.WARE_ID "
					+ generateDateConditionBoq(date, "c");
			strEmpty = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strEmpty);
			strExist = "Select COUNT(DISTINCT a.ITEM_CODE) from FIXED_ASSET_REGISTRY a, FAR_SITE b,NODE_ACTIVE c where a.FAR_ID = b.FAR_ID and a.PO_ID='"
					+ POID + "' AND b.WARE_ID=c.WARE_ID " + generateDateConditionBoq(date, "c");
			strExist = boqDomainVar("a", paramEnterprise, paramTransmission, paramRAN, paramCore, strExist);
			String Item_Query = POID == "" ? strEmpty : strExist;
			// System.out.println(Item_Query);
			Object Item = session.createNativeQuery(Item_Query).uniqueResult();
			strEmpty = "";
			strExist = "";

			BoqHM.put("PO", String.valueOf(Po));

			if (POID != "") {
				String PO_Amount_Query = "Select TOTAL_AMOUNT from PURCHASE_ORDER  where PO_ID='"
						+ POID + "'";
				 //System.out.println(PO_Amount_Query);
				Object PO_Amount = session.createNativeQuery(PO_Amount_Query).uniqueResult();
				BoqHM.put("PO Amount", String.valueOf(PO_Amount));

				String PO_Net_Amount_Query = "Select NET_TOTAL_AMOUNT from PURCHASE_ORDER where PO_ID='"
						+ POID + "'";
				// System.out.println(PO_Net_Amount_Query);
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
			logger.finest("Error in retreiving PO BOQ from database in method GetPOSiteBoqList due to \n "
					+ exceptionAsString);
			logger.info("Error in retreiving PO BOQ from database in method GetPOSiteBoqList due to \n "
					+ exceptionAsString);
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}