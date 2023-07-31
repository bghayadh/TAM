package com.aliat.alm.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.aliat.alm.models.EmailAccounts;
import com.aliat.alm.models.GoodsReceived;
import com.aliat.alm.models.Notification;
import com.aliat.alm.models.NotificationRecipients;
import com.aliat.alm.models.PurchaseOrder;
import com.aliat.alm.models.PurchaseRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmailCondition extends Thread {
	String nID = null,password=null,Message=null,sender=null,EmailTo = null,ccEmail = null,subject=null,notificationName=null,sendAlerton, jobTitle,enabled,condition,nName,ModuleName;
	String modulename;
	String FormSave;
	String moduleID;
	 
	int conditionChecked;
public EmailCondition(String modulename,String FormSave,String moduleID)  {
	this.modulename= modulename;
	this.FormSave= FormSave;
	this.moduleID=moduleID;
	 
}
		
		// TODO Auto-generated method stub
		
public void run(){ 

		Configuration cfg = new Configuration().configure();
    	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
    	
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
    	Session session = sf.openSession();
    	 
    	session.beginTransaction();
    	try {
    	Criteria criteria=session.createCriteria(Notification.class);
    	criteria.add(Restrictions.eq("moduleName", modulename));
    	criteria.add(Restrictions.eq("sendAlertOn",FormSave));//to search database for specific inputs
    	
     	List<Notification> user= (List<Notification>)criteria.list();//here we'll have all the records that have moduleName and sendAlertOn equal to those we entered
     	
     				System.out.println("Value of moduleID  =  "+moduleID);
   
   	 for(Notification u: user)
	 {  
		 nID=u.getNotificationID();
		 nName=u.getNotificationName();
		 Message=u.getMessage();
		 sender=u.getSender();
		 subject=u.getSubject();
		 
		 Criteria criteria3=session.createCriteria(EmailAccounts.class);
	    	criteria3.add(Restrictions.eq("email", sender));
	    	List<EmailAccounts> senders= (List<EmailAccounts>)criteria3.list();
	    	for(EmailAccounts x: senders)
	    	{
	    		password=x.getPassword();//to get the password of sender
	    			System.out.println("password value  = "+password);
	    	}
		 
	    			System.out.println("password value  after= "+password);
		 			System.out.println("nID val ="+nID);
		 	notificationName= u.getNotificationName();
		 			System.out.println("Finish of first table data list");
		 	enabled=u.getEnabled();
		 			System.out.println("Value of enabled = "+enabled);
   	   
   	     	condition=u.getCondition();
   	     			System.out.println("Value of condition "+condition);
   	     			System.out.println("Value of  ModuleName  =  "+modulename);
   	     			
   	     			
 			Query query=session.createQuery("from "+modulename+" t where t."+condition+" and t.ID='"+moduleID+"'");//break
 					System.out.println("Value of query.list PR  =  "+query.list());

 		
 					
 					
		if (StringUtils.equalsAnyIgnoreCase(modulename, "PurchaseRequest")) {
   	    	List<PurchaseRequest>  PR= (List<PurchaseRequest>)query.list();
   	    		if(PR.size()>0) {
		 		conditionChecked=1;
		 			System.out.println("Value of conditionChecked in PR =  "+conditionChecked);
		 	}
   	     } 
   	     else if (StringUtils.equalsAnyIgnoreCase(modulename, "PurchaseOrder")) {
 	    	List<PurchaseOrder>  PO= (List<PurchaseOrder>)query.list();
 	    		if(PO.size()>0) {
		 		conditionChecked=1;
		 			System.out.println("Value of conditionChecked in PO  =  "+conditionChecked);
		 	}
 	     } 
   	     else if (StringUtils.equalsAnyIgnoreCase(modulename, "GoodsReceived")) {
	    	List<GoodsReceived>  GR= (List<GoodsReceived>)query.list();
	    		if(GR.size()>0) {
		 		conditionChecked=1;
		 			System.out.println("Value of conditionChecked in GR  =  "+conditionChecked);
		 	}
	     } 
   	     else {

   	    	 		System.out.println("Value of  ModuleName  in else loop =  "+modulename);
   	     }
   	     
		 			System.out.println("Value of conditionChecked  =  "+conditionChecked);
		 	
		 	 if(Integer.parseInt(enabled)==1 && conditionChecked==1) {
		 		 Criteria criteria2=session.createCriteria(NotificationRecipients.class);
		 		 criteria2.add(Restrictions.eq("notificationID", nID));
		 		 List<NotificationRecipients> user2= (List<NotificationRecipients>)criteria2.list();
	    	
		 		 for(NotificationRecipients u2: user2) {
	    		 jobTitle=u2.getJobTitle();
	    		 EmailTo=u2.getEmailTo();
	    		 ccEmail=u2.getCcEmail();
	    		 
		    		 System.out.println(EmailTo);
		    	 	 System.out.println(u2.getNotificationID());
		    		 System.out.println("Finish of second table data list");
		    	 
	    		 try {
	    			 
	    			JavaMailUtil.SendccMails(sender,password, EmailTo,ccEmail,subject,jobTitle,Message);
	    				System.out.println("it passed successfully");
	 			
	    		 } catch (Exception e) {
	 				 
	 				e.printStackTrace();
	 			}
	    	 }
	     
	 	 }
   	 }
    	}catch (Exception e) {
 		
					System.out.println("Error in Data Saving");
   		 
 	} finally {
 		
		session.getTransaction().commit();
		session.close();
		
 	}
		 	 
		 
	 
	}

}
