package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
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
import com.aliat.alm.models.ClientsListView;
import com.aliat.alm.models.NodeListView;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class NodeActiveController {

	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static String queryString = null;
	private static final Logger logger = LoggerFactory.getLogger(NodeActiveController.class);
	private static StringWriter sw;
	private static String exceptionAsString;
	private static String str;
	@RequestMapping(value = "/NodeListView", method = RequestMethod.GET)
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
					
					
					
					try {
						List <NodeListView> listNodes = new ArrayList<NodeListView>();

						str = "select n.NODE_PK as nodePK, n.NODE_ID as nodeID, n.NODE_NAME as nodeName, n.NODE_TYPE as nodeType, n.NODE_MODEL as nodeModel, n.SITE_ID as siteID, TO_CHAR(n.CREATION_DATE,'YYYY-MM-DD HH24:MI:SS') as createdDate,"
							       + "TO_CHAR(n.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') as updateDate,"
							       + "n.WARE_NAME as wareName "
							       + "from NODE_ACTIVE n" + " order by n.UPDATE_DATE DESC";

						Query query = session.createNativeQuery(str);
						listNodes = ((SQLQuery) query).addScalar("nodePK").addScalar("nodeID").addScalar("nodeName")
								.addScalar("nodeType").addScalar("nodeModel").addScalar("siteID").addScalar("createdDate")
								.addScalar("updateDate").addScalar("wareName")
								.setResultTransformer(Transformers.aliasToBean(NodeListView.class)).list();

						model.addAttribute("ListGridaTable", mapper.writeValueAsString(listNodes));
					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						
						logger.info("Error in NodeListView due to \n "+ exceptionAsString);
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
						}
					}
				}
		return "NodeListView";

	}
}
}
