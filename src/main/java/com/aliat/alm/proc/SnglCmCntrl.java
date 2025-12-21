package com.aliat.alm.proc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.aliat.alm.models.PanelKit;
import com.aliat.alm.models.PanelModule;
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
	private int totalPorts = 0, totalPanelPorts = 0, totalPanelModules = 0;
	private List<String> kitNeedCheck = new ArrayList<>();
	private List<Map<String, Object>> newPanelKits = new ArrayList<>();
	private List<String> dbID_NeedUpdate = new ArrayList<>();
	private List<String> kits = new ArrayList<>();
	private Map<String, List<Object>> kitsInfo = new LinkedHashMap<>();
	private List<Object> kitInfo = new ArrayList<>();
	private Map<String, List<String>> panelKit = new LinkedHashMap<>();
	private Map<String, Integer> panelTotalPorts = new LinkedHashMap<>();
	private List<String> dbIDs = new ArrayList<>();
	private Map<String, Map<String, Integer>> kitModule = new LinkedHashMap<>();
	private List<Map<String, Object>> patches = new ArrayList<>();
	private ObjectMapper mapper = new ObjectMapper();
	private final Logger logger = LoggerFactory.getLogger(SnglCmCntrl.class);

	public void login(String controllerID, String ipAddress, String username, String password, int requestedDuration,
			String serialNo, Session session) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		init(controllerID);

		try {
			rtn = commscopeService.loginAPI(ipAddress, username, password, requestedDuration);
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
			if (rtn.containsKey("responseBody")
					&& !StringUtils.equalsIgnoreCase(rtn.get("status").toString(), "Failed")) {
				rtnBody = (Map<String, Object>) rtn.get("responseBody");
				cntrlRecord.put("serial_numb", rtnBody.get("networkManagerId").toString());
				if (StringUtils.isBlank(serialNo) || "null".equalsIgnoreCase(serialNo)
						|| StringUtils.equalsIgnoreCase(serialNo, rtnBody.get("networkManagerId").toString())) {
					cntrlRecord.put("controller_id", controllerID);
					cntrlRecord.put("status", "Reachable");
				} else {
					session.createNativeQuery(
							"update controller set status = 'Not Reachable', last_scan_date = sysdate where serial_numb =:serialNo")
							.setParameter("serialNo", serialNo).executeUpdate();
					List<Object[]> cntrlModifyedIP = session.createNativeQuery(
							"select controller_id from controller where serial_numb =:serialNo order by last_modified_date desc")
							.setParameter("serialNo", rtnBody.get("networkManagerId").toString()).list();
					cntrlRecord.put("controller_id", cntrlModifyedIP.get(0)[0].toString());
				}
				networkInterface(token, ipAddress);
				panel(cntrlRecord.get("controller_id"), token, ipAddress, cntrlRecord.get("serial_numb"), session);
				/*
				 * cntrl.setControllerId(cntrlRecord.get("controller_id"));
				 * cntrl.setSerialNumber(cntrlRecord.get("serial_numb"));
				 * cntrl.setMacAddress(cntrlRecord.get("mac_address"));
				 */

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
					str = "update distribution_board set DB_TYPE = 'passive', controller_id = null where DB_ID in (select DB_ID from panel_kit "
							+ "where kit_serial_num in (:serial_num))";
					session.createNativeQuery(str).setParameterList("serial_num", kitNeedCheck).executeUpdate();
				}
				portsTreatment(token, ipAddress, cntrlRecord.get("controller_id"), session);
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
			str = "select kit_serial_num from panel_kit where db_id in (select db_id from distribution_board where controller_id ='"
					+ controllerID + "')";
			kitNeedCheck = session.createNativeQuery(str).getResultList();
			rtn = commscopeService.getPanelAPI(token, ipAddress, rackID);
			if (rtn.containsKey("responseBody")
					&& !StringUtils.equalsIgnoreCase(rtn.get("status").toString(), "Failed")) {
				Map<String, Object> rtnBody = new LinkedHashMap<>();
				rtnBody = (Map<String, Object>) rtn.get("responseBody");
				if (rtnBody.containsKey("panels")) {
					panelList = (List<Map<String, Object>>) rtnBody.get("panels");
					for (Map<String, Object> panel : panelList) {
						newPanelKits = new ArrayList<>();
						dbIDs = new ArrayList<>();
						totalPanelPorts = 0;
						if ((Integer) (panel.get("panelNumber")) != null && (Integer) (panel.get("panelNumber")) > 0) {
							panelCount++;
							kits = new ArrayList<>();
							kitsTreatment((List<Map<String, Object>>) panel.get("kits"), session);
							// 1. SELECT controller location info
							String str = "select site, site_name, warehouse, latitude, longitude from controller where controller_id = :id";

							List<Object[]> cntrlLocation = session.createNativeQuery(str)
									.setParameter("id", controllerID).list();

							Object[] row = cntrlLocation.get(0);

							// Safely convert nullable fields
							String site = (row[0] == null ? null : row[0].toString());
							String siteName = (row[1] == null ? null : row[1].toString());
							String warehouse = (row[2] == null ? null : row[2].toString());
							String latitude = (row[3] == null ? null : row[3].toString());
							String longitude = (row[4] == null ? null : row[4].toString());

							// 2. UPDATE distribution_board
							str = "update distribution_board " + "set controller_id = :id, " + "DB_TYPE = 'active', "
									+ "site = :site, " + "    site_name = :siteName, "
									+ "warehouse = :warehouse, db_latitude = :latitude, db_longitude = :longitude where db_id in (:dbIds)";

							session.createNativeQuery(str).setParameter("id", controllerID).setParameter("site", site)
									.setParameter("siteName", siteName).setParameter("warehouse", warehouse)
									.setParameter("latitude", latitude).setParameter("longitude", longitude)
									.setParameterList("dbIds", dbID_NeedUpdate).executeUpdate();

							if (newPanelKits.size() > 0) {
								insertPanel(controllerID, newPanelKits, session);
							}
							panelKit.put(dbIDs.get(0), kits);
							panelTotalPorts.put(dbIDs.get(0), totalPanelPorts);
						}
					}
				}
			}
			cntrlRecord.put("num_of_panels", Integer.toString(panelCount));
			cntrlRecord.put("numb_of_ports", Integer.toString(totalPorts));
		} catch (Exception e) {
			logger.info("Error in panel method of class SnglCmCntrl which call panelAPI with a message : " + e
					+ "\n\" + e.getMessage()", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void kitsTreatment(List<Map<String, Object>> kitList, Session session) throws JsonProcessingException {
		List<Map<String, Object>> modules = new ArrayList<>();
		Map<String, Integer> moduleNumber = new LinkedHashMap<>();
		int i = 0, totalKitPorts = 0;
		for (Map<String, Object> kit : kitList) {
			totalKitPorts = 0;
			kitInfo = new ArrayList<>();
			moduleNumber = new LinkedHashMap<>();
			if (kitNeedCheck.contains(kit.get("kitId").toString())) { // Checking kits that currently assigned to the
																		// controller.
				kitNeedCheck.remove(kitNeedCheck.get(kitNeedCheck.indexOf(kit.get("kitId").toString())));
				kits.add(kit.get("kitId").toString());
				str = "select db_id from panel_kit where KIT_SERIAL_NUM = :serial_num";
				dbIDs = session.createNativeQuery(str).setParameter("serial_num", kit.get("kitId").toString())
						.getResultList();
				if (!dbID_NeedUpdate.contains(dbIDs.get(0))) {
					dbID_NeedUpdate.add(dbIDs.get(0).toString());
				}

				kitInfo.add(dbIDs.get(0));
				modules = (List<Map<String, Object>>) kit.get("modules");
				for (Map<String, Object> module : modules) {
					totalPorts += Integer.parseInt(module.get("sensorCount").toString());
					totalPanelPorts += Integer.parseInt(module.get("sensorCount").toString());
					totalKitPorts += Integer.parseInt(module.get("sensorCount").toString());
					moduleNumber.put(module.get("position").toString(), i + 1);
					i++;
				}
				kitInfo.add(totalKitPorts);
				kitsInfo.put(kit.get("kitId").toString(), kitInfo);
				kitModule.put(kit.get("kitId").toString(), moduleNumber);
			} else { // Checking if the kits are existed with panel assigned to other controller.
				List<String> otherDbID = new ArrayList<>();
				str = "select db_id from panel_kit where kit_serial_num = '" + kit.get("kitId").toString() + "'";
				otherDbID = session.createNativeQuery(str).list();
				dbIDs = otherDbID;
				if (otherDbID.size() > 0) {
					kitInfo.add(otherDbID.get(0));
					modules = (List<Map<String, Object>>) kit.get("modules");
					for (Map<String, Object> module : modules) {
						totalPorts += Integer.parseInt(module.get("sensorCount").toString());
						totalPanelPorts += Integer.parseInt(module.get("sensorCount").toString());
						totalKitPorts += Integer.parseInt(module.get("sensorCount").toString());
						moduleNumber.put(module.get("position").toString(), i + 1);
						i++;
					}
					kitInfo.add(totalKitPorts);
					kitsInfo.put(kit.get("kitId").toString(), kitInfo);
					kitModule.put(kit.get("kitId").toString(), moduleNumber);
					if (!dbID_NeedUpdate.contains(otherDbID.get(0))) {
						dbID_NeedUpdate.add(otherDbID.get(0).toString());
					}
				} else { // Case the kit is new.
					modules = (List<Map<String, Object>>) kit.get("modules");
					for (Map<String, Object> module : modules) {
						totalPorts += Integer.parseInt(module.get("sensorCount").toString());
						totalPanelPorts += Integer.parseInt(module.get("sensorCount").toString());
						totalKitPorts += Integer.parseInt(module.get("sensorCount").toString());
						moduleNumber.put(module.get("position").toString(), i + 1);
						i++;
					}
					newPanelKits.add(kit);
				}
			}
		}
		totalPanelModules = i;
	}

	@SuppressWarnings("unchecked")
	public void insertPanel(String controllerID, List<Map<String, Object>> newKits, Session session)
			throws JsonProcessingException {
		String dbID = "", kitID = "", moduleID = "";
		int rowPerModule = 0, seq = 0;
		DistributionBoard distributionBoard = new DistributionBoard();
		Timestamp creationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		ControllerPanel cntrl = new ControllerPanel();
		List<Map<String, Object>> modules = new ArrayList<>();
		cntrl = session.get(ControllerPanel.class, controllerID);

		seq = ((Number) session.createNativeQuery("SELECT DB_SEQ.NEXTVAL FROM DUAL").uniqueResult()).intValue();
		dbID = "DB_" + year + "_" + seq;

		dbIDs.add(dbID);
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
		distributionBoard.setDistributionBoardProjectId("CurrentPhysicalLayer");
		distributionBoard.setDistributionBoardRowsNum(totalPanelPorts < 24 ? 1f : totalPanelPorts / 24f);
		distributionBoard.setDistributionBoardColsNum((float) Math.min(totalPanelPorts, 24));
		distributionBoard.setDistributionBoardFront(0f);
		distributionBoard.setDistributionBoardBack(0f);
		distributionBoard.setDistributionBoardCapacity((float) totalPanelPorts);
		if (totalPanelPorts <= 24)
			rowPerModule = 1;
		else if (totalPanelPorts == 48 || totalPanelPorts == 96)
			rowPerModule = 2;
		else if (totalPanelPorts == 72)
			rowPerModule = 3;

		distributionBoard.setRowPerModule(rowPerModule);
		distributionBoard.setTotalNumModule(totalPanelModules);
		distributionBoard.setRowCounting("Down To Up");
		session.saveOrUpdate(distributionBoard);
		session.flush();

		for (Map<String, Object> kit : newKits) {
			PanelKit panelKit = new PanelKit();
			seq = ((Number) session.createNativeQuery("SELECT KIT_SEQ.NEXTVAL FROM DUAL").uniqueResult()).intValue();
			kitID = "KIT_" + year + "_" + seq;
			panelKit.setKitId(kitID);
			panelKit.setDbId(dbID);
			panelKit.setKitSerialNum(kit.get("kitId").toString());
			panelKit.setKitType(kit.get("kitType").toString());
			panelKit.setCreateDate(creationDate);
			panelKit.setLastModifiedDate(creationDate);
			session.saveOrUpdate(panelKit);
			modules = (List<Map<String, Object>>) kit.get("modules");
			for (Map<String, Object> module : modules) {
				PanelModule kitModule = new PanelModule();
				seq = ((Number) session.createNativeQuery("SELECT MODULE_SEQ.NEXTVAL FROM DUAL").uniqueResult())
						.intValue();
				moduleID = "KIT_" + year + "_" + seq;
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
		session.flush();
	}

	@SuppressWarnings("unchecked")
	public void portsTreatment(String token, String ipAddress, String ID, Session session) {
		List<Object[]> panelsInfo = new ArrayList<>();
		List<Object[]> dbPorts = new ArrayList<>();
		Map<String, Object[]> portsMap = new HashMap<>();
		List<Object[]> panelModules = new ArrayList<>();
		Map<String, Map<String, Object>> patchMap = new HashMap<>();
		Map<String, Object> rtn = new LinkedHashMap<>();
		Map<String, Object> rtnBody = new LinkedHashMap<>();
		int seq = 0, portIndex = 0, portsPerModule = 0, rowsPerModule = 0, colPerModule = 0, rowModuleNum = 0,
				colModuleNum = 0, dbPortCount = 0, dbPortNum, dbPortIndex;
		String dbPortID = "";
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);

		try {
			str = "SELECT DB_ID, NUM_ROWS, NUM_COLUMNS, MAX_CAPACITY FROM DISTRIBUTION_BOARD WHERE CONTROLLER_ID = '"
					+ ID + "'";
			panelsInfo = session.createNativeQuery(str).list();
			str = " select serial_numb from controller where controller_id ='" + ID + "'"; 
			rtn = commscopeService.patchesAPI(token, ipAddress, session.createNativeQuery(str).uniqueResult().toString());
			if (rtn.containsKey("responseBody")
					&& !StringUtils.equalsIgnoreCase(rtn.get("status").toString(), "Failed")) {
				rtnBody = (Map<String, Object>) rtn.get("responseBody");
				if (rtnBody.containsKey("patches")) {
					patches = (List<Map<String, Object>>) rtnBody.get("patches");
					// Build patch lookup map

					for (Map<String, Object> p : patches) {
						// Start side:
						String sm = p.get("sModule") != null ? p.get("sModule").toString() : "null";
						String sp = p.get("sPort") != null ? p.get("sPort").toString() : null;

						if (p.get("sKitId") != null && sp != null) {
							String keyStart = p.get("sKitId") + "-" + sm + "-" + sp;
							patchMap.put(keyStart, p);
						}
					}
				}
			}			

			for (Object[] panel_Info : panelsInfo) {
				portIndex = 0;
				str = "SELECT DB_PORT_ID, DB_ID, ROW_COL_INDEX, ROW_NUMBER, COLUMN_NUMBER, NEAR_MODULE, NEAR_PORT_NUM, NEAR_PATCH_TYPE, "
						+ "FAR_NEAR_KIT_SERIAL_NUM, FAR_NEAR_MODULE FROM DISTRIBUTION_BOARD_MAPPING WHERE DB_ID = '"
						+ panel_Info[0].toString() + "'";
				dbPorts = session.createNativeQuery(str).getResultList();
				str = "SELECT ROW_NUMBER() OVER (ORDER BY MODULE_POSITION ASC) AS ROW_NUM, MODULE_ID, MODULE_POSITION, KIT_SERIAL_NUM, SENSOR_COUNT FROM PANEL_MODULE WHERE DB_ID = '"
						+ panel_Info[0].toString() + "' ORDER BY MODULE_POSITION ASC";
				panelModules = session.createNativeQuery(str).list();
				dbPortCount = Integer.parseInt(panel_Info[3].toString());
				if (dbPortCount <= 24) {
					rowsPerModule = 1;
					colPerModule = dbPortCount / panelModules.size();
					portsPerModule = dbPortCount / panelModules.size();
				} else if (dbPortCount == 48 || dbPortCount == 96) {
					rowsPerModule = 2;
					colPerModule = 6;
					portsPerModule = dbPortCount / panelModules.size();
				} else if (dbPortCount == 72) {
					rowsPerModule = 3;
					colPerModule = 8;
					portsPerModule = dbPortCount / panelModules.size();
				}

				// Build map for fast lookup
				portsMap = new HashMap<>();
				for (Object[] dbPort : dbPorts) {
					String key = dbPort[5] + "-" + dbPort[6];
					portsMap.put(key, dbPort);
				}
				int j = 0;
				for (Object[] panelModule : panelModules) {
					int modulePortCount = Integer.parseInt(panelModule[4].toString());
					for (int i = 0; i < modulePortCount; i++) {
						if (i < colPerModule) {
							rowModuleNum = 1;
							colModuleNum = (i + 1) + j * colPerModule;
						} else if (i < (2 * colPerModule)) {
							rowModuleNum = 2;
							colModuleNum = (i + 1) - colPerModule + (j * colPerModule);
						} else {
							rowModuleNum = 3;
							colModuleNum = (i + 1) - 2 * colPerModule + (j * colPerModule);
						}
						String key = panelModule[2] + "-" + (i + 1);
						Object[] matchedDbPort = portsMap.get(key);						
						key = panelModule[3] + "-" + key;
						Map<String, Object> matchedPatchPort = patchMap.get(key);
						portIndex = (i + 1) + (Integer.parseInt(panelModule[0].toString()) - 1) * modulePortCount;
						str = "update distribution_board_mapping set ROW_COL_INDEX = :portIndex, ROW_NUMBER = :rowNum, COLUMN_NUMBER = :colNum,"
								+ " NEAR_MODULE = :nearModule, NEAR_PORT_NUM = :nearPortNum, fp_status = :fpStatus";

						String equipment = "";

						if (matchedDbPort != null) {
							// Found matching port and will use matchedPatchPort
							if (matchedPatchPort != null) {
								//str = str + ", near_patch_type = :nearPatchType, fp_equipment = :fpEquipment";
								str = str + ", near_patch_type = :nearPatchType";
								if (matchedPatchPort.containsKey("eEquipment")) {
									//equipment = "Custom";
									str = str + " where DB_PORT_ID = :dbPortID";
									session.createNativeQuery(str).setParameter("portIndex", portIndex)
											.setParameter("rowNum", rowModuleNum).setParameter("colNum", colModuleNum)
											.setParameter("nearModule", panelModule[2])
											.setParameter("nearPortNum", i + 1)
											.setParameter("nearPatchType", matchedPatchPort.get("type"))
											.setParameter("fpStatus", "Connected")
											//.setParameter("fpEquipment", equipment)
											.setParameter("dbPortID", matchedDbPort[0].toString()).executeUpdate();
								} else {
									equipment = "DistBoard";
									str = str + ", fp_equipment = :fpEquipment, FAR_NEAR_KIT_SERIAL_NUM = :farNearKitSerialNum, "
										+ "FAR_NEAR_MODULE = :farNearModule, FAR_NEAR_PORT_NUM = :farNearPortNum where DB_PORT_ID = :dbPortID";
									session.createNativeQuery(str).setParameter("portIndex", portIndex)
											.setParameter("rowNum", rowModuleNum).setParameter("colNum", colModuleNum)
											.setParameter("nearModule", panelModule[2])
											.setParameter("nearPortNum", i + 1)
											.setParameter("nearPatchType", matchedPatchPort.get("type"))
											.setParameter("fpStatus", "Connected")
											.setParameter("fpEquipment", equipment)
											.setParameter("farNearKitSerialNum", matchedPatchPort.get("eKitId"))
											.setParameter("farNearModule", matchedPatchPort.get("eModule"))
											.setParameter("farNearPortNum", matchedPatchPort.get("ePort"))
											.setParameter("dbPortID", matchedDbPort[0].toString()).executeUpdate();
								}
							} else {
								str = str + " where DB_PORT_ID = :dbPortID";
								session.createNativeQuery(str).setParameter("portIndex", portIndex)
										.setParameter("rowNum", rowModuleNum).setParameter("colNum", colModuleNum)
										.setParameter("nearModule", panelModule[2]).setParameter("nearPortNum", i + 1)
										.setParameter("fpStatus", "Disconnected")
										.setParameter("dbPortID", matchedDbPort[0].toString()).executeUpdate();
							}
						} else {
							// Insert new port and will use matchedPatchPort
							seq = ((Number) session.createNativeQuery("SELECT DB_PORT_SEQ.NEXTVAL FROM DUAL")
									.uniqueResult()).intValue();
							dbPortID = "DB_PORT_" + year + "_" + seq;
							str = "insert into distribution_board_mapping (db_port_id, db_id, row_col_index, row_number, column_number, "
									+ "near_module, near_port_num, fp_status";

							if (matchedPatchPort != null) { // Case: new port and has patch
								//str = str + ", near_patch_type, fp_equipment";
								str = str + ", near_patch_type";
								if (matchedPatchPort.containsKey("eEquipment")) { // The patch of type that is connected to equipment.
									//equipment = "Custom";
									str = str
											+ ") values (:dbPortID, :dbID, :portIndex, :rowNum, :colNum, :nearModule, :nearPortNum, "
											//+ ":fpStatus, :nearPatchType, :fpEquipment)";
											+ ":fpStatus, :nearPatchType)";									

									session.createNativeQuery(str).setParameter("dbPortID", dbPortID)
											.setParameter("dbID", panel_Info[0].toString())
											.setParameter("portIndex", portIndex).setParameter("rowNum", rowModuleNum)
											.setParameter("colNum", colModuleNum)
											.setParameter("nearModule", panelModule[2])
											.setParameter("nearPortNum", i + 1)											
											.setParameter("fpStatus", "Connected")
											.setParameter("nearPatchType", matchedPatchPort.get("type"))
											//.setParameter("fpEquipment", equipment)
											.executeUpdate();
								} else { // Case new port and has patch of type that is connected to another DB
									equipment = "DistBoard";
									str = str + ", fp_equipment, FAR_NEAR_KIT_SERIAL_NUM, FAR_NEAR_MODULE, FAR_NEAR_PORT_NUM)"
											+ " values (:dbPortID, :dbID, :portIndex, :rowNum, :colNum, :nearModule, :nearPortNum, "
											+ ":fpStatus, :nearPatchType, :fpEquipment, :farNearKitSerialNum, :farNearModule, :farNearPortNum)";

									session.createNativeQuery(str).setParameter("dbPortID", dbPortID)
											.setParameter("dbID", panel_Info[0].toString())
											.setParameter("portIndex", portIndex).setParameter("rowNum", rowModuleNum)
											.setParameter("colNum", colModuleNum)
											.setParameter("nearModule", panelModule[2])
											.setParameter("nearPortNum", i + 1)
											.setParameter("fpStatus", "Connected")
											.setParameter("nearPatchType", matchedPatchPort.get("type"))
											.setParameter("fpEquipment", equipment)
											.setParameter("farNearKitSerialNum", matchedPatchPort.get("eKitId"))
											.setParameter("farNearModule", matchedPatchPort.get("eModule"))
											.setParameter("farNearPortNum", matchedPatchPort.get("ePort"))
											.executeUpdate();
								}
							} else { // Case new port to be inserted by no patch
								str = str
										+ ") values (:dbPortID, :dbID, :portIndex, :rowNum, :colNum, :nearModule, :nearPortNum, :fpStatus)";
								session.createNativeQuery(str).setParameter("dbPortID", dbPortID)
										.setParameter("dbID", panel_Info[0].toString())
										.setParameter("portIndex", portIndex).setParameter("rowNum", rowModuleNum)
										.setParameter("colNum", colModuleNum).setParameter("nearModule", panelModule[2])
										.setParameter("nearPortNum", i + 1).setParameter("fpStatus", "Disconnected")
										.executeUpdate();
							}
						}
					}
					j++;
				}
			}
		} catch (Exception e) {
			logger.info("Error in updateOrInsertPorts method of class SnglCmCntrl with a message : " + e
					+ "\n\" + e.getMessage()", e);
		}
	}

	public void insertPorts() {

	}

	@SuppressWarnings("unchecked")
	public void patches(String token, String ipAddress, String rackID) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		Map<String, Object> rtnBody = new LinkedHashMap<>();
		List<Map<String, Object>> patches = new ArrayList<>();
		try {
			rtn = commscopeService.patchesAPI(token, ipAddress, rackID);
			if (rtn.containsKey("responseBody")
					&& !StringUtils.equalsIgnoreCase(rtn.get("status").toString(), "Failed")) {
				rtnBody = (Map<String, Object>) rtn.get("responseBody");
				patches = (List<Map<String, Object>>) rtnBody.get("patches");

				for (Map<String, Object> patch : patches) {

				}
			}

		} catch (Exception e) {
			logger.info("Error in patches method of class SnglCmCntrl which call patchesAPI with a message : " + e
					+ "\n\" + e.getMessage()", e);

		}

	}

	public void incompletePatches(String token, String ipAddress, String rackID) {

	}

	@SuppressWarnings("unchecked")
	public void networkInterface(String token, String ipAddress) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		Map<String, Object> rtnBody = new LinkedHashMap<>();
		Map<String, Object> rtnIPv4Settings = new LinkedHashMap<>();
		try {
			rtn = commscopeService.getNetworkInterfaceAPI(token, ipAddress);
			if (rtn.containsKey("responseBody")
					&& !StringUtils.equalsIgnoreCase(rtn.get("status").toString(), "Failed")) {
				rtnBody = (Map<String, Object>) rtn.get("responseBody");
				cntrlRecord.put("mac_address", rtnBody.get("mac").toString());
				cntrlRecord.put("controller_name", rtnBody.get("hostName").toString());
				rtnIPv4Settings = (Map<String, Object>) ((Map<String, Object>) rtnBody.get("staticIpSettings"))
						.get("ipv4Settings");
				cntrlRecord.put("subnet_mask", rtnIPv4Settings.get("ipv4SubnetMask").toString());
				cntrlRecord.put("default_gateway", rtnIPv4Settings.get("ipv4Gateway").toString());
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

	public void init(String controllerID) {
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
		kits = new ArrayList<>();
		kitsInfo = new LinkedHashMap<>();
		kitInfo = new ArrayList<>();
		panelKit = new LinkedHashMap<>();
		dbIDs = new ArrayList<>();
		kitModule = new LinkedHashMap<>();
	}
}