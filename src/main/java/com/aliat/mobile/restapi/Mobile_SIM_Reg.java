package com.aliat.mobile.restapi;

import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.models.AgentShops;
import com.aliat.alm.models.ClientListViewAPI;
import com.aliat.alm.models.DisplayShopsAPI;
import com.aliat.alm.models.MobileAgentAPI;
import com.aliat.alm.models.MobileClientAPI;
import com.aliat.alm.models.RegionName;
import com.aliat.alm.models.ShopImageAPI;
import com.aliat.alm.models.ShopsAPI;
import com.aliat.alm.models.ShopsListViewAPI;
import com.aliat.alm.models.SimRegReportAPI;
import com.aliat.alm.models.SimRegistrationAPI;
import com.aliat.alm.models.SpeedCoverageTestAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import com.aliat.alm.models.MobileChargeListViewAPI;

@Controller
@RestController
public class Mobile_SIM_Reg {

	private static StringWriter sw;
	private static String exceptionAsString;
	private static final Logger logger = Logger.getLogger(Mobile_SIM_Reg.class.getName());

	private static ObjectMapper mapper = new ObjectMapper();
	@Autowired
	ALMSessions almsessions;

	private static Session session = null;
	private static Transaction tx = null;
	@SuppressWarnings("rawtypes")
	private static NativeQuery query = null;
	private static String data = null, msisdn = null;

	// Method to get Regions from Regions Table
	/*
	 * @RequestMapping(value = "/getRegionstest", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public ResponseEntity<String> getRegionstest() {
	 * 
	 * String regions = null; HttpHeaders responseHeaders = new HttpHeaders();
	 * responseHeaders.set("Baeldung-Example-Header", "response"); session =
	 * almsessions.getALMSession();
	 * 
	 * if (session != null && session.isOpen()) { tx = session.beginTransaction();
	 * try { regions = "bisoy"; //
	 * session.createNativeQuery("SELECT REGION_NAME FROM REGION").list().toString();
	 * 
	 * }
	 * 
	 * catch (Exception e) {
	 * System.out.println("Error in creating session with the getRegions method" +
	 * e.getMessage());
	 * logger.info("Error in creating session with the DataBase getRegions: " +
	 * e.getMessage());
	 * 
	 * } finally { if (session != null && session.isOpen()) { tx.commit();
	 * session.close(); session.getSessionFactory().close(); } } } else {
	 * System.out.println("could not connect to DB in  getRegions method");
	 * logger.info("could not connect to DB getRegions: "); }
	 * 
	 * // return regions; return ResponseEntity.ok().headers(responseHeaders).
	 * body("Response with header using ResponseEntity bisoy");
	 * 
	 * }
	 */

	@SuppressWarnings("unchecked")
	// Method to get Regions from Regions Table
	@RequestMapping(value = "/getRegions", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getRegions() {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getRegions", "response_getRegions");
		List<String[]> regions = null;
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				regions = session.createNativeQuery("SELECT REGION_NAME FROM REGION ORDER BY REGION_ID ASC").list();
				query = session.createNativeQuery("SELECT REGION_ID FROM REGION ");
				data = mapper.writeValueAsString(query.list() + ":" + regions);
				System.out.println(data);
			}

			catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getRegions due to \n " + exceptionAsString);
				logger.info("Error in getRegions due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getRegions method");
			logger.info("Could not connect to DB in getRegions method");
			logger.finest("Could not connect to DB in getRegions method");

		}

		// return regions;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	// Method to get Regions from Regions Table
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRegions_Areas", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<Object, Object>> getRegions_Areas() {

		Map<Object, Object> rtn = new LinkedHashMap<>();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getRegions_Areas", "response_getRegions_Areas");
		List<Object[]> regions = null, areaID = null, areaName = null;
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				regions = session.createNativeQuery("SELECT REGION_NAME FROM REGION ORDER BY REGION_ID ASC").list();
				query = session.createNativeQuery("SELECT REGION_ID FROM REGION ");
				data = mapper.writeValueAsString(query.list() + ":" + regions);
				rtn.put("Regions", data);

				if (regions.size() > 0) {
					for (int i = 0; i < regions.size(); i++) {

						areaID = session
								.createNativeQuery("SELECT AREA_ID FROM AREA where REGION_NAME ='" + regions.get(i) + "'")
								.list();
						areaName = session
								.createNativeQuery(
										"SELECT AREA_NAME FROM  AREA where REGION_NAME ='" + regions.get(i) + "'")
								.list();

						data = mapper.writeValueAsString(areaID + ":" + areaName);
						rtn.put(regions.get(i), data);
					}
				}

