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
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;

@Controller
public class SetupController {

	private static final Logger logger = LoggerFactory.getLogger(SetupController.class);
	private static Session session = null;
	private static Transaction tx = null;

	@Autowired
	Notify notifications;

	@RequestMapping(value = "/Setup", method = RequestMethod.GET)
	public String Setup(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		} else {
			session = AlmDbSession.getInstance().getSession();
			System.out.println("HashCode Setup: " + AlmDbSession.getInstance().hashCode());
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
			return "Setup";
		}
	}

	@RequestMapping(value = "/roleGrid", method = RequestMethod.GET)
	public String roleGrid(Locale locale, Model model) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		return "roleGrid";
	}

	@RequestMapping(value = "/DumpsProcessing", method = RequestMethod.GET)
	public String DumpsProcessing(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		} else {
			session = AlmDbSession.getInstance().getSession();
			System.out.println("HashCode Setup: " + AlmDbSession.getInstance().hashCode());
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "DumpsProcessing";
	}
}