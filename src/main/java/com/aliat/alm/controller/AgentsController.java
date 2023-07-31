package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.Agent;
import com.aliat.alm.models.AgentArea;
import com.aliat.alm.models.AgentBorder;
import com.aliat.alm.models.AgentRegion;
import com.aliat.alm.models.AgentShops;
import com.aliat.alm.models.AgentsListView;
import com.aliat.alm.models.AreaBorder;
import com.aliat.alm.models.RegionBorder;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AgentsController {
	private static final Logger logger = Logger.getLogger(AgentsController.class.getName());
	public String AGENTID = null;
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;

	
	@Autowired
	Form form;
	
	@Autowired
	Notify notification;
	
	@Autowired
	ALMSessions almsessions;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/AgentListView", method = RequestMethod.GET)
	public String AgentListView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}

		else {
			session = almsessions.getSession();
			if (session != null && session.isOpen()) {

				tx = session.beginTransaction();
				notification.headerNotifications(session, model);
				try {

					List<AgentsListView> listAR = new ArrayList<AgentsListView>();
					String str = "select AGENT_ID as agentId, FIRST_NAME as agfirstName, LAST_NAME as aglastName, ADDRESS as  "
							+ " agaddress,TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate,"
							+ " MSISDN as agMSISDN , STATUS as agStatus from AGENT order by LAST_MODIFIED_DATE DESC";

					Query query = session.createSQLQuery(str);
					listAR = ((SQLQuery) query).addScalar("agentId").addScalar("agfirstName").addScalar("aglastName")
							.addScalar("lastModifiedDate").addScalar("agaddress").addScalar("agMSISDN")
							.addScalar("agStatus").setResultTransformer(Transformers.aliasToBean(AgentsListView.class))
							.list();

					model.addAttribute("ListGridTable", mapper.writeValueAsString(listAR));

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in AgentListView due to \n "+ exceptionAsString);
					logger.info("Error in AgentListView due to \n "+ exceptionAsString);
					model.addAttribute("ListGridTable", "");
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return "AgentListView";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredAgentListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredAgentListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;	
		}
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			
			try {
				String startdate, enddate,   regionid, regionname,areaid,areaname,shopid,shopname,MSISDN;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				regionid = request.getParameter("regionid");
				regionname = request.getParameter("regionname");
				areaid = request.getParameter("areaid");
				areaname = request.getParameter("areaname");
				shopid = request.getParameter("shopid");
				shopname = request.getParameter("shopname");
				MSISDN = request.getParameter("MSISDN");
			
				List<String> listAgent = new ArrayList<String>();
				String str = " select AGENT_ID as agentId ,FIRST_NAME as firstName,LAST_NAME as lastName,ADDRESS as address,MSISDN as MSISDN , STATUS as agnStatus ,TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate  from AGENT";

				if (startdate != null && enddate != null) {
					str = str + " where CREATE_DATE between TO_DATE('" + startdate + "','YYYY-MM-DD') and TO_DATE('"
							+ enddate + "','YYYY-MM-DD')";
				}

				
				if (areaid != null && !areaid.equalsIgnoreCase("") || areaname != null && !areaname.equalsIgnoreCase("")) {

					str = str + " and AGENT_ID in (select AGENT_ID from AGENT_AREAS where (AREA_ID)  LIKE upper('%" + areaid + "%')  and upper(AREA_NAME) LIKE upper('%" + areaname + "%'))";
				}
				if (regionid != null && !regionid.equalsIgnoreCase("") || regionname != null && !regionname.equalsIgnoreCase("")) {

					str = str + " and AGENT_ID in (select AGENT_ID from AGENT_REGIONS where (REGION_ID)  LIKE upper('%" + regionid + "%')  and upper(REGION_NAME) LIKE upper('%" + regionname + "%'))";
				}
				
				if (shopid != null && !shopid.equalsIgnoreCase("") || shopname != null && !shopname.equalsIgnoreCase("")) {

					str = str + " and AGENT_ID in (select AGENT_ID from AGENT_SHOPS where (SHOPS_ID)  LIKE upper('%" + shopid + "%')  and upper(SHOPS_NAME) LIKE upper('%" + shopname + "%'))";
				}

				if (MSISDN != null && !MSISDN.equalsIgnoreCase("")) {

					str = str + " and upper(MSISDN) LIKE upper('%" + MSISDN + "%')";
				}

				
				str = str + " ORDER BY LAST_MODIFIED_DATE DESC ";

				
				Query query = session.createSQLQuery(str);

				listAgent = query.list();
				
				rtn.put("listAgent",listAgent);
				
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in showing the filtered Agent list view due to \n "+ exceptionAsString);
				logger.info("Error in showing the filtered Agent list view due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;
	}
	@RequestMapping(value = "/AgentDelete", method = RequestMethod.GET)
	@ResponseBody
	public String AgentDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		String rtn = "Failed";
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		String agentID;
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				agentID = request.getParameter("agentID");
				if (agentID != null) {
					query = session.createSQLQuery("delete AGENT_AREAS  where AGENT_ID ='" + agentID + "'");
					query.executeUpdate();
					query = session.createSQLQuery("delete AGENT_SHOPS  where AGENT_ID='" + agentID + "'");
					query.executeUpdate();
					query = session.createSQLQuery("delete AGENT_REGIONS  where AGENT_ID= '" + agentID + "'");
					query.executeUpdate();
					query = session.createSQLQuery("Delete AGENT where AGENT_ID='" + agentID + "'");
					query.executeUpdate();
					rtn = "success";
				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in AgentDelete due to \n "+ exceptionAsString);
				logger.info("Error in AgentDelete due to \n "+ exceptionAsString);
				rtn = "fail";

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
	@RequestMapping(value = "/AgentFormView", method = RequestMethod.GET)
	public String AgentFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		String agentId, navAction = "2";
		Calendar calendar = new GregorianCalendar();
		Agent Agent = null;
		// List<Agent> qAgent = new ArrayList<Agent>();
		// List<AgentShops> qAgentShops = new ArrayList<AgentShops>();
		List<AgentArea> qAgentArea = new ArrayList<AgentArea>();
		List<AgentRegion> qlistAgentRegion = new ArrayList<AgentRegion>();
		// List<AreaBorder> qAreaBorder = new ArrayList<AreaBorder>();
		// List<RegionBorder> qRegionBorder = new ArrayList<RegionBorder>();
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String result[] = new String[4];
		int SelectedIndex = 0;

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notification.headerNotifications(session, model);
			try {
					//////////////////////////////////////Area and Area Borders//////////////////////////////////////////////////
					List<Object[]> areaIDs = new ArrayList<>();
					List<Object> areas = new ArrayList<>();
					List<Object> Area_Borders = new ArrayList<>();
					String LatLong = null;
					query = session.createSQLQuery("SELECT AREA_ID FROM AREA");
					if(query.list().size() > 0) {
						areaIDs = query.list();
						for(int i = 0;i<areaIDs.size();i++) {
							areas = new ArrayList<>();
							query = session.createSQLQuery("SELECT AREA_NAME from AREA where AREA_ID='"+areaIDs.get(i)+"'");
							String areaName = query.uniqueResult().toString();
							
							query = session.createSQLQuery("SELECT LISTAGG(a.LATITUDE || ',' || a.LONGTITUDE, ':') WITHIN GROUP (ORDER BY a.SEQ_SORTING + 0 ASC) AS borders " + 
							" from area_border a" + 
							" where area_id = '"+areaIDs.get(i)+"'");
							
							if(query.uniqueResult() != null) {
							LatLong = query.uniqueResult().toString();
							}else {
							LatLong = "N/A";
							}
							
							areas.add(areaIDs.get(i));
							areas.add(areaName);
							areas.add(LatLong);
							Area_Borders.add(areas);
						}
					}
										
					//////////////////////////////////////Region and Region Borders//////////////////////////////////////////////////
						List<Object[]> regionIDs = new ArrayList<>();
						List<Object> regions = new ArrayList<>();
						List<Object> Region_Borders = new ArrayList<>();
						
						query = session.createSQLQuery("SELECT REGION_ID FROM REGION");
						if(query.list().size() > 0) {
							regionIDs = query.list();
							for(int i = 0;i<regionIDs.size();i++) {
								regions = new ArrayList<>();
								query = session.createSQLQuery("SELECT REGION_NAME from REGION where REGION_ID='"+regionIDs.get(i)+"'");
								String regionName = query.uniqueResult().toString();
								
								query = session.createSQLQuery("SELECT REGION_CODE from REGION where REGION_ID='"+regionIDs.get(i)+"'");
								String regionCode = query.uniqueResult().toString();
								
								query = session.createSQLQuery("SELECT LISTAGG(a.LATITUDE || ',' || a.LONGTITUDE, ':') WITHIN GROUP (ORDER BY a.SEQ_SORTING + 0 ASC) AS borders " + 
								" from REGION_BORDER a" + 
								" where REGION_ID = '"+regionIDs.get(i)+"'");
								
								if(query.uniqueResult() != null) {
								LatLong = query.uniqueResult().toString();
								}else {
								LatLong = "N/A";
								}
								
								regions.add(regionIDs.get(i));
								regions.add(regionName);
								regions.add(regionCode);
								regions.add(LatLong);
								Region_Borders.add(regions);
							}
						}
				navAction = request.getParameter("NavAction");
				agentId = request.getParameter("agentID");
				// System.out.print("agentID is " + agentId);
				if (agentId == null) {
					// to be deleted
					// model.addAttribute("ListPRqItem", "addNew");
					calendar = new GregorianCalendar();
					calendar.setTime(new Timestamp(System.currentTimeMillis()));
					model.addAttribute("creationDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("lastModifiedDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					// to be deleted
					// model.addAttribute("listItemsNav", mapper.writeValueAsString("noList"));
					model.addAttribute("listAgentShops", "addNew");
					model.addAttribute("listAgentArea", "addNew");
					model.addAttribute("listAgentRegion", "addNew");
					model.addAttribute("listAreaBorder", "addNew");
					model.addAttribute("listRegionBorder", "addNew");
					model.addAttribute("AGENT_IMAGE", "addNew");
					model.addAttribute("AGENT_FRONT_ID", "addNew");
					model.addAttribute("AGENT_BACK_ID", "addNew");
					model.addAttribute("SelectedIndex", "addNew");
					model.addAttribute("AgentsCount", "addNew");
					model.addAttribute("docStatus", "addNew");
					model.addAttribute("agentTracingList", "addNew");
					model.addAttribute("coverage_GISquey", "addNew");
					model.addAttribute("upload_GISquey", "addNew");
					model.addAttribute("download_GISquey", "addNew");
					
					//return the area and area_borders on load
					if(Area_Borders.size() > 0) {
					model.addAttribute("areasData", mapper.writeValueAsString(Area_Borders));
					}else {
						model.addAttribute("areasData", "addNew");
					}
					
					//return the region and region_borders on load
					if(Region_Borders.size() > 0) {
					model.addAttribute("regionsData", mapper.writeValueAsString(Region_Borders));
					}else {
						model.addAttribute("regionsData", "addNew");
					}

				} else {
					result = form.NavigationNP(session, "AGENT", "AGENT_ID", agentId, "LAST_MODIFIED_DATE",
							navAction);
					SelectedIndex = Integer.parseInt(result[1]);
					agentId = result[2];
					Agent = (Agent) session.get(Agent.class, agentId);
					model.addAttribute("SelectedIndex", SelectedIndex);
					model.addAttribute("AgentsCount", Integer.parseInt(result[0]));
					// qAgent = session.createSQLQuery("SELECT AGENT_ID FROM AGENT").list();
					// qAgentShops = session.createSQLQuery("SELECT AGENT_SHOPS_ID FROM
					// AGENT_SHOPS").list();
					// qAgentArea = session.createSQLQuery("SELECT AGENT_AREA_ID FROM
					// AGENT_AREAS").list();
					// qlistAgentRegion = session.createSQLQuery("SELECT AGENT_REGION_ID FROM
					// AGENT_REGIONS").list();
					// qAreaBorder = session.createSQLQuery("SELECT AREA_ID FROM
					// AREA_BORDER").list();
					// qRegionBorder = session.createSQLQuery("SELECT REGION_ID FROM
					// REGION_BORDER").list();

					// to be deleted
					/*
					 * if (qAgent != null) { //to be deleted //model.addAttribute("listItemsNav",
					 * mapper.writeValueAsString(qAgent)); query =
					 * session.createQuery("from Agent where agentId like :param1");
					 * query.setParameter("param1", agentId); qAgent = query.list();
					 * model.addAttribute("listAgents", mapper.writeValueAsString(qAgent)); } else {
					 * model.addAttribute("listAgents", "addNew"); }
					 */

					// if (qAgentShops != null) {

					query = session.createSQLQuery(
							"SELECT AGENT_SHOPS_ID,SHOPS_NAME,SHOPS_LONG,SHOPS_LAT,SHOPS_ID,CREATION_DATE,"
									+ "LAST_MODIFIED_DATE" + " from AGENT_SHOPS where AGENT_ID like :param1");
					query.setString("param1", agentId);
					if (query.list().size() > 0) {
						model.addAttribute("listAgentShops", mapper.writeValueAsString(query.list()));
					} else {
						model.addAttribute("listAgentShops", "addNew");
					}

					// get Agent Area if existed
					HashMap<String, List<AreaBorder>> hash_map = new HashMap<String, List<AreaBorder>>();
					List<AreaBorder> listAreaBorder = null;
					query = session.createQuery("from AgentArea where agentId like :param1");
					query.setParameter("param1", agentId);
					qAgentArea = query.list();
					/////////////////////////////////////////////////////////////////////
					if (qAgentArea.size() > 0) {
						for (int i = 0; i < qAgentArea.size(); i++) {
							query = session
									.createQuery("select t.lng as lng, t.lat as lat from AreaBorder t where areaId ='"
											+ qAgentArea.get(i).getAreaId() + "' ORDER BY t.Sequence + 0 ASC ");
							listAreaBorder = query
									.setResultTransformer(new AliasToBeanResultTransformer(AreaBorder.class)).list();
							hash_map.put(qAgentArea.get(i).getAreaId(), listAreaBorder);
						}
						model.addAttribute("listAgentArea", mapper.writeValueAsString(qAgentArea));
						model.addAttribute("listAreaBorder", mapper.writeValueAsString(hash_map));
					} else {
						model.addAttribute("listAreaBorder", "addNew");
						model.addAttribute("listAgentArea", "addNew");
					}

					// get agent region if existed
					List<RegionBorder> listRegionBorder = null;
					HashMap<String, List<RegionBorder>> hash_map1 = new HashMap<String, List<RegionBorder>>();
					//////////////////////
					// if (qlistAgentRegion != null) {
					query = session.createQuery("from AgentRegion where agentId like :param1");
					query.setParameter("param1", agentId);
					qlistAgentRegion = query.list();

					if (qlistAgentRegion.size() > 0) {
						for (int i = 0; i < qlistAgentRegion.size(); i++) {
							query = session.createQuery(
									"select t.lng as lng, t.lat as lat from RegionBorder t where regionId ='"
											+ qlistAgentRegion.get(i).getRegionId() + "' ORDER BY sequence + 0 ASC");
							listRegionBorder = query
									.setResultTransformer(new AliasToBeanResultTransformer(AgentBorder.class)).list();
							hash_map1.put(qlistAgentRegion.get(i).getRegionId(), listRegionBorder);
						}
						model.addAttribute("listAgentRegion", mapper.writeValueAsString(qlistAgentRegion));
						model.addAttribute("listRegionBorder", mapper.writeValueAsString(hash_map1));
					} else {
						model.addAttribute("listRegionBorder", "addNew");
						model.addAttribute("listAgentRegion", "addNew");
					}

					if (Agent.getCreateDate() == null) {
						model.addAttribute("creationDate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					} else {
						model.addAttribute("creationDate", formatter.format(Agent.getCreateDate()).toString());
					}
					model.addAttribute("dName", Agent.getDisplayName());
					model.addAttribute("agentID", Agent.getAgentId());
					model.addAttribute("lastModifiedDate", formatter.format(Agent.getLastModifiedDate()).toString());
					model.addAttribute("fname", Agent.getFirstName());
					model.addAttribute("lname", Agent.getLastName());
					model.addAttribute("address", Agent.getAddress());
					model.addAttribute("emaill", Agent.getEmail());
					model.addAttribute("MSISDN", Agent.getMSISDN());
					model.addAttribute("agentMSISDN", Agent.getMSISDN());
					model.addAttribute("fullName", Agent.getFullName());
					model.addAttribute("PIN", Agent.getPIN());
					model.addAttribute("agentLat", Agent.getAgentLat());
					model.addAttribute("agentLng", Agent.getAgentLng());
					model.addAttribute("ordStatus", Agent.getStatus());
					model.addAttribute("ordStatus", Agent.getStatus());
					model.addAttribute("ordStatus", Agent.getStatus());
					model.addAttribute("runningInterval", Agent.getRunningInterval());
					model.addAttribute("coverageRunningInterval", Agent.getCoverageRunningInterval());
					if (Agent.getSiteEngineer() == '0') {
						model.addAttribute("siteEngineer", "");
					} else {
						model.addAttribute("siteEngineer", "checked");
					}

					if (Agent.getCaptureCoverage() == '0') {
						model.addAttribute("captureCoverage", "");
					} else {
						model.addAttribute("captureCoverage", "checked");
					}
					if (Agent.getSuperAgent().equalsIgnoreCase("0")) {
						model.addAttribute("superAgentPermission", "");
					} else {
						model.addAttribute("superAgentPermission", "checked");
					}

					if (Agent.getCaptureSpeed() == '0') {
						model.addAttribute("captureSpeed", "");
					} else {
						model.addAttribute("captureSpeed", "checked");
					}

					if(StringUtils.equalsAnyIgnoreCase(Agent.getImagestatus(), "1")) {
						if (StringUtils.equalsIgnoreCase(Agent.getAgentImage(), "") || Agent.getAgentImage() == null) {
							model.addAttribute("AGENT_IMAGE", "addNew");
						} else {
							model.addAttribute("AGENT_IMAGE", Agent.getAgentImage());

						}
					}else {
						model.addAttribute("AGENT_IMAGE", "addNew");
					}
					
					if(StringUtils.equalsIgnoreCase(Agent.getFrontsideStatus(),"1")) {
						if (StringUtils.equalsIgnoreCase(Agent.getAgentFrontId(), "") || Agent.getAgentFrontId() == null) {

							model.addAttribute("AGENT_FRONT_ID", "addNew");

						} else {

							model.addAttribute("AGENT_FRONT_ID", Agent.getAgentFrontId().toString());

						}
					}else {
						model.addAttribute("AGENT_FRONT_ID", "addNew");

					}
					
					if(StringUtils.equalsIgnoreCase(Agent.getBacksideStatus(),"1")) {
						if (StringUtils.equalsIgnoreCase(Agent.getAgentBackId(), "") || Agent.getAgentBackId() == null) {
							model.addAttribute("AGENT_BACK_ID", "addNew");
						} else {
							model.addAttribute("AGENT_BACK_ID", Agent.getAgentBackId().toString());

						}
					}else {
						model.addAttribute("AGENT_BACK_ID", "addNew");
					}
					//return the area and area_borders on load
					if(Area_Borders.size() > 0) {
						model.addAttribute("areasData", mapper.writeValueAsString(Area_Borders));
					}else {
							model.addAttribute("areasData", "addNew");
					}
					
					//return the area and area_borders on load
					if(Region_Borders.size() > 0) {
					model.addAttribute("regionsData", mapper.writeValueAsString(Region_Borders));
					}else {
						model.addAttribute("regionsData", "addNew");
					}

					/// get agent trace from the speed coverage table
					query = session.createSQLQuery(
							"select TO_CHAR(SPEEDCOVERAGE_DATE,'YYYY-MM-DD HH24:MI:SS') as startDate ,TO_CHAR(SPEEDCOVERAGE_DATE,'YYYY-MM-DD HH24:MI:SS') as endDate,SPEEDCOVERAGE_LAT,SPEEDCOVERAGE_LNG,COVERAGE_SIGNAL,SPEED_DOWNLOAD,SPEED_UPLOAD"
									+ " from speed_coverage_test " + " where  not(COVERAGE_SIGNAL is null or COVERAGE_SIGNAL='null' or COVERAGE_SIGNAL='N/A') " + 
									"and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') " + 
									"and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') "+
									"and agent_number='" + Agent.getMSISDN()
									+ "' and (SPEEDCOVERAGE_DATE between SYSDATE - INTERVAL '1' DAY and SYSDATE)"
									+ " ORDER BY SPEEDCOVERAGE_DATE asc");
					String agentTracing = mapper.writeValueAsString(query.list());

					if (agentTracing == null || agentTracing.equalsIgnoreCase("[]")) {
						model.addAttribute("agentTracingList", "addNew");
					} else {
						model.addAttribute("agentTracingList", agentTracing);
					}

					/// coverage GIS
					query = session.createSQLQuery(
							"select SPEEDCOVERAGEID,TO_NUMBER(COVERAGE_SIGNAL),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST " + 
							"where not(COVERAGE_SIGNAL is null or COVERAGE_SIGNAL='null' or COVERAGE_SIGNAL='N/A') " + 
							"and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') " + 
							"and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null')" + 
							"and not(CID is null or CID='null') "
							+" and agent_number='" + Agent.getMSISDN()+"'"
							+" and  SPEEDCOVERAGE_DATE between SYSDATE - INTERVAL '1' DAY and SYSDATE"
							+ " ORDER BY SPEEDCOVERAGE_DATE ASC");

					model.addAttribute("coverage_GISquey", mapper.writeValueAsString(query.list()));

					/// Download GIS
					query = session.createSQLQuery(
							"select SPEEDCOVERAGEID,TO_NUMBER(SPEED_DOWNLOAD),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST " + 
							"where not(SPEED_DOWNLOAD is null or SPEED_DOWNLOAD='null' or SPEED_DOWNLOAD= 'N/A') " + 
							"and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') " + 
							"and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') " + 
							"and not(CID is null or CID='null') " +
							" and agent_number='" + Agent.getMSISDN()+"'"
							+"and SPEEDCOVERAGE_DATE between SYSDATE - INTERVAL '1' DAY and SYSDATE"
							+ " ORDER BY SPEEDCOVERAGE_DATE ASC");

					model.addAttribute("download_GISquey", mapper.writeValueAsString(query.list()));

					query = session.createSQLQuery(
							"select SPEEDCOVERAGEID,TO_NUMBER(SPEED_UPLOAD),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST " + 
							"where not(SPEED_UPLOAD is null or SPEED_UPLOAD='null' or SPEED_UPLOAD = 'N/A') " + 
							"and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') " + 
							"and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') " + 
							"and not(CID is null or CID='null') " +
							" and agent_number='" + Agent.getMSISDN()+"'"
							+"and  SPEEDCOVERAGE_DATE between SYSDATE - INTERVAL '1' DAY and SYSDATE"
							+ " ORDER BY SPEEDCOVERAGE_DATE ASC");

					model.addAttribute("upload_GISquey", mapper.writeValueAsString(query.list()));
					
					
					
					
					
					
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in AgentFormView due to \n "+ exceptionAsString);
				logger.info("Error in AgentFormView due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "AgentFormView";
	}

	@RequestMapping(value = "/AgentFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> AgentFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		Calendar calendar = new GregorianCalendar();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Agent Agent = new Agent();
		String agentId, agentShopsId, agentRegionId;

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

///////////////////////////////////////////////////////// SEND EMAIL BUTTON  //////////////////////////////////////////////////////////
				/*
				 * String email = request.getParameter("email"); String emailTo =
				 * request.getParameter("emailTo"); String password =
				 * request.getParameter("password"); String message =
				 * request.getParameter("message"); String subject =
				 * request.getParameter("subject"); String ccmail =
				 * request.getParameter("ccmail");
				 * 
				 * if (StringUtils.equalsIgnoreCase(request.getParameter("email"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("password"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("emailTo"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("message"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("subject"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), "")) {
				 * System.out.println("NO EMAIL TO SEND!");
				 * 
				 * } else if (StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), ""))
				 * {
				 * 
				 * JavaMailUtil.SendEmails(email, password, emailTo, subject, message);
				 * 
				 * } else { JavaMailUtil.SendccEmails(email, password, emailTo, ccmail, subject,
				 * message); }
				 */
///////////////////////////////////////////// END OF SEND EMAIL BUTTON  //////////////////////////////////////////////////////////

				agentId = request.getParameter("agentID");

				int year = calendar.get(Calendar.YEAR);

				if (StringUtils.equalsIgnoreCase(agentId, "")) {
					synchronized (this) {						
						agentId = "AG_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AGENT FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET AGENT = AGENT + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}

					if (request.getParameter("creationDate") == "") {

						Agent.setCreateDate((new Timestamp((new Timestamp(System.currentTimeMillis())).getTime())));

					}

					Agent.setCreateDate(
							(new Timestamp(formatter.parse((request.getParameter("creationDate"))).getTime())));
					Agent.setLastModifiedDate((new Timestamp((new Timestamp(System.currentTimeMillis())).getTime())));
					Agent.setAgentId(agentId);
					Agent.setDisplayName(request.getParameter("dName"));
					Agent.setFirstName(request.getParameter("fname"));
					Agent.setLastName(request.getParameter("lname"));
					Agent.setAddress(request.getParameter("address"));
					Agent.setEmail(request.getParameter("emaill"));
					Agent.setMSISDN(request.getParameter("MSISDN"));
					Agent.setAgentLat(request.getParameter("agentLat"));
					Agent.setAgentLng(request.getParameter("agentLng"));
					Agent.setFullName(request.getParameter("fullName"));
					Agent.setPIN(request.getParameter("PIN"));
					Agent.setSuperAgent(request.getParameter("superAgentPermission"));
					Agent.setStatus(request.getParameter("status"));
					Agent.setCaptureSpeed(request.getParameter("captureSpeed").charAt(0));
					Agent.setRunningInterval(request.getParameter("runningInterval"));
					Agent.setSiteEngineer(request.getParameter("siteEngineer").charAt(0));
					Agent.setCaptureCoverage(request.getParameter("captureCoverage").charAt(0));
					Agent.setCoverageRunningInterval(request.getParameter("coverageRunningInterval"));
					session.saveOrUpdate(Agent);

				} else {

					query = session.createSQLQuery("UPDATE AGENT SET " + "FIRST_NAME='" + request.getParameter("fname")
							+ "'," + "LAST_NAME='" + request.getParameter("lname") + "'," + "DISPLAY_NAME='"
							+ request.getParameter("dName") + "'," + "ADDRESS='" + request.getParameter("address")
							+ "'," + "EMAIL='" + request.getParameter("emaill") + "'," + "MSISDN='"
							+ request.getParameter("MSISDN") + "'," + "LAST_MODIFIED_DATE=SYSDATE," + "STATUS='"
							+ request.getParameter("status") + "'," + "PIN_CODE='" + request.getParameter("PIN") + "',"
							+ "LONGITUDE='" + request.getParameter("agentLng") + "'," + "LATITUDE='"
							+ request.getParameter("agentLat") + "'," + "FULL_NAME='" + request.getParameter("fullName")
							+ "'," + "SUPERAGENT='" + request.getParameter("superAgentPermission") + "',"
							+ "CAPTURE_SPEED='" + request.getParameter("captureSpeed").charAt(0) + "',"
							+ "SITE_ENGINEER='" + request.getParameter("siteEngineer").charAt(0) + "',"
							+ "RUNNING_INTERVAL='" + request.getParameter("runningInterval") + "',"
							+ "CAPTURE_COVERAGE='" + request.getParameter("captureCoverage").charAt(0) + "',"
							+ "COVERAGE_RUNNING_INTERVAL='" + request.getParameter("coverageRunningInterval") + "'"
							+ " WHERE AGENT_ID='" + agentId + "'");

					query.executeUpdate();

				}

				// end of saving agent

				if (request.getParameterValues("slctDelShops[]") != null) {
					System.out.println(request.getParameterValues("slctDelShops[]").length);
					for (int i = 0; i < request.getParameterValues("slctDelShops[]").length; i++) {
						System.out.println(request.getParameterValues("slctDelShops[]")[i]);
						String shopID = session
								.createSQLQuery("SELECT SHOPS_ID from AGENT_SHOPS where AGENT_SHOPS_ID='"
										+ (request.getParameterValues("slctDelShops[]"))[i] + "'")
								.uniqueResult().toString();

						System.out.println(shopID);
						query = session.createSQLQuery("UPDATE SHOPS SET"
								+ " AGENT_ID = '', AGENT_NAME = ''  where SHOPS_ID='" + shopID + "'");
						query.executeUpdate();

					}

					query = session.createQuery("delete AgentShops t  where t.agentShopsId IN (:param1) ");
					query.setParameterList("param1", request.getParameterValues("slctDelShops[]"));
					query.executeUpdate();

				}

				if (request.getParameterValues("slctDelArea[]") != null) {

					query = session.createQuery("delete AgentArea t  where t.agentAreaId IN (:param1) ");
					query.setParameterList("param1", request.getParameterValues("slctDelArea[]"));
					query.executeUpdate();

				}

				if (request.getParameterValues("slctDelRegion[]") != null) {

					query = session.createQuery("delete AgentRegion t  where t.agentRegionId IN (:param1)");
					query.setParameterList("param1", request.getParameterValues("slctDelRegion[]"));
					query.executeUpdate();

				}

				////////////////// boq shops table
				if (itemParameters.getDictParameter() != null) {

					for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
						AgentShops AgentShops = new AgentShops();

						agentShopsId = itemParameters.getDictParameter().get(i).get("agentShopsId");

						String previousShopID = null;
						if (StringUtils.equalsIgnoreCase(agentShopsId, "")) {

							synchronized (this) {						
								agentShopsId = "AG_SHOPS_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AGENT_SHOPS FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createSQLQuery("UPDATE SEQ_TABLE SET AGENT_SHOPS = AGENT_SHOPS + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
								}

							AgentShops.setCreateDate(
									new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));
							AgentShops.setLastModifiedDate(
									new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));
							AgentShops.setAgentShopsId(agentShopsId);
							AgentShops.setShopsId(itemParameters.getDictParameter().get(i).get("shopsId"));
							AgentShops.setShopsname(itemParameters.getDictParameter().get(i).get("Shopsname"));
							AgentShops.setLongtitude(itemParameters.getDictParameter().get(i).get("Longtitude"));
							AgentShops.setLatitude(itemParameters.getDictParameter().get(i).get("Latitude"));
							AgentShops.setAgentId(agentId);

							session.saveOrUpdate(AgentShops);

						} else {

							query = session.createSQLQuery(
									"SELECT SHOPS_ID FROM AGENT_SHOPS WHERE AGENT_SHOPS_ID='" + agentShopsId + "'");
							previousShopID = query.uniqueResult().toString();
							if (previousShopID != null) {
								query = session.createSQLQuery("UPDATE SHOPS SET"
										+ " AGENT_ID = '', AGENT_NAME = ''  where SHOPS_ID='" + previousShopID + "'");
								query.executeUpdate();

							}
							AgentShops.setCreateDate(new Timestamp(formatter
									.parse(itemParameters.getDictParameter().get(i).get("createDate")).getTime()));
							AgentShops.setLastModifiedDate(
									new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));
							AgentShops.setAgentShopsId(agentShopsId);
							AgentShops.setShopsId(itemParameters.getDictParameter().get(i).get("shopsId"));
							AgentShops.setShopsname(itemParameters.getDictParameter().get(i).get("Shopsname"));
							AgentShops.setLongtitude(itemParameters.getDictParameter().get(i).get("Longtitude"));
							AgentShops.setLatitude(itemParameters.getDictParameter().get(i).get("Latitude"));
							AgentShops.setAgentId(agentId);

							session.saveOrUpdate(AgentShops);

						}

						query = session.createSQLQuery("UPDATE SHOPS SET " + " AGENT_ID='" + agentId + "',"
								+ " AGENT_NAME='" + request.getParameter("fullName") + "' where SHOPS_ID='"
								+ itemParameters.getDictParameter().get(i).get("shopsId") + "'");
						query.executeUpdate();

					}

				}

				////////////// end of boq shops table
				if (itemParameters.getDictParameterArea() != null) {

					String agentAreaId;

					for (int i = 0; i < itemParameters.getDictParameterArea().size(); i++) {

						AgentArea AgentArea = new AgentArea();
						agentAreaId = itemParameters.getDictParameterArea().get(i).get("agentAreaId");

						if (StringUtils.equalsIgnoreCase(agentAreaId, "")) {
							synchronized (this) {						
								agentAreaId = "AG_Area_" + year + "_" +  Integer.parseInt(session.createSQLQuery("SELECT AGENT_AREAS FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createSQLQuery("UPDATE SEQ_TABLE SET AGENT_AREAS = AGENT_AREAS + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
								}
						}

						AgentArea.setAgentAreaId(agentAreaId);
						AgentArea.setAreaId(itemParameters.getDictParameterArea().get(i).get("areaId"));
						AgentArea.setAreaname(itemParameters.getDictParameterArea().get(i).get("Areaname"));
						AgentArea.setArealat(itemParameters.getDictParameterArea().get(i).get("areaLat"));
						AgentArea.setArealong(itemParameters.getDictParameterArea().get(i).get("areaLong"));
						AgentArea.setAgentId(agentId);
						session.saveOrUpdate(AgentArea);

					}

				}
				////////////////////////////////////////////////////////////////////////////////////////////////////////
				if (itemParameters.getDictParameterRegion() != null) {

					for (int i = 0; i < itemParameters.getDictParameterRegion().size(); i++) {

						AgentRegion AgentRegion = new AgentRegion();

						agentRegionId = itemParameters.getDictParameterRegion().get(i).get("agentRegionId");

						if (StringUtils.equalsIgnoreCase(agentRegionId, "")) {
							synchronized (this) {						
								agentRegionId = "AG_REGION_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AGENT_REGIONS FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createSQLQuery("UPDATE SEQ_TABLE SET AGENT_REGIONS = AGENT_REGIONS + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
								}
						}

						AgentRegion.setAgentRegionId(agentRegionId);
						AgentRegion.setRegionId(itemParameters.getDictParameterRegion().get(i).get("regionId"));
						AgentRegion.setRegionname(itemParameters.getDictParameterRegion().get(i).get("Regionname"));
						AgentRegion.setRegioncode(itemParameters.getDictParameterRegion().get(i).get("Regioncode"));
						AgentRegion.setAgentId(agentId);
						session.saveOrUpdate(AgentRegion);

					}

				}
				/////////////////////////////////////////////////////////////////////////////////////////////////////
				rtn.put("agentID", agentId);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in AgentFormSave due to \n "+ exceptionAsString);
				logger.info("Error in AgentFormSave due to \n "+ exceptionAsString);
			} finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}

			}
		}
		return rtn;
	}

	//// generate report for agent tracking
	@RequestMapping(value = "/GenerateAgentTracingReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateAgentTracingReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				/// GRID
				String StartDate = request.getParameter("startDate");
				if (StartDate.contains("PM")) {
					StartDate = StartDate.replace(" PM", "").trim();

				} else {
					StartDate = StartDate.replace(" AM", "").trim();
				}

				String EndDate = request.getParameter("endDate");
				if (EndDate.contains("PM")) {
					EndDate = EndDate.replace(" PM", "").trim();

				} else {
					EndDate = EndDate.replace(" AM", "").trim();
				}

				/// get agent trace from the speed coverage table
				query = session.createSQLQuery(
						"select TO_CHAR(SPEEDCOVERAGE_DATE,'YYYY-MM-DD HH24:MI:SS') as startDate ,TO_CHAR(SPEEDCOVERAGE_DATE,'YYYY-MM-DD HH24:MI:SS') as endDate,SPEEDCOVERAGE_LAT,SPEEDCOVERAGE_LNG,COVERAGE_SIGNAL,SPEED_DOWNLOAD,SPEED_UPLOAD"
								+ " from speed_coverage_test " + " where not(COVERAGE_SIGNAL is null or COVERAGE_SIGNAL='null' or COVERAGE_SIGNAL='N/A') " + 
								"and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') " + 
								"and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') " + 
								"and not(CID is null or CID='null') "+
								"and not(TECHNOLOGY is null or TECHNOLOGY ='null' or TECHNOLOGY ='N/A')"
								+" and agent_number='"+ request.getParameter("agentNumber") + "' and SPEEDCOVERAGE_DATE between TO_DATE('"
								+ StartDate + "','MM/DD/YYYY HH24:MI:SS') and TO_DATE('" + EndDate
								+ "','MM/DD/YYYY HH24:MI:SS')" + " ORDER BY SPEEDCOVERAGE_DATE ASC");

				rtn.put("generateAgentTracking", query.list());

				/// coverage GIS
				query = session.createSQLQuery(
						"select SPEEDCOVERAGEID,TO_NUMBER(COVERAGE_SIGNAL),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
								+ "where not(COVERAGE_SIGNAL is null or COVERAGE_SIGNAL='null' or COVERAGE_SIGNAL='N/A') " + 
								"and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') " + 
								"and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') " + 
								"and not(CID is null or CID='null') "+
								"and not(TECHNOLOGY is null or TECHNOLOGY ='null' or TECHNOLOGY ='N/A')"
								+" and (SPEEDCOVERAGE_DATE between TO_DATE('" + StartDate
								+ "','MM/DD/YYYY HH24:MI:SS') and TO_DATE('" + EndDate
								+ "','MM/DD/YYYY HH24:MI:SS')) and AGENT_NUMBER='" + request.getParameter("agentNumber")
								+ "' " + " ORDER BY SPEEDCOVERAGE_DATE ASC");

				rtn.put("CoverageReportGIS", query.list());

				/// Download GIS GENERATE REPROT
				query = session.createSQLQuery(
						"select SPEEDCOVERAGEID,TO_NUMBER(SPEED_DOWNLOAD),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
								+ "where not(SPEED_DOWNLOAD ='N/A' or SPEED_DOWNLOAD = 'null' or SPEED_DOWNLOAD is NULL)"
								+" and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') " + 
								"and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') "+
								"and not(CID is null or CID='null') "+
								"and not(TECHNOLOGY is null or TECHNOLOGY ='null' or TECHNOLOGY ='N/A')"
								+ " and (SPEEDCOVERAGE_DATE between TO_DATE('"
								+ StartDate + "','MM/DD/YYYY HH24:MI:SS') and TO_DATE('" + EndDate
								+ "','MM/DD/YYYY HH24:MI:SS')) and AGENT_NUMBER='" + request.getParameter("agentNumber")
								+ "'  ORDER BY SPEEDCOVERAGE_DATE ASC");
				rtn.put("SpeedDownReportGIS", query.list());

				/// Upload GIS GENERATE REPROT
				query = session.createSQLQuery(
						"select SPEEDCOVERAGEID,TO_NUMBER(SPEED_UPLOAD),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
								+ "where not(SPEED_UPLOAD ='N/A' or SPEED_UPLOAD = 'null' or SPEED_UPLOAD is NULL) " + 
								" and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') " + 
								"and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') " + 
								"and not(CID is null or CID='null') " + 
								"and not(TECHNOLOGY is null or TECHNOLOGY ='null' or TECHNOLOGY ='N/A') "
								+ " and (SPEEDCOVERAGE_DATE between TO_DATE('"
								+ StartDate + "','MM/DD/YYYY HH24:MI:SS') and TO_DATE('" + EndDate
								+ "','MM/DD/YYYY HH24:MI:SS')) and AGENT_NUMBER='" + request.getParameter("agentNumber")
								+ "'  ORDER BY SPEEDCOVERAGE_DATE ASC");
				rtn.put("SpeedUpReportGIS", query.list());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GenerateAgentTracingReport due to \n "+ exceptionAsString);
				logger.info("Error in GenerateAgentTracingReport due to \n "+ exceptionAsString);
			} finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}

			}
		}
		return rtn;
	}

