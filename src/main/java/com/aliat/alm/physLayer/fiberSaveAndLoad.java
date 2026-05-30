package com.aliat.alm.physLayer;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.GetSystemSettings;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.AccessCableJunction;
import com.aliat.alm.models.FiberAuxPoints;
import com.aliat.alm.models.FiberCable;
import com.aliat.alm.models.FiberDuct;
import com.aliat.alm.models.FiberStrands;
import com.aliat.alm.models.FiberTubes;
import com.aliat.alm.models.PhysicalLayerActivity;
import com.aliat.alm.models.StrandAuxPoints;
import com.aliat.alm.models.TubeAuxPoints;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.aliat.alm.utils.NumericUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aliat.alm.utils.RequestUtils;

@Controller
public class fiberSaveAndLoad {

	private final Logger logger = Logger.getLogger(PhysicalLayerController.class.getName());
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	Permissions permissions;
	@Autowired
	GetSystemSettings getSystemSettings;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fiberPathSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fiberPathSave(Locale locale, Model model, @ModelAttribute ItemParameters itemParameters,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		int seq, counter = 0;
		Session session = null;
		Transaction tx = null;
		Query query = null;

		System.out.println("Welcome fiberPathSave");

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
			String ipAddress = RequestUtils.getIpAddress(request);
			String updateModfUser = request.getParameter("updateModfUser");
			PhysicalLayerActivity PhyAct = new PhysicalLayerActivity();

			try {

				String PhyActID = "PHY_ACT_" + year + "_" + Integer.parseInt(
						session.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
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
				} else {
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

							counter++;
							if (counter % 50 == 0) {
								session.flush();
								session.clear();
							}
						}
					}
				}

				// Saving the fiber ducts
				
				session.flush();
				session.clear();

				query = session.createNativeQuery("delete from FIBER_DUCT where FIBER_PATH_ID='" + fiberpathID + "'");
				query.executeUpdate();

				System.out.println("Just before saving fiberDucts, the size is: "
						+ itemParameters.getDictParameterFiberDuct().size());

				if (itemParameters.getDictParameterFiberDuct() != null
						&& itemParameters.getDictParameterFiberDuct().size() > 0) {

					counter = 0;
					for (Map<String, String> ductMap : itemParameters.getDictParameterFiberDuct()) {
						seq = ((Number) session.createNativeQuery("SELECT FIBER_DUCT_SEQ.NEXTVAL FROM DUAL")
								.uniqueResult()).intValue();

						String fiberDuctId = "FIBERDUCT_" + year + "_" + seq;

						FiberDuct fiberDuct = new FiberDuct();
						fiberDuct.setFiberDuctId(fiberDuctId);
						fiberDuct.setFiberPathId(fiberpathID);

						// Sequence
						if (ductMap.get("sequence") != null && !ductMap.get("sequence").isEmpty()) {
							fiberDuct.setSequenceNo(NumericUtils.parseInteger(ductMap.get("sequence")));
						}

						// Duct Info
						fiberDuct.setDuctId(ductMap.get("ductId"));
						fiberDuct.setDuctName(ductMap.get("ductName"));

						// From
						fiberDuct.setFromSequence(ductMap.get("fromSequence"));
						fiberDuct.setFromAuxId(ductMap.get("fromAuxId"));
						fiberDuct.setFromAuxName(ductMap.get("fromAuxName"));
						fiberDuct.setFromLongitude(ductMap.get("fromLongitude"));
						fiberDuct.setFromLatitude(ductMap.get("fromLatitude"));

						// To
						fiberDuct.setToSequence(ductMap.get("toSequence"));
						fiberDuct.setToAuxId(ductMap.get("toAuxId"));
						fiberDuct.setToAuxName(ductMap.get("toAuxName"));
						fiberDuct.setToLongitude(ductMap.get("toLongitude"));
						fiberDuct.setToLatitude(ductMap.get("toLatitude"));

						// Distances

						if (ductMap.get("distance") != null && !ductMap.get("distance").isEmpty()) {
							fiberDuct.setDistanceKm(NumericUtils.parseDouble(ductMap.get("distance")));
						}

						if (ductMap.get("geoDistance") != null && !ductMap.get("geoDistance").isEmpty()) {
							fiberDuct.setGeoDistanceKm(NumericUtils.parseDouble(ductMap.get("geoDistance")));
						}
						session.saveOrUpdate(fiberDuct);
						System.out.println("After saving for fiberDuct");
						counter++;
						if (counter % 50 == 0) {
							session.flush();
							session.clear();
						}
					}
				}

				session.flush();
				session.clear();

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
					fiberTubes.setTubefiberNetLevel(tubenetlevel);
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
				logger.log(Level.SEVERE, "Error in FiberPathSave due to ", e);
				rtn.put("FiberPathId", null);
				rtn.put("tubeIdArrayList", null);
				rtn.put("strandsOfTubes", null);
				rtn.put("fiberAux", null);
				rtn.put("tubeIdArray", null);
				rtn.put("fiberTubes", null);
				rtn.put("fiberStrands", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}
}
