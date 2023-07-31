package com.aliat.alm.controller;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.FloatType;
import org.hibernate.type.StringType;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class AccountController {

	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static String queryString = null;
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@RequestMapping(value = "/Account", method = RequestMethod.GET)
	public String Accounting(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = almsessions.getSession(); 
				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();
					notifications.headerNotifications(session, model);
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
				}
		return "Account";

	}
}
