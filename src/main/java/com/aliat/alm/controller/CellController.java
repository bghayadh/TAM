package com.aliat.alm.controller;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
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
import java.util.Date;
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

import oracle.sql.TIMESTAMP;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.NodeListView;

@Controller
public class CellController {
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
	@RequestMapping(value = "/CellListView", method = RequestMethod.GET)

	public String CellListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {
				List<Object[]> cells = new ArrayList<>();

				 query = session.createNativeQuery("SELECT  ng.Gcell_Id as gcellPk, ng.Gcell_Id as Pk, ng.cellid as cellid,   ng.cellname as cellname , '2G' as Technology, na.unique_node_id as uniqueid, na.site_id as siteid, "
						+ " na.ware_name as sitename, na.Node_Name as nodename,TO_CHAR(ng.creation_date,'YYYY-MM-DD HH24:MI:SS') as creation, TO_CHAR(ng.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') as updated " + 
						" FROM  Node_GCELL ng LEFT JOIN " + 
						" NODE_ACTIVE na ON ng.Node_PK = na.Node_PK union SELECT  nu.Ucell_Id as ucellPk ,nu.Ucell_Id as Pk,  nu.cellid as cellid,   nu.cellname as cellname , '3G' as Technology, na.unique_node_id as uniqueid, na.site_id as siteid, " + 
						" na.ware_name as sitename, na.Node_Name as nodename,TO_CHAR(nu.creation_date,'YYYY-MM-DD HH24:MI:SS') as creation, TO_CHAR(nu.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') as updated " + 
						" FROM  Node_UCELL nu LEFT JOIN " + 
						" NODE_ACTIVE na ON nu.Node_PK = na.Node_PK union SELECT  nl.Lcell_Id as lcellPk ,nl.Lcell_Id as Pk,  nl.cellid as cellid,   nl.cellname as cellname , '4G' as Technology, na.unique_node_id as uniqueid, na.site_id as siteid, " + 
						" na.ware_name as sitename, na.Node_Name as nodename, TO_CHAR(nl.creation_date,'YYYY-MM-DD HH24:MI:SS') as creation,TO_CHAR( nl.update_date,'YYYY-MM-DD HH24:MI:SS') as updated " + 
						" FROM  Node_LCELL nl LEFT JOIN " + 
						" NODE_ACTIVE na ON nl.Node_PK = na.Node_PK");
				
				   cells.addAll(query.getResultList());
				   model.addAttribute("ListGridTable", mapper.writeValueAsString(cells));
					
			
			}catch (Exception e) {
			
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in CellListView due to \n "+ exceptionAsString);
				logger.info("Error in CellListView due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "CellListView";
	}
	
	
	


@SuppressWarnings("unchecked")
@RequestMapping(value = "/CellFormView", method = RequestMethod.GET)

public String CellFormView(Locale locale, Model model, HttpServletRequest request,
		HttpServletResponse response) {

	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	}
	
	session = almsessions.getSession();
	if (session != null && session.isOpen()) {

		tx = session.beginTransaction();
		notification.headerNotifications(session, model);
		String CellPK;
		try {
			CellPK = request.getParameter("CellPk");
			  if (CellPK != null) {
					model.addAttribute("Status", "old");
					if(CellPK.contains("GCELL")) {
						query = session.createNativeQuery("SELECT  ng.Gcell_Id, ng.cellid ,   ng.cellname  , na.unique_node_id , na.site_id , "
								+ " na.ware_name , na.Node_Name ,TO_CHAR(ng.creation_date,'YYYY-MM-DD HH24:MI:SS') , TO_CHAR(ng.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') " + 
								" FROM  Node_GCELL ng LEFT JOIN " + 
								" NODE_ACTIVE na ON ng.Node_PK = na.Node_PK where ng.Gcell_Id=:param1");
						      query.setParameter("param1", CellPK);
				             result = (Object[]) query.uniqueResult();
	                 
				             model.addAttribute("cell_pk", result[0]);
							    model.addAttribute("cell_id", result[1]);
							    model.addAttribute("cell_name", result[2]);
							    model.addAttribute("uniqueId", result[3]);
							    model.addAttribute("site_id", result[4]);
							    model.addAttribute("site_name", result[5]);
							    model.addAttribute("node_name", result[6]);
							    model.addAttribute("creationDate", result[7]);
							    model.addAttribute("LastModified", result[7]);
								   
						
						
						
					}
					else if(CellPK.contains("UCELL")) {
						query = session.createNativeQuery("SELECT  nu.Ucell_Id ,  nu.cellid ,   nu.cellname  , na.unique_node_id , na.site_id , " + 
								" na.ware_name , na.Node_Name ,TO_CHAR(nu.creation_date,'YYYY-MM-DD HH24:MI:SS') , TO_CHAR(nu.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') " + 
								" FROM  Node_UCELL nu LEFT JOIN " + 
								" NODE_ACTIVE na ON nu.Node_PK = na.Node_PK where nu.Ucell_Id=:param1");
						      query.setParameter("param1", CellPK);
				             result = (Object[]) query.uniqueResult();
	                 
				             model.addAttribute("cell_pk", result[0]);
							    model.addAttribute("cell_id", result[1]);
							    model.addAttribute("cell_name", result[2]);
							    model.addAttribute("uniqueId", result[3]);
							    model.addAttribute("site_id", result[4]);
							    model.addAttribute("site_name", result[5]);
							    model.addAttribute("node_name", result[6]);
							    model.addAttribute("creationDate", result[7]);
							    model.addAttribute("LastModified", result[7]);
						}
					else  {
						query = session.createNativeQuery("SELECT  nl.Lcell_Id ,  nL.cellid ,   nL.cellname  , na.unique_node_id , na.site_id , " + 
								" na.ware_name , na.Node_Name ,TO_CHAR(nl.creation_date,'YYYY-MM-DD HH24:MI:SS') , TO_CHAR(nl.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') " + 
								" FROM  Node_LCELL nl LEFT JOIN " + 
								" NODE_ACTIVE na ON nl.Node_PK = na.Node_PK where nl.Lcell_Id=:param1");
						      query.setParameter("param1", CellPK);
				             result = (Object[]) query.uniqueResult();
	                 
				             model.addAttribute("cell_pk", result[0]);
							    model.addAttribute("cell_id", result[1]);
							    model.addAttribute("cell_name", result[2]);
							    model.addAttribute("uniqueId", result[3]);
							    model.addAttribute("site_id", result[4]);
							    model.addAttribute("site_name", result[5]);
							    model.addAttribute("node_name", result[6]);
							    model.addAttribute("creationDate", result[7]);
							    model.addAttribute("LastModified", result[7]);
								   
						
						
						
					}
					
					
			  }
		
		
		
		}catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in CellFormView due to \n "+ exceptionAsString);
			logger.info("Error in CellFormView due to \n "+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
	}

	return "CellFormView";
}
@RequestMapping(value = "/CellFormViewSave", method = RequestMethod.GET)
@ResponseBody
public  Map<String, Object> CellFormViewSave(Locale locale, Model model, HttpServletRequest request,
		@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) throws Exception {

    Map<String, Object> rtn = new LinkedHashMap<>();
    String nodePk = null;

    session = almsessions.getSession();
    if (session != null && session.isOpen()) {
        tx = session.beginTransaction();
        notification.headerNotifications(session, model);
        try {
        	
        	
        	
        	
        	
        	
        	
        	
        }
       

           
         catch (Exception e) {
            sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            exceptionAsString = sw.toString();
            logger.finest("Error in CellFormView due to \n " + exceptionAsString);
            logger.info("Error in CellFormView due to \n " + exceptionAsString);
        } finally {
            if (session != null && session.isOpen()) {
                tx.commit();
                session.close();
            }
        }
        rtn.put("nodePK", nodePk);
    }

  return rtn; 
   
}

}