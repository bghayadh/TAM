package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
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
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.Area;
import com.aliat.alm.models.DevicesCausedProblem;
import com.aliat.alm.models.LinkFailurePlaces;
import com.aliat.alm.models.TroubleTickets;
import com.aliat.alm.models.TroubleTickets_Action;
import com.aliat.alm.models.TroubleTickets_Assign;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Logger;

@Controller
public class TroubleTicketController {

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static final Logger logger = Logger.getLogger(NetworkController.class.getName());
	private static StringWriter sw;
	private static String exceptionAsString;

	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;
	
	@Autowired
	Form form;

	@RequestMapping(value = "/TroubleTicketListView", method = RequestMethod.GET)
	public String TroubleTicketListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {

				query = session.createQuery(
						"select t.ticketId, t.tkdepartment, COALESCE(t.tksubject, ' ') , t.tkdescription, t.tkclientId, TO_CHAR(t.tkcreationDate, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(t.tklastModifiedDate, 'YYYY-MM-DD HH24:MI:SS'), t.tkservice, t.tkserviceIssue, TO_CHAR(t.tkissueAppeared, 'YYYY-MM-DD HH24:MI:SS'), t.tksiteId, t.tksiteName, t.tkstatus, t.tkpriority, t.tkclientName from TroubleTickets t ORDER BY t.tklastModifiedDate DESC");
				model.addAttribute("ListGridTable", mapper.writeValueAsString(query.list()));

			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "TroubleTicketListView";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredTroubleTicketListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredTroubleTicketListView(Locale locale, Model model, HttpServletRequest request,
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

				String startdate, enddate, department, subject,priority,status;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				department = request.getParameter("department");
				subject = request.getParameter("subject");
				priority = request.getParameter("priority");
				status = request.getParameter("status");
				
				
				List<String> listticket = new ArrayList<String>();

				String str = "select 1 as chkBox, TICKET_ID as ticketId,DEPARTMENT as tkdepartment,SUBJECT as tksubject,TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS') as tkcreationDate,TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as tklastModifiedDate,"
						+ " PRIORITY as tkpriority , STATUS as tkstatus from TROUBLE_TICKETS  ";
						
				if (startdate != null && enddate != null) {
					str = str + " where CREATION_DATE between TO_DATE('" + startdate
							+ "','YYYY-MM-DD') and TO_DATE('" + enddate + "','YYYY-MM-DD')";
				}
				if (department != null && !department.equalsIgnoreCase("")) {

					str = str + " and (upper(DEPARTMENT) LIKE upper('%" + department + "%') )";
				}

				if (subject != null && !subject.equalsIgnoreCase("")) {

					str = str + " and (upper(SUBJECT) LIKE upper('%" + subject + "%') )";
				}
				if (priority != null && !priority.equalsIgnoreCase("")) {

					str = str + " and (upper(PRIORITY) LIKE upper('%" + priority + "%') )";
				}
				if (status != null && !status.equalsIgnoreCase("")) {

					str = str + " and (upper(STATUS) LIKE upper('%" + status + "%'))";
				}
				str = str + " ORDER BY LAST_MODIFIED_DATE DESC ";

				Query query = session.createSQLQuery(str);
				
				listticket = query.list();
				
				rtn.put("listticket",listticket);
				System.out.println("Filtered Array: " + mapper.writeValueAsString(listticket));
			} catch (Exception e) {
				sw = new StringWriter();
				 e.printStackTrace(new PrintWriter(sw));
				 exceptionAsString = sw.toString();
				 logger.finest("Error in showing the filtered Trouble Ticket list view due to \n "+ exceptionAsString);
				  logger.info("Error in showing the filtered Trouble Ticket list view due to \n "+ exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;
	}
	@RequestMapping(value = "/TroubleTicketDelete", method = RequestMethod.GET)
	@ResponseBody
	public String TroubleTicketDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		String rtn = "Succeeded";
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			return "redirect:/";
		}

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				String[] idList = request.getParameterValues("tkID[]");

				if (idList != null) {

					query = session.createQuery("delete TroubleTickets t  where t.ticketId IN (:param1)");
					query.setParameterList("param1", idList);
					query.executeUpdate();

					query = session.createQuery("delete TroubleTickets_Action t  where t.ticketId  IN (:param1)");
					query.setParameterList("param1", idList);
					query.executeUpdate();

					query = session.createQuery("delete DevicesCausedProblem t  where t.ticketId  IN (:param1)");
					query.setParameterList("param1", idList);
					query.executeUpdate();

					query = session.createQuery("delete LinkFailurePlaces t  where t.ticketId  IN (:param1)");
					query.setParameterList("param1", idList);
					query.executeUpdate();

					query = session.createQuery("delete TroubleTickets_Assign t  where t.ticketId  IN (:param1)");
					query.setParameterList("param1", idList);
					query.executeUpdate();

				}
			} catch (Exception e) {

				System.out.println("catch messsage is " + e.getMessage());
				rtn = "Failed";

			} finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}

			}
		}
		return rtn;

	}

	@RequestMapping(value = "/TroubleTicketFormView", method = RequestMethod.GET)
	public String TroubleTicketFormView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		String TicketID, client, clientId, clientName, region,navAction = "2";
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String result [] =new String[4];
        int SelectedIndex = 0;

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {

				TicketID = request.getParameter("tkID");
				navAction= request.getParameter("NavAction");

				if (TicketID == null || TicketID == "") {
					System.out.println("TicketID is nulllllllllllllllllllll");
					model.addAttribute("ListPRqItem", "addNew");
					model.addAttribute("TkcreationDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("TklastModifiedDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("ListAssign", "addNew");
					model.addAttribute("ListLFP", "addNew");
					model.addAttribute("ListDCP", "addNew");
					model.addAttribute("ListAcArray", "addNew");
					model.addAttribute("listItemsNav", mapper.writeValueAsString("noList"));
					model.addAttribute("SelectedIndex", "addNew");
					model.addAttribute("TTCount", "addNew");

				} else {
					

					//NavigateNP: Navigate for Next or Previous 
					result = form.NavigationNP(session,"TROUBLE_TICKETS","TICKET_ID",TicketID,"LAST_MODIFIED_DATE",navAction);		
					SelectedIndex= Integer.parseInt(result[1]);
					TicketID=result[2];
					TroubleTickets TroubleTickets = (TroubleTickets) session.get(TroubleTickets.class, TicketID);

					model.addAttribute("TTCount", Integer.parseInt(result[0]));
					model.addAttribute("SelectedIndex", SelectedIndex);
					
					System.out.println("areaID "+TicketID);
					System.out.println("SelectedIndex "+SelectedIndex);
					
					query = session.createSQLQuery("SELECT ASSIGN_ID FROM TT_ASSIGN");

					if (query.list() != null) {

						model.addAttribute("listItemsNav", mapper.writeValueAsString(query.list()));

						query = session.createQuery("from TroubleTickets_Assign where ticketId like :param1");
						query.setParameter("param1", TicketID);

						model.addAttribute("ListAssign", mapper.writeValueAsString(query.list()));

					} else {
						model.addAttribute("listAssign", "addNew");
					}

					query = session.createSQLQuery("SELECT LFP_ID FROM TT_LINK_FAILURE_PLACES");

					if ((query.list()) != null) {

						query = session.createSQLQuery("SELECT LFP_ID FROM TT_LINK_FAILURE_PLACES");
						model.addAttribute("listItemsNav", mapper.writeValueAsString(query.list()));

						query = session.createQuery("from LinkFailurePlaces where ticketId like :param1");
						query.setParameter("param1", TicketID);

						model.addAttribute("ListLFP", mapper.writeValueAsString(query.list()));

					} else {
						model.addAttribute("listLFP", "addNew");
					}

					query = session.createSQLQuery("SELECT ID FROM TT_DEVICES_CAUSED_PROBLEM");

					if (query.list() != null) {

						model.addAttribute("listItemsNav", mapper.writeValueAsString(query.list()));
						model.addAttribute("listDCPSize", mapper.writeValueAsString((query.list()).size()));

						query = session.createQuery("from DevicesCausedProblem where ticketId like :param1");
						query.setParameter("param1", TicketID);

						model.addAttribute("ListDCP", mapper.writeValueAsString(query.list()));

					} else {
						model.addAttribute("ListDCP", "addNew");
					}

					query = session.createSQLQuery("SELECT ACTION_ID FROM TROUBLE_TICKETS_ACTIONS");

					if (query.list() != null) {

						model.addAttribute("listItemsNav", mapper.writeValueAsString(query.list()));
						model.addAttribute("listActionsSize", mapper.writeValueAsString((query.list()).size()));

						query = session.createQuery("from TroubleTickets_Action where ticketId like :param1");
						query.setParameter("param1", TicketID);

						model.addAttribute("ListAcArray", mapper.writeValueAsString(query.list()));

					} else {
						model.addAttribute("ListAcArray", "addNew");
					}

					query = session.createQuery("SELECT t.ticketId from TroubleTickets t ");

					if (query.list() != null) {

						model.addAttribute("listItemsNav", mapper.writeValueAsString(query.list()));

					} else {

						model.addAttribute("listItemsNav", mapper.writeValueAsString("noList"));
					}

					model.addAttribute("ListPRqItem", "NotNew");

					if (TroubleTickets.getTkcreationDate() == null) {
						model.addAttribute("TkcreationDate",
								formatter.format((new Timestamp(System.currentTimeMillis()))).toString());
					} else {
						model.addAttribute("TkcreationDate",
								formatter.format(TroubleTickets.getTkcreationDate()).toString());
					}

					model.addAttribute("TklastModifiedDate",
							formatter.format(TroubleTickets.getTklastModifiedDate()).toString());
					if (TroubleTickets.getTkregionId() == null && TroubleTickets.getTkregionName() == null
							&& TroubleTickets.getTkregionCode() == null) {

						region = "";

					} else {
						region = TroubleTickets.getTkregionId() + ":" + TroubleTickets.getTkregionName() + ":"
								+ TroubleTickets.getTkregionCode();
					}

					model.addAttribute("tkregion", region);
					model.addAttribute("tkresolutionDate", TroubleTickets.getTkresolutionDate());
					model.addAttribute("tkresolutionAction", TroubleTickets.getTkresolutionAction());
					model.addAttribute("tkresolutionDescription", TroubleTickets.getTkresolutionDescription());

					clientId = TroubleTickets.getTkclientId();
					clientName = TroubleTickets.getTkclientName();

					if (clientId == null && clientName == null) {

						client = "";

					} else {
						client = clientId + ":" + clientName;
					}
					model.addAttribute("tkID", TicketID);

					model.addAttribute("reasonForProblem", TroubleTickets.getReasonForProblemn());
					model.addAttribute("rcaDescription", TroubleTickets.getRcaDescription());

					model.addAttribute("tkdepartment", TroubleTickets.getTkdepartment());
					model.addAttribute("tksubject", TroubleTickets.getTksubject());
					model.addAttribute("tkdescription", TroubleTickets.getTkdescription());
					model.addAttribute("tkclient", client);
					model.addAttribute("tksiteId", TroubleTickets.getTksiteId());
					model.addAttribute("tksiteName", TroubleTickets.getTksiteName());
					model.addAttribute("tkservice", TroubleTickets.getTkservice());
					model.addAttribute("tkserviceIssue", TroubleTickets.getTkserviceIssue());
					model.addAttribute("tkPriority", TroubleTickets.getTkpriority());
					model.addAttribute("tkStatus", TroubleTickets.getTkstatus());

					if (TroubleTickets.getTkissueAppeared() == null) {
						model.addAttribute("tkissueAppeared",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());

					} else {
						model.addAttribute("tkissueAppeared",
								formatter.format(TroubleTickets.getTkissueAppeared()).toString());

					}

					model.addAttribute("TkcreationDate",
							formatter.format(TroubleTickets.getTkcreationDate()).toString());

				}

			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getMessage());
			} finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}

			}

		}
		return "TroubleTicketFormView";
	}

	/////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/TroubleTicketFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> TroubleTicketFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		String TicketID, tkRegionId, tkRegionName, tkRegionCode;
		String[] parts;
		TroubleTickets troubletickets = new TroubleTickets();

		DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp assigndate = null;

		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				/*
				 * ///////////////////////////////////////////////////////// SEND EMAIL BUTTON
				 * ////////////////////////////////////////////////////////// String email =
				 * request.getParameter("email"); String emailTo =
				 * request.getParameter("emailTo"); String password =
				 * request.getParameter("password"); String message =
				 * request.getParameter("message"); String subject =
				 * request.getParameter("subject"); String ccmail =
				 * request.getParameter("ccmail");
				 * 
				 * System.out.println("all email parameters  = " + email + " " + emailTo + " " +
				 * password + " " + message + " " + subject + " " + ccmail);
				 * 
				 * if (StringUtils.equalsIgnoreCase(request.getParameter("email"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("password"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("emailTo"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("message"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("subject"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), "")) {
				 * System.out.println("NO EMAIL TO SEND!");
				 * 
				 * } else if (StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), ""))
				 * {
				 * 
				 * 
				 * } else { JavaMailUtil.SendccEmails(email, password, emailTo, ccmail, subject,
				 * message); }
				 */
