package com.aliat.alm.controller;

import java.io.IOException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
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

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.FarPartNumber;
import com.aliat.alm.models.FarSerialNumber;
import com.aliat.alm.models.FarSite;
import com.aliat.alm.models.FixedAssetRegisterListView;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.models.Item;
import com.aliat.alm.models.PurchaseOrderItem;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aliat.alm.models.FarNode;

@Controller
public class FixedAssetRegistryController {

	

	@Autowired
	Notify notifications;

	@Autowired
	Form form;

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static final Logger logger = LoggerFactory.getLogger(FixedAssetRegistryController.class);

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@RequestMapping(value = "/FixedAssetRegistryListView", method = RequestMethod.GET)
	public String FixedAssetRegistryListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}

		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {

				List<FixedAssetRegisterListView> listFAR = new ArrayList<FixedAssetRegisterListView>();

				/*
				 * String str =
				 * "select FAR_ID as farID, FAR_ID as fixedassetID, ITEM_CODE as itemCode, ITEM_NAME as itemName, "
				 * +
				 * "TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate, ITEM_SN as itemSN, ITEM_NAME_REGISTER as itemNameRegister, PO_ID as poID, "
				 * + "COALESCE(NODE_ID, ' ') as nodeID, COALESCE(NODE_NAME, ' ') as nodeName " +
				 * "from FIXED_ASSET_REGISTRY order by LAST_MODIFIED_DATE DESC";
				 */

				String str = "SELECT farID, fixedassetID, itemCode, itemName, lastModifiedDate,itemSN,itemNameRegister,poID,nodeID,nodeName,siteID,siteName FROM ( "
						+ " SELECT A.FAR_ID as farID, A.FAR_ID as fixedassetID, A.ITEM_CODE as itemCode, A.ITEM_NAME as itemName,TO_CHAR(A.LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate, A.ITEM_SN as itemSN,A.ITEM_NAME_REGISTER as itemNameRegister, A.PO_ID as poID,COALESCE(A.NODE_ID, ' ') as nodeID, COALESCE(A.NODE_NAME, ' ') as nodeName, B.SITE_ID AS siteID, B.SITE_NAME AS siteName , "
						+ " ROW_NUMBER() OVER (PARTITION BY A.FAR_ID ORDER BY B.SITE_ID DESC) AS rn FROM FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID ) WHERE rn = 1 ORDER BY lastModifiedDate DESC";

				Query query = session.createNativeQuery(str);

				listFAR = ((SQLQuery) query).addScalar("farID").addScalar("fixedassetID").addScalar("itemCode")
						.addScalar("itemName").addScalar("lastModifiedDate").addScalar("itemSN")
						.addScalar("itemNameRegister").addScalar("poID").addScalar("nodeID").addScalar("nodeName")
						.addScalar("siteID").addScalar("siteName")
						.setResultTransformer(Transformers.aliasToBean(FixedAssetRegisterListView.class)).list();

				Query q = session.createNativeQuery(
						"Select SUM(INITIALCOST) as initialCost, SUM(ACCUMULDEPRECAMNT) as AccumDepr, SUM(NETCOST) as netCost from fixed_asset_registry t");
				Object[] result = (Object[]) q.uniqueResult();

				DecimalFormat df = new DecimalFormat("#,###.00");
				String totalInitialCost = "0.0";
				String totalAccumdepr = "0.0";
				String totalNetCost = "0.0";

				if (result[0] != null)
					totalInitialCost = df.format(new BigDecimal(result[0].toString()));
				if (result[1] != null)
					totalAccumdepr = df.format(new BigDecimal(result[1].toString()));
				if (result[2] != null)
					totalNetCost = df.format(new BigDecimal(result[2].toString()));

				model.addAttribute("totalInitialCost", totalInitialCost);
				model.addAttribute("totalAccumdepr", totalAccumdepr);
				model.addAttribute("totalNetCost", totalNetCost);
				model.addAttribute("ListGridTable", mapper.writeValueAsString(listFAR));

			} catch (Exception e) {
				logger.info("Error at Fixed Asset Register ListView with a message: " + e);
				e.printStackTrace();
				model.addAttribute("ListGridTable", "");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}

		return "FixedAssetRegistryListView";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredFixedAssetListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredFixedAssetListView(Locale locale, Model model, HttpServletRequest request,
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

				String startdate, enddate, itemcode, itemname, itemmodel;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				itemcode = request.getParameter("itemcode");
				itemname = request.getParameter("itemname");
				itemmodel = request.getParameter("itemmodel");

				String startLong = request.getParameter("startLong");
				String startLat = request.getParameter("startLat");
				String endLong = request.getParameter("endLong");
				String endLat = request.getParameter("endLat");
				String longitude = request.getParameter("longitude");
				String latitude = request.getParameter("latitude");
				String radius = request.getParameter("radius");
				double distance = 0.0;

				List<Object[]> listCircleRange = new ArrayList<Object[]>();
				List<Object[]> listFixed = new ArrayList<Object[]>();

				// This list is used in circle range search to get the longitude and latitude in
				// order to calculate the distance
				// (We can't select the long and lat in the main list listAsset because no need
				// for long and lat in appending the grid)
				List<Object[]> listFixedAssetTemp = new ArrayList<Object[]>();

				// List<String> listFixed = new ArrayList<String>();
				/*
				 * String str1 =
				 * "select 1 as chkBox, FAR_ID as farID, ITEM_CODE as itemCode, ITEM_NAME as itemName,"
				 * +" TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate, ITEM_SN as itemSN, ITEM_NAME_REGISTER as itemNameRegister, PO_ID as poID,"
				 * + "COALESCE(NODE_ID, ' ') as nodeID, COALESCE(NODE_NAME, ' ') as nodeName  "
				 * +"from FIXED_ASSET_REGISTRY " ;
				 */

				String str = "SELECT chkBox,farID,itemCode,itemName,lastModifiedDate,itemSN,itemNameRegister,poID,nodeID,nodeName,siteID,siteName FROM ( SELECT 1 as chkBox, A.FAR_ID as farID, A.ITEM_CODE as itemCode, A.ITEM_NAME as itemName, "
						+ "TO_CHAR(A.LAST_MODIFIED_DATE, 'YYYY-MM-DD HH24:MI:SS') AS lastModifiedDate, A.ITEM_SN as itemSN, A.ITEM_NAME_REGISTER as itemNameRegister, A.PO_ID as poID, "
						+ "COALESCE(A.NODE_ID, ' ') AS nodeID, COALESCE(A.NODE_NAME, ' ') AS nodeName, B.SITE_ID as siteID, B.SITE_NAME as siteName, "
						+ "ROW_NUMBER() OVER (PARTITION BY A.FAR_ID ORDER BY B.SITE_ID DESC) AS rn "
						+ "FROM FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID ";

				String strTemp = "SELECT * FROM ( SELECT 1 as chkBox, A.FAR_ID as farID, A.ITEM_CODE as itemCode, A.ITEM_NAME as itemName, "
						+ "TO_CHAR(A.LAST_MODIFIED_DATE, 'YYYY-MM-DD HH24:MI:SS') AS lastModifiedDate, A.ITEM_SN as itemSN, A.ITEM_NAME_REGISTER as itemNameRegister, A.PO_ID as poID, "
						+ "COALESCE(A.NODE_ID, ' ') AS nodeID, COALESCE(A.NODE_NAME, ' ') AS nodeName, B.SITE_ID as siteID, B.SITE_NAME as siteName,C.LONGITUDE as longitude,C.LATITUDE as latitude, "
						+ "ROW_NUMBER() OVER (PARTITION BY A.FAR_ID ORDER BY B.SITE_ID DESC) AS rn "
						+ "FROM FIXED_ASSET_REGISTRY A LEFT JOIN FAR_SITE B ON B.FAR_ID = A.FAR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID ";

				if (startdate != null && enddate != null) {
					str = str + " where A.CREATED_DATE between TO_DATE('" + startdate + "','YYYY-MM-DD') and TO_DATE('"
							+ enddate + "','YYYY-MM-DD')";

					strTemp = strTemp + " where A.CREATED_DATE between TO_DATE('" + startdate
							+ "','YYYY-MM-DD') and TO_DATE('" + enddate + "','YYYY-MM-DD')";
				}
				if (itemcode != null && !itemcode.equalsIgnoreCase("")) {

					str = str + " and (upper(A.ITEM_CODE) LIKE upper('%" + itemcode + "%'))";
					strTemp = strTemp + " and (upper(A.ITEM_CODE) LIKE upper('%" + itemcode + "%'))";
				}

				if (itemname != null && !itemname.equalsIgnoreCase("")) {
					str = str + " and (upper(A.ITEM_NAME) LIKE upper('%" + itemname + "%') )";
					strTemp = strTemp + " and (upper(A.ITEM_NAME) LIKE upper('%" + itemname + "%') )";
				}
				if (itemmodel != null && !itemmodel.equalsIgnoreCase("")) {
					str = str + " and (upper(A.ITEM_MODEL) LIKE upper('%" + itemmodel + "%'))";
					strTemp = strTemp + " and (upper(A.ITEM_MODEL) LIKE upper('%" + itemmodel + "%'))";
				}
				if (startLong != null && !startLong.equalsIgnoreCase("")
						&& (endLong == null || endLong.equalsIgnoreCase(""))) {
					str = str + " and (TO_NUMBER(C.LONGITUDE)) > " + startLong;
					strTemp = strTemp + " and (TO_NUMBER(C.LONGITUDE)) > " + startLong;
				}
				if (endLong != null && !endLong.equalsIgnoreCase("")
						&& (startLong == null || startLong.equalsIgnoreCase(""))) {
					str = str + " and (TO_NUMBER(C.LONGITUDE)) < " + endLong;
					strTemp = strTemp + " and (TO_NUMBER(C.LONGITUDE)) < " + endLong;
				}
				if (startLong != null && endLong != null) {
					String startLng;
					String endLng;

					if (startLong != null && startLong.length() > 0 && (endLong != null && endLong.length() > 0)) {
						if (Double.parseDouble(startLong) < Double.parseDouble(endLong)) {
							startLng = startLong;
							endLng = endLong;

						} else {
							startLng = endLong;
							endLng = startLong;

						}
						str = str + " and (TO_NUMBER(C.LONGITUDE)) > " + startLng;
						str = str + " and (TO_NUMBER(C.LONGITUDE)) < " + endLng;
						strTemp = strTemp + " and (TO_NUMBER(C.LONGITUDE)) > " + startLng;
						strTemp = strTemp + " and (TO_NUMBER(C.LONGITUDE)) < " + endLng;
					}
				}
				if (startLat != null && !startLat.equalsIgnoreCase("")
						&& (endLat == null || endLat.equalsIgnoreCase(""))) {

					str = str + " and (TO_NUMBER(C.LATITUDE)) > " + startLat;
					strTemp = strTemp + " and (TO_NUMBER(C.LATITUDE)) > " + startLat;

				}
				if (endLat != null && !endLat.equalsIgnoreCase("")
						&& (startLat == null || startLat.equalsIgnoreCase(""))) {
					str = str + " and (TO_NUMBER(C.LATITUDE)) < " + endLat;
					strTemp = strTemp + " and (TO_NUMBER(C.LATITUDE)) < " + endLat;

				}
				if (startLat != null && endLat != null) {
					String startlatitude;
					String endLatitude;

					if (startLat != null && startLat.length() > 0 && (endLat != null && endLat.length() > 0)) {
						if (Double.parseDouble(startLat) < Double.parseDouble(endLat)) {
							startlatitude = startLat;
							endLatitude = endLat;

						} else {
							startlatitude = endLat;
							endLatitude = startLat;

						}
						str = str + " and (TO_NUMBER(C.LATITUDE)) > " + startlatitude;
						str = str + " and (TO_NUMBER(C.LATITUDE)) < " + endLatitude;
						strTemp = strTemp + " and (TO_NUMBER(C.LATITUDE)) > " + startlatitude;
						strTemp = strTemp + " and (TO_NUMBER(C.LATITUDE)) < " + endLatitude;

					}
				}
				str = str + " ) WHERE rn = 1 ORDER BY lastModifiedDate DESC  ";
				listFixed = session.createNativeQuery(str).list();

				strTemp = strTemp + " ) WHERE rn = 1 ORDER BY lastModifiedDate DESC ";
				listFixedAssetTemp = session.createNativeQuery(strTemp).list();

				if ((radius != null && !radius.equalsIgnoreCase("") && Double.parseDouble(radius) > 0)
						&& (longitude != null && !longitude.equalsIgnoreCase(""))
						&& (latitude != null && !latitude.equalsIgnoreCase(""))) {

					for (int i = 0; i < listFixed.size(); i++) {

						distance = haversine(Double.parseDouble(latitude), Double.parseDouble(longitude),
								Double.valueOf(listFixedAssetTemp.get(i)[13].toString()),
								Double.valueOf(listFixedAssetTemp.get(i)[12].toString()));
						

						if (distance <= Double.parseDouble(radius)) {
							listCircleRange.add(listFixed.get(i));
						}
					}

					rtn.put("listFixed", listCircleRange);
				} else {
					rtn.put("listFixed", listFixed);
				}

				// rtn.put("listFixed",listFixed);

			} catch (Exception e) {
				logger.info("Error in showing the filtered Fixed Asset Registry list view with a message :" + e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}

		return rtn;
	}

	@RequestMapping(value = "/FixedAssetRegistryFormView", method = RequestMethod.GET)
	public String FixedAssetRegistryFormView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		DateFormat formatterTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");

		Date date;
		String result[] = new String[4];

		String farID = request.getParameter("farID");
		String navAction = request.getParameter("NavAction");
		String itemCategoryDetails = "";
		int SelectedIndex = 0;

		FixedAssetRegistry fassetreg;
		Item itm;
		List<PurchaseOrderItem> listPurchaseOrderItem;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {

				// to open item when click on ADD from item List
				if (farID == null) {

					date = new Timestamp(System.currentTimeMillis());
					model.addAttribute("farcreatedDate", formatterTime.format(date).toString());
					model.addAttribute("farlastModifiedDate", formatterTime.format(date).toString());

					model.addAttribute("ListFarSerialNumber", "addNew");
					model.addAttribute("listFarPartNumber", "addNew");
					model.addAttribute("ListNode", "addNew");
					model.addAttribute("ListSite", "addNew");

					model.addAttribute("docStatus", "addNew");

					model.addAttribute("SelectedIndex", "addNew");
					model.addAttribute("FRCount", "addNew");

					model.addAttribute("ListFARItem", "addNew");

					return "FixedAssetRegistryFormView";

				}

				result = form.NavigationNP(session, "FIXED_ASSET_REGISTRY", "FAR_ID", farID, "LAST_MODIFIED_DATE",
						navAction);

				SelectedIndex = Integer.parseInt(result[1]);
				farID = result[2];

				model.addAttribute("SelectedIndex", SelectedIndex);
				model.addAttribute("FRCount", Integer.parseInt(result[0]));

				fassetreg = (FixedAssetRegistry) session.get(FixedAssetRegistry.class, farID);

				System.out.println(fassetreg);
				// get all details of item found in item table under itemCode
				itm = (Item) session.get(Item.class, fassetreg.getFaritemCode());
				if (itm != null) {

					model.addAttribute("faritemCode", itm.getItemCode());
					model.addAttribute("itemName", itm.getItemName());
					model.addAttribute("itemUnit", itm.getUnit());
					model.addAttribute("itemWeight", itm.getWeight());
					model.addAttribute("itemWeightUOM", itm.getWeightUOM());
					model.addAttribute("itemLength", itm.getLength());
					model.addAttribute("itemWidth", itm.getWidth());
					model.addAttribute("itemheight", itm.getHeight());
					model.addAttribute("itemSizeUOM", itm.getSizeUOM());
					model.addAttribute("itemDesc", itm.getItemDescription());
					

					// model.addAttribute("itemDomain",itm.getDomain());
					model.addAttribute("itemCableType", itm.getCableType());
					model.addAttribute("itemType", itm.getItemType());
					model.addAttribute("itemWarrantyPer", itm.getWarrantyPeriod());
					model.addAttribute("itemValuationRate", itm.getValuationRate());
					model.addAttribute("itemEndofLife", itm.getEndOfLife());
					model.addAttribute("usefulLifeMonth", itm.getUsefull_LifeMonths());
					model.addAttribute("farStatus", fassetreg.getFarStatus());

					if (itm.getService() == '1') {
						model.addAttribute("sService", "checked");
					} else {
						model.addAttribute("sService", "");
					}

					if (itm.getDisabled() == '1') {
						model.addAttribute("sDisabled", "checked");
					} else {
						model.addAttribute("sDisabled", "");
					}

					if (itm.getTech2G() == '1') {
						model.addAttribute("sTech2G", "checked");
					} else {
						model.addAttribute("sTech2G", "");
					}

					if (itm.getTech3G() == '1') {
						model.addAttribute("sTech3G", "checked");
					} else {
						model.addAttribute("sTech3G", "");
					}

					if (itm.getTech4G() == '1') {
						model.addAttribute("sTech4G", "checked");
					} else {
						model.addAttribute("sTech4G", "");
					}

					if (itm.getTech5G() == '1') {
						model.addAttribute("sTech5G", "checked");
					} else {
						model.addAttribute("sTech5G", "");
					}
				}

				// get model and partNo BOQ
				query = session.createQuery("from FarPartNumber where farID like :param1");
				query.setParameter("param1", farID);

				model.addAttribute("listFarPartNumber", mapper.writeValueAsString(query.list()));

				// get Nodes BOQ
				query = session.createQuery("from FarNode where farID like :param1");
				query.setParameter("param1", farID);

				model.addAttribute("ListNode", mapper.writeValueAsString(query.list()));

				// get Sites BOQ
				query = session.createQuery("from FarSite where farID like :param1");
				query.setParameter("param1", farID);

				model.addAttribute("ListSite", mapper.writeValueAsString(query.list()));

				// get SerialNo BOQ
				query = session.createQuery("from FarSerialNumber where farID like :param1");
				query.setParameter("param1", farID);

				model.addAttribute("ListFarSerialNumber", mapper.writeValueAsString(query.list()));

				// get Finance Tab

				itemCategoryDetails = fassetreg.getItemCat() + ':' + fassetreg.getItemCatCode();

				model.addAttribute("farID", fassetreg.getFarID());
				model.addAttribute("itemCategoryDetails", itemCategoryDetails);
				model.addAttribute("itemCatID", fassetreg.getItemCatID());
				model.addAttribute("itemRootCat", fassetreg.getItemRootCode());
				model.addAttribute("dailyDepAmount", fassetreg.getDailyPercent());
				model.addAttribute("netCost", fassetreg.getNetCost());
				model.addAttribute("accumDepAmount", fassetreg.getAccumPer());
				model.addAttribute("accumDepCode", fassetreg.getAccumDeprCode());
				model.addAttribute("DepCode", fassetreg.getDeprCode());
				model.addAttribute("depPercentage", fassetreg.getDeprPer());
				model.addAttribute("farcreatedDate", formatterTime.format(fassetreg.getFarcreatedDate()).toString());
				model.addAttribute("farlastModifiedDate",
				formatterTime.format(fassetreg.getFarlastModifiedDate()).toString());
				model.addAttribute("ownership", fassetreg.getOwnership());
				model.addAttribute("scrapStatus", fassetreg.getScrapStatus());
				if (fassetreg.getScrapDate() == null)
					model.addAttribute("scrapDate", "");
				else
					model.addAttribute("scrapDate", formatter1.format(fassetreg.getScrapDate()).toString());

				model.addAttribute("initialCost", fassetreg.getIniCost());
				model.addAttribute("netCost", fassetreg.getNetCost());
				model.addAttribute("usefulLifemonths", fassetreg.getUsefulLifeMon());
				model.addAttribute("faritemPosition", fassetreg.getPosition());
				model.addAttribute("ID", fassetreg.getPoId());
				model.addAttribute("supplierID", fassetreg.getSupplierID());
				model.addAttribute("supplierName", fassetreg.getSupplierName());
				model.addAttribute("farStatus", fassetreg.getFarStatus());
				model.addAttribute("farDomain", fassetreg.getFarDomain());
				model.addAttribute("farSubDomain", fassetreg.getFarSubDomain());
				model.addAttribute("farVendor", fassetreg.getFarVendor());
				model.addAttribute("farType", fassetreg.getFarType());

				// get Related Orders

				listPurchaseOrderItem = new ArrayList<PurchaseOrderItem>();

				if (fassetreg.getPoItemId() != null) {

					query = session.createQuery(
							"select t.ItemCode || ':'|| t.ItemName ,t.ItemModel,t.ItemPartNb,t.Qty, t.Rate,t.DiscAmnt ,t.Tax1, t.NetRate, t.Total, "
									+ "t.TotalAt,t.GrNo, t.iPrNo, t.ArNo, t.iCIPNo,t.FarNo, t.pordItemId  from PurchaseOrderItem t where t.pordItemId like :param1");
					query.setParameter("param1", farID);

				}
				model.addAttribute("ListFARItem", mapper.writeValueAsString(listPurchaseOrderItem));

			} catch (Exception e) {

				logger.info("Error in Fixed Asset Register FormView with a message: " + e);
				e.printStackTrace();

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}
		return "FixedAssetRegistryFormView";
	}

	@RequestMapping(value = "/FixedAssetRegistryFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FixedAssetRegistryFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) throws Exception {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		else {

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);
			DateFormat formatterTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			DateFormat formatterDate = new SimpleDateFormat("MM/dd/yyyy");

			Timestamp CreationDate, lastModifiedDate, scrDate;

			String createdDate = request.getParameter("farcreatedDate");
			String farcode = request.getParameter("farID");
			String scrapDate = request.getParameter("scrapDate");

			FixedAssetRegistry fassetreg;

			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {

					fassetreg = new FixedAssetRegistry();
					
					CreationDate = new Timestamp(formatterTime.parse(createdDate).getTime());
					lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

					if (StringUtils.equalsIgnoreCase(farcode, "")) {
						synchronized (this) {
							farcode = "FAR_" + year + "_"
									+ Integer.parseInt(
											session.createNativeQuery("SELECT FIXED_ASSET_REGISTRY FROM SEQ_TABLE")
													.uniqueResult().toString());
							query = session.createNativeQuery(
									"UPDATE SEQ_TABLE SET FIXED_ASSET_REGISTRY = FIXED_ASSET_REGISTRY + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
					}
					// farcode= "FAR_"+year+"_" +appConfig.getSequenceNbr(10);

					float initialCost = 0;
					if (request.getParameter("initialCost") != "")
						initialCost = Float.parseFloat(request.getParameter("initialCost"));

					float useful_life_month = 0;
					if (request.getParameter("usefulLifemonths") != "")
						useful_life_month = Float.parseFloat(request.getParameter("usefulLifemonths"));

					float accumDepAmount = 0;
					if (request.getParameter("accumDepAmount") != "")
						accumDepAmount = Float.parseFloat(request.getParameter("accumDepAmount"));

					float dailyDepAmount = 0;
					if (request.getParameter("dailyDepAmount") != "")
						dailyDepAmount = Float.parseFloat(request.getParameter("dailyDepAmount"));

					float depPercentage = 0;
					if (request.getParameter("depPercentage") != "")
						depPercentage = Float.parseFloat(request.getParameter("depPercentage"));

					float netCost = 0;
					if (request.getParameter("netCost") != "")
						netCost = Float.parseFloat(request.getParameter("netCost"));

					fassetreg.setFarID(farcode);
					fassetreg.setFarStatus("farStatus");
					fassetreg.setFaritemCode(request.getParameter("faritemCode"));
					fassetreg.setFaritemName(request.getParameter("faritemName"));
					fassetreg.setItemCat(request.getParameter("itemCategory"));
					fassetreg.setItemCatCode(request.getParameter("itemCatCode"));
					fassetreg.setItemCatID(request.getParameter("itemCatID"));
					fassetreg.setItemRootCode(request.getParameter("itemRootCat"));
					// fassetreg.setSideID(request.getParameter("siteID"));
					// fassetreg.setWareName(request.getParameter("wareName"));
					// fassetreg.setWareID(request.getParameter("wareID"));
					fassetreg.setNodeID(request.getParameter("nodeID"));
					fassetreg.setNodeName(request.getParameter("node_Name"));
					fassetreg.setFarcreatedDate(CreationDate);
					fassetreg.setFarlastModifiedDate(lastModifiedDate);
					fassetreg.setOwnership(request.getParameter("ownership"));
					fassetreg.setScrapStatus(request.getParameter("scrapStatus"));
					fassetreg.setIniCost(initialCost);
					fassetreg.setUsefulLifeMon(useful_life_month);
					fassetreg.setAccumPer(accumDepAmount);
					fassetreg.setDailyPercent(dailyDepAmount);
					fassetreg.setDeprPer(depPercentage);
					fassetreg.setNetCost(netCost);
					fassetreg.setPosition(request.getParameter("faritemPosition"));
					fassetreg.setFarDomain(request.getParameter("farDomain"));
					fassetreg.setFarSubDomain(request.getParameter("farSubDomain"));
					fassetreg.setFarVendor(request.getParameter("farVendor"));
					fassetreg.setFarType(request.getParameter("farType"));

					if (scrapDate != "") {
						scrDate = new Timestamp(formatterDate.parse(scrapDate).getTime());
						fassetreg.setScrapDate(scrDate);
					}

					session.saveOrUpdate(fassetreg);

					// Saving Model & Part Number BOQ
					String[] slctDelModPart = request.getParameterValues("slctDelModPart[]");
					System.out.println("the selectDelModPart is" + slctDelModPart);
					if (slctDelModPart != null) {

						String querydeletePNum = "delete FarPartNumber  where itmId IN (:param1)";
						query = session.createQuery(querydeletePNum);
						query.setParameterList("param1", slctDelModPart);
						query.executeUpdate();
						// appConfig.deleteRowsByQueryParamList(FarPartNumber.class, querydeletePNum,
						// "param1", slctDelModPart);
					}

					float qtyPartNum = 0;
					String itmId;
					if (itemParameters.getDictParameter() != null) {

						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
							FarPartNumber farPartNumber = new FarPartNumber();
							System.out.println("" + itemParameters.getDictParameter().get(i).get("itemPartNum"));
							System.out.println("" + itemParameters.getDictParameter().get(i).get("itemModel"));
							System.out.println("" + itemParameters.getDictParameter().get(i).get("primary"));
							System.out.println("" + itemParameters.getDictParameter().get(i).get("itemCode"));

							if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get("farItemID"),
									"0")) {
								synchronized (this) {
									itmId = "FARMP_" + year + "_"
											+ Integer.parseInt(
													session.createNativeQuery("SELECT FAR_MODEL_PARTNO FROM SEQ_TABLE")
															.uniqueResult().toString());
									query = session.createNativeQuery(
											"UPDATE SEQ_TABLE SET FAR_MODEL_PARTNO = FAR_MODEL_PARTNO + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate();
								}
								// itmId = "FARMP_" + year + "_" + appConfig.getSequenceNbr(25);
								farPartNumber.setItmId(itmId);
							} else {
								farPartNumber.setItmId(itemParameters.getDictParameter().get(i).get("farItemID"));

							}
							farPartNumber.setItemCode(itemParameters.getDictParameter().get(i).get("itemCode"));
							farPartNumber.setItemPartNb(itemParameters.getDictParameter().get(i).get("itemPartNum"));
							farPartNumber.setItemModel(itemParameters.getDictParameter().get(i).get("itemModel"));
							farPartNumber.setPrimary(itemParameters.getDictParameter().get(i).get("primary"));
							farPartNumber.setFarID(farcode);
							if (itemParameters.getDictParameter().get(i).get("qtyPartNum") != "") {

								qtyPartNum = Float
										.parseFloat(itemParameters.getDictParameter().get(i).get("qtyPartNum"));
								farPartNumber.setQtyPartNb(qtyPartNum);
							}

							session.saveOrUpdate(farPartNumber);
						}
					}

					// Saving Node BOQ
					String[] slctDelNode = request.getParameterValues("slctDelNode[]");
					System.out.println("the selectDelNode is" + slctDelNode);
					if (slctDelNode != null) {
						String querydelete = "delete FarNode  where nodefarId IN (:param1)";
						query = session.createQuery(querydelete);
						query.setParameterList("param1", slctDelNode);
						query.executeUpdate();
						// appConfig.deleteRowsByQueryParamList(FarNode.class, querydelete, "param1",
						// slctDelNode);
					}

					String nodefarId;
					if (itemParameters.getDictParameternode() != null) {

						for (int i = 0; i < itemParameters.getDictParameternode().size(); i++) {
							FarNode farNode = new FarNode();
							System.out.println("" + itemParameters.getDictParameternode().get(i).get("nodeID"));
							System.out.println("" + itemParameters.getDictParameternode().get(i).get("node_Name"));

							if (StringUtils.equalsIgnoreCase(
									itemParameters.getDictParameternode().get(i).get("farNodeID"), "0")) {
								synchronized (this) {
									nodefarId = "FARNODE_" + year + "_"
											+ Integer.parseInt(
													session.createNativeQuery("SELECT FAR_NODE FROM SEQ_TABLE")
															.uniqueResult().toString());
									query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_NODE = FAR_NODE + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate();
								}
								// nodefarId = "FARNODE_" + year + "_" + appConfig.getSequenceNbr(27);
								farNode.setNodefarId(nodefarId);
							} else {
								farNode.setNodefarId(itemParameters.getDictParameternode().get(i).get("farNodeID"));

							}

							farNode.setNodeID(itemParameters.getDictParameternode().get(i).get("nodeID"));
							farNode.setNodeName(itemParameters.getDictParameternode().get(i).get("node_Name"));
							farNode.setFarID(farcode);

							// appConfig.persist(farNode, FarNode.class);
							session.saveOrUpdate(farNode);
						}

					}

					// Saving Site BOQ
					String[] slctDelSite = request.getParameterValues("slctDelSite[]");
					System.out.println("the selectDelSite is" + slctDelSite);
					if (slctDelSite != null) {
						String querySitedelete = "delete FarSite  where farsiteId IN (:param1)";
						query = session.createQuery(querySitedelete);
						query.setParameterList("param1", slctDelSite);
						query.executeUpdate();
						// appConfig.deleteRowsByQueryParamList(FarSite.class, querySitedelete,
						// "param1", slctDelSite);
					}

					// create a alert , create a jframe
					final JDialog dialog = new JDialog();
					dialog.setAlwaysOnTop(true);

					String farsiteId;
					if (itemParameters.getDictParametersite() != null) {

						for (int i = 0; i < itemParameters.getDictParametersite().size(); i++) {
							FarSite farSite = new FarSite();

							if (StringUtils.equalsIgnoreCase(
									itemParameters.getDictParametersite().get(i).get("farSiteID"), "0")) {
								synchronized (this) {
									farsiteId = "FARSITE_" + year + "_"
											+ Integer.parseInt(
													session.createNativeQuery("SELECT FAR_SITE FROM SEQ_TABLE")
															.uniqueResult().toString());
									query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_SITE = FAR_SITE + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate();
								}
								// farsiteId = "FARSITE_" + year + "_" + appConfig.getSequenceNbr(29);
								farSite.setFarsiteId(farsiteId);
							} else {
								farSite.setFarsiteId(itemParameters.getDictParametersite().get(i).get("farSiteID"));

							}
							farSite.setSiteID(itemParameters.getDictParametersite().get(i).get("siteID"));
							farSite.setSiteName(itemParameters.getDictParametersite().get(i).get("siteName"));
							farSite.setWareID(itemParameters.getDictParametersite().get(i).get("wareID"));

							farSite.setFarID(farcode);

							// Validation for site BOQ before saving
							if (farSite.getSiteID().equals("")) {
								JOptionPane.showMessageDialog(dialog, "Site ID is null!");
								return null;
							} else if (farSite.getSiteName().equals("")) {
								JOptionPane.showMessageDialog(dialog, "Site Name is null!");
								return null;
							} else if (farSite.getWareID().equals("")) {
								JOptionPane.showMessageDialog(dialog, "Warehouse ID is null!");
								return null;
							} else {
								session.saveOrUpdate(farSite);

							}

						}

					}

					// Saving Serial Number BOQ
					String[] slctDelSerial = request.getParameterValues("slctDelSerial[]");
					System.out.println("the slctDelSerial is" + slctDelSerial);
					if (slctDelSerial != null) {
						String querydeleteSNum = "delete FarSerialNumber  where serialId IN (:param1)";
						query = session.createQuery(querydeleteSNum);
						query.setParameterList("param1", slctDelSerial);
						query.executeUpdate();
						// appConfig.deleteRowsByQueryParamList(FarSerialNumber.class, querydeleteSNum,
						// "param1", slctDelSerial);
					}

					String serialId;
					if (itemParameters.getDictParameterFixedAssetRegistrySerialNumber() != null) {

						for (int i = 0; i < itemParameters.getDictParameterFixedAssetRegistrySerialNumber()
								.size(); i++) {
							FarSerialNumber farSerialNumber = new FarSerialNumber();

							if (StringUtils.equalsIgnoreCase(itemParameters
									.getDictParameterFixedAssetRegistrySerialNumber().get(i).get("farSerialID"), "0")) {
								synchronized (this) {
									serialId = "FARSNUM_" + year + "_"
											+ Integer.parseInt(
													session.createNativeQuery("SELECT FAR_SERIALNO FROM SEQ_TABLE")
															.uniqueResult().toString());
									query = session
											.createNativeQuery("UPDATE SEQ_TABLE SET FAR_SERIALNO = FAR_SERIALNO + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate();
								}
								// serialId = "FARSNUM_" + year + "_" + appConfig.getSequenceNbr(32);
								farSerialNumber.setSerialId(serialId);
							} else {
								farSerialNumber.setSerialId(itemParameters
										.getDictParameterFixedAssetRegistrySerialNumber().get(i).get("farSerialID"));
							}

							farSerialNumber.setInputSerialNb(itemParameters
									.getDictParameterFixedAssetRegistrySerialNumber().get(i).get("SNserialNumber"));
							farSerialNumber.setInputModel(itemParameters
									.getDictParameterFixedAssetRegistrySerialNumber().get(i).get("SNmodel"));
							farSerialNumber.setInputpartNumber(itemParameters
									.getDictParameterFixedAssetRegistrySerialNumber().get(i).get("SNpartNumber"));
							farSerialNumber.setInputNodeID(itemParameters
									.getDictParameterFixedAssetRegistrySerialNumber().get(i).get("SNnodeID"));
							farSerialNumber.setInputNodeName(itemParameters
									.getDictParameterFixedAssetRegistrySerialNumber().get(i).get("SNnodeName"));
							farSerialNumber.setInputsite(itemParameters.getDictParameterFixedAssetRegistrySerialNumber()
									.get(i).get("SNsite"));
							farSerialNumber.setInputPosition(itemParameters
									.getDictParameterFixedAssetRegistrySerialNumber().get(i).get("SNposition"));
							farSerialNumber.setFarID(farcode);

							session.saveOrUpdate(farSerialNumber);

						}

					}
					tx.commit();
					rtn.put("farID", farcode);
					rtn.put("farlastModifiedDate",
							formatterTime.format(new Timestamp(System.currentTimeMillis())).toString());

				} catch (Exception e) {
					tx.rollback();
					logger.info("Error in saving purchase order with a message : " + e);
					e.printStackTrace();
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

	@RequestMapping(value = "/FixedAssetRegistryFormViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FixedAssetRegistryFormViewDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		String idForm = request.getParameter("farID");
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createNativeQuery("Delete FIXED_ASSET_REGISTRY where FAR_ID = '" + idForm + "'");
				query.executeUpdate();

				query = session.createNativeQuery("delete FAR_MODEL_PARTNUMBER  where FAR_ID = '" + idForm + "'");
				query.executeUpdate();

				query = session.createNativeQuery("delete FAR_NODE  where FAR_ID = '" + idForm + "'");
				query.executeUpdate();

				query = session.createNativeQuery("delete FAR_SERIAL_NUMBER  where FAR_ID = '" + idForm + "'");
				query.executeUpdate();

				query = session.createNativeQuery("delete FAR_SITE  where FAR_ID = '" + idForm + "'");
				query.executeUpdate();

			} catch (Exception e) {
				System.out.println(
						"Error in creating session with the FixedAssetRegistryFormViewDelete method " + e.getMessage());
				logger.info("could not connect to DB in FixedAssetRegistryFormViewDelete method ");
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		} else {
			System.out.println("could not connect to DB in FixedAssetRegistryFormViewDelete method");
			logger.info("could not connect to DB in FixedAssetRegistryFormViewDelete method ");
		}

		rtn.put("DeletingTest", "DeleteDone");
		return rtn;
	}

	@RequestMapping(value = "/FixedAssetRegistryListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FixedAssetRegistryListViewDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		String idForm = null;
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		String[] idList = request.getParameterValues("farID[]");

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				for (int i = 0; i < idList.length; i++) {
					// Get FAR_ID from the listview form
					idForm = idList[i];

					query = session.createNativeQuery("Delete FIXED_ASSET_REGISTRY where FAR_ID = '" + idForm + "'");
					query.executeUpdate();

					query = session.createNativeQuery("delete FAR_MODEL_PARTNUMBER  where FAR_ID = '" + idForm + "'");
					query.executeUpdate();

					query = session.createNativeQuery("delete FAR_NODE  where FAR_ID = '" + idForm + "'");
					query.executeUpdate();

					query = session.createNativeQuery("delete FAR_SERIAL_NUMBER  where FAR_ID = '" + idForm + "'");
					query.executeUpdate();

					query = session.createNativeQuery("delete FAR_SITE  where FAR_ID = '" + idForm + "'");
					query.executeUpdate();

				}

			} catch (Exception e) {
				System.out.println(
						"Error in creating session with the FixedAssetRegistryListViewDelete method " + e.getMessage());
				logger.info("could not connect to DB in FixedAssetRegistryListViewDelete method ");
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		} else {
			System.out.println("could not connect to DB in FixedAssetRegistryListViewDelete method");
			logger.info("could not connect to DB in FixedAssetRegistryListViewDelete method ");
		}

		rtn.put("DeletingTest", "DeleteDone");
		return rtn;

	}

	// get All FAR NODE ID BOQ AUTO

	@RequestMapping(value = "/GetAllFarNodeID", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllFarNodeID(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				String requestName = "%" + request.getParameter("nodeID") + "%";
				query = session.createQuery(
						"select distinct nvl(t.nodeID,' '),nvl(t.nodeName,' ') from FarNode t where t.nodeID like :param1");
				query.setParameter("param1", requestName);

				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("listFarNodeIDs", query.list());

			} catch (Exception e) {

				logger.info("Error while getting FAR NODE ID using autocomplete with error message: " + e);
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

// get All FAR NODE NAME BOQ AUTO	

	@RequestMapping(value = "/GetAllFarNodeName", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllFarNodeName(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				String requestName = "%" + request.getParameter("node_Name") + "%";
				query = session.createQuery(
						"select distinct nvl(t.nodeName,' '),nvl(t.nodeID,' ') from FarNode t where t.nodeName like :param1");
				query.setParameter("param1", requestName);

				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("listNodeNames", query.list());

			} catch (Exception e) {

				logger.info("Error while getting FAR NODE NAME using autocomplete with error message: " + e);
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

//getting All FAR using autocomplete in navigation

	@RequestMapping(value = "/GetAllFAR", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllFAR(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				String requestValue = request.getParameter("requestValue");
				query = session.createNativeQuery(
						"select FAR_ID, FAR_STATUS, ITEM_CODE, ITEM_NAME from FIXED_ASSET_REGISTRY where LOWER(FAR_ID) like LOWER(:param1) "
								+ "or LOWER(FAR_STATUS) like LOWER(:param1) ORDER BY LAST_MODIFIED_DATE DESC");
				query.setParameter("param1", "%" + requestValue + "%");

				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("listFAR", query.list());

			} catch (Exception e) {

				logger.info("Error while getting FAR using autocomplete with error message: " + e);
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
		
	static double haversine(double latitude, double longitude, double pointLat, double pointLong) {

		// distance between latitudes and longitudes
		double dLat = Math.toRadians(pointLat - latitude);
		double dLon = Math.toRadians(pointLong - longitude);

		// convert to radians
		latitude = Math.toRadians(latitude);
		pointLat = Math.toRadians(pointLat);

		// apply formulae
		double a = Math.pow(Math.sin(dLat / 2), 2)
						+ Math.pow(Math.sin(dLon / 2), 2) * Math.cos(latitude) * Math.cos(pointLat);
		double rad = 6371;
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;

	}

}
