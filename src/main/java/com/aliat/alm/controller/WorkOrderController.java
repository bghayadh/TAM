package com.aliat.alm.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.GoodsReceived;
import com.aliat.alm.models.GoodsReceivedBoq;
import com.aliat.alm.models.GoodsReceivedItem;
import com.aliat.alm.models.Item;
import com.aliat.alm.models.ItemCategory;
import com.aliat.alm.models.PurchaseOrder;
import com.aliat.alm.models.PurchaseOrderBoq;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.models.PurchaseRequestBoq;
import com.aliat.alm.models.SerialNumber;
import com.aliat.alm.models.Supplier;
import com.aliat.alm.models.Warehouse;
import com.aliat.alm.models.WorkOrder;
import com.aliat.alm.models.WorkOrderDT;
import com.aliat.alm.models.WorkOrderDestination;
import com.aliat.alm.models.WorkOrderDestinationBoq;
import com.aliat.alm.models.WorkOrderSource;
import com.aliat.alm.models.WorkOrderSourceBoq;
import com.aliat.alm.models.WorkOrderSourceSerial;
import com.aliat.alm.models.WorkOrderDestinationSerial;
import com.aliat.alm.services.LoginServices;
import com.aliat.alm.services.WorkOrderDestParams;
import com.aliat.alm.services.WorkOrderSourceParams;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
public class WorkOrderController {
	private static final Logger logger = Logger.getLogger(WorkOrderController.class.getName());


	private Session session = null;
	private Transaction tx = null;
	private Query query = null;
	ObjectMapper mapper = new ObjectMapper();
	private static StringWriter sw;
	private static String exceptionAsString;

	
	
	@Autowired
	Notify notifications;
	
	@Autowired
	Form form;
	
