package com.aliat.alm.proc;

import java.util.LinkedHashMap;
import java.util.List;
//import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.ProcessOperation;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.text.DateFormat;

@Controller
public class CommScopeForm {

	@Autowired
	Notify notifications;

	private Session session = null;
	private Transaction tx = null;
	private ObjectMapper mapper = new ObjectMapper();
	private String str = null;

	@SuppressWarnings("rawtypes")
	private Query query = null;

	Map<String, Object> rtn = new LinkedHashMap<>();
	private final SchedulerService schedulerService;

	@Autowired
	private ApplicationContext appContext;

	private static final Logger logger = LoggerFactory.getLogger(CommScopeForm.class);

	@Autowired
	public CommScopeForm(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	@RequestMapping(value = "/CommScopeFormSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> CommScopeFormSave(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute ItemParameters itemParameters)
			throws JsonProcessingException {

		System.out.println("Welcome to CommScopeFormSave.");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp creationDate, lastModifiedDate;
		String docStatus = request.getParameter("docStatus");
		String[] slctDel;
		String id;
		ProcessOperation procOperation = new ProcessOperation();
		List<ProcessOperation> processOperations = new ArrayList<ProcessOperation>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {
				if (request.getParameter("createDate") == "" || request.getParameter("createDate") == null
						|| request.getParameter("createDate") == "null") {
					creationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
				} else {
					creationDate = new Timestamp(formatter.parse(request.getParameter("createDate")).getTime());
				}
				lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
				str = "update process set creation_date = TO_TIMESTAMP('" + creationDate
						+ "', 'YYYY-MM-DD HH24:MI:SS.FF'), " + "last_modification_date = TO_TIMESTAMP('"
						+ lastModifiedDate + "', 'YYYY-MM-DD HH24:MI:SS.FF'), " + "status = '" + docStatus
						+ "' where LINK_NAME = 'CommScope'";

				query = session.createNativeQuery(str);
				query.executeUpdate();

				slctDel = request.getParameterValues("slctDel[]");
				if (slctDel != null) {
					schedulerService.deleteJobs(Arrays.asList(slctDel));
					str = "delete ProcessOperation t where t.ID IN (:param1)";
					query = session.createQuery(str);
					query.setParameterList("param1", slctDel);
					query.executeUpdate();
				}

				if (itemParameters.getDictParameterProcess() != null) {
					for (int i = 0; i < itemParameters.getDictParameterProcess().size(); i++) {
						procOperation = new ProcessOperation();
						if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameterProcess().get(i).get("procID"),
								"0")) {
							synchronized (this) {
								id = "OPERATION_" + year + "_"
										+ Integer.parseInt(session.createNativeQuery("SELECT OPERATION FROM SEQ_TABLE")
												.uniqueResult().toString());
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET OPERATION = OPERATION + 1");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
							}
						} else {
							id = itemParameters.getDictParameterProcess().get(i).get("procID");
						}
						procOperation.setID(id);
						procOperation.setLinkName(request.getParameter("linkName"));
						procOperation.setOperationName(itemParameters.getDictParameterProcess().get(i).get("procName"));
						procOperation
								.setClassName(itemParameters.getDictParameterProcess().get(i).get("procClassName"));
						procOperation.setStatus(itemParameters.getDictParameterProcess().get(i).get("procStatus"));
						procOperation.setStartDateTime(
								(itemParameters.getDictParameterProcess().get(i).get("procStartDateTime") != null
										&& !itemParameters.getDictParameterProcess().get(i).get("procStartDateTime")
												.isEmpty())
														? new Timestamp(
																formatter
																		.parse(itemParameters.getDictParameterProcess()
																				.get(i).get("procStartDateTime"))
																		.getTime())
														: null);
						procOperation
								.setCronExpression(itemParameters.getDictParameterProcess().get(i).get("procCronExpr"));
						session.saveOrUpdate(procOperation);
						processOperations.add(procOperation);
					}
					for (ProcessOperation op : processOperations) {
						trySchedule(op, docStatus);
					}
				}
			} catch (Exception e) {
				logger.info("Error in saving CommScope Process  with a message : " + e + "\n\" + e.getMessage()", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopeRunProc", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeRunProc(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcome to CommScopeRunProc");
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String beanName = request.getParameter("procClassName");
				if (!appContext.containsBean(beanName)) {
					throw new IllegalArgumentException("No Spring bean found with name: " + beanName);
				}
				ExecutableOperation job = (ExecutableOperation) appContext.getBean(beanName);
				job.execute(request.getParameter("procName"), request.getParameter("procCronExpr"));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		rtn.put("Result", "Success");
		return rtn;
	}

	private void trySchedule(ProcessOperation operation, String procStatus) {
		try {
			schedulerService.scheduleJob(operation, procStatus);
			logger.info("Needed scheduling action done for operation: {}", operation.getOperationName());
		} catch (Exception e) {
			logger.warn("Failed to schedule operation: {} - {}", operation.getOperationName(), e.getMessage());
		}
	}
}