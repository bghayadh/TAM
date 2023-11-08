package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MobileDashboardController {

	private static final Logger logger = Logger.getLogger(MobileDashboardController.class.getName());
	public String AGENTID = null;
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	public static double PI = 3.14159265;
	public static double TWOPI = 2 * PI;
	@SuppressWarnings("rawtypes")
	private Query coverage_GISquey = null, upload_GISquey = null, download_GISquey = null;

	@Autowired
	ALMSessions almsessions;

	@Autowired
	Notify notifications;

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/MobileDashboard", method = RequestMethod.GET)
	public String MobileDashboard(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		double sellLat = 0.0;
		double sellLong = 0.0;
		int coast = 0, south = 0, north = 0, central = 0, western = 0, undefined = 0;

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {
				/// get the number of approved agents of all time
				query = session.createNativeQuery(
						"SELECT nvl(sum(case when STATUS = 'In Progress' then 1 else 0 end ),0) as In_Progress,\r\n"
								+ "       nvl(sum(case when STATUS = 'Activated' then 1 else 0 end),0) as Activated,\r\n"
								+ "       nvl(sum(case when STATUS = 'Deactivated' then 1 else 0 end),0) as Deactivated,\r\n"
								+ "       nvl(sum(case when  STATUS = 'Cancelled' then 1 else 0 end),0) as Cancelled\r\n"
								+ " FROM Agent");

				model.addAttribute("approvedAgents", mapper.writeValueAsString(query.list()));

				ArrayList<String> coast_polygon = new ArrayList<String>();
				ArrayList<String> southNairobi_polygon = new ArrayList<String>();
				ArrayList<String> northNairobi_polygon = new ArrayList<String>();
				ArrayList<String> western_polygon = new ArrayList<String>();
				ArrayList<String> central_polygon = new ArrayList<String>();
				ArrayList<String> other = new ArrayList<String>();
				List<String> lat_long = new ArrayList<String>();
				/// query to get the borders of coast
				query = session.createNativeQuery(
						"SELECT TO_NUMBER(nvl(LATITUDE,0)),TO_NUMBER(nvl(LONGTITUDE,0)) FROM REGION_BORDER WHERE REGION_ID='REGION_2021_1' ORDER BY SEQ_SORTING + 0 ASC");
				for (int i = 0; i < query.list().size(); i++) {
					coast_polygon.add(mapper.writeValueAsString(query.list().get(i)));
				}
				ArrayList<Double> lat_array_coast = new ArrayList<Double>();
				ArrayList<Double> long_array_coast = new ArrayList<Double>();
				for (int i = 0; i < coast_polygon.size(); i++) {
					lat_array_coast.add(Double.parseDouble(coast_polygon.get(i).replace("[", "").split(",")[0]));
					long_array_coast.add(Double.parseDouble(coast_polygon.get(i).replace("]", "").split(",")[1]));
				}

				/// query to get the borders of western region
				query = session.createNativeQuery(
						"SELECT TO_NUMBER(nvl(LATITUDE,0)),TO_NUMBER(nvl(LONGTITUDE,0)) FROM REGION_BORDER WHERE REGION_ID='REGION_2021_3' ORDER BY SEQ_SORTING + 0 ASC");
				for (int i = 0; i < query.list().size(); i++) {
					western_polygon.add(mapper.writeValueAsString(query.list().get(i)));
				}

				ArrayList<Double> lat_array_western = new ArrayList<Double>();
				ArrayList<Double> long_array_western = new ArrayList<Double>();
				for (int i = 0; i < western_polygon.size(); i++) {
					lat_array_western.add(Double.parseDouble(western_polygon.get(i).replace("[", "").split(",")[0]));
					long_array_western.add(Double.parseDouble(western_polygon.get(i).replace("]", "").split(",")[1]));
				}

				/// query to get the borders of north nairobi region
				query = session.createNativeQuery(
						"SELECT TO_NUMBER(nvl(LATITUDE,0)),TO_NUMBER(nvl(LONGTITUDE,0)) FROM REGION_BORDER WHERE REGION_ID='REGION_2022_24' ORDER BY SEQ_SORTING + 0 ASC");
				for (int i = 0; i < query.list().size(); i++) {
					northNairobi_polygon.add(mapper.writeValueAsString(query.list().get(i)));
				}
				ArrayList<Double> lat_array_nNairobi = new ArrayList<Double>();
				ArrayList<Double> long_array_nNairobi = new ArrayList<Double>();
				for (int i = 0; i < northNairobi_polygon.size(); i++) {
					lat_array_nNairobi
							.add(Double.parseDouble(northNairobi_polygon.get(i).replace("[", "").split(",")[0]));
					long_array_nNairobi
							.add(Double.parseDouble(northNairobi_polygon.get(i).replace("]", "").split(",")[1]));
				}

				/// query to get the borders of central region
				query = session.createNativeQuery(
						"SELECT TO_NUMBER(nvl(LATITUDE,0)),TO_NUMBER(nvl(LONGTITUDE,0)) FROM REGION_BORDER WHERE REGION_ID='REGION_2022_22' ORDER BY SEQ_SORTING + 0 ASC");
				for (int i = 0; i < query.list().size(); i++) {
					central_polygon.add(mapper.writeValueAsString(query.list().get(i)));
				}
				ArrayList<Double> lat_array_central = new ArrayList<Double>();
				ArrayList<Double> long_array_central = new ArrayList<Double>();
				for (int i = 0; i < central_polygon.size(); i++) {
					lat_array_central.add(Double.parseDouble(central_polygon.get(i).replace("[", "").split(",")[0]));
					long_array_central.add(Double.parseDouble(central_polygon.get(i).replace("]", "").split(",")[1]));
				}

				/// query to get the borders of south nairobi region
				query = session.createNativeQuery(
						"SELECT TO_NUMBER(nvl(LATITUDE,0)),TO_NUMBER(nvl(LONGTITUDE,0)) FROM REGION_BORDER WHERE REGION_ID='REGION_2022_26' ORDER BY SEQ_SORTING + 0 ASC");
				for (int i = 0; i < query.list().size(); i++) {
					southNairobi_polygon.add(mapper.writeValueAsString(query.list().get(i)));
				}
				ArrayList<Double> lat_array_sNairobi = new ArrayList<Double>();
				ArrayList<Double> long_array_sNairobi = new ArrayList<Double>();
				for (int i = 0; i < southNairobi_polygon.size(); i++) {
					lat_array_sNairobi
							.add(Double.parseDouble(southNairobi_polygon.get(i).replace("[", "").split(",")[0]));
					long_array_sNairobi
							.add(Double.parseDouble(southNairobi_polygon.get(i).replace("]", "").split(",")[1]));
				}

				/// query to get the selling lat and long from clients table for the last 24
				/// hours
				query = session.createNativeQuery(
						"SELECT nvl(TO_NUMBER(SELLING_LATITUDE),0),nvl(TO_NUMBER(SELLING_LONGITUDE),0) FROM CLIENTS where   CREATED_DATE between SYSDATE - INTERVAL '1' DAY and SYSDATE and REGISTRATION_STATUS='Success'");
				List<Double> result = query.list();

				for (int i = 0; i < result.size(); i++) {
					lat_long.add(mapper.writeValueAsString(result.get(i)));
				}

				String totalRegistration = session.createNativeQuery(
						"SELECT COUNT (*) FROM CLIENTS where  CREATED_DATE between SYSDATE - INTERVAL '1' DAY and SYSDATE and REGISTRATION_STATUS='Success'")
						.uniqueResult().toString();

				ArrayList<Integer> salesPerRegion = new ArrayList<Integer>();
				for (int i = 0; i < lat_long.size(); i++) {

					sellLat = Double.parseDouble(lat_long.get(i).replace("[", "").split(",")[0]);
					sellLong = Double.parseDouble(lat_long.get(i).replace("]", "").split(",")[1]);

					if (insideRegion(lat_array_coast, long_array_coast, (sellLat), (sellLong)) == true) {
						coast++;
					} else if (insideRegion(lat_array_western, long_array_western, (sellLat), (sellLong)) == true) {
						western++;
					} else if (insideRegion(lat_array_nNairobi, long_array_nNairobi, (sellLat), (sellLong)) == true) {
						north++;
					} else if (insideRegion(lat_array_central, long_array_central, (sellLat), (sellLong)) == true) {
						central++;
					} else if (insideRegion(lat_array_sNairobi, long_array_sNairobi, (sellLat), (sellLong)) == true) {
						south++;
					} else {
						undefined++;
					}
				}

				salesPerRegion.add(Integer.parseInt(totalRegistration));
				salesPerRegion.add(coast);
				salesPerRegion.add(western);
				salesPerRegion.add(north);
				salesPerRegion.add(central);
				salesPerRegion.add(south);
				salesPerRegion.add(undefined);

				model.addAttribute("salesPerRegion", salesPerRegion);

				/// coverage GIS
				coverage_GISquey = session.createNativeQuery(
						"select SPEEDCOVERAGEID,TO_NUMBER(COVERAGE_SIGNAL),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
								+ "where not(COVERAGE_SIGNAL is null or COVERAGE_SIGNAL='null' or COVERAGE_SIGNAL='N/A') "
								+ "and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') "
								+ "and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null')"
								+ "and not(CID is null or CID='null') "
								+ "and  SPEEDCOVERAGE_DATE between SYSDATE - INTERVAL '1' DAY and SYSDATE");

				model.addAttribute("coverage_GISquey", mapper.writeValueAsString(coverage_GISquey.list()));

				/// Download GIS
				download_GISquey = session.createNativeQuery(
						"select SPEEDCOVERAGEID,TO_NUMBER(SPEED_DOWNLOAD),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
								+ "where not(SPEED_DOWNLOAD is null or SPEED_DOWNLOAD='null' or SPEED_DOWNLOAD= 'N/A') "
								+ "and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') "
								+ "and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') "
								+ "and not(CID is null or CID='null') "
								+ "and SPEEDCOVERAGE_DATE between SYSDATE - INTERVAL '1' DAY and SYSDATE");

				model.addAttribute("download_GISquey", mapper.writeValueAsString(download_GISquey.list()));

				upload_GISquey = session.createNativeQuery(
						"select SPEEDCOVERAGEID,TO_NUMBER(SPEED_UPLOAD),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
								+ "where not(SPEED_UPLOAD is null or SPEED_UPLOAD='null' or SPEED_UPLOAD = 'N/A') "
								+ "and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') "
								+ "and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') "
								+ "and not(CID is null or CID='null') "
								+ "and  SPEEDCOVERAGE_DATE between SYSDATE - INTERVAL '1' DAY and SYSDATE");

				model.addAttribute("upload_GISquey", mapper.writeValueAsString(upload_GISquey.list()));

				// Google maps query
				model.addAttribute("agentSalesCountList", mapper.writeValueAsString(session.createNativeQuery(
						"select count(*),a.agent_id, a.full_name,a.latitude,a.longitude,a.msisdn from agent a inner join clients b on a.msisdn = b.agent_number where b.created_date >=  trunc(sysdate) AND b.created_date < (trunc(sysdate) ) + 1 and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success'  GROUP BY a.agent_id,a.full_name,a.latitude,a.longitude,a.msisdn")
						.list()));

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in MobileDashboard due to \n " + exceptionAsString);
				logger.info("Error in MobileDashboard due to \n " + exceptionAsString);
			}finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}

		return "MobileDashboard";
	}

	public boolean insideRegion(ArrayList<Double> region_lat, ArrayList<Double> region_long, double PointLat,
			double PointLong) {
		int n = region_lat.size();
		int counter = 0;
		double lat1, lng1, lat2, lng2;
		double xinters = 0;

		lat1 = region_lat.get(0);
		lng1 = region_long.get(0);

		for (int i = 1; i <= n; i++) {
			lat2 = region_lat.get(i % n);
			lng2 = region_long.get(i % n);
			if (PointLong > Math.min(lng1, lng2)) {
				if (PointLong <= Math.max(lng1, lng2)) {
					if (PointLat <= Math.max(lat1, lat2)) {

						if (lng1 != lng2) {
							xinters = (PointLong - lng1) * (lat2 - lat1) / (lng2 - lng1) + lat1;
							if (lat1 == lat2 || PointLat <= xinters)
								counter++;
						}
					}
				}
			}
			lat1 = lat2;
			lng1 = lng2;
		}
		if (counter % 2 == 0)
			return false;
		else
			return true;

	}

	@RequestMapping(value = "/SimSalesFilter", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SimSalesFilter(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return rtn;
		} else {
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {

				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
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

					query = session.createNativeQuery(
							"select count(*),a.agent_id, a.full_name,a.latitude,a.longitude,a.msisdn from agent a inner join clients b on a.msisdn = b.agent_number"
									+ " where b.CREATED_DATE between TO_DATE('" + StartDate
									+ "','MM/DD/YYYY HH24:MI:SS') " + "and TO_DATE('" + EndDate
									+ "','MM/DD/YYYY HH24:MI:SS') and b.status !='DEACTIVATED' and b.status !='CANCELLED' AND b.REGISTRATION_STATUS ='Success'  GROUP BY a.agent_id,a.full_name,a.latitude,a.longitude,a.msisdn");

					rtn.put("agentSales", query.list());
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in SimSalesFilter due to \n " + exceptionAsString);
					logger.info("Error in SimSalesFilter due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/SpeedCoverageFilter", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SpeedCoverageFilter(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return rtn;
		} else {
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {

				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
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

					/// coverage GIS
					coverage_GISquey = session.createNativeQuery(
							"select SPEEDCOVERAGEID,TO_NUMBER(COVERAGE_SIGNAL),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
									+ "where not(COVERAGE_SIGNAL is null or COVERAGE_SIGNAL='null' or COVERAGE_SIGNAL='N/A')"
									+ "and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') "
									+ "and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') "
									+ "and not(CID is null or CID='null') " + "and SPEEDCOVERAGE_DATE between TO_DATE('"
									+ StartDate + "','MM/DD/YYYY HH24:MI:SS') and TO_DATE('" + EndDate
									+ "','MM/DD/YYYY HH24:MI:SS')");

					rtn.put("CoverageReportGIS", coverage_GISquey.list());

					/// Download GIS GENERATE REPROT
					download_GISquey = session.createNativeQuery(
							"select SPEEDCOVERAGEID,TO_NUMBER(SPEED_DOWNLOAD),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
									+ "where not(SPEED_DOWNLOAD is null or SPEED_DOWNLOAD='null' or SPEED_DOWNLOAD= 'N/A') "
									+ "and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') "
									+ "and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') "
									+ "and not(CID is null or CID='null') "
									+ "and(SPEED_UPLOAD !='N/A' and SPEED_DOWNLOAD != 'N/A') and SPEEDCOVERAGE_DATE between TO_DATE('"
									+ StartDate + "','MM/DD/YYYY HH24:MI:SS') and TO_DATE('" + EndDate
									+ "','MM/DD/YYYY HH24:MI:SS')");
					rtn.put("SpeedDownReportGIS", download_GISquey.list());

					/// Upload GIS GENERATE REPROT
					upload_GISquey = session.createNativeQuery(
							"select SPEEDCOVERAGEID,TO_NUMBER(SPEED_UPLOAD),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
									+ "where not(SPEED_UPLOAD is null or SPEED_UPLOAD='null' or SPEED_UPLOAD = 'N/A') "
									+ "and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') "
									+ "and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') "
									+ "and not(CID is null or CID='null') "
									+ "and(SPEED_UPLOAD !='N/A' and SPEED_DOWNLOAD != 'N/A') and SPEEDCOVERAGE_DATE between TO_DATE('"
									+ StartDate + "','MM/DD/YYYY HH24:MI:SS') and TO_DATE('" + EndDate
									+ "','MM/DD/YYYY HH24:MI:SS')");
					rtn.put("SpeedUpReportGIS", upload_GISquey.list());

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in SpeedCoverageFilter due to \n " + exceptionAsString);
					logger.info("Error in SpeedCoverageFilter due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			}
		}
		return rtn;
	}
}
