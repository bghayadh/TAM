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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

@Controller
public class FiberDashBoardController {

	private static final Logger logger = Logger.getLogger(FiberDashBoardController.class.getName());

	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/FiberOpticDashboard", method = RequestMethod.GET)
	public String FiberOpticDashboard(Locale locale, Model model, HttpServletRequest request,
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

	    Session session = null;
	    Transaction tx = null;
	    List<Object[]> fiberList = new ArrayList<>();
	    List<Object[]> fiberAuxiliaryData = new ArrayList<>();

	    try {
	        session = AlmDbSession.getInstance().getSession();
	        if (session != null && session.isOpen()) {
	            tx = session.beginTransaction();
	        	notifications.headerNotifications(session, model);
	            fiberList = session.createNativeQuery(
	                    "SELECT SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,A.FIBER_CABLE_ID,"
	                            + "A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,"
	                            + "A.DESTINATION_ID,A.DESTINATION_NAME,"
	                            + "(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID),"
	                            + "(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID),"
	                            + "FIBER_CABLE_NAME,PROJECT_ID,SOURCE_CITY,DESTINATION_CITY,NUMBER_OF_TUBES,"
	                            + "NUMBER_OF_STRANDS,LENGTH,DRAWING_TYPE,FIBER_NETWORK_LEVEL,FIBER_OWNER,"
	                            + "(SELECT B.FIBER_COLOR_OWNER FROM FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER)"
	                            + " AS FIBER_CABLE_COLOR "
	                            + "FROM FIBER_CABLES A WHERE A.PROJECT_ID='CurrentPhysicalLayer' "
	                            + "ORDER BY A.LAST_MODIFIED_DATE DESC")
	                    .getResultList();

	            fiberAuxiliaryData = session.createNativeQuery(
	                    "SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,"
	                            + "B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID "
	                            + "FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B "
	                            + "WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID "
	                            + "AND A.PROJECT_ID='CurrentPhysicalLayer' "
	                            + "ORDER BY B.SEQ_SORTING ASC")
	                    .getResultList();

	            tx.commit();
	        }

	        Gson gson = new Gson();
	        String fiberListJson = gson.toJson(fiberList);
	        String fiberAuxiliaryDataJson = gson.toJson(fiberAuxiliaryData);

	        model.addAttribute("fiberListJson", fiberListJson);
	        model.addAttribute("fiberAuxiliaryDataJson", fiberAuxiliaryDataJson);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }

	        StringWriter sw = new StringWriter();
	        e.printStackTrace(new PrintWriter(sw));
	        String exceptionAsString = sw.toString();
	        logger.info("Error in FiberOpticDashboard: \n" + exceptionAsString);

	    } finally {
	        if (session != null && session.isOpen()) {
	            session.close();
	        }
	    }

	    return "Network/FiberOpticDashboard"; // JSP view name
	}
}