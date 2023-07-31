package com.aliat.alm.controller;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.AgentsListView;
import com.aliat.alm.models.Notification;
import com.aliat.alm.models.NotificationRecipients;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class NotificationController {

	
	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Permissions permissions;
	
	@Autowired
	Notify notification;
	private static Query query = null;
	private static Session session=null;
	private static Transaction tx = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	private static final Logger logger = Logger.getLogger(AgentsController.class.getName());
	
@SuppressWarnings("unchecked")
@RequestMapping(value = "/NotificationListView", method = RequestMethod.GET)
public String NotificationListView(Locale locale, Model model, HttpServletRequest request,
		HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return LoginServices.checkSession(request, response);
	}
	List<Notification> listNotification = new ArrayList<Notification>();

	Session session = almsessions.getSession();
	if (session != null && session.isOpen()) {

		Transaction tx = session.beginTransaction();
		notification.headerNotifications(session, model);
		try {
			listNotification = session.createQuery(
					"select 1,t.id,t.notificationName,t.subject, t.enabled, t.moduleName,t.sendAlertOn from Notification t")
					.list();

			ObjectMapper mapper = new ObjectMapper();
			model.addAttribute("ListGridTable", mapper.writeValueAsString(listNotification));
			
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in NotificationListView due to \n "+ exceptionAsString);
			logger.info("Error in NotificationListView due to \n "+ exceptionAsString);
			model.addAttribute("ListGridTable", "");
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
	}
	return "NotificationListView";
}


@RequestMapping(value = "/NotificationListViewDelete", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> NotificationListViewDelete(Locale locale, Model model, HttpServletRequest request,
		HttpServletResponse response) {

	Map<String, Object> rtn = new LinkedHashMap<>();
	if (LoginServices.checkSession(request, response).equals("Login")) {
		rtn.put("Login", LoginServices.checkSession(request, response));
		return rtn;
	}
	String[] idList = request.getParameterValues("ID[]");
	session = almsessions.getSession();
	if(session != null && session.isOpen()) {
		tx = session.beginTransaction();
		try {
			for(int i=0;i<idList.length;i++) {
				query = session.createSQLQuery("DELETE NOTIFICATION_RECIPIENTS where NOTIFICATION_ID ='"+idList[i]+"'");
				query.executeUpdate();
				
				query = session.createSQLQuery("DELETE NOTIFICATION where ID ='"+idList[i]+"'");
				query.executeUpdate();
			}
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in NotificationListViewDelete due to \n "+ exceptionAsString);
			logger.info("Error in NotificationListViewDelete due to \n "+ exceptionAsString);
			model.addAttribute("ListGridTable", "");
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
	}
	
	rtn.put("Test", "DeleteDone");
	return rtn;

}


@RequestMapping(value = "/NotificationFormViewDelete", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> NotificationFormViewDelete(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
	//logger.info("Welcome home! The client locale is {}.", locale);
		
	Map<String, Object> rtn = new LinkedHashMap<>();
	
	if(LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", LoginServices.checkSession(request, response));
		return rtn;
	}
			
	String noid = request.getParameter("id");
	
	Session session = almsessions.getSession();
	if(session != null && session.isOpen()) {
		Transaction tx = session.beginTransaction();
		try {
			query = session.createSQLQuery("DELETE NOTIFICATION_RECIPIENTS where NOTIFICATION_ID ='"+noid+"'");
			query.executeUpdate();
			
			query = session.createSQLQuery("DELETE NOTIFICATION where ID ='"+noid+"'");
			query.executeUpdate();
		}catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in NotificationFormViewDelete due to \n "+ exceptionAsString);
			logger.info("Error in NotificationFormViewDelete due to \n "+ exceptionAsString);
			model.addAttribute("ListGridTable", "");
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
	}
	rtn.put("Test", "DeleteDone");
	return rtn;
}


/////////////////               NotificationFormView     ////////////////////

@SuppressWarnings("unchecked")
@RequestMapping(value = "/NotificationFormView", method = RequestMethod.GET)
public String NotificationFormView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException{
if(LoginServices.checkSession(request, response).equals("redirect:/")) {
return LoginServices.checkSession(request, response);
}

session = almsessions.getSession();
if(session != null && session.isOpen()) {
	tx = session.beginTransaction();

	String itemsList = request.getParameter("notifiList");
	ObjectMapper mapper2 = new ObjectMapper();
	if(itemsList !=null && itemsList.equals("notifiList")) {
		System.out.println("get item list");
		Query q = session.createSQLQuery("SELECT ID FROM NOTIFICATION");
		if(q.list() !=null) {
			
			System.out.println("have NFV   list");
			System.out.println(mapper2.writeValueAsString(q.list()));
			model.addAttribute("listItemsNav", mapper2.writeValueAsString(q.list()));
		}
		
	}else 
	{  
		model.addAttribute("listItemsNav", mapper2.writeValueAsString("noList"));
		
		System.out.println("Notification done");}

	Date date;
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");


	String Id = request.getParameter("id");
	model.addAttribute("id",Id);
	System.out.println("checking ID   "+Id);



	if(Id==null) {
		
		model.addAttribute("ListNo", "addNew");
		date = new Timestamp(System.currentTimeMillis());
		model.addAttribute("creationDate", formatter.format(date).toString());
		model.addAttribute("lastModifiedDate", formatter.format(date).toString());

		

		return "NotificationFormView";
	}


	Notification notification = (Notification) session.get(Notification.class, Id);

	if(notification!=null) {
		model.addAttribute("id",notification.getNotificationID());
		if (notification.getCreationDate() == null) {
			date = new Timestamp(System.currentTimeMillis());
		}
		else {
			date = notification.getCreationDate();
		}
	   	model.addAttribute("creationDate",formatter.format(date).toString());
	   	if (notification.getLastModifiedDate() == null) {
			date = new Timestamp(System.currentTimeMillis());
		}
		else {
			date = notification.getLastModifiedDate();
		}
	   	
	   	
	   	model.addAttribute("lastModifiedDate",formatter.format(date).toString());
	   	model.addAttribute("id",notification.getNotificationID());
		model.addAttribute("notificationName", notification.getNotificationName());
		model.addAttribute("subject", notification.getSubject());
		model.addAttribute("moduleName", notification.getModuleName());
		model.addAttribute("channel", notification.getChannel());
		model.addAttribute("sendAlertOn",notification.getSendAlertOn());
		model.addAttribute("sender", notification.getSender());
		model.addAttribute("condition",notification.getCondition());
		model.addAttribute("message",notification.getMessage());
		model.addAttribute("enabled",notification.getEnabled());
		
	}
	//////////////////////////////////// data from Recipients BOQ ///////////////////////////////////////////////////
	List permList = permissions.getUserPerms(request);
	permissions.setPerms(model, permList, "Notification", "Form");

		
		Session session1 = almsessions.getSession();
		Transaction tx1 = session1.beginTransaction();
		ObjectMapper mapper = new ObjectMapper();
		String queryStmt = "SELECT  t.JOB_TITLE as \"jobTitle\",t.EMAIL_TO  as \"emailTo\""
		+ ",t.CC_EMAIL as \"ccEmail\",t.CONDITION  as \"Condition\""
		+ ",t.RECIPIENTS_ID  as \"ID\""		
		+ " FROM NOTIFICATION_RECIPIENTS t where t.NOTIFICATION_ID like :param1";

		
		Query result = session1.createSQLQuery(queryStmt);
		result.setParameter("param1",Id);
		
		@SuppressWarnings("unchecked")
		List<NotificationRecipients> listNotificationRecipients = (List<NotificationRecipients>) ((SQLQuery) result)
				.addScalar("jobTitle").addScalar("emailTo").addScalar("ccEmail").addScalar("Condition").addScalar("ID")
				.setResultTransformer(Transformers.aliasToBean(NotificationRecipients.class)).list();
		tx1.commit();
		session1.close();

		model.addAttribute("ListNo", mapper.writeValueAsString(listNotificationRecipients));
	System.out.println("testing ListNo  ==="+model.addAttribute("ListNo", mapper.writeValueAsString(listNotificationRecipients)));
	
}


	return "NotificationFormView";

}


////////        NotificationFormSave    /////////
	
	@RequestMapping(value = "/NotificationFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> NotificationFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response, String message) throws Exception {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;		}
	 
		Notification Notification = new Notification();
		
		
		DateFormat formatter1 =new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		
		Date date1 = null;
		Date date2 = null;
		Date date3 = null;

		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		
		String noid = request.getParameter("id"); //noid=notification id
		
		session = almsessions.getSession();
		if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
			try {
				if (StringUtils.equalsIgnoreCase(noid, ""))  
				{
					
					//noid= "Notification_"+year+"_" +appConfig.getSequenceNbr(45);
					synchronized (this) {						
						noid = "Notification_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT Notification FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET Notification = Notification + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}
				}
				
				String notificationName = request.getParameter("notificationName");
				String Subject = request.getParameter("subject");
				String moduleName =request.getParameter("moduleName");
				String channel = request.getParameter("channel");
				String sendAlertOn =request.getParameter("sendAlertOn");
				String sender = request.getParameter("sender");
				String condition = request.getParameter("condition");
				String Message =request.getParameter("message");
				String lastModifiedDate = request.getParameter("lastModifiedDate");
				String creationDate = request.getParameter("creationDate");
				String enabled = request.getParameter("enabled");
			
				
				
				
				Timestamp CreationDate;
				if (request.getParameter("creationDate") == "") {
					
					
					date1 = new Timestamp(System.currentTimeMillis());
					CreationDate = new Timestamp(date1.getTime());
					Notification.setCreationDate(CreationDate);
				}
				String createdDate = request.getParameter("creationDate");
				date1 = formatter1.parse(createdDate);
				CreationDate = new Timestamp(date1.getTime());

				date2 = new Timestamp(System.currentTimeMillis());
				Timestamp LastModifiedDatee = new Timestamp(date2.getTime());
				
				Notification.setNotificationID(noid);
				Notification.setNotificationName(notificationName);
				Notification.setSubject(Subject);;
				Notification.setModuleName(moduleName);
				Notification.setChannel(channel);
				Notification.setSendAlertOn(sendAlertOn);
				Notification.setSender(sender);
				Notification.setCondition(condition);
				Notification.setMessage(Message);
				Notification.setEnabled(enabled);
				Notification.setLastModifiedDate(LastModifiedDatee);
				Notification.setCreationDate(CreationDate);
				
				session.saveOrUpdate(Notification);
				//appConfig.persist(Notification, Notification.class);
				////////////////////////////////////////////////////end of SAVING DATA TO NOTIFICATION TABLE
				
				String[] slctDelRecipient = request.getParameterValues("slctDelRecipient[]");

				System.out.println("slctDelRecipient      ==== "+Arrays.toString(slctDelRecipient));
					if(slctDelRecipient!=null) {
					String QueryStatement = "delete NotificationRecipients t  where t.ID IN (:param1)";
					query=session.createQuery(QueryStatement);					
					query.setParameterList("param1", slctDelRecipient);
					query.executeUpdate();
					//appConfig.deleteRowsByQueryParamList(NotificationRecipients.class, QueryStatement, "param1",slctDelRecipient);
					}
				
				  //////////////////                     Start of RECIPIENTS BOQ table                 //////////////////////
					new ObjectMapper();
					NotificationRecipients NotificationRecipients = new NotificationRecipients();
				if (itemParameters.getDictParameter() != null) {
					
					String reid; // reid=recipient id
					String jobtitle, Email, Condition1, creationDate1, LastModifiedDate,notificationID,EmailTo;
					System.out.println("getDictParameter size    ======:  "+itemParameters.getDictParameter().size());
					
					for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
						
						reid = itemParameters.getDictParameter().get(i).get("ID");
						EmailTo = itemParameters.getDictParameter().get(i).get("emailTo");
						jobtitle = itemParameters.getDictParameter().get(i).get("jobTitle");
						Email = itemParameters.getDictParameter().get(i).get("ccEmail");
						Condition1 = itemParameters.getDictParameter().get(i).get("Condition");
						creationDate1= itemParameters.getDictParameter().get(i).get("CreationDate");
						LastModifiedDate = itemParameters.getDictParameter().get(i).get("LastModifiedDate");
						notificationID =noid;
						
						/*System.out.println("testing BOQ inputs: reid  "+reid);
						System.out.println("testing BOQ inputs:jobtitle  "+jobtitle);
						System.out.println("testing BOQ inputs:ccEmail  "+ Email);
						System.out.println("testing BOQ inputs:Condition  "+Condition1);
						System.out.println("testing BOQ inputs:CreationDate1  "+creationDate1);
						System.out.println("testing BOQ inputs:LastModifiedDate  "+LastModifiedDate);
						System.out.println("testing BOQ inputs:notificatinID  "+notificationID);*/
						
						
						if (StringUtils.equalsIgnoreCase(reid, "")) {
							//reid = "RECIPIENT_"+year+"_"+appConfig.getSequenceNbr(46);
							synchronized (this) {						
								reid = "RECIPIENT_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT NOTIFICATION_RECIPIENT FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createSQLQuery("UPDATE SEQ_TABLE SET NOTIFICATION_RECIPIENT = NOTIFICATION_RECIPIENT + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
								}
							
							Timestamp CreateDateassign = new Timestamp(date.getTime());
						
							NotificationRecipients.setID(reid);;
						
							DateFormat formatterAssign = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
							DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");													 
							NotificationRecipients.setJobTitle(jobtitle);
							NotificationRecipients.setEmailTo(EmailTo);
							NotificationRecipients.setCondition(Condition1);
							NotificationRecipients.setCcEmail(Email);
							NotificationRecipients.setNotificationID(notificationID);
							session.saveOrUpdate(NotificationRecipients);
							//appConfig.persist(NotificationRecipients, NotificationRecipients.class);
							
						}
						else {
							NotificationRecipients.setID(reid);
							Timestamp creationDate2;
							String CreatedDate = request.getParameter("creationDate");
							date1 = formatter1.parse(CreatedDate);
							creationDate2 = new Timestamp(date1.getTime());
							
							Timestamp CreateDateassign = new Timestamp(date1.getTime());
							NotificationRecipients.setJobTitle(jobtitle);
							NotificationRecipients.setEmailTo(EmailTo);
							NotificationRecipients.setCondition(Condition1);
							NotificationRecipients.setCcEmail(Email);
							NotificationRecipients.setNotificationID(notificationID);
							session.saveOrUpdate(NotificationRecipients);
							//appConfig.persist(NotificationRecipients, NotificationRecipients.class);
						}

					}
								
				}//end of checking if dictParameter!=null
				
				
				
		          //////////////                          End of RECIPIENTS BOQ table               //////////////////////
			
			
				rtn.put("Test", "SaveDone");

				rtn.put("id", noid);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in NotificationListView due to \n "+ exceptionAsString);
				logger.info("Error in NotificationListView due to \n "+ exceptionAsString);
				model.addAttribute("ListGridTable", "");
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
