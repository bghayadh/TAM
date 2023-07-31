package com.aliat.alm.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class NodeActiveController {
	
	@Autowired
	ALMSessions almsessions;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static Session session = null;
	private static Transaction tx = null;
	private static Query query = null;
	private static ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping(value = "/getAllNodes", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPartNo(HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
		
			try
			{
		
				String requestValue = request.getParameter("requestValue");
				
				String str ="select distinct nodeID, nodeName "
				
						+"from NodeActive where LOWER(nodeID) like LOWER(:param1) OR LOWER(nodeName) like LOWER(:param1) ";
		
				
				query = session.createQuery(str);
				query.setString("param1", "%"+requestValue+"%");
				
				
				query.setFirstResult(0);
				query.setMaxResults(40);
		
				rtn.put("ListNodes", query.list());
			}catch(Exception e){
				logger.info(
						"Error while getting Nodes using autocomplete with error message: "+ e);
				e.printStackTrace();
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
