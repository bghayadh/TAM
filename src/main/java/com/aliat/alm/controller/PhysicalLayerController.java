package com.aliat.alm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.AccessCableJunction;
import com.aliat.alm.models.DistributionBoard;
import com.aliat.alm.models.DistributionBoardMapping;
import com.aliat.alm.models.Duct;
import com.aliat.alm.models.DuctAuxPoints;
import com.aliat.alm.models.FiberAuxPoints;
import com.aliat.alm.models.FiberCable;
import com.aliat.alm.models.FiberStrands;
import com.aliat.alm.models.FiberTubes;
import com.aliat.alm.models.NodeActive;
import com.aliat.alm.models.StrandAuxPoints;
import com.aliat.alm.models.Trench;
import com.aliat.alm.models.TrenchAuxPoints;
import com.aliat.alm.models.TubeAuxPoints;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PhysicalLayerController {

	private static final Logger logger = Logger.getLogger(PhysicalLayerController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;

	@Autowired
	ALMSessions almsessions;

	@Autowired
	Notify notifications;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/NetworkPhysicalLayer", method = RequestMethod.GET)

	public String NetworkPhysicalLayer(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/"; 
		} else {

			session = almsessions.getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);

				try {
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
					List<Object[]> EntrepriseList = new ArrayList<Object[]>();
					List<Object[]> TransmissionList = new ArrayList<Object[]>();
					List<Object[]> CoreList = new ArrayList<Object[]>();
					List<Object[]> AccessList = new ArrayList<Object[]>();
					List<Object[]> NodeActiveList = new ArrayList<Object[]>();

					

					// System.out.println("url is "+request.getParameter("selectedField"));
					String checkedOption = "all";
					System.out.println("url is " + request.getParameter("Checked"));
					if (StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "CurrentPhysicalLayer")
							|| StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "PROJECT")) {
						filterFlag = 1;
						String showPointsType = request.getParameter("getRelatedPointsFilter");

						projectList = session.createSQLQuery(
								"SELECT DISTINCT PROJECT_ID,PROJECT_NAME, (select count(*) FROM HANDHOLE a WHERE a.PROJECT_ID = b.PROJECT_ID)  FROM PROJECT b where PROJECT_ID LIKE '%"
										+ request.getParameter("FilteredProject") + "%' OR PROJECT_NAME LIKE '%"
										+ request.getParameter("FilteredProject") + "%' OR PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%'")
								.list();

						manholeList = session.createSQLQuery(
								"SELECT DISTINCT A.MANHOLE_ID,A.MANHOLE_NAME,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN JUNCTION B ON B.PHYSICAL_LAYER_ID = A.manhole_id and B.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' where A.MANHOLE_ID LIKE '%"
										+ request.getParameter("FilteredManhole") + "%' OR A.MANHOLE_NAME LIKE '%"
										+ request.getParameter("FilteredManhole") + "%' OR B.JUNCTION_ID LIKE '%"
										+ request.getParameter("FilteredJunction_Manhole")
										+ "%' OR B.JUNCTION_NAME LIKE '%"
										+ request.getParameter("FilteredJunction_Manhole")
										+ "%' and A.PROJECT_ID LIKE '%" + request.getParameter("Checked") + "%' ")
								.list();

						junctionManholeList = session.createSQLQuery(
								"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A LEFT JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id and (A.JUNCTION_ID LIKE '%"
										+ request.getParameter("FilteredJunction_Manhole")
										+ "%' OR A.JUNCTION_NAME LIKE '%"
										+ request.getParameter("FilteredJunction_Manhole")
										+ "%' OR B.MANHOLE_ID LIKE '%" + request.getParameter("FilteredManhole")
										+ "%' OR B.MANHOLE_NAME LIKE '%" + request.getParameter("FilteredManhole")
										+ "%'  ) and A.PROJECT_ID LIKE '%" + request.getParameter("Checked") + "%'  ")
								.list();

						handholeList = session.createSQLQuery(
								"SELECT DISTINCT A.HANDHOLE_ID,A.HANDHOLE_NAME,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),A.CITY FROM HANDHOLE  A LEFT JOIN JUNCTION B ON B.PHYSICAL_LAYER_ID = A.HANDHOLE_ID and B.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' where A.HANDHOLE_ID LIKE '%"
										+ request.getParameter("FilteredHandhole") + "%' OR A.HANDHOLE_NAME LIKE '%"
										+ request.getParameter("FilteredHandhole") + "%' OR B.JUNCTION_ID LIKE '%"
										+ request.getParameter("FilteredJunction_Handhole")
										+ "%' OR B.JUNCTION_NAME LIKE '%"
										+ request.getParameter("FilteredJunction_Handhole")
										+ "%' and A.PROJECT_ID LIKE '%" + request.getParameter("Checked") + "%' ")
								.list();

						junctionHandholeList = session.createSQLQuery(
								"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id and (A.JUNCTION_ID LIKE '%"
										+ request.getParameter("FilteredJunction_Handhole")
										+ "%' OR A.JUNCTION_NAME LIKE '%"
										+ request.getParameter("FilteredJunction_Handhole")
										+ "%' OR HANDHOLE_ID LIKE '%" + request.getParameter("FilteredHandhole")
										+ "%' OR HANDHOLE_NAME LIKE '%" + request.getParameter("FilteredHandhole")
										+ "%' ) and A.PROJECT_ID LIKE '%" + request.getParameter("Checked") + "%' ")
								.list();

						distribBoardList = session.createSQLQuery(
								"SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY FROM DISTRIBUTION_BOARD where DB_ID LIKE '%"
										+ request.getParameter("FilteredDistribution_Board") + "%' OR DB_NAME LIKE '%"
										+ request.getParameter("FilteredDistribution_Board")
										+ "%' and PROJECT_ID LIKE '%" + request.getParameter("Checked") + "%' ")
								.list();

						fiberList = session.createSQLQuery(
								"SELECT SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID),(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID),FIBER_CABLE_NAME,PROJECT_ID,SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_TUBES,NUMBER_OF_STRANDS,LENGTH,DRAWING_TYPE,FIBER_NETWORK_LEVEL,FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A where FIBER_CABLE_ID LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' OR FIBER_CABLE_NAME LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' and PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ")
								.list();

						fiberAuxiliary_Data = session.createSQLQuery(
								"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID and (A.FIBER_CABLE_ID LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' OR A.FIBER_CABLE_NAME LIKE '%"
										+ request.getParameter("Filteredfiber") + "%') and A.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ORDER BY B.SEQ_SORTING ASC")
								.list();

						fiberTubes = session.createSQLQuery(
								"SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER,b.TUBE_COLOR FROM FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and a.FIBER_CABLE_ID LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' OR a.FIBER_CABLE_NAME LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' and a.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ")
								.list();

						tubesAuxiliaries = session.createSQLQuery(
								"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c,FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.TUBE_ID=c.TUBE_ID and a.FIBER_CABLE_ID LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' OR a.FIBER_CABLE_NAME LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' and a.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ORDER BY c.SEQ_SORTING ASC")
								.list();

						fiberStrands = session.createSQLQuery(
								"SELECT DISTINCT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and a.FIBER_CABLE_ID LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' OR a.FIBER_CABLE_NAME LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' and a.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ")
								.list();

						strandsAuxiliaries = session.createSQLQuery(
								"SELECT DISTINCT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID and a.FIBER_CABLE_ID LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' OR a.FIBER_CABLE_NAME LIKE '%"
										+ request.getParameter("Filteredfiber") + "%' and a.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ORDER BY c.SEQ_SORTING ASC ")
								.list();

						trenchList = session.createSQLQuery(
								"SELECT DISTINCT A.TRENCH_ID,A.TRENCH_NAME,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,A.SOURCE_LATITUDE,A.SOURCE_LONGITUDE,A.DESTINATION_LONGITUDE,A.DESTINATION_LATITUDE,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUM_DUCTS,A.MAX_CAPACITY,A.LENGTH,A.PROJECT_ID,A.DRAWING_TYPE FROM DUCTS B,TRENCH A WHERE B.TRENCH_ID=A.TRENCH_ID and (A.TRENCH_ID LIKE '%"
										+ request.getParameter("FilteredTrench") + "%' OR A.TRENCH_NAME LIKE '%"
										+ request.getParameter("FilteredTrench") + "%' OR B.DUCT_ID LIKE '%"
										+ request.getParameter("Filteredduct") + "%' OR B.DUCT_NAME LIKE '%"
										+ request.getParameter("Filteredduct") + "%' ) and A.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ")
								.list();

						trenchAuxiliary_Data = session.createSQLQuery(
								"SELECT DISTINCT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.TRENCH_ID,B.DISTANCE_FROM_SOURCE,B.SEQ_SORTING,B.AUXILIARY_ID  FROM TRENCH A LEFT JOIN TRENCH_AUXILIARY_POINTS B ON A.TRENCH_ID=B.TRENCH_ID LEFT JOIN DUCTS C ON A.TRENCH_ID=C.TRENCH_ID WHERE (A.TRENCH_ID LIKE '%"
										+ request.getParameter("FilteredTrench") + "%' OR A.TRENCH_NAME LIKE '%"
										+ request.getParameter("FilteredTrench") + "%' OR C.DUCT_ID LIKE '%"
										+ request.getParameter("Filteredduct") + "%' OR C.DUCT_NAME LIKE '%"
										+ request.getParameter("Filteredduct") + "%'  ) and A.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' ORDER BY B.SEQ_SORTING ASC")
								.list();

						ductList = session.createSQLQuery(
								"SELECT DISTINCT B.DUCT_ID,B.DUCT_NAME,B.SOURCE_WARE_ID,B.SOURCE_ID,B.SOURCE_NAME,B.DESTINATION_WARE_ID,B.DESTINATION_ID,B.DESTINATION_NAME,B.SOURCE_LATITUDE,B.SOURCE_LONGITUDE,B.DESTINATION_LONGITUDE,B.DESTINATION_LATITUDE,B.SOURCE_CITY,B.DESTINATION_CITY,B.NUM_FIBERCABLES,B.NUM_FIBERTUBES,B.NUM_FIBERSTRANDS,B.LENGTH,B.TRENCH_ID,B.DRAWING_TYPE FROM DUCTS B,TRENCH A WHERE B.TRENCH_ID=A.TRENCH_ID and (A.TRENCH_ID LIKE '%"
										+ request.getParameter("FilteredTrench") + "%' OR A.TRENCH_NAME LIKE '%"
										+ request.getParameter("FilteredTrench") + "%') and A.PROJECT_ID LIKE '%"
										+ request.getParameter("Checked") + "%' OR (B.DUCT_ID LIKE '%"
										+ request.getParameter("Filteredduct") + "%' OR B.DUCT_NAME LIKE '%"
										+ request.getParameter("Filteredduct") + "%') ")
								.list();

						ductAuxiliary_Data = session.createSQLQuery(
								"SELECT DISTINCT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.DUCT_ID,B.DISTANCE_FROM_SOURCE,B.SEQ_SORTING,B.AUXILIARY_ID FROM DUCTS A LEFT JOIN DUCT_AUXILIARY_POINTS B ON A.DUCT_ID=B.DUCT_ID LEFT JOIN TRENCH D ON A.TRENCH_ID = D.TRENCH_ID WHERE (A.DUCT_ID LIKE '%"
										+ request.getParameter("Filteredduct") + "%' OR A.DUCT_NAME LIKE '%"
										+ request.getParameter("Filteredduct") + "%' OR D.TRENCH_ID LIKE '%"
										+ request.getParameter("FilteredTrench") + "%' OR D.TRENCH_NAME LIKE '%"
										+ request.getParameter("FilteredTrench") + "%'   ) ORDER BY B.SEQ_SORTING ASC ")
								.list();

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

							if (request.getParameter("Filteredfiber") != null) {

								// Select all manholes points that are source / destination / auxiliaries points
								query = session.createSQLQuery(
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
								query.setParameterList("param", allManIdsPointsArray);
								manholeList.addAll(query.list());
								allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
										? findListId(manholeList, "all")
										: new String[] { "" };

								query = session.createSQLQuery(
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
								query.setParameterList("param", allManIdsPointsArray);
								manholeList.addAll(query.list());
								allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
										? findListId(manholeList, "all")
										: new String[] { "" };

								query = session.createSQLQuery(
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
								query.setParameterList("param", allManIdsPointsArray);
								manholeList.addAll(query.list());
								allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
										? findListId(manholeList, "all")
										: new String[] { "" };

								// Select all handholes points that are source / destination / auxiliaries
								// points
								query = session.createSQLQuery(
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
								query.setParameterList("param", allHandIdsPointsArray);
								handholeList.addAll(query.list());
								allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
										? findListId(handholeList, "all")
										: new String[] { "" };

								query = session.createSQLQuery(
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
								query.setParameterList("param", allHandIdsPointsArray);
								handholeList.addAll(query.list());
								allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
										? findListId(handholeList, "all")
										: new String[] { "" };

								query = session.createSQLQuery(
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
								query.setParameterList("param", allHandIdsPointsArray);
								handholeList.addAll(query.list());
								allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
										? findListId(handholeList, "all")
										: new String[] { "" };

								// Select all db points that are source / destination / auxiliaries points
								query = session.createSQLQuery(
										" SELECT * FROM (SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%')   " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID  where B.SOURCE_ID LIKE '%DB%' AND (B.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR B.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%') " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID  where B.DESTINATION_ID LIKE '%DB%' AND (B.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR B.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%') ) where DB_ID NOT IN (:param) ");
								query.setParameterList("param", allDbIdsPointsArray);
								distribBoardList.addAll(query.list());
								allDbIdsPointsArray = (findListId(distribBoardList, "all")).length > 0
										? findListId(distribBoardList, "all")
										: new String[] { "" };

								query = session.createSQLQuery(
										" SELECT * FROM (SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%')   " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.SOURCE_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%') " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.DESTINATION_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%') ) where DB_ID NOT IN (:param)");
								query.setParameterList("param", allDbIdsPointsArray);
								distribBoardList.addAll(query.list());
								allDbIdsPointsArray = (findListId(distribBoardList, "all")).length > 0
										? findListId(distribBoardList, "all")
										: new String[] { "" };

								query = session.createSQLQuery(
										" SELECT * FROM (SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%')   " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.SOURCE_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber") + "%') " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID=B.FIBER_CABLE_ID where B.DESTINATION_ID LIKE '%DB%' AND (C.FIBER_CABLE_ID LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%' OR C.FIBER_CABLE_NAME LIKE '%"
												+ request.getParameter("Filteredfiber")
												+ "%') ) where DB_ID NOT IN (:param)");
								query.setParameterList("param", allDbIdsPointsArray);
								distribBoardList.addAll(query.list());
								allDbIdsPointsArray = (findListId(distribBoardList, "all")).length > 0
										? findListId(distribBoardList, "all")
										: new String[] { "" };
							}
							if (request.getParameter("FilteredTrench") != null
									|| request.getParameter("Filteredduct") != null) {

								// Select all manholes points that are source / destination / auxiliaries points
								// for the filtered trench
								query = session.createSQLQuery(
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
								query.setParameterList("param", allManIdsPointsArray);
								manholeList.addAll(query.list());
								allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
										? findListId(manholeList, "all")
										: new String[] { "" };

								// Select all manholes points that are source / destination / auxiliaries points
								// for the filtered duct
								query = session.createSQLQuery(
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
								query.setParameterList("param", allManIdsPointsArray);
								manholeList.addAll(query.list());
								allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
										? findListId(manholeList, "all")
										: new String[] { "" };

								// Select all handholes points that are source / destination / auxiliaries
								// points for the filtered trench
								query = session.createSQLQuery(
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
								query.setParameterList("param", allHandIdsPointsArray);
								handholeList.addAll(query.list());
								allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
										? findListId(handholeList, "all")
										: new String[] { "" };

								// Select all handholes points that are source / destination / auxiliaries
								// points for the filtered duct
								query = session.createSQLQuery(
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
								query.setParameterList("param", allHandIdsPointsArray);
								handholeList.addAll(query.list());
								allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
										? findListId(handholeList, "all")
										: new String[] { "" };

								// Select all dbs points that are source / destination / auxiliaries points for
								// the filtered trench
								query = session.createSQLQuery(
										"SELECT * FROM (SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN trench_auxiliary_points B ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN TRENCH C ON C.TRENCH_ID=B.TRENCH_ID LEFT JOIN DUCTS G ON C.trench_id=G.trench_id  where B.AUXILIARY_POINT_ID LIKE '%DB%' AND (G.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR G.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR C.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN TRENCH B ON B.SOURCE_ID = A.DB_ID LEFT JOIN DUCTS C ON c.trench_id=b.trench_id  WHERE B.SOURCE_ID LIKE 'DB%' AND  (C.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR B.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN TRENCH B  ON B.DESTINATION_ID = A.DB_ID LEFT JOIN DUCTS C ON c.trench_id=b.trench_id  where B.DESTINATION_ID LIKE '%DB%' AND (C.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR B.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench")
												+ "%') )  where DB_ID NOT IN (:param) ");
								query.setParameterList("param", allDbIdsPointsArray);
								distribBoardList.addAll(query.list());
								allDbIdsPointsArray = (findListId(distribBoardList, "all")).length > 0
										? findListId(distribBoardList, "all")
										: new String[] { "" };

								// Select all dbs points that are source / destination / auxiliaries points for
								// the filtered duct
								query = session.createSQLQuery(
										"SELECT * FROM (SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN duct_auxiliary_points B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN DUCTS C ON C.DUCT_ID=B.DUCT_ID LEFT JOIN TRENCH G ON G.trench_id=C.trench_id where B.AUXILIARY_POINT_ID LIKE '%DB%' AND (C.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR G.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR G.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN DUCTS B ON B.SOURCE_ID = A.DB_ID LEFT JOIN TRENCH C ON c.trench_id=b.trench_id WHERE B.SOURCE_ID LIKE '%DB%' AND  (B.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR C.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench") + "%')  " + " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN DUCTS B  ON B.DESTINATION_ID = A.DB_ID LEFT JOIN TRENCH C ON c.trench_id=b.trench_id  where B.DESTINATION_ID LIKE '%DB%' AND (B.DUCT_ID LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR B.DUCT_Name LIKE '%"
												+ request.getParameter("Filteredduct") + "%' OR C.TRENCH_ID LIKE '%"
												+ request.getParameter("FilteredTrench") + "%' OR C.TRENCH_NAME LIKE '%"
												+ request.getParameter("FilteredTrench")
												+ "%') )  where DB_ID NOT IN (:param) ");
								query.setParameterList("param", allDbIdsPointsArray);
								distribBoardList.addAll(query.list());
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
							query = session.createSQLQuery(
									"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,  A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A left JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id  where (A.JUNCTION_ID LIKE '%"
											+ filteredManJunction + "%' OR A.JUNCTION_NAME LIKE '%"
											+ filteredManJunction + "%') and A.PROJECT_ID LIKE '%"
											+ request.getParameter("Checked") + "%' AND B.MANHOLE_ID IN (:param)  ");
							junctionManholeList = query.setParameterList("param", allManIdsPointsArray).list();
							query = session.createSQLQuery(
									"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A LEFT JOIN handhole B ON A.PHYSICAL_LAYER_ID = B.handhole_id where (A.JUNCTION_ID LIKE '%"
											+ filteredHandJunction + "%' OR A.JUNCTION_NAME LIKE '%"
											+ filteredHandJunction + "%') and A.PROJECT_ID LIKE '%"
											+ request.getParameter("Checked") + "%' AND B.HANDHOLE_ID IN (:param)  ");
							junctionHandholeList = query.setParameterList("param", allHandIdsPointsArray).list();
						}
					} else if (StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "circleRange")
							|| StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "StartEnd")
							|| StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "circleRange_multy")) {
						filterFlag = 2;
						checkedOption = request.getParameter("Checked");
						if (StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "circleRange")) {
							// System.out.println("it is inside circle range");

							String noOfPoints = request.getParameter("noOfPoints");
							String closestDisRange = request.getParameter("closestDisRange");
							String closestLatPoint = request.getParameter("closestLatPoint");
							String closestLongPoint = request.getParameter("closestLongPoint");
							String showPointsType = request.getParameter("getRelatedPoints");

							List<Object[]> manholeListQuery = session.createSQLQuery(
									"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),DM_NAME FROM MANHOLE  ")
									.list();
							manholeList = findNearestArray(manholeListQuery, Double.valueOf(closestLatPoint),
									Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "Manhole",
									noOfPoints);
							// System.out.println("manholeList "+mapper.writeValueAsString(manholeList));

							List<Object[]> handholeListQuery = session.createSQLQuery(
									"SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),DM_NAME FROM HANDHOLE")
									.list();
							handholeList = findNearestArray(handholeListQuery, Double.valueOf(closestLatPoint),
									Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "Handhole",
									noOfPoints);

							List<Object[]> distribBoardListQuery = session.createSQLQuery(
									"SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY FROM DISTRIBUTION_BOARD")
									.list();
							distribBoardList = findNearestArray(distribBoardListQuery, Double.valueOf(closestLatPoint),
									Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "DistribBoard",
									noOfPoints);
							
							List<Object[]> entrepriseListQuery = session.createSQLQuery(
									"SELECT DISTINCT NODE_ID,NODE_NAME,LONGITUDE,LATITUDE FROM NODE_ACTIVE ")
									.list();
							if (entrepriseListQuery != null && closestLatPoint != null && closestLongPoint != null && closestDisRange != null && noOfPoints != null) {
							EntrepriseList = findNearestArray(entrepriseListQuery, Double.valueOf(closestLatPoint),
									Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "Entreprise",
									noOfPoints);
							}
							
							List<Object[]> transmissionListQuery = session.createSQLQuery(
									"SELECT DISTINCT NODE_ID,NODE_NAME,LONGITUDE,LATITUDE FROM NODE_ACTIVE ")
									.list();
							if (transmissionListQuery != null && closestLatPoint != null && closestLongPoint != null && closestDisRange != null && noOfPoints != null) {
							TransmissionList = findNearestArray(transmissionListQuery, Double.valueOf(closestLatPoint),
									Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "Transmission",
									noOfPoints);
							}
							List<Object[]> nearstPoints = new ArrayList<Object[]>();
							nearstPoints.addAll(manholeList);
							nearstPoints.addAll(handholeList);
							nearstPoints.addAll(distribBoardList);
							nearstPoints.addAll(EntrepriseList);
							nearstPoints.addAll(TransmissionList);

							String[] idsArray = (findListId(nearstPoints, "all")).length > 0
									? findListId(nearstPoints, "all")
									: new String[] { "" };
							// Query fiberQuery= session.createSQLQuery("SELECT distinct
							// A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT,
							// A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT
							// COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS
							// Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE
							// C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS
							// Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL
							// FROM FIBER_CABLES A LEFT JOIN FIBER_AUXILIARY_POINTS D ON
							// A.FIBER_CABLE_ID=D.FIBER_CABLE_ID WHERE A.SOURCE_ID IN (:param1) OR
							// A.DESTINATION_ID IN (:param1) OR D.AUXILIARY_POINT_ID IN (:param1)");
							Query fiberQuery = session.createSQLQuery(
									"SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A "
											+ "LEFT  JOIN FIBER_AUXILIARY_POINTS D ON A.FIBER_CABLE_ID=D.FIBER_CABLE_ID "
											+ "LEFT  JOIN FIBER_TUBES B ON A.FIBER_CABLE_ID=B.FIBER_CABLE_ID "
											+ "LEFT  JOIN FIBER_STRANDS C ON A.FIBER_CABLE_ID=C.FIBER_CABLE_ID "
											+ "LEFT  JOIN TUBE_AUXILIARY_POINTS E ON A.FIBER_CABLE_ID=E.FIBER_CABLE_ID "
											+ "LEFT  JOIN STRAND_AUXILIARY_POINTS F ON A.FIBER_CABLE_ID=F.FIBER_CABLE_ID "
											+ "WHERE A.SOURCE_ID IN (:param1) OR A.DESTINATION_ID IN (:param1) OR D.AUXILIARY_POINT_ID IN (:param1) OR B.SOURCE_ID IN (:param1) OR B.DESTINATION_ID IN (:param1) OR C.SOURCE_ID IN (:param1) OR C.DESTINATION_ID IN (:param1) OR E.AUXILIARY_POINT_ID IN (:param1) OR F.AUXILIARY_POINT_ID IN (:param1) ");

							fiberQuery.setParameterList("param1", idsArray);
							fiberList = fiberQuery.list();
							// System.out.println("fiberList "+mapper.writeValueAsString(fiberList));

							Query fiberAuxiliaryQuery = session.createSQLQuery(
									"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID IN (:param1) ORDER BY B.SEQ_SORTING ASC");
							fiberAuxiliaryQuery.setParameterList("param1",
									(findListId(fiberList, "FiberCable")).length > 0
											? findListId(fiberList, "FiberCable")
											: new String[] { "" });
							fiberAuxiliary_Data = fiberAuxiliaryQuery.list();

							// Query fiberTubesQuery = session.createSQLQuery("SELECT DISTINCT
							// b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT
							// COUNT(*) FROM FIBER_STRANDS C WHERE
							// C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME FROM FIBER_TUBES
							// b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID AND b.SOURCE_ID IN
							// (:param1) OR b.DESTINATION_ID IN (:param1)");
							Query fiberTubesQuery = session.createSQLQuery(
									"SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER,b.TUBE_COLOR FROM FIBER_TUBES b "
											+ "LEFT JOIN TUBE_AUXILIARY_POINTS a ON a.TUBE_ID=b.TUBE_ID "
											+ "LEFT JOIN FIBER_STRANDS c ON b.TUBE_ID=c.TUBE_ID "
											+ "LEFT JOIN STRAND_AUXILIARY_POINTS d ON b.FIBER_CABLE_ID=d.FIBER_CABLE_ID "
											+ "WHERE b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1) OR a.AUXILIARY_POINT_ID IN (:param1) OR c.SOURCE_ID IN (:param1) OR c.DESTINATION_ID IN (:param1) OR d.AUXILIARY_POINT_ID IN (:param1) ");

							fiberTubesQuery.setParameterList("param1", idsArray);
							fiberTubes = fiberTubesQuery.list();
							// System.out.println("fiberTubes "+mapper.writeValueAsString(fiberTubes));

							Query tubesAuxiliariesQuery = session.createSQLQuery(
									"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON  b.TUBE_ID=c.TUBE_ID LEFT JOIN FIBER_CABLES a ON a.FIBER_CABLE_ID=b.FIBER_CABLE_ID WHERE c.TUBE_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC");
							tubesAuxiliariesQuery.setParameterList("param1",
									(findListId(fiberTubes, "Tube")).length > 0 ? findListId(fiberTubes, "Tube")
											: new String[] { "" });
							tubesAuxiliaries = tubesAuxiliariesQuery.list();
							// System.out.println("tubesAuxiliaries
							// "+mapper.writeValueAsString(tubesAuxiliaries));

							// Query fiberStrandsQuery = session.createSQLQuery("SELECT DISTINCT
							// b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME
							// FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID
							// AND b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1)");
							Query fiberStrandsQuery = session.createSQLQuery(
									"SELECT DISTINCT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR FROM FIBER_STRANDS b LEFT JOIN STRAND_AUXILIARY_POINTS a ON b.STRAND_ID=a.STRAND_ID WHERE b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1) OR a.AUXILIARY_POINT_ID IN (:param1) ");
							fiberStrandsQuery.setParameterList("param1", idsArray);
							fiberStrands = fiberStrandsQuery.list();

							Query strandsAuxiliariesQuery = session.createSQLQuery(
									"SELECT DISTINCT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID AND c.STRAND_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC ");
							strandsAuxiliariesQuery.setParameterList("param1",
									(findListId(fiberStrands, "Strand")).length > 0 ? findListId(fiberStrands, "Strand")
											: new String[] { "" });
							strandsAuxiliaries = strandsAuxiliariesQuery.list();

							// To retrieve the data needed in show points/real points & are outside the zone
							if (showPointsType.equals("1")) {
								Query manholeData = session.createSQLQuery(
										" SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name ,A.LONGITUDE as LONGITUDE,A.LATITUDE as LATITUDE ,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.FIBER_CABLE_ID IN (:param1) "
												+ " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1)  ) where manhole_id NOT IN (:param2) ");
								manholeData.setParameterList("param1",
										(findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" });
								manholeData.setParameterList("param2", idsArray);
								if (manholeList.size() > 0) {
									newList = findNearestArray(manholeData.list(), Double.valueOf(closestLatPoint),
											Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange),
											"ManHandhole_OutOfZone", noOfPoints);
									manholeList.addAll(newList);
								} else {
									manholeList = findNearestArray(manholeData.list(), Double.valueOf(closestLatPoint),
											Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange),
											"ManHandhole_OutOfZone", noOfPoints);
								}

								Query handholeData = session.createSQLQuery(
										" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name,A.LONGITUDE as LONGITUDE ,A.LATITUDE as LATITUDE,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.FIBER_CABLE_ID IN (:param1) "
												+ " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ "UNION"
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) ) where handhole_id NOT IN (:param2) ");
								handholeData.setParameterList("param1",
										(findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" });
								handholeData.setParameterList("param2", idsArray);
								if (handholeList.size() > 0) {
									newList = findNearestArray(handholeData.list(), Double.valueOf(closestLatPoint),
											Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange),
											"ManHandhole_OutOfZone", noOfPoints);
									handholeList.addAll(newList);
								} else {
									handholeList = findNearestArray(handholeData.list(),
											Double.valueOf(closestLatPoint), Double.valueOf(closestLongPoint),
											Double.valueOf(closestDisRange), "ManHandhole_OutOfZone", noOfPoints);
								}

								Query dbData = session.createSQLQuery(
										" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID,A.DB_LONGITUDE as DB_LONGITUDE,A.DB_LATITUDE as DB_LATITUDE,A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.FIBER_CABLE_ID IN (:param1)   "
												+ " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ "UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) ) where DB_ID NOT IN (:param2)  ");
								dbData.setParameterList("param1",
										(findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" });
								dbData.setParameterList("param2", idsArray);
								if (distribBoardList.size() > 0) {
									newList = findNearestArray(dbData.list(), Double.valueOf(closestLatPoint),
											Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange),
											"DB_OutOfZone", noOfPoints);
									distribBoardList.addAll(newList);
								} else {
									distribBoardList = findNearestArray(dbData.list(), Double.valueOf(closestLatPoint),
											Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange),
											"DB_OutOfZone", noOfPoints);
								}
							}
							junctionManholeList = session.createSQLQuery(
									"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id")
									.list();

							junctionHandholeList = session.createSQLQuery(
									"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id")
									.list();

							model.addAttribute("closestLatPoint", closestLatPoint);
							model.addAttribute("closestLongPoint", closestLongPoint);
							model.addAttribute("closestDisRange", closestDisRange);
							model.addAttribute("noP", noOfPoints);
							model.addAttribute("getRelatedPoints", showPointsType);
							
						} else if(StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "StartEnd")){
							String startLongPoint = request.getParameter("startLongPoint");
							String newStartLngPt = startLongPoint;
							String startLatPoint = request.getParameter("startLatPoint");
							String newStartLatPt = startLatPoint;
							String endLongPoint = request.getParameter("endLongPoint");
							String newEndLngPt = endLongPoint;
							String endLatPoint = request.getParameter("endLatPoint");
							String newEndLatPt = endLatPoint;
							String showPointsType = request.getParameter("getRelatedPoints");

							/*
							 * List<Object[]> manholeListQuery= session.
							 * createSQLQuery("SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),DM_NAME FROM MANHOLE  where to_number(LONGITUDE) >= '"
							 * +endLongPoint+"'  and  to_number(LATITUDE) >= '"
							 * +endLatPoint+"'  and to_number(LONGITUDE) <=  '"
							 * +startLongPoint+"' and  to_number(LATITUDE) <= '"+startLatPoint+"' ").list();
							 * System.out.println("manholeListQuery "+mapper.writeValueAsString(
							 * manholeListQuery)); manholeList =
							 * findMarkerPointsArrayBetween(manholeListQuery,Double.valueOf(startLongPoint),
							 * Double.valueOf(startLatPoint),Double.valueOf(endLongPoint),Double.valueOf(
							 * endLatPoint),"Manhole");
							 * 
							 * List<Object[]> handholeListQuery = session.
							 * createSQLQuery("SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),DM_NAME FROM HANDHOLE  where to_number(LONGITUDE) >= '"
							 * +endLongPoint+"'  and  to_number(LATITUDE) >= '"
							 * +endLatPoint+"'  and to_number(LONGITUDE) <=  '"
							 * +startLongPoint+"' and  to_number(LATITUDE) <= '"+startLatPoint+"' ").list();
							 * handholeList =
							 * findMarkerPointsArrayBetween(handholeListQuery,Double.valueOf(startLongPoint)
							 * ,Double.valueOf(startLatPoint),Double.valueOf(endLongPoint),Double.valueOf(
							 * endLatPoint),"Handhole");
							 * 
							 * List<Object[]> distribBoardListQuery = session.
							 * createSQLQuery("SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY FROM DISTRIBUTION_BOARD  where to_number(LONGITUDE) >= '"
							 * +endLongPoint+"'  and  to_number(LATITUDE) >= '"
							 * +endLatPoint+"'  and to_number(LONGITUDE) <=  '"
							 * +startLongPoint+"' and  to_number(LATITUDE) <= '"+startLatPoint+"' ").list();
							 * distribBoardList =
							 * findMarkerPointsArrayBetween(distribBoardListQuery,Double.valueOf(
							 * startLongPoint),Double.valueOf(startLatPoint),Double.valueOf(endLongPoint),
							 * Double.valueOf(endLatPoint),"DistribBoard");
							 */

							if (Double.parseDouble(startLongPoint) > Double.parseDouble(endLongPoint)) {
								newStartLngPt = endLongPoint;
								newEndLngPt = startLongPoint;
							}
							if (Double.parseDouble(startLatPoint) > Double.parseDouble(endLatPoint)) {
								newStartLatPt = endLatPoint;
								newEndLatPt = startLatPoint;
							}

							manholeList = session.createSQLQuery(
									"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),DM_NAME FROM MANHOLE  where to_number(LONGITUDE) >= "
											+ newStartLngPt + " and  to_number(LATITUDE) >= " + newStartLatPt
											+ " and to_number(LONGITUDE) <=  " + newEndLngPt
											+ " and  to_number(LATITUDE) <= " + newEndLatPt)
									.list();
							// System.out.println("manholeListQuery
							// "+mapper.writeValueAsString(manholeList));

							handholeList = session.createSQLQuery(
									"SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),DM_NAME FROM HANDHOLE  where to_number(LONGITUDE) >= "
											+ newStartLngPt + " and  to_number(LATITUDE) >= " + newStartLatPt
											+ " and to_number(LONGITUDE) <= " + newEndLngPt
											+ " and  to_number(LATITUDE) <= " + newEndLatPt)
									.list();
							// System.out.println("handholeList "+mapper.writeValueAsString(handholeList));
							distribBoardList = session.createSQLQuery(
									"SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY FROM DISTRIBUTION_BOARD  where to_number(DB_LONGITUDE) >= "
											+ newStartLngPt + " and  to_number(DB_LATITUDE) >= " + newStartLatPt
											+ " and to_number(DB_LONGITUDE) <= " + newEndLngPt
											+ " and  to_number(DB_LATITUDE) <= " + newEndLatPt)
									.list();
							
							EntrepriseList = session.createSQLQuery(
									"SELECT DISTINCT NODE_ID,NODE_NAME,LONGITUDE,LATITUDE FROM NODE_ACTIVE  where DOMAIN = 'Enterprise' AND to_number(LONGITUDE) >= "
											+ newStartLngPt + " and  to_number(LATITUDE) >= " + newStartLatPt
											+ " and to_number(LONGITUDE) <= " + newEndLngPt
											+ " and  to_number(LATITUDE) <= " + newEndLatPt)
									.list();
							
							TransmissionList = session.createSQLQuery(
									"SELECT DISTINCT NODE_ID,LONGITUDE,LATITUDE,NODE_NAME  FROM NODE_ACTIVE  where DOMAIN = 'Transmission' AND to_number(LONGITUDE) >= "
											+ newStartLngPt + " and  to_number(LATITUDE) >= " + newStartLatPt
											+ " and to_number(LONGITUDE) <= " + newEndLngPt
											+ " and  to_number(LATITUDE) <= " + newEndLatPt)
									.list();
							
							

							List<Object[]> nearstPoints = new ArrayList<Object[]>();
							nearstPoints.addAll(manholeList);
							nearstPoints.addAll(handholeList);
							nearstPoints.addAll(distribBoardList);
							nearstPoints.addAll(EntrepriseList);
							nearstPoints.addAll(TransmissionList);

							String[] idsArray = (findListId(nearstPoints, "all")).length > 0
									? findListId(nearstPoints, "all")
									: new String[] { "" };
							// System.out.println("listIds "+mapper.writeValueAsString(idsArray));
							// Query fiberQuery= session.createSQLQuery("SELECT distinct
							// A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT,
							// A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT
							// COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS
							// Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE
							// C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS
							// Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL
							// FROM FIBER_CABLES A LEFT JOIN FIBER_AUXILIARY_POINTS D ON
							// A.FIBER_CABLE_ID=D.FIBER_CABLE_ID WHERE A.SOURCE_ID IN (:param1) OR
							// A.DESTINATION_ID IN (:param1) OR D.AUXILIARY_POINT_ID IN (:param1)");
							Query fiberQuery = session.createSQLQuery(
									"SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A "
											+ "LEFT  JOIN FIBER_AUXILIARY_POINTS D ON A.FIBER_CABLE_ID=D.FIBER_CABLE_ID "
											+ "LEFT  JOIN FIBER_TUBES B ON A.FIBER_CABLE_ID=B.FIBER_CABLE_ID "
											+ "LEFT  JOIN FIBER_STRANDS C ON A.FIBER_CABLE_ID=C.FIBER_CABLE_ID "
											+ "LEFT  JOIN TUBE_AUXILIARY_POINTS E ON A.FIBER_CABLE_ID=E.FIBER_CABLE_ID "
											+ "LEFT  JOIN STRAND_AUXILIARY_POINTS F ON A.FIBER_CABLE_ID=F.FIBER_CABLE_ID "
											+ "WHERE A.SOURCE_ID IN (:param1) OR A.DESTINATION_ID IN (:param1) OR D.AUXILIARY_POINT_ID IN (:param1) OR B.SOURCE_ID IN (:param1) OR B.DESTINATION_ID IN (:param1) OR C.SOURCE_ID IN (:param1) OR C.DESTINATION_ID IN (:param1) OR E.AUXILIARY_POINT_ID IN (:param1) OR F.AUXILIARY_POINT_ID IN (:param1) ");

							fiberQuery.setParameterList("param1", idsArray);
							fiberList = fiberQuery.list();
							// System.out.println("fiberCableMarkerPointsBetween
							// "+mapper.writeValueAsString(fiberCableMarkerPointsBetween));

							Query fiberAuxiliaryQuery = session.createSQLQuery(
									"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID IN (:param1) ORDER BY B.SEQ_SORTING ASC");
							fiberAuxiliaryQuery.setParameterList("param1",
									(findListId(fiberList, "FiberCable")).length > 0
											? findListId(fiberList, "FiberCable")
											: new String[] { "" });
							fiberAuxiliary_Data = fiberAuxiliaryQuery.list();
							// System.out.println("fiberAuxiliaryQuery
							// "+mapper.writeValueAsString(fiberAuxiliary_Data));

							// Query fiberTubesQuery = session.createSQLQuery("SELECT
							// b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT
							// COUNT(*) FROM FIBER_STRANDS C WHERE
							// C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME FROM FIBER_TUBES
							// b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID AND b.SOURCE_ID IN
							// (:param1) OR b.DESTINATION_ID IN (:param1)");
							Query fiberTubesQuery = session.createSQLQuery(
									"SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER,b.TUBE_COLOR FROM FIBER_TUBES b "
											+ "LEFT JOIN TUBE_AUXILIARY_POINTS a ON a.TUBE_ID=b.TUBE_ID "
											+ "LEFT JOIN FIBER_STRANDS c ON b.TUBE_ID=c.TUBE_ID "
											+ "LEFT JOIN STRAND_AUXILIARY_POINTS d ON b.FIBER_CABLE_ID=d.FIBER_CABLE_ID "
											+ "WHERE b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1) OR a.AUXILIARY_POINT_ID IN (:param1) OR c.SOURCE_ID IN (:param1) OR c.DESTINATION_ID IN (:param1) OR d.AUXILIARY_POINT_ID IN (:param1) ");

							fiberTubesQuery.setParameterList("param1", idsArray);
							fiberTubes = fiberTubesQuery.list();
							// System.out.println("fiberTubes "+mapper.writeValueAsString(fiberTubes));

							Query tubesAuxiliariesQuery = session.createSQLQuery(
									"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON  b.TUBE_ID=c.TUBE_ID LEFT JOIN FIBER_CABLES a ON a.FIBER_CABLE_ID=b.FIBER_CABLE_ID WHERE c.TUBE_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC");
							tubesAuxiliariesQuery.setParameterList("param1",
									(findListId(fiberTubes, "Tube")).length > 0 ? findListId(fiberTubes, "Tube")
											: new String[] { "" });
							tubesAuxiliaries = tubesAuxiliariesQuery.list();
							// System.out.println("tubesAuxiliaries
							// "+mapper.writeValueAsString(tubesAuxiliaries));

							// Query fiberStrandsQuery = session.createSQLQuery("SELECT
							// b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME
							// FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID
							// AND b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1)");
							Query fiberStrandsQuery = session.createSQLQuery(
									"SELECT DISTINCT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR FROM FIBER_STRANDS b LEFT JOIN STRAND_AUXILIARY_POINTS a ON b.STRAND_ID=a.STRAND_ID WHERE b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1) OR a.AUXILIARY_POINT_ID IN (:param1) ");
							fiberStrandsQuery.setParameterList("param1", idsArray);
							fiberStrands = fiberStrandsQuery.list();

							Query strandsAuxiliariesQuery = session.createSQLQuery(
									"SELECT DISTINCT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID AND c.STRAND_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC ");
							strandsAuxiliariesQuery.setParameterList("param1",
									(findListId(fiberStrands, "Strand")).length > 0 ? findListId(fiberStrands, "Strand")
											: new String[] { "" });
							strandsAuxiliaries = strandsAuxiliariesQuery.list();

							// To retrieve the data needed in show points/real points & are outside the zone
							if (showPointsType.equals("1")) {
								Query manholeData = session.createSQLQuery(
										" SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name ,A.LONGITUDE as LONGITUDE,A.LATITUDE as LATITUDE ,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.FIBER_CABLE_ID IN (:param1) "
												+ " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1)  ) where manhole_id NOT IN (:param2) ");
								manholeData.setParameterList("param1",
										(findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" });
								manholeData.setParameterList("param2", idsArray);

								if (manholeList.size() > 0) {
									manholeList.addAll(manholeData.list());
								} else {
									manholeList = manholeData.list();
								}
								Query handholeData = session.createSQLQuery(
										" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name,A.LONGITUDE as LONGITUDE ,A.LATITUDE as LATITUDE,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.FIBER_CABLE_ID IN (:param1) "
												+ " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ "UNION"
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) ) where handhole_id NOT IN (:param2) ");
								handholeData.setParameterList("param1",
										(findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" });
								handholeData.setParameterList("param2", idsArray);

								if (handholeList.size() > 0) {
									handholeList.addAll(handholeData.list());
								} else {
									handholeList = handholeData.list();
								}

								Query dbData = session.createSQLQuery(
										" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID,A.DB_LONGITUDE as DB_LONGITUDE,A.DB_LATITUDE as DB_LATITUDE,A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.FIBER_CABLE_ID IN (:param1)   "
												+ " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ "UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) ) where DB_ID NOT IN (:param2)  ");
								dbData.setParameterList("param1",
										(findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" });
								dbData.setParameterList("param2", idsArray);

								if (distribBoardList.size() > 0) {
									distribBoardList.addAll(dbData.list());
								} else {
									distribBoardList = dbData.list();
								}
							}

							junctionManholeList = session.createSQLQuery(
									"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id")
									.list();

							junctionHandholeList = session.createSQLQuery(
									"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id")
									.list();

							model.addAttribute("startLongPoint", startLongPoint);
							model.addAttribute("startLatPoint", startLatPoint);
							model.addAttribute("endLongPoint", endLongPoint);
							model.addAttribute("endLatPoint", endLatPoint);
							model.addAttribute("getRelatedPoints", showPointsType);
							
					} else if (StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "circleRange_multy")) {
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
							String seq = seqs[i];
							String siteId = siteIds[i];
							String lng = lngs[i];
							String lat = lats[i];

							Map<String, Object> row = new HashMap<>();
							row.put("seq", seqs[i]);
							row.put("siteId", siteIds[i]);
							row.put("lat", lats[i]);
							row.put("lng", lngs[i]);
							rowData.put("row" + i, new ArrayList<>(row.values()));
							
							List<Object[]> manholeListQuery  = session.createSQLQuery(
									"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),DM_NAME FROM MANHOLE  where to_number(LONGITUDE) <=  '"
											+ lng + "' and  to_number(LATITUDE) <= '" + lat + "' ").list();

							manholeListPt = findNearestArray(manholeListQuery, Double.valueOf(lats[i]),
									Double.valueOf(lngs[i]), Double.valueOf(closestDisRange), "Manhole",noOfPoints);
							
							
							List<Object[]> handholeListQuery  = session.createSQLQuery(
									"SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),DM_NAME FROM HANDHOLE  where to_number(LONGITUDE) <=  '"
											+ lng + "' and  to_number(LATITUDE) <= '" + lat + "' ").list();
							
							handholeListPt = findNearestArray(handholeListQuery, Double.valueOf(lats[i]),
									Double.valueOf(lngs[i]), Double.valueOf(closestDisRange), "Handhole",noOfPoints);
							
							List<Object[]> distribBoardListQuery = session.createSQLQuery(
									"SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY FROM DISTRIBUTION_BOARD  where to_number(DB_LONGITUDE) <=  '"
											+ lng + "' and  to_number(DB_LATITUDE) <= '" + lat + "' ")
									.list();
							
							distribBoardListPt = findNearestArray(distribBoardListQuery, Double.valueOf(lats[i]),
									Double.valueOf(lngs[i]), Double.valueOf(closestDisRange), "DistribBoard",
									noOfPoints);

							List<Object[]> nearstPoints = new ArrayList<Object[]>();
							nearstPoints.addAll(manholeListPt);
							nearstPoints.addAll(handholeListPt);
							nearstPoints.addAll(distribBoardListPt);

							String[] idsArray = (findListId(nearstPoints, "all")).length > 0
									? findListId(nearstPoints, "all")
									: new String[] { "" };

							Query fiberQuery = session.createSQLQuery(
									"SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL FROM FIBER_CABLES A "
											+ "LEFT  JOIN FIBER_AUXILIARY_POINTS D ON A.FIBER_CABLE_ID=D.FIBER_CABLE_ID "
											+ "LEFT  JOIN FIBER_TUBES B ON A.FIBER_CABLE_ID=B.FIBER_CABLE_ID "
											+ "LEFT  JOIN FIBER_STRANDS C ON A.FIBER_CABLE_ID=C.FIBER_CABLE_ID "
											+ "LEFT  JOIN TUBE_AUXILIARY_POINTS E ON A.FIBER_CABLE_ID=E.FIBER_CABLE_ID "
											+ "LEFT  JOIN STRAND_AUXILIARY_POINTS F ON A.FIBER_CABLE_ID=F.FIBER_CABLE_ID "
											+ "WHERE A.SOURCE_ID IN (:param1) OR A.DESTINATION_ID IN (:param1) OR D.AUXILIARY_POINT_ID IN (:param1) OR B.SOURCE_ID IN (:param1) OR B.DESTINATION_ID IN (:param1) OR C.SOURCE_ID IN (:param1) OR C.DESTINATION_ID IN (:param1) OR E.AUXILIARY_POINT_ID IN (:param1) OR F.AUXILIARY_POINT_ID IN (:param1) ");

							fiberQuery.setParameterList("param1", idsArray);
							fiberListPt = fiberQuery.list();

							Query fiberAuxiliaryQuery = session.createSQLQuery(
									"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID IN (:param1) ORDER BY B.SEQ_SORTING ASC");
							fiberAuxiliaryQuery.setParameterList("param1",
									(findListId(fiberList, "FiberCable")).length > 0
											? findListId(fiberList, "FiberCable")
											: new String[] { "" });
							fiberAuxiliary_DataPt = fiberAuxiliaryQuery.list();

							Query fiberTubesQuery = session.createSQLQuery(
									"SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER,b.TUBE_COLOR FROM FIBER_TUBES b "
											+ "LEFT JOIN TUBE_AUXILIARY_POINTS a ON a.TUBE_ID=b.TUBE_ID "
											+ "LEFT JOIN FIBER_STRANDS c ON b.TUBE_ID=c.TUBE_ID "
											+ "LEFT JOIN STRAND_AUXILIARY_POINTS d ON b.FIBER_CABLE_ID=d.FIBER_CABLE_ID "
											+ "WHERE b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1) OR a.AUXILIARY_POINT_ID IN (:param1) OR c.SOURCE_ID IN (:param1) OR c.DESTINATION_ID IN (:param1) OR d.AUXILIARY_POINT_ID IN (:param1) ");

							fiberTubesQuery.setParameterList("param1", idsArray);
							fiberTubesPt = fiberTubesQuery.list();

							Query tubesAuxiliariesQuery = session.createSQLQuery(
									"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON  b.TUBE_ID=c.TUBE_ID LEFT JOIN FIBER_CABLES a ON a.FIBER_CABLE_ID=b.FIBER_CABLE_ID WHERE c.TUBE_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC");
							tubesAuxiliariesQuery.setParameterList("param1",
									(findListId(fiberTubes, "Tube")).length > 0 ? findListId(fiberTubes, "Tube")
											: new String[] { "" });
							tubesAuxiliariesPt = tubesAuxiliariesQuery.list();

							Query fiberStrandsQuery = session.createSQLQuery(
									"SELECT DISTINCT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR FROM FIBER_STRANDS b LEFT JOIN STRAND_AUXILIARY_POINTS a ON b.STRAND_ID=a.STRAND_ID WHERE b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1) OR a.AUXILIARY_POINT_ID IN (:param1) ");
							fiberStrandsQuery.setParameterList("param1", idsArray);
							fiberStrandsPt = fiberStrandsQuery.list();

							Query strandsAuxiliariesQuery = session.createSQLQuery(
									"SELECT DISTINCT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID AND c.STRAND_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC ");
							strandsAuxiliariesQuery.setParameterList("param1",
									(findListId(fiberStrands, "Strand")).length > 0 ? findListId(fiberStrands, "Strand")
											: new String[] { "" });
							strandsAuxiliariesPt = strandsAuxiliariesQuery.list();

						if (showPointsType.equals("1")) {
							Query manholeData = session.createSQLQuery(
									" SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name ,A.LONGITUDE as LONGITUDE,A.LATITUDE as LATITUDE ,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.FIBER_CABLE_ID IN (:param1) "
											+ " UNION "
											+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1) "
											+ " UNION "
											+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1)  ) where manhole_id NOT IN (:param2) ");
							manholeData.setParameterList("param1",
									(findListId(fiberListPt, "FiberCable")).length > 0
											? findListId(fiberListPt, "FiberCable")
											: new String[] { "" });
							manholeData.setParameterList("param2", idsArray);

							if (manholeListPt.size() > 0) {
								newListPt = findNearestArray(manholeData.list(), Double.valueOf(lats[i]),
										Double.valueOf(lngs[i]), Double.valueOf(closestDisRange),"ManHandhole_OutOfZone", noOfPoints);
								manholeListPt.addAll(newListPt);
							} else {
								manholeListPt = findNearestArray(manholeData.list(), Double.valueOf(lats[i]),
										Double.valueOf(lngs[i]), Double.valueOf(closestDisRange),"ManHandhole_OutOfZone", noOfPoints);
							}
							Query handholeData = session.createSQLQuery(
									" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name,A.LONGITUDE as LONGITUDE ,A.LATITUDE as LATITUDE,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.FIBER_CABLE_ID IN (:param1) "
											+ " UNION "
											+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) "
											+ "UNION"
											+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) ) where handhole_id NOT IN (:param2) ");
							handholeData.setParameterList("param1",
									(findListId(fiberListPt, "FiberCable")).length > 0
											? findListId(fiberListPt, "FiberCable")
											: new String[] { "" });
							handholeData.setParameterList("param2", idsArray);

							if (handholeListPt.size() > 0) {
								newListPt = findNearestArray(handholeData.list(), Double.valueOf(lats[i]),
										Double.valueOf(lngs[i]), Double.valueOf(closestDisRange),
										"ManHandhole_OutOfZone", noOfPoints);
								handholeListPt.addAll(newListPt);
							} else {
								newListPt = findNearestArray(handholeData.list(), Double.valueOf(lats[i]),
										Double.valueOf(lngs[i]), Double.valueOf(closestDisRange),
										"ManHandhole_OutOfZone", noOfPoints);
							}

							Query dbData = session.createSQLQuery(
									" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID,A.DB_LONGITUDE as DB_LONGITUDE,A.DB_LATITUDE as DB_LATITUDE,A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.FIBER_CABLE_ID IN (:param1)   "
											+ " UNION "
											+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) "
											+ "UNION "
											+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) ) where DB_ID NOT IN (:param2)  ");
							dbData.setParameterList("param1",
									(findListId(fiberListPt, "FiberCable")).length > 0
											? findListId(fiberListPt, "FiberCable")
											: new String[] { "" });
							dbData.setParameterList("param2", idsArray);

								if (distribBoardListPt.size() > 0) {
									newListPt = findNearestArray(dbData.list(), Double.valueOf(lats[i]),
											Double.valueOf(lngs[i]), Double.valueOf(closestDisRange),
											"DB_OutOfZone", noOfPoints);
									distribBoardListPt.addAll(newListPt);
							} else {
								distribBoardListPt = findNearestArray(dbData.list(), Double.valueOf(lats[i]),
										Double.valueOf(lngs[i]), Double.valueOf(closestDisRange),
										"DB_OutOfZone", noOfPoints);
							}
						}
							junctionManholeListPt = session.createSQLQuery(
									"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id")
									.list();

							junctionHandholeListPt = session.createSQLQuery(
									"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id")
									.list();

							ptPhysicalListResult.put("Junction_Manhole", junctionManholeListPt);
							ptPhysicalListResult.put("Manhole",manholeListPt);
							ptPhysicalListResult.put("Junction_Handhole", junctionHandholeListPt);
							ptPhysicalListResult.put("Handhole", handholeListPt);
							ptPhysicalListResult.put("fiber", fiberListPt);
							ptPhysicalListResult.put("Distribution_Board", distribBoardListPt);
							ptPhysicalListResult.put("Trench", trenchListPt);
							ptPhysicalListResult.put("duct", ductListPt);
							ptPhysicalLayerList.put("ptList" + i, ptPhysicalListResult);
							ptPhysicalDataResult.put("trench_Auxiliary", trenchAuxiliary_DataPt);
							ptPhysicalDataResult.put("strands_Auxiliaries", strandsAuxiliariesPt);
							ptPhysicalDataResult.put("fiber_Strands", fiberStrandsPt);
							ptPhysicalDataResult.put("tubes_Auxiliaries", tubesAuxiliariesPt);
							ptPhysicalDataResult.put("fiber_Tubes", fiberTubesPt);
							ptPhysicalDataResult.put("fiber_Auxiliary", fiberAuxiliary_DataPt);
							ptPhysicalDataResult.put("ductAuxiliary", ductAuxiliary_DataPt);
							ptPhysicalLayerData.put("ptData" + i , ptPhysicalDataResult);
						}
						model.addAttribute("rowData",  mapper.writeValueAsString(rowData));
						model.addAttribute("getRelatedPoints", showPointsType);
						model.addAttribute("noOfPoints", noOfPoints);
						model.addAttribute("closestDisRange", closestDisRange);
					    model.addAttribute("ptList", mapper.writeValueAsString(ptPhysicalLayerList));
						model.addAttribute("ptData",mapper.writeValueAsString(ptPhysicalLayerData));
					 }	
					} else if (StringUtils.equalsIgnoreCase(request.getParameter("Checked"), "connected")) {
						System.out.println("it is inside ");
						filterFlag = 2;
						checkedOption = request.getParameter("Checked");
						String siteId = request.getParameter("siteId").split(":")[0];
						String showPointsType = request.getParameter("getRelatedPoints");

						// String headerSearchlong = request.getParameter("headerSearchLong");
						// String headerSearchlat = request.getParameter("headerSearchLat");
						System.out.println("siteId " + request.getParameter("siteId"));
						// System.out.println("selectHeaderSearch
						// "+request.getParameter("selectHeaderSearch"));
						// fiberList = session.createSQLQuery("SELECT distinct
						// A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT,
						// A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT
						// COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS
						// Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE
						// C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS
						// Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL
						// FROM FIBER_CABLES A LEFT JOIN FIBER_AUXILIARY_POINTS D ON
						// A.FIBER_CABLE_ID=D.FIBER_CABLE_ID WHERE A.SOURCE_WARE_ID LIKE '%"+siteId+"%'
						// OR A.DESTINATION_WARE_ID LIKE '%"+siteId+"%' OR D.AUXILIARY_POINT_ID LIKE
						// '%"+siteId+"%' ").list();
						fiberList = session.createSQLQuery(
								"SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A "
										+ "LEFT  JOIN FIBER_AUXILIARY_POINTS D ON A.FIBER_CABLE_ID=D.FIBER_CABLE_ID "
										+ "LEFT  JOIN FIBER_TUBES B ON A.FIBER_CABLE_ID=B.FIBER_CABLE_ID "
										+ "LEFT  JOIN FIBER_STRANDS C ON A.FIBER_CABLE_ID=C.FIBER_CABLE_ID "
										+ "LEFT  JOIN TUBE_AUXILIARY_POINTS E ON A.FIBER_CABLE_ID=E.FIBER_CABLE_ID "
										+ "LEFT  JOIN STRAND_AUXILIARY_POINTS F ON A.FIBER_CABLE_ID=F.FIBER_CABLE_ID "
										+ "WHERE A.SOURCE_WARE_ID LIKE '%" + siteId
										+ "%' OR A.DESTINATION_WARE_ID LIKE '%" + siteId
										+ "%' OR D.AUXILIARY_POINT_ID LIKE '%" + siteId
										+ "%' OR B.SOURCE_WARE_ID LIKE '%" + siteId
										+ "%' OR B.DESTINATION_WARE_ID LIKE '%" + siteId
										+ "%' OR C.SOURCE_WARE_ID LIKE '%" + siteId
										+ "%' OR C.DESTINATION_WARE_ID LIKE '%" + siteId
										+ "%' OR E.AUXILIARY_POINT_ID LIKE '%" + siteId
										+ "%' OR F.AUXILIARY_POINT_ID LIKE '%" + siteId + "%' ")
								.list();
						System.out.println("fiberList " + mapper.writeValueAsString(fiberList));

						// fiberAuxiliary_Data = session.createSQLQuery("SELECT
						// B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID
						// FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE
						// A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND AUXILIARY_POINT_ID LIKE '%"+siteId+"%'
						// ORDER BY B.SEQ_SORTING ASC ").list();
						Query fiberAuxiliaryQuery = session.createSQLQuery(
								"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID IN (:param1) ORDER BY B.SEQ_SORTING ASC");
						fiberAuxiliaryQuery.setParameterList("param1",
								(findListId(fiberList, "FiberCable")).length > 0 ? findListId(fiberList, "FiberCable")
										: new String[] { "" });
						fiberAuxiliary_Data = fiberAuxiliaryQuery.list();

						// fiberTubes = session.createSQLQuery("SELECT DISTINCT
						// b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT
						// COUNT(*) FROM FIBER_STRANDS C WHERE
						// C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME FROM FIBER_TUBES
						// b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID AND b.SOURCE_WARE_ID
						// LIKE '%"+siteId+"%' OR b.DESTINATION_WARE_ID LIKE '%"+siteId+"%' ").list();
						fiberTubes = session.createSQLQuery(
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
								.list();
						// System.out.println("fiberTubes "+mapper.writeValueAsString(fiberTubes));

						Query tubesAuxiliariesQuery = session.createSQLQuery(
								"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON  b.TUBE_ID=c.TUBE_ID LEFT JOIN FIBER_CABLES a ON a.FIBER_CABLE_ID=b.FIBER_CABLE_ID WHERE c.TUBE_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC");
						tubesAuxiliariesQuery.setParameterList("param1",
								(findListId(fiberTubes, "Tube")).length > 0 ? findListId(fiberTubes, "Tube")
										: new String[] { "" });
						tubesAuxiliaries = tubesAuxiliariesQuery.list();
						// System.out.println("tubesAuxiliaries
						// "+mapper.writeValueAsString(tubesAuxiliaries));

						// fiberStrands = session.createSQLQuery("SELECT DISTINCT
						// b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME
						// FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID
						// AND b.SOURCE_WARE_ID LIKE '%"+siteId+"%' OR b.DESTINATION_WARE_ID LIKE
						// '%"+siteId+"%' ").list();
						fiberStrands = session.createSQLQuery(
								"SELECT DISTINCT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.DRAWING_TYPE,b.STRAND_NAME,STRAND_NUMBER,STRAND_COLOR FROM FIBER_STRANDS b "
										+ "LEFT  JOIN STRAND_AUXILIARY_POINTS a ON b.STRAND_ID=a.STRAND_ID "
										+ "WHERE b.SOURCE_WARE_ID LIKE '%" + siteId
										+ "%' OR b.DESTINATION_WARE_ID LIKE '%" + siteId
										+ "%' OR a.AUXILIARY_POINT_ID LIKE '%" + siteId + "%' ")
								.list();
						System.out.println("fiberStrands " + mapper.writeValueAsString(fiberStrands));
						Query strandsAuxiliariesQuery = session.createSQLQuery(
								"SELECT DISTINCT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID AND c.STRAND_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC ");
						strandsAuxiliariesQuery.setParameterList("param1",
								(findListId(fiberStrands, "Strand")).length > 0 ? findListId(fiberStrands, "Strand")
										: new String[] { "" });
						strandsAuxiliaries = strandsAuxiliariesQuery.list();

						distribBoardList = session.createSQLQuery(
								" SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B  ON B.DB_ID = A.DB_ID  where A.WAREHOUSE LIKE '%"
										+ siteId + "%' OR B.FP_LOCATION LIKE '%" + siteId + "%' ")
								.list();
						int distribBoardListSize = distribBoardList.size();
						System.out.println("distribBoardList 1 is " + mapper.writeValueAsString(distribBoardList));
						List<Object[]> nearstPoints = new ArrayList<Object[]>();
						// nearstPoints.addAll(manholeList);
						// nearstPoints.addAll(handholeList);
						nearstPoints.addAll(distribBoardList);

						String[] idsArray = (findListId(nearstPoints, "all")).length > 0
								? findListId(nearstPoints, "all")
								: new String[] { "" };

						if (showPointsType.equals("1")) {
							if (distribBoardList.size() > 0) {
								Query dbData = session.createSQLQuery(
										" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID,A.DB_LONGITUDE as DB_LONGITUDE,A.DB_LATITUDE as DB_LATITUDE,A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.FIBER_CABLE_ID IN (:param1)   "
												+ " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ "UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) ) where DB_ID NOT IN (:param2)  ");
								dbData.setParameterList("param1",
										(findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" });
								dbData.setParameterList("param2", idsArray);
								distribBoardList.addAll(dbData.list());
							} else {
								Query dbData = session.createSQLQuery(
										" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID,A.DB_LONGITUDE as DB_LONGITUDE,A.DB_LATITUDE as DB_LATITUDE,A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.FIBER_CABLE_ID IN (:param1)   "
												+ " UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ "UNION "
												+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) ) ");
								dbData.setParameterList("param1",
										(findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" });
								distribBoardList = dbData.list();
							}
							if (manholeList.size() > 0) {
								Query manholeData = session.createSQLQuery(
										" SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name ,A.LONGITUDE as LONGITUDE,A.LATITUDE as LATITUDE ,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.FIBER_CABLE_ID IN (:param1) "
												+ " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1)  ) where manhole_id NOT IN (:param2) ");
								manholeData.setParameterList("param1",
										(findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" });
								manholeData.setParameterList("param2", idsArray);
								manholeList.addAll(manholeData.list());
							} else {

								Query manholeData = session.createSQLQuery(
										" SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name ,A.LONGITUDE as LONGITUDE,A.LATITUDE as LATITUDE ,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.FIBER_CABLE_ID IN (:param1) "
												+ " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ " UNION "
												+ " SELECT DISTINCT A.manhole_id,A.manhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1)  )  ");
								manholeData.setParameterList("param1",
										(findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" });
								manholeList = manholeData.list();
							}
							if (handholeList.size() > 0) {
								Query handholeData = session.createSQLQuery(
										" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name,A.LONGITUDE as LONGITUDE ,A.LATITUDE as LATITUDE,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.FIBER_CABLE_ID IN (:param1) "
												+ " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ "UNION"
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) ) where handhole_id NOT IN (:param2)  ");
								handholeData.setParameterList("param1",
										(findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" });
								handholeData.setParameterList("param2", idsArray);
								handholeList.addAll(handholeData.list());
							} else {
								Query handholeData = session.createSQLQuery(
										" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name,A.LONGITUDE as LONGITUDE ,A.LATITUDE as LATITUDE,A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.FIBER_CABLE_ID IN (:param1) "
												+ " UNION "
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) "
												+ "UNION"
												+ " SELECT DISTINCT A.handhole_id,A.handhole_name,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) )  ");
								handholeData.setParameterList("param1",
										(findListId(fiberList, "FiberCable")).length > 0
												? findListId(fiberList, "FiberCable")
												: new String[] { "" });
								handholeList = handholeData.list();
							}
						}

						String[] allManIdsPointsArray = (findListId(manholeList, "all")).length > 0
								? findListId(manholeList, "all")
								: new String[] { "A" };
						query = session.createSQLQuery(
								"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id where B.manhole_id in (:param) ");
						junctionManholeList = query.setParameterList("param", allManIdsPointsArray).list();

						String[] allHandIdsPointsArray = (findListId(handholeList, "all")).length > 0
								? findListId(handholeList, "all")
								: new String[] { "A" };
						query = session.createSQLQuery(
								"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id where b.handhole_id in(:param) ");

						junctionHandholeList = query.setParameterList("param", allHandIdsPointsArray).list();

						model.addAttribute("siteId", request.getParameter("siteId"));
						model.addAttribute("connectedSearchLong", request.getParameter("connectedSearchLong"));
						model.addAttribute("connectedSearchLat", request.getParameter("connectedSearchLat"));
						model.addAttribute("selectConnectedSearch", request.getParameter("selectConnectedSearch"));
						model.addAttribute("connectedViewOnMap", request.getParameter("connectedViewOnMap"));
						model.addAttribute("distribBoardListSize", distribBoardListSize);
						model.addAttribute("getRelatedPoints", showPointsType);

						System.out.println("distribBoardList 2 is " + mapper.writeValueAsString(distribBoardList));
					} else {

						filterFlag = 0;

						projectList = session.createSQLQuery(
								"SELECT DISTINCT PROJECT_ID,PROJECT_NAME, (select count(*) FROM HANDHOLE a WHERE a.PROJECT_ID = b.PROJECT_ID)  FROM PROJECT b ")
								.list();

						manholeList = session.createSQLQuery(
								"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),CITY FROM MANHOLE  ")
								.list();

						handholeList = session.createSQLQuery(
								"SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),CITY FROM HANDHOLE")
								.list();

						fiberList = session.createSQLQuery(
								"SELECT SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID),(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID),FIBER_CABLE_NAME,PROJECT_ID,SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_TUBES,NUMBER_OF_STRANDS,LENGTH,DRAWING_TYPE,FIBER_NETWORK_LEVEL,FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR FROM FIBER_CABLES A")
								.list();

						fiberAuxiliary_Data = session.createSQLQuery(
								"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID ORDER BY B.SEQ_SORTING ASC")
								.list();

						fiberTubes = session.createSQLQuery(
								"SELECT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER, b.TUBE_COLOR FROM FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID")
								.list();

						tubesAuxiliaries = session.createSQLQuery(
								"SELECT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c,FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.TUBE_ID=c.TUBE_ID ORDER BY c.SEQ_SORTING ASC")
								.list();

						fiberStrands = session.createSQLQuery(
								"SELECT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID")
								.list();

						strandsAuxiliaries = session.createSQLQuery(
								"SELECT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,C.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID ORDER BY c.SEQ_SORTING ASC ")
								.list();

						distribBoardList = session.createSQLQuery(
								"SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY,DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD")
								.list();

						trenchList = session.createSQLQuery(
								"SELECT TRENCH_ID,TRENCH_NAME,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LATITUDE,SOURCE_LONGITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY,NUM_DUCTS,MAX_CAPACITY,LENGTH,PROJECT_ID,DRAWING_TYPE  FROM TRENCH")
								.list();

						trenchAuxiliary_Data = session.createSQLQuery(
								"SELECT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.TRENCH_ID,B.DISTANCE_FROM_SOURCE,B.SEQ_SORTING,B.AUXILIARY_ID  FROM TRENCH A,TRENCH_AUXILIARY_POINTS B WHERE A.TRENCH_ID=B.TRENCH_ID ORDER BY B.SEQ_SORTING ASC")
								.list();

						junctionManholeList = session.createSQLQuery(
								"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id")
								.list();

						junctionHandholeList = session.createSQLQuery(
								"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id")
								.list();

						ductList = session.createSQLQuery(
								"SELECT B.DUCT_ID,B.DUCT_NAME,B.SOURCE_WARE_ID,B.SOURCE_ID,B.SOURCE_NAME,B.DESTINATION_WARE_ID,B.DESTINATION_ID,B.DESTINATION_NAME,B.SOURCE_LATITUDE,B.SOURCE_LONGITUDE,B.DESTINATION_LONGITUDE,B.DESTINATION_LATITUDE,B.SOURCE_CITY,B.DESTINATION_CITY,B.NUM_FIBERCABLES,B.NUM_FIBERTUBES,B.NUM_FIBERSTRANDS,B.LENGTH,B.TRENCH_ID,B.DRAWING_TYPE FROM DUCTS B,TRENCH A WHERE B.TRENCH_ID=A.TRENCH_ID")
								.list();

						ductAuxiliary_Data = session.createSQLQuery(
								"SELECT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.DUCT_ID,B.DISTANCE_FROM_SOURCE,B.SEQ_SORTING,B.AUXILIARY_ID FROM DUCTS A,DUCT_AUXILIARY_POINTS B WHERE A.DUCT_ID=B.DUCT_ID ORDER BY B.SEQ_SORTING ASC ")
								.list();
						
						NodeActiveList =  session.createSQLQuery(
								"SELECT DISTINCT NODE_PK,NODE_NAME,NODE_PK || ':'  || NODE_NAME,DOMAIN,SITE_ID,LONGITUDE,LATITUDE,NODE_ID,SUB_DOMAIN_TYPE FROM NODE_ACTIVE ").list();
					
						
					}

					LinkedHashMap<String, List<?>> physicalLayerData = new LinkedHashMap<String, List<?>>();// linkedHashmap
					// instead of
					// HashMap to return
					// values in
					// sequential order
					LinkedHashMap<String, List<?>> physicalLayerList = new LinkedHashMap<String, List<?>>();// linkedHashmap
																											// instead
																											// of
																											// HashMap
																											// to return
																											// values
				for (Object[] obj: manholeListPt) {
					if(!(manholeList.contains(obj))) {
						manholeList.add(obj);
					}
				}	
				for (Object[] obj: handholeListPt ) {
					if(!(handholeList.contains(obj))) {
						handholeList.add(obj);
					}
				}
				for (Object[] obj: distribBoardListPt ) {
					if(!(distribBoardList.contains(obj))) {
						distribBoardList.add(obj);
					}
				}
				for (Object[] obj: fiberListPt ) {
					if(!(fiberList.contains(obj))) {
						fiberList .add(obj);
					}
				}
				for (Object[] obj: fiberTubesPt ) {
					if(!(fiberTubes.contains(obj))) {
						fiberTubes.add(obj);
					}
				}
				for (Object[] obj: tubesAuxiliariesPt ) {
					if(!(tubesAuxiliaries.contains(obj))) {
						tubesAuxiliaries.add(obj);
					}
				}
				for (Object[] obj: fiberStrandsPt) {
					if(!(fiberStrands.contains(obj))) {
						fiberStrands.add(obj);
					}
				}
				for (Object[] obj: strandsAuxiliariesPt) {
					if(!(strandsAuxiliaries.contains(obj))) {
						strandsAuxiliaries.add(obj);
					}
				}
				for (Object[] obj: junctionManholeListPt) {
					if(!(junctionManholeList.contains(obj))) {
						junctionManholeList.add(obj);
					}
				}
				for (Object[] obj: junctionHandholeListPt) {
					if(!(junctionHandholeList.contains(obj))) {
						junctionHandholeList.add(obj);
					}
				}
				for (Object[] obj: fiberAuxiliary_DataPt) {
					if(!(fiberAuxiliary_Data.contains(obj))) {
						fiberAuxiliary_Data.add(obj);
					}
				}
				for (Object[] obj: trenchListPt ) {
					if(!(trenchList.contains(obj))) {
						trenchList.add(obj);
					}
				}
				for (Object[] obj: trenchAuxiliary_DataPt) {
					if(!(trenchAuxiliary_Data.contains(obj))) {
						trenchAuxiliary_Data.add(obj);
					}
				}
				for (Object[] obj: ductListPt) {
					if(!(ductList.contains(obj))) {
						ductList.add(obj);
					}
				}
				for (Object[] obj: ductAuxiliary_DataPt ) {
					if(!(ductAuxiliary_Data.contains(obj))) {
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
					physicalLayerList.put("Node", EntrepriseList);
					physicalLayerList.put("Transmission", TransmissionList);
					physicalLayerList.put("Core", CoreList);
					physicalLayerList.put("Access", AccessList);
					physicalLayerList.put("NodeActiveList", NodeActiveList);
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

//			System.out.println("hash_mapFields value is :"+ mapper.writeValueAsString(hashMapList));
//			rtn.put("hashMap",hashMap);
//			rtn.put("hashMapList",hashMapList);

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in NetworkPhysicalLayer due to \n " + exceptionAsString);
					logger.info("Error in NetworkPhysicalLayer due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return "Network/NetworkPhysicalLayer";
		}
	}

	// Project details
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findProjectDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findProjectDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			String selectedProjectIdContext = request.getParameter("selectedProjectIdContext");
			try {
				Object[] ProjectDetails = (Object[]) session
						.createSQLQuery("SELECT DISTINCT PROJECT_ID,PROJECT_NAME FROM PROJECT WHERE PROJECT_ID='"
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findManholeDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findManholeDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			String selectedManIdContext = request.getParameter("selectedManIdContext");
			try {
				Object[] ManholeDetails = (Object[]) session.createSQLQuery(
						"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,MANHOLE_MODEL,CITY,DM_NAME,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM') FROM MANHOLE WHERE MANHOLE_ID='"
								+ selectedManIdContext + "' ")
						.uniqueResult();

				rtn.put("ManholeDetails", ManholeDetails);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findManholeDetails due to \n " + exceptionAsString);
				logger.info("Error in findManholeDetails due to \n " + exceptionAsString);
				rtn.put("ManholeDetails", null);

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
	@RequestMapping(value = "/findHandholeDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findHandholeDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			String selectedHandIdContext = request.getParameter("selectedHandIdContext");
			try {
				Object[] HandholeDetails = (Object[]) session.createSQLQuery(
						"SELECT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,HANDHOLE_MODEL,CITY,DM_NAME,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM') FROM HANDHOLE WHERE HANDHOLE_ID='"
								+ selectedHandIdContext + "' ")
						.uniqueResult();

				rtn.put("HandholeDetails", HandholeDetails);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findHandholeDetails due to \n " + exceptionAsString);
				logger.info("Error in findHandholeDetails due to \n " + exceptionAsString);
				rtn.put("HandholeDetails", null);

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
	@RequestMapping(value = "/findDistBoardDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findDistBoardDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedDistBoardContext = request.getParameter("selectedDistBoardContext");
			try {
				List<Object[]> DistBoardDetails = session.createSQLQuery(
						"SELECT DISTINCT A.DB_NAME,A.SITE,A.MAX_CAPACITY,A.DB_LONGITUDE,A.DB_LATITUDE,A.NUM_ROWS,A.NUM_COLUMNS,(SELECT COUNT(B.FP_STATUS) FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.FP_STATUS='Active' AND B.DB_ID='"
								+ selectedDistBoardContext
								+ "'),(SELECT COUNT(B.BP_STATUS) FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.BP_STATUS='Active' AND B.DB_ID='"
								+ selectedDistBoardContext
								+ "'),A.CITY, A.SITE_NAME,A.WAREHOUSE,TO_CHAR(A.CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(A.LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM') FROM DISTRIBUTION_BOARD A WHERE A.DB_ID='"
								+ selectedDistBoardContext + "' ")
						.list();

				List<Object[]> DistBoardMappingPts = session.createSQLQuery(
						"SELECT DISTINCT ROW_COL_INDEX,ROW_NUMBER,COLUMN_NUMBER,DB_PORT_ID,FP_STATUS,FP_LOCATION_TYPE,FP_LOCATION_ID,FP_LOCATION_NAME,FP_LOCATION,FP_EQUIPMENT_TYPE,FP_EQUIPMENT,FP_EQUIPMENT_ID,FP_EQUIPMENT_NAME,FP_ADDRESS,BP_STATUS,BP_STRAND_ID,BP_STRAND_NAME,BP_TUBE_ID,BP_TUBE_NAME,BP_FIBER_ID,BP_FIBER_NAME,FP_STRAND_ID,FP_STRAND_NAME,FP_TUBE_ID,FP_TUBE_NAME,FP_FIBER_ID,FP_FIBER_NAME,BP_LOCATION_TYPE,BP_LOCATION_ID,BP_LOCATION_NAME,BP_LOCATION,BP_EQUIPMENT_TYPE,BP_EQUIPMENT,BP_EQUIPMENT_ID,BP_EQUIPMENT_NAME,BP_ADDRESS,FP_STRAND_Nb,FP_TUBE_Nb,BP_STRAND_Nb,BP_TUBE_Nb,FP_STRAND_COLOR,FP_TUBE_COLOR,BP_STRAND_COLOR,BP_TUBE_COLOR,FP_JUNCTION_ID,FP_JUNCTION_NAME,BP_JUNCTION_ID,BP_JUNCTION_NAME FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.DB_ID='"
								+ selectedDistBoardContext + "' ")
						.list();
				
				List<Object[]> DBnetLevel = session.createSQLQuery(
						"SELECT DISTINCT DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD  WHERE DB_ID='"
								+ selectedDistBoardContext + "' ")
						.list();

				rtn.put("DistBoardDetails", DistBoardDetails);
				rtn.put("DistBoardMappingPts", DistBoardMappingPts);
				rtn.put("DBnetLevel", DBnetLevel);
				
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findDistBoardDetails due to \n " + exceptionAsString);
				logger.info("Error in findDistBoardDetails due to \n " + exceptionAsString);
				rtn.put("DistBoardDetails", null);
				rtn.put("DistBoardMappingPts", null);
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
	@RequestMapping(value = "/findDistBoardMappingDetails", method = RequestMethod.GET)
	@ResponseBody

	public Map<String, Object> findDistBoardMappingDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String selectedDistBoardContext = request.getParameter("selectedDistBoardContext");

				List<Object[]> DistBoardMappingPts = session.createSQLQuery(
						"SELECT DISTINCT ROW_NUMBER,COLUMN_NUMBER,FP_EQUIPMENT,BP_STRAND_NAME,DB_PORT_ID,(SELECT A.NUM_ROWS FROM DISTRIBUTION_BOARD A WHERE A.DB_ID='"
								+ selectedDistBoardContext
								+ "'),(SELECT A.NUM_COLUMNS FROM DISTRIBUTION_BOARD A WHERE A.DB_ID='"
								+ selectedDistBoardContext
								+ "'),BP_STATUS,BP_STRAND_ID,BP_TUBE_ID,BP_TUBE_NAME,BP_FIBER_ID,BP_FIBER_NAME,FP_STATUS,FP_LOCATION_TYPE,FP_LOCATION_ID,FP_LOCATION_NAME,FP_EQUIPMENT_TYPE,FP_EQUIPMENT_ID,FP_EQUIPMENT_NAME,ROW_COL_INDEX,FP_STRAND_ID,FP_STRAND_NAME,FP_TUBE_ID,FP_TUBE_NAME,FP_FIBER_ID,FP_FIBER_NAME,BP_LOCATION_TYPE,BP_LOCATION_ID,BP_LOCATION_NAME,BP_LOCATION,BP_EQUIPMENT_TYPE,BP_EQUIPMENT,BP_EQUIPMENT_ID,BP_EQUIPMENT_NAME,BP_ADDRESS,FP_STRAND_Nb,FP_TUBE_Nb,BP_STRAND_Nb,BP_TUBE_Nb FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.DB_ID='"
								+ selectedDistBoardContext + "' ")
						.list();

				if (DistBoardMappingPts.size() > 0) {
					rtn.put("DistBoardMappingPts", DistBoardMappingPts);
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findDistBoardMappingDetails due to \n " + exceptionAsString);
				logger.info("Error in findDistBoardMappingDetails due to \n " + exceptionAsString);
				rtn.put("DistBoardMappingPts", null);
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
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					listTrench = session.createSQLQuery(
							"SELECT TRENCH_ID,TRENCH_NAME,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY, MAX_CAPACITY,NUM_DUCTS,LENGTH,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),CREATED_BY,LAST_MODIFIED_BY,TOTAL_DRIVING_DISTANCE, TOTAL_GEO_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE,DRAWING_TYPE FROM TRENCH WHERE TRENCH_ID='"
									+ selectedTrench + "' ")
							.list();

					List<Object[]> trenchAuxiliary = session.createSQLQuery(
							"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,TRENCH_ID,SEQ_SORTING,DRIVING_DISTANCE,GEO_DISTANCE,AUXILIARY_ID FROM TRENCH_AUXILIARY_POINTS WHERE TRENCH_ID='"
									+ selectedTrench + "' ORDER BY SEQ_SORTING ASC ")
							.list();

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

							finalList.addAll(session.createSQLQuery(querySearch).list());
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
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					listDuct = session.createSQLQuery(
							"SELECT DUCT_ID,DUCT_NAME,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY, NUM_FIBERCABLES,NUM_FIBERTUBES,NUM_FIBERSTRANDS,LENGTH,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),CREATED_BY,LAST_MODIFIED_BY,TOTAL_DRIVING_DISTANCE, TOTAL_GEO_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE,DRAWING_TYPE FROM DUCTS WHERE DUCT_ID='"
									+ selectedDuct + "' ")
							.list();

					List<Object[]> ductAuxiliary = session.createSQLQuery(
							"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,DUCT_ID,SEQ_SORTING,DRIVING_DISTANCE,GEO_DISTANCE,AUXILIARY_ID FROM DUCT_AUXILIARY_POINTS WHERE DUCT_ID='"
									+ selectedDuct + "' ORDER BY SEQ_SORTING ASC ")
							.list();
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

							finalList.addAll(session.createSQLQuery(querySearch).list());
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
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedFiberContext = request.getParameter("ID");
			try {

				List<Object[]> fiberAuxiliaryData = session.createSQLQuery(
						"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.SEQ_SORTING, B.DRIVING_DISTANCE, B.GEO_DISTANCE, B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID ='"
								+ selectedFiberContext + "' ORDER BY B.SEQ_SORTING ASC")
						.list();

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

						finalList.addAll(session.createSQLQuery(querySearch).list());
					}
				}
				if (finalList != null) {
					rtn.put("auxPtSearch", finalList);
				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findFiberAuxDetails due to \n " + exceptionAsString);
				logger.info("Error in findFiberAuxDetails due to \n " + exceptionAsString);
				e.printStackTrace();
				rtn.put("fiberAuxiliaryData", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/findClient&Site", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findClientSite(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> rtn = new LinkedHashMap<>();
		String fiberID = request.getParameter("selectedFiberContext").toString();
		// List<Object[]> SiteData=null;
		System.out.println("the fiber id is " + fiberID);
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				/*
				 * ArrayList<Object> ClientData = new ArrayList<>(); List<Object[]>
				 * StrandsID=session.
				 * createSQLQuery("SELECT STRAND_ID FROM FIBER_STRANDS WHERE FIBER_CABLE_ID='" +
				 * fiberID+ "' ").list();
				 * System.out.println("the strands from the controller is  " +
				 * mapper.writeValueAsString(StrandsID)); int i=0; int j=0; int k=0; int e=0;
				 * for(i=0; i< StrandsID.size(); i++) { System.out.println("std ids i  " +
				 * StrandsID.get(i)); query=session.
				 * createSQLQuery("SELECT DB_ID FROM DISTRIBUTION_BOARD_MAPPING WHERE BP_STATUS= 'Active' AND FP_STATUS= 'Active' AND BP_STRAND_ID = '"
				 * + StrandsID.get(i)+"' OR FP_STRAND_ID= '"+ StrandsID.get(i)+"'");
				 * System.out.println("the db is " +query.list()); List Clt =query.list(); if
				 * (!Clt.isEmpty()) { for (j=0; j< Clt.size(); j++) {
				 * ClientData.add(Clt.get(j)); } } }
				 */
				int i = 0;
				int j = 0;
				int k = 0;
				int e = 0;
				int f = 0;

				String CableName = session
						.createSQLQuery(
								"SELECT DISTINCT FIBER_CABLE_NAME FROM FIBER_CABLES WHERE FIBER_CABLE_ID= '" + fiberID + "'")
						.uniqueResult().toString();
				System.out.println("the cable name is " + CableName);

				List<Object> ClientData = new ArrayList<>();
				List<Object> SiteData = new ArrayList<>();
				List<Object> CltLongLat = new ArrayList<>();
				List<Object> SiteLongLat = new ArrayList<>();

				ArrayList<Object[]> BackLocations = new ArrayList<>();
				ArrayList<Object[]> FrontLocations = new ArrayList<>();
				List<Object[]> DbIdBack = session
						.createSQLQuery("SELECT DISTINCT DB_PORT_ID FROM DISTRIBUTION_BOARD_MAPPING WHERE BP_FIBER_ID= '"
								+ fiberID + "' AND BP_STATUS= 'Active'")
						.list();
				//System.out.println("the db ports back are  " + mapper.writeValueAsString(DbIdBack));
				List<Object[]> DbIdFront = session
						.createSQLQuery("SELECT DISTINCT DB_PORT_ID FROM DISTRIBUTION_BOARD_MAPPING WHERE FP_FIBER_ID='"
								+ fiberID + "' AND FP_STATUS= 'Active'")
						.list();
			//	System.out.println("the db ports front are  " + mapper.writeValueAsString(DbIdFront));

				ArrayList<Object> FinalResultClt = new ArrayList<>();
				ArrayList<Object> FinalResultSite = new ArrayList<>();
				ArrayList<Object> FinalCltLongLat = new ArrayList<>();
				ArrayList<Object> FinalSiteLongLat = new ArrayList<>();

			//	 System.out.println("===> final site data BEFORE <===" + FinalResultSite);
				 //System.out.println("===> final site data BEFORE <===" + FinalResultClt);

				String BackLocId = "";
				for (i = 0; i < DbIdBack.size(); i++) {
					// System.out.println("back ids are " + DbIdBack.get(i));
					query = session.createSQLQuery(
							"SELECT DISTINCT BP_LOCATION_TYPE,BP_LOCATION, BP_LOCATION_NAME,BP_LOCATION_ID FROM DISTRIBUTION_BOARD_MAPPING WHERE DB_PORT_ID='"
									+ DbIdBack.get(i) + "'");
					BackLocations.addAll(query.list());
					BackLocId = BackLocations.get(0)[1].toString();
				//	 System.out.println("the backs are===> " +mapper.writeValueAsString(BackLocations));
				//	 System.out.println("BackLocId ===> " + BackLocId);
					for (j = 0; j < BackLocations.size(); j++) {
				//		System.out.println("for on backs");
				//		 System.out.println("back loactions are " + mapper.writeValueAsString(BackLocations.get(j)));
						if (BackLocations.get(j)[0].equals("Site")){
							boolean valueExists = false;
							for (Object row : FinalResultSite) {
								ArrayList<Object> innerList = (ArrayList<Object>) FinalResultSite.get(f);
								Object secondColumn = innerList.get(1);
								// System.out.println("BACK SITE " + BackLocations.get(j)[3]);
								if (secondColumn.equals(BackLocations.get(j)[3])) {
								//	System.out.println("exist");
									valueExists = true;
									break;
								}
								//System.out.println("not exist");
							}
							if (!valueExists) {
							//	System.out.println("not exist if");
								List<Object[]> SiteIdBack = session.createSQLQuery(
										"SELECT DISTINCT SITE_ID,WARE_NAME,LONGITUDE,LATITUDE FROM WAREHOUSE WHERE WARE_ID= '"
												+ BackLocations.get(j)[1] + "' ")
										.list();
							//	 System.out.println("the site id back is " + mapper.writeValueAsString(SiteIdBack));
							//	 System.out.println("BackLocations.get(j)[1] is " + mapper.writeValueAsString(BackLocations.get(j)[1]));
							//	 System.out.println("SiteIdBack.get(0)[0] is " + mapper.writeValueAsString(SiteIdBack.get(0)[0]));
							//	 System.out.println("SiteIdBack.get(0)[1] is " + mapper.writeValueAsString(SiteIdBack.get(0)[1]));
								 
								SiteData.add(BackLocations.get(j)[1]);
								SiteData.add(SiteIdBack.get(0)[0]);
								SiteData.add(SiteIdBack.get(0)[1]);
								FinalResultSite.add(SiteData);
								SiteLongLat.add(SiteIdBack.get(0)[2].toString());
								SiteLongLat.add(SiteIdBack.get(0)[3].toString());
								FinalSiteLongLat.add(SiteLongLat);
							}
							
						}
						
						// System.out.println("===> final site data AFTER BACK SITE <===" +
						//		 SiteData);
						
							SiteData = new ArrayList<>();
							SiteLongLat = new ArrayList<>();
						// System.out.println("===> final site data AFTER BACK SITE <===" +
						// FinalResultClt);
						if (BackLocations.get(j)[0].equals("Customer")) {
							boolean valueExists = false;
							for (Object row : FinalResultClt) {
								ArrayList<Object> innerList = (ArrayList<Object>) FinalResultClt.get(f);
								Object secondColumn = innerList.get(1);
								// System.out.println("BACK CLIENT " + BackLocations.get(j)[3]);
								if (secondColumn.equals(BackLocations.get(j)[3])) {
									valueExists = true;
									break;
								}
							}
							if (!valueExists) {
								List<Object[]> MobileNbrBack = session.createSQLQuery(
										"SELECT DISTINCT MOBILE_NUMBER,LONGITUDE,LATITUDE FROM CLIENTS WHERE CLIENT_ID= '"
												+ BackLocId + "' ")
										.list();
								// System.out.println("the mobile nbr back is " +
								// mapper.writeValueAsString(MobileNbrBack));
								ClientData.add(BackLocations.get(j)[1]);
								ClientData.add(MobileNbrBack.get(0)[0]);
								FinalResultClt.add(ClientData);
								CltLongLat.add(MobileNbrBack.get(0)[1].toString());
								CltLongLat.add(MobileNbrBack.get(0)[2].toString());
								FinalCltLongLat.add(CltLongLat);
							}
						}
						ClientData = new ArrayList<>();
						CltLongLat = new ArrayList<>();
					}
				}
				// System.out.println("===> final site data AFTER BACK CLIENT <===" +
				// FinalResultSite);
				// System.out.println("===> final site data AFTER BACK CLIENT <===" +
				// FinalResultClt);
			//	System.out.println("===> final site data AFTER BACK  <===" + FinalResultSite);
				ClientData = new ArrayList<>();
				SiteData = new ArrayList<>();
				CltLongLat = new ArrayList<>();
				SiteLongLat = new ArrayList<>();
				String FrontLocId = "";
				for (k = 0; k < DbIdFront.size(); k++) {
					// System.out.println("front ids are " + DbIdFront.get(k));
					query = session.createSQLQuery(
							"SELECT DISTINCT FP_LOCATION_TYPE,FP_LOCATION, FP_LOCATION_NAME,FP_LOCATION_ID FROM DISTRIBUTION_BOARD_MAPPING WHERE DB_PORT_ID='"
									+ DbIdFront.get(k) + "'");
					FrontLocations.addAll(query.list());
					FrontLocId = FrontLocations.get(0)[1].toString();
					// System.out.println("the fronts are "
					// +mapper.writeValueAsString(FrontLocations));

					for (e = 0; e < FrontLocations.size(); e++) {
						if (FrontLocations.get(e)[0].equals("Site")) {
							boolean valueExists = false;
							for (Object row : FinalResultSite) {
								ArrayList<Object> innerList = (ArrayList<Object>) FinalResultSite.get(f);
								Object secondColumn = innerList.get(1);
								// System.out.println("FRONT SITE " + FrontLocations.get(e)[3]);
								if (secondColumn.equals(FrontLocations.get(e)[3])) {
									valueExists = true;
								//	System.out.println("existtttt");
									break;
								}
							}
							if (!valueExists) {
							//	System.out.println("dont existtttt");
								List<Object[]> SiteIdFront = session.createSQLQuery(
										"SELECT DISTINCT SITE_ID,WARE_NAME,LONGITUDE,LATITUDE FROM WAREHOUSE WHERE WARE_ID= '"
												+ FrontLocations.get(e)[1] + "' ")
										.list();
							//	System.out.println("the site id front is " + mapper.writeValueAsString(SiteIdFront));
							//	 System.out.println("FrontLocations.get(e)[1] is " + mapper.writeValueAsString(FrontLocations.get(e)[1]));
							//	 System.out.println("SiteIdFront.get(0)[0] is " + mapper.writeValueAsString(SiteIdFront.get(0)[0]));
							//	 System.out.println("SiteIdFront.get(0)[1] is " + mapper.writeValueAsString(SiteIdFront.get(0)[1]));
								 
								SiteData.add(FrontLocations.get(e)[1]);
								SiteData.add(SiteIdFront.get(0)[0]);
								SiteData.add(SiteIdFront.get(0)[1]);
								FinalResultSite.add(SiteData);
								SiteLongLat.add(SiteIdFront.get(0)[2].toString());
								SiteLongLat.add(SiteIdFront.get(0)[3].toString());
								FinalSiteLongLat.add(SiteLongLat);
							}
						}
					//	System.out.println("===> final site data AFTER FRONT SITE <===" + SiteData);							
						SiteData = new ArrayList<>();
						SiteLongLat = new ArrayList<>();
					//	 System.out.println("===> final site data AFTER FRONT SITE <===" +
					//	 FinalResultClt);

						if (FrontLocations.get(e)[0].equals("Customer")) {
							boolean valueExists = false;
							for (Object row : FinalResultClt) {
								ArrayList<Object> innerList = (ArrayList<Object>) FinalResultClt.get(f);
								Object secondColumn = innerList.get(1);
								// System.out.println("FRONT CLIENT " + FrontLocations.get(e)[3]);
								if (secondColumn.equals(FrontLocations.get(e)[3])) {
									valueExists = true;
									// System.out.println("EXIST");
									break;
								}
							}
							if (!valueExists) {
								List<Object[]> MobileNbrFront = session.createSQLQuery(
										"SELECT DISTINCT MOBILE_NUMBER,LONGITUDE,LATITUDE FROM CLIENTS WHERE CLIENT_ID= '"
												+ FrontLocId + "' ")
										.list();
								// System.out.println("the mobile nbr front is " +
								// mapper.writeValueAsString(MobileNbrFront));
								ClientData.add(FrontLocations.get(e)[1]);
								ClientData.add(MobileNbrFront.get(0)[0]);
								FinalResultClt.add(ClientData);
								CltLongLat.add(MobileNbrFront.get(0)[1].toString());
								CltLongLat.add(MobileNbrFront.get(0)[2].toString());
								FinalCltLongLat.add(CltLongLat);
							}
						}
						ClientData = new ArrayList<>();						
						CltLongLat = new ArrayList<>();
					}
				}
				// System.out.println("===> final site data AFTER FRONT CLIENT <===" +
				// FinalResultSite);
				// System.out.println("===> final site data AFTER FRONT CLIENT <===" +
				// FinalResultClt);
				System.out.println("===> final site data AFTER FRONT <===" + FinalResultSite);
				ClientData = new ArrayList<>();
				SiteData = new ArrayList<>();
				CltLongLat = new ArrayList<>();
				SiteLongLat = new ArrayList<>();
				ArrayList<Object[]> Src = new ArrayList<>();
				String SrcId = "";
				String SrcWareId = "";
				query = session.createSQLQuery(
						"SELECT DISTINCT SOURCE_ID,SOURCE_WARE_ID,SOURCE_NAME,SOURCE_LNG,SOURCE_LAT FROM FIBER_CABLES WHERE FIBER_CABLE_ID= '"
								+ fiberID + "' ");
				Src.addAll(query.list());
				SrcId = Src.get(0)[0].toString();
				SrcWareId = Src.get(0)[1].toString();
				// System.out.println("the Source id is " + SrcId);
				// System.out.println("the Source all are " + mapper.writeValueAsString(Src));

				if ((Src.get(0)[0]).toString().startsWith("CLT_")) {
					boolean valueExists = false;
					for (Object row : FinalResultClt) {
						ArrayList<Object> innerList = (ArrayList<Object>) FinalResultClt.get(f);
						Object secondColumn = innerList.get(1);
						// System.out.println("SRC SITE " +Src.get(0)[0]);
						if (secondColumn.equals(Src.get(0)[0])) {
							valueExists = true;
							break;
						}
					}
					if (!valueExists) {
						String MobileNbrSrc = session
								.createSQLQuery("SELECT DISTINCT MOBILE_NUMBER FROM CLIENTS WHERE CLIENT_ID= '" + SrcId + "' ")
								.uniqueResult().toString();
						// System.out.println("the mobile nbr source is " +
						// mapper.writeValueAsString(MobileNbrSrc));
						ClientData.add(Src.get(0)[0]);
						ClientData.add(Src.get(0)[2]);
						ClientData.add(MobileNbrSrc);
						FinalResultClt.add(ClientData);

						CltLongLat.add(Src.get(0)[3].toString());
						CltLongLat.add(Src.get(0)[4].toString());
						FinalCltLongLat.add(CltLongLat);
					}
				}
				ClientData = new ArrayList<>();
				CltLongLat = new ArrayList<>();
				// System.out.println("===> final site data AFTER SRC CLIENT <===" +
				// FinalResultSite);
				// System.out.println("===> final site data AFTER SRC CLIENT <===" +
				// FinalResultClt);

				if ((Src.get(0)[1]).toString().startsWith("WARE_")) {
					boolean valueExists = false;
					for (Object row : FinalResultSite) {
						ArrayList<Object> innerList = (ArrayList<Object>) FinalResultSite.get(f);
						Object secondColumn = innerList.get(1);
						// System.out.println("SRC DEST " +Src.get(0)[0]);
						if (secondColumn.equals(Src.get(0)[0])) {
							valueExists = true;
							break;
						}
					}
					if (!valueExists) {
						List<Object[]> SiteIdSrc = session
								.createSQLQuery(
										"SELECT DISTINCT SITE_ID,WARE_NAME FROM WAREHOUSE WHERE WARE_ID= '" + SrcWareId + "' ")
								.list();
						// System.out.println("the site id source client is " +
						// mapper.writeValueAsString(SiteIdSrc));
						SiteData.add(Src.get(0)[1]);
						SiteData.add(SiteIdSrc.get(0)[0]);
						SiteData.add(SiteIdSrc.get(0)[1]);
						FinalResultSite.add(SiteData);

						SiteLongLat.add(Src.get(0)[3].toString());
						SiteLongLat.add(Src.get(0)[4].toString());
						FinalSiteLongLat.add(SiteLongLat);
					}
				}
				SiteData = new ArrayList<>();
				SiteLongLat = new ArrayList<>();
				// System.out.println("===> final site data AFTER SRC SITE <===" +
				// FinalResultSite);
				// System.out.println("===> final site data AFTER SRC SITE <===" +
				// FinalResultClt);

				if ((Src.get(0)[0]).toString().startsWith("DB_")) {
					List<Object[]> SiteSrcDB = session.createSQLQuery(
							"SELECT DISTINCT SITE,WAREHOUSE,SITE_NAME FROM DISTRIBUTION_BOARD WHERE DB_ID= '" + SrcId + "' ")
							.list();
					// System.out.println("the site DB source is " +
					// mapper.writeValueAsString(SiteSrcDB));
					if (SiteSrcDB.get(0)[0] != null && SiteSrcDB.get(0)[1] != null) {
						SiteSrcDB.get(0)[0].toString();
						SiteSrcDB.get(0)[1].toString();
						if ((SiteSrcDB.get(0)[1]).toString().startsWith("WARE_")) {
							boolean valueExists = false;
							for (Object row : FinalResultSite) {
								ArrayList<Object> innerList = (ArrayList<Object>) FinalResultSite.get(f);
								Object secondColumn = innerList.get(1);
								// System.out.println("SRC DB SITE " +Src.get(0)[0]);
								if (secondColumn.equals(Src.get(0)[0])) {
									valueExists = true;
									break;
								}
							}
							if (!valueExists) {
								String WareSrcDB = session.createSQLQuery(
										"SELECT DISTINCT WARE_NAME FROM WAREHOUSE WHERE WARE_ID= '" + SiteSrcDB.get(0)[1] + "' ")
										.uniqueResult().toString();
								// System.out.println("the ware DB source is " +
								// mapper.writeValueAsString(WareSrcDB));
								SiteData.add(SiteSrcDB.get(0)[1]);
								SiteData.add(SiteSrcDB.get(0)[0]);
								SiteData.add(WareSrcDB);
								FinalResultSite.add(SiteData);

								SiteLongLat.add(Src.get(0)[3].toString());
								SiteLongLat.add(Src.get(0)[4].toString());
								FinalSiteLongLat.add(SiteLongLat);
							}
						}
						SiteData = new ArrayList<>();
						SiteLongLat = new ArrayList<>();
					//	 System.out.println("===> final site data AFTER SRC DB SITE <===" +
					//	 FinalResultSite);
					//	 System.out.println("===> final site data AFTER SRC DB SITE <===" +
					//	 FinalResultClt);

						if ((SiteSrcDB.get(0)[0]).toString().startsWith("CLT_")) {
							boolean valueExists = false;
							for (Object row : FinalResultClt) {
								ArrayList<Object> innerList = (ArrayList<Object>) FinalResultClt.get(f);
								Object secondColumn = innerList.get(1);
								// System.out.println("SRC DB CLIENT " +SiteSrcDB.get(0)[0]);
								if (secondColumn.equals(SiteSrcDB.get(0)[0])) {
									valueExists = true;
									break;
								}
							}
							if (!valueExists) {
								ClientData.add(SiteSrcDB.get(0)[0]);
								ClientData.add(SiteSrcDB.get(0)[2]);
								ClientData.add(SiteSrcDB.get(0)[1]);
								FinalResultClt.add(ClientData);

								CltLongLat.add(Src.get(0)[3].toString());
								CltLongLat.add(Src.get(0)[4].toString());
								FinalCltLongLat.add(CltLongLat);
							}
						}
						ClientData = new ArrayList<>();
						CltLongLat = new ArrayList<>();
					}
				}
			//	 System.out.println("===> final site data AFTER fb src <===" +
			//	 FinalResultSite);
				// System.out.println("===> final site data AFTER SRC DB CLIENT <===" +
				// FinalResultClt);
				ClientData = new ArrayList<>();
				SiteData = new ArrayList<>();
				CltLongLat = new ArrayList<>();
				SiteLongLat = new ArrayList<>();
				ArrayList<Object[]> Dest = new ArrayList<>();
				String DestId = "";
				String DestWareId = "";
				query = session.createSQLQuery(
						"SELECT DISTINCT DESTINATION_ID,DESTINATION_WARE_ID,DESTINATION_NAME,DESTINATION_LNG,DESTINATION_LAT FROM FIBER_CABLES WHERE FIBER_CABLE_ID= '"
								+ fiberID + "' ");
				Dest.addAll(query.list());
				DestWareId = Dest.get(0)[1].toString();
				DestId = Dest.get(0)[0].toString();
				/*
				 * System.out.println("the Dest id is " + DestId);
				 * System.out.println("the dest  are  " + mapper.writeValueAsString(Dest));
				 * System.out.println("the dest  0 are  "
				 * +mapper.writeValueAsString(Dest.get(0)[0]));
				 * System.out.println("the dest  1 are  "
				 * +mapper.writeValueAsString(Dest.get(0)[1]));
				 * System.out.println("the dest  2 are  "
				 * +mapper.writeValueAsString(Dest.get(0)[2]));
				 */
				if ((Dest.get(0)[0]).toString().startsWith("CLT_")) {
					boolean valueExists = false;
					for (Object row : FinalResultClt) {
						ArrayList<Object> innerList = (ArrayList<Object>) FinalResultClt.get(f);
						Object secondColumn = innerList.get(1);
						// System.out.println("DEST CLIENT " + Dest.get(0)[0]);
						if (secondColumn.equals(Dest.get(0)[0])) {
							valueExists = true;
							break;
						}
					}
					if (!valueExists) {
						String MobileNbrDest = session
								.createSQLQuery("SELECT DISTINCT MOBILE_NUMBER FROM CLIENTS WHERE CLIENT_ID= '" + DestId + "' ")
								.uniqueResult().toString();
						// System.out.println("the mobile nbr dest is " +
						// mapper.writeValueAsString(MobileNbrDest));
						ClientData.add(Dest.get(0)[0]);
						ClientData.add(Dest.get(0)[2]);
						ClientData.add(MobileNbrDest);
						FinalResultClt.add(ClientData);

						CltLongLat.add(Dest.get(0)[3].toString());
						CltLongLat.add(Dest.get(0)[4].toString());
						FinalCltLongLat.add(CltLongLat);
					}
				}
				ClientData = new ArrayList<>();
				CltLongLat = new ArrayList<>();
				// System.out.println("===> final site data AFTER DEST CLIENT <===" +
				// FinalResultSite);
				// System.out.println("===> final site data AFTER DEST CLIENT <===" +
				// FinalResultClt);

				if ((Dest.get(0)[1]).toString().startsWith("WARE_")) {
					boolean valueExists = false;
					for (Object row : FinalResultSite) {
						ArrayList<Object> innerList = (ArrayList<Object>) FinalResultSite.get(f);
						Object secondColumn = innerList.get(1);
						// System.out.println("DEST SITE " + Dest.get(0)[0]);
						if (secondColumn.equals(Dest.get(0)[0])) {
							valueExists = true;
							break;
						}
					}
					if (!valueExists) {
						List<Object[]> SiteIdDest = session
								.createSQLQuery(
										"SELECT DISTINCT SITE_ID,WARE_NAME FROM WAREHOUSE WHERE WARE_ID= '" + DestWareId + "' ")
								.list();
						// System.out.println("the site id dest is " +
						// mapper.writeValueAsString(SiteIdDest));
						SiteData.add(Dest.get(0)[1]);
						// System.out.println("SiteIdDest.get(0)[0] " +
						// mapper.writeValueAsString(SiteIdDest.get(0)[0]));
						SiteData.add(SiteIdDest.get(0)[0]);
						SiteData.add(SiteIdDest.get(0)[1]);
						FinalResultSite.add(SiteData);

						SiteLongLat.add(Dest.get(0)[3].toString());
						SiteLongLat.add(Dest.get(0)[4].toString());
						FinalSiteLongLat.add(SiteLongLat);
					}
				}
				SiteData = new ArrayList<>();
				SiteLongLat = new ArrayList<>();
			//	 System.out.println("===> final site data AFTER DEST SITE <===" +
			//	 FinalResultSite);
			//	 System.out.println("===> final site data AFTER DEST SITE <===" +
			//	 FinalResultClt);

				if ((Dest.get(0)[0]).toString().startsWith("DB_")) {
					// System.out.println("Site data dest db ===> " +SiteData);
					List<Object[]> SiteDestDB = session.createSQLQuery(
							"SELECT DISTINCT SITE,WAREHOUSE,SITE_NAME FROM DISTRIBUTION_BOARD WHERE DB_ID= '" + DestId + "' ")
							.list();
					// System.out.println("the site DB dest is " +
					// mapper.writeValueAsString(SiteDestDB));
					if (SiteDestDB.get(0)[0] != null && SiteDestDB.get(0)[1] != null) {
						SiteDestDB.get(0)[0].toString();
						SiteDestDB.get(0)[1].toString();
						if ((SiteDestDB.get(0)[1]).toString().startsWith("WARE_")) {
							boolean valueExists = false;
							for (Object row : FinalResultSite) {
								ArrayList<Object> innerList = (ArrayList<Object>) FinalResultSite.get(f);
								Object secondColumn = innerList.get(1);
								// System.out.println("DEST DB SITE " + Dest.get(0)[0]);
								if (secondColumn.equals(Dest.get(0)[0])) {
									valueExists = true;
									break;
								}
							}
							if (!valueExists) {
								String WareDestDB = session
										.createSQLQuery("SELECT DISTINCT WARE_NAME FROM WAREHOUSE WHERE WARE_ID= '"
												+ SiteDestDB.get(0)[1] + "' ")
										.uniqueResult().toString();
								// System.out.println("the ware DB dest is " +
								// mapper.writeValueAsString(WareDestDB));
								SiteData.add(SiteDestDB.get(0)[1]);
								SiteData.add(SiteDestDB.get(0)[0]);
								SiteData.add(WareDestDB);
								FinalResultSite.add(SiteData);

								SiteLongLat.add(Dest.get(0)[3].toString());
								SiteLongLat.add(Dest.get(0)[4].toString());
								FinalSiteLongLat.add(SiteLongLat);
							}
						}
						SiteData = new ArrayList<>();
						SiteLongLat = new ArrayList<>();
					//	 System.out.println("===> final site data AFTER DEST DB SITE <===" +
					//	 FinalResultSite);
					//	 System.out.println("===> final site data AFTER DEST DB SITE <===" +
					//	 FinalResultClt);

						if ((SiteDestDB.get(0)[0]).toString().startsWith("CLT_")) {
							boolean valueExists = false;
							for (Object row : FinalResultClt) {
								ArrayList<Object> innerList = (ArrayList<Object>) FinalResultClt.get(f);
								Object secondColumn = innerList.get(1);
								// System.out.println("DEST DB CLIENT " + Dest.get(0)[0]);
								if (secondColumn.equals(Dest.get(0)[0])) {
									valueExists = true;
									break;
								}
							}
							if (!valueExists) {
								ClientData.add(SiteDestDB.get(0)[0]);
								ClientData.add(SiteDestDB.get(0)[2]);
								ClientData.add(SiteDestDB.get(0)[1]);
								FinalResultClt.add(ClientData);

								CltLongLat.add(Dest.get(0)[3].toString());
								CltLongLat.add(Dest.get(0)[4].toString());
								FinalCltLongLat.add(CltLongLat);
							}
						}
						ClientData = new ArrayList<>();
						CltLongLat = new ArrayList<>();
					}
				}
			//	 System.out.println("===> final site data AFTER fb dest <===" +
			//	 FinalResultSite);
				// System.out.println("===> final site data AFTER DEST DB CLIENT <===" +
				// FinalResultClt);
			
					List<Object> uniqueObjectsSites = new ArrayList<>();
					// Iterate over the original list
					for (Object obj : FinalResultSite) {
					    // Check if the object is already in the unique list
					    if (!uniqueObjectsSites.contains(obj)) {
					        uniqueObjectsSites.add(obj);
					    }
					}
					System.out.println("===> final site data AFTER removing duplicates <===" + uniqueObjectsSites);

					List<Object> uniqueObjectsSitesLongLat = new ArrayList<>();
					for (Object obj : FinalSiteLongLat) {
					    if (!uniqueObjectsSitesLongLat.contains(obj)) {
					    	uniqueObjectsSitesLongLat.add(obj);
					    }
					}
					System.out.println("===> final site LONG LAT data AFTER removing duplicates <===" + uniqueObjectsSitesLongLat);

				
					List<Object> uniqueObjectsClients = new ArrayList<>();
					for (Object obj : FinalResultClt) {
					    if (!uniqueObjectsClients.contains(obj)) {
					        uniqueObjectsClients.add(obj);
					    }
					}
					System.out.println("===> final site data AFTER removing duplicates <===" + uniqueObjectsClients);


					List<Object> uniqueObjectsClientsLongLat = new ArrayList<>();
					for (Object obj : FinalCltLongLat) {
					    if (!uniqueObjectsClientsLongLat.contains(obj)) {
					    	uniqueObjectsClientsLongLat.add(obj);
					    }
					}
					System.out.println("===> final site LONG LAT data AFTER removing duplicates <===" + uniqueObjectsClientsLongLat);				
					
					System.out.println("CLIENT DATA  " + uniqueObjectsClients);
					System.out.println("SITE DATA  " + uniqueObjectsSites);
					System.out.println("CLIENT LONGLAT DATA  " + FinalCltLongLat);
					System.out.println("SITE LONGLAT DATA  " + uniqueObjectsSitesLongLat);

					rtn.put("CableName", CableName);
					rtn.put("ClientData", uniqueObjectsClients);
					rtn.put("SiteData", uniqueObjectsSites);
					rtn.put("CltLongLat", uniqueObjectsClientsLongLat);
					rtn.put("SiteLongLat", uniqueObjectsSitesLongLat);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.info("Error in retreiving  Data from database \n" + exceptionAsString);
				rtn.put("ClientData", null);
				rtn.put("SiteData", null);

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
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					fiberDetails = session.createSQLQuery(
							"Select DISTINCT SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,ITEM_CODE,NUMBER_OF_STRANDS,NUMBER_OF_TUBES,LENGTH,CONDUIT_ID,CONDUIT_NAME,SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,CABLE_MODE,FIBER_CABLE_NAME,SOURCE_CITY, DESTINATION_CITY, FIBER_TYPE, FIBER_DEPLOYMENT, FIBER_NETWORK_LEVEL, FIBER_OWNER,CREATED_BY,LAST_MODIFIED_BY,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),TOTAL_DRIVING_DISTANCE, TOTAL_GEO_DISTANCE, DRAWING_TYPE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE,RELATED_STRAND_NUMBER,RELATED_STRAND_COLOR,RELATED_STRAND_ID,RELATED_STRAND_NAME,RELATED_TUBE_NUMBER,RELATED_TUBE_COLOR,RELATED_TUBE_ID,RELATED_TUBE_NAME,RELATED_CABLE_ID,RELATED_CABLE_NAME,OTHERSIDE_LASTMILE_ID,OTHERSIDE_LASTMILE_NAME,OTHERSIDE_LOCATION_ID,OTHERSIDE_LOCATION_NAME,OTHERSIDE_LOCATION_CITY,OTHERSIDE_LOCATION_TYPE FROM FIBER_CABLES WHERE FIBER_CABLE_ID='"
									+ selectedFiberContext + "' ")
							.list();

					// USED IN SHOW POINTS FOR AUXILIARIES //
					List<Object[]> fiberAuxiliaryData = session.createSQLQuery(
							"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.SEQ_SORTING, B.DRIVING_DISTANCE, B.GEO_DISTANCE, B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID ='"
									+ selectedFiberContext + "' ORDER BY B.SEQ_SORTING ASC")
							.list();

					fiberTubes = session.createSQLQuery(
							"SELECT TUBE_ID, FIBER_CABLE_ID, SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,TUBE_NAME,SOURCE_CITY, DESTINATION_CITY, TUBE_TYPE, TUBE_DEPLOYMENT, TUBE_NETWORK_LEVEL, TUBE_OWNER,TUBE_NUMBER,TUBE_COLOR,DRAWING_TYPE,TOTAL_GEO_DISTANCE,TOTAL_DRIVING_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE FROM FIBER_TUBES WHERE FIBER_CABLE_ID='"
									+ selectedFiberContext + "' ")
							.list();

					fiberStrands = session.createSQLQuery(
							"SELECT STRAND_ID,FIBER_CABLE_ID, SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,TUBE_ID,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,STRAND_NAME,SOURCE_CITY, DESTINATION_CITY, STRAND_TYPE, STRAND_DEPLOYMENT, STRAND_NETWORK_LEVEL, STRAND_OWNER,STRAND_NUMBER,STRAND_COLOR,DRAWING_TYPE,TOTAL_GEO_DISTANCE,TOTAL_DRIVING_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE FROM FIBER_STRANDS WHERE FIBER_CABLE_ID='"
									+ selectedFiberContext + "' ")
							.list();

					accessJunctions = session.createSQLQuery(
							"SELECT JUNCTION_ROW_ID,JUNCTION_ID,JUNCTION_NAME,FIBER_CABLE_ID FROM ACCESS_CABLES_JUNCTIONS WHERE FIBER_CABLE_ID='"
									+ selectedFiberContext + "' ")
							.list();

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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findNodesDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findNodesDetails(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			String selectedNodeAcvtiveContext = request.getParameter("selectedNodeAcvtiveContext");
			try {
				Object[] NodesDetails = (Object[]) session.createSQLQuery(
						"SELECT NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,SUB_DOMAIN_TYPE,SITE_ID,WARE_ID,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(UPDATE_DATE, 'MM/dd/YYYY HH:MI AM'),LONGITUDE,LATITUDE FROM NODE_ACTIVE WHERE NODE_PK ='"+selectedNodeAcvtiveContext+"'").uniqueResult();
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
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/findCoreDetails", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> findCoreDetails(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonProcessingException {
			

			Map<String, Object> rtn = new LinkedHashMap<>();

			session = almsessions.getSession();
			if (LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				String selectedCoreIdContext = request.getParameter("selectedCoreIdContext");
				try {
					Object[] CoreNodesDetails = (Object[]) session.createSQLQuery(
							"SELECT NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,SITE_ID,WARE_ID,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(UPDATE_DATE, 'MM/dd/YYYY HH:MI AM'),LONGITUDE,LATITUDE FROM NODE_ACTIVE  WHERE DOMAIN = 'Core' AND NODE_PK ='"+selectedCoreIdContext+"'").uniqueResult();
					
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
				@SuppressWarnings("unchecked")
				@RequestMapping(value = "/findAccessDetails", method = RequestMethod.GET)
				@ResponseBody
				public Map<String, Object> findAccessDetails(Locale locale, Model model, HttpServletRequest request,
						HttpServletResponse response) throws JsonProcessingException {
					

					Map<String, Object> rtn = new LinkedHashMap<>();

					session = almsessions.getSession();
					if (LoginServices.checkSession(request, response).equals("redirect:/")) {
						rtn.put("Login", LoginServices.checkSession(request, response));
						return rtn;
					}
					if (session != null && session.isOpen()) {
						tx = session.beginTransaction();

						String selectedRanIdContext = request.getParameter("selectedRanIdContext");
						try {
							Object[] AccessNodesDetails = (Object[]) session.createSQLQuery(
									"SELECT NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,SITE_ID,WARE_ID,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(UPDATE_DATE, 'MM/dd/YYYY HH:MI AM'),LONGITUDE,LATITUDE FROM NODE_ACTIVE  WHERE DOMAIN = 'Access' AND NODE_PK ='"+selectedRanIdContext+"'").uniqueResult();
							
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

					session = almsessions.getSession();
					if (LoginServices.checkSession(request, response).equals("redirect:/")) {
						rtn.put("Login", LoginServices.checkSession(request, response));
						return rtn;
					}
					if (session != null && session.isOpen()) {
						tx = session.beginTransaction();

						String selectedTransmissionIdContext = request.getParameter("selectedTransmissionIdContext");
						try {
							Object[] TransmissionNodesDetails = (Object[]) session.createSQLQuery(
									"SELECT NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,SITE_ID,WARE_ID,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(UPDATE_DATE, 'MM/dd/YYYY HH:MI AM'),LONGITUDE,LATITUDE FROM NODE_ACTIVE  WHERE DOMAIN = 'Transmission' AND NODE_PK ='"+selectedTransmissionIdContext+"'").uniqueResult();
							
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
		
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/entrNodeSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> entrNodeSave(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				
				NodeActive nodeActive = new NodeActive();
				String nodePk = request.getParameter("nodePk");
				/*Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				if (StringUtils.equalsIgnoreCase(nodePk, "")
						|| StringUtils.equalsIgnoreCase(nodePk, null)) {
					synchronized (this) {

						// distributionBoardId = "DB_" + year + "_" + appConfig.getSeqNbr(52,session);
						nodePk = "NODE_ACTIVE" + year + "_" + Integer.parseInt(
								session.createSQLQuery("SELECT NODE_ACTIVE FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET NODE_ACTIVE = NODE_ACTIVE + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
					}
				}*/
			
				/*Timestamp updateData_node = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

				String createData_node = request.getParameter("createData_node");
				Timestamp nodeCreationDate;
				if (StringUtils.equalsIgnoreCase(createData_node, "")) {
					nodeCreationDate = updateData_node;

				} else {
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					nodeCreationDate = new Timestamp(
					formatter.parse(request.getParameter("createData_node")).getTime());
				}*/
				
				/*String UniqNodeId = request.getParameter("UniqNodeId");
				String NodeId = request.getParameter("NodeId");
				String nodeName = request.getParameter("nodeName");
				String nodeType = request.getParameter("nodeType");
				String nodeSource = request.getParameter("nodeSource");
				String nodeModel = request.getParameter("nodeModel");
				String nodeDomin = request.getParameter("nodeDomin");
				String siteId_node = request.getParameter("siteId_node");
				String wareId_node = request.getParameter("wareId_node");
				String nodeLong = request.getParameter("nodeLong");
				String nodeLat = request.getParameter("nodeLat");*/
				
				nodeActive.setNodePK(nodePk);
				nodeActive.setUniNodeID(request.getParameter("UniqNodeId"));
				nodeActive.setNodeID(request.getParameter("NodeId"));
				nodeActive.setNodeName(request.getParameter("nodeName"));
				nodeActive.setNodeType(request.getParameter("nodeType"));
				nodeActive.setNodeSrc(request.getParameter("nodeSource"));
				nodeActive.setNodeModel(request.getParameter("nodeModel"));
				nodeActive.setDomain(request.getParameter("nodeDomin"));
				nodeActive.setSiteID(request.getParameter("siteId_node"));
				nodeActive.setWareID(request.getParameter("wareId_node"));
				
				//nodeActive.setUpdateDate(updateData_node);
				//nodeActive.setCreatedDate(nodeCreationDate);
				
				//session.saveOrUpdate(nodeActive);

				rtn.put("nodePk", nodePk);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in entrNodeSave due to \n " + exceptionAsString);
				logger.info("Error in entrNodeSave due to \n " + exceptionAsString);
				rtn.put("nodePk", null);
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
	@RequestMapping(value = "/transNodeSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> transNodeSave(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/coreNodeSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> coreNodeSave(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ranNodeSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ranNodeSave(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetALLFIBER", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetALLFIBER(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = almsessions.getSession();
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

	@RequestMapping(value = "/showDBs", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> showDBs(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String contextID = request.getParameter("selectedContext").toString();
		String target = request.getParameter("target").toString();
		// System.out.println("entered target is "+target);
		List<Object[]> DBData = null;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				if (target.equals("Fiber")) {
					System.out.println("entered fiber " + contextID);
					/*
					 * DBData=session.
					 * createSQLQuery("SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where C.FIBER_CABLE_ID LIKE '%"
					 * +contextID+"%' " + " UNION " +
					 * " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.FIBER_CABLE_ID LIKE '%"
					 * +contextID+"%' " + " UNION " +
					 * " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.FIBER_CABLE_ID LIKE '%"
					 * +contextID+"%' ").list();
					 */
					// DBData=session.createSQLQuery("SELECT DISTINCT
					// A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID
					// ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B ON
					// B.DB_ID = A.DB_ID where B.BP_FIBER_ID LIKE '"+contextID+"' OR B.FP_FIBER_ID
					// LIKE '"+contextID+"' ").list();
					DBData = session.createSQLQuery(
							"SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B  ON B.DB_ID = A.DB_ID  where B.BP_FIBER_ID LIKE '"
									+ contextID + "' OR B.FP_FIBER_ID LIKE '" + contextID + "' " + " UNION "
									+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.FIBER_CABLE_ID LIKE '%"
									+ contextID + "%' " + " UNION "
									+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.FIBER_CABLE_ID LIKE '%"
									+ contextID + "%' ")
							.list();

				} else if (target.equals("Tube")) {
					System.out.println("entered Tube " + contextID);
					/*
					 * DBData=session.
					 * createSQLQuery("SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_TUBES C ON C.TUBE_ID = B.TUBE_ID where C.TUBE_ID LIKE '%"
					 * +contextID+"%' " + " UNION " +
					 * " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.DB_ID where B.TUBE_ID LIKE '%"
					 * +contextID+"%' " + " UNION " +
					 * " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.DB_ID where B.TUBE_ID LIKE '%"
					 * +contextID+"%' ").list();
					 */
					// DBData=session.createSQLQuery("SELECT DISTINCT
					// A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID
					// ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B ON
					// B.DB_ID = A.DB_ID where B.BP_TUBE_ID LIKE '"+contextID+"' OR B.FP_TUBE_ID
					// LIKE '"+contextID+"' ").list();
					DBData = session.createSQLQuery(
							"SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B  ON B.DB_ID = A.DB_ID  where B.BP_TUBE_ID LIKE '"
									+ contextID + "' OR B.FP_TUBE_ID LIKE '" + contextID + "' " + " UNION "
									+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.DB_ID where B.TUBE_ID LIKE '%"
									+ contextID + "%' " + " UNION "
									+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.DB_ID where B.TUBE_ID LIKE '%"
									+ contextID + "%' ")
							.list();

				} else if (target.equals("Strand")) {
					System.out.println("entered Strand " + contextID);
					/*
					 * DBData=session.
					 * createSQLQuery("SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_STRANDS C ON C.STRAND_ID = B.STRAND_ID where C.STRAND_ID LIKE '%"
					 * +contextID+"%' " + " UNION " +
					 * " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.DB_ID where B.STRAND_ID LIKE '%"
					 * +contextID+"%' " + " UNION " +
					 * " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.DB_ID where B.STRAND_ID LIKE '%"
					 * +contextID+"%' ").list();
					 */
					// DBData=session.createSQLQuery("SELECT DISTINCT
					// A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID
					// ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B ON
					// B.DB_ID = A.DB_ID where B.BP_STRAND_ID LIKE '"+contextID+"' OR B.FP_STRAND_ID
					// LIKE '"+contextID+"' ").list();
					DBData = session.createSQLQuery(
							"SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN DISTRIBUTION_BOARD_MAPPING B  ON B.DB_ID = A.DB_ID  where B.BP_STRAND_ID LIKE '"
									+ contextID + "' OR B.FP_STRAND_ID LIKE '" + contextID + "' " + " UNION "
									+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.DB_ID where B.STRAND_ID LIKE '%"
									+ contextID + "%' " + " UNION "
									+ " SELECT DISTINCT A.DB_ID,A.DB_LONGITUDE,A.DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID,A.CITY FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.DB_ID where B.STRAND_ID LIKE '%"
									+ contextID + "%' ")
							.list();

				}
				rtn.put("DBData", DBData);

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
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				String dataSel = request.getParameter("dataSel");

				// DATA FOR BACKBONE NETWORKLEVEL
				List<Object[]> BackboneCableData = session.createSQLQuery(
						"Select DISTINCT a.FIBER_CABLE_ID, a.FIBER_CABLE_NAME,a.FIBER_NETWORK_LEVEL,a.FIBER_OWNER from FIBER_CABLES a left join FIBER_AUXILIARY_POINTS b ON a.FIBER_CABLE_ID = b.FIBER_CABLE_ID where (SOURCE_ID='"
								+ dataSel + "' or DESTINATION_ID='" + dataSel + "' or b.AUXILIARY_POINT_ID='" + dataSel
								+ "') and FIBER_NETWORK_LEVEL = 'backbone'")
						.list();
				rtn.put("BackboneCableData", BackboneCableData);

				List<Object[]> BackboneTubeData = session.createSQLQuery(
						"Select DISTINCT a.TUBE_ID,a.TUBE_NAME,c.FIBER_CABLE_ID, c.FIBER_CABLE_NAME,a.TUBE_NETWORK_LEVEL,TUBE_COLOR,TUBE_NUMBER from FIBER_TUBES a left join TUBE_AUXILIARY_POINTS b ON a.TUBE_ID = b.TUBE_ID left join FIBER_CABLES c ON a.FIBER_CABLE_ID = c.FIBER_CABLE_ID where (a.SOURCE_ID='"
								+ dataSel + "' or a.DESTINATION_ID='" + dataSel + "' or b.AUXILIARY_POINT_ID='"
								+ dataSel + "') and TUBE_NETWORK_LEVEL = 'backbone'")
						.list();
				rtn.put("BackboneTubeData", BackboneTubeData);

				List<Object[]> BackboneStrandData = session.createSQLQuery(
						"Select DISTINCT a.STRAND_ID, a.STRAND_NAME, c.TUBE_ID,c.TUBE_NAME,a.STRAND_NETWORK_LEVEL,a.STRAND_COLOR,a.STRAND_NUMBER,d.FIBER_CABLE_NAME,d.FIBER_CABLE_ID from FIBER_STRANDS a left join STRAND_AUXILIARY_POINTS b ON a.STRAND_ID = b.STRAND_ID left join  FIBER_TUBES c ON a.TUBE_ID = c.TUBE_ID left join FIBER_CABLES d ON d.FIBER_CABLE_ID = a.FIBER_CABLE_ID where (a.SOURCE_ID='"
								+ dataSel + "' or a.DESTINATION_ID='" + dataSel + "' or b.AUXILIARY_POINT_ID='"
								+ dataSel + "') and STRAND_NETWORK_LEVEL = 'backbone' ")
						.list();
				rtn.put("BackboneStrandData", BackboneStrandData);

				// DATA FOR METRO NETWORKLEVEL
				List<Object[]> MetroCableData = session.createSQLQuery(
						"Select DISTINCT a.FIBER_CABLE_ID, a.FIBER_CABLE_NAME,a.FIBER_NETWORK_LEVEL,a.FIBER_OWNER from FIBER_CABLES a left join FIBER_AUXILIARY_POINTS b ON a.FIBER_CABLE_ID = b.FIBER_CABLE_ID where (SOURCE_ID='"
								+ dataSel + "' or DESTINATION_ID='" + dataSel + "' or b.AUXILIARY_POINT_ID='" + dataSel
								+ "') and FIBER_NETWORK_LEVEL = 'metro'")
						.list();
				rtn.put("MetroCableData", MetroCableData);

				List<Object[]> MetroTubeData = session.createSQLQuery(
						"Select DISTINCT a.TUBE_ID,a.TUBE_NAME,c.FIBER_CABLE_ID, c.FIBER_CABLE_NAME,a.TUBE_NETWORK_LEVEL,a.TUBE_COLOR,a.TUBE_NUMBER from FIBER_TUBES a left join TUBE_AUXILIARY_POINTS b ON a.TUBE_ID = b.TUBE_ID left join FIBER_CABLES c ON a.FIBER_CABLE_ID = c.FIBER_CABLE_ID where (a.SOURCE_ID='"
								+ dataSel + "' or a.DESTINATION_ID='" + dataSel + "' or AUXILIARY_POINT_ID='" + dataSel
								+ "') and TUBE_NETWORK_LEVEL = 'metro'")
						.list();
				rtn.put("MetroTubeData", MetroTubeData);

				List<Object[]> MetroStrandData = session.createSQLQuery(
						"Select DISTINCT a.STRAND_ID, a.STRAND_NAME, c.TUBE_ID,c.TUBE_NAME,a.STRAND_NETWORK_LEVEL,a.STRAND_COLOR,a.STRAND_NUMBER,d.FIBER_CABLE_NAME,d.FIBER_CABLE_ID from FIBER_STRANDS a left join STRAND_AUXILIARY_POINTS b ON a.STRAND_ID = b.STRAND_ID left join  FIBER_TUBES c ON a.TUBE_ID = c.TUBE_ID left join FIBER_CABLES d ON d.FIBER_CABLE_ID = a.FIBER_CABLE_ID where (a.SOURCE_ID='"
								+ dataSel + "' or a.DESTINATION_ID='" + dataSel + "' or AUXILIARY_POINT_ID='" + dataSel
								+ "') and STRAND_NETWORK_LEVEL = 'metro' ")
						.list();
				rtn.put("MetroStrandData", MetroStrandData);

				// DATA FOR Access NETWORKLEVEL
				List<Object[]> DistributionCableData = session.createSQLQuery(
						"Select DISTINCT a.FIBER_CABLE_ID, a.FIBER_CABLE_NAME,a.FIBER_NETWORK_LEVEL,a.FIBER_OWNER from FIBER_CABLES a left join FIBER_AUXILIARY_POINTS b ON a.FIBER_CABLE_ID = b.FIBER_CABLE_ID where (SOURCE_ID='"
								+ dataSel + "' or DESTINATION_ID='" + dataSel + "' or AUXILIARY_POINT_ID='" + dataSel
								+ "') and FIBER_NETWORK_LEVEL = 'access'")
						.list();
				rtn.put("DistributionCableData", DistributionCableData);

				List<Object[]> DistributionTubeData = session.createSQLQuery(
						"Select DISTINCT a.TUBE_ID,a.TUBE_NAME,c.FIBER_CABLE_ID, c.FIBER_CABLE_NAME,a.TUBE_NETWORK_LEVEL,a.TUBE_COLOR,a.TUBE_NUMBER from FIBER_TUBES a left join TUBE_AUXILIARY_POINTS b ON a.TUBE_ID = b.TUBE_ID left join FIBER_CABLES c ON a.FIBER_CABLE_ID = c.FIBER_CABLE_ID where (a.SOURCE_ID='"
								+ dataSel + "' or a.DESTINATION_ID='" + dataSel + "' or AUXILIARY_POINT_ID='" + dataSel
								+ "') and TUBE_NETWORK_LEVEL = 'access'")
						.list();
				rtn.put("DistributionTubeData", DistributionTubeData);

				List<Object[]> DistributionStrandData = session.createSQLQuery(
						"Select DISTINCT a.STRAND_ID, a.STRAND_NAME, c.TUBE_ID,c.TUBE_NAME,a.STRAND_NETWORK_LEVEL,a.STRAND_COLOR,a.STRAND_NUMBER,d.FIBER_CABLE_NAME,d.FIBER_CABLE_ID from FIBER_STRANDS a left join STRAND_AUXILIARY_POINTS b ON a.STRAND_ID = b.STRAND_ID left join  FIBER_TUBES c ON a.TUBE_ID = c.TUBE_ID left join FIBER_CABLES d ON d.FIBER_CABLE_ID = a.FIBER_CABLE_ID where (a.SOURCE_ID='"
								+ dataSel + "' or a.DESTINATION_ID='" + dataSel + "' or AUXILIARY_POINT_ID='" + dataSel
								+ "') and STRAND_NETWORK_LEVEL = 'access' ")
						.list();
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
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				manholeData = session.createSQLQuery(
						"select MANHOLE_NAME,LONGITUDE,LATITUDE,CITY,DM_NAME FROM MANHOLE " + "where MANHOLE_ID='"
								+ manhole_Id + "' AND PROJECT_ID ='" + request.getParameter("ProjectId") + "'")
						.list();

				query = session.createSQLQuery("select count(*) from FIBER_CABLES a where a.SOURCE_ID = '" + manhole_Id
						+ "' or a.DESTINATION_ID = '" + manhole_Id + "' AND a.PROJECT_ID ='"
						+ request.getParameter("ProjectId") + "' ");

				countFiberCablesSrcDest = query.uniqueResult().toString();

				query = session.createSQLQuery(
						"select count (*) from FIBER_AUXILIARY_POINTS b LEFT JOIN  FIBER_CABLES c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID "
								+ "where b.AUXILIARY_POINT_ID = '" + manhole_Id + "'  and c.PROJECT_ID ='"
								+ request.getParameter("ProjectId") + "'");

				countFiberCablesAux = query.uniqueResult().toString();

				countFiberCables = String
						.valueOf((Integer.parseInt(countFiberCablesSrcDest)) + (Integer.parseInt(countFiberCablesAux)));

				countTubesSrcDest = session.createSQLQuery(
						"select count (*) from FIBER_TUBES b LEFT JOIN  FIBER_CABLES c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID where b.SOURCE_ID LIKE '"
								+ manhole_Id + "%' " + "or b.DESTINATION_ID LIKE '" + manhole_Id
								+ "%' and c.PROJECT_ID ='" + request.getParameter("ProjectId") + "' ")
						.uniqueResult().toString();

				countTubesAux = session.createSQLQuery(
						"select count (*) from FIBER_TUBES b LEFT JOIN  TUBE_AUXILIARY_POINTS c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID "
								+ "where c.AUXILIARY_POINT_ID LIKE '" + manhole_Id + "%'")
						.uniqueResult().toString();

				countTubes = String.valueOf((Integer.parseInt(countTubesSrcDest)) + (Integer.parseInt(countTubesAux)));

				countStrandsSrcDest = session.createSQLQuery(
						"select count (*) from FIBER_STRANDS c LEFT JOIN  FIBER_CABLES b ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID where c.SOURCE_ID LIKE '"
								+ manhole_Id + "%' " + "or c.DESTINATION_ID LIKE '" + manhole_Id
								+ "%' and b.PROJECT_ID ='" + request.getParameter("ProjectId") + "' ")
						.uniqueResult().toString();

				countStrandsAux = session.createSQLQuery(
						"select count (*) from FIBER_STRANDS b LEFT JOIN  STRAND_AUXILIARY_POINTS c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID "
								+ "where c.AUXILIARY_POINT_ID LIKE '" + manhole_Id + "%'")
						.uniqueResult().toString();

				countStrands = String
						.valueOf((Integer.parseInt(countStrandsSrcDest)) + (Integer.parseInt(countStrandsAux)));
				countJunctions = session
						.createSQLQuery("select coalesce(count(*),0) from JUNCTION  where PHYSICAL_LAYER_ID='"
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

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				handholeData = session.createSQLQuery(
						"select HANDHOLE_NAME,LONGITUDE,LATITUDE,CITY,DM_NAME FROM HANDHOLE " + "where HANDHOLE_ID='"
								+ handhole_Id + "' AND PROJECT_ID ='" + request.getParameter("ProjectId") + "'")
						.list();

				query = session.createSQLQuery("select count(*) from FIBER_CABLES a where a.SOURCE_ID = '" + handhole_Id
						+ "' or a.DESTINATION_ID = '" + handhole_Id + "' AND a.PROJECT_ID ='"
						+ request.getParameter("ProjectId") + "' ");

				countFiberCablesSrcDest = query.uniqueResult().toString();

				query = session.createSQLQuery(
						"select count (*) from FIBER_AUXILIARY_POINTS b LEFT JOIN  FIBER_CABLES c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID "
								+ "where b.AUXILIARY_POINT_ID = '" + handhole_Id + "'  and c.PROJECT_ID ='"
								+ request.getParameter("ProjectId") + "'");
				countFiberCablesAux = query.uniqueResult().toString();

				countFiberCables = String
						.valueOf((Integer.parseInt(countFiberCablesSrcDest)) + (Integer.parseInt(countFiberCablesAux)));

				countTubesSrcDest = session.createSQLQuery(
						"select count (*) from FIBER_TUBES b LEFT JOIN  FIBER_CABLES c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID where b.SOURCE_ID LIKE '"
								+ handhole_Id + "%' " + "or b.DESTINATION_ID LIKE '" + handhole_Id
								+ "%' and c.PROJECT_ID ='" + request.getParameter("ProjectId") + "' ")
						.uniqueResult().toString();

				countTubesAux = session.createSQLQuery(
						"select count (*) from FIBER_TUBES b LEFT JOIN  TUBE_AUXILIARY_POINTS c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID "
								+ "where c.AUXILIARY_POINT_ID LIKE '" + handhole_Id + "%'")
						.uniqueResult().toString();

				countTubes = String.valueOf((Integer.parseInt(countTubesSrcDest)) + (Integer.parseInt(countTubesAux)));

				countStrandsSrcDest = session.createSQLQuery(
						"select count (*) from FIBER_STRANDS c LEFT JOIN  FIBER_CABLES b ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID where c.SOURCE_ID LIKE '"
								+ handhole_Id + "%' " + "or c.DESTINATION_ID LIKE '" + handhole_Id
								+ "%' and b.PROJECT_ID ='" + request.getParameter("ProjectId") + "' ")
						.uniqueResult().toString();

				countStrandsAux = session.createSQLQuery(
						"select count (*) from FIBER_STRANDS b LEFT JOIN  STRAND_AUXILIARY_POINTS c ON b.FIBER_CABLE_ID = c.FIBER_CABLE_ID "
								+ "where c.AUXILIARY_POINT_ID LIKE '" + handhole_Id + "%'")
						.uniqueResult().toString();

				countStrands = String
						.valueOf((Integer.parseInt(countStrandsSrcDest)) + (Integer.parseInt(countStrandsAux)));
				countJunctions = session
						.createSQLQuery("select coalesce(count(*),0) from JUNCTION  where PHYSICAL_LAYER_ID='"
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

	@RequestMapping(value = "/FiberBoQ", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FiberBoQ(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String fiberID = request.getParameter("fiberID").toString();
		String projectID = request.getParameter("projectID").toString();

		List<Object[]> FiberData = null;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				FiberData = session.createSQLQuery(
						"SELECT FIBER_CABLE_NAME,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_STRANDS,NUMBER_OF_TUBES,(number_of_strands*number_of_tubes),LENGTH, total_geo_distance from fiber_cables WHERE FIBER_CABLE_ID='"
								+ fiberID + "' and PROJECT_ID='" + projectID + "'")
						.list();
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

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				TubeData = session.createSQLQuery(
						"SELECT SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,total_geo_distance,Length from FIBER_TUBES WHERE TUBE_ID='"
								+ tubeID + "' and FIBER_CABLE_ID='" + fiberID + "'")
						.list();
				rtn.put("TubeData", TubeData);

				strandsCount = session.createSQLQuery("select count (*) from FIBER_STRANDS WHERE TUBE_ID='" + tubeID
						+ "' and FIBER_CABLE_ID='" + fiberID + "'").uniqueResult().toString();
				rtn.put("strandsCount", strandsCount);
				cable_geo = session.createSQLQuery(
						"select total_geo_distance from FIBER_cables WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_tubes where TUBE_ID='"
								+ tubeID + "')")
						.list();
				cable_LineOfSite = session.createSQLQuery(
						"select Length from FIBER_cables WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_tubes where TUBE_ID='"
								+ tubeID + "')")
						.list();
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

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				List<Object[]> DuctData = session.createSQLQuery(
						"SELECT B.SOURCE_WARE_ID,B.SOURCE_ID,B.SOURCE_NAME,B.DESTINATION_WARE_ID,B.DESTINATION_ID,B.DESTINATION_NAME,B.SOURCE_LATITUDE,B.SOURCE_LONGITUDE,B.DESTINATION_LONGITUDE"
								+ ",B.DESTINATION_LATITUDE,B.SOURCE_CITY,B.DESTINATION_CITY,B.LENGTH,B.NUM_FIBERCABLES,B.NUM_FIBERTUBES,"
								+ "B.NUM_FIBERSTRANDS,B.TRENCH_ID,B.DUCT_NAME FROM DUCTS B " + "WHERE B.DUCT_ID='"
								+ ductID + "'")
						.list();

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

		session = almsessions.getSession();
		ObjectMapper map = new ObjectMapper();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				String distBoardSel = request.getParameter("DistBoardSel").toString();
				List<Object[]> DBData = session.createSQLQuery(
						"Select DB_NAME,DB_LONGITUDE,DB_LATITUDE,CITY FROM DISTRIBUTION_BOARD where" + " DB_ID='"
								+ distBoardSel + "' and PROJECT_ID='" + request.getParameter("projectID") + "'")
						.list();
				rtn.put("DBData", DBData);
				List<Object[]> countConnections = session.createSQLQuery(
						"select NUM_ROWS,NUM_COLUMNS,(SELECT COUNT(B.FP_STATUS) FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.FP_STATUS='Active' AND B.DB_ID='"
								+ distBoardSel
								+ "') AS front,(SELECT COUNT(B.BP_STATUS) FROM DISTRIBUTION_BOARD_MAPPING B WHERE B.BP_STATUS='Active' AND B.DB_ID='"
								+ distBoardSel + "') AS back from DISTRIBUTION_BOARD b where b.DB_ID='" + distBoardSel
								+ "'")
						.list();
				System.out.println("countConnections " + map.writeValueAsString(countConnections));
				rtn.put("countConnections", countConnections);

			} catch (Exception e) {

				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in boqDistBoard due to \n " + exceptionAsString);
				logger.info("Error in boqDistBoard due to \n " + exceptionAsString);
				rtn.put("countConnections", null);
				rtn.put("DBData", null);

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
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
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Object MSANCount = session.createSQLQuery("select count (*) from node_active where domain = 'Enterprise'").list();
				rtn.put("MSANCount", MSANCount);
				
				Object DWDMCount = session.createSQLQuery("select count (*) from node_active where domain = 'Transmission' AND SUB_DOMAIN_TYPE = 'DWDM'").list();
				rtn.put("DWDMCount", DWDMCount);
				
				Object SDHCount = session.createSQLQuery("select count (*) from node_active where domain = 'Transmission' AND SUB_DOMAIN_TYPE = 'SDH'").list();
				rtn.put("SDHCount", SDHCount);
				
				Object GPONCount = session.createSQLQuery("select count (*) from node_active where domain = 'Transmission' AND SUB_DOMAIN_TYPE = 'GPON'").list();
				rtn.put("GPONCount", GPONCount);

				Object AllNodesCount = session.createSQLQuery("SELECT COUNT(*) FROM node_active WHERE domain IN ('Enterprise' , 'Transmission') AND SUB_DOMAIN_TYPE IN ('DWDM','SDH','GPON','MSAN')  ").list();
				rtn.put("AllNodesCount", AllNodesCount);
				
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in boqNodesCount due to \n " + exceptionAsString);
				logger.info("Error in boqNodesCount due to \n " + exceptionAsString);
				rtn.put("boqNodesCount", null);
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
	@RequestMapping(value = "/pathDistBoard", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> pathDistBoard(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		ObjectMapper mapper = new ObjectMapper();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				String distBoardSel = request.getParameter("DistBoardSel");
				/* Backbone data for show Path */
				List<Object[]> CableBackboneData = session.createSQLQuery(
						"Select DISTINCT a.BP_FIBER_ID,a.BP_FIBER_NAME,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN  FIBER_CABLES b on a.BP_FIBER_ID = b.FIBER_CABLE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT a.FP_FIBER_ID,a.FP_FIBER_NAME,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN  FIBER_CABLES b on a.FP_FIBER_ID = b.FIBER_CABLE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT FIBER_CABLE_ID,FIBER_CABLE_NAME,FIBER_NETWORK_LEVEL,FIBER_OWNER FROM FIBER_CABLES WHERE (SOURCE_ID='"
								+ distBoardSel + "' OR DESTINATION_ID='" + distBoardSel
								+ "') and (FIBER_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT A.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,B.FIBER_NETWORK_LEVEL,B.FIBER_OWNER FROM FIBER_AUXILIARY_POINTS A LEFT JOIN  FIBER_CABLES B on A.FIBER_CABLE_ID = B.FIBER_CABLE_ID WHERE (AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'backbone')")
						.list();
				rtn.put("CableBackboneData", CableBackboneData);
				System.out.println("CableBackboneData is -->/n" + mapper.writeValueAsString(CableBackboneData));

				List<Object[]> TubeBackboneData = session.createSQLQuery(
						"Select DISTINCT a.BP_TUBE_ID,a.BP_TUBE_NAME,a.BP_TUBE_COLOR,a.BP_TUBE_NB,a.BP_FIBER_ID,a.BP_FIBER_NAME,b.TUBE_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN FIBER_TUBES b on a.BP_TUBE_ID = b.TUBE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT a.FP_TUBE_ID,a.FP_TUBE_NAME,a.FP_TUBE_COLOR,a.FP_TUBE_NB,a.FP_FIBER_ID,a.FP_FIBER_NAME,b.TUBE_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN FIBER_TUBES b on a.FP_TUBE_ID = b.TUBE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT A.TUBE_ID,A.TUBE_NAME,A.TUBE_COLOR,A.TUBE_NUMBER,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,A.TUBE_NETWORK_LEVEL FROM (FIBER_TUBES A LEFT JOIN FIBER_CABLES B on A.FIBER_CABLE_ID=B.FIBER_CABLE_ID) WHERE (A.SOURCE_ID='"
								+ distBoardSel + "' OR A.DESTINATION_ID='" + distBoardSel
								+ "') and (TUBE_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT A.TUBE_ID,B.TUBE_NAME,B.TUBE_COLOR,B.TUBE_NUMBER,A.FIBER_CABLE_ID,C.FIBER_CABLE_NAME,B.TUBE_NETWORK_LEVEL FROM (TUBE_AUXILIARY_POINTS A LEFT JOIN  FIBER_TUBES B on A.TUBE_ID = B.TUBE_ID) LEFT JOIN FIBER_CABLES C on A.FIBER_CABLE_ID=C.FIBER_CABLE_ID  WHERE (A.AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'backbone')")
						.list();
				rtn.put("TubeBackboneData", TubeBackboneData);

				List<Object[]> StrandBackboneData = session.createSQLQuery(
						"Select DISTINCT a.BP_STRAND_ID,a.BP_STRAND_NAME,a.BP_STRAND_COLOR,a.BP_STRAND_NB,a.BP_FIBER_ID,a.BP_FIBER_NAME,a.BP_TUBE_ID,a.BP_TUBE_NAME,b.STRAND_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a left join FIBER_STRANDS b on a.BP_STRAND_ID = b.STRAND_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT a.FP_STRAND_ID,a.FP_STRAND_NAME,a.FP_STRAND_COLOR,a.FP_STRAND_NB,a.FP_FIBER_ID,a.FP_FIBER_NAME,a.FP_TUBE_ID,a.FP_TUBE_NAME,b.STRAND_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a left join FIBER_STRANDS b on a.FP_STRAND_ID = b.STRAND_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT A.STRAND_ID,A.STRAND_NAME,A.STRAND_COLOR,A.STRAND_NUMBER,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_ID,C.TUBE_NAME,A.STRAND_NETWORK_LEVEL FROM (FIBER_STRANDS A LEFT JOIN FIBER_CABLES B on A.FIBER_CABLE_ID=B.FIBER_CABLE_ID) LEFT JOIN FIBER_TUBES C on A.TUBE_ID=C.TUBE_ID WHERE (A.SOURCE_ID='"
								+ distBoardSel + "' OR A.DESTINATION_ID='" + distBoardSel
								+ "') and (STRAND_NETWORK_LEVEL = 'backbone')" + " UNION "
								+ "Select DISTINCT A.STRAND_ID,B.STRAND_NAME,B.STRAND_COLOR,B.STRAND_NUMBER,C.FIBER_CABLE_ID,C.FIBER_CABLE_NAME,D.TUBE_ID,D.TUBE_NAME,B.STRAND_NETWORK_LEVEL FROM (STRAND_AUXILIARY_POINTS A LEFT JOIN  FIBER_STRANDS B on A.STRAND_ID = B.STRAND_ID) LEFT JOIN FIBER_CABLES C on A.FIBER_CABLE_ID=C.FIBER_CABLE_ID LEFT JOIN FIBER_TUBES D on B.TUBE_ID=D.TUBE_ID WHERE (A.AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'backbone')")
						.list();
				rtn.put("StrandBackboneData", StrandBackboneData);

				/* Metro data for show Path */
				List<Object[]> CableMetroData = session.createSQLQuery(
						"Select DISTINCT a.BP_FIBER_ID,a.BP_FIBER_NAME,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN  FIBER_CABLES b on a.BP_FIBER_ID = b.FIBER_CABLE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT a.FP_FIBER_ID,a.FP_FIBER_NAME,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN  FIBER_CABLES b on a.FP_FIBER_ID = b.FIBER_CABLE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT FIBER_CABLE_ID,FIBER_CABLE_NAME,FIBER_NETWORK_LEVEL,FIBER_OWNER FROM FIBER_CABLES WHERE (SOURCE_ID='"
								+ distBoardSel + "' OR DESTINATION_ID='" + distBoardSel
								+ "') and (FIBER_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT A.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,B.FIBER_NETWORK_LEVEL,B.FIBER_OWNER FROM FIBER_AUXILIARY_POINTS A LEFT JOIN  FIBER_CABLES B on A.FIBER_CABLE_ID = B.FIBER_CABLE_ID WHERE (AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'metro')")
						.list();
				rtn.put("CableMetroData", CableMetroData);

				List<Object[]> TubeMetroData = session.createSQLQuery(
						"Select DISTINCT a.BP_TUBE_ID,a.BP_TUBE_NAME,a.BP_TUBE_COLOR,a.BP_TUBE_NB,a.BP_FIBER_ID,a.BP_FIBER_NAME,b.TUBE_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN FIBER_TUBES b on a.BP_TUBE_ID = b.TUBE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT a.FP_TUBE_ID,a.FP_TUBE_NAME,a.FP_TUBE_COLOR,a.FP_TUBE_NB,a.FP_FIBER_ID,a.FP_FIBER_NAME,b.TUBE_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN FIBER_TUBES b on a.FP_TUBE_ID = b.TUBE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT A.TUBE_ID,A.TUBE_NAME,A.TUBE_COLOR,A.TUBE_NUMBER,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,A.TUBE_NETWORK_LEVEL FROM (FIBER_TUBES A LEFT JOIN FIBER_CABLES B on A.FIBER_CABLE_ID=B.FIBER_CABLE_ID) WHERE (A.SOURCE_ID='"
								+ distBoardSel + "' OR A.DESTINATION_ID='" + distBoardSel
								+ "') and (TUBE_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT A.TUBE_ID,B.TUBE_NAME,B.TUBE_COLOR,B.TUBE_NUMBER,A.FIBER_CABLE_ID,C.FIBER_CABLE_NAME,B.TUBE_NETWORK_LEVEL FROM (TUBE_AUXILIARY_POINTS A LEFT JOIN  FIBER_TUBES B on A.TUBE_ID = B.TUBE_ID) LEFT JOIN FIBER_CABLES C on A.FIBER_CABLE_ID=C.FIBER_CABLE_ID  WHERE (A.AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'metro')")
						.list();
				rtn.put("TubeMetroData", TubeMetroData);

				List<Object[]> StrandMetroData = session.createSQLQuery(
						"Select DISTINCT a.BP_STRAND_ID,a.BP_STRAND_NAME,a.BP_STRAND_COLOR,a.BP_STRAND_NB,a.BP_FIBER_ID,a.BP_FIBER_NAME,a.BP_TUBE_ID,a.BP_TUBE_NAME,b.STRAND_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a left join FIBER_STRANDS b on a.BP_STRAND_ID = b.STRAND_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT a.FP_STRAND_ID,a.FP_STRAND_NAME,a.FP_STRAND_COLOR,a.FP_STRAND_NB,a.FP_FIBER_ID,a.FP_FIBER_NAME,a.FP_TUBE_ID,a.FP_TUBE_NAME,b.STRAND_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a left join FIBER_STRANDS b on a.FP_STRAND_ID = b.STRAND_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT A.STRAND_ID,A.STRAND_NAME,A.STRAND_COLOR,A.STRAND_NUMBER,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_ID,C.TUBE_NAME,A.STRAND_NETWORK_LEVEL FROM (FIBER_STRANDS A LEFT JOIN FIBER_CABLES B on A.FIBER_CABLE_ID=B.FIBER_CABLE_ID) LEFT JOIN FIBER_TUBES C on A.TUBE_ID=C.TUBE_ID WHERE (A.SOURCE_ID='"
								+ distBoardSel + "' OR A.DESTINATION_ID='" + distBoardSel
								+ "') and (STRAND_NETWORK_LEVEL = 'metro')" + " UNION "
								+ "Select DISTINCT A.STRAND_ID,B.STRAND_NAME,B.STRAND_COLOR,B.STRAND_NUMBER,C.FIBER_CABLE_ID,C.FIBER_CABLE_NAME,D.TUBE_ID,D.TUBE_NAME,B.STRAND_NETWORK_LEVEL FROM (STRAND_AUXILIARY_POINTS A LEFT JOIN  FIBER_STRANDS B on A.STRAND_ID = B.STRAND_ID) LEFT JOIN FIBER_CABLES C on A.FIBER_CABLE_ID=C.FIBER_CABLE_ID LEFT JOIN FIBER_TUBES D on B.TUBE_ID=D.TUBE_ID WHERE (A.AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'metro')")
						.list();
				rtn.put("StrandMetroData", StrandMetroData);

				/* Access data for show Path */
				List<Object[]> CableAccessData = session.createSQLQuery(
						"Select DISTINCT a.BP_FIBER_ID,a.BP_FIBER_NAME,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN  FIBER_CABLES b on a.BP_FIBER_ID = b.FIBER_CABLE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT a.FP_FIBER_ID,a.FP_FIBER_NAME,b.FIBER_NETWORK_LEVEL,b.FIBER_OWNER FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN  FIBER_CABLES b on a.FP_FIBER_ID = b.FIBER_CABLE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT FIBER_CABLE_ID,FIBER_CABLE_NAME,FIBER_NETWORK_LEVEL,FIBER_OWNER FROM FIBER_CABLES WHERE (SOURCE_ID='"
								+ distBoardSel + "' OR DESTINATION_ID='" + distBoardSel
								+ "') and (FIBER_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT A.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,B.FIBER_NETWORK_LEVEL,B.FIBER_OWNER FROM FIBER_AUXILIARY_POINTS A LEFT JOIN  FIBER_CABLES B on A.FIBER_CABLE_ID = B.FIBER_CABLE_ID WHERE (AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (FIBER_NETWORK_LEVEL = 'access')")
						.list();
				rtn.put("CableAccessData", CableAccessData);

				List<Object[]> TubeAccessData = session.createSQLQuery(
						"Select DISTINCT a.BP_TUBE_ID,a.BP_TUBE_NAME,a.BP_TUBE_COLOR,a.BP_TUBE_NB,a.BP_FIBER_ID,a.BP_FIBER_NAME,b.TUBE_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN FIBER_TUBES b on a.BP_TUBE_ID = b.TUBE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT a.FP_TUBE_ID,a.FP_TUBE_NAME,a.FP_TUBE_COLOR,a.FP_TUBE_NB,a.FP_FIBER_ID,a.FP_FIBER_NAME,b.TUBE_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a LEFT JOIN FIBER_TUBES b on a.FP_TUBE_ID = b.TUBE_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT A.TUBE_ID,A.TUBE_NAME,A.TUBE_COLOR,A.TUBE_NUMBER,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,A.TUBE_NETWORK_LEVEL FROM (FIBER_TUBES A LEFT JOIN FIBER_CABLES B on A.FIBER_CABLE_ID=B.FIBER_CABLE_ID) WHERE (A.SOURCE_ID='"
								+ distBoardSel + "' OR A.DESTINATION_ID='" + distBoardSel
								+ "') and (TUBE_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT A.TUBE_ID,B.TUBE_NAME,B.TUBE_COLOR,B.TUBE_NUMBER,A.FIBER_CABLE_ID,C.FIBER_CABLE_NAME,B.TUBE_NETWORK_LEVEL FROM (TUBE_AUXILIARY_POINTS A LEFT JOIN  FIBER_TUBES B on A.TUBE_ID = B.TUBE_ID) LEFT JOIN FIBER_CABLES C on A.FIBER_CABLE_ID=C.FIBER_CABLE_ID  WHERE (A.AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (TUBE_NETWORK_LEVEL = 'access')")
						.list();
				rtn.put("TubeAccessData", TubeAccessData);

				List<Object[]> StrandAccessData = session.createSQLQuery(
						"Select DISTINCT a.BP_STRAND_ID,a.BP_STRAND_NAME,a.BP_STRAND_COLOR,a.BP_STRAND_NB,a.BP_FIBER_ID,a.BP_FIBER_NAME,a.BP_TUBE_ID,a.BP_TUBE_NAME,b.STRAND_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a left join FIBER_STRANDS b on a.BP_STRAND_ID = b.STRAND_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT a.FP_STRAND_ID,a.FP_STRAND_NAME,a.FP_STRAND_COLOR,a.FP_STRAND_NB,a.FP_FIBER_ID,a.FP_FIBER_NAME,a.FP_TUBE_ID,a.FP_TUBE_NAME,b.STRAND_NETWORK_LEVEL FROM (DISTRIBUTION_BOARD_MAPPING a left join FIBER_STRANDS b on a.FP_STRAND_ID = b.STRAND_ID) WHERE (DB_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT A.STRAND_ID,A.STRAND_NAME,A.STRAND_COLOR,A.STRAND_NUMBER,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_ID,C.TUBE_NAME,A.STRAND_NETWORK_LEVEL FROM (FIBER_STRANDS A LEFT JOIN FIBER_CABLES B on A.FIBER_CABLE_ID=B.FIBER_CABLE_ID) LEFT JOIN FIBER_TUBES C on A.TUBE_ID=C.TUBE_ID WHERE (A.SOURCE_ID='"
								+ distBoardSel + "' OR A.DESTINATION_ID='" + distBoardSel
								+ "') and (STRAND_NETWORK_LEVEL = 'access')" + " UNION "
								+ "Select DISTINCT A.STRAND_ID,B.STRAND_NAME,B.STRAND_COLOR,B.STRAND_NUMBER,C.FIBER_CABLE_ID,C.FIBER_CABLE_NAME,D.TUBE_ID,D.TUBE_NAME,B.STRAND_NETWORK_LEVEL FROM (STRAND_AUXILIARY_POINTS A LEFT JOIN  FIBER_STRANDS B on A.STRAND_ID = B.STRAND_ID) LEFT JOIN FIBER_CABLES C on A.FIBER_CABLE_ID=C.FIBER_CABLE_ID LEFT JOIN FIBER_TUBES D on B.TUBE_ID=D.TUBE_ID WHERE (A.AUXILIARY_POINT_ID='"
								+ distBoardSel + "') and (STRAND_NETWORK_LEVEL = 'access')")
						.list();
				rtn.put("StrandAccessData", StrandAccessData);

			}

			catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in pathDistBoard due to \n " + exceptionAsString);
				logger.info("Error in pathDistBoard due to \n " + exceptionAsString);
				rtn.put("CableBackboneData", null);
				rtn.put("TubeBackboneData", null);
				rtn.put("StrandBackboneData", null);
				rtn.put("CableMetroData", null);
				rtn.put("TubeMetroData", null);
				rtn.put("StrandMetroData", null);
				rtn.put("CableAccessData", null);
				rtn.put("TubeAccessData", null);
				rtn.put("StrandAccessData", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
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

		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				List<Object[]> TrenchData = session.createSQLQuery(
						"SELECT SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LATITUDE,SOURCE_LONGITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY,LENGTH,NUM_DUCTS,TRENCH_NAME FROM TRENCH WHERE TRENCH_ID='"
								+ trenchID + "' and PROJECT_ID='" + projectID + "'")
						.list();

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

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/saveProject", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> saveProject(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);
			String ProjectId = "";

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {

					if (request.getParameter("actionProjectContext").equals("Insert")) {
						System.out.println(
								"actionProjectContext insidde insert>>" + request.getParameter("actionProjectContext"));
						synchronized (this) {
							// ProjectId = "PROJECT_" + year + "_" + appConfig.getSeqNbr(71,session);
							ProjectId = "PROJECT_" + year + "_" + Integer.parseInt(
									session.createSQLQuery("SELECT PROJECT FROM SEQ_TABLE").uniqueResult().toString());
							System.out.println("the project id is " + ProjectId);
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET PROJECT = PROJECT + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
						}
						System.out.println("all gd till here>>" + ProjectId);
						query = session.createSQLQuery("INSERT INTO PROJECT VALUES ('" + ProjectId + "','"
								+ request.getParameter("ProjectName") + "')");
						query.executeUpdate();
						rtn.put("ProjectId", ProjectId);
					} else {
						ProjectId = request.getParameter("ProjectId");
						query = session
								.createSQLQuery("UPDATE PROJECT SET PROJECT_ID= '" + request.getParameter("ProjectId")
										+ "',PROJECT_NAME= '" + request.getParameter("ProjectName")
										+ "' where PROJECT_ID='" + request.getParameter("ProjectId") + "'");
						query.executeUpdate();
						rtn.put("ProjectId", request.getParameter("ProjectId"));

					}
					// String projectNameId=ProjectId+":"+request.getParameter("ProjectName");
					rtn.put("ProjectName", request.getParameter("ProjectName"));

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

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/saveManhole", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> saveManhole(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);
			String manholeNameId, manholeId = "", manholeName = "", city;
			List countCables;

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				System.out.println("project id is " + request.getParameter("ProjectId"));

				try {

					Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

					String manholeCreatedDate = request.getParameter("manholeCreatedDate");
					Timestamp manholeCreationDate;
					if (StringUtils.equalsIgnoreCase(manholeCreatedDate, "")) {
						manholeCreationDate = lastModifiedDate;
					} else {
						DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
						manholeCreationDate = new Timestamp(
								formatter.parse(request.getParameter("manholeCreatedDate")).getTime());
					}

					if (request.getParameter("actionManholeContext").equals("Insert")) {
						synchronized (this) {

							manholeId = "MH_" + year + "_" + Integer.parseInt(
									session.createSQLQuery("SELECT MANHOLE FROM SEQ_TABLE").uniqueResult().toString());
							String[] idSplit;
							idSplit = manholeId.split("_");

							if (request.getParameter("ManholeName").isEmpty()) {
								manholeName = "MH_" + request.getParameter("ManholeCity") + "_" + idSplit[1] + "_"
										+ idSplit[2];
								Query junction = session.createSQLQuery(
										"select count(*) from manhole where manhole.manhole_id IN  (select junction.physical_layer_id  from junction where junction.physical_layer_id='"
												+ manholeId + "')");
								junction.executeUpdate();
								System.out.println(junction.uniqueResult().toString());
								if (Integer.parseInt(junction.uniqueResult().toString()) > 0) {
									manholeName += "_J";
								}
								System.out.print(manholeName);
							} else {
								manholeName = request.getParameter("ManholeName");
							}
							System.out.println("man id " + manholeId);
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET MANHOLE =  MANHOLE +1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
						}
						// replacing the single quote with nothing to avoid problems in database
						manholeName = manholeName.replace("'", "");
						city = request.getParameter("ManholeCity").replace("'", "");

						query = session.createSQLQuery(
								"INSERT INTO MANHOLE(MANHOLE_ID, MANHOLE_NAME, MANHOLE_MODEL, LONGITUDE, LATITUDE, CITY, PROJECT_ID, DM_NAME,CREATION_DATE,LAST_MODIFIED_DATE) VALUES ('"
										+ manholeId + "','" + manholeName + "','" + request.getParameter("ManholeModel")
										+ "','" + request.getParameter("ManholeLong") + "','"
										+ request.getParameter("ManholeLat") + "','" + city + "','"
										+ request.getParameter("ProjectId") + "','" + null + "',TIMESTAMP '"
										+ manholeCreationDate + "',TIMESTAMP '" + lastModifiedDate + "')");
						query.executeUpdate();
						rtn.put("ManholeId", manholeId);
						rtn.put("ManholeName", manholeName);

					} else {
						manholeId = request.getParameter("ManholeId");
						String[] idSplit;
						idSplit = manholeId.split("_");

						if (request.getParameter("ManholeName").isEmpty()) {
							manholeName = "MH_" + request.getParameter("ManholeCity") + "_" + idSplit[1] + "_"
									+ idSplit[2];
							Query junction = session.createSQLQuery(
									"select count(*) from manhole where manhole.manhole_id IN  (select junction.physical_layer_id  from junction where junction.physical_layer_id='"
											+ manholeId + "')");
							junction.executeUpdate();
							System.out.println(junction.uniqueResult().toString());
							if (Integer.parseInt(junction.uniqueResult().toString()) > 0) {
								manholeName += "_J";
							}
							System.out.print(manholeName);
						} else {
							manholeName = request.getParameter("ManholeName");
						}
						// replacing the single quote with nothing to avoid problems in database
						manholeName = manholeName.replace("'", "");
						city = request.getParameter("ManholeCity").replace("'", "");

						query = session.createSQLQuery("UPDATE MANHOLE SET MANHOLE_ID= '"
								+ request.getParameter("ManholeId") + "',MANHOLE_NAME= '" + manholeName
								+ "',MANHOLE_MODEL= '" + request.getParameter("ManholeModel") + "',LONGITUDE= '"
								+ request.getParameter("ManholeLong") + "',LATITUDE= '"
								+ request.getParameter("ManholeLat") + "',CITY= '" + city + "',PROJECT_ID='"
								+ request.getParameter("ProjectId") + "',LAST_MODIFIED_DATE= TIMESTAMP '"
								+ lastModifiedDate + "' where MANHOLE_ID='" + request.getParameter("ManholeId") + "'");
						query.executeUpdate();
						rtn.put("ManholeId", request.getParameter("ManholeId"));
						rtn.put("ManholeName", manholeName);

						Query updateJunction = session.createSQLQuery(
								"UPDATE JUNCTION SET PHYSICAL_LAYER_ID= '" + request.getParameter("ManholeId")
										+ "',PHYSICAL_LAYER_NAME= '" + request.getParameter("ManholeName")
										+ "',LONGITUDE= '" + Float.parseFloat(request.getParameter("ManholeLong"))
										+ "',LATITUDE= '" + Float.parseFloat(request.getParameter("ManholeLat"))
										+ "',CITY= '" + request.getParameter("ManholeCity") + "',PROJECT_ID='"
										+ request.getParameter("ProjectId") + "' where PHYSICAL_LAYER_ID='"
										+ request.getParameter("ManholeId") + "'");
						updateJunction.executeUpdate();

					}

					manholeNameId = manholeId + ":" + manholeName;

					countCables = session.createSQLQuery(
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
							.list();
					rtn.put("countCables", countCables);
					// rtn.put("ManholeName", manholeName);

					List<Object[]> countJct = session.createSQLQuery(
							"select JUNCTION_ID,JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME,JUNCTION_NUMBER,CAPACITY,CITY,LONGITUDE,LATITUDE,count(*) from JUNCTION  where PHYSICAL_LAYER_ID = '"
									+ request.getParameter("ManholeId")
									+ "' group by JUNCTION_ID,JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME,JUNCTION_NUMBER,CAPACITY,CITY,LONGITUDE,LATITUDE ")
							.list();
					rtn.put("countJct", countJct);

					List<Object[]> junctionTotalCount = session
							.createSQLQuery("select coalesce(count(*),0) from JUNCTION  where PHYSICAL_LAYER_ID = '"
									+ request.getParameter("ManholeId") + "' ")
							.list();
					rtn.put("junctionTotalCount", junctionTotalCount);

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in saveManhole due to \n " + exceptionAsString);
					logger.info("Error in saveManhole due to \n " + exceptionAsString);
					rtn.put("ManholeId", null);
					rtn.put("ManholeName", null);
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

	@RequestMapping(value = "/saveHandhole", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> saveHandhole(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

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

				if (request.getParameter("actionHandholeContext").equals("Insert")) {

					synchronized (this) {
						// String numSeq = appConfig.getSequenceNbr(50);

						handholeId = "HH_" + year + "_" + Integer.parseInt(
								session.createSQLQuery("SELECT HANDHOLE FROM SEQ_TABLE").uniqueResult().toString());
						System.out.println("han id " + handholeId);
						String[] idSplit;
						idSplit = handholeId.split("_");

						if (request.getParameter("HandholeName").isEmpty()) {
							handholeName = "HH_" + request.getParameter("HandholeCity") + "_" + idSplit[1] + "_"
									+ idSplit[2];
							Query junction = session.createSQLQuery(
									"select count(*) from handhole where handhole.handhole_id IN  (select junction.physical_layer_id  from junction where junction.physical_layer_id='"
											+ handholeId + "')");
							junction.executeUpdate();
							System.out.println(junction.uniqueResult().toString());
							if (Integer.parseInt(junction.uniqueResult().toString()) > 0) {
								handholeName += "_J";
							}
							System.out.print(handholeName);
						} else {
							handholeName = request.getParameter("HandholeName");
						}
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET HANDHOLE =  HANDHOLE +1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
					}

					Query InsertHandhole = session.createSQLQuery(
							"INSERT INTO HANDHOLE(HANDHOLE_ID, HANDHOLE_NAME, HANDHOLE_MODEL, LONGITUDE, LATITUDE, CITY, PROJECT_ID, DM_NAME,CREATION_DATE,LAST_MODIFIED_DATE) VALUES ('"
									+ handholeId + "','" + handholeName + "','" + request.getParameter("HandholeModel")
									+ "','" + request.getParameter("HandholeLong") + "','"
									+ request.getParameter("HandholeLat") + "','" + request.getParameter("HandholeCity")
									+ "','" + request.getParameter("ProjectId") + "','" + null + "',TIMESTAMP '"
									+ handholeCreationDate + "',TIMESTAMP '" + lastModifiedDate + "')");
					InsertHandhole.executeUpdate();
					rtn.put("handholeId", handholeId);
					rtn.put("handholeName", handholeName);

				} else {

					handholeId = request.getParameter("handholeId");
					String[] idSplit;
					idSplit = handholeId.split("_");

					if (request.getParameter("HandholeName").isEmpty()) {
						handholeName = "HH_" + request.getParameter("HandholeCity") + "_" + idSplit[1] + "_"
								+ idSplit[2];
						Query junction = session.createSQLQuery(
								"select count(*) from handhole where handhole.handhole_id IN  (select junction.physical_layer_id  from junction where junction.physical_layer_id='"
										+ handholeId + "')");
						junction.executeUpdate();
						System.out.println(junction.uniqueResult().toString());
						if (Integer.parseInt(junction.uniqueResult().toString()) > 0) {
							handholeName += "_J";
						}
						System.out.print(handholeName);
					}

					else {
						handholeName = request.getParameter("HandholeName");
					}

					System.out.println("handhole " + request.getParameter("handholeId"));
					Query updateHandhole = session.createSQLQuery("UPDATE HANDHOLE SET HANDHOLE_ID= '" + handholeId
							+ "',HANDHOLE_NAME= '" + handholeName + "',HANDHOLE_MODEL= '"
							+ request.getParameter("HandholeModel") + "',LONGITUDE= '"
							+ request.getParameter("HandholeLong") + "',LATITUDE= '"
							+ request.getParameter("HandholeLat") + "',CITY= '" + request.getParameter("HandholeCity")
							+ "',PROJECT_ID='" + request.getParameter("ProjectId") + "',LAST_MODIFIED_DATE= TIMESTAMP '"
							+ lastModifiedDate + "' where HANDHOLE_ID='" + request.getParameter("handholeId") + "'");
					updateHandhole.executeUpdate();
					rtn.put("handholeId", request.getParameter("handholeId"));
					rtn.put("handholeName", handholeName);

					query = session.createSQLQuery("UPDATE JUNCTION SET PHYSICAL_LAYER_ID= '" + handholeId
							+ "',PHYSICAL_LAYER_NAME= '" + request.getParameter("HandholeName") + "',LONGITUDE= '"
							+ Float.parseFloat(request.getParameter("HandholeLong")) + "',LATITUDE= '"
							+ Float.parseFloat(request.getParameter("HandholeLat")) + "',CITY= '"
							+ request.getParameter("HandholeCity") + "',PROJECT_ID='"
							+ request.getParameter("ProjectId") + "' where PHYSICAL_LAYER_ID='"
							+ request.getParameter("handholeId") + "'");
					query.executeUpdate();
				}

				String handholeNameId = handholeId + ":" + handholeName;

				List countCables = session.createSQLQuery(
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
						.list();

				System.out.println("countCables " + countCables);
				rtn.put("countCables", countCables);
				// rtn.put("HandholeName", request.getParameter("HandholeName"));

				List<Object[]> countJct = session.createSQLQuery(
						"select JUNCTION_ID,JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME,JUNCTION_NUMBER,CAPACITY,CITY,LONGITUDE,LATITUDE,count(*) from JUNCTION  where PHYSICAL_LAYER_ID = '"
								+ request.getParameter("handholeId")
								+ "' group by JUNCTION_ID,JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME,JUNCTION_NUMBER,CAPACITY,CITY,LONGITUDE,LATITUDE ")
						.list();
				rtn.put("countJct", countJct);

				List<Object[]> junctionTotalCount = session
						.createSQLQuery("select coalesce(count(*),0) from JUNCTION  where PHYSICAL_LAYER_ID = '"
								+ request.getParameter("handholeId") + "' ")
						.list();
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
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			List<Object> allPhysicalNodesCount = session.createSQLQuery(
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
					.list();

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
		session = almsessions.getSession();

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
							query = session.createSQLQuery(queryStmnt);

							if (query.list().size() == 0) {
								queryStmnt = "SELECT HANDHOLE_ID || ':' || HANDHOLE_NAME FROM HANDHOLE WHERE longitude = '"
										+ newLongitude + "' AND  latitude = ' " + newLatitude
										+ "' FETCH FIRST 1 ROWS ONLY";
								query = session.createSQLQuery(queryStmnt);
							}

						} else if (lngAfterDecimal.length() >= 6 && latAfterDecimal.length() >= 6) {
							lngAfterDecimal = lngAfterDecimal.substring(0, lngAfterDecimal.length() - 1);
							newLongitude = lngBeforeDecimal + "." + lngAfterDecimal;
							latAfterDecimal = latAfterDecimal.substring(0, latAfterDecimal.length() - 1);
							newLatitude = latBeforeDecimal + "." + latAfterDecimal;

							queryStmnt = "select MANHOLE_ID || ':' || MANHOLE_NAME from MANHOLE WHERE longitude LIKE '"
									+ newLongitude + "%' AND latitude LIKE '" + newLatitude
									+ "%'  FETCH FIRST 1 ROWS ONLY ";
							query = session.createSQLQuery(queryStmnt);

							if (query.list().size() == 0) {
								queryStmnt = "select HANDHOLE_ID || ':' || HANDHOLE_NAME from HANDHOLE WHERE longitude LIKE '"
										+ newLongitude + "%' AND latitude LIKE '" + newLatitude
										+ "%' FETCH FIRST 1 ROWS ONLY ";
								query = session.createSQLQuery(queryStmnt);
							}
						}
						List<Object> resultList = query.list();
						if (resultList.size() == 1 || resultList.size() == 0) {
							String AuxName = (String) query.uniqueResult();
							map.put(i, new ArrayList<>(Arrays.asList(
									itemParameters.getDictParameter().get(i).get("longitude"),
									itemParameters.getDictParameter().get(i).get("latitude"), "", AuxName, "", "")));

						} else if (resultList.size() > 1) {
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
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedFiberContext = request.getParameter("ID");

			try {
				Map<Object, List<Object>> map = new HashMap<>();
				String queryy = "";
				List<Object[]> queryStmnt = session.createSQLQuery(
						"select AUXILIARY_ID,LONGITUDE,LATITUDE,AUXILIARY_POINT_NAME from FIBER_AUXILIARY_POINTS where FIBER_CABLE_ID ='"
								+ selectedFiberContext + "'")
						.list();

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
						query = session.createSQLQuery(queryy);

						if (query.list().size() == 0) {
							queryy = "SELECT HANDHOLE_ID || ':' || HANDHOLE_NAME FROM HANDHOLE WHERE longitude = '"
									+ newLongitude + "' AND latitude = '" + newLatitude + "' FETCH FIRST 1 ROWS ONLY";
							query = session.createSQLQuery(queryy);
						}
					} else if (lngAfterDecimal.length() >= 6 || latAfterDecimal.length() >= 6) {
						lngAfterDecimal = lngAfterDecimal.substring(0, lngAfterDecimal.length() - 1);
						newLongitude = lngBeforeDecimal + "." + lngAfterDecimal;
						latAfterDecimal = latAfterDecimal.substring(0, latAfterDecimal.length() - 1);
						newLatitude = latBeforeDecimal + "." + latAfterDecimal;

						queryy = "select MANHOLE_ID || ':' || MANHOLE_NAME from MANHOLE WHERE longitude LIKE '"
								+ newLongitude + "%' AND latitude LIKE '" + newLatitude + "%' FETCH FIRST 1 ROWS ONLY ";
						query = session.createSQLQuery(queryy);

						if (query.list().size() == 0) {
							queryy = "select HANDHOLE_ID || ':' || HANDHOLE_NAME from HANDHOLE WHERE longitude LIKE '"
									+ newLongitude + "%' AND latitude LIKE '" + newLatitude
									+ "%' FETCH FIRST 1 ROWS ONLY ";
							query = session.createSQLQuery(queryy);
						}
					}

					if (query.list().size() == 1 || query.list().size() == 0) {
						AuxName = (String) query.uniqueResult();
						List<Object> values = new ArrayList<>();

						Arrays.asList(values.add("longitude"));
						Arrays.asList(values.add("latitude"));
						Arrays.asList(values.add(""));
						Arrays.asList(values.add(AuxName));
						Arrays.asList(values.add(""));
						Arrays.asList(values.add(""));
						map.put("longitude" + "latitude", values);

					} else if (query.list().size() > 1) {
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

		TreeMap<Object, Object> sortedMap = new TreeMap<>();
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			String selectedFiberContext = request.getParameter("ID");
			try {
				Map<Object, List<Object>> map = new HashMap<>();

				List<Object[]> listAux = session.createSQLQuery(
						"select AUXILIARY_ID,LONGITUDE,LATITUDE,AUXILIARY_POINT_NAME from FIBER_AUXILIARY_POINTS where FIBER_CABLE_ID ='"
								+ selectedFiberContext + "'")
						.list();

				for (Object[] row : listAux) {
					String auxiliaryId = row[0].toString();
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

					if (lngAfterDecimal.length() > 3 && latAfterDecimal.length() > 3) {

						lngAfterDecimal = lngAfterDecimal.substring(0, lngAfterDecimal.length() - 1);
						newLongitude = lngBeforeDecimal + "." + lngAfterDecimal;
						latAfterDecimal = latAfterDecimal.substring(0, latAfterDecimal.length() - 1);
						newLatitude = latBeforeDecimal + "." + latAfterDecimal;

						String manhoelData = "select MANHOLE_ID || ':' || MANHOLE_NAME from MANHOLE WHERE longitude LIKE '"
								+ newLongitude + "%' AND latitude LIKE '" + newLatitude + "%' FETCH FIRST 1 ROWS ONLY ";
						query = session.createSQLQuery(manhoelData);

						List<String> manholeResult = query.list();
						if (manholeResult != null && !manholeResult.isEmpty()) {
							String concatenatedManholeId = manholeResult.get(0);
							String[] manholeParts = concatenatedManholeId.split(":");
							String manholeId = manholeParts[0];

							if (!manholeId.equals(auxiliaryId) && !manholeId.equals(manholeId)) {
								AuxName = (String) query.uniqueResult();
								map.put(selectedFiberContext,
										new ArrayList<>(Arrays.asList(longitude, latitude, "", AuxName, "", "")));
							}
						}

						String handholeData = "select HANDHOLE_ID || ':' || HANDHOLE_NAME from HANDHOLE WHERE longitude LIKE '"
								+ newLongitude + "%' AND latitude LIKE '" + newLatitude + "%' FETCH FIRST 1 ROWS ONLY ";
						query = session.createSQLQuery(handholeData);

						List<String> handholeResult = query.list();
						if (handholeResult != null && !handholeResult.isEmpty()) {
							String concatenatedHandholeId = handholeResult.get(0);
							String[] handholeParts = concatenatedHandholeId.split(":");
							String handholeId = handholeParts[0];

							if (!handholeId.equals(auxiliaryId) && !handholeId.equals(handholeId)) {
								AuxName = (String) query.uniqueResult();
								map.put(selectedFiberContext,
										new ArrayList<>(Arrays.asList(longitude, latitude, "", AuxName, "", "")));
							}
						}
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
	@RequestMapping(value = "/fiberPathSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fiberPathSave(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
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

			try {

				fibercable = new FiberCable();
				if (StringUtils.equalsIgnoreCase(fiberpathID, "")) {
					synchronized (this) {
						// fiberpathID = "FIBER" + year + "_" + appConfig.getSeqNbr(51,session);
						fiberpathID = "FIBER" + year + "_" + Integer.parseInt(
								session.createSQLQuery("SELECT FIBER_CABLE FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET FIBER_CABLE = FIBER_CABLE + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
					}
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
				} else if (source.contains("CLT") == true) {
					fibercable.setSourceWareID("null");
					fibercable.setSourceID(source.split(":")[0]);
					fibercable.setSourceName(source.split(":")[1] + ":" + source.split(":")[2]);
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
				} else if (destination.contains("CLT") == true) {
					fibercable.setDestinationWareID("null");
					fibercable.setDestinationID(destination.split(":")[0]);
					fibercable.setDestinationName(destination.split(":")[1] + ":" + destination.split(":")[2]);
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
				 * query = session.createSQLQuery(
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

				Query updateMappingJctSideAQuery = session
						.createSQLQuery("UPDATE JUNCTION_MAPPING SET FIBER_NAME_SIDE_A = '" + fiberName
								+ "' WHERE FIBER_ID_SIDE_A = '" + fiberpathID + "' ");
				updateMappingJctSideAQuery.executeUpdate();

				Query updateMappingJctSideBQuery = session
						.createSQLQuery("UPDATE JUNCTION_MAPPING SET FIBER_NAME_SIDE_B = '" + fiberName
								+ "' WHERE FIBER_ID_SIDE_B = '" + fiberpathID + "' ");
				updateMappingJctSideBQuery.executeUpdate();

				String fiberAuxFlag = request.getParameter("fiberAuxFlag");
				if (StringUtils.equalsIgnoreCase(fiberAuxFlag, "opened")
						|| StringUtils.equalsIgnoreCase(fiberAuxFlag, "new cable")) {

					System.out.println("Inside " + fiberAuxFlag);
					query = session.createSQLQuery(
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
												session.createSQLQuery("SELECT FIBER_CABLE_AUX FROM SEQ_TABLE")
														.uniqueResult().toString());
								query = session
										.createSQLQuery("UPDATE SEQ_TABLE SET FIBER_CABLE_AUX = FIBER_CABLE_AUX + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
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

						}
					}
				}

				// added junction
				query = session.createSQLQuery(
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
												session.createSQLQuery("SELECT ACCESS_JUNCTIONS FROM SEQ_TABLE")
														.uniqueResult().toString());
								query = session.createSQLQuery(
										"UPDATE SEQ_TABLE SET ACCESS_JUNCTIONS = ACCESS_JUNCTIONS + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
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
					}

				}

				query = session.createSQLQuery("delete from FIBER_TUBES where FIBER_CABLE_ID='" + fiberpathID + "'");
				query.executeUpdate();
				query = session.createSQLQuery("delete from FIBER_STRANDS where FIBER_CABLE_ID='" + fiberpathID + "'");
				query.executeUpdate();
				query = session
						.createSQLQuery("delete from TUBE_AUXILIARY_POINTS where FIBER_CABLE_ID='" + fiberpathID + "'");
				query.executeUpdate();
				query = session.createSQLQuery(
						"delete from STRAND_AUXILIARY_POINTS where FIBER_CABLE_ID='" + fiberpathID + "'");
				query.executeUpdate();

				float strandSource_Longitude = 0;
				float strandSource_Latitude = 0;
				float strandDest_Longitude = 0;
				float strandDest_Latitude = 0;
				String strandSource = "";
				String strandDestination = "";
				String strand_name = "";
				String strandowner = "";
				String strandnetlevel = "";
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

					if (itemParameters.getDictParameterStrands().get(i).get("strandSource_Longitude") != "") {
						strandSource_Longitude = Float.parseFloat(
								itemParameters.getDictParameterStrands().get(i).get("strandSource_Longitude"));
					}

					if (itemParameters.getDictParameterStrands().get(i).get("strandSource_Latitude") != "") {
						strandSource_Latitude = Float.parseFloat(
								itemParameters.getDictParameterStrands().get(i).get("strandSource_Latitude"));
					}

					if (itemParameters.getDictParameterStrands().get(i).get("strandDestination_Longitude") != "") {
						strandDest_Longitude = Float.parseFloat(
								itemParameters.getDictParameterStrands().get(i).get("strandDestination_Longitude"));
					}

					if (itemParameters.getDictParameterStrands().get(i).get("strandDestination_Latitude") != "") {
						strandDest_Latitude = Float.parseFloat(
								itemParameters.getDictParameterStrands().get(i).get("strandDestination_Latitude"));
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

					if (itemParameters.getDictParameterStrands().get(i).get("strandnetlevel") != "") {
						strandnetlevel = itemParameters.getDictParameterStrands().get(i).get("strandnetlevel");
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
					} else if (strandSource.contains("CLT") == true) {
						sourceWareId = "null";
						sourceId = strandSource.split(":")[0];
						sourceName = strandSource.split(":")[1] + ":" + strandSource.split(":")[2];
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
					} else if (strandDestination.contains("CLT") == true) {
						dstWareId = "null";
						dstId = strandDestination.split(":")[0];
						dstName = strandDestination.split(":")[1] + ":" + strandDestination.split(":")[2];
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
				}

				String tubeSource = "";
				String tubeowner = "";
				String tubenetlevel = "";
				String tubedeployment = "";
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
					} else if (tubeSource.contains("CLT") == true) {
						sourceWareId = "null";
						sourceId = tubeSource.split(":")[0];
						sourceName = tubeSource.split(":")[1] + ":" + tubeSource.split(":")[2];
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
					} else if (tubeDestination.contains("CLT") == true) {
						dstWareId = "null";
						dstId = tubeDestination.split(":")[0];
						dstName = tubeDestination.split(":")[1] + ":" + tubeDestination.split(":")[2];
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

					String strandsInTubeCount = session
							.createSQLQuery("SELECT count(*) FROM FIBER_STRANDS b WHERE b.TUBE_ID='"
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

				strandsOfTubes = session.createSQLQuery(
						"SELECT c.STRAND_ID,c.SOURCE_LONGITUDE,c.SOURCE_LATITUDE,c.DESTINATION_LONGITUDE,c.DESTINATION_LATITUDE,c.SOURCE_WARE_ID,c.SOURCE_ID,c.SOURCE_NAME,c.DESTINATION_WARE_ID,c.DESTINATION_ID,c.DESTINATION_NAME,c.TUBE_ID,c.FIBER_CABLE_ID,c.STRAND_NAME,c.DRAWING_TYPE,c.STRAND_NUMBER,c.STRAND_COLOR FROM FIBER_STRANDS c,FIBER_TUBES b,FIBER_CABLES a WHERE c.TUBE_ID=b.TUBE_ID and b.FIBER_CABLE_ID=a.FIBER_CABLE_ID and c.FIBER_CABLE_ID='"
								+ fiberpathID + "'")
						.list();

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
								.createSQLQuery("SELECT FIBER_TUBE_AUX FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET FIBER_TUBE_AUX = FIBER_TUBE_AUX + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
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
								.createSQLQuery("SELECT FIBER_STRAND_AUX FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET FIBER_STRAND_AUX = FIBER_STRAND_AUX + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
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

				}

				////////////////////////////////////////// end of auxiliary tubes insert

				List<Object[]> fiberTubesList = session.createSQLQuery(
						"SELECT a.TUBE_ID,a.SOURCE_LONGITUDE,a.SOURCE_LATITUDE,a.DESTINATION_LONGITUDE,a.DESTINATION_LATITUDE,(SELECT count(*) FROM FIBER_STRANDS b WHERE b.TUBE_ID=a.TUBE_ID),a.FIBER_CABLE_ID,a.SOURCE_WARE_ID,a.SOURCE_ID,a.SOURCE_NAME,a.DESTINATION_WARE_ID,a.DESTINATION_ID,a.DESTINATION_NAME,a.TUBE_NAME FROM FIBER_TUBES a WHERE a.FIBER_CABLE_ID='"
								+ fiberpathID + "'")
						.list();

				List<Object[]> fiberStrands = session.createSQLQuery(
						"SELECT STRAND_ID,SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,TUBE_ID,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,STRAND_NAME FROM FIBER_STRANDS WHERE FIBER_CABLE_ID='"
								+ fiberpathID + "'")
						.list();

				List<Object[]> fiberAux = session.createSQLQuery(
						"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.SEQ_SORTING,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID ='"
								+ fiberpathID + "' ORDER BY B.SEQ_SORTING ASC")
						.list();

				rtn.put("FiberPathId", fiberpathID);
				rtn.put("tubeIdArrayList", tubeIdArrayList);
				rtn.put("strandsOfTubes", strandsOfTubes);
				rtn.put("fiberAux", fiberAux);

				rtn.put("tubeIdArray", tubeIdArray);
				rtn.put("fiberTubes", fiberTubesList);
				rtn.put("fiberStrands", fiberStrands);

			} catch (Exception e) {
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
					tx.commit();
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

		session = almsessions.getSession();
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
							session.createSQLQuery("SELECT FIBER_TUBE FROM SEQ_TABLE").uniqueResult().toString());
					// System.out.println("hii tubeee");
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET FIBER_TUBE = FIBER_TUBE + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createStrandId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> createStrandId(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = almsessions.getSession();
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
							session.createSQLQuery("SELECT FIBER_STRAND FROM SEQ_TABLE").uniqueResult().toString());
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET FIBER_STRAND = FIBER_STRAND + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
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

		session = almsessions.getSession();
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

				query = session.createSQLQuery("delete from FIBER_AUXILIARY_POINTS where FIBER_CABLE_ID='" + fiberpathID
						+ "' and AUXILIARY_POINT_ID IN (:param1) ");

				query.setParameterList("param1", markersArray);
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

		session = almsessions.getSession();
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
				DistributionBoard distributionBoard = new DistributionBoard();
				String distributionBoardId = "";
				distributionBoardId = request.getParameter("DistributionBoardId");
				if (StringUtils.equalsIgnoreCase(distributionBoardId, "")
						|| StringUtils.equalsIgnoreCase(distributionBoardId, null)) {
					synchronized (this) {

						// distributionBoardId = "DB_" + year + "_" + appConfig.getSeqNbr(52,session);
						distributionBoardId = "DB_" + year + "_" + Integer
								.parseInt(session.createSQLQuery("SELECT DB FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET DB = DB + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
					}
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
				distributionBoard.setdbNetLevel(request.getParameter("dbNetLevel"));
				distributionBoard.setDistributionBoardLat(request.getParameter("DistributionBoardLat"));
				distributionBoard.setDistributionBoardLong(request.getParameter("DistributionBoardLong"));
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
				query = session.createSQLQuery(
						"delete from DISTRIBUTION_BOARD_MAPPING where DB_ID = '" + distributionBoardId + "'");
				query.executeUpdate();
				rtn.put("distributionBoardId", distributionBoardId);

				//System.out.println("itemParam : " + itemParameters.getDictParameter());
				DistributionBoardMapping distributionBoardMapping;
				String db_Port_Id = "";
				if (itemParameters.getDictParameter().size() > 0) {

					for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
						distributionBoardMapping = new DistributionBoardMapping();
						//System.out.println("DB_Mapping : " + itemParameters.getDictParameter().get(i));
						//System.out.println("db_Port_Id : " + itemParameters.getDictParameter().get(i).get("portId"));
						if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get("portId"), "")
								|| StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get("portId"),
										null)) {
							synchronized (this) {
								// db_Port_Id = "DB_PORT_" + year + "_" + appConfig.getSeqNbr(62,session);
								db_Port_Id = "DB_PORT_" + year + "_" + Integer.parseInt(session
										.createSQLQuery("SELECT DB_PORT FROM SEQ_TABLE").uniqueResult().toString());
								query = session.createSQLQuery("UPDATE SEQ_TABLE SET DB_PORT = DB_PORT + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
								//System.out.println("the db port is " + db_Port_Id);
							}
						} else {
							db_Port_Id = itemParameters.getDictParameter().get(i).get("portId");
						}
						//System.out.println(itemParameters.getDictParameter().get(i).get("rowColIndex"));

						distributionBoardMapping.setDb_Port_Id(db_Port_Id);
						distributionBoardMapping.setDistributionBoardId(distributionBoardId);

						if (itemParameters.getDictParameter().get(i).get("rowColIndex") != null) {
							distributionBoardMapping
									.setRowColIndex(itemParameters.getDictParameter().get(i).get("rowColIndex"));
							//System.out.println("ln");
						} else {
							distributionBoardMapping.setRowColIndex(null);

						}

						distributionBoardMapping.setRowNum((itemParameters.getDictParameter().get(i).get("rowNum")));
						distributionBoardMapping.setColNum((itemParameters.getDictParameter().get(i).get("colNum")));
						distributionBoardMapping
								.setfP_Status(itemParameters.getDictParameter().get(i).get("fP_Status"));
						distributionBoardMapping
								.setfP_LocationType(itemParameters.getDictParameter().get(i).get("fP_LocationType"));
						distributionBoardMapping
								.setfP_LocationId(itemParameters.getDictParameter().get(i).get("fP_LocationID") != ""
										? itemParameters.getDictParameter().get(i).get("fP_LocationID")
										: "");
						distributionBoardMapping
								.setfP_LocationName(itemParameters.getDictParameter().get(i).get("fP_LocationM") != ""
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
						distributionBoardMapping
								.setfP_EquipmentId(itemParameters.getDictParameter().get(i).get("fP_EquipmentID") != ""
										? itemParameters.getDictParameter().get(i).get("fP_EquipmentID")
										: "");
						distributionBoardMapping
								.setfP_Address(itemParameters.getDictParameter().get(i).get("fP_Address") != ""
										? itemParameters.getDictParameter().get(i).get("fP_Address")
										: "");

						distributionBoardMapping
								.setfP_JunctionId(itemParameters.getDictParameter().get(i).get("fP_JunctionID") != ""
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
						distributionBoardMapping
								.setfP_StrandColor(itemParameters.getDictParameter().get(i).get("fP_StrandColor") != ""
										? itemParameters.getDictParameter().get(i).get("fP_StrandColor")
										: "");
						distributionBoardMapping
								.setfP_StrandId(itemParameters.getDictParameter().get(i).get("fP_StrandID") != ""
										? itemParameters.getDictParameter().get(i).get("fP_StrandID")
										: "");
						distributionBoardMapping
								.setfP_StrandName(itemParameters.getDictParameter().get(i).get("fP_StrandName") != ""
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
						distributionBoardMapping
								.setbP_LocationType(itemParameters.getDictParameter().get(i).get("bP_LocationType"));
						distributionBoardMapping
								.setbP_LocationId(itemParameters.getDictParameter().get(i).get("bP_LocationID") != ""
										? itemParameters.getDictParameter().get(i).get("bP_LocationID")
										: "");
						distributionBoardMapping
								.setbP_LocationName(itemParameters.getDictParameter().get(i).get("bP_LocationM") != ""
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
						distributionBoardMapping
								.setbP_EquipmentId(itemParameters.getDictParameter().get(i).get("bP_EquipmentID") != ""
										? itemParameters.getDictParameter().get(i).get("bP_EquipmentID")
										: "");
						distributionBoardMapping
								.setbP_Address(itemParameters.getDictParameter().get(i).get("bP_Address") != ""
										? itemParameters.getDictParameter().get(i).get("bP_Address")
										: "");

						distributionBoardMapping
								.setbP_JunctionId(itemParameters.getDictParameter().get(i).get("bP_JunctionID") != ""
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
						distributionBoardMapping
								.setbP_StrandColor(itemParameters.getDictParameter().get(i).get("bP_StrandColor") != ""
										? itemParameters.getDictParameter().get(i).get("bP_StrandColor")
										: "");
						distributionBoardMapping
								.setbP_StrandId(itemParameters.getDictParameter().get(i).get("bP_StrandID") != ""
										? itemParameters.getDictParameter().get(i).get("bP_StrandID")
										: "");
						distributionBoardMapping
								.setbP_StrandName(itemParameters.getDictParameter().get(i).get("bP_StrandName") != ""
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

					}

				}

				List<Object[]> countConnections = session.createSQLQuery(
						"select coalesce(NUM_ROWS,0) as NUM_ROWS,coalesce(NUM_COLUMNS,0) as NUM_COLUMNS,coalesce((select count (*) from DISTRIBUTION_BOARD_MAPPING a where a.DB_ID='"
								+ distributionBoardId
								+ "' and a.FP_EQUIPMENT!='null'),0) AS front,coalesce((select count (*) from DISTRIBUTION_BOARD_MAPPING a where a.DB_ID='"
								+ distributionBoardId
								+ "' and a.BP_STRAND_NAME!='null'),0) AS back from DISTRIBUTION_BOARD b where b.DB_ID='"
								+ distributionBoardId + "'")
						.list();
				// List<Object[]> countConnections=session.createSQLQuery("select
				// NUM_ROWS,NUM_COLUMNS,(select count (*) from DISTRIBUTION_BOARD_MAPPING a
				// where a.DB_ID='"+distributionBoardId+"' and a.FP_EQUIPMENT!='null') AS
				// front,(select count (*) from DISTRIBUTION_BOARD_MAPPING a where
				// a.DB_ID='"+distributionBoardId+"' and a.BP_STRAND_NAME!='null') AS back from
				// DISTRIBUTION_BOARD b where b.DB_ID='"+distributionBoardId+"'").list();
				//System.out.println("countConnections " + mapper.writeValueAsString(countConnections));
				rtn.put("countConnections", countConnections);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in saveDistributionBoard due to \n " + exceptionAsString);
				logger.info("Error in saveDistributionBoard due to \n " + exceptionAsString);
				rtn.put("distributionBoardId", null);
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
	@RequestMapping(value = "/saveLoadedDistributionBoard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveLoadedDistributionBoard(Locale locale, Model model,
			@ModelAttribute ItemParameters itemParameters, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		List<List<String>> DBDetails = new ArrayList<List<String>>();
		ArrayList<Integer> IgnoredDB = new ArrayList<Integer>();

		session = almsessions.getSession();
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
									.createSQLQuery("SELECT city FROM WAREHOUSE WHERE SITE_ID ='" + siteID + "'");
							// query.setParameter("param", "%" + siteID + "%");
							// rtn.put("city", query.toString());
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();

							if (query.list().size() == 1) {
								city = query.uniqueResult().toString();
							}

						}

						if (city != null && city != "") {

							distributionBoard = new DistributionBoard();
							String distributionBoardId = "";
							String WareID = "";
							ArrayList<String> DBDetail = new ArrayList<String>();
							synchronized (this) {

								distributionBoardId = "DB_" + year + "_" + Integer.parseInt(
										session.createSQLQuery("SELECT DB FROM SEQ_TABLE").uniqueResult().toString());
								query = session.createSQLQuery("UPDATE SEQ_TABLE SET DB = DB + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
							}

							query = session
									.createSQLQuery("SELECT WARE_ID FROM WAREHOUSE WHERE SITE_ID ='" + siteID + "'");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							WareID = query.uniqueResult().toString();

							Timestamp boardLastModifiedDate = new Timestamp(
									new Timestamp(System.currentTimeMillis()).getTime());
							Timestamp boardCreationDate = boardLastModifiedDate;
							String coresnb = itemParameters.getDictParameterLoadedDB().get(i).get("Nofports");
							String sitename = itemParameters.getDictParameterLoadedDB().get(i).get("siteName");
							//String BoardName = sitename + "_DB_" + year;
							////
							String BoardName;
							String name = itemParameters.getDictParameterLoadedDB().get(i).get("name");
							String NetworkLevel = itemParameters.getDictParameterLoadedDB().get(i).get("NetworkLevel");
							if (StringUtils.equalsIgnoreCase(name, "") || StringUtils.equalsIgnoreCase(name, null)) {
								BoardName = sitename + "_DB_" + year;
							}
							else {
								BoardName=name;
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
							distributionBoard.setdbNetLevel(NetworkLevel);

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
			} catch (Exception e) {
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
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/GetAllProjectID", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllProjectID(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String projectId = request.getParameter("ProjectId");
					System.out.println("projectId is  " + projectId);
					query = session.createSQLQuery(
							"SELECT DISTINCT PROJECT_ID,PROJECT_NAME FROM PROJECT WHERE lower(PROJECT_ID) LIKE lower(:param)");
					query.setParameter("param", "%" + projectId + "%");
					rtn.put("ListProjectId", query.list());

				} catch (Exception e) {
					logger.info("Error in saving the ManHole with an error of" + e);

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
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();

		List<String> globalList = new ArrayList<String>();

		String search = request.getParameter("search");
		System.out.println("search " + search);
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")

		Query queryStrands = session.createSQLQuery(
				"SELECT STRAND_ID FROM FIBER_STRANDS WHERE LOWER(STRAND_ID) LIKE :param OR UPPER(STRAND_ID) LIKE :param");

		queryStrands.setParameter("param", "%" + search + "%");
		queryStrands.setMaxResults(20);
		@SuppressWarnings("unchecked")
		List<Object[]> strands = queryStrands.list();

		if (search != null) {

			if (strands != null && strands.size() != 0) {

				// if (manholes.size() > 15) {

				for (Object obj : strands) {

					globalList.add((String) obj);
				}

				// }
			}

		}

		map.put("globalList", globalList);

		tx.commit();
		session.close();

		return map;

	}

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

		session = almsessions.getSession();
		tx = session.beginTransaction();
		List<Object[]> globalList = new ArrayList<Object[]>();
		List<String> searchResult = new ArrayList<String>();

		String search = request.getParameter("searchs");
		String line = request.getParameter("line");
		System.out.println("search " + search);
		System.out.println("line " + line);

		if (StringUtils.equalsIgnoreCase(line, "manhole")) {
			query = session.createSQLQuery(
					"SELECT MANHOLE_ID,MANHOLE_NAME FROM MANHOLE WHERE UPPER(MANHOLE_ID) LIKE UPPER(:param)  OR UPPER(MANHOLE_NAME) LIKE UPPER(:param) ");
			query.setParameter("param", "%" + search + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);
			globalList.addAll(query.list());

		} else if (StringUtils.equalsIgnoreCase(line, "handhole")) {

			query = session.createSQLQuery(
					"SELECT HANDHOLE_ID,HANDHOLE_NAME FROM HANDHOLE b WHERE UPPER(HANDHOLE_ID) LIKE UPPER(:param) OR UPPER(HANDHOLE_NAME) LIKE UPPER(:param) ");
			query.setParameter("param", "%" + search + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);
			globalList.addAll(query.list());

		} else if (StringUtils.equalsIgnoreCase(line, "DB")) {

			query = session.createSQLQuery(
					"SELECT DB_ID,DB_NAME FROM DISTRIBUTION_BOARD c WHERE UPPER(DB_ID) LIKE UPPER(:param) OR UPPER(DB_NAME) LIKE UPPER(:param) ");

			query.setParameter("param", "%" + search + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);
			globalList.addAll(query.list());

		} else if (StringUtils.equalsIgnoreCase(line, "client")) {

			// query = session.createSQLQuery("SELECT DISPLAY_NAME FROM CLIENTS WHERE
			// LOWER(DISPLAY_NAME) LIKE LOWER(:param)");
			query = session.createSQLQuery(
					"SELECT CLIENT_ID,DISPLAY_NAME,LONGITUDE,LATITUDE FROM CLIENTS WHERE CLIENT_ID LIKE :param OR DISPLAY_NAME LIKE :param");

			query.setParameter("param", "%" + search + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);
			globalList.addAll(query.list());

		} else if (StringUtils.equalsIgnoreCase(line, "site")) {

			// query = session.createSQLQuery("SELECT WARE_NAME FROM WAREHOUSE WHERE
			// WARE_NAME LIKE :param");
			query = session.createSQLQuery(
					"SELECT WARE_ID,WARE_NAME,LONGITUDE,LATITUDE,CITY,SITE_ID FROM WAREHOUSE WHERE UPPER(WARE_ID) LIKE UPPER(:param)  OR UPPER(WARE_NAME) LIKE UPPER(:param) OR UPPER(SITE_ID) LIKE UPPER(:param) ");

			query.setParameter("param", "%" + search + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);
			globalList.addAll(query.list());

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
			rtn.put("globalList", query.list());

		}
		tx.commit();
		session.close();

		return rtn;

	}

	@RequestMapping(value = "/searchEquipment", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchEquipment(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		session = almsessions.getSession();
		tx = session.beginTransaction();

		String search = request.getParameter("searchs");
		System.out.println("search " + search + "  ");

		query = session.createSQLQuery(
				"SELECT NODE_NAME FROM NODE_ACTIVE WHERE LOWER(NODE_NAME) LIKE :param OR UPPER(NODE_NAME) LIKE :param");
		query.setParameter("param", "%" + search + "%");

		if (query.list().isEmpty()) {
			System.out.println("emptyyyy");
			rtn.put("glist", "");
		} else {
			rtn.put("glist", query.list());
			System.out.println("nottt emptyy");

		}
		tx.commit();
		session.close();

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

		session = almsessions.getSession();
		tx = session.beginTransaction();

		// String sId = request.getParameter("sId");
		// String sName = request.getParameter("sName");
		// System.out.println("search "+sId+ " "+sName);
		String searchId = request.getParameter("searchId");

		query = session.createSQLQuery(
				"SELECT STRAND_ID,STRAND_NAME,TUBE_ID,FIBER_CABLE_ID FROM FIBER_STRANDS WHERE LOWER(STRAND_NAME) LIKE :param OR UPPER(STRAND_NAME) LIKE :param OR LOWER(STRAND_ID) LIKE :param OR UPPER(STRAND_ID) LIKE :param OR LOWER(FIBER_CABLE_ID) LIKE :param OR UPPER(FIBER_CABLE_ID) LIKE :param OR LOWER(TUBE_ID) LIKE :param OR UPPER(TUBE_ID) LIKE :param");
		// query.setParameter("param", "%" + sId + "%");
		// query.setParameter("param2", "%" + sId + "%");
		query.setParameter("param", "%" + searchId + "%");

		rtn.put("glist", query.list());
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
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String searchId = request.getParameter("searchId");

					query = session.createSQLQuery(
							"SELECT A.STRAND_ID,A.STRAND_NAME,A.TUBE_ID,A.FIBER_CABLE_ID,B.FIBER_CABLE_NAME, C.TUBE_NAME,A.STRAND_NUMBER,A.STRAND_COLOR,C.TUBE_NUMBER,C.TUBE_COLOR,A.STRAND_NETWORK_LEVEL FROM (FIBER_STRANDS A LEFT JOIN FIBER_CABLES B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID)"
									+ " LEFT JOIN FIBER_TUBES C ON A.TUBE_ID = C.TUBE_ID "
									+ "WHERE UPPER(A.STRAND_NAME) LIKE UPPER(:param) OR UPPER(A.STRAND_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(A.TUBE_ID) LIKE UPPER(:param) OR UPPER(B.FIBER_CABLE_NAME) LIKE UPPER(:param) OR UPPER(C.TUBE_NAME) LIKE UPPER(:param)");
					// query.setParameter("param", "%" + sId + "%");
					// query.setParameter("param2", "%" + sId + "%");
					query.setParameter("param", "%" + searchId + "%");

					rtn.put("glist", query.list());

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
		session = almsessions.getSession();
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
					 * query = session.createSQLQuery(
					 * "SELECT TUBE_NAME,TUBE_ID,FIBER_CABLE_ID FROM FIBER_TUBES WHERE TUBE_ID IN (SELECT TUBE_ID FROM FIBER_STRANDS WHERE LOWER(STRAND_NAME) LIKE :param OR UPPER(STRAND_NAME) LIKE :param OR LOWER(STRAND_ID) LIKE :param OR UPPER(STRAND_ID) LIKE :param)"
					 * );
					 */

					/*
					 * query = session.createSQLQuery(
					 * "SELECT DISTINCT C.TUBE_ID,C.TUBE_NAME,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_NUMBER,C.TUBE_COLOR FROM (FIBER_TUBES C LEFT JOIN FIBER_STRANDS A ON C.TUBE_ID = A.TUBE_ID)"
					 * + " LEFT JOIN FIBER_CABLES B ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID "
					 * 
					 * +
					 * "WHERE LOWER(A.STRAND_NAME) LIKE :param OR UPPER(A.STRAND_NAME) LIKE :param OR LOWER(A.STRAND_ID) LIKE :param OR UPPER(A.STRAND_ID) LIKE :param OR LOWER(A.FIBER_CABLE_ID) LIKE :param OR UPPER(A.FIBER_CABLE_ID) LIKE :param OR LOWER(A.TUBE_ID) LIKE :param OR UPPER(A.TUBE_ID) LIKE :param OR LOWER(B.FIBER_CABLE_NAME) LIKE :param OR UPPER(B.FIBER_CABLE_NAME) LIKE :param OR LOWER(C.TUBE_NAME) LIKE :param OR UPPER(C.TUBE_NAME) LIKE :param"
					 * );
					 */
					query = session.createSQLQuery(
							"SELECT DISTINCT C.TUBE_ID,C.TUBE_NAME,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_NUMBER,C.TUBE_COLOR,C.TUBE_NETWORK_LEVEL FROM (FIBER_TUBES C LEFT JOIN FIBER_STRANDS A ON C.TUBE_ID = A.TUBE_ID)"
									+ " LEFT JOIN FIBER_CABLES B ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID "
									+ "WHERE UPPER(A.STRAND_NAME) LIKE UPPER(:param) OR UPPER(A.STRAND_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(A.TUBE_ID) LIKE UPPER(:param) OR UPPER(B.FIBER_CABLE_NAME) LIKE UPPER(:param) OR UPPER(C.TUBE_NAME) LIKE UPPER(:param)");
					// query.setParameter("param", "%" + sId + "%");
					// query.setParameter("param2", "%" + sId + "%");
					query.setParameter("param", "%" + searchId + "%");

					rtn.put("clist", query.list());

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
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String searchId = request.getParameter("searchId");
					String fName = request.getParameter("fName");
					/*
					 * query = session.createSQLQuery(
					 * "SELECT TUBE_NAME,TUBE_ID,FIBER_CABLE_ID FROM FIBER_TUBES WHERE TUBE_ID IN (SELECT TUBE_ID FROM FIBER_STRANDS WHERE LOWER(STRAND_NAME) LIKE :param OR UPPER(STRAND_NAME) LIKE :param OR LOWER(STRAND_ID) LIKE :param OR UPPER(STRAND_ID) LIKE :param)"
					 * );
					 */

					/*
					 * query = session.createSQLQuery(
					 * "SELECT DISTINCT A.FIBER_CABLE_ID,A.FIBER_CABLE_NAME FROM (FIBER_CABLES A LEFT JOIN FIBER_STRANDS B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID)"
					 * + " LEFT JOIN FIBER_TUBES C ON A.FIBER_CABLE_ID = C.FIBER_CABLE_ID " +
					 * "WHERE LOWER(B.STRAND_NAME) LIKE :param OR UPPER(B.STRAND_NAME) LIKE :param OR LOWER(B.STRAND_ID) LIKE :param OR UPPER(B.STRAND_ID) LIKE :param OR LOWER(A.FIBER_CABLE_ID) LIKE :param OR UPPER(A.FIBER_CABLE_ID) LIKE :param OR LOWER(C.TUBE_ID) LIKE :param OR UPPER(C.TUBE_ID) LIKE :param OR LOWER(A.FIBER_CABLE_NAME) LIKE :param OR UPPER(A.FIBER_CABLE_NAME) LIKE :param"
					 * );
					 */
					query = session.createSQLQuery(
							"SELECT DISTINCT A.FIBER_CABLE_ID,A.FIBER_CABLE_NAME,A.FIBER_NETWORK_LEVEL FROM (FIBER_CABLES A LEFT JOIN FIBER_STRANDS B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID)"
									+ " LEFT JOIN FIBER_TUBES C ON A.FIBER_CABLE_ID = C.FIBER_CABLE_ID "
									+ "WHERE UPPER(B.STRAND_NAME) LIKE UPPER(:param) OR UPPER(B.STRAND_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(C.TUBE_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_NAME) LIKE UPPER(:param)");
					// query.setParameter("param", "%" + sId + "%");
					// query.setParameter("param2", "%" + sId + "%");
					// query.setParameter("param", "%" + fName + "%");
					query.setParameter("param", "%" + searchId + "%");

					rtn.put("glist", query.list());

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in SearchFiber due to \n " + exceptionAsString);
					logger.info("Error in SearchFiber due to \n " + exceptionAsString);
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

	@RequestMapping(value = "/SearchLastMile", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SearchLastMile(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String searchId = request.getParameter("searchId");

					query = session.createSQLQuery(
							"SELECT DISTINCT FIBER_CABLE_ID,FIBER_CABLE_NAME FROM FIBER_CABLES WHERE (UPPER(FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(FIBER_CABLE_NAME) LIKE UPPER(:param)) AND FIBER_NETWORK_LEVEL='access' ");

					query.setParameter("param", "%" + searchId + "%");

					rtn.put("clist", query.list());

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in SearchLastMile due to \n " + exceptionAsString);
					logger.info("Error in SearchLastMile due to \n " + exceptionAsString);
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

	@RequestMapping(value = "/SearchJunction", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SearchJunction(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		else {

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String searchId = request.getParameter("searchId");

					query = session.createSQLQuery(
							"SELECT DISTINCT JUNCTION_ID,JUNCTION_NAME FROM JUNCTION WHERE UPPER(JUNCTION_ID) LIKE UPPER(:param) OR UPPER(JUNCTION_NAME) LIKE UPPER(:param) OR UPPER(PHYSICAL_LAYER_ID) LIKE UPPER(:param) OR UPPER(PHYSICAL_LAYER_NAME) LIKE UPPER(:param)");

					query.setParameter("param", "%" + searchId + "%");

					rtn.put("clist", query.list());

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
	 * query = session.createSQLQuery(
	 * "SELECT A.STRAND_ID,A.STRAND_NAME,A.TUBE_ID,A.FIBER_CABLE_ID,B.FIBER_CABLE_NAME, C.TUBE_NAME,A.STRAND_NUMBER,A.STRAND_COLOR,C.TUBE_NUMBER,C.TUBE_COLOR FROM (FIBER_STRANDS A LEFT JOIN FIBER_CABLES B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID)"
	 * + " LEFT JOIN FIBER_TUBES C ON A.TUBE_ID = C.TUBE_ID "
	 * +"WHERE UPPER(A.STRAND_NAME) LIKE UPPER(:param) OR UPPER(A.STRAND_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(A.TUBE_ID) LIKE UPPER(:param) OR UPPER(B.FIBER_CABLE_NAME) LIKE UPPER(:param) OR UPPER(C.TUBE_NAME) LIKE UPPER(:param)"
	 * ); // query.setParameter("param", "%" + sId + "%"); //
	 * query.setParameter("param2", "%" + sId + "%"); query.setParameter("param",
	 * "%" + searchId + "%");
	 * 
	 * rtn.put("glist", query.list()); tx.commit(); session.close();
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
	 * /*query = session.createSQLQuery(
	 * "SELECT TUBE_NAME,TUBE_ID,FIBER_CABLE_ID FROM FIBER_TUBES WHERE TUBE_ID IN (SELECT TUBE_ID FROM FIBER_STRANDS WHERE LOWER(STRAND_NAME) LIKE :param OR UPPER(STRAND_NAME) LIKE :param OR LOWER(STRAND_ID) LIKE :param OR UPPER(STRAND_ID) LIKE :param)"
	 * );
	 */

	/*
	 * query = session.createSQLQuery(
	 * "SELECT DISTINCT C.TUBE_ID,C.TUBE_NAME,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_NUMBER,C.TUBE_COLOR FROM (FIBER_TUBES C LEFT JOIN FIBER_STRANDS A ON C.TUBE_ID = A.TUBE_ID)"
	 * + " LEFT JOIN FIBER_CABLES B ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID "
	 * 
	 * +
	 * "WHERE LOWER(A.STRAND_NAME) LIKE :param OR UPPER(A.STRAND_NAME) LIKE :param OR LOWER(A.STRAND_ID) LIKE :param OR UPPER(A.STRAND_ID) LIKE :param OR LOWER(A.FIBER_CABLE_ID) LIKE :param OR UPPER(A.FIBER_CABLE_ID) LIKE :param OR LOWER(A.TUBE_ID) LIKE :param OR UPPER(A.TUBE_ID) LIKE :param OR LOWER(B.FIBER_CABLE_NAME) LIKE :param OR UPPER(B.FIBER_CABLE_NAME) LIKE :param OR LOWER(C.TUBE_NAME) LIKE :param OR UPPER(C.TUBE_NAME) LIKE :param"
	 * );
	 */
	/*
	 * query = session.createSQLQuery(
	 * "SELECT DISTINCT C.TUBE_ID,C.TUBE_NAME,B.FIBER_CABLE_ID,B.FIBER_CABLE_NAME,C.TUBE_NUMBER,C.TUBE_COLOR FROM (FIBER_TUBES C LEFT JOIN FIBER_STRANDS A ON C.TUBE_ID = A.TUBE_ID)"
	 * + " LEFT JOIN FIBER_CABLES B ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID " +
	 * "WHERE UPPER(A.STRAND_NAME) LIKE UPPER(:param) OR UPPER(A.STRAND_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(A.TUBE_ID) LIKE UPPER(:param) OR UPPER(B.FIBER_CABLE_NAME) LIKE UPPER(:param) OR UPPER(C.TUBE_NAME) LIKE UPPER(:param)"
	 * ); // query.setParameter("param", "%" + sId + "%"); //
	 * query.setParameter("param2", "%" + sId + "%"); query.setParameter("param",
	 * "%" + searchId + "%");
	 * 
	 * rtn.put("clist", query.list()); tx.commit(); session.close();
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
	 * = request.getParameter("fName"); /*query = session.createSQLQuery(
	 * "SELECT TUBE_NAME,TUBE_ID,FIBER_CABLE_ID FROM FIBER_TUBES WHERE TUBE_ID IN (SELECT TUBE_ID FROM FIBER_STRANDS WHERE LOWER(STRAND_NAME) LIKE :param OR UPPER(STRAND_NAME) LIKE :param OR LOWER(STRAND_ID) LIKE :param OR UPPER(STRAND_ID) LIKE :param)"
	 * );
	 */

	/*
	 * query = session.createSQLQuery(
	 * "SELECT DISTINCT A.FIBER_CABLE_ID,A.FIBER_CABLE_NAME FROM (FIBER_CABLES A LEFT JOIN FIBER_STRANDS B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID)"
	 * + " LEFT JOIN FIBER_TUBES C ON A.FIBER_CABLE_ID = C.FIBER_CABLE_ID " +
	 * "WHERE LOWER(B.STRAND_NAME) LIKE :param OR UPPER(B.STRAND_NAME) LIKE :param OR LOWER(B.STRAND_ID) LIKE :param OR UPPER(B.STRAND_ID) LIKE :param OR LOWER(A.FIBER_CABLE_ID) LIKE :param OR UPPER(A.FIBER_CABLE_ID) LIKE :param OR LOWER(C.TUBE_ID) LIKE :param OR UPPER(C.TUBE_ID) LIKE :param OR LOWER(A.FIBER_CABLE_NAME) LIKE :param OR UPPER(A.FIBER_CABLE_NAME) LIKE :param"
	 * );
	 */
	/*
	 * query = session.createSQLQuery(
	 * "SELECT DISTINCT A.FIBER_CABLE_ID,A.FIBER_CABLE_NAME FROM (FIBER_CABLES A LEFT JOIN FIBER_STRANDS B ON A.FIBER_CABLE_ID = B.FIBER_CABLE_ID)"
	 * + " LEFT JOIN FIBER_TUBES C ON A.FIBER_CABLE_ID = C.FIBER_CABLE_ID " +
	 * "WHERE UPPER(B.STRAND_NAME) LIKE UPPER(:param) OR UPPER(B.STRAND_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_ID) LIKE UPPER(:param) OR UPPER(C.TUBE_ID) LIKE UPPER(:param) OR UPPER(A.FIBER_CABLE_NAME) LIKE UPPER(:param)"
	 * ); // query.setParameter("param", "%" + sId + "%"); //
	 * query.setParameter("param2", "%" + sId + "%"); //query.setParameter("param",
	 * "%" + fName + "%"); query.setParameter("param", "%" + searchId + "%");
	 * 
	 * rtn.put("glist", query.list()); tx.commit(); session.close();
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

		session = almsessions.getSession();
		tx = session.beginTransaction();

		String cID = request.getParameter("cID");
		String fID = request.getParameter("fID");
		System.out.println("search " + cID);
		System.out.println("search " + fID);

		ObjectMapper mapper = new ObjectMapper();

		Query query1 = session.createSQLQuery(
				"SELECT TUBE_NAME FROM FIBER_TUBES WHERE LOWER(TUBE_ID) LIKE :param OR UPPER(TUBE_ID) LIKE :param");
		query1.setParameter("param", "%" + cID + "%");

		rtn.put("clist", query1.list());

		System.out.println("  " + query1.list());
		Query query2 = session.createSQLQuery(
				"SELECT FIBER_CABLE_NAME FROM FIBER_CABLES WHERE LOWER(FIBER_CABLE_ID) LIKE :param OR UPPER(FIBER_CABLE_ID) LIKE :param");
		query2.setParameter("param", "%" + fID + "%");

		rtn.put("flist", query2.list());

		tx.commit();
		session.close();

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

		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Object TubesCount = session.createSQLQuery("SELECT count(*) FROM FIBER_TUBES WHERE FIBER_CABLE_ID='"
						+ request.getParameter("fiberID") + "'").uniqueResult();
				Object cableGeo = session.createSQLQuery(
						"SELECT  coalesce(total_geo_distance,0) FROM FIBER_cables WHERE FIBER_CABLE_ID='"
								+ request.getParameter("fiberID") + "'")
						.uniqueResult();
				Object cableLineOfSite = session
						.createSQLQuery("SELECT coalesce(Length,0) FROM FIBER_cables WHERE FIBER_CABLE_ID='"
								+ request.getParameter("fiberID") + "'")
						.uniqueResult();

				rtn.put("TubesCount", TubesCount);
				rtn.put("cableGeo", cableGeo);
				rtn.put("cableLineOfSite", cableLineOfSite);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountTubes due to \n " + exceptionAsString);
				logger.info("Error in findCountTubes due to \n " + exceptionAsString);
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

	@RequestMapping(value = "/findCountStrands", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountStrands(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<>();

		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Object StrandsCount = session.createSQLQuery("SELECT count(*) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID='"
						+ request.getParameter("fiberID") + "'").uniqueResult();

				rtn.put("StrandsCount", StrandsCount);
				Object cablegeo = session
						.createSQLQuery("SELECT total_geo_distance FROM FIBER_cables WHERE FIBER_CABLE_ID='"
								+ request.getParameter("fiberID") + "'")
						.list();
				Object cableLineOfSite = session.createSQLQuery("SELECT Length FROM FIBER_cables WHERE FIBER_CABLE_ID='"
						+ request.getParameter("fiberID") + "'").list();
				rtn.put("cablegeo", cablegeo);
				rtn.put("cableLineOfSite", cableLineOfSite);

				Object StrandBackboneGeo = session.createSQLQuery(
						"SELECT coalesce(sum(total_geo_distance),0) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_cables where fiber_network_level='backbone')")
						.uniqueResult();
				Object StrandBackboneLine = session.createSQLQuery(
						"SELECT coalesce(sum(Length),0) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_cables where fiber_network_level='backbone')")
						.uniqueResult();
				rtn.put("StrandBackboneGeo", StrandBackboneGeo);
				rtn.put("StrandBackboneLine", StrandBackboneLine);

				Object StrandMetroGeo = session.createSQLQuery(
						"SELECT coalesce(sum(total_geo_distance),0) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_cables where fiber_network_level='metro')")
						.uniqueResult();
				Object StrandMetroLine = session.createSQLQuery(
						"SELECT coalesce(sum(Length),0) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_cables where fiber_network_level='metro')")
						.uniqueResult();
				rtn.put("StrandMetroGeo", StrandMetroGeo);
				rtn.put("StrandMetroLine", StrandMetroLine);

				Object StrandAccessGeo = session.createSQLQuery(
						"SELECT coalesce(sum(total_geo_distance),0) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_cables where fiber_network_level='access')")
						.uniqueResult();
				Object StrandAccessLine = session.createSQLQuery(
						"SELECT coalesce(sum(Length),0) FROM FIBER_STRANDS WHERE FIBER_CABLE_ID in (Select FIBER_CABLE_ID from fiber_cables where fiber_network_level='access')")
						.uniqueResult();
				rtn.put("StrandAccessGeo", StrandAccessGeo);
				rtn.put("StrandAccessLine", StrandAccessLine);

				Object networkLevel = session
						.createSQLQuery("SELECT fiber_network_Level FROM FIBER_Cables WHERE FIBER_CABLE_ID='"
								+ request.getParameter("fiberID") + "'")
						.uniqueResult();

				rtn.put("networkLevel", networkLevel);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountStrands due to \n " + exceptionAsString);
				logger.info("Error in findCountStrands due to \n " + exceptionAsString);
				rtn.put("StrandsCount", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
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
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Object manholeCount = session.createSQLQuery(
						"SELECT count(*) FROM MANHOLE WHERE PROJECT_ID='" + request.getParameter("ProjectId") + "'")
						.uniqueResult();

				rtn.put("manholeCount", manholeCount);

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in findCountManHoles due to \n " + exceptionAsString);
				logger.info("Error in findCountManHoles due to \n " + exceptionAsString);
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
	
	@RequestMapping(value = "/findCountDbNetLevel", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountDbNetLevel(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Object BackboneCount = session.createSQLQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD WHERE PROJECT_ID='" + request.getParameter("ProjectId") + "' AND DB_NETWORK_LEVEL = 'backbone'")
						.uniqueResult();
				rtn.put("BackboneCount", BackboneCount);
				
				Object MetroCount = session.createSQLQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD WHERE PROJECT_ID='" + request.getParameter("ProjectId") + "' AND DB_NETWORK_LEVEL = 'metro'")
						.uniqueResult();
				rtn.put("MetroCount", MetroCount);
				
				Object AccessCount = session.createSQLQuery(
						"SELECT count(*) FROM DISTRIBUTION_BOARD WHERE PROJECT_ID='" + request.getParameter("ProjectId") + "' AND DB_NETWORK_LEVEL = 'access'")
						.uniqueResult();
				rtn.put("AccessCount", AccessCount);


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
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				Object handholeCount = session.createSQLQuery(
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
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				Object CountDistBoard = session
						.createSQLQuery("SELECT count(*) FROM DISTRIBUTION_BOARD WHERE PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult();

				rtn.put("CountDistBoard", CountDistBoard);

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

	@RequestMapping(value = "/findCountfiber", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCountfiber(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();
		Session session = null;
		Transaction tx = null;
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				@SuppressWarnings("unchecked")
				List<Object[]> Countfiber = session.createSQLQuery(
						"select 'fiber', count(a.FIBER_CABLE_ID),'tube',coalesce(sum(a.NUMBER_OF_TUBES),0) ,'strand',coalesce(sum(a.NUMBER_OF_STRANDS),0),count(a.FIBER_NETWORK_LEVEL) , sum (total_geo_distance), sum (length) from FIBER_CABLES a WHERE a.PROJECT_ID ='"
								+ request.getParameter("ProjectId") + "' ")
						.list();
				@SuppressWarnings("unchecked")
				List<Object[]> backbone = session.createSQLQuery(
						"select 'fiber', count(FIBER_CABLE_ID),'tube',coalesce(sum(NUMBER_OF_TUBES),0) ,'strand',coalesce(sum(NUMBER_OF_STRANDS),0),count(FIBER_NETWORK_LEVEL) from FIBER_CABLES where FIBER_NETWORK_LEVEL='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.list();
				
				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerTklBackbone = session.createSQLQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'tkl' and FIBER_NETWORK_LEVEL = 'backbone'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.list();
				
				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerNofbiBackbone = session.createSQLQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'nofbi' and FIBER_NETWORK_LEVEL = 'backbone'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.list();
				
				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerOtherBackbone = session.createSQLQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'others' and FIBER_NETWORK_LEVEL = 'backbone'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.list();
				
				@SuppressWarnings("unchecked")
				List<Object[]> metro = session.createSQLQuery(
						"SELECT 'fiber', count(FIBER_CABLE_ID),'tube',coalesce(sum(NUMBER_OF_TUBES),0) ,'strand',coalesce(sum(NUMBER_OF_STRANDS),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='metro' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.list();
				
				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerTklMetro = session.createSQLQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'tkl' and FIBER_NETWORK_LEVEL = 'metro'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.list();
				
				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerNofbiMetro = session.createSQLQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'nofbi' and FIBER_NETWORK_LEVEL = 'metro'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.list();
				
				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerOtherMetro = session.createSQLQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'others' and FIBER_NETWORK_LEVEL = 'metro'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.list();
				
				@SuppressWarnings("unchecked")
				List<Object[]> access = session.createSQLQuery(
						"SELECT 'fiber', count(FIBER_CABLE_ID),'tube',coalesce(sum(NUMBER_OF_TUBES),0) ,'strand',coalesce(sum(NUMBER_OF_STRANDS),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='access' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.list();
				
				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerTklAccess = session.createSQLQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'tkl' and FIBER_NETWORK_LEVEL = 'access'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.list();
				
				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerNofbiAccess = session.createSQLQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'nofbi' and FIBER_NETWORK_LEVEL = 'access'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.list();
				
				@SuppressWarnings("unchecked")
				List<Object[]> fiberOwnerOtherAccess = session.createSQLQuery(
						"select count(*) from FIBER_CABLES WHERE FIBER_OWNER = 'others' and FIBER_NETWORK_LEVEL = 'access'  and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.list();
				
				String Backbone = session.createSQLQuery(
						"SELECT COUNT(*) from FIBER_CABLES where FIBER_NETWORK_LEVEL='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String Metro = session.createSQLQuery(
						"SELECT COUNT(*) from FIBER_CABLES where FIBER_NETWORK_LEVEL='metro' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String Access = session.createSQLQuery(
						"SELECT COUNT(*) from FIBER_CABLES where FIBER_NETWORK_LEVEL='access' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();

				String backbone_total_geo = session.createSQLQuery(
						"SELECT coalesce(sum (total_Geo_Distance),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String backbone_total_LineOfsite = session.createSQLQuery(
						"SELECT coalesce(sum (Length),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String Metro_total_geo = session.createSQLQuery(
						"SELECT coalesce(sum(total_Geo_Distance),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='metro' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String Metro_total_LineOfsite = session.createSQLQuery(
						"SELECT coalesce(sum (Length),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='metro' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String Access_total_geo = session.createSQLQuery(
						"SELECT coalesce(sum(total_Geo_Distance),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='access' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String Access_total_LineOfsite = session.createSQLQuery(
						"SELECT coalesce(sum(Length),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='access' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();

				String totalgeo = session.createSQLQuery(
						"SELECT  coalesce(sum((total_geo_distance*number_of_strands*number_of_tubes)),0) from FIBER_CABLES where PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String totalgeotube = session
						.createSQLQuery("SELECT  coalesce(sum(total_geo_distance),0) from FIBER_tubes ").uniqueResult()
						.toString();
				String totalgeostrand = session
						.createSQLQuery("SELECT  coalesce(sum(total_geo_distance),0) from FIBER_Strands ")
						.uniqueResult().toString();

				String totalLine = session.createSQLQuery(
						"SELECT  coalesce(sum((Length*number_of_strands*number_of_tubes)),0) from FIBER_CABLES where PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String totalLinetube = session.createSQLQuery("SELECT  coalesce(sum(Length),0) from FIBER_tubes ")
						.uniqueResult().toString();
				String totalLinestrand = session.createSQLQuery("SELECT  coalesce(sum(Length),0) from FIBER_Strands ")
						.uniqueResult().toString();

				String backboneStrand = session.createSQLQuery(
						"select coalesce(sum((total_geo_distance*number_of_strands*number_of_tubes)),0) from FIBER_CABLES where FIBER_NETWORK_LEVEL='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String tubeStrand = session.createSQLQuery(
						"select coalesce(sum(total_geo_distance),0) from FIBER_tubes where FIBER_cable_id in (select FIBER_cable_id from fiber_cables where FIBER_NETWORK_LEVEL ='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "')")
						.uniqueResult().toString();

				String geoBackboneStrand = session.createSQLQuery(
						"select coalesce(sum(total_geo_distance*number_of_strands),0)  from fiber_cables where FIBER_NETWORK_LEVEL ='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String LengthBackboneStrand = session.createSQLQuery(
						"select coalesce(sum(Length*number_of_strands),0)  from fiber_cables where FIBER_NETWORK_LEVEL ='backbone' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();

				String geoMetroStrand = session.createSQLQuery(
						"select coalesce(sum(total_geo_distance*number_of_strands),0)  from fiber_cables where FIBER_NETWORK_LEVEL ='metro' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String LengthMetroStrand = session.createSQLQuery(
						"select coalesce(sum(Length*number_of_strands),0)  from fiber_cables where FIBER_NETWORK_LEVEL ='metro' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();

				String geoAccessStrand = session.createSQLQuery(
						"select coalesce(sum(total_geo_distance*number_of_strands),0)  from fiber_cables where FIBER_NETWORK_LEVEL ='access' and PROJECT_ID='"
								+ request.getParameter("ProjectId") + "'")
						.uniqueResult().toString();
				String LengthAccessStrand = session.createSQLQuery(
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
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				@SuppressWarnings("unchecked")
				List<Object[]> countTrench = session.createSQLQuery(
						"select count (*) from TRENCH WHERE PROJECT_ID='" + request.getParameter("ProjectId") + "' ")
						.list();

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
		session = almsessions.getSession();
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
								.createSQLQuery(
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

						res_LngLat = (Object[]) session.createSQLQuery(
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auto_FillAux_Name", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> auto_FillAux_Name(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		Object lat = request.getParameter("lat");
		Object lng = request.getParameter("lng");

		try {
			String auxName = "";

			Object[] res_AuxName = (Object[]) session
					.createSQLQuery("SELECT MANHOLE_ID,MANHOLE_NAME FROM MANHOLE WHERE LONGITUDE='" + lng
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
						.createSQLQuery("SELECT HANDHOLE_ID,HANDHOLE_NAME FROM HANDHOLE WHERE LONGITUDE='" + lng
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
		session = almsessions.getSession();
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

						res_Type = (Object[]) session.createSQLQuery(
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
						res_Type1 = (Object[]) session.createSQLQuery(
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
							Query gridQuery = session.createSQLQuery(querySearch);

							List<String> finalSearchList = (List<String>) ((SQLQuery) gridQuery).list();

							finalList.addAll(finalSearchList);

						}
						System.out.println("finalList " + mapper.writeValueAsString(finalList));

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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findSiteAccordingToCity", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findSiteAccordingToCity(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = almsessions.getSession();
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

		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			Object lat = request.getParameter("lat");
			Object lng = request.getParameter("lng");
			String type = "";

			Object[] res_Type = (Object[]) session
					.createSQLQuery("SELECT MANHOLE_ID,MANHOLE_NAME,MANHOLE_MODEL,CITY FROM MANHOLE WHERE LONGITUDE='"
							+ lng + "' and LATITUDE='" + lat + "'")
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
				res_Type = (Object[]) session.createSQLQuery(
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
					res_Type = (Object[]) session.createSQLQuery(
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

		session = almsessions.getSession();

		Query physicalLayerDeleteQuery = null, fiberCableDeleteQuery = null, tubeDeleteQuery = null,
				strandDeleteQuery = null, trenchPathDeleteQuery = null, tubePathDeleteQuery = null;
		Object newCount = null;

		String[] idList = request.getParameterValues("physicalLayerID[]");
		String physicalLayer = request.getParameter("physicalLayer");
		// System.out.println("physicalLayer:" +physicalLayer);
		String NodeID = request.getParameter("NodeId");
		System.out.println("NodeID:" + NodeID);
		String manHandholeID = request.getParameter("manHandholeID");
		String manHandoleName = request.getParameter("manHandoleName");

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				if (idList != null) {

					if (StringUtils.equalsIgnoreCase(physicalLayer, "Project")) {

						// System.out.println("physicalLayer[]:" +mapper.writeValueAsString(idList));

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from PROJECT b where b.PROJECT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from MANHOLE b where b.PROJECT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from HANDHOLE b where b.PROJECT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createSQLQuery(
								"delete from JUNCTION_MAPPING b where b.PHYSICAL_LAYER_ID IN (select PHYSICAL_LAYER_ID from JUNCTION where PROJECT_ID IN (:param1))");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from JUNCTION b where b.PROJECT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						tubeDeleteQuery = session.createSQLQuery(
								"delete from FIBER_TUBES b where b.FIBER_CABLE_ID IN (select FIBER_CABLE_ID from FIBER_CABLES where PROJECT_ID IN (:param1))");
						tubeDeleteQuery.setParameterList("param1", idList);
						tubeDeleteQuery.executeUpdate();

						strandDeleteQuery = session.createSQLQuery(
								"delete from FIBER_STRANDS b where b.FIBER_CABLE_ID IN (select FIBER_CABLE_ID from FIBER_CABLES where PROJECT_ID IN (:param1))");
						strandDeleteQuery.setParameterList("param1", idList);
						strandDeleteQuery.executeUpdate();

						fiberCableDeleteQuery = session
								.createSQLQuery("delete from FIBER_CABLES b where b.PROJECT_ID IN (:param1)");
						fiberCableDeleteQuery.setParameterList("param1", idList);
						fiberCableDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from distribution_board b where b.PROJECT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createSQLQuery(
								"delete from DUCT_AUXILIARY_POINTS b where b.DUCT_ID  IN (select DUCT_ID from DUCTS where TRENCH_ID IN (select TRENCH_ID from TRENCH where PROJECT_ID IN (:param1)))");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createSQLQuery(
								"delete from DUCTS b where b.TRENCH_ID IN (select TRENCH_ID from TRENCH where PROJECT_ID IN (:param1))");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createSQLQuery(
								"delete from TRENCH_AUXILIARY_POINTS b where b.TRENCH_ID  IN (select TRENCH_ID from TRENCH where PROJECT_ID IN (:param1))");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from TRENCH b where b.PROJECT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						newCount = session.createSQLQuery("SELECT count(*) FROM PROJECT").uniqueResult();
						rtn.put("newCount", newCount);
					}

					if (StringUtils.equalsIgnoreCase(physicalLayer, "Manhole")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllManholes")) {

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from MANHOLE b where b.manhole_id IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from JUNCTION b where b.PHYSICAL_LAYER_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createSQLQuery(
								"delete from JUNCTION_MAPPING b where b.PHYSICAL_LAYER_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						newCount = session
								.createSQLQuery("SELECT count(*) FROM MANHOLE where PROJECT_ID ='" + NodeID + "' ")
								.uniqueResult();
						rtn.put("newCount", newCount);
					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "Handhole")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllHandholes")) {

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from HANDHOLE b where b.handhole_id IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from JUNCTION b where b.PHYSICAL_LAYER_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session.createSQLQuery(
								"delete from JUNCTION_MAPPING b where b.PHYSICAL_LAYER_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						newCount = session
								.createSQLQuery("SELECT count(*) FROM HANDHOLE where  PROJECT_ID ='" + NodeID + "' ")
								.uniqueResult();
						rtn.put("newCount", newCount);
					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "Junction")) {
						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from JUNCTION b where b.JUNCTION_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from JUNCTION_MAPPING b where b.JCT_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						query = session.createSQLQuery(
								"SELECT count(*) FROM JUNCTION b WHERE PHYSICAL_LAYER_ID = '" + manHandholeID + "' ");
						String countJunc = query.uniqueResult().toString();

						if (StringUtils.equalsIgnoreCase(countJunc, "0")) {
							manHandoleName = manHandoleName.substring(0, manHandoleName.length() - 2);
							System.out.println("manHandoleName" + manHandoleName);

							query = session.createSQLQuery("UPDATE MANHOLE SET MANHOLE_NAME= '" + manHandoleName
									+ "' where MANHOLE_ID='" + manHandholeID + "' ");
							query.executeUpdate();
							query = session.createSQLQuery("UPDATE HANDHOLE SET HANDHOLE_NAME= '" + manHandoleName
									+ "' where HANDHOLE_ID='" + manHandholeID + "' ");
							query.executeUpdate();

						}
						rtn.put("ManHandholeNewName", manHandoleName);

						newCount = session.createSQLQuery(
								"SELECT count(*) FROM JUNCTION where  PHYSICAL_LAYER_ID ='" + manHandoleName + "' ")
								.uniqueResult();
						rtn.put("ManholeCount", newCount);
						// newCount = session
						// .createSQLQuery("SELECT count(*) FROM HANDHOLE where PROJECT_ID ='" + NodeID
						// + "' ")
						// .uniqueResult();
						// rtn.put("HandholeCount", newCount);

					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "DistBoard")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllDistBoards")) {

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from distribution_board b where b.DB_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						physicalLayerDeleteQuery = session
								.createSQLQuery("delete from distribution_board_mapping b  where b.DB_ID IN (:param1)");
						physicalLayerDeleteQuery.setParameterList("param1", idList);
						physicalLayerDeleteQuery.executeUpdate();

						newCount = session
								.createSQLQuery(
										"SELECT count(*) FROM distribution_board where  PROJECT_ID ='" + NodeID + "' ")
								.uniqueResult();
						rtn.put("newCount", newCount);
					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "FiberCable")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllFiberCables")) {

						fiberCableDeleteQuery = session
								.createSQLQuery("delete from FIBER_CABLES b where b.FIBER_CABLE_ID IN (:param1)");
						fiberCableDeleteQuery.setParameterList("param1", idList);
						fiberCableDeleteQuery.executeUpdate();

						tubeDeleteQuery = session
								.createSQLQuery("delete from FIBER_TUBES b where b.FIBER_CABLE_ID IN (:param1)");
						tubeDeleteQuery.setParameterList("param1", idList);
						tubeDeleteQuery.executeUpdate();

						strandDeleteQuery = session
								.createSQLQuery("delete from FIBER_STRANDS b where b.FIBER_CABLE_ID IN (:param1)");
						strandDeleteQuery.setParameterList("param1", idList);
						strandDeleteQuery.executeUpdate();

						fiberCableDeleteQuery = session.createSQLQuery(
								"delete from fiber_auxiliary_points b where b.fiber_cable_id IN (:param1)");
						fiberCableDeleteQuery.setParameterList("param1", idList);
						fiberCableDeleteQuery.executeUpdate();

						tubeDeleteQuery = session.createSQLQuery(
								"delete from TUBE_AUXILIARY_POINTS b where b.fiber_cable_id IN (:param1)");
						tubeDeleteQuery.setParameterList("param1", idList);
						tubeDeleteQuery.executeUpdate();

						strandDeleteQuery = session.createSQLQuery(
								"delete from strand_auxiliary_points b where b.fiber_cable_id IN (:param1)");
						strandDeleteQuery.setParameterList("param1", idList);
						strandDeleteQuery.executeUpdate();

						List<Object[]> Countfiber = session.createSQLQuery(
								"select 'fiber', count(a.FIBER_CABLE_ID),'tube', sum(a.NUMBER_OF_TUBES) ,'strand', sum(a.NUMBER_OF_STRANDS) from FIBER_CABLES a WHERE a.PROJECT_ID ='"
										+ NodeID + "' ")
								.list();

						rtn.put("newCount", Countfiber);
					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "FiberTube")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllFiberTubes")) {

						strandDeleteQuery = session.createSQLQuery(
								"delete from strand_auxiliary_points b where b.STRAND_ID IN (select strand_id from fiber_strands where TUBE_ID IN (:param1) )");
						strandDeleteQuery.setParameterList("param1", idList);
						strandDeleteQuery.executeUpdate();

						tubeDeleteQuery = session
								.createSQLQuery("delete from FIBER_TUBES b where b.TUBE_ID IN (:param1)");
						tubeDeleteQuery.setParameterList("param1", idList);
						tubeDeleteQuery.executeUpdate();

						strandDeleteQuery = session
								.createSQLQuery("delete from FIBER_STRANDS b where b.TUBE_ID IN (:param1)");
						strandDeleteQuery.setParameterList("param1", idList);
						strandDeleteQuery.executeUpdate();

						tubeDeleteQuery = session
								.createSQLQuery("delete from TUBE_AUXILIARY_POINTS b where b.TUBE_ID IN (:param1)");
						tubeDeleteQuery.setParameterList("param1", idList);
						tubeDeleteQuery.executeUpdate();

						List<Object[]> Countfiber = session.createSQLQuery(
								"select 'fiber', count(a.FIBER_CABLE_ID),'tube', sum(a.NUMBER_OF_TUBES) ,'strand', sum(a.NUMBER_OF_STRANDS) from FIBER_CABLES a WHERE a.PROJECT_ID ='"
										+ NodeID + "' ")
								.list();
						rtn.put("newCount", Countfiber);
					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "FiberStrand")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllFiberStrands")) {

						strandDeleteQuery = session
								.createSQLQuery("delete from FIBER_STRANDS b where b.STRAND_ID IN (:param1)");
						strandDeleteQuery.setParameterList("param1", idList);
						strandDeleteQuery.executeUpdate();

						strandDeleteQuery = session
								.createSQLQuery("delete from strand_auxiliary_points b where b.STRAND_ID IN (:param1)");
						strandDeleteQuery.setParameterList("param1", idList);
						strandDeleteQuery.executeUpdate();

						List<Object[]> Countfiber = session.createSQLQuery(
								"select 'fiber', count(a.FIBER_CABLE_ID),'tube', sum(a.NUMBER_OF_TUBES) ,'strand', sum(a.NUMBER_OF_STRANDS) from FIBER_CABLES a WHERE a.PROJECT_ID ='"
										+ NodeID + "' ")
								.list();

						rtn.put("newCount", Countfiber);
					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "Trench")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "AllTrenches")) {
						trenchPathDeleteQuery = session
								.createSQLQuery("delete from TRENCH b where b.TRENCH_ID IN (:param1)");
						trenchPathDeleteQuery.setParameterList("param1", idList);
						trenchPathDeleteQuery.executeUpdate();

						trenchPathDeleteQuery = session
								.createSQLQuery("delete from TRENCH_AUXILIARY_POINTS b where b.TRENCH_ID IN (:param1)");
						trenchPathDeleteQuery.setParameterList("param1", idList);
						trenchPathDeleteQuery.executeUpdate();

						tubePathDeleteQuery = session.createSQLQuery(
								"delete from DUCT_AUXILIARY_POINTS b where b.DUCT_ID IN (select DUCT_ID from DUCTS where TRENCH_ID IN (:param1))");
						tubePathDeleteQuery.setParameterList("param1", idList);
						tubePathDeleteQuery.executeUpdate();

						tubePathDeleteQuery = session
								.createSQLQuery("delete from DUCTS b where b.TRENCH_ID IN (:param1)");
						tubePathDeleteQuery.setParameterList("param1", idList);
						tubePathDeleteQuery.executeUpdate();

						newCount = session
								.createSQLQuery("SELECT count(*) FROM TRENCH where  PROJECT_ID ='" + NodeID + "' ")
								.uniqueResult();
						rtn.put("newCount", newCount);
					} else if (StringUtils.equalsIgnoreCase(physicalLayer, "trenchDucts")
							|| StringUtils.equalsIgnoreCase(physicalLayer, "DUCT")) {

						tubePathDeleteQuery = session
								.createSQLQuery("delete from DUCTS b where b.DUCT_ID IN (:param1)");
						tubePathDeleteQuery.setParameterList("param1", idList);
						tubePathDeleteQuery.executeUpdate();

						tubePathDeleteQuery = session
								.createSQLQuery("delete from DUCT_AUXILIARY_POINTS b where b.DUCT_ID IN (:param1)");
						tubePathDeleteQuery.setParameterList("param1", idList);
						tubePathDeleteQuery.executeUpdate();

						newCount = session.createSQLQuery(
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

						List<Object[]> Countfiber = session.createSQLQuery(
								"select 'fiber', count(a.FIBER_CABLE_ID),'tube', sum(a.NUMBER_OF_TUBES) ,'strand', sum(a.NUMBER_OF_STRANDS) from FIBER_CABLES a WHERE a.PROJECT_ID ='"
										+ NodeID + "' ")
								.list();
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
		session = almsessions.getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				query = session.createSQLQuery(
						"SELECT FIBER_CABLE_ID,FIBER_CABLE_NAME,SOURCE_CITY,SOURCE_LAT,SOURCE_LNG,DESTINATION_CITY,DESTINATION_LAT,DESTINATION_LNG FROM FIBER_CABLES");

				rtn.put("fiberPathSrcDst", query.list());

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

	@RequestMapping(value = "/saveFiberStrand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveFiberStrand(Locale locale, Model model,
			@ModelAttribute ItemParameters itemParameters, HttpServletRequest request, HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);

		Map<String, Object> rtn = new LinkedHashMap<>();
		// ObjectMapper mapper = new ObjectMapper();

		session = almsessions.getSession();
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

				String strandID = request.getParameter("strandId");

				if (StringUtils.equalsIgnoreCase(strandID, "")) {
					synchronized (this) {
						// strandID = "STRAND" + year + "_" + appConfig.getSeqNbr(48,session);
						strandID = "STRAND" + year + "_" + Integer.parseInt(
								session.createSQLQuery("SELECT FIBER_STRAND FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET FIBER_STRAND = FIBER_STRAND + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
					}
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

				else if (source.contains("CLT") == true) {
					fiberStrand.setSourceWareId("null");
					fiberStrand.setSourceId(source.split(":")[0]);
					fiberStrand.setSourceName(source.split(":")[1] + ":" + source.split(":")[2]);
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

				else if (destination.contains("CLT") == true) {
					fiberStrand.setDestinationWareId("null");
					fiberStrand.setDestinationId(destination.split(":")[0]);
					fiberStrand.setDestinationName(destination.split(":")[1] + ":" + destination.split(":")[2]);
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

				int auxArraySize = 0;
				if (StringUtils.equalsIgnoreCase(strandAuxFlag, "opened")
						|| StringUtils.equalsIgnoreCase(strandAuxFlag, "new strand")) {
					query = session
							.createSQLQuery("delete from STRAND_AUXILIARY_POINTS where STRAND_ID='" + strandID + "'");
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
												session.createSQLQuery("SELECT FIBER_STRAND_AUX FROM SEQ_TABLE")
														.uniqueResult().toString());
								query = session.createSQLQuery(
										"UPDATE SEQ_TABLE SET FIBER_STRAND_AUX = FIBER_STRAND_AUX + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
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

						}
					}

				}
				List<Object[]> strandsAuxiliaries = session.createSQLQuery(
						"SELECT STRAND_ID,LONGITUDE,LATITUDE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,DISTANCE_FROM_SOURCE,SEQ_SORTING,AUXILIARY_ID,DRIVING_DISTANCE,GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS WHERE STRAND_ID='"
								+ strandID + "' ORDER BY SEQ_SORTING ASC ")
						.list();

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
		session = almsessions.getSession();
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

				if (StringUtils.equalsIgnoreCase(tubeID, "")) {
					synchronized (this) {
						// tubeID = "TUBE_" + year + "_" + appConfig.getSeqNbr(47,session);
						tubeID = "TUBE_" + year + "_" + Integer.parseInt(
								session.createSQLQuery("SELECT FIBER_TUBE FROM SEQ_TABLE").uniqueResult().toString());
						System.out.println("the tube id is " + tubeID);
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET FIBER_TUBE = FIBER_TUBE + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
					}
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

				else if (source.contains("CLT") == true) {
					fiberTubes.setSourceWareId("null");
					fiberTubes.setSourceId(source.split(":")[0]);
					fiberTubes.setSourceName(source.split(":")[1] + ":" + source.split(":")[2]);
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

				else if (destination.contains("CLT") == true) {
					fiberTubes.setDestinationWareId("null");
					fiberTubes.setDestinationId(destination.split(":")[0]);
					fiberTubes.setDestinationName(destination.split(":")[1] + ":" + destination.split(":")[2]);
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
				String aux_ID;
				int auxArraySize = 0;
				if (StringUtils.equalsIgnoreCase(tubeAuxFlag, "opened")
						|| StringUtils.equalsIgnoreCase(tubeAuxFlag, "new tube")) {

					query = session.createSQLQuery("delete from TUBE_AUXILIARY_POINTS where TUBE_ID='" + tubeID + "'");
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
										+ Integer
												.parseInt(session.createSQLQuery("SELECT FIBER_TUBE_AUX FROM SEQ_TABLE")
														.uniqueResult().toString());
								query = session
										.createSQLQuery("UPDATE SEQ_TABLE SET FIBER_TUBE_AUX = FIBER_TUBE_AUX + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
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

						}
					}
				}

				List<Object[]> tubeAuxiliary = session.createSQLQuery(
						"SELECT TUBE_ID,LONGITUDE,LATITUDE,WARE_ID, AUXILIARY_POINT_ID, AUXILIARY_POINT_NAME ,DISTANCE_FROM_SOURCE,SEQ_SORTING,AUXILIARY_ID,DRIVING_DISTANCE,GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS WHERE TUBE_ID='"
								+ tubeID + "' ORDER BY SEQ_SORTING ASC ")
						.list();

				strandsCount = session.createSQLQuery("select count (*) from FIBER_STRANDS WHERE TUBE_ID='" + tubeID
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
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					fiberTubes = session.createSQLQuery(
							"SELECT TUBE_ID,TUBE_NAME,FIBER_CABLE_ID,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY, TUBE_TYPE, TUBE_DEPLOYMENT, TUBE_NETWORK_LEVEL, TUBE_OWNER,CREATED_BY,LAST_MODIFIED_BY,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),TOTAL_DRIVING_DISTANCE,TOTAL_GEO_DISTANCE,DRAWING_TYPE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE,LENGTH,TUBE_NUMBER,TUBE_COLOR FROM FIBER_TUBES WHERE TUBE_ID='"
									+ selectedTube + "' ")
							.list();

					List<Object[]> tubeAuxiliary = session.createSQLQuery(
							"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,TUBE_ID,SEQ_SORTING, DRIVING_DISTANCE, GEO_DISTANCE,AUXILIARY_ID FROM TUBE_AUXILIARY_POINTS WHERE TUBE_ID='"
									+ selectedTube + "' ORDER BY SEQ_SORTING ASC ")
							.list();

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

							finalList.addAll(session.createSQLQuery(querySearch).list());
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
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					fiberStrands = session.createSQLQuery(
							"SELECT STRAND_ID,STRAND_NAME,FIBER_CABLE_ID,TUBE_ID,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY, "
									+ "STRAND_TYPE, STRAND_DEPLOYMENT, STRAND_NETWORK_LEVEL, STRAND_OWNER,CREATED_BY,LAST_MODIFIED_BY,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),TOTAL_DRIVING_DISTANCE, TOTAL_GEO_DISTANCE,LENGTH,DRAWING_TYPE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE,STRAND_NUMBER,STRAND_COLOR FROM FIBER_STRANDS WHERE STRAND_ID='"
									+ selectedStrand + "' ")
							.list();
					rtn.put("fiberStrands", fiberStrands);

					List<Object[]> strandAuxiliary = session.createSQLQuery(
							"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,STRAND_ID,SEQ_SORTING,DRIVING_DISTANCE,GEO_DISTANCE,AUXILIARY_ID FROM STRAND_AUXILIARY_POINTS WHERE STRAND_ID='"
									+ selectedStrand + "' ORDER BY SEQ_SORTING ASC ")
							.list();

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

							finalList.addAll(session.createSQLQuery(querySearch).list());
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
		session = almsessions.getSession();
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
					trenchId = "Trench_" + year + "_" + Integer
							.parseInt(session.createSQLQuery("SELECT TRENCH FROM SEQ_TABLE").uniqueResult().toString());
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET TRENCH = TRENCH + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
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

			else if (request.getParameter("SourceTrench").contains("CLT") == true) {
				trench.setSourceWareId("null");
				trench.setSourceId(request.getParameter("SourceTrench").split(":")[0]);
				trench.setSourceName(request.getParameter("SourceTrench").split(":")[1] + ":"
						+ request.getParameter("SourceTrench").split(":")[2]);
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

			else if (request.getParameter("DestinationTrench").contains("CLT") == true) {
				trench.setDestinationWareId("null");
				trench.setDestinationId(request.getParameter("DestinationTrench").split(":")[0]);
				trench.setDestinationName(request.getParameter("DestinationTrench").split(":")[1] + ":"
						+ request.getParameter("DestinationTrench").split(":")[2]);
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

			session.saveOrUpdate(trench);
			session.flush();

			int auxArraySize = 0;
			if (StringUtils.equalsIgnoreCase(trenchAuxFlag, "opened")
					|| StringUtils.equalsIgnoreCase(trenchAuxFlag, "new trench")) {
				query = session
						.createSQLQuery("delete from TRENCH_AUXILIARY_POINTS where TRENCH_ID='" + trenchId + "'");
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
									.createSQLQuery("SELECT TRENCHAUX FROM SEQ_TABLE").uniqueResult().toString());
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET TRENCHAUX = TRENCHAUX + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
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

					}
				}

			}

			List<Object[]> ductList = session.createSQLQuery(
					"SELECT B.DUCT_ID,B.DUCT_NAME,B.SOURCE_WARE_ID,B.SOURCE_ID,B.SOURCE_NAME,B.DESTINATION_WARE_ID,B.DESTINATION_ID,B.DESTINATION_NAME,B.SOURCE_LATITUDE,B.SOURCE_LONGITUDE,B.DESTINATION_LONGITUDE,B.DESTINATION_LATITUDE,B.SOURCE_CITY,B.DESTINATION_CITY,B.NUM_FIBERCABLES,B.NUM_FIBERTUBES,B.NUM_FIBERSTRANDS,B.LENGTH,B.TRENCH_ID FROM DUCTS B WHERE B.TRENCH_ID='"
							+ trenchId + "'")
					.list();

			List<Object[]> trenchAuxiliaryList = session.createSQLQuery(
					"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,TRENCH_ID,SEQ_SORTING,DRIVING_DISTANCE,GEO_DISTANCE,AUXILIARY_ID FROM TRENCH_AUXILIARY_POINTS WHERE TRENCH_ID='"
							+ trenchId + "' ORDER BY SEQ_SORTING ASC ")
					.list();

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
			session = almsessions.getSession();
			List<Object[]> resultList;
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
					if (StringUtils.equalsIgnoreCase(target, "client")) {
						Query result = session.createSQLQuery(
								"SELECT CLIENT_ID,DISPLAY_NAME,LONGITUDE,LATITUDE FROM CLIENTS WHERE CLIENT_ID LIKE :param OR DISPLAY_NAME LIKE :param");

						result.setParameter("param", "%" + searchkey + "%");
						result.setMaxResults(40);
						resultList = result.list();
						rtn.put("searchResult", resultList);

					} else if (StringUtils.equalsIgnoreCase(target, "site")) {
						System.out.println("siteeess");

						Query result = session.createSQLQuery(
								"SELECT WARE_ID,WARE_NAME,LONGITUDE,LATITUDE FROM WAREHOUSE WHERE UPPER(WARE_ID) LIKE :param OR UPPER(WARE_NAME) LIKE :param");

						result.setParameter("param", "%" + searchkey + "%");
						result.setMaxResults(40);
						// resultList=result.list();
						rtn.put("searchResult", result.list());

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

		session = almsessions.getSession();

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

					if (StringUtils.equalsIgnoreCase(junctionID, "")) {
						synchronized (this) {
							// junctionID = "JCT_" + year + "_" + appConfig.getSeqNbr(76,session);
							junctionID = "JCT_" + year + "_" + Integer.parseInt(
									session.createSQLQuery("SELECT JUNCTION FROM SEQ_TABLE").uniqueResult().toString());
							System.out.println("man id " + junctionID);
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET JUNCTION = JUNCTION +1");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
						}
						query = session.createSQLQuery("SELECT count(*) FROM JUNCTION b WHERE PHYSICAL_LAYER_ID = '"
								+ physLayerIdJunction + "' ");
						String countJunc = query.uniqueResult().toString();

						if (StringUtils.equalsIgnoreCase(countJunc, "0")) {

							physLayerNameJunction = physLayerNameJunction.concat("_J");
							query = session.createSQLQuery("UPDATE MANHOLE SET MANHOLE_NAME= '" + physLayerNameJunction
									+ "' where MANHOLE_ID='" + physLayerIdJunction + "' ");
							query.executeUpdate();
							query = session.createSQLQuery("UPDATE HANDHOLE SET HANDHOLE_NAME= '"
									+ physLayerNameJunction + "' where HANDHOLE_ID='" + physLayerIdJunction + "' ");
							query.executeUpdate();
						}

						Query insertJctQuery = session.createSQLQuery("INSERT INTO JUNCTION(JUNCTION_ID,JUNCTION_NAME,PHYSICAL_LAYER_ID"
								+ ",PHYSICAL_LAYER_NAME,LONGITUDE,LATITUDE,CAPACITY,JUNCTION_NUMBER,CITY,PROJECT_ID,CREATION_DATE,LAST_MODIFIED_DATE)"
								+ " VALUES ('" + junctionID
								+ "','" + junctionName + "','" + physLayerIdJunction + "','" + physLayerNameJunction
								+ "','" + junctionLong + "','" + junctionLat + "','" + junctionCapacity + "','"
								+ junctionNumber + "','" + junctionCity + "','" + projectId + "', TIMESTAMP '"
								+ junctionCreationDate + "',TIMESTAMP '" + lastModifiedDate + "')");
						insertJctQuery.executeUpdate();

						rtn.put("ManHandholeName", physLayerNameJunction);

					} else {
						Query updateJunction = session.createSQLQuery("UPDATE JUNCTION SET JUNCTION_NAME = '"
								+ junctionName + "',CAPACITY = '" + junctionCapacity + "',JUNCTION_NUMBER ='"
								+ junctionNumber + "', LAST_MODIFIED_DATE= TIMESTAMP '" + lastModifiedDate + "' "
								+ " WHERE JUNCTION_ID = '" + junctionID + "'");
						updateJunction.executeUpdate();
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
					
					String JctStrandNBSideA ="";   
					String JctTubeNBSideA="";
					String JctNetworkLevelSideA ="";
					
					String JctStrandNBSideB ="";   
					String JctTubeNBSideB="";
					String JctNetworkLevelSideB ="";
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
										+ Integer.parseInt(session.createSQLQuery("SELECT JUNCTION_MAP FROM SEQ_TABLE")
												.uniqueResult().toString());
								System.out.println("man id " + junctionID);
								query = session.createSQLQuery("UPDATE SEQ_TABLE SET JUNCTION_MAP = JUNCTION_MAP +1");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
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
							JctStrandNBSideA =itemParameters.getDictParameter().get(i).get("JctStrandNBSideA");   
							JctTubeNBSideA=itemParameters.getDictParameter().get(i).get("JctTubeNBSideA");
							JctNetworkLevelSideA = itemParameters.getDictParameter().get(i).get("JctNetworkLevelSideA");
								
							JctStrandNBSideB = itemParameters.getDictParameter().get(i).get("JctStrandNBSideB"); 
							JctTubeNBSideB= itemParameters.getDictParameter().get(i).get("JctTubeNBSideB");
						    JctNetworkLevelSideB =itemParameters.getDictParameter().get(i).get("JctNetworkLevelSideB");
					    	JctLocationTypeSideB = itemParameters.getDictParameter().get(i).get("JctLocationTypeSideB");
							JctLocationIdSideB = itemParameters.getDictParameter().get(i).get("JctLocationIdSideB");
							JctLocationNameSideB = itemParameters.getDictParameter().get(i).get("JctLocationNameSideB");
							JctWarehouseIdSideB = itemParameters.getDictParameter().get(i).get("JctWarehouseIdSideB");
							
							//
							
							Query insertMappingJctQuery = session
									.createSQLQuery("INSERT INTO JUNCTION_MAPPING(JCT_ID,SEQUENCE_NUMBER,JCT_MAPPING_ID,PHYSICAL_LAYER_ID,STRAND_ID_SIDE_A,STRAND_NAME_SIDE_A,"
											+ "TUBE_ID_SIDE_A,TUBE_NAME_SIDE_A,FIBER_ID_SIDE_A,FIBER_NAME_SIDE_A,STRAND_ID_SIDE_B,STRAND_NAME_SIDE_B,TUBE_ID_SIDE_B,TUBE_NAME_SIDE_B"
											+ ",FIBER_ID_SIDE_B,FIBER_NAME_SIDE_B,LOCATION_TYPE_SIDE_A,LOCATION_ID_SIDE_A,LOCATION_NAME_SIDE_A,WAREHOUSE_ID_SIDE_A,STRAND_NB_SIDE_A,"
											+ "TUBE_NB_SIDE_A,NETWORK_LEVEL_SIDE_A,STRAND_NB_SIDE_B,TUBE_NB_SIDE_B,NETWORK_LEVEL_SIDE_B,LOCATION_TYPE_SIDE_B,LOCATION_ID_SIDE_B,LOCATION_NAME_SIDE_B,WAREHOUSE_ID_SIDE_B)"
											+ " VALUES ('" + junctionID + "','"
											+ JctSeq + "','" + mappingJctID + "','" + physLayerIdJunction + "','"
											+ JctStrandIdSideA + "','" + JctStrandNameSideA + "','" + JctTubeIdSideA
											+ "','" + JctTubeNameSideA + "','" + JctFiberIdSideA + "','"
											+ JctFiberNameSideA + "','" + JctStrandIdSideB + "','" + JctStrandNameSideB
											+ "','" + JctTubeIdSideB + "','" + JctTubeNameSideB + "','"
											+ JctFiberIdSideB + "','" + JctFiberNameSideB + "','" + JctLocationTypeSideA
											+ "','" + JctLocationIdSideA + "','" + JctLocationNameSideA + "','"
											+ JctWarehouseIdSideA+ "','" + JctStrandNBSideA + "','" + JctTubeNBSideA + "','" + JctNetworkLevelSideA + "','" + JctStrandNBSideB+ "','" 
											+ JctTubeNBSideB + "','" + JctNetworkLevelSideB+ "','" + JctLocationTypeSideB + "','" + JctLocationIdSideB
											+ "','" + JctLocationNameSideB + "','" + JctWarehouseIdSideB + "' )");
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
							JctStrandNBSideA =itemParameters.getDictParameterTemp().get(i).get("JctStrandNBSideA");   
							JctTubeNBSideA=itemParameters.getDictParameterTemp().get(i).get("JctTubeNBSideA");
							JctNetworkLevelSideA = itemParameters.getDictParameterTemp().get(i).get("JctNetworkLevelSideA");
								
							JctStrandNBSideB = itemParameters.getDictParameterTemp().get(i).get("JctStrandNBSideB"); 
							JctTubeNBSideB= itemParameters.getDictParameterTemp().get(i).get("JctTubeNBSideB");
						    JctNetworkLevelSideB =itemParameters.getDictParameterTemp().get(i).get("JctNetworkLevelSideB");
					    	JctLocationTypeSideB = itemParameters.getDictParameterTemp().get(i).get("JctLocationTypeSideB");
							JctLocationIdSideB = itemParameters.getDictParameterTemp().get(i).get("JctLocationIdSideB");
							JctLocationNameSideB = itemParameters.getDictParameterTemp().get(i).get("JctLocationNameSideB");
							JctWarehouseIdSideB = itemParameters.getDictParameterTemp().get(i).get("JctWarehouseIdSideB");
							//

							Query insertMappingJctQuery = session
									.createSQLQuery("UPDATE JUNCTION_MAPPING SET SEQUENCE_NUMBER= '" + JctSeq
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
											+ "',STRAND_NB_SIDE_A = '" + JctStrandNBSideA
											+ "',TUBE_NB_SIDE_A = '" + JctTubeNBSideA
											+ "',NETWORK_LEVEL_SIDE_A = '" + JctNetworkLevelSideA
											+ "',STRAND_NB_SIDE_B = '" + JctStrandNBSideB
											+ "',TUBE_NB_SIDE_B = '" + JctTubeNBSideB
											+ "',NETWORK_LEVEL_SIDE_B = '" + JctNetworkLevelSideB
											+ "',LOCATION_TYPE_SIDE_B = '" + JctLocationTypeSideB
											+ "',LOCATION_ID_SIDE_B = '" + JctLocationIdSideB
											+ "',LOCATION_NAME_SIDE_B = '" + JctLocationNameSideB
											+ "',WAREHOUSE_ID_SIDE_B = '" + JctWarehouseIdSideB+"'"
											+ " WHERE JCT_MAPPING_ID = '" + mappingId + "'");

							insertMappingJctQuery.executeUpdate();

						}
					}

					for (int i = 0; i < itemParameters.getDictParameterDel().size(); i++) {
						Query deleteJunctionMapping = session
								.createSQLQuery("DELETE FROM JUNCTION_MAPPING WHERE JCT_MAPPING_ID='"
										+ itemParameters.getDictParameterDel().get(i).get("jctMappingId") + "'");
						deleteJunctionMapping.executeUpdate();
					}

					List<Object[]> junctionMappingPts = session.createSQLQuery(
							"SELECT DISTINCT SEQUENCE_NUMBER,STRAND_ID_SIDE_A,STRAND_NAME_SIDE_A,TUBE_ID_SIDE_A,TUBE_NAME_SIDE_A,FIBER_ID_SIDE_A,FIBER_NAME_SIDE_A,STRAND_ID_SIDE_B,STRAND_NAME_SIDE_B,TUBE_ID_SIDE_B,TUBE_NAME_SIDE_B,FIBER_ID_SIDE_B,FIBER_NAME_SIDE_B,JCT_MAPPING_ID,JCT_ID,(SELECT JUNCTION_NAME FROM JUNCTION WHERE JUNCTION_ID='"
									+ junctionID + "'),(SELECT PHYSICAL_LAYER_ID FROM JUNCTION WHERE JUNCTION_ID='"
									+ junctionID
									+ "'),LOCATION_TYPE_SIDE_A,LOCATION_ID_SIDE_A,LOCATION_NAME_SIDE_A,WAREHOUSE_ID_SIDE_A,STRAND_NB_SIDE_A,TUBE_NB_SIDE_A,NETWORK_LEVEL_SIDE_A,STRAND_NB_SIDE_B,TUBE_NB_SIDE_B,NETWORK_LEVEL_SIDE_B,LOCATION_TYPE_SIDE_B,LOCATION_ID_SIDE_B,LOCATION_NAME_SIDE_B,WAREHOUSE_ID_SIDE_B FROM JUNCTION_MAPPING B WHERE B.JCT_ID='"
									+ junctionID + "' ")
							.list();

					List<Object[]> junctionDetails = session.createSQLQuery(
							"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,A.LONGITUDE,A.LATITUDE,(SELECT COUNT(DISTINCT B.STRAND_ID_SIDE_A) FROM JUNCTION_MAPPING B WHERE B.STRAND_ID_SIDE_A !='null' AND B.JCT_ID='"
									+ junctionID
									+ "'),(SELECT COUNT(DISTINCT B.STRAND_ID_SIDE_B) FROM JUNCTION_MAPPING B WHERE B.STRAND_ID_SIDE_B !='null' AND B.JCT_ID='"
									+ junctionID + "') FROM JUNCTION A WHERE A.JUNCTION_ID='" + junctionID + "' ")
							.list();

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
			session = almsessions.getSession();

			String physLayerSearch = request.getParameter("physLayerSearch");
			String physicalLayer = request.getParameter("physicalLayer");
			String ProjectId = request.getParameter("ProjectId");

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
					if (StringUtils.equalsIgnoreCase(physicalLayer, "Manhole")) {
						physicalLayerDetailsQuery = session.createSQLQuery(
								"SELECT MANHOLE_NAME,MANHOLE_ID,LONGITUDE,LATITUDE,CITY FROM MANHOLE  WHERE PROJECT_ID ='"
										+ ProjectId
										+ "' AND ( (UPPER(MANHOLE_ID) LIKE UPPER(:param)  OR UPPER(MANHOLE_NAME) LIKE UPPER(:param)) ) ");

						physicalLayerDetailsQuery.setParameter("param", "%" + physLayerSearch + "%");
						physicalLayerDetailsQuery.setFirstResult(0);
						physicalLayerDetailsQuery.setMaxResults(40);

					}

					else {

						physicalLayerDetailsQuery = session.createSQLQuery(
								"SELECT HANDHOLE_NAME,HANDHOLE_ID,LONGITUDE,LATITUDE,CITY FROM HANDHOLE a WHERE PROJECT_ID ='"
										+ ProjectId
										+ "' AND ( (UPPER(HANDHOLE_ID) LIKE UPPER(:param)  OR UPPER(HANDHOLE_NAME) LIKE UPPER(:param)))  ");

						physicalLayerDetailsQuery.setParameter("param", "%" + physLayerSearch + "%");
						physicalLayerDetailsQuery.setFirstResult(0);
						physicalLayerDetailsQuery.setMaxResults(40);

					}

					if (physicalLayerDetailsQuery.list() != null && physicalLayerDetailsQuery.list().size() != 0) {

						System.out.println("searchPhysicalLayResult " + physicalLayerDetailsQuery.list());
						rtn.put("searchPhysicalLayerDetails", physicalLayerDetailsQuery.list());
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
		session = almsessions.getSession();

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
								session.createSQLQuery("SELECT DUCT FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET DUCT = DUCT + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
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
				} else if (request.getParameter("SourceDuct").contains("CLT") == true) {
					duct.setSourceWareId("null");
					duct.setSourceId(request.getParameter("SourceDuct").split(":")[0]);
					duct.setSourceName(request.getParameter("SourceDuct").split(":")[1] + ":"
							+ request.getParameter("SourceDuct").split(":")[2]);
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
				} else if (request.getParameter("DestinationDuct").contains("CLT") == true) {
					duct.setDestinationWareId("null");
					duct.setDestinationId(request.getParameter("DestinationDuct").split(":")[0]);
					duct.setDestinationName(request.getParameter("DestinationDuct").split(":")[1] + ":"
							+ request.getParameter("DestinationDuct").split(":")[2]);
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

				session.saveOrUpdate(duct);
				session.flush();

				query = session.createSQLQuery("UPDATE TRENCH SET NUM_DUCTS = NUM_DUCTS + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();

				int auxArraySize = 0;
				if (StringUtils.equalsIgnoreCase(ductAuxFlag, "opened")
						|| StringUtils.equalsIgnoreCase(ductAuxFlag, "new duct")) {
					query = session.createSQLQuery("delete from DUCT_AUXILIARY_POINTS where DUCT_ID='" + ductId + "'");
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
										.createSQLQuery("SELECT DUCTAUX FROM SEQ_TABLE").uniqueResult().toString());
								query = session.createSQLQuery("UPDATE SEQ_TABLE SET DUCTAUX = DUCTAUX + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
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

						}
					}
				}
				List<Object[]> ductAuxiliaryList = session.createSQLQuery(
						"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,DUCT_ID,SEQ_SORTING,DRIVING_DISTANCE,GEO_DISTANCE,AUXILIARY_ID FROM DUCT_AUXILIARY_POINTS WHERE DUCT_ID='"
								+ ductId + "' ORDER BY SEQ_SORTING ASC ")
						.list();

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

		session = almsessions.getSession();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			try {
				@SuppressWarnings("unchecked")

				String trenchId = request.getParameter("trenchId");

				query = session.createSQLQuery("select count (*) from DUCTS where trench_Id like :param");
				query.setParameter("param", "%" + trenchId + "%");
				Object countDucts = query.uniqueResult();
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

		session = almsessions.getSession();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				String JunctionID = request.getParameter("JunctionID");
				// System.out.println("JunctionID "+JunctionID);
				try {
					List<Object[]> junctionDetails = session.createSQLQuery(
							"SELECT DISTINCT A.JUNCTION_NAME,A.JUNCTION_ID,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.LONGITUDE,A.LATITUDE,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,(SELECT COUNT(DISTINCT B.STRAND_ID_SIDE_A) FROM JUNCTION_MAPPING B WHERE B.STRAND_ID_SIDE_A!='null' AND B.JCT_ID='"
									+ JunctionID
									+ "'),(SELECT COUNT(DISTINCT B.STRAND_ID_SIDE_B) FROM JUNCTION_MAPPING B WHERE B.STRAND_ID_SIDE_B !='null' AND B.JCT_ID='"
									+ JunctionID
									+ "'), TO_CHAR(A.CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(A.LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM')  FROM JUNCTION A WHERE A.JUNCTION_ID='"
									+ JunctionID + "' ")
							.list();

					List<Object[]> junctionMappingPts = session.createSQLQuery(
							"SELECT DISTINCT SEQUENCE_NUMBER,JCT_MAPPING_ID,STRAND_ID_SIDE_A,STRAND_NAME_SIDE_A,TUBE_ID_SIDE_A,TUBE_NAME_SIDE_A,FIBER_ID_SIDE_A,FIBER_NAME_SIDE_A,STRAND_ID_SIDE_B,STRAND_NAME_SIDE_B,TUBE_ID_SIDE_B,TUBE_NAME_SIDE_B,FIBER_ID_SIDE_B,FIBER_NAME_SIDE_B,LOCATION_TYPE_SIDE_A,LOCATION_ID_SIDE_A,LOCATION_NAME_SIDE_A,WAREHOUSE_ID_SIDE_A,STRAND_NB_SIDE_A,TUBE_NB_SIDE_A,NETWORK_LEVEL_SIDE_A,STRAND_NB_SIDE_B,TUBE_NB_SIDE_B,NETWORK_LEVEL_SIDE_B,LOCATION_TYPE_SIDE_B,LOCATION_ID_SIDE_B,LOCATION_NAME_SIDE_B,WAREHOUSE_ID_SIDE_B FROM JUNCTION_MAPPING B WHERE B.JCT_ID='"
									+ JunctionID + "' ORDER BY SEQUENCE_NUMBER ASC")
							.list();

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

		session = almsessions.getSession();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;

		} else {
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					String junctionID = request.getParameter("junctionID");

					List<Object[]> junctionMappingPts = session.createSQLQuery(
							"SELECT DISTINCT SEQUENCE_NUMBER,JCT_MAPPING_ID,STRAND_ID_SIDE_A,STRAND_NAME_SIDE_A,TUBE_ID_SIDE_A,TUBE_NAME_SIDE_A,FIBER_ID_SIDE_A,FIBER_NAME_SIDE_A,STRAND_ID_SIDE_B,STRAND_NAME_SIDE_B,TUBE_ID_SIDE_B,TUBE_NAME_SIDE_B,FIBER_ID_SIDE_B,FIBER_NAME_SIDE_B,(SELECT A.JUNCTION_NAME FROM JUNCTION A WHERE A.JUNCTION_ID='"
									+ junctionID + "'),(SELECT JUNCTION_NUMBER FROM JUNCTION  WHERE JUNCTION_ID='"
									+ junctionID + "') FROM JUNCTION_MAPPING B WHERE B.JCT_ID='" + junctionID + "' ")
							.list();

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

		session = almsessions.getSession();

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

					Query updateJunction = session.createSQLQuery("UPDATE JUNCTION SET JUNCTION_NAME = '" + junctionName
							+ "',CAPACITY = '" + junctionCapacity + "',JUNCTION_NUMBER ='" + junctionNumber + "' "
							+ " WHERE JUNCTION_ID = '" + junctionID + "'");
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
										+ Integer.parseInt(session.createSQLQuery("SELECT JUNCTION_MAP FROM SEQ_TABLE")
												.uniqueResult().toString());
								System.out.println("man id " + junctionID);
								query = session.createSQLQuery("UPDATE SEQ_TABLE SET JUNCTIN_MAP = JUNCTION_MAP +1");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
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
									.createSQLQuery("INSERT INTO JUNCTION_MAPPING VALUES ('" + junctionID + "','"
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
									.createSQLQuery("UPDATE JUNCTION_MAPPING SET SEQUENCE_NUMBER= '" + JctSeq
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
								.createSQLQuery("DELETE FROM JUNCTION_MAPPING WHERE JCT_MAPPING_ID='"
										+ itemParameters.getDictParameterDel().get(i).get("jctMappingId") + "'");
						deleteJunctionMapping.executeUpdate();
					}

					// List<Object[]> countJct=session.createSQLQuery("select count (*) from
					// JUNCTION where JUNCTION_ID = '"+junctionID+"' ").list();
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
		session = almsessions.getSession();
		String search = request.getParameter("search");

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createSQLQuery(
						"SELECT MANHOLE_ID,MANHOLE_NAME,CITY,LONGITUDE,LATITUDE FROM MANHOLE a WHERE UPPER(MANHOLE_ID) LIKE UPPER(:param) OR UPPER(MANHOLE_NAME) LIKE UPPER(:param) ");

				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("globalList", query.list());
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
		Query query;
		session = almsessions.getSession();
		String search = request.getParameter("search");

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createSQLQuery(
						"SELECT HANDHOLE_ID,HANDHOLE_NAME,CITY,LONGITUDE,LATITUDE FROM HANDHOLE b WHERE UPPER(HANDHOLE_ID) LIKE UPPER(:param) OR UPPER(HANDHOLE_NAME) LIKE UPPER(:param) ");

				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("globalList", query.list());

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
		Query query;
		session = almsessions.getSession();
		String search = request.getParameter("search");

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createSQLQuery(
						"SELECT DB_ID,DB_NAME,CITY,DB_LONGITUDE,DB_LATITUDE FROM DISTRIBUTION_BOARD c WHERE UPPER(DB_ID) LIKE UPPER(:param) OR UPPER(DB_NAME) LIKE UPPER(:param) ");

				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("globalList", query.list());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getDbData due to \n " + exceptionAsString);
				logger.info("Error in getDbData due to \n " + exceptionAsString);
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

	@RequestMapping(value = "/getNetworkLevel", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getNetworkLevel(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String pathID = request.getParameter("pathID");
		String target = request.getParameter("target");
		List<Object[]> networkLevel = null;

		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				if (target.equals("tube")) {
					networkLevel = session.createSQLQuery(
							"SELECT FIBER_NETWORK_LEVEL FROM FIBER_CABLES WHERE FIBER_CABLE_ID ='" + pathID + "' ")
							.list();
				} else {
					networkLevel = session
							.createSQLQuery(
									"SELECT TUBE_NETWORK_LEVEL FROM FIBER_TUBES WHERE TUBE_ID ='" + pathID + "' ")
							.list();
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

		session = almsessions.getSession();
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
				// List Query = session.createSQLQuery("select * from FIBER_OWNER_COLOR
				// ").list();
				if (itemParameters.getDictParameter().size() > 0) {
					for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
						// System.out.println(itemParameters.getDictParameter().get(i).get("id"));
						id = itemParameters.getDictParameter().get(i).get("id");
						owner = itemParameters.getDictParameter().get(i).get("owner");
						color = itemParameters.getDictParameter().get(i).get("color");
						System.out.println(itemParameters.getDictParameter().get(i).get("owner") + " color "
								+ itemParameters.getDictParameter().get(i).get("color"));
						List Query = session
								.createSQLQuery("select * from FIBER_OWNER_COLOR WHERE FIBER_OWNER = '" + owner + "' ")
								.list();
						System.out.println("yes size " + Query.size());
						// if(update == String.valueOf("0") ) {
						// if(StringUtils.equalsIgnoreCase(update, "0")) {
						if (Query.size() > 0) {
							// System.out.println("yes update is 1 "+update);
							// saveCableColorQuery = session.createSQLQuery("INSERT INTO FIBER_OWNER_COLOR
							// (FIBER_COLOR_OWNER_ID,FIBER_COLOR_OWNER,FIBER_OWNER) VALUES ( '" + id + "' ,
							// '" + color + "' , '"+ owner+"') ");
							saveCableColorQuery = session
									.createSQLQuery("UPDATE FIBER_OWNER_COLOR SET FIBER_COLOR_OWNER = '" + color
											+ "' WHERE FIBER_OWNER = '" + owner + "' ");
						} else {
							// saveCableColorQuery = session.createSQLQuery("UPDATE FIBER_OWNER_COLOR SET
							// FIBER_COLOR_OWNER = '" + color + "' WHERE FIBER_OWNER = '" + owner + "' ");
							saveCableColorQuery = session.createSQLQuery(
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
	 * session.createSQLQuery(
	 * "SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),DM_NAME FROM MANHOLE  "
	 * ) .list(); List<Object[]> ManholeMarkerPointsBetween = new
	 * ArrayList<Object[]>(); //
	 * System.out.println("manholeList "+mapper.writeValueAsString(manholeList));
	 * ManholeMarkerPointsBetween = findMarkerPointsArrayBetween(manholeList,
	 * Double.valueOf(startLongPoint), Double.valueOf(startLatPoint),
	 * Double.valueOf(endLongPoint), Double.valueOf(endLatPoint), "Manhole");
	 * 
	 * List<?> handholeList = session.createSQLQuery(
	 * "SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,LONGITUDE,LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),DM_NAME FROM HANDHOLE"
	 * ) .list(); List<Object[]> HandholeMarkerPointsBetween = new
	 * ArrayList<Object[]>(); HandholeMarkerPointsBetween =
	 * findMarkerPointsArrayBetween(handholeList, Double.valueOf(startLongPoint),
	 * Double.valueOf(startLatPoint), Double.valueOf(endLongPoint),
	 * Double.valueOf(endLatPoint), "Handhole");
	 * 
	 * List<?> distribBoardList = session.createSQLQuery(
	 * "SELECT DISTINCT DB_ID,DB_LONGITUDE,DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY FROM DISTRIBUTION_BOARD"
	 * ) .list(); List<Object[]> DistribBoardMarkerPointsBetween = new
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
	 * fiberQuery = session.createSQLQuery(
	 * "SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL FROM FIBER_CABLES A LEFT  JOIN FIBER_AUXILIARY_POINTS D ON A.FIBER_CABLE_ID=D.FIBER_CABLE_ID WHERE A.SOURCE_ID IN (:param1) OR A.DESTINATION_ID IN (:param1) OR D.AUXILIARY_POINT_ID IN (:param1)"
	 * ); fiberQuery.setParameterList("param1", idsArray); List<Object>
	 * fiberCableMarkerPointsBetween = fiberQuery.list(); //
	 * System.out.println("fiberCableMarkerPointsBetween //
	 * "+mapper.writeValueAsString(fiberCableMarkerPointsBetween));
	 * 
	 * Query fiberAuxiliaryQuery = session.createSQLQuery(
	 * "SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND AUXILIARY_POINT_ID IN (:param1) ORDER BY B.SEQ_SORTING ASC"
	 * ); fiberAuxiliaryQuery.setParameterList("param1",
	 * findListId(fiberCableMarkerPointsBetween, "FiberCable")); List<Object>
	 * fiberAuxiliaryMarkerPointsBetween = fiberAuxiliaryQuery.list();
	 * 
	 * /* Query fiberTubesQuery = session.
	 * createSQLQuery("SELECT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME FROM FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID AND b.SOURCE_ID IN (:param1) OR b.DESTINATION_ID IN (:param1)"
	 * ); fiberTubesQuery.setParameterList("param1",idsArray); List<Object>
	 * fiberTubes = fiberTubesQuery.list();
	 * 
	 * 
	 * Query tubesAuxiliariesQuery = session.
	 * createSQLQuery("SELECT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID FROM TUBE_AUXILIARY_POINTS c,FIBER_TUBES b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.TUBE_ID=c.TUBE_ID AND AUXILIARY_POINT_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC"
	 * ); tubesAuxiliariesQuery.setParameterList("param1",idsArray); List<Object>
	 * tubesAuxiliaries = tubesAuxiliariesQuery.list();
	 * 
	 * Query fiberStrandsQuery = session.
	 * createSQLQuery("SELECT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE,b.DESTINATION,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME FROM FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID AND b.SOURCE IN (:param1) OR b.DESTINATION IN (:param1)"
	 * ); fiberStrandsQuery.setParameterList("param1",idsArray); List<Object>
	 * fiberStrands = fiberStrandsQuery.list();
	 * 
	 * //Query strandsAuxiliariesQuery = session.
	 * createSQLQuery("SELECT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.AUXILIARY_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b,FIBER_CABLES a WHERE a.FIBER_CABLE_ID=b.FIBER_CABLE_ID and b.STRAND_ID=c.STRAND_ID AND AUXILIARY_POINT_ID IN (:param1) ORDER BY c.SEQ_SORTING ASC "
	 * ); //strandsAuxiliariesQuery.setParameterList("param1",idsArray);
	 * //List<Object> strandsAuxiliaries = strandsAuxiliariesQuery.list();
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

			if (Target == "Manhole" || Target == "Handhole" || Target == "ManHandhole_OutOfZone" || Target == "Entreprise" || Target == "Transmission") {
				pointDist = haversine(closestLatPoint, closestLongPoint, Double.valueOf((String) objectArray[3]),
						Double.valueOf((String) objectArray[2]));
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

				if (Target == "DB_OutOfZone" || Target == "DistribBoard") {
					listofDistances[j] = Double.valueOf(String.valueOf(((Object[]) nearstPointsArray.get(j))[8]));
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

	// for sorting array in terms of distance
	public Object[] findUsingEnhancedForLoop(double distance, List<?> ListOfObjects, String Target) {
		double tempDistance = 0.0;

		for (int x = 0; x < ListOfObjects.size(); x++) {
			if (Target == "DB_OutOfZone" || Target == "DistribBoard") {
				tempDistance = Double.valueOf(String.valueOf(((Object[]) ListOfObjects.get(x))[8]));

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
		Query query;
		session = almsessions.getSession();
		List<Object[]> globalList = new ArrayList<Object[]>();
		List<String> searchResult = new ArrayList<String>();
		search = request.getParameter("search");

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				if (Boolean.parseBoolean(request.getParameter("SearchForSite")) == true) {
					System.out.println("Site Checkbox " + request.getParameter("SearchForSite"));

					query = session.createSQLQuery(
							"SELECT WARE_ID,WARE_NAME,SITE_ID,LONGITUDE,LATITUDE,CITY FROM WAREHOUSE a WHERE UPPER(WARE_ID) LIKE UPPER(:param)  OR UPPER(WARE_NAME) LIKE UPPER(:param) OR UPPER(SITE_ID) LIKE UPPER(:param) ");

					query.setParameter("param", "%" + search + "%");
					query.setFirstResult(0);
					query.setMaxResults(40);

					globalList.addAll(query.list());

				}

				if (Boolean.parseBoolean(request.getParameter("SearchForClient")) == true) {
					System.out.println("Client Checkbox " + request.getParameter("SearchForClient"));

					query = session.createSQLQuery(
							"SELECT FIRST_NAME,LAST_NAME,MOBILE_NUMBER,LONGITUDE,LATITUDE,PHYSICAL_LOCATION FROM CLIENTS a WHERE UPPER(FIRST_NAME) LIKE UPPER(:param)  OR UPPER(LAST_NAME) LIKE UPPER(:param) or MOBILE_NUMBER LIKE :param ");

					query.setParameter("param", "%" + search + "%");
					query.setFirstResult(0);
					query.setMaxResults(40);

					globalList.addAll(query.list());

				}

				query = session.createSQLQuery(
						"SELECT MANHOLE_ID,MANHOLE_NAME FROM MANHOLE a WHERE UPPER(MANHOLE_ID) LIKE UPPER(:param)  OR UPPER(MANHOLE_NAME) LIKE UPPER(:param) ");

				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				globalList.addAll(query.list());

				query = session.createSQLQuery(
						"SELECT HANDHOLE_ID,HANDHOLE_NAME FROM HANDHOLE b WHERE UPPER(HANDHOLE_ID) LIKE UPPER(:param) OR UPPER(HANDHOLE_NAME) LIKE UPPER(:param) ");

				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				globalList.addAll(query.list());

				query = session.createSQLQuery(
						"SELECT DB_ID,DB_NAME FROM DISTRIBUTION_BOARD c WHERE UPPER(DB_ID) LIKE UPPER(:param) OR UPPER(DB_NAME) LIKE UPPER(:param) ");

				query.setParameter("param", "%" + search + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				globalList.addAll(query.list());

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

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in SearchForSource due to \n " + exceptionAsString);
				logger.info("Error in SearchForSource due to \n " + exceptionAsString);
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

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(1500);
	}

}
