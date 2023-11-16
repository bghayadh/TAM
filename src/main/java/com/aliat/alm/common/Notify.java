package com.aliat.alm.common;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
public class Notify {

	
	private long totalCount;
	@RequestMapping(value = "/headerNotifications", method = RequestMethod.GET)
	public void headerNotifications(Session session, Model model) {
		// getting Counts

		
	Query q = null; String query = "";
	JSONObject noteCounts = new JSONObject();
	JSONParser jsonParser = new JSONParser();

	try {	

		query = "select json_object('PrAll' value (select Count (*) from PURCHASE_Request),'PrUnCmp' value (select Count (*) from PURCHASE_Request where  " + 
				"Status != 'completed' and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null), " + 
				"'PoAll' value (select Count (*) from PURCHASE_ORDER),'PoUnCmp' value (select Count (*) from PURCHASE_ORDER where Status != 'completed' and  " + 
				"Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null),'GrAll' value (select Count (*) from GOODS_RECEIVED), " + 
				"'GrUnCmp' value (select Count (*) from GOODS_RECEIVED where Status != 'completed' and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled'  " + 
				"and Status != 'activated' or Status is null),'WoAll' value (select Count (*) from WORK_ORDER),'WoUnCmp' value (select Count (*) from WORK_ORDER where Status != 'completed'  " + 
				"and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null), 'DniAll' value (select Count (*) from DISCOVERY_NEW), " + 
				"'DniUnCmp' value (select Count (*) from DISCOVERY_NEW where Status != 'completed' and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled'  " + 
				"and Status != 'activated' or Status is null), 'WhAll' value (select Count (*) from WAREHOUSE), 'WhUnCmp' value (select Count (*) from WAREHOUSE where Status != 'completed'  " + 
				"and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null), 'TkAll' value (select Count (*) from TROUBLE_TICKETS), " + 
				"'TkUnCmp' value (select Count (*) from TROUBLE_TICKETS where Status != 'completed' and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled'  " + 
				"and Status != 'activated' or Status is null), 'ShAll' value (select Count (*) from SHOPS), 'ShUnCmp' value (select Count (*) from SHOPS where Status != 'completed'  " + 
				"and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null), 'AgnAll' value (select Count (*) from AGENT), " + 
				"'AgnUnCmp' value (select Count (*) from AGENT where Status != 'Completed' and Status != 'Closed' and Status != 'Deactivated' and Status != 'Cancelled' and Status != 'Activated' " + 
				"or Status is null), 'ClnAll' value (select Count (*) from CLIENTS),'ClnUnCmp' value(select Count (*) from CLIENTS where STATUS = 'Draft' or STATUS is null), 'ArAll' value (select Count (*) from AREA), 'ArUnCmp' value (select Count (*) from AREA  " + 
				"where Status != 'completed' and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null), " + 
				"'ClsAll' value (select Count (*) from CLUSTERS), 'ClsUnCmp' value (select Count (*) from CLUSTERS where Status != 'completed' and Status != 'closed' and Status != 'deactivated' " + 
				"and Status != 'cancelled' and Status != 'activated' or Status is null), 'RgnAll' value (select Count (*) from REGION), 'RgnUnCmp' value (select Count (*) from REGION  " + 
				"where Status != 'completed' and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null)"
				+ ", 'ClnimgAll' value (select Count(*) from CLIENTS where REGISTRATION_STATUS ='Success'), 'ClnimgUnCmp' value (Select Count(*) from CLIENTS where REGISTRATION_STATUS ='Success' and PHOTOS_APPROVAL_STATUS != 'Approved'),"
				+ " 'CustAll' value (Select Count(*) from CUSTOMER), 'CustUnCmp' value (Select Count(*) from CUSTOMER where STATUS !='Activated' or STATUS is null)) from dual" ;
			
			q = session.createNativeQuery(query);
			noteCounts =  (JSONObject) jsonParser.parse(q.getSingleResult().toString()); //one row of JSONObject key-values.
			model.addAttribute("PrAll", noteCounts.get("PrAll").toString());
			model.addAttribute("PrUnCmp", noteCounts.get("PrUnCmp").toString());
			model.addAttribute("PoAll", noteCounts.get("PoAll").toString());
			model.addAttribute("PoUnCmp", noteCounts.get("PoUnCmp").toString());
			model.addAttribute("GrAll", noteCounts.get("GrAll").toString());
			model.addAttribute("GrUnCmp", noteCounts.get("GrUnCmp").toString());
			model.addAttribute("WoAll", noteCounts.get("WoAll").toString());
			model.addAttribute("WoUnCmp", noteCounts.get("WoUnCmp").toString());
			model.addAttribute("DniAll", noteCounts.get("DniAll").toString());
			model.addAttribute("DniUnCmp", noteCounts.get("DniUnCmp").toString());
			model.addAttribute("WhAll", noteCounts.get("WhAll").toString());
			model.addAttribute("WhUnCmp", noteCounts.get("WhUnCmp").toString());
			model.addAttribute("TkAll", noteCounts.get("TkAll").toString());
			model.addAttribute("TkUnCmp", noteCounts.get("TkUnCmp").toString());
			model.addAttribute("ShAll", noteCounts.get("ShAll").toString());
			model.addAttribute("ShUnCmp", noteCounts.get("ShUnCmp").toString());
			model.addAttribute("AgnAll", noteCounts.get("AgnAll").toString());
			model.addAttribute("AgnUnCmp", noteCounts.get("AgnUnCmp").toString());
			model.addAttribute("ClnAll", noteCounts.get("ClnAll").toString());
			model.addAttribute("ClnUnCmp", noteCounts.get("ClnUnCmp").toString());
			model.addAttribute("ArAll", noteCounts.get("ArAll").toString());
			model.addAttribute("ArUnCmp", noteCounts.get("ArUnCmp").toString());
			model.addAttribute("ClsAll", noteCounts.get("ClsAll").toString());
			model.addAttribute("ClsUnCmp", noteCounts.get("ClsUnCmp").toString());
			model.addAttribute("RgnAll", noteCounts.get("RgnAll").toString());
			model.addAttribute("RgnUnCmp", noteCounts.get("RgnUnCmp").toString());
			model.addAttribute("ClnimgAll", noteCounts.get("ClnimgAll").toString());
			model.addAttribute("ClnimgUnCmp", noteCounts.get("ClnimgUnCmp").toString());
			model.addAttribute("CustAll", noteCounts.get("CustAll").toString());
			model.addAttribute("CustUnCmp", noteCounts.get("CustUnCmp").toString());
			
			this.totalCount = 	 (long)noteCounts.get("PrUnCmp")+(long)noteCounts.get("PoUnCmp")+(long) noteCounts.get("GrUnCmp")+(long) noteCounts.get("WoUnCmp")+
								+(long) noteCounts.get("DniUnCmp")+(long) noteCounts.get("WhUnCmp")+ (long) noteCounts.get("TkUnCmp")+(long) noteCounts.get("ShUnCmp")+(long) noteCounts.get("AgnUnCmp")+(long) noteCounts.get("ClnUnCmp")+(long) noteCounts.get("ArUnCmp")+(long) noteCounts.get("ClsUnCmp")+(long) noteCounts.get("RgnUnCmp")+(long) noteCounts.get("CustUnCmp");
			model.addAttribute("notnum", this.totalCount);

	} catch (Exception e) {
		
		System.out.println("Error in AppConfig class at headerNotification method while getting records @ query: " + query);
		model.addAttribute("PrAll", "");
		model.addAttribute("PrUnCmp", "");
		model.addAttribute("PoAll", "");
		model.addAttribute("PoUnCmp", "");
		model.addAttribute("GrAll", "");
		model.addAttribute("GrUnCmp", "");
		model.addAttribute("WoAll", "");
		model.addAttribute("WoUnCmp", "");
		model.addAttribute("DniAll", "");
		model.addAttribute("DniUnCmp", "");
		model.addAttribute("WhAll", "");
		model.addAttribute("WhUnCmp", "");
		model.addAttribute("TkAll", "");
		model.addAttribute("TkUnCmp", "");
		model.addAttribute("ShAll", "");
		model.addAttribute("ShUnCmp", "");
		model.addAttribute("AgnAll", "");
		model.addAttribute("AgnUnCmp", "");
		model.addAttribute("ClnAll", "");
		model.addAttribute("ClnUnCmp", "");
		model.addAttribute("ArAll", "");
		model.addAttribute("ArUnCmp", "");
		model.addAttribute("ClsAll", "");
		model.addAttribute("ClsUnCmp", "");
		model.addAttribute("RgnAll", "");
		model.addAttribute("RgnUnCmp", "");
		model.addAttribute("ClnimgAll", "");
		model.addAttribute("ClnimgUnCmp", "");
		model.addAttribute("CustAll", "");
		model.addAttribute("CustUnCmp", "");
		model.addAttribute("notnum", this.totalCount);
		e.printStackTrace();
	}

	//return "header";

	}
	
