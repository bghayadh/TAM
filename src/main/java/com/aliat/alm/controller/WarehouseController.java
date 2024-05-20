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
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
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

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.Warehouse;
import com.aliat.alm.models.WarehousePassive;
import com.aliat.alm.models.WarehouseProfitloss;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;

@Controller
public class WarehouseController {
	private static final String START_DATE = "startDate";
	private static final String END_DATE = "endDate";
	private static final String PROFIT_LOSS_ID = "ProfitLossID";
	private static final String POPULATION = "population";
	private static final String TECH_2g = "tech_2g";
	private static final String TECH_3g = "tech_3g";
	private static final String TECH_4g = "tech_4g";
	private static final String TECH_5g = "tech_5g";

	private static final String Utilization2G = "Utilization2g";
	private static final String Utilization3G = "Utilization3g";
	private static final String Utilization4G = "Utilization4g";
	private static final String Utilization5G = "Utilization5g";
	private static final String Availability2G = "Availability2G";
	private static final String Availability3G = "Availability3G";
	private static final String Availability4G = "Availability4G";
	private static final String Availability5G = "Availability5G";
	private static final String GROSS_ADS = "grossADS";
	private static final String COUNT_OF_SSO = "countOfSSO";
	private static final String CUST_BASE = "customerBase";
	private static final String DATA = "data";
	private static final String VOICE = "voice";
	private static final String SMS_REVENUE = "smsRevenue";
	private static final String GROSS_REVENUE = "grossRevenue";
	private static final String DATA_TRAFFIC = "dataTraffic";
	private static final String TOTAL_SMS_TRAFFIC_OG = "totalSMSTrafficOG";
	private static final String TOTAL_VOICE_TRAFFIC_OG = "totalVoiceTrafficOG";
	private static final String TOTAL_SMS_TRAFFIC_IC = "totalSMSTrafficIC";
	private static final String TOTAL_VOICE_TRAFFIC_IC = "totalVoiceTrafficIC";
	private static final String TOTAL_OPEX_MONTHLY = "totalOpexMon";
	private static final String PROFIT_AND_LOSS = "ProfitAndLoss";

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private String str = null;
	private String siteimgID, siteimgName;
	
	

	@Autowired
	ALMSessions almsessions;

	@Autowired
	Form form;

	@Autowired
	Notify notifications;

