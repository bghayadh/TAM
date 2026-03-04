package com.aliat.alm.controller;


import com.aliat.alm.models.ArNode;
import com.aliat.alm.models.ArPartNumber;
import com.aliat.alm.models.ArSerialNumber;
import com.aliat.alm.models.ArSite;
import com.aliat.alm.models.AssetRegistry;
import com.aliat.alm.models.DNIFormView;
import com.aliat.alm.models.DiscoveryNewItemNode;
import com.aliat.alm.models.DistributionBoardMapping;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.models.PatchingWorkOrder;
import com.aliat.alm.models.PurchaseOrder;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.models.Warehouse;
import com.aliat.alm.models.WorkOrder;
import com.aliat.alm.models.WorkOrderDestination;
import com.aliat.alm.models.WorkOrderSource;
import com.aliat.alm.models.WorkOrderTask;
import com.aliat.alm.models.FarNode;
import com.aliat.alm.models.FarPartNumber;
import com.aliat.alm.models.FarSerialNumber;
import com.aliat.alm.models.FarSite;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
public class PatchingWorkOrderController {
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
	
	@Autowired
	Form form;
	
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

	            System.out.println(mapper.writeValueAsString(patchingOrders));
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
	            	    "FP_JUNCTION_ID, FP_JUNCTION_NAME, " +
	            	    // NEW BP COLUMNS (37-58)
	            	    "BP_STRAND_COLOR, BP_TUBE_COLOR, BP_LOCATION_TYPE, BP_LOCATION_ID, BP_LOCATION_NAME, BP_LOCATION, " +
	            	    "BP_EQUIPMENT_TYPE, BP_EQUIPMENT, BP_STRAND_NB, BP_TUBE_NB, " +
	            	    "BP_EQUIPMENT_ID, BP_EQUIPMENT_NAME, BP_ADDRESS, BP_STATUS, " +
	            	    "BP_STRAND_ID, BP_STRAND_NAME, BP_TUBE_ID, BP_TUBE_NAME, BP_FIBER_ID, BP_FIBER_NAME, " +
	            	    "BP_JUNCTION_ID, BP_JUNCTION_NAME, " +
	            	    // NEW BACK COLUMNS (59-61)
	            	    "BACK_MODULE, BACK_KIT_SERIAL_NUM, BACK_PORT_NUM, " +
	            	    " FAR_NEAR_KIT_SERIAL_NUM, FAR_NEAR_MODULE, FAR_NEAR_PORT_NUM, FP_STRAND_NB, FP_STRAND_ID  FROM WORK_ORDER_TASK"
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
	            	    
	            	    // NEW BP FIELDS (r[37] to r[58])
	            	    task.put("bpStrandColor", r[37]);
	            	    task.put("bpTubeColor", r[38]);
	            	    task.put("bpLocationType", r[39]);
	            	    task.put("bpLocationId", r[40]);
	            	    task.put("bpLocationName", r[41]);
	            	    task.put("bpLocation", r[42]);
	            	    task.put("bpEquipmentType", r[43]);
	            	    task.put("bpEquipment", r[44]);
	            	    task.put("bpStrandNb", r[45]);
	            	    task.put("bpTubeNb", r[46]);
	            	    task.put("bpEquipmentId", r[47]);
	            	    task.put("bpEquipmentName", r[48]);
	            	    task.put("bpAddress", r[49]);
	            	    task.put("bpStatus", r[50]);
	            	    task.put("bpStrandId", r[51]);
	            	    task.put("bpStrandName", r[52]);
	            	    task.put("bpTubeId", r[53]);
	            	    task.put("bpTubeName", r[54]);
	            	    task.put("bpFiberId", r[55]);
	            	    task.put("bpFiberName", r[56]);
	            	    task.put("bpJunctionId", r[57]);
	            	    task.put("bpJunctionName", r[58]);
	            	    
	            	    // NEW BACK FIELDS (r[59] to r[61])
	            	    task.put("backModule", r[59]);
	            	    task.put("backKitSerialNum", r[60]);
	            	    task.put("backPortNum", r[61]);
	            	    task.put("farNearKitSerialNum", r[62]);
	            	    task.put("farNearModule", r[63]);
	            	    task.put("farNearPortNum", r[64]);
	            	    task.put("fpStrandNb", r[65]);
	            	    task.put("fpStrandId", r[66]);
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

	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/savePatchingOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savePatchingOrder(
	        Locale locale,
	        Model model,
	        HttpServletRequest request,
	        @ModelAttribute ItemParameters itemParameters,
	        HttpServletResponse response) {

	    Map<String, Object> rtn = new LinkedHashMap<>();

	    if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	        rtn.put("Login", "redirect:/");
	        return rtn;
	    }

	    Session session = AlmDbSession.getInstance().getSession();

