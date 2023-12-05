package com.aliat.alm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
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
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.Role;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserRoleController {
	Session session = null;
	private static String str;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static Transaction tx = null;
	private static StringWriter sw;
	private static String exceptionAsString;

	@Autowired
	Notify notification;
	@Autowired
	Form form;
	@RequestMapping(value = "/RoleListView", method = RequestMethod.GET)
	public String userListView(Locale locale, Model model, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) throws JsonGenerationException, JsonMappingException, IOException {
		HttpSession httpSession = httpRequest.getSession(false);
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		httpResponse.setDateHeader("Expires", 0); //
System.out.println("roleeeee");
		if (httpSession != null) {
			if (httpSession.getAttribute("userName") != null) {
				System.out.println("user login");
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}

		
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			try {
				notification.headerNotifications(session, model);
				str = "select t.role as rol,  t.role as role, t.description from Role t";

				model.addAttribute("ListGridTable", mapper.writeValueAsString(session.createNativeQuery(str).list()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();

			}
		}
		}

	return "UserRoleListView";
}

	@RequestMapping(value = "/UserRoleFormView", method = RequestMethod.GET)
	public String UserFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse httpResponse)
			throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		HttpSession httpSession = request.getSession(false);
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		httpResponse.setDateHeader("Expires", 0); //

		if (httpSession != null) {
			if (httpSession.getAttribute("userName") != null) {
				System.out.println("user login");
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}

		Map<String, Object> rtn = new LinkedHashMap<>();
		String navAction = "2";
		int SelectedIndex=0;
		String rolename = request.getParameter("rolename");
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			try {
				notification.headerNotifications(session, model);
               
		if (rolename == null) {
			System.out.println("new");
			model.addAttribute("listRoleUser", "addNew");
			model.addAttribute("SelectedIndex", "addNew");
			model.addAttribute("roleCount", "addNew");
			
			return "UserRoleFormView";
		} else {
			 navAction = request.getParameter("NavAction");
             
				String Result[] = new String[4];
				
				Result = form.NavigationNP(session, "Role", "ROLE", rolename, "ROLE", navAction);

				SelectedIndex = Integer.parseInt(Result[1]);
				rolename= Result[2];

				model.addAttribute("SelectedIndex",SelectedIndex);
				model.addAttribute("roleCount", Integer.parseInt(Result[0]));

			System.out.println("role exist");
			Transaction tx = session.beginTransaction();

			Query q = session.createQuery("from Role where role =:rolename");
			q.setParameter("rolename", rolename);
			Role role = (Role) q.uniqueResult();
			if(role != null) {
			model.addAttribute("roleName", role.getRole());
			model.addAttribute("description", role.getDescription());
			}
			model.addAttribute("listRoleUser", "");
			tx.commit();
		}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();

			}
		}
		}

	return "UserRoleFormView";
}
	
	@RequestMapping(value = "/UserRoleFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> UserRoleFormSave(Locale locale, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {
		// logger.info("Welcome home! The client locale is {}.", locale);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpSession httpSession = request.getSession(false);
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		httpResponse.setDateHeader("Expires", 0); //

		if (httpSession != null) {
			System.out.println("session not null");
			if (httpSession.getAttribute("userName") != null) {
				System.out.println("user login");
				}else {
					rtn.put("Login", "Login");
					return rtn;
				}
			}else {
				System.out.println("session null");
				rtn.put("Login", "Login");
				return rtn;				
			}
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			try {
			Transaction tx = session.beginTransaction();

		

		String oldrolename = request.getParameter("oldrolename");
		System.out.println(oldrolename);
		String rolename = request.getParameter("rolename");
		String description = request.getParameter("description");
		
		if(rolename != null) {
			if(oldrolename != null) {
			Query q = session.createSQLQuery("select * from ROLE where ROLE = '"+oldrolename+"'");
			Object[] rRow = (Object[])q.uniqueResult();
			if(rRow != null) {
				System.out.println("row not null");
				Query q2 = session.createSQLQuery("delete from ROLE where ROLE = '"+oldrolename+"'");
				q2.executeUpdate();
			}else {
				System.out.println("row null");
			}
			}
			Role role = new Role();
			role.setRole(rolename);
			role.setDescription(description);
			session.saveOrUpdate(role);
			
		}
		tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();

			}
		}
		}

	return rtn;
}
	
	@RequestMapping(value = "/UserRoleFormDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ItemFormDelete(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String[] rolenames = request.getParameterValues("rolename[]");
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			try {
		Transaction tx = session.beginTransaction();

		Query q = session.createQuery("delete Role t  where t.role IN (:param1)");
		Query q2 = session.createQuery("delete UserRole where rolename IN (:param2)");
		

		q.setParameterList("param1", rolenames);
		q.executeUpdate();

		q2.setParameterList("param2", rolenames);
		q2.executeUpdate();

		tx.commit();
		rtn.put("saveStatus", "ok");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}
			}

		return rtn;
	}
	@RequestMapping(value = "/RoleListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> RoleListViewDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String[] RoleUserList;
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				RoleUserList = request.getParameterValues("rolename[]");
				if (RoleUserList != null) {
					for (int i = 0; i < RoleUserList.length; i++) {
						String rolename = RoleUserList[i];
						query = session.createQuery("delete Role t  where t.role='" + rolename + "'");
						query.executeUpdate();
					}
					tx.commit();
				}

			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		} else {
			System.out.println("could not connect to DB in RoleListViewDelete method");
		}
		return rtn;
	}
	@RequestMapping(value = "/GetAllRole", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllUser(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();

		String Role = "%" + request.getParameter("Role") + "%";
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session
						.createQuery("SELECT t1.role, t1.description  FROM Role t1"
								+ " WHERE LOWER(t1.role) LIKE LOWER(:Role)");
				query.setParameter("Role", Role);
				query.setFirstResult(0);
				query.setMaxResults(40);

				List<Object[]> results = query.list();

				rtn.put("ListUser", results);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		} else {
			System.out.println("could not connect to DB in getUsers method");
		}
		return rtn;
	}

}