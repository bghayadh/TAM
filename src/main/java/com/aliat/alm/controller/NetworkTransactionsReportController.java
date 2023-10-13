package com.aliat.alm.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class NetworkTransactionsReportController {

	private static Session session = null;
	private static Transaction tx = null;
	private static final Logger logger = LoggerFactory.getLogger(SpeedCoverageReportController.class);
	private static ObjectMapper mapper = new ObjectMapper();
	private static NativeQuery query = null;
	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/NetworkTransactionsReport", method = RequestMethod.GET)
	public String NetworkTransactionsReport(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {

				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				try {
					query = session.createNativeQuery("SELECT ELEMENT_ID,ELEMENT,ALM_TRANS_TYPE,DISCOVERED_TRANS_TYPE,TO_CHAR(PARSING_DATE,'DD-MM-YYYY HH:mm:ss') as startdate,TO_CHAR(PARSING_DATE,'DD-MM-YYYY HH:mm:ss') as enddate,FROM_SITE,TO_SITE"
							+ ",FROM_NODE,TO_NODE,FROM_CIRCLE,TO_CIRCLE,APPROVED_BY,MODIFIED_BY,SENT_TO_ALM,ALM_APPROVAL_STATUS"
							+ " FROM NETWORK_TRANSACTION WHERE PARSING_DATE between systimestamp - INTERVAL '7' DAY and systimestamp "
							+ "ORDER BY PARSING_DATE DESC");

					model.addAttribute("TransactionsGrid", mapper.writeValueAsString(query.list()));
				} catch (Exception e) {
					logger.info("Error on NetworkTransactionsReport with a message : " + e);
					e.printStackTrace();
					model.addAttribute("TransactionsGrid", "");
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
		}
		return "Reports/NetworkTransactionsReport";

	}

	@RequestMapping(value = "/GenerateNetworkTransactionsReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateNetworkTransactionsReport(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return rtn;
		} else {
			/// GRID
			System.out.println("GenerateNetworkTransactionsReport");
			String StartDate = request.getParameter("startDate");
			if (StartDate.contains("PM")) {
				StartDate = StartDate.replace(" PM", "").trim();

			} else {
				StartDate = StartDate.replace(" AM", "").trim();
			}

			String EndDate = request.getParameter("endDate");
			if (EndDate.contains("PM")) {
				EndDate = EndDate.replace(" PM", "").trim();

			} else {
				EndDate = EndDate.replace(" AM", "").trim();
			}
			
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {

				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				try {
					query = session.createNativeQuery("SELECT ELEMENT_ID,ELEMENT,ALM_TRANS_TYPE,DISCOVERED_TRANS_TYPE,TO_CHAR(PARSING_DATE,'DD-MM-YYYY HH:mm:ss') as startdate,TO_CHAR(PARSING_DATE,'DD-MM-YYYY HH:mm:ss') as enddate,FROM_SITE,TO_SITE"
							+ ",FROM_NODE,TO_NODE,FROM_CIRCLE,TO_CIRCLE,APPROVED_BY,MODIFIED_BY,SENT_TO_ALM,ALM_APPROVAL_STATUS"
							+ " FROM NETWORK_TRANSACTION WHERE PARSING_DATE between TO_DATE('" + StartDate +"','MM/DD/YYYY HH24:MI:SS')"
							+ " and TO_DATE('" + EndDate +"','MM/DD/YYYY HH24:MI:SS')"
							+ " ORDER BY PARSING_DATE DESC");

					System.out.println(mapper.writeValueAsString(query.list()));
					rtn.put("TransactionsGrid",query.list());
				} catch (Exception e) {
					logger.info("Error on NetworkTransactionsReport with a message : " + e);
					e.printStackTrace();
					rtn.put("TransactionsGrid", "");
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
		}
		return rtn;

	}
	
}