	    if (session != null && session.isOpen()) {

	        notifications.headerNotifications(session, model);

	        try {
	        	tx = session.beginTransaction();
	            Calendar calendar = new GregorianCalendar();
	            calendar.setTime(new Date());
	            int year = calendar.get(Calendar.YEAR);

	            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

	            String patchingId = request.getParameter("patchingId");
	            String patchingStatus = request.getParameter("patchingStatus");
	            String assignedTo = request.getParameter("assignedTo");
	            String patchingNote = request.getParameter("patchingNote");

	            Timestamp plannedExecutionDate = null;
	            Timestamp actualExecutionDate = null;
	            Timestamp creationDate = null;

	            String plannedExecutionStr = request.getParameter("plannedExecutionDate");
	            if (plannedExecutionStr != null && !plannedExecutionStr.isEmpty()) {
	                plannedExecutionDate =
	                        new Timestamp(formatter.parse(plannedExecutionStr).getTime());
	            }

	            String actualExecutionStr = request.getParameter("actualExecutionDate");
	            if (actualExecutionStr != null && !actualExecutionStr.isEmpty()) {
	                actualExecutionDate =
	                        new Timestamp(formatter.parse(actualExecutionStr).getTime());
	            }

	            String creationDateStr = request.getParameter("creationDate");
	            if (creationDateStr != null && !creationDateStr.isEmpty()) {
	                creationDate =
	                        new Timestamp(formatter.parse(creationDateStr).getTime());
	            } else {
	                creationDate = new Timestamp(System.currentTimeMillis());
	            }

	            Timestamp lastModifiedDate = new Timestamp(System.currentTimeMillis());

	            PatchingWorkOrder pwo = new PatchingWorkOrder();
	            pwo.setPatchingId(patchingId);
	            pwo.setPatchingStatus(patchingStatus);
	            pwo.setAssignedTo(assignedTo);
	            pwo.setPlannedExecutionDate(plannedExecutionDate);
	            pwo.setActualExecutionDate(actualExecutionDate);
	            pwo.setCreatedDate(creationDate);
	            pwo.setLastModifiedDate(lastModifiedDate);
	            pwo.setPatchingNote(patchingNote);

	            if (patchingId == null || patchingId.isEmpty()) {
	                patchingId = "PATCHING_WO_" + year + "_"
	                        + ((Number) session.createNativeQuery(
	                        "SELECT PATCHING_WO_SEQ.NEXTVAL FROM DUAL"
	                ).uniqueResult()).intValue();
	                pwo.setPatchingId(patchingId);
	            }

	            session.saveOrUpdate(pwo);
	            session.flush();
	            session.clear();

	            formatter = new SimpleDateFormat("MM/dd/yyyy");
	         // ✅ FORMAT Timestamp → String
	            String formattedLastModified = formatter.format(lastModifiedDate);  // "02/04/2026 10:57 PM"

	            rtn.put("status", patchingStatus);
	            rtn.put("id", patchingId);
	            rtn.put("lastModifiedDate", formattedLastModified);  // Send STRING directly

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

	    return rtn;
	}

	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveTaskOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTaskOrder(
	        Locale locale,
	        Model model,
	        HttpServletRequest request,
	        @ModelAttribute ItemParameters itemParameters,
	        HttpServletResponse response) {

	    Map<String, Object> rtn = new LinkedHashMap<>();

	    if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	        rtn.put("Login", "redirect:/");
	        return rtn;
	    }

	    Session session = AlmDbSession.getInstance().getSession();

	    if (session != null && session.isOpen()) {

	        notifications.headerNotifications(session, model);

	        try {
	            tx = session.beginTransaction();
	            DateFormat formatter;
	          
	         
	            if(request.getParameter("formview")== null) {
	            	 formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	            	
	           
	            }
	            else{
	            	
	            	 formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	            }
	            System.out.println(formatter);
	            // -------- BASIC FIELDS --------
	            String woTaskId = request.getParameter("woTaskId");
	            String patchingId = request.getParameter("patchingId");
	            String taskType = request.getParameter("taskType");
	            String taskStatus = request.getParameter("taskStatus");

	            // -------- DATES --------
	            Timestamp creationDate = null;
	            Timestamp completionDate = null;

	            String creationDateStr = request.getParameter("creationDate");
	            if (creationDateStr != null && !creationDateStr.isEmpty()) {
	                creationDate = new Timestamp(formatter.parse(creationDateStr).getTime());
	            } else {
	                creationDate = new Timestamp(System.currentTimeMillis());
	            }

	            String completionDateStr = request.getParameter("completionDate");
	            if (completionDateStr != null && !completionDateStr.isEmpty()) {
	                completionDate = new Timestamp(formatter.parse(completionDateStr).getTime());
	            }

	            Timestamp lastModifiedDate = new Timestamp(System.currentTimeMillis());

	            // -------- CREATE ENTITY --------
	            WorkOrderTask task = new WorkOrderTask();
	            task.setWoTaskId(woTaskId);
	            task.setPatchingId(patchingId);
	            task.setTaskType(taskType);
	            task.setTaskStatus(taskStatus);
	            task.setCreationDate(creationDate);
	            task.setCompletionDate(completionDate);
	            task.setLastModifiedDate(lastModifiedDate);

	            // -------- LOCATION / DB --------
	            task.setDbId(request.getParameter("dbId"));
	            task.setDbPortId(request.getParameter("dbPortId"));

	            // -------- NUMERIC FIELDS --------
	            task.setRowColIndex(Integer.parseInt(request.getParameter("rowColIndex")));
	            task.setRowNumber(Integer.parseInt(request.getParameter("rowNumber")));
	            task.setColumnNumber(Integer.parseInt(request.getParameter("columnNumber")));

	            // -------- NEAR --------
	            task.setNearModule(request.getParameter("nearModule"));
	            task.setNearPortNum(request.getParameter("nearPortNum"));
	            task.setNearPatchType(request.getParameter("nearPatchType"));

	            // -------- FP LOCATION --------
	            task.setFpLocationType(request.getParameter("fpLocationType"));
	            task.setFpLocationId(request.getParameter("fpLocationId"));
	            task.setFpLocationName(request.getParameter("fpLocationName"));
	            task.setFpLocation(request.getParameter("fpLocation"));
	            task.setPatchingId(request.getParameter("taskPatchingId"));

	            // -------- FP EQUIPMENT --------
	            task.setFpEquipmentType(request.getParameter("fpEquipmentType"));
	            task.setFpEquipment(request.getParameter("fpEquipment"));
	            task.setFpEquipmentId(request.getParameter("fpEquipmentId"));
	            task.setFpEquipmentName(request.getParameter("fpEquipmentName"));

	            // -------- FP CABLE --------
	            task.setFpAddress(request.getParameter("fpAddress"));
	            task.setFpTubeNb(request.getParameter("fpTubeNb"));
	            task.setFpStrandColor(request.getParameter("fpStrandColor"));
	            task.setFpTubeColor(request.getParameter("fpTubeColor"));
	            task.setFpStrandName(request.getParameter("fpStrandName"));
	            task.setFpTubeId(request.getParameter("fpTubeId"));
	            task.setFpTubeName(request.getParameter("fpTubeName"));
	            task.setFpFiberId(request.getParameter("fpFiberId"));
	            task.setFpFiberName(request.getParameter("fpFiberName"));
	            task.setFpKitSerialNum(request.getParameter("fpKitSerialNum"));

	            // -------- MODULE / PORT / JUNCTION --------
	            task.setFpModule(request.getParameter("fpModule"));
	            task.setFpPortNum(request.getParameter("fpPortNum"));
	            task.setFpJunctionId(request.getParameter("fpJunctionId"));
	            task.setFpJunctionName(request.getParameter("fpJunctionName"));
	         // -------- BP LOCATION --------
	            task.setBpLocationType(request.getParameter("bpLocationType"));
	            task.setBpLocationId(request.getParameter("bpLocationId"));
	            task.setBpLocationName(request.getParameter("bpLocationName"));
	            task.setBpLocation(request.getParameter("bpLocation"));

	            // -------- BP EQUIPMENT --------
	            task.setBpEquipmentType(request.getParameter("bpEquipmentType"));
	            task.setBpEquipment(request.getParameter("bpEquipment"));
	            task.setBpEquipmentId(request.getParameter("bpEquipmentId"));
	            task.setBpEquipmentName(request.getParameter("bpEquipmentName"));

	            // -------- BP CABLE / STATUS --------
	            task.setBpAddress(request.getParameter("bpAddress"));
	            task.setBpStatus(request.getParameter("bpStatus"));

	            task.setBpStrandColor(request.getParameter("bpStrandColor"));
	            task.setBpTubeColor(request.getParameter("bpTubeColor"));

	            task.setBpStrandId(request.getParameter("bpStrandId"));
	            task.setBpStrandName(request.getParameter("bpStrandName"));
	            task.setBpStrandNb(request.getParameter("bpStrandNb"));
	            
	            task.setBpTubeId(request.getParameter("bpTubeId"));
	            task.setBpTubeName(request.getParameter("bpTubeName"));
	            task.setBpTubeNb(request.getParameter("bpTubeNb"));
	            
	            task.setBpFiberId(request.getParameter("bpFiberId"));
	            task.setBpFiberName(request.getParameter("bpFiberName"));

	            // -------- BP JUNCTION --------
	            task.setBpJunctionId(request.getParameter("bpJunctionId"));
	            task.setBpJunctionName(request.getParameter("bpJunctionName"));

	            // -------- BACK --------
	            task.setBackModule(request.getParameter("backModule"));
	            task.setBackKitSerialNum(request.getParameter("backKitSerialNum"));
	            task.setBackPortNum(request.getParameter("backPortNum"));
	            task.setFarNearKitSerialNum(request.getParameter("farNearKitSerialNum"));
	            task.setFarNearModule(request.getParameter("farNearModule"));
	            task.setFarNearPortNum(request.getParameter("farNearPortNum"));
	            task.setFpStrandNb(request.getParameter("fpStrandNb"));
	            task.setFpStrandId(request.getParameter("fpStrandId"));
	            task.setBpStrandNb(request.getParameter("bpStrandNb"));
	           
	            // -------- GENERATE ID IF NEW --------
	            if (woTaskId == null || woTaskId.isEmpty()) {
	                woTaskId = "WO_TASK_"
	                        + ((Number) session.createNativeQuery(
	                        "SELECT WO_TASK_SEQ.NEXTVAL FROM DUAL"
	                ).uniqueResult()).intValue();
	                task.setWoTaskId(woTaskId);
	            }
	            
	            

	            session.saveOrUpdate(task);
	            
	            if ("Completed".equalsIgnoreCase(taskStatus)) {
	             DistributionBoardMapping mapping =
	                    session.get(DistributionBoardMapping.class, request.getParameter("dbPortId"));

	            if (mapping != null) {

	            // -------- BASIC INFO --------
	            mapping.setDb_Port_Id(request.getParameter("dbPortId"));
	            mapping.setDistributionBoardId(request.getParameter("dbId"));
	            mapping.setRowColIndex(request.getParameter("rowColIndex"));
	            mapping.setRowNum(request.getParameter("rowNumber"));
	            mapping.setColNum(request.getParameter("columnNumber"));

	            // -------- FP --------
	            mapping.setfP_Status("Connected");
	            mapping.setfP_LocationType(request.getParameter("fpLocationType"));
	            mapping.setfP_LocationId(request.getParameter("fpLocationId"));
	            mapping.setfP_LocationName(request.getParameter("fpLocationName"));
	            mapping.setfP_Location(request.getParameter("fpLocation"));

	            mapping.setfP_EquipmentType(request.getParameter("fpEquipmentType"));
	            mapping.setfP_Equipment(request.getParameter("fpEquipment"));
	            mapping.setfP_EquipmentId(request.getParameter("fpEquipmentId"));
	            mapping.setfP_EquipmentName(request.getParameter("fpEquipmentName"));

	            mapping.setfP_Address(request.getParameter("fpAddress"));
	            mapping.setfP_StrandId(request.getParameter("fpStrandId"));
	            mapping.setfP_StrandName(request.getParameter("fpStrandName"));
	            mapping.setfP_TubeId(request.getParameter("fpTubeId"));
	            mapping.setfP_TubeName(request.getParameter("fpTubeName"));
	            mapping.setbP_TubeNb(request.getParameter("bpTubeNb"));
	            
	            mapping.setfP_FiberId(request.getParameter("fpFiberId"));
	            mapping.setfP_FiberName(request.getParameter("fpFiberName"));
	            mapping.setfP_StrandColor(request.getParameter("fpStrandColor"));
	            mapping.setfP_StrandNb(request.getParameter("fbStrandNb"));
	            mapping.setfP_TubeColor(request.getParameter("fpTubeColor"));
	            mapping.setfP_TubeNb(request.getParameter("fpTubeNb"));

	            mapping.setfP_JunctionId(request.getParameter("fpJunctionId"));
	            mapping.setfP_JunctionName(request.getParameter("fpJunctionName"));

	            // -------- BP --------
	            mapping.setbP_Status(request.getParameter("bpStatus"));
	            mapping.setbP_LocationType(request.getParameter("bpLocationType"));
	            mapping.setbP_LocationId(request.getParameter("bpLocationId"));
	            mapping.setbP_LocationName(request.getParameter("bpLocationName"));
	            mapping.setbP_Location(request.getParameter("bpLocation"));

	            mapping.setbP_EquipmentType(request.getParameter("bpEquipmentType"));
	            mapping.setbP_Equipment(request.getParameter("bpEquipment"));
	            mapping.setbP_EquipmentId(request.getParameter("bpEquipmentId"));
	            mapping.setbP_EquipmentName(request.getParameter("bpEquipmentName"));

	            mapping.setbP_Address(request.getParameter("bpAddress"));
	            mapping.setbP_StrandId(request.getParameter("bpStrandId"));
	            mapping.setbP_StrandName(request.getParameter("bpStrandName"));
	            mapping.setbP_TubeId(request.getParameter("bpTubeId"));
	            mapping.setbP_TubeName(request.getParameter("bpTubeName"));
	            mapping.setbP_FiberId(request.getParameter("bpFiberId"));
	            mapping.setbP_FiberName(request.getParameter("bpFiberName"));
	            mapping.setbP_StrandColor(request.getParameter("bpStrandColor"));
	            mapping.setbP_TubeColor(request.getParameter("bpTubeColor"));

	            mapping.setbP_JunctionId(request.getParameter("bpJunctionId"));
	            mapping.setbP_JunctionName(request.getParameter("bpJunctionName"));

	            // -------- NEAR --------
	            mapping.setNearModule(request.getParameter("nearModule"));
	            mapping.setNearPortNum(request.getParameter("nearPortNum"));
	            mapping.setNearPatchType(request.getParameter("nearPatchType"));

	            // -------- BACK --------
	            mapping.setBackModule(request.getParameter("backModule"));
	            mapping.setBackportNum(request.getParameter("backPortNum"));
	            mapping.setBackKitSerialNum(request.getParameter("backKitSerialNum"));
	            mapping.setFarKitSerialNum(request.getParameter("farNearKitSerialNum"));
	            mapping.setFarModule(request.getParameter("farNearModule"));
	            mapping.setFarPortNum(request.getParameter("farNearPortNum"));
	            mapping.setfP_StrandNb(request.getParameter("fpStrandNb"));
	            mapping.setfP_StrandId(request.getParameter("fpStrandId"));
	            mapping.setbP_StrandNb(request.getParameter("bpStrandNb"));
	       
	            session.saveOrUpdate(mapping);
	            
	            
	            
	            String result[] = new String[4];
				int SelectedIndex = 0;
			String	navAction = request.getParameter("NavAction");
				result = form.NavigationNP(session, "WORK_ORDER_TASK", "WO_TASK_ID", woTaskId, "LAST_MODIFIED_DATE",
							navAction);
					SelectedIndex = Integer.parseInt(result[1]);
					woTaskId = result[2];
					model.addAttribute("workOrderCount", Integer.parseInt(result[0]));
					model.addAttribute("SelectedIndex", SelectedIndex);
				
	            }
	            }
	            tx.commit();

	            rtn.put("taskStatus", taskStatus);
	            rtn.put("woTaskId", woTaskId);
	            rtn.put("patchingId", patchingId);
	            rtn.put("taskType", taskType);

	        } catch (Exception e) {
	            if (tx != null) tx.rollback();
	            logger.info("Error at saveWorkOrderTask: " + e);
	            e.printStackTrace();
	        } finally {
	            if (session != null && session.isOpen()) {
	                session.close();
	            }
	        }
	    }

	    return rtn;
	}