	@RequestMapping(value = "/WorkOrderListView", method = RequestMethod.GET)
	public String WorkOrderListView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response){

		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			
			session = AlmDbSession.getInstance().getSession();
			if (session!=null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				
				try {
					
				      
				      query = session.createQuery("select a.workOrdId, a.workOrdId as ID, a.fromWare  || ':'|| a.warehouseSourceName || ':'|| siteIdSource , a.toWare || ':'|| a.warehouseNameDest || ':'|| siteIdDest , TO_CHAR(a.executionDate, 'YYYY-MM-DD HH24:MI:SS') as execDate,TO_CHAR(a.woLastModifieddate, 'YYYY-MM-DD HH24:MI:SS') as LastModifieddate, a.purpose as purp "
								+ " from WorkOrder a ORDER BY a.woLastModifieddate desc"
								);
				      model.addAttribute("WorkOrderDt", mapper.writeValueAsString(query.list()));
				      
				      
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in WorkOrderListView due to \n "+ exceptionAsString);
					logger.info("Error in WorkOrderListView due to \n "+ exceptionAsString);
					
					model.addAttribute("WorkOrderDt", "");
				}finally {
					if (session!=null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}else {
				logger.info("Could not connect to DB in WorkOrderListView method");
				logger.finest("Could not connect to DB in WorkOrderListView method");
			}
			return "WorkOrderListview";
		}
		
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredWorkOrderListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredWorkOrderListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;	
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {

				String startdate, enddate, warehousefrom, Warehouseto;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				warehousefrom = request.getParameter("warehousefrom");
				Warehouseto = request.getParameter("Warehouseto");
			
				List<String> listWork = new ArrayList<String>();

				String str = "select 1 as chkBox, ID as workOrdId, FROM_WAREHOUSE as fromWare, TO_WAREHOUSE as toWare,TO_CHAR(EXECUTION_DATE,'YYYY-MM-DD HH24:MI:SS') as executionDate ,TO_CHAR(LAST_MODIFICATION_DATE,'YYYY-MM-DD HH24:MI:SS') as lastmodifiedDate ,"
						+ " PURPOSE as purpose  from WORK_ORDER  ";
				
				
				if (startdate != null && enddate != null) {
					str = str + " where LAST_MODIFICATION_DATE between TO_DATE('" + startdate
							+ "','YYYY-MM-DD') and TO_DATE('" + enddate + "','YYYY-MM-DD')";
				}
				if (warehousefrom != null && !warehousefrom.equalsIgnoreCase("")) {

					str = str + " and (upper(FROM_WAREHOUSE) LIKE upper('%" + warehousefrom + "%')  )";
				}

				if (Warehouseto != null && !Warehouseto.equalsIgnoreCase("")) {

					str = str + " and (upper(TO_WAREHOUSE) LIKE upper('%" + Warehouseto + "%') )";
				}
				str = str + " ORDER BY LAST_MODIFICATION_DATE DESC ";
				
				Query query = session.createSQLQuery(str);
				

				
				listWork = query.list();
		
				rtn.put("listWork",listWork);
				System.out.println("Filtered Array: " + mapper.writeValueAsString(listWork));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in showing the filtered Work Order  due to \n "+ exceptionAsString);
				logger.info("Error in showing the filtered Work Order  due to \n "+ exceptionAsString);
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
	@RequestMapping(value = "/PendingWorkOrderListView", method = RequestMethod.GET)
	public String PendingWorkOrderListView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		 if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		List<WorkOrder> listworkOrder = new ArrayList<WorkOrder>();

		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		listworkOrder = session.createQuery(
				"select 1, a.workOrdId as ID, a.fromWare  || ':'|| a.warehouseSourceName || ':'|| siteIdSource , a.toWare || ':'|| a.warehouseNameDest || ':'|| siteIdDest , TO_CHAR(a.executionDate, 'YYYY-MM-DD HH24:MI:SS') as execDate,TO_CHAR(a.woLastModifieddate, 'YYYY-MM-DD HH24:MI:SS') as LastModifieddate, a.purpose as purp "
						+ " from WorkOrder a where a.woStatus != 'completed' and a.woStatus != 'closed' and a.woStatus != 'deactivated' and a.woStatus != 'cancelled' and a.woStatus != 'activated' or a.woStatus is null")						 
				.list();

		 
		tx.commit();
		session.close();
		ObjectMapper mapper = new ObjectMapper();
		model.addAttribute("WorkOrderDt", mapper.writeValueAsString(listworkOrder));
		
		return "WorkOrderListview";
	}
	

	
	@RequestMapping(value = "/WorkOrderFormView", method = RequestMethod.GET)
	public String WorkOrderFormView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {

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
				WorkOrder workOrder = null;
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				String fromWareId="",navAction = "2";
				String toWareId="";
				String fromWareName="";
				String fromWareSiteId="";
				String fromWareLat="";
				String fromWareLong="";
				String toWareLat="";
				String toWareLong="";
				String toWareName="";
				String toWareSiteId="";
				String workOrderId = request.getParameter("workOrdId");
				String workList = request.getParameter("workList");							
				ObjectMapper mapper = new ObjectMapper();
				
				
				try {
					
					System.out.println( request.getParameter("NavAction"));
					String queryWareHouses = "select t.wareID as wareID, t.warehouseName as warehouseName, t.wareSiteID as wareSiteID, t.wareLong as wareLong,t.wareLat as wareLat from Warehouse t where wareLong is not null and wareLat is not null";
				    Query resultWareHouses = session.createQuery(queryWareHouses);

					List<Warehouse> ListWareHouses = (List<Warehouse>) resultWareHouses.setResultTransformer(Transformers.aliasToBean(Warehouse.class)).list();
				    
					model.addAttribute("ListWareHouses", mapper.writeValueAsString(ListWareHouses));
					
					navAction = request.getParameter("NavAction");
					if(workOrderId == null) {

						 date = new Timestamp(System.currentTimeMillis());
						 model.addAttribute("wocreationDate", formatter.format(date).toString());
						 model.addAttribute("wolastModifiedDate", formatter.format(date).toString());
						 model.addAttribute("ListWorkOrderItem", "addNew");	
						 model.addAttribute("ListWorkOrderDestItem", "addNew");	
						 model.addAttribute("docStatus", "addNew");
						 model.addAttribute("SelectedIndex", "addNew");
						 model.addAttribute("workOrderCount", "addNew");
						 
						return "WorkOrderFormView";
					}
					
					System.out.println(workOrderId);
					result = form.NavigationNP(session, "WORK_ORDER", "ID", workOrderId, "LAST_MODIFICATION_DATE",
							navAction);
					SelectedIndex = Integer.parseInt(result[1]);
					workOrderId = result[2];
					workOrder = (WorkOrder) session.get(WorkOrder.class, workOrderId);
					model.addAttribute("workOrderCount", Integer.parseInt(result[0]));
					model.addAttribute("SelectedIndex", SelectedIndex);
					
					WorkOrder workOrderObject= (WorkOrder) session.get(WorkOrder.class, workOrderId);	
					String wareHouseSource=workOrderObject.getFromWare();
					String wareHouseDestination=workOrderObject.getToWare();

					
					
					if(workOrderObject!=null) {					
						model.addAttribute("workOrdId",workOrderObject.getWorkOrdId());										
						if (workOrderObject.getWoCreationDate() == null) {
							date = new Timestamp(System.currentTimeMillis());
							model.addAttribute("wocreationDate", formatter.format(date).toString());
						}
				    	else {
				    		date = workOrderObject.getWoCreationDate();
				    		model.addAttribute("wocreationDate", formatter.format(date).toString());
				    	}
				       	
				       	if (workOrderObject.getWoLastModifieddate() == null) {
							date = new Timestamp(System.currentTimeMillis());
							model.addAttribute("wolastModifiedDate", formatter.format(date).toString());
						}
				    	else {
				    		date = workOrderObject.getWoLastModifieddate();
				    		model.addAttribute("wolastModifiedDate", formatter.format(date).toString());
				    	}
				       	
				       	if (workOrderObject.getExecutionDate() == null) {
							date = new Timestamp(System.currentTimeMillis());
							model.addAttribute("woExecutionDate", formatter.format(date).toString());
						}
				    	else {
				    		date = workOrderObject.getExecutionDate();
				    		model.addAttribute("woExecutionDate", formatter.format(date).toString());
				    	}
				       	System.out.println(wareHouseSource);
				       	System.out.println(wareHouseDestination);

				       	
						
							
						if(wareHouseSource != null) {
							Warehouse WareHSrcObj = (Warehouse) session.get(Warehouse.class, wareHouseSource);
							model.addAttribute("woFromWare", WareHSrcObj.getWareID());
							model.addAttribute("WareName", WareHSrcObj.getWarehouseName());
					       	model.addAttribute("frmwarelat", WareHSrcObj.getWareLat());
					       	model.addAttribute("frmwarelong", WareHSrcObj.getWareLong());
					       	model.addAttribute("SiteId", WareHSrcObj.getWareSiteID());

						}
						
						if(wareHouseDestination != null) {
							Warehouse WareHDesObj = (Warehouse) session.get(Warehouse.class, wareHouseDestination );
					       	model.addAttribute("woToWare", WareHDesObj.getWareID());
					       	model.addAttribute("WareNameDest", WareHDesObj.getWarehouseName());
					       	model.addAttribute("towarelat", WareHDesObj.getWareLat());
					       	model.addAttribute("towarelong", WareHDesObj.getWareLong());
					       	model.addAttribute("SiteIdDest", WareHDesObj.getWareSiteID());
						}
				       	
				       	model.addAttribute("Purpose", workOrderObject.getPurpose());
				       	model.addAttribute("woStatus", workOrderObject.getWoStatus());
				    	model.addAttribute("wosTotalQty", workOrderObject.getWostotalQty());
				    	model.addAttribute("wodTotalQty", workOrderObject.getWodtotalQty());
				    	
				    	String queryWorkOrderSrc = "select t.workOrdId as workOrdId, t.CreationDate as CreationDate, t.LastModifieddate as LastModifieddate, t.itemCode as itemCode, t.itemName as itemName, t.itemModel as itemModel,t.itemPartNumber as itemPartNumber, t.currentQty as currentQty , t.qty as qty,t.fromWare as fromWare, t.ToWare as toWare, t.nodeId as nodeId, t.nodeName as nodeName, t.grId as grId, t.prId as prId, t.poId as poId, t.cipId as cipId, t.status as status, t.reconciled as reconciled,t.barcodeScanned as barcodeScanned,t.Id as id from WorkOrderSource t where t.workOrdId like :param1";
					    Query resultWorkOrderSrc = session.createQuery(queryWorkOrderSrc);
					    resultWorkOrderSrc.setParameter("param1", workOrderId);
					    System.out.println(resultWorkOrderSrc);
					    List<WorkOrderSource> ListWorkOrderSourceBoq = (List<WorkOrderSource>) resultWorkOrderSrc.setResultTransformer(Transformers.aliasToBean(WorkOrderSource.class)).list();
					    System.out.println(ListWorkOrderSourceBoq);

					    if (ListWorkOrderSourceBoq.size() != 0) {			    	
							for (WorkOrderSource WorkOrderSourceBoq : ListWorkOrderSourceBoq) {
								
								String WOsourceId = WorkOrderSourceBoq.getId();
								  String serModParNumString ="";
								  
								  String querySerialModelPartNum = "SELECT id,serialNum,itemModel,itemPartNumber,reconciled  from WorkOrderSourceSerial t  where t.wosID like :param1 ORDER BY id "; 					
								  Query resultSerialModelPartNum =session.createQuery(querySerialModelPartNum);
								  resultSerialModelPartNum.setParameter("param1", WOsourceId);

								  List<Object[]> serialModelPartNumSrc = (List<Object[]>) resultSerialModelPartNum.list();
								  int reconciledSrcCount=0;

											  if(serialModelPartNumSrc.size() !=0) {
												  for (Object[] serialElementSrc : serialModelPartNumSrc) {
													  
													  serModParNumString +=serialElementSrc[0].toString()  +","+ serialElementSrc[1].toString()  +","+ serialElementSrc[2].toString() +","+ serialElementSrc[3].toString() +","+ serialElementSrc[4].toString().charAt(0)+",;";
													  char checkrec=serialElementSrc[4].toString().charAt(0);	
													 
													 if(checkrec=='1') {
														 reconciledSrcCount++;
													  }
												  }
												  if(serialModelPartNumSrc.size()==reconciledSrcCount) {
													  WorkOrderSourceBoq.setReconciled('1');
												  }
												  WorkOrderSourceBoq.setSerialNo(serModParNumString);
											  }
							}
						    model.addAttribute("ListWorkOrderItem", mapper.writeValueAsString(ListWorkOrderSourceBoq));	
					        
						}else {
							 model.addAttribute("ListWorkOrderItem","addNew");
						}
				       
				        
					    
					    String queryWorkOrderDes = "select t.workOrdId as workOrdId, t.wodCreationDate as wodCreationDate, t.wodLastModifieddate as wodLastModifieddate,t.workOrdSrcId as workOrdSrcId, t.itemCode as itemCode, t.itemName as itemName, t.itemModel as itemModel,t.itemPartNumber as itemPartNumber, t.currentQty as currentQty , t.qty as qty,t.fromWare as fromWare, t.toWare as toWare, t.nodeId as nodeId, t.nodeName as nodeName,t.grId as grId, t.prId as prId, t.poId as poId, t.cipId as cipId, t.status as status, t.reconciled as reconciled,t.barcodeScanned as barcodeScanned,t.Id as id,t.serialNo as serialNo from WorkOrderDestination t where t.workOrdId like :param1";
					    Query resultWorkOrderDes = session.createQuery(queryWorkOrderDes);
					    resultWorkOrderDes.setParameter("param1", workOrderId);
					    List<WorkOrderDestination> ListWorkOrderDestBoq = (List<WorkOrderDestination>) resultWorkOrderDes.setResultTransformer(Transformers.aliasToBean(WorkOrderDestination.class)).list();
					    
					   if (ListWorkOrderDestBoq.size() != 0) {				    	
							for (WorkOrderDestination WorkOrderDesBoq : ListWorkOrderDestBoq) {
								
								String WOdestinationId = WorkOrderDesBoq.getId();
								String serModParNumString ="";
								String querySerialModelPartNum = "SELECT id,serialNum,itemModel,itemPartNumber,reconciled  from WorkOrderDestinationSerial t  where t.wodID like :param1 ORDER BY id "; 					
							
								  Query resultSerialModelPartNum =session.createQuery(querySerialModelPartNum);
								  resultSerialModelPartNum.setParameter("param1", WOdestinationId);
								  
								  List<Object[]> serialModelPartNumDes = (List<Object[]>) resultSerialModelPartNum.list();
								  int reconciledDesCount=0;

											  if(serialModelPartNumDes.size() !=0) {
												  for (  Object[] serialElementDes : serialModelPartNumDes) {
												 serModParNumString +=serialElementDes[0].toString()  +","+ serialElementDes[1].toString()  +","+ serialElementDes[2].toString() +","+ serialElementDes[3].toString() +","+ serialElementDes[4].toString().charAt(0)+",;";
												char checkrec=serialElementDes[4].toString().charAt(0);	
												 
												 if(checkrec=='1') {
													 reconciledDesCount++;
												  }
												  }	  
												  if(serialModelPartNumDes.size()==reconciledDesCount) {
													  WorkOrderDesBoq.setReconciled('1');
												  }
												 WorkOrderDesBoq.setSerialNo(serModParNumString);
											  }	
												 model.addAttribute("ListWorkOrderDestItem", mapper.writeValueAsString(ListWorkOrderDestBoq));
							}
						}else {
							 model.addAttribute("ListWorkOrderDestItem", "addNew");	
							 
						}
				       	
					}//End if WorkOrdId != null
					
					    
		  
			}catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in WorkOrderFormView due to \n "+ exceptionAsString);
				logger.info("Error in WorkOrderFormView due to \n "+ exceptionAsString);
			}finally {
				if (session!=null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}	
			
				
				
			}
			return "WorkOrderFormView";
		}
	}
	


