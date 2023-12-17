package com.aliat.alm.controller;

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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.SerialNumber;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller

public class SerialNumberController {
	
	private static Session session = null;
	private static Transaction tx = null;
	private static Query query = null;
	private static String queryString = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(SerialNumberController.class);
	
	//@Autowired
	//ALMSessions almsessions;
	
	@Autowired
	Notify notifications;
	
	@Autowired
	Form form;

	
	@RequestMapping(value = "/SerialNumberListView", method = RequestMethod.GET)
	public String SerialNumberListView(Locale locale, Model model,HttpServletRequest request,HttpServletResponse response) {
		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		else {
		    List<SerialNumber> serialNumberList = new ArrayList<SerialNumber>();
			session = AlmDbSession.getInstance().getSession();
			
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);

				try {
					serialNumberList = session.createQuery("select '',t.serialNumber, t.itemCode, t.itemName, TO_CHAR(t.lastModifieddate,'MM-DD-YYYY HH24:MI:SS') from SerialNumber t ORDER BY t.lastModifieddate DESC").list();
					model.addAttribute("ListGridTable", mapper.writeValueAsString(serialNumberList));

				} catch (Exception e) {
					logger.info("Error on the level of serial number list view with a message : " + e + "\n" + e.getMessage());
					model.addAttribute("ListGridTable", "");
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return "SerialNumberListView";
			
		}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredSerialListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredSerialListView(Locale locale, Model model, HttpServletRequest request,
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

				String startdate, enddate, itemcode, itemname,itemmodel,partno;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				itemcode = request.getParameter("itemcode");
				itemname = request.getParameter("itemname");
				itemmodel = request.getParameter("itemmodel");
				partno = request.getParameter("partno");
				
			
				
				List<String> listSerial= new ArrayList<String>();

				String str = "select 1 as chkBox , SERIAL_NUMBER as serialNumber , ITEM_CODE as itemCode , ITEM_NAME as itemName ,TO_CHAR(LAST_MODIFIED_DATE,'MM-DD-YYYY HH24:MI:SS') as lastModifieddate  " 
											    + " from SERIAL_NUMBER " ;
				
				
				
				if (startdate != null && enddate != null) {
					str = str + " where CREATION_DATE between TO_DATE('" + startdate
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
				if (partno != null && !partno.equalsIgnoreCase("")) {

					str = str + " and (upper(ITEM_PARTNO) LIKE upper('%" + partno + "%'))";
				}
				str = str + " ORDER BY LAST_MODIFIED_DATE DESC ";
				
				Query query = session.createSQLQuery(str);
				

				
				listSerial = query.list();
				
				
				rtn.put("listSerial",listSerial);
				
			} catch (Exception e) {
				logger.info("Error in showing the filtered Serial No list view with a message :" + e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;
	}
	  @RequestMapping(value = "/SerialNumberFormView", method = RequestMethod.GET)
	  public String SerialNumberFormView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		 
		  if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			  return "redirect:/";
		  } 
		  else {
			  
			  SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			  int selectedIndex = 0;
			  String result[] = new String[4];
			  String serialNo="",navAction = "2";
			  session = AlmDbSession.getInstance().getSession();
			  
			  if (session.isOpen() && session != null) {
				  tx = session.beginTransaction();
				  notifications.headerNotifications(session, model);
				  
				  try {
					  serialNo = request.getParameter("serialNumber");
					  navAction = request.getParameter("NavAction");
					  
					  // To load new Serial Number page
					  if (serialNo == null) { 
						  model.addAttribute("creationDate",formatter.format(new Timestamp(System.currentTimeMillis())).toString());
						  model.addAttribute("lastModifieddate",formatter.format(new Timestamp(System.currentTimeMillis())).toString());
						  model.addAttribute("type", "addNew");
						  model.addAttribute("SelectedIndex", "addNew");
						  model.addAttribute("serialNoCount", "addNew");
						  model.addAttribute("ordStatus", "draft");

					  }
					  else {
						  
						  result = form.NavigationNP(session, "SERIAL_NUMBER", "SERIAL_NUMBER", serialNo, "LAST_MODIFIED_DATE", navAction);
						  selectedIndex = Integer.parseInt(result[1]);
						  serialNo = result[2];
						  model.addAttribute("SelectedIndex", selectedIndex);
						  model.addAttribute("serialNoCount", Integer.parseInt(result[0]));

						  SerialNumber serialnum = (SerialNumber) session.get(SerialNumber.class, serialNo);
						  if (serialnum != null) {
							  model.addAttribute("serialNumber", serialnum.getSerialNumber());
							  if (serialnum.getCreationDate() == null) {
								  model.addAttribute("creationDate",formatter.format(new Timestamp(System.currentTimeMillis())).toString());
							  }
							  else {
								  model.addAttribute("creationDate",formatter.format(serialnum.getCreationDate()).toString());					    		
							  }
							  
							  if (serialnum.getLastModifieddate() == null) {
								  model.addAttribute("lastModifieddate",formatter.format(new Timestamp(System.currentTimeMillis())).toString());
							  }
							  else {
								  model.addAttribute("lastModifieddate",formatter.format(serialnum.getLastModifieddate()).toString());					    		
							  }
								model.addAttribute("serialNumber", serialnum.getSerialNumber());
								model.addAttribute("supplierId", serialnum.getSupplierId());
								model.addAttribute("supplierName", serialnum.getSupplierName());
								model.addAttribute("itemCode", serialnum.getItemCode());
								model.addAttribute("itemName", serialnum.getItemName());
								model.addAttribute("poId", serialnum.getPoId());
								model.addAttribute("grId", serialnum.getGrId());
								model.addAttribute("itemModel", serialnum.getItemModel());
								model.addAttribute("itemPart", serialnum.getItemPart());
								model.addAttribute("poItemId", serialnum.getPoItemId());
								model.addAttribute("grItemId", serialnum.getGrItemId());
								model.addAttribute("serialNumberID", serialnum.getSerialNumID());
								model.addAttribute("ordStatus", serialnum.getOrdStatus());

						  }	       	
					  }						
					} catch (Exception e) {
						logger.info("Error on the level of showing serial number form view, with a message : "+ e.getMessage());
						model.addAttribute("serialNumber", "");
						model.addAttribute("supplierId", "");
						model.addAttribute("supplierName", "");
						model.addAttribute("itemCode", "");
						model.addAttribute("itemName","");
						model.addAttribute("poId", "");
						model.addAttribute("grId", "");
						model.addAttribute("itemModel", "");
						model.addAttribute("itemPart", "");
						model.addAttribute("poItemId", "");
						model.addAttribute("grItemId", "");
						model.addAttribute("serialNumberID", "");
						model.addAttribute("ordStatus", "");

						
					} finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
						}
					}
				}
		  }
		  return "SerialNumberFormView";
	}
		

	  @RequestMapping(value = "/SerialNumberFormSave", method = RequestMethod.GET)
	  @ResponseBody
	  public Map<String, Object> SerialNumberFormSave(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	 	
	 	 Map<String, Object> rtn = new LinkedHashMap<>();
		 session = AlmDbSession.getInstance().getSession();

	 	 if (LoginServices.checkSession(request, response).equals("redirect:/")) {
	 			rtn.put("Login", "redirect:/");
	 			return rtn;
	 	}
	 	 
	 	 if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
	 		
	 		try {
				String	email=request.getParameter("email");
				String	emailTo=request.getParameter("emailTo");
				String	password=request.getParameter("password");
				String	message=request.getParameter("message");
				String	subject=request.getParameter("subject");
				String	ccmail=request.getParameter("ccmail");				
		 					 				
		 		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	 			Timestamp creationDate = new Timestamp(formatter.parse(request.getParameter("creationDate")).getTime());
	 			Timestamp lastModifiedDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

	 			Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
	 			String serialID = request.getParameter("serialNumberID");

				if (StringUtils.equalsIgnoreCase(serialID, "")) {
					synchronized (this) {					
					serialID = "SER_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT SERIAL_NUM FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET SERIAL_NUM = SERIAL_NUM + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
					}
				}
	 			
	 			
	 			
	 			if(StringUtils.equalsIgnoreCase(request.getParameter("email"), "") && StringUtils.equalsIgnoreCase(request.getParameter("password"), "") && StringUtils.equalsIgnoreCase(request.getParameter("emailTo"), "") && StringUtils.equalsIgnoreCase(request.getParameter("message"), "")  && StringUtils.equalsIgnoreCase(request.getParameter("subject"), "") && StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), "")  ){
	 				System.out.println("NO EMAIL TO SEND!");
	 			}
	 			else if(StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), "")){
	 				JavaMailUtil.SendEmails(email,password,emailTo,subject,message);
	 			}
	 			else {
	 				JavaMailUtil.SendccEmails(email,password,emailTo,ccmail,subject,message);
	 			} 
	 			
		 		SerialNumber serialnum = new SerialNumber();
	 			serialnum.setSerialNumber(request.getParameter("serialNumber"));
				serialnum.setCreationDate(creationDate);
				serialnum.setLastModifieddate(lastModifiedDate);
				serialnum.setSupplierId(request.getParameter("supplierId"));
				serialnum.setSupplierName(request.getParameter("supplierName"));
				serialnum.setItemCode(request.getParameter("itemCode"));
				serialnum.setItemName(request.getParameter("itemName"));
				serialnum.setPoId(request.getParameter("poId"));
				serialnum.setGrId(request.getParameter("grId"));
				serialnum.setItemModel(request.getParameter("itemModel"));
				serialnum.setItemPart(request.getParameter("itemPart"));
				serialnum.setPoItemId(request.getParameter("poItemId"));
				serialnum.setGrItemId(request.getParameter("grItemId"));	
	 			serialnum.setSerialNumID(serialID);
				serialnum.setOrdStatus(request.getParameter("status"));	
				session.saveOrUpdate(serialnum);
				session.flush();
				
				rtn.put("serialNumber", request.getParameter("serialNumber"));
				rtn.put("serialNumberID", serialID);
	 			rtn.put("lastModifieddate", formatter.format(new Timestamp(System.currentTimeMillis())).toString());
	 					
	 					
	 				} catch (Exception e) {
	 					e.printStackTrace();
	 					logger.info("Error in saving serial number with a message : " + e
	 							+ "////////////////////////////////////////// \n" + e.getMessage());
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



 @RequestMapping(value = "/SerialNumberFormViewDelete", method = RequestMethod.GET)
 @ResponseBody
 public Map<String, Object> SerialNumberFormViewDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	
	Map<String, Object> rtn = new LinkedHashMap<>();
	
	 if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
	    else {
			session = AlmDbSession.getInstance().getSession();
			String serialNum = request.getParameter("serialNumber");				
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
					query = session.createQuery("DELETE SerialNumber t  where t.serialNumber = :param1 ");
					query.setString("param1", serialNum);
					query.executeUpdate();						
				} catch (Exception e) {
					logger.info("Error in deleting serial number with a message : " + e + "// \n" + e.getMessage());
				}

				finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
	    }
			return rtn;
		}
	 
	

