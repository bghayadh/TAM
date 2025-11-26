package com.aliat.alm.physLayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Properties;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
//import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

@Controller
public class updateDistancesController {

	private static final Logger logger = Logger.getLogger(updateDistancesController.class.getName());

	private Transaction tx = null;
	private ObjectMapper mapper = new ObjectMapper();
	private Query query = null;
	private StringWriter sw;
	private String exceptionAsString;
	private final double R = 6371; // Radius of the earth in km



	@Autowired
	Notify notifications;
	@Autowired
	Permissions permissions;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateGeoDistances", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateGeoDistances(HttpServletRequest request, HttpServletResponse response) {
	    Map<String, Object> rtn = new LinkedHashMap<>();

	    if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	        rtn.put("Login", "redirect:/");
	        return rtn;
	    }

	    Session session = AlmDbSession.getInstance().getSession();
	    Transaction tx = null;

	    try {
	        String geoDistancesJson = request.getParameter("geoDistances");
	        if (geoDistancesJson == null || geoDistancesJson.isEmpty()) {
	            rtn.put("status", "error");
	            rtn.put("message", "geoDistances parameter missing");
	            return rtn;
	        }

	        ObjectMapper mapper = new ObjectMapper();
	        List<Map<String, Object>> geoDistances = mapper.readValue(
	            geoDistancesJson, new TypeReference<List<Map<String, Object>>>() {}
	        );

	        if (session != null && session.isOpen()) {
	            System.out.println("Session open and transaction started...");
	            System.out.println("geoDistances size: " + (geoDistances != null ? geoDistances.size() : "NULL"));

	            tx = session.beginTransaction();

	            // Loop through all items in geoDistances list
	            int totalUpdatedRows = 0;
	            for (Map<String, Object> entry : geoDistances) {
	                String fiberId = (String) entry.get("fiberId");
	                Double totalDistance = entry.get("distance") != null
	                        ? ((Number) entry.get("distance")).doubleValue()
	                        : 0.0;

	                System.out.println("About to update fiber: " + fiberId + " → " + totalDistance);

	                Query query = session.createNativeQuery(
	                    "UPDATE FIBER_CABLES SET TOTAL_GEO_DISTANCE = :param1 WHERE FIBER_CABLE_ID = :param2"
	                );
	                query.setParameter("param1", totalDistance);
	                query.setParameter("param2", fiberId);

	                System.out.println("Executing update query for fiberId: " + fiberId);
	                int rowsUpdated = query.executeUpdate();
	                totalUpdatedRows += rowsUpdated;
	                System.out.println("Query done. Rows updated for fiberId " + fiberId + " = " + rowsUpdated);
	            }

	            tx.commit();
	            System.out.println("Transaction committed successfully. Total rows updated: " + totalUpdatedRows);

	            rtn.put("status", "success");
	            rtn.put("updatedRows", totalUpdatedRows);
	        } else {
	            rtn.put("status", "error");
	            rtn.put("message", "Session not open");
	        }

	    } catch (Exception e) {
	        if (tx != null && tx.isActive()) tx.rollback();
	        System.out.println("Error during update:");
	        e.printStackTrace(System.out);
	        rtn.put("status", "error");
	        rtn.put("message", e.getMessage());
	    }

	    return rtn;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getFiberScript", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFiberScript(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
			Map<String, Object> rtn = new LinkedHashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		Session session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				
				List<Object[]> fiberList = new ArrayList<Object[]>();
				fiberList = session.createNativeQuery(
						"SELECT DISTINCT FIBER_CABLE_ID,SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT FROM FIBER_CABLES ")
						.getResultList();
		
				rtn.put("fiberList", fiberList);
				
				
				List<Object[]> auxDat = session.createNativeQuery(
					    "SELECT FIBER_CABLE_ID, LONGITUDE, LATITUDE, SEQ_SORTING " +
					    "FROM FIBER_AUXILIARY_POINTS " +
					    "ORDER BY FIBER_CABLE_ID, SEQ_SORTING ASC"
					).getResultList();

					rtn.put("auxDat", auxDat);
					
						
	System.out.println(auxDat);
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.info("Error in get due to \n " + exceptionAsString);
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
	@RequestMapping(value = "/updateLineOfSites", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> updateLineOfSites(Locale locale, Model model, HttpServletRequest request,
	        HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
	    System.out.println("Passes here getDB");
	    Map<String, Object> rtn = new LinkedHashMap<String, Object>();
	    
	    // Check if user is logged in
	    if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	        rtn.put("Login", LoginServices.checkSession(request, response));
	        return rtn;
	    }

	    Session session = AlmDbSession.getInstance().getSession();

	    if (session != null && session.isOpen()) {
	        Transaction tx = null;
	        try {
	            tx = session.beginTransaction();

	            // Get fiber data
	            List<Object[]> fiberList = session.createNativeQuery(
	                    "SELECT DISTINCT FIBER_CABLE_ID, SOURCE_LNG, SOURCE_LAT, DESTINATION_LNG, DESTINATION_LAT FROM FIBER_CABLES ")
	                    .getResultList();

	            // Get auxiliary point data
	            List<Object[]> auxDat = session.createNativeQuery(
	                    "SELECT FIBER_CABLE_ID, LONGITUDE, LATITUDE, SEQ_SORTING " +
	                    "FROM FIBER_AUXILIARY_POINTS " +
	                    "ORDER BY FIBER_CABLE_ID, SEQ_SORTING ASC")
	                    .getResultList();

	            // Loop through fiberList
	            for (int i = 0; i < fiberList.size(); i++) {
	                // Extract the fiber details from the fiberList
	                String cableId = (String) fiberList.get(i)[0];  // FIBER_CABLE_ID
	                double sourceLng = parseDouble(fiberList.get(i)[1]); // SOURCE_LNG
	                double sourceLat = parseDouble(fiberList.get(i)[2]); // SOURCE_LAT
	                double destinationLng = parseDouble(fiberList.get(i)[3]); // DESTINATION_LNG
	                double destinationLat = parseDouble(fiberList.get(i)[4]); // DESTINATION_LAT

	                // Create a list to hold the related auxiliary points for the current fiber
	                List<Object[]> relatedAux = new ArrayList<>();

	                // Loop through auxDat and collect related auxiliary points for the current fiber
	                for (Object[] auxPoint : auxDat) {
	                    String auxFiberCableId = (String) auxPoint[0]; // FIBER_CABLE_ID
	                    if (cableId.equals(auxFiberCableId)) {
	                        relatedAux.add(auxPoint); // Add the auxiliary point to the list
	                    }
	                }

	                // Ensure there are related auxiliary points to process
	                if (!relatedAux.isEmpty()) {
	                    // Compute the distance from source to the first auxiliary point
	                    double sourceFirstAux = haversine_distance(sourceLat, sourceLng,
	                            parseDouble(relatedAux.get(0)[2]), parseDouble(relatedAux.get(0)[1]));

	                    double betweenAux = 0;
	                    double totalDistance = 0;

	                    // Loop through the auxiliary points and calculate distances between them
	                    for (int c = 0; c < relatedAux.size() - 1; c++) {
	                        double strictBetween = haversine_distance(
	                                parseDouble(relatedAux.get(c)[2]), parseDouble(relatedAux.get(c)[1]),
	                                parseDouble(relatedAux.get(c + 1)[2]), parseDouble(relatedAux.get(c + 1)[1]));
	                        betweenAux += strictBetween;
	                    }

	                    // Calculate distance to the last auxiliary point and destination
	                    double destinationLastAux = haversine_distance(
	                            parseDouble(relatedAux.get(relatedAux.size() - 1)[2]),
	                            parseDouble(relatedAux.get(relatedAux.size() - 1)[1]),
	                            destinationLat, destinationLng);

	                    // Sum up all distances
	                    totalDistance = sourceFirstAux + betweenAux + destinationLastAux;
	                    
	                    Query query = session.createNativeQuery(
	    	                    "UPDATE FIBER_CABLES SET LENGTH = :param1 WHERE FIBER_CABLE_ID = :param2"
	    	                );
	    	                query.setParameter("param1", totalDistance);
	    	                query.setParameter("param2", cableId);

	    	                 query.executeUpdate();
	    	            

	                    System.out.println("Total Distance for Fiber Cable " + cableId + ": " + totalDistance);
	                }

	                // Optional: You can store or process the data further
	                rtn.put("done", "done");
	            }

	            tx.commit();
	        } catch (Exception e) {
	            if (tx != null) {
	                tx.rollback();
	            }
	            StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            String exceptionAsString = sw.toString();
	            logger.info("Error in get due to \n " + exceptionAsString);
	            rtn.put("searchResult", "Failed");
	        } finally {
	            if (session != null && session.isOpen()) {
	                session.close();
	            }
	        }
	    }

	    return rtn;
	}

	// Function to safely parse Double values, handling both String and Number types
	private double parseDouble(Object obj) {
	    if (obj instanceof String) {
	        try {
	            return Double.parseDouble((String) obj);
	        } catch (NumberFormatException e) {
	            // Handle the case where the String can't be parsed to Double
	            System.out.println("Error parsing string to double: " + obj);
	            return 0.0; // Return a default value (adjust based on your needs)
	        }
	    } else if (obj instanceof Number) {
	        return ((Number) obj).doubleValue();
	    }
	    return 0.0; // Default value if the object is neither String nor Number
	}

	// Haversine distance formula to calculate the distance between two coordinates
	public static double haversine_distance(double lat1, double lng1, double lat2, double lng2) {
	    final double R = 3958.8; // Radius of the Earth in miles

	    // Convert degrees to radians
	    lat1 = Math.toRadians(lat1);
	    lat2 = Math.toRadians(lat2);
	    lng1 = Math.toRadians(lng1);
	    lng2 = Math.toRadians(lng2);

	    // Radian difference (latitudes and longitudes)
	    double difflat = lat2 - lat1;
	    double difflon = lng2 - lng1;

	    // Haversine formula
	    double a = Math.sin(difflat / 2) * Math.sin(difflat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(difflon / 2) * Math.sin(difflon / 2);
	    double c = 2 * Math.asin(Math.sqrt(a));

	    // Distance in miles, rounded to 8 decimal places
	    return Math.round(R * c * 100000000.0) / 100000000.0;  // Round to 8 decimal places
	}


}