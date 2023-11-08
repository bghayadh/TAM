package com.aliat.alm.controller;

//import java.util.List;
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
public class SalesController {
	private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);	

	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Permissions permissions;
	
	@Autowired
	Notify notification;
	
	@RequestMapping(value = "/Sales", method = RequestMethod.GET)
	public String Sales(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {

		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
else {
	
	Session session = null;
	Transaction tx = null;

	session = almsessions.getSession();
	if(session != null && session.isOpen()) {
		tx = session.beginTransaction();
		notification.headerNotifications(session, model);
		try {
			permissions.setPerms(model, permissions.getUserPermsWithSession(session, request), "Image Approval",
					"List");
		} catch (Exception e) {
			logger.info(
					"Error at Sales Class and Sales method while getting notifications and permissions with error message: "
							+ e.getMessage());
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
				session.getSessionFactory().close();
			}
		}
		
	}
	return "Sales";		
}
	
	}		

}