@RequestMapping(value = "/SerialNumberListViewDelete", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> SerialNumberListViewDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {	

	Map<String, Object> rtn = new LinkedHashMap<>();
	rtn.put("","");
	 if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
	    else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				
				String[] idList = request.getParameterValues("serialNumber[]");
				tx = session.beginTransaction();
				
				try {
					if (idList != null) {	
						queryString = "DELETE SerialNumber t  where t.serialNumber IN (:param1)";
						query = session.createQuery(queryString);
						query.setParameterList("param1", idList);
						query.executeUpdate();
					}
						
				} catch (Exception e) {
					logger.info("Error in deleting serial number with a message : " + e+ "// \n" + e.getMessage());
				}

				finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
	    }
			return rtn;
}  

@RequestMapping(value = "/GetAllItemSerial", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllItemSerial(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){
	
	Map<String, Object> rtn = new LinkedHashMap<>();
	 if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
	}
	 session = AlmDbSession.getInstance().getSession();
	 if (session != null && session.isOpen()) {
		 String itemCode = "%" + request.getParameter("itemCode") + "%";
	     String purchaseOrderID = request.getParameter("poId");
	     String grId = request.getParameter("grId");
		
	     tx = session.beginTransaction();
		try {
			
			if(purchaseOrderID.equalsIgnoreCase("") == false){ 
				query = session.createQuery("SELECT DISTINCT a.itemCode,a.itemName,b.ItemModel,b.ItemPartNb FROM Item a, PurchaseOrderItem b where UPPER(a.itemCode) like UPPER(:param1) and b.POId =:param2 and a.itemCode = b.ItemCode") ;
				query.setString("param1", itemCode);
				query.setString("param2", purchaseOrderID);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("ListItemser", query.list());
		     }
			else if(grId.equalsIgnoreCase("") == false){ 
				query = session.createQuery( "SELECT  DISTINCT a.itemCode,a.itemName,b.ItemModel,b.ItemPartNb from Item a, GoodsReceivedItem b where UPPER(a.itemCode) like UPPER(:param1) and b.GRid =:param2 and a.itemCode = b.ItemCode");
			    query.setString("param1", itemCode);
			    query.setString("param2", grId);
			    query.setFirstResult(0);
			    query.setMaxResults(40);
			    rtn.put("ListItemser", query.list());
			}
			else {
				query = session.createQuery("SELECT DISTINCT nvl(a.itemCode,' '), nvl(a.itemName,' '), "
		 			+ "(select nvl(b.itemModel,' ') from ItemPartNumber b where b.itemCode=a.itemCode and b.primary=1) as itmModel ,"
		 			+ "(select nvl(b.itemPartNum,' ') from ItemPartNumber b where b.itemCode=a.itemCode and b.primary=1) as PartNumber"
		 			+ "  from Item a where UPPER(a.itemCode) like UPPER(:param1) or UPPER(a.itemName) like UPPER(:param1) or UPPER(a.itemCode) IN (select distinct NVL(b.itemCode,' ') "
		 			+ " from ItemPartNumber b where UPPER(b.itemModel) like UPPER(:param1) or UPPER(b.itemPartNum) like UPPER(:param1))");

			     query.setString("param1", itemCode);
			     query.setFirstResult(0);
		         query.setMaxResults(40);
		         rtn.put("ListItemser", query.list());
		     }
			} catch (Exception e) {
					logger.info("Error in getting item code for autocomplete with a message : " + e+ "// \n" + e.getMessage());
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

@RequestMapping(value = "/GetAllItemNameFormSerial", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllItemNameFormSerial(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
 
	Map<String, Object> rtn = new LinkedHashMap<>();	
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		 rtn.put("Login", "redirect:/");
		 return rtn;
	}
	session = AlmDbSession.getInstance().getSession();
	if (session != null && session.isOpen()) {
		String itemName = "%" + request.getParameter("itemName") + "%";
		String itemCode =  request.getParameter("itemCode") ;
		
		tx = session.beginTransaction();
				
		try {
			if (itemCode.equalsIgnoreCase("")==true ) {
				query = session.createQuery("select distinct a.itemName,a.itemCode,b.itemModel,b.itemPartNum from Item a,ItemPartNumber b where b.itemCode=a.itemCode and b.primary=1 and UPPER(a.itemName) like UPPER(:param) ");
	
				query.setString("param", itemName);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("ListItemNameSerial", query.list());
			}
			else {
				query= session.createQuery("select distinct a.itemName,a.itemCode,b.itemModel,b.itemPartNum from Item a,ItemPartNumber b where b.itemCode=a.itemCode and b.primary=1 and UPPER(a.itemName) like UPPER(:param1) and a.itemCode=:param2");
				query.setString("param1", itemName);
				query.setString("param2", itemCode);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("ListItemNameSerial", query.list());
			}						
				
		} catch (Exception e) {
			logger.info("Error in getting item name for autocomplete with a message : " + e+ "// \n" + e.getMessage());
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


@RequestMapping(value = "/GetAllItemModelFormSerial", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllItemModelFormSerial(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){
  
	Map<String, Object> rtn = new LinkedHashMap<>();	
	 if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
	}
	 session = AlmDbSession.getInstance().getSession();
	 if (session != null && session.isOpen()) {
		 String itemModel = "%" + request.getParameter("itemModel") + "%";
		 String itemCode =  request.getParameter("itemCode") ;
		 tx = session.beginTransaction();
				
		try {
			if (itemCode.equalsIgnoreCase("")==false) {
				
				query = session.createQuery("select distinct nvl(t.itemModel,' '),nvl(t.itemCode,' '),"
								+ "(select nvl(a.itemName,' ') from Item a where a.itemCode=t.itemCode)as itmname,"
								+ "nvl(t.itemPartNum,' ') from ItemPartNumber t where t.itemModel IS NOT NULL and UPPER(t.itemModel) like UPPER(:param1) and t.itemCode =:param2 ");

				query.setString("param1", itemModel);
				query.setString("param2", itemCode);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("ListItemModelSerial", query.list());
			}
			else {
				query = session.createQuery("select distinct nvl(t.itemModel,' '),nvl(t.itemCode,' '),"
									+ "(select nvl(a.itemName,' ') from Item a where a.itemCode=t.itemCode)as itmname,"
									+ "nvl(t.itemPartNum,' ') from ItemPartNumber t where t.itemModel IS NOT NULL and UPPER(t.itemModel) like UPPER(:param1) ");
						
				query.setString("param1", itemModel);								
				query.setFirstResult(0);
				query.setMaxResults(40);
				 rtn.put("ListItemModelSerial", query.list());
			}
			} catch (Exception e) {
					logger.info("Error in getting item name for autocomplete with a message : " + e+ "// \n" + e.getMessage());
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

@RequestMapping(value = "/getSupplierIdName", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> getSupplierIdName(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {

	Map<String, Object> rtn = new LinkedHashMap<>();

	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	} 
	String searchKey = request.getParameter("searchKey");
	session = AlmDbSession.getInstance().getSession();
	
	if (session != null && session.isOpen()) {
		tx = session.beginTransaction();
		try {
				query = session.createSQLQuery(
							"SELECT SUPPLIER_ID,SUPPLIER_NAME FROM SUPPLIER where UPPER(SUPPLIER_ID) LIKE :param OR UPPER(SUPPLIER_NAME) LIKE :param");

				query.setParameter("param", "%" + searchKey + "%");
				query.setMaxResults(40);
				rtn.put("searchResult", query.list());

		
			} catch (Exception e) {
				logger.info("Error in getting item name for autocomplete with a message : " + e+ "// \n" + e.getMessage());
				rtn.put("searchResult", null);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	
}


@RequestMapping(value = "/GetAllItemPartnoFormSerial", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllItemPartnoFormSerial(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){
  
	Map<String, Object> rtn = new LinkedHashMap<>();	
	 if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		 rtn.put("Login", "redirect:/");
		 return rtn;
	}
	 session = AlmDbSession.getInstance().getSession();
	 if (session != null && session.isOpen()) {
		 String itemPartNumber = "%" + request.getParameter("itemPartNumber") + "%";
		 String itemCode =  request.getParameter("itemCode") ;				    
		 
		 tx = session.beginTransaction();
		 try {
			 if (itemCode.equalsIgnoreCase("")==false) {
				 
				 query = session.createQuery("select distinct nvl(t.itemModel,' '),nvl(t.itemCode,' '),"
									+ "(select nvl(a.itemName,' ') from Item a where a.itemCode=t.itemCode)as itmname,"
									+ "nvl(t.itemPartNum,' ') from ItemPartNumber t where t.itemPartNum IS NOT NULL and UPPER(t.itemPartNum) like UPPER(:param1) and t.itemCode =:param2 ");

				query.setString("param1", itemPartNumber);
				query.setString("param2", itemCode);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("ListItemPartnoSerial", query.list());
			}
			 else {
				 
				 query = session.createQuery("select distinct nvl(t.itemModel,' '),nvl(t.itemCode,' '),"
									+ "(select nvl(a.itemName,' ') from Item a where a.itemCode=t.itemCode)as itmname,"
									+ "nvl(t.itemPartNum,' ') from ItemPartNumber t where t.itemPartNum IS NOT NULL and UPPER(t.itemPartNum) like UPPER(:param1) ");							
				 query.setString("param1", itemPartNumber);
				 query.setFirstResult(0);
				 query.setMaxResults(40);
				 rtn.put("ListItemPartnoSerial", query.list());
				 
			 }
			 
		 } catch (Exception e) {
					logger.info("Error in getting item partNo for autocomplete with a message : " + e+ "// \n" + e.getMessage());
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

@RequestMapping(value = "/GetAllItemPoidFormSerial", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllItemPoidFormSerial(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){
  
	Map<String, Object> rtn = new LinkedHashMap<>();
	 if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
	}
	 session = AlmDbSession.getInstance().getSession();
	 if (session != null && session.isOpen()) {
		 String poId = "%" + request.getParameter("poId") + "%";
		 String itemCode =  request.getParameter("itemCode") ;
		 String grId =  request.getParameter("grId") ;
		 tx = session.beginTransaction();
		
		 try {
			 if (itemCode.equalsIgnoreCase("")==false) {
				 query = session.createQuery(  "select distinct a.ID, ' ' from PurchaseOrder a, PurchaseOrderItem b, Item c where (a.ID like :param1 ) and a.ID = b.POId and b.ItemCode = c.itemCode and b.ItemCode =:param2");
				 query.setString("param1", poId);
				 query.setString("param2", itemCode);
				 query.setFirstResult(0);
				 query.setMaxResults(40);						 
				 rtn.put("ListItemPoidSerial", query.list());					
			 }
			else if (grId.equalsIgnoreCase("")==false) {
				
				query= session.createQuery(
								"select distinct a.ID, '' from PurchaseOrder a,  PurchaseRequest b, GoodsReceived c where (a.ID like :param1 ) and a.ordPRQid = b.ID and b.ID = c.grPRQid and c.ID  =:param2");
						
				query.setString("param1", poId);
				query.setString("param2", grId);
				query.setFirstResult(0);
				query.setMaxResults(40);						 
				rtn.put("ListItemPoidSerial", query.list());
					
			}
			else {
				query = session.createQuery( "select  distinct a.ID,''  from PurchaseOrder a, PurchaseOrderItem b, Item c where (a.ID like :param1 )and a.ID = b.POId and b.ItemCode = c.itemCode");
						
				query.setString("param1", poId);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("ListItemPoidSerial", query.list());
					
			}
		 } catch (Exception e) {
					logger.info("Error in getting poid for autocomplete with a message : " + e+ "// \n" + e.getMessage());
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

@RequestMapping(value = "/SerialValidation", method = RequestMethod.GET)
@ResponseBody
public String SerialValidation(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

    String serialExists = "false";    
    if(LoginServices.checkSession(request, response).equals("redirect:/")) {
   	 	return "redirect:/";
   	 }
    session = AlmDbSession.getInstance().getSession();
    if (session != null && session.isOpen()) {
    	String serialNumber =  request.getParameter("serialNumber") ;
    	tx = session.beginTransaction();
		
    	try {
    		query= session.createQuery("select serialNumber from SerialNumber where serialNumber like :param");
    		query.setString("param", serialNumber);
			if(query.list().size()> 0) {
				serialExists = "true";
			}
			else {
				serialExists = "false";
			}
			
    	} catch (Exception e) {
    		logger.info("Error in searching for existing serial number with a message : " + e+ "// \n" + e.getMessage());
		}
    	finally {
    		if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
    	}
	    
    }
		return serialExists;
    }

@RequestMapping(value = "/GetAllSerialNumber", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllSerialNumber(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	
	Map<String, Object> rtn = new LinkedHashMap<>();
	
	 if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				
				String requestValue = request.getParameter("requestValue");
				 
				tx = session.beginTransaction();
				
				try {
					
					query = session.createSQLQuery("select distinct t.SERIAL_NUMBER, Nvl(t.ITEM_MODEL,'-'), Nvl(t.ITEM_PARTNO,'-'),  "
							+ "t.ITEM_CODE, t.ITEM_NAME, a.ITEM_CATEGORY, a.ITEM_CATCODE from SERIAL_NUMBER t "
							+ "left join ITEM a on t.ITEM_CODE = a.ITEM_CODE "
					+ "where LOWER(t.SERIAL_NUMBER) like LOWER(:param1) or LOWER(t.ITEM_MODEL) like LOWER(:param1) or LOWER(t.ITEM_PARTNO) like LOWER(:param1)  ");
					
					query.setString("param1","%"+requestValue+"%" );
					query.setFirstResult(0);
					query.setMaxResults(40);
					model.addAttribute("ListSerialNumber", mapper.writeValueAsString(query.list()));
					rtn.put("ListSerialNumber", query.list());			    
						
				} catch (Exception e) {
					logger.info("Error in getting serial number data with a message : " + e);
					e.printStackTrace();
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
	

////////////////////////////////////////////////////////////////////////

//////////////////////GetAllModel added by KHOULOUD AND ALI///////
@RequestMapping(value = "/GetAllModel", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllModel(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){

Map<String, Object> rtn = new LinkedHashMap<>();


if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			
			String inputModel= request.getParameter("inputModel") ;			 
			tx = session.beginTransaction();
			
	     try {
	    	 query = session.createQuery("select distinct Nvl(itemModel,' '),Nvl(serialNumber,' '),Nvl(itemPart,' ') from SerialNumber where nvl(serialNumber,' ') like :param1 or nvl(itemModel,' ') like :param1 or nvl(itemPart,' ') like :param1  ");
	    	 query.setString("param1","%"+inputModel+"%" );
	    	 query.setFirstResult(0);
	    	 query.setMaxResults(40);
			 model.addAttribute("ListModel", mapper.writeValueAsString(query.list()));
			 rtn.put("ListModel", query.list());					
			} catch (Exception e) {
				logger.info("Error in getting item Model data with a message : " + e+ "/// \n" + e.getMessage());			
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
/////////////////////////////////////////////////////////////////////////

//////////////////////GetAllPartNumber added by KHOULOUD AND ALI///////

@RequestMapping(value = "/GetAllPartNumber", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllPartNumber(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {


	Map<String, Object> rtn = new LinkedHashMap<>();


	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
	}
	session = AlmDbSession.getInstance().getSession();
	if (session != null && session.isOpen()) {
		String inputpartNumber =  request.getParameter("inputpartNumber") ;
		tx = session.beginTransaction();
		try {
			query = session.createQuery("select distinct Nvl(itemPart,' '),Nvl(serialNumber,' '),Nvl(itemModel,' ')  from SerialNumber where nvl(serialNumber,' ') like :param1 or nvl(itemModel,' ') like :param1 or nvl(itemPart,' ') like :param1  ");

			query.setString("param1","%"+inputpartNumber+"%" );
			query.setFirstResult(0);
			query.setMaxResults(40);
		    model.addAttribute("ListPartNumber", mapper.writeValueAsString(query.list()));
		    rtn.put("ListPartNumber", query.list());				
		} catch (Exception e) {
					logger.info("Error in getting item Model data with a message : " + e+ "// \n" + e.getMessage());
				
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
///////////////////////////////////////////////////////////////////////////////////
@RequestMapping(value = "/GetAllItemGridFormSerial", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllItemGridFormSerial(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){

	
	Map<String, Object> rtn = new LinkedHashMap<>();
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
	}
	session = AlmDbSession.getInstance().getSession();
	if (session != null && session.isOpen()) {
		String grId = "%" + request.getParameter("grId") + "%";
		String itemCode =  request.getParameter("itemCode") ;
		String poId =  request.getParameter("poId") ;
		tx = session.beginTransaction();
		try {
			if (itemCode.equalsIgnoreCase("")==false) {
				query = session.createQuery("select distinct a.ID, ' ' from GoodsReceived a, GoodsReceivedItem b, Item c where (a.ID like :param1 ) and a.ID = b.GRid and b.ItemCode = c.itemCode and b.ItemCode =:param2 ");
				query.setString("param1", grId);
				query.setString("param2", itemCode);
				query.setFirstResult(0);
				query.setMaxResults(40);
	            rtn.put("ListItemGridSerial", query.list());			      
			}
			else if (poId.equalsIgnoreCase("")==false) {
				query= session.createQuery("select distinct a.ID, b.ID from GoodsReceived a, PurchaseOrder b, PurchaseRequest c where (a.ID like :param1 ) and a.grPOid = b.ID and b.ID =:param2 and a.grPRQid = b.ordPRQid and b.ordPRQid=c.ID");
				query.setString("param1", grId);
				query.setString("param2", poId);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("ListItemGridSerial", query.list());
			}
			else {
				query= session.createQuery( "select  distinct a.ID,a.grPOid  from GoodsReceived a, GoodsReceivedItem b, Item c where (a.ID like :param1 )and a.ID = b.GRid and b.ItemCode = c.itemCode");
				query.setString("param1", grId);
				query.setFirstResult(0);
				query.setMaxResults(40);
			    rtn.put("ListItemGridSerial", query.list());
			    
			} 
			
		} catch (Exception e) {
					logger.info("Error in getting gr ID data with a message : " + e+ "// \n" + e.getMessage());
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

@RequestMapping(value = "/GetAllOtherSerialNum", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllOtherSerialNum(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response){

	Map<String, Object> rtn = new LinkedHashMap<>();

	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	}
	session = AlmDbSession.getInstance().getSession();
	if (session != null && session.isOpen()) {
		String serialSearch = request.getParameter("serialSearch") ;			 
		tx = session.beginTransaction();
			
	     try {

	 		 query = session.createQuery("select t.serialNumber,'' from SerialNumber t where t.serialNumber like :param ORDER BY t.lastModifieddate DESC");
	    	 query.setString("param","%"+serialSearch+"%" );
	    	 query.setFirstResult(0);
	    	 query.setMaxResults(40);
	 		
	    	 rtn.put("listOtherSerial", query.list());
			
	     } catch (Exception e) {
				logger.info("Error in getting serial data with a message : " + e+ "/// \n" + e.getMessage());			
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
