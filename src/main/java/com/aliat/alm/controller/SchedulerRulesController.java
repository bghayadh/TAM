package com.aliat.alm.controller;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.aliat.alm.models.SchedulerRules;
import com.aliat.alm.models.ReportsList;
import com.aliat.alm.models.ReportInputParams;
import com.aliat.alm.models.RuleParameters;



@Controller
public class SchedulerRulesController {
	
	@Autowired
	ALMSessions almsessions;
	
	private static Session session=null;
	private static Transaction tx = null;
	private static Query query = null;
	
	//Get The Report id to Get the info
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/GetReportID", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> GetReportID(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
		throws JsonGenerationException, JsonMappingException, IOException {


		System.out.println("Welcome to the Method of get all ids in the ajax ");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		
		String ReportName = request.getParameter("ReportName");
		
		System.out.println("The Report Name is "+ReportName);
		
		Query q = session.createQuery(
				"select ReportID from ReportsList where ReportName =:param1 ");

		ObjectMapper mapper = new ObjectMapper();
		
	   q.setString("param1",ReportName );
		q.setFirstResult(0);
		q.setMaxResults(40);
	   List	listReportId = q.list();
	   System.out.println("The list is "+mapper.writeValueAsString(listReportId));
	   System.out.println("The  length of list is "+ listReportId.size());

		 tx.commit();
		 session.close();
		
		rtn.put("ListReportIds", listReportId);
		return rtn;
		}
	
	
	
	//Get ALL Supplier Names
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/GetAllReportName", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllReportName(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
     //logger.info("Welcome home! The client locale is {}.", locale);
        System.out.println("Welcome to GetAllReportName");

		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		ObjectMapper mapper = new ObjectMapper();

		List<ReportsList> listReportName = new ArrayList<ReportsList>();

		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		String ReportID = request.getParameter("ReportID");
		String ReportName = "%"+request.getParameter("ReportName")+"%";
		
     System.out.println("ReportName is " +ReportName);	
     System.out.println("ReportID is " +ReportID);
     
    
		Query q = session.createQuery(
				"select distinct (ReportName), ReportID from ReportsList where ReportName like :param1");

		q.setString("param1", ReportName);
		q.setFirstResult(0);
		q.setMaxResults(40);
		listReportName = q.list();

	
		 tx.commit();
		 session.close();
		
// model.addAttribute("ListItemCategory", mapper.writeValueAsString(listItem));
//System.out.println("end good " + mapper.writeValueAsString(listItem));
		rtn.put("ListReportName", listReportName);
		
     
     
     
     
     
     
     
     

