package com.aliat.alm.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;

import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.models.RolePermission;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Logger;
import java.io.PrintWriter;
import java.io.StringWriter;

@Controller
public class RolePermissionController {
	private static final Logger logger = Logger.getLogger(RolePermissionController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;

	@Autowired
	ALMSessions almsessions;
	@Autowired
	Notify notification;
	
	private static StringWriter sw;
	private static String exceptionAsString;
	@SuppressWarnings("rawtypes")
	Query query;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rolePermission", method = RequestMethod.GET)
	public String RolePermission(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		Transaction tx = null;
		List<PurchaseRequest> listRolePerm = new ArrayList<PurchaseRequest>();
		List<String> listRoles = new ArrayList<>();
		;
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			try {
				tx = session.beginTransaction();

				notification.headerNotifications(session, model);

				listRolePerm = session.createNativeQuery("select SCREEN as screen, VIEW_TYPE as viewType, ROLE as role, ROLE_LEVEL as roleLevel, READ_PERM as readPerm,"
								+ " WRITE_PERM as writePerm, ADD_PERM as addPerm, DELETE_PERM as delPerm, SAVE_PERM as savePerm, STATUS_PERM as statusPerm, ACTION_PERM as actionPerm, DOWNLOAD_PERM as downloadPerm, EXPORT_PERM as exportPerm,  SECOND_LEVEL_PERM as secondLevelPerm, FIRST_LEVEL_PERM as firstLevelPerm, PERM_ID as permID,SEARCH_POPUP_PERM as searchPopupPerm,FIND_CONNECTED_PERM as findConnectedPerm,PROJECTS_PERM as projectsPerm "
								+ " from ROLE_PERMISSIONS t ORDER BY LAST_MODIFICATION_DATE DESC,SCREEN ASC").list();
				listRoles = session.createNativeQuery("select Role From  Role").list();
				for (String role : listRoles) {
					System.out.println(role);
				}
				System.out.println("Length of listRolePerm is " + listRolePerm.size());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in RolePermission due to \n " + exceptionAsString);
				logger.info("Error in RolePermission due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}

		}
		ObjectMapper mapper = new ObjectMapper();
		model.addAttribute("ListGridTable", mapper.writeValueAsString(listRolePerm));
		model.addAttribute("ListRole", mapper.writeValueAsString(listRoles));

		return "RolePermission";
	}

	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rolePermissionSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> rolePermissionSave(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		else {
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					RolePermission rpData = new RolePermission();
					String permID;
					Date date = new Date();
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(date);
					int year = calendar.get(Calendar.YEAR);
					synchronized (this) {
						permID = "PERM_" + year + "_" + Integer.parseInt(
								session.createNativeQuery("SELECT ROLE_PERMISSION FROM SEQ_TABLE").uniqueResult().toString());
						Query query = session.createNativeQuery("UPDATE SEQ_TABLE SET ROLE_PERMISSION = ROLE_PERMISSION + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
					}
					String readPerm="0", writePerm="0";
					if ("1".equals(request.getParameter("readPerm")) & "0".equals(request.getParameter("writePerm"))){
						readPerm="1";
						writePerm="0";
						
					}
					else if ("0".equals(request.getParameter("readPerm")) & "1".equals(request.getParameter("writePerm"))){
						readPerm="1";
						writePerm="1";
						
					}
				
					else if ("1".equals(request.getParameter("readPerm")) & "1".equals(request.getParameter("writePerm"))){
						readPerm="1";
						writePerm="1";
						
					}
					model.addAttribute("permID", permID);

					rpData.setPermID(permID);
					rpData.setScreen(request.getParameter("screen"));
					rpData.setViewType(request.getParameter("viewType"));
					rpData.setRole(request.getParameter("role"));
					rpData.setRoleLevel(request.getParameter("roleLevel").charAt(0));
					rpData.setReadPerm(readPerm.charAt(0));
					rpData.setWritePerm(writePerm.charAt(0));
					rpData.setAddPerm(request.getParameter("addPerm").charAt(0));
					rpData.setDelPerm(request.getParameter("delPerm").charAt(0));
					rpData.setSavePerm(request.getParameter("savePerm").charAt(0));
					rpData.setStatusPerm(request.getParameter("statusPerm").charAt(0));
					rpData.setActionPerm(request.getParameter("actionPerm").charAt(0));
					rpData.setDownloadPerm(request.getParameter("downloadPerm").charAt(0));
					rpData.setExportPerm(request.getParameter("exportPerm").charAt(0));
					rpData.setSecondLevelPerm(request.getParameter("secondlvlPerm").charAt(0));
					rpData.setFirstLevelPerm(request.getParameter("firstlvlPerm").charAt(0));
					rpData.setSearchPopupPerm(request.getParameter("searchPopupPerm").charAt(0));
					rpData.setFindConnectedPerm(request.getParameter("findConnectedPerm").charAt(0));
					rpData.setProjectsPerm(request.getParameter("projectsPerm").charAt(0));
					rpData.setCreationDate(new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));
					rpData.setLastModificationDate(new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));

					session.saveOrUpdate(rpData);
					session.flush();
					session.clear();
					tx.commit();
					
					rtn.put("BassamTest", "SaveDone");

					
				} catch (Exception e) {
					tx.rollback();
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in rolePermissionSave due to \n " + exceptionAsString);
					logger.info("Error in rolePermissionSave due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();

					}
				}
			}
			
			return rtn;
		}
	}
		
	@RequestMapping(value = "/rolePermissionApply", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> rolePermissionApply(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		else {
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					
					String readPerm="0", writePerm="0";
				
					if ("1".equals(request.getParameter("readPerm")) & "0".equals(request.getParameter("writePerm"))){
						readPerm="1";
						writePerm="0";
						
						
					}
					else if ("0".equals(request.getParameter("readPerm")) & "1".equals(request.getParameter("writePerm"))){
						readPerm="1";
						writePerm="1";
						
					}
					
					else if ("1".equals(request.getParameter("readPerm")) & "1".equals(request.getParameter("writePerm"))){
						readPerm="1";
						writePerm="1";
						
					}
					String permID = request.getParameter("permID");
					String viewType = request.getParameter("viewType");
					char Read = readPerm.charAt(0);
					char Write =writePerm.charAt(0);
					char Add = (request.getParameter("addPerm").charAt(0));
					char Delete = (request.getParameter("delPerm").charAt(0));
					char Save = (request.getParameter("savePerm").charAt(0));
					char Status = (request.getParameter("statusPerm").charAt(0));
					char Action = (request.getParameter("actionPerm").charAt(0));
					char Download = (request.getParameter("downloadPerm").charAt(0));
					char Export = request.getParameter("exportPerm").charAt(0);
					char SecondLvl = request.getParameter("secondlvlPerm").charAt(0);
					char searchPopup = (request.getParameter("searchPopup").charAt(0));
					char findConnected = request.getParameter("findConnected").charAt(0);
					char projects = request.getParameter("projects").charAt(0);
					Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
					String roleLevel = request.getParameter("roleLevel");

					query = session.createNativeQuery(
							"UPDATE ROLE_PERMISSIONS SET READ_PERM= '" +Read+ "',WRITE_PERM= '" +Write+ "',ADD_PERM= '" +Add+ "',DELETE_PERM= '" +Delete+ "',SAVE_PERM= '" +Save+ "',STATUS_PERM= '" +Status
									+ "',ACTION_PERM= '" +Action+ "',DOWNLOAD_PERM= '" +Download+ "',VIEW_TYPE= '" +viewType+ "',EXPORT_PERM= '" +Export+ "',SECOND_LEVEL_PERM= '" +SecondLvl+ "',FIRST_LEVEL_PERM= '" +request.getParameter("firstlvlPerm").charAt(0)
									+ "',SEARCH_POPUP_PERM= '" +searchPopup+ "',FIND_CONNECTED_PERM= '" +findConnected+ "',PROJECTS_PERM= '" +projects+ "',LAST_MODIFICATION_DATE= TIMESTAMP '" +lastModifiedDate+"',ROLE_LEVEL= '" +roleLevel+ "' WHERE PERM_ID = '"+permID+"' ");

					query.executeUpdate();
					session.flush();
					session.clear();					
					tx.commit();
					
					rtn.put("BassamTest", "UpdateDone");

					
				} catch (Exception e) {
					tx.rollback();
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in rolePermissionSave due to \n " + exceptionAsString);
					logger.info("Error in rolePermissionSave due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();

					}
				}
			}
			
			return rtn;
		}
		
	}
	
	@RequestMapping(value = "/rolePermissionDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> rolePermissionDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		else {
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String permID = request.getParameter("permID");
					query = session.createNativeQuery("delete ROLE_PERMISSIONS where PERM_ID = '"+permID+"' ");

					query.executeUpdate();
					session.flush();
					session.clear();					
					tx.commit();
					
					
					rtn.put("BassamTest", "DeleteDone");

					
				} catch (Exception e) {
					tx.rollback();
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in rolePermissionDelete due to \n " + exceptionAsString);
					logger.info("Error in rolePermissionDelete due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();

					}
				}
			}
			
			return rtn;
		}
	}
}