package com.aliat.alm.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SpeedCoverageReportController {
	private static final Logger logger = Logger.getLogger(SpeedCoverageReportController.class.getName());
	private static ObjectMapper mapper = new ObjectMapper();
	Object query = null;
	private Query coverage_GISquey = null, upload_GISquey = null, download_GISquey = null;
	private EntityManagerFactory emf = null;
	private EntityManager em = null;

	@Autowired
	Notify notifications;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/SpeedCoverageReport", method = RequestMethod.GET)
	public String RevenueReport(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {

			try {
				emf = Persistence.createEntityManagerFactory("persistence");
				em = emf.createEntityManager();
				notifications.headerNotification(em, model);
				/// coverage GIS
				coverage_GISquey = em.createNativeQuery(
						"select SPEEDCOVERAGEID,TO_NUMBER(COVERAGE_SIGNAL),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
								+ "where not(COVERAGE_SIGNAL is null or COVERAGE_SIGNAL='null' or COVERAGE_SIGNAL='N/A') "
								+ "and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') "
								+ "and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null')"
								+ "and not(CID is null or CID='null') "
								+ "and  SPEEDCOVERAGE_DATE between SYSDATE - INTERVAL '1' DAY and SYSDATE");

				model.addAttribute("coverage_GISquey", mapper.writeValueAsString(coverage_GISquey.getResultList()));

				/// Download GIS
				download_GISquey = em.createNativeQuery(
						"select SPEEDCOVERAGEID,TO_NUMBER(SPEED_DOWNLOAD),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
								+ "where not(SPEED_DOWNLOAD is null or SPEED_DOWNLOAD='null' or SPEED_DOWNLOAD= 'N/A') "
								+ "and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') "
								+ "and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') "
								+ "and not(CID is null or CID='null') "
								+ "and SPEEDCOVERAGE_DATE between SYSDATE - INTERVAL '1' DAY and SYSDATE");

				model.addAttribute("download_GISquey", mapper.writeValueAsString(download_GISquey.getResultList()));

				upload_GISquey = em.createNativeQuery(
						"select SPEEDCOVERAGEID,TO_NUMBER(SPEED_UPLOAD),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
								+ "where not(SPEED_UPLOAD is null or SPEED_UPLOAD='null' or SPEED_UPLOAD = 'N/A') "
								+ "and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') "
								+ "and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') "
								+ "and not(CID is null or CID='null') "
								+ "and  SPEEDCOVERAGE_DATE between SYSDATE - INTERVAL '1' DAY and SYSDATE");

				model.addAttribute("upload_GISquey", mapper.writeValueAsString(upload_GISquey.getResultList()));
				/// GRID
				LocalDateTime Now = LocalDateTime.now();
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				LocalDateTime yesterdayDT = Now.minusDays(1);
				String StartDate = yesterdayDT.format(format);
				String EndDate = Now.format(format);
				ArrayList<Object> speedCoverageReportGrid = new ArrayList<>();
				String CID = null, SignalClass = null;
				List LAC = null, avg_uploadspeed = null, avg_downloadspeed = null, avg_coveragesignal = null;
				List CIDs = em.createNativeQuery("SELECT DISTINCT CID FROM SPEED_COVERAGE_TEST "
						+ "where  not(CID is null or CID='null') "
						+ "and SPEEDCOVERAGE_DATE between systimestamp - INTERVAL '1' DAY and systimestamp ORDER BY CID")
						.getResultList();

				for (int i = 0; i < CIDs.size(); i++) {

					CID = CIDs.get(i).toString();
					List MNC = em.createNativeQuery("Select DISTINCT MNC from SPEED_COVERAGE_TEST where CID='"
							+ CIDs.get(i) + "' and not (MNC is null or MNC='null')").getResultList();
					if (MNC.get(0).toString().equalsIgnoreCase("07")) {

						List Techs = em
								.createNativeQuery("SELECT DISTINCT TECHNOLOGY FROM SPEED_COVERAGE_TEST where CID='"
										+ CID
										+ "' and not (TECHNOLOGY ='null' or TECHNOLOGY ='N/A' or TECHNOLOGY is NULL) ")
								.getResultList();

						for (int j = 0; j < Techs.size(); j++) {
							ArrayList<Object> speedCoverageReportArray = new ArrayList<>();
							speedCoverageReportArray.add(CID);
							LAC = em.createNativeQuery("SELECT DISTINCT LAC FROM SPEED_COVERAGE_TEST WHERE CID='" + CID
									+ "' and not (LAC ='null' or LAC is null)").getResultList();
							speedCoverageReportArray.add(LAC.get(0).toString());
							speedCoverageReportArray.add(StartDate);
							speedCoverageReportArray.add(EndDate);
							avg_coveragesignal = em.createNativeQuery(
									"SELECT TO_CHAR(ROUND(AVG(COVERAGE_SIGNAL))) FROM SPEED_COVERAGE_TEST where CID='"
											+ CID + "' and TECHNOLOGY='" + Techs.get(j).toString()
											+ "' and not(COVERAGE_SIGNAL is null or COVERAGE_SIGNAL='null' or COVERAGE_SIGNAL='N/A')")
									.getResultList();
							System.out.println(avg_coveragesignal.get(0));
							speedCoverageReportArray.add(avg_coveragesignal.get(0));
							if (Integer.parseInt(avg_coveragesignal.get(0).toString()) <= -95) {
								SignalClass = "Extremely Bad Signal";
							} else if (Integer.parseInt(avg_coveragesignal.get(0).toString()) > -95
									&& Integer.parseInt(avg_coveragesignal.get(0).toString()) <= -85) {
								SignalClass = "Bad Signal";
							} else if (Integer.parseInt(avg_coveragesignal.get(0).toString()) > -85
									&& Integer.parseInt(avg_coveragesignal.get(0).toString()) <= -75) {
								SignalClass = "Fair Signal";
							} else if (Integer.parseInt(avg_coveragesignal.get(0).toString()) > -75
									&& Integer.parseInt(avg_coveragesignal.get(0).toString()) <= -65) {
								SignalClass = "Good Signal";
							} else if (Integer.parseInt(avg_coveragesignal.get(0).toString()) > -65) {
								SignalClass = "Excellent Signal";
							}
							speedCoverageReportArray.add(SignalClass);
							avg_uploadspeed = em.createNativeQuery(
									"SELECT nvl(TO_CHAR(ROUND(AVG(SPEED_UPLOAD),2)),'Upload Speed Not Captured') FROM SPEED_COVERAGE_TEST where CID='"
											+ CID + "' and TECHNOLOGY='" + Techs.get(j).toString()
											+ "' and  NOT (SPEED_UPLOAD = 'N/A' or SPEED_UPLOAD = 'null' or SPEED_UPLOAD is Null)")
									.getResultList();
							speedCoverageReportArray.add(avg_uploadspeed.get(0).toString());
							avg_downloadspeed = em.createNativeQuery(
									"SELECT nvl(TO_CHAR(ROUND(AVG(SPEED_DOWNLOAD),2)),'Download Speed Not Captured') FROM SPEED_COVERAGE_TEST where CID='"
											+ CID + "' and TECHNOLOGY='" + Techs.get(j).toString()
											+ "' and NOT (SPEED_DOWNLOAD = 'N/A' or SPEED_DOWNLOAD = 'null' or SPEED_DOWNLOAD is Null)")
									.getResultList();
							speedCoverageReportArray.add(avg_downloadspeed.get(0).toString());
							speedCoverageReportArray.add(Techs.get(j).toString());

							speedCoverageReportGrid.add(speedCoverageReportArray);

						}

					}

				}

				model.addAttribute("speedCoverageReportGrid", mapper.writeValueAsString(speedCoverageReportGrid));

			} catch (Exception e) {
				logger.info("Error on Speed Coverage Report with a message : " + e);
				e.printStackTrace();
				model.addAttribute("coverage_GISquey", "");
			} finally {
				if (em != null && em.isOpen()) {
					em.close();
				}
				if (emf != null && emf.isOpen()) {
					emf.close();
				}
			}
		}

		return "Reports/SpeedCoverageReport";

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/GenerateSpeedCoverageReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateSpeedCoverageReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		ArrayList<Object> speedCoverageReportGrid = new ArrayList<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return rtn;
		} else {

			try {
				emf = Persistence.createEntityManagerFactory("persistence");
				em = emf.createEntityManager();
				notifications.headerNotification(em, model);
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

				///// Generate Report for GRID TABLE
				String CID = null, SignalClass = null;
				List LAC = null, avg_uploadspeed = null, avg_downloadspeed = null, avg_coveragesignal = null;
				List CIDs = em.createNativeQuery("SELECT DISTINCT CID FROM SPEED_COVERAGE_TEST "
						+ "where not(CID is null or CID='null') and SPEEDCOVERAGE_DATE between TO_DATE('" + StartDate
						+ "','MM/DD/YYYY HH24:MI:SS') and TO_DATE('" + EndDate
						+ "','MM/DD/YYYY HH24:MI:SS') ORDER BY CID ").getResultList();

				for (int i = 0; i < CIDs.size(); i++) {

					CID = CIDs.get(i).toString();
					List MNC = em
							.createNativeQuery("Select DISTINCT MNC from SPEED_COVERAGE_TEST where CID='"
									+ CIDs.get(i).toString() + "' and not (MNC ='null' or MNC is null)")
							.getResultList();
					if (MNC.size() > 0 && MNC.get(0).toString().equalsIgnoreCase("07")) {

						List Techs = em.createNativeQuery(
								"SELECT DISTINCT TECHNOLOGY FROM SPEED_COVERAGE_TEST where  not(TECHNOLOGY is null or TECHNOLOGY='null' or TECHNOLOGY ='N/A')"
										+ " and CID='" + CID + "'")
								.getResultList();

						for (int j = 0; j < Techs.size(); j++) {
							ArrayList<Object> speedCoverageReportArray = new ArrayList<>();
							speedCoverageReportArray.add(CID);
							LAC = em.createNativeQuery(
									"SELECT DISTINCT LAC FROM SPEED_COVERAGE_TEST WHERE CID='" + CID + "'")
									.getResultList();
							speedCoverageReportArray.add(LAC.get(0).toString());
							speedCoverageReportArray.add(StartDate);
							speedCoverageReportArray.add(EndDate);
							avg_coveragesignal = em.createNativeQuery(
									"SELECT TO_CHAR(ROUND(AVG(COVERAGE_SIGNAL))) FROM SPEED_COVERAGE_TEST where not(COVERAGE_SIGNAL is null or COVERAGE_SIGNAL='null' or COVERAGE_SIGNAL='N/A')"
											+ " and CID='" + CID + "' and TECHNOLOGY='" + Techs.get(j).toString() + "'")
									.getResultList();
							System.out.println(avg_coveragesignal.get(0));
							if (avg_coveragesignal.get(0).toString() == "null") {
								System.out.println("NULL");
							} else {
								speedCoverageReportArray.add(avg_coveragesignal.get(0).toString());
								if (Integer.parseInt(avg_coveragesignal.get(0).toString()) <= -95) {
									SignalClass = "Extremely Bad Signal";
								} else if (Integer.parseInt(avg_coveragesignal.get(0).toString()) > -95
										&& Integer.parseInt(avg_coveragesignal.get(0).toString()) <= -85) {
									SignalClass = "Bad Signal";
								} else if (Integer.parseInt(avg_coveragesignal.get(0).toString()) > -85
										&& Integer.parseInt(avg_coveragesignal.get(0).toString()) <= -75) {
									SignalClass = "Fair Signal";
								} else if (Integer.parseInt(avg_coveragesignal.get(0).toString()) > -75
										&& Integer.parseInt(avg_coveragesignal.get(0).toString()) <= -65) {
									SignalClass = "Good Signal";
								} else if (Integer.parseInt(avg_coveragesignal.get(0).toString()) > -65) {
									SignalClass = "Excellent Signal";
								}
							}

							speedCoverageReportArray.add(SignalClass);
							avg_uploadspeed = em.createNativeQuery(
									"SELECT nvl(TO_CHAR(ROUND(AVG(SPEED_UPLOAD),2)),'Upload Speed Not Captured') FROM SPEED_COVERAGE_TEST where CID='"
											+ CID + "' and TECHNOLOGY='" + Techs.get(j).toString()
											+ "' and NOT (SPEED_UPLOAD = 'N/A' or SPEED_UPLOAD = 'null' or SPEED_UPLOAD is Null)")
									.getResultList();
							speedCoverageReportArray.add(avg_uploadspeed.get(0).toString());
							avg_downloadspeed = em.createNativeQuery(
									"SELECT nvl(TO_CHAR(ROUND(AVG(SPEED_DOWNLOAD),2)),'Download Speed Not Captured') FROM SPEED_COVERAGE_TEST where CID='"
											+ CID + "' and TECHNOLOGY='" + Techs.get(j).toString()
											+ "' and NOT (SPEED_DOWNLOAD = 'N/A' or SPEED_DOWNLOAD = 'null' or SPEED_DOWNLOAD is Null)")
									.getResultList();
							speedCoverageReportArray.add(avg_downloadspeed.get(0).toString());
							speedCoverageReportArray.add(Techs.get(j).toString());
							speedCoverageReportGrid.add(speedCoverageReportArray);

						}

					}

				}
				rtn.put("speedCoverageReportGrid", speedCoverageReportGrid);

				/// coverage GIS
				coverage_GISquey = em.createNativeQuery(
						"select SPEEDCOVERAGEID,TO_NUMBER(COVERAGE_SIGNAL),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
								+ "where not(COVERAGE_SIGNAL is null or COVERAGE_SIGNAL='null' or COVERAGE_SIGNAL='N/A')"
								+ "and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') "
								+ "and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') "
								+ "and not(CID is null or CID='null') " + "and SPEEDCOVERAGE_DATE between TO_DATE('"
								+ StartDate + "','MM/DD/YYYY HH24:MI:SS') and TO_DATE('" + EndDate
								+ "','MM/DD/YYYY HH24:MI:SS')");

				rtn.put("CoverageReportGIS", coverage_GISquey.getResultList());

				/// Download GIS GENERATE REPROT
				download_GISquey = em.createNativeQuery(
						"select SPEEDCOVERAGEID,TO_NUMBER(SPEED_DOWNLOAD),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
								+ "where not(SPEED_DOWNLOAD is null or SPEED_DOWNLOAD='null' or SPEED_DOWNLOAD= 'N/A') "
								+ "and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') "
								+ "and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') "
								+ "and not(CID is null or CID='null') "
								+ "and(SPEED_UPLOAD !='N/A' and SPEED_DOWNLOAD != 'N/A') and SPEEDCOVERAGE_DATE between TO_DATE('"
								+ StartDate + "','MM/DD/YYYY HH24:MI:SS') and TO_DATE('" + EndDate
								+ "','MM/DD/YYYY HH24:MI:SS')");
				rtn.put("SpeedDownReportGIS", download_GISquey.getResultList());

				/// Upload GIS GENERATE REPROT
				upload_GISquey = em.createNativeQuery(
						"select SPEEDCOVERAGEID,TO_NUMBER(SPEED_UPLOAD),TO_NUMBER(SPEEDCOVERAGE_LAT),TO_NUMBER(SPEEDCOVERAGE_LNG),TECHNOLOGY,AGENT_NAME,AGENT_NUMBER,CID from SPEED_COVERAGE_TEST "
								+ "where not(SPEED_UPLOAD is null or SPEED_UPLOAD='null' or SPEED_UPLOAD = 'N/A') "
								+ "and not(SPEEDCOVERAGE_LAT is null or SPEEDCOVERAGE_LAT='null') "
								+ "and not(SPEEDCOVERAGE_LNG is null or SPEEDCOVERAGE_LNG='null') "
								+ "and not(CID is null or CID='null') "
								+ "and(SPEED_UPLOAD !='N/A' and SPEED_DOWNLOAD != 'N/A') and SPEEDCOVERAGE_DATE between TO_DATE('"
								+ StartDate + "','MM/DD/YYYY HH24:MI:SS') and TO_DATE('" + EndDate
								+ "','MM/DD/YYYY HH24:MI:SS')");
				rtn.put("SpeedUpReportGIS", upload_GISquey.getResultList());

			} catch (Exception e) {
				logger.info("Error on Speed Coverage Report with a message : " + e);
				e.printStackTrace();
				model.addAttribute("coverage_GISquey", "");
			} finally {
				if (em != null && em.isOpen()) {
					em.close();
				}
				if (emf != null && emf.isOpen()) {
					emf.close();
				}
			}
		}

		return rtn;

	}

}
