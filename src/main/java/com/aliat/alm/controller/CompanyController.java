package com.aliat.alm.controller;

import java.io.File;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
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
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.CompanyListView;
import com.aliat.alm.models.Company;


@Controller
public class CompanyController {

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	

	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;
	
	@Autowired
	Form form;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/CompanyListView", method = RequestMethod.GET)

	public String CompanyListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {
				List<CompanyListView> listCompanies = new ArrayList<CompanyListView>();

				String str = "select c.COMPANY_ID as companyId, c.MOBILE_NUMBER as mobile, c.FIRST_NAME as firstName, c.LAST_NAME as lastName,TO_CHAR(c.CREATED_DATE,'YYYY-MM-DD HH24:MI:SS') as createdDate,"
						+ "TO_CHAR(c.LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate,"
						
						+"c.Status as status,"+"nvl(c.registration_status,'Not Registered') as regStatus"+
						
						
						" from COMPANY c" + 
						" order by c.LAST_MODIFIED_DATE DESC";

				Query query = session.createSQLQuery(str);
				listCompanies = ((SQLQuery) query).addScalar("companyId").addScalar("mobile").addScalar("firstName")
						.addScalar("lastName").addScalar("createdDate").addScalar("lastModifiedDate")
						.addScalar("status").addScalar("regStatus")
						.setResultTransformer(Transformers.aliasToBean(CompanyListView.class)).list();

				model.addAttribute("ListGridTable", mapper.writeValueAsString(listCompanies));
				System.out.println(mapper.writeValueAsString(listCompanies));
			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getMessage());
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return "CompanyListView";
	}
	@RequestMapping(value = "/FilteredCompanyListView", method = RequestMethod.GET)
	@ResponseBody
	public String FilteredCompanyListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		
			return "redirect:/";
		}
		String result=null;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {
				
				 String startdate,enddate,mobileNumber,Fname,Lname,status,regStatus;
				 startdate=request.getParameter("startDate");
				 enddate=request.getParameter("endDate");
				 mobileNumber=request.getParameter("mobileNumber");
				 Fname=request.getParameter("Fname");
				 Lname=request.getParameter("Lname");
				
				 status=request.getParameter("status");
				 regStatus=request.getParameter("regStatus");
				 
				
				List<CompanyListView> listCompanies = new ArrayList<CompanyListView>();

				String str = "select c.COMPANY_ID as companyId, c.MOBILE_NUMBER as mobile, c.FIRST_NAME as firstName, c.LAST_NAME as lastName,TO_CHAR(c.CREATED_DATE,'YYYY-MM-DD HH24:MI:SS') as createdDate,"
						+ "TO_CHAR(c.LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate,"
						
						+"c.Status as status," 
						+ "nvl(c.registration_status,'Not Registered') as regStatus"+
						" from COMPANY c ";
				if(startdate != null  && enddate != null ) {
					str = str+" where c.CREATED_DATE between TO_DATE('"+startdate+"','YYYY-MM-DD') and TO_DATE('"+enddate+"','YYYY-MM-DD')";
				}
				
				if(Fname != null && !Fname.equalsIgnoreCase("")) {
					
					str = str +" and upper(c.FIRST_NAME) LIKE upper('%"+Fname+"%')";
				}
				
				if(Lname != null && !Lname.equalsIgnoreCase("")) {
					
					str = str +" and upper(c.LAST_NAME) LIKE upper('%"+Lname+"%')";
				}
				
				if(mobileNumber != null && !mobileNumber.equalsIgnoreCase("")) {
					
					str = str +" and c.MOBILE_NUMBER LIKE '%"+mobileNumber+"%'";
				}
				
				
				if(status != null && !status.equalsIgnoreCase("")) {
					
					str = str +" and upper(c.STATUS) LIKE upper('%"+status+"%')";
				}

				if(regStatus != null && !regStatus.equalsIgnoreCase("")) {
	
					if(regStatus.contains("not") || regStatus.contains("Not")) {
						
						str = str +" and c.REGISTRATION_STATUS IS null";
					}else {
						str = str +" and upper(c.REGISTRATION_STATUS) LIKE upper('%"+regStatus+"%')";
					}
				}

				
				
				str = str + " order by c.LAST_MODIFIED_DATE DESC";
				System.out.print(str);
				
				query = session.createSQLQuery(str);
				listCompanies = ((SQLQuery) query).addScalar("companyId").addScalar("mobile").addScalar("firstName")
						.addScalar("lastName").addScalar("createdDate").addScalar("lastModifiedDate")
					.addScalar("status").addScalar("regStatus")
						.setResultTransformer(Transformers.aliasToBean(CompanyListView.class)).list();

				result = mapper.writeValueAsString(listCompanies);
				System.out.println("Filtered Array: "+result);
			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getMessage());
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return result;
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/CompanyFormView", method = RequestMethod.GET)
	public String CompanyFormView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		String companyID = request.getParameter("clientID"), navAction = "2";
		Company  Companies = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String result[] = new String[4];
		int SelectedIndex = 0;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {
				navAction = request.getParameter("NavAction");
				if (StringUtils.equalsIgnoreCase(companyID, null)) {
					model.addAttribute("ListClient", "addNew");
					model.addAttribute("creationDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("lastModifiedDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
				
				
					model.addAttribute("SelectedIndex", "addNew");
					model.addAttribute("ClientsCount", "addNew");
					model.addAttribute("docStatus", "addNew");
					model.addAttribute("ordStatus", "addNew");
					model.addAttribute("regStatus","");
					model.addAttribute("regStatusLog", "addNew");
				
					model.addAttribute("tkashStatusLog", "addNew");
					System.out.println("121");
					return "CompanyFormView";
					
					
				} else {
					result = form.NavigationNP(session, "COMPANY", "COMPANY_ID", companyID, "LAST_MODIFIED_DATE",
							navAction);
					SelectedIndex = Integer.parseInt(result[1]);
					companyID = result[2];
					Companies = (Company) session.get(Company.class, companyID);
					model.addAttribute("clientID", Companies.getCompanyId());
					model.addAttribute("creationDate", formatter.format(Companies.getCreatedDate()).toString());
					model.addAttribute("lastModifiedDate", formatter.format(Companies.getLastModifiedDate()).toString());
					model.addAttribute("clientID", Companies.getCompanyId());
					model.addAttribute("dspName", Companies.getDisplayName());
					model.addAttribute("firstName", Companies.getFirstName());
					model.addAttribute("lastName", Companies.getLastName());
					model.addAttribute("mobile", Companies.getMobile());
					
					model.addAttribute("loc", Companies.getLocation());
			
					model.addAttribute("ordStatus", Companies.getStatus());
					System.out.println("ordStatus : "+ Companies.getStatus());
					
			
					
					model.addAttribute("SelectedIndex", SelectedIndex);
					model.addAttribute("ClientsCount", Integer.parseInt(result[0]));
					
					

					
					//get registration status
					query = session.createSQLQuery("SELECT nvl(REGISTRATION_STATUS,'Not Registered') from COMPANY where COMPANY_ID='"+companyID+"'");
					String regStatus =  mapper.writeValueAsString(query.list());	
					regStatus=regStatus.replace("[", "").replace("]", "");
					if(regStatus.contains("\"")) {
						regStatus=regStatus.replace("\"", "");
					}
					if(regStatus != null) {
						
						List<String> regStatusLog  = new ArrayList<String>();
						model.addAttribute("regStatus", regStatus);						
						regStatusLog = session.createSQLQuery("SELECT TO_CHAR(REGISTRATIONDATE,'YYYY-MM-DD HH24:MI:SS'),"
								+ "SIM_REG_STATUS,RESPONSE_CODE,RESPONSE_MESSAGE,AGENT_NUMBER,AGENT_NAME,MOBILE_NUMBER FROM SIMREG_LOG "
								+ "where CLIENT_ID='"+companyID+"' order by REGISTRATIONDATE DESC").list();
						
						System.out.println(mapper.writeValueAsString(regStatusLog));
						model.addAttribute("regStatusLog",mapper.writeValueAsString(regStatusLog));
					}else {
						model.addAttribute("regStatus", "");
						model.addAttribute("regStatusLog", "addNew");
					}
					
					
				
				}
			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getMessage());
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "CompanyFormView";
	}
	@RequestMapping(value = "/CompanyFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CompanyFormSave(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		Company Companies = new Company();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Calendar calendar = new GregorianCalendar();
		String companyID;

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();

			try {



				companyID = request.getParameter("clientID");

				if (StringUtils.equalsIgnoreCase(companyID, "")) {

					//companyID = "COMP_" + calendar.get(Calendar.YEAR) + "_" + appConfig.getSequenceNbr(79);
					synchronized (this) {						
						companyID = "COMP_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(session.createSQLQuery("SELECT COMPANY FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET COMPANY = COMPANY + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}
				}

				Companies.setCompanyId(companyID);
				Companies.setCreatedDate(new Timestamp(formatter.parse(request.getParameter("createdate")).getTime()));
				Companies.setLastModifiedDate(new Timestamp(new Timestamp(System.currentTimeMillis()).getTime()));
				Companies.setDisplayName(request.getParameter("DisplayName"));
				Companies.setFirstName(request.getParameter("FirstName"));
				Companies.setLastName(request.getParameter("LastName"));
				Companies.setMobile(request.getParameter("Mobile"));
			
				Companies.setStatus(request.getParameter("status"));
				Companies.setLocation(request.getParameter("Location"));
			

			
			
	

				session.saveOrUpdate(Companies);

				rtn.put("clientID", companyID);
				rtn.put("lstmodifdate", formatter.format(new Timestamp(System.currentTimeMillis())).toString());

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
	@RequestMapping(value = "/CompanyDelete", method = RequestMethod.GET)
	@ResponseBody
	public String CompanyDelete(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			return "redirect:/";
		}

		String rtn = "Succeeded";

		String idList;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				idList = request.getParameter("clientID");

				query = session.createSQLQuery("delete COMPANY  where COMPANY_ID ='" + idList + "'");
				query.executeUpdate();

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
	@RequestMapping(value = "/CompanyListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CompanyListViewDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String[] idList;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				idList = request.getParameterValues("clientID[]");
				for (int i = 0; i < idList.length; i++) {
					// Get AGENT_ID from the listview form
					String idForm = idList[i];

					query = session.createSQLQuery("delete COMPANY  where COMPANY_ID ='" + idForm + "'");
					query.executeUpdate();
				}
			} catch (Exception e) {
				System.out.println("Error in creating session with the ClientListViewDelete method " + e.getMessage());
				e.printStackTrace();
			} finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		} else {
			System.out.println("could not connect to DB in ClientListViewDelete method");
			
		}
		return rtn;
	}
	@RequestMapping(value = "/GetAllCompanies", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllCompanies(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			rtn.put("Login", "redirect:/");
			return rtn;
		}

		String itemdtl = "%" + request.getParameter("client") + "%";
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createQuery(
						"SELECT companyId, (FIRST_NAME || ' ' || LAST_NAME ) from Company where companyId like UPPER(:param1) OR UPPER(FIRST_NAME || ' ' || LAST_NAME)like UPPER(:param1) ORDER BY lastModifiedDate DESC");
				query.setString("param1", itemdtl);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("ListClient", query.list());

			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getMessage());
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