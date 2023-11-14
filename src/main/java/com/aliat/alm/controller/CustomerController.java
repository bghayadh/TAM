package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
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
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

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
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.CustomerListView;

import com.aliat.alm.models.Customer;

@Controller
public class CustomerController {
	private static final Logger logger = Logger.getLogger(CustomerController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;

	@Autowired
	Permissions permissions;

	@Autowired
	Form form;

	@Autowired
	ALMSessions almsessions;

	@Autowired
	Notify notification;

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/CustomerListView", method = RequestMethod.GET)

	public String CustomerListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notification.headerNotifications(session, model);
			try {
				List<CustomerListView> listCustomer = new ArrayList<CustomerListView>();

				String str = "select CUSTOMER_ID as customerId,CUSTOMER_ID as customerIdd, CUSTOMER_NAME as customerName, MOBILE_NUMBER as mobile, CUSTOMER_ACRONYMS as customerAcronyms,TO_CHAR(CREATED_DATE,'YYYY-MM-DD HH24:MI:SS') as createdDate,"
						+ "TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate,"
						+ "STATUS as status" + " from CUSTOMER" + " order by LAST_MODIFIED_DATE DESC";

				query = session.createNativeQuery(str);
				listCustomer = ((NativeQuery<CustomerListView>) query).addScalar("customerId").addScalar("customerIdd")
						.addScalar("customerName").addScalar("mobile").addScalar("customerAcronyms")
						.addScalar("createdDate").addScalar("lastModifiedDate").addScalar("status")
						.setResultTransformer(Transformers.aliasToBean(CustomerListView.class)).list();
				System.out.println("listCustomer " + listCustomer.get(0).getCustomerId());
				model.addAttribute("ListGridTable", mapper.writeValueAsString(listCustomer));
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in CustomerListView due to \n " + exceptionAsString);
				logger.info("Error in CustomerListView due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
					session.getSessionFactory().close();
				}
			}
		}

