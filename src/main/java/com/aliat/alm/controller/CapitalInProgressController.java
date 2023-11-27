package com.aliat.alm.controller;

import java.io.IOException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.CapitalInProgress;
import com.aliat.alm.models.Capital_InProgressListView;
import com.aliat.alm.models.Item;
import com.aliat.alm.models.PurchaseOrderItem;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CapitalInProgressController {

	
	
	
	@Autowired
	Notify notifications;
	
	@Autowired
	Form form;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static Session session = null;
	private static Transaction tx = null;
	private static Query query = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static String POI_Id=null,PR_Id=null;
	private static Float PO_CIP_TotQty;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/CapitalInProgressListView", method = RequestMethod.GET)
	public String CapitalInProgressListView(Locale locale, Model model,HttpServletRequest request,HttpServletResponse response) 
			throws JsonGenerationException, JsonMappingException, IOException {
		//logger.info("Welcome home! The client locale is {}.", locale);
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				try {
		
					List<Capital_InProgressListView> listCIP = new ArrayList<Capital_InProgressListView>();
		
					String str = "select CIP_ID as cipID, CIP_ID as capitalID, ITEM_CODE as cipItemCode, ITEM_NAME as cipItemName,"
							+ "TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as cipLastModifiedDate, PO_ID as PoID, SUPPLIER_ID as supplierID, SUPPLIER_NAME as supplierName "
							+ "from CAPITAL_IN_PROGRESS order by LAST_MODIFIED_DATE DESC";
					
					query = session.createSQLQuery(str);

					listCIP =  ((SQLQuery) query)
							.addScalar("cipID").addScalar("capitalID").addScalar("cipItemCode").addScalar("cipItemName").addScalar("cipLastModifiedDate")
							.addScalar("supplierID").addScalar("supplierName").addScalar("PoID")
			       			.setResultTransformer(Transformers.aliasToBean(Capital_InProgressListView.class)).list();
	
					System.out.println("CIP List = "+mapper.writeValueAsString(listCIP));
					model.addAttribute("ListGridTable", mapper.writeValueAsString(listCIP));
					
				} catch (Exception e) {
					logger.info("Error on Capital in Progress ListView with a message : " + e);
					e.printStackTrace();
					model.addAttribute("ListGridTable", "");
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			
			return "CapitalInProgressListView";
		}
	
		
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredCapitalListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredCapitalListView(Locale locale, Model model, HttpServletRequest request,
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

				String startdate, enddate, itemcode, itemname,itemmodel,supplierid,suppliername;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				itemcode = request.getParameter("itemcode");
				itemname = request.getParameter("itemname");
				itemmodel = request.getParameter("itemmodel");
				supplierid = request.getParameter("supplierid");
				suppliername = request.getParameter("suppliername");
				
				
				List<String> listCapital= new ArrayList<String>();

				String str = "select 1 as chkBox , CIP_ID as cipID , ITEM_CODE as cipitemCode , ITEM_NAME as cipitemName ,TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as cipLastModifiedDate , SUPPLIER_ID as supplierID, SUPPLIER_NAME as supplierName ,  " 
												+ " PO_ID as PoId " 
											    + " from CAPITAL_IN_PROGRESS " ;
				
				
				if (startdate != null && enddate != null) {
					str = str + " where CREATED_DATE between TO_DATE('" + startdate
							+ "','YYYY-MM-DD') and TO_DATE('" + enddate + "','YYYY-MM-DD')";
				}
				if (itemcode != null && !itemcode.equalsIgnoreCase("")) {

					str = str + " and (upper(ITEM_CODE) LIKE upper('%" + itemcode + "%'))";
				}

				if (itemname != null && !itemname.equalsIgnoreCase("")) {

					str = str + " and (upper(ITEM_NAME) LIKE upper('%" + itemname + "%') )";
				}
				if (itemmodel != null && !itemmodel.equalsIgnoreCase("")) {

					str = str + " and (upper(ITEM_MODEL) LIKE upper('%" + itemmodel + "%'))";
				}

				if (supplierid != null && !supplierid.equalsIgnoreCase("")) {

					str = str + " and (upper(SUPPLIER_ID) LIKE upper('%" + supplierid + "%') )";
				}
				if (suppliername != null && !suppliername.equalsIgnoreCase("")) {

					str = str + " and (upper(SUPPLIER_NAME) LIKE upper('%" + suppliername + "%') )";
				}
				str = str + " ORDER BY LAST_MODIFIED_DATE DESC ";
				Query query = session.createSQLQuery(str);
				

				
				listCapital = query.list();
				
			
				rtn.put("listCapital",listCapital);
			
			} catch (Exception e) {
				logger.info("Error in showing the filtered Capital in progress list view with a message :" + e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/CapitalInProgressFormView", method = RequestMethod.GET)
	public String CapitalInProgressFormView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException, org.json.simple.parser.ParseException {
		//logger.info("Welcome home! The client locale is {}.", locale);
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}else {
			session = AlmDbSession.getInstance().getSession();
			String itemsList,navAction,cipCode,ItemName,queryStmt;
			if (session != null && session.isOpen()) {
				
				
				String[] result=null;
				int SelectedIndex = 0;
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				try {
					 itemsList = request.getParameter("cipList");
					 navAction = "2";
			         result=new String[4];
			         Date date;
			         SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					
					
						
					 cipCode = request.getParameter("cipID");
					 ItemName = request.getParameter("cipitemCode");
					 navAction= request.getParameter("NavAction");

					
					
					
					// to open item when click on ADD from item List
					if (cipCode == null) { 
					    date = new Timestamp(System.currentTimeMillis());
						model.addAttribute("cipcreatedDate", formatter.format(date).toString());
						model.addAttribute("ciplastModifiedDate", formatter.format(date).toString());
						model.addAttribute("docStatus", "addNew");
						model.addAttribute("CIPTable", "addNew");
						model.addAttribute("docStatus", "addNew");
						model.addAttribute("SelectedIndex", "addNew");
						model.addAttribute("CIPCount", "addNew");
						return "CapitalInProgressFormView"; 

					}
			
					result = form.NavigationNP(session,"CAPITAL_IN_PROGRESS","CIP_ID",cipCode,"LAST_MODIFIED_DATE",navAction);		

					SelectedIndex= Integer.parseInt(result[1]);
					cipCode=result[2];

					model.addAttribute("SelectedIndex", SelectedIndex);
					model.addAttribute("CIPCount", Integer.parseInt(result[0]));

					//get the CIP according to a given cipID
					CapitalInProgress assetcip = (CapitalInProgress) session.get(CapitalInProgress.class, cipCode);
				
					
					
						// get all details of item found in item table under itemCode
						Item itm = (Item) session.get(Item.class, assetcip.getCipitemCode());

							if (assetcip != null) {
						
								model.addAttribute("cipID", assetcip.getCipID());
								model.addAttribute("cipitemCode", assetcip.getCipitemCode());
								model.addAttribute("totalQty", assetcip.getTOTALQTY());
								model.addAttribute("poID", assetcip.getPoId());
								model.addAttribute("supplierID", assetcip.getSupplierID());
								model.addAttribute("supplierName", assetcip.getSupplierName());
								
								//get the Related Orders in the CIP Related Orders Tab
								POI_Id= assetcip.getPoItemId();
								PR_Id=assetcip.getPrId();
								PO_CIP_TotQty=assetcip.getTOTALQTY();
								//queryStmt = "select t.ItemCode || ':'|| t.ItemName ,t.ItemModel,t.ItemPartNb,t.Qty, t.Rate,t.DiscAmnt ,t.Tax1, t.NetRate, t.Total, t.TotalAt,t.GrNo, t.iPrNo, t.ArNo, t.iCIPNo,t.FarNo, t.pordItemId  from PurchaseOrderItem t where t.pordItemId like :param1";
								String str = "SELECT ITEM_CODE || ':'|| ITEM_NAME,ITEM_MODEL,ITEM_PART_NUMBER,QTY,RATE,DISCOUNT_AMOUNT,TAX1,NET_RATE,TOTAL,TOTAL_AT,GR_QTY,PR_QTY,AR_QTY,CIP_QTY,FAR_QTY,PO_ITEM_ID FROM PURCHASE_ORDER_ITEM WHERE PO_ITEM_ID='"+POI_Id+"'";
								//List<PurchaseOrderItem> listPurchaseOrderItem = (List<PurchaseOrderItem>) appConfig.findAllByQueryParam(PurchaseOrderItem.class, queryStmt, "param1", POI_Id);
								List<PurchaseOrderItem> listPurchaseOrderItem = session.createSQLQuery(str).list();
								//PurchaseOrderItem prOrdtitm = (PurchaseOrderItem) session.get(PurchaseOrderItem.class, POI_Id);
								model.addAttribute("CIPTable", mapper.writeValueAsString(listPurchaseOrderItem));
			

								
								if (assetcip.getCipcreatedDate() == null) {
									date = new Timestamp(System.currentTimeMillis());
								}
								else {
									date = assetcip.getCipcreatedDate();
								}
							
								model.addAttribute("cipcreatedDate", formatter.format(date).toString());
								
								if (assetcip.getCiplastModifiedDate() == null) {
									date = new Timestamp(System.currentTimeMillis());
								}
								else {
									date = assetcip.getCiplastModifiedDate();
								}
					 	    	model.addAttribute("ciplastModifiedDate", formatter.format(date).toString());
						    	model.addAttribute("cipitemName", assetcip.getCipitemName());
						    	model.addAttribute("cipunit", assetcip.getCipunit());
						    	model.addAttribute("cipitemDescription", assetcip.getCipitemDescription());
						    	model.addAttribute("cipdomain", assetcip.getCipdomain());
						    	model.addAttribute("cipitemCategory", itm.getItemCategory());
						    	model.addAttribute("cipitemType", assetcip.getCipitemType());
						    	model.addAttribute("cipcableType", assetcip.getCipcableType());
						    	model.addAttribute("cipweight", assetcip.getCipweight());
						    	model.addAttribute("cipweightUOM", assetcip.getCipweightUOM());
						    	model.addAttribute("ciplength", assetcip.getCiplength());
						    	model.addAttribute("cipwidth", assetcip.getCipwidth());
						    	model.addAttribute("cipheight", assetcip.getCipheight());
						    	model.addAttribute("cipsizeUOM", assetcip.getCipsizeUOM());
						    	if (assetcip.getCipendOfLife() == null) {
									date = new Timestamp(System.currentTimeMillis());
								}
						    	else {
						    		date = assetcip.getCipendOfLife();
						    	}
						    	model.addAttribute("cipendOfLife", formatter.format(date).toString());
						    	model.addAttribute("cipvaluationRate", assetcip.getCipvaluationRate());
						    	model.addAttribute("cipitemImage", assetcip.getCipitemImage());
						    	model.addAttribute("cipwarrantyPeriod", assetcip.getCipwarrantyPeriod());
						       	if (assetcip.getCipservice() == '1') 
						    	 		{model.addAttribute("cipservice", "checked"); }
						    	else 	{model.addAttribute("cipservice", "");}
						       	if (assetcip.getCipdisabled() == '1') 
						       			{model.addAttribute("cipdisabled", "checked"); }
						       	else 	{model.addAttribute("cipdisabled", "");}
						       	if (assetcip.getCiptech2G() == '1') 
					   			{model.addAttribute("ciptech2G", "checked"); }
						       	else 	{model.addAttribute("ciptech2G", "");}
						       	if (assetcip.getCiptech3G() == '1') 
					   			{model.addAttribute("ciptech3G", "checked"); }
						       	else 	{model.addAttribute("ciptech3G", "");}
						       	if (assetcip.getCiptech4G() == '1') 
					   			{model.addAttribute("ciptech4G", "checked"); }
						       	else 	{model.addAttribute("ciptech4G", "");}

						    }
					
						
				
			} catch (Exception e) {
				logger.info("Error on Capital in Progress ListView with a message : " + e);
				e.printStackTrace();
				
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
			
			
		
			}
			return "CapitalInProgressFormView";
		
		}
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/CapitalInProgressFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CapitalInProgressFormSave(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}else {
			String CipId,createdDate,endOfLife,supplierID,supplierName,poId,poIdItem,prID;
			Date date;
			Timestamp CreationDate,lastModifiedDate,EndOfLife;
			
			CipId = request.getParameter("cipID");;
			
			date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			


			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					 if (CipId == "")
					 {
						 synchronized (this) {						
							 CipId = "CIP_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT CAPITAL_IN_PROGRESS FROM SEQ_TABLE").uniqueResult().toString());	
								query = session.createSQLQuery("UPDATE SEQ_TABLE SET CAPITAL_IN_PROGRESS = CAPITAL_IN_PROGRESS + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
								}
						// CipId= "CIP_"+year+"_" +appConfig.getSequenceNbr(8);	
						 model.addAttribute("cipID", CipId);
					 }
					
						
				        CapitalInProgress assetcip = new CapitalInProgress();
						DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
						createdDate = request.getParameter("cipcreatedDate");
						endOfLife = request.getParameter("cipendOfLife");
						date = formatter1.parse(createdDate);
						CreationDate = new Timestamp(date.getTime());	
						lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
						date = formatter1.parse(endOfLife);
						EndOfLife = new Timestamp(date.getTime());
						supplierID=request.getParameter("supplierID");
						poId=request.getParameter("poID");
						
						
						System.out.println(supplierID+" "+poId);//+" "+supplierName+" "+poId+" "+prID);
						
						
						assetcip.setCipID(CipId);
						assetcip.setCipitemCode(request.getParameter("cipitemCode"));
						assetcip.setCipcreatedDate(CreationDate);
						assetcip.setCiplastModifiedDate(lastModifiedDate);
						assetcip.setCipitemName(request.getParameter("cipitemName"));
						assetcip.setCipunit(request.getParameter("cipunit"));
						assetcip.setCipitemDescription(request.getParameter("cipitemDescription"));
						assetcip.setCipdomain(request.getParameter("cipdomain"));
						assetcip.setCipitemCategory(request.getParameter("cipitemCategory"));
						assetcip.setCipitemType(request.getParameter("cipitemType"));
						assetcip.setCipcableType(request.getParameter("cipcableType"));
						assetcip.setCipweight(request.getParameter("cipweight"));
						assetcip.setCipweightUOM(request.getParameter("cipweightUOM"));
						assetcip.setCiplength(request.getParameter("ciplength"));
						assetcip.setCipwidth(request.getParameter("cipwidth"));
						assetcip.setCipheight(request.getParameter("cipheight"));
						assetcip.setCipsizeUOM(request.getParameter("cipsizeUOM"));
						assetcip.setCipservice(request.getParameter("cipservice").charAt(0));
						assetcip.setCipendOfLife(EndOfLife);
						assetcip.setCipvaluationRate(Float.parseFloat(request.getParameter("cipvaluationRate")));
						assetcip.setCipdisabled(request.getParameter("cipdisabled").charAt(0));
						assetcip.setCipitemImage(request.getParameter("cipitemImage"));
						assetcip.setCipwarrantyPeriod(request.getParameter("cipwarrantyPeriod"));
						assetcip.setCiptech2G(request.getParameter("ciptech2G").charAt(0));
						assetcip.setCiptech3G(request.getParameter("ciptech3G").charAt(0));
						assetcip.setCiptech4G(request.getParameter("ciptech4G").charAt(0));
						assetcip.setPoId(request.getParameter("poID"));
						assetcip.setPoItemId(POI_Id);
						assetcip.setTOTALQTY(PO_CIP_TotQty);
						assetcip.setSupplierID(request.getParameter("supplierID"));
						assetcip.setSupplierName(request.getParameter("supplierName"));
						assetcip.setPrId(PR_Id);
						assetcip.setGrId(request.getParameter(assetcip.getGrId()));

						session.saveOrUpdate(assetcip);
						tx.commit();
						session.close();
						rtn.put("ciplastModifiedDate", formatter1.format(date).toString());
						rtn.put("CIPID", CipId);
						
				} catch (Exception e) {
					logger.info("Error in saving Asset Register with a message : " + e);
					e.printStackTrace();
				}

				finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
				
			}else {
				
			}

		}
	
	

		
		return rtn;
	}
	
	@RequestMapping(value = "/CapitalInProgressFormViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CapitalInProgressFormViewDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
			
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}else {
			
			session = AlmDbSession.getInstance().getSession();		
			String idForm = request.getParameter("cipID");
			if (session != null && session.isOpen()) {
	            tx = session.beginTransaction();
	            try {
				
						
	            	query = session.createSQLQuery("Delete Capital_In_Progress where CIP_ID = '"+ idForm +"'");
			 		query.executeUpdate();
						
					
	            } catch (Exception e) {
	                System.out.println("Error in creating session with the CapitalInProgressFormViewDelete method " + e.getMessage());
	                logger.info("could not connect to DB in CapitalInProgressFormViewDelete method ");
				}
				
				finally {
				        if (session != null && session.isOpen()) {
				            tx.commit();
				            session.close();
				         }
				}
				} else {
				        System.out.println("could not connect to DB in CapitalInProgressFormViewDelete  method");
				        logger.info("could not connect to DB in CapitalInProgressFormViewDelete method ");
				}
		}
		return rtn;
		
		
	}	
	
	@RequestMapping(value = "/CapitalInProgressListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CapitalInProgressListViewDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
		String idForm=null;
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
			
		}else {
			
			session = AlmDbSession.getInstance().getSession();		
			String[] idList = request.getParameterValues("cipID[]");
			if (session != null && session.isOpen()) {
	            tx = session.beginTransaction();
	            try {
					for(int i= 0; i<idList.length;i++) {
						//Get CIP_ID from the listview form
						idForm=idList[i];
						
				            	 query = session.createSQLQuery("Delete Capital_In_Progress where CIP_ID = '"+ idForm +"'");
			                     query.executeUpdate();

						}
					
	            } catch (Exception e) {
	                System.out.println("Error in creating session with the CapitalInProgressListViewDelete method " + e.getMessage());
	                logger.info("could not connect to DB in CapitalInProgressListViewDelete method ");
				}
				
				finally {
				        if (session != null && session.isOpen()) {
				            tx.commit();
				            session.close();
				         }
				}
				} else {
				        System.out.println("could not connect to DB in CapitalInProgressListViewDelete  method");
				        logger.info("could not connect to DB in CapitalInProgressListViewDelete method ");
				}
		}
				
	
		
		rtn.put("BassamTest", "DeleteDone");
		return rtn;
		
		
	}
	

@RequestMapping(value = "/GetAllCIP", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllClusters(Locale locale, Model model, HttpServletRequest request) {

	Map<String, Object> rtn = new LinkedHashMap<>();
	String CIP = "%" + request.getParameter("CIP") + "%";
	session = AlmDbSession.getInstance().getSession();
	
	if (session != null && session.isOpen()) {
		tx = session.beginTransaction();
		
try {


	
	query = session.createQuery("select t.cipID, t.cipdomain from CapitalInProgress t where t.cipID like :param1 or t.cipdomain like :param1 ORDER BY ciplastModifiedDate DESC");
	query.setString("param1", CIP);
	rtn.put("listCIP", query.list());


}catch(Exception e) {
	
	System.out.println("catch messsage is "+e.getMessage());
	
}
finally {

      if (session != null && session.isOpen()) {
    	  
    	  tx.commit();
	      session.close();
	      
      }

 }
	}
	
	return rtn;
}

	

	
	
	
	
}




