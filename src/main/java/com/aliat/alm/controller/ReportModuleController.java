package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.models.ReportInputParams;
import com.aliat.alm.models.ReportsList;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.aliat.mobile.restapi.Mobile_SIM_Reg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ReportModuleController {
	
	private static final String Param_ID = "ParamID";
	private static final String Input_Label = "InputLabel";
	private static final String Input_Type = "InputType";
	private static final String Input_Id = "InputId";
	private static final String Input_Name = "InputName";
	private static StringWriter sw;
	private static String exceptionAsString;
	private static final Logger logger = Logger.getLogger(Mobile_SIM_Reg.class.getName());
	private static Query query = null;
	private static Session session = null;
	private static Transaction tx = null;
	
	@Autowired
	ALMSessions almsessions;
	// to render the report list view !
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ReportListView", method = RequestMethod.GET)
	public String ReportList(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException {
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}else {
			List<ReportsList> listReport = new ArrayList<ReportsList>();
			
			session = almsessions.getSession();
			if(session != null && session.isOpen()) {
				tx=session.beginTransaction();
				try {
					listReport = session.createSQLQuery("SELECT ID as checkbox ,ID as idName,NAME,TO_CHAR(CREATION_DATE, 'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(LAST_MODIFICATION_DATE, 'YYYY-MM-DD HH24:MI:SS') FROM REPORTS_LIST").list();
					ObjectMapper mapper = new ObjectMapper();
					model.addAttribute("ListGridTable", mapper.writeValueAsString(listReport));		
				}catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in ReportListView due to \n "+ exceptionAsString);
					logger.info("Error in ReportListView due to \n "+ exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}						
		return "ReportListView";
		}
	}
	
	@RequestMapping(value = "/ReportListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> NotificationListViewDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("Login")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String[] idList = request.getParameterValues("ID[]");
		session = almsessions.getSession();
		if(session != null && session.isOpen()) {
			tx=session.beginTransaction();
			
			try {
				for(int i=0;i<idList.length;i++) {
					
					query = session.createSQLQuery("DELETE REPORT_INPUT_PARAMS where REPORT_ID ='"+idList[i]+"'");
					query.executeUpdate();
					
					query = session.createSQLQuery("DELETE REPORTS_LIST where ID ='"+idList[i]+"'");
					query.executeUpdate();
				}
				
			}catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ReportListViewDelete due to \n "+ exceptionAsString);
				logger.info("Error in ReportListViewDelete due to \n "+ exceptionAsString);
				rtn.put("Test", "Failed");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
			
		}
		rtn.put("Test", "DeleteDone");
		return rtn;

	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ReportFormView", method = RequestMethod.GET)
	public String ReportFormView (Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException {
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		String rprtId;
		ObjectMapper mapper = new ObjectMapper();
		rprtId = request.getParameter("rprtId");
		System.out.println("The rprtId is  " +rprtId);
		session = almsessions.getSession();
		if(session != null && session.isOpen()) {
			
			tx = session.beginTransaction();
			try {
				if ( rprtId == null  ) 
				{
				//	System.out.println("TT*********"+TicketID);
					model.addAttribute("ListPRqItem", "addNew");
					Date date;
					date = new Timestamp(System.currentTimeMillis());
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(date);
					SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					

					model.addAttribute("creationDate", formatter.format(date).toString());
					model.addAttribute("lastModifiedDate", formatter.format(date).toString());
					//System.out.println("The creation date is " +formatter.format(date).toString());
					model.addAttribute("listItemsNav", mapper.writeValueAsString("noList"));
					model.addAttribute("ListRprtItem", "addNew");

					}else{	
						ReportsList ReportList = (ReportsList) session.get(ReportsList.class, rprtId);
						//ReportInputParams ReportInputParams = (ReportInputParams) appConfig.find(ReportInputParams.class, rprtId);

						List<ReportsList> listQuery = new ArrayList<ReportsList>();
						List<ReportInputParams> paramQuery = new ArrayList<ReportInputParams>();
						paramQuery = session.createSQLQuery("SELECT REPORT_ID FROM REPORT_INPUT_PARAMS").list();

						listQuery = session.createSQLQuery("SELECT ID FROM REPORTS_LIST").list();
						if(listQuery !=null) {
								model.addAttribute("listItemsNav", mapper.writeValueAsString(listQuery));
								String queryOfActions = "SELECT * FROM REPORTS_LIST WHERE ID='"+rprtId+"'";
								List<ReportsList> listLFP = session.createSQLQuery(queryOfActions).list();
								model.addAttribute("ListLFP", mapper.writeValueAsString(listLFP));							
						}else {
							model.addAttribute("listLFP", "addNew");	
						}
						
						

						if(paramQuery !=null) {
							String queryOfActions = "from ReportInputParams where ReportID like :param1";
							Query q = session.createQuery(queryOfActions);
							q.setParameter("param1", rprtId);
							List<ReportInputParams> listReportParam =q.list();
							model.addAttribute("ListRprtItem", mapper.writeValueAsString(listReportParam));

						
					}else {

						model.addAttribute("ListRprtItem", "addNew");
						
					}
						SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
						 Date date = new Timestamp(System.currentTimeMillis());

						if (ReportList.getCreationDate() == null) {
							date = new Timestamp(System.currentTimeMillis());
							model.addAttribute("creationDate", formatter.format(date).toString());
						}
				    	else {
				    		date = ReportList.getCreationDate();
				    		model.addAttribute("creationDate", formatter.format(date).toString());
				    	}
						model.addAttribute("RprtName", ReportList.getReportName());
						model.addAttribute("Rprtdescription", ReportList.getDescription());

					  	model.addAttribute("rprtId", ReportList.getReportID());
					  	date = ReportList.getLastModifieddate();
					  	model.addAttribute("lastModifiedDate", formatter.format(date).toString());
					}
			}catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ReportFormView due to \n "+ exceptionAsString);
				logger.info("Error in ReportFormView due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "ReportFormView";
	}
	
	@RequestMapping(value = "/ReportFormSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ReportFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) throws Exception {
		Map<String, Object> rtn = new LinkedHashMap<>();
 		if (itemParameters.getDictParameter().size() != 0) {

		}

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
	//	new ObjectMapper();
		session=almsessions.getSession();
		if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
			try {
				
				
				String rprtid;

				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				//request.getParameter("type");
				if (StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNew")) {


					
					//new GetSeq();
					//rprtid = "RPRT_" + year + "_" + appConfig.getSequenceNbr(54);
					synchronized (this) {						
						rprtid = "RPRT_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT REPORT_LIST FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET REPORT_LIST = REPORT_LIST + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}
					System.out.println("TEst Salah rprtid is:"+ rprtid);
					model.addAttribute("rprtId", rprtid);

				} else {
					rprtid = request.getParameter("rprtId");					
					query=session.createSQLQuery("DELETE REPORTS_LIST WHERE REPORT_ID='"+rprtid+"'");
					query.executeUpdate();

				}



				DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		 
				Date date1 = null;
				Timestamp CreationDate;
				Timestamp lastModifiedDate;

				if (request.getParameter("creationDate") == "") {
					date1 = new Timestamp(System.currentTimeMillis());
					CreationDate = new Timestamp(date1.getTime());
				} else {
					String createdDate = request.getParameter("creationDate");
					date1 = formatter.parse(createdDate);
					CreationDate = new Timestamp(date1.getTime());
				}

			

		 

				Date rdate = new Timestamp(System.currentTimeMillis());
				lastModifiedDate = new Timestamp(rdate.getTime());
				String name = request.getParameter("name");
				String description = request.getParameter("description");

				ReportsList reportList = new ReportsList();


				reportList.setReportID(rprtid);
				reportList.setCreationDate(CreationDate);
				reportList.setLastModifieddate(lastModifiedDate);
				reportList.setReportName(name);
				reportList.setDescription(description);

				session.saveOrUpdate(reportList);
				//appConfig.persist(reportList, ReportsList.class);


		 			String[] slctDel=request.getParameterValues("slctDel[]");
		 			System.out.println("the slctDel val is:  "+Arrays.toString(slctDel));
					if(slctDel != null)
					{
				   		for(int i=0;i<slctDel.length;i++) {
				   			query = session.createSQLQuery("DELETE REPORT_INPUT_PARAMS ID='"+slctDel[i]+"'");
				   			query.executeUpdate();
				   		}
					}

					//////////////////boq shops table
					if (itemParameters.getDictParameter() != null) {
						System.out.println("getDictParameter is not null");
						String paramId ;
						String type ;
						String label ;
						String inputID ;			
						String Name;

					
						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
							

							paramId = itemParameters.getDictParameter().get(i).get(Param_ID);
							type = itemParameters.getDictParameter().get(i).get(Input_Type);
							label = itemParameters.getDictParameter().get(i).get(Input_Label);
							inputID = itemParameters.getDictParameter().get(i).get(Input_Id);
							Name = itemParameters.getDictParameter().get(i).get(Input_Name);
						
							ReportInputParams ReportInputParams = new ReportInputParams();
						if (StringUtils.equalsIgnoreCase(paramId, "")) 
							{
							synchronized (this) {						
								paramId = "RPRTI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT REPORT_PARAM FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createSQLQuery("UPDATE SEQ_TABLE SET REPORT_PARAM = REPORT_PARAM + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
								}
								//paramId= "RPRTI_" + year + "_" + appConfig.getSequenceNbr(55);		
								
								ReportInputParams.setID(paramId);
								ReportInputParams.setReportID(rprtid);
								ReportInputParams.setInputLabel(label);
								ReportInputParams.setInputType(type);
								ReportInputParams.setInputID(inputID);
								ReportInputParams.setInputName(Name);
								session.saveOrUpdate(ReportInputParams);
								//appConfig.persist(ReportInputParams,ReportInputParams.class);
						
								}else {

									ReportInputParams.setID(paramId);
									ReportInputParams.setReportID(rprtid);
									ReportInputParams.setInputLabel(label);
									ReportInputParams.setInputType(type);
									ReportInputParams.setInputID(inputID);
									ReportInputParams.setInputName(Name);
									session.saveOrUpdate(ReportInputParams);

									//appConfig.persist(ReportInputParams,ReportInputParams.class);
									
								}


						}

											
					
					}
					//////////////end of boq shops table
					

					rtn.put("BassamTest", "SaveDone");

					rtn.put("rprtId", rprtid);
			}catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ReportFormSave due to \n "+ exceptionAsString);
				logger.info("Error in ReportFormSave due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
	

		return rtn;

	}


	@RequestMapping(value = "/ReportFormViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ReportFormViewDelete(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {					
		Map<String, Object> rtn = new LinkedHashMap<>();
		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String idForm = request.getParameter("reportID");
		session = almsessions.getSession();
		if(session != null && session.isOpen()) {
			
			tx = session.beginTransaction();
			try {
				
				query = session.createSQLQuery("DELETE REPORT_INPUT_PARAMS where REPORT_ID ='"+idForm+"'");
				query.executeUpdate();
				
				query = session.createSQLQuery("DELETE REPORTS_LIST where ID ='"+idForm+"'");
				query.executeUpdate();
				
			}catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ReportFormSave due to \n "+ exceptionAsString);
				logger.info("Error in ReportFormSave due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		rtn.put("BassamTest", "DeleteDone");
		return rtn;
	}
	
	
}
