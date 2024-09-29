package com.aliat.alm.controller;

import java.util.Calendar;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.NodePortMappingListView;
import com.aliat.alm.models.NodePassive;
import com.aliat.alm.models.NodePortMapping;

@Controller
public class NodePortMappingController {
	private static final Logger logger = Logger.getLogger(ClientsController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	private static String str;
	private static Object[] result;

	@Autowired
	Permissions permissions;

	@Autowired
	Form form;

	@Autowired
	Notify notification;

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/NodePortMappingListView", method = RequestMethod.GET)

	public String NodePortMappingListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {
				List<NodePortMappingListView> listNodes = new ArrayList<NodePortMappingListView>();

				str = "select n.NODE_ID as nodeID,n.NODE_ID as nodeId, nvl(n.NODE_NAME,'null') as nodeName, n.NODE_TYPE as nodeType, n.NODE_MODEL as nodeModel, nvl(n.SITE_ID, 'null') as siteID, nvl(n.WARE_NAME,'null') as wareName, "
						+ "TO_CHAR(n.CREATION_DATE,'YYYY-MM-DD HH24:MI:SS') as createdDate, TO_CHAR(n.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') as updateDate "
						+ "from NODE_ACTIVE n where n.ACTIVE_RECORD =1 order by n.UPDATE_DATE DESC";

				query = session.createNativeQuery(str);
				listNodes = ((NativeQuery<NodePortMappingListView>) query).addScalar("nodeID").addScalar("nodeId")
						.addScalar("nodeName").addScalar("nodeType").addScalar("nodeModel")
						.addScalar("siteID").addScalar("wareName").addScalar("createdDate").addScalar("updateDate")
						.setResultTransformer(Transformers.aliasToBean(NodePortMappingListView.class)).list();

				model.addAttribute("ListGridTable", mapper.writeValueAsString(listNodes));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in NodePortMappingListView due to \n " + exceptionAsString);
				logger.info("Error in NodePortMappingListView due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return "NodePortMappingListView";
	}
	
	
	@RequestMapping(value = "/NodePortMappingFormView", method = RequestMethod.GET)

	public String NodePortMappingFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		String NodeID,NodePK,NodeName, navAction="2";
		int SelectedIndex;


		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {

				NodeID = request.getParameter("NodeID");
				navAction = request.getParameter("NavAction");
				//get node_pk used in navigation 
				query = session.createNativeQuery(
						"Select NODE_ID,NODE_PK FROM NODE_ACTIVE WHERE NODE_ID =:param1 AND ACTIVE_RECORD=1");
				query.setParameter("param1", NodeID);
				
				Object[] row = (Object[]) query.getSingleResult();
				NodePK="";
				if (row != null) {
					
					NodePK = (String) row[1]; 
				   
				    System.out.println("NodePK1: " + NodePK);
				    System.out.println("NodeID1: " + NodeID);
				}
				String Result[] = new String[4];
				
				Result = form.NavigationNP(session, "Node_Active", "Node_PK", NodePK, "UPDATE_DATE", navAction);

				SelectedIndex = Integer.parseInt(Result[1]);
				NodePK= Result[2];
				 System.out.println("NodePK22: " + NodePK);
				query = session.createNativeQuery(
						"Select NODE_ID,NODE_NAME,NODE_PK,UNIQUE_NODE_ID,TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') FROM NODE_ACTIVE WHERE NODE_PK =:param1 AND ACTIVE_RECORD=1");
				query.setParameter("param1", NodePK);
				
				 row = (Object[]) query.getSingleResult();
				NodePK="";
				if (row != null) {
					
					NodePK = (String) row[2]; 
					NodeID=(String) row[0];
					model.addAttribute("node_id", NodeID);
					model.addAttribute("creation_date", row[4]);
					model.addAttribute("update_date", row[5]);
					model.addAttribute("node_name", row[1]);
					
				   
				    System.out.println("NodePK2: " + NodePK);
				    System.out.println("NodeID2: " + NodeID);
				}
				model.addAttribute("SelectedIndex",SelectedIndex);
				model.addAttribute("nodeCount", Integer.parseInt(Result[0]));

				if (NodeID != null) {
					model.addAttribute("Status", "old");
					
		
					query = session.createNativeQuery(
							"SELECT MACADDR,SERIALNUMBER,UNITPOSITION,STATUS "
							+ "FROM NODE_PORT WHERE NODE_PK =:param1");
					query.setParameter("param1", NodePK);
					model.addAttribute("listActivePort", mapper.writeValueAsString(query.list()));
					
					query = session.createNativeQuery(
							"SELECT PORT_MAPPING_ID,MAC_ADDRESS,SERIAL_NUMBER,UNITPOSITION,STATUS,PORT_NUMBER,RECORD_TYPE,"
							+ "LOCATION_TYPE,LOCATION_ID,LOCATION_NAME,WARE_ID,FIBER_ID,FIBER_NAME,TX_STRAND_NB,TX_TUBE_NB,"
							+ "RX_STRAND_NB,RX_TUBE_NB,TX_STRAND_COLOR,TX_TUBE_COLOR,RX_STRAND_COLOR,RX_TUBE_COLOR,CABLE_LENGTH,CABLE_TYPE,TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS'),REF_STATUS "
							+ "FROM NODE_PORT_MAPPING WHERE NODE_ID =:param2");
					query.setParameter("param2", NodeID);
					model.addAttribute("listPassivePort", mapper.writeValueAsString(query.list()));

				}

				else {
					model.addAttribute("Status", "new");

				}
				


			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in NodePortMappingFormView due to \n " + exceptionAsString);
				logger.info("Error in NodePortMappingFormView due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return "NodePortMappingFormView";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/NodePortMappingFormViewSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> NodePortMappingFormViewSave(Locale locale, Model model,
			@ModelAttribute ItemParameters itemParameters, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();

		session = AlmDbSession.getInstance().getSession();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			// String boardCity = request.getParameter("boardCity");
			try {
				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				String nodeID = request.getParameter("nodeID");
				String nodeName = request.getParameter("nodeName");

				String portMappingID = "";

					
					NodePortMapping nodePortMapping;
					
					if (itemParameters.getDictParameter().size() > 0) {
						/*
						query = session.createNativeQuery(
								"delete from NODE_PORT_MAPPING where NODE_ID = '" + nodeID + "'");
						query.executeUpdate();*/
						
						for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
							System.out.println("in "+i);
							nodePortMapping = new NodePortMapping();
							
							Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
							Timestamp creationDate;
							if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get("creationDate"), "") || StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get("creationDate"), null) || StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get("creationDate"), "null")) {
								creationDate = lastModifiedDate;
							} else {
								DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
								creationDate = new Timestamp(
										formatter.parse(request.getParameter("creationDate")).getTime());
							}
							
							
							String MacAddres=itemParameters.getDictParameter().get(i).get("MACAddress");
							String serialNb=itemParameters.getDictParameter().get(i).get("serialNb");
							String portAddress=itemParameters.getDictParameter().get(i).get("portAddress");
							String status=itemParameters.getDictParameter().get(i).get("portStatus");
							String portNb=itemParameters.getDictParameter().get(i).get("portNb");
							String recordType=itemParameters.getDictParameter().get(i).get("recordType");
							String locationType=itemParameters.getDictParameter().get(i).get("locationType");
							String locationId=itemParameters.getDictParameter().get(i).get("locationID");
							String locationName=itemParameters.getDictParameter().get(i).get("locationName");
							String wareID=itemParameters.getDictParameter().get(i).get("wareID");
							String cableType=itemParameters.getDictParameter().get(i).get("cableType");
							String fiberID=itemParameters.getDictParameter().get(i).get("cableID");
							String fiberName=itemParameters.getDictParameter().get(i).get("cableName");
							String TXStrandNb=itemParameters.getDictParameter().get(i).get("txStrandNb");
							String TXTubeNb=itemParameters.getDictParameter().get(i).get("txTubeNb");
							String RXStrandNb=itemParameters.getDictParameter().get(i).get("rxStrandNb");
							String RXTubeNb=itemParameters.getDictParameter().get(i).get("rxTubeNb");
							String TXStrandColor=itemParameters.getDictParameter().get(i).get("txStrandColor");
							String TXTubeColor=itemParameters.getDictParameter().get(i).get("txTubeColor");
							String RXStrandColor=itemParameters.getDictParameter().get(i).get("rxStrandColor");
							String RXTubeColor=itemParameters.getDictParameter().get(i).get("rxTubeColor");
							String cableLength=itemParameters.getDictParameter().get(i).get("cableLength");
							String refStatus=itemParameters.getDictParameter().get(i).get("refPortStatus");
							
							
							//insert case
							if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get("port_Mapping_ID"), "")
									|| StringUtils.equalsIgnoreCase(
											itemParameters.getDictParameter().get(i).get("port_Mapping_ID"), null)) {
								synchronized (this) {
									// db_Port_Id = "DB_PORT_" + year + "_" + appConfig.getSeqNbr(62,session);
									portMappingID = "NODE_PORT_MAPPING_" + year + "_"
											+ Integer
													.parseInt(session.createNativeQuery("SELECT NODE_PORT_MAPPING FROM SEQ_TABLE")
															.uniqueResult().toString());
									query = session.createNativeQuery("UPDATE SEQ_TABLE SET NODE_PORT_MAPPING = NODE_PORT_MAPPING + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate();
									// System.out.println("the db port is " + db_Port_Id);
									
									//insert new record
									nodePortMapping.setPortMappingID(portMappingID);
									
									nodePortMapping.setNodeID(nodeID);
									nodePortMapping.setNodeName(nodeName);
							

								
									nodePortMapping.setMACAddr(MacAddres != "" ? MacAddres : "");
									
									nodePortMapping.setSerialNB(serialNb != "" ? serialNb : "");
									
									nodePortMapping.setUnitPosition(portAddress != "" ? portAddress : "");
									
									nodePortMapping.setStatus(status != "" ? status : "");
									
									nodePortMapping.setRefStatus(refStatus != "" ? refStatus : "");
									
									nodePortMapping.setPortNB(Integer.parseInt(portNb));
									
									nodePortMapping.setRecordType(recordType != "" ? recordType : "");
									
									nodePortMapping.setLocationType(locationType != "" ? locationType : "");
									
									nodePortMapping.setLocationID(locationId != "" ? locationId : "");
									
									nodePortMapping.setLocationName(locationName != "" ? locationName : "");
									
									nodePortMapping.setWareID(wareID != "" ? wareID : "");
									
									nodePortMapping.setCableType(cableType != "" ? cableType : "");
									
									nodePortMapping.setFiberID(fiberID != "" ? fiberID : "");

									nodePortMapping.setFiberName(fiberName != "" ? fiberName : "");
									
									nodePortMapping.setCableLength(Double.parseDouble(cableLength));
									
									nodePortMapping.setTxStrandNB(TXStrandNb != "" ? TXStrandNb : "");
									
									nodePortMapping.setTxTubeNB(TXTubeNb != "" ? TXTubeNb : "");
									
									nodePortMapping.setRxStrandNB(RXStrandNb != "" ? RXStrandNb : "");
									
									nodePortMapping.setRxTubeNB(RXTubeNb != "" ? RXTubeNb : "");
									
									nodePortMapping.setTxStrandColor(TXStrandColor != "" ? TXStrandColor : "");
									
									nodePortMapping.setTxTubeColor(TXTubeColor != "" ? TXTubeColor : "");
									
									nodePortMapping.setRxStrandColor(RXStrandColor != "" ? RXStrandColor : "");
									
									nodePortMapping.setRxTubeColor(RXTubeColor != "" ? RXTubeColor : "");
									
									nodePortMapping.setCreatedDate(creationDate);
									nodePortMapping.setLastModifiedDate(lastModifiedDate);
									
									
									

									session.saveOrUpdate(nodePortMapping);
									session.flush();
									session.clear();
									
									
									
								}
							} else {//update case
								portMappingID = itemParameters.getDictParameter().get(i).get("port_Mapping_ID");
						
								
								 query = session.createNativeQuery("UPDATE NODE_PORT_MAPPING SET MAC_ADDRESS = '"
										+ MacAddres + "',SERIAL_NUMBER = '" + serialNb + "',UNITPOSITION ='"
										+ portAddress + "', LAST_MODIFIED_DATE= TIMESTAMP '" + lastModifiedDate + "' "
										+ ",STATUS = '"+status+ "',PORT_NUMBER = '"+portNb+ "',RECORD_TYPE = '"+recordType
										+ "', LOCATION_TYPE = '"+ locationType + "',LOCATION_ID = '" + locationId + "',LOCATION_NAME ='"
										+ locationName + "' ,WARE_ID = '"
										+ wareID + "',CABLE_TYPE = '" + cableType + "',FIBER_ID ='"
										+ fiberID + "',FIBER_NAME = '"+fiberName+ "',TX_STRAND_NB = '"+TXStrandNb+ "',TX_TUBE_NB = '"+TXTubeNb
										+ "', RX_STRAND_NB = '"+ RXStrandNb + "',RX_TUBE_NB = '" + RXTubeNb + "',TX_STRAND_COLOR ='"
										+ TXStrandColor + "' ,TX_TUBE_COLOR = '"
										+ TXTubeColor + "',RX_STRAND_COLOR = '" + RXStrandColor + "',RX_TUBE_COLOR ='"
										+ RXTubeColor + "', CABLE_LENGTH ='"+cableLength+"',REF_STATUS ='"+refStatus+"' "
										+ " WHERE PORT_MAPPING_ID = '" + portMappingID + "' ");
								 query.executeUpdate();
							
							}
						}

					}
					//delete
					for (int i = 0; i < itemParameters.getDictParameterDel().size(); i++) {
						System.out.println("in delete "+i);
						 query = session
								.createNativeQuery("DELETE FROM NODE_PORT_MAPPING WHERE PORT_MAPPING_ID='"
										+ itemParameters.getDictParameterDel().get(i).get("portMappingID") + "'");
						 query.executeUpdate();
					}
					

				
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in NodePortMappingFormViewSave due to \n " + exceptionAsString);
				logger.info("Error in NodePortMappingFormViewSave due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();

				}
			}
		}
		return rtn;
	}
	
	@RequestMapping(value = "/findNodeMappingDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findNodeMappingDetails(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		
		Map<String, Object> rtn = new LinkedHashMap<>();
		session = AlmDbSession.getInstance().getSession();
		
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;

		} 
		else {
			
			if (session != null && session.isOpen()) {
	
				tx = session.beginTransaction();
				notification.headerNotifications(session, model);
	
				try {
					String NodeID,NodePK;
					NodeID = request.getParameter("NodeID");
					//get node_pk used in navigation 
					query = session.createNativeQuery(
							"Select NODE_ID,NODE_PK FROM NODE_ACTIVE WHERE NODE_ID =:param1 AND ACTIVE_RECORD=1");
					query.setParameter("param1", NodeID);
					
					Object[] row = (Object[]) query.getSingleResult();
					NodePK="";
					if (row != null) {
						
						NodePK = (String) row[1]; 
					   
					    System.out.println("NodePK1: " + NodePK);
					    System.out.println("NodeID1: " + NodeID);
					}
					
	
					if (NodeID != null) {
			
						query = session.createNativeQuery(
								"SELECT MACADDR,SERIALNUMBER,UNITPOSITION,STATUS "
								+ "FROM NODE_PORT WHERE NODE_PK =:param1");
						query.setParameter("param1", NodePK);
						rtn.put("listActivePort", query.list());
						
						query = session.createNativeQuery(
								"SELECT PORT_MAPPING_ID,MAC_ADDRESS,SERIAL_NUMBER,UNITPOSITION,STATUS,PORT_NUMBER,RECORD_TYPE,"
								+ "LOCATION_TYPE,LOCATION_ID,LOCATION_NAME,WARE_ID,FIBER_ID,FIBER_NAME,TX_STRAND_NB,TX_TUBE_NB,"
								+ "RX_STRAND_NB,RX_TUBE_NB,TX_STRAND_COLOR,TX_TUBE_COLOR,RX_STRAND_COLOR,RX_TUBE_COLOR,CABLE_LENGTH,CABLE_TYPE,TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') "
								+ "FROM NODE_PORT_MAPPING WHERE NODE_ID =:param2");
						query.setParameter("param2", NodeID);
						rtn.put("listPassivePort", query.list());
	
					}
	
					else {
						rtn.put("Status", "Failed");
	
					}
					
	
	
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in findNodeMappingDetails due to \n " + exceptionAsString);
					logger.info("Error in findNodeMappingDetails due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
		}

		return rtn;
	}
	
/*
	@RequestMapping(value = "/NodeFormView", method = RequestMethod.GET)

	public String NodeFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		String NodePK, navAction="2";
		int SelectedIndex;


		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {

				NodePK = request.getParameter("NodePk");
				navAction = request.getParameter("NavAction");
                
				String Result[] = new String[4];
				
				Result = form.NavigationNP(session, "Node_Active", "Node_PK", NodePK, "UPDATE_DATE", navAction);

				SelectedIndex = Integer.parseInt(Result[1]);
				NodePK= Result[2];

				model.addAttribute("SelectedIndex",SelectedIndex);
				model.addAttribute("nodeCount", Integer.parseInt(Result[0]));

				if (NodePK != null) {
					model.addAttribute("Status", "old");
					query = session.createNativeQuery(
							"select  node_id, TO_CHAR(creation_date,'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(update_date,'YYYY-MM-DD HH24:MI:SS'),node_name,node_type,node_model,site_id,ware_name,"
									+ "vendor,domain,node_pk,unique_node_id from node_active where node_pk =:param1");
					query.setParameter("param1", NodePK);
					result = (Object[]) query.uniqueResult();
					model.addAttribute("node_id", result[0]);
					model.addAttribute("creation_date", result[1]);
					model.addAttribute("update_date", result[2]);
					model.addAttribute("node_name", result[3]);
					model.addAttribute("node_type", result[4]);
					model.addAttribute("node_model", result[5]);
					model.addAttribute("site_id", result[6]);
					model.addAttribute("ware_name", result[7]);
					model.addAttribute("vendor", result[8]);
					model.addAttribute("domain", result[9]);
					model.addAttribute("node_pk", result[10]);
					model.addAttribute("unique_node_id", result[11]);

					query = session.createNativeQuery("select passive_pk,site_type,swap,swap_date,"
							+ "status,circle_id,TO_CHAR(discovery_date,'YYYY-MM-DD HH24:MI:SS'),"
							+ "TO_CHAR(last_shown_date,'YYYY-MM-DD HH24:MI:SS') ,TO_CHAR(creation_date,'YYYY-MM-DD HH24:MI:SS'),"
							+ " TO_CHAR(last_modified_date,'YYYY-MM-DD HH24:MI:SS')"
							+ "from node_passive where unique_node_id=:param1 and node_name=:param2");
					query.setParameter("param1", result[11]);
					query.setParameter("param2", result[3]);

					result = (Object[]) query.uniqueResult();
					if (result != null) {
						model.addAttribute("passivePk", result[0]);
						model.addAttribute("siteType", result[1]);
						model.addAttribute("swap", result[2]);
						model.addAttribute("swapDate", result[3]);
						model.addAttribute("status", result[4]);
						model.addAttribute("circleId", result[5]);
						String discoveryDateString = result[6].toString();
						String shownDateString = result[7].toString();

						SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date discoveryDate = inputDateFormat.parse(discoveryDateString);
						Date shownDate = inputDateFormat.parse(shownDateString);

						SimpleDateFormat outputDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
						String formattedDiscoveryDate = outputDateFormat.format(discoveryDate);
						String formattedShownDate = outputDateFormat.format(shownDate);

						model.addAttribute("discoveryDate", formattedDiscoveryDate);
						model.addAttribute("shownDate", formattedShownDate);
						model.addAttribute("creationDate", result[8]);
						model.addAttribute("modifiedDate", result[9]);

					}
					query = session.createNativeQuery(
							"SELECT GCELL_ID, CELLID, CELLNAME, MCC, MNC, LAC, CI, NCC, BCC, TYPE, BCCHNO, "
									+ "BASEBANDPOLICY, BASEBANDEQMID, GBTSFUNCTIONNAME,TO_CHAR(UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS'),"
									+ " GLOCELLID, STATUS,"
									+ " TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS'), DOMAIN, VENDOR, TO_TRANS_SOURCE FROM NODE_2GCELL WHERE "
									+ "NODE_PK = :param1");
					query.setParameter("param1", NodePK);

					model.addAttribute("listGCELL", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT UCELL_ID, CELLID, CELLNAME, LOCELL, NODEBFUNCTIONNAME, ULFREQ, DLFREQ, MAXPOWER,"
									+ " USERLABEL, MAXTXPOWER, UARFCNUPLINK, UARFCNDOWNLINK, PSCRAMBCODE, NODEBNAME, LAC, SAC,"
									+ " RAC, MANUFACTURERDATA, RADIUS, HORAD, DI,  TO_CHAR(UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS'), "
									+ "STATUS, TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS'),"
									+ " DOMAIN, VENDOR FROM NODE_3GCELL WHERE NODE_PK = :param1");

					query.setParameter("param1", NodePK);

					model.addAttribute("listUCELL", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT LCELL_ID, LOCALCELLID, CELLNAME, CELLRADIUS, FREQBAND, ULEARFCNCFGIND, ULEARFCN, DLEARFCN,"
									+ " ULBANDWIDTH, DLBANDWIDTH, CELLID, PHYCELLID, FDDTDDIND, ENODEBFUNCTIONNAME, NBCELLFLAG, "
									+ "  TO_CHAR(UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS'), STATUS, TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS'), DOMAIN, VENDOR "
									+ " FROM NODE_4GCELL WHERE NODE_PK = :param1");

					query.setParameter("param1", NodePK);

					model.addAttribute("listLCELL", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"select  board_id,siteindex,cabinetno,subrackno,rackno,frameno,slotno,slotpos,subslotno,inventoryunitid,moduleno,boardname,"
									+ "boardtype,inventoryunittype,vendorunitfamilytype,vendorunittypenumber,vendorname,serialnumber,hardwareversion,"
									+ "TO_CHAR(dateofmanufacture,'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(dateoflastservice,'YYYY-MM-DD HH24:MI:SS'),unitposition,manufacturerdata,softver,logicver,biosver,biosverex,lanver,mbusver,"
									+ "issuenumber,bomcode,model,userlabel,TO_CHAR(UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS'),extinfo,apdevinfo,workmode,status,TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS')"
									+ " from node_board where node_pk =:param1");
					query.setParameter("param1", NodePK);
					model.addAttribute("listBoard", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT CABINET_ID, SITEINDEX, CABINETNO, INVENTORYUNITID, RACKTYPE, OTHERS,"
									+ " BOMRACKTYPE, INVENTORYUNITTYPE, VENDORUNITFAMILYTYPE, VENDORUNITTYPENUMBER, VENDORNAME, SERIALNUMBER,"
									+ " HARDWAREVERSION, TO_CHAR(DATEOFMANUFACTURE,'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(DATEOFLASTSERVICE,'YYYY-MM-DD HH24:MI:SS'), UNITPOSITION, "
									+ "MANUFACTURERDATA, ISSUENUMBER, BOMCODE, EXTINFO, MODEL, USERLABEL, SHAREMODE, CLEICODE, BOM,  TO_CHAR(UPDATE_DATE,"
									+ "'YYYY-MM-DD HH24:MI:SS'), STATUS, TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS'), DOMAIN, VENDOR FROM NODE_CABINET"
									+ " WHERE NODE_PK = :param1");

					query.setParameter("param1", NodePK);
					model.addAttribute("listCabinet", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT ANTENNA_ID, INVENTORYUNITID, INVENTORYUNITTYPE, ANTENNADEVICENO, PRODNR, "
									+ "VENDORUNITFAMILYTYPE, VENDORUNITTYPENUMBER, VENDORNAME, SERIALNUMBER, UNITPOSITION,"
									+ " MANUFACTURERDATA, ANTENNADEVICETYPE, BOMCODE, EXTINFO,MODEL,FILENAME,PARENTDN,CONFIGDN,"
									+ "DISTNAME ,TO_CHAR(UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS'),STATUS, TO_CHAR(CREATION_DATE,"
									+ "'YYYY-MM-DD HH24:MI:SS'), DOMAIN, VENDOR FROM NODE_ANTENNA WHERE NODE_PK = :param1 ");

					query.setParameter("param1", NodePK);
					model.addAttribute("listAntinna", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT HOST_ID, RACKPOSITION, INVENTORYUNITID, VENDORUNITFAMILYTYPE, VENDORUNITTYPENUMBER, "
									+ "VENDORNAME, SERIALNUMBER, HARDWAREVERSION, SOFTVER, "
									+ "TO_CHAR(DATEOFMANUFACTURE, 'YYYY-MM-DD HH24:MI:SS'), "
									+ "TO_CHAR(DATEOFLASTSERVICE, 'YYYY-MM-DD HH24:MI:SS'), "
									+ "MANUFACTURERDATA, HOSTNAME, NUMBEROFCPU, MEMSIZE, HARDDISKSIZE, "
									+ "TO_CHAR(UPDATE_DATE, 'YYYY-MM-DD HH24:MI:SS'), " + " STATUS, DOMAIN, VENDOR "
									+ "FROM NODE_HOST " + "WHERE NODE_PK = :param1");

					query.setParameter("param1", NodePK);
					model.addAttribute("listNodeHost", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT RACK_ID, RACKNO, INVENTORYUNITID, RACKTYPE, INVENTORYUNITTYPE, VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER, "
									+ "VENDORNAME, SERIALNUMBER, HARDWAREVERSION, TO_CHAR(DATEOFMANUFACTURE, 'YYYY-MM-DD HH24:MI:SS'), "
									+ "TO_CHAR(DATEOFLASTSERVICE, 'YYYY-MM-DD HH24:MI:SS'), UNITPOSITION, MANUFACTURERDATA, MODEL, USERLABEL, STATUS "
									+ " FROM NODE_RACK WHERE NODE_PK = :param1");

					query.setParameter("param1", NodePK);
					model.addAttribute("listRack", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT SUBRACK_ID, SITEINDEX, CABINETNO, SUBRACKNO, INVENTORYUNITID, RACKTYPE, BOMRACKTYPE, FRAMETYPE, "
									+ "RACKFRAMENO, MODULENO, INVENTORYUNITTYPE, VENDORUNITFAMILYTYPE, VENDORUNITTYPENUMBER, VENDORNAME, "
									+ "SERIALNUMBER, HARDWAREVERSION, "
									+ "TO_CHAR(DATEOFMANUFACTURE, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(DATEOFLASTSERVICE, 'YYYY-MM-DD HH24:MI:SS'), "
									+ "UNITPOSITION, MANUFACTURERDATA, USERLABEL, BOMCODE, MODEL, ISSUENUMBER, BOMFRAMETYPE, CLEICODE, "
									+ "BOM, EXTINFO, TO_CHAR(UPDATE_DATE, 'YYYY-MM-DD HH24:MI:SS'), STATUS, "
									+ " DOMAIN, VENDOR FROM NODE_SUBRACK " + "WHERE NODE_PK = :param1");

					query.setParameter("param1", NodePK);
					model.addAttribute("listNodeSubrack", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT MODULE_ID, CABINETNO, MODULENO, INVUNITID, IDENTIFICATIONCODE, CONFIGDN, INVUNITTYPE, "
									+ "PARENTDN, RUNTIMEDN, SERIALNUMBER, STATE, UNITPOSITION, VENDORUNITFAMILYTYPE, VENDORUNITTYPENUMBER, "
									+ "SUBRACK_SPECIFIC_TYPE, USERLABEL, VENDORNAME, VERSION, DISTNAME,  "
									+ "TO_CHAR(UPDATE_DATE, 'YYYY-MM-DD HH24:MI:SS') , "
									+ " STATUS, TO_CHAR(CREATION_DATE, 'YYYY-MM-DD HH24:MI:SS'), "
									+ "DOMAIN, VENDOR,  ANTENNA_STATUS FROM NODE_MODULE " + "WHERE NODE_PK = :param1");

					query.setParameter("param1", NodePK);
					model.addAttribute("listNodeModule", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT   SUBMODULE_ID,CABINETNO, MODULENO, SUBMODULENO, INVUNITID, IDENTIFICATIONCODE,"
									+ "       CONFIGDN, PARENTDN, RUNTIMEDN, SERIALNUMBER, UNITTYPE,"
									+ "       VENDORUNITFAMILYTYPE, VENDORUNITTYPENUMBER, VENDORNAME, VERSION, DISTNAME, FILENAME,"
									+ "        TO_CHAR(UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS'), STATUS, TO_CHAR(CREATION_DATE,"
									+ "       'YYYY-MM-DD HH24:MI:SS'), DOMAIN, VENDOR FROM NODE_SUBMODULE WHERE NODE_PK = :param1");

					query.setParameter("param1", NodePK);
					model.addAttribute("listSubModule", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT PORT_ID, SITEINDEX, CABINETNO, SUBRACKNO, RACKNO, FRAMENO, SLOTNO, SLOTPOS, SUBSLOTNO, VENDORUNITFAMILYTYPE,"
									+ "INVENTORYUNITID, PORTNO HARDWAREVERSION, SERIALNUMBER, INVENTORYUNITTYPE, VENDORNAME, VENDORUNITTYPENUMBER,"
									+ "TO_CHAR(DATEOFMANUFACTURE,'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(DATEOFLASTSERVICE,'YYYY-MM-DD HH24:MI:SS'),"
									+ "UNITPOSITION, MACADDR, MANUFACTURERDATA, STATUS, PORTTYPE, PORTRATE FROM NODE_PORT WHERE NODE_PK =:param1");
					query.setParameter("param1", NodePK);
					model.addAttribute("listPort", mapper.writeValueAsString(query.list()));

				}

				else {
					model.addAttribute("Status", "new");

				}
				


			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in NodeFormView due to \n " + exceptionAsString);
				logger.info("Error in NodeFormView due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return "NodeFormView";
	}

	
	
	*/

}