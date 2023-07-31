package com.aliat.alm.controller;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class JavaMailUtil {
/*public static void SendMail(String recipient,String ccEmail,String subject ,String altered_ID,String jobTitle,String messageText) throws Exception {
	System.out.println("Preparing to send Email");
	Properties properties = new Properties();
	properties.put("mail.transport.protocol","smtp");
	properties.put("mail.smtp.auth","true");
	properties.put("mail.smtp.starttls.enable","true");
	properties.put("mail.smtp.host","smtp.gmail.com");
	properties.put("mail.smtp.port","587");
	System.out.println("Passed properties");
	String myAccountEmail= "almansaah.alm2021@gmail.com";//our default email
	String password= "66th@Loop";//default password
	Session session = Session.getInstance(properties, new Authenticator() {
		
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return   new PasswordAuthentication(myAccountEmail,password);
		}
	});
	
	Message message =prepareMessageCC(session,myAccountEmail,recipient,ccEmail,subject,messageText);
	Transport.send(message);
	System.out.println("Email message sent successfully");
}*/ //temporary stopped cause we want to send mail with different accounts

public static void SendccEmails(String email, String passwrd,String recipient,String ccEmail,String subject ,String messageText) throws Exception {
	System.out.println("Preparing to send Email");
	Properties properties = new Properties();
	properties.put("mail.transport.protocol","smtp");
	properties.put("mail.smtp.auth","true");
	properties.put("mail.smtp.starttls.enable","true");
	properties.put("mail.smtp.host","smtp.gmail.com");
	properties.put("mail.smtp.port","587");
	System.out.println("Passed properties");
	String myAccountEmail= email;//email from DB
	String password= passwrd;// password from DB
	Session session = Session.getInstance(properties, new Authenticator() {
		
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return   new PasswordAuthentication(myAccountEmail,password);
		}
	});
	
	Message message =prepareMessageCC(session,myAccountEmail,recipient,ccEmail,subject,messageText);
	Transport.send(message);
	System.out.println("Email message sent successfully");
}





public static void SendEmails(String email, String passwrd,String recipient,String subject ,String messageText) throws Exception {
	System.out.println("Preparing SendEmails");
	Properties properties = new Properties();
	properties.put("mail.transport.protocol","smtp");
	properties.put("mail.smtp.auth","true");
	properties.put("mail.smtp.starttls.enable","true");
	properties.put("mail.smtp.host","smtp.gmail.com");
	properties.put("mail.smtp.port","587");
	System.out.println("Passed properties");
	String myAccountEmail= email;//email from DB
	String password= passwrd;// password from DB
	Session session = Session.getInstance(properties, new Authenticator() {
		
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return   new PasswordAuthentication(myAccountEmail,password);
		}
	});
	
	Message message =prepareMessage(session,myAccountEmail,recipient,subject,messageText);
	Transport.send(message);
	System.out.println("Email message sent successfully");
}


public static void SendccMails(String email, String passwrd,String recipient,String ccEmail,String subject ,String jobTitle, String messageText) throws Exception {
	System.out.println("Preparing to send Email");
	Properties properties = new Properties();
	properties.put("mail.transport.protocol","smtp");
	properties.put("mail.smtp.auth","true");
	properties.put("mail.smtp.starttls.enable","true");
	properties.put("mail.smtp.host","smtp.gmail.com");
	properties.put("mail.smtp.port","587");
	System.out.println("Passed properties");
	String myAccountEmail= email;//email from DB
	String password= passwrd;// password from DB
	Session session = Session.getInstance(properties, new Authenticator() {
		
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return   new PasswordAuthentication(myAccountEmail,password);
		}
	});
	
	Message message =mailMsgCC(session,myAccountEmail,recipient,ccEmail,subject,jobTitle,messageText);
	Transport.send(message);
	System.out.println("Email message sent successfully");
}


private static Message prepareMessage(Session session, String myAccountEmail,String recipient,String subject,String messageText) {
	try {
		
		Message message= new MimeMessage(session);
	
	message.setFrom(new InternetAddress(myAccountEmail));
	message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	
	message.setSubject(subject);
	message.setText(messageText);
	return message;
	}
	catch(Exception ex) {
		
		Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE,null,ex);
	}
	return null;
}

private static Message prepareMessageCC(Session session, String myAccountEmail,String recipient,String ccEmail,String subject,String messageText) {
	try {
		
		Message message= new MimeMessage(session);
	
	message.setFrom(new InternetAddress(myAccountEmail));
	message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	message.addRecipient(Message.RecipientType.CC, new InternetAddress(ccEmail));
	message.setSubject(subject);
	message.setText(messageText);
	return message;
	}
	catch(Exception ex) {
		
		Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE,null,ex);
	}
	return null;
}

private static Message mailMsgCC(Session session, String myAccountEmail,String recipient,String ccEmail,String subject,String jobTitle, String messageText) {
	try {
		
		Message message= new MimeMessage(session);
	
	message.setFrom(new InternetAddress(myAccountEmail));
	message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	message.addRecipient(Message.RecipientType.CC, new InternetAddress(ccEmail));
	message.setSubject(subject);
	message.setText("Dear "+jobTitle+"\n"+messageText);
	return message;
	}
	catch(Exception ex) {
		
		Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE,null,ex);
	}
	return null;
}
}
