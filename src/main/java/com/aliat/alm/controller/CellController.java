package com.aliat.alm.controller;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import oracle.sql.TIMESTAMP;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.GCellPassive;
import com.aliat.alm.models.LCellPassive;
import com.aliat.alm.models.NodeListView;
import com.aliat.alm.models.UCellPassive;

@Controller
public class CellController {
	private static final Logger logger = Logger.getLogger(ClientsController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	private static String str;
	private static Object[] result;
	private static Object[] resultPassive;
	@Autowired
	Permissions permissions;
	
	@Autowired
	Form form;
	
	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notification;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/CellListView", method = RequestMethod.GET)

	public String CellListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {
				List<Object[]> cells = new ArrayList<>();

				 query = session.createNativeQuery("SELECT  ng.Gcell_Id as gcellPk, ng.Gcell_Id as Pk, ng.cellid as cellid,   ng.cellname as cellname , '2G' as Technology, na.unique_node_id as uniqueid, na.site_id as siteid, "
						+ " na.ware_name as sitename, na.Node_Name as nodename,TO_CHAR(ng.creation_date,'YYYY-MM-DD HH24:MI:SS') as creation, TO_CHAR(ng.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') as updated " + 
						" FROM  Node_GCELL ng LEFT JOIN " + 
						" NODE_ACTIVE na ON ng.Node_PK = na.Node_PK union SELECT  nu.Ucell_Id as ucellPk ,nu.Ucell_Id as Pk,  nu.cellid as cellid,   nu.cellname as cellname , '3G' as Technology, na.unique_node_id as uniqueid, na.site_id as siteid, " + 
						" na.ware_name as sitename, na.Node_Name as nodename,TO_CHAR(nu.creation_date,'YYYY-MM-DD HH24:MI:SS') as creation, TO_CHAR(nu.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') as updated " + 
						" FROM  Node_UCELL nu LEFT JOIN " + 
						" NODE_ACTIVE na ON nu.Node_PK = na.Node_PK union SELECT  nl.Lcell_Id as lcellPk ,nl.Lcell_Id as Pk,  nl.cellid as cellid,   nl.cellname as cellname , '4G' as Technology, na.unique_node_id as uniqueid, na.site_id as siteid, " + 
						" na.ware_name as sitename, na.Node_Name as nodename, TO_CHAR(nl.creation_date,'YYYY-MM-DD HH24:MI:SS') as creation,TO_CHAR( nl.update_date,'YYYY-MM-DD HH24:MI:SS') as updated " + 
						" FROM  Node_LCELL nl LEFT JOIN " + 
						" NODE_ACTIVE na ON nl.Node_PK = na.Node_PK");
				
				   cells.addAll(query.getResultList());
				   model.addAttribute("ListGridTable", mapper.writeValueAsString(cells));
					
			
			}catch (Exception e) {
			
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in CellListView due to \n "+ exceptionAsString);
				logger.info("Error in CellListView due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "CellListView";
	}
	
	
	


@SuppressWarnings("unchecked")
@RequestMapping(value = "/CellFormView", method = RequestMethod.GET)

public String CellFormView(Locale locale, Model model, HttpServletRequest request,
		HttpServletResponse response) {

	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		return "redirect:/";
	}
	String FormView="";
	
	session = almsessions.getSession();
	if (session != null && session.isOpen()) {

		tx = session.beginTransaction();
		notification.headerNotifications(session, model);
		String CellPK;
		try {
			CellPK = request.getParameter("CellPk");
			  if (CellPK != null) {
					model.addAttribute("Status", "old");
					if(CellPK.contains("GCELL")) {
						query = session.createNativeQuery("SELECT  ng.Gcell_Id, ng.cellid ,   ng.cellname  , na.unique_node_id , na.site_id , "
								+ " na.ware_name , na.Node_Name ,TO_CHAR(ng.creation_date,'YYYY-MM-DD HH24:MI:SS') , TO_CHAR(ng.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') " + 
								" FROM  Node_GCELL ng LEFT JOIN " + 
								" NODE_ACTIVE na ON ng.Node_PK = na.Node_PK where ng.Gcell_Id=:param1");
						      query.setParameter("param1", CellPK);
				             result = (Object[]) query.uniqueResult();
				             
				           query = session.createNativeQuery("SELECT ID, DATE_ON_AIR, SITE_SUB_TYPE, MODE_OF_OPERATION, "
				         		+ "ANTENNA_SHARED_WITH_4G, GSM_ANTENNA_1_MANUFACTURER, GSM_ANTENNA_MODEL_1,"
				         		+ " GSM_ANTENNA_2_MANUFACTURER, GSM_ANTENNA_MODEL_2, ANTENNA_GAIN, BEAM_WIDTH, "
				         		+ "AZIMUTH, ANTENNA_HEIGHT_AGL, ELECTRICAL_TILT, MECHANICAL_TILT, RET, FEEDER_SIZE, "
				         		+ "APPROXIMATE_FEEDER_LENGTH, TMA_MHA, REMARKS, SECTOR_STATUS, SECTOR_LOCKED_DATE, "
				         		+ "LOCKED_REASON, AT, MASTER_SECTOR_ID, FLAG, STATUS, NEP_SYNCH, CIRCLE_ID "
				         		+ " FROM CELL_PASSIVE_2G where CELL_ID=:param1 and CELLNAME=:param2 ");
				 	     query.setParameter("param1", result[1]);
				         query.setParameter("param2", result[2]);
				            
				       
							
				         resultPassive = (Object[]) query.uniqueResult();
				         if(resultPassive != null) {
							 model.addAttribute("datOnAir", resultPassive[1]);
							 model.addAttribute("siteSubType", resultPassive[2]);
							 model.addAttribute("modeOfOperation", resultPassive[3]);
							 model.addAttribute("antena4G", resultPassive[4]);
							 model.addAttribute("manufacture1", resultPassive[5]);
							 model.addAttribute("model1", resultPassive[6]);
							 model.addAttribute("manufacture2", resultPassive[7]);
							 model.addAttribute("model2", resultPassive[8]);
							 model.addAttribute("gain", resultPassive[9]);
							 model.addAttribute("beam", resultPassive[10]);
							 model.addAttribute("azimuth", resultPassive[11]);
							 model.addAttribute("AGL", resultPassive[12]);
							 model.addAttribute("electric", resultPassive[13]);
							 model.addAttribute("mechanical", resultPassive[14]);
							 model.addAttribute("ret", resultPassive[15]);
							 model.addAttribute("feederSize", resultPassive[16]);
							 model.addAttribute("approx", resultPassive[17]);
							 model.addAttribute("TMA", resultPassive[18]);
							 model.addAttribute("remarks", resultPassive[19]);
							 model.addAttribute("sectorStatus", resultPassive[20]);
							 model.addAttribute("sectorDate", resultPassive[21]);
							 model.addAttribute("lockedReason", resultPassive[22]);
							 model.addAttribute("AT", resultPassive[23]);
							 model.addAttribute("masterId", resultPassive[24]);
							 model.addAttribute("flag", resultPassive[25]);
							 model.addAttribute("status", resultPassive[26]);
							 model.addAttribute("nepSynch", resultPassive[27]);
							 model.addAttribute("circleId", resultPassive[28]);
				         
				        
						
					}
				         FormView= "GCellFormView";
					       
					}
			 
					else if(CellPK.contains("UCELL")) {
						query = session.createNativeQuery("SELECT  nu.Ucell_Id ,  nu.cellid ,   nu.cellname  , na.unique_node_id , na.site_id , " + 
								" na.ware_name , na.Node_Name ,TO_CHAR(nu.creation_date,'YYYY-MM-DD HH24:MI:SS') , TO_CHAR(nu.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') " + 
								" FROM  Node_UCELL nu LEFT JOIN " + 
								" NODE_ACTIVE na ON nu.Node_PK = na.Node_PK where nu.Ucell_Id=:param1");
						      query.setParameter("param1", CellPK);
				             result = (Object[]) query.uniqueResult();
	                 
				             query = session.createNativeQuery("SELECT ID, DATE_ON_AIR, SITE_SUB_TYPE, ANTENNA_GAIN, BEAM_WIDTH, "
						         		+ "AZIMUTH, ANTENNA_HEIGHT_AGL, ELECTRICAL_TILT, MECHANICAL_TILT, RET, FEEDER_SIZE, "
						         		+ "APPROXIMATE_FEEDER_LENGTH, TMA_MHA, REMARKS, SECTOR_STATUS, SECTOR_LOCKED_DATE, "
						         		+ "LOCKED_REASON, AT, MASTER_SECTOR_ID, FLAG, STATUS, NEP_SYNCH, CIRCLE_ID "
						         		+ " FROM CELL_PASSIVE_3G where CELL_ID=:param1 and CELLNAME=:param2 ");
						 	     query.setParameter("param1", result[1]);
						         query.setParameter("param2", result[2]);
						            
						       
									
						         resultPassive = (Object[]) query.uniqueResult();
						         if(resultPassive != null) {
									 model.addAttribute("datOnAir", resultPassive[1]);
									 model.addAttribute("siteSubType", resultPassive[2]);
									 model.addAttribute("gain", resultPassive[3]);
									 model.addAttribute("beam", resultPassive[4]);
									 model.addAttribute("azimuth", resultPassive[5]);
									 model.addAttribute("AGL", resultPassive[6]);
									 model.addAttribute("electric", resultPassive[7]);
									 model.addAttribute("mechanical", resultPassive[8]);
									 model.addAttribute("ret", resultPassive[15]);
									 model.addAttribute("feederSize", resultPassive[9]);
									 model.addAttribute("approx", resultPassive[10]);
									 model.addAttribute("TMA", resultPassive[11]);
									 model.addAttribute("remarks", resultPassive[12]);
									 model.addAttribute("sectorStatus", resultPassive[13]);
									 model.addAttribute("sectorDate", resultPassive[14]);
									 model.addAttribute("lockedReason", resultPassive[15]);
									 model.addAttribute("AT", resultPassive[16]);
									 model.addAttribute("masterId", resultPassive[17]);
									 model.addAttribute("flag", resultPassive[18]);
									 model.addAttribute("status", resultPassive[19]);
									 model.addAttribute("nepSynch", resultPassive[20]);
									 model.addAttribute("circleId", resultPassive[21]);
						         
						        
								
							}
						         FormView= "UCellFormView";
							       
							}
					else  {
						query = session.createNativeQuery("SELECT  nl.Lcell_Id ,  nL.cellid ,   nL.cellname  , na.unique_node_id , na.site_id , " + 
								" na.ware_name , na.Node_Name ,TO_CHAR(nl.creation_date,'YYYY-MM-DD HH24:MI:SS') , TO_CHAR(nl.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') " + 
								" FROM  Node_LCELL nl LEFT JOIN " + 
								" NODE_ACTIVE na ON nl.Node_PK = na.Node_PK where nl.Lcell_Id=:param1");
						      query.setParameter("param1", CellPK);
				             result = (Object[]) query.uniqueResult();
				             
				             
	                 
				             query = session.createNativeQuery("SELECT ID, LTE_CELL_ID, DATE_ON_AIR, CELLNAME, SITE_SUB_TYPE, ANTENNA_SHARED_WITH_2G,\r\n" + 
				             		"ANTENNA_MANUFACTURER, ANTENNA_MODEL, ANTENNA_GAIN, BEAM_WIDTH, AZIMUTH, ANTENNA_HEIGHT_AGL,\r\n" + 
				             		"ELECTRICAL_TILT, MECHANICAL_TILT, RET, FEEDER_SIZE, APPROXIMATE_FEEDER_LENGTH, TMA_MHA, SHARED_SITE,\r\n" + 
				             		"REMARKS, SECTOR_STATUS, SECTOR_LOCKED_DATE, LOCKED_REASON, DIPLEXER, DIPLEXER_PURPOSE, MASTER_SECTOR_ID,\r\n" + 
				             		"AT, FLAG, STATUS, NEP_SYNCH, CIRCLE_ID, DISCOVERY_DATE, LAST_SHOWN_DATE, LAST_MODIFIED_DATE\r\n" + 
				             		"FROM CELL_PASSIVE_4G WHERE LTE_CELL_ID=:param1 and CELLNAME=:param2");
						 	     query.setParameter("param1", result[1]);
						         query.setParameter("param2", result[2]);
						            
						         resultPassive = (Object[]) query.uniqueResult();
						         
						         if(resultPassive!=null) {
						         model.addAttribute("datOnAir", resultPassive[2]);
						         model.addAttribute("passivePK", resultPassive[1]);
						         model.addAttribute("siteSubType", resultPassive[4]);
						         model.addAttribute("antena2G", resultPassive[5]);
						         model.addAttribute("manufacture", resultPassive[6]);
						         model.addAttribute("model", resultPassive[7]);
						         model.addAttribute("gain", resultPassive[8]);
						         model.addAttribute("beam", resultPassive[9]);
						         model.addAttribute("azimuth", resultPassive[10]);
						         model.addAttribute("AGL", resultPassive[11]);
						         model.addAttribute("electric", resultPassive[12]);
						         model.addAttribute("mechanical", resultPassive[13]);
						         model.addAttribute("ret", resultPassive[14]);
						         model.addAttribute("feederSize", resultPassive[15]);
						         model.addAttribute("approx", resultPassive[16]);
						         model.addAttribute("TMA", resultPassive[17]);
						         model.addAttribute("sharedSite", resultPassive[18]);
						         model.addAttribute("remarks", resultPassive[19]);
						         model.addAttribute("sectorStatus", resultPassive[20]);
						         model.addAttribute("sectorDate", resultPassive[21]);
						         model.addAttribute("lockedReason", resultPassive[22]);
						         model.addAttribute("AT", resultPassive[23]);
						         model.addAttribute("flag", resultPassive[24]);
						         model.addAttribute("status", resultPassive[25]);
						         model.addAttribute("nepSynch", resultPassive[26]);
						         model.addAttribute("circleId", resultPassive[27]);
						         }
						         
						      
						         
								    FormView= "LCellFormView";
						
					}
					
					//INFORMATION TAB DATA 
					    model.addAttribute("cell_pk", result[0]);
					    model.addAttribute("cell_id", result[1]);
					    model.addAttribute("cell_name", result[2]);
					    model.addAttribute("uniqueId", result[3]);
					    model.addAttribute("site_id", result[4]);
					    model.addAttribute("site_name", result[5]);
					    model.addAttribute("node_name", result[6]);
					    model.addAttribute("creationDate", result[7]);
					    model.addAttribute("LastModified", result[7]);
					    
					
				  

					    }
					    
					    
					    
					
			  
		
		
		
		}catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in CellFormView due to \n "+ exceptionAsString);
			logger.info("Error in CellFormView due to \n "+ exceptionAsString);
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
		
		
	}

	return (FormView);
}
@RequestMapping(value = "/CellFormViewSave", method = RequestMethod.GET)
@ResponseBody
public  Map<String, Object> CellFormViewSave(Locale locale, Model model, HttpServletRequest request,
		@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) throws Exception {

    Map<String, Object> rtn = new LinkedHashMap<>();
    String cellPk = null;
    String passivePK;
    session = almsessions.getSession();
    if (session != null && session.isOpen()) {
        tx = session.beginTransaction();
        notification.headerNotifications(session, model);
        try {
        	
        	
        	cellPk=request.getParameter("cellpk");
        	Calendar calendar = new GregorianCalendar();
			if(cellPk.contains("GCELL")) {
        	GCellPassive cellPassive2G = new GCellPassive();
        	if (request.getParameter("passivePK") == "") {
        		passivePK = "Passive_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(
						session.createNativeQuery("SELECT GCELL_PASSIVE FROM SEQ_TABLE").uniqueResult().toString());
						} else {
				passivePK = request.getParameter("passivePk");
						}
        	cellPassive2G.setId(passivePK);
        	cellPassive2G.setCellId(request.getParameter("cellId"));
        	cellPassive2G.setDateOnAir(request.getParameter("datOnAir"));
        	cellPassive2G.setCellName(request.getParameter("cellName"));
        	cellPassive2G.setSiteSubType(request.getParameter("siteSubType"));
        	cellPassive2G.setModeOfOperation(request.getParameter("modeOfOperation"));
        	cellPassive2G.setAntennaSharedWith4G(request.getParameter("antena4G"));
        	cellPassive2G.setGsmAntenna1Manufacturer(request.getParameter("manufacture1"));
        	cellPassive2G.setGsmAntennaModel1(request.getParameter("model1"));
        	cellPassive2G.setGsmAntenna2Manufacturer(request.getParameter("manufacture2"));
        	cellPassive2G.setGsmAntennaModel2(request.getParameter("model2"));
        	cellPassive2G.setAntennaGain(request.getParameter("gain"));
        	cellPassive2G.setBeamWidth(request.getParameter("beam"));
        	cellPassive2G.setAzimuth(request.getParameter("azimuth"));
        	cellPassive2G.setAntennaHeightAGL(request.getParameter("AGL"));
        	cellPassive2G.setElectricalTilt(request.getParameter("electric"));
        	cellPassive2G.setMechanicalTilt(request.getParameter("mechanical"));
        	cellPassive2G.setRet(request.getParameter("ret"));
        	cellPassive2G.setFeederSize(request.getParameter("feederSize"));
        	cellPassive2G.setApproximateFeederLength(request.getParameter("approx"));
        	cellPassive2G.setTmaMha(request.getParameter("TMA"));
        	cellPassive2G.setRemarks(request.getParameter("remarks"));
        	cellPassive2G.setSectorStatus(request.getParameter("sectorStatus"));
        	cellPassive2G.setSectorLockedDate(request.getParameter("sectorDate"));
        	cellPassive2G.setLockedReason(request.getParameter("lockedReason"));
        	cellPassive2G.setAt(request.getParameter("AT"));
        	cellPassive2G.setMasterSectorId(request.getParameter("masterId"));
        	cellPassive2G.setFlag(request.getParameter("flag"));
        	cellPassive2G.setStatus(request.getParameter("status"));
        	cellPassive2G.setNepSynch(request.getParameter("nepSynch"));
        	cellPassive2G.setCircleId(request.getParameter("circleId"));

        	
        	session.saveOrUpdate(cellPassive2G);
			}
			
			
			else if(cellPk.contains("LCELL")) {
				
				LCellPassive cellPassive4G = new LCellPassive();
	        	if (request.getParameter("passivePK") == "") {
	        		passivePK = "Passive_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(
							session.createNativeQuery("SELECT LCELL_PASSIVE FROM SEQ_TABLE").uniqueResult().toString());
							} else {
					passivePK = request.getParameter("passivePk");
							}
	        	
	        	cellPassive4G.setId(passivePK);
	        	cellPassive4G.setLteCellId(request.getParameter("cellId"));
	        	cellPassive4G.setCellName(request.getParameter("cellName"));
	        	cellPassive4G.setDateOnAir(request.getParameter("datOnAir"));
	        	cellPassive4G.setSiteSubType(request.getParameter("siteSubType"));
	        	cellPassive4G.setAntennaSharedWith2G(request.getParameter("antena2G"));
	        	cellPassive4G.setAntennaManufacturer(request.getParameter("manufacture"));
	        	cellPassive4G.setAntennaModel(request.getParameter("model"));
	        	cellPassive4G.setAntennaGain(request.getParameter("gain"));
	        	cellPassive4G.setBeamWidth(request.getParameter("beam"));
	        	cellPassive4G.setAzimuth(request.getParameter("azimuth"));
	        	cellPassive4G.setAntennaHeightAGL(request.getParameter("AGL"));
	        	cellPassive4G.setElectricalTilt(request.getParameter("electric"));
	        	cellPassive4G.setMechanicalTilt(request.getParameter("mechanical"));
	        	cellPassive4G.setRet(request.getParameter("ret"));
	        	cellPassive4G.setFeederSize(request.getParameter("feederSize"));
	        	cellPassive4G.setApproximateFeederLength(request.getParameter("approx"));
	        	cellPassive4G.setTmaMha(request.getParameter("TMA"));
	        	cellPassive4G.setRemarks(request.getParameter("remarks"));
	        	cellPassive4G.setSectorStatus(request.getParameter("sectorStatus"));
	        	cellPassive4G.setSectorLockedDate(request.getParameter("sectorDate"));
	        	cellPassive4G.setLockedReason(request.getParameter("lockedReason"));
	        	cellPassive4G.setAt(request.getParameter("AT"));
	        	cellPassive4G.setFlag(request.getParameter("flag"));
	        	cellPassive4G.setStatus(request.getParameter("Status"));
	        	cellPassive4G.setNepSynch(request.getParameter("nepSynch"));
	        	cellPassive4G.setCircleId(request.getParameter("circleId"));
	        	cellPassive4G.setSharedSite(request.getParameter("sharedSite"));
	        	cellPassive4G.setDiplexer(request.getParameter("Diplexer"));
	        	cellPassive4G.setDiplexerPurpose(request.getParameter("dipPurpose"));
	        	cellPassive4G.setMasterSectorId(request.getParameter("masterSectorID"));

	        	session.saveOrUpdate(cellPassive4G);
			}
			
			
			
		else if(cellPk.contains("UCELL")) {
				
				UCellPassive cellPassive3G = new UCellPassive();
	        	if (request.getParameter("passivePK") == "") {
	        		passivePK = "Passive_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(
							session.createNativeQuery("SELECT UCELL_PASSIVE FROM SEQ_TABLE").uniqueResult().toString());
							} else {
					passivePK = request.getParameter("passivePk");
							}
	        	
	        	
	        	// Set the properties for CellPassive3G
	        	cellPassive3G.setId(passivePK);
	        	cellPassive3G.setCellId(request.getParameter("cellId"));
	        	cellPassive3G.setDateOnAir(request.getParameter("datOnAir"));
	        	cellPassive3G.setCellName(request.getParameter("cellName"));
	        	cellPassive3G.setSiteSubType(request.getParameter("siteSubType"));
	        	cellPassive3G.setAntennaGain(request.getParameter("gain"));
	        	cellPassive3G.setBeamWidth(request.getParameter("beam"));
	        	cellPassive3G.setAzimuth(request.getParameter("azimuth"));
	        	cellPassive3G.setAntennaHeightAGL(request.getParameter("AGL"));
	        	cellPassive3G.setElectricalTilt(request.getParameter("electric"));
	        	cellPassive3G.setMechanicalTilt(request.getParameter("mechanical"));
	        	cellPassive3G.setRet(request.getParameter("ret"));
	        	cellPassive3G.setFeederSize(request.getParameter("feederSize"));
	        	cellPassive3G.setApproximateFeederLength(request.getParameter("approx"));
	        	cellPassive3G.setTmaMha(request.getParameter("TMA"));
	        	cellPassive3G.setRemarks(request.getParameter("remarks"));
	        	cellPassive3G.setSectorStatus(request.getParameter("sectorStatus"));
	        	cellPassive3G.setSectorLockedDate(request.getParameter("sectorDate"));
	        	cellPassive3G.setLockedReason(request.getParameter("lockedReason"));
	        	cellPassive3G.setAt(request.getParameter("AT"));
	        	cellPassive3G.setMasterSectorId(request.getParameter("masterId"));
	        	cellPassive3G.setFlag(request.getParameter("flag"));
	        	cellPassive3G.setStatus(request.getParameter("status"));
	        	cellPassive3G.setNepSynch(request.getParameter("nepSynch"));
	        	cellPassive3G.setCircleId(request.getParameter("circleId"));
	        	
	        	
	        	session.saveOrUpdate(cellPassive3G);
			}
        	
        	
        	
						}
       

           
         catch (Exception e) {
            sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            exceptionAsString = sw.toString();
            logger.finest("Error in CellFormView due to \n " + exceptionAsString);
            logger.info("Error in CellFormView due to \n " + exceptionAsString);
        } finally {
            if (session != null && session.isOpen()) {
                tx.commit();
                session.close();
            }
        }
        
