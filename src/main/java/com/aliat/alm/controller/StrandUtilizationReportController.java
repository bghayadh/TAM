package com.aliat.alm.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import com.aliat.alm.services.LoginServices;
import java.util.logging.Logger;
import com.aliat.alm.models.StrandUtilizationReport;


@Controller
public class StrandUtilizationReportController {
	private static final Logger logger = Logger.getLogger(StrandUtilizationReportController.class.getName());

	@Autowired
	Notify notifications;
	private Session session = null;
	@SuppressWarnings("rawtypes")
	Query query;
	Object[] result;
	private static StringWriter sw;
	private static String exceptionAsString;



	@RequestMapping(value = "/StrandUtilizationReport", method = RequestMethod.GET)
	public String StrandUtilizationReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {

			session = AlmDbSession.getInstance().getSession();

			if (session != null && session.isOpen()) {

				notifications.headerNotifications(session, model);
				try {
					
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in StrandUtilizationReport due to \n " + exceptionAsString);
					logger.info("Error in StrandUtilizationReport due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						
						session.close();
						
					}
				}
			}
		}
		return "Reports/StrandUtilizationReport";
	}

	@RequestMapping(value = "/getFiberCableDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFiberCableDetails(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			try {
				String requestValue = request.getParameter("requestValue");

				String str = "select distinct fibercableID,fibercableName from FiberCable where LOWER(fibercableID) like LOWER(:param1) or LOWER(fibercableName) like LOWER(:param1) ";

				query = session.createQuery(str);
				query.setParameter("param1", "%" + requestValue + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("FiberCableList", query.list());
			} catch (Exception e) {
				logger.info("Error in getFiberCableDetails while using autocomplete with error message: " + e);
				e.printStackTrace();

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
					
				}
			}
		}

		return rtn;
	}

	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/GenerateStrandUtilizationReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateStrandUtilizationReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String fiberCableID = request.getParameter("cableID");

		List<StrandUtilizationReport> listStrandsUtilization = new ArrayList<StrandUtilizationReport>();

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {

			try {
				session = AlmDbSession.getInstance().getSession();

				if (session != null && session.isOpen()) {

					List<Object[]> fiberAuxData = session.createNativeQuery(
							"SELECT B.LONGITUDE,B.LATITUDE,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.SEQ_SORTING,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID AND B.FIBER_CABLE_ID ='"
									+ fiberCableID + "' ORDER BY B.SEQ_SORTING ASC").getResultList();
					rtn.put("fiberAuxData", fiberAuxData);
					
					List<Object[]> fiberList = session.createNativeQuery(
							"SELECT SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,SOURCE_WARE_ID,SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME FROM FIBER_CABLES WHERE FIBER_CABLE_ID =' "
							+ fiberCableID + "' ").getResultList();
					rtn.put("fiberList", fiberList);
					
					
				
					
					String str ="SELECT DISTINCT A.STRAND_NB_SIDE_A AS strandNo,A.TUBE_NB_SIDE_A AS tubeNo,'Junction' AS elementType,A.JCT_ID  as elementID, '' as frontBackPort,'' as portIndex,'' as portRow,'' as portColumn,A.LOCATION_TYPE_SIDE_A AS locationType,A.LOCATION_ID_SIDE_A AS locationID,A.LOCATION_NAME_SIDE_A AS locationName, "
							+ " CASE WHEN A.LOCATION_TYPE_SIDE_A = 'Manhole' THEN M.longitude WHEN A.LOCATION_TYPE_SIDE_A = 'Handhole' THEN H.longitude  WHEN A.LOCATION_TYPE_SIDE_A = 'Site' THEN W.longitude  WHEN A.LOCATION_TYPE_SIDE_A = 'Customer' THEN C.longitude  WHEN A.LOCATION_TYPE_SIDE_A = 'DB' THEN D.DB_longitude  ELSE NULL END AS longitude,"
							+ " CASE WHEN A.LOCATION_TYPE_SIDE_A = 'Manhole' THEN M.latitude  WHEN A.LOCATION_TYPE_SIDE_A = 'Handhole' THEN H.latitude WHEN A.LOCATION_TYPE_SIDE_A = 'Site' THEN W.latitude WHEN A.LOCATION_TYPE_SIDE_A = 'Customer' THEN C.latitude WHEN A.LOCATION_TYPE_SIDE_A = 'DB' THEN D.DB_latitude ELSE NULL  END AS latitude, "
							+ " A.LOCATION_ID_SIDE_A AS showLocation,A.JCT_ID || ':' || J.LONGITUDE || ':' || J.LATITUDE || ':' || J.JUNCTION_NAME as showElement FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN manhole M ON A.LOCATION_ID_SIDE_A = M.manhole_id AND A.LOCATION_TYPE_SIDE_A = 'Manhole' LEFT JOIN handhole H ON A.LOCATION_ID_SIDE_A = H.handhole_id AND A.LOCATION_TYPE_SIDE_A = 'Handhole' LEFT JOIN WAREHOUSE W ON A.LOCATION_ID_SIDE_A = W.SITE_ID AND A.LOCATION_TYPE_SIDE_A = 'Site' LEFT JOIN Customer C ON A.LOCATION_ID_SIDE_A = C.CUSTOMER_ID AND A.LOCATION_TYPE_SIDE_A = 'Customer' LEFT JOIN DISTRIBUTION_BOARD D ON A.LOCATION_ID_SIDE_A = D.DB_ID AND A.LOCATION_TYPE_SIDE_A = 'DB' "
							+ " WHERE A.FIBER_ID_SIDE_A = '"+fiberCableID+"' "
							
							+ " UNION "
							
							+ " SELECT DISTINCT A.STRAND_NB_SIDE_B AS strandNo,A.TUBE_NB_SIDE_B AS tubeNo,'Junction' AS elementType,A.JCT_ID  as elementID,'' as frontBackPort,'' as portIndex,'' as portRow,'' as portColumn,A.LOCATION_TYPE_SIDE_B AS locationType,A.LOCATION_ID_SIDE_B AS locationID,A.LOCATION_NAME_SIDE_B AS locationName, "
							+ " CASE WHEN A.LOCATION_TYPE_SIDE_B = 'Manhole' THEN M.longitude WHEN A.LOCATION_TYPE_SIDE_B = 'Handhole' THEN H.longitude  WHEN A.LOCATION_TYPE_SIDE_B = 'Site' THEN W.longitude  WHEN A.LOCATION_TYPE_SIDE_B = 'Customer' THEN C.longitude  WHEN A.LOCATION_TYPE_SIDE_B = 'DB' THEN D.DB_longitude  ELSE NULL END AS longitude,"
							+ " CASE WHEN A.LOCATION_TYPE_SIDE_B = 'Manhole' THEN M.latitude  WHEN A.LOCATION_TYPE_SIDE_B = 'Handhole' THEN H.latitude WHEN A.LOCATION_TYPE_SIDE_B = 'Site' THEN W.latitude WHEN A.LOCATION_TYPE_SIDE_B = 'Customer' THEN C.latitude WHEN A.LOCATION_TYPE_SIDE_B= 'DB' THEN D.DB_latitude ELSE NULL  END AS latitude, "
							+ " A.LOCATION_ID_SIDE_B AS showLocation, A.JCT_ID || ':' || J.LONGITUDE || ':' || J.LATITUDE || ':' || J.JUNCTION_NAME as showElement FROM JUNCTION_MAPPING A LEFT JOIN JUNCTION J ON A.JCT_ID = J.JUNCTION_ID LEFT JOIN manhole M ON A.LOCATION_ID_SIDE_B = M.manhole_id AND A.LOCATION_TYPE_SIDE_B = 'Manhole' LEFT JOIN handhole H ON A.LOCATION_ID_SIDE_B = H.handhole_id AND A.LOCATION_TYPE_SIDE_B = 'Handhole' LEFT JOIN WAREHOUSE W ON A.LOCATION_ID_SIDE_B = W.SITE_ID AND A.LOCATION_TYPE_SIDE_B = 'Site' LEFT JOIN Customer C ON A.LOCATION_ID_SIDE_B = C.CUSTOMER_ID AND A.LOCATION_TYPE_SIDE_B = 'Customer' LEFT JOIN DISTRIBUTION_BOARD D ON A.LOCATION_ID_SIDE_B = D.DB_ID AND A.LOCATION_TYPE_SIDE_B = 'DB' "
							+ " WHERE A.FIBER_ID_SIDE_A = '"+fiberCableID+"' "
							
							+ " UNION "
							
							+" SELECT DISTINCT A.FP_STRAND_Nb AS strandNo, A.FP_TUBE_Nb AS tubeNo, 'DB' AS elementType,A.DB_ID  as elementID, 'Front' as frontBackPort,CAST(A.ROW_COL_INDEX AS VARCHAR2(10)) as portIndex, CAST(A.ROW_NUMBER AS VARCHAR2(10)) as portRow, CAST(A.COLUMN_NUMBER AS VARCHAR2(10)) as columnRow, A.FP_LOCATION_TYPE AS locationType, A.FP_LOCATION_ID AS locationID, A.FP_LOCATION_NAME AS locationName,  "
							+" CASE WHEN A.FP_LOCATION_TYPE = 'Manhole' THEN M.longitude WHEN A.FP_LOCATION_TYPE = 'Handhole' THEN H.longitude WHEN A.FP_LOCATION_TYPE = 'Site' THEN W.longitude WHEN A.FP_LOCATION_TYPE = 'Customer' THEN C.longitude ELSE NULL END AS longitude, "
							+" CASE WHEN A.FP_LOCATION_TYPE = 'Manhole' THEN M.latitude WHEN A.FP_LOCATION_TYPE = 'Handhole' THEN H.latitude WHEN A.FP_LOCATION_TYPE = 'Site' THEN W.latitude WHEN A.FP_LOCATION_TYPE = 'Customer' THEN C.latitude  ELSE NULL END AS latitude, "
							+ " A.FP_LOCATION_ID as showLocation,A.DB_ID || ':' || D.DB_LONGITUDE || ':' || D.DB_LATITUDE || ':' || D.DB_NAME as showElement  FROM distribution_board_mapping A LEFT JOIN DISTRIBUTION_BOARD D ON A.DB_ID = D.DB_ID LEFT JOIN manhole M ON A.FP_LOCATION_ID = M.manhole_id AND A.FP_LOCATION_TYPE = 'Manhole' LEFT JOIN handhole H ON A.FP_LOCATION_ID = H.handhole_id AND A.FP_LOCATION_TYPE = 'Handhole' LEFT JOIN WAREHOUSE W ON A.FP_LOCATION_ID = W.SITE_ID AND A.FP_LOCATION_TYPE = 'Site' LEFT JOIN Customer C ON A.FP_LOCATION_ID = C.CUSTOMER_ID AND A.FP_LOCATION_TYPE = 'Customer'  " 
							+ " WHERE A.FP_FIBER_ID = '"+fiberCableID+"' "
							
							+ " UNION "
							
							+" SELECT DISTINCT  A.BP_STRAND_Nb AS strandNo, A.BP_TUBE_Nb AS tubeNo, 'DB' AS elementType,A.DB_ID  as elementID,  'Back' as frontBackPort, CAST(A.ROW_COL_INDEX AS VARCHAR2(10)) as portIndex, CAST(A.ROW_NUMBER AS VARCHAR2(10)) as portRow, CAST(A.COLUMN_NUMBER AS VARCHAR2(10)) as columnRow, A.BP_LOCATION_TYPE AS locationType,  A.BP_LOCATION_ID AS locationID,  A.BP_LOCATION_NAME AS locationName,"
							+ "	CASE WHEN A.BP_LOCATION_TYPE = 'Manhole' THEN M.longitude WHEN A.BP_LOCATION_TYPE = 'Handhole' THEN H.longitude WHEN A.BP_LOCATION_TYPE = 'Site' THEN W.longitude WHEN A.BP_LOCATION_TYPE = 'Customer' THEN C.longitude ELSE NULL END AS longitude, " 
							+" CASE WHEN A.BP_LOCATION_TYPE = 'Manhole' THEN M.latitude WHEN A.BP_LOCATION_TYPE = 'Handhole' THEN H.latitude WHEN A.BP_LOCATION_TYPE = 'Site' THEN W.latitude WHEN A.BP_LOCATION_TYPE = 'Customer' THEN C.latitude ELSE NULL  END AS latitude," 
							+" A.BP_LOCATION_ID as showLocation,A.DB_ID || ':' || D.DB_LONGITUDE || ':' || D.DB_LATITUDE || ':' || D.DB_NAME as showElement FROM distribution_board_mapping A LEFT JOIN DISTRIBUTION_BOARD D ON A.DB_ID = D.DB_ID  LEFT JOIN manhole M ON A.BP_LOCATION_ID = M.manhole_id AND A.BP_LOCATION_TYPE = 'Manhole' LEFT JOIN handhole H ON A.BP_LOCATION_ID = H.handhole_id AND A.BP_LOCATION_TYPE = 'Handhole' LEFT JOIN WAREHOUSE W ON A.BP_LOCATION_ID = W.SITE_ID AND A.BP_LOCATION_TYPE = 'Site' LEFT JOIN Customer C ON A.BP_LOCATION_ID = C.CUSTOMER_ID AND A.BP_LOCATION_TYPE = 'Customer' "  
							+" WHERE A.BP_FIBER_ID = '"+fiberCableID+"' ";
					
					
			
					query = session.createNativeQuery(str);
					listStrandsUtilization = ((NativeQuery<StrandUtilizationReport>) query).addScalar("strandNo").addScalar("tubeNo").addScalar("elementType").addScalar("elementID").addScalar("frontBackPort").addScalar("portIndex").addScalar("portRow").addScalar("portColumn").addScalar("locationType").addScalar("locationID").addScalar("locationName")
							.addScalar("longitude").addScalar("latitude").addScalar("showLocation").addScalar("showElement").setResultTransformer(Transformers.aliasToBean(StrandUtilizationReport.class))
							.list();
					
					rtn.put("listStrandsUtilization", listStrandsUtilization);



				}

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GenerateStrandUtilizationReport due to \n " + exceptionAsString);
				logger.info("Error in GenerateStrandUtilizationReport due to \n " + exceptionAsString);
			} 
			finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}
	
	
	

}