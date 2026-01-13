package com.aliat.alm.controller;


import com.aliat.alm.models.ArNode;
import com.aliat.alm.models.ArPartNumber;
import com.aliat.alm.models.ArSerialNumber;
import com.aliat.alm.models.ArSite;
import com.aliat.alm.models.AssetRegistry;
import com.aliat.alm.models.DNIFormView;
import com.aliat.alm.models.DiscoveryNewItemNode;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.models.PatchingWorkOrder;
import com.aliat.alm.models.PurchaseOrder;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.models.WorkOrderTask;
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

	            rtn.put("status", patchingStatus);

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

	            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

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

	            // -------- GENERATE ID IF NEW --------
	            if (woTaskId == null || woTaskId.isEmpty()) {
	                woTaskId = "WO_TASK_"
	                        + ((Number) session.createNativeQuery(
	                        "SELECT WO_TASK_SEQ.NEXTVAL FROM DUAL"
	                ).uniqueResult()).intValue();
	                task.setWoTaskId(woTaskId);
	            }

	            session.saveOrUpdate(task);
	            tx.commit();

	            rtn.put("taskStatus", taskStatus);
	            rtn.put("woTaskId", woTaskId);

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

	

	}


	         
	