@RequestMapping(value = "/WorkOrderFormSave", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> WorkOrderFormSave(@ModelAttribute WorkOrderSourceParams woParameters,@ModelAttribute WorkOrderDestParams wodParameters,Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

	Map<String, Object> rtn = new LinkedHashMap<>();
	
if(LoginServices.checkSession(request, response).equals("redirect:/")) {
	rtn.put("Login", "redirect:/");
	return rtn;
}else {
	
	String workOrderId="";
	Date date = new Date();
	Calendar calendar = new GregorianCalendar();
	calendar.setTime(date);
	int year = calendar.get(Calendar.YEAR);
	
	DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	Date date1 = null;
	Date date3 = null;
	Timestamp CreationDate;	
	
	DateFormat rformatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	Date rdate = new Timestamp(System.currentTimeMillis());

	Timestamp lastModifiedDate = new Timestamp(rdate.getTime());
	
	String executionDate = request.getParameter("woExecutionDate");
	Timestamp woExecutionDate;

	 
	String fromWareName=request.getParameter("woFromWareName");
	String toWareName= request.getParameter("woToWareName");
	String fromWareSiteId=request.getParameter("woFromWareSiteId");
	String toWareSiteId= request.getParameter("woToWareSiteId");
	String fromWareId= request.getParameter("woFromWare");
	String toWareId=request.getParameter("woToWare");
	String workOrderPurpose=request.getParameter("purpose");
	String workOrderSrcQTY=request.getParameter("wostotalQty");
	String workOrderDesQTY=request.getParameter("wodtotalQty");
	String workOrderStatus=request.getParameter("Status");
	String[] DelSrcItem = request.getParameterValues("slctDelSrcItem[]");
	String[] DelDesItem = request.getParameterValues("slctDelDesItem[]");

	session = AlmDbSession.getInstance().getSession();
	if(session != null && session.isOpen()) {
	tx = session.beginTransaction();
	

	
	try {
		date3 = formatter1.parse(executionDate);
		woExecutionDate = new Timestamp(date3.getTime());
		workOrderId = request.getParameter("workOrderId");
		
		if(workOrderId.equalsIgnoreCase("") || workOrderId == null) {
			synchronized (this) {						
				workOrderId = "WO_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT WORK_ORDER FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET WORK_ORDER = WORK_ORDER + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
		}
		
		System.out.println("workOrderId :: "+workOrderId);
		if (request.getParameter("wocreationDate") == "") {
			 date1 =new Timestamp(System.currentTimeMillis());
			 CreationDate = new Timestamp(date1.getTime());
			 System.out.println("The create empty date is " + CreationDate);
			} else {
			String createdDate = request.getParameter("wocreationDate");
			date1 = formatter1.parse(createdDate);
			CreationDate = new Timestamp(date1.getTime());
			}


		if (DelSrcItem !=null) {
			String SrcQuery = "delete from WorkOrderSource t where t.id IN (:param1)";
			Query SrcDeleteQuery = session.createQuery(SrcQuery);
			SrcDeleteQuery.setParameterList("param1", DelSrcItem);
			SrcDeleteQuery.executeUpdate();
			
			String serialSrcQuery = "delete from WorkOrderSourceSerial t where t.wosID IN (:param1)";
			Query serialSrcDeleteQuery = session.createQuery(serialSrcQuery);
			serialSrcDeleteQuery.setParameterList("param1", DelSrcItem);
			serialSrcDeleteQuery.executeUpdate();
		}

		if (DelDesItem !=null) {
			
			String desQuery = "delete from WorkOrderDestination t where t.id IN (:param1)";
			Query DesDeleteQuery = session.createQuery(desQuery);
			DesDeleteQuery.setParameterList("param1", DelDesItem);
			DesDeleteQuery.executeUpdate();

			String serialDesQuery= "delete from WorkOrderDestinationSerial d where d.wodID IN (:param1)";
			Query serialDesDeleteQuery = session.createQuery(serialDesQuery);
			serialDesDeleteQuery.setParameterList("param1", DelDesItem);
			serialDesDeleteQuery.executeUpdate();
			
		}
	
		WorkOrder workOrderObj= new WorkOrder();
		workOrderObj.setWorkOrdId(workOrderId);
		workOrderObj.setWoCreationDate(CreationDate);
		workOrderObj.setWoLastModifieddate(lastModifiedDate);
		workOrderObj.setPurpose(workOrderPurpose);
		workOrderObj.setExecutionDate(woExecutionDate);
		workOrderObj.setFromWare(fromWareId);
		workOrderObj.setToWare(toWareId);
		workOrderObj.setSiteIdSource(fromWareSiteId);
		workOrderObj.setSiteIdDest(toWareSiteId);
		workOrderObj.setWarehouseNameDest(toWareName);
		workOrderObj.setWarehouseSourceName(fromWareName);
		workOrderObj.setWostotalQty(Float.parseFloat(workOrderSrcQTY));
		workOrderObj.setWodtotalQty(Float.parseFloat(workOrderDesQTY));
		workOrderObj.setWoStatus(workOrderStatus);
		session.saveOrUpdate(workOrderObj);

		
		String srcFullItemCode="";
		String srcItemCode="";
		String srcItemName="";
		int m;
		String WosId;

		if(woParameters.getParameter1().size()!=0) {
			WorkOrderSource workOrderSrcObj;

				for(Map<String, Object> Wo_Map : woParameters.getParameter1()) {
					
					workOrderSrcObj=new WorkOrderSource();					
					srcFullItemCode=Wo_Map.get("ItemCode").toString();
					
					 if(!srcFullItemCode.isEmpty())
					 {
					m=srcFullItemCode.indexOf(":");
					srcItemCode = srcFullItemCode.substring(0, m);
					srcItemName = srcFullItemCode.substring(m+1, srcFullItemCode.length());
			}
					
					if (StringUtils.equalsIgnoreCase(Wo_Map.get("WosId").toString(), "0")) 
					{
						synchronized (this) {						
							WosId = "WOS_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT WORK_ORDER_SOURCE FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET WORK_ORDER_SOURCE = WORK_ORDER_SOURCE + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
						
					 }else {
						  WosId = Wo_Map.get("WosId").toString();  
					}
					workOrderSrcObj.setId(WosId);
					workOrderSrcObj.setCreationDate(new Timestamp(System.currentTimeMillis()));
					workOrderSrcObj.setLastModifieddate(new Timestamp(System.currentTimeMillis()));
					workOrderSrcObj.setItemCode(srcItemCode);
					workOrderSrcObj.setItemName(srcItemName);
					workOrderSrcObj.setItemModel(Wo_Map.get("ItemModel").toString());
					workOrderSrcObj.setItemPartNumber(Wo_Map.get("ItemPartNumber").toString());
					workOrderSrcObj.setCurrentQty(Float.parseFloat(Wo_Map.get("CurrentQty").toString()));					
					workOrderSrcObj.setQty(Float.parseFloat(Wo_Map.get("Qty").toString()));
					workOrderSrcObj.setWorkOrdId(workOrderId);
					workOrderSrcObj.setFromWare(Wo_Map.get("FromWarehouse").toString());
					workOrderSrcObj.setToWare(Wo_Map.get("ToWarehouse").toString());
					workOrderSrcObj.setNodeId(Wo_Map.get("NodeId").toString());
					workOrderSrcObj.setNodeName(Wo_Map.get("NodeName").toString());
					workOrderSrcObj.setStatus(Wo_Map.get("Status").toString());
					workOrderSrcObj.setGrId(Wo_Map.get("GrId").toString());
					workOrderSrcObj.setPrId(Wo_Map.get("PrId").toString());
					workOrderSrcObj.setPoId(Wo_Map.get("PoId").toString());
					workOrderSrcObj.setCipId(Wo_Map.get("CipId").toString());
					workOrderSrcObj.setBarcodeScanned(Wo_Map.get("BarCodeScanned").toString().charAt(0));
					workOrderSrcObj.setReconciled(Wo_Map.get("Reconciled").toString().charAt(0));
					
					// ---- smpnrsrc > Means ( Serial,Model,PartNumber,Reconciled For Source )
					String smpnrsrc = Wo_Map.get("serialNum").toString();
					
					if(smpnrsrc!="") {

						if (smpnrsrc.contains(",;")) {
							String[] smpnrSrcRows = smpnrsrc.split(",;");
							WorkOrderSourceSerial serialnumDetailSrc;

							for (String smpnrSrcRow : smpnrSrcRows) {
								serialnumDetailSrc = new WorkOrderSourceSerial();

								String smpnrId="";
								String[] smpnrSrcCols = smpnrSrcRow.split(",");
								if (StringUtils.equalsIgnoreCase(smpnrSrcCols[0], "0")) 
								{
									synchronized (this) {						
										smpnrId = "WOSS_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT WORK_ORDER_SOUCE_SERIAL FROM SEQ_TABLE").uniqueResult().toString());	
										query = session.createSQLQuery("UPDATE SEQ_TABLE SET WORK_ORDER_SOUCE_SERIAL = WORK_ORDER_SOUCE_SERIAL + 1 ");
										query.executeUpdate();
										session.createSQLQuery("commit").executeUpdate();
										}
									// smpnrId= "WOSS_"+year+"_" +appConfig.getSequenceNbr(58);
									serialnumDetailSrc.setId(smpnrId);
								  } else {
									smpnrId = smpnrSrcCols[0].toString();						
									  }
								serialnumDetailSrc.setId(smpnrId);	
								serialnumDetailSrc.setWosID(WosId);
								serialnumDetailSrc.setItemCode(srcItemCode);
								serialnumDetailSrc.setSerialNum(smpnrSrcCols[1]);
								serialnumDetailSrc.setItemModel(smpnrSrcCols[2]);
								serialnumDetailSrc.setItemPartNumber(smpnrSrcCols[3]);
								serialnumDetailSrc.setReconciled(smpnrSrcCols[4].charAt(0));
								session.saveOrUpdate(serialnumDetailSrc);

							}
						}
					}
					session.saveOrUpdate(workOrderSrcObj);
				}
			
		}


		String desFullItemCode="";
		String desItemCode="";
		String desItemName="";
		int n;
		String WodId;
		if(wodParameters.getParameter2().size()!= 0) {
			WorkOrderDestination workOrderDesObj;
		
			for(Map<String, Object> Wod_Map : wodParameters.getParameter2()) {
				
				workOrderDesObj=new WorkOrderDestination();
				desFullItemCode=Wod_Map.get("ItemCode").toString();
				
				 if(!desFullItemCode.isEmpty())
				 	{
				n=desFullItemCode.indexOf(":");
				desItemCode = desFullItemCode.substring(0, n);
				desItemName = desFullItemCode.substring(n+1, desFullItemCode.length());				
		}

				if (StringUtils.equalsIgnoreCase(Wod_Map.get("WodId").toString(), "0")) 
				{
					// --- Create Id Sequence for work order Destination Item
					
					synchronized (this) {						
						WodId = "WOD_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT WORK_ORDER_DESTINATION FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET WORK_ORDER_DESTINATION = WORK_ORDER_DESTINATION + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}
					
				  } else {
					  WodId = Wod_Map.get("WodId").toString();
					  
				  }
				workOrderDesObj.setId(WodId);
				workOrderDesObj.setWodCreationDate(new Timestamp(System.currentTimeMillis()));
				workOrderDesObj.setWodLastModifieddate(new Timestamp(System.currentTimeMillis()));
				workOrderDesObj.setItemCode(desItemCode);
				workOrderDesObj.setItemName(desItemName);
				workOrderDesObj.setItemModel(Wod_Map.get("ItemModel").toString());
				workOrderDesObj.setItemPartNumber(Wod_Map.get("ItemPartNumber").toString());
				workOrderDesObj.setCurrentQty(Float.parseFloat(Wod_Map.get("CurrentQty").toString()));
				workOrderDesObj.setQty(Float.parseFloat(Wod_Map.get("Qty").toString()));
				workOrderDesObj.setWorkOrdId(workOrderId);
				workOrderDesObj.setFromWare(Wod_Map.get("FromWarehouse").toString());
				workOrderDesObj.setToWare(Wod_Map.get("ToWarehouse").toString());
				workOrderDesObj.setNodeId(Wod_Map.get("NodeId").toString());
				workOrderDesObj.setNodeName(Wod_Map.get("NodeName").toString());
				workOrderDesObj.setStatus(Wod_Map.get("Status").toString());
				workOrderDesObj.setGrId(Wod_Map.get("GrId").toString());
				workOrderDesObj.setPrId(Wod_Map.get("PrId").toString());
				workOrderDesObj.setPoId(Wod_Map.get("PoId").toString());
				workOrderDesObj.setCipId(Wod_Map.get("CipId").toString());
				workOrderDesObj.setBarcodeScanned(Wod_Map.get("BarCodeScanned").toString().charAt(0));
				workOrderDesObj.setReconciled(Wod_Map.get("Reconciled").toString().charAt(0));		
				
				// ---- smpnrdes > Means ( Serial,Model,PartNumber,Reconciled For Destination BOQ )
				String smpnrdes = Wod_Map.get("serialNum").toString();
				if(smpnrdes!="") {

					if (smpnrdes.contains(",;")) {
						String[] smpnrDesRows = smpnrdes.split(",;");
						WorkOrderDestinationSerial serialnumDetailDes;
						
						for (String smpnrDesRow : smpnrDesRows) {
							serialnumDetailDes = new WorkOrderDestinationSerial();
							String smpnrId="";
							String[] smpnrDesCols = smpnrDesRow.split(",");
							if (StringUtils.equalsIgnoreCase(smpnrDesCols[0], "0")) 
							{
								synchronized (this) {						
									smpnrId = "WOSD_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT WORK_ORDER_DESTINATION_SERIAL FROM SEQ_TABLE").uniqueResult().toString());	
									query = session.createSQLQuery("UPDATE SEQ_TABLE SET WORK_ORDER_DESTINATION_SERIAL = WORK_ORDER_DESTINATION_SERIAL + 1 ");
									query.executeUpdate();
									session.createSQLQuery("commit").executeUpdate();
									}
								 //smpnrId= "WOSD_"+year+"_" +appConfig.getSequenceNbr(58);
							  } else {
								smpnrId = smpnrDesCols[0].toString();						
								  }		
							serialnumDetailDes.setId(smpnrId);	
							serialnumDetailDes.setWodID(WodId);
							serialnumDetailDes.setItemCode(desItemCode);
							serialnumDetailDes.setSerialNum(smpnrDesCols[1]);
							serialnumDetailDes.setItemModel(smpnrDesCols[2]);
							serialnumDetailDes.setItemPartNumber(smpnrDesCols[3]);
							serialnumDetailDes.setReconciled(smpnrDesCols[4].charAt(0));
							session.saveOrUpdate(serialnumDetailDes);

						}
		
					}
				}		
				session.saveOrUpdate(workOrderDesObj);
			}	

		}
		
		
	}catch (Exception e) {
		sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		exceptionAsString = sw.toString();
		logger.finest("Error in WorkOrderSave due to \n "+ exceptionAsString);
		logger.info("Error in WorkOrderSave due to \n "+ exceptionAsString);
	}finally {
		if(session !=null && session.isOpen()) {
			tx.commit();
			session.close();
		}
	}
		
	}
	
	//rtn.put("wolastModifieddate", rformatter.format(rdate).toString());
	rtn.put("wolastModifieddate", lastModifiedDate.toString());

	rtn.put("Sohaib", "SaveDone");
	rtn.put("WOID", workOrderId);	
}
	
return rtn; 
}