	@RequestMapping(value = "/WarehouseListView", method = RequestMethod.GET)
	public String WarehouseListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				
				try {
					notifications.headerNotifications(session, model);
					//List<WareHouseListView> listAR = new ArrayList<WareHouseListView>();
	
						str = "select WARE_ID as wareID,WARE_ID as warehouseID,WARE_NAME as warehouseName,SITE_ID as wareSiteID,Status as whStatus, TO_CHAR ( LAST_MODIFY_DATE , 'YYYY-MM-DD HH24:MI:SS') as wlastModifieddate,"
								+ " AREA_NAME as areaName,LONGITUDE as wareLong,LATITUDE as wareLat,"
								+ "CITY as wareCity,REGION_NAME as regionName from WAREHOUSE order by LAST_MODIFY_DATE DESC";
	
						//listAR = entityManager.createNativeQuery(str).getResultList(); //  setResultTransformer(Transformers.aliasToBean(WareHouseListView.class)).list();
	
						model.addAttribute("ListGridTable", mapper.writeValueAsString(session.createNativeQuery(str).getResultList()));
	
					} catch (Exception e) {
						logger.info("Error on WarehouseListView with a message : " + e);
						e.printStackTrace();
						model.addAttribute("ListGridTable", "");
					} finally {
						if (session != null && session.isOpen()) {
							session.close();
						}
						
					}
				}
			
			return "WarehouseListView";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredWarehouseListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredWarehouseListView(Locale locale, Model model, HttpServletRequest request,
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

				String startdate, enddate, regionid, regionname, areaid, areaname, startlong, startlat, endlong, endlat,
						longg, lat, radius;

				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				regionid = request.getParameter("regionid");
				regionname = request.getParameter("regionname");
				areaid = request.getParameter("areaid");
				areaname = request.getParameter("areaname");

				startlong = request.getParameter("startlong");
				startlat = request.getParameter("startlat");
				endlong = request.getParameter("endlong");
				endlat = request.getParameter("endlat");

				longg = request.getParameter("longg");
				lat = request.getParameter("lat");
				radius = request.getParameter("radius");
				double distance = 0.0;

				List<Object[]> listCrclWare = new ArrayList<Object[]>();
				List<Object[]> listwarehouse = new ArrayList<Object[]>();

				str = "select WARE_ID as wareID,WARE_ID as warehouseID,WARE_NAME as warehouseName,SITE_ID as wareSiteID,TO_CHAR(LAST_MODIFY_DATE, 'YYYY-MM-DD HH24:MI:SS') as wlastModifieddate,"
						+ " AREA_NAME as areaName,LONGITUDE as wareLong,LATITUDE as wareLat,"
						+ "CITY as wareCity,REGION_NAME as regionName from WAREHOUSE";

				if (startdate != null && enddate != null) {
					str = str + " where CREATION_DATE between TO_DATE('" + startdate + "','YYYY-MM-DD') and TO_DATE('"
							+ enddate + "','YYYY-MM-DD')";
				}

				if (regionid != null && !regionid.equalsIgnoreCase("")) {

					str = str + " and (upper(REGION_ID) LIKE upper('%" + regionid + "%'))";
				}
				if (regionname != null && !regionname.equalsIgnoreCase("")) {

					str = str + " and (upper(REGION_NAME) LIKE upper('%" + regionname + "%'))";
				}
				if (areaid != null && !areaid.equalsIgnoreCase("")) {

					str = str + " and (upper(AREA_ID) LIKE upper('%" + areaid + "%'))";
				}
				if (areaname != null && !areaname.equalsIgnoreCase("")) {

					str = str + " and (upper(AREA_NAME) LIKE upper('%" + areaname + "%'))";
				}

				if (startlong != null && !startlong.equalsIgnoreCase("")
						&& (endlong == null || endlong.equalsIgnoreCase(""))) {

					str = str + " and (TO_NUMBER(LONGITUDE)) > " + startlong;

				}
				if (endlong != null && !endlong.equalsIgnoreCase("")
						&& (startlong == null || startlong.equalsIgnoreCase(""))) {

					str = str + " and (TO_NUMBER(LONGITUDE)) < " + endlong;

				}

				if (startlong != null && endlong != null) {
					String startlong1;
					String endlong1;

					if (startlong != null && startlong.length() > 0 && (endlong != null && endlong.length() > 0)) {
						if (Double.parseDouble(startlong) < Double.parseDouble(endlong)) {
							startlong1 = startlong;
							endlong1 = endlong;

						} else {
							startlong1 = endlong;
							endlong1 = startlong;

						}
						str = str + " and (TO_NUMBER(LONGITUDE)) > " + startlong1;
						str = str + " and (TO_NUMBER(LONGITUDE)) < " + endlong1;

					}
				}
				if (startlat != null && !startlat.equalsIgnoreCase("")
						&& (endlat == null || endlat.equalsIgnoreCase(""))) {

					str = str + " and (TO_NUMBER(LATITUDE)) > " + startlat;

				}
				if (endlat != null && !endlat.equalsIgnoreCase("")
						&& (startlat == null || startlat.equalsIgnoreCase(""))) {

					str = str + " and (TO_NUMBER(LATITUDE)) < " + endlat;

				}

				if (startlat != null && endlat != null) {
					String startlat1;
					String endlat1;

					if (startlat != null && startlat.length() > 0 && (endlat != null && endlat.length() > 0)) {
						if (Double.parseDouble(startlat) < Double.parseDouble(endlat)) {
							startlat1 = startlat;
							endlat1 = endlat;

						} else {
							startlat1 = endlat;
							endlat1 = startlat;

						}
						str = str + " and (TO_NUMBER(LATITUDE)) > " + startlat1;
						str = str + " and (TO_NUMBER(LATITUDE)) < " + endlat1;

					}
				}
				str = str + " ORDER BY LAST_MODIFY_DATE DESC ";

				listwarehouse = session.createNativeQuery(str).list();

				if ((radius != null && !radius.equalsIgnoreCase("") && Double.parseDouble(radius) > 0)
						&& (longg != null && !longg.equalsIgnoreCase(""))
						&& (lat != null && !lat.equalsIgnoreCase(""))) {

					for (int i = 0; i < listwarehouse.size(); i++) {

						distance = haversine(Double.parseDouble(longg), Double.parseDouble(lat),
								Double.valueOf(listwarehouse.get(i)[6].toString()),
								Double.valueOf(listwarehouse.get(i)[7].toString()));

						if (distance <= Double.parseDouble(radius)) {
							listCrclWare.add(listwarehouse.get(i));
						}
					}

					rtn.put("listwarehouse", listCrclWare);
				} else {
					rtn.put("listwarehouse", listwarehouse);
					System.out.println("Filtered Array: " + mapper.writeValueAsString(listwarehouse));
				}
				session.clear();

			} catch (Exception e) {
				logger.info("Error in showing the filtered warehouse list view with a message :" + e);
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
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

	public String convSpecialChar(String htmlString) {
		htmlString = htmlString.replaceAll("\\<.*?\\>", "");
		htmlString = htmlString.replaceAll("\r", "<br/>");
		htmlString = htmlString.replaceAll("\n", "<br/>");
		htmlString = htmlString.replaceAll("\"", "&quot;");
		htmlString = htmlString.replaceAll("\'", "&#39;");
		htmlString = htmlString.replaceAll(",", "&#44;");
		return htmlString;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/WarehouseFormView", method = RequestMethod.GET)
	public String WarehouseFormView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Object[] Result;

		Double initialcost = null, accumPer = null, totalNetCost = null;
		String wareID, WarehouseN, wareCity, navAction = "2";
		List<WarehouseProfitloss> listWareProLoss = new ArrayList<>();
		WarehousePassive warePassive;
		Warehouse wareh;
		String result[] = new String[4];
		int SelectedIndex = 0;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			notifications.headerNotifications(session, model);

			try {
				tx=session.beginTransaction();
				
				wareID = request.getParameter("wareID");
				navAction = request.getParameter("NavAction");

				// to open supplier when click on ADD from supplier List
				if (wareID == null) {
					model.addAttribute("wcreationDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("wlastModifieddate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("docStatus", "addNew");
					model.addAttribute("listWareProLoss", "addNew");
					model.addAttribute("SelectedIndex", "addNew");
					model.addAttribute("wareCount", "addNew");
					model.addAttribute("listSiteImage", "addNew");
					model.addAttribute("Listnode", "addNew");
					model.addAttribute("ListGCell", "addNew");
					model.addAttribute("ListUCell", "addNew");
					model.addAttribute("ListLCell", "addNew");
					model.addAttribute("eachNodeTypeCount", "addNew");
					model.addAttribute("listInventory", "addNew");

					return "WarehouseFormView";
				}

				result = form.NavigationNP(session, "WAREHOUSE", "WARE_ID", wareID, "LAST_MODIFY_DATE", navAction);

				SelectedIndex = Integer.parseInt(result[1]);
				wareID = result[2];

				model.addAttribute("SelectedIndex", SelectedIndex);
				model.addAttribute("wareCount", Integer.parseInt(result[0]));

				wareh = (Warehouse) session.get(Warehouse.class, wareID);

				if (wareh != null) {
					model.addAttribute("wareID", wareh.getWareID());
					if (wareh.getWcreationDate() == null) {
						model.addAttribute("wcreationDate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());

					} else {
						model.addAttribute("wcreationDate", formatter.format(wareh.getWcreationDate()).toString());
					}

					if (wareh.getWlastModifieddate() == null) {
						model.addAttribute("wlastModifieddate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());

					} else {
						model.addAttribute("wlastModifieddate",
								formatter.format((wareh.getWlastModifieddate())).toString());
					}

					WarehouseN = convSpecialChar(wareh.getWarehouseName());
					model.addAttribute("warehouseName", WarehouseN);
					model.addAttribute("wareCity", wareh.getWareCity());
					model.addAttribute("wareLong", wareh.getWareLong());
					model.addAttribute("wareLat", wareh.getWareLat());
					if (wareh.getWareSite() == '1') {
						model.addAttribute("wareSite", "checked");
					} else {
						model.addAttribute("wareSite", "");
					}
					model.addAttribute("siteId", wareh.getWareSiteID());
					model.addAttribute("areaID", wareh.getAreaID());
					model.addAttribute("areaName", wareh.getAreaName());
					model.addAttribute("regionID", wareh.getRegionID());
					model.addAttribute("regionName", wareh.getRegionName());
					model.addAttribute("clusterID", wareh.getClusterID());
					model.addAttribute("clusterName", wareh.getClusterName());
					model.addAttribute("ordStatus", wareh.getWhStatus());

					if (wareh.getTech2G() == '1') {
						model.addAttribute("tech2G", "checked");
					} else {
						model.addAttribute("tech2G", "");
					}
					if (wareh.getTech3G() == '1') {
						model.addAttribute("tech3G", "checked");
					} else {
						model.addAttribute("tech3G", "");
					}
					if (wareh.getTech4G() == '1') {
						model.addAttribute("tech4G", "checked");
					} else {
						model.addAttribute("tech4G", "");
					}
					if (wareh.getTech5G() == '1') {
						model.addAttribute("tech5G", "checked");
					} else {
						model.addAttribute("tech5G", "");
					}

					model.addAttribute("wareAddress", wareh.getAddress());
					model.addAttribute("wareCombination", wareh.getCombtech());
					
					
					if (!(wareh.getPopulation() instanceof Integer)) {
						model.addAttribute("population", "0");

					}
					else { 
						model.addAttribute("population", wareh.getPopulation());

					}
			
					warePassive = (WarehousePassive) session.get(WarehousePassive.class, wareID);

					if (warePassive != null) {
						model.addAttribute("siteMode", warePassive.getSiteMode());
						model.addAttribute("existing_newtown", warePassive.getExiNewTown());
						model.addAttribute("showcase", warePassive.getShowCase());
						model.addAttribute("siteOwner", warePassive.getSiteOwner());
						model.addAttribute("towerCoID", warePassive.getTowerCoID());
						model.addAttribute("towerType", warePassive.getTowerType());
						model.addAttribute("towerHeight", warePassive.getTowerHeight());
						model.addAttribute("buildingHeight", warePassive.getBuildheight());
						model.addAttribute("shared", warePassive.getShared());
						model.addAttribute("icrCategory", warePassive.getIcrCategory());
						model.addAttribute("transmission", warePassive.getTransmission());
						model.addAttribute("hubSite", warePassive.getHubSite());
						model.addAttribute("locationNotes", warePassive.getLocationNotes());
						model.addAttribute("shelter", warePassive.getShelter());
						model.addAttribute("shelterID", warePassive.getShelterID());
						model.addAttribute("shelterType", warePassive.getShelterType());
						model.addAttribute("shelterVendor", warePassive.getShelterVenfdor());
						if (warePassive.getShelterWithWithout() == '1') {
							model.addAttribute("wshelter", "checked");
						} else {
							model.addAttribute("wshelter", "");
						}

					}

					System.out.println(wareID);
					query = session.createQuery(
							"SELECT nvl(SUM(t.iniCost),'0') as initialcost ,nvl(SUM(t.accumPer),'0') as deprec,nvl(SUM(t.netCost),'0') as netcost FROM FixedAssetRegistry t where t.FarsiteId =:param1");
					query.setParameter("param1", wareID);

					Result = (Object[]) query.uniqueResult();

					initialcost = (double) Math.round(Double.parseDouble(Result[0].toString()) * 100) / 100;
					model.addAttribute("InitialCost", initialcost);

					accumPer = (double) Math.round(Double.parseDouble(Result[1].toString()) * 100) / 100;
					model.addAttribute("accumPer", accumPer);

					totalNetCost = (double) Math.round(Double.parseDouble(Result[2].toString()) * 100) / 100;
					model.addAttribute("totalNetCost", totalNetCost);

					query = session.createQuery(
							"select t.id as id, t.startDate as startDate, t.endDate as endDate, t.population as population, t.tech2G as tech2G, t.tech3G as tech3G, t.tech4G as tech4G, t.tech5G as tech5G, t.utilization2G as utilization2G, t.utilization3G as utilization3G, t.utilization4G as utilization4G, t.utilization5G as utilization5G, t.availability2G as availability2G, t.availability3G as availability3G, t.availability4G as availability4G, t.availability5G as availability5G, t.grossADS as grossADS, t.countOfSSO as countOfSSO, t.custBase as custBase, t.data as data, t.voice as voice, t.smsRevenu as smsRevenu, t.grossRevenu as grossRevenu, t.datatraffic as datatraffic, t.totalSmsTraOG as totalSmsTraOG, t.totalVoiceTraOG as totalVoiceTraOG, t.totalSmsTraIC as totalSmsTraIC, t.totalVoiceTraIC as totalVoiceTraIC, t.totalOpexMon as totalOpexMon, t.profitAndLoss as profitAndLoss from WarehouseProfitloss t where t.wareID =:param1");
					query.setParameter("param1", wareID);

					listWareProLoss = (List<WarehouseProfitloss>) query
							.setResultTransformer(Transformers.aliasToBean(WarehouseProfitloss.class)).list();
					model.addAttribute("listWareProLoss", mapper.writeValueAsString(listWareProLoss));

					wareCity = wareh.getWareCity();
					query = session.createQuery(
							"select wareLat,wareLong,address,wareCity,areaName from Warehouse where wareCity like:param1");
					;
					query.setParameter("param1", wareCity);

					model.addAttribute("listNearWares", mapper.writeValueAsString(query.list()));
					// get inventory info
/*
					str = "SELECT ar.Item_code, ar.Item_Name, ar.Item_Model, ar.Item_part_number, COUNT(*) AS quantity, "
							+ "SUM(COALESCE(ar.Initial_Cost, " + "(SELECT AVG(Net_Rate) "
							+ "FROM purchase_order_item poi " + "WHERE poi.Item_code = ar.Item_code)"
							+ ")) AS Total_Initial_Cost, "
							+ "SUM(COALESCE(far.ACCUMULDEPRECAMNT, 0)) AS Total_Depreciation, "
							+ "SUM(COALESCE(far.NetCost, COALESCE(ar.Initial_Cost, (SELECT AVG(Net_Rate) "
							+ "FROM purchase_order_item poi "
							+ "WHERE poi.Item_code = ar.Item_code)))) AS Total_Net_Cost " + "FROM asset_registry ar "
							+ "LEFT JOIN fixed_asset_registry far ON ar.Ar_ID = far.Ar_ID "
							+ "WHERE ar.Ar_ID IN (SELECT Ar_ID FROM AR_SITE WHERE WARE_ID = :param1) "
							+ "GROUP BY ar.Item_code, ar.Item_Name, ar.Item_Model, ar.Item_part_number"; 
					
					
					str = "SELECT fmp.Item_code, i.Item_Name, fmp.Item_Model, fmp.Item_part_number, COUNT(*) AS quantity, "
							+ "SUM(COALESCE(far.Initial_Cost, " + "(SELECT AVG(Net_Rate) "
							+ "FROM purchase_order_item poi " + "WHERE poi.Item_code = fmp.Item_code)"
							+ ")) AS Total_Initial_Cost, "
							+ "SUM(COALESCE(far.ACCUMULDEPRECAMNT, 0)) AS Total_Depreciation, "
							+ "SUM(COALESCE(far.NetCost, SELECT AVG(Net_Rate) "
							+ "FROM purchase_order_item poi WHERE poi.Item_code = fmp.Item_code "
							+ ")) AS Total_Net_Cost " 
							+ "FROM fixed_asset_registry far, far_model_partnumber fmp, item i "
							+ "WHERE far.FAR_ID IN (SELECT FAR_ID FROM FAR_SITE WHERE WARE_ID = :param1) "
							+ "AND fmp.item_code = i.item_code "
							+ "GROUP BY fmp.Item_code, i.Item_Name, fmp.Item_Model, fmp.Item_part_number"; */
					
					str = "SELECT fmp.Item_code, i.Item_Name, fmp.Item_Model, fmp.Item_part_number, COUNT(*) AS quantity, "
							+ "SUM(COALESCE(far.InitialCost, (SELECT AVG(Net_Rate) "
							+ "FROM purchase_order_item poi WHERE poi.Item_code = fmp.Item_code))) AS Total_Initial_Cost, "					
							+ "SUM(COALESCE(far.ACCUMULDEPRECAMNT, 0)) AS Total_Depreciation, "
							+ "SUM(COALESCE(far.NetCost, (SELECT AVG(Net_Rate) "
							+ "FROM purchase_order_item poi WHERE poi.Item_code = fmp.Item_code))) AS Total_Net_Cost "
							+ "FROM fixed_asset_registry far, far_model_partnumber fmp, item i "
							+ "WHERE far.FAR_ID IN (SELECT FAR_ID FROM FAR_SITE WHERE WARE_ID = :param1) "
							+ "AND fmp.item_code = i.item_code "
							+ "AND far.far_id = fmp.far_id "
							+ "GROUP BY fmp.Item_code, i.Item_Name, fmp.Item_Model, fmp.Item_part_number";					

					query = session.createNativeQuery(str);
					query.setParameter("param1", wareID);

					model.addAttribute("listInventory", mapper.writeValueAsString(query.list()));

					str = "SELECT COUNT(UNIQUE_NODE_ID) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='" + wareID
							+ "'";
					model.addAttribute("nodes",
							mapper.writeValueAsString(session.createNativeQuery(str).uniqueResult()));

					str = "select count(ngc.gcell_id) from NODE_2GCELL ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"
							+ wareID + "'";
					model.addAttribute("g-cell",
							mapper.writeValueAsString(session.createNativeQuery(str).uniqueResult()));

					str = "select count(nlc.LCell_Id) from NODE_4GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
							+ wareID + "'";
					model.addAttribute("l-cell",
							mapper.writeValueAsString(session.createNativeQuery(str).uniqueResult()));

					str = "select count(nlc.UCell_Id) from NODE_3GCELL nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
							+ wareID + "'";
					model.addAttribute("u-cell",
							mapper.writeValueAsString(session.createNativeQuery(str).uniqueResult()));

					str = "SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"
							+ wareID + "'";
					model.addAttribute("nodeType",
							mapper.writeValueAsString(session.createNativeQuery(str).uniqueResult()));

					str = "select distinct (node_type) as node_type, count(*) from node_active where active_record = 1 and ware_id = '"
							+ wareID + "' group by node_type";
					model.addAttribute("eachNodeTypeCount",
							mapper.writeValueAsString(session.createNativeQuery(str).list()));

					str = "SELECT node_id, node_name, node_type,domain,vendor,TECH_2G,TECH_3G,TECH_4G,TECH_5G,Node_model from node_active where WARE_ID=:param1";
					query = session.createNativeQuery(str);
					query.setParameter("param1", wareID);
					model.addAttribute("Listnode", mapper.writeValueAsString(query.list()));

					str = " select GCELL_ID ,CELLNAME FROM NODE_2GCELL WHERE NODE_PK IN (Select NODE_PK from node_active where WARE_ID=:param1)";
					query = session.createNativeQuery(str);
					query.setParameter("param1", wareID);
					model.addAttribute("ListGCell", mapper.writeValueAsString(query.list()));

					str = " select LCELL_ID ,CELLNAME FROM NODE_4GCELL WHERE NODE_PK IN (Select NODE_PK from node_active where WARE_ID=:param1)";
					query = session.createNativeQuery(str);
					query.setParameter("param1", wareID);
					model.addAttribute("ListLCell", mapper.writeValueAsString(query.list()));

					str = " select UCELL_ID ,CELLNAME FROM NODE_3GCELL WHERE NODE_PK IN (Select NODE_PK from node_active where WARE_ID=:param1)";
					query = session.createNativeQuery(str);
					query.setParameter("param1", wareID);
					model.addAttribute("ListUCell", mapper.writeValueAsString(query.list()));

					// get site_IMAGE_NAME
					query = session
							.createNativeQuery("select IMAGE_PATH from WAREHOUSE_IMAGE where WARE_ID like :param1");
					query.setParameter("param1", wareh);
					String imgpath = mapper.writeValueAsString(query.list());

					if (!imgpath.equalsIgnoreCase("[]")) {
						String data = imgpath.substring(2, Integer.parseInt(String.valueOf(imgpath.length())));
						StringBuffer sb = new StringBuffer(data);
						sb.deleteCharAt(sb.length() - 1);
						sb.deleteCharAt(sb.length() - 1);
						String data1 = String.valueOf(sb).trim();

						String[] imgresult = null, imgres2 = null, scanlist = null;

						if (data1.contains(",")) {
							imgresult = data1.split(",");
							for (int i = 0; i < imgresult.length; i++) {

								imgres2 = imgresult[i].split(wareID);
								imgres2[1] = imgres2[1].replace("/", "");
								imgres2[1] = imgres2[1].replace("\"", "");
								scanlist = ArrayUtils.add(scanlist, imgres2[1]);
							}

						} else {
							imgres2 = data1.split(wareID);
							imgres2[1] = imgres2[1].replace("/", "");
							imgres2[1] = imgres2[1].replace("\"", "");
							scanlist = ArrayUtils.add(scanlist, imgres2[1]);

						}

						System.out.println("NEW RESULT = " + mapper.writeValueAsString(scanlist));
						model.addAttribute("listSiteImage", mapper.writeValueAsString(scanlist));
						siteimgID = wareID;
						siteimgName = mapper.writeValueAsString(scanlist);
						imagetoweb.start();

					} else {
						model.addAttribute("listSiteImage", "addNew");
					}
					session.flush();
					session.clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("catch messsage is " + e.getMessage());
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
					
				}
			}
		}

		return "WarehouseFormView";
	}
/*
	@RequestMapping(value = "/WarehouseBOQ", method = RequestMethod.GET)
	public void WarehouseBOQ(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Object[] Result;

		Double initialcost = null, accumPer = null, totalNetCost = null;
		String wareID, WarehouseN, wareCity, navAction = "2";
		List<WarehouseProfitloss> listWareProLoss = new ArrayList<>();
		WarehousePassive warePassive;
		Warehouse wareh;
		String result[] = new String[4];
		int SelectedIndex = 0;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {
				wareID = request.getParameter("wareID");
				navAction = request.getParameter("NavAction");

				// get inventory info

				query = session.createSQLQuery("SELECT site_id from warehouse where ware_id=:param1");
				query.setParameter("param1", wareID);
				String siteId = (String) query.uniqueResult();

				System.out.println(siteId);

				List<Object[]> Inventory = new ArrayList<Object[]>();

				query = session.createSQLQuery(
						"SELECT Item_code, Item_Name, Item_Model, Item_part_number, COUNT(*) AS quantity "
								+ "FROM asset_registry where Ar_ID in (select Ar_ID from AR_SITE where Site_id=:param1) "
								+ "GROUP BY Item_code, Item_Name, Item_part_number, Item_Model");

				query.setParameter("param1", siteId);
				ObjectMapper mapper = new ObjectMapper();
				String resultList = mapper.writeValueAsString(query.list());
				;

				System.out.println(resultList);

				List<List<Object>> listOfLists = mapper.readValue(resultList, new TypeReference<List<List<Object>>>() {
				});

				for (List<Object> innerList : listOfLists) {
					System.out.println(innerList.get(0));
				}
				model.addAttribute("listInventory", mapper.writeValueAsString(query.list()));

				LinkedHashMap<String, String> BoqHM = new LinkedHashMap<String, String>();

				String Site_Query = "Select DISTINCT   Ware_Name From NODE_ACTIVE where Ware_Id='" + wareID + "'";
				Object Sites = session.createSQLQuery(Site_Query).uniqueResult();
				model.addAttribute("siteName", mapper.writeValueAsString(Sites));

				String Node_Active_Query = wareID == ""
						? "SELECT COUNT(distinct UNIQUE_NODE_ID) FROM NODE_ACTIVE where Active_record='1'"
						: "SELECT COUNT(UNIQUE_NODE_ID) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='" + wareID
								+ "'";

				Object CountNodes_Active = session.createSQLQuery(Node_Active_Query).uniqueResult();

				model.addAttribute("nodes", mapper.writeValueAsString(CountNodes_Active));

				String Node_GCell_Query = wareID == "" ? "SELECT COUNT(GCELL_ID) FROM NODE_GCELL"
						: "select count(ngc.gcell_id) from node_gcell ngc , node_active na where na.node_pk = ngc.node_pk and na.Ware_Id = '"
								+ wareID + "'";
				Object CountNodes_G_CELL = session.createSQLQuery(Node_GCell_Query).uniqueResult();
				model.addAttribute("g-cell", mapper.writeValueAsString(CountNodes_G_CELL));

				String Node_LCell_Query = wareID == "" ? "SELECT COUNT(LCELL_ID) FROM NODE_LCELL"
						: "select count(nlc.LCell_Id) from node_lcell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
								+ wareID + "'";
				Object CountNodes_L_CELL = session.createSQLQuery(Node_LCell_Query).uniqueResult();
				model.addAttribute("l-cell", mapper.writeValueAsString(CountNodes_L_CELL));

				String Node_UCell_Query = wareID == "" ? "SELECT COUNT(UCELL_ID) FROM NODE_UCELL"
						: "select count(nlc.UCell_Id) from node_ucell nlc , node_active na where na.node_pk = nlc.node_pk and na.Ware_Id = '"
								+ wareID + "'";
				Object CountNodes_U_CELL = session.createSQLQuery(Node_UCell_Query).uniqueResult();
				model.addAttribute("u-cell", mapper.writeValueAsString(CountNodes_U_CELL));

				String Node_Type_Count = "SELECT COUNT(distinct NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and Ware_Id='"
						+ wareID + "'";
				Object CountNodesType = session.createSQLQuery(Node_Type_Count).uniqueResult();
				model.addAttribute("nodeType", mapper.writeValueAsString(CountNodesType));

				String SRanBs = "SELECT COUNT( NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and NODE_TYPE='SRanBs' and Ware_Id='"
						+ wareID + "'";
				Object SRanBscount = session.createSQLQuery(SRanBs).uniqueResult();
				model.addAttribute("SRanBscount", mapper.writeValueAsString(SRanBscount));

				String IDU = "SELECT COUNT( NODE_TYPE) FROM NODE_ACTIVE where Active_record='1' and NODE_TYPE='IDU' and Ware_Id='"
						+ wareID + "'";
				Object IDUcount = session.createSQLQuery(IDU).uniqueResult();
				model.addAttribute("IDUcount", mapper.writeValueAsString(IDUcount));

				query = session.createQuery(
						"SELECT nvl(SUM(t.iniCost),'0') as initialcost ,nvl(SUM(t.accumPer),'0') as deprec,nvl(SUM(t.netCost),'0') as netcost FROM FixedAssetRegistry t where t.wareID =:param1");
				query.setParameter("param1", wareID);

				Result = (Object[]) query.uniqueResult();

				double initialcost1 = (double) Math.round(Double.parseDouble(Result[0].toString()) * 100) / 100;
				model.addAttribute("InitialCost1", initialcost1);

				double accumPer1 = (double) Math.round(Double.parseDouble(Result[1].toString()) * 100) / 100;
				model.addAttribute("accumPer1", accumPer1);

				double totalNetCost1 = (double) Math.round(Double.parseDouble(Result[2].toString()) * 100) / 100;
				model.addAttribute("totalNetCost1", totalNetCost1);

				query = session.createSQLQuery("SELECT  COUNT(*)  "
						+ "FROM asset_registry where Ar_ID in (select Ar_ID from AR_SITE where Site_id=:param1) "

				);
				query.setParameter("param1", siteId);
				String quan = mapper.writeValueAsString(query.uniqueResult());
				model.addAttribute("totalquan", quan);

				// get site_IMAGE_NAME

			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getMessage());
			}

			finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

	}

*/

	@RequestMapping(value = "/WarehouseFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> WarehouseFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) throws Exception {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		Warehouse Wareh = new Warehouse();
		WarehousePassive warePassive = new WarehousePassive();
		String wareID = "", area, region, cluster, areaName = "", areaID = "", regionName = "", regionID = "",
				clusterName = "", clusterID = "";
		Calendar calendar = new GregorianCalendar();
		DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				System.out.println("first step");
				/*
				 * ///////////////////////////////////////////////////////// SEND EMAIL BUTTON
				 * ////////////////////////////////////////////////////////// String
				 * email=request.getParameter("email"); String
				 * emailTo=request.getParameter("emailTo"); String
				 * password=request.getParameter("password"); String
				 * message=request.getParameter("message"); String
				 * subject=request.getParameter("subject"); String
				 * ccmail=request.getParameter("ccmail");
				 * 
				 * System.out.println("all email parameters  = "+email+" "+emailTo+" "
				 * +password+" "+message+" "+subject+" "+ccmail);
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
				 * } else {
				 * JavaMailUtil.SendccEmails(email,password,emailTo,ccmail,subject,message); }
				 * 
				 */
///////////////////////////////////////////// END OF SEND EMAIL BUTTON  //////////////////////////////////////////////////////////

				wareID = request.getParameter("wareID");
				if (StringUtils.equalsIgnoreCase(wareID, "")) {
					synchronized (this) {
						wareID = "WARE_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(
								session.createNativeQuery("SELECT WAREHOUSE FROM SEQ_TABLE").uniqueResult().toString());
						session.createNativeQuery("UPDATE SEQ_TABLE SET WAREHOUSE = WAREHOUSE + 1 ").executeUpdate();						
						session.createNativeQuery("commit").executeUpdate();
						session.flush();
						session.clear();
					}
					// wareID = "WARE_" + calendar.get(Calendar.YEAR) + "_" +
					// appConfig.getSequenceNbr(12);
				}
				area = request.getParameter("areaID");
				region = request.getParameter("regionID");
				cluster = request.getParameter("clusterID");

				if (!area.isEmpty()) {
					areaID = area.substring(0, area.indexOf(":"));
					areaName = area.substring((area.indexOf(":")) + 1, area.length());
				}

				if (!region.isEmpty()) {
					regionID = region.substring(0, region.indexOf(":"));
					regionName = region.substring((region.indexOf(":")) + 1, region.length());
				}

				if (!cluster.isEmpty()) {
					clusterID = cluster.substring(0, cluster.indexOf(":"));
					clusterName = cluster.substring((cluster.indexOf(":")) + 1, cluster.length());
				}
				int population;
				if(request.getParameter("population")=="" || request.getParameter("population")==null) {
					population=0;
				}
				else {
					population= Integer.parseInt(request.getParameter("population"));
				}
				

				Wareh.setWareID(wareID);
				Wareh.setWcreationDate(
						new Timestamp((formatter1.parse(request.getParameter("wcreationDate"))).getTime()));
				Wareh.setWlastModifieddate(new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));
				Wareh.setWarehouseName(request.getParameter("warehouseName"));
				Wareh.setWareCity(request.getParameter("wareCity"));
				Wareh.setWareLong(request.getParameter("wareLong"));
				Wareh.setWareLat(request.getParameter("wareLat"));
				Wareh.setWareSite(request.getParameter("wareSite").charAt(0));
				Wareh.setWareSiteID(request.getParameter("siteId"));
				Wareh.setTech2G(request.getParameter("tech2G").charAt(0));
				Wareh.setTech3G(request.getParameter("tech3G").charAt(0));
				Wareh.setTech4G(request.getParameter("tech4G").charAt(0));
				Wareh.setTech5G(request.getParameter("tech5G").charAt(0));
				Wareh.setAreaID(areaID);
				Wareh.setAreaName(areaName);
				Wareh.setRegionID(regionID);
				Wareh.setRegionName(regionName);
				Wareh.setClusterID(clusterID);
				Wareh.setClusterName(clusterName);
				Wareh.setAddress(request.getParameter("wareAddress"));
				Wareh.setWhStatus(request.getParameter("status"));
				Wareh.setPopulation(population);

				session.saveOrUpdate(Wareh);
				session.flush();
				session.clear();

				warePassive.setWareID(wareID);
				warePassive.setWareName(request.getParameter("warehouseName"));
				warePassive.setSiteID(request.getParameter("siteId"));
				warePassive.setSiteAddress(request.getParameter("wareAddress"));
				warePassive.setExiNewTown(request.getParameter("existing_newtown"));
				warePassive.setShowCase(request.getParameter("showcase"));
				warePassive.setSiteOwner(request.getParameter("siteOwner"));
				warePassive.setTowerCoID(request.getParameter("towerCoID"));
				warePassive.setTowerType(request.getParameter("towerType"));
				warePassive.setTowerHeight(request.getParameter("towerHeight"));
				warePassive.setBuildheight(request.getParameter("buildingHeight"));
				warePassive.setShared(request.getParameter("shared"));
				warePassive.setIcrCategory(request.getParameter("icrCategory"));
				warePassive.setTransmission(request.getParameter("transmission"));
				warePassive.setHubSite(request.getParameter("hubSite"));
				warePassive.setLocationNotes(request.getParameter("locationNotes"));
				warePassive.setShelter(request.getParameter("shelter"));
				warePassive.setShelterID(request.getParameter("shelterID"));
				warePassive.setShelterType(request.getParameter("shelterType"));
				warePassive.setShelterVenfdor(request.getParameter("shelterVendor"));
				warePassive.setSiteMode(request.getParameter("siteMode"));
				warePassive.setShelterWithWithout(request.getParameter("wshelter").charAt(0));
				warePassive.setClusterID(clusterID);
				warePassive.setClusterName(clusterName);
				session.saveOrUpdate(warePassive);
				session.flush();
				session.clear();

				if (request.getParameterValues("slctdelWareFinance[]") != null) {

					query = session.createQuery("delete WarehouseProfitloss t where t.id IN (:param1) ");
					query.setParameterList("param1", request.getParameterValues("slctdelWareFinance[]"));
					query.executeUpdate();
					session.flush();
					session.clear();
				}

				if (itemParameters.getDictParameter() != null) {

					int i;
					String wareProfitLossID;
					for (i = 0; i < itemParameters.getDictParameter().size(); i++) {

						WarehouseProfitloss wareProfitLoss = new WarehouseProfitloss();

						if (itemParameters.getDictParameter().get(i).get(START_DATE) != "") {
						}

						if (itemParameters.getDictParameter().get(i).get(END_DATE) != "") {
						}

						if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get(PROFIT_LOSS_ID),
								"0")) {

							synchronized (this) {
								wareProfitLossID = "PrLoss_" + calendar.get(Calendar.YEAR) + "_"
										+ Integer.parseInt(
												session.createNativeQuery("SELECT WARE_PROFIT_LOSS FROM SEQ_TABLE")
														.uniqueResult().toString());
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET WARE_PROFIT_LOSS = WARE_PROFIT_LOSS + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
							}
							// wareProfitLossID = "PrLoss_" + calendar.get(Calendar.YEAR) + "_" +
							// appConfig.getSequenceNbr(19);
						}

						else {
							wareProfitLossID = itemParameters.getDictParameter().get(i).get(PROFIT_LOSS_ID);
						}

						wareProfitLoss.setId(wareProfitLossID);
						wareProfitLoss.setStartDate(new Timestamp(
								(formatter2.parse(itemParameters.getDictParameter().get(i).get(START_DATE)))
										.getTime()));
						wareProfitLoss.setEndDate(new Timestamp(
								(formatter2.parse(itemParameters.getDictParameter().get(i).get(END_DATE))).getTime()));
						wareProfitLoss.setWareID(wareID);
						wareProfitLoss.setWareName(request.getParameter("warehouseName"));
						wareProfitLoss.setAreaID(request.getParameter("areaID"));
						wareProfitLoss.setPopulation(itemParameters.getDictParameter().get(i).get(POPULATION));
						wareProfitLoss.setTech2G(itemParameters.getDictParameter().get(i).get(TECH_2g).charAt(0));
						wareProfitLoss.setTech3G(itemParameters.getDictParameter().get(i).get(TECH_3g).charAt(0));
						wareProfitLoss.setTech4G(itemParameters.getDictParameter().get(i).get(TECH_4g).charAt(0));
						wareProfitLoss.setTech5G(itemParameters.getDictParameter().get(i).get(TECH_5g).charAt(0));
						wareProfitLoss.setTransmission(request.getParameter("transmission"));
						wareProfitLoss.setSiteOwner(request.getParameter("siteOwner"));
						wareProfitLoss.setTowerType(request.getParameter("towerType"));
						wareProfitLoss.setTowerHeight(request.getParameter("towerHeight"));
						wareProfitLoss.setAreaID(areaID);
						wareProfitLoss.setAreaName(areaName);
						wareProfitLoss.setSiteID(request.getParameter("siteId"));
						wareProfitLoss.setUtilization2G(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(Utilization2G)));
						wareProfitLoss.setUtilization3G(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(Utilization3G)));
						wareProfitLoss.setUtilization4G(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(Utilization4G)));
						wareProfitLoss.setUtilization5G(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(Utilization5G)));
						wareProfitLoss.setAvailability2G(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(Availability2G)));
						wareProfitLoss.setAvailability3G(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(Availability3G)));
						wareProfitLoss.setAvailability4G(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(Availability4G)));
						wareProfitLoss.setAvailability5G(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(Availability5G)));
						wareProfitLoss
								.setGrossADS(Float.parseFloat(itemParameters.getDictParameter().get(i).get(GROSS_ADS)));
						wareProfitLoss.setCountOfSSO(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(COUNT_OF_SSO)));
						wareProfitLoss
								.setCustBase(Float.parseFloat(itemParameters.getDictParameter().get(i).get(CUST_BASE)));
						wareProfitLoss.setData(Float.parseFloat(itemParameters.getDictParameter().get(i).get(DATA)));
						wareProfitLoss.setVoice(Float.parseFloat(itemParameters.getDictParameter().get(i).get(VOICE)));
						wareProfitLoss.setSmsRevenu(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(SMS_REVENUE)));
						wareProfitLoss.setGrossRevenu(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(GROSS_REVENUE)));
						wareProfitLoss.setDatatraffic(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(DATA_TRAFFIC)));
						wareProfitLoss.setTotalSmsTraOG(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(TOTAL_SMS_TRAFFIC_OG)));
						wareProfitLoss.setTotalVoiceTraOG(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(TOTAL_VOICE_TRAFFIC_OG)));
						wareProfitLoss.setTotalSmsTraIC(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(TOTAL_SMS_TRAFFIC_IC)));
						wareProfitLoss.setTotalVoiceTraIC(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(TOTAL_VOICE_TRAFFIC_IC)));
						wareProfitLoss.setTotalOpexMon(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(TOTAL_OPEX_MONTHLY)));
						wareProfitLoss.setProfitAndLoss(
								Float.parseFloat(itemParameters.getDictParameter().get(i).get(PROFIT_AND_LOSS)));
						wareProfitLoss.setClusterID(clusterID);
						wareProfitLoss.setClusterName(clusterName);

						session.saveOrUpdate(wareProfitLoss);
						session.flush();
						session.clear();
					}
				}

			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getMessage());
				e.printStackTrace();
			}

			finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}
		rtn.put("wareID", wareID);
		// rtn.put("wlastModifieddate", formatter1.format(date2).toString());
		return rtn;
	}

	@RequestMapping(value = "/WarehouseDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> WarehouseDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		String idForm = null;
		session = AlmDbSession.getInstance().getSession();
		String[] idList = request.getParameterValues("wareID[]");

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				for (int i = 0; i < idList.length; i++) {
					idForm = idList[i];
					session.createNativeQuery("Delete WAREHOUSE_PROFIT_LOSS where WARE_ID = '" + idForm + "'").executeUpdate();
					session.createNativeQuery("delete WAREHOUSE_PASSIVE  where WARE_ID = '" + idForm + "'").executeUpdate();
					session.createNativeQuery("delete WAREHOUSE  where WARE_ID = '" + idForm + "'").executeUpdate();
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error in creating session with the WarehouseDelete method " + e.getMessage());
				logger.info("could not connect to DB in WarehouseDelete method ");
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}

		} else {
			System.out.println("could not connect to DB in WarehouseDelete method");
			logger.info("could not connect to DB in WarehouseDelete method ");
		}

		return rtn;
	}

	@RequestMapping(value = "/getWarehouseDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getWarehouseDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		
		System.out.println("getWarehouseDetails");

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			try {
				String requestValue = request.getParameter("requestValue");

				str = "select distinct wareID, warehouseName, wareSiteID, wareLong, "
						+ "wareLat from Warehouse where LOWER(wareID) like LOWER(:param1) or LOWER(warehouseName) like LOWER(:param1) "
						+ "or LOWER(wareSiteID) like LOWER(:param1) ";

				query = session.createQuery(str);
				query.setParameter("param1", "%" + requestValue + "%");
				//query.setParameter("param1", "%" + requestValue + "%");

				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("ListWarehouses", query.list());
			} catch (Exception e) {
				logger.info("Error while getting warehouses using autocomplete with error message: " + e);
				e.printStackTrace();

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
					
				}
			}
		}

		return rtn;
	}

	// to get all warehouse Ids in the form view
	@RequestMapping(value = "/GetAllWarehouse", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllWarehouse(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String WarehouseName = request.getParameter("warehouseName");
		String Site = request.getParameter("Site");

		System.out.println("****GetAllWarehouse****");
		System.out.println("Site is " + Site);
		System.out.println("WarehouseName is " + WarehouseName);

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			try {
				if (Site == null || Site == "empty") {
					query = session.createQuery(
							"select wareID, warehouseName, wareSiteID, wareLong, wareLat,wareCity from Warehouse where wareID like :param1 or warehouseName like :param1 or wareSiteID like :param1");
					query.setParameter("param1", "%" + WarehouseName + "%");
					query.setFirstResult(0);
					query.setMaxResults(40);
					rtn.put("globalList", query.list());
					session.clear();
				} else {
					query = session
							.createQuery("SELECT warehouseName, wareSiteID from Warehouse where wareSite = '1' ");
					query.setFirstResult(0);
					query.setMaxResults(40);
					rtn.put("listSite", query.list());
					session.clear();
				}
			} catch (Exception e) {
				logger.info("Error in GetAllWarehouse  Data from database", e);
			}
			 finally {
					if (session != null && session.isOpen()) {
						session.close();
						
					}
				}
		}
		return rtn;
	}

	@RequestMapping(value = "/GetAllSiteIds_ALC", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllSiteIds_ALC(HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		/*
		 * if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		 * rtn.put("Login", "redirect:/"); return rtn; }
		 */

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			try {
				String requestValue = request.getParameter("requestValue");
				query = session.createQuery(
						"select distinct (wareSiteID) ,(warehouseName), wareID from Warehouse where UPPER(wareSiteID) like UPPER(:param1) "
								+ "or UPPER(warehouseName) like UPPER(:param1)  ");
				query.setParameter("param1", "%" + requestValue + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("ListSites", query.list());

			} catch (Exception e) {
				logger.info("Error while getting Site using autocomplete with error message: " + e);
				e.printStackTrace();

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
					
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/GetAllWareSiteNames_ALC", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllWareSiteNames_ALC(HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		/*
		 * if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		 * rtn.put("Login", "redirect:/"); return rtn; }
		 */

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			try {
				String requestValue = request.getParameter("requestValue");
				query = session.createQuery(
						"select distinct warehouseName , wareSiteID, wareID  from Warehouse  where UPPER(warehouseName) like UPPER(:param1) ");
				query.setParameter("param1", "%" + requestValue + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("ListWareSiteName", query.list());

			} catch (Exception e) {
				logger.info("Error while getting Site Name using autocomplete with error message: " + e);
				e.printStackTrace();

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
					
				}
			}
		}
		return rtn;
	}

	// Thread to get image from sftp server

	Thread imagetoweb = new Thread(new Runnable() {

		@Override
		public void run() {
			try {

				String host = "196.202.208.23";
				String user = "root";
				String pass = "TKLdev#2021$";
				int e = 22;

				Properties config = new Properties();
				config.put("StrictHostKeyChecking", "no");

				JSch jSch = new JSch();
				com.jcraft.jsch.Session session = jSch.getSession(user, host, e);
				session.setPassword(pass);
				session.setConfig(config);
				try {
					session.connect();
					ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
					channelSftp.connect();

					String data = siteimgName.substring(2, Integer.parseInt(String.valueOf(siteimgName.length())));

					StringBuffer sb = new StringBuffer(data);
					sb.deleteCharAt(sb.length() - 1);
					sb.deleteCharAt(sb.length() - 1);
					String data1 = String.valueOf(sb).trim();

					String[] imgresult = null;

					if (data1.contains(",")) {
						imgresult = data1.split(",");

						for (int i = 0; i < imgresult.length; i++) {

							imgresult[i] = imgresult[i].replace("\"", "");
							// search for pending client files
							File dir1 = new File("src/main/webapp/resources/SitePic/" + siteimgID);
							if (!dir1.exists()) {
								dir1.mkdirs();
							}

							File sitePicture = new File(
									"src/main/webapp/resources/SitePic" + "/" + siteimgID + "/" + imgresult[i]);

							if (!sitePicture.exists()) {

								String siteSFTPpath = "/usr/share/tomcat/webapps/alm/resources/";
								String fullpath = siteSFTPpath + "SitePic";
								channelSftp.cd(fullpath);
								String path = channelSftp.pwd() + "/" + siteimgID + "/" + imgresult[i];
								File sitesPicture = new File("src/main/webapp/resources/SitePic/" + siteimgID);
								String sitePicturePath = sitesPicture.getAbsolutePath();
								channelSftp.get(path, sitePicturePath);

							}

						}

					} else {
						data1 = data1.replace("\"", "");

						// search for pending client files
						File dir1 = new File("src/main/webapp/resources/SitePic/" + siteimgID);
						if (!dir1.exists()) {
							dir1.mkdirs();
						}

						File sitePicture = new File(
								"src/main/webapp/resources/SitePic" + "/" + siteimgID + "/" + data1);

						if (!sitePicture.exists()) {

							String siteSFTPpath = "/usr/share/tomcat/webapps/alm/resources/";
							String fullpath = siteSFTPpath + "SitePic";
							channelSftp.cd(fullpath);
							String path = channelSftp.pwd() + "/" + siteimgID + "/" + data1;
							File sitesPicture = new File("src/main/webapp/resources/SitePic/" + siteimgID);
							String sitePicturePath = sitesPicture.getAbsolutePath();
							channelSftp.get(path, sitePicturePath);

						}

					}

					channelSftp.disconnect();
					session.disconnect();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});

}
