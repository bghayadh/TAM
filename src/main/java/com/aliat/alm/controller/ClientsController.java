package com.aliat.alm.controller;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.ClientsListView;

import com.aliat.alm.models.Clients;

@Controller
public class ClientsController {
	private static final Logger logger = Logger.getLogger(ClientsController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;

	
	@Autowired
	Permissions permissions;
	
	@Autowired
	Form form;
	
	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notification;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ClientsListView", method = RequestMethod.GET)

	public String ClientsListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {
				List<ClientsListView> listClients = new ArrayList<ClientsListView>();

				String str = "select c.CLIENT_ID as clientId, c.MOBILE_NUMBER as mobile, c.FIRST_NAME as firstName, c.LAST_NAME as lastName,c.CLIENT_ID_NUMBER as clientIdNumber,TO_CHAR(c.CREATED_DATE,'YYYY-MM-DD HH24:MI:SS') as createdDate,"
						+ "TO_CHAR(c.LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate,"
						+ "nvl(c.AGENT_NUMBER,'-') as agentNumber," + "c.Status as status,"
						+ "nvl(c.registration_status,'Not Registered') as regStatus,decode(c.tkash_registration_status,'0','T-Kash Not Registered','Success','Success','Failed','Failed') as tkashregstatus,"
						+ "nvl((select a.full_name from agent a where c.agent_number = a.msisdn),'-') as agentFullName "
						+ " from CLIENTS c" + " order by c.LAST_MODIFIED_DATE DESC";

				Query query = session.createSQLQuery(str);
				listClients = ((SQLQuery) query).addScalar("clientId").addScalar("mobile").addScalar("firstName")
						.addScalar("lastName").addScalar("clientIdNumber").addScalar("createdDate").addScalar("lastModifiedDate")
						.addScalar("agentNumber").addScalar("agentFullName").addScalar("status").addScalar("regStatus")
						.addScalar("tkashregstatus")
						.setResultTransformer(Transformers.aliasToBean(ClientsListView.class)).list();

				model.addAttribute("ListGridTable", mapper.writeValueAsString(listClients));

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ClientsListView due to \n "+ exceptionAsString);
				logger.info("Error in ClientsListView due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return "ClientsListView";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ClientsImageListView", method = RequestMethod.GET)

	public String ClientsImageListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {
				
				permissions.setPerms(model, permissions.getUserPermsWithSession(session, request), "Image Approval",
						"List");
				Query query = session.createSQLQuery("Select CLIENT_ID,MOBILE_NUMBER,FIRST_NAME || ' ' || LAST_NAME,decode(PHOTOS_APPROVAL_STATUS,'0','Pending Approval','Approved','Approved','Rejected','Rejected'),"
						+"nvl((select DISTINCT (REJECTION_REASON) from PHOTOS_APPROVAL_LOG b where clients.CLIENT_ID = b.CLIENT_ID ORDER BY b.transaction_date DESC FETCH FIRST 1 ROW ONLY),'-'),"
						+ "'View Images',CLIENT_ID_NUMBER,TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS')," + 
						"nvl(AGENT_NUMBER,'-'),nvl((select full_name from agent a where clients.agent_number = a.msisdn),'-'),ID_BACK_SIDE_PHOTO,ID_FRONT_SIDE_PHOTO," + 
						"nvl((select DISTINCT (USERNAME) from PHOTOS_APPROVAL_LOG b where clients.CLIENT_ID = b.CLIENT_ID ORDER BY b.transaction_date DESC FETCH FIRST 1 ROW ONLY),'-') " + 
						" from CLIENTS" + 
						" WHERE REGISTRATION_STATUS ='Success'" + 
						" order by LAST_MODIFIED_DATE DESC");
				
				System.out.println(mapper.writeValueAsString(query.list()));

				model.addAttribute("ListGridTable", mapper.writeValueAsString(query.list()));

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ClientsListView due to \n "+ exceptionAsString);
				logger.info("Error in ClientsListView due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return "ClientsImageListView";
	}
	
	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getClientImages_Approval", method = RequestMethod.GET)
	@ResponseBody
	public String getClientImages_Approval(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String data = null;
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		
		System.out.println(request.getParameter("data"));
		session = almsessions.getALMSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {
				
				Query query = session.createSQLQuery("Select CLIENT_ID,MOBILE_NUMBER,CLIENT_ID_NUMBER,TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS'),"
						+ "nvl(AGENT_NUMBER,'-'),nvl((select full_name from agent a where clients.agent_number = a.msisdn),'-'),ID_BACK_SIDE_PHOTO,ID_FRONT_SIDE_PHOTO,nvl(PHOTOS_APPROVAL_STATUS,'-'),'View Images' "
						+ " from CLIENTS"
						+ " WHERE REGISTRATION_STATUS ='Success'"
						+ " order by LAST_MODIFIED_DATE DESC");
				
				System.out.println(mapper.writeValueAsString(query.list()));
				//model.addAttribute("clientImagesData", query.list());
				data = mapper.writeValueAsString(query.list());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ClientsListView due to \n "+ exceptionAsString);
				logger.info("Error in ClientsListView due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return data;
	}
*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SortSequence", method = RequestMethod.GET)
	@ResponseBody
	public String SortSequence(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String data = null;
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
	
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {

				List<String> sortedList = session.createSQLQuery("Select CLIENT_ID from CLIENTS WHERE CLIENT_ID LIKE '%_2023_%' ORDER BY CREATED_DATE ASC").list();
				for( int i=0;i<sortedList.size();i++) {
										
					Query query=null;
					int seq = i+1;
					String ClientID = "CLT_2023_"+seq;
					query = session.createSQLQuery("UPDATE CLIENTS SET CLIENT_ID ='"+ClientID+"' where CLIENT_ID='"+sortedList.get(i)+"'");
					query.executeUpdate();
					
					query = session.createSQLQuery("SELECT CLIENT_ID FROM SIMREG_LOG where CLIENT_ID='"+sortedList.get(i)+"'");
					
					System.out.println("in sim reg : "+query.list().size());
					if(query.list().size() > 0)
					{
						query = session.createSQLQuery("UPDATE SIMREG_LOG SET CLIENT_ID ='"+ClientID+"' where CLIENT_ID='"+sortedList.get(i)+"'");
						query.executeUpdate();
					}
					
					query = session.createSQLQuery("SELECT CLIENT_ID FROM TKASHREG_LOG where CLIENT_ID='"+sortedList.get(i)+"'");
					System.out.println("in tkash : "+query.list().size());
					if(query.list().size() > 0)
					{
						query = session.createSQLQuery("UPDATE TKASHREG_LOG SET CLIENT_ID ='"+ClientID+"' where CLIENT_ID='"+sortedList.get(i)+"'");
						query.executeUpdate();
					}
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ClientsListView due to \n "+ exceptionAsString);
				logger.info("Error in ClientsListView due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return data;
	}
	@RequestMapping(value = "/FilteredClientsListView", method = RequestMethod.GET)
	@ResponseBody
	public String FilteredClientsListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			return "redirect:/";
		}
		String result = null;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {

				String startdate, enddate, mobileNumber, Fname, Lname, agentName, agentNumber, status, regStatus,
						TkashStatus,region,area;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				mobileNumber = request.getParameter("mobileNumber");
				Fname = request.getParameter("Fname");
				Lname = request.getParameter("Lname");
				agentName = request.getParameter("agentName");
				agentNumber = request.getParameter("agentNumber");
				status = request.getParameter("status");
				regStatus = request.getParameter("regStatus");
				TkashStatus = request.getParameter("TkashStatus");
				region = request.getParameter("region");
				area = request.getParameter("area");

				List<ClientsListView> listClients = new ArrayList<ClientsListView>();

				String str = "select c.CLIENT_ID as clientId, c.MOBILE_NUMBER as mobile, c.FIRST_NAME as firstName, c.LAST_NAME as lastName,c.CLIENT_ID_NUMBER as clientIdNumber,TO_CHAR(c.CREATED_DATE,'YYYY-MM-DD HH24:MI:SS') as createdDate,"
						+ "TO_CHAR(c.LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate,"
						+ "nvl(c.AGENT_NUMBER,'-') as agentNumber," + "c.Status as status,"
						+ "nvl(c.registration_status,'Not Registered') as regStatus,decode(c.tkash_registration_status,'0','T-Kash Not Registered','Success','Success','Failed','Failed') as tkashregstatus,"
						+ "nvl((select a.full_name from agent a where c.agent_number = a.msisdn),'-') as agentFullName"
						+ " from CLIENTS c";
				if (startdate != null && enddate != null) {
					str = str + " where c.CREATED_DATE between TO_DATE('" + startdate + "','YYYY-MM-DD') and TO_DATE('"
							+ enddate + "','YYYY-MM-DD')";
				}

				if (Fname != null && !Fname.equalsIgnoreCase("")) {

					str = str + " and upper(c.FIRST_NAME) LIKE upper('%" + Fname + "%')";
				}

				if (Lname != null && !Lname.equalsIgnoreCase("")) {

					str = str + " and upper(c.LAST_NAME) LIKE upper('%" + Lname + "%')";
				}

				if (mobileNumber != null && !mobileNumber.equalsIgnoreCase("")) {

					str = str + " and c.MOBILE_NUMBER LIKE '%" + mobileNumber + "%'";
				}
				if (agentNumber != null && !agentNumber.equalsIgnoreCase("")) {

					str = str + " and c.AGENT_NUMBER LIKE '%" + agentNumber + "%'";
				}

				if (status != null && !status.equalsIgnoreCase("")) {

					str = str + " and upper(c.STATUS) LIKE upper('%" + status + "%')";
				}

				if (region != null && !region.equalsIgnoreCase("")) {

					str = str + " and upper(c.REGION_ID) LIKE upper('%" + region + "%') or upper(c.REGION_NAME) LIKE upper('%" + region + "%')";
				}
				if (area != null && !area.equalsIgnoreCase("")) {

					str = str + " and upper(c.AREA_ID) LIKE upper('%" + area + "%') or upper(c.AREA_NAME) LIKE upper('%" + area + "%')";
				}
				if (regStatus != null && !regStatus.equalsIgnoreCase("")) {

					if (regStatus.contains("not") || regStatus.contains("Not")) {

						str = str + " and c.REGISTRATION_STATUS IS null";
					} else {
						str = str + " and upper(c.REGISTRATION_STATUS) LIKE upper('%" + regStatus + "%')";
					}
				}

				if (TkashStatus != null && !TkashStatus.equalsIgnoreCase("")) {

					if (TkashStatus.contains("not") || TkashStatus.contains("Not")) {
						TkashStatus = "0";
					}
					str = str + " and upper(c.tkash_registration_status) LIKE upper('%" + TkashStatus + "%')";
				}

				str = str + " order by c.LAST_MODIFIED_DATE DESC";

				query = session.createSQLQuery(str);
				listClients = ((SQLQuery) query).addScalar("clientId").addScalar("mobile").addScalar("firstName")
						.addScalar("lastName").addScalar("clientIdNumber").addScalar("createdDate").addScalar("lastModifiedDate")
						.addScalar("agentNumber").addScalar("agentFullName").addScalar("status").addScalar("regStatus")
						.addScalar("tkashregstatus")
						.setResultTransformer(Transformers.aliasToBean(ClientsListView.class)).list();

				result = mapper.writeValueAsString(listClients);
				System.out.println("Filtered Array: " + result);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in FilteredClientsListView due to \n "+ exceptionAsString);
				logger.info("Error in FilteredClientsListView due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return result;
	}
	@RequestMapping(value = "/FilteredClientsImageListView", method = RequestMethod.GET)
	@ResponseBody
	public String FilteredClientsImageListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			return "redirect:/";
		}
		String result = null;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {

				String startdate, enddate, mobileNumber, Fname, Lname, agentName, agentNumber, status, appStatus,
						TkashStatus,region,area;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				mobileNumber = request.getParameter("mobileNumber");
				Fname = request.getParameter("Fname");
				Lname = request.getParameter("Lname");
				agentName = request.getParameter("agentName");
				agentNumber = request.getParameter("agentNumber");
				status = request.getParameter("status");
				appStatus = request.getParameter("appStatus");
				TkashStatus = request.getParameter("TkashStatus");
				region = request.getParameter("region");
				area = request.getParameter("area");

				List<ClientsListView> listClients = new ArrayList<ClientsListView>();
				
						
				String str = "select CLIENT_ID, MOBILE_NUMBER , FIRST_NAME || ' ' || LAST_NAME ,decode(PHOTOS_APPROVAL_STATUS,'0','Pending Approval','Approved','Approved','Rejected','Rejected'),'View Images',CLIENT_ID_NUMBER ,"
						+ "TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS'),"
						+ "nvl(AGENT_NUMBER,'-')," 
						+ "nvl((select full_name from agent a where clients.agent_number = a.msisdn),'-') ,"
						+"ID_BACK_SIDE_PHOTO  , ID_FRONT_SIDE_PHOTO,"
						+"nvl((select DISTINCT (USERNAME) from PHOTOS_APPROVAL_LOG b where clients.MOBILE_NUMBER = b.CLIENT_MSISDN ORDER BY b.transaction_date DESC FETCH FIRST 1 ROW ONLY),'-') "
						+ " from CLIENTS "
						+ " WHERE REGISTRATION_STATUS ='Success'";
				if (startdate != null && enddate != null) {
					str = str + " and CREATED_DATE between TO_DATE('" + startdate + "','YYYY-MM-DD') and TO_DATE('"
							+ enddate + "','YYYY-MM-DD')";
				}

				if (Fname != null && !Fname.equalsIgnoreCase("")) {

					str = str + " and upper(FIRST_NAME) LIKE upper('%" + Fname + "%')";
				}

				if (Lname != null && !Lname.equalsIgnoreCase("")) {

					str = str + " and upper(LAST_NAME) LIKE upper('%" + Lname + "%')";
				}

				if (mobileNumber != null && !mobileNumber.equalsIgnoreCase("")) {

					str = str + " and MOBILE_NUMBER LIKE '%" + mobileNumber + "%'";
				}
				if (agentNumber != null && !agentNumber.equalsIgnoreCase("")) {

					str = str + " and AGENT_NUMBER LIKE '%" + agentNumber + "%'";
				}

				if (status != null && !status.equalsIgnoreCase("")) {

					str = str + " and upper(STATUS) LIKE upper('%" + status + "%')";
				}

				if (appStatus != null && !appStatus.equalsIgnoreCase("")) {
                     if(appStatus.equalsIgnoreCase("Pending Approval"))
                     { appStatus="0";
                     }
            
					str = str + " and upper(PHOTOS_APPROVAL_STATUS) LIKE upper('%" + appStatus + "%')";
				}
				if (region != null && !region.equalsIgnoreCase("")) {

					str = str + " and upper(REGION_ID) LIKE upper('%" + region + "%') or upper(REGION_NAME) LIKE upper('%" + region + "%')";
				}
				if (area != null && !area.equalsIgnoreCase("")) {

					str = str + " and upper(AREA_ID) LIKE upper('%" + area + "%') or upper(AREA_NAME) LIKE upper('%" + area + "%')";
				}
				
				if (TkashStatus != null && !TkashStatus.equalsIgnoreCase("")) {

					if (TkashStatus.contains("not") || TkashStatus.contains("Not")) {
						TkashStatus = "0";
					}
					str = str + " and upper(tkash_registration_status) LIKE upper('%" + TkashStatus + "%')";
				}

				str = str + " order by LAST_MODIFIED_DATE DESC";

				query = session.createSQLQuery(str);
				/*listClients = ((SQLQuery) query).addScalar("clientId").addScalar("mobile").addScalar("firstName")
						.addScalar("clientIdNumber").addScalar("createdDate").addScalar("lastModifiedDate")
						.addScalar("agentNumber").addScalar("agentFullName").addScalar("status").addScalar("regStatus")
						.addScalar("tkashregstatus")
						.setResultTransformer(Transformers.aliasToBean(ClientsListView.class)).list();
*/
				//result = mapper.writeValueAsString(listClients);
				result = mapper.writeValueAsString(query.list());
				System.out.println("Filtered Array: " + result);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in FilteredClientsImageListView due to \n "+ exceptionAsString);
				logger.info("Error in FilteredClientsImageListView due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ClientsFormView", method = RequestMethod.GET)
	public String ClientsFormView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		String clientId = request.getParameter("clientID"), navAction = "2";
		Clients clients = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String result[] = new String[4];
		int SelectedIndex = 0;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notification.headerNotifications(session, model);
			try {
				navAction = request.getParameter("NavAction");
				if (StringUtils.equalsIgnoreCase(clientId, null)) {
					model.addAttribute("ListClient", "addNew");
					model.addAttribute("creationDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("lastModifiedDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("CLIENT_IMAGE", "addNew");
					model.addAttribute("CLIENT_FRONT_ID", "addNew");
					model.addAttribute("CLIENT_BACK_ID", "addNew");
					model.addAttribute("SIGNATURE", "addNew");
					model.addAttribute("SelectedIndex", "addNew");
					model.addAttribute("ClientsCount", "addNew");
					model.addAttribute("docStatus", "addNew");
					model.addAttribute("ordStatus", "addNew");
					model.addAttribute("regStatus", "");
					model.addAttribute("regStatusLog", "addNew");
					model.addAttribute("tkashStatus", "");
					model.addAttribute("tkashStatusLog", "addNew");
					model.addAttribute("approvalStatus","addNew");
					model.addAttribute("imageLog","addNew");
					return "ClientsFormView";

				} else {
					result = form.NavigationNP(session, "CLIENTS", "CLIENT_ID", clientId, "LAST_MODIFIED_DATE",
							navAction);
					SelectedIndex = Integer.parseInt(result[1]);
					clientId = result[2];
					clients = (Clients) session.get(Clients.class, clientId);
					model.addAttribute("clientID", clients.getClientId());
					model.addAttribute("creationDate", formatter.format(clients.getCreatedDate()).toString());
					model.addAttribute("lastModifiedDate", formatter.format(clients.getLastModifiedDate()).toString());
					model.addAttribute("clientID", clients.getClientId());
					model.addAttribute("dspName", clients.getDisplayName());
					model.addAttribute("firstName", clients.getFirstName());
					model.addAttribute("lastName", clients.getLastName());
					model.addAttribute("mobile", clients.getMobile());
					model.addAttribute("dep", clients.getDepartment());
					model.addAttribute("loc", clients.getLocation());
					model.addAttribute("clientDesc", clients.getDescreption());
					model.addAttribute("ordStatus", clients.getStatus());
					System.out.println("ordStatus : " + clients.getStatus());
					model.addAttribute("normlat", clients.getLatitude());
					model.addAttribute("normlng", clients.getLongitude());
					model.addAttribute("sellat", clients.getSellingLatitude());
					model.addAttribute("sellng", clients.getSellingLongitude());
					model.addAttribute("agentNumber", clients.getAgentNumber());
					model.addAttribute("SelectedIndex", SelectedIndex);
					model.addAttribute("ClientsCount", Integer.parseInt(result[0]));
					model.addAttribute("idNumber",clients.getClientIdNumber());

					if(StringUtils.equalsIgnoreCase(clients.getClientStatus(),"1")) {
						if (StringUtils.equalsIgnoreCase(clients.getClientImage(), "")
								|| clients.getClientImage() == null) {
							model.addAttribute("CLIENT_IMAGE", "addNew");
						} else {
							model.addAttribute("CLIENT_IMAGE", clients.getClientImage());
						}
					}else {
						model.addAttribute("CLIENT_IMAGE", "addNew");
					}
					if(StringUtils.equalsIgnoreCase(clients.getFrontIDStatus(), "1")) {

						if (StringUtils.equalsIgnoreCase(clients.getClientFrontId(), "")
								|| clients.getClientFrontId() == null) {
							model.addAttribute("CLIENT_FRONT_ID", "addNew");
						} else {
							model.addAttribute("CLIENT_FRONT_ID", clients.getClientFrontId());							
						}
					}else {
						model.addAttribute("CLIENT_FRONT_ID", "addNew");
					}
				
					if(StringUtils.equalsIgnoreCase(clients.getBackIDStatus(),"1")) {
						if (StringUtils.equalsIgnoreCase(clients.getClientBackId(), "")
								|| clients.getClientBackId() == null) {
							model.addAttribute("CLIENT_BACK_ID", "addNew");
						} else {
							model.addAttribute("CLIENT_BACK_ID", clients.getClientBackId());
						}
					}else {
						model.addAttribute("CLIENT_BACK_ID", "addNew");
					}
					
					if(StringUtils.equalsIgnoreCase(clients.getSignatureStatus(),"1")) {
						if (StringUtils.equalsIgnoreCase(clients.getSignature(), "") || clients.getSignature() == null) {
							model.addAttribute("SIGNATURE", "addNew");
						} else {
							model.addAttribute("SIGNATURE", clients.getSignature());
						}
					}else {
						model.addAttribute("SIGNATURE", "addNew");
					}
				

					// get registration status
					query = session.createSQLQuery(
							"SELECT nvl(REGISTRATION_STATUS,'Not Registered') from CLIENTS where CLIENT_ID='" + clientId
									+ "'");
					String regStatus = mapper.writeValueAsString(query.list());
					regStatus = regStatus.replace("[", "").replace("]", "");
					if (regStatus.contains("\"")) {
						regStatus = regStatus.replace("\"", "");
					}
					if (regStatus != null) {

						List<String> regStatusLog = new ArrayList<String>();
						model.addAttribute("regStatus", regStatus);
						regStatusLog = session
								.createSQLQuery("SELECT TO_CHAR(REGISTRATIONDATE,'YYYY-MM-DD HH24:MI:SS'),"
										+ "SIM_REG_STATUS,RESPONSE_CODE,RESPONSE_MESSAGE,AGENT_NUMBER,AGENT_NAME,MOBILE_NUMBER FROM SIMREG_LOG "
										+ "where CLIENT_ID='" + clientId + "' order by REGISTRATIONDATE DESC")
								.list();

						model.addAttribute("regStatusLog", mapper.writeValueAsString(regStatusLog));
					} else {
						model.addAttribute("regStatus", "");
						model.addAttribute("regStatusLog", "addNew");
					}

					// get tkash registration status
					query = session.createSQLQuery(
							"SELECT decode(tkash_registration_status,'0','T-Kash Not Registered','Success','Success','Failed','Failed') from CLIENTS where CLIENT_ID='"
									+ clientId + "'");
					String tkashStatus = mapper.writeValueAsString(query.list());
					tkashStatus = tkashStatus.replace("[", "").replace("]", "");
					if (tkashStatus.contains("\"")) {
						tkashStatus = tkashStatus.replace("\"", "");
					}
					if (tkashStatus != null) {

						List<String> tkashStatusLog = new ArrayList<String>();
						model.addAttribute("tkashStatus", tkashStatus);
						tkashStatusLog = session
								.createSQLQuery("SELECT TO_CHAR(REGISTRATIONDATE,'YYYY-MM-DD HH24:MI:SS'),"
										+ "SIM_REG_STATUS,RESPONSE_CODE,RESPONSE_MESSAGE,AGENT_NUMBER,AGENT_NAME,MOBILE_NUMBER FROM TKASHREG_LOG "
										+ "where CLIENT_ID='" + clientId + "' order by REGISTRATIONDATE DESC")
								.list();

						System.out.println(mapper.writeValueAsString(tkashStatusLog));
						model.addAttribute("tkashStatusLog", mapper.writeValueAsString(tkashStatusLog));
					} else {
						model.addAttribute("tkashStatus", "");
						model.addAttribute("tkashStatusLog", "addNew");
					}
					
					
					//get Image Approval Log
					query = session.createSQLQuery("Select PHOTOS_APPROVAL_STATUS from clients where CLIENT_ID ='"+clientId+"'");
					if(query.uniqueResult() != null) {
						
						
						String imageApprovalStatus = query.uniqueResult().toString();
						if(!imageApprovalStatus.equalsIgnoreCase("0")) {
							
							query = session.createSQLQuery("SELECT TO_CHAR(TRANSACTION_DATE,'YYYY-MM-DD HH24:MI:SS'),"
									+ "APPROVAL_STATUS,REJECTION_REASON,RESPONSE_CODE,RESPONSE_MESSAGE,CLIENT_MSISDN,CLIENT_ID_NUMBER,USERNAME FROM PHOTOS_APPROVAL_LOG  "
									+ "where CLIENT_ID='" + clientId + "' order by TRANSACTION_DATE DESC");
							
							model.addAttribute("approvalStatus",imageApprovalStatus);
							model.addAttribute("imageLog", mapper.writeValueAsString(query.list()));
						}else {
							model.addAttribute("approvalStatus","");
							model.addAttribute("imageLog","addNew");
						}
						
					}else {
						model.addAttribute("approvalStatus","");
						model.addAttribute("imageLog","addNew");
					}
					

				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ClientsFormView due to \n "+ exceptionAsString);
				logger.info("Error in ClientsFormView due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "ClientsFormView";
	}

	@RequestMapping(value = "/ClientsFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ClientsFormSave(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		Clients Clients = new Clients();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Calendar calendar = new GregorianCalendar();
		String clientID;

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();

			try {

///////////////////////////////////////////////////////// SEND EMAIL BUTTON  //////////////////////////////////////////////////////////
				/*
				 * email=request.getParameter("email"); emailTo=request.getParameter("emailTo");
				 * password=request.getParameter("password");
				 * message=request.getParameter("message");
				 * subject=request.getParameter("subject");
				 * ccmail=request.getParameter("ccmail");
				 * 
				 * 
				 * if(StringUtils.equalsIgnoreCase(request.getParameter("email"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("password"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("emailTo"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("message"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("subject"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), "") ) {
				 * System.out.println("NO EMAIL TO SEND!");
				 * 
				 * } else if(StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), "")) {
				 * 
				 * JavaMailUtil.SendEmails(email,password,emailTo,subject,message);
				 * 
				 * } else {
				 * JavaMailUtil.SendccEmails(email,password,emailTo,ccmail,subject,message); }
				 * 
				 */
///////////////////////////////////////////// END OF SEND EMAIL BUTTON  //////////////////////////////////////////////////////////

				clientID = request.getParameter("clientID");
				System.out.println(clientID);
				if (StringUtils.equalsIgnoreCase(clientID, "")) {

					synchronized (this) {						
						//clientID = "CLT_" + calendar.get(Calendar.YEAR) + "_" + appConfig.getSequenceNbr(34);
						clientID = "CLT_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(session.createSQLQuery("SELECT CLIENTS FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET CLIENTS = CLIENTS + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}

					Clients.setClientId(clientID);
					Clients.setCreatedDate(new Timestamp(formatter.parse(request.getParameter("createdate")).getTime()));
					Clients.setLastModifiedDate(new Timestamp(new Timestamp(System.currentTimeMillis()).getTime()));
					Clients.setDisplayName(request.getParameter("DisplayName"));
					Clients.setFirstName(request.getParameter("FirstName"));
					Clients.setLastName(request.getParameter("LastName"));
					Clients.setMobile(request.getParameter("Mobile"));
					Clients.setDepartment(request.getParameter("Department"));
					Clients.setStatus(request.getParameter("status"));
					Clients.setLocation(request.getParameter("Location"));
					Clients.setDescreption(request.getParameter("clientDesc"));
					Clients.setLatitude(request.getParameter("normLat"));
					Clients.setLongitude(request.getParameter("normLng"));
					Clients.setAgentNumber(request.getParameter("agentNumber"));
					Clients.setClientIdNumber(request.getParameter("clientIdNumber"));
					session.saveOrUpdate(Clients);
				}else {
					
					query=session.createSQLQuery("Update CLIENTS SET " + 
							"DISPLAY_NAME='"+request.getParameter("DisplayName") +"'," +
							" FIRST_NAME='" + request.getParameter("FirstName")+"',"+
							" LAST_NAME='" +request.getParameter("LastName")+"',"+ 
							" MOBILE_NUMBER='" +request.getParameter("Mobile")+"',"+ 
							" DEPARTMENT='" +request.getParameter("Department")+"',"+ 
							" PHYSICAL_LOCATION='" +request.getParameter("Location")+"',"+ 
							" LAST_MODIFIED_DATE=SYSDATE,"+
							" DESCREPTION='" + request.getParameter("clientDesc")+"',"+
							" STATUS='" + request.getParameter("status")+"',"+
							" AGENT_NUMBER='" + request.getParameter("agentNumber")+"',"+
							" LONGITUDE='" + request.getParameter("normLng")+"',"+
							" LATITUDE='"+request.getParameter("normLat")+"',"+
							"CLIENT_ID_NUMBER='"+request.getParameter("clientIdNumber")+"'"
							+ " WHERE CLIENT_ID='"+clientID+"'"	);
					query.executeUpdate();
				}


				rtn.put("clientID", clientID);
				rtn.put("lstmodifdate", formatter.format(new Timestamp(System.currentTimeMillis())).toString());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ClientsFormSave due to \n "+ exceptionAsString);
				logger.info("Error in ClientsFormSave due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;

	}

	@RequestMapping(value = "/ClientsDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ClientsDelete(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}


		String idList;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				idList = request.getParameter("clientID");

				query = session.createSQLQuery("delete CLIENTS  where CLIENT_ID ='" + idList + "'");
				query.executeUpdate();
				rtn.put("result","Succeeded");
			} catch (Exception e) {

				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ClientsDelete due to \n "+ exceptionAsString);
				logger.info("Error in ClientsDelete due to \n "+ exceptionAsString);
				rtn.put("result","Failed");

			} finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}

			}
		}
		return rtn;

	}

	@RequestMapping(value = "/ClientListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ClientListViewDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String[] idList;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				idList = request.getParameterValues("clientID[]");
				for (int i = 0; i < idList.length; i++) {
					// Get AGENT_ID from the listview form
					String idForm = idList[i];

					query = session.createSQLQuery("delete CLIENTS  where CLIENT_ID ='" + idForm + "'");
					query.executeUpdate();
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ClientListViewDelete due to \n "+ exceptionAsString);
				logger.info("Error in ClientListViewDelete due to \n "+ exceptionAsString);
			} finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		} else {
			System.out.println("could not connect to DB in ClientListViewDelete method");
			logger.info("could not connect to DB in ClientListViewDelete method ");
		}
		return rtn;
	}

///////////////

	@RequestMapping(value = "/GetAllClients", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllClients(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			rtn.put("Login", "redirect:/");
			return rtn;
		}
		
		//String itemdtl = "%" + request.getParameter("client") + "%";
		System.out.println("Passing from GetAllClients where client is ");
		
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createQuery(
						"SELECT clientId, (FIRST_NAME || ' ' || LAST_NAME ),mobile from Clients where clientId like UPPER(:param1) OR UPPER(FIRST_NAME || ' ' || LAST_NAME)like UPPER(:param1) or mobile like UPPER(:param1) ORDER BY lastModifiedDate DESC");
				query.setString("param1", "%" + request.getParameter("client") + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);				
				rtn.put("ListClient", query.list());
				System.out.println("ListClient is " +mapper.writeValueAsString(query.list()));

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GetAllClients due to \n "+ exceptionAsString);
				logger.info("Error in GetAllClients due to \n "+ exceptionAsString);
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
	@RequestMapping(value = "/GetAllNetworkClients", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllNetworkClients(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		session = almsessions.getSession();
		String search = request.getParameter("search");
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createSQLQuery(
						"SELECT CLIENT_ID,FIRST_NAME,LAST_NAME,MOBILE_NUMBER,LONGITUDE,LATITUDE,PHYSICAL_LOCATION FROM CLIENTS WHERE UPPER(CLIENT_ID) LIKE UPPER(:param) OR UPPER(FIRST_NAME) LIKE UPPER(:param) OR UPPER(LAST_NAME) LIKE UPPER(:param) OR UPPER(MOBILE_NUMBER) LIKE UPPER(:param) ");
				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("globalList", query.list());
			} catch (Exception e) {				
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GetAllClients due to \n "+ exceptionAsString);
				logger.info("Error in GetAllClients due to \n "+ exceptionAsString);
				rtn.put("searchResult", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}
	
	@RequestMapping(value = "/ImageValidation2ndLevel", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> ImageValidation2ndLevel(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		String Result = null;
		HttpURLConnection httpConnection = null;
		OutputStream out = null;
		BufferedWriter writer = null;
		BufferedReader rd = null;
		HttpURLConnection urlConnection = null;
		Boolean connectionFailed = false;
		URL url = null;
		try {
			
			String msisdn = request.getParameter("clientMobilenumber");
			String status = request.getParameter("status");
			String idNumber = request.getParameter("clientIDnumber");
			String clientID = request.getParameter("clientID");
			String rejReason = request.getParameter("rejReason");
			String username = request.getParameter("userName");
			
			
			System.out.println(msisdn);
			System.out.println(status);
			System.out.println(idNumber);

			
			JsonObject postData = new JsonObject();
			postData.addProperty("msisdn", "254"+msisdn);
			postData.addProperty("status", status);
			postData.addProperty("customer_id", idNumber);
			url = new URL("http://10.22.23.27/katelecom-api/api/update.php");
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestProperty("Content-Type", "application/json");
				urlConnection.setRequestMethod("POST");
				urlConnection.setDoOutput(true);
				urlConnection.setDoInput(true);
				urlConnection.setChunkedStreamingMode(0);
				/// validate if we have access to URL
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setConnectTimeout(60000);
				int responseCode = httpConnection.getResponseCode();

				String responseMessage = httpConnection.getResponseMessage();
				if (responseCode == 200) {
						urlConnection.connect();
						connectionFailed = true;
						out = new BufferedOutputStream(urlConnection.getOutputStream());
						writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
						writer.write(postData.toString());
						writer.flush();
						//flag = 1;
						String response_code = null;
						String response_message = null;
						int code = urlConnection.getResponseCode();
						rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
						String line = null;
						while ((line = rd.readLine()) != null) {
							System.out.println(line);
							if (line.contains("message")) {
								int n = 0;
								n = line.indexOf(":");
								String message = line.substring(n + 1, line.length());
								String[] result = message.split(".,");
								response_message = result[0].replace("\"","").replace("}","");			
								response_code = result[1].replace("\"","").replace("}","").replace("Code:","");
							}
						}
						
						if (rd != null) {
								rd.close();
							
						}
						if (writer != null) {
								writer.close();
							
						}
						if (out != null) {
								writer.close();
						}
						
						if(response_message != null && !response_message.contains(" Data is incomplete")) {
							
							//update clients table and insert into the log table
							session = almsessions.getSession();
							if(session != null && session.isOpen()) {
								tx = session.beginTransaction();
								String approve_status = null;
								if(status.equalsIgnoreCase("2")) {
									approve_status= "Approved";
									query =session.createSQLQuery("UPDATE CLIENTS SET PHOTOS_APPROVAL_STATUS ='"+approve_status+"',"
											+ " LAST_MODIFIED_DATE = SYSDATE WHERE CLIENT_ID='"+clientID+"'");
									
									rtn.put("Result","Images approval done successfully.");
									//Result = "Images approval done successfully.";
								}else {
									approve_status= "Rejected";
									query =session.createSQLQuery("UPDATE CLIENTS SET STATUS ='Deactivated',PHOTOS_APPROVAL_STATUS ='"+approve_status+"',"
											+ " LAST_MODIFIED_DATE = SYSDATE WHERE CLIENT_ID='"+clientID+"'");
									
									rtn.put("Result","Images had been rejected.");
									//Result = "Images been rejected.";
								}
								
								
								query.executeUpdate();
								session.flush();
								
								query = session.createSQLQuery("INSERT INTO PHOTOS_APPROVAL_LOG (TRANSACTION_DATE,CLIENT_ID,CLIENT_MSISDN,CLIENT_ID_NUMBER,APPROVAL_STATUS,REJECTION_REASON,RESPONSE_CODE,RESPONSE_MESSAGE,USERNAME) values "
										+ " (sysdate,'"+clientID+"','"+msisdn+"','"+idNumber+"','"+approve_status+"','"+rejReason+"','"+response_code+"','"+response_message+"','"+username+"')");
								query.executeUpdate();
								//session.createSQLQuery("commit").executeUpdate();
							}
							
						}else {
							rtn.put("Result","Error Occured while communicating with the server.");
						}
						
						
					}else {
						//Result = "Error Occured while communicating with the server.";
						rtn.put("Result","Error Occured while communicating with the server.");
					}

		} catch (IOException e) {
			rtn.put("Result","Error Occured while communicating with the server.");
			//Result = "Error Occured while communicating with the server.";
			logger.finest("Error Occured in ImageValidation2ndLevel "+e);
			logger.info("Error Occured in ImageValidation2ndLevel. "+e);
		}
		finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			
			if(httpConnection != null) {
				httpConnection.disconnect();
			}
			
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}

		
		return rtn;
	}
	
}
