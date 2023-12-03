package com.aliat.alm.controller;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import com.aliat.alm.models.Cluster;
import com.aliat.alm.models.GoodsReceived;
import com.aliat.alm.models.GoodsReceivedBoq;
import com.aliat.alm.models.GoodsReceivedItem;
import com.aliat.alm.models.PurchaseOrder;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.models.SerialNumber;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class GoodsReceiveController {
	// for get fields from dict array
	private static final String GR_ITEMCODE = "itemCode";
	private static final String GR_ITEMMODEL = "itemModel";
	private static final String GR_ITEMPARTNO = "itemPartNo";
	private static final String GR_QTY = "qty";
	private static final String GR_RATE = "rate";
	private static final String GR_DISCOUNT_AMMOUNT = "discountAmount";
	private static final String GR_TAX = "tax1";
	private static final String GR_NETRATE = "netRate";
	private static final String GR_TOTAL = "total";
	private static final String GR_TOTAL_AT = "totalAt";
	private static final String GR_ITEM_ID = "grItemId";
	private static final String GR_STATUS = "grStatus";
	private static final String SERIAL_NUM = "serialNo";

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(GoodsReceiveController.class);
	private static Query query = null;
	private static String queryStatement = null;

	/**
	 * the above fields used in GoodsRcvFormSave
	 * 
	 * @param locale
	 * @param model
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */


	
	@Autowired
	Notify notifications;
	
	@Autowired
	Form form;
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GoodsRcvListView", method = RequestMethod.GET)
	public String GoodsRcvListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			List<GoodsReceived> listGoodsReceived = new ArrayList<GoodsReceived>();

			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				notifications.headerNotifications(session, model);
				try {

					listGoodsReceived = session.createQuery(
							"select 1,t.ID,TO_CHAR(t.lastmodifiedDate, 'YYYY-MM-DD HH24:MI:SS') , t.supplierName, t.TotalAmount,t.TotalQty,t.warehouse,t.siteId,t.warehouseName  from GoodsReceived t order by t.lastmodifiedDate DESC")
							.list();

					model.addAttribute("ListGridTable", mapper.writeValueAsString(listGoodsReceived));
				} catch (Exception e) {
					logger.info("Error in showing the list view of goods received with a message : " + e);
					model.addAttribute("ListGridTable", "");
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "GoodsRcvListView";
		}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredGoodsListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredGoodsListView(Locale locale, Model model, HttpServletRequest request,
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

				String startdate, enddate, supplier, warehouse;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				supplier = request.getParameter("Supplier");
				warehouse = request.getParameter("Warehouse");
				
		
				List<String> listGoods = new ArrayList<String>();

				String str = "select 1 as chkBox, GR_ID as ID,TO_CHAR(LAST_MODIFICATION_DATE,'YYYY-MM-DD HH24:MI:SS') as lastmodifiedDate ,  SUPPLIER_NAME as supplierName, TOTAL_AMOUNT as TotalAmount,"
						+ " TOTAL_QTY as TotalQty,WAREHOUSE as WareHouse, SITE_ID as siteID , WAREHOUSE_NAME as wareName from GOODS_RECEIVED  ";
		
				
				if (startdate != null && enddate != null) {
					str = str + " where CREATION_DATE between TO_DATE('" + startdate
							+ "','YYYY-MM-DD') and TO_DATE('" + enddate + "','YYYY-MM-DD')";
				}
				if (supplier != null && !supplier.equalsIgnoreCase("")) {

					str = str + " and (upper(SUPPLIER) LIKE upper('%" + supplier + "%') or upper(SUPPLIER_NAME) LIKE upper('%" + supplier + "%') )";
				}

				if (warehouse != null && !warehouse.equalsIgnoreCase("")) {

					str = str + " and (upper(WAREHOUSE) LIKE upper('%" + warehouse + "%')or upper(WAREHOUSE_NAME) LIKE upper('%" + warehouse + "%') or upper(SITE_ID) LIKE upper('%" + warehouse + "%')  )";
				}
				str = str + " ORDER BY LAST_MODIFICATION_DATE DESC ";

				Query query = session.createNativeQuery(str);
				

				listGoods = query.list();
				
			
				rtn.put("listGoods",listGoods);
		
			} catch (Exception e) {
				logger.info("Error in showing the filtered GoodsRcv  list view with a message :" + e);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PendingGoodsRcvListView", method = RequestMethod.GET)
	public String PendingGoodsRcvListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			List<GoodsReceived> listGoodsReceived = new ArrayList<GoodsReceived>();

			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				try {

					listGoodsReceived = session.createQuery(
							"select 1,t.ID, t.supplierID, t.TotalAmount,t.TotalQty,t.warehouse from GoodsReceived t where t.grStatus != 'completed' and t.grStatus != 'closed' and t.grStatus != 'deactivated' and t.grStatus != 'cancelled' and t.grStatus != 'activated' or t.grStatus is null")
							.list();

					model.addAttribute("ListGridTable", mapper.writeValueAsString(listGoodsReceived));
				} catch (Exception e) {
					logger.info("Error in showing the pending list view of goods received with a message : " + e);
					model.addAttribute("ListGridTable", "");
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			return "GoodsRcvListView";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GoodsRcvFormView", method = RequestMethod.GET)
	public String GoodsRcvFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {

			PurchaseRequest pRq;
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			String grid = request.getParameter("ID"),navAction = "2";
			String type = request.getParameter("type");
			String grId;
			GoodsReceived goodsRcve;
			PurchaseOrder pOrder;
	        String result [] =new String[4];
	        int SelectedIndex = 0;

			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);

				try {
					navAction= request.getParameter("NavAction");

					//////////////////////////////////////////////////////////////////////////

					
					if (request.getParameter("ID") == null) {
						model.addAttribute("navStatus", "addNew");
						model.addAttribute("SelectedIndex", "addNew");
						model.addAttribute("GoodsRcvCount", "addNew");

						if (request.getParameter("grPRQid") != null) {
							grId = request.getParameter("grPRQid");
							if (StringUtils.equalsAnyIgnoreCase(type, "addNewFromPRQ")) {

								
								pRq = (PurchaseRequest) session.get(PurchaseRequest.class, grId);

								model.addAttribute("grPRQid", grId);
								model.addAttribute("grPRQid", pRq.getPurchaseReqID());
								model.addAttribute("grsupplierId", pRq.getPrsupplierName());
								model.addAttribute("supplierName", pRq.getPrsuppname());
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
								model.addAttribute("discPercent", pRq.getPrdiscountPercent());
								model.addAttribute("paidAmount", pRq.getPrpaidAmount());
								model.addAttribute("grOutstanding", pRq.getPrOutstanding());
								model.addAttribute("grStatus", pRq.getPrStatus());
								model.addAttribute("Nettotal", pRq.getnTotal());
								model.addAttribute("NettotalinWord", pRq.getPrnettotalinWord());
								model.addAttribute("TotalQty", pRq.getPrtotalQty());
								model.addAttribute("docStatus", "addNewFromPRQ");

								// add data in table PurchasereqItem
								queryStatement = "select t.ItemCode as ItemCode ,t.ItemName as ItemName, t.ItemModel as ItemModel , t.ItemPartNb as ItemPartNumber , t.Qty as qty, t.Rate as rate ,t.DiscAmnt as discountAmount ,t.Tax1 as tax1, t.NetRate as netRate , t.Total as total , t.TotalAt as totalAt ,t.PqNo as poQty , t.GrNo as prQty , t.ArNo as arQty , t.CipNo as cipQty , t.FarNo as farQty, '0' as grItemId  from PurchaseRequestItem t where t.PRQId like :param1";

								query = session.createQuery(queryStatement);
								query.setParameter("param1", grId);
								List<GoodsReceivedBoq> listGoodsReceivedBoq = (List<GoodsReceivedBoq>) query
										.setResultTransformer(Transformers.aliasToBean(GoodsReceivedBoq.class)).list();

								model.addAttribute("ListPRqItem", mapper.writeValueAsString(listGoodsReceivedBoq));

								return "GoodsRcvFormView";

							}
						}

						else if (request.getParameter("grPOid") != null) {
							grId = request.getParameter("grPOid");
							if (StringUtils.equalsAnyIgnoreCase(type, "addNewFromPO")) {

								pOrder = (PurchaseOrder) session.get(PurchaseOrder.class, grId);

								model.addAttribute("grPOid", grId);
								model.addAttribute("grPRQid", pOrder.getOrdPRQid());
								model.addAttribute("grsupplierId", pOrder.getOrdsupplierId());
								model.addAttribute("supplierName", pOrder.getOrdSuppName());
								model.addAttribute("supplierAddress", pOrder.getOrdsupplierAddress());
								model.addAttribute("WareId", pOrder.getOrdwarehouse());
								model.addAttribute("WareName", pOrder.getWareName());
								model.addAttribute("SiteId", pOrder.getSiteID());
								model.addAttribute("creationDate",
										formatter.format(pOrder.getOrdcreationDate()).toString());
								model.addAttribute("lastmodifiedDate",
										formatter.format(pOrder.getOrdlastModifieddate()).toString());
								model.addAttribute("orderedDate",
										formatter.format(pOrder.getOrdorderedDate()).toString());
								model.addAttribute("delivereyDate",
										formatter.format(pOrder.getOrddeliveredDate()).toString());
								model.addAttribute("TotalAmount", pOrder.getOrdtotalAmount());
								model.addAttribute("discAmount", pOrder.getOrddiscountAmount());
								model.addAttribute("discPercent", pOrder.getOrddiscountPercent());
								model.addAttribute("paidAmount", pOrder.getOrdpaidAmount());
								model.addAttribute("grOutstanding", pOrder.getOrdOutstanding());
								model.addAttribute("grStatus", pOrder.getOrdStatus());
								model.addAttribute("Nettotal", pOrder.getOrdnettotal());
								model.addAttribute("NettotalinWord", pOrder.getOrdPonumber());
								model.addAttribute("TotalQty", pOrder.getOrdtotalQty());
								model.addAttribute("docStatus", "addNewFromPO");

								queryStatement ="SELECT t.Item_Code as itemCode, t.Item_Name as itemName, t.Item_Model as itemModel, t.item_part_number as itemPartNumber,\n"+
									   " t.Qty as qty, t.Rate as rate, t.discount_amount as discountAmount, t.Tax1 as tax1,\n"+
									   " t.Net_Rate as netRate, t.Total as total, t.Total_At as totalAt, t.GR_QTY as poQty, \n"+
									   " t.pr_qty as prQty, t.AR_QTY as arQty, t.CIP_QTY as cipQty, t.FAR_QTY as farQty, \n"+
									   " '0' as grItemId, x.serial_obj \n"+
									"FROM purchase_order_item t\n"+ 
									"LEFT JOIN (\n"+
									    "SELECT DISTINCT poitem_id,\n"+ 
									      "json_object('serialArray' value json_arrayagg(json_object('serial_no' value serial_number, 'itm_model' value item_model, 'itm_partno' value item_partno))) as serial_obj\n"+
									   "FROM serial_number \n"+
									   "GROUP BY POITEM_ID\n"+
									") x ON x.POITEM_ID = t.PO_ITEM_ID\n"+
									"WHERE t.PO_ID LIKE :param1";


								query = session.createSQLQuery(queryStatement);
								query.setParameter("param1", grId);

								List<GoodsReceivedBoq> listGoodsReceivedBoq = (List<GoodsReceivedBoq>) ((SQLQuery) query)
										.addScalar("itemCode").addScalar("itemName").addScalar("itemModel")
										.addScalar("itemPartNumber").addScalar("qty", new FloatType())
										.addScalar("rate", new FloatType()).addScalar("discountAmount", new FloatType())
										.addScalar("tax1", new FloatType()).addScalar("netRate", new FloatType())
										.addScalar("total", new FloatType()).addScalar("totalAt", new FloatType())
										.addScalar("poQty", new StringType()).addScalar("prQty", new StringType())
										.addScalar("arQty", new StringType()).addScalar("cipQty", new StringType())
										.addScalar("farQty", new StringType()).addScalar("grItemId", new StringType())
										.addScalar("serial_obj", new StringType())
										.setResultTransformer(Transformers.aliasToBean(GoodsReceivedBoq.class)).list();

								model.addAttribute("ListPRqItem", mapper.writeValueAsString(listGoodsReceivedBoq));

								return "GoodsRcvFormView";

							}
						}

						// to create new Goods Received from List View
						else {

							model.addAttribute("creationDate",
									formatter.format(new Timestamp(System.currentTimeMillis())).toString());
							model.addAttribute("lastmodifiedDate",
									formatter.format(new Timestamp(System.currentTimeMillis())).toString());
							model.addAttribute("docStatus", "addNew");
							model.addAttribute("ListPRqItem", "addNew");
							model.addAttribute("SelectedIndex", "addNew");
							model.addAttribute("GoodsRcvCount", "addNew");
							model.addAttribute("navStatus", "addNew");

							return "GoodsRcvFormView";
						}
					}

					result = form.NavigationNP(session,"GOODS_RECEIVED","GR_ID",grid,"LAST_MODIFICATION_DATE",navAction);		

					SelectedIndex= Integer.parseInt(result[1]);
					grid=result[2];
					//goodsRcve = (GoodsReceived) session.get(GoodsReceived.class, grid);

					model.addAttribute("SelectedIndex", SelectedIndex);
					model.addAttribute("GoodsRcvCount", Integer.parseInt(result[0]));

					goodsRcve = (GoodsReceived) session.get(GoodsReceived.class, grid);

					if (goodsRcve != null) {

						model.addAttribute("ID", goodsRcve.getGoodsReceiveID());
						model.addAttribute("creationDate", formatter.format(goodsRcve.getGrcreationDate()).toString());
						model.addAttribute("lastmodifiedDate",
								formatter.format(goodsRcve.getGrlastModifieddate()).toString());
						model.addAttribute("orderedDate", formatter.format(goodsRcve.getGrorderedDate()).toString());
						model.addAttribute("delivereyDate",
								formatter.format(goodsRcve.getGrdeliveredDate()).toString());
						model.addAttribute("grsupplierId", goodsRcve.getGrsupplierID());
						model.addAttribute("supplierName", goodsRcve.getGrsupplierName());
						model.addAttribute("supplierAddress", goodsRcve.getGrsupplierAddress());
						model.addAttribute("WareId", goodsRcve.getGrwarehouse());
						model.addAttribute("WareName", goodsRcve.getGrwarehouseName());
						model.addAttribute("SiteId", goodsRcve.getGrsiteId());
						model.addAttribute("TotalAmount", goodsRcve.getGrtotalAmount());
						model.addAttribute("discAmount", goodsRcve.getGrdiscountAmount());
						model.addAttribute("discPercent", goodsRcve.getGrdiscountPercent());
						model.addAttribute("paidAmount", goodsRcve.getGrpaidAmount());
						model.addAttribute("grOutstanding", goodsRcve.getGrOutstanding());
						model.addAttribute("grStatus", goodsRcve.getGrStatus());
						model.addAttribute("Nettotal", goodsRcve.getGrnettotal());
						model.addAttribute("NettotalinWord", goodsRcve.getGrnettotalinWord());
						model.addAttribute("TotalQty", goodsRcve.getGrtotalQty());
						model.addAttribute("grPOid", goodsRcve.getGrPOid());
						model.addAttribute("grPRQid", goodsRcve.getGrPRQid());

					}

					queryStatement = "select t.ITEM_CODE as itemCode, t.ITEM_NAME as itemName,t.ITEM_MODEL as itemModel,t.ITEM_PART_NUMBER as itemPartNumber,t.QTY as qty,t.RATE as rate,t.DISCOUNT_AMOUNT as discountAmount ,t.TAX1 as tax1, t.NET_RATE as netRate, t.TOTAL as total, t.TOTAL_AT as totalAt,  "
							+ "(select coalesce(sum(a.QTY),0) FROM PURCHASE_ORDER_ITEM a  "
							+ "INNER JOIN PURCHASE_ORDER b ON b.PO_ID= a.PO_ID  "
							+ "INNER JOIN GOODS_RECEIVED c ON c.PO_ID=b.PO_ID  "
							+ "WHERE c.GR_ID =t.GR_ID AND a.ITEM_CODE = t.ITEM_CODE) as poQty,  "
							+ "(select coalesce(sum(a.QTY),0) FROM PURCHASE_REQUEST_ITEM a  "
							+ "INNER JOIN PURCHASE_REQUEST b ON b.PRQ_ID= a.PRQ_ID  "
							+ "INNER JOIN GOODS_RECEIVED c ON c.PRQ_ID=b.PRQ_ID  "
							+ "WHERE c.GR_ID =t.GR_ID  AND a.ITEM_CODE = t.ITEM_CODE) as prQty,  "
							+ "(select count(a.AR_ID) FROM ASSET_REGISTRY a  "
							+ "INNER JOIN GOODS_RECEIVED b ON b.GR_ID=a.GR_ID  "
							+ "WHERE b.GR_ID =t.GR_ID AND a.ITEM_CODE = t.ITEM_CODE) as arQty,  "
							+ "(select a.TOTALQTY FROM CAPITAL_IN_PROGRESS a  "
							+ "INNER JOIN GOODS_RECEIVED b ON b.GR_ID=a.GR_ID  "
							+ "WHERE b.GR_ID =t.GR_ID AND a.ITEM_CODE = t.ITEM_CODE) as cipQty,  "
							+ "(select count(a.FAR_ID) FROM FIXED_ASSET_REGISTRY a  "
							+ "INNER JOIN GOODS_RECEIVED b ON b.GR_ID=a.GR_ID  "
							+ "WHERE b.GR_ID =t.GR_ID AND a.ITEM_CODE = t.ITEM_CODE) as farQty,  "
							+ "t.GR_RCV_ITEM_ID as grItemId, t.GR_STATUS as grStatus  , x.serial_obj "
							+ "FROM GOODS_RECEIVED_ITEM t "
							+ "LEFT JOIN (select distinct gritem_id,json_object('serialArray' value json_arrayagg(json_object('serial_no' value serial_number,'itm_model'  value item_model,'itm_partno' value item_partno))) as serial_obj "
							+ "from serial_number group by gritem_id) x on x.GRITEM_ID = t.gr_rcv_item_id "
							+ "where t.GR_ID like :param1 ";

					query = session.createSQLQuery(queryStatement);
					query.setParameter("param1", grid);

					List<GoodsReceivedBoq> listGoodsRcvBoq = (List<GoodsReceivedBoq>) ((SQLQuery) query)
							.addScalar("itemCode").addScalar("itemName").addScalar("itemModel")
							.addScalar("itemPartNumber").addScalar("qty", new FloatType())
							.addScalar("rate", new FloatType()).addScalar("discountAmount", new FloatType())
							.addScalar("tax1", new FloatType()).addScalar("netRate", new FloatType())
							.addScalar("total", new FloatType()).addScalar("totalAt", new FloatType())
							.addScalar("poQty", new StringType()).addScalar("prQty", new StringType())
							.addScalar("arQty", new StringType()).addScalar("cipQty", new StringType())
							.addScalar("farQty", new StringType()).addScalar("grItemId")
							.addScalar("grStatus", new StringType()).addScalar("serial_obj", new StringType())
							.setResultTransformer(Transformers.aliasToBean(GoodsReceivedBoq.class)).list();

					model.addAttribute("docStatus", "Existed");
					model.addAttribute("ListPRqItem", mapper.writeValueAsString(listGoodsRcvBoq));

				} catch (Exception e) {
					logger.info("Error in viewing the goods received form view with an error of :" + e);
					model.addAttribute("ID", "");
					model.addAttribute("creationDate", "");
					model.addAttribute("lastmodifiedDate", "");
					model.addAttribute("orderedDate", "");
					model.addAttribute("delivereyDate", "");
					model.addAttribute("grsupplierId", "");
					model.addAttribute("supplierName", "");
					model.addAttribute("supplierAddress", "");
					model.addAttribute("WareId", "");
					model.addAttribute("WareName", "");
					model.addAttribute("SiteId", "");
					model.addAttribute("TotalAmount", "");
					model.addAttribute("discAmount", "");
					model.addAttribute("discPercent", "");
					model.addAttribute("paidAmount", "");
					model.addAttribute("grOutstanding", "");
					model.addAttribute("grStatus", "");
					model.addAttribute("Nettotal", "");
					model.addAttribute("NettotalinWord", "");
					model.addAttribute("TotalQty", "");
					model.addAttribute("grPOid", "");
					model.addAttribute("grPRQid", "");
					model.addAttribute("docStatus", "Existed");
					model.addAttribute("ListPRqItem", "");
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}

			return "GoodsRcvFormView";
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/GoodsRcvFormSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> GoodsRcvFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {

			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				String grCancelFlag, grApproveFlag, supplierID, supplierName, supaddress, warehouse, warehouseName,
						siteID, totamnt, discamnt, discperc, paidamnt, grout, grstat;
				String grnettot, grnettotlw, totqty, grPOrderID, grPRqID, Dar, serialNum;
				String grid;
				String fullitemcode;
				String itmcode;
				String itmname;
				String griID;
				String PON, PRN, ARN, CIPN, FARN;

				Calendar calendar = new GregorianCalendar();
				calendar.setTime(new Date());
				int year = calendar.get(Calendar.YEAR);
				DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				Timestamp CreationDate;
				Timestamp lastModifiedDate;
				GoodsReceived goodRcv;
				tx = session.beginTransaction();
				try {

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
					// JavaMailUtil.SendccEmails(email,password,emailTo,ccmail,subject,message);
					// }

					goodRcv = new GoodsReceived();

					if (StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNew")
							|| StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNewFromPO")
							|| StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNewFromPRQ")) {
						
						synchronized (this) {						
							grid = "GR_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT GOOD_RECEIVED FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET GOOD_RECEIVED = GOOD_RECEIVED + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
						//grid = "GR_" + year + "_" + appConfig.getSequenceNbr(4);

						model.addAttribute("ID", grid);

					} else {
						grid = request.getParameter("ID");

					}
					grApproveFlag = request.getParameter("grApproveFlag");
					grCancelFlag = request.getParameter("grCancelFlag");
					CreationDate = new Timestamp(formatter.parse(request.getParameter("creationDate")).getTime());
					lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
					Timestamp orderedDate = new Timestamp(
							formatter.parse(request.getParameter("orderedDate")).getTime());
					Timestamp delivereyDate = new Timestamp(
							formatter.parse(request.getParameter("delivereyDate")).getTime());
					supplierID = request.getParameter("supplierID");
					supplierName = request.getParameter("supplierName");
					supaddress = request.getParameter("supplierAddress");
					warehouse = request.getParameter("warehouse");
					warehouseName = request.getParameter("warehouseName");
					siteID = request.getParameter("grsiteID");
					totamnt = request.getParameter("TotalAmount");
					discamnt = request.getParameter("discAmount");
					discperc = request.getParameter("discPercent");
					paidamnt = request.getParameter("paidAmount");
					grout = request.getParameter("grOutstanding");
					grstat = request.getParameter("grStatus");
					grnettot = request.getParameter("Nettotal");
					grnettotlw = request.getParameter("NettotalinWord");
					totqty = request.getParameter("TotalQty");
					grPOrderID = request.getParameter("grPOid");
					grPRqID = request.getParameter("grPREQid");
					Dar = request.getParameter("DarNo");

					goodRcv.setGoodsReceiveID(grid);
					goodRcv.setGrcreationDate(CreationDate);
					goodRcv.setGrlastModifieddate(lastModifiedDate);
					goodRcv.setGrsupplierID(supplierID);
					goodRcv.setGrsupplierName(supplierName);
					goodRcv.setGrsupplierAddress(supaddress);
					goodRcv.setGrorderedDate(orderedDate);
					goodRcv.setGrdeliveredDate(delivereyDate);
					goodRcv.setGrwarehouse(warehouse);
					goodRcv.setGrwarehouseName(warehouseName);
					goodRcv.setGrsiteId(siteID);
					goodRcv.setGrtotalAmount(Float.parseFloat(totamnt));
					goodRcv.setGrdiscountAmount(Float.parseFloat(discamnt));
					goodRcv.setGrdiscountPercent(Float.parseFloat(discperc));
					goodRcv.setGrpaidAmount(Float.parseFloat(paidamnt));
					goodRcv.setGrOutstanding(Float.parseFloat(grout));
					goodRcv.setGrStatus(grstat);
					goodRcv.setGrnettotal(Float.parseFloat(grnettot));
					goodRcv.setGrnettotalinWord(grnettotlw);
					goodRcv.setGrtotalQty(Float.parseFloat(totqty));
					goodRcv.setGrPOid(grPOrderID);
					goodRcv.setGrPRQid(grPRqID);

					session.saveOrUpdate(goodRcv);

					
					/*query = session.createSQLQuery(
							"DELETE from SERIAL_NUMBER a where a.gritem_id in (select b.GR_RCV_ITEM_ID from GOODS_RECEIVED_ITEM b where b.GR_ID = :param1)");
					query.setString("param1", grid);
					query.executeUpdate();*/

					//Remove the deleted serials in js
					String[] allDeletedSerialsArray = request.getParameterValues("allDeletedSerialsArray[]");
					if (allDeletedSerialsArray != null) {
						query = session.createQuery("delete from SerialNumber t where t.serialNumber IN (:param)");
						query.setParameterList("param", allDeletedSerialsArray);
						query.executeUpdate();
					}
					

					////////////// checking data for email notification in GR ///////////////////
					// String[] formSave= request.getParameterValues("FormSave[]");//to get the
					////////////// parameters from the FormView (whether it is New,Approve,Cancel)
					// for(String f: formSave)
					// { if(f != null && f.length() > 0)
					// System.out.println("the value of formSave f = "+f);
					// EmailCondition thread= new EmailCondition("GoodsReceived", f,grid);//if
					////////////// moduleName=Purchase Request and SendAlertOn=formSave are found in
					////////////// DB then it will send an email
					// thread.start();
					// }
					///////////////////////////////////////////////////////////////////////////////////////////////

					String[] slctDelGds = request.getParameterValues("slctDelGds[]");
					if (slctDelGds != null) {
						queryStatement = "delete GoodsReceivedItem t where t.grItemId IN (:param1)";
						query = session.createQuery(queryStatement);
						query.setParameterList("param1", slctDelGds);
						query.executeUpdate();
					}

					if (itemParameters.getDictParameter().size() != 0) {

						GoodsReceivedItem goodsRcvItem;

						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {

							goodsRcvItem = new GoodsReceivedItem();

							fullitemcode = itemParameters.getDictParameter().get(i).get(GR_ITEMCODE);
							itmcode = fullitemcode.substring(0, fullitemcode.indexOf(":"));
							itmname = fullitemcode.substring(fullitemcode.indexOf(":") + 1, fullitemcode.length());

							if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get(GR_ITEM_ID),
									"0")) {
								synchronized (this) {						
									griID = "GRI_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT GR_ITEM FROM SEQ_TABLE").uniqueResult().toString());	
									query = session.createSQLQuery("UPDATE SEQ_TABLE SET GR_ITEM = GR_ITEM + 1 ");
									query.executeUpdate();
									session.createSQLQuery("commit").executeUpdate();
									}
								//griID = "GRI_" + year + "_" + appConfig.getSequenceNbr(5);

							} else {
								griID = itemParameters.getDictParameter().get(i).get(GR_ITEM_ID);

							}
							String GrStatus;
							if(StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNewFromPO")
							|| StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNewFromPRQ")) {
							 GrStatus="0";
							}
							else {
								GrStatus=itemParameters.getDictParameter().get(i).get(GR_STATUS);
							}

							goodsRcvItem.setGrItemId(griID);
							goodsRcvItem.setGriCreationDate(new Timestamp(System.currentTimeMillis()));
							goodsRcvItem.setGriLastMOdifiedDate(new Timestamp(System.currentTimeMillis()));
							goodsRcvItem.setGrdiItemCode(itmcode);
							goodsRcvItem.setGrdiItemName(itmname);
							goodsRcvItem.setGrdiItemModel(itemParameters.getDictParameter().get(i).get(GR_ITEMMODEL));
							goodsRcvItem
									.setGrdiItemPartNumber(itemParameters.getDictParameter().get(i).get(GR_ITEMPARTNO));
							goodsRcvItem.setGriRate(
									Float.parseFloat(itemParameters.getDictParameter().get(i).get(GR_RATE)));
							goodsRcvItem.setGriDiscountAmount(Float
									.parseFloat(itemParameters.getDictParameter().get(i).get(GR_DISCOUNT_AMMOUNT)));
							goodsRcvItem.setGriDiscountPercent(0);
							goodsRcvItem.setGriNetRate(
									Float.parseFloat(itemParameters.getDictParameter().get(i).get(GR_NETRATE)));
							goodsRcvItem
									.setGriTax1(Float.parseFloat(itemParameters.getDictParameter().get(i).get(GR_TAX)));
							goodsRcvItem.setGriTax2(0);
							goodsRcvItem.setGriTotal(
									Float.parseFloat(itemParameters.getDictParameter().get(i).get(GR_TOTAL)));
							goodsRcvItem.setGriTotalAt(
									Float.parseFloat(itemParameters.getDictParameter().get(i).get(GR_TOTAL_AT)));
							goodsRcvItem
									.setGriQty(Float.parseFloat(itemParameters.getDictParameter().get(i).get(GR_QTY)));
							System.out.println(GrStatus);
							goodsRcvItem.setGrStatus(GrStatus);
							
							goodsRcvItem.setGriGRId(grid);
							goodsRcvItem.setGriDarNo(Dar);

							PON = goodsRcvItem.getGriPoNo();
							PRN = goodsRcvItem.getGriPrNo();
							ARN = goodsRcvItem.getGriArNo();
							CIPN = goodsRcvItem.getGriCIPNo();
							FARN = goodsRcvItem.getGriFarNo();

							if (PON == null)
								goodsRcvItem.setGriPoNo("0");
							else
								goodsRcvItem.setGriPoNo(PON);

							if (ARN == null)
								goodsRcvItem.setGriArNo("0");
							else
								goodsRcvItem.setGriArNo(ARN);
							if (PRN == null)
								goodsRcvItem.setGriPrNo("0");
							else
								goodsRcvItem.setGriPrNo(PRN);

							if (CIPN == null)
								goodsRcvItem.setGriCIPNo("0");
							else
								goodsRcvItem.setGriCIPNo(CIPN);

							if (FARN == null)
								goodsRcvItem.setGriFarNo("0");
							else
								goodsRcvItem.setGriFarNo(FARN);

							session.saveOrUpdate(goodsRcvItem);
							
							// Save serial number
							serialNum = itemParameters.getDictParameter().get(i).get(SERIAL_NUM);
							if (!StringUtils.equalsIgnoreCase(serialNum, null) && !serialNum.isEmpty()) {
								SerialNumber serialnum;
								ArrayList serialArrayList;
								Object serialObj = new JSONParser().parse(serialNum);
								JSONArray serialJSNArray = (JSONArray) ((HashMap) serialObj).get("serialArray");
								
								SerialNumber serNum = null;
								String poID="",poItemID="",serialNumID="",serialNumStatus="";

								for (Object serialJSN : (JSONArray) serialJSNArray) {
									serialArrayList = new ArrayList((((HashMap) serialJSN).values()));								
									
									//Get purchase ID,purchase item ID
									serNum = (SerialNumber) session.get(SerialNumber.class, (String) serialArrayList.get(1));
									poID="";poItemID="";serialNumStatus="";serialNumID="";
									
								    if (serNum != null) {
										if (!StringUtils.equalsIgnoreCase(serNum.getSerialNumber(), null)) {
											 poID = serNum.getPoId();
											 poItemID = serNum.getPoItemId();
											 serialNumID=serNum.getSerialNumID();
											 serialNumStatus=serNum.getOrdStatus();
										}
										 session.evict(serNum);
									}
								    if (StringUtils.equalsIgnoreCase(serialNumID, null) || StringUtils.equalsIgnoreCase(serialNumID, "")) {
										synchronized (this) {					
											serialNumID = "SER_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT SERIAL_NUM FROM SEQ_TABLE").uniqueResult().toString());	
											query = session.createSQLQuery("UPDATE SEQ_TABLE SET SERIAL_NUM = SERIAL_NUM + 1 ");
											query.executeUpdate();
											session.createSQLQuery("commit").executeUpdate();
										}
									}
									serialnum = new SerialNumber();
									serialnum.setCreationDate(CreationDate);
									serialnum.setLastModifieddate(lastModifiedDate);
									serialnum.setSupplierId(supplierID);
									serialnum.setSupplierName(supplierName);
									serialnum.setItemCode(itmcode);
									serialnum.setItemName(itmname);

									if (StringUtils.equalsIgnoreCase(poID, null) || StringUtils.equalsIgnoreCase(poID, "")) {
										serialnum.setPoId("0");
									}
									else {
										serialnum.setPoId(poID);
									}
									serialnum.setGrId(grid);
									serialnum.setSerialNumber((String) serialArrayList.get(1));
									serialnum.setItemModel((String) serialArrayList.get(2));
									serialnum.setItemPart((String) serialArrayList.get(0));
									
									if (StringUtils.equalsIgnoreCase(poItemID, null) || StringUtils.equalsIgnoreCase(poItemID, "")) {
										serialnum.setPoItemId("0");
									}
									else {
										serialnum.setPoItemId(poItemID);
									}
									serialnum.setGrItemId(griID);
									if (StringUtils.equalsIgnoreCase(serialNumStatus, null) || StringUtils.equalsIgnoreCase(serialNumStatus, "")) {
										serialnum.setOrdStatus("draft");
									}
									else {
										serialnum.setOrdStatus(serialNumStatus);
									}
									serialnum.setSerialNumID(serialNumID);
									session.saveOrUpdate(serialnum);
									session.flush();
								}

							}
						}
						if (StringUtils.equalsIgnoreCase(grApproveFlag, "1") && StringUtils.equalsIgnoreCase(grCancelFlag, "0")) { // && StringUtils.equalsIgnoreCase(grstat, "completed")
							AssetLifeCycleThread InsertionALCThread;

							if(grPOrderID.isEmpty()) {
								if(grPRqID.isEmpty() ) {
									rtn.put("RevPrStatAprv", "accepted");
									query = session.createQuery(
											"update GoodsReceived set grStatus = 'completed' WHERE ID = :param1");
									query.setString("param1", grid);
									query.executeUpdate();
									InsertionALCThread = new AssetLifeCycleThread("GR", grPRqID,
											grPOrderID, grid, itemParameters);
									InsertionALCThread.start(); // will occur parallel with the execution of saving GR.
									
									
								} 
								else {
								query = session.createQuery("SELECT prStatus FROM PurchaseRequest WHERE ID = :param1");
								query.setString("param1", grPRqID);
								String processStat = query.uniqueResult().toString();
								if (StringUtils.equalsIgnoreCase(processStat, "inprog")
										|| StringUtils.equalsIgnoreCase(processStat, "cancelled")) {
									rtn.put("RevPrStatAprv", "rejected");
									rtn.put("PRtobeAprvd", grPRqID);
								}
								else {
									rtn.put("RevPrStatAprv", "accepted");
									query = session.createQuery(
											"update GoodsReceived set grStatus = 'completed' WHERE ID = :param1");
									query.setString("param1", grid);
									query.executeUpdate();
									InsertionALCThread = new AssetLifeCycleThread("GR", grPRqID,
											grPOrderID, grid, itemParameters);
									InsertionALCThread.start(); // will occur parallel with the execution of saving GR.
								}
								}
							}
							else {
								query = session.createQuery("SELECT ordStatus FROM PurchaseOrder WHERE ID = :param1");
								query.setString("param1", grPOrderID);
								String processStat = query.uniqueResult().toString();
								if (!StringUtils.equalsIgnoreCase(processStat, "approved")
										&& !StringUtils.equalsIgnoreCase(processStat, "completed")
										&& !StringUtils.equalsIgnoreCase(processStat, "closed")) {
									rtn.put("RevPoStatAprv", "rejected");
									rtn.put("POtobeAprvd", grPOrderID);
								}
								else {
									rtn.put("RevPoStatAprv", "accepted");
									query = session.createQuery(
											"update GoodsReceived set grStatus = 'completed' WHERE ID = :param1");
									query.setString("param1", grid);
									query.executeUpdate();
									InsertionALCThread = new AssetLifeCycleThread("GR", grPRqID,
											grPOrderID, grid, itemParameters);
									InsertionALCThread.start(); // will occur parallel with the execution of saving GR.
								}
							
							}
						} else if (StringUtils.equalsIgnoreCase(grCancelFlag, "1")
								&& StringUtils.equalsIgnoreCase(grApproveFlag, "0")) {	
							AssetLifeCycleCanceling aslcc = new AssetLifeCycleCanceling("GR", grPRqID, grPOrderID, grid);
							aslcc.start();
							queryStatement = "update GoodsReceivedItem set grStatus = :param_1  where GRid = :param_2";
							query = session.createQuery(queryStatement);
							query.setString("param_1", "0");
							query.setString("param_2", grid);
							query.executeUpdate();
							query = session.createQuery(
									"update GoodsReceived set grStatus = 'cancelled' WHERE ID = :param1");
							query.setString("param1", grid);
							query.executeUpdate();
						}
					}

					rtn.put("GRID", grid);

				} catch (Exception e) {
					e.printStackTrace();
					logger.info("Error in saving the goods received with a message : " + e);
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

	@RequestMapping(value = "/DeleteGoodsReceive", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GoodsRcvListViewDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;

		} else {
			String[] idList = request.getParameterValues("ID[]");
			if (idList != null && idList.length != 0) {
				session = AlmDbSession.getInstance().getSession();
				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();

					try {

						queryStatement = "delete GoodsReceived t  where t.ID IN (:param1)";
						query = session.createQuery(queryStatement);
						query.setParameterList("param1", idList);
						query.executeUpdate();

						queryStatement = "delete GoodsReceivedItem   where GRid IN (:param1)";
						query = session.createQuery(queryStatement);
						query.setParameterList("param1", idList);
						query.executeUpdate();

						queryStatement = "delete AssetLifeCycle   where voucherNB IN (:param1)";
						query = session.createQuery(queryStatement);
						query.setParameterList("param1", idList);
						query.executeUpdate();

					} catch (Exception e) {

						logger.info("Error in deleting on the level of goods received with a message : " + e);
					}

					finally {

						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
						}
					}
				}
			}
			return rtn;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetGRDATA", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetGRDATA(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String GrId = request.getParameter("ID");
		List<Object[]> grList;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			try {
				grList = (List<Object[]>) session.createSQLQuery(

						"select t.GR_ID, " +

								" (SELECT b.PRQ_ID FROM PURCHASE_REQUEST b " + "  WHERE b.PRQ_ID =t.PRQ_ID) as preqId, "
								+

								" (SELECT  COALESCE(b.STATUS,' ') FROM PURCHASE_REQUEST b "
								+ " WHERE b.PRQ_ID =t.PRQ_ID) as preqStatus, " +

								" (SELECT COALESCE(SUM(b.TOTAL_QTY),0) FROM PURCHASE_REQUEST b "
								+ " WHERE b.PRQ_ID =t.PRQ_ID) as preqQty, " +

								" (SELECT COALESCE(SUM(b.NET_TOTAL_AMOUNT),0) FROM PURCHASE_REQUEST b "
								+ " WHERE b.PRQ_ID =t.PRQ_ID ) as preqNetAmount, " +

								" (SELECT b.PO_ID FROM PURCHASE_ORDER b " + " WHERE b.PO_ID =t.PO_ID ) as poId, " +

								" (SELECT b.STATUS FROM PURCHASE_ORDER b" + " WHERE b.PO_ID =t.PO_ID ) as poStatus, " +

								" (SELECT COALESCE(SUM(b.NET_TOTAL_AMOUNT),0)  FROM PURCHASE_ORDER b "
								+ " WHERE b.PO_ID =t.PO_ID ) as poNetAmount, " +

								" (SELECT COALESCE(SUM(b.TOTAL_QTY),0)  FROM PURCHASE_ORDER b "
								+ " WHERE b.PO_ID =t.PO_ID  ) as poQty, " +

								" (SELECT count(DISTINCT a.DN_ID) FROM DISCOVERY_NEW_ITEM a  "
								+ " INNER JOIN PURCHASE_ORDER b on b.PO_ID = a.PO_ID"
								+ " WHERE b.PO_ID = t.PO_ID ) as dnCount, " +

								" (SELECT COALESCE(SUM(a.QTY),0) FROM DISCOVERY_NEW_ITEM a  "
								+ " INNER JOIN PURCHASE_ORDER b on b.PO_ID = a.PO_ID"
								+ " WHERE b.PO_ID = t.PO_ID ) as dnQty, " +

								" (SELECT COALESCE(SUM(a.TOTAL_AMOUNT),0) FROM DISCOVERY_NEW_ITEM a  "
								+ " INNER JOIN PURCHASE_ORDER b on b.PO_ID = a.PO_ID"
								+ " WHERE b.PO_ID = t.PO_ID ) as dnTotalAmount, " +

								" (SELECT count(DISTINCT a.DN_ID) FROM DISCOVERY_NEW_ITEM a  "
								+ " INNER JOIN PURCHASE_ORDER b on b.PO_ID = a.PO_ID"
								+ " INNER JOIN DISCOVERY_NEW c ON a.DN_ID = c.DN_ID"
								+ " WHERE b.PO_ID = t.PO_ID and c.STATUS !='completed' and c.STATUS !='closed' ) as dnNotCompCount, "
								+

								" (SELECT COALESCE(SUM(a.QTY),0) FROM DISCOVERY_NEW_ITEM a  "
								+ " INNER JOIN PURCHASE_ORDER b on b.PO_ID = a.PO_ID"
								+ " INNER JOIN DISCOVERY_NEW c ON a.DN_ID = c.DN_ID"
								+ " WHERE b.PO_ID = t.PO_ID and c.STATUS !='completed' and c.STATUS !='closed' ) as dnNotCompQty, "
								+

								" (SELECT COALESCE(SUM(a.TOTAL_AMOUNT),0) FROM DISCOVERY_NEW_ITEM a  "
								+ " INNER JOIN PURCHASE_ORDER b on b.PO_ID = a.PO_ID"
								+ " INNER JOIN DISCOVERY_NEW c ON a.DN_ID = c.DN_ID"
								+ " WHERE b.PO_ID = t.PO_ID and c.STATUS !='completed' and c.STATUS !='closed' ) as dnNotCompTotAmount, "
								+

								" (SELECT count(b.CIP_ID) FROM CAPITAL_IN_PROGRESS b "
								+ "  WHERE b.PO_ID =t.PO_ID ) as cipCount, " +

								" (SELECT COALESCE(SUM(b.VALUATION_RATE),0) FROM CAPITAL_IN_PROGRESS b "
								+ "  WHERE b.PO_ID =t.PO_ID ) as cipValRate, " +

								" (SELECT COALESCE(SUM(b.TOTALQTY),0) FROM CAPITAL_IN_PROGRESS b "
								+ "  WHERE b.PO_ID =t.PO_ID ) as cipQty, " +

								" (SELECT count(b.AR_ID) FROM ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID  ) as arCount, " +

								" (SELECT COALESCE(SUM(b.VALUATION_RATE),0) FROM ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID  ) as arValRate, " +

								" (SELECT count(b.FAR_ID) FROM FIXED_ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID  ) as farCount, " +

								" (SELECT COALESCE(SUM(b.INITIALCOST),0) FROM FIXED_ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID  ) as farIntCost, " +

								" (SELECT COALESCE(SUM(b.NETCOST),0) FROM FIXED_ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID  ) as farNetCost, " +

								" (SELECT count(b.FAR_ID) FROM FIXED_ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID and b.FAR_STATUS !='running' ) as farNotRunCount, " +

								" (SELECT COALESCE(SUM(b.INITIALCOST),0) FROM FIXED_ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID and b.FAR_STATUS !='running'  ) as farNotRunIntCost, " +

								" (SELECT COALESCE(SUM(b.NETCOST),0) FROM FIXED_ASSET_REGISTRY b "
								+ " WHERE b.PO_ID =t.PO_ID and b.FAR_STATUS !='running' ) as farNotRunNetCost " +

								" FROM GOODS_RECEIVED t WHERE t.GR_ID ='" + GrId + "' ")
						.list();

				if (grList != null) {

					for (Object[] obj : grList) {

						if (obj[1] == null) {
							obj[1] = "No Purchase Request ";
						}
						if (obj[5] == null) {
							obj[5] = "No Purchase Order";
						}

						rtn.put("grPurchReqId", obj[1]);
						rtn.put("grPrStatus", obj[2]);
						rtn.put("grPrNetTot", obj[4]);
						rtn.put("grPrTotQty", obj[3]);

						rtn.put("grPurchOrdId", obj[5]);
						rtn.put("grPoStatus", obj[6]);
						rtn.put("grOrdtotQty", obj[8]);
						rtn.put("grOrdnetTot", obj[7]);

						rtn.put("dnComp", obj[9]);
						rtn.put("dnTotQtyComp", obj[10]);
						rtn.put("dnNetTotComp", obj[11]);

						rtn.put("dnNotComp", obj[12]);
						rtn.put("dnNetTotNotComp", obj[14]);
						rtn.put("dnTotQtyNotComp", obj[13]);

						rtn.put("grCipCountAll", obj[15]);
						rtn.put("grCipNetTotAll", obj[16]);
						rtn.put("grCipTotQtyAll", obj[17]);

						rtn.put("grArCountAll", obj[18]);
						rtn.put("grArValRateAll", obj[19]);

						rtn.put("grFarCountAll", obj[21]);
						rtn.put("grFarValRateAll", obj[22]);
						rtn.put("farCount", obj[20]);

						rtn.put("farNotRunCount", obj[23]);
						rtn.put("farNotRunIntCost", obj[24]);
						rtn.put("farNotRunNetCost", obj[25]);

					}

				}
			}

			catch (Exception e) {
				logger.info("Error in viewing the data in the over view form with a message of : " + e);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		return rtn;

	}
	
	
	@RequestMapping(value = "/GetAllGdRcvs", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllClusters(Locale locale, Model model, HttpServletRequest request) {
		
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		String gdrcv = "%" + request.getParameter("gdrcv") + "%";

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			
	try {


		
		query = session.createQuery("select t.ID, t.supplierName from GoodsReceived t where t.ID like :param1 or t.supplierName like :param1 ORDER BY lastmodifiedDate DESC");
		query.setString("param1", gdrcv);
		rtn.put("listGdRcvs", query.list());

	
	}catch(Exception e) {
		
		System.out.println("catch messsage is "+e.getMessage());
		
	}
	finally {

	      if (session != null && session.isOpen()) {
		      session.close();
		      
	      }

	 }
		}
		
		return rtn;
	}
	


	
}