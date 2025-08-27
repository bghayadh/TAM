package com.aliat.alm.controller;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DepreciationController {

	@Autowired
	ALMSessions almsessions;
	@Autowired
	Notify notifications;
	private static final Logger logger = LoggerFactory.getLogger(DepreciationController.class);
	private static Session session = null;
	private static Transaction tx = null;	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/CalculateDepreciation", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CalculateDepreciation(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		List<FixedAssetRegistry> FAR = new ArrayList<FixedAssetRegistry>();
		
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		
		Query q = session.createQuery("SELECT farID, farcreatedDate, iniCost, usefulLifeMon FROM FixedAssetRegistry");
		FAR = q.list();
		
		Iterator itr = FAR.iterator();
		while(itr.hasNext()){
			   Object[] obj = (Object[]) itr.next();
			   
			   String farID = String.valueOf(obj[0]);
			   String farcreatedDate = String.valueOf(obj[1]); 
			   Float iniCost = Float.parseFloat(String.valueOf(obj[2]));
			   Float usefulLifeMon = Float.parseFloat(String.valueOf(obj[3]));
			  
			   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
			   TemporalAccessor temporalAccessor = formatter.parse(farcreatedDate);
			   LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
			   ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
			   Instant lastDate = Instant.from(zonedDateTime);
			   
			   
			   
			   
			   long diffInSeconds = Duration.between(lastDate,
                       Instant.now()).getSeconds();
			   
			   long diffInDays = diffInSeconds / 86400;
			   
			   Float usefulLifeDays = usefulLifeMon * 30;
			   
			   Float dailyDepreciation = iniCost / usefulLifeDays;
			   
			   Float accumDepreciation = diffInDays * dailyDepreciation;
			   
			   Float netCost = iniCost - accumDepreciation;
			   
			   Float depPercentage = accumDepreciation / 100;
			   
			   Float monthlyDepreciation = dailyDepreciation * 30;
			   
			   // Update Depreciation In Fixed Asset Registry
			   Configuration cfg2 = new Configuration().configure();
			   StandardServiceRegistryBuilder builder2 = new StandardServiceRegistryBuilder().applySettings(cfg2.getProperties());
			   SessionFactory sf2 = cfg2.buildSessionFactory(builder2.build());
			   Session session2 = sf2.openSession();
			   Transaction tx2 = session2.beginTransaction();
				
			   Query q2 = session2.createQuery("UPDATE FixedAssetRegistry SET netCost =:p_netCost, deprPer =:p_deprPer,"
			   		+ "dailyPercent =:p_dailyPercent, accumPer =:p_accumPer WHERE farID =:p_farID");
			   
			   q2.setFloat("p_netCost", netCost);
			   q2.setFloat("p_deprPer", depPercentage);
			   q2.setFloat("p_dailyPercent", dailyDepreciation);
			   q2.setFloat("p_accumPer", depPercentage);
			   q2.setParameter("p_farID", farID);
			   q2.executeUpdate();

			   tx2.commit();
			   session2.close();
			   
			}

		
		tx.commit();
		session.close();

		ObjectMapper mapper = new ObjectMapper();
//		model.addAttribute("ListItemCategory", mapper.writeValueAsString(listItem));
//		System.out.println("end good " + mapper.writeValueAsString(listItem));
		rtn.put("FAR", FAR);

		return rtn;
	}

}
