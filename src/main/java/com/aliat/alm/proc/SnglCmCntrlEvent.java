package com.aliat.alm.proc;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SnglCmCntrlEvent {

	@Autowired
	CommScopeService commscopeService;

	private String str = "";
	private ObjectMapper mapper = new ObjectMapper();
	private final Logger logger = LoggerFactory.getLogger(SnglCmCntrlEvent.class);

	@SuppressWarnings("unchecked")
	public void login(Object[] cntrl_login, int requestedDuration, Object[] latestEvent, Session session) {
		System.out.println("Welcome to login");
		String token;
		Map<String, Object> rtn = new LinkedHashMap<>();
		Map<String, Object> rtnResponseBody = new LinkedHashMap<>();
		try {
			System.out.println("cntrls_login is " + mapper.writeValueAsString(cntrl_login));
			rtn = commscopeService.loginAPI(cntrl_login[1].toString(), cntrl_login[2].toString(),
					cntrl_login[3].toString(), requestedDuration);
			if (rtn.containsKey("accessToken")) {
				token = rtn.get("accessToken").toString();
				rtn = commscopeService.controllerxAPI(token, cntrl_login[1].toString());
				System.out.println("rtn is " +mapper.writeValueAsString(rtn));
				if (rtn.containsKey("responseBody")
						&& !StringUtils.equalsIgnoreCase(rtn.get("status").toString(), "Failed")) {
					rtnResponseBody = (Map<String, Object>) rtn.get("responseBody");
					if (StringUtils.equalsIgnoreCase(rtnResponseBody.get("networkManagerId").toString(),
							cntrl_login[4].toString())) {
						controllerEvent(cntrl_login, token, latestEvent, session);
					} else {
						session.createNativeQuery(
								"update controller set status = 'Not Reachable' where controller_id = :id")
								.setParameter("id", cntrl_login[0].toString()).executeUpdate();
					}
				}

			} else {
				session.createNativeQuery("update controller set status = 'Not Reachable' where controller_id = :id")
						.setParameter("id", cntrl_login[0].toString()).executeUpdate();
			}
		} catch (Exception e) {
			logger.info("Error in login method of SnglCmCntrl that call LoginAPI with a message : " + e
					+ "\n\" + e.getMessage()", e);
		}
	}

	@SuppressWarnings("unchecked")
	private void controllerEvent(Object[] cntrl_login, String token, Object[] latestEvent, Session session) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		Map<String, Object> rtnBody = new LinkedHashMap<>();
		List<Map<String, Object>> eventList = new ArrayList<>();
		String latestEventID = "1";

		System.out.println("Welcome to controllerEvent");

		try {
			if (latestEvent != null && latestEvent.length > 0 && latestEvent[0] != null) {
				latestEventID = String.valueOf((((Number) latestEvent[0]).longValue() + 1));
			}
			System.out.println("latestEventID is " + latestEventID);
			rtn = commscopeService.eventNoteAPI(token, cntrl_login[1].toString(), latestEventID, "10000");
			if (rtn.containsKey("responseBody")
					&& !StringUtils.equalsIgnoreCase(rtn.get("status").toString(), "Failed")) {
				rtnBody = (Map<String, Object>) rtn.get("responseBody");
				eventList = (List<Map<String, Object>>) rtnBody.get("eventList");
				System.out.println("eventList is " + mapper.writeValueAsString(eventList));
				if (eventList.size() > 0)
					insertEvent(eventList, cntrl_login, session);
			}
		} catch (Exception e) {
			logger.info(
					"Error in controllerEvent method of class SnglCmCntrlEvent which call eventNoteAPI with a message : "
							+ e + "\n\" + e.getMessage()",
					e);
		}
	}

	private void insertEvent(List<Map<String, Object>> events, Object[] cntrl_login, Session session) {

		String ipatchEventID = "", ipatchEventAttrID = "";
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);

		System.out.println("Welcome to insertEvent");

		try {
			for (Map<String, Object> event : events) {
				String tsStr = event.get("timestamp").toString();
				Timestamp ts = Timestamp.from(Instant.parse(tsStr + "Z"));
				ipatchEventID = "IPATCH_EVENT_" + year + "_" + ((Number) session
						.createNativeQuery("SELECT IPATCH_EVENT_SEQ.NEXTVAL FROM DUAL").uniqueResult()).longValue();
				str = "insert into ipatch_event (id, event_id, event_type, event_timestamp, serial_numb, raw_payload, controller_id, "
						+ "site, site_name, warehouse, longitude, latitude) values (:ipatchEventId, :eventID, :eventType, :ts, :serialNo, :payload, "
						+ ":controllerID, :site, :site_name, :warehouse, :longitude, :latitude)";
				session.createNativeQuery(str).setParameter("ipatchEventId", ipatchEventID)
						.setParameter("eventID", ((Number) event.get("eventId")).longValue())
						.setParameter("eventType", event.get("eventType").toString()).setParameter("ts", ts)
						.setParameter("serialNo", cntrl_login[4].toString())
						.setParameter("payload", mapper.writeValueAsString(event))
						.setParameter("controllerID", cntrl_login[0]).setParameter("site", cntrl_login[5])
						.setParameter("site_name", cntrl_login[6]).setParameter("warehouse", cntrl_login[7])
						.setParameter("longitude", cntrl_login[8]).setParameter("latitude", cntrl_login[9])
						.executeUpdate();

				// --- Insert attributes into IPATCH_EVENT_ATTR ---
				for (Map.Entry<String, Object> entry : event.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();

					// Skip main columns already mapped
					if (key.equals("eventId") || key.equals("eventType") || key.equals("timestamp")
							|| key.equals("controllerId"))
						continue;

					ipatchEventAttrID = "IPATCH_EVENT_ATTR_" + year + "_"
							+ ((Number) session.createNativeQuery("SELECT IPATCH_EVENT_ATTR_SEQ.NEXTVAL FROM DUAL")
									.uniqueResult()).longValue();

					str = "insert into ipatch_event_attr (id, event_id, attr_key, attr_val) "
							+ "values (:ipatchEventAttrId, :eventID, :attrKey, :attrVal)";

					session.createNativeQuery(str).setParameter("ipatchEventAttrId", ipatchEventAttrID)
							.setParameter("eventID", ipatchEventID).setParameter("attrKey", key)
							.setParameter("attrVal", value != null ? value.toString() : null).executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error(
					"Error in insertEvent method of class SnglCmCntrlEvent which is used to insert the events and its attributes in the databasewith a message : "
							+ e + "\n\" + e.getMessage()",
					e);
		}
	}
}