///////////////
	@RequestMapping(value = "/GetAllAgents", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllRegionss(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			System.out.println("GetAllAgents");

			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				String agentId = "%" + request.getParameter("agentId") + "%";
				query = session.createSQLQuery(
						"SELECT distinct AGENT_ID,FULL_NAME,MSISDN FROM AGENT where UPPER(AGENT_ID)like UPPER(:param1) OR UPPER(FULL_NAME)like UPPER(:param1) OR MSISDN like :param1 ORDER BY LAST_MODIFIED_DATE DESC");
				query.setString("param1", agentId);
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("ListAgent", query.list());
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GetAllAgents due to \n "+ exceptionAsString);
				logger.info("Error in GetAllAgents due to \n "+ exceptionAsString);
			} finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}

			}
		}
		return rtn;
	}

	@RequestMapping(value = "/getSelectedShop", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSelectedShop(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			rtn.put("Login", "redirect:/");
			return rtn;
		}

		String shopID;

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				shopID = "%" + request.getParameter("Shops") + "%";
				query = session.createSQLQuery(
						"SELECT SHOPS_ID,SHOP_NAME,LONGTITUDE,LATITUDE from SHOPS where (UPPER(SHOPS_ID) LIKE UPPER('" + shopID+ "')"
								+ " or UPPER(SHOP_NAME) LIKE UPPER('" + shopID + "'))"
								+ " and (SHOPS_ID IN (SELECT SHOPS_ID FROM SHOPS WHERE (AGENT_ID IS NULL) OR (AGENT_ID='"+request.getParameter("agentID")+"' )))");
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("selectedShop", query.list());

			} catch (Exception e) {

				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getSelectedShop due to \n "+ exceptionAsString);
				logger.info("Error in getSelectedShop due to \n "+ exceptionAsString);

			} finally {

				if (session != null && session.isOpen()) {

					tx.commit();
					session.close();

				}

			}
		}

		return rtn;
	}

	/*
	 * @RequestMapping(value = "/getSelectedArea", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> getSelectedArea(Locale locale, Model
	 * model, HttpServletRequest request, HttpServletResponse response) {
	 * 
	 * Map<String, Object> rtn = new LinkedHashMap<>(); if
	 * (LoginServices.checkSession(request, response).equals("redirect:/")) {
	 * 
	 * rtn.put("Login", "redirect:/"); return rtn; }
	 * 
	 * String shopID;
	 * 
	 * session = almsessions.getALMSession();
	 * 
	 * if (session != null && session.isOpen()) { tx = session.beginTransaction();
	 * 
	 * try {
	 * 
	 * shopID = "%"+request.getParameter("Shops")+"%"; query =
	 * session.createSQLQuery(
	 * "SELECT SHOPS_ID,SHOP_NAME,LONGTITUDE,LATITUDE from SHOPS where (SHOPS_ID LIKE UPPER('"
	 * +shopID+"') or SHOP_NAME LIKE UPPER('"+shopID+"')) and (AGENT_ID IS NULL)");
	 * query.setFirstResult(0); query.setMaxResults(40); rtn.put("selectedShop",
	 * query.list());
	 * 
	 * } catch (Exception e) {
	 * 
	 * System.out.println("catch messsage is " + e.getMessage());
	 * 
	 * } finally {
	 * 
	 * if (session != null && session.isOpen()) {
	 * 
	 * tx.commit(); session.close();
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * return rtn; }
	 */

	// Thread to get image from sftp server

	/*
	 * Thread imagetoweb = new Thread(new Runnable() {
	 * 
	 * @Override public void run() { try {
	 * 
	 * String host = "196.202.208.23"; String user = "root"; String pass =
	 * "TKLdev#2021$"; int e = 22;
	 * 
	 * Properties config = new Properties(); config.put("StrictHostKeyChecking",
	 * "no");
	 * 
	 * JSch jSch = new JSch(); com.jcraft.jsch.Session session =
	 * jSch.getSession(user, host, e); session.setPassword(pass);
	 * session.setConfig(config); try { session.connect(); ChannelSftp channelSftp =
	 * (ChannelSftp) session.openChannel("sftp"); channelSftp.connect();
	 * 
	 * File BackAgentPicture = new File("src/main/webapp/resources/AgentPic" + "/" +
	 * backside + ".jpg"); File FrontAgenPicture = new
	 * File("src/main/webapp/resources/AgentPic" + "/" + frontside + ".jpg");
	 * 
	 * if (!FrontAgenPicture.exists() && frontsideStatus.equalsIgnoreCase("1")) {
	 * 
	 * String agentSFTPpath = "/usr/share/tomcat/webapps/alm/resources/"; String
	 * fullpath = agentSFTPpath + "AgentPic"; channelSftp.cd(fullpath); String path
	 * = channelSftp.pwd() + "/" + frontside + ".jpg"; File AgentPicture = new
	 * File("src/main/webapp/resources/AgentPic"); String AgentPicturePath =
	 * AgentPicture.getAbsolutePath(); channelSftp.get(path, AgentPicturePath);
	 * 
	 * }
	 * 
	 * if (!BackAgentPicture.exists() && backsideStatus.equalsIgnoreCase("1")) {
	 * 
	 * String agentSFTPpath = "/usr/share/tomcat/webapps/alm/resources/"; String
	 * fullpath = agentSFTPpath + "AgentPic"; channelSftp.cd(fullpath); String path
	 * = channelSftp.pwd() + "/" + backside + ".jpg"; File AgentPicture = new
	 * File("src/main/webapp/resources/AgentPic"); String AgentPicturePath =
	 * AgentPicture.getAbsolutePath(); channelSftp.get(path, AgentPicturePath);
	 * 
	 * }
	 * 
	 * channelSftp.disconnect(); session.disconnect(); } catch (Exception e1) {
	 * e1.printStackTrace(); } } catch (Exception e) { e.printStackTrace(); } } });
	 */

	@RequestMapping(value = "/AgentListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> AgentListViewDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();

		String idForm = null;
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = almsessions.getSession();
		String[] idList = request.getParameterValues("agentID[]");
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				for (int i = 0; i < idList.length; i++) {
					// Get AGENT_ID from the listview form
					idForm = idList[i];

					query = session.createSQLQuery("delete AGENT_AREAS  where AGENT_ID ='" + idForm + "'");
					query.executeUpdate();

					query = session.createSQLQuery("delete AGENT_SHOPS  where AGENT_ID='" + idForm + "'");
					query.executeUpdate();

					query = session.createSQLQuery("delete AGENT_REGIONS  where AGENT_ID= '" + idForm + "'");
					query.executeUpdate();

					query = session.createSQLQuery("Delete AGENT where AGENT_ID='" + idForm + "'");
					query.executeUpdate();

				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in AgentListViewDelete due to \n "+ exceptionAsString);
				logger.info("Error in AgentListViewDelete due to \n "+ exceptionAsString);
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

}
