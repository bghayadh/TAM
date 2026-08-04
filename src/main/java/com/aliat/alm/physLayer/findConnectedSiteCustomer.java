package com.aliat.alm.physLayer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.GetSystemSettings;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.ModuleField;
import com.aliat.alm.models.PhysicalLayerActivity;
import com.aliat.alm.services.LoginServices;
import com.aliat.alm.utils.RequestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class findConnectedSiteCustomer {
	private final Logger logger = Logger.getLogger(findConnectedSiteCustomer.class.getName());

	@Autowired
	Notify notifications;
	@Autowired
	Permissions permissions;
	@Autowired
	GetSystemSettings getSystemSettings;

	private ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/findConnected", method = RequestMethod.GET)
	public String findConnected(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {
		System.out.println("Welcome to findConnected");

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			String originalUrl = request.getRequestURL().toString();
			String queryString = request.getQueryString();
			if (queryString != null) {
				originalUrl += "?" + queryString;
			}
			model.addAttribute("redirectUrl", originalUrl);
			return "Login";
		} else {

			Session session = null;
			Transaction tx = null;
			Query query = null;
			String str;
			physicalCommon phyCommon = new physicalCommon();

			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);

				try {

					PhysicalLayerActivity PhyAct = new PhysicalLayerActivity();
					String ipAddress = RequestUtils.getIpAddress(request);
					String updateModfUser = request.getParameter("updateModfUser");
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(new Date());
					int year = calendar.get(Calendar.YEAR);

					String PhyActID = "PHY_ACT_" + year + "_" + Integer.parseInt(
							session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();

					PhyAct.setPhyActID(PhyActID);
					PhyAct.setScreenName("Physical Layer");
					PhyAct.setUsername(updateModfUser);
					PhyAct.setUserIP(ipAddress);
					PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
					PhyAct.setActivityDescription("Physical Layer Access");
					session.saveOrUpdate(PhyAct);
					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request), "Physical Layer",
							"Tree");
					String searchPopup = ((Integer) model.asMap().get("srchPopupTree")).toString();
					String findConnedted = ((Integer) model.asMap().get("findConnectedTree")).toString();
					String projects = ((Integer) model.asMap().get("projectsTree")).toString();
					model.addAttribute("searchPopup", searchPopup);
					model.addAttribute("findConnedted", findConnedted);
					model.addAttribute("projects", projects);

					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
							"Physical Layer Manhole", "Tree");

					String readManhole = ((Integer) model.asMap().get("readTree")).toString();
					String writeManhole = ((Integer) model.asMap().get("writeTree")).toString();
					String addManhole = ((Integer) model.asMap().get("addTree")).toString();
					String delManhole = ((Integer) model.asMap().get("delTree")).toString();
					String saveManhole = ((Integer) model.asMap().get("saveTree")).toString();
					model.addAttribute("readManhole", readManhole);
					model.addAttribute("writeManhole", writeManhole);
					model.addAttribute("addManhole", addManhole);
					model.addAttribute("saveManhole", saveManhole);
					model.addAttribute("delManhole", delManhole);

					permissions.checkAndAddExceptions(model, readManhole, writeManhole, session,
							"Physical Layer Manhole", request);

					String readExceptionMan = (String) model.asMap().get("readExceptionMan");
					String writeExceptionMan = (String) model.asMap().get("writeExceptionMan");

					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
							"Physical Layer Handhole", "Tree");

					String readHandhole = ((Integer) model.asMap().get("readTree")).toString();
					String writeHandhole = ((Integer) model.asMap().get("writeTree")).toString();
					String addHandhole = ((Integer) model.asMap().get("addTree")).toString();
					String delHandhole = ((Integer) model.asMap().get("delTree")).toString();
					String saveHandhole = ((Integer) model.asMap().get("saveTree")).toString();
					model.addAttribute("readHandhole", readHandhole);
					model.addAttribute("writeHandhole", writeHandhole);
					model.addAttribute("addHandhole", addHandhole);
					model.addAttribute("saveHandhole", saveHandhole);
					model.addAttribute("delHandhole", delHandhole);

					permissions.checkAndAddExceptions(model, readHandhole, writeHandhole, session,
							"Physical Layer Handhole", request);

					String readExceptionHand = (String) model.asMap().get("readExceptionHand");
					String writeExceptionHand = (String) model.asMap().get("writeExceptionHand");

					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
							"Physical Layer Fiber", "Tree");

					String readFiber = ((Integer) model.asMap().get("readTree")).toString();
					String writeFiber = ((Integer) model.asMap().get("writeTree")).toString();
					String addFiber = ((Integer) model.asMap().get("addTree")).toString();
					String delFiber = ((Integer) model.asMap().get("delTree")).toString();
					String saveFiber = ((Integer) model.asMap().get("saveTree")).toString();
					model.addAttribute("readFiber", readFiber);
					model.addAttribute("writeFiber", writeFiber);
					model.addAttribute("addFiber", addFiber);
					model.addAttribute("delFiber", delFiber);
					model.addAttribute("saveFiber", saveFiber);

					permissions.checkAndAddExceptions(model, readFiber, writeFiber, session, "Physical Layer Fiber",
							request);

					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
							"Physical Layer DB", "Tree");

					String readDB = ((Integer) model.asMap().get("readTree")).toString();
					String writeDB = ((Integer) model.asMap().get("writeTree")).toString();
					String addDB = ((Integer) model.asMap().get("addTree")).toString();
					String delDB = ((Integer) model.asMap().get("delTree")).toString();
					String saveDB = ((Integer) model.asMap().get("saveTree")).toString();
					model.addAttribute("readDB", readDB);
					model.addAttribute("writeDB", writeDB);
					model.addAttribute("addDB", addDB);
					model.addAttribute("delDB", delDB);
					model.addAttribute("saveDB", saveDB);

					permissions.checkAndAddExceptions(model, readDB, writeDB, session, "Physical Layer DB", request);

					String readExceptionDB = (String) model.asMap().get("readExceptionDB");
					String writeExceptionDB = (String) model.asMap().get("writeExceptionDB");
					List<String> fiberOwners = new ArrayList<>();
					String hql = "FROM ModuleField WHERE screenTable = :table AND fieldName = :field";
					ModuleField field = (ModuleField) session.createQuery(hql).setParameter("table", "FIBER_CABLES")
							.setParameter("field", "FIBER_OWNER").uniqueResult();

					if (field != null && field.getFieldValues() != null) {
						String json = field.getFieldValues(); // e.g. ["owner 1","owner 2"]
						json = json.replace("[", "").replace("]", "").replace("\"", "");
						for (String val : json.split(",")) {
							fiberOwners.add(val.trim());
						}
					}

					model.addAttribute("fiberOwners", fiberOwners);
					model.addAttribute("writeFiber", 1);

					int filterFlag = 0;
					List<?> projectList = new ArrayList<Object[]>();
					List<Object[]> manholeList = new ArrayList<Object[]>();
					List<Object[]> manholeListPt = new ArrayList<Object[]>();
					List<Object[]> handholeList = new ArrayList<Object[]>();
					List<Object[]> handholeListPt = new ArrayList<Object[]>();
					List<Object[]> fiberList = new ArrayList<Object[]>();
					List<Object[]> fiberListPt = new ArrayList<Object[]>();
					List<Object[]> fiberAuxiliary_Data = new ArrayList<Object[]>();
					List<Object[]> fiberAuxiliary_DataPt = new ArrayList<Object[]>();
					List<Object[]> fiberTubes = new ArrayList<Object[]>();
					List<Object[]> fiberTubesPt = new ArrayList<Object[]>();
					List<Object[]> tubesAuxiliaries = new ArrayList<Object[]>();
					List<Object[]> tubesAuxiliariesPt = new ArrayList<Object[]>();
					List<Object[]> fiberStrands = new ArrayList<Object[]>();
					List<Object[]> fiberStrandsPt = new ArrayList<Object[]>();
					List<Object[]> strandsAuxiliaries = new ArrayList<Object[]>();
					List<Object[]> strandsAuxiliariesPt = new ArrayList<Object[]>();
					List<Object[]> trenchList = new ArrayList<Object[]>();
					List<Object[]> trenchListPt = new ArrayList<Object[]>();
					List<Object[]> trenchAuxiliary_Data = new ArrayList<Object[]>();
					List<Object[]> trenchAuxiliary_DataPt = new ArrayList<Object[]>();
					List<Object[]> junctionManholeList = new ArrayList<Object[]>();
					List<Object[]> junctionManholeListPt = new ArrayList<Object[]>();
					List<Object[]> junctionHandholeList = new ArrayList<Object[]>();
					List<Object[]> junctionHandholeListPt = new ArrayList<Object[]>();
					List<Object[]> distribBoardList = new ArrayList<Object[]>();
					List<Object[]> controllerList = new ArrayList<Object[]>();

					List<Object[]> distribBoardListPt = new ArrayList<Object[]>();
					List<Object[]> ductList = new ArrayList<Object[]>();
					List<Object[]> ductListPt = new ArrayList<Object[]>();
					List<Object[]> ductAuxiliary_Data = new ArrayList<Object[]>();
					List<Object[]> ductAuxiliary_DataPt = new ArrayList<Object[]>();
					List<Object[]> newList = new ArrayList<Object[]>();
					List<Object[]> NodeList = new ArrayList<Object[]>();
					List<String> mhFilteredIDs = new ArrayList<>();
					List<String> hhFilteredIDs = new ArrayList<>();
					List<String> dbFilteredIDs = new ArrayList<>();
					List<String> combinedTubeList = new ArrayList<>();
					List<String> combinedCablesList = new ArrayList<>();
					List<Object[]> tempList = new ArrayList<Object[]>();

					String checkedOption = "all";
					System.out.println("url is " + request.getParameter("Checked"));

					getSystemSettings.getLongLat(session, model);

					System.out.println("find connected");
					filterFlag = 2;
					checkedOption = request.getParameter("Checked");
					String locationType = request.getParameter("locationType");
					System.out.println("locationType is " + locationType);
					String siteId = request.getParameter("siteId").split(":")[0];
					System.out.println("siteId is " + siteId);
					String showPointsType = request.getParameter("getRelatedPoints");

					fiberList = session.createNativeQuery(
							"SELECT distinct SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,FIBER_CABLE_ID,SOURCE_WARE_ID,SOURCE_ID,"
									+ "SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,Tube_Count,Strand_Count,FIBER_CABLE_NAME,PROJECT_ID,"
									+ "SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_TUBES,NUMBER_OF_STRANDS,LENGTH,DRAWING_TYPE,FIBER_NETWORK_LEVEL,FIBER_OWNER,"
									+ "FIBER_CABLE_COLOR FROM (" 
									+ "SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,"
									+ "A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,"
									+ "(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,"
									+ "(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,"
									+ "A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,"
									+ "(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A "
									+ "LEFT  JOIN FIBER_AUXILIARY_POINTS D ON A.FIBER_CABLE_ID=D.FIBER_CABLE_ID "
									+ "LEFT  JOIN FIBER_TUBES B ON A.FIBER_CABLE_ID=B.FIBER_CABLE_ID "
									+ "LEFT  JOIN FIBER_STRANDS C ON A.FIBER_CABLE_ID=C.FIBER_CABLE_ID "
									+ "LEFT  JOIN TUBE_AUXILIARY_POINTS E ON A.FIBER_CABLE_ID=E.FIBER_CABLE_ID "
									+ "LEFT  JOIN STRAND_AUXILIARY_POINTS F ON A.FIBER_CABLE_ID=F.FIBER_CABLE_ID "
									+ "WHERE A.SOURCE_WARE_ID LIKE '%" + siteId + "%' OR A.DESTINATION_WARE_ID LIKE '%"
									+ siteId + "%' OR A.SOURCE_ID LIKE '%" + siteId + "%' OR A.DESTINATION_ID LIKE '%"
									+ siteId + "%' " + "OR D.AUXILIARY_POINT_ID LIKE '%" + siteId
									+ "%' OR B.SOURCE_WARE_ID LIKE '%" + siteId + "%' OR B.DESTINATION_WARE_ID LIKE '%"
									+ siteId + "%' " + "OR C.SOURCE_WARE_ID LIKE '%" + siteId
									+ "%' OR C.DESTINATION_WARE_ID LIKE '%" + siteId
									+ "%' OR E.AUXILIARY_POINT_ID LIKE '%" + siteId + "%' "
									+ "OR F.AUXILIARY_POINT_ID LIKE '%" + siteId + "%' " 
									+ "UNION "
									+ "SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,"
									+ "A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,"
									+ "(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,"
									+ "(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,"
									+ "A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,"
									+ "(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A "
									+ "LEFT  JOIN DISTRIBUTION_BOARD_MAPPING G ON A.FIBER_CABLE_ID=G.FP_FIBER_ID OR A.FIBER_CABLE_ID=G.BP_FIBER_ID "
									+ "WHERE  G.BP_LOCATION_ID LIKE '%" + siteId + "%' OR G.FP_LOCATION_ID LIKE '%"
									+ siteId + "%' " 
									+ "UNION "
									+ "SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,"
									+ "A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,"
									+ "(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,"
									+ "(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,"
									+ "A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,"
									+ "(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A "
									+ "LEFT  JOIN DISTRIBUTION_BOARD D  ON A.SOURCE_ID = D.DB_ID OR A.DESTINATION_ID = D.DB_ID "
									+ "WHERE D.WAREHOUSE LIKE '%" + siteId + "%' " + ")")
							.getResultList();

					System.out.println("fiberList " + mapper.writeValueAsString(fiberList));

					Query fiberAuxiliaryQuery = session.createNativeQuery(
							"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID IN (:param1) ORDER BY B.SEQ_SORTING ASC");
					fiberAuxiliaryQuery.setParameter("param1",
							Arrays.asList((phyCommon.findListId(fiberList, "FiberCable")).length > 0
									? phyCommon.findListId(fiberList, "FiberCable")
									: new String[] { "" }));
					fiberAuxiliary_Data = fiberAuxiliaryQuery.getResultList();

					System.out.println("fiberAuxiliary_Data " + mapper.writeValueAsString(fiberAuxiliary_Data));

					fiberTubes = session.createNativeQuery(
							"SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.DRAWING_TYPE,b.TUBE_NAME,TUBE_NUMBER,TUBE_COLOR FROM FIBER_TUBES b "
									+ "LEFT  JOIN TUBE_AUXILIARY_POINTS a ON a.TUBE_ID=b.TUBE_ID "
									+ "LEFT  JOIN FIBER_STRANDS c ON b.TUBE_ID=c.TUBE_ID "
									+ "LEFT  JOIN STRAND_AUXILIARY_POINTS d ON b.FIBER_CABLE_ID=d.FIBER_CABLE_ID "
									+ "WHERE b.SOURCE_WARE_ID  LIKE '%" + siteId + "%' OR b.DESTINATION_WARE_ID LIKE '%"
									+ siteId + "%' OR a.AUXILIARY_POINT_ID LIKE '%" + siteId
									+ "%' OR c.SOURCE_WARE_ID LIKE '%" + siteId + "%' OR c.DESTINATION_WARE_ID LIKE '%"
									+ siteId + "%' OR d.AUXILIARY_POINT_ID LIKE '%" + siteId + "%' ")
							.getResultList();
					System.out.println("fiberTubessss " + mapper.writeValueAsString(fiberTubes));

					Query tubesAuxiliariesQuery = session.createNativeQuery(
							"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON  b.TUBE_ID=c.TUBE_ID LEFT JOIN FIBER_CABLES a ON a.FIBER_CABLE_ID=b.FIBER_CABLE_ID WHERE c.TUBE_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC");
					tubesAuxiliariesQuery.setParameter("param1",
							Arrays.asList((phyCommon.findListId(fiberTubes, "Tube")).length > 0
									? phyCommon.findListId(fiberTubes, "Tube")
									: new String[] { "" }));
					tubesAuxiliaries = tubesAuxiliariesQuery.getResultList();
					fiberStrands = session.createNativeQuery(
							"SELECT DISTINCT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.DRAWING_TYPE,b.STRAND_NAME,STRAND_NUMBER,STRAND_COLOR FROM FIBER_STRANDS b "
									+ "LEFT  JOIN STRAND_AUXILIARY_POINTS a ON b.STRAND_ID=a.STRAND_ID "
									+ "WHERE b.SOURCE_WARE_ID LIKE '%" + siteId + "%' OR b.DESTINATION_WARE_ID LIKE '%"
									+ siteId + "%' OR a.AUXILIARY_POINT_ID LIKE '%" + siteId + "%' ")
							.getResultList();
					Query strandsAuxiliariesQuery = session.createNativeQuery(
							"SELECT DISTINCT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID AND c.STRAND_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC ");
					strandsAuxiliariesQuery.setParameter("param1",
							Arrays.asList((phyCommon.findListId(fiberStrands, "Strand")).length > 0
									? phyCommon.findListId(fiberStrands, "Strand")
									: new String[] { "" }));
					strandsAuxiliaries = strandsAuxiliariesQuery.getResultList();
					System.out.println("fiber Strandss " + mapper.writeValueAsString(fiberStrands));

					distribBoardList = session.createNativeQuery(
							"SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B  ON B.DB_ID = A.DB_ID  where A.WAREHOUSE LIKE '%"
									+ siteId + "%' OR B.BP_LOCATION_ID LIKE '%" + siteId
									+ "%' OR B.FP_LOCATION_ID LIKE '%" + siteId + "%' ")
							.getResultList();

					System.out.println("distribBoardList " + mapper.writeValueAsString(distribBoardList));

					int distribBoardListSize = distribBoardList.size();
					List<Object[]> nearstPoints = new ArrayList<Object[]>();
					nearstPoints.addAll(distribBoardList);

					String[] idsArray = (phyCommon.findListId(nearstPoints, "all")).length > 0
							? phyCommon.findListId(nearstPoints, "all")
							: new String[] { "" };

					if (showPointsType.equals("1")) {
						System.out.println("showPointsType is: " + showPointsType);
						if (distribBoardList.size() > 0) {
							query = session.createNativeQuery(
									" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID,A.DB_LONGITUDE as DB_LONGITUDE,A.DB_LATITUDE as DB_LATITUDE,A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.FIBER_CABLE_ID IN (:param1)   "
											+ " UNION "
											+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) "
											+ "UNION "
											+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) ) where DB_ID NOT IN (:param2)  ");
							query.setParameter("param1",
									Arrays.asList((phyCommon.findListId(fiberList, "FiberCable")).length > 0
											? phyCommon.findListId(fiberList, "FiberCable")
											: new String[] { "" }));
							query.setParameter("param2", Arrays.asList(idsArray));
							distribBoardList.addAll(query.getResultList());
						} else {
							query = session.createNativeQuery(
									" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID,A.DB_LONGITUDE as DB_LONGITUDE,A.DB_LATITUDE as DB_LATITUDE,A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.FIBER_CABLE_ID IN (:param1)   "
											+ " UNION "
											+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) "
											+ "UNION "
											+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) ) ");
							query.setParameter("param1",
									Arrays.asList((phyCommon.findListId(fiberList, "FiberCable")).length > 0
											? phyCommon.findListId(fiberList, "FiberCable")
											: new String[] { "" }));
							distribBoardList = query.getResultList();
						}
						if (manholeList.size() > 0) {
							Query manholeData = session.createNativeQuery(
									" SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name ,A.LONGITUDE as LONGITUDE,A.LATITUDE as LATITUDE ,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.FIBER_CABLE_ID IN (:param1) "
											+ " UNION "
											+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1) "
											+ " UNION "
											+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1)  ) where manhole_id NOT IN (:param2) ");
							manholeData.setParameter("param1",
									Arrays.asList((phyCommon.findListId(fiberList, "FiberCable")).length > 0
											? phyCommon.findListId(fiberList, "FiberCable")
											: new String[] { "" }));
							manholeData.setParameter("param2", Arrays.asList(idsArray));
							manholeList.addAll(manholeData.getResultList());
						} else {

							Query manholeData = session.createNativeQuery(
									" SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name ,A.LONGITUDE as LONGITUDE,A.LATITUDE as LATITUDE ,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.FIBER_CABLE_ID IN (:param1) "
											+ " UNION "
											+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1) "
											+ " UNION "
											+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1)  )  ");
							manholeData.setParameter("param1",
									Arrays.asList((phyCommon.findListId(fiberList, "FiberCable")).length > 0
											? phyCommon.findListId(fiberList, "FiberCable")
											: new String[] { "" }));
							manholeList = manholeData.getResultList();
						}
						if (handholeList.size() > 0) {
							Query handholeData = session.createNativeQuery(
									" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name,A.LONGITUDE as LONGITUDE ,A.LATITUDE as LATITUDE,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.FIBER_CABLE_ID IN (:param1) "
											+ " UNION "
											+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) "
											+ "UNION"
											+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) ) where handhole_id NOT IN (:param2)  ");
							handholeData.setParameter("param1",
									Arrays.asList((phyCommon.findListId(fiberList, "FiberCable")).length > 0
											? phyCommon.findListId(fiberList, "FiberCable")
											: new String[] { "" }));
							handholeData.setParameter("param2", Arrays.asList(idsArray));
							handholeList.addAll(handholeData.getResultList());
						} else {
							query = session.createNativeQuery(
									" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name,A.LONGITUDE as LONGITUDE ,A.LATITUDE as LATITUDE,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.FIBER_CABLE_ID IN (:param1) "
											+ " UNION "
											+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) "
											+ "UNION"
											+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) )  ");
							query.setParameter("param1",
									Arrays.asList((phyCommon.findListId(fiberList, "FiberCable")).length > 0
											? phyCommon.findListId(fiberList, "FiberCable")
											: new String[] { "" }));
							handholeList = query.getResultList();
						}
					}

					String[] allManIdsPointsArray = (phyCommon.findListId(manholeList, "all")).length > 0
							? phyCommon.findListId(manholeList, "all")
							: new String[] { "A" };
					query = session.createNativeQuery(
							"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id where B.manhole_id in (:param) ");
					junctionManholeList = query.setParameter("param", Arrays.asList(allManIdsPointsArray))
							.getResultList();

					String[] allHandIdsPointsArray = (phyCommon.findListId(handholeList, "all")).length > 0
							? phyCommon.findListId(handholeList, "all")
							: new String[] { "A" };
					query = session.createNativeQuery(
							"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id where b.handhole_id in(:param) ");

					junctionHandholeList = query.setParameter("param", Arrays.asList(allHandIdsPointsArray))
							.getResultList();
					query = session.createNativeQuery(
							"SELECT DISTINCT FP_FIBER_ID ,FP_FIBER_Name, FP_TUBE_NB, FP_TUBE_ID, FP_TUBE_NAME, FP_STRAND_NB, "
									+ "FP_STRAND_ID, FP_STRAND_NAME FROM DISTRIBUTION_BOARD_MAPPING WHERE FP_LOCATION =:param");
					List fpPath = query.setParameter("param", siteId).getResultList();
					query = session.createNativeQuery("\n"
							+ "SELECT DISTINCT BP_FIBER_ID , BP_FIBER_Name, BP_TUBE_NB, BP_TUBE_ID,BP_TUBE_NAME, BP_STRAND_NB, "
							+ "BP_STRAND_ID, BP_STRAND_NAME FROM DISTRIBUTION_BOARD_MAPPING WHERE BP_LOCATION =:param");
					List bpPath = query.setParameter("param", siteId).getResultList();
					NodeList = session.createNativeQuery(
							"SELECT DISTINCT a.NODE_PK, a.NODE_NAME,  a.NODE_TYPE || ':' || a.NODE_NAME AS NODE_INFO,  a.DOMAIN,   a.SITE_ID,"
									+ " a.LONGITUDE, a.LATITUDE, a.NODE_ID, a.SUB_DOMAIN_TYPE FROM NODE_ACTIVE a JOIN NODE_PORT_MAPPING b"
									+ " ON a.NODE_ID = b.NODE_ID "
									+ "WHERE a.SUB_DOMAIN_TYPE IN ('MSAN', 'SDH', 'DWDM', 'GPON', 'SWITCH')"
									+ " AND (a.WARE_ID = :param1 OR b.WARE_ID = :param1)"
									+ " AND a.DOMAIN IN ('Enterprise', 'Transmission')")
							.setParameter("param1", siteId).getResultList();

					List<Object[]> updatedDistribBoardList = new ArrayList<>();

					for (Object[] row : distribBoardList) {
						// Extract DB_ID (index 0)
						String dbId = (String) row[0];

						// Query TYPE for this DB_ID
						String dbType = (String) session
								.createNativeQuery("SELECT DB_TYPE FROM DISTRIBUTION_BOARD WHERE DB_ID = :param1")
								.setParameter("param1", dbId).getSingleResult();
						String controllerId = (String) session
								.createNativeQuery("SELECT CONTROLLER_ID FROM DISTRIBUTION_BOARD WHERE DB_ID = :param1")
								.setParameter("param1", dbId).getSingleResult();

						// Append TYPE at the end of the array
						Object[] newRow = Arrays.copyOf(row, row.length + 2); // +2 for TYPE and CONTROLLER_ID
						newRow[newRow.length - 2] = dbType; // TYPE
						newRow[newRow.length - 1] = controllerId; // CONTROLLER_ID

						updatedDistribBoardList.add(newRow);
					}
					distribBoardList = updatedDistribBoardList;
					List<String> controllerIdsList = new ArrayList<>();

					// Loop through the distribBoardList to get the DB_IDs
					for (Object[] row : distribBoardList) {
						String dbId = (String) row[0]; // Assuming DB_ID is at index 0

						str = "SELECT DISTINCT CONTROLLER_ID " + "FROM DISTRIBUTION_BOARD "
								+ "WHERE CONTROLLER_ID IS NOT NULL " + "AND DB_TYPE = 'active' " + "AND DB_ID = :dbId";

						List<String> result = session.createNativeQuery(str).setParameter("dbId", dbId).getResultList();

						// Add the controller IDs to the controllerIdsList
						if (result != null && !result.isEmpty()) {
							controllerIdsList.addAll(result); // Add the retrieved controller IDs to the list
						}
					}
					System.out.println(mapper.writeValueAsString(controllerIdsList));
					// Convert the controller IDs list to an array if needed
					controllerIdsList = controllerIdsList.stream().distinct().collect(Collectors.toList());

					System.out.println(mapper.writeValueAsString(controllerIdsList));

					// Create query with WHERE condition

					// Loop through each controller ID in the controllerIdsList
					for (String controllerId : controllerIdsList) {
						// Create query for each controller ID
						str = "SELECT C.CONTROLLER_ID, C.LONGITUDE, C.LATITUDE, C.CONTROLLER_NAME, C.NETWORK_LAYER, "
								+ "COUNT(DB.DB_ID) AS DB_COUNT " + "FROM CONTROLLER C "
								+ "LEFT JOIN DISTRIBUTION_BOARD DB ON C.CONTROLLER_ID = DB.CONTROLLER_ID "
								+ "WHERE C.CONTROLLER_ID = :controllerId " + // Use = instead of IN
								"GROUP BY C.CONTROLLER_ID, C.LONGITUDE, C.LATITUDE, C.CONTROLLER_NAME, C.NETWORK_LAYER";

						// Execute the query for the current controller ID
						List<Object[]> result = session.createNativeQuery(str)
								.setParameter("controllerId", controllerId) // Set the current controller ID as
																			// parameter
								.getResultList();

						// Add the result to the controllerDetailsList
						if (result != null && !result.isEmpty()) {
							controllerList.addAll(result); // Add the retrieved results
						}
					}

					// Print the controller details (if needed)
					System.out.println(mapper.writeValueAsString(controllerList));

					model.addAttribute("siteId", request.getParameter("siteId"));
					model.addAttribute("connectedSearchLong", request.getParameter("connectedSearchLong"));
					model.addAttribute("connectedSearchLat", request.getParameter("connectedSearchLat"));
					model.addAttribute("selectConnectedSearch", request.getParameter("selectConnectedSearch"));
					model.addAttribute("connectedViewOnMap", request.getParameter("connectedViewOnMap"));
					model.addAttribute("distribBoardListSize", distribBoardListSize);
					model.addAttribute("getRelatedPoints", showPointsType);
					System.out.println("fpPath is: " + mapper.writeValueAsString(fpPath));
					System.out.println("bpPath is: " + mapper.writeValueAsString(bpPath));
					model.addAttribute("fpPath", mapper.writeValueAsString(fpPath));
					model.addAttribute("bpPath", mapper.writeValueAsString(bpPath));

					/* linkedHashmap instead of HashMap to return values in sequential order */
					LinkedHashMap<String, List<?>> physicalLayerData = new LinkedHashMap<String, List<?>>();

					/* linkedHashmap instead of HashMap to return values */
					LinkedHashMap<String, List<?>> physicalLayerList = new LinkedHashMap<String, List<?>>();

					for (Object[] obj : manholeListPt) {
						if (!(manholeList.contains(obj))) {
							manholeList.add(obj);
						}
					}
					for (Object[] obj : handholeListPt) {
						if (!(handholeList.contains(obj))) {
							handholeList.add(obj);
						}
					}
					for (Object[] obj : distribBoardListPt) {
						if (!(distribBoardList.contains(obj))) {
							distribBoardList.add(obj);
						}
					}
					for (Object[] obj : fiberListPt) {
						if (!(fiberList.contains(obj))) {
							fiberList.add(obj);
						}
					}
					for (Object[] obj : fiberTubesPt) {
						if (!(fiberTubes.contains(obj))) {
							fiberTubes.add(obj);
						}
					}
					for (Object[] obj : tubesAuxiliariesPt) {
						if (!(tubesAuxiliaries.contains(obj))) {
							tubesAuxiliaries.add(obj);
						}
					}
					for (Object[] obj : fiberStrandsPt) {
						if (!(fiberStrands.contains(obj))) {
							fiberStrands.add(obj);
						}
					}
					for (Object[] obj : strandsAuxiliariesPt) {
						if (!(strandsAuxiliaries.contains(obj))) {
							strandsAuxiliaries.add(obj);
						}
					}
					for (Object[] obj : junctionManholeListPt) {
						if (!(junctionManholeList.contains(obj))) {
							junctionManholeList.add(obj);
						}
					}
					for (Object[] obj : junctionHandholeListPt) {
						if (!(junctionHandholeList.contains(obj))) {
							junctionHandholeList.add(obj);
						}
					}
					for (Object[] obj : fiberAuxiliary_DataPt) {
						if (!(fiberAuxiliary_Data.contains(obj))) {
							fiberAuxiliary_Data.add(obj);
						}
					}
					for (Object[] obj : trenchListPt) {
						if (!(trenchList.contains(obj))) {
							trenchList.add(obj);
						}
					}
					for (Object[] obj : trenchAuxiliary_DataPt) {
						if (!(trenchAuxiliary_Data.contains(obj))) {
							trenchAuxiliary_Data.add(obj);
						}
					}
					for (Object[] obj : ductListPt) {
						if (!(ductList.contains(obj))) {
							ductList.add(obj);
						}
					}
					for (Object[] obj : ductAuxiliary_DataPt) {
						if (!(ductAuxiliary_Data.contains(obj))) {
							ductAuxiliary_Data.add(obj);
						}
					}
					// in sequential order

					// HashMap<String,List<?>> hash_map = new HashMap<String, List<?>>();
					physicalLayerData.clear();
					physicalLayerList.clear();
					physicalLayerList.put("Project", projectList);
					physicalLayerList.put("Junction_Manhole", junctionManholeList);
					physicalLayerList.put("Manhole", manholeList);
					physicalLayerList.put("Junction_Handhole", junctionHandholeList);
					physicalLayerList.put("Handhole", handholeList);
					physicalLayerList.put("fiber", fiberList);
					physicalLayerList.put("Distribution_Board", distribBoardList);
					physicalLayerList.put("controllerList", controllerList);

					physicalLayerList.put("Trench", trenchList);
					physicalLayerList.put("Node", NodeList);
					physicalLayerList.put("duct", ductList);
					physicalLayerData.put("trench_Auxiliary", trenchAuxiliary_Data);
					physicalLayerData.put("strands_Auxiliaries", strandsAuxiliaries);
					physicalLayerData.put("fiber_Strands", fiberStrands);
					physicalLayerData.put("tubes_Auxiliaries", tubesAuxiliaries);
					physicalLayerData.put("fiber_Tubes", fiberTubes);
					physicalLayerData.put("fiber_Auxiliary", fiberAuxiliary_Data);
					physicalLayerData.put("ductAuxiliary", ductAuxiliary_Data);

					model.addAttribute("physicalLayerList", mapper.writeValueAsString(physicalLayerList));
					model.addAttribute("physicalLayerData", mapper.writeValueAsString(physicalLayerData));
					model.addAttribute("filterFlag", filterFlag);
					model.addAttribute("checkedOption", checkedOption);
					session.flush();
					session.clear();
					tx.commit();
				} catch (Exception e) {
					logger.log(Level.SEVERE, "Error in findConnectedSiteCustomer due to ", e);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
		}

		return "Network/NetworkPhysicalLayer";
	}
}
