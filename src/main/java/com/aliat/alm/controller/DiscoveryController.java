package com.aliat.alm.controller;


import com.aliat.alm.models.ArNode;
import com.aliat.alm.models.ArPartNumber;
import com.aliat.alm.models.ArSerialNumber;
import com.aliat.alm.models.ArSite;
import com.aliat.alm.models.AssetRegistry;
import com.aliat.alm.models.DNIFormView;
import com.aliat.alm.models.DiscoverNewItemNode;
import com.aliat.alm.models.FixedAssetRegistry;
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
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.NativeQuery;
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
import java.util.Arrays;
import org.hibernate.type.FloatType;
import org.hibernate.type.StringType;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.DiscoveryNew;
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
	private static final String DN_FROM_SLOT = "fromSlot";
	private static final String DN_TO_SLOT = "itemToSlot";
	private static final String DN_APPROVE_STATUS = "aprovStatus";
	private static final String DN_TRANS_TYPE = "transType";
	private static final String DN_NOTES = "notes";
	private static final String DN_ELEMENT_NAME = "elementName";
	private static final String DN_ADDRESS = "address";
	private static final String DESCRIPTION = "description";
	private static final String FarId = "FarId";
	private static final String Mac_Address = "macAddress";
	private static final String TO_NODE_DN = "toNode";
	private static final String FROM_NODE_DN = "fromNode";
	private static final String ALC_FLG = "alcflg";
	private static final String APPROVED_BY = "approvedby";
	int i;
	private static String str = null;
	private static 	Object[] result = null;


	@Autowired
	Form form;

	
	
	@Autowired
	Notify notifications;
	
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static final Logger logger = LoggerFactory.getLogger(DiscoveryController.class);
	
    Calendar calendar = new GregorianCalendar();
	int year = calendar.get(Calendar.YEAR);
	

	@RequestMapping(value = "/DiscoveryNewListView", method = RequestMethod.GET)
	public String DiscoveryNewListView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		
		session = AlmDbSession.getInstance().getSession();
		
		if (session != null && session.isOpen()) {
			notifications.headerNotifications(session, model);
			
			try {
				String str = "select a.DN_ID as dnID,a.DN_ID as dnewID,CAST((a.TOTAL_AMOUNT) AS VARCHAR(10)) as dnTotalAmount,"
						+ "CAST((a.TOTAL_QTY) AS VARCHAR(10)) as dnTotalQty,a.STATUS as dnStatus,"
						+ "(Select COUNT(*) from DISCOVERY_NEW_ITEM WHERE (APPROVAL='Project Manager' OR APPROVAL ='Operation Manager' OR APPROVAL IS NULL)"
						+ " AND DN_ID=a.DN_ID) as pendingPM,"
						+ "(Select COUNT(*) from DISCOVERY_NEW_ITEM WHERE (APPROVAL='Asset Unit') AND DN_ID=a.DN_ID) as pendingAM,"
						+ "(Select COUNT(*) from DISCOVERY_NEW_ITEM WHERE (APPROVAL='Finance') AND DN_ID=a.DN_ID) as pendingFM,"
						+ "TO_CHAR(LAST_MODIF_DATE,'YYYY-MM-DD HH24:MI:SS') as dnlastmodifDate "
						+ "from DISCOVERY_NEW a order by LAST_MODIF_DATE DESC";
				
		model.addAttribute("ListGridTable", mapper.writeValueAsString(session.createNativeQuery(str).list()));		
		
		} catch (Exception e) {
			logger.info("Error at DiscoveryNew ListView with a message: "+ e);
			e.printStackTrace();
			model.addAttribute("ListGridTable","");
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
			
		}

		return "DiscoveryNewListView";
	}
	
	@RequestMapping(value = "/FilteredDiscoveryNewListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredDiscoveryNewListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;	
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			notifications.headerNotifications(session, model);

			try {

				String startdate, enddate, status;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				status = request.getParameter("status");	
			

				String str = "select 1 as chkBox, a.DN_ID as dnID,  a.TOTAL_AMOUNT as dnTotalAmount, a.TOTAL_QTY as dnTotalQty,"
						+ " a.STATUS as dnStatus,"
						+ "(Select COUNT(*) from DISCOVERY_NEW_ITEM WHERE (APPROVAL='Project Manager' OR APPROVAL ='Operation Manager')"
						+ " AND DN_ID=a.DN_ID) as pendingPM,"
						+ "(Select COUNT(*) from DISCOVERY_NEW_ITEM WHERE (APPROVAL='Asset Unit') AND DN_ID=a.DN_ID) as pendingAM,"
						+ "(Select COUNT(*) from DISCOVERY_NEW_ITEM WHERE (APPROVAL='Finance') AND DN_ID=a.DN_ID) as pendingFM,"
						+ "TO_CHAR(LAST_MODIF_DATE,'YYYY-MM-DD HH24:MI:SS') as dnlastmodifDate from DISCOVERY_NEW a  ";
				
				if (startdate != null && enddate != null) {
					str = str + " where CREATION_DATE between TO_DATE('" + startdate
							+ "','YYYY-MM-DD') and TO_DATE('" + enddate + "','YYYY-MM-DD')";
				}
				if (status != null && !status.equalsIgnoreCase("")) {

					str = str + " and (upper(STATUS) LIKE upper('%" + status + "%')  )";
				}
				str = str + " ORDER BY LAST_MODIF_DATE DESC ";
				rtn.put("listNew",session.createNativeQuery(str).list());
			} catch (Exception e) {
				logger.info("Error in showing the filtered Discovery New list view with a message :" + e);
			} finally {
				if (session != null && session.isOpen()) {
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
		
		session = AlmDbSession.getInstance().getSession();		
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
		
		session = AlmDbSession.getInstance().getSession();

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
		
		/* to open discovery when click on ADD from discovery List */
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

		/* add data in table discoveryNewItem */
		String queryStmt = "SELECT t.DNI_ID AS dniID, t.ITEM_CODE AS dniItemcode, t.ITEM_NAME AS dniItemname, t.TRANS_TYPE AS transType, " +
			    "t.ELEMENT_NAME AS elementName, NVL(t.NOTES, ' ') AS notes, NVL(t.POSITION, ' ') AS position, t.APPROVAL AS dniAPPROVAL, " +
			    "t.PO_ID AS dniPOID, t.SUPPLIER_ID AS supplierID, t.SUPPLIER_NAME AS supplierName, t.TOTAL_AMOUNT AS totalAmount, " +
			    "t.WO_ID AS dniWOID, t.WO_PURPOSE AS purpose, t.QTY AS dniQty, t.RATE AS dniRate, t.DISCOUNT_AMOUNT AS dniDiscamount, " +
			    "t.TAX1 AS dniTax1, t.NET_RATE AS dniNetrate, t.TOTAL AS dniTotal, t.TOTAL_AT AS dniTotalat, t.FROM_SITE AS dniSIte, " +
			    "t.WARE_ID AS wareID, t.WARE_NAME AS wareName, NVL(t.FROM_SERIAL_NUMBER, ' ') AS dniSN, t.DNI_ID AS dniDNID, " +
			    "NVL(t.ITEM_MODEL, '') AS itemModel, NVL(t.ITEM_PART_NUMBER, '') AS itemPartNb,  " +
			    " t.APPROVAL_STATUS AS approvalStatus, NVL(t.FROM_SLOT, ' ') AS fromSlot, NVL(t.FAR_ID, ' ') AS farID, NVL(t.MAC_ADDRESS, ' ') AS macAddress, " +
			    "NVL(t.TO_SLOT, ' ') AS toSlot, t.TO_SITE AS toSite, t.TO_WARE_NAME AS toWareName, " +
			    "t.TO_WARE_ID AS toWareId, NVL(t.ALCFLG, ' ') AS alcFlg, " +
			    "NVL(t.TO_SERIAL_NUMBER, ' ') AS toSerialNumber, NVL(t.DESCRIPTION, ' ') AS description, x.toNodeArray, y.fromNodeArray " +
			    "FROM DISCOVERY_NEW_ITEM t " +
			    "LEFT JOIN (SELECT DISTINCT DNI_ID, json_object('toNodeArray' VALUE json_arrayagg(json_object('NodeId' VALUE TO_NODE_ID, " +
			    "'NodeName' VALUE TO_NODE_NAME, 'NodeType' VALUE TO_NODE_TYPE))) AS toNodeArray FROM Discover_New_Item_Node WHERE TO_NODE_ID IS NOT NULL GROUP BY DNI_ID) x " +
			    "ON x.DNI_ID = t.DNI_ID " +
			    "LEFT JOIN (SELECT DISTINCT DNI_ID, json_object('fromNodeArray' VALUE json_arrayagg(json_object('NodeId' VALUE FROM_NODE_ID, " +
			    "'NodeName' VALUE FROM_NODE_NAME, 'NodeType' VALUE FROM_NODE_TYPE))) AS fromNodeArray FROM Discover_New_Item_Node WHERE FROM_NODE_ID IS NOT NULL GROUP BY DNI_ID) y " +
			    "ON y.DNI_ID = t.DNI_ID " +
			    "WHERE t.DN_ID = :param1";


		query = session.createNativeQuery(queryStmt);
		query.setParameter("param1", dnid);
		

		List<DNIFormView> listDiscoveryNewItems = (List<DNIFormView>) ((NativeQuery) query)
			    .addScalar("dniID", new StringType())
			    .addScalar("dniItemcode", new StringType())
			    .addScalar("dniItemname", new StringType())
			    .addScalar("transType", new StringType())
			    .addScalar("elementName", new StringType())
			    .addScalar("notes", new StringType())
			    .addScalar("position", new StringType())
			    .addScalar("dniAPPROVAL", new StringType())
			    .addScalar("dniPOID", new StringType())
			    .addScalar("supplierID", new StringType())
			    .addScalar("supplierName", new StringType())
			    .addScalar("totalAmount", new FloatType())
			    .addScalar("dniWOID", new StringType())
			    .addScalar("purpose", new StringType())
			    .addScalar("dniQty", new FloatType())
			    .addScalar("dniRate", new FloatType())
			    .addScalar("dniDiscamount", new FloatType())
			    .addScalar("dniTax1", new FloatType())
			    .addScalar("dniNetrate", new FloatType())
			    .addScalar("dniTotal", new FloatType())
			    .addScalar("dniTotalat", new FloatType())
			    .addScalar("dniSIte", new StringType())
			    .addScalar("wareID", new StringType())
			    .addScalar("wareName", new StringType())
			    .addScalar("dniSN", new StringType())
			    .addScalar("dniDNID", new StringType())
			    .addScalar("itemModel", new StringType())
			    .addScalar("itemPartNb", new StringType())
			    .addScalar("approvalStatus", new StringType())
			    .addScalar("fromSlot", new StringType())
			    .addScalar("toSlot", new StringType())
			     .addScalar("toSite", new StringType())
			    .addScalar("toWareName", new StringType())
			    .addScalar("toWareId", new StringType())
			    .addScalar("alcFlg", new StringType())
			    .addScalar("farID", new StringType())
			    .addScalar("macAddress", new StringType())
			    .addScalar("toSerialNumber", new StringType())
			    .addScalar("description", new StringType())
			    .addScalar("toNodeArray", new StringType())
			    .addScalar("fromNodeArray", new StringType())
			    .setResultTransformer(Transformers.aliasToBean(DNIFormView.class))
			    .list();

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
		

		session = AlmDbSession.getInstance().getSession(); 
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
				dnid = "DND_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					}
			/*dnid = "DND_" + year + "_" + appConfig.getSequenceNbr(6);*/
			

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
		
		
		/* SAVE DISCOVERY NEW */
		discoverynew.setDnID(dnid);
		discoverynew.setDncreationDate(CreationDate);
		discoverynew.setdnlastmodifDate(ModifDate);
		discoverynew.setDnTotalAmount(Float.parseFloat(totamnt));
		discoverynew.setDnStatus(dnstat);
		discoverynew.setDnTotalQty(Float.parseFloat(totqty));
		
		session.saveOrUpdate(discoverynew);
		
		query = session.createQuery("select t.dniID as dniID, t.dniAPPROVAL as dniAPPROVAL from DiscoveryNewItem t where t.dniDNID =:param1");
		query.setParameter("param1",dnid);
		
		List<DiscoveryNewItem> listDnItems = (List<DiscoveryNewItem>) query.setResultTransformer(Transformers.aliasToBean(DiscoveryNewItem.class)).list();
		
		if (itemParameters.getDictParameter() != null) {
			
			/* SAVE DISCOVERY NEW ITEM */
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

				
				/* String nodePK = itemParameters.getDictParameter().get(i).get(NODE_PK);
				if (nodePK != "") {
				String QueryUpdateInNodeActive ="UPDATE NODE_ACTIVE SET SITE_ID ='"+SiteID+"', WARE_ID ='"+wareID+"' WHERE NODE_PK ='"+nodePK+"'";
				Query qUpdate = session.createNativeQuery(QueryUpdateInNodeActive);
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
				String fromSlot= itemParameters.getDictParameter().get(i).get(DN_FROM_SLOT);
				String toSlot= itemParameters.getDictParameter().get(i).get(DN_TO_SLOT);
				String transType= itemParameters.getDictParameter().get(i).get(DN_TRANS_TYPE);
				String notes= itemParameters.getDictParameter().get(i).get(DN_NOTES);
				String elementName= itemParameters.getDictParameter().get(i).get(DN_ELEMENT_NAME);
				String position= itemParameters.getDictParameter().get(i).get(DN_ADDRESS);
				String dniAPPROVAL= itemParameters.getDictParameter().get(i).get(APPROVED_BY);
				String approvalStatus= itemParameters.getDictParameter().get(i).get(DN_APPROVE_STATUS);
				String Description= itemParameters.getDictParameter().get(i).get(DESCRIPTION);
				String FAR= itemParameters.getDictParameter().get(i).get(FarId);
				String MacAddress= itemParameters.getDictParameter().get(i).get(Mac_Address);
				
				
				
				if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get(DN_ITEM_ID), "0")) {
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							}
					/*DniID = "DNI_" + year + "_" + appConfig.getSequenceNbr(7); */
					

					/* assign the new ID */
					discoverynewitem.setDniID(DniID); /* dnitmid */

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
					if(FAR!=null) {
					discoverynewitem.setFarID(FAR);
					}
					discoverynewitem.setMacAddress(MacAddress);
					discoverynewitem.setItemModel(itemParameters.getDictParameter().get(i).get(DN_ITEM_MODEL));
					discoverynewitem.setItemPartNb(itemParameters.getDictParameter().get(i).get(DN_ITEM_PART_NB));
					discoverynewitem.setFromSlot(itemParameters.getDictParameter().get(i).get(DN_FROM_SLOT));
					discoverynewitem.setToSlot(itemParameters.getDictParameter().get(i).get(DN_TO_SLOT));
					discoverynewitem.setTransType(itemParameters.getDictParameter().get(i).get(DN_TRANS_TYPE));
					discoverynewitem.setNotes(itemParameters.getDictParameter().get(i).get(DN_NOTES));
					discoverynewitem.setElementName(itemParameters.getDictParameter().get(i).get(DN_ELEMENT_NAME));
					discoverynewitem.setPosition(itemParameters.getDictParameter().get(i).get(DN_ADDRESS));
					discoverynewitem.setDniAPPROVAL(itemParameters.getDictParameter().get(i).get(APPROVED_BY));
					discoverynewitem.setApprovalStatus(itemParameters.getDictParameter().get(i).get(DN_APPROVE_STATUS));
					discoverynewitem.setDescription(itemParameters.getDictParameter().get(i).get(DESCRIPTION));
					/*discoverynewitem.setNodePK(itemParameters.getDictParameter().get(i).get(NODE_PK)); */
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
					query = session.createNativeQuery("UPDATE DISCOVERY_NEW_ITEM SET ITEM_CODE = '" + itmcode + "', LAST_MODIFIED_DATE = sysdate, ITEM_NAME = '" + itmname + "', RATE = '" + dniRate + "', DISCOUNT_AMOUNT = '" + dniDiscamount + "', NET_RATE = '" + dniNetrate + "', "
						    + "TAX1 = '" + dniTax1 + "' , TOTAL = '" + dniTotal + "' , TOTAL_AT = '" + dniTotalat + "' , WARE_ID = '" + wareID + "' , WARE_NAME = '" + wareName + "' , "
						    + "FROM_SITE = '" + SiteID + "' , TO_WARE_ID = '" + towareID + "' , TO_WARE_NAME = '" + towareName + "' , TO_SITE = '" + toSiteID + "' , QTY = '" + dniQty + "' , DN_ID = '" + dnid + "' , "
						    + "FROM_SERIAL_NUMBER = '" + dniSN + "' , TO_SERIAL_NUMBER = '" + toSerialNumber + "' , PO_ID = '" + PurchaseOrId + "' , SUPPLIER_ID = '" + supplierID + "' , SUPPLIER_NAME = '" + supplierName + "' , "
						    + " TOTAL_AMOUNT = '" + TotalAmount + "' , WO_ID = '" + woId + "' , WO_PURPOSE = '" + purpose + "' , ITEM_MODEL = '" + itemModel + "' , ITEM_PART_NUMBER = '" + itemPartNb + "' , Far_ID =  '" + FAR+ "', MAC_ADDRESS = '" + MacAddress +"', " 
						    + "FROM_SLOT = '" + fromSlot + "' , TO_SLOT = '" + toSlot + "', TRANS_TYPE = '" + transType + "', NOTES = '" + notes + "', ELEMENT_NAME = '" + elementName + "', POSITION = '" + position + "', APPROVAL = '" + dniAPPROVAL + "', "
						    + "APPROVAL_STATUS = '" + approvalStatus + "', DESCRIPTION = '" + Description + "'  WHERE DNI_ID = :param1");

query.setParameter("param1", DniID);
query.executeUpdate();


					
				}
				
				String toNodeDn = itemParameters.getDictParameter().get(i).get(TO_NODE_DN);
				System.out.println(toNodeDn);

				if (toNodeDn != null && !toNodeDn.isEmpty()) {
				    try {
				        Object toNodeObj = new JSONParser().parse(toNodeDn);
				        JSONArray toNodeJSNArray = (JSONArray) ((HashMap) toNodeObj).get("toNodeArray");
				        DiscoverNewItemNode nodeDn = null;
				        ArrayList toNodeArrayList;

				        for (Object toNodeJSN : (JSONArray) toNodeJSNArray) {
				            toNodeArrayList = new ArrayList((((HashMap) toNodeJSN).values()));
				            String ToNodeId = (String) toNodeArrayList.get(0);

				            /* Check if a node with the same toNodeId already exists */
				            nodeDn = (DiscoverNewItemNode) session
				                .createQuery("FROM DiscoverNewItemNode WHERE toNodeId = :toNodeId AND dniId = :dniId")
				                .setParameter("toNodeId", ToNodeId).setParameter("dniId", DniID)
				                .uniqueResult();

				            if (nodeDn != null) {
				                nodeDn.setToNodeName((String) toNodeArrayList.get(2));
				                nodeDn.setToNodeType((String) toNodeArrayList.get(1));
				                nodeDn.setLastModifiedDate(ModifDate);
				            } else {
				                /* Node with the same toNodeId doesn't exist, create a new one */
				                String DN_NodeId;
				                synchronized (this) {
				                    DN_NodeId = "DNI_Node_" + year + "_" +
				                            Integer.parseInt(session
				                                    .createNativeQuery("SELECT DNI_Node FROM SEQ_TABLE")
				                                    .uniqueResult().toString());

				                    /* Update the sequence table */
				                    session.createNativeQuery("UPDATE SEQ_TABLE SET DNI_Node = DNI_Node + 1 ").executeUpdate();
				                    session.createNativeQuery("commit").executeUpdate();
				                }

				                nodeDn = new DiscoverNewItemNode();
				                nodeDn.setToNodeId(ToNodeId);
				                nodeDn.setToNodeName((String) toNodeArrayList.get(2));
				                nodeDn.setToNodeType((String) toNodeArrayList.get(1));
				                nodeDn.setDniNode(DN_NodeId);
				                nodeDn.setDniId(DniID);
				                nodeDn.setCreationDate(CreationDate);
				                nodeDn.setLastModifiedDate(ModifDate);
				                session.save(nodeDn);
				            }

				            session.flush();
				        }

				        System.out.println("Processing toNodeDn completed.");
				    } catch (Exception e) {
				        e.printStackTrace();
				    }
				}
				

				String fromNodeDn = itemParameters.getDictParameter().get(i).get(FROM_NODE_DN);
				System.out.println(fromNodeDn);

				if (fromNodeDn != null && !fromNodeDn.isEmpty()) {
				    try {
				        Object fromNodeObj = new JSONParser().parse(fromNodeDn);
				        JSONArray fromNodeJSNArray = (JSONArray) ((HashMap) fromNodeObj).get("fromNodeArray");
				        DiscoverNewItemNode nodeDn = null;
				        ArrayList fromNodeArrayList;

				        for (Object fromNodeJSN : (JSONArray) fromNodeJSNArray) {
				            fromNodeArrayList = new ArrayList((((HashMap) fromNodeJSN).values()));
				            String fromNodeId = (String) fromNodeArrayList.get(0);

				             nodeDn = (DiscoverNewItemNode) session
				                .createQuery("FROM DiscoverNewItemNode WHERE fromNodeId = :fromNodeId  AND dniId = :dniId")
				                .setParameter("fromNodeId", fromNodeId).setParameter("dniId", DniID)
				                .uniqueResult();

				            if (nodeDn != null) {
				                nodeDn.setFromNodeName((String) fromNodeArrayList.get(2));
				                nodeDn.setFromNodeType((String) fromNodeArrayList.get(1));
				                nodeDn.setLastModifiedDate(ModifDate);
				            } else {
				                String DN_NodeId;
				                synchronized (this) {
				                    DN_NodeId = "DNI_Node_" + year + "_" +
				                            Integer.parseInt(session
				                                    .createNativeQuery("SELECT DNI_Node FROM SEQ_TABLE")
				                                    .uniqueResult().toString());

				                    /* Update the sequence table */
				                    session.createNativeQuery("UPDATE SEQ_TABLE SET DNI_Node = DNI_Node + 1 ").executeUpdate();
				                    session.createNativeQuery("commit").executeUpdate();
				                }

				                nodeDn = new DiscoverNewItemNode();
				                nodeDn.setFromNodeId(fromNodeId);
				                nodeDn.setFromNodeName((String) fromNodeArrayList.get(2));
				                nodeDn.setFromNodeType((String) fromNodeArrayList.get(1));
				                nodeDn.setDniNode(DN_NodeId);
				                nodeDn.setDniId(DniID);
				                nodeDn.setCreationDate(CreationDate);
				                nodeDn.setLastModifiedDate(ModifDate);
				                session.save(nodeDn);
				            }

				            session.flush();
				        }
				       
				        System.out.println("Processing fromNodeDn completed.");
				    } catch (Exception e) {
				        e.printStackTrace();
				    }
				}
				String[] allDeletedToNodeArray = request.getParameterValues("allDeletedToNodeArray[]");

				if (allDeletedToNodeArray != null) {
				    for (int z = 0; z < allDeletedToNodeArray.length; z++) {
				        String[] values = allDeletedToNodeArray[z].split(",");
				        if (values.length >= 2) {
				            String NodeId = values[0];
				            String dniId = values[1];

				            query = session.createQuery("delete from DiscoverNewItemNode t where t.toNodeId = :toNodeId and t.dniId = :dniId");
				            query.setParameter("toNodeId", NodeId);
				            query.setParameter("dniId", dniId);
				            query.executeUpdate();
				        }
				    }
				}

				String[] allDeletedFromNodeArray = request.getParameterValues("allDeletedFromNodeArray[]");
				System.out.println(Arrays.toString(allDeletedFromNodeArray));

				if (allDeletedFromNodeArray != null) {
				    for (int z = 0; z < allDeletedFromNodeArray.length; z++) {
				        String[] values = allDeletedFromNodeArray[z].split(",");
				        if (values.length >= 2) {
				            String NodeId = values[0];
				            String dniId = values[1];

				             query = session.createQuery("delete from DiscoverNewItemNode t where t.fromNodeId = :fromNodeId and t.dniId = :dniId");
				            query.setParameter("fromNodeId", NodeId);
				            query.setParameter("dniId", dniId);
				            query.executeUpdate();
				        }
				    }
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


				
				/* DiscoveryThread thread = new DiscoveryThread(trans_Type, getApproval, dnStatus,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,WorkOrder,DniID,toSiteID,supplierID,supplierName,towareID,towareName,serialnb,dnRate,itemModel,itemPartNb,toSite,toSerialNumber,toNodeId,toNodeName,toSlot,nodeID,nodeName,Site,fromSlot,SiteID);
				thread.start(); */
				query =session.createNativeQuery("select Trans_id from discovery_new_item  where DNI_ID=:param1");
				query.setParameter("param1",DniID);
				String transId = (String) query.uniqueResult();
		

				if ((StringUtils.equalsIgnoreCase(getApproval, "Project Manager") && StringUtils.equalsIgnoreCase(dnStatus, "Approved")) || ((StringUtils.equalsIgnoreCase(getApproval,"Asset Unit") && StringUtils.equalsIgnoreCase(dnStatus, "Approved")))) {
				

				if(StringUtils.equalsIgnoreCase(trans_Type, "New Node on New Hardware") || StringUtils.equalsIgnoreCase(trans_Type, "New Hardware on Existed Node") || StringUtils. 
						equalsIgnoreCase(trans_Type, "Replacement") || StringUtils.equalsIgnoreCase(trans_Type, "New Hardware on New Node"))  {

					System.out.println("-- PROJECT MANAGER APPROVAL --");
					
						ApprovalProjectandAsset(trans_Type, getApproval, dnStatus,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,WorkOrder,DniID,toSiteID,supplierID,supplierName,towareID,towareName,serialnb,dnRate,itemModel,itemPartNb,toSite,toSerialNumber,toSlot,Site,fromSlot,SiteID, MacAddress);
						if(transId != null) {
						    transactionUpdate(transId,trans_Type,getApproval);
					}
					}
				}
				
				

				if (StringUtils.equalsIgnoreCase(getApproval, "Finance") &&  StringUtils.equalsIgnoreCase(dnStatus, "Approved")) { 

				if(StringUtils.equalsIgnoreCase(trans_Type, "New Node on New Hardware") || StringUtils.equalsIgnoreCase(trans_Type, "New Hardware on Existed Node") || StringUtils. 
						equalsIgnoreCase(trans_Type, "Replacement") || StringUtils.equalsIgnoreCase(trans_Type, "New Hardware on New Node"))  {

					System.out.println("-- FINANCE APPROVAL --");

						ApprovalFinance(trans_Type, getApproval, dnStatus,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,WorkOrder,DniID,toSiteID,supplierID,supplierName,towareID,towareName,serialnb,dnRate,itemModel,itemPartNb,toSite,toSerialNumber,toSlot,Site,fromSlot,SiteID,MacAddress);
						if(transId != null) {
							transactionUpdate(transId,trans_Type,getApproval);
						}
					}
				}
				
				
				

				if (StringUtils.equalsIgnoreCase(getApproval, "Operation Manager") && StringUtils.equalsIgnoreCase(dnStatus, "Approved")) {
				
				
				if(StringUtils.equalsIgnoreCase(trans_Type, "New Node on Existed Hardware") || StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Slot to Slot") || StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Node to Node") 
						|| StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Site to Site") || StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Maintenance")
						|| StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Transfer") || StringUtils.equalsIgnoreCase(trans_Type, "Maintenance") || StringUtils.equalsIgnoreCase(trans_Type, "Retirement"))
					
								{	

					System.out.println("-- OPERATION MANAGER APPROVAL --");
					
							ApprovalOperational(trans_Type, getApproval, dnStatus,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,WorkOrder,DniID,toSiteID,supplierID,supplierName,towareID,towareName,serialnb,dnRate,itemModel,itemPartNb,toSite,toSerialNumber,toSlot,Site,fromSlot,SiteID, FAR, MacAddress);
							if(transId != null) {
								transactionUpdate(transId,trans_Type,getApproval);
							}
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
	        
	        session = AlmDbSession.getInstance().getSession();
	        String[] idList = request.getParameterValues("dnID[]");

			if (session != null && session.isOpen()) {
	            tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
	            try {
	            	
	            	for(int i= 0; i<idList.length;i++) {
						idForm=idList[i];
						
		            	 query = session.createNativeQuery("Delete DISCOVERY_NEW where DN_ID = '"+ idForm +"'");
	                     query.executeUpdate();
						
		            	 query = session.createNativeQuery("Delete DISCOVERY_NEW_ITEM where DN_ID = '"+ idForm +"'");
	                     query.executeUpdate();
						
		            	 query = session.createNativeQuery("Delete ASSET_LIFE_CYCLE where VOUCHER_NB = '"+ idForm +"'");
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
		
		
	     session = AlmDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {
		            tx = session.beginTransaction();
					notifications.headerNotifications(session, model);
		            try {
		            	
		        		
		        		String idForm = request.getParameter("dnID");
		            	
	                    query = session.createNativeQuery("delete DISCOVERY_NEW_ITEM where DN_ID = '"+ idForm +"'");
	                    query.executeUpdate();
	                    
	                    query = session.createNativeQuery("delete DISCOVERY_NEW where DN_ID = '"+ idForm +"'");
	                    query.executeUpdate();

	                    query = session.createNativeQuery("delete ASSET_LIFE_CYCLE where VOUCHER_NB = '"+ idForm +"'");
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


	     session = AlmDbSession.getInstance().getSession();
			
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
		
		if (itemModel.equalsIgnoreCase("empty") == false) {query.setParameter("param2", itemModel); }
		
		if (itemPartNb.equalsIgnoreCase("empty") == false) {query.setParameter("param3", itemPartNb); }
		
		query.setParameter("param4", Item_code);
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
			
			if (itemModel.equalsIgnoreCase("empty") == false) {query.setParameter("param2", itemModel); }
			
			if (itemPartNb.equalsIgnoreCase("empty") == false) {query.setParameter("param3", itemPartNb); }
			query.setParameter("param5", workOrder);
		 
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
				
				if (itemModel.equalsIgnoreCase("empty") == false) {query.setParameter("param2", itemModel); }
				
				if (itemPartNb.equalsIgnoreCase("empty") == false) {query.setParameter("param3", itemPartNb); }
				
		 }
		 
		 
		query.setParameter("param1", PoID);

		
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
	
	@RequestMapping(value = "/GetAllFarDn", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllFarDn(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		String Site = request.getParameter("siteId");
		String SiteId="";
		if(Site!=null) {
			 SiteId=Site.split(":")[2];
		}
		String[] idList = request.getParameterValues("nodeID[]");
		String Far = request.getParameter("Far");
		
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
		try {
				
				query = session.createNativeQuery("SELECT FAR_ID, Item_Code, Item_Name, Item_Model, Item_Part_number FROM FIXED_ASSET_REGISTRY " +
				        "WHERE FAR_Id IN (SELECT FAR_ID FROM FAR_NODE WHERE Node_ID IN (:nodeIDs) " +
				        "INTERSECT SELECT FAR_ID FROM FAR_SITE WHERE site_id = :site) " +
				        "AND (FAR_ID LIKE :Far OR Item_Code LIKE :Far OR Item_Name LIKE :Far OR Item_Model LIKE :Far OR Item_Part_number LIKE :Far)");

				query.setParameter("nodeIDs", Arrays.asList(idList));
				query.setParameter("site", SiteId);
				query.setParameter("Far", "%" + Far + "%"); 
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("globalList", query.list());
	
			}catch(Exception e) {
				
				logger.info(
						"Error while getting AR using autocomplete with error message: "+ e);
				e.printStackTrace();
				
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetALLWOByItem", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetALLWOByItem(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){

		Map<String, Object> rtn = new LinkedHashMap<>();
		List<Object[]> listResult = new ArrayList<Object[]>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

     session = AlmDbSession.getInstance().getSession();
		
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			
			try {

		String woID="%" + request.getParameter("woId") + "%";
		String PoID =request.getParameter("PrOrderID");
		String Item_code = request.getParameter("Item_code");
		String itemModel = request.getParameter("itemModel");
		String itemPartNb = request.getParameter("ItemPartNb");
		
		String str = "";
		
		 if(Item_code.equalsIgnoreCase("empty") == false){
			 System.out.println("Passed from here as Item_code is not empty");
			 
			str="select distinct a.workOrdId ,a.purpose from WorkOrder a,WorkOrderSource b,WorkOrderDestination c, "
					+"Item d, ItemPartNumber t where(a.workOrdId like :param1 or a.purpose like :param1) "
					/* +"and b.itemCode = d.itemCode and c.itemCode = d.itemCode " */
					+"and ((b.itemCode =:param4 and a.workOrdId = b.workOrdId) or (c.itemCode =:param4 and a.workOrdId = c.workOrdId)) and t.itemCode = d.itemCode ";

		
			if (itemModel.equalsIgnoreCase("empty") == false) {
				str += " and t.itemModel  =:param2";
			}
						
			if (itemPartNb.equalsIgnoreCase("empty") == false) {
				str += " and t.itemPartNum =:param3";
			}						
			query = session.createQuery(str);					
			if (itemModel.equalsIgnoreCase("empty") == false) {query.setParameter("param2", itemModel); }			
			if (itemPartNb.equalsIgnoreCase("empty") == false) {query.setParameter("param3", itemPartNb); }			
			
			query.setParameter("param4", Item_code);
			query.setParameter("param1", woID);
		    listResult = query.list();			
		}
		
		
/*		
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
			
			if (itemModel.equalsIgnoreCase("empty") == false) {query.setParameter("param2", itemModel); }
			
			if (itemPartNb.equalsIgnoreCase("empty") == false) {query.setParameter("param3", itemPartNb); }
			
			
			query.setParameter("param4",PoID );
			
			
			
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
				
				if (itemModel.equalsIgnoreCase("empty") == false) {query.setParameter("param2", itemModel); }
				
				if (itemPartNb.equalsIgnoreCase("empty") == false) {query.setParameter("param3", itemPartNb); }
				
			}
		
*/						

		/*model.addAttribute("listResult", mapper.writeValueAsString(listResult)); */
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
		session = AlmDbSession.getInstance().getSession();		
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
			String ArCode="";
			if (ApproveType.equalsIgnoreCase("1") == true) {
				
				
				
				/* ArCode = "AR_" + year + "_" + appConfig.getSequenceNbr(9); */
				synchronized (this) {						
					ArCode = "AR_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET ASSET_REGISTRY = ASSET_REGISTRY + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						}

				
				/* assign the AR ID */
				AssetRegistry assetregistry = new AssetRegistry();
				
				assetregistry.setArID(ArCode);
				assetregistry.setAritemCode(itmcode);
				 query = session.createNativeQuery("SELECT item_category, item_catcode FROM Item WHERE item_code = :param1")
				                    .setParameter("param1", itmcode);
				 result = (Object[]) query.uniqueResult();
		        assetregistry.setItemCat(result[0].toString());
				assetregistry.setItemCatCode(result[1].toString());
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
				 

				/* check, if this itemCode related to poID, exist in CIP table (get the qty and cipID) */
				
				
				query = session.createQuery("Select t.TOTALQTY as totalqty, t.cipID as cipID from CapitalInProgress t where t.PoId =:param1 and t.cipitemCode =:param2");

				query.setParameter("param1", PurchaseOrId);
				query.setParameter("param2", itmcode);
			
				List result = query.list();
				
				String FarCode;

				/* if exist, get only one row, with CIPqty field and cipID */
				if (result != null) {
					for (Object obj : result) {
						Object[] fields = (Object[]) obj;

						String result1 = fields[0].toString();
						float updatedQty = Float.parseFloat(result1) - 1; // substract 1 from the TotalQty
						System.out.println("updatedQty is: "+updatedQty);

						if (updatedQty == 0) {
							/* delete from CIP table */

							
							query = session.createQuery("delete from CapitalInProgress c where c.cipID =:param1");

							query.setParameter("param1", fields[1]);
							query.executeUpdate();
							

						}

						else {

							/* update the qty in CIP table */
							
							query = session.createQuery("update CapitalInProgress c set c.TOTALQTY =:param1 where c.cipID =:param2");

							query.setParameter("param1", updatedQty);
							query.setParameter("param2", fields[1]);
							query.executeUpdate();
							

						}
						query = session.createNativeQuery("select USEFULL_LIFE_MONTHS from ITEM where ITEM_CODE = '"+itmcode+"'");
						query.executeUpdate();
						String item_Usefull_LifeMonths = (String) query.uniqueResult();
						float useful_life_month = 0;

						if (!StringUtils.equalsIgnoreCase(item_Usefull_LifeMonths, "")) { 
							
							useful_life_month = Float.parseFloat(item_Usefull_LifeMonths);
						}
                          synchronized (this) {						
							FarCode = "FAR_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FIXED_ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET FIXED_ASSET_REGISTRY = FIXED_ASSET_REGISTRY + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							}
						/*FarCode = "FAR_" + year + "_" + appConfig.getSequenceNbr(10); */
						
						/* insert the DisNewItem in FAR table */
						float initialCost = Float.parseFloat(itemParameters.getDictParameter().get(i).get(DN_RATE));
						
						FixedAssetRegistry FixedAssetReg = new FixedAssetRegistry();
						FixedAssetReg.setARID(ArCode);
						FixedAssetReg.setFarID(FarCode);
						FixedAssetReg.setFaritemCode(itmcode);
						query = session.createNativeQuery("SELECT item_category, item_catcode FROM Item WHERE item_code = :param1")
				                    .setParameter("param1", itmcode);
						Object[] res = (Object[]) query.uniqueResult();
                        FixedAssetReg.setItemCat(res[0].toString());
				        FixedAssetReg.setItemCatCode(res[1].toString());
				     	FixedAssetReg.setFarcreatedDate(new Timestamp(System.currentTimeMillis()));
						FixedAssetReg.setFarlastModifiedDate(new Timestamp(System.currentTimeMillis()));
						FixedAssetReg.setFaritemName(itmname);
						FixedAssetReg.setIniCost(initialCost);
						FixedAssetReg.setNetCost(initialCost);
						FixedAssetReg.setUsefulLifeMon(useful_life_month);
						FixedAssetReg.setDailyPercent(0);
						FixedAssetReg.setAccumPer(0);
						FixedAssetReg.setAccumDeprCode("0");
						FixedAssetReg.setDeprCode("0");
						FixedAssetReg.setDniID(DNItemID);
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
		

		Session session = AlmDbSession.getInstance().getSession();
		Transaction tx =null;
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
	try {


		String DN = "%" + request.getParameter("DN") + "%";
		
		query = session.createQuery("select t.dnID, t.dnStatus from DiscoveryNew t where t.dnID like :param1 or t.dnStatus like :param1 ORDER BY dnlastmodifDate DESC");
		query.setParameter("param1", DN);
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

	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/runDNScript", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runDNScript(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		Map<String,Object> map = new HashMap<String, Object>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
		}
		

			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			
			 session = AlmDbSession.getInstance().getSession();
			 tx = session.beginTransaction();
			if(session != null && session.isOpen()) {
				try {
				List<Object[]> boardList = new ArrayList<Object[]>(), cabinetList =  new ArrayList<Object[]>(),antennaList =  new ArrayList<Object[]>(),nodeList =  new ArrayList<Object[]>(),subrackList =  new ArrayList<Object[]>();
				List<Object[]> boardDisList =  new ArrayList<Object[]>(), cabinetDisList =  new ArrayList<Object[]>(),antennaDisList =  new ArrayList<Object[]>(),nodeDisList =  new ArrayList<Object[]>(),subrackDisList =  new ArrayList<Object[]>();
				
				/*getting the  new or transferred elements */
				String getBoardsquery,getCabinetsquery, getSubRackquery, getAntennasquery,getNodesQuery ; 
				String getDisBoardsquery,getDisCabinetsquery, getDisSubRackquery, getDisAntennasquery,getDisNodesQuery ;
				
				getBoardsquery = getMigrationQuery(session,"NODE_BOARD","BOARD_ID","1");
				if(getBoardsquery != null) {boardList = session.createNativeQuery(getBoardsquery).list();}
				
				getCabinetsquery = getMigrationQuery(session,"NODE_CABINET","CABINET_ID","1");
				if(getCabinetsquery != null) {cabinetList = session.createNativeQuery(getCabinetsquery).list();}
				
				getNodesQuery = getMigrationQuery(session,"NODE_ACTIVE","NODE_PK","1");
				if(getNodesQuery != null) {nodeList = session.createNativeQuery(getNodesQuery).list();}
				
				getAntennasquery = getMigrationQuery(session,"NODE_ANTENNA","ANTENNA_ID","1");
				if(getAntennasquery != null) {antennaList = session.createNativeQuery(getAntennasquery).list();}

				getSubRackquery = getMigrationQuery(session,"NODE_SUBRACK","SUBRACK_ID","1");
				if(getSubRackquery != null) {subrackList = session.createNativeQuery(getSubRackquery).list();}
				
				/////////////////////////////////////////////////////////////////////////////////////////////////
				
				getDisBoardsquery = getMigrationQuery(session,"NODE_BOARD","BOARD_ID","2");
				if(getDisBoardsquery != null) {boardDisList = session.createNativeQuery(getDisBoardsquery).list();}
				
				getDisCabinetsquery = getMigrationQuery(session,"NODE_CABINET","CABINET_ID","2");
				if(getDisCabinetsquery != null) {cabinetDisList = session.createNativeQuery(getDisCabinetsquery).list();}
				
				getDisNodesQuery = getMigrationQuery(session,"NODE_ACTIVE","NODE_PK","2");
				if(getDisNodesQuery != null) {nodeDisList = session.createNativeQuery(getDisNodesQuery).list();}
				
				getDisAntennasquery = getMigrationQuery(session,"NODE_ANTENNA","ANTENNA_ID","2");
				if(getDisAntennasquery != null) {antennaDisList = session.createNativeQuery(getDisAntennasquery).list();}

				getDisSubRackquery = getMigrationQuery(session,"NODE_SUBRACK","SUBRACK_ID","2");
				if(getDisSubRackquery != null) {subrackDisList = session.createNativeQuery(getDisSubRackquery).list();}
				
				
				if(nodeList.size() > 0 || cabinetList.size() > 0 || boardList.size() > 0 || antennaList.size() > 0 || subrackList.size() > 0
					|| nodeDisList.size() > 0 || cabinetDisList.size() > 0 || boardDisList.size() > 0 || antennaDisList.size() > 0  || subrackDisList.size() > 0) {
					
					String status = "Approved";
					String dnid; DiscoveryNew discoverynew = new DiscoveryNew();
					synchronized (this) {
						dnid = "DN_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
												}
					discoverynew.setDnID(dnid);
					discoverynew.setDncreationDate(new Timestamp(System.currentTimeMillis()));
					discoverynew.setdnlastmodifDate(new Timestamp(System.currentTimeMillis()));
					discoverynew.setDnStatus(status.toString());
					session.saveOrUpdate(discoverynew);
					
					insertDiscoveredElements(session,nodeList,dnid,"Node","New_Trans");
					insertDiscoveredElements(session,cabinetList,dnid,"Cabinet","New_Trans");
					insertDiscoveredElements(session,antennaList,dnid,"Antenna","New_Trans");
					insertDiscoveredElements(session,subrackList,dnid,"Subrack","New_Trans");
					insertDiscoveredElements(session,boardList,dnid,"Board","New_Trans");
					
					insertDiscoveredElements(session,nodeDisList,dnid,"Node","Disappear");
					insertDiscoveredElements(session,cabinetDisList,dnid,"Cabinet","Disappear");
					insertDiscoveredElements(session,antennaDisList,dnid,"Antenna","Disappear");
					insertDiscoveredElements(session,subrackDisList,dnid,"Subrack","Disappear");
					insertDiscoveredElements(session,boardDisList,dnid,"Board","Disappear");
					map.put("Result", "Done Successfully");
						
				}else {
					map.put("Result", "No Data to be Discovered");
					
				}
				
				}catch(Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					map.put("Result", "Error Occured");
				}
				
				finally {
					tx.commit();
					session.close();
					
				}
			}
		return map;
	}
	
/* These scripts to get the discovered items by the Auto Parser */	
	
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	@RequestMapping(value = "/runDiscoveryNewScript", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runDiscoveryNewScript(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		Map<String,Object> map = new HashMap<String, Object>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
		}
		

			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			
			 session = AlmDbSession.getInstance().getSession();
			 tx = session.beginTransaction();
			if(session != null && session.isOpen()) {
				try {
				String getBoardsquery,getCabinetsquery, getSubRackquery, getAntennasquery, getAntennaquery,getNodesQuery ; 
				String getDisBoardsquery,getDisCabinetsquery, getDisSubRackquery, getDisAntennasquery, getDisAntennaquery,getDisNodesQuery ;
				NativeQuery q;
				List<Object[]> boardList, cabinetList,antennaList,nodeList,subrackList;
				List<Object[]> boardDisList, cabinetDisList,antennaDisList,nodeDisList,subrackDisList;
				List<Object[]> ExistedNodeBoardsList; List<Object[]> ExistedNodeCabinetsList; List<Object[]> ExistedNodeAntennasList;
				
				/* getting queries for new and transferred site for nodes,cabinets,antennas,boards and subracks */
				getNodesQuery="select t.trans_id as transID,t.element_id as elementID,t.element as elementName,a.node_model as nodeModel," + 
						"t.discovered_trans_type as transType,t.from_site as fromSite,t.to_site as toSite,a.node_pk," + 
						"(select ware_id from node_active a where a.site_id=t.from_site and a.node_pk=t.element_id) as fromWareID,(select ware_id from node_active a where a.site_id=t.to_site and a.node_pk=t.element_id) as toWareID," + 
						"(select ware_name from node_active a where a.site_id=t.from_site and a.node_pk=t.element_id) as fromWareName,(select ware_name from node_active a where a.site_id=t.to_site and a.node_pk=t.element_id) as toWareName" + 
						" from network_transaction t  inner join node_active a on t.element_id=a.node_pk" + 
						" where t.sent_to_alm='0' and active_record='1'";
				q = session.createNativeQuery(getNodesQuery);
				nodeList = q.list();
				
				
				getCabinetsquery="select t.trans_id as transID,t.element_id as elementID,t.element as elementName,c.model as nodeModel," + 
						"t.discovered_trans_type as transType,t.from_site as fromSite,t.to_site as toSite,c.node_pk," + 
						"(select ware_id from node_active a where a.site_id=t.from_site and a.node_pk=c.node_pk) as fromWareID,(select ware_id from node_active a where a.site_id=t.to_site and a.node_pk=c.node_pk) as toWareID," + 
						"(select ware_name from node_active a where a.site_id=t.from_site and a.node_pk=c.node_pk) as fromWareName,(select ware_name from node_active a where a.site_id=t.to_site and a.node_pk=c.node_pk) as toWareName" + 
						" from network_transaction t  inner join node_cabinet c on t.element_id=c.cabinet_id" + 
						" where t.sent_to_alm='0' and c.active_record='1'";
				
				q = session.createNativeQuery(getCabinetsquery);
				cabinetList=q.list();

				getBoardsquery="select t.trans_id as transID,t.element_id as elementID,t.element as elementName,b.model as nodeModel," + 
						"t.discovered_trans_type as transType,t.from_site as fromSite,t.to_site as toSite,b.node_pk," + 
						"(select ware_id from node_active a where a.site_id=t.from_site and a.node_pk=b.node_pk) as fromWareID,(select ware_id from node_active a where a.site_id=t.to_site and a.node_pk=b.node_pk) as toWareID," + 
						"(select ware_name from node_active a where a.site_id=t.from_site and a.node_pk=b.node_pk) as fromWareName,(select ware_name from node_active a where a.site_id=t.to_site and a.node_pk=b.node_pk) as toWareName" + 
						" from network_transaction t  inner join node_board b on t.element_id=b.board_id" + 
						" where t.sent_to_alm='0' and b.active_record='1'";
				q = session.createNativeQuery(getBoardsquery);
				boardList=q.list();

				getAntennaquery="select t.trans_id as transID,t.element_id as elementID,t.element as elementName,n.model as nodeModel," + 
						"t.discovered_trans_type as transType,t.from_site as fromSite,t.to_site as toSite,n.node_pk," + 
						"(select ware_id from node_active a where a.site_id=t.from_site and a.node_pk=n.node_pk) as fromWareID,(select ware_id from node_active a where a.site_id=t.to_site and a.node_pk=n.node_pk) as toWareID," + 
						"(select ware_name from node_active a where a.site_id=t.from_site and a.node_pk=n.node_pk) as fromWareName,(select ware_name from node_active a where a.site_id=t.to_site and a.node_pk=n.node_pk) as toWareName" + 
						" from network_transaction t  inner join node_antenna n on t.element_id=n.antenna_id" + 
						" where t.sent_to_alm='0' and n.active_record='1'";
				q = session.createNativeQuery(getAntennaquery);
				antennaList=q.list();
				
				getSubRackquery="select t.trans_id as transID,t.element_id as elementID,t.element as elementName,s.model as nodeModel," + 
						"t.discovered_trans_type as transType,t.from_site as fromSite,t.to_site as toSite,s.node_pk," + 
						"(select ware_id from node_active a where a.site_id=t.from_site and a.node_pk=s.node_pk) as fromWareID,(select ware_id from node_active a where a.site_id=t.to_site and a.node_pk=s.node_pk) as toWareID," + 
						"(select ware_name from node_active a where a.site_id=t.from_site and a.node_pk=s.node_pk) as fromWareName,(select ware_name from node_active a where a.site_id=t.to_site and a.node_pk=s.node_pk) as toWareName" + 
						" from network_transaction t  inner join node_subrack s on t.element_id=s.subrack_id" + 
						" where t.sent_to_alm='0' and s.active_record='1'";
				q=session.createNativeQuery(getSubRackquery);
				subrackList=q.list();
				
				/* getting queries for disappeared nodes,cabinets,antennas,boards and subracks */
				getDisNodesQuery="select t.trans_id as transID,t.element_id as elementID,t.element as elementName,a.node_model as nodeModel," + 
						"t.discovered_trans_type as transType,t.from_site as fromSite,t.to_site as toSite,a.node_pk," + 
						"(select ware_id from node_active a where a.site_id=t.from_site and a.node_pk=t.element_id) as fromWareID,(select ware_id from node_active a where a.site_id=t.to_site and a.node_pk=t.element_id) as toWareID," + 
						"(select ware_name from node_active a where a.site_id=t.from_site and a.node_pk=t.element_id) as fromWareName,(select ware_name from node_active a where a.site_id=t.to_site and a.node_pk=t.element_id) as toWareName" + 
						" from network_transaction t  inner join node_active a on t.element_id=a.node_pk" + 
						" where t.sent_to_alm='0' and active_record='2'";
				q = session.createNativeQuery(getDisNodesQuery);
				nodeDisList = q.list();
				
				
				getDisCabinetsquery="select t.trans_id as transID,t.element_id as elementID,t.element as elementName,c.model as nodeModel," + 
						"t.discovered_trans_type as transType,t.from_site as fromSite,t.to_site as toSite,c.node_pk," + 
						"(select ware_id from node_active a where a.site_id=t.from_site and a.node_pk=c.node_pk) as fromWareID,(select ware_id from node_active a where a.site_id=t.to_site and a.node_pk=c.node_pk) as toWareID," + 
						"(select ware_name from node_active a where a.site_id=t.from_site and a.node_pk=c.node_pk) as fromWareName,(select ware_name from node_active a where a.site_id=t.to_site and a.node_pk=c.node_pk) as toWareName" + 
						" from network_transaction t  inner join node_cabinet c on t.element_id=c.cabinet_id" + 
						" where t.sent_to_alm='0' and c.active_record='2'";
				
				q = session.createNativeQuery(getDisCabinetsquery);
				cabinetDisList=q.list();

				getDisBoardsquery="select t.trans_id as transID,t.element_id as elementID,t.element as elementName,b.model as nodeModel," + 
						"t.discovered_trans_type as transType,t.from_site as fromSite,t.to_site as toSite,b.node_pk," + 
						"(select ware_id from node_active a where a.site_id=t.from_site and a.node_pk=b.node_pk) as fromWareID,(select ware_id from node_active a where a.site_id=t.to_site and a.node_pk=b.node_pk) as toWareID," + 
						"(select ware_name from node_active a where a.site_id=t.from_site and a.node_pk=b.node_pk) as fromWareName,(select ware_name from node_active a where a.site_id=t.to_site and a.node_pk=b.node_pk) as toWareName" + 
						" from network_transaction t  inner join node_board b on t.element_id=b.board_id" + 
						" where t.sent_to_alm='0' and b.active_record='2'";
				q = session.createNativeQuery(getDisBoardsquery);
				boardDisList=q.list();

				getDisAntennaquery="select t.trans_id as transID,t.element_id as elementID,t.element as elementName,n.model as nodeModel," + 
						"t.discovered_trans_type as transType,t.from_site as fromSite,t.to_site as toSite,n.node_pk," + 
						"(select ware_id from node_active a where a.site_id=t.from_site and a.node_pk=n.node_pk) as fromWareID,(select ware_id from node_active a where a.site_id=t.to_site and a.node_pk=n.node_pk) as toWareID," + 
						"(select ware_name from node_active a where a.site_id=t.from_site and a.node_pk=n.node_pk) as fromWareName,(select ware_name from node_active a where a.site_id=t.to_site and a.node_pk=n.node_pk) as toWareName" + 
						" from network_transaction t  inner join node_antenna n on t.element_id=n.antenna_id" + 
						" where t.sent_to_alm='0' and n.active_record='2'";
				q = session.createNativeQuery(getDisAntennaquery);
				antennaDisList=q.list();
				
				getDisSubRackquery="select t.trans_id as transID,t.element_id as elementID,t.element as elementName,s.model as nodeModel," + 
						"t.discovered_trans_type as transType,t.from_site as fromSite,t.to_site as toSite,s.node_pk," + 
						"(select ware_id from node_active a where a.site_id=t.from_site and a.node_pk=s.node_pk) as fromWareID,(select ware_id from node_active a where a.site_id=t.to_site and a.node_pk=s.node_pk) as toWareID," + 
						"(select ware_name from node_active a where a.site_id=t.from_site and a.node_pk=s.node_pk) as fromWareName,(select ware_name from node_active a where a.site_id=t.to_site and a.node_pk=s.node_pk) as toWareName" + 
						" from network_transaction t  inner join node_subrack s on t.element_id=s.subrack_id" + 
						" where t.sent_to_alm='0' and s.active_record='2'";
				q=session.createNativeQuery(getDisSubRackquery);
				subrackDisList=q.list();
				
				String dnid; DiscoveryNew discoverynew = new DiscoveryNew();
				synchronized (this) {
					dnid = "DND_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
				}
				
				Date date1;
				
				date1 = new Timestamp(System.currentTimeMillis());
				Timestamp CreationDate = new Timestamp(date1.getTime());
					
				List<String> transBoardIDlist = new ArrayList<>();
				List<String> transnodeIDlist = new ArrayList<>();
				List<String> transCabinetIDlist = new ArrayList<>();
				List<String> transAntennaIDlist = new ArrayList<>();
				List<String> transSubRackIDlist = new ArrayList<>();
				
				Object [] arrayList=null;
				if(nodeList.size() > 0 || cabinetList.size() > 0 || boardList.size() > 0 || antennaList.size() > 0 || subrackList.size() > 0
				   || nodeDisList.size() > 0 || cabinetDisList.size() > 0 || boardDisList.size() > 0 || antennaDisList.size() > 0 || subrackDisList.size() > 0) {
					System.out.println("inside if condition");
					discoverynew.setDnID(dnid);
					discoverynew.setDncreationDate(CreationDate);
					discoverynew.setdnlastmodifDate(CreationDate);
					session.saveOrUpdate(discoverynew);
					
					String DniID,node_pk = "",trans_id="",element_id="";
					for(int i=0;i< nodeList.size();i++) {
						System.out.println(mapper.writeValueAsString(nodeList));
						DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
						arrayList=  nodeList.get(i);
						trans_id = arrayList[0].toString();
						node_pk = arrayList[7].toString();
						System.out.println(node_pk);
						synchronized (this) {
							DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
						}
						
						if(transnodeIDlist.indexOf(node_pk) < 0){transnodeIDlist.add(node_pk);}
						discoverynewitem.setDniID(DniID);
						discoverynewitem.setTransID(arrayList[0].toString());
						discoverynewitem.setElementName(arrayList[2].toString());
						discoverynewitem.setItemModel(arrayList[3].toString());
						discoverynewitem.setTransType(arrayList[4].toString());
						discoverynewitem.setDniSIte(arrayList[5].toString());
						discoverynewitem.setToSite(arrayList[6].toString());
						discoverynewitem.setWareID(arrayList[8].toString());
						discoverynewitem.setToWareId(arrayList[9].toString());
						discoverynewitem.setWareName(arrayList[10].toString());
						discoverynewitem.setToWareName(arrayList[11].toString());
						discoverynewitem.setDniDNID(dnid);
						discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
						discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
						discoverynewitem.setDniAPPROVAL("Project Manager");
						
						session.saveOrUpdate(discoverynewitem);
					}
					
					for(int i=0;i< cabinetList.size();i++) {
						DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
						arrayList=  cabinetList.get(i);
						trans_id = arrayList[0].toString();
						element_id=arrayList[1].toString();
						node_pk = arrayList[7].toString();
						System.out.println(node_pk);
						synchronized (this) {
							DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
						}
						
						if(transCabinetIDlist.indexOf(element_id) < 0){transCabinetIDlist.add(element_id);}
						discoverynewitem.setDniID(DniID);
						discoverynewitem.setTransID(arrayList[0].toString());
						discoverynewitem.setElementName(arrayList[2].toString());
						discoverynewitem.setItemModel(arrayList[3].toString());
						discoverynewitem.setTransType(arrayList[4].toString());
						discoverynewitem.setDniSIte(arrayList[5].toString());
						discoverynewitem.setToSite(arrayList[6].toString());
						discoverynewitem.setWareID(arrayList[8].toString());
						discoverynewitem.setToWareId(arrayList[9].toString());
						discoverynewitem.setWareName(arrayList[10].toString());
						discoverynewitem.setToWareName(arrayList[11].toString());
						discoverynewitem.setDniDNID(dnid);
						discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
						discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
						discoverynewitem.setDniAPPROVAL("Project Manager");
						
						session.saveOrUpdate(discoverynewitem);
					}
					
					for(int i=0;i< boardList.size();i++) {
						System.out.println(mapper.writeValueAsString(nodeList));
						DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
						arrayList=  boardList.get(i);
						trans_id = arrayList[0].toString();
						element_id=arrayList[1].toString();
						node_pk = arrayList[7].toString();
						System.out.println(node_pk);
						synchronized (this) {
							DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
						}
						
						if(transBoardIDlist.indexOf(element_id) < 0){transBoardIDlist.add(element_id);}
						discoverynewitem.setDniID(DniID);
						discoverynewitem.setTransID(arrayList[0].toString());
						discoverynewitem.setElementName(arrayList[2].toString());
						discoverynewitem.setItemModel(arrayList[3].toString());
						discoverynewitem.setTransType(arrayList[4].toString());
						discoverynewitem.setDniSIte(arrayList[5].toString());
						discoverynewitem.setToSite(arrayList[6].toString());
						discoverynewitem.setWareID(arrayList[8].toString());
						discoverynewitem.setToWareId(arrayList[9].toString());
						discoverynewitem.setWareName(arrayList[10].toString());
						discoverynewitem.setToWareName(arrayList[11].toString());
						discoverynewitem.setDniDNID(dnid);
						discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
						discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
						discoverynewitem.setDniAPPROVAL("Project Manager");
						
						session.saveOrUpdate(discoverynewitem);
					}
					
					for(int i=0;i< antennaList.size();i++) {
						System.out.println(mapper.writeValueAsString(nodeList));
						DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
						arrayList=  antennaList.get(i);
						trans_id = arrayList[0].toString();
						element_id=arrayList[1].toString();
						node_pk = arrayList[7].toString();
						System.out.println(node_pk);
						synchronized (this) {
							DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
						}
						
						if(transAntennaIDlist.indexOf(element_id) < 0){transAntennaIDlist.add(element_id);}
						discoverynewitem.setDniID(DniID);
						discoverynewitem.setTransID(arrayList[0].toString());
						discoverynewitem.setElementName(arrayList[2].toString());
						discoverynewitem.setItemModel(arrayList[3].toString());
						discoverynewitem.setTransType(arrayList[4].toString());
						discoverynewitem.setDniSIte(arrayList[5].toString());
						discoverynewitem.setToSite(arrayList[6].toString());
						discoverynewitem.setWareID(arrayList[8].toString());
						discoverynewitem.setToWareId(arrayList[9].toString());
						discoverynewitem.setWareName(arrayList[10].toString());
						discoverynewitem.setToWareName(arrayList[11].toString());
						discoverynewitem.setDniDNID(dnid);
						discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
						discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
						discoverynewitem.setDniAPPROVAL("Project Manager");
						
						session.saveOrUpdate(discoverynewitem);
					}
					
					for(int i=0;i< subrackList.size();i++) {
						System.out.println(mapper.writeValueAsString(nodeList));
						DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
						arrayList=  subrackList.get(i);
						trans_id = arrayList[0].toString();
						element_id=arrayList[1].toString();
						node_pk = arrayList[7].toString();
						System.out.println(node_pk);
						synchronized (this) {
							DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
						}
						
						if(transSubRackIDlist.indexOf(element_id) < 0){transSubRackIDlist.add(element_id);}
						discoverynewitem.setDniID(DniID);
						discoverynewitem.setTransID(arrayList[0].toString());
						discoverynewitem.setElementName(arrayList[2].toString());
						discoverynewitem.setItemModel(arrayList[3].toString());
						discoverynewitem.setTransType(arrayList[4].toString());
						discoverynewitem.setDniSIte(arrayList[5].toString());
						discoverynewitem.setToSite(arrayList[6].toString());
						discoverynewitem.setWareID(arrayList[8].toString());
						discoverynewitem.setToWareId(arrayList[9].toString());
						discoverynewitem.setWareName(arrayList[10].toString());
						discoverynewitem.setToWareName(arrayList[11].toString());
						discoverynewitem.setDniDNID(dnid);
						discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
						discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
						discoverynewitem.setDniAPPROVAL("Project Manager");
						
						session.saveOrUpdate(discoverynewitem);
					}
					
					for(int i=0;i< nodeDisList.size();i++) {
						System.out.println(mapper.writeValueAsString(nodeList));
						DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
						arrayList=  nodeDisList.get(i);
						trans_id = arrayList[0].toString();
						node_pk = arrayList[7].toString();
						System.out.println(node_pk);
						synchronized (this) {
							DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
						}
						
						if(transnodeIDlist.indexOf(node_pk) < 0){transnodeIDlist.add(node_pk);}
						discoverynewitem.setDniID(DniID);
						discoverynewitem.setTransID(arrayList[0].toString());
						discoverynewitem.setElementName(arrayList[2].toString());
						discoverynewitem.setItemModel(arrayList[3].toString());
						discoverynewitem.setTransType(arrayList[4].toString());
						discoverynewitem.setDniSIte(arrayList[5].toString());
						discoverynewitem.setToSite(arrayList[6].toString());
						discoverynewitem.setWareID(arrayList[8].toString());
						discoverynewitem.setToWareId(arrayList[9].toString());
						discoverynewitem.setWareName(arrayList[10].toString());
						discoverynewitem.setToWareName(arrayList[11].toString());
						discoverynewitem.setDniDNID(dnid);
						discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
						discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
						discoverynewitem.setDniAPPROVAL("Project Manager");
						
						session.saveOrUpdate(discoverynewitem);
					}
					
					for(int i=0;i< cabinetDisList.size();i++) {
						System.out.println(mapper.writeValueAsString(nodeList));
						DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
						arrayList=  cabinetDisList.get(i);
						trans_id = arrayList[0].toString();
						element_id=arrayList[1].toString();
						node_pk = arrayList[7].toString();
						System.out.println(node_pk);
						synchronized (this) {
							DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
						}
						
						if(transCabinetIDlist.indexOf(element_id) < 0){transCabinetIDlist.add(element_id);}
						discoverynewitem.setDniID(DniID);
						discoverynewitem.setTransID(arrayList[0].toString());
						discoverynewitem.setElementName(arrayList[2].toString());
						discoverynewitem.setItemModel(arrayList[3].toString());
						discoverynewitem.setTransType(arrayList[4].toString());
						discoverynewitem.setDniSIte(arrayList[5].toString());
						discoverynewitem.setToSite(arrayList[6].toString());
						discoverynewitem.setWareID(arrayList[8].toString());
						discoverynewitem.setToWareId(arrayList[9].toString());
						discoverynewitem.setWareName(arrayList[10].toString());
						discoverynewitem.setToWareName(arrayList[11].toString());
						discoverynewitem.setDniDNID(dnid);
						discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
						discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
						discoverynewitem.setDniAPPROVAL("Project Manager");
						
						session.saveOrUpdate(discoverynewitem);
					}
					
					for(int i=0;i< boardDisList.size();i++) {
						System.out.println(mapper.writeValueAsString(nodeList));
						DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
						arrayList=  boardDisList.get(i);
						trans_id = arrayList[0].toString();
						element_id=arrayList[1].toString();
						node_pk = arrayList[7].toString();
						System.out.println(node_pk);
						synchronized (this) {
							DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
						}
						
						if(transBoardIDlist.indexOf(element_id) < 0){transBoardIDlist.add(element_id);}
						discoverynewitem.setDniID(DniID);
						discoverynewitem.setTransID(arrayList[0].toString());
						discoverynewitem.setElementName(arrayList[2].toString());
						discoverynewitem.setItemModel(arrayList[3].toString());
						discoverynewitem.setTransType(arrayList[4].toString());
						discoverynewitem.setDniSIte(arrayList[5].toString());
						discoverynewitem.setToSite(arrayList[6].toString());
						discoverynewitem.setWareID(arrayList[8].toString());
						discoverynewitem.setToWareId(arrayList[9].toString());
						discoverynewitem.setWareName(arrayList[10].toString());
						discoverynewitem.setToWareName(arrayList[11].toString());
						discoverynewitem.setDniDNID(dnid);
						discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
						discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
						discoverynewitem.setDniAPPROVAL("Project Manager");
						
						session.saveOrUpdate(discoverynewitem);
					}
					
					for(int i=0;i< antennaDisList.size();i++) {
						System.out.println(mapper.writeValueAsString(nodeList));
						DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
						arrayList=  antennaDisList.get(i);
						trans_id = arrayList[0].toString();
						element_id=arrayList[1].toString();
						node_pk = arrayList[7].toString();
						System.out.println(node_pk);
						synchronized (this) {
							DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
						}
						
						if(transAntennaIDlist.indexOf(element_id) < 0){transAntennaIDlist.add(element_id);}
						discoverynewitem.setDniID(DniID);
						discoverynewitem.setTransID(arrayList[0].toString());
						discoverynewitem.setElementName(arrayList[2].toString());
						discoverynewitem.setItemModel(arrayList[3].toString());
						discoverynewitem.setTransType(arrayList[4].toString());
						discoverynewitem.setDniSIte(arrayList[5].toString());
						discoverynewitem.setToSite(arrayList[6].toString());
						discoverynewitem.setWareID(arrayList[8].toString());
						discoverynewitem.setToWareId(arrayList[9].toString());
						discoverynewitem.setWareName(arrayList[10].toString());
						discoverynewitem.setToWareName(arrayList[11].toString());
						discoverynewitem.setDniDNID(dnid);
						discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
						discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
						discoverynewitem.setDniAPPROVAL("Project Manager");
						
						session.saveOrUpdate(discoverynewitem);
					}
					
					for(int i=0;i< subrackDisList.size();i++) {
						System.out.println(mapper.writeValueAsString(nodeList));
						DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
						arrayList=  subrackDisList.get(i);
						trans_id = arrayList[0].toString();
						element_id=arrayList[1].toString();
						node_pk = arrayList[7].toString();
						System.out.println(node_pk);
						synchronized (this) {
							DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
						}
						
						if(transSubRackIDlist.indexOf(element_id) < 0){transSubRackIDlist.add(element_id);}
						discoverynewitem.setDniID(DniID);
						discoverynewitem.setTransID(arrayList[0].toString());
						discoverynewitem.setElementName(arrayList[2].toString());
						discoverynewitem.setItemModel(arrayList[3].toString());
						discoverynewitem.setTransType(arrayList[4].toString());
						discoverynewitem.setDniSIte(arrayList[5].toString());
						discoverynewitem.setToSite(arrayList[6].toString());
						discoverynewitem.setWareID(arrayList[8].toString());
						discoverynewitem.setToWareId(arrayList[9].toString());
						discoverynewitem.setWareName(arrayList[10].toString());
						discoverynewitem.setToWareName(arrayList[11].toString());
						discoverynewitem.setDniDNID(dnid);
						discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
						discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
						discoverynewitem.setDniAPPROVAL("Project Manager");
						
						session.saveOrUpdate(discoverynewitem);
					}
					for(int i=0;i<transnodeIDlist.size();i++) {
						String nodeID = transnodeIDlist.get(i);
						query = session.createNativeQuery("update network_transaction set sent_to_alm = '1' where element_id ='"+transnodeIDlist.get(i)+"'");
						query.executeUpdate();
					}
					
					for(int i=0;i<transCabinetIDlist.size();i++) {
						query = session.createNativeQuery("update network_transaction set sent_to_alm = '1' where element_id ='"+transCabinetIDlist.get(i)+"'");
						query.executeUpdate();
					}
					
					for(int i=0;i<transBoardIDlist.size();i++) {
						query = session.createNativeQuery("update network_transaction set sent_to_alm = '1' where element_id ='"+transBoardIDlist.get(i)+"'");
						query.executeUpdate();
					}
					
					for(int i=0;i<transAntennaIDlist.size();i++) {
						query = session.createNativeQuery("update network_transaction set sent_to_alm = '1' where element_id ='"+transAntennaIDlist.get(i)+"'");
						query.executeUpdate();
					}
					
					for(int i=0;i<transSubRackIDlist.size();i++) {
						query = session.createNativeQuery("update network_transaction set sent_to_alm = '1' where element_id ='"+transSubRackIDlist.get(i)+"'");
						query.executeUpdate();
					}
				}
				}catch(Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				
				finally {
					tx.commit();
					session.close();
					
				}
			}
		map.put("DeleteTest", "DeleteDone");
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
			Session session = AlmDbSession.getInstance().getSession();
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
			
			
				
			
			q = session.createNativeQuery(getTransferFromSlotToSlotsquery);
			q2 = session.createNativeQuery(getCabinetTransfertoNewNode);
			q3  = session.createNativeQuery(getAntennaTransferToAnotherNode);
			//q3 = session.createNativeQuery(getTransferFromSiteToSite);
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
				dnid = "DND_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
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
					DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
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
				discoverynewitem.setItemModel(arrayList[9].toString());
				discoverynewitem.setDniSIte(arrayList[11].toString());
				discoverynewitem.setToSite(arrayList[12].toString());
				discoverynewitem.setPosition(arrayList[13].toString());
				discoverynewitem.setDescription(arrayList[14].toString());
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
				
				Query updateNodetrans = session.createNativeQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
					DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
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
				discoverynewitem.setItemModel(arrayList[6].toString());
			    discoverynewitem.setDniSIte(arrayList[8].toString());
				discoverynewitem.setToSite(arrayList[9].toString());
				discoverynewitem.setPosition(arrayList[10].toString());
				discoverynewitem.setDescription(arrayList[11].toString());
				discoverynewitem.setElementName("Cabinet");
				discoverynewitem.setDniDNID(dnid);
				discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
				discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
				discoverynewitem.setDniAPPROVAL("Operation Manager");
				discoverynewitem.setTransType("Transfer from Node to Node Within Site");
				
				//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
				session.saveOrUpdate(discoverynewitem);
				
				Query updateNodetrans = session.createNativeQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
					DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
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
				discoverynewitem.setItemModel(arrayList[6].toString());
				discoverynewitem.setDniSIte(arrayList[8].toString());
				discoverynewitem.setToSite(arrayList[9].toString());
				//discoverynewitem.setPosition(arrayList[13].toString());
				discoverynewitem.setDescription(arrayList[10].toString());
				discoverynewitem.setElementName("Antenna");
				discoverynewitem.setDniDNID(dnid);
				discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
				discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
				discoverynewitem.setDniAPPROVAL("Operation Manager");
				discoverynewitem.setTransType("Transfer from Node to Node Within Site");
				
				//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
				session.saveOrUpdate(discoverynewitem);
				
				Query updateNodetrans = session.createNativeQuery("update node_antenna_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
			
			Session session = AlmDbSession.getInstance().getSession();
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
			
				
			
			q = session.createNativeQuery(getReplaceBoards);
			q2 = session.createNativeQuery(getReplaceCabinet);
			q3  = session.createNativeQuery(getReplaceAntenna);
		
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
				dnid = "DND_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
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
					DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
				}
				trans_id = arrayList[0].toString();
				node_pk = arrayList[10].toString();
				
				discoverynewitem.setTransID(trans_id);
				discoverynewitem.setDniID(DniID);
				discoverynewitem.setDniSN(arrayList[1].toString());
				discoverynewitem.setToSerialNumber(arrayList[2].toString());
				discoverynewitem.setItemModel(arrayList[9].toString());
				discoverynewitem.setToSite(arrayList[6].toString());
				discoverynewitem.setToSlot(arrayList[7].toString());
				discoverynewitem.setDescription(arrayList[13].toString());	
				discoverynewitem.setElementName("Board");
				discoverynewitem.setDniDNID(dnid);
				discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
				discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
				discoverynewitem.setDniAPPROVAL("Project Manager");
				discoverynewitem.setTransType("Replacement");
				
				
				//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
				session.saveOrUpdate(discoverynewitem);
				
				Query updateBoardReplacement = session.createNativeQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
				updateBoardReplacement.setParameter("param1", trans_id);
				updateBoardReplacement.setParameter("param2", node_pk);
				updateBoardReplacement.executeUpdate();
			}
			
			
			for(int i=0; i<ReplaceCabinetList.size(); i++)
			{
				DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
				Object [] arrayList=  ReplaceCabinetList.get(i);
				synchronized (this) {						
					DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						}
				trans_id = arrayList[0].toString();
				node_pk = arrayList[10].toString();

				discoverynewitem.setTransID(trans_id);
				discoverynewitem.setDniID(DniID);
				discoverynewitem.setDniSN(arrayList[1].toString());
				discoverynewitem.setToSerialNumber(arrayList[2].toString());
				discoverynewitem.setTransSrc(arrayList[3].toString());
				discoverynewitem.setToSite(arrayList[6].toString());
				discoverynewitem.setToSlot(arrayList[7].toString());
				discoverynewitem.setItemModel(arrayList[9].toString());
				discoverynewitem.setDescription(arrayList[13].toString());	
				discoverynewitem.setElementName("Cabinet");
				discoverynewitem.setDniDNID(dnid);
				discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
				discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
				discoverynewitem.setDniAPPROVAL("Project Manager");
				discoverynewitem.setTransType("Replacement");
				
				
				//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
				session.saveOrUpdate(discoverynewitem);
				
				Query updateCabinetReplacement = session.createNativeQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
				updateCabinetReplacement.setParameter("param1", trans_id);
				updateCabinetReplacement.setParameter("param2", node_pk);
				updateCabinetReplacement.executeUpdate();
			}
				for(int i=0; i<ReplaceAntennaList.size(); i++)
			{
					DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
					Object [] arrayList=  ReplaceAntennaList.get(i);
					synchronized (this) {						
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[10].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setToSerialNumber(arrayList[2].toString());
					discoverynewitem.setTransSrc(arrayList[3].toString());
			       	discoverynewitem.setToSite(arrayList[5].toString());
					discoverynewitem.setToSlot(arrayList[6].toString());
					discoverynewitem.setItemModel(arrayList[7].toString());
					discoverynewitem.setDescription(arrayList[12].toString());	
					discoverynewitem.setElementName("Antenna");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Project Manager");
					discoverynewitem.setTransType("Replacement");
					
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateAntennaReplacement = session.createNativeQuery("update node_antenna_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
			Session session = AlmDbSession.getInstance().getSession();
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

			
			q = session.createNativeQuery(getBoardDisappear);
			q1 = session.createNativeQuery(getCabinetDisappear);
			q2 = session.createNativeQuery(getAntennaDisappear);
			//q3 = session.createNativeQuery(getNodeDisappear);
			q4 = session.createNativeQuery(getBoardDisappearForTransfer);
			q5 = session.createNativeQuery(getBoardDisappearForMaintenance);
			q6 = session.createNativeQuery(getCabinetDisappearForTransfer);
			q7 = session.createNativeQuery(getCabinetDisappearForMaintenance);
			q8 = session.createNativeQuery(getAntennaDisappearForTransfer);
			
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
				dnid = "DND_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
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
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
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
					discoverynewitem.setItemModel(arrayList[5].toString());
					//discoverynewitem.setDniSIte(arrayList[7].toString());
					discoverynewitem.setPosition(arrayList[8].toString());
					//discoverynewitem.setToSlot(arrayList[8].toString());
					discoverynewitem.setDescription(arrayList[9].toString());
					discoverynewitem.setElementName("Board");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Project Manager");
					discoverynewitem.setTransType("Disappear");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createNativeQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
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
					//discoverynewitem.setNodeName(arrayList[4].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
					discoverynewitem.setDniSIte(arrayList[7].toString());
					//discoverynewitem.setToSite(arrayList[12].toString());
					discoverynewitem.setPosition(arrayList[8].toString());
					discoverynewitem.setDescription(arrayList[9].toString());
					discoverynewitem.setElementName("Cabinet");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Project Manager");
					discoverynewitem.setTransType("Disappear");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createNativeQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
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
					discoverynewitem.setItemModel(arrayList[6].toString());
					discoverynewitem.setDniSIte(arrayList[8].toString());
					discoverynewitem.setToSite(arrayList[9].toString());
					//discoverynewitem.setPosition(arrayList[13].toString());
					discoverynewitem.setDescription(arrayList[10].toString());
					discoverynewitem.setElementName("Antenna");
					discoverynewitem.setDniDNID(dnid);
					discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
					discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
					discoverynewitem.setDniAPPROVAL("Project Manager");
					discoverynewitem.setTransType("Disappear");
					
					//appConfig.persist(discoverynewitem, DsicoveryNewItem.class);
					session.saveOrUpdate(discoverynewitem);
					
					Query updateNodetrans = session.createNativeQuery("update node_antenna_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
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
					
					Query updateNodetrans = session.createNativeQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
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
					
					Query updateNodetrans = session.createNativeQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
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
					
					Query updateNodetrans = session.createNativeQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
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
					
					Query updateNodetrans = session.createNativeQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							}
					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
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
					
					Query updateNodetrans = session.createNativeQuery("update node_antenna_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
			
			Session session = AlmDbSession.getInstance().getSession();
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
			





                        q = session.createNativeQuery(getBoardMaintenance);
			q1 = session.createNativeQuery(getCabinetMaintenance);
			q2 = session.createNativeQuery(getAntennaMaintenance);
			
			boardMaintenenceList = q.list();
			cabinetMaintenenceList = q1.list();
			antennaMaintenenceList = q2.list();
			


                       System.out.println("List of Board Disappear "+mapper.writeValueAsString(boardMaintenenceList));
		       System.out.println("List of Cabient Disappear "+mapper.writeValueAsString(cabinetMaintenenceList));
		       System.out.println("List of Antenna Disappear "+mapper.writeValueAsString(antennaMaintenenceList));



                       String dnid; DiscoveryNew discoverynew = new DiscoveryNew();
			//dnid = "DND_" + year + "_" + appConfig.getSequenceNbr(6);
			synchronized (this) {						
				dnid = "DND_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW = DISCOVERY_NEW + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
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
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							}

					trans_id = arrayList[0].toString();
					node_pk = arrayList[6].toString();

					discoverynewitem.setTransID(trans_id);
					discoverynewitem.setDniID(DniID);
					discoverynewitem.setDniSN(arrayList[1].toString());
					discoverynewitem.setTransSrc(arrayList[2].toString());
					discoverynewitem.setItemModel(arrayList[5].toString());
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
					
					Query updateNodetrans = session.createNativeQuery("update node_board_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
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
		     		discoverynewitem.setItemModel(arrayList[9].toString());
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
					
					Query updateNodetrans = session.createNativeQuery("update node_cabinet_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
						DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
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
					discoverynewitem.setItemModel(arrayList[9].toString());
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
					
					Query updateNodetrans = session.createNativeQuery("update node_antenna_transactions set sent_to_alm = '1' where trans_id = :param1 and node_pk = :param2");
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
@RequestMapping(value = "/DN_Approval", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> DN_Approval(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
		}
		session = AlmDbSession.getInstance().getSession();
		if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
			try {
				System.out.println(request.getParameter("dnID"));
				System.out.println(request.getParameter("status"));

				query = session.createNativeQuery("UPDATE DISCOVERY_NEW SET STATUS ='"+request.getParameter("status")+"', LAST_MODIF_DATE = SYSDATE"
						+ " WHERE DN_ID='"+request.getParameter("dnID")+"'");
				query.executeUpdate();
				tx.commit();
			}catch(Exception e) {
				tx.rollback();
				e.printStackTrace();
			}finally {
				
				if(session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		
		return map;
}



// APPROVED BY project manager or asset unit
public void ApprovalProjectandAsset(String trans_Type, String getApproval, String dnStatus, String AssetRegID, String ArCode, String PurchaseOrId, String itmcode, String itmname, String WorkOrder, String DniID, String toSiteID, String supplierID, String supplierName, String towareID, String towareName, String serialnb, float dnRate, String itemModel, String itemPartNb, String toSite, String toSerialNumber, String toSlot, String Site, String fromSlot, String SiteID,String macAddress) {
	
	Query getpoItemID = session.createNativeQuery("select PO_ITEM_ID from PURCHASE_ORDER_ITEM where ITEM_CODE = '"+itmcode+"' and PO_ID = '"+PurchaseOrId+"'");
	getpoItemID.executeUpdate();
	String poItemID = (String) getpoItemID.uniqueResult();
	
	AssetNewNode(trans_Type, getApproval, dnStatus,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,WorkOrder,DniID,toSiteID,supplierID,supplierName,towareID,towareName,serialnb,dnRate,itemModel,itemPartNb,toSite,toSerialNumber,toSlot,Site,fromSlot,SiteID);
	

	if (AssetRegID == null) {

		String AR_Site_ID;
		//ArCode = "AR_" + year + "_" + appConfig.getSequenceNbr(9);
		synchronized (this) {						
			ArCode = "AR_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
			query = session.createNativeQuery("UPDATE SEQ_TABLE SET ASSET_REGISTRY = ASSET_REGISTRY + 1 ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			AR_Site_ID = "ARSITE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
			query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_SITE = AR_SITE + 1 ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			}
    	//String AR_Site_ID = "AR_SITE_" + year + "_"  + appConfig.getSequenceNbr(30);
	
    	
		// ADD DiscoveryNewItem TO ASSET REGISTRY TABLE

    	AssetRegistry assetregistry = new AssetRegistry();
		assetregistry.setArID(ArCode);
		assetregistry.setAritemCode(itmcode);
		str = "SELECT item_category, item_catcode FROM Item WHERE item_code = :param1";
		 query = session.createNativeQuery(str)
		                    .setParameter("param1", itmcode);
		result = (Object[]) query.uniqueResult();
        assetregistry.setItemCat(result[0].toString());
		assetregistry.setItemCatCode(result[1].toString());
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
		assetregistry.setArStatus("Running");
		assetregistry.setPoItemId(poItemID);
		assetregistry.setArsiteId(AR_Site_ID);
		session.saveOrUpdate(assetregistry);


		// UPDATE AR_ID & AR_SITE_ID IN DISCOVERY_NEW_ITEM TABLE
		
		query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1, AR_SITE_ID = :param2 where DNI_ID = :param3");
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
        query = session.createNativeQuery("select TO_NODE_ID,TO_NODE_NAME,TO_NODE_TYPE FROM DISCOVER_NEW_ITEM_Node WHERE DNI_ID=:param1");
 		query.setParameter("param1", DniID);
 		query.executeUpdate();
 		List<Object[]> resultList = query.getResultList();
 		
 			
 		for (Object[] result : resultList) {
 			if (result[0] != null) {
 		String NodeId= result[0].toString();
 	
 		
 			    AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
 				query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
 				query.executeUpdate();
 				session.createNativeQuery("commit").executeUpdate();
 			
     		
     	ArNode assetRegNode = new ArNode();
     	assetRegNode.setNodearId(AR_NodeID);
     	assetRegNode.setNodeID(NodeId);
     	assetRegNode.setNodeName(result[1].toString());
     	assetRegNode.setNodeType(result[2].toString());
     	assetRegNode.setArID(ArCode);
     	session.saveOrUpdate(assetRegNode);
     
     	
 		}
 		}
    	
    	
		// Add to AR_SERIAL_NUMBER table
    	String AR_SerialNum_ID;
		if(!StringUtils.equalsIgnoreCase(serialnb, "0") ||  !StringUtils.equalsIgnoreCase(macAddress, "0")) {
			synchronized (this) {						
				AR_SerialNum_ID = "ARSNUM_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
				}
			//String AR_SerialNum_ID = "ARSNUM_" + year + "_" + appConfig.getSequenceNbr(31);
		
		ArSerialNumber assetRegSerialNumber = new ArSerialNumber();
		assetRegSerialNumber.setSerialId(AR_SerialNum_ID);
		assetRegSerialNumber.setSerialNumber(toSerialNumber);
		assetRegSerialNumber.setMacAddress(macAddress);
		assetRegSerialNumber.setModel(itemModel);
		assetRegSerialNumber.setPartNumber(itemPartNb);
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
				AR_model_partNum = "ARMP_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_MODEL_PARTNO = AR_MODEL_PARTNO + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
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
	
	query = session.createNativeQuery("update ASSET_REGISTRY SET ITEM_CODE = '"+itmcode+"',LAST_MODIFIED_DATE = sysdate,ITEM_NAME = '"+itmname+"',PO_ID = '"+PurchaseOrId+"',"
			+ "SUPPLIER_ID = '"+supplierID+"', SUPPLIER_NAME = '"+supplierName+"',ITEM_MODEL = '"+itemModel+"',ITEM_PART_NUMBER = '"+itemPartNb+"',ITEM_SN = '"+toSerialNumber+"', PO_ITEM_ID = '"+poItemID+"' WHERE AR_ID = :param1 ");
	query.setParameter("param1", ArCode);
	query.executeUpdate();
	
	query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
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
			ARModelPartNum = "ARMP_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
			query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_MODEL_PARTNO = AR_MODEL_PARTNO + 1 ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			}
		 //ARModelPartNum = "itmId_" + year + "_" +  appConfig.getSequenceNbr(26);
	}
	
	query = session.createNativeQuery("update AR_MODEL_PARTNUMBER SET ITM_ID = '"+ARModelPartNum+"',ITEM_PART_NUMBER = '"+itemPartNb+"',QUANTITY = 1,ITEM_MODEL = '"+itemModel+"',ITEM_CODE = '"+itmcode+"',"
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
			AR_SiteID = "ARSITE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
			query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_SITE = AR_SITE + 1 ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			}
		//AR_SiteID = "AR_SITE_" + year + "_"  + appConfig.getSequenceNbr(30);
	}
	
	query = session.createNativeQuery("update AR_SITE SET ARSITE_ID = '"+AR_SiteID+"',SITE_ID = '"+toSiteID+"',SITE_NAME = '"+towareName+"',WARE_ID = '"+towareID+"'"
			+ " WHERE AR_ID = :param1 ");
	query.setParameter("param1", ArCode);
	query.executeUpdate();
	
	
	
	
	/// ADD TO AR_NODE TABLE                                  

	query = session.createQuery("delete from ArNode t where t.arID = :param1");

	query.setParameter("param1", ArCode);
	query.executeUpdate();
	
	String AR_NodeID;
    query = session.createNativeQuery("select TO_NODE_ID,TO_NODE_NAME,TO_NODE_TYPE FROM DISCOVER_NEW_ITEM_Node WHERE DNI_ID=:param1");
		query.setParameter("param1", DniID);
		query.executeUpdate();
		List<Object[]> resultList = query.getResultList();
			
		for (Object[] result : resultList) {
			if (result[0] != null) {
		String NodeId= result[0].toString();
		String Name= result[1].toString();
		String Type= result[2].toString();
		System.out.println(result);

		
			    AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
			
 		
 	ArNode assetRegNode = new ArNode();
 	assetRegNode.setNodearId(AR_NodeID);
 	assetRegNode.setNodeID(NodeId);
 	assetRegNode.setNodeName(result[1].toString());
 	assetRegNode.setNodeType(result[2].toString());
 	assetRegNode.setArID(ArCode);
 	session.saveOrUpdate(assetRegNode);
 
		}
		}
	
	
	// Add to AR_SERIAL_NUMBER table
	
	query = session.createQuery("select t.serialId from ArSerialNumber t where t.arID = :param1 ");
	query.setParameter("param1", ArCode);
	String AR_SerialID = (String) query.uniqueResult();
	
	
	if(AR_SerialID == null) {
		synchronized (this) {						
			AR_SerialID = "ARSNUM_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
			query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			}
		 //AR_SerialID = "ARSNUM_" + year + "_" + appConfig.getSequenceNbr(31);
	}
	
	query = session.createNativeQuery("update AR_SERIAL_NUMBER SET SERIAL_ID = '"+AR_SerialID+"',SERIAL_NUMBER = '"+toSerialNumber+"',MODEL = '"+itemModel+"',PART_NUMBER = '"+itemPartNb+"', SITE = '"+toSite+"',POSITION = '"+toSlot+"' "
			+ " WHERE AR_ID = :param1 ");
	query.setParameter("param1", ArCode);
	query.executeUpdate();
	
}

	
}



// APPROVED BY Finance
@SuppressWarnings({ "rawtypes", "unchecked" })
public void ApprovalFinance(String trans_Type, String getApproval, String dnStatus, String AssetRegID, String ArCode, String PurchaseOrId, String itmcode, String itmname, String WorkOrder, String DniID, String toSiteID, String supplierID, String supplierName, String towareID, String towareName, String serialnb, float dnRate, String itemModel, String itemPartNb, String toSite, String toSerialNumber, String toSlot, String Site, String fromSlot, String SiteID, String MacAddress) {
	


	Query getpoItemID = session.createNativeQuery("select PO_ITEM_ID from PURCHASE_ORDER_ITEM where ITEM_CODE = '"+itmcode+"' and PO_ID = '"+PurchaseOrId+"'");
	getpoItemID.executeUpdate();
	String poItemID = (String) getpoItemID.uniqueResult();

	// check, if this itemCode related to poID, exist in CIP table (get the qty and cipID)
	query = session.createQuery(
			"Select t.TOTALQTY as totalqty, t.cipID as cipID from CapitalInProgress t where t.PoId =:param1 and t.cipitemCode =:param2");

	query.setParameter("param1", PurchaseOrId);
	query.setParameter("param2", itmcode);

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
				 q5.setParameter("param1", updatedQty); 
				 q5.setParameter("param2", fields[1]); 
				 q5.executeUpdate();
				 query = session.createNativeQuery("commit");
				 query.executeUpdate(); 

			}



			//FarCode = "FAR_" + year + "_" + appConfig.getSequenceNbr(10);
			synchronized (this) {						
				FarCode = "FAR_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FIXED_ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET FIXED_ASSET_REGISTRY = FIXED_ASSET_REGISTRY + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
				}
			float initialCost = dnRate;
			
			query = session.createNativeQuery("select USEFULL_LIFE_MONTHS from ITEM where ITEM_CODE = '"+itmcode+"'");
			query.executeUpdate();
			String item_Usefull_LifeMonths = (String) query.uniqueResult();
			float useful_life_month = 0;

			if (!StringUtils.equalsIgnoreCase(item_Usefull_LifeMonths, "")) { 
				
				useful_life_month = Float.parseFloat(item_Usefull_LifeMonths);
			}

			

			String FarSiteID;
			synchronized (this) {						
				FarSiteID = "FARSITE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_SITE = FAR_SITE + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
				}
			
			
			// ADD DiscoveryNewItem TO ASSET REGISTRY TABLE

			FixedAssetRegistry FixedAssetReg = new FixedAssetRegistry();
			FixedAssetReg.setARID(AssetRegID);
			FixedAssetReg.setFarID(FarCode);     
			FixedAssetReg.setFaritemCode(itmcode);
			query = session.createNativeQuery("SELECT item_category, item_catcode FROM Item WHERE item_code = :param1")
                    .setParameter("param1", itmcode);
		    Object[] res = (Object[]) query.uniqueResult();
            FixedAssetReg.setItemCat(res[0].toString());
            FixedAssetReg.setItemCatCode(res[1].toString());
     		FixedAssetReg.setFarcreatedDate(new Timestamp(System.currentTimeMillis()));
			FixedAssetReg.setFarlastModifiedDate(new Timestamp(System.currentTimeMillis()));
			FixedAssetReg.setFaritemName(itmname);
			FixedAssetReg.setIniCost(initialCost);
			FixedAssetReg.setNetCost(initialCost);
			FixedAssetReg.setUsefulLifeMon(useful_life_month);
			FixedAssetReg.setDailyPercent(0);
			FixedAssetReg.setAccumPer(0);
			FixedAssetReg.setAccumDeprCode("0");
			FixedAssetReg.setDeprCode("0");
			FixedAssetReg.setDniID(DniID);
			FixedAssetReg.setSupplierID(supplierID);
			FixedAssetReg.setSupplierName(supplierName);
			FixedAssetReg.setPoId(PurchaseOrId);
			FixedAssetReg.setItemModel(itemModel);
			FixedAssetReg.setItemPartNb(itemPartNb);
			FixedAssetReg.setItemSN(toSerialNumber);
		    FixedAssetReg.setFarStatus("Running");
			FixedAssetReg.setPoItemId(poItemID);
			FixedAssetReg.setFarsiteId(FarSiteID);
			session.saveOrUpdate(FixedAssetReg);
			
			
			
			query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1,  FR_SITE_ID = :param2  where DNI_ID = :param3");
			query.setParameter("param1", FarCode);
			query.setParameter("param2", FarSiteID);
			query.setParameter("param3", DniID);
			query.executeUpdate();
			
			
			
			// ADD TO FAR NODE TABLE

			
            query = session.createNativeQuery("select TO_NODE_ID,TO_NODE_NAME,TO_NODE_TYPE FROM DISCOVER_NEW_ITEM_Node WHERE DNI_ID=:param1");
			query.setParameter("param1", DniID);
			query.executeUpdate();
			List<Object[]> resultNList = query.getResultList();


			String FAR_NodeID;
	
			
				
	for (Object[] resultN : resultNList) {
		
		if (resultN[0] != null) {
	FAR_NodeID = "FARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
	query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_NODE = FAR_NODE + 1 ");
	query.executeUpdate();
	session.createNativeQuery("commit").executeUpdate();
	

FarNode FixedAssetRegNode = new FarNode();

FixedAssetRegNode.setNodefarId(FAR_NodeID);
FixedAssetRegNode.setNodeID(resultN[0].toString());
FixedAssetRegNode.setNodeName(resultN[1].toString());
FixedAssetRegNode.setNodeType(resultN[2].toString());
FixedAssetRegNode.setFarID(FarCode);

session.saveOrUpdate(FixedAssetRegNode);

}
			}
			
			
			// ADD TO FAR MODEL_PART_NB TABLE
			
			if(!StringUtils.equalsIgnoreCase(itemModel, "") || !StringUtils.equalsIgnoreCase(itemPartNb, ""))
			{
			String FAR_model_partNum;
			synchronized (this) {						
				FAR_model_partNum = "FARMP_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_MODEL_PARTNO = FAR_MODEL_PARTNO + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
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

			if(!StringUtils.equalsIgnoreCase(serialnb, "0") || (!StringUtils.equalsIgnoreCase(MacAddress, "0")) ) {
			String FAR_SerialNum_ID;
			synchronized (this) {						
				FAR_SerialNum_ID = "FARSNUM_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_SERIALNO = FAR_SERIALNO + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
				}
			FarSerialNumber fixedAssetRegSerialNumber = new FarSerialNumber();
			
			fixedAssetRegSerialNumber.setSerialId(FAR_SerialNum_ID);
			fixedAssetRegSerialNumber.setInputSerialNb(toSerialNumber);
			fixedAssetRegSerialNumber.setInputModel(itemModel);
			fixedAssetRegSerialNumber.setMacAddress(MacAddress);
			fixedAssetRegSerialNumber.setInputpartNumber(itemPartNb);
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
		query.setParameter("param_" + i, param.toString());
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
			query.setParameter("param_" + i, param.toString());
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
			query.setParameter("param_" + i, param.toString());
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
							query.setParameter("param_" + i, param.toString());
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
public void ApprovalOperational(String trans_Type, String getApproval, String dnStatus, String AssetRegID, String ArCode, String PurchaseOrId, String itmcode, String itmname, String WorkOrder, String DniID, String toSiteID, String supplierID, String supplierName, String towareID, String towareName, String serialnb, float dnRate, String itemModel, String itemPartNb, String toSite, String toSerialNumber, String toSlot, String Site, String fromSlot, String SiteID, String FAR,String MacAddress) {
	
String ARid=null;

	if( (!StringUtils.equalsIgnoreCase(serialnb, "0")) ||  (!StringUtils.equalsIgnoreCase(MacAddress, "0")) || (!StringUtils.equalsIgnoreCase(FAR, "0"))) // serial number exist
	{
		if (!StringUtils.equalsIgnoreCase(serialnb, "0")){
			query = session.createQuery("select distinct arID from ArSerialNumber where serialNumber = :param1");

			query.setParameter("param1", serialnb);
			 ARid = (String) query.uniqueResult();
			 query = session.createQuery("select distinct farID from FarSerialNumber where inputSerialNb = :param1");
            	query.setParameter("param1", serialnb);
				FAR = (String) query.uniqueResult();
		}// serial number exist 
		
		else if (!StringUtils.equalsIgnoreCase(MacAddress, "0")){
			query = session.createQuery("select distinct arID from ArSerialNumber where macAddress = :param1");
        	query.setParameter("param1", MacAddress);
			 ARid = (String) query.uniqueResult();
			 query = session.createQuery("select distinct farID from FarSerialNumber where macAddress = :param1");
         	query.setParameter("param1", MacAddress);
				FAR = (String) query.uniqueResult();
	
			} // Mac_Address exist
		else if (!StringUtils.equalsIgnoreCase(FAR, "0")){
			query = session.createQuery("select distinct ARID from FixedAssetRegistry where farID = :param1");

			query.setParameter("param1", FAR);
			 ARid = (String) query.uniqueResult();
			} // FARId exist 
		

		// TRANS TYPE NEW NODE ON EXISTED HARDWARE
		
		if(StringUtils.equalsIgnoreCase(trans_Type, "New Node on Existed Hardware"))
		
		{
				
			query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", ARid);
			query.setParameter("param2", DniID);
			query.executeUpdate();
			
			if (ARid != null) {
				
				//Add to Table AR_SERIAL_NUMBER
				
				query = session.createQuery(" select serialId from ArSerialNumber where"
						+ " arID = :param1 and (serialNumber = :param2 OR macAddress = :param3 )");
				
				query.setParameter("param1", ARid);
				query.setParameter("param2", serialnb);
				query.setParameter("param3", MacAddress);
				String checkIfEx = (String) query.uniqueResult();
				String arserialID="";
				if(checkIfEx != null) {
					 arserialID= checkIfEx;
					}
				else {
					synchronized (this) {						
						arserialID = "ARSNUM_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						}
					// arserialID="ARSNUM_" + year + "_" + appConfig.getSequenceNbr(31); 
				}
				
			
				 ArSerialNumber assetRegSerialNumber = new ArSerialNumber();
				 
	        		assetRegSerialNumber.setSerialId(arserialID);
					assetRegSerialNumber.setSerialNumber(toSerialNumber);
					assetRegSerialNumber.setModel(itemModel);
					assetRegSerialNumber.setPartNumber(itemPartNb);
					assetRegSerialNumber.setSite(toSite);
					assetRegSerialNumber.setArID(ARid);
					assetRegSerialNumber.setMacAddress(MacAddress);
					assetRegSerialNumber.setPosition(toSlot);
					session.saveOrUpdate(assetRegSerialNumber);
	        		
					
				// Add to table AR_Node
					
					query = session.createQuery("delete from ArNode t where t.arID = :param1");

					query.setParameter("param1", ARid);
					query.executeUpdate();
					
					String AR_NodeID;
				    query = session.createNativeQuery("select TO_NODE_ID,TO_NODE_NAME,TO_NODE_TYPE FROM DISCOVER_NEW_ITEM_Node WHERE DNI_ID=:param1");
						query.setParameter("param1", DniID);
						query.executeUpdate();
						List<Object[]> resultList = query.getResultList();
							
						for (Object[] result : resultList) {
							if (result[0] != null) {
						String NodeId= result[0].toString();
						
						
							    AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
							
				 		
				 	ArNode assetRegNode = new ArNode();
				 	assetRegNode.setNodearId(AR_NodeID);
				 	assetRegNode.setNodeID(NodeId);
				 	assetRegNode.setNodeName(result[1].toString());
				 	assetRegNode.setNodeType(result[2].toString());
				 	assetRegNode.setArID(ARid);
				 	session.saveOrUpdate(assetRegNode);
						}
						}
			
			}
			
			
			
		
			
			query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", FAR);
			query.setParameter("param2", DniID);
			query.executeUpdate();


			if (FAR != null) {

			// Add to FAR_SERIAL_NUMBER table

				query = session.createQuery(" select serialId from FarSerialNumber where farID = :param1 and ( inputSerialNb = :param2 OR macAddress = :param3 ) "
			);

				query.setParameter("param1", FAR);
				query.setParameter("param2", serialnb);
				query.setParameter("param3", MacAddress);
				String farSerialID = (String) query.uniqueResult();
			String farserialID="";
			if(farSerialID != null) {
			 farserialID= farSerialID;
			
			}
			else {
			 //farserialID="FARSNUM_" + year + "_" + appConfig.getSequenceNbr(32); 
			 synchronized (this) {						
				 farserialID = "FARSNUM_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_SERIALNO = FAR_SERIALNO + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					}
			}

			

			// Add in Fixed_Asset_Registry
			
			FarSerialNumber fixedAssetRegSerialNumber = new FarSerialNumber();
			
			fixedAssetRegSerialNumber.setSerialId(farserialID);
			fixedAssetRegSerialNumber.setInputSerialNb(toSerialNumber);
			fixedAssetRegSerialNumber.setInputModel(itemModel);
			fixedAssetRegSerialNumber.setMacAddress(MacAddress);
			fixedAssetRegSerialNumber.setInputpartNumber(itemPartNb);
			fixedAssetRegSerialNumber.setInputsite(toSite);
			fixedAssetRegSerialNumber.setFarID(FAR);
			fixedAssetRegSerialNumber.setInputPosition(toSlot);
			
			session.saveOrUpdate(fixedAssetRegSerialNumber);

			// Add to table FAR_NODE

			query = session.createQuery(" select nodefarId from FarNode where farID = :param1  "
			);

			query.setParameter("param1",FAR);
			String farNodeID = (String) query.uniqueResult();
			String FAR_NodeID = "";
			if(farNodeID != null) {
				FAR_NodeID = farNodeID;
				}
			else {
			 //FAR_NodeID = "FARNODE_" + year + "_" + appConfig.getSequenceNbr(27);
			 synchronized (this) {						
				 FAR_NodeID = "FARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_NODE = FAR_NODE + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					}
			}


			FarNode farNode = new FarNode();
			
			farNode.setNodefarId(FAR_NodeID);
			farNode.setFarID(FAR);


			session.saveOrUpdate(farNode);
			}



			}
		
		//get arID
		
		
		
		if(StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Slot to Slot") || StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Node to Node") ||
		StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Site to Site"))
		{
			
		//Update AR_SERIAL_NUMBER
		query = session.createNativeQuery("update AR_SERIAL_NUMBER set  POSITION = :param3, SITE = :param4 where  "
		+ "SERIAL_NUMBER = :param6 and POSITION = :param7 and SITE = :param8");
		query.setParameter("param3", toSlot);
		query.setParameter("param4", toSite);
		query.setParameter("param6", serialnb);
		query.setParameter("param7", fromSlot);
		query.setParameter("param8", Site);
		query.executeUpdate();

		//Update In AR_SITE
		
		query = session.createNativeQuery("update AR_SITE SET SITE_ID = '"+toSiteID+"',SITE_NAME = '"+towareName+"',WARE_ID = '"+towareID+"'"
				+ " WHERE AR_ID = :param1 ");
		query.setParameter("param1", ARid);
		query.executeUpdate();
		

		//Update FAR_SERIAL_NUMBER
		query = session.createNativeQuery("update FAR_SERIAL_NUMBER set POSITION = :param3, SITE = :param4 where  "
		+ "SERIAL_NUMBER = :param6 and POSITION = :param7 and SITE = :param8");
		query.setParameter("param3", toSlot);
		query.setParameter("param4", toSite);
		query.setParameter("param6", serialnb);
		query.setParameter("param7", fromSlot);
		query.setParameter("param8", Site);
		query.executeUpdate();
			
		//Update In Far_Site

		query = session.createNativeQuery("update FAR_SITE SET SITE_ID = '"+toSiteID+"',SITE_NAME = '"+towareName+"',WARE_ID = '"+towareID+"'"
				+ " WHERE FAR_ID = :param1 ");
		query.setParameter("param1", FAR);
		query.executeUpdate();


		//Update AR_NODE
		
		query = session.createQuery("delete from ArNode t where t.arID = :param1");
     	query.setParameter("param1", ARid);
    	query.executeUpdate();
	
	String AR_NodeID;
    query = session.createNativeQuery("select TO_NODE_ID,TO_NODE_NAME,TO_NODE_TYPE FROM DISCOVER_NEW_ITEM_Node WHERE DNI_ID=:param1");
		query.setParameter("param1", DniID);
		query.executeUpdate();
		List<Object[]> resultList = query.getResultList();

			 
		for (Object[] result : resultList) {
			if ( result[0] != null) {
				
		String NodeId= result[0].toString();
	
		
			    AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
			
 		
 	ArNode assetRegNode = new ArNode();
 	assetRegNode.setNodearId(AR_NodeID);
 	assetRegNode.setNodeID(NodeId);
 	assetRegNode.setNodeName(result[1].toString());
 	assetRegNode.setNodeType(result[2].toString());
 	assetRegNode.setArID(ArCode);
 	session.saveOrUpdate(assetRegNode);
		}
		}
		
		
	
	
	//Update FAR_NODE 
		
		query = session.createNativeQuery("delete FAR_NODE where FAR_ID = :param1");
		query.setParameter("param1", FAR);
		query.executeUpdate();
		  query = session.createNativeQuery("select TO_NODE_ID,TO_NODE_NAME,TO_NODE_TYPE FROM DISCOVER_NEW_ITEM_Node WHERE DNI_ID=:param1");
			query.setParameter("param1", DniID);
			query.executeUpdate();
			List<Object[]> resultNList = query.getResultList();


			String FAR_NodeID;
				
	
	for (Object[] resultN : resultNList) {
		if (resultN[0] != null) {
			
	
	FAR_NodeID = "FARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
	query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_NODE = FAR_NODE + 1 ");
	query.executeUpdate();
	session.createNativeQuery("commit").executeUpdate();
	

FarNode FixedAssetRegNode = new FarNode();

FixedAssetRegNode.setNodefarId(FAR_NodeID);
FixedAssetRegNode.setNodeID(resultN[0].toString());
FixedAssetRegNode.setNodeName(resultN[1].toString());
FixedAssetRegNode.setNodeType(resultN[2].toString());
FixedAssetRegNode.setFarID(FAR);

session.saveOrUpdate(FixedAssetRegNode);

}
	}
		}

		
		
		
		// TRANS TYPE DISAPPEAR FOR TRANSFER

		if (StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Transfer"))

		{
		
			query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", FAR);
			query.setParameter("param2", DniID);
			query.executeUpdate();
		          
			query = session.createNativeQuery("update ASSET_REGISTRY set AR_STATUS = 'Disappear' where AR_ID =:param1");
			query.setParameter("param1", FAR);
			query.executeUpdate();


			query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", FAR);
			query.setParameter("param2", DniID);
			query.executeUpdate();
		

		    query = session.createNativeQuery("update FIXED_ASSET_REGISTRY set FAR_STATUS ='Disappear' where FAR_ID =:param1");
		    query.setParameter("param1", FAR);
		    query.executeUpdate();

		}

		
		
		
		// TRANS TYPE DISAPPEAR FOR MAINTENANCE 

		if (StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Maintenance"))

		{

		
			query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", ARid);
			query.setParameter("param2", DniID);
			query.executeUpdate();
							             
			query = session.createNativeQuery("update ASSET_REGISTRY set AR_STATUS = 'Maintenance' where AR_ID =:param1");
			query.setParameter("param1", ARid);
			query.executeUpdate();

			query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", FAR);
			query.setParameter("param2", DniID);
			query.executeUpdate();
		
			query = session.createNativeQuery("update FIXED_ASSET_REGISTRY set FAR_STATUS ='Maintenance' where FAR_ID =:param1");
			query.setParameter("param1", FAR);
			query.executeUpdate();

		} 

		
		
		
		// TRANS TYPE MAINTENANCE

		if (StringUtils.equalsIgnoreCase(trans_Type, "Maintenance"))

		{
		
			query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", ARid);
			query.setParameter("param2", DniID);
			query.executeUpdate();
		
							             
			query = session.createNativeQuery("update ASSET_REGISTRY set AR_STATUS = 'Running' where AR_ID =:param1");
			query.setParameter("param1", ARid);
			query.executeUpdate();

		
			query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
			query.setParameter("param1", FAR);
			query.setParameter("param2", DniID);
			query.executeUpdate();
		


			query = session.createNativeQuery("update FIXED_ASSET_REGISTRY set FAR_STATUS ='Running' where FAR_ID =:param1");
			query.setParameter("param1", FAR);
			query.executeUpdate();


		} 

	
		
//TRANS TYPE RETIREMENT										

	if (StringUtils.equalsIgnoreCase(trans_Type, "Retirement"))
		
	{
		
	
	
	//DELETE FROM AR_SITE BOQ
	query = session.createNativeQuery("delete AR_SITE where AR_ID = :param1");
	query.setParameter("param1", ARid);
	query.executeUpdate();

	//DELETE FROM AR_NODE BOQ

	query = session.createNativeQuery("delete AR_NODE where AR_ID = :param1");
	query.setParameter("param1", ARid);
	query.executeUpdate();

	//DELETE FROM AR_SERIAL_NUMBER
	query = session.createNativeQuery("delete AR_SERIAL_NUMBER where AR_ID = :param1");
	query.setParameter("param1", ARid);
	query.executeUpdate();

	//DELETE FROM AR_MODEL_PARTNUMBER

	query = session.createNativeQuery("delete AR_MODEL_PARTNUMBER where AR_ID = :param1");
	query.setParameter("param1", ARid);
	query.executeUpdate();

	//DELETE FROM ASSET_REGISTRY

	query = session.createNativeQuery("delete ASSET_REGISTRY where AR_ID = :param1");
	query.setParameter("param1", ARid);
	query.executeUpdate();

	//DELETE FROM FAR_SITE BOQ

	query = session.createNativeQuery("delete FAR_SITE where FAR_ID = :param1");
	query .setParameter("param1", FAR);
	query .executeUpdate();

	//DELETE FROM FAR_NODE BOQ

	query = session.createNativeQuery("delete FAR_NODE where FAR_ID = :param1");
	query.setParameter("param1", FAR);
	query.executeUpdate();

	//DELETE FROM FAR_SERIAL_NUMBER

	query = session.createNativeQuery("delete FAR_SERIAL_NUMBER where FAR_ID = :param1");
	query.setParameter("param1", FAR);
	query.executeUpdate();

	//DELETE FROM FAR_MODEL_PARTNUMBER

	query = session.createNativeQuery("delete FAR_MODEL_PARTNUMBER where FAR_ID = :param1");
	query.setParameter("param1", FAR);
	query.executeUpdate();

	//DELETE FROM FIXED_ASSET_REGISTRY

	query = session.createNativeQuery("delete FIXED_ASSET_REGISTRY where FAR_ID = :param1");
	query.setParameter("param1", FAR);
	query.executeUpdate();

	} // END TRANSACTION TYPE RETIREMENT 
			
	
}

}



//APPROVED BY project manager or asset unit
public void AssetNewNode(String trans_Type, String getApproval, String dnStatus, String AssetRegID, String ArCode, String PurchaseOrId, String itmcode, String itmname, String WorkOrder, String DniID, String toSiteID, String supplierID, String supplierName, String towareID, String towareName, String serialnb, float dnRate, String itemModel, String itemPartNb, String toSite, String toSerialNumber, String toSlot, String Site, String fromSlot, String SiteID) {
	

	
if(StringUtils.equalsIgnoreCase(dnStatus, "Approved") && StringUtils.equalsIgnoreCase(trans_Type, "New Node on New Hardware")) {
	
	System.out.println("the serial number is "+toSerialNumber);
	
	if(StringUtils.equalsIgnoreCase(toSerialNumber, "0")) {
		System.out.println("update when serial is 0 for source serial");
		Query sn1=session.createNativeQuery("UPDATE WORK_ORDER_DESTINATION_SERIAL SET RECONCILED ='1' WHERE EXISTS(SELECT * FROM WORK_ORDER_DESTINATION_SERIAL WHERE ITEM_MODEL = :param1 and ITEM_PART_NUMBER= :param2 and WO_ID = :param3)");
		sn1.setParameter("param1",itemModel);
		sn1.setParameter("param2", itemPartNb);
		sn1.setParameter("param3","WO_2021_62");
		sn1.executeUpdate();
		
		System.out.println("update when serial is 0 for destination serial");
		Query sn2=session.createNativeQuery("UPDATE WORK_ORDER_SOURCE_SERIAL SET RECONCILED ='1' WHERE EXISTS(SELECT * FROM WORK_ORDER_SOURCE_SERIAL WHERE ITEM_MODEL = :param1 and ITEM_PART_NUMBER= :param2 and WO_ID = :param3)");
		sn2.setParameter("param1",itemModel);
		sn2.setParameter("param2", itemPartNb);
		sn2.setParameter("param3","WO_2021_62");
		sn2.executeUpdate();

	} else if (!StringUtils.equalsIgnoreCase(toSerialNumber, "0")) {
		System.out.println("update when we have a serial");
		
		Query sn1=session.createNativeQuery("SELECT ID FROM WORK_ORDER_SOURCE_SERIAL WHERE SERIAL_NO = :param1");
		sn1.setParameter("param1",toSerialNumber);
		 String sn1Result = (String) sn1.uniqueResult();
			System.out.println("sn1Result"+sn1Result);

		 if (sn1Result !=null) {
			System.out.println("SERIAL IS IN WORK ORDER SOURCE TABLE");
     		query=session.createNativeQuery("update WORK_ORDER_SOURCE_SERIAL set RECONCILED ='1' where SERIAL_NO = :param1");
     		query.setParameter("param1",toSerialNumber);
     		query.executeUpdate();
			System.out.println("SERIAL IS RECONCILED IN WORK ORDER");

		 } else {
			 System.out.println("SERIAL IS NOTTTTTTT IN WORK ORDER SOURCE TABLE");
			 query=session.createNativeQuery("update WORK_ORDER_SOURCE_SERIAL set RECONCILED ='1', SERIAL_NO = :param1 WHERE EXISTS(SELECT * FROM WORK_ORDER_SOURCE_SERIAL WHERE ITEM_CODE = :param2 and ITEM_MODEL = :param3 and ITEM_PART_NUMBER= :param4 and WO_ID = :param5 and SERIAL_NO = '0' and ROWNUM = 1)"); 
			 query.setParameter("param1",toSerialNumber);
			 query.setParameter("param2",itmcode);
			 query.setParameter("param3",itemModel);
			 query.setParameter("param4", itemPartNb);
			 query.setParameter("param5",WorkOrder);

			 query.executeUpdate();
	     	 
				System.out.println("SERIAL IS RECONCILED IN WORK ORDER sourceeeee");
		 }
		 
		  query=session.createNativeQuery("SELECT ID FROM WORK_ORDER_DESTINATION_SERIAL WHERE SERIAL_NO = :param1");
		  query.setParameter("param1",toSerialNumber);
			 String sn2Result = (String) query.setMaxResults(1).uniqueResult();
				System.out.println("sn1Result"+sn1Result);

			 if (sn2Result !=null) {
				System.out.println("SERIAL IS IN WORK ORDER DESTINATION TABLE");
				query=session.createNativeQuery("update WORK_ORDER_DESTINATION_SERIAL set RECONCILED ='1' where SERIAL_NO = :param1");
				query.setParameter("param1",toSerialNumber);
				query.executeUpdate();
				System.out.println("SERIAL IS RECONCILED IN WORK ORDER");

			 } else {
				 System.out.println("SERIAL IS NOTTTTTTT IN WORK ORDER DESTINATION TABLE");
				 query=session.createNativeQuery("update WORK_ORDER_DESTINATION_SERIAL set RECONCILED ='1', SERIAL_NO = :param1 WHERE EXISTS(SELECT * FROM WORK_ORDER_SOURCE_SERIAL WHERE ITEM_CODE = :param2 and ITEM_MODEL = :param3 and ITEM_PART_NUMBER= :param4 and WO_ID = :param5 and SERIAL_NO = '0' and ROWNUM = 1)"); 
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
	query.setParameter("param_" + i, param.toString());
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
	//Transaction tx =session.beginTransaction();
	query = session.createQuery(queryString);
	for (Object param : params) {
		i = params.indexOf(param) + 1;
		query.setParameter("param_" + i, param.toString());
	}
	query.executeUpdate();
	//tx.commit();
 }



public String getMigrationQuery(Session session,String tableName,String primaryKey,String activeRecord) {
	String migQuery = null;
	if(tableName.equalsIgnoreCase("NODE_ACTIVE")) {
		migQuery ="select t.trans_id as transID,t.model as nodeModel,t.discovered_trans_type as transType,t.from_site as fromSite,t.to_site as toSite,"+
				  "t.from_ware_id as fromWareID,t.to_ware_id as toWareID,t.from_ware_name as fromWareName,t.to_ware_name as toWareName, "+
				  " t.old_serial_number as oldsn , t.serial_number as sn,t.parsing_date,t.old_mac,t.mac_address " + 
				  " from network_transaction t  inner join "+tableName+" a on t.element_id=a."+primaryKey+ 
				  " where t.sent_to_alm='0' and active_record='"+activeRecord+"'";
	}else {
	String query = "select distinct t.trans_id,b.serialnumber " + 
			" from network_transaction t inner join "+tableName+" b" + 
			" on t.element_id = b."+primaryKey+ 
			" where t.sent_to_alm='0' and b.active_record='"+activeRecord+"'" + 
			" order by b.serialnumber";
	
	
	String prevSN =null;
	List<String> transID = new ArrayList<>();
	List<Object[] >transIDList = session.createNativeQuery(query).list();
	for(int i=0;i<transIDList.size();i++) {
		//if(transID.size() ==0 || !prevSN.equalsIgnoreCase(transIDList.get(i)[1].toString())) {
			prevSN = transIDList.get(i)[1].toString();
			transID.add(transIDList.get(i)[0].toString());
		//}
	}
	if(transID.size() > 0) {
		/*convert list of string from format ["data1","data2","data3"], into string of format 'data1','data2','data3'
		 * to be used in the IN of the where condition in the query
		 * */
		
		String str1 = transID.stream()
	            .map(value -> "'" + value + "'")
	            .reduce((value1, value2) -> value1 + "," + value2)
	            .orElse("");
		
		migQuery ="select t.trans_id as transID,t.model as Model,t.discovered_trans_type as transType,t.from_site as fromSite,t.to_site as toSite," + 
				" t.from_ware_id as fromWareID,t.to_ware_id as toWareID,t.from_ware_name as fromWareName,t.to_ware_name as toWareName,"+
				" t.old_serial_number as oldsn , t.serial_number as sn,t.parsing_date " + 
				" from network_transaction t  inner join " +tableName+" b on t.element_id=b."+primaryKey+ 
				" where t.sent_to_alm='0' and b.active_record='"+activeRecord+"' and t.trans_id IN ("+str1+") and t.serial_number<>'0' " + 
				" order by t.serial_number";
	}
	}
	return migQuery;
}

public void insertDiscoveredElements(Session session,List<Object[]> element,String dnid,String elementName,String type) {
	List<String> transElementID = new ArrayList<>();
	List<String> transSerial = new ArrayList<>();
	Object [] arrayList=null;
	ArrayList<ArrayList<String>> insertedElementsList = new ArrayList<>();
	ArrayList<String> insertedElement = new ArrayList<String>();
	String trans_id = null,DniID =null,node_pk=null;
	for(int i=0;i< element.size();i++) {
		
		int row=findElementIndex(insertedElementsList,(String) element.get(i)[10]);
		 if(((containsValue(insertedElementsList,(String) element.get(i)[10])) && (!insertedElementsList.get(row).get(1).toString().equals(element.get(i)[11].toString())))
				|| (!containsValue(insertedElementsList,(String) element.get(i)[10]))
			){
			
			DiscoveryNewItem discoverynewitem = new DiscoveryNewItem();
			arrayList=  element.get(i);
			trans_id = arrayList[0].toString();
			synchronized (this) {
				DniID = "DNI_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DISCOVERY_NEW_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET DISCOVERY_NEW_ITEM = DISCOVERY_NEW_ITEM + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
			}
			
			if(transElementID.indexOf(trans_id) < 0){transElementID.add(trans_id);}
			if(transSerial.indexOf(arrayList[10].toString()) < 0){transSerial.add(arrayList[10].toString());}
			
			discoverynewitem.setDniID(DniID);
			discoverynewitem.setTransID(arrayList[0].toString());
			discoverynewitem.setElementName(elementName);
			discoverynewitem.setItemModel(arrayList[1].toString());
			if(type.equalsIgnoreCase("Disappear")) {
				discoverynewitem.setTransType("Disappear");
			}else {
				if(arrayList[2].toString().contains("Transfer")) {
					discoverynewitem.setTransType("Transfer from Site to Site");
				}else {
					if(elementName.equalsIgnoreCase("Node")) {
					discoverynewitem.setTransType("New Node");
					}else {
						discoverynewitem.setTransType("New Hardware");
					}
				}
			}
			discoverynewitem.setDniSIte(arrayList[3].toString());
			discoverynewitem.setToSite(arrayList[4].toString());
			discoverynewitem.setWareID(arrayList[5].toString());
			discoverynewitem.setToWareId(arrayList[6].toString());
			discoverynewitem.setWareName(arrayList[7].toString());
			discoverynewitem.setToWareName(arrayList[8].toString());
			discoverynewitem.setDniSN(arrayList[9].toString());
			discoverynewitem.setToSerialNumber(arrayList[10].toString());
			
	     	if(elementName.equalsIgnoreCase("Node")) {
				discoverynewitem.setOldMacAddress(arrayList[12].toString());
				discoverynewitem.setMacAddress(arrayList[13].toString());
			}
			discoverynewitem.setDniDNID(dnid);
			discoverynewitem.setDnicreationDate(new Timestamp(System.currentTimeMillis())); 
			discoverynewitem.setDnilastModifieddate(new Timestamp(System.currentTimeMillis()));
			discoverynewitem.setDniAPPROVAL("Project Manager");
			session.saveOrUpdate(discoverynewitem);
			
			insertedElement = new ArrayList<String>();
			insertedElement.add(arrayList[10].toString());
			insertedElement.add(arrayList[11].toString());
			insertedElement.add(arrayList[0].toString());
			insertedElementsList.add(insertedElement);
			
			String transNodequery = "Select NODE_TRANS_ID,FROM_NODE_ID,TO_NODE_ID,FROM_NODE_NAME,TO_NODE_NAME,FROM_NODE_TYPE,TO_NODE_TYPE " + 
				"FROM NODE_TRANSACTIONS WHERE NODE_TRANS_ID=(select node_trans_id from network_transaction where trans_id='"+trans_id+"') and SENT_TO_ALM='0'";
			List<Object> nodeTransList = session.createNativeQuery(transNodequery).list();
			String dnNodeID ="";
			Object [] nodeArrayList=null;
			if(nodeTransList.size() > 0) {
				nodeArrayList =(Object[]) nodeTransList.get(0); 
				DiscoverNewItemNode dniNode = new DiscoverNewItemNode();
				synchronized (this) {
					dnNodeID = "DNI_NODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT DNI_NODE FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET DNI_NODE = DNI_NODE + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
				}
				
				dniNode.setDniNode(dnNodeID);
				dniNode.setCreationDate(new Timestamp(System.currentTimeMillis()));
				dniNode.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
				dniNode.setFromNodeId(nodeArrayList[1].toString());
				dniNode.setToNodeId(nodeArrayList[2].toString());
				dniNode.setFromNodeName(nodeArrayList[3].toString());
				dniNode.setToNodeName(nodeArrayList[4].toString());
				dniNode.setFromNodeType(nodeArrayList[5].toString());
				dniNode.setToNodeType(nodeArrayList[6].toString());
				dniNode.setDniId(DniID);
				session.saveOrUpdate(dniNode);
			}
			
		}
	 	else if(containsValue(insertedElementsList,(String) element.get(i)[10])) {
			if(row !=-1 && insertedElementsList.get(row).get(1).toString().equals(element.get(i)[11].toString())) {
				//String tempParsingdata=insertedElementsList.get(row).get(1).toString();
				String tempSN=element.get(i)[10].toString();
				trans_id=element.get(i)[0].toString();
				if(transElementID.indexOf(trans_id) < 0){transElementID.add(trans_id);}
				
				
				String hql = "FROM DiscoveryNewItem WHERE toSerialNumber = :targetSerialNumber";
	            DiscoveryNewItem entity = (DiscoveryNewItem) session.createQuery(hql)
	                    .setParameter("targetSerialNumber", tempSN)
	                    .uniqueResult();
				
	            if (entity != null) {
					String tempTrans_id=element.get(i)[0].toString();
	                String currentTrans_id = entity.getTransID();
	                entity.setTransID(currentTrans_id + "," + tempTrans_id);

	                session.saveOrUpdate(entity);
	            }
				
				/*
				String sql ="update discovery_new_item a set a.trans_id  = trans_id || ',' || '"+tempTrans_id+"' where a.to_serial_number='"+tempSN+"'";
				query = session.createNativeQuery(sql);
			    query.executeUpdate();
			    System.out.println(sql);
			     */
			
			}
		}
	}

	updateNodeNetworkTranscations(session,transElementID,transSerial);
	
	
}
	
	public void updateNodeNetworkTranscations(Session session,List<String> transElementID,List<String> transSerial) {
		if(transElementID.size() > 0) {
			String str1 = transElementID.stream()
		            .map(value -> "'" + value + "'")
		            .reduce((value1, value2) -> value1 + "," + value2)
		            .orElse("");
			
			query = session.createNativeQuery("update network_transaction set sent_to_alm ='1' where trans_id IN ("+str1+")");
			query.executeUpdate();
			query = session.createNativeQuery("update node_transactions set sent_to_alm ='1' where node_trans_id in (select node_trans_id from network_transaction where trans_id in ("+str1+"))");
			query.executeUpdate();
		}
		
	}
	
	public Integer findElementIndex(ArrayList<ArrayList<String>> twoDArrayList, String targetElement) {
        for (int i = 0; i < twoDArrayList.size(); i++) {
            ArrayList<String> row = twoDArrayList.get(i);
                if (row.contains(targetElement)) {
                    return i ;
                }
            
        }
        return -1; // Element not found
    }
	
	public static boolean containsValue(ArrayList<ArrayList<String>> arrayList2D, String value) {
        for (ArrayList<String> row : arrayList2D) {
            if (row.contains(value)) {
                return true; // Value found
            }
        }
        return false; // Value not found
    }
	
	
	public void transactionUpdate(String transId,String transType, String approvalType) {
		
		
		  String[] transIdArray = transId.split(",");
	        
	        for (String id : transIdArray) {
	        	
	        	query = session.createNativeQuery("UPDATE Network_TRANSACTION set "
	        			+ "ALM_TRANS_TYPE =:param1 , ALM_APPROVAL_STATUS=:param2 "
	        			+ "where Trans_Id=:param3 ");
	        	query.setParameter("param1", transType);
	        	query.setParameter("param2", approvalType);
	        	query.setParameter("param3", id);
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
	        }
		
	
	}
}