        rtn.put("cellPK",cellPk);
    }

  return rtn; 
   

}
@RequestMapping(value = "/GetAllCell", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllCell(Locale locale, Model model, HttpServletRequest request,
        HttpServletResponse response) {

    Map<String, Object> rtn = new LinkedHashMap<>();
    
    
    
    String Cell = "%" + request.getParameter("Cell") + "%"; 
    session = almsessions.getSession();
    if (session != null && session.isOpen()) {
        tx = session.beginTransaction();
        try {
        	
        	
        	query = session.createNativeQuery(
        		    "SELECT t1.CELLNAME, t1.GCELL_ID, t1.CELLID FROM NODE_GCELL t1 " +
        		    "WHERE LOWER(t1.CELLNAME) LIKE LOWER(:Cell) OR LOWER(t1.CELLID) " +
        		    "LIKE LOWER(:Cell) OR LOWER(t1.GCELL_ID) LIKE LOWER(:Cell) " +
        		    " UNION ALL " +
        		    "SELECT t2.CELLNAME, t2.LCELL_ID, t2.CELLID FROM NODE_LCELL t2 " +
        		    "WHERE LOWER(t2.CELLNAME) LIKE LOWER(:Cell) OR LOWER(t2.CELLID) " +
        		    "LIKE LOWER(:Cell) OR LOWER(t2.LCELL_ID) LIKE LOWER(:Cell) " +
        		    " UNION ALL " +
        		    "SELECT t3.CELLNAME, t3.UCELL_ID, t3.CELLID FROM NODE_UCELL t3 " +
        		    "WHERE LOWER(t3.CELLNAME) LIKE LOWER(:Cell) OR LOWER(t3.CELLID) " +
        		    "LIKE LOWER(:Cell) OR LOWER(t3.UCELL_ID) LIKE LOWER(:Cell)"
        		);
        		query.setParameter("Cell", Cell);
        		query.setFirstResult(0); 
        		query.setMaxResults(40); 

			
        	List<Object[]> results = query.list();
        	
        	rtn.put("ListCell", results);
        	}
        	
        	
   catch (Exception e) {
            sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            exceptionAsString = sw.toString();
            logger.finest("Error in GetAllNode due to \n " + exceptionAsString);
            logger.info("Error in GetAllNode due to \n " + exceptionAsString);
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