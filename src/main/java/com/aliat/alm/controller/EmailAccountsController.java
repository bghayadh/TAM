package com.aliat.alm.controller;


import java.io.IOException;

import java.sql.Timestamp;
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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.EmailAccounts;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class EmailAccountsController {
	
	Session session = null;
	Transaction tx = null;
	Query query = null;
	
	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;
	
	@Autowired
	Form form;
	@Autowired
	Notify notification;
	
	/////////////////////////////////  EmailAccountsListView   ///////////////////////////
@SuppressWarnings("unchecked")
@RequestMapping(value = "/EmailAccountsListView", method = RequestMethod.GET)
public String EmailAccountsListView(Locale locale, Model model, HttpServletRequest request,
		HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return LoginServices.checkSession(request, response);
	}
		List<EmailAccounts> listEmailAccounts = new ArrayList<EmailAccounts>();

		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		notification.headerNotifications(session, model);
		
		listEmailAccounts = session.createQuery(
				"select 1,t.id,t.email from EmailAccounts t")
				.list();

		
		ObjectMapper mapper = new ObjectMapper();
		model.addAttribute("ListGridTable", mapper.writeValueAsString(listEmailAccounts));
		tx.commit();
		session.close();
		return "EmailAccountsListView";
}

/////////////////////////////////  EmailAccountsListViewDelete   ///////////////////////////