	@RequestMapping(value = "/deletePatchingWO", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deletePatchingWO(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String patchingId = request.getParameter("patchingId");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createQuery("delete PatchingWorkOrder where patchingId =:param1");
				query.setParameter("param1", patchingId);
				query.executeUpdate();

				query = session.createQuery("delete WorkOrderTask where patchingId =:param1");
				query.setParameter("param1", patchingId);
				query.executeUpdate();

				

			} catch (Exception e) {
				logger.info("Error in deleting on the level of patching Work order Tree with a message : " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}

		return rtn;

	}
	
	@RequestMapping(value = "/deletePatchingWoTree", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deletePatchingWoTree(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String[] checkedPatchingIds = request.getParameterValues("checkedPatchingIds[]");
		  List<String> patchingIdList = checkedPatchingIds != null ? 
                  Arrays.asList(checkedPatchingIds) : new ArrayList<>();
		System.out.println(checkedPatchingIds);
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createQuery("delete PatchingWorkOrder where patchingId IN (:patchingIds)");
				query.setParameter("patchingIds", patchingIdList);
				query.executeUpdate();

				query = session.createQuery("delete WorkOrderTask where patchingId IN (:patchingIds)");
				query.setParameter("patchingIds", patchingIdList);
				query.executeUpdate();

				

			} catch (Exception e) {
				logger.info("Error in deleting on the level of patching Work order Tree with a message : " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}

		return rtn;

	}

	@RequestMapping(value = "/deleteWOTask", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteWOTask(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String taskId = request.getParameter("taskId");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				
				query = session.createQuery("delete WorkOrderTask where woTaskId =:param1");
				query.setParameter("param1", taskId);
				query.executeUpdate();

				

			} catch (Exception e) {
				logger.info("Error in deleting on the level of patching Work order Tree with a message : " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}

		return rtn;

	}
	
	@RequestMapping(value = "/deleteTaskTree", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteTaskTree(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String[] checkedTaskIds = request.getParameterValues("checkedTaskIds[]");
		  List<String> taskIdList = checkedTaskIds != null ? 
                  Arrays.asList(checkedTaskIds) : new ArrayList<>();
		System.out.println(taskIdList);
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				
				query = session.createQuery("delete WorkOrderTask where woTaskId IN (:taskIds)");
				query.setParameter("taskIds", taskIdList);
				query.executeUpdate();

				

			} catch (Exception e) {
				logger.info("Error in deleting on the level of task Work order Tree with a message : " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}

		return rtn;

	}
	
	@RequestMapping(value = "/PatchingWorkOrderTaskListView", method = RequestMethod.GET)
	public String PatchingWorkOrderListView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response){

		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			
			session = AlmDbSession.getInstance().getSession();
			if (session!=null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				
				try {
					
				   
				       query = session.createSQLQuery(
				    		    "SELECT " +
				    		    "  w.WO_TASK_ID, " +
				    		    "  w.WO_TASK_ID AS ID, " +
				    		    "  w.PATCHING_ID, " +
				    		    "  w.ROW_COL_INDEX, " +
				    		    "  w.TASK_TYPE, " +
				    		    "  w.TASK_STATUS, " +
				    		    "  TO_CHAR(w.COMPLETION_DATE, 'YYYY-MM-DD HH24:MI:SS') AS COMPLETION_DATE, " +
				    		    "  TO_CHAR(w.LAST_MODIFIED_DATE, 'YYYY-MM-DD HH24:MI:SS') AS LAST_MODIFIED_DATE, " +
				    		    "  TO_CHAR(w.CREATION_DATE, 'YYYY-MM-DD HH24:MI:SS') AS CREATION_DATE, " +
				    		    "  w.DB_PORT_ID, " +
				    		    "  w.DB_ID, " +
				    		    "  d.DB_NAME, " +
				    		    "  d.SITE, " +
				    		    "  d.SITE_NAME, " +
				    		    "  d.WAREHOUSE " +
				    		    "FROM WORK_ORDER_TASK w " +
				    		    "LEFT JOIN DISTRIBUTION_BOARD d ON w.DB_ID = d.DB_ID " +
				    		    "ORDER BY w.LAST_MODIFIED_DATE DESC"
				    		);

				    		System.out.println("query " + mapper.writeValueAsString(query.list()));
				    		model.addAttribute("WorkOrderTaskDt", mapper.writeValueAsString(query.list()));

				} catch (Exception e) {
						
					model.addAttribute("WorkOrderDt", "");
				}finally {
					if (session!=null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}else {
				logger.info("Could not connect to DB in WorkOrderListView method");
			
			}
			return "PatchingWorkOrderTaskListView";
		}
		
	}
	@RequestMapping(value = "/WoTaskListDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> WoTaskListDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

	Map<String, Object> rtn = new LinkedHashMap<>();
	if(LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	}else {
		session = AlmDbSession.getInstance().getSession();
		if(session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				
				String[] valuesWorkOrderTaskIds = request.getParameterValues("workOrdTaskId[]");
				System.out.println(mapper.writeValueAsString(valuesWorkOrderTaskIds));
				for(int i = 0;i<valuesWorkOrderTaskIds.length;i++) {
					System.out.println(valuesWorkOrderTaskIds[i]);
					query = session.createSQLQuery("DELETE WORK_ORDER_TASK WHERE WO_TASK_ID='"+valuesWorkOrderTaskIds[i]+"'");
					query.executeUpdate();
					
						
				}
		
				

			}catch (Exception e) {
				logger.info("Error in Controller Deleting WorkOrder, WorkOrder Source, WorkOrder Destination: "+e.getMessage());
			} finally {
				if(session !=null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		

	}

	rtn.put("YaraTest", "DeleteDone");
	return rtn;

	}
	@RequestMapping(value = "/PatchingWorkOrderTaskFormView", method = RequestMethod.GET)
	public String PatchingWorkOrderTaskFormView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {

		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				
				notifications.headerNotifications(session, model);
				String result[] = new String[4];
				int SelectedIndex = 0;
				Date date;
				WorkOrderTask workOrder = null;
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				String navAction = "2";
				String workOrderTaskId = request.getParameter("workOrdTaskId");
				ObjectMapper mapper = new ObjectMapper();
				
				
				try {
					
					System.out.println( request.getParameter("NavAction"));
					
					navAction = request.getParameter("NavAction");
					if(workOrderTaskId == null) {

						 date = new Timestamp(System.currentTimeMillis());
						 model.addAttribute("wocreationDate", formatter.format(date).toString());
						 model.addAttribute("wolastModifiedDate", formatter.format(date).toString());
						 model.addAttribute("docStatus", "addNew");
						 model.addAttribute("SelectedIndex", "addNew");
						 model.addAttribute("workOrderCount", "addNew");
						 model.addAttribute("woPatchingId",request.getParameter("woPatchingId"));
						 model.addAttribute("patchingOrderNote", request.getParameter("patchingOrderNote"));
						return "PatchingWorkOrderTaskFormView";
					}
					
					System.out.println(workOrderTaskId);
					result = form.NavigationNP(session, "WORK_ORDER_TASK", "WO_TASK_ID", workOrderTaskId, "LAST_MODIFIED_DATE",
							navAction);
					SelectedIndex = Integer.parseInt(result[1]);
					workOrderTaskId = result[2];
					workOrder = (WorkOrderTask) session.get(WorkOrderTask.class, workOrderTaskId);
					model.addAttribute("workOrderCount", Integer.parseInt(result[0]));
					model.addAttribute("SelectedIndex", SelectedIndex);
					
					WorkOrderTask workOrderObject= (WorkOrderTask) session.get(WorkOrderTask.class, workOrderTaskId);	
				
					PatchingWorkOrder patchingOrder = (PatchingWorkOrder) session.get(PatchingWorkOrder.class,  workOrderObject.getPatchingId());	
					
					if(workOrderObject != null) {
					    // ID fields
					    model.addAttribute("workOrdIdTask", workOrderObject.getWoTaskId());
					    model.addAttribute("woPatchingId", workOrderObject.getPatchingId());
					    
					    // Date fields - handle null with current timestamp
					 
					    
					    // Creation Date
					    if (workOrderObject.getCreationDate() == null) {
					        date = new Timestamp(System.currentTimeMillis());
					        model.addAttribute("wocreationDate", formatter.format(date).toString());
					    } else {
					        date = workOrderObject.getCreationDate();
					        model.addAttribute("wocreationDate", formatter.format(date).toString());
					    }
					    
					    // Last Modified Date
					    if (workOrderObject.getLastModifiedDate() == null) {
					        date = new Timestamp(System.currentTimeMillis());
					        model.addAttribute("wolastModifiedDate", formatter.format(date).toString());
					    } else {
					        date = workOrderObject.getLastModifiedDate();
					        model.addAttribute("wolastModifiedDate", formatter.format(date).toString());
					    }
					    
					    // Completion Date
					    if (workOrderObject.getCompletionDate() == null) {
					        date = new Timestamp(System.currentTimeMillis());
					        model.addAttribute("wocompletionDate", formatter.format(date).toString());
					    } else {
					        date = workOrderObject.getCompletionDate();
					        model.addAttribute("wocompletionDate", formatter.format(date).toString());
					    }
					    
					    // String fields - direct assignment (null-safe)
					    model.addAttribute("woTaskType", workOrderObject.getTaskType());
					    model.addAttribute("woStatus", workOrderObject.getTaskStatus());
					    model.addAttribute("woDbId", workOrderObject.getDbId());
					    model.addAttribute("woDbPortId", workOrderObject.getDbPortId());
					    
					    // Integer fields - handle null with default 0
					    model.addAttribute("woRowColIndex", workOrderObject.getRowColIndex() != null ? workOrderObject.getRowColIndex() : 0);
					    model.addAttribute("woRowNumber", workOrderObject.getRowNumber() != null ? workOrderObject.getRowNumber() : 0);
					    model.addAttribute("woColumnNumber", workOrderObject.getColumnNumber() != null ? workOrderObject.getColumnNumber() : 0);
					    
					    // NEAR fields
					    model.addAttribute("woNearModule", workOrderObject.getNearModule());
					    model.addAttribute("woNearPortNum", workOrderObject.getNearPortNum());
					    model.addAttribute("woNearPatchType", workOrderObject.getNearPatchType());
					    
					    // FP fields (Far Patch)
					    model.addAttribute("woFpLocationType", workOrderObject.getFpLocationType());
					    model.addAttribute("woFpLocationId", workOrderObject.getFpLocationId());
					    model.addAttribute("woFpLocationName", workOrderObject.getFpLocationName());
					    model.addAttribute("woFpLocation", workOrderObject.getFpLocation());
					    model.addAttribute("woFpEquipmentType", workOrderObject.getFpEquipmentType());
					    model.addAttribute("woFpEquipment", workOrderObject.getFpEquipment());
					    model.addAttribute("woFpEquipmentId", workOrderObject.getFpEquipmentId());
					    model.addAttribute("woFpEquipmentName", workOrderObject.getFpEquipmentName());
					    model.addAttribute("woFpAddress", workOrderObject.getFpAddress());
					    model.addAttribute("woFpTubeNb", workOrderObject.getFpTubeNb());
					    model.addAttribute("woFpStrandNb", workOrderObject.getFpStrandNb());
					    model.addAttribute("woFpStrandId", workOrderObject.getFpStrandId());
					    model.addAttribute("woFpStrandColor", workOrderObject.getFpStrandColor());
					    model.addAttribute("woFpTubeColor", workOrderObject.getFpTubeColor());
					    model.addAttribute("woFpStrandName", workOrderObject.getFpStrandName());
					    model.addAttribute("woFpTubeId", workOrderObject.getFpTubeId());
					    model.addAttribute("woFpTubeName", workOrderObject.getFpTubeName());
					    model.addAttribute("woFpFiberId", workOrderObject.getFpFiberId());
					    model.addAttribute("woFpFiberName", workOrderObject.getFpFiberName());
					    model.addAttribute("woFpKitSerialNum", workOrderObject.getFpKitSerialNum());
					    model.addAttribute("woFpModule", workOrderObject.getFpModule());
					    model.addAttribute("woFpPortNum", workOrderObject.getFpPortNum());
					    model.addAttribute("woFpJunctionId", workOrderObject.getFpJunctionId());
					    model.addAttribute("woFpJunctionName", workOrderObject.getFpJunctionName());
					    
					    // FAR_NEAR fields
					    model.addAttribute("woFarNearKitSerialNum", workOrderObject.getFarNearKitSerialNum());
					    model.addAttribute("woFarNearModule", workOrderObject.getFarNearModule());
					    model.addAttribute("woFarNearPortNum", workOrderObject.getFarNearPortNum());
					    
					    // BP fields (Back Patch)
					    model.addAttribute("woBpStrandColor", workOrderObject.getBpStrandColor());
					    model.addAttribute("woBpTubeColor", workOrderObject.getBpTubeColor());
					    model.addAttribute("woBpLocationType", workOrderObject.getBpLocationType());
					    model.addAttribute("woBpLocationId", workOrderObject.getBpLocationId());
					    model.addAttribute("woBpLocationName", workOrderObject.getBpLocationName());
					    model.addAttribute("woBpLocation", workOrderObject.getBpLocation());
					    model.addAttribute("woBpEquipmentType", workOrderObject.getBpEquipmentType());
					    model.addAttribute("woBpEquipment", workOrderObject.getBpEquipment());
					    model.addAttribute("woBpStrandNb", workOrderObject.getBpStrandNb());
					    model.addAttribute("woBpTubeNb", workOrderObject.getBpTubeNb());
					    model.addAttribute("woBpEquipmentId", workOrderObject.getBpEquipmentId());
					    model.addAttribute("woBpEquipmentName", workOrderObject.getBpEquipmentName());
					    model.addAttribute("woBpAddress", workOrderObject.getBpAddress());
					    model.addAttribute("woBpStatus", workOrderObject.getBpStatus());
					    model.addAttribute("woBpStrandId", workOrderObject.getBpStrandId());
					    model.addAttribute("woBpStrandName", workOrderObject.getBpStrandName());
					    model.addAttribute("woBpTubeId", workOrderObject.getBpTubeId());
					    model.addAttribute("woBpTubeName", workOrderObject.getBpTubeName());
					    model.addAttribute("woBpFiberId", workOrderObject.getBpFiberId());
					    model.addAttribute("woBpFiberName", workOrderObject.getBpFiberName());
					    model.addAttribute("woBpJunctionId", workOrderObject.getBpJunctionId());
					    model.addAttribute("woBpJunctionName", workOrderObject.getBpJunctionName());
					    
					    // BACK fields
					    model.addAttribute("woBackModule", workOrderObject.getBackModule());
					    model.addAttribute("woBackKitSerialNum", workOrderObject.getBackKitSerialNum());
					    model.addAttribute("woBackPortNum", workOrderObject.getBackPortNum());
					    model.addAttribute("patchingOrderNote", patchingOrder.getPatchingNote());
					    System.out.println(patchingOrder);
					}
					
					    
		  
			}catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				logger.info("Error in WorkOrderFormView due to \n "+ exceptionAsString);
			}finally {
				if (session!=null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}	
			
				
				
			}
			return "PatchingWorkOrderTaskFormView";
		}
	}
	
	@RequestMapping(value = "/GetAllWorkOrdersTask", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllWorkOrders(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String workOrder=request.getParameter("workOrder");
			session = AlmDbSession.getInstance().getSession();
			if(session != null && session.isOpen()) {
				tx = session.beginTransaction();
				
				System.out.println("workOrder :: " + workOrder);
				try {
					query= session.createSQLQuery("Select WO_TASK_ID,PATCHING_ID,TASK_TYPE,TASK_STATUS from WORK_ORDER_TASK"
					+ " where upper(WO_TASK_ID) like '%" + workOrder + "%' OR upper(PATCHING_ID) LIKE upper('%" + workOrder + "%') "
					+ " OR upper(TASK_TYPE) LIKE upper('%" + workOrder + "%') OR upper(TASK_STATUS) LIKE upper('%" + workOrder + "%') " );
					query.setFirstResult(0);
					query.setMaxResults(20);
					//model.addAttribute("allWorkOrders", mapper.writeValueAsString(query.list()));
					rtn.put("allWorkOrders", query.list());
					System.out.println(mapper.writeValueAsString(query.list()));
				}catch (Exception e) {
					logger.info("Errorrrrrrr");
				}finally {
					if(session !=null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			
			return rtn;
		}		
	}	
	

	
	@RequestMapping(value = "/GetAllWorkPatchingOrders", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllWorkPatchingOrders(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String workOrder=request.getParameter("workOrder");
			session = AlmDbSession.getInstance().getSession();
			if(session != null && session.isOpen()) {
				tx = session.beginTransaction();
				
				System.out.println("workOrder :: " + workOrder);
				try {
					query= session.createSQLQuery("Select PATCHING_ID,PATCHING_NOTE from PATCHING_WORK_ORDER"
					+ " where upper(PATCHING_ID) like '%" + workOrder + "%' OR upper(PATCHING_NOTE) LIKE upper('%" + workOrder + "%') "
					 );
					query.setFirstResult(0);
					query.setMaxResults(20);
					//model.addAttribute("allWorkOrders", mapper.writeValueAsString(query.list()));
					rtn.put("allWorkOrders", query.list());
					System.out.println(mapper.writeValueAsString(query.list()));
				}catch (Exception e) {
					logger.info("Errorrrrrrr");
				}finally {
					if(session !=null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			
			return rtn;
		}		
	}	
	
	
	
	}


	         
	

