package com.aliat.alm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToBeanResultTransformer;
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
import com.aliat.alm.models.AgentBorder;
import com.aliat.alm.models.RegionBorder;
import com.aliat.alm.models.Shops;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ShopsController {
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	private static final Logger logger = Logger.getLogger(ShopsController.class.getName());

	@Autowired
	Form form;

	@Autowired
	Notify notification;

	@RequestMapping(value = "/ShopsListView", method = RequestMethod.GET)
	public String ShopsListView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			try {
				notification.headerNotifications(session, model);
				String str = "select SHOPS_ID as shopId,Owner as owner,SHOP_NAME as shopName,ADDRESS as address,REGION_NAME as regionName,TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate from SHOPS ORDER BY LAST_MODIFIED_DATE DESC ";
				query = session.createNativeQuery(str);
				model.addAttribute("ListGridTable", mapper.writeValueAsString(session.createNativeQuery(str).list()));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ShopsListView due to \n " + exceptionAsString);
				logger.info("Error in ShopsListView due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return "ShopsListView";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredShopsListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredShopsListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			try {
				notification.headerNotifications(session, model);
				String startdate, enddate, regionid, regionname;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				regionid = request.getParameter("regionid");
				regionname = request.getParameter("regionname");

				List<String> listShop = new ArrayList<String>();
				String str = " select SHOPS_ID as shopId,Owner as owner,SHOP_NAME as shopName,ADDRESS as address,REGION_NAME as regionName , TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate from SHOPS";

				if (startdate != null && enddate != null) {
					str = str + " where CREATE_DATE between TO_DATE('" + startdate + "','YYYY-MM-DD') and TO_DATE('"
							+ enddate + "','YYYY-MM-DD')";
				}

				if (regionid != null && !regionid.equalsIgnoreCase("")) {

					str = str + " and upper(REGION_ID) LIKE upper('%" + regionid + "%')";
				}

				if (regionname != null && !regionname.equalsIgnoreCase("")) {

					str = str + " and upper(REGION_NAME) LIKE upper('%" + regionname + "%')";
				}

				str = str + " ORDER BY LAST_MODIFIED_DATE DESC ";

				query = session.createNativeQuery(str);

				listShop = query.list();
				rtn.put("listShop", listShop);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in Filtered Shops List View due to \n " + exceptionAsString);
				logger.info("Error in  Filtered Shops List View due to \n " + exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		return rtn;
	}

	@RequestMapping(value = "/ShopsDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ShopsDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();

			try {
				String[] idList;
				idList = request.getParameterValues("shopsID[]");
				if(idList != null) {
					for (int i = 0; i < idList.length; i++) {
						String idForm = idList[i];

						query = session.createNativeQuery("delete SHOPS  where SHOPS_ID ='" + idForm + "'");
						query.executeUpdate();

					}
					tx.commit();
				}
				

			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ShopsDelete due to \n " + exceptionAsString);
				logger.info("Error in ShopsDelete due to \n " + exceptionAsString);

			}

			finally {

				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		return rtn;

	}

	@SuppressWarnings({ "unchecked"})
	@RequestMapping(value = "/ShopsFormView", method = RequestMethod.GET)
	public String ShopsFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		ObjectMapper mapper = new ObjectMapper();
		String shopId, regionId, area, navAction = "2";
		Shops Shop;
		HashMap<String, List<Map<String, String>>> hashRegionBorder = new HashMap<String, List<Map<String, String>>>();
		HashMap<String,List<Map<String, String>>> hashAreaBorder = new HashMap<String, List<Map<String, String>>>();

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Calendar calendar = new GregorianCalendar();
		String result[] = new String[4];
		int SelectedIndex = 0;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			try {
				notification.headerNotifications(session, model);
				////////////////////////////////////// Area and Area
				////////////////////////////////////// Borders//////////////////////////////////////////////////
				List<Object[]> areaIDs = new ArrayList<>();
				List<Object> areas = new ArrayList<>();
				List<Object> areaData = new ArrayList<>();
				String[] areaArray = null;
				query = session.createNativeQuery("SELECT AREA_ID FROM AREA");
				if (query.list().size() > 0) {
					areaIDs = query.list();
					for (int i = 0; i < areaIDs.size(); i++) {
						areas = new ArrayList<>();
						query = session.createNativeQuery(
								"SELECT a.AREA_ID,b.AREA_NAME, LISTAGG(a.LATITUDE || ',' || a.LONGTITUDE, ':') WITHIN GROUP (ORDER BY a.SEQ_SORTING) AS borders"
										+ " FROM AREA_BORDER a ,Area b where  a.AREA_ID=b.AREA_ID"
										+ " and a.AREA_ID='" + areaIDs.get(i) + "'"
										+ " GROUP BY a.AREA_ID,b.AREA_NAME");
						
						areaArray= ((List<Object[]>) query.list()).stream()
						        .flatMap(array -> Arrays.stream(array)
						                .map(Object::toString))
						        .toArray(String[]::new);
						
						if (areaArray[2] == null) {
							areaArray[2] ="N/A";
						}
						areas.add(areaArray[0]);
						areas.add(areaArray[1]);
						areas.add(areaArray[2]);
						areaData.add(areas);
					}
				}

				////////////////////////////////////// Region and Region
				////////////////////////////////////// Borders//////////////////////////////////////////////////
				List<Object[]> regionIDs = new ArrayList<>();
				List<Object> regions = new ArrayList<>();
				List<Object> regionData = new ArrayList<>();

				query = session.createNativeQuery("SELECT REGION_ID FROM REGION");
				if (query.list().size() > 0) {
					regionIDs = query.list();
					for (int i = 0; i < regionIDs.size(); i++) {
						regions = new ArrayList<>();
						
						query = session.createNativeQuery(
								"SELECT a.REGION_ID,b.REGION_NAME,b.REGION_CODE, LISTAGG(a.LATITUDE || ',' || a.LONGTITUDE, ':') WITHIN GROUP (ORDER BY a.SEQ_SORTING + 0 ASC) AS borders"
										+ " FROM REGION_BORDER a,REGION b WHERE  a.REGION_ID=b.REGION_ID"
										+ " AND a.REGION_ID ='" + regionIDs.get(i) + "'"
										+ " GROUP BY a.REGION_ID,b.REGION_NAME,b.REGION_CODE");
						
						List<Object[]> listOfArrays = query.list();
						// Convert list of arrays to a single array
						String[] resultArray = listOfArrays.stream()
						        .flatMap(array -> Arrays.stream(array)
						                .map(Object::toString))
						        .toArray(String[]::new);
						
						if (resultArray[3] == null) {
							resultArray[3] ="N/A";
						}
						
						System.out.println(resultArray[0]);
						System.out.println(resultArray[1]);
						System.out.println(resultArray[2]);
						System.out.println(resultArray[3]);
						
						regions.add(resultArray[0]);
						regions.add(resultArray[1]);
						regions.add(resultArray[2]);
						regions.add(resultArray[3]);
						regionData.add(regions);
					}
				}
				navAction = request.getParameter("NavAction");
				shopId = request.getParameter("shopsID");

				if (shopId == null) {
					model.addAttribute("shopId", "addNew");
					calendar.setTime(new Timestamp(System.currentTimeMillis()));
					model.addAttribute("creationDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("lastModifiedDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("shopStatus", "New");
					model.addAttribute("listRegionBorder", "addNew");
					model.addAttribute("listAgentShops", "addNew");
					model.addAttribute("listAgentDisplayName", "addNew");
					model.addAttribute("listAreaBorder", "addNew");
					model.addAttribute("listAgentDisplayName", "addNew");
					model.addAttribute("SelectedIndex", "addNew");
					model.addAttribute("ShopsCount", "addNew");
					model.addAttribute("docStatus", "addNew");
					model.addAttribute("listShopImage", "addNew");

					// return the area and area_borders on load
					if (areaData.size() > 0) {
						model.addAttribute("areasData", mapper.writeValueAsString(areaData));
					} else {
						model.addAttribute("areasData", "addNew");
					}

					// return the region and region_borders on load
					if (regionData.size() > 0) {
						model.addAttribute("regionsData", mapper.writeValueAsString(regionData));
					} else {
						model.addAttribute("regionsData", "addNew");
					}

				} else {

					System.out.println("navAction " + navAction);
					result = form.NavigationNP(session, "Shops", "SHOPS_ID", shopId, "LAST_MODIFIED_DATE", navAction);

					SelectedIndex = Integer.parseInt(result[1]);
					shopId = result[2];
					Shop = (Shops) session.get(Shops.class, shopId);
					regionId = Shop.getRegion();

					if (regionId != null) {
						query = session.createQuery(
								"SELECT t.lng AS lng, t.lat AS lat FROM RegionBorder t WHERE regionId = :regionId ORDER BY sequence + 0 ASC");
						query.setParameter("regionId", regionId);

						//List<Object[]> result1 = query.getResultList();
						
						List<Map<String, String>> listRegionBorder =	((List<Object[]>) query.getResultList()).stream().map(arr -> {
							Map<String, String> newMap = new HashMap<>();
							newMap.put("lng", String.valueOf(arr[0])); // Convert to String
							newMap.put("lat", String.valueOf(arr[1])); // Convert to String
							return newMap;
						}).collect(Collectors.toList());
						
						hashRegionBorder.put(regionId, listRegionBorder);
						model.addAttribute("listRegionBorder", mapper.writeValueAsString(hashRegionBorder));

					} else {

						model.addAttribute("listRegionBorder", "addNew"); // No need, to be corrected. It can be
																			// corrected from client side

					}

					if (Shop.getAreaId() != null) {
						area = Shop.getAreaId();
						query = session
								.createQuery("select t.lng as lng, t.lat as lat from AreaBorder t where areaId ='"
										+ area + "' ORDER BY Id ");
						
						List<Map<String, String>> listAreaBorder =	((List<Object[]>) query.getResultList()).stream().map(arr -> {
							Map<String, String> newMap = new HashMap<>();
							newMap.put("lng", String.valueOf(arr[0])); // Convert to String
							newMap.put("lat", String.valueOf(arr[1])); // Convert to String
							return newMap;
						}).collect(Collectors.toList());
						hashAreaBorder.put(area, listAreaBorder);

						if (listAreaBorder != null) {
							model.addAttribute("listAreaBorder", mapper.writeValueAsString(hashAreaBorder));
						} else {
							model.addAttribute("listAreaBorder", "addNew");
						}
					} else {
						model.addAttribute("listAreaBorder", "addNew");
					}

					model.addAttribute("shopsID", Shop.getShopId());
					model.addAttribute("lastModifiedDate", formatter.format(Shop.getLastModifiedDate()).toString());
					model.addAttribute("creationDate", formatter.format(Shop.getCreateDate()).toString());
					model.addAttribute("owner", Shop.getOwner());
					model.addAttribute("longtitude", Shop.getLongtitude());
					model.addAttribute("latitude", Shop.getLatitude());
					model.addAttribute("address", Shop.getAddress());
					model.addAttribute("salesman", Shop.getSalesman());
					model.addAttribute("regionManager", Shop.getRegionManager());
					model.addAttribute("region", Shop.getRegion() + ":" + Shop.getRegionName());
					model.addAttribute("regionId", Shop.getRegion());
					model.addAttribute("area", Shop.getAreaId() + ":" + Shop.getAreaName());
					model.addAttribute("shopName", Shop.getShopName());
					model.addAttribute("agent", Shop.getAgent() + ":" + Shop.getAgentName());
					model.addAttribute("shopStatus", Shop.getStatus());
					model.addAttribute("SelectedIndex", SelectedIndex);
					model.addAttribute("ShopsCount", Integer.parseInt(result[0]));

					// get SHOP_IMAGE_NAME
					query = session.createNativeQuery("select IMAGE_NAME from SHOPS_IMAGE where SHOP_ID like :param1");
					query.setParameter("param1", shopId);

					model.addAttribute("listShopImage", mapper.writeValueAsString(query.list()));

					// return the area and area_borders on load
					if (areaData.size() > 0) {
						model.addAttribute("areasData", mapper.writeValueAsString(areaData));
					} else {
						model.addAttribute("areasData", "addNew");
					}

					// return the region and region_borders on load
					if (regionData.size() > 0) {
						model.addAttribute("regionsData", mapper.writeValueAsString(regionData));
					} else {
						model.addAttribute("regionsData", "addNew");
					}
				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ShopsFormView due to \n " + exceptionAsString);
				logger.info("Error in ShopsFormView due to \n " + exceptionAsString);
			} finally {

				if (session != null && session.isOpen()) {
					session.close();
				}

			}

		}

		return "ShopsFormView";
	}

	@RequestMapping(value = "/ShopsFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> TroubleTicketFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		DateFormat formatter;
		Shops Shops = new Shops();
		Calendar calendar = new GregorianCalendar();
		Timestamp CreationDate;
		String shopsId, createdDate, owner, latitude, longtitude, address, shopName, salesman, regionManager, agent,
				region, status, area;
		shopsId = request.getParameter("shopsID");
		
		String[] agentIdName, areaparts, regionIdName;
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

				if (shopsId == null || StringUtils.equalsIgnoreCase(shopsId, "")) {
					synchronized (this) {
						//
						shopsId = "SHOP_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(
								session.createNativeQuery("SELECT SHOPS FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET SHOPS = SHOPS + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
					}
					Shops.setCreateDate(new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));

				}
				
				System.out.println("shopsId is : "+shopsId);
				owner = request.getParameter("owner");
				latitude = request.getParameter("latitude");
				longtitude = request.getParameter("longtitude");
				address = request.getParameter("address");
				shopName = request.getParameter("shopName");
				salesman = request.getParameter("salesman");
				regionManager = request.getParameter("regionManager");
				agent = request.getParameter("agent");
				region = request.getParameter("region");
				status = request.getParameter("status");
				area = request.getParameter("area");

				if (request.getParameter("creationDate") == "") {

					CreationDate = new Timestamp((new Timestamp(System.currentTimeMillis())).getTime());

				}

				createdDate = request.getParameter("creationDate");
				CreationDate = new Timestamp((formatter.parse(createdDate)).getTime());
				Shops.setCreateDate(CreationDate);

				if (agent.contains(":")) {

					agentIdName = agent.split(":");
					Shops.setAgent(agentIdName[0]);
					Shops.setAgentName(agentIdName[1]);

				}

				Shops.setShopId(shopsId);
				Shops.setOwner(owner);
				Shops.setLatitude(latitude);
				Shops.setLongtitude(longtitude);
				Shops.setAddress(address);
				Shops.setShopName(shopName);
				Shops.setSalesman(salesman);
				Shops.setRegionManager(regionManager);
				Shops.setStatus(status);

				areaparts = area.split(":");

				if (area.contains(":")) {

					Shops.setAreaId(areaparts[0]);
					Shops.setAreaName(areaparts[1]);

				}

				regionIdName = region.split(":");

				if (region.contains(":")) {
					Shops.setRegion(regionIdName[0]);
					Shops.setRegionName(regionIdName[1]);
				}
				Shops.setLastModifiedDate(new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));
				session.saveOrUpdate(Shops);
				tx.commit();
				rtn.put("shopsID", shopsId);
				
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ShopsFormSave due to \n " + exceptionAsString);
				logger.info("Error in ShopsFormSave due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}

		}

		return rtn;
	}

///////////////
	@RequestMapping(value = "/GetAvailableShops", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAvailableShops(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		String shops = "%" + request.getParameter("Shops") + "%";
		String agentID = request.getParameter("agentID");
		String getAllShops = request.getParameter("getAllShops");

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				System.out.println("getAllShops  " + getAllShops);

				if (getAllShops == null) {
					if (agentID == null || agentID == "") {
						System.out.println("test 2.1");

						query = session.createQuery(
								"SELECT shopId, shopName, longtitude, latitude, areaName, regionName from Shops where (shopId like UPPER(:param1) OR UPPER(shopName)like UPPER(:param1)) and shopId not in (select shopsId from AgentShops) ");
						query.setParameter("param1", shops);
						query.setFirstResult(0);
						query.setMaxResults(40);
						rtn.put("ListShops", query.list());

					} else {
						System.out.println("test 2.2");

						query = session.createQuery(
								"SELECT shopId, shopName, longtitude, latitude, areaName, regionName from Shops where (shopId like UPPER(:param1) OR UPPER(shopName)like UPPER(:param1)) and shopId not in (select shopsId from AgentShops where agentId not in (:param2) ) ");
						query.setParameter("param2", agentID);
						query.setParameter("param1", shops);
						query.setFirstResult(0);
						query.setMaxResults(40);
						rtn.put("ListShops", query.list());

					}
				} else {
					if (getAllShops.equalsIgnoreCase("1")) {
						System.out.println("test 1");

						query = session.createQuery(
								"SELECT shopId, shopName from Shops where (shopId like UPPER(:param1) OR UPPER(shopName)like UPPER(:param1)) ORDER BY lastModifiedDate DESC");
						query.setParameter("param1", shops);
						query.setFirstResult(0);
						query.setMaxResults(40);
						rtn.put("ListShops", query.list());
						System.out.println("ListShops value is :" + mapper.writeValueAsString(query.list()));
					}
				}

			} catch (Exception e) {

				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GetAvailableShops due to \n " + exceptionAsString);
				logger.info("Error in GetAvailableShops due to \n " + exceptionAsString);

			} finally {

				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/GetHashmapRegionShop", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetHashmapRegion(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		List<RegionBorder> listRegionBorder = null;
		HashMap<String, List<RegionBorder>> hashRegionBorder = new HashMap<String, List<RegionBorder>>();
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				String regionId = request.getParameter("regionId");

				if (regionId != null) {

					query = session
							.createQuery("select t.lng as lng, t.lat as lat from RegionBorder t where regionId ='"
									+ regionId + "' ORDER BY sequence + 0 ASC");
					listRegionBorder = query.setResultTransformer(new AliasToBeanResultTransformer(AgentBorder.class))
							.list();
					hashRegionBorder.put(regionId, listRegionBorder);
					model.addAttribute("listRegionBorder", mapper.writeValueAsString(hashRegionBorder));

				} else {

					model.addAttribute("listRegionBorder", "addNew");

				}

				rtn.put("listRegions", hashRegionBorder);

			} catch (Exception e) {

				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GetHashmapRegionShop due to \n " + exceptionAsString);
				logger.info("Error in GetHashmapRegionShop due to \n " + exceptionAsString);

			} finally {

				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

}