@RequestMapping(value = "/WoFormDelete", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> WoFormDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

Map<String, Object> rtn = new LinkedHashMap<>();
if(LoginServices.checkSession(request, response).equals("redirect:/")) {
	rtn.put("Login", "redirect:/");
	return rtn;
}else {
	session = AlmDbSession.getInstance().getSession();
	if(session != null && session.isOpen()) {
		tx = session.beginTransaction();
		try {
			
			String[] valuesWorkOrderIds = request.getParameterValues("workOrdId[]");
			System.out.println(mapper.writeValueAsString(valuesWorkOrderIds));
			for(int i = 0;i<valuesWorkOrderIds.length;i++) {
				System.out.println(valuesWorkOrderIds[i]);
				query = session.createSQLQuery("DELETE WORK_ORDER WHERE ID='"+valuesWorkOrderIds[i]+"'");
				query.executeUpdate();
				
				query = session.createSQLQuery("DELETE WORK_ORDER_BOQ_DESTINATION WHERE WO_ID='"+valuesWorkOrderIds[i]+"'");
				query.executeUpdate();
				
				query = session.createSQLQuery("DELETE WORK_ORDER_BOQ_SOURCE WHERE WO_ID='"+valuesWorkOrderIds[i]+"'");
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

///to be deleted
/*@RequestMapping(value = "/WoFormDeleteParam", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> WoFormDeleteParam(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

Map<String, Object> rtn = new LinkedHashMap<>();
if(LoginServices.checkSession(request, response).equals("redirect:/")) {
	rtn.put("Login", "redirect:/");
	return rtn;
}
else {

	Configuration cfg = new Configuration().configure();
	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
	SessionFactory sf = cfg.buildSessionFactory(builder.build());
	Session session = sf.openSession();
	Transaction tx = session.beginTransaction();
	
	try {
		
		String[] valuesWorkOrderIds = request.getParameterValues("workOrdId[]");
		
		String deleteWorkOrderStmt = "delete WorkOrder t where t.workOrdId IN (:param1)";
		Query resultWODQ = session.createQuery(deleteWorkOrderStmt);
		resultWODQ.setParameterList("param1", valuesWorkOrderIds);
		resultWODQ.executeUpdate();
		
		String deleteWorkOrderSRCStmt = "delete WorkOrderSource t where t.workOrdId IN (:param1)";
		Query queryWOSD = session.createQuery(deleteWorkOrderSRCStmt);
		queryWOSD.setParameterList("param1", valuesWorkOrderIds);
		queryWOSD.executeUpdate();
		
		String deleteWorkOrderDESStmt = "delete WorkOrderDestination t where t.workOrdId IN (:param1)";
		Query queryWODD= session.createQuery(deleteWorkOrderDESStmt);
		queryWODD.setParameterList("param1", valuesWorkOrderIds);
		queryWODD.executeUpdate();
		

	}catch (Exception e) {
		logger.info("Error in Controller Deleting WorkOrder, WorkOrder Source, WorkOrder Destination: "+e.getMessage());
	} finally {
		if(session !=null && session.isOpen()) {
			tx.commit();
			session.close();
		}
	}
	rtn.put("YaraTest", "DeleteDone");
	return rtn;
}
}*/


@RequestMapping(value = "/GetAllGoodsReceivedItem", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllGoodsReceivedItem(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){

	Map<String, Object> rtn = new LinkedHashMap<>();
	if(LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	}else {
		List<PurchaseRequest> listrequest = new ArrayList<PurchaseRequest>();
		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			String requestName = "%" + request.getParameter("GR") + "%";
			String Item_code = request.getParameter("Item_code");
			Query q = null; String query = "";
			
				query= "select a.ID ,a.supplierID,a.supplierName from GoodsReceived a, GoodsReceivedItem b where (a.ID like :param1 or a.supplierID like :param1 or a.supplierName like :param1 ) and b.ItemCode =:param2 and a.ID = b.GRid";
				q = session.createQuery(query);
				q.setString("param1", requestName);
				q.setString("param2", Item_code);
			

			q.setFirstResult(0);
			q.setMaxResults(40);
			listrequest = q.list();
			model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
			rtn.put("Listreq", listrequest);
			
		}catch (Exception e) {
			logger.info("");
		}finally {
			if(session !=null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
		return rtn;
	}
}


@RequestMapping(value = "/GetAllPO", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllPO(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){

	Map<String, Object> rtn = new LinkedHashMap<>();
	if(LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	}else {
		
		//List<PurchaseOrder> listPo = new ArrayList<PurchaseOrder>();

		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		String PoID = "%" + request.getParameter("PO") + "%";
		String Item_code = request.getParameter("Item_code");
		ObjectMapper mapper = new ObjectMapper();

		try {
			Query q = session.createQuery(
					"select a.ID, a.supplier, a.TotalQty from PurchaseOrder a, PurchaseOrderItem b where (a.ID like :param1 or a.supplier like :param1 or a.TotalQty like :param1) and b.ItemCode =:param2 and a.ID = b.POId");

			q.setString("param1", PoID);
			q.setString("param2", Item_code);
			q.setFirstResult(0);
			q.setMaxResults(40);
			List<Object[]> listResult = q.list();

			model.addAttribute("listResult", mapper.writeValueAsString(listResult));
			rtn.put("ListPos1", listResult);
			
		}catch (Exception e) {
			logger.info("");
		}finally {
			if(session !=null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
		return rtn;
	}
}


@RequestMapping(value = "/GetAllPurchaseRequestsItem", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllPurchaseRequestsItem(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

	Map<String, Object> rtn = new LinkedHashMap<>();
	if(LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	}
	else {
		List<PurchaseRequest> listrequest = new ArrayList<PurchaseRequest>();

		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String requestName = "%" + request.getParameter("PR") + "%";
		String Item_code = request.getParameter("Item_code");
		ObjectMapper mapper = new ObjectMapper();

		try {
			Query q = session.createQuery("select a.ID ,a.supplier,a.supplierName, a.TotalQty from PurchaseRequest a, PurchaseRequestItem b where (a.ID like :param1 or a.supplier like :param1 or a.TotalQty like :param1 ) and b.ItemCode =:param2 and a.ID = b.PRQId");
			q.setString("param1", requestName);
			q.setString("param2", Item_code);
			q.setFirstResult(0);
			q.setMaxResults(40);
			listrequest = q.list();
			
			model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
			rtn.put("Listreq", listrequest);
			
		}catch (Exception e) {
			logger.info("");
		}finally {
			if(session !=null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
		return rtn;
	}
}


@RequestMapping(value = "/GetAllCIPItem", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllCIPItem(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
Map<String, Object> rtn = new LinkedHashMap<>();

if(LoginServices.checkSession(request, response).equals("redirect:/")) {
	rtn.put("Login", "redirect:/");
	return rtn;
} else {
	
	List<PurchaseRequest> listrequest = new ArrayList<PurchaseRequest>();
	Configuration cfg = new Configuration().configure();
	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
	SessionFactory sf = cfg.buildSessionFactory(builder.build());
	Session session = sf.openSession();
	Transaction tx = session.beginTransaction();
	ObjectMapper mapper = new ObjectMapper();
	String requestName = "%" + request.getParameter("CIP") + "%";
	String Item_code = request.getParameter("Item_code");

	try {
		
		Query q = session.createQuery("select a.cipID ,a.cipitemCode, a.cipitemName from CapitalInProgress a where a.cipID like :param1 and a.cipitemCode like :param2 ");
		q.setString("param1", requestName);
		q.setString("param2", Item_code);
		listrequest = q.list();
		q.setFirstResult(0);
		q.setMaxResults(40);

		model.addAttribute("ListCIP", mapper.writeValueAsString(listrequest));
		rtn.put("ListCIP", listrequest);

	}catch (Exception e) {
		logger.info("");
	}finally {
		if(session !=null && session.isOpen()) {
			tx.commit();
			session.close();
		}
	}
	return rtn;
}
}


@RequestMapping(value = "/GetNodeIdBOQ", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetNodeIdBOQ(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> rtn = new LinkedHashMap<>();
	if(LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	} else {
		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		ObjectMapper mapper = new ObjectMapper();
		String node_id="%"+request.getParameter("NodeId")+"%";
		
		try {
			List<Object[]> listnodeid;
			
			Query q= session.createSQLQuery("Select distinct NODE_ID,NODE_NAME from NODE_ACTIVE where NODE_ID like :param1" );
			q.setString("param1", node_id);
			listnodeid = q.list();	
			model.addAttribute("ListNodeId", mapper.writeValueAsString(listnodeid));
			rtn.put("ListNodeId", listnodeid);
			
		}catch (Exception e) {
			logger.info("");
		}finally {
			if(session !=null && session.isOpen()) {
				tx.commit();
				session.close();
			}
	}
}
	return rtn;
}



//Get Node Name mohammad turkieh

@RequestMapping(value = "/GetNodeNameBOQ", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetNodeNameBOQ(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){
	Map<String, Object> rtn = new LinkedHashMap<>();
	if(LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	} else {
		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		ObjectMapper mapper = new ObjectMapper();
		String node_name="%"+request.getParameter("NodeName")+"%";
		
		try {
			List<Object[]> listnodename;

			Query q= session.createSQLQuery("Select distinct NODE_NAME,NODE_ID from NODE_ACTIVE where NODE_NAME like :param1" );
			q.setString("param1", node_name);
			listnodename = q.list();
			tx.commit();
			session.close();
			model.addAttribute("ListNodeName", mapper.writeValueAsString(listnodename));
			rtn.put("ListNodeName", listnodename);
			
		}catch (Exception e) {
			logger.info("");
		}finally {
			if(session !=null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
		return rtn;
	}		
}

@RequestMapping(value = "/GetAllWorkOrders", method = RequestMethod.GET)
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
				query= session.createSQLQuery("Select ID,WAREHOUSE_NAME_SOURCE,SITE_ID_SOURCE,WAREHOUSE_NAME_DEST,SITE_ID_DEST from WORK_ORDER"
				+ " where upper(ID) like '%" + workOrder + "%' OR upper(WAREHOUSE_NAME_SOURCE) LIKE upper('%" + workOrder + "%') "
				+ " OR upper(SITE_ID_SOURCE) LIKE upper('%" + workOrder + "%') OR upper(WAREHOUSE_NAME_DEST) LIKE upper('%" + workOrder + "%') "
				+ " OR upper(SITE_ID_DEST) LIKE upper('%" + workOrder + "%')" );
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