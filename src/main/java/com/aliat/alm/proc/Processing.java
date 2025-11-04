package com.aliat.alm.discoveryProc;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.math.BigDecimal; // Add this import statement
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
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.DiscoveryProcess;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DiscoveryProcessing {

	@Autowired
	Notify notifications;

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private String str = null;

	@SuppressWarnings("rawtypes")
	private static Query query = null;

	private static final Logger logger = LoggerFactory.getLogger(DiscoveryProcessing.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/DiscoveryListView", method = RequestMethod.GET)
	public String DiscoveryListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			String originalUrl = request.getRequestURL().toString();
			System.out.println("originalUrl is " + originalUrl);
			model.addAttribute("redirectUrl", originalUrl);
			return "Login";
			// return "redirect:/";
		}
		List<DiscoveryProcess> listDiscovery = new ArrayList<DiscoveryProcess>();
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {
				listDiscovery = session.createQuery(
						"select t.linkName,t.processName,t.status,TO_CHAR(t.lastRunningTime, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(t.nextRunningTime, 'YYYY-MM-DD HH24:MI:SS') from DiscoveryProcess t order by t.lastRunningTime DESC")
						.list();
				model.addAttribute("ListGridTable", mapper.writeValueAsString(listDiscovery));
				System.out.println("listDiscovery is " + mapper.writeValueAsString(listDiscovery));
			} catch (Exception e) {
				logger.info("Error on the level of Discovery Listview with a message : " + e + "\n" + e.getMessage());
				model.addAttribute("ListGridTable", "");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "DiscoveryListView";
	}

	@RequestMapping(value = "/DiscoveryProcess", method = RequestMethod.GET)
	public String DiscoveryProcess(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			String originalUrl = request.getRequestURL().toString();
			System.out.println("originalUrl is " + originalUrl);
			System.out.println("originalUrl with ID is " + originalUrl + "?ID=" + request.getParameter("ID"));
			model.addAttribute("redirectUrl", originalUrl + "?ID=" + request.getParameter("ID"));
			return "Login";
		}
		String linkName = null;
		String processName = request.getParameter("ID");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {
				linkName = session
						.createNativeQuery("select LINK_NAME from DISCOVERY_PROCESS where PROCESS_NAME = :param")
						.setParameter("param", processName).uniqueResult().toString();
				System.out.println("listDiscovery is " + linkName);
			} catch (Exception e) {
				logger.info("Error on the level of Discovery Process with a message : " + e + "\n" + e.getMessage());
				model.addAttribute("linkName", "");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return linkName;
	}
}