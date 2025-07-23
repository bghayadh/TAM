package com.aliat.alm.discoveryNew;


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
import com.aliat.alm.models.DiscoveryNew;
import com.aliat.alm.models.DiscoveryNewItem;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DiscoveryTransController {
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
	@RequestMapping(value = "/DiscoveryNewApprovalListView", method = RequestMethod.GET)
	public String DiscoveryNewApprovalListView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		
		Session session = AlmDbSession.getInstance().getSession();
		
		if (session != null && session.isOpen()) {
			notifications.headerNotifications(session, model);
			
			try {
				
				
				permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
				        "Discovery New Project Manger", "Tree");
				String readProjectM = ((Integer) model.asMap().get("readTree")).toString();
				model.addAttribute("readProjectM", readProjectM);

				permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
				        "Discovery New Asset Manger", "Tree");
				String readAssetM = ((Integer) model.asMap().get("readTree")).toString();
				model.addAttribute("readAssetM", readAssetM);

				permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
				        "Discovery New Finance Manger", "Tree");
				String readFinanceM = ((Integer) model.asMap().get("readTree")).toString();
				model.addAttribute("readFinanceM", readFinanceM);

				// Build WHERE condition dynamically
				List<String> conditions = new ArrayList<>();
				conditions.add("APPROVAL_STATUS = 'q' ");
				if (!"0".equals(readProjectM)) {
				    conditions.add("(APPROVAL = 'Project Manager' AND APPROVAL_STATUS = '-- Select Option --') 	OR (APPROVAL = 'Operation Manager' AND APPROVAL_STATUS = '-- Select Option --') ");
				}
				if (!"0".equals(readAssetM)) {
				    conditions.add("(APPROVAL = 'Asset Unit' AND APPROVAL_STATUS = '-- Select Option --') OR (APPROVAL = 'Project Manager' AND APPROVAL_STATUS = 'Approved')");
				}
				
				if (!"0".equals(readFinanceM)) {
				    conditions.add("(APPROVAL = 'Finance' AND APPROVAL_STATUS = '-- Select Option --') OR (APPROVAL = 'Asset Unit' AND APPROVAL_STATUS = 'Approved')");
				}
