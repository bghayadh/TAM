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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.NodeListView;
import com.aliat.alm.models.NodePassive;

@Controller
public class NodeActiveController {
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
	@RequestMapping(value = "/NodeListView", method = RequestMethod.GET)

	public String ClientsListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			try {
				List<NodeListView> listNodes = new ArrayList<NodeListView>();

				str = "select n.NODE_PK as nodePk, n.NODE_PK as nodePK, nvl(n.NODE_ID,'null') as nodeID, nvl(n.NODE_NAME,'null') as nodeName, n.NODE_TYPE as nodeType, n.NODE_MODEL as nodeModel, nvl(n.SITE_ID, 'null') as siteID, nvl(n.WARE_NAME,'null') as wareName, "
						+ "TO_CHAR(n.CREATION_DATE,'YYYY-MM-DD HH24:MI:SS') as createdDate, TO_CHAR(n.UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS') as updateDate "
						+ "from NODE_ACTIVE n" + " order by n.UPDATE_DATE DESC";

				query = session.createNativeQuery(str);
				listNodes = ((NativeQuery<NodeListView>) query).addScalar("nodePk").addScalar("nodePK")
						.addScalar("nodeID").addScalar("nodeName").addScalar("nodeType").addScalar("nodeModel")
						.addScalar("siteID").addScalar("wareName").addScalar("createdDate").addScalar("updateDate")
						.setResultTransformer(Transformers.aliasToBean(NodeListView.class)).list();

				model.addAttribute("ListGridTable", mapper.writeValueAsString(listNodes));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in NodeListView due to \n " + exceptionAsString);
				logger.info("Error in NodeListView due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return "NodeListView";
	}

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
									+ " TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS'), DOMAIN, VENDOR, TO_TRANS_SOURCE FROM NODE_2G WHERE "
									+ "NODE_PK = :param1");
					query.setParameter("param1", NodePK);

					model.addAttribute("listGCELL", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT UCELL_ID, CELLID, CELLNAME, LOCELL, NODEBFUNCTIONNAME, ULFREQ, DLFREQ, MAXPOWER,"
									+ " USERLABEL, MAXTXPOWER, UARFCNUPLINK, UARFCNDOWNLINK, PSCRAMBCODE, NODEBNAME, LAC, SAC,"
									+ " RAC, MANUFACTURERDATA, RADIUS, HORAD, DI,  TO_CHAR(UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS'), "
									+ "STATUS, TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS'),"
									+ " DOMAIN, VENDOR FROM NODE_4G WHERE NODE_PK = :param1");

					query.setParameter("param1", NodePK);

					model.addAttribute("listUCELL", mapper.writeValueAsString(query.list()));

					query = session.createNativeQuery(
							"SELECT LCELL_ID, LOCALCELLID, CELLNAME, CELLRADIUS, FREQBAND, ULEARFCNCFGIND, ULEARFCN, DLEARFCN,"
									+ " ULBANDWIDTH, DLBANDWIDTH, CELLID, PHYCELLID, FDDTDDIND, ENODEBFUNCTIONNAME, NBCELLFLAG, "
									+ "  TO_CHAR(UPDATE_DATE,'YYYY-MM-DD HH24:MI:SS'), STATUS, TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS'), DOMAIN, VENDOR "
									+ " FROM NODE_3G WHERE NODE_PK = :param1");

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

	@RequestMapping(value = "/NodeFormViewSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> NodeFormViewSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) throws Exception {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String nodePk = null;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notification.headerNotifications(session, model);
			try {
				nodePk = request.getParameter("nodepk");
				NodePassive nodePassive = new NodePassive();
				String passivePk;
				Timestamp CreationDate;
				Calendar calendar = new GregorianCalendar();
				DateFormat inputFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println(request.getParameter("passivePk"));
				if (request.getParameter("passivePk") == "") {
					passivePk = "Passive_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(
							session.createNativeQuery("SELECT NODE_PASSIVE FROM SEQ_TABLE").uniqueResult().toString());
					CreationDate = new Timestamp((new Timestamp(System.currentTimeMillis())).getTime());
				} else {
					passivePk = request.getParameter("passivePk");
					CreationDate = (new Timestamp(
							(outputFormatter.parse(request.getParameter("creationDate"))).getTime()));

				}
				session.createNativeQuery("UPDATE SEQ_TABLE SET NODE_PASSIVE = NODE_PASSIVE + 1 ").executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
				nodePassive.setPassivePk(passivePk);
				nodePassive.setNodeId(request.getParameter("uniqueNodeId"));
				nodePassive.setNodeName(request.getParameter("nodeName"));
				nodePassive.setSiteType(request.getParameter("siteType"));
				nodePassive.setSwap(request.getParameter("swap"));
				nodePassive.setSwapDate(request.getParameter("SwapDate"));
				nodePassive.setStatus(request.getParameter("status"));
				nodePassive.setCircleId(request.getParameter("circleId"));
				nodePassive.setDiscoveryDate(Timestamp
						.valueOf(outputFormatter.format(inputFormatter.parse(request.getParameter("discoveryDate")))));
				nodePassive.setLastShownDate(Timestamp
						.valueOf(outputFormatter.format(inputFormatter.parse(request.getParameter("shownDate")))));
				nodePassive.setLastModifiedDate(new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));
				nodePassive.setCreationDate(CreationDate);

				session.saveOrUpdate(nodePassive);
				System.out.println(nodePk);
			}

			catch (Exception e) {
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
			rtn.put("nodePK", nodePk);
		}

		return rtn;

	}
	
	@RequestMapping(value = "/GetAllNode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllNode(Locale locale, Model model, HttpServletRequest request,
	        HttpServletResponse response) {

	    Map<String, Object> rtn = new LinkedHashMap<>();

	    String Node = "%" + request.getParameter("Node") + "%";
	    System.out.println("Node is " +Node);
	    session = AlmDbSession.getInstance().getSession();
	    if (session != null && session.isOpen()) {
	        tx = session.beginTransaction();
	        try {
	        	query = session.createQuery("SELECT t1.nodeName, t1.nodePK, t1.uniNodeID,t1.nodeName,t1.nodeType"
	        			+ " FROM NodeActive t1"
	        	        + " WHERE LOWER(t1.nodeName) LIKE LOWER(:Node) OR LOWER(t1.nodePK) "
	        	        + "LIKE LOWER(:Node) OR LOWER(t1.uniNodeID) LIKE LOWER(:Node) "
	        	        + "OR LOWER(t1.nodeName) LIKE LOWER(:Node) OR LOWER(t1.nodeType) LIKE LOWER(:Node)");
	        query.setParameter("Node", Node); 
	        	query.setFirstResult(0);
				query.setMaxResults(40);
	        	
				System.out.println("ListNode is " +mapper.writeValueAsString(query.list()));
	        	rtn.put("ListNode", query.list());
	   } catch (Exception e) {
	            sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            exceptionAsString = sw.toString();
	            logger.finest("Error in GetAllNode due to \n " + exceptionAsString);
	            logger.info("Error in GetAllNode due to \n " + exceptionAsString);
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