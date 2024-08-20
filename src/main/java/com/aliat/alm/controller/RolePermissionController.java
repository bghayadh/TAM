package com.aliat.alm.controller;

import java.io.IOException;

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

@Controller
public class RolePermissionController {
	private Session session = null;

	@Autowired
	ALMSessions almsessions;
	@Autowired
	Notify notification;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rolePermission", method = RequestMethod.GET)
	public String RolePermission(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		Transaction tx = null;
		List<PurchaseRequest> listRolePerm = new ArrayList<PurchaseRequest>();
		//List<List<String>> listRolePerm = new ArrayList<List<String>>();
		List<String> listRoles = new ArrayList<>();
		;
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			try {
				tx = session.beginTransaction();

				notification.headerNotifications(session, model);

				listRolePerm = session
						.createQuery("select t.permID, t.screen, t.viewType, t.role, t.roleLevel, t.readPerm,"
								+ " t.writePerm, t.addPerm, t.delPerm, t.savePerm, t.statusPerm, t.actionPerm, t.downloadPerm, t.exportPerm, t.secondLevelPerm,t.firstLevelPerm"
								+ " from RolePermission t")
						.list();
				listRoles = session.createNativeQuery("select Role From  Role").list();
				for (String role : listRoles) {
					System.out.println(role);
				}
				System.out.println("Length of listRolePerm is " + listRolePerm.size());

			} catch (Exception e) {
				System.out.println("Error in rolePermission" + e.toString());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}

		}
				
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("listRolePerm is " +mapper.writeValueAsString(listRolePerm));
		model.addAttribute("ListGridTable", mapper.writeValueAsString(listRolePerm));
		model.addAttribute("ListRole", mapper.writeValueAsString(listRoles));

		return "RolePermission";
	}

	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rolePermissionSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> rolePermissionSave(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();

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
		// permID = "PERM_" + year + "_" + appConfig.getSequenceNbr(17);
		System.out.println("DNI*********" + permID);

		model.addAttribute("permID", permID);

		rpData.setPermID(permID);
		rpData.setScreen(request.getParameter("screen"));
		rpData.setViewType(request.getParameter("viewType"));
		rpData.setRole(request.getParameter("role"));
		rpData.setRoleLevel(request.getParameter("roleLevel").charAt(0));
		rpData.setReadPerm(request.getParameter("readPerm").charAt(0));
		rpData.setWritePerm(request.getParameter("writePerm").charAt(0));
		rpData.setAddPerm(request.getParameter("addPerm").charAt(0));
		rpData.setDelPerm(request.getParameter("delPerm").charAt(0));
		rpData.setSavePerm(request.getParameter("savePerm").charAt(0));
		rpData.setStatusPerm(request.getParameter("statusPerm").charAt(0));
		rpData.setActionPerm(request.getParameter("actionPerm").charAt(0));
		rpData.setDownloadPerm(request.getParameter("downloadPerm").charAt(0));
		System.out.println("data is: " + request.getParameter("permID") + request.getParameter("screen")
				+ request.getParameter("role"));
		rpData.setExportPerm(request.getParameter("exportPerm").charAt(0));
		rpData.setSecondLevelPerm(request.getParameter("secondlvlPerm").charAt(0));
		rpData.setFirstLevelPerm(request.getParameter("firstlvlPerm").charAt(0));
		session.saveOrUpdate(rpData);

		tx.commit();
		session.close();

		rtn.put("BassamTest", "SaveDone");
		// rtn.put("permID", request.getParameter("permID"));
		return rtn;
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@RequestMapping(value = "/rolePermissionApply", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> rolePermissionApply(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();

		String permID = request.getParameter("permID");
		String viewType = request.getParameter("viewType");
		char Read = request.getParameter("readPerm").charAt(0);
		char Write = request.getParameter("writePerm").charAt(0);
		char Add = (request.getParameter("addPerm").charAt(0));
		char Delete = (request.getParameter("delPerm").charAt(0));
		char Save = (request.getParameter("savePerm").charAt(0));
		char Status = (request.getParameter("statusPerm").charAt(0));
		char Action = (request.getParameter("actionPerm").charAt(0));
		char Download = (request.getParameter("downloadPerm").charAt(0));
		char Export = request.getParameter("exportPerm").charAt(0);
		char SecondLvl = request.getParameter("secondlvlPerm").charAt(0);

		// char FirstLvl = request.getParameter("firstlvlPerm").charAt(0);

		// String[] checkedList = request.getParameterValues("checked[]");
		// String permID = checkedList[0];

		// System.out.println("Checked array is: "+checkedList);

		System.out.println("Add is: " + Add);
		System.out.println("permID is: " + permID);
		System.out.println("Read is " + Read + " Write is " + Write + " Delete is " + Delete + " Save is " + Save);

		Query q = session.createQuery("update RolePermission set readPerm = :param1, writePerm =:param2, "
				+ "addPerm = :param3, delPerm =:param4, savePerm = :param5, statusPerm =:param6, "
				+ "actionPerm = :param7, downloadPerm =:param8, viewType =:param9, exportPerm =:param11, secondLevelPerm =:param12, firstLevelPerm = :param13"
				+ " where permID =:param10");

		q.setCharacter("param1", Read);
		q.setCharacter("param2", Write);
		q.setCharacter("param3", Add);
		q.setCharacter("param4", Delete);
		q.setCharacter("param5", Save);
		q.setCharacter("param6", Status);
		q.setCharacter("param7", Action);
		q.setCharacter("param8", Download);
		q.setString("param9", viewType);
		q.setString("param10", permID);
		q.setCharacter("param11", Export);
		q.setCharacter("param12", SecondLvl);
		q.setCharacter("param13", request.getParameter("firstlvlPerm").charAt(0));

		q.executeUpdate();

		tx.commit();
		session.close();

		rtn.put("BassamTest", "UpdateDone");
		// rtn.put("permID", request.getParameter("permID"));
		return rtn;
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@RequestMapping(value = "/rolePermissionDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> rolePermissionDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();

		String permID = request.getParameter("permID");
		// System.out.println("idList array is: "+idList);
		Query q = session.createQuery("delete RolePermission where permID = :param1");

		q.setString("param1", permID);

		q.executeUpdate();

		tx.commit();
		session.close();

		rtn.put("BassamTest", "DeleteDone");
		return rtn;

	}

}