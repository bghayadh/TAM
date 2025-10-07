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
public class updateCitiesController {

	private static final Logger logger = Logger.getLogger(updateCitiesController.class.getName());

	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	  private static final double R = 6371; // Radius of the earth in km



	@Autowired
	Notify notifications;
	@Autowired
	Permissions permissions;

	@SuppressWarnings("unchecked")
			
		@RequestMapping(value = "/getFiberInfoCity", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> getFiberInfoCity(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

	    Map<String, Object> rtn = new LinkedHashMap<>();

	    if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	        rtn.put("Login", "redirect:/");
	        return rtn;
	    }

	    Session session = AlmDbSession.getInstance().getSession();
	    Transaction tx = null;

	    try {
	    	List<Object[]> fiberList = new ArrayList<Object[]>();
			fiberList = session.createNativeQuery(
					"SELECT DISTINCT FIBER_CABLE_ID,SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT FROM FIBER_CABLES ")
					.getResultList();
	
			rtn.put("fiberList", fiberList);
			

	    } catch (Exception e) {
	        if (tx != null && tx.isActive()) 
	        tx.rollback();
	        System.out.println("Error during update:");
	        e.printStackTrace(System.out);
	        rtn.put("status", "error");
	        rtn.put("message", e.getMessage());
	    }

	    return rtn;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveFiberCities", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveFiberCities(HttpServletRequest request, HttpServletResponse response) {
	    Map<String, Object> rtn = new LinkedHashMap<>();

	    if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	        rtn.put("Login", "redirect:/");
	        return rtn;
	    }

	    Session session = AlmDbSession.getInstance().getSession();
	    Transaction tx = null;

	    try {
	        String fiberCityList = request.getParameter("fiberCityList");
	        if (fiberCityList == null || fiberCityList.isEmpty()) {
	            rtn.put("status", "error");
	            rtn.put("message", "geoDistances parameter missing");
	            return rtn;
	        }

	        ObjectMapper mapper = new ObjectMapper();
	        List<Map<String, Object>> fiberCity = mapper.readValue(
	        		fiberCityList, new TypeReference<List<Map<String, Object>>>() {}
	        );

	        if (session != null && session.isOpen()) {
	            System.out.println("Session open and transaction started...");
	            System.out.println("fiberCityList size: " + (fiberCity != null ? fiberCity.size() : "NULL"));

	            tx = session.beginTransaction();

	            // Loop through all items in geoDistances list
	            int totalUpdatedRows = 0;
	            for (Map<String, Object> entry : fiberCity) {
	                String fiberId = (String) entry.get("fiberId");
	                String srcCity = (String) entry.get("srcCity");
	                String destCity = (String) entry.get("destCity");
	                
	              
	                Query query = session.createNativeQuery(
	                	    "UPDATE FIBER_CABLES SET SOURCE_CITY = :param1, DESTINATION_CITY = :param2 WHERE FIBER_CABLE_ID = :param3"
	                	);
	                	query.setParameter("param1", srcCity);
	                	query.setParameter("param2", destCity);
	                	query.setParameter("param3", fiberId);

	                	int result = query.executeUpdate();
	                	System.out.println("Number of rows updated: " + result);
  
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
	}