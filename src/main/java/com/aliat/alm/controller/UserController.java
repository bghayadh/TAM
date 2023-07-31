package com.aliat.alm.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aliat.alm.securepassword.EncryptDecryptPassword;
import com.aliat.alm.services.LoginServices;
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
import com.aliat.alm.models.User;
import com.aliat.alm.models.UserRole;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {
	String globalUserName;
	private static final String ENCRIPT_KEY= "alm";
	
	
	Session session=null;
	Transaction tx = null;
	
	@Autowired
	ALMSessions almsessions;
	@RequestMapping(value = "/UserListView", method = RequestMethod.GET)
	public String userListView(Locale locale, Model model, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest)
			throws JsonGenerationException, JsonMappingException, IOException {
		HttpSession httpSession = httpRequest.getSession(false);
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		httpResponse.setDateHeader("Expires", 0); //

		if (httpSession != null) {
			if (httpSession.getAttribute("userName") != null) {
				System.out.println("user login");
				}else {
					return "redirect:/";
				}
			}else {
				return "redirect:/";
			}

		// logger.info("Welcome home! The client locale is {}.", locale);

		List<User> listUser = new ArrayList<User>();

		Session session =almsessions.getSession();
		Transaction tx = session.beginTransaction();

		listUser = session.createQuery("select 1, t.userName, t.emailAddress from User t").list();
		// System.out.println("length of listuser is " +listUser.size());
		tx.commit();
		session.close();
		ObjectMapper mapper = new ObjectMapper();
		model.addAttribute("ListGridTable", mapper.writeValueAsString(listUser));
//		model.addAttribute("ListGridTable", listUser);
		return "UserListView";
	}

	@RequestMapping(value = "/UserFormView", method = RequestMethod.GET)
	public String UserFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);
		
		HttpSession httpSession = request.getSession(false);
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		httpResponse.setDateHeader("Expires", 0); //

		if (httpSession != null) {
			if (httpSession.getAttribute("userName") != null) {
				System.out.println("user login");
				}else {
					return "redirect:/";
				}
			}else {
				return "redirect:/";
			}
		
		
		List<Object> params = new ArrayList<Object>();
		// System.out.println("Welcome to User Form View");
		Map<String, Object> rtn = new LinkedHashMap<>();

		Date date;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		String username = request.getParameter("username");
		globalUserName = username;
		System.out.println("The username is " + username);
		String type = request.getParameter("type");

		if (username == null) {
			date = new Timestamp(System.currentTimeMillis());
			model.addAttribute("createdDate", formatter.format(date).toString());
			model.addAttribute("lastModifiedDate", formatter.format(date).toString());
			model.addAttribute("listUser", "addNew");
			return "UserFormView";
		}
		session = almsessions.getSession();
		
		User user = (User) session.get(User.class, username);
		if (user != null) {
			model.addAttribute("username", user.getUserName());
			// System.out.println("the username is " +user.getUserName());
			model.addAttribute("Fname", user.getFname());
			model.addAttribute("Mname", user.getMname());
			model.addAttribute("Lname", user.getLname());
			model.addAttribute("Eadd", user.getEmailAddress());
			model.addAttribute("mobNum", user.getMobileNb());
			model.addAttribute("Address", user.getAddress());
			model.addAttribute("circleCountry", user.getUserCirclecountry());
			model.addAttribute("CircleCity", user.getUserCirclecity());
			model.addAttribute("c_state", user.getUserCirclestate());
			model.addAttribute("circleId", user.getUserCircle());
			model.addAttribute("jobTitle", user.getUserJobTitle());

			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			tx.commit();
			session.close();

			final String secretKey = "alm";
			String decryptedPassword = EncryptDecryptPassword.decrypt(user.getUserpass(), secretKey);
			model.addAttribute("pass", decryptedPassword);

			if (user.getCreatedDate() == null) {
				date = new Timestamp(System.currentTimeMillis());
			} else {
				date = user.getCreatedDate();
			}

			// System.out.print("The create date is " +date);
			model.addAttribute("createdDate", formatter.format(date).toString());

			if (user.getModified_date() == null) {
				date = new Timestamp(System.currentTimeMillis());
			} else {
				date = user.getModified_date();
			}
			model.addAttribute("lastModifiedDate", formatter.format(date).toString());

			// Role

			// Pass
			if (user.getUpdatePass() == '1') {
				model.addAttribute("UpdatePass", "checked");
			} else {
				model.addAttribute("UpdatePass", "");
			}

			if (user.getLogout() == '1') {
				model.addAttribute("Logout", "checked");
			} else {
				model.addAttribute("Logout", "");
			}

		}

		return "UserFormView";

	}

	@RequestMapping(value = "/UserFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> UserFormSave(Locale locale, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {
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

		
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();

		User user = new User();


		String password = request.getParameter("pass");
		System.out.println("the pass is " + password);
		String encryptedPassword = EncryptDecryptPassword.encrypt(password, ENCRIPT_KEY);
		System.out.println("the encrypted pass " + encryptedPassword);

		DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		Date date1 = null;
		Date date2 = null;
		String un = null;
		String createdDate = request.getParameter("createdDate");
		try {
		String roleName = request.getParameter("roleChecked");
		System.out.println("role "+roleName);
		ObjectMapper mapper = new ObjectMapper();
		String[] array = mapper.readValue(roleName, String[].class);
		if(array != null && array.length != 0) {
			un = request.getParameter("username");
			Query getRole = session.createSQLQuery("select ROLE_NAME from USER_ROLE where USER_NAME ='"+un+"'");

			List<String> getroleList = getRole.list();
			if(getroleList != null) {
				System.out.println("delete user");
				Query deleteRole = session.createSQLQuery("DELETE FROM USER_ROLE WHERE USER_NAME='"+un+"'");
				deleteRole.executeUpdate();
			}
			UserRole userRole = new UserRole();
		System.out.println("the array is "+array[0]);	    
		for (String role : array) {			//insert into user_role (USER_ROLE_ID,ROLE_NAME,USER_NAME) values (USER_ROLE_ID.nextval,'hi','bye')
			Query insertRole = session.createSQLQuery("insert into USER_ROLE (USER_ROLE_ID,ROLE_NAME,USER_NAME) values (USER_ROLE_ID.nextval,'"+role.trim()+"','"+un+"')");
//			userRole.setRolename(role.trim());
//			userRole.setUsername(un);
//			session.saveOrUpdate(userRole);
			insertRole.executeUpdate();
		}
		}else {
			System.out.println("no role user");
			Query getRole = session.createSQLQuery("select ROLE_NAME from USER_ROLE where USER_NAME ='"+un+"'");
			List<String> getroleList = getRole.list();
			if(getroleList != null) {
				System.out.println("delete user");
				Query deleteRole = session.createSQLQuery("DELETE FROM USER_ROLE WHERE USER_NAME='"+un+"'");
				deleteRole.executeUpdate();
			}
		}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}

		date1 = formatter1.parse(createdDate);
		Timestamp CreationDate = new Timestamp(date1.getTime());


		date2 = new Timestamp(System.currentTimeMillis());
		Timestamp lastModifiedDate = new Timestamp(date2.getTime());

		user.setCreatedDate(CreationDate);
		user.setModified_date(lastModifiedDate);
		user.setUserName(request.getParameter("username"));
		user.setFname(request.getParameter("fname"));
		user.setMname(request.getParameter("mname"));
		user.setLname(request.getParameter("lname"));
		user.setAddress(request.getParameter("address"));
		user.setEmailAddress(request.getParameter("emailAddress"));
		user.setMobileNb(request.getParameter("mobileNumber"));
		user.setUserCirclecity(request.getParameter("circle_city"));
		user.setUserCirclecountry(request.getParameter("circle_country"));
		user.setUserCirclestate(request.getParameter("circle_state"));
		user.setAddress(request.getParameter("address"));
		user.setUserpass(encryptedPassword);
		user.setUpdatePass(request.getParameter("update").charAt(0));
		user.setLogout(request.getParameter("log").charAt(0));
		user.setUserCircle(request.getParameter("circleId"));
		user.setUserJobTitle(request.getParameter("jobTitle"));

		session.saveOrUpdate(user);

		tx.commit();
		session.close();

		rtn.put("lastModifiedDate", formatter1.format(date2).toString());
		return rtn;
	}

	@RequestMapping(value = "/UserFormDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ItemFormDelete(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String d = request.getParameter("userName");
		System.out.println("The username is " + d);
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();

		Query q = session.createQuery("delete User t  where t.userName = :param1 ");

		q.setString("param1", d);
		q.executeUpdate();

		tx.commit();
		session.close();
		rtn.put("Safatest", "DeleteDone");

		return rtn;

	}

	@RequestMapping(value = "/getUserRole", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserRole(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> map = new HashMap<String, Object>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
		}
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();

		Query q = session.createSQLQuery("select ROLE from ROLE");

		List<String> list = q.list();
		
		List<String> getroleList = null;
		if (globalUserName != null) {
			System.out.println("have user ");
			Query getRole = session.createSQLQuery("select ROLE_NAME from USER_ROLE where USER_NAME ='"+globalUserName+"'");
			getroleList = getRole.list();
		}

		tx.commit();
		session.close();
		map.put("roleList", list);
		map.put("getroleList", getroleList);

		return map;

	}
	
	
	
	
			
			///////////////
			@RequestMapping(value = "/GetUserJobTitle", method = RequestMethod.GET)
			@ResponseBody
			public Map<String, Object> GetAllRegionss(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
			System.out.println("GetAShops");
			Map<String, Object> rtn = new LinkedHashMap<>();
			if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			System.out.println("GetAShops");
			
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
			}
			
			List<User> listSalesManager = new ArrayList<User>();
			List<User> listRegionManager = new ArrayList<User>();
			Query query;
			String users = "%" + request.getParameter("users") + "%";

			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			String SalesManager = "Sales Manager";
			listSalesManager = session.createQuery("SELECT  t.userName, t.fname, t.mname, t.lname from User t where userJobTitle ='"+SalesManager+"'").list();
			
			String RegionManager = "Region Manager";
			listRegionManager = session.createQuery("SELECT  t.userName, t.fname, t.mname, t.lname from User t where userJobTitle ='"+RegionManager+"'").list();

			query = session.createQuery("SELECT  userName, fname, mname, lname from User where  UPPER(userName) like UPPER(:param1) OR UPPER(fname)like UPPER(:param1) OR UPPER(mname)like UPPER(:param1) OR UPPER(lname)like UPPER(:param1)");
			query.setString("param1", users);
			query.setFirstResult(0);
			query.setMaxResults(40);
			rtn.put("UsersList", query.list() );

			
			//model.addAttribute("ListItemprreq", mapper.writeValueAsString(listItem));
			
			
			tx.commit();
			session.close();
			ObjectMapper mapper = new ObjectMapper();
			
			//model.addAttribute("ListItemprreq", mapper.writeValueAsString(listItem));
			
			System.out.println("/*/*listItSalesManager autocomplete " + mapper.writeValueAsString(listSalesManager));
			System.out.println("/*/*listRegionManager autocomplete " + mapper.writeValueAsString(listRegionManager));
			rtn.put("listSalesManager", listSalesManager);
			
			rtn.put("listRegionManager", listRegionManager);
			
			
			
			return rtn;
			}

}
