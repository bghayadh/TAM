package com.aliat.alm.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.RptDbSession;
import com.aliat.alm.models.RevenueChartsReport;
import com.aliat.alm.models.SiteRevenueReport;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SiteRevenueReportController {

	@Autowired
	Notify notifications;
	// Audited By Yara B.Mezher

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SiteRevenueReport", method = RequestMethod.GET)
	public String RevenueReport(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		// logger.info("Welcome home! The client locale is {}.", locale);

		String defaultSites = "", defaultChartSites = "", gridQuery = "", chartQuery = "", revPieQuery = "";
		Query grid, mapQuery, firstChart, technologyPieChart, pieQuery, periodQuery;
		String periodicDataQuery;
		List<Object> defPeriodicData = new ArrayList<Object>();
		ArrayList<Float> chartArray;
		JSONArray chartJSNArr;

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {

			Session sessionRPT = null;
			Session sessionALM = null;
			Transaction txALM = null;
			Transaction txRPT = null;
			sessionALM = AlmDbSession.getInstance().getSession();

			if (sessionALM != null && sessionALM.isOpen()) {

				txALM = sessionALM.beginTransaction();
				notifications.headerNotifications(sessionALM, model);
				txALM.commit();
				sessionALM.close();
			}

			try {
				sessionRPT = RptDbSession.getInstance().getSession();

				if (sessionRPT != null && sessionRPT.isOpen()) {

					txRPT = sessionRPT.beginTransaction();

					// GRID query
					gridQuery = "select t.site_name as \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT DISTINCT a.WARE_NAME as ware,a.COMBINATION_TECHNOLOGY as combination_tech,"
							+ "b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
							+ "sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
							+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= trunc(sysdate) - 2 and b.REVENUE_DATE < (trunc(sysdate) - 2) + 1 "
							+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS'),"
							+ "a.COMBINATION_TECHNOLOGY "
							+ "Union SELECT distinct site_name as ware, 'No Combination Technology' as combination_tech,"
							+ " 'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
							+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
							+ "FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= trunc(sysdate) - 2 and REVENUE_DATE < (trunc(sysdate) - 2) + 1 "
							+ "and site_name LIKE '%null%' " + "GROUP BY site_name, area_id, revenue_date"
							+ ") t order by t.site_name,t.combination_tech asc, t.revenue_date desc";

					grid = sessionRPT.createNativeQuery(gridQuery);

					@SuppressWarnings("unchecked")
					List<SiteRevenueReport> revenueReportResults = (List<SiteRevenueReport>) ((SQLQuery) grid)
							.addScalar("siteName").addScalar("areaId").addScalar("startDate").addScalar("endDate")
							.addScalar("voiceRevenue").addScalar("smsRevenue").addScalar("dataRevneue")
							.addScalar("vasRevenue").addScalar("combination_technology")
							.setResultTransformer(Transformers.aliasToBean(SiteRevenueReport.class)).list();
					// System.out.println("****The SiteRevenueReport is:
					// "+mapper.writeValueAsString(revenueReportResults));

					model.addAttribute("revenueReportList", mapper.writeValueAsString(revenueReportResults));

					// Google maps query
					defaultSites = "SELECT a.WARE_NAME,a.LONGITUDE,a.LATITUDE,a.TECH_2G,a.TECH_3G,a.TECH_4G,a.TECH_5G,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE,a.SITE_ID FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= trunc(sysdate) - 2 AND b.REVENUE_DATE < (trunc(sysdate) - 2) + 1 GROUP BY a.WARE_NAME,a.LONGITUDE, a.LATITUDE, a.TECH_2G,a.TECH_3G, a.TECH_4G, a.TECH_5G, a.SITE_ID";
					mapQuery = sessionRPT.createNativeQuery(defaultSites);
					model.addAttribute("listSites", mapper.writeValueAsString(mapQuery.list()));

					// chartssss
					chartQuery = "select site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, (sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= (trunc(sysdate) - 2) AND REVENUE_DATE < ((trunc(sysdate) - 2) + 1) group by site_name, area_id order by site_name asc";
					firstChart = sessionRPT.createNativeQuery(chartQuery);

					periodicDataQuery = " select TO_CHAR(revenue_date, 'DD/MM/YYYY') as revenue_date, coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue, coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= trunc(sysdate) - 2 AND REVENUE_DATE < (trunc(sysdate) - 2) + 1 group by revenue_date order by REVENUE_DATE desc";
					periodQuery = sessionRPT.createNativeQuery(periodicDataQuery);

					revPieQuery = "select coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue, coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= (trunc(sysdate) - 2) AND REVENUE_DATE < ((trunc(sysdate) - 2) + 1)";
					pieQuery = sessionRPT.createNativeQuery(revPieQuery);

					defaultChartSites = "select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= (trunc(sysdate) - 2) AND b.REVENUE_DATE < ((trunc(sysdate) - 2) + 1) and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology order by a.combination_technology asc";
					technologyPieChart = sessionRPT.createNativeQuery(defaultChartSites);

					if (firstChart.list().size() > 0) {
						chartJSNArr = chartRevenues(firstChart.list());
						model.addAttribute("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
					} else {
						model.addAttribute("msColumnChartObj", mapper.writeValueAsString(""));
					}

					// easy to throw the

					if (periodQuery.list().size() > 0) {

						defPeriodicData = getStackedChartData(periodQuery.list());
						model.addAttribute("StackedandLineRevenue", mapper.writeValueAsString(defPeriodicData));
					} else {
						model.addAttribute("StackedandLineRevenue", mapper.writeValueAsString(""));
					}

					model.addAttribute("listChartSites", mapper.writeValueAsString(technologyPieChart.list()));
					chartArray = new ArrayList<Float>(PieRevPerTech(technologyPieChart.list()));
					model.addAttribute("chartData", mapper.writeValueAsString(chartArray));

					model.addAttribute("pieRevenue", mapper.writeValueAsString(pieQuery.list()));

				}

			}

			catch (Exception e) {
				e.printStackTrace();
				logger.info("Error in creating session with the DataBase " + e.getMessage());
			}

			finally {

				if (sessionRPT != null && sessionRPT.isOpen()) {

					txRPT.commit();
					sessionRPT.close();
				}

			}

		}

		return "Reports/SiteRevenueReport";

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/GenerateRevenueReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateRevenueReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String area = request.getParameter("area");
		String technologyRegions = request.getParameter("technologyRegions");
		String period = request.getParameter("period");
		String max = request.getParameter("Max");
		String min = request.getParameter("Min");

		/*
		 * if(selectedValue != null) { queryRange =
		 * " and "+revenueOption+" >= DECODE("+val1+",null,0,"+val1+") AND "
		 * +revenueOption+" <= DECODE('"+val2+"','null',(select SUM("
		 * +revenueOption+") from prepaid_payg_revenue),"+val2+")"; } else queryRange =
		 * "";
		 */

		String siteTech = request.getParameter("selectedTech");
		String finalSiteTechList = "";
		String[] siteTechList = null;
		if (StringUtils.equalsIgnoreCase(siteTech, "") || StringUtils.equalsIgnoreCase(siteTech, null)) {
			finalSiteTechList = "";
		} else {
			String allSiteTech = siteTech.concat(",");
			// Split the string of technologies on comma to get each technology separately
			siteTechList = allSiteTech.split(",");
			StringBuilder allSiteTechs = new StringBuilder();

			// Append to each element '' and comma to satisfy the format of IN parameters
			for (int i = 0; i < siteTechList.length; i++) {

				allSiteTechs.append("'" + siteTechList[i] + "',");
			}

			finalSiteTechList = allSiteTechs.toString();
			finalSiteTechList = finalSiteTechList.substring(0, finalSiteTechList.length() - 1);
		}

		String nullSite = request.getParameter("nullSite");
		String sitesName = request.getParameter("allSitesName");

		String finalSitesNameList = "";
		String[] sitesNameList = null;

		if (StringUtils.equalsIgnoreCase(sitesName, "") || StringUtils.equalsIgnoreCase(sitesName, null)) {
			finalSitesNameList = "";
		} else {
			String allSitesName = sitesName.concat(",");

			// Split the string of sites on space-comma to get each site separately
			sitesNameList = allSitesName.split(" ,");

			StringBuilder allSitesNames = new StringBuilder();

			// Append to each element '' and comma to satisfy the format of IN parameters
			for (int i = 0; i < sitesNameList.length; i++) {

				allSitesNames.append("'" + sitesNameList[i] + "',");
			}

			finalSitesNameList = allSitesNames.toString();
			finalSitesNameList = finalSitesNameList.substring(0, finalSitesNameList.length() - 1);

		}

		String start_Date = request.getParameter("startDate");
		String end_Date = request.getParameter("endDate");
		Date startDate = null;
		Date endDate = null;
		Timestamp StartDate;
		Timestamp EndDate;
		int i = 0;
		Query gridQuery;
		String gridQueryString = "";
		List<SiteRevenueReport> SiteRevenueReportFinal = new ArrayList<SiteRevenueReport>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
			// return "redirect:/";
		} else {

			Session session = null;
			Transaction tx = null;

			try {

				startDate = formatter.parse(start_Date);
				StartDate = new Timestamp(startDate.getTime());

				endDate = formatter.parse(end_Date);
				EndDate = new Timestamp(endDate.getTime());

				session = RptDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {

					tx = session.beginTransaction();

					// if site is not selected
					if (finalSitesNameList == "") {

						// period is daily
						if (StringUtils.equalsIgnoreCase(period, "Daily")) {

							// no technology is selected
							if (finalSiteTechList == "") {

								// Select all sites name including NULL site
								if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

									// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {
										gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,t.revenue_date as \"startDate\" ,t.revenue_date as \"endDate\",t.voice_revenue  as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\" ,t.data_revenue as \"dataRevneue\" ,t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from (SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue , "
												+ "RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech, "
												+ " b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ "and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date "
												+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions
												+ "%' GROUP BY site_name, area_id, revenue_date "
												+ ") group by combination_tech, site_name, AREA_ID, revenue_date ) t where RANK = 1 "
												+ " UNION ("
												+ "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,t.revenue_date as \"startDate\" ,t.revenue_date as \"endDate\",t.voice_revenue  as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\" ,t.data_revenue as \"dataRevneue\" ,t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from (SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue , "
												+ "RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech, "
												+ " b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ "and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date "
												+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions
												+ "%' GROUP BY site_name, area_id, revenue_date "
												+ ") group by combination_tech, site_name, AREA_ID, revenue_date ) t where RANK = 1 ) order by 1,9 asc, 3 desc";
									}
									// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,t.revenue_date as \"startDate\" ,t.revenue_date as \"endDate\",t.voice_revenue  as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\" ,t.data_revenue as \"dataRevneue\" ,t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from (SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue , "
												+ "RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech, "
												+ " b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ "and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date "
												+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions
												+ "%' GROUP BY site_name, area_id, revenue_date "
												+ ") group by combination_tech, site_name, AREA_ID, revenue_date ) t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

									}
									// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,t.revenue_date as \"startDate\" ,t.revenue_date as \"endDate\",t.voice_revenue  as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\" ,t.data_revenue as \"dataRevneue\" ,t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from (SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue , "
												+ "RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech, "
												+ " b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ "and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date "
												+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions
												+ "%' GROUP BY site_name, area_id, revenue_date "
												+ ") group by combination_tech, site_name, AREA_ID, revenue_date ) t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

									}
									// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' "
												+ " order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date "
												+ " Union SELECT distinct 'No Combination Technology' as combination_tech,"
												+ " 'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
												+ "FROM PREPAID_PAYG_REVENUE WHERE  REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "GROUP BY  site_name, area_id, revenue_date"
												+ ") t order by t.site_name,t.combination_tech asc, t.revenue_date desc";

									}
								} // end if null sites

								// else select all not null sites name
								else {
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' "
												+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID, revenue_date )t order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

									} else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

									} else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

									} else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 "
												+ " UNION ("
												+ "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 ) order by 1,9 asc, 3 desc";

									}
								} // end else
							} // end if null tech

							// if there is a technology
							else if (finalSiteTechList != "") {

								// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {
									gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1  "
											+ "UNION ("
											+ "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1) order by 1,9 asc, 3 desc";

								}
								// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

								}
								// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

								}
								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ( "
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID, revenue_date )t order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

								}
							}

							gridQuery = session.createNativeQuery(gridQueryString);
							gridQuery.setParameter("param1", StartDate);
							gridQuery.setParameter("param2", EndDate);
							@SuppressWarnings("unchecked")
							List<SiteRevenueReport> SiteRevenueReport = (List<SiteRevenueReport>) ((SQLQuery) gridQuery)
									.addScalar("siteName").addScalar("areaId").addScalar("startDate")
									.addScalar("endDate").addScalar("voiceRevenue").addScalar("smsRevenue")
									.addScalar("dataRevneue").addScalar("vasRevenue")
									.addScalar("combination_technology")
									.setResultTransformer(Transformers.aliasToBean(SiteRevenueReport.class)).list();

							// System.out.println("****The SiteRevenueReport is:
							// "+mapper.writeValueAsString(SiteRevenueReport));

							rtn.put("revenueReportList", SiteRevenueReport);
						} // end daily

						// period is weekly
						if (StringUtils.equalsIgnoreCase(period, "Weekly")) {

							// System.out.println("the start date is "+ StartDate);
							double diff = (EndDate.getTime() - StartDate.getTime()) / 1000.0;
							diff /= (60 * 60 * 24 * 7);
							// System.out.println("the week difference is before "+ diff);
							diff = Math.abs(Math.ceil(diff));
							// System.out.println("the week difference is after "+ diff);
							Date weeklyDate;
							Timestamp weeklyEndDate = StartDate;
							Calendar cal = Calendar.getInstance();

							for (i = 0; i < diff; i++) {
								weeklyEndDate = StartDate;
								cal.setTime(weeklyEndDate);
								cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
								weeklyDate = cal.getTime();
								weeklyEndDate = new Timestamp(weeklyDate.getTime());

								// the weeklyEndDate is greater than set enddate
								if ((weeklyEndDate).compareTo(EndDate) > 0) {
									weeklyEndDate = EndDate;
								}

								// no technology is selected
								if (finalSiteTechList == "") {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
										if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											gridQueryString = "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
													+ StartDate + "' as \"startDate\" ,'" + weeklyEndDate
													+ "'  as \"endDate\",SUM(voice_revenue)  as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\" ,SUM(data_revenue) as \"dataRevneue\" ,SUM(vas_revenue) as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
													+ "select t.site_name site_name,t.area_id as area_id ,t.revenue_date as revenue_date ,t.voice_revenue as voice_revenue, t.sms_revenue as sms_revenue ,t.data_revenue as data_revenue ,t.vas_revenue as vas_revenue ,t.combination_tech  as combination_tech "
													+ "from (SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, "
													+ "sum(vas_revenue) as vas_revenue ,RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,  "
													+ " b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue  "
													+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ "and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
													+ technologyRegions
													+ "%'order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date  "
													+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
													+ "FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%null%' and AREA_NAME LIKE '%"
													+ area + "%' and REGION_NAME LIKE '%" + technologyRegions
													+ "%'  GROUP BY site_name, area_id, revenue_date "
													+ " ) group by combination_tech, site_name, AREA_ID, revenue_date ) t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc "
													+ ")GROUP BY combination_tech,site_name ,area_id " + " UNION ("
													+ "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
													+ StartDate + "' as \"startDate\" ,'" + weeklyEndDate
													+ "'  as \"endDate\",SUM(voice_revenue)  as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\" ,SUM(data_revenue) as \"dataRevneue\" ,SUM(vas_revenue) as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
													+ "select t.site_name site_name,t.area_id as area_id ,t.revenue_date as revenue_date ,t.voice_revenue as voice_revenue, t.sms_revenue as sms_revenue ,t.data_revenue as data_revenue ,t.vas_revenue as vas_revenue ,t.combination_tech  as combination_tech "
													+ "from (SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, "
													+ "sum(vas_revenue) as vas_revenue ,RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,  "
													+ " b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue  "
													+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ "and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
													+ technologyRegions
													+ "%'order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date  "
													+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
													+ "FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%null%' and AREA_NAME LIKE '%"
													+ area + "%' and REGION_NAME LIKE '%" + technologyRegions
													+ "%'  GROUP BY site_name, area_id, revenue_date "
													+ " ) group by combination_tech, site_name, AREA_ID, revenue_date ) t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc "
													+ ")GROUP BY combination_tech,site_name ,area_id) order by 1,9 asc";
										}
										// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, null)) {

											gridQueryString = "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
													+ StartDate + "' as \"startDate\" ,'" + weeklyEndDate
													+ "'  as \"endDate\",SUM(voice_revenue)  as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\" ,SUM(data_revenue) as \"dataRevneue\" ,SUM(vas_revenue) as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
													+ "select t.site_name site_name,t.area_id as area_id ,t.revenue_date as revenue_date ,t.voice_revenue as voice_revenue, t.sms_revenue as sms_revenue ,t.data_revenue as data_revenue ,t.vas_revenue as vas_revenue ,t.combination_tech  as combination_tech "
													+ "from (SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, "
													+ "sum(vas_revenue) as vas_revenue ,RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,  "
													+ " b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue  "
													+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ "and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
													+ technologyRegions
													+ "%'order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date  "
													+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
													+ "FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%null%' and AREA_NAME LIKE '%"
													+ area + "%' and REGION_NAME LIKE '%" + technologyRegions
													+ "%'  GROUP BY site_name, area_id, revenue_date "
													+ " ) group by combination_tech, site_name, AREA_ID, revenue_date ) t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc "
													+ ")GROUP BY combination_tech,site_name ,area_id";

										}
										// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											gridQueryString = "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
													+ StartDate + "' as \"startDate\" ,'" + weeklyEndDate
													+ "'  as \"endDate\",SUM(voice_revenue)  as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\" ,SUM(data_revenue) as \"dataRevneue\" ,SUM(vas_revenue) as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
													+ "select t.site_name site_name,t.area_id as area_id ,t.revenue_date as revenue_date ,t.voice_revenue as voice_revenue, t.sms_revenue as sms_revenue ,t.data_revenue as data_revenue ,t.vas_revenue as vas_revenue ,t.combination_tech  as combination_tech "
													+ "from (SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, "
													+ "sum(vas_revenue) as vas_revenue ,RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,  "
													+ " b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue  "
													+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ "and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
													+ technologyRegions
													+ "%'order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date  "
													+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
													+ "FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%null%' and AREA_NAME LIKE '%"
													+ area + "%' and REGION_NAME LIKE '%" + technologyRegions
													+ "%'  GROUP BY site_name, area_id, revenue_date "
													+ " ) group by combination_tech, site_name, AREA_ID, revenue_date ) t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc "
													+ ")GROUP BY combination_tech,site_name ,area_id";

										}
										// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, null)) {

											gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
													+ StartDate + "' as \"startDate\",'" + weeklyEndDate
													+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ( "
													+ "select t.site_name as site_name,t.area_id as area_id ,t.voice_revenue as voice_revenue,t.sms_revenue as sms_revenue,"
													+ "t.data_revenue as data_revenue,t.vas_revenue as vas_revenue,t.combination_tech as combination_tech from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,  "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from(  "
													+ "SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
													+ "b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2  and b.AREA_NAME LIKE '%"
													+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
													+ "order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date  "
													+ " Union SELECT distinct 'No Combination Technology' as combination_tech, "
													+ "'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
													+ "FROM PREPAID_PAYG_REVENUE WHERE  REVENUE_DATE >=:param1 and REVENUE_DATE <= :param2  "
													+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
													+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
													+ "GROUP BY  site_name, area_id, revenue_date "
													+ ") t order by t.site_name,t.combination_tech asc, t.revenue_date desc "
													+ ")GROUP BY combination_tech,site_name ,area_id order by site_name,combination_tech asc";

										}
									} // end if null site is checked

									// else select all not null sites name
									else {
										if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, null)) {

											gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
													+ StartDate + "' as \"startDate\",'" + weeklyEndDate
													+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
													+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue\r\n"
													+ ",t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech\r\n"
													+ " from( SELECT combination_tech,revenue_date, site_name, AREA_ID ,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date"
													+ " ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
													+ technologyRegions + "%' "
													+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID,revenue_date)t order by t.site_name,t.combination_tech asc )"
													+ "GROUP BY site_name ,area_id,combination_tech";

										} else if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, null)) {

											gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
													+ StartDate + "' as \"startDate\",'" + weeklyEndDate
													+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
													+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
													+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
													+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2  and b.AREA_NAME LIKE '%"
													+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
													+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
													+ "GROUP BY site_name ,area_id,combination_tech ";

										} else if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
													+ StartDate + "' as \"startDate\",'" + weeklyEndDate
													+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
													+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
													+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
													+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2  and b.AREA_NAME LIKE '%"
													+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
													+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
													+ "GROUP BY site_name ,area_id,combination_tech ";

										} else if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
													+ StartDate + "' as \"startDate\",'" + weeklyEndDate
													+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
													+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
													+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
													+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2  and b.AREA_NAME LIKE '%"
													+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
													+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
													+ "GROUP BY site_name ,area_id,combination_tech " + "UNION ("
													+ "select site_name as \"siteName\",area_id as \"areaId\",'"
													+ StartDate + "' as \"startDate\",'" + weeklyEndDate
													+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
													+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
													+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
													+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2  and b.AREA_NAME LIKE '%"
													+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
													+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
													+ "GROUP BY site_name ,area_id,combination_tech) order by 1,9 asc ";
										}
									} // end else

								}
								// if there is a technology
								else if (finalSiteTechList != "") {

									// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech  " + " UNION ("
												+ "select site_name as \"siteName\",area_id as \"areaId\",'" + StartDate
												+ "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech ) order by 1,9 asc";

									}
									// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech order by site_name ,combination_tech asc ";

									}
									// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech order by site_name ,combination_tech asc ";

									}
									// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue\r\n"
												+ ",t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech\r\n"
												+ " from( SELECT combination_tech,revenue_date, site_name, AREA_ID ,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date"
												+ " ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID,revenue_date)t order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech";

									}
								}

								gridQuery = session.createNativeQuery(gridQueryString);
								gridQuery.setParameter("param1", StartDate);
								gridQuery.setParameter("param2", weeklyEndDate);
								@SuppressWarnings("unchecked")
								List<SiteRevenueReport> SiteRevenueReport = (List<SiteRevenueReport>) ((SQLQuery) gridQuery)
										.addScalar("siteName").addScalar("areaId").addScalar("startDate")
										.addScalar("endDate").addScalar("voiceRevenue").addScalar("smsRevenue")
										.addScalar("dataRevneue").addScalar("vasRevenue")
										.addScalar("combination_technology")
										.setResultTransformer(Transformers.aliasToBean(SiteRevenueReport.class)).list();

								// System.out.println("****The SiteRevenueReport is:
								// "+mapper.writeValueAsString(SiteRevenueReport));
								SiteRevenueReportFinal.addAll(SiteRevenueReport);

								StartDate = weeklyEndDate;
								cal.setTime(StartDate);
								cal.add(Calendar.DAY_OF_MONTH, 1); // 1 is the days you want to add or subtract
								weeklyDate = cal.getTime();
								StartDate = new Timestamp(weeklyDate.getTime());
								// System.out.println("after start date "+StartDate);

							}
							rtn.put("revenueReportList", SiteRevenueReportFinal);
						} // end if of weekly

						// start of monthly
						if (StringUtils.equalsIgnoreCase(period, "Monthly")) {
							// System.out.println("site is not selectedd and it is Monthly");

							Calendar m = Calendar.getInstance();
							m.setTime(StartDate);
							int nMonth1 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
							m.setTime(EndDate);
							int nMonth2 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
							int diff = Math.abs(nMonth2 - nMonth1);

							Timestamp month = StartDate;

							for (i = 0; i <= diff; i++) {

								String startmonth = formatter.format(month);
								Date monthDate = formatter.parse(startmonth);
								Timestamp monthofDate = new Timestamp(monthDate.getTime());

								Calendar calendar = Calendar.getInstance();
								calendar.setTime(monthofDate);
								calendar.add(Calendar.MONTH, 1);
								calendar.set(Calendar.DAY_OF_MONTH, 1);
								calendar.add(Calendar.DATE, -1);
								Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

								String endMonth = formatter.format(lastDayOfMonth);
								Date endOfDate = formatter.parse(endMonth);
								Timestamp endOfMonth = new Timestamp(endOfDate.getTime());

								// the endOfMonth is greater than set enddate
								if ((endOfMonth).compareTo(EndDate) > 0) {
									endOfMonth = EndDate;
								}

								// no technology is selected
								if (finalSiteTechList == "") {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
										if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											gridQueryString = "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
													+ monthofDate + "' as \"startDate\" ,'" + endOfMonth
													+ "'  as \"endDate\",SUM(voice_revenue)  as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\" ,SUM(data_revenue) as \"dataRevneue\" ,SUM(vas_revenue) as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
													+ "select t.site_name site_name,t.area_id as area_id ,t.revenue_date as revenue_date ,t.voice_revenue as voice_revenue, t.sms_revenue as sms_revenue ,t.data_revenue as data_revenue ,t.vas_revenue as vas_revenue ,t.combination_tech  as combination_tech "
													+ "from (SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, "
													+ "sum(vas_revenue) as vas_revenue ,RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,  "
													+ " b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue  "
													+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ "and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
													+ technologyRegions
													+ "%'order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date  "
													+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
													+ "FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%null%' and AREA_NAME LIKE '%"
													+ area + "%' and REGION_NAME LIKE '%" + technologyRegions
													+ "%'  GROUP BY site_name, area_id, revenue_date "
													+ " ) group by combination_tech, site_name, AREA_ID, revenue_date ) t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc "
													+ ")GROUP BY combination_tech,site_name ,area_id " + " UNION ("
													+ "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
													+ monthofDate + "' as \"startDate\" ,'" + endOfMonth
													+ "'  as \"endDate\",SUM(voice_revenue)  as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\" ,SUM(data_revenue) as \"dataRevneue\" ,SUM(vas_revenue) as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
													+ "select t.site_name site_name,t.area_id as area_id ,t.revenue_date as revenue_date ,t.voice_revenue as voice_revenue, t.sms_revenue as sms_revenue ,t.data_revenue as data_revenue ,t.vas_revenue as vas_revenue ,t.combination_tech  as combination_tech "
													+ "from (SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, "
													+ "sum(vas_revenue) as vas_revenue ,RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,  "
													+ " b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue  "
													+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ "and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
													+ technologyRegions
													+ "%'order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date  "
													+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
													+ "FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%null%' and AREA_NAME LIKE '%"
													+ area + "%' and REGION_NAME LIKE '%" + technologyRegions
													+ "%'  GROUP BY site_name, area_id, revenue_date "
													+ " ) group by combination_tech, site_name, AREA_ID, revenue_date ) t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc "
													+ ")GROUP BY combination_tech,site_name ,area_id) order by 1,9 asc";
										}
										// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, null)) {

											gridQueryString = "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
													+ monthofDate + "' as \"startDate\" ,'" + endOfMonth
													+ "'  as \"endDate\",SUM(voice_revenue)  as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\" ,SUM(data_revenue) as \"dataRevneue\" ,SUM(vas_revenue) as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
													+ "select t.site_name site_name,t.area_id as area_id ,t.revenue_date as revenue_date ,t.voice_revenue as voice_revenue, t.sms_revenue as sms_revenue ,t.data_revenue as data_revenue ,t.vas_revenue as vas_revenue ,t.combination_tech  as combination_tech "
													+ "from (SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, "
													+ "sum(vas_revenue) as vas_revenue ,RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,  "
													+ " b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue  "
													+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ "and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
													+ technologyRegions
													+ "%'order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date  "
													+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
													+ "FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%null%' and AREA_NAME LIKE '%"
													+ area + "%' and REGION_NAME LIKE '%" + technologyRegions
													+ "%'  GROUP BY site_name, area_id, revenue_date "
													+ " ) group by combination_tech, site_name, AREA_ID, revenue_date ) t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc "
													+ ")GROUP BY combination_tech,site_name ,area_id";

										}
										// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											gridQueryString = "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
													+ monthofDate + "' as \"startDate\" ,'" + endOfMonth
													+ "'  as \"endDate\",SUM(voice_revenue)  as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\" ,SUM(data_revenue) as \"dataRevneue\" ,SUM(vas_revenue) as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
													+ "select t.site_name site_name,t.area_id as area_id ,t.revenue_date as revenue_date ,t.voice_revenue as voice_revenue, t.sms_revenue as sms_revenue ,t.data_revenue as data_revenue ,t.vas_revenue as vas_revenue ,t.combination_tech  as combination_tech "
													+ "from (SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, "
													+ "sum(vas_revenue) as vas_revenue ,RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,  "
													+ " b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue  "
													+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ "and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
													+ technologyRegions
													+ "%'order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date  "
													+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
													+ "FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%null%' and AREA_NAME LIKE '%"
													+ area + "%' and REGION_NAME LIKE '%" + technologyRegions
													+ "%'  GROUP BY site_name, area_id, revenue_date "
													+ " ) group by combination_tech, site_name, AREA_ID, revenue_date ) t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc "
													+ ")GROUP BY combination_tech,site_name ,area_id";

										}
										// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, null)) {

											gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
													+ monthofDate + "' as \"startDate\",'" + endOfMonth
													+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ( "
													+ "select t.site_name as site_name,t.area_id as area_id ,t.voice_revenue as voice_revenue,t.sms_revenue as sms_revenue,"
													+ "t.data_revenue as data_revenue,t.vas_revenue as vas_revenue,t.combination_tech as combination_tech from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,  "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from(  "
													+ "SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
													+ "b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2  and b.AREA_NAME LIKE '%"
													+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
													+ "order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date  "
													+ " Union SELECT distinct 'No Combination Technology' as combination_tech, "
													+ "'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
													+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
													+ "FROM PREPAID_PAYG_REVENUE WHERE  REVENUE_DATE >=:param1 and REVENUE_DATE <= :param2  "
													+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
													+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
													+ "GROUP BY  site_name, area_id, revenue_date "
													+ ") t order by t.site_name,t.combination_tech asc, t.revenue_date desc "
													+ ")GROUP BY combination_tech,site_name ,area_id order by site_name,combination_tech asc";

										}
									}

									// else select all not null sites name
									else {
										if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, null)) {

											gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
													+ monthofDate + "' as \"startDate\",'" + endOfMonth
													+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
													+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue\r\n"
													+ ",t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech\r\n"
													+ " from( SELECT combination_tech,revenue_date, site_name, AREA_ID ,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date"
													+ " ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
													+ technologyRegions + "%' "
													+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID,revenue_date)t order by t.site_name,t.combination_tech asc )"
													+ "GROUP BY site_name ,area_id,combination_tech";

										} else if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, null)) {

											gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
													+ monthofDate + "' as \"startDate\",'" + endOfMonth
													+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
													+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
													+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
													+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2  and b.AREA_NAME LIKE '%"
													+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
													+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
													+ "GROUP BY site_name ,area_id,combination_tech ";

										} else if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
													+ monthofDate + "' as \"startDate\",'" + endOfMonth
													+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
													+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
													+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
													+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2  and b.AREA_NAME LIKE '%"
													+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
													+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
													+ "GROUP BY site_name ,area_id,combination_tech ";

										} else if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
													+ monthofDate + "' as \"startDate\",'" + endOfMonth
													+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
													+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
													+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
													+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2  and b.AREA_NAME LIKE '%"
													+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
													+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
													+ "GROUP BY site_name ,area_id,combination_tech " + "UNION ("
													+ "select site_name as \"siteName\",area_id as \"areaId\",'"
													+ monthofDate + "' as \"startDate\",'" + endOfMonth
													+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
													+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
													+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
													+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2  and b.AREA_NAME LIKE '%"
													+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
													+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
													+ "GROUP BY site_name ,area_id,combination_tech) order by 1,9 asc ";
										}
									} // end else

								}
								// if there is a technology
								else if (finalSiteTechList != "") {

									// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {
										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech  " + " UNION ("
												+ "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech ) order by 1,9 asc";

									}
									// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech order by site_name ,combination_tech asc";

									}
									// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech order by site_name ,combination_tech asc ";

									}
									// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue\r\n"
												+ ",t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech\r\n"
												+ " from( SELECT combination_tech,revenue_date, site_name, AREA_ID ,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date"
												+ " ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID,revenue_date)t order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech";

									}
								}

								gridQuery = session.createNativeQuery(gridQueryString);
								gridQuery.setParameter("param1", monthofDate);
								gridQuery.setParameter("param2", endOfMonth);
								@SuppressWarnings("unchecked")
								List<SiteRevenueReport> SiteRevenueReport = (List<SiteRevenueReport>) ((SQLQuery) gridQuery)
										.addScalar("siteName").addScalar("areaId").addScalar("startDate")
										.addScalar("endDate").addScalar("voiceRevenue").addScalar("smsRevenue")
										.addScalar("dataRevneue").addScalar("vasRevenue")
										.addScalar("combination_technology")
										.setResultTransformer(Transformers.aliasToBean(SiteRevenueReport.class)).list();

								// System.out.println("****The SiteRevenueReport is:
								// "+mapper.writeValueAsString(SiteRevenueReport));
								SiteRevenueReportFinal.addAll(SiteRevenueReport);

								calendar.setTime(endOfMonth);
								calendar.add(Calendar.DATE, +1);
								Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
								month = NextDayOfMonth;

							}
							rtn.put("revenueReportList", SiteRevenueReportFinal);

						} // end if of monthly

						// start of acc

						if (StringUtils.equalsIgnoreCase(period, "Accu")) {

							// System.out.println("/*/*period is acc and site is not selected");

							// no technology is selected
							if (finalSiteTechList == "") {

								// Select all sites name including NULL site
								if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

									// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\",voice_revenue as \"voiceRevenue\",sms_revenue as \"smsRevenue\" ,data_revenue as \"dataRevneue\" ,vas_revenue as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
												+ " SELECT combination_tech, site_name, AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue , "
												+ "RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( SELECT combination_tech, site_name, AREA_ID , revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ "SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech, b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue, "
												+ " b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID ,revenue_date "
												+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions
												+ "%'  GROUP BY site_name, area_id,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') )"
												+ "group by combination_tech, site_name, AREA_ID ) t where RANK = 1 "
												+ " UNION ("
												+ "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\",voice_revenue as \"voiceRevenue\",sms_revenue as \"smsRevenue\" ,data_revenue as \"dataRevneue\" ,vas_revenue as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
												+ " SELECT combination_tech, site_name, AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue , "
												+ "RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( SELECT combination_tech, site_name, AREA_ID , revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ "SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech, b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue, "
												+ " b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID ,revenue_date "
												+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions
												+ "%'  GROUP BY site_name, area_id,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') )"
												+ "group by combination_tech, site_name, AREA_ID ) t where RANK = 1) order by 1,9 asc";
									}
									// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\",voice_revenue as \"voiceRevenue\",sms_revenue as \"smsRevenue\" ,data_revenue as \"dataRevneue\" ,vas_revenue as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
												+ " SELECT combination_tech, site_name, AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue , "
												+ "RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( SELECT combination_tech, site_name, AREA_ID , revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ "SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech, b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue, "
												+ " b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID ,revenue_date "
												+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions
												+ "%'  GROUP BY site_name, area_id,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') )"
												+ "group by combination_tech, site_name, AREA_ID ) t where RANK = 1 order by t.site_name,t.combination_tech asc";

									}
									// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\",voice_revenue as \"voiceRevenue\",sms_revenue as \"smsRevenue\" ,data_revenue as \"dataRevneue\" ,vas_revenue as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
												+ " SELECT combination_tech, site_name, AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue , "
												+ "RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( SELECT combination_tech, site_name, AREA_ID , revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ "SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech, b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue, "
												+ " b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID ,revenue_date "
												+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions
												+ "%'  GROUP BY site_name, area_id,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') )"
												+ "group by combination_tech, site_name, AREA_ID ) t where RANK = 1 order by t.site_name,t.combination_tech asc";

									}
									// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + EndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ( "
												+ "select t.site_name as site_name,t.area_id as area_id ,t.voice_revenue as voice_revenue,t.sms_revenue as sms_revenue,"
												+ "t.data_revenue as data_revenue,t.vas_revenue as vas_revenue,t.combination_tech as combination_tech from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,  "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from(  "
												+ "SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ "b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2  and b.AREA_NAME LIKE '%"
												+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID, revenue_date  "
												+ " Union SELECT distinct 'No Combination Technology' as combination_tech, "
												+ "'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue "
												+ "FROM PREPAID_PAYG_REVENUE WHERE  REVENUE_DATE >=:param1 and REVENUE_DATE <= :param2  "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "GROUP BY  site_name, area_id, revenue_date "
												+ ") t order by t.site_name,t.combination_tech asc, t.revenue_date desc "
												+ ")GROUP BY combination_tech,site_name ,area_id order by site_name,combination_tech asc";

									}
								}

								// else select all not null sites name
								else {
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + EndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue "
												+ ",t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech,revenue_date, site_name, AREA_ID ,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date"
												+ " ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' "
												+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID,revenue_date)t order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech";

									} else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
												+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
												+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.AREA_NAME LIKE '%"
												+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
												+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

									} else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
												+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK FROM( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
												+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.AREA_NAME LIKE '%"
												+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
												+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

									} else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
												+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
												+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.AREA_NAME LIKE '%"
												+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
												+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1   "
												+ " UNION ("
												+ "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
												+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK FROM( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
												+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.AREA_NAME LIKE '%"
												+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
												+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1) order by 1,9 asc, 3 desc";

									}
								} // end else
							}
							// if there is a technology
							else if (finalSiteTechList != "") {

								// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) DESC) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 "
											+ " UNION ("
											+ "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ASC) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 ) order by 1,9 asc";

								}
								// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

								}
								// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ASC) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

								}
								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
											+ StartDate + "' as \"startDate\",'" + EndDate
											+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
											+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue\r\n"
											+ ",t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech\r\n"
											+ " from( SELECT combination_tech,revenue_date, site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date"
											+ " ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID,revenue_date)t order by t.site_name,t.combination_tech asc )"
											+ "GROUP BY site_name ,area_id,combination_tech";

								}
							}

							gridQuery = session.createNativeQuery(gridQueryString);
							gridQuery.setParameter("param1", StartDate);
							gridQuery.setParameter("param2", EndDate);
							@SuppressWarnings("unchecked")
							List<SiteRevenueReport> SiteRevenueReport = (List<SiteRevenueReport>) ((SQLQuery) gridQuery)
									.addScalar("siteName").addScalar("areaId").addScalar("startDate")
									.addScalar("endDate").addScalar("voiceRevenue").addScalar("smsRevenue")
									.addScalar("dataRevneue").addScalar("vasRevenue")
									.addScalar("combination_technology")
									.setResultTransformer(Transformers.aliasToBean(SiteRevenueReport.class)).list();

							// System.out.println("****The SiteRevenueReport is:
							// "+mapper.writeValueAsString(SiteRevenueReport));

							rtn.put("revenueReportList", SiteRevenueReport);
						} /// end of accumulated

					} // end if site is null

					// if site is selected
					else {

						// period method is daily
						if (StringUtils.equalsIgnoreCase(period, "Daily")) {

							// System.out.println("/*/*Here with site");

							// no technology is selected
							if (finalSiteTechList == "") {

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 "
											+ " UNION ("
											+ "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 ) order by 1,9 asc, 3 desc";

								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

								}
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList + " ) "
											+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID, revenue_date )t order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

								}

							}
							// if there is a technology
							else if (finalSiteTechList != "") {

								// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList
											+ " ) and a.COMBINATION_TECHNOLOGY IN ( " + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1  "
											+ "UNION ("
											+ "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList
											+ " ) and a.COMBINATION_TECHNOLOGY IN ( " + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1) order by 1,9 asc, 3 desc";

								}
								// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList
											+ " ) and a.COMBINATION_TECHNOLOGY IN ( " + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

								}
								// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) , RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList
											+ " ) and a.COMBINATION_TECHNOLOGY IN ( " + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

								}
								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									gridQueryString = "select t.site_name as  \"siteName\",t.area_id as \"areaId\",t.revenue_date as \"startDate\",t.revenue_date as \"endDate\",t.voice_revenue as \"voiceRevenue\",t.sms_revenue as \"smsRevenue\",t.data_revenue as \"dataRevneue\",t.vas_revenue as \"vasRevenue\",t.combination_tech as \"combination_technology\" from ( SELECT combination_tech, site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList
											+ " ) and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
											+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID, revenue_date )t order by t.site_name,t.combination_tech asc, t.revenue_date desc ";

								}
							}

							gridQuery = session.createNativeQuery(gridQueryString);
							gridQuery.setParameter("param1", StartDate);
							gridQuery.setParameter("param2", EndDate);
							@SuppressWarnings("unchecked")
							List<SiteRevenueReport> SiteRevenueReport = (List<SiteRevenueReport>) ((SQLQuery) gridQuery)
									.addScalar("siteName").addScalar("areaId").addScalar("startDate")
									.addScalar("endDate").addScalar("voiceRevenue").addScalar("smsRevenue")
									.addScalar("dataRevneue").addScalar("vasRevenue")
									.addScalar("combination_technology")
									.setResultTransformer(Transformers.aliasToBean(SiteRevenueReport.class)).list();

							// System.out.println("****The SiteRevenueReport is:
							// "+mapper.writeValueAsString(SiteRevenueReport));

							rtn.put("revenueReportList", SiteRevenueReport);

						} // DAILY ENDED

						// WEEKLY STARTED WITH SITE
						if (StringUtils.equalsIgnoreCase(period, "Weekly")) {

							double diff = (EndDate.getTime() - StartDate.getTime()) / 1000.0;
							diff /= (60 * 60 * 24 * 7);
							diff = Math.abs(Math.ceil(diff));
							Date weeklyDate;
							Timestamp weeklyEndDate = StartDate;
							Calendar cal = Calendar.getInstance();

							for (i = 0; i < diff; i++) {
								weeklyEndDate = StartDate;
								cal.setTime(weeklyEndDate);
								cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
								weeklyDate = cal.getTime();
								weeklyEndDate = new Timestamp(weeklyDate.getTime());

								if ((weeklyEndDate).compareTo(EndDate) > 0) {
									weeklyEndDate = EndDate;
								}

								// Tech is null
								if (finalSiteTechList == "") {

									// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue\r\n"
												+ ",t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech\r\n"
												+ " from( SELECT combination_tech,revenue_date, site_name, AREA_ID ,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date"
												+ " ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList
												+ " ) "
												+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID,revenue_date)t order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech";

									}

									// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech order by site_name ,combination_tech asc ";

									}
									// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech order by site_name ,combination_tech asc ";

									}

									// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech " + " UNION ("
												+ "select site_name as \"siteName\",area_id as \"areaId\",'" + StartDate
												+ "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech ) order by 1,9 asc";
									}
								} // END NULL TECH

								// if there is a technology
								else if (finalSiteTechList != "") {
									// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue\r\n"
												+ ",t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech\r\n"
												+ " from( SELECT combination_tech,revenue_date, site_name, AREA_ID ,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date"
												+ " ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ " ) and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " )"
												+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID,revenue_date)t order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech";

									}
									// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech order by site_name ,combination_tech asc";

									}
									// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech order by site_name ,combination_tech asc";

									}

									// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {
										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ StartDate + "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech " + " UNION ("
												+ "select site_name as \"siteName\",area_id as \"areaId\",'" + StartDate
												+ "' as \"startDate\",'" + weeklyEndDate
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech ) order by 1,9 asc";

									}
								} // end selected tech

								gridQuery = session.createNativeQuery(gridQueryString);
								gridQuery.setParameter("param1", StartDate);
								gridQuery.setParameter("param2", weeklyEndDate);
								List<SiteRevenueReport> SiteRevenueReport = (List<SiteRevenueReport>) ((SQLQuery) gridQuery)
										.addScalar("siteName").addScalar("areaId").addScalar("startDate")
										.addScalar("endDate").addScalar("voiceRevenue").addScalar("smsRevenue")
										.addScalar("dataRevneue").addScalar("vasRevenue")
										.addScalar("combination_technology")
										.setResultTransformer(Transformers.aliasToBean(SiteRevenueReport.class)).list();

								SiteRevenueReportFinal.addAll(SiteRevenueReport);

								StartDate = weeklyEndDate;
								cal.setTime(StartDate);
								cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
								weeklyDate = cal.getTime();
								StartDate = new Timestamp(weeklyDate.getTime());

							}
							rtn.put("revenueReportList", SiteRevenueReportFinal);
						} // end weekly period

						// start of monthly
						if (StringUtils.equalsIgnoreCase(period, "Monthly")) {

							Calendar m = Calendar.getInstance();
							m.setTime(StartDate);
							int nMonth1 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
							m.setTime(EndDate);
							int nMonth2 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
							int diff = Math.abs(nMonth2 - nMonth1);
							Timestamp month = StartDate;

							for (i = 0; i <= diff; i++) {

								String startmonth = formatter.format(month);
								Date monthDate = formatter.parse(startmonth);
								Timestamp monthofDate = new Timestamp(monthDate.getTime());

								Calendar calendar = Calendar.getInstance();
								calendar.setTime(monthofDate);
								calendar.add(Calendar.MONTH, 1);
								calendar.set(Calendar.DAY_OF_MONTH, 1);
								calendar.add(Calendar.DATE, -1);
								Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

								String endMonth = formatter.format(lastDayOfMonth);
								Date endOfDate = formatter.parse(endMonth);
								Timestamp endOfMonth = new Timestamp(endOfDate.getTime());

								// the endOfMonth is greater than set enddate
								if ((endOfMonth).compareTo(EndDate) > 0) {
									// System.out.println("the endOfMonth is greater than enddate");
									endOfMonth = EndDate;
								}

								// no technology is selected
								if (finalSiteTechList == "") {

									// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue\r\n"
												+ ",t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech\r\n"
												+ " from( SELECT combination_tech,revenue_date, site_name, AREA_ID ,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date"
												+ " ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList
												+ " ) "
												+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID,revenue_date)t order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech";

									}

									// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech order by site_name ,combination_tech asc ";

									}
									// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech order by site_name ,combination_tech asc ";
									}
									// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech " + " UNION ("
												+ "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech ) order by 1,9 asc";
									}
								} // end of null tech

								// tech is selected
								else if (finalSiteTechList != "") {

									// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue\r\n"
												+ ",t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech\r\n"
												+ " from( SELECT combination_tech,revenue_date, site_name, AREA_ID ,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date"
												+ " ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ " ) and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " )"
												+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID,revenue_date)t order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech";

									}
									// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech order by site_name ,combination_tech asc";

									}

									// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech order by site_name ,combination_tech asc";

									}
									// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech " + " UNION ("
												+ "select site_name as \"siteName\",area_id as \"areaId\",'"
												+ monthofDate + "' as \"startDate\",'" + endOfMonth
												+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
												+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue, t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech "
												+ " from( SELECT combination_tech, site_name, AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ,"
												+ " RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
												+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
												+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%'  "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date )t where RANK = 1 order by t.site_name,t.combination_tech asc )"
												+ "GROUP BY site_name ,area_id,combination_tech ) order by 1,9 asc";

									}

								} // end tech selected

								gridQuery = session.createNativeQuery(gridQueryString);
								gridQuery.setParameter("param1", monthofDate);
								gridQuery.setParameter("param2", endOfMonth);
								@SuppressWarnings("unchecked")
								List<SiteRevenueReport> SiteRevenueReport = (List<SiteRevenueReport>) ((SQLQuery) gridQuery)
										.addScalar("siteName").addScalar("areaId").addScalar("startDate")
										.addScalar("endDate").addScalar("voiceRevenue").addScalar("smsRevenue")
										.addScalar("dataRevneue").addScalar("vasRevenue")
										.addScalar("combination_technology")
										.setResultTransformer(Transformers.aliasToBean(SiteRevenueReport.class)).list();

								// System.out.println("****The SiteRevenueReport is:
								// "+mapper.writeValueAsString(SiteRevenueReport));
								SiteRevenueReportFinal.addAll(SiteRevenueReport);

								calendar.setTime(endOfMonth);
								calendar.add(Calendar.DATE, +1);
								Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
								month = NextDayOfMonth;

							}
							rtn.put("revenueReportList", SiteRevenueReportFinal);

						} // end of monthly period

						// start of acc
						if (StringUtils.equalsIgnoreCase(period, "Accu")) {

							// System.out.println("/*/*period is acc and site is selected");

							// no technology is selected
							if (finalSiteTechList == "") {

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 "
											+ " UNION ("
											+ "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ASC) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1) order by 1,9 asc";
								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ASC) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

								}
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
											+ StartDate + "' as \"startDate\",'" + EndDate
											+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
											+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue\r\n"
											+ ",t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech\r\n"
											+ " from( SELECT combination_tech,revenue_date, site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date"
											+ " ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList + " ) "
											+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID,revenue_date)t order by t.site_name,t.combination_tech asc )"
											+ "GROUP BY site_name ,area_id,combination_tech";

								}

							}
							// if there is a technology
							else if (finalSiteTechList != "") {

								// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {
									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 "
											+ " UNION ("
											+ "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ASC) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1) order by 1,9 asc";

								}
								// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

								}
								// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) ASC) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

								}
								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									gridQueryString = "select site_name as \"siteName\",area_id as \"areaId\",'"
											+ StartDate + "' as \"startDate\",'" + EndDate
											+ "' as \"endDate\",SUM(voice_revenue) as \"voiceRevenue\",SUM(sms_revenue) as \"smsRevenue\",SUM(data_revenue) as \"dataRevneue\",SUM(vas_revenue) as \"vasRevenue\",combination_tech as \"combination_technology\" from ("
											+ "select t.site_name AS site_name ,t.area_id AS area_id ,t.voice_revenue AS voice_revenue,t.sms_revenue AS sms_revenue\r\n"
											+ ",t.data_revenue AS data_revenue,t.vas_revenue AS vas_revenue ,t.combination_tech AS combination_tech\r\n"
											+ " from( SELECT combination_tech,revenue_date, site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date"
											+ " ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and b.site_name IN (" + finalSitesNameList
											+ " )  and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
											+ "  order by b.site_name)group by combination_tech, site_name, AREA_ID,revenue_date)t order by t.site_name,t.combination_tech asc )"
											+ "GROUP BY site_name ,area_id,combination_tech";

								}
							}

							gridQuery = session.createNativeQuery(gridQueryString);
							gridQuery.setParameter("param1", StartDate);
							gridQuery.setParameter("param2", EndDate);
							@SuppressWarnings("unchecked")
							List<SiteRevenueReport> SiteRevenueReport = (List<SiteRevenueReport>) ((SQLQuery) gridQuery)
									.addScalar("siteName").addScalar("areaId").addScalar("startDate")
									.addScalar("endDate").addScalar("voiceRevenue").addScalar("smsRevenue")
									.addScalar("dataRevneue").addScalar("vasRevenue")
									.addScalar("combination_technology")
									.setResultTransformer(Transformers.aliasToBean(SiteRevenueReport.class)).list();

							// System.out.println("****The SiteRevenueReport is:
							// "+mapper.writeValueAsString(SiteRevenueReport));

							rtn.put("revenueReportList", SiteRevenueReport);
						} /// end of accumulated

					} // end selected sites

					// period is null with maximum - minimum
					if (StringUtils.equalsIgnoreCase(period, "null")) {
						// Maximum is checked
						if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, null)) {

							// technology is selected
							if (finalSiteTechList != "") {

								// if site is selected
								if (finalSitesNameList != "") {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

								}
								// site is not selected
								else {
									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

								}

							}
							// if tech is not selected
							else {
								// if site is selected
								if (finalSitesNameList != "") {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

								}
								// site is not selected
								else {
									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										gridQueryString = "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\",voice_revenue as \"voiceRevenue\",sms_revenue as \"smsRevenue\" ,data_revenue as \"dataRevneue\" ,vas_revenue as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
												+ " SELECT combination_tech, site_name, AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue , "
												+ "RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( SELECT combination_tech, site_name, AREA_ID , revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ "SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech, b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue, "
												+ " b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID ,revenue_date "
												+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions
												+ "%'  GROUP BY site_name, area_id,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') )"
												+ "group by combination_tech, site_name, AREA_ID ) t where RANK = 1 order by t.site_name,t.combination_tech asc";

									}
									// Select all not null sites
									else {

										gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
												+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
												+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.AREA_NAME LIKE '%"
												+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
												+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

									}
								}
							}
						}

						// Minimum is checked
						if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, "Min")) {

							// if tech is selected
							if (finalSiteTechList != "") {

								// if site is selected
								if (finalSitesNameList != "") {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

								}
								// site is not selected
								else {
									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

								}

							} // end if tech is selected

							// tech is not selected
							else {

								// site is selected
								if (finalSitesNameList != "") {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

								}
								// site is not selected
								else {
									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										gridQueryString = "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\",voice_revenue as \"voiceRevenue\",sms_revenue as \"smsRevenue\" ,data_revenue as \"dataRevneue\" ,vas_revenue as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
												+ " SELECT combination_tech, site_name, AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue , "
												+ "RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( SELECT combination_tech, site_name, AREA_ID , revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ "SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech, b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue, "
												+ " b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID ,revenue_date "
												+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions
												+ "%'  GROUP BY site_name, area_id,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') )"
												+ "group by combination_tech, site_name, AREA_ID ) t where RANK = 1 order by t.site_name,t.combination_tech asc";

									}
									// Select all not null sites
									else {

										gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
												+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK FROM( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
												+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.AREA_NAME LIKE '%"
												+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
												+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 order by t.site_name,t.combination_tech asc ";

									}
								}
							}
						}
						// Maximum and minimum are checked
						if (StringUtils.equalsIgnoreCase(max, "Max") && StringUtils.equalsIgnoreCase(min, "Min")) {

							// if tech is selected
							if (finalSiteTechList != "") {

								// if site is selected
								if (finalSitesNameList != "") {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1"
											+ " UNION ("
											+ "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1) order by 1,9 asc";
								}
								// site is not selected
								else {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1"
											+ " UNION ("
											+ "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1) order by 1,9 asc";
								}
							}
							// Tech is not selected
							else {

								// site name is selected
								if (finalSitesNameList != "") {

									gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1"
											+ " UNION ("
											+ "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
											+ StartDate + "' as \"startDate\" ,'" + EndDate
											+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
											+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK FROM( "
											+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
											+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name IN ("
											+ finalSitesNameList + " ) and b.AREA_NAME LIKE '%" + area
											+ "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
											+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
											+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 ) order by 1,9 asc";
								}
								// site name is not selected
								else {
									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										gridQueryString = "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\",voice_revenue as \"voiceRevenue\",sms_revenue as \"smsRevenue\" ,data_revenue as \"dataRevneue\" ,vas_revenue as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
												+ " SELECT combination_tech, site_name, AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue , "
												+ "RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( SELECT combination_tech, site_name, AREA_ID , revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ "SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech, b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue, "
												+ " b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID ,revenue_date "
												+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions
												+ "%'  GROUP BY site_name, area_id,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') )"
												+ "group by combination_tech, site_name, AREA_ID ) t where RANK = 1 "
												+ " UNION ("
												+ "select site_name as \"siteName\" ,area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\",voice_revenue as \"voiceRevenue\",sms_revenue as \"smsRevenue\" ,data_revenue as \"dataRevneue\" ,vas_revenue as \"vasRevenue\" ,combination_tech  as \"combination_technology\" from ( "
												+ " SELECT combination_tech, site_name, AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue , "
												+ "RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( SELECT combination_tech, site_name, AREA_ID , revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ "SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech, b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue, "
												+ " b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' order by b.site_name) GROUP BY combination_tech, site_name, AREA_ID ,revenue_date "
												+ "Union SELECT distinct 'No Combination Technology' as combination_tech,'No Site Name' as site_name,area_id as AREA_ID ,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue FROM PREPAID_PAYG_REVENUE WHERE REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and site_name LIKE '%null%' and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions
												+ "%'  GROUP BY site_name, area_id,TO_CHAR(revenue_date, 'YYYY-MM-DD HH24:MI:SS') )"
												+ "group by combination_tech, site_name, AREA_ID ) t where RANK = 1 ) order by 1,9 asc";

									} // end null sites name
										// Select all not null sites
									else {
										gridQueryString = "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
												+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK FROM( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
												+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.AREA_NAME LIKE '%"
												+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
												+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1"
												+ " UNION ("
												+ "select t.site_name as \"siteName\" ,t.area_id as \"areaId\" ,'"
												+ StartDate + "' as \"startDate\" ,'" + EndDate
												+ "'  as \"endDate\", t.voice_revenue as \"voiceRevenue\", t.sms_revenue as \"smsRevenue\" , t.data_revenue as \"dataRevneue\" , t.vas_revenue as \"vasRevenue\" ,t.combination_tech  as \"combination_technology\" from ( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,"
												+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK FROM( "
												+ "SELECT combination_tech as combination_tech , site_name as site_name, AREA_ID as AREA_ID ,revenue_date, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) "
												+ " from( SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.AREA_NAME LIKE '%"
												+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "order by b.site_name) group by combination_tech, site_name, AREA_ID,revenue_date)"
												+ "GROUP BY combination_tech,site_name,AREA_ID)t where RANK = 1 ) order by 1,9 asc, 3 desc";

									}
								}
							}
						}

						gridQuery = session.createNativeQuery(gridQueryString);
						gridQuery.setParameter("param1", StartDate);
						gridQuery.setParameter("param2", EndDate);
						@SuppressWarnings("unchecked")
						List<SiteRevenueReport> SiteRevenueReport = (List<SiteRevenueReport>) ((SQLQuery) gridQuery)
								.addScalar("siteName").addScalar("areaId").addScalar("startDate").addScalar("endDate")
								.addScalar("voiceRevenue").addScalar("smsRevenue").addScalar("dataRevneue")
								.addScalar("vasRevenue").addScalar("combination_technology")
								.setResultTransformer(Transformers.aliasToBean(SiteRevenueReport.class)).list();

						rtn.put("revenueReportList", SiteRevenueReport);
					}

				}

			} catch (Exception e) {
				logger.info("Error in creating session with Database", e);
			} finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}

		}

		return rtn;
	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	@RequestMapping(value = "/GenerateRevenueCharts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateRevenueCharts(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		List<Object> periodicData = new ArrayList<Object>();
		List<Object> maxPeriodicData = new ArrayList<Object>();
		List<Object> minPeriodicData = new ArrayList<Object>();
		ArrayList<Float> chartArray;
		JSONArray chartJSNArr;

		String area = request.getParameter("area");
		String technologyRegions = request.getParameter("technologyRegions");

		String period = request.getParameter("period");
		String max = request.getParameter("Max");
		String min = request.getParameter("Min");
		String revenueOption = request.getParameter("revenueOption");
		String selectedValue = request.getParameter("selectedValue");
		String minVal = request.getParameter("minVal");
		String maxVal = request.getParameter("maxVal");
		String start_Date = request.getParameter("startDate");
		String end_Date = request.getParameter("endDate");
		Date startDate = null;
		Timestamp StartDate;
		Timestamp EndDate;
		Date endDate = null;
		String queryRange;

		List<RevenueChartsReport> revenueCharts = new ArrayList<RevenueChartsReport>();
		List<RevenueChartsReport> maxRevenueCharts = new ArrayList<RevenueChartsReport>();
		List<RevenueChartsReport> minRevenueCharts = new ArrayList<RevenueChartsReport>();
		List<RevenueChartsReport> revenueChartList = new ArrayList<RevenueChartsReport>();
		List<RevenueChartsReport> maxRevenueChartList = new ArrayList<RevenueChartsReport>();
		List<RevenueChartsReport> minRevenueChartList = new ArrayList<RevenueChartsReport>();

		if (selectedValue != null) {
			queryRange = " and " + revenueOption + " >= DECODE(" + minVal + ",null,0," + minVal + ") AND "
					+ revenueOption + " <= DECODE('" + maxVal + "','null',(select SUM(" + revenueOption
					+ ") from prepaid_payg_revenue)," + maxVal + ")";
		} else
			queryRange = "";

		String siteTech = request.getParameter("selectedTech");
		String finalSiteTechList = "";
		String[] siteTechList = null;
		if (StringUtils.equalsIgnoreCase(siteTech, "") || StringUtils.equalsIgnoreCase(siteTech, null)) {
			finalSiteTechList = "";
		} else {
			String allSiteTech = siteTech.concat(",");
			// Split the string of technologies on comma to get each technology separately
			siteTechList = allSiteTech.split(",");
			StringBuilder allSiteTechs = new StringBuilder();

			// Append to each element '' and comma to satisfy the format of IN parameters
			for (int i = 0; i < siteTechList.length; i++) {

				allSiteTechs.append("'" + siteTechList[i] + "',");
			}

			finalSiteTechList = allSiteTechs.toString();
			finalSiteTechList = finalSiteTechList.substring(0, finalSiteTechList.length() - 1);

		}

		String nullSite = request.getParameter("nullSite");
		String sitesName = request.getParameter("allSitesName");
		String finalSitesNameList = "";
		String[] sitesNameList = null;

		if (StringUtils.equalsIgnoreCase(sitesName, "") || StringUtils.equalsIgnoreCase(sitesName, null)) {
			finalSitesNameList = "";
		} else {
			String allSitesName = sitesName.concat(",");

			// Split the string of sites on space-comma to get each site separately
			sitesNameList = allSitesName.split(" ,");

			StringBuilder allSitesNames = new StringBuilder();

			// Append to each element '' and comma to satisfy the format of IN parameters
			for (int i = 0; i < sitesNameList.length; i++) {

				allSitesNames.append("'" + sitesNameList[i] + "',");
			}

			finalSitesNameList = allSitesNames.toString();
			finalSitesNameList = finalSitesNameList.substring(0, finalSitesNameList.length() - 1);
		}

		int i = 0;
		Query siteRevenueChartQuery = null, timeRevenueStackandLineChartQuery = null,
				maxTimeRevenueStackandLineChartQuery = null, minTimeRevenueStackandLineChartQuery = null,
				pieRevenueChartQuery = null, pieRevenueTechChartQuery = null;
		String siteRevenueChartQueryString = "", timeRevenueStackandLineChartQueryString = "",
				maxTimeRevenueStackandLineChartQueryString = "", minTimeRevenueStackandLineChartQueryString = "",
				pieRevenueChartQueryString = "", pieRevenueTechChartQueryString = "";

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			Session session = null;
			Transaction tx = null;

			try {

				startDate = formatter.parse(start_Date);
				StartDate = new Timestamp(startDate.getTime());

				endDate = formatter.parse(end_Date);
				EndDate = new Timestamp(endDate.getTime());

				session = RptDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();

					// Site is not selected
					if (StringUtils.equalsIgnoreCase(sitesName, "null") || sitesName == null || sitesName == "") {

						// period is daily
						if (StringUtils.equalsIgnoreCase(period, "Daily")) {
							// no technology is selected
							if (finalSiteTechList == "") {

								// Select all sites name including NULL site
								if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

									// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										maxTimeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum desc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ " and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where RANK = 1 " + queryRange
												+ " group by  revenue_date order by revenue_date desc";

										minTimeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where RANK = 1 " + queryRange
												+ " group by  revenue_date order by revenue_date desc";

									}
									// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										maxTimeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum desc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where RANK = 1 " + queryRange
												+ " group by  revenue_date order by revenue_date desc";

									}
									// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										minTimeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where RANK = 1 " + queryRange
												+ " group by  revenue_date order by revenue_date desc";

									}

									// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
									else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {
										siteRevenueChartQueryString = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange
												+ " group by site_name, area_id order by site_name asc";

										timeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,\r\n"
												+ "sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue))\r\n"
												+ "as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by REVENUE_DATE order by REVENUE_DATE desc \r\n" + "";

										pieRevenueChartQueryString = "select coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange + " ";

									}

								}

								// else select all not null sites name
								else {
									// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {
										siteRevenueChartQueryString = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
												+ " and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange
												+ " group by site_name, area_id order by site_name asc";
										timeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
												+ " and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%'  " + queryRange
												+ " group by REVENUE_DATE order by REVENUE_DATE desc";
										pieRevenueChartQueryString = "select coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
												+ " and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange + " ";

									}

									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										maxTimeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum desc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
												+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where RANK = 1 " + queryRange
												+ " group by  revenue_date order by revenue_date desc";

									} else if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										minTimeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
												+ " and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where RANK = 1 " + queryRange
												+ " group by  revenue_date order by revenue_date desc";

									}

									else if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										maxTimeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum desc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
												+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
												+ " ) where RANK = 1 " + queryRange
												+ " group by  revenue_date order by revenue_date desc";

										minTimeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
												+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions
												+ "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
												+ " ) where RANK = 1 " + queryRange
												+ " group by  revenue_date order by revenue_date desc";

									}
								}

							} // end null tech

							// if there is a technology
							else if (finalSiteTechList != "") {

								// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									maxTimeRevenueStackandLineChartQueryString = "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date,site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 group by t.revenue_date order by t.revenue_date desc ";

									minTimeRevenueStackandLineChartQueryString = "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date,site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 group by t.revenue_date order by t.revenue_date desc ";

								}
								// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									maxTimeRevenueStackandLineChartQueryString = "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date,site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 group by t.revenue_date order by t.revenue_date desc ";

								}
								// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									minTimeRevenueStackandLineChartQueryString = "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date,site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 group by t.revenue_date order by t.revenue_date desc ";

								}
								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									siteRevenueChartQueryString = "select t.site_name,t.area_id, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by site_name, AREA_ID, revenue_date )t group by t.site_name,t.area_id order by t.site_name asc ";

									timeRevenueStackandLineChartQueryString = "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date,site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t group by t.revenue_date order by t.revenue_date desc ";

									pieRevenueChartQueryString = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
											+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT revenue_date,site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by site_name, AREA_ID, revenue_date )t group by t.revenue_date order by t.revenue_date asc )";

								}

							}

							// if max and min null
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
								siteRevenueChartQuery = session.createNativeQuery(siteRevenueChartQueryString);
								siteRevenueChartQuery.setParameter("param1", StartDate);
								siteRevenueChartQuery.setParameter("param2", EndDate);
								chartJSNArr = chartRevenues(siteRevenueChartQuery.list());

								if (siteRevenueChartQuery.list().size() > 0) {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
								} else {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								}

								pieRevenueChartQuery = session.createNativeQuery(pieRevenueChartQueryString);
								pieRevenueChartQuery.setParameter("param1", StartDate);
								pieRevenueChartQuery.setParameter("param2", EndDate);

								if (pieRevenueChartQuery.list().size() > 0) {
									rtn.put("pieRevenue", mapper.writeValueAsString(pieRevenueChartQuery.list()));
								} else {
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
								}

								timeRevenueStackandLineChartQuery = session
										.createNativeQuery(timeRevenueStackandLineChartQueryString);
								timeRevenueStackandLineChartQuery.setParameter("param1", StartDate);
								timeRevenueStackandLineChartQuery.setParameter("param2", EndDate);

								System.out.println("the StackedandLineRevenue is "
										+ mapper.writeValueAsString(timeRevenueStackandLineChartQuery.list()));

								if (timeRevenueStackandLineChartQuery.list().size() > 0) {

									periodicData = getStackedChartData(timeRevenueStackandLineChartQuery.list());

									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(periodicData));

								} else {
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
								}

								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
							}
							// max and min not null
							else {
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("pieRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));

								if (StringUtils.equalsIgnoreCase(max, "Max")
										|| StringUtils.equalsIgnoreCase(min, "Min")) {

									// line and stack charts
									if (StringUtils.equalsIgnoreCase(max, "Max")) {
										maxTimeRevenueStackandLineChartQuery = session
												.createNativeQuery(maxTimeRevenueStackandLineChartQueryString);
										maxTimeRevenueStackandLineChartQuery.setParameter("param1", StartDate);
										maxTimeRevenueStackandLineChartQuery.setParameter("param2", EndDate);

										if (maxTimeRevenueStackandLineChartQuery.list().size() > 0) {
											maxPeriodicData = getStackedChartData(
													maxTimeRevenueStackandLineChartQuery.list());
											rtn.put("MaxStackedandLineRevenue",
													mapper.writeValueAsString(maxPeriodicData));
										} else {
											rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										}

									}
									if (StringUtils.equalsIgnoreCase(min, "Min")) {
										minTimeRevenueStackandLineChartQuery = session
												.createNativeQuery(minTimeRevenueStackandLineChartQueryString);
										minTimeRevenueStackandLineChartQuery.setParameter("param1", StartDate);
										minTimeRevenueStackandLineChartQuery.setParameter("param2", EndDate);

										if (minTimeRevenueStackandLineChartQuery.list().size() > 0) {
											minPeriodicData = getStackedChartData(
													minTimeRevenueStackandLineChartQuery.list());
											rtn.put("MinStackedandLineRevenue",
													mapper.writeValueAsString(minPeriodicData));
										} else {
											rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										}

									}
								}

							}

						}
						// period is weekly
						if (StringUtils.equalsIgnoreCase(period, "Weekly")) {

							// System.out.println("site is not selectedd");

							double diff = (EndDate.getTime() - StartDate.getTime()) / 1000.0;
							diff /= (60 * 60 * 24 * 7);
							diff = Math.abs(Math.ceil(diff));

							String date = "";
							Date weeklyDate;
							Timestamp weeklyEndDate = StartDate;
							Calendar cal = Calendar.getInstance();

							// Tech is not selected
							if (finalSiteTechList == "") {

								// Select all sites name including NULL site
								if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

									// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										maxTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
												+ queryRange;

										minTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
												+ queryRange;
									}
									// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										maxTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
												+ queryRange;
									}
									// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										minTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
												+ queryRange;
									}
									// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {
										siteRevenueChartQueryString = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ " and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange
												+ " group by site_name, area_id order by site_name asc ";

										timeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date
												+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 "
												+ " and t.AREA_NAME LIKE '%" + area + "%' and t.REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange + "";

										pieRevenueChartQueryString = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ " and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange + "";

									}
								}
								// else select all not null sites name
								else {
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {
										siteRevenueChartQueryString = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
												+ " and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange
												+ " group by site_name, area_id";
										timeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date
												+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name !='null' "
												+ "and t.AREA_NAME LIKE '%" + area + "%' and t.REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange + "";
										pieRevenueChartQueryString = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
												+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange + "";

									}

									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										maxTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
												+ queryRange;
									}

									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										minTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null'  and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
												+ queryRange;
									}
									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										maxTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null'  and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
												+ queryRange;

										minTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name  !='null'  and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
												+ queryRange;
									}
								}
							} // end null tech

							// if there is a technology
							else if (finalSiteTechList != "") {
								// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									maxTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
											+ date
											+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

									minTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
											+ date
											+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

								}
								// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									maxTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
											+ date
											+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

								}
								// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									minTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
											+ date
											+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

								}
								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									siteRevenueChartQueryString = "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0) ,coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0), coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t group by t.site_name,t.area_id order by t.site_name asc ";

									timeRevenueStackandLineChartQueryString = "select  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\" ,coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\", coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0)  as \"totalRevenue\" ,'"
											+ date
											+ "' as \"Date\"  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID)t order by t.site_name asc ";

									pieRevenueChartQueryString = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
											+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by site_name,revenue_date, AREA_ID)t order by t.site_name asc )";

								}

							}

							// if max and min null
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
								siteRevenueChartQuery = session.createNativeQuery(siteRevenueChartQueryString);
								siteRevenueChartQuery.setParameter("param1", StartDate);
								siteRevenueChartQuery.setParameter("param2", EndDate);
								chartJSNArr = chartRevenues(siteRevenueChartQuery.list());

								if (siteRevenueChartQuery.list().size() > 0) {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
								} else {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								}

								pieRevenueChartQuery = session.createNativeQuery(pieRevenueChartQueryString);
								pieRevenueChartQuery.setParameter("param1", StartDate);
								pieRevenueChartQuery.setParameter("param2", EndDate);

								if (pieRevenueChartQuery.list().size() > 0) {
									rtn.put("pieRevenue", mapper.writeValueAsString(pieRevenueChartQuery.list()));
								} else {
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
								}

								Timestamp weeklyStartDate = StartDate;
								for (i = 0; i < diff; i++) {

									weeklyEndDate = weeklyStartDate;
									cal.setTime(weeklyEndDate);
									cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									// weeklyEndDate = formatter.parse(weeklyEndDate);
									weeklyEndDate = new Timestamp(weeklyDate.getTime());
									// System.out.println("for week " + i + ":" + weeklyEndDate);

									if ((weeklyEndDate).compareTo(EndDate) > 0) {
										System.out.println("the weeklyEndDate is greater than enddate");
										weeklyEndDate = EndDate;

									}
									// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth() +1)+"/"+
									// (weeklyStartDate.getYear()+1900)+"-"+ (weeklyEndDate.getDate())+"/"+
									// (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getYear()+1900);

									date = (weeklyStartDate.getYear() + 1900) + "/" + (weeklyStartDate.getMonth() + 1)
											+ "/" + (weeklyStartDate.getDate()) + "-" + (weeklyEndDate.getYear() + 1900)
											+ "/" + (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

									timeRevenueStackandLineChartQuery = session
											.createNativeQuery(timeRevenueStackandLineChartQueryString);
									timeRevenueStackandLineChartQuery.setParameter("param1", weeklyStartDate);
									timeRevenueStackandLineChartQuery.setParameter("param2", weeklyEndDate);
									revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) timeRevenueStackandLineChartQuery)
											.addScalar("voiceRevenue").addScalar("smsRevenue").addScalar("dataRevneue")
											.addScalar("vasRevenue").addScalar("totalRevenue").addScalar("Date")
											.setResultTransformer(Transformers.aliasToBean(RevenueChartsReport.class))
											.list();
									revenueCharts.get(0).setDate(date);
									revenueChartList.addAll(revenueCharts);

									weeklyStartDate = weeklyEndDate;
									cal.setTime(weeklyStartDate);
									cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									weeklyStartDate = new Timestamp(weeklyDate.getTime());

									// System.out.println("after start date " + weeklyStartDate);

								}

								periodicData = getPeriodicChartData(revenueChartList);
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(periodicData));

								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
							}
							// max and min not null
							else {
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("pieRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));

								if (StringUtils.equalsIgnoreCase(max, "Max")
										|| StringUtils.equalsIgnoreCase(min, "Min")) {
									// line and stack charts
									Timestamp weeklyStartDate = StartDate;
									for (i = 0; i < diff; i++) {

										weeklyEndDate = weeklyStartDate;
										cal.setTime(weeklyEndDate);
										cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
										weeklyDate = cal.getTime();
										// weeklyEndDate = formatter.parse(weeklyEndDate);
										weeklyEndDate = new Timestamp(weeklyDate.getTime());

										if ((weeklyEndDate).compareTo(EndDate) > 0) {
											System.out.println("the weeklyEndDate is greater than enddate");
											weeklyEndDate = EndDate;

										}
										// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth() +1)+"/"+
										// (weeklyStartDate.getYear()+1900)+"-"+ (weeklyEndDate.getDate())+"/"+
										// (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getYear()+1900);
										date = (weeklyStartDate.getYear() + 1900) + "/"
												+ (weeklyStartDate.getMonth() + 1) + "/" + (weeklyStartDate.getDate())
												+ "-" + (weeklyEndDate.getYear() + 1900) + "/"
												+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

										if (StringUtils.equalsIgnoreCase(max, "Max")) {
											maxTimeRevenueStackandLineChartQuery = session
													.createNativeQuery(maxTimeRevenueStackandLineChartQueryString);
											maxTimeRevenueStackandLineChartQuery.setParameter("param1",
													weeklyStartDate);
											maxTimeRevenueStackandLineChartQuery.setParameter("param2", weeklyEndDate);
											maxRevenueCharts = (List<RevenueChartsReport>) ((SQLQuery) maxTimeRevenueStackandLineChartQuery)
													.addScalar("voiceRevenue").addScalar("smsRevenue")
													.addScalar("dataRevneue").addScalar("vasRevenue")
													.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
															Transformers.aliasToBean(RevenueChartsReport.class))
													.list();
											maxRevenueCharts.get(0).setDate(date);
											maxRevenueChartList.addAll(maxRevenueCharts);
										}
										if (StringUtils.equalsIgnoreCase(min, "Min")) {
											minTimeRevenueStackandLineChartQuery = session
													.createNativeQuery(minTimeRevenueStackandLineChartQueryString);
											minTimeRevenueStackandLineChartQuery.setParameter("param1",
													weeklyStartDate);
											minTimeRevenueStackandLineChartQuery.setParameter("param2", weeklyEndDate);
											minRevenueCharts = (List<RevenueChartsReport>) ((SQLQuery) minTimeRevenueStackandLineChartQuery)
													.addScalar("voiceRevenue").addScalar("smsRevenue")
													.addScalar("dataRevneue").addScalar("vasRevenue")
													.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
															Transformers.aliasToBean(RevenueChartsReport.class))
													.list();
											minRevenueCharts.get(0).setDate(date);
											minRevenueChartList.addAll(minRevenueCharts);
										}

										weeklyStartDate = weeklyEndDate;
										cal.setTime(weeklyStartDate);
										cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
										weeklyDate = cal.getTime();
										weeklyStartDate = new Timestamp(weeklyDate.getTime());

									}

									if (StringUtils.equalsIgnoreCase(max, "Max")) {

										if (maxRevenueChartList.size() > 0) {
											maxPeriodicData = getPeriodicChartData(maxRevenueChartList);
											rtn.put("MaxStackedandLineRevenue",
													mapper.writeValueAsString(maxPeriodicData));
										} else {
											rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										}

									}
									if (StringUtils.equalsIgnoreCase(min, "Min")) {

										if (minRevenueChartList.size() > 0) {
											minPeriodicData = getPeriodicChartData(minRevenueChartList);
											rtn.put("MinStackedandLineRevenue",
													mapper.writeValueAsString(minPeriodicData));
										} else {
											rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										}

									}

								} else {
									if (StringUtils.equalsIgnoreCase(min, null)) {
										rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									}
									if (StringUtils.equalsIgnoreCase(max, null)) {
										rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									}

								}

							}

						} // ended weekly

						// start of monthly

						if (StringUtils.equalsIgnoreCase(period, "Monthly")) {
							Calendar m = Calendar.getInstance();
							m.setTime(StartDate);
							int nMonth1 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
							m.setTime(EndDate);
							int nMonth2 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
							int diff = Math.abs(nMonth2 - nMonth1);

							Timestamp month = StartDate;
							String date = "";

							// Tech is not selected
							if (finalSiteTechList == "") {

								// Select all sites name including NULL site
								if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

									// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										maxTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
												+ queryRange;

										minTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
												+ queryRange;

									}
									// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										maxTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
												+ queryRange;

									}
									// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										minTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
												+ queryRange;

									}
									// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {
										siteRevenueChartQueryString = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ " and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange
												+ " group by site_name, area_id order by site_name asc ";

										timeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date
												+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 "
												+ " and t.AREA_NAME LIKE '%" + area + "%' and t.REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange + "";

										pieRevenueChartQueryString = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ " and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange + "";

									}
								}
								// else select all not null sites name
								else {
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {
										siteRevenueChartQueryString = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
												+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange
												+ " group by site_name, area_id";
										timeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date
												+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name !='null' "
												+ "and t.AREA_NAME LIKE '%" + area + "%' and t.REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange + "";
										pieRevenueChartQueryString = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
												+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
												+ technologyRegions + "%' " + queryRange + "";

									}

									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, null)) {

										maxTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null'  and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
												+ queryRange;

									}
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										minTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null'  and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
												+ queryRange;

									}
									if (StringUtils.equalsIgnoreCase(max, "Max")
											&& StringUtils.equalsIgnoreCase(min, "Min")) {

										maxTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
												+ queryRange;

										minTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date + "' as \"Date\" from ( "
												+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
												+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
												+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name  !='null' and AREA_NAME LIKE '%"
												+ area + "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
												+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
												+ queryRange;

									}
								}
							} // end null tech

							// if there is a technology
							else if (finalSiteTechList != "") {

								// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									maxTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
											+ date
											+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

									minTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
											+ date
											+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

								}
								// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									maxTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
											+ date
											+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";
								}
								// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									minTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
											+ date
											+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

								}
								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									siteRevenueChartQueryString = "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0) ,coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0), coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t group by t.site_name,t.area_id order by t.site_name asc ";

									timeRevenueStackandLineChartQueryString = "select  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\" ,coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\", coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0)  as \"totalRevenue\" ,'"
											+ date
											+ "' as \"Date\"  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID)t order by t.site_name asc ";

									pieRevenueChartQueryString = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
											+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by site_name,revenue_date, AREA_ID)t order by t.site_name asc )";

								}

							}

							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {

								siteRevenueChartQuery = session.createNativeQuery(siteRevenueChartQueryString);
								siteRevenueChartQuery.setParameter("param1", StartDate);
								siteRevenueChartQuery.setParameter("param2", EndDate);
								chartJSNArr = chartRevenues(siteRevenueChartQuery.list());

								if (siteRevenueChartQuery.list().size() > 0) {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
								} else {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								}

								pieRevenueChartQuery = session.createNativeQuery(pieRevenueChartQueryString);
								pieRevenueChartQuery.setParameter("param1", StartDate);
								pieRevenueChartQuery.setParameter("param2", EndDate);

								if (pieRevenueChartQuery.list().size() > 0) {

									rtn.put("pieRevenue", mapper.writeValueAsString(pieRevenueChartQuery.list()));
								} else {
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
								}

								for (i = 0; i <= diff; i++) {

									String startmonth = formatter.format(month);
									Date monthDate = formatter.parse(startmonth);
									Timestamp monthofDate = new Timestamp(monthDate.getTime());

									Calendar calendar = Calendar.getInstance();
									calendar.setTime(monthofDate);
									calendar.add(Calendar.MONTH, 1);
									calendar.set(Calendar.DAY_OF_MONTH, 1);
									calendar.add(Calendar.DATE, -1);
									Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

									String endMonth = formatter.format(lastDayOfMonth);
									Date endOfDate = formatter.parse(endMonth);
									Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
									// System.out.println("for month " + i + ":" + endOfMonth);

									if ((endOfMonth).compareTo(EndDate) > 0) {
										System.out.println("the endOfMonth is greater than enddate");
										endOfMonth = EndDate;

									}

									// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
									// (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
									// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
									date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1) + "/"
											+ (monthofDate.getDate()) + "-" + (endOfMonth.getYear() + 1900) + "/"
											+ (endOfMonth.getMonth() + 1) + "/" + (endOfMonth.getDate());

									timeRevenueStackandLineChartQuery = session
											.createNativeQuery(timeRevenueStackandLineChartQueryString);
									timeRevenueStackandLineChartQuery.setParameter("param1", monthofDate);
									timeRevenueStackandLineChartQuery.setParameter("param2", endOfMonth);
									revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) timeRevenueStackandLineChartQuery)
											.addScalar("voiceRevenue").addScalar("smsRevenue").addScalar("dataRevneue")
											.addScalar("vasRevenue").addScalar("totalRevenue").addScalar("Date")
											.setResultTransformer(Transformers.aliasToBean(RevenueChartsReport.class))
											.list();
									revenueCharts.get(0).setDate(date);
									revenueChartList.addAll(revenueCharts);

									calendar.setTime(endOfMonth);
									calendar.add(Calendar.DATE, +1);
									Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
									month = NextDayOfMonth;

								}

								periodicData = getPeriodicChartData(revenueChartList);
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(periodicData));

								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));

							} else {
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("pieRevenue", mapper.writeValueAsString(""));
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));

								if (StringUtils.equalsIgnoreCase(max, "Max")
										|| StringUtils.equalsIgnoreCase(min, "Min")) {
									// line and stack charts
									for (i = 0; i <= diff; i++) {

										String startmonth = formatter.format(month);
										Date monthDate = formatter.parse(startmonth);
										Timestamp monthofDate = new Timestamp(monthDate.getTime());

										Calendar calendar = Calendar.getInstance();
										calendar.setTime(monthofDate);
										calendar.add(Calendar.MONTH, 1);
										calendar.set(Calendar.DAY_OF_MONTH, 1);
										calendar.add(Calendar.DATE, -1);
										Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

										String endMonth = formatter.format(lastDayOfMonth);
										Date endOfDate = formatter.parse(endMonth);
										Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
										// System.out.println("for month " + i + ":" + endOfMonth);

										if ((endOfMonth).compareTo(EndDate) > 0) {
											System.out.println("the endOfMonth is greater than enddate");
											endOfMonth = EndDate;

										}
										// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
										// (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
										// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
										date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1) + "/"
												+ (monthofDate.getDate()) + "-" + (endOfMonth.getYear() + 1900) + "/"
												+ (endOfMonth.getMonth() + 1) + "/" + (endOfMonth.getDate());

										if (StringUtils.equalsIgnoreCase(max, "Max")) {
											maxTimeRevenueStackandLineChartQuery = session
													.createNativeQuery(maxTimeRevenueStackandLineChartQueryString);
											maxTimeRevenueStackandLineChartQuery.setParameter("param1", monthofDate);
											maxTimeRevenueStackandLineChartQuery.setParameter("param2", endOfMonth);
											maxRevenueCharts = (List<RevenueChartsReport>) ((SQLQuery) maxTimeRevenueStackandLineChartQuery)
													.addScalar("voiceRevenue").addScalar("smsRevenue")
													.addScalar("dataRevneue").addScalar("vasRevenue")
													.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
															Transformers.aliasToBean(RevenueChartsReport.class))
													.list();
											maxRevenueCharts.get(0).setDate(date);
											maxRevenueChartList.addAll(maxRevenueCharts);
										}
										if (StringUtils.equalsIgnoreCase(min, "Min")) {
											minTimeRevenueStackandLineChartQuery = session
													.createNativeQuery(minTimeRevenueStackandLineChartQueryString);
											minTimeRevenueStackandLineChartQuery.setParameter("param1", monthofDate);
											minTimeRevenueStackandLineChartQuery.setParameter("param2", endOfMonth);
											minRevenueCharts = (List<RevenueChartsReport>) ((SQLQuery) minTimeRevenueStackandLineChartQuery)
													.addScalar("voiceRevenue").addScalar("smsRevenue")
													.addScalar("dataRevneue").addScalar("vasRevenue")
													.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
															Transformers.aliasToBean(RevenueChartsReport.class))
													.list();
											minRevenueCharts.get(0).setDate(date);
											minRevenueChartList.addAll(minRevenueCharts);
										}

										calendar.setTime(endOfMonth);
										calendar.add(Calendar.DATE, +1);
										Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
										month = NextDayOfMonth;
										// System.out.println("after start date " + NextDayOfMonth);

									}

									if (StringUtils.equalsIgnoreCase(max, "Max")) {

										if (maxRevenueChartList.size() > 0) {
											maxPeriodicData = getPeriodicChartData(maxRevenueChartList);
											rtn.put("MaxStackedandLineRevenue",
													mapper.writeValueAsString(maxPeriodicData));
										} else {
											rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										}

									}
									if (StringUtils.equalsIgnoreCase(min, "Min")) {

										if (minRevenueChartList.size() > 0) {
											minPeriodicData = getPeriodicChartData(minRevenueChartList);
											rtn.put("MinStackedandLineRevenue",
													mapper.writeValueAsString(minPeriodicData));
										} else {
											rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										}

									}

								}

							}

						}

						// start of acc or absolute max or min
						if (StringUtils.equalsIgnoreCase(period, "Accu")
								|| StringUtils.equalsIgnoreCase(period, "null")) {
							// System.out.println("acccccc");
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
								// Tech is not selected
								if (finalSiteTechList == "") {

									// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
									/*
									 * if (StringUtils.equalsIgnoreCase(max, "Max") &&
									 * StringUtils.equalsIgnoreCase(min, "Min")) { siteRevenueChartQueryString =
									 * "select site_name,AREA_ID,sum(voice_rev),sum(sms_rev),sum(data_rev),sum(vas_rev),sum(rev_sum) from("
									 * +
									 * "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum desc)" +
									 * " ) where row_nb = 1 " + queryRange + "" + "UNION (" +
									 * "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum asc)" +
									 * " ) where row_nb = 1 " + queryRange +
									 * ")) group by site_name,AREA_ID order by site_name asc";
									 * 
									 * timeRevenueStackandLineChartQueryString =
									 * "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from ("
									 * +
									 * "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,(sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum desc)" +
									 * " ) where row_nb = 1 " + queryRange + "" + "UNION (" +
									 * "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,(sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum asc)" +
									 * " ) where row_nb = 1 " + queryRange + "))";
									 * 
									 * pieRevenueChartQueryString =
									 * "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from ( select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum desc)" +
									 * " ) where row_nb = 1 " + queryRange + "" + "UNION (" +
									 * "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum asc)" +
									 * " ) where row_nb = 1 " + queryRange + "))"; } // IF MAX IS ONLY CHECKED
									 * WITHOUT TECHNOLOGY if (StringUtils.equalsIgnoreCase(max, "Max") &&
									 * StringUtils.equalsIgnoreCase(min, null)) { siteRevenueChartQueryString =
									 * "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum desc)" +
									 * " ) where row_nb = 1 " + queryRange + " ";
									 * 
									 * timeRevenueStackandLineChartQueryString =
									 * "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),(sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum desc)" +
									 * " ) where row_nb = 1 " + queryRange + " ";
									 * 
									 * pieRevenueChartQueryString =
									 * "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum desc)" +
									 * " ) where row_nb = 1 " + queryRange + " "; } // IF MIN IS ONLY CHECKED
									 * WITHOUT TECHNOLOGY if (StringUtils.equalsIgnoreCase(max, null) &&
									 * StringUtils.equalsIgnoreCase(min, "Min")) { siteRevenueChartQueryString =
									 * "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum asc)" +
									 * " ) where row_nb = 1 " + queryRange + " ";
									 * 
									 * timeRevenueStackandLineChartQueryString =
									 * "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),(sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum asc)" +
									 * " ) where row_nb = 1 " + queryRange + " ";
									 * 
									 * pieRevenueChartQueryString =
									 * "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum asc)" +
									 * " ) where row_nb = 1 " + queryRange + " "; }
									 */

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
										if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, null)) {
											siteRevenueChartQueryString = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ " and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
													+ technologyRegions + "%' " + queryRange
													+ " group by site_name, area_id order by site_name asc";
											/*
											 * timeRevenueStackandLineChartQueryString =
											 * "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
											 * +date+"' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name LIKE '%"
											 * + site + "%' and t.AREA_NAME LIKE '%" + area +
											 * "%' and t.REGION_NAME LIKE '%" + technologyRegions + "%' " + queryRange +
											 * "";
											 */
											pieRevenueChartQueryString = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ " and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
													+ technologyRegions + "%' " + queryRange + "";

										}
									}
									// else select all not null sites name
									else {

										if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, null)) {
											siteRevenueChartQueryString = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
													+ technologyRegions + "%' " + queryRange
													+ " group by site_name, area_id order by site_name asc";

											pieRevenueChartQueryString = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
													+ technologyRegions + "%' " + queryRange + "";

										}
									}
								}
								// if there is a technology
								else if (finalSiteTechList != "") {

									// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
									/*
									 * if(StringUtils.equalsIgnoreCase(max,"Max") &&
									 * StringUtils.equalsIgnoreCase(min,"Min")) { siteRevenueChartQueryString=
									 * "select site_name,area_id,coalesce(sum(voice_revenue),0),coalesce(sum(sms_revenue),0),coalesce(sum(data_revenue),0),coalesce(sum(vas_revenue),0),coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from ("
									 * +
									 * "select t.site_name,t.area_id, t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)"
									 * + ") t where row_nb = 1 " + " UNION ("+
									 * "select t.site_name,t.area_id, t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)"
									 * +
									 * ") t  where row_nb = 1 ) order by 1 asc) group by site_name,AREA_ID order by site_name"
									 * ;
									 * 
									 * timeRevenueStackandLineChartQueryString=
									 * "  select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0),coalesce(sum(tot_data),0),coalesce(sum(tot_vas),0),coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from ("
									 * +
									 * "	select  t.voice_revenue as tot_voice, t.sms_revenue as tot_sms,t.data_revenue as tot_data, t.vas_revenue as tot_vas from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)"
									 * + ") t where row_nb = 1 " + " UNION ("+
									 * "select  t.voice_revenue as tot_voice, t.sms_revenue as tot_sms,t.data_revenue as tot_data, t.vas_revenue as tot_vas from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)"
									 * + ") t  where row_nb = 1 ))";
									 * 
									 * pieRevenueChartQueryString=
									 * "  select coalesce(sum(voice_revenue),0) as tot_voice,coalesce(sum(sms_revenue),0) as tot_sms,coalesce(sum(data_revenue),0) as tot_data,coalesce(sum(vas_revenue),0) as tot_vas,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_rev from ("
									 * +
									 * "	select  t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)"
									 * + ") t where row_nb = 1 " + " UNION ("+
									 * "select  t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)"
									 * + ") t  where row_nb = 1 ))";
									 * 
									 * } // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
									 * if(StringUtils.equalsIgnoreCase(max,"Max") &&
									 * StringUtils.equalsIgnoreCase(min,null)) {
									 * //'"+weeklyStartDate+"','"+weeklyEndDate+"',
									 * 
									 * siteRevenueChartQueryString=
									 * "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)"
									 * +
									 * ") t where row_nb = 1 group by t.site_name,t.area_id order by  t.site_name asc"
									 * ;
									 * 
									 * timeRevenueStackandLineChartQueryString=
									 * "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)"
									 * + ") t where row_nb = 1 order by t.site_name asc";
									 * 
									 * pieRevenueChartQueryString=
									 * "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)"
									 * + ") t where row_nb = 1 order by t.site_name asc";
									 * 
									 * } // IF MIN IS ONLY CHECKED WITH TECHNOLOGY
									 * if(StringUtils.equalsIgnoreCase(max,null) &&
									 * StringUtils.equalsIgnoreCase(min,"Min")) { siteRevenueChartQueryString=
									 * "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)"
									 * +
									 * ") t where row_nb = 1 group by t.site_name,t.area_id order by  t.site_name asc"
									 * ;
									 * 
									 * timeRevenueStackandLineChartQueryString=
									 * "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)"
									 * + ") t where row_nb = 1 order by t.site_name asc";
									 * 
									 * pieRevenueChartQueryString=
									 * "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)"
									 * + ") t where row_nb = 1 order by t.site_name asc"; }
									 */
									// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {
										siteRevenueChartQueryString = "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0) ,coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0), coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t group by t.site_name,t.area_id order by t.site_name asc ";

										pieRevenueChartQueryString = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
												+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "  order by b.site_name) group by site_name,revenue_date, AREA_ID)t order by t.site_name asc )";

									}

								}

								siteRevenueChartQuery = session.createNativeQuery(siteRevenueChartQueryString);
								siteRevenueChartQuery.setParameter("param1", StartDate);
								siteRevenueChartQuery.setParameter("param2", EndDate);
								chartJSNArr = chartRevenues(siteRevenueChartQuery.list());

								pieRevenueChartQuery = session.createNativeQuery(pieRevenueChartQueryString);
								pieRevenueChartQuery.setParameter("param1", StartDate);
								pieRevenueChartQuery.setParameter("param2", EndDate);

								if (siteRevenueChartQuery.list().size() > 0) {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
								} else {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								}
								if (pieRevenueChartQuery.list().size() > 0) {
									rtn.put("pieRevenue", mapper.writeValueAsString(pieRevenueChartQuery.list()));
								} else {
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
								}
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
							} else {
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("pieRevenue", mapper.writeValueAsString(""));
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
							}
						} // END OF ACC

					} // end if site is null

					else {
						// if site is exist

						// period is daily
						if (StringUtils.equalsIgnoreCase(period, "Daily")) {

							// System.out.println("/*/*Here site is selected");

							// no technology is selected
							if (finalSiteTechList == "") {

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									maxTimeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION BY revenue_date order by rev_sum desc) as RANK from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
											+ finalSitesNameList + " ) " + " and AREA_NAME LIKE '%" + area
											+ "%' and REGION_NAME LIKE '%" + technologyRegions
											+ "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
											+ " ) where RANK = 1 " + queryRange
											+ " group by  revenue_date order by revenue_date desc";

									minTimeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION BY revenue_date order by rev_sum asc) as RANK from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
											+ finalSitesNameList + " ) " + " and AREA_NAME LIKE '%" + area
											+ "%' and REGION_NAME LIKE '%" + technologyRegions
											+ "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
											+ " ) where RANK = 1 " + queryRange
											+ " group by  revenue_date order by revenue_date desc";

								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									maxTimeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION BY revenue_date order by rev_sum desc) as RANK from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
											+ finalSitesNameList + " )" + " and AREA_NAME LIKE '%" + area
											+ "%' and REGION_NAME LIKE '%" + technologyRegions
											+ "%' group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
											+ " ) where RANK = 1 " + queryRange
											+ " group by  revenue_date order by revenue_date desc";
								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									minTimeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
											+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION BY revenue_date order by rev_sum asc) as RANK from "
											+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
											+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
											+ finalSitesNameList + " ) " + "and AREA_NAME LIKE '%" + area
											+ "%' and REGION_NAME LIKE '%" + technologyRegions
											+ "%' group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
											+ " ) where RANK = 1 " + queryRange
											+ " group by  revenue_date order by revenue_date desc";

								}
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {
									siteRevenueChartQueryString = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
											+ " and AREA_NAME LIKE '%" + area + "%' and site_name IN ( "
											+ finalSitesNameList + " ) and REGION_NAME LIKE '%" + technologyRegions
											+ "%' " + queryRange
											+ " group by site_name, area_id order by site_name asc";
									timeRevenueStackandLineChartQueryString = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
											+ " and AREA_NAME LIKE '%" + area + "%' and site_name IN ( "
											+ finalSitesNameList + " )  and REGION_NAME LIKE '%" + technologyRegions
											+ "%'  " + queryRange + " group by REVENUE_DATE order by REVENUE_DATE desc";
									pieRevenueChartQueryString = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
											+ " and AREA_NAME LIKE '%" + area + "%' and site_name IN ( "
											+ finalSitesNameList + " ) and REGION_NAME LIKE '%" + technologyRegions
											+ "%' " + queryRange + " ";

								}

							}
							// if there is a technology
							else if (finalSiteTechList != "") {

								// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									maxTimeRevenueStackandLineChartQueryString = "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date,site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN ( " + finalSitesNameList
											+ " )  and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 group by t.revenue_date order by t.revenue_date desc ";

									minTimeRevenueStackandLineChartQueryString = "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date,site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN ( " + finalSitesNameList
											+ " )  and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 group by t.revenue_date order by t.revenue_date desc ";

								}
								// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									maxTimeRevenueStackandLineChartQueryString = "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date,site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN ( " + finalSitesNameList
											+ " )  and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 group by t.revenue_date order by t.revenue_date desc ";

								}
								// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									minTimeRevenueStackandLineChartQueryString = "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date,site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(PARTITION BY revenue_date order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN ( " + finalSitesNameList
											+ " )  and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t where RANK = 1 group by t.revenue_date order by t.revenue_date desc ";

								}
								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								else if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									siteRevenueChartQueryString = "select t.site_name,t.area_id, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN (" + finalSitesNameList + ") and b.AREA_NAME LIKE '%"
											+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions
											+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by site_name, AREA_ID, revenue_date )t group by t.site_name,t.area_id order by t.site_name asc ";

									timeRevenueStackandLineChartQueryString = "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date,site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN (" + finalSitesNameList + ") and b.AREA_NAME LIKE '%"
											+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions
											+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t group by t.revenue_date order by t.revenue_date desc ";

									pieRevenueChartQueryString = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
											+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT revenue_date,site_name, AREA_ID ,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN (" + finalSitesNameList + ")and b.AREA_NAME LIKE '%"
											+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions
											+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by site_name, AREA_ID, revenue_date )t group by t.revenue_date order by t.revenue_date asc )";

								}

							}

							// if max and min null
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
								siteRevenueChartQuery = session.createNativeQuery(siteRevenueChartQueryString);
								siteRevenueChartQuery.setParameter("param1", StartDate);
								siteRevenueChartQuery.setParameter("param2", EndDate);
								chartJSNArr = chartRevenues(siteRevenueChartQuery.list());

								if (siteRevenueChartQuery.list().size() > 0) {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
								} else {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								}

								pieRevenueChartQuery = session.createNativeQuery(pieRevenueChartQueryString);
								pieRevenueChartQuery.setParameter("param1", StartDate);
								pieRevenueChartQuery.setParameter("param2", EndDate);

								if (pieRevenueChartQuery.list().size() > 0) {
									rtn.put("pieRevenue", mapper.writeValueAsString(pieRevenueChartQuery.list()));
								} else {
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
								}

								timeRevenueStackandLineChartQuery = session
										.createNativeQuery(timeRevenueStackandLineChartQueryString);
								timeRevenueStackandLineChartQuery.setParameter("param1", StartDate);
								timeRevenueStackandLineChartQuery.setParameter("param2", EndDate);

								if (timeRevenueStackandLineChartQuery.list().size() > 0) {
									periodicData = getStackedChartData(timeRevenueStackandLineChartQuery.list());
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(periodicData));
								} else {
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
								}

								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
							}
							// max and min not null
							else {
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("pieRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));

								if (StringUtils.equalsIgnoreCase(max, "Max")
										|| StringUtils.equalsIgnoreCase(min, "Min")) {

									// line and stack charts
									if (StringUtils.equalsIgnoreCase(max, "Max")) {
										maxTimeRevenueStackandLineChartQuery = session
												.createNativeQuery(maxTimeRevenueStackandLineChartQueryString);
										maxTimeRevenueStackandLineChartQuery.setParameter("param1", StartDate);
										maxTimeRevenueStackandLineChartQuery.setParameter("param2", EndDate);

										if (maxTimeRevenueStackandLineChartQuery.list().size() > 0) {
											maxPeriodicData = getStackedChartData(
													maxTimeRevenueStackandLineChartQuery.list());
											rtn.put("MaxStackedandLineRevenue",
													mapper.writeValueAsString(maxPeriodicData));
										} else {
											rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										}

									}
									if (StringUtils.equalsIgnoreCase(min, "Min")) {
										minTimeRevenueStackandLineChartQuery = session
												.createNativeQuery(minTimeRevenueStackandLineChartQueryString);
										minTimeRevenueStackandLineChartQuery.setParameter("param1", StartDate);
										minTimeRevenueStackandLineChartQuery.setParameter("param2", EndDate);

										if (minTimeRevenueStackandLineChartQuery.list().size() > 0) {
											minPeriodicData = getStackedChartData(
													minTimeRevenueStackandLineChartQuery.list());
											rtn.put("MinStackedandLineRevenue",
													mapper.writeValueAsString(minPeriodicData));
										} else {
											rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										}

									}
								}

							}

						} // end of daily

						// period is weekly
						if (StringUtils.equalsIgnoreCase(period, "Weekly")) {

							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {

								double diff = (EndDate.getTime() - StartDate.getTime()) / 1000.0;
								diff /= (60 * 60 * 24 * 7);
								// System.out.println("the week difference is before " + diff);
								diff = Math.abs(Math.ceil(diff));
								// System.out.println("the week difference is after " + diff);

								String date = "";
								Date weeklyDate;
								Timestamp weeklyStartDate = StartDate;
								Timestamp weeklyEndDate = StartDate;
								Calendar cal = Calendar.getInstance();

								// Tech is not selected
								if (finalSiteTechList == "") {

									siteRevenueChartQueryString = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ("
											+ finalSitesNameList + " )  " + " and AREA_NAME LIKE '%" + area
											+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' " + queryRange
											+ " group by site_name, area_id";

									timeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
											+ date
											+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2  "
											+ "and t.AREA_NAME LIKE '%" + area + "%' and t.REGION_NAME LIKE '%"
											+ technologyRegions + "%'  AND t.site_name IN (" + finalSitesNameList + ") "
											+ queryRange + "";

									pieRevenueChartQueryString = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
											+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' AND site_name IN (" + finalSitesNameList + ")  "
											+ queryRange + "";

								}
								// if tech is selected
								else if (finalSiteTechList != "") {

									siteRevenueChartQueryString = "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0) ,coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0), coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN (" + finalSitesNameList + ") and b.AREA_NAME LIKE '%"
											+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions
											+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t group by t.site_name,t.area_id order by t.site_name asc ";

									timeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\",coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\",coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
											+ date
											+ "' as \"Date\" from (SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN (" + finalSitesNameList + ") and b.AREA_NAME LIKE '%"
											+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions
											+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t order by t.site_name asc";

									pieRevenueChartQueryString = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
											+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN (" + finalSitesNameList + ")and b.AREA_NAME LIKE '%"
											+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions
											+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t order by t.site_name asc )";

								} // end tech selected

								siteRevenueChartQuery = session.createNativeQuery(siteRevenueChartQueryString);
								siteRevenueChartQuery.setParameter("param1", StartDate);
								siteRevenueChartQuery.setParameter("param2", EndDate);
								chartJSNArr = chartRevenues(siteRevenueChartQuery.list());

								if (siteRevenueChartQuery.list().size() > 0) {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
								} else {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								}

								pieRevenueChartQuery = session.createNativeQuery(pieRevenueChartQueryString);
								pieRevenueChartQuery.setParameter("param1", StartDate);
								pieRevenueChartQuery.setParameter("param2", EndDate);

								if (pieRevenueChartQuery.list().size() > 0) {

									rtn.put("pieRevenue", mapper.writeValueAsString(pieRevenueChartQuery.list()));
								} else {
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
								}

								for (i = 0; i < diff; i++) {

									weeklyEndDate = weeklyStartDate;
									cal.setTime(weeklyEndDate);
									cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									// weeklyEndDate = formatter.parse(weeklyEndDate);
									weeklyEndDate = new Timestamp(weeklyDate.getTime());
									// System.out.println("for week " + i + ":" + weeklyEndDate);

									if ((weeklyEndDate).compareTo(EndDate) > 0) {
										System.out.println("the weeklyEndDate is greater than enddate");
										weeklyEndDate = EndDate;
										// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth() +1) +"-"
										// + (EndDate.getDate()) +"/"+ (EndDate.getMonth() +1);

									}
									// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
									// +1)+"/"+(weeklyStartDate.getYear()+1900)+"-"+ (weeklyEndDate.getDate())+"/"+
									// (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getYear()+1900);
									date = (weeklyStartDate.getYear() + 1900) + "/" + (weeklyStartDate.getMonth() + 1)
											+ "/" + (weeklyStartDate.getDate()) + "-" + (weeklyEndDate.getYear() + 1900)
											+ "/" + (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

									timeRevenueStackandLineChartQuery = session
											.createNativeQuery(timeRevenueStackandLineChartQueryString);
									timeRevenueStackandLineChartQuery.setParameter("param1", weeklyStartDate);
									timeRevenueStackandLineChartQuery.setParameter("param2", weeklyEndDate);
									revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) timeRevenueStackandLineChartQuery)
											.addScalar("voiceRevenue").addScalar("smsRevenue").addScalar("dataRevneue")
											.addScalar("vasRevenue").addScalar("totalRevenue").addScalar("Date")
											.setResultTransformer(Transformers.aliasToBean(RevenueChartsReport.class))
											.list();
									revenueCharts.get(0).setDate(date);
									// System.out.println("for week " + i + " the date is :" + date);
									// System.out.println("the
									// revenueChartsList"+mapper.writeValueAsString(revenueCharts));
									revenueChartList.addAll(revenueCharts);

									weeklyStartDate = weeklyEndDate;
									cal.setTime(weeklyStartDate);
									cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									weeklyStartDate = new Timestamp(weeklyDate.getTime());

									// System.out.println("after start date " + weeklyStartDate);

								}

								periodicData = getPeriodicChartData(revenueChartList);
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(periodicData));

								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));

							}

							// Max or Min is checked
							else {
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("pieRevenue", mapper.writeValueAsString(""));
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));

								double diff = (EndDate.getTime() - StartDate.getTime()) / 1000.0;
								diff /= (60 * 60 * 24 * 7);
								diff = Math.abs(Math.ceil(diff));

								String date = "";
								Date weeklyDate;
								Timestamp weeklyStartDate = StartDate;
								Timestamp weeklyEndDate = StartDate;
								Calendar cal = Calendar.getInstance();
								Timestamp weeklyStrtDate = StartDate;

								for (i = 0; i < diff; i++) {

									weeklyEndDate = weeklyStartDate;
									cal.setTime(weeklyEndDate);
									cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									weeklyEndDate = new Timestamp(weeklyDate.getTime());

									// System.out.println("for week " + i + ":" + weeklyEndDate);

									if ((weeklyEndDate).compareTo(EndDate) > 0) {
										// System.out.println("the weeklyEndDate is greater than enddate");
										weeklyEndDate = EndDate;
									}

									// Tech is not selected
									if (finalSiteTechList == "") {

										// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
										if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, null)) {

											maxTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ("
													+ finalSitesNameList + " ) and AREA_NAME LIKE '%" + area
													+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

										}
										// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											minTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ("
													+ finalSitesNameList + " ) and AREA_NAME LIKE '%" + area
													+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

										}

										else if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											maxTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ("
													+ finalSitesNameList + " ) and AREA_NAME LIKE '%" + area
													+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											minTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ("
													+ finalSitesNameList + " ) and AREA_NAME LIKE '%" + area
													+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

										}
									} // end null tech
									// if tech is selected
									else if (finalSiteTechList != "") {

										// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
										if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											maxTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
													+ date
													+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and b.site_name IN (" + finalSitesNameList
													+ " ) and b.AREA_NAME LIKE '%" + area
													+ "%' and b.REGION_NAME LIKE '%" + technologyRegions
													+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
													+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

											minTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
													+ date
													+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and b.site_name IN (" + finalSitesNameList
													+ " ) and b.AREA_NAME LIKE '%" + area
													+ "%' and b.REGION_NAME LIKE '%" + technologyRegions
													+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
													+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										}
										// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, null)) {

											maxTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
													+ date
													+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and b.site_name IN (" + finalSitesNameList
													+ " ) and b.AREA_NAME LIKE '%" + area
													+ "%' and b.REGION_NAME LIKE '%" + technologyRegions
													+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
													+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										}
										// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											minTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
													+ date
													+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and b.site_name IN (" + finalSitesNameList
													+ " ) and b.AREA_NAME LIKE '%" + area
													+ "%' and b.REGION_NAME LIKE '%" + technologyRegions
													+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
													+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										}

									} // end selected tech

									// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
									// +1)+"/"+(weeklyStartDate.getYear()+1900)+"-"+ (weeklyEndDate.getDate())+"/"+
									// (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getYear()+1900);
									date = (weeklyStartDate.getYear() + 1900) + "/" + (weeklyStartDate.getMonth() + 1)
											+ "/" + (weeklyStartDate.getDate()) + "-" + (weeklyEndDate.getYear() + 1900)
											+ "/" + (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

									if (StringUtils.equalsIgnoreCase(max, "Max")) {

										maxTimeRevenueStackandLineChartQuery = session
												.createNativeQuery(maxTimeRevenueStackandLineChartQueryString);
										maxTimeRevenueStackandLineChartQuery.setParameter("param1", weeklyStartDate);
										maxTimeRevenueStackandLineChartQuery.setParameter("param2", weeklyEndDate);
										maxRevenueCharts = (List<RevenueChartsReport>) ((SQLQuery) maxTimeRevenueStackandLineChartQuery)
												.addScalar("voiceRevenue").addScalar("smsRevenue")
												.addScalar("dataRevneue").addScalar("vasRevenue")
												.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
														Transformers.aliasToBean(RevenueChartsReport.class))
												.list();
										maxRevenueCharts.get(0).setDate(date);
										maxRevenueChartList.addAll(maxRevenueCharts);

									}

									if (StringUtils.equalsIgnoreCase(min, "Min")) {

										minTimeRevenueStackandLineChartQuery = session
												.createNativeQuery(minTimeRevenueStackandLineChartQueryString);
										minTimeRevenueStackandLineChartQuery.setParameter("param1", weeklyStartDate);
										minTimeRevenueStackandLineChartQuery.setParameter("param2", weeklyEndDate);
										minRevenueCharts = (List<RevenueChartsReport>) ((SQLQuery) minTimeRevenueStackandLineChartQuery)
												.addScalar("voiceRevenue").addScalar("smsRevenue")
												.addScalar("dataRevneue").addScalar("vasRevenue")
												.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
														Transformers.aliasToBean(RevenueChartsReport.class))
												.list();
										minRevenueCharts.get(0).setDate(date);
										minRevenueChartList.addAll(minRevenueCharts);

									}

									weeklyStartDate = weeklyEndDate;
									cal.setTime(weeklyStartDate);
									cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
									weeklyDate = cal.getTime();
									weeklyStartDate = new Timestamp(weeklyDate.getTime());

								}

								if (maxRevenueChartList.size() > 0) {
									maxPeriodicData = getPeriodicChartData(maxRevenueChartList);
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(maxPeriodicData));
								} else {
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
								}

								if (minRevenueChartList.size() > 0) {
									minPeriodicData = getPeriodicChartData(minRevenueChartList);
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(minPeriodicData));
								} else {
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
								}

								if (StringUtils.equalsIgnoreCase(min, null)) {
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
								}
								if (StringUtils.equalsIgnoreCase(max, null)) {
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
								}

							} // end else max or min is checked

						} // ended weekly

						// start of monthly
						if (StringUtils.equalsIgnoreCase(period, "Monthly")) {
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {

								Calendar m = Calendar.getInstance();
								m.setTime(StartDate);
								int nMonth1 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
								m.setTime(EndDate);
								int nMonth2 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
								int diff = Math.abs(nMonth2 - nMonth1);

								Timestamp month = StartDate;
								String date = "";

								// Tech is not selected
								if (finalSiteTechList == "") {

									siteRevenueChartQueryString = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ("
											+ finalSitesNameList + " )  " + " and AREA_NAME LIKE '%" + area
											+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' " + queryRange
											+ " group by site_name, area_id";

									timeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
											+ date
											+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2  "
											+ "and t.AREA_NAME LIKE '%" + area + "%' and t.REGION_NAME LIKE '%"
											+ technologyRegions + "%'  AND t.site_name IN (" + finalSitesNameList + ") "
											+ queryRange + "";

									pieRevenueChartQueryString = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
											+ "and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%"
											+ technologyRegions + "%' AND site_name IN (" + finalSitesNameList + ")  "
											+ queryRange + "";

								} // end null tech

								// if there is a technology
								else if (finalSiteTechList != "") {

									siteRevenueChartQueryString = "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0) ,coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0), coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN (" + finalSitesNameList + ") and b.AREA_NAME LIKE '%"
											+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions
											+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t group by t.site_name,t.area_id order by t.site_name asc ";

									timeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\",coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\",coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
											+ date
											+ "' as \"Date\" from (SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech from( "
											+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN (" + finalSitesNameList + ") and b.AREA_NAME LIKE '%"
											+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions
											+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t order by t.site_name asc";

									pieRevenueChartQueryString = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
											+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, AREA_ID ,revenue_date,"
											+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
											+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
											+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN (" + finalSitesNameList + ")and b.AREA_NAME LIKE '%"
											+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions
											+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t order by t.site_name asc )";

								} // end selected tech

								siteRevenueChartQuery = session.createNativeQuery(siteRevenueChartQueryString);
								siteRevenueChartQuery.setParameter("param1", StartDate);
								siteRevenueChartQuery.setParameter("param2", EndDate);
								chartJSNArr = chartRevenues(siteRevenueChartQuery.list());

								if (siteRevenueChartQuery.list().size() > 0) {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
								} else {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								}

								pieRevenueChartQuery = session.createNativeQuery(pieRevenueChartQueryString);
								pieRevenueChartQuery.setParameter("param1", StartDate);
								pieRevenueChartQuery.setParameter("param2", EndDate);

								if (pieRevenueChartQuery.list().size() > 0) {

									rtn.put("pieRevenue", mapper.writeValueAsString(pieRevenueChartQuery.list()));
								} else {
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
								}

								for (i = 0; i <= diff; i++) {

									String startmonth = formatter.format(month);
									Date monthDate = formatter.parse(startmonth);
									Timestamp monthofDate = new Timestamp(monthDate.getTime());

									Calendar calendar = Calendar.getInstance();
									calendar.setTime(monthofDate);
									calendar.add(Calendar.MONTH, 1);
									calendar.set(Calendar.DAY_OF_MONTH, 1);
									calendar.add(Calendar.DATE, -1);
									Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

									String endMonth = formatter.format(lastDayOfMonth);
									Date endOfDate = formatter.parse(endMonth);
									Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
									// System.out.println("for month " + i + ":" + endOfMonth);

									if ((endOfMonth).compareTo(EndDate) > 0) {
										System.out.println("the endOfMonth is greater than enddate");
										endOfMonth = EndDate;

									}
									// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1)
									// +"/"+(monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
									// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
									date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1) + "/"
											+ (monthofDate.getDate()) + "-" + (endOfMonth.getYear() + 1900) + "/"
											+ (endOfMonth.getMonth() + 1) + "/" + (endOfMonth.getDate());

									timeRevenueStackandLineChartQuery = session
											.createNativeQuery(timeRevenueStackandLineChartQueryString);
									timeRevenueStackandLineChartQuery.setParameter("param1", monthofDate);
									timeRevenueStackandLineChartQuery.setParameter("param2", endOfMonth);
									revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) timeRevenueStackandLineChartQuery)
											.addScalar("voiceRevenue").addScalar("smsRevenue").addScalar("dataRevneue")
											.addScalar("vasRevenue").addScalar("totalRevenue").addScalar("Date")
											.setResultTransformer(Transformers.aliasToBean(RevenueChartsReport.class))
											.list();
									revenueCharts.get(0).setDate(date);
									// System.out.println("for week " + i + " the date is :" + date);
									// System.out.println("the
									// revenueChartsList"+mapper.writeValueAsString(revenueCharts));
									revenueChartList.addAll(revenueCharts);

									calendar.setTime(endOfMonth);
									calendar.add(Calendar.DATE, +1);
									Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
									month = NextDayOfMonth;
									// System.out.println("after start date " + NextDayOfMonth);

								}

								periodicData = getPeriodicChartData(revenueChartList);
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(periodicData));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));

							} // end null max/min

							// Max or Min is checked
							else {
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("pieRevenue", mapper.writeValueAsString(""));
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));

								Calendar m = Calendar.getInstance();
								m.setTime(StartDate);
								int nMonth1 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
								m.setTime(EndDate);
								int nMonth2 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
								int diff = Math.abs(nMonth2 - nMonth1);

								Timestamp month = StartDate;
								String date = "";

								for (i = 0; i <= diff; i++) {

									String startmonth = formatter.format(month);
									Date monthDate = formatter.parse(startmonth);
									Timestamp monthofDate = new Timestamp(monthDate.getTime());

									Calendar calendar = Calendar.getInstance();
									calendar.setTime(monthofDate);
									calendar.add(Calendar.MONTH, 1);
									calendar.set(Calendar.DAY_OF_MONTH, 1);
									calendar.add(Calendar.DATE, -1);
									Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

									String endMonth = formatter.format(lastDayOfMonth);
									Date endOfDate = formatter.parse(endMonth);
									Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
									// System.out.println("for month " + i + ":" + endOfMonth);

									if ((endOfMonth).compareTo(EndDate) > 0) {
										// System.out.println("the endOfMonth is greater than enddate");
										endOfMonth = EndDate;
									}

									if (finalSiteTechList == "") {

										// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
										if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, null)) {

											maxTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ("
													+ finalSitesNameList + " ) and AREA_NAME LIKE '%" + area
													+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

										}
										// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											minTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ("
													+ finalSitesNameList + " ) and AREA_NAME LIKE '%" + area
													+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

										}
										// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											maxTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ("
													+ finalSitesNameList + " ) and AREA_NAME LIKE '%" + area
													+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											minTimeRevenueStackandLineChartQueryString = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ("
													+ finalSitesNameList + " ) and AREA_NAME LIKE '%" + area
													+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

										}

									} // end null tech

									// if there is a technology
									else if (finalSiteTechList != "") {

										// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
										if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, null)) {

											maxTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
													+ date
													+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and b.site_name IN (" + finalSitesNameList
													+ " ) and b.AREA_NAME LIKE '%" + area
													+ "%' and b.REGION_NAME LIKE '%" + technologyRegions
													+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
													+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										}

										// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, null)
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											minTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
													+ date
													+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and b.site_name IN (" + finalSitesNameList
													+ " ) and b.AREA_NAME LIKE '%" + area
													+ "%' and b.REGION_NAME LIKE '%" + technologyRegions
													+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
													+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										}
										// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
										else if (StringUtils.equalsIgnoreCase(max, "Max")
												&& StringUtils.equalsIgnoreCase(min, "Min")) {

											maxTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
													+ date
													+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and b.site_name IN (" + finalSitesNameList
													+ " ) and b.AREA_NAME LIKE '%" + area
													+ "%' and b.REGION_NAME LIKE '%" + technologyRegions
													+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
													+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

											minTimeRevenueStackandLineChartQueryString = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
													+ date
													+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
													+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and b.site_name IN (" + finalSitesNameList
													+ " ) and b.AREA_NAME LIKE '%" + area
													+ "%' and b.REGION_NAME LIKE '%" + technologyRegions
													+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
													+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										}
									} // end selected tech

									// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1)
									// +"/"+(monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
									// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
									date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1) + "/"
											+ (monthofDate.getDate()) + "-" + (endOfMonth.getYear() + 1900) + "/"
											+ (endOfMonth.getMonth() + 1) + "/" + (endOfMonth.getDate());

									if (StringUtils.equalsIgnoreCase(max, "Max")) {
										maxTimeRevenueStackandLineChartQuery = session
												.createNativeQuery(maxTimeRevenueStackandLineChartQueryString);
										maxTimeRevenueStackandLineChartQuery.setParameter("param1", monthofDate);
										maxTimeRevenueStackandLineChartQuery.setParameter("param2", endOfMonth);
										maxRevenueCharts = (List<RevenueChartsReport>) ((SQLQuery) maxTimeRevenueStackandLineChartQuery)
												.addScalar("voiceRevenue").addScalar("smsRevenue")
												.addScalar("dataRevneue").addScalar("vasRevenue")
												.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
														Transformers.aliasToBean(RevenueChartsReport.class))
												.list();
										maxRevenueCharts.get(0).setDate(date);
										maxRevenueChartList.addAll(maxRevenueCharts);

									}

									if (StringUtils.equalsIgnoreCase(min, "Min")) {
										minTimeRevenueStackandLineChartQuery = session
												.createNativeQuery(minTimeRevenueStackandLineChartQueryString);
										minTimeRevenueStackandLineChartQuery.setParameter("param1", monthofDate);
										minTimeRevenueStackandLineChartQuery.setParameter("param2", endOfMonth);
										minRevenueCharts = (List<RevenueChartsReport>) ((SQLQuery) minTimeRevenueStackandLineChartQuery)
												.addScalar("voiceRevenue").addScalar("smsRevenue")
												.addScalar("dataRevneue").addScalar("vasRevenue")
												.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
														Transformers.aliasToBean(RevenueChartsReport.class))
												.list();
										minRevenueCharts.get(0).setDate(date);
										minRevenueChartList.addAll(minRevenueCharts);
									}

									calendar.setTime(endOfMonth);
									calendar.add(Calendar.DATE, +1);
									Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
									month = NextDayOfMonth;

								}

								if (maxRevenueChartList.size() > 0) {
									maxPeriodicData = getPeriodicChartData(maxRevenueChartList);
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(maxPeriodicData));
								} else {
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
								}

								if (minRevenueChartList.size() > 0) {
									minPeriodicData = getPeriodicChartData(minRevenueChartList);
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(minPeriodicData));
								} else {
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
								}

								if (StringUtils.equalsIgnoreCase(min, null)) {
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
								}
								if (StringUtils.equalsIgnoreCase(max, null)) {
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
								}
							} // end else max or min is checked

						} /// END OF MONTLY

						// start of acc or absolute max or min
						if (StringUtils.equalsIgnoreCase(period, "Accu")
								|| StringUtils.equalsIgnoreCase(period, "null")) {
							// System.out.println("acccccc");
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
								// Tech is not selected
								if (finalSiteTechList == "") {

									// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
									/*
									 * if (StringUtils.equalsIgnoreCase(max, "Max") &&
									 * StringUtils.equalsIgnoreCase(min, "Min")) { siteRevenueChartQueryString =
									 * "select site_name,AREA_ID,sum(voice_rev),sum(sms_rev),sum(data_rev),sum(vas_rev),sum(rev_sum) from("
									 * +
									 * "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum desc)" +
									 * " ) where row_nb = 1 " + queryRange + "" + "UNION (" +
									 * "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum asc)" +
									 * " ) where row_nb = 1 " + queryRange +
									 * ")) group by site_name,AREA_ID order by site_name asc";
									 * 
									 * timeRevenueStackandLineChartQueryString =
									 * "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from ("
									 * +
									 * "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,(sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum desc)" +
									 * " ) where row_nb = 1 " + queryRange + "" + "UNION (" +
									 * "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,(sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum asc)" +
									 * " ) where row_nb = 1 " + queryRange + "))";
									 * 
									 * pieRevenueChartQueryString =
									 * "select sum(tot_voice),sum(tot_sms),sum(tot_data),sum(tot_vas),sum(tot_revenue) from ( select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum desc)" +
									 * " ) where row_nb = 1 " + queryRange + "" + "UNION (" +
									 * "select coalesce(sum(voice_rev),0) as tot_voice,coalesce(sum(sms_rev),0) as tot_sms,coalesce(sum(data_rev),0) as tot_data,coalesce(sum(vas_rev),0) as tot_vas,coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum asc)" +
									 * " ) where row_nb = 1 " + queryRange + "))"; } // IF MAX IS ONLY CHECKED
									 * WITHOUT TECHNOLOGY if (StringUtils.equalsIgnoreCase(max, "Max") &&
									 * StringUtils.equalsIgnoreCase(min, null)) { siteRevenueChartQueryString =
									 * "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum desc)" +
									 * " ) where row_nb = 1 " + queryRange + " ";
									 * 
									 * timeRevenueStackandLineChartQueryString =
									 * "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),(sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum desc)" +
									 * " ) where row_nb = 1 " + queryRange + " ";
									 * 
									 * pieRevenueChartQueryString =
									 * "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum desc)" +
									 * " ) where row_nb = 1 " + queryRange + " "; } // IF MIN IS ONLY CHECKED
									 * WITHOUT TECHNOLOGY if (StringUtils.equalsIgnoreCase(max, null) &&
									 * StringUtils.equalsIgnoreCase(min, "Min")) { siteRevenueChartQueryString =
									 * "select site_name,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev,rev_sum from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum asc)" +
									 * " ) where row_nb = 1 " + queryRange + " ";
									 * 
									 * timeRevenueStackandLineChartQueryString =
									 * "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),(sum(voice_rev)+sum(sms_rev)+sum(data_rev)+sum(vas_rev)) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum asc)" +
									 * " ) where row_nb = 1 " + queryRange + " ";
									 * 
									 * pieRevenueChartQueryString =
									 * "select coalesce(sum(voice_rev),0),coalesce(sum(sms_rev),0),coalesce(sum(data_rev),0),coalesce(sum(vas_rev),0),coalesce(sum(voice_rev+sms_rev+data_rev+vas_rev),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_rev,sms_rev,data_rev,vas_rev, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_rev ,sum(sms_revenue)as sms_rev,sum(data_revenue)as data_rev ,sum(vas_revenue)as vas_rev ,site_name,AREA_ID from "
									 * +
									 * "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name LIKE '%"
									 * + site + "%' and AREA_NAME LIKE '%" + area + "%' and REGION_NAME LIKE '%" +
									 * technologyRegions + "%' group by site_name,area_id order by rev_sum asc)" +
									 * " ) where row_nb = 1 " + queryRange + " "; }
									 */
									// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {
										siteRevenueChartQueryString = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
												+ finalSitesNameList + " ) " + " and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' " + queryRange
												+ " group by site_name, area_id ORDER BY site_name ASC";
										/*
										 * timeRevenueStackandLineChartQueryString =
										 * "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
										 * +date+"' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name LIKE '%"
										 * + site + "%' and t.AREA_NAME LIKE '%" + area + "%' and t.REGION_NAME LIKE '%"
										 * + technologyRegions + "%' " + queryRange + "";
										 */
										pieRevenueChartQueryString = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
												+ finalSitesNameList + " ) " + " and AREA_NAME LIKE '%" + area
												+ "%' and REGION_NAME LIKE '%" + technologyRegions + "%' " + queryRange
												+ "";

									}

								}
								// if there is a technology
								else if (finalSiteTechList != "") {
									// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
									/*
									 * if(StringUtils.equalsIgnoreCase(max,"Max") &&
									 * StringUtils.equalsIgnoreCase(min,"Min")) { siteRevenueChartQueryString=
									 * "select site_name,area_id,coalesce(sum(voice_revenue),0),coalesce(sum(sms_revenue),0),coalesce(sum(data_revenue),0),coalesce(sum(vas_revenue),0),coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from ("
									 * +
									 * "select t.site_name,t.area_id, t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)"
									 * + ") t where row_nb = 1 " + " UNION ("+
									 * "select t.site_name,t.area_id, t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)"
									 * +
									 * ") t  where row_nb = 1 ) order by 1 asc) group by site_name,AREA_ID order by site_name"
									 * ;
									 * 
									 * timeRevenueStackandLineChartQueryString=
									 * "  select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0),coalesce(sum(tot_data),0),coalesce(sum(tot_vas),0),coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from ("
									 * +
									 * "	select  t.voice_revenue as tot_voice, t.sms_revenue as tot_sms,t.data_revenue as tot_data, t.vas_revenue as tot_vas from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)"
									 * + ") t where row_nb = 1 " + " UNION ("+
									 * "select  t.voice_revenue as tot_voice, t.sms_revenue as tot_sms,t.data_revenue as tot_data, t.vas_revenue as tot_vas from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)"
									 * + ") t  where row_nb = 1 ))";
									 * 
									 * pieRevenueChartQueryString=
									 * "  select coalesce(sum(voice_revenue),0) as tot_voice,coalesce(sum(sms_revenue),0) as tot_sms,coalesce(sum(data_revenue),0) as tot_data,coalesce(sum(vas_revenue),0) as tot_vas,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_rev from ("
									 * +
									 * "	select  t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)"
									 * + ") t where row_nb = 1 " + " UNION ("+
									 * "select  t.voice_revenue, t.sms_revenue,t.data_revenue, t.vas_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)"
									 * + ") t  where row_nb = 1 ))";
									 * 
									 * } // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
									 * if(StringUtils.equalsIgnoreCase(max,"Max") &&
									 * StringUtils.equalsIgnoreCase(min,null)) {
									 * //'"+weeklyStartDate+"','"+weeklyEndDate+"',
									 * 
									 * siteRevenueChartQueryString=
									 * "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)"
									 * +
									 * ") t where row_nb = 1 group by t.site_name,t.area_id order by  t.site_name asc"
									 * ;
									 * 
									 * timeRevenueStackandLineChartQueryString=
									 * "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)"
									 * + ") t where row_nb = 1 order by t.site_name asc";
									 * 
									 * pieRevenueChartQueryString=
									 * "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum desc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum desc)"
									 * + ") t where row_nb = 1 order by t.site_name asc";
									 * 
									 * } // IF MIN IS ONLY CHECKED WITH TECHNOLOGY
									 * if(StringUtils.equalsIgnoreCase(max,null) &&
									 * StringUtils.equalsIgnoreCase(min,"Min")) { siteRevenueChartQueryString=
									 * "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)"
									 * +
									 * ") t where row_nb = 1 group by t.site_name,t.area_id order by  t.site_name asc"
									 * ;
									 * 
									 * timeRevenueStackandLineChartQueryString=
									 * "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)"
									 * + ") t where row_nb = 1 order by t.site_name asc";
									 * 
									 * pieRevenueChartQueryString=
									 * "select coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0),coalesce(sum(t.data_revenue),0), coalesce(sum(t.vas_revenue),0),coalesce(sum(t.voice_revenue+t.sms_revenue+t.data_revenue+t.vas_revenue),0) from ( "
									 * +
									 * "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, ROW_NUMBER() over(order by rev_sum asc) as row_nb from "
									 * +
									 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
									 * +
									 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
									 * +site+"%' and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and "+ paramTechnologies+ " "+
									 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id order by rev_sum asc)"
									 * + ") t where row_nb = 1 order by t.site_name asc"; }
									 */
									// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {
										siteRevenueChartQueryString = "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0) ,coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0), coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ") and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t group by t.site_name,t.area_id order by t.site_name asc ";

										/*
										 * timeRevenueStackandLineChartQueryString =
										 * "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\",coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\",coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
										 * +date+"' as \"Date\" from (" + " SELECT DISTINCT a.WARE_NAME,a.SITE_ID," +
										 * "b.site_name as site_name,b.area_id as AREA_ID , " +
										 * "sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  "
										 * +
										 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
										 * + "and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"
										 * +area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+
										 * paramTechnologies+ " "+
										 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id) t order by t.site_name asc"
										 * ;
										 */
										pieRevenueChartQueryString = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
												+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ")and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
												+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t order by t.site_name asc )";
									}

								}

								siteRevenueChartQuery = session.createNativeQuery(siteRevenueChartQueryString);
								siteRevenueChartQuery.setParameter("param1", StartDate);
								siteRevenueChartQuery.setParameter("param2", EndDate);
								chartJSNArr = chartRevenues(siteRevenueChartQuery.list());

								pieRevenueChartQuery = session.createNativeQuery(pieRevenueChartQueryString);
								pieRevenueChartQuery.setParameter("param1", StartDate);
								pieRevenueChartQuery.setParameter("param2", EndDate);

								if (siteRevenueChartQuery.list().size() > 0) {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
								} else {
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								}
								// System.out.println("the pie rev "+mapper.writeValueAsString(pieRev));
								if (pieRevenueChartQuery.list().size() > 0) {

									rtn.put("pieRevenue", mapper.writeValueAsString(pieRevenueChartQuery.list()));
								} else {
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
								}
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
							} else {
								rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
								rtn.put("pieRevenue", mapper.writeValueAsString(""));
								rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
								rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
							}
						} // END OF ACC

					}

					////// last two charts
					if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
						// no technology is selected
						if (finalSiteTechList == "") {

							// NO MAX AND MIN ONLY CHECKED WITH TECHNOLOGY
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {

								if (finalSitesNameList != "") {

									pieRevenueTechChartQueryString = "select t.combination_tech , count(distinct t.site_name)  ,sum(t.sms_revenue+t.data_revenue+t.voice_revenue+t.vas_revenue) FROM( "
											+ "SELECT combination_tech,revenue_date, site_name, AREA_ID , sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from("
											+ " SELECT DISTINCT b.site_name as site_name,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN (" + finalSitesNameList + ") and b.AREA_NAME LIKE '%"
											+ area + "%' and b.REGION_NAME LIKE '%" + technologyRegions
											+ "%' and a.combination_technology In ('2G_3G_4G','2G_3G','2G') "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t  GROUP BY t.combination_tech  ";

									// pieRevenueTechChartQueryString = "select a.combination_technology as tech,
									// count(distinct a.ware_name) as sites
									// ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as
									// Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON
									// a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <=
									// :param2 "
									// + "and b.site_name IN ( "+finalSitesNameList+" ) and b.AREA_NAME LIKE '%" +
									// area + "%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and
									// a.combination_technology In ('2G_3G_4G','2G_3G','2G') group by
									// a.combination_technology order by a.combination_technology asc";
								} else {

									pieRevenueTechChartQueryString = "select t.combination_tech , count(distinct t.site_name)  ,sum(t.sms_revenue+t.data_revenue+t.voice_revenue+t.vas_revenue) FROM( "
											+ "SELECT combination_tech,revenue_date, site_name, AREA_ID , sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from("
											+ " SELECT DISTINCT b.site_name as site_name,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions
											+ "%' and a.combination_technology In ('2G_3G_4G','2G_3G','2G') "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t  GROUP BY t.combination_tech  ";

									// pieRevenueTechChartQueryString = "select a.combination_technology as tech,
									// count(distinct a.ware_name) as sites
									// ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as
									// Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON
									// a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <=
									// :param2 and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE
									// '%"+technologyRegions+"%' and a.combination_technology In
									// ('2G_3G_4G','2G_3G','2G') group by a.combination_technology order by
									// a.combination_technology asc";

								}
							}

						}
						// if there is a technology
						else if (finalSiteTechList != "") {

							/*
							 * // IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
							 * if(StringUtils.equalsIgnoreCase(max,"Max") &&
							 * StringUtils.equalsIgnoreCase(min,"Min")) { pieRevenueTechChartQueryString =
							 * "select technology,sum_total,totalRevSum from ( select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE desc) as row_nb from ( select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
							 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
							 * +technologyRegions+"%' and a.combination_technology " +
							 * paramTechnologies+" group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
							 * +" UNION "+
							 * "select technology,sum_total,totalRevSum from ( select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE asc) as row_nb from ( select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
							 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
							 * +technologyRegions+"%' and a.combination_technology " +
							 * paramTechnologies+" group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
							 * ;
							 * 
							 * } // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
							 * if(StringUtils.equalsIgnoreCase(max,"Max") &&
							 * StringUtils.equalsIgnoreCase(min,null)) { //pieRevenueTechChartQueryString =
							 * "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from( select tech as technology, sites as sum_total,Max(Total_REVENUE) as totalRevSum,date_rev from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev,b.SITE_NAME,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
							 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
							 * +technologyRegions+"%' and a.combination_technology " +
							 * paramTechnologies+" group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc) group by tech, sites, date_rev) group by technology"
							 * ; pieRevenueTechChartQueryString =
							 * "select technology,sum_total,totalRevSum from ( select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE desc) as row_nb from ( select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
							 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
							 * +technologyRegions+"%' and a.combination_technology " +
							 * paramTechnologies+" group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
							 * ; } // IF MIN IS ONLY CHECKED WITH TECHNOLOGY
							 * if(StringUtils.equalsIgnoreCase(max,null) &&
							 * StringUtils.equalsIgnoreCase(min,"Min")) { //pieRevenueTechChartQueryString =
							 * "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from( select tech as technology, sites as sum_total,Min(Total_REVENUE) as totalRevSum,date_rev from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev,b.SITE_NAME,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
							 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
							 * +technologyRegions+"%' and a.combination_technology " +
							 * paramTechnologies+" group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc) group by tech, sites, date_rev) group by technology"
							 * ; pieRevenueTechChartQueryString =
							 * "select technology,sum_total,totalRevSum from ( select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE asc) as row_nb from ( select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
							 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
							 * +technologyRegions+"%' and a.combination_technology " +
							 * paramTechnologies+" group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
							 * ; }
							 */
							// NO MAX AND MIN ONLY CHECKED WITH TECHNOLOGY
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
								if (finalSitesNameList != "") {

									pieRevenueTechChartQueryString = "select t.combination_tech , count(distinct t.site_name)  ,sum(t.sms_revenue+t.data_revenue+t.voice_revenue+t.vas_revenue) FROM( "
											+ "SELECT combination_tech,revenue_date, site_name, AREA_ID , sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from("
											+ " SELECT DISTINCT b.site_name as site_name,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.site_name IN (" + finalSitesNameList
											+ " ) and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.combination_technology In ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t  GROUP BY t.combination_tech  ";

									// pieRevenueTechChartQueryString = "select a.combination_technology as tech,
									// count(distinct a.ware_name) as sites
									// ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as
									// Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON
									// a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <=
									// :param2 and b.site_name IN ( "+finalSitesNameList+" ) and b.AREA_NAME LIKE
									// '%" + area + "%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and
									// a.combination_technology IN ( "+finalSiteTechList+" ) group by
									// a.combination_technology order by a.combination_technology asc";
								} else {
									/*
									 * pieRevenueTechChartQueryString =
									 * "select t.combination_tech , count(distinct t.site_name)  ,sum(t.sms_revenue+t.data_revenue+t.voice_revenue+t.vas_revenue) FROM( "
									 * +
									 * "SELECT combination_tech,site_name, AREA_ID , sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from("
									 * +
									 * " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
									 * +
									 * " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
									 * + " and b.AREA_NAME LIKE '%"+area+"%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and a.combination_technology In ('2G_3G_4G','2G_3G','2G') "
									 * +
									 * "  order by b.site_name) group by combination_tech, site_name, AREA_ID)t  GROUP BY t.combination_tech  "
									 * ;
									 */
									pieRevenueTechChartQueryString = "select t.combination_tech , count(distinct t.site_name)  ,sum(t.sms_revenue+t.data_revenue+t.voice_revenue+t.vas_revenue) FROM( "
											+ "SELECT combination_tech,revenue_date, site_name, AREA_ID , sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from("
											+ " SELECT DISTINCT b.site_name as site_name,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
											+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
											+ " and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
											+ technologyRegions + "%' and a.combination_technology In ("
											+ finalSiteTechList + " ) "
											+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t  GROUP BY t.combination_tech  ";

									/*
									 * pieRevenueTechChartQueryString =
									 * "select a.combination_technology as tech,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE "
									 * +
									 * "from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE"
									 * + " <= :param2  and b.AREA_NAME LIKE '%" + area +
									 * "%' and b.REGION_NAME LIKE '%"
									 * +technologyRegions+"%' and a.combination_technology IN ( "
									 * +finalSiteTechList+" ) group by a.combination_technology order by a.combination_technology asc"
									 * ;
									 */
								}

							}

						}

						pieRevenueTechChartQuery = session.createNativeQuery(pieRevenueTechChartQueryString);
						pieRevenueTechChartQuery.setParameter("param1", StartDate);
						pieRevenueTechChartQuery.setParameter("param2", EndDate);

						// System.out.println("The siteResult is: " +
						// mapper.writeValueAsString(siteResult));

						chartArray = new ArrayList<Float>(PieRevPerTech(pieRevenueTechChartQuery.list()));

						if (pieRevenueTechChartQuery.list().size() > 0) {

							rtn.put("listChartSites", mapper.writeValueAsString(pieRevenueTechChartQuery.list()));
							rtn.put("chartData", chartArray);
						} else {

							rtn.put("listChartSites", mapper.writeValueAsString(""));
							rtn.put("chartData", "");
						}
					} else {
						rtn.put("listChartSites", mapper.writeValueAsString(""));
						rtn.put("chartData", "");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Error in creating session with Database", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	// Filter Method Charts
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteringMethod", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteringMethod(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		int i = 0;
		String applyFilterRevSum = "";
		Query applyFilterQuery = null;
		String paramTechnologies = "";
		List<Object> periodicData = new ArrayList<Object>();
		List<RevenueChartsReport> revenueCharts = new ArrayList<RevenueChartsReport>();
		List<RevenueChartsReport> revenueChartList = new ArrayList<RevenueChartsReport>();
		List<RevenueChartsReport> revenueChartListFinal = new ArrayList<RevenueChartsReport>();

		JSONArray chartJSNArr;

		String area = request.getParameter("area");
		String technologyRegions = request.getParameter("technologyRegions");
		String Payment_Method = request.getParameter("Payment Method");
		// String technologies = request.getParameter("technologies");
		String period = request.getParameter("period");
		String max = request.getParameter("Max");
		String min = request.getParameter("Min");
		String revenueOption = request.getParameter("revenueOption");
		String selectedValue = request.getParameter("selectedValue");
		String minVal = request.getParameter("minVal");
		String maxVal = request.getParameter("maxVal");
		String filterID = request.getParameter("filterID");
		String queryRange;
		String queryyRange;

		if (selectedValue != null) {
			queryRange = " and " + revenueOption + " >= DECODE(" + minVal + ",null,0," + minVal + ") AND "
					+ revenueOption + " <= DECODE('" + maxVal + "','null',(select SUM(" + revenueOption
					+ ") from prepaid_payg_revenue)," + maxVal + ")";
			queryyRange = " where " + revenueOption + " >= DECODE(" + minVal + ",null,0," + minVal + ") AND "
					+ revenueOption + " <= DECODE('" + maxVal + "','null',(select SUM(" + revenueOption
					+ ") from prepaid_payg_revenue)," + maxVal + ")";
		} else {
			queryRange = "";
			queryyRange = "";

		}

		String siteTech = request.getParameter("selectedTech");
		String finalSiteTechList = "";
		String[] siteTechList = null;
		if (StringUtils.equalsIgnoreCase(siteTech, "") || StringUtils.equalsIgnoreCase(siteTech, null)) {
			finalSiteTechList = "";
		} else {
			String allSiteTech = siteTech.concat(",");
			// Split the string of technologies on comma to get each technology separately
			siteTechList = allSiteTech.split(",");
			StringBuilder allSiteTechs = new StringBuilder();

			// Append to each element '' and comma to satisfy the format of IN parameters
			for (int h = 0; h < siteTechList.length; h++) {

				allSiteTechs.append("'" + siteTechList[h] + "',");
			}

			finalSiteTechList = allSiteTechs.toString();
			finalSiteTechList = finalSiteTechList.substring(0, finalSiteTechList.length() - 1);

		}

		String nullSite = request.getParameter("nullSite");
		// All sites name from popup
		String sitesName = request.getParameter("allSitesNameChart");

		String finalSitesNameList = "";
		String[] sitesNameList = null;

		if (StringUtils.equalsIgnoreCase(sitesName, "") || StringUtils.equalsIgnoreCase(sitesName, null)) {
			finalSitesNameList = "";
		} else {
			String allSitesName = sitesName.concat(",");

			// Split the string of sites on space-comma to get each site separately
			sitesNameList = allSitesName.split(" ,");

			StringBuilder allSitesNames = new StringBuilder();

			// Append to each element '' and comma to satisfy the format of IN parameters
			for (int x = 0; x < sitesNameList.length; x++) {

				allSitesNames.append("'" + sitesNameList[x] + "',");
			}

			finalSitesNameList = allSitesNames.toString();
			finalSitesNameList = finalSitesNameList.substring(0, finalSitesNameList.length() - 1);

		}
		String start_Date = request.getParameter("startDate");
		String end_Date = request.getParameter("endDate");
		Date startDate = null;
		Date endDate = null;
		Timestamp StartDate;
		Timestamp EndDate;

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			Session session = null;
			Transaction tx = null;

			try {

				startDate = formatter.parse(start_Date);
				StartDate = new Timestamp(startDate.getTime());
				endDate = formatter.parse(end_Date);
				EndDate = new Timestamp(endDate.getTime());

				session = RptDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();

					// Site name is not selected
					if (finalSitesNameList == "") {

						// period is daily
						if (StringUtils.equalsIgnoreCase(period, "Daily")) {

							System.out.println("/*/*Here site is not selected");
							// no technology is selected
							if (finalSiteTechList == "") {

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										switch (filterID) {

										case "applyButtStackChartMax":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ " group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;

										case "applyButtStackChartMin":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ " group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;

										case "applyButtLineChartMax":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ " group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;

										case "applyButtLineChartMin":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ " group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;

										}

									}

									// else select all not null sites name
									else {

										switch (filterID) {

										case "applyButtStackChartMax":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ " group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;

										case "applyButtStackChartMin":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;

										case "applyButtLineChartMax":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;

										case "applyButtLineChartMin":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;

										}
									}

								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										switch (filterID) {

										case "applyButtStackChartMax":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ " group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;
										case "applyButtLineChartMax":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ " group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;

										}
									}
									// else select all not null sites name
									else {
										switch (filterID) {

										case "applyButtStackChartMax":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name != 'null' "
													+ " group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;
										case "applyButtLineChartMax":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by REVENUE_DATE, site_name,area_id order by rev_sum desc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;

										}
									}
								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										switch (filterID) {

										case "applyButtStackChartMin":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ " group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;
										case "applyButtLineChartMin":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ " group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;

										}

									}
									// else select all not null sites name
									else {
										switch (filterID) {

										case "applyButtStackChartMin":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;
										case "applyButtLineChartMin":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate,sum(voice_revenue),sum(sms_revenue),sum(data_revenue),sum(vas_revenue),(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from ( "
													+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue ,revenue_date,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by REVENUE_DATE, site_name,area_id order by rev_sum asc)"
													+ " ) where RANK = 1 " + queryRange
													+ " group by  revenue_date order by revenue_date desc";

											break;

										}
									}
								}
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										switch (filterID) {
										case "applyButtMSChart":
											applyFilterRevSum = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ queryRange
													+ " group by site_name, area_id order by site_name asc";
											break;
										case "applyButtStackChart":

											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ queryRange + " group by REVENUE_DATE order by REVENUE_DATE desc";

											break;
										case "applyButtLineChart":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ queryRange + " group by REVENUE_DATE order by REVENUE_DATE desc";
											break;
										case "applyButtPieChart":
											applyFilterRevSum = "select coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ queryRange + " ";

											break;
										}

									}
									// else select all not null sites name
									else {
										switch (filterID) {
										case "applyButtMSChart":
											applyFilterRevSum = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ queryRange
													+ " group by site_name, area_id order by site_name asc";
											break;
										case "applyButtStackChart":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ queryRange + " group by REVENUE_DATE order by REVENUE_DATE desc";
											break;
										case "applyButtLineChart":
											applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ queryRange + " group by REVENUE_DATE order by REVENUE_DATE desc";
											break;
										case "applyButtPieChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ queryRange + " ";
											break;
										}

									}
								}

							} // end null tech

							// if there is a technology
							else if (finalSiteTechList != "") {

								// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {
									switch (filterID) {

									case "applyButtStackChartMax":
										applyFilterRevSum = "select TO_CHAR(t.revenue_date, 'YYYY/MM/DD') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, RANK() over(PARTITION by combination_tech order by rev_sum desc) as RANK from "
												+ "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,a.COMBINATION_TECHNOLOGY as combination_tech,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
												+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date,a.COMBINATION_TECHNOLOGY order by rev_sum desc)"
												+ ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";

										break;

									case "applyButtStackChartMin":

										applyFilterRevSum = "select TO_CHAR(t.revenue_date, 'YYYY/MM/DD') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, RANK() over(PARTITION by combination_tech order by rev_sum asc) as RANK from "
												+ "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,a.COMBINATION_TECHNOLOGY as combination_tech,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
												+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date,a.COMBINATION_TECHNOLOGY order by rev_sum asc)"
												+ ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";

										break;

									case "applyButtLineChartMax":
										applyFilterRevSum = "select TO_CHAR(t.revenue_date, 'YYYY/MM/DD') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, RANK() over(PARTITION by combination_tech order by rev_sum desc) as RANK from "
												+ "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,a.COMBINATION_TECHNOLOGY as combination_tech,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
												+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date,a.COMBINATION_TECHNOLOGY order by rev_sum desc)"
												+ ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";

										break;

									case "applyButtLineChartMin":
										applyFilterRevSum = "select TO_CHAR(t.revenue_date, 'YYYY/MM/DD') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, RANK() over(PARTITION by combination_tech order by rev_sum asc) as RANK from "
												+ "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,a.COMBINATION_TECHNOLOGY as combination_tech,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
												+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date,a.COMBINATION_TECHNOLOGY order by rev_sum asc)"
												+ ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";

										break;

									}

								}
								// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {

									case "applyButtStackChartMax":
										applyFilterRevSum = "select TO_CHAR(t.revenue_date, 'YYYY/MM/DD') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, RANK() over(PARTITION by combination_tech order by rev_sum desc) as RANK from "
												+ "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,a.COMBINATION_TECHNOLOGY as combination_tech,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
												+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date,a.COMBINATION_TECHNOLOGY order by rev_sum desc)"
												+ ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";

										break;

									case "applyButtLineChartMax":
										applyFilterRevSum = "select TO_CHAR(t.revenue_date, 'YYYY/MM/DD') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, RANK() over(PARTITION by combination_tech order by rev_sum desc) as RANK from "
												+ "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,a.COMBINATION_TECHNOLOGY as combination_tech,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
												+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date,a.COMBINATION_TECHNOLOGY order by rev_sum desc)"
												+ ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";

										break;

									}
								}
								// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {
									switch (filterID) {

									case "applyButtStackChartMin":

										applyFilterRevSum = "select TO_CHAR(t.revenue_date, 'YYYY/MM/DD') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, RANK() over(PARTITION by combination_tech order by rev_sum asc) as RANK from "
												+ "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,a.COMBINATION_TECHNOLOGY as combination_tech,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
												+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date,a.COMBINATION_TECHNOLOGY order by rev_sum asc)"
												+ ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";

										break;

									case "applyButtLineChartMin":
										applyFilterRevSum = "select TO_CHAR(t.revenue_date, 'YYYY/MM/DD') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
												+ "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue,combination_tech, RANK() over(PARTITION by combination_tech order by rev_sum asc) as RANK from "
												+ "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,a.COMBINATION_TECHNOLOGY as combination_tech,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
												+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
												+ finalSiteTechList + " ) "
												+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date,a.COMBINATION_TECHNOLOGY order by rev_sum asc)"
												+ ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";

										break;

									}
								}
								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {
									case "applyButtMSChart":
										applyFilterRevSum = "select t.site_name,t.area_id ,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID,"
												+ "b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, "
												+ "sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  "
												+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ "and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + ") "
												+ queryRange + ""
												+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')"
												+ ")  t group by t.site_name, t.area_id order by t.site_name asc";
										break;

									case "applyButtStackChart":

										applyFilterRevSum = "select TO_CHAR(t.revenueDate, 'YYYY/MM/DD') as revenueDate ,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,(sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)) as tot_revenue  from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID,"
												+ "b.site_name as site_name,b.area_id as AREA_ID ,b.revenue_date as revenueDate, "
												+ "sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  "
												+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ "and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ queryRange + " "
												+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date "
												+ ") t group by t.revenueDate order by t.revenueDate desc";

										break;
									case "applyButtLineChart":

										applyFilterRevSum = "select TO_CHAR(t.revenueDate, 'YYYY/MM/DD') as revenueDate ,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas,(sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)) as tot_revenue  from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID,"
												+ "b.site_name as site_name,b.area_id as AREA_ID ,b.revenue_date as revenueDate, "
												+ "sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  "
												+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ "and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ queryRange + " "
												+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date "
												+ ") t group by t.revenueDate order by t.revenueDate desc";

										break;
									case "applyButtPieChart":
										applyFilterRevSum = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0)"
												+ "from (select t.revenue_date ,sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas  from ( SELECT DISTINCT a.WARE_NAME,a.SITE_ID,"
												+ "b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'DD/MM/YYYY') as revenue_date, "
												+ "sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  "
												+ "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ "and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " )"
												+ queryRange + ""
												+ "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, TO_CHAR(b.revenue_date, 'DD/MM/YYYY')"
												+ ") t group by t.revenue_date order by t.revenue_date asc)";
										break;

									}
								}

							}

							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {

								switch (filterID) {
								case "applyButtMSChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);
									chartJSNArr = chartRevenues(applyFilterQuery.list());

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
									} else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									}
									break;
								case "applyButtStackChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									if (applyFilterQuery.list().size() > 0) {

										periodicData = getStackedChartData(applyFilterQuery.list());
										rtn.put("StackedandLineRevenue", mapper.writeValueAsString(periodicData));

									} else {
										rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));

									}
									break;
								case "applyButtLineChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									if (applyFilterQuery.list().size() > 0) {

										periodicData = getStackedChartData(applyFilterQuery.list());
										rtn.put("StackedandLineRevenue", mapper.writeValueAsString(periodicData));

									} else {
										rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));

									}
									break;
								case "applyButtPieChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("pieRevenue", mapper.writeValueAsString(applyFilterQuery.list()));

									} else {
										rtn.put("pieRevenue", mapper.writeValueAsString(""));
									}
									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));

									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));

									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));

									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));

									break;
								}

							} else {
								switch (filterID) {

								case "applyButtMSChart":
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtPieChart":
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
									break;
								}

								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									switch (filterID) {
									case "applyButtStackChartMax":
										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", StartDate);
										applyFilterQuery.setParameter("param2", EndDate);

										if (applyFilterQuery.list().size() > 0) {
											periodicData = getStackedChartData(applyFilterQuery.list());
											rtn.put("MaxStackedandLineRevenue",
													mapper.writeValueAsString(periodicData));
										} else {
											rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										}

										break;

									case "applyButtLineChartMax":
										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", StartDate);
										applyFilterQuery.setParameter("param2", EndDate);

										if (applyFilterQuery.list().size() > 0) {
											periodicData = getStackedChartData(applyFilterQuery.list());
											rtn.put("MaxStackedandLineRevenue",
													mapper.writeValueAsString(periodicData));
										} else {
											rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										}

										break;

									case "applyButtStackChartMin":
										rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										break;

									case "applyButtLineChartMin":
										rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										break;

									}

								}
								if (StringUtils.equalsIgnoreCase(min, "Min")
										&& StringUtils.equalsIgnoreCase(max, null)) {
									switch (filterID) {
									case "applyButtStackChartMax":
										rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										break;

									case "applyButtLineChartMax":
										rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										break;

									case "applyButtStackChartMin":
										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", StartDate);
										applyFilterQuery.setParameter("param2", EndDate);

										if (applyFilterQuery.list().size() > 0) {
											periodicData = getStackedChartData(applyFilterQuery.list());
											rtn.put("MinStackedandLineRevenue",
													mapper.writeValueAsString(periodicData));
										} else {
											rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										}

										break;

									case "applyButtLineChartMin":
										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", StartDate);
										applyFilterQuery.setParameter("param2", EndDate);

										if (applyFilterQuery.list().size() > 0) {
											periodicData = getStackedChartData(applyFilterQuery.list());
											rtn.put("MinStackedandLineRevenue",
													mapper.writeValueAsString(periodicData));
										} else {
											rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										}

										break;

									}
								}
								if (StringUtils.equalsIgnoreCase(min, "Min")
										&& StringUtils.equalsIgnoreCase(max, "Max")) {
									switch (filterID) {
									case "applyButtStackChartMax":
										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", StartDate);
										applyFilterQuery.setParameter("param2", EndDate);

										if (applyFilterQuery.list().size() > 0) {
											periodicData = getStackedChartData(applyFilterQuery.list());
											rtn.put("MaxStackedandLineRevenue",
													mapper.writeValueAsString(periodicData));
										} else {
											rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										}

										break;

									case "applyButtLineChartMax":
										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", StartDate);
										applyFilterQuery.setParameter("param2", EndDate);

										if (applyFilterQuery.list().size() > 0) {
											periodicData = getStackedChartData(applyFilterQuery.list());

											rtn.put("MaxStackedandLineRevenue",
													mapper.writeValueAsString(periodicData));
										} else {
											rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										}

										break;

									case "applyButtStackChartMin":
										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", StartDate);
										applyFilterQuery.setParameter("param2", EndDate);

										if (applyFilterQuery.list().size() > 0) {
											periodicData = getStackedChartData(applyFilterQuery.list());
											rtn.put("MinStackedandLineRevenue",
													mapper.writeValueAsString(periodicData));
										} else {
											rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										}

										break;

									case "applyButtLineChartMin":
										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", StartDate);
										applyFilterQuery.setParameter("param2", EndDate);

										if (applyFilterQuery.list().size() > 0) {
											periodicData = getStackedChartData(applyFilterQuery.list());
											rtn.put("MinStackedandLineRevenue",
													mapper.writeValueAsString(periodicData));
										} else {
											rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										}

										break;

									}
								}
							}

						}
						if (StringUtils.equalsIgnoreCase(period, "Weekly")) {

							System.out.println("the start date is " + StartDate);
							double diff = (EndDate.getTime() - StartDate.getTime()) / 1000.0;
							diff /= (60 * 60 * 24 * 7);
							System.out.println("the week difference is before " + diff);
							diff = Math.abs(Math.ceil(diff));
							System.out.println("the week difference is after " + diff);

							String date = "";
							Date weeklyDate;
							Timestamp weeklyEndDate = StartDate;
							Calendar cal = Calendar.getInstance();
							Timestamp weeklyStartDate = StartDate;

							// Tech is not selected
							if (finalSiteTechList == "") {

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										switch (filterID) {
										case "applyButtStackChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtLineChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtStackChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtLineChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;
										}
									}

									// Select all not null sites name
									else {
										switch (filterID) {
										case "applyButtStackChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null'  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtLineChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtStackChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtLineChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;
										}
									}
								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										switch (filterID) {
										case "applyButtStackChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;
										case "applyButtLineChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;
										}
									}

									// Select all not null sites name
									else {
										switch (filterID) {
										case "applyButtStackChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null'  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;
										case "applyButtLineChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;
										}
									}

								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										switch (filterID) {
										case "applyButtStackChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;
										case "applyButtLineChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;
										}
									}
									// Select all not null sites name
									else {
										switch (filterID) {
										case "applyButtStackChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null'  "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;
										case "applyButtLineChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null'  "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;
										}
									}

								}
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										switch (filterID) {
										case "applyButtMSChart":
											applyFilterRevSum = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ queryRange
													+ " group by site_name, area_id order by site_name asc";
											break;
										case "applyButtStackChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date
													+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 "
													+ queryRange + "";
											break;
										case "applyButtLineChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date
													+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 "
													+ queryRange + "";
											break;
										case "applyButtPieChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ queryRange + "";
											break;
										}
									}

									// Select all not null sites name
									else {
										switch (filterID) {
										case "applyButtMSChart":
											applyFilterRevSum = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ queryRange + " group by site_name, area_id order by site_name";
											break;
										case "applyButtStackChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date
													+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name !='null' "
													+ queryRange + "";
											break;
										case "applyButtLineChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date
													+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name !='null' "
													+ queryRange + "";
											break;
										case "applyButtPieChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ queryRange + "";
											break;
										}
									}
								}

							}
							// if there is a technology
							else if (finalSiteTechList != "") {

								// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {
									switch (filterID) {
									case "applyButtStackChartMax":

										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;

									case "applyButtLineChartMax":
										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;

									case "applyButtStackChartMin":
										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;

									case "applyButtLineChartMin":
										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;
									}

								}
								// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {
									case "applyButtStackChartMax":

										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;

									case "applyButtLineChartMax":

										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;

									}

								}
								// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {
									switch (filterID) {

									case "applyButtStackChartMin":
										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;

									case "applyButtLineChartMin":
										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;
									}
								}
								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {
									case "applyButtMSChart":
										applyFilterRevSum = "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0) ,coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0), coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t group by t.site_name,t.area_id order by t.site_name asc ";
										break;
									case "applyButtStackChart":
										applyFilterRevSum = "select  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\" ,coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\", coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0)  as \"totalRevenue\" ,'"
												+ date
												+ "' as \"Date\"  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID)t order by t.site_name asc ";
										break;
									case "applyButtLineChart":
										applyFilterRevSum = "select  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\" ,coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\", coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0)  as \"totalRevenue\" ,'"
												+ date
												+ "' as \"Date\"  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID)t order by t.site_name asc ";
										break;
									case "applyButtPieChart":
										applyFilterRevSum = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
												+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ "  and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by site_name,revenue_date, AREA_ID)t order by t.site_name asc )";
										break;
									}
								}

							}
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
								switch (filterID) {

								case "applyButtMSChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									chartJSNArr = chartRevenues(applyFilterQuery.list());

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
									} else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));

									}
									break;

								case "applyButtPieChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("pieRevenue", mapper.writeValueAsString(applyFilterQuery.list()));

									} else {
										rtn.put("pieRevenue", mapper.writeValueAsString(""));
									}
									break;

								case "applyButtStackChart":

									for (i = 0; i < diff; i++) {

										weeklyEndDate = weeklyStartDate;
										cal.setTime(weeklyEndDate);
										cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
										weeklyDate = cal.getTime();
										System.out.println(weeklyDate);
										// weeklyEndDate = formatter.parse(weeklyEndDate);
										weeklyEndDate = new Timestamp(weeklyDate.getTime());
										System.out.println("for week " + i + ":" + weeklyEndDate);

										if ((weeklyEndDate).compareTo(EndDate) > 0) {
											System.out.println("the weeklyEndDate is greater than enddate");
											weeklyEndDate = EndDate;
											// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth() +1)
											// +"-" + (EndDate.getDate()) +"/"+ (EndDate.getMonth() +1);

										} else {
											System.out.println("the weeklyEndDate is less than enddate");

										}
										// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth() +1)+"/"+
										// (weeklyStartDate.getYear()+1900)+"-"+ (weeklyEndDate.getDate())+"/"+
										// (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getYear()+1900);

										date = (weeklyStartDate.getYear() + 1900) + "/"
												+ (weeklyStartDate.getMonth() + 1) + "/" + (weeklyStartDate.getDate())
												+ "-" + (weeklyEndDate.getYear() + 1900) + "/"
												+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", weeklyStartDate);
										applyFilterQuery.setParameter("param2", weeklyEndDate);
										revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
												.addScalar("voiceRevenue").addScalar("smsRevenue")
												.addScalar("dataRevneue").addScalar("vasRevenue")
												.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
														Transformers.aliasToBean(RevenueChartsReport.class))
												.list();
										revenueCharts.get(0).setDate(date);
										// System.out.println("for week " + i + " the date is :" + date);
										System.out.println(
												"the revenueChartsList" + mapper.writeValueAsString(revenueCharts));
										revenueChartList.addAll(revenueCharts);

										weeklyStartDate = weeklyEndDate;
										cal.setTime(weeklyStartDate);
										cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
										weeklyDate = cal.getTime();
										weeklyStartDate = new Timestamp(weeklyDate.getTime());

										System.out.println("after start date " + weeklyStartDate);

									}

									revenueChartListFinal = getPeriodicChartData(revenueChartList);
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(revenueChartListFinal));
									break;

								case "applyButtLineChart":

									for (i = 0; i < diff; i++) {

										weeklyEndDate = weeklyStartDate;
										cal.setTime(weeklyEndDate);
										cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
										weeklyDate = cal.getTime();
										System.out.println(weeklyDate);
										// weeklyEndDate = formatter.parse(weeklyEndDate);
										weeklyEndDate = new Timestamp(weeklyDate.getTime());
										System.out.println("for week " + i + ":" + weeklyEndDate);

										if ((weeklyEndDate).compareTo(EndDate) > 0) {
											System.out.println("the weeklyEndDate is greater than enddate");
											weeklyEndDate = EndDate;
											// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth() +1)
											// +"-" + (EndDate.getDate()) +"/"+ (EndDate.getMonth() +1);

										}
										// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth() +1)+"/"+
										// (weeklyStartDate.getYear()+1900)+"-"+ (weeklyEndDate.getDate())+"/"+
										// (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getYear()+1900);
										date = (weeklyStartDate.getYear() + 1900) + "/"
												+ (weeklyStartDate.getMonth() + 1) + "/" + (weeklyStartDate.getDate())
												+ "-" + (weeklyEndDate.getYear() + 1900) + "/"
												+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", weeklyStartDate);
										applyFilterQuery.setParameter("param2", weeklyEndDate);
										revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
												.addScalar("voiceRevenue").addScalar("smsRevenue")
												.addScalar("dataRevneue").addScalar("vasRevenue")
												.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
														Transformers.aliasToBean(RevenueChartsReport.class))
												.list();
										revenueCharts.get(0).setDate(date);
										// System.out.println("for week " + i + " the date is :" + date);
										// System.out.println("the
										// revenueChartsList"+mapper.writeValueAsString(revenueCharts));
										revenueChartList.addAll(revenueCharts);

										weeklyStartDate = weeklyEndDate;
										cal.setTime(weeklyStartDate);
										cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
										weeklyDate = cal.getTime();
										weeklyStartDate = new Timestamp(weeklyDate.getTime());

										System.out.println("after start date " + weeklyStartDate);

									}

									revenueChartListFinal = getPeriodicChartData(revenueChartList);
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(revenueChartListFinal));
									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								}
							} else {
								switch (filterID) {

								case "applyButtMSChart":
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									break;

								case "applyButtPieChart":
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;
								}
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {

									case "applyButtStackChartMax":

										for (i = 0; i < diff; i++) {

											weeklyEndDate = weeklyStartDate;
											cal.setTime(weeklyEndDate);
											cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											System.out.println(weeklyDate);
											// weeklyEndDate = formatter.parse(weeklyEndDate);
											weeklyEndDate = new Timestamp(weeklyDate.getTime());
											System.out.println("for week " + i + ":" + weeklyEndDate);

											if ((weeklyEndDate).compareTo(EndDate) > 0) {
												System.out.println("the weeklyEndDate is greater than enddate");
												weeklyEndDate = EndDate;
												// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
												// +1) +"-" + (EndDate.getDate()) +"/"+ (EndDate.getMonth() +1);

											}

											date = (weeklyStartDate.getYear() + 1900) + "/"
													+ (weeklyStartDate.getMonth() + 1) + "/"
													+ (weeklyStartDate.getDate()) + "-"
													+ (weeklyEndDate.getYear() + 1900) + "/"
													+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

											if (StringUtils.equalsIgnoreCase(max, "Max")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", weeklyStartDate);
												applyFilterQuery.setParameter("param2", weeklyEndDate);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("for week " + i + " the date is :" + date);
												revenueChartList.addAll(revenueCharts);
											}

											weeklyStartDate = weeklyEndDate;
											cal.setTime(weeklyStartDate);
											cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											weeklyStartDate = new Timestamp(weeklyDate.getTime());

										}

										if (StringUtils.equalsIgnoreCase(max, "Max")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MaxStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}

										break;

									case "applyButtLineChartMax":

										for (i = 0; i < diff; i++) {

											weeklyEndDate = weeklyStartDate;
											cal.setTime(weeklyEndDate);
											cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											System.out.println(weeklyDate);
											// weeklyEndDate = formatter.parse(weeklyEndDate);
											weeklyEndDate = new Timestamp(weeklyDate.getTime());
											System.out.println("for week " + i + ":" + weeklyEndDate);

											if ((weeklyEndDate).compareTo(EndDate) > 0) {
												System.out.println("the weeklyEndDate is greater than enddate");
												weeklyEndDate = EndDate;
												// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
												// +1) +"-" + (EndDate.getDate()) +"/"+ (EndDate.getMonth() +1);

											} else {
												System.out.println("the weeklyEndDate is less than enddate");

											}
											// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
											// +1)+"/"+ (weeklyStartDate.getYear()+1900)+"-"+
											// (weeklyEndDate.getDate())+"/"+ (weeklyEndDate.getMonth() +1)+"/"+
											// (weeklyEndDate.getYear()+1900);
											date = (weeklyStartDate.getYear() + 1900) + "/"
													+ (weeklyStartDate.getMonth() + 1) + "/"
													+ (weeklyStartDate.getDate()) + "-"
													+ (weeklyEndDate.getYear() + 1900) + "/"
													+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

											if (StringUtils.equalsIgnoreCase(max, "Max")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", weeklyStartDate);
												applyFilterQuery.setParameter("param2", weeklyEndDate);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("for week " + i + " the date is :" + date);
												revenueChartList.addAll(revenueCharts);
											}

											weeklyStartDate = weeklyEndDate;
											cal.setTime(weeklyStartDate);
											cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											weeklyStartDate = new Timestamp(weeklyDate.getTime());

										}

										if (StringUtils.equalsIgnoreCase(max, "Max")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MaxStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}

										break;

									case "applyButtStackChartMin":
										rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										break;

									case "applyButtLineChartMin":
										rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										break;
									}
								}
								if (StringUtils.equalsIgnoreCase(min, "Min")
										&& StringUtils.equalsIgnoreCase(max, null)) {
									switch (filterID) {
									case "applyButtStackChartMin":

										for (i = 0; i < diff; i++) {

											weeklyEndDate = weeklyStartDate;
											cal.setTime(weeklyEndDate);
											cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											// weeklyEndDate = formatter.parse(weeklyEndDate);
											weeklyEndDate = new Timestamp(weeklyDate.getTime());
											System.out.println("for week " + i + ":" + weeklyEndDate);

											if ((weeklyEndDate).compareTo(EndDate) > 0) {
												System.out.println("the weeklyEndDate is greater than enddate");
												weeklyEndDate = EndDate;
												// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
												// +1) +"-" + (EndDate.getDate()) +"/"+ (EndDate.getMonth() +1);

											}
											// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
											// +1)+"/"+ (weeklyStartDate.getYear()+1900)+"-"+
											// (weeklyEndDate.getDate())+"/"+ (weeklyEndDate.getMonth() +1)+"/"+
											// (weeklyEndDate.getYear()+1900);
											date = (weeklyStartDate.getYear() + 1900) + "/"
													+ (weeklyStartDate.getMonth() + 1) + "/"
													+ (weeklyStartDate.getDate()) + "-"
													+ (weeklyEndDate.getYear() + 1900) + "/"
													+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

											if (StringUtils.equalsIgnoreCase(min, "Min")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", weeklyStartDate);
												applyFilterQuery.setParameter("param2", weeklyEndDate);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("for week " + i + " the date is :" + date);
												revenueChartList.addAll(revenueCharts);
											}

											weeklyStartDate = weeklyEndDate;
											cal.setTime(weeklyStartDate);
											cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											weeklyStartDate = new Timestamp(weeklyDate.getTime());

										}

										if (StringUtils.equalsIgnoreCase(min, "Min")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MinStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}
										break;
									case "applyButtLineChartMin":

										for (i = 0; i < diff; i++) {

											weeklyEndDate = weeklyStartDate;
											cal.setTime(weeklyEndDate);
											cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											System.out.println(weeklyDate);
											// weeklyEndDate = formatter.parse(weeklyEndDate);
											weeklyEndDate = new Timestamp(weeklyDate.getTime());
											System.out.println("for week " + i + ":" + weeklyEndDate);

											if ((weeklyEndDate).compareTo(EndDate) > 0) {
												System.out.println("the weeklyEndDate is greater than enddate");
												weeklyEndDate = EndDate;
												// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
												// +1) +"-" + (EndDate.getDate()) +"/"+ (EndDate.getMonth() +1);

											}
											// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
											// +1)+"/"+ (weeklyStartDate.getYear()+1900)+"-"+
											// (weeklyEndDate.getDate())+"/"+ (weeklyEndDate.getMonth() +1)+"/"+
											// (weeklyEndDate.getYear()+1900);
											date = (weeklyStartDate.getYear() + 1900) + "/"
													+ (weeklyStartDate.getMonth() + 1) + "/"
													+ (weeklyStartDate.getDate()) + "-"
													+ (weeklyEndDate.getYear() + 1900) + "/"
													+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

											if (StringUtils.equalsIgnoreCase(min, "Min")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", weeklyStartDate);
												applyFilterQuery.setParameter("param2", weeklyEndDate);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("for week " + i + " the date is :" + date);
												revenueChartList.addAll(revenueCharts);
											}

											weeklyStartDate = weeklyEndDate;
											cal.setTime(weeklyStartDate);
											cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											weeklyStartDate = new Timestamp(weeklyDate.getTime());

											// System.out.println("after start date " + weeklyStartDate);

										}

										if (StringUtils.equalsIgnoreCase(min, "Min")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MinStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}
										break;

									case "applyButtStackChartMax":
										rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										break;

									case "applyButtLineChartMax":
										rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										break;

									}

								}
								// Max and Min

								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {
									switch (filterID) {

									case "applyButtStackChartMax":

										for (i = 0; i < diff; i++) {

											weeklyEndDate = weeklyStartDate;
											cal.setTime(weeklyEndDate);
											cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											weeklyEndDate = new Timestamp(weeklyDate.getTime());

											if ((weeklyEndDate).compareTo(EndDate) > 0) {
												weeklyEndDate = EndDate;

											}
											// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
											// +1)+"/"+ (weeklyStartDate.getYear()+1900)+"-"+
											// (weeklyEndDate.getDate())+"/"+ (weeklyEndDate.getMonth() +1)+"/"+
											// (weeklyEndDate.getYear()+1900);
											// System.out.println("the weeklyStartDate.getYear() "+
											// (weeklyStartDate.getYear()+1900));
											date = (weeklyStartDate.getYear() + 1900) + "/"
													+ (weeklyStartDate.getMonth() + 1) + "/"
													+ (weeklyStartDate.getDate()) + "-"
													+ (weeklyEndDate.getYear() + 1900) + "/"
													+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

											if (StringUtils.equalsIgnoreCase(max, "Max")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", weeklyStartDate);
												applyFilterQuery.setParameter("param2", weeklyEndDate);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("for week " + i + " the date is :" + date);
												revenueChartList.addAll(revenueCharts);
											}

											weeklyStartDate = weeklyEndDate;
											cal.setTime(weeklyStartDate);
											cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											weeklyStartDate = new Timestamp(weeklyDate.getTime());

										}

										if (StringUtils.equalsIgnoreCase(max, "Max")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MaxStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}

										break;

									case "applyButtLineChartMax":

										for (i = 0; i < diff; i++) {

											weeklyEndDate = weeklyStartDate;
											cal.setTime(weeklyEndDate);
											cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											// System.out.println(weeklyDate);
											// weeklyEndDate = formatter.parse(weeklyEndDate);
											weeklyEndDate = new Timestamp(weeklyDate.getTime());
											System.out.println("for week " + i + ":" + weeklyEndDate);

											if ((weeklyEndDate).compareTo(EndDate) > 0) {
												System.out.println("the weeklyEndDate is greater than enddate");
												weeklyEndDate = EndDate;
												// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
												// +1) +"-" + (EndDate.getDate()) +"/"+ (EndDate.getMonth() +1);

											}
											// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
											// +1)+"/"+ (weeklyStartDate.getYear()+1900)+"-"+
											// (weeklyEndDate.getDate())+"/"+ (weeklyEndDate.getMonth() +1)+"/"+
											// (weeklyEndDate.getYear()+1900);
											date = (weeklyStartDate.getYear() + 1900) + "/"
													+ (weeklyStartDate.getMonth() + 1) + "/"
													+ (weeklyStartDate.getDate()) + "-"
													+ (weeklyEndDate.getYear() + 1900) + "/"
													+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

											if (StringUtils.equalsIgnoreCase(max, "Max")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", weeklyStartDate);
												applyFilterQuery.setParameter("param2", weeklyEndDate);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("for week " + i + " the date is :" + date);
												revenueChartList.addAll(revenueCharts);
											}

											weeklyStartDate = weeklyEndDate;
											cal.setTime(weeklyStartDate);
											cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											weeklyStartDate = new Timestamp(weeklyDate.getTime());

											System.out.println("after start date " + weeklyStartDate);

										}

										if (StringUtils.equalsIgnoreCase(max, "Max")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MaxStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}

										break;

									case "applyButtStackChartMin":

										for (i = 0; i < diff; i++) {

											weeklyEndDate = weeklyStartDate;
											cal.setTime(weeklyEndDate);
											cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											System.out.println(weeklyDate);
											// weeklyEndDate = formatter.parse(weeklyEndDate);
											weeklyEndDate = new Timestamp(weeklyDate.getTime());
											System.out.println("for week " + i + ":" + weeklyEndDate);

											if ((weeklyEndDate).compareTo(EndDate) > 0) {
												System.out.println("the weeklyEndDate is greater than enddate");
												weeklyEndDate = EndDate;
												// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
												// +1) +"-" + (EndDate.getDate()) +"/"+ (EndDate.getMonth() +1);

											}
											// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
											// +1)+"/"+ (weeklyStartDate.getYear()+1900)+"-"+
											// (weeklyEndDate.getDate())+"/"+ (weeklyEndDate.getMonth() +1)+"/"+
											// (weeklyEndDate.getYear()+1900);
											date = (weeklyStartDate.getYear() + 1900) + "/"
													+ (weeklyStartDate.getMonth() + 1) + "/"
													+ (weeklyStartDate.getDate()) + "-"
													+ (weeklyEndDate.getYear() + 1900) + "/"
													+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

											if (StringUtils.equalsIgnoreCase(min, "Min")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", weeklyStartDate);
												applyFilterQuery.setParameter("param2", weeklyEndDate);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("for week " + i + " the date is :" + date);
												// System.out.println("the
												// minRevenueChartsList"+mapper.writeValueAsString(minRevenueCharts));
												revenueChartList.addAll(revenueCharts);
											}

											weeklyStartDate = weeklyEndDate;
											cal.setTime(weeklyStartDate);
											cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											weeklyStartDate = new Timestamp(weeklyDate.getTime());

											System.out.println("after start date " + weeklyStartDate);

										}

										if (StringUtils.equalsIgnoreCase(min, "Min")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MinStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}
										break;
									case "applyButtLineChartMin":

										for (i = 0; i < diff; i++) {

											weeklyEndDate = weeklyStartDate;
											cal.setTime(weeklyEndDate);
											cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											System.out.println(weeklyDate);
											// weeklyEndDate = formatter.parse(weeklyEndDate);
											weeklyEndDate = new Timestamp(weeklyDate.getTime());
											System.out.println("for week " + i + ":" + weeklyEndDate);

											if ((weeklyEndDate).compareTo(EndDate) > 0) {
												System.out.println("the weeklyEndDate is greater than enddate");
												weeklyEndDate = EndDate;
												// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
												// +1) +"-" + (EndDate.getDate()) +"/"+ (EndDate.getMonth() +1);

											} else {
												System.out.println("the weeklyEndDate is less than enddate");

											}
											// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth()
											// +1)+"/"+ (weeklyStartDate.getYear()+1900)+"-"+
											// (weeklyEndDate.getDate())+"/"+ (weeklyEndDate.getMonth() +1)+"/"+
											// (weeklyEndDate.getYear()+1900);
											date = (weeklyStartDate.getYear() + 1900) + "/"
													+ (weeklyStartDate.getMonth() + 1) + "/"
													+ (weeklyStartDate.getDate()) + "-"
													+ (weeklyEndDate.getYear() + 1900) + "/"
													+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

											if (StringUtils.equalsIgnoreCase(min, "Min")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", weeklyStartDate);
												applyFilterQuery.setParameter("param2", weeklyEndDate);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("for week " + i + " the date is :" + date);
												// System.out.println("the
												// minRevenueChartsList"+mapper.writeValueAsString(minRevenueCharts));
												revenueChartList.addAll(revenueCharts);
											}

											weeklyStartDate = weeklyEndDate;
											cal.setTime(weeklyStartDate);
											cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or
																				// subtract
											weeklyDate = cal.getTime();
											weeklyStartDate = new Timestamp(weeklyDate.getTime());

											System.out.println("after start date " + weeklyStartDate);

										}

										if (StringUtils.equalsIgnoreCase(min, "Min")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MinStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}
										break;
									}
								}

							}

						} // end if of weekly

						// start of monthly
						if (StringUtils.equalsIgnoreCase(period, "Monthly")) {

							System.out.println("Monthy and site is selectedd");

							System.out.println("the start date is " + StartDate);
							Calendar m = Calendar.getInstance();
							m.setTime(StartDate);
							int nMonth1 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
							m.setTime(EndDate);
							int nMonth2 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
							int diff = Math.abs(nMonth2 - nMonth1);

							System.out.println("the Month difference is " + diff);
							Timestamp month = StartDate;
							String date = "";

							if (finalSiteTechList == "") {

								// IF MAX AND MIN IS CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										switch (filterID) {
										case "applyButtStackChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtLineChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtStackChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtLineChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;
										}
									}
									// Select all not null sites
									else {

										switch (filterID) {
										case "applyButtStackChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null'  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtLineChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtStackChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtLineChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;
										}
									}
								}
								// IF MAX IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										switch (filterID) {
										case "applyButtStackChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtLineChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;

										}
									}
									// Select all not null sites
									else {
										switch (filterID) {
										case "applyButtStackChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null'  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtLineChartMax":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum desc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null'  "
													+ "group by site_name,area_id order by rev_sum desc) ) t where RANK = 1 "
													+ queryRange;

											break;

										}
									}
								}
								// IF MIN IS ONLY CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										switch (filterID) {

										case "applyButtStackChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtLineChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2  "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;
										}
									}
									// Select all not null sites
									else {

										switch (filterID) {

										case "applyButtStackChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;

										case "applyButtLineChartMin":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date + "' as \"Date\" from ( "
													+ "select rev_sum,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(order by rev_sum asc) as RANK from "
													+ "(select sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(voice_revenue)as voice_revenue ,sum(sms_revenue)as sms_revenue,sum(data_revenue)as data_revenue ,sum(vas_revenue)as vas_revenue,site_name,AREA_ID from "
													+ "prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ "group by site_name,area_id order by rev_sum asc) ) t where RANK = 1 "
													+ queryRange;

											break;
										}
									}
								}
								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									// Select all sites name including NULL site
									if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

										switch (filterID) {
										case "applyButtMSChart":
											applyFilterRevSum = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ queryRange + " group by site_name, area_id";
											break;
										case "applyButtStackChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date
													+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 "
													+ queryRange + "";
											break;
										case "applyButtLineChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date
													+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 "
													+ queryRange + "";
											break;
										case "applyButtPieChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
													+ queryRange + "";
											break;
										}
									}
									// Select all not null sites
									else {
										switch (filterID) {
										case "applyButtMSChart":
											applyFilterRevSum = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ queryRange + " group by site_name, area_id";
											break;
										case "applyButtStackChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date
													+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name !='null' "
													+ queryRange + "";
											break;
										case "applyButtLineChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
													+ date
													+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name !='null' "
													+ queryRange + "";
											break;
										case "applyButtPieChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
													+ queryRange + "";
											break;
										}
									}
								}

							}
							// if there is a technology
							else if (finalSiteTechList != "") {

								// IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {
									switch (filterID) {
									case "applyButtStackChartMax":

										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;

									case "applyButtLineChartMax":

										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;

									case "applyButtStackChartMin":
										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;

									case "applyButtLineChartMin":
										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;
									}

								}
								// IF MAX IS ONLY CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {

									switch (filterID) {
									case "applyButtStackChartMax":

										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;

									case "applyButtLineChartMax":

										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) desc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;

									}

								}
								// IF MIN IS ONLY CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, "Min")) {
									switch (filterID) {

									case "applyButtStackChartMin":

										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;

									case "applyButtLineChartMin":

										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\" , '"
												+ date
												+ "' as \"Date\" from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech, RANK() over(order by sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) asc) as RANK from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t where RANK = 1 ";

										break;
									}
								}
								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {
									case "applyButtMSChart":

										applyFilterRevSum = "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0) ,coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0), coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t group by t.site_name,t.area_id order by t.site_name asc ";

										break;
									case "applyButtStackChart":

										applyFilterRevSum = "select  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\" ,coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\", coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0)  as \"totalRevenue\" ,'"
												+ date
												+ "' as \"Date\"  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID)t order by t.site_name asc ";

										break;
									case "applyButtLineChart":
										applyFilterRevSum = "select  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\" ,coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\", coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0)  as \"totalRevenue\" ,'"
												+ date
												+ "' as \"Date\"  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID)t order by t.site_name asc ";
										break;
									case "applyButtPieChart":

										applyFilterRevSum = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
												+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ "  and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by site_name,revenue_date, AREA_ID)t order by t.site_name asc )";
										break;
									}
								}

							}

							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
								switch (filterID) {

								case "applyButtMSChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									chartJSNArr = chartRevenues(applyFilterQuery.list());

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
									} else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));

									}
									break;

								case "applyButtPieChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("pieRevenue", mapper.writeValueAsString(applyFilterQuery.list()));

									} else {
										rtn.put("pieRevenue", mapper.writeValueAsString(""));
									}
									break;

								case "applyButtStackChart":

									for (i = 0; i <= diff; i++) {

										String startmonth = formatter.format(month);
										Date monthDate = formatter.parse(startmonth);
										Timestamp monthofDate = new Timestamp(monthDate.getTime());

										Calendar calendar = Calendar.getInstance();
										calendar.setTime(monthofDate);
										calendar.add(Calendar.MONTH, 1);
										calendar.set(Calendar.DAY_OF_MONTH, 1);
										calendar.add(Calendar.DATE, -1);
										Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

										String endMonth = formatter.format(lastDayOfMonth);
										Date endOfDate = formatter.parse(endMonth);
										Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
										System.out.println("for month " + i + ":" + endOfMonth);

										if ((endOfMonth).compareTo(EndDate) > 0) {
											System.out.println("the endOfMonth is greater than enddate");
											endOfMonth = EndDate;

										} else {
											System.out.println("the endOfMonth is less than enddate");

										}

										date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1) + "/"
												+ (monthofDate.getDate()) + "-" + (endOfMonth.getYear() + 1900) + "/"
												+ (endOfMonth.getMonth() + 1) + "/" + (endOfMonth.getDate());

										// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1)
										// +"/"+(monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
										// (endOfMonth.getMonth() +1) +"/"+ (monthofDate.getYear()+1900);

										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", monthofDate);
										applyFilterQuery.setParameter("param2", endOfMonth);
										revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
												.addScalar("voiceRevenue").addScalar("smsRevenue")
												.addScalar("dataRevneue").addScalar("vasRevenue")
												.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
														Transformers.aliasToBean(RevenueChartsReport.class))
												.list();
										revenueCharts.get(0).setDate(date);
										// System.out.println("for week " + i + " the date is :" + date);
										// System.out.println("the
										// revenueChartsList"+mapper.writeValueAsString(revenueCharts));
										revenueChartList.addAll(revenueCharts);

										calendar.setTime(endOfMonth);
										calendar.add(Calendar.DATE, +1);
										Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
										month = NextDayOfMonth;
										System.out.println("after start date " + NextDayOfMonth);

									}

									revenueChartListFinal = getPeriodicChartData(revenueChartList);
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(revenueChartListFinal));

									break;

								case "applyButtLineChart":

									for (i = 0; i <= diff; i++) {

										String startmonth = formatter.format(month);
										Date monthDate = formatter.parse(startmonth);
										Timestamp monthofDate = new Timestamp(monthDate.getTime());

										Calendar calendar = Calendar.getInstance();
										calendar.setTime(monthofDate);
										calendar.add(Calendar.MONTH, 1);
										calendar.set(Calendar.DAY_OF_MONTH, 1);
										calendar.add(Calendar.DATE, -1);
										Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

										String endMonth = formatter.format(lastDayOfMonth);
										Date endOfDate = formatter.parse(endMonth);
										Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
										System.out.println("for month " + i + ":" + endOfMonth);

										if ((endOfMonth).compareTo(EndDate) > 0) {
											System.out.println("the endOfMonth is greater than enddate");
											endOfMonth = EndDate;

										} else {
											System.out.println("the endOfMonth is less than enddate");

										}
										// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"-" +
										// (endOfMonth.getDate())+"/"+ (endOfMonth.getMonth() +1);
										date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1) + "/"
												+ (monthofDate.getDate()) + "-" + (endOfMonth.getYear() + 1900) + "/"
												+ (endOfMonth.getMonth() + 1) + "/" + (endOfMonth.getDate());

										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", monthofDate);
										applyFilterQuery.setParameter("param2", endOfMonth);
										revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
												.addScalar("voiceRevenue").addScalar("smsRevenue")
												.addScalar("dataRevneue").addScalar("vasRevenue")
												.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
														Transformers.aliasToBean(RevenueChartsReport.class))
												.list();
										revenueCharts.get(0).setDate(date);
										// System.out.println("for week " + i + " the date is :" + date);
										// System.out.println("the
										// revenueChartsList"+mapper.writeValueAsString(revenueCharts));
										revenueChartList.addAll(revenueCharts);

										calendar.setTime(endOfMonth);
										calendar.add(Calendar.DATE, +1);
										Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
										month = NextDayOfMonth;
										System.out.println("after start date " + NextDayOfMonth);

									}

									revenueChartListFinal = getPeriodicChartData(revenueChartList);
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(revenueChartListFinal));

									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								}
							} else {
								switch (filterID) {

								case "applyButtMSChart":
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									break;

								case "applyButtPieChart":
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;
								}
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {

									case "applyButtStackChartMax":
										for (i = 0; i <= diff; i++) {

											String startmonth = formatter.format(month);
											Date monthDate = formatter.parse(startmonth);
											Timestamp monthofDate = new Timestamp(monthDate.getTime());

											Calendar calendar = Calendar.getInstance();
											calendar.setTime(monthofDate);
											calendar.add(Calendar.MONTH, 1);
											calendar.set(Calendar.DAY_OF_MONTH, 1);
											calendar.add(Calendar.DATE, -1);
											Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

											String endMonth = formatter.format(lastDayOfMonth);
											Date endOfDate = formatter.parse(endMonth);
											Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
											System.out.println("for month " + i + ":" + endOfMonth);

											if ((endOfMonth).compareTo(EndDate) > 0) {
												System.out.println("the endOfMonth is greater than enddate");
												endOfMonth = EndDate;

											} else {
												System.out.println("the endOfMonth is less than enddate");

											}
											// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
											// (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
											// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
											date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1)
													+ "/" + (monthofDate.getDate()) + "-"
													+ (endOfMonth.getYear() + 1900) + "/" + (endOfMonth.getMonth() + 1)
													+ "/" + (endOfMonth.getDate());

											if (StringUtils.equalsIgnoreCase(max, "Max")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", monthofDate);
												applyFilterQuery.setParameter("param2", endOfMonth);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("for week " + i + " the date is :" + date);
												// System.out.println("the
												// maxRevenueChartsList"+mapper.writeValueAsString(maxRevenueCharts));
												revenueChartList.addAll(revenueCharts);
											}

											calendar.setTime(endOfMonth);
											calendar.add(Calendar.DATE, +1);
											Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
											month = NextDayOfMonth;
											System.out.println("after start date " + NextDayOfMonth);

										}

										if (StringUtils.equalsIgnoreCase(max, "Max")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MaxStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}

										break;

									case "applyButtLineChartMax":
										for (i = 0; i <= diff; i++) {

											String startmonth = formatter.format(month);
											Date monthDate = formatter.parse(startmonth);
											Timestamp monthofDate = new Timestamp(monthDate.getTime());

											Calendar calendar = Calendar.getInstance();
											calendar.setTime(monthofDate);
											calendar.add(Calendar.MONTH, 1);
											calendar.set(Calendar.DAY_OF_MONTH, 1);
											calendar.add(Calendar.DATE, -1);
											Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

											String endMonth = formatter.format(lastDayOfMonth);
											Date endOfDate = formatter.parse(endMonth);
											Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
											System.out.println("for month " + i + ":" + endOfMonth);

											if ((endOfMonth).compareTo(EndDate) > 0) {
												System.out.println("the endOfMonth is greater than enddate");
												endOfMonth = EndDate;

											} else {
												System.out.println("the endOfMonth is less than enddate");

											}
											// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
											// (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
											// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
											date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1)
													+ "/" + (monthofDate.getDate()) + "-"
													+ (endOfMonth.getYear() + 1900) + "/" + (endOfMonth.getMonth() + 1)
													+ "/" + (endOfMonth.getDate());

											if (StringUtils.equalsIgnoreCase(max, "Max")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", monthofDate);
												applyFilterQuery.setParameter("param2", endOfMonth);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("for week " + i + " the date is :" + date);
												// System.out.println("the
												// maxRevenueChartsList"+mapper.writeValueAsString(maxRevenueCharts));
												revenueChartList.addAll(revenueCharts);
											}

											calendar.setTime(endOfMonth);
											calendar.add(Calendar.DATE, +1);
											Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
											month = NextDayOfMonth;
											System.out.println("after start date " + NextDayOfMonth);

										}

										if (StringUtils.equalsIgnoreCase(max, "Max")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MaxStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}

										break;
									case "applyButtStackChartMin":
										rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										break;

									case "applyButtLineChartMin":
										rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
										break;
									}
								}
								if (StringUtils.equalsIgnoreCase(min, "Min")
										&& StringUtils.equalsIgnoreCase(max, null)) {
									switch (filterID) {
									case "applyButtStackChartMin":
										for (i = 0; i <= diff; i++) {

											String startmonth = formatter.format(month);
											Date monthDate = formatter.parse(startmonth);
											Timestamp monthofDate = new Timestamp(monthDate.getTime());

											Calendar calendar = Calendar.getInstance();
											calendar.setTime(monthofDate);
											calendar.add(Calendar.MONTH, 1);
											calendar.set(Calendar.DAY_OF_MONTH, 1);
											calendar.add(Calendar.DATE, -1);
											Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

											String endMonth = formatter.format(lastDayOfMonth);
											Date endOfDate = formatter.parse(endMonth);
											Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
											System.out.println("for month " + i + ":" + endOfMonth);

											if ((endOfMonth).compareTo(EndDate) > 0) {
												System.out.println("the endOfMonth is greater than enddate");
												endOfMonth = EndDate;

											} else {
												System.out.println("the endOfMonth is less than enddate");

											}
											// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
											// (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
											// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
											date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1)
													+ "/" + (monthofDate.getDate()) + "-"
													+ (endOfMonth.getYear() + 1900) + "/" + (endOfMonth.getMonth() + 1)
													+ "/" + (endOfMonth.getDate());

											if (StringUtils.equalsIgnoreCase(min, "Min")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", monthofDate);
												applyFilterQuery.setParameter("param2", endOfMonth);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												revenueChartList.addAll(revenueCharts);
											}

											calendar.setTime(endOfMonth);
											calendar.add(Calendar.DATE, +1);
											Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
											month = NextDayOfMonth;
											System.out.println("after start date " + NextDayOfMonth);

										}

										if (StringUtils.equalsIgnoreCase(min, "Min")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MinStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}
										break;

									case "applyButtLineChartMin":
										for (i = 0; i <= diff; i++) {

											String startmonth = formatter.format(month);
											Date monthDate = formatter.parse(startmonth);
											Timestamp monthofDate = new Timestamp(monthDate.getTime());

											Calendar calendar = Calendar.getInstance();
											calendar.setTime(monthofDate);
											calendar.add(Calendar.MONTH, 1);
											calendar.set(Calendar.DAY_OF_MONTH, 1);
											calendar.add(Calendar.DATE, -1);
											Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

											String endMonth = formatter.format(lastDayOfMonth);
											Date endOfDate = formatter.parse(endMonth);
											Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
											System.out.println("for month " + i + ":" + endOfMonth);

											if ((endOfMonth).compareTo(EndDate) > 0) {
												System.out.println("the endOfMonth is greater than enddate");
												endOfMonth = EndDate;

											} else {
												System.out.println("the endOfMonth is less than enddate");

											}
											// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
											// (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
											// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
											date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1)
													+ "/" + (monthofDate.getDate()) + "-"
													+ (endOfMonth.getYear() + 1900) + "/" + (endOfMonth.getMonth() + 1)
													+ "/" + (endOfMonth.getDate());

											if (StringUtils.equalsIgnoreCase(min, "Min")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", monthofDate);
												applyFilterQuery.setParameter("param2", endOfMonth);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("the
												// minRevenueChartsList"+mapper.writeValueAsString(minRevenueCharts));
												revenueChartList.addAll(revenueCharts);
											}

											calendar.setTime(endOfMonth);
											calendar.add(Calendar.DATE, +1);
											Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
											month = NextDayOfMonth;
											// System.out.println("after start date " + NextDayOfMonth);

										}

										if (StringUtils.equalsIgnoreCase(min, "Min")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MinStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}
										break;

									case "applyButtStackChartMax":
										rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										break;

									case "applyButtLineChartMax":
										rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
										break;

									}

								}
								// Max and Min
								if (StringUtils.equalsIgnoreCase(max, "Max")
										&& StringUtils.equalsIgnoreCase(min, "Min")) {
									switch (filterID) {

									case "applyButtStackChartMax":
										for (i = 0; i <= diff; i++) {

											String startmonth = formatter.format(month);
											Date monthDate = formatter.parse(startmonth);
											Timestamp monthofDate = new Timestamp(monthDate.getTime());

											Calendar calendar = Calendar.getInstance();
											calendar.setTime(monthofDate);
											calendar.add(Calendar.MONTH, 1);
											calendar.set(Calendar.DAY_OF_MONTH, 1);
											calendar.add(Calendar.DATE, -1);
											Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

											String endMonth = formatter.format(lastDayOfMonth);
											Date endOfDate = formatter.parse(endMonth);
											Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
											System.out.println("for month " + i + ":" + endOfMonth);

											if ((endOfMonth).compareTo(EndDate) > 0) {
												System.out.println("the endOfMonth is greater than enddate");
												endOfMonth = EndDate;

											} else {
												System.out.println("the endOfMonth is less than enddate");

											}
											// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
											// (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
											// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
											date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1)
													+ "/" + (monthofDate.getDate()) + "-"
													+ (endOfMonth.getYear() + 1900) + "/" + (endOfMonth.getMonth() + 1)
													+ "/" + (endOfMonth.getDate());

											if (StringUtils.equalsIgnoreCase(max, "Max")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", monthofDate);
												applyFilterQuery.setParameter("param2", endOfMonth);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("for week " + i + " the date is :" + date);
												// System.out.println("the
												// maxRevenueChartsList"+mapper.writeValueAsString(maxRevenueCharts));
												revenueChartList.addAll(revenueCharts);
											}

											calendar.setTime(endOfMonth);
											calendar.add(Calendar.DATE, +1);
											Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
											month = NextDayOfMonth;
											System.out.println("after start date " + NextDayOfMonth);

										}

										if (StringUtils.equalsIgnoreCase(max, "Max")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MaxStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}

										break;

									case "applyButtLineChartMax":
										for (i = 0; i <= diff; i++) {

											String startmonth = formatter.format(month);
											Date monthDate = formatter.parse(startmonth);
											Timestamp monthofDate = new Timestamp(monthDate.getTime());

											Calendar calendar = Calendar.getInstance();
											calendar.setTime(monthofDate);
											calendar.add(Calendar.MONTH, 1);
											calendar.set(Calendar.DAY_OF_MONTH, 1);
											calendar.add(Calendar.DATE, -1);
											Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

											String endMonth = formatter.format(lastDayOfMonth);
											Date endOfDate = formatter.parse(endMonth);
											Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
											System.out.println("for month " + i + ":" + endOfMonth);

											if ((endOfMonth).compareTo(EndDate) > 0) {
												System.out.println("the endOfMonth is greater than enddate");
												endOfMonth = EndDate;

											} else {
												System.out.println("the endOfMonth is less than enddate");

											}
											// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
											// (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
											// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
											date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1)
													+ "/" + (monthofDate.getDate()) + "-"
													+ (endOfMonth.getYear() + 1900) + "/" + (endOfMonth.getMonth() + 1)
													+ "/" + (endOfMonth.getDate());

											if (StringUtils.equalsIgnoreCase(max, "Max")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", monthofDate);
												applyFilterQuery.setParameter("param2", endOfMonth);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("for week " + i + " the date is :" + date);
												// System.out.println("the
												// maxRevenueChartsList"+mapper.writeValueAsString(maxRevenueCharts));
												revenueChartList.addAll(revenueCharts);
											}

											calendar.setTime(endOfMonth);
											calendar.add(Calendar.DATE, +1);
											Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
											month = NextDayOfMonth;
											System.out.println("after start date " + NextDayOfMonth);

										}

										if (StringUtils.equalsIgnoreCase(max, "Max")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MaxStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}

										break;
									case "applyButtStackChartMin":
										for (i = 0; i <= diff; i++) {

											String startmonth = formatter.format(month);
											Date monthDate = formatter.parse(startmonth);
											Timestamp monthofDate = new Timestamp(monthDate.getTime());

											Calendar calendar = Calendar.getInstance();
											calendar.setTime(monthofDate);
											calendar.add(Calendar.MONTH, 1);
											calendar.set(Calendar.DAY_OF_MONTH, 1);
											calendar.add(Calendar.DATE, -1);
											Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

											String endMonth = formatter.format(lastDayOfMonth);
											Date endOfDate = formatter.parse(endMonth);
											Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
											System.out.println("for month " + i + ":" + endOfMonth);

											if ((endOfMonth).compareTo(EndDate) > 0) {
												System.out.println("the endOfMonth is greater than enddate");
												endOfMonth = EndDate;

											} else {
												System.out.println("the endOfMonth is less than enddate");

											}
											// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
											// (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
											// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
											date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1)
													+ "/" + (monthofDate.getDate()) + "-"
													+ (endOfMonth.getYear() + 1900) + "/" + (endOfMonth.getMonth() + 1)
													+ "/" + (endOfMonth.getDate());

											if (StringUtils.equalsIgnoreCase(min, "Min")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", monthofDate);
												applyFilterQuery.setParameter("param2", endOfMonth);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												revenueChartList.addAll(revenueCharts);
											}

											calendar.setTime(endOfMonth);
											calendar.add(Calendar.DATE, +1);
											Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
											month = NextDayOfMonth;
											System.out.println("after start date " + NextDayOfMonth);

										}

										if (StringUtils.equalsIgnoreCase(min, "Min")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MinStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}
										break;

									case "applyButtLineChartMin":
										for (i = 0; i <= diff; i++) {

											String startmonth = formatter.format(month);
											Date monthDate = formatter.parse(startmonth);
											Timestamp monthofDate = new Timestamp(monthDate.getTime());

											Calendar calendar = Calendar.getInstance();
											calendar.setTime(monthofDate);
											calendar.add(Calendar.MONTH, 1);
											calendar.set(Calendar.DAY_OF_MONTH, 1);
											calendar.add(Calendar.DATE, -1);
											Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

											String endMonth = formatter.format(lastDayOfMonth);
											Date endOfDate = formatter.parse(endMonth);
											Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
											System.out.println("for month " + i + ":" + endOfMonth);

											if ((endOfMonth).compareTo(EndDate) > 0) {
												System.out.println("the endOfMonth is greater than enddate");
												endOfMonth = EndDate;

											} else {
												System.out.println("the endOfMonth is less than enddate");

											}
											// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
											// (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
											// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
											date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1)
													+ "/" + (monthofDate.getDate()) + "-"
													+ (endOfMonth.getYear() + 1900) + "/" + (endOfMonth.getMonth() + 1)
													+ "/" + (endOfMonth.getDate());

											if (StringUtils.equalsIgnoreCase(min, "Min")) {
												applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
												applyFilterQuery.setParameter("param1", monthofDate);
												applyFilterQuery.setParameter("param2", endOfMonth);
												revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
														.addScalar("voiceRevenue").addScalar("smsRevenue")
														.addScalar("dataRevneue").addScalar("vasRevenue")
														.addScalar("totalRevenue").addScalar("Date")
														.setResultTransformer(
																Transformers.aliasToBean(RevenueChartsReport.class))
														.list();
												revenueCharts.get(0).setDate(date);
												// System.out.println("the
												// minRevenueChartsList"+mapper.writeValueAsString(minRevenueCharts));
												revenueChartList.addAll(revenueCharts);
											}

											calendar.setTime(endOfMonth);
											calendar.add(Calendar.DATE, +1);
											Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
											month = NextDayOfMonth;
											System.out.println("after start date " + NextDayOfMonth);

										}

										if (StringUtils.equalsIgnoreCase(min, "Min")) {

											if (revenueChartList.size() > 0) {
												revenueChartListFinal = getPeriodicChartData(revenueChartList);
												rtn.put("MinStackedandLineRevenue",
														mapper.writeValueAsString(revenueChartListFinal));
											} else {
												rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
											}

										}
										break;
									}
								}

							}

						}
						// start of acc or absolute max or min
						if (StringUtils.equalsIgnoreCase(period, "Accu")
								|| StringUtils.equalsIgnoreCase(period, "null")) {
							System.out.println("acccccc");
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
								// Tech is not selected
								if (finalSiteTechList == "") {

									// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {

										// Select all sites name including NULL site
										if (StringUtils.equalsIgnoreCase(nullSite, "nullSite")) {

											switch (filterID) {
											case "applyButtMSChart":
												applyFilterRevSum = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
														+ queryRange
														+ " group by site_name, area_id order by site_name asc ";
												break;
											/*
											 * case "applyButtStackChart": timeRevenueStackandLineChartQueryString =
											 * "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
											 * +date+"' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name LIKE '%"
											 * + site + "%' and t.AREA_NAME LIKE '%" + area +
											 * "%' and t.REGION_NAME LIKE '%" + technologyRegions + "%' " + queryRange +
											 * ""; break; case "applyButtLineChart":
											 * timeRevenueStackandLineChartQueryString =
											 * "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
											 * +date+"' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name LIKE '%"
											 * + site + "%' and t.AREA_NAME LIKE '%" + area +
											 * "%' and t.REGION_NAME LIKE '%" + technologyRegions + "%' " + queryRange +
											 * ""; break;
											 */
											case "applyButtPieChart":
												applyFilterRevSum = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
														+ queryRange + "";
												break;
											}
										}
										// Select all not null sites
										else {
											switch (filterID) {
											case "applyButtMSChart":
												applyFilterRevSum = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
														+ queryRange
														+ " group by site_name, area_id order by site_name asc";
												break;

											case "applyButtPieChart":
												applyFilterRevSum = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name !='null' "
														+ queryRange + "";
												break;
											}
										}
									}

								}
								// if there is a technology
								else if (finalSiteTechList != "") {

									// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {
										switch (filterID) {
										case "applyButtMSChart":

											applyFilterRevSum = "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0) ,coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0), coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), AREA_ID ,revenue_date,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
													+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ "  and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
													+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t group by t.site_name,t.area_id order by t.site_name asc ";

											break;
										/*
										 * case "applyButtStackChart": timeRevenueStackandLineChartQueryString =
										 * "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\",coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\",coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
										 * +date+"' as \"Date\" from (" + " SELECT DISTINCT a.WARE_NAME,a.SITE_ID," +
										 * "b.site_name as site_name,b.area_id as AREA_ID , " +
										 * "sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  "
										 * +
										 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
										 * + "and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"
										 * +area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+
										 * paramTechnologies+ " "+
										 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id) t order by t.site_name asc"
										 * ; break; case "applyButtLineChart": timeRevenueStackandLineChartQueryString =
										 * "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\",coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\",coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
										 * +date+"' as \"Date\" from (" + " SELECT DISTINCT a.WARE_NAME,a.SITE_ID," +
										 * "b.site_name as site_name,b.area_id as AREA_ID , " +
										 * "sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  "
										 * +
										 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
										 * + "and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"
										 * +area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+
										 * paramTechnologies+ " "+
										 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id) t order by t.site_name asc"
										 * ; break;
										 */
										case "applyButtPieChart":

											applyFilterRevSum = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
													+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, AREA_ID ,revenue_date,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
													+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
													+ "  order by b.site_name) group by site_name,revenue_date, AREA_ID)t order by t.site_name asc )";

											break;
										}

									}

								}

								switch (filterID) {
								case "applyButtMSChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									chartJSNArr = chartRevenues(applyFilterQuery.list());

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
									} else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));

									}
									break;

								case "applyButtPieChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("pieRevenue", mapper.writeValueAsString(applyFilterQuery.list()));

									} else {
										rtn.put("pieRevenue", mapper.writeValueAsString(""));
									}
									break;

								case "applyButtStackChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;
								}

							} else {
								switch (filterID) {
								case "applyButtMSChart":
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									break;

								case "applyButtPieChart":
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								}
							}
						} // END OF ACC

					} // end if site is null

					else {
						// if site is exist
						System.out.println("/*/*Here site is selected");
						// period is daily
						if (StringUtils.equalsIgnoreCase(period, "Daily")) {

							// no technology is selected
							if (finalSiteTechList == "") {

								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {
									case "applyButtMSChart":
										applyFilterRevSum = "SELECT * FROM(select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
												+ finalSitesNameList + " ) "
												+ " group by site_name, area_id order by site_name asc) " + queryyRange;
										break;
									case "applyButtStackChart":
										applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
												+ finalSitesNameList + " ) " + queryRange
												+ " group by REVENUE_DATE order by REVENUE_DATE desc";
										break;
									case "applyButtLineChart":
										applyFilterRevSum = "select TO_CHAR(revenue_date, 'YYYY/MM/DD') as revenueDate, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue,(sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
												+ finalSitesNameList + " ) " + queryRange
												+ " group by REVENUE_DATE order by REVENUE_DATE desc";
										break;
									case "applyButtPieChart":

										applyFilterRevSum = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 "
												+ " and site_name IN ( " + finalSitesNameList + " )" + queryRange + " ";

										break;
									}

								}

							}
							// if there is a technology
							else if (finalSiteTechList != "") {
								/*
								 * // IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
								 * if(StringUtils.equalsIgnoreCase(max,"Max") &&
								 * StringUtils.equalsIgnoreCase(min,"Min")) { switch (filterID) {
								 * 
								 * case "applyButtStackChartMax": maxTimeRevenueStackandLineChartQueryString=
								 * "select TO_CHAR(t.revenue_date, 'DD/MM/YYYY') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
								 * +
								 * "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by  rev_sum desc) as RANK from "
								 * +
								 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
								 * +
								 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
								 * +site+"%'  and "+ paramTechnologies+ " "+
								 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date order by rev_sum desc)"
								 * + ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";
								 * 
								 * break;
								 * 
								 * case "applyButtStackChartMin": minTimeRevenueStackandLineChartQueryString=
								 * "select TO_CHAR(t.revenue_date, 'DD/MM/YYYY') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
								 * +
								 * "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by  rev_sum asc) as RANK from "
								 * +
								 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
								 * +
								 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
								 * +site+"%'  and "+ paramTechnologies+ " "+
								 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date order by rev_sum asc)"
								 * + ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";
								 * 
								 * break;
								 * 
								 * case "applyButtLineChartMax": maxTimeRevenueStackandLineChartQueryString=
								 * "select TO_CHAR(t.revenue_date, 'DD/MM/YYYY') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
								 * +
								 * "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by  rev_sum desc) as RANK from "
								 * +
								 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
								 * +
								 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
								 * +site+"%'  and "+ paramTechnologies+ " "+
								 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date order by rev_sum desc)"
								 * + ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";
								 * 
								 * break;
								 * 
								 * case "applyButtLineChartMin": minTimeRevenueStackandLineChartQueryString=
								 * "select TO_CHAR(t.revenue_date, 'DD/MM/YYYY') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
								 * +
								 * "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by  rev_sum asc) as RANK from "
								 * +
								 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
								 * +
								 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
								 * +site+"%'  and "+ paramTechnologies+ " "+
								 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date order by rev_sum asc)"
								 * + ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";
								 * 
								 * break;
								 * 
								 * }
								 * 
								 * } // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
								 * if(StringUtils.equalsIgnoreCase(max,"Max") &&
								 * StringUtils.equalsIgnoreCase(min,null)) { switch (filterID) {
								 * 
								 * case "applyButtStackChartMax": maxTimeRevenueStackandLineChartQueryString=
								 * "select TO_CHAR(t.revenue_date, 'DD/MM/YYYY') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
								 * +
								 * "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by  rev_sum desc) as RANK from "
								 * +
								 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
								 * +
								 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
								 * +site+"%'  and "+ paramTechnologies+ " "+
								 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date order by rev_sum desc)"
								 * + ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";
								 * 
								 * 
								 * break;
								 * 
								 * case "applyButtLineChartMax": maxTimeRevenueStackandLineChartQueryString=
								 * "select TO_CHAR(t.revenue_date, 'DD/MM/YYYY') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
								 * +
								 * "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by  rev_sum desc) as RANK from "
								 * +
								 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
								 * +
								 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
								 * +site+"%'  and "+ paramTechnologies+ " "+
								 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date order by rev_sum desc)"
								 * + ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";
								 * 
								 * break;
								 * 
								 * 
								 * } } // IF MIN IS ONLY CHECKED WITH TECHNOLOGY
								 * if(StringUtils.equalsIgnoreCase(max,null) &&
								 * StringUtils.equalsIgnoreCase(min,"Min")) { switch (filterID) {
								 * 
								 * case "applyButtStackChartMin": minTimeRevenueStackandLineChartQueryString=
								 * "select TO_CHAR(t.revenue_date, 'DD/MM/YYYY') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
								 * +
								 * "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
								 * +
								 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
								 * +
								 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
								 * +site+"%'  and "+ paramTechnologies+ " "+
								 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date order by rev_sum asc)"
								 * + ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";
								 * 
								 * break;
								 * 
								 * case "applyButtLineChartMin": minTimeRevenueStackandLineChartQueryString=
								 * "select TO_CHAR(t.revenue_date, 'DD/MM/YYYY') as revenueDate , sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( "
								 * +
								 * "select rev_sum,revenue_date,site_name ,AREA_ID,voice_revenue,sms_revenue,data_revenue,vas_revenue, RANK() over(PARTITION by REVENUE_DATE order by rev_sum asc) as RANK from "
								 * +
								 * "(SELECT DISTINCT a.WARE_NAME as ware,a.SITE_ID as site_id, b.site_name as site_name,b.area_id as AREA_ID,  b.revenue_date as revenue_date, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue) as rev_sum,sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue "
								 * +
								 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
								 * +site+"%'  and "+ paramTechnologies+ " "+
								 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id, b.revenue_date order by rev_sum asc)"
								 * + ") t where RANK = 1 group by t.revenue_date order by t.revenue_date desc";
								 * 
								 * break;
								 * 
								 * } }
								 */
								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {
									case "applyButtMSChart":

										applyFilterRevSum = "select t.site_name,t.area_id, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date, AREA_ID ,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ")  and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by site_name, AREA_ID, revenue_date )t group by t.site_name,t.area_id order by t.site_name asc ";

										break;

									case "applyButtStackChart":

										applyFilterRevSum = "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date,site_name, AREA_ID ,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ") and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t group by t.revenue_date order by t.revenue_date desc ";

										break;
									case "applyButtLineChart":

										applyFilterRevSum = "select t.revenue_date, sum(t.voice_revenue) as tot_voice, sum(t.sms_revenue) as tot_sms ,sum(t.data_revenue) as tot_data , sum(t.vas_revenue) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),revenue_date,site_name, AREA_ID ,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ")  and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by combination_tech, site_name, AREA_ID, revenue_date )t group by t.revenue_date order by t.revenue_date desc ";

										break;
									case "applyButtPieChart":
										applyFilterRevSum = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
												+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT revenue_date,site_name, AREA_ID ,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID ,TO_CHAR(b.revenue_date, 'YYYY-MM-DD') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ") and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by site_name, AREA_ID, revenue_date )t group by t.revenue_date order by t.revenue_date asc )";

										break;

									}
								}

							}

							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {

								switch (filterID) {
								case "applyButtMSChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);
									chartJSNArr = chartRevenues(applyFilterQuery.list());
									if (applyFilterQuery.list().size() > 0) {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
									} else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									}
									break;
								case "applyButtStackChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);
									// perDataChrtLst = timeRevenueStackandLineChartQuery.list();
									// periodicData = getStackedChartData(perDataChrtLst);
									// System.out.println("######StackedandLineRevenue "+ periodicData);
									if (applyFilterQuery.list().size() > 0) {

										periodicData = getStackedChartData(applyFilterQuery.list());
										rtn.put("StackedandLineRevenue", mapper.writeValueAsString(periodicData));

									} else {
										rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));

									}
									break;
								case "applyButtLineChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);
									// periodicData = getStackedChartData(perDataChrtLst);
									// System.out.println("######StackedandLineRevenue "+ periodicData);
									if (applyFilterQuery.list().size() > 0) {

										periodicData = getStackedChartData(applyFilterQuery.list());
										rtn.put("StackedandLineRevenue", mapper.writeValueAsString(periodicData));

									} else {
										rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));

									}
									break;
								case "applyButtPieChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("pieRevenue", mapper.writeValueAsString(applyFilterQuery.list()));

									} else {
										rtn.put("pieRevenue", mapper.writeValueAsString(""));

									}
									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;
								}

							} else {
								switch (filterID) {
								case "applyButtMSChart":
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									break;

								case "applyButtPieChart":
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								}
							}

							/*
							 * case "applyButtStackChartMax": maxTimeRevenueStackandLineChartQuery =
							 * session.createNativeQuery(maxTimeRevenueStackandLineChartQueryString);
							 * maxTimeRevenueStackandLineChartQuery.setParameter("param1", StartDate);
							 * maxTimeRevenueStackandLineChartQuery.setParameter("param2", EndDate);
							 * maxPerDataChrtLst = maxTimeRevenueStackandLineChartQuery.list();
							 * maxPeriodicData = getStackedChartData(maxPerDataChrtLst);
							 * 
							 * if (maxPerDataChrtLst.size()>0) { rtn.put("MaxStackedandLineRevenue",
							 * mapper.writeValueAsString(maxPeriodicData)); } else {
							 * rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString("")); }
							 * 
							 * 
							 * break;
							 * 
							 * case "applyButtStackChartMin": minTimeRevenueStackandLineChartQuery =
							 * session.createNativeQuery(minTimeRevenueStackandLineChartQueryString);
							 * minTimeRevenueStackandLineChartQuery.setParameter("param1", StartDate);
							 * minTimeRevenueStackandLineChartQuery.setParameter("param2", EndDate);
							 * minPerDataChrtLst = minTimeRevenueStackandLineChartQuery.list();
							 * minPeriodicData = getStackedChartData(minPerDataChrtLst);
							 * 
							 * if (minPerDataChrtLst.size()>0) { rtn.put("MinStackedandLineRevenue",
							 * mapper.writeValueAsString(minPeriodicData)); } else {
							 * rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString("")); }
							 * 
							 * 
							 * break;
							 * 
							 * case "applyButtLineChartMax": maxTimeRevenueStackandLineChartQuery =
							 * session.createNativeQuery(maxTimeRevenueStackandLineChartQueryString);
							 * maxTimeRevenueStackandLineChartQuery.setParameter("param1", StartDate);
							 * maxTimeRevenueStackandLineChartQuery.setParameter("param2", EndDate);
							 * maxPerDataChrtLst = maxTimeRevenueStackandLineChartQuery.list();
							 * maxPeriodicData = getStackedChartData(maxPerDataChrtLst);
							 * 
							 * if (maxPerDataChrtLst.size()>0) { rtn.put("MaxStackedandLineRevenue",
							 * mapper.writeValueAsString(maxPeriodicData)); } else {
							 * rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString("")); }
							 * 
							 * break;
							 * 
							 * case "applyButtLineChartMin": minTimeRevenueStackandLineChartQuery =
							 * session.createNativeQuery(minTimeRevenueStackandLineChartQueryString);
							 * minTimeRevenueStackandLineChartQuery.setParameter("param1", StartDate);
							 * minTimeRevenueStackandLineChartQuery.setParameter("param2", EndDate);
							 * minPerDataChrtLst = minTimeRevenueStackandLineChartQuery.list();
							 * minPeriodicData = getStackedChartData(minPerDataChrtLst);
							 * 
							 * if (minPerDataChrtLst.size()>0) { rtn.put("MinStackedandLineRevenue",
							 * mapper.writeValueAsString(minPeriodicData)); } else {
							 * rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString("")); }
							 * 
							 * break;
							 */
							// }

						} // DAILY ENDED
							// START OF WEEKLY
						if (StringUtils.equalsIgnoreCase(period, "Weekly")) {

							System.out.println("site is selectedd");

							System.out.println("the start date is " + StartDate);
							double diff = (EndDate.getTime() - StartDate.getTime()) / 1000.0;
							diff /= (60 * 60 * 24 * 7);
							System.out.println("the week difference is before " + diff);
							diff = Math.abs(Math.ceil(diff));
							System.out.println("the week difference is after " + diff);

							String date = "";
							Date weeklyDate;
							Timestamp weeklyEndDate = StartDate;
							Calendar cal = Calendar.getInstance();
							Timestamp weeklyStartDate = StartDate;

							// Tech is not selected
							if (finalSiteTechList == "") {

								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {

									switch (filterID) {
									case "applyButtMSChart":
										applyFilterRevSum = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
												+ finalSitesNameList + " ) " + queryRange
												+ " group by site_name, area_id order by site_name asc";
										break;
									case "applyButtStackChart":
										applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date
												+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name IN ("
												+ finalSitesNameList + " ) " + queryRange + "";
										break;
									case "applyButtLineChart":
										applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date
												+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name IN ("
												+ finalSitesNameList + " ) " + queryRange + "";
										break;
									case "applyButtPieChart":
										applyFilterRevSum = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
												+ finalSitesNameList + " )" + queryRange + "";
										break;
									}
								}

							}
							// if there is a technology
							else if (finalSiteTechList != "") {

								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {
									case "applyButtMSChart":
										applyFilterRevSum = "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0) ,coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0), coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ")  and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t group by t.site_name,t.area_id order by t.site_name asc ";

										break;
									case "applyButtStackChart":
										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\",coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\",coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date
												+ "' as \"Date\" from (SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ") and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t order by t.site_name asc";
										break;
									case "applyButtLineChart":
										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\",coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\",coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date
												+ "' as \"Date\" from (SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ") and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t order by t.site_name asc";
										break;
									case "applyButtPieChart":
										applyFilterRevSum = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
												+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ")and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t order by t.site_name asc )";

										break;
									}
								}

							}
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {

								switch (filterID) {
								case "applyButtMSChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									chartJSNArr = chartRevenues(applyFilterQuery.list());

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
									} else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));

									}
									break;

								case "applyButtPieChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("pieRevenue", mapper.writeValueAsString(applyFilterQuery.list()));

									} else {
										rtn.put("pieRevenue", mapper.writeValueAsString(""));
									}
									break;

								case "applyButtStackChart":

									for (i = 0; i < diff; i++) {

										weeklyEndDate = weeklyStartDate;
										cal.setTime(weeklyEndDate);
										cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
										weeklyDate = cal.getTime();
										System.out.println(weeklyDate);
										// weeklyEndDate = formatter.parse(weeklyEndDate);
										weeklyEndDate = new Timestamp(weeklyDate.getTime());
										System.out.println("for week " + i + ":" + weeklyEndDate);

										if ((weeklyEndDate).compareTo(EndDate) > 0) {
											System.out.println("the weeklyEndDate is greater than enddate");
											weeklyEndDate = EndDate;
											// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth() +1)
											// +"-" + (EndDate.getDate()) +"/"+ (EndDate.getMonth() +1);

										} else {
											System.out.println("the weeklyEndDate is less than enddate");

										}
										// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth() +1)+"/"+
										// (weeklyStartDate.getYear()+1900)+"-"+ (weeklyEndDate.getDate())+"/"+
										// (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getYear()+1900);
										date = (weeklyStartDate.getYear() + 1900) + "/"
												+ (weeklyStartDate.getMonth() + 1) + "/" + (weeklyStartDate.getDate())
												+ "-" + (weeklyEndDate.getYear() + 1900) + "/"
												+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", weeklyStartDate);
										applyFilterQuery.setParameter("param2", weeklyEndDate);
										revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
												.addScalar("voiceRevenue").addScalar("smsRevenue")
												.addScalar("dataRevneue").addScalar("vasRevenue")
												.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
														Transformers.aliasToBean(RevenueChartsReport.class))
												.list();
										revenueCharts.get(0).setDate(date);
										// System.out.println("for week " + i + " the date is :" + date);
										revenueChartList.addAll(revenueCharts);

										weeklyStartDate = weeklyEndDate;
										cal.setTime(weeklyStartDate);
										cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
										weeklyDate = cal.getTime();
										weeklyStartDate = new Timestamp(weeklyDate.getTime());

										System.out.println("after start date " + weeklyStartDate);

									}

									revenueChartListFinal = getPeriodicChartData(revenueChartList);
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(revenueChartListFinal));
									break;

								case "applyButtLineChart":

									for (i = 0; i < diff; i++) {

										weeklyEndDate = weeklyStartDate;
										cal.setTime(weeklyEndDate);
										cal.add(Calendar.DAY_OF_MONTH, 6); // 7 is the days you want to add or subtract
										weeklyDate = cal.getTime();
										System.out.println(weeklyDate);
										// weeklyEndDate = formatter.parse(weeklyEndDate);
										weeklyEndDate = new Timestamp(weeklyDate.getTime());
										System.out.println("for week " + i + ":" + weeklyEndDate);

										if ((weeklyEndDate).compareTo(EndDate) > 0) {
											System.out.println("the weeklyEndDate is greater than enddate");
											weeklyEndDate = EndDate;
											// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth() +1)
											// +"-" + (EndDate.getDate()) +"/"+ (EndDate.getMonth() +1);

										} else {
											System.out.println("the weeklyEndDate is less than enddate");

										}

										// date = (weeklyStartDate.getDate()) +"/"+ (weeklyStartDate.getMonth() +1)+"/"+
										// (weeklyStartDate.getYear()+1900)+"-"+ (weeklyEndDate.getDate())+"/"+
										// (weeklyEndDate.getMonth() +1)+"/"+ (weeklyEndDate.getYear()+1900);
										date = (weeklyStartDate.getYear() + 1900) + "/"
												+ (weeklyStartDate.getMonth() + 1) + "/" + (weeklyStartDate.getDate())
												+ "-" + (weeklyEndDate.getYear() + 1900) + "/"
												+ (weeklyEndDate.getMonth() + 1) + "/" + (weeklyEndDate.getDate());

										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", weeklyStartDate);
										applyFilterQuery.setParameter("param2", weeklyEndDate);
										revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
												.addScalar("voiceRevenue").addScalar("smsRevenue")
												.addScalar("dataRevneue").addScalar("vasRevenue")
												.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
														Transformers.aliasToBean(RevenueChartsReport.class))
												.list();
										revenueCharts.get(0).setDate(date);
										// System.out.println("for week " + i + " the date is :" + date);
										revenueChartList.addAll(revenueCharts);

										weeklyStartDate = weeklyEndDate;
										cal.setTime(weeklyStartDate);
										cal.add(Calendar.DAY_OF_MONTH, 1); // 7 is the days you want to add or subtract
										weeklyDate = cal.getTime();
										weeklyStartDate = new Timestamp(weeklyDate.getTime());

										System.out.println("after start date " + weeklyStartDate);

									}

									revenueChartListFinal = getPeriodicChartData(revenueChartList);
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(revenueChartListFinal));
									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;
								}

							} else {
								switch (filterID) {
								case "applyButtMSChart":
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									break;

								case "applyButtPieChart":
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								}
							}

						} // END OF WEEKLY

						// start of monthly
						if (StringUtils.equalsIgnoreCase(period, "Monthly")) {

							System.out.println("Monthy and site is selectedd");

							System.out.println("the start date is " + StartDate);
							Calendar m = Calendar.getInstance();
							m.setTime(StartDate);
							int nMonth1 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
							m.setTime(EndDate);
							int nMonth2 = 12 * m.get(Calendar.YEAR) + m.get(Calendar.MONTH);
							int diff = Math.abs(nMonth2 - nMonth1);

							System.out.println("the Month difference is " + diff);
							Timestamp month = StartDate;
							String date = "";

							if (finalSiteTechList == "") {

								// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {
									case "applyButtMSChart":
										applyFilterRevSum = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
												+ finalSitesNameList + " ) " + queryRange
												+ " group by site_name, area_id order by site_name asc";
										break;
									case "applyButtStackChart":
										applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date
												+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name IN ("
												+ finalSitesNameList + " ) " + queryRange + "";
										break;
									case "applyButtLineChart":
										applyFilterRevSum = "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date
												+ "' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name IN ("
												+ finalSitesNameList + " ) " + queryRange + "";
										break;
									case "applyButtPieChart":
										applyFilterRevSum = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ("
												+ finalSitesNameList + " ) " + queryRange + "";
										break;
									}

								}

							}
							// if there is a technology
							else if (finalSiteTechList != "") {

								// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
								if (StringUtils.equalsIgnoreCase(max, null)
										&& StringUtils.equalsIgnoreCase(min, null)) {
									switch (filterID) {
									case "applyButtMSChart":
										applyFilterRevSum = "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0) ,coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0), coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ") and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t group by t.site_name,t.area_id order by t.site_name asc ";
										break;
									case "applyButtStackChart":
										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\",coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\",coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date
												+ "' as \"Date\" from (SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ")  and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t order by t.site_name asc";

										break;
									case "applyButtLineChart":
										applyFilterRevSum = "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\",coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\",coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
												+ date
												+ "' as \"Date\" from (SELECT sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue ,combination_tech from( "
												+ " SELECT DISTINCT b.site_name as site_name,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, "
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ") and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t order by t.site_name asc";

										break;
									case "applyButtPieChart":
										applyFilterRevSum = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
												+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, AREA_ID ,revenue_date,"
												+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
												+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
												+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
												+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
												+ " and b.site_name IN (" + finalSitesNameList
												+ ") and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
												+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t order by t.site_name asc )";

										break;
									}
								}

							}

							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {

								switch (filterID) {
								case "applyButtMSChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									chartJSNArr = chartRevenues(applyFilterQuery.list());

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
									} else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));

									}
									break;

								case "applyButtPieChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("pieRevenue", mapper.writeValueAsString(applyFilterQuery.list()));

									} else {
										rtn.put("pieRevenue", mapper.writeValueAsString(""));
									}
									break;

								case "applyButtStackChart":

									for (i = 0; i <= diff; i++) {

										String startmonth = formatter.format(month);
										Date monthDate = formatter.parse(startmonth);
										Timestamp monthofDate = new Timestamp(monthDate.getTime());

										Calendar calendar = Calendar.getInstance();
										calendar.setTime(monthofDate);
										calendar.add(Calendar.MONTH, 1);
										calendar.set(Calendar.DAY_OF_MONTH, 1);
										calendar.add(Calendar.DATE, -1);
										Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

										String endMonth = formatter.format(lastDayOfMonth);
										Date endOfDate = formatter.parse(endMonth);
										Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
										System.out.println("for month " + i + ":" + endOfMonth);

										if ((endOfMonth).compareTo(EndDate) > 0) {
											System.out.println("the endOfMonth is greater than enddate");
											endOfMonth = EndDate;

										} else {
											System.out.println("the endOfMonth is less than enddate");

										}
										// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
										// (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
										// (endOfMonth.getMonth() +1) +"/"+ (monthofDate.getYear()+1900);
										date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1) + "/"
												+ (monthofDate.getDate()) + "-" + (endOfMonth.getYear() + 1900) + "/"
												+ (endOfMonth.getMonth() + 1) + "/" + (endOfMonth.getDate());

										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", monthofDate);
										applyFilterQuery.setParameter("param2", endOfMonth);
										revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
												.addScalar("voiceRevenue").addScalar("smsRevenue")
												.addScalar("dataRevneue").addScalar("vasRevenue")
												.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
														Transformers.aliasToBean(RevenueChartsReport.class))
												.list();
										revenueCharts.get(0).setDate(date);
										// System.out.println("for week " + i + " the date is :" + date);
										// System.out.println("the
										// revenueChartsList"+mapper.writeValueAsString(revenueCharts));
										revenueChartList.addAll(revenueCharts);

										calendar.setTime(endOfMonth);
										calendar.add(Calendar.DATE, +1);
										Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
										month = NextDayOfMonth;
										System.out.println("after start date " + NextDayOfMonth);

									}
									revenueChartListFinal = getPeriodicChartData(revenueChartList);
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(revenueChartListFinal));

									break;

								case "applyButtLineChart":

									for (i = 0; i <= diff; i++) {

										String startmonth = formatter.format(month);
										Date monthDate = formatter.parse(startmonth);
										Timestamp monthofDate = new Timestamp(monthDate.getTime());

										Calendar calendar = Calendar.getInstance();
										calendar.setTime(monthofDate);
										calendar.add(Calendar.MONTH, 1);
										calendar.set(Calendar.DAY_OF_MONTH, 1);
										calendar.add(Calendar.DATE, -1);
										Timestamp lastDayOfMonth = new Timestamp(calendar.getTime().getTime());

										String endMonth = formatter.format(lastDayOfMonth);
										Date endOfDate = formatter.parse(endMonth);
										Timestamp endOfMonth = new Timestamp(endOfDate.getTime());
										System.out.println("for month " + i + ":" + endOfMonth);

										if ((endOfMonth).compareTo(EndDate) > 0) {
											System.out.println("the endOfMonth is greater than enddate");
											endOfMonth = EndDate;

										} else {
											System.out.println("the endOfMonth is less than enddate");

										}
										// date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1)
										// +"/"+(monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
										// (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
										date = (monthofDate.getYear() + 1900) + "/" + (monthofDate.getMonth() + 1) + "/"
												+ (monthofDate.getDate()) + "-" + (endOfMonth.getYear() + 1900) + "/"
												+ (endOfMonth.getMonth() + 1) + "/" + (endOfMonth.getDate());

										applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
										applyFilterQuery.setParameter("param1", monthofDate);
										applyFilterQuery.setParameter("param2", endOfMonth);
										revenueCharts = (List<RevenueChartsReport>) ((SQLQuery) applyFilterQuery)
												.addScalar("voiceRevenue").addScalar("smsRevenue")
												.addScalar("dataRevneue").addScalar("vasRevenue")
												.addScalar("totalRevenue").addScalar("Date").setResultTransformer(
														Transformers.aliasToBean(RevenueChartsReport.class))
												.list();
										revenueCharts.get(0).setDate(date);
										// System.out.println("for week " + i + " the date is :" + date);
										// System.out.println("the
										// revenueChartsList"+mapper.writeValueAsString(revenueCharts));
										revenueChartList.addAll(revenueCharts);

										calendar.setTime(endOfMonth);
										calendar.add(Calendar.DATE, +1);
										Timestamp NextDayOfMonth = new Timestamp(calendar.getTime().getTime());
										month = NextDayOfMonth;
										// System.out.println("after start date " + NextDayOfMonth);

									}

									revenueChartListFinal = getPeriodicChartData(revenueChartList);
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(revenueChartListFinal));

									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;
								}

							} else {
								switch (filterID) {
								case "applyButtMSChart":
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									break;

								case "applyButtPieChart":
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								}
							}

							/*
							 * case "applyButtStackChartMax": for (i = 0; i <= diff; i++) {
							 * 
							 * String startmonth = formatter.format(month); Date monthDate =
							 * formatter.parse(startmonth); Timestamp monthofDate = new
							 * Timestamp(monthDate.getTime());
							 * 
							 * Calendar calendar = Calendar.getInstance(); calendar.setTime(monthofDate);
							 * calendar.add(Calendar.MONTH, 1); calendar.set(Calendar.DAY_OF_MONTH, 1);
							 * calendar.add(Calendar.DATE, -1); Timestamp lastDayOfMonth = new
							 * Timestamp(calendar.getTime().getTime());
							 * 
							 * String endMonth = formatter.format(lastDayOfMonth); Date endOfDate =
							 * formatter.parse(endMonth); Timestamp endOfMonth = new
							 * Timestamp(endOfDate.getTime()); System.out.println("for month " + i + ":" +
							 * endOfMonth);
							 * 
							 * 
							 * if ((endOfMonth).compareTo(EndDate) > 0) {
							 * System.out.println("the endOfMonth is greater than enddate"); endOfMonth =
							 * EndDate;
							 * 
							 * } else { System.out.println("the endOfMonth is less than enddate");
							 * 
							 * } date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
							 * (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
							 * (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
							 * 
							 * 
							 * if(StringUtils.equalsIgnoreCase(max,"Max")) {
							 * maxTimeRevenueStackandLineChartQuery =
							 * session.createNativeQuery(maxTimeRevenueStackandLineChartQueryString);
							 * maxTimeRevenueStackandLineChartQuery.setParameter("param1", monthofDate);
							 * maxTimeRevenueStackandLineChartQuery.setParameter("param2", endOfMonth);
							 * maxRevenueCharts = (List<RevenueChartsReport>) ((SQLQuery)
							 * maxTimeRevenueStackandLineChartQuery)
							 * .addScalar("voiceRevenue").addScalar("smsRevenue").addScalar("dataRevneue").
							 * addScalar("vasRevenue").addScalar("totalRevenue").addScalar("Date")
							 * .setResultTransformer(Transformers.aliasToBean(RevenueChartsReport.class)).
							 * list(); maxRevenueCharts.get(0).setDate(date);
							 * //System.out.println("for week " + i + " the date is :" + date);
							 * System.out.println("the maxRevenueChartsList"+mapper.writeValueAsString(
							 * maxRevenueCharts)); maxRevenueChartList.addAll(maxRevenueCharts); }
							 * 
							 * calendar.setTime(endOfMonth); calendar.add(Calendar.DATE, +1); Timestamp
							 * NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); month =
							 * NextDayOfMonth; System.out.println("after start date " + NextDayOfMonth);
							 * 
							 * }
							 * 
							 * 
							 * //System.out.println("Max or Min Charts");
							 * 
							 * if(StringUtils.equalsIgnoreCase(max,"Max")) {
							 * maxFinalRevenueReportResults.addAll(maxRevenueChartList);
							 * System.out.println("after maxFinalRevenueReportResults " +
							 * mapper.writeValueAsString(maxFinalRevenueReportResults));
							 * if(maxFinalRevenueReportResults.size()>0) {
							 * System.out.println("****The maxFinalRevenueReportResults.get(0) is:  "+mapper
							 * .writeValueAsString(maxFinalRevenueReportResults.get(0)));
							 * RevenueChartsReport tempRevChart = RevenueStackAndLineChartMax =
							 * maxFinalRevenueReportResults.get(0); TempTotalRevMax =
							 * (tempRevChart.getTotalRevenue()).intValue(); for(int j = 1; j<
							 * maxFinalRevenueReportResults.size(); j++) { RevenueChartsReport
							 * RevenueStackAndLineChart = maxFinalRevenueReportResults.get(j);
							 * totalRevenueMax = (RevenueStackAndLineChart.getTotalRevenue()).intValue();
							 * if(TempTotalRevMax < totalRevenueMax){ TempTotalRevMax = totalRevenueMax;
							 * RevenueStackAndLineChartMax = RevenueStackAndLineChart;
							 * System.out.println("************the RevenueStackAndLineChartMax is "+mapper.
							 * writeValueAsString(RevenueStackAndLineChartMax));
							 * 
							 * } } } RevenueStackAndLineChartMaxFinal.add(RevenueStackAndLineChartMax);
							 * maxRevenueChartListFinal =
							 * getPeriodicChartData(RevenueStackAndLineChartMaxFinal);
							 * //System.out.println("after maxRevenueChartListFinal " +
							 * mapper.writeValueAsString(maxRevenueChartListFinal));
							 * 
							 * if (maxFinalRevenueReportResults.size()>0) {
							 * rtn.put("MaxStackedandLineRevenue",
							 * mapper.writeValueAsString(maxRevenueChartListFinal)); } else {
							 * rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString("")); } }
							 * 
							 * break;
							 * 
							 * case "applyButtStackChartMin": for (i = 0; i <= diff; i++) {
							 * 
							 * String startmonth = formatter.format(month); Date monthDate =
							 * formatter.parse(startmonth); Timestamp monthofDate = new
							 * Timestamp(monthDate.getTime());
							 * 
							 * Calendar calendar = Calendar.getInstance(); calendar.setTime(monthofDate);
							 * calendar.add(Calendar.MONTH, 1); calendar.set(Calendar.DAY_OF_MONTH, 1);
							 * calendar.add(Calendar.DATE, -1); Timestamp lastDayOfMonth = new
							 * Timestamp(calendar.getTime().getTime());
							 * 
							 * String endMonth = formatter.format(lastDayOfMonth); Date endOfDate =
							 * formatter.parse(endMonth); Timestamp endOfMonth = new
							 * Timestamp(endOfDate.getTime()); System.out.println("for month " + i + ":" +
							 * endOfMonth);
							 * 
							 * 
							 * if ((endOfMonth).compareTo(EndDate) > 0) {
							 * System.out.println("the endOfMonth is greater than enddate"); endOfMonth =
							 * EndDate;
							 * 
							 * } else { System.out.println("the endOfMonth is less than enddate");
							 * 
							 * } date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
							 * (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
							 * (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
							 * 
							 * if(StringUtils.equalsIgnoreCase(min,"Min")) {
							 * minTimeRevenueStackandLineChartQuery =
							 * session.createNativeQuery(minTimeRevenueStackandLineChartQueryString);
							 * minTimeRevenueStackandLineChartQuery.setParameter("param1", monthofDate);
							 * minTimeRevenueStackandLineChartQuery.setParameter("param2", endOfMonth);
							 * minRevenueCharts = (List<RevenueChartsReport>) ((SQLQuery)
							 * minTimeRevenueStackandLineChartQuery)
							 * .addScalar("voiceRevenue").addScalar("smsRevenue").addScalar("dataRevneue").
							 * addScalar("vasRevenue").addScalar("totalRevenue").addScalar("Date")
							 * .setResultTransformer(Transformers.aliasToBean(RevenueChartsReport.class)).
							 * list(); minRevenueCharts.get(0).setDate(date);
							 * System.out.println("the minRevenueChartsList"+mapper.writeValueAsString(
							 * minRevenueCharts)); if( minRevenueCharts.get(0).getTotalRevenue() != null) {
							 * minRevenueChartList.addAll(minRevenueCharts); } }
							 * 
							 * 
							 * calendar.setTime(endOfMonth); calendar.add(Calendar.DATE, +1); Timestamp
							 * NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); month =
							 * NextDayOfMonth; System.out.println("after start date " + NextDayOfMonth);
							 * 
							 * }
							 * 
							 * 
							 * // System.out.println("Max or Min Charts");
							 * 
							 * if(StringUtils.equalsIgnoreCase(min,"Min")) {
							 * System.out.println("BEFORE minRevenueChartList " +
							 * mapper.writeValueAsString(minRevenueChartList));
							 * minFinalRevenueReportResults.addAll(minRevenueChartList);
							 * System.out.println("after minFinalRevenueReportResults " +
							 * mapper.writeValueAsString(minFinalRevenueReportResults));
							 * 
							 * if(minFinalRevenueReportResults.size()>0) {
							 * System.out.println("****The minFinalRevenueReportResults.get(0) is:  "+mapper
							 * .writeValueAsString(minFinalRevenueReportResults.get(0)));
							 * RevenueChartsReport tempRevChart = RevenueStackAndLineChartMin =
							 * minFinalRevenueReportResults.get(0); TempTotalRevMin =
							 * (tempRevChart.getTotalRevenue()).intValue(); for(int j = 1; j <
							 * minFinalRevenueReportResults.size(); j++) { RevenueChartsReport
							 * RevenueStackAndLineChart = minFinalRevenueReportResults.get(j);
							 * totalRevenueMin = (RevenueStackAndLineChart.getTotalRevenue()).intValue();
							 * if(TempTotalRevMin > totalRevenueMin){ TempTotalRevMin = totalRevenueMin;
							 * RevenueStackAndLineChartMin = minFinalRevenueReportResults.get(j);
							 * System.out.println("************the RevenueStackAndLineChartMin is "+mapper.
							 * writeValueAsString(RevenueStackAndLineChartMin));
							 * 
							 * } } }
							 * 
							 * 
							 * RevenueStackAndLineChartMinFinal.add(RevenueStackAndLineChartMin);
							 * minRevenueChartListFinal =
							 * getPeriodicChartData(RevenueStackAndLineChartMinFinal);
							 * System.out.println("after minRevenueChartListFinal " +
							 * mapper.writeValueAsString(minRevenueChartListFinal));
							 * 
							 * if (RevenueStackAndLineChartMinFinal.size()>0) {
							 * rtn.put("MinStackedandLineRevenue",
							 * mapper.writeValueAsString(minRevenueChartListFinal)); } else {
							 * rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString("")); }
							 * 
							 * } break;
							 * 
							 * case "applyButtLineChartMax": for (i = 0; i <= diff; i++) {
							 * 
							 * String startmonth = formatter.format(month); Date monthDate =
							 * formatter.parse(startmonth); Timestamp monthofDate = new
							 * Timestamp(monthDate.getTime());
							 * 
							 * Calendar calendar = Calendar.getInstance(); calendar.setTime(monthofDate);
							 * calendar.add(Calendar.MONTH, 1); calendar.set(Calendar.DAY_OF_MONTH, 1);
							 * calendar.add(Calendar.DATE, -1); Timestamp lastDayOfMonth = new
							 * Timestamp(calendar.getTime().getTime());
							 * 
							 * String endMonth = formatter.format(lastDayOfMonth); Date endOfDate =
							 * formatter.parse(endMonth); Timestamp endOfMonth = new
							 * Timestamp(endOfDate.getTime()); System.out.println("for month " + i + ":" +
							 * endOfMonth);
							 * 
							 * 
							 * if ((endOfMonth).compareTo(EndDate) > 0) {
							 * System.out.println("the endOfMonth is greater than enddate"); endOfMonth =
							 * EndDate;
							 * 
							 * } else { System.out.println("the endOfMonth is less than enddate");
							 * 
							 * } date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
							 * (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
							 * (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
							 * 
							 * 
							 * if(StringUtils.equalsIgnoreCase(max,"Max")) {
							 * maxTimeRevenueStackandLineChartQuery =
							 * session.createNativeQuery(maxTimeRevenueStackandLineChartQueryString);
							 * maxTimeRevenueStackandLineChartQuery.setParameter("param1", monthofDate);
							 * maxTimeRevenueStackandLineChartQuery.setParameter("param2", endOfMonth);
							 * maxRevenueCharts = (List<RevenueChartsReport>) ((SQLQuery)
							 * maxTimeRevenueStackandLineChartQuery)
							 * .addScalar("voiceRevenue").addScalar("smsRevenue").addScalar("dataRevneue").
							 * addScalar("vasRevenue").addScalar("totalRevenue").addScalar("Date")
							 * .setResultTransformer(Transformers.aliasToBean(RevenueChartsReport.class)).
							 * list(); maxRevenueCharts.get(0).setDate(date);
							 * //System.out.println("for week " + i + " the date is :" + date);
							 * System.out.println("the maxRevenueChartsList"+mapper.writeValueAsString(
							 * maxRevenueCharts)); maxRevenueChartList.addAll(maxRevenueCharts); }
							 * 
							 * calendar.setTime(endOfMonth); calendar.add(Calendar.DATE, +1); Timestamp
							 * NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); month =
							 * NextDayOfMonth; System.out.println("after start date " + NextDayOfMonth);
							 * 
							 * }
							 * 
							 * 
							 * //System.out.println("Max or Min Charts");
							 * 
							 * if(StringUtils.equalsIgnoreCase(max,"Max")) {
							 * maxFinalRevenueReportResults.addAll(maxRevenueChartList);
							 * System.out.println("after maxFinalRevenueReportResults " +
							 * mapper.writeValueAsString(maxFinalRevenueReportResults));
							 * if(maxFinalRevenueReportResults.size()>0) {
							 * System.out.println("****The maxFinalRevenueReportResults.get(0) is:  "+mapper
							 * .writeValueAsString(maxFinalRevenueReportResults.get(0)));
							 * RevenueChartsReport tempRevChart = RevenueStackAndLineChartMax =
							 * maxFinalRevenueReportResults.get(0); TempTotalRevMax =
							 * (tempRevChart.getTotalRevenue()).intValue(); for(int j = 1; j<
							 * maxFinalRevenueReportResults.size(); j++) { RevenueChartsReport
							 * RevenueStackAndLineChart = maxFinalRevenueReportResults.get(j);
							 * totalRevenueMax = (RevenueStackAndLineChart.getTotalRevenue()).intValue();
							 * if(TempTotalRevMax < totalRevenueMax){ TempTotalRevMax = totalRevenueMax;
							 * RevenueStackAndLineChartMax = RevenueStackAndLineChart;
							 * System.out.println("************the RevenueStackAndLineChartMax is "+mapper.
							 * writeValueAsString(RevenueStackAndLineChartMax));
							 * 
							 * } } } RevenueStackAndLineChartMaxFinal.add(RevenueStackAndLineChartMax);
							 * maxRevenueChartListFinal =
							 * getPeriodicChartData(RevenueStackAndLineChartMaxFinal);
							 * //System.out.println("after maxRevenueChartListFinal " +
							 * mapper.writeValueAsString(maxRevenueChartListFinal));
							 * 
							 * if (maxFinalRevenueReportResults.size()>0) {
							 * rtn.put("MaxStackedandLineRevenue",
							 * mapper.writeValueAsString(maxRevenueChartListFinal)); } else {
							 * rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString("")); } }
							 * break;
							 * 
							 * case "applyButtLineChartMin": for (i = 0; i <= diff; i++) {
							 * 
							 * String startmonth = formatter.format(month); Date monthDate =
							 * formatter.parse(startmonth); Timestamp monthofDate = new
							 * Timestamp(monthDate.getTime());
							 * 
							 * Calendar calendar = Calendar.getInstance(); calendar.setTime(monthofDate);
							 * calendar.add(Calendar.MONTH, 1); calendar.set(Calendar.DAY_OF_MONTH, 1);
							 * calendar.add(Calendar.DATE, -1); Timestamp lastDayOfMonth = new
							 * Timestamp(calendar.getTime().getTime());
							 * 
							 * String endMonth = formatter.format(lastDayOfMonth); Date endOfDate =
							 * formatter.parse(endMonth); Timestamp endOfMonth = new
							 * Timestamp(endOfDate.getTime()); //System.out.println("for month " + i + ":" +
							 * endOfMonth);
							 * 
							 * 
							 * if ((endOfMonth).compareTo(EndDate) > 0) {
							 * System.out.println("the endOfMonth is greater than enddate"); endOfMonth =
							 * EndDate;
							 * 
							 * } else { //System.out.println("the endOfMonth is less than enddate");
							 * 
							 * } date = (monthofDate.getDate()) +"/"+ (monthofDate.getMonth() +1) +"/"+
							 * (monthofDate.getYear()+1900)+"-" + (endOfMonth.getDate())+"/"+
							 * (endOfMonth.getMonth() +1) +"/"+(monthofDate.getYear()+1900);
							 * 
							 * if(StringUtils.equalsIgnoreCase(min,"Min")) {
							 * minTimeRevenueStackandLineChartQuery =
							 * session.createNativeQuery(minTimeRevenueStackandLineChartQueryString);
							 * minTimeRevenueStackandLineChartQuery.setParameter("param1", monthofDate);
							 * minTimeRevenueStackandLineChartQuery.setParameter("param2", endOfMonth);
							 * minRevenueCharts = (List<RevenueChartsReport>) ((SQLQuery)
							 * minTimeRevenueStackandLineChartQuery)
							 * .addScalar("voiceRevenue").addScalar("smsRevenue").addScalar("dataRevneue").
							 * addScalar("vasRevenue").addScalar("totalRevenue").addScalar("Date")
							 * .setResultTransformer(Transformers.aliasToBean(RevenueChartsReport.class)).
							 * list(); minRevenueCharts.get(0).setDate(date);
							 * //System.out.println("the minRevenueChartsList"+mapper.writeValueAsString(
							 * minRevenueCharts)); if( minRevenueCharts.get(0).getTotalRevenue() != null) {
							 * minRevenueChartList.addAll(minRevenueCharts); } }
							 * 
							 * 
							 * calendar.setTime(endOfMonth); calendar.add(Calendar.DATE, +1); Timestamp
							 * NextDayOfMonth = new Timestamp(calendar.getTime().getTime()); month =
							 * NextDayOfMonth; //System.out.println("after start date " + NextDayOfMonth);
							 * 
							 * }
							 * 
							 * 
							 * // System.out.println("Max or Min Charts");
							 * 
							 * if(StringUtils.equalsIgnoreCase(min,"Min")) {
							 * System.out.println("BEFORE minRevenueChartList " +
							 * mapper.writeValueAsString(minRevenueChartList));
							 * minFinalRevenueReportResults.addAll(minRevenueChartList);
							 * //System.out.println("after minFinalRevenueReportResults " +
							 * mapper.writeValueAsString(minFinalRevenueReportResults));
							 * 
							 * if(minFinalRevenueReportResults.size()>0) {
							 * System.out.println("****The minFinalRevenueReportResults.get(0) is:  "+mapper
							 * .writeValueAsString(minFinalRevenueReportResults.get(0)));
							 * RevenueChartsReport tempRevChart = RevenueStackAndLineChartMin =
							 * minFinalRevenueReportResults.get(0); TempTotalRevMin =
							 * (tempRevChart.getTotalRevenue()).intValue(); for(int j = 1; j <
							 * minFinalRevenueReportResults.size(); j++) { RevenueChartsReport
							 * RevenueStackAndLineChart = minFinalRevenueReportResults.get(j);
							 * totalRevenueMin = (RevenueStackAndLineChart.getTotalRevenue()).intValue();
							 * if(TempTotalRevMin > totalRevenueMin){ TempTotalRevMin = totalRevenueMin;
							 * RevenueStackAndLineChartMin = minFinalRevenueReportResults.get(j);
							 * //System.out.println("************the RevenueStackAndLineChartMin is "+mapper
							 * .writeValueAsString(RevenueStackAndLineChartMin));
							 * 
							 * } } }
							 * 
							 * 
							 * RevenueStackAndLineChartMinFinal.add(RevenueStackAndLineChartMin);
							 * minRevenueChartListFinal =
							 * getPeriodicChartData(RevenueStackAndLineChartMinFinal);
							 * System.out.println("after minRevenueChartListFinal " +
							 * mapper.writeValueAsString(minRevenueChartListFinal));
							 * 
							 * if (RevenueStackAndLineChartMinFinal.size()>0) {
							 * rtn.put("MinStackedandLineRevenue",
							 * mapper.writeValueAsString(minRevenueChartListFinal)); } else {
							 * rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString("")); }
							 * 
							 * } break;
							 */
							// }

						}
						// start of acc or absolute max or min
						if (StringUtils.equalsIgnoreCase(period, "Accu")
								|| StringUtils.equalsIgnoreCase(period, "null")) {
							// System.out.println("acccccc");
							if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {

								if (finalSiteTechList == "") {

									// IF MAX AND MIN IS NOT CHECKED WITHOUT TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {
										switch (filterID) {
										case "applyButtMSChart":
											applyFilterRevSum = "select DISTINCT site_name,area_id, sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue, coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
													+ finalSitesNameList + " )" + queryRange
													+ " group by site_name, area_id order by site_name asc";
											break;
										/*
										 * case "applyButtStackChart": timeRevenueStackandLineChartQueryString =
										 * "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
										 * +date+"' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name LIKE '%"
										 * + site + "%' and t.AREA_NAME LIKE '%" + area + "%' and t.REGION_NAME LIKE '%"
										 * + technologyRegions + "%' " + queryRange + ""; break; case
										 * "applyButtLineChart": timeRevenueStackandLineChartQueryString =
										 * "select DISTINCT  coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\", coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\", coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
										 * +date+"' as \"Date\" from prepaid_payg_revenue t where t.REVENUE_DATE >= :param1 and t.REVENUE_DATE <= :param2 and t.site_name LIKE '%"
										 * + site + "%' and t.AREA_NAME LIKE '%" + area + "%' and t.REGION_NAME LIKE '%"
										 * + technologyRegions + "%' " + queryRange + ""; break;
										 */
										case "applyButtPieChart":
											applyFilterRevSum = "select DISTINCT  coalesce(sum(voice_revenue),0) as voice_revenue, coalesce(sum(sms_revenue),0) as sms_revenue,coalesce(sum(data_revenue),0) as data_revenue, coalesce(sum(vas_revenue),0) as vas_revenue,coalesce(sum(voice_revenue+sms_revenue+data_revenue+vas_revenue),0) as tot_revenue from prepaid_payg_revenue where REVENUE_DATE >= :param1 and REVENUE_DATE <= :param2 and site_name IN ( "
													+ finalSitesNameList + " ) " + queryRange + "";
											break;
										}

									}

								}
								// if there is a technology
								else if (finalSiteTechList != "") {

									// IF MAX AND MIN IS NOT CHECKED WITH TECHNOLOGY
									if (StringUtils.equalsIgnoreCase(max, null)
											&& StringUtils.equalsIgnoreCase(min, null)) {
										switch (filterID) {
										case "applyButtMSChart":

											applyFilterRevSum = "select t.site_name,t.area_id, coalesce(sum(t.voice_revenue),0), coalesce(sum(t.sms_revenue),0) ,coalesce(sum(t.data_revenue),0) , coalesce(sum(t.vas_revenue),0), coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, sum(voice_revenue+sms_revenue+data_revenue+vas_revenue), AREA_ID ,revenue_date,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
													+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and b.site_name IN (" + finalSitesNameList
													+ ") and b.AREA_NAME LIKE '%" + area
													+ "%' and b.REGION_NAME LIKE '%" + technologyRegions
													+ "%' and a.COMBINATION_TECHNOLOGY IN (" + finalSiteTechList + " ) "
													+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t group by t.site_name,t.area_id order by t.site_name asc ";

											break;
										/*
										 * case "applyButtStackChart": timeRevenueStackandLineChartQueryString =
										 * "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\",coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\",coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
										 * +date+"' as \"Date\" from (" + " SELECT DISTINCT a.WARE_NAME,a.SITE_ID," +
										 * "b.site_name as site_name,b.area_id as AREA_ID , " +
										 * "sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  "
										 * +
										 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :parand b.AREA_NAME LIKE '%"
										 * +area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+
										 * paramTechnologies+ " "+
										 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id) t order by t.site_name asc"
										 * ; break; case "applyButtLineChart": timeRevenueStackandLineChartQueryString =
										 * "select coalesce(sum(t.voice_revenue),0) as \"voiceRevenue\",coalesce(sum(t.sms_revenue),0) as \"smsRevenue\",coalesce(sum(t.data_revenue),0) as \"dataRevneue\",coalesce(sum(t.vas_revenue),0) as \"vasRevenue\",coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as \"totalRevenue\", '"
										 * +date+"' as \"Date\" from (" + " SELECT DISTINCT a.WARE_NAME,a.SITE_ID," +
										 * "b.site_name as site_name,b.area_id as AREA_ID , " +
										 * "sum(b.voice_revenue)as voice_revenue, sum(b.sms_revenue) as sms_revenue,sum(b.data_revenue) as data_revenue, sum(b.vas_revenue) as vas_revenue  "
										 * +
										 * "FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
										 * + "and b.site_name LIKE '%"+site+"%' and b.AREA_NAME LIKE '%"
										 * +area+"%' and b.REGION_NAME LIKE '%"+technologyRegions+"%' and "+
										 * paramTechnologies+ " "+
										 * "GROUP BY a.WARE_NAME, a.SITE_ID, b.site_name, b.area_id) t order by t.site_name asc"
										 * ; break;
										 */
										case "applyButtPieChart":
											applyFilterRevSum = "select coalesce(sum(tot_voice),0),coalesce(sum(tot_sms),0), coalesce(sum(tot_data),0), coalesce(sum(tot_vas),0),coalesce(sum(tot_voice+tot_sms+tot_data+tot_vas),0) from( "
													+ "select coalesce(sum(t.voice_revenue),0) as tot_voice, coalesce(sum(t.sms_revenue),0) as tot_sms ,coalesce(sum(t.data_revenue),0) as tot_data , coalesce(sum(t.vas_revenue),0) as tot_vas, coalesce((sum(t.voice_revenue)+sum(t.sms_revenue)+sum(t.data_revenue)+sum(t.vas_revenue)),0) as tot_revenue  from ( SELECT site_name, AREA_ID ,revenue_date,"
													+ " sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from( "
													+ " SELECT DISTINCT b.site_name as site_name,b.area_id as AREA_ID , TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,"
													+ " b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
													+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
													+ " and b.site_name IN (" + finalSitesNameList
													+ ")and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
													+ technologyRegions + "%' and a.COMBINATION_TECHNOLOGY IN ("
													+ finalSiteTechList + " ) "
													+ "  order by b.site_name) group by revenue_date,site_name, AREA_ID )t order by t.site_name asc )";

											break;
										}

									}

								}

								switch (filterID) {
								case "applyButtMSChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									chartJSNArr = chartRevenues(applyFilterQuery.list());

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(chartJSNArr));
									} else {
										rtn.put("msColumnChartObj", mapper.writeValueAsString(""));

									}
									break;

								case "applyButtPieChart":
									applyFilterQuery = session.createNativeQuery(applyFilterRevSum);
									applyFilterQuery.setParameter("param1", StartDate);
									applyFilterQuery.setParameter("param2", EndDate);

									if (applyFilterQuery.list().size() > 0) {
										rtn.put("pieRevenue", mapper.writeValueAsString(applyFilterQuery.list()));

									} else {
										rtn.put("pieRevenue", mapper.writeValueAsString(""));
									}
									break;

								case "applyButtStackChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;
								}

							} else {
								switch (filterID) {
								case "applyButtMSChart":
									rtn.put("msColumnChartObj", mapper.writeValueAsString(""));
									break;

								case "applyButtPieChart":
									rtn.put("pieRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChart":
									rtn.put("StackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtStackChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMax":
									rtn.put("MaxStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								case "applyButtLineChartMin":
									rtn.put("MinStackedandLineRevenue", mapper.writeValueAsString(""));
									break;

								}
							}
						} // END OF ACC

					}
				}

			} catch (Exception e) {
				logger.info("Error in creating session with Database", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	// Method for filter last 2 piecharts
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetSiteRevCharts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetSiteRevCharts(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String pieRevenueTechChartQueryString = "", paramTechnologies = "";
		Query pieRevenueTechChartQuery = null;
		ArrayList<Float> chartArray;

		String site = request.getParameter("siteName");
		String area = request.getParameter("area");
		String technologyRegions = request.getParameter("technologyRegions");
		String Payment_Method = request.getParameter("Payment Method");
		String period = request.getParameter("period");
		String max = request.getParameter("Max");
		String min = request.getParameter("Min");

		String nullSite = request.getParameter("nullSite");

		// All sites name from popup
		String sitesName = request.getParameter("allSitesNameChart");

		String finalSitesNameList = "";
		String[] sitesNameList = null;

		if (StringUtils.equalsIgnoreCase(sitesName, "") || StringUtils.equalsIgnoreCase(sitesName, null)) {
			finalSitesNameList = "";
		} else {
			String allSitesName = sitesName.concat(",");

			// Split the string of sites on space-comma to get each site separately
			sitesNameList = allSitesName.split(" ,");

			StringBuilder allSitesNames = new StringBuilder();

			// Append to each element '' and comma to satisfy the format of IN parameters
			for (int x = 0; x < sitesNameList.length; x++) {

				allSitesNames.append("'" + sitesNameList[x] + "',");
			}

			finalSitesNameList = allSitesNames.toString();
			finalSitesNameList = finalSitesNameList.substring(0, finalSitesNameList.length() - 1);
		}

		String siteTech = request.getParameter("selectedTech");
		String finalSiteTechList = "";
		String[] siteTechList = null;
		if (StringUtils.equalsIgnoreCase(siteTech, "") || StringUtils.equalsIgnoreCase(siteTech, null)) {
			finalSiteTechList = "";
		} else {
			String allSiteTech = siteTech.concat(",");
			// Split the string of technologies on comma to get each technology separately
			siteTechList = allSiteTech.split(",");
			StringBuilder allSiteTechs = new StringBuilder();

			// Append to each element '' and comma to satisfy the format of IN parameters
			for (int h = 0; h < siteTechList.length; h++) {

				allSiteTechs.append("'" + siteTechList[h] + "',");
			}

			finalSiteTechList = allSiteTechs.toString();
			finalSiteTechList = finalSiteTechList.substring(0, finalSiteTechList.length() - 1);

		}
		String start_Date = request.getParameter("startDate");
		String end_Date = request.getParameter("endDate");
		Date startDate = null;
		Date endDate = null;
		Timestamp StartDate;
		Timestamp EndDate;

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			Session session = null;
			Transaction tx = null;

			try {

				startDate = formatter.parse(start_Date);
				StartDate = new Timestamp(startDate.getTime());
				endDate = formatter.parse(end_Date);
				EndDate = new Timestamp(endDate.getTime());

				session = RptDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();

					// no technology is selected
					if (finalSiteTechList == "") {
						/*
						 * // IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
						 * if(StringUtils.equalsIgnoreCase(max,"Max") &&
						 * StringUtils.equalsIgnoreCase(min,"Min")) { pieRevenueTechChartQueryString =
						 * "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
						 * +"UNION "+
						 * "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G')   group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
						 * ;
						 * 
						 * } // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
						 * if(StringUtils.equalsIgnoreCase(max,"Max") &&
						 * StringUtils.equalsIgnoreCase(min,null)) { //pieRevenueTechChartQueryString =
						 * "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,date_rev,ROW_NUMBER() over(PARTITION by date_rev order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc))where row_nb = 1 group by technology"
						 * ; pieRevenueTechChartQueryString =
						 * "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
						 * ;
						 * 
						 * } // IF MIN IS ONLY CHECKED WITH TECHNOLOGY
						 * if(StringUtils.equalsIgnoreCase(max,null) &&
						 * StringUtils.equalsIgnoreCase(min,"Min")) { //pieRevenueTechChartQueryString =
						 * "select tech as technology, sum(sites) as sum_total,sum(Total_REVENUE) as totalRevSum from (select tech , sites ,Total_REVENUE ,ROW_NUMBER() over(order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech,b.REVENUE_DATE,b.SITE_NAME,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology In ('2G_3G_4G') group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc)) where row_nb IN (1,2) group by tech"
						 * ; //pieRevenueTechChartQueryString =
						 * "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from( select tech as technology, sites as sum_total,MIN(Total_REVENUE) as totalRevSum,date_rev from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev,b.SITE_NAME,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology  In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc) group by tech, sites, date_rev) group by technology"
						 * ;
						 * 
						 * //pieRevenueTechChartQueryString =
						 * "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,date_rev,ROW_NUMBER() over(PARTITION by date_rev order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc))where row_nb = 1 group by technology"
						 * ; pieRevenueTechChartQueryString =
						 * "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G') group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
						 * ; }
						 */
						// NO MAX AND MIN ONLY CHECKED WITH TECHNOLOGY
						if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {

							if (sitesName != "") {

								pieRevenueTechChartQueryString = "select t.combination_tech , count(distinct t.site_name)  ,sum(t.sms_revenue+t.data_revenue+t.voice_revenue+t.vas_revenue) FROM( "
										+ "SELECT combination_tech,revenue_date, site_name, AREA_ID , sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from("
										+ " SELECT DISTINCT b.site_name as site_name,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
										+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
										+ " and b.site_name IN (" + finalSitesNameList
										+ ") and a.combination_technology In ('2G_3G_4G','2G_3G','2G') "
										+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t  GROUP BY t.combination_tech  ";

								// pieRevenueTechChartQueryString = "select a.combination_technology as tech,
								// count(distinct a.ware_name) as sites
								// ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as
								// Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON
								// a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <=
								// :param2 and b.site_name IN ( "+finalSitesNameList+" ) and
								// a.combination_technology In ('2G_3G_4G','2G_3G','2G') group by
								// a.combination_technology order by a.combination_technology asc";
							} else {

								pieRevenueTechChartQueryString = "select t.combination_tech , count(distinct t.site_name)  ,sum(t.sms_revenue+t.data_revenue+t.voice_revenue+t.vas_revenue) FROM( "
										+ "SELECT combination_tech,revenue_date, site_name, AREA_ID , sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from("
										+ " SELECT DISTINCT b.site_name as site_name,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
										+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
										+ " and a.combination_technology In ('2G_3G_4G','2G_3G','2G') "
										+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t  GROUP BY t.combination_tech  ";

								// pieRevenueTechChartQueryString = "select a.combination_technology as tech,
								// count(distinct a.ware_name) as sites
								// ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as
								// Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON
								// a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <=
								// :param2 and a.combination_technology In ('2G_3G_4G','2G_3G','2G') group by
								// a.combination_technology order by a.combination_technology asc";

							}
						}

					}
					// if there is a technology
					else if (finalSiteTechList != "") {

						/*
						 * // IF MAX AND MIN IS CHECKED WITH TECHNOLOGY
						 * if(StringUtils.equalsIgnoreCase(max,"Max") &&
						 * StringUtils.equalsIgnoreCase(min,"Min")) { pieRevenueTechChartQueryString =
						 * "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology " +
						 * paramTechnologies+" group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
						 * +" UNION "+
						 * "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology " +
						 * paramTechnologies+" group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
						 * ;
						 * 
						 * } // IF MAX IS ONLY CHECKED WITH TECHNOLOGY
						 * if(StringUtils.equalsIgnoreCase(max,"Max") &&
						 * StringUtils.equalsIgnoreCase(min,null)) { //pieRevenueTechChartQueryString =
						 * "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from( select tech as technology, sites as sum_total,Max(Total_REVENUE) as totalRevSum,date_rev from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev,b.SITE_NAME,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology " +
						 * paramTechnologies+" group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc) group by tech, sites, date_rev) group by technology"
						 * ; pieRevenueTechChartQueryString =
						 * "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE desc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology " +
						 * paramTechnologies+" group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
						 * ; } // IF MIN IS ONLY CHECKED WITH TECHNOLOGY
						 * if(StringUtils.equalsIgnoreCase(max,null) &&
						 * StringUtils.equalsIgnoreCase(min,"Min")) { //pieRevenueTechChartQueryString =
						 * "select technology as technology, sum(sum_total) as sum_total,sum(totalRevSum) as totalRevSum from( select tech as technology, sites as sum_total,Min(Total_REVENUE) as totalRevSum,date_rev from(select a.combination_technology as tech,b.REVENUE_DATE as date_rev,b.SITE_NAME,  count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology " +
						 * paramTechnologies+" group by a.combination_technology, b.REVENUE_DATE, b.SITE_NAME order by Total_REVENUE asc) group by tech, sites, date_rev) group by technology"
						 * ; pieRevenueTechChartQueryString =
						 * "select technology,sum_total,totalRevSum from(select tech as technology, sites as sum_total,Total_REVENUE as totalRevSum,ROW_NUMBER() over( order by Total_REVENUE asc)as row_nb from(select a.combination_technology as tech, count(distinct a.ware_name) as sites ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 and b.site_name LIKE '%"
						 * + site + "%' and b.AREA_NAME LIKE '%" + area + "%' and b.REGION_NAME LIKE '%"
						 * +technologyRegions+"%' and a.combination_technology " +
						 * paramTechnologies+" group by a.combination_technology,b.SITE_ID order by a.combination_technology asc) ) where row_nb = 1"
						 * ; }
						 */
						// NO MAX AND MIN ONLY CHECKED WITH TECHNOLOGY
						if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
							if (sitesName != "") {
								// pieRevenueTechChartQueryString = "select a.combination_technology as tech,
								// count(distinct a.ware_name) as sites
								// ,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as
								// Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON
								// a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <=
								// :param2 and b.site_name IN ( "+finalSitesNameList+" ) and
								// a.COMBINATION_TECHNOLOGY IN ("+finalSiteTechList+" ) group by
								// a.combination_technology order by a.combination_technology asc";
								pieRevenueTechChartQueryString = "select t.combination_tech , count(distinct t.site_name)  ,sum(t.sms_revenue+t.data_revenue+t.voice_revenue+t.vas_revenue) FROM( "
										+ "SELECT combination_tech,revenue_date, site_name, AREA_ID , sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from("
										+ " SELECT DISTINCT b.site_name as site_name,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
										+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
										+ " and b.site_name IN (" + finalSitesNameList
										+ " ) and a.combination_technology In (" + finalSiteTechList + " ) "
										+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t  GROUP BY t.combination_tech  ";

							} else {
								pieRevenueTechChartQueryString = "select t.combination_tech , count(distinct t.site_name)  ,sum(t.sms_revenue+t.data_revenue+t.voice_revenue+t.vas_revenue) FROM( "
										+ "SELECT combination_tech,revenue_date, site_name, AREA_ID , sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue, sum(vas_revenue) as vas_revenue from("
										+ " SELECT DISTINCT b.site_name as site_name,TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date,a.COMBINATION_TECHNOLOGY as combination_tech,b.area_id as AREA_ID ,b.voice_revenue as voice_revenue, b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
										+ " FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 "
										+ "  and a.combination_technology In (" + finalSiteTechList + " ) "
										+ "  order by b.site_name) group by revenue_date,combination_tech, site_name, AREA_ID)t  GROUP BY t.combination_tech  ";

							}
						}

					}

					if (StringUtils.equalsIgnoreCase(max, null) && StringUtils.equalsIgnoreCase(min, null)) {
						pieRevenueTechChartQuery = session.createNativeQuery(pieRevenueTechChartQueryString);
						pieRevenueTechChartQuery.setParameter("param1", StartDate);
						pieRevenueTechChartQuery.setParameter("param2", EndDate);

						// System.out.println("The siteResult is: " +
						// mapper.writeValueAsString(siteResult));
						chartArray = new ArrayList<Float>(PieRevPerTech(pieRevenueTechChartQuery.list()));
						// System.out.println("#########The chartArray is: " + chartArray);
						if (pieRevenueTechChartQuery.list().size() > 0) {
							// rtn.put("listChartSites", mapper.writeValueAsString(siteResult));
							rtn.put("chartData", chartArray);
						} else {
							// rtn.put("listChartSites", mapper.writeValueAsString(""));
							rtn.put("chartData", "");
						}
					} else {
						rtn.put("listChartSites", mapper.writeValueAsString(""));
						rtn.put("chartData", "");
					}
				}

			}

			catch (Exception e) {
				logger.info("Error at <T>  creating session with the DataBase " + e.getMessage());

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
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value ="/GetAllSitesByDefault", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> GetAllSitesByDefault(Locale locale,
	 * Model model, HttpServletRequest request, HttpServletResponse response) throws
	 * JsonProcessingException, ParseException {
	 * logger.info("Welcome home! The client locale is {}.", locale);
	 * 
	 * Map<String, Object> rtn = new LinkedHashMap<>(); ObjectMapper mapper = new
	 * ObjectMapper();
	 * 
	 * 
	 * SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	 * 
	 * String start_Date = request.getParameter("startDate"); Date startDate =
	 * formatter.parse(start_Date); Timestamp StartDate = new
	 * Timestamp(startDate.getTime()); String end_Date =
	 * request.getParameter("endDate"); Date endDate = formatter.parse(end_Date);
	 * Timestamp EndDate = new Timestamp(endDate.getTime());
	 * 
	 * String defaultSites =""; Query query = null;
	 * 
	 * 
	 * Configuration cfg = new Configuration().configure("/almrpt.cfg.xml");
	 * StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
	 * .applySettings(cfg.getProperties()); SessionFactory sf
	 * =cfg.buildSessionFactory(builder.build()); Session session =
	 * sf.openSession(); Transaction tx = session.beginTransaction();
	 * 
	 * defaultSites =
	 * "SELECT a.WARE_NAME,a.LONGITUDE,a.LATITUDE,a.TECH_2G,a.TECH_3G,a.TECH_4G,a.TECH_5G,sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue) as Total_REVENUE,a.SITE_ID FROM alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE b.REVENUE_DATE >= :param1 AND b.REVENUE_DATE <= :param2 GROUP BY a.WARE_NAME,a.LONGITUDE, a.LATITUDE, a.TECH_2G,a.TECH_3G, a.TECH_4G, a.TECH_5G, a.SITE_ID"
	 * ;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * /*
	 * "SELECT * FROM( SELECT DISTINCT a.site_name,a.longitude,a.latitude,a.tech_2g,a.tech_3g,a.tech_4g,a.tech_5g,\r\n"
	 * + "b.totalRev,a.site_id FROM prepaid_payg_revenue a\r\n" +
	 * "RIGHT JOIN( SELECT DISTINCT site_name,site_id,voice_revenue,sms_revenue,data_revenue,vas_revenue,sum(sms_revenue+data_revenue+voice_revenue+vas_revenue) as totalRev\r\n"
	 * + " FROM prepaid_payg_revenue where site_name !='null'\r\n" +
	 * " GROUP BY site_name,site_id,voice_revenue,sms_revenue,data_revenue,vas_revenue)b on a.site_name =b.site_name )"
	 * ).list();
	 * 
	 */

	/*
	 * query=session.createNativeQuery(defaultSites); query.setParameter("param1",
	 * StartDate); query.setParameter("param2", EndDate);
	 * 
	 * List<Object[]> siteList = (List<Object[]>) (query).list(); List<Object[]>
	 * siteResult = new ArrayList<Object[]>(); if (siteList != null) { for (Object[]
	 * obj : siteList) {
	 * 
	 * siteResult.add(obj);
	 * 
	 * } } tx.commit(); session.close(); // System.out.println("DEFAULT SITE LIST:"
	 * +mapper.writeValueAsString(siteResult)); rtn.put("listSites", siteResult);
	 * 
	 * 
	 * return rtn;
	 * 
	 * }
	 * 
	 */
	// Google Maps Method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetRevenuePerSite", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetRevenuePerSite(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		String area = request.getParameter("area");
		String region = request.getParameter("region");
		String period = request.getParameter("period");
		String sitesName = request.getParameter("allSitesName");
		String start_Date = request.getParameter("startDate");
		String end_Date = request.getParameter("endDate");

		Date startDate = null;
		Date endDate = null;
		Timestamp StartDate;
		Timestamp EndDate;

		String finalSitesNameList = "";
		String[] sitesNameList = null;
		String siteNameEle = "";
		if (StringUtils.equalsIgnoreCase(sitesName, "") || StringUtils.equalsIgnoreCase(sitesName, null)) {
			finalSitesNameList = "";
		} else {
			String allSitesName = sitesName.concat(",");

			// Split the string of sites on space-comma to get each site separately
			sitesNameList = allSitesName.split(" ,");
			StringBuilder allSitesNames = new StringBuilder();
			// Append to each element '' and comma to satisfy the format of IN parameters
			for (int i = 0; i < sitesNameList.length; i++) {

				allSitesNames.append("'" + sitesNameList[i] + "',");
			}

			finalSitesNameList = allSitesNames.toString();
			finalSitesNameList = finalSitesNameList.substring(0, finalSitesNameList.length() - 1);
		}

		String siteTech = request.getParameter("selectedTech");
		String finalSiteTechList = "";
		String[] siteTechList = null;
		if (StringUtils.equalsIgnoreCase(siteTech, "") || StringUtils.equalsIgnoreCase(siteTech, null)) {
			finalSiteTechList = "";
		} else {
			String allSiteTech = siteTech.concat(",");
			// Split the string of technologies on comma to get each technology separately
			siteTechList = allSiteTech.split(",");
			StringBuilder allSiteTechs = new StringBuilder();

			// Append to each element '' and comma to satisfy the format of IN parameters
			for (int i = 0; i < siteTechList.length; i++) {

				allSiteTechs.append("'" + siteTechList[i] + "',");
			}

			finalSiteTechList = allSiteTechs.toString();
			finalSiteTechList = finalSiteTechList.substring(0, finalSiteTechList.length() - 1);

		}

		List<Object[]> finalSiteList = new ArrayList<Object[]>();
		String mapSitesList = "";
		String warehouseSitesList = "", mapPrepaidRevSitesList = "", warehouseAllSites = "";
		Query mapSitesListQuery = null, warehouseSitesListQuery = null, mapPrepaidRevSitesListQuery = null,
				warehouseAllSitesQuery = null;

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;

		} else {
			Session session = null;
			Transaction tx = null;

			try {

				startDate = formatter.parse(start_Date);
				StartDate = new Timestamp(startDate.getTime());

				endDate = formatter.parse(end_Date);
				EndDate = new Timestamp(endDate.getTime());

				session = RptDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();

					// Site name is selected

					System.out.println("Bilal - GetRevenuePerSite");

					if (finalSitesNameList != "") {

						if (finalSiteTechList == "") {

							for (int i = 0; i < sitesNameList.length; i++) {

								siteNameEle = finalSitesNameList.split(",")[i];

								mapSitesList = " SELECT DISTINCT ware_name,longitude,latitude,tech_2g,tech_3g,tech_4g,tech_5g, (SELECT coalesce(sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue),0) "
										+ "FROM prepaid_payg_revenue b "
										+ "WHERE revenue_date >= :param1 and revenue_date <= :param2 and area_name like '%"
										+ area + "%' and  region_name like '%" + region + "%' and site_name = "
										+ siteNameEle + " )sumRev,SITE_ID " + "FROM alm_warehouse where ware_name ="
										+ siteNameEle + "  and area_name like '%" + area + "%' and  region_name like '%"
										+ region + "%' and rownum='1' ";

								mapSitesListQuery = session.createNativeQuery(mapSitesList);
								mapSitesListQuery.setParameter("param1", StartDate);
								mapSitesListQuery.setParameter("param2", EndDate);

								finalSiteList.addAll(mapSitesListQuery.list());

							} // end loop

							warehouseSitesList = "SELECT distinct ware_name,longitude,latitude,tech_2g ,tech_3g ,tech_4g ,tech_5g,'0' as sumRevenue,site_id "
									+ "FROM alm_warehouse WHERE ware_name NOT IN(select site_name from prepaid_payg_revenue) "
									+ "and ware_name IN(" + finalSitesNameList + ") and area_name like '%" + area
									+ "%' and region_name like '%" + region
									+ "%' and (status ='approved' or status='activated')   ";

							warehouseAllSites = "SELECT distinct ware_name,longitude,latitude,tech_2g ,tech_3g ,tech_4g ,tech_5g,'0' as sumRevenue,site_id "
									+ "FROM alm_warehouse WHERE ware_name IN(" + finalSitesNameList
									+ ") and area_name like '%" + area + "%' and region_name like '%" + region + "%' "
									+ " and (status ='approved' OR status='activated')  ";

						} // end null tech

						// Tech is selected
						else {
							for (int i = 0; i < sitesNameList.length; i++) {

								siteNameEle = finalSitesNameList.split(",")[i];

								mapSitesList = " SELECT DISTINCT ware_name,longitude,latitude,tech_2g,tech_3g,tech_4g,tech_5g, (SELECT coalesce(sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue),0) "
										+ "FROM prepaid_payg_revenue b "
										+ "WHERE revenue_date >= :param1 and revenue_date <= :param2 and area_name like '%"
										+ area + "%' and  region_name like '%" + region + "%' and site_name = "
										+ siteNameEle + " )sumRev,SITE_ID " + "FROM alm_warehouse where ware_name ="
										+ siteNameEle + " and COMBINATION_TECHNOLOGY IN (" + finalSiteTechList
										+ " )  and area_name like '%" + area + "%' and  region_name like '%" + region
										+ "%' and rownum='1' ";

								mapSitesListQuery = session.createNativeQuery(mapSitesList);
								mapSitesListQuery.setParameter("param1", StartDate);
								mapSitesListQuery.setParameter("param2", EndDate);

								finalSiteList.addAll(mapSitesListQuery.list());

							} // end loop

							warehouseSitesList = "SELECT distinct ware_name,longitude,latitude,tech_2g ,tech_3g ,tech_4g ,tech_5g,'0' as sumRevenue,site_id "
									+ "FROM alm_warehouse WHERE ware_name NOT IN(select site_name from prepaid_payg_revenue) "
									+ "and ware_name IN(" + finalSitesNameList + ") and COMBINATION_TECHNOLOGY IN ("
									+ finalSiteTechList + " ) and area_name like '%" + area
									+ "%' and region_name like '%" + region
									+ "%' and (status ='approved' or status='activated')   ";

							warehouseAllSites = "SELECT distinct ware_name,longitude,latitude,tech_2g ,tech_3g ,tech_4g ,tech_5g,'0' as sumRevenue,site_id "
									+ "FROM alm_warehouse WHERE ware_name IN(" + finalSitesNameList
									+ ") and COMBINATION_TECHNOLOGY IN (" + finalSiteTechList
									+ " ) and area_name like '%" + area + "%' and region_name like '%" + region + "%' "
									+ " and (status ='approved' OR status='activated')  ";

						}

						if (finalSiteList != null) {
							rtn.put("listSites", finalSiteList);
						}
						warehouseSitesListQuery = session.createNativeQuery(warehouseSitesList);
						if (warehouseSitesListQuery.list() != null) {
							rtn.put("listWarehouseSites", warehouseSitesListQuery.list());
						}

						warehouseAllSitesQuery = session.createNativeQuery(warehouseAllSites);
						if (warehouseAllSitesQuery.list() != null) {
							rtn.put("listAllWarehouseSites", warehouseAllSitesQuery.list());
						}

					} // end selected site name

					// Site is not selected

					else if (finalSitesNameList == "") {
						System.out.println("Bilal - Site is not selected in GetRevenuePerSite");

						if (StringUtils.equalsIgnoreCase(period, "Daily")
								|| StringUtils.equalsIgnoreCase(period, "null")
								|| StringUtils.equalsIgnoreCase(period, "Weekly")
								|| StringUtils.equalsIgnoreCase(period, "Monthly")
								|| StringUtils.equalsIgnoreCase(period, "Accu")) {
							if (finalSiteTechList == "") {

								mapSitesList = "SELECT DISTINCT SITE_NAME,LONGITUDE,LATITUDE,TECH_2G,TECH_3G,TECH_4G,TECH_5G,sum(sms_revenue+data_revenue+voice_revenue+vas_revenue),SITE_ID from( "
										+ "SELECT SITE_NAME as SITE_NAME,LONGITUDE AS LONGITUDE ,LATITUDE AS LATITUDE,TECH_2G AS TECH_2G,TECH_3G AS TECH_3G,TECH_4G AS TECH_4G,TECH_5G AS TECH_5G,"
										+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue,SITE_ID AS SITE_ID from( "
										+ "SELECT DISTINCT b.site_name as site_name,b.LONGITUDE AS LONGITUDE,b.LATITUDE AS LATITUDE,a.TECH_2G AS TECH_2G,a.TECH_3G AS TECH_3G,a.TECH_4G AS TECH_4G,a.TECH_5G AS TECH_5G,b.SITE_ID AS SITE_ID, "
										+ "TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, b.voice_revenue as voice_revenue,b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
										+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and b.area_name like '%"
										+ area + "%' and b.region_name like '%" + region
										+ "%' order by b.site_name) group by SITE_NAME,LONGITUDE,LATITUDE,TECH_2G,TECH_3G,"
										+ "TECH_4G,TECH_5G,SITE_ID)"
										+ "GROUP BY SITE_NAME,LONGITUDE,LATITUDE,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID ";

								warehouseSitesList = "SELECT distinct ware_name,longitude,latitude,tech_2g ,tech_3g ,tech_4g ,tech_5g,'0' as sumRevenue,site_id "
										+ "FROM alm_warehouse WHERE ware_name NOT IN(select site_name from prepaid_payg_revenue) "
										+ " and area_name like '%" + area + "%' and region_name like '%" + region
										+ "%' and (status ='approved' OR status='activated')  ";

								warehouseAllSites = "SELECT distinct ware_name,longitude,latitude,tech_2g ,tech_3g ,tech_4g ,tech_5g,'0' as sumRevenue,site_id "
										+ "FROM alm_warehouse WHERE area_name like '%" + area
										+ "%' and region_name like '%" + region + "%' "
										+ " and (status ='approved' OR status='activated')  ";

							} else if (finalSiteTechList != "") {

								mapSitesList = "SELECT DISTINCT SITE_NAME,LONGITUDE,LATITUDE,TECH_2G,TECH_3G,TECH_4G,TECH_5G,sum(sms_revenue+data_revenue+voice_revenue+vas_revenue),SITE_ID from( "
										+ "SELECT SITE_NAME as SITE_NAME,LONGITUDE AS LONGITUDE ,LATITUDE AS LATITUDE,TECH_2G AS TECH_2G,TECH_3G AS TECH_3G,TECH_4G AS TECH_4G,TECH_5G AS TECH_5G,"
										+ "sum(voice_revenue)as voice_revenue, sum(sms_revenue) as sms_revenue,sum(data_revenue) as data_revenue,sum(vas_revenue) as vas_revenue,SITE_ID AS SITE_ID from( "
										+ "SELECT DISTINCT b.site_name as site_name,b.LONGITUDE AS LONGITUDE,b.LATITUDE AS LATITUDE,a.TECH_2G AS TECH_2G,a.TECH_3G AS TECH_3G,a.TECH_4G AS TECH_4G,a.TECH_5G AS TECH_5G,b.SITE_ID AS SITE_ID, "
										+ "TO_CHAR(b.revenue_date, 'YYYY-MM-DD HH24:MI:SS') as revenue_date, b.voice_revenue as voice_revenue,b.sms_revenue as sms_revenue,b.data_revenue as data_revenue,b.vas_revenue as vas_revenue "
										+ "FROM alm_warehouse a, PREPAID_PAYG_REVENUE b WHERE a.WARE_NAME = b.site_name and b.REVENUE_DATE >= :param1 and b.REVENUE_DATE <= :param2 and a.COMBINATION_TECHNOLOGY IN ("
										+ finalSiteTechList + " )  and b.area_name like '%" + area
										+ "%' and b.region_name like '%" + region
										+ "%' order by b.site_name) group by SITE_NAME,LONGITUDE,LATITUDE,TECH_2G,TECH_3G,"
										+ "TECH_4G,TECH_5G,SITE_ID)"
										+ "GROUP BY SITE_NAME,LONGITUDE,LATITUDE,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID ";

								warehouseSitesList = "SELECT distinct ware_name,longitude,latitude,tech_2g ,tech_3g ,tech_4g ,tech_5g,'0' as sumRevenue,site_id "
										+ "FROM alm_warehouse WHERE ware_name NOT IN(select site_name from prepaid_payg_revenue) "
										+ "and COMBINATION_TECHNOLOGY IN (" + finalSiteTechList
										+ " ) and area_name like '%" + area + "%' and region_name like '%" + region
										+ "%' and (status ='approved' or status='activated')   ";

								warehouseAllSites = "SELECT distinct ware_name,longitude,latitude,tech_2g ,tech_3g ,tech_4g ,tech_5g,'0' as sumRevenue,site_id "
										+ "FROM alm_warehouse WHERE area_name like '%" + area
										+ "%' and region_name like '%" + region + "%' "
										+ " and COMBINATION_TECHNOLOGY IN (" + finalSiteTechList
										+ " ) and (status ='approved' OR status='activated')  ";

							}

						}

						mapSitesListQuery = session.createNativeQuery(mapSitesList);
						mapSitesListQuery.setParameter("param1", StartDate);
						mapSitesListQuery.setParameter("param2", EndDate);

						if (mapSitesListQuery.list() != null) {
							rtn.put("listSites", mapSitesListQuery.list());
						}

						warehouseSitesListQuery = session.createNativeQuery(warehouseSitesList);
						if (warehouseSitesListQuery.list() != null) {
							rtn.put("listWarehouseSites", warehouseSitesListQuery.list());
						}

						warehouseAllSitesQuery = session.createNativeQuery(warehouseAllSites);
						if (warehouseAllSitesQuery.list() != null) {
							rtn.put("listAllWarehouseSites", warehouseAllSitesQuery.list());
						}

					} // end period condition

				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Error at <T>  creating session with the DataBase " + e.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;

	}

	// Revenue Report auto complete for sites

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllSiteAutocomplete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllSiteAutocomplete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		String siteId = "%" + request.getParameter("SiteId") + "%";

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			Session session = null;
			Transaction tx = null;

			try {
				session = AlmDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {

					tx = session.beginTransaction();

					/*
					 * String area = request.getParameter("Area") ; String[] words=area.split(";");
					 * 
					 * //String Area = "%" + request.getParameter("Area") + "%";
					 * System.out.println("Area is "+words[0]); String Region = "%" +
					 * request.getParameter("Region") + "%";
					 */

					/*
					 * Query q = session.
					 * createNativeQuery("SELECT  distinct site_name,longitude,latitude FROM prepaid_payg_revenue "
					 * );
					 */

					Query q = session.createNativeQuery(
							"SELECT  distinct (ware_name),site_id,longitude,latitude FROM warehouse where UPPER(ware_name) like UPPER(:param1) or UPPER(site_id) like UPPER(:param1)  ");

					q.setParameter("param1", siteId);
					// q.setParameter("param2", Area);
					q.setFirstResult(0);
					q.setMaxResults(40);

					rtn.put("listSites", q.list());
				}

			} catch (Exception e) {
				logger.info("Error at <T>  creating session with the DataBase " + e.getMessage());

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

	// AutoComplete Region
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllRegionsRPT", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllRegionsRPT(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);
		// System.out.println("Here");

		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		String region = "%" + request.getParameter("Region") + "%";

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {
			Session session = null;
			Transaction tx = null;

			try {
				session = AlmDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();

					Query q = session.createNativeQuery(
							"SELECT  distinct REGION_ID,REGION_NAME,REGION_CODE FROM REGION where UPPER(REGION_NAME)like UPPER(:param1) OR UPPER(REGION_CODE)like UPPER(:param1) OR UPPER(REGION_ID)like UPPER(:param1)");
					q.setParameter("param1", region);
					q.setFirstResult(0);
					q.setMaxResults(40);

					rtn.put("listRegions", q.list());
				}
			} catch (Exception e) {
				logger.info("Error at <T>  creating session with the DataBase " + e.getMessage());

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

	// AutoComplete Method
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllAreasRPT", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllAreasRPT(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		String areaid = "%" + request.getParameter("areaID") + "%";

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {
			Session session = null;
			Transaction tx = null;

			try {
				session = AlmDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();

					Query q = session.createNativeQuery(
							"SELECT  distinct AREA_ID,AREA_NAME FROM area where UPPER(AREA_NAME)like UPPER(:param1)");
					q.setParameter("param1", areaid);
					q.setFirstResult(0);
					q.setMaxResults(40);

					rtn.put("listAreas", q.list());
				}
			} catch (Exception e) {
				logger.info("Error at <T>  creating session with the DataBase " + e.getMessage());

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

	// Method For the First Chart
	@SuppressWarnings({ "unchecked", "null" })
	private JSONArray chartRevenues(List<Object> defaultChartRevenueReport) {
		JSONArray siteNames = new JSONArray(), voiceRevenue = new JSONArray(), smsRevenue = new JSONArray(),
				dataRevenue = new JSONArray(), vasRevenue = new JSONArray(), totalRevenue = new JSONArray();
		JSONArray objRevenue = new JSONArray();
		JSONObject json;

		if (defaultChartRevenueReport != null && defaultChartRevenueReport.size() != 0) {
			List<Object> c = defaultChartRevenueReport;
			Object[] fof;
			for (int i = 0; i < c.size(); i++) {
				fof = (Object[]) c.get(i);

				if (fof[0] != null) {
					json = new JSONObject();
					json.put("label", StringUtils.equalsIgnoreCase((String) fof[0], "null") ? "No site name" : fof[0]);
					siteNames.add(json);
				} else {
					json = new JSONObject();
					json.put("label", "No Data");

					siteNames.add(json);
				}

				if (fof[2] != null) {
					json = new JSONObject();
					json.put("value", fof[2]);
					voiceRevenue.add(json);

				} else {
					json = new JSONObject();
					json.put("value", 0);
					voiceRevenue.add(json);

				}
				if (fof[3] != null) {
					json = new JSONObject();
					json.put("value", fof[3]);
					smsRevenue.add(json);

				} else {
					json = new JSONObject();
					json.put("value", 0);
					smsRevenue.add(json);

				}
				if (fof[4] != null) {
					json = new JSONObject();
					json.put("value", fof[4]);
					dataRevenue.add(json);

				} else {
					json = new JSONObject();
					json.put("value", 0);
					dataRevenue.add(json);

				}
				if (fof[5] != null) {
					json = new JSONObject();
					json.put("value", fof[5]);
					vasRevenue.add(json);

				} else {
					json = new JSONObject();
					json.put("value", 0);
					vasRevenue.add(json);

				}
				if (fof[5] != null) {
					json = new JSONObject();
					json.put("value", fof[6]);
					totalRevenue.add(json);

				} else {
					json = new JSONObject();
					json.put("value", 0);
					totalRevenue.add(json);

				}

			}
		} else {
			json = new JSONObject();
			json.put("label", "No Data");
			siteNames.add(json);
			json = new JSONObject();
			json.put("value", 0);
			voiceRevenue.add(json);
			smsRevenue.add(json);
			dataRevenue.add(json);
			vasRevenue.add(json);
			totalRevenue.add(json);
		}

		objRevenue.add(siteNames);
		objRevenue.add(voiceRevenue);
		objRevenue.add(smsRevenue);
		objRevenue.add(dataRevenue);
		objRevenue.add(vasRevenue);
		objRevenue.add(totalRevenue);

		return objRevenue;
	}

	/*
	 * @SuppressWarnings("unused") /*private static Boolean check(HashSet<?>
	 * siteDuplicates, Object site) { // check if the specified element // is
	 * present in the array or not // using Linear Search method boolean test =
	 * false; for (Object element : siteDuplicates) { if (element == site) { test =
	 * true; break; } }
	 * 
	 * // Print the result System.out.println("Is " + site +
	 * " present in the array: " + test); return test; }
	 * 
	 * @SuppressWarnings("unchecked") public static <T extends Comparable<T>>
	 * HashSet<T> getDuplicates(ArrayList<?> siteArr) { HashSet<T> dupes = new
	 * HashSet<T>(); HashSet<T> siteDuplicates = new HashSet<T>(); for (Object i :
	 * siteArr) { if (!dupes.add((T) i)) { siteDuplicates.add((T) i); } /* if
	 * (!dupes.add(i)) { System.out.println("Duplicate element in array is : " + i);
	 * }
	 */
	/*
	 * }
	 * 
	 * return siteDuplicates; }
	 */

	/*
	 * @SuppressWarnings("unchecked") private JSONArray
	 * splittingFuncSpare(ArrayList<?> Array, String c) { // split any ArrayList
	 * using this method ///// Created By: HAjouz ________ // 25.8.2021 JSONArray
	 * FinalArray = new JSONArray(); String[] elmnt; if (c == "-") {
	 * 
	 * for (Object Element : Array) { elmnt = ((String) Element).split(c);
	 * FinalArray.add(elmnt[1] + "/" + elmnt[2] + "/" + elmnt[0]); } } else { for
	 * (Object Element : Array) {
	 * 
	 * elmnt = ((String) Element).split(c); FinalArray.add(elmnt[0].toString()); } }
	 * 
	 * return FinalArray; }
	 */

	// Get StackedChart Data for Daily(One Day)
	@SuppressWarnings({ "unchecked" })
	private JSONArray getStackedChartData(List<Object[]> perDataChrtLst) {
		JSONObject json;
		JSONArray voiceArrFnl = new JSONArray(), smsArrFnl = new JSONArray(), dataArrFnl = new JSONArray(),
				vasArrFnl = new JSONArray(), totalRevArrFnl = new JSONArray();
		JSONArray obj = new JSONArray();
		JSONArray startDateArrOriginal = new JSONArray();

		Object[] getObj;

		if (!perDataChrtLst.isEmpty()) {
			for (int i = perDataChrtLst.size() - 1; i >= 0; i--) {

				getObj = perDataChrtLst.get(i);

				json = new JSONObject();
				json.put("label", getObj[0]);
				startDateArrOriginal.add(json);

				json = new JSONObject();
				json.put("value", getObj[1]);
				voiceArrFnl.add(json);

				json = new JSONObject();
				json.put("value", getObj[2]);
				smsArrFnl.add(json);

				json = new JSONObject();
				json.put("value", getObj[3]);
				dataArrFnl.add(json);

				json = new JSONObject();
				json.put("value", getObj[4]);
				vasArrFnl.add(json);

				json = new JSONObject();
				json.put("value", getObj[5]);
				totalRevArrFnl.add(json);

			}
			startDateArrOriginal = (((ArrayList<?>) startDateArrOriginal).isEmpty()) == true ? null
					: startDateArrOriginal;
		}

		obj.add(startDateArrOriginal);
		obj.add(voiceArrFnl);
		obj.add(smsArrFnl);
		obj.add(dataArrFnl);
		obj.add(vasArrFnl);
		obj.add(totalRevArrFnl);

		return obj;

	}

	/*
	 * @SuppressWarnings("unchecked") private JSONArray splittingFunc(ArrayList<?>
	 * Array, ArrayList<?> Array2, String c) { // split any ArrayList using this
	 * method ///// Created By: HAjouz ________ // 25.8.2021 ArrayList<String> Arr1
	 * = new ArrayList<>(), Arr2 = new ArrayList<>();
	 * 
	 * JSONArray FinalArray = new JSONArray(); String[] elmnt, elmnt2; if (c == "-")
	 * {
	 * 
	 * for (Object Element : Array) { elmnt = ((String) Element).split(c);
	 * Arr1.add(elmnt[2]+ "/" + elmnt[1]);// + "/" + elmnt[1] } for (Object Element
	 * : Array2) { elmnt2 = ((String) Element).split(c); Arr2.add(elmnt2[2] + "/" +
	 * elmnt2[1]); } FinalArray.add(Arr1.get(0) + "-" + Arr2.get(0));
	 * 
	 * 
	 * } else { for (Object Element : Array) {
	 * 
	 * elmnt = ((String) Element).split(c); // FinalArray.add(elmnt[0].toString());
	 * } for (Object Element : Array2) {
	 * 
	 * elmnt2 = ((String) Element).split(c); //
	 * FinalArray.add(elmnt2[0].toString()); } }
	 * 
	 * return FinalArray; }
	 * 
	 * @SuppressWarnings({ "unchecked" }) private JSONArray
	 * preparePeriodicData(String startDate, String endDate, List<Object[]>
	 * perDataChrtLst) throws ParseException {
	 * 
	 * ArrayList<String> voiceArrFnl = new ArrayList<String>(), smsArrFnl = new
	 * ArrayList<String>(), dataArrFnl = new ArrayList<String>(), vasArrFnl = new
	 * ArrayList<String>(),totalRevArrFnl = new ArrayList<String>(); JSONArray obj =
	 * new JSONArray(); ArrayList<String> startDateArrOriginal = new
	 * ArrayList<String>(); ArrayList<String> endDateArrOriginal = new
	 * ArrayList<String>();
	 * 
	 * ArrayList<String> DateArrFnl = new ArrayList<String>();
	 * 
	 * /* System.out.println("the start date is "+startDate);
	 * System.out.println("the end date is "+endDate); DateFormat formatter = new
	 * SimpleDateFormat("dd/MM/yyyy"); Date StartDay = formatter.parse(startDate);
	 * System.out.println("startDate after : " + StartDay); Date EndDay
	 * =formatter.parse(endDate);
	 */

	/*
	 * startDateArrOriginal.add(startDate); endDateArrOriginal.add(endDate);
	 * startDateArrOriginal = splittingFuncSpare(startDateArrOriginal, " ");
	 * endDateArrOriginal = splittingFuncSpare(endDateArrOriginal, " "); DateArrFnl
	 * = splittingFunc(startDateArrOriginal, endDateArrOriginal, "-"); Object[]
	 * getObj;
	 * 
	 * if (!perDataChrtLst.isEmpty()) { for (int i = perDataChrtLst.size() - 1; i >=
	 * 0; i--) {
	 * 
	 * getObj = perDataChrtLst.get(i); // startDateArrOriginal.add((String)
	 * getObj[0]); voiceArrFnl.add((getObj[0]).toString());
	 * smsArrFnl.add(getObj[1].toString()); dataArrFnl.add(getObj[2].toString());
	 * vasArrFnl.add(getObj[3].toString());
	 * totalRevArrFnl.add(getObj[4].toString());
	 * 
	 * } DateArrFnl = (((ArrayList<?>) DateArrFnl).isEmpty()) == true ? null :
	 * DateArrFnl; } //obj.add(startDate); obj.add(DateArrFnl.get(0));
	 * obj.add(voiceArrFnl.get(0).isEmpty() ? 0 : voiceArrFnl);
	 * obj.add(smsArrFnl.get(0).isEmpty() ? 0 : smsArrFnl);
	 * obj.add(dataArrFnl.get(0).isEmpty() ? 0 : dataArrFnl);
	 * obj.add(vasArrFnl.get(0).isEmpty() ? 0 : vasArrFnl);
	 * obj.add(totalRevArrFnl.get(0).isEmpty() ? 0 : totalRevArrFnl);
	 * 
	 * return obj;
	 * 
	 * }
	 */

	// Method for the line and StackChart weekly and monthly
	@SuppressWarnings({ "unchecked" })
	private JSONArray getPeriodicChartData(List<RevenueChartsReport> revenueChartList) throws JsonProcessingException { // List<Object[]>

		JSONArray voiceArrFnl = new JSONArray(), smsArrFnl = new JSONArray(), dataArrFnl = new JSONArray(),
				vasArrFnl = new JSONArray(), totalRevArrFnl = new JSONArray();
		JSONArray obj = new JSONArray(), startDateArrOriginal = new JSONArray(), endDateArrOriginal = new JSONArray(),
				DateArrFnl = new JSONArray();
		JSONObject json;
		// startDateArrOriginal.add(startDate);
		// endDateArrOriginal.add(endDate);

		json = new JSONObject();
		// startDateArrOriginal = splittingFuncSpare(startDateArrOriginal, " ");
		// endDateArrOriginal = splittingFuncSpare(endDateArrOriginal, " ");
		// DateArrFnl = splittingFunc(startDateArrOriginal, endDateArrOriginal, "-");
		ObjectMapper mapper = new ObjectMapper();

		RevenueChartsReport getObj;
		if (!revenueChartList.isEmpty()) {
			for (int i = 0; i < revenueChartList.size(); i++) {
				// System.out.println("data "+
				// mapper.writeValueAsString(revenueChartList.get(i)));
				getObj = (RevenueChartsReport) revenueChartList.get(i);
				// System.out.println("data 1 "+ mapper.writeValueAsString(getObj.));
				// for (Object coa : getObj) {
				json = new JSONObject();

				json.put("label", getObj.getDate());
				// DateArrFnl.remove(0);
				DateArrFnl.add(json);

				json = new JSONObject();
				json.put("value", getObj.getVoiceRevenue());
				voiceArrFnl.add(json);

				json = new JSONObject();
				json.put("value", getObj.getSmsRevenue());
				smsArrFnl.add(json);

				json = new JSONObject();
				json.put("value", getObj.getDataRevneue());
				dataArrFnl.add(json);

				json = new JSONObject();
				json.put("value", getObj.getVasRevenue());
				vasArrFnl.add(json);

				json = new JSONObject();
				json.put("value", getObj.getTotalRevenue());
				totalRevArrFnl.add(json);
				// }
			}
			DateArrFnl = (((ArrayList<?>) DateArrFnl).isEmpty()) == true ? null : DateArrFnl;
		}

		obj.add(DateArrFnl);
		obj.add(voiceArrFnl.isEmpty() ? 0 : voiceArrFnl);
		obj.add(smsArrFnl.isEmpty() ? 0 : smsArrFnl);
		obj.add(dataArrFnl.isEmpty() ? 0 : dataArrFnl);
		obj.add(vasArrFnl.isEmpty() ? 0 : vasArrFnl);
		obj.add(totalRevArrFnl.isEmpty() ? 0 : totalRevArrFnl);

		return obj;

	}

//Method For last 2 pieCharts
	private List<Float> PieRevPerTech(List<Object[]> siteResult) {
		float revSite2g = 0, revSite2g3g = 0, revSite2g3g4g = 0;
		float site2g = 0, site2g3g = 0, site2g3g4g = 0, totTech = 0;

		float totRevTech;
		List<Float> arrlist = new ArrayList<Float>();
		if (siteResult != null && siteResult.size() != 0) {

			/*
			 * for (Object[] obj : siteResult) {
			 * 
			 * if ((Character) obj[1] == '1' && (Character) obj[2] == '0' && (Character)
			 * obj[3] == '0') { site2g += 1; revSite2g +=
			 * Float.parseFloat(obj[5].toString());// parseFloat(obj[3]) } else { revSite2g
			 * += 0; } if ((Character) obj[1] == '1' && (Character) obj[2] == '1' &&
			 * (Character) obj[3] == '0') { site2g3g += 1; revSite2g3g +=
			 * Float.parseFloat(obj[5].toString()); // parseFloat(obj[4])
			 * 
			 * } else { revSite2g3g += 0; } if ((Character) obj[1] == '1' && (Character)
			 * obj[2] == '1' && (Character) obj[3] == '1') { site2g3g4g += 1; revSite2g3g4g
			 * += Float.parseFloat(obj[5].toString());
			 * 
			 * } else { revSite2g3g4g += 0; }
			 * 
			 * }
			 */

			for (Object[] obj : siteResult) {
				if (StringUtils.equalsIgnoreCase((CharSequence) obj[0], "2G")) {
					revSite2g = ((BigDecimal) obj[2]).floatValue();
					site2g = ((BigDecimal) obj[1]).floatValue();
				} else if (StringUtils.equalsIgnoreCase((CharSequence) obj[0], "2G_3G")) {
					revSite2g3g = ((BigDecimal) obj[2]).floatValue();
					site2g3g = ((BigDecimal) obj[1]).floatValue();
				} else if (StringUtils.equalsIgnoreCase((CharSequence) obj[0], "2G_3G_4G")) {
					revSite2g3g4g = ((BigDecimal) obj[2]).floatValue();
					site2g3g4g = ((BigDecimal) obj[1]).floatValue();
				}
			}

			totRevTech = (float) Math.round((revSite2g + revSite2g3g + revSite2g3g4g) * 100) / 100;
			if (totRevTech == 0) {
				revSite2g = 100;
				revSite2g3g = 0;
				revSite2g3g4g = 0;
			} else {
				revSite2g = (revSite2g / totRevTech) * 100;
				revSite2g3g = (revSite2g3g / totRevTech) * 100;
				revSite2g3g4g = (revSite2g3g4g / totRevTech) * 100;

			}
			// totRevTech = (float) Math.round(totRevTech) * 1000 / 1000;
			totRevTech = (float) totRevTech * 1000 / 1000;

			totTech = site2g + site2g3g + site2g3g4g;

			if (totTech == 0) {
				site2g = 100;
				site2g3g = 0;
				site2g3g4g = 0;

			} else {

				site2g = (site2g / totTech) * 100;
				site2g3g = (site2g3g / totTech) * 100;
				site2g3g4g = (site2g3g4g / totTech) * 100;
			}
		} else {

			revSite2g = 100;
			revSite2g3g = 0;
			revSite2g3g4g = 0;
			totRevTech = 0;
			site2g = 100;
			site2g3g = 0;
			site2g3g4g = 0;
			totTech = 0;
		}
		arrlist.add(revSite2g);
		arrlist.add(revSite2g3g);
		arrlist.add(revSite2g3g4g);
		arrlist.add(totRevTech);
		arrlist.add(site2g);
		arrlist.add(site2g3g);
		arrlist.add(site2g3g4g);
		arrlist.add(totTech);

		// rtn.put("listChartSites", siteResult);
		return arrlist;
	}

}
