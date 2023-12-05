package com.aliat.alm.controller;

import java.util.Locale;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SystemSettingsController {
	private Session session = null;
	
	@Autowired
	Notify notification;
	@RequestMapping(value = "/systemSettings", method = RequestMethod.GET)
	public String antennaDetails(Locale locale, Model model) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			try {
			
			notification.headerNotifications(session, model);
	     
			}catch(Exception e){
				System.out.println("Error in rolePermission"+e.toString());
			}
			finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}

		}
				
	return "systemSettings";
	}
}