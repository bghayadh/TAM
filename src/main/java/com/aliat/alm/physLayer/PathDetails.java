package com.aliat.alm.physLayer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.models.FiberDuct;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PathDetails {
	private final Logger logger = Logger.getLogger(PhysicalLayerController.class.getName());
	private Session session = null;
	private Transaction tx = null;
	private ObjectMapper mapper = new ObjectMapper();
	private Query query = null;
	private StringWriter sw;
	private String exceptionAsString;

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
			
			System.out.println("selectedFiberContext is " +selectedFiberContext);
			List<Object[]> fiberDetails, fiberTubes, fiberStrands, accessJunctions;
			List<FiberDuct> fiberDucts = new ArrayList<FiberDuct>();
			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					fiberDetails = session.createNativeQuery(
							"Select DISTINCT SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,ITEM_CODE,NUMBER_OF_STRANDS,NUMBER_OF_TUBES,LENGTH,CONDUIT_ID,CONDUIT_NAME,SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,CABLE_MODE,FIBER_CABLE_NAME,SOURCE_CITY, DESTINATION_CITY, FIBER_TYPE, FIBER_DEPLOYMENT, FIBER_NETWORK_LEVEL, FIBER_OWNER,CREATED_BY,LAST_MODIFIED_BY,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),TOTAL_DRIVING_DISTANCE, TOTAL_GEO_DISTANCE, DRAWING_TYPE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE,RELATED_STRAND_NUMBER,RELATED_STRAND_COLOR,RELATED_STRAND_ID,RELATED_STRAND_NAME,RELATED_TUBE_NUMBER,RELATED_TUBE_COLOR,RELATED_TUBE_ID,RELATED_TUBE_NAME,RELATED_CABLE_ID,RELATED_CABLE_NAME,OTHERSIDE_LASTMILE_ID,OTHERSIDE_LASTMILE_NAME,OTHERSIDE_LOCATION_ID,OTHERSIDE_LOCATION_NAME,OTHERSIDE_LOCATION_CITY,OTHERSIDE_LOCATION_TYPE,FIBER_CABLE_SIZE,FIBER_ENGINEER_NAME,FIBER_INSTALLER FROM FIBER_CABLES WHERE FIBER_CABLE_ID='"
									+ selectedFiberContext + "' ")
							.getResultList();

					// USED IN SHOW POINTS FOR AUXILIARIES //
/*					
					List<Object[]> fiberAuxiliaryData = session.createNativeQuery(
							"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.SEQ_SORTING, B.DRIVING_DISTANCE, B.GEO_DISTANCE, B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID ='"
									+ selectedFiberContext + "' ORDER BY B.SEQ_SORTING ASC")
							.getResultList();
*/							
					
					fiberDucts = session.createQuery("from FiberDuct where fiberPathId = :param").setParameter("param", selectedFiberContext).getResultList();

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
//					rtn.put("fiberAuxData", fiberAuxiliaryData);
					rtn.put("fiberDucts", fiberDucts);
					rtn.put("fiberTubes", fiberTubes);
					rtn.put("fiberStrands", fiberStrands);
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

	@RequestMapping(value = "/findFiberAuxDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findFiberAuxDetails(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String selectedFiberContext = request.getParameter("ID");
		rtn = getFiberAux(selectedFiberContext, itemParameters.getDictParameter());
		return rtn;
	}

	@RequestMapping(value = "/findTrenchDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findTrenchDetails(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String selectedTrench = request.getParameter("ID");
		rtn = getTrenchDetails(selectedTrench, itemParameters.getDictParameter());
		return rtn;
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

	@RequestMapping(value = "/getPathForTrenchGen", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPathForTrenchGen(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response)
			throws JsonProcessingException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		String selectedFiberContext = request.getParameter("ID");
		rtn = getFiberAux(selectedFiberContext, itemParameters.getDictParameter());
		System.out.println("rtn is " + mapper.writeValueAsString(rtn));
		return rtn;
	}

	@RequestMapping(value = "/getPathForDuctGen", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPathForDuctGen(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response)
			throws JsonProcessingException {

		logger.finest("FINEST at getPathForDuctGen");

		String selectedDuctContext = request.getParameter("selected");
		System.out.println("selectedDuctContext is " + selectedDuctContext);
		String pathID = request.getParameter("ID");
		System.out.println("pathID is " + pathID);
		boolean fromTrench = Boolean.parseBoolean(request.getParameter("fromTrench"));
		System.out.println("fromTrench is " + fromTrench);

		if (!fromTrench) {
			return getFiberAux(pathID, itemParameters.getDictParameter());
		}

		Map<String, Object> rtn = new LinkedHashMap<>();
		String trenchId = null;
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			try {
				trenchId = session.createNativeQuery("SELECT TRENCH_ID FROM DUCTS WHERE DUCT_ID = :param")
						.setParameter("param", selectedDuctContext).getSingleResult().toString();
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error in getPathForDuctGen when getting trenchId based on ductId due to", e);
				trenchId = null;
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		if (trenchId != null) {
			System.out.println("Generating Duct from Trench");
			rtn = getTrenchDetails(trenchId, itemParameters.getDictParameter());
		}
		System.out.println("rtn is " + mapper.writeValueAsString(rtn));
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fiberAuxAutoComplete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> fiberAuxAutoComplete(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		String ID = request.getParameter("ID");
		String aux = request.getParameter("aux");

		Session session = null;

		try {
			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {

				String sql = "select source_id,source_name,'Source',source_lng,source_lat,1 as grp,0 as seq "
						+ "from fiber_cables " + "where fiber_cable_id=:id "
						+ "and (lower(source_id) like lower(:aux) or lower(source_name) like lower(:aux) or 'source' like lower(:aux)) "
						+ "union all "
						+ "select destination_id,destination_name,'Destination',destination_lng,destination_lat,2 as grp,0 as seq "
						+ "from fiber_cables " + "where fiber_cable_id=:id "
						+ "and (lower(destination_id) like lower(:aux) or lower(destination_name) like lower(:aux) or 'destination' like lower(:aux)) "
						+ "union all "
						+ "select auxiliary_point_id,auxiliary_point_name,to_char(seq_sorting),longitude,latitude,3 as grp,seq_sorting as seq "
						+ "from fiber_auxiliary_points " + "where fiber_cable_id=:id "
						+ "and (lower(auxiliary_point_id) like lower(:aux) or lower(auxiliary_point_name) like lower(:aux) "
						+ "or (:seqLike is not null and to_char(seq_sorting) like :seqLike)) " + "order by grp,seq";

				String seqLike = null;

				if (aux != null && aux.matches(".*\\d+.*")) {
					seqLike = "%" + aux + "%";
				}

				List<Object[]> auxDetails = session.createNativeQuery(sql).setParameter("id", ID)
						.setParameter("aux", "%" + aux + "%").setParameter("seqLike", seqLike).getResultList();
				rtn.put("fiberAuxDetails", auxDetails);
			}

		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error in fiberAuxAutoComplete", e);

			rtn.put("fiberAuxDetails", null);

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fiberDuctAutoComplete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> fiberDuctAutoComplete(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		String duct = request.getParameter("duct");
		Session session = null;
		try {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				String sql = "SELECT duct_id, duct_name " + "FROM ducts " + "WHERE UPPER(duct_id) LIKE UPPER(:duct) "
						+ "OR UPPER(duct_name) LIKE UPPER(:duct) " + "ORDER BY duct_name " + "FETCH FIRST 40 ROWS ONLY";
				
				List<Object[]> ductDetails = session.createNativeQuery(sql).setParameter("duct", "%" + duct + "%")
						.getResultList();

				rtn.put("ducts", ductDetails);
			}

		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error in fiberDuctAutoComplete", e);
			rtn.put("ducts", new ArrayList<>());

		} finally {

			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getFiberAux(String fiberPathID, List<Map<String, String>> aux) {

		System.out.println("Welcome to getFiberAux, the fiberPathID is " + fiberPathID);

		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			try {

				List<Object[]> fiberAuxiliaryData = session
						.createNativeQuery("SELECT B.LONGITUDE, B.LATITUDE, B.DISTANCE_FROM_SOURCE, B.WARE_ID, "
								+ "B.AUXILIARY_POINT_ID, B.AUXILIARY_POINT_NAME, B.FIBER_CABLE_ID, B.SEQ_SORTING, "
								+ "B.DRIVING_DISTANCE, B.GEO_DISTANCE, B.AUXILIARY_ID "
								+ "FROM FIBER_CABLES A, FIBER_AUXILIARY_POINTS B "
								+ "WHERE A.FIBER_CABLE_ID = B.FIBER_CABLE_ID " + "AND B.FIBER_CABLE_ID = :fiberId "
								+ "ORDER BY B.SEQ_SORTING ASC")
						.setParameter("fiberId", fiberPathID).getResultList();

				rtn.put("auxData", fiberAuxiliaryData);

				List<String> finalList = new ArrayList<String>();
				if (aux != null) {
					for (int i = 0; i < aux.size(); i++) {
						String aux_Long = (aux.get(i).get("auxLng"));
						String aux_Lat = (aux.get(i).get("auxLat"));

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
				session.clear();
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error in getFiberAux", e);
				rtn.put("auxData", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getTrenchDetails(String trenchPathID, List<Map<String, String>> aux) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		List<Object[]> listTrench;
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			try {

				listTrench = session.createNativeQuery(
						"SELECT TRENCH_ID,TRENCH_NAME,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,SOURCE_LONGITUDE,SOURCE_LATITUDE,DESTINATION_LONGITUDE,DESTINATION_LATITUDE,SOURCE_CITY,DESTINATION_CITY, MAX_CAPACITY,NUM_DUCTS,LENGTH,TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'),CREATED_BY,LAST_MODIFIED_BY,TOTAL_DRIVING_DISTANCE, TOTAL_GEO_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DISTANCE,LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE,DRAWING_TYPE,OWNER,TRENCH_INSTALLER,TRENCH_ENGINEER_NAME FROM TRENCH WHERE TRENCH_ID='"
								+ trenchPathID + "' ")
						.getResultList();

				List<Object[]> trenchAuxiliary = session.createNativeQuery(
						"SELECT LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,TRENCH_ID,SEQ_SORTING,DRIVING_DISTANCE,GEO_DISTANCE,AUXILIARY_ID FROM TRENCH_AUXILIARY_POINTS WHERE TRENCH_ID='"
								+ trenchPathID + "' ORDER BY SEQ_SORTING ASC ")
						.getResultList();

				List<String> finalList = new ArrayList<String>();
				if (aux != null) {
					for (int i = 0; i < aux.size(); i++) {
						String aux_Long = (aux.get(i).get("auxLng"));
						String aux_Lat = (aux.get(i).get("auxLat"));

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
				logger.log(Level.SEVERE, "Error in findTrenchDetails due to", e);
				rtn.put("listTrench", null);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}
}