@RequestMapping(value = "/EmailAccountsListViewDelete", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> EmailAccountsListViewDelete(Locale locale, Model model, HttpServletRequest request,
		HttpServletResponse response) {

	Map<String, Object> rtn = new LinkedHashMap<>();
	if (LoginServices.checkSession(request, response).equals("Login")) {
		rtn.put("Login", LoginServices.checkSession(request, response));
		return rtn;
	}
	
	
	String[] idList = request.getParameterValues("ID[]");
	
	session=almsessions.getSession();
	if(session != null && session.isOpen()) {
		tx = session.beginTransaction();
		
		try {
			
			for(int i=0;i<idList.length;i++) {
				
				query = session.createSQLQuery("DELETE EMAIL_ACCOUNTS WHERE ID='"+idList[i]+"'");
				query.executeUpdate();
			}
			
		}catch(Exception e) {
			System.out.println("Error in EmailAccountsListViewDelete");
		}finally {
            if (session != null && session.isOpen()) {
                tx.commit();
                session.close();
            }
		}
		
	}

	
	rtn.put("Test", "DeleteDone");
	return rtn;

}

////////////////////////////////// EmailAccountsFormViewDelete  /////////////////////////////////////////////

@RequestMapping(value = "/EmailAccountsFormViewDelete", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> EmailAccountsFormViewDelete(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
	//logger.info("Welcome home! The client locale is {}.", locale);
		
	Map<String, Object> rtn = new LinkedHashMap<>();
	
	if(LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", LoginServices.checkSession(request, response));
		return rtn;
	}
			
	String id = request.getParameter("id");
	session=almsessions.getSession();
	if(session != null && session.isOpen()) {
		tx = session.beginTransaction();
		
		try {
			
			
				query = session.createSQLQuery("DELETE EMAIL_ACCOUNTS WHERE ID='"+id+"'");
				query.executeUpdate();
			
			
		}catch(Exception e) {
			System.out.println("Error in EmailAccountsListViewDelete");
		}finally {
            if (session != null && session.isOpen()) {
                tx.commit();
                session.close();
            }
		}
	}


	rtn.put("Test", "DeleteDone");
	return rtn;
}


/////////////////////////////// EmailAccountsFormView  /////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/EmailAccountsFormView", method = RequestMethod.GET)
	public String EmailAccountsFormView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException{
	if(LoginServices.checkSession(request, response).equals("redirect:/")) {
	return LoginServices.checkSession(request, response);
	}
	System.out.println("FORM VIEW testing");


	Configuration cfg = new Configuration().configure();
	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
	SessionFactory sf = cfg.buildSessionFactory(builder.build());
	Session session = sf.openSession();
	Transaction tx = session.beginTransaction();
	notification.headerNotifications(session, model);
	
	String itemsList = request.getParameter("notifiList");
	ObjectMapper mapper = new ObjectMapper();
	if(itemsList !=null && itemsList.equals("notifiList")) {
		System.out.println("get item list");
		Query q = session.createSQLQuery("SELECT ID FROM EMAIL_ACCOUNTS");
		if(q.list() !=null) {
			
			System.out.println("have NFV   list");
			System.out.println(mapper.writeValueAsString(q.list()));
			model.addAttribute("listItemsNav", mapper.writeValueAsString(q.list()));
		}
		
	}else 
	{  
		model.addAttribute("listItemsNav", mapper.writeValueAsString("noList"));
		
		System.out.println("EmailAccounts done");}

String id = request.getParameter("id");
if(id==null) {
	
	model.addAttribute("ListNo", "addNew");
	return 	"EmailAccountsFormView";
}

EmailAccounts EmailAccounts = (EmailAccounts) session.get(EmailAccounts.class, id);
model.addAttribute("id",EmailAccounts.getId());
model.addAttribute("password", EmailAccounts.getPassword());
model.addAttribute("email", EmailAccounts.getEmail());

/*String queryStmt = "SELECT  t.EMAIL_ADDRESS as \"email\",t.PASSWORD  as \"password\""
		+ ",t.ID  as \"id\""		
		+ " FROM EMAIL_ACCOUNTS t where t.ID like :param1";
Query result = session.createSQLQuery(queryStmt);
result.setParameter("param1",id);
List<EmailAccounts> listEmailAccounts = (List<EmailAccounts>) ((SQLQuery) result)
.addScalar("email").addScalar("password").addScalar("id")
.setResultTransformer(Transformers.aliasToBean(EmailAccounts.class)).list();
model.addAttribute("ListNo", mapper.writeValueAsString(listEmailAccounts));*/ //we use this in case of boq table
	return 	"EmailAccountsFormView";
}
	
	
	
	
////////////////////////////////EmailAccountsFormSave    /////////////////////////////////////////////////
	
@RequestMapping(value = "/EmailAccountsFormSave", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> EmailAccountsFormSave(Locale locale, Model model, HttpServletRequest request,
@ModelAttribute ItemParameters itemParameters, HttpServletResponse response, String message) throws Exception {


Map<String, Object> rtn = new LinkedHashMap<>();
if(LoginServices.checkSession(request, response).equals("redirect:/")) {
rtn.put("Login", LoginServices.checkSession(request, response));
return rtn;		}

EmailAccounts EmailAccounts = new EmailAccounts();


session=almsessions.getSession();
if(session != null && session.isOpen()) {
	tx = session.beginTransaction();
	
	Date date = new Date();
	Calendar calendar = new GregorianCalendar();
	calendar.setTime(date);
	int year = calendar.get(Calendar.YEAR);
	String id = request.getParameter("id");

	new ObjectMapper();
	if (StringUtils.equalsIgnoreCase(id, ""))  
	{
		synchronized (this) {						
			id = "EMAIL_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT EMAIL FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET EMAIL = EMAIL + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
		}
	}
	

	String email= request.getParameter("email");
	String password= request.getParameter("password");

	EmailAccounts.setId(id);
	EmailAccounts.setEmail(email);
	EmailAccounts.setPassword(password);
	session.saveOrUpdate(EmailAccounts);
	
	rtn.put("Test", "SaveDone");
	System.out.println("EmailAccounts ID****"+id);
	rtn.put("id", id);
}


return rtn;	
}


////////////////////////////////////////////for autocomplete

@RequestMapping(value = "/GetAllEmailAccounts", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllreqEmailAccounts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
		throws JsonGenerationException, JsonMappingException, IOException {


	Map<String, Object> rtn = new LinkedHashMap<>();
	if(LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", LoginServices.checkSession(request, response));
		return rtn;
	}
	List<EmailAccounts> listEmailAccounts = new ArrayList<EmailAccounts>();

	Configuration cfg = new Configuration().configure();
	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
			.applySettings(cfg.getProperties());
	SessionFactory sf = cfg.buildSessionFactory(builder.build());
	Session session = sf.openSession();
	Transaction tx = session.beginTransaction();
	
	
	String EmailAccountsName = "%" + request.getParameter("email") + "%";
	String Password=request.getParameter("password");
	Query q = session.createQuery("select email,password from EmailAccounts where email like :param1");

	q.setString("param1", EmailAccountsName);
	q.setFirstResult(0);
	q.setMaxResults(40);

				
	listEmailAccounts = q.list();
					System.out.println("autoComplete is ON!!");
					tx.commit();
					session.close();
	ObjectMapper mapper = new ObjectMapper();
	///model.addAttribute("ListItemEmailAccounts", mapper.writeValueAsString(listEmailAccounts));
					System.out.println("values =  "+mapper.writeValueAsString(listEmailAccounts));
					
					
	rtn.put("ListItemEmailAccounts", listEmailAccounts);
	
	return rtn;
	

}

////////////////////////////////////////////////////
}
