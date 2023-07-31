package com.aliat.alm.controller;


import com.aliat.alm.models.ArNode;
import com.aliat.alm.models.ArPartNumber;
import com.aliat.alm.models.ArSerialNumber;
import com.aliat.alm.models.ArSite;
import com.aliat.alm.models.AssetLifeCycle;
import com.aliat.alm.models.AssetRegistry;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.models.GoodsReceived;
import com.aliat.alm.models.PurchaseOrder;
import com.aliat.alm.models.PurchaseOrderItem;
import com.aliat.alm.models.DiscoveryNewListView;
import com.aliat.alm.models.FarNode;
import com.aliat.alm.models.FarPartNumber;
import com.aliat.alm.models.FarSerialNumber;
import com.aliat.alm.models.FarSite;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import org.hibernate.SQLQuery;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.DiscoveryNew;
import com.aliat.alm.models.DiscoveryNewBoq;
import com.aliat.alm.models.DiscoveryNewItem;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DiscoveryController {
	
	private static final String DN_ITEM = "item";
	private static final String DN_PURCHASE_ORDER = "purchaseOrder";
	private static final String DN_WORK_ORDER= "workOrder";
	private static final String DN_QTY = "itemQty";
	private static final String DN_RATE = "itemRate";
	private static final String DN_AMOUT = "itemDAmout";
	private static final String DN_TAX = "itemTax";
	private static final String DN_ITEM_MODEL = "itemModel";
	private static final String DN_ITEM_PART_NB = "itemPartNb";
	private static final String DN_NET_RATE = "itemNetRate";
	private static final String DN_TOTAL = "itemTotal";
	private static final String DN_TOTALAT = "itemTotalAt";
	private static final String DN_SITE_ID = "siteID";
	private static final String DN_TO_SITE_ID = "tositeID";
	private static final String DN_ITEM_SN = "itemSN";
	private static final String DN_TO_SN = "toSN";
	private static final String DN_ITEM_ID = "itemDniID";
	private static final String DN_ITEM_NODE_ID = "itemNodeID";
	private static final String DN_TO_NODE_ID = "toNodeID";
	private static final String DN_ITEM_NODE_NAME = "itemNodeName";
	private static final String DN_ITEM_TO_NODE_NAME = "itemToNodeName";
	private static final String DN_FROM_SLOT = "fromSlot";
	private static final String DN_TO_SLOT = "itemToSlot";
	private static final String DN_APPROVE = "buttonApprove";
	private static final String DN_APPROVE_STATUS = "aprovStatus";
	private static final String DN_TRANS_TYPE = "transType";
	private static final String DN_NOTES = "notes";
	private static final String DN_ELEMENT_NAME = "elementName";
	private static final String DN_ADDRESS = "address";
	private static final String DESCRIPTION = "description";
	//private static final String NODE_PK = "nodePK";
	private static final String ALC_FLG = "alcflg";
	private static final String APPROVED_BY = "approvedby";
	int i;

	@Autowired
	Form form;

	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;
	
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static final Logger logger = LoggerFactory.getLogger(DiscoveryController.class);

    Calendar calendar = new GregorianCalendar();
	 int year = calendar.get(Calendar.YEAR);
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/DiscoveryNewListView", method = RequestMethod.GET)
	public String DiscoveryNewListView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		
		session = almsessions.getSession();
		
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			
			try {


				List<DiscoveryNewListView> listDiscoveryNew = new ArrayList<DiscoveryNewListView>();
				
				String str = "select DN_ID as dnID,DN_ID as dnewID,CAST((TOTAL_AMOUNT) AS VARCHAR(10)) as dnTotalAmount,CAST((TOTAL_QTY) AS VARCHAR(10)) as dnTotalQty,STATUS as dnStatus,TO_CHAR(LAST_MODIF_DATE,'YYYY-MM-DD HH24:MI:SS') as dnlastmodifDate from DISCOVERY_NEW order by LAST_MODIF_DATE DESC";
				query = session.createSQLQuery(str);
				
				listDiscoveryNew=((SQLQuery) query)
						.addScalar("dnID").addScalar("dnewID").addScalar("dnTotalAmount").addScalar("dnTotalQty").addScalar("dnStatus").addScalar("dnlastmodifDate")
		       			.setResultTransformer(Transformers.aliasToBean(DiscoveryNewListView.class)).list();
		model.addAttribute("ListGridTable", mapper.writeValueAsString(listDiscoveryNew));
		
		System.out.println(mapper.writeValueAsString(listDiscoveryNew));
		
		} catch (Exception e) {
			logger.info("Error at DiscoveryNew ListView with a message: "+ e);
			e.printStackTrace();
			model.addAttribute("ListGridTable","");
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
			
		}

		return "DiscoveryNewListView";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredDiscoveryNewListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredDiscoveryNewListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;	
		}
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {

				String startdate, enddate, status;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				status = request.getParameter("status");	
			
				List<String> listNew = new ArrayList<String>();

				String str = "select 1 as chkBox, DN_ID as dnID,  TOTAL_AMOUNT as dnTotalAmount, TOTAL_QTY as dnTotalQty,"
						+ " STATUS as dnStatus,TO_CHAR(LAST_MODIF_DATE,'YYYY-MM-DD HH24:MI:SS') as dnlastmodifDate from DISCOVERY_NEW  ";
				
				if (startdate != null && enddate != null) {
					str = str + " where CREATION_DATE between TO_DATE('" + startdate
							+ "','YYYY-MM-DD') and TO_DATE('" + enddate + "','YYYY-MM-DD')";
				}
				if (status != null && !status.equalsIgnoreCase("")) {

					str = str + " and (upper(STATUS) LIKE upper('%" + status + "%')  )";
				}
				str = str + " ORDER BY LAST_MODIF_DATE DESC ";
				Query query = session.createSQLQuery(str);
				

				listNew = query.list();
				
				rtn.put("listNew",listNew);
				System.out.println("Filtered Array: " + mapper.writeValueAsString(listNew));
			} catch (Exception e) {
				logger.info("Error in showing the filtered Discovery New list view with a message :" + e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PendingDiscoveryNewListView", method = RequestMethod.GET)
	public String PendingDiscoveryNewListView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		

		
		session = almsessions.getSession();
		
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			
			try {

		
		List<DiscoveryNew> listDiscoveryNew = new ArrayList<DiscoveryNew>();


		listDiscoveryNew = session
				.createQuery("SELECT t.dnID AS ID,t.dnID, t.dnTotalAmount,t.dnTotalQty , t.dnStatus, TO_CHAR(LAST_MODIF_DATE,'YYYY-MM-DD HH24:MI:SS') as dnlastmodifDate from DiscoveryNew t where t.dnStatus != 'completed' and t.dnStatus != 'closed' and t.dnStatus != 'deactivated' and t.dnStatus != 'cancelled' and t.dnStatus != 'activated' or t.dnStatus is null")
				.list();
		
		model.addAttribute("ListGridTable", mapper.writeValueAsString(listDiscoveryNew));
		
		} catch (Exception e) {
			logger.info("Error at DiscoveryNew ListView with a message: "+ e);
			e.printStackTrace();
			model.addAttribute("ListGridTable","");
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
			
		}


		return "DiscoveryNewListView";
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/DiscoveryNewFormView", method = RequestMethod.GET)
	public String DiscoveryNewFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
        tx = session.beginTransaction(); 
       notifications.headerNotifications(session, model); 
       
       try { 
		
		String navAction = "2";
        String result [] =new String[4];
        int SelectedIndex = 0;

		navAction= request.getParameter("NavAction");

		
		Date date;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		String dnid = request.getParameter("dnID");
		
		// to open discovery when click on ADD from discovery List
		if (StringUtils.equalsIgnoreCase(dnid, null)) {
			date = new Timestamp(System.currentTimeMillis());
			model.addAttribute("dncreationDate", formatter.format(date).toString());
			model.addAttribute("dnlastModifieddate", formatter.format(date).toString());
			model.addAttribute("ListPRqItem", "addNew");
			model.addAttribute("docStatus", "addNew");
			model.addAttribute("SelectedIndex", "addNew");
			model.addAttribute("DNCount", "addNew");

			return "DiscoveryNewFormView";
		}

		result = form.NavigationNP(session,"DISCOVERY_NEW","DN_ID",dnid,"LAST_MODIF_DATE",navAction);		

		SelectedIndex= Integer.parseInt(result[1]);
		dnid=result[2];

		model.addAttribute("SelectedIndex", SelectedIndex);
		model.addAttribute("DNCount", Integer.parseInt(result[0]));

		DiscoveryNew discoverynew = (DiscoveryNew) session.get(DiscoveryNew.class, dnid);

		if (discoverynew != null) {
			model.addAttribute("dnID", discoverynew.getDnID());

			if (StringUtils.equalsIgnoreCase(discoverynew.getDncreationDate().toString(), null)) {
				date = new Timestamp(System.currentTimeMillis());
				model.addAttribute("dncreationDate", formatter.format(date).toString());
			} else {
				date = discoverynew.getDncreationDate();
				model.addAttribute("dncreationDate", formatter.format(date).toString());
			}

			if (StringUtils.equalsIgnoreCase(discoverynew.getdnlastmodifDate().toString(), null)) {
				date = new Timestamp(System.currentTimeMillis());
				model.addAttribute("dnlastModifieddate", formatter.format(date).toString());
			} else {
				date = discoverynew.getdnlastmodifDate();
				model.addAttribute("dnlastModifieddate", formatter.format(date).toString());
			}

			model.addAttribute("dnTotalAmount", discoverynew.getDnTotalAmount());
			model.addAttribute("dnStatus", discoverynew.getDnStatus());
			model.addAttribute("dnTotalQty", discoverynew.getDnTotalQty());

		}


		// add data in table discoveryNewItem

		String queryStmt = "select t.dniID as discItemID, t.dniItemcode as discItemCode, t.dniItemname as itemName, t.transType as transType, "
							+"t.elementName as elementName, nvl(t.notes,' ') as notes, "
							+"nvl(t.position,' ') as position, t.dniAPPROVAL as dniAPPROVAL, t.dniPOID as dniPOID, t.supplierID as supplierID, "
							+"t.supplierName as supplierName, t.totalAmount as totalAmount, t.dniWOID as dniWOID,t.purpose as purpose, t.dniQty as dniQty, "
							+"t.dniRate as dniRate, t.dniDiscamount as dniDiscamount , t.dniTax1 as dniTax1, t.dniNetrate as dniNetrate, t.dniTotal as dniTotal, "
							+"t.dniTotalat as dniTotalat, t.dniSIte as dniSIte, t.wareID as wareID, t.wareName as wareName, nvl(t.dniSN,' ') as dniSN, t.dniID as dniDNID, "
							+"nvl(t.itemModel,'') as itemModel, nvl(t.itemPartNb,'') as itemPartNb, nvl(t.nodeID,' ') as nodeID, nvl(t.nodeName,' ') as nodeName, t.approvalStatus as approvalStatus, "
							+"nvl(t.fromSlot,' ') as fromSlot, nvl(t.toSlot,' ') as toSlot, nvl(t.toNodeId,' ') as toNodeId, t.toSite as toSite, "
							+ "t.toWareName as toWareName, t.toWareId as toWareId, "
							+ "nvl(t.toNodeName,' ') as toNodeName, nvl(t.alcFlg,' ') as alcFlg, nvl(t.toSerialNumber,' ') as toSerialNumber, nvl(t.description,' ') as description from DiscoveryNewItem t where t.dniDNID like :param1";

		
		query = session.createQuery(queryStmt);
		query.setParameter("param1", dnid);

		List<DiscoveryNewBoq> listDiscoveryNewItems = (List<DiscoveryNewBoq>) query.setResultTransformer(Transformers.aliasToBean(DiscoveryNewBoq.class)).list();
	
		model.addAttribute("ListPRqItem", mapper.writeValueAsString(listDiscoveryNewItems));
		
		
		} catch (Exception e) {

			logger.info("Error in DiscoveryNew FormView with a message: "+ e);
			e.printStackTrace();
			
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
		    	}
	    	}
	    }
		return "DiscoveryNewFormView";
	}
	
	
	

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/DiscoveryNewItemFormSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> DiscoveryNewItemFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response ) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		

		session = almsessions.getSession(); 
        if (session != null && session.isOpen()) { 
        tx = session.beginTransaction();
		notifications.headerNotifications(session, model);
		try {
		
		String dnid;

		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);

		
		
		
		if (StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNew")) {
			synchronized (this) {						
				dnid = "DND_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
					}
			//dnid = "DND_" + year + "_" + appConfig.getSequenceNbr(6);
			

			model.addAttribute("dnID", dnid);

		} else {
			dnid = request.getParameter("dnID");
		}
		
		Timestamp CreationDate;
		Timestamp ModifDate;
		String crdate = request.getParameter("dncreationDate");
		String totamnt = request.getParameter("dnTotalAmount");
		String dnstat = request.getParameter("dnStatus");
		String totqty = request.getParameter("dnTotalQty"); 
		
		DiscoveryNew discoverynew = new DiscoveryNew();


		DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		Date date1 = null;
		date1 = formatter1.parse(crdate);
		CreationDate = new Timestamp(date1.getTime());

		Date rdate = new Timestamp(System.currentTimeMillis());
		ModifDate = new Timestamp(rdate.getTime());
		
		
		
		// SAVE DISCOVERY NEW
		discoverynew.setDnID(dnid);
		discoverynew.setDncreationDate(CreationDate);
		discoverynew.setdnlastmodifDate(ModifDate);
		discoverynew.setDnTotalAmount(Float.parseFloat(totamnt));
		discoverynew.setDnStatus(dnstat);
		discoverynew.setDnTotalQty(Float.parseFloat(totqty));
		
		session.saveOrUpdate(discoverynew);

		
		
		
		query = session.createQuery("select t.dniID as dniID, t.dniAPPROVAL as dniAPPROVAL from DiscoveryNewItem t where t.dniDNID =:param1");
		query.setString("param1",dnid);
		
		List<DiscoveryNewItem> listDnItems = (List<DiscoveryNewItem>) query.setResultTransformer(Transformers.aliasToBean(DiscoveryNewItem.class)).list();
		
		
		
		if (itemParameters.getDictParameter() != null) {

			System.out.println("AHMAD NEW SAVE = " +itemParameters.getDictParameter());
			
			/// SAVE DISCOVERY NEW ITEM
			String fullitemcode;
			String itmcode;
			String itmname;

			int n;


			String DniID;
			String getApproval;
			
			int size = itemParameters.getDictParameter().size();
			for (int i = 0; i < size ; i++) {
				
				System.out.println("START OF DISCOVERY NEW ITERATION for i= " +i);
				
				DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();

				itmcode = itemParameters.getDictParameter().get(i).get(DN_ITEM);
				itmname = "";
				
				if (!itmcode.isEmpty()) {
					fullitemcode = itemParameters.getDictParameter().get(i).get(DN_ITEM);
					
					n = fullitemcode.indexOf(":");
					itmcode = fullitemcode.substring(0, n);
					itmname = fullitemcode.substring(n + 1, fullitemcode.length());
					
				}

				String PurchaseOrder = itemParameters.getDictParameter().get(i).get(DN_PURCHASE_ORDER);
				
				String supplierID = "";
				String supplierName = "";
				String PurchaseOrId = "";
				float TotalAmount = 0;

				String m, g;
				int part2, part3;
				
				if (!PurchaseOrder.isEmpty()) {
					n = PurchaseOrder.indexOf(":");
					PurchaseOrId = PurchaseOrder.substring(0, n);
					
					
					m = PurchaseOrder.substring(n + 1, PurchaseOrder.length());
					
					part2 = m.indexOf(":");
					supplierID = m.substring(0, part2);
					
					
					g = m.substring(part2 + 1, m.length());
					part3 = g.indexOf(":");
					supplierName = g.substring(0, part3);
					
					
					String Amount = g.substring(part3 + 1, g.length());
					TotalAmount = Float.valueOf(Amount);
					
				}
				
				
				
				String WorkOrder = itemParameters.getDictParameter().get(i).get(DN_WORK_ORDER);
				
				
				String purpose = "";
				String woId="";
				if (!WorkOrder.isEmpty()) {
					n = WorkOrder.indexOf(":");
					woId=WorkOrder.substring(0, n);
					purpose=WorkOrder.substring(n+1,WorkOrder.length());
					
				}

				String Site = itemParameters.getDictParameter().get(i).get(DN_SITE_ID);
				String SiteID = "";
				String wareID = "";
				String wareName = "";

				if (!Site.isEmpty()) {
					n = Site.indexOf(":");
					wareID = Site.substring(0, n);
					m = Site.substring(n + 1, Site.length());
					part2 = m.indexOf(":");
					wareName = m.substring(0, part2);
					SiteID = m.substring(part2 + 1, m.length());
				}
				String alcflg = itemParameters.getDictParameter().get(i).get(ALC_FLG);

				
				//String nodePK = itemParameters.getDictParameter().get(i).get(NODE_PK);
				/*if (nodePK != "") {
				String QueryUpdateInNodeActive ="UPDATE NODE_ACTIVE SET SITE_ID ='"+SiteID+"', WARE_ID ='"+wareID+"' WHERE NODE_PK ='"+nodePK+"'";
				Query qUpdate = session.createSQLQuery(QueryUpdateInNodeActive);
				System.out.println("qUpdate: "+qUpdate);
				qUpdate.executeUpdate();
				}*/
				
			
				String toSite = itemParameters.getDictParameter().get(i).get(DN_TO_SITE_ID);
				String toSiteID = "";
				String towareID = "";
				String towareName = "";

				if (!toSite.isEmpty()) {
					n = toSite.indexOf(":");
					towareID = toSite.substring(0, n);
					m = toSite.substring(n + 1, toSite.length());
					part2 = m.indexOf(":");
					towareName = m.substring(0, part2);
					toSiteID = m.substring(part2 + 1, m.length());
				}

				float dniRate= Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_RATE));
				float dniDiscamount= Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_AMOUT));
				float dniNetrate= Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_NET_RATE));
				String dniTax1= itemParameters.getDictParameter().get(i).get(DN_TAX);
				float dniTotal= Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_TOTAL));
				float dniTotalat= Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_TOTALAT));
				float dniQty= Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_QTY));
				String dniSN= itemParameters.getDictParameter().get(i).get(DN_ITEM_SN);
				String toSerialNumber= itemParameters.getDictParameter().get(i).get(DN_TO_SN);
			    String itemModel= itemParameters.getDictParameter().get(i).get(DN_ITEM_MODEL);
				String itemPartNb= itemParameters.getDictParameter().get(i).get(DN_ITEM_PART_NB);
				String nodeID= itemParameters.getDictParameter().get(i).get(DN_ITEM_NODE_ID);
				String toNodeId= itemParameters.getDictParameter().get(i).get(DN_TO_NODE_ID);
				String nodeName= itemParameters.getDictParameter().get(i).get(DN_ITEM_NODE_NAME);
				String toNodeName= itemParameters.getDictParameter().get(i).get(DN_ITEM_TO_NODE_NAME);
				String fromSlot= itemParameters.getDictParameter().get(i).get(DN_FROM_SLOT);
				String toSlot= itemParameters.getDictParameter().get(i).get(DN_TO_SLOT);
				String transType= itemParameters.getDictParameter().get(i).get(DN_TRANS_TYPE);
				String notes= itemParameters.getDictParameter().get(i).get(DN_NOTES);
				String elementName= itemParameters.getDictParameter().get(i).get(DN_ELEMENT_NAME);
				String position= itemParameters.getDictParameter().get(i).get(DN_ADDRESS);
				String dniAPPROVAL= itemParameters.getDictParameter().get(i).get(APPROVED_BY);
				String approvalStatus= itemParameters.getDictParameter().get(i).get(DN_APPROVE_STATUS);
				String Description= itemParameters.getDictParameter().get(i).get(DESCRIPTION);
				
				
				
				
				if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get(DN_ITEM_ID), "0")) {
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
					//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
					

					// assign the new ID
					discoverynewitem.setDniID(DniID); // dnitmid

					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniItemcode(itmcode);
					discoverynewitem.setDniItemname(itmname);
					discoverynewitem.setDniRate(Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_RATE)));
					discoverynewitem.setDniDiscamount(Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_AMOUT)));
					discoverynewitem.setDniDiscpercent(0);
					discoverynewitem.setDniNetrate(Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_NET_RATE)));
					discoverynewitem.setDniTax1(Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_TAX)));
					discoverynewitem.setDniTax2(0);
					discoverynewitem.setDniTotal(Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_TOTAL)));
					discoverynewitem.setDniTotalat(Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_TOTALAT)));
					discoverynewitem.setWareID(wareID);
					discoverynewitem.setWareName(wareName);
					discoverynewitem.setDniSIte(SiteID);
					discoverynewitem.setToWareId(towareID);
					discoverynewitem.setToWareName(towareName);
					discoverynewitem.setToSite(toSiteID);
					discoverynewitem.setDniQty(Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_QTY)));
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDniSN(itemParameters.getDictParameter().get(i).get(DN_ITEM_SN));
					discoverynewitem.setToSerialNumber(itemParameters.getDictParameter().get(i).get(DN_TO_SN));
					discoverynewitem.setDniPOID(PurchaseOrId);
					discoverynewitem.setSupplierID(supplierID);
					discoverynewitem.setSupplierName(supplierName);
					discoverynewitem.setTotalAmount(TotalAmount);
					discoverynewitem.setDniWOID(woId);
					discoverynewitem.setPurpose(purpose);
					discoverynewitem.setItemModel(itemParameters.getDictParameter().get(i).get(DN_ITEM_MODEL));
					discoverynewitem.setItemPartNb(itemParameters.getDictParameter().get(i).get(DN_ITEM_PART_NB));
					discoverynewitem.setNodeID(itemParameters.getDictParameter().get(i).get(DN_ITEM_NODE_ID));
					discoverynewitem.setToNodeId(itemParameters.getDictParameter().get(i).get(DN_TO_NODE_ID));
					discoverynewitem.setNodeName(itemParameters.getDictParameter().get(i).get(DN_ITEM_NODE_NAME));
					discoverynewitem.setToNodeName(itemParameters.getDictParameter().get(i).get(DN_ITEM_TO_NODE_NAME));
					discoverynewitem.setFromSlot(itemParameters.getDictParameter().get(i).get(DN_FROM_SLOT));
					discoverynewitem.setToSlot(itemParameters.getDictParameter().get(i).get(DN_TO_SLOT));
					discoverynewitem.setTransType(itemParameters.getDictParameter().get(i).get(DN_TRANS_TYPE));
					discoverynewitem.setNotes(itemParameters.getDictParameter().get(i).get(DN_NOTES));
					discoverynewitem.setElementName(itemParameters.getDictParameter().get(i).get(DN_ELEMENT_NAME));
					discoverynewitem.setPosition(itemParameters.getDictParameter().get(i).get(DN_ADDRESS));
					discoverynewitem.setDniAPPROVAL(itemParameters.getDictParameter().get(i).get(APPROVED_BY));
					discoverynewitem.setApprovalStatus(itemParameters.getDictParameter().get(i).get(DN_APPROVE_STATUS));
					discoverynewitem.setDescription(itemParameters.getDictParameter().get(i).get(DESCRIPTION));
					//discoverynewitem.setNodePK(itemParameters.getDictParameter().get(i).get(NODE_PK));
					discoverynewitem.setAlcFlg("0");
					
					session.saveOrUpdate(discoverynewitem);
					

				} else {
					
					
					DniID = itemParameters.getDictParameter().get(i).get(DN_ITEM_ID);
					
					/*paramss.clear();
					paramss.add(DniID);
					String getDnApproveFlagquery = " select alcFlg from DsicoveryNewItem where dniID = :param_1";
					String dnApproveFlag = appConfig.findAllByQueryConditionByParam(DsicoveryNewItem.class, getDnApproveFlagquery,
					paramss);
					paramss.clear();*/
					
query = session.createSQLQuery("update DISCOVERY_NEW_ITEM SET ITEM_CODE ='"+itmcode+"',LAST_MODIFIED_DATE = sysdate, ITEM_NAME = '"+itmname+"', RATE = '"+dniRate+"', DISCOUNT_AMOUNT = '"+dniDiscamount+"', NET_RATE = '"+dniNetrate+"', " 
                            + " TAX1 = '"+dniTax1+"' , TOTAL = '"+dniTotal+"' , TOTAL_AT = '"+dniTotalat+"' , WARE_ID = '"+wareID+"' , WARE_NAME = '"+wareName+"' , "
                            + " FROM_SITE = '"+SiteID+"' , TO_WARE_ID = '"+towareID+"' , TO_WARE_NAME = '"+towareName+"' , TO_SITE = '"+toSiteID+"' , QTY = '"+dniQty+"' , DN_ID = '"+dnid+"' , "
                            + " FROM_SERIAL_NUMBER = '"+dniSN+"' , TO_SERIAL_NUMBER = '"+toSerialNumber+"' , PO_ID = '"+PurchaseOrId+"' , SUPPLIER_ID = '"+supplierID+"' , SUPPLIER_NAME = '"+supplierName+"' , "
                            + " TOTAL_AMOUNT = '"+TotalAmount+"' , WO_ID = '"+woId+"' , WO_PURPOSE = '"+purpose+"' , ITEM_MODEL = '"+itemModel+"' , ITEM_PART_NUMBER = '"+itemPartNb+"' , "
                            + " FROM_NODE_ID = '"+nodeID+"', TO_NODE_ID = '"+toNodeId+"', FROM_NODE_NAME = '"+nodeName+"', TO_NODE_NAME = '"+toNodeName+"', FROM_SLOT = '"+fromSlot+"' , "
                            + " TO_SLOT = '"+toSlot+"', TRANS_TYPE = '"+transType+"', NOTES = '"+notes+"', ELEMENT_NAME ='"+elementName+"', POSITION = '"+position+"', APPROVAL = '"+dniAPPROVAL+"', "
                            + " APPROVAL_STATUS = '"+approvalStatus+"', DESCRIPTION = '"+Description+"', ALCFLG = '"+alcflg+"' where DNI_ID = :param1");

query.setParameter("param1", DniID);
query.executeUpdate();


					
				}
				

				
				
				
				
				String trans_Type = "";
				trans_Type = itemParameters.getDictParameter().get(i).get(DN_TRANS_TYPE);
				String dnStatus = itemParameters.getDictParameter().get(i).get(DN_APPROVE_STATUS);




				getApproval = itemParameters.getDictParameter().get(i).get(APPROVED_BY);
				String ArCode = "";


				query = session.createQuery("select t.arID from AssetRegistry t where t.ardniID = :param1");

				query.setParameter("param1", DniID);
				String AssetRegID = (String) query.uniqueResult();

				String serialnb =""; 
				 serialnb = itemParameters.getDictParameter().get(i).get(DN_TO_SN);
				
				
				float dnRate = Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_RATE));


				
				//DiscoveryThread thread = new DiscoveryThread(trans_Type, getApproval, dnStatus,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,WorkOrder,DniID,toSiteID,supplierID,supplierName,towareID,towareName,serialnb,dnRate,itemModel,itemPartNb,toSite,toSerialNumber,toNodeId,toNodeName,toSlot,nodeID,nodeName,Site,fromSlot,SiteID);
				//thread.start();


				if ((StringUtils.equalsIgnoreCase(getApproval, "Project Manager") && StringUtils.equalsIgnoreCase(dnStatus, "Approved")) || ((StringUtils.equalsIgnoreCase(getApproval,"Asset Unit") && StringUtils.equalsIgnoreCase(dnStatus, "Approved")))) {
				

				if(StringUtils.equalsIgnoreCase(trans_Type, "New Node on New Hardware") || StringUtils.equalsIgnoreCase(trans_Type, "New Hardware on Existed Node") || StringUtils. 
						equalsIgnoreCase(trans_Type, "Replacement") || StringUtils.equalsIgnoreCase(trans_Type, "New Hardware on New Node"))  {

					System.out.println("-- PROJECT MANAGER APPROVAL --");
					
						ApprovalProjectandAsset(trans_Type, getApproval, dnStatus,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,WorkOrder,DniID,toSiteID,supplierID,supplierName,towareID,towareName,serialnb,dnRate,itemModel,itemPartNb,toSite,toSerialNumber,toNodeId,toNodeName,toSlot,nodeID,nodeName,Site,fromSlot,SiteID);
						
					}
				}
				
				

				if (StringUtils.equalsIgnoreCase(getApproval, "Finance") &&  StringUtils.equalsIgnoreCase(dnStatus, "Approved")) { 

				if(StringUtils.equalsIgnoreCase(trans_Type, "New Node on New Hardware") || StringUtils.equalsIgnoreCase(trans_Type, "New Hardware on Existed Node") || StringUtils. 
						equalsIgnoreCase(trans_Type, "Replacement") || StringUtils.equalsIgnoreCase(trans_Type, "New Hardware on New Node"))  {

					System.out.println("-- FINANCE APPROVAL --");

						ApprovalFinance(trans_Type, getApproval, dnStatus,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,WorkOrder,DniID,toSiteID,supplierID,supplierName,towareID,towareName,serialnb,dnRate,itemModel,itemPartNb,toSite,toSerialNumber,toNodeId,toNodeName,toSlot,nodeID,nodeName,Site,fromSlot,SiteID);
						
					}
				}
				
				
				

				if (StringUtils.equalsIgnoreCase(getApproval, "Operation Manager") && StringUtils.equalsIgnoreCase(dnStatus, "Approved")) {
				
				
				if(StringUtils.equalsIgnoreCase(trans_Type, "New Node on Existed Hardware") || StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Slot to Slot") || StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Node to Node") 
						|| StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Site to Site") || StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Maintenance")
						|| StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Transfer") || StringUtils.equalsIgnoreCase(trans_Type, "Maintenance") || StringUtils.equalsIgnoreCase(trans_Type, "Retirement"))
					
								{	

					System.out.println("-- OPERATION MANAGER APPROVAL --");
					
							ApprovalOperational(trans_Type, getApproval, dnStatus,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,WorkOrder,DniID,toSiteID,supplierID,supplierName,towareID,towareName,serialnb,dnRate,itemModel,itemPartNb,toSite,toSerialNumber,toNodeId,toNodeName,toSlot,nodeID,nodeName,Site,fromSlot,SiteID);
		
						}
						
					}
				
				


				
			}
		}

		rtn.put("dnTest", "ok");
		rtn.put("ModifDate", formatter1.format(rdate).toString());
		rtn.put("DNID", dnid);
		return rtn;
		
		
	}catch (Exception e) {
		logger.info("Error in saving discovery new with a message : " + e); 
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
	




	   @RequestMapping(value = "/DiscoveryNewListViewDelete", method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String, Object> DiscoveryNewListViewDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	      

			String idForm=null;
	        Map<String, Object> rtn = new LinkedHashMap<>();
	        if(LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
	        
	        session = almsessions.getSession();
	        String[] idList = request.getParameterValues("dnID[]");

			if (session != null && session.isOpen()) {
	            tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
	            try {
	            	
	            	for(int i= 0; i<idList.length;i++) {
						idForm=idList[i];
						
		            	 query = session.createSQLQuery("Delete DISCOVERY_NEW where DN_ID = '"+ idForm +"'");
	                     query.executeUpdate();
						
		            	 query = session.createSQLQuery("Delete DISCOVERY_NEW_ITEM where DN_ID = '"+ idForm +"'");
	                     query.executeUpdate();
						
		            	 query = session.createSQLQuery("Delete ASSET_LIFE_CYCLE where VOUCHER_NB = '"+ idForm +"'");
	                     query.executeUpdate();
			
	            	}
	            	
	            } catch (Exception e) {
	                System.out.println("Error in creating session with the DiscoveryNewListViewDelete method " + e.getMessage());
	                logger.info("could not connect to DB in DiscoveryNewListViewDelete method ");
	                e.printStackTrace();
				}
				
				finally {
				        if (session != null && session.isOpen()) {
				            tx.commit();
				            session.close();
				         }
				}
				} else {
				        System.out.println("could not connect to DB in DiscoveryNewListViewDelete method");
				        logger.info("could not connect to DB in DiscoveryNewListViewDelete method ");
				}
			
			
			rtn.put("BassamTest", "DeleteDone");
	        return rtn;
	            
	    }
	   	  	   

	   
	   
	@RequestMapping(value = "/DiscoveryNewFormDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> DiscoveryNewFormDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		
	     session = almsessions.getSession();

				if (session != null && session.isOpen()) {
		            tx = session.beginTransaction();
					notifications.headerNotifications(session, model);
		            try {
		            	
		        		
		        		String idForm = request.getParameter("dnID");
		            	
	                    query = session.createSQLQuery("delete DISCOVERY_NEW_ITEM where DN_ID = '"+ idForm +"'");
	                    query.executeUpdate();
	                    
	                    query = session.createSQLQuery("delete DISCOVERY_NEW where DN_ID = '"+ idForm +"'");
	                    query.executeUpdate();

	                    query = session.createSQLQuery("delete ASSET_LIFE_CYCLE where VOUCHER_NB = '"+ idForm +"'");
	                    query.executeUpdate();
	                    
		            } catch (Exception e) {
		                            System.out.println("Error in creating session with the DiscoveryNewFormDelete method " + e.getMessage());
		                            logger.info("could not connect to DB in DiscoveryNewFormDelete method ");
		                            e.printStackTrace();
		            }
		
		            finally {
		                            if (session != null && session.isOpen()) {
		                                            tx.commit();
		                                            session.close();
		                            }
		            }
				} else {
				            System.out.println("could not connect to DB in DiscoveryNewFormDelete method");
				            logger.info("could not connect to DB in DiscoveryNewFormDelete method ");
				}

	rtn.put("DeletingTest", "DeleteDone");
	return rtn;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllPoByItem", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllPoByItem(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){

		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}


	     session = almsessions.getSession();
			
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				
				try {


		
		String PoID = "%" + request.getParameter("PO") + "%";
		String Item_code = request.getParameter("Item_code");
		String itemModel = request.getParameter("itemModel");
		String itemPartNb = request.getParameter("itemPartNb");
		String workOrder=request.getParameter("WoOrderID");
		
		
		String str = "";
		
		
		if(Item_code.equalsIgnoreCase("empty") == false){
			str = "select distinct a.ID, a.supplier, a.supplierName, a.TotalAmount from PurchaseOrder a, PurchaseOrderItem b, "
				+"ItemPartNumber t where (a.ID like :param1 or a.supplier like :param1 or a.supplierName like :param1 "
				+"or a.TotalAmount like :param1) and a.ID = b.POId and b.ItemCode = t.itemCode and b.ItemCode =:param4";
		
		if (itemModel.equalsIgnoreCase("empty") == false) {
			str += " and t.itemModel  =:param2";
		}
		
		if (itemPartNb.equalsIgnoreCase("empty") == false) {
			str += " and t.itemPartNum =:param3";
		}
		
		
		query = session.createQuery(str);
		
		if (itemModel.equalsIgnoreCase("empty") == false) {query.setString("param2", itemModel); }
		
		if (itemPartNb.equalsIgnoreCase("empty") == false) {query.setString("param3", itemPartNb); }
		
		query.setString("param4", Item_code);
	}
	 
		else if(workOrder.equalsIgnoreCase("empty") == false) {
			
			str = "select distinct a.ID, a.supplier, a.supplierName, a.TotalAmount from PurchaseOrder a, PurchaseOrderItem b, "
				 +"Item c, ItemPartNumber t, WorkOrderSource d,WorkOrderDestination e where (a.ID like :param1 or a.supplier like :param1 or a.supplierName "
				 +"like :param1 or a.TotalAmount like :param1) and a.ID = b.POId and b.ItemCode = c.itemCode "
				 +"and d.workOrdId =:param5 and e.workOrdId =:param5 and b.ItemCode = d.itemCode and b.ItemCode = e.itemCode and b.ItemCode = t.itemCode";
		 
		 if (itemModel.equalsIgnoreCase("empty") == false) {
			 str += " and t.itemModel  =:param2";
			}
			
			if (itemPartNb.equalsIgnoreCase("empty") == false) {
				str += " and t.itemPartNum =:param3";
			}
			
			
			query = session.createQuery(str);
			
			if (itemModel.equalsIgnoreCase("empty") == false) {query.setString("param2", itemModel); }
			
			if (itemPartNb.equalsIgnoreCase("empty") == false) {query.setString("param3", itemPartNb); }
			query.setString("param5", workOrder);
		 
	 }
		 
		else {
			
			str = "select  distinct a.ID, a.supplier, a.supplierName, a.TotalAmount from PurchaseOrder a, "
					 +"PurchaseOrderItem b, Item c, ItemPartNumber t where (a.ID like :param1 or a.supplier like :param1 "
					 +"or a.supplierName like :param1 or a.TotalAmount like :param1) and a.ID = b.POId "
					 + "and b.ItemCode = c.itemCode and b.ItemCode = t.itemCode";
			 
			 
				
				if (itemModel.equalsIgnoreCase("empty") == false) {
					str += " and t.itemModel  =:param2";
				}
				
				if (itemPartNb.equalsIgnoreCase("empty") == false) {
					str += " and t.itemPartNum =:param3";
				}
				
				
				query = session.createQuery(str);
				
				if (itemModel.equalsIgnoreCase("empty") == false) {query.setString("param2", itemModel); }
				
				if (itemPartNb.equalsIgnoreCase("empty") == false) {query.setString("param3", itemPartNb); }
				
		 }
		 
		 
		query.setString("param1", PoID);

		
		System.out.println("the purchase order id is " + PoID);
		List<Object[]> listResult = query.list();
		System.out.println("The list is " + mapper.writeValueAsString(listResult));

		
		model.addAttribute("listResult", mapper.writeValueAsString(listResult));
		rtn.put("ListPos", listResult);

				}catch(Exception e){ 

					logger.info("Error while getting PurchaseOrder using autocomplete with error message: "+ e); 
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
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetALLWOByItem", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetALLWOByItem(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){

		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}


     session = almsessions.getSession();
		
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			
			try {


		String woID="%" + request.getParameter("woId") + "%";
		String PoID =request.getParameter("PrOrderID") ;
		String Item_code = request.getParameter("Item_code");
		String itemModel = request.getParameter("itemModel");
		String itemPartNb = request.getParameter("ItemPartNb");
		
		
		
		String str = "";
		
		 if(Item_code.equalsIgnoreCase("empty") == false){
			 
			str="select distinct a.workOrdId ,a.purpose from WorkOrder a,WorkOrderSource b,WorkOrderDestination c, "
					+"Item d, ItemPartNumber t where(a.workOrdId like :param1 or a.purpose like :param1 ) and a.workOrdId = b.workOrdId "
					+"and a.workOrdId = c.workOrdId and b.itemCode = d.itemCode and c.itemCode = d.itemCode "
					+"and b.itemCode =:param4 and t.itemCode = d.itemCode and c.itemCode =:param4 ";

		
			if (itemModel.equalsIgnoreCase("empty") == false) {
				str += " and t.itemModel  =:param2";
			}
			
			
			
			if (itemPartNb.equalsIgnoreCase("empty") == false) {
				str += " and t.itemPartNum =:param3";
			}
			
			
			
			query = session.createQuery(str);
			
			if (itemModel.equalsIgnoreCase("empty") == false) {query.setString("param2", itemModel); }
			
			if (itemPartNb.equalsIgnoreCase("empty") == false) {query.setString("param3", itemPartNb); }
			
			
			query.setString("param4", Item_code);

		}
		
		
		
		 else if(PoID.equalsIgnoreCase("empty") == false) {
			
			str="select distinct  a.workOrdId ,a.purpose from WorkOrder a,WorkOrderSource b,WorkOrderDestination c,Item d, "
				  +"PurchaseOrderItem e, ItemPartNumber t "
				  +"where(a.workOrdId like :param1 or a.purpose like :param1 ) and a.workOrdId = b.workOrdId and a.workOrdId = c.workOrdId "
				  +"and b.itemCode = d.itemCode and c.itemCode = d.itemCode and e.POId =:param4 and t.itemCode = d.itemCode";
			

			if (itemModel.equalsIgnoreCase("empty") == false) {
				str += " and t.itemModel  =:param2";
			}
			
			
			if (itemPartNb.equalsIgnoreCase("empty") == false) {
				str += " and t.itemPartNum =:param3";
			}
			
			
			query = session.createQuery(str);
			
			if (itemModel.equalsIgnoreCase("empty") == false) {query.setString("param2", itemModel); }
			
			if (itemPartNb.equalsIgnoreCase("empty") == false) {query.setString("param3", itemPartNb); }
			
			
			query.setString("param4",PoID );
			
			
			
		}
		 
		 
		 
		 else
			{
				
				str="select distinct  a.workOrdId ,a.purpose from WorkOrder a,WorkOrderSource b,WorkOrderDestination c,Item d, ItemPartNumber t "
					  +"where(a.workOrdId like :param1 or a.purpose like :param1 ) and a.workOrdId = b.workOrdId and a.workOrdId = c.workOrdId "
					  +"and b.itemCode = d.itemCode and c.itemCode = d.itemCode and t.itemCode = d.itemCode";
			
				if (itemModel.equalsIgnoreCase("empty") == false) {
					str += " and t.itemModel  =:param2";
				}
				
				
				if (itemPartNb.equalsIgnoreCase("empty") == false) {
					str += " and t.itemPartNum =:param3";
				}
				
				query = session.createQuery(str);
				
				if (itemModel.equalsIgnoreCase("empty") == false) {query.setString("param2", itemModel); }
				
				if (itemPartNb.equalsIgnoreCase("empty") == false) {query.setString("param3", itemPartNb); }
				
			}
		
		
		
		 query.setString("param1", woID);

		
		List<Object[]> listResult = query.list();
		System.out.println("The list is " + mapper.writeValueAsString(listResult));

		model.addAttribute("listResult", mapper.writeValueAsString(listResult));
		rtn.put("ListWO", listResult); 

			}catch(Exception e){ 

				logger.info("Error while getting WorkOrder using autocomplete with error message: "+ e); 
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
	
	
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/setGlobalApprove", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> setGlobalApprove(Locale locale, Model model,ItemParameters itemParameters, HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
		}
		

		
		session = almsessions.getSession();
		
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			
			try {

		
		for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {

			String ApproveType = itemParameters.getDictParameter().get(i).get("ApproveType");
			String ApprovAction = itemParameters.getDictParameter().get(i).get("ApprovAction");
			String DNItemID = itemParameters.getDictParameter().get(i).get("DNItem");
			
			
			query = session.createQuery("update DiscoveryNewItem c set c.dniAPPROVAL =:param1, c.approvalStatus=:param2 where c.dniID =:param3");

			query.setParameter("param1",ApproveType);
			query.setParameter("param2", ApprovAction);
			query.setParameter("param3", DNItemID);
			
			query.executeUpdate();
			
			
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			
			String itmcode = itemParameters.getDictParameter().get(i).get(DN_ITEM);
			String itmname = "";
			String fullitemcode;
			int n;
			
			if (!itmcode.isEmpty()) {
				fullitemcode = itmcode;
				n = fullitemcode.indexOf(":");
				itmcode = fullitemcode.substring(0, n);
				itmname = fullitemcode.substring(n + 1, fullitemcode.length());
			}

			String PurchaseOrder = itemParameters.getDictParameter().get(i).get(DN_PURCHASE_ORDER);
			String supplierID = "";
			String supplierName = "";
			String PurchaseOrId = "";
			float TotalAmount = 0;

			String m, g;
			int part2, part3;
			if (!PurchaseOrder.isEmpty()) {
				n = PurchaseOrder.indexOf(":");
				PurchaseOrId = PurchaseOrder.substring(0, n);
				
				m = PurchaseOrder.substring(n + 1, PurchaseOrder.length());
				part2 = m.indexOf(":");
				supplierID = m.substring(0, part2);
				
				g = m.substring(part2 + 1, m.length());
				part3 = g.indexOf(":");
				supplierName = g.substring(0, part3);
				
				String Amount = g.substring(part3 + 1, g.length());
				TotalAmount = Float.valueOf(Amount);
			}

			String Site = itemParameters.getDictParameter().get(i).get(DN_SITE_ID);
			String SiteID = "";
			String wareID = "";
			String wareName = "";

			if (!Site.isEmpty()) {
				n = Site.indexOf(":");
				wareID = Site.substring(0, n);
				m = Site.substring(n + 1, Site.length());
				part2 = m.indexOf(":");
				wareName = m.substring(0, part2);
				SiteID = m.substring(part2 + 1, m.length());
			}
			
			/*String toSite = itemParameters.getDictParameter().get(i).get(DN_TO_SITE_ID);
			String toSiteID = "";
			String towareID = "";
			String towareName = "";

			if (!toSite.isEmpty()) {
				n = toSite.indexOf(":");
				towareID = toSite.substring(0, n);
				m = toSite.substring(n + 1, toSite.length());
				part2 = m.indexOf(":");
				towareName = m.substring(0, part2);
				toSiteID = m.substring(part2 + 1, m.length());
			}*/
			
			if (ApproveType.equalsIgnoreCase("1") == true) {
				
				
				String ArCode;
				//ArCode = "AR_" + year + "_" + appConfig.getSequenceNbr(9);
				synchronized (this) {						
					ArCode = "AR_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET ASSET_REGISTRY = ASSET_REGISTRY + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}

				
				// assign the AR ID
				AssetRegistry assetregistry = new AssetRegistry();
				
				assetregistry.setArID(ArCode);
				assetregistry.setAritemCode(itmcode);
				assetregistry.setArcreatedDate(new Timestamp(System.currentTimeMillis()));
				assetregistry.setArlastModifiedDate(new Timestamp(System.currentTimeMillis()));
				assetregistry.setAritemName(itmname);
				assetregistry.setArdniID(DNItemID);
				assetregistry.setPoID(PurchaseOrId);
				//assetregistry.setSiteID(SiteID);
				assetregistry.setSupplierID(supplierID);
				assetregistry.setSupplierName(supplierName);
				assetregistry.setItemModel(itemParameters.getDictParameter().get(i).get(DN_ITEM_MODEL));
				assetregistry.setItemPartNumber(itemParameters.getDictParameter().get(i).get(DN_ITEM_PART_NB));
				assetregistry.setItemSN(itemParameters.getDictParameter().get(i).get(DN_ITEM_SN));

				session.saveOrUpdate(assetregistry);

			}
			
			if (ApproveType.equalsIgnoreCase("3") == true) {

				// check, if this itemCode related to poID, exist in CIP table (get the qty and cipID)
				
				
				query = session.createQuery("Select t.TOTALQTY as totalqty, t.cipID as cipID from CapitalInProgress t where t.PoId =:param1 and t.cipitemCode =:param2");

				query.setString("param1", PurchaseOrId);
				query.setString("param2", itmcode);
			
				List result = query.list();
				
				String FarCode;

				// if exist, get only one row, with CIPqty field and cipID
				if (result != null) {
					for (Object obj : result) {
						Object[] fields = (Object[]) obj;

						String result1 = fields[0].toString();
						float updatedQty = Float.parseFloat(result1) - 1; // substract 1 from the TotalQty
						System.out.println("updatedQty is: "+updatedQty);

						if (updatedQty == 0) {
							// delete from CIP table

							
							query = session.createQuery("delete from CapitalInProgress c where c.cipID =:param1");

							query.setParameter("param1", fields[1]);
							query.executeUpdate();
							

						}

						else {

							// update the qty in CIP table
							
							query = session.createQuery("update CapitalInProgress c set c.TOTALQTY =:param1 where c.cipID =:param2");

							query.setFloat("param1", updatedQty);
							query.setParameter("param2", fields[1]);
							query.executeUpdate();
							

						}
						synchronized (this) {						
							FarCode = "FAR_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FIXED_ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET FIXED_ASSET_REGISTRY = FIXED_ASSET_REGISTRY + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
						//FarCode = "FAR_" + year + "_" + appConfig.getSequenceNbr(10);
						
						// insert the DisNewItem in FAR table
						float initialCost = Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_RATE));
						
						FixedAssetRegistry FixedAssetReg = new FixedAssetRegistry();
						
						FixedAssetReg.setFarID(FarCode);
						FixedAssetReg.setFaritemCode(itmcode);
						FixedAssetReg.setFarcreatedDate(new Timestamp(System.currentTimeMillis()));
						FixedAssetReg.setFarlastModifiedDate(new Timestamp(System.currentTimeMillis()));
						FixedAssetReg.setFaritemName(itmname);
						FixedAssetReg.setIniCost(initialCost);
						FixedAssetReg.setDniID(DNItemID);
						//FixedAssetReg.setSideID(SiteID);
						FixedAssetReg.setSupplierID(supplierID);
						FixedAssetReg.setSupplierName(supplierName);
						FixedAssetReg.setPoId(PurchaseOrId);
						FixedAssetReg.setItemModel(itemParameters.getDictParameter().get(i).get(DN_ITEM_MODEL));
						FixedAssetReg.setItemPartNb(itemParameters.getDictParameter().get(i).get(DN_ITEM_PART_NB));
						FixedAssetReg.setItemSN(itemParameters.getDictParameter().get(i).get(DN_ITEM_SN));

						session.saveOrUpdate(FixedAssetReg);

					}
				}

			}
		
		}
		map.put("DeleteTest", "DeleteDone");
		
		} catch (Exception e) {
			logger.info("Error at DiscoveryNew setglobalApprove with a message: "+ e);
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
			
		}
		return map;
	}
	
		
		
	
	
	@RequestMapping(value = "/GetAllDN", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllDN(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) { 
			rtn.put("Login", "redirect:/"); 
			return rtn; 
			}  
		

		Session session = almsessions.getSession();
		Transaction tx =null;
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
	try {


		String DN = "%" + request.getParameter("DN") + "%";
		
		query = session.createQuery("select t.dnID, t.dnStatus from DiscoveryNew t where t.dnID like :param1 or t.dnStatus like :param1 ORDER BY dnlastmodifDate DESC");
		query.setString("param1", DN);
		rtn.put("listDN", query.list());


	}catch(Exception e) {
		
		System.out.println("catch messsage is "+e.getMessage());
		
	}
	finally {

	      if (session != null && session.isOpen()) {
	    	  
	    	  tx.commit();
		      session.close();
		      
	      }

	 }
		}
		
		return rtn;
	}

	
	
	
	
