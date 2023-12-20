package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ActionDashboard {
	
	@Autowired
	Notify notifications;
	
	private static final Logger logger = Logger.getLogger(ActionDashboard.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static StringWriter sw;
	private static String exceptionAsString;
	@SuppressWarnings("rawtypes")
	private Query query;
	
	@RequestMapping(value = "/ActionDashboard", method = RequestMethod.GET)
	public String ActionsDashboard(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		else {
			
			try {
				
				session = AlmDbSession.getInstance().getSession();
				System.out.println("HashCode Action Dashboard: "+AlmDbSession.getInstance().hashCode());
			
				if(session != null && session.isOpen()) {
					notifications.headerNotifications(session, model);
					
					String dnquery = "select sum (case when Approval <> 'Completely Approved' then 1 else 0 end) as needApproval," + 
							" sum (case when (Approval ='Operation Manager' or Approval='Project Manager') then 1 else 0 end) as PM," + 
							" sum (case when Approval='Asset Unit' then 1 else 0 end) as AUM," + 
							" sum (case when Approval='Finance' then 1 else 0 end) as FM," + 
							" count (Approval) as total " + 
							" from discovery_new_item";
					
					String prquery = "select nvl(sum(case when STATUS ='inprog' then 1 else 0 end),0) as needApproval,"
							+ "nvl(sum(case when STATUS='approved' then 1 else 0 end),0) as approved "
							+ " from Purchase_Request";
					
					String poquery = "select nvl(sum(case when STATUS ='inprog' then 1 else 0 end),0) as needApproval,"
							+ "nvl(sum(case when STATUS='approved' then 1 else 0 end),0) as approved "
							+ " from Purchase_Order";
					
					String grquery = "select nvl(sum(case when STATUS ='inprog' then 1 else 0 end),0) as needApproval,"
							+ "nvl(sum(case when STATUS='approved' then 1 else 0 end),0) as approved "
							+ " from Goods_Received";
					
					String woquery = "select nvl(sum(case when STATUS ='inprog' then 1 else 0 end),0) as needApproval,"
							+ "nvl(sum(case when STATUS='approved' then 1 else 0 end),0) as approved "
							+ " from Work_Order";
					
					model.addAttribute("dnApprovalsCount", mapper.writeValueAsString(session.createNativeQuery(dnquery).list()));
					model.addAttribute("prApprovalsCount", mapper.writeValueAsString(session.createNativeQuery(prquery).list()));
					model.addAttribute("poApprovalsCount", mapper.writeValueAsString(session.createNativeQuery(poquery).list()));
					model.addAttribute("grApprovalsCount", mapper.writeValueAsString(session.createNativeQuery(grquery).list()));
					model.addAttribute("woApprovalsCount", mapper.writeValueAsString(session.createNativeQuery(woquery).list()));

					System.out.println(mapper.writeValueAsString(session.createNativeQuery(dnquery).list()));
					System.out.println(mapper.writeValueAsString(session.createNativeQuery(prquery).list()));
					System.out.println(mapper.writeValueAsString(session.createNativeQuery(poquery).list()));
					System.out.println(mapper.writeValueAsString(session.createNativeQuery(grquery).list()));
					System.out.println(mapper.writeValueAsString(session.createNativeQuery(woquery).list()));

				}
				
			}catch(Exception e) {
				model.addAttribute("dnApprovalsCount", "addNew");
				model.addAttribute("prApprovalsCount", "addNew");
				model.addAttribute("poApprovalsCount", "addNew");
				model.addAttribute("grApprovalsCount", "addNew");
				model.addAttribute("woApprovalsCount", "addNew");
				
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in MobileDashboard due to \n "+ exceptionAsString);
				logger.info("Error in MobileDashboard due to \n "+ exceptionAsString);
			}
			finally {
				if(session.isOpen() && session != null) {
					session.close();
				}
			}
			
		}
		
		return "ActionDashboard";
	}
}