		return rtn;
	}
	

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SchedulerRulesListView", method = RequestMethod.GET)
	
	public String SchedulerRulesListView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) 
			throws JsonGenerationException, JsonMappingException, IOException {
		//logger.info("Welcome home! The client locale is {}.", locale);
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		List<SchedulerRules> listSchedulerRules = new ArrayList<SchedulerRules>();
		
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		
		listSchedulerRules = session.createQuery("SELECT t.RuleID AS ID, t.RuleName,t.ReportTimes,TO_CHAR(t.LastRunTime, 'DD/MM/YYYY HH24:MI') from SchedulerRules t").list();
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("qaqaqaqq"+mapper.writeValueAsString(listSchedulerRules));
		tx.commit();
		session.close();
		
		model.addAttribute("ListGridTable", mapper.writeValueAsString(listSchedulerRules));
		return "SchedulerRulesListView";
}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/SchedulerRulesFormView", method = RequestMethod.GET)
	public String SchedulerRulesFormView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException {
		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		
		System.out.println("***********************************");

		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		boolean IsCheck = true;
		
		model.addAttribute("Weekdays",IsCheck);
		model.addAttribute("Weekdays","Tue");
		String itemsList = request.getParameter("SchedulerRulesList");
		ObjectMapper mapper2 = new ObjectMapper();
		if(itemsList !=null && itemsList.equals("SchedulerRulesList")) {
			System.out.println("get item list");
			Query q = session.createSQLQuery("SELECT RULE_ID FROM SCHEDULER_RULES");
			if(q.list() !=null) {
				
				System.out.println("have list");
				System.out.println(mapper2.writeValueAsString(q.list()));
				model.addAttribute("listItemsNav", mapper2.writeValueAsString(q.list()));
			}
			
		}else 
		{model.addAttribute("listItemsNav", mapper2.writeValueAsString("noList"));
			System.out.println("Automate Report done");}
		
		Date date;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		

		String RuleID = request.getParameter("RuleID");
		model.addAttribute("RuleID",RuleID);
		System.out.println("8888888888888888888888888888888888888888888888888888888888"+RuleID);
		
		
		
		if(RuleID==null) {
			
			
			date = new Timestamp(System.currentTimeMillis());
			model.addAttribute("creationDate", formatter.format(date).toString());
			model.addAttribute("lastModifiedDate", formatter.format(date).toString());
			


			
			return "SchedulerRulesFormView";
		}
		
		
		SchedulerRules SchedulerRules = (SchedulerRules) session.get(SchedulerRules.class, RuleID);
		
		if(SchedulerRules!=null) {
			model.addAttribute("RuleID",SchedulerRules.getRuleID());
			if (SchedulerRules.getCreatedDate() == null) {
				date = new Timestamp(System.currentTimeMillis());
			}
	    	else {
	    		date = SchedulerRules.getCreatedDate();
	    	}
	       	model.addAttribute("creationDate",formatter.format(date).toString());
	       	if (SchedulerRules.getLastModifiedDate() == null) {
				date = new Timestamp(System.currentTimeMillis());
			}
	    	else {
	    		date = SchedulerRules.getLastModifiedDate();
	    	}
	       	
	       	System.out.println("Weekdays Are: " + SchedulerRules.getStatus());
	       	String[] weeks = {"Tuesday"};
	       	List<String> listweek = Arrays.asList(weeks);
	       	//String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
	       	
	       	char enable = 'Y';
	       	List<RuleParameters> Parameters = new ArrayList<RuleParameters>();
	       	Query d = session.createQuery(
					"select FieldID, Value from RuleParameters where Enable =:param1 ORDER BY ID");
	       	

			d.setCharacter("param1", enable);
			
			Parameters = d.list();
			ObjectMapper mapper = new ObjectMapper();
			String rts = "<div><p>Here</p></p>";
			System.out.println("Parameters are: " + mapper.writeValueAsString(Parameters));
	       	
	       	if(listweek.contains("Mon"))
	       		model.addAttribute("Mon",true);
	       	else
	       		model.addAttribute("Mon",false);
	       	
	       	if(listweek.contains("Tue"))
	       		model.addAttribute("Tue",true);
	       	else
	       		model.addAttribute("Tue",false);
	       	
	       	if(listweek.contains("Wed"))
	       		model.addAttribute("Wed",true);
	       	else
	       		model.addAttribute("Wed",false);
	       	
	       	if(listweek.contains("Thu"))
	       		model.addAttribute("Thu",true);
	       	else
	       		model.addAttribute("Thu",false);
	       	
	       	if(listweek.contains("Fri"))
	       		model.addAttribute("Fri",true);
	       	else
	       		model.addAttribute("Fri",false);
	       	
	       	if(listweek.contains("Sat"))
	       		model.addAttribute("Sat",true);
	       	else
	       		model.addAttribute("Sat",false);
	       	
	       	if(listweek.contains("Sun"))
	       		model.addAttribute("Sun",true);
	       	else
	       		model.addAttribute("Sun",false);
	       	
	       	model.addAttribute("lastModifiedDate",formatter.format(date).toString());
	       	model.addAttribute("RuleID",SchedulerRules.getRuleID());
			model.addAttribute("RuleName", SchedulerRules.getRuleName());
			model.addAttribute("ReportID",SchedulerRules.getReportID());
			model.addAttribute("ReportName", SchedulerRules.getReportName());
			model.addAttribute("ReportTimes", SchedulerRules.getReportTimes());
			model.addAttribute("Weekdays", listweek);
			model.addAttribute("ReportMonthdays", SchedulerRules.getReportMonthdays());
			model.addAttribute("ReportDestination", SchedulerRules.getReportDestination());
			model.addAttribute("EmailFrom", SchedulerRules.getEmailFrom());
			model.addAttribute("EmailTo", SchedulerRules.getEmailTo());
			model.addAttribute("EmailSubject", SchedulerRules.getEmailSubject());
			model.addAttribute("Status", SchedulerRules.getStatus());
			model.addAttribute("TriggerTime", SchedulerRules.getTriggerTime());
			model.addAttribute("PhoneNB", SchedulerRules.getPhoneNB());
			if(SchedulerRules.getLastRunTime()!= null)
			model.addAttribute("LastRunTime", formatter.format(SchedulerRules.getLastRunTime()).toString());
			
			

			
			
			
			
			
		}


		
			return "SchedulerRulesFormView";
		}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/SchedulerRulesFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SchedulerRulesFormSave(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		
		SchedulerRules SchedulerRules = new SchedulerRules();
		
		DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		Date date1 = null;
		Date date2 = null;
		Date date3 = null;

		String createdDate = request.getParameter("createdate");
		System.out.println("creation date"+createdDate);
		String dateModification = request.getParameter("lastModifiedDate");
		String RunTime = request.getParameter("LastRunTime");
		System.out.println("creation date"+createdDate);
		date1 = formatter1.parse(createdDate);
		Timestamp CreationDate = new Timestamp(date1.getTime());
		date2 = new Timestamp(System.currentTimeMillis());
		Timestamp lastModificationDate = new Timestamp(date2.getTime());
		boolean initialized = false;
		Timestamp LastRunTime;
		if(RunTime.length() != 0) {
		
			date3 = formatter1.parse(RunTime);
			LastRunTime = new Timestamp(date3.getTime());
			initialized = true;
		}
		else {
			RunTime = ("00/00/0000 0:00 PM");
			date3 = formatter1.parse(RunTime);
			LastRunTime = new Timestamp(date3.getTime());
			initialized = false;
		}
		
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);

		String RuleID;

		RuleID = request.getParameter("RuleID");
		
		if (StringUtils.equalsIgnoreCase(RuleID, "")) 
		{
			synchronized (this) {						
				RuleID = "SCH_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT SCHEDULER_RULES FROM SEQ_TABLE").uniqueResult().toString());	
				Query query = session.createSQLQuery("UPDATE SEQ_TABLE SET SCHEDULER_RULES = SCHEDULER_RULES + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
			//RuleID= "SCH_"+year+"_" +appConfig.getSequenceNbr(44);
			System.out.println("Rule*********"+RuleID);
		}
		String Filter  = request.getParameter("FilteredValues");
		String Variables[] = Filter.split("\n");
		String IDFilter[] = new String[Variables.length];
		String ValueFilter[] = new String[Variables.length];
		String TypeFilter[] = new String[Variables.length];
		String OptionsFilter[] = new String[Variables.length];
		String ReportValues = request.getParameter("ReportValues");
		String CurrentVariables[] = ReportValues.split("\n");
		String ReportFilter[] = new String[CurrentVariables.length];
		String ValueNewFilter[] = new String[CurrentVariables.length];
		String ReportFilterID[] = new String[CurrentVariables.length];
		for(int i =0; i< Variables.length;i++) {
			RuleParameters RuleParameters = new RuleParameters();
			IDFilter[i] = Variables[i].split("=")[0];
			ValueFilter[i] = Variables[i].split("=")[1];
			TypeFilter[i] = Variables[i].split("=")[2];
			OptionsFilter[i] = Variables[i].split("=")[3];
			if(ValueFilter[i].length() !=0 ) {
			RuleParameters.setReportID(request.getParameter("ReportID"));
			RuleParameters.setRuleID(RuleID);
			RuleParameters.setFieldID(IDFilter[i]);
			RuleParameters.setValue(ValueFilter[i]);
			RuleParameters.setType(TypeFilter[i]);
			RuleParameters.setOptions(OptionsFilter[i]);
			if(ValueFilter[i].equalsIgnoreCase("None"))
				RuleParameters.setEnable('N');
			else
				RuleParameters.setEnable('Y');
			RuleParameters.setParameterID(RuleID + i);
			System.out.print("ID FILTEEEEEEEEEEEEER" + Filter);
			System.out.println("LENGTHHHHHHHHHHHH is:  " + ValueFilter[i].length());
			
				session.saveOrUpdate(RuleParameters);}
			
			
			
		}
		
		
		SchedulerRules.setRuleID(RuleID);
		SchedulerRules.setCreatedDate(CreationDate);
		SchedulerRules.setLastModifiedDate(lastModificationDate);
		SchedulerRules.setRuleName(request.getParameter("RuleName"));
		SchedulerRules.setReportID(request.getParameter("ReportID"));
		SchedulerRules.setReportName(request.getParameter("ReportName"));
		SchedulerRules.setEmailTo(request.getParameter("EmailTo"));;
		SchedulerRules.setEmailFrom(request.getParameter("EmailFrom"));;
		SchedulerRules.setPhoneNB(request.getParameter("PhoneNB"));
		SchedulerRules.setEmailSubject(request.getParameter("EmailSubject"));
		SchedulerRules.setReportTimes(request.getParameter("ReportTimes"));
		SchedulerRules.setReportWeekdays(request.getParameter("ReportWeekdays"));
		SchedulerRules.setReportMonthdays(request.getParameter("ReportMonthdays"));
		SchedulerRules.setReportDestination(request.getParameter("ReportDestination"));
		SchedulerRules.setStatus(request.getParameter("Status"));
		if(initialized)
		SchedulerRules.setLastRunTime(LastRunTime);
		
		System.out.print(SchedulerRules.toString());
		System.out.print("Object is : \n" +SchedulerRules.toString());
		
		session.saveOrUpdate(SchedulerRules);
		tx.commit();
		session.close();
		
		rtn.put("AliTest", "SaveDone");
		rtn.put("RuleID", RuleID);
		rtn.put("lstmodifdate",formatter1.format(date2).toString());
		rtn.put("LastRunTime",formatter1.format(date3).toString());
		

		return rtn;
	
	}
	
	@RequestMapping(value = "/DeleteRuleFormView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> DeleteRuleFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
			
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String idForm = request.getParameter("RuleID");
		session = almsessions.getSession();
		if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				
				Query q = session.createQuery("delete SchedulerRules t  where t.RuleID = :param1 ");
				q.setString("param1", idForm);
				q.executeUpdate();
				
			}catch(Exception e) {
				
			}finally {
				
				if(session != null && session.isOpen()) {
					session.close();
					tx.commit();
				}
			}
		}
		rtn.put("ALITest", "DeleteDone");
		return rtn;
		
		
	}
	
	@RequestMapping(value = "/SchedulerRulesListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SchedulerRulesListViewDelete(Locale locale, Model model, HttpServletRequest request,@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {
	
		
		
			
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		session = almsessions.getSession();
		if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
			try {
				String[] idList = request.getParameterValues("RuleID[]");
				String QueryStatement1 = "delete SchedulerRules t  where t.RuleID IN (:param1)";
				query=session.createQuery(QueryStatement1);
				query.setParameterList("param1", idList);
				query.executeUpdate();
				//appConfig.deleteRowsByQueryParamList(SchedulerRules.class, QueryStatement1, "param1",idList);
				
			}catch(Exception e) {
				
			}finally {
				
				if(session != null && session.isOpen()) {
					session.close();
					tx.commit();
				}
			}
		}
		
		
		rtn.put("AliTEST", "DeleteDone");
		return rtn;
		
		}


