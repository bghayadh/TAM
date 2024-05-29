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
import java.util.Locale;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.ArNode;
import com.aliat.alm.models.ArPartNumber;
import com.aliat.alm.models.ArSite;
import com.aliat.alm.models.ArSerialNumber;
import com.aliat.alm.models.AssetRegistry;
import com.aliat.alm.models.PurchaseOrderItem;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AssetRegistryController {

	
	
	@Autowired
	Notify notifications;
	
	@Autowired
	Form form;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static Session session = null;
	private static Transaction tx = null;
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping(value = "/AssetRegistryListView", method = RequestMethod.GET)
	public String AssetRegistryListView(Locale locale, Model model,HttpServletRequest request,HttpServletResponse response ) {
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				System.out.println("asset");
				try {
/*				    String str = "SELECT arID, assetID, aritemCode, aritemName, itemModel, itemPartNumber, ArStatus, arlastModifiedDate, itemSN, itemNameReg, poID, siteID, siteName FROM ( "
				                 + " SELECT A.AR_ID AS arID, A.AR_ID AS assetID, A.ITEM_CODE AS aritemCode, A.ITEM_NAME AS aritemName, A.ITEM_MODEL AS itemModel, A.ITEM_PART_NUMBER AS itemPartNumber, A.AR_STATUS as ArStatus, TO_CHAR(A.LAST_MODIFIED_DATE, 'YYYY-MM-DD HH24:MI:SS') AS arlastModifiedDate, A.ITEM_SN AS itemSN, A.ITEM_NAME_REGISTER AS itemNameReg, A.PO_ID AS poID, B.SITE_ID AS siteID, B.SITE_NAME AS siteName , "
				                 + " ROW_NUMBER() OVER (PARTITION BY A.AR_ID ORDER BY B.SITE_ID DESC) AS rn FROM ASSET_REGISTRY A LEFT JOIN AR_SITE B ON B.AR_ID = A.AR_ID ) WHERE rn = 1 ORDER BY arlastModifiedDate DESC";
*/				                 
				    

				    String str = "SELECT arID, assetID, aritemCode, aritemName, itemModel, itemPartNumber, ArStatus, arlastModifiedDate, itemSN, itemNameReg, poID, siteID, siteName FROM ("
			                 + " SELECT A.AR_ID AS arID, A.AR_ID AS assetID, A.ITEM_CODE AS aritemCode, A.ITEM_NAME AS aritemName, D.ITEM_MODEL AS itemModel, D.ITEM_PART_NUMBER AS itemPartNumber,"
			                 + " ROW_NUMBER() OVER (PARTITION BY A.AR_ID ORDER BY D.ITM_ID DESC) as rn1, A.AR_STATUS as ArStatus, TO_CHAR(A.LAST_MODIFIED_DATE, 'YYYY-MM-DD HH24:MI:SS') AS arlastModifiedDate, C.SERIAL_NUMBER AS itemSN,"
			                 + " ROW_NUMBER() OVER (PARTITION BY A.AR_ID ORDER BY C.SERIAL_ID DESC) as rn2, A.ITEM_NAME_REGISTER AS itemNameReg, A.PO_ID AS poID, B.SITE_ID AS siteID, B.SITE_NAME AS siteName,"
			                 + " ROW_NUMBER() OVER (PARTITION BY A.AR_ID ORDER BY B.SITE_ID DESC) AS rn3"
			                 + " FROM ASSET_REGISTRY A LEFT JOIN AR_SITE B ON B.AR_ID = A.AR_ID LEFT JOIN AR_SERIAL_NUMBER C ON C.AR_ID = A.AR_ID LEFT JOIN AR_MODEL_PARTNUMBER D ON D.AR_ID = A.AR_ID)"
			                 + " WHERE (rn1 = 0 OR rn1 = 1) and (rn2 = 0 OR rn2 = 1) and (rn3 = 0 OR rn3 = 1) ORDER BY arlastModifiedDate DESC";


				    model.addAttribute("ListGridTable", mapper.writeValueAsString(session.createNativeQuery(str).list()));
				    List<Object[]> resultList = session.createNativeQuery(str).list();

				  
				} catch (Exception e) {
					logger.info("Error on Asset Register ListView with a message : " + e);
					e.printStackTrace();
					model.addAttribute("ListGridTable", "");
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						
					}
				}
			}
			return "AssetRegistryListView";
		}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredAssetRegistryListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredAssetRegistryListView(Locale locale, Model model, HttpServletRequest request,
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

				String startdate, enddate, itemcode, itemname,itemmodel;
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

			//	List<String> listAsset = new ArrayList<String>();
				List<Object[]> listCircleRange = new ArrayList<Object[]>();
				List<Object[]> listAsset = new ArrayList<Object[]>();
				
				//This list is used in circle range search to get the longitude and latitude in order to calculate the distance 
				//(We can't select the long and lat in the main list listAsset because no need for long and lat in appending the grid)
				List<Object[]> listAssetTemp = new ArrayList<Object[]>();

				/*String str = "select 1 as chkBox, AR_ID as arID, ITEM_CODE as aritemCode, ITEM_NAME as aritemName, ITEM_MODEL as itemModel, ITEM_PART_NUMBER as itemPartNumber,"  
												+ "TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as arlastModifiedDate, ITEM_SN as itemSN, ITEM_NAME_REGISTER as itemNameReg, PO_ID as poID,"  
												+ "COALESCE(NODE_ID, ' ') as nodeID, COALESCE(NODE_NAME, ' ') as nodeName  " 
													+ "from ASSET_REGISTRY  ";*/
				
				String str = "SELECT chkBox,arID,aritemCode,aritemName,itemModel,itemPartNumber,arlastModifiedDate,itemSN,itemNameReg,poID,siteID,siteName FROM ( SELECT 1 AS chkBox, A.AR_ID AS arID, A.ITEM_CODE AS aritemCode, A.ITEM_NAME AS aritemName, A.ITEM_MODEL AS itemModel, A.ITEM_PART_NUMBER AS itemPartNumber, "
						+ "TO_CHAR(A.LAST_MODIFIED_DATE, 'YYYY-MM-DD HH24:MI:SS') AS arlastModifiedDate, A.ITEM_SN AS itemSN, A.ITEM_NAME_REGISTER AS itemNameReg, A.PO_ID AS poID, "
						+ " B.SITE_ID as siteID, B.SITE_NAME as siteName, "
						+ "ROW_NUMBER() OVER (PARTITION BY A.AR_ID ORDER BY B.SITE_ID DESC) AS rn "
						+ "FROM ASSET_REGISTRY A LEFT JOIN AR_SITE B ON B.AR_ID = A.AR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID ";

				String strTemp = "SELECT * FROM ( SELECT 1 AS chkBox, A.AR_ID AS arID, A.ITEM_CODE AS aritemCode, A.ITEM_NAME AS aritemName, A.ITEM_MODEL AS itemModel, A.ITEM_PART_NUMBER AS itemPartNumber, "
						+ "TO_CHAR(A.LAST_MODIFIED_DATE, 'YYYY-MM-DD HH24:MI:SS') AS arlastModifiedDate, A.ITEM_SN AS itemSN, A.ITEM_NAME_REGISTER AS itemNameReg, A.PO_ID AS poID, "
						+ " B.SITE_ID as siteID, B.SITE_NAME as siteName,C.LONGITUDE as longitude,C.LATITUDE as latitude, "
						+ "ROW_NUMBER() OVER (PARTITION BY A.AR_ID ORDER BY B.SITE_ID DESC) AS rn "
						+ "FROM ASSET_REGISTRY A LEFT JOIN AR_SITE B ON B.AR_ID = A.AR_ID LEFT JOIN WAREHOUSE C ON C.WARE_ID = B.WARE_ID ";

				
				if (startdate != null && enddate != null) {
					str = str + " WHERE  A.CREATED_DATE between TO_DATE('" + startdate
							+ "','YYYY-MM-DD') and TO_DATE('" + enddate + "','YYYY-MM-DD')";
					
					strTemp = strTemp + " WHERE  A.CREATED_DATE between TO_DATE('" + startdate
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
				str = str + ") WHERE rn = 1 ORDER BY arlastModifiedDate DESC ";
				listAsset = session.createNativeQuery(str).list();
				
				strTemp = strTemp + ") WHERE rn = 1 ORDER BY arlastModifiedDate DESC ";
				listAssetTemp = session.createNativeQuery(strTemp).list();
				
				if (( radius != null && !radius.equalsIgnoreCase("") && Double.parseDouble(radius) > 0) && (longitude != null && !longitude.equalsIgnoreCase("")) && (latitude != null
						&& !latitude.equalsIgnoreCase(""))) {
				
					for (int i = 0; i < listAsset.size(); i++) {
					
						distance = haversine(Double.parseDouble(longitude), Double.parseDouble(latitude),
								Double.valueOf(listAssetTemp.get(i)[14].toString()),Double.valueOf(listAssetTemp.get(i)[15].toString()));
					
						if (distance <= Double.parseDouble(radius)) {
							listCircleRange.add(listAsset.get(i));
						}
					}
			
					rtn.put("listAsset", listCircleRange);
				}
				else {
					rtn.put("listAsset",listAsset);
				}
				
			
				//rtn.put("listAsset",listAsset);
			
			} catch (Exception e) {
				logger.info("Error in showing the filtered Asset Registry list view with a message :" + e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}

		return rtn;
	}
	
	@RequestMapping(value = "/AssetRegistryFormView", method = RequestMethod.GET)
	public String AssetRegistryFormView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		DateFormat formatterTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		
		Date date;
		String result [] =new String[4];
		
		String arID = request.getParameter("arID"); 
		String navAction = request.getParameter("NavAction"); 
		String itemCategoryDetails = "";
		int SelectedIndex = 0;
		
		AssetRegistry assetreg; 
		List<PurchaseOrderItem> listPurchaseOrderItem;
		
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			
			try {

				// to open item when click on ADD from item List
				if (arID == null) { 
					
					date = new Timestamp(System.currentTimeMillis());
					model.addAttribute("arcreatedDate", formatterTime.format(date).toString());
					model.addAttribute("arlastModifiedDate", formatterTime.format(date).toString());
					
					model.addAttribute("ListArSerialNumber", "addNew");
					model.addAttribute("listArPartNumber", "addNew");
					model.addAttribute("ListNode", "addNew");
					model.addAttribute("ListSite", "addNew");
					
					model.addAttribute("docStatus", "addNew");
		
					model.addAttribute("SelectedIndex", "addNew");
					model.addAttribute("ARCount", "addNew");
					
					model.addAttribute("ListARItem", "addNew");
		
					return "AssetRegistryFormView"; 
					
					}
		
					
				System.out.println(arID);
				result = form.NavigationNP(session,"ASSET_REGISTRY","AR_ID",arID,"LAST_MODIFIED_DATE",navAction);		
		
				SelectedIndex= Integer.parseInt(result[1]);
				System.out.println(SelectedIndex);
				arID=result[2];

				model.addAttribute("SelectedIndex", SelectedIndex);
				model.addAttribute("ARCount", Integer.parseInt(result[0]));
				
				assetreg = (AssetRegistry) session.get(AssetRegistry.class, arID);
		
				// get model and partNo BOQ
				query = session.createQuery("from ArPartNumber where arID like :param1");
				query.setParameter("param1", arID);

				model.addAttribute("listArPartNumber", mapper.writeValueAsString(query.list()));
				 
				
				// get Nodes BOQ
				query = session.createQuery("from ArNode where arID like :param1");
				query.setParameter("param1", arID);

				model.addAttribute("ListNode", mapper.writeValueAsString(query.list()));
				
				
				// get Sites BOQ
				query = session.createQuery("from ArSite where arID like :param1");
				query.setParameter("param1", arID);

				model.addAttribute("ListSite", mapper.writeValueAsString(query.list()));
				
				
				// get SerialNo BOQ
				query = session.createQuery("from ArSerialNumber where arID like :param1");
				query.setParameter("param1", arID);
		
				//send data serial number formview in AR
				model.addAttribute("ListArSerialNumber", mapper.writeValueAsString(query.list()));
				
				
				itemCategoryDetails= assetreg.getItemCat() +':'+ assetreg.getItemCatCode();
				
				model.addAttribute("itemCategoryDetails", itemCategoryDetails);
				model.addAttribute("itemCatID", assetreg.getItemCatID());
				model.addAttribute("itemRootCat", assetreg.getItemRootCode());
				model.addAttribute("arID", assetreg.getArID());
				model.addAttribute("ID", assetreg.getPoID());
				model.addAttribute("aritemCode", assetreg.getAritemCode()); 
				model.addAttribute("aritemName", assetreg.getAritemName());
				model.addAttribute("supplierID", assetreg.getSupplierID());
				model.addAttribute("supplierName", assetreg.getSupplierName());
				model.addAttribute("arStatus", assetreg.getArStatus());
				
				
				model.addAttribute("dniId", assetreg.getArdniID());
				model.addAttribute("poId", assetreg.getPoID());
				model.addAttribute("poItemId", assetreg.getPoItemId());
				model.addAttribute("initialCost", assetreg.getInitialCost());
				model.addAttribute("suppId", assetreg.getSupplierID());
				model.addAttribute("suppName", assetreg.getSupplierName()); 
				model.addAttribute("arSiteId", assetreg.getArsiteId());
				
				if (assetreg.getArcreatedDate() == null) {
					date = new Timestamp(System.currentTimeMillis());
				}
				else {
					date = assetreg.getArcreatedDate();
				}
				model.addAttribute("arcreatedDate", formatterTime.format(date).toString());
				
				
				if (assetreg.getArlastModifiedDate() == null) {
					date = new Timestamp(System.currentTimeMillis());
				}
				else {
					date = assetreg.getArlastModifiedDate();
				}
	 	    	model.addAttribute("arlastModifiedDate", formatterTime.format(date).toString());
	 	    	
		    	model.addAttribute("aritemPosition", assetreg.getPosition());
		    	model.addAttribute("arunit", assetreg.getArunit());
		    	model.addAttribute("aritemDescription", assetreg.getAritemDescription());
		    	model.addAttribute("aritemType", assetreg.getAritemType());
		    	model.addAttribute("arcableType", assetreg.getArcableType());
		    	model.addAttribute("arweight", assetreg.getArweight());
		    	model.addAttribute("arweightUOM", assetreg.getArweightUOM());
		    	model.addAttribute("arlength", assetreg.getArlength());
		    	model.addAttribute("arwidth", assetreg.getArwidth());
		    	model.addAttribute("arheight", assetreg.getArheight());
		    	model.addAttribute("arsizeUOM", assetreg.getArsizeUOM());
		    	model.addAttribute("arDomain", assetreg.getArDomain());
		    	model.addAttribute("arSubdomain", assetreg.getArSubDomain());
		    	model.addAttribute("arVendor", assetreg.getArVendor());
		    	model.addAttribute("arType", assetreg.getArType());

		    	if (assetreg.getArendOfLife() == null) {
					date = new Timestamp(System.currentTimeMillis());
				}
		    	else {
		    		date = assetreg.getArendOfLife();
		    	}
		    	model.addAttribute("arendOfLife", formatterTime.format(date).toString());
		    	
		    	model.addAttribute("arvaluationRate", assetreg.getArvaluationRate());
		    	model.addAttribute("aritemImage", assetreg.getAritemImage());
		    	model.addAttribute("arwarrantyPeriod", assetreg.getArwarrantyPeriod());
		       	if (assetreg.getArservice() == '1') 
		    	 		{model.addAttribute("arservice", "checked"); }
		    	else 	{model.addAttribute("arservice", "");}
		       	if (assetreg.getArdisabled() == '1') 
		       			{model.addAttribute("ardisabled", "checked"); }
		       	else 	{model.addAttribute("ardisabled", "");}
		       	
		       	if (assetreg.getArtech2G() == '1') 
	   			{model.addAttribute("artech2G", "checked"); }
		       	else 	{model.addAttribute("artech2G", "");}
		       	if (assetreg.getArtech3G() == '1') 
	   			{model.addAttribute("artech3G", "checked"); }
		       	else 	{model.addAttribute("artech3G", "");}
		       	if (assetreg.getArtech4G() == '1') 
	   			{model.addAttribute("artech4G", "checked"); }
		       	else 	{model.addAttribute("artech4G", "");}
		       	
		    	if (assetreg.getArtech5G() == '1') 
	   			{model.addAttribute("artech5G", "checked"); }
		       	else 	{model.addAttribute("artech5G", "");}
		    	
		    	model.addAttribute("itemNameReg", assetreg.getItemNameReg());
		    	//model.addAttribute("siteID", assetreg.getSiteID());
				

				// get Related Orders
				listPurchaseOrderItem = new ArrayList<PurchaseOrderItem>();
				
				if(assetreg.getPoItemId() != null) {
					System.out.println(assetreg.getAritemCode());
				 query = session.createQuery("select t.ItemCode || ':'|| t.ItemName ,t.ItemModel,t.ItemPartNb,t.Qty, t.Rate,t.DiscAmnt ,t.Tax1, t.NetRate, t.Total, "
							+ "t.TotalAt,t.GrNo, t.iPrNo, t.ArNo, t.iCIPNo,t.FarNo, t.pordItemId  from PurchaseOrderItem t where ItemCode IN (:param1)");
				query.setParameter("param1", assetreg.getAritemCode());
				System.out.println(assetreg.getAritemCode());
				 listPurchaseOrderItem = query.list();
				 System.out.println(mapper.writeValueAsString(listPurchaseOrderItem));
					
				}
			
				model.addAttribute("ListARItem", mapper.writeValueAsString(listPurchaseOrderItem));
					System.out.println(mapper.writeValueAsString(listPurchaseOrderItem));
					
			} catch (Exception e) {

				logger.info("Error in Asset Register FormView with a message: "+ e);
				e.printStackTrace();
				
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "AssetRegistryFormView";
	}
	//end of form view
	
	@RequestMapping(value = "/AssetRegistryFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> AssetRegistryFormSave(Locale locale, Model model, HttpServletRequest request, @ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		else
		{
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);
			DateFormat formatterTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			
			Timestamp CreationDate, lastModifiedDate, EndOfLife;
			Date date;
			
			String createdDate = request.getParameter("arcreatedDate");
			String endOfLife = request.getParameter("arendOfLife");
			String AssetRegID = request.getParameter("arID");
			
			String[] slctDelModPart = request.getParameterValues("slctDelModPart[]");
			String[] slctDelNode = request.getParameterValues("slctDelNode[]");
			String[] slctDelSite = request.getParameterValues("slctDelSite[]");
			String[] slctDelSerial = request.getParameterValues("slctDelSerial[]");
			
			AssetRegistry assetreg; ArPartNumber arPartNumber; ArNode arNode; ArSite arSite; ArSerialNumber arSerialNumber;
			
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
		
						assetreg = new AssetRegistry();
						

		date = formatterTime.parse(createdDate);
		CreationDate = new Timestamp(date.getTime());
		
		date = new Timestamp(System.currentTimeMillis());
		lastModifiedDate = new Timestamp(date.getTime());

		date = formatterTime.parse(endOfLife);
		EndOfLife = new Timestamp(date.getTime());
		

		if (StringUtils.equalsIgnoreCase(AssetRegID, "")) {
			synchronized (this) {						
				AssetRegID = "AR_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET ASSET_REGISTRY = ASSET_REGISTRY + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
				}
		}
			//AssetRegID= "AR_"+year+"_" +appConfig.getSequenceNbr(9);
		
		assetreg.setArID(AssetRegID);
		assetreg.setAritemCode(request.getParameter("aritemCode"));
		assetreg.setAritemName(request.getParameter("aritemName"));
		assetreg.setArcreatedDate(CreationDate);
		assetreg.setArlastModifiedDate(lastModifiedDate);
		assetreg.setItemCat(request.getParameter("itemCategory"));
		assetreg.setItemCatCode(request.getParameter("itemCatCode"));
		assetreg.setItemCatID(request.getParameter("itemCatID"));
		assetreg.setItemRootCode(request.getParameter("itemRootCat"));
		assetreg.setArStatus(request.getParameter("arStatus"));
		//assetreg.setSiteID(request.getParameter("siteID"));
		//assetreg.setWareName(request.getParameter("wareName"));
		//assetreg.setWareID(request.getParameter("wareID"));
		assetreg.setArunit(request.getParameter("arunit"));
		assetreg.setAritemDescription(request.getParameter("aritemDescription"));
		assetreg.setArDomain(request.getParameter("arDomain"));
		assetreg.setAritemType(request.getParameter("aritemType"));
		assetreg.setArcableType(request.getParameter("arcableType"));
		assetreg.setArweight(request.getParameter("arweight"));
		assetreg.setArweightUOM(request.getParameter("arweightUOM"));
		assetreg.setArlength(request.getParameter("arlength"));
		assetreg.setArwidth(request.getParameter("arwidth"));
		assetreg.setArheight(request.getParameter("arheight"));
		assetreg.setArsizeUOM(request.getParameter("arsizeUOM"));
		assetreg.setArservice(request.getParameter("arservice").charAt(0));
		assetreg.setArendOfLife(EndOfLife);
		assetreg.setArvaluationRate(Float.parseFloat(request.getParameter("arvaluationRate")));
		assetreg.setArdisabled(request.getParameter("ardisabled").charAt(0));
		assetreg.setAritemImage(request.getParameter("aritemImage"));		
		assetreg.setPosition(request.getParameter("aritemPosition"));
		assetreg.setArwarrantyPeriod(request.getParameter("arwarrantyPeriod"));
		assetreg.setArtech2G(request.getParameter("artech2G").charAt(0));
		assetreg.setArtech3G(request.getParameter("artech3G").charAt(0));
		assetreg.setArtech4G(request.getParameter("artech4G").charAt(0));
		assetreg.setArtech5G(request.getParameter("artech5G").charAt(0));
		assetreg.setItemNameReg(request.getParameter("itemNameReg"));
		assetreg.setArSubDomain(request.getParameter("arSubDomain"));
		assetreg.setArVendor(request.getParameter("arVendor"));
		assetreg.setArType(request.getParameter("arType"));
		
		assetreg.setArdniID(request.getParameter("dniId"));
		assetreg.setPoID(request.getParameter("poId"));
		assetreg.setPoItemId(request.getParameter("poItemId"));
		String cost =request.getParameter("initialCost");
		float initialCost;

		if (cost != null && !cost.isEmpty()) {
			    initialCost = Float.parseFloat(cost);
		} else {
			    initialCost = 0;
		}
		assetreg.setInitialCost(initialCost);
		
		assetreg.setSupplierID(request.getParameter("suppId"));
		assetreg.setSupplierName(request.getParameter("suppName"));
		assetreg.setArsiteId(request.getParameter("arSiteId"));
		
		session.saveOrUpdate(assetreg);
		
		
		
		if(slctDelModPart != null ) {
			query = session.createQuery("delete ArPartNumber where itmId IN (:param1)");
			query.setParameterList("param1", slctDelModPart);
			query.executeUpdate();
		}
		
		
		if( slctDelNode != null) {
			query = session.createQuery("delete ArNode where nodearId IN (:param1)");
			query.setParameterList("param1", slctDelNode);
			query.executeUpdate();
		}
		
	    
		if( slctDelSite != null) {
			query = session.createQuery("delete ArSite where arsiteId IN (:param1)");
			query.setParameterList("param1", slctDelSite);
			query.executeUpdate();
		}
		
		
        if( slctDelSerial != null) {
			query = session.createQuery("delete ArSerialNumber where serialId IN (:param1)");
			query.setParameterList("param1", slctDelSerial);
			query.executeUpdate();
        }
		
		
		float qtyPartNum=0;
		String itmId;
		if (itemParameters.getDictParameteritemPartnum() != null) {
			
			for (int i = 0; i < itemParameters.getDictParameteritemPartnum().size(); i++) {
				
				arPartNumber=new ArPartNumber();
				
				if(StringUtils.equalsIgnoreCase(itemParameters.getDictParameteritemPartnum().get(i).get("arItemID"), "0")) {
					synchronized (this) {						
						itmId = "ARMP_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_MODEL_PARTNO = AR_MODEL_PARTNO + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						}
					//itmId = "ARMP_" + year + "_" + appConfig.getSequenceNbr(26);
					arPartNumber.setItmId(itmId);
				}
				else {
					arPartNumber.setItmId(itemParameters.getDictParameteritemPartnum().get(i).get("arItemID"));
				}
				
				arPartNumber.setItemCode(itemParameters.getDictParameteritemPartnum().get(i).get("itemCode"));
				arPartNumber.setItemPartNb(itemParameters.getDictParameteritemPartnum().get(i).get("itemPartNum"));
				arPartNumber.setItemModel(itemParameters.getDictParameteritemPartnum().get(i).get("itemModel"));
				arPartNumber.setPrimary(itemParameters.getDictParameteritemPartnum().get(i).get("primary"));
				arPartNumber.setArID(AssetRegID);
				if(itemParameters.getDictParameteritemPartnum().get(i).get("qtyPartNum")!="") {
					
					 qtyPartNum=Float.parseFloat(itemParameters.getDictParameteritemPartnum().get(i).get("qtyPartNum"));
					 arPartNumber.setQtyPartNb(qtyPartNum);
				}
				
				session.saveOrUpdate(arPartNumber);
			}
		}
		
		//Node Tab Saving
		if (itemParameters.getDictParameternode() != null) {
		    for (int i = 0; i < itemParameters.getDictParameternode().size(); i++) {
		         arNode = new ArNode();

		        if(StringUtils.equalsIgnoreCase(itemParameters.getDictParameternode().get(i).get("arNodeID"), "0")) {
		            String node_ID = itemParameters.getDictParameternode().get(i).get("nodeID");
		            String nodearId = null;

		            query = session.createSQLQuery("select NODEAR_ID from AR_NODE where Node_ID = :param1 AND AR_ID = :param2");
		            query.setParameter("param1", node_ID);
		            query.setParameter("param2", AssetRegID);

		            Object result = query.uniqueResult();

		            if (result != null) {
		                nodearId = result.toString();
		            }

		            if (nodearId == null) {
		                synchronized (this) {                        
		                    nodearId = "ARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());    
		                    query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
		                    query.executeUpdate();
		                    session.createNativeQuery("commit").executeUpdate();
		                }
		            }

		            arNode.setNodearId(nodearId);
		        } else {
		            arNode.setNodearId(itemParameters.getDictParameternode().get(i).get("arNodeID"));
		        }

		        arNode.setNodeID(itemParameters.getDictParameternode().get(i).get("nodeID"));
		        arNode.setNodeName(itemParameters.getDictParameternode().get(i).get("node_Name"));
		        arNode.setNodeType(itemParameters.getDictParameternode().get(i).get("node_Type"));
		        arNode.setArID(AssetRegID);

		        session.merge(arNode); // Use merge instead of saveOrUpdate to handle detached entities
		    }
		}

	
			//Site Tab Saving
			String arsiteId = null;
			if (itemParameters.getDictParametersite() != null) {
				
				for (int i = 0; i < itemParameters.getDictParametersite().size(); i++) {
					arSite = new ArSite();
					
					if(StringUtils.equalsIgnoreCase(itemParameters.getDictParametersite().get(i).get("arSiteID"), "0")) {
						synchronized (this) {						
							arsiteId = "ARSITE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_SITE = AR_SITE + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							}
						arSite.setArsiteId(arsiteId);
					}
					else {
						arSite.setArsiteId(itemParameters.getDictParametersite().get(i).get("arSiteID"));
					}
					
					arSite.setSiteID(itemParameters.getDictParametersite().get(i).get("siteID"));
					arSite.setSiteName(itemParameters.getDictParametersite().get(i).get("siteName"));
					arSite.setWareID(itemParameters.getDictParametersite().get(i).get("wareID"));
			
					arSite.setArID(AssetRegID);
					session.saveOrUpdate(arSite);
				}
		         
			}

				//Serial Nmbr Tab Saving 
				String serialId = null;
				if (itemParameters.getDictParameterserialNumber() != null) {
				
				for (int i = 0; i < itemParameters.getDictParameterserialNumber().size(); i++) {
				arSerialNumber = new ArSerialNumber();
				
				if(StringUtils.equalsIgnoreCase(itemParameters.getDictParameterserialNumber().get(i).get("arSerialID"), "0")) {
					synchronized (this) {						
						serialId = "ARSNUM_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						}
					//serialId = "ARSNUM_" + year + "_" + appConfig.getSequenceNbr(31);
					arSerialNumber.setSerialId(serialId);
				}
				else {
					arSerialNumber.setSerialId(itemParameters.getDictParameterserialNumber().get(i).get("arSerialID"));
				}
				
				arSerialNumber.setSerialNumber(itemParameters.getDictParameterserialNumber().get(i).get("SNserialNumber"));
				arSerialNumber.setMacAddress(itemParameters.getDictParameterserialNumber().get(i).get("SNMacAddress"));
				arSerialNumber.setModel(itemParameters.getDictParameterserialNumber().get(i).get("SNmodel"));
				arSerialNumber.setPartNumber(itemParameters.getDictParameterserialNumber().get(i).get("SNpartNumber"));
				arSerialNumber.setSite(itemParameters.getDictParameterserialNumber().get(i).get("SNsite"));
				arSerialNumber.setPosition(itemParameters.getDictParameterserialNumber().get(i).get("SNposition"));
				arSerialNumber.setArID(AssetRegID);				session.saveOrUpdate(arSerialNumber);
				
				}
				
				}
			
			tx.commit();
			session.close();
					
		
		rtn.put("arID", AssetRegID);
		rtn.put("arlastModifiedDate", formatterTime.format(new Timestamp(System.currentTimeMillis())).toString());
		
		} catch (Exception e) {
			logger.info("Error in saving Asset Register with a message : " + e);
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
	
	@RequestMapping(value = "/AssetRegistryFormViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> AssetRegistryFormViewDelete(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
				
		String idForm = request.getParameter("arID");
		session = AlmDbSession.getInstance().getSession();
		
					if (session != null && session.isOpen()) {
			            tx = session.beginTransaction();
			            try {
			                            query = session.createNativeQuery("Delete ASSET_REGISTRY where AR_ID = '"+ idForm +"'");
			                            query.executeUpdate();

			                            query = session.createNativeQuery("delete AR_MODEL_PARTNUMBER  where AR_ID = '"+ idForm +"'");
			                            query.executeUpdate();

			                            query = session.createNativeQuery("delete AR_NODE  where AR_ID = '"+ idForm +"'");
			                            query.executeUpdate();

			                            query = session.createNativeQuery("delete AR_SERIAL_NUMBER  where AR_ID = '"+ idForm +"'");
			                            query.executeUpdate();

			                            query = session.createNativeQuery("delete AR_SITE  where AR_ID = '"+ idForm +"'");
			                            query.executeUpdate();


			            } catch (Exception e) {
			                            System.out.println("Error in creating session with the AssetRegistryFormViewDelete method " + e.getMessage());
			                            logger.info("could not connect to DB in AssetRegistryFormViewDelete method ");
			            }
			
			            finally {
			                            if (session != null && session.isOpen()) {
			                                            tx.commit();
			                                            session.close();
			                            }
			            }
					} else {
					            System.out.println("could not connect to DB in deleteClient method");
					            logger.info("could not connect to DB in deleteClient method ");
					}

		rtn.put("BassamTest", "DeleteDone");
		return rtn;
	}	
	
	@RequestMapping(value = "/AssetRegistryListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> AssetRegistryListViewDelete(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
		String idForm=null;	
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();		
		String[] idList = request.getParameterValues("arID[]");

		if (session != null && session.isOpen()) {
            tx = session.beginTransaction();
            try {
				for(int i= 0; i<idList.length;i++) {
					//Get AR_ID from the listview form
					idForm=idList[i];
					
			            	 query = session.createNativeQuery("Delete ASSET_REGISTRY where AR_ID = '"+ idForm +"'");
		                     query.executeUpdate();

		                     query = session.createNativeQuery("delete AR_MODEL_PARTNUMBER  where AR_ID = '"+ idForm +"'");
		                     query.executeUpdate();

		                     query = session.createNativeQuery("delete AR_NODE  where AR_ID = '"+ idForm +"'");
		                     query.executeUpdate();

		                     query = session.createNativeQuery("delete AR_SERIAL_NUMBER  where AR_ID = '"+ idForm +"'");
		                     query.executeUpdate();

		                     query = session.createNativeQuery("delete AR_SITE  where AR_ID = '"+ idForm +"'");
		                     query.executeUpdate();

					}
				
            } catch (Exception e) {
                System.out.println("Error in creating session with the AssetRegistryFormViewDelete method " + e.getMessage());
                logger.info("could not connect to DB in AssetRegistryFormViewDelete method ");
			}
			
			finally {
			        if (session != null && session.isOpen()) {
			            tx.commit();
			            session.close();
			         }
			}
			} else {
			        System.out.println("could not connect to DB in AssetRegistryFormViewDelete method");
			        logger.info("could not connect to DB in AssetRegistryFormViewDelete method ");
			}
				
		rtn.put("BassamTest", "DeleteDone");
		return rtn;
		
		
	}
	
	// getting All AR using autocomplete in navigation
	@RequestMapping(value = "/GetAllAR", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllAR(Locale locale, Model model, HttpServletRequest request, 
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
				query = session.createNativeQuery("select AR_ID, AR_STATUS, ITEM_CODE, ITEM_NAME from ASSET_REGISTRY where LOWER(AR_ID) like LOWER(:param1) "
						+ "or LOWER(AR_STATUS) like LOWER(:param1) ORDER BY LAST_MODIFIED_DATE DESC");
				query.setString("param1", "%"+requestValue+"%");
		
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("listAR", query.list());
	
			}catch(Exception e) {
				
				logger.info(
						"Error while getting AR using autocomplete with error message: "+ e);
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

	static double haversine(double lat1, double lon1, double lat2, double lon2) {
		// distance between latitudes and longitudes
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		// convert to radians
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// apply formulae
		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double rad = 6371;
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;

	}	

}
