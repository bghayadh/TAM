package com.aliat.alm.proc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.aliat.alm.models.ControllerKit;
import com.aliat.alm.models.ControllerModule;
import com.aliat.alm.models.ControllerPanel;
import com.aliat.alm.models.DistributionBoard;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SnglCmCntrl {

	@Autowired
	CommScopeService commscopeService;

	private Map<String, String> cntrlRecord = new LinkedHashMap<>();
	private String str = "";
	private int totalPorts = 0;
	private List<String> kitNeedCheck = new ArrayList<>();
	private List<Map<String, Object>> newPanelKits = new ArrayList<>();
	private List<String> dbID_NeedUpdate = new ArrayList<>();
	private ObjectMapper mapper = new ObjectMapper();
	private final Logger logger = LoggerFactory.getLogger(SnglCmCntrl.class);
	
	public SnglCmCntrl() {
	    cntrlRecord.put("serial_numb", "");
	    cntrlRecord.put("mac_address", "");
	    cntrlRecord.put("subnet_mask", "");
	    cntrlRecord.put("default_gateway", "");
	    cntrlRecord.put("status", "");
	    cntrlRecord.put("num_of_panels", "0");
	    cntrlRecord.put("numb_of_ports", "0");
	    cntrlRecord.put("controller_id", "");		
	}

	public void login(String controllerID, String ipAddress, String username, String password, int requestedDuration,
			String serialNo, Session session) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		System.out.println("controllerID is " +controllerID);
	    cntrlRecord.put("serial_numb", "");
	    cntrlRecord.put("mac_address", "");
	    cntrlRecord.put("subnet_mask", "");
	    cntrlRecord.put("default_gateway", "");
	    cntrlRecord.put("status", "");
	    cntrlRecord.put("num_of_panels", "0");
	    cntrlRecord.put("numb_of_ports", "0");
		cntrlRecord.put("controller_id", controllerID);
		str = "";
		totalPorts = 0;
		kitNeedCheck = new ArrayList<>();
		newPanelKits = new ArrayList<>();
		dbID_NeedUpdate = new ArrayList<>();
		
		try {
			System.out.println("Welcome to login at SnglCmCntrl, the cntrlRecord is " +mapper.writeValueAsString(cntrlRecord));
			rtn = commscopeService.loginAPI(ipAddress, username, password, requestedDuration);
			System.out.println("rtn is " + mapper.writeValueAsString(rtn));
			if (rtn.containsKey("accessToken")) {
				controllerX(controllerID, rtn.get("accessToken").toString(), ipAddress, serialNo, session);
			} else {
				session.createNativeQuery("update controller set status = 'Not Reachable' where controller_id = :id")
						.setParameter("id", controllerID).executeUpdate();
			}
		} catch (Exception e) {
			logger.info("Error in login method of SnglCmCntrl that call LoginAPI with a message : " + e
					+ "\n\" + e.getMessage()", e);
		}
	}

	public void rack(String token, String ipAddress, String serialNo, Session session) {

	}

	@SuppressWarnings("unchecked")
	public void controllerX(String controllerID, String token, String ipAddress, String serialNo, Session session) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		Map<String, Object> rtnBody = new LinkedHashMap<>();
		// ControllerPanel cntrl = new ControllerPanel();
		try {
			rtn = commscopeService.controllerxAPI(token, ipAddress);
			System.out.println("rtn from controllerxAPI is " + mapper.writeValueAsString(rtn));
			if (rtn.containsKey("responseBody")
					&& !StringUtils.equalsIgnoreCase(rtn.get("status").toString(), "Failed")) {
				rtnBody = (Map<String, Object>) rtn.get("responseBody");
				System.out.println("rtnBody is " + mapper.writeValueAsString(rtnBody));
				cntrlRecord.put("serial_numb", rtnBody.get("networkManagerId").toString());
				if (StringUtils.isBlank(serialNo) || "null".equalsIgnoreCase(serialNo)
						|| StringUtils.equalsIgnoreCase(serialNo, rtnBody.get("networkManagerId").toString())) {
					cntrlRecord.put("controller_id", controllerID);
					System.out.println(
							"New Serial or Same, the cntrlRecord is: " + mapper.writeValueAsString(cntrlRecord));
					cntrlRecord.put("status", "Reachable");
				} else {
					session.createNativeQuery(
							"update controller set status = 'Not Reachable', last_scan_date = sysdate where serial_numb =:serialNo")
							.setParameter("serialNo", serialNo).executeUpdate();
					System.out.println("Not Reachable Controller");
					List<Object[]> cntrlModifyedIP = session.createNativeQuery(
							"select controller_id from controller where serial_numb =:serialNo order by last_modified_date desc")
							.setParameter("serialNo", rtnBody.get("networkManagerId").toString()).list();
					System.out.println("Length of the cntrlModifyedIP List is " + cntrlModifyedIP.size());
					cntrlRecord.put("controller_id", cntrlModifyedIP.get(0)[0].toString());
				}
				networkInterface(token, ipAddress);
				panel(cntrlRecord.get("controller_id"), token, ipAddress, cntrlRecord.get("serial_numb"), session);
				/*
				 * cntrl.setControllerId(cntrlRecord.get("controller_id"));
				 * cntrl.setSerialNumber(cntrlRecord.get("serial_numb"));
				 * cntrl.setMacAddress(cntrlRecord.get("mac_address"));
				 */
				
				System.out.println("Just before updating controller information, the cntrlRecord is " +mapper.writeValueAsString(cntrlRecord));
				str = "update controller set SERIAL_NUMB = :serial_numb, mac_address = :mac_address, subnet_mask = :subnet_mask, "
						+ "default_gateway = :dgw, last_scan_date = sysdate, status = :status, num_of_panels = :num_of_panels, "
						+ "numb_of_ports = :numb_of_ports where controller_id = :ID";

				session.createNativeQuery(str).setParameter("serial_numb", cntrlRecord.get("serial_numb"))
						.setParameter("mac_address", cntrlRecord.get("mac_address"))
						.setParameter("subnet_mask", cntrlRecord.get("subnet_mask"))
						.setParameter("dgw", cntrlRecord.get("default_gateway"))
						.setParameter("status", cntrlRecord.get("status"))
						.setParameter("num_of_panels", cntrlRecord.get("num_of_panels"))
						.setParameter("numb_of_ports", cntrlRecord.get("numb_of_ports"))
						.setParameter("ID", cntrlRecord.get("controller_id")).executeUpdate();
				if (!kitNeedCheck.isEmpty()) {
					str = "update distribution_board set \"TYPE\" = 'passive', controller_id = null where DB_ID in (select DB_ID from controller_kit "
							+ "where kit_serial_num in (:serial_num))";
					session.createNativeQuery(str).setParameterList("serial_num", kitNeedCheck).executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.info("Error in controllerX method of class SnglCmCntrl which call controllerxAPI with a message : "
					+ e + "\n\" + e.getMessage()", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void panel(String controllerID, String token, String ipAddress, String rackID, Session session) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		List<Map<String, Object>> panelList = new ArrayList<>();
		int panelCount = 0;
		try {
			System.out.println("controllerID is " +controllerID);
			str = "select kit_serial_num from controller_kit where db_id in (select db_id from distribution_board where controller_id ='"
					+ controllerID + "')";
			kitNeedCheck = session.createNativeQuery(str).getResultList();
			System.out.println("kitNeedCheck is " +mapper.writeValueAsString(kitNeedCheck));
			rtn = commscopeService.getPanelAPI(token, ipAddress, rackID);
			System.out.println("rtn of getPanelAPI is " +mapper.writeValueAsString(rtn));
			if (rtn.containsKey("responseBody")
					&& !StringUtils.equalsIgnoreCase(rtn.get("status").toString(), "Failed")) {
				Map<String, Object> rtnBody = new LinkedHashMap<>();
				rtnBody = (Map<String, Object>) rtn.get("responseBody");
				System.out.println("responseBody of getPanelAPI is " +mapper.writeValueAsString(rtnBody));
				if (rtnBody.containsKey("panels")) {
					panelList = (List<Map<String, Object>>) rtnBody.get("panels");
					System.out.println("panelList is " +mapper.writeValueAsString(panelList));
					for (Map<String, Object> panel : panelList) {
						System.out.println("panel is " +mapper.writeValueAsString(panel));
						if ((Integer) (panel.get("panelNumber")) != null && (Integer) (panel.get("panelNumber")) > 0) {
							panelCount++;
							kitsTreatment((List<Map<String, Object>>) panel.get("kits"), session);
							System.out.println("After kitsTreatment and before updating DB's for the controller ID");
							session.createNativeQuery(
									"update distribution_board set controller_id = :id, \"TYPE\" = 'active' where db_id in (:db_IDs)")
									.setParameter("id", controllerID).setParameterList("db_IDs", dbID_NeedUpdate)
									.executeUpdate();
							if (newPanelKits.size() > 0) {
								System.out.println("newPanelKits is " +mapper.writeValueAsString(newPanelKits));
								insertPanel(controllerID, newPanelKits, session);
							}
						}
					}
				}
			}
			cntrlRecord.put("num_of_panels", Integer.toString(panelCount));
			cntrlRecord.put("numb_of_ports", Integer.toString(totalPorts));
			System.out.println("cntrlRecord hashmap in the end of the panel method is " +mapper.writeValueAsString(cntrlRecord));
		} catch (Exception e) {
			logger.info("Error in panel method of class SnglCmCntrl which call panelAPI with a message : " + e
					+ "\n\" + e.getMessage()", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void kitsTreatment(List<Map<String, Object>> kitList, Session session) throws JsonProcessingException {
		List<Map<String, Object>> modules = new ArrayList<>();
		System.out.println("Welcome to kitsTreatment");
		System.out.println("kitList is " +mapper.writeValueAsString(kitList));
		System.out.println("kitNeedCheck is " +mapper.writeValueAsString(kitNeedCheck));
		for (Map<String, Object> kit : kitList) {			
			if (kitNeedCheck.contains(kit.get("kitId").toString())) {
				System.out.println("Index of kit serial number: " +kit.get("kitId").toString() + " is " +kitNeedCheck.indexOf(kit.get("kitId").toString()));
				kitNeedCheck.remove(kitNeedCheck.get(kitNeedCheck.indexOf(kit.get("kitId").toString())));
				modules = (List<Map<String, Object>>) kit.get("modules");
				for (Map<String, Object> module : modules) {
					totalPorts += Integer.parseInt(module.get("sensorCount").toString());
				}
			} else {
				List<Object> otherDbID = new ArrayList<>();
				System.out.println("******************* kitID is " +kit.get("kitId").toString());
				str = "select db_id from controller_kit where kit_serial_num = '" + kit.get("kitId").toString() + "'";
				System.out.println("str is " +str);
				otherDbID = session.createNativeQuery(str).list();
				System.out.println("otherDbID is " +otherDbID.size());
				if (otherDbID.size() > 0) {
					modules = (List<Map<String, Object>>) kit.get("modules");
					for (Map<String, Object> module : modules) {
						totalPorts += Integer.parseInt(module.get("sensorCount").toString());
					}
					if (!dbID_NeedUpdate.contains(otherDbID.get(0))) {
						dbID_NeedUpdate.add(otherDbID.get(0).toString());
					}
				} else {
					System.out.println("The kit is new, kit serial number is " +kit.get("kitId").toString());
					modules = (List<Map<String, Object>>) kit.get("modules");
					for (Map<String, Object> module : modules) {
						totalPorts += Integer.parseInt(module.get("sensorCount").toString());
					}
					newPanelKits.add(kit);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void insertPanel(String controllerID, List<Map<String, Object>> newKits, Session session) {
		System.out.println("Welcome to insertPanel, newKits size if " +newKits.size());
		String dbID = "", kitID = "", moduleID = "";
		int rowPerModule = 0, seq = 0;
		DistributionBoard distributionBoard = new DistributionBoard();
		Timestamp creationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		ControllerPanel cntrl = new ControllerPanel();
		//ControllerKit panelKit = new ControllerKit();
		//ControllerModule kitModule = new ControllerModule();
		List<Map<String, Object>> modules = new ArrayList<>();
		cntrl = session.get(ControllerPanel.class, controllerID);
		
		seq = ((Number) session.createNativeQuery("SELECT DB_SEQ.NEXTVAL FROM DUAL").uniqueResult()).intValue();
		dbID = "DB_" + year + "_" + seq;
		
		System.out.println("dbID is " +dbID);

		distributionBoard.setDistributionBoardId(dbID);
		distributionBoard.setBoardCreationDate(creationDate);
		distributionBoard.setBoardLastModifiedDate(creationDate);
		distributionBoard.setDistributionBoardName("DB_" + cntrl.getControllerName());
		distributionBoard.setDistributionBoardSite(cntrl.getSite());
		distributionBoard.setDistributionBoardSiteName(cntrl.getSiteName());
		distributionBoard.setDistributionBoardWarehouse(cntrl.getWarehouse());
		distributionBoard.setDistributionBoardCity(cntrl.getControllerCity());
		distributionBoard.setDbNetLevel(cntrl.getNetworkLayer());
		distributionBoard.setDistributionBoardLat(cntrl.getLatitude());
		distributionBoard.setDistributionBoardLong(cntrl.getLongitude());
		distributionBoard.setDBInstaller("");
		distributionBoard.setDistributionBoardType("");
		distributionBoard.setDBEngineerName("");
		distributionBoard.setRowCounting("");
		distributionBoard.setDistributionBoardControllerId(controllerID);
		distributionBoard.setDistributionBoardControllerName(cntrl.getControllerName());
		distributionBoard.setDistributionBoardSerialNum("");
		distributionBoard.setDBDeploymentType("");
		distributionBoard.setDBAdaptorPanelType("");
		distributionBoard.setDistributionBoardType("active");
		/*
		 * String projectId= request.getParameter("ProjectId"); if(projectId == null ||
		 * projectId == "SC") {
		 * 
		 * projectId="CurrentPhysicalLayer";
		 * 
		 * }
		 */
		distributionBoard.setDistributionBoardProjectId("CurrentPhysicalLayer");
		distributionBoard.setDistributionBoardRowsNum(totalPorts < 24 ? 1f : totalPorts / 24f);
		distributionBoard.setDistributionBoardColsNum((float) Math.min(totalPorts, 24));
		distributionBoard.setDistributionBoardFront(0f);
		distributionBoard.setDistributionBoardBack(0f);
		distributionBoard.setDistributionBoardCapacity((float) totalPorts);
		if (totalPorts <= 24)
			rowPerModule = 1;
		else if (totalPorts == 48 || totalPorts == 96)
			rowPerModule = 2;
		else if (totalPorts == 72)
			rowPerModule = 3;

		distributionBoard.setRowPerModule(rowPerModule);
		distributionBoard.setRowCounting("Down To Up");
		session.saveOrUpdate(distributionBoard);

		for (Map<String, Object> kit : newKits) {
			ControllerKit panelKit = new ControllerKit();
			seq = ((Number) session.createNativeQuery("SELECT KIT_SEQ.NEXTVAL FROM DUAL").uniqueResult()).intValue();
			kitID = "KIT_" + year + "_" + seq;
			System.out.println("kitID is " +kitID);
			panelKit.setKitId(kitID);
			panelKit.setDbId(dbID);
			panelKit.setKitSerialNum(kit.get("kitId").toString());
			panelKit.setKitType(kit.get("kitType").toString());
			panelKit.setCreateDate(creationDate);
			panelKit.setLastModifiedDate(creationDate);
			session.saveOrUpdate(panelKit);
			modules = (List<Map<String, Object>>) kit.get("modules");
			for (Map<String, Object> module : modules) {
				ControllerModule kitModule = new ControllerModule();
				seq = ((Number) session.createNativeQuery("SELECT MODULE_SEQ.NEXTVAL FROM DUAL").uniqueResult()).intValue();
				moduleID = "KIT_" + year + "_" + seq;
				System.out.println("moduleID is " +moduleID);
				kitModule.setModuleId(moduleID);
				kitModule.setDbId(dbID);
				kitModule.setKitSerialNum(kit.get("kitId").toString());
				kitModule.setCreateDate(creationDate);
				kitModule.setLastModifiedDate(creationDate);
				kitModule.setModulePosition(module.get("position").toString());
				kitModule.setOrientation(module.get("orientation").toString());
				kitModule.setLowestPortNum(module.get("lowestPortNumber").toString());
				kitModule.setSensorsPerPortNum(module.get("sensorsPerPortNumber").toString());
				kitModule.setSensorCount(module.get("sensorCount").toString());
				kitModule.setOccupiedSensorMask(module.get("occupiedSensorMask").toString());
				session.saveOrUpdate(kitModule);
			} // End for loop for modules.
		} // End for loop for kits.
	}

	public void patches(String token, String ipAddress, String rackID) {

	}

	public void incompletePatches(String token, String ipAddress, String rackID) {

	}

	@SuppressWarnings("unchecked")
	public void networkInterface(String token, String ipAddress) {
		System.out.println("Welcome to networkInterface");
		Map<String, Object> rtn = new LinkedHashMap<>();
		Map<String, Object> rtnBody = new LinkedHashMap<>();
		// Map<String, Object> rtnStaticIpSettings = new LinkedHashMap<>();
		Map<String, Object> rtnIPv4Settings = new LinkedHashMap<>();
		rtn = commscopeService.getNetworkInterfaceAPI(token, ipAddress);		
		try {
			System.out.println("rtn from getNetworkInterfaceAPI is " +mapper.writeValueAsString(rtn));
			if (rtn.containsKey("responseBody")
					&& !StringUtils.equalsIgnoreCase(rtn.get("status").toString(), "Failed")) {
				rtnBody = (Map<String, Object>) rtn.get("responseBody");
				cntrlRecord.put("mac_address", rtnBody.get("mac").toString());
				cntrlRecord.put("controller_name", rtnBody.get("hostName").toString());
				rtnIPv4Settings = (Map<String, Object>) ((Map<String, Object>) rtnBody.get("staticIpSettings"))
						.get("ipv4Settings");
				cntrlRecord.put("subnet_mask", rtnIPv4Settings.get("ipv4SubnetMask").toString());
				cntrlRecord.put("default_gateway", rtnIPv4Settings.get("ipv4Gateway").toString());
				System.out.println("cntrlRecord at networkInterface method is " +mapper.writeValueAsString(cntrlRecord));
			}
		} catch (Exception e) {
			logger.info(
					"Error in networkInterface method of class SnglCmCntrl which call networkInterfaceAPI with a message : "
							+ e + "\n\" + e.getMessage()",
					e);
		}
	}

	public void portStatusAPI(String token, String ipAddress, String rackID, String kitID, String moduleID,
			String portID) {

	}

	public void eventNoteAPI(String token, String ipAddress, String eventID, String timeout) {

	}

	public void setDateTimeAPI(String token, String ipAddress, String dateTime) {

	}

	public void setCurrentDateTimeAPI(String token, String ipAddress) {

	}

	public void genWorkOrder(String token, String ipAddress, List<Map<String, Object>> woDetails) {

	}

	public void getWorkOrderAPI(String token, String ipAddress, int workOrderTaskId) {

	}

	public void listWorkOrderAPI(String token, String ipAddress) {

	}

	public void deleteWorkOrderAPI(String token, String ipAddress, int workOrderTaskId) {

	}

	public void deleteAllWorkOrderAPI(String token, String ipAddress) {

	}
}