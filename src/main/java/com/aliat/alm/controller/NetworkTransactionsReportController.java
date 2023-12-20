package com.aliat.alm.controller;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class NetworkTransactionsReportController {
	private static final Logger logger = Logger.getLogger(NetworkTransactionsReportController.class.getName());
	private static ObjectMapper mapper = new ObjectMapper();
	private static Session session = null;
	@SuppressWarnings("rawtypes")
	private static Query query = null;

	@Autowired
	Notify notifications;

	@RequestMapping(value = "/NetworkTransactionsReport", method = RequestMethod.GET)
	public String NetworkTransactionsReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {

			try {
				session = AlmDbSession.getInstance().getSession();
				notifications.headerNotifications(session, model);
				query = session.createNativeQuery(
						"SELECT a.ELEMENT_ID,a.ELEMENT,a.ALM_TRANS_TYPE,a.DISCOVERED_TRANS_TYPE,TO_CHAR(a.PARSING_DATE,'DD-MM-YYYY HH:mm:ss') as startdate,a.FROM_SITE,a.TO_SITE," + 
						" b.FROM_NODE_ID,b.TO_NODE_ID,b.FROM_NODE_TYPE,b.TO_NODE_TYPE,MODEL,a.MAC_ADDRESS,a.SERIAL_NUMBER,a.FROM_CIRCLE,a.TO_CIRCLE,a.APPROVED_BY,a.MODIFIED_BY,a.SENT_TO_ALM,a.ALM_APPROVAL_STATUS" + 
						" FROM NETWORK_TRANSACTION a LEFT JOIN NODE_TRANSACTIONS b on a.trans_id=b.trans_id" + 
						" WHERE a.PARSING_DATE between systimestamp - INTERVAL '7' DAY and systimestamp" + 
						" ORDER BY a.element_id DESC");

				model.addAttribute("TransactionsGrid", mapper.writeValueAsString(query.getResultList()));
			} catch (Exception e) {
				logger.info("Error on NetworkTransactionsReport with a message : " + e);
				e.printStackTrace();
				model.addAttribute("TransactionsGrid", "");
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		return "Reports/NetworkTransactionsReport";

	}

	@RequestMapping(value = "/GenerateNetworkTransactionsReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateNetworkTransactionsReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
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

			try {
				session = AlmDbSession.getInstance().getSession();
				notifications.headerNotifications(session, model);
				query = session.createNativeQuery(
						"SELECT a.ELEMENT_ID,a.ELEMENT,a.ALM_TRANS_TYPE,a.DISCOVERED_TRANS_TYPE,TO_CHAR(a.PARSING_DATE,'DD-MM-YYYY HH:mm:ss') as startdate,a.FROM_SITE,a.TO_SITE," + 
						" b.FROM_NODE_ID,b.TO_NODE_ID,b.FROM_NODE_TYPE,b.TO_NODE_TYPE,MODEL,a.MAC_ADDRESS,a.SERIAL_NUMBER,a.FROM_CIRCLE,a.TO_CIRCLE,a.APPROVED_BY,a.MODIFIED_BY,a.SENT_TO_ALM,a.ALM_APPROVAL_STATUS" + 
						" FROM NETWORK_TRANSACTION a LEFT JOIN NODE_TRANSACTIONS b on a.trans_id=b.trans_id "
						+ "WHERE a.PARSING_DATE between TO_DATE('" + StartDate+ "','MM/DD/YYYY HH24:MI:SS')" 
						+ "and TO_DATE('" + EndDate + "','MM/DD/YYYY HH24:MI:SS')" + " ORDER BY a.PARSING_DATE DESC");
				System.out.println(mapper.writeValueAsString(query.getResultList()));
				rtn.put("TransactionsGrid", query.getResultList());
			} catch (Exception e) {
				logger.info("Error on NetworkTransactionsReport with a message : " + e);
				e.printStackTrace();
				rtn.put("TransactionsGrid", "");
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		return rtn;

	}

}
