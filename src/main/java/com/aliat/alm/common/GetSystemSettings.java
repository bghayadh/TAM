package com.aliat.alm.common;

import java.io.StringWriter;
import java.util.HashMap;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.aliat.alm.models.SystemSettings;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GetSystemSettings {
	
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	private static final Logger logger = LoggerFactory.getLogger(GetSystemSettings.class);
	private SystemSettings systemSettings;
		
	public void getLongLat(Session session, Model model) {		
		try {
			systemSettings = session.createQuery("from SystemSettings", SystemSettings.class).getSingleResult();
			System.out.println("From GetSystemSettings, the default long is " +systemSettings.getLongitude() + " and the default lat is " +systemSettings.getLat());
			model.addAttribute("systemLong", systemSettings.getLongitude().toString());
			model.addAttribute("systemLat", systemSettings.getLat().toString());
		}
		catch (Exception e){
			logger.info("Error in opening the purchase request view with a message of :", e.getMessage());
			model.addAttribute("systemLong", "");
			model.addAttribute("systemLat", "");
			e.printStackTrace();
		}
	}
}