///////////////
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/GetAllRules", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllRules (Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
	System.out.println("get GetAllRules 1");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
	  System.out.println("get GetAllRules 2");
	
		rtn.put("Login", LoginServices.checkSession(request, response));
		return rtn;
	}
	List<SchedulerRules> listItem = new ArrayList<SchedulerRules>();
	
	Session session = almsessions.getSession();
	if(session != null && session.isOpen()) {
		tx = session.beginTransaction();
		
		try {
			String itemdtl = "%" + request.getParameter("RuleName") + "%";
			listItem = session.createQuery("SELECT t.RuleID, RuleName from SchedulerRules t").list();
			rtn.put("ListSchedulerRules", listItem);
		}catch(Exception e) {
			
		}finally {
			
			if(session != null && session.isOpen()) {
				session.close();
				tx.commit();
			}
		}
	}
	
	return rtn;
}
	
	
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/GetAllReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllReport(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
//logger.info("Welcome home! The client locale is {}.", locale);
System.out.println("Welcome to GetAllReport");

		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		ObjectMapper mapper = new ObjectMapper();

		List<ReportsList> listReport = new ArrayList<ReportsList>();
		String ReportID =  "%"+ request.getParameter("ReportID")+"%";
		String ReportName=request.getParameter("ReportName");
		session = almsessions.getSession();
		if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
			try {
				
				Query q = session.createQuery(
						"select ReportID, ReportName from ReportsList where ReportID like :param1 or ReportName like:param1 ");

		    	q.setString("param1", ReportID);
				q.setFirstResult(0);
				q.setMaxResults(40);
				listReport = q.list();
				rtn.put("ListGetReport", listReport);
			}catch(Exception e) {
				
			}finally {
				
				if(session != null && session.isOpen()) {
					session.close();
					tx.commit();
				}
			}
			
		}

		return rtn;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllInputParams", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllInputParams(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		//logger.info("Welcome home! The client locale is {}.", locale);
		System.out.println("Welcome to GetAllReportParameters");

		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		ObjectMapper mapper = new ObjectMapper();

		List<ReportInputParams> listReportParam = new ArrayList<ReportInputParams>();
		String ReportID = "%"+ request.getParameter("ReportID")+"%";
		session = almsessions.getSession();
		if(session != null && session.isOpen()) {
			
			tx = session.beginTransaction();
			try {
			 	Query q = session.createQuery(
						"select ReportID, InputLabel from ReportInputParams where ReportID like :param1");

		    	q.setString("param1", ReportID);
				q.setFirstResult(0);
				q.setMaxResults(40);
				listReportParam = q.list();
				rtn.put("ListGetReportParam", listReportParam);
			}catch(Exception e) {
				
			}finally {
				
				if(session != null && session.isOpen()) {
					session.close();
					tx.commit();
				}
			}
		}
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetReportInputs", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetReportInputs(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		String ReportID = request.getParameter("ReportID");
		
		session = almsessions.getSession();
		if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
			try {
				Query q = session.createQuery(
						"select InputLabel, InputType, InputName, InputID, InputValue  from ReportInputParams where ReportID =:param1 ORDER BY ID");

				q.setString("param1", ReportID);
				List<Object[]> listResult = q.list();
				ObjectMapper mapper = new ObjectMapper();
				model.addAttribute("listResult", mapper.writeValueAsString(listResult));
				rtn.put("InputsList", listResult);
				
			}catch(Exception e) {
				
			}finally {
				
				if(session != null && session.isOpen()) {
					session.close();
					tx.commit();
				}
			}
			
		}
		return rtn;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllFilters", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllFilters(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		ObjectMapper mapper = new ObjectMapper();
		String ReportID = request.getParameter("ReportID");
		String RuleID = request.getParameter("RuleID");
		session =almsessions.getSession();
		if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
			try {
				Query q = session.createQuery(
						"select FieldID, Value, Enable, Type, Options, ParameterID from RuleParameters where RuleID =:param1 ORDER BY ID");
				q.setString("param1", RuleID);
				List<RuleParameters> listResult = q.list();
				model.addAttribute("listResult", mapper.writeValueAsString(listResult));
				rtn.put("ListGetAllFilters", listResult);
			}catch(Exception e) {
				
			}finally {
				
				if(session != null && session.isOpen()) {
					session.close();
					tx.commit();
				}
			}
		}
		return rtn;
	}


}


	
	