//These scripts to get the discovered items by the Auto Parser	
	
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/runDiscoveryNewScript", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runDiscoveryNewScript(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		Map<String,Object> map = new HashMap<String, Object>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
		}
		try {

			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			
			ObjectMapper mapper = new ObjectMapper();
			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			
			String getBoardsquery, getNewBoardOnExistedNodequery, getCabinetsquery, getNewCabinetOnExistedNode, getAntennasquery, getNewAntennaOnExistedNodequery,getNewNode ; 
			Query q, q1, q2, q3, q4, q5, q6;
			List<Object[]> NewNodeBoardsList; List<Object[]> NewNodeCabinetsList; List<Object[]> NewNodeAntennasList; List<Object[]> NewNodeList;
			List<Object[]> ExistedNodeBoardsList; List<Object[]> ExistedNodeCabinetsList; List<Object[]> ExistedNodeAntennasList;
			
			getBoardsquery = "Select nodeBoard.FROM_TRANS_ID, nodeActive.node_pk, nodeBoard.from_trans_source, nodeBoard.serialnumber, nodeboard.boardtype, " 
			 				+"nodeBoard.alm_position, nodeBoard.TRANS_TYPE, nodeActive.NODE_ID, nodeActive.NODE_NAME from NODE_BOARD nodeBoard " 
							+"inner join NODE_ACTIVE nodeActive on nodeBoard.NODE_PK = nodeActive.NODE_PK where nodeBoard.FROM_TRANS_ID IN (" 
							+"select node_active_trans.trans_id from NODE_ACTIVE node_active inner join " 
							+"NODE_ACTIVE_TRANSACTIONS node_active_trans on node_active.FROM_TRANS_ID = node_active_trans.trans_id " 
							+"where node_active.active_record = 1 and node_active.trans_type = 'New Node' and sent_to_alm = 0) and nodeBoard.active_record = 1"
							+"and (nodeBoard.TRANS_TYPE = 'New Hardware' OR nodeBoard.TRANS_TYPE = 'Existed Hardware' OR nodeBoard.TRANS_TYPE = 'New Node')";
			
			getNewBoardOnExistedNodequery = "Select nodeBoard.FROM_TRANS_ID, nodeActive.node_pk, nodeBoard.from_trans_source, nodeBoard.serialnumber, nodeboard.boardtype, nodeBoard.alm_position, nodeBoard.TRANS_TYPE, "
											+"nodeActive.NODE_ID, nodeActive.NODE_NAME from NODE_BOARD nodeBoard  " 
											+"inner join NODE_ACTIVE nodeActive on nodeBoard.NODE_PK = nodeActive.NODE_PK where nodeBoard.FROM_TRANS_ID IN ( "
											+"select node_board_trans.trans_id from NODE_BOARD node_board inner join " 
											+"NODE_BOARD_TRANSACTIONS node_board_trans on node_board.FROM_TRANS_ID = node_board_trans.trans_id " 
											+"where node_board.active_record = 1 and node_board.trans_type = 'New Board' and sent_to_alm = 0) and nodeBoard.active_record = 1";
			
			getCabinetsquery = "Select nodeCabinet.FROM_TRANS_ID, nodeActive.node_pk, nodeCabinet.from_trans_source, nodeCabinet.serialnumber, nodeCabinet.model, nodeCabinet.alm_position, nodeCabinet.TRANS_TYPE, nodeActive.NODE_ID, nodeActive.NODE_NAME from NODE_CABINET nodeCabinet " 
								+"inner join NODE_ACTIVE nodeActive on nodeCabinet.NODE_PK = nodeActive.NODE_PK where nodeCabinet.FROM_TRANS_ID IN (" 
								+"select node_active_trans.trans_id from NODE_ACTIVE node_active inner join " 
								+"NODE_ACTIVE_TRANSACTIONS node_active_trans on node_active.FROM_TRANS_ID = node_active_trans.trans_id " 
								+"where node_active.active_record = 1 and node_active.trans_type = 'New Node' and sent_to_alm = 0) and nodeCabinet.active_record = 1"
								+ " and (nodeCabinet.TRANS_TYPE = 'New Hardware' OR nodeCabinet.TRANS_TYPE = 'Existed Hardware' OR nodeCabinet.TRANS_TYPE = 'New Node')"; 
			
			getNewCabinetOnExistedNode = "Select nodeCabinet.FROM_TRANS_ID, nodeActive.node_pk, nodeCabinet.from_trans_source, nodeCabinet.serialnumber, nodeCabinet.model, nodeCabinet.alm_position, " 
									     +"nodeCabinet.TRANS_TYPE, nodeActive.NODE_ID, nodeActive.NODE_NAME from NODE_CABINET nodeCabinet " 
									     +"inner join NODE_ACTIVE nodeActive on nodeCabinet.NODE_PK = nodeActive.NODE_PK where nodeCabinet.FROM_TRANS_ID IN( "  
									     +"select node_cabinet_trans.trans_id from NODE_CABINET node_cabinet inner join " 
									     +"NODE_CABINET_TRANSACTIONS node_cabinet_trans on node_cabinet.FROM_TRANS_ID = node_cabinet_trans.trans_id " 
									     +"where node_cabinet.active_record = 1 and node_cabinet.trans_type = 'NEW CABINET' and sent_to_alm = 0) and nodeCabinet.active_record = 1";
			
			getAntennasquery = "Select nodeAntenna.FROM_TRANS_ID, nodeActive.node_pk, nodeAntenna.from_trans_source, nodeAntenna.serialnumber, nodeAntenna.model, nodeAntenna.TRANS_TYPE, nodeActive.NODE_ID, " 
								+"nodeActive.NODE_NAME from NODE_ANTENNA nodeAntenna "
								+"inner join NODE_ACTIVE nodeActive on nodeAntenna.NODE_PK = nodeActive.NODE_PK where nodeAntenna.FROM_TRANS_ID IN ( " 
								+"select node_active_trans.trans_id from NODE_ACTIVE node_active inner join " 
								+"NODE_ACTIVE_TRANSACTIONS node_active_trans on node_active.FROM_TRANS_ID = node_active_trans.trans_id  "
								+"where node_active.active_record = 1 and node_active.trans_type = 'New Node' and sent_to_alm = 0) and nodeAntenna.active_record = 1 "  
								+"and (nodeAntenna.TRANS_TYPE = 'New Hardware' OR nodeAntenna.TRANS_TYPE = 'Existed Hardware' OR nodeAntenna.TRANS_TYPE = 'New Node') ";
			
			getNewAntennaOnExistedNodequery = "Select nodeAntenna.FROM_TRANS_ID, nodeActive.node_pk, nodeAntenna.from_trans_source, nodeAntenna.serialnumber, nodeAntenna.model, nodeAntenna.TRANS_TYPE, nodeActive.NODE_ID, " 
											  +"nodeActive.NODE_NAME from NODE_ANTENNA nodeAntenna " 
											  +"inner join NODE_ACTIVE nodeActive on nodeAntenna.NODE_PK = nodeActive.NODE_PK where nodeAntenna.FROM_TRANS_ID IN ( "
											  +"select node_antenna_trans.trans_id from NODE_ANTENNA node_antenna inner join "
											  +"NODE_ANTENNA_TRANSACTIONS node_antenna_trans on node_antenna.FROM_TRANS_ID = node_antenna_trans.trans_id  "  
											  +"where node_antenna.active_record = 1 and node_antenna.trans_type = 'Antenna Change' and sent_to_alm = 0) and nodeAntenna.active_record = 1";
			
			getNewNode = " Select nodeCabinet.FROM_TRANS_ID, nodeActive.node_pk, nodeCabinet.from_trans_source,nodeCabinet.serialnumber, nodeCabinet.model, "
					+ "nodeCabinet.alm_position, nodeCabinet.TRANS_TYPE,nodeActive.NODE_ID, nodeActive.NODE_NAME from NODE_CABINET nodeCabinet"
					+ " inner join NODE_ACTIVE nodeActive on nodeCabinet.NODE_PK = nodeActive.NODE_PK where nodeCabinet.FROM_TRANS_ID "
					+ "IN (select node_active_trans.trans_id from NODE_ACTIVE node_active"
					+ " inner join NODE_ACTIVE_TRANSACTIONS node_active_trans on node_active.FROM_TRANS_ID = node_active_trans.trans_id "
					+ "where node_active.active_record = 1 and node_active.trans_type = 'New Node' and sent_to_alm = 0) "
					+ "and nodeCabinet.active_record = 1 and nodeCabinet.CABINETNO ='0' "
					+ "and (nodeCabinet.TRANS_TYPE = 'New Hardware' OR nodeCabinet.TRANS_TYPE = 'Existed Hardware')";
			
			q = session.createSQLQuery(getBoardsquery);
			q1 = session.createSQLQuery(getCabinetsquery);
			q2 = session.createSQLQuery(getAntennasquery);
			q3 = session.createSQLQuery(getNewBoardOnExistedNodequery);
			q4 = session.createSQLQuery(getNewCabinetOnExistedNode);
			q5 = session.createSQLQuery(getNewAntennaOnExistedNodequery);
			q6 = session.createSQLQuery(getNewNode);
			
			NewNodeBoardsList = q.list();
			NewNodeCabinetsList = q1.list();
			NewNodeAntennasList = q2.list();
			ExistedNodeBoardsList = q3.list();
			ExistedNodeCabinetsList = q4.list();
			ExistedNodeAntennasList = q5.list();
			NewNodeList = q6.list();
			
			
			String dnid; DiscoveryNew discoverynew = new DiscoveryNew();
			synchronized (this) {						
				dnid = "DND_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
					}
			//dnid = "DND_" + year + "_" + appConfig.getSequenceNbr(6);
			
			Date date1;
			
			date1 = new Timestamp(System.currentTimeMillis());
			Timestamp CreationDate = new Timestamp(date1.getTime());
				
			List<String> transBoardIDlist = new ArrayList<>();
			List<String> nodeIDlist = new ArrayList<>();
			List<String> transCabinetIDlist = new ArrayList<>();
			List<String> transAntennaIDlist = new ArrayList<>();
			
			if(NewNodeCabinetsList.size() >0 || NewNodeBoardsList.size() >0 || ExistedNodeBoardsList.size()>0 || ExistedNodeCabinetsList.size()>0 || ExistedNodeAntennasList.size()>0 || NewNodeAntennasList.size() > 0 || NewNodeList.size() > 0)
			{
				discoverynew.setDnID(dnid);
				discoverynew.setDncreationDate(CreationDate);
				discoverynew.setdnlastmodifDate(CreationDate);
				session.saveOrUpdate(discoverynew);
				//appConfig.persist(discoverynew, DiscoveryNew.class);
				
				String DniID;
				//NewItem discoverynewitem = new DsicoveryNewItem();
				
				String trans_id = "", node_pk = "";
				for(int i=0; i<NewNodeCabinetsList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  NewNodeCabinetsList.get(i);
					//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[1].toString();

					
					if(nodeIDlist.indexOf(node_pk) < 0)
						nodeIDlist.add(node_pk);
					
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setTransID(arrayList[0].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setToSerialNumber(arrayList[3].toString());
					discoverynewitem.setItemModel(arrayList[4].toString());
					discoverynewitem.setPosition(arrayList[5].toString());
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Hardware"))
						//discoverynewitem.setTransType("New Node on New Hardware");
						discoverynewitem.setTransType("New Hardware on New Node");
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "Existed Hardware"))
						//discoverynewitem.setTransType("New Node on Existed Hardware");
						discoverynewitem.setTransType("Existed Hardware on New Node");
					/*if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Node"))
						discoverynewitem.setTransType("New Node");*/
					
					discoverynewitem.setToNodeId(arrayList[7].toString());
					discoverynewitem.setToNodeName(arrayList[8].toString());
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setNodePK(node_pk);
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Hardware"))
						discoverynewitem.setDniAPPROVAL("Project Manager");
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "Existed Hardware"))
						discoverynewitem.setDniAPPROVAL("Operation Manager");
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Node"))
						discoverynewitem.setDniAPPROVAL("");
					
					discoverynewitem.setElementName("Cabinet");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
				}
				
				for(int i=0; i<ExistedNodeCabinetsList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					
					Object [] arrayList=  ExistedNodeCabinetsList.get(i);
					//DniID = "DNI_" + year + "_" +appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[1].toString();

					 
					if(transCabinetIDlist.indexOf(trans_id) < 0)
						transCabinetIDlist.add(trans_id);
				
					
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setTransID(arrayList[0].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setToSerialNumber(arrayList[3].toString());
					discoverynewitem.setItemModel(arrayList[4].toString());
					discoverynewitem.setPosition(arrayList[5].toString());
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "NEW CABINET"))
						discoverynewitem.setTransType("New Hardware on Existed Node");
					
					discoverynewitem.setToNodeId(arrayList[7].toString());
					discoverynewitem.setToNodeName(arrayList[8].toString());
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Project Manager");
					discoverynewitem.setElementName("Cabinet");
					discoverynewitem.setNodePK(node_pk);
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
				}
				
				for(int i=0; i<NewNodeBoardsList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  NewNodeBoardsList.get(i);
					//DniID = "DNI_" + year + "_" +appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}

					trans_id = arrayList[0].toString();
					node_pk = arrayList[1].toString();

					if(transBoardIDlist.indexOf(trans_id) < 0)
						transBoardIDlist.add(trans_id);
					
					if(nodeIDlist.indexOf(node_pk) < 0)
						nodeIDlist.add(node_pk);
					
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setTransID(arrayList[0].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setToSerialNumber(arrayList[3].toString());
					discoverynewitem.setItemModel(arrayList[4].toString());
					discoverynewitem.setPosition(arrayList[5].toString());
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Hardware"))
						//discoverynewitem.setTransType("New Node on New Hardware");
						discoverynewitem.setTransType("New Hardware on New Node");
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "Existed Hardware"))
						//discoverynewitem.setTransType("New Node on Existed Hardware");
							discoverynewitem.setTransType("Existed Hardware on New Node");
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Node"))
						discoverynewitem.setTransType("New Node");
					
					discoverynewitem.setToNodeId(arrayList[7].toString());
					discoverynewitem.setToNodeName(arrayList[8].toString());
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Hardware"))
						discoverynewitem.setDniAPPROVAL("Project Manager");
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "Existed Hardware"))
						discoverynewitem.setDniAPPROVAL("Operation Manager");
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Node"))
						discoverynewitem.setDniAPPROVAL("");
					
					discoverynewitem.setElementName("Board");
					discoverynewitem.setNodePK(node_pk);
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
				}
				
				for(int i=0; i<ExistedNodeBoardsList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  ExistedNodeBoardsList.get(i);
					//DniID = "DNI_" + year + "_" +appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}

					trans_id = arrayList[0].toString();
					node_pk = arrayList[1].toString();

					 
					if(transBoardIDlist.indexOf(trans_id) < 0)
						transBoardIDlist.add(trans_id);
				
					
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setTransID(arrayList[0].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setToSerialNumber(arrayList[3].toString());
					discoverynewitem.setItemModel(arrayList[4].toString());
					discoverynewitem.setPosition(arrayList[5].toString());
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Board"))
						discoverynewitem.setTransType("New Hardware on Existed Node");
					
					discoverynewitem.setToNodeId(arrayList[7].toString());
					discoverynewitem.setToNodeName(arrayList[8].toString());
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Project Manager");
					discoverynewitem.setElementName("Board");
					discoverynewitem.setNodePK(node_pk);
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
				}
				
				for(int i=0; i<NewNodeAntennasList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  NewNodeAntennasList.get(i);
					//DniID = "DNI_" + year + "_" +appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}

					trans_id = arrayList[0].toString();
					node_pk = arrayList[1].toString();

					if(transBoardIDlist.indexOf(trans_id) < 0)
						transBoardIDlist.add(trans_id);
					
					
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setTransID(arrayList[0].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setToSerialNumber(arrayList[3].toString());
					discoverynewitem.setItemModel(arrayList[4].toString());
					
					if (StringUtils.equalsIgnoreCase(arrayList[5].toString(), "New Hardware"))
						//discoverynewitem.setTransType("New Node on New Hardware");
							discoverynewitem.setTransType("New Hardware on New Node");
					if (StringUtils.equalsIgnoreCase(arrayList[5].toString(), "Existed Hardware"))
						//discoverynewitem.setTransType("New Node on Existed Hardware");
							discoverynewitem.setTransType("Existed Hardware on New Node");
					if (StringUtils.equalsIgnoreCase(arrayList[5].toString(), "New Node"))
						discoverynewitem.setTransType("New Node");
					
					discoverynewitem.setToNodeId(arrayList[6].toString());
					discoverynewitem.setToNodeName(arrayList[7].toString());
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					
					if (StringUtils.equalsIgnoreCase(arrayList[5].toString(), "New Hardware"))
						discoverynewitem.setDniAPPROVAL("Project Manager");
					
					if (StringUtils.equalsIgnoreCase(arrayList[5].toString(), "Existed Hardware"))
						discoverynewitem.setDniAPPROVAL("Operation Manager");
					
					if (StringUtils.equalsIgnoreCase(arrayList[5].toString(), "New Node"))
						{discoverynewitem.setDniAPPROVAL("");
						}
					
					discoverynewitem.setElementName("Antenna");
					discoverynewitem.setNodePK(node_pk);
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
				}
				
				for(int i=0; i<ExistedNodeAntennasList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  ExistedNodeAntennasList.get(i);
					//DniID = "DNI_" + year + "_" +appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}

					trans_id = arrayList[0].toString();
					node_pk = arrayList[1].toString();

					 
					if(transAntennaIDlist.indexOf(trans_id) < 0)
						transAntennaIDlist.add(trans_id);
				
					
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setTransID(arrayList[0].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setToSerialNumber(arrayList[3].toString());
					discoverynewitem.setItemModel(arrayList[4].toString());
					
					if (StringUtils.equalsIgnoreCase(arrayList[5].toString(), "Antenna Change"))
						discoverynewitem.setTransType("New Hardware on Existed Node");
					
					discoverynewitem.setToNodeId(arrayList[6].toString());
					discoverynewitem.setToNodeName(arrayList[7].toString());
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Project Manager");
					discoverynewitem.setElementName("Antenna");
					discoverynewitem.setNodePK(node_pk);
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
				}
				
					for(int i=0; i<NewNodeList.size(); i++)
				{
						DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  NewNodeList.get(i);
					//DniID = "DNI_" + year + "_" +appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}

					trans_id = arrayList[0].toString();
					node_pk = arrayList[1].toString();

					
					/*if(nodeIDlist.indexOf(node_pk) < 0)
						nodeIDlist.add(node_pk);*/
					
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setTransID(arrayList[0].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setToSerialNumber(arrayList[3].toString());
					discoverynewitem.setItemModel(arrayList[4].toString());
					discoverynewitem.setPosition(arrayList[5].toString());
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Hardware"))
						discoverynewitem.setTransType("New Node on New Hardware");
						//discoverynewitem.setTransType("New Hardware on New Node");
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "Existed Hardware"))
						discoverynewitem.setTransType("New Node on Existed Hardware");
						//discoverynewitem.setTransType("Existed Hardware on New Node");
					/*if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Node"))
						discoverynewitem.setTransType("New Node");*/
					
					discoverynewitem.setToNodeId(arrayList[7].toString());
					discoverynewitem.setToNodeName(arrayList[8].toString());
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setNodePK(node_pk);
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Hardware"))
						discoverynewitem.setDniAPPROVAL("Project Manager");
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "Existed Hardware"))
						discoverynewitem.setDniAPPROVAL("Operation Manager");
					
					if (StringUtils.equalsIgnoreCase(arrayList[6].toString(), "New Node"))
						discoverynewitem.setDniAPPROVAL("");
					
					discoverynewitem.setElementName("Cabinet");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
				}
				
	
				for(int i=0; i<nodeIDlist.size(); i++)
				{
					String nodeID = nodeIDlist.get(i);
					Query updateNodetrans = session.createSQLQuery("update node_active_transactions set sent_to_alm = '1' where node_pk = :param1");
					updateNodetrans.setParameter("param1", nodeID);
					updateNodetrans.executeUpdate();
				}
				
				for(int i=0; i<transCabinetIDlist.size(); i++)
				{
					String CabinettransID = transCabinetIDlist.get(i);
					Query updateCabinettrans = session.createSQLQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1");
					updateCabinettrans.setParameter("param1", CabinettransID);
					updateCabinettrans.executeUpdate();
				}
				
				for(int i=0; i<transBoardIDlist.size(); i++)
				{
					String boardtransID = transBoardIDlist.get(i);
					Query updateBoardtrans = session.createSQLQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1");
					updateBoardtrans.setParameter("param1", boardtransID);
					updateBoardtrans.executeUpdate();
				}
				
				for(int i=0; i<transAntennaIDlist.size(); i++)
				{
					String AntennatransID = transAntennaIDlist.get(i);
					Query updateAntennatrans = session.createSQLQuery("update node_antenna_transactions set sent_to_alm = '1' where trans_id = :param1");
					updateAntennatrans.setParameter("param1", AntennatransID);
					updateAntennatrans.executeUpdate();
				}
				
				
				
			}
			
			tx.commit();
			session.close();			
			
			
			
		map.put("DeleteTest", "DeleteDone");
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return map;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/transfer", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> transfer(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
	}
		System.out.println("afif transfer");
		
		try {
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			
			ObjectMapper mapper = new ObjectMapper();
			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			
			String getTransferFromSlotToSlotsquery,  getTransferFromSiteToSite, getAntennaTransferToAnotherNode, getCabinetTransfertoNewNode;
			Query q,q2,q3;
			List<Object[]> TransferBoardsList;
			 List<Object[]> TransferFromSiteToSiteList;List<Object[]> TransferCabinetToNewNodeList;
			List<Object[]> TransferAntennaToAnotherNodeList;
			
			getTransferFromSlotToSlotsquery = " select * from (Select nodeBoard.FROM_TRANS_ID, nodeBoardTrans.FROM_SERIAL_NUMBER,nodeBoardTrans.TO_SERIAL_NUMBER,"
					+ "nodeBoard.FROM_TRANS_SOURCE, nodeBoardTrans.from_slot,nodeBoardTrans.to_slot,nodeBoardTrans.FROM_NODE_ID,nodeBoardTrans.TO_NODE_ID,"
					+ "nodeActive.NODE_NAME,nodeBoard.BOARDTYPE, nodeBoard.NODE_PK,nodeBoardTrans.FROM_SITE, nodeBoardTrans.TO_SITE,nodeBoard.ALM_POSITION,"
					+ " nodeBoardTrans.TRANS_DESCRIPTION FROM NODE_BOARD nodeBoard inner join NODE_BOARD_TRANSACTIONS nodeBoardTrans "
					+ "on nodeBoard.FROM_TRANS_ID = nodeBoardTrans.TRANS_ID inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeBoardTrans.NODE_PK"
					+ " where nodeBoard.ACTIVE_RECORD = '1' and ((nodeBoard.TRANS_TYPE = 'Board Movement') OR (nodeBoard.TRANS_TYPE = 'Board movement After Disappear' )"
					+ " OR (nodeBoard.TRANS_TYPE = 'Board Transfer to another Node After Disappear')) and nodeBoardTrans.SENT_TO_ALM = '0') tbl1 "
					+ "right join (select distinct nodeactive.node_id, nodeactive.NODE_NAME as TO_NODE_NAME from NODE_ACTIVE nodeactive "
					+ "inner join NODE_BOARD_TRANSACTIONS nodeboardtrans on nodeboardtrans.TO_NODE_ID = nodeactive.NODE_ID "
					+ "inner join node_board  nodeBoard on nodeBoard.from_trans_id = nodeboardtrans.TRANS_ID  "
					+ "where nodeBoard.ACTIVE_RECORD = '1' and ((nodeBoard.TRANS_TYPE = 'Board Movement')	"
					+ "OR (nodeBoard.TRANS_TYPE = 'Board movement After Disappear' ) OR "
					+ "(nodeBoard.TRANS_TYPE = 'Board Transfer to another Node After Disappear')) and  "
					+ "nodeBoardTrans.SENT_TO_ALM = '0') tbl2 on tbl1.to_node_id = tbl2.node_id";
			
			//getTransferFromSiteToSite = "";
			
		
			 
			 getCabinetTransfertoNewNode = " select * from (Select nodeCabinet.FROM_TRANS_ID, nodeCabinetTrans.FROM_SERIAL_NUMBER,	nodeCabinet.FROM_TRANS_SOURCE, "
			 		+ "nodeCabinetTrans.FROM_NODE_ID, nodeCabinetTrans.TO_NODE_ID, nodeActive.NODE_NAME,nodeCabinet.MODEL, nodeCabinet.NODE_PK,nodeCabinetTrans.FROM_SITE,"
			 		+ " nodeCabinetTrans.TO_SITE, nodeCabinet.ALM_POSITION, nodeCabinetTrans.TRANS_DESCRIPTION FROM NODE_CABINET nodeCabinet "
			 		+ "inner join NODE_CABINET_TRANSACTIONS nodeCabinetTrans	on nodeCabinet.FROM_TRANS_ID = nodeCabinetTrans.TRANS_ID "
			 		+ "inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeCabinetTrans.NODE_PK where nodeCabinet.ACTIVE_RECORD = '1' "
			 		+ "and nodeCabinet.TRANS_TYPE = 'Cabinet transfer to another node' and nodeCabinetTrans.SENT_TO_ALM = '0')tbl1 "
			 		+ "right join (select distinct nodeactive.node_id, nodeactive.NODE_NAME as TO_NODE_NAME  from NODE_ACTIVE nodeactive "
			 		+ "inner join NODE_CABINET_TRANSACTIONS nodecabinettrans  on nodecabinettrans.TO_NODE_ID = nodeactive.NODE_ID "
			 		+ "inner join NODE_CABINET nodecabinet on nodecabinet.FROM_TRANS_ID = nodecabinettrans.TRANS_ID where nodeCabinet.ACTIVE_RECORD = '1' "
			 		+ "and nodeCabinet.TRANS_TYPE = 'Cabinet transfer to another node' and nodeCabinetTrans.SENT_TO_ALM = '0') tbl2 on tbl1.to_node_id = tbl2.node_id";

			 
			 getAntennaTransferToAnotherNode = " select * from (Select nodeAntenna.FROM_TRANS_ID, nodeAntennaTrans.FROM_SERIAL_NUMBER, nodeAntenna.FROM_TRANS_SOURCE,"
			 		+ "nodeAntennaTrans.FROM_NODE_ID, nodeAntennaTrans.TO_NODE_ID, nodeActive.NODE_NAME,nodeAntenna.MODEL, nodeAntenna.NODE_PK,nodeAntennaTrans.FROM_SITE,"
			 		+ "nodeAntennaTrans.TO_SITE,	nodeAntennaTrans.TRANS_DESCRIPTION FROM NODE_ANTENNA nodeAntenna inner join NODE_ANTENNA_TRANSACTIONS nodeAntennaTrans"
			 		+ " on nodeAntenna.FROM_TRANS_ID = nodeAntennaTrans.TRANS_ID inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeAntennaTrans.NODE_PK"
			 		+ " where nodeAntenna.ACTIVE_RECORD = '1' and nodeAntenna.TRANS_TYPE = 'ANTENNA transfer to another node' and nodeAntennaTrans.SENT_TO_ALM = '0')tbl1 "
			 		+ "RIGHT JOIN (select distinct nodeactive.node_id, nodeactive.NODE_NAME as TO_NODE_NAME from NODE_ACTIVE nodeactive "
			 		+ "inner join NODE_ANTENNA_TRANSACTIONS nodeantennatrans on nodeantennatrans.TO_NODE_ID = nodeactive.NODE_ID "
			 		+ "inner join NODE_ANTENNA nodeantenna on nodeantenna.FROM_TRANS_ID = nodeantennatrans.TRANS_ID where nodeantenna.ACTIVE_RECORD = '1'"
			 		+ "and nodeantenna.TRANS_TYPE = 'ANTENNA transfer to another node' and nodeantennaTrans.SENT_TO_ALM = '0')tbl2 on tbl1.to_node_id = tbl2.node_id"; 
			
			
				
			
			q = session.createSQLQuery(getTransferFromSlotToSlotsquery);
			q2 = session.createSQLQuery(getCabinetTransfertoNewNode);
			q3  = session.createSQLQuery(getAntennaTransferToAnotherNode);
			//q3 = session.createSQLQuery(getTransferFromSiteToSite);
			TransferBoardsList = q.list();
			TransferCabinetToNewNodeList = q2.list();
			TransferAntennaToAnotherNodeList = q3.list();
			//TransferBoardsNodeList = q2.list();
			//TransferFromSiteToSiteList = q3.list();
			System.out.println("List of Transfer from slot to slot are: "+mapper.writeValueAsString(TransferBoardsList));
			System.out.println("List of Transfer from Node to Node after disappear are: "+mapper.writeValueAsString(TransferCabinetToNewNodeList));
			System.out.println("List of Transfer from Site to Site: "+mapper.writeValueAsString(TransferAntennaToAnotherNodeList));
			String dnid; DiscoveryNew discoverynew = new DiscoveryNew();
			//dnid = "DND_" + year + "_" + appConfig.getSequenceNbr(6);
			synchronized (this) {						
				dnid = "DND_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
			System.out.println("DNI*********" + dnid);
			
			Date date1;
			
			date1 = new Timestamp(System.currentTimeMillis());
			Timestamp CreationDate = new Timestamp(date1.getTime());
			
			if(TransferBoardsList.size() >0 || TransferCabinetToNewNodeList.size() > 0 || TransferAntennaToAnotherNodeList.size() > 0) 
			{
			discoverynew.setDnID(dnid);
			discoverynew.setDncreationDate(CreationDate);
			discoverynew.setdnlastmodifDate(CreationDate);
			session.saveOrUpdate(discoverynew);
			//appConfig.persist(discoverynew, DiscoveryNew.class);
			
			String DniID; 
			//DsicoveryNewItem discoverynewitem = new DsicoveryNewItem();
			System.out.println("size of Board Array is : "+TransferBoardsList.size());
			//System.out.println("size of BoardNode Array is : "+TransferBoardsNodeList.size());
			String trans_id = "", node_pk = "";
			for(int i=0; i<TransferBoardsList.size(); i++)
			{
				DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
				Object [] arrayList=  TransferBoardsList.get(i);
				//DniID = "DNI_" + year + "_" +appConfig.getSequenceNbr(7);
				synchronized (this) {						
					DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}


				trans_id = arrayList[0].toString();
				node_pk = arrayList[10].toString();

				discoverynewitem.setTransID(trans_id);
				discoverynewitem.setDniID(DniID);
				discoverynewitem.setToSerialNumber(arrayList[1].toString());
				//discoverynewitem.setToSerialNumber(arrayList[2].toString());
				discoverynewitem.setTransSrc(arrayList[3].toString());
				discoverynewitem.setFromSlot(arrayList[4].toString());
				discoverynewitem.setToSlot(arrayList[5].toString());
				discoverynewitem.setNodeID(arrayList[6].toString());
				discoverynewitem.setToNodeId(arrayList[7].toString());
				discoverynewitem.setNodeName(arrayList[8].toString());
				discoverynewitem.setItemModel(arrayList[9].toString());
				discoverynewitem.setNodePK(arrayList[10].toString());
				discoverynewitem.setDniSIte(arrayList[11].toString());
				discoverynewitem.setToSite(arrayList[12].toString());
				discoverynewitem.setPosition(arrayList[13].toString());
				discoverynewitem.setDescription(arrayList[14].toString());
				discoverynewitem.setToNodeName(arrayList[16].toString());
				discoverynewitem.setElementName("Board");
				discoverynewitem.setDniDNID(dnid);
				discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
				discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
				discoverynewitem.setDniAPPROVAL("Operation Manager");
				
				if (StringUtils.equalsIgnoreCase(arrayList[14].toString(), "Board Transfer to another Node After Disappear"))
					discoverynewitem.setTransType("Transfer from Node to Node");
				
					//discoverynewitem.setTransType("New Node on New Hardware");
				
				if (StringUtils.equalsIgnoreCase(arrayList[14].toString(), "Board movement After Disappear"))
					discoverynewitem.setTransType("Transfer from Slot to Slot");
				
				if (StringUtils.equalsIgnoreCase(arrayList[14].toString(), "Position Changed"))
					discoverynewitem.setTransType("Transfer from Slot to Slot");
				
				//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
				session.saveOrUpdate(discoverynewitem);
				
				Query updateNodetrans = session.createSQLQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
				updateNodetrans.setParameter("param1", trans_id);
				updateNodetrans.setParameter("param2", node_pk);
				updateNodetrans.executeUpdate();
			}
			
			
			for(int i=0; i<TransferCabinetToNewNodeList.size(); i++)
			{
				DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
				Object [] arrayList=  TransferCabinetToNewNodeList.get(i);
				//DniID = "DNI_" + year + "_" +appConfig.getSequenceNbr(7);
				synchronized (this) {						
					DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}


				trans_id = arrayList[0].toString();
				node_pk = arrayList[7].toString();

				discoverynewitem.setTransID(trans_id);
				discoverynewitem.setDniID(DniID);
				discoverynewitem.setToSerialNumber(arrayList[1].toString());
				//discoverynewitem.setToSerialNumber(arrayList[2].toString());
				discoverynewitem.setTransSrc(arrayList[2].toString());
				//discoverynewitem.setFromSlot(arrayList[4].toString());
				//discoverynewitem.setToSlot(arrayList[5].toString());
				discoverynewitem.setNodeID(arrayList[3].toString());
				discoverynewitem.setToNodeId(arrayList[4].toString());
				discoverynewitem.setNodeName(arrayList[5].toString());
				discoverynewitem.setItemModel(arrayList[6].toString());
				discoverynewitem.setNodePK(arrayList[7].toString());
				discoverynewitem.setDniSIte(arrayList[8].toString());
				discoverynewitem.setToSite(arrayList[9].toString());
				discoverynewitem.setPosition(arrayList[10].toString());
				discoverynewitem.setDescription(arrayList[11].toString());
				discoverynewitem.setToNodeName(arrayList[13].toString());
				discoverynewitem.setElementName("Cabinet");
				discoverynewitem.setDniDNID(dnid);
				discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
				discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
				discoverynewitem.setDniAPPROVAL("Operation Manager");
				discoverynewitem.setTransType("Transfer from Node to Node Within Site");
				
				//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
				session.saveOrUpdate(discoverynewitem);
				
				Query updateNodetrans = session.createSQLQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
				updateNodetrans.setParameter("param1", trans_id);
				updateNodetrans.setParameter("param2", node_pk);
				updateNodetrans.executeUpdate();
			}
				for(int i=0; i<TransferAntennaToAnotherNodeList.size(); i++)
			{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
				Object [] arrayList=  TransferAntennaToAnotherNodeList.get(i);
				//DniID = "DNI_" + year + "_" +appConfig.getSequenceNbr(7);
				synchronized (this) {						
					DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}


				trans_id = arrayList[0].toString();
				node_pk = arrayList[7].toString();

				discoverynewitem.setTransID(trans_id);
				discoverynewitem.setDniID(DniID);
				discoverynewitem.setToSerialNumber(arrayList[1].toString());
				//discoverynewitem.setToSerialNumber(arrayList[2].toString());
				discoverynewitem.setTransSrc(arrayList[2].toString());
				//discoverynewitem.setFromSlot(arrayList[4].toString());
				//discoverynewitem.setToSlot(arrayList[5].toString());
				discoverynewitem.setNodeID(arrayList[3].toString());
				discoverynewitem.setToNodeId(arrayList[4].toString());
				discoverynewitem.setNodeName(arrayList[5].toString());
				discoverynewitem.setItemModel(arrayList[6].toString());
				discoverynewitem.setNodePK(arrayList[7].toString());
				discoverynewitem.setDniSIte(arrayList[8].toString());
				discoverynewitem.setToSite(arrayList[9].toString());
				//discoverynewitem.setPosition(arrayList[13].toString());
				discoverynewitem.setDescription(arrayList[10].toString());
				discoverynewitem.setToNodeName(arrayList[12].toString());
				discoverynewitem.setElementName("Antenna");
				discoverynewitem.setDniDNID(dnid);
				discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
				discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
				discoverynewitem.setDniAPPROVAL("Operation Manager");
				discoverynewitem.setTransType("Transfer from Node to Node Within Site");
				
				//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
				session.saveOrUpdate(discoverynewitem);
				
				Query updateNodetrans = session.createSQLQuery("update node_antenna_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
				updateNodetrans.setParameter("param1", trans_id);
				updateNodetrans.setParameter("param2", node_pk);
				updateNodetrans.executeUpdate();
			}
			
			/*for(int i=0; i<TransferFromSiteToSiteList.size(); i++)
			{
				
			}*/
			}
			tx.commit();
			session.close();
			map.put("DeleteTest", "DeleteDone");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return map;
	}

	//@SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/replacement", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> replacement(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
	}
		System.out.println("ali replacement");
		
		try {
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			
			ObjectMapper mapper = new ObjectMapper();
			
			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			
			String getReplaceBoards,  getReplaceCabinet, getReplaceAntenna;
			Query q,q2,q3;
			List<Object[]> ReplaceBoardsList;
			List<Object[]> ReplaceCabinetList;
			List<Object[]> ReplaceAntennaList;
			
			getReplaceBoards =  "Select nodeBoard.FROM_TRANS_ID, nodeBoardTrans.FROM_SERIAL_NUMBER,nodeBoardTrans.TO_SERIAL_NUMBER,nodeBoard.FROM_TRANS_SOURCE,nodeBoardTrans.FROM_NODE_ID,nodeBoard.MODEL,"
					+ " nodeBoardTrans.TO_SITE,nodeBoardTrans.TO_SLOT,nodeActive.NODE_NAME,nodeBoard.BOARDTYPE, nodeBoard.NODE_PK,nodeBoardTrans.FROM_SITE, nodeBoard.ALM_POSITION, nodeBoardTrans.TRANS_DESCRIPTION"
					+ " FROM NODE_BOARD nodeBoard inner join NODE_BOARD_TRANSACTIONS nodeBoardTrans on nodeBoard.FROM_TRANS_ID = nodeBoardTrans.TRANS_ID"
					+ "	inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeBoardTrans.NODE_PK where nodeBoard.ACTIVE_RECORD = '1' and"
					+ " nodeBoard.TRANS_TYPE = 'Replacement SN' and nodeBoardTrans.SENT_TO_ALM = '0'";
			
			//getTransferFromSiteToSite = "";
			
		
			 
			getReplaceCabinet = "Select nodeCabinet.FROM_TRANS_ID, nodeCabinetTrans.FROM_SERIAL_NUMBER,nodeCabinetTrans.TO_SERIAL_NUMBER,nodeCabinet.FROM_TRANS_SOURCE,nodeCabinetTrans.FROM_NODE_ID,"
					+ " nodeActive.NODE_NAME,nodeCabinetTrans.TO_SITE,nodeCabinetTrans.TO_SLOT,nodeCabinet.MODEL,nodeCabinet.RACKTYPE, nodeCabinet.NODE_PK,nodeCabinetTrans.FROM_SITE,nodeCabinet.ALM_POSITION,"
					+ "	nodeCabinetTrans.TRANS_DESCRIPTION FROM NODE_CABINET nodeCabinet inner join NODE_CABINET_TRANSACTIONS nodeCabinetTrans"
					+ " on nodeCabinet.FROM_TRANS_ID = nodeCabinetTrans.TRANS_ID inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeCabinetTrans.NODE_PK "
					+ "	where nodeCabinet.ACTIVE_RECORD = '1' and nodeCabinet.TRANS_TYPE = 'Replacement' and nodeCabinetTrans.SENT_TO_ALM = '0'";
			 
			getReplaceAntenna ="Select nodeAntenna.FROM_TRANS_ID, nodeAntennaTrans.FROM_SERIAL_NUMBER,nodeAntennaTrans.TO_SERIAL_NUMBER,nodeAntenna.FROM_TRANS_SOURCE, nodeAntennaTrans.FROM_NODE_ID,"
					+ "nodeAntennaTrans.TO_SITE,nodeAntennaTrans.TO_SLOT,nodeAntenna.ANTENNADEVICETYPE,nodeActive.NODE_NAME, nodeAntenna.MODEL, nodeAntenna.NODE_PK,nodeAntennaTrans.FROM_SITE,"
					+ "nodeAntennaTrans.TRANS_DESCRIPTION FROM NODE_ANTENNA nodeAntenna inner join NODE_ANTENNA_TRANSACTIONS nodeAntennaTrans"
					+ " on nodeAntenna.FROM_TRANS_ID = nodeAntennaTrans.TRANS_ID inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeAntennaTrans.NODE_PK "
					+ "where nodeAntenna.ACTIVE_RECORD = '1' and nodeAntenna.TRANS_TYPE = 'Antenna SN Replacement' and nodeAntennaTrans.SENT_TO_ALM = '0'";
			
				
			
			q = session.createSQLQuery(getReplaceBoards);
			q2 = session.createSQLQuery(getReplaceCabinet);
			q3  = session.createSQLQuery(getReplaceAntenna);
		
			ReplaceBoardsList = q.list();
			ReplaceCabinetList = q2.list();
			ReplaceAntennaList = q3.list();
			//TransferBoardsNodeList = q2.list();
			//TransferFromSiteToSiteList = q3.list();
			System.out.println("List of Replace Bord: "+mapper.writeValueAsString(ReplaceBoardsList));
			System.out.println("List of Replace Cabinet:"+mapper.writeValueAsString(ReplaceCabinetList));
			System.out.println("List of Antenna"+mapper.writeValueAsString(ReplaceAntennaList));
			String dnid; DiscoveryNew discoverynew = new DiscoveryNew();
			//dnid = "DND_" + year + "_" + appConfig.getSequenceNbr(6);
			
			synchronized (this) {						
				dnid = "DND_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
					}


			System.out.println("DNI*********" + dnid);
			
			Date date1;
			
			date1 = new Timestamp(System.currentTimeMillis());
			Timestamp CreationDate = new Timestamp(date1.getTime());
			
			if(ReplaceBoardsList.size() >0 || ReplaceCabinetList.size() > 0 || ReplaceAntennaList.size() > 0) 
			{
				System.out.println("Aliiiiiiii");
			discoverynew.setDnID(dnid);
			discoverynew.setDncreationDate(CreationDate);
			discoverynew.setdnlastmodifDate(CreationDate);
			session.saveOrUpdate(discoverynew);
			//appConfig.persist(discoverynew, DiscoveryNew.class);
			
			String DniID; 
			//DsicoveryNewItem discoverynewitem = new DsicoveryNewItem();
			System.out.println("size of Board Array is : "+ReplaceBoardsList.size());
			//System.out.println("size of BoardNode Array is : "+TransferBoardsNodeList.size());
			String trans_id = "", node_pk = "";
			for(int i=0; i<ReplaceBoardsList.size(); i++)
			{
				DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
				Object [] arrayList=  ReplaceBoardsList.get(i);
				//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
				synchronized (this) {						
					DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
				}
				trans_id = arrayList[0].toString();
				node_pk = arrayList[10].toString();
				
				discoverynewitem.setTransID(trans_id);
				discoverynewitem.setDniID(DniID);
				discoverynewitem.setDniSN(arrayList[1].toString());
				discoverynewitem.setToSerialNumber(arrayList[2].toString());
				discoverynewitem.setToNodeId(arrayList[4].toString());
				discoverynewitem.setItemModel(arrayList[9].toString());
				discoverynewitem.setToSite(arrayList[6].toString());
				discoverynewitem.setToSlot(arrayList[7].toString());
				discoverynewitem.setToNodeName(arrayList[8].toString());
				discoverynewitem.setNodePK(arrayList[10].toString());
				discoverynewitem.setDescription(arrayList[13].toString());	
				discoverynewitem.setElementName("Board");
				discoverynewitem.setDniDNID(dnid);
				discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
				discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
				discoverynewitem.setDniAPPROVAL("Project Manager");
				discoverynewitem.setTransType("Replacement");
				
				
				//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
				session.saveOrUpdate(discoverynewitem);
				
				Query updateBoardReplacement = session.createSQLQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
				updateBoardReplacement.setParameter("param1", trans_id);
				updateBoardReplacement.setParameter("param2", node_pk);
				updateBoardReplacement.executeUpdate();
			}
			
			
			for(int i=0; i<ReplaceCabinetList.size(); i++)
			{
				DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
				Object [] arrayList=  ReplaceCabinetList.get(i);
				synchronized (this) {						
					DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}
				trans_id = arrayList[0].toString();
				node_pk = arrayList[10].toString();

				discoverynewitem.setTransID(trans_id);
				discoverynewitem.setDniID(DniID);
				discoverynewitem.setDniSN(arrayList[1].toString());
				discoverynewitem.setToSerialNumber(arrayList[2].toString());
				discoverynewitem.setTransSrc(arrayList[3].toString());
				discoverynewitem.setToNodeId(arrayList[4].toString());
				discoverynewitem.setToNodeName(arrayList[5].toString());
				discoverynewitem.setToSite(arrayList[6].toString());
				discoverynewitem.setToSlot(arrayList[7].toString());
				discoverynewitem.setItemModel(arrayList[9].toString());
				discoverynewitem.setNodePK(arrayList[10].toString());
				discoverynewitem.setDescription(arrayList[13].toString());	
				discoverynewitem.setElementName("Cabinet");
				discoverynewitem.setDniDNID(dnid);
				discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
				discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
				discoverynewitem.setDniAPPROVAL("Project Manager");
				discoverynewitem.setTransType("Replacement");
				
				
				//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
				session.saveOrUpdate(discoverynewitem);
				
				Query updateCabinetReplacement = session.createSQLQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
				updateCabinetReplacement.setParameter("param1", trans_id);
				updateCabinetReplacement.setParameter("param2", node_pk);
				updateCabinetReplacement.executeUpdate();
			}
				for(int i=0; i<ReplaceAntennaList.size(); i++)
			{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  ReplaceAntennaList.get(i);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[10].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setToSerialNumber(arrayList[2].toString());
					discoverynewitem.setTransSrc(arrayList[3].toString());
					discoverynewitem.setToNodeId(arrayList[4].toString());
					discoverynewitem.setToSite(arrayList[5].toString());
					discoverynewitem.setToSlot(arrayList[6].toString());
					discoverynewitem.setItemModel(arrayList[7].toString());
					discoverynewitem.setToNodeName(arrayList[8].toString());
					discoverynewitem.setNodePK(arrayList[10].toString());
					discoverynewitem.setDescription(arrayList[12].toString());	
					discoverynewitem.setElementName("Antenna");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Project Manager");
					discoverynewitem.setTransType("Replacement");
					
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateAntennaReplacement = session.createSQLQuery("update node_antenna_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
					updateAntennaReplacement.setParameter("param1", trans_id);
					updateAntennaReplacement.setParameter("param2", node_pk);
					updateAntennaReplacement.executeUpdate();
			}
			

			}
			tx.commit();
			session.close();
			map.put("DeleteTest", "DeleteDone");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return map;
	}

	




	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/disappear", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> disappear(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
	}
		System.out.println("disappear");
		try {
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			
			ObjectMapper mapper = new ObjectMapper();
			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			
			String getBoardDisappear,getBoardDisappearForTransfer, getBoardDisappearForMaintenance, getCabinetDisappear,
			getCabinetDisappearForTransfer, getCabinetDisappearForMaintenance, getAntennaDisappear, getAntennaDisappearForTransfer, getNodeDisappear; 
			Query q,q1,q2,q3,q4,q5,q6,q7,q8;
			List<Object[]> boardDisappearList;List<Object[]> cabinetDisappearList;List<Object[]> antennaDisappearList;
			List<Object[]> nodeDisappearList;List<Object[]> boardDisappearForTransferList;List<Object[]> boardDisappearForMaintenanceList;
			List<Object[]> cabinetDisappearForTransferList;List<Object[]> cabientDisappearForMaintenanceList;
			List<Object[]> antennaDisappearForTransferList;
			
			getBoardDisappear = "Select nodeBoard.FROM_TRANS_ID, nodeBoardTrans.FROM_SERIAL_NUMBER,nodeBoard.FROM_TRANS_SOURCE, nodeBoardTrans.FROM_NODE_ID,"
					+ " nodeActive.NODE_NAME,nodeBoard.BOARDTYPE, nodeBoard.NODE_PK,nodeBoardTrans.FROM_SITE, nodeBoard.ALM_POSITION, nodeBoardTrans.TRANS_DESCRIPTION"
					+ " FROM NODE_BOARD nodeBoard inner join NODE_BOARD_TRANSACTIONS nodeBoardTrans on nodeBoard.FROM_TRANS_ID = nodeBoardTrans.TRANS_ID"
					+ "	inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeBoardTrans.NODE_PK where nodeBoard.ACTIVE_RECORD = '1' and"
					+ " nodeBoard.TRANS_TYPE = 'Board Disappeared' and nodeBoardTrans.SENT_TO_ALM = '0'";
		
			
			
			getBoardDisappearForTransfer = "Select nodeBoard.FROM_TRANS_ID, nodeBoardTrans.FROM_SERIAL_NUMBER,nodeBoard.FROM_TRANS_SOURCE, nodeBoardTrans.FROM_NODE_ID,"
					+ " nodeActive.NODE_NAME,nodeBoard.BOARDTYPE, nodeBoard.NODE_PK,nodeBoardTrans.FROM_SITE,nodeBoard.ALM_POSITION, nodeBoardTrans.TRANS_DESCRIPTION"
					+ " FROM NODE_BOARD nodeBoard inner join NODE_BOARD_TRANSACTIONS nodeBoardTrans on nodeBoard.FROM_TRANS_ID = nodeBoardTrans.TRANS_ID"
					+ " inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeBoardTrans.NODE_PK where nodeBoard.TO_TRANS_ID "
					+ "in (select nodeBoardTrans.TRANS_ID from NODE_BOARD nodeBoard inner join NODE_BOARD_TRANSACTIONS nodeBoardTrans "
					+ "on nodeBoardTrans.TRANS_ID = nodeBoard.FROM_TRANS_ID where nodeBoard.ACTIVE_RECORD = '1' "
					+ "and ((nodeBoardTrans.TRANS_TYPE = 'Board Transfer to another Node After Disappear') or "
					+ "(nodeBoardTrans.TRANS_TYPE = 'Board movement After Disappear'))) and nodeBoard.ACTIVE_RECORD = '0' and "
					+ "nodeBoard.TRANS_TYPE = 'Board Disappeared' and nodeBoardTrans.SENT_TO_ALM = '0'";
			
		
			
			getBoardDisappearForMaintenance = "Select nodeBoard.FROM_TRANS_ID, nodeBoardTrans.FROM_SERIAL_NUMBER,nodeBoard.FROM_TRANS_SOURCE, nodeBoardTrans.FROM_NODE_ID,"
					+ " nodeActive.NODE_NAME,nodeBoard.BOARDTYPE, nodeBoard.NODE_PK,nodeBoardTrans.FROM_SITE, nodeBoard.ALM_POSITION,nodeBoardTrans.TRANS_DESCRIPTION"
					+ " FROM NODE_BOARD nodeBoard inner join NODE_BOARD_TRANSACTIONS nodeBoardTrans on nodeBoard.FROM_TRANS_ID = nodeBoardTrans.TRANS_ID"
					+ " inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeBoardTrans.NODE_PK	where nodeBoard.TO_TRANS_ID "
					+ "in (select nodeBoardTrans.TRANS_ID from NODE_BOARD nodeBoard inner join NODE_BOARD_TRANSACTIONS nodeBoardTrans "
					+ "on nodeBoardTrans.TRANS_ID = nodeBoard.FROM_TRANS_ID where nodeBoard.ACTIVE_RECORD = '1' "
					+ "and nodeBoardTrans.TRANS_TYPE = 'Maintenance After Disappear') and nodeBoard.ACTIVE_RECORD = '0' "
					+ "and nodeBoard.TRANS_TYPE = 'Board Disappeared' and nodeBoardTrans.SENT_TO_ALM = '0'";


			
			
			getCabinetDisappear = "Select nodeCabinet.FROM_TRANS_ID, nodeCabinet.SERIALNUMBER,nodeCabinet.FROM_TRANS_SOURCE,nodeCabinetTrans.FROM_NODE_ID,"
					+ " nodeActive.NODE_NAME,nodeCabinet.MODEL, nodeCabinet.NODE_PK,nodeCabinetTrans.FROM_SITE,nodeCabinet.ALM_POSITION,"
					+ "	nodeCabinetTrans.TRANS_DESCRIPTION FROM NODE_CABINET nodeCabinet inner join NODE_CABINET_TRANSACTIONS nodeCabinetTrans"
					+ " on nodeCabinet.FROM_TRANS_ID = nodeCabinetTrans.TRANS_ID inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeCabinetTrans.NODE_PK "
					+ "	where nodeCabinet.ACTIVE_RECORD = '1' and nodeCabinet.TRANS_TYPE = 'Cabinet Disappeared' and nodeCabinetTrans.SENT_TO_ALM = '0'";
		
			
			getCabinetDisappearForTransfer = "Select nodeCabinet.FROM_TRANS_ID, nodeCabinetTrans.FROM_SERIAL_NUMBER,nodeCabinet.FROM_TRANS_SOURCE,"
					+ "nodeCabinetTrans.FROM_NODE_ID, nodeActive.NODE_NAME,nodeCabinet.MODEL, nodeCabinet.NODE_PK,nodeCabinetTrans.FROM_SITE,nodeCabinet.ALM_POSITION,"
					+ "nodeCabinetTrans.TRANS_DESCRIPTION FROM NODE_CABINET nodeCabinet inner join NODE_CABINET_TRANSACTIONS nodeCabinetTrans"
					+ " on nodeCabinet.FROM_TRANS_ID = nodeCabinetTrans.TRANS_ID inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeCabinetTrans.NODE_PK"
					+ "	where nodeCabinet.TO_TRANS_ID in (select nodeCabinetTrans.TRANS_ID from NODE_CABINET nodeCabinet "
					+ "inner join NODE_CABINET_TRANSACTIONS nodeCabinetTrans on nodeCabinetTrans.TRANS_ID = nodeCabinet.FROM_TRANS_ID "
					+ "where nodeCabinet.ACTIVE_RECORD = '1' and nodeCabinetTrans.TRANS_TYPE = 'Cabinet transfer to another node')	"
					+ "and nodeCabinet.ACTIVE_RECORD = '0' and nodeCabinet.TRANS_TYPE = 'Cabinet Disappeared' and nodeCabinetTrans.SENT_TO_ALM = '0'";


			
			getCabinetDisappearForMaintenance = "Select nodeCabinet.FROM_TRANS_ID, nodeCabinetTrans.FROM_SERIAL_NUMBER,nodeCabinet.FROM_TRANS_SOURCE,"
					+ " nodeCabinetTrans.FROM_NODE_ID, nodeActive.NODE_NAME,nodeCabinet.MODEL, nodeCabinet.NODE_PK,nodeCabinetTrans.FROM_SITE,nodeCabinet.ALM_POSITION,"
					+ " nodeCabinetTrans.TRANS_DESCRIPTION FROM NODE_CABINET nodeCabinet inner join NODE_CABINET_TRANSACTIONS nodeCabinetTrans "
					+ "on nodeCabinet.FROM_TRANS_ID = nodeCabinetTrans.TRANS_ID	inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeCabinetTrans.NODE_PK"
					+ " where nodeCabinet.TO_TRANS_ID in (select nodeCabinetTrans.TRANS_ID from NODE_CABINET nodeCabinet "
					+ "inner join NODE_CABINET_TRANSACTIONS nodeCabinetTrans on nodeCabinetTrans.TRANS_ID = nodeCabinet.FROM_TRANS_ID "
					+ "where nodeCabinet.ACTIVE_RECORD = '1' and nodeCabinet.TRANS_TYPE = 'Maintenance') and nodeCabinet.ACTIVE_RECORD = '0' "
					+ "and nodeCabinet.TRANS_TYPE = 'Cabinet Reappeared' and nodeCabinetTrans.SENT_TO_ALM = '0'";

		
			
			getAntennaDisappear = "Select nodeAntenna.FROM_TRANS_ID, nodeAntenna.SERIALNUMBER,nodeAntenna.FROM_TRANS_SOURCE, nodeAntennaTrans.FROM_NODE_ID,"
					+ " nodeAntennaTrans.TO_NODE_ID,nodeActive.NODE_NAME, nodeAntenna.MODEL, nodeAntenna.NODE_PK,nodeAntennaTrans.FROM_SITE,nodeAntennaTrans.TO_SITE,"
					+ "nodeAntennaTrans.TRANS_DESCRIPTION FROM NODE_ANTENNA nodeAntenna inner join NODE_ANTENNA_TRANSACTIONS nodeAntennaTrans"
					+ " on nodeAntenna.FROM_TRANS_ID = nodeAntennaTrans.TRANS_ID inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeAntennaTrans.NODE_PK "
					+ "where nodeAntenna.ACTIVE_RECORD = '1' and nodeAntenna.TRANS_TYPE = 'Disappear' and nodeAntennaTrans.SENT_TO_ALM = '0'";
			
		
			
			getAntennaDisappearForTransfer = "Select nodeAntenna.FROM_TRANS_ID, nodeAntennaTrans.FROM_SERIAL_NUMBER,nodeAntenna.FROM_TRANS_SOURCE,"
					+ " nodeAntennaTrans.FROM_NODE_ID, nodeActive.NODE_NAME,nodeAntenna.MODEL, nodeAntenna.NODE_PK,nodeAntennaTrans.FROM_SITE, "
					+ "nodeAntennaTrans.TRANS_DESCRIPTION FROM NODE_ANTENNA nodeAntenna inner join NODE_ANTENNA_TRANSACTIONS nodeAntennaTrans "
					+ "on nodeAntenna.FROM_TRANS_ID = nodeAntennaTrans.TRANS_ID inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeAntennaTrans.NODE_PK"
					+ " where nodeAntenna.TO_TRANS_ID in (select nodeAntennaTrans.TRANS_ID from NODE_ANTENNA nodeAntenna "
					+ "inner join NODE_ANTENNA_TRANSACTIONS nodeAntennaTrans on nodeAntennaTrans.TRANS_ID = nodeAntenna.FROM_TRANS_ID "
					+ "where nodeAntenna.ACTIVE_RECORD = '1' and nodeAntenna.TRANS_TYPE = 'ANTENNA transfer to another node') and nodeAntenna.ACTIVE_RECORD = '0'"
					+ " and nodeAntenna.TRANS_TYPE = 'Disappear' and nodeAntennaTrans.SENT_TO_ALM = '0'";

			/*getNodeDisappear =" Select nodeActive.FROM_TRANS_ID, nodeActive.TRANS_SOURCE,nodeActive.NODE_ID, nodeActive.NODE_NAME,nodeActive.NODE_TYPE,"
					+ " nodeActive.NODE_PK,  nodeActiveTrans.TRANS_DESCRIPTION FROM NODE_ACTIVE nodeActive inner join NODE_ACTIVE_TRANSACTIONS nodeActiveTrans"
					+ " on nodeActive.FROM_TRANS_ID = nodeActiveTrans.TRANS_ID where nodeActive.ACTIVE_RECORD = '1' and nodeActive.TRANS_TYPE = 'Node Disappeared'"
					+ " and nodeActiveTrans.SENT_TO_ALM = '0'";*/

			
			q = session.createSQLQuery(getBoardDisappear);
			q1 = session.createSQLQuery(getCabinetDisappear);
			q2 = session.createSQLQuery(getAntennaDisappear);
			//q3 = session.createSQLQuery(getNodeDisappear);
			q4 = session.createSQLQuery(getBoardDisappearForTransfer);
			q5 = session.createSQLQuery(getBoardDisappearForMaintenance);
			q6 = session.createSQLQuery(getCabinetDisappearForTransfer);
			q7 = session.createSQLQuery(getCabinetDisappearForMaintenance);
			q8 = session.createSQLQuery(getAntennaDisappearForTransfer);
			
			boardDisappearList = q.list();
			cabinetDisappearList = q1.list();
			antennaDisappearList = q2.list();
			//nodeDisappearList = q3.list();
			boardDisappearForTransferList = q4.list();
			boardDisappearForMaintenanceList = q5.list();
			cabinetDisappearForTransferList = q6.list();
			cabientDisappearForMaintenanceList = q7.list();
			antennaDisappearForTransferList = q8.list();
			
			
			
			System.out.println("List of Board Disappear "+mapper.writeValueAsString(boardDisappearList));
			System.out.println("List of Cabient Disappear "+mapper.writeValueAsString(cabinetDisappearList));
			System.out.println("List of Antenna Disappear "+mapper.writeValueAsString(antennaDisappearList));
			//System.out.println("List of Node Disappear "+mapper.writeValueAsString(nodeDisappearList));
			System.out.println("List of Node Disappear "+mapper.writeValueAsString(boardDisappearForTransferList));
			
			String dnid; DiscoveryNew discoverynew = new DiscoveryNew();
			//dnid = "DND_" + year + "_" + appConfig.getSequenceNbr(6);
			synchronized (this) {						
				dnid = "DND_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
					}
			System.out.println("DNI*********" + dnid);
			
			Date date1;
			
			date1 = new Timestamp(System.currentTimeMillis());
			Timestamp CreationDate = new Timestamp(date1.getTime());
			if(boardDisappearList.size() >0 || cabinetDisappearList.size() > 0 || antennaDisappearList.size() > 0 || boardDisappearForTransferList.size() > 0 || boardDisappearForMaintenanceList.size() > 0 || cabinetDisappearForTransferList.size() > 0 || cabientDisappearForMaintenanceList.size() > 0 || antennaDisappearForTransferList.size() > 0)
			{
				discoverynew.setDnID(dnid);
				discoverynew.setDncreationDate(CreationDate);
				discoverynew.setdnlastmodifDate(CreationDate);
				session.saveOrUpdate(discoverynew);
				//appConfig.persist(discoverynew, DiscoveryNew.class);
				
				String DniID; 
				//DsicoveryNewItem discoverynewitem = new DsicoveryNewItem();
				System.out.println("size of Board Array is : "+boardDisappearList.size());
				String trans_id = "", node_pk = "";
				for(int i=0; i<boardDisappearList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  boardDisappearList.get(i);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					//discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setToSerialNumber(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					//discoverynewitem.setNodeID(arrayList[3].toString());
					//discoverynewitem.setNodeName(arrayList[4].toString());
					discoverynewitem.setToNodeId(arrayList[3].toString());
					discoverynewitem.setToNodeName(arrayList[4].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
					discoverynewitem.setNodePK(arrayList[6].toString());
					//discoverynewitem.setDniSIte(arrayList[7].toString());
					discoverynewitem.setPosition(arrayList[8].toString());
					//discoverynewitem.setToSlot(arrayList[8].toString());
					discoverynewitem.setDescription(arrayList[9].toString());
					discoverynewitem.setElementName("Board");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("");
					discoverynewitem.setTransType("Disappear");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createSQLQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
					updateNodetrans.setParameter("param1", trans_id);
					updateNodetrans.setParameter("param2", node_pk);
					updateNodetrans.executeUpdate();
				}
				
				for(int i=0; i<cabinetDisappearList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  cabinetDisappearList.get(i);
					//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					//discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setToSerialNumber(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					//discoverynewitem.setFromSlot(arrayList[4].toString());
					discoverynewitem.setToSlot(arrayList[8].toString());
					//discoverynewitem.setNodeID(arrayList[3].toString());
					discoverynewitem.setToNodeId(arrayList[3].toString());
					//discoverynewitem.setNodeName(arrayList[4].toString());
					discoverynewitem.setToNodeName(arrayList[4].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
					discoverynewitem.setNodePK(arrayList[6].toString());
					discoverynewitem.setDniSIte(arrayList[7].toString());
					//discoverynewitem.setToSite(arrayList[12].toString());
					discoverynewitem.setPosition(arrayList[8].toString());
					discoverynewitem.setDescription(arrayList[9].toString());
					discoverynewitem.setElementName("Cabinet");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("");
					discoverynewitem.setTransType("Disappear");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createSQLQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
					updateNodetrans.setParameter("param1", trans_id);
					updateNodetrans.setParameter("param2", node_pk);
					updateNodetrans.executeUpdate();
				}
				
				
				for(int i=0; i<antennaDisappearList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  antennaDisappearList.get(i);
					//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[7].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					//discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setToSerialNumber(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					//discoverynewitem.setFromSlot(arrayList[4].toString());
					//discoverynewitem.setToSlot(arrayList[5].toString());
					//discoverynewitem.setNodeID(arrayList[6].toString());
					discoverynewitem.setToNodeId(arrayList[3].toString());
					discoverynewitem.setNodeName(arrayList[5].toString());
					discoverynewitem.setItemModel(arrayList[6].toString());
					discoverynewitem.setNodePK(arrayList[7].toString());
					discoverynewitem.setDniSIte(arrayList[8].toString());
					discoverynewitem.setToSite(arrayList[9].toString());
					//discoverynewitem.setPosition(arrayList[13].toString());
					discoverynewitem.setDescription(arrayList[10].toString());
					discoverynewitem.setElementName("Antenna");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("");
					discoverynewitem.setTransType("Disappear");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createSQLQuery("update node_antenna_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
					updateNodetrans.setParameter("param1", trans_id);
					updateNodetrans.setParameter("param2", node_pk);
					updateNodetrans.executeUpdate();
				}
				
				for(int i=0; i<boardDisappearForTransferList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  boardDisappearForTransferList.get(i);
					//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setNodeID(arrayList[3].toString());
					discoverynewitem.setNodeName(arrayList[4].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
					discoverynewitem.setNodePK(arrayList[6].toString());
					discoverynewitem.setDniSIte(arrayList[7].toString());
					discoverynewitem.setPosition(arrayList[8].toString());
					discoverynewitem.setDescription(arrayList[9].toString());
					discoverynewitem.setElementName("Board");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Operation Manager");
					discoverynewitem.setTransType("Disappear for Transfer");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createSQLQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
					updateNodetrans.setParameter("param1", trans_id);
					updateNodetrans.setParameter("param2", node_pk);
					updateNodetrans.executeUpdate();
				}
				
				for(int i=0; i<boardDisappearForMaintenanceList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  boardDisappearForMaintenanceList.get(i);
					//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setNodeID(arrayList[3].toString());
					discoverynewitem.setNodeName(arrayList[4].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
					discoverynewitem.setNodePK(arrayList[6].toString());
					discoverynewitem.setDniSIte(arrayList[7].toString());
					discoverynewitem.setPosition(arrayList[8].toString());
					discoverynewitem.setDescription(arrayList[9].toString());
					discoverynewitem.setElementName("Board");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Operation Manager");
					discoverynewitem.setTransType("Disappear for Maintenance");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createSQLQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
					updateNodetrans.setParameter("param1", trans_id);
					updateNodetrans.setParameter("param2", node_pk);
					updateNodetrans.executeUpdate();
				}
				
				for(int i=0; i<cabinetDisappearForTransferList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  cabinetDisappearForTransferList.get(i);
					//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setNodeID(arrayList[3].toString());
					discoverynewitem.setNodeName(arrayList[4].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
					discoverynewitem.setNodePK(arrayList[6].toString());
					discoverynewitem.setDniSIte(arrayList[7].toString());
					discoverynewitem.setPosition(arrayList[8].toString());
					discoverynewitem.setDescription(arrayList[9].toString());
					discoverynewitem.setElementName("Cabinet");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Operation Manager");
					discoverynewitem.setTransType("Disappear for Transfer");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createSQLQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
					updateNodetrans.setParameter("param1", trans_id);
					updateNodetrans.setParameter("param2", node_pk);
					updateNodetrans.executeUpdate();
				}
				
				for(int i=0; i<cabientDisappearForMaintenanceList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  cabientDisappearForMaintenanceList.get(i);
					//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setNodeID(arrayList[3].toString());
					discoverynewitem.setNodeName(arrayList[4].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
					discoverynewitem.setNodePK(arrayList[6].toString());
					discoverynewitem.setDniSIte(arrayList[7].toString());
					discoverynewitem.setPosition(arrayList[8].toString());
					discoverynewitem.setDescription(arrayList[9].toString());
					discoverynewitem.setElementName("Cabinet");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Operation Manager");
					discoverynewitem.setTransType("Disappear for Maintenance");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createSQLQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
					updateNodetrans.setParameter("param1", trans_id);
					updateNodetrans.setParameter("param2", node_pk);
					updateNodetrans.executeUpdate();
				}
				
				for(int i=0; i<antennaDisappearForTransferList.size(); i++)
				{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  antennaDisappearForTransferList.get(i);
					//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setNodeID(arrayList[3].toString());
					discoverynewitem.setNodeName(arrayList[4].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
					discoverynewitem.setNodePK(arrayList[6].toString());
					discoverynewitem.setDniSIte(arrayList[7].toString());
					//discoverynewitem.setPosition(arrayList[8].toString());
					discoverynewitem.setDescription(arrayList[8].toString());
					discoverynewitem.setElementName("Antenna");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Operation Manager");
					discoverynewitem.setTransType("Disappear for Transfer");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createSQLQuery("update node_antenna_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
					updateNodetrans.setParameter("param1", trans_id);
					updateNodetrans.setParameter("param2", node_pk);
					updateNodetrans.executeUpdate();
				}
			}
		tx.commit();
		session.close();
		map.put("DeleteTest", "DeleteDone");
	}catch(Exception e) {
		System.out.println(e.getMessage());
	}
	
	return map;
}
			


@SuppressWarnings("unchecked")
@RequestMapping(value = "/maintenance", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> maintenance(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
	}
		System.out.println("maintenance");
		try {
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			
			ObjectMapper mapper = new ObjectMapper();
			
			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();
			
			String getBoardMaintenance, getCabinetMaintenance, getAntennaMaintenance ; 
			Query q,q1,q2;
			List<Object[]> boardMaintenenceList;
                        List<Object[]> cabinetMaintenenceList;
                        List<Object[]> antennaMaintenenceList;
			





getBoardMaintenance =" Select nodeBoard.FROM_TRANS_ID, nodeBoardTrans.FROM_SERIAL_NUMBER,nodeBoard.TRANS_SOURCE, nodeBoardTrans.FROM_NODE_ID, "
					+ "nodeActive.NODE_NAME,nodeBoard.BOARDTYPE, nodeBoard.NODE_PK,nodeBoardTrans.FROM_SITE, nodeBoard.ALM_POSITION, nodeBoardTrans.TRANS_DESCRIPTION"
					+ " FROM NODE_BOARD nodeBoard inner join NODE_BOARD_TRANSACTIONS nodeBoardTrans on nodeBoard.FROM_TRANS_ID = nodeBoardTrans.TRANS_ID "
					+ "inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeBoardTrans.NODE_PK where nodeBoard.ACTIVE_RECORD = '1' "
					+ "and nodeBoard.TRANS_TYPE = 'Maintenance After Disappear' and nodeBoardTrans.SENT_TO_ALM = '0'";



getCabinetMaintenance = "Select nodeCabinet.FROM_TRANS_ID, nodeCabinetTrans.FROM_SERIAL_NUMBER,nodeCabinetTrans.TO_SERIAL_NUMBER,nodeCabinet.TRANS_SOURCE,"
					+ " nodeCabinetTrans.from_slot,nodeCabinetTrans.to_slot,nodeCabinetTrans.FROM_NODE_ID, nodeCabinetTrans.TO_NODE_ID, nodeActive.NODE_NAME,"
					+ "nodeCabinet.MODEL, nodeCabinet.NODE_PK,nodeCabinetTrans.FROM_SITE, nodeCabinetTrans.TO_SITE,nodeCabinet.ALM_POSITION, "
					+ "nodeCabinetTrans.TRANS_DESCRIPTION FROM NODE_CABINET nodeCabinet inner join NODE_CABINET_TRANSACTIONS nodeCabinetTrans "
					+ "on nodeCabinet.FROM_TRANS_ID = nodeCabinetTrans.TRANS_ID inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeCabinetTrans.NODE_PK "
					+ "where nodeCabinet.ACTIVE_RECORD = '1' and nodeCabinet.TRANS_TYPE = 'Maintenance' and nodeCabinetTrans.SENT_TO_ALM = '0'";
			
getAntennaMaintenance = "Select nodeAntenna.FROM_TRANS_ID, nodeAntennaTrans.FROM_SERIAL_NUMBER,nodeAntennaTrans.TO_SERIAL_NUMBER,nodeAntenna.TRANS_SOURCE, "
					+ "nodeAntennaTrans.from_slot,nodeAntennaTrans.to_slot,nodeAntennaTrans.FROM_NODE_ID, nodeAntennaTrans.TO_NODE_ID,nodeActive.NODE_NAME,"
					+ "nodeAntenna.MODEL, nodeAntenna.NODE_PK,nodeAntennaTrans.FROM_SITE,nodeAntennaTrans.TO_SITE,nodeAntennaTrans.TRANS_DESCRIPTION"
					+ " FROM NODE_ANTENNA nodeAntenna inner join NODE_ANTENNA_TRANSACTIONS nodeAntennaTrans on nodeAntenna.FROM_TRANS_ID = nodeAntennaTrans.TRANS_ID"
					+ " inner join NODE_ACTIVE nodeActive on nodeActive.NODE_PK = nodeAntennaTrans.NODE_PK where nodeAntenna.ACTIVE_RECORD = '1' "
					+ "and nodeAntenna.TRANS_TYPE = 'Maintenance' and nodeAntennaTrans.SENT_TO_ALM = '0'";
			





                        q = session.createSQLQuery(getBoardMaintenance);
			q1 = session.createSQLQuery(getCabinetMaintenance);
			q2 = session.createSQLQuery(getAntennaMaintenance);
			
			boardMaintenenceList = q.list();
			cabinetMaintenenceList = q1.list();
			antennaMaintenenceList = q2.list();
			


                       System.out.println("List of Board Disappear "+mapper.writeValueAsString(boardMaintenenceList));
		       System.out.println("List of Cabient Disappear "+mapper.writeValueAsString(cabinetMaintenenceList));
		       System.out.println("List of Antenna Disappear "+mapper.writeValueAsString(antennaMaintenenceList));



                       String dnid; DiscoveryNew discoverynew = new DiscoveryNew();
			//dnid = "DND_" + year + "_" + appConfig.getSequenceNbr(6);
			synchronized (this) {						
				dnid = "DND_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
					}
			System.out.println("DNI*********" + dnid);
			
			Date date1;
			
			date1 = new Timestamp(System.currentTimeMillis());
			Timestamp CreationDate = new Timestamp(date1.getTime());
			if(boardMaintenenceList.size() >0 || cabinetMaintenenceList.size() > 0 || antennaMaintenenceList.size() > 0 )
			{
				discoverynew.setDnID(dnid);
				discoverynew.setDncreationDate(CreationDate);
				discoverynew.setdnlastmodifDate(CreationDate);
				session.saveOrUpdate(discoverynew);
				//appConfig.persist(discoverynew, DiscoveryNew.class);
				
				String DniID; 
				//DsicoveryNewItem discoverynewitem = new DsicoveryNewItem();
				System.out.println("size of Board Array is : "+boardMaintenenceList.size());
				String trans_id = "", node_pk = "";




                       for(int i=0; i<boardMaintenenceList.size(); i++)
				{
                    	   DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  boardMaintenenceList.get(i);
					//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}

					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setNodeID(arrayList[3].toString());
					discoverynewitem.setNodeName(arrayList[4].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
					discoverynewitem.setNodePK(arrayList[6].toString());
					discoverynewitem.setDniSIte(arrayList[7].toString());
					discoverynewitem.setPosition(arrayList[8].toString());
					discoverynewitem.setDescription(arrayList[9].toString());
					discoverynewitem.setElementName("Board");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Operation Manager");
					discoverynewitem.setTransType("Maintenace");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createSQLQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
					updateNodetrans.setParameter("param1", trans_id);
					updateNodetrans.setParameter("param2", node_pk);
					updateNodetrans.executeUpdate();
				}




			for(int i=0; i<cabinetMaintenenceList.size(); i++)
				{
				DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  cabinetMaintenenceList.get(i);
					//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}

					trans_id = arrayList[0].toString();
					node_pk = arrayList[10].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setToSerialNumber(arrayList[2].toString());
					discoverynewitem.setTransSrc(arrayList[3].toString());
					discoverynewitem.setFromSlot(arrayList[4].toString());
					discoverynewitem.setToSlot(arrayList[5].toString());
					discoverynewitem.setNodeID(arrayList[6].toString());
					discoverynewitem.setToNodeId(arrayList[7].toString());
					discoverynewitem.setNodeName(arrayList[8].toString());
					discoverynewitem.setItemModel(arrayList[9].toString());
					discoverynewitem.setNodePK(arrayList[10].toString());
					discoverynewitem.setDniSIte(arrayList[11].toString());
					discoverynewitem.setToSite(arrayList[12].toString());
					discoverynewitem.setPosition(arrayList[13].toString());
					discoverynewitem.setDescription(arrayList[14].toString());
					discoverynewitem.setElementName("Cabinet");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Operation Manager");
					discoverynewitem.setTransType("Maintenace");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createSQLQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
					updateNodetrans.setParameter("param1", trans_id);
					updateNodetrans.setParameter("param2", node_pk);
					updateNodetrans.executeUpdate();
				}
			




                         for(int i=0; i<antennaMaintenenceList.size(); i++)
				{
                        	 DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  antennaMaintenenceList.get(i);
					//DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}

					trans_id = arrayList[0].toString();
					node_pk = arrayList[10].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setToSerialNumber(arrayList[2].toString());
					discoverynewitem.setTransSrc(arrayList[3].toString());
					discoverynewitem.setFromSlot(arrayList[4].toString());
					discoverynewitem.setToSlot(arrayList[5].toString());
					discoverynewitem.setNodeID(arrayList[6].toString());
					discoverynewitem.setToNodeId(arrayList[7].toString());
					discoverynewitem.setNodeName(arrayList[8].toString());
					discoverynewitem.setItemModel(arrayList[9].toString());
					discoverynewitem.setNodePK(arrayList[10].toString());
					discoverynewitem.setDniSIte(arrayList[11].toString());
					discoverynewitem.setToSite(arrayList[12].toString());
					discoverynewitem.setDescription(arrayList[13].toString());
					discoverynewitem.setElementName("Antenna");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Operation Manager");
					discoverynewitem.setTransType("Maintenace");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createSQLQuery("update node_antenna_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
					updateNodetrans.setParameter("param1", trans_id);
					updateNodetrans.setParameter("param2", node_pk);
					updateNodetrans.executeUpdate();
				

                             	}
			
			}
		tx.commit();
		session.close();
		map.put("DeleteTest", "DeleteDone");
	}catch(Exception e) {
		System.out.println(e.getMessage());
	}
	
	return map;
}





// APPROVED BY project manager or asset unit
public void ApprovalProjectandAsset(String trans_Type, String getApproval, String dnStatus, String AssetRegID, String ArCode, String PurchaseOrId, String itmcode, String itmname, String WorkOrder, String DniID, String toSiteID, String supplierID, String supplierName, String towareID, String towareName, String serialnb, float dnRate, String itemModel, String itemPartNb, String toSite, String toSerialNumber, String toNodeId, String toNodeName, String toSlot, String nodeID, String nodeName, String Site, String fromSlot, String SiteID) {
	
	Query getpoItemID = session.createSQLQuery("select PO_ITEM_ID from PURCHASE_ORDER_ITEM where ITEM_CODE = '"+itmcode+"' and PO_ID = '"+PurchaseOrId+"'");
	getpoItemID.executeUpdate();
	String poItemID = (String) getpoItemID.uniqueResult();
	
	AssetNewNode(trans_Type, getApproval, dnStatus,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,WorkOrder,DniID,toSiteID,supplierID,supplierName,towareID,towareName,serialnb,dnRate,itemModel,itemPartNb,toSite,toSerialNumber,toNodeId,toNodeName,toSlot,nodeID,nodeName,Site,fromSlot,SiteID);
	

	if (AssetRegID == null) {

		String AR_Site_ID;
		//ArCode = "AR_" + year + "_" + appConfig.getSequenceNbr(9);
		synchronized (this) {						
			ArCode = "AR_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
			query = session.createSQLQuery("UPDATE SEQ_TABLE SET ASSET_REGISTRY = ASSET_REGISTRY + 1 ");
			query.executeUpdate();
			session.createSQLQuery("commit").executeUpdate();
			AR_Site_ID = "ARSITE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
			query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_SITE = AR_SITE + 1 ");
			query.executeUpdate();
			session.createSQLQuery("commit").executeUpdate();
			}
    	//String AR_Site_ID = "AR_SITE_" + year + "_"  + appConfig.getSequenceNbr(30);
	
    	
		// ADD DiscoveryNewItem TO ASSET REGISTRY TABLE

    	AssetRegistry assetregistry = new AssetRegistry();
		assetregistry.setArID(ArCode);
		assetregistry.setAritemCode(itmcode);
		assetregistry.setArcreatedDate(new Timestamp(System.currentTimeMillis()));
		assetregistry.setArlastModifiedDate(new Timestamp(System.currentTimeMillis()));
		assetregistry.setAritemName(itmname);
		assetregistry.setArdniID(DniID);
		assetregistry.setPoID(PurchaseOrId);
		assetregistry.setSupplierID(supplierID);
		assetregistry.setSupplierName(supplierName);
		assetregistry.setItemModel(itemModel);
		assetregistry.setItemPartNumber(itemPartNb);
		assetregistry.setItemSN(toSerialNumber);
		assetregistry.setNodeID(toNodeId);
		assetregistry.setNodeName(toNodeName);
		assetregistry.setArStatus("Running");
		assetregistry.setPoItemId(poItemID);
		assetregistry.setArsiteId(AR_Site_ID);
		session.saveOrUpdate(assetregistry);


		// UPDATE AR_ID & AR_SITE_ID IN DISCOVERY_NEW_ITEM TABLE
		
		query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1, AR_SITE_ID = :param2 where DNI_ID = :param3");
		query.setParameter("param1", ArCode);
		query.setParameter("param2", AR_Site_ID);
		query.setParameter("param3", DniID);
		session.flush();
		query.executeUpdate();
		
		
		
		// ADD TO AR_SITE TABLE
    	
    	ArSite arSite = new ArSite();
    	arSite.setArsiteId(AR_Site_ID);
    	arSite.setSiteID(toSiteID);
    	arSite.setSiteName(towareName);
    	arSite.setWareID(towareID);  
    	arSite.setArID(ArCode);
    	session.saveOrUpdate(arSite);
		
		
		// ADD TO AR_NODE TABLE
    	String AR_NodeID;
    	if(!StringUtils.equalsIgnoreCase(toNodeId, "") ||  !StringUtils.equalsIgnoreCase(toNodeName, "")) {
    		synchronized (this) {						
    			AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
    		//String AR_NodeID = "ARNODE_" + year + "_" + appConfig.getSequenceNbr(28);
    	
    	ArNode assetRegNode = new ArNode();
    	assetRegNode.setNodearId(AR_NodeID);
    	assetRegNode.setNodeID(toNodeId);
    	assetRegNode.setNodeName(toNodeName);
    	assetRegNode.setArID(ArCode);
    	session.saveOrUpdate(assetRegNode);
    	}
    	
    	
		// Add to AR_SERIAL_NUMBER table
    	String AR_SerialNum_ID;
		if(!StringUtils.equalsIgnoreCase(serialnb, "0") ) {
			synchronized (this) {						
				AR_SerialNum_ID = "ARSNUM_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
			//String AR_SerialNum_ID = "ARSNUM_" + year + "_" + appConfig.getSequenceNbr(31);
		
		ArSerialNumber assetRegSerialNumber = new ArSerialNumber();
		assetRegSerialNumber.setSerialId(AR_SerialNum_ID);
		assetRegSerialNumber.setSerialNumber(toSerialNumber);
		assetRegSerialNumber.setModel(itemModel);
		assetRegSerialNumber.setPartNumber(itemPartNb);
		assetRegSerialNumber.setSnodeID(toNodeId);
		assetRegSerialNumber.setSnodeName(toNodeName);
		assetRegSerialNumber.setSite(toSite);
		assetRegSerialNumber.setPosition(toSlot);
		assetRegSerialNumber.setArID(ArCode);
		session.saveOrUpdate(assetRegSerialNumber);
		
		}
		
		// Add to AR_MODEL_PARTNUMBER table
		
		String itemmodel = itemModel;
		String AR_model_partNum;
		if(!StringUtils.equalsIgnoreCase(itemmodel, ""))
		{
			synchronized (this) {						
				AR_model_partNum = "ARMP_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_MODEL_PARTNO = AR_MODEL_PARTNO + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
			//String AR_model_partNum = "itmId_" + year + "_" +  appConfig.getSequenceNbr(26);
			
			ArPartNumber arPartNum = new ArPartNumber();
			arPartNum.setItmId(AR_model_partNum);
			arPartNum.setItemCode(itmcode);
			arPartNum.setItemPartNb(itemPartNb);
			arPartNum.setItemModel(itemModel);
			arPartNum.setArID(ArCode);
			arPartNum.setPrimary("1");
			arPartNum.setQtyPartNb(1);
			session.saveOrUpdate(arPartNum);
		}
		
		
	}


// AssetReg Id not null
if (AssetRegID != null) {
	ArCode = AssetRegID;
	
	query = session.createSQLQuery("update ASSET_REGISTRY SET ITEM_CODE = '"+itmcode+"',LAST_MODIFIED_DATE = sysdate,ITEM_NAME = '"+itmname+"',PO_ID = '"+PurchaseOrId+"',"
			+ "SUPPLIER_ID = '"+supplierID+"', SUPPLIER_NAME = '"+supplierName+"',ITEM_MODEL = '"+itemModel+"',ITEM_PART_NUMBER = '"+itemPartNb+"',ITEM_SN = '"+toSerialNumber+"',NODE_ID = '"+toNodeId+"',NODE_NAME = '"+toNodeName+"',PO_ITEM_ID = '"+poItemID+"' WHERE AR_ID = :param1 ");
	query.setParameter("param1", ArCode);
	query.executeUpdate();
	
	query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
	query.setParameter("param1", ArCode);
	query.setParameter("param2", DniID);
	query.executeUpdate();
	
	
	
	// ADD TO AR_MODEL_PARTNUMBER TABLE
	
	query = session.createQuery("select t.itmId from ArPartNumber t where t.arID = :param1 ");
	query.setParameter("param1", ArCode);
	String ARModelPartNum = (String) query.uniqueResult();
	
	if(ARModelPartNum == null)
	{
		synchronized (this) {						
			ARModelPartNum = "ARMP_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
			query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_MODEL_PARTNO = AR_MODEL_PARTNO + 1 ");
			query.executeUpdate();
			session.createSQLQuery("commit").executeUpdate();
			}
		 //ARModelPartNum = "itmId_" + year + "_" +  appConfig.getSequenceNbr(26);
	}
	
	query = session.createSQLQuery("update AR_MODEL_PARTNUMBER SET ITM_ID = '"+ARModelPartNum+"',ITEM_PART_NUMBER = '"+itemPartNb+"',QUANTITY = 1,ITEM_MODEL = '"+itemModel+"',ITEM_CODE = '"+itmcode+"',"
			+ "PRIMARY = '1' WHERE AR_ID = :param1 ");
	query.setParameter("param1", ArCode);
	query.executeUpdate();
	
	
	
	// ADD TO AR_SITE TABLE
	
	query = session.createQuery("select t.arsiteId from ArSite t where t.arID = :param1 ");
	query.setParameter("param1", ArCode);
	
	String AR_SiteID = (String) query.uniqueResult();
	
	if(AR_SiteID == null)
	{
		synchronized (this) {						
			AR_SiteID = "ARSITE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
			query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_SITE = AR_SITE + 1 ");
			query.executeUpdate();
			session.createSQLQuery("commit").executeUpdate();
			}
		//AR_SiteID = "AR_SITE_" + year + "_"  + appConfig.getSequenceNbr(30);
	}
	
	query = session.createSQLQuery("update AR_SITE SET ARSITE_ID = '"+AR_SiteID+"',SITE_ID = '"+toSiteID+"',SITE_NAME = '"+towareName+"',WARE_ID = '"+towareID+"'"
			+ " WHERE AR_ID = :param1 ");
	query.setParameter("param1", ArCode);
	query.executeUpdate();
	
	
	
	
	/// ADD TO AR_NODE TABLE                                  
	
	query = session.createQuery("select t.nodearId from ArNode t where t.arID = :param1 and t.nodeID = :param2");

	query.setParameter("param1", ArCode);
	query.setParameter("param2", toNodeId);
	String AR_NodeID = (String) query.uniqueResult();
	
	if(AR_NodeID == null)
	{
		synchronized (this) {						
			AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
			query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
			query.executeUpdate();
			session.createSQLQuery("commit").executeUpdate();
			}
		//AR_NodeID = "ARNODE_" + year + "_" + appConfig.getSequenceNbr(28);
	}
	
	query = session.createSQLQuery("update AR_NODE SET NODEAR_ID = '"+AR_NodeID+"',NODE_ID = '"+toNodeId+"',NODE_NAME = '"+toNodeName+"'"
			+ " WHERE AR_ID = :param1 ");
	query.setParameter("param1", ArCode);
	query.executeUpdate();
	
	
	
	// Add to AR_SERIAL_NUMBER table
	
	query = session.createQuery("select t.serialId from ArSerialNumber t where t.arID = :param1 and snodeID = :param2");
	query.setParameter("param1", ArCode);
	query.setParameter("param2", toNodeId);
	String AR_SerialID = (String) query.uniqueResult();
	
	
	if(AR_SerialID == null) {
		synchronized (this) {						
			AR_SerialID = "ARSNUM_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
			query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
			query.executeUpdate();
			session.createSQLQuery("commit").executeUpdate();
			}
		 //AR_SerialID = "ARSNUM_" + year + "_" + appConfig.getSequenceNbr(31);
	}
	
	query = session.createSQLQuery("update AR_SERIAL_NUMBER SET SERIAL_ID = '"+AR_SerialID+"',SERIAL_NUMBER = '"+toSerialNumber+"',MODEL = '"+itemModel+"',PART_NUMBER = '"+itemPartNb+"',NODE_ID = '"+toNodeId+"',NODE_NAME = '"+toNodeName+"',SITE = '"+toSite+"',POSITION = '"+toSlot+"' "
			+ " WHERE AR_ID = :param1 ");
	query.setParameter("param1", ArCode);
	query.executeUpdate();
	
}

	
}



// APPROVED BY Finance
@SuppressWarnings({ "rawtypes", "unchecked" })
public void ApprovalFinance(String trans_Type, String getApproval, String dnStatus, String AssetRegID, String ArCode, String PurchaseOrId, String itmcode, String itmname, String WorkOrder, String DniID, String toSiteID, String supplierID, String supplierName, String towareID, String towareName, String serialnb, float dnRate, String itemModel, String itemPartNb, String toSite, String toSerialNumber, String toNodeId, String toNodeName, String toSlot, String nodeID, String nodeName, String Site, String fromSlot, String SiteID) {
	


	Query getpoItemID = session.createSQLQuery("select PO_ITEM_ID from PURCHASE_ORDER_ITEM where ITEM_CODE = '"+itmcode+"' and PO_ID = '"+PurchaseOrId+"'");
	getpoItemID.executeUpdate();
	String poItemID = (String) getpoItemID.uniqueResult();

	// check, if this itemCode related to poID, exist in CIP table (get the qty and cipID)
	query = session.createQuery(
			"Select t.TOTALQTY as totalqty, t.cipID as cipID from CapitalInProgress t where t.PoId =:param1 and t.cipitemCode =:param2");

	query.setString("param1", PurchaseOrId);
	query.setString("param2", itmcode);

	List result = query.list();


	String FarCode;

	
	// if exist, get only one row, with CIPqty field and cipID
	if (result != null) {
		

		for (Object obj : result) {
			Object[] fields = (Object[]) obj;
			String result1 = fields[0].toString();
			float updatedQty = Float.parseFloat(result1) - 1; // substract 1 from the TotalQty

			
			if (updatedQty == 0) {

				// DELETE FROM CIP TABLE
				
				query = session.createQuery("delete from CapitalInProgress c where c.cipID =:param1");
				query.setParameter("param1", fields[1]);
				query.executeUpdate();
			}

			else {

				 Query q5 = session.createQuery("update CapitalInProgress c set c.TOTALQTY =:param1 where c.cipID =:param2"); 
				 q5.setFloat("param1", updatedQty); 
				 q5.setParameter("param2", fields[1]); 
				 q5.executeUpdate();
				 query = session.createSQLQuery("commit");
				 query.executeUpdate(); 

			}



			//FarCode = "FAR_" + year + "_" + appConfig.getSequenceNbr(10);
			synchronized (this) {						
				FarCode = "FAR_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FIXED_ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET FIXED_ASSET_REGISTRY = FIXED_ASSET_REGISTRY + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
			float initialCost = dnRate;
			
			query = session.createSQLQuery("select USEFULL_LIFE_MONTHS from ITEM where ITEM_CODE = '"+itmcode+"'");
			query.executeUpdate();
			String item_Usefull_LifeMonths = (String) query.uniqueResult();
			float useful_life_month = 0;

			if (!StringUtils.equalsIgnoreCase(item_Usefull_LifeMonths, "")) { 
				
				useful_life_month = Float.parseFloat(item_Usefull_LifeMonths);
			}

			

			String FarSiteID;
			synchronized (this) {						
				FarSiteID = "FARSITE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FAR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET FAR_SITE = FAR_SITE + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
			
			
			// ADD DiscoveryNewItem TO ASSET REGISTRY TABLE

			FixedAssetRegistry FixedAssetReg = new FixedAssetRegistry();
			
			FixedAssetReg.setFarID(FarCode);     
			FixedAssetReg.setFaritemCode(itmcode);
			FixedAssetReg.setFarcreatedDate(new Timestamp(System.currentTimeMillis()));
			FixedAssetReg.setFarlastModifiedDate(new Timestamp(System.currentTimeMillis()));
			FixedAssetReg.setFaritemName(itmname);
			FixedAssetReg.setIniCost(initialCost);
			FixedAssetReg.setUsefulLifeMon(useful_life_month);
			FixedAssetReg.setDniID(DniID);
			FixedAssetReg.setSupplierID(supplierID);
			FixedAssetReg.setSupplierName(supplierName);
			FixedAssetReg.setPoId(PurchaseOrId);
			FixedAssetReg.setItemModel(itemModel);
			FixedAssetReg.setItemPartNb(itemPartNb);
			FixedAssetReg.setItemSN(toSerialNumber);
			FixedAssetReg.setNodeID(toNodeId);
			FixedAssetReg.setNodeName(toNodeName);
			FixedAssetReg.setFarStatus("Running");
			FixedAssetReg.setPoItemId(poItemID);
			FixedAssetReg.setFarsiteId(FarSiteID);
			session.saveOrUpdate(FixedAssetReg);
			
			
			
			query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1,  FR_SITE_ID = :param2  where DNI_ID = :param3");
			query.setParameter("param1", FarCode);
			query.setParameter("param2", FarSiteID);
			query.setParameter("param3", DniID);
			query.executeUpdate();
			
			
			
			// ADD TO FAR NODE TABLE

			query = session.createQuery("select t.nodefarId from FarNode t where t.farID = :param1");

			query.setParameter("param1", FarCode);
			String FAR_NodeID = (String) query.uniqueResult();

			if(FAR_NodeID == null)
			{
			//FAR_NodeID = "FARNODE_" + year + "_" + appConfig.getSequenceNbr(27);
			synchronized (this) {						
				FAR_NodeID = "FARNODE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FAR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET FAR_NODE = FAR_NODE + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
			
			FarNode FixedAssetRegNode = new FarNode();
			
			FixedAssetRegNode.setNodefarId(FAR_NodeID);
			FixedAssetRegNode.setNodeID(toNodeId);
			FixedAssetRegNode.setNodeName(toNodeName);
			FixedAssetRegNode.setFarID(FarCode);

			session.saveOrUpdate(FixedAssetRegNode);
			
			}
			
			
			// ADD TO FAR MODEL_PART_NB TABLE
			
			if(!StringUtils.equalsIgnoreCase(itemModel, "") || !StringUtils.equalsIgnoreCase(itemPartNb, ""))
			{
			String FAR_model_partNum;
			synchronized (this) {						
				FAR_model_partNum = "FARMP_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FAR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET FAR_MODEL_PARTNO = FAR_MODEL_PARTNO + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
			
			FarPartNumber farPartNumber = new FarPartNumber();
			
			farPartNumber.setItmId(FAR_model_partNum);
			farPartNumber.setItemCode(itmcode);
			farPartNumber.setItemPartNb(itemPartNb);
			farPartNumber.setItemModel(itemModel);
			farPartNumber.setFarID(FarCode);
			farPartNumber.setPrimary("1");
			farPartNumber.setQtyPartNb(1);
			session.saveOrUpdate(farPartNumber);
			
			}

			
			
			//Add to FAR_SERIAL_NUMBER table

			if(!StringUtils.equalsIgnoreCase(serialnb, "0") ) {
			String FAR_SerialNum_ID;
			synchronized (this) {						
				FAR_SerialNum_ID = "FARSNUM_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FAR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET FAR_SERIALNO = FAR_SERIALNO + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
			FarSerialNumber fixedAssetRegSerialNumber = new FarSerialNumber();
			
			fixedAssetRegSerialNumber.setSerialId(FAR_SerialNum_ID);
			fixedAssetRegSerialNumber.setInputSerialNb(toSerialNumber);
			fixedAssetRegSerialNumber.setInputModel(itemModel);
			fixedAssetRegSerialNumber.setInputpartNumber(itemPartNb);
			fixedAssetRegSerialNumber.setInputNodeID(toNodeId);
			fixedAssetRegSerialNumber.setInputNodeName(toNodeName);
			fixedAssetRegSerialNumber.setInputsite(toSite);
			fixedAssetRegSerialNumber.setInputPosition(toSlot);
			fixedAssetRegSerialNumber.setFarID(FarCode);
			session.saveOrUpdate(fixedAssetRegSerialNumber);

			}

			
			
			//Add to FAR_SITE Table       
			
			if(!StringUtils.equalsIgnoreCase(toSite, null) ) {
				
			FarSite farSite = new FarSite();
			farSite.setFarsiteId(FarSiteID);
			farSite.setSiteID(toSiteID);
			farSite.setSiteName(towareName);
			farSite.setWareID(towareID);
			farSite.setFarID(FarCode);
			session.saveOrUpdate(farSite);
			}
			
			
			
			} // END for(Object obj : result) Loop
		
			} // if (result != null)


	
	List<Object> params= new ArrayList<Object>();
	String poStat = null;
	String getPoStatus = " select ordStatus from PurchaseOrder where ID = :param_1";
	params.add(PurchaseOrId);
	query = session.createQuery(getPoStatus);
	for (Object param : params) {
		i = params.indexOf(param) + 1;
		query.setString("param_" + i, param.toString());
	}
	if (query.uniqueResult() != null) {poStat = query.uniqueResult().toString();}
	params.clear();
	
	if (StringUtils.equalsIgnoreCase(poStat, "approved") ) 
	{
		String allGoodsRec = null;
		String getAllGRs = " select count(*) from GoodsReceived where grPOid = :param_1";
		params.add(PurchaseOrId);
		query = session.createQuery(getAllGRs);
		for (Object param : params) {
			i = params.indexOf(param) + 1;
			query.setString("param_" + i, param.toString());
		}
		if (query.uniqueResult() != null) {allGoodsRec = query.uniqueResult().toString();}
		params.clear();
		//String allGoodsRec = appConfig.findAllByQueryConditionByParam(GoodsReceived.class,getAllGRs, params);
			
		
		String allGRsApproved = null;
		String getAllGrsApproved = " select count(*) from GoodsReceived where grPOid = :param_1 and grStatus = 'completed' ";
		params.add(PurchaseOrId);
		query = session.createQuery(getAllGrsApproved);
		for (Object param : params) {
			i = params.indexOf(param) + 1;
			query.setString("param_" + i, param.toString());
		}
		if (query.uniqueResult() != null) {allGRsApproved = query.uniqueResult().toString();}
		params.clear();
		//String allGRsApproved = appConfig.findAllByQueryConditionByParam(GoodsReceived.class,getAllGrsApproved, params);
		
		
		if( Double.parseDouble(allGoodsRec)== Double.parseDouble(allGRsApproved))
		{
			
			String itemPO = "select distinct ItemCode from PurchaseOrderItem where POId like :param1";
			query = session.createQuery(itemPO);
			query.setParameter("param1", PurchaseOrId);
			List<Object[]> purchOrdItem = query.list();
			Object itemCode = null;
			for(Object PurchOrdItem: purchOrdItem ) {
				itemCode = ""+PurchOrdItem;
				String qtyItem= "select sum(Qty) from PurchaseOrderItem where ItemCode like :param_1 and POId like :param_2 ";
				params.add(itemCode);
				params.add(PurchaseOrId);
				double qty = findAllByMultiClzMultiQryPrms(session,qtyItem,params);
				String itemGR = "select sum(a.Qty) from GoodsReceivedItem a, GoodsReceived b where a.ItemCode like :param_1 and b.grPOid like :param_2 and a.GRid = b.ID";
				//List paramsGR = new ArrayList();
				params.add(itemCode);
				params.add(PurchaseOrId);
				double resQTYGR = findAllByMultiClzMultiQryPrms(session,itemGR,params);
				
				String resStatus="0";
				params.clear();
					if (resQTYGR < qty )
				{
						String itemInFAR = null;
						String getItemsInFAR = " select count(*) from FixedAssetRegistry where faritemCode = :param_1 and PoId = :param_2 ";
						params.add(itemCode);
						params.add(PurchaseOrId);
						query = session.createQuery(getItemsInFAR);
						for (Object param : params) {
							i = params.indexOf(param) + 1;
							query.setString("param_" + i, param.toString());
						}
						if (query.uniqueResult() != null) {itemInFAR = query.uniqueResult().toString();}
						params.clear();
						//String itemInFAR = appConfig.findAllByQueryConditionByParam(FixedAssetRegistry.class,getItemsInFAR, params);
					
						double ItemInFAR = 0;
						if(itemInFAR !=null) { ItemInFAR = Double.parseDouble(itemInFAR);}
						
					if((ItemInFAR + resQTYGR) >= qty)
					{
						resStatus = "1";
						
						params.add(resStatus);
						params.add(PurchaseOrId);
						params.add(itemCode);
						
						String Query = "update PurchaseOrderItem set poItemStatus = :param_1  where POId = :param_2 and ItemCode = :param_3";
						persistSingleColumn(session ,Query, params);
						params.clear();
					}
					else
					{
						resStatus = "0";
						
						params.add(resStatus);
						params.add(PurchaseOrId);
						params.add(itemCode);

						String Query = "update PurchaseOrderItem set poItemStatus = :param_1  where POId = :param_2 and ItemCode = :param_3";
						persistSingleColumn(session ,Query, params);
						params.clear();
					}
				}
			
			}
			String statusPoItem = " select distinct ItemCode from PurchaseOrderItem where POId like :param1 and poItemStatus = '1'";
			query = session.createQuery(statusPoItem);
			query.setParameter("param1", PurchaseOrId);
			List<Object[]> purchOrdItemList = query.list();
			//List<Object[]> purchOrdItemList = (List<Object[]>) appConfig.findAllByMultiClzQryPrms(statusPoItem, "param1",PurchaseOrId );
			if(purchOrdItem.size()==purchOrdItemList.size())
			{
				
				params.add("completed");

				params.add(PurchaseOrId);
				String QueryPoStatus = "update PurchaseOrder set ordStatus = :param_1  where ID = :param_2 ";
				persistSingleColumn(session ,QueryPoStatus, params);
				params.clear();

			}
		}
	}



	
}



//APPROVED BY Operation manager
public void ApprovalOperational(String trans_Type, String getApproval, String dnStatus, String AssetRegID, String ArCode, String PurchaseOrId, String itmcode, String itmname, String WorkOrder, String DniID, String toSiteID, String supplierID, String supplierName, String towareID, String towareName, String serialnb, float dnRate, String itemModel, String itemPartNb, String toSite, String toSerialNumber, String toNodeId, String toNodeName, String toSlot, String nodeID, String nodeName, String Site, String fromSlot, String SiteID) {
	


	if(!StringUtils.equalsIgnoreCase(serialnb, "0") ) // serial number exist
	{
		

		// TRANS TYPE NEW NODE ON EXISTED HARDWARE
		
		if(StringUtils.equalsIgnoreCase(trans_Type, "New Node on Existed Hardware"))
		{
				
			
			query = session.createQuery("select distinct arID from ArSerialNumber where serialNumber = :param1");

			query.setParameter("param1", serialnb);
			String ARid = (String) query.uniqueResult();
			
			
			query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", ARid);
			query.setParameter("param2", DniID);
			query.executeUpdate();
			
			
			if (ARid != null) {
				
				//Add to Table AR_SERIAL_NUMBER
				
				query = session.createQuery(" select serialId from ArSerialNumber where arID = :param1 and serialNumber = :param2 "
						+ " and snodeID = :param3 and snodeName = :param4");
				
				query.setParameter("param1", ARid);
				query.setParameter("param2", serialnb);
				query.setParameter("param3", toNodeId);
				query.setParameter("param4", toNodeName);
				String checkIfEx = (String) query.uniqueResult();
				String arserialID="";
				if(checkIfEx != null) {
					 arserialID= checkIfEx;
					}
				else {
					synchronized (this) {						
						arserialID = "ARSNUM_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}
					// arserialID="ARSNUM_" + year + "_" + appConfig.getSequenceNbr(31); 
				}
				
			
				 ArSerialNumber assetRegSerialNumber = new ArSerialNumber();
				 
	        		assetRegSerialNumber.setSerialId(arserialID);
					assetRegSerialNumber.setSerialNumber(toSerialNumber);
					assetRegSerialNumber.setModel(itemModel);
					assetRegSerialNumber.setPartNumber(itemPartNb);
					assetRegSerialNumber.setSnodeID(toNodeId);
					assetRegSerialNumber.setSnodeName(toNodeName);
					assetRegSerialNumber.setSite(toSite);
					assetRegSerialNumber.setArID(ARid);
					assetRegSerialNumber.setPosition(toSlot);
					session.saveOrUpdate(assetRegSerialNumber);
	        		
					
				// Add to table AR_Node
					
					query = session.createQuery(" select nodearId from ArNode where arID = :param1  "
						+ " and nodeID = :param2 and nodeName = :param3");
					query.setParameter("param1",ARid);
					query.setParameter("param2", toNodeId);
					query.setParameter("param3", toNodeName);
				String checkIfExist = (String) query.uniqueResult();
				String AR_NodeID = "";
				if(checkIfExist != null) {
					AR_NodeID = checkIfExist;
					}
				else {
					 //AR_NodeID = "ARNODE_" + year + "_" + appConfig.getSequenceNbr(28);
					 synchronized (this) {						
						 AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
				}

				
		        	
				    ArNode assetRegNode = new ArNode();
		        	assetRegNode.setNodearId(AR_NodeID);
					assetRegNode.setNodeID(toNodeId);
					assetRegNode.setNodeName(toNodeName);
					assetRegNode.setArID(ARid);
					session.saveOrUpdate(assetRegNode);
			}
			
			
			
			
			query = session.createQuery("select distinct farID from FarSerialNumber where inputSerialNb = :param1");
			query.setParameter("param1", serialnb);
			String FARid = (String) query.uniqueResult();
			
			Query query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", FARid);
			query.setParameter("param2", DniID);
			query.executeUpdate();


			if (FARid != null) {

			// Add to FAR_SERIAL_NUMBER table

				query = session.createQuery(" select serialId from FarSerialNumber where farID = :param1 and inputSerialNb = :param2 "
			+ " and inputNodeID = :param3 and inputNodeName = :param4");

				query.setParameter("param1", FARid);
				query.setParameter("param2", serialnb);
				query.setParameter("param3", toNodeId);
				query.setParameter("param4", toNodeName);
			String farSerialID = (String) query.uniqueResult();
			String farserialID="";
			if(farSerialID != null) {
			 farserialID= farSerialID;
			
			}
			else {
			 //farserialID="FARSNUM_" + year + "_" + appConfig.getSequenceNbr(32); 
			 synchronized (this) {						
				 farserialID = "FARSNUM_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FAR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET FAR_SERIALNO = FAR_SERIALNO + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
					}
			}

			

			// Add in Fixed_Asset_Registry
			
			FarSerialNumber fixedAssetRegSerialNumber = new FarSerialNumber();
			
			fixedAssetRegSerialNumber.setSerialId(farserialID);
			fixedAssetRegSerialNumber.setInputSerialNb(toSerialNumber);
			fixedAssetRegSerialNumber.setInputModel(itemModel);
			fixedAssetRegSerialNumber.setInputpartNumber(itemPartNb);
			fixedAssetRegSerialNumber.setInputNodeID(toNodeId);
			fixedAssetRegSerialNumber.setInputNodeName(toNodeName);
			fixedAssetRegSerialNumber.setInputsite(toSite);
			fixedAssetRegSerialNumber.setFarID(FARid);
			fixedAssetRegSerialNumber.setInputPosition(toSlot);
			
			session.saveOrUpdate(fixedAssetRegSerialNumber);

			// Add to table FAR_NODE

			query = session.createQuery(" select nodefarId from FarNode where farID = :param1  "
			+ " and nodeID = :param2 and nodeName = :param3");

			query.setParameter("param1",FARid);
			query.setParameter("param2", toNodeId);
			query.setParameter("param3", toNodeName);
			String farNodeID = (String) query.uniqueResult();
			String FAR_NodeID = "";
			if(farNodeID != null) {
				FAR_NodeID = farNodeID;
				}
			else {
			 //FAR_NodeID = "FARNODE_" + year + "_" + appConfig.getSequenceNbr(27);
			 synchronized (this) {						
				 FAR_NodeID = "FARNODE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FAR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET FAR_NODE = FAR_NODE + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
					}
			}


			FarNode farNode = new FarNode();
			
			farNode.setNodefarId(FAR_NodeID);
			farNode.setNodeID(toNodeId);
			farNode.setNodeName(toNodeName);
			farNode.setFarID(FARid);


			session.saveOrUpdate(farNode);
			}



			}
		
		//get arID
		query = session.createQuery("select distinct arID from ArSerialNumber where serialNumber = :param1 ");
		query.setParameter("param1",serialnb );
		String arID = (String) query.uniqueResult();
		
		
		query = session.createQuery("select distinct farID from FarSerialNumber where inputSerialNb = :param1 ");
		query.setParameter("param1",serialnb );
		String farID = (String) query.uniqueResult();

		if(StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Slot to Slot") || StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Node to Node") ||
		StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Site to Site"))
		{
			
		//Update AR_SERIAL_NUMBER
		Query query = session.createSQLQuery("update AR_SERIAL_NUMBER set NODE_NAME = :param1, NODE_ID = :param2, POSITION = :param3, SITE = :param4 where NODE_ID = :param5 and "
		+ "SERIAL_NUMBER = :param6 and POSITION = :param7 and SITE = :param8");
		query.setParameter("param1", toNodeName);
		query.setParameter("param2", toNodeId);
		query.setParameter("param3", toSlot);
		query.setParameter("param4", toSite);
		query.setParameter("param5", nodeID );
		query.setParameter("param6", serialnb);
		query.setParameter("param7", fromSlot);
		query.setParameter("param8", Site);
		query.executeUpdate();

		//Update In AR_SITE
		
		query = session.createSQLQuery("update AR_SITE SET SITE_ID = '"+toSiteID+"',SITE_NAME = '"+towareName+"',WARE_ID = '"+towareID+"'"
				+ " WHERE AR_ID = :param1 ");
		query.setParameter("param1", arID);
		query.executeUpdate();
		

		//Update FAR_SERIAL_NUMBER
		query = session.createSQLQuery("update FAR_SERIAL_NUMBER set NODE_NAME = :param1, NODE_ID = :param2, POSITION = :param3, SITE = :param4 where NODE_ID = :param5 and "
		+ "SERIAL_NUMBER = :param6 and POSITION = :param7 and SITE = :param8");
		query.setParameter("param1", toNodeName);
		query.setParameter("param2", toNodeId);
		query.setParameter("param3", toSlot);
		query.setParameter("param4", toSite);
		query.setParameter("param5", nodeID );
		query.setParameter("param6", serialnb);
		query.setParameter("param7", fromSlot);
		query.setParameter("param8", Site);
		query.executeUpdate();
			
		//Update In Far_Site

		query = session.createSQLQuery("update FAR_SITE SET SITE_ID = '"+toSiteID+"',SITE_NAME = '"+towareName+"',WARE_ID = '"+towareID+"'"
				+ " WHERE FAR_ID = :param1 ");
		query.setParameter("param1", farID);
		query.executeUpdate();


		//Update AR_NODE
		query = session.createSQLQuery("update AR_NODE set NODE_NAME = :param1, NODE_ID = :param2 where NODE_ID = :param3 and "
		+ "NODE_NAME = :param4 and AR_ID = :param5");
		query.setParameter("param1", toNodeName);
		query.setParameter("param2", toNodeId);
		query.setParameter("param3", nodeID);
		query.setParameter("param4", nodeName);
		query.setParameter("param5", arID);
		query.executeUpdate();
		
		
		//Update FAR_NODE
		query = session.createSQLQuery("update FAR_NODE set NODE_NAME = :param1, NODE_ID = :param2 where NODE_ID = :param3 and "
		+ "NODE_NAME = :param4 and FAR_ID = :param5");
		query.setParameter("param1", toNodeName);
		query.setParameter("param2", toNodeId);
		query.setParameter("param3", nodeID);
		query.setParameter("param4", nodeName);
		query.setParameter("param5", farID);
		query.executeUpdate();
		
		}

		
		
		
		// TRANS TYPE DISAPPEAR FOR TRANSFER

		if (StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Transfer"))

		{
		
			query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", arID);
			query.setParameter("param2", DniID);
			query.executeUpdate();
		          
			query = session.createSQLQuery("update ASSET_REGISTRY set AR_STATUS = 'Disappear' where AR_ID =:param1");
			query.setParameter("param1", arID);
			query.executeUpdate();


			query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", farID);
			query.setParameter("param2", DniID);
			query.executeUpdate();
		

		    query = session.createSQLQuery("update FIXED_ASSET_REGISTRY set FAR_STATUS ='Disappear' where FAR_ID =:param1");
		    query.setParameter("param1", farID);
		    query.executeUpdate();

		}

		
		
		
		// TRANS TYPE DISAPPEAR FOR MAINTENANCE 

		if (StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Maintenance"))

		{

		
			query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", arID);
			query.setParameter("param2", DniID);
			query.executeUpdate();
							             
			query = session.createSQLQuery("update ASSET_REGISTRY set AR_STATUS = 'Maintenance' where AR_ID =:param1");
			query.setParameter("param1", arID);
			query.executeUpdate();

			query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", farID);
			query.setParameter("param2", DniID);
			query.executeUpdate();
		
			query = session.createSQLQuery("update FIXED_ASSET_REGISTRY set FAR_STATUS ='Maintenance' where FAR_ID =:param1");
			query.setParameter("param1", farID);
			query.executeUpdate();

		} 

		
		
		
		// TRANS TYPE MAINTENANCE

		if (StringUtils.equalsIgnoreCase(trans_Type, "Maintenance"))

		{
		
			query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", arID);
			query.setParameter("param2", DniID);
			query.executeUpdate();
		
							             
			query = session.createSQLQuery("update ASSET_REGISTRY set AR_STATUS = 'Running' where AR_ID =:param1");
			query.setParameter("param1", arID);
			query.executeUpdate();

		
			query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", farID);
			query.setParameter("param2", DniID);
			query.executeUpdate();
		


			query = session.createSQLQuery("update FIXED_ASSET_REGISTRY set FAR_STATUS ='Running' where FAR_ID =:param1");
			query.setParameter("param1", farID);
			query.executeUpdate();


		} 

		
		
		
//TRANS TYPE RETIREMENT										
		
if (StringUtils.equalsIgnoreCase(trans_Type, "Retirement"))
			
{
//DELETE FROM AR_SITE BOQ
query = session.createSQLQuery("delete AR_SITE where AR_ID = :param1");
query.setParameter("param1", arID);
query.executeUpdate();

//DELETE FROM AR_NODE BOQ

query = session.createSQLQuery("delete AR_NODE where AR_ID = :param1");
query.setParameter("param1", arID);
query.executeUpdate();

//DELETE FROM AR_SERIAL_NUMBER
query = session.createSQLQuery("delete AR_SERIAL_NUMBER where AR_ID = :param1");
query.setParameter("param1", arID);
query.executeUpdate();

//DELETE FROM AR_MODEL_PARTNUMBER

query = session.createSQLQuery("delete AR_MODEL_PARTNUMBER where AR_ID = :param1");
query.setParameter("param1", arID);
query.executeUpdate();

//DELETE FROM ASSET_REGISTRY

query = session.createSQLQuery("delete ASSET_REGISTRY where AR_ID = :param1");
query.setParameter("param1", arID);
query.executeUpdate();

//DELETE FROM FAR_SITE BOQ

query = session.createSQLQuery("delete FAR_SITE where FAR_ID = :param1");
query .setParameter("param1", farID);
query .executeUpdate();

//DELETE FROM FAR_NODE BOQ

query = session.createSQLQuery("delete FAR_NODE where FAR_ID = :param1");
query.setParameter("param1", farID);
query.executeUpdate();

//DELETE FROM FAR_SERIAL_NUMBER

query = session.createSQLQuery("delete FAR_SERIAL_NUMBER where FAR_ID = :param1");
query.setParameter("param1", farID);
query.executeUpdate();

//DELETE FROM FAR_MODEL_PARTNUMBER

query = session.createSQLQuery("delete FAR_MODEL_PARTNUMBER where FAR_ID = :param1");
query.setParameter("param1", farID);
query.executeUpdate();

//DELETE FROM FIXED_ASSET_REGISTRY

query = session.createSQLQuery("delete FIXED_ASSET_REGISTRY where FAR_ID = :param1");
query.setParameter("param1", farID);
query.executeUpdate();

} // END TRANSACTION TYPE RETIREMENT

		
				
	}  // serial number exist 
	
	
	
}





//APPROVED BY project manager or asset unit
public void AssetNewNode(String trans_Type, String getApproval, String dnStatus, String AssetRegID, String ArCode, String PurchaseOrId, String itmcode, String itmname, String WorkOrder, String DniID, String toSiteID, String supplierID, String supplierName, String towareID, String towareName, String serialnb, float dnRate, String itemModel, String itemPartNb, String toSite, String toSerialNumber, String toNodeId, String toNodeName, String toSlot, String nodeID, String nodeName, String Site, String fromSlot, String SiteID) {
	

	
if(StringUtils.equalsIgnoreCase(dnStatus, "Approved") && StringUtils.equalsIgnoreCase(trans_Type, "New Node on New Hardware")) {
	
	System.out.println("the serial number is "+toSerialNumber);
	
	if(StringUtils.equalsIgnoreCase(toSerialNumber, "0")) {
		System.out.println("update when serial is 0 for source serial");
		Query sn1=session.createSQLQuery("UPDATE WORK_ORDER_DESTINATION_SERIAL SET RECONCILED ='1' WHERE EXISTS(SELECT * FROM WORK_ORDER_DESTINATION_SERIAL WHERE ITEM_MODEL = :param1 and ITEM_PART_NUMBER= :param2 and WO_ID = :param3)");
		sn1.setParameter("param1",itemModel);
		sn1.setParameter("param2", itemPartNb);
		sn1.setParameter("param3","WO_2021_62");
		sn1.executeUpdate();
		
		System.out.println("update when serial is 0 for destination serial");
		Query sn2=session.createSQLQuery("UPDATE WORK_ORDER_SOURCE_SERIAL SET RECONCILED ='1' WHERE EXISTS(SELECT * FROM WORK_ORDER_SOURCE_SERIAL WHERE ITEM_MODEL = :param1 and ITEM_PART_NUMBER= :param2 and WO_ID = :param3)");
		sn2.setParameter("param1",itemModel);
		sn2.setParameter("param2", itemPartNb);
		sn2.setParameter("param3","WO_2021_62");
		sn2.executeUpdate();

	} else if (!StringUtils.equalsIgnoreCase(toSerialNumber, "0")) {
		System.out.println("update when we have a serial");
		
		Query sn1=session.createSQLQuery("SELECT ID FROM WORK_ORDER_SOURCE_SERIAL WHERE SERIAL_NO = :param1");
		sn1.setParameter("param1",toSerialNumber);
		 String sn1Result = (String) sn1.uniqueResult();
			System.out.println("sn1Result"+sn1Result);

		 if (sn1Result !=null) {
			System.out.println("SERIAL IS IN WORK ORDER SOURCE TABLE");
     		query=session.createSQLQuery("update WORK_ORDER_SOURCE_SERIAL set RECONCILED ='1' where SERIAL_NO = :param1");
     		query.setParameter("param1",toSerialNumber);
     		query.executeUpdate();
			System.out.println("SERIAL IS RECONCILED IN WORK ORDER");

		 } else {
			 System.out.println("SERIAL IS NOTTTTTTT IN WORK ORDER SOURCE TABLE");
			 query=session.createSQLQuery("update WORK_ORDER_SOURCE_SERIAL set RECONCILED ='1', SERIAL_NO = :param1 WHERE EXISTS(SELECT * FROM WORK_ORDER_SOURCE_SERIAL WHERE ITEM_CODE = :param2 and ITEM_MODEL = :param3 and ITEM_PART_NUMBER= :param4 and WO_ID = :param5 and SERIAL_NO = '0' and ROWNUM = 1)"); 
			 query.setParameter("param1",toSerialNumber);
			 query.setParameter("param2",itmcode);
			 query.setParameter("param3",itemModel);
			 query.setParameter("param4", itemPartNb);
			 query.setParameter("param5",WorkOrder);

			 query.executeUpdate();
	     	 
				System.out.println("SERIAL IS RECONCILED IN WORK ORDER sourceeeee");
		 }
		 
		 Query sn2=session.createSQLQuery("SELECT ID FROM WORK_ORDER_DESTINATION_SERIAL WHERE SERIAL_NO = :param1");
			sn2.setParameter("param1",toSerialNumber);
			 String sn2Result = (String) sn2.setMaxResults(1).uniqueResult();
				System.out.println("sn1Result"+sn1Result);

			 if (sn2Result !=null) {
				System.out.println("SERIAL IS IN WORK ORDER DESTINATION TABLE");
				query=session.createSQLQuery("update WORK_ORDER_DESTINATION_SERIAL set RECONCILED ='1' where SERIAL_NO = :param1");
				query.setParameter("param1",toSerialNumber);
				query.executeUpdate();
				System.out.println("SERIAL IS RECONCILED IN WORK ORDER");

			 } else {
				 System.out.println("SERIAL IS NOTTTTTTT IN WORK ORDER DESTINATION TABLE");
				 query=session.createSQLQuery("update WORK_ORDER_DESTINATION_SERIAL set RECONCILED ='1', SERIAL_NO = :param1 WHERE EXISTS(SELECT * FROM WORK_ORDER_SOURCE_SERIAL WHERE ITEM_CODE = :param2 and ITEM_MODEL = :param3 and ITEM_PART_NUMBER= :param4 and WO_ID = :param5 and SERIAL_NO = '0' and ROWNUM = 1)"); 
				 query.setParameter("param1",toSerialNumber);
				 query.setParameter("param2",itmcode);
				 query.setParameter("param3",itemModel);
				 query.setParameter("param4", itemPartNb);
				 query.setParameter("param5",WorkOrder);

				 query.setMaxResults(1).executeUpdate();
		     		
					System.out.println("SERIAL IS RECONCILED IN WORK ORDER");
			 }

	}
		       		
        	}

}
public <T>double findAllByMultiClzMultiQryPrms(Session session, String queryString, List<Object> params) {

	double value=0;
query = session.createQuery(queryString);
int i;
for (Object param : params) {
	i = params.indexOf(param) + 1;
	query.setString("param_" + i, param.toString());
}

if(query.uniqueResult() != null)
{
	value = (double)query.uniqueResult();
}
else 
	System.out.println("There is no any record related to the query: " +query+ "in the method: findAllByMultiClzMultiQryPrms" );

return value;
}


public  <T> void persistSingleColumn(Session session ,String queryString, List<Object> params) {
	
	int i;
	Transaction tx =session.beginTransaction();
	query = session.createQuery(queryString);
	for (Object param : params) {
		i = params.indexOf(param) + 1;
		query.setString("param_" + i, param.toString());
	}
	query.executeUpdate();
	tx.commit();
 }

}
