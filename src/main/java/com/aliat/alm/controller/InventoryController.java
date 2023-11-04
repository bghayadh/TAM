package com.aliat.alm.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.services.LoginServices;

@Controller
public class InventoryController {
	private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

	
	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;

	@Autowired
	Permissions permissions;
	
	@RequestMapping(value = "/Inventory", method = RequestMethod.GET)
	public String Inventory(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		Session session = null;
		Transaction tx = null;

//		logger.info("Welcome Inventory! The client locale is {}.", locale);
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		else {
			
 			session = almsessions.getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
	 			try {
					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request), "Purchase Request", "List");
				}
	 			catch (Exception e) { 				
	 				logger.info("Error at Purchase Class and Purchase method while getting notifications and permissions with error message: " +e.getMessage());
	 			}
	 			finally
	 			{
	 				if (session != null && session.isOpen()) {
	 					tx.commit();
	 					session.close();
	 					session.getSessionFactory().close();
	 				} 			
	 			}
			}
		}
			
			return "Inventory";
	}
}
