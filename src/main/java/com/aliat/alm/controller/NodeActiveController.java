package com.aliat.alm.controller;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.NodeListView;

@Controller
public class NodeActiveController {
	private static final Logger logger = Logger.getLogger(ClientsController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	private static String str;
	private static Object[] result;
	
	@Autowired
	Permissions permissions;
	
	@Autowired
	Form form;
	
	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notification;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/NodeListView", method = RequestMethod.GET)

	public String ClientsListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {
				List<NodeListView> listNodes = new ArrayList<NodeListView>();

				str = "select n.NODE_PK as nodePk, n.NODE_PK as nodePK, n.NODE_ID as nodeID, n.NODE_NAME as nodeName, n.NODE_TYPE as nodeType, n.NODE_MODEL as nodeModel, n.SITE_ID as siteID, TO_CHAR(n.CREATION_DATE,'YYYY-MM-DD HH24:MI:SS') as createdDate,"
					       + "TO_CHAR(n.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') as updateDate,"
					       + "n.WARE_NAME as wareName "
					       + "from NODE_ACTIVE n" + " order by n.UPDATE_DATE DESC";

				Query query = session.createNativeQuery(str);
				listNodes = ((SQLQuery) query).addScalar("nodePk").addScalar("nodePK").addScalar("nodeID").addScalar("nodeName")
						.addScalar("nodeType").addScalar("nodeModel").addScalar("siteID").addScalar("createdDate")
						.addScalar("updateDate").addScalar("wareName")
						.setResultTransformer(Transformers.aliasToBean(NodeListView.class)).list();

				model.addAttribute("ListGridaTable", mapper.writeValueAsString(listNodes));
				} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in NodeListView due to \n "+ exceptionAsString);
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
	
	


@SuppressWarnings("unchecked")
@RequestMapping(value = "/NodeFormView", method = RequestMethod.GET)

public String NodeFormView(Locale locale, Model model, HttpServletRequest request,
		HttpServletResponse response) {

	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	}
	String NodePK;

	session = almsessions.getSession();
	if (session != null && session.isOpen()) {

		tx = session.beginTransaction();
		notification.headerNotifications(session, model);

		try {
			
			NodePK = request.getParameter("NodePk");
			System.out.println(NodePK);
			if (NodePK != null) {
				model.addAttribute("Status", "old");
				query = session.createNativeQuery(
						"select node_id,creation_date,update_date,node_name,node_type,node_model,site_id,ware_name,"
						+ "vendor,domain,node_pk from node_active where node_pk =:param1"); 
							query.setParameter("param1", NodePK);
			        result = (Object[]) query.uniqueResult();
                    model.addAttribute("node_id", result[0]);
				    model.addAttribute("creation_date", result[1]);
				    model.addAttribute("update_date", result[2]);
				    model.addAttribute("node_name", result[3]);
				    model.addAttribute("node_type", result[4]);
				    model.addAttribute("node_model", result[5]);
				    model.addAttribute("site_id", result[6]);
				    model.addAttribute("ware_name", result[7]);
				    model.addAttribute("vendor", result[8]);
				    model.addAttribute("domain", result[9]);
				    model.addAttribute("node_pk", result[10]);
				    
				    query = session.createNativeQuery(
				    	    "SELECT GCELL_ID, CELLID, CELLNAME, MCC, MNC, LAC, CI, NCC, BCC, TYPE, BCCHNO, "
				    	    + "BASEBANDPOLICY, BASEBANDEQMID, GBTSFUNCTIONNAME, NODE_PK, NODE_ATTR_PK, UPDATE_DATE,"
				    	    + " FILENAME, GLOCELLID, STATUS, FROM_TRANS_SOURCE, FROM_TRANS_ID, TO_TRANS_ID, TRANS_TYPE,"
				    	    + " ACTIVE_RECORD, LINE, CREATION_DATE, DOMAIN, VENDOR, TO_TRANS_SOURCE FROM NODE_GCELL WHERE "
				    	    + "NODE_PK = :param1");
				    	query.setParameter("param1", NodePK);

				    	model.addAttribute("listGCELL", mapper.writeValueAsString(query.list()));
						
				    	query = session.createNativeQuery(
				    		    "SELECT UCELL_ID, CELLID, CELLNAME, LOCELL, NODEBFUNCTIONNAME, ULFREQ, DLFREQ, MAXPOWER,"
				    		    + " USERLABEL, MAXTXPOWER, UARFCNUPLINK, UARFCNDOWNLINK, PSCRAMBCODE, NODEBNAME, LAC, SAC,"
				    		    + " RAC, MANUFACTURERDATA, RADIUS, HORAD, DI, NODE_PK, NODE_ATTR_PK, UPDATE_DATE, FILENAME, "
				    		    + "STATUS, FROM_TRANS_SOURCE, FROM_TRANS_ID, TO_TRANS_ID, TRANS_TYPE, ACTIVE_RECORD, LINE, CREATION_DATE,"
				    		    + " DOMAIN, VENDOR, TO_TRANS_SOURCE FROM NODE_UCELL WHERE NODE_PK = :param1");

				    		query.setParameter("param1", NodePK);
				    		
				    		model.addAttribute("listUCELL", mapper.writeValueAsString(query.list()));
							
				    
				    		query = session.createNativeQuery(
				    			    "SELECT LCELL_ID, LOCALCELLID, CELLNAME, CELLRADIUS, FREQBAND, ULEARFCNCFGIND, ULEARFCN, DLEARFCN,"
				    			    + " ULBANDWIDTH, DLBANDWIDTH, CELLID, PHYCELLID, FDDTDDIND, ENODEBFUNCTIONNAME, NBCELLFLAG, NODE_PK,"
				    			    + " NODE_ATTR_PK, UPDATE_DATE, FILENAME, STATUS, FROM_TRANS_SOURCE, FROM_TRANS_ID, TO_TRANS_ID, TRANS_TYPE,"
				    			    + " ACTIVE_RECORD, LINE, CREATION_DATE, DOMAIN, VENDOR, TO_TRANS_SOURCE"
				    			    + " FROM NODE_LCELL WHERE NODE_PK = :param1");

				    				query.setParameter("param1", NodePK);
				    				
				    				model.addAttribute("listLCELL", mapper.writeValueAsString(query.list()));
									

	
		}
			
			else {
				model.addAttribute("Status", "new");
				
				
			}
		
		
		
		
		}catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in NodeFormView due to \n "+ exceptionAsString);
			logger.info("Error in NodeFormView due to \n "+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
	}

	return "NodeFormView";
}


}