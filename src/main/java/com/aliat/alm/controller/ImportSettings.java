package com.aliat.alm.controller;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
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
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.models.AreaFinance;
import com.aliat.alm.models.DiscoveryNewBoq;
import com.aliat.alm.models.GoodsReceived;	
import com.aliat.alm.models.Item;

import com.aliat.alm.models.ItemBarcode;										
import com.aliat.alm.models.ItemCategory;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ImportSettings {

	
	@RequestMapping(value = "/importSettingsForm", method = RequestMethod.GET)
	@ResponseBody
	public String importSettingsForm(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) 
			throws JsonGenerationException, JsonMappingException, IOException {
				
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		
		Configuration cfg2 = new Configuration().configure();
		StandardServiceRegistryBuilder builder2 = new StandardServiceRegistryBuilder()
				.applySettings(cfg2.getProperties());
		SessionFactory sf2 = cfg2.buildSessionFactory(builder2.build());
		Session session2 = sf2.openSession();
		Transaction tx2 = session2.beginTransaction();
		
		List<Object> list = session2.createSQLQuery("select table_name from user_tables").list();                              

		for(Object l : list){
		     System.out.println("" +l.toString());
		}
		
		tx2.commit();
		session2.close();
		
		return "importSettings";
	
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
}
