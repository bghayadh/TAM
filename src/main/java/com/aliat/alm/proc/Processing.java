package com.aliat.alm.proc;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.math.BigDecimal; // Add this import statement
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import com.aliat.alm.models.Process;
import com.aliat.alm.models.ProcessOperation;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class Processing {

	@Autowired
	Notify notifications;

	private Session session = null;
	private Transaction tx = null;
	private ObjectMapper mapper = new ObjectMapper();
	private String str = null;
	private Object[] row = null;

	@SuppressWarnings("rawtypes")
	private Query query = null;

	private static final Logger logger = LoggerFactory.getLogger(Processing.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ProcessListView", method = RequestMethod.GET)
	public String ProcessListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			String originalUrl = request.getRequestURL().toString();
			System.out.println("originalUrl is " + originalUrl);
			model.addAttribute("redirectUrl", originalUrl);
			return "Login";
			// return "redirect:/";
		}
		List<Process> listDiscovery = new ArrayList<Process>();
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {
				listDiscovery = session.createQuery(
						"select t.linkName,t.processName,t.status,TO_CHAR(t.lastRunningTime, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(t.nextRunningTime, 'YYYY-MM-DD HH24:MI:SS') from Process t order by t.lastRunningTime DESC")
						.list();
				model.addAttribute("ListGridTable", mapper.writeValueAsString(listDiscovery));
				System.out.println("listDiscovery is " + mapper.writeValueAsString(listDiscovery));
			} catch (Exception e) {
				logger.info("Error on the level of Process Listview with a message : " + e + "\n" + e.getMessage());
				model.addAttribute("ListGridTable", "");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "ProcessListView";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Process", method = RequestMethod.GET)
	public String Process(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			String originalUrl = request.getRequestURL().toString();
			model.addAttribute("redirectUrl", originalUrl + "?ID=" + request.getParameter("ID"));
			return "Login";
		}

		Map<String, Object> processParams = new HashMap<>();
		String processName = request.getParameter("ID");
		List<ProcessOperation> listProcessOperation = new ArrayList<ProcessOperation>();
		;
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {
				row = (Object[]) session.createNativeQuery(
						"select LINK_NAME, STATUS, TO_CHAR(CREATION_DATE, 'YYYY-MM-DD HH24:MI:SS') as CREATION_DATE, "
								+ "TO_CHAR(LAST_MODIFICATION_DATE, 'YYYY-MM-DD HH24:MI:SS') as LAST_MODIFICATION_DATE from PROCESS "
								+ "WHERE PROCESS_NAME = :param")
						.setParameter("param", processName).getResultStream().findFirst().orElse(null);

				if (row != null) {
					processParams.put("linkName", row[0]);
					processParams.put("status", row[1]);
					processParams.put("creationDate", row[2]);
					processParams.put("lastModificationDate", row[3]);
				}

				model.addAttribute("processParams", processParams);

				listProcessOperation = session
						.createQuery("from ProcessOperation where linkName = :param1 order by creation_date desc")
						.setParameter("param1", processParams.get("linkName").toString()).list();
				mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
				model.addAttribute("listProcessOperation", mapper.writeValueAsString(listProcessOperation));
				System.out.println("listProcessOperation is " + mapper.writeValueAsString(listProcessOperation));

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
		return processParams.get("linkName").toString();
	}
}