	@RequestMapping(value = "/headerNotification", method = RequestMethod.GET)
	public void headerNotification(EntityManager entitymanager, Model model) {
		// getting Counts

		
	Query q = null; String query = "";
	JSONObject noteCounts = new JSONObject();
	JSONParser jsonParser = new JSONParser();

	try {	

		query = "select json_object('PrAll' value (select Count (*) from PURCHASE_Request),'PrUnCmp' value (select Count (*) from PURCHASE_Request where  " + 
				"Status != 'completed' and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null), " + 
				"'PoAll' value (select Count (*) from PURCHASE_ORDER),'PoUnCmp' value (select Count (*) from PURCHASE_ORDER where Status != 'completed' and  " + 
				"Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null),'GrAll' value (select Count (*) from GOODS_RECEIVED), " + 
				"'GrUnCmp' value (select Count (*) from GOODS_RECEIVED where Status != 'completed' and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled'  " + 
				"and Status != 'activated' or Status is null),'WoAll' value (select Count (*) from WORK_ORDER),'WoUnCmp' value (select Count (*) from WORK_ORDER where Status != 'completed'  " + 
				"and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null), 'DniAll' value (select Count (*) from DISCOVERY_NEW), " + 
				"'DniUnCmp' value (select Count (*) from DISCOVERY_NEW where Status != 'completed' and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled'  " + 
				"and Status != 'activated' or Status is null), 'WhAll' value (select Count (*) from WAREHOUSE), 'WhUnCmp' value (select Count (*) from WAREHOUSE where Status != 'completed'  " + 
				"and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null), 'TkAll' value (select Count (*) from TROUBLE_TICKETS), " + 
				"'TkUnCmp' value (select Count (*) from TROUBLE_TICKETS where Status != 'completed' and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled'  " + 
				"and Status != 'activated' or Status is null), 'ShAll' value (select Count (*) from SHOPS), 'ShUnCmp' value (select Count (*) from SHOPS where Status != 'completed'  " + 
				"and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null), 'AgnAll' value (select Count (*) from AGENT), " + 
				"'AgnUnCmp' value (select Count (*) from AGENT where Status != 'Completed' and Status != 'Closed' and Status != 'Deactivated' and Status != 'Cancelled' and Status != 'Activated' " + 
				"or Status is null), 'ClnAll' value (select Count (*) from CLIENTS),'ClnUnCmp' value(select Count (*) from CLIENTS where STATUS = 'Draft' or STATUS is null), 'ArAll' value (select Count (*) from AREA), 'ArUnCmp' value (select Count (*) from AREA  " + 
				"where Status != 'completed' and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null), " + 
				"'ClsAll' value (select Count (*) from CLUSTERS), 'ClsUnCmp' value (select Count (*) from CLUSTERS where Status != 'completed' and Status != 'closed' and Status != 'deactivated' " + 
				"and Status != 'cancelled' and Status != 'activated' or Status is null), 'RgnAll' value (select Count (*) from REGION), 'RgnUnCmp' value (select Count (*) from REGION  " + 
				"where Status != 'completed' and Status != 'closed' and Status != 'deactivated' and Status != 'cancelled' and Status != 'activated' or Status is null)"
				+ ", 'ClnimgAll' value (select Count(*) from CLIENTS where REGISTRATION_STATUS ='Success'), 'ClnimgUnCmp' value (Select Count(*) from CLIENTS where REGISTRATION_STATUS ='Success' and PHOTOS_APPROVAL_STATUS != 'Approved'),"
				+ " 'CustAll' value (Select Count(*) from CUSTOMER), 'CustUnCmp' value (Select Count(*) from CUSTOMER where STATUS !='Activated' or STATUS is null)) from dual" ;
			
			q = entitymanager.createNativeQuery(query);
			noteCounts =  (JSONObject) jsonParser.parse(q.getSingleResult().toString()); //one row of JSONObject key-values.
			model.addAttribute("PrAll", noteCounts.get("PrAll").toString());
			model.addAttribute("PrUnCmp", noteCounts.get("PrUnCmp").toString());
			model.addAttribute("PoAll", noteCounts.get("PoAll").toString());
			model.addAttribute("PoUnCmp", noteCounts.get("PoUnCmp").toString());
			model.addAttribute("GrAll", noteCounts.get("GrAll").toString());
			model.addAttribute("GrUnCmp", noteCounts.get("GrUnCmp").toString());
			model.addAttribute("WoAll", noteCounts.get("WoAll").toString());
			model.addAttribute("WoUnCmp", noteCounts.get("WoUnCmp").toString());
			model.addAttribute("DniAll", noteCounts.get("DniAll").toString());
			model.addAttribute("DniUnCmp", noteCounts.get("DniUnCmp").toString());
			model.addAttribute("WhAll", noteCounts.get("WhAll").toString());
			model.addAttribute("WhUnCmp", noteCounts.get("WhUnCmp").toString());
			model.addAttribute("TkAll", noteCounts.get("TkAll").toString());
			model.addAttribute("TkUnCmp", noteCounts.get("TkUnCmp").toString());
			model.addAttribute("ShAll", noteCounts.get("ShAll").toString());
			model.addAttribute("ShUnCmp", noteCounts.get("ShUnCmp").toString());
			model.addAttribute("AgnAll", noteCounts.get("AgnAll").toString());
			model.addAttribute("AgnUnCmp", noteCounts.get("AgnUnCmp").toString());
			model.addAttribute("ClnAll", noteCounts.get("ClnAll").toString());
			model.addAttribute("ClnUnCmp", noteCounts.get("ClnUnCmp").toString());
			model.addAttribute("ArAll", noteCounts.get("ArAll").toString());
			model.addAttribute("ArUnCmp", noteCounts.get("ArUnCmp").toString());
			model.addAttribute("ClsAll", noteCounts.get("ClsAll").toString());
			model.addAttribute("ClsUnCmp", noteCounts.get("ClsUnCmp").toString());
			model.addAttribute("RgnAll", noteCounts.get("RgnAll").toString());
			model.addAttribute("RgnUnCmp", noteCounts.get("RgnUnCmp").toString());
			model.addAttribute("ClnimgAll", noteCounts.get("ClnimgAll").toString());
			model.addAttribute("ClnimgUnCmp", noteCounts.get("ClnimgUnCmp").toString());
			model.addAttribute("CustAll", noteCounts.get("CustAll").toString());
			model.addAttribute("CustUnCmp", noteCounts.get("CustUnCmp").toString());
			
			this.totalCount = 	 (long)noteCounts.get("PrUnCmp")+(long)noteCounts.get("PoUnCmp")+(long) noteCounts.get("GrUnCmp")+(long) noteCounts.get("WoUnCmp")+
								+(long) noteCounts.get("DniUnCmp")+(long) noteCounts.get("WhUnCmp")+ (long) noteCounts.get("TkUnCmp")+(long) noteCounts.get("ShUnCmp")+(long) noteCounts.get("AgnUnCmp")+(long) noteCounts.get("ClnUnCmp")+(long) noteCounts.get("ArUnCmp")+(long) noteCounts.get("ClsUnCmp")+(long) noteCounts.get("RgnUnCmp")+(long) noteCounts.get("CustUnCmp");
			model.addAttribute("notnum", this.totalCount);

	} catch (Exception e) {
		
		System.out.println("Error in AppConfig class at headerNotification method while getting records @ query: " + query);
		model.addAttribute("PrAll", "");
		model.addAttribute("PrUnCmp", "");
		model.addAttribute("PoAll", "");
		model.addAttribute("PoUnCmp", "");
		model.addAttribute("GrAll", "");
		model.addAttribute("GrUnCmp", "");
		model.addAttribute("WoAll", "");
		model.addAttribute("WoUnCmp", "");
		model.addAttribute("DniAll", "");
		model.addAttribute("DniUnCmp", "");
		model.addAttribute("WhAll", "");
		model.addAttribute("WhUnCmp", "");
		model.addAttribute("TkAll", "");
		model.addAttribute("TkUnCmp", "");
		model.addAttribute("ShAll", "");
		model.addAttribute("ShUnCmp", "");
		model.addAttribute("AgnAll", "");
		model.addAttribute("AgnUnCmp", "");
		model.addAttribute("ClnAll", "");
		model.addAttribute("ClnUnCmp", "");
		model.addAttribute("ArAll", "");
		model.addAttribute("ArUnCmp", "");
		model.addAttribute("ClsAll", "");
		model.addAttribute("ClsUnCmp", "");
		model.addAttribute("RgnAll", "");
		model.addAttribute("RgnUnCmp", "");
		model.addAttribute("ClnimgAll", "");
		model.addAttribute("ClnimgUnCmp", "");
		model.addAttribute("CustAll", "");
		model.addAttribute("CustUnCmp", "");
		model.addAttribute("notnum", this.totalCount);
		e.printStackTrace();
	}

	//return "header";

	}
}
