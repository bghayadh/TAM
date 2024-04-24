package com.aliat.alm.controller;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.Area;
import com.aliat.alm.models.AreaBorder;
import com.aliat.alm.models.AreaFinance;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AreaController {
	private static final String Latitude = "Latitude";
	private static final String Longitude = "Longitude";
	private static final String ID = "Id";
	private static final String START_DATE = "StartDate";
	private static final String END_DATE = "EndDate";
	private static final String SITES2G = "2GSites";
	private static final String PROFIT2G = "ProfitLoss2G";
	private static final String SITES2G3G = "2G3GSites";
	private static final String PROFIT2G3G = "ProfitLoss2G3G";
	private static final String SITES2G3G4G = "2G3G4GSites";
	private static final String PROFIT2G3G4G = "ProfitLoss2G3G4G";
	private static final String TOTALSITES = "TotalSites";
	private static final String TOTALSUM = "SumProfitLoss";
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	private static final Logger logger = Logger.getLogger(AreaController.class.getName());

	@Autowired
	Form form;

	@Autowired
	Notify notification;

	@RequestMapping(value = "/AreaListView", method = RequestMethod.GET)
	public String AreaListView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			try {
				notification.headerNotifications(session, model);
				query = session.createNativeQuery(
						"SELECT AREA_ID as id,AREA_ID as areaID, AREA_NAME as name,REGION_NAME as regionName, Status as arStatus, "
						+ "TO_CHAR(CREATION_DATE, 'YYYY-MM-DD HH24:MI:SS') as creationDate, "
						+ "TO_CHAR(LAST_MODIFICATION_DATE, 'YYYY-MM-DD HH24:MI:SS') as lastModifieddate from Area  ORDER BY LAST_MODIFICATION_DATE DESC");
				model.addAttribute("ListGridTable", mapper.writeValueAsString(query.list()));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in AreaListView due to \n " + exceptionAsString);
				logger.info("Error in AreaListView due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return "AreaListView";
	}

	@RequestMapping(value = "/FilteredAreaListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredAreaListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {
				String startdate, enddate, regionid, regionname, areaid, areaname;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				regionid = request.getParameter("regionid");
				regionname = request.getParameter("regionname");
				areaid = request.getParameter("areaid");
				areaname = request.getParameter("areaname");

				String str = " select 1 as chkBox,AREA_ID as id,AREA_NAME as name,REGION_NAME as regionName,TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS') as creationDate,TO_CHAR(LAST_MODIFICATION_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifieddate from AREA";

				if (startdate != null && enddate != null) {
					str = str + " where CREATION_DATE between TO_DATE('" + startdate + "','YYYY-MM-DD') and TO_DATE('"
							+ enddate + "','YYYY-MM-DD')";
				}

				if (regionid != null && !regionid.equalsIgnoreCase("")) {

					str = str + " and upper(REGION_ID) LIKE upper('%" + regionid + "%')";
				}

				if (regionname != null && !regionname.equalsIgnoreCase("")) {

					str = str + " and upper(REGION_NAME) LIKE upper('%" + regionname + "%')";
				}
				if (areaid != null && !areaid.equalsIgnoreCase("")) {

					str = str + " and upper(AREA_ID) LIKE upper('%" + areaid + "%')";
				}

				if (areaname != null && !areaname.equalsIgnoreCase("")) {

					str = str + " and upper(AREA_NAME) LIKE upper('%" + areaname + "%')";
				}

				str = str + " ORDER BY LAST_MODIFICATION_DATE DESC ";

				rtn.put("listArea", session.createNativeQuery(str).list());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in showing the filtered Area list view due to \n " + exceptionAsString);
				logger.info("Error in showing the filtered Area list view due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		return rtn;
	}

	@SuppressWarnings({ "unchecked"})
	@RequestMapping(value = "/AreaFormView", method = RequestMethod.GET)
	public String AreaFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Area area;
		String regionId, navAction = "2", areaID;
		List<AreaBorder> areaBorderList = new ArrayList<>();
		HashMap<String, List<Map<String, String>>> hash_map1 = new HashMap<String, List<Map<String, String>>>();
		boolean EmptyList;
		String result[] = new String[4];
		int SelectedIndex = 0;
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			notification.headerNotifications(session, model);
			try {

//////////////////////////////////////Region and Region Borders//////////////////////////////////////////////////
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
								.flatMap(array -> Arrays.stream(array).map(Object::toString)).toArray(String[]::new);

						if (resultArray[3] == null) {
							resultArray[3] = "N/A";
						}
						
						regions.add(resultArray[0]);
						regions.add(resultArray[1]);
						regions.add(resultArray[2]);
						regions.add(resultArray[3]);
						regionData.add(regions);
					}
				}

				areaID = request.getParameter("AreaID");
				navAction = request.getParameter("NavAction");

				if (areaID == null) {

					model.addAttribute("creationDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("lastModifiedDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("areaLong", "addNew");
					model.addAttribute("areaLat", "addNew");
					model.addAttribute("docStatus", "addNew");
					model.addAttribute("ListAreaFinance", "addNew");
					model.addAttribute("listAreaBorder", "addNew");
					model.addAttribute("listRegionBorder", "addNew");
					model.addAttribute("SelectedIndex", "addNew");
					model.addAttribute("AreasCount", "addNew");

					// return the region and region_borders on load
					if (regionData.size() > 0) {
						model.addAttribute("regionsData", mapper.writeValueAsString(regionData));
					} else {
						model.addAttribute("regionsData", "addNew");
					}
					return "AreaFormView";
				} else {

					// NavigateNP: Navigate for Next or Previous
					result = form.NavigationNP(session, "Area", "AREA_ID", areaID, "last_modification_date", navAction);
					SelectedIndex = Integer.parseInt(result[1]);
					areaID = result[2];
					area = (Area) session.get(Area.class, areaID);

					if (area != null) {

						query = session
								.createQuery("from AreaBorder where areaId LIKE :param1 ORDER BY (Sequence) + 0 ASC");
						query.setParameter("param1", areaID);
						areaBorderList = query.list();
						EmptyList = areaBorderList.isEmpty();

						if (areaBorderList == null || EmptyList == true) {
							model.addAttribute("listAreaBorder", "addNew");
						} else {
							model.addAttribute("listAreaBorder", mapper.writeValueAsString(areaBorderList));
						}
						model.addAttribute("creationDate", formatter.format(area.getCreationDate()).toString());
						model.addAttribute("lastModifiedDate", formatter.format(area.getLastModifieddate()).toString());
						model.addAttribute("AreaId", areaID);
						model.addAttribute("areaName", area.getName());
						model.addAttribute("RegionID", area.getRegionID() + ":" + area.getRegionName());
						model.addAttribute("AreasCount", Integer.parseInt(result[0]));
						model.addAttribute("SelectedIndex", SelectedIndex);

					} else {

						model.addAttribute("listAreaBorder", "addNew");

					}

					regionId = area.getRegionID();

					if (regionId != null) {
						query = session.createQuery(
								"SELECT t.lng AS lng, t.lat AS lat FROM RegionBorder t WHERE regionId = :regionId ORDER BY sequence + 0 ASC");
						query.setParameter("regionId", regionId);

						// List<Object[]> result1 = query.getResultList();

						List<Map<String, String>> listRegionBorder = ((List<Object[]>) query.getResultList()).stream().map(arr -> {
							Map<String, String> newMap = new HashMap<>();
							newMap.put("lng", String.valueOf(arr[0])); // Convert to String
							newMap.put("lat", String.valueOf(arr[1])); // Convert to String
							return newMap;
						}).collect(Collectors.toList());

						hash_map1.put(regionId, listRegionBorder);
						model.addAttribute("listRegionBorder", mapper.writeValueAsString(hash_map1));

					} else {
						model.addAttribute("listRegionBorder", "addNew");
					}

					query = session.createQuery(
							"select  TO_CHAR(t.startDate,'YYYY-MM-DD') as startDate , TO_CHAR(t.endDate,'YYYY-MM-DD') as endDate,t.areaId as areaId,t.number2gSites as number2gSites, t.number2g3gSites as number2g3gSites , t.number2g3g4gSites as number2g3g4gSites, t.pl2g as pl2g, t.pl2g3g as pl2g3g, t.pl2g3g4g as pl2g3g4g, t.totalSites as totalSites, t.sumProfitLoss as sumProfitLoss, t.id as id from AreaFinance t where t.areaId like :param1");
					query.setParameter("param1", areaID);
									
					List<Map<String, String>> ListAreaFinance = ((List<Object[]>) query.getResultList()).stream().map(arr -> {
						Map<String, String> newMap = new HashMap<>();
						newMap.put("startDate", String.valueOf(arr[0])); // Convert to String
						newMap.put("endDate", String.valueOf(arr[1])); // Convert to String
						newMap.put("areaId", String.valueOf(arr[2])); // Convert to String
						newMap.put("number2gSites", String.valueOf(arr[3])); // Convert to String
						newMap.put("number2g3gSites", String.valueOf(arr[4])); // Convert to String
						newMap.put("number2g3g4gSites", String.valueOf(arr[5])); // Convert to String
						newMap.put("pl2g", String.valueOf(arr[6])); // Convert to String
						newMap.put("pl2g3g", String.valueOf(arr[7])); // Convert to String
						newMap.put("pl2g3g4g", String.valueOf(arr[8])); // Convert to String
						newMap.put("totalSites", String.valueOf(arr[9])); // Convert to String
						newMap.put("sumProfitLoss", String.valueOf(arr[10])); // Convert to String
						newMap.put("id", String.valueOf(arr[11])); // Convert to String
						return newMap;
					}).collect(Collectors.toList());
					model.addAttribute("ListAreaFinance", mapper.writeValueAsString(ListAreaFinance));
					model.addAttribute("docStatus", "Existed");
					model.addAttribute("ordStatus", area.getStatus());
					model.addAttribute("AreaCoordinates", "0");

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
				logger.finest("Error in AreaFormView due to \n " + exceptionAsString);
				logger.info("Error in AreaFormView due to \n " + exceptionAsString);
			} finally {

				if (session != null && session.isOpen()) {
					session.close();
				}

			}

		}
		return "AreaFormView";
	}

	@RequestMapping(value = "/AreaDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> AreaDelete(Locale locale, Model model, HttpServletRequest request,
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

				String[] idList = request.getParameterValues("AreaId[]");

				if (idList != null) {
					for (int j = 0; j < idList.length; j++) {
						query = session
								.createNativeQuery("delete Area_Border where AREA_ID ='" + idList[j].toString() + "'");
						query.executeUpdate();
						query = session.createNativeQuery("delete Area where AREA_ID ='" + idList[j].toString() + "'");
						query.executeUpdate();
					}
					tx.commit();
				}
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in AreaDelete due to \n " + exceptionAsString);
				logger.info("Error in AreaDelete due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		rtn.put("BassamTest", "DeleteDone");
		return rtn;

	}

	@RequestMapping(value = "/AreaFormSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> AreaFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		String areaid = "", regionId = "", regionName = "";
		String region, areaName, latitude = "", longitude = "", areaIdFinance = "", areaBorderID, streched;
		Calendar calendar = new GregorianCalendar();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp CreationDate;
		Area area = new Area();

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				if (StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNew")) {
					synchronized (this) {
						// areaid= "AREA_"+calendar.get(Calendar.YEAR)+"_"
						// +appConfig.getSequenceNbr(20);
						areaid = "AREA_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(
								session.createNativeQuery("SELECT AREA FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET AREA = AREA + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
					}
					model.addAttribute("AreaId", areaid);

				} else {

					areaid = request.getParameter("AreaID");
				}

				if (request.getParameterValues("slctDelAreaBorder[]") != null) {
					query = session.createQuery("delete AreaBorder t where t.Id IN (:param1) ");
					query.setParameterList("param1", request.getParameterValues("slctDelAreaBorder[]"));
					query.executeUpdate();
				}

				if (request.getParameterValues("slctDelAreaFinance[]") != null) {
					query = session.createQuery("delete AreaFinance t where t.id IN (:param1)");
					query.setParameterList("param1", request.getParameterValues("slctDelAreaFinance[]"));
					query.executeUpdate();
				}

				if (request.getParameter("creationDate") == "") {

					CreationDate = new Timestamp((new Timestamp(System.currentTimeMillis())).getTime());

				} else {

					CreationDate = new Timestamp((formatter.parse(request.getParameter("creationDate"))).getTime());

				}

				region = request.getParameter("Region");

				if (region != "") {
					if (region.contains(":")) {
						regionId = region.substring(0, region.indexOf(':'));
						regionName = region.substring(region.indexOf(':') + 1, region.length());

					} else {
						regionId = region;
					}
				}

				areaName = request.getParameter("AreaName");
				streched = request.getParameter("streched") + "";

				// when polygon or Polyline streched
				if (streched.equals("1")) {
					query = session.createQuery("delete AreaBorder t where t.areaId IN (:param1) ");
					query.setParameter("param1", areaid);
					query.executeUpdate();

				}

				for (int i = 0; i < itemParameters.getDictParameterArea().size(); i++) {
					AreaBorder areaBorder = new AreaBorder();
					if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameterArea().get(i).get(Latitude), "")) {
						latitude = "0"; // HAjouz : it is wrong, waiting a request/modification to be changed

					} else {
						latitude = itemParameters.getDictParameterArea().get(i).get(Latitude);
					}
					if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameterArea().get(i).get(Longitude), "")) {
						longitude = "0"; // HAjouz : it is wrong, waiting a request/modification to be changed
					} else {
						longitude = itemParameters.getDictParameterArea().get(i).get(Longitude);
					}
					if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameterArea().get(i).get("areaBorderID"),
							null)) {
						synchronized (this) {
							// areaBorderID= "AREA_BORDER_ID_"+calendar.get(Calendar.YEAR)+"_"
							// +appConfig.getSequenceNbr(67);
							areaBorderID = "AREA_BORDER_ID_" + calendar.get(Calendar.YEAR) + "_"
									+ Integer.parseInt(session.createNativeQuery("SELECT AREA_BORDER FROM SEQ_TABLE")
											.uniqueResult().toString());
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET AREA_BORDER = AREA_BORDER + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
					} else {
						areaBorderID = itemParameters.getDictParameterArea().get(i).get("areaBorderID");
					}

					areaBorder.setAreaId(areaid);
					areaBorder.setSequence(itemParameters.getDictParameterArea().get(i).get("sequence"));
					areaBorder.setId(areaBorderID);
					areaBorder.setLat(latitude);
					areaBorder.setLng(longitude);
					session.saveOrUpdate(areaBorder);
				}

				for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
					AreaFinance areaFinance = new AreaFinance();

					if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get(ID), "0")) {

						synchronized (this) {
							// areaIdFinance= "AREA_FIN_"+calendar.get(Calendar.YEAR)+"_"
							// +appConfig.getSequenceNbr(18);
							areaIdFinance = "AREA_FIN_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(session
									.createNativeQuery("SELECT AREA_FINANCE FROM SEQ_TABLE").uniqueResult().toString());
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET AREA_FINANCE = AREA_FINANCE + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						areaFinance.setId(areaIdFinance);

					} else {
						areaIdFinance = itemParameters.getDictParameter().get(i).get(ID);
						areaFinance.setId(areaIdFinance);
					}

					if ((itemParameters.getDictParameter().get(i).get(START_DATE)) != "") {
						areaFinance.setStartDate(new Timestamp(
								(formatter1.parse(itemParameters.getDictParameter().get(i).get(START_DATE)))
										.getTime()));
					}
					if ((itemParameters.getDictParameter().get(i).get(END_DATE)) != "") {
						areaFinance.setEndDate(new Timestamp(
								(formatter1.parse(itemParameters.getDictParameter().get(i).get(END_DATE))).getTime()));
					}

					areaFinance.setCreationDate(CreationDate);
					areaFinance.setLastModifyDate(new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));
					areaFinance
							.setNumber2gSites(Float.parseFloat(itemParameters.getDictParameter().get(i).get(SITES2G)));
					areaFinance.setPl2g(Float.parseFloat(itemParameters.getDictParameter().get(i).get(PROFIT2G)));
					areaFinance.setNumber2g3gSites(
							Float.parseFloat(itemParameters.getDictParameter().get(i).get(SITES2G3G)));
					areaFinance.setPl2g3g(Float.parseFloat(itemParameters.getDictParameter().get(i).get(PROFIT2G3G)));
					areaFinance.setNumber2g3g4gSites(
							Float.parseFloat(itemParameters.getDictParameter().get(i).get(SITES2G3G4G)));
					areaFinance
							.setPl2g3g4g(Float.parseFloat(itemParameters.getDictParameter().get(i).get(PROFIT2G3G4G)));
					areaFinance
							.setTotalSites(Float.parseFloat(itemParameters.getDictParameter().get(i).get(TOTALSITES)));
					areaFinance.setSumProfitLoss(
							(Float.parseFloat(itemParameters.getDictParameter().get(i).get(TOTALSUM))));
					areaFinance.setAreaId(areaid);
					areaFinance.setAreaName(areaName);
					session.saveOrUpdate(areaFinance);
				}

				area.setCreationDate(CreationDate);
				area.setLastModifieddate(new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));
				area.setId(areaid);
				area.setName(areaName);
				area.setRegionID(regionId);
				area.setRegionName(regionName);
				area.setStatus(request.getParameter("status"));
				session.saveOrUpdate(area);
				tx.commit();
				rtn.put("AREAID", areaid);

			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in AreaFormSave due to \n " + exceptionAsString);
				logger.info("Error in AreaFormSave due to \n " + exceptionAsString);
			} finally {

				if (session != null && session.isOpen()) {
					session.close();
				}

			}
		}
		return rtn;

	}

	@RequestMapping(value = "/GetAllAreas", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllAreas(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		String Area = "%" + request.getParameter("areaId") + "%";
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			try {

				query = session.createQuery(
						"SELECT id, name from Area where id like UPPER(:param1) OR UPPER(name)like UPPER(:param1) ORDER BY lastModifieddate DESC");
				query.setParameter("param1", Area);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("listAreas", query.list());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GetAllAreas due to \n " + exceptionAsString);
				logger.info("Error in GetAllAreas due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSelectedArea", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSelectedArea(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		String AreaId;
		List<AreaBorder> listAreas = null;
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			try {

				AreaId = request.getParameter("areaId");
				query = session.createNativeQuery(
						"SELECT a.AREA_ID,b.AREA_NAME, LISTAGG(a.LATITUDE || ',' || a.LONGTITUDE, ':') WITHIN GROUP (ORDER BY a.SEQ_SORTING) AS borders"
								+ " FROM AREA_BORDER a Inner Join Area b on  a.AREA_ID=b.AREA_ID"
								+ " WHERE (a.AREA_ID LIKE UPPER('%" + AreaId + "%') OR b.AREA_NAME LIKE UPPER('%"
								+ AreaId + "%'))" + " GROUP BY a.AREA_ID,b.AREA_NAME");
				listAreas = query.list();

				rtn.put("listAreas", listAreas);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getSelectedArea due to \n " + exceptionAsString);
				logger.info("Error in getSelectedArea due to \n " + exceptionAsString);
			} finally {

				if (session != null && session.isOpen()) {
					session.close();
				}

			}
		}

		return rtn;
	}

}