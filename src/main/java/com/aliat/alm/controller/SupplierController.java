package com.aliat.alm.controller;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
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

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.Item;
import com.aliat.alm.models.Supplier;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SupplierController {

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);
	@SuppressWarnings("rawtypes")
	private static Query query = null;

	@Autowired
	ALMSessions almsessions;

	@Autowired
	Notify notifications;

	@Autowired
	Form form;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SupplierListView", method = RequestMethod.GET)
	public String SupplierListView(Model model, HttpServletRequest request, HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {

			List<Item> listItem = new ArrayList<Item>();

			session = almsessions.getSession();
			notifications.headerNotifications(session, model);

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					listItem = session.createQuery(
							"select 1,t.supplierID, t.supplierName, t.supplierCategory,t.sCountry,t.supplierAddress1 from Supplier t order by t.slastModifieddate DESC")
							.list();

					model.addAttribute("ListGridTable", mapper.writeValueAsString(listItem));
				} catch (Exception e) {
					model.addAttribute("ListGridTable", "");

					logger.info("Error in retreiving the data for Supplier list view with a message of :" + e);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return "SupplierListView";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredSupplierListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredSupplierListView(Locale locale, Model model, HttpServletRequest request,
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

				String startDate, endDate, Supplierid, Suppliername;
				startDate = request.getParameter("startDate");
				endDate = request.getParameter("endDate");
				Supplierid = request.getParameter("Supplierid");
				Suppliername = request.getParameter("Suppliername");

				System.out.println(startDate + " " + endDate + " " + Supplierid + " " + Suppliername);
				List<String> listsupplier = new ArrayList<String>();
				/*
				 * String str =
				 * "select 1 as chkBox, PRQ_ID as ID, SUPPLIER as supplier, TOTAL_AMOUNT as TotalAmount,"
				 * + " TOTAL_QTY as TotalQty, WAREHOUSE as WareHouse from PURCHASE_REQUEST";
				 */
				String str = "select 1 as chkBox, SUPPLIER_ID as supplierID, SUPPLIER_NAME as supplierName, SUPPLIER_CATEGORY as supplierCategory,"
						+ " COUNTRY as sCountry, ADDRESS_1 as supplierAddress1 from SUPPLIER  ";
				if (startDate != null && endDate != null) {
					str = str + " where CREATION_DATE between TO_DATE('" + startDate + "','YYYY-MM-DD') and TO_DATE('"
							+ endDate + "','YYYY-MM-DD')";
				}
				if (Suppliername != null && !Suppliername.equalsIgnoreCase("")) {

					str = str + " and upper(SUPPLIER_NAME) LIKE upper('%" + Suppliername + "%')";
				}
				if (Supplierid != null && !Supplierid.equalsIgnoreCase("")) {

					str = str + " and SUPPLIER_ID LIKE '%" + Supplierid + "%'";
				}

				// String str="select 1,t.ID, t.supplier, t.TotalAmount,t.TotalQty,t.WareHouse
				// from PurchaseRequest t";

				query = session.createNativeQuery(str);

				/*
				 * listPReq = ((SQLQuery) query).addScalar("chkBox",new
				 * IntegerType()).addScalar("ID").addScalar("supplier").addScalar("TotalAmount",
				 * new FloatType()) .addScalar("TotalQty", new
				 * FloatType()).addScalar("WareHouse")
				 * .setResultTransformer(Transformers.aliasToBean(PurchaseRequestListView.class)
				 * ).list();
				 */

				listsupplier = query.list();

				// rtn.put("listPReq", listPReq);
				rtn.put("listsupplier", listsupplier);
				System.out.println("Filtered Array: " + mapper.writeValueAsString(listsupplier));
			} catch (Exception e) {
				logger.info("Error in showing the filtered supplier request list view with a message :" + e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}

		return rtn;
	}

	@RequestMapping(value = "/SupplierFormView", method = RequestMethod.GET)
	public String SupplierFormView(Model model, HttpServletRequest request, HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {

			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			String SupplierID = request.getParameter("supplierID"), navAction = "2";
			Supplier supp;
			session = almsessions.getSession();
			notifications.headerNotifications(session, model);
			String result[] = new String[4];
			int SelectedIndex = 0;

			if (session != null && session.isOpen()) {

				tx = session.beginTransaction();
				try {

					navAction = request.getParameter("NavAction");

					// to open supplier when click on ADD from supplier List
					if (SupplierID == null) {
						model.addAttribute("screationDate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());
						model.addAttribute("slastModifieddate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());
						model.addAttribute("ListPRqItem", "addNew");
						model.addAttribute("SelectedIndex", "addNew");
						model.addAttribute("supplierCount", "addNew");
						model.addAttribute("docStatus", "addNew");

						return "SupplierFormView";
					} else {

						result = form.NavigationNP(session, "SUPPLIER", "SUPPLIER_ID", SupplierID, "LAST_MODIFIED_DATE",
								navAction);

						SelectedIndex = Integer.parseInt(result[1]);
						SupplierID = result[2];

						model.addAttribute("SelectedIndex", SelectedIndex);
						model.addAttribute("supplierCount", Integer.parseInt(result[0]));
						supp = (Supplier) session.get(Supplier.class, SupplierID);

						model.addAttribute("supplierID", supp.getSupplierID());
						model.addAttribute("screationDate", formatter.format(supp.getScreationDate()).toString());
						model.addAttribute("slastModifieddate",
								formatter.format(supp.getSlastModifieddate()).toString());
						model.addAttribute("supplierName", supp.getSupplierName());
						model.addAttribute("supplierDescription", supp.getSupplierDescription());
						model.addAttribute("supplierCategory", supp.getSupplierCategory());
						model.addAttribute("svendorNb", supp.getVendorNb());
						model.addAttribute("sWebsite", supp.getsWebsite());
						model.addAttribute("sCountry", supp.getsCountry());
						model.addAttribute("supplierTaxid", supp.getSupplierTaxid());
						model.addAttribute("supplierCreditlimit", supp.getSupplierCreditlimit());
						model.addAttribute("supplierAddress1", supp.getSupplierAddress1());
						model.addAttribute("supplierAddress2", supp.getSupplierAddress2());
						model.addAttribute("sPhone", supp.getsPhone());
						model.addAttribute("sMobile", supp.getsMobile());
						model.addAttribute("sEmail", supp.getsEmail());
						model.addAttribute("sContactperson", supp.getsContactperson());

						if (supp.getsDisabled() == '1') {
							model.addAttribute("sDisabled", "checked");
						} else {
							model.addAttribute("sDisabled", "");
						}

					}
				} catch (Exception e) {
					model.addAttribute("supplierID", "");
					model.addAttribute("screationDate", "");
					model.addAttribute("slastModifieddate", "");
					model.addAttribute("supplierName", "");
					model.addAttribute("supplierDescription", "");
					model.addAttribute("supplierCategory", "");
					model.addAttribute("svendorNb", "");
					model.addAttribute("sWebsite", "");
					model.addAttribute("sCountry", "");
					model.addAttribute("supplierTaxid", "");
					model.addAttribute("supplierCreditlimit", "");
					model.addAttribute("supplierAddress1", "");
					model.addAttribute("supplierAddress2", "");
					model.addAttribute("sPhone", "");
					model.addAttribute("sMobile", "");
					model.addAttribute("sEmail", "");
					model.addAttribute("sContactperson", "");
					logger.info("Error in retreiving the data for Supplier form view with a message :" + e);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}

				}
			}
			return "SupplierFormView";
		}
	}

	@RequestMapping(value = "/SupplierFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SupplierFormSave(Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);
			String SupplierID = request.getParameter("supplierID");
			Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			String createdDate = request.getParameter("screationDate");

			session = almsessions.getSession();
			if (session != null && session.isOpen()) {

				tx = session.beginTransaction();
				try {

					Supplier supp = new Supplier();
					Timestamp CreationDate = new Timestamp(formatter.parse(createdDate).getTime());

					// Date date2 = null;
					///////////////////////////////////////////////////////// SEND EMAIL BUTTON
					////////////////////////////////////////////////////////////
					// String email=request.getParameter("email");
					// String emailTo=request.getParameter("emailTo");
					// String password=request.getParameter("password");
					// String message=request.getParameter("message");
					// String subject=request.getParameter("subject");
					// String ccmail=request.getParameter("ccmail");
					//
					//
					//
					// if(StringUtils.equalsIgnoreCase(request.getParameter("email"), "") &&
					// StringUtils.equalsIgnoreCase(request.getParameter("password"), "") &&
					// StringUtils.equalsIgnoreCase(request.getParameter("emailTo"), "") &&
					// StringUtils.equalsIgnoreCase(request.getParameter("message"), "") &&
					// StringUtils.equalsIgnoreCase(request.getParameter("subject"), "") &&
					// StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), "") )
					// {
					// System.out.println("NO EMAIL TO SEND!");
					//
					// }
					// else if(StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), ""))
					// {
					//
					// JavaMailUtil.SendEmails(email,password,emailTo,subject,message);
					//
					// }
					// else {
					// JavaMailUtil.SendccEmails(email,password,emailTo,ccmail,subject,message);
					// }

					if (StringUtils.equalsIgnoreCase(SupplierID, "")) {
						synchronized (this) {
							SupplierID = "SUP_" + year + "_" + Integer.parseInt(session
									.createNativeQuery("SELECT SUPPLIER FROM SEQ_TABLE").uniqueResult().toString());
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET SUPPLIER = SUPPLIER + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						// SupplierID = "SUP_" + year + "_" + appConfig.getSequenceNbr(11);
					}

					supp.setSupplierID(SupplierID);
					supp.setScreationDate(CreationDate);
					supp.setSlastModifieddate(lastModifiedDate);
					supp.setSupplierName(request.getParameter("supplierName"));
					supp.setVendorNb(request.getParameter("svendorNb"));
					supp.setSupplierDescription(request.getParameter("supplierDescription"));
					supp.setSupplierCategory(request.getParameter("supplierCategory"));
					supp.setsWebsite(request.getParameter("sWebsite"));
					supp.setsCountry(request.getParameter("sCountry"));
					supp.setSupplierTaxid(request.getParameter("supplierTaxid"));
					supp.setSupplierCreditlimit(Float.parseFloat(request.getParameter("supplierCreditlimit")));
					supp.setSupplierAddress1(request.getParameter("supplierAddress1"));
					supp.setSupplierAddress2(request.getParameter("supplierAddress2"));
					supp.setsPhone(request.getParameter("sPhone"));
					supp.setsMobile(request.getParameter("sMobile"));
					supp.setsEmail(request.getParameter("sEmail"));
					supp.setsContactperson(request.getParameter("sContactperson"));
					supp.setsDisabled(request.getParameter("sDisabled").charAt(0));
					session.saveOrUpdate(supp);
					rtn.put("supplierID", SupplierID);
					rtn.put("slastModifieddate", formatter.format(lastModifiedDate).toString());

				} catch (Exception e) {
					rtn.put("supplierID", "");
					rtn.put("slastModifieddate", "");
					logger.info("Error in saving data for Supplier form view with a message :" + e);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}
	}

	@RequestMapping(value = "/SupplierDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SupplierDelete(Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {

			session = almsessions.getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
					String[] idList = request.getParameterValues("ID[]");
					query = session.createQuery("delete Supplier t  where t.supplierID IN (:param1)");
					query.setParameterList("param1", idList);
					query.executeUpdate();
				} catch (Exception e) {
					logger.info("Error in deleting data for Supplier with a message :" + e);
					e.printStackTrace();
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}

	}

	// Used in the supplier formview to get the supplier Category
	@RequestMapping(value = "/GetAllSuppliers", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllSuppliers(Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String SuppCategory = "%" + request.getParameter("supplierCategory") + "%";
			session = almsessions.getSession();
			if (session != null && session.isOpen()) {

				tx = session.beginTransaction();
				try {
					query = session.createQuery(
							"select supplierID, supplierName, supplierCreditlimit from Supplier where supplierID like :param1 or supplierName like :param1");
					query.setParameter("param1", SuppCategory);

					rtn.put("ListItemSupplier", query.list());
				} catch (Exception e) {
					rtn.put("ListItemSupplier", "");
					logger.info("Error in getting all Suppliers with a message :" + e);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}
	}

	// to get all the supplier based on therir supplier Names
	@RequestMapping(value = "/GetAllSupplier", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllSupplier(Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String SuppCategory = "%" + request.getParameter("supplierId") + "%";
			session = almsessions.getSession();
			if (session != null && session.isOpen()) {

				tx = session.beginTransaction();
				try {
					query = session.createQuery(
							"select supplierID, supplierName,supplierAddress1 from Supplier where supplierID like :param1 or supplierName like:param1 ");
					query.setParameter("param1", SuppCategory);
					query.setFirstResult(0);
					query.setMaxResults(40);
					rtn.put("ListGetSupplier", query.list());

				} catch (Exception e) {
					rtn.put("ListGetSupplier", "");
					logger.info("Error in getting all Suppliers with a message :" + e);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}
	}

	// Get ALL Supplier Names
	@RequestMapping(value = "/GetAllSupplierName", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllSupplierName(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String SuppName = "%" + request.getParameter("supplierName") + "%";

			session = almsessions.getSession();
			if (session != null && session.isOpen()) {

				tx = session.beginTransaction();
				try {

					query = session.createQuery(
							"select distinct (supplierName), supplierAddress1,supplierID from Supplier where supplierName like :param1 ORDER BY slastModifieddate DESC");
					query.setParameter("param1", SuppName);
					query.setFirstResult(0);
					query.setMaxResults(40);

					rtn.put("ListSupplierName", query.list());
				} catch (Exception e) {
					rtn.put("ListSupplierName", "");
					logger.info("Error in getting all Supplier Names with a message :" + e);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}
	}

	// Get The supp id to Get the info
	@RequestMapping(value = "/GetSuppID", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetSuppID(Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String SuppName = request.getParameter("SuppName");
			session = almsessions.getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
					query = session.createQuery("select  supplierID   from Supplier where supplierName =:param1 ");
					query.setParameter("param1", SuppName);
					query.setFirstResult(0);
					query.setMaxResults(40);
					rtn.put("ListSuppIds", query.list());
				} catch (Exception e) {
					rtn.put("ListSuppIds", "");
					logger.info("Error in getting all Supplier IDs with a message :" + e);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
			return rtn;
		}
	}
}