///////////////////////////////////////////// END OF SEND EMAIL BUTTON  //////////////////////////////////////////////////////////

				TicketID = request.getParameter("tkID");

				if ((request.getParameterValues("slctDelAssign[]")) != null) {
					query = session.createQuery("delete TroubleTickets_Assign t  where t.assignId IN (:param1)");
					query.setParameterList("param1", request.getParameterValues("slctDelAssign[]"));
					query.executeUpdate();

				}

				if (request.getParameterValues("slctDelDCP[]") != null) {
					query = session.createQuery("delete DevicesCausedProblem t  where t.Id IN (:param1)");
					query.setParameterList("param1", request.getParameterValues("slctDelDCP[]"));
					query.executeUpdate();

				}

				if (request.getParameterValues("slctDelActions[]") != null) {

					query = session.createQuery("delete TroubleTickets_Action t  where t.actionId IN (:param1)");
					query.setParameterList("param1", request.getParameterValues("slctDelActions[]"));
					query.executeUpdate();

				}

				if (request.getParameterValues("slctDelLFP[]") != null) {
					query = session.createQuery("delete LinkFailurePlaces t  where t.lfpId IN (:param1)");
					query.setParameterList("param1", request.getParameterValues("slctDelLFP[]"));
					query.executeUpdate();

				}

				if (StringUtils.equalsIgnoreCase(TicketID, "")) {
					synchronized (this) {						
						TicketID = "TT_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT TROUBLE_TICKET FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET TROUBLE_TICKET = TROUBLE_TICKET + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}
					//TicketID = "TT_" + year + "_" + appConfig.getSequenceNbr(35);

				}

				if (itemParameters.getDictParameterbarcode() != null) {

					for (int i = 0; i < itemParameters.getDictParameterbarcode().size(); i++) {
						TroubleTickets_Assign TroubleTickets_Assign = new TroubleTickets_Assign();

						if (itemParameters.getDictParameterbarcode().get(i).get("assignDate") != "") {
							assigndate = new Timestamp((formatter2
									.parse(itemParameters.getDictParameterbarcode().get(i).get("assignDate")))
											.getTime());
						}

						String TT_Assign = null;
						if (StringUtils.equalsIgnoreCase(
								itemParameters.getDictParameterbarcode().get(i).get("assignId"), "")) {
							 synchronized (this) {						
								 TT_Assign = "TT_Assign" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT TT_ASSIGN FROM SEQ_TABLE").uniqueResult().toString());	
									query = session.createSQLQuery("UPDATE SEQ_TABLE SET TT_ASSIGN = TT_ASSIGN + 1 ");
									query.executeUpdate();
									session.createSQLQuery("commit").executeUpdate();
									}
							TroubleTickets_Assign.setAssignId(TT_Assign);
							TroubleTickets_Assign
									.setCreateDate(new Timestamp(new Timestamp(System.currentTimeMillis()).getTime()));

						} else {
							TroubleTickets_Assign
									.setAssignId(itemParameters.getDictParameterbarcode().get(i).get("assignId"));
							TroubleTickets_Assign.setCreateDate(new Timestamp(
									formatter1.parse(itemParameters.getDictParameterbarcode().get(i).get("createDate"))
											.getTime()));
						}

						TroubleTickets_Assign.setLastModifiedDate(new Timestamp((new Date()).getTime()));
						TroubleTickets_Assign.setAssign_date(assigndate);
						TroubleTickets_Assign
								.setAssignTo(itemParameters.getDictParameterbarcode().get(i).get("assignTo"));
						TroubleTickets_Assign
								.setAssignBy(itemParameters.getDictParameterbarcode().get(i).get("assignBy"));
						TroubleTickets_Assign.setRequiredAction(
								itemParameters.getDictParameterbarcode().get(i).get("requiredAction"));
						TroubleTickets_Assign.setNote(itemParameters.getDictParameterbarcode().get(i).get("note"));
						TroubleTickets_Assign.setTicketId(TicketID);
						session.saveOrUpdate(TroubleTickets_Assign);
					}

				}

				if (itemParameters.getDictParameternode() != null) {
					String TT_LFP = null;
					for (int i = 0; i < itemParameters.getDictParameternode().size(); i++) {
						LinkFailurePlaces LinkFailurePlaces = new LinkFailurePlaces();
						
						if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameternode().get(i).get("lfpID"),
								"")) {
							 synchronized (this) {						
								 TT_LFP = "TT_LFP_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT TT_LINK_FAILURE_PLACES FROM SEQ_TABLE").uniqueResult().toString());	
									query = session.createSQLQuery("UPDATE SEQ_TABLE SET TT_LINK_FAILURE_PLACES = TT_LINK_FAILURE_PLACES + 1 ");
									query.executeUpdate();
									session.createSQLQuery("commit").executeUpdate();
									}
							LinkFailurePlaces.setLfpId(TT_LFP);
						} else {
							LinkFailurePlaces.setLfpId(itemParameters.getDictParameternode().get(i).get("lfpID"));
						}

						LinkFailurePlaces.setLinkId(itemParameters.getDictParameternode().get(i).get("linkID"));
						LinkFailurePlaces.setLinkName(itemParameters.getDictParameternode().get(i).get("linkName"));
						LinkFailurePlaces.setLongg(itemParameters.getDictParameternode().get(i).get("longg"));
						LinkFailurePlaces.setLatitude(itemParameters.getDictParameternode().get(i).get("latitude"));
						LinkFailurePlaces.setReason(itemParameters.getDictParameternode().get(i).get("Reason"));
						LinkFailurePlaces
								.setDescription(itemParameters.getDictParameternode().get(i).get("lfpdescription"));
						LinkFailurePlaces.setTicketId(TicketID);
						session.saveOrUpdate(LinkFailurePlaces);

					}

				}

				if (itemParameters.getDictParameterArea() != null) {
					String TT_DCP = null;
					for (int i = 0; i < itemParameters.getDictParameterArea().size(); i++) {
						DevicesCausedProblem DevicesCausedProblem = new DevicesCausedProblem();

						if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameterArea().get(i).get("ID"), "")) {
							 synchronized (this) {						
								 TT_DCP = "TT_DCP_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT TT_DEVICE_CAUSE_PROBLEM FROM SEQ_TABLE").uniqueResult().toString());	
									query = session.createSQLQuery("UPDATE SEQ_TABLE SET TT_DEVICE_CAUSE_PROBLEM = TT_DEVICE_CAUSE_PROBLEM + 1 ");
									query.executeUpdate();
									session.createSQLQuery("commit").executeUpdate();
									}
							DevicesCausedProblem.setId(TT_DCP);

						} else {
							DevicesCausedProblem.setId(itemParameters.getDictParameterArea().get(i).get("ID"));
						}
						DevicesCausedProblem.setType(itemParameters.getDictParameterArea().get(i).get("type"));
						DevicesCausedProblem.setSiteId(itemParameters.getDictParameterArea().get(i).get("siteID"));
						DevicesCausedProblem.setSiteName(itemParameters.getDictParameterArea().get(i).get("siteName"));
						DevicesCausedProblem.setNodeId(itemParameters.getDictParameterArea().get(i).get("nodeID"));
						DevicesCausedProblem.setNodeName(itemParameters.getDictParameterArea().get(i).get("nodeName"));
						DevicesCausedProblem.setCabinet(itemParameters.getDictParameterArea().get(i).get("cabinet"));
						DevicesCausedProblem.setSlot(itemParameters.getDictParameterArea().get(i).get("slot"));
						DevicesCausedProblem.setBoard(itemParameters.getDictParameterArea().get(i).get("board"));
						DevicesCausedProblem.setAntenna(itemParameters.getDictParameterArea().get(i).get("antenna"));
						DevicesCausedProblem.setVersion(itemParameters.getDictParameterArea().get(i).get("version"));
						DevicesCausedProblem.setNote(itemParameters.getDictParameterArea().get(i).get("note"));
						DevicesCausedProblem.setTicketId(TicketID);
						session.saveOrUpdate(DevicesCausedProblem);

					}
				}

				if (itemParameters.getDictParameter() != null) {
					String TT_Action = null;
					for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
						TroubleTickets_Action TroubleTickets_Action = new TroubleTickets_Action();

						if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get("actionID"),
								"")) {
							synchronized (this) {						
								TT_Action = "TT_A_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT TT_ACTIONS FROM SEQ_TABLE").uniqueResult().toString());	
									query = session.createSQLQuery("UPDATE SEQ_TABLE SET TT_ACTIONS = TT_ACTIONS + 1 ");
									query.executeUpdate();
									session.createSQLQuery("commit").executeUpdate();
									}
							TroubleTickets_Action.setActionId(TT_Action);
						} else {
							TroubleTickets_Action.setActionId(itemParameters.getDictParameter().get(i).get("actionID"));
						}

						if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get("creationDate"),
								"")) {
							TroubleTickets_Action.setTkacreationDate(
									new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));
						} else {
							TroubleTickets_Action.setTkacreationDate(new Timestamp(System.currentTimeMillis()));
						}
						TroubleTickets_Action.setEmployee(itemParameters.getDictParameter().get(i).get("employee"));
						TroubleTickets_Action.setTeam(itemParameters.getDictParameter().get(i).get("team"));
						TroubleTickets_Action.setStatus(itemParameters.getDictParameter().get(i).get("status"));
						TroubleTickets_Action
								.setDescription(itemParameters.getDictParameter().get(i).get("description"));
						TroubleTickets_Action.setAction(itemParameters.getDictParameter().get(i).get("action"));
						TroubleTickets_Action.setTicketId(TicketID);
						session.saveOrUpdate(TroubleTickets_Action);

					}
				}

				if ((request.getParameter("tkClient")).contains(":")) {
					String[] cli = (request.getParameter("tkClient")).split(":");
					troubletickets.setTkclientId(cli[0]);
					troubletickets.setTkclientName(cli[1]);
				} else {
					troubletickets.setTkclientId(request.getParameter("tkClient"));
				}

				tkRegionId = null;
				tkRegionName = null;
				tkRegionCode = null;

				parts = (request.getParameter("tkRegion")).split(":");
				if (parts.length == 3) {
					tkRegionId = parts[0];
					tkRegionName = parts[1];
					tkRegionCode = parts[2];

				} else if (parts.length == 2) {
					tkRegionId = parts[0];
					tkRegionName = parts[1];
					tkRegionCode = null;

				} else {
					tkRegionId = parts[0];
					tkRegionName = null;
					tkRegionCode = null;
				}

				troubletickets.setTkresolutionAction(request.getParameter("tkresolutionAction"));
				troubletickets.setTkresolutionDate(request.getParameter("tkresolutionDate"));
				troubletickets.setTkresolutionDescription(request.getParameter("tkresolutionDescription"));
				troubletickets.setRcaDescription(request.getParameter("rcaDescription"));
				troubletickets.setReasonForProblemn(request.getParameter("reasonForProblem"));
				troubletickets.setTicketId(TicketID);
				troubletickets.setTkcreationDate(
						new Timestamp(formatter1.parse(request.getParameter("TkCreationDate")).getTime()));
				troubletickets
						.setTklastModifiedDate(new Timestamp(new Timestamp(System.currentTimeMillis()).getTime()));
				troubletickets.setTkdepartment(request.getParameter("tkDepartment"));
				troubletickets.setTksubject(request.getParameter("tkSubject"));
				troubletickets.setTkdescription(request.getParameter("tkdescription"));
				troubletickets.setTksiteId(request.getParameter("tkSiteId"));
				troubletickets.setTkservice(request.getParameter("tkService"));
				troubletickets.setTkserviceIssue(request.getParameter("tkServiceIssue"));
				troubletickets.setTksiteName(request.getParameter("tksiteName"));
				troubletickets.setTkstatus(request.getParameter("tkStatus"));
				troubletickets.setTkpriority(request.getParameter("tkPriority"));
				troubletickets.setTkregionId(tkRegionId);
				troubletickets.setTkregionName(tkRegionName);
				troubletickets.setTkregionCode(tkRegionCode);
				troubletickets.setTkissueAppeared(
						new Timestamp(formatter1.parse(request.getParameter("tkIssueAppeared")).getTime()));
				session.saveOrUpdate(troubletickets);

				rtn.put("BassamTest", "SaveDone");
				rtn.put("tkID", TicketID);
				rtn.put("tklastModifiedDate", formatter1.format(new Timestamp(System.currentTimeMillis())).toString());

			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getMessage());
			} finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}

			}
		}
		return rtn;
	}
	
	@RequestMapping(value = "/GetAllTroubleTickets", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllTroubleTickets(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		String TTvalue = "%" + request.getParameter("TTvalue") + "%";

		session = almsessions.getSession();
		
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {	
				
			query = session.createQuery("SELECT ticketId, tkdepartment from TroubleTickets where ticketId like UPPER(:param1) OR UPPER(tkdepartment)like UPPER(:param1) ORDER BY tklastModifiedDate DESC");
			query.setString("param1", TTvalue);
			query.setFirstResult(0);
			query.setMaxResults(40);
			rtn.put("listtroubleTickets", query.list() );

		}
		catch(Exception e) {			
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