System.out.println(readFinanceM);
				// Combine conditions with OR
				String whereClause = String.join(" OR ", conditions);

				String str =
				    "SELECT " +
				    "  DNI_ID AS dniI, " +
				    "  DNI_ID AS dniID, " +
				    "  TRANS_ID AS transID, " +
				    "  TRANS_TYPE AS transType, " +
				    "  CASE " +
				    "    WHEN APPROVAL = 'Asset Unit' AND APPROVAL_STATUS = 'Approved' THEN 'Finance' " +
				    "    WHEN APPROVAL = 'Project Manager' AND APPROVAL_STATUS = 'Approved' THEN 'Asset Unit' " +
				    "    ELSE APPROVAL " +
				    "  END AS approval, " +
				    "  FROM_SITE AS fromSite, " +
				    "  TO_SITE AS toSite, " +
				    "  PO_ID AS poID, " +
				    "  TO_CHAR(LAST_MODIFIED_DATE, 'YYYY-MM-DD HH24:MI:SS') AS lastModifiedDate " +
				    "FROM DISCOVERY_NEW_ITEM " +
				    "WHERE " + whereClause + " " +
				    "ORDER BY LAST_MODIFIED_DATE DESC";

			model.addAttribute("ListGridTable", mapper.writeValueAsString(session.createNativeQuery(str).list()));

				System.out.println(whereClause);
		
		} catch (Exception e) {
			logger.info("Error at DiscoveryNew ListView with a message: "+ e);
			e.printStackTrace();
			model.addAttribute("ListGridTable","");
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
			
		}

		return "DiscoveryNewApprovalListView";
	} 
	
	

	   @RequestMapping(value = "/DiscoveryNewApprovalListViewDelete", method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String, Object> DiscoveryNewApprovalListViewDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	      

			String idForm=null;
	        Map<String, Object> rtn = new LinkedHashMap<>();
	        if(LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
	        
	        session = AlmDbSession.getInstance().getSession();
	       
			if (session != null && session.isOpen()) {
	            tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
	            try {
	            	
	      		  String[] delIds = request.getParameterValues("slctDelDNItem[]");


	  		    if (delIds != null && delIds.length > 0) {
	  		        for (String id : delIds) {
	  		        	 query = session.createQuery("delete from DiscoveryNewItem t where t.dniID = :dniId");
	  			            query.setParameter("dniId", id);
	  			            query.executeUpdate();
	  			
	  		        }
	  		    }
	            	
	            } catch (Exception e) {
	                System.out.println("Error in creating session with the DiscoveryNewListViewDelete method " + e.getMessage());
	                logger.info("could not connect to DB in DiscoveryNewListViewDelete method ");
	                e.printStackTrace();
				}
				
				finally {
				        if (session != null && session.isOpen()) {
				            tx.commit();
				            session.close();
				         }
				}
				} else {
				        System.out.println("could not connect to DB in DiscoveryNewListViewDelete method");
				        logger.info("could not connect to DB in DiscoveryNewListViewDelete method ");
				}
			
			
			rtn.put("BassamTest", "DeleteDone");
	        return rtn;
	            
	    }

	
	
	
	
	
	
	
	
	@Autowired
	Form form;
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/DiscoveryNewApprovalFormView", method = RequestMethod.GET)
	public String DiscoveryNewApprovalFormView(Locale locale, Model model, HttpServletRequest request,
	                                          HttpServletResponse response) {
	    if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	        return LoginServices.checkSession(request, response);
	    } else {
	        String toNodeJson = "{}";
	        String fromNodeJson = "{}";
	        String navAction = "2";
	        String status = "";
	        String dniID = request.getParameter("dniID");
	        status = request.getParameter("status");
	        DiscoveryNewItem DNi = null;
	        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

	        System.out.println("---- Start DiscoveryNewApprovalFormView ----");
	        System.out.println("dniID param: " + dniID);
	        System.out.println("status param: " + status);

	        session = AlmDbSession.getInstance().getSession();
	        if (session != null && session.isOpen()) {
	            tx = session.beginTransaction();
	            notifications.headerNotifications(session, model);
	            model.addAttribute("DBConnStat", "successed");
	            try {
	                navAction = request.getParameter("NavAction");

	                if (status == null || status.isEmpty()) {
	                	if(dniID != null) {
	                	
	            
	                	String sql = "SELECT APPROVAL, APPROVAL_STATUS FROM DISCOVERY_NEW_ITEM WHERE DNI_ID = :param1";
	                	Query query = session.createNativeQuery(sql);
	                	query.setParameter("param1", dniID);

	                	Object[] result = (Object[]) query.getSingleResult();

	                	String app = result[0] != null ? result[0].toString() : "";
	                	String appStatus = result[1] != null ? result[1].toString() : "";

	                	if((app.equals("Project Manager") & appStatus.equals("-- Select Option --")) || 
	                			(app.equals("Operation Manager") & appStatus.equals("-- Select Option --"))
	                			|| (app.equals("Asset Unit") & appStatus.equals("-- Select Option --"))
	                			|| (app.equals("Finance") & appStatus.equals("-- Select Option --"))) {
	                		
	                		status= app;
	                	}
	                	
	                	else if((app.equals("Project Manager") & appStatus.equals("Approved"))) {
	                		
	                		status= "Asset Unit";
	                		
	                	}
                          
	                	else if((app.equals("Asset Unit") & appStatus.equals("Approved"))) {
	                		
	                		status= "Finance";
	                		
	                	}
	                	System.out.println(status);
	                	
	                }
	                
	                }
	                // safer: check status is not null
	                if ("Project Manager".equals(status) || "Operation Manager".equals(status)) {
	                    permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
	                            "Discovery New Project Manger", "Tree");
	                    model.addAttribute("read", model.asMap().get("readTree").toString());
	                    model.addAttribute("write", model.asMap().get("writeTree").toString());
	                    model.addAttribute("add", model.asMap().get("addTree").toString());
	                    model.addAttribute("delete", model.asMap().get("delTree").toString());
	                    model.addAttribute("save", model.asMap().get("saveTree").toString());
	                    model.addAttribute("approveReject", model.asMap().get("approveRejectTree").toString());
	                } else if ("Asset Unit".equals(status)) {
	                    permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
	                            "Discovery New Asset Manger", "Tree");
	                    model.addAttribute("read", model.asMap().get("readTree").toString());
	                    model.addAttribute("write", model.asMap().get("writeTree").toString());
	                    model.addAttribute("add", model.asMap().get("addTree").toString());
	                    model.addAttribute("delete", model.asMap().get("delTree").toString());
	                    model.addAttribute("save", model.asMap().get("saveTree").toString());
	                    model.addAttribute("approveReject", model.asMap().get("approveRejectTree").toString());
	              } else if ("Finance".equals(status)) {
	                    permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
	                            "Discovery New Finance Manger", "Tree");
	                    model.addAttribute("read", model.asMap().get("readTree").toString());
	                    model.addAttribute("write", model.asMap().get("writeTree").toString());
	                    model.addAttribute("add", model.asMap().get("addTree").toString());
	                    model.addAttribute("delete", model.asMap().get("delTree").toString());
	                    model.addAttribute("save", model.asMap().get("saveTree").toString());
	                    model.addAttribute("approveReject", model.asMap().get("approveRejectTree").toString());
	                 }

	                // safer check for dniID: if null or empty → create new
	                if (dniID == null || dniID.trim().isEmpty()) {
	                    System.out.println("dniID is empty → creating new form");
	                    model.addAttribute("creationDate", formatter.format(new Timestamp(System.currentTimeMillis())));
	                    model.addAttribute("lastmodifiedDate", formatter.format(new Timestamp(System.currentTimeMillis())));
	                    model.addAttribute("docStatus", "addNew");
	                    model.addAttribute("SelectedIndex", "addNew");
	                    model.addAttribute("dnCount", "addNew");
	                    model.addAttribute("fromNodeJson", fromNodeJson);
	                    model.addAttribute("toNodeJson", toNodeJson);
	                    return "DiscoveryNewApprovalFormView";
	                } else {
	                    System.out.println("Loading existing item with dniID=" + dniID);
	                   /* String[] result = form.NavigationNP(session, "DISCOVERY_NEW_ITEM", "DNI_ID", dniID,
	                            "LAST_MODIFIED_DATE", navAction);*/
	                    
	                    
	                	permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
						        "Discovery New Project Manger", "Tree");
						String readProjectM = ((Integer) model.asMap().get("readTree")).toString();
						model.addAttribute("readProjectM", readProjectM);

						permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
						        "Discovery New Asset Manger", "Tree");
						String readAssetM = ((Integer) model.asMap().get("readTree")).toString();
						model.addAttribute("readAssetM", readAssetM);

						permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
						        "Discovery New Finance Manger", "Tree");
						String readFinanceM = ((Integer) model.asMap().get("readTree")).toString();
						model.addAttribute("readFinanceM", readFinanceM);

						// Build WHERE condition dynamically
						List<String> conditions = new ArrayList<>();
						conditions.add("APPROVAL_STATUS = 'q '");
						if (!"0".equals(readProjectM)) {
						    conditions.add("(APPROVAL = 'Project Manager' AND APPROVAL_STATUS = '-- Select Option --') 	OR (APPROVAL = 'Operation Manager' AND APPROVAL_STATUS = '-- Select Option --') ");
						}
						if (!"0".equals(readAssetM)) {
						    conditions.add("(APPROVAL = 'Asset Unit' AND APPROVAL_STATUS = '-- Select Option --') OR (APPROVAL = 'Project Manager' AND APPROVAL_STATUS = 'Approved')");
						}
						
						if (!"0".equals(readFinanceM)) {
						    conditions.add("(APPROVAL = 'Finance' AND APPROVAL_STATUS = '-- Select Option --') OR (APPROVAL = 'Asset Unit' AND APPROVAL_STATUS = 'Approved')");
						}

						// Combine conditions with OR
						String whereClause = String.join(" OR ", conditions);

						String dynamicQuery =
							    "SELECT " +
							    "  DNI_ID, " +            
							    "  TRANS_ID, " +
							    "  TRANS_TYPE, " +
							    "  CASE " +
							    "    WHEN APPROVAL = 'Asset Unit' AND APPROVAL_STATUS = 'Approved' THEN 'Finance' " +
							    "    WHEN APPROVAL = 'Project Manager' AND APPROVAL_STATUS = 'Approved' THEN 'Asset Unit' " +
							    "    ELSE APPROVAL " +
							    "  END AS approval, " +
							    "  FROM_SITE, " +
							    "  TO_SITE, " +
							    "  PO_ID, " +
							    "  LAST_MODIFIED_DATE " +
							    "FROM DISCOVERY_NEW_ITEM " +
							    "WHERE " + whereClause;

	                   
	                    
	                    String[] result = form.customNavigation(session, dynamicQuery, "DNI_ID", dniID, "LAST_MODIFIED_DATE", navAction);
	                    int SelectedIndex = Integer.parseInt(result[1]);
	                    dniID = result[2];
	                    model.addAttribute("SelectedIndex", SelectedIndex);
	                    model.addAttribute("dnCount", Integer.parseInt(result[0]));

	                    DNi = (DiscoveryNewItem) session.get(DiscoveryNewItem.class, dniID);
	                    if (DNi != null) {
	                    	
	                    	String app = DNi.getDniAPPROVAL();
		                	String appStatus = DNi.getApprovalStatus();

		                	if((app.equals("Project Manager") & appStatus.equals("-- Select Option --")) || 
		                			(app.equals("Operation Manager") & appStatus.equals("-- Select Option --"))
		                			|| (app.equals("Asset Unit") & appStatus.equals("-- Select Option --"))
		                			|| (app.equals("Finance") & appStatus.equals("-- Select Option --"))) {
		                		
		                		status= app;
		                	
		                	}
		                	
		                	else if((app.equals("Project Manager") & appStatus.equals("Approved"))) {
		                		
		                		status= "Asset Unit";
		                		
		                	}
	                          
		                	else if((app.equals("Asset Unit") & appStatus.equals("Approved"))) {
		                		
		                		status= "Finance";
		                		
		                	}
		                	System.out.println("zeee");
		                	System.out.println(status);
		                	  if ("Project Manager".equals(status) || "Operation Manager".equals(status)) {
		  	                    permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
		  	                            "Discovery New Project Manger", "Tree");
		  	                    model.addAttribute("read", model.asMap().get("readTree").toString());
		  	                    model.addAttribute("write", model.asMap().get("writeTree").toString());
		  	                    model.addAttribute("add", model.asMap().get("addTree").toString());
		  	                    model.addAttribute("delete", model.asMap().get("delTree").toString());
		  	                    model.addAttribute("save", model.asMap().get("saveTree").toString());
		  	                    model.addAttribute("approveReject", model.asMap().get("approveRejectTree").toString());
		  	                } else if ("Asset Unit".equals(status)) {
		  	                    permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
		  	                            "Discovery New Asset Manger", "Tree");
		  	                    model.addAttribute("read", model.asMap().get("readTree").toString());
		  	                    model.addAttribute("write", model.asMap().get("writeTree").toString());
		  	                    model.addAttribute("add", model.asMap().get("addTree").toString());
		  	                    model.addAttribute("delete", model.asMap().get("delTree").toString());
		  	                    model.addAttribute("save", model.asMap().get("saveTree").toString());
		  	                    model.addAttribute("approveReject", model.asMap().get("approveRejectTree").toString());
		  	              } else if ("Finance".equals(status)) {
		  	                    permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
		  	                            "Discovery New Finance Manger", "Tree");
		  	                    model.addAttribute("read", model.asMap().get("readTree").toString());
		  	                    model.addAttribute("write", model.asMap().get("writeTree").toString());
		  	                    model.addAttribute("add", model.asMap().get("addTree").toString());
		  	                    model.addAttribute("delete", model.asMap().get("delTree").toString());
		  	                    model.addAttribute("save", model.asMap().get("saveTree").toString());
		  	                    model.addAttribute("approveReject", model.asMap().get("approveRejectTree").toString());
		  	                 }
		                	System.out.println(status);
		                	
	                        // add attributes like before
	                        model.addAttribute("ID", DNi.getDniID());
	                        model.addAttribute("transId", DNi.getTransID());
	                        model.addAttribute("creationDate", formatter.format(DNi.getDnicreationDate()));
	                        model.addAttribute("lastmodifiedDate", formatter.format(DNi.getDnilastModifieddate()));
	                        model.addAttribute("itemId", DNi.getDniItemcode());
	                        model.addAttribute("itemName", DNi.getDniItemname());
	                        model.addAttribute("itemModel", DNi.getItemModel());
	                        model.addAttribute("itemPartNb", DNi.getItemPartNb());
	                        model.addAttribute("transType", DNi.getTransType());
	                        model.addAttribute("description", DNi.getDescription());
	                        model.addAttribute("elementName", DNi.getElementName());
	                   
	                        model.addAttribute("PO", DNi.getDniPOID() + ":" + DNi.getSupplierID() + ":" + DNi.getSupplierName() + ":" + DNi.getTotalAmount());
	                        model.addAttribute("WO", DNi.getDniWOID());
	                        model.addAttribute("fromSlot", DNi.getFromSlot());
	                        model.addAttribute("toSlot", DNi.getToSlot());
	                        model.addAttribute("fromSite", DNi.getWareID() + ":" + DNi.getWareName() + ":" + DNi.getDniSIte());
	                        model.addAttribute("toSite", DNi.getToWareId() + ":" + DNi.getToWareName() + ":" + DNi.getToSite());
	                        model.addAttribute("farId", DNi.getFarID());
	                        model.addAttribute("macAddress", DNi.getMacAddress());
	                        model.addAttribute("qty", DNi.getDniQty());
	                        model.addAttribute("rate", DNi.getDniRate());
	                        model.addAttribute("discountAmount", DNi.getDniDiscamount());
	                        model.addAttribute("tax", DNi.getDniTax1());
	                        model.addAttribute("netRate", DNi.getDniNetrate());
	                        model.addAttribute("totalAt", DNi.getDniTotalat());
	                        model.addAttribute("oldSerialNb", DNi.getDniSN());
	                        model.addAttribute("toSerialNb", DNi.getToSerialNumber());
	                        model.addAttribute("oldAddress", DNi.getOldMacAddress());
	                        model.addAttribute("dnId", DNi.getDniDNID());
	                        model.addAttribute("status", status);
	                        // Load toNodeJson
	                        Query toQuery = session.createNativeQuery(
	                                "SELECT JSON_OBJECT(" +
	                                        "'toNodeArray' VALUE JSON_ARRAYAGG(JSON_OBJECT(" +
	                                        "'NodeId' VALUE TO_NODE_ID, 'NodeName' VALUE TO_NODE_NAME, 'NodeType' VALUE TO_NODE_TYPE))) " +
	                                        "FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID = :param1 AND TO_NODE_ID IS NOT NULL")
	                                .setParameter("param1", DNi.getDniID());
	                        Object toResult = toQuery.getSingleResult();
	                        if (toResult != null) {
	                            toNodeJson = toResult.toString();
	                        }

	                        // Load fromNodeJson
	                        Query fromQuery = session.createNativeQuery(
	                                "SELECT JSON_OBJECT(" +
	                                        "'fromNodeArray' VALUE JSON_ARRAYAGG(JSON_OBJECT(" +
	                                        "'NodeId' VALUE FROM_NODE_ID, 'NodeName' VALUE FROM_NODE_NAME, 'NodeType' VALUE FROM_NODE_TYPE))) " +
	                                        "FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID = :param1 AND FROM_NODE_ID IS NOT NULL")
	                                .setParameter("param1", DNi.getDniID());
	                        Object fromResult = fromQuery.getSingleResult();
	                        if (fromResult != null) {
	                            fromNodeJson = fromResult.toString();
	                        }

	                        model.addAttribute("fromNodeJson", fromNodeJson);
	                        model.addAttribute("toNodeJson", toNodeJson);
	                        System.out.println("TO JSON: " + toNodeJson);
	                        System.out.println("FROM JSON: " + fromNodeJson);
	                    }
	                }
	            } catch (Exception e) {
	                e.printStackTrace(); // to see the error
	                return "DiscoveryNewApprovalFormView";
	            } finally {
	                if (session != null && session.isOpen()) {
	                    tx.commit();
	                    session.close();
	                }
	            }
	        }
	        return "DiscoveryNewApprovalFormView";
	    }
	}

	
	
	
	@RequestMapping(value = "/DiscoveryNewTreeView", method = RequestMethod.GET)
	public String DiscoveryNewTreeView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		
		Session session = AlmDbSession.getInstance().getSession();
		
		if (session != null && session.isOpen()) {
			notifications.headerNotifications(session, model);
			
			try {
		
				permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
						"Discovery New Project Manger", "Tree");

				String readProjectM = ((Integer) model.asMap().get("readTree")).toString();
				String writeProjectM = ((Integer) model.asMap().get("writeTree")).toString();
				String addProjectM = ((Integer) model.asMap().get("addTree")).toString();
				String deleteProjectM = ((Integer) model.asMap().get("delTree")).toString();
				String saveProjectM = ((Integer) model.asMap().get("saveTree")).toString();
				String approveRejectProjectM = ((Integer) model.asMap().get("approveRejectTree")).toString();
				model.addAttribute("readProjectM", readProjectM);
				model.addAttribute("writeProjectM", writeProjectM);
				model.addAttribute("addProjectM", addProjectM);
				model.addAttribute("deleteProjectM", deleteProjectM);
				model.addAttribute("saveProjectM", saveProjectM);
				model.addAttribute("approveRejectProjectM", approveRejectProjectM);
				permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
						"Discovery New Asset Manger", "Tree");
				String readAssetM = ((Integer) model.asMap().get("readTree")).toString();
				String writeAssetM = ((Integer) model.asMap().get("writeTree")).toString();
				String addAssetM = ((Integer) model.asMap().get("addTree")).toString();
				String deleteAssetM = ((Integer) model.asMap().get("delTree")).toString();
				String saveAssetM = ((Integer) model.asMap().get("saveTree")).toString();
				String approveRejectAssetM = ((Integer) model.asMap().get("approveRejectTree")).toString();
				model.addAttribute("readAssetM", readAssetM);
				model.addAttribute("writeAssetM", writeAssetM);
				model.addAttribute("addAssetM", addAssetM);
				model.addAttribute("deleteAssetM", deleteAssetM);
				model.addAttribute("saveAssetM", saveAssetM);
				model.addAttribute("approveRejectAssetM", approveRejectAssetM);
				
				permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
						"Discovery New Finance Manger", "Tree");
				String readFinanceM = ((Integer) model.asMap().get("readTree")).toString();
				String writeFinanceM = ((Integer) model.asMap().get("writeTree")).toString();
				String addFinanceM = ((Integer) model.asMap().get("addTree")).toString();
				String deleteFinanceM = ((Integer) model.asMap().get("delTree")).toString();
				String saveFinanceM = ((Integer) model.asMap().get("saveTree")).toString();
				String approveRejectFinanceM = ((Integer) model.asMap().get("approveRejectTree")).toString();
				model.addAttribute("readFinanceM", readFinanceM);
				model.addAttribute("writeFinanceM", writeFinanceM);
				model.addAttribute("addFinanceM", addFinanceM);
				model.addAttribute("deleteFinanceM", deleteFinanceM);
				model.addAttribute("saveFinanceM", saveFinanceM);
				model.addAttribute("approveRejectFinanceM", approveRejectFinanceM);
				ObjectMapper mapper = new ObjectMapper();
				LinkedHashMap<String, List<?>> discoveryNewList = new LinkedHashMap<>();
				List<Object[]> ProjectMangerList = new ArrayList<Object[]>();
				if ("1".equals(readProjectM)) {
					
					
					ProjectMangerList = session.createNativeQuery(
						    "SELECT t.DNI_ID AS dniID, t.TRANS_ID AS transID, t.ITEM_CODE AS dniItemcode, t.ITEM_NAME AS dniItemname, t.TRANS_TYPE AS transType, " +
						    "t.ELEMENT_NAME AS elementName, NVL(t.NOTES, ' ') AS notes, NVL(t.POSITION, ' ') AS position, t.APPROVAL AS dniAPPROVAL, " +
						    "t.PO_ID AS dniPOID, TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM') AS dnicreationDate, TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM') AS dnilastModifiedDate, " +
						    "t.SUPPLIER_ID AS supplierID, t.SUPPLIER_NAME AS supplierName, t.TOTAL_AMOUNT AS totalAmount, " +
						    "t.WO_ID AS dniWOID, t.WO_PURPOSE AS purpose, t.QTY AS dniQty, t.RATE AS dniRate, t.DISCOUNT_AMOUNT AS dniDiscamount, " +
						    "t.TAX1 AS dniTax1, t.NET_RATE AS dniNetrate, t.TOTAL AS dniTotal, t.TOTAL_AT AS dniTotalat, t.FROM_SITE AS dniSIte, " +
						    "t.WARE_ID AS wareID, t.WARE_NAME AS wareName, NVL(t.FROM_SERIAL_NUMBER, ' ') AS dniSN, t.DNI_ID AS dniDNID, " +
						    "NVL(t.ITEM_MODEL, '') AS itemModel, NVL(t.ITEM_PART_NUMBER, '') AS itemPartNb, " +
						    "t.APPROVAL_STATUS AS approvalStatus, NVL(t.FROM_SLOT, ' ') AS fromSlot, NVL(t.FAR_ID, ' ') AS farID, NVL(t.MAC_ADDRESS, ' ') AS macAddress, " +
						    "NVL(t.TO_SLOT, ' ') AS toSlot, t.TO_SITE AS toSite, t.TO_WARE_NAME AS toWareName, " +
						    "t.TO_WARE_ID AS toWareId, NVL(t.ALCFLG, ' ') AS alcFlg, " +
						    "NVL(t.TO_SERIAL_NUMBER, ' ') AS toSerialNumber, NVL(t.DESCRIPTION, ' ') AS description, x.toNodeArray, y.fromNodeArray " +
						    "FROM DISCOVERY_NEW_ITEM t " +
						    "LEFT JOIN (SELECT DISTINCT DNI_ID, json_object('toNodeArray' VALUE json_arrayagg(json_object('NodeId' VALUE TO_NODE_ID, " +
						    "'NodeName' VALUE TO_NODE_NAME, 'NodeType' VALUE TO_NODE_TYPE))) AS toNodeArray FROM DISCOVERY_NEW_ITEM_NODE WHERE TO_NODE_ID IS NOT NULL GROUP BY DNI_ID) x " +
						    "ON x.DNI_ID = t.DNI_ID " +
						    "LEFT JOIN (SELECT DISTINCT DNI_ID, json_object('fromNodeArray' VALUE json_arrayagg(json_object('NodeId' VALUE FROM_NODE_ID, " +
						    "'NodeName' VALUE FROM_NODE_NAME, 'NodeType' VALUE FROM_NODE_TYPE))) AS fromNodeArray FROM DISCOVERY_NEW_ITEM_NODE WHERE FROM_NODE_ID IS NOT NULL GROUP BY DNI_ID) y " +
						    "ON y.DNI_ID = t.DNI_ID " +
						    "WHERE ((t.APPROVAL = 'Project Manager' OR t.APPROVAL = 'Operation Manager') AND t.APPROVAL_STATUS = '-- Select Option --')"
						).getResultList();

				}
			        
					List<Object[]> AssetMangerList = new ArrayList<Object[]>();
					if ("1".equals(readAssetM)) {
						
						
						AssetMangerList = session.createNativeQuery(
							    "SELECT t.DNI_ID AS dniID, t.TRANS_ID AS transID, t.ITEM_CODE AS dniItemcode, t.ITEM_NAME AS dniItemname, t.TRANS_TYPE AS transType, " +
							    "t.ELEMENT_NAME AS elementName, NVL(t.NOTES, ' ') AS notes, NVL(t.POSITION, ' ') AS position, t.APPROVAL AS dniAPPROVAL, " +
							    "t.PO_ID AS dniPOID, TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM') AS dnicreationDate, TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM') AS dnilastModifiedDate, " +
							    "t.SUPPLIER_ID AS supplierID, t.SUPPLIER_NAME AS supplierName, t.TOTAL_AMOUNT AS totalAmount, " +
							    "t.WO_ID AS dniWOID, t.WO_PURPOSE AS purpose, t.QTY AS dniQty, t.RATE AS dniRate, t.DISCOUNT_AMOUNT AS dniDiscamount, " +
							    "t.TAX1 AS dniTax1, t.NET_RATE AS dniNetrate, t.TOTAL AS dniTotal, t.TOTAL_AT AS dniTotalat, t.FROM_SITE AS dniSIte, " +
							    "t.WARE_ID AS wareID, t.WARE_NAME AS wareName, NVL(t.FROM_SERIAL_NUMBER, ' ') AS dniSN, t.DNI_ID AS dniDNID, " +
							    "NVL(t.ITEM_MODEL, '') AS itemModel, NVL(t.ITEM_PART_NUMBER, '') AS itemPartNb, " +
							    "t.APPROVAL_STATUS AS approvalStatus, NVL(t.FROM_SLOT, ' ') AS fromSlot, NVL(t.FAR_ID, ' ') AS farID, NVL(t.MAC_ADDRESS, ' ') AS macAddress, " +
							    "NVL(t.TO_SLOT, ' ') AS toSlot, t.TO_SITE AS toSite, t.TO_WARE_NAME AS toWareName, " +
							    "t.TO_WARE_ID AS toWareId, NVL(t.ALCFLG, ' ') AS alcFlg, " +
							    "NVL(t.TO_SERIAL_NUMBER, ' ') AS toSerialNumber, NVL(t.DESCRIPTION, ' ') AS description, x.toNodeArray, y.fromNodeArray " +
							    "FROM DISCOVERY_NEW_ITEM t " +
							    "LEFT JOIN (SELECT DISTINCT DNI_ID, json_object('toNodeArray' VALUE json_arrayagg(json_object('NodeId' VALUE TO_NODE_ID, " +
							    "'NodeName' VALUE TO_NODE_NAME, 'NodeType' VALUE TO_NODE_TYPE))) AS toNodeArray FROM DISCOVERY_NEW_ITEM_NODE WHERE TO_NODE_ID IS NOT NULL GROUP BY DNI_ID) x " +
							    "ON x.DNI_ID = t.DNI_ID " +
							    "LEFT JOIN (SELECT DISTINCT DNI_ID, json_object('fromNodeArray' VALUE json_arrayagg(json_object('NodeId' VALUE FROM_NODE_ID, " +
							    "'NodeName' VALUE FROM_NODE_NAME, 'NodeType' VALUE FROM_NODE_TYPE))) AS fromNodeArray FROM DISCOVERY_NEW_ITEM_NODE WHERE FROM_NODE_ID IS NOT NULL GROUP BY DNI_ID) y " +
							    "ON y.DNI_ID = t.DNI_ID " +
							    "WHERE (   (t.APPROVAL = 'Asset Unit' AND t.APPROVAL_STATUS = '-- Select Option --') OR "
							    + "(t.APPROVAL = 'Project Manager' AND t.APPROVAL_STATUS = 'Approved'))"
						).getResultList();

						       
					}
					
				       
									List<Object[]> FinanceMangerList = new ArrayList<Object[]>();
									if ("1".equals(readFinanceM)) {
										
										
										FinanceMangerList = session.createNativeQuery(
											    "SELECT t.DNI_ID AS dniID, t.TRANS_ID AS transID, t.ITEM_CODE AS dniItemcode, t.ITEM_NAME AS dniItemname, t.TRANS_TYPE AS transType, " +
											    "t.ELEMENT_NAME AS elementName, NVL(t.NOTES, ' ') AS notes, NVL(t.POSITION, ' ') AS position, t.APPROVAL AS dniAPPROVAL, " +
											    "t.PO_ID AS dniPOID, TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM') AS dnicreationDate, TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM') AS dnilastModifiedDate, " +
											    "t.SUPPLIER_ID AS supplierID, t.SUPPLIER_NAME AS supplierName, t.TOTAL_AMOUNT AS totalAmount, " +
											    "t.WO_ID AS dniWOID, t.WO_PURPOSE AS purpose, t.QTY AS dniQty, t.RATE AS dniRate, t.DISCOUNT_AMOUNT AS dniDiscamount, " +
											    "t.TAX1 AS dniTax1, t.NET_RATE AS dniNetrate, t.TOTAL AS dniTotal, t.TOTAL_AT AS dniTotalat, t.FROM_SITE AS dniSIte, " +
											    "t.WARE_ID AS wareID, t.WARE_NAME AS wareName, NVL(t.FROM_SERIAL_NUMBER, ' ') AS dniSN, t.DNI_ID AS dniDNID, " +
											    "NVL(t.ITEM_MODEL, '') AS itemModel, NVL(t.ITEM_PART_NUMBER, '') AS itemPartNb, " +
											    "t.APPROVAL_STATUS AS approvalStatus, NVL(t.FROM_SLOT, ' ') AS fromSlot, NVL(t.FAR_ID, ' ') AS farID, NVL(t.MAC_ADDRESS, ' ') AS macAddress, " +
											    "NVL(t.TO_SLOT, ' ') AS toSlot, t.TO_SITE AS toSite, t.TO_WARE_NAME AS toWareName, " +
											    "t.TO_WARE_ID AS toWareId, NVL(t.ALCFLG, ' ') AS alcFlg, " +
											    "NVL(t.TO_SERIAL_NUMBER, ' ') AS toSerialNumber, NVL(t.DESCRIPTION, ' ') AS description, x.toNodeArray, y.fromNodeArray " +
											    "FROM DISCOVERY_NEW_ITEM t " +
											    "LEFT JOIN (SELECT DISTINCT DNI_ID, json_object('toNodeArray' VALUE json_arrayagg(json_object('NodeId' VALUE TO_NODE_ID, " +
											    "'NodeName' VALUE TO_NODE_NAME, 'NodeType' VALUE TO_NODE_TYPE))) AS toNodeArray FROM DISCOVERY_NEW_ITEM_NODE WHERE TO_NODE_ID IS NOT NULL GROUP BY DNI_ID) x " +
											    "ON x.DNI_ID = t.DNI_ID " +
											    "LEFT JOIN (SELECT DISTINCT DNI_ID, json_object('fromNodeArray' VALUE json_arrayagg(json_object('NodeId' VALUE FROM_NODE_ID, " +
											    "'NodeName' VALUE FROM_NODE_NAME, 'NodeType' VALUE FROM_NODE_TYPE))) AS fromNodeArray FROM DISCOVERY_NEW_ITEM_NODE WHERE FROM_NODE_ID IS NOT NULL GROUP BY DNI_ID) y " +
											    "ON y.DNI_ID = t.DNI_ID " +
											    "WHERE (   (t.APPROVAL = 'Finance' AND t.APPROVAL_STATUS = '-- Select Option --') OR "
											    + "(t.APPROVAL = 'Asset Unit' AND t.APPROVAL_STATUS = 'Approved'))"
											).getResultList();

										       
									}
		
					
			            // Store the results in a LinkedHashMap
			                discoveryNewList.put("projectManager", ProjectMangerList);
			           	    
			           	    discoveryNewList.put("assetManager", AssetMangerList);
			           	   
			           	    discoveryNewList.put("financeManager", FinanceMangerList);
			           	    model.addAttribute("discoveryNewList", mapper.writeValueAsString(discoveryNewList));
						System.out.println(mapper.writeValueAsString(discoveryNewList));
				
				
				
		
		} catch (Exception e) {
			logger.info("Error at DiscoveryNew ListView with a message: "+ e);
			e.printStackTrace();
			model.addAttribute("ListGridTable","");
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
			
		}

		return "DiscoveryNewTreeView";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveFormTreeDN", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> SaveFormTreeDN(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
