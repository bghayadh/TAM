	package com.aliat.alm.controller;
	
	import java.sql.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.AlmDbSession;
//import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.Agent;
import com.aliat.alm.models.AgentArea;
import com.aliat.alm.models.AgentRegion;
import com.aliat.alm.models.AgentShops;
import com.aliat.alm.models.AreaBorder;
import com.aliat.alm.models.Clients;
import com.aliat.alm.models.RegionBorder;
import com.aliat.alm.models.Shops;
import com.aliat.alm.models.SimCard;
import com.aliat.alm.models.Warehouse;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
	
	@Controller
	public class SimCardController {
		private static final Logger logger = LoggerFactory.getLogger(SimCardController.class);
		
		ObjectMapper mapper = new ObjectMapper();
		Query query;
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String queryString;
		Session session = null;
		Transaction tx = null;

		
		@Autowired
		Form form;
		
		
		
		@Autowired
		Notify notifications;
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SimCardListView", method = RequestMethod.GET)
	public String SimCardListView(Model model, HttpServletRequest request,HttpServletResponse response){
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}else {
			List<SimCard> simList = new ArrayList<SimCard>();

		session = AlmDbSession.getInstance().getSession();
		if(session != null && session.isOpen()) {
			notifications.headerNotifications(session, model);
		tx = session.beginTransaction();
		try {
					
			simList = session.createQuery("SELECT t.simCardID,t.simCardID,t.serialNum,t.MSISDN,TO_CHAR(t.lastModifieddate, 'YYYY-MM-DD HH24:MI:SS'),t.locationName,t.locationType from SimCard t").list();
			model.addAttribute("ListGridTable", mapper.writeValueAsString(simList));
		}
		catch (Exception e) {
		logger.info("Error in showing the list view of Sim Card with a message of :"+e+'\n'+e.getMessage());
		model.addAttribute("ListGridTable", "");

		}
		finally {
			if(session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
			}
		}
		return "SimCardListView";
		}
	}
	
	
	@RequestMapping(value = "/SimCardDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SimCardDelete(Model model, HttpServletRequest request,
			HttpServletResponse response) {
	
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("Login")) {
			rtn.put("Login","Login" );
			return rtn;
		}
		else {
		String[] idList = request.getParameterValues("ID[]");
		session = AlmDbSession.getInstance().getSession();
		if(session != null && session.isOpen())
		{
		tx = session.beginTransaction();
		try {
			if(idList != null) {
				queryString = "delete SimCard t  where t.simCardID IN (:param1)";
				query = session.createQuery(queryString);
				query.setParameterList("param1", idList);
				query.executeUpdate();
		
			}
		}
		catch (Exception e) {
			logger.info("Error in deleting on the level of SimCard with a message of :"+e);	
}
		finally {
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
	@RequestMapping(value = "/SimCardFormView", method = RequestMethod.GET)
	public String SimCardFormView (Model model, HttpServletRequest request,HttpServletResponse response) {
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		else {
		String simId = request.getParameter("simId");
		SimCard SimCardList;
		List<SimCard> listQuery;
		List<SimCard> listLFP;
		calendar.setTime(new Timestamp(System.currentTimeMillis()));
		session = AlmDbSession.getInstance().getSession();

		String navAction = "2";
        String result [] =new String[4];
        int SelectedIndex = 0;

		if(session != null && session.isOpen()) {
		tx = session.beginTransaction();
		notifications.headerNotifications(session, model);
	try {
		navAction= request.getParameter("NavAction");

		if ( simId == null  ) 
		{
			model.addAttribute("type", "addNew");
			model.addAttribute("creationDate", formatter.format(new Timestamp(System.currentTimeMillis())).toString());
			model.addAttribute("lastModifiedDate", formatter.format(new Timestamp(System.currentTimeMillis())).toString());
			model.addAttribute("listItemsNav", mapper.writeValueAsString("noList"));
			model.addAttribute("ListSim", "addNew");
			model.addAttribute("SelectedIndex", "addNew");
			model.addAttribute("simCardCount", "addNew");
			model.addAttribute("docStatus", "addNew");

	
		}else{	
			
			listQuery = session.createQuery("SELECT simCardID FROM SimCard").list();
			
			result = form.NavigationNP(session,"SIM_CARD","ID",simId,"LAST_MODIFIED_DATE",navAction);		

			SelectedIndex= Integer.parseInt(result[1]);
			simId=result[2];

			model.addAttribute("SelectedIndex", SelectedIndex);
			model.addAttribute("simCardCount", Integer.parseInt(result[0]));

			SimCardList = (SimCard) session.get(SimCard.class, simId);
			listQuery = new ArrayList<SimCard>();
		    model.addAttribute("creationDate", formatter.format(SimCardList.getCreationDate()).toString());
			model.addAttribute("SrlNum", SimCardList.getSerialNum());
			model.addAttribute("TeleNum", SimCardList.getMSISDN());
			model.addAttribute("locationID", SimCardList.getLocationID());
			model.addAttribute("locationName", SimCardList.getLocationName());
			model.addAttribute("locationType", SimCardList.getLocationType());
		  	model.addAttribute("longtitude", SimCardList.getLongtitude());
		  	model.addAttribute("latitude", SimCardList.getLatitude());
			model.addAttribute("simId", SimCardList.getSimCardID());
		  	model.addAttribute("ordStatus", SimCardList.getStatus());
		  	model.addAttribute("lastModifiedDate", formatter.format(SimCardList.getLastModifieddate()).toString());

			}
		}catch (Exception e) {
				logger.info("Error in viewing the form view of SimCard with a message of :"+e +"\n"+e.getMessage());		
			    model.addAttribute("creationDate", "");
				model.addAttribute("SrlNum", "");
				model.addAttribute("TeleNum","");
				model.addAttribute("locationID", "");
				model.addAttribute("locationName", "");
				model.addAttribute("locationType", "");
			  	model.addAttribute("longtitude", "");
			  	model.addAttribute("latitude", "");
				model.addAttribute("simId", "");
			  	model.addAttribute("ordStatus", "");
			  	model.addAttribute("lastModifiedDate", "");	
				model.addAttribute("type", "");
				model.addAttribute("listItemsNav","");
				model.addAttribute("ListSim", "");
		}
		finally {
			if(session != null && session.isOpen()) {
			tx.commit();
			session.close();
			}
		}
			}
		

		return "SimCardFormView";
		}
	}
	
	@RequestMapping(value = "/SimCardFormSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> SimCardFormSave(Model model, HttpServletRequest request,
		 HttpServletResponse response){
		Map<String, Object> rtn = new LinkedHashMap<>();
	
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		else {
		String simId;
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		String srlNum = request.getParameter("SrlNum");
		String teleNum = request.getParameter("TeleNum");
		String locationID = request.getParameter("locationID");
		String locationName = request.getParameter("locationName");
		String locationType = request.getParameter("locationType");
		String status = request.getParameter("status");
		String latitude = request.getParameter("latitude");
		String longtitude = request.getParameter("longtitude");
		SimCard SimCardList;
		session = AlmDbSession.getInstance().getSession();
		if(session != null && session.isOpen()) {
		tx = session.beginTransaction();
		try {
		if (StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNew")) {
			synchronized (this) {						
				simId = "SIM_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT SIM_CARD FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET SIM_CARD = SIM_CARD + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
			//simId = "SIM_" + year + "_" + appConfig.getSequenceNbr(60);
			model.addAttribute("simid", simId);
	
		} else {
			simId = request.getParameter("simId");			
		}
			SimCardList = new SimCard();
		
			SimCardList.setSimCardID(simId);	
			SimCardList.setCreationDate(new Timestamp(formatter.parse(request.getParameter("creationDate")).getTime()));
			SimCardList.setLastModifieddate(new Timestamp(new Timestamp(System.currentTimeMillis()).getTime()));
			SimCardList.setSerialNum(srlNum);
			SimCardList.setMSISDN(teleNum);
			SimCardList.setLocationID(locationID);
			SimCardList.setLocationName(locationName);
			SimCardList.setLocationType(locationType);
			SimCardList.setStatus(status);
			SimCardList.setLatitude(latitude);
			SimCardList.setLongtitude(longtitude);
			session.saveOrUpdate(SimCardList);
			rtn.put("simId", simId);
			
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("Error in saving the SimCard form view with a message of :"+e+"\n"+e.getMessage());		}
		finally {
			if(session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}

		}
		return rtn;
		}
	}

	
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllLocations", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllLocations( Model model, HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}else {
			List<Warehouse> listWarehouse = new ArrayList<Warehouse>();
			List<Shops> listShops = new ArrayList<Shops>();
			List<Agent> listAgents = new ArrayList<Agent>();
			List<Clients> listClient = new ArrayList<Clients>();
			String location = "%"+ request.getParameter("location")+"%";
			session = AlmDbSession.getInstance().getSession();
			if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

		Query warehouse = session.createQuery("SELECT wareID, warehouseName, concat('','Warehouse'), wareLong,wareLat from Warehouse  WHERE wareID like :param1 OR warehouseName like :param1 ");
		warehouse.setString("param1","%"+location+"%" );

		warehouse.setFirstResult(0);
		warehouse.setMaxResults(40);
		listWarehouse = warehouse.list();

		Query shop = session.createQuery("SELECT shopId, shopName, concat('','Shop'),longtitude,latitude from Shops  WHERE shopId like :param1 OR shopName like :param1 ");
		shop.setString("param1","%"+location+"%" );
		shop.setFirstResult(0);
		shop.setMaxResults(40);
		listShops = shop.list();

		Query agent = session.createQuery("SELECT agentId,  concat(FIRST_NAME, ' ', LAST_NAME), concat('','Agent'),agentLng,agentLat from Agent  WHERE agentId like :param1 OR FIRST_NAME like :param1 OR LAST_NAME like :param1 ");
		agent.setString("param1","%"+location+"%" );
		agent.setFirstResult(0);
		agent.setMaxResults(40);
		listAgents = agent.list();
		
		Query client = session.createQuery("SELECT clientId,  concat(FIRST_NAME, ' ', LAST_NAME), concat('','Client') from Clients  WHERE clientId like :param1 OR FIRST_NAME like :param1 OR LAST_NAME like :param1 ");
		client.setString("param1","%"+location+"%" );
		client.setFirstResult(0);
		client.setMaxResults(40);
		listClient = client.list();

		List<String> listLocations = (List<String>) Stream.of(listWarehouse, listShops, listAgents, listClient)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

			mapper.writeValueAsString(listLocations); 
			
			if(listLocations.isEmpty()) {
				rtn.put("listLocations", "Empty");
			}
			else {
				rtn.put("listLocations", listLocations);
			}
		

			System.out.println("/*/*end good " + mapper.writeValueAsString(rtn));
			}catch (Exception e) {
				logger.info("error while getting locations"+e+"\n"+e.getMessage());
				
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
    
	
	@RequestMapping(value = "/GetSimCardsOnSite", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetSimCardsOnSite(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
			Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
					String ID = request.getParameter("ID");
					
					if (ID != null) {
						queryString = 	"select MSISDN, serialNum, status,TO_CHAR(creationDate, 'YYYY-MM-DD HH24:MI:SS'),"
								+ "TO_CHAR(lastModifieddate, 'YYYY-MM-DD HH24:MI:SS'), simCardID"
								+ " from SimCard where locationID like :param1" ;
								query = session.createQuery(queryString);
								query.setParameter("param1", ID);
						if (query.list() != null) {
							rtn.put("ListSimCards", query.list());						
						}
					} else {
						rtn.put("ListSimCards", "Empty");
					}

				} catch (Exception e) {
					logger.info(
							"Error getting sim cards that belong to this site with error message: "
									+ e.getMessage());
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
   

	@RequestMapping(value = "/GetAllSimCards", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllRegionss(Locale locale, Model model, HttpServletRequest request,
		HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			System.out.println("GetAllAgents");

			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
	try {

			String simId = "%" + request.getParameter("simId") + "%";
			query = session.createQuery("SELECT simCardID,MSISDN FROM SimCard where UPPER(simCardID)like UPPER(:param1) OR UPPER(MSISDN)like UPPER(:param1) ORDER BY lastModifieddate DESC");
			query.setString("param1", simId);
			query.setFirstResult(0);
			query.setMaxResults(40);

			rtn.put("ListSim", query.list());
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
