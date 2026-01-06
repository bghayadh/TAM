package com.aliat.alm.Reports;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Clob;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.hibernate.Session;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Controller
public class IpanelEventsReport {
	private static final Logger logger = Logger.getLogger(IpanelEventsReport.class.getName());

	@Autowired
	Notify notifications;
	private Session session = null;
	@SuppressWarnings("rawtypes")
	Query query;
	Object[] result;
	private static ObjectMapper mapper = new ObjectMapper();
	private static StringWriter sw;
	private static String exceptionAsString;

	@RequestMapping(value = "/iPanelEvents", method = RequestMethod.GET)
	public String iPanelEventsReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {

			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {

				notifications.headerNotifications(session, model);
				try {

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in iPanelEventsReport due to \n " + exceptionAsString);
					logger.info("Error in iPanelEventsReport due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
		}
		return "Reports/IpanelEvents";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GenerateiPanelEventsReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateiPanelEventsReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		String fiberCableID = request.getParameter("cableID");
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			try {
				session = AlmDbSession.getInstance().getSession();
				if (session != null && session.isOpen()) {
					String str = "select id, event_id, event_type, event_timestamp, created_at, controller_id, serial_numb, raw_payload, site, site_name, warehouse, longitude, latitude from ipatch_event order by event_timestamp desc";
					NativeQuery<?> qry = session.createNativeQuery(str);
					List<Map<String, String>> iPanelEvents = ((List<Object[]>) qry.getResultList()).stream()
							.map(arr -> {
								Map<String, String> newMap = new LinkedHashMap<>();
								newMap.put("id", String.valueOf(arr[0])); // Convert to String
								newMap.put("event_id", String.valueOf(arr[1])); // Convert to String
								newMap.put("event_type", String.valueOf(arr[2]));
								newMap.put("event_timestamp", String.valueOf(arr[3]));
								newMap.put("created_at", String.valueOf(arr[4]));								
								newMap.put("controller_id", String.valueOf(arr[5]));
								newMap.put("serial_numb", String.valueOf(arr[6]));								
								newMap.put("raw_payload", clobToString(arr[7]));
								newMap.put("site", String.valueOf(arr[8]));
								newMap.put("site_name", String.valueOf(arr[9]));
								newMap.put("warehouse", String.valueOf(arr[10]));
								newMap.put("longitude", String.valueOf(arr[11]));
								newMap.put("latitude", String.valueOf(arr[12]));
								newMap.put("show_location", String.valueOf(arr[8]));
								return newMap;
							}).collect(Collectors.toList());
					System.out.println("iPanelEvents is " +mapper.writeValueAsString(iPanelEvents));
					rtn.put("iPanelEvents", iPanelEvents);
				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GenerateiPanelEventsReport due to \n " + exceptionAsString);
				logger.info("Error in GenerateiPanelEventsReport due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}
	
	private static String clobToString(Object clobObj) {
	    if (clobObj == null) return null;

	    try {
	        Clob clob = (Clob) clobObj;
	        return clob.getSubString(1, (int) clob.length());
	    } catch (Exception e) {
	        throw new RuntimeException("Error converting CLOB to String", e);
	    }
	}	
}