rtn.put("done", 1);
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		} 
	   
	     session = AlmDbSession.getInstance().getSession();
	     tx = session.beginTransaction();
	    if (session != null && session.isOpen()) {
	        notifications.headerNotifications(session, model);

	        
	        	 try {
	        	
	        		    String itmcode = request.getParameter("itemId"); // item ID
	        		    String itmname = request.getParameter("itemName"); // item name
	        		    String itemModel = request.getParameter("DitemModel"); // item model
	        		    String itemPartNb = request.getParameter("DitemPartNumber"); // part number
	        		    String transType = request.getParameter("popupTransType"); // transaction type
	        		    String Description = request.getParameter("description"); // description
	        		    String elementName = request.getParameter("popupElementName"); // element name
	        		    String MacAddress = request.getParameter("address"); // MAC address
	        		    String PurchaseOrder = request.getParameter("PoId"); // PO ID
	        		    String woId = request.getParameter("WoId"); // WO ID
	        		    String address=request.getParameter("address");
	        		    String fromSlot = request.getParameter("fromSlotId"); // from slot
	        		    String toSlot = request.getParameter("toSlotId"); // to slot
	        		    String SiteID = request.getParameter("fromSiteId"); // from site
	        		    String wareID = "";
	        		    String wareName = "";
	        
	        		    if (SiteID != null && SiteID.contains(":")) {
	        		        String[] parts = SiteID.split(":");
	        		        if (parts.length == 3) {
	        		            wareID = parts[0];      // WARE_ID
	        		            wareName = parts[1];    // WARE_NAME
	        		            SiteID = parts[2];      // FROM_SITE
	        		        }
	        		    }

	        		    String toSiteID = request.getParameter("toSiteId"); // to site
	        		    String toWareID = "";
	        		    String toWareName = "";
	        		    if (toSiteID != null && toSiteID.contains(":")) {
	        		       String[] parts = toSiteID.split(":");
	        		        if (parts.length == 3) {
	        		        	toWareID = parts[0];      // 
	        		        	toWareName = parts[1];    // 
	        		        	toSiteID = parts[2];      // 
	        		        }
	        		    }
	        		    String FAR = request.getParameter("farId"); // FAR ID
	        		    String dniQty = request.getParameter("Qty"); // quantity
	        		    float dniRate = Float.parseFloat(request.getParameter("rate"));
	        		    String dniDiscamount = request.getParameter("discountAmount"); // discount amount
	        		    String dniTax1 = request.getParameter("tax"); // tax
	        		    String dniNetrate = request.getParameter("netRate"); // net rate
	        		    String dniTotalat = request.getParameter("totalAt"); // total currency
	        		    String dniSN = request.getParameter("oldSerialNum"); // from serial number
	        		    String toSerialNumber = request.getParameter("serialNum"); // to serial number
	        		    String transId = request.getParameter("transId"); // transaction ID
	        		    String DniID = request.getParameter("DnItemId"); // DNI ID
	        		    String slctFromNodeDel= request.getParameter("slctFromNodeDel");
	        		    String slctToNodeDel= request.getParameter("slctToNodeDel");
	        		    String DnItemNotes = request.getParameter("DnItemNotes");
	        		    String toNodes = request.getParameter("toNodes");
	        		    String fromNodes = request.getParameter("fromNodes");
	        		    String supplierID = "";
	        			String supplierName = "";
	        			String PurchaseOrId = "";
	        			float TotalAmount = 0;
	        			int n;
	        			String m, g;
	        			int part2, part3;
	        			if (!PurchaseOrder.isEmpty()) {
	        				n = PurchaseOrder.indexOf(":");
	        				PurchaseOrId = PurchaseOrder.substring(0, n);
	        				
	        				m = PurchaseOrder.substring(n + 1, PurchaseOrder.length());
	        				part2 = m.indexOf(":");
	        				supplierID = m.substring(0, part2);
	        				
	        				g = m.substring(part2 + 1, m.length());
	        				part3 = g.indexOf(":");
	        				supplierName = g.substring(0, part3);
	        				
	        				String Amount = g.substring(part3 + 1, g.length());
	        				TotalAmount = Float.valueOf(Amount);
	        			}
	        			
	        			String action= request.getParameter("action");
        				if (action.equals("Actions")) {
	        			
	        				action = "-- Select Option --";
	        			}
	        			
	        	
                 String getApproval= request.getParameter("getApproval");
	        		    query = session.createNativeQuery(
	        		    	    "UPDATE DISCOVERY_NEW_ITEM SET ITEM_CODE = '" + itmcode + "', LAST_MODIFIED_DATE = sysdate, ITEM_NAME = '" + itmname + 
	        		    	    "', RATE = '" + dniRate + "', DISCOUNT_AMOUNT = '" + dniDiscamount + "', NET_RATE = '" + dniNetrate + 
	        		    	    "', TAX1 = '" + dniTax1 + "', TOTAL_AT = '" + dniTotalat + "', WARE_ID = '" + wareID + 
	        		    	    "', WARE_NAME = '" + wareName + "', FROM_SITE = '" + SiteID + "', TO_WARE_ID = '" + toWareID + 
	        		    	    "', TO_WARE_NAME = '" + toWareName + "', TO_SITE = '" + toSiteID + "', QTY = '" + dniQty + 
	        		    	    "', FROM_SERIAL_NUMBER = '" + dniSN + "', TO_SERIAL_NUMBER = '" + toSerialNumber + 
	        		    	    "', PO_ID = '" + PurchaseOrId + "', WO_ID = '" + woId + 
	        		    	    "', ITEM_MODEL = '" + itemModel + "', ITEM_PART_NUMBER = '" + itemPartNb + "', Far_ID = '" + FAR + 
	        		    	    "', MAC_ADDRESS = '" + MacAddress + "', FROM_SLOT = '" + fromSlot + "', TRANS_ID = '" + transId+ "', TO_SLOT = '" + toSlot + 
	        		    	    "', TRANS_TYPE = '" + transType + "', NOTES = '" + DnItemNotes + 
	        		    	    "', ELEMENT_NAME = '" + elementName + "', SUPPLIER_Name = '" + supplierName+ "', SUPPLIER_ID = '" + supplierID+ "', APPROVAL = '" + getApproval + "', APPROVAL_STATUS = '" + action+ "', DESCRIPTION = '" + Description + 
	        		    	    "' WHERE DNI_ID = :param1"
	        		    	);

	        		    	query.setParameter("param1", DniID).executeUpdate();
	        		    	
	        		    	if (slctToNodeDel != null && !slctToNodeDel.equals("[]")) {
	        		    	    // Convert the string back into an array
	        		    	    slctToNodeDel = slctToNodeDel.replace("[", "").replace("]", "").replace("\"", "");
	        		    	    String[] toNodeIds = slctToNodeDel.split(",");

	        		    	    for (String nodeId : toNodeIds) {
	        		    	        nodeId = nodeId.trim(); // Clean whitespace just in case

	        		    	        query = session.createNativeQuery(
	        		    	            "DELETE FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID = :param1 AND TO_NODE_ID = :param2"
	        		    	        );
	        		    	        query.setParameter("param1", DniID);
	        		    	        query.setParameter("param2", nodeId);
	        		    	        query.executeUpdate();
	        		    	    }
	        		    	}
	        		    	    
	        		    	   	
		        		    	if (slctFromNodeDel != null && !slctFromNodeDel.equals("[]")) {
		        		    	    // Convert the string back into an array
		        		    	    slctToNodeDel = slctToNodeDel.replace("[", "").replace("]", "").replace("\"", "");
		        		    	    String[] fromNodeIds = slctToNodeDel.split(",");

		        		    	    for (String nodeId : fromNodeIds) {
		        		    	        nodeId = nodeId.trim(); // Clean whitespace just in case

		        		    	        query = session.createNativeQuery(
		        		    	            "DELETE FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID = :param1 AND From_NODE_ID = :param2"
		        		    	        );
		        		    	        query.setParameter("param1", DniID);
		        		    	        query.setParameter("param2", nodeId);
		        		    	        query.executeUpdate();
		        		    	    }
		        		    	}
		        		    	  JSONParser parser = new JSONParser();
		        		    	if (toNodes != null && !toNodes.equals("[]")) {
		        		    	  
		        		    	    JSONArray toNodeArray = (JSONArray) parser.parse(toNodes);

		        		    	    for (Object obj : toNodeArray) {
		        		    	        JSONArray arr = (JSONArray) obj;
		        		    	        if (arr.size() < 3) continue;

		        		    	        String toNodeId = arr.get(0).toString();

		        		    	        // Check if TO_NODE_ID already exists for this DNI_ID
		        		    	        Long count = ((Number) session.createNativeQuery(
		        		    	            "SELECT COUNT(*) FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID = :dniId AND TO_NODE_ID = :toNodeId"
		        		    	        )
		        		    	        .setParameter("dniId", DniID)
		        		    	        .setParameter("toNodeId", toNodeId)
		        		    	        .uniqueResult()).longValue();

		        		    	        if (count == 0) {
		        		    	            // Only create if not found
		        		    	            String dnNodeID;
		        		    	            synchronized (this) {
		        		    	                Object seqResult = session.createNativeQuery("SELECT DNI_NODE FROM SEQ_TABLE").uniqueResult();
		        		    	                dnNodeID = "DNI_NODE_" + year + "_" + Integer.parseInt(seqResult.toString());
		        		    	                session.createNativeQuery("UPDATE SEQ_TABLE SET DNI_NODE = DNI_NODE + 1").executeUpdate();
		        		    	                session.createNativeQuery("commit").executeUpdate();
		        		    	            }

		        		    	            DiscoveryNewItemNode dniNode = new DiscoveryNewItemNode();
		        		    	            dniNode.setDniNode(dnNodeID);
		        		    	            dniNode.setCreationDate(new Timestamp(System.currentTimeMillis()));
		        		    	            dniNode.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
		        		    	            dniNode.setFromNodeId(" ");
		        		    	            dniNode.setToNodeId(toNodeId);
		        		    	            dniNode.setFromNodeName(" ");
		        		    	            dniNode.setToNodeName(arr.get(1).toString());
		        		    	            dniNode.setFromNodeType(" ");
		        		    	            dniNode.setToNodeType(arr.get(2).toString());
		        		    	            dniNode.setDniId(DniID);

		        		    	            session.saveOrUpdate(dniNode);
		        		    	        }
		        		    	    }
		        		    	}


	        		    	    // Handle fromNodes
		        		    	if (fromNodes != null && !fromNodes.equals("[]")) {
		        		    	    JSONArray fromNodeArray = (JSONArray) parser.parse(fromNodes);

		        		    	    for (Object obj : fromNodeArray) {
		        		    	        JSONArray arr = (JSONArray) obj;
		        		    	        if (arr.size() < 3) continue;

		        		    	        String fromNodeId = arr.get(0).toString();

		        		    	        // Check if FROM_NODE_ID already exists for this DNI_ID
		        		    	        Long count = ((Number) session.createNativeQuery(
		        		    	            "SELECT COUNT(*) FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID = :dniId AND FROM_NODE_ID = :fromNodeId"
		        		    	        )
		        		    	        .setParameter("dniId", DniID)
		        		    	        .setParameter("fromNodeId", fromNodeId)
		        		    	        .uniqueResult()).longValue();

		        		    	        if (count == 0) {
		        		    	            String dnNodeID;
		        		    	            synchronized (this) {
		        		    	                Object seqResult = session.createNativeQuery("SELECT DNI_NODE FROM SEQ_TABLE").uniqueResult();
		        		    	                dnNodeID = "DNI_NODE_" + year + "_" + Integer.parseInt(seqResult.toString());
		        		    	                session.createNativeQuery("UPDATE SEQ_TABLE SET DNI_NODE = DNI_NODE + 1").executeUpdate();
		        		    	                session.createNativeQuery("commit").executeUpdate();
		        		    	            }

		        		    	            DiscoveryNewItemNode dniNode = new DiscoveryNewItemNode();
		        		    	            dniNode.setDniNode(dnNodeID);
		        		    	            dniNode.setCreationDate(new Timestamp(System.currentTimeMillis()));
		        		    	            dniNode.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
		        		    	            dniNode.setFromNodeId(fromNodeId);
		        		    	            dniNode.setToNodeId(" ");
		        		    	            dniNode.setFromNodeName(arr.get(1).toString());
		        		    	            dniNode.setToNodeName(" ");
		        		    	            dniNode.setFromNodeType(arr.get(2).toString());
		        		    	            dniNode.setToNodeType(" ");
		        		    	            dniNode.setDniId(DniID);

		        		    	            session.saveOrUpdate(dniNode);
		        		    	        }
		        		    	    }
		        		    	}

	        		    	


	         		    	query = session.createQuery("select t.arID from AssetRegistry t where t.ardniID = :param1");

	    		    				query.setParameter("param1", DniID);
	    		    				String AssetRegID = (String) query.uniqueResult();
	    		    				
	    		    				String ArCode = "";
	    		    				System.out.println("yea");
	    	        		    	if ((StringUtils.equalsIgnoreCase(getApproval, "Project Manager") && StringUtils.equalsIgnoreCase(action, "Approved")) || ((StringUtils.equalsIgnoreCase(getApproval,"Asset Unit") && StringUtils.equalsIgnoreCase(action, "Approved")))) {
	    	        		    		System.out.println("yea");

	    	        					if(StringUtils.equalsIgnoreCase(transType, "New Node on New Hardware") || StringUtils.equalsIgnoreCase(transType, "New Hardware on Existed Node") || StringUtils. 
	    	        							equalsIgnoreCase(transType, "Replacement") || StringUtils.equalsIgnoreCase(transType, "New Hardware on New Node"))  {
			                             System.out.println("-- PROJECT MANAGER APPROVAL --");
	    	        						System.out.println(transId);
	    	        						Manager.ApprovalProjectandAsset(transType, getApproval, action,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,woId,DniID,toSiteID,supplierID,supplierName,toWareID,toWareName,dniSN,dniRate,itemModel,itemPartNb,toSiteID,toSerialNumber,toSlot,SiteID,fromSlot,SiteID, MacAddress,session,tx);
	    	        							if(transId != null) {
	    	        								System.out.println("yessss tr");
	    	        								Manager.transactionUpdate(transId,transType,getApproval,session,tx);
	    	        							}
	    	        						}
	    	        					}
	    	        		    
	    	        		    	if (StringUtils.equalsIgnoreCase(getApproval, "Finance") &&  StringUtils.equalsIgnoreCase(action, "Approved")) { 

	    	        					if(StringUtils.equalsIgnoreCase(transType, "New Node on New Hardware") || StringUtils.equalsIgnoreCase(transType, "New Hardware on Existed Node") || StringUtils. 
	    	        							equalsIgnoreCase(transType, "Replacement") || StringUtils.equalsIgnoreCase(transType, "New Hardware on New Node"))  {

	    	        						System.out.println("-- FINANCE APPROVAL --");

	    	        							Manager.ApprovalFinance(transType, getApproval, action,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,woId,DniID,toSiteID,supplierID,supplierName,toWareID,toWareName,dniSN,dniRate,itemModel,itemPartNb,toSiteID,toSerialNumber,toSlot,SiteID,fromSlot,SiteID,MacAddress,session,tx);
	    	        							if(transId != null ) {
	    	        								System.out.println("yessss tr");
	    	        								Manager.transactionUpdate(transId,transType,getApproval,session,tx);
	    	        							}
	    	        						}
	    	        					}
	    	        					
	    	        		    	if (StringUtils.equalsIgnoreCase(getApproval, "Operation Manager") && StringUtils.equalsIgnoreCase(action, "Approved")) {
	    	        					
	    	        					
	    	        					if(StringUtils.equalsIgnoreCase(transType, "New Node on Existed Hardware") || StringUtils.equalsIgnoreCase(transType, "Transfer from Slot to Slot") || StringUtils.equalsIgnoreCase(transType, "Transfer from Node to Node") 
	    	        							|| StringUtils.equalsIgnoreCase(transType, "Transfer from Site to Site") || StringUtils.equalsIgnoreCase(transType, "Disappear for Maintenance")
	    	        							|| StringUtils.equalsIgnoreCase(transType, "Disappear for Transfer") || StringUtils.equalsIgnoreCase(transType, "Maintenance") || StringUtils.equalsIgnoreCase(transType, "Retirement"))
	    	        						
	    	        									{	

	    	        						System.out.println("-- OPERATION MANAGER APPROVAL --");
	    	        						
	    	        								Manager.ApprovalOperational(transType, getApproval, action,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,woId,DniID,toSiteID,supplierID,supplierName,toWareID,toWareName,dniSN,
	    	        										dniRate,itemModel,itemPartNb,toSiteID,toSerialNumber,toSlot,SiteID,fromSlot,SiteID, FAR, MacAddress,session,tx);
	    	        								if(transId != null) {
	    	        									Manager.transactionUpdate(transId,transType,getApproval,session,tx);
	    	        								}
		
	    	        									}
	    	        		    	}
	    	        		        List<Object[]> result = session.createNativeQuery(
	    	        		                "SELECT t.DNI_ID AS dniID, t.TRANS_ID AS transID, t.ITEM_CODE AS dniItemcode, t.ITEM_NAME AS dniItemname, t.TRANS_TYPE AS transType, " +
	    	        		                "t.ELEMENT_NAME AS elementName, NVL(t.NOTES, ' ') AS notes, NVL(t.POSITION, ' ') AS position, t.APPROVAL AS dniAPPROVAL, " +
	    	        		                "t.PO_ID AS dniPOID, TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM') AS dnicreationDate, TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM') AS dnilastModifiedDate, " +
	    	        		                "t.SUPPLIER_ID AS supplierID, t.SUPPLIER_NAME AS supplierName, t.TOTAL_AMOUNT AS totalAmount, " +
	    	        		                "t.WO_ID AS dniWOID, t.WO_PURPOSE AS purpose, t.QTY AS dniQty, t.RATE AS dniRate, t.DISCOUNT_AMOUNT AS dniDiscamount, " +
	    	        		                "t.TAX1 AS dniTax1, t.NET_RATE AS dniNetrate, t.TOTAL AS dniTotal, t.TOTAL_AT AS dniTotalat, t.FROM_SITE AS dniSIte, " +
	    	        		                "t.WARE_ID AS wareID, t.WARE_NAME AS wareName, NVL(t.FROM_SERIAL_NUMBER, ' ') AS dniSN, t.DNI_ID AS dniDNID, " +
	    	        		                "NVL(t.ITEM_MODEL, '') AS itemModel, NVL(t.ITEM_PART_NUMBER, '') AS itemPartNb, " +
	    	        		                "t.APPROVAL_STATUS AS approvalStatus, NVL(t.FROM_SLOT, ' ') AS fromSlot, NVL(t.FAR_ID, ' ') AS farID, NVL(t.MAC_ADDRESS, ' ') AS macAddress, " +
	    	        		                "NVL(t.TO_SLOT, ' ') AS toSlot, t.TO_SITE AS toSite, t.TO_WARE_NAME AS toWareName, " +
	    	        		                "t.TO_WARE_ID AS toWareId, NVL(t.ALCFLG, ' ') AS alcFlg, " +
	    	        		                "NVL(t.TO_SERIAL_NUMBER, ' ') AS toSerialNumber, NVL(t.DESCRIPTION, ' ') AS description, x.toNodeArray, y.fromNodeArray " +
	    	        		                "FROM DISCOVERY_NEW_ITEM t " +
	    	        		                "LEFT JOIN (SELECT DISTINCT DNI_ID, json_object('toNodeArray' VALUE json_arrayagg(json_object('NodeId' VALUE TO_NODE_ID, " +
	    	        		                "'NodeName' VALUE TO_NODE_NAME, 'NodeType' VALUE TO_NODE_TYPE))) AS toNodeArray FROM DISCOVERY_NEW_ITEM_NODE WHERE TO_NODE_ID IS NOT NULL GROUP BY DNI_ID) x " +
	    	        		                "ON x.DNI_ID = t.DNI_ID " +
	    	        		                "LEFT JOIN (SELECT DISTINCT DNI_ID, json_object('fromNodeArray' VALUE json_arrayagg(json_object('NodeId' VALUE FROM_NODE_ID, " +
	    	        		                "'NodeName' VALUE FROM_NODE_NAME, 'NodeType' VALUE FROM_NODE_TYPE))) AS fromNodeArray FROM DISCOVERY_NEW_ITEM_NODE WHERE FROM_NODE_ID IS NOT NULL GROUP BY DNI_ID) y " +
	    	        		                "ON y.DNI_ID = t.DNI_ID " +
	    	        		                "WHERE ( t.DNI_ID = :dniId )"
	    	        		            ).setParameter("dniId", DniID)
	    	        		             .getResultList();

	    	        		            tx.commit();

	    	        		      List<Object> savedItem = result.isEmpty() ? new ArrayList<>() : Arrays.asList(result.get(0));
	    	        		      rtn.put("savedItem", savedItem);
	    	        		      rtn.put("readProjectM", request.getParameter("readProjectM"));
	    	  					  rtn.put("readAssetM", request.getParameter("readAssetM"));
	    	        		      rtn.put("readFinanceM", request.getParameter("readFinanceM"));
	    	        		      rtn.put("writeProjectM", request.getParameter("writeProjectM"));
	    		    	  		  rtn.put("writeAssetM", request.getParameter("writeAssetM"));
	    		    	  		  rtn.put("writeFinanceM", request.getParameter("writeFinanceM"));
	    		    	  		  rtn.put("saveProjectM", request.getParameter("saveProjectM"));
	    		    	  		  rtn.put("saveAssetM", request.getParameter("saveAssetM"));
	    		    	  		  rtn.put("saveFinanceM", request.getParameter("saveFinanceM"));
	    		    	  		  rtn.put("approveRejectProjectM", request.getParameter("approveRejectProjectM"));
	    		    	  		  rtn.put("approveRejectAssetM", request.getParameter("approveRejectAssetM"));
	    		    	  		  rtn.put("approveRejectFinanceM", request.getParameter("approveRejectFinanceM"));
	    		    	  		  rtn.put("DniID", DniID);
	    	  		  
	    	  		
	    	  		
	                 return rtn;
	             } catch (Exception e) {
	                 e.printStackTrace();
	                 return rtn;
	             }
	    }
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveFormViewDN", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> SaveFormViewDN(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
rtn.put("done", 1);
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		} 
	   
	     session = AlmDbSession.getInstance().getSession();
	     tx = session.beginTransaction();
	    if (session != null && session.isOpen()) {
	        notifications.headerNotifications(session, model);

	        
	        	 try {
	        		 
	        		    String itmcode = request.getParameter("itemId"); // item ID
	        		    String itmname = request.getParameter("itemName"); // item name
	        		    String itemModel = request.getParameter("DitemModel"); // item model
	        		    String itemPartNb = request.getParameter("DitemPartNumber"); // part number
	        		    String transType = request.getParameter("popupTransType"); // transaction type
	        		    String Description = request.getParameter("description"); // description
	        		    String elementName = request.getParameter("popupElementName"); // element name
	        		    String MacAddress = request.getParameter("address"); // MAC address
	        		    String PurchaseOrder = request.getParameter("PoId"); // PO ID
	        		    String woId = request.getParameter("WoId"); // WO ID
	        		    String address=request.getParameter("address");
	        		    String fromSlot = request.getParameter("fromSlotId"); // from slot
	        		    String toSlot = request.getParameter("toSlotId"); // to slot
	        		    String SiteID = request.getParameter("fromSiteId"); // from site
	        		    String wareID = "";
	        		    String wareName = "";
	        
	        		    if (SiteID != null && SiteID.contains(":")) {
	        		        String[] parts = SiteID.split(":");
	        		        if (parts.length == 3) {
	        		            wareID = parts[0];      // WARE_ID
	        		            wareName = parts[1];    // WARE_NAME
	        		            SiteID = parts[2];      // FROM_SITE
	        		        }
	        		    }

	        		    String toSiteID = request.getParameter("toSiteId"); // to site
	        		    String toWareID = "";
	        		    String toWareName = "";
	        		    if (toSiteID != null && toSiteID.contains(":")) {
	        		       String[] parts = toSiteID.split(":");
	        		        if (parts.length == 3) {
	        		        	toWareID = parts[0];      // 
	        		        	toWareName = parts[1];    // 
	        		        	toSiteID = parts[2];      // 
	        		        }
	        		    }
	        		    String FAR = request.getParameter("farId"); // FAR ID
	        		    String dniQty = request.getParameter("Qty"); // quantity
	        		    float dniRate = Float.parseFloat(request.getParameter("rate"));
	        		    String dniDiscamount = request.getParameter("discountAmount"); // discount amount
	        		    String dniTax1 = request.getParameter("tax"); // tax
	        		    String dniNetrate = request.getParameter("netRate"); // net rate
	        		    String dniTotalat = request.getParameter("totalAt"); // total currency
	        		    String dniSN = request.getParameter("oldSerialNum"); // from serial number
	        		    String toSerialNumber = request.getParameter("serialNum"); // to serial number
	        		    String transId = request.getParameter("transId"); // transaction ID
	        		    String DniID = request.getParameter("DnItemId"); // DNI ID
	        		    String DnItemNotes = request.getParameter("DnItemNotes");
	        		    String toNodes = request.getParameter("toNodes");
	        		    String fromNodes = request.getParameter("fromNodes");
	        		    String  slctFromNodeDel= request.getParameter("slctFromNodeDel");
	        		    String  slctToNodeDel= request.getParameter("slctToNodeDel");
	        		    System.out.println(slctFromNodeDel);
	        		    System.out.println(slctToNodeDel);
	        		    String supplierID = "";
	        			String supplierName = "";
	        			String PurchaseOrId = "";
	        			float TotalAmount = 0;
	        			int n;
	        			String m, g;
	        			int part2, part3;
	        			if (!PurchaseOrder.isEmpty()) {
	        				n = PurchaseOrder.indexOf(":");
	        				PurchaseOrId = PurchaseOrder.substring(0, n);
	        				
	        				m = PurchaseOrder.substring(n + 1, PurchaseOrder.length());
	        				part2 = m.indexOf(":");
	        				supplierID = m.substring(0, part2);
	        				
	        				g = m.substring(part2 + 1, m.length());
	        				part3 = g.indexOf(":");
	        				supplierName = g.substring(0, part3);
	        				
	        				String Amount = g.substring(part3 + 1, g.length());
	        				TotalAmount = Float.valueOf(Amount);
	        			}
	        			
	        			String action= request.getParameter("action");
        				if (action.equals("Actions")) {
	        			
	        				action = "-- Select Option --";
	        			}
	        			
	        	
                 String getApproval= request.getParameter("getApproval");
	        		    query = session.createNativeQuery(
	        		    	    "UPDATE DISCOVERY_NEW_ITEM SET ITEM_CODE = '" + itmcode + "', LAST_MODIFIED_DATE = sysdate, ITEM_NAME = '" + itmname + 
	        		    	    "', RATE = '" + dniRate + "', DISCOUNT_AMOUNT = '" + dniDiscamount + "', NET_RATE = '" + dniNetrate + 
	        		    	    "', TAX1 = '" + dniTax1 + "', TOTAL_AT = '" + dniTotalat + "', WARE_ID = '" + wareID + 
	        		    	    "', WARE_NAME = '" + wareName + "', FROM_SITE = '" + SiteID + "', TO_WARE_ID = '" + toWareID + 
	        		    	    "', TO_WARE_NAME = '" + toWareName + "', TO_SITE = '" + toSiteID + "', QTY = '" + dniQty + 
	        		    	    "', FROM_SERIAL_NUMBER = '" + dniSN + "', TO_SERIAL_NUMBER = '" + toSerialNumber + 
	        		    	    "', PO_ID = '" + PurchaseOrId + "', WO_ID = '" + woId + 
	        		    	    "', ITEM_MODEL = '" + itemModel + "', ITEM_PART_NUMBER = '" + itemPartNb + "', Far_ID = '" + FAR + 
	        		    	    "', MAC_ADDRESS = '" + MacAddress + "', FROM_SLOT = '" + fromSlot + "', TRANS_ID = '" + transId+ "', TO_SLOT = '" + toSlot + 
	        		    	    "', TRANS_TYPE = '" + transType + "', NOTES = '" + DnItemNotes + 
	        		    	    "', ELEMENT_NAME = '" + elementName + "', SUPPLIER_Name = '" + supplierName+ "', SUPPLIER_ID = '" + supplierID+ "', APPROVAL = '" + getApproval + "', APPROVAL_STATUS = '" + action+ "', DESCRIPTION = '" + Description + 
	        		    	    "' WHERE DNI_ID = :param1"
	        		    	);

	        		    	query.setParameter("param1", DniID).executeUpdate();
	        		    	
	        		    	
	        		    	if (slctToNodeDel != null && !slctToNodeDel.equals("[]")) {
	        		    	    // Convert the string back into an array
	        		    	    slctToNodeDel = slctToNodeDel.replace("[", "").replace("]", "").replace("\"", "");
	        		    	    String[] toNodeIds = slctToNodeDel.split(",");

	        		    	    for (String nodeId : toNodeIds) {
	        		    	        nodeId = nodeId.trim(); // Clean whitespace just in case

	        		    	        query = session.createNativeQuery(
	        		    	            "DELETE FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID = :param1 AND TO_NODE_ID = :param2"
	        		    	        );
	        		    	        query.setParameter("param1", DniID);
	        		    	        query.setParameter("param2", nodeId);
	        		    	        query.executeUpdate();
	        		    	    }
	        		    	}
	        		    	    
	        		    	   	
		        		    	if (slctFromNodeDel != null && !slctFromNodeDel.equals("[]")) {
		        		    	    // Convert the string back into an array
		        		    	    slctToNodeDel = slctToNodeDel.replace("[", "").replace("]", "").replace("\"", "");
		        		    	    String[] fromNodeIds = slctToNodeDel.split(",");

		        		    	    for (String nodeId : fromNodeIds) {
		        		    	        nodeId = nodeId.trim(); // Clean whitespace just in case

		        		    	        query = session.createNativeQuery(
		        		    	            "DELETE FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID = :param1 AND From_NODE_ID = :param2"
		        		    	        );
		        		    	        query.setParameter("param1", DniID);
		        		    	        query.setParameter("param2", nodeId);
		        		    	        query.executeUpdate();
		        		    	    }
		        		    	}
		        		    	  JSONParser parser = new JSONParser();
		        		    	if (toNodes != null && !toNodes.equals("[]")) {
		        		    	  
		        		    	    JSONArray toNodeArray = (JSONArray) parser.parse(toNodes);

		        		    	    for (Object obj : toNodeArray) {
		        		    	        JSONArray arr = (JSONArray) obj;
		        		    	        if (arr.size() < 3) continue;

		        		    	        String toNodeId = arr.get(0).toString();

		        		    	        // Check if TO_NODE_ID already exists for this DNI_ID
		        		    	        Long count = ((Number) session.createNativeQuery(
		        		    	            "SELECT COUNT(*) FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID = :dniId AND TO_NODE_ID = :toNodeId"
		        		    	        )
		        		    	        .setParameter("dniId", DniID)
		        		    	        .setParameter("toNodeId", toNodeId)
		        		    	        .uniqueResult()).longValue();

		        		    	        if (count == 0) {
		        		    	            // Only create if not found
		        		    	            String dnNodeID;
		        		    	            synchronized (this) {
		        		    	                Object seqResult = session.createNativeQuery("SELECT DNI_NODE FROM SEQ_TABLE").uniqueResult();
		        		    	                dnNodeID = "DNI_NODE_" + year + "_" + Integer.parseInt(seqResult.toString());
		        		    	                session.createNativeQuery("UPDATE SEQ_TABLE SET DNI_NODE = DNI_NODE + 1").executeUpdate();
		        		    	                session.createNativeQuery("commit").executeUpdate();
		        		    	            }

		        		    	            DiscoveryNewItemNode dniNode = new DiscoveryNewItemNode();
		        		    	            dniNode.setDniNode(dnNodeID);
		        		    	            dniNode.setCreationDate(new Timestamp(System.currentTimeMillis()));
		        		    	            dniNode.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
		        		    	            dniNode.setFromNodeId(" ");
		        		    	            dniNode.setToNodeId(toNodeId);
		        		    	            dniNode.setFromNodeName(" ");
		        		    	            dniNode.setToNodeName(arr.get(1).toString());
		        		    	            dniNode.setFromNodeType(" ");
		        		    	            dniNode.setToNodeType(arr.get(2).toString());
		        		    	            dniNode.setDniId(DniID);

		        		    	            session.saveOrUpdate(dniNode);
		        		    	        }
		        		    	    }
		        		    	}


	        		    	    // Handle fromNodes
		        		    	if (fromNodes != null && !fromNodes.equals("[]")) {
		        		    	    JSONArray fromNodeArray = (JSONArray) parser.parse(fromNodes);

		        		    	    for (Object obj : fromNodeArray) {
		        		    	        JSONArray arr = (JSONArray) obj;
		        		    	        if (arr.size() < 3) continue;

		        		    	        String fromNodeId = arr.get(0).toString();

		        		    	        // Check if FROM_NODE_ID already exists for this DNI_ID
		        		    	        Long count = ((Number) session.createNativeQuery(
		        		    	            "SELECT COUNT(*) FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID = :dniId AND FROM_NODE_ID = :fromNodeId"
		        		    	        )
		        		    	        .setParameter("dniId", DniID)
		        		    	        .setParameter("fromNodeId", fromNodeId)
		        		    	        .uniqueResult()).longValue();

		        		    	        if (count == 0) {
		        		    	            String dnNodeID;
		        		    	            synchronized (this) {
		        		    	                Object seqResult = session.createNativeQuery("SELECT DNI_NODE FROM SEQ_TABLE").uniqueResult();
		        		    	                dnNodeID = "DNI_NODE_" + year + "_" + Integer.parseInt(seqResult.toString());
		        		    	                session.createNativeQuery("UPDATE SEQ_TABLE SET DNI_NODE = DNI_NODE + 1").executeUpdate();
		        		    	                session.createNativeQuery("commit").executeUpdate();
		        		    	            }

		        		    	            DiscoveryNewItemNode dniNode = new DiscoveryNewItemNode();
		        		    	            dniNode.setDniNode(dnNodeID);
		        		    	            dniNode.setCreationDate(new Timestamp(System.currentTimeMillis()));
		        		    	            dniNode.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
		        		    	            dniNode.setFromNodeId(fromNodeId);
		        		    	            dniNode.setToNodeId(" ");
		        		    	            dniNode.setFromNodeName(arr.get(1).toString());
		        		    	            dniNode.setToNodeName(" ");
		        		    	            dniNode.setFromNodeType(arr.get(2).toString());
		        		    	            dniNode.setToNodeType(" ");
		        		    	            dniNode.setDniId(DniID);

		        		    	            session.saveOrUpdate(dniNode);
		        		    	        }
		        		    	    }
		        		    	}

	        		    	

	         		    	query = session.createQuery("select t.arID from AssetRegistry t where t.ardniID = :param1");

	    		    				query.setParameter("param1", DniID);
	    		    				String AssetRegID = (String) query.uniqueResult();
	    		    				
	    		    				String ArCode = "";
	    		    				
	    	        		    	if ((StringUtils.equalsIgnoreCase(getApproval, "Project Manager") && StringUtils.equalsIgnoreCase(action, "Approved")) || ((StringUtils.equalsIgnoreCase(getApproval,"Asset Unit") && StringUtils.equalsIgnoreCase(action, "Approved")))) {
	    	        		    		
	    	        					if(StringUtils.equalsIgnoreCase(transType, "New Node on New Hardware") || StringUtils.equalsIgnoreCase(transType, "New Hardware on Existed Node") || StringUtils. 
	    	        							equalsIgnoreCase(transType, "Replacement") || StringUtils.equalsIgnoreCase(transType, "New Hardware on New Node"))  {
			                             System.out.println("-- PROJECT MANAGER APPROVAL --");
	    	        						System.out.println(transId);
	    	        						Manager.ApprovalProjectandAsset(transType, getApproval, action,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,woId,DniID,toSiteID,supplierID,supplierName,toWareID,toWareName,dniSN,dniRate,itemModel,itemPartNb,toSiteID,toSerialNumber,toSlot,SiteID,fromSlot,SiteID, MacAddress,session,tx);
	    	        							if(transId != null) {
	    	        								System.out.println("yessss tr");
	    	        								Manager.transactionUpdate(transId,transType,getApproval,session,tx);
	    	        							}
	    	        						}
	    	        					}
	    	        		    
	    	        		    	if (StringUtils.equalsIgnoreCase(getApproval, "Finance") &&  StringUtils.equalsIgnoreCase(action, "Approved")) { 

	    	        					if(StringUtils.equalsIgnoreCase(transType, "New Node on New Hardware") || StringUtils.equalsIgnoreCase(transType, "New Hardware on Existed Node") || StringUtils. 
	    	        							equalsIgnoreCase(transType, "Replacement") || StringUtils.equalsIgnoreCase(transType, "New Hardware on New Node"))  {

	    	        						System.out.println("-- FINANCE APPROVAL --");

	    	        							Manager.ApprovalFinance(transType, getApproval, action,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,woId,DniID,toSiteID,supplierID,supplierName,toWareID,toWareName,dniSN,dniRate,itemModel,itemPartNb,toSiteID,toSerialNumber,toSlot,SiteID,fromSlot,SiteID,MacAddress,session,tx);
	    	        							if(transId != null ) {
	    	        								System.out.println("yessss tr");
	    	        								Manager.transactionUpdate(transId,transType,getApproval,session,tx);
	    	        							}
	    	        						}
	    	        					}
	    	        					
	    	        		    	if (StringUtils.equalsIgnoreCase(getApproval, "Operation Manager") && StringUtils.equalsIgnoreCase(action, "Approved")) {
	    	        					
	    	        					
	    	        					if(StringUtils.equalsIgnoreCase(transType, "New Node on Existed Hardware") || StringUtils.equalsIgnoreCase(transType, "Transfer from Slot to Slot") || StringUtils.equalsIgnoreCase(transType, "Transfer from Node to Node") 
	    	        							|| StringUtils.equalsIgnoreCase(transType, "Transfer from Site to Site") || StringUtils.equalsIgnoreCase(transType, "Disappear for Maintenance")
	    	        							|| StringUtils.equalsIgnoreCase(transType, "Disappear for Transfer") || StringUtils.equalsIgnoreCase(transType, "Maintenance") || StringUtils.equalsIgnoreCase(transType, "Retirement"))
	    	        						
	    	        									{	

	    	        						System.out.println("-- OPERATION MANAGER APPROVAL --");
	    	        						
	    	        								Manager.ApprovalOperational(transType, getApproval, action,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,woId,DniID,toSiteID,supplierID,supplierName,toWareID,toWareName,dniSN,
	    	        										dniRate,itemModel,itemPartNb,toSiteID,toSerialNumber,toSlot,SiteID,fromSlot,SiteID, FAR, MacAddress,session,tx);
	    	        								if(transId != null) {
	    	        									Manager.transactionUpdate(transId,transType,getApproval,session,tx);
	    	        								}
		
	    	        									}
	    	        		    	}
	    	        		        List<Object[]> result = session.createNativeQuery(
	    	        		                "SELECT t.DNI_ID AS dniID, t.TRANS_ID AS transID, t.ITEM_CODE AS dniItemcode, t.ITEM_NAME AS dniItemname, t.TRANS_TYPE AS transType, " +
	    	        		                "t.ELEMENT_NAME AS elementName, NVL(t.NOTES, ' ') AS notes, NVL(t.POSITION, ' ') AS position, t.APPROVAL AS dniAPPROVAL, " +
	    	        		                "t.PO_ID AS dniPOID, TO_CHAR(CREATION_DATE, 'MM/dd/YYYY HH:MI AM') AS dnicreationDate, TO_CHAR(LAST_MODIFIED_DATE, 'MM/dd/YYYY HH:MI AM') AS dnilastModifiedDate, " +
	    	        		                "t.SUPPLIER_ID AS supplierID, t.SUPPLIER_NAME AS supplierName, t.TOTAL_AMOUNT AS totalAmount, " +
	    	        		                "t.WO_ID AS dniWOID, t.WO_PURPOSE AS purpose, t.QTY AS dniQty, t.RATE AS dniRate, t.DISCOUNT_AMOUNT AS dniDiscamount, " +
	    	        		                "t.TAX1 AS dniTax1, t.NET_RATE AS dniNetrate, t.TOTAL AS dniTotal, t.TOTAL_AT AS dniTotalat, t.FROM_SITE AS dniSIte, " +
	    	        		                "t.WARE_ID AS wareID, t.WARE_NAME AS wareName, NVL(t.FROM_SERIAL_NUMBER, ' ') AS dniSN, t.DNI_ID AS dniDNID, " +
	    	        		                "NVL(t.ITEM_MODEL, '') AS itemModel, NVL(t.ITEM_PART_NUMBER, '') AS itemPartNb, " +
	    	        		                "t.APPROVAL_STATUS AS approvalStatus, NVL(t.FROM_SLOT, ' ') AS fromSlot, NVL(t.FAR_ID, ' ') AS farID, NVL(t.MAC_ADDRESS, ' ') AS macAddress, " +
	    	        		                "NVL(t.TO_SLOT, ' ') AS toSlot, t.TO_SITE AS toSite, t.TO_WARE_NAME AS toWareName, " +
	    	        		                "t.TO_WARE_ID AS toWareId, NVL(t.ALCFLG, ' ') AS alcFlg, " +
	    	        		                "NVL(t.TO_SERIAL_NUMBER, ' ') AS toSerialNumber, NVL(t.DESCRIPTION, ' ') AS description, x.toNodeArray, y.fromNodeArray " +
	    	        		                "FROM DISCOVERY_NEW_ITEM t " +
	    	        		                "LEFT JOIN (SELECT DISTINCT DNI_ID, json_object('toNodeArray' VALUE json_arrayagg(json_object('NodeId' VALUE TO_NODE_ID, " +
	    	        		                "'NodeName' VALUE TO_NODE_NAME, 'NodeType' VALUE TO_NODE_TYPE))) AS toNodeArray FROM DISCOVERY_NEW_ITEM_NODE WHERE TO_NODE_ID IS NOT NULL GROUP BY DNI_ID) x " +
	    	        		                "ON x.DNI_ID = t.DNI_ID " +
	    	        		                "LEFT JOIN (SELECT DISTINCT DNI_ID, json_object('fromNodeArray' VALUE json_arrayagg(json_object('NodeId' VALUE FROM_NODE_ID, " +
	    	        		                "'NodeName' VALUE FROM_NODE_NAME, 'NodeType' VALUE FROM_NODE_TYPE))) AS fromNodeArray FROM DISCOVERY_NEW_ITEM_NODE WHERE FROM_NODE_ID IS NOT NULL GROUP BY DNI_ID) y " +
	    	        		                "ON y.DNI_ID = t.DNI_ID " +
	    	        		                "WHERE ( t.DNI_ID = :dniId )"
	    	        		            ).setParameter("dniId", DniID)
	    	        		             .getResultList();

	    	        		            tx.commit();

	    	        		   	 rtn.put("status", request.getParameter("getApproval"));
	    	        		   	 rtn.put("action", request.getParameter("action"));
	    		    	  		 rtn.put("DniID", DniID);
	    		    	  		 String status="";
	    		    	  		
	    		    	
	    		    	  		String actionParam = request.getParameter("action");
	    		    	  		String getApprovalParam = request.getParameter("getApproval");

	    		    	  			

	    		    	  		if ("Project Manager".equals(getApprovalParam) && "Approved".equals(actionParam)) {
	    		    	  		    status = "Asset Unit";
	    		    	  		} else if ("Asset Unit".equals(getApprovalParam) && "Approved".equals(actionParam)) {
	    		    	  		    status = "Finance";
	    		    	  		}

	    		    	  		
	    		    	  		if ("Project Manager".equals(status) || "Operation Manager".equals(status)) {
	    		    	  		    permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
	    		    	  		            "Discovery New Project Manger", "Tree");
	    		    	  		    rtn.put("read", model.asMap().get("readTree").toString());

	    		    	  		} else if ("Asset Unit".equals(status)) {
	    		    	  		    permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
	    		    	  		            "Discovery New Asset Manger", "Tree");
	    		    	  		    rtn.put("read", model.asMap().get("readTree").toString());

	    		    	  		} else if ("Finance".equals(status)) {  // fixed spelling here
	    		    	  		    permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
	    		    	  		            "Discovery New Finance Manger", "Tree");
	    		    	  		    rtn.put("read", model.asMap().get("readTree").toString());
	    		    	  		}

	    	  		
	    	  		
	                 return rtn;
	             } catch (Exception e) {
	                 e.printStackTrace();
	                 return rtn;
	             }
	    }
		return rtn;
	}

	
	
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveAction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> SaveAction(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
rtn.put("done", 1);
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		} 
	   
	     session = AlmDbSession.getInstance().getSession();
	     tx = session.beginTransaction();
	    if (session != null && session.isOpen()) {
	        notifications.headerNotifications(session, model);

	        
	        	 try {
	        	
	        		 String ListJson = request.getParameter("listItems");
	        		 String getApproval= request.getParameter("getApproval");
	        		 String action= request.getParameter("action");
	        		    ObjectMapper mapper = new ObjectMapper();
	        		    List<List<Object>> List = new ArrayList<>();

	        		    try {
	        		        // assuming each item is an array: [[id, name], [id, name], ...]
	        		    	List = mapper.readValue(ListJson, new TypeReference<List<List<Object>>>(){});
	        		    } catch (Exception e) {
	        		        e.printStackTrace();
	        		    }
                  
                   String[] Ids = new String[List.size()];
                   
	        		    for (List<Object> project : List) {
	        		      
	        		    //zeinaaa
	        		    
	        		   System.out.println(project);
	        		    	query = session.createQuery("select t.arID from AssetRegistry t where t.ardniID = :param1");

		    				query.setParameter("param1", project.get(0).toString());
		    				String AssetRegID     = (String) query.uniqueResult();
		    				
		    				String transType      = (project.size() > 4  && project.get(4)  != null) ? project.get(4).toString()  : "";
		    				String transId        = (project.size() > 1  && project.get(1)  != null) ? project.get(1).toString()  : "";
		    				String PurchaseOrId   = (project.size() > 9  && project.get(9)  != null) ? project.get(9).toString()  : "";
		    				String itmcode        = (project.size() > 2  && project.get(2)  != null) ? project.get(2).toString()  : "";
		    				String itmname        = (project.size() > 3  && project.get(3)  != null) ? project.get(3).toString()  : "";
		    				String woId           = (project.size() > 15 && project.get(15) != null) ? project.get(15).toString() : "";
		    				String DniID          = (project.size() > 0  && project.get(0)  != null) ? project.get(0).toString()  : "";
		    				String toSiteID       = (project.size() > 36 && project.get(36) != null) ? project.get(36).toString() : "";
		    				String supplierID     = (project.size() > 12 && project.get(12) != null) ? project.get(12).toString() : "";
		    				String supplierName   = (project.size() > 13 && project.get(13) != null) ? project.get(13).toString() : "";
		    				String toWareID       = (project.size() > 38 && project.get(38) != null) ? project.get(38).toString() : "";
		    				String toWareName     = (project.size() > 37 && project.get(37) != null) ? project.get(37).toString() : "";
		    				String dniSN          = (project.size() > 27 && project.get(27) != null) ? project.get(27).toString() : "";
		    				String itemModel      = (project.size() > 29 && project.get(29) != null) ? project.get(29).toString() : "";
		    				String itemPartNb     = (project.size() > 30 && project.get(30) != null) ? project.get(30).toString() : "";
		    				String toSerialNumber = (project.size() > 40 && project.get(40) != null) ? project.get(40).toString() : "";
		    				String toSlot         = (project.size() > 35 && project.get(35) != null) ? project.get(35).toString() : "";
		    				String SiteID         = (project.size() > 24 && project.get(24) != null) ? project.get(24).toString() : "";
		    				String fromSlot       = (project.size() > 32 && project.get(32) != null) ? project.get(32).toString() : "";
		    				String MacAddress     = (project.size() > 34 && project.get(34) != null) ? project.get(34).toString() : "";
		    				String FAR            = (project.size() > 33 && project.get(33) != null) ? project.get(33).toString() : "";

		    			
		    				float dniRate = 0.0f;
		    				if (project.size() > 18 && project.get(18) != null && !project.get(18).toString().isEmpty()) {
		    				    try {
		    				        dniRate = Float.parseFloat(project.get(18).toString());
		    				    } catch (NumberFormatException e) {
		    				      
		    				    }
		    				}
		    				query = session.createNativeQuery("SELECT APPROVAL, APPROVAL_STATUS FROM DISCOVERY_NEW_ITEM WHERE DNI_ID = :param2");
		    				query.setParameter("param2", project.get(0).toString());

		    				// Execute and fetch result
		    				Object[] result = (Object[]) query.getSingleResult();

		    				// Store into separate strings
		    				String approval = result[0] != null ? result[0].toString() : null;
		    				String approvalStatus = result[1] != null ? result[1].toString() : null;

		    				// Optional: print or log
		    				System.out.println("Before Update - APPROVAL: " + approval + ", APPROVAL_STATUS: " + approvalStatus);
		    				if ("Project Manager".equals(approval) && "Approved".equals(approvalStatus)) {
		    				    query = session.createNativeQuery("UPDATE DISCOVERY_NEW_ITEM SET APPROVAL='Asset Unit', APPROVAL_STATUS=:param1 WHERE DNI_ID= :param2");
		    				    query.setParameter("param1", action);
		    				    query.setParameter("param2", project.get(0).toString());
		    				    query.executeUpdate();
		    				    session.createNativeQuery("commit").executeUpdate();

		    				} 
		    				else if ("Operation Manager".equals(approval) && "-- Select Option --".equals(approvalStatus)) {
		    				    query = session.createNativeQuery("UPDATE DISCOVERY_NEW_ITEM SET APPROVAL='Operation Manager', APPROVAL_STATUS=:param1 WHERE DNI_ID= :param2");
		    				    query.setParameter("param1", action);
		    				    query.setParameter("param2", project.get(0).toString());
		    				    query.executeUpdate();
		    				    session.createNativeQuery("commit").executeUpdate();
		    				    getApproval="Operation Manager";

		    				} 
		    				
		    			
		    				
		    				else if ("Asset Unit".equals(approval) && "Approved".equals(approvalStatus)) {
		    				    query = session.createNativeQuery("UPDATE DISCOVERY_NEW_ITEM SET APPROVAL='Finance', APPROVAL_STATUS=:param1 WHERE DNI_ID= :param2");
		    				    query.setParameter("param1", action);
		    				    query.setParameter("param2", project.get(0).toString());
		    				    query.executeUpdate();
		    				    session.createNativeQuery("commit").executeUpdate();

		    				} else {
		    				    query = session.createNativeQuery("UPDATE DISCOVERY_NEW_ITEM SET APPROVAL_STATUS=:param1 WHERE DNI_ID= :param2");
		    				    System.out.println(project.get(0).toString());
		    				    query.setParameter("param1", action);
		    				    query.setParameter("param2", project.get(0).toString());
		    				    query.executeUpdate();
		    				    session.createNativeQuery("commit").executeUpdate();
		    				}

			String ArCode = "";
		    		    	if ((StringUtils.equalsIgnoreCase(getApproval, "Project Manager") && StringUtils.equalsIgnoreCase(action, "Approved")) || ((StringUtils.equalsIgnoreCase(getApproval,"Asset Unit") && StringUtils.equalsIgnoreCase(action, "Approved")))) {
	        			

	        					if(StringUtils.equalsIgnoreCase(transType, "New Node on New Hardware") || StringUtils.equalsIgnoreCase(transType, "New Hardware on Existed Node") || StringUtils. 
	        							equalsIgnoreCase(transType, "Replacement") || StringUtils.equalsIgnoreCase(transType, "New Hardware on New Node"))  {
	                             System.out.println("-- PROJECT MANAGER APPROVAL --");
	        						System.out.println(transId);
	        						Manager.ApprovalProjectandAsset(transType, getApproval, action,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,woId,DniID,toSiteID,supplierID,supplierName,toWareID,toWareName,dniSN,dniRate,itemModel,itemPartNb,toSiteID,toSerialNumber,toSlot,SiteID,fromSlot,SiteID, MacAddress,session,tx);
	        							if(transId != null) {
	        								System.out.println("yessss tr");
	        								Manager.transactionUpdate(transId,transType,getApproval,session,tx);
	        							}
	        						}
	        					}
	        		    
	        		    	if (StringUtils.equalsIgnoreCase(getApproval, "Finance") &&  StringUtils.equalsIgnoreCase(action, "Approved")) { 

	        					if(StringUtils.equalsIgnoreCase(transType, "New Node on New Hardware") || StringUtils.equalsIgnoreCase(transType, "New Hardware on Existed Node") || StringUtils. 
	        							equalsIgnoreCase(transType, "Replacement") || StringUtils.equalsIgnoreCase(transType, "New Hardware on New Node"))  {

	        						System.out.println("-- FINANCE APPROVAL --");

	        							Manager.ApprovalFinance(transType, getApproval, action,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,woId,DniID,toSiteID,supplierID,supplierName,toWareID,toWareName,dniSN,dniRate,itemModel,itemPartNb,toSiteID,toSerialNumber,toSlot,SiteID,fromSlot,SiteID,MacAddress,session,tx);
	        							if(transId != null ) {
	        								System.out.println("yessss tr");
	        								Manager.transactionUpdate(transId,transType,getApproval,session,tx);
	        							}
	        						}
	        					}
	        					
	        		    	if (StringUtils.equalsIgnoreCase(getApproval, "Operation Manager") && StringUtils.equalsIgnoreCase(action, "Approved")) {
	        					
	        					
	        					if(StringUtils.equalsIgnoreCase(transType, "New Node on Existed Hardware") || StringUtils.equalsIgnoreCase(transType, "Transfer from Slot to Slot") || StringUtils.equalsIgnoreCase(transType, "Transfer from Node to Node") 
	        							|| StringUtils.equalsIgnoreCase(transType, "Transfer from Site to Site") || StringUtils.equalsIgnoreCase(transType, "Disappear for Maintenance")
	        							|| StringUtils.equalsIgnoreCase(transType, "Disappear for Transfer") || StringUtils.equalsIgnoreCase(transType, "Maintenance") || StringUtils.equalsIgnoreCase(transType, "Retirement"))
	        						
	        									{	

	        						System.out.println("-- OPERATION MANAGER APPROVAL --");
	        						
	        								Manager.ApprovalOperational(transType, getApproval, action,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,woId,DniID,toSiteID,supplierID,supplierName,toWareID,toWareName,dniSN,
	        										dniRate,itemModel,itemPartNb,toSiteID,toSerialNumber,toSlot,SiteID,fromSlot,SiteID, FAR, MacAddress,session,tx);
	        								if(transId != null) {
	        									Manager.transactionUpdate(transId,transType,getApproval,session,tx);
	        								}

	        									}
	        					
	        					
	        					
	        		    	}  	
	        		    	
	        		    
	        		    
	        		    }
	        			for (int i = 0; i < List.size(); i++) {
    					    Ids[i] = List.get(i).get(0).toString(); // assuming ID is at index 0
    					}
	        		      rtn.put("Ids",Ids);
	        		      rtn.put("approvlStatus", getApproval);
	        		      rtn.put("action", action);
	        		      rtn.put("readProjectM", request.getParameter("readProjectM"));
	  					  rtn.put("readAssetM", request.getParameter("readAssetM"));
	        		      rtn.put("readFinanceM", request.getParameter("readFinanceM"));
	        		      rtn.put("writeProjectM", request.getParameter("writeProjectM"));
		    	  		  rtn.put("writeAssetM", request.getParameter("writeAssetM"));
		    	  		  rtn.put("writeFinanceM", request.getParameter("writeFinanceM"));
		    	  		  rtn.put("saveProjectM", request.getParameter("saveProjectM"));
		    	  		  rtn.put("saveAssetM", request.getParameter("saveAssetM"));
		    	  		  rtn.put("saveFinanceM", request.getParameter("saveFinanceM"));
		    	  		  rtn.put("approveRejectProjectM", request.getParameter("approveRejectProjectM"));
		    	  		  rtn.put("approveRejectAssetM", request.getParameter("approveRejectAssetM"));
		    	  		  rtn.put("approveRejectFinanceM", request.getParameter("approveRejectFinanceM"));
		    	  		
	    	  		
	                 return rtn;
	             } catch (Exception e) {
	                 e.printStackTrace();
	                 return rtn;
	             }
	    }
		return rtn;
	}

	
	// To get all purchaseRequestsId in the purchasOrder Form View Module
		@RequestMapping(value = "/GetAllPendingDN", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> GetAllPendingDN(Locale locale, Model model, HttpServletRequest request,
				HttpServletResponse response) {

			Map<String, Object> rtn = new LinkedHashMap<>();
			if (LoginServices.checkSession(request, response).equals("Login")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
			String requestName = "%" + request.getParameter("ID") + "%";

			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
					        "Discovery New Project Manger", "Tree");
					String readProjectM = ((Integer) model.asMap().get("readTree")).toString();
					model.addAttribute("readProjectM", readProjectM);

					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
					        "Discovery New Asset Manger", "Tree");
					String readAssetM = ((Integer) model.asMap().get("readTree")).toString();
					model.addAttribute("readAssetM", readAssetM);

					permissions.setPerms(model, permissions.getUserPermsWithSession(session, request),
					        "Discovery New Finance Manger", "Tree");
					String readFinanceM = ((Integer) model.asMap().get("readTree")).toString();
					model.addAttribute("readFinanceM", readFinanceM);

					// Build WHERE condition dynamically
					List<String> conditions = new ArrayList<>();
					conditions.add("APPROVAL_STATUS = '-- Select Option --'");
					if (!"0".equals(readProjectM)) {
					    conditions.add("(APPROVAL = 'Project Manager' AND APPROVAL_STATUS = '-- Select Option --') 	OR (APPROVAL = 'Operation Manager' AND APPROVAL_STATUS = '-- Select Option --') ");
					}
					if (!"0".equals(readAssetM)) {
					    conditions.add("(APPROVAL = 'Asset Unit' AND APPROVAL_STATUS = '-- Select Option --') OR (APPROVAL = 'Project Manager' AND APPROVAL_STATUS = 'Approved')");
					}
					
					if (!"0".equals(readFinanceM)) {
					    conditions.add("(APPROVAL = 'Finance' AND APPROVAL_STATUS = '-- Select Option --') OR (APPROVAL = 'Asset Unit' AND APPROVAL_STATUS = 'Approved')");
					}

					// Combine conditions with OR
					String whereClause = String.join(" OR ", conditions);

					String str =
					    "SELECT " +
					    "  DNI_ID AS dniI, " +
					    "  DNI_ID AS dniID, " +
					    "  TRANS_ID AS transID, " +
					    "  TRANS_TYPE AS transType, " +
					    "  CASE " +
					    "    WHEN APPROVAL = 'Asset Unit' AND APPROVAL_STATUS = 'Approved' THEN 'Finance' " +
					    "    WHEN APPROVAL = 'Project Manager' AND APPROVAL_STATUS = 'Approved' THEN 'Asset Unit' " +
					    "    ELSE APPROVAL " +
					    "  END AS approval, " +
					    "  FROM_SITE AS fromSite, " +
					    "  TO_SITE AS toSite, " +
					    "  PO_ID AS poID, " +
					    "  TO_CHAR(LAST_MODIFIED_DATE, 'YYYY-MM-DD HH24:MI:SS') AS lastModifiedDate " +
					    "FROM DISCOVERY_NEW_ITEM " +
					    "WHERE " + whereClause + " " +
					    "ORDER BY LAST_MODIFIED_DATE DESC";

					
					
					query = session.createNativeQuery(str);
				
					query.setFirstResult(0);
					query.setMaxResults(40);
					rtn.put("ListPending", query.list());
				} catch (Exception e) {
					logger.info("Error in getting the purchase requests related to the PO with a message : " + e);
					rtn.put("ListPending", "");
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


	         
	

