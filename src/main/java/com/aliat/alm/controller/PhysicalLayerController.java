package com.aliat.alm.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.Properties;


import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
//import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.AccessCableJunction;
import com.aliat.alm.models.AttachmentUpload;
import com.aliat.alm.models.DistributionBoard;
import com.aliat.alm.models.DistributionBoardMapping;
import com.aliat.alm.models.DistributionBoardSurvey;
import com.aliat.alm.models.Duct;
import com.aliat.alm.models.DuctAuxPoints;
import com.aliat.alm.models.FiberAuxPoints;
import com.aliat.alm.models.FiberCable;
import com.aliat.alm.models.FiberCableSurvey;
import com.aliat.alm.models.FiberStrands;
import com.aliat.alm.models.FiberStrandsSurvey;
import com.aliat.alm.models.FiberTubes;
import com.aliat.alm.models.FiberTubesSurvey;
import com.aliat.alm.models.HandholeSurvey;
import com.aliat.alm.models.ManholeSurvey;
import com.aliat.alm.models.NodeActive;
import com.aliat.alm.models.NodeSurvey;
import com.aliat.alm.models.PhysicalLayerActivity;
import com.aliat.alm.models.StrandAuxPoints;
import com.aliat.alm.models.Survey;
import com.aliat.alm.models.Trench;
import com.aliat.alm.models.TrenchAuxPoints;
import com.aliat.alm.models.TubeAuxPoints;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

@Controller
public class PhysicalLayerController {

	private static final Logger logger = Logger.getLogger(PhysicalLayerController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;

//	private EntityManagerFactory emf =null;

//	private EntityManager entityManager=null;

	/*
	 * @Autowired ALMSessions almsessions;
	 */

	@Autowired
	Notify notifications;
	@Autowired
	Permissions permissions;
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/NetworkPhysicalLayer", method = RequestMethod.GET)

	public String NetworkPhysicalLayer(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

	        String originalUrl = request.getRequestURL().toString();
            String queryString = request.getQueryString();
            if (queryString != null) {
                originalUrl += "?" + queryString;
            }
            model.addAttribute("redirectUrl", originalUrl);
            return "Login";
        }
	 else {

			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);

				try {


					PhysicalLayerActivity PhyAct= new PhysicalLayerActivity();
					String ipAddress = getIpAddress(request);
					String updateModfUser=request.getParameter("updateModfUser");
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(new Date());
					int year = calendar.get(Calendar.YEAR);
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

					String PhyActID=
						 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
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
					
						
						
						
						
					 permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
				                "Physical Layer", "Tree");
				       String  searchPopup = ((Integer) model.asMap().get("writeTree")).toString();
				       String  findConnedted = ((Integer) model.asMap().get("addTree")).toString();
				       String  projects = ((Integer) model.asMap().get("saveTree")).toString();
				       model.addAttribute("searchPopup", searchPopup);
					   model.addAttribute("findConnedted", findConnedted);
				       model.addAttribute("projects", projects);
				       
				       permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
				                "Physical Layer Manhole", "Tree");
				    
				       String  readManhole = ((Integer) model.asMap().get("readTree")).toString();
				       String  writeManhole = ((Integer) model.asMap().get("writeTree")).toString();
				       String  addManhole = ((Integer) model.asMap().get("addTree")).toString();
				       String  delManhole = ((Integer) model.asMap().get("delTree")).toString();
				       String  saveManhole = ((Integer) model.asMap().get("saveTree")).toString();
				       model.addAttribute("readManhole", readManhole);
				       model.addAttribute("writeManhole", writeManhole);
				       model.addAttribute("addManhole", addManhole);
				       model.addAttribute("saveManhole", saveManhole);
				       model.addAttribute("delManhole", delManhole);


				       
				       permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
				                "Physical Layer Handhole", "Tree");
				       String  readHandhole = ((Integer) model.asMap().get("readTree")).toString();
				       String  writeHandhole = ((Integer) model.asMap().get("writeTree")).toString();
				       String  addHandhole = ((Integer) model.asMap().get("addTree")).toString();
				       String  delHandhole = ((Integer) model.asMap().get("delTree")).toString();
				       String  saveHandhole = ((Integer) model.asMap().get("saveTree")).toString();
				       model.addAttribute("readHandhole", readHandhole);
				       model.addAttribute("writeHandhole", writeHandhole);
				       model.addAttribute("addHandhole", addHandhole);
				       model.addAttribute("saveHandhole", saveHandhole);
				       model.addAttribute("delHandhole", delHandhole);
				       
				       
				       
				       permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
				                "Physical Layer Fiber", "Tree");
				       
				       String  readFiber = ((Integer) model.asMap().get("readTree")).toString();
				       String  writeFiber = ((Integer) model.asMap().get("writeTree")).toString();
				       String  addFiber = ((Integer) model.asMap().get("addTree")).toString();
				       String  delFiber = ((Integer) model.asMap().get("delTree")).toString();
				       String  saveFiber = ((Integer) model.asMap().get("saveTree")).toString();
				       model.addAttribute("readFiber", readFiber);
				       model.addAttribute("writeFiber", writeFiber);
				       model.addAttribute("addFiber", addFiber);
				       model.addAttribute("delFiber", delFiber);
				       model.addAttribute("saveFiber", saveFiber);
				       
				       
				       permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
				                "Physical Layer DB", "Tree");
				       
				       String  readDB = ((Integer) model.asMap().get("readTree")).toString();
				       String  writeDB = ((Integer) model.asMap().get("writeTree")).toString();
				       String  addDB = ((Integer) model.asMap().get("addTree")).toString();
				       String  delDB = ((Integer) model.asMap().get("delTree")).toString();
				       String  saveDB = ((Integer) model.asMap().get("saveTree")).toString();
				       model.addAttribute("readDB", readDB);
				       model.addAttribute("writeDB", writeDB);
				       model.addAttribute("addDB", addDB);
				       model.addAttribute("delDB", delDB);
				       model.addAttribute("saveDB", saveDB);
				       
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
					List<Object[]> distribBoardListPt = new ArrayList<Object[]>();
					List<Object[]> ductList = new ArrayList<Object[]>();
					List<Object[]> ductListPt = new ArrayList<Object[]>();
					List<Object[]> ductAuxiliary_Data = new ArrayList<Object[]>();
					List<Object[]> ductAuxiliary_DataPt = new ArrayList<Object[]>();
					List<Object[]> newList = new ArrayList<Object[]>();
					List<Object[]> newListPt = new ArrayList<Object[]>();
					List<Object[]> NodeList = new ArrayList<Object[]>();
					List<String> mhFilteredIDs = new ArrayList<>();
				    List<String> hhFilteredIDs = new ArrayList<>();
				    List<String> dbFilteredIDs = new ArrayList<>();							 
				    List<String> combinedTubeList = new ArrayList<>();
				    List<String> combinedCablesList = new ArrayList<>();
					List<Object[]> manholeTempList = new ArrayList<Object[]>();
					List<Object[]> handholeTempList = new ArrayList<Object[]>();
					List<Object[]> dbTempList = new ArrayList<Object[]>();
					List<Object[]> tempList = new ArrayList<Object[]>();
					List<Object[]> tempDataList = new ArrayList<Object[]>();



					// System.out.println("url is "+request.getParameter("selectedField"));
					String checkedOption = "all";
					System.out.println("url is " + request.getParameter("Checked"));
					if (StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "CurrentPhysicalLayer")
							|| StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "PROJECT")) {
						
						filterFlag = 1;
						String showPointsType = request.getParameter("getRelatedPointsFilter");
						if ("1".equals(projects)) {
							projectList = session.createNativeQuery(
									"SELECT DISTINCT PROJECT_ID,PROJECT_NAME, (select count(*) FROM HANDHOLE a WHERE a.PROJECT_ID = b.PROJECT_ID),PROJECT_LAYER  FROM PROJECT b where PROJECT_ID LIKE '%"
											+ request.getParameter("FilteredProject") + "%' OR PROJECT_NAME LIKE '%"
											+ request.getParameter("FilteredProject") + "%' OR PROJECT_ID LIKE '%"
											+ request.getParameter("Checked") + "%'")
									.getResultList();
							}
							if("1".equals(readManhole) ) {
							manholeList = session.createNativeQuery(
									"SELECT DISTINCT A.MANHOLE_ID,A.MANHOLE_NAME,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN JUNCTION B ON B.PHYSICAL_LAYER_ID = A.manhole_id and B.PROJECT_ID LIKE '%"
											+ request.getParameter("Checked") + "%' where A.MANHOLE_ID LIKE '%"
											+ request.getParameter("FilteredManhole") + "%' OR A.MANHOLE_NAME LIKE '%"
											+ request.getParameter("FilteredManhole") + "%' OR B.JUNCTION_ID LIKE '%"
											+ request.getParameter("FilteredJunction_Manhole")
											+ "%' OR B.JUNCTION_NAME LIKE '%"
											+ request.getParameter("FilteredJunction_Manhole")
											+ "%' and A.PROJECT_ID LIKE '%" + request.getParameter("Checked") + "%' ")
									.getResultList();
							
							junctionManholeList = session.createNativeQuery(
									"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A LEFT JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id and (A.JUNCTION_ID LIKE '%"
											+ request.getParameter("FilteredJunction_Manhole")
											+ "%' OR A.JUNCTION_NAME LIKE '%"
											+ request.getParameter("FilteredJunction_Manhole")
											+ "%' OR B.MANHOLE_ID LIKE '%" + request.getParameter("FilteredManhole")
											+ "%' OR B.MANHOLE_NAME LIKE '%" + request.getParameter("FilteredManhole")
											+ "%'  ) and A.PROJECT_ID LIKE '%" + request.getParameter("Checked") + "%'  ")
									.getResultList();
							}
							if("1".equals(readHandhole) ) {
							handholeList = session.createNativeQuery(
									"SELECT DISTINCT A.HANDHOLE_ID,A.HANDHOLE_NAME,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),A.CITY FROM HANDHOLE  A LEFT JOIN JUNCTION B ON B.PHYSICAL_LAYER_ID = A.HANDHOLE_ID and B.PROJECT_ID LIKE '%"
											+ request.getParameter("Checked") + "%' where A.HANDHOLE_ID LIKE '%"
											+ request.getParameter("FilteredHandhole") + "%' OR A.HANDHOLE_NAME LIKE '%"
											+ request.getParameter("FilteredHandhole") + "%' OR B.JUNCTION_ID LIKE '%"
											+ request.getParameter("FilteredJunction_Handhole")
											+ "%' OR B.JUNCTION_NAME LIKE '%"
											+ request.getParameter("FilteredJunction_Handhole")
											+ "%' and A.PROJECT_ID LIKE '%" + request.getParameter("Checked") + "%' ")
									.getResultList();

							junctionHandholeList = session.createNativeQuery(
									"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id and (A.JUNCTION_ID LIKE '%"
											+ request.getParameter("FilteredJunction_Handhole")
											+ "%' OR A.JUNCTION_NAME LIKE '%"
											+ request.getParameter("FilteredJunction_Handhole")
											+ "%' OR HANDHOLE_ID LIKE '%" + request.getParameter("FilteredHandhole")
											+ "%' OR HANDHOLE_NAME LIKE '%" + request.getParameter("FilteredHandhole")
											+ "%' ) and A.PROJECT_ID LIKE '%" + request.getParameter("Checked") + "%' ")
									.getResultList();
							}
						distribBoardList = session.createNativeQuery(
								"SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY,DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD where DB_ID LIKE '%"
										+ request.getParameter("FilteredDistribution_Board") + "%' OR DB_NAME LIKE '%"
										+ request.getParameter("FilteredDistribution_Board")
										+ "%' and PROJECT_ID LIKE '%" + request.getParameter("Checked") + "%' ")
								.getResultList();
						if("1".equals(readFiber) ) {
						fiberList = session.createNativeQuery(
								"SELECT SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID),(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID),FIBER_CABLE_NAME,PROJECT_ID,SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_TUBES,NUMBER_OF_STRANDS,LENGTH,DRAWING_TYPE,FIBER_NETWORK_LEVEL,FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A where FIBER_CABLE_ID LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' OR FIBER_CABLE_NAME LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' and PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ")
								.getResultList();

						fiberAuxiliary_Data = session.createNativeQuery(
								"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID and (A.FIBER_CABLE_ID LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' OR A.FIBER_CABLE_NAME LIKE '%"
										+ request.getParameter("Filteredfiber") + "%') and A.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ORDER BY B.SEQ_SORTING ASC")
								.getResultList();

						fiberTubes = session.createNativeQuery(
								"SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER,b.TUBE_COLOR FROM FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and a.FIBER_CABLE_ID LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' OR a.FIBER_CABLE_NAME LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' and a.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%'")
								.getResultList();
						System.out.println("fbbbb >>" + mapper.writeValueAsString(fiberTubes));
						tubesAuxiliaries = session.createNativeQuery(
								"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c,FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.TUBE_ID=c.TUBE_ID and a.FIBER_CABLE_ID LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' OR a.FIBER_CABLE_NAME LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' and a.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ORDER BY c.SEQ_SORTING ASC")
								.getResultList();

						fiberStrands = session.createNativeQuery(
								"SELECT DISTINCT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and a.FIBER_CABLE_ID LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' OR a.FIBER_CABLE_NAME LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' and a.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%'")
								.getResultList();
						strandsAuxiliaries = session.createNativeQuery(
								"SELECT DISTINCT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID and a.FIBER_CABLE_ID LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' OR a.FIBER_CABLE_NAME LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' and a.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ORDER BY c.SEQ_SORTING ASC ")
								.getResultList();
						}
						trenchList = session.createNativeQuery(
								"SELECT DISTINCT A.TRENCH_ID,A.TRENCH_NAME,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,A.SOURCE_LATITUDE,A.SOURCE_LONGITUDE,A.DESTINATION_LONGITUDE,A.DESTINATION_LATITUDE,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUM_DUCTS,A.MAX_CAPACITY,A.LENGTH,A.PROJECT_ID,A.DRAWING_TYPE FROM DUCTS B,TRENCH A WHERE B.TRENCH_ID=A.TRENCH_ID and (A.TRENCH_ID LIKE '%"
										+ request.getParameter("FilteredTrench") + "%' OR A.TRENCH_NAME LIKE '%"
										+ request.getParameter("FilteredTrench") + "%' OR B.DUCT_ID LIKE '%"
										+ request.getParameter("Filteredduct") + "%' OR B.DUCT_NAME LIKE '%"
										+ request.getParameter("Filteredduct") + "%' ) and A.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ")
								.getResultList();

						trenchAuxiliary_Data = session.createNativeQuery(
								"SELECT DISTINCT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.TRENCH_ID,B.DISTANCE_FROM_SOURCE,B.SEQ_SORTING,B.AUXILIARY_ID  FROM TRENCH A LEFT JOIN TRENCH_AUXILIARY_POINTS B ON A.TRENCH_ID=B.TRENCH_ID LEFT JOIN DUCTS C ON A.TRENCH_ID=C.TRENCH_ID WHERE (A.TRENCH_ID LIKE '%"
										+ request.getParameter("FilteredTrench") + "%' OR A.TRENCH_NAME LIKE '%"
										+ request.getParameter("FilteredTrench") + "%' OR C.DUCT_ID LIKE '%"
										+ request.getParameter("Filteredduct") + "%' OR C.DUCT_NAME LIKE '%"
										+ request.getParameter("Filteredduct") + "%'  ) and A.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ORDER BY B.SEQ_SORTING ASC")
								.getResultList();

						ductList = session.createNativeQuery(
								"SELECT DISTINCT B.DUCT_ID,B.DUCT_NAME,B.SOURCE_WARE_ID,B.SOURCE_ID,B.SOURCE_NAME,B.DESTINATION_WARE_ID,B.DESTINATION_ID,B.DESTINATION_NAME,B.SOURCE_LATITUDE,B.SOURCE_LONGITUDE,B.DESTINATION_LONGITUDE,B.DESTINATION_LATITUDE,B.SOURCE_CITY,B.DESTINATION_CITY,B.NUM_FIBERCABLES,B.NUM_FIBERTUBES,B.NUM_FIBERSTRANDS,B.LENGTH,B.TRENCH_ID,B.DRAWING_TYPE FROM DUCTS B,TRENCH A WHERE B.TRENCH_ID=A.TRENCH_ID and (A.TRENCH_ID LIKE '%"
										+ request.getParameter("FilteredTrench") + "%' OR A.TRENCH_NAME LIKE '%"
										+ request.getParameter("FilteredTrench") + "%') and A.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' OR (B.DUCT_ID LIKE '%"
										+ request.getParameter("Filteredduct") + "%' OR B.DUCT_NAME LIKE '%"
										+ request.getParameter("Filteredduct") + "%') ")
								.getResultList();

						ductAuxiliary_Data = session.createNativeQuery(
								"SELECT DISTINCT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.DUCT_ID,B.DISTANCE_FROM_SOURCE,B.SEQ_SORTING,B.AUXILIARY_ID FROM DUCTS A LEFT JOIN DUCT_AUXILIARY_POINTS B ON A.DUCT_ID=B.DUCT_ID LEFT JOIN TRENCH D ON A.TRENCH_ID = D.TRENCH_ID WHERE (A.DUCT_ID LIKE '%"
										+ request.getParameter("Filteredduct") + "%' OR A.DUCT_NAME LIKE '%"
										+ request.getParameter("Filteredduct") + "%' OR D.TRENCH_ID LIKE '%"
										+ request.getParameter("FilteredTrench") + "%' OR D.TRENCH_NAME LIKE '%"
										+ request.getParameter("FilteredTrench") + "%'   ) ORDER BY B.SEQ_SORTING ASC ")
								.getResultList();

						NodeList = session.createNativeQuery(
								"SELECT DISTINCT NODE_PK,NODE_NAME,NODE_TYPE || ':'  || NODE_NAME,DOMAIN,SITE_ID,LONGITUDE,LATITUDE,NODE_ID,SUB_DOMAIN_TYPE FROM NODE_ACTIVE WHERE (SUB_DOMAIN_TYPE='MSAN' OR SUB_DOMAIN_TYPE='SDH' OR SUB_DOMAIN_TYPE='DWDM' OR SUB_DOMAIN_TYPE='GPON' OR SUB_DOMAIN_TYPE='SWITCH' ) AND (NODE_NAME LIKE '%"
										+ request.getParameter("FilteredNode") + "%' OR NODE_PK  LIKE '%"
										+ request.getParameter("FilteredNode") + "%')     ")
								.getResultList();

						// To retrieve the data needed in show points/real points
						if (showPointsType.equals("1")) {
							String[] allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
									? findListId(manholeList, "all")
									: new String[] { "A" };
							String[] allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
									? findListId(handholeList, "all")
									: new String[] { "A" };
							String[] allDbIdsPointsArray = (findListId(distribBoardList, "all")).length > 0
									? findListId(distribBoardList, "all")
									: new String[] { "A" };
									
							List<String> allManIdsPointsList = Arrays.asList(allManIdsPointsArray);
							List<String> allHandIdsPointsList = Arrays.asList(allHandIdsPointsArray);
							List<String> allDbIdsPointsList = Arrays.asList(allDbIdsPointsArray);

							if (request.getParameter("Filteredfiber") != null) {

								// Select all manholes points that are source / destination / auxiliaries points
								query = session.createNativeQuery(
										" SELECT * FROM (SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%')   " + " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND (B.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR B.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%') " + " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND (B.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR B.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%') ) where manhole_id NOT IN (:param) ");
								query.setParameter("param", allManIdsPointsList);
								manholeList.addAll(query.getResultList());
								allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
										? findListId(manholeList, "all")
										: new String[] { "" };

								query = session.createNativeQuery(
										" SELECT * FROM (SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%')   " + " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.SOURCE_ID LIKE '%MH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%') " + " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.DESTINATION_ID LIKE '%MH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%') ) where manhole_id NOT IN (:param)");
								query.setParameter("param", allManIdsPointsList);
								manholeList.addAll(query.getResultList());
								allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
										? findListId(manholeList, "all")
										: new String[] { "" };

								query = session.createNativeQuery(
										" SELECT * FROM (SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%')   " + " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.SOURCE_ID LIKE '%MH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%') " + " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.DESTINATION_ID LIKE '%MH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%') ) where manhole_id NOT IN (:param)");
								query.setParameter("param", allManIdsPointsList);
								manholeList.addAll(query.getResultList());
								allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
										? findListId(manholeList, "all")
										: new String[] { "" };

								// Select all handholes points that are source / destination / auxiliaries
								// points
								query = session.createNativeQuery(
										" SELECT * FROM (SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%')   " + " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND (B.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR B.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%') " + " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND (B.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR B.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%') ) where handhole_id NOT IN (:param) ");
								query.setParameter("param", allHandIdsPointsList);
								handholeList.addAll(query.getResultList());
								allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
										? findListId(handholeList, "all")
										: new String[] { "" };

								query = session.createNativeQuery(
										" SELECT * FROM (SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%')   " + " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.SOURCE_ID LIKE '%HH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%') " + " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.DESTINATION_ID LIKE '%HH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%') ) where handhole_id NOT IN (:param)");
								query.setParameter("param", allHandIdsPointsList);
								handholeList.addAll(query.getResultList());
								allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
										? findListId(handholeList, "all")
										: new String[] { "" };

								query = session.createNativeQuery(
										" SELECT * FROM (SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%')   " + " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.SOURCE_ID LIKE '%HH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%') " + " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.DESTINATION_ID LIKE '%HH%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%') ) where handhole_id NOT IN (:param)");
								query.setParameter("param", allHandIdsPointsList);
								handholeList.addAll(query.getResultList());
								allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
										? findListId(handholeList, "all")
										: new String[] { "" };

								// Select all db points that are source / destination / auxiliaries points
								query = session.createNativeQuery(
										" SELECT * FROM (SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%')   " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID  where B.SOURCE_ID LIKE '%DB%' AND (B.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR B.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%') " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID  where B.DESTINATION_ID LIKE '%DB%' AND (B.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR B.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%') ) where DB_ID NOT IN (:param) ");
								query.setParameter("param", allDbIdsPointsList);
								distribBoardList.addAll(query.getResultList());
								allDbIdsPointsArray = (findListId(distribBoardList, "all")).length > 0
										? findListId(distribBoardList, "all")
										: new String[] { "" };

								query = session.createNativeQuery(
										" SELECT * FROM (SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%')   " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.SOURCE_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%') " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.DESTINATION_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%') ) where DB_ID NOT IN (:param)");
								query.setParameter("param", allDbIdsPointsList);
								distribBoardList.addAll(query.getResultList());
								allDbIdsPointsArray = (findListId(distribBoardList, "all")).length > 0
										? findListId(distribBoardList, "all")
										: new String[] { "" };

								query = session.createNativeQuery(
										" SELECT * FROM (SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%')   " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.SOURCE_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%') " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.DESTINATION_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%') ) where DB_ID NOT IN (:param)");
								query.setParameter("param", allDbIdsPointsList);
								distribBoardList.addAll(query.getResultList());
								allDbIdsPointsArray = (findListId(distribBoardList, "all")).length > 0
										? findListId(distribBoardList, "all")
										: new String[] { "" };
							}
							if (request.getParameter("FilteredTrench") != null
									|| request.getParameter("Filteredduct") != null) {

								// Select all manholes points that are source / destination / auxiliaries points
								// for the filtered trench
								query = session.createNativeQuery(
										"SELECT * FROM (SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN trench_auxiliary_points B ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN TRENCH C ON C.TRENCH_ID=B.TRENCH_ID LEFT JOIN DUCTS G ON C.trench_id=G.trench_id  where B.AUXILIARY_POINT_ID LIKE '%MH%' AND (G.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR G.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR C.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN TRENCH B ON B.SOURCE_ID = A.manhole_id LEFT JOIN DUCTS C ON c.trench_id=b.trench_id  WHERE B.SOURCE_ID LIKE '%MH%' AND  (C.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR B.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN TRENCH B  ON B.DESTINATION_ID = A.manhole_id LEFT JOIN DUCTS C ON c.trench_id=b.trench_id  where B.DESTINATION_ID LIKE '%MH%' AND (C.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR B.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench")
												+ "%') )  where manhole_id NOT IN (:param) ");
								query.setParameter("param", allManIdsPointsList);
								manholeList.addAll(query.getResultList());
								allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
										? findListId(manholeList, "all")
										: new String[] { "" };

								// Select all manholes points that are source / destination / auxiliaries points
								// for the filtered duct
								query = session.createNativeQuery(
										"SELECT * FROM (SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN duct_auxiliary_points B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN DUCTS C ON C.DUCT_ID=B.DUCT_ID LEFT JOIN TRENCH G ON G.trench_id=C.trench_id where B.AUXILIARY_POINT_ID LIKE '%MH%' AND (C.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR G.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR G.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN DUCTS B ON B.SOURCE_ID = A.manhole_id LEFT JOIN TRENCH C ON c.trench_id=b.trench_id WHERE B.SOURCE_ID LIKE '%MH%' AND  (B.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR C.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN DUCTS B  ON B.DESTINATION_ID = A.manhole_id LEFT JOIN TRENCH C ON c.trench_id=b.trench_id  where B.DESTINATION_ID LIKE '%MH%' AND (B.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR C.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench")
												+ "%') )  where manhole_id NOT IN (:param) ");
								query.setParameter("param", allManIdsPointsList);
								manholeList.addAll(query.getResultList());
								allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
										? findListId(manholeList, "all")
										: new String[] { "" };

								// Select all handholes points that are source / destination / auxiliaries
								// points for the filtered trench
								query = session.createNativeQuery(
										"SELECT * FROM (SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN trench_auxiliary_points B ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN TRENCH C ON C.TRENCH_ID=B.TRENCH_ID LEFT JOIN DUCTS G ON C.trench_id=G.trench_id  where B.AUXILIARY_POINT_ID LIKE '%HH%' AND (G.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR G.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR C.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN TRENCH B ON B.SOURCE_ID = A.HANDHOLE_ID LEFT JOIN DUCTS C ON c.trench_id=b.trench_id  WHERE B.SOURCE_ID LIKE '%HH%' AND  (C.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR B.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN TRENCH B  ON B.DESTINATION_ID = A.HANDHOLE_ID LEFT JOIN DUCTS C ON c.trench_id=b.trench_id  where B.DESTINATION_ID LIKE '%HH%' AND (C.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR B.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench")
												+ "%') )  where handhole_id NOT IN (:param) ");
								query.setParameter("param", allHandIdsPointsList);
								handholeList.addAll(query.getResultList());
								allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
										? findListId(handholeList, "all")
										: new String[] { "" };

								// Select all handholes points that are source / destination / auxiliaries
								// points for the filtered duct
								query = session.createNativeQuery(
										"SELECT * FROM (SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN duct_auxiliary_points B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN DUCTS C ON C.DUCT_ID=B.DUCT_ID LEFT JOIN TRENCH G ON G.trench_id=C.trench_id where B.AUXILIARY_POINT_ID LIKE '%HH%' AND (C.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR G.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR G.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN DUCTS B ON B.SOURCE_ID = A.HANDHOLE_ID LEFT JOIN TRENCH C ON c.trench_id=b.trench_id WHERE B.SOURCE_ID LIKE '%HH%' AND  (B.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR C.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.CITY FROM HANDHOLE A LEFT JOIN DUCTS B  ON B.DESTINATION_ID = A.HANDHOLE_ID LEFT JOIN TRENCH C ON c.trench_id=b.trench_id  where B.DESTINATION_ID LIKE '%HH%' AND (B.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR C.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench")
												+ "%') )  where handhole_id NOT IN (:param) ");
								query.setParameter("param", allHandIdsPointsList);
								handholeList.addAll(query.getResultList());
								allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
										? findListId(handholeList, "all")
										: new String[] { "" };

								// Select all dbs points that are source / destination / auxiliaries points for
								// the filtered trench
								query = session.createNativeQuery(
										"SELECT * FROM (SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN trench_auxiliary_points B ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN TRENCH C ON C.TRENCH_ID=B.TRENCH_ID LEFT JOIN DUCTS G ON C.trench_id=G.trench_id  where B.AUXILIARY_POINT_ID LIKE '%DB%' AND (G.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR G.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR C.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN TRENCH B ON B.SOURCE_ID = A.DB_ID LEFT JOIN DUCTS C ON c.trench_id=b.trench_id  WHERE B.SOURCE_ID LIKE 'DB%' AND  (C.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR B.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN TRENCH B  ON B.DESTINATION_ID = A.DB_ID LEFT JOIN DUCTS C ON c.trench_id=b.trench_id  where B.DESTINATION_ID LIKE '%DB%' AND (C.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR B.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench")
												+ "%') )  where DB_ID NOT IN (:param) ");
								query.setParameter("param", allDbIdsPointsList);
								distribBoardList.addAll(query.getResultList());
								allDbIdsPointsArray = (findListId(distribBoardList, "all")).length > 0
										? findListId(distribBoardList, "all")
										: new String[] { "" };

								// Select all dbs points that are source / destination / auxiliaries points for
								// the filtered duct
								query = session.createNativeQuery(
										"SELECT * FROM (SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN duct_auxiliary_points B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN DUCTS C ON C.DUCT_ID=B.DUCT_ID LEFT JOIN TRENCH G ON G.trench_id=C.trench_id where B.AUXILIARY_POINT_ID LIKE '%DB%' AND (C.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR G.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR G.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN DUCTS B ON B.SOURCE_ID = A.DB_ID LEFT JOIN TRENCH C ON c.trench_id=b.trench_id WHERE B.SOURCE_ID LIKE '%DB%' AND  (B.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR C.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN DUCTS B  ON B.DESTINATION_ID = A.DB_ID LEFT JOIN TRENCH C ON c.trench_id=b.trench_id  where B.DESTINATION_ID LIKE '%DB%' AND (B.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR C.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench")
												+ "%') )  where DB_ID NOT IN (:param) ");
								query.setParameter("param", allDbIdsPointsList);
								distribBoardList.addAll(query.getResultList());
								allDbIdsPointsArray = (findListId(distribBoardList, "all")).length > 0
										? findListId(distribBoardList, "all")
										: new String[] { "" };
							}

							String filteredManJunction = request.getParameter("FilteredJunction_Manhole");
							String filteredHandJunction = request.getParameter("FilteredJunction_Handhole");

							if (filteredManJunction == null) {
								filteredManJunction = "";
							}
							if (filteredHandJunction == null) {
								filteredHandJunction = "";
							}
							query = session.createNativeQuery(
									"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,  A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A left JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id  where (A.JUNCTION_ID LIKE '%"
											+ filteredManJunction + "%' OR A.JUNCTION_NAME LIKE '%"
											+ filteredManJunction + "%') and A.PROJECT_ID LIKE '%"
											+ request.getParameter("Checked") + "%' AND B.MANHOLE_ID IN (:param)  ");
							junctionManholeList = query.setParameter("param", allManIdsPointsList).getResultList();
							query = session.createNativeQuery(
									"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A LEFT JOIN handhole B ON A.PHYSICAL_LAYER_ID = B.handhole_id where (A.JUNCTION_ID LIKE '%"
											+ filteredHandJunction + "%' OR A.JUNCTION_NAME LIKE '%"
											+ filteredHandJunction + "%') and A.PROJECT_ID LIKE '%"
											+ request.getParameter("Checked") + "%' AND B.HANDHOLE_ID IN (:param)  ");
							junctionHandholeList = query.setParameter("param", allHandIdsPointsList).getResultList();
						}
					} 
					// Find nearest option
					else if (StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "circleRange")
							|| StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "StartEnd")
							|| StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "circleRange_multy")) {
						filterFlag = 2;
						checkedOption = request.getParameter("Checked");
						
						if (StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "circleRange")) {
									String noOfPoints = request.getParameter("noP");
							String closestDisRange = request.getParameter("closestDisRange");
							String closestLatPoint = request.getParameter("closestLatPoint");
							String closestLongPoint = request.getParameter("closestLongPoint");
							String getRelatedPoints = request.getParameter("getRelatedPoints");
							String customerID = request.getParameter("CustomerID");
							String customerName=request.getParameter("CustomerName");
							
							String serviceReq = request.getParameter("serviceReq");
							String serviceRef = request.getParameter("serviceRef");

							//Calculate 2 long & 2 lat on circle's borders
							double[] bordeCircleLatitudes = calculateBorderCircleLatitudes(Double.valueOf(closestLatPoint), Double.valueOf(closestLongPoint),  Double.valueOf(closestDisRange));							
							double[] borderCircleLongitudes = calculateBorderCircleLongitudes(Double.valueOf(closestLatPoint), Double.valueOf(closestLongPoint),  Double.valueOf(closestDisRange));
															
							
							fiberList = session.createNativeQuery(
								"SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR "
								+ "FROM FIBER_CABLES A LEFT JOIN FIBER_AUXILIARY_POINTS D ON A.FIBER_CABLE_ID=D.FIBER_CABLE_ID "
								+ "WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) > "+borderCircleLongitudes[0]  +"and  to_number(SUBSTR(A.SOURCE_LAT,1,6)) >  "+bordeCircleLatitudes[0] +" and to_number (SUBSTR(A.SOURCE_LNG,1,6)) < "+borderCircleLongitudes[1]+" AND to_number (SUBSTR(A.SOURCE_LAT,1,6)) < " +  bordeCircleLatitudes[1]+") "
								+ "OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) > "+borderCircleLongitudes[0]  +"and  to_number(SUBSTR(A.DESTINATION_LAT,1,6)) >  "+bordeCircleLatitudes[0] +" and to_number (SUBSTR(A.DESTINATION_LNG,1,6)) < "+borderCircleLongitudes[1]+" AND to_number (SUBSTR(A.DESTINATION_LAT,1,6)) < " +  bordeCircleLatitudes[1]+") "
								+ "OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) > "+borderCircleLongitudes[0]  +"and  to_number(SUBSTR(D.LATITUDE,1,6)) >  "+bordeCircleLatitudes[0] +" and to_number (SUBSTR(D.LONGITUDE,1,6)) < "+borderCircleLongitudes[1]+" AND to_number (SUBSTR(D.LATITUDE,1,6)) < " +  bordeCircleLatitudes[1]+") ").getResultList();
																		
							List<String> cablesIDs = Arrays.asList((findListId(fiberList, "FiberCable")).length > 0 ? findListId(fiberList, "FiberCable") : new String[] { "" });
							combinedCablesList.addAll(cablesIDs);// used in get related points
							findIDsSrcDest(fiberList,"Cables", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs, borderCircleLongitudes[0],  bordeCircleLatitudes[0], borderCircleLongitudes[1], bordeCircleLatitudes[1]); // Add the cables src/dest points that are within the range & areMH,HH,DB to array
							
							// Split the array into sub-arrays( each of 1000 elements )
						   if(fiberList.size() >0) {
								int subArraySize = 1000;
								int listSize = cablesIDs.size();

								for (int i = 0; i < listSize; i += subArraySize) {
								    int remaining = listSize - i;
								    int currentListSize = Math.min(subArraySize, remaining);
								    List<String> sublist = cablesIDs.subList(i, i + currentListSize);
								    
								    	//Get all auxiliaries of each cable
										query = session.createNativeQuery(
											"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID "
										  + " AND B.FIBER_CABLE_ID IN (:param) ORDER BY B.SEQ_SORTING ASC").setParameter("param",sublist);
										fiberAuxiliary_Data.addAll(query.getResultList());
								}
							}  
							
							findIDsForAux(fiberAuxiliary_Data,"Cables", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs, borderCircleLongitudes[0],  bordeCircleLatitudes[0], borderCircleLongitudes[1], bordeCircleLatitudes[1]);// Add the cables Aux points that are within the range & are MH,HH,DB to array						  
						
							fiberTubes = session.createNativeQuery(
								"SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER,b.TUBE_COLOR FROM FIBER_TUBES b "
								+ "LEFT JOIN TUBE_AUXILIARY_POINTS a ON a.TUBE_ID=b.TUBE_ID LEFT JOIN FIBER_STRANDS E ON E.TUBE_ID=b.TUBE_ID "
								+ "WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) > "+borderCircleLongitudes[0]  +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >  "+bordeCircleLatitudes[0] +" and to_number (SUBSTR(b.SOURCE_LONGITUDE,1,6)) < "+borderCircleLongitudes[1]+" AND to_number (SUBSTR(b.SOURCE_LATITUDE,1,6)) < " +  bordeCircleLatitudes[1]+") "
								+ "OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > "+borderCircleLongitudes[0]  +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >  "+bordeCircleLatitudes[0] +" and to_number (SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+borderCircleLongitudes[1]+" AND to_number (SUBSTR(b.DESTINATION_LATITUDE,1,6)) < " +  bordeCircleLatitudes[1]+") "
								+ "OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > "+borderCircleLongitudes[0]  +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >  "+bordeCircleLatitudes[0] +" and to_number (SUBSTR(a.LONGITUDE,1,6)) < "+borderCircleLongitudes[1]+" AND to_number (SUBSTR(a.LATITUDE,1,6)) < " +  bordeCircleLatitudes[1]+") ").getResultList();

					      List<String> tubesIDs = Arrays.asList((findListId(fiberTubes, "Tube")).length > 0 ? findListId(fiberTubes, "Tube") : new String[] { "" });
					      combinedTubeList.addAll(tubesIDs);// used in get related points
						  findIDsSrcDest(fiberTubes,"Tubes", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs, borderCircleLongitudes[0],  bordeCircleLatitudes[0], borderCircleLongitudes[1], bordeCircleLatitudes[1]);

						// Split the array into sub-arrays( each of 1000 elements )  
						  if(fiberTubes.size() >0) {
								
								int subArraySize = 1000;
								int listSize = tubesIDs.size();

								for (int i = 0; i < listSize; i += subArraySize) {
								    int remaining = listSize - i;
								    int currentListSize = Math.min(subArraySize, remaining);
								    List<String> sublist = tubesIDs.subList(i, i + currentListSize);
								    
								  //Get all auxiliaries of each tube
							        query = session.createNativeQuery(
											"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON  b.TUBE_ID=c.TUBE_ID "
											+ " WHERE c.TUBE_ID IN (:param) ORDER BY c.SEQ_SORTING ASC").setParameter("param",sublist);
							        tubesAuxiliaries.addAll(query.getResultList());
								}
							}  
						  
						findIDsForAux(tubesAuxiliaries,"Tubes", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs, borderCircleLongitudes[0],  bordeCircleLatitudes[0], borderCircleLongitudes[1], bordeCircleLatitudes[1]);// Add the tubes Aux points that are within the range & are MH,HH,DB to array
						
						fiberStrands = session.createNativeQuery(
								"SELECT DISTINCT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR FROM FIBER_STRANDS b LEFT JOIN STRAND_AUXILIARY_POINTS a ON b.STRAND_ID=a.STRAND_ID "
								+ "WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) > "+borderCircleLongitudes[0]  +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >  "+bordeCircleLatitudes[0] +" and to_number (SUBSTR(b.SOURCE_LONGITUDE,1,6)) < "+borderCircleLongitudes[1]+" AND to_number (SUBSTR(b.SOURCE_LATITUDE,1,6)) < " +  bordeCircleLatitudes[1]+" ) "	
								+ "OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > "+borderCircleLongitudes[0]  +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >  "+bordeCircleLatitudes[0] +" and to_number (SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+borderCircleLongitudes[1]+" AND to_number (SUBSTR(b.DESTINATION_LATITUDE,1,6)) < " +  bordeCircleLatitudes[1]+") "
								+ "OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > "+borderCircleLongitudes[0]  +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >  "+bordeCircleLatitudes[0] +" and to_number (SUBSTR(a.LONGITUDE,1,6)) < "+borderCircleLongitudes[1]+" AND to_number (SUBSTR(a.LATITUDE,1,6)) < " +  bordeCircleLatitudes[1]+") ").getResultList();
						List<String> strandsIDs = Arrays.asList((findListId(fiberStrands, "Strand")).length > 0 ? findListId(fiberStrands, "Strand") : new String[] { "" });
						
						findIDsSrcDest(fiberStrands,"Strands", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs, borderCircleLongitudes[0],  bordeCircleLatitudes[0], borderCircleLongitudes[1], bordeCircleLatitudes[1]);// Add the strands src/dest points that are within the range & are MH,HH,DB to array
						
						// Split the array into sub-arrays( each of 1000 elements )  
						  if(fiberStrands.size() >0) {
								int subArraySize = 1000;
								int listSize = strandsIDs.size();

								for (int i = 0; i < listSize; i += subArraySize) {
								    int remaining = listSize - i;
								    int currentListSize = Math.min(subArraySize, remaining);
								    List<String> sublist = strandsIDs.subList(i, i + currentListSize);
								    
								  //Get all auxiliaries of each strand
									query = session.createNativeQuery(
											"SELECT DISTINCT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b WHERE b.STRAND_ID=c.STRAND_ID "
											+ " AND c.STRAND_ID IN (:param) ORDER BY c.SEQ_SORTING ASC ").setParameter("param",sublist);
									strandsAuxiliaries.addAll(query.getResultList());
									
								 //Check and Select if there is tubes that are outside the range but have strands in range and not selected before
									query = session.createNativeQuery(
											"SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER,b.TUBE_COLOR "
											+ "FROM FIBER_TUBES b LEFT JOIN FIBER_STRANDS E ON E.TUBE_ID=b.TUBE_ID "
											+ "WHERE E.STRAND_ID in (:param) ").setParameter("param",strandsIDs);
									tempDataList.addAll(query.getResultList());

								}
							}  
						findIDsForAux(strandsAuxiliaries,"Strands", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs, borderCircleLongitudes[0],  bordeCircleLatitudes[0], borderCircleLongitudes[1], bordeCircleLatitudes[1]);// Add the strands Aux points that are within the range & are MH,HH,DB to array		
		   
					
					   //Check and Select if there is tubes that are outside the range but have strands in range and not selected before
					   if(tempDataList.size()>0) {
					   
						
							List<Object[]> tmprList = filterDataList(tempDataList,tubesIDs,"Tubes");
							
							List<String> outOfRangeTubeIDs = Arrays.asList((findListId(tmprList, "Tube")).length > 0 ? findListId(tmprList, "Tube") : new String[] { "" });
						    combinedTubeList.addAll(outOfRangeTubeIDs);// used in get related points
				
							if (fiberTubes.size() > 0) {
								fiberTubes.addAll(tmprList);
							} 
							else {
								fiberTubes = tmprList;
							}

							
							// Split the array into sub-arrays( each of 1000 elements )  
							  if(tmprList.size() >0) {
									int subArraySize = 1000;
									int listSize = outOfRangeTubeIDs.size();

									for (int i = 0; i < listSize; i += subArraySize) {
									    int remaining = listSize - i;
									    int currentListSize = Math.min(subArraySize, remaining);
									    List<String> sublist = outOfRangeTubeIDs.subList(i, i + currentListSize);
									   										
										 query = session.createNativeQuery(
													"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON  b.TUBE_ID=c.TUBE_ID  "
													+ " WHERE c.TUBE_ID IN (:param) ORDER BY c.SEQ_SORTING ASC").setParameter("param",sublist);
									
										 tubesAuxiliaries.addAll(query.getResultList());
									}
								}  						
					   }
		   
					   tempDataList.clear();
					   //Check and Select if there is cables that are outside the range but have tubes in range and not selected before
					   if(fiberTubes.size()>0) {
						   
						   int subArraySize = 1000;
						   int listSize = combinedTubeList.size();

							for (int i = 0; i < listSize; i += subArraySize) {
							    int remaining = listSize - i;
							    int currentListSize = Math.min(subArraySize, remaining);
							    List<String> sublist = combinedTubeList.subList(i, i + currentListSize);
							   	
								query = session.createNativeQuery(
										"SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR "
										+ "FROM FIBER_CABLES A LEFT JOIN FIBER_TUBES E ON E.FIBER_CABLE_ID=A.FIBER_CABLE_ID "
										+ "WHERE E.TUBE_ID in (:param) ").setParameter("param",sublist);
					
								tempDataList.addAll(query.getResultList());								 
							}
							
						List<Object[]> tmprList = filterDataList(tempDataList,cablesIDs,"Cables");

						List<String> outOfRangeCableIDs = Arrays.asList((findListId(tmprList, "FiberCable")).length > 0 ? findListId(tmprList, "FiberCable") : new String[] { "" });
					    combinedCablesList.addAll(outOfRangeCableIDs);// used in get related points
			
						if (fiberList.size() > 0) {
							fiberList.addAll(tmprList);
						} 
						else {
							fiberList = tmprList;
						}
						
						
						   int subArrayLngth = 1000;
						   int lstSize = outOfRangeCableIDs.size();

							for (int i = 0; i < lstSize; i += subArrayLngth) {
							    int remaining = lstSize - i;
							    int currentListSize = Math.min(subArrayLngth, remaining);
							    List<String> sublist = outOfRangeCableIDs.subList(i, i + currentListSize);
							   	
								query = session.createNativeQuery(
										"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A LEFT JOIN FIBER_AUXILIARY_POINTS B ON A.FIBER_CABLE_ID=B.FIBER_CABLE_ID "
									  + " WHERE B.FIBER_CABLE_ID IN (:param) ORDER BY B.SEQ_SORTING ASC").setParameter("param",sublist);
						
								fiberAuxiliary_Data.addAll(query.getResultList());								 
							}
					   }
					   
					//Select all MH details that are within the range

					List<Object[]> manholeListQuery = session.createNativeQuery(
							"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,trim(replace(LONGITUDE,'�','')) as LONGITUDE, trim(replace(LATITUDE,'�','')) as LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),DM_NAME FROM MANHOLE"
						  + " WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+borderCircleLongitudes[0]  +"and  to_number(SUBSTR(LATITUDE,1,6)) >  "+bordeCircleLatitudes[0] +" and to_number (SUBSTR(LONGITUDE,1,6)) < "+borderCircleLongitudes[1]+" AND to_number (SUBSTR(LATITUDE,1,6)) < " +  bordeCircleLatitudes[1]+")").getResultList();
					   
					   
					//Filter the MH that have distance < distanceRange or MH that exists in mhFilteredIDs (mhFilteredIDs contains MH that are src/dst/aux of cables,tubes,strands)
					manholeList = findLinearDistance(mhFilteredIDs,manholeListQuery, Double.valueOf(closestLatPoint),Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange),"Manhole", noOfPoints);														
					
					//Select all HH details that are within the range
					
					List<Object[]> handholeListQuery = session.createNativeQuery(
							"SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,trim(replace(LONGITUDE,'�','')) as LONGITUDE,trim(replace(LATITUDE,'�','')) as LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),DM_NAME FROM HANDHOLE"
						  + " WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+borderCircleLongitudes[0]  +"and  to_number(SUBSTR(LATITUDE,1,6)) >  "+bordeCircleLatitudes[0] +" and to_number (SUBSTR(LONGITUDE,1,6)) < "+borderCircleLongitudes[1]+" AND to_number (SUBSTR(LATITUDE,1,6)) < " +  bordeCircleLatitudes[1]+")").getResultList();
					
				
					//Filter the HH that have distance<distanceRange or HH that exists in hhFilteredIDs (hhFilteredIDs contains HH that are src/dst/aux of cables,tubes,strands)
					handholeList = findLinearDistance(hhFilteredIDs,handholeListQuery, Double.valueOf(closestLatPoint),Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange),"Handhole", noOfPoints);
					
					//Select all DB details that are within the range
					List<Object[]> distribBoardListQuery = session.createNativeQuery(
							"SELECT DISTINCT DB_ID,trim(replace(DB_LONGITUDE,'�','')) as DB_LONGITUDE,trim(replace(DB_LATITUDE,'�','')) as DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY,DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD "
						  + " WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) > "+borderCircleLongitudes[0]  +"and  to_number(SUBSTR(DB_LATITUDE,1,6)) >  "+bordeCircleLatitudes[0] +" and to_number (SUBSTR(DB_LONGITUDE,1,6)) < "+borderCircleLongitudes[1]+" AND to_number (SUBSTR(DB_LATITUDE,1,6)) < " +  bordeCircleLatitudes[1]+")").getResultList();
					
					//Filter the DB that have distance<distanceRange or DB that exists in dbFilteredIDs (dbFilteredIDs contains HH that are src/dst/aux of cables/tubes/strands)			
					distribBoardList = findLinearDistance(dbFilteredIDs,distribBoardListQuery, Double.valueOf(closestLatPoint),Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange),"DistribBoard", noOfPoints);
																		
				// To select the data needed in show points/real points & are outside the range
				if (getRelatedPoints.equals("1")) {
					
					String[] manholesId = (findListId(manholeList, "all")).length > 0 ? findListId(manholeList, "all") : new String[] { "A" };										
					String[] handholesId = (findListId(handholeList, "all")).length > 0 ? findListId(handholeList, "all") : new String[] { "A" };										
					String[] dbsId = (findListId(distribBoardList, "all")).length > 0 ? findListId(distribBoardList, "all") : new String[] { "A" };										

					int subArraySize = 1000;
					int listSize = combinedCablesList.size();

					for (int i = 0; i < listSize; i += subArraySize) {
					    int remaining = listSize - i;
					    int currentListSize = Math.min(subArraySize, remaining);
					    List<String> sublist = combinedCablesList.subList(i, i + currentListSize);
					    
					  //MH that are outside the range
						query = session.createNativeQuery(
								" SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.FIBER_CABLE_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1)  ) ");
						query.setParameter("param1",sublist);
						manholeTempList.addAll(query.getResultList());
						
						// HH that are outside the range
						query = session.createNativeQuery(
							" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.FIBER_CABLE_ID IN (:param1) "
							+ " UNION "
							+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) "
							+ "UNION"
							+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) )  ");
						query.setParameter("param1",sublist);
						handholeTempList.addAll(query.getResultList());
						
						query = session.createNativeQuery(
								" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE,trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE, A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.FIBER_CABLE_ID IN (:param1)   "
										+ " UNION "
										+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE,trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) "
										+ "UNION "
										+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE,trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) )   ");
						query.setParameter("param1",sublist);
						dbTempList.addAll(query.getResultList());								
					}
					
					 subArraySize = 1000;
					 listSize = combinedTubeList.size();

					for (int i = 0; i < listSize; i += subArraySize) {
					    int remaining = listSize - i;
					    int currentListSize = Math.min(subArraySize, remaining);
					    List<String> sublist = combinedTubeList.subList(i, i + currentListSize);
					    
						query = session.createNativeQuery("SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_TUBES C ON C.TUBE_ID = B.TUBE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.TUBE_ID IN (:param1) " + 
								" UNION "
								+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.TUBE_ID IN (:param1) " + 
								" UNION "
								+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.TUBE_ID IN (:param1) ) ");
						
						query.setParameter("param1",sublist);
						manholeTempList.addAll(query.getResultList());
						
						
						query = session.createNativeQuery(
								" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_TUBES C ON C.TUBE_ID = B.TUBE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.TUBE_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.TUBE_ID IN (:param1) "
								+ "UNION"
								+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.TUBE_ID IN (:param1) ) ");
						query.setParameter("param1",sublist);
						handholeTempList.addAll(query.getResultList());
						
						query = session.createNativeQuery(
								" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID, trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE, A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_TUBES C ON C.TUBE_ID = B.TUBE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.TUBE_ID IN (:param1)   "
										+ " UNION "
										+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.TUBE_ID IN (:param1) "
										+ "UNION "
										+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.TUBE_ID IN (:param1) )  ");
						query.setParameter("param1",sublist);
						dbTempList.addAll(query.getResultList());					
					}
					
					 subArraySize = 1000;
					 listSize = strandsIDs.size();

					for (int i = 0; i < listSize; i += subArraySize) {
					    int remaining = listSize - i;
					    int currentListSize = Math.min(subArraySize, remaining);
					    List<String> sublist = strandsIDs.subList(i, i + currentListSize);
					    
					    query = session.createNativeQuery("SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_STRANDS C ON C.STRAND_ID = B.STRAND_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.STRAND_ID IN (:param1) " + 
								" UNION "
								+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.STRAND_ID IN (:param1) " + 
								" UNION "
								+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.STRAND_ID IN (:param1) ) ");
						
						query.setParameter("param1",sublist);
						manholeTempList.addAll(query.getResultList());	
						
						query = session.createNativeQuery(
								" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_STRANDS C ON C.STRAND_ID = B.STRAND_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.STRAND_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.STRAND_ID IN (:param1) "
								+ "UNION"
								+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.STRAND_ID IN (:param1) )  ");
						query.setParameter("param1",sublist);
						handholeTempList.addAll(query.getResultList());		
			
						query = session.createNativeQuery(
								" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID, trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE, A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_STRANDS C ON C.STRAND_ID = B.STRAND_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.STRAND_ID IN (:param1)   "
										+ " UNION "
										+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.STRAND_ID IN (:param1) "
										+ "UNION "
										+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.STRAND_ID IN (:param1) ) ");
						query.setParameter("param1",sublist);
						dbTempList.addAll(query.getResultList());		
					}
				
		
				if (manholeList.size() > 0) {
					List<Object[]> tmprList = filterTempList(manholeTempList,manholesId);
					newList = findNearestArray(tmprList, Double.valueOf(closestLatPoint),Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "ManHandhole_OutOfZone", noOfPoints);
					manholeList.addAll(newList);
				}								 
				else {
					manholeList = findNearestArray(manholeTempList,Double.valueOf(closestLatPoint), Double.valueOf(closestLongPoint),Double.valueOf(closestDisRange), "ManHandhole_OutOfZone", noOfPoints);
				}								
								
				if (handholeList.size() > 0) {
					List<Object[]> tmprList = filterTempList(handholeTempList,handholesId);
					newList = findNearestArray(tmprList, Double.valueOf(closestLatPoint),Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange),"ManHandhole_OutOfZone", noOfPoints);
					handholeList.addAll(newList);
				} 						
				else {
					handholeList = findNearestArray(handholeTempList,Double.valueOf(closestLatPoint), Double.valueOf(closestLongPoint),Double.valueOf(closestDisRange), "ManHandhole_OutOfZone", noOfPoints);
				}
																			
				if (distribBoardList.size() > 0) {
					List<Object[]> tmprList = filterTempList(dbTempList,dbsId);
					newList = findNearestArray(tmprList, Double.valueOf(closestLatPoint),Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange),"DB_OutOfZone", noOfPoints);
					distribBoardList.addAll(newList);
				} 
				else {
					distribBoardList = findNearestArray(dbTempList,Double.valueOf(closestLatPoint), Double.valueOf(closestLongPoint),Double.valueOf(closestDisRange), "DB_OutOfZone", noOfPoints);
				}
				
			}
				junctionManholeList = session.createNativeQuery(
						"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE,trim(replace(A.LATITUDE,'�','')) as LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id ").getResultList();
				
				junctionHandholeList = session.createNativeQuery(
						"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE,trim(replace(A.LATITUDE,'�','')) as LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id ").getResultList();

				List<Object[]> nodeListQuery = session.createNativeQuery(
						"SELECT DISTINCT NODE_PK,NODE_NAME,NODE_TYPE || ':'  || NODE_NAME,DOMAIN,SITE_ID,trim(replace(LONGITUDE,'�','')) as LONGITUDE,trim(replace(LATITUDE,'�','')) as LATITUDE,NODE_ID,SUB_DOMAIN_TYPE FROM NODE_ACTIVE WHERE (SUB_DOMAIN_TYPE='MSAN' OR SUB_DOMAIN_TYPE='SDH' OR SUB_DOMAIN_TYPE='DWDM' OR SUB_DOMAIN_TYPE='GPON' OR SUB_DOMAIN_TYPE='SWITCH' ) "
								+ " AND (LONGITUDE !='null' or LONGITUDE !=null ) AND (LATITUDE !='null' or LATITUDE !=null ) ").getResultList();
				NodeList = findNearestArray(nodeListQuery, Double.valueOf(closestLatPoint),Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "Nodes",noOfPoints);
				
				model.addAttribute("closestLatPoint", closestLatPoint);
				model.addAttribute("closestLongPoint", closestLongPoint);
				model.addAttribute("closestDisRange", closestDisRange);
				model.addAttribute("noP", noOfPoints);
				model.addAttribute("getRelatedPoints", getRelatedPoints);
				model.addAttribute("startLng", borderCircleLongitudes[0]);
				model.addAttribute("endLng", borderCircleLongitudes[1]);
				model.addAttribute("startLat", bordeCircleLatitudes[0]);
				model.addAttribute("endLat", bordeCircleLatitudes[1]);	
				model.addAttribute("CustomerID", customerID);
				model.addAttribute("CustomerName",customerName);
				model.addAttribute("serviceReq", serviceReq);	
				model.addAttribute("serviceRef", serviceRef);	

			}
			else if (StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "StartEnd")) {
				
				String startLongPoint = request.getParameter("startLongPoint");
				String startLatPoint = request.getParameter("startLatPoint");
				String endLongPoint = request.getParameter("endLongPoint");
				String endLatPoint = request.getParameter("endLatPoint");
				String getRelatedPoints = request.getParameter("getRelatedPoints");
				String startLng, endLng,startlatitude,endLatitude;

				String cableStr = "SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR "
				+ "FROM FIBER_CABLES A LEFT JOIN FIBER_AUXILIARY_POINTS D ON A.FIBER_CABLE_ID=D.FIBER_CABLE_ID ";
		
				String tubeStr = "SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER,b.TUBE_COLOR FROM FIBER_TUBES b "
				+ "LEFT JOIN TUBE_AUXILIARY_POINTS a ON a.TUBE_ID=b.TUBE_ID LEFT JOIN FIBER_STRANDS E ON E.TUBE_ID=b.TUBE_ID ";
		
				String strandStr = "SELECT DISTINCT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR FROM FIBER_STRANDS b "
				+ "LEFT JOIN STRAND_AUXILIARY_POINTS a ON b.STRAND_ID=a.STRAND_ID ";
			
				String manholeStr =	"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,trim(replace(LONGITUDE,'�','')) as LONGITUDE,trim(replace(LATITUDE,'�','')) as LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),DM_NAME FROM MANHOLE ";
				String handholeStr = "SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,trim(replace(LONGITUDE,'�','')) as LONGITUDE,trim(replace(LATITUDE,'�','')) as LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),DM_NAME FROM HANDHOLE ";
				String dbStr = 	"SELECT DISTINCT DB_ID,trim(replace(DB_LONGITUDE,'�','')) as DB_LONGITUDE,trim(replace(DB_LATITUDE,'�','')) as DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY,DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD ";
				
				String nodeStr ="SELECT DISTINCT NODE_PK,NODE_NAME,NODE_TYPE || ':'  || NODE_NAME,DOMAIN,SITE_ID,trim(replace(LONGITUDE,'�','')) as LONGITUDE,trim(replace(LATITUDE,'�','')) as LATITUDE,NODE_ID,SUB_DOMAIN_TYPE FROM NODE_ACTIVE WHERE (SUB_DOMAIN_TYPE='MSAN' OR SUB_DOMAIN_TYPE='SDH' OR SUB_DOMAIN_TYPE='DWDM' OR SUB_DOMAIN_TYPE='GPON' OR SUB_DOMAIN_TYPE='SWITCH' ) "
				+ " AND (LONGITUDE !='null' or LONGITUDE !=null ) AND (LATITUDE !='null' or LATITUDE !=null ) ";
		
				if (startLongPoint != null && !startLongPoint.equalsIgnoreCase("")  && endLongPoint != null && !endLongPoint.equalsIgnoreCase("")
						&&	startLatPoint != null && !startLatPoint.equalsIgnoreCase("") &&	endLatPoint != null && !endLatPoint.equalsIgnoreCase("") ) {
				
					if (Double.parseDouble(startLongPoint) < Double.parseDouble(endLongPoint)) {
						startLng = startLongPoint;
						endLng = endLongPoint;
					} 
					else {
						startLng = endLongPoint;
						endLng = startLongPoint;
					}
					
					if (Double.parseDouble(startLatPoint) < Double.parseDouble(endLatPoint)) {
						startlatitude = startLatPoint;
						endLatitude = endLatPoint;
					} 
					else {
						startlatitude = endLatPoint;
						endLatitude = startLatPoint;
					}
					
					cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) >"+startLng +"and  to_number(SUBSTR(A.SOURCE_LAT,1,6)) >"+startlatitude +" and to_number (SUBSTR(A.SOURCE_LNG,1,6)) <"+endLng +" AND to_number (SUBSTR(A.SOURCE_LAT,1,6)) < " +endLatitude +" )"
							+ " OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) > "+startLng +"and  to_number(SUBSTR(A.DESTINATION_LAT,1,6)) >"+startlatitude +" and to_number (SUBSTR(A.DESTINATION_LNG,1,6)) < "+endLng+" AND to_number (SUBSTR(A.DESTINATION_LAT,1,6)) <" +endLatitude+") "
							+ " OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) > "+startLng +"and  to_number(SUBSTR(D.LATITUDE,1,6)) >"+startlatitude +" and to_number (SUBSTR(D.LONGITUDE,1,6)) < "+endLng+" AND to_number (SUBSTR(D.LATITUDE,1,6)) <" +endLatitude+") "; 
				
					tubeStr = tubeStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLng +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startlatitude +" and to_number (SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLng +" AND to_number (SUBSTR(b.SOURCE_LATITUDE,1,6)) < " +endLatitude +" )"
							+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > "+startLng +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startlatitude +" and to_number (SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLng+" AND to_number (SUBSTR(b.DESTINATION_LATITUDE,1,6)) <" +endLatitude+") "
							+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > "+startLng +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >"+startlatitude +" and to_number (SUBSTR(a.LONGITUDE,1,6)) < "+endLng+" AND to_number (SUBSTR(a.LATITUDE,1,6)) <" +endLatitude+") "; 
			
					strandStr = strandStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLng +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startlatitude +" and to_number (SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLng +" AND to_number (SUBSTR(b.SOURCE_LATITUDE,1,6)) < " +endLatitude +" )"
							+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > "+startLng +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startlatitude +" and to_number (SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLng+" AND to_number (SUBSTR(b.DESTINATION_LATITUDE,1,6)) <" +endLatitude+") "
							+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > "+startLng +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >"+startlatitude +" and to_number (SUBSTR(a.LONGITUDE,1,6)) < "+endLng+" AND to_number (SUBSTR(a.LATITUDE,1,6)) <" +endLatitude+") "; 
			
					manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(LONGITUDE,1,6)) <"+endLng +" and  to_number(SUBSTR(LATITUDE,1,6)) > "+startlatitude  +" and to_number (SUBSTR(LATITUDE,1,6)) < "+endLatitude +"  ) ";
					handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(LONGITUDE,1,6)) <"+endLng +" and to_number(SUBSTR(LATITUDE,1,6)) > "+startlatitude  +" and to_number (SUBSTR(LATITUDE,1,6)) < "+endLatitude +" ) ";
					dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(DB_LONGITUDE,1,6)) <"+endLng +"and  to_number(SUBSTR(DB_LATITUDE,1,6)) >"+startlatitude  +" and to_number (SUBSTR(DB_LATITUDE,1,6)) < "+endLatitude +" ) ";
					nodeStr = nodeStr+" AND ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(LONGITUDE,1,6)) <"+endLng +" and to_number(SUBSTR(LATITUDE,1,6)) > "+startlatitude  +" and to_number (SUBSTR(LATITUDE,1,6)) < "+endLatitude +" ) ";

					//To return the correct start/end long lat after comparison above
					startLongPoint=startLng;
					endLongPoint= endLng;
					startLatPoint= startlatitude;
					endLatPoint=endLatitude;				
				}
				
				else {// At least one point is entered
					
					
					if (startLongPoint != null && !startLongPoint.equalsIgnoreCase("") && (endLongPoint == null || endLongPoint.equalsIgnoreCase(""))) {
						
						//Start Longitude with start latitude
						if (startLatPoint != null && !startLatPoint.equalsIgnoreCase("") && (endLatPoint == null || endLatPoint.equalsIgnoreCase(""))) {
							cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) >"+startLongPoint +"and  to_number(SUBSTR(A.SOURCE_LAT,1,6)) >"+startLatPoint +" )"
									+ " OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(A.DESTINATION_LAT,1,6)) >"+startLatPoint +" ) "
									+ " OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(D.LATITUDE,1,6)) >"+startLatPoint +" ) "; 
						
							tubeStr = tubeStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLongPoint +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startLatPoint +" )"
									+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startLatPoint +") "
									+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >"+startLatPoint +") "; 
							
							strandStr = strandStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLongPoint +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startLatPoint +" )"
									+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startLatPoint +") "
									+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >"+startLatPoint +") "; 
							
							manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLongPoint +" and  to_number(SUBSTR(LATITUDE,1,6)) > "+startLatPoint +"  ) ";
							handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLongPoint +" and to_number(SUBSTR(LATITUDE,1,6)) > "+startLatPoint  +" ) ";
							dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(DB_LATITUDE,1,6)) >"+startLatPoint +" ) ";
							nodeStr = nodeStr+" AND ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLongPoint +" and to_number(SUBSTR(LATITUDE,1,6)) > "+startLatPoint  +" ) ";
						}
						//Start Longitude with end latitude
						else if (endLatPoint != null && !endLatPoint.equalsIgnoreCase("") && (startLatPoint == null || startLatPoint.equalsIgnoreCase(""))) {
							cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) >"+startLongPoint +" AND to_number (SUBSTR(A.SOURCE_LAT,1,6)) < " +endLatPoint +" )"
									+ " OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) > "+startLongPoint +" AND to_number (SUBSTR(A.DESTINATION_LAT,1,6)) <" +endLatPoint+" ) "
									+ " OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) > "+startLongPoint +" AND to_number (SUBSTR(D.LATITUDE,1,6)) <" +endLatPoint+" ) "; 
						
							tubeStr = tubeStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLongPoint +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) <"+endLatPoint +" )"
									+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) <"+endLatPoint +") "
									+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(a.LATITUDE,1,6)) <"+endLatPoint +") "; 
							
							strandStr = strandStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLongPoint +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) <"+endLatPoint +" )"
									+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) <"+endLatPoint +") "
									+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(a.LATITUDE,1,6)) <"+endLatPoint +") "; 
						
							manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLongPoint +" and  to_number(SUBSTR(LATITUDE,1,6)) < "+endLatPoint +"  ) ";
							handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLongPoint +" and to_number(SUBSTR(LATITUDE,1,6)) < "+endLatPoint  +" ) ";
							dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(DB_LATITUDE,1,6)) <"+endLatPoint +" ) ";
							nodeStr = nodeStr+" AND ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLongPoint +" and to_number(SUBSTR(LATITUDE,1,6)) < "+endLatPoint  +" ) ";
						}
						//Start Longitude with start & end latitude
						else if (startLatPoint != null && startLatPoint.length() > 0 && (endLatPoint != null && endLatPoint.length() > 0)) {
							
							if (Double.parseDouble(startLatPoint) < Double.parseDouble(endLatPoint)) {
								startlatitude = startLatPoint;
								endLatitude = endLatPoint;

							} else {
								startlatitude = endLatPoint;
								endLatitude = startLatPoint;
							}
							
							cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) >"+startLongPoint +"and  to_number(SUBSTR(A.SOURCE_LAT,1,6)) >"+startlatitude +" AND to_number (SUBSTR(A.SOURCE_LAT,1,6)) < " +endLatitude +" )"
									+ " OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(A.DESTINATION_LAT,1,6)) >"+startlatitude +" AND to_number (SUBSTR(A.DESTINATION_LAT,1,6)) <" +endLatitude+") "
									+ " OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(D.LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(D.LATITUDE,1,6)) <" +endLatitude+") "; 
						
							tubeStr = tubeStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLongPoint +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(b.SOURCE_LATITUDE,1,6)) < " +endLatitude +" )"
									+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(b.DESTINATION_LATITUDE,1,6)) <" +endLatitude+") "
									+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(a.LATITUDE,1,6)) <" +endLatitude+") "; 
							
							strandStr = strandStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLongPoint +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(b.SOURCE_LATITUDE,1,6)) < " +endLatitude +" )"
									+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(b.DESTINATION_LATITUDE,1,6)) <" +endLatitude+") "
									+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(a.LATITUDE,1,6)) <" +endLatitude+") "; 
					
							manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLongPoint +" and  to_number(SUBSTR(LATITUDE,1,6)) > "+startlatitude  +" and to_number (SUBSTR(LATITUDE,1,6)) < "+endLatitude +"  ) ";
							handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLongPoint +" and to_number(SUBSTR(LATITUDE,1,6)) > "+startlatitude  +" and to_number (SUBSTR(LATITUDE,1,6)) < "+endLatitude +" ) ";
							dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) > "+startLongPoint +"and  to_number(SUBSTR(DB_LATITUDE,1,6)) >"+startlatitude  +" and to_number (SUBSTR(DB_LATITUDE,1,6)) < "+endLatitude +" ) ";
							nodeStr = nodeStr+" AND ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLongPoint +" and to_number(SUBSTR(LATITUDE,1,6)) > "+startlatitude  +" and to_number (SUBSTR(LATITUDE,1,6)) < "+endLatitude +" ) ";

							//To return the correct start/end lat after the comparison above
							startLatPoint= startlatitude;
							endLatPoint=endLatitude;
						}
						//Only start longitude
						else {
							cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) >"+startLongPoint +" ) "
									+ " OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) > "+startLongPoint +" ) "
									+ " OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) > "+startLongPoint +" ) "; 
							
							tubeStr = tubeStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLongPoint+" )"
									+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > "+startLongPoint +") "
									+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > "+startLongPoint +") "; 
					
							strandStr = strandStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLongPoint+" )"
									+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > "+startLongPoint +") "
									+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > "+startLongPoint +") "; 
					
							manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLongPoint +"  ) ";
							handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLongPoint +" ) ";
							dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) > "+startLongPoint +" ) ";
							nodeStr = nodeStr+" AND ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLongPoint +" ) ";
					}								
				}//end the start long condition
				
				else if (endLongPoint != null && !endLongPoint.equalsIgnoreCase("") && (startLongPoint == null || startLongPoint.equalsIgnoreCase(""))) {
					
					//End Longitude with start latitude
					if (startLatPoint != null && !startLatPoint.equalsIgnoreCase("") && (endLatPoint == null || endLatPoint.equalsIgnoreCase(""))) {
						
						cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) <"+endLongPoint +"and  to_number(SUBSTR(A.SOURCE_LAT,1,6)) >"+startLatPoint +" )"
								+ " OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(A.DESTINATION_LAT,1,6)) >"+startLatPoint +" ) "
								+ " OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(D.LATITUDE,1,6)) >"+startLatPoint +" ) "; 
					
						tubeStr = tubeStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLongPoint +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startLatPoint +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startLatPoint +") "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >"+startLatPoint +") "; 
				
						strandStr = strandStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLongPoint +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startLatPoint +" )"
							+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startLatPoint +") "
							+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >"+startLatPoint +") "; 
			
						manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) < "+endLongPoint +" and  to_number(SUBSTR(LATITUDE,1,6)) > "+startLatPoint +"  ) ";
						handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) < "+endLongPoint +" and to_number(SUBSTR(LATITUDE,1,6)) > "+startLatPoint  +" ) ";
						dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(DB_LATITUDE,1,6)) >"+startLatPoint +" ) ";
						nodeStr = nodeStr+" AND ( to_number(SUBSTR(LONGITUDE,1,6)) < "+endLongPoint +" and to_number(SUBSTR(LATITUDE,1,6)) > "+startLatPoint  +" ) ";
					}
					//End Longitude with end latitude
					else if (endLatPoint != null && !endLatPoint.equalsIgnoreCase("") && (startLatPoint == null || startLatPoint.equalsIgnoreCase(""))) {
						cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) <"+endLongPoint +" AND to_number (SUBSTR(A.SOURCE_LAT,1,6)) < " +endLatPoint +" )"
								+ " OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) < "+endLongPoint +" AND to_number (SUBSTR(A.DESTINATION_LAT,1,6)) <" +endLatPoint+" ) "
								+ " OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) < "+endLongPoint +" AND to_number (SUBSTR(D.LATITUDE,1,6)) <" +endLatPoint+" ) "; 
					
						tubeStr = tubeStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLongPoint +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) <"+endLatPoint +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) <"+endLatPoint +") "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(a.LATITUDE,1,6)) <"+endLatPoint +") "; 
				
						strandStr = strandStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLongPoint +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) <"+endLatPoint +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) <"+endLatPoint +") "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(a.LATITUDE,1,6)) <"+endLatPoint +") "; 
				
						manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) < "+endLongPoint +" and  to_number(SUBSTR(LATITUDE,1,6)) < "+endLatPoint +"  ) ";
						handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) < "+endLongPoint +" and to_number(SUBSTR(LATITUDE,1,6)) < "+endLatPoint  +" ) ";
						dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(DB_LATITUDE,1,6)) < "+endLatPoint +" ) ";
						nodeStr = nodeStr+" AND ( to_number(SUBSTR(LONGITUDE,1,6)) < "+endLongPoint +" and to_number(SUBSTR(LATITUDE,1,6)) < "+endLatPoint  +" ) ";
					}							
					//End Longitude with start & end latitude
					else if (startLatPoint != null && startLatPoint.length() > 0 && (endLatPoint != null && endLatPoint.length() > 0)) {
						
						if (Double.parseDouble(startLatPoint) < Double.parseDouble(endLatPoint)) {
							startlatitude = startLatPoint;
							endLatitude = endLatPoint;
						} 
						else {
							startlatitude = endLatPoint;
							endLatitude = startLatPoint;
						}							
						cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) <"+endLongPoint +"and  to_number(SUBSTR(A.SOURCE_LAT,1,6)) >"+startlatitude +" AND to_number (SUBSTR(A.SOURCE_LAT,1,6)) < " +endLatitude +" )"
								+ " OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(A.DESTINATION_LAT,1,6)) >"+startlatitude +" AND to_number (SUBSTR(A.DESTINATION_LAT,1,6)) <" +endLatitude+") "
								+ " OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(D.LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(D.LATITUDE,1,6)) <" +endLatitude+") "; 
					
						tubeStr = tubeStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLongPoint +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(b.SOURCE_LATITUDE,1,6)) < " +endLatitude +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(b.DESTINATION_LATITUDE,1,6)) <" +endLatitude+") "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(a.LATITUDE,1,6)) <" +endLatitude+") "; 
				
						strandStr = strandStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLongPoint +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(b.SOURCE_LATITUDE,1,6)) < " +endLatitude +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(b.DESTINATION_LATITUDE,1,6)) <" +endLatitude+") "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(a.LATITUDE,1,6)) <" +endLatitude+") "; 
				
						manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) < "+endLongPoint +" and  to_number(SUBSTR(LATITUDE,1,6)) > "+startlatitude  +" and to_number (SUBSTR(LATITUDE,1,6)) < "+endLatitude +"  ) ";
						handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) < "+endLongPoint +" and to_number(SUBSTR(LATITUDE,1,6)) > "+startlatitude  +" and to_number (SUBSTR(LATITUDE,1,6)) < "+endLatitude +" ) ";
						dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) < "+endLongPoint +"and  to_number(SUBSTR(DB_LATITUDE,1,6)) >"+startlatitude  +" and to_number (SUBSTR(DB_LATITUDE,1,6)) < "+endLatitude +" ) ";
						nodeStr = nodeStr+" AND ( to_number(SUBSTR(LONGITUDE,1,6)) < "+endLongPoint +" and to_number(SUBSTR(LATITUDE,1,6)) > "+startlatitude  +" and to_number (SUBSTR(LATITUDE,1,6)) < "+endLatitude +" ) ";
						
						//To return the correct start/end lat after the comparison above
						startLatPoint= startlatitude;
						endLatPoint=endLatitude;
					}
					//Only end longitude
					else {
						cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) <"+endLongPoint +" ) "
								+ " OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) < "+endLongPoint +" ) "
								+ " OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) < "+endLongPoint +" ) "; 
						
						tubeStr = tubeStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLongPoint+" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLongPoint +") "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLongPoint +") "; 
						
						strandStr = strandStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLongPoint+" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLongPoint +") "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLongPoint +") "; 
						
						manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) < "+endLongPoint +"  ) ";
						handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) < "+endLongPoint +" ) ";
						dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) < "+endLongPoint +" ) ";
						nodeStr = nodeStr+" AND ( to_number(SUBSTR(LONGITUDE,1,6)) < "+endLongPoint +" ) ";
				}				
			 }// end the End long condition
					
				else if ((startLongPoint != null && !startLongPoint.equalsIgnoreCase("") ) && (endLongPoint != null && !endLongPoint.equalsIgnoreCase(""))) {
					
					if (Double.parseDouble(startLongPoint) < Double.parseDouble(endLongPoint)) {
						startLng = startLongPoint;
						endLng = endLongPoint;
					} 
					else {
						startLng = endLongPoint;
						endLng = startLongPoint;
					}
					//Start & end longitude with start latitude
					if (startLatPoint != null && !startLatPoint.equalsIgnoreCase("") && (endLatPoint == null || endLatPoint.equalsIgnoreCase(""))) {
						cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) >"+startLng +"and  to_number(SUBSTR(A.SOURCE_LAT,1,6)) >"+startLatPoint +" and to_number (SUBSTR(A.SOURCE_LNG,1,6)) <"+endLng +" )"
								+ " OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) > "+startLng +"and  to_number(SUBSTR(A.DESTINATION_LAT,1,6)) >"+startLatPoint +" and to_number (SUBSTR(A.DESTINATION_LNG,1,6)) < "+endLng+") "
								+ " OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) > "+startLng +"and  to_number(SUBSTR(D.LATITUDE,1,6)) >"+startLatPoint +" and to_number (SUBSTR(D.LONGITUDE,1,6)) < "+endLng+") "; 
					
					
						tubeStr = tubeStr +" WHERE (to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLng +" and to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLng +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startLatPoint +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) >"+startLng +"and to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLng +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startLatPoint +") "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) >"+startLng +"and to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLng +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >"+startLatPoint +") "; 
				
						strandStr = strandStr +" WHERE (to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLng +" and to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLng +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startLatPoint +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) >"+startLng +"and to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLng +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startLatPoint +") "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) >"+startLng +"and to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLng +"and  to_number(SUBSTR(a.LATITUDE,1,6)) >"+startLatPoint +") "; 
				

						manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(LONGITUDE,1,6)) <"+endLng +" and  to_number(SUBSTR(LATITUDE,1,6)) > "+startLatPoint +"  ) ";
						handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(LONGITUDE,1,6)) <"+endLng +" and to_number(SUBSTR(LATITUDE,1,6)) > "+startLatPoint +" ) ";
						dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(DB_LONGITUDE,1,6)) <"+endLng +"and  to_number(SUBSTR(DB_LATITUDE,1,6)) >"+startLatPoint +" ) ";
						nodeStr = nodeStr+" AND ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(LONGITUDE,1,6)) <"+endLng +" and to_number(SUBSTR(LATITUDE,1,6)) > "+startLatPoint +" ) ";
					}
					//Start & end longitude with end latitude
					else if (endLatPoint != null && !endLatPoint.equalsIgnoreCase("") && (startLatPoint == null || startLatPoint.equalsIgnoreCase(""))) {
						cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) >"+startLng +"and  to_number(SUBSTR(A.SOURCE_LAT,1,6)) <"+endLatPoint +" and to_number (SUBSTR(A.SOURCE_LNG,1,6)) <"+endLng +" )"
								+ " OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) > "+startLng +"and  to_number(SUBSTR(A.DESTINATION_LAT,1,6)) <"+endLatPoint +" and to_number (SUBSTR(A.DESTINATION_LNG,1,6)) < "+endLng+") "
								+ " OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) > "+startLng +"and  to_number(SUBSTR(D.LATITUDE,1,6)) <"+endLatPoint +" and to_number (SUBSTR(D.LONGITUDE,1,6)) < "+endLng+") "; 
					

						tubeStr = tubeStr +" WHERE (to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLng +" and to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLng +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) <"+endLatPoint +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) >"+startLng +"and to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLng +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) <"+endLatPoint +") "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) >"+startLng +"and to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLng +"and  to_number(SUBSTR(a.LATITUDE,1,6)) <"+endLatPoint +") "; 
				
						strandStr = strandStr +" WHERE (to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLng +" and to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLng +"and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) <"+endLatPoint +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) >"+startLng +"and to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLng +"and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) <"+endLatPoint +") "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) >"+startLng +"and to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLng +"and  to_number(SUBSTR(a.LATITUDE,1,6)) <"+endLatPoint +") "; 
				
						manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(LONGITUDE,1,6)) <"+endLng +" and  to_number(SUBSTR(LATITUDE,1,6)) < "+endLatPoint +"  ) ";
						handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(LONGITUDE,1,6)) <"+endLng +" and to_number(SUBSTR(LATITUDE,1,6)) < "+endLatPoint +" ) ";
						dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(DB_LONGITUDE,1,6)) <"+endLng +"and  to_number(SUBSTR(DB_LATITUDE,1,6)) < "+endLatPoint +" ) ";
						nodeStr = nodeStr+" AND ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(LONGITUDE,1,6)) <"+endLng +" and to_number(SUBSTR(LATITUDE,1,6)) < "+endLatPoint +" ) ";
				 }
				//Only Start & end longitude
				else {
					cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) >"+startLng +" and to_number (SUBSTR(A.SOURCE_LNG,1,6)) <"+endLng +" )"
								+ " OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) > "+startLng +" and to_number (SUBSTR(A.DESTINATION_LNG,1,6)) < "+endLng+") "
								+ " OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(D.LONGITUDE,1,6)) < "+endLng+") "; 							

					tubeStr = tubeStr +" WHERE (to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLng +" and to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLng +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) >"+startLng +"and to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLng +" ) "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) >"+startLng +"and to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLng +" ) "; 
				
					strandStr = strandStr +" WHERE (to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) >"+startLng +" and to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) <"+endLng +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) >"+startLng +"and to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < "+endLng +" ) "
								+ " OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) >"+startLng +"and to_number(SUBSTR(a.LONGITUDE,1,6)) < "+endLng +" ) "; 
				
					manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(LONGITUDE,1,6)) <"+endLng +"  ) ";
					handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(LONGITUDE,1,6)) <"+endLng +" ) ";
					dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(DB_LONGITUDE,1,6)) <"+endLng +" ) ";
					nodeStr = nodeStr+" AND ( to_number(SUBSTR(LONGITUDE,1,6)) > "+startLng +" and to_number (SUBSTR(LONGITUDE,1,6)) <"+endLng +" ) ";
				}	
				//To return the correct start/end long after the comparison above	
				startLongPoint=startLng;
				endLongPoint= endLng;
					
				}// end the start / end long condition
				
				else { //No longitude , only latitude
					
					if (startLatPoint != null && !startLatPoint.equalsIgnoreCase("") && (endLatPoint == null || endLatPoint.equalsIgnoreCase(""))) {
						cableStr = cableStr +" WHERE ( to_number(SUBSTR(A.SOURCE_LAT,1,6)) >"+startLatPoint +" )"
								+ " OR ( to_number(SUBSTR(A.DESTINATION_LAT,1,6)) >"+startLatPoint +" ) "
								+ " OR ( to_number(SUBSTR(D.LATITUDE,1,6)) >"+startLatPoint +" ) "; 
						
						tubeStr = tubeStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startLatPoint +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startLatPoint +") "
								+ " OR ( to_number(SUBSTR(a.LATITUDE,1,6)) >"+startLatPoint +") "; 
				
						strandStr = strandStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startLatPoint +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startLatPoint +") "
								+ " OR ( to_number(SUBSTR(a.LATITUDE,1,6)) >"+startLatPoint +") "; 
				
						manholeStr = manholeStr+" WHERE (to_number(SUBSTR(LATITUDE,1,6)) > "+startLatPoint +"  ) ";
						handholeStr = handholeStr+" WHERE (to_number(SUBSTR(LATITUDE,1,6)) > "+startLatPoint +" ) ";
						dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LATITUDE,1,6)) > "+startLatPoint +" ) ";
						nodeStr = nodeStr+" AND (to_number(SUBSTR(LATITUDE,1,6)) > "+startLatPoint +" ) ";
					}
					else if (endLatPoint != null && !endLatPoint.equalsIgnoreCase("") && (startLatPoint == null || startLatPoint.equalsIgnoreCase(""))) {
						cableStr = cableStr +" WHERE (  to_number (SUBSTR(A.SOURCE_LAT,1,6)) < " +endLatPoint +" )"
								+ " OR ( to_number (SUBSTR(A.DESTINATION_LAT,1,6)) <" +endLatPoint+" ) "
								+ " OR ( to_number (SUBSTR(D.LATITUDE,1,6)) <" +endLatPoint+" ) "; 
						
						tubeStr = tubeStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) < "+endLatPoint +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) < "+endLatPoint +") "
								+ " OR ( to_number(SUBSTR(a.LATITUDE,1,6)) < "+endLatPoint +") "; 
						
						strandStr = strandStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) < "+endLatPoint +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) < "+endLatPoint +") "
								+ " OR ( to_number(SUBSTR(a.LATITUDE,1,6)) < "+endLatPoint +") "; 

						manholeStr = manholeStr+" WHERE (to_number(SUBSTR(LATITUDE,1,6)) < "+endLatPoint +"  ) ";
						handholeStr = handholeStr+" WHERE (to_number(SUBSTR(LATITUDE,1,6)) < "+endLatPoint +" ) ";
						dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LATITUDE,1,6)) < "+endLatPoint +" ) ";
						nodeStr = nodeStr+" AND (to_number(SUBSTR(LATITUDE,1,6)) < "+endLatPoint +" ) ";
					}
					else if (startLatPoint != null && startLatPoint.length() > 0 && (endLatPoint != null && endLatPoint.length() > 0)) {
						if (Double.parseDouble(startLatPoint) < Double.parseDouble(endLatPoint)) {
							startlatitude = startLatPoint;
							endLatitude = endLatPoint;
						} 
						else {
							startlatitude = endLatPoint;
							endLatitude = startLatPoint;
						}						
						cableStr = cableStr +" WHERE (  to_number(SUBSTR(A.SOURCE_LAT,1,6)) >"+startlatitude +" AND to_number (SUBSTR(A.SOURCE_LAT,1,6)) < " +endLatitude +" )"
								+ " OR ( to_number(SUBSTR(A.DESTINATION_LAT,1,6)) >"+startlatitude +" AND to_number (SUBSTR(A.DESTINATION_LAT,1,6)) <" +endLatitude+") "
								+ " OR ( to_number(SUBSTR(D.LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(D.LATITUDE,1,6)) <" +endLatitude+") "; 
					
						tubeStr = tubeStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(b.SOURCE_LATITUDE,1,6)) < " +endLatitude +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(b.DESTINATION_LATITUDE,1,6)) <" +endLatitude+") "
								+ " OR ( to_number(SUBSTR(a.LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(a.LATITUDE,1,6)) <" +endLatitude+") "; 
				
						strandStr = strandStr +" WHERE ( to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(b.SOURCE_LATITUDE,1,6)) < " +endLatitude +" )"
								+ " OR ( to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(b.DESTINATION_LATITUDE,1,6)) <" +endLatitude+") "
								+ " OR ( to_number(SUBSTR(a.LATITUDE,1,6)) >"+startlatitude +" AND to_number (SUBSTR(a.LATITUDE,1,6)) <" +endLatitude+") "; 
				
						manholeStr = manholeStr+" WHERE ( to_number(SUBSTR(LATITUDE,1,6)) > "+startlatitude  +" and to_number (SUBSTR(LATITUDE,1,6)) < "+endLatitude +"  ) ";
						handholeStr = handholeStr+" WHERE ( to_number(SUBSTR(LATITUDE,1,6)) > "+startlatitude  +" and to_number (SUBSTR(LATITUDE,1,6)) < "+endLatitude +" ) ";
						dbStr = dbStr+" WHERE ( to_number(SUBSTR(DB_LATITUDE,1,6)) >"+startlatitude  +" and to_number (SUBSTR(DB_LATITUDE,1,6)) < "+endLatitude +" ) ";
						nodeStr = nodeStr+" AND ( to_number(SUBSTR(LATITUDE,1,6)) > "+startlatitude  +" and to_number (SUBSTR(LATITUDE,1,6)) < "+endLatitude +" ) ";
						
						//To return the correct start/end lat after the comparison above	
						startLatPoint= startlatitude;
						endLatPoint=endLatitude;
					}
				}// end no longitude (only latitude) condition
					
			}//end else
				
				fiberList = session.createNativeQuery(cableStr).getResultList();
				List<String> cablesIDs = Arrays.asList((findListId(fiberList, "FiberCable")).length > 0 ? findListId(fiberList, "FiberCable") : new String[] { "" });
			    combinedCablesList.addAll(cablesIDs);// used in get related points
			    findIDsSrcDestStrtEndCoord(fiberList,"Cables", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs,startLongPoint,startLatPoint,endLongPoint,endLatPoint);

			    fiberTubes = session.createNativeQuery(tubeStr).getResultList();
			    List<String> tubesIDs = Arrays.asList((findListId(fiberTubes, "Tube")).length > 0 ? findListId(fiberTubes, "Tube") : new String[] { "" });
				combinedTubeList.addAll(tubesIDs);// used in get related points
			    findIDsSrcDestStrtEndCoord(fiberTubes,"Tubes", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs,startLongPoint,startLatPoint,endLongPoint,endLatPoint);

			    fiberStrands = session.createNativeQuery(strandStr).getResultList();
			    List<String> strandsIDs = Arrays.asList((findListId(fiberStrands, "Strand")).length > 0 ? findListId(fiberStrands, "Strand") : new String[] { "" });
			    findIDsSrcDestStrtEndCoord(fiberStrands,"Strands", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs,startLongPoint,startLatPoint,endLongPoint,endLatPoint);
			 
			    //Get all auxiliaries of each cable
			    int batchSize = 1000;

			    if (!cablesIDs.isEmpty()) {
			        for (int i = 0; i < cablesIDs.size(); i += batchSize) {
			            List<String> sublist = cablesIDs.subList(i, Math.min(i + batchSize, cablesIDs.size()));
			            
			            Query query = session.createNativeQuery(
			                "SELECT B.LONGITUDE, B.LATITUDE, B.DISTANCE_FROM_SOURCE, B.WARE_ID, B.AUXILIARY_POINT_ID, B.AUXILIARY_POINT_NAME, B.FIBER_CABLE_ID, B.AUXILIARY_ID " +
			                "FROM FIBER_CABLES A, FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID = B.FIBER_CABLE_ID " +
			                "AND B.FIBER_CABLE_ID IN (:param) ORDER BY B.SEQ_SORTING ASC"
			            ).setParameter("param", sublist);

			            List<Object[]> results = query.getResultList();
			            fiberAuxiliary_Data.addAll(results);  // Add results to existing list
			        }
			        findIDsForAuxStrtEndCoord(fiberAuxiliary_Data, "Cables", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs, startLongPoint, startLatPoint, endLongPoint, endLatPoint);
			    }
				//Get all auxiliaries of each tube
			    if (!tubesIDs.isEmpty()) {
			        for (int i = 0; i < tubesIDs.size(); i += batchSize) {
			            List<String> sublist = tubesIDs.subList(i, Math.min(i + batchSize, tubesIDs.size()));
			            
			            Query query = session.createNativeQuery(
			                "SELECT DISTINCT c.TUBE_ID, c.LONGITUDE, c.LATITUDE, c.WARE_ID, c.AUXILIARY_POINT_ID, c.AUXILIARY_POINT_NAME, c.DISTANCE_FROM_SOURCE, c.SEQ_SORTING, c.AUXILIARY_ID, c.DRIVING_DISTANCE, c.GEO_DISTANCE " +
			                "FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON b.TUBE_ID = c.TUBE_ID " +
			                "WHERE c.TUBE_ID IN (:param) ORDER BY c.SEQ_SORTING ASC"
			            ).setParameter("param", sublist);

			            List<Object[]> results = query.getResultList();
			            tubesAuxiliaries.addAll(results);  // Add results to existing list
			        }
			        findIDsForAuxStrtEndCoord(tubesAuxiliaries, "Tubes", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs, startLongPoint, startLatPoint, endLongPoint, endLatPoint);
			    }
				//Get all auxiliaries of each strand	
			    if (!strandsIDs.isEmpty()) {
			        for (int i = 0; i < strandsIDs.size(); i += batchSize) {
			            List<String> sublist = strandsIDs.subList(i, Math.min(i + batchSize, strandsIDs.size()));
			            
			            Query query = session.createNativeQuery(
			                "SELECT DISTINCT c.STRAND_ID, c.LONGITUDE, c.LATITUDE, c.WARE_ID, c.AUXILIARY_POINT_ID, c.AUXILIARY_POINT_NAME, c.DISTANCE_FROM_SOURCE, c.SEQ_SORTING, c.AUXILIARY_ID, c.DRIVING_DISTANCE, c.GEO_DISTANCE " +
			                "FROM STRAND_AUXILIARY_POINTS c, FIBER_STRANDS b WHERE b.STRAND_ID = c.STRAND_ID " +
			                "AND c.STRAND_ID IN (:param) ORDER BY c.SEQ_SORTING ASC"
			            ).setParameter("param", sublist);

			            List<Object[]> results = query.getResultList();
			            strandsAuxiliaries.addAll(results);  // Add results to existing list
			        }
			        findIDsForAuxStrtEndCoord(strandsAuxiliaries, "Strands", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs, startLongPoint, startLatPoint, endLongPoint, endLatPoint);
			    }
				//Check and Select if there is tubes that are outside the range but have strands in range and not selected before
			    if (!fiberStrands.isEmpty()) {
			        for (int i = 0; i < strandsIDs.size(); i += batchSize) {
			            List<String> sublistStrands = strandsIDs.subList(i, Math.min(i + batchSize, strandsIDs.size()));
			            
			            // Query for tubes based on strand IDs
			            Query query = session.createNativeQuery(
			                "SELECT DISTINCT b.TUBE_ID, b.SOURCE_LONGITUDE, b.SOURCE_LATITUDE, b.DESTINATION_LONGITUDE, b.DESTINATION_LATITUDE, " +
			                "b.SOURCE_WARE_ID, b.SOURCE_ID, b.SOURCE_NAME, b.DESTINATION_WARE_ID, b.DESTINATION_ID, b.DESTINATION_NAME, " +
			                "(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID = b.TUBE_ID) AS Strand_Count, b.FIBER_CABLE_ID, " +
			                "b.TUBE_NAME, b.DRAWING_TYPE, b.TUBE_NUMBER, b.TUBE_COLOR " +
			                "FROM FIBER_TUBES b LEFT JOIN FIBER_STRANDS E ON E.TUBE_ID = b.TUBE_ID " +
			                "WHERE E.STRAND_ID IN (:param) AND b.TUBE_ID NOT IN (:param1)"
			            ).setParameter("param", sublistStrands).setParameter("param1", tubesIDs);

			            // Get query results and convert to List<String>
			            List<Object[]> queryResults = query.getResultList();
			            String[] tubeIDsArray = findListId(queryResults, "Tube");
			            List<String> outOfRangeTubeIDs = Arrays.asList(tubeIDsArray.length > 0 ? tubeIDsArray : new String[] { "" });
			            combinedTubeList.addAll(outOfRangeTubeIDs);  // Add IDs to combined list
			            
			            // Add results to fiberTubes list
			            if (!fiberTubes.isEmpty()) {
			                fiberTubes.addAll(queryResults);
			            } else {
			                fiberTubes = queryResults;
			            }
			            
			            // Query for auxiliary points for out-of-range tubes
			            Query queryAux = session.createNativeQuery(
			                "SELECT DISTINCT c.TUBE_ID, c.LONGITUDE, c.LATITUDE, c.WARE_ID, c.AUXILIARY_POINT_ID, c.AUXILIARY_POINT_NAME, " +
			                "c.DISTANCE_FROM_SOURCE, c.SEQ_SORTING, c.AUXILIARY_ID, c.DRIVING_DISTANCE, c.GEO_DISTANCE " +
			                "FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON b.TUBE_ID = c.TUBE_ID " +
			                "WHERE c.TUBE_ID IN (:param) ORDER BY c.SEQ_SORTING ASC"
			            ).setParameter("param", outOfRangeTubeIDs);

			            List<Object[]> tubeAuxiliariesResults = queryAux.getResultList();
			            if (!tubesAuxiliaries.isEmpty()) {
			                tubesAuxiliaries.addAll(tubeAuxiliariesResults);
			            } else {
			                tubesAuxiliaries = tubeAuxiliariesResults;
			            }
			        }
			    }

			//Check and Select if there is cables that are outside the range but have tubes in range and not selected before
			    if (!fiberTubes.isEmpty()) {
			    	 for (int i = 0; i < combinedTubeList.size(); i += batchSize) {
			    	        List<String> sublistTubeIDs = combinedTubeList.subList(i, Math.min(i + batchSize, combinedTubeList.size()));
			    	        
			    	        // Process cables in batches
			    	        for (int j = 0; j < cablesIDs.size(); j += batchSize) {
			    	            List<String> sublistCableIDs = cablesIDs.subList(j, Math.min(j + batchSize, cablesIDs.size()));
			    	            
			    	            // Query for fiber cables based on tube IDs
			    	            Query query = session.createNativeQuery(
			    	                "SELECT DISTINCT A.SOURCE_LNG, A.SOURCE_LAT, A.DESTINATION_LNG, A.DESTINATION_LAT, A.FIBER_CABLE_ID, " +
			    	                "A.SOURCE_WARE_ID, A.SOURCE_ID, A.SOURCE_NAME, A.DESTINATION_WARE_ID, A.DESTINATION_ID, A.DESTINATION_NAME, " +
			    	                "(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID = A.FIBER_CABLE_ID) AS Tube_Count, " +
			    	                "(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID = A.FIBER_CABLE_ID) AS Strand_Count, " +
			    	                "A.FIBER_CABLE_NAME, A.PROJECT_ID, A.SOURCE_CITY, A.DESTINATION_CITY, A.NUMBER_OF_TUBES, A.NUMBER_OF_STRANDS, " +
			    	                "A.LENGTH, A.DRAWING_TYPE, A.FIBER_NETWORK_LEVEL, A.FIBER_OWNER, " +
			    	                "(SELECT B.FIBER_COLOR_OWNER FROM FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER = A.FIBER_OWNER) AS FIBER_CABLE_COLOR " +
			    	                "FROM FIBER_CABLES A LEFT JOIN FIBER_TUBES E ON E.FIBER_CABLE_ID = A.FIBER_CABLE_ID " +
			    	                "WHERE E.TUBE_ID IN (:tubeParam) AND A.FIBER_CABLE_ID NOT IN (:cableParam)"
			    	            ).setParameter("tubeParam", sublistTubeIDs).setParameter("cableParam", sublistCableIDs);

			    	            // Convert the result to a list of String IDs
			    	            List<Object[]> queryResults = query.getResultList();
			    	            String[] cableIDsArray = findListId(queryResults, "FiberCable");
			    	            List<String> outOfRangeCableIDs = Arrays.asList(cableIDsArray.length > 0 ? cableIDsArray : new String[] { "" });
			    	            combinedCablesList.addAll(outOfRangeCableIDs); // Add IDs to combined list
			    	            
			    	            // Add results to fiberList
			    	            fiberList.addAll(queryResults);
			    	            
			    	            // Query for auxiliary points for out-of-range cables
			    	            Query queryAux = session.createNativeQuery(
			    	                "SELECT B.LONGITUDE, B.LATITUDE, B.DISTANCE_FROM_SOURCE, B.WARE_ID, B.AUXILIARY_POINT_ID, " +
			    	                "B.AUXILIARY_POINT_NAME, B.FIBER_CABLE_ID, B.AUXILIARY_ID " +
			    	                "FROM FIBER_CABLES A LEFT JOIN FIBER_AUXILIARY_POINTS B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID " +
			    	                "WHERE B.FIBER_CABLE_ID IN (:cableAuxParam) ORDER BY B.SEQ_SORTING ASC"
			    	            ).setParameter("cableAuxParam", outOfRangeCableIDs);

			    	            List<Object[]> cableAuxiliariesResults = queryAux.getResultList();
			    	            fiberAuxiliary_Data.addAll(cableAuxiliariesResults);
			    	        }
			    	    }
			    }

			    
			 // Process mhFilteredIDs in batches
			 for (int i = 0; i < mhFilteredIDs.size(); i += batchSize) {
			     // Get the current batch
			     List<String> batchList = mhFilteredIDs.subList(i, Math.min(i + batchSize, mhFilteredIDs.size()));
			     
			     // Build the query string for the current batch
			      manholeStr = manholeStr + " AND MANHOLE_ID IN (:param)";
			     
			     // Create and execute the query
			     Query query = session.createNativeQuery(manholeStr);
			     query.setParameter("param", batchList);
			     
			     // Retrieve and accumulate results
			     List<Object[]> batchResults = query.getResultList();
			     manholeList.addAll(batchResults);
			 }
			handholeStr = handholeStr + " OR HANDHOLE_ID IN (:param) ";
			handholeList = session.createNativeQuery(handholeStr).setParameter("param",hhFilteredIDs).getResultList();

			dbStr = dbStr + " OR DB_ID IN (:param) ";
			distribBoardList = session.createNativeQuery(dbStr).setParameter("param",dbFilteredIDs).getResultList();
			
			
			nodeStr = nodeStr + " OR NODE_PK IN (:param) ";
			NodeList = session.createNativeQuery(nodeStr).setParameter("param",NodeList).getResultList();
			
			// To select the data needed in show points/real points & are outside the range
			if (getRelatedPoints.equals("1")) {
				
				
				String[] manholesId = (findListId(manholeList, "all")).length > 0 ? findListId(manholeList, "all") : new String[] { "A" };										
				String[] handholesId = (findListId(handholeList, "all")).length > 0 ? findListId(handholeList, "all") : new String[] { "A" };										
				String[] dbsId = (findListId(distribBoardList, "all")).length > 0 ? findListId(distribBoardList, "all") : new String[] { "A" };										

				//MH that are outside the range
				query = session.createNativeQuery(
						" SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.FIBER_CABLE_ID IN (:param1) "
						+ " UNION "
						+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1) "
						+ " UNION "
						+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1)  ) ");
							
				query.setParameter("param1",combinedCablesList);
				tempList.addAll(query.getResultList());
				
				query = session.createNativeQuery("SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_TUBES C ON C.TUBE_ID = B.TUBE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.TUBE_ID IN (:param1) " + 
						" UNION "
						+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.TUBE_ID IN (:param1) " + 
						" UNION "
						+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.TUBE_ID IN (:param1) ) ");
				
				query.setParameter("param1",combinedTubeList);
				tempList.addAll(query.getResultList());
				
			
			query = session.createNativeQuery("SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_STRANDS C ON C.STRAND_ID = B.STRAND_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.STRAND_ID IN (:param1) " + 
					" UNION "
					+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.STRAND_ID IN (:param1) " + 
					" UNION "
					+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.STRAND_ID IN (:param1) ) ");
			
			query.setParameter("param1",strandsIDs);
			tempList.addAll(query.getResultList());		

			if (manholeList.size() > 0) {
				System.out.println("manholesId "+mapper.writeValueAsString(manholesId));
				System.out.println("tempList "+mapper.writeValueAsString(tempList));

				newList = filterTempList(tempList,manholesId);
				manholeList.addAll(newList);
			}								 
			else {
				manholeList = filterTempList(tempList,manholesId);
			}								

			tempList.clear();

			// HH that are outside the range
			query = session.createNativeQuery(
				" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.FIBER_CABLE_ID IN (:param1) "
				+ " UNION "
				+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) "
				+ "UNION"
				+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) )  ");
			query.setParameter("param1",combinedCablesList);
			tempList.addAll(query.getResultList());		
				
			query = session.createNativeQuery(
					" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_TUBES C ON C.TUBE_ID = B.TUBE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.TUBE_ID IN (:param1) "
					+ " UNION "
					+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.TUBE_ID IN (:param1) "
					+ "UNION"
					+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.TUBE_ID IN (:param1) ) ");
			query.setParameter("param1",combinedTubeList);
			tempList.addAll(query.getResultList());		

			query = session.createNativeQuery(
					" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_STRANDS C ON C.STRAND_ID = B.STRAND_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.STRAND_ID IN (:param1) "
					+ " UNION "
					+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.STRAND_ID IN (:param1) "
					+ "UNION"
					+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.STRAND_ID IN (:param1) )  ");
			query.setParameter("param1",strandsIDs);
			tempList.addAll(query.getResultList());		

			
			if (handholeList.size() > 0) {
				newList = filterTempList(tempList,handholesId);
				handholeList.addAll(newList);
			} 						
			else {
				handholeList = filterTempList(tempList,handholesId);
			}
			
			tempList.clear();
			
			//DB that are outside the range
			query = session.createNativeQuery(
					" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID, trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE, A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.FIBER_CABLE_ID IN (:param1)   "
							+ " UNION "
							+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) "
							+ "UNION "
							+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) )   ");
			query.setParameter("param1",combinedCablesList);
			tempList.addAll(query.getResultList());		
			
			query = session.createNativeQuery(
					" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID, trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE, A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_TUBES C ON C.TUBE_ID = B.TUBE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.TUBE_ID IN (:param1)   "
							+ " UNION "
							+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.TUBE_ID IN (:param1) "
							+ "UNION "
							+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.TUBE_ID IN (:param1) )  ");
			query.setParameter("param1",combinedTubeList);
			tempList.addAll(query.getResultList());
			
			query = session.createNativeQuery(
					" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID, trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE, A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_STRANDS C ON C.STRAND_ID = B.STRAND_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.STRAND_ID IN (:param1)   "
							+ " UNION "
							+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.STRAND_ID IN (:param1) "
							+ "UNION "
							+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.STRAND_ID IN (:param1) ) ");
			query.setParameter("param1",strandsIDs);
			tempList.addAll(query.getResultList());
			
			if (distribBoardList.size() > 0) {
				newList = filterTempList(tempList,dbsId);
				distribBoardList.addAll(newList);
			} 
			else {
				distribBoardList = filterTempList(tempList,dbsId);
			}
			
		}
			junctionManholeList = session.createNativeQuery(
					"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE,trim(replace(A.LATITUDE,'�','')) as LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id ").getResultList();
			
			junctionHandholeList = session.createNativeQuery(
					"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE,trim(replace(A.LATITUDE,'�','')) as LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id ").getResultList();
						
			model.addAttribute("startLongPoint", startLongPoint);
			model.addAttribute("startLatPoint", startLatPoint);
			model.addAttribute("endLongPoint", endLongPoint);
			model.addAttribute("endLatPoint", endLatPoint);
			model.addAttribute("getRelatedPoints", getRelatedPoints);
				
		}										

			else if (StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "circleRange_multy")) {
							String[] seqs = request.getParameterValues("seq");
							String[] siteIds = request.getParameterValues("name");
							String[] lngs = request.getParameterValues("lng");
							String[] lats = request.getParameterValues("lat");
							String noOfPoints = request.getParameter("nop");
							String closestDisRange = request.getParameter("closestDisRange");
							String showPointsType = request.getParameter("getRelatedPoints");

							LinkedHashMap<String, List<?>> rowData = new LinkedHashMap<>();
							LinkedHashMap<String, LinkedHashMap<String, List<?>>> ptPhysicalLayerList = new LinkedHashMap<String, LinkedHashMap<String, List<?>>>();
							LinkedHashMap<String, List<?>> ptPhysicalListResult = new LinkedHashMap<String, List<?>>();
							LinkedHashMap<String, LinkedHashMap<String, List<?>>> ptPhysicalLayerData = new LinkedHashMap<String, LinkedHashMap<String, List<?>>>();
							LinkedHashMap<String, List<?>> ptPhysicalDataResult = new LinkedHashMap<String, List<?>>();

							for (int i = 0; i < seqs.length; i++) {
								String lng = lngs[i];
								String lat = lats[i];

								Map<String, Object> row = new HashMap<>();
								row.put("seq", seqs[i]);
								row.put("siteId", siteIds[i]);
								row.put("lat", lats[i]);
								row.put("lng", lngs[i]);
								rowData.put("row" + i, new ArrayList<>(row.values()));

								 if(readManhole.equals("1")) {
										List<Object[]> manholeListQuery = session.createNativeQuery(
												"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),DM_NAME FROM MANHOLE  where to_number(SUBSTR(LONGITUDE,1,6)) <=  "
														+ lng + " and  to_number(SUBSTR(LATITUDE,1,6)) <= " + lat + " ")
												.getResultList();

										manholeListPt = findNearestArray(manholeListQuery, Double.valueOf(lats[i]),
												Double.valueOf(lngs[i]), Double.valueOf(closestDisRange), "Manhole",
												noOfPoints);
										 }
										 if(readHandhole.equals("1")) {
										List<Object[]> handholeListQuery = session.createNativeQuery(
												"SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),DM_NAME FROM HANDHOLE  where to_number(LONGITUDE) <=  '"
														+ lng + "' and  to_number(LATITUDE) <= '" + lat + "' ")
												.getResultList();

										handholeListPt = findNearestArray(handholeListQuery, Double.valueOf(lats[i]),
												Double.valueOf(lngs[i]), Double.valueOf(closestDisRange), "Handhole",
												noOfPoints);
										 }
										 if("1".equals(readDB) ) {
								List<Object[]> distribBoardListQuery = session.createNativeQuery(
										"SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY,DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD  where to_number(SUBSTR(DB_LONGITUDE,1,6)) <=  "
												+ lng + " and  to_number(SUBSTR(DB_LATITUDE,1,6)) <= " + lat + " ")
										.getResultList();
										 
								distribBoardListPt = findNearestArray(distribBoardListQuery, Double.valueOf(lats[i]),
										Double.valueOf(lngs[i]), Double.valueOf(closestDisRange), "DistribBoard",
										noOfPoints);
										 }
								List<Object[]> nearstPoints = new ArrayList<Object[]>();
								nearstPoints.addAll(manholeListPt);
								nearstPoints.addAll(handholeListPt);
								nearstPoints.addAll(distribBoardListPt);

								String[] idsArray = (findListId(nearstPoints, "all")).length > 0
										? findListId(nearstPoints, "all")
										: new String[] { "" };
								List<String> mpIdsList = Arrays.asList(idsArray);
								if("1".equals(readFiber) ) {
								Query fiberQuery = session.createNativeQuery(
										"SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL FROM FIBER_CABLES A "
												+ "LEFT  JOIN FIBER_AUXILIARY_POINTS D ON A.FIBER_CABLE_ID=D.FIBER_CABLE_ID "
												+ "LEFT  JOIN FIBER_TUBES B ON A.FIBER_CABLE_ID=B.FIBER_CABLE_ID "
												+ "LEFT  JOIN FIBER_STRANDS C ON A.FIBER_CABLE_ID=C.FIBER_CABLE_ID "
												+ "LEFT  JOIN TUBE_AUXILIARY_POINTS E ON A.FIBER_CABLE_ID=E.FIBER_CABLE_ID "
												+ "LEFT  JOIN STRAND_AUXILIARY_POINTS F ON A.FIBER_CABLE_ID=F.FIBER_CABLE_ID "
												+ "WHERE A.SOURCE_ID IN (:param1) OR A.DESTINATION_ID IN (:param1) OR D.AUXILIARY_POINT_ID IN (:param1) OR B.SOURCE_ID IN (:param1) OR B.DESTINATION_ID IN (:param1) OR C.SOURCE_ID IN (:param1) OR C.DESTINATION_ID IN (:param1) OR E.AUXILIARY_POINT_ID IN (:param1) OR F.AUXILIARY_POINT_ID IN (:param1) ");

								fiberQuery.setParameter("param1", mpIdsList);
								fiberListPt = fiberQuery.getResultList();

								Query fiberAuxiliaryQuery = session.createNativeQuery(
										"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID IN (:param1) ORDER BY B.SEQ_SORTING ASC");
								fiberAuxiliaryQuery.setParameter("param1",
										Arrays.asList((findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" }));
								fiberAuxiliary_DataPt = fiberAuxiliaryQuery.getResultList();

								Query fiberTubesQuery = session.createNativeQuery(
										"SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER,b.TUBE_COLOR FROM FIBER_TUBES b "
												+ "LEFT JOIN TUBE_AUXILIARY_POINTS a ON a.TUBE_ID=b.TUBE_ID "
												+ "LEFT JOIN FIBER_STRANDS c ON b.TUBE_ID=c.TUBE_ID "
												+ "LEFT JOIN STRAND_AUXILIARY_POINTS d ON b.FIBER_CABLE_ID=d.FIBER_CABLE_ID "
												+ "WHERE b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1) OR a.AUXILIARY_POINT_ID IN (:param1) OR c.SOURCE_ID IN (:param1) OR c.DESTINATION_ID IN (:param1) OR d.AUXILIARY_POINT_ID IN (:param1) ");

								fiberTubesQuery.setParameter("param1", mpIdsList);
								fiberTubesPt = fiberTubesQuery.getResultList();
								System.out.println("fiberTubesPt >> " + mapper.writeValueAsString(fiberTubesPt));

								query = session.createNativeQuery(
										"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON  b.TUBE_ID=c.TUBE_ID LEFT JOIN FIBER_CABLES a ON a.FIBER_CABLE_ID=b.FIBER_CABLE_ID WHERE c.TUBE_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC");
								query.setParameter("param1",
										Arrays.asList((findListId(fiberTubes, "Tube")).length > 0 ? findListId(fiberTubes, "Tube")
												: new String[] { "" }));
								tubesAuxiliariesPt = query.getResultList();

								query = session.createNativeQuery(
										"SELECT DISTINCT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR "
												+ "FROM FIBER_STRANDS b LEFT JOIN STRAND_AUXILIARY_POINTS a ON b.STRAND_ID=a.STRAND_ID WHERE b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1) OR a.AUXILIARY_POINT_ID IN (:param1) ");
								query.setParameter("param1", mpIdsList);
								fiberStrandsPt = query.getResultList();

								query = session.createNativeQuery(
										"SELECT DISTINCT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID AND c.STRAND_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC ");
								query.setParameter("param1",
										Arrays.asList((findListId(fiberStrands, "Strand")).length > 0
												? findListId(fiberStrands, "Strand")
												: new String[] { "" }));
								strandsAuxiliariesPt = query.getResultList();
								}
								if (showPointsType.equals("1")) {
									Query manholeData = session.createNativeQuery(
											" SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name ,A.LONGITUDE as LONGITUDE,A.LATITUDE as LATITUDE ,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.FIBER_CABLE_ID IN (:param1) "
													+ " UNION "
													+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1) "
													+ " UNION "
													+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1)  ) where manhole_id NOT IN (:param2) ");
									manholeData.setParameter("param1",
											Arrays.asList((findListId(fiberListPt, "FiberCable")).length > 0
													? findListId(fiberListPt, "FiberCable")
													: new String[] { "" }));
									manholeData.setParameter("param2", mpIdsList);

									if (manholeListPt.size() > 0) {
										newListPt = findNearestArray(manholeData.getResultList(),
												Double.valueOf(lats[i]), Double.valueOf(lngs[i]),
												Double.valueOf(closestDisRange), "ManHandhole_OutOfZone", noOfPoints);
										manholeListPt.addAll(newListPt);
									} else {
										manholeListPt = findNearestArray(manholeData.getResultList(),
												Double.valueOf(lats[i]), Double.valueOf(lngs[i]),
												Double.valueOf(closestDisRange), "ManHandhole_OutOfZone", noOfPoints);
									}
									query = session.createNativeQuery(
											" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name,A.LONGITUDE as LONGITUDE ,A.LATITUDE as LATITUDE,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.FIBER_CABLE_ID IN (:param1) "
													+ " UNION "
													+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) "
													+ "UNION"
													+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) ) where handhole_id NOT IN (:param2) ");
									query.setParameter("param1",
											Arrays.asList((findListId(fiberListPt, "FiberCable")).length > 0
													? findListId(fiberListPt, "FiberCable")
													: new String[] { "" }));
									query.setParameter("param2", mpIdsList);

									if (handholeListPt.size() > 0) {
										newListPt = findNearestArray(query.getResultList(), Double.valueOf(lats[i]),
												Double.valueOf(lngs[i]), Double.valueOf(closestDisRange),
												"ManHandhole_OutOfZone", noOfPoints);
										handholeListPt.addAll(newListPt);
									} else {
										newListPt = findNearestArray(query.getResultList(), Double.valueOf(lats[i]),
												Double.valueOf(lngs[i]), Double.valueOf(closestDisRange),
												"ManHandhole_OutOfZone", noOfPoints);
									}

									query = session.createNativeQuery(
											" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID,A.DB_LONGITUDE as DB_LONGITUDE,A.DB_LATITUDE as DB_LATITUDE,A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.FIBER_CABLE_ID IN (:param1)   "
													+ " UNION "
													+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) "
													+ "UNION "
													+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) ) where DB_ID NOT IN (:param2)  ");
									query.setParameter("param1",
											Arrays.asList((findListId(fiberListPt, "FiberCable")).length > 0
													? findListId(fiberListPt, "FiberCable")
													: new String[] { "" }));
									query.setParameter("param2", mpIdsList);

									if (distribBoardListPt.size() > 0) {
										newListPt = findNearestArray(query.getResultList(), Double.valueOf(lats[i]),
												Double.valueOf(lngs[i]), Double.valueOf(closestDisRange),
												"DB_OutOfZone", noOfPoints);
										distribBoardListPt.addAll(newListPt);
									} else {
										distribBoardListPt = findNearestArray(query.getResultList(),
												Double.valueOf(lats[i]), Double.valueOf(lngs[i]),
												Double.valueOf(closestDisRange), "DB_OutOfZone", noOfPoints);
									}
								}
								junctionManholeListPt = session.createNativeQuery(
										"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id")
										.getResultList();

								junctionHandholeListPt = session.createNativeQuery(
										"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id")
										.getResultList();

								ptPhysicalListResult.put("Junction_Manhole", junctionManholeListPt);
								ptPhysicalListResult.put("Manhole", manholeListPt);
								ptPhysicalListResult.put("Junction_Handhole", junctionHandholeListPt);
								ptPhysicalListResult.put("Handhole", handholeListPt);
								ptPhysicalListResult.put("fiber", fiberListPt);
								ptPhysicalListResult.put("Distribution_Board", distribBoardListPt);
								ptPhysicalListResult.put("Trench", trenchListPt);
								ptPhysicalListResult.put("NodeList", NodeList);
								ptPhysicalListResult.put("duct", ductListPt);
								ptPhysicalLayerList.put("ptList" + i, ptPhysicalListResult);
								ptPhysicalDataResult.put("trench_Auxiliary", trenchAuxiliary_DataPt);
								ptPhysicalDataResult.put("strands_Auxiliaries", strandsAuxiliariesPt);
								ptPhysicalDataResult.put("fiber_Strands", fiberStrandsPt);
								ptPhysicalDataResult.put("tubes_Auxiliaries", tubesAuxiliariesPt);
								ptPhysicalDataResult.put("fiber_Tubes", fiberTubesPt);
								ptPhysicalDataResult.put("fiber_Auxiliary", fiberAuxiliary_DataPt);
								ptPhysicalDataResult.put("ductAuxiliary", ductAuxiliary_DataPt);
								ptPhysicalLayerData.put("ptData" + i, ptPhysicalDataResult);
							}
							model.addAttribute("rowData", mapper.writeValueAsString(rowData));
							model.addAttribute("getRelatedPoints", showPointsType);
							model.addAttribute("noOfPoints", noOfPoints);
							model.addAttribute("closestDisRange", closestDisRange);
							model.addAttribute("ptList", mapper.writeValueAsString(ptPhysicalLayerList));
							model.addAttribute("ptData", mapper.writeValueAsString(ptPhysicalLayerData));
						}
					} else if (StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "connected")) {
						System.out.println("it is inside ");
						filterFlag = 2;
						checkedOption = request.getParameter("Checked");
						String siteId = request.getParameter("siteId").split(":")[0];
						String showPointsType = request.getParameter("getRelatedPoints");

						// String headerSearchlong = request.getParameter("headerSearchLong");
						// String headerSearchlat = request.getParameter("headerSearchLat");
						// System.out.println("siteId " + request.getParameter("siteId"));
						// System.out.println("selectHeaderSearch
						// "+request.getParameter("selectHeaderSearch"));
						// fiberList = session.createNativeQuery("SELECT distinct
						// A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT,
						// A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT
						// COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS
						// Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE
						// C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS
						// Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL
						// FROM FIBER_CABLES A LEFT JOIN FIBER_AUXILIARY_POINTS D ON
						// A.FIBER_CABLE_ID=D.FIBER_CABLE_ID WHERE A.SOURCE_WARE_ID LIKE '%"+siteId+"%'
						// OR A.DESTINATION_WARE_ID LIKE '%"+siteId+"%' OR D.AUXILIARY_POINT_ID LIKE
						// '%"+siteId+"%' ").getResultList();

						fiberList = session.createNativeQuery(
								"SELECT distinct SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,FIBER_CABLE_ID,SOURCE_WARE_ID,SOURCE_ID,"
										+ "SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,Tube_Count,Strand_Count,FIBER_CABLE_NAME,PROJECT_ID,"
										+ "SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_TUBES,NUMBER_OF_STRANDS,LENGTH,DRAWING_TYPE,FIBER_NETWORK_LEVEL,FIBER_OWNER,"
										+ "FIBER_CABLE_COLOR FROM(" +

										"SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,"
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
										+ "WHERE A.SOURCE_WARE_ID LIKE '%" + siteId
										+ "%' OR A.DESTINATION_WARE_ID LIKE '%" + siteId + "%' OR A.SOURCE_ID LIKE '%"
										+ siteId + "%' OR A.DESTINATION_ID LIKE '%" + siteId + "%' "
										+ "OR D.AUXILIARY_POINT_ID LIKE '%" + siteId + "%' OR B.SOURCE_WARE_ID LIKE '%"
										+ siteId + "%' OR B.DESTINATION_WARE_ID LIKE '%" + siteId + "%' "
										+ "OR C.SOURCE_WARE_ID LIKE '%" + siteId + "%' OR C.DESTINATION_WARE_ID LIKE '%"
										+ siteId + "%' OR E.AUXILIARY_POINT_ID LIKE '%" + siteId + "%' "
										+ "OR F.AUXILIARY_POINT_ID LIKE '%" + siteId + "%' " + "UNION "
										+ "SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,"
										+ "A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,"
										+ "(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,"
										+ "(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,"
										+ "A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,"
										+ "(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A "
										+ "LEFT  JOIN DISTRIBUTION_BOARD_MAPPING G ON A.FIBER_CABLE_ID=G.FP_FIBER_ID OR A.FIBER_CABLE_ID=G.BP_FIBER_ID "
										+ "WHERE  G.BP_LOCATION_ID LIKE '%" + siteId + "%' OR G.FP_LOCATION_ID LIKE '%"
										+ siteId + "%' " + "UNION "
										+ "SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,"
										+ "A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,"
										+ "(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,"
										+ "(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,"
										+ "A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,"
										+ "(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A "
										+ "LEFT  JOIN DISTRIBUTION_BOARD D  ON A.SOURCE_ID = D.DB_ID OR A.DESTINATION_ID = D.DB_ID "
										+ "WHERE D.WAREHOUSE LIKE '%" + siteId + "%' " + ")")
								.getResultList();
						// System.out.println("fiberList " + mapper.writeValueAsString(fiberList));

						// fiberAuxiliary_Data = session.createNativeQuery("SELECT
						// B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID
						// FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE
						// A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND AUXILIARY_POINT_ID LIKE '%"+siteId+"%'
						// ORDER BY B.SEQ_SORTING ASC ").getResultList();
						Query fiberAuxiliaryQuery = session.createNativeQuery(
								"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID IN (:param1) ORDER BY B.SEQ_SORTING ASC");
						fiberAuxiliaryQuery.setParameter("param1",
								Arrays.asList((findListId(fiberList, "FiberCable")).length > 0 ? findListId(fiberList, "FiberCable")
										: new String[] { "" }));
						fiberAuxiliary_Data = fiberAuxiliaryQuery.getResultList();

						// fiberTubes = session.createNativeQuery("SELECT DISTINCT
						// b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT
						// COUNT(*) FROM FIBER_STRANDS C WHERE
						// C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME FROM FIBER_TUBES
						// b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID AND b.SOURCE_WARE_ID
						// LIKE '%"+siteId+"%' OR b.DESTINATION_WARE_ID LIKE '%"+siteId+"%'
						// ").getResultList();
						fiberTubes = session.createNativeQuery(
								"SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.DRAWING_TYPE,b.TUBE_NAME,TUBE_NUMBER,TUBE_COLOR FROM FIBER_TUBES b "
										+ "LEFT  JOIN TUBE_AUXILIARY_POINTS a ON a.TUBE_ID=b.TUBE_ID "
										+ "LEFT  JOIN FIBER_STRANDS c ON b.TUBE_ID=c.TUBE_ID "
										+ "LEFT  JOIN STRAND_AUXILIARY_POINTS d ON b.FIBER_CABLE_ID=d.FIBER_CABLE_ID "
										+ "WHERE b.SOURCE_WARE_ID  LIKE '%" + siteId
										+ "%' OR b.DESTINATION_WARE_ID LIKE '%" + siteId
										+ "%' OR a.AUXILIARY_POINT_ID LIKE '%" + siteId
										+ "%' OR c.SOURCE_WARE_ID LIKE '%" + siteId
										+ "%' OR c.DESTINATION_WARE_ID LIKE '%" + siteId
										+ "%' OR d.AUXILIARY_POINT_ID LIKE '%" + siteId + "%' ")
								.getResultList();
						System.out.println("fiberTubessss " + mapper.writeValueAsString(fiberTubes));

						Query tubesAuxiliariesQuery = session.createNativeQuery(
								"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON  b.TUBE_ID=c.TUBE_ID LEFT JOIN FIBER_CABLES a ON a.FIBER_CABLE_ID=b.FIBER_CABLE_ID WHERE c.TUBE_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC");
						tubesAuxiliariesQuery.setParameter("param1",
								Arrays.asList((findListId(fiberTubes, "Tube")).length > 0 ? findListId(fiberTubes, "Tube")
										: new String[] { "" }));
						tubesAuxiliaries = tubesAuxiliariesQuery.getResultList();
						// System.out.println("tubesAuxiliaries
						// "+mapper.writeValueAsString(tubesAuxiliaries));

						// fiberStrands = session.createNativeQuery("SELECT DISTINCT
						// b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME
						// FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID
						// AND b.SOURCE_WARE_ID LIKE '%"+siteId+"%' OR b.DESTINATION_WARE_ID LIKE
						// '%"+siteId+"%' ").getResultList();
						fiberStrands = session.createNativeQuery(
								"SELECT DISTINCT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.DRAWING_TYPE,b.STRAND_NAME,STRAND_NUMBER,STRAND_COLOR FROM FIBER_STRANDS b "
										+ "LEFT  JOIN STRAND_AUXILIARY_POINTS a ON b.STRAND_ID=a.STRAND_ID "
										+ "WHERE b.SOURCE_WARE_ID LIKE '%" + siteId
										+ "%' OR b.DESTINATION_WARE_ID LIKE '%" + siteId
										+ "%' OR a.AUXILIARY_POINT_ID LIKE '%" + siteId + "%' ")
								.getResultList();
						// System.out.println("fiberStrands " +
						// mapper.writeValueAsString(fiberStrands));
						Query strandsAuxiliariesQuery = session.createNativeQuery(
								"SELECT DISTINCT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID AND c.STRAND_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC ");
						strandsAuxiliariesQuery.setParameter("param1",
								Arrays.asList((findListId(fiberStrands, "Strand")).length > 0 ? findListId(fiberStrands, "Strand")
										: new String[] { "" }));
						strandsAuxiliaries = strandsAuxiliariesQuery.getResultList();

						distribBoardList = session.createNativeQuery(
								"SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B  ON B.DB_ID = A.DB_ID  where A.WAREHOUSE LIKE '%"
										+ siteId + "%' OR B.BP_LOCATION_ID LIKE '%" + siteId
										+ "%' OR B.FP_LOCATION_ID LIKE '%" + siteId + "%' ")
								.getResultList();
						int distribBoardListSize = distribBoardList.size();
						// System.out.println("distribBoardList 1 is " +
						// mapper.writeValueAsString(distribBoardList));
						List<Object[]> nearstPoints = new ArrayList<Object[]>();
						// nearstPoints.addAll(manholeList);
						// nearstPoints.addAll(handholeList);
						nearstPoints.addAll(distribBoardList);

						String[] idsArray = (findListId(nearstPoints, "all")).length > 0
								? findListId(nearstPoints, "all")
								: new String[] { "" };

						if (showPointsType.equals("1")) {
							if (distribBoardList.size() > 0) {
								query = session.createNativeQuery(
										" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID,A.DB_LONGITUDE as DB_LONGITUDE,A.DB_LATITUDE as DB_LATITUDE,A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.FIBER_CABLE_ID IN (:param1)   "
												+ " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ "UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) ) where DB_ID NOT IN (:param2)  ");
								query.setParameter("param1",
										Arrays.asList((findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
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
										Arrays.asList((findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
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
										Arrays.asList((findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
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
										Arrays.asList((findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
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
										Arrays.asList((findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
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
										Arrays.asList((findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" }));
								handholeList = query.getResultList();
							}
						}

						String[] allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
								? findListId(manholeList, "all")
								: new String[] { "A" };
						query = session.createNativeQuery(
								"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id where B.manhole_id in (:param) ");
						junctionManholeList = query.setParameter("param", Arrays.asList(allManIdsPointsArray)).getResultList();

						String[] allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
								? findListId(handholeList, "all")
								: new String[] { "A" };
						query = session.createNativeQuery(
								"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id where b.handhole_id in(:param) ");

						junctionHandholeList = query.setParameter("param", Arrays.asList(allHandIdsPointsArray)).getResultList();

						model.addAttribute("siteId", request.getParameter("siteId"));
						model.addAttribute("connectedSearchLong", request.getParameter("connectedSearchLong"));
						model.addAttribute("connectedSearchLat", request.getParameter("connectedSearchLat"));
						model.addAttribute("selectConnectedSearch", request.getParameter("selectConnectedSearch"));
						model.addAttribute("connectedViewOnMap", request.getParameter("connectedViewOnMap"));
						model.addAttribute("distribBoardListSize", distribBoardListSize);
						model.addAttribute("getRelatedPoints", showPointsType);

						// System.out.println("distribBoardList 2 is " +
						// mapper.writeValueAsString(distribBoardList));
					}  else {
						
						filterFlag = 0;
						if ("1".equals(projects)) {
						projectList = session.createNativeQuery(
								"SELECT DISTINCT PROJECT_ID,PROJECT_NAME, (select count(*) FROM HANDHOLE a WHERE a.PROJECT_ID = b.PROJECT_ID),PROJECT_LAYER  FROM PROJECT b ")
								.getResultList();
						}
						
						if ("1".equals(readManhole)) {
						manholeList = session.createNativeQuery(
								"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),CITY FROM MANHOLE where PROJECT_ID= 'CurrentPhysicalLayer'  ")
								.getResultList();
						
						junctionManholeList = session.createNativeQuery(
								"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id WHERE B.PROJECT_ID='CurrentPhysicalLayer'")
								.getResultList();
						
						}
						if ("1".equals(readHandhole)) {
						handholeList = session.createNativeQuery(
								"SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),CITY FROM HANDHOLE where PROJECT_ID= 'CurrentPhysicalLayer'  ")
								.getResultList();
						
						junctionHandholeList = session.createNativeQuery(
								"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id WHERE B.PROJECT_ID='CurrentPhysicalLayer'")
								.getResultList();

						}
						
						if ("1".equals(readDB)) {
							
							distribBoardList = session.createNativeQuery(
									"SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY,DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD WHERE PROJECT_ID='CurrentPhysicalLayer'")
									.getResultList();						
						}
						
						/*
						 * fiberList = session.createNativeQuery(
						 * "SELECT SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID),(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID),FIBER_CABLE_NAME,PROJECT_ID,SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_TUBES,NUMBER_OF_STRANDS,LENGTH,DRAWING_TYPE,FIBER_NETWORK_LEVEL,FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A"
						 * ) .getResultList();
						 * 
						 * fiberAuxiliary_Data = session.createNativeQuery(
						 * "SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID ORDER BY B.SEQ_SORTING ASC"
						 * ) .getResultList();
						 * 
						 * fiberTubes = session.createNativeQuery(
						 * "SELECT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,"
						 * +
						 * "(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER, b.TUBE_COLOR "
						 * +
						 * "FROM FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID ORDER BY FIBER_CABLE_ID,TUBE_NUMBER ASC"
						 * ) .getResultList(); //System.out.println("fb >>" +
						 * mapper.writeValueAsString(fiberTubes));
						 * 
						 * tubesAuxiliaries = session.createNativeQuery(
						 * "SELECT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c,FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.TUBE_ID=c.TUBE_ID ORDER BY c.SEQ_SORTING ASC"
						 * ) .getResultList();
						 * 
						 * fiberStrands = session.createNativeQuery(
						 * "SELECT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID ORDER BY STRAND_NUMBER"
						 * ) .getResultList(); //System.out.println("fs >>" +
						 * mapper.writeValueAsString(fiberStrands));
						 * 
						 * strandsAuxiliaries = session.createNativeQuery(
						 * "SELECT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,C.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID ORDER BY c.SEQ_SORTING ASC "
						 * ) .getResultList();
						 */
						

						trenchList = session.createNativeQuery(
								"SELECT TRENCH_ID,TRENCH_NAME,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LATITUDE,SOURCE_LONGITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY,NUM_DUCTS,MAX_CAPACITY,LENGTH,PROJECT_ID,DRAWING_TYPE  FROM TRENCH WHERE PROJECT_ID='CurrentPhysicalLayer'")
								.getResultList();

						trenchAuxiliary_Data = session.createNativeQuery(
								"SELECT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.TRENCH_ID,B.DISTANCE_FROM_SOURCE,B.SEQ_SORTING,B.AUXILIARY_ID  FROM TRENCH A,TRENCH_AUXILIARY_POINTS B WHERE A.TRENCH_ID=B.TRENCH_ID AND A.PROJECT_ID='CurrentPhysicalLayer' ORDER BY B.SEQ_SORTING ASC")
								.getResultList();
/*
						junctionManholeList = session.createNativeQuery(
								"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id WHERE B.PROJECT_ID='CurrentPhysicalLayer'")
								.getResultList();
*/								

/*						
						junctionHandholeList = session.createNativeQuery(
								"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id WHERE B.PROJECT_ID='CurrentPhysicalLayer'")
								.getResultList();
*/								

						ductList = session.createNativeQuery(
								"SELECT B.DUCT_ID,B.DUCT_NAME,B.SOURCE_WARE_ID,B.SOURCE_ID,B.SOURCE_NAME,B.DESTINATION_WARE_ID,B.DESTINATION_ID,B.DESTINATION_NAME,B.SOURCE_LATITUDE,B.SOURCE_LONGITUDE,B.DESTINATION_LONGITUDE,B.DESTINATION_LATITUDE,B.SOURCE_CITY,B.DESTINATION_CITY,B.NUM_FIBERCABLES,B.NUM_FIBERTUBES,B.NUM_FIBERSTRANDS,B.LENGTH,B.TRENCH_ID,B.DRAWING_TYPE FROM DUCTS B,TRENCH A WHERE B.TRENCH_ID=A.TRENCH_ID AND A.PROJECT_ID= 'CurrentPhysicalLayer'")
								.getResultList();

						ductAuxiliary_Data = session.createNativeQuery(
								"SELECT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.DUCT_ID,B.DISTANCE_FROM_SOURCE,B.SEQ_SORTING,B.AUXILIARY_ID FROM DUCTS A,DUCT_AUXILIARY_POINTS B WHERE A.DUCT_ID=B.DUCT_ID ORDER BY B.SEQ_SORTING ASC ")
								.getResultList();
/*
						NodeList = session.createNativeQuery(
								"SELECT DISTINCT NODE_PK,NODE_NAME,NODE_PK || ':'  || NODE_NAME,DOMAIN,SITE_ID,LONGITUDE,LATITUDE,NODE_ID,SUB_DOMAIN_TYPE FROM NODE_ACTIVE WHERE (SUB_DOMAIN_TYPE='MSAN' OR SUB_DOMAIN_TYPE='SDH' OR SUB_DOMAIN_TYPE='DWDM' OR SUB_DOMAIN_TYPE='GPON' OR SUB_DOMAIN_TYPE='SWITCH' )    ")
								.getResultList();
*/								

					}

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

//			System.out.println("hash_mapFields value is :"+ mapper.writeValueAsString(hashMapList));
//			rtn.put("hashMap",hashMap);
//			rtn.put("hashMapList",hashMapList);

				} catch (Exception e) {
					tx.rollback();
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in NetworkPhysicalLayer due to \n " + exceptionAsString);
					logger.info("Error in NetworkPhysicalLayer due to \n " + exceptionAsString);
					e.printStackTrace();
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
			System.out.println("Loading NetworkPhysicalLayer Controller Finished");
			return "Network/NetworkPhysicalLayer";
		}
	}

	// Project details
	@RequestMapping(value = "/findProjectDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findProjectDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			String selectedProjectIdContext = request.getParameter("selectedProjectIdContext");
			try {
				Object[] ProjectDetails = (Object[]) session
						.createNativeQuery("SELECT DISTINCT PROJECT_ID,PROJECT_NAME FROM PROJECT WHERE PROJECT_ID='"
								+ selectedProjectIdContext + "' ")
						.uniqueResult();

				rtn.put("ProjectDetails", ProjectDetails);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findProjectDetails due to \n " + exceptionAsString);
				logger.info("Error in findProjectDetails due to \n " + exceptionAsString);
				rtn.put("ProjectDetails", null);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	// Manhole details
	@RequestMapping(value = "/findManholeDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findManholeDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {


		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedManIdContext = request.getParameter("selectedManIdContext");
			try {
				Object[] ManholeDetails = (Object[]) session.createNativeQuery(
						"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,MANHOLE_MODEL,CITY,DM_NAME,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),OWNER,MH_INSTALLER,MH_ENGINEER_NAME FROM MANHOLE WHERE MANHOLE_ID='"
								+ selectedManIdContext + "' ")
						.uniqueResult();
				rtn.put("ManholeDetails", ManholeDetails);
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findManholeDetails due to \n " + exceptionAsString);
				logger.info("Error in findManholeDetails due to \n " + exceptionAsString);
				rtn.put("ManholeDetails", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	
	}

	@RequestMapping(value = "/findHandholeDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findHandholeDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {


		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedHandIdContext = request.getParameter("selectedHandIdContext");
			try {
				Object[] HandholeDetails = (Object[]) session.createNativeQuery(
						"SELECT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,HANDHOLE_MODEL,CITY,DM_NAME,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),OWNER,HH_INSTALLER,HH_ENGINEER_NAME  FROM HANDHOLE WHERE HANDHOLE_ID='"
								+ selectedHandIdContext + "' ")
						.uniqueResult();

				rtn.put("HandholeDetails", HandholeDetails);
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findHandholeDetails due to \n " + exceptionAsString);
				logger.info("Error in findHandholeDetails due to \n " + exceptionAsString);
				rtn.put("HandholeDetails", null);

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findDistBoardDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findDistBoardDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedDistBoardContext = request.getParameter("selectedDistBoardContext");
			try {
				List<Object[]> DistBoardDetails = session.createNativeQuery(
						"SELECT DISTINCT A.DB_NAME,A.SITE,A.MAX_CAPACITY,A.DB_LONGITUDE,A.DB_LATITUDE,A.NUM_ROWS,A.NUM_COLUMNS,(SELECT COUNT(B.FP_STATUS) FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.FP_STATUS='Active' AND B.DB_ID='"
								+ selectedDistBoardContext
								+ "'),(SELECT COUNT(B.BP_STATUS) FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.BP_STATUS='Active' AND B.DB_ID='"
								+ selectedDistBoardContext
								+ "'),A.CITY, A.SITE_NAME,A.WAREHOUSE,TO_CHAR(A.CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(A.LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),DB_INSTALLER ,DB_ENGINEER_NAME ,DB_DEPLOYMENT_TYPE ,DB_ADAPTOR_PANEL_TYPE  FROM DISTRIBUTION_BOARD A WHERE A.DB_ID='"
								+ selectedDistBoardContext + "' ")
						.getResultList();
				/*
				 * List<Object[]> DistBoardMappingPts = session.createNativeQuery(
				 * "SELECT DISTINCT ROW_COL_INDEX,ROW_NUMBER,COLUMN_NUMBER,DB_PORT_ID,FP_STATUS,FP_LOCATION_TYPE,FP_LOCATION_ID,FP_LOCATION_NAME,FP_LOCATION,FP_EQUIPMENT_TYPE,FP_EQUIPMENT,FP_EQUIPMENT_ID,FP_EQUIPMENT_NAME,FP_ADDRESS,BP_STATUS,BP_STRAND_ID,BP_STRAND_NAME,BP_TUBE_ID,BP_TUBE_NAME,BP_FIBER_ID,BP_FIBER_NAME,FP_STRAND_ID,FP_STRAND_NAME,FP_TUBE_ID,FP_TUBE_NAME,FP_FIBER_ID,FP_FIBER_NAME,BP_LOCATION_TYPE,BP_LOCATION_ID,BP_LOCATION_NAME,BP_LOCATION,BP_EQUIPMENT_TYPE,BP_EQUIPMENT,BP_EQUIPMENT_ID,BP_EQUIPMENT_NAME,BP_ADDRESS,FP_STRAND_Nb,FP_TUBE_Nb,BP_STRAND_Nb,BP_TUBE_Nb,FP_STRAND_COLOR,FP_TUBE_COLOR,BP_STRAND_COLOR,BP_TUBE_COLOR,FP_JUNCTION_ID,FP_JUNCTION_NAME,BP_JUNCTION_ID,BP_JUNCTION_NAME FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.DB_ID='"
				 * + selectedDistBoardContext + "' ") .getResultList();
				 */
				List<Object[]> DBnetLevel = session
						.createNativeQuery("SELECT DISTINCT DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD  WHERE DB_ID='"
								+ selectedDistBoardContext + "' ")
						.getResultList();

				rtn.put("DistBoardDetails", DistBoardDetails);
				rtn.put("DBnetLevel", DBnetLevel);
				session.flush();
				session.clear();
				tx.commit();

			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findDistBoardDetails due to \n " + exceptionAsString);
				logger.info("Error in findDistBoardDetails due to \n " + exceptionAsString);
				rtn.put("DistBoardDetails", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findDistBoardMappingData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findDistBoardMappingData(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedDistBoardContext = request.getParameter("selectedDistBoardContext");
			try {

				List<Object[]> DistBoardMappingPts = session.createNativeQuery(
						"SELECT DISTINCT ROW_COL_INDEX,ROW_NUMBER,COLUMN_NUMBER,DB_PORT_ID,FP_STATUS,FP_LOCATION_TYPE,FP_LOCATION_ID,FP_LOCATION_NAME,FP_LOCATION,FP_EQUIPMENT_TYPE,FP_EQUIPMENT,FP_EQUIPMENT_ID,FP_EQUIPMENT_NAME,FP_ADDRESS,BP_STATUS,BP_STRAND_ID,BP_STRAND_NAME,BP_TUBE_ID,BP_TUBE_NAME,BP_FIBER_ID,BP_FIBER_NAME,FP_STRAND_ID,FP_STRAND_NAME,FP_TUBE_ID,FP_TUBE_NAME,FP_FIBER_ID,FP_FIBER_NAME,BP_LOCATION_TYPE,BP_LOCATION_ID,BP_LOCATION_NAME,BP_LOCATION,BP_EQUIPMENT_TYPE,BP_EQUIPMENT,BP_EQUIPMENT_ID,BP_EQUIPMENT_NAME,BP_ADDRESS,FP_STRAND_NB,FP_TUBE_NB,BP_STRAND_NB,BP_TUBE_NB,FP_STRAND_COLOR,FP_TUBE_COLOR,BP_STRAND_COLOR,BP_TUBE_COLOR,FP_JUNCTION_ID,FP_JUNCTION_NAME,BP_JUNCTION_ID,BP_JUNCTION_NAME FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.DB_ID='"
								+ selectedDistBoardContext + "' ")
						.getResultList();

				rtn.put("DistBoardMappingPts", DistBoardMappingPts);
				session.flush();
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findDistBoardMappingData due to \n " + exceptionAsString);
				logger.info("Error in findDistBoardMappingData due to \n " + exceptionAsString);
				rtn.put("DistBoardMappingPts", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findDistBoardMappingDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findDistBoardMappingDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String selectedDistBoardContext = request.getParameter("selectedDistBoardContext");

				List<Object[]> DistBoardMappingPts = session.createNativeQuery(
						"SELECT DISTINCT ROW_NUMBER,COLUMN_NUMBER,FP_EQUIPMENT,BP_STRAND_NAME,DB_PORT_ID,(SELECT A.NUM_ROWS FROM DISTRIBUTION_BOARD A WHERE A.DB_ID='"
								+ selectedDistBoardContext
								+ "'),(SELECT A.NUM_COLUMNS FROM DISTRIBUTION_BOARD A WHERE A.DB_ID='"
								+ selectedDistBoardContext
								+ "'),BP_STATUS,BP_STRAND_ID,BP_TUBE_ID,BP_TUBE_NAME,BP_FIBER_ID,BP_FIBER_NAME,FP_STATUS,FP_LOCATION_TYPE,FP_LOCATION_ID,FP_LOCATION_NAME,FP_EQUIPMENT_TYPE,FP_EQUIPMENT_ID,FP_EQUIPMENT_NAME,ROW_COL_INDEX,FP_STRAND_ID,FP_STRAND_NAME,FP_TUBE_ID,FP_TUBE_NAME,FP_FIBER_ID,FP_FIBER_NAME,BP_LOCATION_TYPE,BP_LOCATION_ID,BP_LOCATION_NAME,BP_LOCATION,BP_EQUIPMENT_TYPE,BP_EQUIPMENT,BP_EQUIPMENT_ID,BP_EQUIPMENT_NAME,BP_ADDRESS,FP_STRAND_NB,FP_TUBE_NB,BP_STRAND_NB,BP_TUBE_NB FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.DB_ID='"
								+ selectedDistBoardContext + "' ")
						.getResultList();

				if (DistBoardMappingPts.size() > 0) {
					rtn.put("DistBoardMappingPts", DistBoardMappingPts);
				}
				session.flush();
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findDistBoardMappingDetails due to \n " + exceptionAsString);
				logger.info("Error in findDistBoardMappingDetails due to \n " + exceptionAsString);
				rtn.put("DistBoardMappingPts", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findTrenchDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findTrenchDetails(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {


		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String selectedTrench = request.getParameter("ID");
			List<Object[]> listTrench;
			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					listTrench = session.createNativeQuery(
							"SELECT TRENCH_ID,TRENCH_NAME,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY, MAX_CAPACITY,NUM_DUCTS,LENGTH,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),CREATED_BY,LAST_MODIFIED_BY,TOTAL_DRIVING_DISTANCE, TOTAL_GEO_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE,DRAWING_TYPE,OWNER,TRENCH_INSTALLER,TRENCH_ENGINEER_NAME FROM TRENCH WHERE TRENCH_ID='"
									+ selectedTrench + "' ")
							.getResultList();

					List<Object[]> trenchAuxiliary = session.createNativeQuery(
							"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,TRENCH_ID,SEQ_SORTING,DRIVING_DISTANCE,GEO_DISTANCE,AUXILIARY_ID FROM TRENCH_AUXILIARY_POINTS WHERE TRENCH_ID='"
									+ selectedTrench + "' ORDER BY SEQ_SORTING ASC ")
							.getResultList();

					List<String> finalList = new ArrayList<String>();
					if (itemParameters.getDictParameter().size() > 0) {
						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
							String aux_Long = (itemParameters.getDictParameter().get(i).get("auxLng"));
							String aux_Lat = (itemParameters.getDictParameter().get(i).get("auxLat"));

							String querySearch = "SELECT * FROM ( ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM MANHOLE) UNION (SELECT MANHOLE_ID,MANHOLE_NAME,city FROM MANHOLE WHERE LONGITUDE like '"
									+ aux_Long + "%' and LATITUDE like '" + aux_Lat
									+ "%' )) UNION ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM MANHOLE) "
									+ " UNION (SELECT HANDHOLE_ID,HANDHOLE_NAME,city FROM HANDHOLE WHERE LONGITUDE like '"
									+ aux_Long + "%' and LATITUDE like '" + aux_Lat
									+ "%' )) UNION ( ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM distribution_board) UNION (SELECT DB_ID,DB_NAME,CITY FROM distribution_board "
									+ " WHERE DB_LONGITUDE like '" + aux_Long + "%' and DB_LATITUDE like '" + aux_Lat
									+ "%' )) ) )WHERE ROWNUM=1";

							finalList.addAll(session.createNativeQuery(querySearch).getResultList());
						}
					}
					if (finalList != null) {
						rtn.put("auxPtSearch", finalList);
					}

					rtn.put("listTrench", listTrench);
					rtn.put("auxData", trenchAuxiliary);

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in findTrenchDetails due to \n " + exceptionAsString);
					logger.info("Error in findTrenchDetails due to \n " + exceptionAsString);
					rtn.put("listTrench", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return rtn;
		}
	
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findDuctDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findDuctDetails(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {


		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String selectedDuct = request.getParameter("ID");
			List<Object[]> listDuct;
			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					listDuct = session.createNativeQuery(
							"SELECT DUCT_ID,DUCT_NAME,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY, NUM_FIBERCABLES,NUM_FIBERTUBES,NUM_FIBERSTRANDS,LENGTH,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),CREATED_BY,LAST_MODIFIED_BY,TOTAL_DRIVING_DISTANCE, TOTAL_GEO_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE,DRAWING_TYPE,OWNER,DUCT_INSTALLER,DUCT_ENGINEER_NAME FROM DUCTS WHERE DUCT_ID='"
									+ selectedDuct + "' ")
							.getResultList();

					List<Object[]> ductAuxiliary = session.createNativeQuery(
							"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,DUCT_ID,SEQ_SORTING,DRIVING_DISTANCE,GEO_DISTANCE,AUXILIARY_ID FROM DUCT_AUXILIARY_POINTS WHERE DUCT_ID='"
									+ selectedDuct + "' ORDER BY SEQ_SORTING ASC ")
							.getResultList();
					List<String> finalList = new ArrayList<String>();
					if (itemParameters.getDictParameter().size() > 0) {
						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
							String aux_Long = (itemParameters.getDictParameter().get(i).get("auxLng"));
							String aux_Lat = (itemParameters.getDictParameter().get(i).get("auxLat"));

							String querySearch = "SELECT * FROM ( ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM MANHOLE) UNION (SELECT MANHOLE_ID,MANHOLE_NAME,city FROM MANHOLE WHERE LONGITUDE like '"
									+ aux_Long + "%' and LATITUDE like '" + aux_Lat
									+ "%' )) UNION ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM MANHOLE) "
									+ " UNION (SELECT HANDHOLE_ID,HANDHOLE_NAME,city FROM HANDHOLE WHERE LONGITUDE like '"
									+ aux_Long + "%' and LATITUDE like '" + aux_Lat
									+ "%' )) UNION ( ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM distribution_board) UNION (SELECT DB_ID,DB_NAME,CITY FROM distribution_board "
									+ " WHERE DB_LONGITUDE like '" + aux_Long + "%' and DB_LATITUDE like '" + aux_Lat
									+ "%' )) ) )WHERE ROWNUM=1";

							finalList.addAll(session.createNativeQuery(querySearch).getResultList());
						}
					}
					if (finalList != null) {
						rtn.put("auxPtSearch", finalList);
					}

					rtn.put("listDuct", listDuct);
					rtn.put("auxData", ductAuxiliary);

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in findDuctDetails due to \n " + exceptionAsString);
					logger.info("Error in findDuctDetails due to \n " + exceptionAsString);
					rtn.put("listDuct", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return rtn;
		}
	
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findFiberAuxDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findFiberAuxDetails(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedFiberContext = request.getParameter("ID");
			try {

				List<Object[]> fiberAuxiliaryData = session.createNativeQuery(
						"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.SEQ_SORTING, B.DRIVING_DISTANCE, B.GEO_DISTANCE, B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID ='"
								+ selectedFiberContext + "' ORDER BY B.SEQ_SORTING ASC")
						.getResultList();

				rtn.put("auxData", fiberAuxiliaryData);

				List<String> finalList = new ArrayList<String>();
				if (itemParameters.getDictParameter().size() > 0) {
					for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
						String aux_Long = (itemParameters.getDictParameter().get(i).get("auxLng"));
						String aux_Lat = (itemParameters.getDictParameter().get(i).get("auxLat"));

						String querySearch = "SELECT * FROM ( ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM MANHOLE) UNION (SELECT MANHOLE_ID,MANHOLE_NAME,city FROM MANHOLE WHERE LONGITUDE like '"
								+ aux_Long + "%' and LATITUDE like '" + aux_Lat
								+ "%' )) UNION ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM MANHOLE) "
								+ " UNION (SELECT HANDHOLE_ID,HANDHOLE_NAME,city FROM HANDHOLE WHERE LONGITUDE like '"
								+ aux_Long + "%' and LATITUDE like '" + aux_Lat
								+ "%' )) UNION ( ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM distribution_board) UNION (SELECT DB_ID,DB_NAME,CITY FROM distribution_board "
								+ " WHERE DB_LONGITUDE like '" + aux_Long + "%' and DB_LATITUDE like '" + aux_Lat
								+ "%' )) ) )WHERE ROWNUM=1";

						finalList.addAll(session.createNativeQuery(querySearch).getResultList());
					}
				}
				if (finalList != null) {
					rtn.put("auxPtSearch", finalList);
				}
				session.flush();
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findFiberAuxDetails due to \n " + exceptionAsString);
				logger.info("Error in findFiberAuxDetails due to \n " + exceptionAsString);
				e.printStackTrace();
				rtn.put("fiberAuxiliaryData", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findClientSite", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findClientSite(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String fiberID = request.getParameter("selectedFiberContext").toString();
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				List<String> siteIds = session.createNativeQuery("select distinct site_id from ("
						+ " select distinct fp_location as site_id from distribution_board_mapping "
						+ " where (fp_location_type = 'Site' or bp_location_type = 'Site') and (fp_fiber_id = '"
						+ fiberID + "' or bp_fiber_id = '" + fiberID + "')" + " union"
						+ " select distinct bp_location as site_id from distribution_board_mapping "
						+ " where (fp_location_type = 'Site' or bp_location_type = 'Site') and (fp_fiber_id = '"
						+ fiberID + "' or bp_fiber_id = '" + fiberID + "')" + " union"
						+ " select distinct WAREHOUSE_ID_SIDE_A as site_id from JUNCTION_MAPPING "
						+ " where (LOCATION_TYPE_SIDE_A = 'Site') and (FIBER_ID_SIDE_A = '"
						+ fiberID + "' or FIBER_ID_SIDE_B = '" + fiberID + "')" + " union"
						+ " select distinct WAREHOUSE_ID_SIDE_B as site_id from JUNCTION_MAPPING "
						+ " where (LOCATION_TYPE_SIDE_B = 'Site') and (FIBER_ID_SIDE_B = '"
						+ fiberID + "' or FIBER_ID_SIDE_A = '" + fiberID + "')" + " union"
						+ " (select distinct source_ware_id as site_id from fiber_cables where (source_ware_id is not null or source_ware_id !='null') and fiber_cable_id = '"
						+ fiberID + "')" + " union"
						+ " (select distinct destination_ware_id as site_id from fiber_cables where (destination_ware_id is not null or destination_ware_id !='null') and fiber_cable_id = '"
						+ fiberID + "')" + " union"
						+ " (select distinct source_ware_id as site_id from fiber_tubes where (source_ware_id is not null or source_ware_id !='null') and fiber_cable_id ='"
						+ fiberID + "')" + " union"
						+ " (select distinct destination_ware_id as site_id from fiber_tubes where (destination_ware_id is not null or destination_ware_id !='null') and fiber_cable_id = '"
						+ fiberID + "')" + " union"
						+ " (select distinct source_ware_id as site_id from fiber_strands where (source_ware_id is not null or source_ware_id !='null') and fiber_cable_id = '"
						+ fiberID + "')" + " union"
						+ " (select distinct destination_ware_id as site_id from fiber_strands where (destination_ware_id is not null or destination_ware_id !='null') and fiber_cable_id = '"
						+ fiberID + "')" + " union"
						+ " select distinct warehouse as site_id from distribution_board where (warehouse LIKE 'WARE%') and db_id in ("
						+ " (select LOCATION_ID_SIDE_A from JUNCTION_MAPPING where LOCATION_TYPE_SIDE_A ='DB' and (FIBER_ID_SIDE_A ='"+fiberID+"' or FIBER_ID_SIDE_B ='"+fiberID+"')"
						+ " and LOCATION_ID_SIDE_A LIKE 'DB%') " + " union"
						+ " (select LOCATION_ID_SIDE_B from JUNCTION_MAPPING where LOCATION_TYPE_SIDE_B ='DB' and (FIBER_ID_SIDE_A ='"+fiberID+"' or FIBER_ID_SIDE_B ='"+fiberID+"')"
						+ "	 and LOCATION_ID_SIDE_B LIKE 'DB%'))"+ " union"
						+ " select distinct warehouse as site_id from distribution_board where (warehouse LIKE 'WARE%') and db_id in ("
						+ " (select source_id from fiber_cables where fiber_cable_id ='" + fiberID
						+ "' and source_id LIKE 'DB%') " + " union"
						+ " (select destination_id from fiber_cables where fiber_cable_id = '" + fiberID
						+ "' and destination_id LIKE 'DB%'))) where site_id !='null' and site_id is not null")
						.getResultList();

				session.flush();
				session.clear();

				List<String> clientIds = session.createNativeQuery("select distinct customer_id from ("
						+ " select distinct fp_location_id as customer_id from distribution_board_mapping "
						+ " where (fp_location_type = 'Customer' or bp_location_type = 'Customer') and (fp_fiber_id = '"
						+ fiberID + "' or bp_fiber_id = '" + fiberID + "') and fp_location_id LIKE 'CUST_%' " + " union"
						+ " select distinct bp_location_id as customer_id from distribution_board_mapping "
						+ " where (fp_location_type = 'Customer' or bp_location_type = 'Customer') and (fp_fiber_id = '"
						+ fiberID + "' or bp_fiber_id = '" + fiberID + "') and bp_location_id LIKE 'CUST_%' " + " union"
						+ " select distinct LOCATION_ID_SIDE_A as customer_id from junction_mapping "
						+ " where (LOCATION_TYPE_SIDE_A = 'Customer' or LOCATION_TYPE_SIDE_B = 'Customer') and (FIBER_ID_SIDE_A = '"
						+ fiberID + "' or FIBER_ID_SIDE_B = '" + fiberID + "') and LOCATION_ID_SIDE_A LIKE 'CUST_%' " + " union"
						+ " select distinct LOCATION_ID_SIDE_B as customer_id from junction_mapping "
						+ " where (LOCATION_TYPE_SIDE_B = 'Customer' or LOCATION_TYPE_SIDE_A = 'Customer') and (FIBER_ID_SIDE_B = '"
						+ fiberID + "' or FIBER_ID_SIDE_A = '" + fiberID + "') and LOCATION_ID_SIDE_B LIKE 'CUST_%' " + " union"
						+ " (select distinct source_id as customer_id from fiber_cables where (source_ware_id is null or source_ware_id ='null') and fiber_cable_id = '"
						+ fiberID + "' and source_id LIKE 'CUST_%')" + " union"
						+ " (select distinct destination_id as customer_id from fiber_cables where (destination_ware_id is null or destination_ware_id ='null') and fiber_cable_id = '"
						+ fiberID + "' and destination_id LIKE 'CUST_%')" + " union"
						+ " (select distinct source_id as customer_id from fiber_tubes where (source_ware_id is null or source_ware_id ='null') and fiber_cable_id ='"
						+ fiberID + "' and source_id LIKE 'CUST_%')" + " union"
						+ " (select distinct destination_id as customer_id from fiber_tubes where (destination_ware_id is null or destination_ware_id ='null') and fiber_cable_id = '"
						+ fiberID + "' and destination_id LIKE 'CUST_%')" + " union"
						+ " (select distinct source_id as customer_id from fiber_strands where (source_ware_id is null or source_ware_id ='null') and fiber_cable_id = '"
						+ fiberID + "' and source_id LIKE 'CUST_%')" + " union"
						+ " (select distinct destination_id as customer_id from fiber_strands where (destination_ware_id is null or destination_ware_id='null') and fiber_cable_id = '"
						+ fiberID + "' and destination_id LIKE 'CUST_%')" + " union"
						+ " select distinct site as customer_id from distribution_board where (site LIKE 'CUST_%') and db_id in ("
						+ " (select LOCATION_ID_SIDE_A from JUNCTION_MAPPING where LOCATION_TYPE_SIDE_A ='DB' and (FIBER_ID_SIDE_A ='"+fiberID+"' or FIBER_ID_SIDE_B ='"+fiberID+"')"
						+ " and LOCATION_ID_SIDE_A LIKE 'DB%') " + " union"
						+ " (select LOCATION_ID_SIDE_B from JUNCTION_MAPPING where LOCATION_TYPE_SIDE_B ='DB' and (FIBER_ID_SIDE_A ='"+fiberID+"' or FIBER_ID_SIDE_B ='"+fiberID+"')"
						+ "	 and LOCATION_ID_SIDE_B LIKE 'DB%'))"+ " union"
						+ " select distinct site as customer_id from distribution_board where (site LIKE 'CUST_%') and db_id in ("
						+ " (select source_id from fiber_cables where fiber_cable_id ='" + fiberID
						+ "' and source_id LIKE 'DB%') " + " union"
						+ " (select destination_id from fiber_cables where fiber_cable_id = '" + fiberID
						+ "' and destination_id LIKE 'DB%')))").getResultList();

				session.flush();
				session.clear();

				query = session.createNativeQuery(
						"SELECT DISTINCT WARE_ID,SITE_ID,WARE_NAME,LONGITUDE,LATITUDE FROM WAREHOUSE WHERE WARE_ID IN (:param1)");
				query.setParameter("param1", siteIds);
				rtn.put("SiteData", query.getResultList());

				session.flush();
				session.clear();

				query = session.createNativeQuery(
						"SELECT DISTINCT CUSTOMER_ID,CUSTOMER_NAME,MOBILE_NUMBER,LONGITUDE,LATITUDE FROM CUSTOMER WHERE CUSTOMER_ID IN (:param1)");
				query.setParameter("param1", clientIds);
				rtn.put("ClientData", query.getResultList());

				session.flush();
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.info("Error in retreiving  Data from database \n" + exceptionAsString);
				rtn.put("ClientData", null);
				rtn.put("SiteData", null);

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findDBClientSite", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findDBClientSite(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String DBID = request.getParameter("selectedDB").toString();
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				
				
				List<String> siteIds = session.createNativeQuery("select distinct site_id from ("
						+ "select distinct fp_location as site_id from distribution_board_mapping where fp_location_type = 'Site' and DB_ID = '"+DBID+"' "
						+ "union "
						+ "select distinct bp_location as site_id from distribution_board_mapping where bp_location_type = 'Site' and DB_ID = '"+DBID+"' "
						+ "union "
						+ "select distinct WAREHOUSE as site_id  from distribution_board where DB_ID ='"+DBID+"' and warehouse LIKE 'WARE%' "
						+ "union "
						+ "select distinct bp_location as site_id from distribution_board_mapping "
						+ "where (BP_EQUIPMENT='DistBoard' or FP_EQUIPMENT='DistBoard') and (BP_EQUIPMENT_ID='"+DBID+"' or FP_EQUIPMENT_ID='"+DBID+"') "
						+ "and bp_location_type='Site' "
						+ "union "
						+ "select distinct fp_location as site_id from distribution_board_mapping "
						+ "where (BP_EQUIPMENT='DistBoard' or FP_EQUIPMENT='DistBoard') and (BP_EQUIPMENT_ID='"+DBID+"' or FP_EQUIPMENT_ID='"+DBID+"') "
						+ "and fp_location_type='Site'"
						+ ")where site_id !='null' and site_id is not null").getResultList();
				

				session.flush();
				session.clear();

				List<String> clientIds = session.createNativeQuery("select distinct customer_id from ("
						+ "select distinct fp_location_id as customer_id from distribution_board_mapping where fp_location_type = 'Customer' and DB_ID = '"+DBID+"' "
						+ "union "
						+ "select distinct bp_location_id as customer_id from distribution_board_mapping where bp_location_type = 'Customer' and DB_ID = '"+DBID+"' "
						+ "union "
						+ "select distinct site as customer_id from distribution_board where DB_ID ='"+DBID+"' and site LIKE 'CUST_%' "
						+ "union "
						+ "select distinct bp_location_id as customer_id from distribution_board_mapping "
						+ "where (BP_EQUIPMENT='DistBoard' or FP_EQUIPMENT='DistBoard') and (BP_EQUIPMENT_ID='"+DBID+"' or FP_EQUIPMENT_ID='"+DBID+"') "
						+ "and bp_location_type='Customer' "
						+ "union "
						+ "select distinct fp_location_id as customer_id from distribution_board_mapping "
						+ "where (BP_EQUIPMENT='DistBoard' or FP_EQUIPMENT='DistBoard') and (BP_EQUIPMENT_ID='"+DBID+"' or FP_EQUIPMENT_ID='"+DBID+"') "
						+ "and fp_location_type='Customer')").getResultList();

				session.flush();
				session.clear();

				query = session.createNativeQuery(
						"SELECT DISTINCT WARE_ID,SITE_ID,WARE_NAME,LONGITUDE,LATITUDE FROM WAREHOUSE WHERE WARE_ID IN (:param1)");
				query.setParameter("param1", siteIds);
				rtn.put("SiteData", query.getResultList());

				session.flush();
				session.clear();

				query = session.createNativeQuery(
						"SELECT DISTINCT CUSTOMER_ID,CUSTOMER_NAME,MOBILE_NUMBER,LONGITUDE,LATITUDE FROM CUSTOMER WHERE CUSTOMER_ID IN (:param1)");
				query.setParameter("param1", clientIds);
				rtn.put("ClientData", query.getResultList());

				session.flush();
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				//logger.info("Error in retreiving  Data from database \n" + exceptionAsString);
				logger.finest("Error in findDBClientSite due to \n " + exceptionAsString);
				logger.info("Error in findDBClientSite due to \n " + exceptionAsString);
				rtn.put("ClientData", null);
				rtn.put("SiteData", null);

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findFiberDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findFiberDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {


		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String selectedFiberContext = request.getParameter("selectedFiberContext");
			List<Object[]> fiberDetails, fiberTubes, fiberStrands, accessJunctions;
			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					fiberDetails = session.createNativeQuery(
							"Select DISTINCT SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,ITEM_CODE,NUMBER_OF_STRANDS,NUMBER_OF_TUBES,LENGTH,CONDUIT_ID,CONDUIT_NAME,SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,CABLE_MODE,FIBER_CABLE_NAME,SOURCE_CITY, DESTINATION_CITY, FIBER_TYPE, FIBER_DEPLOYMENT, FIBER_NETWORK_LEVEL, FIBER_OWNER,CREATED_BY,LAST_MODIFIED_BY,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),TOTAL_DRIVING_DISTANCE, TOTAL_GEO_DISTANCE, DRAWING_TYPE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE,RELATED_STRAND_NUMBER,RELATED_STRAND_COLOR,RELATED_STRAND_ID,RELATED_STRAND_NAME,RELATED_TUBE_NUMBER,RELATED_TUBE_COLOR,RELATED_TUBE_ID,RELATED_TUBE_NAME,RELATED_CABLE_ID,RELATED_CABLE_NAME,OTHERSIDE_LASTMILE_ID,OTHERSIDE_LASTMILE_NAME,OTHERSIDE_LOCATION_ID,OTHERSIDE_LOCATION_NAME,OTHERSIDE_LOCATION_CITY,OTHERSIDE_LOCATION_TYPE,FIBER_CABLE_SIZE,FIBER_ENGINEER_NAME,FIBER_INSTALLER FROM FIBER_CABLES WHERE FIBER_CABLE_ID='"
									+ selectedFiberContext + "' ")
							.getResultList();

					// USED IN SHOW POINTS FOR AUXILIARIES //
					List<Object[]> fiberAuxiliaryData = session.createNativeQuery(
							"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.SEQ_SORTING, B.DRIVING_DISTANCE, B.GEO_DISTANCE, B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID ='"
									+ selectedFiberContext + "' ORDER BY B.SEQ_SORTING ASC")
							.getResultList();

					fiberTubes = session.createNativeQuery(
							"SELECT TUBE_ID, FIBER_CABLE_ID, SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,TUBE_NAME,SOURCE_CITY, DESTINATION_CITY, TUBE_TYPE, TUBE_DEPLOYMENT, TUBE_NETWORK_LEVEL, TUBE_OWNER,TUBE_NUMBER,TUBE_COLOR,DRAWING_TYPE,TOTAL_GEO_DISTANCE,TOTAL_DRIVING_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE FROM FIBER_TUBES WHERE FIBER_CABLE_ID='"
									+ selectedFiberContext + "' ")
							.getResultList();

					fiberStrands = session.createNativeQuery(
							"SELECT STRAND_ID,FIBER_CABLE_ID, SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,TUBE_ID,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,STRAND_NAME,SOURCE_CITY, DESTINATION_CITY, STRAND_TYPE, STRAND_DEPLOYMENT, STRAND_NETWORK_LEVEL, STRAND_OWNER,STRAND_NUMBER,STRAND_COLOR,DRAWING_TYPE,TOTAL_GEO_DISTANCE,TOTAL_DRIVING_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE FROM FIBER_STRANDS WHERE FIBER_CABLE_ID='"
									+ selectedFiberContext + "' ")
							.getResultList();

					accessJunctions = session.createNativeQuery(
							"SELECT JUNCTION_ROW_ID,JUNCTION_ID,JUNCTION_NAME,FIBER_CABLE_ID FROM ACCESS_CABLES_JUNCTIONS WHERE FIBER_CABLE_ID='"
									+ selectedFiberContext + "' ")
							.getResultList();

					// System.out.println("fiberStrands " +
					// mapper.writeValueAsString(fiberStrands));

					rtn.put("fiberDetails", fiberDetails);
					rtn.put("fiberTubes", fiberTubes);
					rtn.put("fiberStrands", fiberStrands);
					rtn.put("fiberAuxData", fiberAuxiliaryData);
					rtn.put("accessJunctions", accessJunctions);

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in findFiberDetails due to \n " + exceptionAsString);
					logger.info("Error in findFiberDetails due to \n " + exceptionAsString);
					rtn.put("fiberDetails", null);
					rtn.put("fiberTubes", null);
					rtn.put("fiberStrands", null);
					rtn.put("fiberAuxData", null);
					rtn.put("accessJunctions", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}
			}
			return rtn;
		}
	
	}

	// Nodes details

	@RequestMapping(value = "/findNodesDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findNodesDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			String selectedNodeAcvtiveContext = request.getParameter("selectedNodeAcvtiveContext");
			try {
				Object[] NodesDetails = (Object[]) session.createNativeQuery(
						"SELECT NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,SUB_DOMAIN_TYPE,SITE_ID,WARE_ID,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(UPDATE_DATE, 'MM/dd/YYYY HH:MI AM'),LONGITUDE,LATITUDE FROM NODE_ACTIVE WHERE NODE_PK ='"
								+ selectedNodeAcvtiveContext + "'")
						.uniqueResult();
				rtn.put("NodesDetails", NodesDetails);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findNodesDetails due to \n " + exceptionAsString);
				logger.info("Error in findNodesDetails due to \n " + exceptionAsString);
				rtn.put("NodesDetails", null);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	// Core details
	@RequestMapping(value = "/findCoreDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCoreDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			String selectedCoreIdContext = request.getParameter("selectedCoreIdContext");
			try {
				Object[] CoreNodesDetails = (Object[]) session.createNativeQuery(
						"SELECT NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,SITE_ID,WARE_ID,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(UPDATE_DATE, 'MM/dd/YYYY HH:MI AM'),LONGITUDE,LATITUDE FROM NODE_ACTIVE  WHERE DOMAIN = 'Core' AND NODE_PK ='"
								+ selectedCoreIdContext + "'")
						.uniqueResult();

				rtn.put("CoreNodesDetails", CoreNodesDetails);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCoreDetails due to \n " + exceptionAsString);
				logger.info("Error in findCoreDetails due to \n " + exceptionAsString);
				rtn.put("CoreNodesDetails", null);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	// Access details
	@RequestMapping(value = "/findAccessDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findAccessDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			String selectedRanIdContext = request.getParameter("selectedRanIdContext");
			try {
				Object[] AccessNodesDetails = (Object[]) session.createNativeQuery(
						"SELECT NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,SITE_ID,WARE_ID,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(UPDATE_DATE, 'MM/dd/YYYY HH:MI AM'),LONGITUDE,LATITUDE FROM NODE_ACTIVE  WHERE DOMAIN = 'Access' AND NODE_PK ='"
								+ selectedRanIdContext + "'")
						.uniqueResult();

				rtn.put("AccessNodesDetails", AccessNodesDetails);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findAccessDetails due to \n " + exceptionAsString);
				logger.info("Error in findAccessDetails due to \n " + exceptionAsString);
				rtn.put("AccessNodesDetails", null);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	// Transmission details
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findTransmissionDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findTransmissionDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			String selectedTransmissionIdContext = request.getParameter("selectedTransmissionIdContext");
			try {
				Object[] TransmissionNodesDetails = (Object[]) session.createNativeQuery(
						"SELECT NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,SITE_ID,WARE_ID,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(UPDATE_DATE, 'MM/dd/YYYY HH:MI AM'),LONGITUDE,LATITUDE FROM NODE_ACTIVE  WHERE DOMAIN = 'Transmission' AND NODE_PK ='"
								+ selectedTransmissionIdContext + "'")
						.uniqueResult();

				rtn.put("TransmissionNodesDetails", TransmissionNodesDetails);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findTransmissionDetails due to \n " + exceptionAsString);
				logger.info("Error in findTransmissionDetails due to \n " + exceptionAsString);
				rtn.put("TransmissionNodesDetails", null);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/transNodeSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> transNodeSave(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				NodeActive nodeActive = new NodeActive();
				String transNode_pk = request.getParameter("transNode_pk");

				nodeActive.setNodePK(transNode_pk);
				nodeActive.setUniNodeID(request.getParameter("transUniqNodeId"));
				nodeActive.setNodeID(request.getParameter("transNodeId"));
				nodeActive.setNodeName(request.getParameter("transNodeName"));
				nodeActive.setNodeType(request.getParameter("transNodeType"));
				nodeActive.setNodeSrc(request.getParameter("transNodeSource"));
				nodeActive.setNodeModel(request.getParameter("transNodeModel"));
				nodeActive.setDomain(request.getParameter("transNodeDomin"));
				nodeActive.setSiteID(request.getParameter("transSiteId_node"));
				nodeActive.setWareID(request.getParameter("transWareId_node"));

				rtn.put("transNode_pk", transNode_pk);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in transNodeSave due to \n " + exceptionAsString);
				logger.info("Error in transNodeSave due to \n " + exceptionAsString);
				rtn.put("transNode_pk", null);
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

	@RequestMapping(value = "/coreNodeSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> coreNodeSave(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				NodeActive nodeActive = new NodeActive();
				String coreNode_pk = request.getParameter("coreNode_pk");

				nodeActive.setNodePK(coreNode_pk);
				nodeActive.setUniNodeID(request.getParameter("coreUniqNodeId"));
				nodeActive.setNodeID(request.getParameter("coreNodeId"));
				nodeActive.setNodeName(request.getParameter("coreNodeName"));
				nodeActive.setNodeType(request.getParameter("coreNodeType"));
				nodeActive.setNodeSrc(request.getParameter("coreNodeSource"));
				nodeActive.setNodeModel(request.getParameter("coreNodeModel"));
				nodeActive.setDomain(request.getParameter("coreNodeDomin"));
				nodeActive.setSiteID(request.getParameter("coreSiteId_node"));
				nodeActive.setWareID(request.getParameter("coreWareId_node"));

				rtn.put("coreNode_pk", coreNode_pk);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in coreNodeSave due to \n " + exceptionAsString);
				logger.info("Error in coreNodeSave due to \n " + exceptionAsString);
				rtn.put("transNode_pk", null);
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

	@RequestMapping(value = "/ranNodeSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ranNodeSave(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				NodeActive nodeActive = new NodeActive();
				String ranNode_pk = request.getParameter("ranNode_pk");

				nodeActive.setNodePK(ranNode_pk);
				nodeActive.setUniNodeID(request.getParameter("ranUniqNodeId"));
				nodeActive.setNodeID(request.getParameter("ranNodeId"));
				nodeActive.setNodeName(request.getParameter("ranNodeName"));
				nodeActive.setNodeType(request.getParameter("ranNodeType"));
				nodeActive.setNodeSrc(request.getParameter("ranNodeSource"));
				nodeActive.setNodeModel(request.getParameter("ranNodeModel"));
				nodeActive.setDomain(request.getParameter("ranNodeDomin"));
				nodeActive.setSiteID(request.getParameter("ranSiteId_node"));
				nodeActive.setWareID(request.getParameter("ranWareId_node"));

				rtn.put("ranNode_pk", ranNode_pk);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in ranNodeSave due to \n " + exceptionAsString);
				logger.info("Error in ranNodeSave due to \n " + exceptionAsString);
				rtn.put("transNode_pk", null);
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

	@RequestMapping(value = "/GetALLFIBER", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetALLFIBER(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GetALLFIBER due to \n " + exceptionAsString);
				logger.info("Error in GetALLFIBER due to \n " + exceptionAsString);
				rtn.put("fiberRouteData", null);
			}

			try {

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GetALLFIBER due to \n " + exceptionAsString);
				logger.info("Error in GetALLFIBER due to \n " + exceptionAsString);
				rtn.put("auxiliary_Data", null);
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
	@RequestMapping(value = "/showDBs", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> showDBs(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String contextID = request.getParameter("selectedContext").toString();
		String target = request.getParameter("target").toString();
		// System.out.println("entered target is "+target);
		List<Object[]> DBData = null;
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				if (target.equals("Fiber")) {
					System.out.println("entered fiber " + contextID);
					/*
					 * DBData=session.
					 * createNativeQuery("SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where C.FIBER_CABLE_ID LIKE '%"
					 * +contextID+"%' " + " UNION " +
					 * " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.FIBER_CABLE_ID LIKE '%"
					 * +contextID+"%' " + " UNION " +
					 * " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.FIBER_CABLE_ID LIKE '%"
					 * +contextID+"%' ").getResultList();
					 */
					// DBData=session.createNativeQuery("SELECT DISTINCT
					// A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID
					// ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B ON
					// B.DB_ID = A.DB_ID where B.BP_FIBER_ID LIKE '"+contextID+"' OR B.FP_FIBER_ID
					// LIKE '"+contextID+"' ").getResultList();
					DBData = session.createNativeQuery(
							"SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B  ON B.DB_ID = A.DB_ID  where B.BP_FIBER_ID LIKE '"
									+ contextID + "' OR B.FP_FIBER_ID LIKE '" + contextID + "' " + " UNION "
									+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.FIBER_CABLE_ID LIKE '"
									+ contextID + "' " + " UNION "
									+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.FIBER_CABLE_ID LIKE '"
									+ contextID + "' ")
							.getResultList();
					System.out.println("DBData is "+ mapper.writeValueAsString(DBData));

				} else if (target.equals("Tube")) {
					System.out.println("entered Tube " + contextID);
					/*
					 * DBData=session.
					 * createNativeQuery("SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_TUBES C ON C.TUBE_ID = B.TUBE_ID where C.TUBE_ID LIKE '%"
					 * +contextID+"%' " + " UNION " +
					 * " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.DB_ID where B.TUBE_ID LIKE '%"
					 * +contextID+"%' " + " UNION " +
					 * " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.DB_ID where B.TUBE_ID LIKE '%"
					 * +contextID+"%' ").getResultList();
					 */
					// DBData=session.createNativeQuery("SELECT DISTINCT
					// A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID
					// ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B ON
					// B.DB_ID = A.DB_ID where B.BP_TUBE_ID LIKE '"+contextID+"' OR B.FP_TUBE_ID
					// LIKE '"+contextID+"' ").getResultList();
					DBData = session.createNativeQuery(
							"SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B  ON B.DB_ID = A.DB_ID  where B.BP_TUBE_ID LIKE '"
									+ contextID + "' OR B.FP_TUBE_ID LIKE '" + contextID + "' " + " UNION "
									+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.DB_ID where B.TUBE_ID LIKE '"
									+ contextID + "' " + " UNION "
									+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.DB_ID where B.TUBE_ID LIKE '"
									+ contextID + "' ")
							.getResultList();

				} else if (target.equals("Strand")) {
					System.out.println("entered Strand " + contextID);
					/*
					 * DBData=session.
					 * createNativeQuery("SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_STRANDS C ON C.STRAND_ID = B.STRAND_ID where C.STRAND_ID LIKE '%"
					 * +contextID+"%' " + " UNION " +
					 * " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.DB_ID where B.STRAND_ID LIKE '%"
					 * +contextID+"%' " + " UNION " +
					 * " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.DB_ID where B.STRAND_ID LIKE '%"
					 * +contextID+"%' ").getResultList();
					 */
					// DBData=session.createNativeQuery("SELECT DISTINCT
					// A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID
					// ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B ON
					// B.DB_ID = A.DB_ID where B.BP_STRAND_ID LIKE '"+contextID+"' OR B.FP_STRAND_ID
					// LIKE '"+contextID+"' ").getResultList();
					DBData = session.createNativeQuery(
							"SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B  ON B.DB_ID = A.DB_ID  where B.BP_STRAND_ID LIKE '"
									+ contextID + "' OR B.FP_STRAND_ID LIKE '" + contextID + "' " + " UNION "
									+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.DB_ID where B.STRAND_ID LIKE '"
									+ contextID + "%' " + " UNION "
									+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.DB_ID where B.STRAND_ID LIKE '"
									+ contextID + "' ")
							.getResultList();

				}
				rtn.put("DBData", DBData);
				System.out.println("DBData is "+ mapper.writeValueAsString(DBData));

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findFiberDBs due to \n " + exceptionAsString);
				logger.info("Error in findFiberDBs due to \n " + exceptionAsString);
				rtn.put("FiberDBData", null);

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
	@RequestMapping(value = "/manHandHolePath", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> path(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				String dataSel = request.getParameter("dataSel");

				// DATA FOR BACKBONE NETWORKLEVEL
				List<Object[]> BackboneCableData = session.createNativeQuery(
						"Select DISTINCT a.FIBER_CABLE_ID, a.FIBER_CABLE_NAME,a.FIBER_NETWORK_LEVEL,a.FIBER_OWNER from FIBER_CABLES a left join FIBER_AUXILIARY_POINTS b ON a.FIBER_CABLE_ID = b.FIBER_CABLE_ID where (SOURCE_ID='"
								+ dataSel + "' or DESTINATION_ID='" + dataSel + "' or b.AUXILIARY_POINT_ID='" + dataSel
								+ "') and FIBER_NETWORK_LEVEL = 'backbone'")
						.getResultList();
				rtn.put("BackboneCableData", BackboneCableData);

				List<Object[]> BackboneTubeData = session.createNativeQuery(
						"Select DISTINCT a.TUBE_ID,a.TUBE_NAME,c.FIBER_CABLE_ID, c.FIBER_CABLE_NAME,a.TUBE_NETWORK_LEVEL,TUBE_COLOR,TUBE_NUMBER from FIBER_TUBES a left join TUBE_AUXILIARY_POINTS b ON a.TUBE_ID = b.TUBE_ID left join FIBER_CABLES c ON a.FIBER_CABLE_ID = c.FIBER_CABLE_ID where (a.SOURCE_ID='"
								+ dataSel + "' or a.DESTINATION_ID='" + dataSel + "' or b.AUXILIARY_POINT_ID='"
								+ dataSel + "') and TUBE_NETWORK_LEVEL = 'backbone'")
						.getResultList();
				rtn.put("BackboneTubeData", BackboneTubeData);

				List<Object[]> BackboneStrandData = session.createNativeQuery(
						"Select DISTINCT a.STRAND_ID, a.STRAND_NAME, c.TUBE_ID,c.TUBE_NAME,a.STRAND_NETWORK_LEVEL,a.STRAND_COLOR,a.STRAND_NUMBER,d.FIBER_CABLE_NAME,d.FIBER_CABLE_ID from FIBER_STRANDS a left join STRAND_AUXILIARY_POINTS b ON a.STRAND_ID = b.STRAND_ID left join  FIBER_TUBES c ON a.TUBE_ID = c.TUBE_ID left join FIBER_CABLES d ON d.FIBER_CABLE_ID = a.FIBER_CABLE_ID where (a.SOURCE_ID='"
								+ dataSel + "' or a.DESTINATION_ID='" + dataSel + "' or b.AUXILIARY_POINT_ID='"
								+ dataSel + "') and STRAND_NETWORK_LEVEL = 'backbone' ")
						.getResultList();
				rtn.put("BackboneStrandData", BackboneStrandData);

				// DATA FOR METRO NETWORKLEVEL
				List<Object[]> MetroCableData = session.createNativeQuery(
						"Select DISTINCT a.FIBER_CABLE_ID, a.FIBER_CABLE_NAME,a.FIBER_NETWORK_LEVEL,a.FIBER_OWNER from FIBER_CABLES a left join FIBER_AUXILIARY_POINTS b ON a.FIBER_CABLE_ID = b.FIBER_CABLE_ID where (SOURCE_ID='"
								+ dataSel + "' or DESTINATION_ID='" + dataSel + "' or b.AUXILIARY_POINT_ID='" + dataSel
								+ "') and FIBER_NETWORK_LEVEL = 'metro'")
						.getResultList();
				rtn.put("MetroCableData", MetroCableData);

				List<Object[]> MetroTubeData = session.createNativeQuery(
						"Select DISTINCT a.TUBE_ID,a.TUBE_NAME,c.FIBER_CABLE_ID, c.FIBER_CABLE_NAME,a.TUBE_NETWORK_LEVEL,a.TUBE_COLOR,a.TUBE_NUMBER from FIBER_TUBES a left join TUBE_AUXILIARY_POINTS b ON a.TUBE_ID = b.TUBE_ID left join FIBER_CABLES c ON a.FIBER_CABLE_ID = c.FIBER_CABLE_ID where (a.SOURCE_ID='"
								+ dataSel + "' or a.DESTINATION_ID='" + dataSel + "' or AUXILIARY_POINT_ID='" + dataSel
								+ "') and TUBE_NETWORK_LEVEL = 'metro'")
						.getResultList();
				rtn.put("MetroTubeData", MetroTubeData);

				List<Object[]> MetroStrandData = session.createNativeQuery(
						"Select DISTINCT a.STRAND_ID, a.STRAND_NAME, c.TUBE_ID,c.TUBE_NAME,a.STRAND_NETWORK_LEVEL,a.STRAND_COLOR,a.STRAND_NUMBER,d.FIBER_CABLE_NAME,d.FIBER_CABLE_ID from FIBER_STRANDS a left join STRAND_AUXILIARY_POINTS b ON a.STRAND_ID = b.STRAND_ID left join  FIBER_TUBES c ON a.TUBE_ID = c.TUBE_ID left join FIBER_CABLES d ON d.FIBER_CABLE_ID = a.FIBER_CABLE_ID where (a.SOURCE_ID='"
								+ dataSel + "' or a.DESTINATION_ID='" + dataSel + "' or AUXILIARY_POINT_ID='" + dataSel
								+ "') and STRAND_NETWORK_LEVEL = 'metro' ")
						.getResultList();
				rtn.put("MetroStrandData", MetroStrandData);

				// DATA FOR Access NETWORKLEVEL
				List<Object[]> DistributionCableData = session.createNativeQuery(
						"Select DISTINCT a.FIBER_CABLE_ID, a.FIBER_CABLE_NAME,a.FIBER_NETWORK_LEVEL,a.FIBER_OWNER from FIBER_CABLES a left join FIBER_AUXILIARY_POINTS b ON a.FIBER_CABLE_ID = b.FIBER_CABLE_ID where (SOURCE_ID='"
								+ dataSel + "' or DESTINATION_ID='" + dataSel + "' or AUXILIARY_POINT_ID='" + dataSel
								+ "') and FIBER_NETWORK_LEVEL = 'access'")
						.getResultList();
				rtn.put("DistributionCableData", DistributionCableData);

				List<Object[]> DistributionTubeData = session.createNativeQuery(
						"Select DISTINCT a.TUBE_ID,a.TUBE_NAME,c.FIBER_CABLE_ID, c.FIBER_CABLE_NAME,a.TUBE_NETWORK_LEVEL,a.TUBE_COLOR,a.TUBE_NUMBER from FIBER_TUBES a left join TUBE_AUXILIARY_POINTS b ON a.TUBE_ID = b.TUBE_ID left join FIBER_CABLES c ON a.FIBER_CABLE_ID = c.FIBER_CABLE_ID where (a.SOURCE_ID='"
								+ dataSel + "' or a.DESTINATION_ID='" + dataSel + "' or AUXILIARY_POINT_ID='" + dataSel
								+ "') and TUBE_NETWORK_LEVEL = 'access'")
						.getResultList();
				rtn.put("DistributionTubeData", DistributionTubeData);

				List<Object[]> DistributionStrandData = session.createNativeQuery(
						"Select DISTINCT a.STRAND_ID, a.STRAND_NAME, c.TUBE_ID,c.TUBE_NAME,a.STRAND_NETWORK_LEVEL,a.STRAND_COLOR,a.STRAND_NUMBER,d.FIBER_CABLE_NAME,d.FIBER_CABLE_ID from FIBER_STRANDS a left join STRAND_AUXILIARY_POINTS b ON a.STRAND_ID = b.STRAND_ID left join  FIBER_TUBES c ON a.TUBE_ID = c.TUBE_ID left join FIBER_CABLES d ON d.FIBER_CABLE_ID = a.FIBER_CABLE_ID where (a.SOURCE_ID='"
								+ dataSel + "' or a.DESTINATION_ID='" + dataSel + "' or AUXILIARY_POINT_ID='" + dataSel
								+ "') and STRAND_NETWORK_LEVEL = 'access' ")
						.getResultList();
				rtn.put("DistributionStrandData", DistributionStrandData);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in manHandHolePath due to \n " + exceptionAsString);
				logger.info("Error in manHandHolePath due to \n " + exceptionAsString);
				rtn.put("BackboneCableData", null);
				rtn.put("BackboneTubeData", null);
				rtn.put("BackboneStrandData", null);
				rtn.put("MetroCableData", null);
				rtn.put("MetroTubeData", null);
				rtn.put("MetroStrandData", null);
				rtn.put("DistributionCableData", null);
				rtn.put("DistributionTubeData", null);
				rtn.put("DistributionStrandData", null);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}

		}
		return rtn;
	}
	
	//////////////////////////////77777777777777
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/showRelatedPath", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> showRelatedPath(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				String dataSel = request.getParameter("dataSel");

				// DATA FOR BACKBONE NETWORKLEVEL
				List<Object[]> BackboneCableData = session.createNativeQuery(
						"select distinct a.FIBER_ID_SIDE_A,a.FIBER_NAME_SIDE_A,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER"
						+ " from JUNCTION_MAPPING a left join FIBER_CABLES b on a.FIBER_ID_SIDE_A=b.FIBER_CABLE_ID"
						+ " where FIBER_ID_SIDE_B='"+dataSel+"' and b.FIBER_NETWORK_LEVEL='backbone'"
						+ " union "
						+ " select distinct a.FIBER_ID_SIDE_B,a.FIBER_NAME_SIDE_B,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER"
						+ " from JUNCTION_MAPPING a left join FIBER_CABLES b on a.FIBER_ID_SIDE_B=b.FIBER_CABLE_ID"
						+ " where a.FIBER_ID_SIDE_A='"+dataSel+"' and b.FIBER_NETWORK_LEVEL='backbone'")
						.getResultList();
				rtn.put("BackboneCableData", BackboneCableData);
				rtn.put("BackboneTubeData", "");
				rtn.put("BackboneStrandData", "");

				// DATA FOR METRO NETWORKLEVEL
				List<Object[]> MetroCableData = session.createNativeQuery(
						"select distinct a.FIBER_ID_SIDE_A,a.FIBER_NAME_SIDE_A,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER"
						+ " from JUNCTION_MAPPING a left join FIBER_CABLES b on a.FIBER_ID_SIDE_A=b.FIBER_CABLE_ID"
						+ " where FIBER_ID_SIDE_B='"+dataSel+"' and b.FIBER_NETWORK_LEVEL='metro'"
						+ " union"
						+ " select distinct a.FIBER_ID_SIDE_B,a.FIBER_NAME_SIDE_B,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER"
						+ " from JUNCTION_MAPPING a left join FIBER_CABLES b on a.FIBER_ID_SIDE_B=b.FIBER_CABLE_ID"
						+ " where a.FIBER_ID_SIDE_A='"+dataSel+"' and b.FIBER_NETWORK_LEVEL='metro'")
						.getResultList();
		
				rtn.put("MetroCableData", MetroCableData);
				rtn.put("MetroTubeData", "");
				rtn.put("MetroStrandData", "");

				// DATA FOR Access NETWORKLEVEL
				List<Object[]> DistributionCableData = session.createNativeQuery(
						"select distinct a.FIBER_ID_SIDE_A,a.FIBER_NAME_SIDE_A,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER"
						+ " from JUNCTION_MAPPING a left join FIBER_CABLES b on a.FIBER_ID_SIDE_A=b.FIBER_CABLE_ID"
						+ " where FIBER_ID_SIDE_B='"+dataSel+"' and b.FIBER_NETWORK_LEVEL='access'"
						+ " union"
						+ " select distinct a.FIBER_ID_SIDE_B,a.FIBER_NAME_SIDE_B,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER"
						+ " from JUNCTION_MAPPING a left join FIBER_CABLES b on a.FIBER_ID_SIDE_B=b.FIBER_CABLE_ID"
						+ " where a.FIBER_ID_SIDE_A='"+dataSel+"' and b.FIBER_NETWORK_LEVEL='access'")
						.getResultList();
				
				rtn.put("DistributionCableData", DistributionCableData);
				rtn.put("DistributionTubeData", "");
				rtn.put("DistributionStrandData", "");

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in showRelatedPath due to \n " + exceptionAsString);
				logger.info("Error in showRelatedPath due to \n " + exceptionAsString);
				rtn.put("BackboneCableData", null);
				rtn.put("BackboneTubeData", null);
				rtn.put("BackboneStrandData", null);
				rtn.put("MetroCableData", null);
				rtn.put("MetroTubeData", null);
				rtn.put("MetroStrandData", null);
				rtn.put("DistributionCableData", null);
				rtn.put("DistributionTubeData", null);
				rtn.put("DistributionStrandData", null);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}

		}
		return rtn;
	}
	//////////////////////////////777777777777

	@RequestMapping(value = "/boqManhole", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> boqManhole(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String manhole_Id = request.getParameter("ManholeId").toString();
		String[] junctionManholeID = manhole_Id.split(":");
		String countFiberCables, countFiberCablesAux, countFiberCablesSrcDest, countTubes, countTubesSrcDest,
				countTubesAux, countStrands, countStrandsSrcDest, countStrandsAux, countJunctions;
		List<Object[]> manholeData = null;
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				manholeData = session.createNativeQuery(
						"select MANHOLE_NAME,LONGITUDE,LATITUDE,CITY,DM_NAME FROM MANHOLE " + "where MANHOLE_ID='"
								+ manhole_Id + "' AND PROJECT_ID ='" + request.getParameter("ProjectId") + "'")
						.getResultList();

				query = session.createNativeQuery("select count(*) from FIBER_CABLES a where a.SOURCE_ID = '"
						+ manhole_Id + "' or a.DESTINATION_ID = '" + manhole_Id + "' AND a.PROJECT_ID ='"
						+ request.getParameter("ProjectId") + "' ");

				countFiberCablesSrcDest = query.getSingleResult().toString();

				query = session.createNativeQuery(
						"select count (*) from FIBER_AUXILIARY_POINTS b LEFT JOIN  FIBER_CABLES c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID "
								+ "where b.AUXILIARY_POINT_ID = '" + manhole_Id + "'  and c.PROJECT_ID ='"
								+ request.getParameter("ProjectId") + "'");

				countFiberCablesAux = query.getSingleResult().toString();

				countFiberCables = String
						.valueOf((Integer.parseInt(countFiberCablesSrcDest)) + (Integer.parseInt(countFiberCablesAux)));

				countTubesSrcDest = session.createNativeQuery(
						"select count (*) from FIBER_TUBES b LEFT JOIN  FIBER_CABLES c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID where b.SOURCE_ID LIKE '"
								+ manhole_Id + "%' " + "or b.DESTINATION_ID LIKE '" + manhole_Id
								+ "%' and c.PROJECT_ID ='" + request.getParameter("ProjectId") + "' ")
						.uniqueResult().toString();

				countTubesAux = session.createNativeQuery(
						"select count (*) from FIBER_TUBES b LEFT JOIN  TUBE_AUXILIARY_POINTS c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID "
								+ "where c.AUXILIARY_POINT_ID LIKE '" + manhole_Id + "%'")
						.uniqueResult().toString();

				countTubes = String.valueOf((Integer.parseInt(countTubesSrcDest)) + (Integer.parseInt(countTubesAux)));

				countStrandsSrcDest = session.createNativeQuery(
						"select count (*) from FIBER_STRANDS c LEFT JOIN  FIBER_CABLES b ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID where c.SOURCE_ID LIKE '"
								+ manhole_Id + "%' " + "or c.DESTINATION_ID LIKE '" + manhole_Id
								+ "%' and b.PROJECT_ID ='" + request.getParameter("ProjectId") + "' ")
						.uniqueResult().toString();

				countStrandsAux = session.createNativeQuery(
						"select count (*) from FIBER_STRANDS b LEFT JOIN  STRAND_AUXILIARY_POINTS c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID "
								+ "where c.AUXILIARY_POINT_ID LIKE '" + manhole_Id + "%'")
						.uniqueResult().toString();

				countStrands = String
						.valueOf((Integer.parseInt(countStrandsSrcDest)) + (Integer.parseInt(countStrandsAux)));
				countJunctions = session
						.createNativeQuery("select coalesce(count(*),0) from JUNCTION  where PHYSICAL_LAYER_ID='"
								+ junctionManholeID[0] + "' ")
						.uniqueResult().toString();

				rtn.put("manholeData", manholeData);
				rtn.put("countJunctions", countJunctions);
				rtn.put("countFiberCables", countFiberCables);
				rtn.put("countTubes", countTubes);
				rtn.put("countStrands", countStrands);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in boqManhole due to \n " + exceptionAsString);
				logger.info("Error in boqManhole due to \n " + exceptionAsString);
				rtn.put("countTubes", null);

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
	@RequestMapping(value = "/boqHandhole", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> boqHandhole(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String handhole_Id = request.getParameter("HandholeId");
		String[] junctionHandholeID = handhole_Id.split(":");
		String countFiberCables, countFiberCablesAux, countFiberCablesSrcDest, countTubes, countTubesSrcDest,
				countTubesAux, countStrands, countStrandsSrcDest, countStrandsAux, countJunctions;
		List<Object[]> handholeData = null;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				handholeData = session.createNativeQuery(
						"select HANDHOLE_NAME,LONGITUDE,LATITUDE,CITY,DM_NAME FROM HANDHOLE " + "where HANDHOLE_ID='"
								+ handhole_Id + "' AND PROJECT_ID ='" + request.getParameter("ProjectId") + "'")
						.getResultList();

				query = session.createNativeQuery("select count(*) from FIBER_CABLES a where a.SOURCE_ID = '"
						+ handhole_Id + "' or a.DESTINATION_ID = '" + handhole_Id + "' AND a.PROJECT_ID ='"
						+ request.getParameter("ProjectId") + "' ");

				countFiberCablesSrcDest = query.getSingleResult().toString();

				query = session.createNativeQuery(
						"select count (*) from FIBER_AUXILIARY_POINTS b LEFT JOIN  FIBER_CABLES c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID "
								+ "where b.AUXILIARY_POINT_ID = '" + handhole_Id + "'  and c.PROJECT_ID ='"
								+ request.getParameter("ProjectId") + "'");
				countFiberCablesAux = query.getSingleResult().toString();

				countFiberCables = String
						.valueOf((Integer.parseInt(countFiberCablesSrcDest)) + (Integer.parseInt(countFiberCablesAux)));

				countTubesSrcDest = session.createNativeQuery(
						"select count (*) from FIBER_TUBES b LEFT JOIN  FIBER_CABLES c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID where b.SOURCE_ID LIKE '"
								+ handhole_Id + "%' " + "or b.DESTINATION_ID LIKE '" + handhole_Id
								+ "%' and c.PROJECT_ID ='" + request.getParameter("ProjectId") + "' ")
						.uniqueResult().toString();

				countTubesAux = session.createNativeQuery(
						"select count (*) from FIBER_TUBES b LEFT JOIN  TUBE_AUXILIARY_POINTS c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID "
								+ "where c.AUXILIARY_POINT_ID LIKE '" + handhole_Id + "%'")
						.uniqueResult().toString();

				countTubes = String.valueOf((Integer.parseInt(countTubesSrcDest)) + (Integer.parseInt(countTubesAux)));

				countStrandsSrcDest = session.createNativeQuery(
						"select count (*) from FIBER_STRANDS c LEFT JOIN  FIBER_CABLES b ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID where c.SOURCE_ID LIKE '"
								+ handhole_Id + "%' " + "or c.DESTINATION_ID LIKE '" + handhole_Id
								+ "%' and b.PROJECT_ID ='" + request.getParameter("ProjectId") + "' ")
						.uniqueResult().toString();

				countStrandsAux = session.createNativeQuery(
						"select count (*) from FIBER_STRANDS b LEFT JOIN  STRAND_AUXILIARY_POINTS c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID "
								+ "where c.AUXILIARY_POINT_ID LIKE '" + handhole_Id + "%'")
						.uniqueResult().toString();

				countStrands = String
						.valueOf((Integer.parseInt(countStrandsSrcDest)) + (Integer.parseInt(countStrandsAux)));
				countJunctions = session
						.createNativeQuery("select coalesce(count(*),0) from JUNCTION  where PHYSICAL_LAYER_ID='"
								+ junctionHandholeID[0] + "' ")
						.uniqueResult().toString();

				rtn.put("handholeData", handholeData);
				rtn.put("countJunctions", countJunctions);
				rtn.put("countFiberCables", countFiberCables);
				rtn.put("countTubes", countTubes);
				rtn.put("countStrands", countStrands);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in boqHandhole due to \n " + exceptionAsString);
				logger.info("Error in boqHandhole due to \n " + exceptionAsString);
				rtn.put("countFiberCables", null);
				rtn.put("countTubes", null);
				rtn.put("countStrands", null);

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
	@RequestMapping(value = "/FiberBoQ", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FiberBoQ(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String fiberID = request.getParameter("fiberID").toString();
		String projectID = request.getParameter("projectID").toString();

		List<Object[]> FiberData = null;
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				FiberData = session.createNativeQuery(
						"SELECT FIBER_CABLE_NAME,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_STRANDS,NUMBER_OF_TUBES,(number_of_strands*number_of_tubes),LENGTH, total_geo_distance from fiber_cables WHERE FIBER_CABLE_ID='"
								+ fiberID + "' and PROJECT_ID='" + projectID + "'")
						.getResultList();
				rtn.put("FiberData", FiberData);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in FiberBoQ due to \n " + exceptionAsString);
				logger.info("Error in FiberBoQ due to \n " + exceptionAsString);
				rtn.put("FiberData", null);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/TubeBoQ", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> TubeBoQ(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String fiberID = request.getParameter("fiberID").toString();
		String tubeID = request.getParameter("tubeID").toString();

		List<Object[]> TubeData = null;
		String strandsCount = null;
		List<Object[]> cable_geo = null;
		List<Object[]> cable_LineOfSite = null;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				TubeData = session.createNativeQuery(
						"SELECT SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,total_geo_distance,Length from FIBER_TUBES WHERE TUBE_ID='"
								+ tubeID + "' and FIBER_CABLE_ID='" + fiberID + "'")
						.getResultList();
				rtn.put("TubeData", TubeData);

				strandsCount = session.createNativeQuery("select count (*) from FIBER_STRANDS WHERE TUBE_ID='" + tubeID
						+ "' and FIBER_CABLE_ID='" + fiberID + "'").uniqueResult().toString();
				rtn.put("strandsCount", strandsCount);
				cable_geo = session.createNativeQuery(
						"select total_geo_distance from FIBER_cables WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_tubes where TUBE_ID='"
								+ tubeID + "')")
						.getResultList();
				cable_LineOfSite = session.createNativeQuery(
						"select Length from FIBER_cables WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_tubes where TUBE_ID='"
								+ tubeID + "')")
						.getResultList();
				rtn.put("cable_geo", cable_geo);
				rtn.put("cable_LineOfSite", cable_LineOfSite);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in TubeBoQ due to \n " + exceptionAsString);
				logger.info("Error in TubeBoQ due to \n " + exceptionAsString);
				rtn.put("strandsCount", "0");
				rtn.put("TubeData", null);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/DuctBoQ", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> DuctBoQ(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String ductID = request.getParameter("ductID").toString();

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				List<Object[]> DuctData = session.createNativeQuery(
						"SELECT B.SOURCE_WARE_ID,B.SOURCE_ID,B.SOURCE_NAME,B.DESTINATION_WARE_ID,B.DESTINATION_ID,B.DESTINATION_NAME,B.SOURCE_LATITUDE,B.SOURCE_LONGITUDE,B.DESTINATION_LONGITUDE"
								+ ",B.DESTINATION_LATITUDE,B.SOURCE_CITY,B.DESTINATION_CITY,B.LENGTH,B.NUM_FIBERCABLES,B.NUM_FIBERTUBES,"
								+ "B.NUM_FIBERSTRANDS,B.TRENCH_ID,B.DUCT_NAME FROM DUCTS B " + "WHERE B.DUCT_ID='"
								+ ductID + "'")
						.getResultList();

				rtn.put("DuctData", DuctData);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in DuctBoQ due to \n " + exceptionAsString);
				logger.info("Error in DuctBoQ due to \n " + exceptionAsString);
				rtn.put("DuctData", null);

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
	@RequestMapping(value = "/boqDistBoard", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> boqDistBoard(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		ObjectMapper map = new ObjectMapper();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				String distBoardSel = request.getParameter("DistBoardSel").toString();
				List<Object[]> DBData = session.createNativeQuery(
						"Select DB_NAME,DB_LONGITUDE,DB_LATITUDE,CITY FROM DISTRIBUTION_BOARD where" + " DB_ID='"
								+ distBoardSel + "' and PROJECT_ID='" + request.getParameter("projectID") + "'")
						.getResultList();
				rtn.put("DBData", DBData);
				List<Object[]> countConnections = session.createNativeQuery(
						"select NUM_ROWS,NUM_COLUMNS,(SELECT COUNT(B.FP_STATUS) FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.FP_STATUS IN ('Active', 'InActive') AND B.DB_ID='"
								+ distBoardSel
								+ "') AS front,(SELECT COUNT(B.BP_STATUS) FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.BP_STATUS IN ('Active', 'InActive') AND B.DB_ID='"
								+ distBoardSel + "') AS back from DISTRIBUTION_BOARD b where b.DB_ID='" + distBoardSel
								+ "'")
						.getResultList();
				System.out.println("countConnections " + map.writeValueAsString(countConnections));
				rtn.put("countConnections", countConnections);

				Object DbMappingCount = session
						.createNativeQuery(
								"Select count(*) FROM DISTRIBUTION_BOARD_MAPPING where DB_ID='" + distBoardSel + "' ")
						.uniqueResult();
				rtn.put("DbMappingCount", DbMappingCount);

				Object countDbActiveFP = session.createNativeQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING WHERE FP_STATUS='Active' AND DB_ID='"
								+ distBoardSel + "' ")
						.uniqueResult();
				rtn.put("countDbActiveFP", countDbActiveFP);

				Object countDbInactiveFP = session.createNativeQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING WHERE FP_STATUS='InActive' AND DB_ID='"
								+ distBoardSel + "' ")
						.uniqueResult();
				rtn.put("countDbInactiveFP", countDbInactiveFP);

				Object countDbActiveBP = session.createNativeQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING WHERE BP_STATUS='Active' AND DB_ID='"
								+ distBoardSel + "' ")
						.uniqueResult();
				rtn.put("countDbActiveBP", countDbActiveBP);

				Object countDbInactiveBP = session.createNativeQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING WHERE BP_STATUS='InActive' AND DB_ID='"
								+ distBoardSel + "' ")
						.uniqueResult();
				rtn.put("countDbInactiveBP", countDbInactiveBP);

				session.clear();
				tx.commit();

			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in boqDistBoard due to \n " + exceptionAsString);
				logger.info("Error in boqDistBoard due to \n " + exceptionAsString);
				rtn.put("countConnections", null);
				rtn.put("DBData", null);

			} finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/boqNodesCount", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> boqNodesCount(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Object MSANCount = session
						.createNativeQuery("select count (*) from node_active where domain = 'Enterprise' AND SUB_DOMAIN_TYPE ='MSAN' AND ACTIVE_RECORD =1")
						.getResultList();
				rtn.put("MSANCount", MSANCount);
				
				Object EntSwitchCount = session
						.createNativeQuery("select count (*) from node_active where domain = 'Enterprise' AND SUB_DOMAIN_TYPE ='SWITCH' AND ACTIVE_RECORD =1")
						.getResultList();
				rtn.put("EntSwitchCount", EntSwitchCount);

				Object DWDMCount = session.createNativeQuery(
						"select count (*) from node_active where domain = 'Transmission' AND SUB_DOMAIN_TYPE = 'DWDM' AND ACTIVE_RECORD =1")
						.getResultList();
				rtn.put("DWDMCount", DWDMCount);

				Object SDHCount = session.createNativeQuery(
						"select count (*) from node_active where domain = 'Transmission' AND SUB_DOMAIN_TYPE = 'SDH' AND ACTIVE_RECORD =1")
						.getResultList();
				rtn.put("SDHCount", SDHCount);

				Object GPONCount = session.createNativeQuery(
						"select count (*) from node_active where domain = 'Transmission' AND SUB_DOMAIN_TYPE = 'GPON' AND ACTIVE_RECORD =1")
						.getResultList();
				rtn.put("GPONCount", GPONCount);

				Object AllNodesCount = session.createNativeQuery(
						"SELECT COUNT(*) FROM node_active WHERE domain IN ('Enterprise' , 'Transmission') AND SUB_DOMAIN_TYPE IN ('DWDM','SDH','GPON','MSAN','SWITCH') AND ACTIVE_RECORD =1")
						.getResultList();
				rtn.put("AllNodesCount", AllNodesCount);
				session.clear();
				tx.commit();

			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in boqNodesCount due to \n " + exceptionAsString);
				logger.info("Error in boqNodesCount due to \n " + exceptionAsString);
				rtn.put("boqNodesCount", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pathDistBoard", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> pathDistBoard(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				String distBoardSel = request.getParameter("dataSel");
				/* Backbone data for show Path */
				List<Object[]> CableBackboneData = session.createNativeQuery(
						"Select DISTINCT a.BP_FIBER_ID,a.BP_FIBER_NAME,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN  FIBER_CABLES b on a.BP_FIBER_ID = b.FIBER_CABLE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT a.FP_FIBER_ID,a.FP_FIBER_NAME,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN  FIBER_CABLES b on a.FP_FIBER_ID = b.FIBER_CABLE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT FIBER_CABLE_ID,FIBER_CABLE_NAME,FIBER_NETWORK_LEVEL,FIBER_OWNER FROM FIBER_CABLES WHERE (SOURCE_ID='"
								+ distBoardSel + "' OR DESTINATION_ID='" + distBoardSel
								+ "') and (FIBER_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT A.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,B.FIBER_NETWORK_LEVEL,B.FIBER_OWNER FROM FIBER_AUXILIARY_POINTS A LEFT JOIN  FIBER_CABLES B on A.FIBER_CABLE_ID = B.FIBER_CABLE_ID WHERE (AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'backbone')")
						.getResultList();
				rtn.put("BackboneCableData", CableBackboneData);

				List<Object[]> TubeBackboneData = session.createNativeQuery(
						"Select DISTINCT a.BP_TUBE_ID,a.BP_TUBE_NAME,a.BP_TUBE_COLOR,a.BP_TUBE_NB,a.BP_FIBER_ID,a.BP_FIBER_NAME,b.TUBE_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN FIBER_TUBES b on a.BP_TUBE_ID = b.TUBE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT a.FP_TUBE_ID,a.FP_TUBE_NAME,a.FP_TUBE_COLOR,a.FP_TUBE_NB,a.FP_FIBER_ID,a.FP_FIBER_NAME,b.TUBE_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN FIBER_TUBES b on a.FP_TUBE_ID = b.TUBE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT A.TUBE_ID,A.TUBE_NAME,A.TUBE_COLOR,A.TUBE_NUMBER,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,A.TUBE_NETWORK_LEVEL FROM (FIBER_TUBES A LEFT JOIN FIBER_CABLES B on A.FIBER_CABLE_ID=B.FIBER_CABLE_ID) WHERE (A.SOURCE_ID='"
								+ distBoardSel + "' OR A.DESTINATION_ID='" + distBoardSel
								+ "') and (TUBE_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT A.TUBE_ID,B.TUBE_NAME,B.TUBE_COLOR,B.TUBE_NUMBER,A.FIBER_CABLE_ID,C.FIBER_CABLE_NAME,B.TUBE_NETWORK_LEVEL FROM (TUBE_AUXILIARY_POINTS A LEFT JOIN  FIBER_TUBES B on A.TUBE_ID = B.TUBE_ID) LEFT JOIN FIBER_CABLES C on A.FIBER_CABLE_ID=C.FIBER_CABLE_ID  WHERE (A.AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'backbone')")
						.getResultList();
				rtn.put("BackboneTubeData", TubeBackboneData);

				List<Object[]> StrandBackboneData = session.createNativeQuery(
						"Select DISTINCT a.BP_STRAND_ID,a.BP_STRAND_NAME,a.BP_STRAND_COLOR,a.BP_STRAND_NB,a.BP_FIBER_ID,a.BP_FIBER_NAME,a.BP_TUBE_ID,a.BP_TUBE_NAME,b.STRAND_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a left join FIBER_STRANDS b on a.BP_STRAND_ID = b.STRAND_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT a.FP_STRAND_ID,a.FP_STRAND_NAME,a.FP_STRAND_COLOR,a.FP_STRAND_NB,a.FP_FIBER_ID,a.FP_FIBER_NAME,a.FP_TUBE_ID,a.FP_TUBE_NAME,b.STRAND_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a left join FIBER_STRANDS b on a.FP_STRAND_ID = b.STRAND_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT A.STRAND_ID,A.STRAND_NAME,A.STRAND_COLOR,A.STRAND_NUMBER,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_ID,C.TUBE_NAME,A.STRAND_NETWORK_LEVEL FROM (FIBER_STRANDS A LEFT JOIN FIBER_CABLES B on A.FIBER_CABLE_ID=B.FIBER_CABLE_ID) LEFT JOIN FIBER_TUBES C on A.TUBE_ID=C.TUBE_ID WHERE (A.SOURCE_ID='"
								+ distBoardSel + "' OR A.DESTINATION_ID='" + distBoardSel
								+ "') and (STRAND_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT A.STRAND_ID,B.STRAND_NAME,B.STRAND_COLOR,B.STRAND_NUMBER,C.FIBER_CABLE_ID,C.FIBER_CABLE_NAME,D.TUBE_ID,D.TUBE_NAME,B.STRAND_NETWORK_LEVEL FROM (STRAND_AUXILIARY_POINTS A LEFT JOIN  FIBER_STRANDS B on A.STRAND_ID = B.STRAND_ID) LEFT JOIN FIBER_CABLES C on A.FIBER_CABLE_ID=C.FIBER_CABLE_ID LEFT JOIN FIBER_TUBES D on B.TUBE_ID=D.TUBE_ID WHERE (A.AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'backbone')")
						.getResultList();
				rtn.put("BackboneStrandData", StrandBackboneData);

				/* Metro data for show Path */
				List<Object[]> CableMetroData = session.createNativeQuery(
						"Select DISTINCT a.BP_FIBER_ID,a.BP_FIBER_NAME,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN  FIBER_CABLES b on a.BP_FIBER_ID = b.FIBER_CABLE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT a.FP_FIBER_ID,a.FP_FIBER_NAME,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN  FIBER_CABLES b on a.FP_FIBER_ID = b.FIBER_CABLE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT FIBER_CABLE_ID,FIBER_CABLE_NAME,FIBER_NETWORK_LEVEL,FIBER_OWNER FROM FIBER_CABLES WHERE (SOURCE_ID='"
								+ distBoardSel + "' OR DESTINATION_ID='" + distBoardSel
								+ "') and (FIBER_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT A.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,B.FIBER_NETWORK_LEVEL,B.FIBER_OWNER FROM FIBER_AUXILIARY_POINTS A LEFT JOIN  FIBER_CABLES B on A.FIBER_CABLE_ID = B.FIBER_CABLE_ID WHERE (AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'metro')")
						.getResultList();
				rtn.put("MetroCableData", CableMetroData);

				List<Object[]> TubeMetroData = session.createNativeQuery(
						"Select DISTINCT a.BP_TUBE_ID,a.BP_TUBE_NAME,a.BP_TUBE_COLOR,a.BP_TUBE_NB,a.BP_FIBER_ID,a.BP_FIBER_NAME,b.TUBE_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN FIBER_TUBES b on a.BP_TUBE_ID = b.TUBE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT a.FP_TUBE_ID,a.FP_TUBE_NAME,a.FP_TUBE_COLOR,a.FP_TUBE_NB,a.FP_FIBER_ID,a.FP_FIBER_NAME,b.TUBE_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN FIBER_TUBES b on a.FP_TUBE_ID = b.TUBE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT A.TUBE_ID,A.TUBE_NAME,A.TUBE_COLOR,A.TUBE_NUMBER,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,A.TUBE_NETWORK_LEVEL FROM (FIBER_TUBES A LEFT JOIN FIBER_CABLES B on A.FIBER_CABLE_ID=B.FIBER_CABLE_ID) WHERE (A.SOURCE_ID='"
								+ distBoardSel + "' OR A.DESTINATION_ID='" + distBoardSel
								+ "') and (TUBE_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT A.TUBE_ID,B.TUBE_NAME,B.TUBE_COLOR,B.TUBE_NUMBER,A.FIBER_CABLE_ID,C.FIBER_CABLE_NAME,B.TUBE_NETWORK_LEVEL FROM (TUBE_AUXILIARY_POINTS A LEFT JOIN  FIBER_TUBES B on A.TUBE_ID = B.TUBE_ID) LEFT JOIN FIBER_CABLES C on A.FIBER_CABLE_ID=C.FIBER_CABLE_ID  WHERE (A.AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'metro')")
						.getResultList();
				rtn.put("MetroTubeData", TubeMetroData);

				List<Object[]> StrandMetroData = session.createNativeQuery(
						"Select DISTINCT a.BP_STRAND_ID,a.BP_STRAND_NAME,a.BP_STRAND_COLOR,a.BP_STRAND_NB,a.BP_FIBER_ID,a.BP_FIBER_NAME,a.BP_TUBE_ID,a.BP_TUBE_NAME,b.STRAND_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a left join FIBER_STRANDS b on a.BP_STRAND_ID = b.STRAND_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT a.FP_STRAND_ID,a.FP_STRAND_NAME,a.FP_STRAND_COLOR,a.FP_STRAND_NB,a.FP_FIBER_ID,a.FP_FIBER_NAME,a.FP_TUBE_ID,a.FP_TUBE_NAME,b.STRAND_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a left join FIBER_STRANDS b on a.FP_STRAND_ID = b.STRAND_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT A.STRAND_ID,A.STRAND_NAME,A.STRAND_COLOR,A.STRAND_NUMBER,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_ID,C.TUBE_NAME,A.STRAND_NETWORK_LEVEL FROM (FIBER_STRANDS A LEFT JOIN FIBER_CABLES B on A.FIBER_CABLE_ID=B.FIBER_CABLE_ID) LEFT JOIN FIBER_TUBES C on A.TUBE_ID=C.TUBE_ID WHERE (A.SOURCE_ID='"
								+ distBoardSel + "' OR A.DESTINATION_ID='" + distBoardSel
								+ "') and (STRAND_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT A.STRAND_ID,B.STRAND_NAME,B.STRAND_COLOR,B.STRAND_NUMBER,C.FIBER_CABLE_ID,C.FIBER_CABLE_NAME,D.TUBE_ID,D.TUBE_NAME,B.STRAND_NETWORK_LEVEL FROM (STRAND_AUXILIARY_POINTS A LEFT JOIN  FIBER_STRANDS B on A.STRAND_ID = B.STRAND_ID) LEFT JOIN FIBER_CABLES C on A.FIBER_CABLE_ID=C.FIBER_CABLE_ID LEFT JOIN FIBER_TUBES D on B.TUBE_ID=D.TUBE_ID WHERE (A.AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'metro')")
						.getResultList();
				rtn.put("MetroStrandData", StrandMetroData);

				/* Access data for show Path */
				List<Object[]> CableAccessData = session.createNativeQuery(
						"Select DISTINCT a.BP_FIBER_ID,a.BP_FIBER_NAME,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN  FIBER_CABLES b on a.BP_FIBER_ID = b.FIBER_CABLE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT a.FP_FIBER_ID,a.FP_FIBER_NAME,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN  FIBER_CABLES b on a.FP_FIBER_ID = b.FIBER_CABLE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT FIBER_CABLE_ID,FIBER_CABLE_NAME,FIBER_NETWORK_LEVEL,FIBER_OWNER FROM FIBER_CABLES WHERE (SOURCE_ID='"
								+ distBoardSel + "' OR DESTINATION_ID='" + distBoardSel
								+ "') and (FIBER_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT A.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,B.FIBER_NETWORK_LEVEL,B.FIBER_OWNER FROM FIBER_AUXILIARY_POINTS A LEFT JOIN  FIBER_CABLES B on A.FIBER_CABLE_ID = B.FIBER_CABLE_ID WHERE (AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'access')")
						.getResultList();
				rtn.put("DistributionCableData", CableAccessData);

				List<Object[]> TubeAccessData = session.createNativeQuery(
						"Select DISTINCT a.BP_TUBE_ID,a.BP_TUBE_NAME,a.BP_TUBE_COLOR,a.BP_TUBE_NB,a.BP_FIBER_ID,a.BP_FIBER_NAME,b.TUBE_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN FIBER_TUBES b on a.BP_TUBE_ID = b.TUBE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT a.FP_TUBE_ID,a.FP_TUBE_NAME,a.FP_TUBE_COLOR,a.FP_TUBE_NB,a.FP_FIBER_ID,a.FP_FIBER_NAME,b.TUBE_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN FIBER_TUBES b on a.FP_TUBE_ID = b.TUBE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT A.TUBE_ID,A.TUBE_NAME,A.TUBE_COLOR,A.TUBE_NUMBER,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,A.TUBE_NETWORK_LEVEL FROM (FIBER_TUBES A LEFT JOIN FIBER_CABLES B on A.FIBER_CABLE_ID=B.FIBER_CABLE_ID) WHERE (A.SOURCE_ID='"
								+ distBoardSel + "' OR A.DESTINATION_ID='" + distBoardSel
								+ "') and (TUBE_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT A.TUBE_ID,B.TUBE_NAME,B.TUBE_COLOR,B.TUBE_NUMBER,A.FIBER_CABLE_ID,C.FIBER_CABLE_NAME,B.TUBE_NETWORK_LEVEL FROM (TUBE_AUXILIARY_POINTS A LEFT JOIN  FIBER_TUBES B on A.TUBE_ID = B.TUBE_ID) LEFT JOIN FIBER_CABLES C on A.FIBER_CABLE_ID=C.FIBER_CABLE_ID  WHERE (A.AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'access')")
						.getResultList();
				rtn.put("DistributionTubeData", TubeAccessData);

				List<Object[]> StrandAccessData = session.createNativeQuery(
						"Select DISTINCT a.BP_STRAND_ID,a.BP_STRAND_NAME,a.BP_STRAND_COLOR,a.BP_STRAND_NB,a.BP_FIBER_ID,a.BP_FIBER_NAME,a.BP_TUBE_ID,a.BP_TUBE_NAME,b.STRAND_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a left join FIBER_STRANDS b on a.BP_STRAND_ID = b.STRAND_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT a.FP_STRAND_ID,a.FP_STRAND_NAME,a.FP_STRAND_COLOR,a.FP_STRAND_NB,a.FP_FIBER_ID,a.FP_FIBER_NAME,a.FP_TUBE_ID,a.FP_TUBE_NAME,b.STRAND_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a left join FIBER_STRANDS b on a.FP_STRAND_ID = b.STRAND_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT A.STRAND_ID,A.STRAND_NAME,A.STRAND_COLOR,A.STRAND_NUMBER,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_ID,C.TUBE_NAME,A.STRAND_NETWORK_LEVEL FROM (FIBER_STRANDS A LEFT JOIN FIBER_CABLES B on A.FIBER_CABLE_ID=B.FIBER_CABLE_ID) LEFT JOIN FIBER_TUBES C on A.TUBE_ID=C.TUBE_ID WHERE (A.SOURCE_ID='"
								+ distBoardSel + "' OR A.DESTINATION_ID='" + distBoardSel
								+ "') and (STRAND_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT A.STRAND_ID,B.STRAND_NAME,B.STRAND_COLOR,B.STRAND_NUMBER,C.FIBER_CABLE_ID,C.FIBER_CABLE_NAME,D.TUBE_ID,D.TUBE_NAME,B.STRAND_NETWORK_LEVEL FROM (STRAND_AUXILIARY_POINTS A LEFT JOIN  FIBER_STRANDS B on A.STRAND_ID = B.STRAND_ID) LEFT JOIN FIBER_CABLES C on A.FIBER_CABLE_ID=C.FIBER_CABLE_ID LEFT JOIN FIBER_TUBES D on B.TUBE_ID=D.TUBE_ID WHERE (A.AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'access')")
						.getResultList();
				rtn.put("DistributionStrandData", StrandAccessData);
				session.clear();
				tx.commit();
			}

			catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in pathDistBoard due to \n " + exceptionAsString);
				logger.info("Error in pathDistBoard due to \n " + exceptionAsString);
				rtn.put("BackboneCableData", null);
				rtn.put("BackboneTubeData", null);
				rtn.put("BackboneStrandData", null);
				rtn.put("MetroCableData", null);
				rtn.put("MetroTubeData", null);
				rtn.put("MetroStrandData", null);
				rtn.put("DistributionCableData", null);
				rtn.put("DistributionTubeData", null);
				rtn.put("DistributionStrandData", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}

		}
		return rtn;

	}

	@RequestMapping(value = "/TrenchBoQ", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> TrenchBoQ(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();

		String projectID = request.getParameter("projectID").toString();
		String trenchID = request.getParameter("trenchID").toString();

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				List<Object[]> TrenchData = session.createNativeQuery(
						"SELECT SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LATITUDE,SOURCE_LONGITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY,LENGTH,NUM_DUCTS,TRENCH_NAME FROM TRENCH WHERE TRENCH_ID='"
								+ trenchID + "' and PROJECT_ID='" + projectID + "'")
						.getResultList();

				rtn.put("TrenchData", TrenchData);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in TrenchBoQ due to \n " + exceptionAsString);
				logger.info("Error in TrenchBoQ due to \n " + exceptionAsString);
				rtn.put("TrenchData", null);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/saveProject", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> saveProject(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);
			String ProjectId = "";
			String projectType=request.getParameter("projectType");
			

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				
				try {
					String ipAddress = request.getRemoteAddr();
					String updateModfUser=request.getParameter("updateModfUser");
					PhysicalLayerActivity PhyAct= new PhysicalLayerActivity();
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

					String PhyActID=
							 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							
							PhyAct.setPhyActID(PhyActID);
							PhyAct.setScreenName("Project");
							PhyAct.setUsername(updateModfUser);
							PhyAct.setUserIP(ipAddress);
							PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
					
					if (request.getParameter("actionProjectContext").equals("Insert")) {
						System.out.println(
								"actionProjectContext insidde insert>>" + request.getParameter("actionProjectContext"));
						synchronized (this) {
							// ProjectId = "PROJECT_" + year + "_" + appConfig.getSeqNbr(71,session);
							ProjectId = "PROJECT_" + year + "_" + Integer.parseInt(session
									.createNativeQuery("SELECT PROJECT FROM SEQ_TABLE").uniqueResult().toString());
							System.out.println("the project id is " + ProjectId);
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET PROJECT = PROJECT + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						System.out.println("all gd till here>>" + ProjectId);
						query = session.createNativeQuery("INSERT INTO PROJECT VALUES ('" + ProjectId + "','"
								+ request.getParameter("ProjectName") +"','"+projectType+ "')");
						query.executeUpdate();
						rtn.put("ProjectId", ProjectId);
						PhyAct.setActivityDescription("Add new Project");
						
					} else {
						ProjectId = request.getParameter("ProjectId");
						query = session.createNativeQuery(
								"UPDATE PROJECT SET PROJECT_ID= '" + request.getParameter("ProjectId")
										+ "',PROJECT_NAME= '" + request.getParameter("ProjectName")
										+ "' where PROJECT_ID='" + request.getParameter("ProjectId") + "'");
						query.executeUpdate();
						rtn.put("ProjectId", request.getParameter("ProjectId"));
						PhyAct.setActivityDescription("Edit Existing Project");
					}
					PhyAct.setElementID(ProjectId);
					session.saveOrUpdate(PhyAct);
					// String projectNameId=ProjectId+":"+request.getParameter("ProjectName");
					rtn.put("ProjectName", request.getParameter("ProjectName"));
					rtn.put("projectType", projectType);

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in saveProject due to \n " + exceptionAsString);
					logger.info("Error in saveProject due to \n " + exceptionAsString);
					rtn.put("ProjectId", null);
					rtn.put("projectName", null);
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
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveManhole", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> saveManhole(HttpServletRequest request, HttpServletResponse response) {


		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);
			String manholeId = "", manholeName = "", city;
			List countCables;

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				System.out.println("project id is " + request.getParameter("ProjectId"));

				try {

					Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
					PhysicalLayerActivity PhyAct=new PhysicalLayerActivity();
					String updateModfUser =request.getParameter("updateModfUser");
					String ipAddress = getIpAddress(request);

					String manholeCreatedDate = request.getParameter("manholeCreatedDate");
					Timestamp manholeCreationDate;
					if (StringUtils.equalsIgnoreCase(manholeCreatedDate, "")) {
						manholeCreationDate = lastModifiedDate;
					} else {
						DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
						manholeCreationDate = new Timestamp(
								formatter.parse(request.getParameter("manholeCreatedDate")).getTime());
					}
					
					String PhyActID=
							 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							
							PhyAct.setPhyActID(PhyActID);
							PhyAct.setScreenName("Manhole");
							PhyAct.setUsername(updateModfUser);
							PhyAct.setUserIP(ipAddress);
							PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
							
								
							
						
							
					if (request.getParameter("actionManholeContext").equals("Insert")) {
						synchronized (this) {

							manholeId = "MH_" + year + "_" + Integer.parseInt(session
									.createNativeQuery("SELECT MANHOLE FROM SEQ_TABLE").uniqueResult().toString());
							String[] idSplit;
							idSplit = manholeId.split("_");
							
							PhyAct.setActivityDescription("Add New Element");
							if (request.getParameter("ManholeName").isEmpty()) {
								manholeName = "MH_" + request.getParameter("ManholeCity") + "_" + idSplit[1] + "_"
										+ idSplit[2];
								Query junction = session.createNativeQuery(
										"select count(*) from manhole where manhole.manhole_id IN  (select junction.physical_layer_id  from junction where junction.physical_layer_id='"
												+ manholeId + "')");
								junction.executeUpdate();
								System.out.println(junction.getSingleResult().toString());
								if (Integer.parseInt(junction.getSingleResult().toString()) > 0) {
									manholeName += "_J";
								}
								System.out.print(manholeName);
							} else {
								manholeName = request.getParameter("ManholeName");
							}
							System.out.println("man id " + manholeId);
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET MANHOLE =  MANHOLE +1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							session.flush();
							session.clear();
						}
						// replacing the single quote with nothing to avoid problems in database
						manholeName = manholeName.replace("'", "");
						city = request.getParameter("ManholeCity").replace("'", "");

						query = session.createNativeQuery(
								"INSERT INTO MANHOLE(MANHOLE_ID, MANHOLE_NAME, MANHOLE_MODEL, LONGITUDE, LATITUDE, CITY, PROJECT_ID, DM_NAME,CREATION_DATE,LAST_MODIFIED_DATE,OWNER,MH_INSTALLER,MH_ENGINEER_NAME) VALUES ('"
										+ manholeId + "','" + manholeName + "','" + request.getParameter("ManholeModel")
										+ "','" + request.getParameter("ManholeLong") + "','"
										+ request.getParameter("ManholeLat") + "','" + city + "','"
										+ request.getParameter("ProjectId") + "','" + null + "',TIMESTAMP '"
										+ manholeCreationDate + "',TIMESTAMP '" + lastModifiedDate+"','"+request.getParameter("manholeowner")+"','"+request.getParameter("manholeInstaller")+"','"+request.getParameter("manholeEngineerName")+ "')");
						query.executeUpdate();
						rtn.put("ManholeId", manholeId);
						rtn.put("ManholeName", manholeName);
						session.flush();
						session.clear();
						PhyAct.setElementID(manholeId);
						PhyAct.setActivityDescription("Add New Element");
						session.saveOrUpdate(PhyAct);
						
					} else {
						manholeId = request.getParameter("ManholeId");
						PhyAct.setElementID(manholeId);
						PhyAct.setActivityDescription("Edit Existing Element");
						session.saveOrUpdate(PhyAct);
						String[] idSplit;
						idSplit = manholeId.split("_");

						if (request.getParameter("ManholeName").isEmpty()) {
							manholeName = "MH_" + request.getParameter("ManholeCity") + "_" + idSplit[1] + "_"
									+ idSplit[2];
							Query junction = session.createNativeQuery(
									"select count(*) from manhole where manhole.manhole_id IN  (select junction.physical_layer_id  from junction where junction.physical_layer_id='"
											+ manholeId + "')");
							junction.executeUpdate();
							System.out.println(junction.getSingleResult().toString());
							if (Integer.parseInt(junction.getSingleResult().toString()) > 0) {
								manholeName += "_J";
							}
							System.out.print(manholeName);
						} else {
							manholeName = request.getParameter("ManholeName");
						}
						// replacing the single quote with nothing to avoid problems in database
						manholeName = manholeName.replace("'", "");
						city = request.getParameter("ManholeCity").replace("'", "");

						query = session.createNativeQuery("UPDATE MANHOLE SET MANHOLE_ID= '"
								+ request.getParameter("ManholeId") + "',MANHOLE_NAME= '" + manholeName
								+ "',MANHOLE_MODEL= '" + request.getParameter("ManholeModel") + "',LONGITUDE= '"
								+ request.getParameter("ManholeLong") + "',LATITUDE= '"
								+ request.getParameter("ManholeLat") + "',CITY= '" + city + "',PROJECT_ID='"
								+ request.getParameter("ProjectId") + "',OWNER ='"
								+ request.getParameter("manholeowner") + "',LAST_MODIFIED_DATE= TIMESTAMP '"
								+ lastModifiedDate + "',MH_INSTALLER ='" + request.getParameter("manholeInstaller") + "',MH_ENGINEER_NAME ='"
								+ request.getParameter("manholeEngineerName") + "' where MANHOLE_ID='" + request.getParameter("ManholeId") + "'");
						query.executeUpdate();
						rtn.put("ManholeId", request.getParameter("ManholeId"));
						rtn.put("ManholeName", manholeName);
						session.flush();
						session.clear();
					
						Query updateJunction = session.createNativeQuery(
								"UPDATE JUNCTION SET PHYSICAL_LAYER_ID= '" + request.getParameter("ManholeId")
										+ "',PHYSICAL_LAYER_NAME= '" + request.getParameter("ManholeName")
										+ "',LONGITUDE= '" + Float.parseFloat(request.getParameter("ManholeLong"))
										+ "',LATITUDE= '" + Float.parseFloat(request.getParameter("ManholeLat"))
										+ "',CITY= '" + request.getParameter("ManholeCity") + "',PROJECT_ID='"
										+ request.getParameter("ProjectId") + "' where PHYSICAL_LAYER_ID='"
										+ request.getParameter("ManholeId") + "'");
						updateJunction.executeUpdate();
						session.flush();
						session.clear();
					}

					countCables = session.createNativeQuery(
							"select 'fiber', count(a.FIBER_CABLE_ID),'tube', count(b.TUBE_ID) ,'strand', count(c.STRAND_ID) from FIBER_CABLES a "
									+ "LEFT JOIN  FIBER_TUBES b ON  a.FIBER_CABLE_ID = b.FIBER_CABLE_ID AND ( b.SOURCE_ID='"
									+ manholeId + "' AND b.SOURCE_NAME='" + manholeName + "' ) or (b.DESTINATION_ID='"
									+ manholeId + "' AND b.DESTINATION_NAME='" + manholeName + "' ) "
									+ "LEFT JOIN  FIBER_STRANDS c ON a.FIBER_CABLE_ID = c.FIBER_CABLE_ID AND ( c.SOURCE_ID='"
									+ manholeId + "' AND c.SOURCE_NAME='" + manholeName + "' ) or ( c.DESTINATION_ID='"
									+ manholeId + "' AND c.DESTINATION_NAME='" + manholeName + "' ) "
									+ "WHERE a.PROJECT_ID ='" + request.getParameter("ProjectId")
									+ "' AND ( a.SOURCE_ID='" + manholeId + "' AND a.SOURCE_NAME='" + manholeName
									+ "' ) or (a.DESTINATION_ID='" + manholeId + "' AND a.DESTINATION_NAME='"
									+ manholeName + "' ) ")
							.getResultList();
					rtn.put("countCables", countCables);
					// rtn.put("ManholeName", manholeName);

					List<Object[]> countJct = session.createNativeQuery(
							"select JUNCTION_ID,JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME,JUNCTION_NUMBER,CAPACITY,CITY,LONGITUDE,LATITUDE,count(*) from JUNCTION  where PHYSICAL_LAYER_ID = '"
									+ request.getParameter("ManholeId")
									+ "' group by JUNCTION_ID,JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME,JUNCTION_NUMBER,CAPACITY,CITY,LONGITUDE,LATITUDE ")
							.getResultList();
					rtn.put("countJct", countJct);

					List<Object[]> junctionTotalCount = session
							.createNativeQuery("select coalesce(count(*),0) from JUNCTION  where PHYSICAL_LAYER_ID = '"
									+ request.getParameter("ManholeId") + "' ")
							.getResultList();
					rtn.put("junctionTotalCount", junctionTotalCount);
					session.flush();
					session.clear();
					tx.commit();
					
				} catch (Exception e) {
					tx.rollback();
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in saveManhole due to \n " + exceptionAsString);
					logger.info("Error in saveManhole due to \n " + exceptionAsString);
					rtn.put("ManholeId", null);
					rtn.put("ManholeName", null);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();

					}
				}
			}
			
			return rtn;
		}
	
	}
	
	@RequestMapping(value = "/saveHandhole", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> saveHandhole(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				String ipAddress = getIpAddress(request);
				String updateModfUser=request.getParameter("updateModfUser");
				PhysicalLayerActivity PhyAct= new PhysicalLayerActivity();

				String handholeId = "", handholeName = "";
				Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

				String handholeCreatedDate = request.getParameter("handholeCreatedDate");
				Timestamp handholeCreationDate;
				if (StringUtils.equalsIgnoreCase(handholeCreatedDate, "")) {
					handholeCreationDate = lastModifiedDate;
				} else {
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					handholeCreationDate = new Timestamp(
							formatter.parse(request.getParameter("handholeCreatedDate")).getTime());
				}
				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				 String PhyActID=
						 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						
						PhyAct.setPhyActID(PhyActID);
						PhyAct.setElementID(handholeId);
						PhyAct.setScreenName("Handhole");
						PhyAct.setUsername(updateModfUser);
						PhyAct.setUserIP(ipAddress);
						PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
					
				if (request.getParameter("actionHandholeContext").equals("Insert")) {

					synchronized (this) {
						// String numSeq = appConfig.getSequenceNbr(50);

						handholeId = "HH_" + year + "_" + Integer.parseInt(
								session.createNativeQuery("SELECT HANDHOLE FROM SEQ_TABLE").uniqueResult().toString());
						System.out.println("han id " + handholeId);
						String[] idSplit;
						idSplit = handholeId.split("_");

						if (request.getParameter("HandholeName").isEmpty()) {
							handholeName = "HH_" + request.getParameter("HandholeCity") + "_" + idSplit[1] + "_"
									+ idSplit[2];
							Query junction = session.createNativeQuery(
									"select count(*) from handhole where handhole.handhole_id IN  (select junction.physical_layer_id  from junction where junction.physical_layer_id='"
											+ handholeId + "')");
							junction.executeUpdate();
							System.out.println(junction.getSingleResult().toString());
							if (Integer.parseInt(junction.getSingleResult().toString()) > 0) {
								handholeName += "_J";
							}
							System.out.print(handholeName);
							
						} else {
							handholeName = request.getParameter("HandholeName");
						}
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET HANDHOLE =  HANDHOLE +1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
					}

					Query InsertHandhole = session.createNativeQuery(
							"INSERT INTO HANDHOLE(HANDHOLE_ID, HANDHOLE_NAME, HANDHOLE_MODEL, LONGITUDE, LATITUDE, CITY, PROJECT_ID, DM_NAME,CREATION_DATE,LAST_MODIFIED_DATE,OWNER,HH_INSTALLER,HH_ENGINEER_NAME ) VALUES ('"
									+ handholeId + "','" + handholeName + "','" + request.getParameter("HandholeModel")
									+ "','" + request.getParameter("HandholeLong") + "','"
									+ request.getParameter("HandholeLat") + "','" + request.getParameter("HandholeCity")
									+ "','" + request.getParameter("ProjectId") + "','" + null + "',TIMESTAMP '"
									+ handholeCreationDate + "',TIMESTAMP '" + lastModifiedDate + "','" + request.getParameter("handholeOwner") + "','" + request.getParameter("handholeInstaller") + "','"+ request.getParameter("handholeEngineerName") + "')");
					InsertHandhole.executeUpdate();
					rtn.put("handholeId", handholeId);
					rtn.put("handholeName", handholeName);
					    PhyAct.setElementID(handholeId);
					 	PhyAct.setActivityDescription("Add New Element");
							session.saveOrUpdate(PhyAct);
				} else {

					handholeId = request.getParameter("handholeId");
					String[] idSplit;
					idSplit = handholeId.split("_");

					if (request.getParameter("HandholeName").isEmpty()) {
						handholeName = "HH_" + request.getParameter("HandholeCity") + "_" + idSplit[1] + "_"
								+ idSplit[2];
						Query junction = session.createNativeQuery(
								"select count(*) from handhole where handhole.handhole_id IN  (select junction.physical_layer_id  from junction where junction.physical_layer_id='"
										+ handholeId + "')");
						junction.executeUpdate();
						System.out.println(junction.getSingleResult().toString());
						if (Integer.parseInt(junction.getSingleResult().toString()) > 0) {
							handholeName += "_J";
						}
						System.out.print(handholeName);
					}

					else {
						handholeName = request.getParameter("HandholeName");
					}

					System.out.println("handhole " + request.getParameter("handholeId"));
					Query updateHandhole = session.createNativeQuery("UPDATE HANDHOLE SET HANDHOLE_ID= '" + handholeId
							+ "',HANDHOLE_NAME= '" + handholeName + "',HANDHOLE_MODEL= '"
							+ request.getParameter("HandholeModel") + "',LONGITUDE= '"
							+ request.getParameter("HandholeLong") + "',LATITUDE= '"
							+ request.getParameter("HandholeLat") + "',CITY= '" + request.getParameter("HandholeCity")
							+ "',PROJECT_ID='" + request.getParameter("ProjectId") + "',LAST_MODIFIED_DATE= TIMESTAMP '"
							+ lastModifiedDate + "',OWNER= '" + request.getParameter("handholeOwner")+ "',HH_INSTALLER= '" + request.getParameter("handholeInstaller")+ "',HH_ENGINEER_NAME= '" + request.getParameter("handholeEngineerName")+ "' where HANDHOLE_ID='" + request.getParameter("handholeId") + "'");
					updateHandhole.executeUpdate();
					rtn.put("handholeId", request.getParameter("handholeId"));
					rtn.put("handholeName", handholeName);
					PhyAct.setElementID(handholeId);
				 	PhyAct.setActivityDescription("Edit Existing Element");
					session.saveOrUpdate(PhyAct);
					query = session.createNativeQuery("UPDATE JUNCTION SET PHYSICAL_LAYER_ID= '" + handholeId
							+ "',PHYSICAL_LAYER_NAME= '" + request.getParameter("HandholeName") + "',LONGITUDE= '"
							+ Float.parseFloat(request.getParameter("HandholeLong")) + "',LATITUDE= '"
							+ Float.parseFloat(request.getParameter("HandholeLat")) + "',CITY= '"
							+ request.getParameter("HandholeCity") + "',PROJECT_ID='"
							+ request.getParameter("ProjectId") + "' where PHYSICAL_LAYER_ID='"
							+ request.getParameter("handholeId") + "'");
					query.executeUpdate();
				}

				String handholeNameId = handholeId + ":" + handholeName;

				List countCables = session.createNativeQuery(
						"select 'fiber', count(a.FIBER_CABLE_ID),'tube', count(b.TUBE_ID) ,'strand', count(c.STRAND_ID) from FIBER_CABLES a "
								+ "LEFT JOIN  FIBER_TUBES b ON  a.FIBER_CABLE_ID = b.FIBER_CABLE_ID AND ( b.SOURCE_ID='"
								+ handholeId + "' AND b.SOURCE_NAME='" + handholeName + "' ) or ( b.DESTINATION_ID='"
								+ handholeNameId + "' AND b.DESTINATION_NAME='" + handholeName + "' ) "
								+ "LEFT JOIN  FIBER_STRANDS c ON a.FIBER_CABLE_ID = c.FIBER_CABLE_ID AND ( c.SOURCE_ID='"
								+ handholeId + "' AND c.SOURCE_NAME='" + handholeName + "' ) or ( c.DESTINATION_ID='"
								+ handholeId + "' AND c.DESTINATION_NAME='" + handholeName + "' ) "
								+ "WHERE a.PROJECT_ID ='" + request.getParameter("ProjectId") + "' AND ( a.SOURCE_ID='"
								+ handholeId + "' AND a.SOURCE_NAME='" + handholeName + "' ) or (a.DESTINATION_ID='"
								+ handholeId + "' AND a.DESTINATION_NAME='" + handholeName + "' )")
						.getResultList();

				System.out.println("countCables " + countCables);
				rtn.put("countCables", countCables);
				// rtn.put("HandholeName", request.getParameter("HandholeName"));

				List<Object[]> countJct = session.createNativeQuery(
						"select JUNCTION_ID,JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME,JUNCTION_NUMBER,CAPACITY,CITY,LONGITUDE,LATITUDE,count(*) from JUNCTION  where PHYSICAL_LAYER_ID = '"
								+ request.getParameter("handholeId")
								+ "' group by JUNCTION_ID,JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME,JUNCTION_NUMBER,CAPACITY,CITY,LONGITUDE,LATITUDE ")
						.getResultList();
				rtn.put("countJct", countJct);

				List<Object[]> junctionTotalCount = session
						.createNativeQuery("select coalesce(count(*),0) from JUNCTION  where PHYSICAL_LAYER_ID = '"
								+ request.getParameter("handholeId") + "' ")
						.getResultList();
				rtn.put("junctionTotalCount", junctionTotalCount);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in saveHandhole due to \n " + exceptionAsString);
				logger.info("Error in saveHandhole due to \n " + exceptionAsString);
				rtn.put("handholeId", null);
				rtn.put("HandholeName", null);
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findCountAll", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountAll(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			List<Object> allPhysicalNodesCount = session.createNativeQuery(
					"select 'MANHOLE', count (*) from MANHOLE WHERE PROJECT_ID='" + request.getParameter("ProjectId")
							+ "' " + "union select 'HANDHOLE', count (*) from HANDHOLE WHERE PROJECT_ID='"
							+ request.getParameter("ProjectId") + "' "
							+ "union select 'FIBER_CABLES', count (*) from FIBER_CABLES WHERE PROJECT_ID='"
							+ request.getParameter("ProjectId") + "' "
							+ "union select 'DISTRIBUTION_BOARD', count (*) from DISTRIBUTION_BOARD WHERE PROJECT_ID='"
							+ request.getParameter("ProjectId") + "' "
							+ "union select 'TRENCH', count (*) from TRENCH WHERE PROJECT_ID='"
							+ request.getParameter("ProjectId") + "' "
							+ "union select 'JUNCTION', count (*) from JUNCTION WHERE PROJECT_ID='"
							+ request.getParameter("ProjectId") + "' "
							+ "union select 'zgeo', coalesce(sum(total_geo_distance),0) from fiber_cables where Project_ID='"
							+ request.getParameter("ProjectId") + "' "
							+ "union select 'zzLength', coalesce(sum(Length),0) from fiber_cables where Project_ID='"
							+ request.getParameter("ProjectId") + "' "
							+ "union select 'zzz' , coalesce(sum((total_geo_distance*number_of_strands*number_of_tubes)),0) from fiber_cables where Project_ID='"
							+ request.getParameter("ProjectId") + "' "
							+ "union select 'zzzw', coalesce(sum((Length*number_of_strands*number_of_tubes)),0) from fiber_cables where Project_ID='"
							+ request.getParameter("ProjectId") + "' "
							+ "union select 'zzzx', coalesce(sum(total_geo_distance),0) from fiber_tubes "
							+ "union select 'zzzy', coalesce(sum(total_geo_distance),0) from fiber_strands "
							+ "union select 'zzzzx', coalesce(sum(Length),0) from fiber_tubes "
							+ "union select 'zzzzy', coalesce(sum(Length),0) from fiber_strands ")
					.getResultList();

			rtn.put("allPhysicalNodesCount", allPhysicalNodesCount);

			tx.commit();
			session.close();

		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SearchForAuxName", method = RequestMethod.POST)
	@ResponseBody
	public TreeMap<Object, Object> SearchForAuxName(@ModelAttribute ItemParameters itemParameters) {

		TreeMap<Object, Object> sortedMap = new TreeMap<>();
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String queryStmnt = "";
			try {
				if (itemParameters.getDictParameter().size() > 0) {

					String longitude = "", latitude = "";
					String newLongitude = "", newLatitude = "";
					Map<Object, List<Object>> map = new HashMap<>();

					for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {

						longitude = itemParameters.getDictParameter().get(i).get("longitude");
						String[] lngparts = longitude.split("\\.");
						String lngBeforeDecimal = lngparts[0];
						String lngAfterDecimal = lngparts[1];

						latitude = itemParameters.getDictParameter().get(i).get("latitude");
						String[] latparts = latitude.split("\\.");
						String latBeforeDecimal = latparts[0];
						String latAfterDecimal = latparts[1];

						if (lngAfterDecimal.length() <= 5 && latAfterDecimal.length() <= 5) {
							newLongitude = longitude;
							newLatitude = latitude;

							queryStmnt = "select MANHOLE_ID || ':' || MANHOLE_NAME from MANHOLE WHERE longitude = '"
									+ newLongitude + "' AND  latitude = '" + newLatitude + "' FETCH FIRST 1 ROWS ONLY ";
							query = session.createNativeQuery(queryStmnt);

							if (query.getResultList().size() == 0) {
								queryStmnt = "SELECT HANDHOLE_ID || ':' || HANDHOLE_NAME FROM HANDHOLE WHERE longitude = '"
										+ newLongitude + "' AND  latitude = ' " + newLatitude
										+ "' FETCH FIRST 1 ROWS ONLY";
								query = session.createNativeQuery(queryStmnt);
							}

						} else if (lngAfterDecimal.length() >= 6 && latAfterDecimal.length() >= 6) {
							lngAfterDecimal = lngAfterDecimal.substring(0, lngAfterDecimal.length() - 1);
							newLongitude = lngBeforeDecimal + "." + lngAfterDecimal;
							latAfterDecimal = latAfterDecimal.substring(0, latAfterDecimal.length() - 1);
							newLatitude = latBeforeDecimal + "." + latAfterDecimal;

							queryStmnt = "select MANHOLE_ID || ':' || MANHOLE_NAME from MANHOLE WHERE longitude LIKE '"
									+ newLongitude + "%' AND latitude LIKE '" + newLatitude
									+ "%'  FETCH FIRST 1 ROWS ONLY ";
							query = session.createNativeQuery(queryStmnt);

							if (query.getResultList().size() == 0) {
								queryStmnt = "select HANDHOLE_ID || ':' || HANDHOLE_NAME from HANDHOLE WHERE longitude LIKE '"
										+ newLongitude + "%' AND latitude LIKE '" + newLatitude
										+ "%' FETCH FIRST 1 ROWS ONLY ";
								query = session.createNativeQuery(queryStmnt);
							}
						}
						List<Object> resultList = query.getResultList();
						//if (resultList.size() == 1 || resultList.size() == 0) {
							if (resultList.size() == 1) {
							String AuxName = (String) query.getSingleResult();
							map.put(i, new ArrayList<>(Arrays.asList(
									itemParameters.getDictParameter().get(i).get("longitude"),
									itemParameters.getDictParameter().get(i).get("latitude"), "", AuxName, "", "")));

						//} else if (resultList.size() > 1) {
						} else if (resultList.size() > 1 || resultList.size() ==0) {
							String AuxName = "null";
							map.put(i, new ArrayList<>(Arrays.asList(
									itemParameters.getDictParameter().get(i).get("longitude"),
									itemParameters.getDictParameter().get(i).get("latitude"), "", AuxName, "", "")));
						}
					}

					sortedMap.putAll(map);
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in SearchForAuxName due to \n " + exceptionAsString);
				logger.info("Error in SearchForAuxName due to \n " + exceptionAsString);
				logger.info("Failed query: " + queryStmnt);
				e.printStackTrace();
			}

			finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return sortedMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/UpdateAuxPoints", method = RequestMethod.POST)
	@ResponseBody
	public TreeMap<Object, Object> UpdateAuxPoints(HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters) {

		TreeMap<Object, Object> sortedMap = new TreeMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedFiberContext = request.getParameter("ID");

			try {
				Map<Object, List<Object>> map = new HashMap<>();
				String queryy = "";
				List<Object[]> queryStmnt = session.createNativeQuery(
						"select AUXILIARY_ID,LONGITUDE,LATITUDE,AUXILIARY_POINT_NAME from FIBER_AUXILIARY_POINTS where FIBER_CABLE_ID ='"
								+ selectedFiberContext + "'")
						.getResultList();

				for (Object[] row : queryStmnt) {
					String longitude = row[1].toString();
					String latitude = row[2].toString();
					String AuxName = row[3].toString();

					String[] lngparts = longitude.split("\\.");
					String lngBeforeDecimal = lngparts[0];
					String lngAfterDecimal = lngparts[1];
					String newLongitude = "";

					String[] latparts = latitude.split("\\.");
					String latBeforeDecimal = latparts[0];
					String latAfterDecimal = latparts[1];
					String newLatitude = "";

					if (lngAfterDecimal.length() <= 5 || latAfterDecimal.length() <= 5) {
						newLongitude = longitude;
						newLatitude = latitude;
						queryy = "select MANHOLE_ID || ':' || MANHOLE_NAME from MANHOLE WHERE longitude = '"
								+ newLongitude + "' AND latitude = '" + newLatitude + "'  FETCH FIRST 1 ROWS ONLY ";
						query = session.createNativeQuery(queryy);

						if (query.getResultList().size() == 0) {
							queryy = "SELECT HANDHOLE_ID || ':' || HANDHOLE_NAME FROM HANDHOLE WHERE longitude = '"
									+ newLongitude + "' AND latitude = '" + newLatitude + "' FETCH FIRST 1 ROWS ONLY";
							query = session.createNativeQuery(queryy);
						}
					} else if (lngAfterDecimal.length() >= 6 || latAfterDecimal.length() >= 6) {
						lngAfterDecimal = lngAfterDecimal.substring(0, lngAfterDecimal.length() - 1);
						newLongitude = lngBeforeDecimal + "." + lngAfterDecimal;
						latAfterDecimal = latAfterDecimal.substring(0, latAfterDecimal.length() - 1);
						newLatitude = latBeforeDecimal + "." + latAfterDecimal;

						queryy = "select MANHOLE_ID || ':' || MANHOLE_NAME from MANHOLE WHERE longitude LIKE '"
								+ newLongitude + "%' AND latitude LIKE '" + newLatitude + "%' FETCH FIRST 1 ROWS ONLY ";
						query = session.createNativeQuery(queryy);

						if (query.getResultList().size() == 0) {
							queryy = "select HANDHOLE_ID || ':' || HANDHOLE_NAME from HANDHOLE WHERE longitude LIKE '"
									+ newLongitude + "%' AND latitude LIKE '" + newLatitude
									+ "%' FETCH FIRST 1 ROWS ONLY ";
							query = session.createNativeQuery(queryy);
						}
					}

					if (query.getResultList().size() == 1 || query.getResultList().size() == 0) {
						AuxName = (String) query.getSingleResult();
						List<Object> values = new ArrayList<>();

						Arrays.asList(values.add("longitude"));
						Arrays.asList(values.add("latitude"));
						Arrays.asList(values.add(""));
						Arrays.asList(values.add(AuxName));
						Arrays.asList(values.add(""));
						Arrays.asList(values.add(""));
						map.put("longitude" + "latitude", values);

					} else if (query.getResultList().size() > 1) {
						AuxName = "null";
						List<Object> values = new ArrayList<>();

						Arrays.asList(values.add("longitude"));
						Arrays.asList(values.add("latitude"));
						Arrays.asList(values.add(""));
						Arrays.asList(values.add(AuxName));
						Arrays.asList(values.add(""));
						Arrays.asList(values.add(""));
						map.put("longitude" + "latitude", values);
					}
					sortedMap.putAll(map);
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in UpdateAuxPoints due to \n " + exceptionAsString);
				logger.info("Error in UpdateAuxPoints due to \n " + exceptionAsString);
				e.printStackTrace();

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return sortedMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GrabManHand", method = RequestMethod.POST)
	@ResponseBody
	public TreeMap<Object, Object> GrabManHand(HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters) {

		TreeMap<Object, Object> resultManhole = new TreeMap<>();
		TreeMap<Object, Object> resultMapHand = new TreeMap<>();
		TreeMap<Object, Object> sortedMap = new TreeMap<>();
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedFiberContext = request.getParameter("ID");

			try {
				List<Object[]> auxList = session.createNativeQuery(
						"SELECT AUXILIARY_ID, LONGITUDE, LATITUDE, AUXILIARY_POINT_NAME FROM FIBER_AUXILIARY_POINTS WHERE FIBER_CABLE_ID ='"
								+ selectedFiberContext + "'")
						.getResultList();

				// Create a set to store unique manhole Id
				Set<String> addedManholeNames = new HashSet<>();
				for (Object[] auxData : auxList) {
					String auxId = (String) auxData[0];
					double auxLng = Double.parseDouble(auxData[1].toString());
					double auxLat = Double.parseDouble(auxData[2].toString());
					String auxPointName = (String) auxData[3];

					Map<String, Object> nearestManhole = findNearestManhole(auxLng, auxLat, 30.0, auxList); // Pass 30.0
																											// meters as
																											// the
																											// maximum
																											// distance

					if (nearestManhole != null) {
						String manholeId = (String) nearestManhole.get("ManholeId");
						String manholeName = (String) nearestManhole.get("ManholeName");
						String combinedValue = manholeId + ":" + manholeName;
						double manholeLng = (Double) nearestManhole.get("ManholeLng");
						double manholeLat = (Double) nearestManhole.get("ManholeLat");

						if (!addedManholeNames.contains(manholeId)) {
							resultManhole.put(auxId,
									new ArrayList<>(Arrays.asList(manholeLng, manholeLat, "", combinedValue, "", "")));
							addedManholeNames.add(manholeId);
							System.out.println("Nearest Manhole IS : " + combinedValue);
							System.out.println("Distance: " + nearestManhole.get("Distance") + " meters");
							System.out.println("--------------------------------------------------------------");
						}
					}
					sortedMap.putAll(resultManhole);
				}

				// Create a set to store unique handhole Id
				Set<String> addedHandholeNames = new HashSet<>();
				for (Object[] auxData : auxList) {
					String auxId = (String) auxData[0];
					double auxLng = Double.parseDouble(auxData[1].toString());
					double auxLat = Double.parseDouble(auxData[2].toString());
					/* Pass 30.0 meters as the maximum distance*/					
					Map<String, Object> nearestHandhole = findNearestHandhole(auxLng, auxLat, 30.0, auxList);
					if (nearestHandhole != null) {
						String handholeId = (String) nearestHandhole.get("HandholeId");
						String handholeName = (String) nearestHandhole.get("HandholeName");
						String combinedValue = handholeId + ":" + handholeName;
						double handholeLng = (Double) nearestHandhole.get("HandholeLng");
						double handholeLat = (Double) nearestHandhole.get("HandholeLat");

						if (!addedHandholeNames.contains(handholeId)) {
							resultMapHand.put(auxId, new ArrayList<>(
									Arrays.asList(handholeLng, handholeLat, "", combinedValue, "", "")));
							addedHandholeNames.add(handholeId);
							System.out.println("Nearest HandHole IS : " + combinedValue);
							System.out.println("Distance: " + nearestHandhole.get("Distance") + " meters");
							System.out.println("--------------------------------------------------------------");
						}
					}
					sortedMap.putAll(resultMapHand);
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in UpdateAuxPoints due to \n " + exceptionAsString);
				logger.info("Error in UpdateAuxPoints due to \n " + exceptionAsString);
				e.printStackTrace();

			} finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}
		}
		return sortedMap;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> findNearestManhole(double auxLng, double auxLat, double maxDistance,
			List<Object[]> auxList) {
		Map<String, Object> nearestManhole = null;
		double minDistance = maxDistance + 1;

		List<Object[]> manholeList = session
				.createNativeQuery("SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE FROM MANHOLE")
				.getResultList();

		for (Object[] manholeData : manholeList) {
			String manholeId = (String) manholeData[0];
			String manholeName = (String) manholeData[1];
			double manholeLng = Double.parseDouble(manholeData[2].toString());
			double manholeLat = Double.parseDouble(manholeData[3].toString());

			double distance = calculateDistance(auxLat, auxLng, manholeLat, manholeLng); // Swap lat and lng in
																							// calculateDistance
																							// function

			// Check if manholeID exists in auxList
			boolean manholeExistsInAuxList = auxList.stream()
					.anyMatch(auxData -> auxData[3].toString().equals(manholeName));

			if (!manholeExistsInAuxList && distance <= maxDistance && distance < minDistance) {
				minDistance = distance;
				nearestManhole = new HashMap<>();
				nearestManhole.put("ManholeId", manholeId);
				nearestManhole.put("ManholeName", manholeName);
				nearestManhole.put("ManholeLng", manholeLng);
				nearestManhole.put("ManholeLat", manholeLat);
				nearestManhole.put("Distance", distance);
			}
		}

		return nearestManhole;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> findNearestHandhole(double auxLng, double auxLat, double maxDistance,
			List<Object[]> auxList) {
		Map<String, Object> nearestHandhole = null;
		double minDistance = maxDistance + 1;

		List<Object[]> handholeList = session
				.createNativeQuery("SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE FROM HANDHOLE")
				.getResultList();

		for (Object[] handholeData : handholeList) {
			String handholeId = (String) handholeData[0];
			String handholeName = (String) handholeData[1];
			double handholeLng = Double.parseDouble(handholeData[2].toString());
			double handholeLat = Double.parseDouble(handholeData[3].toString());

			double distance = calculateDistance(auxLat, auxLng, handholeLat, handholeLng); // Swap lat and lng in
																							// calculateDistance
																							// function

			// Check if handholeId exists in auxList
			boolean handholeExistsInAuxList = auxList.stream()
					.anyMatch(auxData -> auxData[3].toString().equals(handholeName));

			if (!handholeExistsInAuxList && distance <= maxDistance && distance < minDistance) {
				minDistance = distance;
				nearestHandhole = new HashMap<>();
				nearestHandhole.put("HandholeId", handholeId);
				nearestHandhole.put("HandholeName", handholeName);
				nearestHandhole.put("HandholeLng", handholeLng);
				nearestHandhole.put("HandholeLat", handholeLat);
				nearestHandhole.put("Distance", distance);
			}
		}

		return nearestHandhole;
	}

	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		// Convert latitude and longitude from degrees to radians
		double radLat1 = Math.toRadians(lat1);
		double radLon1 = Math.toRadians(lon1);
		double radLat2 = Math.toRadians(lat2);
		double radLon2 = Math.toRadians(lon2);

		// Radius of the Earth in meters
		final double R = 6371000.0;

		// Haversine formula
		double dLat = radLat2 - radLat1;
		double dLon = radLon2 - radLon1;
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(radLat1) * Math.cos(radLat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c;

		return distance; // Return distance in meters
	}
	
	// Function to calculate latitudes on the circle's border
    private static double[] calculateBorderCircleLatitudes(double centerLat, double centerLon, double distanceRange) {

    	 // Calculate circle radius 
        //double circleRadius = distanceRange * 1.609344 * 1.609344;
        double circleRadius = distanceRange;
        
        final double EARTH_RADIUS = 6371.0;

        // Convert latitude to radians
        double centerLatRad = Math.toRadians(centerLat);

        // Calculate delta latitude (angular distance)
        double deltaLat = circleRadius / EARTH_RADIUS;

        // Calculate latitudes of points on the circle's border
        double northLatitude = Math.toDegrees(centerLatRad + deltaLat);
        double southLatitude = Math.toDegrees(centerLatRad - deltaLat);
       
        // Return lat in descending order 
        return new double[]{Math.min(northLatitude, southLatitude), Math.max(northLatitude, southLatitude)};

    }
   
    private static double[] calculateBorderCircleLongitudes(double centerLat, double centerLon, double distanceRange) {
        // Calculate circle radius 
        //double circleRadius = distanceRange * 1.609344 * 1.609344;
        double circleRadius = distanceRange;

        final double EARTH_RADIUS = 6371.0;

        // Convert latitude to radians
        double centerLatRad = Math.toRadians(centerLat);

        // Calculate angular distance (in radians) covered by the circle's border
        double angularDistance = circleRadius / EARTH_RADIUS;

        // Calculate longitude difference based on the latitude
        double deltaLon = Math.asin(Math.sin(angularDistance) / Math.cos(centerLatRad));

        // Convert longitude to radians
        double centerLonRad = Math.toRadians(centerLon);

        // Calculate longitudes of points on the circle's border
        double westLongitude = Math.toDegrees(centerLonRad - deltaLon);
        double eastLongitude = Math.toDegrees(centerLonRad + deltaLon);

     // Return longitudes in descending order
      return new double[]{Math.min(westLongitude, eastLongitude), Math.max(westLongitude, eastLongitude)};
    }
	public List<Object[]> findLinearDistance(List<String> listIDs,List<?> ListOfObjects, double closestLatPoint, double closestLongPoint,
			double closestDisRange, String Target, String noOfPoints) throws JsonProcessingException {
		List<Object[]> nearstPointsArray = new ArrayList<Object[]>();
		List<Object[]> nearstPointsArraySorted = new ArrayList<Object[]>();
		List<Object[]> result = new ArrayList<Object[]>();
		double pointDist = 0.0;
		String ID="";
		
		for (int i = 0; i < ListOfObjects.size(); i++) {

			Object[] objectArray = (Object[]) ListOfObjects.get(i);

			if (Target == "Manhole" || Target == "Handhole") {
				pointDist = haversine(closestLatPoint, closestLongPoint, Double.valueOf((String) objectArray[3]),
						Double.valueOf((String) objectArray[2]));
				System.out.println("pointDist is " +pointDist);
			} 
			else {
				pointDist = haversine(closestLatPoint, closestLongPoint, Double.valueOf((String) objectArray[2]),
						Double.valueOf((String) objectArray[1]));
				System.out.println("pointDist is " +pointDist);
			}
			ID = (String) objectArray[0];
						
		if (pointDist < closestDisRange || listIDs.contains(ID) ==true ) {
			objectArray = append(objectArray, (Object) pointDist);
			nearstPointsArray.add(objectArray);	
		}
			
		}// end of loop 
		
		
		if (nearstPointsArray.size() > 0) {
			
			double[] listofDistances = new double[nearstPointsArray.size()];
			for (int j = 0; j < nearstPointsArray.size(); j++) {

				if (Target == "DistribBoard") {
					listofDistances[j] = Double.valueOf(String.valueOf(((Object[]) nearstPointsArray.get(j))[9]));
				} 
				else {
					listofDistances[j] = Double.valueOf(String.valueOf(((Object[]) nearstPointsArray.get(j))[7]));
				}
			}
			Arrays.sort(listofDistances, 0, listofDistances.length);

			for (int j = 0; j < listofDistances.length; j++) {
				nearstPointsArraySorted.add(findUsingEnhancedForLoop(listofDistances[j], nearstPointsArray, Target));
				nearstPointsArray.remove(findUsingEnhancedForLoop(listofDistances[j], nearstPointsArray, Target));
			}
		}
		if (nearstPointsArraySorted.size() > 0) {
			if (StringUtils.equalsIgnoreCase(noOfPoints, "") || StringUtils.equalsIgnoreCase(noOfPoints, null)) {
				result = nearstPointsArraySorted;

			} else {
				result = nearstPointsArraySorted.subList(0, Integer.valueOf(noOfPoints));
			}

		}
		return result;

	}

	 public void findIDsSrcDestStrtEndCoord(List<Object[]> listOfObjects, String target, List<String> mhIDs, List<String> hhIDs, List<String> dbIDs, String newStartLngPt, String newStartLatPt, String newEndLngPt, String newEndLatPt) {
		    double sourceLng, sourceLat, destLng, destLat,strtLong=0,strtLat=0,endLong=0,endLat=0;

		    boolean checkStartLng = newStartLngPt != "";
		    boolean checkStartLat = newStartLatPt != "";
		    boolean checkEndLng = newEndLngPt != "";
		    boolean checkEndLat = newEndLatPt != "";
		    
		    if(checkStartLng) {
		    	strtLong = Double.parseDouble(newStartLngPt);
		    }
		    if(checkEndLng) {
		    	endLong = Double.parseDouble(newEndLngPt);
		    }
		    if(checkStartLat) {
		    	strtLat = Double.parseDouble(newStartLatPt);
		    }
		    if(checkEndLat) {
		    	endLat = Double.parseDouble(newEndLatPt);
		    }
		    
		    if(checkStartLng && checkEndLng) {		    
			    if (Double.parseDouble(newStartLngPt) < Double.parseDouble(newEndLngPt)) {
			    	strtLong = Double.parseDouble(newStartLngPt);
			    	endLong = Double.parseDouble(newEndLngPt);
				} 
				else {
			    	strtLong = Double.parseDouble(newEndLngPt);
			    	endLong = Double.parseDouble(newStartLngPt);
				}
		    }
		    
		    if(checkStartLat && checkEndLat) {		    
			    if (Double.parseDouble(newStartLatPt) < Double.parseDouble(newEndLatPt)) {
			    	strtLat = Double.parseDouble(newStartLatPt);
			    	endLat = Double.parseDouble(newEndLatPt);
				} 
				else {
			    	strtLat = Double.parseDouble(newEndLatPt);
			    	endLat = Double.parseDouble(newStartLatPt);
				}
		    }
		    
		    
		    
		    for (Object[] row : listOfObjects) {
		        if (target.equals("Cables")) {
		            sourceLng = Double.parseDouble(row[0].toString());
		            sourceLat = Double.parseDouble(row[1].toString());
		            destLng = Double.parseDouble(row[2].toString());
		            destLat = Double.parseDouble(row[3].toString());
		        } else {
		            sourceLng = Double.parseDouble(row[1].toString());
		            sourceLat = Double.parseDouble(row[2].toString());
		            destLng = Double.parseDouble(row[3].toString());
		            destLat = Double.parseDouble(row[4].toString());
		        }
		        
		        String sourceId = row[6].toString();
		        String destinationId = row[9].toString();

		        boolean sourceCheck = (!checkStartLng || (checkStartLng && sourceLng > strtLong)) &&
		                                  (!checkStartLat || (checkStartLat && sourceLat > strtLat)) &&
		                                  (!checkEndLng || (checkEndLng && sourceLng < endLong)) &&
		                                  (!checkEndLat || (checkEndLat && sourceLat < endLat));
		        
		        boolean destinationCheck = (!checkStartLng || (checkStartLng && destLng > strtLong)) &&
                     (!checkStartLat || (checkStartLat && destLat > strtLat)) &&
                     (!checkEndLng || (checkEndLng && destLng < endLong)) &&
                     (!checkEndLat || (checkEndLat && destLat < endLat));

		        
		        if (sourceCheck) {
		            if (sourceId.contains("MH") && !mhIDs.contains(sourceId)) {
		                mhIDs.add(sourceId);
		            } else if (sourceId.contains("HH") && !hhIDs.contains(sourceId)) {
		                hhIDs.add(sourceId);
		            } else if (sourceId.contains("DB") && !dbIDs.contains(sourceId)) {
		                dbIDs.add(sourceId);
		            }
		        }
		        
		        if (destinationCheck) {
		            if (destinationId.contains("MH") && !mhIDs.contains(destinationId)) {
		                mhIDs.add(destinationId);
		            } else if (destinationId.contains("HH") && !hhIDs.contains(destinationId)) {
		                hhIDs.add(destinationId);
		            } else if (destinationId.contains("DB") && !dbIDs.contains(destinationId)) {
		                dbIDs.add(destinationId);
		            }
		        }
		        
		        
		        
		    }
		}
	 
    public void findIDsSrcDest(List<Object[]> listOfObjects, String target, List<String> mhIDs, List<String> hhIDs, List<String> dbIDs, double newStartLngPt, double newStartLatPt, double newEndLngPt, double newEndLatPt) {
	 	double sourceLng,sourceLat,destLng,destLat;

	 for (Object[] row : listOfObjects) {
	    	
	    	if(target == "Cables") {
	    		 sourceLng = Double.parseDouble(row[0].toString());
		    	 sourceLat = Double.parseDouble(row[1].toString());
		    	 destLng = Double.parseDouble(row[2].toString());
		    	 destLat = Double.parseDouble(row[3].toString());

	    	}
	    	else {
	    		 sourceLng = Double.parseDouble(row[1].toString());
		    	 sourceLat = Double.parseDouble(row[2].toString());
		    	 destLng = Double.parseDouble(row[3].toString());
		    	 destLat = Double.parseDouble(row[4].toString());
	    	}
	    	
	        String sourceId = row[6].toString();
	        String destinationId = row[9].toString();

	        if (sourceLng > newStartLngPt && sourceLat > newStartLatPt && sourceLng < newEndLngPt && sourceLat < newEndLatPt) {
	       
	        		if (sourceId.contains("MH") && !mhIDs.contains(sourceId)) {
	        			mhIDs.add(sourceId);
		            } 
		            else if (sourceId.contains("HH") && !hhIDs.contains(sourceId)) {
		            	hhIDs.add(sourceId);
		            }
		            else if (sourceId.contains("DB") && !dbIDs.contains(sourceId)) {
		            	dbIDs.add(sourceId);
		            }
	        }

	        if (destLng > newStartLngPt && destLat > newStartLatPt && destLng < newEndLngPt && destLat < newEndLatPt) {
	        	 if (destinationId.contains("MH") && !mhIDs.contains(destinationId)) {
	        		 mhIDs.add(destinationId);
		            } 
		            else if (destinationId.contains("HH") && !hhIDs.contains(destinationId)) {
		            	hhIDs.add(destinationId);
		            }
		            else if (destinationId.contains("DB") && !dbIDs.contains(destinationId)) {
		            	dbIDs.add(destinationId);
		            }
	        
	        
	        }
	        
	        
	    }
	}
    
    public void findIDsForAuxStrtEndCoord(List<Object[]> listOfObjects, String target, List<String> mhIDs, List<String> hhIDs, List<String> dbIDs, String newStartLngPt, String newStartLatPt, String newEndLngPt, String newEndLatPt) {
	    double strtLong=0,strtLat=0,endLong=0,endLat=0;
		 double Lng,Lat;  

	    boolean checkStartLng = newStartLngPt != "";
	    boolean checkStartLat = newStartLatPt != "";
	    boolean checkEndLng = newEndLngPt != "";
	    boolean checkEndLat = newEndLatPt != "";
	    
	    if(checkStartLng) {
	    	strtLong = Double.parseDouble(newStartLngPt);
	    }
	    if(checkEndLng) {
	    	endLong = Double.parseDouble(newEndLngPt);
	    }
	    if(checkStartLat) {
	    	strtLat = Double.parseDouble(newStartLatPt);
	    }
	    if(checkEndLat) {
	    	endLat = Double.parseDouble(newEndLatPt);
	    }
	    
	    if(checkStartLng && checkEndLng) {		    
		    if (Double.parseDouble(newStartLngPt) < Double.parseDouble(newEndLngPt)) {
		    	strtLong = Double.parseDouble(newStartLngPt);
		    	endLong = Double.parseDouble(newEndLngPt);
			} 
			else {
		    	strtLong = Double.parseDouble(newEndLngPt);
		    	endLong = Double.parseDouble(newStartLngPt);
			}
	    }
	    
	    if(checkStartLat && checkEndLat) {		    
		    if (Double.parseDouble(newStartLatPt) < Double.parseDouble(newEndLatPt)) {
		    	strtLat = Double.parseDouble(newStartLatPt);
		    	endLat = Double.parseDouble(newEndLatPt);
			} 
			else {
		    	strtLat = Double.parseDouble(newEndLatPt);
		    	endLat = Double.parseDouble(newStartLatPt);
			}
	    }
	    
	    
	    
	    for (Object[] row : listOfObjects) {
	    	if(target=="Cables") {
	    		 Lng = Double.parseDouble(row[0].toString());
		    	 Lat = Double.parseDouble(row[1].toString());
		    
	    	}
	    	else {
	    		 Lng = Double.parseDouble(row[1].toString());
		    	 Lat = Double.parseDouble(row[2].toString());
	    	}
	    	
	        String auxId = row[4].toString();
	        
	        boolean auxiliaryCheck = (!checkStartLng || (checkStartLng && Lng > strtLong)) &&
	                                  (!checkStartLat || (checkStartLat && Lat > strtLat)) &&
	                                  (!checkEndLng || (checkEndLng && Lng < endLong)) &&
	                                  (!checkEndLat || (checkEndLat && Lat < endLat));
	        
	       
	        if (auxiliaryCheck) {
	            if (auxId.contains("MH") && !mhIDs.contains(auxId)) {
	                mhIDs.add(auxId);
	            } else if (auxId.contains("HH") && !hhIDs.contains(auxId)) {
	                hhIDs.add(auxId);
	            } else if (auxId.contains("DB") && !dbIDs.contains(auxId)) {
	                dbIDs.add(auxId);
	            }
	        }		        
	    }
	}
    
 public void findIDsForAux(List<Object[]> listOfObjects, String target, List<String> mhIDs, List<String> hhIDs, List<String> dbIDs, double newStartLngPt, double newStartLatPt, double newEndLngPt, double newEndLatPt) {
	 double Lng,Lat;  
	 for (Object[] row : listOfObjects) {
	    	
	    	if(target=="Cables") {
	    		 Lng = Double.parseDouble(row[0].toString());
		    	 Lat = Double.parseDouble(row[1].toString());
		    
	    	}
	    	else {
	    		 Lng = Double.parseDouble(row[1].toString());
		    	 Lat = Double.parseDouble(row[2].toString());
	    	}
	    	
	        String auxId = row[4].toString();
	        
	        if (Lng > newStartLngPt && Lat > newStartLatPt && Lng < newEndLngPt && Lat < newEndLatPt) {
	       
	        		if (auxId.contains("MH") && !mhIDs.contains(auxId)) {
	        			mhIDs.add(auxId);
		            } 
		            else if (auxId.contains("HH") && !hhIDs.contains(auxId)) {
		            	hhIDs.add(auxId);
		            }
		            else if (auxId.contains("DB") && !dbIDs.contains(auxId)) {
		            	dbIDs.add(auxId);
		            }
	        }		       		        
	    }
	}

 
 public List<Object[]> filterDataList(List<Object[]> listOfObjects, List<String> arrayIDs,String target) {
	 
	 List<Object[]> filteredList = new ArrayList<>();  
	 String Id ="";
	 for (Object[] row : listOfObjects) {
		 	if(target=="Cables") {
		         Id = row[4].toString();

		 	}
		 	else {
		         Id = row[0].toString();
		 	}
	       if (!arrayIDs.contains(Id)) {
	    	   filteredList.add(row);
		   } 			           
	  }
	 	return filteredList; 
	}
 
 public List<Object[]> filterTempList(List<Object[]> listOfObjects, String[] IDsArray) {
	
	 List<Object[]> filteredList = new ArrayList<>();  
	 List<String> idsList = Arrays.asList(IDsArray); // Convert array to List
	 List<String> tempList = new ArrayList<>();  // to check if the point is repeated in listOfObjects
	   
	 for (Object[] row : listOfObjects) {
	    	
	        String Id = row[0].toString();
	      
	       if (!idsList.contains(Id) && !tempList.contains(Id)) {
	    	   filteredList.add(row);
	    	   tempList.add(Id);
		   } 			           
	     }
	 	return filteredList;
	}



	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fiberPathSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fiberPathSave(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		System.out.println("In fiberPathSave New Method");
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);

			String fiberpathID = request.getParameter("fiberpathID");
			FiberCable fibercable;
			FiberAuxPoints fiberAuxPoints;
			FiberTubes fiberTubes;
			TubeAuxPoints fiberAuxtubes;
			FiberStrands fiberStrand;
			StrandAuxPoints fiberAuxstrands;
			String ipAddress = getIpAddress(request);
			String updateModfUser=request.getParameter("updateModfUser");
			PhysicalLayerActivity PhyAct= new PhysicalLayerActivity();

			try {

				String PhyActID=
						 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						
						PhyAct.setPhyActID(PhyActID);
						PhyAct.setScreenName("Fiber Cable");
						PhyAct.setUsername(updateModfUser);
						PhyAct.setUserIP(ipAddress);
						PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
							fibercable = new FiberCable();
				if (StringUtils.equalsIgnoreCase(fiberpathID, "")) {
					synchronized (this) {
						// fiberpathID = "FIBER" + year + "_" + appConfig.getSeqNbr(51,session);
						fiberpathID = "FIBER" + year + "_" + Integer.parseInt(session
								.createNativeQuery("SELECT FIBER_CABLE FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET FIBER_CABLE = FIBER_CABLE + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						session.flush();
						session.clear();
						PhyAct.setActivityDescription("Add New Element");
					}
				}
				else {
					PhyAct.setActivityDescription("Edit Existing Element");
				}

				String ItemCodeId = request.getParameter("ItemCodeId");
				Float FiberLength = (float) 0;
				Float totalDrivingDistance = (float) 0;
				float totalGeoDistance = 0;
				int NumTubes = 0;
				int NumStrands = 0;
				String selectedFiberMode = "";
				float lastAuxToDestDistance = 0;
				float lastAuxToDestDrivDistance = 0;
				int auxArraySize = 0;

				if (request.getParameter("FiberLength") != "") {
					FiberLength = Float.parseFloat(request.getParameter("FiberLength"));
				}

				if (request.getParameter("totalDrivingDistance") != "") {
					totalDrivingDistance = Float.parseFloat(request.getParameter("totalDrivingDistance"));
				}

				if (request.getParameter("totalGeoDistance") != "") {
					totalGeoDistance = Float.parseFloat(request.getParameter("totalGeoDistance"));
				}

				if (request.getParameter("NumStrands") != "") {
					NumStrands = Integer.parseInt(request.getParameter("NumStrands"));
				}

				if (request.getParameter("NumTubes") != "") {
					NumTubes = Integer.parseInt(request.getParameter("NumTubes"));
				}

				if (request.getParameter("selectedFiberMode") != "") {
					selectedFiberMode = request.getParameter("selectedFiberMode");
				}

				if (request.getParameter("lastAuxToDestDistance") != "") {
					lastAuxToDestDistance = Float.parseFloat(request.getParameter("lastAuxToDestDistance"));
				}
				if (request.getParameter("lastAuxToDestDrivDistance") != "") {
					lastAuxToDestDrivDistance = Float.parseFloat(request.getParameter("lastAuxToDestDrivDistance"));
				}

				String fiberName = request.getParameter("fiberName");
				String sourceLng = request.getParameter("sourceLng");
				String sourceLat = request.getParameter("sourceLat");
				String destinationLng = request.getParameter("destinationLng");
				String destinationLat = request.getParameter("destinationLat");
				String srcCity = request.getParameter("sourceCity");
				String dstCity = request.getParameter("destinationCity");
				String source = request.getParameter("Source");
				String destination = request.getParameter("Destination");
				String ProjectId = request.getParameter("ProjectId");
				String Condiut_Id = request.getParameter("Condiut_Id");
				String Condiut_Name = request.getParameter("Condiut_Name");
				String fibertype = request.getParameter("fibertype");
				String fiberdeployment = request.getParameter("fiberdeployment");
				String fibernetlevel = request.getParameter("fibernetlevel");
				String fiberowner = request.getParameter("fiberowner");
				String fiberCableCreatedByUser = request.getParameter("fiberCableCreatedByUser");
				String fiberCableModifiedByUser = request.getParameter("fiberCableModifiedByUser");
				String drawingtype = request.getParameter("drawingType");
				String fiberCableSize = request.getParameter("fiberCableSize");
				String fiberEngineerName = request.getParameter("fiberEngineerName");
				String fiberInstaller = request.getParameter("fiberInstaller");
				// related cable
				String relatedStrandNb = request.getParameter("relatedStrandNb");
				String relatedStrandColor = request.getParameter("relatedStrandColor");
				String relatedStrandID = request.getParameter("relatedStrandID");
				String relatedStrandName = request.getParameter("relatedStrandName");
				String relatedTubeNb = request.getParameter("relatedTubeNb");
				String relatedTubeColor = request.getParameter("relatedTubeColor");
				String relatedTubeID = request.getParameter("relatedTubeID");
				String relatedTubeName = request.getParameter("relatedTubeName");
				String relatedCableID = request.getParameter("relatedCableID");
				String relatedCableName = request.getParameter("relatedCableName");
				String otherLastMileID = request.getParameter("otherLastMileID");
				String otherLastMileName = request.getParameter("otherLastMileName");
				String otherSideLocationID = request.getParameter("otherSideLocationID");
				String otherSideLocationName = request.getParameter("otherSideLocationName");
				String otherSideLocationCity = request.getParameter("otherSideLocationCity");
				String otherSideLocationType = request.getParameter("otherSideLocationType");

				//
				Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

				String cableCreatedDate = request.getParameter("cableCreatedDate");
				Timestamp cableCreationDate;
				if (StringUtils.equalsIgnoreCase(cableCreatedDate, "")) {
					cableCreationDate = lastModifiedDate;

				} else {
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					cableCreationDate = new Timestamp(
							formatter.parse(request.getParameter("cableCreatedDate")).getTime());
				}

				fibercable.setFibercableID(fiberpathID);
				if (source.contains("WARE") == true) {
					fibercable.setSourceWareID(source.split(":")[0]);
					fibercable.setSourceID(source.split(":")[2]);
					fibercable.setSourceName(source.split(":")[1]);
				} else if (source.contains("CUST") == true) {
					fibercable.setSourceWareID("null");
					fibercable.setSourceID(source.split(":")[0]);
					fibercable.setSourceName(source.split(":")[1]);
				}

				else if (source.contains("MH") == true || source.contains("HH") == true
						|| source.contains("DB") == true) {
					fibercable.setSourceWareID("null");
					fibercable.setSourceID(source.split(":")[0]);
					fibercable.setSourceName(source.split(":")[1]);

				} else {
					fibercable.setSourceWareID("null");
					fibercable.setSourceID("null");
					fibercable.setSourceName(source);
				}

				if (destination.contains("WARE") == true) {
					fibercable.setDestinationWareID(destination.split(":")[0]);
					fibercable.setDestinationID(destination.split(":")[2]);
					fibercable.setDestinationName(destination.split(":")[1]);
				} else if (destination.contains("CUST") == true) {
					fibercable.setDestinationWareID("null");
					fibercable.setDestinationID(destination.split(":")[0]);
					fibercable.setDestinationName(destination.split(":")[1]);
				}

				else if (destination.contains("MH") == true || destination.contains("HH") == true
						|| destination.contains("DB") == true) {
					fibercable.setDestinationWareID("null");
					fibercable.setDestinationID(destination.split(":")[0]);
					fibercable.setDestinationName(destination.split(":")[1]);
				} else {
					fibercable.setDestinationWareID("null");
					fibercable.setDestinationID("null");
					fibercable.setDestinationName(destination);
				}

				fibercable.setItemcode(ItemCodeId);
				fibercable.setNbofStrands(NumStrands);
				fibercable.setNbofTubes(NumTubes);
				fibercable.setFiberlength(FiberLength);
				fibercable.setConduitID(Condiut_Id);
				fibercable.setConduitName(Condiut_Name);
				fibercable.setSrcLNG(sourceLng);
				fibercable.setSrcLAT(sourceLat);
				fibercable.setDestLNG(destinationLng);
				fibercable.setDestLAT(destinationLat);
				fibercable.setCableMode(selectedFiberMode);
				fibercable.setFibercableName(fiberName);
				fibercable.setSrcCity(srcCity);
				fibercable.setDestCity(dstCity);
				fibercable.setProjectID(ProjectId);
				fibercable.setFiberType(fibertype);
				fibercable.setFiberDeployment(fiberdeployment);
				fibercable.setFiberNetLevel(fibernetlevel);
				fibercable.setFiberOwner(fiberowner);
				fibercable.setCreationDate(cableCreationDate);
				fibercable.setLastModifieddate(lastModifiedDate);
				fibercable.setCreatedBy(fiberCableCreatedByUser);
				fibercable.setLastmodifiedBy(fiberCableModifiedByUser);
				fibercable.setTotaldriving(totalDrivingDistance);
				fibercable.setDrawingtype(drawingtype);
				fibercable.setTotalGeoDist(totalGeoDistance);
				fibercable.setFiberCableSize(fiberCableSize);
				fibercable.setFiberEngineerName(fiberEngineerName);
				fibercable.setFiberInstaller(fiberInstaller);

				fibercable.setLastAuxToDestDistance(lastAuxToDestDistance);
				fibercable.setLastAuxToDestDrivDistance(lastAuxToDestDrivDistance);

				fibercable.setRelatedstrandnumber(relatedStrandNb);
				fibercable.setRelatedstrandcolor(relatedStrandColor);
				fibercable.setRelatedstrandID(relatedStrandID);
				fibercable.setRelatedstrandName(relatedStrandName);
				fibercable.setRelatedtubenumber(relatedTubeNb);
				fibercable.setRelatedtubecolor(relatedTubeColor);
				fibercable.setRelatedtubeID(relatedTubeID);
				fibercable.setRelatedtubeName(relatedTubeName);
				fibercable.setRelatedcableID(relatedCableID);
				fibercable.setRelatedcableName(relatedCableName);
				fibercable.setOthersideLastmileID(otherLastMileID);
				fibercable.setOthersideLastmileName(otherLastMileName);
				fibercable.setOthersideLocationID(otherSideLocationID);
				fibercable.setOthersideLocationName(otherSideLocationName);
				fibercable.setOthersideLocationCity(otherSideLocationCity);
				fibercable.setOthersideLocationType(otherSideLocationType);

				/*
				 * query = session.createNativeQuery(
				 * "INSERT INTO FIBER_CABLES (FIBER_CABLE_ID,SOURCE,DESTINATION,ITEM_CODE,NUMBER_OF_STRANDS,NUMBER_OF_TUBES,LENGTH, "
				 * +
				 * "CONDUIT_ID,CONDUIT_NAME,SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,CABLE_MODE,FIBER_CABLE_NAME,SOURCE_CITY,PROJECT_ID,DESTINATION_CITY, "
				 * +
				 * "FIBER_TYPE, FIBER_DEPLOYMENT, FIBER_NETWORK_LEVEL, FIBER_OWNER, CREATION_DATE, LAST_MODIFIED_DATE, CREATED_BY, LAST_MODIFIED_BY ) VALUES ('"
				 * + fiberpathID + "','" + source + "','" + destination + "','" + ItemCodeId +
				 * "','" + NumStrands + "','" + NumTubes + "'," + "'" + FiberLength + "','" +
				 * Condiut_Id + "','" + Condiut_Name + "','" + sourceLng + "','" + sourceLat +
				 * "','" + destinationLng + "','" + destinationLat + "'," + "'" +
				 * selectedFiberMode + "','" + fiberName + "','" + srcCity + "','" + ProjectId +
				 * "','" + dstCity + "'," + "'" + fibertype + "','" + fiberdeployment + "','" +
				 * fibernetlevel + "','" + fiberowner + "', TIMESTAMP '" + CreationLastModDate +
				 * "' , TIMESTAMP '" + CreationLastModDate + "' ,'" + fiberCableCreatedByUser +
				 * "','" + fiberCableModifiedByUser + "' )"); query.executeUpdate();
				 */
				session.saveOrUpdate(fibercable);
				session.flush();
				session.clear();
				PhyAct.setElementID(fiberpathID);
				session.saveOrUpdate(PhyAct);
	
				Query updateMappingJctSideAQuery = session
						.createNativeQuery("UPDATE JUNCTION_MAPPING SET FIBER_NAME_SIDE_A = '" + fiberName
								+ "' WHERE FIBER_ID_SIDE_A = '" + fiberpathID + "' ");
				updateMappingJctSideAQuery.executeUpdate();

				Query updateMappingJctSideBQuery = session
						.createNativeQuery("UPDATE JUNCTION_MAPPING SET FIBER_NAME_SIDE_B = '" + fiberName
								+ "' WHERE FIBER_ID_SIDE_B = '" + fiberpathID + "' ");
				updateMappingJctSideBQuery.executeUpdate();

				session.flush();
				session.clear();

				String fiberAuxFlag = request.getParameter("fiberAuxFlag");
				if (StringUtils.equalsIgnoreCase(fiberAuxFlag, "opened")
						|| StringUtils.equalsIgnoreCase(fiberAuxFlag, "new cable")) {

					System.out.println("Inside " + fiberAuxFlag);
					query = session.createNativeQuery(
							"delete from FIBER_AUXILIARY_POINTS where FIBER_CABLE_ID='" + fiberpathID + "'");
					query.executeUpdate();

					if (itemParameters.getDictParameter().size() > 0) {
						if (itemParameters.getDictParameter().size() > 1500) {
							auxArraySize = 1500;
						} else {
							auxArraySize = itemParameters.getDictParameter().size();
						}

						for (int i = 0; i < auxArraySize; i++) {
							String aux_ID;
							synchronized (this) {
								// String aux_ID = "AUXILIARY_PT_" + year + "_" +
								// appConfig.getSeqNbr(53,session);
								aux_ID = "AUXILIARY_PT_" + year + "_"
										+ Integer.parseInt(
												session.createNativeQuery("SELECT FIBER_CABLE_AUX FROM SEQ_TABLE")
														.uniqueResult().toString());
								query = session.createNativeQuery(
										"UPDATE SEQ_TABLE SET FIBER_CABLE_AUX = FIBER_CABLE_AUX + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
							}
							fiberAuxPoints = new FiberAuxPoints();

							Double driving_distance, geo_distance;

							driving_distance = itemParameters.getDictParameter().get(i).get("driving_distance") == ""
									? 0
									: itemParameters.getDictParameter().get(i).get("driving_distance") == null ? 0
											: StringUtils.equalsIgnoreCase(
													itemParameters.getDictParameter().get(i).get("driving_distance"),
													"null") ? 0
															: Double.parseDouble(itemParameters.getDictParameter()
																	.get(i).get("driving_distance"));
							geo_distance = itemParameters.getDictParameter().get(i).get("geo_distance") == "" ? 0
									: itemParameters.getDictParameter().get(i).get("geo_distance") == null ? 0
											: StringUtils.equalsIgnoreCase(
													itemParameters.getDictParameter().get(i).get("geo_distance"),
													"null") ? 0
															: Double.parseDouble(itemParameters.getDictParameter()
																	.get(i).get("geo_distance"));

							fiberAuxPoints.setAuxID(aux_ID);
							fiberAuxPoints.setFibercableID(fiberpathID);
							fiberAuxPoints.setLong(
									Double.parseDouble(itemParameters.getDictParameter().get(i).get("aux_Longitude")));
							fiberAuxPoints.setLat(
									Double.parseDouble(itemParameters.getDictParameter().get(i).get("aux_Latitude")));
							fiberAuxPoints.setDistancefromsource(Double
									.parseDouble(itemParameters.getDictParameter().get(i).get("distance_From_Source")));
							// fiberAuxPoints.setAuxName(
							// itemParameters.getDictParameter().get(i).get("aux_Name"));

							if (itemParameters.getDictParameter().get(i).get("aux_Name").contains("WARE") == true) {
								fiberAuxPoints.setWareID(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[0]);
								fiberAuxPoints.setAuxPointID(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[2]);
								fiberAuxPoints.setAuxPointName(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[1]);
							} else if (itemParameters.getDictParameter().get(i).get("aux_Name")
									.contains("Auxiliary_Point") == true) {
								fiberAuxPoints.setWareID("null");
								fiberAuxPoints.setAuxPointID("null");
								if (itemParameters.getDictParameter().get(i).get("aux_Name")
										.contains("AUXILIARY_PT") == true) {
									fiberAuxPoints.setAuxPointName(
											itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[1]);
								} else {
									fiberAuxPoints
											.setAuxPointName(itemParameters.getDictParameter().get(i).get("aux_Name"));
								}
							} else if (itemParameters.getDictParameter().get(i).get("aux_Name").contains("MH") == true
									|| itemParameters.getDictParameter().get(i).get("aux_Name").contains("HH") == true
									|| itemParameters.getDictParameter().get(i).get("aux_Name")
											.contains("DB") == true) {
								fiberAuxPoints.setWareID("null");
								fiberAuxPoints.setAuxPointID(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[0]);
								fiberAuxPoints.setAuxPointName(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[1]);

							} else {
								fiberAuxPoints.setWareID("null");
								fiberAuxPoints.setAuxPointID("null");
								String AuxName = (String) itemParameters.getDictParameter().get(i).get("aux_Name");
								fiberAuxPoints.setAuxPointName(AuxName);

							}
							fiberAuxPoints.setCreationDate(cableCreationDate);
							fiberAuxPoints.setLastModifieddate(lastModifiedDate);
							fiberAuxPoints
									.setSeqSorting(itemParameters.getDictParameter().get(i).get("aux_seqSorting"));
							fiberAuxPoints.setDrivingDist(driving_distance);
							fiberAuxPoints.setGeoDist(geo_distance);

							session.saveOrUpdate(fiberAuxPoints);
							session.flush();
							session.clear();

						}
					}
				}

				// added junction
				query = session.createNativeQuery(
						"delete from ACCESS_CABLES_JUNCTIONS where FIBER_CABLE_ID = '" + fiberpathID + "'");
				query.executeUpdate();

				System.out.println("itemParam : " + itemParameters.getDictParameterJunct());
				AccessCableJunction accessCableJunction;
				String access_Junc_ID = "";
				if (itemParameters.getDictParameterJunct().size() > 0) {

					for (int i = 0; i < itemParameters.getDictParameterJunct().size(); i++) {
						accessCableJunction = new AccessCableJunction();
						if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameterJunct().get(i).get("junctId"),
								"")
								|| StringUtils.equalsIgnoreCase(
										itemParameters.getDictParameterJunct().get(i).get("junctId"), null)) {
							synchronized (this) {
								access_Junc_ID = "ACCESS_JUNCTION_PORT_" + year + "_"
										+ Integer.parseInt(
												session.createNativeQuery("SELECT ACCESS_JUNCTIONS FROM SEQ_TABLE")
														.uniqueResult().toString());
								query = session.createNativeQuery(
										"UPDATE SEQ_TABLE SET ACCESS_JUNCTIONS = ACCESS_JUNCTIONS + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
								System.out.println("the jun port is " + access_Junc_ID);
							}
						} else {
							access_Junc_ID = itemParameters.getDictParameterJunct().get(i).get("junctId");
						}
						// System.out.println(itemParameters.getDictParameterJunct().get(i).get("rowColIndex"));

						accessCableJunction.setJunctionRowID(access_Junc_ID);
						accessCableJunction.setFibercableID(fiberpathID);

						accessCableJunction.setJunctionID(itemParameters.getDictParameterJunct().get(i).get("junID"));
						accessCableJunction
								.setJunctionName(itemParameters.getDictParameterJunct().get(i).get("junName"));

						session.saveOrUpdate(accessCableJunction);
						session.flush();
						session.clear();
					}

				}

				query = session.createNativeQuery("delete from FIBER_TUBES where FIBER_CABLE_ID='" + fiberpathID + "'");
				query.executeUpdate();
				query = session
						.createNativeQuery("delete from FIBER_STRANDS where FIBER_CABLE_ID='" + fiberpathID + "'");
				query.executeUpdate();
				query = session.createNativeQuery(
						"delete from TUBE_AUXILIARY_POINTS where FIBER_CABLE_ID='" + fiberpathID + "'");
				query.executeUpdate();
				query = session.createNativeQuery(
						"delete from STRAND_AUXILIARY_POINTS where FIBER_CABLE_ID='" + fiberpathID + "'");
				query.executeUpdate();

				session.flush();
				session.clear();

				String strandSource = "";
				String strandDestination = "";
				String strand_name = "";
				String strandowner = "";
				String stranddeployment = "";
				String strandtype = "";

				String tubeId = "";

				for (int i = 0; i < itemParameters.getDictParameterStrands().size(); i++) {

					if (itemParameters.getDictParameterStrands().get(i).get("strandSource") != "") {
						strandSource = itemParameters.getDictParameterStrands().get(i).get("strandSource");
					}

					if (itemParameters.getDictParameterStrands().get(i).get("strandDestination") != "") {
						strandDestination = itemParameters.getDictParameterStrands().get(i).get("strandDestination");
					}

					if (itemParameters.getDictParameterStrands().get(i).get("tubeId") != "") {
						tubeId = itemParameters.getDictParameterStrands().get(i).get("tubeId");
					}
					if (itemParameters.getDictParameterStrands().get(i).get("StrandName") != "") {
						strand_name = itemParameters.getDictParameterStrands().get(i).get("StrandName");
					}

					if (itemParameters.getDictParameterStrands().get(i).get("strandtype") != "") {
						strandtype = itemParameters.getDictParameterStrands().get(i).get("strandtype");
					}

					if (itemParameters.getDictParameterStrands().get(i).get("stranddeployment") != "") {
						stranddeployment = itemParameters.getDictParameterStrands().get(i).get("stranddeployment");
					}

					if (itemParameters.getDictParameterStrands().get(i).get("strandowner") != "") {
						strandowner = itemParameters.getDictParameterStrands().get(i).get("strandowner");
					}
					System.out.print("****************the tube is " + tubeId);

					String sourceWareId = "", sourceId = "", sourceName = "";
					if (strandSource.contains("WARE") == true) {
						sourceWareId = strandSource.split(":")[0];
						sourceId = strandSource.split(":")[2];
						sourceName = strandSource.split(":")[1];
					} else if (strandSource.contains("CUST") == true) {
						sourceWareId = "null";
						sourceId = strandSource.split(":")[0];
						sourceName = strandSource.split(":")[1];
					} else if (strandSource.contains("MH") == true || strandSource.contains("HH") == true
							|| strandSource.contains("DB") == true) {
						sourceWareId = "null";
						sourceId = strandSource.split(":")[0];
						sourceName = strandSource.split(":")[1];
					} else {
						sourceWareId = "null";
						sourceId = "null";
						sourceName = strandSource;
					}

					String dstWareId = "", dstId = "", dstName = "";
					if (strandDestination.contains("WARE") == true) {
						dstWareId = strandDestination.split(":")[0];
						dstId = strandDestination.split(":")[2];
						dstName = strandDestination.split(":")[1];
					} else if (strandDestination.contains("CUST") == true) {
						dstWareId = "null";
						dstId = strandDestination.split(":")[0];
						dstName = strandDestination.split(":")[1];
					} else if (strandDestination.contains("MH") == true || strandDestination.contains("HH") == true
							|| strandDestination.contains("DB") == true) {
						dstWareId = "null";
						dstId = strandDestination.split(":")[0];
						dstName = strandDestination.split(":")[1];
					} else {
						dstWareId = "null";
						dstId = "null";
						dstName = strandDestination;
					}

					float totalDrivDist = 0;
					float totalGeoDist = 0;
					float lastAuxToDestDist = 0;
					float lastAuxToDestDrivDist = 0;

					if (itemParameters.getDictParameterStrands().get(i).get("totalGeoDistance") != "") {
						totalGeoDist = Float
								.parseFloat(itemParameters.getDictParameterStrands().get(i).get("totalGeoDistance"));
					}
					if (itemParameters.getDictParameterStrands().get(i).get("totalDrivDistance") != "") {
						totalDrivDist = Float
								.parseFloat(itemParameters.getDictParameterStrands().get(i).get("totalDrivDistance"));
					}
					if (itemParameters.getDictParameterStrands().get(i).get("distanceLstAuxToDest") != "") {
						lastAuxToDestDist = Float.parseFloat(
								itemParameters.getDictParameterStrands().get(i).get("distanceLstAuxToDest"));
					}
					if (itemParameters.getDictParameterStrands().get(i).get("drivDistanceLstAuxToDest") != "") {
						lastAuxToDestDrivDist = Float.parseFloat(
								itemParameters.getDictParameterStrands().get(i).get("drivDistanceLstAuxToDest"));
					}

					fiberStrand = new FiberStrands();

					fiberStrand.setStrandID(itemParameters.getDictParameterStrands().get(i).get("strandId"));
					fiberStrand.setFibercableID(fiberpathID);
					fiberStrand.setTubeID(tubeId);
					fiberStrand
							.setSrcLong(itemParameters.getDictParameterStrands().get(i).get("strandSource_Longitude"));
					fiberStrand.setSrcLat(itemParameters.getDictParameterStrands().get(i).get("strandSource_Latitude"));
					fiberStrand.setDestLong(
							itemParameters.getDictParameterStrands().get(i).get("strandDestination_Longitude"));
					fiberStrand.setDestLat(
							itemParameters.getDictParameterStrands().get(i).get("strandDestination_Latitude"));
					fiberStrand.setSourceWareId(sourceWareId);
					fiberStrand.setSourceId(sourceId);
					fiberStrand.setSourceName(sourceName);
					fiberStrand.setDestinationWareId(dstWareId);
					fiberStrand.setDestinationId(dstId);
					fiberStrand.setDestinationName(dstName);
					fiberStrand.setStrandName(strand_name);
					fiberStrand.setStrandType(strandtype);
					fiberStrand.setStrandDeployment(stranddeployment);
					fiberStrand.setStrandfiberNetLevel(fibernetlevel);
					fiberStrand.setStrandOwner(strandowner);
					fiberStrand.setCreationDate(cableCreationDate);
					fiberStrand.setLastModifieddate(lastModifiedDate);
					fiberStrand.setCreatedBy(fiberCableCreatedByUser);
					fiberStrand.setLastmodifiedBy(fiberCableCreatedByUser);
					fiberStrand.setTotaldriving(totalDrivDist);
					fiberStrand.setTotalGeoDist(totalGeoDist);
					fiberStrand.setStrandlength(
							Double.parseDouble(itemParameters.getDictParameterStrands().get(i).get("strandLength")));
					fiberStrand.setDrawingtype(itemParameters.getDictParameterStrands().get(i).get("drawingType"));
					fiberStrand.setLastAuxToDestDistance(lastAuxToDestDist);
					fiberStrand.setLastAuxToDestDrivDistance(lastAuxToDestDrivDist);
					fiberStrand.setSrcCity(itemParameters.getDictParameterStrands().get(i).get("SourceCity"));
					fiberStrand.setDestCity(itemParameters.getDictParameterStrands().get(i).get("DestCity"));
					fiberStrand.setStrandNumber(itemParameters.getDictParameterStrands().get(i).get("strandNumber"));
					fiberStrand.setStrandColor(itemParameters.getDictParameterStrands().get(i).get("strandColor"));

					session.saveOrUpdate(fiberStrand);
					session.flush();
					session.clear();
				}

				String tubeSource = "";
				String tubeowner = "";
				String tubedeployment = "";
				String tubenetlevel = "";
				String tubetype = "";
				String tubeDestination = "";
				String tubeIdArray[] = new String[itemParameters.getDictParameterTubes().size()];
				ArrayList<String[]> tubeIdArrayList = new ArrayList<String[]>();
				List<Object[]> strandsOfTubes;

				for (int i = 0; i < itemParameters.getDictParameterTubes().size(); i++) {

					if (itemParameters.getDictParameterTubes().get(i).get("tubeDestination") != "") {
						tubeDestination = itemParameters.getDictParameterTubes().get(i).get("tubeDestination");

					}
					if (itemParameters.getDictParameterTubes().get(i).get("tubeSource") != "") {
						tubeSource = itemParameters.getDictParameterTubes().get(i).get("tubeSource");
					}

					if (itemParameters.getDictParameterTubes().get(i).get("tubetype") != "") {
						tubetype = itemParameters.getDictParameterTubes().get(i).get("tubetype");
					}

					if (itemParameters.getDictParameterTubes().get(i).get("tubedeployment") != "") {
						tubedeployment = itemParameters.getDictParameterTubes().get(i).get("tubedeployment");
					}

					if (itemParameters.getDictParameterTubes().get(i).get("tubenetlevel") != "") {
						tubenetlevel = itemParameters.getDictParameterTubes().get(i).get("tubenetlevel");
					}

					if (itemParameters.getDictParameterTubes().get(i).get("tubeowner") != "") {
						tubeowner = itemParameters.getDictParameterTubes().get(i).get("tubeowner");
					}

					String sourceWareId = "", sourceId = "", sourceName = "";
					if (tubeSource.contains("WARE") == true) {
						sourceWareId = tubeSource.split(":")[0];
						sourceId = tubeSource.split(":")[2];
						sourceName = tubeSource.split(":")[1];
					} else if (tubeSource.contains("CUST") == true) {
						sourceWareId = "null";
						sourceId = tubeSource.split(":")[0];
						sourceName = tubeSource.split(":")[1];
					} else if (tubeSource.contains("MH") == true || tubeSource.contains("HH") == true
							|| tubeSource.contains("DB") == true) {
						sourceWareId = "null";
						sourceId = tubeSource.split(":")[0];
						sourceName = tubeSource.split(":")[1];
					} else {
						sourceWareId = "null";
						sourceId = "null";
						sourceName = tubeSource;
					}

					String dstWareId = "", dstId = "", dstName = "";
					if (tubeDestination.contains("WARE") == true) {
						dstWareId = tubeDestination.split(":")[0];
						dstId = tubeDestination.split(":")[2];
						dstName = tubeDestination.split(":")[1];
					} else if (tubeDestination.contains("CUST") == true) {
						dstWareId = "null";
						dstId = tubeDestination.split(":")[0];
						dstName = tubeDestination.split(":")[1];
					} else if (tubeDestination.contains("MH") == true || tubeDestination.contains("HH") == true
							|| tubeDestination.contains("DB") == true) {
						dstWareId = "null";
						dstId = tubeDestination.split(":")[0];
						dstName = tubeDestination.split(":")[1];
					} else {
						dstWareId = "null";
						dstId = "null";
						dstName = tubeDestination;
					}

					tubeIdArray[i] = itemParameters.getDictParameterTubes().get(i).get("tubeId");

					float totalDrivDist = 0;
					float totalGeoDist = 0;
					float lastAuxToDestDist = 0;
					float lastAuxToDestDrivDist = 0;

					if (itemParameters.getDictParameterTubes().get(i).get("totalGeoDistance") != "") {
						totalGeoDist = Float
								.parseFloat(itemParameters.getDictParameterTubes().get(i).get("totalGeoDistance"));
					}
					if (itemParameters.getDictParameterTubes().get(i).get("totalDrivDistance") != "") {
						totalDrivDist = Float
								.parseFloat(itemParameters.getDictParameterTubes().get(i).get("totalDrivDistance"));
					}
					if (itemParameters.getDictParameterTubes().get(i).get("distanceLstAuxToDest") != "") {
						lastAuxToDestDist = Float
								.parseFloat(itemParameters.getDictParameterTubes().get(i).get("distanceLstAuxToDest"));
					}
					if (itemParameters.getDictParameterTubes().get(i).get("drivDistanceLstAuxToDest") != "") {
						lastAuxToDestDrivDist = Float.parseFloat(
								itemParameters.getDictParameterTubes().get(i).get("drivDistanceLstAuxToDest"));
					}

					fiberTubes = new FiberTubes();

					fiberTubes.setTubeID(itemParameters.getDictParameterTubes().get(i).get("tubeId"));
					fiberTubes.setFibercableID(fiberpathID);
					fiberTubes.setSrcLong(itemParameters.getDictParameterTubes().get(i).get("tubeSource_Longitude"));
					fiberTubes.setSrcLat(itemParameters.getDictParameterTubes().get(i).get("tubeSource_Latitude"));
					fiberTubes.setDestLong(
							itemParameters.getDictParameterTubes().get(i).get("tubeDestination_Longitude"));
					fiberTubes
							.setDestLat(itemParameters.getDictParameterTubes().get(i).get("tubeDestination_Latitude"));
					fiberTubes.setSourceWareId(sourceWareId);
					fiberTubes.setSourceId(sourceId);
					fiberTubes.setSourceName(sourceName);
					fiberTubes.setDestinationWareId(dstWareId);
					fiberTubes.setDestinationId(dstId);
					fiberTubes.setDestinationName(dstName);
					fiberTubes.setTubeName(itemParameters.getDictParameterTubes().get(i).get("tubeName"));
					fiberTubes.setSrcCity(itemParameters.getDictParameterTubes().get(i).get("SourceCity"));
					fiberTubes.setDestCity(itemParameters.getDictParameterTubes().get(i).get("DestCity"));
					fiberTubes.setTubeType(tubetype);
					fiberTubes.setTubeDeployment(tubedeployment);
					fiberTubes.setTubefiberNetLevel(fibernetlevel);
					fiberTubes.setTubeOwner(tubeowner);
					fiberTubes.setCreationDate(cableCreationDate);
					fiberTubes.setLastModifieddate(lastModifiedDate);
					fiberTubes.setCreatedBy(fiberCableCreatedByUser);
					fiberTubes.setLastmodifiedBy(fiberCableCreatedByUser);
					fiberTubes.setTotaldriving(totalDrivDist);
					fiberTubes.setTotalGeoDist(totalGeoDist);
					fiberTubes.setDrawingtype(itemParameters.getDictParameterTubes().get(i).get("drawingType"));
					fiberTubes.setLastAuxToDestDistance(lastAuxToDestDist);
					fiberTubes.setLastAuxToDestDrivDistance(lastAuxToDestDrivDist);
					fiberTubes.setTubelength(
							Double.parseDouble(itemParameters.getDictParameterTubes().get(i).get("tubeLength")));
					fiberTubes.setTubeNumber(itemParameters.getDictParameterTubes().get(i).get("tubeNumber"));
					fiberTubes.setTubeColor(itemParameters.getDictParameterTubes().get(i).get("tubeColor"));
					session.saveOrUpdate(fiberTubes);
					session.flush();
					session.clear();

					String strandsInTubeCount = session
							.createNativeQuery("SELECT count(*) FROM FIBER_STRANDS b WHERE b.TUBE_ID='"
									+ itemParameters.getDictParameterTubes().get(i).get("tubeId") + "'")
							.uniqueResult().toString();

					// System.out.println("SELECT count(*) FROM FIBER_STRANDS b WHERE b.TUBE_ID='" +
					// itemParameters.getDictParameterTubes().get(i).get("tubeId") + "'");

					String tube_StrandRowCount[] = { itemParameters.getDictParameterTubes().get(i).get("tubeId"),
							itemParameters.getDictParameterTubes().get(i).get("tubeSource_Longitude"),
							itemParameters.getDictParameterTubes().get(i).get("tubeSource_Latitude"),
							itemParameters.getDictParameterTubes().get(i).get("tubeDestination_Longitude"),
							itemParameters.getDictParameterTubes().get(i).get("tubeDestination_Latitude"), sourceWareId,
							sourceId, sourceName, dstWareId, dstId, dstName, strandsInTubeCount, fiberpathID,
							itemParameters.getDictParameterTubes().get(i).get("tubeName"),
							itemParameters.getDictParameterTubes().get(i).get("drawingType"),
							itemParameters.getDictParameterTubes().get(i).get("tubeNumber"),
							itemParameters.getDictParameterTubes().get(i).get("tubeColor") };

					tubeIdArrayList.add(tube_StrandRowCount);

				}

				strandsOfTubes = session.createNativeQuery(
						"SELECT c.STRAND_ID,c.SOURCE_LONGITUDE,c.SOURCE_LATITUDE,c.DESTINATION_LONGITUDE,c.DESTINATION_LATITUDE,c.SOURCE_WARE_ID,c.SOURCE_ID,c.SOURCE_NAME,c.DESTINATION_WARE_ID,c.DESTINATION_ID,c.DESTINATION_NAME,c.TUBE_ID,c.FIBER_CABLE_ID,c.STRAND_NAME,c.DRAWING_TYPE,c.STRAND_NUMBER,c.STRAND_COLOR FROM FIBER_STRANDS c,FIBER_TUBES b,FIBER_CABLES a WHERE c.TUBE_ID=b.TUBE_ID and b.FIBER_CABLE_ID=a.FIBER_CABLE_ID and c.FIBER_CABLE_ID='"
								+ fiberpathID + "'")
						.getResultList();

				////////////////////////////////////////// beginning of auxiliary tubes insert

				if (itemParameters.getdictParameterTubesAux().size() > 1500) {
					auxArraySize = 1500;
				} else {
					auxArraySize = itemParameters.getdictParameterTubesAux().size();
				}

				for (int i = 0; i < auxArraySize; i++) {

					fiberAuxtubes = new TubeAuxPoints();
					String auxTube_ID;

					synchronized (this) {
						// String auxTube_ID = "AUXILIARY_TUBE_PT_" + year + "_" +
						// appConfig.getSeqNbr(56,session);
						auxTube_ID = "AUXILIARY_TUBE_PT_" + year + "_" + Integer.parseInt(session
								.createNativeQuery("SELECT FIBER_TUBE_AUX FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET FIBER_TUBE_AUX = FIBER_TUBE_AUX + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						session.flush();
						session.clear();
					}

					Double drivingDistance = itemParameters.getdictParameterTubesAux().get(i)
							.get("drivingDistance") == ""
									? 0
									: itemParameters.getdictParameterTubesAux().get(i).get("drivingDistance") == null
											? 0
											: StringUtils.equalsIgnoreCase(itemParameters.getdictParameterTubesAux()
													.get(i).get("drivingDistance"), "null")
															? 0
															: Double.parseDouble(
																	itemParameters.getdictParameterTubesAux().get(i)
																			.get("drivingDistance"));
					Double geoDistance = itemParameters.getdictParameterTubesAux().get(i).get("geoDistance") == "" ? 0
							: itemParameters.getdictParameterTubesAux().get(i).get("geoDistance") == null ? 0
									: StringUtils.equalsIgnoreCase(
											itemParameters.getdictParameterTubesAux().get(i).get("geoDistance"), "null")
													? 0
													: Double.parseDouble(itemParameters.getdictParameterTubesAux()
															.get(i).get("geoDistance"));

					fiberAuxtubes.setAuxID(auxTube_ID);
					fiberAuxtubes.setFibercableID(fiberpathID);
					fiberAuxtubes.setTubeID(itemParameters.getdictParameterTubesAux().get(i).get("tubeId"));
					fiberAuxtubes.setLong(
							Double.parseDouble(itemParameters.getdictParameterTubesAux().get(i).get("aux_Longitude")));
					fiberAuxtubes.setLat(
							Double.parseDouble(itemParameters.getdictParameterTubesAux().get(i).get("aux_Latitude")));
					fiberAuxtubes.setDistancefromsource(Double
							.parseDouble(itemParameters.getdictParameterTubesAux().get(i).get("distance_From_Source")));

					if (itemParameters.getdictParameterTubesAux().get(i).get("aux_Name").contains("WARE") == true) {

						fiberAuxtubes.setWareID(
								itemParameters.getdictParameterTubesAux().get(i).get("aux_Name").split(":")[0]);
						fiberAuxtubes.setAuxPointID(
								itemParameters.getdictParameterTubesAux().get(i).get("aux_Name").split(":")[2]);
						fiberAuxtubes.setAuxPointName(
								itemParameters.getdictParameterTubesAux().get(i).get("aux_Name").split(":")[1]);
					} else if (itemParameters.getdictParameterTubesAux().get(i).get("aux_Name")
							.contains("Auxiliary_Point") == true) {
						fiberAuxtubes.setWareID("null");
						fiberAuxtubes.setAuxPointID("null");
						if (itemParameters.getdictParameterTubesAux().get(i).get("aux_Name")
								.contains("AUXILIARY_TUBE_PT") == true) {
							fiberAuxtubes.setAuxPointName(
									itemParameters.getdictParameterTubesAux().get(i).get("aux_Name").split(":")[1]);
						} else {
							fiberAuxtubes
									.setAuxPointName(itemParameters.getdictParameterTubesAux().get(i).get("aux_Name"));
						}
					} else if (itemParameters.getdictParameterTubesAux().get(i).get("aux_Name").contains("MH") == true
							|| itemParameters.getdictParameterTubesAux().get(i).get("aux_Name").contains("HH") == true
							|| itemParameters.getdictParameterTubesAux().get(i).get("aux_Name")
									.contains("DB") == true) {
						fiberAuxtubes.setWareID("null");
						fiberAuxtubes.setAuxPointID(
								itemParameters.getdictParameterTubesAux().get(i).get("aux_Name").split(":")[0]);
						fiberAuxtubes.setAuxPointName(
								itemParameters.getdictParameterTubesAux().get(i).get("aux_Name").split(":")[1]);
					} else {
						fiberAuxtubes.setWareID("null");
						fiberAuxtubes.setAuxPointID("null");
						fiberAuxtubes.setAuxPointName(itemParameters.getdictParameterTubesAux().get(i).get("aux_Name"));
					}

					fiberAuxtubes.setCreationDate(cableCreationDate);
					fiberAuxtubes.setLastModifieddate(lastModifiedDate);
					fiberAuxtubes.setSeqSorting(itemParameters.getdictParameterTubesAux().get(i).get("seqSorting"));
					fiberAuxtubes.setDrivingDist(drivingDistance);
					fiberAuxtubes.setGeoDist(geoDistance);

					session.saveOrUpdate(fiberAuxtubes);
					session.flush();
					session.clear();

				}

				if (itemParameters.getDictParameterStrandsAux().size() > 1500) {
					auxArraySize = 1500;
				} else {
					auxArraySize = itemParameters.getDictParameterStrandsAux().size();
				}

				for (int i = 0; i < auxArraySize; i++) {
					String auxStrand_ID;
					synchronized (this) {
						// String auxStrand_ID = "AUXILIARY_STRAND_PT_" + year + "_" +
						// appConfig.getSeqNbr(57,session);
						auxStrand_ID = "AUXILIARY_STRAND_PT_" + year + "_" + Integer.parseInt(session
								.createNativeQuery("SELECT FIBER_STRAND_AUX FROM SEQ_TABLE").uniqueResult().toString());
						query = session
								.createNativeQuery("UPDATE SEQ_TABLE SET FIBER_STRAND_AUX = FIBER_STRAND_AUX + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						session.flush();
						session.clear();
					}
					Double drivingDistance = itemParameters.getDictParameterStrandsAux().get(i)
							.get("drivingDistance") == ""
									? 0
									: itemParameters.getDictParameterStrandsAux().get(i).get("drivingDistance") == null
											? 0
											: StringUtils.equalsIgnoreCase(itemParameters.getDictParameterStrandsAux()
													.get(i).get("drivingDistance"), "null")
															? 0
															: Double.parseDouble(
																	itemParameters.getDictParameterStrandsAux().get(i)
																			.get("drivingDistance"));
					Double geoDistance = itemParameters.getDictParameterStrandsAux().get(i).get("geoDistance") == "" ? 0
							: itemParameters.getDictParameterStrandsAux().get(i).get("geoDistance") == null ? 0
									: StringUtils.equalsIgnoreCase(
											itemParameters.getDictParameterStrandsAux().get(i).get("geoDistance"),
											"null") ? 0
													: Double.parseDouble(itemParameters.getDictParameterStrandsAux()
															.get(i).get("geoDistance"));

					fiberAuxstrands = new StrandAuxPoints();
					fiberAuxstrands.setAuxID(auxStrand_ID);
					fiberAuxstrands.setFibercableID(fiberpathID);
					fiberAuxstrands.setStrandID(itemParameters.getDictParameterStrandsAux().get(i).get("strandId"));
					fiberAuxstrands.setLong(Double
							.parseDouble(itemParameters.getDictParameterStrandsAux().get(i).get("aux_Longitude")));
					fiberAuxstrands.setLat(
							Double.parseDouble(itemParameters.getDictParameterStrandsAux().get(i).get("aux_Latitude")));
					fiberAuxstrands.setDistancefromsource(100.0);

					if (itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name").contains("WARE") == true) {

						fiberAuxstrands.setWareID(
								itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name").split(":")[0]);
						fiberAuxstrands.setAuxPointID(
								itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name").split(":")[2]);
						fiberAuxstrands.setAuxPointName(
								itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name").split(":")[1]);
					} else if (itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name")
							.contains("Auxiliary_Point") == true) {
						fiberAuxstrands.setWareID("null");
						fiberAuxstrands.setAuxPointID("null");
						if (itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name")
								.contains("AUXILIARY_STRAND_PT") == true) {
							fiberAuxstrands.setAuxPointName(
									itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name").split(":")[1]);
						} else {
							fiberAuxstrands.setAuxPointName(
									itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name"));
						}
					} else if (itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name").contains("MH") == true
							|| itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name").contains("HH") == true
							|| itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name")
									.contains("DB") == true) {
						fiberAuxstrands.setWareID("null");
						fiberAuxstrands.setAuxPointID(
								itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name").split(":")[0]);
						fiberAuxstrands.setAuxPointName(
								itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name").split(":")[1]);
					} else {
						fiberAuxstrands.setWareID("null");
						fiberAuxstrands.setAuxPointID("null");
						fiberAuxstrands
								.setAuxPointName(itemParameters.getDictParameterStrandsAux().get(i).get("aux_Name"));
					}

					fiberAuxstrands.setCreationDate(cableCreationDate);
					fiberAuxstrands.setLastModifieddate(lastModifiedDate);
					fiberAuxstrands.setSeqSorting(itemParameters.getDictParameterStrandsAux().get(i).get("seqSorting"));
					fiberAuxstrands.setDrivingDist(drivingDistance);
					fiberAuxstrands.setGeoDist(geoDistance);

					session.saveOrUpdate(fiberAuxstrands);
					session.flush();
					session.clear();

				}

				////////////////////////////////////////// end of auxiliary tubes insert

				List<Object[]> fiberTubesList = session.createNativeQuery(
						"SELECT a.TUBE_ID,a.SOURCE_LONGITUDE,a.SOURCE_LATITUDE,a.DESTINATION_LONGITUDE,a.DESTINATION_LATITUDE,(SELECT count(*) FROM FIBER_STRANDS b WHERE b.TUBE_ID=a.TUBE_ID),a.FIBER_CABLE_ID,a.SOURCE_WARE_ID,a.SOURCE_ID,a.SOURCE_NAME,a.DESTINATION_WARE_ID,a.DESTINATION_ID,a.DESTINATION_NAME,a.TUBE_NAME FROM FIBER_TUBES a WHERE a.FIBER_CABLE_ID='"
								+ fiberpathID + "'")
						.getResultList();

				List<Object[]> fiberStrands = session.createNativeQuery(
						"SELECT STRAND_ID,SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,TUBE_ID,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,STRAND_NAME FROM FIBER_STRANDS WHERE FIBER_CABLE_ID='"
								+ fiberpathID + "'")
						.getResultList();

				List<Object[]> fiberAux = session.createNativeQuery(
						"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.SEQ_SORTING,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID ='"
								+ fiberpathID + "' ORDER BY B.SEQ_SORTING ASC")
						.getResultList();

				rtn.put("FiberPathId", fiberpathID);
				rtn.put("tubeIdArrayList", tubeIdArrayList);
				rtn.put("strandsOfTubes", strandsOfTubes);
				rtn.put("fiberAux", fiberAux);

				rtn.put("tubeIdArray", tubeIdArray);
				rtn.put("fiberTubes", fiberTubesList);
				rtn.put("fiberStrands", fiberStrands);
				session.flush();
				session.clear();
				tx.commit();

			
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in FiberPathSave due to \n " + exceptionAsString);
				logger.info("Error in FiberPathSave due to \n " + exceptionAsString);
				rtn.put("FiberPathId", null);
				rtn.put("tubeIdArrayList", null);
				rtn.put("strandsOfTubes", null);
				rtn.put("fiberAux", null);

				rtn.put("tubeIdArray", null);
				rtn.put("fiberTubes", null);
				rtn.put("fiberStrands", null);
			}

			finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}
		}
		return rtn;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createTubeId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> createTubeId(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				String tube_Id;
				synchronized (this) {
					// String tube_Id = "TUBE_" + year + "_" + appConfig.getSeqNbr(47,session);
					tube_Id = "TUBE_" + year + "_" + Integer.parseInt(
							session.createNativeQuery("SELECT FIBER_TUBE FROM SEQ_TABLE").uniqueResult().toString());
					// System.out.println("hii tubeee");
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET FIBER_TUBE = FIBER_TUBE + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
				}
				rtn.put("tube_Id", tube_Id);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in createTubeId due to \n " + exceptionAsString);
				logger.info("Error in createTubeId due to \n " + exceptionAsString);
				rtn.put("tube_Id", null);
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

	@RequestMapping(value = "/createStrandId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> createStrandId(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				String strand_Id;
				synchronized (this) {
					// String strand_Id = "STRAND_" + year + "_" + appConfig.getSeqNbr(48,session);
					strand_Id = "STRAND_" + year + "_" + Integer.parseInt(
							session.createNativeQuery("SELECT FIBER_STRAND FROM SEQ_TABLE").uniqueResult().toString());
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET FIBER_STRAND = FIBER_STRAND + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
				}
				rtn.put("strand_Id", strand_Id);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in createStrandId due to \n " + exceptionAsString);
				logger.info("Error in createStrandId due to \n " + exceptionAsString);
				rtn.put("strand_Id", null);
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/DeleteFiberPathAux", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> DeleteFiberPathAux(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Inside delete aux ");
				String fiberpathID = request.getParameter("fiberpathID");
				String[] markersArray = request.getParameterValues("markersArray[]");
				System.out.println("delete aux array " + mapper.writeValueAsString(markersArray));

				query = session.createNativeQuery("delete from FIBER_AUXILIARY_POINTS where FIBER_CABLE_ID='"
						+ fiberpathID + "' and AUXILIARY_POINT_ID IN (:param1) ");

				query.setParameter("param1", Arrays.asList(markersArray));
				query.executeUpdate();

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in DeleteFiberPathAux due to \n " + exceptionAsString);
				logger.info("Error in DeleteFiberPathAux due to \n " + exceptionAsString);
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveDistributionBoard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDistributionBoard(Locale locale, Model model,
			@ModelAttribute ItemParameters itemParameters, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			// String boardCity = request.getParameter("boardCity");
			try {
				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				PhysicalLayerActivity PhyAct=new PhysicalLayerActivity();
				String updateModfUser =request.getParameter("updateModfUser");
				String ipAddress = getIpAddress(request);

				DistributionBoard distributionBoard = new DistributionBoard();
				String distributionBoardId = "";
				distributionBoardId = request.getParameter("DistributionBoardId");
				PhyAct.setElementID(distributionBoardId);
				String PhyActID=
						 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						PhyAct.setPhyActID(PhyActID);
						PhyAct.setScreenName("Distribiution Board");
						PhyAct.setUsername(updateModfUser);
						PhyAct.setUserIP(ipAddress);
						PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
			
				if (StringUtils.equalsIgnoreCase(distributionBoardId, "")
						|| StringUtils.equalsIgnoreCase(distributionBoardId, null)) {
					synchronized (this) {

						// distributionBoardId = "DB_" + year + "_" + appConfig.getSeqNbr(52,session);
						distributionBoardId = "DB_" + year + "_" + Integer.parseInt(
								session.createNativeQuery("SELECT DB FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET DB = DB + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						session.flush();
						session.clear();
						PhyAct.setElementID(distributionBoardId);
						PhyAct.setActivityDescription("Add New Element");
					

					}
				}
				else {
					
					PhyAct.setActivityDescription("Edit Existing Element");
					

				}

				Timestamp boardLastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

				String boardCreatedDate = request.getParameter("boardCreatedDate");
				Timestamp boardCreationDate;
				if (StringUtils.equalsIgnoreCase(boardCreatedDate, "")) {
					boardCreationDate = boardLastModifiedDate;

				} else {
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					boardCreationDate = new Timestamp(
							formatter.parse(request.getParameter("boardCreatedDate")).getTime());
				}
				System.out.println("net level" + request.getParameter("dbNetLevel"));

				distributionBoard.setDistributionBoardId(distributionBoardId);
				distributionBoard.setBoardCreationDate(boardCreationDate);
				distributionBoard.setBoardLastModifiedDate(boardLastModifiedDate);
				distributionBoard.setDistributionBoardName(request.getParameter("DistributionBoardName"));
				distributionBoard.setDistributionBoardSite(request.getParameter("DistributionBoardSite"));
				distributionBoard.setDistributionBoardSiteName(request.getParameter("DistributionBoardSiteName"));
				distributionBoard.setDistributionBoardWarehouse(request.getParameter("DistributionBoardWarehouse"));
				distributionBoard.setDistributionBoardCity(request.getParameter("boardCity"));
				distributionBoard.setDbNetLevel(request.getParameter("dbNetLevel"));
				distributionBoard.setDistributionBoardLat(request.getParameter("DistributionBoardLat"));
				distributionBoard.setDistributionBoardLong(request.getParameter("DistributionBoardLong"));
				distributionBoard.setDBInstaller(request.getParameter("DBInstaller"));
				distributionBoard.setDBEngineerName(request.getParameter("DBEngineerName"));
				distributionBoard.setDBDeploymentType(request.getParameter("DBDeploymentType"));
				distributionBoard.setDBAdaptorPanelType(request.getParameter("DBAdaptorPanelType"));
				distributionBoard.setDistributionBoardProjectId(request.getParameter("ProjectId"));
				distributionBoard.setDistributionBoardRowsNum(
						Float.parseFloat(request.getParameter("DistributionBoardRowsNum") != ""
								? request.getParameter("DistributionBoardRowsNum")
								: "0"));
				distributionBoard.setDistributionBoardColsNum(
						Float.parseFloat(request.getParameter("DistributionBoardColsNum") != ""
								? request.getParameter("DistributionBoardColsNum")
								: "0"));
				distributionBoard
						.setDistributionBoardFront(Float.parseFloat(request.getParameter("DistributionBoardFront") != ""
								? request.getParameter("DistributionBoardFront")
								: "0"));
				distributionBoard
						.setDistributionBoardBack(Float.parseFloat(request.getParameter("DistributionBoardBack") != ""
								? request.getParameter("DistributionBoardBack")
								: "0"));
				distributionBoard.setDistributionBoardCapacity(
						Float.parseFloat(request.getParameter("DistributionBoardCapacity") != ""
								? request.getParameter("DistributionBoardCapacity")
								: "0"));
				session.saveOrUpdate(distributionBoard);
				session.flush();
				session.clear();
				session.saveOrUpdate(PhyAct);
				rtn.put("distributionBoardId", distributionBoardId);

				String distBoardMappingFlag = request.getParameter("distBoardMappingFlag");
				if (StringUtils.equalsIgnoreCase(distBoardMappingFlag, "opened")
						|| StringUtils.equalsIgnoreCase(distBoardMappingFlag, "new DB")) {
					query = session.createNativeQuery(
							"delete from DISTRIBUTION_BOARD_MAPPING where DB_ID = '" + distributionBoardId + "'");
					query.executeUpdate();

					// System.out.println("itemParam : " + itemParameters.getDictParameter());
					DistributionBoardMapping distributionBoardMapping;
					String db_Port_Id = "";
					if (itemParameters.getDictParameter().size() > 0) {

						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
							distributionBoardMapping = new DistributionBoardMapping();
							// System.out.println("DB_Mapping : " +
							// itemParameters.getDictParameter().get(i));
							// System.out.println("db_Port_Id : " +
							// itemParameters.getDictParameter().get(i).get("portId"));
							if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get("portId"), "")
									|| StringUtils.equalsIgnoreCase(
											itemParameters.getDictParameter().get(i).get("portId"), null)) {
								synchronized (this) {
									// db_Port_Id = "DB_PORT_" + year + "_" + appConfig.getSeqNbr(62,session);
									db_Port_Id = "DB_PORT_" + year + "_"
											+ Integer
													.parseInt(session.createNativeQuery("SELECT DB_PORT FROM SEQ_TABLE")
															.uniqueResult().toString());
									query = session.createNativeQuery("UPDATE SEQ_TABLE SET DB_PORT = DB_PORT + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate();
									// System.out.println("the db port is " + db_Port_Id);
								}
							} else {
								db_Port_Id = itemParameters.getDictParameter().get(i).get("portId");
							}
							// System.out.println(itemParameters.getDictParameter().get(i).get("rowColIndex"));

							distributionBoardMapping.setDb_Port_Id(db_Port_Id);
							distributionBoardMapping.setDistributionBoardId(distributionBoardId);

							if (itemParameters.getDictParameter().get(i).get("rowColIndex") != null) {
								distributionBoardMapping
										.setRowColIndex(itemParameters.getDictParameter().get(i).get("rowColIndex"));
								// System.out.println("ln");
							} else {
								distributionBoardMapping.setRowColIndex(null);

							}

							distributionBoardMapping
									.setRowNum((itemParameters.getDictParameter().get(i).get("rowNum")));
							distributionBoardMapping
									.setColNum((itemParameters.getDictParameter().get(i).get("colNum")));
							distributionBoardMapping
									.setfP_Status(itemParameters.getDictParameter().get(i).get("fP_Status"));
							distributionBoardMapping.setfP_LocationType(
									itemParameters.getDictParameter().get(i).get("fP_LocationType"));
							distributionBoardMapping.setfP_LocationId(
									itemParameters.getDictParameter().get(i).get("fP_LocationID") != ""
											? itemParameters.getDictParameter().get(i).get("fP_LocationID")
											: "");
							distributionBoardMapping.setfP_LocationName(
									itemParameters.getDictParameter().get(i).get("fP_LocationM") != ""
											? itemParameters.getDictParameter().get(i).get("fP_LocationM")
											: "");
							distributionBoardMapping
									.setfP_Location(itemParameters.getDictParameter().get(i).get("fP_Location") != ""
											? itemParameters.getDictParameter().get(i).get("fP_Location")
											: "");
							distributionBoardMapping.setfP_EquipmentType(
									itemParameters.getDictParameter().get(i).get("fP_EquipmentType") != ""
											? itemParameters.getDictParameter().get(i).get("fP_EquipmentType")
											: "");
							distributionBoardMapping
									.setfP_Equipment(itemParameters.getDictParameter().get(i).get("fP_Equipment"));
							distributionBoardMapping.setfP_EquipmentName(
									itemParameters.getDictParameter().get(i).get("fP_EquipmentName") != ""
											? itemParameters.getDictParameter().get(i).get("fP_EquipmentName")
											: "");
							distributionBoardMapping.setfP_EquipmentId(
									itemParameters.getDictParameter().get(i).get("fP_EquipmentID") != ""
											? itemParameters.getDictParameter().get(i).get("fP_EquipmentID")
											: "");
							distributionBoardMapping
									.setfP_Address(itemParameters.getDictParameter().get(i).get("fP_Address") != ""
											? itemParameters.getDictParameter().get(i).get("fP_Address")
											: "");

							distributionBoardMapping.setfP_JunctionId(
									itemParameters.getDictParameter().get(i).get("fP_JunctionID") != ""
											? itemParameters.getDictParameter().get(i).get("fP_JunctionID")
											: "");

							distributionBoardMapping.setfP_JunctionName(
									itemParameters.getDictParameter().get(i).get("fP_JunctionName") != ""
											? itemParameters.getDictParameter().get(i).get("fP_JunctionName")
											: "");
							/// added
							distributionBoardMapping
									.setfP_StrandNb(itemParameters.getDictParameter().get(i).get("fP_StrandNb") != ""
											? itemParameters.getDictParameter().get(i).get("fP_StrandNb")
											: "");
							distributionBoardMapping.setfP_StrandColor(
									itemParameters.getDictParameter().get(i).get("fP_StrandColor") != ""
											? itemParameters.getDictParameter().get(i).get("fP_StrandColor")
											: "");
							distributionBoardMapping
									.setfP_StrandId(itemParameters.getDictParameter().get(i).get("fP_StrandID") != ""
											? itemParameters.getDictParameter().get(i).get("fP_StrandID")
											: "");
							distributionBoardMapping.setfP_StrandName(
									itemParameters.getDictParameter().get(i).get("fP_StrandName") != ""
											? itemParameters.getDictParameter().get(i).get("fP_StrandName")
											: "");
							distributionBoardMapping
									.setfP_TubeNb(itemParameters.getDictParameter().get(i).get("fP_TubeNb") != ""
											? itemParameters.getDictParameter().get(i).get("fP_TubeNb")
											: "");
							distributionBoardMapping
									.setfP_TubeColor(itemParameters.getDictParameter().get(i).get("fP_TubeColor") != ""
											? itemParameters.getDictParameter().get(i).get("fP_TubeColor")
											: "");
							distributionBoardMapping
									.setfP_TubeId(itemParameters.getDictParameter().get(i).get("fP_TubeID") != ""
											? itemParameters.getDictParameter().get(i).get("fP_TubeID")
											: "");
							distributionBoardMapping
									.setfP_TubeName(itemParameters.getDictParameter().get(i).get("fP_TubeName") != ""
											? itemParameters.getDictParameter().get(i).get("fP_TubeName")
											: "");
							distributionBoardMapping
									.setfP_FiberId(itemParameters.getDictParameter().get(i).get("fP_FiberID") != ""
											? itemParameters.getDictParameter().get(i).get("fP_FiberID")
											: "");
							distributionBoardMapping
									.setfP_FiberName(itemParameters.getDictParameter().get(i).get("fP_FiberName") != ""
											? itemParameters.getDictParameter().get(i).get("fP_FiberName")
											: "");

							///

							distributionBoardMapping
									.setbP_Status(itemParameters.getDictParameter().get(i).get("bP_Status") != ""
											? itemParameters.getDictParameter().get(i).get("bP_Status")
											: "");

							// added
							distributionBoardMapping.setbP_LocationType(
									itemParameters.getDictParameter().get(i).get("bP_LocationType"));
							distributionBoardMapping.setbP_LocationId(
									itemParameters.getDictParameter().get(i).get("bP_LocationID") != ""
											? itemParameters.getDictParameter().get(i).get("bP_LocationID")
											: "");
							distributionBoardMapping.setbP_LocationName(
									itemParameters.getDictParameter().get(i).get("bP_LocationM") != ""
											? itemParameters.getDictParameter().get(i).get("bP_LocationM")
											: "");
							distributionBoardMapping
									.setbP_Location(itemParameters.getDictParameter().get(i).get("bP_Location") != ""
											? itemParameters.getDictParameter().get(i).get("bP_Location")
											: "");
							distributionBoardMapping.setbP_EquipmentType(
									itemParameters.getDictParameter().get(i).get("bP_EquipmentType") != ""
											? itemParameters.getDictParameter().get(i).get("bP_EquipmentType")
											: "");
							distributionBoardMapping
									.setbP_Equipment(itemParameters.getDictParameter().get(i).get("bP_Equipment"));
							distributionBoardMapping.setbP_EquipmentName(
									itemParameters.getDictParameter().get(i).get("bP_EquipmentName") != ""
											? itemParameters.getDictParameter().get(i).get("bP_EquipmentName")
											: "");
							distributionBoardMapping.setbP_EquipmentId(
									itemParameters.getDictParameter().get(i).get("bP_EquipmentID") != ""
											? itemParameters.getDictParameter().get(i).get("bP_EquipmentID")
											: "");
							distributionBoardMapping
									.setbP_Address(itemParameters.getDictParameter().get(i).get("bP_Address") != ""
											? itemParameters.getDictParameter().get(i).get("bP_Address")
											: "");

							distributionBoardMapping.setbP_JunctionId(
									itemParameters.getDictParameter().get(i).get("bP_JunctionID") != ""
											? itemParameters.getDictParameter().get(i).get("bP_JunctionID")
											: "");

							distributionBoardMapping.setbP_JunctionName(
									itemParameters.getDictParameter().get(i).get("bP_JunctionName") != ""
											? itemParameters.getDictParameter().get(i).get("bP_JunctionName")
											: "");

							//
							distributionBoardMapping
									.setbP_StrandNb(itemParameters.getDictParameter().get(i).get("bP_StrandNb") != ""
											? itemParameters.getDictParameter().get(i).get("bP_StrandNb")
											: "");
							distributionBoardMapping.setbP_StrandColor(
									itemParameters.getDictParameter().get(i).get("bP_StrandColor") != ""
											? itemParameters.getDictParameter().get(i).get("bP_StrandColor")
											: "");
							distributionBoardMapping
									.setbP_StrandId(itemParameters.getDictParameter().get(i).get("bP_StrandID") != ""
											? itemParameters.getDictParameter().get(i).get("bP_StrandID")
											: "");
							distributionBoardMapping.setbP_StrandName(
									itemParameters.getDictParameter().get(i).get("bP_StrandName") != ""
											? itemParameters.getDictParameter().get(i).get("bP_StrandName")
											: "");
							distributionBoardMapping
									.setbP_TubeNb(itemParameters.getDictParameter().get(i).get("bP_TubeNb") != ""
											? itemParameters.getDictParameter().get(i).get("bP_TubeNb")
											: "");
							distributionBoardMapping
									.setbP_TubeColor(itemParameters.getDictParameter().get(i).get("bP_TubeColor") != ""
											? itemParameters.getDictParameter().get(i).get("bP_TubeColor")
											: "");
							distributionBoardMapping
									.setbP_TubeId(itemParameters.getDictParameter().get(i).get("bP_TubeID") != ""
											? itemParameters.getDictParameter().get(i).get("bP_TubeID")
											: "");
							distributionBoardMapping
									.setbP_TubeName(itemParameters.getDictParameter().get(i).get("bP_TubeName") != ""
											? itemParameters.getDictParameter().get(i).get("bP_TubeName")
											: "");
							distributionBoardMapping
									.setbP_FiberId(itemParameters.getDictParameter().get(i).get("bP_FiberID") != ""
											? itemParameters.getDictParameter().get(i).get("bP_FiberID")
											: "");
							distributionBoardMapping
									.setbP_FiberName(itemParameters.getDictParameter().get(i).get("bP_FiberName") != ""
											? itemParameters.getDictParameter().get(i).get("bP_FiberName")
											: "");

							session.saveOrUpdate(distributionBoardMapping);
							session.flush();
							session.clear();
						}

					}
				}

				List<Object[]> countConnections = session.createNativeQuery(
						"select coalesce(NUM_ROWS,0) as NUM_ROWS,coalesce(NUM_COLUMNS,0) as NUM_COLUMNS,coalesce((select count (*) from DISTRIBUTION_BOARD_MAPPING a where a.DB_ID='"
								+ distributionBoardId
								+ "' and a.FP_EQUIPMENT!='null'),0) AS front,coalesce((select count (*) from DISTRIBUTION_BOARD_MAPPING a where a.DB_ID='"
								+ distributionBoardId
								+ "' and a.BP_STRAND_NAME!='null'),0) AS back from DISTRIBUTION_BOARD b where b.DB_ID='"
								+ distributionBoardId + "'")
						.getResultList();
				// List<Object[]> countConnections=session.createNativeQuery("select
				// NUM_ROWS,NUM_COLUMNS,(select count (*) from DISTRIBUTION_BOARD_MAPPING a
				// where a.DB_ID='"+distributionBoardId+"' and a.FP_EQUIPMENT!='null') AS
				// front,(select count (*) from DISTRIBUTION_BOARD_MAPPING a where
				// a.DB_ID='"+distributionBoardId+"' and a.BP_STRAND_NAME!='null') AS back from
				// DISTRIBUTION_BOARD b where
				// b.DB_ID='"+distributionBoardId+"'").getResultList();
				// System.out.println("countConnections " +
				// mapper.writeValueAsString(countConnections));
				rtn.put("countConnections", countConnections);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in saveDistributionBoard due to \n " + exceptionAsString);
				logger.info("Error in saveDistributionBoard due to \n " + exceptionAsString);
				rtn.put("distributionBoardId", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveLoadedDistributionBoard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveLoadedDistributionBoard(Locale locale, Model model,
			@ModelAttribute ItemParameters itemParameters, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		List<List<String>> DBDetails = new ArrayList<List<String>>();
		ArrayList<Integer> IgnoredDB = new ArrayList<Integer>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			try {
				tx = session.beginTransaction();

				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				DistributionBoard distributionBoard;
				// System.out.println("itemParam : " +
				// itemParameters.getDictParameterLoadedDB());

				if (itemParameters.getDictParameterLoadedDB().size() > 0) {

					for (int i = 0; i < itemParameters.getDictParameterLoadedDB().size(); i++) {

						String city = itemParameters.getDictParameterLoadedDB().get(i).get("city");
						String siteID = itemParameters.getDictParameterLoadedDB().get(i).get("siteID");

						if (StringUtils.equalsIgnoreCase(city, "") || StringUtils.equalsIgnoreCase(city, null)) {
							// synchronized (this) {

							query = session
									.createNativeQuery("SELECT city FROM WAREHOUSE WHERE SITE_ID ='" + siteID + "'");
							// query.setParameter("param", "%" + siteID + "%");
							// rtn.put("city", query.toString());
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();

							if (query.getResultList().size() == 1) {
								city = query.getSingleResult().toString();
							}

						}

						if (city != null && city != "") {

							distributionBoard = new DistributionBoard();
							String distributionBoardId = "";
							String WareID = "";
							ArrayList<String> DBDetail = new ArrayList<String>();
							synchronized (this) {

								distributionBoardId = "DB_" + year + "_"
										+ Integer.parseInt(
												((Query) session.createNativeQuery("SELECT DB FROM SEQ_TABLE"))
														.getSingleResult().toString());
								query = (Query) session.createNativeQuery("UPDATE SEQ_TABLE SET DB = DB + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
							}

							query = session
									.createNativeQuery("SELECT WARE_ID FROM WAREHOUSE WHERE SITE_ID ='" + siteID + "'");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							WareID = query.getSingleResult().toString();

							Timestamp boardLastModifiedDate = new Timestamp(
									new Timestamp(System.currentTimeMillis()).getTime());
							Timestamp boardCreationDate = boardLastModifiedDate;
							String coresnb = itemParameters.getDictParameterLoadedDB().get(i).get("Nofports");
							String sitename = itemParameters.getDictParameterLoadedDB().get(i).get("siteName");
							// String BoardName = sitename + "_DB_" + year;
							////
							String BoardName;
							String name = itemParameters.getDictParameterLoadedDB().get(i).get("name");
							String NetworkLevel = itemParameters.getDictParameterLoadedDB().get(i).get("NetworkLevel");
							if (StringUtils.equalsIgnoreCase(name, "") || StringUtils.equalsIgnoreCase(name, null)) {
								BoardName = sitename + "_DB_" + year;
							} else {
								BoardName = name;
							}
							////
							Float numberofcores = Float.parseFloat(coresnb);
							// System.out.println("cores number "+numberofcores);
							double num = Math.ceil(numberofcores / 12);

							Float boardfrontports = (float) 0;
							Float boardbackports = (float) 0;
							Float boardcolnum = (float) 12;
							Float boardrownum = (float) num;

							// System.out.println("dit "+request.getParameter("DistributionBoardName")+"
							// "+request.getParameter("DistributionBoardLat"));
							distributionBoard.setDistributionBoardId(distributionBoardId);
							distributionBoard.setDistributionBoardName(BoardName);
							distributionBoard.setDistributionBoardWarehouse(WareID);
							distributionBoard.setDistributionBoardCity(city);
							distributionBoard.setBoardCreationDate(boardCreationDate);
							distributionBoard.setBoardLastModifiedDate(boardLastModifiedDate);
							distributionBoard.setDistributionBoardFront(boardfrontports);
							distributionBoard.setDistributionBoardBack(boardbackports);
							distributionBoard.setDistributionBoardColsNum(boardcolnum);
							distributionBoard.setDistributionBoardProjectId(request.getParameter("ProjectId"));

							distributionBoard.setDistributionBoardRowsNum(boardrownum);
							distributionBoard.setDistributionBoardCapacity(numberofcores);
							distributionBoard.setDbNetLevel(NetworkLevel);

							distributionBoard.setDistributionBoardSiteName(
									itemParameters.getDictParameterLoadedDB().get(i).get("siteName") != ""
											? itemParameters.getDictParameterLoadedDB().get(i).get("siteName")
											: "");

							distributionBoard.setDistributionBoardSite(
									itemParameters.getDictParameterLoadedDB().get(i).get("siteID") != ""
											? itemParameters.getDictParameterLoadedDB().get(i).get("siteID")
											: "");

							distributionBoard.setDistributionBoardLat(
									itemParameters.getDictParameterLoadedDB().get(i).get("latitude") != ""
											? itemParameters.getDictParameterLoadedDB().get(i).get("latitude")
											: "");

							distributionBoard.setDistributionBoardLong(
									itemParameters.getDictParameterLoadedDB().get(i).get("longitude") != ""
											? itemParameters.getDictParameterLoadedDB().get(i).get("longitude")
											: "");
							DBDetail.add(distributionBoardId);
							DBDetail.add(itemParameters.getDictParameterLoadedDB().get(i).get("longitude"));
							DBDetail.add(itemParameters.getDictParameterLoadedDB().get(i).get("latitude"));
							DBDetail.add(BoardName);
							DBDetail.add(coresnb);
							DBDetail.add(itemParameters.getDictParameterLoadedDB().get(i).get("siteID"));
							DBDetail.add(city);
							DBDetail.add(NetworkLevel);
							DBDetails.add(DBDetail);
							session.saveOrUpdate(distributionBoard);

						} else {

							IgnoredDB.add(i + 2);
						}
					}

				}

				rtn.put("distributionBoardDetails", DBDetails);
				rtn.put("IgnoredDB", IgnoredDB);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in saveLoadedDistributionBoard due to \n " + exceptionAsString);
				logger.info("Error in saveLoadedDistributionBoard due to \n " + exceptionAsString);
				rtn.put("distributionBoardId", null);
				rtn.put("IgnoredDB", null);
			}

			finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		return rtn;
	}

	@RequestMapping(value = "/GetAllProjectID", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllProjectID( @RequestParam("SearchTerm") String searchTerm,  Locale locale, Model model, 
	        HttpServletRequest request,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
	    
	    Map<String, Object> rtn = new LinkedHashMap<>();
	    session = AlmDbSession.getInstance().getSession();
	    
	    if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	        rtn.put("Login", "redirect:/");
	        return rtn;
	    } else {
	        if (session != null && session.isOpen()) {
	            tx = session.beginTransaction();
	            try {
	                query = session.createNativeQuery(
	                        "SELECT DISTINCT PROJECT_ID, PROJECT_NAME FROM PROJECT " +
	                        "WHERE PROJECT_LAYER != 'Completed' " +
	                        "AND (lower(PROJECT_ID) LIKE lower(:param) OR lower(PROJECT_NAME) LIKE lower(:param))");
	                query.setParameter("param", "%" + searchTerm + "%");
	                rtn.put("ListProjectId", query.getResultList());
	                session.clear();
	                tx.commit();
	            } catch (Exception e) {
	                tx.rollback();
	                logger.finest("Error in GetAllProjectID due to: " + e.getMessage());
	                rtn.put("error", "Error fetching projects.");
	            } finally {
	                if (session != null && session.isOpen()) {
	                    session.close();
	                }
	            }
	        }
	    }
	    return rtn;
	}



	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SearchForStrand", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SearchForStrand(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				List<String> globalList = new ArrayList<String>();
				String search = request.getParameter("search");
				System.out.println("search " + search);
				query = session.createNativeQuery(
						"SELECT STRAND_ID FROM FIBER_STRANDS WHERE LOWER(STRAND_ID) LIKE :param OR UPPER(STRAND_ID) LIKE :param");
				query.setParameter("param", "%" + search + "%");
				query.setMaxResults(20);
				List<Object[]> strands = query.getResultList();
				if (search != null) {
					if (strands != null && strands.size() != 0) {
						for (Object obj : strands) {
							globalList.add((String) obj);
						}
					}
				}
				rtn.put("globalList", globalList);
				session.clear();
				tx.commit();

			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in saveLoadedDistributionBoard due to \n " + exceptionAsString);
				logger.info("Error in saveLoadedDistributionBoard due to \n " + exceptionAsString);
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchLocation", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchLocation(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		session = AlmDbSession.getInstance().getSession();
		tx = session.beginTransaction();
		List<Object[]> globalList = new ArrayList<Object[]>();
		List<String> searchResult = new ArrayList<String>();

		String search = request.getParameter("searchs");
		String line = request.getParameter("line");
		System.out.println("search " + search);
		System.out.println("line " + line);

		if (StringUtils.equalsIgnoreCase(line, "manhole")) {
			query = session.createNativeQuery(
					"SELECT MANHOLE_ID,MANHOLE_NAME FROM MANHOLE WHERE UPPER(MANHOLE_ID) LIKE UPPER(:param)  OR UPPER(MANHOLE_NAME) LIKE UPPER(:param) ");
			query.setParameter("param", "%" + search + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);
			globalList.addAll(query.getResultList());

		} else if (StringUtils.equalsIgnoreCase(line, "handhole")) {

			query = session.createNativeQuery(
					"SELECT HANDHOLE_ID,HANDHOLE_NAME FROM HANDHOLE b WHERE UPPER(HANDHOLE_ID) LIKE UPPER(:param) OR UPPER(HANDHOLE_NAME) LIKE UPPER(:param) ");
			query.setParameter("param", "%" + search + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);
			globalList.addAll(query.getResultList());

		} else if (StringUtils.equalsIgnoreCase(line, "DB")) {

			query = session.createNativeQuery(
					"SELECT DB_ID,DB_NAME FROM DISTRIBUTION_BOARD c WHERE UPPER(DB_ID) LIKE UPPER(:param) OR UPPER(DB_NAME) LIKE UPPER(:param) ");

			query.setParameter("param", "%" + search + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);
			globalList.addAll(query.getResultList());

		} else if (StringUtils.equalsIgnoreCase(line, "client")) {

			// query = session.createNativeQuery("SELECT DISPLAY_NAME FROM CLIENTS WHERE
			// LOWER(DISPLAY_NAME) LIKE LOWER(:param)");
			query = session.createNativeQuery(
					"SELECT CLIENT_ID,DISPLAY_NAME,LONGITUDE,LATITUDE FROM CLIENTS WHERE CLIENT_ID LIKE :param OR DISPLAY_NAME LIKE :param");

			query.setParameter("param", "%" + search + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);
			globalList.addAll(query.getResultList());

		} else if (StringUtils.equalsIgnoreCase(line, "site")) {

			// query = session.createNativeQuery("SELECT WARE_NAME FROM WAREHOUSE WHERE
			// WARE_NAME LIKE :param");
			query = session.createNativeQuery(
					"SELECT WARE_ID,WARE_NAME,LONGITUDE,LATITUDE,CITY,SITE_ID FROM WAREHOUSE WHERE UPPER(WARE_ID) LIKE UPPER(:param)  OR UPPER(WARE_NAME) LIKE UPPER(:param) OR UPPER(SITE_ID) LIKE UPPER(:param) ");

			query.setParameter("param", "%" + search + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);
			globalList.addAll(query.getResultList());

		} else {
			List<Object[]> emptyList = new ArrayList<Object[]>();
			globalList.addAll(emptyList);

		}

		if (globalList != null && globalList.size() != 0) {

			for (Object[] obj : globalList) {
				if (obj.length > 4) {
					searchResult.add(obj[0] + ":" + obj[1] + ":" + obj[2] + ":" + obj[3] + ":" + obj[4] + ":" + obj[5]);
				} else if (obj.length > 2) {
					searchResult.add(obj[0] + ":" + obj[1] + ":" + obj[2] + ":" + obj[3]);
				} else {
					searchResult.add(obj[0] + ":" + obj[1]);
				}
			}

			System.out.println("searchResult " + searchResult);
			rtn.put("searchResult", searchResult);
			rtn.put("globalList", query.getResultList());

		}
		tx.commit();
		session.close();

		return rtn;

	}

	@RequestMapping(value = "/getNodeData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getNodeData(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here getNodeData");
		Map<String, Object> rtn = new LinkedHashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		Query query;
		session = AlmDbSession.getInstance().getSession();
		String search = request.getParameter("searchs");
		System.out.println("search " + search);
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				/*
				 * if(search == null) { System.out.println("search is null"); search = ""; }else
				 * { System.out.println("search is not null"); }
				 */
				query = session.createNativeQuery(
						// "SELECT NODE_ID,NODE_NAME,NODE_TYPE FROM NODE_ACTIVE WHERE UPPER(NODE_ID)
						// LIKE UPPER(:param) OR UPPER(NODE_NAME) LIKE UPPER(:param) ");
						"SELECT UNIQUE_NODE_ID,NODE_NAME,NODE_TYPE FROM NODE_ACTIVE WHERE NODE_ID LIKE :param OR NODE_NAME LIKE :param ");
				// "SELECT NODE_ID,NODE_NAME,NODE_TYPE FROM NODE_ACTIVE ");

				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("globalList", query.getResultList());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getNodeData due to \n " + exceptionAsString);
				logger.info("Error in getNodeData due to \n " + exceptionAsString);
				rtn.put("searchResult", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}

		return rtn;

	}

	@RequestMapping(value = "/getFiberPath", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFiberPath(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here getFiberPath");
		Map<String, Object> rtn = new LinkedHashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				List<Object[]> fiberList = new ArrayList<Object[]>();
				List<Object[]> fiberAuxiliary_Data = new ArrayList<Object[]>();
				List<Object[]> fiberTubes = new ArrayList<Object[]>();
				List<Object[]> tubesAuxiliaries = new ArrayList<Object[]>();
				List<Object[]> fiberStrands = new ArrayList<Object[]>();
				List<Object[]> strandsAuxiliaries = new ArrayList<Object[]>();
				fiberList = session.createNativeQuery(
						"SELECT SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID),(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID),FIBER_CABLE_NAME,PROJECT_ID,SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_TUBES,NUMBER_OF_STRANDS,LENGTH,DRAWING_TYPE,FIBER_NETWORK_LEVEL,FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A WHERE A.PROJECT_ID='CurrentPhysicalLayer'")
						.getResultList();

				fiberAuxiliary_Data = session.createNativeQuery(
						"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND A.PROJECT_ID='CurrentPhysicalLayer' ORDER BY B.SEQ_SORTING ASC")
						.getResultList();

				fiberTubes = session.createNativeQuery(
						"SELECT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,"
								+ "(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER, b.TUBE_COLOR "
								+ "FROM FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID AND a.PROJECT_ID='CurrentPhysicalLayer' ORDER BY FIBER_CABLE_ID,TUBE_NUMBER ASC")
						.getResultList();
				// System.out.println("fb >>" + mapper.writeValueAsString(fiberTubes));

				tubesAuxiliaries = session.createNativeQuery(
						"SELECT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c,FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.TUBE_ID=c.TUBE_ID and a.PROJECT_ID='CurrentPhysicalLayer' ORDER BY c.SEQ_SORTING ASC")
						.getResultList();

				fiberStrands = session.createNativeQuery(
						"SELECT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and a.PROJECT_ID='CurrentPhysicalLayer' ORDER BY STRAND_NUMBER")
						.getResultList();
				// System.out.println("fs >>" + mapper.writeValueAsString(fiberStrands));

				strandsAuxiliaries = session.createNativeQuery(
						"SELECT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,C.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID and a.PROJECT_ID='CurrentPhysicalLayer' ORDER BY c.SEQ_SORTING ASC ")
						.getResultList();

				rtn.put("fiber", fiberList);
				rtn.put("strands_Auxiliaries", strandsAuxiliaries);
				rtn.put("fiber_Strands", fiberStrands);
				rtn.put("tubes_Auxiliaries", tubesAuxiliaries);
				rtn.put("fiber_Tubes", fiberTubes);
				rtn.put("fiber_Auxiliary", fiberAuxiliary_Data);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getFiberPath due to \n " + exceptionAsString);
				logger.info("Error in getFiberPath due to \n " + exceptionAsString);
				rtn.put("searchResult", "Failed");
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
	@RequestMapping(value = "/getNode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getNode(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here getNode");
		Map<String, Object> rtn = new LinkedHashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				List<Object[]> NodeList = new ArrayList<Object[]>();
				NodeList = session.createNativeQuery(
						"SELECT DISTINCT NODE_PK,NODE_NAME,NODE_TYPE || ':'  || NODE_NAME,DOMAIN,SITE_ID,LONGITUDE,LATITUDE,NODE_ID,SUB_DOMAIN_TYPE"
						+" FROM NODE_ACTIVE WHERE ((SUB_DOMAIN_TYPE IN ('MSAN', 'SDH', 'DWDM', 'GPON', 'SWITCH')"
						+" OR NODE_TYPE IN ('MSAN', 'SDH', 'DWDM', 'GPON', 'SWITCH')) AND ACTIVE_RECORD = 1)")
						.getResultList();

				rtn.put("nodeList", NodeList);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getNodeActive due to \n " + exceptionAsString);
				logger.info("Error in getNodeActive due to \n " + exceptionAsString);
				rtn.put("searchResult", "Failed");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}

		return rtn;

	}
	
	@RequestMapping(value = "/getJunction", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getJunction(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		//System.out.println("Passes here getJunction");
		Map<String, Object> rtn = new LinkedHashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		Query query;
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				List<Object[]> JunctionList = new ArrayList<Object[]>();
				JunctionList = session.createNativeQuery("SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A where (A.PROJECT_ID='CurrentPhysicalLayer') AND A.PHYSICAL_LAYER_ID IS NULL OR A.PHYSICAL_LAYER_ID = ' ' OR A.PHYSICAL_LAYER_ID = 'null' ").getResultList();
				rtn.put("JunctionList", JunctionList);
				//System.out.println("JunctionList "+JunctionList);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getJunction due to \n " + exceptionAsString);
				logger.info("Error in getJunction due to \n " + exceptionAsString);
				rtn.put("searchResult", "Failed");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					//session.getSessionFactory().close();
				}
			}
		}

		return rtn;

	}

	@RequestMapping(value = "/SearchStrand", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SearchStrand(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		session = AlmDbSession.getInstance().getSession();
		tx = session.beginTransaction();

		// String sId = request.getParameter("sId");
		// String sName = request.getParameter("sName");
		// System.out.println("search "+sId+ " "+sName);
		String searchId = request.getParameter("searchId");

		query = session.createNativeQuery(
				"SELECT STRAND_ID,STRAND_NAME,TUBE_ID,FIBER_CABLE_ID FROM FIBER_STRANDS WHERE LOWER(STRAND_NAME) LIKE :param OR UPPER(STRAND_NAME) LIKE :param OR LOWER(STRAND_ID) LIKE :param OR UPPER(STRAND_ID) LIKE :param OR LOWER(FIBER_CABLE_ID) LIKE :param OR UPPER(FIBER_CABLE_ID) LIKE :param OR LOWER(TUBE_ID) LIKE :param OR UPPER(TUBE_ID) LIKE :param");
		// query.setParameter("param", "%" + sId + "%");
		// query.setParameter("param2", "%" + sId + "%");
		query.setParameter("param", "%" + searchId + "%");

		rtn.put("glist", query.getResultList());
		tx.commit();
		session.close();

		return rtn;

	}

	@RequestMapping(value = "/SearchMappingStrand", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SearchMappingStrand(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here strand");
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String searchId = request.getParameter("searchId");

					query = session.createNativeQuery(
							"SELECT A.STRAND_ID,A.STRAND_NAME,A.TUBE_ID,A.FIBER_CABLE_ID,B.FIBER_CABLE_NAME, C.TUBE_NAME,A.STRAND_NUMBER,A.STRAND_COLOR,C.TUBE_NUMBER,C.TUBE_COLOR,A.STRAND_NETWORK_LEVEL FROM (FIBER_STRANDS A LEFT JOIN FIBER_CABLES B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID)"
									+ " LEFT JOIN FIBER_TUBES C ON A.TUBE_ID = C.TUBE_ID "
									+ "WHERE UPPER(A.STRAND_NAME) LIKE UPPER(:param) OR UPPER(A.STRAND_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(A.TUBE_ID) LIKE UPPER(:param) OR UPPER(B.FIBER_CABLE_NAME) LIKE UPPER(:param) OR UPPER(C.TUBE_NAME) LIKE UPPER(:param)");
					// query.setParameter("param", "%" + sId + "%");
					// query.setParameter("param2", "%" + sId + "%");
					query.setParameter("param", "%" + searchId + "%");

					rtn.put("glist", query.getResultList());

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in SearchMappingStrand due to \n " + exceptionAsString);
					logger.info("Error in SearchMappingStrand due to \n " + exceptionAsString);
					rtn.put("glist", null);
				}

				finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}

			}
		}

		return rtn;

	}

	@RequestMapping(value = "/SearchTube", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SearchTube(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here tube");
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String searchId = request.getParameter("searchId");

					/*
					 * query = session.createNativeQuery(
					 * "SELECT TUBE_NAME,TUBE_ID,FIBER_CABLE_ID FROM FIBER_TUBES WHERE TUBE_ID IN (SELECT TUBE_ID FROM FIBER_STRANDS WHERE LOWER(STRAND_NAME) LIKE :param OR UPPER(STRAND_NAME) LIKE :param OR LOWER(STRAND_ID) LIKE :param OR UPPER(STRAND_ID) LIKE :param)"
					 * );
					 */

					/*
					 * query = session.createNativeQuery(
					 * "SELECT DISTINCT C.TUBE_ID,C.TUBE_NAME,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_NUMBER,C.TUBE_COLOR FROM (FIBER_TUBES C LEFT JOIN FIBER_STRANDS A ON C.TUBE_ID = A.TUBE_ID)"
					 * + " LEFT JOIN FIBER_CABLES B ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID "
					 * 
					 * +
					 * "WHERE LOWER(A.STRAND_NAME) LIKE :param OR UPPER(A.STRAND_NAME) LIKE :param OR LOWER(A.STRAND_ID) LIKE :param OR UPPER(A.STRAND_ID) LIKE :param OR LOWER(A.FIBER_CABLE_ID) LIKE :param OR UPPER(A.FIBER_CABLE_ID) LIKE :param OR LOWER(A.TUBE_ID) LIKE :param OR UPPER(A.TUBE_ID) LIKE :param OR LOWER(B.FIBER_CABLE_NAME) LIKE :param OR UPPER(B.FIBER_CABLE_NAME) LIKE :param OR LOWER(C.TUBE_NAME) LIKE :param OR UPPER(C.TUBE_NAME) LIKE :param"
					 * );
					 */
					query = session.createNativeQuery(
							"SELECT DISTINCT C.TUBE_ID,C.TUBE_NAME,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_NUMBER,C.TUBE_COLOR,C.TUBE_NETWORK_LEVEL FROM (FIBER_TUBES C LEFT JOIN FIBER_STRANDS A ON C.TUBE_ID = A.TUBE_ID)"
									+ " LEFT JOIN FIBER_CABLES B ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID "
									+ "WHERE UPPER(A.STRAND_NAME) LIKE UPPER(:param) OR UPPER(A.STRAND_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(A.TUBE_ID) LIKE UPPER(:param) OR UPPER(B.FIBER_CABLE_NAME) LIKE UPPER(:param) OR UPPER(C.TUBE_NAME) LIKE UPPER(:param)");
					// query.setParameter("param", "%" + sId + "%");
					// query.setParameter("param2", "%" + sId + "%");
					query.setParameter("param", "%" + searchId + "%");

					rtn.put("clist", query.getResultList());

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in SearchTube due to \n " + exceptionAsString);
					logger.info("Error in SearchTube due to \n " + exceptionAsString);
					rtn.put("clist", null);
				}

				finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}

			}
		}

		return rtn;

	}

	@RequestMapping(value = "/SearchFiber", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SearchFiber(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here fiber");
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String searchId = request.getParameter("searchId");
					/*
					 * query = session.createNativeQuery(
					 * "SELECT TUBE_NAME,TUBE_ID,FIBER_CABLE_ID FROM FIBER_TUBES WHERE TUBE_ID IN (SELECT TUBE_ID FROM FIBER_STRANDS WHERE LOWER(STRAND_NAME) LIKE :param OR UPPER(STRAND_NAME) LIKE :param OR LOWER(STRAND_ID) LIKE :param OR UPPER(STRAND_ID) LIKE :param)"
					 * );
					 */

					/*
					 * query = session.createNativeQuery(
					 * "SELECT DISTINCT A.FIBER_CABLE_ID,A.FIBER_CABLE_NAME FROM (FIBER_CABLES A LEFT JOIN FIBER_STRANDS B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID)"
					 * + " LEFT JOIN FIBER_TUBES C ON A.FIBER_CABLE_ID = C.FIBER_CABLE_ID " +
					 * "WHERE LOWER(B.STRAND_NAME) LIKE :param OR UPPER(B.STRAND_NAME) LIKE :param OR LOWER(B.STRAND_ID) LIKE :param OR UPPER(B.STRAND_ID) LIKE :param OR LOWER(A.FIBER_CABLE_ID) LIKE :param OR UPPER(A.FIBER_CABLE_ID) LIKE :param OR LOWER(C.TUBE_ID) LIKE :param OR UPPER(C.TUBE_ID) LIKE :param OR LOWER(A.FIBER_CABLE_NAME) LIKE :param OR UPPER(A.FIBER_CABLE_NAME) LIKE :param"
					 * );
					 */
					query = session.createNativeQuery(
							"SELECT DISTINCT A.FIBER_CABLE_ID,A.FIBER_CABLE_NAME,A.FIBER_NETWORK_LEVEL FROM (FIBER_CABLES A LEFT JOIN FIBER_STRANDS B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID)"
									+ " LEFT JOIN FIBER_TUBES C ON A.FIBER_CABLE_ID = C.FIBER_CABLE_ID "
									+ "WHERE UPPER(B.STRAND_NAME) LIKE UPPER(:param) OR UPPER(B.STRAND_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(C.TUBE_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_NAME) LIKE UPPER(:param)");
					// query.setParameter("param", "%" + sId + "%");
					// query.setParameter("param2", "%" + sId + "%");
					// query.setParameter("param", "%" + fName + "%");
					query.setParameter("param", "%" + searchId + "%");
					rtn.put("glist", query.getResultList());
					session.clear();
					tx.commit();

				} catch (Exception e) {
					tx.rollback();
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in SearchFiber due to \n " + exceptionAsString);
					logger.info("Error in SearchFiber due to \n " + exceptionAsString);
					rtn.put("glist", null);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();

					}
				}

			}
		}

		return rtn;

	}

	@RequestMapping(value = "/SearchLastMile", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SearchLastMile(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String searchId = request.getParameter("searchId");

					query = session.createNativeQuery(
							"SELECT DISTINCT FIBER_CABLE_ID,FIBER_CABLE_NAME FROM FIBER_CABLES WHERE (UPPER(FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(FIBER_CABLE_NAME) LIKE UPPER(:param)) AND FIBER_NETWORK_LEVEL='access' ");

					query.setParameter("param", "%" + searchId + "%");

					rtn.put("clist", query.getResultList());
					session.clear();
					tx.commit();

				} catch (Exception e) {
					tx.rollback();
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in SearchLastMile due to \n " + exceptionAsString);
					logger.info("Error in SearchLastMile due to \n " + exceptionAsString);
					rtn.put("clist", null);
				}

				finally {
					if (session != null && session.isOpen()) {
						session.close();

					}
				}

			}
		}

		return rtn;

	}

	@RequestMapping(value = "/SearchJunction", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SearchJunction(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String searchId = request.getParameter("searchId");

					query = session.createNativeQuery(
							"SELECT DISTINCT JUNCTION_ID,JUNCTION_NAME FROM JUNCTION WHERE UPPER(JUNCTION_ID) LIKE UPPER(:param) OR UPPER(JUNCTION_NAME) LIKE UPPER(:param) OR UPPER(PHYSICAL_LAYER_ID) LIKE UPPER(:param) OR UPPER(PHYSICAL_LAYER_NAME) LIKE UPPER(:param)");

					query.setParameter("param", "%" + searchId + "%");

					rtn.put("clist", query.getResultList());

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in SearchJunction due to \n " + exceptionAsString);
					logger.info("Error in SearchJunction due to \n " + exceptionAsString);
					rtn.put("clist", null);
				}

				finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}

			}
		}

		return rtn;

	}

	/*
	 * @RequestMapping(value = "/SearchMappingStrand", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> SearchMappingStrand(Locale locale,
	 * Model model, HttpServletRequest request, HttpServletResponse response) throws
	 * JsonGenerationException, JsonMappingException, IOException {
	 * System.out.println("Passes herenn"); Map<String, Object> rtn = new
	 * LinkedHashMap<String, Object>(); if (LoginServices.checkSession(request,
	 * response).equals("redirect:/")) { rtn.put("Login",
	 * LoginServices.checkSession(request, response)); return rtn; }
	 * 
	 * session = almsessions.getALMSession(); tx = session.beginTransaction();
	 * 
	 * // String sId = request.getParameter("sId"); // String sName =
	 * request.getParameter("sName"); // System.out.println("search "+sId+
	 * " "+sName); String searchId = request.getParameter("searchId");
	 * 
	 * query = session.createNativeQuery(
	 * "SELECT A.STRAND_ID,A.STRAND_NAME,A.TUBE_ID,A.FIBER_CABLE_ID,B.FIBER_CABLE_NAME, C.TUBE_NAME,A.STRAND_NUMBER,A.STRAND_COLOR,C.TUBE_NUMBER,C.TUBE_COLOR FROM (FIBER_STRANDS A LEFT JOIN FIBER_CABLES B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID)"
	 * + " LEFT JOIN FIBER_TUBES C ON A.TUBE_ID = C.TUBE_ID "
	 * +"WHERE UPPER(A.STRAND_NAME) LIKE UPPER(:param) OR UPPER(A.STRAND_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(A.TUBE_ID) LIKE UPPER(:param) OR UPPER(B.FIBER_CABLE_NAME) LIKE UPPER(:param) OR UPPER(C.TUBE_NAME) LIKE UPPER(:param)"
	 * ); // query.setParameter("param", "%" + sId + "%"); //
	 * query.setParameter("param2", "%" + sId + "%"); query.setParameter("param",
	 * "%" + searchId + "%");
	 * 
	 * rtn.put("glist", query.getResultList()); tx.commit(); session.close();
	 * 
	 * return rtn;
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/SearchTube", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> SearchTube(Locale locale, Model
	 * model, HttpServletRequest request, HttpServletResponse response) throws
	 * JsonGenerationException, JsonMappingException, IOException {
	 * System.out.println("Passes hereh"); Map<String, Object> rtn = new
	 * LinkedHashMap<String, Object>(); if (LoginServices.checkSession(request,
	 * response).equals("redirect:/")) { rtn.put("Login",
	 * LoginServices.checkSession(request, response)); return rtn; }
	 * 
	 * session = almsessions.getALMSession(); tx = session.beginTransaction();
	 * 
	 * // String sId = request.getParameter("sId"); // String sName =
	 * request.getParameter("sName"); // System.out.println("search "+sId+
	 * " "+sName); String searchId = request.getParameter("searchId");
	 * 
	 * /*query = session.createNativeQuery(
	 * "SELECT TUBE_NAME,TUBE_ID,FIBER_CABLE_ID FROM FIBER_TUBES WHERE TUBE_ID IN (SELECT TUBE_ID FROM FIBER_STRANDS WHERE LOWER(STRAND_NAME) LIKE :param OR UPPER(STRAND_NAME) LIKE :param OR LOWER(STRAND_ID) LIKE :param OR UPPER(STRAND_ID) LIKE :param)"
	 * );
	 */

	/*
	 * query = session.createNativeQuery(
	 * "SELECT DISTINCT C.TUBE_ID,C.TUBE_NAME,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_NUMBER,C.TUBE_COLOR FROM (FIBER_TUBES C LEFT JOIN FIBER_STRANDS A ON C.TUBE_ID = A.TUBE_ID)"
	 * + " LEFT JOIN FIBER_CABLES B ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID "
	 * 
	 * +
	 * "WHERE LOWER(A.STRAND_NAME) LIKE :param OR UPPER(A.STRAND_NAME) LIKE :param OR LOWER(A.STRAND_ID) LIKE :param OR UPPER(A.STRAND_ID) LIKE :param OR LOWER(A.FIBER_CABLE_ID) LIKE :param OR UPPER(A.FIBER_CABLE_ID) LIKE :param OR LOWER(A.TUBE_ID) LIKE :param OR UPPER(A.TUBE_ID) LIKE :param OR LOWER(B.FIBER_CABLE_NAME) LIKE :param OR UPPER(B.FIBER_CABLE_NAME) LIKE :param OR LOWER(C.TUBE_NAME) LIKE :param OR UPPER(C.TUBE_NAME) LIKE :param"
	 * );
	 */
	/*
	 * query = session.createNativeQuery(
	 * "SELECT DISTINCT C.TUBE_ID,C.TUBE_NAME,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_NUMBER,C.TUBE_COLOR FROM (FIBER_TUBES C LEFT JOIN FIBER_STRANDS A ON C.TUBE_ID = A.TUBE_ID)"
	 * + " LEFT JOIN FIBER_CABLES B ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID " +
	 * "WHERE UPPER(A.STRAND_NAME) LIKE UPPER(:param) OR UPPER(A.STRAND_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(A.TUBE_ID) LIKE UPPER(:param) OR UPPER(B.FIBER_CABLE_NAME) LIKE UPPER(:param) OR UPPER(C.TUBE_NAME) LIKE UPPER(:param)"
	 * ); // query.setParameter("param", "%" + sId + "%"); //
	 * query.setParameter("param2", "%" + sId + "%"); query.setParameter("param",
	 * "%" + searchId + "%");
	 * 
	 * rtn.put("clist", query.getResultList()); tx.commit(); session.close();
	 * 
	 * return rtn;
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/SearchFiber", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> SearchFiber(Locale locale, Model
	 * model, HttpServletRequest request, HttpServletResponse response) throws
	 * JsonGenerationException, JsonMappingException, IOException {
	 * System.out.println("Passes here"); Map<String, Object> rtn = new
	 * LinkedHashMap<String, Object>(); if (LoginServices.checkSession(request,
	 * response).equals("redirect:/")) { rtn.put("Login",
	 * LoginServices.checkSession(request, response)); return rtn; }
	 * 
	 * session = almsessions.getALMSession(); tx = session.beginTransaction();
	 * 
	 * // String sId = request.getParameter("sId"); // String sName =
	 * request.getParameter("sName"); // System.out.println("search "+sId+
	 * " "+sName); String searchId = request.getParameter("searchId"); String fName
	 * = request.getParameter("fName"); /*query = session.createNativeQuery(
	 * "SELECT TUBE_NAME,TUBE_ID,FIBER_CABLE_ID FROM FIBER_TUBES WHERE TUBE_ID IN (SELECT TUBE_ID FROM FIBER_STRANDS WHERE LOWER(STRAND_NAME) LIKE :param OR UPPER(STRAND_NAME) LIKE :param OR LOWER(STRAND_ID) LIKE :param OR UPPER(STRAND_ID) LIKE :param)"
	 * );
	 */

	/*
	 * query = session.createNativeQuery(
	 * "SELECT DISTINCT A.FIBER_CABLE_ID,A.FIBER_CABLE_NAME FROM (FIBER_CABLES A LEFT JOIN FIBER_STRANDS B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID)"
	 * + " LEFT JOIN FIBER_TUBES C ON A.FIBER_CABLE_ID = C.FIBER_CABLE_ID " +
	 * "WHERE LOWER(B.STRAND_NAME) LIKE :param OR UPPER(B.STRAND_NAME) LIKE :param OR LOWER(B.STRAND_ID) LIKE :param OR UPPER(B.STRAND_ID) LIKE :param OR LOWER(A.FIBER_CABLE_ID) LIKE :param OR UPPER(A.FIBER_CABLE_ID) LIKE :param OR LOWER(C.TUBE_ID) LIKE :param OR UPPER(C.TUBE_ID) LIKE :param OR LOWER(A.FIBER_CABLE_NAME) LIKE :param OR UPPER(A.FIBER_CABLE_NAME) LIKE :param"
	 * );
	 */
	/*
	 * query = session.createNativeQuery(
	 * "SELECT DISTINCT A.FIBER_CABLE_ID,A.FIBER_CABLE_NAME FROM (FIBER_CABLES A LEFT JOIN FIBER_STRANDS B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID)"
	 * + " LEFT JOIN FIBER_TUBES C ON A.FIBER_CABLE_ID = C.FIBER_CABLE_ID " +
	 * "WHERE UPPER(B.STRAND_NAME) LIKE UPPER(:param) OR UPPER(B.STRAND_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(C.TUBE_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_NAME) LIKE UPPER(:param)"
	 * ); // query.setParameter("param", "%" + sId + "%"); //
	 * query.setParameter("param2", "%" + sId + "%"); //query.setParameter("param",
	 * "%" + fName + "%"); query.setParameter("param", "%" + searchId + "%");
	 * 
	 * rtn.put("glist", query.getResultList()); tx.commit(); session.close();
	 * 
	 * return rtn;
	 * 
	 * }
	 */
	@RequestMapping(value = "/GetFiberTubeNames", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetFiberTubeNames(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("GetFiberTubeNames");
		Map<String, Object> rtn = new LinkedHashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				String cID = request.getParameter("cID");
				String fID = request.getParameter("fID");
				System.out.println("search " + cID);
				System.out.println("search " + fID);

				query = session.createNativeQuery(
						"SELECT TUBE_NAME FROM FIBER_TUBES WHERE LOWER(TUBE_ID) LIKE :param OR UPPER(TUBE_ID) LIKE :param");
				query.setParameter("param", "%" + cID + "%");
				rtn.put("clist", query.getResultList());
				System.out.println("  " + query.getResultList());
				query = session.createNativeQuery(
						"SELECT FIBER_CABLE_NAME FROM FIBER_CABLES WHERE LOWER(FIBER_CABLE_ID) LIKE :param OR UPPER(FIBER_CABLE_ID) LIKE :param");
				query.setParameter("param", "%" + fID + "%");
				rtn.put("flist", query.getResultList());
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in SearchJunction due to \n " + exceptionAsString);
				logger.info("Error in SearchJunction due to \n " + exceptionAsString);
				rtn.put("clist", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}
		}
		return rtn;
	}

	// to be deleted
	/*
	 * @RequestMapping(value = "/GetManholeCount", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> GetManholeCount(Locale locale, Model
	 * model, HttpServletRequest request, HttpServletResponse response) throws
	 * JsonGenerationException, JsonMappingException, IOException { Map<String,
	 * Object> rtn = new LinkedHashMap<>(); session = almsessions.getALMSession();
	 * if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	 * rtn.put("Login", LoginServices.checkSession(request, response)); return rtn;
	 * } if (session != null && session.isOpen()) { tx = session.beginTransaction();
	 * try { Date date = new Date(); Calendar calendar = new GregorianCalendar();
	 * calendar.setTime(date); int year = calendar.get(Calendar.YEAR);
	 * 
	 * String manholeId = ""; manholeId = "MANH_" + year + "_" +
	 * appConfig.getSeqNbr(49, session);
	 * 
	 * rtn.put("manholeId", manholeId); } catch (Exception e) { sw = new
	 * StringWriter(); e.printStackTrace(new PrintWriter(sw)); exceptionAsString =
	 * sw.toString(); logger.finest("Error in GetManholeCount due to \n " +
	 * exceptionAsString); logger.info("Error in GetManholeCount due to \n " +
	 * exceptionAsString); rtn.put("manholeId", null); } } return rtn;
	 * 
	 * }
	 */

	// to be deleted
	/*
	 * @RequestMapping(value = "/GetHandholeCount", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> GetHandholeCount(Locale locale,
	 * Model model, HttpServletRequest request, HttpServletResponse response) throws
	 * JsonGenerationException, JsonMappingException, IOException {
	 * System.out.println("Passes here"); Map<String, Object> rtn = new
	 * LinkedHashMap<>(); // ObjectMapper mapper = new ObjectMapper(); Session
	 * session = null; Transaction tx = null; session = almsessions.getALMSession();
	 * if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	 * rtn.put("Login", LoginServices.checkSession(request, response)); return rtn;
	 * } if (session != null && session.isOpen()) { tx = session.beginTransaction();
	 * try { Date date = new Date(); Calendar calendar = new GregorianCalendar();
	 * calendar.setTime(date); int year = calendar.get(Calendar.YEAR);
	 * 
	 * String handholeId = ""; handholeId = "HANDH_" + year + "_" +
	 * appConfig.getSeqNbr(50, session);
	 * 
	 * rtn.put("handholeId", handholeId); } catch (Exception e) { sw = new
	 * StringWriter(); e.printStackTrace(new PrintWriter(sw)); exceptionAsString =
	 * sw.toString(); logger.finest("Error in GetHandholeCount due to \n " +
	 * exceptionAsString); logger.info("Error in GetHandholeCount due to \n " +
	 * exceptionAsString); rtn.put("handholeId", null); } } return rtn; }
	 */

	// to be deleted
	/// to be checked
	/*
	 * @RequestMapping(value = "/GetFiberCount", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> GetFiberCount(Locale locale, Model
	 * model, HttpServletRequest request, HttpServletResponse response) throws
	 * JsonGenerationException, JsonMappingException, IOException {
	 * System.out.println("Passes here"); Map<String, Object> rtn = new
	 * LinkedHashMap<>(); // ObjectMapper mapper = new ObjectMapper(); Session
	 * session = null; Transaction tx = null; session = almsessions.getALMSession();
	 * if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	 * rtn.put("Login", LoginServices.checkSession(request, response)); return rtn;
	 * } if (session != null && session.isOpen()) { tx = session.beginTransaction();
	 * try { Date date = new Date(); Calendar calendar = new GregorianCalendar();
	 * calendar.setTime(date); int year = calendar.get(Calendar.YEAR);
	 * 
	 * String fiberId = ""; fiberId = "FIBER" + year + "_" + appConfig.getSeqNbr(51,
	 * session);
	 * 
	 * rtn.put("fiberId", fiberId); } catch (Exception e) { sw = new StringWriter();
	 * e.printStackTrace(new PrintWriter(sw)); exceptionAsString = sw.toString();
	 * logger.finest("Error in GetFiberCount due to \n " + exceptionAsString);
	 * logger.info("Error in GetFiberCount due to \n " + exceptionAsString);
	 * rtn.put("fiberId", null); } } return rtn; }
	 */
	@RequestMapping(value = "/findCountTubes", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountTubes(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Object TubesCount = session.createNativeQuery("SELECT count(*) FROM FIBER_TUBES WHERE FIBER_CABLE_ID='"
						+ request.getParameter("fiberID") + "'").uniqueResult();
				Object cableGeo = session.createNativeQuery(
						"SELECT  coalesce(total_geo_distance,0) FROM FIBER_cables WHERE FIBER_CABLE_ID='"
								+ request.getParameter("fiberID") + "'")
						.uniqueResult();
				Object cableLineOfSite = session
						.createNativeQuery("SELECT coalesce(Length,0) FROM FIBER_cables WHERE FIBER_CABLE_ID='"
								+ request.getParameter("fiberID") + "'")
						.uniqueResult();

				rtn.put("TubesCount", TubesCount);
				rtn.put("cableGeo", cableGeo);
				rtn.put("cableLineOfSite", cableLineOfSite);
				session.clear();
				tx.commit();

			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountTubes due to \n " + exceptionAsString);
				logger.info("Error in findCountTubes due to \n " + exceptionAsString);
				rtn.put("manholeCount", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/findCountStrands", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountStrands(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Object StrandsCount = session
						.createNativeQuery("SELECT count(*) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID='"
								+ request.getParameter("fiberID") + "'")
						.uniqueResult();

				rtn.put("StrandsCount", StrandsCount);
				Object cablegeo = session
						.createNativeQuery("SELECT total_geo_distance FROM FIBER_cables WHERE FIBER_CABLE_ID='"
								+ request.getParameter("fiberID") + "'")
						.getResultList();
				Object cableLineOfSite = session
						.createNativeQuery("SELECT Length FROM FIBER_cables WHERE FIBER_CABLE_ID='"
								+ request.getParameter("fiberID") + "'")
						.getResultList();
				rtn.put("cablegeo", cablegeo);
				rtn.put("cableLineOfSite", cableLineOfSite);

				Object StrandBackboneGeo = session.createNativeQuery(
						"SELECT coalesce(sum(total_geo_distance),0) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_cables where fiber_network_level='backbone')")
						.uniqueResult();
				Object StrandBackboneLine = session.createNativeQuery(
						"SELECT coalesce(sum(Length),0) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_cables where fiber_network_level='backbone')")
						.uniqueResult();
				rtn.put("StrandBackboneGeo", StrandBackboneGeo);
				rtn.put("StrandBackboneLine", StrandBackboneLine);

				Object StrandMetroGeo = session.createNativeQuery(
						"SELECT coalesce(sum(total_geo_distance),0) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_cables where fiber_network_level='metro')")
						.uniqueResult();
				Object StrandMetroLine = session.createNativeQuery(
						"SELECT coalesce(sum(Length),0) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_cables where fiber_network_level='metro')")
						.uniqueResult();
				rtn.put("StrandMetroGeo", StrandMetroGeo);
				rtn.put("StrandMetroLine", StrandMetroLine);

				Object StrandAccessGeo = session.createNativeQuery(
						"SELECT coalesce(sum(total_geo_distance),0) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_cables where fiber_network_level='access')")
						.uniqueResult();
				Object StrandAccessLine = session.createNativeQuery(
						"SELECT coalesce(sum(Length),0) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_cables where fiber_network_level='access')")
						.uniqueResult();
				rtn.put("StrandAccessGeo", StrandAccessGeo);
				rtn.put("StrandAccessLine", StrandAccessLine);

				Object networkLevel = session
						.createNativeQuery("SELECT fiber_network_Level FROM FIBER_Cables WHERE FIBER_CABLE_ID='"
								+ request.getParameter("fiberID") + "'")
						.uniqueResult();

				rtn.put("networkLevel", networkLevel);
				session.clear();
				tx.commit();

			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountStrands due to \n " + exceptionAsString);
				logger.info("Error in findCountStrands due to \n " + exceptionAsString);
				rtn.put("StrandsCount", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/findCountManHoles", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountManHoles(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Object manholeCount = session.createNativeQuery(
						"SELECT count(*) FROM MANHOLE WHERE PROJECT_ID='" + request.getParameter("ProjectId") + "'")
						.uniqueResult();

				rtn.put("manholeCount", manholeCount);
				session.clear();
				tx.commit();

			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountManHoles due to \n " + exceptionAsString);
				logger.info("Error in findCountManHoles due to \n " + exceptionAsString);
				rtn.put("manholeCount", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/findCountDbNetLevel", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountDbNetLevel(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Object totalDBCount = session.createNativeQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD WHERE PROJECT_ID='" + request.getParameter("ProjectId")
								+ "' AND DB_NETWORK_LEVEL = '" + request.getParameter("networkLevel") + "'   ")
						.uniqueResult();
				rtn.put("totalDBCount", totalDBCount);

				Object totalDBMappingCount = session.createNativeQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING A LEFT JOIN distribution_board B ON A.DB_ID = B.DB_ID WHERE "
								+ " B.DB_NETWORK_LEVEL = '" + request.getParameter("networkLevel") + "'   ")
						.uniqueResult();
				rtn.put("totalDBMappingCount", totalDBMappingCount);

				Object countAllPorts = session.createNativeQuery(
						"SELECT SUM (dbSum) FROM ( SELECT DB_ID,SUM(NUM_ROWS*NUM_COLUMNS) as dbSum FROM distribution_board WHERE "
								+ " DB_NETWORK_LEVEL = '" + request.getParameter("networkLevel")
								+ "' GROUP BY DB_ID ) ")
						.uniqueResult();

				rtn.put("countAllPorts", countAllPorts);

				Object countDbActiveFP = session.createNativeQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING A LEFT JOIN distribution_board B ON A.DB_ID = B.DB_ID WHERE "
								+ " B.DB_NETWORK_LEVEL = '" + request.getParameter("networkLevel")
								+ "' AND A.FP_STATUS='Active' ")
						.uniqueResult();
				rtn.put("countDbActiveFP", countDbActiveFP);

				Object countDbInactiveFP = session.createNativeQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING A LEFT JOIN distribution_board B ON A.DB_ID = B.DB_ID WHERE "
								+ " B.DB_NETWORK_LEVEL = '" + request.getParameter("networkLevel")
								+ "' AND A.FP_STATUS='InActive' ")
						.uniqueResult();
				rtn.put("countDbInactiveFP", countDbInactiveFP);

				Object countDbActiveBP = session.createNativeQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING A LEFT JOIN distribution_board B ON A.DB_ID = B.DB_ID WHERE "
								+ " B.DB_NETWORK_LEVEL = '" + request.getParameter("networkLevel")
								+ "' AND A.BP_STATUS='Active' ")
						.uniqueResult();
				rtn.put("countDbActiveBP", countDbActiveBP);

				Object countDbInactiveBP = session.createNativeQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING A LEFT JOIN distribution_board B ON A.DB_ID = B.DB_ID WHERE "
								+ " B.DB_NETWORK_LEVEL = '" + request.getParameter("networkLevel")
								+ "' AND A.BP_STATUS='InActive' ")
						.uniqueResult();
				rtn.put("countDbInactiveBP", countDbInactiveBP);

				/*
				 * Object MetroCount = session
				 * .createNativeQuery("SELECT count(*) FROM DISTRIBUTION_BOARD WHERE PROJECT_ID='"
				 * + request.getParameter("ProjectId") + "' AND DB_NETWORK_LEVEL = 'metro'")
				 * .uniqueResult(); rtn.put("MetroCount", MetroCount);
				 * 
				 * Object AccessCount = session
				 * .createNativeQuery("SELECT count(*) FROM DISTRIBUTION_BOARD WHERE PROJECT_ID='"
				 * + request.getParameter("ProjectId") + "' AND DB_NETWORK_LEVEL = 'access'")
				 * .uniqueResult(); rtn.put("AccessCount", AccessCount);
				 */

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountDbNetLevel due to \n " + exceptionAsString);
				logger.info("Error in findCountDbNetLevel due to \n " + exceptionAsString);
				rtn.put("manholeCount", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/findCountHandHoles", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountHandHoles(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Object handholeCount = session.createNativeQuery(
						"SELECT count(*) FROM HANDHOLE WHERE PROJECT_ID='" + request.getParameter("ProjectId") + "'")
						.uniqueResult();

				rtn.put("handholeCount", handholeCount);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountHandHoles due to \n " + exceptionAsString);
				logger.info("Error in findCountHandHoles due to \n " + exceptionAsString);

				rtn.put("handholeCount", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/findCountDistBoard", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountDistBoard(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				Object CountDistBoard = session
						.createNativeQuery("SELECT count(*) FROM DISTRIBUTION_BOARD WHERE PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult();

				rtn.put("CountDistBoard", CountDistBoard);

				Object CountDistBoardMapping = session
						.createNativeQuery("SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING ").uniqueResult();

				rtn.put("CountDistBoardMapping", CountDistBoardMapping);

				Object countAllPorts = session.createNativeQuery(
						"SELECT SUM (dbSum) FROM ( SELECT DB_ID,SUM(NUM_ROWS*NUM_COLUMNS) as dbSum FROM distribution_board GROUP BY DB_ID ) ")
						.uniqueResult();
				rtn.put("countAllPorts", countAllPorts);

				Object countDbActiveFP = session
						.createNativeQuery("SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING WHERE FP_STATUS='Active' ")
						.uniqueResult();
				rtn.put("countDbActiveFP", countDbActiveFP);

				Object countDbInactiveFP = session
						.createNativeQuery(
								"SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING WHERE FP_STATUS='InActive' ")
						.uniqueResult();
				rtn.put("countDbInactiveFP", countDbInactiveFP);

				Object countDbActiveBP = session
						.createNativeQuery("SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING WHERE BP_STATUS='Active' ")
						.uniqueResult();
				rtn.put("countDbActiveBP", countDbActiveBP);

				Object countDbInactiveBP = session
						.createNativeQuery(
								"SELECT count(*) FROM DISTRIBUTION_BOARD_MAPPING WHERE BP_STATUS='InActive' ")
						.uniqueResult();
				rtn.put("countDbInactiveBP", countDbInactiveBP);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountDistBoard due to \n " + exceptionAsString);
				logger.info("Error in findCountDistBoard due to \n " + exceptionAsString);
				rtn.put("CountDistBoard", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}
	
	@RequestMapping(value = "/findCountJunction", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountJunction(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				Object CountJunction = session
						.createNativeQuery("SELECT count(*) FROM JUNCTION WHERE PROJECT_ID='"
								+ request.getParameter("ProjectId") + "' and PHYSICAL_LAYER_ID is null")
						.uniqueResult();

				rtn.put("CountJunction", CountJunction);

				Object CountJunctionMapping = session
						.createNativeQuery("SELECT count(*) FROM JUNCTION_MAPPING where PHYSICAL_LAYER_ID is null ").uniqueResult();

				rtn.put("CountJunctionMapping", CountJunctionMapping);

				Object countAllConnections = session.createNativeQuery(
						"SELECT nvl(SUM (JctConnectionSum), '0') FROM ( SELECT JUNCTION_ID,SUM(CAPACITY) as JctConnectionSum FROM JUNCTION WHERE PHYSICAL_LAYER_ID is null GROUP BY JUNCTION_ID ) ")
						.uniqueResult();
				rtn.put("countAllConnections", countAllConnections);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountJunction due to \n " + exceptionAsString);
				logger.info("Error in findCountJunction due to \n " + exceptionAsString);
				rtn.put("CountJunction", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/findCountfiber", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountfiber(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				@SuppressWarnings("unchecked")
				List<Object[]> Countfiber = session.createNativeQuery(
						"select 'fiber', count(a.FIBER_CABLE_ID),'tube',coalesce(sum(a.NUMBER_OF_TUBES),0) ,'strand',coalesce(sum(a.NUMBER_OF_STRANDS),0),count(a.FIBER_NETWORK_LEVEL) , sum (total_geo_distance), sum (length) from FIBER_CABLES a WHERE a.PROJECT_ID ='"
								+ request.getParameter("ProjectId") + "' ")
						.getResultList();
				@SuppressWarnings("unchecked")
				List<Object[]> backbone = session.createNativeQuery(
						"select 'fiber', count(FIBER_CABLE_ID),'tube',coalesce(sum(NUMBER_OF_TUBES),0) ,'strand',coalesce(sum(NUMBER_OF_STRANDS),0),count(FIBER_NETWORK_LEVEL) from FIBER_CABLES where FIBER_NETWORK_LEVEL='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.getResultList();

				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerTklBackbone = session.createNativeQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'tkl' and FIBER_NETWORK_LEVEL = 'backbone'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.getResultList();

				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerNofbiBackbone = session.createNativeQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'nofbi' and FIBER_NETWORK_LEVEL = 'backbone'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.getResultList();

				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerOtherBackbone = session.createNativeQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'others' and FIBER_NETWORK_LEVEL = 'backbone'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.getResultList();

				@SuppressWarnings("unchecked")
				List<Object[]> metro = session.createNativeQuery(
						"SELECT 'fiber', count(FIBER_CABLE_ID),'tube',coalesce(sum(NUMBER_OF_TUBES),0) ,'strand',coalesce(sum(NUMBER_OF_STRANDS),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='metro' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.getResultList();

				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerTklMetro = session.createNativeQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'tkl' and FIBER_NETWORK_LEVEL = 'metro'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.getResultList();

				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerNofbiMetro = session.createNativeQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'nofbi' and FIBER_NETWORK_LEVEL = 'metro'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.getResultList();

				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerOtherMetro = session.createNativeQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'others' and FIBER_NETWORK_LEVEL = 'metro'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.getResultList();

				@SuppressWarnings("unchecked")
				List<Object[]> access = session.createNativeQuery(
						"SELECT 'fiber', count(FIBER_CABLE_ID),'tube',coalesce(sum(NUMBER_OF_TUBES),0) ,'strand',coalesce(sum(NUMBER_OF_STRANDS),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='access' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.getResultList();

				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerTklAccess = session.createNativeQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'tkl' and FIBER_NETWORK_LEVEL = 'access'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.getResultList();

				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerNofbiAccess = session.createNativeQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'nofbi' and FIBER_NETWORK_LEVEL = 'access'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.getResultList();

				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerOtherAccess = session.createNativeQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'others' and FIBER_NETWORK_LEVEL = 'access'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.getResultList();

				String Backbone = session.createNativeQuery(
						"SELECT COUNT(*) from FIBER_CABLES where FIBER_NETWORK_LEVEL='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String Metro = session.createNativeQuery(
						"SELECT COUNT(*) from FIBER_CABLES where FIBER_NETWORK_LEVEL='metro' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String Access = session.createNativeQuery(
						"SELECT COUNT(*) from FIBER_CABLES where FIBER_NETWORK_LEVEL='access' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();

				String backbone_total_geo = session.createNativeQuery(
						"SELECT coalesce(sum (total_Geo_Distance),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String backbone_total_LineOfsite = session.createNativeQuery(
						"SELECT coalesce(sum (Length),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String Metro_total_geo = session.createNativeQuery(
						"SELECT coalesce(sum(total_Geo_Distance),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='metro' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String Metro_total_LineOfsite = session.createNativeQuery(
						"SELECT coalesce(sum (Length),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='metro' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String Access_total_geo = session.createNativeQuery(
						"SELECT coalesce(sum(total_Geo_Distance),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='access' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String Access_total_LineOfsite = session.createNativeQuery(
						"SELECT coalesce(sum(Length),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='access' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();

				String totalgeo = session.createNativeQuery(
						"SELECT  coalesce(sum((total_geo_distance*number_of_strands*number_of_tubes)),0) from FIBER_CABLES where PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String totalgeotube = session
						.createNativeQuery("SELECT  coalesce(sum(total_geo_distance),0) from FIBER_tubes ")
						.uniqueResult().toString();
				String totalgeostrand = session
						.createNativeQuery("SELECT  coalesce(sum(total_geo_distance),0) from FIBER_Strands ")
						.uniqueResult().toString();

				String totalLine = session.createNativeQuery(
						"SELECT  coalesce(sum((Length*number_of_strands*number_of_tubes)),0) from FIBER_CABLES where PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String totalLinetube = session.createNativeQuery("SELECT  coalesce(sum(Length),0) from FIBER_tubes ")
						.uniqueResult().toString();
				String totalLinestrand = session
						.createNativeQuery("SELECT  coalesce(sum(Length),0) from FIBER_Strands ").uniqueResult()
						.toString();

				String backboneStrand = session.createNativeQuery(
						"select coalesce(sum((total_geo_distance*number_of_strands*number_of_tubes)),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String tubeStrand = session.createNativeQuery(
						"select coalesce(sum(total_geo_distance),0) from FIBER_tubes where FIBER_cable_id in (select FIBER_cable_id from fiber_cables where FIBER_NETWORK_LEVEL ='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "')")
						.uniqueResult().toString();

				String geoBackboneStrand = session.createNativeQuery(
						"select coalesce(sum(total_geo_distance*number_of_strands),0)  from fiber_cables where FIBER_NETWORK_LEVEL ='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String LengthBackboneStrand = session.createNativeQuery(
						"select coalesce(sum(Length*number_of_strands),0)  from fiber_cables where FIBER_NETWORK_LEVEL ='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();

				String geoMetroStrand = session.createNativeQuery(
						"select coalesce(sum(total_geo_distance*number_of_strands),0)  from fiber_cables where FIBER_NETWORK_LEVEL ='metro' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String LengthMetroStrand = session.createNativeQuery(
						"select coalesce(sum(Length*number_of_strands),0)  from fiber_cables where FIBER_NETWORK_LEVEL ='metro' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();

				String geoAccessStrand = session.createNativeQuery(
						"select coalesce(sum(total_geo_distance*number_of_strands),0)  from fiber_cables where FIBER_NETWORK_LEVEL ='access' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String LengthAccessStrand = session.createNativeQuery(
						"select coalesce(sum(Length*number_of_strands),0)  from fiber_cables where FIBER_NETWORK_LEVEL ='access' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();

				rtn.put("geoBackboneStrand", geoBackboneStrand);
				rtn.put("LengthBackboneStrand", LengthBackboneStrand);

				rtn.put("geoMetroStrand", geoMetroStrand);
				rtn.put("LengthMetroStrand", LengthMetroStrand);

				rtn.put("geoAccessStrand", geoAccessStrand);
				rtn.put("LengthAccessStrand", LengthAccessStrand);

				rtn.put("backboneStrand", backboneStrand);
				rtn.put("tubeStrand", tubeStrand);

				rtn.put("totalgeo", totalgeo);
				rtn.put("totalgeotube", totalgeotube);
				rtn.put("totalgeostrand", totalgeostrand);
				rtn.put("totalLine", totalLine);
				rtn.put("totalLinetube", totalLinetube);
				rtn.put("totalLinestrand", totalLinestrand);

				rtn.put("Countfiber", Countfiber);
				rtn.put("backbone", backbone);
				rtn.put("fiberOwnerTklBackbone", fiberOwnerTklBackbone);
				rtn.put("fiberOwnerNofbiBackbone", fiberOwnerNofbiBackbone);
				rtn.put("fiberOwnerOtherBackbone", fiberOwnerOtherBackbone);
				rtn.put("metro", metro);
				rtn.put("fiberOwnerTklMetro", fiberOwnerTklMetro);
				rtn.put("fiberOwnerNofbiMetro", fiberOwnerNofbiMetro);
				rtn.put("fiberOwnerOtherMetro", fiberOwnerOtherMetro);
				rtn.put("access", access);
				rtn.put("fiberOwnerTklAccess", fiberOwnerTklAccess);
				rtn.put("fiberOwnerNofbiAccess", fiberOwnerNofbiAccess);
				rtn.put("fiberOwnerOtherAccess", fiberOwnerOtherAccess);
				rtn.put("Backbone", Backbone);
				rtn.put("Metro", Metro);
				rtn.put("Access", Access);

				rtn.put("Metro_total_geo", Metro_total_geo);
				rtn.put("Metro_total_LineOfsite", Metro_total_LineOfsite);
				rtn.put("backbone_total_geo", backbone_total_geo);
				rtn.put("backbone_total_LineOfsite", backbone_total_LineOfsite);
				rtn.put("Access_total_geo", Access_total_geo);
				rtn.put("Access_total_LineOfsite", Access_total_LineOfsite);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountfiber due to \n " + exceptionAsString);
				logger.info("Error in findCountfiber due to \n " + exceptionAsString);
				rtn.put("Countfiber", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/findCountTrench", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountTrench(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				@SuppressWarnings("unchecked")
				List<Object[]> countTrench = session.createNativeQuery(
						"select count (*) from TRENCH WHERE PROJECT_ID='" + request.getParameter("ProjectId") + "' ")
						.getResultList();

				rtn.put("countTrench", countTrench);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountTrench due to \n " + exceptionAsString);
				logger.info("Error in findCountTrench due to \n " + exceptionAsString);
				rtn.put("countTrench", null);
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
	@RequestMapping(value = "/auto_FillLongLat", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> auto_FillLongLat(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedID = request.getParameter("selectedID");
			Object[] res_LngLat = null;
			Object Lat = "";
			Object Lng = "";
			System.out.println(" selectedID " + selectedID);
			try {
				if (selectedID.length() > 5) {

					String resultingID = selectedID.substring(0, 4);
					System.out.println(" after being " + resultingID);

					if (resultingID.equals("MANH")) {
						res_LngLat = (Object[]) session
								.createNativeQuery(
										"SELECT LONGITUDE,LATITUDE FROM MANHOLE WHERE MANHOLE_ID='" + selectedID + "'")
								.uniqueResult();
						System.out.println("inside resulting id if");

						if (res_LngLat != null) {

							System.out.println("manhole res not null " + res_LngLat[0]);
							Lat = res_LngLat[1];
							Lng = res_LngLat[0];
							rtn.put("Lat", Lat);
							rtn.put("Lng", Lng);
						}
					} else if (resultingID.equals("HAND")) {

						res_LngLat = (Object[]) session.createNativeQuery(
								"SELECT LONGITUDE,LATITUDE FROM HANDHOLE WHERE HANDHOLE_ID='" + selectedID + "'")
								.uniqueResult();

						if (res_LngLat != null) {

							System.out.println("handhole res not null " + res_LngLat[0]);
							Lat = res_LngLat[1];
							Lng = res_LngLat[0];
							rtn.put("Lat", Lat);
							rtn.put("Lng", Lng);
						}

					}

				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in auto_FillLongLat due to \n " + exceptionAsString);
				logger.info("Error in auto_FillLongLat due to \n " + exceptionAsString);
				rtn.put("Lat", null);
				rtn.put("Lng", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/auto_FillAux_Name", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> auto_FillAux_Name(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		Session session = AlmDbSession.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		Object lat = request.getParameter("lat");
		Object lng = request.getParameter("lng");

		try {
			String auxName = "";

			Object[] res_AuxName = (Object[]) session
					.createNativeQuery("SELECT MANHOLE_ID,MANHOLE_NAME FROM MANHOLE WHERE LONGITUDE='" + lng
							+ "' and LATITUDE='" + lat + "'")
					.uniqueResult();
			System.out.println("inside resulting id if" + res_AuxName);

			if (res_AuxName != null) {

				System.out.println("manhole res not null " + res_AuxName[0]);
				auxName = res_AuxName[1] + ": " + res_AuxName[1];

				rtn.put("auxName", auxName);

			}

			else {
				res_AuxName = (Object[]) session
						.createNativeQuery("SELECT HANDHOLE_ID,HANDHOLE_NAME FROM HANDHOLE WHERE LONGITUDE='" + lng
								+ "' and LATITUDE='" + lat + "'")
						.uniqueResult();
				System.out.println("inside resulting id if" + res_AuxName);

				if (res_AuxName != null) {

					System.out.println("HanDhole res not null " + res_AuxName[0]);
					auxName = res_AuxName[1] + ": " + res_AuxName[1];

					rtn.put("auxName", auxName);

				}

			}

		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in auto_FillAux_Name due to \n " + exceptionAsString);
			logger.info("Error in auto_FillAux_Name due to \n " + exceptionAsString);
			rtn.put("auxName", null);
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();

			}
		}

		return rtn;
	}

	@RequestMapping(value = "/findByLatLng", method = RequestMethod.GET)
	@ResponseBody
	// public Map<String, Object> findByLatLng(Locale locale, Model model,
	// HttpServletRequest request, HttpServletResponse response)
	public Map<String, Object> findByLatLng(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				Object[] res_Type1, res_Type;
				String latSource = request.getParameter("SourceLat");
				String lngSource = request.getParameter("SourceLng");
				String latDestination = request.getParameter("DestinationLat");
				String lngDestination = request.getParameter("DestinationLng");
				String tube_Id, strand_Id;
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(new Date());
				int year = calendar.get(Calendar.YEAR);

				System.out.println("itemParameters.getDictParameter() " + itemParameters.getDictParameter());

				try {
					if (request.getParameter("SourceLat") != null && request.getParameter("SourceLng") != null) {

						res_Type = (Object[]) session.createNativeQuery(
								"SELECT * FROM (SELECT MANHOLE_ID,MANHOLE_NAME,CITY FROM MANHOLE WHERE LONGITUDE like '"
										+ lngSource + "%' and LATITUDE like '" + latSource + "%'"
										+ " union SELECT HANDHOLE_ID,HANDHOLE_NAME,CITY FROM HANDHOLE WHERE LONGITUDE like '"
										+ lngSource + "%' and LATITUDE like '" + latSource + "%'"
										+ " union SELECT DB_ID,DB_NAME,CITY FROM DISTRIBUTION_BOARD WHERE DB_LONGITUDE like '"
										+ lngSource + "%' and DB_LATITUDE like '" + latSource + "%')WHERE ROWNUM=1 ")
								.uniqueResult();

						if (res_Type != null) {
							rtn.put("res_Type", res_Type);

						}
					}
					if (request.getParameter("DestinationLat") != null
							&& request.getParameter("DestinationLng") != null) {
						res_Type1 = (Object[]) session.createNativeQuery(
								"SELECT * FROM( SELECT MANHOLE_ID,MANHOLE_NAME,CITY FROM MANHOLE WHERE LONGITUDE like '"
										+ lngDestination + "%' and LATITUDE like '" + latDestination + "%'"
										+ " union SELECT HANDHOLE_ID,HANDHOLE_NAME,CITY FROM HANDHOLE WHERE LONGITUDE like '"
										+ lngDestination + "%' and LATITUDE like '" + latDestination + "%'"
										+ " union SELECT DB_ID,DB_NAME,CITY FROM DISTRIBUTION_BOARD WHERE DB_LONGITUDE like '"
										+ lngDestination + "%' and DB_LATITUDE like '" + latDestination
										+ "%' )WHERE ROWNUM=1")
								.uniqueResult();

						if (res_Type1 != null) {
							rtn.put("res_Type1", res_Type1);

						}
					}
					List<String> finalList = new ArrayList<String>();

					if (itemParameters.getDictParameter().size() > 0) {
						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
							String aux_Long = (itemParameters.getDictParameter().get(i).get("auxLng"));
							String aux_Lat = (itemParameters.getDictParameter().get(i).get("auxLat"));

							String querySearch = "SELECT * FROM ( ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM MANHOLE) UNION (SELECT MANHOLE_ID,MANHOLE_NAME,city FROM MANHOLE WHERE LONGITUDE like '"
									+ aux_Long + "%' and LATITUDE like '" + aux_Lat
									+ "%' )) UNION ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM MANHOLE) "
									+ " UNION (SELECT HANDHOLE_ID,HANDHOLE_NAME,city FROM HANDHOLE WHERE LONGITUDE like '"
									+ aux_Long + "%' and LATITUDE like '" + aux_Lat
									+ "%' )) UNION ( ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM distribution_board) UNION (SELECT DB_ID,DB_NAME,CITY FROM distribution_board "
									+ " WHERE DB_LONGITUDE like '" + aux_Long + "%' and DB_LATITUDE like '" + aux_Lat
									+ "%' )) ) )WHERE ROWNUM=1";

							/*
							 * String querySearch =
							 * "SELECT MANHOLE_ID,MANHOLE_NAME,city FROM MANHOLE WHERE LONGITUDE like '" +
							 * aux_Long + "%' and LATITUDE like '" + aux_Lat + "%' ";
							 */

							finalList.addAll(session.createNativeQuery(querySearch).getResultList());
						}
					}
					if (finalList != null) {
						rtn.put("auxPtSearch", finalList);

					}
					/*
					 * if (request.getParameter("tube_Id") != "") {
					 * 
					 * tube_Id = "TUBE_" + year + "_" + appConfig.getSeqNbr(47, session);
					 * rtn.put("tube_Id", tube_Id);
					 * 
					 * }
					 * 
					 * if (request.getParameter("strand_Id") != "") {
					 * 
					 * strand_Id = "STRAND_" + year + "_" + appConfig.getSeqNbr(48, session);
					 * rtn.put("strand_Id", strand_Id);
					 * 
					 * }
					 */

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in findByLatLng due to \n " + exceptionAsString);
					logger.info("Error in findByLatLng due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}

			}
			return rtn;
		}
	}

	@RequestMapping(value = "/findSiteAccordingToCity", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findSiteAccordingToCity(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			String city = request.getParameter("city");
			try {
				System.out.println("city: " + city);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findSiteAccordingToCity due to \n " + exceptionAsString);
				logger.info("Error in findSiteAccordingToCity due to \n " + exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/find_TypeDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> find_TypeDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			Object lat = request.getParameter("lat");
			Object lng = request.getParameter("lng");
			String type = "";

			Object[] res_Type = (Object[]) session.createNativeQuery(
					"SELECT MANHOLE_ID,MANHOLE_NAME,MANHOLE_MODEL,CITY FROM MANHOLE WHERE LONGITUDE='" + lng
							+ "' and LATITUDE='" + lat + "'")
					.uniqueResult();

			if (res_Type != null) {

				type = "Manhole";
				System.out.println("manhole res not null " + res_Type[0]);

				String id = res_Type[0].toString();
				rtn.put("id", id);

				if (res_Type[1] != null) {
					String name = res_Type[1].toString();
					rtn.put("name", name);
				}
				if (res_Type[2] != null) {
					String modell = res_Type[2].toString();
					rtn.put("modell", modell);
				}

				if (res_Type[3] != null) {
					String city = res_Type[3].toString();
					rtn.put("city", city);
				}

				rtn.put("type", type);

			}

			else {
				res_Type = (Object[]) session.createNativeQuery(
						"SELECT HANDHOLE_ID,HANDHOLE_NAME,HANDHOLE_MODEL,CITY FROM HANDHOLE WHERE LONGITUDE='" + lng
								+ "' and LATITUDE='" + lat + "'")
						.uniqueResult();

				if (res_Type != null) {

					type = "Handhole";

					System.out.println("handhole res not null " + res_Type[0]);

					String id = res_Type[0].toString();
					rtn.put("id", id);

					if (res_Type[1] != null) {
						String name = res_Type[1].toString();
						rtn.put("name", name);
					}
					if (res_Type[2] != null) {
						String modell = res_Type[2].toString();
						rtn.put("modell", modell);
					}

					if (res_Type[3] != null) {
						String city = res_Type[3].toString();
						rtn.put("city", city);
					}

					rtn.put("type", type);

				}

				else {
					res_Type = (Object[]) session.createNativeQuery(
							"SELECT DB_ID,DB_NAME,MAX_CAPACITY,SITE FROM DISTRIBUTION_BOARD WHERE DB_LONGITUDE='" + lng
									+ "' and DB_LATITUDE='" + lat + "'")
							.uniqueResult();

					if (res_Type != null) {

						type = "distribution_Board";

						System.out.println("distribution board res not null " + res_Type[0]);
						String id = res_Type[0].toString();

						rtn.put("id", id);

						rtn.put("type", type);

						if (res_Type[1] != null) {
							String name = res_Type[1].toString();
							rtn.put("name", name);
						}
						if (res_Type[2] != null) {
							String max_Capacity = res_Type[2].toString();
							rtn.put("max_Capacity", max_Capacity);
						}

						if (res_Type[3] != null) {
							String site = res_Type[3].toString();
							rtn.put("site", site);
						}

					}
				}

			}

			tx.commit();
			session.close();

		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePhysicalLayer", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deletePhysicalLayer(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();

		Query physicalLayerDeleteQuery = null, fiberCableDeleteQuery = null, tubeDeleteQuery = null,
				strandDeleteQuery = null, trenchPathDeleteQuery = null, tubePathDeleteQuery = null;
		Object newCount = null;
		String ipAddress = getIpAddress(request);
		String updateModfUser=request.getParameter("updateModfUser");
		PhysicalLayerActivity PhyAct= new PhysicalLayerActivity();
		Integer phyActID = 0;
		String[] idArray = request.getParameterValues("physicalLayerID[]");
		System.out.println("idArray:" +idArray);
		String physicalLayer = request.getParameter("physicalLayer");
		System.out.println("physicalLayer:" +physicalLayer);
		String NodeID = request.getParameter("NodeId");
		System.out.println("NodeID:" + NodeID);
		String manHandholeID = request.getParameter("manHandholeID");
		String manHandoleName = request.getParameter("manHandoleName");
        List<String> idList = Arrays.asList(idArray);
        System.out.println("idList:" +idList);Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				if (idList != null) {
					phyActID = Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID +" +idList.size());
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();

					if (StringUtils.equalsIgnoreCase(physicalLayer, "Project")) {

						// System.out.println("physicalLayer[]:" +mapper.writeValueAsString(idList));

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from PROJECT b where b.PROJECT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from MANHOLE b where b.PROJECT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from HANDHOLE b where b.PROJECT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createNativeQuery(
								"delete from JUNCTION_MAPPING b where b.PHYSICAL_LAYER_ID IN (select PHYSICAL_LAYER_ID from JUNCTION where PROJECT_ID IN (:param1))");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from JUNCTION b where b.PROJECT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						tubeDeleteQuery = session.createNativeQuery(
								"delete from FIBER_TUBES b where b.FIBER_CABLE_ID IN (select FIBER_CABLE_ID from FIBER_CABLES where PROJECT_ID IN (:param1))");
						tubeDeleteQuery.setParameter("param1", idList);
						tubeDeleteQuery.executeUpdate();

						strandDeleteQuery = session.createNativeQuery(
								"delete from FIBER_STRANDS b where b.FIBER_CABLE_ID IN (select FIBER_CABLE_ID from FIBER_CABLES where PROJECT_ID IN (:param1))");
						strandDeleteQuery.setParameter("param1", idList);
						strandDeleteQuery.executeUpdate();

						fiberCableDeleteQuery = session
								.createNativeQuery("delete from FIBER_CABLES b where b.PROJECT_ID IN (:param1)");
						fiberCableDeleteQuery.setParameter("param1", idList);
						fiberCableDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from distribution_board b where b.PROJECT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createNativeQuery(
								"delete from DUCT_AUXILIARY_POINTS b where b.DUCT_ID  IN (select DUCT_ID from DUCTS where TRENCH_ID IN (select TRENCH_ID from TRENCH where PROJECT_ID IN (:param1)))");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createNativeQuery(
								"delete from DUCTS b where b.TRENCH_ID IN (select TRENCH_ID from TRENCH where PROJECT_ID IN (:param1))");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createNativeQuery(
								"delete from TRENCH_AUXILIARY_POINTS b where b.TRENCH_ID  IN (select TRENCH_ID from TRENCH where PROJECT_ID IN (:param1))");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from TRENCH b where b.PROJECT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						newCount = session.createNativeQuery("SELECT count(*) FROM PROJECT").uniqueResult();
						rtn.put("newCount", newCount);
						for (int i = 0; i < idList.size(); i++) {
							
											
																PhyAct= new PhysicalLayerActivity();
																PhyAct.setPhyActID("PHY_ACT_" + year + "_"+ (phyActID+i));
																PhyAct.setElementID(idList.get(i));
																PhyAct.setScreenName("Project");
																PhyAct.setUsername(updateModfUser);
																PhyAct.setUserIP(ipAddress);
																PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
																PhyAct.setActivityDescription("Delete Project");
																session.saveOrUpdate(PhyAct);
																	
					}
					}

					if (StringUtils.equalsIgnoreCase(physicalLayer, "Manhole")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllManholes")) {
						
						
								
						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from MANHOLE b where b.manhole_id IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from JUNCTION b where b.PHYSICAL_LAYER_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createNativeQuery(
								"delete from JUNCTION_MAPPING b where b.PHYSICAL_LAYER_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						newCount = session
								.createNativeQuery("SELECT count(*) FROM MANHOLE where PROJECT_ID ='" + NodeID + "' ")
								.uniqueResult();
						rtn.put("newCount", newCount);
						
/*						phyActID = Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID +" +idList.size());
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
*/						

						/*String PhyActID=
								 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate(); */
						
						//String PhyActID;

						
						for (int i = 0; i < idList.size(); i++) {
							
/*							String PhyActID=
									 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
									query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate(); */
							
									PhyAct= new PhysicalLayerActivity();
									PhyAct.setPhyActID("PHY_ACT_" + year + "_"+ (phyActID+i));
									PhyAct.setElementID(idList.get(i));
									PhyAct.setScreenName("Manhole");
									PhyAct.setUsername(updateModfUser);
									PhyAct.setUserIP(ipAddress);
									PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
									PhyAct.setActivityDescription("Delete Element");
									session.saveOrUpdate(PhyAct);
//									session.createNativeQuery("commit").executeUpdate();
//									session.clear();
							}
					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "Handhole")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllHandholes")) {

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from HANDHOLE b where b.handhole_id IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from JUNCTION b where b.PHYSICAL_LAYER_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createNativeQuery(
								"delete from JUNCTION_MAPPING b where b.PHYSICAL_LAYER_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						newCount = session
								.createNativeQuery("SELECT count(*) FROM HANDHOLE where  PROJECT_ID ='" + NodeID + "' ")
								.uniqueResult();
						rtn.put("newCount", newCount);
						
                          for (int i = 0; i < idList.size(); i++) {
							
/*							String PhyActID=
									 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
									query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate();
*/									
									
									PhyAct= new PhysicalLayerActivity();
									PhyAct.setPhyActID("PHY_ACT_" + year + "_"+ (phyActID+i));
									PhyAct.setElementID(idList.get(i));
									PhyAct.setScreenName("Handhole");
									PhyAct.setUsername(updateModfUser);
									PhyAct.setUserIP(ipAddress);
									PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
									PhyAct.setActivityDescription("Delete Element");
									session.saveOrUpdate(PhyAct);
							}
					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "Junction")) {
						System.out.println("physicalLayer:" +physicalLayer);
						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from JUNCTION b where b.JUNCTION_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from JUNCTION_MAPPING b where b.JCT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();
                        for (int i = 0; i < idList.size(); i++) {
							
/*							String PhyActID=
									 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
									query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate();
*/									
									
									PhyAct.setPhyActID("PHY_ACT_" + year + "_"+ (phyActID+i));
									PhyAct.setElementID(idList.get(i));
									PhyAct.setScreenName("Junction");
									PhyAct.setUsername(updateModfUser);
									PhyAct.setUserIP(ipAddress);
									PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
									PhyAct.setActivityDescription("Delete Element");
									session.saveOrUpdate(PhyAct);
							}
						query = session.createNativeQuery(
								"SELECT count(*) FROM JUNCTION b WHERE PHYSICAL_LAYER_ID = '" + manHandholeID + "' ");
						String countJunc = query.getSingleResult().toString();
                        if(manHandholeID != null) {
						if (StringUtils.equalsIgnoreCase(countJunc, "0")) {
							manHandoleName = manHandoleName.substring(0, manHandoleName.length() - 2);
							System.out.println("manHandoleName" + manHandoleName);

							query = session.createNativeQuery("UPDATE MANHOLE SET MANHOLE_NAME= '" + manHandoleName
									+ "' where MANHOLE_ID='" + manHandholeID + "' ");
							query.executeUpdate();
							query = session.createNativeQuery("UPDATE HANDHOLE SET HANDHOLE_NAME= '" + manHandoleName
									+ "' where HANDHOLE_ID='" + manHandholeID + "' ");
							query.executeUpdate();

						}
                        }
						rtn.put("ManHandholeNewName", manHandoleName);

						newCount = session.createNativeQuery(
								"SELECT count(*) FROM JUNCTION where  PHYSICAL_LAYER_ID ='" + manHandoleName + "' ")
								.uniqueResult();
						rtn.put("ManholeCount", newCount);
						// newCount = session
						// .createNativeQuery("SELECT count(*) FROM HANDHOLE where PROJECT_ID ='" +
						// NodeID
						// + "' ")
						// .uniqueResult();
						// rtn.put("HandholeCount", newCount);

					} else if ( StringUtils.equalsIgnoreCase(physicalLayer, "JUNCTION")) {
						System.out.println("ONLY JUNCTION" );

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from JUNCTION b where b.JUNCTION_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from JUNCTION_MAPPING b where b.JCT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						query = session.createNativeQuery(
								"SELECT count(*) FROM JUNCTION b WHERE PHYSICAL_LAYER_ID IS NULL ");
						String countJunc = query.getSingleResult().toString();
                        

					
					}
					else if (StringUtils.equalsIgnoreCase(physicalLayer, "DistBoard")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllDistBoards")) {

						physicalLayerDeleteQuery = session
								.createNativeQuery("delete from distribution_board b where b.DB_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createNativeQuery(
								"delete from distribution_board_mapping b  where b.DB_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameter("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						newCount = session
								.createNativeQuery(
										"SELECT count(*) FROM distribution_board where  PROJECT_ID ='" + NodeID + "' ")
								.uniqueResult();
						rtn.put("newCount", newCount);
						
						 for (int i = 0; i < idList.size(); i++) {
								
				 				PhyAct= new PhysicalLayerActivity();									
				 			   PhyAct.setPhyActID("PHY_ACT_" + year + "_"+ (phyActID+i));
				 			PhyAct.setElementID(idList.get(i));
				 			PhyAct.setScreenName("Distribution Board");
				 			PhyAct.setUsername(updateModfUser);
				 			PhyAct.setUserIP(ipAddress);
				 			PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
				 			PhyAct.setActivityDescription("Delete Element");
				 									session.saveOrUpdate(PhyAct);
				 							}
			

					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "FiberCable")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllFiberCables")) {

						fiberCableDeleteQuery = session
								.createNativeQuery("delete from FIBER_CABLES b where b.FIBER_CABLE_ID IN (:param1)");
						fiberCableDeleteQuery.setParameter("param1", idList);
						fiberCableDeleteQuery.executeUpdate();

						tubeDeleteQuery = session
								.createNativeQuery("delete from FIBER_TUBES b where b.FIBER_CABLE_ID IN (:param1)");
						tubeDeleteQuery.setParameter("param1", idList);
						tubeDeleteQuery.executeUpdate();

						strandDeleteQuery = session
								.createNativeQuery("delete from FIBER_STRANDS b where b.FIBER_CABLE_ID IN (:param1)");
						strandDeleteQuery.setParameter("param1", idList);
						strandDeleteQuery.executeUpdate();

						fiberCableDeleteQuery = session.createNativeQuery(
								"delete from fiber_auxiliary_points b where b.fiber_cable_id IN (:param1)");
						fiberCableDeleteQuery.setParameter("param1", idList);
						fiberCableDeleteQuery.executeUpdate();

						tubeDeleteQuery = session.createNativeQuery(
								"delete from TUBE_AUXILIARY_POINTS b where b.fiber_cable_id IN (:param1)");
						tubeDeleteQuery.setParameter("param1", idList);
						tubeDeleteQuery.executeUpdate();

						strandDeleteQuery = session.createNativeQuery(
								"delete from strand_auxiliary_points b where b.fiber_cable_id IN (:param1)");
						strandDeleteQuery.setParameter("param1", idList);
						strandDeleteQuery.executeUpdate();

						List<Object[]> Countfiber = session.createNativeQuery(
								"select 'fiber', count(a.FIBER_CABLE_ID),'tube', sum(a.NUMBER_OF_TUBES) ,'strand', sum(a.NUMBER_OF_STRANDS) from FIBER_CABLES a WHERE a.PROJECT_ID ='"
										+ NodeID + "' ")
								.getResultList();

						rtn.put("newCount", Countfiber);
                       for (int i = 0; i < idList.size(); i++) {
							
/*							String PhyActID=
									 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
									query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate();
*/									
									
									PhyAct= new PhysicalLayerActivity();									
									PhyAct.setPhyActID("PHY_ACT_" + year + "_"+ (phyActID+i));
									PhyAct.setElementID(idList.get(i));
									PhyAct.setScreenName("Fiber Cable");
									PhyAct.setUsername(updateModfUser);
									PhyAct.setUserIP(ipAddress);
									PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
									PhyAct.setActivityDescription("Delete Element");
									session.saveOrUpdate(PhyAct);
							}
					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "FiberTube")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllFiberTubes")) {

						strandDeleteQuery = session.createNativeQuery(
								"delete from strand_auxiliary_points b where b.STRAND_ID IN (select strand_id from fiber_strands where TUBE_ID IN (:param1) )");
						strandDeleteQuery.setParameter("param1", idList);
						strandDeleteQuery.executeUpdate();

						tubeDeleteQuery = session
								.createNativeQuery("delete from FIBER_TUBES b where b.TUBE_ID IN (:param1)");
						tubeDeleteQuery.setParameter("param1", idList);
						tubeDeleteQuery.executeUpdate();

						strandDeleteQuery = session
								.createNativeQuery("delete from FIBER_STRANDS b where b.TUBE_ID IN (:param1)");
						strandDeleteQuery.setParameter("param1", idList);
						strandDeleteQuery.executeUpdate();

						tubeDeleteQuery = session
								.createNativeQuery("delete from TUBE_AUXILIARY_POINTS b where b.TUBE_ID IN (:param1)");
						tubeDeleteQuery.setParameter("param1", idList);
						tubeDeleteQuery.executeUpdate();

						List<Object[]> Countfiber = session.createNativeQuery(
								"select 'fiber', count(a.FIBER_CABLE_ID),'tube', sum(a.NUMBER_OF_TUBES) ,'strand', sum(a.NUMBER_OF_STRANDS) from FIBER_CABLES a WHERE a.PROJECT_ID ='"
										+ NodeID + "' ")
								.getResultList();
						rtn.put("newCount", Countfiber);
						  for (int i = 0; i < idList.size(); i++) {
								
/*								String PhyActID=
										 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
										query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
										query.executeUpdate();
										session.createNativeQuery("commit").executeUpdate();
*/										
										
										PhyAct= new PhysicalLayerActivity();									
										PhyAct.setPhyActID("PHY_ACT_" + year + "_"+ (phyActID+i));
										PhyAct.setElementID(idList.get(i));
										PhyAct.setScreenName("Fiber Tube");
										PhyAct.setUsername(updateModfUser);
										PhyAct.setUserIP(ipAddress);
										PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
										PhyAct.setActivityDescription("Delete Element");
										session.saveOrUpdate(PhyAct);
								}
					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "FiberStrand")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllFiberStrands")) {

						strandDeleteQuery = session
								.createNativeQuery("delete from FIBER_STRANDS b where b.STRAND_ID IN (:param1)");
						strandDeleteQuery.setParameter("param1", idList);
						strandDeleteQuery.executeUpdate();

						strandDeleteQuery = session.createNativeQuery(
								"delete from strand_auxiliary_points b where b.STRAND_ID IN (:param1)");
						strandDeleteQuery.setParameter("param1", idList);
						strandDeleteQuery.executeUpdate();

						List<Object[]> Countfiber = session.createNativeQuery(
								"select 'fiber', count(a.FIBER_CABLE_ID),'tube', sum(a.NUMBER_OF_TUBES) ,'strand', sum(a.NUMBER_OF_STRANDS) from FIBER_CABLES a WHERE a.PROJECT_ID ='"
										+ NodeID + "' ")
								.getResultList();

						rtn.put("newCount", Countfiber);
						for (int i = 0; i < idList.size(); i++) {
							
					           PhyAct= new PhysicalLayerActivity();
					           PhyAct.setPhyActID("PHY_ACT_" + year + "_"+ (phyActID+i));
							   PhyAct.setElementID(idList.get(i));
							   PhyAct.setScreenName("Fiber Strand");
							   PhyAct.setUsername(updateModfUser);
							   PhyAct.setUserIP(ipAddress);
							   PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
							   PhyAct.setActivityDescription("Delete Element");
							   session.saveOrUpdate(PhyAct);
//																	session.createNativeQuery("commit").executeUpdate();
//																	session.clear();
															}

					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "Trench")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllTrenches")) {
						trenchPathDeleteQuery = session
								.createNativeQuery("delete from TRENCH b where b.TRENCH_ID IN (:param1)");
						trenchPathDeleteQuery.setParameter("param1", idList);
						trenchPathDeleteQuery.executeUpdate();

						trenchPathDeleteQuery = session.createNativeQuery(
								"delete from TRENCH_AUXILIARY_POINTS b where b.TRENCH_ID IN (:param1)");
						trenchPathDeleteQuery.setParameter("param1", idList);
						trenchPathDeleteQuery.executeUpdate();

						tubePathDeleteQuery = session.createNativeQuery(
								"delete from DUCT_AUXILIARY_POINTS b where b.DUCT_ID IN (select DUCT_ID from DUCTS where TRENCH_ID IN (:param1))");
						tubePathDeleteQuery.setParameter("param1", idList);
						tubePathDeleteQuery.executeUpdate();

						tubePathDeleteQuery = session
								.createNativeQuery("delete from DUCTS b where b.TRENCH_ID IN (:param1)");
						tubePathDeleteQuery.setParameter("param1", idList);
						tubePathDeleteQuery.executeUpdate();

						newCount = session
								.createNativeQuery("SELECT count(*) FROM TRENCH where  PROJECT_ID ='" + NodeID + "' ")
								.uniqueResult();
						rtn.put("newCount", newCount);
					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "trenchDucts")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "DUCT")) {

						tubePathDeleteQuery = session
								.createNativeQuery("delete from DUCTS b where b.DUCT_ID IN (:param1)");
						tubePathDeleteQuery.setParameter("param1", idList);
						tubePathDeleteQuery.executeUpdate();

						tubePathDeleteQuery = session
								.createNativeQuery("delete from DUCT_AUXILIARY_POINTS b where b.DUCT_ID IN (:param1)");
						tubePathDeleteQuery.setParameter("param1", idList);
						tubePathDeleteQuery.executeUpdate();

						newCount = session.createNativeQuery(
								"SELECT count(*) FROM DUCTS where TRENCH_ID IN (select TRENCH_ID from TRENCH where  PROJECT_ID ='"
										+ NodeID + "' )")
								.uniqueResult();
						rtn.put("newCount", newCount);

					}

				} else {
					if (StringUtils.equalsIgnoreCase(physicalLayer, "FiberCable")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllFiberCables")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "FiberTube")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllFiberTubes")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "FiberStrand")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllFiberStrands")) {

						List<Object[]> Countfiber = session.createNativeQuery(
								"select 'fiber', count(a.FIBER_CABLE_ID),'tube', sum(a.NUMBER_OF_TUBES) ,'strand', sum(a.NUMBER_OF_STRANDS) from FIBER_CABLES a WHERE a.PROJECT_ID ='"
										+ NodeID + "' ")
								.getResultList();
						rtn.put("newCount", Countfiber);
					} else {

						rtn.put("newCount", "0");
					}
				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in deletePhysicalLayer due to \n " + exceptionAsString);
				logger.info("Error in deletePhysicalLayer due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/fiberPathSrcToDst", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> fiberPathSrcToDst(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				query = session.createNativeQuery(
						"SELECT FIBER_CABLE_ID,FIBER_CABLE_NAME,SOURCE_CITY,SOURCE_LAT,SOURCE_LNG,DESTINATION_CITY,DESTINATION_LAT,DESTINATION_LNG FROM FIBER_CABLES");

				rtn.put("fiberPathSrcDst", query.getResultList());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in fiberPathSrcToDst due to \n " + exceptionAsString);
				logger.info("Error in fiberPathSrcToDst due to \n " + exceptionAsString);
				rtn.put("fiberPathSrcDst", null);

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
	@RequestMapping(value = "/saveFiberStrand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveFiberStrand(Locale locale, Model model,
			@ModelAttribute ItemParameters itemParameters, HttpServletRequest request, HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				PhysicalLayerActivity PhyAct=new PhysicalLayerActivity();
				String updateModfUser =request.getParameter("updateModfUser");
				String ipAddress = getIpAddress(request);

				String strandID = request.getParameter("strandId");
				PhyAct.setElementID(strandID);
				String PhyActID=
						 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						
						PhyAct.setPhyActID(PhyActID);
						PhyAct.setScreenName("Fiber Strand");
						PhyAct.setUsername(updateModfUser);
						PhyAct.setUserIP(ipAddress);
						PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
			
				if (StringUtils.equalsIgnoreCase(strandID, "")) {
					synchronized (this) {
						// strandID = "STRAND" + year + "_" + appConfig.getSeqNbr(48,session);
						strandID = "STRAND" + year + "_" + Integer.parseInt(session
								.createNativeQuery("SELECT FIBER_STRAND FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET FIBER_STRAND = FIBER_STRAND + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						PhyAct.setElementID(strandID);
						PhyAct.setActivityDescription("Add New Element");
					

					}
				}
				else {
					
					PhyAct.setActivityDescription("Edit Existing Element");
					
				}
				String strandName = request.getParameter("strandName");
				String fiberCableId = request.getParameter("fiberCableId");
				String tubeID = request.getParameter("tubeId");
				String sourceLng = request.getParameter("sourceLng");
				String sourceLat = request.getParameter("sourceLat");
				String destinationLng = request.getParameter("destinationLng");
				String destinationLat = request.getParameter("destinationLat");

				String source = request.getParameter("Source");
				String destination = request.getParameter("Destination");

				String sourceCity = request.getParameter("sourceCity");
				String destinationCity = request.getParameter("destinationCity");

				String strandType = request.getParameter("strandType");
				String strandDeployment = request.getParameter("strandDepl");
				String strandNetworklevel = request.getParameter("strandNetworkLevel");
				String strandOwner = request.getParameter("strandOwner");
				String fiberStrandCreatedByUser = request.getParameter("fiberStrandCreatedByUser");
				String fiberStrandModifiedByUser = request.getParameter("fiberStrandModifiedByUser");
				String strandAuxFlag = request.getParameter("strandAuxFlag");
				String drawingtype = request.getParameter("drawingType");

				String strandNumber = request.getParameter("strandNumber");
				String strandColor = request.getParameter("strandColor");

				Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

				String strandCreatedDate = request.getParameter("strandCreatedDate");
				Timestamp strandCreationDate;
				if (StringUtils.equalsIgnoreCase(strandCreatedDate, "")) {
					strandCreationDate = lastModifiedDate;

				} else {
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					strandCreationDate = new Timestamp(
							formatter.parse(request.getParameter("strandCreatedDate")).getTime());
				}

				Double drivingDistance, geoDistance;
				float totalDrivingDistance = 0;
				float totalGeoDistance = 0;
				float lastAuxToDestDistance = 0;
				float lastAuxToDestDrivDistance = 0;
				float strandLength = (float) 0;

				if (request.getParameter("strandLength") != "") {
					strandLength = Float.parseFloat(request.getParameter("strandLength"));
				}
				if (request.getParameter("totalDrivingDistance") != "") {
					totalDrivingDistance = Float.parseFloat(request.getParameter("totalDrivingDistance"));
				}

				if (request.getParameter("totalGeoDistance") != "") {
					totalGeoDistance = Float.parseFloat(request.getParameter("totalGeoDistance"));
				}
				if (request.getParameter("lastAuxToDestDistance") != "") {
					lastAuxToDestDistance = Float.parseFloat(request.getParameter("lastAuxToDestDistance"));
				}
				if (request.getParameter("lastAuxToDestDrivDistance") != "") {
					lastAuxToDestDrivDistance = Float.parseFloat(request.getParameter("lastAuxToDestDrivDistance"));
				}

				FiberStrands fiberStrand = new FiberStrands();

				fiberStrand.setStrandID(strandID);
				fiberStrand.setFibercableID(fiberCableId);
				fiberStrand.setTubeID(tubeID);
				fiberStrand.setSrcLong(sourceLng);
				fiberStrand.setSrcLat(sourceLat);
				fiberStrand.setDestLong(destinationLng);
				fiberStrand.setDestLat(destinationLat);
				fiberStrand.setStrandNumber(strandNumber);
				fiberStrand.setStrandColor(strandColor);

				if (source.contains("WARE") == true) {
					fiberStrand.setSourceWareId(source.split(":")[0]);
					fiberStrand.setSourceId(source.split(":")[2]);
					fiberStrand.setSourceName(source.split(":")[1]);
				}

				else if (source.contains("CUST") == true) {
					fiberStrand.setSourceWareId("null");
					fiberStrand.setSourceId(source.split(":")[0]);
					fiberStrand.setSourceName(source.split(":")[1]);
				} else if (source.contains("MH") == true || source.contains("HH") == true
						|| source.contains("DB") == true) {
					fiberStrand.setSourceWareId("null");
					fiberStrand.setSourceId(source.split(":")[0]);
					fiberStrand.setSourceName(source.split(":")[1]);
				} else {
					fiberStrand.setSourceWareId("null");
					fiberStrand.setSourceId("null");
					fiberStrand.setSourceName(source);
				}

				if (destination.contains("WARE") == true) {
					fiberStrand.setDestinationWareId(destination.split(":")[0]);
					fiberStrand.setDestinationId(destination.split(":")[2]);
					fiberStrand.setDestinationName(destination.split(":")[1]);
				}

				else if (destination.contains("CUST") == true) {
					fiberStrand.setDestinationWareId("null");
					fiberStrand.setDestinationId(destination.split(":")[0]);
					fiberStrand.setDestinationName(destination.split(":")[1]);
				} else if (destination.contains("MH") == true || destination.contains("HH") == true
						|| destination.contains("DB") == true) {
					fiberStrand.setDestinationWareId("null");
					fiberStrand.setDestinationId(destination.split(":")[0]);
					fiberStrand.setDestinationName(destination.split(":")[1]);
				} else {
					fiberStrand.setDestinationWareId("null");
					fiberStrand.setDestinationId("null");
					fiberStrand.setDestinationName(destination);
				}

				fiberStrand.setStrandName(strandName);
				fiberStrand.setStrandType(strandType);
				fiberStrand.setStrandDeployment(strandDeployment);
				fiberStrand.setStrandfiberNetLevel(strandNetworklevel);
				fiberStrand.setStrandOwner(strandOwner);
				fiberStrand.setCreationDate(strandCreationDate);
				fiberStrand.setLastModifieddate(lastModifiedDate);
				fiberStrand.setCreatedBy(fiberStrandCreatedByUser);
				fiberStrand.setLastmodifiedBy(fiberStrandModifiedByUser);
				fiberStrand.setLastmodifiedBy(fiberStrandModifiedByUser);
				fiberStrand.setSrcCity(sourceCity);
				fiberStrand.setDestCity(destinationCity);
				fiberStrand.setTotaldriving(totalDrivingDistance);
				fiberStrand.setTotalGeoDist(totalGeoDistance);
				fiberStrand.setStrandlength(strandLength);
				fiberStrand.setDrawingtype(drawingtype);
				fiberStrand.setLastAuxToDestDistance(lastAuxToDestDistance);
				fiberStrand.setLastAuxToDestDrivDistance(lastAuxToDestDrivDistance);

				session.saveOrUpdate(fiberStrand);
				session.flush();
				session.clear();
				session.saveOrUpdate(PhyAct);
				int auxArraySize = 0;
				if (StringUtils.equalsIgnoreCase(strandAuxFlag, "opened")
						|| StringUtils.equalsIgnoreCase(strandAuxFlag, "new strand")) {
					query = session.createNativeQuery(
							"delete from STRAND_AUXILIARY_POINTS where STRAND_ID='" + strandID + "'");
					query.executeUpdate();

					if (itemParameters.getDictParameter().size() > 0) {
						if (itemParameters.getDictParameter().size() > 1500) {
							auxArraySize = 1500;
						} else {
							auxArraySize = itemParameters.getDictParameter().size();
						}
						for (int i = 0; i < auxArraySize; i++) {

							Float aux_Long = Float
									.parseFloat(itemParameters.getDictParameter().get(i).get("aux_Longitude"));
							Float aux_Lat = Float
									.parseFloat(itemParameters.getDictParameter().get(i).get("aux_Latitude"));
							Float distance = Float
									.parseFloat(itemParameters.getDictParameter().get(i).get("distance_From_Source"));

							// Float aux_seqSorting = Float
							// .parseFloat(itemParameters.getDictParameter().get(i).get("aux_seqSorting"));
							String aux_ID;
							synchronized (this) {
								// String aux_ID = "AUXILIARY_STRAND_PT_" + year + "_" +
								// appConfig.getSeqNbr(57,session);
								aux_ID = "AUXILIARY_STRAND_PT_" + year + "_"
										+ Integer.parseInt(
												session.createNativeQuery("SELECT FIBER_STRAND_AUX FROM SEQ_TABLE")
														.uniqueResult().toString());
								query = session.createNativeQuery(
										"UPDATE SEQ_TABLE SET FIBER_STRAND_AUX = FIBER_STRAND_AUX + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
							}
							drivingDistance = itemParameters.getDictParameter().get(i).get("drivingDistance") == "" ? 0
									: itemParameters.getDictParameter().get(i).get("drivingDistance") == null ? 0
											: StringUtils.equalsIgnoreCase(
													itemParameters.getDictParameter().get(i).get("drivingDistance"),
													"null") ? 0
															: Double.parseDouble(itemParameters.getDictParameter()
																	.get(i).get("drivingDistance"));
							geoDistance = itemParameters.getDictParameter().get(i).get("geoDistance") == "" ? 0
									: itemParameters.getDictParameter().get(i).get("geoDistance") == null ? 0
											: StringUtils.equalsIgnoreCase(
													itemParameters.getDictParameter().get(i).get("geoDistance"), "null")
															? 0
															: Double.parseDouble(itemParameters.getDictParameter()
																	.get(i).get("geoDistance"));

							StrandAuxPoints fiberAuxstrands = new StrandAuxPoints();
							fiberAuxstrands.setAuxID(aux_ID);
							fiberAuxstrands.setFibercableID(fiberCableId);
							fiberAuxstrands.setStrandID(strandID);
							fiberAuxstrands.setLong(aux_Long);
							fiberAuxstrands.setLat(aux_Lat);
							fiberAuxstrands.setDistancefromsource(distance);

							if (itemParameters.getDictParameter().get(i).get("aux_Name").contains("WARE") == true) {

								fiberAuxstrands.setWareID(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[0]);
								fiberAuxstrands.setAuxPointID(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[2]);
								fiberAuxstrands.setAuxPointName(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[1]);
							} else if (itemParameters.getDictParameter().get(i).get("aux_Name")
									.contains("Auxiliary_Point") == true) {
								fiberAuxstrands.setWareID("null");
								fiberAuxstrands.setAuxPointID("null");
								if (itemParameters.getDictParameter().get(i).get("aux_Name")
										.contains("AUXILIARY_STRAND_PT") == true) {
									fiberAuxstrands.setAuxPointName(
											itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[1]);
								} else {
									fiberAuxstrands
											.setAuxPointName(itemParameters.getDictParameter().get(i).get("aux_Name"));
								}
							} else if (itemParameters.getDictParameter().get(i).get("aux_Name").contains("MH") == true
									|| itemParameters.getDictParameter().get(i).get("aux_Name").contains("HH") == true
									|| itemParameters.getDictParameter().get(i).get("aux_Name")
											.contains("DB") == true) {
								fiberAuxstrands.setWareID("null");
								fiberAuxstrands.setAuxPointID(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[0]);
								fiberAuxstrands.setAuxPointName(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[1]);
							} else {
								fiberAuxstrands.setWareID("null");
								fiberAuxstrands.setAuxPointID("null");
								fiberAuxstrands
										.setAuxPointName(itemParameters.getDictParameter().get(i).get("aux_Name"));
							}
							fiberAuxstrands.setCreationDate(strandCreationDate);
							fiberAuxstrands.setLastModifieddate(lastModifiedDate);
							fiberAuxstrands
									.setSeqSorting(itemParameters.getDictParameter().get(i).get("aux_seqSorting"));
							fiberAuxstrands.setDrivingDist(drivingDistance);
							fiberAuxstrands.setGeoDist(geoDistance);

							session.saveOrUpdate(fiberAuxstrands);
							session.flush();
							session.clear();

						}
					}

				}
				List<Object[]> strandsAuxiliaries = session.createNativeQuery(
						"SELECT STRAND_ID,LONGITUDE,LATITUDE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,DISTANCE_FROM_SOURCE,SEQ_SORTING,AUXILIARY_ID,DRIVING_DISTANCE,GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS WHERE STRAND_ID='"
								+ strandID + "' ORDER BY SEQ_SORTING ASC ")
						.getResultList();

				rtn.put("strandsAuxiliaries", strandsAuxiliaries);
				rtn.put("StrandID", strandID);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in saveFiberStrand due to \n " + exceptionAsString);
				logger.info("Error in saveFiberStrand due to \n " + exceptionAsString);
				rtn.put("StrandID", null);

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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveFiberTube", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveFiberTube(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			try {

				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				FiberTubes fiberTubes;
				TubeAuxPoints fiberAuxtubes;

				String tubeID = request.getParameter("tubeId");
				String ipAddress = getIpAddress(request);
				String updateModfUser=request.getParameter("updateModfUser");
				PhysicalLayerActivity PhyAct= new PhysicalLayerActivity();

				String PhyActID=
						 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						
						PhyAct.setPhyActID(PhyActID);
						PhyAct.setScreenName("Fiber Tube");
						PhyAct.setUsername(updateModfUser);
						PhyAct.setUserIP(ipAddress);
						PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
						
				if (StringUtils.equalsIgnoreCase(tubeID, "")) {
					synchronized (this) {
						// tubeID = "TUBE_" + year + "_" + appConfig.getSeqNbr(47,session);
						tubeID = "TUBE_" + year + "_" + Integer.parseInt(session
								.createNativeQuery("SELECT FIBER_TUBE FROM SEQ_TABLE").uniqueResult().toString());
						System.out.println("the tube id is " + tubeID);
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET FIBER_TUBE = FIBER_TUBE + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						PhyAct.setActivityDescription("Add New Element");
				
					}
					
				}
				else {
					PhyAct.setActivityDescription("Edit Existing Element");
				}
				String strandsCount = null;
				String tubeName = request.getParameter("tubeName");
				String fiberCableId = request.getParameter("fiberCableId");
				String sourceLng = request.getParameter("sourceLng");
				String sourceLat = request.getParameter("sourceLat");
				String destinationLng = request.getParameter("destinationLng");
				String destinationLat = request.getParameter("destinationLat");
				String source = request.getParameter("Source");
				String destination = request.getParameter("Destination");
				String sourceCity = request.getParameter("sourceCity");
				String destinationCity = request.getParameter("destinationCity");
				String tubeType = request.getParameter("tubeType");
				String tubeDeployment = request.getParameter("tubeDeployment");
				String tubeNetworkLevel = request.getParameter("tubeNetworkLevel");
				String tubeOwner = request.getParameter("tubeOwner");
				String fiberTubeCreatedByUser = request.getParameter("fiberTubeCreatedByUser");
				String fiberTubeModifiedByUser = request.getParameter("fiberTubeModifiedByUser");
				String tubeAuxFlag = request.getParameter("tubeAuxFlag");
				String drawingtype = request.getParameter("drawingType");
				String tubeNumber = request.getParameter("tubeNumber");
				String tubeColor = request.getParameter("tubeColor");

				Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

				String tubeCreatedDate = request.getParameter("tubeCreatedDate");
				Timestamp tubeCreationDate;
				if (StringUtils.equalsIgnoreCase(tubeCreatedDate, "")) {
					tubeCreationDate = lastModifiedDate;

				} else {
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					tubeCreationDate = new Timestamp(
							formatter.parse(request.getParameter("tubeCreatedDate")).getTime());
				}

				float totalDrivingDistance = 0;
				float totalGeoDistance = 0;
				float lastAuxToDestDistance = 0;
				float lastAuxToDestDrivDistance = 0;
				float tubeLength = (float) 0;

				if (request.getParameter("tubeLength") != "") {
					tubeLength = Float.parseFloat(request.getParameter("tubeLength"));
				}
				if (request.getParameter("totalDrivingDistance") != "") {
					totalDrivingDistance = Float.parseFloat(request.getParameter("totalDrivingDistance"));
				}

				if (request.getParameter("totalGeoDistance") != "") {
					totalGeoDistance = Float.parseFloat(request.getParameter("totalGeoDistance"));
				}
				if (request.getParameter("lastAuxToDestDistance") != "") {
					lastAuxToDestDistance = Float.parseFloat(request.getParameter("lastAuxToDestDistance"));
				}
				if (request.getParameter("lastAuxToDestDrivDistance") != "") {
					lastAuxToDestDrivDistance = Float.parseFloat(request.getParameter("lastAuxToDestDrivDistance"));
				}

				fiberTubes = new FiberTubes();
				fiberTubes.setTubeID(tubeID);
				fiberTubes.setFibercableID(fiberCableId);
				fiberTubes.setSrcLong(sourceLng);
				fiberTubes.setSrcLat(sourceLat);
				fiberTubes.setDestLong(destinationLng);
				fiberTubes.setDestLat(destinationLat);
				fiberTubes.setTubeNumber(tubeNumber);
				fiberTubes.setTubeColor(tubeColor);

				if (source.contains("WARE") == true) {
					fiberTubes.setSourceWareId(source.split(":")[0]);
					fiberTubes.setSourceId(source.split(":")[2]);
					fiberTubes.setSourceName(source.split(":")[1]);
				}

				else if (source.contains("CUST") == true) {
					fiberTubes.setSourceWareId("null");
					fiberTubes.setSourceId(source.split(":")[0]);
					fiberTubes.setSourceName(source.split(":")[1]);
				} else if (source.contains("MH") == true || source.contains("HH") == true
						|| source.contains("DB") == true) {
					fiberTubes.setSourceWareId("null");
					fiberTubes.setSourceId(source.split(":")[0]);
					fiberTubes.setSourceName(source.split(":")[1]);
				} else {
					fiberTubes.setSourceWareId("null");
					fiberTubes.setSourceId("null");
					fiberTubes.setSourceName(source);
				}
				if (destination.contains("WARE") == true) {
					fiberTubes.setDestinationWareId(destination.split(":")[0]);
					fiberTubes.setDestinationId(destination.split(":")[2]);
					fiberTubes.setDestinationName(destination.split(":")[1]);
				}

				else if (destination.contains("CUST") == true) {
					fiberTubes.setDestinationWareId("null");
					fiberTubes.setDestinationId(destination.split(":")[0]);
					fiberTubes.setDestinationName(destination.split(":")[1]);
				} else if (destination.contains("MH") == true || destination.contains("HH") == true
						|| destination.contains("DB") == true) {
					fiberTubes.setDestinationWareId("null");
					fiberTubes.setDestinationId(destination.split(":")[0]);
					fiberTubes.setDestinationName(destination.split(":")[1]);
				} else {
					fiberTubes.setDestinationWareId("null");
					fiberTubes.setDestinationId("null");
					fiberTubes.setDestinationName(destination);
				}
				fiberTubes.setTubeName(tubeName);
				fiberTubes.setSrcCity(sourceCity);
				fiberTubes.setDestCity(destinationCity);
				fiberTubes.setTubeType(tubeType);
				fiberTubes.setTubeDeployment(tubeDeployment);
				fiberTubes.setTubefiberNetLevel(tubeNetworkLevel);
				fiberTubes.setTubeOwner(tubeOwner);
				fiberTubes.setCreationDate(tubeCreationDate);
				fiberTubes.setLastModifieddate(lastModifiedDate);
				fiberTubes.setCreatedBy(fiberTubeCreatedByUser);
				fiberTubes.setLastmodifiedBy(fiberTubeModifiedByUser);
				fiberTubes.setTotaldriving(totalDrivingDistance);
				fiberTubes.setTotalGeoDist(totalGeoDistance);
				fiberTubes.setDrawingtype(drawingtype);
				fiberTubes.setLastAuxToDestDistance(lastAuxToDestDistance);
				fiberTubes.setLastAuxToDestDrivDistance(lastAuxToDestDrivDistance);
				fiberTubes.setTubelength(tubeLength);

				session.saveOrUpdate(fiberTubes);
				session.flush();
				session.clear();
				PhyAct.setElementID(tubeID);
				session.saveOrUpdate(PhyAct);
				String aux_ID;
				int auxArraySize = 0;
				if (StringUtils.equalsIgnoreCase(tubeAuxFlag, "opened")
						|| StringUtils.equalsIgnoreCase(tubeAuxFlag, "new tube")) {

					query = session
							.createNativeQuery("delete from TUBE_AUXILIARY_POINTS where TUBE_ID='" + tubeID + "'");
					query.executeUpdate();

					if (itemParameters.getDictParameter().size() > 0) {
						if (itemParameters.getDictParameter().size() > 1500) {
							auxArraySize = 1500;
						} else {
							auxArraySize = itemParameters.getDictParameter().size();
						}
						for (int i = 0; i < auxArraySize; i++) {

							fiberAuxtubes = new TubeAuxPoints();
							synchronized (this) {

								// String aux_ID = "AUXILIARY_TUBE_PT_" + year + "_" +
								// appConfig.getSeqNbr(56,session);
								aux_ID = "AUXILIARY_TUBE_PT_" + year + "_"
										+ Integer.parseInt(
												session.createNativeQuery("SELECT FIBER_TUBE_AUX FROM SEQ_TABLE")
														.uniqueResult().toString());
								query = session
										.createNativeQuery("UPDATE SEQ_TABLE SET FIBER_TUBE_AUX = FIBER_TUBE_AUX + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
							}
							Double driving_distance, geo_distance;

							driving_distance = itemParameters.getDictParameter().get(i).get("driving_distance") == ""
									? 0
									: itemParameters.getDictParameter().get(i).get("driving_distance") == null ? 0
											: StringUtils.equalsIgnoreCase(
													itemParameters.getDictParameter().get(i).get("driving_distance"),
													"null") ? 0
															: Double.parseDouble(itemParameters.getDictParameter()
																	.get(i).get("driving_distance"));
							geo_distance = itemParameters.getDictParameter().get(i).get("geo_distance") == "" ? 0
									: itemParameters.getDictParameter().get(i).get("geo_distance") == null ? 0
											: StringUtils.equalsIgnoreCase(
													itemParameters.getDictParameter().get(i).get("geo_distance"),
													"null") ? 0
															: Double.parseDouble(itemParameters.getDictParameter()
																	.get(i).get("geo_distance"));

							fiberAuxtubes.setAuxID(aux_ID);
							fiberAuxtubes.setFibercableID(fiberCableId);
							fiberAuxtubes.setTubeID(tubeID);
							fiberAuxtubes.setLong(
									Double.parseDouble(itemParameters.getDictParameter().get(i).get("aux_Longitude")));
							fiberAuxtubes.setLat(
									Double.parseDouble(itemParameters.getDictParameter().get(i).get("aux_Latitude")));
							fiberAuxtubes.setDistancefromsource(Double
									.parseDouble(itemParameters.getDictParameter().get(i).get("distance_From_Source")));

							if (itemParameters.getDictParameter().get(i).get("aux_Name").contains("WARE") == true) {

								fiberAuxtubes.setWareID(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[0]);
								fiberAuxtubes.setAuxPointID(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[2]);
								fiberAuxtubes.setAuxPointName(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[1]);
							} else if (itemParameters.getDictParameter().get(i).get("aux_Name")
									.contains("Auxiliary_Point") == true) {
								fiberAuxtubes.setWareID("null");
								fiberAuxtubes.setAuxPointID("null");
								if (itemParameters.getDictParameter().get(i).get("aux_Name")
										.contains("AUXILIARY_TUBE_PT") == true) {
									fiberAuxtubes.setAuxPointName(
											itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[1]);
								} else {
									fiberAuxtubes
											.setAuxPointName(itemParameters.getDictParameter().get(i).get("aux_Name"));
								}
							} else if (itemParameters.getDictParameter().get(i).get("aux_Name").contains("MH") == true
									|| itemParameters.getDictParameter().get(i).get("aux_Name").contains("HH") == true
									|| itemParameters.getDictParameter().get(i).get("aux_Name")
											.contains("DB") == true) {
								fiberAuxtubes.setWareID("null");
								fiberAuxtubes.setAuxPointID(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[0]);
								fiberAuxtubes.setAuxPointName(
										itemParameters.getDictParameter().get(i).get("aux_Name").split(":")[1]);
							} else {
								fiberAuxtubes.setWareID("null");
								fiberAuxtubes.setAuxPointID("null");
								fiberAuxtubes.setAuxPointName(itemParameters.getDictParameter().get(i).get("aux_Name"));
							}
							fiberAuxtubes.setCreationDate(tubeCreationDate);
							fiberAuxtubes.setLastModifieddate(lastModifiedDate);
							fiberAuxtubes.setSeqSorting(itemParameters.getDictParameter().get(i).get("aux_seqSorting"));
							fiberAuxtubes.setDrivingDist(driving_distance);
							fiberAuxtubes.setGeoDist(geo_distance);

							session.saveOrUpdate(fiberAuxtubes);
							session.flush();
							session.clear();
						}
					}
				}

				List<Object[]> tubeAuxiliary = session.createNativeQuery(
						"SELECT TUBE_ID,LONGITUDE,LATITUDE,WARE_ID, AUXILIARY_POINT_ID, AUXILIARY_POINT_NAME ,DISTANCE_FROM_SOURCE,SEQ_SORTING,AUXILIARY_ID,DRIVING_DISTANCE,GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS WHERE TUBE_ID='"
								+ tubeID + "' ORDER BY SEQ_SORTING ASC ")
						.getResultList();

				strandsCount = session.createNativeQuery("select count (*) from FIBER_STRANDS WHERE TUBE_ID='" + tubeID
						+ "' and FIBER_CABLE_ID='" + fiberCableId + "'").uniqueResult().toString();
				rtn.put("strandsCount", strandsCount);

				rtn.put("TubeAUX", tubeAuxiliary);
				rtn.put("tubeID", tubeID);
				rtn.put("TubeName", tubeName);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in saveFiberTube due to \n " + exceptionAsString);
				logger.info("Error in saveFiberTube due to \n " + exceptionAsString);
				rtn.put("tubeID", null);
				rtn.put("TubeAUX", null);

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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findTubeDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findTubeDetails(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String selectedTube = request.getParameter("ID");
			List<Object[]> fiberTubes;
			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					fiberTubes = session.createNativeQuery(
							"SELECT TUBE_ID,TUBE_NAME,FIBER_CABLE_ID,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY, TUBE_TYPE, TUBE_DEPLOYMENT, TUBE_NETWORK_LEVEL, TUBE_OWNER,CREATED_BY,LAST_MODIFIED_BY,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),TOTAL_DRIVING_DISTANCE,TOTAL_GEO_DISTANCE,DRAWING_TYPE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE,LENGTH,TUBE_NUMBER,TUBE_COLOR FROM FIBER_TUBES WHERE TUBE_ID='"
									+ selectedTube + "' ")
							.getResultList();

					List<Object[]> tubeAuxiliary = session.createNativeQuery(
							"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,TUBE_ID,SEQ_SORTING, DRIVING_DISTANCE, GEO_DISTANCE,AUXILIARY_ID FROM TUBE_AUXILIARY_POINTS WHERE TUBE_ID='"
									+ selectedTube + "' ORDER BY SEQ_SORTING ASC ")
							.getResultList();

					rtn.put("fiberTubes", fiberTubes);
					rtn.put("auxData", tubeAuxiliary);

					List<String> finalList = new ArrayList<String>();
					if (itemParameters.getDictParameter().size() > 0) {
						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
							String aux_Long = (itemParameters.getDictParameter().get(i).get("auxLng"));
							String aux_Lat = (itemParameters.getDictParameter().get(i).get("auxLat"));

							String querySearch = "SELECT * FROM ( ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM MANHOLE) UNION (SELECT MANHOLE_ID,MANHOLE_NAME,city FROM MANHOLE WHERE LONGITUDE like '"
									+ aux_Long + "%' and LATITUDE like '" + aux_Lat
									+ "%' )) UNION ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM MANHOLE) "
									+ " UNION (SELECT HANDHOLE_ID,HANDHOLE_NAME,city FROM HANDHOLE WHERE LONGITUDE like '"
									+ aux_Long + "%' and LATITUDE like '" + aux_Lat
									+ "%' )) UNION ( ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM distribution_board) UNION (SELECT DB_ID,DB_NAME,CITY FROM distribution_board "
									+ " WHERE DB_LONGITUDE like '" + aux_Long + "%' and DB_LATITUDE like '" + aux_Lat
									+ "%' )) ) )WHERE ROWNUM=1";

							finalList.addAll(session.createNativeQuery(querySearch).getResultList());
						}
					}
					if (finalList != null) {
						rtn.put("auxPtSearch", finalList);
					}

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in findTubeDetails due to \n " + exceptionAsString);
					logger.info("Error in findTubeDetails due to \n " + exceptionAsString);
					// rtn.put("fiberDetails", null);
					rtn.put("fiberTubes", null);
					// rtn.put("fiberStrands", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}
			}
			return rtn;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findStrandDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findStrandDetails(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String selectedStrand = request.getParameter("ID");
			List<Object[]> fiberStrands;
			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					fiberStrands = session.createNativeQuery(
							"SELECT STRAND_ID,STRAND_NAME,FIBER_CABLE_ID,TUBE_ID,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY, "
									+ "STRAND_TYPE, STRAND_DEPLOYMENT, STRAND_NETWORK_LEVEL, STRAND_OWNER,CREATED_BY,LAST_MODIFIED_BY,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),TOTAL_DRIVING_DISTANCE, TOTAL_GEO_DISTANCE,LENGTH,DRAWING_TYPE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE,STRAND_NUMBER,STRAND_COLOR FROM FIBER_STRANDS WHERE STRAND_ID='"
									+ selectedStrand + "' ")
							.getResultList();
					rtn.put("fiberStrands", fiberStrands);

					List<Object[]> strandAuxiliary = session.createNativeQuery(
							"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,STRAND_ID,SEQ_SORTING,DRIVING_DISTANCE,GEO_DISTANCE,AUXILIARY_ID FROM STRAND_AUXILIARY_POINTS WHERE STRAND_ID='"
									+ selectedStrand + "' ORDER BY SEQ_SORTING ASC ")
							.getResultList();

					rtn.put("auxData", strandAuxiliary);

					List<String> finalList = new ArrayList<String>();
					if (itemParameters.getDictParameter().size() > 0) {
						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
							String aux_Long = (itemParameters.getDictParameter().get(i).get("auxLng"));
							String aux_Lat = (itemParameters.getDictParameter().get(i).get("auxLat"));

							String querySearch = "SELECT * FROM ( ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM MANHOLE) UNION (SELECT MANHOLE_ID,MANHOLE_NAME,city FROM MANHOLE WHERE LONGITUDE like '"
									+ aux_Long + "%' and LATITUDE like '" + aux_Lat
									+ "%' )) UNION ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM MANHOLE) "
									+ " UNION (SELECT HANDHOLE_ID,HANDHOLE_NAME,city FROM HANDHOLE WHERE LONGITUDE like '"
									+ aux_Long + "%' and LATITUDE like '" + aux_Lat
									+ "%' )) UNION ( ((SELECT 'NULL' as id,'NULL' as name,'NULL' as city  FROM distribution_board) UNION (SELECT DB_ID,DB_NAME,CITY FROM distribution_board "
									+ " WHERE DB_LONGITUDE like '" + aux_Long + "%' and DB_LATITUDE like '" + aux_Lat
									+ "%' )) ) )WHERE ROWNUM=1";

							finalList.addAll(session.createNativeQuery(querySearch).getResultList());
						}
					}
					if (finalList != null) {
						rtn.put("auxPtSearch", finalList);
					}

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in findStrandDetails due to \n " + exceptionAsString);
					logger.info("Error in findStrandDetails due to \n " + exceptionAsString);
					// rtn.put("fiberDetails", null);
					rtn.put("fiberStrands", null);
					// rtn.put("fiberStrands", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}
			}
			return rtn;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveTrench", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTrench(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {


		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);

			String trenchId = request.getParameter("trenchID");

			// try {

			if (StringUtils.equalsIgnoreCase(trenchId, "")) {
				synchronized (this) {
					// trenchId = "Trench_" + year + "_" + appConfig.getSeqNbr(71,session);
					trenchId = "Trench_" + year + "_" + Integer.parseInt(
							session.createNativeQuery("SELECT TRENCH FROM SEQ_TABLE").uniqueResult().toString());
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET TRENCH = TRENCH + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
				}
			}

			String trenchName = "";
			String projId = "";
			String trenchCreatedByUser = "", trenchModifiedByUser = "";
			Float trenchLength = (float) 0;
			Float maxCapacity = (float) 0;
			Float numDucts = (float) 0;

			if (request.getParameter("trenchName") != "") {
				trenchName = request.getParameter("trenchName");
			}
			if (request.getParameter("ProjectId") != "") {
				projId = request.getParameter("ProjectId");
			}

			if (request.getParameter("NumDucts") != "") {
				numDucts = Float.parseFloat(request.getParameter("NumDucts"));
			}

			if (request.getParameter("trenchCapacity") != "") {
				maxCapacity = Float.parseFloat(request.getParameter("trenchCapacity"));
			}
			if (request.getParameter("trenchLength") != "") {
				trenchLength = Float.parseFloat(request.getParameter("trenchLength"));
			}
			trenchCreatedByUser = request.getParameter("trenchCreatedByUser");
			trenchModifiedByUser = request.getParameter("trenchModifiedByUser");
			Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

			String trenchCreatedDate = request.getParameter("trenchCreatedDate");
			Timestamp trenchCreationDate;
			if (StringUtils.equalsIgnoreCase(trenchCreatedDate, "")) {
				trenchCreationDate = lastModifiedDate;

			} else {
				DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				trenchCreationDate = new Timestamp(
						formatter.parse(request.getParameter("trenchCreatedDate")).getTime());
			}
			float totalDrivingDistance = 0;
			float totalGeoDistance = 0;
			float lastAuxToDestDistance = 0;
			float lastAuxToDestDrivDistance = 0;
			if (request.getParameter("totalDrivingDistance") != "") {
				totalDrivingDistance = Float.parseFloat(request.getParameter("totalDrivingDistance"));
			}
			if (request.getParameter("totalGeoDistance") != "") {
				totalGeoDistance = Float.parseFloat(request.getParameter("totalGeoDistance"));
			}
			if (request.getParameter("lastAuxToDestDistance") != "") {
				lastAuxToDestDistance = Float.parseFloat(request.getParameter("lastAuxToDestDistance"));
			}
			if (request.getParameter("lastAuxToDestDrivDistance") != "") {
				lastAuxToDestDrivDistance = Float.parseFloat(request.getParameter("lastAuxToDestDrivDistance"));
			}

			String trenchAuxFlag = request.getParameter("trenchAuxFlag");
			String drawingtype = request.getParameter("drawingType");

			Trench trench = new Trench();

			trench.setTrenchID(trenchId);
			trench.setTrenchName(trenchName);
			if (request.getParameter("SourceTrench").contains("WARE") == true) {
				trench.setSourceWareId(request.getParameter("SourceTrench").split(":")[0]);
				trench.setSourceId(request.getParameter("SourceTrench").split(":")[2]);
				trench.setSourceName(request.getParameter("SourceTrench").split(":")[1]);
			}

			else if (request.getParameter("SourceTrench").contains("CUST") == true) {
				trench.setSourceWareId("null");
				trench.setSourceId(request.getParameter("SourceTrench").split(":")[0]);
				trench.setSourceName(request.getParameter("SourceTrench").split(":")[1]);
			} else if (request.getParameter("SourceTrench").contains("MH") == true
					|| request.getParameter("SourceTrench").contains("HH") == true
					|| request.getParameter("SourceTrench").contains("DB") == true) {
				trench.setSourceWareId("null");
				trench.setSourceId(request.getParameter("SourceTrench").split(":")[0]);
				trench.setSourceName(request.getParameter("SourceTrench").split(":")[1]);
			} else {
				trench.setSourceWareId("null");
				trench.setSourceId("null");
				trench.setSourceName(request.getParameter("SourceTrench"));
			}

			if (request.getParameter("DestinationTrench").contains("WARE") == true) {
				trench.setDestinationWareId(request.getParameter("DestinationTrench").split(":")[0]);
				trench.setDestinationId(request.getParameter("DestinationTrench").split(":")[2]);
				trench.setDestinationName(request.getParameter("DestinationTrench").split(":")[1]);
			}

			else if (request.getParameter("DestinationTrench").contains("CUST") == true) {
				trench.setDestinationWareId("null");
				trench.setDestinationId(request.getParameter("DestinationTrench").split(":")[0]);
				trench.setDestinationName(request.getParameter("DestinationTrench").split(":")[1]);
			} else if (request.getParameter("DestinationTrench").contains("MH") == true
					|| request.getParameter("DestinationTrench").contains("HH") == true
					|| request.getParameter("DestinationTrench").contains("DB") == true) {
				trench.setDestinationWareId("null");
				trench.setDestinationId(request.getParameter("DestinationTrench").split(":")[0]);
				trench.setDestinationName(request.getParameter("DestinationTrench").split(":")[1]);
			} else {
				trench.setDestinationWareId("null");
				trench.setDestinationId("null");
				trench.setDestinationName(request.getParameter("DestinationTrench"));
			}
			trench.setSrcLong(request.getParameter("SourceTrenchLng"));
			trench.setSrcLat(request.getParameter("SourceTrenchLat"));
			trench.setDestLong(request.getParameter("DestinationTrenchLng"));
			trench.setDestLat(request.getParameter("DestinationTrenchLat"));
			trench.setSrcCity(request.getParameter("srcCityTrench"));
			trench.setDestCity(request.getParameter("dstCityTrench"));
			trench.setNumDucts(numDucts);
			trench.setMaxCapacity(maxCapacity);
			trench.setLength(trenchLength);
			trench.setProjectId(projId);
			trench.setCreationDate(trenchCreationDate);
			trench.setLastModifieddate(lastModifiedDate);
			trench.setCreatedBy(trenchCreatedByUser);
			trench.setLastmodifiedBy(trenchModifiedByUser);
			trench.setTotaldriving(totalDrivingDistance);
			trench.setTotalGeoDist(totalGeoDistance);
			trench.setLastAuxToDestDistance(lastAuxToDestDistance);
			trench.setLastAuxToDestDrivDistance(lastAuxToDestDrivDistance);
			trench.setDrawingtype(drawingtype);
			trench.setTrenchOwner(request.getParameter("trenchOwner"));
			trench.setTrenchInstaller(request.getParameter("trenchInstaller"));
			trench.setTrenchEngineerName(request.getParameter("trenchEngineerName"));
			
			session.saveOrUpdate(trench);
			session.flush();
			session.clear();

			int auxArraySize = 0;
			if (StringUtils.equalsIgnoreCase(trenchAuxFlag, "opened")
					|| StringUtils.equalsIgnoreCase(trenchAuxFlag, "new trench")) {
				query = session
						.createNativeQuery("delete from TRENCH_AUXILIARY_POINTS where TRENCH_ID='" + trenchId + "'");
				query.executeUpdate();

				if (itemParameters.getDictParameter().size() > 0) {
					if (itemParameters.getDictParameter().size() > 1500) {
						auxArraySize = 1500;
					} else {
						auxArraySize = itemParameters.getDictParameter().size();
					}
					for (int i = 0; i < auxArraySize; i++) {
						String aux_ID;
						synchronized (this) {
							// String aux_ID = "AUXILIARY_TRENCH_PT_" + year + "_" +
							// appConfig.getSeqNbr(72,session);
							aux_ID = "AUXILIARY_TRENCH_PT_" + year + "_" + Integer.parseInt(session
									.createNativeQuery("SELECT TRENCHAUX FROM SEQ_TABLE").uniqueResult().toString());
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET TRENCHAUX = TRENCHAUX + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						Float aux_Long = Float
								.parseFloat(itemParameters.getDictParameter().get(i).get("aux_Longitude"));
						Float aux_Lat = Float.parseFloat(itemParameters.getDictParameter().get(i).get("aux_Latitude"));
						Float distance = Float
								.parseFloat(itemParameters.getDictParameter().get(i).get("distance_From_Source"));

						Double driving_distance, geo_distance;
						driving_distance = itemParameters.getDictParameter().get(i).get("drivingDistance") == "" ? 0
								: itemParameters.getDictParameter().get(i).get("drivingDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameter().get(i).get("drivingDistance"), "null")
														? 0
														: Double.parseDouble(itemParameters.getDictParameter().get(i)
																.get("drivingDistance"));
						geo_distance = itemParameters.getDictParameter().get(i).get("geoDistance") == "" ? 0
								: itemParameters.getDictParameter().get(i).get("geoDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameter().get(i).get("geoDistance"), "null") ? 0
														: Double.parseDouble(itemParameters.getDictParameter().get(i)
																.get("geoDistance"));

						TrenchAuxPoints trenchAuxPoint = new TrenchAuxPoints();

						trenchAuxPoint.setAuxID(aux_ID);
						trenchAuxPoint.setTrenchID(trenchId);
						trenchAuxPoint.setLong(aux_Long);
						trenchAuxPoint.setLat(aux_Lat);

						if (itemParameters.getDictParameter().get(i).get("auxTrench_Name").contains("WARE") == true) {

							trenchAuxPoint.setWareID(
									itemParameters.getDictParameter().get(i).get("auxTrench_Name").split(":")[0]);
							trenchAuxPoint.setAuxPointID(
									itemParameters.getDictParameter().get(i).get("auxTrench_Name").split(":")[2]);
							trenchAuxPoint.setAuxPointName(
									itemParameters.getDictParameter().get(i).get("auxTrench_Name").split(":")[1]);
						} else if (itemParameters.getDictParameter().get(i).get("auxTrench_Name")
								.contains("Auxiliary_Point") == true) {
							trenchAuxPoint.setWareID("null");
							trenchAuxPoint.setAuxPointID("null");
							if (itemParameters.getDictParameter().get(i).get("auxTrench_Name")
									.contains("AUXILIARY_TRENCH_PT") == true) {
								trenchAuxPoint.setAuxPointName(
										itemParameters.getDictParameter().get(i).get("auxTrench_Name").split(":")[1]);
							} else {
								trenchAuxPoint.setAuxPointName(
										itemParameters.getDictParameter().get(i).get("auxTrench_Name"));
							}
						} else if (itemParameters.getDictParameter().get(i).get("auxTrench_Name").contains("MH") == true
								|| itemParameters.getDictParameter().get(i).get("auxTrench_Name").contains("HH") == true
								|| itemParameters.getDictParameter().get(i).get("auxTrench_Name")
										.contains("DB") == true) {
							trenchAuxPoint.setWareID("null");
							trenchAuxPoint.setAuxPointID(
									itemParameters.getDictParameter().get(i).get("auxTrench_Name").split(":")[0]);
							trenchAuxPoint.setAuxPointName(
									itemParameters.getDictParameter().get(i).get("auxTrench_Name").split(":")[1]);
						} else {
							trenchAuxPoint.setWareID("null");
							trenchAuxPoint.setAuxPointID("null");
							trenchAuxPoint
									.setAuxPointName(itemParameters.getDictParameter().get(i).get("auxTrench_Name"));
						}
						trenchAuxPoint.setDistancefromsource(distance);
						trenchAuxPoint.setSeqSorting(itemParameters.getDictParameter().get(i).get("aux_seqSorting"));
						trenchAuxPoint.setCreationDate(trenchCreationDate);
						trenchAuxPoint.setLastModifieddate(lastModifiedDate);
						trenchAuxPoint.setDrivingDist(driving_distance);
						trenchAuxPoint.setGeoDist(geo_distance);
						session.saveOrUpdate(trenchAuxPoint);
						session.flush();
						session.clear();
					}
				}

			}

			List<Object[]> ductList = session.createNativeQuery(
					"SELECT B.DUCT_ID,B.DUCT_NAME,B.SOURCE_WARE_ID,B.SOURCE_ID,B.SOURCE_NAME,B.DESTINATION_WARE_ID,B.DESTINATION_ID,B.DESTINATION_NAME,B.SOURCE_LATITUDE,B.SOURCE_LONGITUDE,B.DESTINATION_LONGITUDE,B.DESTINATION_LATITUDE,B.SOURCE_CITY,B.DESTINATION_CITY,B.NUM_FIBERCABLES,B.NUM_FIBERTUBES,B.NUM_FIBERSTRANDS,B.LENGTH,B.TRENCH_ID,B.OWNER,B.DUCT_INSTALLER,B.DUCT_ENGINEER_NAME  FROM DUCTS B WHERE B.TRENCH_ID='"
							+ trenchId + "'")
					.getResultList();

			List<Object[]> trenchAuxiliaryList = session.createNativeQuery(
					"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,TRENCH_ID,SEQ_SORTING,DRIVING_DISTANCE,GEO_DISTANCE,AUXILIARY_ID FROM TRENCH_AUXILIARY_POINTS WHERE TRENCH_ID='"
							+ trenchId + "' ORDER BY SEQ_SORTING ASC ")
					.getResultList();

			rtn.put("ductList", ductList);
			rtn.put("trenchId", trenchId);
			rtn.put("trenchAuxiliaryList", trenchAuxiliaryList);

			// } catch (Exception e) {
			// logger.info("Error in saving Data for fiber cables with a message :"+e);

			// }

			// finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();

				// }
			}
		}
		return rtn;
	
	}

	@RequestMapping(value = "/getSearchHeader", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSearchHeader(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {

			String target = request.getParameter("target");
			String searchkey = request.getParameter("searchkey");
			System.out.println("Search key :" + searchkey);
			System.out.println("target :" + target);
			session = AlmDbSession.getInstance().getSession();
			List<Object[]> resultList;
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
					if (StringUtils.equalsIgnoreCase(target, "client")) {
						Query result = session.createNativeQuery(
								"SELECT CLIENT_ID,DISPLAY_NAME,LONGITUDE,LATITUDE FROM CLIENTS WHERE CLIENT_ID LIKE :param OR DISPLAY_NAME LIKE :param");

						result.setParameter("param", "%" + searchkey + "%");
						result.setMaxResults(40);
						resultList = result.getResultList();
						rtn.put("searchResult", resultList);

					} else if (StringUtils.equalsIgnoreCase(target, "site")) {
						System.out.println("siteeess");

						Query result = session.createNativeQuery(
								"SELECT WARE_ID,WARE_NAME,LONGITUDE,LATITUDE FROM WAREHOUSE WHERE UPPER(WARE_ID) LIKE :param OR UPPER(WARE_NAME) LIKE :param");

						result.setParameter("param", "%" + searchkey + "%");
						result.setMaxResults(40);
						// resultList=result.getResultList();
						rtn.put("searchResult", result.getResultList());

					} else {
						rtn.put("searchResult", null);
					}

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in getSearchHeader due to \n " + exceptionAsString);
					logger.info("Error in getSearchHeader due to \n " + exceptionAsString);
					rtn.put("searchResult", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}
			}
			return rtn;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveJunction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveJunction(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {


		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;

		} else {
			if (session != null && session.isOpen()) {

				tx = session.beginTransaction();
				try {

					Date date = new Date();
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(date);
					int year = calendar.get(Calendar.YEAR);
					Float junctionLong = (float) 0;
					Float junctionLat = (float) 0;
					Float junctionCapacity = (float) 0;
					Float junctionNumber = (float) 0;

					String junctionCity = request.getParameter("JunctionCity");
					String junctionName = request.getParameter("JunctionName");
					String physLayerIdJunction = request.getParameter("LayerIdJunction");
					String physLayerNameJunction = request.getParameter("LayerNameJunction");
					String projectId = request.getParameter("ProjectId");
					String junctionID = request.getParameter("JunctionId");
					String JunctionOwner = request.getParameter("JunctionOwner");
					String JunctionInstaller = request.getParameter("JunctionInstaller");
					String JunctionEngineerName = request.getParameter("JunctionEngineerName");
					String ipAddress = getIpAddress(request);
					String updateModfUser=request.getParameter("updateModfUser");
					PhysicalLayerActivity PhyAct= new PhysicalLayerActivity();

					if (request.getParameter("JunctionLong") != "") {
						junctionLong = Float.parseFloat(request.getParameter("JunctionLong"));
					}

					if (request.getParameter("JunctionLat") != "") {
						junctionLat = Float.parseFloat(request.getParameter("JunctionLat"));
					}

					if (request.getParameter("JunctionCapacity") != "") {
						junctionCapacity = Float.parseFloat(request.getParameter("JunctionCapacity"));
					}

					if (request.getParameter("JunctionNum") != "") {
						junctionNumber = Float.parseFloat(request.getParameter("JunctionNum"));
					}

					Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

					String junctionCreatedDate = request.getParameter("JctCreatedDate");
					Timestamp junctionCreationDate;
					if (StringUtils.equalsIgnoreCase(junctionCreatedDate, "")) {
						junctionCreationDate = lastModifiedDate;
					} else {
						DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
						junctionCreationDate = new Timestamp(
								formatter.parse(request.getParameter("JctCreatedDate")).getTime());
					}
					String PhyActID=
							 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							
							PhyAct.setPhyActID(PhyActID);
							PhyAct.setScreenName("Junction");
							PhyAct.setUsername(updateModfUser);
							PhyAct.setUserIP(ipAddress);
							PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
						
					if (StringUtils.equalsIgnoreCase(junctionID, "")) {
						//System.out.println(" id is null" + junctionID);
						synchronized (this) {
							// junctionID = "JCT_" + year + "_" + appConfig.getSeqNbr(76,session);
							junctionID = "JCT_" + year + "_" + Integer.parseInt(session
									.createNativeQuery("SELECT JUNCTION FROM SEQ_TABLE").uniqueResult().toString());
							System.out.println("man id " + junctionID);
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET JUNCTION = JUNCTION +1");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						query = session.createNativeQuery("SELECT count(*) FROM JUNCTION b WHERE PHYSICAL_LAYER_ID = '"
								+ physLayerIdJunction + "' ");
						String countJunc = query.getSingleResult().toString();
						
						

						if(physLayerIdJunction != null) {
							if (StringUtils.equalsIgnoreCase(countJunc, "0")) {

								physLayerNameJunction = physLayerNameJunction.concat("_J");
								query = session.createNativeQuery("UPDATE MANHOLE SET MANHOLE_NAME= '"
										+ physLayerNameJunction + "' where MANHOLE_ID='" + physLayerIdJunction + "' ");
								query.executeUpdate();
								query = session.createNativeQuery("UPDATE HANDHOLE SET HANDHOLE_NAME= '"
										+ physLayerNameJunction + "' where HANDHOLE_ID='" + physLayerIdJunction + "' ");
								query.executeUpdate();
							}
						
						}
						String[] idSplit;
						idSplit = junctionID.split("_");
						if (request.getParameter("JunctionName").isEmpty()) {
							junctionName = "JUNC_" + request.getParameter("JunctionCity") + "_" + idSplit[1] + "_"
									+ idSplit[2];
						}
						Query insertJctQuery = session
								.createNativeQuery("INSERT INTO JUNCTION(JUNCTION_ID,JUNCTION_NAME,PHYSICAL_LAYER_ID"
										+ ",PHYSICAL_LAYER_NAME,LONGITUDE,LATITUDE,CAPACITY,JUNCTION_NUMBER,CITY,PROJECT_ID,CREATION_DATE,LAST_MODIFIED_DATE,OWNER,JUNC_INSTALLER,JUNC_ENGINEER_NAME)"
										+ " VALUES ('" + junctionID + "','" + junctionName + "','" + physLayerIdJunction
										+ "','" + physLayerNameJunction + "','" + junctionLong + "','" + junctionLat
										+ "','" + junctionCapacity + "','" + junctionNumber + "','" + junctionCity
										+ "','" + projectId + "', TIMESTAMP '" + junctionCreationDate + "',TIMESTAMP '"
										+ lastModifiedDate + "','" + JunctionOwner + "','" + JunctionInstaller + "','" + JunctionEngineerName + "')");
						insertJctQuery.executeUpdate();

						rtn.put("ManHandholeName", physLayerNameJunction);
						PhyAct.setElementID(junctionID);
						PhyAct.setActivityDescription("Add New Element");
						session.saveOrUpdate(PhyAct);
					} else {
						//System.out.println(" id is null" + junctionID);
						Query updateJunction = session.createNativeQuery("UPDATE JUNCTION SET JUNCTION_NAME = '"
								+ junctionName + "',CAPACITY = '" + junctionCapacity + "',JUNCTION_NUMBER ='"
								+ junctionNumber + "', LAST_MODIFIED_DATE= TIMESTAMP '" + lastModifiedDate + "' "
								+ ",OWNER = '"+JunctionOwner+ "',JUNC_INSTALLER = '"+JunctionInstaller+ "',JUNC_ENGINEER_NAME = '"+JunctionEngineerName
								+ "' WHERE JUNCTION_ID = '" + junctionID + "' ");
						updateJunction.executeUpdate();
						PhyAct.setElementID(junctionID);
						PhyAct.setActivityDescription("Edit Existing Element");
						session.saveOrUpdate(PhyAct);
				
					}

					float JctSeq = 0;
					String JctStrandIdSideA = "";
					String JctStrandNameSideA = "";
					String JctTubeIdSideA = "";
					String JctTubeNameSideA = "";
					String JctFiberIdSideA = "";
					String JctFiberNameSideA = "";
					String JctStrandIdSideB = "";
					String JctStrandNameSideB = "";
					String JctTubeIdSideB = "";
					String JctTubeNameSideB = "";
					String JctFiberIdSideB = "";
					String JctFiberNameSideB = "";
					String mappingId = "";
					String JctLocationTypeSideA = "";
					String JctLocationIdSideA = "";
					String JctLocationNameSideA = "";
					String JctWarehouseIdSideA = "";

					String JctStrandNBSideA = "";
					String JctTubeNBSideA = "";
					String JctNetworkLevelSideA = "";

					String JctStrandNBSideB = "";
					String JctTubeNBSideB = "";
					String JctNetworkLevelSideB = "";
					String JctLocationTypeSideB = "";
					String JctLocationIdSideB = "";
					String JctLocationNameSideB = "";
					String JctWarehouseIdSideB = "";
//
					if (itemParameters.getDictParameter().size() > 0) {
						String mappingJctID = "";
						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {

							synchronized (this) {
								// mappingJctID = "JCT_MAP_" + year + "_" + appConfig.getSeqNbr(77,session);
								mappingJctID = "JCT_MAP_" + year + "_"
										+ Integer.parseInt(
												session.createNativeQuery("SELECT JUNCTION_MAP FROM SEQ_TABLE")
														.uniqueResult().toString());
								System.out.println("man id " + junctionID);
								query = session
										.createNativeQuery("UPDATE SEQ_TABLE SET JUNCTION_MAP = JUNCTION_MAP +1");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
								System.out.println("the junction id map is " + mappingJctID);
							}
							JctSeq = Float.parseFloat(itemParameters.getDictParameter().get(i).get("JctSeq"));

							JctLocationTypeSideA = itemParameters.getDictParameter().get(i).get("JctLocationTypeSideA");
							JctLocationIdSideA = itemParameters.getDictParameter().get(i).get("JctLocationIdSideA");
							JctLocationNameSideA = itemParameters.getDictParameter().get(i).get("JctLocationNameSideA");
							JctWarehouseIdSideA = itemParameters.getDictParameter().get(i).get("JctWarehouseIdSideA");

							JctStrandIdSideA = itemParameters.getDictParameter().get(i).get("JctStrandIdSideA");
							JctStrandNameSideA = itemParameters.getDictParameter().get(i).get("JctStrandNameSideA");
							JctTubeIdSideA = itemParameters.getDictParameter().get(i).get("JctTubeIdSideA");
							JctTubeNameSideA = itemParameters.getDictParameter().get(i).get("JctTubeNameSideA");
							JctFiberIdSideA = itemParameters.getDictParameter().get(i).get("JctFiberIdSideA");
							JctFiberNameSideA = itemParameters.getDictParameter().get(i).get("JctFiberNameSideA");

							JctStrandIdSideB = itemParameters.getDictParameter().get(i).get("JctStrandIdSideB");
							JctStrandNameSideB = itemParameters.getDictParameter().get(i).get("JctStrandNameSideB");
							JctTubeIdSideB = itemParameters.getDictParameter().get(i).get("JctTubeIdSideB");
							JctTubeNameSideB = itemParameters.getDictParameter().get(i).get("JctTubeNameSideB");
							JctFiberIdSideB = itemParameters.getDictParameter().get(i).get("JctFiberIdSideB");
							JctFiberNameSideB = itemParameters.getDictParameter().get(i).get("JctFiberNameSideB");

							//
							JctStrandNBSideA = itemParameters.getDictParameter().get(i).get("JctStrandNBSideA");
							JctTubeNBSideA = itemParameters.getDictParameter().get(i).get("JctTubeNBSideA");
							JctNetworkLevelSideA = itemParameters.getDictParameter().get(i).get("JctNetworkLevelSideA");

							JctStrandNBSideB = itemParameters.getDictParameter().get(i).get("JctStrandNBSideB");
							JctTubeNBSideB = itemParameters.getDictParameter().get(i).get("JctTubeNBSideB");
							JctNetworkLevelSideB = itemParameters.getDictParameter().get(i).get("JctNetworkLevelSideB");
							JctLocationTypeSideB = itemParameters.getDictParameter().get(i).get("JctLocationTypeSideB");
							JctLocationIdSideB = itemParameters.getDictParameter().get(i).get("JctLocationIdSideB");
							JctLocationNameSideB = itemParameters.getDictParameter().get(i).get("JctLocationNameSideB");
							JctWarehouseIdSideB = itemParameters.getDictParameter().get(i).get("JctWarehouseIdSideB");

							//

							Query insertMappingJctQuery = session.createNativeQuery(
									"INSERT INTO JUNCTION_MAPPING(JCT_ID,SEQUENCE_NUMBER,JCT_MAPPING_ID,PHYSICAL_LAYER_ID,STRAND_ID_SIDE_A,STRAND_NAME_SIDE_A,"
											+ "TUBE_ID_SIDE_A,TUBE_NAME_SIDE_A,FIBER_ID_SIDE_A,FIBER_NAME_SIDE_A,STRAND_ID_SIDE_B,STRAND_NAME_SIDE_B,TUBE_ID_SIDE_B,TUBE_NAME_SIDE_B"
											+ ",FIBER_ID_SIDE_B,FIBER_NAME_SIDE_B,LOCATION_TYPE_SIDE_A,LOCATION_ID_SIDE_A,LOCATION_NAME_SIDE_A,WAREHOUSE_ID_SIDE_A,STRAND_NB_SIDE_A,"
											+ "TUBE_NB_SIDE_A,NETWORK_LEVEL_SIDE_A,STRAND_NB_SIDE_B,TUBE_NB_SIDE_B,NETWORK_LEVEL_SIDE_B,LOCATION_TYPE_SIDE_B,LOCATION_ID_SIDE_B,LOCATION_NAME_SIDE_B,WAREHOUSE_ID_SIDE_B)"
											+ " VALUES ('" + junctionID + "','" + JctSeq + "','" + mappingJctID + "','"
											+ physLayerIdJunction + "','" + JctStrandIdSideA + "','"
											+ JctStrandNameSideA + "','" + JctTubeIdSideA + "','" + JctTubeNameSideA
											+ "','" + JctFiberIdSideA + "','" + JctFiberNameSideA + "','"
											+ JctStrandIdSideB + "','" + JctStrandNameSideB + "','" + JctTubeIdSideB
											+ "','" + JctTubeNameSideB + "','" + JctFiberIdSideB + "','"
											+ JctFiberNameSideB + "','" + JctLocationTypeSideA + "','"
											+ JctLocationIdSideA + "','" + JctLocationNameSideA + "','"
											+ JctWarehouseIdSideA + "','" + JctStrandNBSideA + "','" + JctTubeNBSideA
											+ "','" + JctNetworkLevelSideA + "','" + JctStrandNBSideB + "','"
											+ JctTubeNBSideB + "','" + JctNetworkLevelSideB + "','"
											+ JctLocationTypeSideB + "','" + JctLocationIdSideB + "','"
											+ JctLocationNameSideB + "','" + JctWarehouseIdSideB + "' )");
							insertMappingJctQuery.executeUpdate();
						}

					}

					if (itemParameters.getDictParameterTemp().size() > 0) {
						for (int i = 0; i < itemParameters.getDictParameterTemp().size(); i++) {

							JctSeq = Float.parseFloat(itemParameters.getDictParameterTemp().get(i).get("JctSeq"));

							JctLocationTypeSideA = itemParameters.getDictParameterTemp().get(i)
									.get("JctLocationTypeSideA");
							JctLocationIdSideA = itemParameters.getDictParameterTemp().get(i).get("JctLocationIdSideA");
							JctLocationNameSideA = itemParameters.getDictParameterTemp().get(i)
									.get("JctLocationNameSideA");
							JctWarehouseIdSideA = itemParameters.getDictParameterTemp().get(i)
									.get("JctWarehouseIdSideA");

							JctStrandIdSideA = itemParameters.getDictParameterTemp().get(i).get("JctStrandIdSideA");
							JctStrandNameSideA = itemParameters.getDictParameterTemp().get(i).get("JctStrandNameSideA");
							JctTubeIdSideA = itemParameters.getDictParameterTemp().get(i).get("JctTubeIdSideA");
							JctTubeNameSideA = itemParameters.getDictParameterTemp().get(i).get("JctTubeNameSideA");
							JctFiberIdSideA = itemParameters.getDictParameterTemp().get(i).get("JctFiberIdSideA");
							JctFiberNameSideA = itemParameters.getDictParameterTemp().get(i).get("JctFiberNameSideA");

							JctStrandIdSideB = itemParameters.getDictParameterTemp().get(i).get("JctStrandIdSideB");
							JctStrandNameSideB = itemParameters.getDictParameterTemp().get(i).get("JctStrandNameSideB");
							JctTubeIdSideB = itemParameters.getDictParameterTemp().get(i).get("JctTubeIdSideB");
							JctTubeNameSideB = itemParameters.getDictParameterTemp().get(i).get("JctTubeNameSideB");
							JctFiberIdSideB = itemParameters.getDictParameterTemp().get(i).get("JctFiberIdSideB");
							JctFiberNameSideB = itemParameters.getDictParameterTemp().get(i).get("JctFiberNameSideB");
							mappingId = itemParameters.getDictParameterTemp().get(i).get("jctMappingId");
							//
							JctStrandNBSideA = itemParameters.getDictParameterTemp().get(i).get("JctStrandNBSideA");
							JctTubeNBSideA = itemParameters.getDictParameterTemp().get(i).get("JctTubeNBSideA");
							JctNetworkLevelSideA = itemParameters.getDictParameterTemp().get(i)
									.get("JctNetworkLevelSideA");

							JctStrandNBSideB = itemParameters.getDictParameterTemp().get(i).get("JctStrandNBSideB");
							JctTubeNBSideB = itemParameters.getDictParameterTemp().get(i).get("JctTubeNBSideB");
							JctNetworkLevelSideB = itemParameters.getDictParameterTemp().get(i)
									.get("JctNetworkLevelSideB");
							JctLocationTypeSideB = itemParameters.getDictParameterTemp().get(i)
									.get("JctLocationTypeSideB");
							JctLocationIdSideB = itemParameters.getDictParameterTemp().get(i).get("JctLocationIdSideB");
							JctLocationNameSideB = itemParameters.getDictParameterTemp().get(i)
									.get("JctLocationNameSideB");
							JctWarehouseIdSideB = itemParameters.getDictParameterTemp().get(i)
									.get("JctWarehouseIdSideB");
							//

							Query insertMappingJctQuery = session
									.createNativeQuery("UPDATE JUNCTION_MAPPING SET SEQUENCE_NUMBER= '" + JctSeq
											+ "',STRAND_ID_SIDE_A = '" + JctStrandIdSideA + "',STRAND_NAME_SIDE_A = '"
											+ JctStrandNameSideA + "'," + "TUBE_ID_SIDE_A = '" + JctTubeIdSideA
											+ "',TUBE_NAME_SIDE_A = '" + JctTubeNameSideA + "',FIBER_ID_SIDE_A = '"
											+ JctFiberIdSideA + "',FIBER_NAME_SIDE_A = '" + JctFiberNameSideA + "', "
											+ " STRAND_ID_SIDE_B = '" + JctStrandIdSideB + "',STRAND_NAME_SIDE_B = '"
											+ JctStrandNameSideB + "',TUBE_ID_SIDE_B = '" + JctTubeIdSideB
											+ "',TUBE_NAME_SIDE_B = '" + JctTubeNameSideB + "', "
											+ " FIBER_ID_SIDE_B = '" + JctFiberIdSideB + "',FIBER_NAME_SIDE_B = '"
											+ JctFiberNameSideB + "', LOCATION_TYPE_SIDE_A = '" + JctLocationTypeSideA
											+ "',LOCATION_ID_SIDE_A = '" + JctLocationIdSideA
											+ "',LOCATION_NAME_SIDE_A = '" + JctLocationNameSideA
											+ "',WAREHOUSE_ID_SIDE_A = '" + JctWarehouseIdSideA
											+ "',STRAND_NB_SIDE_A = '" + JctStrandNBSideA + "',TUBE_NB_SIDE_A = '"
											+ JctTubeNBSideA + "',NETWORK_LEVEL_SIDE_A = '" + JctNetworkLevelSideA
											+ "',STRAND_NB_SIDE_B = '" + JctStrandNBSideB + "',TUBE_NB_SIDE_B = '"
											+ JctTubeNBSideB + "',NETWORK_LEVEL_SIDE_B = '" + JctNetworkLevelSideB
											+ "',LOCATION_TYPE_SIDE_B = '" + JctLocationTypeSideB
											+ "',LOCATION_ID_SIDE_B = '" + JctLocationIdSideB
											+ "',LOCATION_NAME_SIDE_B = '" + JctLocationNameSideB
											+ "',WAREHOUSE_ID_SIDE_B = '" + JctWarehouseIdSideB + "'"
											+ " WHERE JCT_MAPPING_ID = '" + mappingId + "'");

							insertMappingJctQuery.executeUpdate();

						}
					}

					for (int i = 0; i < itemParameters.getDictParameterDel().size(); i++) {
						Query deleteJunctionMapping = session
								.createNativeQuery("DELETE FROM JUNCTION_MAPPING WHERE JCT_MAPPING_ID='"
										+ itemParameters.getDictParameterDel().get(i).get("jctMappingId") + "'");
						deleteJunctionMapping.executeUpdate();
					}

					List<Object[]> junctionMappingPts = session.createNativeQuery(
							"SELECT DISTINCT SEQUENCE_NUMBER,STRAND_ID_SIDE_A,STRAND_NAME_SIDE_A,TUBE_ID_SIDE_A,TUBE_NAME_SIDE_A,FIBER_ID_SIDE_A,FIBER_NAME_SIDE_A,STRAND_ID_SIDE_B,STRAND_NAME_SIDE_B,TUBE_ID_SIDE_B,TUBE_NAME_SIDE_B,FIBER_ID_SIDE_B,FIBER_NAME_SIDE_B,JCT_MAPPING_ID,JCT_ID,(SELECT JUNCTION_NAME FROM JUNCTION WHERE JUNCTION_ID='"
									+ junctionID + "'),(SELECT PHYSICAL_LAYER_ID FROM JUNCTION WHERE JUNCTION_ID='"
									+ junctionID
									+ "'),LOCATION_TYPE_SIDE_A,LOCATION_ID_SIDE_A,LOCATION_NAME_SIDE_A,WAREHOUSE_ID_SIDE_A,STRAND_NB_SIDE_A,TUBE_NB_SIDE_A,NETWORK_LEVEL_SIDE_A,STRAND_NB_SIDE_B,TUBE_NB_SIDE_B,NETWORK_LEVEL_SIDE_B,LOCATION_TYPE_SIDE_B,LOCATION_ID_SIDE_B,LOCATION_NAME_SIDE_B,WAREHOUSE_ID_SIDE_B FROM JUNCTION_MAPPING B WHERE B.JCT_ID='"
									+ junctionID + "' ")
							.getResultList();

					List<Object[]> junctionDetails = session.createNativeQuery(
							"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,(SELECT COUNT(DISTINCT B.STRAND_ID_SIDE_A) FROM JUNCTION_MAPPING B WHERE B.STRAND_ID_SIDE_A !='null' AND B.JCT_ID='"
									+ junctionID
									+ "'),(SELECT COUNT(DISTINCT B.STRAND_ID_SIDE_B) FROM JUNCTION_MAPPING B WHERE B.STRAND_ID_SIDE_B !='null' AND B.JCT_ID='"
									+ junctionID + "') FROM JUNCTION A WHERE A.JUNCTION_ID='" + junctionID + "' ")
							.getResultList();

					rtn.put("junctionDetails", junctionDetails);
					rtn.put("junctionMappingList", junctionMappingPts);
					rtn.put("JunctionID", junctionID);

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in saveJunction due to \n " + exceptionAsString);
					logger.info("Error in saveJunction due to \n " + exceptionAsString);
					// rtn.put("manholeJctID", null);
				}

				finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}
			}
		}
		return rtn;
	
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SearchForManholeHandhole", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SearchForManholeHandhole(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {

			Query physicalLayerDetailsQuery;
			session = AlmDbSession.getInstance().getSession();

			String physLayerSearch = request.getParameter("physLayerSearch");
			String physicalLayer = request.getParameter("physicalLayer");
			String ProjectId = request.getParameter("ProjectId");

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
					if (StringUtils.equalsIgnoreCase(physicalLayer, "Manhole")) {
						physicalLayerDetailsQuery = session.createNativeQuery(
								"SELECT MANHOLE_NAME,MANHOLE_ID,LONGITUDE,LATITUDE,CITY FROM MANHOLE  WHERE PROJECT_ID ='"
										+ ProjectId
										+ "' AND ( (UPPER(MANHOLE_ID) LIKE UPPER(:param)  OR UPPER(MANHOLE_NAME) LIKE UPPER(:param)) ) ");

						physicalLayerDetailsQuery.setParameter("param", "%" + physLayerSearch + "%");
						physicalLayerDetailsQuery.setFirstResult(0);
						physicalLayerDetailsQuery.setMaxResults(40);

					}

					else {

						physicalLayerDetailsQuery = session.createNativeQuery(
								"SELECT HANDHOLE_NAME,HANDHOLE_ID,LONGITUDE,LATITUDE,CITY FROM HANDHOLE a WHERE PROJECT_ID ='"
										+ ProjectId
										+ "' AND ( (UPPER(HANDHOLE_ID) LIKE UPPER(:param)  OR UPPER(HANDHOLE_NAME) LIKE UPPER(:param)))  ");

						physicalLayerDetailsQuery.setParameter("param", "%" + physLayerSearch + "%");
						physicalLayerDetailsQuery.setFirstResult(0);
						physicalLayerDetailsQuery.setMaxResults(40);

					}

					if (physicalLayerDetailsQuery.getResultList() != null
							&& physicalLayerDetailsQuery.getResultList().size() != 0) {

						System.out.println("searchPhysicalLayResult " + physicalLayerDetailsQuery.getResultList());
						rtn.put("searchPhysicalLayerDetails", physicalLayerDetailsQuery.getResultList());
					} else {
						rtn.put("searchPhysicalLayerDetails", "");
					}

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in SearchForManholeHandhole due to \n " + exceptionAsString);
					logger.info("Error in SearchForManholeHandhole due to \n " + exceptionAsString);
					rtn.put("searchPhysicalLayerDetails", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}
			}
		}
		return rtn;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveDuct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDuct(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {


		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);

			try {

				String ductCreatedByUser = request.getParameter("ductCreatedByUser");
				String ductModifiedByUser = request.getParameter("ductModifiedByUser");
				Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

				String ductCreatedDate = request.getParameter("ductCreatedDate");
				Timestamp ductCreationDate;
				if (StringUtils.equalsIgnoreCase(ductCreatedDate, "")) {
					ductCreationDate = lastModifiedDate;

				} else {
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					ductCreationDate = new Timestamp(
							formatter.parse(request.getParameter("ductCreatedDate")).getTime());
				}

				String ductId = request.getParameter("ductID");

				if (StringUtils.equalsIgnoreCase(ductId, "")) {
					synchronized (this) {
						// ductId = "Duct" + year + "_" + appConfig.getSeqNbr(74,session);
						ductId = "Duct_" + year + "_" + Integer.parseInt(
								session.createNativeQuery("SELECT DUCT FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET DUCT = DUCT + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
					}
				}
				String ductAuxFlag = request.getParameter("ductAuxFlag");

				Float numFiberCables = (float) 0;
				Float numFiberTubes = (float) 0;
				Float numFiberStrands = (float) 0;
				Float length = (float) 0;

				if (request.getParameter("ductsFiberCables") != "") {
					numFiberCables = Float.parseFloat(request.getParameter("ductsFiberCables"));
				}
				if (request.getParameter("ductsTubes") != "") {
					numFiberTubes = Float.parseFloat(request.getParameter("ductsTubes"));
				}
				if (request.getParameter("ductsStrands") != "") {
					numFiberStrands = Float.parseFloat(request.getParameter("ductsStrands"));
				}
				if (request.getParameter("ductsLength") != "") {
					length = Float.parseFloat(request.getParameter("ductsLength"));
				}

				float totalDrivingDistance = 0;
				float totalGeoDistance = 0;
				float lastAuxToDestDistance = 0;
				float lastAuxToDestDrivDistance = 0;
				if (request.getParameter("totalDrivingDistance") != "") {
					totalDrivingDistance = Float.parseFloat(request.getParameter("totalDrivingDistance"));
				}
				if (request.getParameter("totalGeoDistance") != "") {
					totalGeoDistance = Float.parseFloat(request.getParameter("totalGeoDistance"));
				}
				if (request.getParameter("lastAuxToDestDistance") != "") {
					lastAuxToDestDistance = Float.parseFloat(request.getParameter("lastAuxToDestDistance"));
				}
				if (request.getParameter("lastAuxToDestDrivDistance") != "") {
					lastAuxToDestDrivDistance = Float.parseFloat(request.getParameter("lastAuxToDestDrivDistance"));
				}

				String drawingtype = request.getParameter("drawingType");

				Duct duct = new Duct();

				duct.setDuctID(ductId);
				duct.setTrenchID(request.getParameter("trenchId"));
				duct.setDuctName(request.getParameter("ductName"));
				if (request.getParameter("SourceDuct").contains("WARE") == true) {
					duct.setSourceWareId(request.getParameter("SourceDuct").split(":")[0]);
					duct.setSourceId(request.getParameter("SourceDuct").split(":")[2]);
					duct.setSourceName(request.getParameter("SourceDuct").split(":")[1]);
				} else if (request.getParameter("SourceDuct").contains("CUST") == true) {
					duct.setSourceWareId("null");
					duct.setSourceId(request.getParameter("SourceDuct").split(":")[0]);
					duct.setSourceName(request.getParameter("SourceDuct").split(":")[1]);
				} else if (request.getParameter("SourceDuct").contains("MH") == true
						|| request.getParameter("SourceDuct").contains("HH") == true
						|| request.getParameter("SourceDuct").contains("DB") == true) {
					duct.setSourceWareId("null");
					duct.setSourceId(request.getParameter("SourceDuct").split(":")[0]);
					duct.setSourceName(request.getParameter("SourceDuct").split(":")[1]);
				} else {
					duct.setSourceWareId("null");
					duct.setSourceId("null");
					duct.setSourceName(request.getParameter("SourceDuct"));
				}
				if (request.getParameter("DestinationDuct").contains("WARE") == true) {
					duct.setDestinationWareId(request.getParameter("DestinationDuct").split(":")[0]);
					duct.setDestinationId(request.getParameter("DestinationDuct").split(":")[2]);
					duct.setDestinationName(request.getParameter("DestinationDuct").split(":")[1]);
				} else if (request.getParameter("DestinationDuct").contains("CUST") == true) {
					duct.setDestinationWareId("null");
					duct.setDestinationId(request.getParameter("DestinationDuct").split(":")[0]);
					duct.setDestinationName(request.getParameter("DestinationDuct").split(":")[1]);
				} else if (request.getParameter("DestinationDuct").contains("MH") == true
						|| request.getParameter("DestinationDuct").contains("HH") == true
						|| request.getParameter("DestinationDuct").contains("DB") == true) {
					duct.setDestinationWareId("null");
					duct.setDestinationId(request.getParameter("DestinationDuct").split(":")[0]);
					duct.setDestinationName(request.getParameter("DestinationDuct").split(":")[1]);
				} else {
					duct.setDestinationWareId("null");
					duct.setDestinationId("null");
					duct.setDestinationName(request.getParameter("DestinationDuct"));
				}

				duct.setSrcLong(request.getParameter("SourceDuctLng"));
				duct.setSrcLat(request.getParameter("SourceDuctLat"));
				duct.setDestLong(request.getParameter("DestinationDuctLng"));
				duct.setDestLat(request.getParameter("DestinationDuctLat"));
				duct.setSrcCity(request.getParameter("srcCityDuct"));
				duct.setDestCity(request.getParameter("dstCityDuct"));
				duct.setTrenchID(request.getParameter("trenchId"));
				duct.setNumFiberCables(numFiberCables);
				duct.setNumFiberTubes(numFiberTubes);
				duct.setNumFiberStrands(numFiberStrands);
				duct.setLength(length);
				duct.setCreationDate(ductCreationDate);
				duct.setLastModifieddate(lastModifiedDate);
				duct.setCreatedBy(ductCreatedByUser);
				duct.setLastmodifiedBy(ductModifiedByUser);
				duct.setTotaldriving(totalDrivingDistance);
				duct.setTotalGeoDist(totalGeoDistance);
				duct.setLastAuxToDestDistance(lastAuxToDestDistance);
				duct.setLastAuxToDestDrivDistance(lastAuxToDestDrivDistance);
				duct.setDrawingType(drawingtype);
				duct.setDuctOwner(request.getParameter("ductOwner"));
				duct.setDuctInstaller(request.getParameter("ductInstaller"));
				duct.setDuctEngineerName(request.getParameter("ductEngineerName"));

				session.saveOrUpdate(duct);
				session.flush();
				session.clear();

				query = session.createNativeQuery("UPDATE TRENCH SET NUM_DUCTS = NUM_DUCTS + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();

				int auxArraySize = 0;
				if (StringUtils.equalsIgnoreCase(ductAuxFlag, "opened")
						|| StringUtils.equalsIgnoreCase(ductAuxFlag, "new duct")) {
					query = session
							.createNativeQuery("delete from DUCT_AUXILIARY_POINTS where DUCT_ID='" + ductId + "'");
					query.executeUpdate();

					if (itemParameters.getDictParameter().size() > 0) {
						if (itemParameters.getDictParameter().size() > 1500) {
							auxArraySize = 1500;
						} else {
							auxArraySize = itemParameters.getDictParameter().size();
						}
						for (int i = 0; i < auxArraySize; i++) {
							String aux_ID;
							synchronized (this) {
								// String aux_ID = "AUXILIARY_DUCT_PT_" + year + "_" +
								// appConfig.getSeqNbr(75,session);
								aux_ID = "AUXILIARY_DUCT_PT_" + year + "_" + Integer.parseInt(session
										.createNativeQuery("SELECT DUCTAUX FROM SEQ_TABLE").uniqueResult().toString());
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET DUCTAUX = DUCTAUX + 1 ");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
							}
							Float distance = Float
									.parseFloat(itemParameters.getDictParameter().get(i).get("distance_From_Source"));
							Float aux_seqSorting = Float
									.parseFloat(itemParameters.getDictParameter().get(i).get("aux_seqSorting"));

							Double drivingDistance = itemParameters.getDictParameter().get(i)
									.get("drivingDistance") == ""
											? 0
											: itemParameters.getDictParameter().get(i).get("drivingDistance") == null
													? 0
													: StringUtils.equalsIgnoreCase(itemParameters.getDictParameter()
															.get(i).get("drivingDistance"), "null")
																	? 0
																	: Double.parseDouble(
																			itemParameters.getDictParameter().get(i)
																					.get("drivingDistance"));
							Double geoDistance = itemParameters.getDictParameter().get(i).get("geoDistance") == "" ? 0
									: itemParameters.getDictParameter().get(i).get("geoDistance") == null ? 0
											: StringUtils.equalsIgnoreCase(
													itemParameters.getDictParameter().get(i).get("geoDistance"), "null")
															? 0
															: Double.parseDouble(itemParameters.getDictParameter()
																	.get(i).get("geoDistance"));

							DuctAuxPoints ductAuxPoint = new DuctAuxPoints();
							ductAuxPoint.setAuxID(aux_ID);
							ductAuxPoint.setDuctID(ductId);
							if (itemParameters.getDictParameter().get(i).get("auxDuct_Name").contains("WARE") == true) {
								ductAuxPoint.setWareID(
										itemParameters.getDictParameter().get(i).get("auxDuct_Name").split(":")[0]);
								ductAuxPoint.setAuxPointID(
										itemParameters.getDictParameter().get(i).get("auxDuct_Name").split(":")[2]);
								ductAuxPoint.setAuxPointName(
										itemParameters.getDictParameter().get(i).get("auxDuct_Name").split(":")[1]);
							} else if (itemParameters.getDictParameter().get(i).get("auxDuct_Name")
									.contains("Auxiliary_Point") == true) {
								ductAuxPoint.setWareID("null");
								ductAuxPoint.setAuxPointID("null");
								if (itemParameters.getDictParameter().get(i).get("auxDuct_Name")
										.contains("AUXILIARY_DUCT_PT") == true) {
									ductAuxPoint.setAuxPointName(
											itemParameters.getDictParameter().get(i).get("auxDuct_Name").split(":")[1]);
								} else {
									ductAuxPoint.setAuxPointName(
											itemParameters.getDictParameter().get(i).get("auxDuct_Name"));
								}
							} else if (itemParameters.getDictParameter().get(i).get("auxDuct_Name")
									.contains("MH") == true
									|| itemParameters.getDictParameter().get(i).get("auxDuct_Name")
											.contains("HH") == true
									|| itemParameters.getDictParameter().get(i).get("auxDuct_Name")
											.contains("DB") == true) {
								ductAuxPoint.setWareID("null");
								ductAuxPoint.setAuxPointID(
										itemParameters.getDictParameter().get(i).get("auxDuct_Name").split(":")[0]);
								ductAuxPoint.setAuxPointName(
										itemParameters.getDictParameter().get(i).get("auxDuct_Name").split(":")[1]);
							} else {
								ductAuxPoint.setWareID("null");
								ductAuxPoint.setAuxPointID("null");
								ductAuxPoint
										.setAuxPointName(itemParameters.getDictParameter().get(i).get("auxDuct_Name"));
							}

							ductAuxPoint.setLong(itemParameters.getDictParameter().get(i).get("aux_Longitude"));
							ductAuxPoint.setLat(itemParameters.getDictParameter().get(i).get("aux_Latitude"));
							ductAuxPoint.setDistancefromsource(distance);
							ductAuxPoint.setSeqSorting(aux_seqSorting);
							ductAuxPoint.setCreationDate(ductCreationDate);
							ductAuxPoint.setLastModifieddate(lastModifiedDate);
							ductAuxPoint.setDrivingDist(drivingDistance);
							ductAuxPoint.setGeoDist(geoDistance);
							session.saveOrUpdate(ductAuxPoint);
							session.flush();
							session.clear();

						}
					}
				}
				List<Object[]> ductAuxiliaryList = session.createNativeQuery(
						"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,DUCT_ID,SEQ_SORTING,DRIVING_DISTANCE,GEO_DISTANCE,AUXILIARY_ID FROM DUCT_AUXILIARY_POINTS WHERE DUCT_ID='"
								+ ductId + "' ORDER BY SEQ_SORTING ASC ")
						.getResultList();

				rtn.put("ductId", ductId);
				rtn.put("ductAuxiliaryList", ductAuxiliaryList);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in saveDuct due to \n " + exceptionAsString);
				logger.info("Error in saveDuct due to \n " + exceptionAsString);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();

				}
			}
		}

		return rtn;
	
	}

	@RequestMapping(value = "/findCountDucts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountDucts(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			try {
				@SuppressWarnings("unchecked")

				String trenchId = request.getParameter("trenchId");

				query = session.createNativeQuery("select count (*) from DUCTS where trench_Id like :param");
				query.setParameter("param", "%" + trenchId + "%");
				Object countDucts = query.getSingleResult();
				rtn.put("countDucts", countDucts);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountDucts due to \n " + exceptionAsString);
				logger.info("Error in findCountDucts due to \n " + exceptionAsString);
				rtn.put("countDucts", null);
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
	@RequestMapping(value = "/findJunctionDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findJunctionDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {


		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				String JunctionID = request.getParameter("JunctionID");
				// System.out.println("JunctionID "+JunctionID);
				try {
					List<Object[]> junctionDetails = session.createNativeQuery(
							"SELECT DISTINCT A.JUNCTION_NAME,A.JUNCTION_ID,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.LONGITUDE,A.LATITUDE,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,(SELECT COUNT(DISTINCT B.STRAND_ID_SIDE_A) FROM JUNCTION_MAPPING B WHERE B.STRAND_ID_SIDE_A!='null' AND B.JCT_ID='"
									+ JunctionID
									+ "'),(SELECT COUNT(DISTINCT B.STRAND_ID_SIDE_B) FROM JUNCTION_MAPPING B WHERE B.STRAND_ID_SIDE_B !='null' AND B.JCT_ID='"
									+ JunctionID
									+ "'), TO_CHAR(A.CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(A.LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),OWNER,JUNC_INSTALLER,JUNC_ENGINEER_NAME FROM JUNCTION A WHERE A.JUNCTION_ID='"
									+ JunctionID + "' ")
							.getResultList();

					List<Object[]> junctionMappingPts = session.createNativeQuery(
							"SELECT DISTINCT SEQUENCE_NUMBER,JCT_MAPPING_ID,STRAND_ID_SIDE_A,STRAND_NAME_SIDE_A,TUBE_ID_SIDE_A,TUBE_NAME_SIDE_A,FIBER_ID_SIDE_A,FIBER_NAME_SIDE_A,STRAND_ID_SIDE_B,STRAND_NAME_SIDE_B,TUBE_ID_SIDE_B,TUBE_NAME_SIDE_B,FIBER_ID_SIDE_B,FIBER_NAME_SIDE_B,LOCATION_TYPE_SIDE_A,LOCATION_ID_SIDE_A,LOCATION_NAME_SIDE_A,WAREHOUSE_ID_SIDE_A,STRAND_NB_SIDE_A,TUBE_NB_SIDE_A,NETWORK_LEVEL_SIDE_A,STRAND_NB_SIDE_B,TUBE_NB_SIDE_B,NETWORK_LEVEL_SIDE_B,LOCATION_TYPE_SIDE_B,LOCATION_ID_SIDE_B,LOCATION_NAME_SIDE_B,WAREHOUSE_ID_SIDE_B FROM JUNCTION_MAPPING B WHERE B.JCT_ID='"
									+ JunctionID + "' ORDER BY SEQUENCE_NUMBER ASC")
							.getResultList();

					rtn.put("junctionDetails", junctionDetails);
					rtn.put("junctionMappingPts", junctionMappingPts);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in findJunctionDetails due to \n " + exceptionAsString);
					logger.info("Error in findJunctionDetails due to \n " + exceptionAsString);
					rtn.put("junctionDetails", null);
					rtn.put("junctionMappingPts", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}
			}
		}
		return rtn;
	
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findJctMappingDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findJctMappingDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;

		} else {
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String junctionID = request.getParameter("junctionID");

					List<Object[]> junctionMappingPts = session.createNativeQuery(
							"SELECT DISTINCT SEQUENCE_NUMBER,JCT_MAPPING_ID,STRAND_ID_SIDE_A,STRAND_NAME_SIDE_A,TUBE_ID_SIDE_A,TUBE_NAME_SIDE_A,FIBER_ID_SIDE_A,FIBER_NAME_SIDE_A,STRAND_ID_SIDE_B,STRAND_NAME_SIDE_B,TUBE_ID_SIDE_B,TUBE_NAME_SIDE_B,FIBER_ID_SIDE_B,FIBER_NAME_SIDE_B,(SELECT A.JUNCTION_NAME FROM JUNCTION A WHERE A.JUNCTION_ID='"
									+ junctionID + "'),(SELECT JUNCTION_NUMBER FROM JUNCTION  WHERE JUNCTION_ID='"
									+ junctionID + "'),LOCATION_NAME_SIDE_A,LOCATION_NAME_SIDE_B,TUBE_NB_SIDE_A,STRAND_NB_SIDE_A,TUBE_NB_SIDE_B,STRAND_NB_SIDE_B FROM JUNCTION_MAPPING B WHERE B.JCT_ID='" + junctionID + "' ")
							.getResultList();

					if (junctionMappingPts.size() > 0) {
						rtn.put("junctionMappingPts", junctionMappingPts);
					} else {
						rtn.put("junctionMappingPts", "noData");
					}
					System.out.println("junctionMappingPts  data is : " + junctionMappingPts);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in findJctMappingDetails due to \n " + exceptionAsString);
					logger.info("Error in findJctMappingDetails due to \n " + exceptionAsString);
					rtn.put("junctionMappingPts", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateJunction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateJunction(Locale locale, @ModelAttribute ItemParameters itemParameters, Model model,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;

		} else {
			if (session != null && session.isOpen()) {

				tx = session.beginTransaction();
				try {
					Date date = new Date();
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(date);
					int year = calendar.get(Calendar.YEAR);
					String junctionID = "";

					Float junctionCapacity = (float) 0;
					Float junctionNumber = (float) 0;

					String junctionName = request.getParameter("JunctionName");
					String physLayerIdJunction = request.getParameter("LayerIdJunction");
					junctionID = request.getParameter("JunctionID");

					if (request.getParameter("JunctionCapacity") != "") {

						junctionCapacity = Float.parseFloat(request.getParameter("JunctionCapacity"));
					}

					if (request.getParameter("JunctionNum") != "") {

						junctionNumber = Float.parseFloat(request.getParameter("JunctionNum"));
					}

					Query updateJunction = session.createNativeQuery("UPDATE JUNCTION SET JUNCTION_NAME = '"
							+ junctionName + "',CAPACITY = '" + junctionCapacity + "',JUNCTION_NUMBER ='"
							+ junctionNumber + "' " + " WHERE JUNCTION_ID = '" + junctionID + "'");
					updateJunction.executeUpdate();

					float JctSeq = 0;
					String JctStrandIdSideA = "";
					String JctStrandNameSideA = "";
					String JctTubeIdSideA = "";
					String JctTubeNameSideA = "";
					String JctFiberIdSideA = "";
					String JctFiberNameSideA = "";
					String JctStrandIdSideB = "";
					String JctStrandNameSideB = "";
					String JctTubeIdSideB = "";
					String JctTubeNameSideB = "";
					String JctFiberIdSideB = "";
					String JctFiberNameSideB = "";

					String mappingId = "";

					if (itemParameters.getDictParameter().size() > 0) {

						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
							synchronized (this) {
								// mappingId = "JCT_MAP_" + year + "_" + appConfig.getSeqNbr(77,session);
								mappingId = "JCT_MAP_" + year + "_"
										+ Integer.parseInt(
												session.createNativeQuery("SELECT JUNCTION_MAP FROM SEQ_TABLE")
														.uniqueResult().toString());
								System.out.println("man id " + junctionID);
								query = session.createNativeQuery("UPDATE SEQ_TABLE SET JUNCTIN_MAP = JUNCTION_MAP +1");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
								System.out.println("the update junction id map is " + mappingId);
							}
							JctSeq = Float.parseFloat(itemParameters.getDictParameter().get(i).get("JctSeq"));

							JctStrandIdSideA = itemParameters.getDictParameter().get(i).get("JctStrandIdSideA");
							JctStrandNameSideA = itemParameters.getDictParameter().get(i).get("JctStrandNameSideA");
							JctTubeIdSideA = itemParameters.getDictParameter().get(i).get("JctTubeIdSideA");
							JctTubeNameSideA = itemParameters.getDictParameter().get(i).get("JctTubeNameSideA");
							JctFiberIdSideA = itemParameters.getDictParameter().get(i).get("JctFiberIdSideA");
							JctFiberNameSideA = itemParameters.getDictParameter().get(i).get("JctFiberNameSideA");

							JctStrandIdSideB = itemParameters.getDictParameter().get(i).get("JctStrandIdSideB");
							JctStrandNameSideB = itemParameters.getDictParameter().get(i).get("JctStrandNameSide");
							JctTubeIdSideB = itemParameters.getDictParameter().get(i).get("JctTubeIdSideB");
							JctTubeNameSideB = itemParameters.getDictParameter().get(i).get("JctTubeNameSideB");
							JctFiberIdSideB = itemParameters.getDictParameter().get(i).get("JctFiberIdSideB");
							JctFiberNameSideB = itemParameters.getDictParameter().get(i).get("JctFiberNameSideB");

							Query insertMappingJctQuery = session
									.createNativeQuery("INSERT INTO JUNCTION_MAPPING VALUES ('" + junctionID + "','"
											+ JctSeq + "','" + mappingId + "','" + physLayerIdJunction + "','"
											+ JctStrandIdSideA + "','" + JctStrandNameSideA + "','" + JctTubeIdSideA
											+ "','" + JctTubeNameSideA + "','" + JctFiberIdSideA + "','"
											+ JctFiberNameSideA + "','" + JctStrandIdSideB + "','" + JctStrandNameSideB
											+ "','" + JctTubeIdSideB + "','" + JctTubeNameSideB + "','"
											+ JctFiberIdSideB + "','" + JctFiberNameSideB + "' )");
							insertMappingJctQuery.executeUpdate();
						}
					}

					// System.out.println("itemParamTEmp: "+itemParameters.getDictParameterTemp());

					if (itemParameters.getDictParameterTemp().size() > 0) {
						for (int i = 0; i < itemParameters.getDictParameterTemp().size(); i++) {

							JctSeq = Float.parseFloat(itemParameters.getDictParameterTemp().get(i).get("JctSeq"));

							JctStrandIdSideA = itemParameters.getDictParameterTemp().get(i).get("JctStrandIdSideA");
							JctStrandNameSideA = itemParameters.getDictParameterTemp().get(i).get("JctStrandNameSideA");
							JctTubeIdSideA = itemParameters.getDictParameterTemp().get(i).get("JctTubeIdSideA");
							JctTubeNameSideA = itemParameters.getDictParameterTemp().get(i).get("JctTubeNameSideA");
							JctFiberIdSideA = itemParameters.getDictParameterTemp().get(i).get("JctFiberIdSideA");
							JctFiberNameSideA = itemParameters.getDictParameterTemp().get(i).get("JctFiberNameSideA");

							JctStrandIdSideB = itemParameters.getDictParameterTemp().get(i).get("JctStrandIdSideB");
							JctStrandNameSideB = itemParameters.getDictParameterTemp().get(i).get("JctStrandNameSide");
							JctTubeIdSideB = itemParameters.getDictParameterTemp().get(i).get("JctTubeIdSideB");
							JctTubeNameSideB = itemParameters.getDictParameterTemp().get(i).get("JctTubeNameSideB");
							JctFiberIdSideB = itemParameters.getDictParameterTemp().get(i).get("JctFiberIdSideB");
							JctFiberNameSideB = itemParameters.getDictParameterTemp().get(i).get("JctFiberNameSideB");
							mappingId = itemParameters.getDictParameterTemp().get(i).get("jctMappingId");

							Query insertMappingJctQuery = session
									.createNativeQuery("UPDATE JUNCTION_MAPPING SET SEQUENCE_NUMBER= '" + JctSeq
											+ "',STRAND_ID_SIDE_A = '" + JctStrandIdSideA + "',STRAND_NAME_SIDE_A = '"
											+ JctStrandNameSideA + "'," + "TUBE_ID_SIDE_A = '" + JctTubeIdSideA
											+ "',TUBE_NAME_SIDE_A = '" + JctTubeNameSideA + "',FIBER_ID_SIDE_A = '"
											+ JctFiberIdSideA + "',FIBER_NAME_SIDE_A = '" + JctFiberNameSideA + "', "
											+ " STRAND_ID_SIDE_B = '" + JctStrandIdSideB + "',STRAND_NAME_SIDE_B = '"
											+ JctStrandNameSideB + "',TUBE_ID_SIDE_B = '" + JctTubeIdSideB
											+ "',TUBE_NAME_SIDE_B = '" + JctTubeNameSideB + "', "
											+ " FIBER_ID_SIDE_B = '" + JctFiberIdSideB + "',FIBER_NAME_SIDE_B = '"
											+ JctFiberNameSideB + "'" + " WHERE JCT_MAPPING_ID = '" + mappingId + "'");

							insertMappingJctQuery.executeUpdate();

						}
					}

					System.out.println("itemParamDel : " + itemParameters.getDictParameterDel());
					for (int i = 0; i < itemParameters.getDictParameterDel().size(); i++) {
						System.out.println("hiii" + itemParameters.getDictParameterDel().get(i).get("jctMappingId"));
						Query deleteJunctionMapping = session
								.createNativeQuery("DELETE FROM JUNCTION_MAPPING WHERE JCT_MAPPING_ID='"
										+ itemParameters.getDictParameterDel().get(i).get("jctMappingId") + "'");
						deleteJunctionMapping.executeUpdate();
					}

					// List<Object[]> countJct=session.createNativeQuery("select count (*) from
					// JUNCTION where JUNCTION_ID = '"+junctionID+"' ").getResultList();
					// rtn.put("countJct",countJct);

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in updateJunction due to \n " + exceptionAsString);
					logger.info("Error in updateJunction due to \n " + exceptionAsString);
				}

				finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getManholeData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getManholeData(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		Query query;
		session = AlmDbSession.getInstance().getSession();
		String search = request.getParameter("search");

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createNativeQuery(
						"SELECT MANHOLE_ID,MANHOLE_NAME,CITY,LONGITUDE,LATITUDE FROM MANHOLE a WHERE UPPER(MANHOLE_ID) LIKE UPPER(:param) OR UPPER(MANHOLE_NAME) LIKE UPPER(:param) ");

				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("globalList", query.getResultList());
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getManholeData due to \n " + exceptionAsString);
				logger.info("Error in getManholeData due to \n " + exceptionAsString);
				rtn.put("searchResult", null);
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
	@RequestMapping(value = "/getHandholeData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getHandholeData(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		String search = request.getParameter("search");

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createNativeQuery(
						"SELECT HANDHOLE_ID,HANDHOLE_NAME,CITY,LONGITUDE,LATITUDE FROM HANDHOLE b WHERE UPPER(HANDHOLE_ID) LIKE UPPER(:param) OR UPPER(HANDHOLE_NAME) LIKE UPPER(:param) ");

				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("globalList", query.getResultList());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getHandholeData due to \n " + exceptionAsString);
				logger.info("Error in getHandholeData due to \n " + exceptionAsString);
				rtn.put("searchResult", null);
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
	@RequestMapping(value = "/getDbData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDbData(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null & session.isOpen()) {
			try {
				String search = request.getParameter("search");
				query = session.createQuery(
						"select DistributionBoardId,distributionBoardName,distributionBoardCity,distributionBoardLong,distributionBoardLat from DistributionBoard where DistributionBoardId LIKE UPPER(:param)");
				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("globalList", query.getResultList());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getDbData due to \n " + exceptionAsString);
				logger.info("Error in getDbData due to \n " + exceptionAsString);
				rtn.put("searchResult", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		return rtn;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getNetworkLevel", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getNetworkLevel(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String pathID = request.getParameter("pathID");
		String target = request.getParameter("target");
		List<Object[]> networkLevel = null;

		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				if (target.equals("tube")) {
					networkLevel = session.createNativeQuery(
							"SELECT FIBER_NETWORK_LEVEL FROM FIBER_CABLES WHERE FIBER_CABLE_ID ='" + pathID + "' ")
							.getResultList();
				} else {
					networkLevel = session
							.createNativeQuery(
									"SELECT TUBE_NETWORK_LEVEL FROM FIBER_TUBES WHERE TUBE_ID ='" + pathID + "' ")
							.getResultList();
				}
				rtn.put("networkLevel", networkLevel);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getNetworkLevel due to \n " + exceptionAsString);
				logger.info("Error in getNetworkLevel due to \n " + exceptionAsString);
				rtn.put("networkLevel", null);

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
	@RequestMapping(value = "/saveCableColor", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> saveCableColor(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String update = request.getParameter("update");
				System.out.println("yes update is  " + update);
				System.out.println("yes " + itemParameters.getDictParameter().size());
				String id = "", owner = "", color = "";
				Query saveCableColorQuery;
				// List Query = session.createNativeQuery("select * from FIBER_OWNER_COLOR
				// ").getResultgetResultList();
				if (itemParameters.getDictParameter().size() > 0) {
					for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
						// System.out.println(itemParameters.getDictParameter().get(i).get("id"));
						id = itemParameters.getDictParameter().get(i).get("id");
						owner = itemParameters.getDictParameter().get(i).get("owner");
						color = itemParameters.getDictParameter().get(i).get("color");
						System.out.println(itemParameters.getDictParameter().get(i).get("owner") + " color "
								+ itemParameters.getDictParameter().get(i).get("color"));
						List Query = session
								.createNativeQuery(
										"select * from FIBER_OWNER_COLOR WHERE FIBER_OWNER = '" + owner + "' ")
								.getResultList();
						System.out.println("yes size " + Query.size());
						// if(update == String.valueOf("0") ) {
						// if(StringUtils.equalsIgnoreCase(update, "0")) {
						if (Query.size() > 0) {
							// System.out.println("yes update is 1 "+update);
							// saveCableColorQuery = session.createNativeQuery("INSERT INTO
							// FIBER_OWNER_COLOR
							// (FIBER_COLOR_OWNER_ID,FIBER_COLOR_OWNER,FIBER_OWNER) VALUES ( '" + id + "' ,
							// '" + color + "' , '"+ owner+"') ");
							saveCableColorQuery = session
									.createNativeQuery("UPDATE FIBER_OWNER_COLOR SET FIBER_COLOR_OWNER = '" + color
											+ "' WHERE FIBER_OWNER = '" + owner + "' ");
						} else {
							// saveCableColorQuery = session.createNativeQuery("UPDATE FIBER_OWNER_COLOR SET
							// FIBER_COLOR_OWNER = '" + color + "' WHERE FIBER_OWNER = '" + owner + "' ");
							saveCableColorQuery = session.createNativeQuery(
									"INSERT INTO FIBER_OWNER_COLOR  (FIBER_COLOR_OWNER_ID,FIBER_COLOR_OWNER,FIBER_OWNER) VALUES ( '"
											+ id + "' , '" + color + "' , '" + owner + "') ");

						}
						saveCableColorQuery.executeUpdate();
					}
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in saveCableColor due to \n " + exceptionAsString);
				logger.info("Error in saveCableColor due to \n " + exceptionAsString);
				// rtn.put("distributionBoardId", null);
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
// to be deleted
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value = "/findMarkerPointsBetween", method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> findMarkerPointsBetween(Locale
	 * locale, Model model, HttpServletRequest request, HttpServletResponse
	 * response) throws JsonProcessingException { //
	 * logger.info("Welcome home! The client locale is {}.", locale);
	 * 
	 * Map<String, Object> rtn = new LinkedHashMap<>(); ObjectMapper mapper = new
	 * ObjectMapper(); Session session = null; Transaction tx = null; session =
	 * almsessions.getALMSession(); if (LoginServices.checkSession(request,
	 * response).equals("redirect:/")) { rtn.put("Login",
	 * LoginServices.checkSession(request, response)); return rtn; } if (session !=
	 * null && session.isOpen()) { tx = session.beginTransaction(); try { //
	 * System.out.println("Inside FindNearestPoints"); String startLongPoint =
	 * request.getParameter("startLongPoint"); String startLatPoint =
	 * request.getParameter("startLatPoint"); String endLongPoint =
	 * request.getParameter("endLongPoint"); String endLatPoint =
	 * request.getParameter("endLatPoint"); List<?> manholeList =
	 * session.createNativeQuery(
	 * "SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),DM_NAME FROM MANHOLE  "
	 * ) .getResultList(); List<Object[]> ManholeMarkerPointsBetween = new
	 * ArrayList<Object[]>(); //
	 * System.out.println("manholeList "+mapper.writeValueAsString(manholeList));
	 * ManholeMarkerPointsBetween = findMarkerPointsArrayBetween(manholeList,
	 * Double.valueOf(startLongPoint), Double.valueOf(startLatPoint),
	 * Double.valueOf(endLongPoint), Double.valueOf(endLatPoint), "Manhole");
	 * 
	 * List<?> handholeList = session.createNativeQuery(
	 * "SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),DM_NAME FROM HANDHOLE"
	 * ) .getResultList(); List<Object[]> HandholeMarkerPointsBetween = new
	 * ArrayList<Object[]>(); HandholeMarkerPointsBetween =
	 * findMarkerPointsArrayBetween(handholeList, Double.valueOf(startLongPoint),
	 * Double.valueOf(startLatPoint), Double.valueOf(endLongPoint),
	 * Double.valueOf(endLatPoint), "Handhole");
	 * 
	 * List<?> distribBoardList = session.createNativeQuery(
	 * "SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY FROM DISTRIBUTION_BOARD"
	 * ) .getResultList(); List<Object[]> DistribBoardMarkerPointsBetween = new
	 * ArrayList<Object[]>(); DistribBoardMarkerPointsBetween =
	 * findMarkerPointsArrayBetween(distribBoardList,
	 * Double.valueOf(startLongPoint), Double.valueOf(startLatPoint),
	 * Double.valueOf(endLongPoint), Double.valueOf(endLatPoint), "DistribBoard");
	 * 
	 * List<Object[]> nearstPoints = new ArrayList<Object[]>();
	 * nearstPoints.addAll(ManholeMarkerPointsBetween);
	 * nearstPoints.addAll(HandholeMarkerPointsBetween);
	 * nearstPoints.addAll(DistribBoardMarkerPointsBetween);
	 * 
	 * String[] idsArray = findListId(nearstPoints, "all"); //
	 * System.out.println("listIds "+mapper.writeValueAsString(idsArray)); Query
	 * fiberQuery = session.createNativeQuery(
	 * "SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL FROM FIBER_CABLES A LEFT  JOIN FIBER_AUXILIARY_POINTS D ON A.FIBER_CABLE_ID=D.FIBER_CABLE_ID WHERE A.SOURCE_ID IN (:param1) OR A.DESTINATION_ID IN (:param1) OR D.AUXILIARY_POINT_ID IN (:param1)"
	 * ); fiberQuery.setParameter("param1", idsArray); List<Object>
	 * fiberCableMarkerPointsBetween = fiberQuery.getResultList(); //
	 * System.out.println("fiberCableMarkerPointsBetween //
	 * "+mapper.writeValueAsString(fiberCableMarkerPointsBetween));
	 * 
	 * Query fiberAuxiliaryQuery = session.createNativeQuery(
	 * "SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND AUXILIARY_POINT_ID IN (:param1) ORDER BY B.SEQ_SORTING ASC"
	 * ); fiberAuxiliaryQuery.setParameter("param1",
	 * findListId(fiberCableMarkerPointsBetween, "FiberCable")); List<Object>
	 * fiberAuxiliaryMarkerPointsBetween = fiberAuxiliaryQuery.getResultList();
	 * 
	 * /* Query fiberTubesQuery = session.
	 * createNativeQuery("SELECT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME FROM FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID AND b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1)"
	 * ); fiberTubesQuery.setParameter("param1",idsArray); List<Object> fiberTubes =
	 * fiberTubesQuery.getResultList();
	 * 
	 * 
	 * Query tubesAuxiliariesQuery = session.
	 * createNativeQuery("SELECT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID FROM TUBE_AUXILIARY_POINTS c,FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.TUBE_ID=c.TUBE_ID AND AUXILIARY_POINT_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC"
	 * ); tubesAuxiliariesQuery.setParameter("param1",idsArray); List<Object>
	 * tubesAuxiliaries = tubesAuxiliariesQuery.getResultList();
	 * 
	 * Query fiberStrandsQuery = session.
	 * createNativeQuery("SELECT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE,b.DESTINATION,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID AND b.SOURCE IN (:param1) OR b.DESTINATION IN (:param1)"
	 * ); fiberStrandsQuery.setParameter("param1",idsArray); List<Object>
	 * fiberStrands = fiberStrandsQuery.getResultList();
	 * 
	 * //Query strandsAuxiliariesQuery = session.
	 * createNativeQuery("SELECT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.AUXILIARY_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID AND AUXILIARY_POINT_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC "
	 * ); //strandsAuxiliariesQuery.setParameter("param1",idsArray); //List<Object>
	 * strandsAuxiliaries = strandsAuxiliariesQuery.getResultList();
	 */

	// System.out.println("ManholeMarkerPointsBetween
	// "+mapper.writeValueAsString(ManholeMarkerPointsBetween));
	/*
	 * rtn.put("ManholeMarkerPointsBetween", ManholeMarkerPointsBetween);
	 * rtn.put("HandholeMarkerPointsBetween", HandholeMarkerPointsBetween);
	 * rtn.put("DistribBoardMarkerPointsBetween", DistribBoardMarkerPointsBetween);
	 * rtn.put("fiberCableMarkerPointsBetween", fiberCableMarkerPointsBetween);
	 * rtn.put("fiberAuxiliaryMarkerPointsBetween",
	 * fiberAuxiliaryMarkerPointsBetween); } catch (Exception e) { sw = new
	 * StringWriter(); e.printStackTrace(new PrintWriter(sw)); exceptionAsString =
	 * sw.toString(); logger.finest("Error in findMarkerPointsBetween due to \n " +
	 * exceptionAsString); logger.info("Error in findMarkerPointsBetween due to \n "
	 * + exceptionAsString); }
	 * 
	 * finally { if (session != null && session.isOpen()) { tx.commit();
	 * session.close(); } } } return rtn; }
	 */
	// function find marker Id array for man/hand/db between
	public String[] findListId(List<?> ListOfObjects, String Target) throws JsonProcessingException {

		Object[] objectIdArray = new Object[ListOfObjects.size()];

		for (int i = 0; i < ListOfObjects.size(); i++) {
			if (Target == "FiberCable") {
				objectIdArray[i] = (String) ((Object[]) ListOfObjects.get(i))[4];
			} else {
				objectIdArray[i] = (String) ((Object[]) ListOfObjects.get(i))[0];
			}

		}
		String[] stringArray = Arrays.copyOf(objectIdArray, objectIdArray.length, String[].class);
		// System.out.println("stringArray "+mapper.writeValueAsString(stringArray));

		return stringArray;
	}

	// function find nearest array for man/hand/db
	public List<Object[]> findNearestArray(List<?> ListOfObjects, double closestLatPoint, double closestLongPoint,
			double closestDisRange, String Target, String noOfPoints) throws JsonProcessingException {
		List<Object[]> nearstPointsArray = new ArrayList<Object[]>();
		List<Object[]> nearstPointsArraySorted = new ArrayList<Object[]>();
		List<Object[]> result = new ArrayList<Object[]>();
		double pointDist = 0.0;
		for (int i = 0; i < ListOfObjects.size(); i++) {

			Object[] objectArray = (Object[]) ListOfObjects.get(i);

			if (Target == "Manhole" || Target == "Handhole" || Target == "ManHandhole_OutOfZone") {
				pointDist = haversine(closestLatPoint, closestLongPoint, Double.valueOf((String) objectArray[3]),
						Double.valueOf((String) objectArray[2]));
			} else if (Target == "Nodes") {
				pointDist = haversine(closestLatPoint, closestLongPoint, Double.valueOf((String) objectArray[6]),
						Double.valueOf((String) objectArray[5]));
			} else {
				pointDist = haversine(closestLatPoint, closestLongPoint, Double.valueOf((String) objectArray[2]),
						Double.valueOf((String) objectArray[1]));
			}
			// System.out.println("pointDist "+pointDist);
			if (pointDist < closestDisRange || Target == "ManHandhole_OutOfZone" || Target == "DB_OutOfZone") {
				objectArray = append(objectArray, (Object) pointDist);
				// System.out.println("pass if statment ");
				nearstPointsArray.add(objectArray);

			}
		}
		if (nearstPointsArray.size() > 0) {
			// Sorting using a single loop
			int index = 0;
			List<Object[]> nearstPointsArraySortedTemp = new ArrayList<Object[]>();
			// nearstPointsArraySortedTemp= nearstPointsArray;
			double[] listofDistances = new double[nearstPointsArray.size()];
			for (int j = 0; j < nearstPointsArray.size(); j++) {

				if (Target == "DB_OutOfZone" || Target == "DistribBoard" || Target == "Nodes") {
					listofDistances[j] = Double.valueOf(String.valueOf(((Object[]) nearstPointsArray.get(j))[9]));
				} else {
					listofDistances[j] = Double.valueOf(String.valueOf(((Object[]) nearstPointsArray.get(j))[7]));
				}
			}
			System.out.println("listofDistances  " + mapper.writeValueAsString(listofDistances));
			Arrays.sort(listofDistances, 0, listofDistances.length);

			for (int j = 0; j < listofDistances.length; j++) {
				nearstPointsArraySorted.add(findUsingEnhancedForLoop(listofDistances[j], nearstPointsArray, Target));
				nearstPointsArray.remove(findUsingEnhancedForLoop(listofDistances[j], nearstPointsArray, Target));
			}
			// System.out.println("nearstPointsArraySorted
			// "+mapper.writeValueAsString(nearstPointsArraySorted));
		}
		// System.out.println("nearstPointsArray.size "+nearstPointsArray.size());
		if (nearstPointsArraySorted.size() > 0) {
			if (StringUtils.equalsIgnoreCase(noOfPoints, "") || StringUtils.equalsIgnoreCase(noOfPoints, null)) {
				result = nearstPointsArraySorted;

			} else {
				result = nearstPointsArraySorted.subList(0, Integer.valueOf(noOfPoints));
			}

		}
		return result;

	}

	// function find marker points array for man/hand/db between
	public List<Object[]> findMarkerPointsArrayBetween(List<?> ListOfObjects, double startLongPoint,
			double startLatPoint, double endLongPoint, double endLatPoint, String Target)
			throws JsonProcessingException {
		List<Object[]> result = new ArrayList<Object[]>();
		double lng = 0.0;
		double lat = 0.0;
		for (int i = 0; i < ListOfObjects.size(); i++) {
			Object[] objectArray = (Object[]) ListOfObjects.get(i);
			if (Target == "Manhole" || Target == "Handhole") {
				lat = Double.valueOf((String) objectArray[3]);
				lng = Double.valueOf((String) objectArray[2]);
			} else {
				lat = Double.valueOf((String) objectArray[2]);
				lng = Double.valueOf((String) objectArray[1]);
			}
			if (lng > endLongPoint && lat > endLatPoint && lng < startLongPoint && lat < startLatPoint) {
				result.add(objectArray);

			}

		}

		return result;

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
		double rad = 6371; // This to calculate the distance in km.
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;

	}

	static <T> T[] append(T[] arr, T element) {
		final int N = arr.length;
		arr = Arrays.copyOf(arr, N + 1);
		arr[N] = element;
		return arr;
	}

	// for sorting array in terms of distance
	public Object[] findUsingEnhancedForLoop(double distance, List<?> ListOfObjects, String Target) {
		double tempDistance = 0.0;

		for (int x = 0; x < ListOfObjects.size(); x++) {
			if (Target == "DB_OutOfZone" || Target == "DistribBoard" || Target == "Nodes") {
				tempDistance = Double.valueOf(String.valueOf(((Object[]) ListOfObjects.get(x))[9]));

			} else {
				tempDistance = Double.valueOf(String.valueOf(((Object[]) ListOfObjects.get(x))[7]));
			}
			if (distance == tempDistance) {
				return (Object[]) ListOfObjects.get(x);
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveSurvey", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSurvey(Locale locale, Model model,
			@ModelAttribute ItemParameters itemParameters, HttpServletRequest request, HttpServletResponse response)
			 {

	Map<String, Object> rtn = new LinkedHashMap<>();
	session = AlmDbSession.getInstance().getSession();
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	}
	if (session != null && session.isOpen()) {

		tx = session.beginTransaction();
		try {

			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			Survey survey;
			ManholeSurvey manholeSurvey;
			HandholeSurvey handholeSurvey;
			DistributionBoardSurvey dbSurvey;
			NodeSurvey nodeSurvey;
			FiberCableSurvey cableSurvey;
			FiberTubesSurvey tubeSurvey;
			FiberStrandsSurvey strandSurvey;
			
			String surveyID= request.getParameter("surveyID");
			System.out.println("surveyID "+surveyID);
			String ipAddress = request.getRemoteAddr();
			String updateModfUser=request.getParameter("updateModfUser");
			PhysicalLayerActivity PhyAct= new PhysicalLayerActivity();

		if (StringUtils.equalsIgnoreCase(surveyID, "") || StringUtils.equalsIgnoreCase(surveyID, null)) {
			synchronized (this) {
				surveyID = "SURV_" + year + "_" + Integer.parseInt(session
							.createNativeQuery("SELECT SURVEY FROM SEQ_TABLE").uniqueResult().toString());
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET SURVEY = SURVEY + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
			}
		}
		else {
			query = session.createNativeQuery("DELETE FROM MANHOLE_SURVEY WHERE SURVEY_ID = '" +surveyID+ "' ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			
			query = session.createNativeQuery("DELETE FROM HANDHOLE_SURVEY WHERE SURVEY_ID = '" +surveyID+ "' ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			
			query = session.createNativeQuery("DELETE FROM DISTRIBUTION_BOARD_SURVEY WHERE SURVEY_ID = '" +surveyID+ "' ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			
			query = session.createNativeQuery("DELETE FROM DISTRIBUTION_BOARD_SURVEY WHERE SURVEY_ID = '" +surveyID+ "' ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			
			query = session.createNativeQuery("DELETE FROM NODE_SURVEY WHERE SURVEY_ID = '" +surveyID+ "' ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			
			query = session.createNativeQuery("DELETE FROM FIBER_CABLES_SURVEY WHERE SURVEY_ID = '" +surveyID+ "' ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			
			query = session.createNativeQuery("DELETE FROM FIBER_TUBES_SURVEY WHERE SURVEY_ID = '" +surveyID+ "' ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			
			query = session.createNativeQuery("DELETE FROM FIBER_STRANDS_SURVEY WHERE SURVEY_ID = '" +surveyID+ "' ");
			query.executeUpdate();
			session.createNativeQuery("commit").executeUpdate();
			
		}
			rtn.put("surveyID", surveyID);

			String customerID = request.getParameter("customerID");
			String serviceReference = request.getParameter("serviceReference");
			String serviceRequest = request.getParameter("serviceRequest");
			String longitude = request.getParameter("longitude");
			String latitude = request.getParameter("latitude");
			String serviceAppNo = request.getParameter("serviceAppNo");

			String manholeSurvID="",handholeSurvID="",dbSurvID="",nodeSurvID="",cableSurvID="",tubeSurvID="",strandSurvID="";				
			Timestamp creationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());				

			survey = new Survey();
			survey.setSurveyID(surveyID);
			survey.setCustomerID(customerID);
			survey.setLongitude(longitude);
			survey.setLatitude(latitude);
			survey.setCreationDate(creationDate);
			survey.setServiceReference(serviceReference);
			survey.setServiceRequest(serviceRequest);
			survey.setServiceAppNo(serviceAppNo);
			session.saveOrUpdate(survey);
			session.flush();
			session.clear();

			String PhyActID= "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
											query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
											query.executeUpdate();
											session.createNativeQuery("commit").executeUpdate();
											
											PhyAct.setPhyActID(PhyActID);
											PhyAct.setElementID(surveyID);
											PhyAct.setScreenName("Physical Layer");
											PhyAct.setUsername(updateModfUser);
											PhyAct.setUserIP(ipAddress);
											PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
											PhyAct.setActivityDescription("Save Survey");
											session.saveOrUpdate(PhyAct);
			if (itemParameters.getDictParameter().size() > 0) {
				for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {

				manholeSurvey = new ManholeSurvey();
						synchronized (this) {

							manholeSurvID = "MH_SURV_" + year + "_"
									+ Integer.parseInt(
											session.createNativeQuery("SELECT MANHOLE_SURVEY FROM SEQ_TABLE")
													.uniqueResult().toString());
							query = session
									.createNativeQuery("UPDATE SEQ_TABLE SET MANHOLE_SURVEY = MANHOLE_SURVEY + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						Double drivingDistance, geoDistance,linearDistance;

						drivingDistance = itemParameters.getDictParameter().get(i).get("drivingDistance") == ""
								? 0
								: itemParameters.getDictParameter().get(i).get("drivingDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameter().get(i).get("drivingDistance"),
												"null") ? 0
														: Double.parseDouble(itemParameters.getDictParameter()
																.get(i).get("drivingDistance"));
						
						
						geoDistance = itemParameters.getDictParameter().get(i).get("geoDistance") == "" ? 0
								: itemParameters.getDictParameter().get(i).get("geoDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameter().get(i).get("geoDistance"),
												"null") ? 0
														: Double.parseDouble(itemParameters.getDictParameter()
																.get(i).get("geoDistance"));
						
						linearDistance = itemParameters.getDictParameter().get(i).get("linearDistance") == "" ? 0
								: itemParameters.getDictParameter().get(i).get("linearDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameter().get(i).get("linearDistance"),
												"null") ? 0
														: Double.parseDouble(itemParameters.getDictParameter()
																.get(i).get("linearDistance"));
						
						manholeSurvey.setManholeSurvID(manholeSurvID);
						manholeSurvey.setManholeID(itemParameters.getDictParameter().get(i).get("ID"));
						manholeSurvey.setManholeName(itemParameters.getDictParameter().get(i).get("Name"));
						manholeSurvey.setLongitude(itemParameters.getDictParameter().get(i).get("longitude"));
						manholeSurvey.setLatitude(itemParameters.getDictParameter().get(i).get("latitude"));
						manholeSurvey.setLinearDistance(linearDistance);
						manholeSurvey.setDrivingDistance(drivingDistance);
						manholeSurvey.setGeoDistance(geoDistance);
						manholeSurvey.setSurveyID(surveyID);
						session.saveOrUpdate(manholeSurvey);
						session.flush();
						session.clear();
					}
				}


			if (itemParameters.getDictParameterHandholeSurv().size() > 0) {
				for (int i = 0; i < itemParameters.getDictParameterHandholeSurv().size(); i++) {

				handholeSurvey = new HandholeSurvey();
						synchronized (this) {

							handholeSurvID = "HH_SURV_" + year + "_"
									+ Integer.parseInt(
											session.createNativeQuery("SELECT HANDHOLE_SURVEY FROM SEQ_TABLE")
													.uniqueResult().toString());
							query = session
									.createNativeQuery("UPDATE SEQ_TABLE SET HANDHOLE_SURVEY = HANDHOLE_SURVEY + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						Double drivingDistance, geoDistance,linearDistance;

						drivingDistance = itemParameters.getDictParameterHandholeSurv().get(i).get("drivingDistance") == ""
								? 0
								: itemParameters.getDictParameterHandholeSurv().get(i).get("drivingDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameterHandholeSurv().get(i).get("drivingDistance"),
												"null") ? 0
														: Double.parseDouble(itemParameters.getDictParameterHandholeSurv()
																.get(i).get("drivingDistance"));
						
						
						geoDistance = itemParameters.getDictParameterHandholeSurv().get(i).get("geoDistance") == "" ? 0
								: itemParameters.getDictParameterHandholeSurv().get(i).get("geoDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameterHandholeSurv().get(i).get("geoDistance"),
												"null") ? 0
														: Double.parseDouble(itemParameters.getDictParameterHandholeSurv()
																.get(i).get("geoDistance"));
						
						linearDistance = itemParameters.getDictParameterHandholeSurv().get(i).get("linearDistance") == "" ? 0
								: itemParameters.getDictParameterHandholeSurv().get(i).get("linearDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameterHandholeSurv().get(i).get("linearDistance"),
												"null") ? 0
														: Double.parseDouble(itemParameters.getDictParameterHandholeSurv()
																.get(i).get("linearDistance"));
						
						handholeSurvey.setHandholeSurvID(handholeSurvID);
						handholeSurvey.setHandholeID(itemParameters.getDictParameterHandholeSurv().get(i).get("ID"));
						handholeSurvey.setHandholeName(itemParameters.getDictParameterHandholeSurv().get(i).get("Name"));
						handholeSurvey.setLongitude(itemParameters.getDictParameterHandholeSurv().get(i).get("longitude"));
						handholeSurvey.setLatitude(itemParameters.getDictParameterHandholeSurv().get(i).get("latitude"));
						handholeSurvey.setLinearDistance(linearDistance);
						handholeSurvey.setDrivingDistance(drivingDistance);
						handholeSurvey.setGeoDistance(geoDistance);
						handholeSurvey.setSurveyID(surveyID);
						session.saveOrUpdate(handholeSurvey);
						session.flush();
						session.clear();
					}
				}
			if (itemParameters.getDictParameterDbSurv().size() > 0) {
				for (int i = 0; i < itemParameters.getDictParameterDbSurv().size(); i++) {

				dbSurvey = new DistributionBoardSurvey();
						synchronized (this) {

							dbSurvID = "DB_SURV_" + year + "_"
									+ Integer.parseInt(
											session.createNativeQuery("SELECT DISTRIBUTION_BOARD_SURVEY FROM SEQ_TABLE")
													.uniqueResult().toString());
							query = session
									.createNativeQuery("UPDATE SEQ_TABLE SET DISTRIBUTION_BOARD_SURVEY = DISTRIBUTION_BOARD_SURVEY + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						Double drivingDistance, geoDistance,linearDistance;

						drivingDistance = itemParameters.getDictParameterDbSurv().get(i).get("drivingDistance") == ""
								? 0
								: itemParameters.getDictParameterDbSurv().get(i).get("drivingDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameterDbSurv().get(i).get("drivingDistance"),
												"null") ? 0
														: Double.parseDouble(itemParameters.getDictParameterDbSurv()
																.get(i).get("drivingDistance"));
						
						
						geoDistance = itemParameters.getDictParameterDbSurv().get(i).get("geoDistance") == "" ? 0
								: itemParameters.getDictParameterDbSurv().get(i).get("geoDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameterDbSurv().get(i).get("geoDistance"),
												"null") ? 0
														: Double.parseDouble(itemParameters.getDictParameterDbSurv()
																.get(i).get("geoDistance"));
						
						linearDistance = itemParameters.getDictParameterDbSurv().get(i).get("linearDistance") == "" ? 0
								: itemParameters.getDictParameterDbSurv().get(i).get("linearDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameterDbSurv().get(i).get("linearDistance"),
												"null") ? 0
														: Double.parseDouble(itemParameters.getDictParameterDbSurv()
																.get(i).get("linearDistance"));
						
						dbSurvey.setDbSurvID(dbSurvID);
						dbSurvey.setDbID(itemParameters.getDictParameterDbSurv().get(i).get("ID"));
						dbSurvey.setDbName(itemParameters.getDictParameterDbSurv().get(i).get("Name"));
						dbSurvey.setLongitude(itemParameters.getDictParameterDbSurv().get(i).get("longitude"));
						dbSurvey.setLatitude(itemParameters.getDictParameterDbSurv().get(i).get("latitude"));
						dbSurvey.setLinearDistance(linearDistance);
						dbSurvey.setDrivingDistance(drivingDistance);
						dbSurvey.setGeoDistance(geoDistance);
						dbSurvey.setSurveyID(surveyID);
						session.saveOrUpdate(dbSurvey);
						session.flush();
						session.clear();
					}
				}
			
			if (itemParameters.getDictParameterNodeSurv().size() > 0) {
				for (int i = 0; i < itemParameters.getDictParameterNodeSurv().size(); i++) {

				nodeSurvey = new NodeSurvey();
						synchronized (this) {

							nodeSurvID = "NODE_SURV_" + year + "_"
									+ Integer.parseInt(
											session.createNativeQuery("SELECT NODE_SURVEY FROM SEQ_TABLE")
													.uniqueResult().toString());
							query = session
									.createNativeQuery("UPDATE SEQ_TABLE SET NODE_SURVEY = NODE_SURVEY + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						Double drivingDistance, geoDistance,linearDistance;

						drivingDistance = itemParameters.getDictParameterNodeSurv().get(i).get("drivingDistance") == ""
								? 0
								: itemParameters.getDictParameterNodeSurv().get(i).get("drivingDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameterNodeSurv().get(i).get("drivingDistance"),
												"null") ? 0
														: Double.parseDouble(itemParameters.getDictParameterNodeSurv()
																.get(i).get("drivingDistance"));
						
						
						geoDistance = itemParameters.getDictParameterNodeSurv().get(i).get("geoDistance") == "" ? 0
								: itemParameters.getDictParameterNodeSurv().get(i).get("geoDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameterNodeSurv().get(i).get("geoDistance"),
												"null") ? 0
														: Double.parseDouble(itemParameters.getDictParameterNodeSurv()
																.get(i).get("geoDistance"));
						
						linearDistance = itemParameters.getDictParameterNodeSurv().get(i).get("linearDistance") == "" ? 0
								: itemParameters.getDictParameterNodeSurv().get(i).get("linearDistance") == null ? 0
										: StringUtils.equalsIgnoreCase(
												itemParameters.getDictParameterNodeSurv().get(i).get("linearDistance"),
												"null") ? 0
														: Double.parseDouble(itemParameters.getDictParameterNodeSurv()
																.get(i).get("linearDistance"));
						
						nodeSurvey.setNodeSurvID(nodeSurvID);
						nodeSurvey.setNodeID(itemParameters.getDictParameterNodeSurv().get(i).get("ID"));
						nodeSurvey.setNodeName(itemParameters.getDictParameterNodeSurv().get(i).get("Name"));
						nodeSurvey.setLongitude(itemParameters.getDictParameterNodeSurv().get(i).get("longitude"));
						nodeSurvey.setLatitude(itemParameters.getDictParameterNodeSurv().get(i).get("latitude"));
						nodeSurvey.setLinearDistance(linearDistance);
						nodeSurvey.setDrivingDistance(drivingDistance);
						nodeSurvey.setGeoDistance(geoDistance);
						nodeSurvey.setSurveyID(surveyID);
						session.saveOrUpdate(nodeSurvey);
						session.flush();
						session.clear();
					}
				}
			if (itemParameters.getDictParameterCableSurv().size() > 0) {
				for (int i = 0; i < itemParameters.getDictParameterCableSurv().size(); i++) {

				cableSurvey = new FiberCableSurvey();
						synchronized (this) {

							cableSurvID = "CABLE_SURV_" + year + "_"
									+ Integer.parseInt(
											session.createNativeQuery("SELECT FIBER_CABLES_SURVEY FROM SEQ_TABLE")
													.uniqueResult().toString());
							query = session
									.createNativeQuery("UPDATE SEQ_TABLE SET FIBER_CABLES_SURVEY = FIBER_CABLES_SURVEY + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}

						
						cableSurvey.setFiberCableSurvID(cableSurvID);
						cableSurvey.setFiberCableID(itemParameters.getDictParameterCableSurv().get(i).get("ID"));
						cableSurvey.setFiberCableName(itemParameters.getDictParameterCableSurv().get(i).get("Name"));
						cableSurvey.setSource(itemParameters.getDictParameterCableSurv().get(i).get("source"));
						cableSurvey.setDestination(itemParameters.getDictParameterCableSurv().get(i).get("destination"));
						cableSurvey.setSurveyID(surveyID);
						session.saveOrUpdate(cableSurvey);
						session.flush();
						session.clear();
					}
				}
			if (itemParameters.getDictParameterTubeSurv().size() > 0) {
				for (int i = 0; i < itemParameters.getDictParameterTubeSurv().size(); i++) {

				tubeSurvey = new FiberTubesSurvey();
						synchronized (this) {

							tubeSurvID = "TUBE_SURV_" + year + "_"
									+ Integer.parseInt(
											session.createNativeQuery("SELECT FIBER_TUBES_SURVEY FROM SEQ_TABLE")
													.uniqueResult().toString());
							query = session
									.createNativeQuery("UPDATE SEQ_TABLE SET FIBER_TUBES_SURVEY = FIBER_TUBES_SURVEY + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}

						
						tubeSurvey.setTubeSurvID(tubeSurvID);
						tubeSurvey.setTubeID(itemParameters.getDictParameterTubeSurv().get(i).get("ID"));
						tubeSurvey.setTubeName(itemParameters.getDictParameterTubeSurv().get(i).get("Name"));
						tubeSurvey.setSource(itemParameters.getDictParameterTubeSurv().get(i).get("source"));
						tubeSurvey.setDestination(itemParameters.getDictParameterTubeSurv().get(i).get("destination"));
						tubeSurvey.setSurveyID(surveyID);
						session.saveOrUpdate(tubeSurvey);
						session.flush();
						session.clear();
					}
				}
						
			if (itemParameters.getDictParameterStrandSurv().size() > 0) {
				for (int i = 0; i < itemParameters.getDictParameterStrandSurv().size(); i++) {

				strandSurvey = new FiberStrandsSurvey();
						synchronized (this) {

							strandSurvID = "STRAND_SURV_" + year + "_"
									+ Integer.parseInt(
											session.createNativeQuery("SELECT FIBER_STRANDS_SURVEY FROM SEQ_TABLE")
													.uniqueResult().toString());
							query = session
									.createNativeQuery("UPDATE SEQ_TABLE SET FIBER_STRANDS_SURVEY = FIBER_STRANDS_SURVEY + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}

						
						strandSurvey.setStrandSurvID(strandSurvID);
						strandSurvey.setStrandID(itemParameters.getDictParameterStrandSurv().get(i).get("ID"));
						strandSurvey.setStrandName(itemParameters.getDictParameterStrandSurv().get(i).get("Name"));
						strandSurvey.setSource(itemParameters.getDictParameterStrandSurv().get(i).get("source"));
						strandSurvey.setDestination(itemParameters.getDictParameterStrandSurv().get(i).get("destination"));
						strandSurvey.setSurveyID(surveyID);
						session.saveOrUpdate(strandSurvey);
						session.flush();
						session.clear();
					}
				}
			
			
						
									
		} catch (Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in save Survey due to \n " + exceptionAsString);
			logger.info("Error in save Survey due to \n " + exceptionAsString);
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

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateOnMySD", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateOnMySD(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response1) {

	    Map<String, Object> rtn = new LinkedHashMap<>();
	    session = AlmDbSession.getInstance().getSession();
	    if (LoginServices.checkSession(request, response1).equals("redirect:/")) {
	        rtn.put("Login", "redirect:/");
	        return rtn;
	    }
	    if (session != null && session.isOpen()) {

	        tx = session.beginTransaction();

	        Socket socket = null;
	        HttpURLConnection httpConnection = null;
	        String connectionStatus = "Failed";
	        String resultMsg = "";
	        String responseMessage = "null";
	        int statusCode = -1;

	        String surveyID = request.getParameter("surveyID");
	        String currentUrl = request.getParameter("currentUrl");
	        String serviceAppNumber = request.getParameter("serviceAppNumber");
	        String nearestPoint = request.getParameter("nearestPoint");
	        String price = request.getParameter("totalPrice");
            Timestamp creationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());



	        try {

	            String body = "{\"serviceAppNumber\": " + serviceAppNumber + ",\"surveyID\": \"" + surveyID + "\", \"nearestpoint\": \"" + nearestPoint + "\",  \"url\": \"" + currentUrl + "\",  \"price\": " + price + "}";
				boolean portAvailable = false;

	            try {
	                socket = new Socket("10.22.25.18", 42023);
					portAvailable = true;

	            } catch (IOException e) {
	                System.out.println("Port is closed.");
	                query = session.createNativeQuery("INSERT INTO SURVEY_LOGS VALUES (TIMESTAMP '" + creationDate+"' ,'"
							+ surveyID+ "', '" + connectionStatus + "', 'null', '" +statusCode+"', 'Port is closed')");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					session.flush();
					session.clear();					
	                logger.info("Port is closed.");
					rtn.put("updateOnMySDStatus", "Failed - Port is closed");
	                System.out.println("Port is closed.");
	                return rtn;
	            }
	            
	            
				if (portAvailable == true) {
					System.out.println("Enter Port available condition");
					
					  HttpClient client = HttpClient.newHttpClient();
					  HttpRequest.Builder builder = HttpRequest.newBuilder();
					  builder.uri(URI.create("http://10.22.25.18:42023/api/Home/Survey"));
					  builder.header("Content-Type", "application/json");
					  builder.header("MYSdKey", "JDJhJDA0JHNaZFlDTVRGWW5ZbWVHUjJyUXhMZi4xRWU5U1h6RGNXT25pWUNoRks0bnRPNENGNXdVVXlT");
					  builder.POST(HttpRequest.BodyPublishers.ofString(body));
						
					  HttpRequest rqst = builder.build();
					  HttpResponse<String> response = client.send(rqst, HttpResponse.BodyHandlers.ofString());
					  System.out.println(" Response is " +response.body());
					  
					  int responseCode = response.statusCode();
					  String responseMsg = response.body();					 
					 
		                if (responseCode == 500) {
							System.out.println("Network issue, please contact your support.");
							
							 query = session.createNativeQuery("INSERT INTO SURVEY_LOGS VALUES (TIMESTAMP '" + creationDate+"' ,'"
										+ surveyID+ "', '" + connectionStatus + "', '" +responseCode+"', '" +statusCode+"', 'Network issue')");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							session.flush();
							session.clear();
							
							resultMsg="Failed - "+responseCode+" - Network issue";
		                } 
		                else {
								System.out.println("You can proceed");
								
								if (responseMsg.contains("statusCode") && responseMsg.contains("responseMessage") ) {

									 // Remove curly braces and double quotes
							        String[] parts = responseMsg.replaceAll("[{}\"]", "").split(",");
							        
							        for (String part : parts) {
							            String[] keyValue = part.split(":");
							            String key = keyValue[0].trim();
							            String value = keyValue[1].trim();

							            if (key.equals("statusCode")) {
							                statusCode = Integer.parseInt(value);
							            } else if (key.equals("responseMessage")) {
							                responseMessage = value;
							            }
							        }
							        
							        if (responseMessage.contains("Successfully saved the survey") || responseMessage.contains("Success") ) {
							            connectionStatus = "Success";
							        }
							        else {
							            connectionStatus = "Failed";
							        }							      
								}
							
				                resultMsg=connectionStatus+" - "+responseCode+":"+responseMessage;

						        System.out.println("Insert into survey_logs ");
								 query = session.createNativeQuery("INSERT INTO SURVEY_LOGS VALUES (TIMESTAMP '" + creationDate+"' ,'"
											+ surveyID+ "', '" + connectionStatus + "', '" +responseCode+"', '" +statusCode+"', '" +responseMessage+"')");
								query.executeUpdate();
								session.createNativeQuery("commit").executeUpdate();
								session.flush();
								session.clear();
								
						        System.out.println("Insert done");

		                }
		                
				}
				else {
					System.out.println("Service is not available");
					resultMsg="Failed - Service is not available & port is closed";
				}
				
				System.out.println("resultMsg is "+resultMsg);

				rtn.put("updateOnMySDStatus", resultMsg);
	          

	        } catch (Exception e) {
	            e.printStackTrace();
	            connectionStatus = "Failed";
				rtn.put("updateOnMySDStatus", "Failed");
	            StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            String exceptionAsString = sw.toString();
	            logger.info("Error in updateOnMySD due to \n " + exceptionAsString);
	        }
 
	    }

	    return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SearchForSource", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SearchForSource(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		String search;
		session = AlmDbSession.getInstance().getSession();
		List<Object[]> globalList = new ArrayList<Object[]>();
		List<String> searchResult = new ArrayList<String>();
		search = request.getParameter("search");

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				if (Boolean.parseBoolean(request.getParameter("SearchForSite")) == true) {
					System.out.println("Site Checkbox " + request.getParameter("SearchForSite"));
					query = session.createNativeQuery(
							"SELECT WARE_ID,WARE_NAME,SITE_ID,LONGITUDE,LATITUDE,CITY FROM WAREHOUSE a WHERE UPPER(WARE_ID) LIKE UPPER(:param)  OR UPPER(WARE_NAME) LIKE UPPER(:param) OR UPPER(SITE_ID) LIKE UPPER(:param) ");

					query.setParameter("param", "%" + search + "%");
					query.setFirstResult(0);
					query.setMaxResults(40);
					globalList.addAll(query.getResultList());
				}

				if (Boolean.parseBoolean(request.getParameter("SearchForClient")) == true) {
					System.out.println("Client Checkbox " + request.getParameter("SearchForClient"));

					query = session.createNativeQuery(
							"SELECT FIRST_NAME,LAST_NAME,MOBILE_NUMBER,LONGITUDE,LATITUDE,PHYSICAL_LOCATION FROM CLIENTS a WHERE UPPER(FIRST_NAME) LIKE UPPER(:param)  OR UPPER(LAST_NAME) LIKE UPPER(:param) or MOBILE_NUMBER LIKE :param ");

					query.setParameter("param", "%" + search + "%");
					query.setFirstResult(0);
					query.setMaxResults(40);
					globalList.addAll(query.getResultList());
				}

				query = session.createNativeQuery(
						"SELECT MANHOLE_ID,MANHOLE_NAME FROM MANHOLE a WHERE UPPER(MANHOLE_ID) LIKE UPPER(:param)  OR UPPER(MANHOLE_NAME) LIKE UPPER(:param) ");
				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);
				globalList.addAll(query.getResultList());
				query = session.createNativeQuery(
						"SELECT HANDHOLE_ID,HANDHOLE_NAME FROM HANDHOLE b WHERE UPPER(HANDHOLE_ID) LIKE UPPER(:param) OR UPPER(HANDHOLE_NAME) LIKE UPPER(:param) ");
				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);
				globalList.addAll(query.getResultList());
				query = session.createNativeQuery(
						"SELECT DB_ID,DB_NAME FROM DISTRIBUTION_BOARD c WHERE UPPER(DB_ID) LIKE UPPER(:param) OR UPPER(DB_NAME) LIKE UPPER(:param) ");
				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);
				globalList.addAll(query.getResultList());
				if (globalList != null && globalList.size() != 0) {

					for (Object[] obj : globalList) {
						if (obj.length > 2) {
							searchResult.add(obj[0] + ":" + obj[1] + ":" + obj[2]);
						} else {
							searchResult.add(obj[0] + ":" + obj[1]);
						}
					}
					System.out.println("searchResult " + searchResult);
					rtn.put("searchResult", searchResult);
					rtn.put("globalList", globalList);
				} else {
					rtn.put("searchResult", "");
					rtn.put("globalList", "");
				}
				session.flush();
				session.clear();
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in SearchForSource due to \n " + exceptionAsString);
				logger.info("Error in SearchForSource due to \n " + exceptionAsString);
				rtn.put("searchResult", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}
		}
		return rtn;

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ServiceReferenceAutocomplete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ServiceReferenceAutocomplete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {

			session = AlmDbSession.getInstance().getSession();
			String search = request.getParameter("search");

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
						query = session.createNativeQuery(
								"SELECT CUST_SERV_ID,REF_ID FROM CUSTOMER_SERVICE a WHERE "
								+ " (( UPPER(CUST_SERV_ID) LIKE UPPER(:param)  OR UPPER(REF_ID) LIKE UPPER(:param) ))  ");

						query.setParameter("param", "%" +search+ "%");
						query.setFirstResult(0);
						query.setMaxResults(40);

					if (query.getResultList() != null && query.getResultList().size() != 0) {

						rtn.put("serviceList", query.getResultList());
					} 
					else {
						rtn.put("serviceList", "");
					}

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in ServiceReferenceAutocomplete due to \n " + exceptionAsString);
					logger.info("Error in ServiceReferenceAutocomplete due to \n " + exceptionAsString);
					rtn.put("serviceList", null);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();

					}
				}
			}
		}
		return rtn;

	}
	@RequestMapping(value = "/showJunctionsData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> showJunctionsData(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		session = AlmDbSession.getInstance().getSession();
		List<Object[]> showJunctionList = new ArrayList<Object[]>();

		String cableID = request.getParameter("fiberID");


		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				
				showJunctionList = session.createNativeQuery("SELECT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A LEFT JOIN JUNCTION_MAPPING B ON A.JUNCTION_ID = B.JCT_ID "
						+ " WHERE (A.PHYSICAL_LAYER_ID IS NULL OR A.PHYSICAL_LAYER_ID = 'null') AND (B.FIBER_ID_SIDE_B = '"+cableID+"' OR B.FIBER_ID_SIDE_A = '"+cableID+"') ").getResultList();
				rtn.put("showJunctionList", showJunctionList);

				
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in showJunctionsData due to \n " + exceptionAsString);
				logger.info("Error in showJunctionsData due to \n " + exceptionAsString);
				rtn.put("showJunctionList",null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;

	}
	
	
	
		@RequestMapping(value = "/moveToImplementation", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> moveToImplementation(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			// logger.info("Welcome home! The client locale is {}.", locale);

			Map<String, Object> rtn = new LinkedHashMap<>();
			// ObjectMapper mapper = new ObjectMapper();
			Session session = null;
			Transaction tx = null;
			session = AlmDbSession.getInstance().getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				String selectedProjectIdContext = request.getParameter("selectedProjectIdContext");
				//System.out.println("selectedProjectIdContext "+selectedProjectIdContext);
				try {
					query = session.createNativeQuery("UPDATE project SET project_layer = 'Implementation' where project_id ='"+selectedProjectIdContext+"' ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();

					rtn.put("Status", "Success");
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(new Date());
					int year = calendar.get(Calendar.YEAR);
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

					PhysicalLayerActivity PhyAct=new PhysicalLayerActivity();
					String updateModfUser =request.getParameter("updateModfUser");
					String ipAddress = getIpAddress(request);

                         String PhyActID=
							 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							
							PhyAct.setPhyActID(PhyActID);
							PhyAct.setScreenName("Project");
							PhyAct.setUsername(updateModfUser);
							PhyAct.setUserIP(ipAddress);
							PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
						PhyAct.setElementID(selectedProjectIdContext);
						PhyAct.setActivityDescription("Move To Implementation");
						session.saveOrUpdate(PhyAct);


				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in moveToImplementation due to \n " + exceptionAsString);
					logger.info("Error in moveToImplementation due to \n " + exceptionAsString);
					rtn.put("Status", "Failed");

				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return rtn;
		}
		
		
		@RequestMapping(value = "/moveProjectToCurrentPhysicalLayer", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> moveProjectToCurrentPhysicalLayer(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			// logger.info("Welcome home! The client locale is {}.", locale);

			Map<String, Object> rtn = new LinkedHashMap<>();
			
			// ObjectMapper mapper = new ObjectMapper();
			Session session = null;
			Transaction tx = null;
			session = AlmDbSession.getInstance().getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				String selectedProjectIdContext = request.getParameter("selectedProjectIdContext");
				try {
					
					Map<String, Object> resultMap = getProjectElement(selectedProjectIdContext,"moveproject",session);
					
					// linkedHashmap instead of HashMap to return values in sequential order 
					LinkedHashMap<String, List<?>> physicalLayerData = (LinkedHashMap<String, List<?>>) resultMap.get("physicalLayerData");
					


					//linkedHashmap instead of HashMap to return values 									
					LinkedHashMap<String, List<?>> physicalLayerList = (LinkedHashMap<String, List<?>>) resultMap.get("physicalLayerList");
					
					rtn.put("physicalLayerList", mapper.writeValueAsString(physicalLayerList));
					rtn.put("physicalLayerData", mapper.writeValueAsString(physicalLayerData));
					
					
					
					
					query = session.createNativeQuery("UPDATE FIBER_CABLES SET PROJECT_ID = 'CurrentPhysicalLayer' where PROJECT_ID ='"+selectedProjectIdContext+"' ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					
					query = session.createNativeQuery("UPDATE TRENCH SET PROJECT_ID = 'CurrentPhysicalLayer' where PROJECT_ID ='"+selectedProjectIdContext+"' ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					
					query = session.createNativeQuery("UPDATE MANHOLE SET PROJECT_ID = 'CurrentPhysicalLayer' where PROJECT_ID ='"+selectedProjectIdContext+"' ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					
					query = session.createNativeQuery("UPDATE HANDHOLE SET PROJECT_ID = 'CurrentPhysicalLayer' where PROJECT_ID ='"+selectedProjectIdContext+"' ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					
					query = session.createNativeQuery("UPDATE JUNCTION SET PROJECT_ID = 'CurrentPhysicalLayer' where PROJECT_ID ='"+selectedProjectIdContext+"' ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					
					query = session.createNativeQuery("UPDATE DISTRIBUTION_BOARD SET PROJECT_ID = 'CurrentPhysicalLayer' where PROJECT_ID ='"+selectedProjectIdContext+"' ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					
					
					
					query = session.createNativeQuery("UPDATE project SET project_layer = 'Completed' where project_id ='"+selectedProjectIdContext+"' ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();

					rtn.put("Status", "Success");
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(new Date());
					int year = calendar.get(Calendar.YEAR);
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

					PhysicalLayerActivity PhyAct=new PhysicalLayerActivity();
					String updateModfUser =request.getParameter("updateModfUser");
					String ipAddress = getIpAddress(request);

                         String PhyActID=
							 "PHY_ACT_" + year + "_"+ Integer.parseInt(session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							
							PhyAct.setPhyActID(PhyActID);
							PhyAct.setScreenName("Project");
							PhyAct.setUsername(updateModfUser);
							PhyAct.setUserIP(ipAddress);
							PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
						PhyAct.setElementID(selectedProjectIdContext);
						PhyAct.setActivityDescription("Move To Current Physical Layer");
						session.saveOrUpdate(PhyAct);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in moveProjectToCurrentPhysicalLayer due to \n " + exceptionAsString);
					logger.info("Error in moveProjectToCurrentPhysicalLayer due to \n " + exceptionAsString);
					rtn.put("Status", "Failed");

				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return rtn;
		}
		
		@RequestMapping(value = "/getProject", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> getProject(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			// logger.info("Welcome home! The client locale is {}.", locale);

			Map<String, Object> rtn = new LinkedHashMap<>();
			
			// ObjectMapper mapper = new ObjectMapper();
			Session session = null;
			Transaction tx = null;
			session = AlmDbSession.getInstance().getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				String selectedProjectIdContext = request.getParameter("selectedProjectIdContext");
				try {
					
					Map<String, Object> resultMap = getProjectElement(selectedProjectIdContext,"getproject",session);
					
					// linkedHashmap instead of HashMap to return values in sequential order 
					LinkedHashMap<String, List<?>> physicalLayerData = (LinkedHashMap<String, List<?>>) resultMap.get("physicalLayerData");
					


					//linkedHashmap instead of HashMap to return values 									
					LinkedHashMap<String, List<?>> physicalLayerList = (LinkedHashMap<String, List<?>>) resultMap.get("physicalLayerList");
					
					rtn.put("physicalLayerList", mapper.writeValueAsString(physicalLayerList));
					rtn.put("physicalLayerData", mapper.writeValueAsString(physicalLayerData));
					

					rtn.put("Status", "Success");
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in getProject due to \n " + exceptionAsString);
					logger.info("Error in getProject due to \n " + exceptionAsString);
					rtn.put("Status", "Failed");

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
		public Map<String, Object> getProjectElement(String ProjectId, String Target,Session session){
			List<Object[]> manholeList = new ArrayList<Object[]>();
			List<Object[]> handholeList = new ArrayList<Object[]>();
			List<Object[]> fiberList = new ArrayList<Object[]>();
			List<Object[]> fiberAuxiliary_Data = new ArrayList<Object[]>();
			List<Object[]> fiberTubes = new ArrayList<Object[]>();
			List<Object[]> tubesAuxiliaries = new ArrayList<Object[]>();
			List<Object[]> fiberStrands = new ArrayList<Object[]>();
			List<Object[]> strandsAuxiliaries = new ArrayList<Object[]>();
			List<Object[]> trenchList = new ArrayList<Object[]>();
			List<Object[]> trenchAuxiliary_Data = new ArrayList<Object[]>();
			List<Object[]> junctionManholeList = new ArrayList<Object[]>();
			List<Object[]> junctionHandholeList = new ArrayList<Object[]>();
			List<Object[]> distribBoardList = new ArrayList<Object[]>();
			List<Object[]> ductList = new ArrayList<Object[]>();
			List<Object[]> ductAuxiliary_Data = new ArrayList<Object[]>();
			
			List<Object[]> JunctionList = new ArrayList<Object[]>();
			
			
			if(StringUtils.equalsIgnoreCase(Target, "moveproject")) {
				fiberList = session.createNativeQuery(
						"SELECT SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID),(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID),FIBER_CABLE_NAME,'CurrentPhysicalLayer' AS PROJECT_ID,SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_TUBES,NUMBER_OF_STRANDS,LENGTH,DRAWING_TYPE,FIBER_NETWORK_LEVEL,FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A where A.PROJECT_ID ='"+ProjectId+"' ")
						.getResultList();
			}else {
			fiberList = session.createNativeQuery(
					"SELECT SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID),(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID),FIBER_CABLE_NAME,PROJECT_ID,SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_TUBES,NUMBER_OF_STRANDS,LENGTH,DRAWING_TYPE,FIBER_NETWORK_LEVEL,FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A where A.PROJECT_ID ='"+ProjectId+"' ")
					.getResultList();
			}
			

			fiberAuxiliary_Data = session.createNativeQuery(
					"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID and A.PROJECT_ID ='"+ProjectId+"'  ORDER BY B.SEQ_SORTING ASC")
					.getResultList();
			
			fiberTubes = session.createNativeQuery(
					"SELECT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,"
							+ "(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER, b.TUBE_COLOR "
							+ "FROM FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and a.PROJECT_ID ='"+ProjectId+"' ORDER BY FIBER_CABLE_ID,TUBE_NUMBER ASC")
					.getResultList();

			tubesAuxiliaries = session.createNativeQuery(
					"SELECT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c,FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.TUBE_ID=c.TUBE_ID and A.PROJECT_ID ='"+ProjectId+"' ORDER BY c.SEQ_SORTING ASC")
					.getResultList();

			fiberStrands = session.createNativeQuery(
					"SELECT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and A.PROJECT_ID ='"+ProjectId+"' ORDER BY STRAND_NUMBER")
					.getResultList();

			strandsAuxiliaries = session.createNativeQuery(
					"SELECT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,C.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID and A.PROJECT_ID ='"+ProjectId+"' ORDER BY c.SEQ_SORTING ASC ")
					.getResultList();
			
			if(StringUtils.equalsIgnoreCase(Target, "moveproject")) {
				manholeList = session.createNativeQuery(
						"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,'CurrentPhysicalLayer' AS PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),CITY FROM MANHOLE where PROJECT_ID= '"+ProjectId+"' ")
						.getResultList();
			}else {
				manholeList = session.createNativeQuery(
						"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),CITY FROM MANHOLE where PROJECT_ID= '"+ProjectId+"' ")
						.getResultList();
			}

			if(StringUtils.equalsIgnoreCase(Target, "moveproject")) {
				handholeList = session.createNativeQuery(
						"SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,'CurrentPhysicalLayer' AS PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),CITY FROM HANDHOLE where PROJECT_ID= '"+ProjectId+"' ")
						.getResultList();
			}else {
				handholeList = session.createNativeQuery(
						"SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),CITY FROM HANDHOLE where PROJECT_ID= '"+ProjectId+"' ")
						.getResultList();
			}
			
			if(StringUtils.equalsIgnoreCase(Target, "moveproject")) {
				distribBoardList = session.createNativeQuery(
						"SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,'CurrentPhysicalLayer' AS PROJECT_ID ,CITY,DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD where PROJECT_ID= '"+ProjectId+"' ")
						.getResultList();
			}else {
				distribBoardList = session.createNativeQuery(
						"SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY,DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD where PROJECT_ID= '"+ProjectId+"' ")
						.getResultList();
			}
			
			if(StringUtils.equalsIgnoreCase(Target, "moveproject")) {
				trenchList = session.createNativeQuery(
						"SELECT TRENCH_ID,TRENCH_NAME,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LATITUDE,SOURCE_LONGITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY,NUM_DUCTS,MAX_CAPACITY,LENGTH,'CurrentPhysicalLayer' AS PROJECT_ID,DRAWING_TYPE  FROM TRENCH where PROJECT_ID= '"+ProjectId+"' ")
						.getResultList();
			}else {
				trenchList = session.createNativeQuery(
						"SELECT TRENCH_ID,TRENCH_NAME,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LATITUDE,SOURCE_LONGITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY,NUM_DUCTS,MAX_CAPACITY,LENGTH,PROJECT_ID,DRAWING_TYPE  FROM TRENCH where PROJECT_ID= '"+ProjectId+"' ")
						.getResultList();
			}

			
			trenchAuxiliary_Data = session.createNativeQuery(
					"SELECT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.TRENCH_ID,B.DISTANCE_FROM_SOURCE,B.SEQ_SORTING,B.AUXILIARY_ID  FROM TRENCH A,TRENCH_AUXILIARY_POINTS B WHERE A.TRENCH_ID=B.TRENCH_ID and A.PROJECT_ID= '"+ProjectId+"' ORDER BY B.SEQ_SORTING ASC")
					.getResultList();

			junctionManholeList = session.createNativeQuery(
					"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id WHERE B.PROJECT_ID ='"+ProjectId+"' ")
					.getResultList();

			junctionHandholeList = session.createNativeQuery(
					"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id WHERE B.PROJECT_ID ='"+ProjectId+"' ")
					.getResultList();

			ductList = session.createNativeQuery(
					"SELECT B.DUCT_ID,B.DUCT_NAME,B.SOURCE_WARE_ID,B.SOURCE_ID,B.SOURCE_NAME,B.DESTINATION_WARE_ID,B.DESTINATION_ID,B.DESTINATION_NAME,B.SOURCE_LATITUDE,B.SOURCE_LONGITUDE,B.DESTINATION_LONGITUDE,B.DESTINATION_LATITUDE,B.SOURCE_CITY,B.DESTINATION_CITY,B.NUM_FIBERCABLES,B.NUM_FIBERTUBES,B.NUM_FIBERSTRANDS,B.LENGTH,B.TRENCH_ID,B.DRAWING_TYPE FROM DUCTS B,TRENCH A WHERE B.TRENCH_ID=A.TRENCH_ID and A.PROJECT_ID= '"+ProjectId+"' ")
					.getResultList();

			/*ductAuxiliary_Data = session.createNativeQuery(
					"SELECT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.DUCT_ID,B.DISTANCE_FROM_SOURCE,B.SEQ_SORTING,B.AUXILIARY_ID FROM DUCTS A,DUCT_AUXILIARY_POINTS B WHERE A.DUCT_ID=B.DUCT_ID ORDER BY B.SEQ_SORTING ASC ")
					.getResultList();*/
			
			ductAuxiliary_Data = session.createNativeQuery(
				    "SELECT B.LONGITUDE, B.LATITUDE, B.WARE_ID, B.AUXILIARY_POINT_ID, B.AUXILIARY_POINT_NAME, B.DUCT_ID, B.DISTANCE_FROM_SOURCE, B.SEQ_SORTING, B.AUXILIARY_ID " +
				    "FROM DUCT_AUXILIARY_POINTS B " +
				    "WHERE B.DUCT_ID IN (SELECT B.DUCT_ID " +
				                        "FROM DUCTS B, TRENCH A " +
				                        "WHERE B.TRENCH_ID = A.TRENCH_ID " +
				                        "AND A.PROJECT_ID = '" + ProjectId + "') " +
				    "ORDER BY B.SEQ_SORTING ASC")
				    .getResultList();
			
			
			if(StringUtils.equalsIgnoreCase(Target, "moveproject")) {
				JunctionList = session.createNativeQuery("SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,'CurrentPhysicalLayer' AS PROJECT_ID FROM JUNCTION A where (PROJECT_ID ='"+ProjectId+"') and A.PHYSICAL_LAYER_ID IS NULL OR A.PHYSICAL_LAYER_ID = ' ' OR A.PHYSICAL_LAYER_ID = 'null' ").getResultList();
			}else {
				JunctionList = session.createNativeQuery("SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A where (PROJECT_ID ='"+ProjectId+"') and A.PHYSICAL_LAYER_ID IS NULL OR A.PHYSICAL_LAYER_ID = ' ' OR A.PHYSICAL_LAYER_ID = 'null' ").getResultList();
			}
			
			/* linkedHashmap instead of HashMap to return values in sequential order */
			LinkedHashMap<String, List<?>> physicalLayerData = new LinkedHashMap<String, List<?>>();

			/* linkedHashmap instead of HashMap to return values */					
			LinkedHashMap<String, List<?>> physicalLayerList = new LinkedHashMap<String, List<?>>();
			
			
		
			physicalLayerList.clear();
			physicalLayerList.put("Junction_Manhole", junctionManholeList);
			physicalLayerList.put("Manhole", manholeList);
			physicalLayerList.put("Junction_Handhole", junctionHandholeList);
			physicalLayerList.put("Handhole", handholeList);
			physicalLayerList.put("JunctionList", JunctionList);
			physicalLayerList.put("fiber", fiberList);
			physicalLayerList.put("Distribution_Board", distribBoardList);
			physicalLayerList.put("Trench", trenchList);
			physicalLayerList.put("duct", ductList);
			
			physicalLayerData.clear();
			physicalLayerData.put("trench_Auxiliary", trenchAuxiliary_Data);
			physicalLayerData.put("strands_Auxiliaries", strandsAuxiliaries);
			physicalLayerData.put("fiber_Strands", fiberStrands);
			physicalLayerData.put("tubes_Auxiliaries", tubesAuxiliaries);
			physicalLayerData.put("fiber_Tubes", fiberTubes);
			physicalLayerData.put("fiber_Auxiliary", fiberAuxiliary_Data);
			physicalLayerData.put("ductAuxiliary", ductAuxiliary_Data);
			
			
			Map<String, Object> resultMap = new HashMap<>();
		    resultMap.put("physicalLayerList", physicalLayerList);
		    resultMap.put("physicalLayerData", physicalLayerData);

		    return resultMap;
			
			
			
			
		}
	
	
		@RequestMapping(value = "/SaveFile", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> SaveFile(Locale locale, Model model, HttpServletRequest request,@RequestParam("attachment") MultipartFile file,@RequestParam("elementID") String elementID) {

			Map<String, Object> rtn = new LinkedHashMap<>();
			String status="";
			String attachmentID="";
			String attachmentName =""; 
			String attachmentPath ="";
			String elementId ="";

			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				if (file.isEmpty()) {
					rtn.put("Status", "Failed");
		            return rtn;
		        }
				try {					
					Map<String, String> uploadResult = uploadFile(file);
		            
					status = uploadResult.get("status");
					attachmentPath = uploadResult.get("attachmentPath");
					attachmentName = uploadResult.get("attachmentName");
					elementId=elementID;
					
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(new Date());
					
					AttachmentUpload attachment = new AttachmentUpload();
						
						synchronized (this) {
							
							attachmentID = "ATTACHMENT_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(session.createNativeQuery("SELECT ATTACHMENT_UPLOAD FROM SEQ_TABLE").uniqueResult().toString());							
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET ATTACHMENT_UPLOAD  = ATTACHMENT_UPLOAD  + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						
						attachment.setId(attachmentID);
						attachment.setAttachmentPath(attachmentPath);
						attachment.setAttachmentName(attachmentName);
						attachment.setElementID(elementId);
						session.saveOrUpdate(attachment);
						
					
					rtn.put("Status", status);
					rtn.put("attachmentID", attachmentID);
					rtn.put("attachmentPath", attachmentPath);
					rtn.put("attachmentName", attachmentName);
					

				} catch (Exception e) {
					logger.info("Error in Save File  " + e.getMessage());
					rtn.put("Status", "Failed");
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}

			return rtn;
		}

		
		public Map<String, String> uploadFile(MultipartFile file) {
		    Map<String, String> result = new HashMap<>();
		    String status = "";
		    String attachmentPath = "";
		    String attachmentName = "";
		    String username="",pass="",uploadPath="",hostname="";
		    
		    try {
		    	query = session.createNativeQuery(
						"select USERNAME,PASSWORD,PATH,IP_ADDRESS FROM SYSTEM_SETTINGS");
		    	List<Object[]> results = query.getResultList();
				if(results.size()>0) {
					for (Object[] row : results) {
						username=(String) row[0];
						pass=(String) row[1];
						uploadPath=(String) row[2];
						hostname=(String) row[3];
					}
				}
				System.out.println("username is "+username+" pass is "+pass+" path is "+uploadPath+" hostname "+hostname);
		    	
		        JSch jsch = new JSch();
		        com.jcraft.jsch.Session session = (com.jcraft.jsch.Session) jsch.getSession(username,hostname, 22);

		        session.setPassword(pass);
		        session.setConfig("StrictHostKeyChecking", "no");
		        session.connect();

		        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
		        channelSftp.connect();
		        
				System.out.println("Start uploading ...");

		      
		        try (InputStream inputStream = file.getInputStream()) {		        	
		        	 String remoteDirectory = uploadPath;
		        	
		            channelSftp.cd(remoteDirectory);
		            channelSftp.put(inputStream, file.getOriginalFilename());

		            // Construct the full path of the uploaded image
		            attachmentPath = remoteDirectory;
		            attachmentName=file.getOriginalFilename();
		            status = "Success";
		        }

		        channelSftp.disconnect();
		        session.disconnect();
		    } catch (JSchException | SftpException | IOException e) {
		        //logger.error("Error in uploading Image", e);
		        status = "Failed";
		    }
		    result.put("status", status);
		    result.put("attachmentPath", attachmentPath);
		    result.put("attachmentName", attachmentName);
		    return result;
		}

		@RequestMapping(value = "/DeleteAttachment", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> DeleteAttachment(Locale locale, Model model, HttpServletRequest request,@RequestBody List<Map<String, String>> slctDel) {

			Map<String, Object> rtn = new LinkedHashMap<>();			
			
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					for (Map<String, String> attachmentData : slctDel) {
			            String attachmentId = attachmentData.get("attachmentId");
			            String attachmentName = attachmentData.get("attachmentName");
			            String attachmentPath = attachmentData.get("attachmentPath");
			            
			            query = session.createQuery("delete AttachmentUpload  where id = :param1");
						query.setParameter("param1", attachmentId);
						query.executeUpdate();
						
						DeleteAttachmentFile(attachmentPath+attachmentName);
			            
			        }
				} catch (Exception e) {
					logger.info("Error in DeleteAttachment " + e.getMessage());
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}

			return rtn;
		}
		
		public Map<String, String> DeleteAttachmentFile(String imagePath) {
		    Map<String, String> result = new HashMap<>();
		  
		    String username="",pass="",hostname="";
		    String  status="";
		    
		    try {
		    	query = session.createNativeQuery(
						"select USERNAME,PASSWORD,IP_ADDRESS FROM SYSTEM_SETTINGS");
		    	List<Object[]> results = query.getResultList();
				if(results.size()>0) {
					for (Object[] row : results) {
						username=(String) row[0];
						pass=(String) row[1];
						hostname=(String) row[2];
					}
				}
				
		              JSch jsch = new JSch();
		              com.jcraft.jsch.Session session = jsch.getSession(username, hostname, 22);
		              session.setPassword(pass);

		              Properties config = new Properties();
		              config.put("StrictHostKeyChecking", "no");
		              session.setConfig(config);

		              // Connect to SFTP server
		              session.connect();

		              // Open SFTP channel
		              ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
		              channel.connect();

		              // Delete the image file
		              channel.rm(imagePath);

		              // Disconnect SFTP channel and session
		              channel.disconnect();
		              session.disconnect();
		              status="Success";
		    } catch (JSchException | SftpException e) {
		        status = "Failed";
		    }
		    result.put("status", status);
		    return result;
		}
		


		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/GetUploadedAttachment", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> GetUploadedAttachment(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {


			Map<String, Object> rtn = new LinkedHashMap<>();

			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", "redirect:/");
				return rtn;
			} else {
				String elementID = request.getParameter("elementID");
				List<Object[]> listUploadedAttachment;
				session = AlmDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {
					tx = session.beginTransaction();
					try {

						listUploadedAttachment = session.createNativeQuery(
								"SELECT * FROM ATTACHMENT_UPLOAD WHERE ELEMENT_ID='"
										+ elementID + "' ORDER BY ID DESC ")
								.getResultList();
						rtn.put("listUploadedAttachment", listUploadedAttachment);

					} catch (Exception e) {
						sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						exceptionAsString = sw.toString();
						logger.finest("Error in GetUploadedAttachment due to \n " + exceptionAsString);
						logger.info("Error in GetUploadedAttachment due to \n " + exceptionAsString);
						rtn.put("listTrench", null);
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
						}
					}
				}
				return rtn;
			}
		
		}
		
		
		

		



	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(1500);
	}
	private String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_FORWARDED");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        
        // Handle IPv6 local address
        if (ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.equals("::1")) {
            ipAddress = "127.0.0.1"; // This is the IPv4 loopback address
        }
        
        // In some cases, IP addresses may come in IPv6 format, so you might need to convert it to IPv4
        if (ipAddress != null && ipAddress.contains(":")) {
            ipAddress = ipAddress.split(":")[0];
        }

        return ipAddress;
    }
}
