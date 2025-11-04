package com.aliat.alm.proc;

import java.util.LinkedHashMap;
import java.util.List;
//import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private String str = null;	

	@SuppressWarnings("rawtypes")
	private static Query query = null;

	private static final Logger logger = LoggerFactory.getLogger(CommScopeForm.class);
	
	@RequestMapping(value = "/CommScopeFormSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> CommScopeFormSave(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("Welcome to CommScopeFormSave.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp creationDate, lastModifiedDate;
		String docStatus = request.getParameter("docStatus");

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		/*String token = request.getParameter("status");
		String ipAddress = request.getParameter("ipAddress");
		System.out.println("ipAddress is " + ipAddress + " and token is " + token); */
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
				str = "update process set creation_date = TO_TIMESTAMP('" + creationDate + "', 'YYYY-MM-DD HH24:MI:SS.FF'), "
						+ "last_modification_date = TO_TIMESTAMP('" + lastModifiedDate + "', 'YYYY-MM-DD HH24:MI:SS.FF'), "
						+ "status = '" + docStatus + "' where LINK_NAME = 'CommScope'";

				System.out.println("str is " + str);
				query = session.createNativeQuery(str);
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
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
}
