package com.aliat.alm.controller;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
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
import org.springframework.web.bind.annotation.RestController;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Permissions;

@RestController
@Controller
public class HeaderController {
	private static final Logger logger = LoggerFactory.getLogger(HeaderController.class);
	private int totalCount;
	Session session = null;
	Transaction tx = null;
	@Autowired
	Permissions permissions;
	@Autowired
	ALMSessions almsessions;
	
	
	@RequestMapping(value = "/headerController", method = RequestMethod.GET)
	public String headerController( Model model) {
		// getting Counts
	/*
		Configuration cfg = new Configuration().configure();
	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
	SessionFactory sf = cfg.buildSessionFactory(builder.build());
	Session session = sf.openSession();
	Transaction tx = session.beginTransaction();
	*/

	Query q = null; String query = "";
	
	try {
		session = almsessions.getSession();
		tx = session.beginTransaction();
		
	}
		catch (Exception e) {
			logger.info("Error in opening DBConnection : "+e);
			return "header";
		}
		
		try {
//		 Purchase Request
		query ="select Count (*) from PurchaseRequest ";
		q = session.createQuery(query);
		model.addAttribute("PrAll", q.uniqueResult().toString());
		query ="select Count (*) from PurchaseRequest where prStatus != 'completed' and prStatus != 'closed' and prStatus != 'deactivated' and prStatus != 'cancelled' and prStatus != 'activated' or prStatus is null ";
		q = session.createQuery(query);
		this.totalCount=Integer.parseInt(q.uniqueResult().toString());
		model.addAttribute("PrUnCmp", q.uniqueResult().toString());

		// Purchase Order
		query ="select Count (*) from PurchaseOrder ";
		q = session.createQuery(query);
		model.addAttribute("PoAll", q.uniqueResult().toString());
		query ="select Count (*) from PurchaseOrder where ordStatus != 'completed' and ordStatus != 'closed' and ordStatus != 'deactivated' and ordStatus != 'cancelled' and ordStatus != 'activated' or ordStatus is null ";
		q = session.createQuery(query);
		this.totalCount+=Integer.parseInt(q.uniqueResult().toString());
		model.addAttribute("PoUnCmp", q.uniqueResult().toString());
		
		//Goods Received
		query ="select Count (*) from GoodsReceived ";
		q = session.createQuery(query);
		model.addAttribute("GrAll", q.uniqueResult().toString());
	    query ="select Count (*) from GoodsReceived where grStatus != 'completed' and grStatus != 'closed' and grStatus != 'deactivated' and grStatus != 'cancelled' and grStatus != 'activated' or grStatus is null ";
	    q = session.createQuery(query);
	    this.totalCount+=Integer.parseInt(q.uniqueResult().toString());
	    model.addAttribute("GrUnCmp", q.uniqueResult().toString());
		
	    //Work Order
		query ="select Count (*) from WorkOrder ";
		q = session.createQuery(query);
		model.addAttribute("WoAll", q.uniqueResult().toString());
	    query ="select Count (*) from WorkOrder where woStatus != 'completed' and woStatus != 'closed' and woStatus != 'deactivated' and woStatus != 'cancelled' and woStatus != 'activated' or woStatus is null ";
	    q = session.createQuery(query);
	    this.totalCount+=Integer.parseInt(q.uniqueResult().toString());
	    model.addAttribute("WoUnCmp", q.uniqueResult().toString());

		//Discovery New
		query ="select Count (*) from DiscoveryNew ";
		q = session.createQuery(query);
		model.addAttribute("DniAll", q.uniqueResult().toString());
	    query ="select Count (*) from DiscoveryNew where dnStatus != 'completed' and dnStatus != 'closed' and dnStatus != 'deactivated' and dnStatus != 'cancelled' and dnStatus != 'activated' or dnStatus is null ";
	    q = session.createQuery(query);
	    this.totalCount+=Integer.parseInt(q.uniqueResult().toString());
	    model.addAttribute("DniUnCmp", q.uniqueResult().toString());

		//Warehouse
		query ="select Count (*) from Warehouse ";
		q = session.createQuery(query);
		 model.addAttribute("WhAll", q.uniqueResult().toString());
	    query ="select Count (*) from Warehouse where whStatus != 'completed' and whStatus != 'closed' and whStatus != 'deactivated' and whStatus != 'cancelled' and whStatus != 'activated' or whStatus is null ";
	    q = session.createQuery(query);
	    this.totalCount+=Integer.parseInt(q.uniqueResult().toString());
	    model.addAttribute("WhUnCmp", q.uniqueResult().toString());

		//TroubleTickets
		query ="select Count (*) from TroubleTickets ";
		q = session.createQuery(query);
		model.addAttribute("TkAll", q.uniqueResult().toString());
	    query ="select Count (*) from TroubleTickets where tkstatus != 'completed' and tkstatus != 'closed' and tkstatus != 'deactivated' and tkstatus != 'cancelled' and tkstatus != 'activated' or tkstatus is null ";
	    q = session.createQuery(query);
	    this.totalCount+=Integer.parseInt(q.uniqueResult().toString());
	    model.addAttribute("TkUnCmp", q.uniqueResult().toString());

		//Shops
		query ="select Count (*) from Shops";
		q = session.createQuery(query);
		 model.addAttribute("ShAll", q.uniqueResult().toString());
	    query ="select Count (*) from Shops where shStatus != 'completed' and shStatus != 'closed' and shStatus != 'deactivated' and shStatus != 'cancelled' and shStatus != 'activated' or shStatus is null ";
	    q = session.createQuery(query);
	    this.totalCount+=Integer.parseInt(q.uniqueResult().toString());
	    model.addAttribute("ShUnCmp", q.uniqueResult().toString());

		//Agent
		query ="select Count (*) from Agent ";
		q = session.createQuery(query);
		model.addAttribute("AgnAll", q.uniqueResult().toString());
	    query ="select Count (*) from Agent where agnStatus != 'completed' and agnStatus != 'closed' and agnStatus != 'deactivated' and agnStatus != 'cancelled' and agnStatus != 'activated' or agnStatus is null ";
	    q = session.createQuery(query);
	    this.totalCount+=Integer.parseInt(q.uniqueResult().toString());
	    model.addAttribute("AgnUnCmp", q.uniqueResult().toString());

		//Clients
		query ="select Count (*) from Clients ";
		q = session.createQuery(query);
		model.addAttribute("ClnAll", q.uniqueResult().toString());
	    query ="select Count (*) from Clients where clnStatus = 'inprog' or clnStatus is null ";
	    q = session.createQuery(query);
	    this.totalCount+=Integer.parseInt(q.uniqueResult().toString());
	    model.addAttribute("ClnUnCmp", q.uniqueResult().toString());

		//Area
		query ="select Count (*) from Area ";
		q = session.createQuery(query);
		model.addAttribute("ArAll", q.uniqueResult().toString());
	    query ="select Count (*) from Area where arStatus != 'completed' and arStatus != 'closed' and arStatus != 'deactivated' and arStatus != 'cancelled' and arStatus != 'activated' or arStatus is null ";
	    q = session.createQuery(query);
	    this.totalCount+=Integer.parseInt(q.uniqueResult().toString());
	    model.addAttribute("ArUnCmp", q.uniqueResult().toString());
		
		//Cluster
		query ="select Count (*) from Cluster ";
		q = session.createQuery(query);
		model.addAttribute("ClsAll", q.uniqueResult().toString());
	    query ="select Count (*) from Cluster where clsStatus != 'completed' and clsStatus != 'closed' and clsStatus != 'deactivated' and clsStatus != 'cancelled' and clsStatus != 'activated' or clsStatus is null ";
	    q = session.createQuery(query);
	    this.totalCount+=Integer.parseInt(q.uniqueResult().toString());
	    model.addAttribute("ClsUnCmp", q.uniqueResult().toString());

		//Region
		query ="select Count (*) from Region ";
		q = session.createQuery(query);
		model.addAttribute("RgnAll", q.uniqueResult().toString());
		query ="select Count (*) from Region where rgnStatus != 'completed' and rgnStatus != 'closed' and rgnStatus != 'deactivated' and rgnStatus != 'cancelled' and rgnStatus != 'activated' or rgnStatus is null ";
		q = session.createQuery(query);
		this.totalCount+=Integer.parseInt(q.uniqueResult().toString());
		model.addAttribute("RgnUnCmp", q.uniqueResult().toString());

		//Total uncompleted
		 model.addAttribute("notnum", this.totalCount);
	} catch (Exception e) {
	logger.info("Error while getting records @ query: " + query , e);
	}
	finally {
	if(session != null && session.isOpen()) {
		tx.commit();
	session.close();
	}
		}
	
	return "header";

		}
	
	

	@RequestMapping(value = "/PhysicalPerm", method = RequestMethod.GET)
	public Map<String, Object> physicalPerm(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
	
		String readTree=null;
	    try {
	        session = AlmDbSession.getInstance().getSession();

	        // Set permissions and other attributes in the model
	        permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
	                "Physical Layer", "Tree");
	         readTree = ((Integer) model.asMap().get("readTree")).toString();
	         rtn.put("readTree", readTree);
	    } catch (Exception e) {
	        logger.error("Error at permissions with error message: " + e.getMessage());
	    } finally {
	        if (session != null && session.isOpen()) {
	            session.close();
	        }
	    }
	    return rtn;
	}
}

	



