package com.aliat.alm.Reports;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.proc.SchedulerService;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Logger;

@Controller
public class ProcessOperationsScheduleReport {
	private static final Logger logger = Logger.getLogger(ProcessOperationsScheduleReport.class.getName());

	@Autowired
	Notify notifications;
	private Session session = null;			
	private final SchedulerService schedulerService;
	private static ObjectMapper mapper = new ObjectMapper();
	private StringWriter sw;
	private String exceptionAsString;

	@Autowired
	public ProcessOperationsScheduleReport(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	@RequestMapping(value = "/OperationsSchedule", method = RequestMethod.GET)
	public String operationsScheduleReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				notifications.headerNotifications(session, model);
				try {
					model.addAttribute("listOperationsReport", mapper.writeValueAsString(getOperationsReport(session)));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in OperationsScheduleReport due to \n " + exceptionAsString);
					logger.info("Error in OperationsScheduleReport due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
		}
		return "Reports/OperationsScheduleReport";
	}

	@RequestMapping(value = "/GenerateOperationsScheduleReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> generateOperationsScheduleReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				try {
					rtn.put("listOperationsReport", getOperationsReport(session));
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in GenerateOperationsScheduleReport due to \n " + exceptionAsString);
					logger.info("Error in GenerateOperationsScheduleReport due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
		}
		return rtn;
	}

	private List<Map<String, Object>> getOperationsReport(Session session) {
		List<Map<String, Object>> operationsReport = new ArrayList<>();
		String str = "";
		List<Object[]> listResult = new ArrayList<>();
		List<Map<String, Object>> jobs = new ArrayList<>();
		Map<String, Date> nextFireByJobId = new LinkedHashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			str = "select id, link_name, operation_name, status, class_name, to_char(last_execution_date, 'YYYY-MM-DD HH24:MI:SS') as last_execution_date, "
				+"cron_expression, to_char(creation_date, 'YYYY-MM-DD HH24:MI:SS') as creation_date, "
				+"to_char(last_modification_date, 'YYYY-MM-DD HH24:MI:SS') as last_modification_date, "
				+"to_char(start_date_time, 'YYYY-MM-DD HH24:MI:SS') as start_date_time from process_operation";
			listResult = session.createNativeQuery(str).list();
			jobs = schedulerService.getAllJobs();
			for (Map<String, Object> job : jobs) {
				Object nft = job.get("Next Fire");
				nextFireByJobId.put((String) job.get("Job ID"), nft instanceof Date ? (Date) nft : null);
			}
			for (Object[] result : listResult) {
				Map<String, Object> operation = new LinkedHashMap<>();
				String operationId = result[0].toString();
				Date operationNextFire = nextFireByJobId.get(operationId); // may be null
				String nextFireStr = operationNextFire != null
				        ? sdf.format(operationNextFire)
				        : null;
				operation.put("id", result[0]);
				operation.put("link_name", result[1]);
				operation.put("operation_name", result[2]);
				operation.put("status", result[3]);
				operation.put("class_name", result[4]);
				operation.put("next_execution_date", operationNextFire);
				operation.put("next_execution_date_str", nextFireStr);
				operation.put("last_execution_date", result[5]);
				operation.put("cron_expression", result[6]);
				operation.put("creation_date", result[7]);
				operation.put("last_modification_date", result[8]);
				operation.put("start_date_time", result[9]);
				operationsReport.add(operation);
			}
			operationsReport.sort(
				    Comparator.comparing(
				        o -> (Date) o.get("next_execution_date"),
				        Comparator.nullsLast(Comparator.naturalOrder())
				    )
				);
			System.out.println("##################################operationsReport is " +mapper.writeValueAsString(operationsReport));
			
		} catch (

		Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest(
					"Error in class: getOperationsReport, method: getOperationsReport due to \n " + exceptionAsString);
			logger.info(
					"Error in class: getOperationsReport, method: getOperationsReport due to \n " + exceptionAsString);
		}
		return operationsReport;
	}
}