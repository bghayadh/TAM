package com.aliat.alm.controller;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.math.BigDecimal; // Add this import statement

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.FloatType;
import org.hibernate.type.StringType;
import org.json.simple.JSONArray;
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

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.CapitalInProgress;
import com.aliat.alm.models.PurchaseOrder;
import com.aliat.alm.models.PurchaseOrderBoq;
import com.aliat.alm.models.PurchaseOrderItem;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.models.PurchaseRequestBoq;
import com.aliat.alm.models.PurchaseRequestItem;
import com.aliat.alm.models.SerialNumber;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PurchaseController {

	

	@Autowired
	Notify notifications;

	@Autowired
	Permissions permissions;

	@Autowired
	Form form;

	private static final String PR_ITEMCODE = "prItemCode";
	private static final String PR_ITEMMODEL = "prItemModel";
	private static final String PR_ITEMPARTNO = "prItemPartNo";

	private static final String PR_ITEMBARCODE = "prBarCode";

	private static final String PR_QTY = "prQty";

	private static final String PR_RATE = "prRate";
	private static final String PR_DISCOUNT_AMMOUNT = "prDiscountAmount";
	private static final String PR_TAX = "prTax1";
	private static final String PR_NETRATE = "prNetRate";
	private static final String PR_TOTAL = "prTotal";
	private static final String PR_TOTAL_AT = "prTotalAt";
	private static final String PRQ_ITEM_ID = "prqItemId";
	private static final String PRQ_PO_STATUS = "prqPoStatus";
	/**
	 * po global fields to the dict array
	 */
	private static final String PO_ITEMCODE = "itemCode";
	private static final String PO_ITEMMODEL = "itemModel";
	private static final String PO_ITEMPARTNO = "itemPartNo";
	private static final String PO_QTY = "qty";
	private static final String PO_RATE = "rate";
	private static final String PO_DISCOUNT_AMMOUNT = "discountAmount";
	private static final String PO_TAX = "tax1";
	private static final String PO_NETRATE = "netRate";
	private static final String PO_TOTAL = "total";
	private static final String PO_TOTAL_AT = "totalAt";
	private static final String POR_ITEM_ID = "porItemId";
	private static final String PO_ITEM_STATUS = "poItemStatus";
	private static final String SERIAL_NUM = "serialNo";
	private static final String PO_ITEMBARCODE = "poBarCode";
//	private static final String PO_CAT1 = "poCat1";
//	private static final String PO_CAT2 = "poCat2";
//	private static final String PO_CAT3 = "poCat3";
//	private static final String PO_CAT4 = "poCat4";
//	private static final String PO_Seq = "poSequ";
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private String str = null;
	
	@SuppressWarnings("rawtypes")
	private static Query query = null;

	private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */

// Test

// Test from Bilal

// Test by Bassam

// Returned Test by Bilal

	@RequestMapping(value = "/Purchase", method = RequestMethod.GET)
	public String Purchasing(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			System.out.println("HashCode Purchase: "+AlmDbSession.getInstance().hashCode());
				//tx = session.beginTransaction();
			try {
				System.out.println("purchasessssss");
				notifications.headerNotifications(session, model);
				
					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
							"Purchase Request", "List");
				} catch (Exception e) {
					logger.info(
							"Error at Purchase Class and Purchase method while getting notifications and permissions with error message: "
									+ e.getMessage());
				} finally {
					if(session != null && session.isOpen()) {
						session.close();
					}
				}
			
			return "Purchase";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PurchaseReqListView", method = RequestMethod.GET)
	public String PurchaseReqListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			List<PurchaseRequest> listPurchaseRequest = new ArrayList<PurchaseRequest>();
			Integer readList;
			String returnValue = "";
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
					notifications.headerNotifications(session, model);
					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
							"Purchase Request", "List");
					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
							"Purchase Request", "Form");
					readList = (Integer) model.asMap().get("readList");
					listPurchaseRequest = session.createQuery(
							"select 1,t.ID,TO_CHAR(t.lastmodifiedDate, 'YYYY-MM-DD HH24:MI:SS') ,  t.supplierName,t.prStatus, t.TotalAmount,t.TotalQty,t.WareHouse,t.siteID , t.wareName from PurchaseRequest t  order by t.lastmodifiedDate DESC ")
							.list();
					if (readList == 1) {
						returnValue = "PurchaseReqListView";
						model.addAttribute("ListGridTable", mapper.writeValueAsString(listPurchaseRequest));
					} else if (readList == 0) {
						returnValue = "redirect:/Purchase";
						model.asMap().clear();
					}
				} catch (Exception e) {
					logger.info("Error on the level of purchase request listview, with the message :" + e.getMessage());
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						
					}
				}
			}
			return returnValue;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredPurchaseListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredPurchaseListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {
				String startdate, enddate, supplier, warehouse;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				supplier = request.getParameter("Supplier");
				warehouse = request.getParameter("Warehouse");
				/*
				 * System.out.println( startdate + " " + enddate + " " + supplier + " " +
				 * warehouse );
				 */
				List<String> listPReq = new ArrayList<String>();
				/*
				 * String str =
				 * "select 1 as chkBox, PRQ_ID as ID, SUPPLIER as supplier, TOTAL_AMOUNT as TotalAmount,"
				 * + " TOTAL_QTY as TotalQty, WAREHOUSE as WareHouse from PURCHASE_REQUEST";
				 */
				str = "select 1 as chkBox, PRQ_ID as ID,TO_CHAR(LAST_MODIFICATION_DATE,'YYYY-MM-DD HH24:MI:SS') as lastmodifiedDate ,  SUPPLIER_NAME as supplierName, TOTAL_AMOUNT as TotalAmount,"
						+ " TOTAL_QTY as TotalQty,WAREHOUSE as WareHouse, SITE_ID as siteID , WAREHOUSE_NAME as wareName from PURCHASE_REQUEST  ";

				// String str="select 1,t.ID, t.supplier, t.TotalAmount,t.TotalQty,t.WareHouse
				// from PurchaseRequest t";

				if (startdate != null && enddate != null) {
					str = str + " where CREATION_DATE between TO_DATE('" + startdate + "','YYYY-MM-DD') and TO_DATE('"
							+ enddate + "','YYYY-MM-DD')";
				}
				if (supplier != null && !supplier.equalsIgnoreCase("")) {
					str = str + " and (upper(SUPPLIER) LIKE upper('%" + supplier
							+ "%') or upper(SUPPLIER_NAME) LIKE upper('%" + supplier + "%') )";
				}

				if (warehouse != null && !warehouse.equalsIgnoreCase("")) {
					str = str + " and (upper(WAREHOUSE) LIKE upper('%" + warehouse
							+ "%')or upper(WAREHOUSE_NAME) LIKE upper('%" + warehouse
							+ "%') or upper(SITE_ID) LIKE upper('%" + warehouse + "%')  )";
				}
				query = session.createNativeQuery(str);

				/*
				 * listPReq = ((SQLQuery) query).addScalar("chkBox",new
				 * IntegerType()).addScalar("ID").addScalar("supplier").addScalar("TotalAmount",
				 * new FloatType()) .addScalar("TotalQty", new
				 * FloatType()).addScalar("WareHouse")
				 * .setResultTransformer(Transformers.aliasToBean(PurchaseRequestListView.class)
				 * ).list();
				 */

				listPReq = query.list();

				// rtn.put("listPReq", listPReq);
				rtn.put("listPReq", listPReq);
				// System.out.println("Filtered Array: " + mapper.writeValueAsString(listPReq));
			} catch (Exception e) {
				logger.info("Error in showing the filtered purchase request list view with a message :" + e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/FilteredPurchaseOrderListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredPurchaseOrderListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {

				String startdatee, enddatee, supplierr, warehousee;
				startdatee = request.getParameter("startDatee");
				enddatee = request.getParameter("endDatee");
				supplierr = request.getParameter("Supplierr");
				warehousee = request.getParameter("warehousee");

				/*
				 * String str =
				 * "select 1 as chkBox, PRQ_ID as ID, SUPPLIER as supplier, TOTAL_AMOUNT as TotalAmount,"
				 * + " TOTAL_QTY as TotalQty, WAREHOUSE as WareHouse from PURCHASE_REQUEST";
				 */
				str = "select 1 as chkBox, PO_ID as ID, TO_CHAR(LAST_MODIFICATION_DATE,'YYYY-MM-DD HH24:MI:SS') as lastmodifiedDate ,SUPPLIER_NAME as supplierName, TOTAL_AMOUNT as TotalAmount,"
						+ " TOTAL_QTY as TotalQty,WAREHOUSE as WareHouse, SITE_ID as siteID ,WAREHOUSE_NAME as wareName from PURCHASE_ORDER ";

				// String str="select 1,t.ID, t.supplier, t.TotalAmount,t.TotalQty,t.WareHouse
				// from PurchaseRequest t";
				if (startdatee != null && enddatee != null) {
					str = str + " where CREATION_DATE between TO_DATE('" + startdatee + "','YYYY-MM-DD') and TO_DATE('"
							+ enddatee + "','YYYY-MM-DD')";
				}
				if (supplierr != null && !supplierr.equalsIgnoreCase("")) {

					str = str + " and (upper(SUPPLIER) LIKE upper('%" + supplierr
							+ "%') or upper(SUPPLIER_NAME) LIKE upper('%" + supplierr + "%') )";
				}
				if (warehousee != null && !warehousee.equalsIgnoreCase("")) {

					str = str + " and (upper(WAREHOUSE) LIKE upper('%" + warehousee
							+ "%')or upper(WAREHOUSE_NAME) LIKE upper('%" + warehousee
							+ "%') or upper(SITE_ID) LIKE upper('%" + warehousee + "%')  )";
				}
				rtn.put("listPOrder", session.createNativeQuery(str).list());
			} catch (Exception e) {
				logger.info("Error in showing the filtered purchase order list view with a message :" + e);
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
	@RequestMapping(value = "/PendingPurchaseReqListView", method = RequestMethod.GET)
	public String PendingPurchaseReqListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			List<PurchaseRequest> listPurchaseRequest = new ArrayList<PurchaseRequest>();
			String returnValue = "";

			permissions.setPerms(model, permissions.getUserPerms(request), "Purchase Request", "List");
			permissions.setPerms(model, permissions.getUserPerms(request), "Purchase Request", "Form");
			Integer readList = (Integer) model.asMap().get("readList");
			session = AlmDbSession.getInstance().getSession();
			if (session.isOpen() && session != null) {
				tx = session.beginTransaction();
				try {
					listPurchaseRequest = session.createQuery(
							"select 1,t.ID, t.supplier, t.TotalAmount,t.TotalQty,t.WareHouse from PurchaseRequest t where t.prStatus != 'completed' and t.prStatus != 'closed' and t.prStatus != 'deactivated' and t.prStatus != 'cancelled' and t.prStatus != 'activated' or t.prStatus is null")
							.list();

					if (readList == 1) {
						returnValue = "PurchaseReqListView";
						model.addAttribute("ListGridTable", mapper.writeValueAsString(listPurchaseRequest));
					} else if (readList == 0) {
						returnValue = "redirect:/Purchase";
						model.asMap().clear();
					}
				} catch (Exception e) {
					logger.info("Error in showing the pending purchase request list view with a message :" + e);
				} finally {
					if (session.isOpen() && session != null) {
						tx.commit();
						session.close();
						
					}
				}
			}
			return returnValue;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PurchaseOrderListView", method = RequestMethod.GET)
	public String PurchaseOrderListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		List<PurchaseOrder> listPurchaseOrder = new ArrayList<PurchaseOrder>();
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {
				listPurchaseOrder = session.createQuery(
						"select 1,t.ID,TO_CHAR(t.lastmodifiedDate, 'YYYY-MM-DD HH24:MI:SS'), t.supplierName ,t.ordStatus, t.TotalAmount,t.TotalQty,t.WareHouse,t.siteID , t.wareName  from PurchaseOrder t order by t.lastmodifiedDate DESC ")
						.list();
				model.addAttribute("ListGridTable", mapper.writeValueAsString(listPurchaseOrder));
			} catch (Exception e) {
				logger.info(
						"Error on the level of purchase order list view with a message : " + e + "\n" + e.getMessage());
				model.addAttribute("ListGridTable", "");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}
		return "PurchaseOrderListView";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PendingPurchaseOrderListView", method = RequestMethod.GET)
	public String PendingPurchaseOrderListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		} else {
			List<PurchaseOrder> listPurchaseOrder = new ArrayList<PurchaseOrder>();
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
					listPurchaseOrder = session.createQuery(
							"select 1,t.ID, t.supplier, t.TotalAmount,t.TotalQty,t.WareHouse from PurchaseOrder t where t.ordStatus != 'completed' and t.ordStatus != 'closed' and t.ordStatus != 'deactivated' and t.ordStatus != 'cancelled' and t.ordStatus != 'activated' or t.ordStatus is null")
							.list();

					model.addAttribute("ListGridTable", mapper.writeValueAsString(listPurchaseOrder));
				} catch (Exception e) {
					logger.info("Error on the level of purchase order list view with a message : " + e + "\n"
							+ e.getMessage());
					model.addAttribute("ListGridTable", "");
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						
					}
				}
			}
			return "PurchaseOrderListView";
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/PurchaseReqFormView", method = RequestMethod.GET)
	public String PurchaseReqFormView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			String wareDetails = "", wareName = "", waresiteId = "", navAction = "2";
			PurchaseRequest pRq = null;
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			String pRqID = request.getParameter("ID");
			String result[] = new String[4];
			int SelectedIndex = 0;

			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				model.addAttribute("DBConnStat", "successed");
				try {
					navAction = request.getParameter("NavAction");
					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
							"Purchase Request", "Form");

					// To load new Purchase Request (PR) page
					if (StringUtils.equalsIgnoreCase(pRqID, null)) {
						model.addAttribute("creationDate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());
						model.addAttribute("lastmodifiedDate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());
						model.addAttribute("ListPRqItem", "addNew");
						model.addAttribute("docStatus", "addNew");
						model.addAttribute("SelectedIndex", "addNew");
						model.addAttribute("prCount", "addNew");

						return "PurchaseReqFormView";
					} else {
						result = form.NavigationNP(session, "PURCHASE_REQUEST", "PRQ_ID", pRqID,
								"LAST_MODIFICATION_DATE", navAction);

						SelectedIndex = Integer.parseInt(result[1]);
						pRqID = result[2];
						model.addAttribute("SelectedIndex", SelectedIndex);
						model.addAttribute("prCount", Integer.parseInt(result[0]));
						pRq = (PurchaseRequest) session.get(PurchaseRequest.class, pRqID);
						if (!StringUtils.equalsIgnoreCase(pRq.getPrwarehouse(), null)) {
							wareDetails = pRq.getPrwarehouse();
							wareName = pRq.getWareName();
							waresiteId = pRq.getSiteID();
						}
						model.addAttribute("DBConnStat", "successed");
						model.addAttribute("ID", pRq.getPurchaseReqID());
						model.addAttribute("creationDate", formatter.format(pRq.getPrcreationDate()).toString());
						model.addAttribute("lastmodifiedDate",
								formatter.format(pRq.getPrlastModifieddate()).toString());
						model.addAttribute("orderedDate", formatter.format(pRq.getProrderedDate()).toString());
						model.addAttribute("delivereyDate", formatter.format(pRq.getPrdeliveredDate()).toString());
						model.addAttribute("prsupplierID", pRq.getPrsupplierName());
						model.addAttribute("supplier", pRq.getPrsuppname());
						model.addAttribute("supplierAddress", pRq.getPrsupplierAddress());
						model.addAttribute("WareId", wareDetails);
						model.addAttribute("WareName", wareName);
						model.addAttribute("SiteId", waresiteId);
						model.addAttribute("TotalAmount", pRq.getPrtotalAmount());
						model.addAttribute("discAmnt", pRq.getPrdiscountAmount());
						model.addAttribute("discountPercent", pRq.getPrdiscountPercent());
						model.addAttribute("paidAmount", pRq.getPrpaidAmount());
						model.addAttribute("prOutstanding", pRq.getPrOutstanding());
						model.addAttribute("prStatus", pRq.getPrStatus());
						model.addAttribute("netTotal", pRq.getnTotal());
						model.addAttribute("netTotalinWord", pRq.getPrnettotalinWord());
						model.addAttribute("TotalQty", pRq.getPrtotalQty());

						str = "SELECT t.ITEM_CODE  as \"prItemCode\", t.ITEM_NAME as \"prItemName\""
								+ ",t.ITEM_MODEL as \"prItemModel\",t.ITEM_PART_NUMBER  as \"prItemPartNumber\""
								+ ",t.ITEM_BARCODE as \"prBarCode\""

								+ ", t.QTY as \"prQty\", t.RATE as \"prRate\""
								+ ", t.DISCOUNT_AMOUNT as \"prDiscountAmount\", t.TAX1 as \"prTax1\", t.NET_RATE as \"prNetRate\""
								+ ", t.TOTAL as \"prTotal\", t.TOTAL_AT as \"prTotalAt\", "
								+ " (select coalesce(sum(a.QTY),0) FROM PURCHASE_ORDER_ITEM a "
								+ " INNER JOIN PURCHASE_ORDER b ON b.PO_ID= a.PO_ID "
								+ " INNER JOIN PURCHASE_REQUEST  c ON c.PRQ_ID = b.PRQ_ID"
								+ " WHERE c.PRQ_ID =t.PRQ_ID AND a.ITEM_CODE = t.ITEM_CODE) as \"poQty\","
								+ " (SELECT COALESCE(SUM(a.QTY), 0) FROM GOODS_RECEIVED_ITEM a "
								+ " INNER JOIN GOODS_RECEIVED b ON b.GR_ID=a.GR_ID "
								+ " INNER JOIN PURCHASE_REQUEST c ON c.PRQ_ID= b.PRQ_ID "
								+ " WHERE c.PRQ_ID = t.PRQ_ID AND a.ITEM_CODE = t.ITEM_CODE) as \"grQty\","
								+ " (select count(a.AR_ID) FROM ASSET_REGISTRY a "
								+ " INNER JOIN PURCHASE_REQUEST b ON b.PRQ_ID=a.PRQ_ID "
								+ " WHERE b.PRQ_ID =t.PRQ_ID AND a.ITEM_CODE = t.ITEM_CODE) as \"arQty\","
								+ " (select a.TOTALQTY FROM CAPITAL_IN_PROGRESS a "
								+ " INNER JOIN PURCHASE_REQUEST b ON b.PRQ_ID=a.PRQ_ID "
								+ " WHERE b.PRQ_ID =t.PRQ_ID AND a.ITEM_CODE = t.ITEM_CODE) as \"cipQty\","
								+ " (select count(a.FAR_ID) FROM FIXED_ASSET_REGISTRY a "
								+ " INNER JOIN PURCHASE_REQUEST b ON b.PRQ_ID=a.PRQ_ID "
								+ " WHERE b.PRQ_ID =t.PRQ_ID AND a.ITEM_CODE = t.ITEM_CODE) as \"farQty\","
								+ " t.PRQ_ITEM_ID as \"prqItemId\", t.PRQ_PO_STATUS as \"prqPoStatus\""
								+ " FROM PURCHASE_REQUEST_ITEM t where t.PRQ_ID like :param1";
//						,t.ITEM_CAT1 as \"prCat1\""
//								+ ",t.ITEM_CAT2 as \"prCat2\",t.ITEM_CAT3 as \"prCat3\""
//								+ ",t.ITEM_CAT4 as \"prCat4\",t.ITEM_SEQ as \"prSequ\""
						System.out.println(str);
						query = session.createNativeQuery(str);
						query.setParameter("param1", pRqID);
						List<PurchaseRequestBoq> listPRqBoq = ((NativeQuery<PurchaseRequestBoq>) query)
								.addScalar("prItemCode").addScalar("prItemName").addScalar("prItemModel")
								.addScalar("prItemPartNumber")// addScalar("prCat1").addScalar("prCat2").addScalar("prCat3")

								// .addScalar("prCat4").addScalar("prSequ").
								.addScalar("prBarCode").addScalar("prQty", new FloatType())
								.addScalar("prRate", new FloatType()).addScalar("prDiscountAmount", new FloatType())
								.addScalar("prTax1", new FloatType()).addScalar("prNetRate", new FloatType())
								.addScalar("prTotal", new FloatType()).addScalar("prTotalAt", new FloatType())
								.addScalar("poQty", new StringType()).addScalar("grQty", new StringType())
								.addScalar("arQty", new StringType()).addScalar("cipQty", new StringType())
								.addScalar("farQty", new StringType()).addScalar("prqItemId")
								.addScalar("prqPoStatus", new StringType())
								.setResultTransformer(Transformers.aliasToBean(PurchaseRequestBoq.class)).list();
						model.addAttribute("ListPRqItem", mapper.writeValueAsString(listPRqBoq));
					}

				} catch (Exception e) {
					logger.info("Error in opening the purchase request view with a message of :", e.getMessage());
					model.addAttribute("ID", null);
					model.addAttribute("creationDate", null);
					model.addAttribute("lastmodifiedDate", null);
					model.addAttribute("orderedDate", null);
					model.addAttribute("delivereyDate", null);
					model.addAttribute("prsupplierID", null);
					model.addAttribute("supplier", null);
					model.addAttribute("supplierAddress", null);
					model.addAttribute("WareId", null);
					model.addAttribute("WareName", null);
					model.addAttribute("SiteId", null);
					model.addAttribute("TotalAmount", null);
					model.addAttribute("discAmnt", null);
					model.addAttribute("discountPercent", null);
					model.addAttribute("paidAmount", null);
					model.addAttribute("prOutstanding", null);
					model.addAttribute("prStatus", null);
					model.addAttribute("netTotal", null);
					model.addAttribute("netTotalinWord", null);
					model.addAttribute("TotalQty", null);
					model.addAttribute("ListPRqItem", "notAddNew");
					model.addAttribute("DBConnStat", "failed");
					return "redirect:/PurchaseReqListView";
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						
					}
				}
			}
			return "PurchaseReqFormView";
		}

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/PurchaseOrderFormView", method = RequestMethod.GET)
	public String PurchaseOrderFormView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session.isOpen() && session != null) {
				String pRqID, navAction = "2";
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				String pOrdrID = request.getParameter("ID");
				String type = request.getParameter("type");
				PurchaseRequest pRq;
				PurchaseOrder pOrdr;
				String result[] = new String[4];
				int SelectedIndex = 0;

				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				try {
					navAction = request.getParameter("NavAction");
					if (pOrdrID == null) {
						pRqID = request.getParameter("ordPRQid");
						if (pRqID != null) {
							if (StringUtils.equalsAnyIgnoreCase(type, "addNewFromPRQ")) {
								System.out.println("type is " +type);
								pRq = (PurchaseRequest) session.get(PurchaseRequest.class, pRqID);
								model.addAttribute("ordPRQid", pRqID);
								model.addAttribute("supplier", pRq.getPrsupplierName());
								model.addAttribute("ordsupplierName", pRq.getPrsuppname());
								model.addAttribute("supplierAddress", pRq.getPrsupplierAddress());
								model.addAttribute("WareId", pRq.getPrwarehouse());
								model.addAttribute("WareName", pRq.getWareName());
								model.addAttribute("SiteId", pRq.getSiteID());
								model.addAttribute("creationDate",
										formatter.format(pRq.getPrcreationDate()).toString());
								model.addAttribute("lastmodifiedDate",
										formatter.format(pRq.getPrlastModifieddate()).toString());
								model.addAttribute("orderedDate", formatter.format(pRq.getProrderedDate()).toString());
								model.addAttribute("delivereyDate",
										formatter.format(pRq.getPrdeliveredDate()).toString());
								model.addAttribute("TotalAmount", pRq.getPrtotalAmount());
								model.addAttribute("discAmount", pRq.getPrdiscountAmount());
								model.addAttribute("discountPercent", pRq.getPrdiscountPercent());
								model.addAttribute("paidAmount", pRq.getPrpaidAmount());
								model.addAttribute("ordOutstanding", pRq.getPrOutstanding());
								model.addAttribute("ordStatus", "draft");
								model.addAttribute("netTotal", pRq.getnTotal());
								model.addAttribute("ordponumber", pRq.getPrnettotalinWord());
								model.addAttribute("TotalQty", pRq.getPrtotalQty());
								model.addAttribute("docStatus", "addNewFromPRQ");
								model.addAttribute("SelectedIndex", "addNewFromPRQ");
								model.addAttribute("poCount", "addNewFromPRQ");
								model.addAttribute("discrepancy", "addNewFromPRQ");								

								// add data in table PurchasereqItem

								str = "select t.ItemCode as ItemCode , t.ItemName as ItemName , t.ItemModel as ItemModel , t.ItemPartNb as ItemPartNumber ,"
										+ " t.Qty as qty, t.Rate as rate , t.DiscAmnt as discountAmount , t.Tax1 as tax1, t.NetRate as netRate , t.Total as total ,"
										+ " t.TotalAt as totalAt, t.PqNo as grQty , t.GrNo as prQty , t.ArNo as arQty , t.CipNo as cipQty , t.FarNo as farQty, '0' as pordItemId"
										+ " from PurchaseRequestItem t where t.PRQId like :param1";

								query = session.createQuery(str);
								query.setParameter("param1", pRqID);
								
								List<PurchaseOrderBoq> listPurchaseOrderBoq = (List<PurchaseOrderBoq>) query
										.setResultTransformer(Transformers.aliasToBean(PurchaseOrderBoq.class)).list();
								System.out.println("BoQ is " +mapper.writeValueAsString(listPurchaseOrderBoq));
								model.addAttribute("ListPoItem", mapper.writeValueAsString(listPurchaseOrderBoq));
							}
						} else {

							model.addAttribute("creationDate",
									formatter.format(new Timestamp(System.currentTimeMillis())).toString());
							model.addAttribute("lastmodifiedDate",
									formatter.format(new Timestamp(System.currentTimeMillis())).toString());
							model.addAttribute("docStatus", "addNew");
							model.addAttribute("ordPRQid", request.getParameter("ordPRQid"));
							model.addAttribute("ListPoItem", "addNew");
							model.addAttribute("docStatus", "addNew");
							model.addAttribute("SelectedIndex", "addNew");
							model.addAttribute("poCount", "addNew");
							model.addAttribute("discrepancy", "addNew");

						}

					}

					else {
						result = form.NavigationNP(session, "PURCHASE_ORDER", "PO_ID", pOrdrID,
								"LAST_MODIFICATION_DATE", navAction);

						SelectedIndex = Integer.parseInt(result[1]);
						pOrdrID = result[2];

						model.addAttribute("SelectedIndex", SelectedIndex);
						model.addAttribute("poCount", Integer.parseInt(result[0]));

						pOrdr = (PurchaseOrder) session.get(PurchaseOrder.class, pOrdrID);
						if (pOrdr != null) {
							model.addAttribute("ID", pOrdr.getPurchaseOrdID());
							model.addAttribute("creationDate", formatter.format(pOrdr.getOrdcreationDate()).toString());
							model.addAttribute("lastmodifiedDate",
									formatter.format(pOrdr.getOrdlastModifieddate()).toString());
							model.addAttribute("orderedDate", formatter.format(pOrdr.getOrdorderedDate()).toString());
							model.addAttribute("delivereyDate",
									formatter.format(pOrdr.getOrddeliveredDate()).toString());
							model.addAttribute("supplier", pOrdr.getOrdsupplierId());
							model.addAttribute("ordsupplierName", pOrdr.getOrdSuppName());
							model.addAttribute("supplierAddress", pOrdr.getOrdsupplierAddress());
							model.addAttribute("WareId", pOrdr.getOrdwarehouse());
							model.addAttribute("WareName", pOrdr.getWareName());
							model.addAttribute("SiteId", pOrdr.getSiteID());
							model.addAttribute("TotalAmount", pOrdr.getOrdtotalAmount());
							model.addAttribute("discAmount", pOrdr.getOrddiscountAmount());
							model.addAttribute("discountPercent", pOrdr.getOrddiscountPercent());
							model.addAttribute("paidAmount", pOrdr.getOrdpaidAmount());
							model.addAttribute("ordOutstanding", pOrdr.getOrdOutstanding());
							model.addAttribute("ordStatus", pOrdr.getOrdStatus());
							model.addAttribute("netTotal", pOrdr.getOrdnettotal());
							model.addAttribute("ordponumber", pOrdr.getOrdPonumber());
							model.addAttribute("TotalQty", pOrdr.getOrdtotalQty());
							model.addAttribute("ordPRQid", pOrdr.getOrdPRQid());
						}

						// add data in table PurchaseorderItem
						
						str = "SELECT t.ITEM_CODE as itemCode , t.ITEM_NAME as itemName , t.ITEM_MODEL as itemModel , t.ITEM_PART_NUMBER as itemPartNumber ,t.QTY as qty,"
								+ " t.RATE as rate,t.DISCOUNT_AMOUNT as discountAmount,t.TAX1 as tax1 , t.NET_RATE as netRate, t.TOTAL as total, t.TOTAL_AT as totalAt, "
								+ " t.ITEM_BARCODE as poBarCode, "
								+ " (SELECT coalesce(sum(distinct(a.QTY)),0) FROM GOODS_RECEIVED_ITEM a   "
								+ " INNER JOIN GOODS_RECEIVED b ON b.GR_ID=a.GR_ID "
								+ " INNER JOIN PURCHASE_ORDER c ON c.PO_ID= b.PO_ID "
								+ " WHERE c.PO_ID = t.PO_ID AND a.ITEM_CODE = t.ITEM_CODE) as grQty,  "
								+ " (SELECT coalesce(sum(distinct(a.QTY)),0) FROM PURCHASE_REQUEST_ITEM a "
								+ " INNER JOIN PURCHASE_REQUEST b ON b.PRQ_ID= a.PRQ_ID "
								+ " INNER JOIN PURCHASE_ORDER  c ON c.PRQ_ID = b.PRQ_ID "
								+ " WHERE c.PO_ID =t.PO_ID AND a.ITEM_CODE = t.ITEM_CODE) as prQty,      "
								+ " (select count(distinct(a.AR_ID)) FROM ASSET_REGISTRY a "
								+ " INNER JOIN PURCHASE_ORDER_ITEM b ON b.PO_ID=a.PO_ID "
								+ " WHERE b.PO_ID =t.PO_ID AND a.ITEM_CODE = t.ITEM_CODE) as arQty,"
								+ " (select coalesce(sum(distinct(a.TOTALQTY)),0) FROM CAPITAL_IN_PROGRESS a "
								+ " INNER JOIN PURCHASE_ORDER_ITEM b ON b.PO_ID=a.PO_ID "
								+ " WHERE b.PO_ID =t.PO_ID AND a.ITEM_CODE = t.ITEM_CODE) as cipQty,"
								+ " (select count(distinct(a.FAR_ID)) FROM FIXED_ASSET_REGISTRY a "
								+ " INNER JOIN PURCHASE_ORDER_ITEM b ON b.PO_ID=a.PO_ID "
								+ " WHERE b.PO_ID =t.PO_ID AND a.ITEM_CODE = t.ITEM_CODE) as farQty,"
								+ " t.PO_ITEM_ID as pordItemId, t.PO_ITEM_STATUS as poItemStatus , x.serial_obj"
								+ " FROM PURCHASE_ORDER_ITEM t LEFT JOIN (select distinct poitem_id,json_object('serialArray' value json_arrayagg(json_object('serial_no' value serial_number,'itm_model'  value item_model,'itm_partno' value item_partno))) as serial_obj "
								+ "from serial_number group by poitem_id) x on x.POITEM_ID = t.PO_ITEM_ID where t.PO_ID like :param1 order by t.ITEM_CODE";
																
						
						query = session.createNativeQuery(str);
						query.setParameter("param1", pOrdrID);
						List<PurchaseOrderBoq> listPurchaseOrderBoq = (List<PurchaseOrderBoq>) ((NativeQuery<PurchaseOrderBoq>) query)
								.addScalar("itemCode").addScalar("itemName").addScalar("itemModel")
								.addScalar("itemPartNumber").addScalar("poBarCode", new StringType())
								.addScalar("qty", new FloatType()).addScalar("rate", new FloatType())
								.addScalar("discountAmount", new FloatType()).addScalar("tax1", new FloatType())
								.addScalar("netRate", new FloatType()).addScalar("total", new FloatType())
								.addScalar("totalAt", new FloatType()).addScalar("grQty", new StringType())
								.addScalar("prQty", new StringType()).addScalar("arQty", new StringType())
								.addScalar("cipQty", new StringType()).addScalar("farQty", new StringType())
								.addScalar("pordItemId").addScalar("poItemStatus", new StringType())
								.addScalar("serial_obj", new StringType())
								.setResultTransformer(Transformers.aliasToBean(PurchaseOrderBoq.class)).list();
						model.addAttribute("docStatus", "Existed");
						model.addAttribute("ListPoItem", mapper.writeValueAsString(listPurchaseOrderBoq));

						///////// Discrepancy Report
						query = session.createNativeQuery(
								"SELECT item_code, item_name, item_model, item_part_number, SUM(qty), net_rate  FROM purchase_order_item WHERE po_id = :param1 GROUP BY item_code,item_name,item_model,item_part_number,net_rate");
						query.setParameter("param1", pOrdrID);

						List<Object[]> purchaseOrderItems = query.list();
						List<Object[]> discrepancy = new ArrayList<Object[]>();

						for (Object[] items : purchaseOrderItems) {
							BigDecimal qty = (BigDecimal) items[4]; // Use BigDecimal for quantity
							// Use the item_code and pOrdrID to query the asset_registry table
/*							
							query = session.createNativeQuery(
									"select count(*) from asset_registry where item_code=:param1 and po_id=:param2"); */
							
							query = session.createNativeQuery(
									"select count(*) from far_model_partnumber a, fixed_asset_registry b where a.item_code=:param1 and b.po_id=:param2 and a.far_id = b.far_id");
							
							
							query.setParameter("param1", (String) items[0]);
							query.setParameter("param2", pOrdrID);
							BigDecimal assetCount = (BigDecimal) query.uniqueResult(); // Retrieve the count as a
																						// BigDecimal
							if (assetCount.compareTo(BigDecimal.ZERO) > 0) {
								if (qty.compareTo(assetCount) > 0) {
									items[4] = qty.subtract(assetCount);
									discrepancy.add(items);
								}
							} else {
								discrepancy.add(items);
							}
						}
						model.addAttribute("discrepancy", mapper.writeValueAsString(discrepancy));
					}
				} catch (Exception e) {
					logger.info("Error on the level of showing purchase order form view, with a message : "
							+ e.getMessage());
					e.printStackTrace();
					model.addAttribute("ID", "");
					model.addAttribute("creationDate", "");
					model.addAttribute("lastmodifiedDate", "");
					model.addAttribute("orderedDate", "");
					model.addAttribute("delivereyDate", "");
					model.addAttribute("supplier", "");
					model.addAttribute("ordsupplierName", "");
					model.addAttribute("supplierAddress", "");
					model.addAttribute("WareId", "");
					model.addAttribute("WareName", "");
					model.addAttribute("SiteId", "");
					model.addAttribute("TotalAmount", "");
					model.addAttribute("discAmount", "");
					model.addAttribute("discountPercent", "");
					model.addAttribute("paidAmount", "");
					model.addAttribute("ordOutstanding", "");
					model.addAttribute("ordStatus", "");
					model.addAttribute("netTotal", "");
					model.addAttribute("ordponumber", "");
					model.addAttribute("TotalQty", "");
					model.addAttribute("ordPRQid", "");
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						
					}
				}
			}
			return "PurchaseOrderFormView";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PurchaseReqFormSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> PurchaseReqFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		} else {

//		String[] formSave= request.getParameterValues("FormSave[]");//to get the parameters from the FormView (whether it is New,Approve,Cancel)
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				PurchaseRequest pRq = new PurchaseRequest();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(new Date());
				int year = calendar.get(Calendar.YEAR);
				DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

				PurchaseRequestItem pRqItem;
				Map<String, String> BoqItem;
				String pRqID, pRqItmID = null, fullItmCode, itmCode, itmName;
				String pRqAppFlag = request.getParameter("pRqAppFlag");
				String pRqCnclFlg = request.getParameter("pRqCnclFlg");
				String pRqStat = request.getParameter("prStatus");
				String[] slctDel = request.getParameterValues("slctDel[]");
				List<Object[]> prcsData;

				tx = session.beginTransaction();
				try {

					Timestamp creationDate = new Timestamp(
							formatter.parse(request.getParameter("creationDate")).getTime());
					Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
					Timestamp orderedDate = new Timestamp(
							formatter.parse(request.getParameter("orderedDate")).getTime());
					Timestamp delivereyDate = new Timestamp(
							formatter.parse(request.getParameter("delivereyDate")).getTime());

					String prList = request.getParameter("prList");
					if (prList != null && prList.equals("prList")) {
						query = session.createQuery("select ID,supplier from PurchaseRequest");
						if (query.list() != null) {
							rtn.put("listItemsNav", query.list());
						}

					} else {
						model.addAttribute("listItemsNav", "noList");
					}

					/*
					 * 
					 * ///////////////////////////////////////////////////////// SEND EMAIL BUTTON
					 * ////////////////////////////////////////////////////////// String
					 * email=request.getParameter("email"); String
					 * emailTo=request.getParameter("emailTo"); String
					 * password=request.getParameter("password"); String
					 * message=request.getParameter("message"); String
					 * subject=request.getParameter("subject"); String
					 * ccmail=request.getParameter("ccmail");
					 * 
					 * 
					 * 
					 * if(StringUtils.equalsIgnoreCase(request.getParameter("email"), "") &&
					 * StringUtils.equalsIgnoreCase(request.getParameter("password"), "") &&
					 * StringUtils.equalsIgnoreCase(request.getParameter("emailTo"), "") &&
					 * StringUtils.equalsIgnoreCase(request.getParameter("message"), "") &&
					 * StringUtils.equalsIgnoreCase(request.getParameter("subject"), "") &&
					 * StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), "") ) {
					 * System.out.println("NO EMAIL TO SEND!");
					 * 
					 * } else if(StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), "")) {
					 * 
					 * JavaMailUtil.SendEmails(email,password,emailTo,subject,message);
					 * 
					 * } else { //Note : the following function is making an error while canceling
					 * the purchase request
					 * //JavaMailUtil.SendccEmails(email,password,emailTo,ccmail,subject,message); }
					 * 
					 * 
					 * ///////////////////////////////////////////// END OF SEND EMAIL BUTTON
					 * //////////////////////////////////////////////////////////
					 */

					if (StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNew")) {

						synchronized (this) {
							pRqID = "PRQ_" + year + "_"
									+ Integer.parseInt(
											session.createNativeQuery("SELECT PURCHASE_REQUEST FROM SEQ_TABLE")
													.uniqueResult().toString());
							query = session
									.createNativeQuery("UPDATE SEQ_TABLE SET PURCHASE_REQUEST = PURCHASE_REQUEST + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						// pRqID = "PRQ_" + year + "_";
						model.addAttribute("ID", pRqID);
						System.out.println("The PRQ ID is " + pRqID);

					} else {
						pRqID = request.getParameter("ID");

					}
					pRq.setPurchaseReqID(pRqID);
					pRq.setPrcreationDate(creationDate);
					pRq.setPrlastModifieddate(lastModifiedDate);
					pRq.setPrsupplierName(request.getParameter("supplier"));
					pRq.setPrsuppname(request.getParameter("supplierName"));
					pRq.setPrsupplierAddress(request.getParameter("supplierAddress"));
					pRq.setProrderedDate(orderedDate);
					pRq.setPrdeliveredDate(delivereyDate);
					pRq.setPrwarehouse(request.getParameter("WareHouse"));
					pRq.setPrtotalAmount(Float.parseFloat(request.getParameter("TotalAmount")));
					pRq.setPrdiscountAmount(Float.parseFloat(request.getParameter("discAmnt")));
					pRq.setPrdiscountPercent(Float.parseFloat(request.getParameter("discountPercent")));
					pRq.setPrpaidAmount(Float.parseFloat(request.getParameter("paidAmount")));
					pRq.setPrOutstanding(Float.parseFloat(request.getParameter("prOutstanding")));
					pRq.setPrStatus(pRqStat);
					pRq.setnTotal(Float.parseFloat(request.getParameter("netTotal")));
					pRq.setPrnettotalinWord(request.getParameter("netTotalinWord"));
					pRq.setPrtotalQty(Float.parseFloat(request.getParameter("TotalQty")));
					pRq.setWareName(request.getParameter("WareName"));
					pRq.setSiteID(request.getParameter("siteID"));
					session.saveOrUpdate(pRq);

					/*
					 * query = session.createSQLQuery("INSERT INTO PRQTEST VALUES ('" + pRqID +
					 * "' || PURCHASE_REQUEST_SEQ.nextval,'Hiba', 5000, '"+lastModifiedDateStr+"')")
					 * ;
					 * 
					 * query.executeUpdate();
					 */

					////////////// checking data for email notification in PR ///////////////////
					/*
					 * 
					 * for(String frmSave: formSave) { if(frmSave != null && frmSave.length() > 0) {
					 * System.out.println("the value of frmSave =  "+frmSave); EmailCondition
					 * thread= new EmailCondition("PurchaseRequest", frmSave,pRqID); //Calling Email
					 * Notification checker thread.start(); } }
					 */

					///////////////////////////////////////////////////////////////////////////////////////////////
					if (slctDel != null) {
						str = "delete PurchaseRequestItem t where t.prqItemId IN (:param1) ";
						query = session.createQuery(str);
						query.setParameterList("param1", slctDel);
						query.executeUpdate();

					}

					if (itemParameters.getDictParameter() != null) {

						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {

							BoqItem = itemParameters.getDictParameter().get(i);
							pRqItem = new PurchaseRequestItem();
							fullItmCode = BoqItem.get(PR_ITEMCODE);
							itmCode = "";
							itmName = "";
							if (!fullItmCode.isEmpty()) {
								itmCode = fullItmCode.substring(0, fullItmCode.indexOf(":"));
								itmName = fullItmCode.substring(fullItmCode.indexOf(":") + 1, fullItmCode.length());
							}

							if (StringUtils.equalsIgnoreCase(BoqItem.get(PRQ_ITEM_ID), "0")) {

								// pRqItmID = "PRQI_" + year + "_" + appConfig.getSequenceNbr(1);
								synchronized (this) {
									pRqItmID = "PRQI_" + year + "_"
											+ Integer
													.parseInt(session.createNativeQuery("SELECT PR_ITEM FROM SEQ_TABLE")
															.uniqueResult().toString());
									query = session.createNativeQuery("UPDATE SEQ_TABLE SET PR_ITEM = PR_ITEM + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate();
								}
								pRqItem.setPrqItemId(pRqItmID);

							} else {
								pRqItmID = BoqItem.get(PRQ_ITEM_ID);
								pRqItem.setPrqItemId(pRqItmID);
							}

							pRqItem.setPrqiCreationDate(new Timestamp(System.currentTimeMillis()));
							pRqItem.setPrqiLastMOdifiedDate(new Timestamp(System.currentTimeMillis()));
							pRqItem.setPrqiItemCode(itmCode);
							pRqItem.setPrqiItemName(itmName);
							pRqItem.setPrqiItemModel(BoqItem.get(PR_ITEMMODEL));
							pRqItem.setPrqiItemPartNumber(BoqItem.get(PR_ITEMPARTNO));
							pRqItem.setPrqiBarcode(BoqItem.get(PR_ITEMBARCODE));
							pRqItem.setPrqiRate(Float.parseFloat(BoqItem.get(PR_RATE)));
							pRqItem.setPrqiDiscountAmount(Float.parseFloat(BoqItem.get(PR_DISCOUNT_AMMOUNT)));
							pRqItem.setPrqiDiscountPercent(0);
							pRqItem.setPrqiNetRate(Float.parseFloat(BoqItem.get(PR_NETRATE)));
							pRqItem.setPrqiTax1(Float.parseFloat(BoqItem.get(PR_TAX)));
							pRqItem.setPrqiTax2(0);
							pRqItem.setPrqiTotal(Float.parseFloat(BoqItem.get(PR_TOTAL)));
							pRqItem.setPrqiTotalAt(Float.parseFloat(BoqItem.get(PR_TOTAL_AT)));
							pRqItem.setPrqiFarNo(request.getParameter("priFarNo"));
							pRqItem.setPrqiQty(Float.parseFloat(BoqItem.get(PR_QTY)));
							pRqItem.setPrqiPrqId(pRqID);
							pRqItem.setiDarNo(request.getParameter("priDarNo"));
							pRqItem.setPrqPoStatus(BoqItem.get(PRQ_PO_STATUS));

							session.saveOrUpdate(pRqItem);

						}

						if (StringUtils.equalsIgnoreCase(pRqAppFlag, "1")
								&& StringUtils.equalsIgnoreCase(pRqStat, "approved")) {

							AssetLifeCycleThread InsertionALCThread = new AssetLifeCycleThread("PR", pRqID, "", "",
									itemParameters);
							InsertionALCThread.start(); // will occur parallel with the execution of saving PR.
						}

						else if (StringUtils.equalsIgnoreCase(pRqAppFlag, "0")
								&& StringUtils.equalsIgnoreCase(pRqCnclFlg, "1")) {
							if (StringUtils.equalsIgnoreCase(pRqStat, "draft")) {
								query = session.createQuery(
										"update PurchaseRequest set prStatus = 'cancelled' WHERE ID = :param1");
								query.setParameter("param1", pRqID);
								query.executeUpdate();
							} else {
								query = session
										.createQuery("select ordStatus,ID FROM PurchaseOrder WHERE ordPRQid = :param1");
								query.setParameter("param1", pRqID);
								prcsData = query.list();
								if (!prcsData.isEmpty() && prcsData != null) {
									String processStat = (String) prcsData.get(0)[0];
									if (StringUtils.equalsIgnoreCase(processStat, "approved")
											|| StringUtils.equalsIgnoreCase(processStat, "completed")
											|| StringUtils.equalsIgnoreCase(processStat, "closed")) {
										rtn.put("RevPrcStatCancel", "rejected");
										rtn.put("POtobeCanceled", prcsData.get(0)[1]);
									} else {

										rtn.put("RevPrcStatCancel", "accepted");
										query = session.createQuery(
												"update PurchaseRequest set prStatus = 'cancelled' WHERE ID = :param1");
										query.setParameter("param1", pRqID);
										query.executeUpdate();
										/// thread
										AssetLifeCycleCanceling ALCCancel = new AssetLifeCycleCanceling("PR", pRqID, "",
												"");
										ALCCancel.start();
									}
								} else {
									query = session.createQuery(
											"select grStatus,ID FROM GoodsReceived WHERE grPRQid = :param1");
									query.setParameter("param1", pRqID);
									prcsData = query.list();
									if (!prcsData.isEmpty() && prcsData != null) {

										String processStat = (String) prcsData.get(0)[0];
										if (StringUtils.equalsIgnoreCase(processStat, "completed")) {
											rtn.put("GrStatCancel", "rejected");
											rtn.put("GRtobeCanceled", prcsData.get(0)[1]);
										} else {

											rtn.put("GrStatCancel", "accepted");
											/// thread
											query = session.createQuery(
													"update PurchaseRequest set prStatus = 'cancelled' WHERE ID = :param1");
											query.setParameter("param1", pRqID);
											query.executeUpdate();
											AssetLifeCycleCanceling ALCCancel = new AssetLifeCycleCanceling("PR", pRqID,
													"", "");
											ALCCancel.start();
										}

									} else {
										rtn.put("GrStatCancel", "accepted");
										/// thread
										query = session.createQuery(
												"update PurchaseRequest set prStatus = 'cancelled' WHERE ID = :param1");
										query.setParameter("param1", pRqID);
										query.executeUpdate();
										AssetLifeCycleCanceling ALCCancel = new AssetLifeCycleCanceling("PR", pRqID, "",
												"");
										ALCCancel.start();
									}
								}
							}

						}
					}

					rtn.put("PREQID", pRqID);
				} catch (Exception e) {
					logger.info("Error in opening the pRq form view. ", e);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/PurchaseOrdFormSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> PurchaseOrdFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {

			String poID, pRqID, fullitemcode, itmname, poiID, itmcode, PoitemId = null, CipId, grn, prn, arn, cipn,
					farn, serialNum;
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);
			DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			String ordDate = request.getParameter("orderedDate"), delivdate = request.getParameter("delivereyDate");
			Timestamp CreationDate, lastModifiedDate, cipDate, orderedDate, delivereyDate;
			String pOrdAppFlag = request.getParameter("pOrdAppFlag"), pOrdCnclFlg = request.getParameter("pOrdCnclFlg");
			String Far = request.getParameter("FarNo"), Dar = request.getParameter("DarNo");
			CapitalInProgress assetCip;
			PurchaseOrderItem pOrdItem;
			PurchaseOrder pOrd;
			String[] slctDelOrd;

			///////////////////////////////////////////////////////// SEND EMAIL BUTTON
			///////////////////////////////////////////////////////// //////////////////////////////////////////////////////////
			// String email=request.getParameter("email");
			// String emailTo=request.getParameter("emailTo");
			// String password=request.getParameter("password");
			// String message=request.getParameter("message");
			// String subject=request.getParameter("subject");
			// String ccmail=request.getParameter("ccmail");
			//
			// System.out.println("all email parameters = "+email+" "+emailTo+" "+password+"
			///////////////////////////////////////////////////////// "+message+"
			///////////////////////////////////////////////////////// "+subject+" "+ccmail);
			//
			//
			// if(StringUtils.equalsIgnoreCase(request.getParameter("email"), "") &&
			///////////////////////////////////////////////////////// StringUtils.equalsIgnoreCase(request.getParameter("password"),
			///////////////////////////////////////////////////////// "") &&
			///////////////////////////////////////////////////////// StringUtils.equalsIgnoreCase(request.getParameter("emailTo"),
			///////////////////////////////////////////////////////// "") &&
			///////////////////////////////////////////////////////// StringUtils.equalsIgnoreCase(request.getParameter("message"),
			///////////////////////////////////////////////////////// "") &&
			///////////////////////////////////////////////////////// StringUtils.equalsIgnoreCase(request.getParameter("subject"),
			///////////////////////////////////////////////////////// "") &&
			///////////////////////////////////////////////////////// StringUtils.equalsIgnoreCase(request.getParameter("ccmail"),
			///////////////////////////////////////////////////////// "") )
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
			//// JavaMailUtil.SendccEmails(email,password,emailTo,ccmail,subject,message);
			// }

			///////////////////////////////////////////// END OF SEND EMAIL BUTTON
			///////////////////////////////////////////// //////////////////////////////////////////////////////////

			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {

					if (StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNew")
							|| StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNewFromPRQ")) {
						synchronized (this) {
							poID = "PO_" + year + "_"
									+ Integer.parseInt(session.createNativeQuery("SELECT PURCHASE_ORDER FROM SEQ_TABLE")
											.uniqueResult().toString());
							query = session
									.createNativeQuery("UPDATE SEQ_TABLE SET PURCHASE_ORDER = PURCHASE_ORDER + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						// poID = "PO_" + year + "_" + appConfig.getSequenceNbr(2);
						model.addAttribute("ID", poID);

					} else {
						poID = request.getParameter("ID");

					}

					orderedDate = new Timestamp(formatter1.parse(ordDate).getTime());
					delivereyDate = new Timestamp(formatter1.parse(delivdate).getTime());
					CreationDate = new Timestamp(formatter1.parse(request.getParameter("creationDate")).getTime());
					lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

					pOrd = new PurchaseOrder();

					pOrd.setPurchaseOrdID(poID);
					pOrd.setOrdcreationDate(CreationDate);
					pOrd.setOrdlastModifieddate(lastModifiedDate);
					pOrd.setOrdsupplierId(request.getParameter("supplier"));
					pOrd.setOrdSuppName(request.getParameter("ordsupplierName"));
					pOrd.setOrdsupplierAddress(request.getParameter("supplierAddress"));
					pOrd.setOrdorderedDate(orderedDate);
					pOrd.setOrddeliveredDate(delivereyDate);
					pOrd.setOrdwarehouse(request.getParameter("WareHouse"));
					pOrd.setOrdtotalAmount(Float.parseFloat(request.getParameter("TotalAmount")));
					pOrd.setOrddiscountAmount(Float.parseFloat(request.getParameter("discAmount")));
					pOrd.setOrddiscountPercent(Float.parseFloat(request.getParameter("discountPercent")));
					pOrd.setOrdpaidAmount(Float.parseFloat(request.getParameter("paidAmount")));
					pOrd.setOrdOutstanding(Float.parseFloat(request.getParameter("ordOutstanding")));
					pOrd.setOrdStatus(request.getParameter("ordStatus"));
					pOrd.setOrdnettotal(Float.parseFloat(request.getParameter("netTotal")));
					pOrd.setOrdPonumber(request.getParameter("ordponumber"));
					pOrd.setOrdtotalQty(Float.parseFloat(request.getParameter("TotalQty")));
					pOrd.setOrdPRQid(request.getParameter("ordPRQid"));
					pOrd.setWareName(request.getParameter("WareName"));
					pOrd.setSiteID(request.getParameter("siteID"));
					session.saveOrUpdate(pOrd);

					////////////// checking data for email notification in PO ///////////////////
					// String[] formSave= request.getParameterValues("FormSave[]");//to get the
					// parameters from the FormView (whether it is New,Approve,Cancel)
					// for(String f: formSave)
					// { if(f != null && f.length() > 0)
					// System.out.println("the value of formSave f = "+f);
					// EmailCondition thread= new EmailCondition("PurchaseOrder", f,poID);//if
					// moduleName=Purchase Request and SendAlertOn=formSave are found in DB then it
					// will send an email
					// thread.start();
					// }
					///////////////////////////////////////////////////////////////////////////////////////////////

					// delete all serial numbers related to this PO.
					/*
					 * query = session.createSQLQuery(
					 * "delete from SERIAL_NUMBER a where a.POITEM_ID in (select b.PO_ITEM_ID from PURCHASE_ORDER_ITEM b where b.PO_ID = :param1)"
					 * ); query.setString("param1", poID); query.executeUpdate();
					 */

					// Remove the deleted serials in js
					String[] allDeletedSerialsArray = request.getParameterValues("allDeletedSerialsArray[]");
					if (allDeletedSerialsArray != null) {
						query = session.createQuery("delete from SerialNumber t where t.serialNumber IN (:param)");
						query.setParameterList("param", allDeletedSerialsArray);
						query.executeUpdate();
					}
					slctDelOrd = request.getParameterValues("slctDelOrd[]");
					if (slctDelOrd != null) {
						str = "delete PurchaseOrderItem t where t.pordItemId IN (:param1) ";
						query = session.createQuery(str);
						query.setParameterList("param1", slctDelOrd);
						query.executeUpdate();

					}

					if (itemParameters.getDictParameter() != null) {
						cipDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {

							pOrdItem = new PurchaseOrderItem();
							fullitemcode = itemParameters.getDictParameter().get(i).get(PO_ITEMCODE);
							itmcode = fullitemcode.substring(0, fullitemcode.indexOf(":"));
							itmname = fullitemcode.substring(fullitemcode.indexOf(":") + 1, fullitemcode.length());
							if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get(POR_ITEM_ID),
									"0")) {
								synchronized (this) {
									poiID = "POI_" + year + "_"
											+ Integer
													.parseInt(session.createNativeQuery("SELECT PO_ITEM FROM SEQ_TABLE")
															.uniqueResult().toString());
									query = session.createNativeQuery("UPDATE SEQ_TABLE SET PO_ITEM = PO_ITEM + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate();
								}
								PoitemId = poiID;
								pOrdItem.setPordItemId(poiID);

							} else {
								PoitemId = itemParameters.getDictParameter().get(i).get(POR_ITEM_ID);
								pOrdItem.setPordItemId(PoitemId);
							}
							query = session.createQuery("select ordStatus FROM PurchaseOrder WHERE ID = :param1");
							query.setParameter("param1", poID);
							String result = query.uniqueResult().toString();
							if (StringUtils.equalsIgnoreCase(result, "approved")) {
								query = session
										.createQuery("select cipID FROM CapitalInProgress WHERE PoItemId = :param1");
								query.setParameter("param1", PoitemId);
								ArrayList result2 = (ArrayList) query.list();

								if (result2.isEmpty()) {
									// CipId = "CIP_" + year + "_" + appConfig.getSequenceNbr(8);
									synchronized (this) {
										CipId = "CIP_" + year + "_"
												+ Integer.parseInt(session
														.createNativeQuery("SELECT CAPITAL_IN_PROGRESS FROM SEQ_TABLE")
														.uniqueResult().toString());
										query = session.createNativeQuery(
												"UPDATE SEQ_TABLE SET CAPITAL_IN_PROGRESS = CAPITAL_IN_PROGRESS + 1 ");
										query.executeUpdate();
										session.createNativeQuery("commit").executeUpdate();
									}
									assetCip = new CapitalInProgress();
									assetCip.setCipitemCode(itmcode);
									assetCip.setCipitemName(itmname);
									assetCip.setPoItemId(PoitemId);
									assetCip.setCipID(CipId);
									assetCip.setPoId(poID);
									assetCip.setPrId(request.getParameter("ordPRQid"));
									assetCip.setSupplierID(request.getParameter("supplier"));
									assetCip.setSupplierName(request.getParameter("ordsupplierName"));
									assetCip.setTOTALQTY(
											(Float.parseFloat(itemParameters.getDictParameter().get(i).get(PO_QTY))));
									assetCip.setCipcreatedDate(cipDate);
									assetCip.setCiplastModifiedDate(cipDate);
									session.saveOrUpdate(assetCip);

								}
							}

							pOrdItem.setPordiCreationDate(new Timestamp(System.currentTimeMillis()));
							pOrdItem.setPordiLastMOdifiedDate(new Timestamp(System.currentTimeMillis()));
							pOrdItem.setPordiItemCode(itmcode);
							pOrdItem.setPordiItemName(itmname);
							pOrdItem.setPordiItemModel(itemParameters.getDictParameter().get(i).get(PO_ITEMMODEL));
							pOrdItem.setPordiItemPartNumber(
									itemParameters.getDictParameter().get(i).get(PO_ITEMPARTNO));
							pOrdItem.setPordiRate(
									Float.parseFloat(itemParameters.getDictParameter().get(i).get(PO_RATE)));
							pOrdItem.setPordiDiscountAmount(Float
									.parseFloat(itemParameters.getDictParameter().get(i).get(PO_DISCOUNT_AMMOUNT)));
							pOrdItem.setPordiDiscountPercent(0);
							pOrdItem.setPordiNetRate(
									Float.parseFloat(itemParameters.getDictParameter().get(i).get(PO_NETRATE)));
							pOrdItem.setPordiTax1(
									Float.parseFloat(itemParameters.getDictParameter().get(i).get(PO_TAX)));
							pOrdItem.setPordiTax2(0);
							pOrdItem.setPordiTotal(
									Float.parseFloat(itemParameters.getDictParameter().get(i).get(PO_TOTAL)));
							pOrdItem.setPordiTotalAt(
									Float.parseFloat(itemParameters.getDictParameter().get(i).get(PO_TOTAL_AT)));
							pOrdItem.setPordiQty(
									Float.parseFloat(itemParameters.getDictParameter().get(i).get(PO_QTY)));
							pOrdItem.setPordiPrqId(poID);
							pOrdItem.setPordiDarNo(Far);
							pOrdItem.setPordiFarNo(Dar);
							pOrdItem.setPoItemStatus(itemParameters.getDictParameter().get(i).get(PO_ITEM_STATUS));
							pOrdItem.setPordiBarcode(itemParameters.getDictParameter().get(i).get(PO_ITEMBARCODE));
							grn = pOrdItem.getPordiGrNo();
							prn = pOrdItem.getPordiPrNo();
							arn = pOrdItem.getPordiArNo();
							cipn = pOrdItem.getPordiCIPNo();
							farn = pOrdItem.getPordiFarNo();

							if (grn == null)
								pOrdItem.setPordiGrNo("0");
							else
								pOrdItem.setPordiGrNo(grn);

							if (prn == null)
								pOrdItem.setPordiPrNo("0");
							else
								pOrdItem.setPordiPrNo(prn);

							if (arn == null)
								pOrdItem.setPordiArNo("0");
							else
								pOrdItem.setPordiArNo(arn);

							if (cipn == null)
								pOrdItem.setPordiCIPNo("0");
							else
								pOrdItem.setPordiCIPNo(cipn);

							if (farn == null)
								pOrdItem.setPordiFarNo("0");
							else
								pOrdItem.setPordiFarNo(farn);

							session.saveOrUpdate(pOrdItem);

							// Save serial number
							serialNum = itemParameters.getDictParameter().get(i).get(SERIAL_NUM);
							if (!StringUtils.equalsIgnoreCase(serialNum, null) && !serialNum.isEmpty()) {
								SerialNumber serialnum;
								ArrayList serialArrayList;
								Object serialObj = new JSONParser().parse(serialNum);
								JSONArray serialJSNArray = (JSONArray) ((HashMap) serialObj).get("serialArray");

								SerialNumber serNum = null;
								String grID = "", grItemID = "", serialNumID = "", serialNumStatus = "";

								for (Object serialJSN : (JSONArray) serialJSNArray) {
									serialArrayList = new ArrayList((((HashMap) serialJSN).values()));

									// Get purchase ID,purchase item ID
									serNum = (SerialNumber) session.get(SerialNumber.class,
											(String) serialArrayList.get(1));
									grID = "";
									grItemID = "";
									serialNumID = "";
									serialNumStatus = "";

									if (serNum != null) {
										if (!StringUtils.equalsIgnoreCase(serNum.getSerialNumber(), null)) {
											grID = serNum.getGrId();
											grItemID = serNum.getGrItemId();
											serialNumID = serNum.getSerialNumID();
											serialNumStatus = serNum.getOrdStatus();
										}
										session.evict(serNum);
									}

									if (StringUtils.equalsIgnoreCase(serialNumID, null)
											|| StringUtils.equalsIgnoreCase(serialNumID, "")) {
										synchronized (this) {
											serialNumID = "SER_" + year + "_"
													+ Integer.parseInt(session
															.createNativeQuery("SELECT SERIAL_NUM FROM SEQ_TABLE")
															.uniqueResult().toString());
											query = session.createNativeQuery(
													"UPDATE SEQ_TABLE SET SERIAL_NUM = SERIAL_NUM + 1 ");
											query.executeUpdate();
											session.createNativeQuery("commit").executeUpdate();
										}
									}
									serialnum = new SerialNumber();
									serialnum.setCreationDate(CreationDate);
									serialnum.setLastModifieddate(lastModifiedDate);
									serialnum.setSupplierId(request.getParameter("supplier"));
									serialnum.setSupplierName(request.getParameter("ordsupplierName"));
									serialnum.setItemCode(itmcode);
									serialnum.setItemName(itmname);
									serialnum.setPoId(poID);
									if (StringUtils.equalsIgnoreCase(grID, null)
											|| StringUtils.equalsIgnoreCase(grID, "")) {
										serialnum.setGrId("0");
									} else {
										serialnum.setGrId(grID);
									}
									serialnum.setSerialNumber((String) serialArrayList.get(1));
									serialnum.setItemModel((String) serialArrayList.get(2));
									serialnum.setItemPart((String) serialArrayList.get(0));
									serialnum.setPoItemId(PoitemId);
									if (StringUtils.equalsIgnoreCase(grItemID, null)
											|| StringUtils.equalsIgnoreCase(grItemID, "")) {
										serialnum.setGrItemId("0");
									} else {
										serialnum.setGrItemId(grItemID);
									}
									if (StringUtils.equalsIgnoreCase(serialNumStatus, null)
											|| StringUtils.equalsIgnoreCase(serialNumStatus, "")) {
										serialnum.setOrdStatus("draft");
									} else {
										serialnum.setOrdStatus(serialNumStatus);
									}
									serialnum.setSerialNumID(serialNumID);
									session.saveOrUpdate(serialnum);
									session.flush();
								}

							}

						}
						pRqID = request.getParameter("ordPRQid");
						if (StringUtils.equalsIgnoreCase(pOrdAppFlag, "1")) {

							//AssetLifeCycleThread InsertionALCThread;

							if (!pRqID.isEmpty()) {

								query = session.createQuery("SELECT prStatus FROM PurchaseRequest WHERE ID = :param1");
								query.setParameter("param1", pRqID);
								String processStat = query.uniqueResult().toString();

								if (!StringUtils.equalsIgnoreCase(processStat, "approved")
										&& !StringUtils.equalsIgnoreCase(processStat, "completed")
										&& !StringUtils.equalsIgnoreCase(processStat, "closed")) {
									rtn.put("RevPrcStatAprv", "rejected");
									rtn.put("PRtobeAprvd", pRqID);
								} else {
									rtn.put("RevPrcStatAprv", "accepted");
									/* InsertionALCThread = new AssetLifeCycleThread("PO", pRqID, poID, "",
											itemParameters); 
									InsertionALCThread.start(); */ // will occur parallel with the execution of saving PO.
									query = session.createQuery(
											"update PurchaseOrder set ordStatus = 'approved' WHERE ID = :param1");
									query.setParameter("param1", poID);
									query.executeUpdate();
								}
							} else {
								rtn.put("RevPrcStatAprv", "accepted");
								/* InsertionALCThread = new AssetLifeCycleThread("PO", pRqID, poID, "", itemParameters);
								InsertionALCThread.start(); // will occur parallel with the execution of saving PO. */
								query = session.createQuery(
										"update PurchaseOrder set ordStatus = 'approved' WHERE ID = :param1");
								query.setParameter("param1", poID);
								query.executeUpdate();
							}
						} else if (StringUtils.equalsIgnoreCase(pOrdAppFlag, "0")
								&& StringUtils.equalsIgnoreCase(pOrdCnclFlg, "1")) {
							//AssetLifeCycleCanceling ALCDelete;
							ArrayList grStatus;
							Object[] grStat = null;
							query = session
									.createQuery("SELECT ID, grStatus FROM GoodsReceived WHERE grPOid = :param1");
							query.setParameter("param1", poID);
							grStatus = (ArrayList) query.list();
							if (grStatus.size() != 0) {
								grStat = (Object[]) grStatus.get(0);
								if (!StringUtils.equalsIgnoreCase((CharSequence) grStat[1], "cancelled")
										&& !StringUtils.equalsIgnoreCase((CharSequence) grStat[1], "draft")) {
									rtn.put("RevGrStatCncl", "rejected");
									rtn.put("GRtobeCncld", grStat[0]);
								} else {
									rtn.put("RevGrStatCncl", "accepted");
									query = session.createQuery(
											"update PurchaseOrder set ordStatus = 'cancelled' WHERE ID = :param1");
									query.setParameter("param1", poID);
									query.executeUpdate();
									/*
									ALCDelete = new AssetLifeCycleCanceling("PO", pRqID, poID, "");
									ALCDelete.start();*/

								}
							} else {
								rtn.put("RevGrStatCncl", "accepted");
								query = session.createQuery(
										"update PurchaseOrder set ordStatus = 'cancelled' WHERE ID = :param1");
								query.setParameter("param1", poID);
								query.executeUpdate();
								/*
								ALCDelete = new AssetLifeCycleCanceling("PO", pRqID, poID, "");
								ALCDelete.start(); */
							}
						}
					}

					rtn.put("lastmodifiedDate",
							formatter1.format(new Timestamp(System.currentTimeMillis())).toString());
					rtn.put("POID", poID);

					String itemsList2 = request.getParameter("poList");
					if (itemsList2 != null && itemsList2.equals("poList")) {
						query = session.createQuery("SELECT ID,supplier FROM PurchaseOrder");
						if (query.list() != null) {
							rtn.put("listItemsNav", query.list());
						}
					} else {
						model.addAttribute("listItemsNav", "noList");
					}

				} catch (Exception e) {
					logger.info("Error in saving purchase order with a message : " + e + "" + e.getMessage());
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

	}

///////////////////////////////////////////HAjouz//////////////////////////////// END

	@RequestMapping(value = "/DeletePO", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> DeletePO(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String[] idList = request.getParameterValues("ID[]");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createQuery("delete PurchaseOrder where ID IN (:param1)");
				query.setParameterList("param1", idList);
				query.executeUpdate();

				query = session.createQuery("delete PurchaseOrderItem where POId IN (:param1)");
				query.setParameterList("param1", idList);
				query.executeUpdate();

				query = session.createQuery("delete AssetLifeCycle   where voucherNB IN (:param1)");
				query.setParameterList("param1", idList);
				query.executeUpdate();

			} catch (Exception e) {
				logger.info("Error in deleting on the level of purchase order with a message : " + e + "\n"
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

	@RequestMapping(value = "/DeletePRQ", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> DeletePRQ(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("Login")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		} else {

			String[] idList = request.getParameterValues("ID[]");
			if (idList != null) {
				session = AlmDbSession.getInstance().getSession();
				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();
					try {
						query = session.createQuery("delete PurchaseRequest t  where t.ID IN (:param1)");
						query.setParameterList("param1", idList);
						query.executeUpdate();

						query = session.createQuery("delete PurchaseRequestItem   where PRQId IN (:param1)");
						query.setParameterList("param1", idList);
						query.executeUpdate();

						query = session.createQuery("delete AssetLifeCycle   where voucherNB IN (:param1)");
						query.setParameterList("param1", idList);
						query.executeUpdate();

					} catch (Exception e) {
						logger.info("Error at Purchase Class while deleting the purchase request(s) with message : "
								+ e.getMessage());
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
							
						}
					}
				}

			} else {
				rtn.put("idListElements", "Empty");
			}
			return rtn;
		}
	}

	// To get all the purchaseOrders in the goodsRcvFormView based on the PO ID and
	// PRq ID
	@RequestMapping(value = "/GetAllPurchaseOrders", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllPurchaseOrders(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		String pOID = "%" + request.getParameter("ID") + "%"; // Getting the typed PO ID to search based on it
		String pRqID = "%" + request.getParameter("PreqId") + "%";

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
//		if (pRqID.equalsIgnoreCase("empty") == true) {

				query = session.createQuery(
						"select ID, ordPRQid from PurchaseOrder where ID like :param1 or (supplierName like :param1 or supplier like :param1) and ordPRQid like :param2");

				query.setParameter("param1", pOID);
				query.setParameter("param2", pRqID);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("Listreq", query.list());
				/*
				 * }
				 * 
				 * else { query = session.createQuery(
				 * "select ID, ordPRQid from PurchaseOrder where ID like :param1 and ordPRQid =:param2"
				 * );
				 * 
				 * query.setString("param1", pOID); query.setString("param2", pRqID);
				 * query.setFirstResult(0); query.setMaxResults(40);
				 * 
				 * rtn.put("Listreq", query.list());
				 * 
				 * }
				 */
			} catch (Exception e) {
				logger.info("Error in getting all purchase orders with a message :" + e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}

		return rtn;
	}

	@RequestMapping(value = "/GetAllPo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllPo(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		String ID = "%" + request.getParameter("ID") + "%"; // Getting the typed PO ID to search based on it
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
//		if (pRqID.equalsIgnoreCase("empty") == true) {

				query = session.createQuery(
						"select ID, supplierName from PurchaseOrder where ID like :param1 or (supplierName like :param1 or supplier like :param1) ORDER BY lastmodifiedDate DESC ");

				query.setParameter("param1", ID);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("Listreq", query.list());
				/*
				 * }
				 * 
				 * else { query = session.createQuery(
				 * "select ID, ordPRQid from PurchaseOrder where ID like :param1 and ordPRQid =:param2"
				 * );
				 * 
				 * query.setString("param1", pOID); query.setString("param2", pRqID);
				 * query.setFirstResult(0); query.setMaxResults(40);
				 * 
				 * rtn.put("Listreq", query.list());
				 * 
				 * }
				 */
			} catch (Exception e) {
				logger.info("Error in getting all purchase orders with a message :" + e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}

		return rtn;
	}

// Used in DiscoveryNew module to get the related fields of the item in the boq of the purchase Order
	@RequestMapping(value = "/GetPOitemDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetPOitemDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		String PoID = request.getParameter("PoID");
		String ItemCode = request.getParameter("ItemCode");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				query = session.createQuery(
						"select pordItemId, Rate, DiscAmnt, Tax1 from PurchaseOrderItem where POId =:param1 and ItemCode =:param2");
				query.setParameter("param1", PoID);
				query.setParameter("param2", ItemCode);
				rtn.put("PoDetails", query.list());
			} catch (Exception e) {
				logger.info("Error in getting POItem details with a message :" + e);
				rtn.put("PoDetails", "");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}

		return rtn;
	}

	// To get All the purchaseRequest IDs in the goodsReceived Form View Module
	// based on the typed PRq ID and entered PO ID

	@RequestMapping(value = "/GetAllPurchaseRequests", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllPurchaseRequests(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("Login")) {
			rtn.put("Login", "Login");
			return rtn;
		}
		String requestName = "%" + request.getParameter("ID") + "%";
		String poId = request.getParameter("PoId");

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				if (poId.equalsIgnoreCase("empty") == true) {
					query = session.createQuery("select ID from PurchaseRequest where ID like :param1");
					query.setParameter("param1", requestName);
					query.setFirstResult(0);
					query.setMaxResults(40);
					rtn.put("Listreq", query.list());
				}

				else {

					query = session.createQuery(
							"select distinct ordPRQid from  PurchaseOrder where ordPRQid like :param1 or ID =:param2");
					query.setParameter("param1", requestName);
					query.setParameter("param2", poId);
					query.setFirstResult(0);
					query.setMaxResults(40);
					rtn.put("Listreq", query.list());

				}
			} catch (Exception e) {
				logger.info("Error in getting the purchases with a message : " + e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}
		return rtn;
	}

	// To get all purchaseRequestsId in the purchasOrder Form View Module
	@RequestMapping(value = "/GetAllPurchaseRequestsOrd", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllPurchaseRequestsOrd(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("Login")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String requestName = "%" + request.getParameter("ID") + "%";

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createQuery(
						"select ID,' ',supplier,supplierName from PurchaseRequest where ID like :param1 ORDER BY lastmodifiedDate DESC");
				query.setParameter("param1", requestName);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("Listreq", query.list());
			} catch (Exception e) {
				logger.info("Error in getting the purchase requests related to the PO with a message : " + e);
				rtn.put("Listreq", "");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}

		}
		return rtn;
	}

// To get the orderId in the goodsReceived related to the purchaseRequest Id
	@RequestMapping(value = "/GetORDID", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetORDID(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String PreqId = request.getParameter("preqID");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createQuery("select  ID   from PurchaseOrder  where ordPRQid =:param1 ");
				query.setParameter("param1", PreqId);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("ListOrdIds", query.list());
			} catch (Exception e) {
				logger.info("Error in getting the purchase order ids with a message : " + e);

				rtn.put("ListOrdIds", "");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}
		return rtn;
	}

	// Getting the related information for the PR, this method will be used in the
	// OVERVIEW tab in the Purchase Request Form View
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetPrqData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetPrqData(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String ID = request.getParameter("ID");

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();

			try {

				List<Object[]> prList = (List<Object[]>) session.createNativeQuery(

						"select count(t.PO_ID), SUM(t.TOTAL_QTY), SUM(t.NET_TOTAL_AMOUNT), " +

								" (SELECT count(b.PO_ID) FROM PURCHASE_ORDER b "
								+ " WHERE b.PRQ_ID =t.PRQ_ID AND b.STATUS !='completed' AND b.STATUS!='closed') as poNotCompCount, "
								+

								" (SELECT COALESCE(SUM(b.TOTAL_QTY),0) FROM PURCHASE_ORDER b "
								+ " WHERE b.PRQ_ID =t.PRQ_ID AND b.STATUS !='completed' AND b.STATUS !='closed') as poNotCompQty, "
								+

								" (SELECT COALESCE(SUM(b.NET_TOTAL_AMOUNT),0) FROM PURCHASE_ORDER b "
								+ " WHERE b.PRQ_ID =t.PRQ_ID AND b.STATUS !='completed' AND b.STATUS !='closed') as poNotCompNetTotal, "
								+

								" (select count(a.GR_ID) FROM GOODS_RECEIVED a "
								+ " INNER JOIN PURCHASE_ORDER b ON a.PO_ID= b.PO_ID "
								+ " WHERE a.PRQ_ID = t.PRQ_ID ) as grCount, " +

								" (select COALESCE(SUM(a.TOTAL_QTY),0) FROM GOODS_RECEIVED a "
								+ " INNER JOIN PURCHASE_ORDER b ON a.PO_ID= b.PO_ID "
								+ " WHERE a.PRQ_ID = t.PRQ_ID ) as grQty, " +

								" (select COALESCE(SUM(a.NET_TOTAL_AMOUNT),0) FROM GOODS_RECEIVED a "
								+ " INNER JOIN PURCHASE_ORDER b ON a.PO_ID= b.PO_ID "
								+ " WHERE a.PRQ_ID = t.PRQ_ID ) as grNetTot, " +

								" (select count(a.GR_ID) FROM GOODS_RECEIVED a "
								+ " INNER JOIN PURCHASE_ORDER b ON a.PO_ID= b.PO_ID "
								+ " WHERE a.PRQ_ID = t.PRQ_ID and a.STATUS!='completed' and a.STATUS!='closed') as grNotCompCount, "
								+

								" (select  COALESCE(SUM(a.TOTAL_QTY),0)  FROM GOODS_RECEIVED a "
								+ " INNER JOIN PURCHASE_ORDER b ON a.PO_ID= b.PO_ID "
								+ " WHERE a.PRQ_ID = t.PRQ_ID and a.STATUS!='completed' and a.STATUS!='closed') as grNotcompQty, "
								+

								" (select  COALESCE(SUM(a.NET_TOTAL_AMOUNT),0) FROM GOODS_RECEIVED a "
								+ " INNER JOIN PURCHASE_ORDER b ON a.PO_ID= b.PO_ID "
								+ " WHERE a.PRQ_ID = t.PRQ_ID and a.STATUS!='completed' and a.STATUS!='closed') as grNotCompNetTotal, "
								+

								" (select count(a.CIP_ID) FROM CAPITAL_IN_PROGRESS a "
								+ " INNER JOIN PURCHASE_ORDER b ON a.PO_ID = b.PO_ID "
								+ " WHERE b.PRQ_ID = t.PRQ_ID ) as cipCount, " +

								" (select COALESCE(SUM(a.VALUATION_RATE),0) FROM CAPITAL_IN_PROGRESS a "
								+ " INNER JOIN PURCHASE_ORDER b ON a.PO_ID = b.PO_ID "
								+ " WHERE b.PRQ_ID = t.PRQ_ID ) as cipValRate, " +

								" (select COALESCE(SUM(a.TOTALQTY),0) FROM CAPITAL_IN_PROGRESS a "
								+ " INNER JOIN PURCHASE_ORDER b ON a.PO_ID = b.PO_ID "
								+ " WHERE b.PRQ_ID = t.PRQ_ID ) as cipQty , " +

								" (SELECT count(DISTINCT b.DN_ID) FROM DISCOVERY_NEW_ITEM b "
								+ " INNER JOIN PURCHASE_ORDER c ON b.PO_ID = c.PO_ID "
								+ " WHERE c.PRQ_ID = t.PRQ_ID ) as dnCount, " +

								" (SELECT COALESCE(SUM(b.QTY),0) FROM DISCOVERY_NEW_ITEM b "
								+ " INNER JOIN PURCHASE_ORDER c ON b.PO_ID = c.PO_ID "
								+ " WHERE c.PRQ_ID = t.PRQ_ID ) as dnQty, " +

								" (select COALESCE(SUM(b.TOTAL_AMOUNT),0) FROM DISCOVERY_NEW_ITEM b "
								+ " INNER JOIN PURCHASE_ORDER c ON b.PO_ID = c.PO_ID "
								+ " WHERE c.PRQ_ID = t.PRQ_ID ) as dnTotal, " +

								" (SELECT count(DISTINCT b.DN_ID) FROM DISCOVERY_NEW_ITEM b "
								+ " INNER JOIN PURCHASE_ORDER c ON b.PO_ID = c.PO_ID "
								+ " WHERE c.PRQ_ID = t.PRQ_ID and b.Approval  !='Completely Approved' ) as dnNotCompCount, "
								+

								" (SELECT COALESCE(SUM(b.QTY),0) FROM DISCOVERY_NEW_ITEM b "
								+ " INNER JOIN PURCHASE_ORDER c ON b.PO_ID = c.PO_ID "
								+ " WHERE c.PRQ_ID = t.PRQ_ID and b.Approval  !='Completely Approved' ) as dnNotCompQty, "
								+

								" (SELECT COALESCE(SUM(b.TOTAL_AMOUNT),0) FROM DISCOVERY_NEW_ITEM b "
								+ " INNER JOIN PURCHASE_ORDER c ON b.PO_ID = c.PO_ID "
								+ " INNER JOIN DISCOVERY_NEW a ON b.DN_ID = a.DN_ID "
								+ " WHERE c.PRQ_ID = t.PRQ_ID and b.Approval  !='Completely Approved' ) as dnNotCompTotal, "
								+

								" (select count (g.AR_ID) FROM ASSET_REGISTRY g "
								+ " INNER JOIN PURCHASE_ORDER b ON g.PO_ID = b.PO_ID "
								+ " WHERE b.PRQ_ID = t.PRQ_ID ) as arCount, " +

								" (select COALESCE(Sum(g.INITIAL_COST),0) FROM ASSET_REGISTRY g "
								+ " INNER JOIN PURCHASE_ORDER b ON g.PO_ID = b.PO_ID "
								+ " WHERE b.PRQ_ID = t.PRQ_ID ) as arValRate, " +

								" (select count (h.FAR_ID) FROM FIXED_ASSET_REGISTRY h "
								+ " INNER JOIN PURCHASE_ORDER b ON h.PO_ID = b.PO_ID "
								+ " WHERE b.PRQ_ID = t.PRQ_ID ) as farCount, " +

								" (select COALESCE(Sum(h.INITIALCOST),0) FROM FIXED_ASSET_REGISTRY h "
								+ " INNER JOIN PURCHASE_ORDER b ON h.PO_ID = b.PO_ID "
								+ " WHERE b.PRQ_ID = t.PRQ_ID ) as farInitialCost, " +

								" (select COALESCE(Sum(h.NETCOST),0) FROM FIXED_ASSET_REGISTRY h "
								+ " INNER JOIN PURCHASE_ORDER b ON h.PO_ID = b.PO_ID "
								+ " WHERE b.PRQ_ID = t.PRQ_ID ) as farNetCost, " +

								" (select count (h.FAR_ID) FROM FIXED_ASSET_REGISTRY h "
								+ " INNER JOIN PURCHASE_ORDER b ON h.PO_ID = b.PO_ID "
								+ " WHERE b.PRQ_ID = t.PRQ_ID and h.FAR_STATUS !='Running' ) as farNotRunCount, " +

								" (select COALESCE(Sum(h.INITIALCOST),0) FROM FIXED_ASSET_REGISTRY h "
								+ " INNER JOIN PURCHASE_ORDER b ON h.PO_ID = b.PO_ID "
								+ " WHERE b.PRQ_ID = t.PRQ_ID  and h.FAR_STATUS !='Running' ) as farNotRunIntCost, " +

								" (select COALESCE(Sum(h.NETCOST),0) FROM FIXED_ASSET_REGISTRY h "
								+ " INNER JOIN PURCHASE_ORDER b ON h.PO_ID = b.PO_ID "
								+ " WHERE b.PRQ_ID = t.PRQ_ID  and h.FAR_STATUS !='Running' ) as farNotRunNetCost " +

								" FROM PURCHASE_ORDER t where t.PRQ_ID='" + ID + "' GROUP BY PRQ_ID ")
						.list();

				
				List<Object[]> CIP =null;
				CIP = (List<Object[]>) session.createNativeQuery("Select totalQTY, PO_ITEM_ID from "
						    + "CAPITAL_IN_PROGRESS WHERE PRQ_ID='" +ID+ "' ").list();
				float cipCost=0;
				if(CIP != null) {
					for (Object[] obj : CIP) {
					
					query= session.createNativeQuery("select Rate from purchase_order_item where PO_ITEM_ID='" +obj[1]+ "' ");
					Object rateObject = query.uniqueResult();
					 float rate = ((Number) rateObject).floatValue();
					 cipCost+=rate* Integer.parseInt(obj[0].toString());
					
				}
				}
				
				
				
				if (prList != null) {

					for (Object[] obj : prList) {

						rtn.put("poCom", obj[0]);
						rtn.put("totQtyPOCom", obj[1]);
						rtn.put("netPOCom", obj[2]);

						rtn.put("ponotCom", obj[3]);
						rtn.put("netponotCom", obj[5]);
						rtn.put("totqtypoNotCom", obj[4]);

						rtn.put("grCom", obj[6]);
						rtn.put("netGrCom", obj[8]);
						rtn.put("totQtyGrCom", obj[7]);

						rtn.put("grNotCom", obj[9]);
						rtn.put("netGRNotCom", obj[11]);
						rtn.put("totqtyGRNotCom", obj[10]);

						rtn.put("cipCountAll", obj[12]);
						rtn.put("cipNetTotAll", cipCost);
						rtn.put("cipTotQtyAll", obj[14]);

						rtn.put("dnComp", obj[15]);
						rtn.put("dnNetTotComp", obj[17]);
						rtn.put("dnTotQtyComp", obj[16]);

						rtn.put("dnNotComp", obj[18]);
						rtn.put("dnNetTotNotComp", obj[20]);
						rtn.put("dnTotQtyNotComp", obj[19]);

						rtn.put("arCountAll", obj[21]);
						rtn.put("arValRateAll", obj[22]);

						rtn.put("farCount", obj[23]);
						rtn.put("farCountAll", obj[24]);
						rtn.put("farValRateAll", obj[25]);

						rtn.put("farNotRunCount", obj[26]);
						rtn.put("farNotRunIntCost", obj[27]);
						rtn.put("farNotRunNetCost", obj[28]);

					}
				}
			} catch (Exception e) {
				logger.info("Error in getting the PRDATA with a message : " + e);

				rtn.put("poCom", "");
				rtn.put("totQtyPOCom", "");
				rtn.put("netPOCom", "");

				rtn.put("ponotCom", "");
				rtn.put("netponotCom", "");
				rtn.put("totqtypoNotCom", "");

				rtn.put("grCom", "");
				rtn.put("netGrCom", "");
				rtn.put("totQtyGrCom", "");

				rtn.put("grNotCom", "");
				rtn.put("netGRNotCom", "");
				rtn.put("totqtyGRNotCom", "");

				rtn.put("cipCountAll", "");
				rtn.put("cipNetTotAll", "");
				rtn.put("cipTotQtyAll", "");

				rtn.put("dnComp", "");
				rtn.put("dnNetTotComp", "");
				rtn.put("dnTotQtyComp", "");

				rtn.put("dnNotComp", "");
				rtn.put("dnNetTotNotComp", "");
				rtn.put("dnTotQtyNotComp", "");

				rtn.put("arCountAll", "");
				rtn.put("arValRateAll", "");

				rtn.put("farCount", "");
				rtn.put("farCountAll", "");
				rtn.put("farValRateAll", "");

				rtn.put("farNotRunCount", "");
				rtn.put("farNotRunIntCost", "");
				rtn.put("farNotRunNetCost", "");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}

		}

		return rtn;
	}

	// Getting the related information for the PO, this method will be used in the
	// OVERVIEW tab in the Purchase Order Form View
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetPODATA", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetPODATA(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String poId = request.getParameter("ID");
		List<Object[]> poList;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();

			try {

				poList = (List<Object[]>) session.createNativeQuery(

						"select t.PO_ID, " +

								" (SELECT count(b.GR_ID) FROM GOODS_RECEIVED b "
								+ " WHERE b.PO_ID =t.PO_ID) as grCount, " +

								" (SELECT COALESCE(SUM(b.NET_TOTAL_AMOUNT),0) FROM GOODS_RECEIVED b "
								+ " WHERE b.PO_ID =t.PO_ID) as grTotalAmount, " +

								" (SELECT COALESCE(SUM(b.TOTAL_QTY),0) FROM GOODS_RECEIVED b "
								+ " WHERE b.PO_ID =t.PO_ID) as grQty, " +

								" (SELECT count(b.GR_ID) FROM GOODS_RECEIVED b "
								+ " WHERE b.PO_ID =t.PO_ID and b.STATUS!='completed' and b.STATUS!='closed') as grNotCompCount, "
								+

								" (SELECT COALESCE(SUM(b.NET_TOTAL_AMOUNT),0) FROM GOODS_RECEIVED b "
								+ " WHERE b.PO_ID =t.PO_ID and b.STATUS!='completed' and b.STATUS!='closed') as grNotCompTotAmount, "
								+

								" (SELECT COALESCE(SUM(b.TOTAL_QTY),0) FROM GOODS_RECEIVED b "
								+ " WHERE b.PO_ID =t.PO_ID and b.STATUS!='completed' and b.STATUS!='closed') as grNotCompQty, "
								+

								" (SELECT b.PRQ_ID FROM PURCHASE_REQUEST b "
								+ " WHERE b.PRQ_ID = t.PRQ_ID ) as preqId, " +

								" (SELECT b.STATUS FROM PURCHASE_REQUEST b "
								+ " WHERE b.PRQ_ID = t.PRQ_ID ) as preqStatus, " +

								" (SELECT COALESCE(SUM(b.NET_TOTAL_AMOUNT),0) FROM PURCHASE_REQUEST b "
								+ " WHERE b.PRQ_ID = t.PRQ_ID ) as preqTotalAmount, " +

								" (SELECT COALESCE(SUM(b.TOTAL_QTY),0) FROM PURCHASE_REQUEST b "
								+ " WHERE b.PRQ_ID = t.PRQ_ID ) as preqQty, " +

								" (SELECT count(DISTINCT b.DN_ID) FROM DISCOVERY_NEW_ITEM b "
								+ " WHERE b.PO_ID = t.PO_ID ) as dnCount, " +

								" (SELECT COALESCE(SUM(b.TOTAL),0) FROM DISCOVERY_NEW_ITEM b "
								+ " WHERE b.PO_ID = t.PO_ID ) as dnTotalAmount, " +

								" (SELECT COALESCE(SUM(b.QTY),0) FROM DISCOVERY_NEW_ITEM b "
								+ " WHERE b.PO_ID = t.PO_ID ) as dnQty, " +

								" (SELECT count(DISTINCT a.DN_ID) FROM DISCOVERY_NEW_ITEM a "
								+ " WHERE a.PO_ID = t.PO_id and a.Approval  !='Completely Approved' ) as dnNotCompCount, "
								+

								" (SELECT COALESCE(SUM(a.TOTAL),0) FROM DISCOVERY_NEW_ITEM a "
								+ " WHERE a.PO_ID = t.PO_id and a.Approval  !='Completely Approved' ) as dnNotCompTotAmount, "
								+

								" (SELECT COALESCE(SUM(a.QTY),0) FROM DISCOVERY_NEW_ITEM a "
								+ " WHERE a.PO_ID = t.PO_id and a.Approval  !='Completely Approved' ) as dnNotCompQty, "
								+

								" (SELECT count(b.CIP_ID) FROM CAPITAL_IN_PROGRESS b "
								+ " WHERE b.PO_ID =t.PO_ID) as cipCount, " +

//								" (SELECT COALESCE(SUM(b.VALUATION_RATE),0) FROM CAPITAL_IN_PROGRESS b "
//								+ " WHERE b.PO_ID =t.PO_ID) as cipValRate, " +

								" (SELECT COALESCE(SUM(c.NET_RATE * b.TOTALQTY),0) FROM PURCHASE_ORDER_ITEM c "
								+ "inner join CAPITAL_IN_PROGRESS b ON c.PO_ITEM_ID = b.PO_ITEM_ID and c.PO_ID = t.PO_ID) as cipValRate, " +


								" (SELECT COALESCE(SUM(b.TOTALQTY),0) FROM CAPITAL_IN_PROGRESS b "
								+ " WHERE b.PO_ID =t.PO_ID) as cipQty, " +

								" (SELECT count(b.AR_ID) FROM ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID) as arCount, " +

								" (SELECT COALESCE(SUM(b.INITIAL_COST),0) FROM ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID) as arValRate, " +

								" (SELECT count(b.FAR_ID) FROM FIXED_ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID) as farCount, " +

								" (SELECT COALESCE(SUM(b.INITIALCOST),0) FROM FIXED_ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID) as farIntCost, " +

								" (SELECT COALESCE(SUM(b.NETCOST),0) FROM FIXED_ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID) as farNetCost, " +

								" (SELECT count(b.FAR_ID) FROM FIXED_ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID and b.FAR_STATUS !='Running' ) as farNotRunCount, " +

								" (SELECT COALESCE(SUM(b.INITIALCOST),0) FROM FIXED_ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID and b.FAR_STATUS !='Running'  ) as farNotRunIntCost, " +

								" (SELECT COALESCE(SUM(b.NETCOST),0) FROM FIXED_ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID and b.FAR_STATUS !='Running' ) as farNotRunNetCost " +

								" FROM PURCHASE_ORDER t where t.PO_ID ='" + poId + "' ")
						.list();
				List<Object[]> CIP =null;
				CIP = (List<Object[]>) session.createNativeQuery("Select totalQTY, PO_ITEM_ID from "
						    + "CAPITAL_IN_PROGRESS WHERE PO_ID='" +poId+ "' ").list();
				float cipCost=0;
				if(CIP != null) {
					for (Object[] obj : CIP) {
					
					query= session.createNativeQuery("select Rate from purchase_order_item where PO_ITEM_ID='" +obj[1]+ "' ");
					Object rateObject = query.uniqueResult();
					 float rate = ((Number) rateObject).floatValue();
					 cipCost+=rate* Integer.parseInt(obj[0].toString());
					
				}
				}
				if (poList != null) {
					
					System.out.println("poList Size is " +poList.size());
					

					for (Object[] obj : poList) {

						if (obj[7] == null) {
							obj[7] = "-";
						}

						System.out.println("CIP Value " +obj[18]);
						
						rtn.put("goodsrCom", obj[1]);
						rtn.put("netGoodsrCom", obj[2]);
						rtn.put("totQtyGoodsrCom", obj[3]);

						rtn.put("goodsrNotCom", obj[4]);
						rtn.put("netGoodsrNotCom", obj[5]);
						rtn.put("totQtyGoodsrNotCom", obj[6]);

						rtn.put("PurchReqId", obj[7]);
						rtn.put("prStatus", obj[8]);
						rtn.put("prNetTot", obj[9]);
						rtn.put("prTotQty", obj[10]);

						rtn.put("dnComp", obj[11]);
						rtn.put("dnNetTotComp", obj[12]);
						rtn.put("dnTotQtyComp", obj[13]);

						rtn.put("dnNotComp", obj[14]);
						rtn.put("dnNetTotNotComp", obj[15]);
						rtn.put("dnTotQtyNotComp", obj[16]);

						rtn.put("cipCouuntAll", obj[17]);
						rtn.put("cipNeetTotAll", cipCost); // It is possible to use obj[18] and cipCost can be deleted as the query fixed.
						rtn.put("cipTootQtyAll", obj[19]);

						rtn.put("arCountAll", obj[20]);
						rtn.put("arValRateAll", obj[21]);

						rtn.put("farCount", obj[22]);
						rtn.put("faRCountAll", obj[23]);
						rtn.put("faRValRateAll", obj[24]);

						rtn.put("farNotRunCount", obj[25]);
						rtn.put("farNotRunIntCost", obj[26]);
						rtn.put("farNotRunNetCost", obj[27]);

					}
				}
			} catch (Exception e) {
				logger.info("Error in getting the PODATA with a message : " + e);

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