				// data = mapper.writeValueAsString(regions);
				System.out.println(rtn);
			}

			catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getRegions due to \n " + exceptionAsString);
				logger.info("Error in getRegions due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getRegions method");
			logger.info("Could not connect to DB in getRegions method");
			logger.finest("Could not connect to DB in getRegions method");

		}

		// return regions;
		return ResponseEntity.ok().headers(responseHeaders).body(rtn);

	}

	// Method to check the validation of the agent number if exist
	@RequestMapping(value = "/agentNumberValidation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> agentNumberValidation(@RequestBody MobileAgentAPI agentRegistration) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("agentNumberValidation", "response_agentNumberValidation");

		boolean agentExistence = false;
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				msisdn = agentRegistration.getMsisdn();

				logger.finest("MSISDN in agentNumberValidation " + msisdn);
				logger.info("MSISDN in agentNumberValidation " + msisdn);
				if (msisdn != null && !msisdn.equalsIgnoreCase("")) {

					query = session.createNativeQuery(
							"select MSISDN from Agent where MSISDN='" + msisdn + "' and STATUS ='Activated'");

					System.out.println(query.uniqueResult());
					if (query.uniqueResult() != null) {
						agentExistence = true;
					} else {
						agentExistence = false;
					}
				} else {
					logger.finest("MSISDN IS NULL in agentNumberValidation");
					logger.info("MSISDN IS NULL in agentNumberValidation");
				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in agentNumberValidation due to \n " + exceptionAsString);
				logger.info("Error in agentNumberValidation due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in agentNumberValidation method");
			logger.info("Could not connect to DB in agentNumberValidation method");
			logger.finest("Could not connect to DB in agentNumberValidation method");

		}

		// return agentExistence;
		return ResponseEntity.ok().headers(responseHeaders).body(agentExistence);

	}

	// Method to save the agent in the Agent Table
	@SuppressWarnings("unused")
	@RequestMapping(value = "/savingAgent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> savingAgent(@RequestBody MobileAgentAPI agentRegistration) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("savingAgent", "response_savingAgent");
		boolean agentExistence = false;

		session = almsessions.getSession();

		data = null;
		// session = almsessions.getALMSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				msisdn = agentRegistration.getMsisdn();
				if (msisdn != null && !msisdn.equalsIgnoreCase("")) {
					query = session.createNativeQuery("select MSISDN from Agent where MSISDN='" + msisdn + "'");

					if (query.uniqueResult() != null) {
						data = "Existed";

					} else {

						session.flush();
						Date date = new Date();
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(date);
						int year = calendar.get(Calendar.YEAR);
						String agentId = null;
						synchronized (this) {
							// agentId = "AG_" + year + "_" + appConfig.getSequenceNbr(42);
							agentId = "AG_" + year + "_" + Integer.parseInt(
									session.createNativeQuery("SELECT AGENT FROM SEQ_TABLE").uniqueResult().toString());
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET AGENT = AGENT + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						Timestamp CreationDate, LastModified;
						CreationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
						LastModified = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

						MobileAgentAPI Agent = new MobileAgentAPI();
						Agent.setAgentId(agentId);
						Agent.setFirstName(agentRegistration.getFirstName());
						Agent.setLastName(agentRegistration.getLastName());
						Agent.setDisplayName(agentRegistration.getDisplayName());
						Agent.setAddress(agentRegistration.getAddress());
						Agent.setEmail(agentRegistration.getEmail());
						Agent.setMsisdn(agentRegistration.getMsisdn());
						Agent.setLongitude(agentRegistration.getLongitude());
						Agent.setLatitude(agentRegistration.getLatitude());
						Agent.setRegionName(agentRegistration.getRegionName());
						Agent.setRegionID(agentRegistration.getRegionID());
						Agent.setStatus(agentRegistration.getStatus());
						Agent.setPinCode(agentRegistration.getPinCode());
						Agent.setAgentImage(agentRegistration.getAgentImage());
						Agent.setAgentfrontID(agentRegistration.getAgentfrontID());
						Agent.setAgentbackID(agentRegistration.getAgentbackID());
						Agent.setVerificationCode(agentRegistration.getVerificationCode());
						Agent.setCreateDate(CreationDate);
						Agent.setLastModifiedDate(LastModified);
						Agent.setFullName(agentRegistration.getFirstName() + " " + agentRegistration.getLastName());
						Agent.setAgentImagestatus("0");
						Agent.setAgentfrontIDstatus("0");
						Agent.setAgentbackIDstatus("0");

						if (session.isOpen() && session != null) {

							session.saveOrUpdate(Agent);
							data = "Saved";

						}

					}
				} else {
					logger.finest("MSISDN IS NULL");
					logger.info("MSISDN IS NULL");
					agentExistence = true;
					data = "not saved";
				}

			} catch (Exception e) {
				data = "not saved";
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in savingAgent due to \n " + exceptionAsString);
				logger.info("Error in savingAgent due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}

		} else {
			System.out.println("Could not connect to DB in savingAgent method");
			logger.info("Could not connect to DB in savingAgent method");
			logger.finest("Could not connect to DB in savingAgent method");

		}

		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	// Update the agent photos status
	@RequestMapping(value = "/updateAgentPhotosStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> updateAgentPhotosStatus(@RequestBody String agentphotostatus) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("updateAgentPhotosStatus", "response_updateAgentPhotosStatus");

		data = null;

		String vcolname = null, vsimregid = null;
		int val1 = 0;
		String[] data1, data2, data3, data4;
		agentphotostatus = agentphotostatus.substring(2, Integer.parseInt(String.valueOf(agentphotostatus.length())));
		StringBuffer sb = new StringBuffer(agentphotostatus);
		sb.deleteCharAt(sb.length() - 1);
		agentphotostatus = sb.toString();
		data1 = agentphotostatus.split(",");
		data2 = data1[0].split("\":\"");
		vsimregid = data2[1];
		sb = new StringBuffer(vsimregid);
		sb.deleteCharAt(sb.length() - 1);
		vsimregid = sb.toString();
		data3 = data1[1].split("\":\"");
		vcolname = data3[1];
		sb = new StringBuffer(vcolname);
		sb.deleteCharAt(sb.length() - 1);
		vcolname = sb.toString();
		data4 = data1[2].split(":");
		val1 = Integer.parseInt(data4[1]);

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				query = session.createNativeQuery("update AGENT set " + vcolname + "=" + val1
						+ ",LAST_MODIFIED_DATE=SYSDATE  where MSISDN ='" + vsimregid + "'");
				query.executeUpdate();
				data = "Updated";

			} catch (Exception e) {

				data = "not Updated";
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in updateAgentPhotosStatus due to \n " + exceptionAsString);
				logger.info("Error in updateAgentPhotosStatus due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in updateAgentPhotosStatus method");
			logger.info("Could not connect to DB in updateAgentPhotosStatus method");
			logger.finest("Could not connect to DB in updateAgentPhotosStatus method");

		}

		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	// Method to get the Agent status and pin
	@RequestMapping(value = "/AgentLogin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> AgentLogin(@RequestBody MobileAgentAPI agentRegistration) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("AgentLogin", "response_AgentLogin");

		System.out.println(agentRegistration.getMsisdn());
		System.out.println(agentRegistration.getPinCode());

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				if (agentRegistration.getMsisdn() != null && agentRegistration.getPinCode() != null) {
					msisdn = agentRegistration.getMsisdn().toString();

					query = session.createNativeQuery(
							"SELECT MSISDN,PIN_CODE FROM AGENT WHERE MSISDN = '" + agentRegistration.getMsisdn() + "'"
									+ " AND PIN_CODE='" + agentRegistration.getPinCode() + "'");

					if (query.list().size() > 0) {
						query = session.createNativeQuery(
								"SELECT Status FROM AGENT WHERE MSISDN = '" + agentRegistration.getMsisdn() + "'"
										+ " AND PIN_CODE='" + agentRegistration.getPinCode() + "'");

						if (query.uniqueResult() == null) {
							data = "MSISDN or PIN CODE could be wrong, please check and try again later";
						} else if (query.uniqueResult().equals("Deactivated")) {
							data = "The MSISDN had been Deactivated.";
						} else if (query.uniqueResult().equals("In Progress")) {
							data = "The MSISDN is not Activated yet.";
						} else if (query.uniqueResult().equals("Cancelled")) {
							data = "The MSISDN had been Cancelled";
						} else {
							data = "Activated";
						}
					} else {
						data = "MSISDN or PIN CODE could be wrong, please check and try again later";
					}

				}
			} catch (Exception e) {
				data = "No Data";
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getAgentStatusPin due to \n " + exceptionAsString);
				logger.info("Error in getAgentStatusPin due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			data = "No Data";
			logger.info("Could not connect to DB in getAgentStatusPin method");
			logger.finest("Could not connect to DB in getAgentStatusPin method");

		}

		// return statuspin;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	// Method to get the statistical summary for a specific agent
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getStatisticalSummary", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getStatisticalSummary(@RequestBody ClientListViewAPI client) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getStatisticalSummary", "response_getStatisticalSummary");
		String agentNumber = client.getAgentNumber().toString();
		data = null;

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				query = session.createNativeQuery("SELECT "
						+ "nvl(sum(case when CREATED_DATE  >= SysDate-30 AND AGENT_NUMBER='" + agentNumber
						+ "' then 1 else 0 end ),0),"
						+ "nvl(sum(case when STATUS = 'Success' AND CREATED_DATE  >= SysDate-30 AND AGENT_NUMBER='"
						+ agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when STATUS = 'Failed' AND CREATED_DATE  >= SysDate-30 AND AGENT_NUMBER='"
						+ agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when  STATUS = 'Draft' AND CREATED_DATE  >= SysDate-30 AND AGENT_NUMBER='"
						+ agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when CREATED_DATE  >= SysDate-7 AND AGENT_NUMBER='" + agentNumber
						+ "' then 1 else 0 end ),0),"
						+ "nvl(sum(case when STATUS = 'Success' AND CREATED_DATE  >= SysDate-7 AND AGENT_NUMBER='"
						+ agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when STATUS = 'Failed' AND CREATED_DATE  >= SysDate-7 AND AGENT_NUMBER='"
						+ agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when  STATUS = 'Draft' AND CREATED_DATE  >= SysDate-7 AND AGENT_NUMBER='"
						+ agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when  CREATED_DATE  >= cast(trunc(current_timestamp) "
						+ "as timestamp) AND AGENT_NUMBER='" + agentNumber + "' then 1 else 0 end ),0),"
						+ "nvl(sum(case when STATUS = 'Success' AND CREATED_DATE  >= cast(trunc(current_timestamp)"
						+ "as timestamp) AND AGENT_NUMBER='" + agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when STATUS = 'Failed' AND CREATED_DATE  >= cast(trunc(current_timestamp)"
						+ "as timestamp) AND AGENT_NUMBER='" + agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when  STATUS = 'Draft' AND CREATED_DATE  >= cast(trunc(current_timestamp)"
						+ "as timestamp) AND AGENT_NUMBER='" + agentNumber + "' then 1 else 0 end),0)"
						+ " FROM CLIENTS");

				List<String> list = (List<String>) query.list();
				ObjectMapper mapper = new ObjectMapper();
				data = mapper.writeValueAsString(list);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getStatisticalSummary due to \n " + exceptionAsString);
				logger.info("Error in getStatisticalSummary due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getStatisticalSummary method");
			logger.info("Could not connect to DB in getStatisticalSummary method ");
			logger.finest("Could not connect to DB in getStatisticalSummary method ");

		}

		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	// Method to get pending pictures counter and super agent data
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPendingPicCounter", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getPendingPicCounter(
			@RequestBody SpeedCoverageTestAPI testingspeedcoverage) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getPendingPicCounter", "response_getPendingPicCounter");

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				if (testingspeedcoverage.getAgentNumber() != null
						&& !testingspeedcoverage.getAgentNumber().equalsIgnoreCase("")) {
					String agentNumber = testingspeedcoverage.getAgentNumber();

					data = session.createNativeQuery(
							"SELECT COUNT(*) from CLIENTS where (CLIENT_PHOTO_STATUS='0' or front_side_id_status='0' or  back_side_id_status='0' or signature_status='0') and AGENT_NUMBER='"
									+ agentNumber + "' ")
							.uniqueResult().toString();

					rtn.put("PendingPics", data);

					query = session.createNativeQuery(
							"SELECT SUPERAGENT,CAPTURE_SPEED,SITE_ENGINEER,RUNNING_INTERVAL,CAPTURE_COVERAGE,COVERAGE_RUNNING_INTERVAL from AGENT where MSISDN='"
									+ agentNumber + "'");
					List<Object[]> result = query.list();
					System.out.println(result.get(0)[1]);
					System.out.println(mapper.writeValueAsString(result.get(0)[2]));
					System.out.println(mapper.writeValueAsString(result.get(0)[4]));

					rtn.put("AgentData", mapper.writeValueAsString(query.list()));
					System.out.println("AgentData sent successfully");
					if (result.get(0)[1].equals('0') && result.get(0)[2].equals('0') && result.get(0)[4].equals('0')) {
						Date date = new Date();
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(date);
						int year = calendar.get(Calendar.YEAR);

						String coverageSignal = null, technology = null;

						System.out.println(testingspeedcoverage);
						if (testingspeedcoverage.getCoverageSignal() != null) {
							if (testingspeedcoverage.getCoverageSignal().equalsIgnoreCase("null")) {
								coverageSignal = "-180";
							} else {
								coverageSignal = testingspeedcoverage.getCoverageSignal();
							}

						} else {
							coverageSignal = "-180";

						}

						if (testingspeedcoverage.getTechnology() != null) {
							if (testingspeedcoverage.getTechnology().equalsIgnoreCase("null")) {
								technology = "Tech";
							} else {
								technology = testingspeedcoverage.getTechnology();
							}

						} else {
							technology = "Tech";
						}

						String speedCoverageId = null;
						synchronized (this) {
							// agentId = "AG_" + year + "_" + appConfig.getSequenceNbr(42);
							speedCoverageId = "TEST_" + year + "_" + Integer.parseInt(session
									.createNativeQuery("SELECT SPEED_COVERAGE FROM SEQ_TABLE").uniqueResult().toString());
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET SPEED_COVERAGE = SPEED_COVERAGE + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						if (session.isOpen() && session != null) {
							query = session.createNativeQuery(
									"insert into speed_coverage_test (SPEEDCOVERAGEID,SPEEDCOVERAGE_DATE,SPEEDCOVERAGE_LAT,SPEEDCOVERAGE_LNG,COVERAGE_SIGNAL,SPEED_DOWNLOAD,"
											+ "SPEED_UPLOAD,AGENT_NUMBER,AGENT_NAME,CID,LAC,TECHNOLOGY,MMC,MNC,APP_VERSION) "
											+ "VALUES ('" + speedCoverageId + "',sysdate,'"
											+ testingspeedcoverage.getSpeedCoverageLat() + "','"
											+ testingspeedcoverage.getSpeedCoverageLong() + "'," + "'" + coverageSignal
											+ "','" + testingspeedcoverage.getSpeedDown() + "','"
											+ testingspeedcoverage.getSpeedUp() + "'," + "'"
											+ testingspeedcoverage.getAgentNumber()
											+ "',(SELECT AGENT.FULL_NAME FROM AGENT WHERE MSISDN='"
											+ testingspeedcoverage.getAgentNumber() + "')," + "'"
											+ testingspeedcoverage.getcid() + "','" + testingspeedcoverage.getlac()
											+ "','" + technology + "'," + "'" + testingspeedcoverage.getMmc() + "','"
											+ testingspeedcoverage.getMnc() + "','"
											+ testingspeedcoverage.getAppVersion() + "')");

							query.executeUpdate();
							data = "Saved";
						}
						session.flush();

						String data1 = null;
						if (data.equalsIgnoreCase("Saved")) {
							data1 = testingspeedcoverage.getSpeedUp() + "," + testingspeedcoverage.getSpeedDown() + ","
									+ testingspeedcoverage.getSpeedCoverageLat() + ","
									+ testingspeedcoverage.getSpeedCoverageLong() + "," + coverageSignal;

							rtn.put("speedResult", data1);
							if (session.isOpen() && session != null) {
								query = session.createNativeQuery("update agent set capture_speed ='0' where msisdn = "
										+ "(select msisdn from agent where capture_speed='1' and msisdn ='"
										+ testingspeedcoverage.getAgentNumber() + "')");
								query.executeUpdate();
							}
						} else {
							data1 = "N/A,N/A,N/A,N/A,N/A";
							rtn.put("speedResult", data1);

						}
					}

				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getPendingPicCounter due to \n " + exceptionAsString);
				logger.info("Error in getPendingPicCounter due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getPendingPicCounter method");
			logger.info("Could not connect to DB in getPendingPicCounter method ");
			logger.finest("Could not connect to DB in getPendingPicCounter method ");

		}
		System.out.println(rtn);
		return ResponseEntity.ok().headers(responseHeaders).body(rtn);

	}

	// Method to get the agent status if activated
	@RequestMapping(value = "/getAgentStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> getAgentStatus(@RequestBody MobileAgentAPI agentRegistration) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getAgentStatus", "response_getAgentStatus");
		boolean agentExistence = false;
		msisdn = agentRegistration.getMsisdn().toString();
		String listItem;

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				if (agentRegistration.getMsisdn() != null) {
					msisdn = agentRegistration.getMsisdn().toString();
					listItem = session.createNativeQuery("select STATUS from Agent where MSISDN='" + msisdn + "'")
							.uniqueResult().toString();

					if (listItem.equalsIgnoreCase("Activated")) {
						agentExistence = true;
					} else {
						agentExistence = false;
					}
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getAgentStatus due to \n " + exceptionAsString);
				logger.info("Error in getAgentStatus due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in agentAgentStatus method");
			logger.info("Could not connect to DB in agentAgentStatus method ");
			logger.finest("Could not connect to DB in agentAgentStatus method ");

		}

		// return agentExistence;
		return ResponseEntity.ok().headers(responseHeaders).body(agentExistence);

	}

	// Method to get the sim registration list view
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPendingPictures", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getPendingPictures(@RequestBody ClientListViewAPI client) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getPendingPictures", "response_getPendingPictures");
		Map<String, Object> rtn = new LinkedHashMap<>();

		List<ClientListViewAPI> clientAPI = new ArrayList<ClientListViewAPI>();

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String agentNumber = client.getAgentNumber();
				int vfrom = client.getVfrom();
				int vto = client.getVto();

				clientAPI = session.createNativeQuery(
						"SELECT * FROM (select ROW_NUMBER() OVER (ORDER BY CREATED_DATE DESC) row_num,CLIENT_ID,AGENT_NUMBER,MOBILE_NUMBER,FRONT_SIDE_ID_STATUS, BACK_SIDE_ID_STATUS,SIGNATURE_STATUS,CLIENT_PHOTO_STATUS"
								+ " from CLIENTS where ( FRONT_SIDE_ID_STATUS='0' or BACK_SIDE_ID_STATUS='0' or SIGNATURE_STATUS='0' or CLIENT_PHOTO_STATUS='0')"
								+ " AND AGENT_NUMBER='" + agentNumber + "'  ) T WHERE  row_num >='" + vfrom
								+ "' AND row_num <='" + vto + "'")
						.list();

				rtn.put("PendingPicturesLV", clientAPI);

				session.flush();
				if (clientAPI.size() > 0) {

					data = session.createNativeQuery(
							"SELECT COUNT(*) FROM CLIENTS where ( FRONT_SIDE_ID_STATUS='0' or BACK_SIDE_ID_STATUS='0' or SIGNATURE_STATUS='0' or CLIENT_PHOTO_STATUS='0')AND AGENT_NUMBER='"
									+ agentNumber + "' ")
							.uniqueResult().toString();

					rtn.put("PendingPicturesPagination", data);

				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getPendingPictures due to \n " + exceptionAsString);
				logger.info("Error in getPendingPictures due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getPendingPictures method");
			logger.info("Could not connect to DB in getPendingPictures method ");
			logger.finest("Could not connect to DB in getPendingPictures method ");
		}
		// return clientAPI;
		return ResponseEntity.ok().headers(responseHeaders).body(rtn);
	}

	// Method to get the picture of a specific client to be resent
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPicture", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<ClientListViewAPI>> getPicture(@RequestBody ClientListViewAPI client) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getPicture", "response_getPicture");
		List<ClientListViewAPI> clientAPI = new ArrayList<ClientListViewAPI>();

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				clientAPI = session.createNativeQuery(
						"select MOBILE_NUMBER,ID_FRONT_SIDE_PHOTO,ID_BACK_SIDE_PHOTO,SIGNATURE,SIGNATURE_STATUS,BACK_SIDE_ID_STATUS,FRONT_SIDE_ID_STATUS,CLIENT_PHOTO,CLIENT_PHOTO_STATUS FROM CLIENTS where CLIENT_ID = '"
								+ client.getClientid() + "'")
						.list();
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getPicture due to \n " + exceptionAsString);
				logger.info("Error in getPicture due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getPicture method");
			logger.info("Could not connect to DB in getPicture method ");
			logger.finest("Could not connect to DB in getPicture method ");
		}

		// return clientAPI;
		return ResponseEntity.ok().headers(responseHeaders).body(clientAPI);

	}

	// Method to update the client photos statuses from pending
	@RequestMapping(value = "/updateClientPhotosStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> updateClientPhotosStatus(@RequestBody ClientListViewAPI client) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("updateClientPhotosStatus", "response_updateClientPhotosStatus");
		data = null;

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String vcolname = client.getVcolname().toString();
				String vsimregid = client.getVsimregid().toString();
				query = session.createNativeQuery("update CLIENTS set " + vcolname
						+ "='1', LAST_MODIFIED_DATE=SYSDATE  where CLIENT_ID ='" + vsimregid + "'");
				query.executeUpdate();
				data = "updated";
			} catch (Exception e) {
				data = "not update";
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in updateClientPhotosStatus due to \n " + exceptionAsString);
				logger.info("Error in updateClientPhotosStatus due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("could not connect to DB in updateClientPhotosStatus method");
			logger.info("could not connect to DB in updateClientPhotosStatus method ");
			logger.finest("could not connect to DB in updateClientPhotosStatus method ");
		}

		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	// Method to get the sim registration list view
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSimData", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getSimData(@RequestBody ClientListViewAPI client) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getSimData", "response_getSimData");
		Map<String, Object> rtn = new LinkedHashMap<>();

		List<ClientListViewAPI> clientAPI = new ArrayList<ClientListViewAPI>();

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String agentNumber = client.getAgentNumber();
				String date = client.getDate();
				int vfrom = client.getVfrom();
				int vto = client.getVto();

				clientAPI = session.createNativeQuery(
						"SELECT row_num,AGENT_NUMBER,CLIENT_ID,FIRST_NAME,LAST_NAME,MOBILE_NUMBER,STATUS,nvl(REGISTRATION_STATUS,'0') FROM (select ROW_NUMBER() OVER (ORDER BY CREATED_DATE DESC) row_num,AGENT_NUMBER,CLIENT_ID,FIRST_NAME,LAST_NAME,MOBILE_NUMBER,STATUS,REGISTRATION_STATUS,CREATED_DATE"
								+ " from CLIENTS where AGENT_NUMBER = '" + agentNumber
								+ "' and  TO_DATE(TO_CHAR(CREATED_DATE,'DD-MM-YYYY'),'DD-MM-YYYY') =TO_DATE('" + date
								+ "','DD-MM-YYYY')) where  row_num >= " + vfrom + " AND row_num <=" + vto + " ")
						.list();

				rtn.put("SimListView", clientAPI);

				if (clientAPI.size() > 0) {
					rtn.put("SimListView", clientAPI);
					data = session.createNativeQuery(
							"SELECT COUNT(*) FROM CLIENTS where TO_DATE(TO_CHAR(CREATED_DATE,'DD-MM-YYYY'),'DD-MM-YYYY') =TO_DATE('"
									+ date + "','DD-MM-YYYY') AND AGENT_NUMBER='" + agentNumber + "'")
							.uniqueResult().toString();

					rtn.put("SimListViewPagination", data);
				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getSimData due to \n " + exceptionAsString);
				logger.info("Error in getSimData due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getSimData method");
			logger.info("Could not connect to DB in getSimData method ");
			logger.finest("Could not connect to DB in getSimData method ");
		}

		// return clientAPI;
		return ResponseEntity.ok().headers(responseHeaders).body(rtn);

	}

	// Method to get a client and fill in the sim registration form
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getClientData", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<ClientListViewAPI>> getClientData(@RequestBody ClientListViewAPI client) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getClientData", "response_getClientData");
		List<ClientListViewAPI> clientAPI = new ArrayList<ClientListViewAPI>();

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				clientAPI = session.createNativeQuery(
						"select FIRST_NAME,nvl(MIDDLE_NAME,'-'),LAST_NAME,STATUS,MOBILE_NUMBER,TO_CHAR(DATE_OF_BIRTH,'DD-MM-YYYY'),NATIONALITY,nvl(ALTERNATIVE_NUMBER,'-'),EMAIL_ADDRESS,PHYSICAL_LOCATION,POSTAL_ADDRESS,GENDER,CLIENT_ID_NUMBER,SIGNATURE,ID_FRONT_SIDE_PHOTO,ID_BACK_SIDE_PHOTO,SIGNATURE_STATUS,FRONT_SIDE_ID_STATUS,BACK_SIDE_ID_STATUS,nvl(CLIENT_PHOTO,'No Image'),CLIENT_PHOTO_STATUS,USSD_STATUS,LONGITUDE,LATITUDE,SELLING_LONGITUDE,SELLING_LATITUDE,TO_CHAR(CREATED_DATE,'DD-MM-YYYY'),nvl(REGISTRATION_STATUS,'0'),nvl(TKASH_REGISTRATION_STATUS,'0'),ID_TYPE,nvl(PASSPORT_NUMBER,'0'),nvl(REGION_ID,'0'),nvl(REGION_NAME,'None'),nvl(AREA_ID,'0'),nvl(AREA_NAME,'None')  FROM CLIENTS where CLIENT_ID = '"
								+ client.getClientid() + "'")
						.list();
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getClientData due to \n " + exceptionAsString);
				logger.info("Error in getClientData due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getClientData method");
			logger.info("Could not connect to DB in getClientData method ");
			logger.finest("Could not connect to DB in getClientData method ");
		}

		// return clientAPI;
		return ResponseEntity.ok().headers(responseHeaders).body(clientAPI);

	}

	// Method to delete the client
	@RequestMapping(value = "/deleteClient", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> deleteClient(@RequestBody ClientListViewAPI client) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("deleteClient", "response_deleteClient");
		data = null;

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String clientid = client.getClientid();
				query = session.createNativeQuery("Delete CLIENTS where CLIENT_ID='" + clientid + "'");
				query.executeUpdate();

				data = "deleted";
			} catch (Exception e) {
				data = "not deleted";
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in deleteClient due to \n " + exceptionAsString);
				logger.info("Error in deleteClient due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in deleteClient method");
			logger.info("Could not connect to DB in deleteClient method ");
			logger.finest("Could not connect to DB in deleteClient method ");
		}

		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);
	}

	// Method to save the client in the CLIENT table
	@RequestMapping(value = "/SavingorUpdatingClient", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> SavingorUpdatingClient(@RequestBody MobileClientAPI client) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("SavingorUpdatingClient", "response_SavingorUpdatingClient");

		data = "DB not reachable";
		Timestamp CreationDate, LastModified;
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String clientid = client.getClientid();
				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);

				if (clientid.equalsIgnoreCase("0")) {

					// clientid = "CLT_" + year + "_" + appConfig.getSequenceNbr(34);
					synchronized (this) {
						// agentId = "AG_" + year + "_" + appConfig.getSequenceNbr(42);
						clientid = "CLT_" + year + "_" + Integer.parseInt(
								session.createNativeQuery("SELECT CLIENTS FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET CLIENTS = CLIENTS + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
					}
					CreationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
				} else {
					if (client.getCreatedDate() == null) {
						CreationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
					} else {
						CreationDate = client.getCreatedDate();
					}
				}

				LastModified = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
				MobileClientAPI clienttable = new MobileClientAPI();
				clienttable.setAgentNumber(client.getAgentNumber());
				clienttable.setClientid(clientid);
				clienttable.setDisplayName("0");
				clienttable.setFirstName(client.getFirstName());
				clienttable.setLastName(client.getLastName());
				clienttable.setMobileNumber(client.getMobileNumber());
				clienttable.setDepartment("0");
				clienttable.setPhysicalLocation(client.getPhysicalLocation());
				clienttable.setCreatedDate(CreationDate);
				clienttable.setLastModified(LastModified);
				clienttable.setDescription("0");
				clienttable.setStatus(client.getStatus());
				clienttable.setGender(client.getGender());
				clienttable.setEmailAddress(client.getEmailAddress());
				clienttable.setPostalAddress(client.getPostalAddress());
				clienttable.setNationality(client.getNationality());
				clienttable.setMiddleName(client.getMiddleName());
				clienttable.setDateofBirth(client.getDateofBirth());
				clienttable.setAlternativeNumber(client.getAlternativeNumber());
				clienttable.setSignature(client.getSignature());
				clienttable.setClientIDNumber(client.getClientIDNumber());
				clienttable.setFrontID(client.getFrontID());
				clienttable.setBackID(client.getBackID());
				clienttable.setSignatureStatus(client.getSignatureStatus());
				clienttable.setFrontIDStatus(client.getFrontIDStatus());
				clienttable.setBackIDStatus(client.getBackIDStatus());
				clienttable.setUssdStatus(client.getUssdStatus());
				clienttable.setClientPhoto(client.getClientPhoto());
				clienttable.setClientStatus(client.getClientStatus());
				clienttable.setLongitude(client.getLongitude());
				clienttable.setLatitude(client.getLatitude());
				clienttable.setSellingLongitude(client.getSellingLongitude());
				clienttable.setSellingLatitude(client.getSellingLatitude());
				clienttable.setIdType(client.getIdType());
				clienttable.setPassNumber(client.getPassNumber());
				clienttable.setRegionID(session
						.createNativeQuery(
								"SELECT REGION_ID FROM REGION WHERE REGION_NAME ='" + client.getRegionName() + "'")
						.uniqueResult().toString());
				clienttable.setRegionName(client.getRegionName());
				clienttable.setAreaID(session
						.createNativeQuery("SELECT AREA_ID FROM AREA WHERE AREA_NAME ='" + client.getAreaName() + "'")
						.uniqueResult().toString());
				clienttable.setAreaName(client.getAreaName());
				clienttable.setCid(client.getCid());
				clienttable.setLac(client.getLac());

				if (session.isOpen() && session != null) {
					session.saveOrUpdate(clienttable);
					data = clientid;
				}

			} catch (Exception e) {
				data = "not saved";
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in savingClient due to \n " + exceptionAsString);
				logger.info("Error in savingClient due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			data = "DB not reachable";
			System.out.println("Could not connect to DB in savingClient method");
			logger.info("Could not connect to DB in savingClient method ");
			logger.finest("Could not connect to DB in savingClient method ");
		}

		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);
	}

	// Update the client status in the sim registration form
	@RequestMapping(value = "/UpdateSimRegistrationPicStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> UpdateSimRegistrationPicStatus(@RequestBody ClientListViewAPI client) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("UpdateSimRegistrationPicStatus", "response_UpdateSimRegistrationPicStatus");

		data = null;
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String vcolname = client.getVcolname();
				String vsimregid = client.getVsimregid();
				int val1 = client.getVal1();
				String vcolname1 = client.getVcolname1();
				String val2 = client.getVal2();
				if (vcolname1 != null) {
					query = session.createNativeQuery(
							"update CLIENTS set " + vcolname + "=" + val1 + ",LAST_MODIFIED_DATE=SYSDATE," + vcolname1
									+ "='" + val2 + "'  where CLIENT_ID ='" + vsimregid + "'");
					query.executeUpdate();
					data = "Updated";
				}

			} catch (Exception e) {
				data = "not Updated";
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in UpdateSimRegistrationPicStatus due to \n " + exceptionAsString);
				logger.info("Error in UpdateSimRegistrationPicStatus due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in UpdateSimRegistrationPicStatus method");
			logger.info("Could not connect to DB in UpdateSimRegistrationPicStatus method ");
			logger.finest("Could not connect to DB in UpdateSimRegistrationPicStatus method ");
		}

		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	// Method to delete the client photos statuses
	@RequestMapping(value = "/DeleteSimRegistrationPicStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> DeleteSimRegistrationPicStatus(@RequestBody ClientListViewAPI client) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("DeleteSimRegistrationPicStatus", "response_DeleteSimRegistrationPicStatus");
		data = null;

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String vcolname = client.getVcolname();
				String vsimregid = client.getVsimregid();
				query = session.createNativeQuery("update CLIENTS set " + vcolname
						+ "=-1,LAST_MODIFIED_DATE=SYSDATE   where CLIENT_ID ='" + vsimregid + "'");
				query.executeUpdate();
				data = "updated";
			} catch (Exception e) {
				data = "not update";
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in DeleteSimRegistrationPicStatus due to \n " + exceptionAsString);
				logger.info("Error in DeleteSimRegistrationPicStatus due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in DeleteSimRegistrationPicStatus method");
			logger.info("Could not connect to DB in DeleteSimRegistrationPicStatus method ");
			logger.finest("Could not connect to DB in DeleteSimRegistrationPicStatus method ");
		}

		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	// Method to update the client ussd statuses
	@RequestMapping(value = "/UpdateUSSDStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> UpdateUSSDStatus(@RequestBody MobileClientAPI client) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("UpdateUSSDStatus", "response_UpdateUSSDStatus");
		data = null;

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String clientid = client.getClientid();
				String ussdStatus = client.getUssdStatus();
				query = session.createNativeQuery(
						"UPDATE CLIENTS SET LAST_MODIFIED_DATE=SYSDATE,STATUS='SENT_USSD', USSD_STATUS='"
								+ ussdStatus.toString() + "' WHERE CLIENT_ID ='" + clientid + "'");
				query.executeUpdate();
				data = "updated";
			} catch (Exception e) {
				data = "not update";
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in UpdateUSSDStatus due to \n " + exceptionAsString);
				logger.info("Error in UpdateUSSDStatus due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in UpdateUSSDStatus method");
			logger.info("Could not connect to DB in UpdateUSSDStatus method ");
			logger.finest("Could not connect to DB in UpdateUSSDStatus method ");
		}

		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	// Method to update the client ussd statuses
	@RequestMapping(value = "/UpdateClientStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> UpdateClientStatus(@RequestBody String clienttest) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("UpdateClientStatus", "response_UpdateClientStatus");
		data = "Not Updated";
		String clientid = null, response_code = null, response_message = null, status = null;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				String clientest1 = clienttest.substring(2, Integer.parseInt(String.valueOf(clienttest.length())));

				StringBuffer sb = new StringBuffer(clientest1);
				sb.deleteCharAt(sb.length() - 1);
				sb.deleteCharAt(sb.length() - 1);

				clientest1 = sb.toString();

				String[] result = clientest1.split(",");
				for (int i = 0; i < result.length; i++) {
					clientid = result[0];
					response_code = result[1];
					response_message = result[2];
					status = result[3];
				}

				StringBuffer sb1 = new StringBuffer(clientid);
				sb1.deleteCharAt(sb1.length() - 1);
				clientid = sb1.toString();
				String[] clientid1 = clientid.split("\":\"");
				for (int i = 0; i < clientid1.length; i++) {
					clientid = clientid1[1];
				}

				StringBuffer sb2 = new StringBuffer(response_code);
				sb2.deleteCharAt(sb2.length() - 1);
				response_code = sb2.toString();
				String[] response_code1 = response_code.split("\":\"");
				for (int i = 0; i < response_code1.length; i++) {
					response_code = response_code1[1];
				}

				StringBuffer sb3 = new StringBuffer(response_message);
				sb3.deleteCharAt(sb3.length() - 1);
				response_message = sb3.toString();
				String[] response_message1 = response_message.split("\":\"");
				for (int i = 0; i < response_message1.length; i++) {
					response_message = response_message1[1];
				}

				String[] status1 = status.split("\":\"");
				for (int i = 0; i < status1.length; i++) {
					status = status1[1];
				}

				query = session.createNativeQuery(
						"UPDATE CLIENTS" + " SET " + "LAST_MODIFIED_DATE=SYSDATE, RESPONSE_CODE='" + response_code
								+ "'," + "RESPONSE_MESSAGE='" + response_message + "'," + "REGISTRATION_STATUS='"
								+ status + "'," + "STATUS='" + status + "'" + "WHERE CLIENT_ID='" + clientid + "'");
				query.executeUpdate();
				data = "UPDATED";
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in UpdateClientStatus due to \n " + exceptionAsString);
				logger.info("Error in UpdateClientStatus due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in UpdateClientStatus method");
			logger.info("Could not connect to DB in UpdateClientStatus method ");
			logger.finest("Could not connect to DB in UpdateClientStatus method ");
		}

		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);
	}

	// method to get agent profile
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAgent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<RegionName>> getAgent(@RequestBody MobileAgentAPI agent) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Baeldung-Example-Header", "response");
		List<RegionName> agentAPI = new ArrayList<RegionName>();

		String agentNumber = null;
		if (agent.getMsisdn() != null) {
			agentNumber = agent.getMsisdn();
		}

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				if (agentNumber != null) {
					agentAPI = session.createNativeQuery(
							"select FIRST_NAME,LAST_NAME,DISPLAY_NAME,ADDRESS,EMAIL,STATUS,nvl(REGION_NAME,'None'),nvl(AGENT_IMAGE,'0'),nvl(AGENT_FRONT_ID,'0'),nvl(AGENT_BACK_ID,'0'),nvl(AGENT_IMAGE_STATUS,0),nvl(FRONT_SIDE_ID_STATUS,0),nvl(BACK_SIDE_ID_STATUS,0),LONGITUDE,LATITUDE,FULL_NAME,AGENT_ID,REGION_ID FROM AGENT where MSISDN='"
									+ agentNumber + "' AND PIN_CODE='" + agent.getPinCode() + "'")
							.list();
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getAgent due to \n " + exceptionAsString);
				logger.info("Error in getAgent due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getAgent method");
			logger.info("Could not connect to DB in getAgent method ");
			logger.finest("Could not connect to DB in getAgent method ");
		}

		// return agentAPI;
		return ResponseEntity.ok().headers(responseHeaders).body(agentAPI);
	}

	// Method to update the client ussd statuses
	@RequestMapping(value = "/UpdateAgent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> UpdateAgent(@RequestBody MobileAgentAPI agentRegistration) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("UpdateAgent", "response_UpdateAgent");
		data = null;

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createNativeQuery("update AGENT set LAST_MODIFIED_DATE=SYSDATE ,FIRST_NAME='"
						+ agentRegistration.getFirstName() + "',DISPLAY_NAME='" + agentRegistration.getDisplayName()
						+ "',LAST_NAME='" + agentRegistration.getLastName() + "',FULL_NAME='"
						+ agentRegistration.getFirstName() + " " + agentRegistration.getLastName() + "',ADDRESS='"
						+ agentRegistration.getAddress() + "',EMAIL='" + agentRegistration.getEmail()
						+ "',REGION_NAME='" + agentRegistration.getRegionName() + "',REGION_ID='"
						+ agentRegistration.getRegionID() + "',AGENT_FRONT_ID='" + agentRegistration.getAgentfrontID()
						+ "',AGENT_BACK_ID='" + agentRegistration.getAgentbackID() + "',LONGITUDE='"
						+ agentRegistration.getLongitude() + "',LATITUDE='" + agentRegistration.getLatitude()
						+ "' Where MSISDN='" + agentRegistration.getMsisdn() + "' and PIN_CODE='"
						+ agentRegistration.getPinCode() + "'");
				query.executeUpdate();
				data = "UPDATED";

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in UpdateAgent due to \n " + exceptionAsString);
				logger.info("Error in UpdateAgent due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in UpdateAgent method");
			logger.info("Could not connect to DB in UpdateAgent method ");
			logger.finest("Could not connect to DB in UpdateAgent method ");
		}

		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);
	}

	// /////
	@SuppressWarnings("unused")
	@RequestMapping(value = "/SimRegistrationAPIWeb", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> SimRegistrationAPIWeb(@RequestBody SimRegistrationAPI sim) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("SimRegistrationAPIWeb", "response_SimRegistrationAPIWeb");
		boolean canRegister = false;
		String count = null;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				query = session
						.createNativeQuery("Select COUNT(*) FROM CLIENTS WHERE CLIENT_ID_NUMBER = '" + sim.getIdNumber()
								+ "' " + " AND CREATED_DATE  >= SysDate-7 AND REGISTRATION_STATUS ='Success'");

				System.out.println(query.uniqueResult());
				count = query.uniqueResult().toString();
				if (Integer.parseInt(count) >= 3) {
					data = "GA not submitted. Limit of SIMREG registrations for this ID reached. Advise customer to visit Telkom shop.";
				} else {
					canRegister = true;
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in SimRegistrationAPIWeb due to \n " + exceptionAsString);
				logger.info("Error in SimRegistrationAPIWeb due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}

		}
		if (canRegister == true) {
			String api_response_code = null, response_message = null, registration_status, clientpkid = "0", fname,
					mname, lname, msisdn = "0", idType, idNumber, dob, gender, email, altnumber, address1, state,
					agentmsisdn = "0", getid = "0";
			int flag = 0;

			// 776761539 msisdn
			Socket socket = null;
			HttpURLConnection httpConnection = null;
			OutputStream out = null;
			BufferedWriter writer = null;
			BufferedReader rd = null;
			HttpURLConnection urlConnection = null;
			Boolean connectionFailed = false;
			try {

				clientpkid = sim.getClientpkid();
				msisdn = sim.getMsisdn();
				fname = sim.getFname();
				mname = sim.getMname();
				lname = sim.getLname();
				idType = sim.getIdType();
				idNumber = sim.getIdNumber();
				dob = sim.getDob();
				email = sim.getEmail();
				altnumber = sim.getAltnumber();
				address1 = sim.getAddress1();
				state = sim.getState();
				agentmsisdn = sim.getAgentmsisdn();
				gender = sim.getGender();
				getid = getrequestid();

				JsonObject postData = new JsonObject();
				postData.addProperty("requestId", getid);
				postData.addProperty("serviceId", "SIMREG");
				postData.addProperty("clientId", "1");
				postData.addProperty("msisdn", msisdn);
				postData.addProperty("firstName", fname);
				postData.addProperty("middleName", mname);
				postData.addProperty("lastName", lname);
				postData.addProperty("idType", idType);
				postData.addProperty("idNumber", idNumber);
				postData.addProperty("dateOfBirth", dob);
				postData.addProperty("minorFlag", "NO");
				postData.addProperty("gender", gender);
				postData.addProperty("emailId", email);
				postData.addProperty("alternateNumber", altnumber);
				postData.addProperty("address_1", address1);
				postData.addProperty("address_2", "");
				postData.addProperty("address_3", "");
				postData.addProperty("cityName", "city test");
				postData.addProperty("stateName", state);
				postData.addProperty("country", "KENYA");
				postData.addProperty("userId", "APPUSER");
				postData.addProperty("clientPassword", "iPacsUssd@123");
				postData.addProperty("agentMsisdn", agentmsisdn);

				URL url = new URL("http://10.22.25.10:8995/ipacs/ussd/api/");

				boolean portAvailable = false;

				try {
					socket = new Socket("10.22.25.10", 8995);
					portAvailable = true;
					// socket.close();
				} catch (IOException e) {
					logger.finest("port is closed.");
					logger.info("port is closed.");
				}

				if (portAvailable == true) {
					urlConnection = (HttpURLConnection) url.openConnection();
					urlConnection.setRequestProperty("Content-Type", "application/json");
					urlConnection.setRequestMethod("POST");
					urlConnection.setDoOutput(true);
					urlConnection.setDoInput(true);
					urlConnection.setChunkedStreamingMode(0);
					/// validate if we have access to URL
					httpConnection = (HttpURLConnection) url.openConnection();
					httpConnection.setConnectTimeout(5000);
					int responseCode = httpConnection.getResponseCode();
					logger.finest("ResponseCode in SimRegistrationAPIWeb is " + responseCode);
					logger.info("ResponseCode in SimRegistrationAPIWeb due is " + responseCode);

					String responseMessage = httpConnection.getResponseMessage();
					logger.finest("ResponseMessage in SimRegistrationAPIWeb is " + responseMessage);
					logger.info("ResponseMessage in SimRegistrationAPIWeb due is " + responseMessage);
					if (responseCode == 500) {

						return ResponseEntity.ok().headers(responseHeaders)
								.body("Network issue, please contact your support.");
					} else {

						if (responseCode != 200) {
							urlConnection.connect();
							connectionFailed = true;
							out = new BufferedOutputStream(urlConnection.getOutputStream());
							writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
							writer.write(postData.toString());
							writer.flush();
							flag = 1;

							int code = urlConnection.getResponseCode();
							rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
							String line = null;
							while ((line = rd.readLine()) != null) {
								if (line.contains("responseCode")) {

									int m = 0;
									m = line.indexOf(";");
									String response_code = line.substring(m + 1, line.length());
									String[] test1 = response_code.split("[:,]");

									String[] splitterString = test1[1].split("\"");

									for (String s : splitterString) {

										api_response_code = s;

									}
								}

								if (line.contains("message")) {

									int n = 0;
									n = line.indexOf(";");
									String message = line.substring(n + 1, line.length());
									String[] test1 = message.split("[:,]");
									String[] splitterString = test1[1].split("\"");
									for (String s : splitterString) {
										response_message = s;

									}

								}

							}

							if (rd != null) {
								rd.close();
							}
							if (writer != null) {
								writer.close();
							}
							if (out != null) {
								writer.close();
							}
							if (socket != null) {
								socket.close();
							}

						}
					}
				} else {
					data = "Service is not available.";
					return ResponseEntity.ok().headers(responseHeaders).body(data);
				}

				/////////////////////////////////////////////

			} catch (Exception e) {
				e.printStackTrace();
				flag = 0;
				connectionFailed = false;
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in SimRegistrationAPIWeb due to \n " + exceptionAsString);
				logger.info("Error in SimRegistrationAPIWeb due to \n " + exceptionAsString);
			} finally {
				if (urlConnection != null) {
					urlConnection.disconnect();
				} else {
					data = "0";
				}

				if (httpConnection != null) {
					httpConnection.disconnect();
				}
			}

			if (connectionFailed == true) {
				if (response_message.contains("Success") || response_message.contains("success")
						|| response_message.contains("SUCCESS")) {
					registration_status = "Success";
				} else {
					registration_status = "Failed";
				}

				// to call update in database (SIM status

				// thread1.start();

				data = api_response_code + "!!" + response_message + "!!" + registration_status;

				// update in DB based on registration status
				UpdateClientStatusfromALM(getid, clientpkid, registration_status, api_response_code, response_message,
						agentmsisdn, msisdn);

			}
		}

		System.out.println(data);
		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	public static String getrequestid() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd;HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String res = dtf.format(now).toString();

		String[] data1 = res.split(";");
		String val1 = data1[0];
		String val2 = data1[1];

		String[] data2 = val1.split("-");
		String valyear = data2[0];
		valyear = valyear.substring(2, 4);
		String valmonth = data2[1];
		String valday = data2[2];

		String[] data3 = val2.split(":");
		String valtime = data3[0] + data3[1] + data3[2];

		return (valyear + valmonth + valday + valtime);
	}

	// Method to get the sim registration report list view
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSimRegReport", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<SimRegReportAPI>> getSimRegReport(@RequestBody SimRegReportAPI simreport) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getSimRegReport", "response_getSimRegReport");
		List<SimRegReportAPI> simReport = new ArrayList<SimRegReportAPI>();

		String todate, fromdate, status, msisdn, fname, lname, agentnumber, idNumber;

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				fromdate = simreport.getFromdate();
				todate = simreport.getTodate();
				status = simreport.getStatus();
				msisdn = simreport.getMsisdn();
				agentnumber = simreport.getAgentnumber();
				fname = simreport.getFname();
				lname = simreport.getLname();
				idNumber = simreport.getIdNumber();

				String str = "select TO_CHAR(a.REGISTRATIONDATE,'DD-MM-YYYY'),a.response_code,b.FIRST_NAME || ' ' || b.LAST_NAME,a.SIM_REG_STATUS,a.MOBILE_NUMBER,b.CLIENT_ID_NUMBER,a.RESPONSE_MESSAGE"
						+ " from simreg_log a inner join clients b on a.client_id = b.client_id where a.AGENT_NUMBER='"
						+ agentnumber + "'";

				if (fromdate != null && !fromdate.equalsIgnoreCase("")) {
					str = str + " AND a.REGISTRATIONDATE between TO_DATE('" + fromdate + "','DD-MM-YYYY') and TO_DATE('"
							+ todate + "','DD-MM-YYYY')";
				}

				if (status != null && (status.equalsIgnoreCase("Success") || status.equalsIgnoreCase("Failed"))) {
					str = str + " AND a.SIM_REG_STATUS='" + status + "'";
				}

				if (msisdn != null && !msisdn.equalsIgnoreCase("")) {
					str = str + " AND a.MOBILE_NUMBER LIKE '%" + msisdn + "%'";
				}

				if (fname != null && !fname.equalsIgnoreCase("")) {
					str = str + " AND upper(b.FIRST_NAME) LIKE upper('%" + fname + "%')";
				}

				if (lname != null && !lname.equalsIgnoreCase("")) {
					str = str + " AND upper(b.LAST_NAME) LIKE upper('%" + fname + "%')";
				}

				if (idNumber != null && !idNumber.equalsIgnoreCase("")) {
					str = str + " AND b.CLIENT_ID_NUMBER LIKE '%" + idNumber + "%'";
				}

				str = str + " order by a.REGISTRATIONDATE DESC";

				System.out.println(str);
				simReport = session.createNativeQuery(str).list();

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getSimRegReport due to \n " + exceptionAsString);
				logger.info("Error in getSimRegReport due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getSimRegReport method");
			logger.info("Could not connect to DB in getSimRegReport method ");
			logger.finest("Could not connect to DB in getSimRegReport method ");
		}

		// return simReport;
		return ResponseEntity.ok().headers(responseHeaders).body(simReport);

	}

	// Method to get the sim registration report list view
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTkashRegReport", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<SimRegReportAPI>> getTkashRegReport(@RequestBody SimRegReportAPI simreport) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getSimRegReport", "response_getSimRegReport");
		List<SimRegReportAPI> simReport = new ArrayList<SimRegReportAPI>();

		String todate, fromdate, status, msisdn, fname, lname, agentnumber, idNumber;

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				fromdate = simreport.getFromdate();
				todate = simreport.getTodate();
				status = simreport.getStatus();
				msisdn = simreport.getMsisdn();
				agentnumber = simreport.getAgentnumber();
				fname = simreport.getFname();
				lname = simreport.getLname();
				idNumber = simreport.getIdNumber();

				String str = "select TO_CHAR(a.REGISTRATIONDATE,'DD-MM-YYYY'),a.response_code,b.FIRST_NAME || ' ' || b.LAST_NAME,a.SIM_REG_STATUS,a.MOBILE_NUMBER,b.CLIENT_ID_NUMBER,a.RESPONSE_MESSAGE"
						+ " from tkashreg_log a inner join clients b on a.client_id = b.client_id where a.AGENT_NUMBER='"
						+ agentnumber + "'";

				if (fromdate != null && !fromdate.equalsIgnoreCase("")) {
					str = str + " AND a.REGISTRATIONDATE between TO_DATE('" + fromdate + "','DD-MM-YYYY') and TO_DATE('"
							+ todate + "','DD-MM-YYYY')";
				}

				if (status != null && (status.equalsIgnoreCase("Success") || status.equalsIgnoreCase("Failed"))) {
					str = str + " AND a.SIM_REG_STATUS='" + status + "'";
				}

				if (msisdn != null && !msisdn.equalsIgnoreCase("")) {
					str = str + " AND a.MOBILE_NUMBER LIKE '%" + msisdn + "%'";
				}

				if (fname != null && !fname.equalsIgnoreCase("")) {
					str = str + " AND upper(b.FIRST_NAME) LIKE upper('%" + fname + "%')";
				}

				if (lname != null && !lname.equalsIgnoreCase("")) {
					str = str + " AND upper(b.LAST_NAME) LIKE upper('%" + fname + "%')";
				}

				if (idNumber != null && !idNumber.equalsIgnoreCase("")) {
					str = str + " AND b.CLIENT_ID_NUMBER LIKE '%" + idNumber + "%'";
				}

				str = str + " order by a.REGISTRATIONDATE DESC";
				simReport = session.createNativeQuery(str).list();

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getSimRegReport due to \n " + exceptionAsString);
				logger.info("Error in getSimRegReport due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getSimRegReport method");
			logger.info("Could not connect to DB in getSimRegReport method ");
			logger.finest("Could not connect to DB in getSimRegReport method ");
		}

		// return simReport;
		return ResponseEntity.ok().headers(responseHeaders).body(simReport);

	}

	// Method to get the sim registration report list view

	@SuppressWarnings("unused")
	@RequestMapping(value = "/ALMSMSCRUN", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> ALMSMSCRUN(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("ALMSMSCRUN", "response_ALMSMSCRUN");

		String pin, msisdn, message, resultsms = "Failed";
		pin = request.getParameter("PIN");
		msisdn = "254" + request.getParameter("MSISDN");
		message = "Welcome to our network , your PIN is " + pin;

		TimeFormatter timeFormatter = new AbsoluteTimeFormatter();
		BasicConfigurator.configure();
		String host = "10.21.7.180";
		int port = 5019;
		SMPPSession session = new SMPPSession();
		try {
			session.connectAndBind(host, port, new BindParameter(BindType.BIND_TRX, "Katelecom", "123456", null,
					TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));

			/*
			 * String messageId = session.submitShortMessage("CMT",
			 * TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, "25470077",
			 * TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, "254770962641",
			 * new ESMClass(), (byte) 0, (byte) 1, timeFormatter .format(new Date()), null,
			 * new RegisteredDelivery( SMSCDeliveryReceipt.DEFAULT), (byte) 0, new
			 * GeneralDataCoding( Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false),
			 * (byte) 0, "test from Bassam".getBytes());
			 */

			String messageId = session.submitShortMessage("CMT", TypeOfNumber.INTERNATIONAL,
					NumberingPlanIndicator.UNKNOWN, "25470077", TypeOfNumber.INTERNATIONAL,
					NumberingPlanIndicator.UNKNOWN, msisdn, new ESMClass(), (byte) 0, (byte) 1,
					timeFormatter.format(new Date()), null, new RegisteredDelivery(SMSCDeliveryReceipt.FAILURE),
					(byte) 0, new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte) 0,
					message.getBytes());
			resultsms = "Success";
		} catch (PDUException e) {
			// Invalid PDU parameter
			System.err.println("Invalid PDU parameter");
			e.printStackTrace();

		} catch (ResponseTimeoutException e) {
			// Response timeout
			System.err.println("Response timeout");
			e.printStackTrace();

		} catch (InvalidResponseException e) {
			// Invalid response
			System.err.println("Receive invalid response");
			e.printStackTrace();

		} catch (NegativeResponseException e) {
			// Receiving negative response (non-zero command_status)
			System.err.println("Receive negative response");
			e.printStackTrace();

		} catch (IOException e) {
			System.err.println("IO error occur");
			System.err.println("Failed connect and bind to host");
			e.printStackTrace();

		}

		finally {
			if (session != null) {
				session.unbindAndClose();
			}

		}

		// return resultsms;
		return ResponseEntity.ok().headers(responseHeaders).body(resultsms);

	}

	// Method to update the client ussd statuses
	@RequestMapping(value = "/UpdateClientStatusfromALM", method = RequestMethod.POST)
	@ResponseBody
	public void UpdateClientStatusfromALM(String registartionid, String clientid, String status, String response_code,
			String response_message, String agentmsisdn, String msisdn) {
		session = almsessions.getSession();
		String clientstatus = status;

		if (status.equalsIgnoreCase("Success")) {
			clientstatus = "Activated";
		}
		if (status.equalsIgnoreCase("Failed")) {
			clientstatus = "Draft";
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				String ussdstatus = session
						.createNativeQuery("SELECT STATUS FROM CLIENTS where CLIENT_ID='" + clientid + "'").uniqueResult()
						.toString();
				if (ussdstatus.equalsIgnoreCase("SENT_USSD")) {
					clientstatus = "SENT_USSD";
				}

				session.flush();

				if (session.isOpen() && session != null) {

					query = session.createNativeQuery("UPDATE CLIENTS SET LAST_MODIFIED_DATE=SYSDATE, REGISTRATION_ID ='"
							+ registartionid + "' ,RESPONSE_CODE='" + response_code + "',RESPONSE_MESSAGE='"
							+ response_message + "',REGISTRATION_STATUS='" + status + "',STATUS='" + clientstatus
							+ "' where CLIENT_ID='" + clientid + "'");
					query.executeUpdate();

					session.flush();

					String agentname = session
							.createNativeQuery(
									"SELECT FIRST_NAME ||' '|| LAST_NAME FROM AGENT where MSISDN='" + agentmsisdn + "'")
							.uniqueResult().toString();

					// Add SIMREG_LOG_DATA
					query = session.createNativeQuery(
							"insert into SIMREG_LOG (REGISTRATIONDATE,CLIENT_ID,SIM_REG_STATUS,RESPONSE_CODE,RESPONSE_MESSAGE,AGENT_NUMBER,AGENT_NAME,MOBILE_NUMBER )values (sysdate,'"
									+ clientid + "','" + status + "','" + response_code + "','" + response_message
									+ "','" + agentmsisdn + "','" + agentname + "','" + msisdn + "')");
					query.executeUpdate();

					session.flush();

				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in UpdateClientStatusfromALM due to \n " + exceptionAsString);
				logger.info("Error in UpdateClientStatusfromALM due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in UpdateClientStatusfromALM method");
			logger.info("Could not connect to DB in UpdateClientStatusfromALM method ");
			logger.finest("Could not connect to DB in UpdateClientStatusfromALM method ");
		}

	}

	/////// TKASH REGISTRATION
	@SuppressWarnings({ "unused", "resource" })
	@RequestMapping(value = "/TKASHRegistrationAPIWeb", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> TKASHRegistrationAPIWeb(@RequestBody SimRegistrationAPI sim) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("TKASHRegistrationAPIWeb", "response_TKASHRegistrationAPIWeb");

		logger.finest("Entering TKash Registration.");
		logger.info("Entering TKash Registration.");
		data = "Service is not available";
		String result, api_response_code = null, response_message = null, registration_status, clientpkid = "0", fname,
				mname, lname, msisdn = "0", idType, idNumber, dob, gender, email, altnumber, address1, state,
				agentmsisdn = "0", getid = "0";
		int flag = 0;

		// 776761539 msisdn
		HttpURLConnection urlConnection = null;
		Boolean connectionFailed = false;
		try {
			logger.finest("Reading parameters.");
			logger.info("Reading parameters.");
			clientpkid = sim.getClientpkid();
			msisdn = sim.getMsisdn();
			fname = sim.getFname();
			mname = sim.getMname();
			lname = sim.getLname();
			idType = sim.getIdType();
			idNumber = sim.getIdNumber();
			dob = sim.getDob();
			email = sim.getEmail();
			altnumber = sim.getAltnumber();
			address1 = sim.getAddress1();
			state = sim.getState();
			agentmsisdn = sim.getAgentmsisdn();
			gender = sim.getGender();
			getid = getrequestid();

			if (gender.equalsIgnoreCase("Male")) {
				gender = "M";
			}

			if (gender.equalsIgnoreCase("Female")) {
				gender = "F";
			}

			logger.info("gender is :" + gender);
			logger.finest("gender is : " + gender);
			logger.finest("Date of birth is : " + dob);
			logger.info("Date of birth is : " + dob);

			logger.finest("Client ID is " + clientpkid);
			logger.info("Client ID is " + clientpkid);

			logger.finest("Sending parameters.");
			logger.info("Sending parameters.");
			JsonObject postData = new JsonObject();
			postData.addProperty("requestId", getid);
			postData.addProperty("serviceId", "REGMFS");
			postData.addProperty("clientId", "1");
			postData.addProperty("msisdn", msisdn);
			postData.addProperty("firstName", fname);
			postData.addProperty("middleName", mname);
			postData.addProperty("lastName", lname);
			postData.addProperty("idType", idType);
			postData.addProperty("idNumber", idNumber);
			postData.addProperty("dateOfBirth", dob);
			postData.addProperty("minorFlag", "NO");
			postData.addProperty("gender", gender);
			postData.addProperty("emailId", email);
			postData.addProperty("alternateNumber", altnumber);
			postData.addProperty("address_1", address1);
			postData.addProperty("address_2", "");
			postData.addProperty("address_3", "");
			postData.addProperty("cityName", "Nairobi");
			postData.addProperty("stateName", state);
			postData.addProperty("country", "KENYA");
			postData.addProperty("userId", "APPUSER");
			postData.addProperty("clientPassword", "iPacsUssd@123");
			postData.addProperty("agentMsisdn", agentmsisdn);

			logger.finest("before Opening Connection.");
			logger.info("before Opening Connection.");
			URL url = new URL("http://10.22.25.10:8995/ipacs/ussd/api/");

			boolean portAvailable = false;

			try {
				Socket socket = new Socket("10.22.25.10", 8995);
				portAvailable = true;
				// socket.close();
			} catch (IOException e) {
				logger.finest("port is closed.");
				logger.info("port is closed.");
			}

			if (portAvailable == true) {
				urlConnection = (HttpURLConnection) url.openConnection();
				logger.finest("after Opening Connection.");
				logger.info("after Opening Connection.");
				urlConnection.setRequestProperty("Content-Type", "application/json");
				urlConnection.setRequestMethod("POST");
				urlConnection.setDoOutput(true);
				urlConnection.setDoInput(true);
				urlConnection.setChunkedStreamingMode(0);

				/// validate if we have access to URL
				HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
				httpUrlConnection.setDoOutput(true);
				httpUrlConnection.setDoInput(true);
				httpUrlConnection.setConnectTimeout(5000);
				int responseCode = httpUrlConnection.getResponseCode();
				logger.finest("Http Connection Response Code : " + responseCode);
				logger.info("Http Connection Response Code : " + responseCode);
				if (responseCode != 200) {
					urlConnection.connect();
					logger.finest("Connection success.");
					logger.info("Connection success.");
					connectionFailed = true;

					OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
					writer.write(postData.toString());
					writer.flush();
					flag = 1;

					int code = urlConnection.getResponseCode();
					logger.finest("URL Connection Response Code : " + code);
					logger.info("URL Connection Response Code : " + code);
					BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					String line = null;
					while ((line = rd.readLine()) != null) {
						logger.finest("line is : " + line);
						logger.info("line is : " + line);
						if (line.contains("responseCode")) {

							int m = 0;
							m = line.indexOf(";");
							String response_code = line.substring(m + 1, line.length());
							String[] test1 = response_code.split("[:,]");

							String[] splitterString = test1[1].split("\"");

							for (String s : splitterString) {

								api_response_code = s;

							}
						}

						if (line.contains("message")) {

							int n = 0;
							n = line.indexOf(";");
							String message = line.substring(n + 1, line.length());
							String[] test1 = message.split("[:,]");
							String[] splitterString = test1[1].split("\"");
							for (String s : splitterString) {
								response_message = s;

							}

						}

					}

					logger.finest("API response Code : " + api_response_code);
					logger.info("API response Code : " + api_response_code);

					logger.finest("API response Message : " + response_message);
					logger.info("API response Message : " + response_message);

				}

			} else {

				data = "Service is not available at this moment, please try again later.";
				return ResponseEntity.ok().headers(responseHeaders).body(data);

			}
			/////////////////////////////////////////////

		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
			connectionFailed = false;
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in TKASHRegistrationAPIWeb due to \n " + exceptionAsString);
			logger.info("Error in TKASHRegistrationAPIWeb due to \n " + exceptionAsString);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();

			} else {
				data = "0";
			}
		}

		if (connectionFailed == true) {
			if (response_message.contains("Success") || response_message.contains("success")
					|| response_message.contains("SUCCESS")) {
				registration_status = "Success";

			} else {
				registration_status = "Failed";
			}

			if (response_message.contains("'")) {
				response_message = response_message.replace("'", " ");
			}
			// update in DB based on TKASH_REGISTRATION_STATUS status
			String tkashresposne = UpdatetkashStatusfromALM(getid, clientpkid, registration_status, api_response_code,
					response_message, agentmsisdn, msisdn);

			if (tkashresposne.equalsIgnoreCase("Already Success")) {
				data = api_response_code + "!!" + "Already Success in T-KASH" + "!!" + registration_status;
			} else {
				data = tkashresposne;// api_response_code + "!!" + response_message + "!!" + registration_status;
			}

		}
		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	// Method to update the client tkash status
	@RequestMapping(value = "/UpdatetkashStatusfromALM", method = RequestMethod.POST)
	@ResponseBody
	public String UpdatetkashStatusfromALM(String tkashregistartionid, String clientid, String status,
			String response_code, String response_message, String agentmsisdn, String msisdn) {
		String tkashststus = null, data = "0";

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			if (response_message.contains("ORA-") || response_message.contains("ora-")) {
				response_message = "Call TKASH support for " + response_message;
				data = response_code + "!!" + response_message + "!!" + status;
			} else {
				data = response_code + "!!" + response_message + "!!" + status;
			}

			try {
				tkashststus = session
						.createNativeQuery(
								"SELECT TKASH_REGISTRATION_STATUS FROM CLIENTS where CLIENT_ID='" + clientid + "'")
						.uniqueResult().toString();
				if (!tkashststus.equalsIgnoreCase("Success")) {
					query = session.createNativeQuery("UPDATE CLIENTS SET LAST_MODIFIED_DATE=SYSDATE, TKASHREGISTER_ID ='"
							+ tkashregistartionid + "',TKASH_RESPONSE_CODE='" + response_code
							+ "',TKASH_RESPONSE_MESSAGE='" + response_message + "',TKASH_REGISTRATION_STATUS='" + status
							+ "' where CLIENT_ID='" + clientid + "'");
					query.executeUpdate();

					session.flush();

					// Add SIMREG_LOG_DATA
					query = session.createNativeQuery(
							"insert into TKASHREG_LOG (REGISTRATIONDATE,CLIENT_ID,SIM_REG_STATUS,RESPONSE_CODE,RESPONSE_MESSAGE,AGENT_NUMBER,AGENT_NAME,MOBILE_NUMBER )values (sysdate,'"
									+ clientid + "','" + status + "','" + response_code + "','" + response_message
									+ "','" + agentmsisdn + "',(SELECT AGENT.FULL_NAME FROM AGENT where MSISDN='"
									+ agentmsisdn + "'),'" + msisdn + "')");
					query.executeUpdate();
				} else {
					data = "Already Success";
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in UpdatetkashStatusfromALM due to \n " + exceptionAsString);
				logger.info("Error in UpdatetkashStatusfromALM due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in UpdateClientStatusfromALM method");
			logger.info("Could not connect to DB in UpdateClientStatusfromALM method ");
		}
		return data;

	}

	// Method to get the TKASH statistical summary for a specific agent
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTKASHStatisticalSummary", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getTKASHStatisticalSummary(@RequestBody ClientListViewAPI client) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getStatisticalSummary", "response_getStatisticalSummary");
		String agentNumber = client.getAgentNumber();
		data = null;

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				query = session.createNativeQuery("SELECT "
						+ "nvl(sum(case when CREATED_DATE  >= SysDate-30 AND AGENT_NUMBER='" + agentNumber
						+ "' then 1 else 0 end ),0),"
						+ "nvl(sum(case when TKASH_REGISTRATION_STATUS = 'Success' AND CREATED_DATE  >= SysDate-30 AND AGENT_NUMBER='"
						+ agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when TKASH_REGISTRATION_STATUS = 'Failed' AND CREATED_DATE  >= SysDate-30 AND AGENT_NUMBER='"
						+ agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when  TKASH_REGISTRATION_STATUS = '0' AND CREATED_DATE  >= SysDate-30 AND AGENT_NUMBER='"
						+ agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when CREATED_DATE  >= SysDate-7 AND AGENT_NUMBER='" + agentNumber
						+ "' then 1 else 0 end ),0),"
						+ "nvl(sum(case when TKASH_REGISTRATION_STATUS = 'Success' AND CREATED_DATE  >= SysDate-7 AND AGENT_NUMBER='"
						+ agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when TKASH_REGISTRATION_STATUS = 'Failed' AND CREATED_DATE  >= SysDate-7 AND AGENT_NUMBER='"
						+ agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when  TKASH_REGISTRATION_STATUS = '0' AND CREATED_DATE  >= SysDate-7 AND AGENT_NUMBER='"
						+ agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when  CREATED_DATE  >= cast(trunc(current_timestamp) "
						+ "as timestamp) AND AGENT_NUMBER='" + agentNumber + "' then 1 else 0 end ),0),"
						+ "nvl(sum(case when TKASH_REGISTRATION_STATUS = 'Success' AND CREATED_DATE  >= cast(trunc(current_timestamp)"
						+ "as timestamp) AND AGENT_NUMBER='" + agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when TKASH_REGISTRATION_STATUS = 'Failed' AND CREATED_DATE  >= cast(trunc(current_timestamp)"
						+ "as timestamp) AND AGENT_NUMBER='" + agentNumber + "' then 1 else 0 end),0),"
						+ "nvl(sum(case when  TKASH_REGISTRATION_STATUS = '0' AND CREATED_DATE  >= cast(trunc(current_timestamp)"
						+ "as timestamp) AND AGENT_NUMBER='" + agentNumber + "' then 1 else 0 end),0)"
						+ " FROM CLIENTS");

				List<String> list = (List<String>) query.list();
				data = mapper.writeValueAsString(list);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in TkashStatisticalSummary due to \n " + exceptionAsString);
				logger.info("Error in TkashStatisticalSummary due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in TkashStatisticalSummary method");
			logger.info("Could not connect to DB in TkashStatisticalSummary method ");
			logger.finest("Could not connect to DB in TkashStatisticalSummary method ");
		}

		// return data;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	/////// AirtimeAPIWeb
	@RequestMapping(value = "/AirtimeAPIWeb", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> AirtimeAPIWeb(@RequestBody String rechargeData) throws Exception {
		String[] data1, data2;
		String respstatus = "0", clientbalance = "0";

		rechargeData = rechargeData.substring(2, Integer.parseInt(String.valueOf(rechargeData.length())));
		StringBuffer sb = new StringBuffer(rechargeData);
		sb.deleteCharAt(sb.length() - 1);
		rechargeData = sb.toString();
		String agentNumber = null, clientNumber = null, clientAmount = null;
		rechargeData = rechargeData.replaceAll("\"", "");
		data1 = rechargeData.split(",");
		data2 = data1[0].split(":");
		agentNumber = data2[1];
		data2 = data1[1].split(":");
		clientNumber = data2[1];
		data2 = data1[2].split(":");
		clientAmount = data2[1];

		Pattern pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*");
		String consumerKey = "2FwROZCkSk6d5QgcTvdDNfxDX4Ea";// clientId //2FwROZCkSk6d5QgcTvdDNfxDX4Ea
															// _SGJcPQXiAdyGJiqRR9Yv2JPOnsa
		String consumerSecret = "Ky6qLIThW9556xAQPoKz1rCrBhca";// client secret //Ky6qLIThW9556xAQPoKz1rCrBhca
																// qQAY4rz6Cn_ydRf4Os50WrcPMVUa
		String tokenUrl = "https://api.telkom.co.ke/token"; // "https://api.telkom.co.ke/ejaze/V3/ejazeAtp"
		String auth = consumerKey + ":" + consumerSecret;
		String authentication = Base64.getEncoder().encodeToString(auth.getBytes());
		String apimessage = "";
		int apicode = 0;
		SSLContext context = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("AirtimeAPIWeb", "response_AirtimeAPIWeb");

		try {
			context = SSLContext.getInstance("TLSv1.2");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TrustManager[] trustManager = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkClientTrusted(X509Certificate[] certificate, String str) {
			}

			public void checkServerTrusted(X509Certificate[] certificate, String str) {
			}
		} };

		context.init(null, trustManager, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

		String content = "grant_type=password&username=katelecomapp&password=katelecoM20";
		BufferedReader readertoken = null;
		String returnValue = "";

		HttpsURLConnection httpsurlconnection = null;

		try {
			URL url = new URL(tokenUrl);
			httpsurlconnection = (HttpsURLConnection) url.openConnection();
			httpsurlconnection.setRequestMethod("POST");
			httpsurlconnection.setDoOutput(true);
			httpsurlconnection.setRequestProperty("username", "katelecomapp");// katelecomapp katelecom22
			httpsurlconnection.setRequestProperty("password", "katelecoM20");// katelecoM20 katelecoM20
			httpsurlconnection.setRequestProperty("Authorization", "Basic " + authentication);
			httpsurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpsurlconnection.setRequestProperty("Accept", "application/json");
			PrintStream os = new PrintStream(httpsurlconnection.getOutputStream());
			os.print(content);
			os.close();
			readertoken = new BufferedReader(new InputStreamReader(httpsurlconnection.getInputStream()));
			String line = null;
			StringWriter out = new StringWriter(
					httpsurlconnection.getContentLength() > 0 ? httpsurlconnection.getContentLength() : 2048);
			while ((line = readertoken.readLine()) != null) {
				out.append(line);
			}
			String response = out.toString();
			Matcher matcher = pat.matcher(response);
			if (matcher.matches() && matcher.groupCount() > 0) {
				returnValue = matcher.group(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (readertoken != null) {
				try {
					readertoken.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			httpsurlconnection.disconnect();
		}

		/////////////////////////////////////////////////////////////////////////////////

		HttpURLConnection urlConnection = null;
		try {
			// read variable daa amount, source msisdn and destination nmsisdn
			String varamount = clientAmount;
			String sourcenumber = "254" + agentNumber;// 254774898334
			String destinationnumber = "254" + clientNumber; // 254774258153

			String data = "{\"airtimeRequest\": {\"loginId\": \"KatelecomTest\",\"password\": \"katecoM2022%\",\"pin\": \"1357\",\"code\": \"8084\",\"sourceMsisdn\": \""
					+ sourcenumber + "\",\"destMsisdn\": \"" + destinationnumber + "\",\"amount\": \"" + varamount
					+ "\",\"extrefnum\": \"1374925\"}}";

			URL url = new URL("https://api.telkom.co.ke/ejaze/v3/ejazeAtp");

			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Authorization", "Bearer " + returnValue);// returnValue "Bearer
																						// 46bff224-da9d-3d98-ad38-b4155d63f834"
			urlConnection.setRequestProperty("Cookie", "route=845cfb0baf56b152b52a2267bb5b403abfc44ee5");
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setChunkedStreamingMode(0);
			/// validate if we have access to URL
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setConnectTimeout(5000);
			urlConnection.connect();
			OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
			writer.write(data);
			writer.flush();

			apicode = urlConnection.getResponseCode();
			System.out.println("code = " + apicode);
			BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line = null;
			while ((line = rd.readLine()) != null) {
				if (line.contains("airtimeResponse")) {

				}

				if (line.contains("message")) {

					int n = 0, m = 0;
					n = line.indexOf("message");
					m = line.indexOf("transactionId");
					apimessage = line.substring(n + 10, m - 3);
				}
			}

			/////////////////////////////////////////////

		} catch (Exception e) {
			e.printStackTrace();
			apimessage = e.toString();

		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();

			}
		}

		//// update hisotry table MOBILE_CHARGE

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				String agentname = session
						.createNativeQuery("SELECT FULL_NAME FROM AGENT where MSISDN='" + agentNumber + "'").uniqueResult()
						.toString();

				session.flush();
				// Add MOBILE_CHARGE_LOG_DATA
				// apimessage="Transaction ID R220530.1338.210056 to top up 20 kshs to
				// 254770224100 is successful. Your new balance is 460 kshs";
				if (apimessage.contains("successful")) {
					respstatus = "Success";
					int gettotalbalance = apimessage.indexOf("Your new balance is");

					String message = apimessage.substring(gettotalbalance + 20, apimessage.length()).trim();

					gettotalbalance = message.indexOf("kshs");

					clientbalance = message.substring(0, gettotalbalance).trim();

				}

				if (session.isOpen() && session != null) {
					query = session.createNativeQuery(
							"insert into MOBILE_CHARGE (RECHARGEDATE,RECHARGE_STATUS,RESPONSE_CODE,RESPONSE_MESSAGE,AGENT_NUMBER,AGENT_NAME,MOBILE_NUMBER,AMOUNT,TOTAL_BALANCE)values (sysdate,'"
									+ respstatus + "','" + apicode + "','" + apimessage + "','" + agentNumber + "','"
									+ agentname + "','" + clientNumber + "','" + clientAmount + "','" + clientbalance
									+ "')");
					query.executeUpdate();
				}
			} catch (Exception e) {
				System.out.println("Error in insertion into mobile_charge with the AirtimeAPIWeb method");
				logger.info("Error in insertion into mobile_charge with the DataBase AirtimeAPIWeb method");
				e.printStackTrace();

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in  AirtimeAPIWeb method");
			logger.info("Could not connect to DB AirtimeAPIWeb method");
		}

		////////////////////////////////////////////////////////////////////////////

		// return data;
		data = apicode + "::" + apimessage;
		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	// Method to get the ClientchargeListView
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getClientchargeListView", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getClientchargeListView(
			@RequestBody MobileChargeListViewAPI chargeData) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getClientchargeListView", "response_getClientchargeListView");
		Map<String, Object> rtn = new LinkedHashMap<>();
		String agentNumber = chargeData.getAgentNumber();
		String date = chargeData.getDate();
		int vfrom = chargeData.getVfrom();
		int vto = chargeData.getVto();

		List<MobileChargeListViewAPI> chargeList = new ArrayList<MobileChargeListViewAPI>();

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				// if (agentnumber != null && rechargeDate != null) {

				chargeList = session.createNativeQuery("SELECT row_num,MOBILE_NUMBER,AMOUNT,RESPONSE_MESSAGE FROM "
						+ "(select ROW_NUMBER() OVER (ORDER BY RECHARGEDATE DESC) row_num,MOBILE_NUMBER,AMOUNT,RESPONSE_MESSAGE"
						+ "    from MOBILE_CHARGE where AGENT_NUMBER = '" + agentNumber + "'"
						+ "and  TO_DATE(TO_CHAR(RECHARGEDATE,'DD-MM-YYYY'),'DD-MM-YYYY') =TO_DATE('" + date
						+ "','DD-MM-YYYY')) " + "where  row_num >= " + vfrom + " AND row_num <= " + vto + "").list();

				rtn.put("ChargeListView", chargeList);
				session.flush();
				// get pagination
				if (chargeList.size() > 0) {
					data = session.createNativeQuery(
							"SELECT COUNT(*) FROM MOBILE_CHARGE where TO_DATE(TO_CHAR(RECHARGEDATE,'DD-MM-YYYY'),'DD-MM-YYYY') =TO_DATE('"
									+ date + "','DD-MM-YYYY') AND AGENT_NUMBER='" + agentNumber + "'")
							.uniqueResult().toString();

					rtn.put("ChargeListViewPagination", data);
				}

			}

			catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getClientchargeListView due to \n " + exceptionAsString);
				logger.info("Error in getClientchargeListView due to \n " + exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getClientchargeListView method");
			logger.info("Could not connect to DB getClientchargeListView method: ");
			logger.finest("Could not connect to DB getClientchargeListView method: ");
		}

		// return regionid;
		return ResponseEntity.ok().headers(responseHeaders).body(rtn);
	}

	///////////////////////////////////////////////

	///////// GET SHOPS FOR LIST VIEW
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getShopsData", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getShopsData(@RequestBody ShopsListViewAPI shopslist) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getShopsData", "response_getShopsData");
		Map<String, Object> rtn = new LinkedHashMap<>();

		List<ShopsListViewAPI> shopsdata = new ArrayList<ShopsListViewAPI>();
		String agentID = null;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				int vfrom = shopslist.getVfrom();
				int vto = shopslist.getVto();
				String agentNumber = shopslist.getAgentNumber();
				String shopname = shopslist.getShopName();
				String owner = shopslist.getOwner();

				agentID = session.createNativeQuery("SELECT AGENT_ID FROM AGENT WHERE MSISDN='" + agentNumber + "'")
						.uniqueResult().toString();

				session.flush();

				query = session.createNativeQuery(
						"SELECT * FROM (select ROW_NUMBER() OVER (ORDER BY LAST_MODIFIED_DATE DESC) row_num,SHOPS_ID as shopID,SHOP_NAME as shopName,OWNER as owner,ADDRESS as address,AGENT_ID as agentID FROM SHOPS "
								+ " where AGENT_ID = '" + agentID + "' and UPPER(SHOP_NAME) LIKE UPPER('%" + shopname
								+ "%') AND UPPER(OWNER) LIKE UPPER('%" + owner + "%')"
								+ " order by LAST_MODIFIED_DATE DESC) T WHERE row_num >= '" + vfrom
								+ "' AND row_num <='" + vto + "'");

				shopsdata = query.list();
				rtn.put("ShopListView", shopsdata);
				session.flush();

				if (shopsdata.size() > 0) {
					data = session
							.createNativeQuery("SELECT COUNT(*) FROM SHOPS where AGENT_ID='" + agentID
									+ "' and SHOP_NAME LIKE '%" + shopname + "' and OWNER LIKE '%" + owner + "'")
							.uniqueResult().toString();

					rtn.put("ShopListViewPagination", data);
				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getShopsData due to \n " + exceptionAsString);
				logger.info("Error in getShopsData due to \n " + exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getShopsData method");
			logger.info("Could not connect to DB getShopsData method: ");
			logger.finest("Could not connect to DB getShopsData method: ");

		}
		return ResponseEntity.ok().headers(responseHeaders).body(rtn);

	}

	//////

	///////////////////////////// DISPLAY SELECT SHOP
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/DisplayShops", method = RequestMethod.POST)
	@ResponseBody

	public ResponseEntity<List<DisplayShopsAPI>> DisplayShops(@RequestBody String display) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("DisplayShops", "response_DisplayShops");

		List<DisplayShopsAPI> data = new ArrayList<DisplayShopsAPI>();
		String shopid = null;
		String[] myresult;
		display = display.substring(1, Integer.parseInt(String.valueOf(display.length())));
		StringBuffer sb = new StringBuffer(display);
		sb.deleteCharAt(sb.length() - 1);
		display = sb.toString();
		myresult = display.split(":");
		shopid = myresult[1].replace("\"", "");

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				data = session.createNativeQuery(
						"select SHOPS_ID, OWNER, LONGTITUDE, LATITUDE, ADDRESS, SHOP_NAME,REGION_ID,REGION_NAME,REGION_CODE,"
								+ "nvl(SITE_ID,'0'),nvl(WARE_ID,'0'),nvl(SITE_NAME,'0'),"
								+ " SALES_OUTLET_TYPE,TOUCH_POINT_TYPE,TELKOM_CONTACT,ALTERNATIVE_CONTACT,"
								+ "nvl((SELECT LATITUDE from WAREHOUSE where WARE_ID = (SELECT WARE_ID FROM SHOPS WHERE SHOPS_ID='"
								+ shopid + "')),'0')as siteLat,"
								+ "nvl((SELECT LONGITUDE from WAREHOUSE where WARE_ID = (SELECT WARE_ID FROM SHOPS WHERE SHOPS_ID='"
								+ shopid + "')),'0')as siteLong,AGENT_NUMBER" + " from SHOPS " + " where SHOPS_ID='"
								+ shopid + "' ")
						.list();

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in DisplayShops due to \n " + exceptionAsString);
				logger.info("Error in DisplayShops due to \n " + exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in DisplayShops method");
			logger.info("Could not connect to DB DisplayShops method: ");
			logger.finest("Could not connect to DB DisplayShops method: ");

		}

		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	///////
	@SuppressWarnings("unchecked")
	// Method to get SITEs from WAREHOUSE Table
	@RequestMapping(value = "/getSitesAutoComplete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getSiteAutoComplete() throws JsonProcessingException {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getRegionsAutoComplete", "response_getRegionsAutoComplete");
		List<String> sites = null;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				query = session.createNativeQuery(
						"SELECT WARE_ID,SITE_ID,WARE_NAME,LATITUDE,LONGITUDE FROM WAREHOUSE WHERE SITE='1'");
				sites = query.list();
			}

			catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getSiteAutoComplete due to \n " + exceptionAsString);
				logger.info("Error in getSiteAutoComplete due to \n " + exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getSiteAutoComplete method");
			logger.info("Could not connect to DB getSiteAutoComplete");
			logger.finest("Could not connect to DB getSiteAutoComplete");
		}

		// return regions;
		return ResponseEntity.ok().headers(responseHeaders).body(mapper.writeValueAsString(sites));

	}

	///////
	// Method to get Regions from Regions Table
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRegionsAutoComplete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getRegionsAutoComplete() throws JsonProcessingException {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getRegionsAutoComplete", "response_getRegionsAutoComplete");
		List<String> regions = null;

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createNativeQuery("SELECT REGION_ID,REGION_NAME,REGION_CODE FROM REGION ");

				regions = query.list();
			}

			catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getRegionsAutoComplete due to \n " + exceptionAsString);
				logger.info("Error in getRegionsAutoComplete due to \n " + exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in  getRegionsAutoComplete method");
			logger.info("Could not connect to DB getRegionsAutoComplete");
			logger.finest("Could not connect to DB getRegionsAutoComplete");
		}

		// return regions;
		return ResponseEntity.ok().headers(responseHeaders).body(mapper.writeValueAsString(regions));

	}

	@RequestMapping(value = "/SavingorUpdatingShops", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> SavingorUpdatingShops(@RequestBody ShopsAPI shops) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("SavingorUpdatingShops", "response_SavingorUpdatingShops");

		String agentID, agentName, shopId, agentShopID;

		shopId = shops.getShopid();
		data = "not Saved";

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				agentID = session
						.createNativeQuery("SELECT AGENT_ID FROM AGENT WHERE MSISDN='" + shops.getAgentNumber() + "'")
						.uniqueResult().toString();

				session.flush();
				agentName = session
						.createNativeQuery("SELECT FULL_NAME FROM AGENT where MSISDN='" + shops.getAgentNumber() + "'")
						.uniqueResult().toString();
				session.flush();

				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);

				Timestamp CreationDate, LastModified;
				CreationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
				LastModified = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

				if (shopId.equalsIgnoreCase("0")) {
					// shopId = "SHOP_" + year + "_" + appConfig.getSequenceNbr(40);
					synchronized (this) {
						// agentId = "AG_" + year + "_" + appConfig.getSequenceNbr(42);
						shopId = "AG_" + year + "_" + Integer.parseInt(
								session.createNativeQuery("SELECT SHOPS FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET SHOPS = SHOPS + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
					}
				}

				ShopsAPI shop = new ShopsAPI();
				shop.setShopid(shopId);
				shop.setOwner(shops.getOwner());
				shop.setLongitude(shops.getLongitude());
				shop.setLatitude(shops.getLatitude());
				shop.setAddress(shops.getAddress());
				shop.setShopName(shops.getShopName());
				shop.setAgentId(agentID);
				shop.setAgentName(agentName);
				shop.setRegionId(shops.getRegionId());
				shop.setRegionName(shops.getRegionName());
				shop.setRegionCode(shops.getRegionCode());
				shop.setCreatedDate(CreationDate);
				shop.setLastModified(LastModified);
				shop.setAltContact(shops.getAltContact());
				shop.setSalesOutletType(shops.getSalesOutletType());
				shop.setSiteID(shops.getSiteID());
				shop.setSiteName(shops.getSiteName());
				shop.setTelkomContact(shops.getTelkomContact());
				shop.setTouchPoint(shops.getTouchPoint());
				shop.setWareID(shops.getWareID());
				shop.setAgentNumber(shops.getAgentNumber());
				if (session.isOpen() && session != null) {
					session.saveOrUpdate(shop);
					data = shopId;
				}

				session.flush();

				query = session
						.createNativeQuery("SELECT AGENT_SHOPS_ID from AGENT_SHOPS where SHOPS_ID='" + shopId + "'");
				if (query.uniqueResult() != null) {
					agentShopID = query.uniqueResult().toString();
				} else {
					synchronized (this) {
						// agentId = "AG_" + year + "_" + appConfig.getSequenceNbr(42);
						agentShopID = "AG_SHOPS_" + year + "_" + Integer.parseInt(
								session.createNativeQuery("SELECT AGENT_SHOPS FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET AGENT_SHOPS = AGENT_SHOPS + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
					}
					// agentShopID = "AG_SHOPS_" + year + "_" + appConfig.getSequenceNbr(43);
				}
				session.flush();

				AgentShops AgentShops = new AgentShops();
				AgentShops.setCreateDate(CreationDate);
				AgentShops.setLastModifiedDate(LastModified);
				AgentShops.setAgentShopsId(agentShopID);
				AgentShops.setShopsId(shopId);
				AgentShops.setShopsname(shops.getShopName());
				AgentShops.setLongtitude(shops.getLongitude());
				AgentShops.setLatitude(shops.getLatitude());
				AgentShops.setAgentId(agentID);

				if (session.isOpen() && session != null) {
					session.saveOrUpdate(AgentShops);

				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in SavingorUpdatingShops due to \n " + exceptionAsString);
				logger.info("Error in SavingorUpdatingShops due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in SavingorUpdatingShops method");
			logger.info("Could not connect to DB in SavingorUpdatingShops method ");
			logger.finest("Could not connect to DB in SavingorUpdatingShops method ");
		}

		return ResponseEntity.ok().headers(responseHeaders).body(data);
	}

	@RequestMapping(value = "/UploadShopImage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> UploadShopImage(@RequestBody ShopImageAPI image) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("deleteShop", "response_deleteShop");

		data = "not Saved";
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				String shopimgID = null;

				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);

				// shopimgID = "SHOP_IMG_" + year + "_" + appConfig.getSequenceNbr(73);

				synchronized (this) {
					// agentId = "AG_" + year + "_" + appConfig.getSequenceNbr(42);
					shopimgID = "SHOP_IMG_" + year + "_" + Integer.parseInt(
							session.createNativeQuery("SELECT SHOP_IMAGE FROM SEQ_TABLE").uniqueResult().toString());
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET SHOP_IMAGE = SHOP_IMAGE + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
				}
				ShopImageAPI shopimage = new ShopImageAPI();
				shopimage.setShopid(image.getShopid());
				shopimage.setShopimgID(shopimgID);
				shopimage.setUploadDate(new Timestamp(new Timestamp(System.currentTimeMillis()).getTime()));
				shopimage.setImageName(image.getImageName());
				if (session.isOpen() && session != null) {
					session.saveOrUpdate(shopimage);
					data = "saved";
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in UploadShopImage due to \n " + exceptionAsString);
				logger.info("Error in UploadShopImage due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in UploadShopImage method");
			logger.info("Could not connect to DB in UploadShopImage method ");
			logger.finest("Could not connect to DB in UploadShopImage method ");
		}

		return ResponseEntity.ok().headers(responseHeaders).body(data);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getShopImage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<ShopImageAPI>> getShopImage(@RequestBody ShopImageAPI shopimage) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("getShopImage", "response_getShopImage");
		List<ShopImageAPI> shopimagelist = new ArrayList<ShopImageAPI>();

		String shopid = shopimage.getShopid();

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				query = session.createNativeQuery("Select IMAGE_NAME FROM SHOPS_IMAGE WHERE SHOP_ID='" + shopid + "' ");
				shopimagelist = query.list();
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getShopImage due to \n " + exceptionAsString);
				logger.info("Error in getShopImage due to \n " + exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in getShopImage");
			logger.info("Could not connect to DB getShopImage: ");
			logger.finest("Could not connect to DB getShopImage: ");

		}

		return ResponseEntity.ok().headers(responseHeaders).body(shopimagelist);
	}

	@RequestMapping(value = "/deleteShopImage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> deleteShopImage(@RequestBody ShopImageAPI image) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("deleteShopImage", "response_deleteShopImage");
		data = null;

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String path = image.getImageName();
				query = session.createNativeQuery("delete  SHOPS_IMAGE  where IMAGE_NAME ='" + path + "'");
				query.executeUpdate();
				data = "deleted";
			} catch (Exception e) {
				data = "not deleted";
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in deleteShopImage due to \n " + exceptionAsString);
				logger.info("Error in deleteShopImage due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in deleteShopImage method");
			logger.info("Could not connect to DB in deleteShopImage method ");
			logger.finest("Could not connect to DB in deleteShopImage method ");
		}

		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	@RequestMapping(value = "/deleteShop", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> deleteShop(@RequestBody ShopsAPI shops) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("deleteShop", "response_deleteShop");
		String ShopId = shops.getShopid();
		data = null;

		String ImageCounter = "0";

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				ImageCounter = session.createNativeQuery("SELECT COUNT(*) from SHOPS_IMAGE where SHOP_ID='" + ShopId + "'")
						.uniqueResult().toString();
				if (ImageCounter.equalsIgnoreCase("0")) {

					query = session.createNativeQuery("delete AGENT_SHOPS where SHOPS_ID='" + ShopId + "'");
					query.executeUpdate();

					query = session.createNativeQuery("delete SHOPS where SHOPS_ID='" + ShopId + "'");
					query.executeUpdate();

					data = "deleted";
				} else {
					data = "Delete shop images first to delete this shop";

				}
			} catch (Exception e) {
				data = "not deleted";
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in deleteShop due to \n " + exceptionAsString);
				logger.info("Error in deleteShop due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in deleteShop method");
			logger.info("Could not connect to DB in deleteShop method ");
			logger.finest("Could not connect to DB in deleteShop method ");
		}

		return ResponseEntity.ok().headers(responseHeaders).body(data);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/savingSpeedCoverageTest", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> savingSpeedCoverageTest(@RequestBody SpeedCoverageTestAPI testingspeedcoverage) {
		String data1 = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("savingSpeedCoverageTest", "response_savingSpeedCoverageTest");

		logger.info("MSISDN in savingSpeedCoverageTest" + testingspeedcoverage.getAgentNumber());
		logger.finest("MSISDN in savingSpeedCoverageTest" + testingspeedcoverage.getAgentNumber());

		if (testingspeedcoverage.getAgentNumber() != null && testingspeedcoverage.getAgentNumber() != "") {
			session = almsessions.getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					Date date = new Date();
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(date);
					int year = calendar.get(Calendar.YEAR);

					String coverageSignal = null, technology = null;
					if (testingspeedcoverage.getCoverageSignal() != null) {
						if (testingspeedcoverage.getCoverageSignal().equalsIgnoreCase("null")) {
							coverageSignal = "0";
						} else {
							coverageSignal = testingspeedcoverage.getCoverageSignal();
						}

					} else {
						coverageSignal = "0";

					}

					if (testingspeedcoverage.getTechnology() != null) {
						if (testingspeedcoverage.getTechnology().equalsIgnoreCase("null")) {
							technology = "Tech";
						} else {
							technology = testingspeedcoverage.getTechnology();
						}

					} else {
						technology = "Tech";
					}
					String speedCoverageId = null;
					synchronized (this) {
						// agentId = "AG_" + year + "_" + appConfig.getSequenceNbr(42);
						speedCoverageId = "TEST_" + year + "_" + Integer.parseInt(session
								.createNativeQuery("SELECT SPEED_COVERAGE FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET SPEED_COVERAGE = SPEED_COVERAGE + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
					}
					// String speedCoverageId = "TEST_" + year + "_" + appConfig.getSequenceNbr(42);
					if (session.isOpen() && session != null) {
						query = session.createNativeQuery(
								"insert into speed_coverage_test (SPEEDCOVERAGEID,SPEEDCOVERAGE_DATE,SPEEDCOVERAGE_LAT,SPEEDCOVERAGE_LNG,COVERAGE_SIGNAL,SPEED_DOWNLOAD,"
										+ "SPEED_UPLOAD,AGENT_NUMBER,AGENT_NAME,CID,LAC,TECHNOLOGY,MMC,MNC,APP_VERSION) "
										+ "VALUES ('" + speedCoverageId + "',sysdate,'"
										+ testingspeedcoverage.getSpeedCoverageLat() + "','"
										+ testingspeedcoverage.getSpeedCoverageLong() + "'," + "'" + coverageSignal
										+ "','" + testingspeedcoverage.getSpeedDown() + "','"
										+ testingspeedcoverage.getSpeedUp() + "'," + "'"
										+ testingspeedcoverage.getAgentNumber()
										+ "',(SELECT AGENT.FULL_NAME FROM AGENT WHERE MSISDN='"
										+ testingspeedcoverage.getAgentNumber() + "')," + "'"
										+ testingspeedcoverage.getcid() + "','" + testingspeedcoverage.getlac() + "','"
										+ testingspeedcoverage.getTechnology() + "'," + "'"
										+ testingspeedcoverage.getMmc() + "','" + testingspeedcoverage.getMnc() + "','"
										+ testingspeedcoverage.getAppVersion() + "')");

						query.executeUpdate();
						data = "Saved";
					}
					session.flush();

					if (data.equalsIgnoreCase("Saved")) {
						data1 = testingspeedcoverage.getSpeedUp() + ":" + testingspeedcoverage.getSpeedDown() + ":"
								+ testingspeedcoverage.getSpeedCoverageLat() + ":"
								+ testingspeedcoverage.getSpeedCoverageLong() + ":"
								+ testingspeedcoverage.getCoverageSignal();
						if (session.isOpen() && session != null) {
							query = session.createNativeQuery("update agent set capture_speed ='0' where msisdn = "
									+ "(select msisdn from agent where capture_speed='1' and msisdn ='"
									+ testingspeedcoverage.getAgentNumber() + "')");
							query.executeUpdate();
						}
					}

					logger.info("Returned value after saving in savingSpeedCoverageTest is " + data);
					logger.finest("Returned value after saving in savingSpeedCoverageTest is " + data);

					logger.info("Returned value in savingSpeedCoverageTest is " + data1);
					logger.finest("Returned value in savingSpeedCoverageTest is " + data1);

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in savingSpeedCoverageTest due to \n " + exceptionAsString);
					logger.info("Error in savingSpeedCoverageTest due to \n " + exceptionAsString);
					data = "not saved";
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						session.getSessionFactory().close();
					}
				}
			} else {
				System.out.println("Could not connect to DB in  savingSpeedCoverageTest");
				logger.info("Could not connect to DB savingSpeedCoverageTest");
				logger.finest("Could not connect to DB savingSpeedCoverageTest");
				data = "not update";
			}
		} else {
			logger.info("MSISDN is null in savingSpeedCoverageTest");
			logger.finest("MSISDN is null in savingSpeedCoverageTest");
		}

		return ResponseEntity.ok().headers(responseHeaders).body(data1);

	}

	// update flag to zero after stopping the service in mobile

	@RequestMapping(value = "/UpdateSiteEngineerFlag", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> UpdateSiteEngineerFlag(@RequestBody RegionName regionName) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("UpdateSiteEngineerFlag", "response_UpdateSiteEngineerFlag");

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				if (regionName.getAgentNumber() != null || regionName.getAgentNumber().equalsIgnoreCase("")) {

					query = session.createNativeQuery(
							"Update AGENT set SITE_ENGINEER='0' where MSISDN='" + regionName.getAgentNumber() + "'");
					query.executeUpdate();
					data = "Updated";

				}

			}

			catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in UpdateSiteEngineerFlag due to \n " + exceptionAsString);
				logger.info("Error in UpdateSiteEngineerFlag due to \n " + exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		} else {
			System.out.println("Could not connect to DB in  UpdateSiteEngineerFlag method");
			logger.info("Could not connect to DB UpdateSiteEngineerFlag method");
			logger.finest("Could not connect to DB UpdateSiteEngineerFlag method");
		}

		return ResponseEntity.ok().headers(responseHeaders).body(data);

	}

	////// find nearest site to the shop
	@RequestMapping(value = "/FindNearestPoints_Mobile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> FindNearestPoints_Mobile(@RequestBody String lat) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("FindNearestPoints_Mobile", "response_FindNearestPoints_Mobile");
		String latitude, longitude;
		lat = lat.replace("{\"latitude\":\"", "").replace("\"longitude\":\"", "").replace("\"}", "").replace("\"", "");
		latitude = lat.split(",")[0];
		longitude = lat.split(",")[1];
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				query = session.createNativeQuery(
						"SELECT WARE_ID,SITE_ID,WARE_NAME,LATITUDE,LONGITUDE FROM WAREHOUSE WHERE SITE='1'");
				List<?> sitesList = query.list();

				List<Object[]> nearstSite = new ArrayList<Object[]>();
				nearstSite = findNearestArray(sitesList, Double.valueOf(latitude), Double.valueOf(longitude));
				int tempIndex = 0;
				List<Double> distance = new ArrayList<Double>();
				for (int i = 0; i < nearstSite.size(); i++) {
					distance.add((Double) nearstSite.get(i)[5]);

				}
				tempIndex = distance.indexOf(Collections.min(distance));

				rtn.put("nearestSite", nearstSite.get(tempIndex));

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in FindNearestPoints_Mobile due to \n " + exceptionAsString);
				logger.info("Error in FindNearestPoints_Mobile due to \n " + exceptionAsString);
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					session.getSessionFactory().close();
				}
			}
		}
		return ResponseEntity.ok().headers(responseHeaders).body(rtn);
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

	static <T> T[] append(T[] arr, T element) {
		final int N = arr.length;
		arr = Arrays.copyOf(arr, N + 1);
		arr[N] = element;
		return arr;
	}

	// function find nearest array for sites
	public List<Object[]> findNearestArray(List<?> ListOfObjects, double closestLatPoint, double closestLongPoint)
			throws JsonProcessingException {
		List<Object[]> nearstPointsArray = new ArrayList<Object[]>();
		List<Object[]> result = new ArrayList<Object[]>();
		double pointDist = 0.0;
		for (int i = 0; i < ListOfObjects.size(); i++) {
			Object[] objectArray = (Object[]) ListOfObjects.get(i);

			pointDist = haversine(closestLatPoint, closestLongPoint, Double.valueOf((String) objectArray[3]),
					Double.valueOf((String) objectArray[4]));
			objectArray = append(objectArray, (Object) pointDist);
			nearstPointsArray.add(objectArray);
		}
		if (nearstPointsArray.size() > 0) {

			result = nearstPointsArray.subList(0, Integer.valueOf(nearstPointsArray.size()));

		}

		return result;
	}

	@RequestMapping(value = "/versionValidation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> versionValidation(@RequestBody RegionName version) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("versionValidation", "response_versionValidation");
		// Ver2.1.0
		System.out.println("you are in the version validation");
		String versionnbr = null;
		String[] ver = null;
		try {
			System.out.println("VERSION IS :: " + version.getVersionNbr());
			if (version.getVersionNbr() != null) {
				versionnbr = version.getVersionNbr().replace("Ver", "").replace(".", ":");
				ver = versionnbr.split(":");
				if (Integer.parseInt(ver[0]) < 2) {
					data = "Invalid Version";
				} else if (Integer.parseInt(ver[1]) < 2) {
					data = "Invalid Version";

				} else if (Integer.parseInt(ver[2]) < 1) {
					data = "Invalid Version";
				} else {
					data = "Valid Version";
				}

			}

		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in versionValidation due to \n " + exceptionAsString);
			logger.info("Error in versionValidation due to \n " + exceptionAsString);

		}

		return ResponseEntity.ok().headers(responseHeaders).body(data);
	}

}
