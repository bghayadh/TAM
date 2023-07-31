package com.aliat.alm.controller;



import java.sql.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import com.aliat.alm.models.Agent;
import com.aliat.alm.models.SerialNumberSimTransaction;
import com.aliat.alm.models.Shops;
import com.aliat.alm.models.SimCard;
import com.aliat.alm.models.SimTransaction;
import com.aliat.alm.models.SimTransactionBoq;
import com.aliat.alm.models.SimTransactionItem;
import com.aliat.alm.models.Warehouse;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SimTransactionController {
	
	private static final String SIM_TRANS_ITEM_SOURCE_TYPE = "trSourceType";	
	private static final String SIM_TRANS_ITEM_SOURCE = "trSource";	
	private static final String SIM_TRANS_ITEM_SOURCE_ID = "trSourceID";	
	private static final String SIM_TRANS_ITEM_SOURCE_MSISDN = "trSourceMSISDN";
	private static final String SIM_TRANS_ITEM_DESTINATION_TYPE = "trDestinationType";
	private static final String SIM_TRANS_ITEM_DESTINATION = "trDestination";
	private static final String SIM_TRANS_ITEM_DESTINATION_ID = "trDestinationID";
	private static final String SIM_TRANS_ITEM_CREATION_DATE = "trCreationDate";
	private static final String SIM_TRANS_ITEM_ID = "trItemID";
	private static final String SIM_TRANS_SERIAL_NUM = "serialNo";
	private static final String SIM_TRANS_ITEM_QTY = "trTotQty";

	private static final Logger logger = LoggerFactory.getLogger(SimTransactionController.class);

	private List<String> incorrectSerials=null;  
	private List<String> incorrectMSISDN=null; 
	private List<String> incorrectSimSource=null; 
	private HashSet<Integer> incorrectIndex=null;
	private JSONObject jsonObjectErrors= null;

	Session session=null;
	Transaction tx=null;
	ObjectMapper mapper = new ObjectMapper();
	Calendar calendar = new GregorianCalendar();
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	String queryString;
	Query query;

	
	@Autowired
	ALMSessions almsessions;

	@Autowired
	Notify notifications;
	
	@Autowired
	Form form;
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SimTransactionListView", method = RequestMethod.GET)
	public String SimTransactionListView( Model model, HttpServletRequest request,
			HttpServletResponse response) {
 		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
 		else {
 		List<SimTransaction> simTransaction = new ArrayList<SimTransaction>();

 		session=almsessions.getSession();
 		if(session.isOpen() && session !=null) {
 			tx=session.beginTransaction();
 			notifications.headerNotifications(session, model);
 			try {
 				simTransaction = session.createQuery(
				"SELECT t.transactionID,t.transactionID,t.transactionType,t.totalQty,t.status,TO_CHAR(t.creationDate, 'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(t.lastModifieddate, 'YYYY-MM-DD HH24:MI:SS') from SimTransaction t")
				.list();
					model.addAttribute("ListGridTable", mapper.writeValueAsString(simTransaction));
		 		}catch (Exception e) {
	 				logger.info("error displaying list view of sim transaction"+e+"/n"+e.getMessage());
	 				model.addAttribute("ListGridTable", "");
		 		}
 				finally {
 				if(session.isOpen() && session !=null) {
 					
 					
 					tx.commit();
 					session.close();
 				}
			}
 		}
 		
		return "SimTransactionListView";
 		}
		}


	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/SimTransactionFormView", method = RequestMethod.GET)
	public String SimTransactionFormView ( Model model, HttpServletRequest request,HttpServletResponse response){
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		else {

		String transID = request.getParameter("transactionID");
		calendar.setTime(new Timestamp(System.currentTimeMillis()));
		SimTransaction simTransaction;
		List<SimTransaction> listSimQuery = new ArrayList<SimTransaction>();
		String navAction = "2";
        String result [] =new String[4];
        int SelectedIndex = 0;

		session=almsessions.getSession();
	if(session.isOpen() && session !=null) {
		tx=session.beginTransaction();
		notifications.headerNotifications(session, model);
		try {
			navAction= request.getParameter("NavAction");

			if ( transID == null  ) 
			{

				model.addAttribute("creationDate", formatter.format(new Timestamp(System.currentTimeMillis())).toString());
				model.addAttribute("lastModifiedDate", formatter.format(new Timestamp(System.currentTimeMillis())).toString());
				model.addAttribute("listItemsNav", mapper.writeValueAsString("noList"));
				model.addAttribute("ListTransItem", "addNew");
				model.addAttribute("totalQty", "0");
				model.addAttribute("SelectedIndex", "addNew");
				model.addAttribute("simTransCount", "addNew");
				model.addAttribute("docStatus", "addNew");

				}else{	


					 listSimQuery = session.createQuery("SELECT transactionID FROM SimTransaction").list();
						result = form.NavigationNP(session,"SIM_TRANSACTION","TRANSACTION_ID",transID,"LAST_MODIFIED_DATE",navAction);		

						SelectedIndex= Integer.parseInt(result[1]);
						transID=result[2];

						model.addAttribute("SelectedIndex", SelectedIndex);
						model.addAttribute("simTransCount", Integer.parseInt(result[0]));

					 simTransaction = (SimTransaction) session.get(SimTransaction.class, transID);

		    		model.addAttribute("creationDate", formatter.format(simTransaction.getCreationDate()).toString());
					model.addAttribute("transactionType", simTransaction.getTransactionType());
					model.addAttribute("totalQty", simTransaction.getTotalQty());
					model.addAttribute("status", simTransaction.getStatus());
				  	model.addAttribute("transactionID", simTransaction.getTransactionID());
				  	model.addAttribute("lastModifiedDate", formatter.format(simTransaction.getLastModifieddate()).toString());


					queryString = 	" SELECT t.SOURCE_TYPE as sourceType , t.SOURCE as source ,t.SOURCE_ID as sourceID," + 
					" t.SOURCE_MSISDN as sourceMSISDN,t.DESTINATION_TYPE as destinationType,t.DESTINATION as destination , t.DESTINATION_ID as destinationID, t.TRANSACTION_ID as transactionID,t.TOTAL_QTY as totalQty, TO_CHAR(t.CREATION_DATE, 'YYYY-MM-DD HH24:MI:SS') as creationDate," + 
					" TO_CHAR(t.LAST_MODIFIED_DATE, 'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate,t.SIM_TRANSACTION_ITEM_ID as simTransactionItemID, x.serial_obj " + 
					" FROM SIM_TRANSACTION_ITEM t LEFT JOIN(select distinct SIM_TRANSACTION_ITEM_ID,json_object('serialArray' value json_arrayagg(json_object('serial_no' value SERIAL_NUMBER,'msisdn'  value MSISDN,'simID' value SIM_ID,'serialItemId' value ID))) as serial_obj " + 
					"from SERIAL_NUMBER_SIM_TRANSACTION group by SIM_TRANSACTION_ITEM_ID) x on x.SIM_TRANSACTION_ITEM_ID = t.SIM_TRANSACTION_ITEM_ID where t.TRANSACTION_ID like :param1 " ;
					query = session.createSQLQuery(queryString);
					query.setParameter("param1", transID);
					
					List<SimTransactionBoq> listBoq = (List<SimTransactionBoq>)((SQLQuery) query).addScalar("sourceType")
					.addScalar("source").addScalar("sourceID", new StringType()).addScalar("sourceMSISDN", new StringType())
					.addScalar("destinationType", new StringType()).addScalar("destination", new StringType())
					.addScalar("destinationID", new StringType()).addScalar("transactionID", new StringType()).addScalar("totalQty", new IntegerType())
					.addScalar("creationDate", new TimestampType()).addScalar("lastModifieddate", new TimestampType())
					.addScalar("simTransactionItemID", new StringType()).addScalar("serial_obj", new StringType())
					.setResultTransformer(Transformers.aliasToBean(SimTransactionBoq.class)).list();
					
				if(listBoq !=null) {
					model.addAttribute("ListTransItem", mapper.writeValueAsString(listBoq));
				}else {
				model.addAttribute("ListTransItem", mapper.writeValueAsString("Empty"));	
				}	
  	
			}

	}
		catch (Exception e) {
			logger.info("Error in displaying the SimTransaction form view with a message of :"+e+"\n"+e.getMessage());
			model.addAttribute("ListTransItem", "notAddNew");
    		model.addAttribute("creationDate", null);
			model.addAttribute("transactionType", null);
			model.addAttribute("totalQty", null);
			model.addAttribute("status", null);
		  	model.addAttribute("transactionID", null);
		  	model.addAttribute("lastModifiedDate", null);
		  	model.addAttribute("listItemsNav", null);
		  	return "redirect:/SimTransactionListView";
		  	}
		finally {
			if(session != null && session.isOpen())
			{
				tx.commit();
				session.close();
			}
		}
	}
		return "SimTransactionFormView";
	}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/SimTransactionFormSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> SimTransactionFormSave( Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response){
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login","redirect:/");
			return rtn;
		}else {
			
			session=almsessions.getSession();
			if(session.isOpen() && session !=null) {
				tx=session.beginTransaction();
				try {

		String ID;
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		String createdDate = request.getParameter("creationDate");
		String creationDate;
		Date date = new Timestamp(System.currentTimeMillis());
		Timestamp CreationDate;
		Timestamp lastModifiedDate = new Timestamp(date.getTime());
		String transactionType = request.getParameter("transactionType");
		String status = request.getParameter("status");
		String totalQty = request.getParameter("totalQty");
		String approveFlag = request.getParameter("approveFlag");
		String[] slctDel=request.getParameterValues("slctDel[]");
		String[] slctDel1=request.getParameterValues("slctDel1[]");
		String smTrItID;
		String serialId;
		String qty;
		int smTrItemQty=0;
		String lat=null;
		String lng=null;

		SimTransaction simTransaction = new SimTransaction();
		
		if (StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNew")) {

			synchronized (this) {						
				ID = "SIMTRANS_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT SIM_TRANSACTION FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET SIM_TRANSACTION = SIM_TRANSACTION + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
			//ID = "SIMTRANS_" + year + "_" + appConfig.getSequenceNbr(65);
		} else {
			ID = request.getParameter("transactionID");
		}
		
		model.addAttribute("transactionID", ID);
		
		simTransaction.setTransactionID(ID);
		simTransaction.setCreationDate(new Timestamp(formatter.parse(createdDate).getTime()));
		simTransaction.setLastModifieddate(lastModifiedDate);
		simTransaction.setTransactionType(transactionType);
		simTransaction.setStatus(status);
		simTransaction.setTotalQty(Integer.parseInt(totalQty));

		session.saveOrUpdate(simTransaction);
	
		if(slctDel !=null)
		{
			queryString = "delete SimTransactionItem t where t.simTransactionItemID IN (:param1) ";
			query = session.createQuery(queryString);
			query.setParameterList("param1", slctDel);
			query.executeUpdate();

			queryString = "delete SerialNumberSimTransaction t where t.simTransactionItemID IN (:param1) ";
			query = session.createQuery(queryString);
			query.setParameterList("param1", slctDel);
			query.executeUpdate();

		}
		

		if(slctDel1 != null)
		{
         	queryString = "delete SerialNumberSimTransaction t where t.ID IN (:param1) ";
         	query = session.createQuery(queryString);
    		query.setParameterList("param1", slctDel1);
    		query.executeUpdate();		}
		
		if (itemParameters.getDictParameter() != null) {
			SimTransactionItem smTrItem;
			incorrectSerials=new ArrayList<String>();  
			incorrectMSISDN=new ArrayList<String>(); 
			incorrectSimSource=new ArrayList<String>(); 
			incorrectIndex=new HashSet<Integer>();
			jsonObjectErrors  = new JSONObject();
			for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
				smTrItem  = new SimTransactionItem();
				String sourceID=itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_SOURCE_ID);
				if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_ID), "0")) {
					synchronized (this) {						
						smTrItID = "SMTRNSI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT SIM_TRANS_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET SIM_TRANS_ITEM = SIM_TRANS_ITEM + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}
					//smTrItID = "SMTRNSI_" + year + "_" + appConfig.getSequenceNbr(66);
				} else {
					smTrItID = itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_ID);										
				}
				
				smTrItem.setSimTransactionItemID(smTrItID);
				creationDate = itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_CREATION_DATE);
				
				if (creationDate == "") {
					CreationDate = new Timestamp(System.currentTimeMillis());
				} else {
					date = formatter.parse(creationDate);
					CreationDate = new Timestamp(date.getTime());
				}
				
				smTrItem.setCreationDate(CreationDate);
				smTrItem.setLastModifieddate(lastModifiedDate);
				smTrItem.setTransactionID(ID);
				smTrItem.setSourceType(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_SOURCE_TYPE));
				smTrItem.setSource(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_SOURCE));
				smTrItem.setSourceID(sourceID);
				smTrItem.setSourceMSISDN(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_SOURCE_MSISDN));
				smTrItem.setDestinationType(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION_TYPE));
				smTrItem.setDestination(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION));
				smTrItem.setDestinationID(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION_ID));
				qty=itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_QTY);
				smTrItemQty=Integer.parseInt(qty);
				smTrItem.setTotalQty(smTrItemQty);
				session.saveOrUpdate(smTrItem);

				String serialNum = itemParameters.getDictParameter().get(i).get(SIM_TRANS_SERIAL_NUM);
				if(!StringUtils.equalsAnyIgnoreCase(serialNum, "null") && !StringUtils.equalsAnyIgnoreCase(serialNum, "{\"serialArray\":[]}") ) {
					SerialNumberSimTransaction serialnum;
					 ArrayList serialArrayList;
						Query simCardID;
						String smid;
	
						//ValidateSimTransactions(session, serialNum, sourceID, i);
					 Object serialObj = new JSONParser().parse(serialNum);
					 JSONArray serialJSNArray =  (JSONArray) ((HashMap) serialObj).get("serialArray");
					for (Object serialJSN : (JSONArray) serialJSNArray) {
						 serialnum = new SerialNumberSimTransaction();

						serialArrayList = new ArrayList((((HashMap) serialJSN).values()));
						ValidateSimTransactions(session,(String)serialArrayList.get(3),(String)serialArrayList.get(2),(String)serialArrayList.get(1),sourceID,i);

						if (StringUtils.equalsIgnoreCase((String)serialArrayList.get(0), "0")) {
							synchronized (this) {						
								serialId = "SrlId_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT SIM_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createSQLQuery("UPDATE SEQ_TABLE SET SIM_SERIALNO = SIM_SERIALNO + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
								}
						//serialId = "SrlId_" + year + "_" + appConfig.getSequenceNbr(70);
					} else {
						serialId = (String)serialArrayList.get(0);										
					}
											
					serialnum.setID(serialId);
					serialnum.setSerialNumber((String)serialArrayList.get(3));
					serialnum.setMSISDN((String)serialArrayList.get(2));
					serialnum.setSimID((String)serialArrayList.get(1));					
					serialnum.setCreationDate(CreationDate);
					serialnum.setLastModifieddate(lastModifiedDate);
					serialnum.setSimTransactionItemID(smTrItID);
					serialnum.setTransactionID(ID);
					serialnum.setSourceType(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_SOURCE_TYPE));
					serialnum.setSource(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_SOURCE));
					serialnum.setSourceID(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_SOURCE_ID));
					serialnum.setSourceMSISDN(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_SOURCE_MSISDN));
					serialnum.setDestinationType(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION_TYPE));
					serialnum.setDestination(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION));
					serialnum.setDestinationID(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION_ID));
					session.saveOrUpdate(serialnum);


				if (StringUtils.equalsIgnoreCase(approveFlag, "1") && StringUtils.equalsIgnoreCase(status, "Approved"))
				{
					
					
					SimCard SimCardList;
					Agent agent;
					Warehouse warehouse;
					Shops shops;

						simCardID = session.createSQLQuery("SELECT ID FROM SIM_CARD WHERE ID = :param1");
						simCardID.setString("param1", (String)serialArrayList.get(1));
						smid= (String) simCardID.uniqueResult();
						SimCardList = (SimCard) session.get(SimCard.class, smid);
						SimCardList.setLastModifieddate(lastModifiedDate);
						SimCardList.setLocationID(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION_ID));
						SimCardList.setLocationName(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION));
						SimCardList.setLocationType(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION_TYPE));

						if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION_TYPE), "Agent"))
						{
							agent = (Agent) session.get(Agent.class, itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION_ID));
							lat=agent.getAgentLat();
							lng=agent.getAgentLng();


						}
						else if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION_TYPE), "Warehouse"))

							{
								warehouse = (Warehouse) session.get(Warehouse.class, itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION_ID));
								lat= warehouse.getWareLat();
								lng= warehouse.getWareLong();


							}
							else if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION_TYPE), "Shop"))

								{
									shops = (Shops) session.get(Shops.class, itemParameters.getDictParameter().get(i).get(SIM_TRANS_ITEM_DESTINATION_ID));
									lat=shops.getLatitude();
									lng= shops.getLongtitude();


								}

						SimCardList.setLatitude(lat);										
						SimCardList.setLongtitude(lng);

						session.saveOrUpdate(SimCardList);
				}//end if for approve flag											
			}//end for serial		
		}//end if serial empty
	}//end for dict for param	
}//end iff getdict
		
		if(!incorrectIndex.isEmpty() && StringUtils.equalsIgnoreCase(approveFlag, "1")){
			jsonObjectErrors.put("incorrectSerials", incorrectSerials);
			jsonObjectErrors.put("incorrectMSISDN", incorrectMSISDN);
			jsonObjectErrors.put("incorrectSimSource", incorrectSimSource);
			jsonObjectErrors.put("incorrectIndex", incorrectIndex);
			rtn.put("jsonObjectErrors", jsonObjectErrors);
			tx.rollback();
			session.close();
		}else if(!incorrectIndex.isEmpty()){
			jsonObjectErrors.put("incorrectSerials", incorrectSerials);
			jsonObjectErrors.put("incorrectMSISDN", incorrectMSISDN);
			jsonObjectErrors.put("incorrectSimSource", incorrectSimSource);
			jsonObjectErrors.put("incorrectIndex", incorrectIndex);
			rtn.put("jsonObjectErrors", jsonObjectErrors);
			tx.commit();
			session.close();
			}else{
				rtn.put("jsonObjectErrors", "");
				tx.commit();
				session.close();
			}

		rtn.put("transactionId", ID);
		
				}catch (Exception e) {
				logger.info("error while saving the transaction "+"\n"+e.getMessage());
				rtn.put("jsonObjectErrors", "");
				}finally {
					if(session != null && session.isOpen()) {
						tx.commit();
						session.close();	
					}
					}
				}

		return rtn;
		}
	}


	@RequestMapping(value = "/SimTransactionListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SimTransactionListViewDelete(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("Login")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		else {

		String[] idList = request.getParameterValues("ID[]");

		session=almsessions.getSession();
		if(session.isOpen() && session !=null) {
		tx=session.beginTransaction();
		try {


		queryString = "delete SimTransaction t where t.transactionID IN (:param1)";
     	query = session.createQuery(queryString);
		query.setParameterList("param1", idList);
		query.executeUpdate();	

		queryString = "delete SimTransactionItem   where transactionID IN (:param1)";
    	query = session.createQuery(queryString);
		query.setParameterList("param1", idList);
		query.executeUpdate();	

		queryString = "delete SerialNumberSimTransaction t where t.transactionID IN (:param1)";
		query = session.createQuery(queryString);
		query.setParameterList("param1", idList);
		query.executeUpdate();	

		}catch (Exception e) {
			logger.info("error deleting sim transaction in sim transaction list view"+e);
		}finally {
		if(session != null && session.isOpen())
		{
			tx.commit();
			session.close();
		}
			}
	}
		return rtn;
		}
	}


	@RequestMapping(value = "/DeleteTransaction", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> DeleteTransaction( Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("Login")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}else {
		String transactionID = request.getParameter("transactionID");
		if (transactionID.isEmpty())
		{
			return rtn;	
		}

		session = almsessions.getSession();
		if(session!= null && session.isOpen()) {
			tx=session.beginTransaction();
			try {
				query=session.createSQLQuery("DELETE SIM_TRANSACTION_ITEM WHERE TRANSACTION_ID='"+transactionID+"'");
				query.executeUpdate();
				
				query=session.createSQLQuery("DELETE SERIAL_NUMBER_SIM_TRANSACTION WHERE TRANSACTION_ID='"+transactionID+"'");
				query.executeUpdate();
				
				query=session.createSQLQuery("DELETE SIM_TRANSACTION WHERE TRANSACTION_ID='"+transactionID+"'");
				query.executeUpdate();
					
			}catch (Exception e) {
				logger.info("Error @ Deleting Transaction with a msg "+e+"\n"+e.getMessage());
			}finally {
				if(session != null && session.isOpen())
				{
					tx.commit();
					session.close();
				}
					}
		}
		return rtn;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllSimSerials", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllSimSerials( Model model,HttpServletResponse response, HttpServletRequest request ) {
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("Login")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}else {

			List<SimCard> listSims = new ArrayList<SimCard>();
			String serialNumber = "%" + request.getParameter("serialNumber") + "%";
			String location = "%" + request.getParameter("location") + "%";
			Query simCards=null;
			session = almsessions.getSession();
	
		if(session!=null && session.isOpen()) {
			tx = session.beginTransaction();
			

			try {
			simCards = session.createQuery("select a.serialNum,a.MSISDN,"
			+ "a.simCardID from SimCard a where a.serialNum like :param1 AND a.locationID like :param2 ");
			simCards.setString("param1", serialNumber);
			simCards.setString("param2", location);
			simCards.setFirstResult(0);
			simCards.setMaxResults(40);
			listSims = simCards.list();

			} catch (Exception e) {
			logger.info("Error while getting records @ query: " + simCards+"with a msg"+ e+"\n"+e.getMessage());
			rtn.put("ListSim", "");
			}
			finally {
			if(session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
				}
		
			rtn.put("ListSim", listSims);
				}
			return rtn;
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllSimMSISDN", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllSimMSISDN( Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}else {

		List<SimCard> listSims = new ArrayList<SimCard>();
		String msisdn = "%" + request.getParameter("msisdn") + "%";
		String location = "%" + request.getParameter("location") + "%";
		Query simCards;
		session=almsessions.getSession();
		if(session !=null && session.isOpen()) {
			tx=session.beginTransaction();
		try {

			 simCards = session.createQuery("select a.serialNum,a.MSISDN,"
			+ "a.simCardID from SimCard a where a.MSISDN like :param1 AND a.locationID like :param2 ");
			simCards.setString("param1", msisdn);
			simCards.setString("param2", location);
			simCards.setFirstResult(0);
			simCards.setMaxResults(40);
			listSims = simCards.list();


			rtn.put("ListSim", listSims);
			}catch (Exception e) {
			logger.info("Error getting Sim MSISDN with a msg"+e+"\n"+e.getMessage());
			rtn.put("ListSim", "");
			}finally {
			if(session !=null && session.isOpen()) {
				tx.commit();
				session.close();
			}
					}
}
		return rtn;
}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllSimID", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllSimID(Model model, HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login","redirect:/");
			return rtn;
		}else {

		List<SimCard> listSims = new ArrayList<SimCard>();
		String id = "%" + request.getParameter("id") + "%";	
		String location = "%" + request.getParameter("location") + "%";
		session=almsessions.getSession();
		Query simCards;
		if(session !=null && session.isOpen()) {
		tx=session.beginTransaction();
		try {

			simCards = session.createQuery("select a.serialNum,a.MSISDN,"
					+ "a.simCardID from SimCard a where a.simCardID like :param1 AND a.locationID like :param2 ");
			simCards.setString("param1", id);
			simCards.setString("param2", location);
			simCards.setFirstResult(0);
			simCards.setMaxResults(40);
			listSims = simCards.list();


			rtn.put("ListSim", listSims);
		}catch (Exception e) {
			logger.info("error getting sim IDs"+e+"\n"+e.getMessage());
			rtn.put("ListSim", "");
			}finally {
				if(session !=null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
		}
	}
	

	public void ValidateSimTransactions(Session session,String serialNum,String msisdn,String simID, String sourceID,int index){

			try {
				String simLocation;
				String simId;
				 query = session.createQuery("SELECT locationID FROM SimCard WHERE simCardID like :param1");
				 query.setString("param1",simID);
				 simLocation= (String) query.uniqueResult();									
				if (!StringUtils.equalsIgnoreCase(simLocation, sourceID)) {
					incorrectSimSource.add(simID);
					incorrectIndex.add(index);
				} 									

					 query = session.createQuery("SELECT simCardID FROM SimCard WHERE serialNum like :param1");
					 query.setString("param1",serialNum);
					 simId= (String) query.uniqueResult();										
					if (StringUtils.equalsIgnoreCase(simId, null)) {
						incorrectSerials.add(serialNum);
						incorrectIndex.add(index);
					} 										
					 query = session.createQuery("SELECT simCardID FROM SimCard WHERE MSISDN = :param1");
					 query.setString("param1", msisdn);									
					 simId= (String) query.uniqueResult();									
					if(StringUtils.equalsIgnoreCase(simId, null)) {
						incorrectMSISDN.add(msisdn);
						incorrectIndex.add(index);
						}
				
			}catch (Exception e) {
					logger.info("Error Validating : "+e);
				}

		
	}
	

	@RequestMapping(value = "/GetAllSimTrans", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllRegionss(Locale locale, Model model, HttpServletRequest request,
		HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			System.out.println("GetAllAgents");

			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
	try {

			String simTrans = "%" + request.getParameter("simTrans") + "%";
			query = session.createSQLQuery("SELECT distinct TRANSACTION_ID,TRANSACTION_TYPE FROM SIM_TRANSACTION where UPPER(TRANSACTION_ID)like UPPER(:param1) OR UPPER(TRANSACTION_TYPE)like UPPER(:param1) ORDER BY LAST_MODIFIED_DATE DESC");
			query.setString("param1", simTrans);
			query.setFirstResult(0);
			query.setMaxResults(40);

			rtn.put("ListSimTrans", query.list());
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
	
}
