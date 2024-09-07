package com.aliat.alm.controller;

import java.io.IOException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.models.ModuleField;
import com.aliat.alm.models.ModuleScreen;
import com.aliat.alm.models.PurchaseOrder;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.models.RolePermission;
import com.aliat.alm.models.RolePermissionException;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RolePermissionExcepController {
	private Session session = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	@Autowired
	ALMSessions almsessions;
	@Autowired
	Notify notification;
	@Autowired
	Notify notifications;
	private static final Logger logger = LoggerFactory.getLogger(RolePermissionExcepController.class);

	@RequestMapping(value = "/rolePermissionExcep", method = RequestMethod.GET)
	public String RolePermission(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) 
		throws JsonGenerationException, JsonMappingException, IOException {
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		Transaction tx =null;
			List<RolePermissionException> listRolePerm = new ArrayList<RolePermissionException>();
			List <String> listRoles=new ArrayList<>();;
			session = AlmDbSession.getInstance().getSession();
			List<ModuleScreen> ModuleScreenList = new ArrayList<ModuleScreen>();
			  ObjectMapper objectMapper = new ObjectMapper();
			if (session != null && session.isOpen()) {
				try {
				tx = session.beginTransaction();
				
				notification.headerNotifications(session, model);
				List<Object[]> Screens = session.createQuery(
					    "select t.id, t.screenName, t.screenTable from ModuleScreen t order by t.screenName")
					    .list();

					model.addAttribute("moduleScreenList", Screens);

                
				listRolePerm = session.createQuery("select  t.screenName, t.action, t.role, t.fieldName, t.fieldValue, t.id"
							+ " from RolePermissionException t ORDER BY t.lastModificationDate DESC").list();
					listRoles= session.createNativeQuery("select Role From  Role").list();
					for (String role : listRoles) {
					    System.out.println(role);
					}
							System.out.println("Length of listRolePerm is " +listRolePerm.size());
					
				}catch(Exception e){
					System.out.println("Error in rolePermission"+e.toString());
				}
				finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}

			}
			ObjectMapper mapper = new ObjectMapper();
			model.addAttribute("ListGridTable", mapper.writeValueAsString(listRolePerm));
			model.addAttribute("ListRole", mapper.writeValueAsString(listRoles));
					
		return "RolePermissionExcep";
		}

	@RequestMapping(value = "/rolePermissionExcepSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> rolePermissionExcepSave(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
	        throws Exception {
	    Map<String, Object> rtn = new LinkedHashMap<>();

	    // Check for user session
	    if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	        rtn.put("Login", LoginServices.checkSession(request, response));
	        return rtn;
	    }

	    // Get the session and start transaction
	    Session session = almsessions.getSession();
	    Transaction tx = session.beginTransaction();

	    // Collect data from the request
	    String permExcepID = request.getParameter("id");
	    String screenName = request.getParameter("screenName");
	    String role = request.getParameter("role");
	    String fieldName = request.getParameter("fieldName");
	    String fieldValue = request.getParameter("fieldValue");
	    String exceptionType = request.getParameter("exceptionType");
System.out.println(exceptionType);
	    // Check if a row with the same screenName, fieldName, and fieldValue exists
	    String checkQuery = "SELECT COUNT(*) FROM RolePermissionException WHERE screenName = :screenName AND fieldName = :fieldName AND ACTION =: ACTION  AND fieldValue = :fieldValue AND role= :role";
	    Long count = (Long) session.createQuery(checkQuery)
	            .setParameter("screenName", screenName)
	            .setParameter("fieldName", fieldName)
	            .setParameter("fieldValue", fieldValue)
	            .setParameter("ACTION", exceptionType)
	            .setParameter("role", role)
	            .uniqueResult();

	    // If such a row exists, return an error message
	    if (count > 0) {
	        rtn.put("error", "A record with the same screen name, field name, and field value already exists.");
	        tx.rollback();
	        session.close();
	        return rtn;
	    }
	    Timestamp lastModifiedDate = new Timestamp(System.currentTimeMillis());


	    // Generate new ID if not provided
	    if (permExcepID == null || permExcepID.isEmpty()) {
	        synchronized (this) {
	            permExcepID = "PERM_" + Calendar.getInstance().get(Calendar.YEAR) + "_" +
	                    Integer.parseInt(session.createSQLQuery("SELECT PERMISSION_EXCEPTION FROM SEQ_TABLE").uniqueResult().toString());
	            session.createSQLQuery("UPDATE SEQ_TABLE SET PERMISSION_EXCEPTION = PERMISSION_EXCEPTION + 1").executeUpdate();
	               
	            session.createSQLQuery("commit").executeUpdate();
	        }
	        model.addAttribute("permID", permExcepID);
	    }
	    else {
	    	
	    	
	    	
	    	
	    }

	    // Create or update the RolePermissionException entity
	    RolePermissionException rpData = new RolePermissionException();
	    rpData.setId(permExcepID);
	    rpData.setScreenName(screenName);
	    rpData.setAction(exceptionType);
	    rpData.setRole(role);
	    rpData.setLastModificationDate(lastModifiedDate);
	    rpData.setFieldName(fieldName);
	    rpData.setFieldValue(fieldValue);
	    session.saveOrUpdate(rpData);

	    // Commit transaction and close session
	    tx.commit();
	    session.close();

	    rtn.put("BassamTest", "SaveDone");
	    return rtn;
	}

	
	
	@RequestMapping(value = "/rolePermissionExcepDel", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> rolePermissionExcepDel(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		Session session =almsessions.getSession();
		Transaction tx = session.beginTransaction();

		RolePermissionException rpData = new RolePermissionException();
		
		String permExcepID = request.getParameter("id");
	
		
		 query = session.createNativeQuery("delete ROLE_PERMISSION_EXCEPTION  where ID =:param1").setParameter("param1", permExcepID);
		
		 query.executeUpdate();
			tx.commit();
		
		
		
		
		
	
		session.close();

		rtn.put("BassamTest", "Delete Done");
		// rtn.put("permID", request.getParameter("permID"));
		return rtn;
	}
	
	
	@RequestMapping(value = "/getScreenFields", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTableFields(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	    Map<String, Object> rtn = new LinkedHashMap<>();
	    
	    // Check session status
	    String loginStatus = LoginServices.checkSession(request, response);
	    if (loginStatus.equals("redirect:/")) {
	        rtn.put("Login", loginStatus);
	        return rtn;
	    }

	    // Retrieve the selected value (table name)
	    String ScreenName = request.getParameter("selectedValue");
	    String TableName;
	    if (ScreenName == null || ScreenName.trim().isEmpty()) {
	        rtn.put("error", "Table name cannot be empty.");
	        return rtn;
	    }
	    
	    // Initialize session and transaction
	    Session session = null;
	    Transaction tx = null;
	    try {
	        // Get the session
	        session = AlmDbSession.getInstance().getSession();
	        
	        if (session == null || !session.isOpen()) {
	            rtn.put("error", "Database session is not available.");
	            return rtn;
	        }

	        tx = session.beginTransaction();
	        TableName = (String) session.createSQLQuery(
                    "SELECT SCREEN_TABLE FROM MODULE_SCREEN WHERE SCREEN_NAME = :param1")
                    .setParameter("param1", ScreenName)
                    .uniqueResult();
          
	        // Sanitize and process the table name
	        TableName = TableName.toUpperCase();

	        // Query for table columns
	        List<String> fieldNames = session.createSQLQuery(
	                "SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = :param1 ORDER BY COLUMN_NAME")
	                .setParameter("param1", TableName)
	                .list();

	        // Add field names to response
	        rtn.put("fieldNames", fieldNames);
	        
	        // Commit transaction
	        tx.commit();
	        
	    } catch (Exception e) {
	        // Log the exception and rollback if necessary
	        logger.error("Error retrieving table fields with a message: " + e.getMessage(), e);
	        if (tx != null) {
	            tx.rollback();
	        }
	        rtn.put("error", "An error occurred while retrieving table fields.");
	    } finally {
	        // Ensure the session is closed
	        if (session != null && session.isOpen()) {
	            session.close();
	        }
	    }

	    return rtn;
	}

		
	
	
	/*
	@RequestMapping(value = "/rolePermissionApply", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> rolePermissionApply(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){
			
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
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

		//char FirstLvl = request.getParameter("firstlvlPerm").charAt(0);
		
		//String[] checkedList = request.getParameterValues("checked[]");
		//String permID = checkedList[0];
		
		//System.out.println("Checked array is: "+checkedList);
		
		
		System.out.println("Add is: "+Add);
		
		Query q = session.createQuery("update RolePermission set readPerm = :param1, writePerm =:param2, "
		+ "addPerm = :param3, delPerm =:param4, savePerm = :param5, statusPerm =:param6, "
		+ "actionPerm = :param7, downloadPerm =:param8, viewType =:param9, exportPerm =:param11, secondLevelPerm =:param12, firstLevelPerm = :param13"
		+ " where permID =:param10");
		
		q.setCharacter("param1", Read); q.setCharacter("param2", Write);
		q.setCharacter("param3", Add); q.setCharacter("param4", Delete);
		q.setCharacter("param5", Save); q.setCharacter("param6", Status);
		q.setCharacter("param7", Action); q.setCharacter("param8", Download);
		q.setString("param9", viewType); q.setString("param10", permID);
		q.setCharacter("param11", Export); q.setCharacter("param12", SecondLvl);
		q.setCharacter("param13", request.getParameter("firstlvlPerm").charAt(0));
		

		

		q.executeUpdate();

		tx.commit();
		session.close();

		rtn.put("BassamTest", "UpdateDone");
		// rtn.put("permID", request.getParameter("permID"));
		return rtn;
	}
	
	
	@RequestMapping(value = "/rolePermissionDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> rolePermissionDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();

		String permID = request.getParameter("permID");
		//System.out.println("idList array is: "+idList);
		Query q = session.createQuery("delete RolePermission where permID = :param1");
		
		q.setString("param1", permID);

		q.executeUpdate();

		tx.commit();
		session.close();

		
		rtn.put("BassamTest", "DeleteDone");
		return rtn;

	}
	*/

}