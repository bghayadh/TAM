package com.aliat.alm.controller;


import com.aliat.alm.models.ArNode;
import com.aliat.alm.models.ArPartNumber;
import com.aliat.alm.models.ArSerialNumber;
import com.aliat.alm.models.ArSite;
import com.aliat.alm.models.AssetRegistry;
import com.aliat.alm.models.DNIFormView;
import com.aliat.alm.models.DiscoveryNewItemNode;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.models.PurchaseOrder;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.models.FarNode;
import com.aliat.alm.models.FarPartNumber;
import com.aliat.alm.models.FarSerialNumber;
import com.aliat.alm.models.FarSite;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Arrays;
import org.hibernate.type.FloatType;
import org.hibernate.type.StringType;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.discoveryNew.DiscoveryController;
import com.aliat.alm.discoveryNew.ManagerApprovals;
import com.aliat.alm.models.DiscoveryNew;
import com.aliat.alm.models.DiscoveryNewItem;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PatchingWorkOrderTreeController {
	@Autowired
	Notify notifications;
	@Autowired
	ManagerApprovals Manager;
	private static final Logger logger = LoggerFactory.getLogger(DiscoveryController.class);
	private static Query query = null;
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	int i;
	private static String str = null;
	private static 	Object[] result = null;
 
 
	Calendar calendar = new GregorianCalendar();
	int year = calendar.get(Calendar.YEAR);
		

	
	@Autowired
	Permissions permissions;
	
	
	@RequestMapping(value = "/PatchingWorkOrderTree", method = RequestMethod.GET)
	public String PatchingWorkOrderTree(
	        Locale locale,
	        Model model,
	        HttpServletRequest request,
	        HttpServletResponse response) {

	    if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	        return LoginServices.checkSession(request, response);
	    }

	    Session session = AlmDbSession.getInstance().getSession();

	    if (session != null && session.isOpen()) {
	        notifications.headerNotifications(session, model);

	        try {
	            ObjectMapper mapper = new ObjectMapper();

	            /* ===============================
	             * 1️⃣ GET PATCHING WORK ORDERS
	             * =============================== */
	            List<Object[]> patchingOrders = session.createNativeQuery(
	                "SELECT " +
	                "t.PATCHING_ID, " +
	                "t.ASSIGNED_TO, " +
	                "TO_CHAR(t.PLANED_EXECUTION_DATE, 'MM/dd/YYYY HH:MI AM'), " +
	                "TO_CHAR(t.ACTUAL_EXECUTION_DATE, 'MM/dd/YYYY HH:MI AM'), " +
	                "t.PATCHING_STATUS, " +
	                "NVL(t.PATCHING_NOTE, ' '), " +
	                "TO_CHAR(t.CREATED_DATE, 'MM/dd/YYYY HH:MI AM'), " +
	                "TO_CHAR(t.LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM') " +
	                "FROM PATCHING_WORK_ORDER t " +
	                "WHERE t.PATCHING_STATUS IN ('Approved', 'Draft') " +
	                "ORDER BY t.LAST_MODIFIED_DATE DESC"
	            ).getResultList();

	            /* ===============================
	             * 2️⃣ GET ALL WORK ORDER TASKS
	             * =============================== */
	            List<Object[]> tasks = session.createNativeQuery(
	                "SELECT " +
	                "WO_TASK_ID, PATCHING_ID, TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM'),  TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM'), TO_CHAR(COMPLETION_DATE, 'MM/dd/YYYY HH:MI AM')  , " +
	                "TASK_TYPE, TASK_STATUS, DB_ID, DB_PORT_ID, ROW_COL_INDEX, " +
	                "ROW_NUMBER, COLUMN_NUMBER, NEAR_MODULE, NEAR_PORT_NUM, NEAR_PATCH_TYPE, " +
	                "FP_LOCATION_TYPE, FP_LOCATION_ID, FP_LOCATION_NAME, FP_LOCATION, " +
	                "FP_EQUIPMENT_TYPE, FP_EQUIPMENT, FP_EQUIPMENT_ID, FP_EQUIPMENT_NAME, FP_ADDRESS, " +
	                "FP_TUBE_NB, FP_STRAND_COLOR, FP_TUBE_COLOR, FP_STRAND_NAME, " +
	                "FP_TUBE_ID, FP_TUBE_NAME, FP_FIBER_ID, FP_FIBER_NAME, " +
	                "FP_KIT_SERIAL_NUM, FP_MODULE, FP_PORT_NUM, " +
	                "FP_JUNCTION_ID, FP_JUNCTION_NAME " +
	                "FROM WORK_ORDER_TASK"
	            ).getResultList();

	            /* ===============================
	             * 3️⃣ GROUP TASKS BY PATCHING_ID
	             * =============================== */
	            Map<String, List<Map<String, Object>>> tasksByPatchingId = new LinkedHashMap<>();

	            for (Object[] r : tasks) {
	                String patchingId = (String) r[1];

	                Map<String, Object> task = new LinkedHashMap<>();
	                task.put("woTaskId", r[0]);
	                task.put("patchingId", r[1]);
	                task.put("creationDate", r[2]);
	                task.put("lastModifiedDate", r[3]);
	                task.put("completionDate", r[4]);
	                task.put("taskType", r[5]);
	                task.put("taskStatus", r[6]);
	                task.put("dbId", r[7]);
	                task.put("dbPortId", r[8]);
	                task.put("rowColIndex", r[9]);
	                task.put("rowNumber", r[10]);
	                task.put("columnNumber", r[11]);
	                task.put("nearModule", r[12]);
	                task.put("nearPortNum", r[13]);
	                task.put("nearPatchType", r[14]);
	                task.put("fpLocationType", r[15]);
	                task.put("fpLocationId", r[16]);
	                task.put("fpLocationName", r[17]);
	                task.put("fpLocation", r[18]);
	                task.put("fpEquipmentType", r[19]);
	                task.put("fpEquipment", r[20]);
	                task.put("fpEquipmentId", r[21]);
	                task.put("fpEquipmentName", r[22]);
	                task.put("fpAddress", r[23]);
	                task.put("fpTubeNb", r[24]);
	                task.put("fpStrandColor", r[25]);
	                task.put("fpTubeColor", r[26]);
	                task.put("fpStrandName", r[27]);
	                task.put("fpTubeId", r[28]);
	                task.put("fpTubeName", r[29]);
	                task.put("fpFiberId", r[30]);
	                task.put("fpFiberName", r[31]);
	                task.put("fpKitSerialNum", r[32]);
	                task.put("fpModule", r[33]);
	                task.put("fpPortNum", r[34]);
	                task.put("fpJunctionId", r[35]);
	                task.put("fpJunctionName", r[36]);

	                tasksByPatchingId
	                    .computeIfAbsent(patchingId, k -> new ArrayList<>())
	                    .add(task);
	            }

	            /* ===============================
	             * 4️⃣ BUILD FINAL TREE
	             * =============================== */
	            List<Map<String, Object>> finalList = new ArrayList<>();

	            for (Object[] r : patchingOrders) {
	                String patchingId = (String) r[0];

	                Map<String, Object> patching = new LinkedHashMap<>();
	                patching.put("patchingId", r[0]);
	                patching.put("assignedTo", r[1]);
	                patching.put("plannedExecutionDate", r[2]);
	                patching.put("actualExecutionDate", r[3]);
	                patching.put("patchingStatus", r[4]);
	                patching.put("patchingNote", r[5]);
	                patching.put("createdDate", r[6]);
	                patching.put("lastModifiedDate", r[7]);

	                patching.put(
	                    "tasks",
	                    tasksByPatchingId.getOrDefault(patchingId, new ArrayList<>())
	                );

	                finalList.add(patching);
	            }

	            /* ===============================
	             * 5️⃣ SEND TO FRONTEND
	             * =============================== */
	            Map<String, Object> responseMap = new LinkedHashMap<>();
	            responseMap.put("PatchingList", finalList);

	            model.addAttribute("PatchingList", mapper.writeValueAsString(responseMap));
	            System.out.println(mapper.writeValueAsString(responseMap));

	        } catch (Exception e) {
	            logger.info("Error at PatchingWorkOrderTree: " + e);
	            e.printStackTrace();
	            model.addAttribute("PatchingList", "");
	        } finally {
	            if (session != null && session.isOpen()) {
	                session.close();
	            }
	        }
	    }

	    return "PatchingWorkOrderTree";
	}

	
	
	
	
	
	
	
	
	
	
	

	}


	         
	