		return "CustomerListView";
	}

	/*
	 * @RequestMapping(value = "/FilteredClientsListView", method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseBody public String FilteredClientsListView(Locale locale, Model
	 * model, HttpServletRequest request, HttpServletResponse response) {
	 * 
	 * if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	 * 
	 * return "redirect:/"; } String result = null; session =
	 * almsessions.getSession(); if (session != null && session.isOpen()) {
	 * 
	 * tx = session.beginTransaction(); notification.headerNotifications(session,
	 * model);
	 * 
	 * try {
	 * 
	 * String startdate, enddate, mobileNumber, Fname, Lname, agentName,
	 * agentNumber, status, regStatus, TkashStatus,region,area; startdate =
	 * request.getParameter("startDate"); enddate = request.getParameter("endDate");
	 * mobileNumber = request.getParameter("mobileNumber"); Fname =
	 * request.getParameter("Fname"); Lname = request.getParameter("Lname");
	 * agentName = request.getParameter("agentName"); agentNumber =
	 * request.getParameter("agentNumber"); status = request.getParameter("status");
	 * regStatus = request.getParameter("regStatus"); TkashStatus =
	 * request.getParameter("TkashStatus"); region = request.getParameter("region");
	 * area = request.getParameter("area");
	 * 
	 * List<ClientsListView> listClients = new ArrayList<ClientsListView>();
	 * 
	 * String str =
	 * "select c.CLIENT_ID as clientId, c.MOBILE_NUMBER as mobile, c.FIRST_NAME as firstName, c.LAST_NAME as lastName,c.CLIENT_ID_NUMBER as clientIdNumber,TO_CHAR(c.CREATED_DATE,'YYYY-MM-DD HH24:MI:SS') as createdDate,"
	 * +
	 * "TO_CHAR(c.LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate,"
	 * + "nvl(c.AGENT_NUMBER,'-') as agentNumber," + "c.Status as status," +
	 * "nvl(c.registration_status,'Not Registered') as regStatus,decode(c.tkash_registration_status,'0','T-Kash Not Registered','Success','Success','Failed','Failed') as tkashregstatus,"
	 * +
	 * "nvl((select a.full_name from agent a where c.agent_number = a.msisdn),'-') as agentFullName"
	 * + " from CLIENTS c"; if (startdate != null && enddate != null) { str = str +
	 * " where c.CREATED_DATE between TO_DATE('" + startdate +
	 * "','YYYY-MM-DD') and TO_DATE('" + enddate + "','YYYY-MM-DD')"; }
	 * 
	 * if (Fname != null && !Fname.equalsIgnoreCase("")) {
	 * 
	 * str = str + " and upper(c.FIRST_NAME) LIKE upper('%" + Fname + "%')"; }
	 * 
	 * if (Lname != null && !Lname.equalsIgnoreCase("")) {
	 * 
	 * str = str + " and upper(c.LAST_NAME) LIKE upper('%" + Lname + "%')"; }
	 * 
	 * if (mobileNumber != null && !mobileNumber.equalsIgnoreCase("")) {
	 * 
	 * str = str + " and c.MOBILE_NUMBER LIKE '%" + mobileNumber + "%'"; } if
	 * (agentNumber != null && !agentNumber.equalsIgnoreCase("")) {
	 * 
	 * str = str + " and c.AGENT_NUMBER LIKE '%" + agentNumber + "%'"; }
	 * 
	 * if (status != null && !status.equalsIgnoreCase("")) {
	 * 
	 * str = str + " and upper(c.STATUS) LIKE upper('%" + status + "%')"; }
	 * 
	 * if (region != null && !region.equalsIgnoreCase("")) {
	 * 
	 * str = str + " and upper(c.REGION_ID) LIKE upper('%" + region +
	 * "%') or upper(c.REGION_NAME) LIKE upper('%" + region + "%')"; } if (area !=
	 * null && !area.equalsIgnoreCase("")) {
	 * 
	 * str = str + " and upper(c.AREA_ID) LIKE upper('%" + area +
	 * "%') or upper(c.AREA_NAME) LIKE upper('%" + area + "%')"; } if (regStatus !=
	 * null && !regStatus.equalsIgnoreCase("")) {
	 * 
	 * if (regStatus.contains("not") || regStatus.contains("Not")) {
	 * 
	 * str = str + " and c.REGISTRATION_STATUS IS null"; } else { str = str +
	 * " and upper(c.REGISTRATION_STATUS) LIKE upper('%" + regStatus + "%')"; } }
	 * 
	 * if (TkashStatus != null && !TkashStatus.equalsIgnoreCase("")) {
	 * 
	 * if (TkashStatus.contains("not") || TkashStatus.contains("Not")) { TkashStatus
	 * = "0"; } str = str + " and upper(c.tkash_registration_status) LIKE upper('%"
	 * + TkashStatus + "%')"; }
	 * 
	 * str = str + " order by c.LAST_MODIFIED_DATE DESC";
	 * 
	 * query = session.createSQLQuery(str); listClients = ((SQLQuery)
	 * query).addScalar("clientId").addScalar("mobile").addScalar("firstName")
	 * .addScalar("lastName").addScalar("clientIdNumber").addScalar("createdDate").
	 * addScalar("lastModifiedDate")
	 * .addScalar("agentNumber").addScalar("agentFullName").addScalar("status").
	 * addScalar("regStatus") .addScalar("tkashregstatus")
	 * .setResultTransformer(Transformers.aliasToBean(ClientsListView.class)).list()
	 * ;
	 * 
	 * result = mapper.writeValueAsString(listClients);
	 * System.out.println("Filtered Array: " + result); } catch (Exception e) { sw =
	 * new StringWriter(); e.printStackTrace(new PrintWriter(sw)); exceptionAsString
	 * = sw.toString(); logger.finest("Error in FilteredClientsListView due to \n "+
	 * exceptionAsString);
	 * logger.info("Error in FilteredClientsListView due to \n "+
	 * exceptionAsString); } finally { if (session != null && session.isOpen()) {
	 * tx.commit(); session.close(); session.getSessionFactory().close(); } } }
	 * 
	 * return result; }
	 */

	@RequestMapping(value = "/CustomerFormView", method = RequestMethod.GET)
	public String CustomerFormView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		String customerId = request.getParameter("customerID"), navAction = "2";
		Customer customer = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String result[] = new String[4];
		int SelectedIndex = 0;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notification.headerNotifications(session, model);
			try {
				navAction = request.getParameter("NavAction");
				if (StringUtils.equalsIgnoreCase(customerId, null)) {
					model.addAttribute("ListCustomer", "addNew");
					model.addAttribute("creationDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("lastModifiedDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					return "CustomerFormView";

				} else {
					result = form.NavigationNP(session, "CUSTOMER", "CUSTOMER_ID", customerId, "LAST_MODIFIED_DATE",
							navAction);
					SelectedIndex = Integer.parseInt(result[1]);
					customerId = result[2];
					customer = (Customer) session.get(Customer.class, customerId);
					model.addAttribute("customerID", customer.getCustomerId());
					model.addAttribute("creationDate", formatter.format(customer.getCreatedDate()).toString());
					model.addAttribute("lastModifiedDate", formatter.format(customer.getLastModifiedDate()).toString());
					model.addAttribute("customerID", customer.getCustomerId());
					model.addAttribute("customerName", customer.getCustomerName());
					model.addAttribute("customerAcronyms", customer.getCustomerAcronyms());
					model.addAttribute("mobile", customer.getMobile());
					model.addAttribute("custstat", customer.getStatus());
					model.addAttribute("lat", customer.getLattitude());
					model.addAttribute("lng", customer.getLongitude());
					model.addAttribute("SelectedIndex", SelectedIndex);
					model.addAttribute("CustomerCount", Integer.parseInt(result[0]));

					model.addAttribute("refCustId", customer.getRefCustId());
					model.addAttribute("telNnumber", customer.getTelNumber());
					model.addAttribute("customerType", customer.getCustomerType());
					model.addAttribute("customerCategory", customer.getCustomerCategory());
					model.addAttribute("address", customer.getAddress());
					model.addAttribute("locationId", customer.getLocationId());
					model.addAttribute("city", customer.getCity());
					model.addAttribute("regionId", customer.getRegionId());
					model.addAttribute("regionName", customer.getRegionName());
					model.addAttribute("areaId", customer.getAreaId());
					model.addAttribute("areaName", customer.getAreaName());
					model.addAttribute("postalAddress", customer.getPostalAddress());
					model.addAttribute("nationality", customer.getNationality());
					model.addAttribute("email", customer.getEmail());
					model.addAttribute("website", customer.getWebsite());
					session.clear();
					tx.commit();
				}
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in CustomerFormView due to \n " + exceptionAsString);
				logger.info("Error in CustomerFormView due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return "CustomerFormView";
	}

	@RequestMapping(value = "/CustomerFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CustomerFormSave(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		Customer Customer = new Customer();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Calendar calendar = new GregorianCalendar();
		String customerID;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				customerID = request.getParameter("customerID");
				System.out.println(customerID);
				if (StringUtils.equalsIgnoreCase(customerID, "")) {

					synchronized (this) {
						// clientID = "CLT_" + calendar.get(Calendar.YEAR) + "_" +
						// appConfig.getSequenceNbr(34);
						customerID = "CUST_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(
								session.createNativeQuery("SELECT CUSTOMER FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET CUSTOMER = CUSTOMER + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						session.flush();
						session.clear();
					}

					Customer.setCustomerId(customerID);
					Customer.setCreatedDate(
							new Timestamp(formatter.parse(request.getParameter("createdate")).getTime()));
					Customer.setLastModifiedDate(new Timestamp(new Timestamp(System.currentTimeMillis()).getTime()));
					Customer.setCustomerName(request.getParameter("CustomertName"));
					Customer.setCustomerAcronyms(request.getParameter("AcronymsName"));
					Customer.setRefCustId(request.getParameter("custRefId"));
					Customer.setMobile(request.getParameter("Mobile"));
					Customer.setStatus(request.getParameter("status"));
					Customer.setAddress(request.getParameter("Address"));
					Customer.setLocationId(request.getParameter("LocationId"));
					Customer.setEmail(request.getParameter("email"));
					Customer.setRegionId(request.getParameter("RegionId"));
					Customer.setRegionName(request.getParameter("RegionName"));
					Customer.setAreaId(request.getParameter("AreaId"));
					Customer.setAreaName(request.getParameter("AreaName"));
					Customer.setLattitude(request.getParameter("Lat"));
					Customer.setLongitude(request.getParameter("Lng"));
					Customer.setCity(request.getParameter("City"));
					Customer.setPostalAddress(request.getParameter("PostalAddr"));
					Customer.setNationality(request.getParameter("Nationality"));
					Customer.setWebsite(request.getParameter("Website"));
					Customer.setCustomerCategory(request.getParameter("CustomerCategory"));
					Customer.setCustomerType(request.getParameter("CustomerType"));
					Customer.setTelNumber(request.getParameter("TelNumber"));
					session.saveOrUpdate(Customer);
				} else {
					query = session.createNativeQuery("Update CUSTOMER SET " + "CUSTOMER_NAME='"
							+ request.getParameter("CustomertName") + "'," + " CUSTOMER_ACRONYMS='"
							+ request.getParameter("AcronymsName") + "'," + " REF_CUST_ID='"
							+ request.getParameter("custRefId") + "'," + " MOBILE_NUMBER='"
							+ request.getParameter("Mobile") + "'," + " STATUS='" + request.getParameter("status")
							+ "'," + " ADDRESS='" + request.getParameter("Address") + "'," + " LOCATION_ID='"
							+ request.getParameter("LocationId") + "'," + " LAST_MODIFIED_DATE=SYSDATE," + " EMAIL='"
							+ request.getParameter("email") + "'," + " REGION_ID='" + request.getParameter("RegionId")
							+ "'," + " REGION_NAME='" + request.getParameter("RegionName") + "'," + " AREA_ID='"
							+ request.getParameter("AreaId") + "'," + " AREA_NAME='" + request.getParameter("AreaName")
							+ "'," + " LATITUDE='" + request.getParameter("Lat") + "'," + " LONGITUDE='"
							+ request.getParameter("Lng") + "'," + " CITY='" + request.getParameter("City") + "',"
							+ " POSTAL_ADDRESS='" + request.getParameter("PostalAddr") + "'," + " NATIONALITY='"
							+ request.getParameter("Nationality") + "'," + " WEBSITE='"
							+ request.getParameter("Website") + "'," + " CUSTOMER_CATEGORY='"
							+ request.getParameter("CustomerCategory") + "'," + " CUSTOMER_TYPE='"
							+ request.getParameter("CustomerType") + "'," + "TEL_NUMBER='"
							+ request.getParameter("TelNumber") + "'" + " WHERE CUSTOMER_ID='" + customerID + "'");
					query.executeUpdate();
				}

				rtn.put("customerID", customerID);
				rtn.put("lstmodifdate", formatter.format(new Timestamp(System.currentTimeMillis())).toString());
				session.flush();
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in CustomerFormSave due to \n " + exceptionAsString);
				logger.info("Error in CustomerFormSave due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CustomerDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CustomerDelete(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String idList;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				idList = request.getParameter("customerID");
				query = session.createNativeQuery("delete CUSTOMER  where CUSTOMER_ID ='" + idList + "'");
				query.executeUpdate();
				rtn.put("result", "Succeeded");
				session.flush();
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in CustomerDelete due to \n " + exceptionAsString);
				logger.info("Error in CustomerDelete due to \n " + exceptionAsString);
				rtn.put("result", "Failed");
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;

	}

	@RequestMapping(value = "/CustomerListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CustomerListViewDelete(Locale locale, Model model, HttpServletRequest request,
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

				idList = request.getParameterValues("customerID[]");
				for (int i = 0; i < idList.length; i++) {
					// Get Customer_ID from the listview form
					String idForm = idList[i];

					query = session.createNativeQuery("delete CUSTOMER  where CUSTOMER_ID ='" + idForm + "'");
					query.executeUpdate();
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in CustomerListViewDelete due to \n " + exceptionAsString);
				logger.info("Error in CustomerListViewDelete due to \n " + exceptionAsString);
			} finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("could not connect to DB in CustomerListViewDelete method");
			logger.info("could not connect to DB in CustomerListViewDelete method ");
		}
		return rtn;
	}

///////////////

	@RequestMapping(value = "/GetAllCustomer", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllCustomer(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			rtn.put("Login", "redirect:/");
			return rtn;
		}

		// String itemdtl = "%" + request.getParameter("client") + "%";
		System.out.println("Passing from GetAllCustomer where client is ");

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createQuery(
						"SELECT customerId,customerName,mobile from Customer where customerId like UPPER(:param1) OR UPPER(customerName)like UPPER(:param1) or mobile like UPPER(:param1) ORDER BY lastModifiedDate DESC");
				query.setParameter("param1", "%" + request.getParameter("customer") + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("ListCustomer", query.list());
				System.out.println("ListCustomer is " + mapper.writeValueAsString(query.list()));

				session.clear();
				tx.commit();

			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GetAllCustomer due to \n " + exceptionAsString);
				logger.info("Error in GetAllCustomer due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/GetAllNetworkCustomer", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllNetworkCustomer(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		session = almsessions.getSession();
		String search = request.getParameter("search");
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createNativeQuery(
						"SELECT CUSTOMER_ID,CUSTOMER_NAME,MOBILE_NUMBER,CITY,LONGITUDE,LATITUDE,LOCATION_ID FROM CUSTOMER WHERE UPPER(CUSTOMER_ID) LIKE UPPER(:param) OR UPPER(CUSTOMER_NAME) LIKE UPPER(:param) OR UPPER(MOBILE_NUMBER) LIKE UPPER(:param) OR UPPER(LOCATION_ID) LIKE UPPER(:param) ");
				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("globalList", query.list());
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GetAllNetworkCustomer due to \n " + exceptionAsString);
				logger.info("Error in GetAllNetworkCustomer due to \n " + exceptionAsString);
				rtn.put("searchResult", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return rtn;
	}
}
