package com.aliat.alm.Reports;


import java.io.IOException;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.models.ScheduleReport;



@Controller
public class ScheduleReportController {
	
	@Autowired
	ALMSessions almsessions;
	
	private static Session session = null;
	private static Query query = null;
	private static Transaction tx = null;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ScheduleReportListView", method = RequestMethod.GET)
	
	public String ScheduleReportListView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) 
			throws JsonGenerationException, JsonMappingException, IOException {
		//logger.info("Welcome home! The client locale is {}.", locale);
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		List<ScheduleReport> listScheduleReport = new ArrayList<ScheduleReport>();
		
		
	
		session = almsessions.getSession();
		if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
			try {
				
				listScheduleReport = session.createQuery("SELECT t.ruleID AS ID, t.rName,t.repTimes,t.phoneNumber,t.emailFrom,t.emailTo,t.emailSubject,t.lastRunTime from ScheduleReport t").list();
				ObjectMapper mapper = new ObjectMapper();
				System.out.println("qaqaqaqq"+mapper.writeValueAsString(listScheduleReport));
				model.addAttribute("ListGridTable", mapper.writeValueAsString(listScheduleReport));
				
			}catch(Exception e) {
				
			}finally {
				
				if(session != null && session.isOpen()) {
					session.close();
					tx.commit();
				}
			}
		}
		
		
		
		return "ScheduleReportListView";
}
	@RequestMapping(value = "/ScheduleReportFormView", method = RequestMethod.GET)
	public String ScheduleReportFormView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException {
		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		
		System.out.println("***********************************");

		session = almsessions.getSession();
		if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
			try {
				String itemsList = request.getParameter("scheduleList");
				ObjectMapper mapper2 = new ObjectMapper();
				if(itemsList !=null && itemsList.equals("scheduleList")) {
					System.out.println("get item list");
					Query q = session.createSQLQuery("SELECT RULE_ID FROM SCHEDULEREPORT");
					if(q.list() !=null) {
						
						System.out.println("have list");
						System.out.println(mapper2.writeValueAsString(q.list()));
						model.addAttribute("listItemsNav", mapper2.writeValueAsString(q.list()));
					}
					
				}else 
				{model.addAttribute("listItemsNav", mapper2.writeValueAsString("noList"));
					System.out.println("Schedule Report done");}
				
				Date date;
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				

				String ruleId = request.getParameter("ruleID");
				model.addAttribute("ruleID",ruleId);
				System.out.println("8888888888888888888888888888888888888888888888888888888888"+ruleId);
				
				if(ruleId==null) {
					
					
					date = new Timestamp(System.currentTimeMillis());
					model.addAttribute("creationDate", formatter.format(date).toString());
					model.addAttribute("lastModifiedDate", formatter.format(date).toString());


					
					return "ScheduleReportFormView";
				}
				
				ScheduleReport scheduleReport = (ScheduleReport) session.get(ScheduleReport.class, ruleId);
				
				if(scheduleReport!=null) {
					model.addAttribute("ruleID",scheduleReport.getRuleID());
					if (scheduleReport.getCreatedDate() == null) {
						date = new Timestamp(System.currentTimeMillis());
					}
			    	else {
			    		date = scheduleReport.getCreatedDate();
			    	}
			       	model.addAttribute("creationDate",formatter.format(date).toString());
			       	if (scheduleReport.getLastModifiedDate() == null) {
						date = new Timestamp(System.currentTimeMillis());
					}
			    	else {
			    		date = scheduleReport.getLastModifiedDate();
			    	}
			       	
			       	model.addAttribute("lastModifiedDate",formatter.format(date).toString());
			       	model.addAttribute("ruleID",scheduleReport.getRuleID());
					model.addAttribute("rName", scheduleReport.getrName());
					model.addAttribute("repTimes", scheduleReport.getRepTimes());
					model.addAttribute("lastRunTime", scheduleReport.getLastRunTime());				
				}
			}catch(Exception e) {
				
			}finally {
				
				if(session != null && session.isOpen()) {
					session.close();
					tx.commit();
				}
			}
			
		}
		

		


		
			return "ScheduleReportFormView";
		}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/ScheduleReportFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ScheduleReportFormSave(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		
		ScheduleReport ScheduleReport = new ScheduleReport();

		DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		Date date1 = null;
		Date date2 = null;
		Date date3 = null;

		String createdDate = request.getParameter("createdate");
		String dateModification = request.getParameter("lastModifiedDate");
		String dateRunTime = request.getParameter("LastRunTime");
		System.out.println("creation date"+createdDate);
		date1 = formatter1.parse(createdDate);
		Timestamp CreationDate = new Timestamp(date1.getTime());
		date2 = new Timestamp(System.currentTimeMillis());
		Timestamp lastModificationDate = new Timestamp(date2.getTime());
		date3 = new Timestamp(System.currentTimeMillis());
		Timestamp LastRunTime = new Timestamp(date3.getTime());
		
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);

		String ruleID;

		ruleID = request.getParameter("RuleId");
		
		if (StringUtils.equalsIgnoreCase(ruleID, "")) 
		{
			synchronized (this) {						
				ruleID = "SCH_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT SCHEDULE_REPORT FROM SEQ_TABLE").uniqueResult().toString());	
				Query query = session.createSQLQuery("UPDATE SEQ_TABLE SET SCHEDULE_REPORT = SCHEDULE_REPORT + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
			//ruleID= "SCH_"+year+"_" +appConfig.getSequenceNbr(40);
			System.out.println("Rule*********"+ruleID);
		}
		
		ScheduleReport.setRuleID(ruleID);
		ScheduleReport.setCreatedDate(CreationDate);
		ScheduleReport.setLastModifiedDate(lastModificationDate);
		ScheduleReport.setrName(request.getParameter("RuleName"));
		ScheduleReport.setEmailTo(request.getParameter("EmailTo"));;
		ScheduleReport.setEmailFrom(request.getParameter("EmailFrom"));;
		ScheduleReport.setPhoneNumber(request.getParameter("PhoneNumber"));
		ScheduleReport.setEmailSubject(request.getParameter("EmailSubject"));
		ScheduleReport.setRepTimes(request.getParameter("ScheduleTime"));
		ScheduleReport.setLastRunTime(request.getParameter("LastRunTime"));
		
		System.out.print(ScheduleReport.toString());
		System.out.print("Object is : \n" +ScheduleReport.toString());
		session.saveOrUpdate(ScheduleReport);
		tx.commit();
		session.close();
		
		rtn.put("AliTest", "SaveDone");
		rtn.put("ruleID", "1");
		rtn.put("lstmodifdate",formatter1.format(date2).toString());
		rtn.put("LastRunTime","Any");
		

		return rtn;
	
	}
	
	@RequestMapping(value = "/DeleteScheduleFormView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> DeleteScheduleFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
			
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String idForm = request.getParameter("ruleID");
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
				
		Query q = session.createQuery("delete ScheduleReport t  where t.ruleID = :param1 ");
		
		q.setString("param1", idForm);
		q.executeUpdate();
		
		tx.commit();
		session.close();
		rtn.put("ALITest", "DeleteDone");
		return rtn;
		
		
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/ScheduleListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ScheduleListViewDelete(Locale locale, Model model, HttpServletRequest request,@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {
	
		
		
			
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		ObjectMapper mapper = new ObjectMapper();
		
		String[] idList = request.getParameterValues("RuleId[]");
		
		session = almsessions.getSession();
		if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String QueryStatement1 = "delete ScheduleReport t  where t.ruleID IN (:param1)";
				query=session.createQuery(QueryStatement1);
				query.setParameterList("param1", idList);
				query.executeUpdate();
			}catch(Exception e) {
				
				System.out.println("Error in ScheduleListViewDelete");
			}finally {
				
				if(session != null && session.isOpen()) {
					session.close();
					tx.commit();
				}
			}
			
		}
		
		//appConfig.deleteRowsByQueryParamList(ScheduleReport.class, QueryStatement1, "param1",idList);
		
		rtn.put("AliTEST", "DeleteDone");
		return rtn;
		
		}


///////////////
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/GetAllSchedules", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllSchedules (Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
	System.out.println("get GetAllScchedules 1");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
	  System.out.println("get GetAllSchedules 2");
	
		rtn.put("Login", LoginServices.checkSession(request, response));
		return rtn;
	}
	List<ScheduleReport> listItem = new ArrayList<ScheduleReport>();
	
	Session session = almsessions.getSession();
	Transaction tx = session.beginTransaction();
	String itemdtl = "%" + request.getParameter("RuleName") + "%";
	
	listItem = session.createQuery("SELECT t.ruleID, rName from ScheduleReport t").list();
	
	System.out.println("Length of listItem is " +listItem.size());
	
	
	
	
	
	
		tx.commit();
		session.close();
		ObjectMapper mapper = new ObjectMapper();
		
		
	
		System.out.println("/*/*end good " + mapper.writeValueAsString(listItem));
		rtn.put("ListSchedule", listItem);
		
		
	
	return rtn;
	}
	





}


	
	




