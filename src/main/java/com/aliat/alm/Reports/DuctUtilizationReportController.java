package com.aliat.alm.Reports;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.dto.DuctSegment;
import com.aliat.alm.dto.DuctSegmentDTO;
import com.aliat.alm.dto.FiberCableDetailsDTO;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.aliat.alm.models.Duct;
import com.aliat.alm.models.DuctAuxPoints;
import com.aliat.alm.models.FiberAuxPoints;
import com.aliat.alm.models.FiberCable;
import com.aliat.alm.models.FiberDuct;
import com.aliat.alm.models.StrandUtilizationReport;

@Controller
public class DuctUtilizationReportController {
	private static final Logger logger = Logger.getLogger(DuctUtilizationReportController.class.getName());

	@Autowired
	Notify notifications;
	private Session session = null;
	@SuppressWarnings("rawtypes")
	Query query;
	Object[] result;
	private static StringWriter sw;
	private static String exceptionAsString;
	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value = "/DuctUtilizationReport", method = RequestMethod.GET)
	public String DuctUtilizationReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				notifications.headerNotifications(session, model);
				try {

				} catch (Exception e) {
					logger.log(Level.SEVERE, "Error in DuctUtilizationReport due to", e);
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
		}
		return "Reports/DuctUtilizationReport";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDuctAutComplete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDuctAutComplete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();

		System.out.println("Welcome to getDuctAutoComplete");

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		String duct = request.getParameter("requestValue");
		System.out.println("duct is " + duct);
		Session session = null;
		try {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				String sql = "SELECT duct_id, duct_name " + "FROM ducts " + "WHERE UPPER(duct_id) LIKE UPPER(:duct) "
						+ "OR UPPER(duct_name) LIKE UPPER(:duct) " + "ORDER BY duct_name " + "FETCH FIRST 40 ROWS ONLY";

				System.out.println("sql is " + sql);

				List<Object[]> ductDetails = session.createNativeQuery(sql).setParameter("duct", "%" + duct + "%")
						.getResultList();

				rtn.put("ducts", ductDetails);
			}

		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error in getDuctAutComplete", e);
			rtn.put("ducts", new ArrayList<>());

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GenerateDuctUtilizationReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateDuctUtilizationReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String ductID = request.getParameter("ductID");
		System.out.println("Welcome to Generate Duct Util Report");
		System.out.println("ductID is " + ductID);

		String str = "";
		Duct duct = new Duct();
		List<DuctAuxPoints> listDuctAuxPoints = new ArrayList<DuctAuxPoints>();
		List<DuctAuxPoints> renderPoints = new ArrayList<>();
		List<FiberDuct> listFiberDuct = new ArrayList<FiberDuct>();
		List<Object[]> listFiberCable = new ArrayList<Object[]>();
		List<FiberAuxPoints> listAuxCables = new ArrayList<FiberAuxPoints>();
		LinkedHashMap<String, List<FiberAuxPoints>> cablesAuxMap = new LinkedHashMap<String, List<FiberAuxPoints>>();
		List<Object[]> fiberOwnerColor = new ArrayList<Object[]>();
		Map<String, String> fiberOwnerColorMap = new HashMap<>();
		Map<String, DuctAuxPoints> usedPointsMap = new LinkedHashMap<>();
		LinkedHashMap<String, FiberCableDetailsDTO> fiberCablesDetails = new LinkedHashMap<String, FiberCableDetailsDTO>();
		Map<String, Double> ductSeqMap = new HashMap<>();
		Map<String, DuctAuxPoints> ductPointById = new HashMap<>();
		List<DuctSegmentDTO> listDuctSegmentDTO = new ArrayList<DuctSegmentDTO>();
		List<DuctSegment> reportSegments = new ArrayList<>();
		int gapSeq = 0;
		List<DuctSegment> listEmptyDuctSegment = new ArrayList<DuctSegment>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			try {
				session = AlmDbSession.getInstance().getSession();
				if (session != null && session.isOpen()) {
					duct = (Duct) session.createQuery("from Duct where duct_id = :param").setParameter("param", ductID)
							.getSingleResult();

					// System.out.println("duct is " + mapper.writeValueAsString(duct));

					listDuctAuxPoints = session
							.createQuery("from DuctAuxPoints where duct_id = :param order by seq_sorting",
									DuctAuxPoints.class)
							.setParameter("param", ductID).getResultList();

					// System.out.println("listDuctAuxPoints is " +
					// mapper.writeValueAsString(listDuctAuxPoints));

					DuctAuxPoints ductSourceDesttination = new DuctAuxPoints(duct.getDuctID(), duct.getDuctID(),
							duct.getSrcLong(), duct.getSrcLat(), 0, duct.getSourceWareId(), duct.getSourceName(),
							duct.getSourceId(), duct.getCreationDate(), duct.getLastModifieddate(), 0, 0, 0);

					renderPoints.add(ductSourceDesttination);
					renderPoints.addAll(listDuctAuxPoints);

					ductSourceDesttination = new DuctAuxPoints(duct.getDuctID(), duct.getDuctID(), duct.getDestLong(),
							duct.getDestLat(), 0, duct.getDestinationWareId(), duct.getDestinationName(),
							duct.getDestinationId(), duct.getCreationDate(), duct.getLastModifieddate(),
							listDuctAuxPoints.size() + 1, 0, 0);

					renderPoints.add(ductSourceDesttination);

					for (DuctAuxPoints p : renderPoints) {
						ductSeqMap.put(p.getAuxPointID(), p.getSeqSorting());
						ductPointById.put(p.getAuxPointID(), p);
					}

					listFiberDuct = (List<FiberDuct>) session.createQuery("from FiberDuct where duct_id = :param")
							.setParameter("param", ductID).getResultList();

					// System.out.println("listFiberDuct is " +
					// mapper.writeValueAsString(listFiberDuct));

					for (FiberDuct fd : listFiberDuct) {

						DuctAuxPoints from = ductPointById.get(fd.getFromAuxId());
						DuctAuxPoints to = ductPointById.get(fd.getToAuxId());

						if (from != null) {
							usedPointsMap.put(from.getAuxPointID(), from);
						}

						if (to != null) {
							usedPointsMap.put(to.getAuxPointID(), to);
						}
					}

					List<DuctAuxPoints> orderedPoints = new ArrayList<>(usedPointsMap.values());

					orderedPoints.sort(Comparator.comparingDouble(DuctAuxPoints::getSeqSorting));

					for (int i = 0; i < orderedPoints.size() - 1; i++) {

						DuctAuxPoints from = orderedPoints.get(i);
						DuctAuxPoints to = orderedPoints.get(i + 1);

						DuctSegmentDTO dto = new DuctSegmentDTO();

						dto.setFromSequence(from.getSeqSorting());
						dto.setToSequence(to.getSeqSorting());

						dto.setFromAuxId(from.getAuxPointID());
						dto.setFromAuxName(from.getAuxPointName());

						dto.setToAuxId(to.getAuxPointID());
						dto.setToAuxName(to.getAuxPointName());

						dto.setFromLongitude(from.getLong());
						dto.setFromLatitude(from.getLat());

						dto.setToLongitude(to.getLong());
						dto.setToLatitude(to.getLat());

						listDuctSegmentDTO.add(dto);
					}

					listDuctSegmentDTO.sort(Comparator.comparingDouble(DuctSegmentDTO::getFromSequence));

					str = "select distinct a.FIBER_PATH_ID, b.FIBER_CABLE_NAME, b.NUMBER_OF_TUBES, b.NUMBER_OF_STRANDS, "
							+ "b.FIBER_NETWORK_LEVEL, b.FIBER_TYPE, b.FIBER_DEPLOYMENT, b.CREATION_DATE, FIBER_OWNER, b.SOURCE_WARE_ID, "
							+ "b.SOURCE_ID, b.SOURCE_NAME, b.SOURCE_LNG, b.SOURCE_LAT, b.DESTINATION_WARE_ID, b.DESTINATION_ID, "
							+ "b.DESTINATION_NAME, b.DESTINATION_LNG, b.DESTINATION_LAT from fiber_duct a, fiber_cables b "
							+ "where a.duct_id = :param and a.FIBER_PATH_ID = b.FIBER_CABLE_ID "
							+ "order by CREATION_DATE asc, a.FIBER_PATH_ID asc";

					listFiberCable = session.createNativeQuery(str).setParameter("param", ductID).getResultList();

					// System.out.println("listFiberCable is " +
					// mapper.writeValueAsString(listFiberCable));

					AtomicInteger sortingCounter = new AtomicInteger(1);

					fiberCablesDetails = listFiberCable.stream()
							.collect(Collectors.toMap(row -> String.valueOf(row[0]),
									row -> new FiberCableDetailsDTO(String.valueOf(row[1]), String.valueOf(row[2]),
											String.valueOf(row[3]), String.valueOf(row[4]), String.valueOf(row[5]),
											String.valueOf(row[6]), (Timestamp) row[7], String.valueOf(row[8]),
											String.valueOf(row[9]), String.valueOf(row[10]), String.valueOf(row[11]),
											String.valueOf(row[12]), String.valueOf(row[13]), String.valueOf(row[14]),
											String.valueOf(row[15]), String.valueOf(row[16]), String.valueOf(row[17]),
											String.valueOf(row[18]), sortingCounter.getAndIncrement()),
									(existing, replacement) -> existing, LinkedHashMap::new));

					listAuxCables = session
							.createQuery("from FiberAuxPoints where fibercableID in :param order by seqSorting asc",
									FiberAuxPoints.class)
							.setParameter("param", fiberCablesDetails.keySet()).getResultList();

					cablesAuxMap = listAuxCables.stream().collect(Collectors.groupingBy(
							aux -> String.valueOf(aux.getFibercableID()), LinkedHashMap::new, Collectors.toList()));

					fiberOwnerColor = session
							.createNativeQuery("select distinct fiber_owner, fiber_color_owner from fiber_owner_color")
							.getResultList();

					for (Object[] ownerColor : fiberOwnerColor) {
						fiberOwnerColorMap.put(ownerColor[0].toString(), ownerColor[1].toString());
					}

					for (FiberDuct fd : listFiberDuct) {

						Double fromSeq = ductSeqMap.get(fd.getFromAuxId());
						Double toSeq = ductSeqMap.get(fd.getToAuxId());

						if (fromSeq == null || toSeq == null) {
							continue;
						}

						double cableStart = Math.min(fromSeq, toSeq);
						// System.out.println("cableStart is " + cableStart);

						double cableEnd = Math.max(fromSeq, toSeq);
						// System.out.println("cableEnd is " + cableEnd);

						String cableId = fd.getFiberPathId();

						FiberCableDetailsDTO details = fiberCablesDetails.get(cableId);

						for (DuctSegmentDTO segment : listDuctSegmentDTO) {

							double segStart = segment.getFromSequence();
							double segEnd = segment.getToSequence();

							// overlap condition (IMPORTANT FIX)
							boolean overlaps = cableStart <= segStart && cableEnd >= segEnd;

							// System.out.println("overlaps is " + overlaps);

							if (overlaps) {
								if (segment.getCables() == null) {
									segment.setCables(new LinkedHashMap<>());
								}
								segment.getCables().put(cableId,
										details != null ? details.getFiberCableName() : "UNKNOWN");
							}
						}
					}

					int destinationSeq = renderPoints.size() - 1;

					for (DuctSegmentDTO segment : listDuctSegmentDTO) {
						DuctSegment reportSegment = new DuctSegment();
						reportSegment.setSortSequence(segment.getFromSequence().intValue());
						reportSegment.setFromSequence(segment.getFromSequence().intValue() == 0 ? "Source"
								: String.valueOf(segment.getFromSequence().intValue()));

						reportSegment.setToSequence(segment.getToSequence().intValue() == destinationSeq ? "Destination"
								: String.valueOf(segment.getToSequence().intValue()));
						reportSegment.setCableQty(segment.getCables() != null ? segment.getCables().size() : 0);
						reportSegment.setFromAuxId(segment.getFromAuxId());
						reportSegment.setFromAuxName(segment.getFromAuxName());
						reportSegment.setToAuxId(segment.getToAuxId());
						reportSegment.setToAuxName(segment.getToAuxName());
						reportSegment.setFromLongitude(segment.getFromLongitude());
						reportSegment.setFromLatitude(segment.getFromLatitude());
						reportSegment.setToLongitude(segment.getToLongitude());
						reportSegment.setToLatitude(segment.getToLatitude());
						reportSegment.setCables(segment.getCables());

						reportSegments.add(reportSegment);

						if (segment.getFromSequence().intValue() != gapSeq) {
							DuctSegment emptyDuctSeqment = new DuctSegment();
							emptyDuctSeqment.setSortSequence(gapSeq);
							emptyDuctSeqment.setFromSequence(gapSeq == 0 ? "Source" : String.valueOf(gapSeq));
							emptyDuctSeqment
									.setToSequence(segment.getToSequence().intValue() == destinationSeq ? "Destination"
											: String.valueOf(segment.getToSequence().intValue()));
							emptyDuctSeqment.setFromAuxId(renderPoints.get(gapSeq).getAuxPointID());
							emptyDuctSeqment.setToAuxId(segment.getFromAuxId());
							emptyDuctSeqment.setFromAuxName(renderPoints.get(gapSeq).getAuxPointName());
							emptyDuctSeqment.setToAuxName(segment.getFromAuxName());
							emptyDuctSeqment.setFromLongitude(renderPoints.get(gapSeq).getLong());
							emptyDuctSeqment.setToLongitude(segment.getToLongitude());
							emptyDuctSeqment.setFromLatitude(renderPoints.get(gapSeq).getLat());
							emptyDuctSeqment.setToLatitude(segment.getToLatitude());
							emptyDuctSeqment.setCableQty(0);
							emptyDuctSeqment.setCables(new LinkedHashMap<>());
							listEmptyDuctSegment.add(emptyDuctSeqment);
						}
						gapSeq = segment.getToSequence().intValue();
					}
					reportSegments.addAll(listEmptyDuctSegment);
					reportSegments.sort(Comparator.comparingInt(DuctSegment::getSortSequence));
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error in DuctUtilizationReport loading page due to", e);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
			/*
			 * System.out.println("duct is " + mapper.writeValueAsString(duct));
			 * System.out.println("ductRenderPoints is " +
			 * mapper.writeValueAsString(renderPoints));
			 * System.out.println("ductPointById is " +
			 * mapper.writeValueAsString(ductPointById));
			 */
			System.out.println("fiberCablesDetails is " + mapper.writeValueAsString(fiberCablesDetails));
			System.out.println("cablesAuxMap" + mapper.writeValueAsString(cablesAuxMap));
			System.out.println("fiberOwnerColorMap" + mapper.writeValueAsString(fiberOwnerColorMap));
			System.out.println("reportSegments is " + mapper.writeValueAsString(reportSegments));

			rtn.put("duct", duct);
			rtn.put("listDuctAuxPoints", listDuctAuxPoints);
			rtn.put("ductRenderPoints", renderPoints);
			rtn.put("ductPointById", ductPointById);
			rtn.put("fiberCablesDetails", fiberCablesDetails);
			rtn.put("cablesAuxMap", cablesAuxMap);
			rtn.put("fiberOwnerColorMap", fiberOwnerColorMap);
			rtn.put("reportSegments", reportSegments);
		}
		return rtn;
	}

	/*
	 * @SuppressWarnings({ "unchecked", "deprecation" })
	 * 
	 * @RequestMapping(value = "/GenerateStrandUtilizationReport", method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object>
	 * GenerateStrandUtilizationReport(Locale locale, Model model,
	 * HttpServletRequest request, HttpServletResponse response) {
	 * 
	 * Map<String, Object> rtn = new LinkedHashMap<>(); String fiberCableID =
	 * request.getParameter("cableID");
	 * 
	 * List<StrandUtilizationReport> listStrandsUtilization = new
	 * ArrayList<StrandUtilizationReport>(); List<Object[]> fiberAuxDataRelatedPath
	 * = new ArrayList<Object[]>(); List<Object[]> relatedPathCablesID = new
	 * ArrayList<Object[]>(); List<Object[]> relatedPathCablesList = new
	 * ArrayList<Object[]>(); List<String> junctionIDs = new ArrayList<>();
	 * List<Object[]> manHandList = new ArrayList<Object[]>(); List<String>
	 * manHandoleList = new ArrayList<>();
	 * 
	 * if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	 * rtn.put("Login", "redirect:/"); return rtn; } else {
	 * 
	 * try { session = AlmDbSession.getInstance().getSession();
	 * 
	 * if (session != null && session.isOpen()) {
	 * 
	 * String str =
	 * "SELECT * FROM ( SELECT DISTINCT A.STRAND_NB_SIDE_A AS strandNo,A.TUBE_NB_SIDE_A AS tubeNo,A.LOCATION_TYPE_SIDE_A AS locationType,A.LOCATION_ID_SIDE_A AS locationID,A.LOCATION_NAME_SIDE_A AS locationName,"
	 * +
	 * " CASE WHEN A.LOCATION_TYPE_SIDE_A = 'Manhole' THEN M.longitude WHEN A.LOCATION_TYPE_SIDE_A = 'Handhole' THEN H.longitude  WHEN A.LOCATION_TYPE_SIDE_A = 'Site' THEN W.longitude  WHEN A.LOCATION_TYPE_SIDE_A = 'Customer' THEN C.longitude  WHEN A.LOCATION_TYPE_SIDE_A = 'DB' THEN D.DB_longitude  ELSE NULL END AS longitude,"
	 * +
	 * " CASE WHEN A.LOCATION_TYPE_SIDE_A = 'Manhole' THEN M.latitude  WHEN A.LOCATION_TYPE_SIDE_A = 'Handhole' THEN H.latitude WHEN A.LOCATION_TYPE_SIDE_A = 'Site' THEN W.latitude WHEN A.LOCATION_TYPE_SIDE_A = 'Customer' THEN C.latitude WHEN A.LOCATION_TYPE_SIDE_A = 'DB' THEN D.DB_latitude ELSE NULL  END AS latitude, "
	 * +
	 * " 'Junction' AS elementType,A.JCT_ID  as elementID, 'Side A' as frontBackPort, '' as relatedPath, CAST(A.SEQUENCE_NUMBER AS VARCHAR2(10)) as portIndex,'' as portRow,'' as portColumn,'' AS status,'' as equipmentType,'' AS equipmentID,'' as equipmentName,"
	 * +
	 * " A.LOCATION_ID_SIDE_A AS showLocation,A.JCT_ID || ':' || J.LONGITUDE || ':' || J.LATITUDE || ':' || J.JUNCTION_NAME as showElement FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN manhole M ON A.LOCATION_ID_SIDE_A = M.manhole_id AND A.LOCATION_TYPE_SIDE_A = 'Manhole' LEFT JOIN handhole H ON A.LOCATION_ID_SIDE_A = H.handhole_id AND A.LOCATION_TYPE_SIDE_A = 'Handhole' LEFT JOIN WAREHOUSE W ON A.LOCATION_ID_SIDE_A = W.SITE_ID AND A.LOCATION_TYPE_SIDE_A = 'Site' LEFT JOIN Customer C ON A.LOCATION_ID_SIDE_A = C.CUSTOMER_ID AND A.LOCATION_TYPE_SIDE_A = 'Customer' LEFT JOIN DISTRIBUTION_BOARD D ON A.LOCATION_ID_SIDE_A = D.DB_ID AND A.LOCATION_TYPE_SIDE_A = 'DB' "
	 * + " WHERE A.FIBER_ID_SIDE_A = '" + fiberCableID +
	 * "' AND (A.STRAND_NB_SIDE_A !=null OR A.STRAND_NB_SIDE_A !='null') AND (A.TUBE_NB_SIDE_A !=null OR A.TUBE_NB_SIDE_A !='null' ) "
	 * 
	 * + " UNION "
	 * 
	 * +
	 * " SELECT DISTINCT A.STRAND_NB_SIDE_B AS strandNo,A.TUBE_NB_SIDE_B AS tubeNo,A.LOCATION_TYPE_SIDE_A AS locationType,A.LOCATION_ID_SIDE_A AS locationID,A.LOCATION_NAME_SIDE_A AS locationName,"
	 * +
	 * " CASE WHEN A.LOCATION_TYPE_SIDE_A = 'Manhole' THEN M.longitude WHEN A.LOCATION_TYPE_SIDE_A = 'Handhole' THEN H.longitude  WHEN A.LOCATION_TYPE_SIDE_A = 'Site' THEN W.longitude  WHEN A.LOCATION_TYPE_SIDE_A = 'Customer' THEN C.longitude  WHEN A.LOCATION_TYPE_SIDE_A = 'DB' THEN D.DB_longitude  ELSE NULL END AS longitude,"
	 * +
	 * " CASE WHEN A.LOCATION_TYPE_SIDE_A = 'Manhole' THEN M.latitude  WHEN A.LOCATION_TYPE_SIDE_A = 'Handhole' THEN H.latitude WHEN A.LOCATION_TYPE_SIDE_A = 'Site' THEN W.latitude WHEN A.LOCATION_TYPE_SIDE_A = 'Customer' THEN C.latitude WHEN A.LOCATION_TYPE_SIDE_A = 'DB' THEN D.DB_latitude ELSE NULL  END AS latitude, "
	 * +
	 * " 'Junction' AS elementType,A.JCT_ID  as elementID, 'Side A' as frontBackPort,('Cable ID: ' || A.FIBER_ID_SIDE_A || ' ' || ' / Cable Name: ' || A.FIBER_NAME_SIDE_A || ' ' || ' / Tube#:' || A.TUBE_NB_SIDE_A || ' ' || ' / Strand#: ' || A.STRAND_NB_SIDE_A ) as relatedPath, CAST(A.SEQUENCE_NUMBER AS VARCHAR2(10)) as portIndex,'' as portRow,'' as portColumn,'' AS status,'' as equipmentType,'' AS equipmentID,'' as equipmentName,"
	 * +
	 * " A.LOCATION_ID_SIDE_A AS showLocation,A.JCT_ID || ':' || J.LONGITUDE || ':' || J.LATITUDE || ':' || J.JUNCTION_NAME as showElement FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN manhole M ON A.LOCATION_ID_SIDE_A = M.manhole_id AND A.LOCATION_TYPE_SIDE_A = 'Manhole' LEFT JOIN handhole H ON A.LOCATION_ID_SIDE_A = H.handhole_id AND A.LOCATION_TYPE_SIDE_A = 'Handhole' LEFT JOIN WAREHOUSE W ON A.LOCATION_ID_SIDE_A = W.SITE_ID AND A.LOCATION_TYPE_SIDE_A = 'Site' LEFT JOIN Customer C ON A.LOCATION_ID_SIDE_A = C.CUSTOMER_ID AND A.LOCATION_TYPE_SIDE_A = 'Customer' LEFT JOIN DISTRIBUTION_BOARD D ON A.LOCATION_ID_SIDE_A = D.DB_ID AND A.LOCATION_TYPE_SIDE_A = 'DB' "
	 * + " WHERE A.FIBER_ID_SIDE_B = '" + fiberCableID +
	 * "' AND (A.STRAND_NB_SIDE_A !=null OR A.STRAND_NB_SIDE_A !='null') AND (A.TUBE_NB_SIDE_A !=null OR A.TUBE_NB_SIDE_A !='null' ) "
	 * 
	 * + " UNION "
	 * 
	 * +
	 * " SELECT DISTINCT A.STRAND_NB_SIDE_B AS strandNo,A.TUBE_NB_SIDE_B AS tubeNo,A.LOCATION_TYPE_SIDE_B AS locationType,A.LOCATION_ID_SIDE_B AS locationID,A.LOCATION_NAME_SIDE_B AS locationName,"
	 * +
	 * " CASE WHEN A.LOCATION_TYPE_SIDE_B = 'Manhole' THEN M.longitude WHEN A.LOCATION_TYPE_SIDE_B = 'Handhole' THEN H.longitude  WHEN A.LOCATION_TYPE_SIDE_B = 'Site' THEN W.longitude  WHEN A.LOCATION_TYPE_SIDE_B = 'Customer' THEN C.longitude  WHEN A.LOCATION_TYPE_SIDE_B = 'DB' THEN D.DB_longitude  ELSE NULL END AS longitude,"
	 * +
	 * " CASE WHEN A.LOCATION_TYPE_SIDE_B = 'Manhole' THEN M.latitude  WHEN A.LOCATION_TYPE_SIDE_B = 'Handhole' THEN H.latitude WHEN A.LOCATION_TYPE_SIDE_B = 'Site' THEN W.latitude WHEN A.LOCATION_TYPE_SIDE_B = 'Customer' THEN C.latitude WHEN A.LOCATION_TYPE_SIDE_B= 'DB' THEN D.DB_latitude ELSE NULL  END AS latitude, "
	 * +
	 * " 'Junction' AS elementType,A.JCT_ID  as elementID,'Side B' as frontBackPort,'' as relatedPath,CAST(A.SEQUENCE_NUMBER AS VARCHAR2(10)) as portIndex,'' as portRow,'' as portColumn,'' AS status,'' as equipmentType,'' AS equipmentID,'' as equipmentName,"
	 * +
	 * " A.LOCATION_ID_SIDE_B AS showLocation, A.JCT_ID || ':' || J.LONGITUDE || ':' || J.LATITUDE || ':' || J.JUNCTION_NAME as showElement FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN manhole M ON A.LOCATION_ID_SIDE_B = M.manhole_id AND A.LOCATION_TYPE_SIDE_B = 'Manhole' LEFT JOIN handhole H ON A.LOCATION_ID_SIDE_B = H.handhole_id AND A.LOCATION_TYPE_SIDE_B = 'Handhole' LEFT JOIN WAREHOUSE W ON A.LOCATION_ID_SIDE_B = W.SITE_ID AND A.LOCATION_TYPE_SIDE_B = 'Site' LEFT JOIN Customer C ON A.LOCATION_ID_SIDE_B = C.CUSTOMER_ID AND A.LOCATION_TYPE_SIDE_B = 'Customer' LEFT JOIN DISTRIBUTION_BOARD D ON A.LOCATION_ID_SIDE_B = D.DB_ID AND A.LOCATION_TYPE_SIDE_B = 'DB' "
	 * + " WHERE A.FIBER_ID_SIDE_B = '" + fiberCableID +
	 * "' AND (A.STRAND_NB_SIDE_B !=null OR A.STRAND_NB_SIDE_B !='null') AND (A.TUBE_NB_SIDE_B !=null OR A.TUBE_NB_SIDE_B !='null' ) "
	 * 
	 * + " UNION "
	 * 
	 * +
	 * " SELECT DISTINCT A.STRAND_NB_SIDE_A AS strandNo,A.TUBE_NB_SIDE_A AS tubeNo,A.LOCATION_TYPE_SIDE_B AS locationType,A.LOCATION_ID_SIDE_B AS locationID,A.LOCATION_NAME_SIDE_B AS locationName, "
	 * +
	 * " CASE WHEN A.LOCATION_TYPE_SIDE_B = 'Manhole' THEN M.longitude WHEN A.LOCATION_TYPE_SIDE_B = 'Handhole' THEN H.longitude  WHEN A.LOCATION_TYPE_SIDE_B = 'Site' THEN W.longitude  WHEN A.LOCATION_TYPE_SIDE_B = 'Customer' THEN C.longitude  WHEN A.LOCATION_TYPE_SIDE_B = 'DB' THEN D.DB_longitude  ELSE NULL END AS longitude,"
	 * +
	 * " CASE WHEN A.LOCATION_TYPE_SIDE_B = 'Manhole' THEN M.latitude  WHEN A.LOCATION_TYPE_SIDE_B = 'Handhole' THEN H.latitude WHEN A.LOCATION_TYPE_SIDE_B = 'Site' THEN W.latitude WHEN A.LOCATION_TYPE_SIDE_B = 'Customer' THEN C.latitude WHEN A.LOCATION_TYPE_SIDE_B= 'DB' THEN D.DB_latitude ELSE NULL  END AS latitude, "
	 * +
	 * " 'Junction' AS elementType,A.JCT_ID  as elementID,'Side B' as frontBackPort,('Cable ID: ' || A.FIBER_ID_SIDE_B || ' ' || ' / Cable Name: ' || A.FIBER_NAME_SIDE_B || ' ' || ' / Tube#:' || A.TUBE_NB_SIDE_B || ' ' || ' / Strand#: ' || A.STRAND_NB_SIDE_B) as relatedPath,CAST(A.SEQUENCE_NUMBER AS VARCHAR2(10)) as portIndex,'' as portRow,'' as portColumn,'' AS status,'' as equipmentType,'' AS equipmentID,'' as equipmentName,"
	 * +
	 * " A.LOCATION_ID_SIDE_B AS showLocation, A.JCT_ID || ':' || J.LONGITUDE || ':' || J.LATITUDE || ':' || J.JUNCTION_NAME as showElement FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN manhole M ON A.LOCATION_ID_SIDE_B = M.manhole_id AND A.LOCATION_TYPE_SIDE_B = 'Manhole' LEFT JOIN handhole H ON A.LOCATION_ID_SIDE_B = H.handhole_id AND A.LOCATION_TYPE_SIDE_B = 'Handhole' LEFT JOIN WAREHOUSE W ON A.LOCATION_ID_SIDE_B = W.SITE_ID AND A.LOCATION_TYPE_SIDE_B = 'Site' LEFT JOIN Customer C ON A.LOCATION_ID_SIDE_B = C.CUSTOMER_ID AND A.LOCATION_TYPE_SIDE_B = 'Customer' LEFT JOIN DISTRIBUTION_BOARD D ON A.LOCATION_ID_SIDE_B = D.DB_ID AND A.LOCATION_TYPE_SIDE_B = 'DB' "
	 * + " WHERE A.FIBER_ID_SIDE_A = '" + fiberCableID +
	 * "' AND (A.STRAND_NB_SIDE_B !=null OR A.STRAND_NB_SIDE_B !='null') AND (A.TUBE_NB_SIDE_B !=null OR A.TUBE_NB_SIDE_B !='null' ) "
	 * 
	 * + " UNION "
	 * 
	 * +
	 * " SELECT DISTINCT A.FP_STRAND_Nb AS strandNo, A.FP_TUBE_Nb AS tubeNo,A.FP_LOCATION_TYPE AS locationType, A.FP_LOCATION_ID AS locationID, A.FP_LOCATION_NAME AS locationName,  "
	 * +
	 * " CASE WHEN A.FP_LOCATION_TYPE = 'Manhole' THEN M.longitude WHEN A.FP_LOCATION_TYPE = 'Handhole' THEN H.longitude WHEN A.FP_LOCATION_TYPE = 'Site' THEN W.longitude WHEN A.FP_LOCATION_TYPE = 'Customer' THEN C.longitude ELSE NULL END AS longitude, "
	 * +
	 * " CASE WHEN A.FP_LOCATION_TYPE = 'Manhole' THEN M.latitude WHEN A.FP_LOCATION_TYPE = 'Handhole' THEN H.latitude WHEN A.FP_LOCATION_TYPE = 'Site' THEN W.latitude WHEN A.FP_LOCATION_TYPE = 'Customer' THEN C.latitude  ELSE NULL END AS latitude, "
	 * +
	 * " 'DB' AS elementType,A.DB_ID  as elementID, 'Front' as frontBackPort,'' AS relatedPath,CAST(A.ROW_COL_INDEX AS VARCHAR2(10)) as portIndex, CAST(A.ROW_NUMBER AS VARCHAR2(10)) as portRow, CAST(A.COLUMN_NUMBER AS VARCHAR2(10)) as columnRow,A.FP_STATUS as status,A.FP_EQUIPMENT as equipmentType,A.FP_EQUIPMENT_ID AS equipmentID,A.FP_EQUIPMENT_NAME as equipmentName, "
	 * +
	 * " A.FP_LOCATION_ID as showLocation,A.DB_ID || ':' || D.DB_LONGITUDE || ':' || D.DB_LATITUDE || ':' || D.DB_NAME as showElement  FROM distribution_board_mapping A LEFT JOIN DISTRIBUTION_BOARD D ON A.DB_ID = D.DB_ID LEFT JOIN manhole M ON A.FP_LOCATION_ID = M.manhole_id AND A.FP_LOCATION_TYPE = 'Manhole' LEFT JOIN handhole H ON A.FP_LOCATION_ID = H.handhole_id AND A.FP_LOCATION_TYPE = 'Handhole' LEFT JOIN WAREHOUSE W ON A.FP_LOCATION_ID = W.SITE_ID AND A.FP_LOCATION_TYPE = 'Site' LEFT JOIN Customer C ON A.FP_LOCATION_ID = C.CUSTOMER_ID AND A.FP_LOCATION_TYPE = 'Customer'  "
	 * + " WHERE A.FP_FIBER_ID = '" + fiberCableID +
	 * "' AND (A.FP_STRAND_Nb !=null OR A.FP_STRAND_Nb !='null') AND (A.FP_TUBE_Nb !=null OR A.FP_TUBE_Nb !='null' ) "
	 * 
	 * + " UNION "
	 * 
	 * +
	 * " SELECT DISTINCT  A.BP_STRAND_Nb AS strandNo, A.BP_TUBE_Nb AS tubeNo,  A.BP_LOCATION_TYPE AS locationType,  A.BP_LOCATION_ID AS locationID,  A.BP_LOCATION_NAME AS locationName, "
	 * +
	 * "	CASE WHEN A.BP_LOCATION_TYPE = 'Manhole' THEN M.longitude WHEN A.BP_LOCATION_TYPE = 'Handhole' THEN H.longitude WHEN A.BP_LOCATION_TYPE = 'Site' THEN W.longitude WHEN A.BP_LOCATION_TYPE = 'Customer' THEN C.longitude ELSE NULL END AS longitude, "
	 * +
	 * " CASE WHEN A.BP_LOCATION_TYPE = 'Manhole' THEN M.latitude WHEN A.BP_LOCATION_TYPE = 'Handhole' THEN H.latitude WHEN A.BP_LOCATION_TYPE = 'Site' THEN W.latitude WHEN A.BP_LOCATION_TYPE = 'Customer' THEN C.latitude ELSE NULL  END AS latitude,"
	 * +
	 * " 'DB' AS elementType,A.DB_ID  as elementID,  'Back' as frontBackPort,'' AS relatedPath, CAST(A.ROW_COL_INDEX AS VARCHAR2(10)) as portIndex, CAST(A.ROW_NUMBER AS VARCHAR2(10)) as portRow, CAST(A.COLUMN_NUMBER AS VARCHAR2(10)) as columnRow,A.BP_STATUS as status,A.BP_EQUIPMENT as equipmentType,A.BP_EQUIPMENT_ID AS equipmentID,A.BP_EQUIPMENT_NAME as equipmentName,"
	 * +
	 * " A.BP_LOCATION_ID as showLocation,A.DB_ID || ':' || D.DB_LONGITUDE || ':' || D.DB_LATITUDE || ':' || D.DB_NAME as showElement FROM distribution_board_mapping A LEFT JOIN DISTRIBUTION_BOARD D ON A.DB_ID = D.DB_ID  LEFT JOIN manhole M ON A.BP_LOCATION_ID = M.manhole_id AND A.BP_LOCATION_TYPE = 'Manhole' LEFT JOIN handhole H ON A.BP_LOCATION_ID = H.handhole_id AND A.BP_LOCATION_TYPE = 'Handhole' LEFT JOIN WAREHOUSE W ON A.BP_LOCATION_ID = W.SITE_ID AND A.BP_LOCATION_TYPE = 'Site' LEFT JOIN Customer C ON A.BP_LOCATION_ID = C.CUSTOMER_ID AND A.BP_LOCATION_TYPE = 'Customer' "
	 * + " WHERE A.BP_FIBER_ID = '" + fiberCableID +
	 * "' AND (A.BP_STRAND_Nb !=null OR A.BP_STRAND_Nb !='null') AND (A.BP_TUBE_Nb !=null OR A.BP_TUBE_Nb !='null' ) ) ORDER BY TO_NUMBER(tubeNo) ASC, TO_NUMBER(strandNo) ASC  "
	 * ;
	 * 
	 * query = session.createNativeQuery(str); listStrandsUtilization =
	 * ((NativeQuery<StrandUtilizationReport>) query).addScalar("strandNo")
	 * .addScalar("tubeNo").addScalar("locationType").addScalar("locationID")
	 * .addScalar("locationName").addScalar("longitude").addScalar("latitude")
	 * .addScalar("elementType").addScalar("elementID").addScalar("frontBackPort")
	 * .addScalar("relatedPath").addScalar("portIndex").addScalar("portRow")
	 * .addScalar("portColumn").addScalar("status").addScalar("equipmentType")
	 * .addScalar("equipmentID").addScalar("equipmentName").addScalar(
	 * "showLocation") .addScalar("showElement")
	 * .setResultTransformer(Transformers.aliasToBean(StrandUtilizationReport.class)
	 * ).list(); rtn.put("listStrandsUtilization", listStrandsUtilization);
	 * 
	 * List<Object[]> junctionList = session.createNativeQuery(str).getResultList();
	 * 
	 * List<Object[]> fiberList = session.createNativeQuery(
	 * "SELECT A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR,A.NUMBER_OF_STRANDS,A.NUMBER_OF_TUBES FROM FIBER_CABLES A WHERE A.FIBER_CABLE_ID ='"
	 * + fiberCableID + "' ") .getResultList(); rtn.put("fiberList", fiberList);
	 * 
	 * List<Object[]> fiberAuxData = session.createNativeQuery(
	 * "SELECT B.LONGITUDE,B.LATITUDE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID ='"
	 * + fiberCableID + "' ORDER BY B.SEQ_SORTING ASC") .getResultList();
	 * rtn.put("fiberAuxData", fiberAuxData);
	 * 
	 * relatedPathCablesID = session
	 * .createNativeQuery("select distinct a.FIBER_ID_SIDE_A ,a.FIBER_NAME_SIDE_A "
	 * +
	 * "from JUNCTION_MAPPING a left join FIBER_CABLES b on a.FIBER_ID_SIDE_A=b.FIBER_CABLE_ID "
	 * + "where FIBER_ID_SIDE_B='" + fiberCableID +
	 * "' and (b.FIBER_NETWORK_LEVEL='access' OR b.FIBER_NETWORK_LEVEL='metro' or b.FIBER_NETWORK_LEVEL='backbone')"
	 * + "union " + "select distinct a.FIBER_ID_SIDE_B ,a.FIBER_NAME_SIDE_B " +
	 * "from JUNCTION_MAPPING a left join FIBER_CABLES b on a.FIBER_ID_SIDE_B=b.FIBER_CABLE_ID "
	 * + "where a.FIBER_ID_SIDE_A='" + fiberCableID +
	 * "' and (b.FIBER_NETWORK_LEVEL='access' OR b.FIBER_NETWORK_LEVEL='metro' or b.FIBER_NETWORK_LEVEL='backbone')"
	 * ) .getResultList();
	 * 
	 * for (int x = 0; x < relatedPathCablesID.size(); x++) { Object[] row =
	 * relatedPathCablesID.get(x); String fiberId = (String) row[0];
	 * 
	 * query = session.createNativeQuery(
	 * "SELECT A.FIBER_CABLE_ID,B.LONGITUDE,B.LATITUDE,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.SEQ_SORTING,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID ='"
	 * + fiberId + "' ORDER BY B.SEQ_SORTING ASC");
	 * fiberAuxDataRelatedPath.addAll(query.list());
	 * 
	 * query = session.createNativeQuery(
	 * "SELECT A.FIBER_CABLE_ID,A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,A.FIBER_CABLE_NAME,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR,A.NUMBER_OF_STRANDS,A.NUMBER_OF_TUBES FROM FIBER_CABLES A WHERE A.FIBER_CABLE_ID ='"
	 * + fiberId + "' "); relatedPathCablesList.addAll(query.list());
	 * 
	 * } rtn.put("fiberAuxDataRelatedPath", fiberAuxDataRelatedPath);
	 * rtn.put("relatedPathCables", relatedPathCablesList);
	 * 
	 * for (int x = 0; x < junctionList.size(); x++) { Object[] row =
	 * junctionList.get(x); String elementType = (String) row[7]; String elementID =
	 * (String) row[8];
	 * 
	 * if (elementType.equals("Junction") == true) { if
	 * (junctionIDs.contains(elementID) == false) { junctionIDs.add(elementID); } }
	 * } query = session.createNativeQuery(
	 * "SELECT PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME,JUNCTION_ID,JUNCTION_NAME FROM JUNCTION B WHERE JUNCTION_ID IN (:param1) "
	 * ); query.setParameter("param1", junctionIDs);
	 * manHandList.addAll(query.list()); rtn.put("manHandList", manHandList);
	 * 
	 * for (int x = 0; x < manHandList.size(); x++) { Object[] row =
	 * manHandList.get(x); String ID = (String) row[0]; if
	 * (manHandoleList.contains(ID) == false) { manHandoleList.add(ID); } }
	 * rtn.put("manHandoleList", manHandoleList);
	 * 
	 * List<Object[]> frontTotalUsedStrands = session.createNativeQuery(
	 * "Select count(*) from distribution_board_mapping where fp_status ='Active' and (fp_equipment_id !=null or fp_equipment_id !='null' or fp_equipment_name!=null or fp_equipment_name!='null') AND fp_fiber_id ='"
	 * + fiberCableID + "' ") .getResultList(); rtn.put("frontTotalUsedStrands",
	 * frontTotalUsedStrands);
	 * 
	 * List<Object[]> backTotalUsedStrands = session.createNativeQuery(
	 * "Select count(*) from distribution_board_mapping where bp_status ='Active' and (bp_equipment_id !=null or bp_equipment_id !='null' or bp_equipment_name!=null or bp_equipment_name!='null') AND bp_fiber_id ='"
	 * + fiberCableID + "' ") .getResultList(); rtn.put("backTotalUsedStrands",
	 * backTotalUsedStrands);
	 * 
	 * List<Object[]> jctTotalUsedStrands = session
	 * .createNativeQuery("Select count(*) from JUNCTION_MAPPING where FIBER_ID_SIDE_A ='"
	 * + fiberCableID + "' OR FIBER_ID_SIDE_B ='" + fiberCableID + "' ")
	 * .getResultList(); rtn.put("jctTotalUsedStrands", jctTotalUsedStrands);
	 * 
	 * String str1 = "SELECT * FROM ( " +
	 * " SELECT DISTINCT A.STRAND_NB_SIDE_A AS strandNo,A.TUBE_NB_SIDE_A AS tubeNo, A.JCT_ID  as jctID, J.JUNCTION_NAME as jctName,M.manhole_id as manID,M.manhole_name as manName "
	 * +
	 * " FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN manhole M ON A.LOCATION_ID_SIDE_A = M.manhole_id AND A.LOCATION_TYPE_SIDE_A = 'Manhole' "
	 * + " WHERE A.FIBER_ID_SIDE_A = '" + fiberCableID +
	 * "' AND (A.STRAND_NB_SIDE_A !=null OR A.STRAND_NB_SIDE_A !='null') AND (A.TUBE_NB_SIDE_A !=null OR A.TUBE_NB_SIDE_A !='null' ) "
	 * 
	 * + " UNION "
	 * 
	 * +
	 * " SELECT DISTINCT A.STRAND_NB_SIDE_A AS strandNo,A.TUBE_NB_SIDE_A AS tubeNo, A.JCT_ID  as jctID, J.JUNCTION_NAME as jctName,M.handhole_id as handID,M.handhole_name as handName "
	 * +
	 * " FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN handhole M ON A.LOCATION_ID_SIDE_A = M.handhole_id AND A.LOCATION_TYPE_SIDE_A = 'Handhole' "
	 * + " WHERE A.FIBER_ID_SIDE_A = '" + fiberCableID +
	 * "' AND (A.STRAND_NB_SIDE_A !=null OR A.STRAND_NB_SIDE_A !='null') AND (A.TUBE_NB_SIDE_A !=null OR A.TUBE_NB_SIDE_A !='null' ) "
	 * 
	 * + " UNION "
	 * 
	 * +
	 * " SELECT DISTINCT A.STRAND_NB_SIDE_B AS strandNo,A.TUBE_NB_SIDE_B AS tubeNo, A.JCT_ID  as jctID, J.JUNCTION_NAME as jctName,M.manhole_id as manID,M.manhole_name as manName "
	 * +
	 * " FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN manhole M ON A.LOCATION_ID_SIDE_A = M.manhole_id AND A.LOCATION_TYPE_SIDE_A = 'Manhole' "
	 * + " WHERE A.FIBER_ID_SIDE_B = '" + fiberCableID +
	 * "' AND (A.STRAND_NB_SIDE_A !=null OR A.STRAND_NB_SIDE_A !='null') AND (A.TUBE_NB_SIDE_A !=null OR A.TUBE_NB_SIDE_A !='null' ) "
	 * 
	 * + " UNION "
	 * 
	 * +
	 * " SELECT DISTINCT A.STRAND_NB_SIDE_B AS strandNo,A.TUBE_NB_SIDE_B AS tubeNo, A.JCT_ID  as jctID, J.JUNCTION_NAME as jctName,M.handhole_id as handID,M.handhole_name as handName "
	 * +
	 * " FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN handhole M ON A.LOCATION_ID_SIDE_A = M.handhole_id AND A.LOCATION_TYPE_SIDE_A = 'Handhole' "
	 * + " WHERE A.FIBER_ID_SIDE_B = '" + fiberCableID +
	 * "' AND (A.STRAND_NB_SIDE_A !=null OR A.STRAND_NB_SIDE_A !='null') AND (A.TUBE_NB_SIDE_A !=null OR A.TUBE_NB_SIDE_A !='null' ) "
	 * 
	 * + " UNION "
	 * 
	 * +
	 * " SELECT DISTINCT A.STRAND_NB_SIDE_B AS strandNo,A.TUBE_NB_SIDE_B AS tubeNo,A.JCT_ID  as jctID,J.JUNCTION_NAME as jctName,M.manhole_id as manID,M.manhole_name as manName "
	 * +
	 * " FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN manhole M ON A.LOCATION_ID_SIDE_B = M.manhole_id AND A.LOCATION_TYPE_SIDE_B = 'Manhole' "
	 * + " WHERE A.FIBER_ID_SIDE_B = '" + fiberCableID +
	 * "' AND (A.STRAND_NB_SIDE_B !=null OR A.STRAND_NB_SIDE_B !='null') AND (A.TUBE_NB_SIDE_B !=null OR A.TUBE_NB_SIDE_B !='null' ) "
	 * 
	 * + " UNION "
	 * 
	 * +
	 * " SELECT DISTINCT A.STRAND_NB_SIDE_B AS strandNo,A.TUBE_NB_SIDE_B AS tubeNo,A.JCT_ID  as jctID,J.JUNCTION_NAME as jctName,M.handhole_id as handID,M.handhole_name as handName "
	 * +
	 * " FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN handhole M ON A.LOCATION_ID_SIDE_B = M.handhole_id AND A.LOCATION_TYPE_SIDE_B = 'Handhole' "
	 * + " WHERE A.FIBER_ID_SIDE_B = '" + fiberCableID +
	 * "' AND (A.STRAND_NB_SIDE_B !=null OR A.STRAND_NB_SIDE_B !='null') AND (A.TUBE_NB_SIDE_B !=null OR A.TUBE_NB_SIDE_B !='null' ) "
	 * 
	 * + " UNION "
	 * 
	 * +
	 * " SELECT DISTINCT A.STRAND_NB_SIDE_A AS strandNo,A.TUBE_NB_SIDE_A AS tubeNo,A.JCT_ID  as jctID,J.JUNCTION_NAME as jctName,M.manhole_id as manID,M.manhole_name as manName "
	 * +
	 * " FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN manhole M ON A.LOCATION_ID_SIDE_B = M.manhole_id AND A.LOCATION_TYPE_SIDE_B = 'Manhole' "
	 * + " WHERE A.FIBER_ID_SIDE_A = '" + fiberCableID +
	 * "' AND (A.STRAND_NB_SIDE_B !=null OR A.STRAND_NB_SIDE_B !='null') AND (A.TUBE_NB_SIDE_B !=null OR A.TUBE_NB_SIDE_B !='null' ) "
	 * 
	 * + " UNION "
	 * 
	 * +
	 * " SELECT DISTINCT A.STRAND_NB_SIDE_A AS strandNo,A.TUBE_NB_SIDE_A AS tubeNo,A.JCT_ID  as jctID,J.JUNCTION_NAME as jctName,M.handhole_id as handID,M.handhole_name as handName "
	 * +
	 * " FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN handhole M ON A.LOCATION_ID_SIDE_B = M.handhole_id AND A.LOCATION_TYPE_SIDE_B = 'Handhole' "
	 * + " WHERE A.FIBER_ID_SIDE_A = '" + fiberCableID +
	 * "' AND (A.STRAND_NB_SIDE_B !=null OR A.STRAND_NB_SIDE_B !='null') AND (A.TUBE_NB_SIDE_B !=null OR A.TUBE_NB_SIDE_B !='null' ) "
	 * 
	 * + ")";
	 * 
	 * }
	 * 
	 * } catch (Exception e) { sw = new StringWriter(); e.printStackTrace(new
	 * PrintWriter(sw)); exceptionAsString = sw.toString();
	 * logger.finest("Error in GenerateStrandUtilizationReport due to \n " +
	 * exceptionAsString);
	 * logger.info("Error in GenerateStrandUtilizationReport due to \n " +
	 * exceptionAsString); } finally { if (session != null && session.isOpen()) {
	 * session.close(); } } } return rtn; }
	 * 
	 * @RequestMapping(value = "/getJctElementsDetails", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> getJctElementsDetails(Locale locale,
	 * Model model, HttpServletRequest request, HttpServletResponse response) throws
	 * JsonProcessingException {
	 * 
	 * Map<String, Object> rtn = new LinkedHashMap<>();
	 * 
	 * session = AlmDbSession.getInstance().getSession(); if
	 * (LoginServices.checkSession(request, response).equals("redirect:/")) {
	 * rtn.put("Login", LoginServices.checkSession(request, response)); return rtn;
	 * } if (session != null && session.isOpen()) { try { String[]
	 * jctElementsIDArray = request.getParameterValues("jctElementsIDArray[]");
	 * 
	 * if (jctElementsIDArray == null) { rtn.put("jctList", ""); } else {
	 * 
	 * query = session.createNativeQuery(
	 * "SELECT JUNCTION_ID,JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME,LONGITUDE,LATITUDE"
	 * + " FROM JUNCTION WHERE JUNCTION_ID IN (:param1) ");
	 * 
	 * query.setParameter("param1", Arrays.asList(jctElementsIDArray));
	 * rtn.put("jctList", query.getResultList()); }
	 * 
	 * } catch (Exception e) { sw = new StringWriter(); e.printStackTrace(new
	 * PrintWriter(sw)); exceptionAsString = sw.toString();
	 * logger.finest("Error in getJctElementsDetails due to \n " +
	 * exceptionAsString); logger.info("Error in getJctElementsDetails due to \n " +
	 * exceptionAsString); }
	 * 
	 * finally { if (session != null && session.isOpen()) { session.close();
	 * 
	 * } } } return rtn; }
	 * 
	 * @RequestMapping(value = "/getSingleJctElementsDetails", method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> getSingleJctElementsDetails(Locale
	 * locale, Model model, HttpServletRequest request, HttpServletResponse
	 * response) throws JsonProcessingException {
	 * 
	 * Map<String, Object> rtn = new LinkedHashMap<>();
	 * 
	 * session = AlmDbSession.getInstance().getSession(); if
	 * (LoginServices.checkSession(request, response).equals("redirect:/")) {
	 * rtn.put("Login", LoginServices.checkSession(request, response)); return rtn;
	 * } if (session != null && session.isOpen()) { try { String ID =
	 * request.getParameter("ID");
	 * 
	 * query = session.createNativeQuery(
	 * "SELECT JUNCTION_ID,JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME,LONGITUDE,LATITUDE"
	 * + " FROM JUNCTION WHERE JUNCTION_ID = '" + ID + "' ");
	 * 
	 * rtn.put("singleJctList", query.getResultList());
	 * 
	 * } catch (Exception e) { sw = new StringWriter(); e.printStackTrace(new
	 * PrintWriter(sw)); exceptionAsString = sw.toString();
	 * logger.finest("Error in getSingleJctElementsDetails due to \n " +
	 * exceptionAsString);
	 * logger.info("Error in getSingleJctElementsDetails due to \n " +
	 * exceptionAsString); }
	 * 
	 * finally { if (session != null && session.isOpen()) { session.close();
	 * 
	 * } } } return rtn; }
	 */
}