package com.aliat.alm.controller;

import java.io.IOException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.models.AssetLifeCycle;
import com.aliat.alm.models.AssetLifeCycle_Balance;
import com.aliat.alm.models.AssetLifeCycle_Ledger;
import com.aliat.alm.models.AssetLifeCycle_Transaction;
import com.aliat.alm.models.AssetRegistry;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.models.GoodsReceived;
import com.aliat.alm.models.PurchaseOrder;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Notify;

@Controller
public class AssetLifeCycleController {
	
	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;
	
	
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static final Logger logger = LoggerFactory.getLogger(FixedAssetRegistryController.class);
	
	
	@RequestMapping(value = "/AssetLifeCycle", method = RequestMethod.GET)
	
	public String AssetLifeCycle(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) 
			throws JsonGenerationException, JsonMappingException, IOException {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {

				List<String> ALCEmptyArray = new ArrayList<String>();
				model.addAttribute("ALCArrayList", ALCEmptyArray);
				
			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		/*Map<String, Object> rtn = new LinkedHashMap<>();
		String wareDetails = "";
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		List<Object> params= new ArrayList<Object>();
		List<AssetLifeCycle> assetLifeCycle = new ArrayList<AssetLifeCycle>();*/

		/*String Item_code = request.getParameter("item_code");
		System.out.println("the item code is " +Item_code);
		
	  	if (Item_code != null) {
		   
		   Item item = (Item) appConfig.find(Item.class, Item_code);
		   System.out.println("the item model is "+item.getItemModel());
		   model.addAttribute("itemModel", item.getItemModel());
		}
		*/
	/*	Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		String alcid="alc_2020_1";
		
		/*GetSeq myClass3 = new GetSeq();
		String alcid = "ALC_" + year + "_" + appConfig.getSequenceNbr(16);
		System.out.println("ACL*********" + alcid);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String warehouse = request.getParameter("warehouse");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<AssetLifeCycle> listAssetLifeCycle = new ArrayList<AssetLifeCycle>();
		
		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		Session session = almsessions.getALMSession();
		Transaction tx = session.beginTransaction();

		Query q = session.createQuery("Select t.startDate as startDate, t.itemCode as itemCode, t.warehouse as warehouse from AssetLifeCycle t where t.alcID like :param1");

		q.setString("param1", alcid);
		
		List<AssetLifeCycle> AssetlifeItems = (List<AssetLifeCycle>) q.setResultTransformer(Transformers.aliasToBean(AssetLifeCycle.class)).list();
		System.out.println("Array of Asset life Cycle items is: "+mapper.writeValueAsString(AssetlifeItems));

		//listAssetLifeCycle = q.list();
		
		tx.commit();
		session.close();
		
		System.out.println("the list "+mapper.writeValueAsString(listAssetLifeCycle));
		model.addAttribute("List", mapper.writeValueAsString(AssetlifeItems)); */
		
		
		//model.addAttribute("Type", "addNew");
		//model.addAttribute("AssetlifeCycleArray", assetLifeCycle);
		return "AssetLifeCycle";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/GenerateReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateReport(Locale locale, Model model, HttpServletRequest request)throws Exception {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
		System.out.println("Starting for Generate Report !!!");
		Map<String, Object> rtn = new LinkedHashMap<>();
		Date date;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		//SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		//AssetLifeCycle_Balance assetLifeWareItemBalance = new AssetLifeCycle_Balance();
		//String alcid="ALC_2020_1";
		String  checkVal = request.getParameter("checkValType");
		String  transactionTableOption = request.getParameter("transactionTableOption");
		
		//System.out.println("the checkbx is "+checkVal);
		//Query query,q,q1,q2,q3,q4,q5,q6,q7;
		List<AssetLifeCycle_Ledger> AssetlifeItemsLeger = new ArrayList<AssetLifeCycle_Ledger>();
		//List<AssetLifeCycle_Balance> AssetlifeItemsBalance = new ArrayList<AssetLifeCycle_Balance>();
		List<AssetLifeCycle_Balance> assetLifeItemBalance = new ArrayList<AssetLifeCycle_Balance>();
		List<AssetLifeCycle_Transaction> assetLifeITransaction = new ArrayList<AssetLifeCycle_Transaction>();
	
		// get item
		String itemCode = request.getParameter("item_code");
		System.out.println("the item code is " +itemCode);
		// get date
		String start_Date = request.getParameter("startDate");
		//System.out.println("the start date code is before " +start_Date);
		Date startDate = formatter.parse(start_Date);
		//System.out.println("the start date code is after " +startDate);
		//System.out.println("the start date code is after " +formatter1.format(startDate));
		//Date startDate1 = formatter1.parse(startDate);
		Timestamp StartDate = new Timestamp(startDate.getTime());
		//System.out.println("the start date code is after time stamp " +StartDate);
		String end_Date = request.getParameter("endDate");
		//System.out.println("the end date is before " +end_Date);
		Date endDate = formatter.parse(end_Date);
		//System.out.println("the end date is after " +endDate);
		//Date endDate1 = formatter1.parse(endDate);
		Timestamp EndDate = new Timestamp(endDate.getTime());
		// get warehouse
		String wareId = request.getParameter("warehouse");
		System.out.println("the warehouse is " +wareId);
		// get supplier
		String supplierId = request.getParameter("supplier");
		//System.out.println("the supplier is " +supplierId);
		String voucherType = request.getParameter("voucherType");
		//System.out.println("the voucherType is " +voucherType);
		String voucherNb= request.getParameter("voucherNb");
		//System.out.println("the voucher# is " +voucherNb);
		
		String siteId= request.getParameter("siteId");
		//System.out.println("the site Id is " +siteId);
		String siteName= request.getParameter("siteName");
		//System.out.println("the site Name is " +siteName);
		String nodeId= request.getParameter("nodeId");
		//System.out.println("the node Id is " +nodeId);
		//String nodeName= request.getParameter("nodeName");
		//System.out.println("the node Name is " +nodeName);
		String nodeType= request.getParameter("nodeType");
		//System.out.println("the node Type is " +nodeType);
		String cellId= request.getParameter("cellId");
		//System.out.println("the cell Id is " +cellId);
		//String cellName= request.getParameter("cellName");
		//System.out.println("the cell Name is " +cellName);
		String itemModel= request.getParameter("itemModel");
		//System.out.println("the itemModel is " +itemModel);
		String itemPartNo= request.getParameter("itemPartNo");
		//System.out.println("the itemPartNo is " +itemPartNo);
		
		
		/*Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		Session session = almsessions.getALMSession();
		Transaction tx = session.beginTransaction();*/
		if (StringUtils.equalsIgnoreCase(checkVal, "ledger")) {
			System.out.println("the checkbx is "+checkVal);
		   
			if (!itemCode.isEmpty() || !wareId.isEmpty() || !supplierId.isEmpty() || !(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
	                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
	    		AssetlifeItemsLeger = findLegerListBy(itemCode,StartDate,EndDate,wareId, supplierId,voucherType, voucherNb);
	    	
			}
			else {
				System.out.println("the Item is emptyyy ");
	    		AssetlifeItemsLeger = findLegerListBy(itemCode,StartDate,EndDate,wareId, supplierId,voucherType, voucherNb);

			}
			
			   System.out.println("THE AssetlifeItemsLeger.size() of balance "+AssetlifeItemsLeger.size());
			   System.out.println("THE AssetlifeItemsLeger list of balance "+mapper.writeValueAsString(AssetlifeItemsLeger));
			   float totalBalanceValue = 0, totalBalanceQty = 0, totalAccDep = 0, totalNetBalanceValue = 0;
			   AssetLifeCycle_Ledger totalLedgerArray = null;
			   int valiationShowTotals = 0;
			   if(AssetlifeItemsLeger.size() > 1) {
				   valiationShowTotals = 1;
			   for (int i = 0; i < AssetlifeItemsLeger.size(); i++) {
				   totalLedgerArray = AssetlifeItemsLeger.get(i);
				   System.out.println("THE totalBalanceArray list of Ledger "+mapper.writeValueAsString(totalLedgerArray));

				   totalBalanceValue +=  totalLedgerArray.getBalanceValue().floatValue();
				   totalBalanceQty +=  totalLedgerArray.getBalanceQty().floatValue();
				   totalAccDep +=  totalLedgerArray.gettAccumulatedDepreciation();
				   totalNetBalanceValue +=  totalLedgerArray.getNetBalanceValue().floatValue();
				   
			   }
			   
			  
			   System.out.println(" totalBalanceValue "+  totalBalanceValue);
			   System.out.println(" totalBalanceQty "+  totalBalanceQty);
			   System.out.println(" totalAccDep "+  totalAccDep);
			   System.out.println(" totalNetBalanceValue "+  totalNetBalanceValue);
			   
			   rtn.put("totalBalanceValueLedger", totalBalanceValue);
			   rtn.put("totalBalanceQtyLedger", totalBalanceQty);
			   rtn.put("totalAccDepLedger", totalAccDep);
			   rtn.put("totalNetBalanceValueLedger", totalNetBalanceValue);
			   }
			   rtn.put("valiationShowTotalsLedger", valiationShowTotals);
	/*		if (!itemCode.isEmpty()){
		    		AssetlifeItemsLeger = findLegerListBy(itemCode,StartDate,EndDate,wareId, supplierId,voucherType, voucherNb);
		    	
		    }
		    else {
				   if (!wareId.isEmpty()){
						  
					   ////supplier and warehouse are checked
					   if (!supplierId.isEmpty()){
						   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
				                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty()))
				           {
							   System.out.println("THE WAREHOUSE, SUPPIER, AND VOUCHER TYPE & NB ");
							   AssetlifeItemsLeger = findLegerListBy(itemCode,StartDate,EndDate,wareId, supplierId,voucherType, voucherNb );						   

				           }
						   else {
							   AssetlifeItemsLeger = findLegerListBy(itemCode,StartDate,EndDate,wareId, supplierId,voucherType, voucherNb );	
							   }
						  
					   }
					   else {
						   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
				                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty()))
				           {
							   System.out.println("THE WAREHOUSE AND VOUCHER TYPE & NB ");
							   AssetlifeItemsLeger = findLegerListBy(itemCode,StartDate,EndDate,wareId,supplierId,voucherType, voucherNb );						   
							   }
						   else {
							   AssetlifeItemsLeger = findLegerListBy(itemCode,StartDate,EndDate,wareId,supplierId,voucherType, voucherNb );						
							   }
						   }
					   }
					   
				    
				   ////supplier is checked
				   else if (!supplierId.isEmpty() ){
					  if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
			                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty()))
			          {
						   System.out.println("THE SUPPLIER AND VOUCHER TYPE & NB ");
						   AssetlifeItemsLeger = findLegerListBy(itemCode,StartDate,EndDate,wareId,supplierId,voucherType, voucherNb );						  
					  }
					   else {
						   AssetlifeItemsLeger = findLegerListBy(itemCode,StartDate,EndDate,wareId,supplierId,voucherType, voucherNb );						
						   }
					   }
				   
				   else if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
		                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty()))
		           {
					   System.out.println("THE VOCHER TYPE & NB ");
					   AssetlifeItemsLeger = findLegerListBy(itemCode,StartDate,EndDate,wareId,supplierId,voucherType, voucherNb );						   
					   }
				   
		    }*/	
		    rtn.put("AssetlifeCycleArray", AssetlifeItemsLeger);
		}	
		    
		else if (StringUtils.equalsIgnoreCase(checkVal, "balance"))  {
		   //System.out.println("the checkbx is "+checkVal);
		   
            ////item code is checked
		   
		  /* if (!itemCode.isEmpty()){
			   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
                       && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty()))
               {
				   System.out.println("THE ITEM and VOCHER TYPE & NB ");
				   assetLifeItemBalance=  findBalanceListByVoucherTypeVoucherNb(voucherType ,voucherNb,StartDate,EndDate,supplierId,wareId,itemCode );
				   }
			   else {
			   System.out.println("THE ITEM ");
			   assetLifeItemBalance=  findBalanceListByItem( itemCode,StartDate,EndDate,supplierId, wareId );
			   } 
			   
			   
		   }
		   else {*/
			   /*if (!wareId.isEmpty()){
				  
			   ////supplier and warehouse are checked
			   if (!supplierId.isEmpty()){
				   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
		                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty()))
		           {
					   System.out.println("THE WAREHOUSE, SUPPIER, AND VOUCHER TYPE & NB ");
					   assetLifeItemBalance=  findBalanceListByVoucherTypeVoucherNb(voucherType ,voucherNb,StartDate,EndDate,supplierId,wareId,itemCode );
					   }
				   else {
					   assetLifeItemBalance=  findBalanceListBySupplierWare( supplierId,wareId,StartDate,EndDate ); 
				   }
				  
			   }
			   else {
				   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
		                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty()))
		           {
					   System.out.println("THE WAREHOUSE AND VOUCHER TYPE & NB ");
					   assetLifeItemBalance=  findBalanceListByVoucherTypeVoucherNb(voucherType ,voucherNb,StartDate,EndDate,supplierId,wareId,itemCode );
					   }
				   else {
				   assetLifeItemBalance=  findBalanceListByWare( wareId,StartDate,EndDate );
				   }
			   }
			   
		   } 
		   ////supplier is checked
		   else if (!supplierId.isEmpty() ){
			  if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
	                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty()))
	          {
				   System.out.println("THE SUPPLIER AND VOUCHER TYPE & NB ");
				   assetLifeItemBalance=  findBalanceListByVoucherTypeVoucherNb(voucherType ,voucherNb,StartDate,EndDate,supplierId,wareId,itemCode );
			  }
			   else {
			       assetLifeItemBalance=  findBalanceListBySupplier( supplierId,StartDate,EndDate );
			   }
		   }
		   else if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty()))
           {
			   System.out.println("THE VOCHER TYPE & NB ");
			   assetLifeItemBalance=  findBalanceListByVoucherTypeVoucherNb(voucherType ,voucherNb,StartDate,EndDate,supplierId,wareId,itemCode );
			   }*/
		   /// no item selected
		  // else {
			   assetLifeItemBalance=  findBalanceListByItem( itemCode,StartDate,EndDate,supplierId, wareId );

			//}
		   //}
		
		   //System.out.println("THE assetLifeItemBalance.size() of balance "+assetLifeItemBalance.size());
		   //System.out.println("THE assetLifeItemBalance list of balance "+mapper.writeValueAsString(assetLifeItemBalance));
		   float totalInValue = 0,totalOutValue = 0,totalInQty = 0,totalOutQty = 0, totalBalanceValue = 0, totalBalanceQty = 0, totalAccDep = 0, totalNetBalanceValue = 0;
		   AssetLifeCycle_Balance totalBalanceArray = null;
		   int valiationShowTotals = 0;
		   if(assetLifeItemBalance.size() > 1) {
			   valiationShowTotals = 1;
		/* for (int i = 0; i < assetLifeItemBalance.size(); i++) {
			   totalBalanceArray = assetLifeItemBalance.get(i);
			  //ystem.out.println("THE totalBalanceArray list of balance "+mapper.writeValueAsString(totalBalanceArray));

			   totalInValue +=  totalBalanceArray.getInValue();
			   totalOutValue +=  totalBalanceArray.getOutValue();
			   totalInQty +=  totalBalanceArray.getInQty();
			   totalOutQty +=  totalBalanceArray.getOutQty();
			   
			   if(totalBalanceArray.getBalanceValue() != null)
				   totalBalanceValue +=  totalBalanceArray.getBalanceValue();
			   else
				   totalBalanceValue +=  0.0;
			   
			   if(totalBalanceArray.getBalanceQty() != null)
				   totalBalanceQty +=  totalBalanceArray.getBalanceQty();
			   else
				   totalBalanceQty +=  0.0;
			   
			   if(totalBalanceArray.getAccumulatedDepreciation() != null)
				   totalAccDep +=  totalBalanceArray.getAccumulatedDepreciation();
			   else
				   totalAccDep +=  0.0;
			   
			   if(totalBalanceArray.getNetBalanceVale() != null)
				   totalNetBalanceValue +=  totalBalanceArray.getNetBalanceVale();
			   else
				   totalNetBalanceValue +=  0.0;
			   
			   
		   }*/
		   
		   //System.out.println(" totalInValue "+  totalInValue);
		   //System.out.println(" totalOutValue "+  totalOutValue);
		  // System.out.println(" totalInQty "+  totalInQty);
		   //System.out.println(" totalOutQty "+  totalOutQty);
		   //System.out.println(" totalBalanceValue "+  totalBalanceValue);
		  // System.out.println(" totalBalanceQty "+  totalBalanceQty);
		   //System.out.println(" totalAccDep "+  totalAccDep);
		   //System.out.println(" totalNetBalanceValue "+  totalNetBalanceValue);
		   
		  // rtn.put("totalInValueBalance", totalInValue);
		   //rtn.put("totalOutValueBalance", totalOutValue);
		  // rtn.put("totalInQtyBalance", totalInQty);
		   //rtn.put("totalOutQtyBalance", totalOutQty);
		  // rtn.put("totalBalanceValueBalance", totalBalanceValue);
		  // rtn.put("totalAccDepBalance", totalAccDep);
		  // rtn.put("totalBalanceQtyBalance", totalBalanceQty);
		  // rtn.put("totalNetBalanceValueBalance", totalNetBalanceValue);
		   }
		   
		  // rtn.put("valiationShowTotalsBalance", valiationShowTotals);
		   //rtn.put("AssetlifeCycleArray", mapper.writeValueAsString(assetLifeItemBalance));
		   //System.out.println(mapper.writeValueAsString(assetLifeItemBalance));
		   rtn.put("AssetlifeCycleArray", assetLifeItemBalance);

		   //model.addAttribute("AssetlifeCycleArray", mapper.writeValueAsString(assetLifeItemBalance));
	/*	   ////item code is checked
		   
		   if (!itemCode.isEmpty()){
			   
			//// item code, warehouse and supplier are checked
			   if (!wareId.isEmpty()) {
			
				   if (!supplierId.isEmpty() ){
					   assetLifeWaresItemBalance=  findBalanceListByItemWareSupplier( supplierId,StartDate,EndDate,wareId,itemCode);
				   }
				//// item code and warehouse are checked
				   else {
					   assetLifeWaresItemBalance=  findBalanceListByItemWare( itemCode,StartDate,EndDate,wareId );
				   }
			   }
			   //// warehouse is not checked
			   else {
				////item and supplier are checked
				   if (!supplierId.isEmpty()){
					   assetLifeWaresItemBalance=  findBalanceListByItemSupplier(itemCode, supplierId,StartDate,EndDate );
				   }
				   ////item code is checked supplier and warehouse are not checked
				   else {
			      assetLifeWaresItemBalance=  findBalanceListByItem( itemCode,StartDate,EndDate );
			   }
				   
			  }
			
		  //     if(StringUtils.equalsIgnoreCase(voucherNb,"null") || voucherNb != null || voucherNb != "" && StringUtils.equalsIgnoreCase(voucherType,"null") || voucherType != null || voucherType != ""){ 

			   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
                       && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty()))
               {
		 		   assetLifeWaresItemBalance=  findBalanceListByVoucherTypeVoucherNbItem(voucherType ,voucherNb,StartDate,EndDate,supplierId,wareId,itemCode );
				   }  
		   }
		//// item code is not checked  
		   else {
			//// warehouse is checked
			   if (!wareId.isEmpty()){
				  
				   ////supplier and warehouse are checked
				   if (!supplierId.isEmpty()){
					   assetLifeWaresItemBalance=  findBalanceListBySupplierWare( supplierId,wareId,StartDate,EndDate );
				   }
				   else {
					   assetLifeWaresItemBalance=  findBalanceListByWare( wareId,StartDate,EndDate );
				   }
				   
			   }   
			//// supplier is checked but warehouse is not  
			   else {  
				   if (!supplierId.isEmpty()){
					   assetLifeWaresItemBalance=  findBalanceListBySupplier( supplierId,StartDate,EndDate );
				   }
			   
			   }
			   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
                       && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty()))
               {
		 		   assetLifeWaresItemBalance=  findBalanceListByVoucherTypeVoucherNb(voucherType ,voucherNb,StartDate,EndDate,supplierId,wareId );
				   }
		}
		*/   
		   
		  
		/*   ////item code is checked
		   //if (itemCode != null){
		   if (!itemCode.isEmpty() && wareId.isEmpty() && supplierId.isEmpty()){
			   assetLifeWaresItemBalance=  findBalanceListByItem( itemCode,StartDate,EndDate );
		   }
		   //// item code and warehouse are checked
		   //if (itemCode != null && wareId != null) {
		   if (!itemCode.isEmpty() && !wareId.isEmpty()&& supplierId.isEmpty()) {
			   assetLifeWaresItemBalance=  findBalanceListByItemWare( itemCode,StartDate,EndDate,wareId );
		   }
		   //// item code null but warehouse is checked
		   //if (wareId != null){
		   if (!wareId.isEmpty()&& supplierId.isEmpty() && itemCode.isEmpty()){
			   assetLifeWaresItemBalance=  findBalanceListByWare( wareId,StartDate,EndDate );
		   }
		   //// item code, warehouse and supplier are checked
		  // if (itemCode != null && wareId != null && supplierId != null){
		   if (!itemCode.isEmpty()  && !wareId.isEmpty()  && !supplierId.isEmpty() ){
			   assetLifeWaresItemBalance=  findBalanceListByItemWareSupplier( supplierId,StartDate,EndDate,wareId,itemCode);
			  
			}
		   //// supplier is checked
		  // if (supplierId != null){
		   if (!supplierId.isEmpty() && itemCode.isEmpty() && wareId.isEmpty()){
			   assetLifeWaresItemBalance=  findBalanceListBySupplier( supplierId,StartDate,EndDate );
		   }
		   ////supplier and warehouse are checked
		   if (!supplierId.isEmpty() && !wareId.isEmpty() && itemCode.isEmpty()){
			   assetLifeWaresItemBalance=  findBalanceListBySupplierWare( supplierId,wareId,StartDate,EndDate );
		   }
		   ////item and supplier are checked
		   if (!itemCode.isEmpty() && !supplierId.isEmpty() && wareId.isEmpty()){
			   assetLifeWaresItemBalance=  findBalanceListByItemSupplier(itemCode, supplierId,StartDate,EndDate );
		   }*/
		  
		}
		
		else if (StringUtils.equalsIgnoreCase(checkVal, "transaction"))  {
			   System.out.println("the checkbx is "+checkVal);
			   System.out.println("the transactionTableOption is "+transactionTableOption);
			   
			   if (!StringUtils.equalsIgnoreCase(nodeId,"null") && nodeId != null && nodeId != "") {
				   assetLifeITransaction = findTransactionListByNode(transactionTableOption,StartDate,EndDate,nodeId, siteId ,siteName, cellId);

			   }
			   else {
				   System.out.println("Node IS not empty ");
				   assetLifeITransaction = findTransactionListBy(transactionTableOption, StartDate,EndDate, siteId,cellId);
			   }
			   //if(!assetLifeITransaction.isEmpty()) {
			   rtn.put("transList", assetLifeITransaction);
			   //}
			  /* AssetLifeCycle_Transaction transaction = new AssetLifeCycle_Transaction();
			   String optionTableStringQuery = null, siteStringQuery  = null, nodeStringQuery = null, cellStringQuery = null, cabinetStringQuery = null, boardStringQuery = null ,antennaStringQuery = null ,hostStringQuery = null  ;
			   
			    Configuration cfg = new Configuration().configure();
				StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
				SessionFactory sf = cfg.buildSessionFactory(builder.build());
				Session session = almsessions.getALMSession();
				Transaction tx = session.beginTransaction();
				List<Object> params = new ArrayList<Object>();
				 params.add(StartDate);
				 params.add(EndDate);
			   if (StringUtils.equalsIgnoreCase(transactionTableOption, "Site"))  {
				   System.out.println("THE SITE");
				   siteStringQuery = "SELECT SITE_ID, WARE_NAME FROM WAREHOUSE WHERE SITE_ID IN(SELECT SITE_ID FROM NODE_ACTIVE) AND CREATION_DATE >= :param_1 AND LAST_MODIFY_DATE <= :param_2 ";
				   List siteColumns = appConfig.findAllBySQLQueryParamsAsStringList(optionTableStringQuery,params);
				   System.out.println("the RESULT IS "+ mapper.writeValueAsString(siteColumns));
				   transaction.setSiteId(siteColumns.get(0).toString());
				   transaction.setSiteName(siteColumns.get(1).toString());
			   
			   }
			   if (StringUtils.equalsIgnoreCase(transactionTableOption, "Node"))  {
				   nodeStringQuery = "SELECT NODE_CABINET.SERIALNUMBER, NODE_ACTIVE.node_Id, NODE_ACTIVE.node_name, NODE_ACTIVE.Node_type, NODE_ACTIVE.trans_type FROM NODE_CABINET, NODE_ACTIVE WHERE  NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK";
				   List nodeColumns = appConfig.findAllBySQLQueryParamsAsStringList(optionTableStringQuery,params);
				   System.out.println("the RESULT IS "+ mapper.writeValueAsString(nodeColumns));
					
			   }
			   if (StringUtils.equalsIgnoreCase(transactionTableOption, "Cabinet"))  {
				   cabinetStringQuery = "SELECT UNITPOSITION, MODEL, SERIALNUMBER, TRANS_TYPE FROM NODE_CABINET";
			   }
			   if (StringUtils.equalsIgnoreCase(transactionTableOption, "Board"))  {
				   boardStringQuery = "SELECT UNITPOSITION, MODEL, SERIALNUMBER, TRANS_TYPE FROM NODE_BOARD";
			   }
			   if (StringUtils.equalsIgnoreCase(transactionTableOption, "Antenna"))  {
				   antennaStringQuery = "SELECT ANTENNA_ID,MODEL, SERIALNUMBER, TRANS_TYPE FROM NODE_ANTENNA";
			   }
			   if (StringUtils.equalsIgnoreCase(transactionTableOption, "Cell"))  {
				   cellStringQuery = "SELECT GCELL_ID AS CELL_ID, CELLNAME , TRANS_TYPE  FROM NODE_GCELL UNION (SELECT LCELL_ID AS CELL_ID, CELLNAME,TRANS_TYPE FROM NODE_LCELL) UNION (SELECT UCELL_ID AS CELL_ID, CELLNAME, TRANS_TYPE FROM NODE_UCELL)";
			   }
			   
			   if (StringUtils.equalsIgnoreCase(transactionTableOption, "Host"))  {
				   hostStringQuery = "SELECT HOSTVER, TRANS_TYPE FROM NODE_HOSTVER";
			   }
			   
			   // Query q = session.createSQLQuery(optionTableStringQuery);
			    //q.setString("param1", WarehouseName);
				//List transList = null;
				//transList = q.list();
				//System.out.println("the RESULT IS "+ mapper.writeValueAsString(transList));
				//List<String[]> transaction = null;
				//transaction.add(trans);


				tx.commit();
				session.close();
				*/
				
			   
		}
		
		  // System.out.println("the assetLifeWaresItemBalance is: "+mapper.writeValueAsString(assetLifeWaresItemBalance));
		  
		   //tx.commit();
		   //session.close();
		   
		   //rtn.put("AssetlifeCycleArray", assetLifeItemBalance);
		  // rtn.put("Type", "generateReport");
		System.out.println("Ending Generate Report !!!");
		   return rtn;
	}
	

/*@RequestMapping(value = "/GetAllitem_ALC", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllitem_ALC(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
	
	
	Map<String, Object> rtn = new LinkedHashMap<>();
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	}
	
	session = almsessions.getALMSession();
	if (session != null && session.isOpen()) {
		tx = session.beginTransaction();
	
		try
		{
			query = session.createQuery("select distinct nvl(a.itemCode,' '), nvl(a.itemName,' '),(select nvl(b.itemModel,' ') from ItemPartNumber b where "
					+ "b.itemCode=a.itemCode and b.primary=1) as itmModel ,"
					+ "(select nvl(b.itemPartNum,' ') from ItemPartNumber b where b.itemCode=a.itemCode and b.primary=1) as PartNumber"
					+ "  from Item a where a.itemCode like :param1 or a.itemName like :param1 or a.itemCode IN(select distinct NVL(b.itemCode,' ') "
					+ "from ItemPartNumber b where b.itemModel like :param1 or b.itemPartNum like :param1) ");
			query.setString("param1","%" + request.getParameter("itemCode") + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);

			rtn.put("ListItem", query.list());
			
	    } catch(Exception e){
		  logger.info("Error while getting Item using autocomplete with error message: "+ e);
		  e.printStackTrace();
						
	    } finally {
		  if (session != null && session.isOpen()) {
			 tx.commit();
			 session.close();
	      }
	   }		
	}
return rtn;
}

@RequestMapping(value = "/GetAllItemModel_ALC", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllItemModel_ALC(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
	
	
	Map<String, Object> rtn = new LinkedHashMap<>();
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	}
	
	session = almsessions.getALMSession();
	if (session != null && session.isOpen()) {
		tx = session.beginTransaction();
	
		try
		{
			query = session.createQuery("select distinct nvl(t.itemModel,' '),nvl(t.itemCode,' '),"
					                 + "(select nvl(a.itemName,' ') from Item a where a.itemCode=t.itemCode)as itmname,"
					                 + "nvl(t.itemPartNum,' ') from ItemPartNumber t where t.itemModel like :param1");
			query.setString("param1","%" + request.getParameter("itemModel") + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);

			rtn.put("ListItemModel", query.list());
			
	    } catch(Exception e){
		  logger.info("Error while getting Item Model using autocomplete with error message: "+ e);
		  e.printStackTrace();
						
	    } finally {
		  if (session != null && session.isOpen()) {
			 tx.commit();
			 session.close();
	      }
	   }		
	}
return rtn;
}

@RequestMapping(value = "/GetAllItemPartNumbers_ALC", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllItemPartNumbers_ALC(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
	
	
	Map<String, Object> rtn = new LinkedHashMap<>();
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	}
	
	session = almsessions.getALMSession();
	if (session != null && session.isOpen()) {
		tx = session.beginTransaction();
	
		try
		{
			query = session.createQuery("select distinct nvl(t.itemPartNum,' '),nvl(t.itemCode,' '),"
					+ "(select nvl(a.itemName,' ') from Item a where a.itemCode=t.itemCode)as itmname,"
					+ "nvl(t.itemModel,' ') from ItemPartNumber t where t.itemPartNum like :param1");
			query.setString("param1","%" + request.getParameter("itemPartNo") + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);

			rtn.put("ListPartNbs", query.list());
			
	    } catch(Exception e){
		  logger.info("Error while getting Item Part Number using autocomplete with error message: "+ e);
		  e.printStackTrace();
						
	    } finally {
		  if (session != null && session.isOpen()) {
			 tx.commit();
			 session.close();
	      }
	   }		
	}
return rtn;
}


@RequestMapping(value = "/GetAllSerialNumber_ALC", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> GetAllSerialNumber_ALC(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
	
	
	Map<String, Object> rtn = new LinkedHashMap<>();
	if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		rtn.put("Login", "redirect:/");
		return rtn;
	}
	
	session = almsessions.getALMSession();
	if (session != null && session.isOpen()) {
		tx = session.beginTransaction();
	
		try
		{
			query = session.createQuery("select serialNumber, itemCode, itemName,itemModel,itemPart from SerialNumber where serialNumber like :param1 ");
			query.setString("param1","%" + request.getParameter("serialNumber") + "%");
			query.setFirstResult(0);
			query.setMaxResults(40);

			rtn.put("listSerialNumber", query.list());
			
	    } catch(Exception e){
		  logger.info("Error while getting Item serial Number using autocomplete with error message: "+ e);
		  e.printStackTrace();
						
	    } finally {
		  if (session != null && session.isOpen()) {
			 tx.commit();
			 session.close();
	      }
	   }		
	}
return rtn;
}
*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllNodes_ALC", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllNodes_ALC(Locale locale, Model model, HttpServletRequest request)
	throws JsonGenerationException, JsonMappingException, IOException {
	//logger.info("Welcome home! The client locale is {}.", locale);
	//System.out.println("Welcome to GetAllSupplier");

	Map<String, Object> rtn = new LinkedHashMap<>();

	List<String> listrequest = new ArrayList<String>();

	session = almsessions.getSession();
	tx = session.beginTransaction();
	String requestName = "%" + request.getParameter("node") + "%";
	Query q = session.createSQLQuery("select distinct NODE_NAME, NODE_ID, NODE_TYPE, NODE_MODEL  from NODE_ACTIVE where NODE_ID like :param1");
	q.setString("param1", requestName);
	q.setFirstResult(0);
	q.setMaxResults(40);
	listrequest = q.list();


	tx.commit();
	session.close();
	//model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
	//System.out.println("end good " + mapper.writeValueAsString(listItem));
	rtn.put("nodeList", listrequest);

	return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllNodesName_ALC", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllNodesName_ALC(Locale locale, Model model, HttpServletRequest request)
	throws JsonGenerationException, JsonMappingException, IOException {
	//logger.info("Welcome home! The client locale is {}.", locale);
	//System.out.println("Welcome to GetAllSupplier");

	Map<String, Object> rtn = new LinkedHashMap<>();

	List<String> listrequest = new ArrayList<String>();

	session = almsessions.getSession();
	tx = session.beginTransaction();
	String requestName = "%" + request.getParameter("nodeName") + "%";
	Query q = session.createSQLQuery("select distinct NODE_NAME, NODE_ID, NODE_TYPE, NODE_MODEL  from NODE_ACTIVE where NODE_ID like :param1");
	q.setString("param1", requestName);
	q.setFirstResult(0);
	q.setMaxResults(40);
	listrequest = q.list();


	tx.commit();
	session.close();
	//model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
	//System.out.println("end good " + mapper.writeValueAsString(listItem));
	rtn.put("nodeNameList", listrequest);

	return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllCells_ALC", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllCells_ALC(Locale locale, Model model, HttpServletRequest request)
	throws JsonGenerationException, JsonMappingException, IOException {
	//logger.info("Welcome home! The client locale is {}.", locale);
	//System.out.println("Welcome to GetAllSupplier");

	Map<String, Object> rtn = new LinkedHashMap<>();

	List<String> listrequest = new ArrayList<String>();

	session = almsessions.getSession();
	tx = session.beginTransaction();
	String requestName = "%" + request.getParameter("cell") + "%";
	Query q = session.createSQLQuery("SELECT CELL_ID, CELL_NAME FROM (SELECT GCELL_ID AS CELL_ID, CELLNAME AS CELL_NAME FROM NODE_GCELL  UNION (SELECT LCELL_ID AS CELL_ID, CELLNAME AS CELL_NAME FROM NODE_LCELL) UNION (SELECT UCELL_ID AS CELL_ID, CELLNAME AS CELL_NAME FROM NODE_UCELL)) WHERE CELL_ID like :param1");
	q.setString("param1", requestName);
	q.setFirstResult(0);
	q.setMaxResults(40);
	listrequest = q.list();


	tx.commit();
	session.close();
	//model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
	//System.out.println("end good " + mapper.writeValueAsString(listItem));
	rtn.put("cellList", listrequest);

	return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllCellsName_ALC", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllCellsName_ALC(Locale locale, Model model, HttpServletRequest request)
	throws JsonGenerationException, JsonMappingException, IOException {
	//logger.info("Welcome home! The client locale is {}.", locale);
	//System.out.println("Welcome to GetAllSupplier");

	Map<String, Object> rtn = new LinkedHashMap<>();

	List<String> listrequest = new ArrayList<String>();

	session = almsessions.getSession();
	tx = session.beginTransaction();
	String requestName = "%" + request.getParameter("cellName") + "%";
	Query q = session.createSQLQuery("SELECT CELL_ID, CELL_NAME FROM (SELECT GCELL_ID AS CELL_ID, CELLNAME AS CELL_NAME FROM NODE_GCELL  UNION (SELECT LCELL_ID AS CELL_ID, CELLNAME AS CELL_NAME FROM NODE_LCELL) UNION (SELECT UCELL_ID AS CELL_ID, CELLNAME AS CELL_NAME FROM NODE_UCELL)) WHERE CELL_ID like :param1");
	q.setString("param1", requestName);
	q.setFirstResult(0);
	q.setMaxResults(40);
	listrequest = q.list();


	tx.commit();
	session.close();
	//model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
	//System.out.println("end good " + mapper.writeValueAsString(listItem));
	rtn.put("cellNameList", listrequest);

	return rtn;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/GetAllIDs", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllIDs(Locale locale, Model model, HttpServletRequest request)
	throws JsonGenerationException, JsonMappingException, IOException {
	//logger.info("Welcome home! The client locale is {}.", locale);
	//System.out.println("Welcome to GetAllSupplier");

	Map<String, Object> rtn = new LinkedHashMap<>();
     String tableName = null;
     String requestName= null;
	//List<PurchaseOrder> listrequest = new ArrayList<PurchaseOrder>();
	String VType = request.getParameter("voucherType");
	System.out.println("the Vtype is "+ VType);
	
	session = almsessions.getSession();
	tx = session.beginTransaction();
	Query q, q1;
	
	if(StringUtils.equalsIgnoreCase(VType, "pr")) {
		 List<PurchaseRequest> listrequest = new ArrayList<PurchaseRequest>();
		 requestName = "%" + request.getParameter("VoucherNb") + "%";
			
	     q = session.createQuery("select ID ,supplier from PurchaseRequest where ID like :param1");
		 q.setString("param1", requestName);
		 q.setFirstResult(0);
		 q.setMaxResults(40);
		  listrequest = q.list();
		  tx.commit();
		  session.close();
		  System.out.println("the list is "+ mapper.writeValueAsString(listrequest));
		  model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
		  rtn.put("Listreq", listrequest);
	 }
	if (StringUtils.equalsIgnoreCase(VType, "po")) {
		 List<PurchaseOrder> listrequest = new ArrayList<PurchaseOrder>();
		 requestName = "%" + request.getParameter("VoucherNb") + "%";
		
		  q = session.createQuery("select ID ,supplier, supplierName from PurchaseOrder where ID like :param1");
		  q.setString("param1", requestName);
		  q.setFirstResult(0);
		  q.setMaxResults(40);
		  listrequest = q.list();
		  tx.commit();
		  session.close();
		  System.out.println("the list is "+ mapper.writeValueAsString(listrequest));
		  model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
		  rtn.put("Listreq", listrequest);
	 }
	if (StringUtils.equalsIgnoreCase(VType, "gr")) {
		 List<GoodsReceived> listrequest = new ArrayList<GoodsReceived>();
		 requestName = "%" + request.getParameter("VoucherNb") + "%";
			
		  q = session.createQuery("select ID ,supplierID, supplierName, warehouse, warehouseName from GoodsReceived where ID like :param1");
		  q.setString("param1", requestName);
		  q.setFirstResult(0);
		  q.setMaxResults(40);
		  listrequest = q.list();
		  tx.commit();
		  session.close();
		  System.out.println("the list is "+ mapper.writeValueAsString(listrequest));
		  model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
		  rtn.put("Listreq", listrequest);
	}
	if (StringUtils.equalsIgnoreCase(VType, "ar")) {
		 List<AssetRegistry> listrequest = new ArrayList<AssetRegistry>();
		 requestName = "%" + request.getParameter("VoucherNb") + "%";
			
		  q = session.createQuery("select arID ,supplierID,supplierName, wareID,wareName from AssetRegistry where arID like :param1");
		  q.setString("param1", requestName);
		  q.setFirstResult(0);
		  q.setMaxResults(40);
		  listrequest = q.list();
		  tx.commit();
		  session.close();
		  System.out.println("the list is "+ mapper.writeValueAsString(listrequest));
		  model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
		  rtn.put("Listreq", listrequest);
	 }
	if (StringUtils.equalsIgnoreCase(VType, "far")) {
		 List<FixedAssetRegistry> listrequest = new ArrayList<FixedAssetRegistry>();
		 requestName = "%" + request.getParameter("VoucherNb") + "%";
			
		  q = session.createQuery("select farID ,supplierID, supplierName, wareID,wareName  from FixedAssetRegistry where farID like :param1");
		  q.setString("param1", requestName);
		  q.setFirstResult(0);
		  q.setMaxResults(40);
		  listrequest = q.list();
		  tx.commit();
		  session.close();
		  System.out.println("the list is "+ mapper.writeValueAsString(listrequest));
		  model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
		  rtn.put("Listreq", listrequest);
	 }
/*	if (StringUtils.equalsIgnoreCase(VType, "none")) {
		 List result = new ArrayList<>();
		// requestName = "%" + request.getParameter("VoucherNb") + "%";
			
		//q = session.createSQLQuery("select t.ID as prqid,' '  from PurchaseRequest t where t.ID like :param1 union(select t.ID as poid,' '  from PurchaseOrder t where t.ID like :param1)");
		q = session.createSQLQuery("select ID,' '  from PurchaseRequest  where ID like :param1 union(select ID,' '  from PurchaseOrder where ID like :param1)");
		//select PRQ_ID from PURCHASE_REQUEST  union select PO_ID from PURCHASE_ORDER union select GR_ID from GOODS_RECEIVED union select AR_ID from ASSET_REGISTRY union select FAR_ID from FIXED_ASSET_REGISTRY;

		// q = session.createSQLQuery("select ID from PurchaseRequest where ID like :param1 union select ID from PurchaseOrder where ID like :param1 union select ID from GoodsReceived where ID like :param1 union select arID from AssetRegistry where arID like :param1 union select farID from FixedAssetRegistry where farID like :param1");
		 // q = session.createSQLQuery("select ID from PurchaseRequest union select ID from PurchaseOrder union select ID from GoodsReceived  union select arID from AssetRegistry union select farID from FixedAssetRegistry");

		  q.setString("param1", requestName);
		  q.setFirstResult(0);
		  q.setMaxResults(40);
		  //System.out.println("*****the list is "+ mapper.writeValueAsString(q.list()));
		  result = q.list();
		  tx.commit();
		  session.close();
		  System.out.println("the list is "+ mapper.writeValueAsString(result));
		  model.addAttribute("Listreq", mapper.writeValueAsString(result));
		  rtn.put("Listreq", result);
	 }
*/
	return rtn;
	}


	@RequestMapping(value = "/GetAllSupplier_ALC", method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> GetAllSupplier_ALC(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		/*if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}*/
		
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
		
			try
			{
				String requestValue = request.getParameter("requestValue");
				query = session.createQuery("select supplierID, supplierName,supplierAddress1 from Supplier where UPPER(supplierID) like UPPER(:param1) or UPPER(supplierName) like UPPER(:param1)");
				query.setString("param1","%" + requestValue + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("ListSupplier", query.list());
				
		    } catch(Exception e){
			  logger.info("Error while getting supplier using autocomplete with error message: "+ e);
			  e.printStackTrace();
							
		    } finally {
			  if (session != null && session.isOpen()) {
				 tx.commit();
				 session.close();
		      }
		   }		
		}
	return rtn;
	}
	
	@SuppressWarnings("null")
	public AssetLifeCycle_Balance findBalanceListParameters(Session session, int methodOption,String itemCode,Timestamp StartDate,Timestamp EndDate,String supplierId, String wareId, String voucherType,String voucherNB) throws Exception{
		AssetLifeCycle_Balance assetLifeBalance = new AssetLifeCycle_Balance();
		
		//List<AssetLifeCycle_Balance> assetLifeBalance2 = new ArrayList<>();
		List<Object> params = new ArrayList<Object>();
		String assetLifeWareItemBalanceQuery = null, inQtyBalanceQuery = null,  outQtyBalanceQuery = null,  inValueBalanceQuery = null, outValueBalanceQuery = null, FarListQuery = null, balanceQtyValueQuery = null, openingBalanceQuery = null;
		
		 params.add(StartDate);
		 params.add(EndDate);
		 params.add(itemCode);
		 params.add(wareId);
		 
		
		 
		 
		  if(methodOption == 1) {
			 // System.out.print("Here");
			  
			  assetLifeWareItemBalanceQuery = "Select t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.valuationRate as valuationRate, 0 as accumulatedDepreciation, t.balanceQty as balanceQty, 0 as netBalanceVale, t.balanceValue as balanceValue, 0 as openingQty, 0 as outQty, 0 as inQty, 0 as inValue, 0 as outValue, 0 as openingValue, t.creationDate as creationDate from AssetLifeCycle t where t.itemCode like :param_3  and (t.creationDate >= :param_1 and t.creationDate <= :param_2) and t.warehouse like :param_4 order by t.creationDate desc";
			  
			  
			
		      ///// in qty query
			  inQtyBalanceQuery = "Select sum(actualQty) from AssetLifeCycle  where (creationDate >= :param_1 and creationDate <= :param_2) and "
			    		+ " itemCode like :param_3 and inValue > 0 and voucherType in ('GR','AR') or isOpening = 1 and warehouse  =:param_4";
			  
			  ///// out qty query
			  outQtyBalanceQuery = "Select sum(actualQty) from AssetLifeCycle where (creationDate >= :param_1 and creationDate <= :param_2) and itemCode like :param_3 and outValue > 0 and voucherType in ('AR') and warehouse  =:param_4 ";
			   
			  ////inValue query
		 	  inValueBalanceQuery = "Select actualQty, inComingRate from AssetLifeCycle where (creationDate >= :param_1 and creationDate <= :param_2) and itemCode like :param_3 and inValue > 0 and voucherType in ('GR','AR') OR isOpening = 1 and warehouse  =:param_4";
		 	
		 	  /// outValue query
		 	  outValueBalanceQuery = "Select actualQty, inComingRate from AssetLifeCycle where (creationDate >= :param_1 and creationDate <= :param_2) and itemCode like :param_3 and outValue > 0 and voucherType in ('AR') and warehouse  =:param_4 ";
		 	
		 	  /////// Net balance value  
		 	  FarListQuery = "Select farcreatedDate, dailyPercent, iniCost, faritemCode from FixedAssetRegistry where farcreatedDate <= :param_1  and faritemCode like :param_2 and wareID  =:param_3";
		 	  
		 	  ////// Balance Qty , Balance Value and  
		 	  balanceQtyValueQuery = "select t.balanceQty as balanceQty, t.balanceValue as balanceValue, t.creationDate as creationDate from AssetLifeCycle t where (t.creationDate <= :param_1 and t.itemCode like :param_2 and t.warehouse  =:param_3) order by t.creationDate desc";
		 	 
		 	  ///// opening qty, opening value query and valuationRate
			   openingBalanceQuery = "select t.balanceQty as balanceQty, t.balanceValue as balanceValue, t.valuationRate as valuationRate, t.creationDate as creationDate from AssetLifeCycle t where (t.creationDate < :param_1 and t.itemCode like :param_2 and t.warehouse  =:param_3) order by creationDate desc";
			
			
		}
		  if(methodOption == 2) {
			  params.add(supplierId);
			  
			  assetLifeWareItemBalanceQuery = "Select t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.valuationRate as valuationRate, 0 as accumulatedDepreciation, t.balanceQty as balanceQty, 0 as netBalanceVale, t.balanceValue as balanceValue, 0 as openingQty, 0 as outQty, 0 as inQty, 0 as inValue, 0 as outValue, 0 as openingValue, t.creationDate as creationDate from AssetLifeCycle t where t.itemCode like :param_3  and (t.creationDate >= :param_1 and t.creationDate <= :param_2) and t.warehouse like :param_4 and Supplier like :param_5 order by t.creationDate desc";		   

			  
			  ///// in qty query
			  inQtyBalanceQuery = "Select sum(actualQty) from AssetLifeCycle  where (creationDate >= :param_1 and creationDate <= :param_2)  "
		          		+ " and itemCode like :param_3 and inValue > 0 and voucherType in ('GR','AR') and warehouse  =:param_4 and Supplier like :param_5";

			  ///// out qty query
			  outQtyBalanceQuery = "Select sum(actualQty) from AssetLifeCycle where (creationDate >= :param_1 and creationDate <= :param_2) and itemCode like :param_3 and outValue > 0 and voucherType in ('AR') and warehouse  =:param_4 and Supplier like :param_5";
	        
			  //inValue query
		      inValueBalanceQuery = "Select actualQty, inComingRate from AssetLifeCycle where (creationDate >= :param_1 and creationDate <= :param_2) and itemCode like :param_3 and inValue > 0 and voucherType in ('GR','AR') and warehouse  =:param_4 and Supplier like :param_5";
		     
		      /// outValue query  
		      outValueBalanceQuery ="Select actualQty, inComingRate from AssetLifeCycle where (creationDate >= :param_1 and creationDate <= :param_2) and itemCode like :param_3 and outValue > 0 and voucherType in ('AR') and warehouse  =:param_4 and Supplier like :param_5";
		      
		      /////// Net balance value
		      
	          FarListQuery = "Select farcreatedDate, dailyPercent, iniCost, faritemCode from FixedAssetRegistry where farcreatedDate <= :param_1  and faritemCode =:param_2 and wareID  =:param_3 and supplierID like :param_4";
		     
		      ////// Balance Qty , Balance Value and 
		      balanceQtyValueQuery = "select t.balanceQty as balanceQty, t.balanceValue as balanceValue, t.creationDate as creationDate from AssetLifeCycle t where (t.creationDate <= :param_1  and t.itemCode =:param_2 and t.warehouse  =:param_3 and t.Supplier like :param_4) order by t.creationDate desc";
        
		      ///// opening qty, opening value query and valuationRate
		       openingBalanceQuery = "select t.balanceQty as balanceQty, t.balanceValue as balanceValue, t.valuationRate as valuationRate, t.creationDate as creationDate from AssetLifeCycle t where (t.creationDate < :param_1  and t.itemCode =:param_2 and t.warehouse  =:param_3 and t.Supplier like :param_4) order by creationDate desc";
	           
		  }
		  
		  if(methodOption == 3) {
			  params.add(supplierId);
			  params.add(voucherType);
			  params.add(voucherNB);
			  
			  assetLifeWareItemBalanceQuery = "Select t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.valuationRate as valuationRate, 0 as accumulatedDepreciation, t.balanceQty as balanceQty, 0 as netBalanceVale, t.balanceValue as balanceValue, 0 as openingQty, 0 as outQty, 0 as inQty, 0 as inValue, 0 as outValue, 0 as openingValue, t.creationDate as creationDate from AssetLifeCycle t where t.itemCode like :param_3 and (t.creationDate >= :param_1 and t.creationDate <= :param_2) and  t.warehouse like :param_4 and t.Supplier like :param_5 and t.voucherType like :param_6 and t.voucherNB like :param_7 order by t.creationDate desc";		   

			  
			  ///// in qty query
			  inQtyBalanceQuery = "Select sum(actualQty) from AssetLifeCycle  where (creationDate >= :param_1 and creationDate <= :param_2) and itemCode like :param_3 and inValue > 0 and voucherType in ('GR','AR') and warehouse like :param_4 and Supplier like :param_5 and voucherType like :param_6 and voucherNB like :param_7";

			  ///// out qty query
			  outQtyBalanceQuery = "Select sum(actualQty) from AssetLifeCycle where (creationDate >= :param_1 and creationDate <= :param_2) and itemCode like :param_3 and outValue > 0 and voucherType in ('AR') and warehouse like :param_4 and Supplier like :param_5 and voucherType like :param_6 and voucherNB like :param_7";

			  //inValue query
		      inValueBalanceQuery = "Select actualQty, inComingRate from AssetLifeCycle where (creationDate >= :param_1 and creationDate <= :param_2) and itemCode like :param_3 and inValue > 0 and voucherType in ('GR','AR') and warehouse like :param_4 and Supplier like :param_5 and voucherType like :param_6 and voucherNB like :param_7";

		      /// outValue query  
		      outValueBalanceQuery = "Select actualQty, inComingRate from AssetLifeCycle where (creationDate >= :param_1 and creationDate <= :param_2)  and itemCode like :param_3 and outValue > 0 and voucherType in ('AR') and warehouse like :param_4 and Supplier like :param_5 and voucherType like :param_6 and voucherNB like :param_7";

		      /////// Net balance value
		      
	          FarListQuery = "Select farcreatedDate, dailyPercent, iniCost, faritemCode from FixedAssetRegistry where farcreatedDate <= :param_1 and faritemCode =:param_2 and supplierID =:param_3 and PoId =:param_4";

		      ////// Balance Qty , Balance Value and 
		      balanceQtyValueQuery = "select t.balanceQty as balanceQty, t.balanceValue as balanceValue, t.creationDate as creationDate from AssetLifeCycle t where (t.creationDate <= :param_1 and t.itemCode like :param_2 and t.warehouse like :param_3 and t.Supplier like :param_4 and t.voucherType like :param_5 and t.voucherNB like :param_6) order by t.creationDate desc";

		      ///// opening qty, opening value query and valuationRate
		       openingBalanceQuery = "select t.balanceQty as balanceQty, t.balanceValue as balanceValue, t.valuationRate as valuationRate, t.creationDate as creationDate from AssetLifeCycle t where  (t.creationDate < :param_1 and t.itemCode like :param_2 and t.warehouse like :param_3 and t.Supplier like :param_4 and t.voucherType like :param_5 and t.voucherNB like :param_6) order by creationDate desc";

		  }
		
		 // assetLifeBalance =  (AssetLifeCycle_Balance) appConfig.findAllByQueryParamsAsClass(AssetLifeCycle_Balance.class, assetLifeWareItemBalanceQuery, params);
		 	
		 int indx; String parameterName;
		  Query result;
		  result = session.createQuery(assetLifeWareItemBalanceQuery);
		  for (Object param : params) {
			  indx = params.indexOf(param) + 1;
			  parameterName = "param_" + indx;
			  result.setParameter(parameterName, param);
		  }
		  
		  if (result.list().size() > 0)
			  assetLifeBalance = (AssetLifeCycle_Balance) result.setResultTransformer(Transformers.aliasToBean(AssetLifeCycle_Balance.class)).list().get(0);
		  
		 // System.out.println("###### The assetLifeBalance " + mapper.writeValueAsString(assetLifeBalance2));
		  if(assetLifeBalance != null) {
			  
			     //System.out.println("if it is not null The assetLifeBalance2 is " +mapper.writeValueAsString(assetLifeBalance2));
	     ///Class className = AssetLifeCycle_Balance.class;
	   
	     ///// in qty query
	   // BigDecimal inQtyBalance = appConfig.findAllByQueryParamsAsBigDecimal(AssetLifeCycle_Balance.class, inQtyBalanceQuery, params);
	    
	    int j = 0;
	    Query getresult;
	    getresult = session.createQuery(inQtyBalanceQuery);
	    for (Object param : params) {
	    j = params.indexOf(param) + 1;
	    parameterName = "param_" + j;
	    getresult.setParameter(parameterName,param );
	    }
	    
	    Float inQtyBalance = null;
	    if (getresult.uniqueResult() != null)
	    	 inQtyBalance =  (Float) getresult.uniqueResult();
	   
		//System.out.println("****** The inQtyBalance " + mapper.writeValueAsString(inQtyBalance));

		if (inQtyBalance != null) {

 	    Float InQty = inQtyBalance;
 	    if (InQty == 0) {
 	    	//System.out.println("The returned result is Null for q2");
 	    	//assetLifeBalance.setInQty(null);
         }
 	    else {
 	    	assetLifeBalance.setInQty(InQty);      	  
            //System.out.println("the in inQtyBalance is "+mapper.writeValueAsString(inQtyBalance));
           // System.out.println("the assetLifeWareItemBalance.getInQty is "+assetLifeBalance.getInQty());               
     	   
 	    }	
 	    }
    
 	   
	
	    
    ///// out qty query
	   
		//BigDecimal outQtyBalance = appConfig.findAllByQueryParamsAsBigDecimal(AssetLifeCycle_Balance.class, outQtyBalanceQuery, params);
		
		j = 0;
	    getresult = session.createQuery(outQtyBalanceQuery);
	    for (Object param : params) {
	    j = params.indexOf(param) + 1;
	    parameterName = "param_" + j;
	    getresult.setParameter(parameterName,param );
	    }
	    
	    Float outQtyBalance = null;
	    if (getresult.uniqueResult() != null)
	    	outQtyBalance = (Float) getresult.uniqueResult();
	    
    if (outQtyBalance != null) {
   
 	   Float OutQty = outQtyBalance;
 	  /* if (OutQty == 0) {
 		  assetLifeBalance.setOutQty((Float) 0);
 	   }
 	   else {*/
 		  assetLifeBalance.setOutQty(OutQty);      	  
        //System.out.println("the outQtyBalance is "+outQtyBalance);
        //System.out.println("the assetLifeWareItemBalance.getOutQty is "+assetLifeBalance.getOutQty()); 
       // assetLifeWareItemBalance.setOutQty(outQty);
    //}
    }     
	
	   
	

	 ////inValue query
 	  
 	 // Object[] inValueBalance = (Object[]) appConfig.findAllByQueryParamsASObjectList(AssetLifeCycle_Balance.class, inValueBalanceQuery, params);
 	  
 	  j = 0;
 	 getresult = session.createQuery(inValueBalanceQuery);
 	 for (Object param : params) {
 	j = params.indexOf(param) + 1;
 	parameterName = "param_" + j;
 	getresult.setParameter(parameterName, param);
 	}
 	Object[] inValueBalance = null;
 	if (getresult.list().size() > 0) {
 		inValueBalance = (Object[]) getresult.list().toArray();
 	}
      //System.out.println("the inValueBalance is "+mapper.writeValueAsString(inValueBalance));
      
      if(ArrayUtils.isNotEmpty(inValueBalance)) {
     // System.out.println("the inValueBalance length is "+inValueBalance.length);
      int length1 = inValueBalance.length;
      //System.out.println("the first length "+length1);
      if (length1 > 0) {
         Float inValueSum = null;
         Float inValue = null;
        for (int i = 0; i < length1; i++) {
    	   Object[] inValueBalance1 = (Object[])inValueBalance[i];
           //System.out.println("$$$$$$$ the inValueBalance2 is "+mapper.writeValueAsString(inValueBalance1));
 	   
           BigDecimal  actualQty = (BigDecimal) inValueBalance1[0];
           BigDecimal incomingRate = (BigDecimal)inValueBalance1[1];
           inValue = actualQty.floatValue() * incomingRate.floatValue();
          // System.out.println("***the actualQty is "+actualQty);
          // System.out.println("***the incomingRate is "+incomingRate);
          // System.out.println("***the inValue is "+inValue);
         
    }
        inValueSum += inValue;
        //System.out.println("***the inValueSum is "+inValueSum);
     
        assetLifeBalance.setInValue(inValueSum);
    }
    else {
    	//assetLifeBalance.setInValue(null);
    }
   }
	   /// outValue query
     
 	  //Object[] outValueBalance = (Object[]) appConfig.findAllByQueryParamsASObjectList(AssetLifeCycle_Balance.class, outValueBalanceQuery, params);
 	 
 	 j = 0;
 	 getresult = session.createQuery(outValueBalanceQuery);
 	 for (Object param : params) {
 	j = params.indexOf(param) + 1;
 	parameterName = "param_" + j;
 	getresult.setParameter(parameterName, param);
 	}
 	Object[] outValueBalance = null;
 	if (getresult.list().size() > 0) {
 		outValueBalance = (Object[]) getresult.list().toArray();
 	}
 	  
 	  //System.out.println("***the outValueBalanc is "+outValueBalance);
	  // if (!(StringUtils.equalsIgnoreCase(outValueBalance.toString(), "") || StringUtils.equalsIgnoreCase(outValueBalance.toString(), null) || StringUtils.equalsIgnoreCase(outValueBalance.toString(), "null") || outValueBalance.toString().isEmpty())){

    if (ArrayUtils.isNotEmpty(outValueBalance) ) {
     // System.out.println("the inValueBalance length is "+outValueBalance.length);
      int length = outValueBalance.length;
     // System.out.println("the first length "+length);
      if (length > 0) {
    	 Float outValueSum = null;
         Float outValue = null;
         for (int i = 0; i < length; i++) {
        	 Object[] outValueBalance1 = (Object[]) outValueBalance[i];
            // System.out.println("*******the inValueBalance1 is "+mapper.writeValueAsString(outValueBalance1));
             
             BigDecimal actualQty = (BigDecimal)outValueBalance1[0];
             BigDecimal incomingRate = (BigDecimal)outValueBalance1[1];
             outValue = actualQty.floatValue() * incomingRate.floatValue();
           //  System.out.println("***the actualQty is "+actualQty);
           //  System.out.println("***the incomingRate is "+incomingRate);
            // System.out.println("***the outValue is "+outValue);
          
    }
        outValueSum += outValue;
       // System.out.println("***the outValueSum is "+outValueSum);
     

        assetLifeBalance.setOutValue(outValueSum);
    }
    else {
    	//assetLifeBalance.setOutValue(null);
    }
    
   }   
    /////// Net balance value
   
    params.remove(StartDate);
    
    if(methodOption == 3) {
    	params.remove(voucherType);
    }
    
    //Object[] FarList = (Object[]) appConfig.findAllByQueryParamsASObjectList(AssetLifeCycle_Balance.class, FarListQuery, params);
	    
    j = 0;
	 getresult = session.createQuery(FarListQuery);
	 for (Object param : params) {
	j = params.indexOf(param) + 1;
	parameterName = "param_" + j;
	getresult.setParameter(parameterName, param);
	}
	Object[] FarList = null;
	if (getresult.list().size() > 0) {
		FarList = (Object[]) getresult.list().toArray();
	}
    
   // System.out.println("the FarList is "+mapper.writeValueAsString(FarList));
    if (ArrayUtils.isNotEmpty(FarList) ) {
    int FarListlength = FarList.length;
   // System.out.println("the List length is "+FarListlength);
    if (FarListlength > 0) {
 	  float netBalanceValue = 0;
 	  float accumulatedDepreciation2 = 0;
      float accumulatedDepreciation1 = 0;
      float initialCost = 0;
         for (int i = 0; i < FarListlength; i++) {
         	 Object[] FarList1 = (Object[])FarList[i];
  
              Date farCreationDate = (Date)FarList1[0];
              float farDailyPercent = (float) FarList1[1];
              float farIniCost = (float) FarList1[2];
              initialCost = farIniCost;
             // System.out.println("***far CreationDate is "+farCreationDate);
             // System.out.println("***far DailyPercent is "+farDailyPercent);
             // System.out.println("***far IniCost is "+farIniCost);
               
                 
              long diffInMillies = Math.abs(farCreationDate.getTime() - EndDate.getTime());
              long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                 //System.out.println("*** The diffInMillies is "+diffInMillies);
                 //System.out.println("*** The diff is "+diff);
              int depreciationDuration = (int)diff;
             // System.out.println("*** The depreciation Duration is "+depreciationDuration);
                 
              accumulatedDepreciation1 = farDailyPercent *depreciationDuration;
             // System.out.println("*** The accumulated Depreciation 1 is "+accumulatedDepreciation1);
                     
         }
         accumulatedDepreciation2 += accumulatedDepreciation1;
        // System.out.println("*** The accumulated Depreciation 2 is "+accumulatedDepreciation2);
         
         netBalanceValue = assetLifeBalance.getBalanceValue() - accumulatedDepreciation2;
         //System.out.println("*** The assetLifeWareItemBalance.getBalanceValue is "+assetLifeBalance.getBalanceValue());
        // System.out.println("*** The NetBalanceValueis "+netBalanceValue);
        // System.out.println("***The initialCost  is "+initialCost);
         if (accumulatedDepreciation2 >= initialCost) {
        	 assetLifeBalance.setAccumulatedDepreciation(initialCost);
         }
         else {
        	 assetLifeBalance.setAccumulatedDepreciation(accumulatedDepreciation2);
         }
         assetLifeBalance.setNetBalanceVale(netBalanceValue);
    }
    
    else {
    	//assetLifeBalance.setNetBalanceVale(0);
    }
    
    }
 
 ////// Balance Qty , Balance Value and 
    if(methodOption == 3) {
    	params.add(4,voucherType);
    }
    
    //Object[] balanceQtyValueList = appConfig.findAllByQueryParamsASObjectListMax(AssetLifeCycle_Balance.class, balanceQtyValueQuery, params);
    
    
    j = 0;
    getresult = session.createQuery(balanceQtyValueQuery);
    for (Object param : params) {
    j = params.indexOf(param) + 1;
    parameterName = "param_" + j;
    getresult.setParameter(parameterName, param);
    }
    getresult.setFirstResult(0);
    getresult.setMaxResults(1);
    
    Object[] balanceQtyValueList = null;
    if (getresult.list().size() > 0) {
    	balanceQtyValueList = (Object[]) getresult.list().toArray()[0];
	}
    
   // System.out.println("$$$$$$ The balanceQtyValue List is " + mapper.writeValueAsString(balanceQtyValueList));

  
	  

	   if(ArrayUtils.isNotEmpty(balanceQtyValueList)) {
		  
		   //System.out.println("The first value of q4 is " + balanceQtyValueList[0]);
		  // System.out.println("The second value of q4 is " + balanceQtyValueList[1]);
         
		   float balanceQty = (float) balanceQtyValueList[0];
		  // System.out.println("The value of balanceQty after casting is " + balanceQty);
		   assetLifeBalance.setBalanceQty(balanceQty);
		   
		   float balanceValue = (float)balanceQtyValueList[1];
		   assetLifeBalance.setBalanceValue(balanceValue);
      } 
	   else {
		   assetLifeBalance.setBalanceQty(assetLifeBalance.getOpeningQty());
		   assetLifeBalance.setBalanceValue(assetLifeBalance.getOpeningValue());
		  
		  // assetLifeBalance.setBalanceQty(0);
		  // assetLifeBalance.setBalanceValue(0);
		  
	   }
	   
	///// opening qty, opening value query and valuationRate
	      params.add(0,StartDate);
		  params.remove(EndDate);
		 
	     //Object[] openingBalanceList = appConfig.findAllByQueryParamsASObjectListMax(AssetLifeCycle_Balance.class, openingBalanceQuery, params);
	    
	     j = 0;
	     getresult = session.createQuery(openingBalanceQuery);
	     for (Object param : params) {
	     j = params.indexOf(param) + 1;
	     parameterName = "param_" + j;
	     getresult.setParameter(parameterName, param);
	     }
	     getresult.setFirstResult(0);
	     getresult.setMaxResults(1);
	     
	     Object[] openingBalanceList = null;
	     if (getresult.list().size() > 0) {
	    	 openingBalanceList = (Object[]) getresult.list().toArray()[0];
	 	}
	     
	     //System.out.println("$$$$$$ The openingBalance List is " + mapper.writeValueAsString(openingBalanceList));

	
		
	   

	  if (ArrayUtils.isNotEmpty(openingBalanceList)) {
		  System.out.println("The first value of q1 is " +mapper.writeValueAsString(openingBalanceList[0]));
		  System.out.println("The second value of q1 is " +mapper.writeValueAsString(openingBalanceList[1]));
		  System.out.println("The third value of q1 is " +mapper.writeValueAsString(openingBalanceList[2]));
	   
		  float openingQty = (float)openingBalanceList[0];
	      assetLifeBalance.setOpeningQty(openingQty);
	   
	      float openingValue = (float)openingBalanceList[1];
	      assetLifeBalance.setOpeningValue(openingValue);
	   
	      float valuationRate = (float) openingBalanceList[2];
	      assetLifeBalance.setValuationRate(valuationRate);
	   
	   }
	   else {
		   //System.out.println("******The assetLifeWareItemBalance.getBalanceQty is " +assetLifeBalance.getBalanceQty());
		   //System.out.println("******The assetLifeWareItemBalance.getBalanceValue is " +assetLifeBalance.getBalanceValue());
		   //assetLifeBalance.setOpeningQty(assetLifeBalance.getBalanceQty());
		   //assetLifeBalance.setOpeningValue(assetLifeBalance.getBalanceValue());
		  // assetLifeBalance.setOpeningQty(null);
		  // assetLifeBalance.setOpeningValue(null);
		  // assetLifeBalance.setValuationRate(0);
	   } 
		  }
		  
		  else {
			  
			  params.remove(StartDate);
			  params.remove(EndDate);
			  if(methodOption == 1) {
				 
				 assetLifeWareItemBalanceQuery = "Select t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.valuationRate as valuationRate, 0 as accumulatedDepreciation, t.balanceQty as balanceQty, 0 as netBalanceVale, t.balanceValue as balanceValue, 0 as openingQty, 0 as outQty, 0 as inQty, 0 as inValue, 0 as outValue, 0 as openingValue, t.creationDate as creationDate from AssetLifeCycle t where t.itemCode like :param_1 and t.warehouse like :param_2";		   
			  }
			  if(methodOption == 2) {
				 
			     assetLifeWareItemBalanceQuery = "Select t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.valuationRate as valuationRate, 0 as accumulatedDepreciation, t.balanceQty as balanceQty, 0 as netBalanceVale, t.balanceValue as balanceValue, 0 as openingQty, 0 as outQty, 0 as inQty, 0 as inValue, 0 as outValue, 0 as openingValue, t.creationDate as creationDate from AssetLifeCycle t where t.itemCode like :param_1 and t.warehouse like :param_2 and Supplier like :param_3";		   
			  }
			  
			  if(methodOption == 3) {
				 
				  assetLifeWareItemBalanceQuery = "Select t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.valuationRate as valuationRate, 0 as accumulatedDepreciation, t.balanceQty as balanceQty, 0 as netBalanceVale, t.balanceValue as balanceValue, 0 as openingQty, 0 as outQty, 0 as inQty, 0 as inValue, 0 as outValue, 0 as openingValue, t.creationDate as creationDate from AssetLifeCycle t where t.itemCode like :param_1 and  t.warehouse like :param_2 and t.Supplier like :param_3 and t.voucherType like :param_4 and t.voucherNB like :param_5 order by t.creationDate desc";		   
			  }
			  int i; 
			  query = session.createQuery(assetLifeWareItemBalanceQuery);
			  for (Object param : params) {
				  i = params.indexOf(param) + 1;
				  query.setParameter("param_" + i, param);
			  }

			  if (query.list().size() > 0)
				  assetLifeBalance = (AssetLifeCycle_Balance) query.setResultTransformer(Transformers.aliasToBean(AssetLifeCycle_Balance.class)).list().get(0);
			  else
			  	System.out.println("There is not any record related to the query: " +query + " in the method: AppConfig.findAllByQueryParamsAsClass");

			     //assetLifeBalance =  (AssetLifeCycle_Balance) appConfig.findAllByQueryParamsAsClass(AssetLifeCycle_Balance.class, assetLifeWareItemBalanceQuery, params);
			     System.out.println("###### The assetLifeBalance of the else " + mapper.writeValueAsString(assetLifeBalance));
			     if(assetLifeBalance != null) {
			     
			  
			     
			     params.add(0,EndDate);
		        ////// Balance Qty , Balance Value and 
			    
			    Object[] balanceQtyValueList = findAllByQueryParamsASObjectListMax(session,assetLifeWareItemBalanceQuery,params);
			    
			    System.out.println("$$$$$$ The balanceQtyValue List is " + mapper.writeValueAsString(balanceQtyValueList));

			  
				  

				   if(ArrayUtils.isNotEmpty(balanceQtyValueList)) {
					  
					   System.out.println("The first value of q4 is " + balanceQtyValueList[0]);
					   System.out.println("The second value of q4 is " + balanceQtyValueList[1]);
			         
					   float balanceQty = (float)balanceQtyValueList[0];
					   System.out.println("The value of balanceQty after casting is " + balanceQty);
					   assetLifeBalance.setBalanceQty(balanceQty);
					   
					   float balanceValue = (float)balanceQtyValueList[1];
					   assetLifeBalance.setBalanceValue(balanceValue);
			      } 
				   else {
					   assetLifeBalance.setBalanceQty(assetLifeBalance.getOpeningQty());
					   assetLifeBalance.setBalanceValue(assetLifeBalance.getOpeningValue());
					  
				   }
				   
				///// opening qty, opening value query and valuationRate
				      params.add(0,StartDate);
					  params.remove(EndDate);
					 
				     Object[] openingBalanceList = findAllByQueryParamsASObjectListMax(session,openingBalanceQuery,params);
				    
					System.out.println("$$$$$$ The openingBalance List is " + mapper.writeValueAsString(openingBalanceList));

				
					
				   

				  if (ArrayUtils.isNotEmpty(openingBalanceList)) {
					  System.out.println("The first value of q1 is " +mapper.writeValueAsString(openingBalanceList[0]));
					  System.out.println("The second value of q1 is " +mapper.writeValueAsString(openingBalanceList[1]));
					  System.out.println("The third value of q1 is " +mapper.writeValueAsString(openingBalanceList[2]));
				   
					  float openingQty = (float)openingBalanceList[0];
				      assetLifeBalance.setOpeningQty(openingQty);
				   
				      float openingValue = (float)openingBalanceList[1];
				      assetLifeBalance.setOpeningValue(openingValue);
				   
				      float valuationRate = (float)openingBalanceList[2];
				      assetLifeBalance.setValuationRate(valuationRate);
				   
				   }
				   else {
					   //System.out.println("******The assetLifeWareItemBalance.getBalanceQty is " +assetLifeBalance.getBalanceQty());
					   //System.out.println("******The assetLifeWareItemBalance.getBalanceValue is " +assetLifeBalance.getBalanceValue());
					   System.out.println("****** NULLLLL" );
					 //  assetLifeBalance.setOpeningQty(null);
					 //  assetLifeBalance.setOpeningValue(null);
					  // assetLifeBalance.setValuationRate(0);
				   }
				  
				   if(ArrayUtils.isEmpty(balanceQtyValueList)&& ArrayUtils.isEmpty(openingBalanceList)) {
					   assetLifeBalance = null;
			     } 
			   }
			
			 
		  }
   
   
		params.clear();
    
    return assetLifeBalance;
	}
	
	
	@SuppressWarnings({ "unused", "unchecked" })
	public List<AssetLifeCycle_Balance> findBalanceListByItem(String itemCode,Timestamp StartDate,Timestamp EndDate, String supplierId, String wareId ) throws Exception{
		AssetLifeCycle_Balance assetLifeItemBalance = new AssetLifeCycle_Balance();
		//List<AssetLifeCycle_Balance> ALC_balance;
		 //System.out.println("******The initialized assetLifeItemBalance is " + mapper.writeValueAsString(assetLifeItemBalance));
		List<AssetLifeCycle_Balance> assetLifeItemsBalance = new ArrayList<AssetLifeCycle_Balance>();
 
		List<Object> params = new ArrayList<Object>();
		
		session = almsessions.getSession();
		tx = session.beginTransaction();
		
		/* if (!itemCode.isEmpty()){
			   
				//// item code, warehouse and supplier are checked
				   if (!wareId.isEmpty()) {
				
					   if (!supplierId.isEmpty() ){
						   System.out.println("******The Item , Supplier and Warehouse are checked");
						    	        assetLifeItemBalance = findBalanceListParameters(session,2,itemCode,StartDate,EndDate,supplierId,wareId,null,null);
						    	        if(assetLifeItemBalance != null) {
											   assetLifeItemsBalance.add(assetLifeItemBalance);
									    }

								   
						     
					   }
					//// item code and warehouse are checked
					   else {
						   String ware = wareId;
							  
						   System.out.println("******The Item and Warehouse are checked");
						   
						   System.out.println("The Warehouse is "+ware);
						   System.out.println("The Item code is "+itemCode);
						   
						          assetLifeItemBalance = findBalanceListParameters(session,1,itemCode,StartDate,EndDate,null,ware,null,null);
						          if(assetLifeItemBalance != null) {
									   assetLifeItemsBalance.add(assetLifeItemBalance);
								  }
						      }
				        
					   
				   }
				   //// warehouse is not checked
				   else {
					////item and supplier are checked
					   if (!supplierId.isEmpty()){
						   System.out.println("******The Item and Supplier are checked");
						   
						   System.out.println("The Item code is "+itemCode);
						
						   System.out.println("The Supplier is "+supplierId);
						   String wareListQuery = "Select distinct t.warehouse as warehouse from AssetLifeCycle t where t.itemCode like :param_1 and Supplier like :param_2 and voucherType in ('GR','AR')";
						   params.add(itemCode);
						   params.add(supplierId);
				    	  
				    	   List<String> wareList =(List<String>) appConfig.findAllByQueryParamsAsStringList(AssetLifeCycle_Balance.class,wareListQuery,params);
							
						 
						   System.out.println("The wareList is " +mapper.writeValueAsString(wareList));
						  
						   for (String ware : wareList) {
				
					                  assetLifeItemBalance = findBalanceListParameters(session,2,itemCode,StartDate,EndDate,supplierId,ware,null,null);
					                  if(assetLifeItemBalance != null) {
										   assetLifeItemsBalance.add(assetLifeItemBalance);
									 }

						   }       
				
					   }
					   ////item code is checked supplier and warehouse are not checked
					   else {
						   System.out.println("********The Item code is checked");
						   System.out.println("The Item code is " +itemCode);
						   String wareListQuery = "Select distinct t.warehouse as warehouse from AssetLifeCycle t where t.itemCode like :param_1 and voucherType in ('GR','AR')";
						   params.add(itemCode);
				    	  
				    	   List<String> wareList =(List<String>) appConfig.findAllByQueryParamsAsStringList(AssetLifeCycle_Balance.class,wareListQuery,params);
							
		
						   System.out.println("The wareList is " +mapper.writeValueAsString(wareList));
						  
						   for (String ware : wareList) {
							 
							   assetLifeItemBalance = findBalanceListParameters(session,1,itemCode,StartDate,EndDate,null,ware,null,null);
							   if(assetLifeItemBalance != null) {
							   assetLifeItemsBalance.add(assetLifeItemBalance);
							   }
				   }
			     }
					   
			}
				
		 } */
		// else {// no item selected
			 
			    Object[] listItemWare;
			    
			    
			    
				
				//Query q = session.createQuery("Select distinct itemCode, warehouse from AssetLifeCycle where creationDate >= :param1 and creationDate <= :param2 and voucherType in ('GR','AR')");
				/*Query q = session.createQuery("Select distinct itemCode, warehouse from AssetLifeCycle where creationDate >= :param1 and creationDate <= :param2");
				q.setParameter("param1", StartDate);
				q.setParameter("param2", EndDate);
				q.setFirstResult(0);
				q.setMaxResults(100);
				listItemWare = (Object[]) q.list().toArray();
				System.out.println("The listItemWare is " +mapper.writeValueAsString(listItemWare));*/
			    
			    
			    /*Query query = session.createSQLQuery(
			    		"CALL TRYPRO(:stockCode)")
			    		.addEntity(AssetLifeCycle.class)
			    		.setParameter("stockCode", "7277");
			    				
			    	List result = query.list();
			    	for(int i=0; i<result.size(); i++){
			    		AssetLifeCycle asset = (AssetLifeCycle)result.get(i);
			    		System.out.println(asset.getItemCode());
			    	}
			    	
			    	StoredProcedureQuery department = session.createStoredProcedureQuery("TRYPRO");
			        department.registerStoredProcedureParameter("emp_department", String.class, ParameterMode.IN);
			 
			        String dparam = "Technology";
			        department.setParameter("emp_department", dparam);
			 
			        List dlist = (List) department.getResultList();
			 
			        for(Employee employee : dlist) {
			            System.out.println(employee.toString());
			        }*/
			    	
			    	/*final String idasset = "ALC_2021_1";
			        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
			        EntityManager em = emf.createEntityManager();
			        AssetLifeCycle asset = em.find(AssetLifeCycle.class, idasset);*/
				
			    System.out.println("Starting before Query !!!");
				/*String str = "Select distinct a.ITEM_CODE as \"itemCode\", a.WAREHOUSE as \"warehouse\","  
						
						+ "(Select COALESCE(sum(ACTUAL_QTY),0) from ASSET_LIFE_CYCLE t where (CREATION_DATE BETWEEN :param1 and :param2) and " + 
						"a.ITEM_CODE =  t.ITEM_CODE and IN_VALUE > 0 "+
						"and (VOUCHER_TYPE IN ('GR','AR') or IS_OPENING = 1) and a.warehouse  = t.warehouse) as inQty, " 
						
						
						+ "(Select COALESCE(sum(ACTUAL_QTY),0) from ASSET_LIFE_CYCLE t where (CREATION_DATE BETWEEN :param1 and :param2) and " + 
						"a.ITEM_CODE = t.ITEM_CODE and OUT_VALUE > 0 "+
						"and (VOUCHER_TYPE IN ('AR') or IS_OPENING = 1) and a.warehouse  = t.warehouse) as outQty, "
						
						
						+"(Select COALESCE(sum(ACTUAL_QTY*INCOMING_RATE),0) from ASSET_LIFE_CYCLE t where (CREATION_DATE BETWEEN :param1 and :param2) and "+
						"a.ITEM_CODE = t.ITEM_CODE and IN_VALUE > 0 "+
						"and (VOUCHER_TYPE IN ('GR','AR') or IS_OPENING = 1) and a.warehouse = t.warehouse) as inValue, "
						
						
						+"(Select COALESCE(sum(ACTUAL_QTY*INCOMING_RATE),0) from ASSET_LIFE_CYCLE t where (CREATION_DATE BETWEEN :param1 and :param2) and "+
						"a.ITEM_CODE = t.ITEM_CODE and OUT_VALUE > 0 "+
						"and (VOUCHER_TYPE IN ('AR') or IS_OPENING = 1 ) and a.warehouse = t.warehouse) as outValue, "
						
						
						+"(Select COALESCE(BALANCE_QTY,0) FROM ASSET_LIFE_CYCLE t where t.CREATION_DATE <= :param1 "+
						"and a.ITEM_CODE = t.ITEM_CODE and a.warehouse = t.warehouse "+
						" ORDER BY t.CREATION_DATE DESC FETCH FIRST 1 ROW ONLY) as balanceQty, "
						
						
						+"(Select COALESCE(BALANCE_VALUE,0) FROM ASSET_LIFE_CYCLE t where t.CREATION_DATE <= :param1 "+
						"and a.ITEM_CODE = t.ITEM_CODE and a.warehouse = t.warehouse "+
						"ORDER BY t.CREATION_DATE DESC FETCH FIRST 1 ROW ONLY) as balanceValue, "
						
						
						+"(Select COALESCE(VALUATION_RATE,0) FROM ASSET_LIFE_CYCLE t where t.CREATION_DATE < :param1 " 
						+"and a.ITEM_CODE = t.ITEM_CODE and a.warehouse = t.warehouse " 
						+"ORDER BY t.CREATION_DATE DESC FETCH FIRST 1 ROW ONLY) as valuationRate "
						
						+", a.ITEM_NAME as itemName "
						

						+"from ASSET_LIFE_CYCLE a";
						
						*/
			    
			   
			   
			   // String startDateStrQuery = "select * from (select CREATION_DATE from ASSET_LIFE_CYCLE where IS_OPENING = 1 and CREATION_DATE <= '"+EndDate+" and ITEM_CODE LIKE '"+itemCode+"' and WAREHOUSE LIKE '"+wareId+"' ORDER BY CREATION_DATE DESC ) FETCH FIRST 1 ROWS ONLY ";
			    String startDateStrQuery = "select * from (select CREATION_DATE from ASSET_LIFE_CYCLE where IS_OPENING = 1 and CREATION_DATE <= :param2 and ITEM_CODE LIKE :param3 and WAREHOUSE LIKE :param4 ORDER BY CREATION_DATE DESC ) FETCH FIRST 1 ROWS ONLY ";  
			    Query queryDate = session.createSQLQuery(startDateStrQuery);
			    queryDate.setParameter("param2", EndDate);
			    queryDate.setParameter("param3", "%"+itemCode+"%");
			    queryDate.setParameter("param4", "%"+wareId+"%");
				List<Object[]> startDateQuery = queryDate.list();
			   // System.out.println("startDateQuery !!!"+startDateQuery.get(0));
			    if(startDateQuery.size() > 0 ) {
			    	StartDate = Timestamp.valueOf(String.valueOf(startDateQuery.get(0)));
			    }
			    
			    String str = "select  distinct itemCode, warehouse, siteID, wareName, itemName, itemModel, UOM, supplierID, sum(balanceqty) as balanceqty, sum(balancevalue) as balancevalue, sum(valuationrate) as valuationrate, sum(openingqty) as openingqty,sum(openingValue) as openingValue,sum(inQty) as inQty,sum(outQty) as outQty,sum(inValue) as inValue,sum(outValue) as outValue from (" + 
			    		"Select a.ITEM_CODE as itemCode, a.WAREHOUSE as warehouse, a.SITE_ID as siteID, a.WARE_NAME as wareName, a.ITEM_NAME as itemName, a.ITEM_MODEL as itemModel, a.UOM as UOM, a.SUPPLIER_ID as supplierID, 0 as balanceQty,0 as balanceValue,0 as valuationRate, 0 as openingQty, 0 as openingValue, " + 
			    		"COALESCE(sum(a.ACTUAL_QTY),0) as inQty, 0 as outQty, COALESCE(sum(a.IN_VALUE),0) as inValue, 0 as outValue " + 
			    		"FROM ASSET_LIFE_CYCLE a WHERE (a.CREATION_DATE BETWEEN :param1 and :param2) and a.VOUCHER_TYPE IN ('GR') and a.IN_VALUE > 0 and a.ITEM_CODE LIKE :param3 and a.WAREHOUSE LIKE :param4 " + 
			    		"group by a.ITEM_CODE, a.WAREHOUSE, a.SITE_ID, a.WARE_NAME, a.ITEM_NAME, a.ITEM_MODEL, a.UOM, a.SUPPLIER_ID " + 
			    		"UNION " + 
			    		"Select a.ITEM_CODE as itemCode, a.WAREHOUSE as warehouse, a.SITE_ID as siteID, a.WARE_NAME as wareName, a.ITEM_NAME as itemName, a.ITEM_MODEL as itemModel, a.UOM as UOM, a.SUPPLIER_ID as supplierID, 0 as balanceQty,0 as balanceValue,0 as valuationRate, 0 as openingQty, 0 as openingValue, " + 
			    		"0 as inQty, COALESCE(sum(a.ACTUAL_QTY),0) as outQty, 0 as inValue, COALESCE(sum(a.OUT_VALUE),0) as outValue " + 
			    		"FROM ASSET_LIFE_CYCLE a WHERE (a.CREATION_DATE BETWEEN :param1 and :param2) and a.VOUCHER_TYPE IN ('DN') and a.OUT_VALUE > 0  and a.ITEM_CODE LIKE :param3 and a.WAREHOUSE LIKE :param4 " + 
			    		"group by a.ITEM_CODE, a.WAREHOUSE, a.SITE_ID, a.WARE_NAME, a.ITEM_NAME, a.ITEM_MODEL, a.UOM, a.SUPPLIER_ID " + 
			    		"UNION " + 
			    		"(Select  itemCode,  warehouse, siteID,  wareName, itemName, itemModel, UOM, supplierID, balanceQty, balanceValue, valuationRate, openingQty, openingValue, " + 
			    		" inQty, outQty, inValue, outValue  from (Select a.ITEM_CODE as itemCode, a.WAREHOUSE as warehouse, a.SITE_ID as siteID, a.WARE_NAME as wareName, a.ITEM_NAME as itemName, a.ITEM_MODEL as itemModel, a.UOM as UOM, a.SUPPLIER_ID as supplierID, " + 
			    		"0 as balanceQty,0 as balanceValue, 0 as valuationRate, COALESCE(a.BALANCE_QTY,0) as openingQty, COALESCE(a.BALANCE_VALUE,0) as openingValue, 0 as inQty,0 as outQty, 0 as inValue, 0 as outValue  " + 
			    		"FROM ASSET_LIFE_CYCLE a WHERE a.CREATION_DATE <= :param1 and a.ITEM_CODE LIKE :param3 and a.WAREHOUSE LIKE :param4  ORDER BY a.CREATION_DATE DESC )FETCH FIRST 1 ROWS ONLY " + 
			    		") UNION " + 
			    		"(Select  itemCode,  warehouse, siteID,  wareName, itemName, itemModel, UOM, supplierID, balanceQty, balanceValue, valuationRate, openingQty, openingValue, " + 
			    		" inQty, outQty, inValue, outValue  from (Select a.ITEM_CODE as itemCode, a.WAREHOUSE as warehouse, a.SITE_ID as siteID, a.WARE_NAME as wareName, a.ITEM_NAME as itemName, a.ITEM_MODEL as itemModel, a.UOM as UOM, a.SUPPLIER_ID as supplierID, " + 
			    		"a.BALANCE_QTY as balanceQty,a.BALANCE_VALUE as balanceValue, a.VALUATION_RATE as valuationRate, 0 as openingQty, 0 as openingValue, 0 as inQty,0 as outQty, 0 as inValue, 0 as outValue " + 
			    		"FROM ASSET_LIFE_CYCLE a WHERE a.CREATION_DATE <= :param2 and a.ITEM_CODE LIKE :param3 and a.WAREHOUSE LIKE :param4  ORDER BY a.CREATION_DATE DESC )FETCH FIRST 1 ROWS ONLY " + 
			    		") ) group by itemCode, warehouse, siteID, wareName, itemName, itemModel,UOM, supplierID";
				Query query = session.createSQLQuery(str);
				query.setParameter("param1", StartDate);
				query.setParameter("param2", EndDate);
				query.setParameter("param3", "%"+itemCode+"%");
				query.setParameter("param4", "%"+wareId+"%");
				//query.setParameter("param5", "%"+supplierId+"%");
				
				//query.setFirstResult(0);
				//query.setMaxResults(100);
				
				assetLifeItemsBalance =  ((SQLQuery) query)
       			.addScalar("itemCode").addScalar("warehouse").addScalar("siteID").addScalar("wareName").addScalar("itemName").addScalar("itemModel").addScalar("UOM").addScalar("inQty",StandardBasicTypes.FLOAT).addScalar("outQty",StandardBasicTypes.FLOAT)
       			.addScalar("inValue",StandardBasicTypes.FLOAT).addScalar("outValue",StandardBasicTypes.FLOAT)
       			.addScalar("balanceQty",StandardBasicTypes.FLOAT).addScalar("balanceValue",StandardBasicTypes.FLOAT)
       			.addScalar("valuationRate",StandardBasicTypes.FLOAT).addScalar("openingQty",StandardBasicTypes.FLOAT).addScalar("openingValue",StandardBasicTypes.FLOAT)
       			.setResultTransformer(Transformers.aliasToBean(AssetLifeCycle_Balance.class)).list();
				
				
				System.out.println("After finishing Query !!!");
				//List<Object[]> getRes = query.list();
				//System.out.println("The itemWare is " +mapper.writeValueAsString(getRes));

				/*if (ArrayUtils.isNotEmpty(listItemWare) ) {
				 for (int i = 0; i < listItemWare.length ; i++) {
					 Object[] itemWare = (Object[]) listItemWare[i];
					 //System.out.println("The itemWare is " +mapper.writeValueAsString(itemWare));

					 
					 itemCode = itemWare[0].toString();
					 wareId = itemWare[1].toString();
					 assetLifeItemBalance = findBalanceListParameters(session,1,itemCode,StartDate,EndDate,null,wareId,null,null);
					 if(assetLifeItemBalance != null) {
					   assetLifeItemsBalance.add(assetLifeItemBalance);
					 }
					 
				 }

				}*/
				tx.commit();
				session.close();
			 
		 //}
		 
		  // System.out.println("the assetLifeWaresItemBalance is: "+mapper.writeValueAsString(assetLifeItemsBalance));
			  
		   
		   
		return assetLifeItemsBalance;
		}
	
	
	@SuppressWarnings("unused")
	public List<AssetLifeCycle_Balance> findBalanceListByWare(String wareId,Timestamp StartDate,Timestamp EndDate ) throws Exception{

		AssetLifeCycle_Balance assetLifeWareItemBalance = new AssetLifeCycle_Balance();
		
		session = almsessions.getSession();
		tx = session.beginTransaction();
		
		 System.out.println("******The initialized assetLifeWareItemBalance is " + mapper.writeValueAsString(assetLifeWareItemBalance));

		List<AssetLifeCycle_Balance> assetLifeWaresItemBalance = new ArrayList<AssetLifeCycle_Balance>();
		List<Object> params = new ArrayList<Object>();
		
		   String ware = wareId;
		  
		   System.out.println("########### The Warehouse is checked");
		   
		   System.out.println("The Warehouse is "+ware);
		   
		   String itemListQuery = "Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.warehouse like :param_1";
    	   params.add(ware);
    	   List<String> itemList =AssetLifeCycleLists (session,itemListQuery,params);
			
		   
		   System.out.println("The itemList is " +mapper.writeValueAsString(itemList));
		  
		   for (String item : itemList) {
			   
			   assetLifeWareItemBalance = findBalanceListParameters(session,1,item,StartDate,EndDate,null,ware,null,null);
			   if(assetLifeWareItemBalance != null) {
			   assetLifeWaresItemBalance.add(assetLifeWareItemBalance);
			   }
			}
		   System.out.println("the assetLifeWaresItemBalance is: "+mapper.writeValueAsString(assetLifeWaresItemBalance));
			  
		
		
		
		
		   
		   
		return assetLifeWaresItemBalance;
	}
	
	@SuppressWarnings("unused")
	public List<AssetLifeCycle_Balance> findBalanceListBySupplier(String supplierId,Timestamp StartDate,Timestamp EndDate ) throws Exception{
		AssetLifeCycle_Balance assetLifeSuppItemBalance = new AssetLifeCycle_Balance();
		System.out.println("******The initialized assetLifeSuppItemBalance is " + mapper.writeValueAsString(assetLifeSuppItemBalance));
		
		session =almsessions.getSession();
		tx = session.beginTransaction();

		List<AssetLifeCycle_Balance> assetLifeSuppItemsBalance = new ArrayList<AssetLifeCycle_Balance>();
		
		List<Object> params = new ArrayList<Object>();
	
		   String supplier = supplierId;
		  
		   System.out.println("########### The Supplier is checked");
		   
		   System.out.println("The Supplier is "+supplier);
		   
		   String itemListQuery = "Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.Supplier  like :param_1";
    	   params.add(supplier);
    	   List<String> itemList =AssetLifeCycleLists (session,itemListQuery,params);
			
		   
		   System.out.println("The itemList is " +mapper.writeValueAsString(itemList));
		  
          for (String item : itemList) {
			   
		       String itemCode = item;
		       System.out.println("The item code in ware is " +itemCode);
		       
		       String wareSuppListQuery = "Select distinct t.warehouse as warehouse from AssetLifeCycle t where t.itemCode like :param_2 and t.Supplier like :param_1 and voucherType in ('GR','AR')";
		       params.add(itemCode);
		      // params.add(supplier);
	    	   List<String> wareSuppList =AssetLifeCycleLists (session,itemListQuery,params);
			
		   
		       System.out.println("The assetlifeWareItemBalance is " +mapper.writeValueAsString(wareSuppList));
			  
			   for (String ware : wareSuppList) {
			
                  System.out.println("The assetLifeSuppItemBalance is " +mapper.writeValueAsString(assetLifeSuppItemBalance));
                  assetLifeSuppItemBalance = findBalanceListParameters(session,2,itemCode,StartDate,EndDate,supplierId,ware,null,null);
                  if(assetLifeSuppItemBalance != null) {
                  assetLifeSuppItemsBalance.add(assetLifeSuppItemBalance);
                  }
              }
			   
		   }
		
		   System.out.println("the assetLifeSuppItemsBalance is: "+mapper.writeValueAsString(assetLifeSuppItemsBalance));
			  
		
	
		
		   
		   
		return assetLifeSuppItemsBalance;
	}
	
	@SuppressWarnings("unused")
	public List<AssetLifeCycle_Balance> findBalanceListBySupplierWare( String supplierId,String wareId,Timestamp StartDate,Timestamp EndDate ) throws Exception{
		AssetLifeCycle_Balance assetLifeWareSuppItemBalance = new AssetLifeCycle_Balance();
		System.out.println("******The initialized assetLifeWareSuppItemBalance is " + assetLifeWareSuppItemBalance);
		
		session =almsessions.getSession();
		tx = session.beginTransaction();

		List<AssetLifeCycle_Balance> assetLifeWareSuppItemsBalance = new ArrayList<AssetLifeCycle_Balance>();
		List<Object> params = new ArrayList<Object>();
		
		  String ware = wareId;
		  
		   System.out.println("******The Warehouseand Supplier are checked");
		   
		   System.out.println("The Warehouse is "+ware);
		   System.out.println("The Supplier is "+supplierId);
		   
		   
		   
		   String itemWareSuppQuery = "Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.warehouse like :param_1 and Supplier like :param_2 and voucherType in ('GR','AR')";
		   params.add(ware);
		   params.add(supplierId);
    	   List<String> itemWareSuppList =AssetLifeCycleLists (session,itemWareSuppQuery,params);

		   if (!itemWareSuppList.isEmpty()) {
		   System.out.println("The itemWareSuppList is " +mapper.writeValueAsString(itemWareSuppList));
		  
		      for (String item : itemWareSuppList) {
		    	  String itemCode = item;
		          System.out.println("The item code in ware is " +itemCode);
		       
		          assetLifeWareSuppItemBalance = findBalanceListParameters(session,2,itemCode,StartDate,EndDate,supplierId,wareId,null,null);
		          if(assetLifeWareSuppItemBalance != null) {
		          assetLifeWareSuppItemsBalance.add(assetLifeWareSuppItemBalance);
		          }
		   }
		 }
	
	
	    System.out.println("the assetLifeWareSuppItemsBalance is: "+mapper.writeValueAsString(assetLifeWareSuppItemsBalance));
	  
	 
		   
		return assetLifeWareSuppItemsBalance;
	}	
	
	
	public List<AssetLifeCycle_Balance> findBalanceListByVoucherTypeVoucherNb(String voucherType,String voucherNB,Timestamp StartDate,Timestamp EndDate, String supplierId, String wareId, String itemId ) throws Exception{
		AssetLifeCycle_Balance assetLifeVoucherItemBalance = new AssetLifeCycle_Balance();
		List<AssetLifeCycle_Balance> assetLifeVouchersItemBalance = new ArrayList<AssetLifeCycle_Balance>();
		List<Object> params = new ArrayList<Object>();
		
		session = almsessions.getSession();
		tx = session.beginTransaction();
		
		System.out.println("########### The voucherType and voucher# are checked");
		   if (!(StringUtils.equalsIgnoreCase(itemId, "") || StringUtils.equalsIgnoreCase(itemId, null) || StringUtils.equalsIgnoreCase(itemId, "null") || itemId.isEmpty())){
			   System.out.println("The ITEM IS NOT NULL");
			   if ((StringUtils.equalsIgnoreCase(supplierId, "") || StringUtils.equalsIgnoreCase(supplierId, null) || StringUtils.equalsIgnoreCase(supplierId, "null") || supplierId.isEmpty()) || (StringUtils.equalsIgnoreCase(wareId, "") || StringUtils.equalsIgnoreCase(wareId, null) || StringUtils.equalsIgnoreCase(wareId, "null") || wareId.isEmpty())){
				   params.add(itemId);
				   if ((StringUtils.equalsIgnoreCase(supplierId, "") || StringUtils.equalsIgnoreCase(supplierId, null) || StringUtils.equalsIgnoreCase(supplierId, "null") || supplierId.isEmpty()) && (StringUtils.equalsIgnoreCase(wareId, "") || StringUtils.equalsIgnoreCase(wareId, null) || StringUtils.equalsIgnoreCase(wareId, "null") || wareId.isEmpty())){
					   System.out.println("The SUPPLIER AND WAREHOUSE ARE NULL");
					   String itemSupplierWarehouseQuery = "Select distinct Supplier, warehouse from AssetLifeCycle where itemCode like :param_1 ";
					   List<Object[]> supplierWareList =  AssetLifeCycleListObject(session,itemSupplierWarehouseQuery,params);
					   System.out.println("***The supplierWareList is "+supplierWareList);
					   params.clear();
					   for (Object[] wareSuppParams : supplierWareList) {
					   if(StringUtils.equalsIgnoreCase(voucherType,"PR")) {
							 params.add(voucherNB);
							 String voucherNBquery = "Select ID from PurchaseOrder  where ordPRQid like :param_1";
							 List<String> voucherNBList = AssetLifeCycleLists (session,voucherNBquery,params);
							 voucherType = "PO";
							 System.out.println("The poids are " +mapper.writeValueAsString(voucherNBList));
							   
							   for(String voucherNb :voucherNBList) {
								   System.out.println("The voucherType is "+voucherType);
								   System.out.println("The voucher# is "+voucherNb);
								   
								   String itemCode = itemId;
								   System.out.println("The item code is " +itemCode);
								   supplierId = String.valueOf(wareSuppParams[0]);
								   wareId = String.valueOf(wareSuppParams[1]);
								   System.out.println("The supplier is " +supplierId);
								   System.out.println("The warehouse is " +wareId);

								   assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);

								   System.out.println("*#*#*#The assetLifeVoucherItemBalance is " +assetLifeVoucherItemBalance);
							       if(assetLifeVoucherItemBalance != null) {
    
								      assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
							       }
								        
									    

								       
								   
								   }       
						 }
						 else if(StringUtils.equalsIgnoreCase(voucherType,"PO")) {
							 System.out.println("The voucherType is "+voucherType);
							 String voucherNb = voucherNB;
							 System.out.println("The voucher# is "+voucherNb);
							   
							 String itemCode = itemId;
							 supplierId = String.valueOf(wareSuppParams[0]);
							 wareId = String.valueOf(wareSuppParams[1]);
							 System.out.println("The supplier is " +supplierId);
							 System.out.println("The warehouse is " +wareId);
      
							 assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
							 System.out.println("*#*#*#The assetLifeVoucherItemBalance is " +assetLifeVoucherItemBalance);
						       if(assetLifeVoucherItemBalance != null) {
                                  assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
						       }
							      
							   
						 
						 }
						 
						 else if(StringUtils.equalsIgnoreCase(voucherType,"GR") || StringUtils.equalsIgnoreCase(voucherType,"AR") || StringUtils.equalsIgnoreCase(voucherType,"FAR") ) {
							   
							   String voucherNb = voucherNB;
							   params.add(voucherNB);
							   if(StringUtils.equalsIgnoreCase(voucherType,"GR")) {
								   String GrQuery = "Select grPOid from GoodsReceived  where ID like :param_1";
								   voucherNb = VoucherNbr(session,GrQuery,params);
								   voucherType = "PO";
								   System.out.println("The poid for GR is " +voucherNb);
							    }
							   if(StringUtils.equalsIgnoreCase(voucherType,"AR")) {
							       String ArQuery = "Select poID from AssetRegistry  where arID like :param_1";
							       voucherNb = VoucherNbr(session,ArQuery,params);
							       voucherType = "PO";
							       System.out.println("The poid for AR is " +voucherNb);
						       }
							   if(StringUtils.equalsIgnoreCase(voucherType,"FAR")) {
								   String FarQuery = "Select PoId from FixedAssetRegistry  where farID like :param_1";  
								   voucherNb = VoucherNbr(session,FarQuery,params);
								   voucherType = "PO";
								   System.out.println("The poid for Far is " +voucherNb);
							   }
							   params.clear();
							   System.out.println("The voucherType is "+voucherType);
							   System.out.println("The voucher# is "+voucherNb);
							  
							   String itemCode = itemId;
							   supplierId = String.valueOf(wareSuppParams[0]);
							   wareId = String.valueOf(wareSuppParams[1]);
							   System.out.println("The supplier is " +supplierId);
							   System.out.println("The warehouse is " +wareId);
    
							       assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
							       System.out.println("*#*#*#The assetLifeVoucherItemBalance is " +assetLifeVoucherItemBalance);
							       if(assetLifeVoucherItemBalance != null) {
							       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
							       }
							      
						   
						 }
				   }
					   
				   }  
				   
				   
				   if ((StringUtils.equalsIgnoreCase(supplierId, "") || StringUtils.equalsIgnoreCase(supplierId, null) || StringUtils.equalsIgnoreCase(supplierId, "null") || supplierId.isEmpty()) && !(StringUtils.equalsIgnoreCase(wareId, "") || StringUtils.equalsIgnoreCase(wareId, null) || StringUtils.equalsIgnoreCase(wareId, "null") || wareId.isEmpty())){
					   System.out.println("The SUPPLIER IS NULL");
					   params.add(wareId);   
					   String itemSupplierQuery = "Select distinct Supplier from AssetLifeCycle where itemCode like :param_1 and  warehouse like :param_2";
					   List<String> supplierList =  AssetLifeCycleLists (session,itemSupplierQuery,params);
					   System.out.println("***The supplierList is "+supplierList);
					   params.clear(); 
					   for (String supplier : supplierList) {
						   if(StringUtils.equalsIgnoreCase(voucherType,"PR")) {
								 params.add(voucherNB);
								 String voucherNBquery = "Select ID from PurchaseOrder  where ordPRQid like :param_1";
								 List<String> voucherNBList =  AssetLifeCycleLists (session,voucherNBquery,params);
								 params.clear();
								 voucherType = "PO";
								 System.out.println("The poids are " +mapper.writeValueAsString(voucherNBList));
								   
								   for(String voucherNb :voucherNBList) {
									   System.out.println("The voucherType is "+voucherType);
									   System.out.println("The voucher# is "+voucherNb);
									   
									   String itemCode = itemId;
									   System.out.println("The item code is " +itemCode);
									   System.out.println("The supplier is " +supplier);
									       
									   assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplier,wareId,voucherType,voucherNb);
									   
									   if(assetLifeVoucherItemBalance != null) {
									       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
									   }

									        
										    

									       
									   
									   }       
							 }
							 else if(StringUtils.equalsIgnoreCase(voucherType,"PO")) {
								 System.out.println("The voucherType is "+voucherType);
								 String voucherNb = voucherNB;
								 System.out.println("The voucher# is "+voucherNb);
								   
								 String itemCode = itemId;
								 System.out.println("The item code is " +itemCode);
								 System.out.println("The supplier is " +supplier);
								       
								 assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplier,wareId,voucherType,voucherNb);
								 if(assetLifeVoucherItemBalance != null) {
								       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
								 }


								      
								   
							 
							 }
							 
							 else if(StringUtils.equalsIgnoreCase(voucherType,"GR") || StringUtils.equalsIgnoreCase(voucherType,"AR") || StringUtils.equalsIgnoreCase(voucherType,"FAR") ) {
								   
								   String voucherNb = voucherNB;
								   params.add(voucherNB);
								   if(StringUtils.equalsIgnoreCase(voucherType,"GR")) {
									   String GrQuery = "Select grPOid from GoodsReceived  where ID like :param_1";
									   voucherNb =VoucherNbr(session,GrQuery,params);
									   voucherType = "PO";
									   System.out.println("The poid for GR is " +voucherNb);
								    }
								   if(StringUtils.equalsIgnoreCase(voucherType,"AR")) {
								       String ArQuery = "Select poID from AssetRegistry  where arID like :param_1";
								       voucherNb = VoucherNbr(session,ArQuery,params);
								       voucherType = "PO";
								       System.out.println("The poid for AR is " +voucherNb);
							       }
								   if(StringUtils.equalsIgnoreCase(voucherType,"FAR")) {
									   String FarQuery = "Select PoId from FixedAssetRegistry  where farID like :param_1";  
									   voucherNb = VoucherNbr(session,FarQuery,params);
									   voucherType = "PO";
									   System.out.println("The poid for Far is " +voucherNb);
								   }
								   params.clear();
								   System.out.println("The voucherType is "+voucherType);
								   System.out.println("The voucher# is "+voucherNb);
								  
								   String itemCode = itemId;
								   System.out.println("The item code is " +itemCode);
								   System.out.println("The supplier is " +supplierId);
								       
								       assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplier,wareId,voucherType,voucherNb);
								       if(assetLifeVoucherItemBalance != null) {
									       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
									   }


								      
							   
							 }
					      }
						   
				   }  
					   
				   
				   if (!(StringUtils.equalsIgnoreCase(supplierId, "") || StringUtils.equalsIgnoreCase(supplierId, null) || StringUtils.equalsIgnoreCase(supplierId, "null") || supplierId.isEmpty()) && (StringUtils.equalsIgnoreCase(wareId, "") || StringUtils.equalsIgnoreCase(wareId, null) || StringUtils.equalsIgnoreCase(wareId, "null") || wareId.isEmpty())){
					   System.out.println("The WAREHOUSE IS NULL");
					   params.add(supplierId);
					   String itemWarehouseQuery = "Select distinct warehouse from AssetLifeCycle where itemCode like :param_1 and  Supplier like :param_2";
					   List<String> wareList =  AssetLifeCycleLists (session,itemWarehouseQuery,params);
					   System.out.println("***The wareList is "+wareList);
					   params.clear();
					   for (String ware : wareList) {
						   if(StringUtils.equalsIgnoreCase(voucherType,"PR")) {
								 params.add(voucherNB);
								 String voucherNBquery = "Select ID from PurchaseOrder  where ordPRQid like :param_1";
								 List<String> voucherNBList =  AssetLifeCycleLists (session,voucherNBquery,params);
								 params.clear();
								 voucherType = "PO";
								 System.out.println("The poids are " +mapper.writeValueAsString(voucherNBList));
								   
								   for(String voucherNb :voucherNBList) {
									   System.out.println("The voucherType is "+voucherType);
									   System.out.println("The voucher# is "+voucherNb);
									   
									   String itemCode = itemId;
									   System.out.println("The item code is " +itemCode);
									   System.out.println("The warehouse is " + ware);
									       
									   assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,ware,voucherType,voucherNb);
									   if(assetLifeVoucherItemBalance != null) {
									       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
									   }


									        
										    

									       
									   
									   }       
							 }
							 else if(StringUtils.equalsIgnoreCase(voucherType,"PO")) {
								 System.out.println("The voucherType is "+voucherType);
								 String voucherNb = voucherNB;
								 System.out.println("The voucher# is "+voucherNb);
								   
								 String itemCode = itemId;
								 System.out.println("The item code is " +itemCode);
								 System.out.println("The warehouse is " +ware);
								       
								 assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,ware,voucherType,voucherNb);
								 if(assetLifeVoucherItemBalance != null) {
								       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
								   }


								      
								   
							 
							 }
							 
							 else if(StringUtils.equalsIgnoreCase(voucherType,"GR") || StringUtils.equalsIgnoreCase(voucherType,"AR") || StringUtils.equalsIgnoreCase(voucherType,"FAR") ) {
								   
								   String voucherNb = voucherNB;
								   params.add(voucherNB);
								   if(StringUtils.equalsIgnoreCase(voucherType,"GR")) {
									   String GrQuery = "Select grPOid from GoodsReceived  where ID like :param_1";
									   voucherNb = VoucherNbr(session,GrQuery,params);
									   voucherType = "PO";
									   System.out.println("The poid for GR is " +voucherNb);
								    }
								   if(StringUtils.equalsIgnoreCase(voucherType,"AR")) {
								       String ArQuery = "Select poID from AssetRegistry  where arID like :param_1";
								       voucherNb = VoucherNbr(session,ArQuery,params);
								       voucherType = "PO";
								       System.out.println("The poid for AR is " +voucherNb);
							       }
								   if(StringUtils.equalsIgnoreCase(voucherType,"FAR")) {
									   String FarQuery = "Select PoId from FixedAssetRegistry  where farID like :param_1";  
									   voucherNb = VoucherNbr(session,FarQuery,params);
									   voucherType = "PO";
									   System.out.println("The poid for Far is " +voucherNb);
								   }
								   params.clear();
								   System.out.println("The voucherType is "+voucherType);
								   System.out.println("The voucher# is "+voucherNb);
								  
								   String itemCode = itemId;
								   System.out.println("The item code is " +itemCode);
								   System.out.println("The warehouse is " +ware);
								       
								       assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,ware,voucherType,voucherNb);
								       if(assetLifeVoucherItemBalance != null) {
									       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
									   }


								      
							   
							 }
					      }
						   
					   }
			   }
					
		 else {
				     
			 System.out.println("The ALL NOT NULL ");
			   
			   if(StringUtils.equalsIgnoreCase(voucherType,"PR")) {
					 params.add(voucherNB);
					 String voucherNBquery = "Select ID from PurchaseOrder  where ordPRQid like :param_1";
					 List<String> voucherNBList =  AssetLifeCycleLists (session,voucherNBquery,params);
					 params.clear();
					 voucherType = "PO";
					 System.out.println("The poids are " +mapper.writeValueAsString(voucherNBList));
					   
					   for(String voucherNb :voucherNBList) {
						   System.out.println("The voucherType is "+voucherType);
						   System.out.println("The voucher# is "+voucherNb);
						   
						   String itemCode = itemId;
						   System.out.println("The item code is " +itemCode);
						   System.out.println("The supplier is " +supplierId);
						       
						   assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
						   if(assetLifeVoucherItemBalance != null) {
						       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
						   }


						        
							    

						       
						   
						   }       
				 }
				 else if(StringUtils.equalsIgnoreCase(voucherType,"PO")) {
					 System.out.println("The voucherType is "+voucherType);
					 String voucherNb = voucherNB;
					 System.out.println("The voucher# is "+voucherNb);
					   
					 String itemCode = itemId;
					 System.out.println("The item code is " +itemCode);
					 System.out.println("The supplier is " +supplierId);
					       
					 assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
					 if(assetLifeVoucherItemBalance != null) {
					       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
					   }


					      
					   
				 
				 }
				 
				 else if(StringUtils.equalsIgnoreCase(voucherType,"GR") || StringUtils.equalsIgnoreCase(voucherType,"AR") || StringUtils.equalsIgnoreCase(voucherType,"FAR") ) {
					   
					   String voucherNb = voucherNB;
					   params.add(voucherNB);
					   if(StringUtils.equalsIgnoreCase(voucherType,"GR")) {
						   String GrQuery = "Select grPOid from GoodsReceived  where ID like :param_1";
						   voucherNb = VoucherNbr(session,GrQuery,params);
						   voucherType = "PO";
						   System.out.println("The poid for GR is " +voucherNb);
					    }
					   if(StringUtils.equalsIgnoreCase(voucherType,"AR")) {
					       String ArQuery = "Select poID from AssetRegistry  where arID like :param_1";
					       voucherNb = VoucherNbr(session,ArQuery,params);
					       voucherType = "PO";
					       System.out.println("The poid for AR is " +voucherNb);
				       }
					   if(StringUtils.equalsIgnoreCase(voucherType,"FAR")) {
						   String FarQuery = "Select PoId from FixedAssetRegistry  where farID like :param_1";  
						   voucherNb = VoucherNbr(session,FarQuery,params);
						   voucherType = "PO";
						   System.out.println("The poid for Far is " +voucherNb);
					   }
					   params.clear();
					   System.out.println("The voucherType is "+voucherType);
					   System.out.println("The voucher# is "+voucherNb);
					  
					   String itemCode = itemId;
					   System.out.println("The item code is " +itemCode);
					   System.out.println("The supplier is " +supplierId);
					       
					       assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
					       if(assetLifeVoucherItemBalance != null) {
						       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
						   }


					      
				   
				 }
		      } 
		   }
		   
		   else {
			   System.out.println("The ITEM IS NULL ");
			   if ((StringUtils.equalsIgnoreCase(supplierId, "") || StringUtils.equalsIgnoreCase(supplierId, null) || StringUtils.equalsIgnoreCase(supplierId, "null") || supplierId.isEmpty()) || (StringUtils.equalsIgnoreCase(wareId, "") || StringUtils.equalsIgnoreCase(wareId, null) || StringUtils.equalsIgnoreCase(wareId, "null") || wareId.isEmpty())){
				 
				   if ((StringUtils.equalsIgnoreCase(supplierId, "") || StringUtils.equalsIgnoreCase(supplierId, null) || StringUtils.equalsIgnoreCase(supplierId, "null") || supplierId.isEmpty()) && (StringUtils.equalsIgnoreCase(wareId, "") || StringUtils.equalsIgnoreCase(wareId, null) || StringUtils.equalsIgnoreCase(wareId, "null") || wareId.isEmpty())){
					   System.out.println("The SUPPLIER AND WAREHOUSE ARE NULL");
					 
					   if(StringUtils.equalsIgnoreCase(voucherType,"PR")) {
							 params.add(voucherNB);
							 String voucherNBquery = "Select ID from PurchaseOrder  where ordPRQid like :param_1";
							 List<String> voucherNBList =  AssetLifeCycleLists (session,voucherNBquery,params);
							 params.clear();
							 voucherType = "PO";
							 System.out.println("The poids are " +mapper.writeValueAsString(voucherNBList));
							   
							   for(String voucherNb :voucherNBList) {
								   System.out.println("The voucherType is "+voucherType);
								   System.out.println("The voucher# is "+voucherNb);
								   params.add(voucherType);
								   params.add(voucherNb);
								   String itemQuery ="Select distinct itemCode , Supplier, warehouse from AssetLifeCycle where voucherType like :param_1 and voucherNB like :param_2";
								
						    	   List<Object[]> itemSuppWareVoucherList =AssetLifeCycleListObject(session,itemQuery,params);
						    	   System.out.println("The itemVoucherList is " +mapper.writeValueAsString(itemSuppWareVoucherList));
								   params.clear();
								   
								   for (Object[] itemSuppWareParams : itemSuppWareVoucherList) {
								   
								   System.out.println("The item code is " +itemSuppWareParams[0]);
								   System.out.println("The supplier is " +itemSuppWareParams[1]);
								   System.out.println("The warehouse is " +itemSuppWareParams[2]);
								   String itemCode =String.valueOf( itemSuppWareParams[0]);
								   supplierId =String.valueOf( itemSuppWareParams[1]);   
								   wareId =String.valueOf( itemSuppWareParams[2]);   

								   assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
								   if(assetLifeVoucherItemBalance != null) {
								       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
								   }

								   }
								        
									    

								       
								   
								   }       
						 }
						 else if(StringUtils.equalsIgnoreCase(voucherType,"PO")) {
							 System.out.println("The voucherType is "+voucherType);
							 String voucherNb = voucherNB;
							 System.out.println("The voucher# is "+voucherNb);
							 params.add(voucherType);
							 params.add(voucherNb);
							 String itemQuery ="Select distinct itemCode , Supplier, warehouse from AssetLifeCycle where voucherType like :param_1 and voucherNB like :param_2";
							
					    	 List<Object[]> itemSuppWareVoucherList =AssetLifeCycleListObject(session,itemQuery,params);
					    	 System.out.println("The itemVoucherList is " +mapper.writeValueAsString(itemSuppWareVoucherList));
							 params.clear();
							   
							 for (Object[] itemSuppWareParams : itemSuppWareVoucherList) {
							   
							   System.out.println("The item code is " +itemSuppWareParams[0]);
							   System.out.println("The supplier is " +itemSuppWareParams[1]);
							   System.out.println("The warehouse is " +itemSuppWareParams[2]);
							   String itemCode =String.valueOf( itemSuppWareParams[0]);
							   supplierId =String.valueOf( itemSuppWareParams[1]);   
							   wareId =String.valueOf( itemSuppWareParams[2]);   
       
							   assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
							   if(assetLifeVoucherItemBalance != null) {
							       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
							   }

							 }     
							   
						 
						 }
						 
						 else if(StringUtils.equalsIgnoreCase(voucherType,"GR") || StringUtils.equalsIgnoreCase(voucherType,"AR") || StringUtils.equalsIgnoreCase(voucherType,"FAR") ) {
							   
							   String voucherNb = voucherNB;
							   params.add(voucherNB);
							   if(StringUtils.equalsIgnoreCase(voucherType,"GR")) {
								   String GrQuery = "Select grPOid from GoodsReceived  where ID like :param_1";
								   voucherNb = VoucherNbr(session,GrQuery,params);
								   voucherType = "PO";
								   System.out.println("The poid for GR is " +voucherNb);
							    }
							   if(StringUtils.equalsIgnoreCase(voucherType,"AR")) {
							       String ArQuery = "Select poID from AssetRegistry  where arID like :param_1";
							       voucherNb = VoucherNbr(session,ArQuery,params);
							       voucherType = "PO";
							       System.out.println("The poid for AR is " +voucherNb);
						       }
							   if(StringUtils.equalsIgnoreCase(voucherType,"FAR")) {
								   String FarQuery = "Select PoId from FixedAssetRegistry  where farID like :param_1";  
								   voucherNb = VoucherNbr(session,FarQuery,params);
								   voucherType = "PO";
								   System.out.println("The poid for Far is " +voucherNb);
							   }
							   params.clear();
							   System.out.println("The voucherType is "+voucherType);
							   System.out.println("The voucher# is "+voucherNb);
							   params.add(voucherType);
							   params.add(voucherNb);
							   String itemQuery ="Select distinct itemCode , Supplier, warehouse from AssetLifeCycle where voucherType like :param_1 and voucherNB like :param_2";
							
					    	   List<Object[]> itemSuppWareVoucherList =AssetLifeCycleListObject(session,itemQuery,params);
					    	   if (!itemSuppWareVoucherList.isEmpty()) {
					    	   System.out.println("The itemVoucherList is " +mapper.writeValueAsString(itemSuppWareVoucherList));
							   params.clear();
							   
							   for (Object[] itemSuppWareParams : itemSuppWareVoucherList) {
							   
							      System.out.println("The item code is " +itemSuppWareParams[0]);
							      System.out.println("The supplier is " +itemSuppWareParams[1]);
							      System.out.println("The warehouse is " +itemSuppWareParams[2]);
							      String itemCode =String.valueOf( itemSuppWareParams[0]);
							      supplierId =String.valueOf( itemSuppWareParams[1]);   
							      wareId =String.valueOf( itemSuppWareParams[2]);   
     
							       assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
							       if(assetLifeVoucherItemBalance != null) {
								       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
								   }

							   }
					      }
					    	
						
				   }
					   
				   }  
				   
				   
				   if ((StringUtils.equalsIgnoreCase(supplierId, "") || StringUtils.equalsIgnoreCase(supplierId, null) || StringUtils.equalsIgnoreCase(supplierId, "null") || supplierId.isEmpty()) && !(StringUtils.equalsIgnoreCase(wareId, "") || StringUtils.equalsIgnoreCase(wareId, null) || StringUtils.equalsIgnoreCase(wareId, "null") || wareId.isEmpty())){
					   System.out.println("The SUPPLIER IS NULL");
					    
					   	   if(StringUtils.equalsIgnoreCase(voucherType,"PR")) {
								 params.add(voucherNB);
								 String voucherNBquery = "Select ID from PurchaseOrder  where ordPRQid like :param_1";
								 List<String> voucherNBList =AssetLifeCycleLists (session,voucherNBquery,params);
								 params.clear();
								 voucherType = "PO";
								 System.out.println("The poids are " +mapper.writeValueAsString(voucherNBList));
								   
								   for(String voucherNb :voucherNBList) {
									   System.out.println("The voucherType is "+voucherType);
									   System.out.println("The voucher# is "+voucherNb);
									   
									   params.add(voucherType);
									   params.add(voucherNb);
									   params.add(wareId); 
									   String itemSuppQuery ="Select distinct itemCode, Supplier from AssetLifeCycle where voucherType like :param_1 and voucherNB like :param_2 and  warehouse like :param_3";
									
									   List<Object[]> itemSuppVoucherList =AssetLifeCycleListObject(session,itemSuppQuery,params);
							    	   if (!itemSuppVoucherList.isEmpty()) {
							    	   System.out.println("The itemVoucherList is " +mapper.writeValueAsString(itemSuppVoucherList));
									   params.clear();
									   
									   for (Object[] itemSuppParams : itemSuppVoucherList) {
									   
									      System.out.println("The item code is " +itemSuppParams[0]);
									      System.out.println("The supplier is " +itemSuppParams[1]);
									      String itemCode =String.valueOf( itemSuppParams[0]);
									      supplierId =String.valueOf( itemSuppParams[1]);   
		          
									      assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
									      if(assetLifeVoucherItemBalance != null) {
										       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
										   }

									   }
									        
										    
							     }
									       
									   
							   }       
							 }
							 else if(StringUtils.equalsIgnoreCase(voucherType,"PO")) {
								 System.out.println("The voucherType is "+voucherType);
								 String voucherNb = voucherNB;
								 System.out.println("The voucher# is "+voucherNb);
								 params.add(voucherType);
								 params.add(voucherNb);
								 params.add(wareId); 
								 String itemSuppQuery ="Select distinct itemCode, Supplier from AssetLifeCycle where voucherType like :param_1 and voucherNB like :param_2 and  warehouse like :param_3";
									
								   List<Object[]> itemSuppVoucherList =AssetLifeCycleListObject(session,itemSuppQuery,params);
						    	   if (!itemSuppVoucherList.isEmpty()) {
						    	   System.out.println("The itemVoucherList is " +mapper.writeValueAsString(itemSuppVoucherList));
								   params.clear();
								   
								   for (Object[] itemSuppParams : itemSuppVoucherList) {
								   
								      System.out.println("The item code is " +itemSuppParams[0]);
								      System.out.println("The supplier is " +itemSuppParams[1]);
								      String itemCode =String.valueOf( itemSuppParams[0]);
								      supplierId =String.valueOf( itemSuppParams[1]);   
	                
								      assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
								      if(assetLifeVoucherItemBalance != null) {
									       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
									   }

								 }
								      
						       }   
							 
							 }
							 
							 else if(StringUtils.equalsIgnoreCase(voucherType,"GR") || StringUtils.equalsIgnoreCase(voucherType,"AR") || StringUtils.equalsIgnoreCase(voucherType,"FAR") ) {
								   
								   String voucherNb = voucherNB;
								   params.add(voucherNB);
								   if(StringUtils.equalsIgnoreCase(voucherType,"GR")) {
									   String GrQuery = "Select grPOid from GoodsReceived  where ID like :param_1";
									   voucherNb = VoucherNbr(session,GrQuery,params);
									   voucherType = "PO";
									   System.out.println("The poid for GR is " +voucherNb);
								    }
								   if(StringUtils.equalsIgnoreCase(voucherType,"AR")) {
								       String ArQuery = "Select poID from AssetRegistry  where arID like :param_1";
								       voucherNb = VoucherNbr(session,ArQuery,params);
								       voucherType = "PO";
								       System.out.println("The poid for AR is " +voucherNb);
							       }
								   if(StringUtils.equalsIgnoreCase(voucherType,"FAR")) {
									   String FarQuery = "Select PoId from FixedAssetRegistry  where farID like :param_1";  
									   voucherNb = VoucherNbr(session,FarQuery,params);
									   voucherType = "PO";
									   System.out.println("The poid for Far is " +voucherNb);
								   }
								   params.clear();
								   System.out.println("The voucherType is "+voucherType);
								   System.out.println("The voucher# is "+voucherNb);
								  
								   params.add(voucherType);
								   params.add(voucherNb);
								   params.add(wareId); 
								   String itemSuppQuery ="Select distinct itemCode, Supplier from AssetLifeCycle where voucherType like :param_1 and voucherNB like :param_2 and  warehouse like :param_3";
									
								   List<Object[]> itemSuppVoucherList =AssetLifeCycleListObject(session,itemSuppQuery,params);
						    	   if (!itemSuppVoucherList.isEmpty()) {
						    	   System.out.println("The itemVoucherList is " +mapper.writeValueAsString(itemSuppVoucherList));
								   params.clear();
								   
								   for (Object[] itemSuppParams : itemSuppVoucherList) {
								   
								      System.out.println("The item code is " +itemSuppParams[0]);
								      System.out.println("The supplier is " +itemSuppParams[1]);
								      String itemCode =String.valueOf( itemSuppParams[0]);
								      supplierId =String.valueOf( itemSuppParams[1]);   
	                
								      assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
								      if(assetLifeVoucherItemBalance != null) {
									       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
									   }

								 }
							  }
								      
							   
					    }
				   }
						   
				     
					   
				   
				   if (!(StringUtils.equalsIgnoreCase(supplierId, "") || StringUtils.equalsIgnoreCase(supplierId, null) || StringUtils.equalsIgnoreCase(supplierId, "null") || supplierId.isEmpty()) && (StringUtils.equalsIgnoreCase(wareId, "") || StringUtils.equalsIgnoreCase(wareId, null) || StringUtils.equalsIgnoreCase(wareId, "null") || wareId.isEmpty())){
					   System.out.println("The WAREHOUSE IS NULL");
					  
					     if(StringUtils.equalsIgnoreCase(voucherType,"PR")) {
							     params.add(voucherNB);
								 String voucherNBquery = "Select ID from PurchaseOrder  where ordPRQid like :param_1";
								 List<String> voucherNBList = AssetLifeCycleLists (session,voucherNBquery,params);
								 params.clear();
								 voucherType = "PO";
								 System.out.println("The poids are " +mapper.writeValueAsString(voucherNBList));
								   
								   for(String voucherNb :voucherNBList) {
									   System.out.println("The voucherType is "+voucherType);
									   System.out.println("The voucher# is "+voucherNb);
									   
									   params.add(voucherType);
									   params.add(voucherNb);
									   params.add(supplierId);
									   String itemWareQuery ="Select distinct itemCode, warehouse from AssetLifeCycle where voucherType like :param_1 and voucherNB like :param_2 and  Supplier like :param_3";
									   
									   List<Object[]> itemWareVoucherList =AssetLifeCycleListObject(session,itemWareQuery,params);
							    	   if (!itemWareVoucherList.isEmpty()) {
							    	   System.out.println("The itemVoucherList is " +mapper.writeValueAsString(itemWareVoucherList));
									   params.clear();
									   
									   for (Object[] itemWareParams : itemWareVoucherList) {
									   
									      System.out.println("The item code is " +itemWareParams[0]);
									      System.out.println("The warehouse is " +itemWareParams[1]);
									      String itemCode =String.valueOf( itemWareParams[0]);
									      wareId =String.valueOf( itemWareParams[1]);   
		                   
									       assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
									       if(assetLifeVoucherItemBalance != null) {
										       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
										   }


									   }     
										    

							    	   }   
									   
									   }
								      
								   
						   }
							 else if(StringUtils.equalsIgnoreCase(voucherType,"PO")) {
								 params.add(voucherNB);
								 String voucherNBquery = "Select ID from PurchaseOrder  where ordPRQid like :param_1";
								 List<String> voucherNBList =AssetLifeCycleLists (session,voucherNBquery,params);
								 params.clear();
								 voucherType = "PO";
								 System.out.println("The poids are " +mapper.writeValueAsString(voucherNBList));
								   
								   for(String voucherNb :voucherNBList) {
									   System.out.println("The voucherType is "+voucherType);
									   System.out.println("The voucher# is "+voucherNb);
									   
									   params.add(voucherType);
									   params.add(voucherNb);
									   params.add(supplierId);
	                                   String itemWareQuery ="Select distinct itemCode, warehouse from AssetLifeCycle where voucherType like :param_1 and voucherNB like :param_2 and  Supplier like :param_3";
									   
									   List<Object[]> itemWareVoucherList =AssetLifeCycleListObject(session,itemWareQuery,params);
							    	   if (!itemWareVoucherList.isEmpty()) {
							    	   System.out.println("The itemVoucherList is " +mapper.writeValueAsString(itemWareVoucherList));
									   params.clear();
									   
									   for (Object[] itemWareParams : itemWareVoucherList) {
									   
									      System.out.println("The item code is " +itemWareParams[0]);
									      System.out.println("The warehouse is " +itemWareParams[1]);
									      String itemCode =String.valueOf( itemWareParams[0]);
									      wareId =String.valueOf( itemWareParams[1]);   
		                   
									       assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
									       if(assetLifeVoucherItemBalance != null) {
										       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
										   }

									   }     
										    

							    	   }     
										    

									       
									   
									   }       
								   
							 
							 }
							 
							 else if(StringUtils.equalsIgnoreCase(voucherType,"GR") || StringUtils.equalsIgnoreCase(voucherType,"AR") || StringUtils.equalsIgnoreCase(voucherType,"FAR") ) {
								   
								   String voucherNb = voucherNB;
								   params.add(voucherNB);
								   if(StringUtils.equalsIgnoreCase(voucherType,"GR")) {
									   String GrQuery = "Select grPOid from GoodsReceived  where ID like :param_1";
									   voucherNb = VoucherNbr(session,GrQuery,params);
									   voucherType = "PO";
									   System.out.println("The poid for GR is " +voucherNb);
								    }
								   if(StringUtils.equalsIgnoreCase(voucherType,"AR")) {
								       String ArQuery = "Select poID from AssetRegistry  where arID like :param_1";
								       voucherNb = VoucherNbr(session,ArQuery,params);
								       voucherType = "PO";
								       System.out.println("The poid for AR is " +voucherNb);
							       }
								   if(StringUtils.equalsIgnoreCase(voucherType,"FAR")) {
									   String FarQuery = "Select PoId from FixedAssetRegistry  where farID like :param_1";  
									   voucherNb = VoucherNbr(session,FarQuery,params);
									   voucherType = "PO";
									   System.out.println("The poid for Far is " +voucherNb);
								   }
								   params.clear();
								   System.out.println("The voucherType is "+voucherType);
								   System.out.println("The voucher# is "+voucherNb);
								   
								   params.add(voucherType);
								   params.add(voucherNb);
								   params.add(supplierId);
								   String itemWareQuery ="Select distinct itemCode, warehouse from AssetLifeCycle where voucherType like :param_1 and voucherNB like :param_2 and  Supplier like :param_3";
								   
								   List<Object[]> itemWareVoucherList =AssetLifeCycleListObject(session,itemWareQuery,params);
						    	   if (!itemWareVoucherList.isEmpty()) {
						    	   System.out.println("The itemVoucherList is " +mapper.writeValueAsString(itemWareVoucherList));
								   params.clear();
								   
								   for (Object[] itemWareParams : itemWareVoucherList) {
								   
								      System.out.println("The item code is " +itemWareParams[0]);
								      System.out.println("The warehouse is " +itemWareParams[1]);
								      String itemCode =String.valueOf( itemWareParams[0]);
								      wareId =String.valueOf( itemWareParams[1]);   
	                   
								       assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
								       if(assetLifeVoucherItemBalance != null) {
									       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
									   }

								   }     
									    

						    	   }   
							   
							 }
								  
								   
								      
							   
							 }
					      }
						   
					  
			   
	
			   
		   else {   
			   System.out.println("The ITEM IS NULL AND SUPP AND WARE AVAILABLE");
		       if(StringUtils.equalsIgnoreCase(voucherType,"PR")) {
			       params.add(voucherNB);
			       String voucherNBquery = "Select ID from PurchaseOrder  where ordPRQid like :param_1";
			       List<String> voucherNBList =  AssetLifeCycleLists (session,voucherNBquery,params);
			       params.clear();
			       voucherType = "PO";
			       System.out.println("The poids are " +mapper.writeValueAsString(voucherNBList));
			   
			       for(String voucherNb :voucherNBList) {
				     System.out.println("The voucherType is "+voucherType);
				     System.out.println("The voucher# is "+voucherNb);
				   
				     params.add(voucherType);
				     params.add(voucherNb);
				     String itemQuery ="Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2";
				   
		    	     List<String> itemVoucherList =AssetLifeCycleLists (session,itemQuery,params);
		    	     System.out.println("The itemVoucherList is " +mapper.writeValueAsString(itemVoucherList));
		    	     params.clear();
				   
				     for (String item : itemVoucherList) {
					   
				       String itemCode = item;
				       System.out.println("The item code is " +itemCode);
				       System.out.println("The supplier is " +supplierId);
				       
				       assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
				       if(assetLifeVoucherItemBalance != null) {
					       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
					   }


				   }     
					    

				       
				   
				   }       
		 }
		 else if(StringUtils.equalsIgnoreCase(voucherType,"PO")) {
			 System.out.println("The voucherType is "+voucherType);
			 String voucherNb = voucherNB;
			 System.out.println("The voucher# is "+voucherNb);
			   
			   params.add(voucherType);
			   params.add(voucherNb);
			   String itemQuery ="Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2";
			
	    	   List<String> itemVoucherList =AssetLifeCycleLists (session,itemQuery,params);
	    	   System.out.println("The itemVoucherList is " +mapper.writeValueAsString(itemVoucherList));
			   params.clear();
			   
			   for (String item : itemVoucherList) {
				   
			       String itemCode = item;
			       System.out.println("The item code is " +itemCode);
			       System.out.println("The supplier is " +supplierId);
			       
			       assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
			       if(assetLifeVoucherItemBalance != null) {
				       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
				   }

			   }   
			   
		 
		 }
		 
		 else if(StringUtils.equalsIgnoreCase(voucherType,"GR") || StringUtils.equalsIgnoreCase(voucherType,"AR") || StringUtils.equalsIgnoreCase(voucherType,"FAR") ) {
			   
			   String voucherNb = voucherNB;
			   params.add(voucherNB);
			   if(StringUtils.equalsIgnoreCase(voucherType,"GR")) {
				   String GrQuery = "Select grPOid from GoodsReceived  where ID like :param_1";
				   voucherNb = VoucherNbr(session,GrQuery,params);
				   voucherType = "PO";
				   System.out.println("The poid for GR is " +voucherNb);
			    }
			   if(StringUtils.equalsIgnoreCase(voucherType,"AR")) {
			       String ArQuery = "Select poID from AssetRegistry  where arID like :param_1";
			       voucherNb = VoucherNbr(session,ArQuery,params);
			       voucherType = "PO";
			       System.out.println("The poid for AR is " +voucherNb);
		       }
			   if(StringUtils.equalsIgnoreCase(voucherType,"FAR")) {
				   String FarQuery = "Select PoId from FixedAssetRegistry  where farID like :param_1";  
				   voucherNb = VoucherNbr(session,FarQuery,params);
				   voucherType = "PO";
				   System.out.println("The poid for Far is " +voucherNb);
			   }
			   params.clear();
			   System.out.println("The voucherType is "+voucherType);
			   System.out.println("The voucher# is "+voucherNb);
			   
			   params.add(voucherType);
			   params.add(voucherNb);
			   String itemQuery ="Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2";
			
	    	   List<String> itemVoucherList =AssetLifeCycleLists (session,itemQuery,params);
	    	   System.out.println("The itemVoucherList is " +mapper.writeValueAsString(itemVoucherList));
			   params.clear();
			   
			   for (String item : itemVoucherList) {
				   
			       String itemCode = item;
			       System.out.println("The item code is " +itemCode);
			       System.out.println("The supplier is " +supplierId);
			       
			       assetLifeVoucherItemBalance = findBalanceListParameters(session,3,itemCode,StartDate,EndDate,supplierId,wareId,voucherType,voucherNb);
			       if(assetLifeVoucherItemBalance != null) {
				       assetLifeVouchersItemBalance.add(assetLifeVoucherItemBalance);
				   }


			   }   
		   
		 }
		}
		   
	}  
		   
		   System.out.println("###### The assetLifeVouchersItemBalance " + mapper.writeValueAsString(assetLifeVouchersItemBalance));
	
		   
		   
		return assetLifeVouchersItemBalance;
	}

	public List<AssetLifeCycle_Ledger> findLegerListBy(String itemCode,Timestamp StartDate,Timestamp EndDate, String wareId ,String supplierId, String voucherType, String voucherNb ) throws Exception{
	

		
		List<AssetLifeCycle_Ledger> assetLifeLedgerItem = new ArrayList<AssetLifeCycle_Ledger>();
		ArrayList<AssetLifeCycle_Ledger> assetLifeLedgerItemArray = new ArrayList<AssetLifeCycle_Ledger>();

		List<Object> params = new ArrayList<Object>();
		
		
		   
		   if (!itemCode.isEmpty()){
			   
				//// item code, warehouse and supplier are checked
				   if (!wareId.isEmpty()) {
				
					   if (!supplierId.isEmpty() ){
						   String itemQuery = null;
						   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
				                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
							   System.out.println("******The Item , Supplier and Warehouse , voucher type and Nb are checked");
							   itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.itemCode like :param_3 and t.Supplier like :param_4 and (t.creationDate >= :param_5 and t.creationDate <= :param_6) and  t.warehouse like :param_7 order by t.creationDate desc"; 
							   params.add(voucherType);
							   params.add(voucherNb);
						   }
						   else {
						      System.out.println("******The Item , Supplier and Warehouse are checked");
						      itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.itemCode like :param_1 and t.Supplier like :param_2 and (t.creationDate >= :param_3 and t.creationDate <= :param_4) and  t.warehouse like :param_5 order by t.creationDate desc"; 
						   }
						   params.add(itemCode);
						   params.add(supplierId);
						   params.add(StartDate);
						   params.add(EndDate);
						   params.add(wareId);
							
						  
							assetLifeLedgerItem =  AssetLifeLedgerItem(session,itemQuery,params);
						    System.out.println("******The  assetLifeItemLedger is " + mapper.writeValueAsString(assetLifeLedgerItem));
							
						    assetLifeLedgerItemArray.addAll(assetLifeLedgerItem);
							System.out.println("******The  assetLifeLedgerItemArray is " + mapper.writeValueAsString(assetLifeLedgerItemArray));
		   	      

								   
						     
					   }
					//// item code and warehouse are checked
					   else {
						   String ware = wareId;
						   String itemQuery = null;
						   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
				                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
							   System.out.println("******The Item , Warehouse , voucher type and Nb are checked");
							   itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.itemCode like :param_3 and (t.creationDate >= :param_4 and t.creationDate <= :param_5) and  t.warehouse like :param_6 order by t.creationDate desc"; 
							   params.add(voucherType);
							   params.add(voucherNb);
						   }
						   else {
							   System.out.println("******The Item and Warehouse are checked");
						   
						       System.out.println("The Warehouse is "+ware);
						       System.out.println("The Item code is "+itemCode);
						   itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.itemCode like :param_1 and (t.creationDate >= :param_2 and t.creationDate <= :param_3) and  t.warehouse like :param_4 order by t.creationDate desc"; 
						   }
						   params.add(itemCode);
						   params.add(StartDate);
						   params.add(EndDate);
						   params.add(ware);
							
						  
							assetLifeLedgerItem =  AssetLifeLedgerItem(session,itemQuery,params);
						    System.out.println("******The  assetLifeItemLedger is " + mapper.writeValueAsString(assetLifeLedgerItem));
								
						    assetLifeLedgerItemArray.addAll(assetLifeLedgerItem);
							System.out.println("******The  assetLifeLedgerItemArray is " + mapper.writeValueAsString(assetLifeLedgerItemArray));
		   
							
						         
						      }
				        
					   
				   }
				   //// warehouse is not checked
				   else {
					////item and supplier are checked
					   if (!supplierId.isEmpty()){
						   String itemQuery = null;
						   String wareListQuery = null;
						   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
				                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
							   System.out.println("******The Item , Supplier, voucher type and Nb are checked");
							   wareListQuery = "Select distinct t.warehouse as warehouse from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.itemCode like :param_3 and Supplier like :param_4";

							   itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.itemCode like :param_3 and t.Supplier like :param_4 and (t.creationDate >= :param_5 and t.creationDate <= :param_6) order by t.creationDate desc"; 
							   params.add(voucherType);
							   params.add(voucherNb);
						   }
						   else { 
							   System.out.println("******The Item and Supplier are checked");
						   
						       System.out.println("The Item code is "+itemCode);
						       System.out.println("The Supplier is "+supplierId);
						       wareListQuery = "Select distinct t.warehouse as warehouse from AssetLifeCycle t where t.itemCode like :param_1 and Supplier like :param_2";
						   }
						   params.add(itemCode);
						   params.add(supplierId);
						   
				    	   List<String> wareList =AssetLifeCycleLists (session,wareListQuery,params);
							
						 
						   System.out.println("The wareList is " +mapper.writeValueAsString(wareList));
						  
						   for (String ware : wareList) {
							   
							   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
					                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
							
								   itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.itemCode like :param_3 and t.Supplier like :param_4 and (t.creationDate >= :param_5 and t.creationDate <= :param_6) and  t.warehouse like :param_7 order by t.creationDate desc"; 
						      }
						      else {
							   itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.itemCode like :param_1 and t.Supplier like :param_2 and (t.creationDate >= :param_3 and t.creationDate <= :param_4) and  t.warehouse like :param_5 order by t.creationDate desc"; 
						      }
						        params.add(StartDate);
								params.add(EndDate);
								params.add(ware);
								
							  
								assetLifeLedgerItem =  AssetLifeLedgerItem(session,itemQuery,params);
							    System.out.println("******The  assetLifeItemLedger is " + mapper.writeValueAsString(assetLifeLedgerItem));
								
							    assetLifeLedgerItemArray.addAll(assetLifeLedgerItem);
							    System.out.println("******The  assetLifeLedgerItemArray is " + mapper.writeValueAsString(assetLifeLedgerItemArray));
			   
							    params.remove(ware);
								params.remove(StartDate);
								params.remove(EndDate);   

						   }       
				
					   }
					   ////item code is checked supplier and warehouse are not checked
					   else {
						   String wareListQuery = null, itemQuery = null;
						   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
				                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
							   System.out.println("******The Item , voucher type and Nb are checked");
							   wareListQuery = "Select distinct t.warehouse as warehouse from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.itemCode like :param_3";

							   params.add(voucherType);
							   params.add(voucherNb);
						   }
						   else {
						       System.out.println("********The Item code is checked");
						       System.out.println("The Item code is " +itemCode);
						       wareListQuery = "Select distinct t.warehouse as warehouse from AssetLifeCycle t where t.itemCode like :param_1";
						   }
						   params.add(itemCode);
						  
				  	       List<String> wareList =AssetLifeCycleLists (session,wareListQuery,params);
						
						   System.out.println("The wareList is " +mapper.writeValueAsString(wareList));
						   
						   for (String ware : wareList) {
							   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
					                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
								   itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.itemCode like :param_3 and (t.creationDate >= :param_4 and t.creationDate <= :param_5) and  t.warehouse like :param_6 order by t.creationDate desc"; 

							   }
							   else {
							     itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.itemCode like :param_1 and (t.creationDate >= :param_2 and t.creationDate <= :param_3) and  t.warehouse like :param_4 order by t.creationDate desc"; 
							   }
							   params.add(StartDate);
							   params.add(EndDate);
							   params.add(ware);
								
							  
								assetLifeLedgerItem =  AssetLifeLedgerItem(session,itemQuery,params);
							    System.out.println("******The  assetLifeItemLedger is " + mapper.writeValueAsString(assetLifeLedgerItem));
								
							    assetLifeLedgerItemArray.addAll(assetLifeLedgerItem);
								System.out.println("******The  assetLifeLedgerItemArray is " + mapper.writeValueAsString(assetLifeLedgerItemArray));
			   
								params.remove(ware);
								params.remove(StartDate);
								params.remove(EndDate);
							   
						   }
						  
						   
			     }
					   
				   }
		   }
		   //item is not checked
		   else {
			   
			   
			   if (!wareId.isEmpty()) {
					
				   if (!supplierId.isEmpty() ){
					   String itemListQuery = null,  itemQuery = null;
					   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
			                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
						   System.out.println("******The Item is not checked, Supplier and Warehouse , voucher type and nb are checked");
						   itemListQuery = "Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.warehouse like :param_3 and t.Supplier like :param_4";
						   params.add(voucherType);
						   params.add(voucherNb);
					   }
					   else {
					       System.out.println("******The Item is not checked, Supplier and Warehouse are checked");
					       itemListQuery = "Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.warehouse like :param_1 and t.Supplier like :param_2";
					   }
					   params.add(wareId);
					   params.add(supplierId);
					   List<String> itemList =AssetLifeCycleLists (session,itemListQuery,params);
                       //params.clear();
					   System.out.println("The itemList is " +mapper.writeValueAsString(itemList));
					   params.add(StartDate);
					   params.add(EndDate);
					  // String itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD HH24:MI:SS') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.itemCode like :param_5 and t.Supplier like :param_2 and (t.creationDate >= :param_3 and t.creationDate <= :param_4) and  t.warehouse like :param_1 order by t.creationDate desc"; 

					  // String itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD HH24:MI:SS') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.itemCode like :param_5 and t.Supplier like :param_2 and (t.creationDate >= :param_3 and t.creationDate <= :param_4) and  t.warehouse like :param_1 order by t.creationDate desc"; 

					   for (String item : itemList) {
						   itemCode = item; 
						   System.out.println("The Item code is "+itemCode);
						   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
				                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
							    itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.itemCode like :param_7 and t.Supplier like :param_4 and (t.creationDate >= :param_5 and t.creationDate <= :param_6) and  t.warehouse like :param_3 order by t.creationDate desc"; 

						   }
						   else {
						      itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.itemCode like :param_5 and t.Supplier like :param_2 and (t.creationDate >= :param_3 and t.creationDate <= :param_4) and  t.warehouse like :param_1 order by t.creationDate desc"; 
						   }
						  
						   params.add(itemCode);
						   assetLifeLedgerItem =  AssetLifeLedgerItem(session,itemQuery,params);
						   System.out.println("******The  assetLifeItemLedger is " + mapper.writeValueAsString(assetLifeLedgerItem));
						   params.remove(itemCode);
						   assetLifeLedgerItemArray.addAll(assetLifeLedgerItem);
						   System.out.println("******The  assetLifeLedgerItemArray is " + mapper.writeValueAsString(assetLifeLedgerItemArray));
	   
					   }
					  
					     
				   }
				//// item code is not checked and warehouse is checked
				   else {
					   String itemListQuery = null, itemLedgerQuery = null;
					   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
			                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
						   System.out.println("******The Item is not checked and Warehouse, voucher type an nb are checked");

						   itemListQuery = "Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.warehouse like :param_3";
						   params.add(voucherType);
						   params.add(voucherNb);
					   }
					   else {
					      System.out.println("******The Item is not checked and Warehouse is checked");
					    
					      System.out.println("The Warehouse is "+wareId);
					      itemListQuery = "Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.warehouse like :param_1";
					      System.out.println("The list of params is " +params);
					   }
					   params.add(wareId);
					   System.out.println("The list of params is after " +params);
					   List<String> itemList =AssetLifeCycleLists (session,itemListQuery,params);

					   //System.out.println("The itemList is " +mapper.writeValueAsString(itemList));
					   //System.out.println("The StartDate is " +StartDate);
					   //System.out.println("The EndDate is " +EndDate);
					   

					   for (String item : itemList) {
						  
						   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
				                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
							   itemLedgerQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.itemCode like :param_6 and (t.creationDate >= :param_4 and t.creationDate <= :param_5) and  t.warehouse like :param_3 order by t.creationDate desc"; 

						   }
						   else{
							   itemLedgerQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.itemCode like :param_4 and (t.creationDate >= :param_2 and t.creationDate <= :param_3) and  t.warehouse like :param_1 order by t.creationDate desc"; 
						   }

						   System.out.println("The list of params is inside loop " +params);
						   params.add(StartDate);
						   params.add(EndDate);
						   params.add(item);
						   System.out.println("The list of params is after params in loop " +params);
						   assetLifeLedgerItem =  AssetLifeLedgerItem (session, itemLedgerQuery, params);
						   System.out.println("******The  assetLifeItemLedger is " + mapper.writeValueAsString(assetLifeLedgerItem));
						   params.remove(StartDate);
						   params.remove(EndDate);
						   params.remove(item);
						   
						   assetLifeLedgerItemArray.addAll(assetLifeLedgerItem);
						   System.out.println("******The  assetLifeLedgerItemArray is " + mapper.writeValueAsString(assetLifeLedgerItemArray));
	   
					   }
					  
					         
					      }
			        
				   
			   }
			   //// warehouse is not checked
			   else {
				//// supplier only checked
				   if (!supplierId.isEmpty()){
					   String itemListQuery = null, itemQuery = null, wareListQuery = null;
					   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
			                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
						   System.out.println("******The Item is not checked and Supplier, voucher type and nb are checked");
						   itemListQuery = "Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.Supplier like :param_3";
						   params.add(voucherType);
						   params.add(voucherNb);
					   }
					   else {
					      System.out.println("******The Item is not checked and Supplier is checked");
					
					      System.out.println("The Supplier is "+supplierId);
					      itemListQuery = "Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.Supplier like :param_1";
					   }
					   params.add(supplierId);
					   List<String> itemList =AssetLifeCycleLists (session,itemListQuery,params);

					   System.out.println("The itemList is " +mapper.writeValueAsString(itemList));
					  
					   for (String item : itemList) {
						   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
				                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
							    wareListQuery = "Select distinct t.warehouse as warehouse from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.itemCode like :param_4 and t.Supplier like :param_3";

						   }
					
						   else{
							    wareListQuery = "Select distinct t.warehouse as warehouse from AssetLifeCycle t where t.itemCode like :param_2 and t.Supplier like :param_1";
						   }
						   params.add(item);
					  
			    	  
			    	       List<String> wareList =AssetLifeCycleLists (session,wareListQuery,params);
						
					 
					       System.out.println("The wareList is " +mapper.writeValueAsString(wareList));
					   
					      for (String ware : wareList) {
					    	  if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
					                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
					    		  itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.itemCode like :param_4 and t.Supplier like :param_3 and (t.creationDate >= :param_5 and t.creationDate <= :param_6) and  t.warehouse like :param_7 order by t.creationDate desc"; 

					    	  }
						   
					    	  else{
					    		  itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.itemCode like :param_2 and t.Supplier like :param_1 and (t.creationDate >= :param_3 and t.creationDate <= :param_4) and  t.warehouse like :param_5 order by t.creationDate desc"; 
					    	  }
						      params.add(StartDate);
							  params.add(EndDate);
							  params.add(ware);
							
						  
							assetLifeLedgerItem =  AssetLifeLedgerItem(session,itemQuery,params);
						    System.out.println("******The  assetLifeItemLedger is " + mapper.writeValueAsString(assetLifeLedgerItem));
						    params.remove(EndDate);
						    params.remove(StartDate);
						    params.remove(ware);
						    assetLifeLedgerItemArray.addAll(assetLifeLedgerItem);
						    System.out.println("******The  assetLifeLedgerItemArray is " + mapper.writeValueAsString(assetLifeLedgerItemArray));
    

					   }
					   params.remove(item);
					   
					   } 
					   
			   }
			   if (!(StringUtils.equalsIgnoreCase(voucherNb, "") || StringUtils.equalsIgnoreCase(voucherNb, null) || StringUtils.equalsIgnoreCase(voucherNb, "null") || voucherNb.isEmpty())
		                   && !(StringUtils.equalsIgnoreCase(voucherType, "") || StringUtils.equalsIgnoreCase(voucherType, null) || StringUtils.equalsIgnoreCase(voucherType, "null") || voucherType.isEmpty())){
					   System.out.println("******The Item is not checked only voucher type and nb are checked");
					   String itemListQuery = "Select distinct t.itemCode as itemCode from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2";
					   params.add(voucherType);
					   params.add(voucherNb);
					   
					   List<String> itemList =AssetLifeCycleLists (session,itemListQuery,params);

					   System.out.println("The itemList is " +mapper.writeValueAsString(itemList));
					  
					   for (String item : itemList) {
				    		 String itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.voucherType like :param_1 and t.voucherNB like :param_2 and t.itemCode like :param_3 and (t.creationDate >= :param_4 and t.creationDate <= :param_5) order by t.creationDate desc"; 
				    		 params.add(item);
				    		 params.add(StartDate);
							 params.add(EndDate);
							 
							
						  
							 assetLifeLedgerItem =  AssetLifeLedgerItem(session,itemQuery,params);
						     System.out.println("******The  assetLifeItemLedger is " + mapper.writeValueAsString(assetLifeLedgerItem));
						     params.remove(item);
						     params.remove(EndDate);
						     params.remove(StartDate);
						    
						    assetLifeLedgerItemArray.addAll(assetLifeLedgerItem);
						    System.out.println("******The  assetLifeLedgerItemArray is " + mapper.writeValueAsString(assetLifeLedgerItemArray));
   
					   }
				   }
			   else {
				   
				   
				   Object[] listItemWare;

					session = almsessions.getSession();
					tx = session.beginTransaction();
					Query q = session.createQuery("Select distinct itemCode, warehouse from AssetLifeCycle where creationDate >= :param1 and creationDate <= :param2");
					q.setParameter("param1", StartDate);
					q.setParameter("param2", EndDate);
					q.setFirstResult(0);
					q.setMaxResults(50000);
					listItemWare = (Object[]) q.list().toArray();
					System.out.println("The listItemWare is " +mapper.writeValueAsString(listItemWare));

					if (ArrayUtils.isNotEmpty(listItemWare) ) {
					 for (int i = 0; i < listItemWare.length ; i++) {
						 Object[] itemWare = (Object[]) listItemWare[i];
						 //System.out.println("The itemWare is " +mapper.writeValueAsString(itemWare));

						 
						 itemCode = itemWare[0].toString();
						 wareId = itemWare[1].toString();
						 
						 String itemQuery = "Select TO_CHAR(t.creationDate, 'YYYY-MM-DD') as creationDate, t.warehouse as warehouse,t.itemCode as itemCode,t.itemModel as itemModel ,t.itemName as itemName,t.UOM as UOM, t.actualQty as actualQty, t.voucherType as voucherType, t.voucherNB as voucherNB, t.inComingRate as inComingRate, t.outGoingRate as outGoingRate, t.valuationRate as valuationRate, t.accumulatedDepreciation as accumulatedDepreciation, t.balanceQty as balanceQty,t.netBalanceValue as netBalanceValue, t.balanceValue as balanceValue, t.netPrice as netPrice from AssetLifeCycle t where t.itemCode like :param_1 and (t.creationDate >= :param_2 and t.creationDate <= :param_3) and t.warehouse like :param_4 order by t.creationDate desc"; 
			    		 params.add(itemCode);
			    		 params.add(StartDate);
						 params.add(EndDate);
						 params.add(wareId);
						 
						
					  
						 assetLifeLedgerItem =  AssetLifeLedgerItem(session,itemQuery,params);
					     System.out.println("******The  assetLifeItemLedger is " + mapper.writeValueAsString(assetLifeLedgerItem));
					     params.remove(itemCode);
					     params.remove(EndDate);
					     params.remove(StartDate);
					     params.remove(wareId);
					    
					     assetLifeLedgerItemArray.addAll(assetLifeLedgerItem);
					     System.out.println("******The  assetLifeLedgerItemArray is " + mapper.writeValueAsString(assetLifeLedgerItemArray));

						 
						 
					 }

					}
					tx.commit();
					session.close();
				   
				   
				   
				   
			   }
		   }
			   
				
	    	   
			   }
		
		
		
	return assetLifeLedgerItemArray;
	}

	@SuppressWarnings("unused")
	public List<AssetLifeCycle_Transaction> findTransactionListBy(String transactionTableOption, Timestamp StartDate,Timestamp EndDate, String siteId, String cellId) throws Exception{
		
		List<AssetLifeCycle_Transaction> transactionList = new ArrayList<AssetLifeCycle_Transaction>();
		AssetLifeCycle_Transaction transaction = new AssetLifeCycle_Transaction();
		ArrayList<AssetLifeCycle_Transaction> assetLifeTransArray = new ArrayList<AssetLifeCycle_Transaction>();
		String nodePkQuery = null,siteStringQuery  = null, nodeStringQuery = null, cellStringQuery = null, cabinetStringQuery = null, boardStringQuery = null ,antennaStringQuery = null ,hostStringQuery = null  ;
		   
		List<Object> params = new ArrayList<Object>();
		params.add(StartDate);
   	    params.add(EndDate);
		
   	    /*Pattern pattern ;//= Pattern.compile("SNCeBAHCa");
   	    Matcher matcher ;//= pattern.matcher(transactionTableOption); 
   	    
   	   if (Pattern.matches("S", transactionTableOption)) {
   		 System.out.println("clear");
   		} else {
   		 System.out.println("buzz");
   		}
		*/
   	           List<String> nodePkList = new ArrayList<String>();
   	           /// node_pk
			   /*nodePkQuery = "SELECT NODE_PK FROM NODE_ACTIVE where CREATION_DATE > :param_1 AND UPDATE_DATE < :param_2";
			   nodePkList = appConfig.findAllBySQLQueryParamsAsStringList(nodePkQuery,params);
			   System.out.println("the RESULT IS node Pk "+ mapper.writeValueAsString(nodePkList));
			   //System.out.println("the nodePkList.size() "+nodePkList.size());
			   //if(nodePkList.size() > 0) {
			   if(nodePkList != null) {
			   //if(!nodePkList.isEmpty() || nodePkList != null) {
            	   for (String nodePk : nodePkList) {
            		   transaction = new AssetLifeCycle_Transaction();  
            		*/   
            		// params.add(nodePk);
            		 
            		/* if (StringUtils.equalsIgnoreCase(transactionTableOption, "CeSN"))  {
          			   System.out.println("THE SITE/NODE/CELL");
			         ///site
			         siteStringQuery = "SELECT SITE_ID, WARE_NAME FROM WAREHOUSE WHERE SITE_ID IN(SELECT SITE_ID FROM NODE_ACTIVE WHERE NODE_PK =:param_3) AND CREATION_DATE >= :param_1 AND LAST_MODIFY_DATE <= :param_2";
			         Object[] siteColumns = appConfig.findAllBySQLQueryParamsAsObjectList(siteStringQuery,params);
			         System.out.println("the RESULT IS SITE"+ mapper.writeValueAsString(siteColumns));
			         //System.out.println("PASS THE SITE SIZE "+ siteColumns.size());
			         //if(siteColumns.length>0) {
			         if(siteColumns != null ) {
				       System.out.println("PASS THE SITE ");
				       //Object[] siteColumnsa = siteColumns.toArray();
				       System.out.println("the RESULT as object IS "+ mapper.writeValueAsString(siteColumns[0]));
				       System.out.println("the RESULT as object 1 IS "+ mapper.writeValueAsString(siteColumns[1]));

				       //String siteId = (String) siteColumns[0];
				       //System.out.println("the RESULT as object 1 IS "+siteId);
				       transaction.setSiteId(siteColumns[0].toString());
			           transaction.setSiteName(siteColumns[1].toString());
			         }
		   
		             ///node
			         nodeStringQuery = "SELECT NODE_ACTIVE.node_Id, NODE_ACTIVE.node_name, NODE_CABINET.SERIALNUMBER, NODE_ACTIVE.Node_type, NODE_ACTIVE.trans_type FROM NODE_CABINET, NODE_ACTIVE WHERE  NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.BOMCODE != '0' AND NODE_CABINET.MANUFACTURERDATA != '0' AND NODE_CABINET.ACTIVE_RECORD = '1' AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_PK =:param_3";
			         Object[] nodeColumns = appConfig.findAllBySQLQueryParamsAsObjectList(nodeStringQuery,params);
			         System.out.println("the RESULT OF THE NODE IS "+ mapper.writeValueAsString(nodeColumns));
			         if(nodeColumns != null) {
				       transaction.setNodeId(nodeColumns[0].toString());
			           transaction.setNodeName(nodeColumns[1].toString());
			           transaction.setNodeSNo(nodeColumns[2].toString());
			           transaction.setNodeType(nodeColumns[3].toString());
			           transaction.setNodeTransType(nodeColumns[4].toString());
			        }
		            ///cabinet
		            cabinetStringQuery = "SELECT TO_CHAR(CREATION_DATE, 'DD-MM-YYYY') as CREATION_DATE, TRANS_SOURCE, TRANS_TYPE, TRANS_ID , NODE_ID, NODE_NAME, SERIAL_NUMBER, SITE_ID, CABINET_POSITION,CAPINET_MODEL FROM (SELECT NODE_CABINET.CREATION_DATE AS CREATION_DATE, NODE_CABINET.TRANS_SOURCE AS TRANS_SOURCE , NODE_CABINET.TRANS_TYPE AS TRANS_TYPE, NODE_CABINET.TRANS_ID AS TRANS_ID, NODE_ACTIVE.node_Id AS NODE_ID, NODE_ACTIVE.node_name AS NODE_NAME, NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER ,NODE_ACTIVE.SITE_ID AS SITE_ID, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL AS CAPINET_MODEL FROM NODE_CABINET, NODE_ACTIVE WHERE NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.BOMCODE != '0' AND NODE_CABINET.MANUFACTURERDATA != '0' AND NODE_CABINET.ACTIVE_RECORD = '1' AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_PK =:param_3)";
		            Object[] cabinetColumns = appConfig.findAllBySQLQueryParamsAsObjectList(cabinetStringQuery,params);
			        System.out.println("the RESULT OF THE CABINET IS "+ mapper.writeValueAsString(cabinetColumns));
			        if(cabinetColumns != null) {
			        	for (int i = 0; i < cabinetColumns.length; i++) {
 			        		 Object[] cabinetColumn = (Object[])cabinetColumns[i];
 			        		System.out.println("THE nodeColumn_"+i+" IS "+mapper.writeValueAsString(cabinetColumn));
 			        		System.out.println("THE nodeColumn 0 IS "+cabinetColumn[0].toString());
 			        		System.out.println("THE nodeColumn 1 IS "+cabinetColumn[1]);
 			        		System.out.println("THE nodeColumn 2 IS "+cabinetColumn[2].toString());
 			        		System.out.println("THE nodeColumn 3 IS "+cabinetColumn[3].toString());
 			        		System.out.println("THE nodeColumn 4 IS "+cabinetColumn[4].toString());
 			        		System.out.println("THE nodeColumn 5 IS "+cabinetColumn[5].toString());
 			        		System.out.println("THE nodeColumn 6 IS "+cabinetColumn[6].toString());
 			        		System.out.println("THE nodeColumn 7 IS "+cabinetColumn[7].toString());
 			        		System.out.println("THE nodeColumn 8 IS "+cabinetColumn[8].toString());
 			        		System.out.println("THE nodeColumn 9 IS "+cabinetColumn[9].toString());
 			        		
 			        		String transSource = String.valueOf(cabinetColumn[1].toString());
 			        		String transType = String.valueOf(cabinetColumn[2].toString());
 			        		String transID = String.valueOf(cabinetColumn[3].toString());
 			        		String nodeID = String.valueOf(cabinetColumn[4].toString());
 			        		String nodeName = String.valueOf(cabinetColumn[5].toString()); 
 			        		String cabinetSNo = String.valueOf(cabinetColumn[6].toString());
 			        		String SiteID = String.valueOf(cabinetColumn[7].toString()); 
 			        		String cabinetPosition = String.valueOf(cabinetColumn[8].toString());
 			        		String cabinetModel = String.valueOf(cabinetColumn[9].toString());
 			        		
 			        		
 			        		 transaction.setCreationDate((String)cabinetColumn[0]);
			        	     transaction.setTransSource(transSource);
			        	     transaction.setTransType(transType);
			        	     transaction.setTransID(transID);
			        	     transaction.setCellId(String.valueOf(""));
				             transaction.setCellName(String.valueOf(""));
			        	     transaction.setNodeId(nodeID);
			        	     transaction.setNodeName(nodeName);
			        	     transaction.setSiteId(SiteID);
			        	     transaction.setCabinetPosition(cabinetPosition);
					         transaction.setCabinetModel(cabinetModel);
					         transaction.setCabinetSNo(cabinetSNo);
					         //transaction.setCabinetTransType(cabinetColumns[3].toString());
			        
			           transactionList.add(transaction);
   			           System.out.println("THE transaction List IS "+mapper.writeValueAsString(transactionList));
			          }
			        	
				     
			        }
			   
		            ///board
			        boardStringQuery = "SELECT TO_CHAR(CREATION_DATE, 'DD-MM-YYYY') as CREATION_DATE, TRANS_SOURCE, TRANS_TYPE, TRANS_ID , NODE_ID, NODE_NAME, SERIAL_NUMBER, SITE_ID, CABINET_POSITION, CAPINET_MODEL, CABINETNO, SUBRACKNO, SLOTNO, SLOTPOS, BOARD_MODEL, BOARD_SERIALNO FROM (SELECT  NODE_BOARD.CREATION_DATE AS CREATION_DATE, NODE_BOARD.TRANS_SOURCE AS TRANS_SOURCE ,  NODE_BOARD.TRANS_TYPE AS TRANS_TYPE,  NODE_BOARD.TRANS_ID AS TRANS_ID, NODE_ACTIVE.node_Id AS NODE_ID, NODE_ACTIVE.node_name AS NODE_NAME, NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER ,NODE_ACTIVE.SITE_ID AS SITE_ID, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL AS CAPINET_MODEL , NODE_BOARD.CABINETNO AS CABINETNO,  NODE_BOARD.SUBRACKNO AS SUBRACKNO ,  NODE_BOARD.SLOTNO AS SLOTNO,  NODE_BOARD.SLOTPOS AS SLOTPOS ,  NODE_BOARD.MODEL AS BOARD_MODEL, NODE_BOARD.SERIALNUMBER AS BOARD_SERIALNO FROM NODE_CABINET, NODE_BOARD ,NODE_ACTIVE WHERE  NODE_BOARD.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.BOMCODE != '0' AND NODE_CABINET.MANUFACTURERDATA != '0' AND NODE_CABINET.ACTIVE_RECORD = '1' AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_PK =:param_3)";
			        Object[] boardColumns = appConfig.findAllBySQLQueryParamsAsObjectList(boardStringQuery,params);
			        System.out.println("the RESULT OF THE BOARD IS "+ mapper.writeValueAsString(boardColumns));
			        if(boardColumns != null) {
			        	for (int i = 0; i < boardColumns.length; i++) {
 			        		 Object[] boardColumn = (Object[])boardColumns[i];
 			        		 System.out.println("THE nodeColumn_"+i+" IS "+mapper.writeValueAsString(boardColumn));
 			        		 System.out.println("THE nodeColumn 0 IS "+boardColumn[0].toString());
 			        		 System.out.println("THE nodeColumn 1 IS "+boardColumn[1]);
 			        		 System.out.println("THE nodeColumn 2 IS "+boardColumn[2].toString());
 			        		 System.out.println("THE nodeColumn 3 IS "+boardColumn[3].toString());
 			        		 System.out.println("THE nodeColumn 4 IS "+boardColumn[4].toString());
 			        		 System.out.println("THE nodeColumn 5 IS "+boardColumn[5].toString());
 			        		 System.out.println("THE nodeColumn 6 IS "+boardColumn[6].toString());
 			        		 System.out.println("THE nodeColumn 7 IS "+boardColumn[7].toString());
 			        		 System.out.println("THE nodeColumn 8 IS "+boardColumn[8].toString());
 			        		 System.out.println("THE nodeColumn 9 IS "+boardColumn[9].toString());
					         String boardPosition= "Cabinet "+boardColumns[10].toString()+"/ Shelf "+boardColumns[11].toString()+"/ Slot "+boardColumns[12].toString()+"/ Slot Pos "+boardColumns[13].toString();
					         System.out.println("THE nodeColumn 15 IS "+boardColumn[15].toString());
					         
 			        		String transSource = String.valueOf(boardColumn[1].toString());
 			        		String transType = String.valueOf(boardColumn[2].toString());
 			        		String transID = String.valueOf(boardColumn[3].toString());
 			        		String nodeID = String.valueOf(boardColumn[4].toString());
 			        		String nodeName = String.valueOf(boardColumn[5].toString()); 
 			        		String cabinetSNo = String.valueOf(boardColumn[6].toString());
 			        		String SiteID = String.valueOf(boardColumn[7].toString()); 
 			        		String cabinetPosition = String.valueOf(boardColumn[8].toString());
 			        		String cabinetModel = String.valueOf(boardColumn[9].toString());
 			        		String boardModel = String.valueOf(boardColumn[14].toString());
 			        		String boardSNo = String.valueOf(boardColumn[15].toString());
 			        		
 			        		
 			        		 transaction.setCreationDate((String)boardColumn[0]);
			        	     transaction.setTransSource(transSource);
			        	     transaction.setTransType(transType);
			        	     transaction.setTransID(transID);
			        	     transaction.setCellId(String.valueOf(""));
				             transaction.setCellName(String.valueOf(""));
			        	     transaction.setNodeId(nodeID);
			        	     transaction.setNodeName(nodeName);
			        	     transaction.setSiteId(SiteID);
			        	     transaction.setCabinetPosition(cabinetPosition);
					         transaction.setCabinetModel(cabinetModel);
					         transaction.setCabinetSNo(cabinetSNo);
						     transaction.setBoardPosition(boardPosition);
					         transaction.setBoardModel(boardModel);
					         transaction.setBoardSNo(boardSNo);
					         //transaction.setBoardTransType(boardColumns[6].toString());
			        
			               transactionList.add(transaction);
   			           System.out.println("THE transaction List IS "+mapper.writeValueAsString(transactionList));
			          }
			        	
			        	
			       }
		           ///antenna
			       antennaStringQuery = "SELECT ANTENNA_ID,MODEL, SERIALNUMBER, TRANS_TYPE FROM NODE_ANTENNA AND DATEOFMANUFACTURE >= :param_1 AND DATEOFLASTSERVICE <= :param_2 AND NODE_PK =:param_3";
			       Object[] antennaColumns = appConfig.findAllBySQLQueryParamsAsObjectList(antennaStringQuery,params);
			       System.out.println("the RESULT OF THE ANTENNA IS "+ mapper.writeValueAsString(antennaColumns));
			       if(antennaColumns != null) {
				     transaction.setAntennaId(antennaColumns[0].toString());
			         transaction.setAntennaModel(antennaColumns[1].toString());
			         transaction.setAntennaSNo(antennaColumns[2].toString());
			         transaction.setAntennaTransType(antennaColumns[3].toString());
			       }
		   
		           ///cell 
			       cellStringQuery ="SELECT GCELL_ID AS CELL_ID, CELLNAME , TRANS_TYPE  FROM NODE_GCELL WHERE NODE_PK = :param_3 AND CREATION_DATE >= :param_1 AND UPDATE_DATE <= :param_2 UNION (SELECT LCELL_ID AS CELL_ID, CELLNAME,TRANS_TYPE FROM NODE_LCELL WHERE NODE_PK = :param_3 AND CREATION_DATE >= :param_1 AND UPDATE_DATE <= :param_2) UNION (SELECT UCELL_ID AS CELL_ID, CELLNAME, TRANS_TYPE FROM NODE_UCELL WHERE NODE_PK = :param_3 AND CREATION_DATE >= :param_1 AND UPDATE_DATE <= :param_2)";
                   Object[] cellColumns = appConfig.findAllBySQLQueryParamsAsObjectList(cellStringQuery,params);
			       System.out.println("the RESULT OF THE CELL IS "+ mapper.writeValueAsString(cellColumns));
			       if(cellColumns != null) {
				     transaction.setCellId(cellColumns[0].toString());
				     transaction.setCellName(cellColumns[1].toString());
				     transaction.setCellTransType(cellColumns[2].toString());
			   
			       }
			   
		   
		           ///host
			       hostStringQuery = "SELECT HOSTVER, TRANS_TYPE FROM NODE_HOSTVER where CREATION_DATE >= :param_1 AND UPDATE_DATE <= :param_2 AND NODE_PK =:param_3";
			       Object[] hostColumns = appConfig.findAllBySQLQueryParamsAsObjectList(hostStringQuery,params);
			       System.out.println("the RESULT OF THE HOST IS "+ mapper.writeValueAsString(hostColumns));
			       if(hostColumns != null) {
				     transaction.setHostVersion(hostColumns[0].toString());
				     transaction.setHostVersionTrans(hostColumns[1].toString());
				   
			       }
			       
			       if(nodeColumns != null || siteColumns != null || nodeColumns != null) {
          			 transactionList.add(transaction);
    			     System.out.println("THE transaction List IS "+mapper.writeValueAsString(transactionList));
    			   }
			       
			       params.remove(nodePk);
			       
                }*/
            		 if (StringUtils.equalsIgnoreCase(transactionTableOption, "S"))  {
            			   System.out.println("THE SITE");
            			   //params.add(nodePk);
  			              ///site
        			      siteStringQuery = "SELECT SITE_ID, WARE_NAME FROM WAREHOUSE WHERE SITE_ID IN(SELECT SITE_ID FROM NODE_ACTIVE WHERE CREATION_DATE >= :param_1 AND UPDATE_DATE <= :param_2 AND NODE_PK =:param_3) ";

            			   if(!StringUtils.equalsIgnoreCase(siteId,"null") && siteId != null && siteId != ""){
            				   System.out.println("THE SITE is available");
            				   params.add(siteId);
          			          siteStringQuery = "SELECT SITE_ID, WARE_NAME FROM WAREHOUSE WHERE SITE_ID IN(SELECT SITE_ID FROM NODE_ACTIVE WHERE NODE_PK =:param_3 AND SITE_ID =:param_4) AND CREATION_DATE >= :param_1 AND LAST_MODIFY_DATE <= :param_2";

            			   }
            			   
  			         Object[] siteColumns = AssetLifeCycleCulomns(session,siteStringQuery ,params);
  			         System.out.println("the RESULT IS SITE "+ mapper.writeValueAsString(siteColumns));
  			         //System.out.println("PASS THE SITE SIZE "+ siteColumns.size());
  			         //if(siteColumns.size()>0) {
  			         if(siteColumns != null) {
  				       System.out.println("PASS THE SITE ");
  				       for (int i = 0; i < siteColumns.length; i++) {
			        		 Object[] siteColumn = (Object[])siteColumns[i];
			        		 System.out.println("THE siteColumn"+i+" IS "+mapper.writeValueAsString(siteColumn));
			        		 System.out.println("THE siteColumn 0 IS "+siteColumn[0].toString());
			        		 System.out.println("THE siteColumn 1 IS "+siteColumn[1].toString());
			        		 String siteID = siteColumn[0].toString(); 
  				             String siteName = siteColumn[1].toString();
  				             transaction.setSiteId(String.valueOf(siteID));
  			                 //transaction.setSiteName(mapper.writeValueAsString(siteName));
  				             transaction.setSiteName(String.valueOf(siteName));
  				       }
  			           //transactionList.add(transaction);
  			           //System.out.println("THE transaction List IS "+mapper.writeValueAsString(transactionList));
  			       
  			         }
  			         
      			     if(!StringUtils.equalsIgnoreCase(siteId,"null") && siteId != null && siteId != ""){
      			    	 params.remove(siteId);
      			     }
			       //params.remove(nodePk);
            	}
            		 if (StringUtils.equalsIgnoreCase(transactionTableOption, "N") || StringUtils.equalsIgnoreCase(transactionTableOption, "CeSN"))  {
            			 System.out.println("THE NODE");
            			 //params.add(nodePk);
            			///node
    			         //nodeStringQuery = "SELECT TO_CHAR(CREATION_DATE, 'DD-MM-YYYY') as CREATION_DATE, TRANS_SOURCE, TRANS_TYPE, TRANS_ID , NODE_ID, NODE_NAME, SERIAL_NUMBER, NODE_TYPE, SITE_ID, WARE_ID FROM (SELECT NODE_ACTIVE.CREATION_DATE AS CREATION_DATE, NODE_ACTIVE.TRANS_SOURCE AS TRANS_SOURCE , NODE_ACTIVE.TRANS_TYPE AS TRANS_TYPE, NODE_ACTIVE.TRANS_ID AS TRANS_ID, NODE_ACTIVE.node_Id AS NODE_ID, NODE_ACTIVE.node_name AS NODE_NAME, NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER, NODE_ACTIVE.NODE_MODEL AS NODE_TYPE ,NODE_ACTIVE.SITE_ID AS SITE_ID , NODE_ACTIVE.WARE_ID AS WARE_ID FROM NODE_CABINET, NODE_ACTIVE WHERE  NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.BOMCODE != '0' AND NODE_CABINET.MANUFACTURERDATA != '0' AND NODE_CABINET.ACTIVE_RECORD = '1' AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_PK =:param_3)";
    			         nodeStringQuery = "SELECT TO_CHAR(NODE_ACTIVE.CREATION_DATE, 'DD-MM-YYYY') AS CREATION_DATE, NODE_ACTIVE.FROM_TRANS_SOURCE,"
    			         		+ " NODE_ACTIVE.TRANS_TYPE, NODE_ACTIVE.FROM_TRANS_ID AS TRANS_ID,NODE_ACTIVE.node_Id, NODE_ACTIVE.node_name, "
    			         		+ "NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER,NODE_ACTIVE.NODE_MODEL AS NODE_TYPE ,"
    			         		+ "NODE_ACTIVE.SITE_ID,NODE_ACTIVE.WARE_ID, NODE_ACTIVE.WARE_NAME,NODE_ACTIVE.ACTIVE_RECORD FROM NODE_ACTIVE INNER JOIN NODE_CABINET ON"
    			         		+ " NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.CABINETNO = '0' AND  "
    			         		+ "NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 order by NODE_ACTIVE.CREATION_DATE desc";
            			
            			// nodeStringQuery =  "SELECT TO_CHAR( NODE_CABINET.CREATION_DATE, 'DD-MM-YYYY') as CREATION_DATE, NODE_CABINET.FROM_TRANS_SOURCE, NODE_CABINET.TRANS_TYPE, NODE_CABINET.FROM_TRANS_ID, NODE_ACTIVE.NODE_ID, NODE_ACTIVE.NODE_NAME, NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER ,NODE_ACTIVE.SITE_ID, NODE_ACTIVE.WARE_NAME, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL,NODE_CABINET.ACTIVE_RECORD, NODE_ACTIVE.TRANS_TYPE  FROM NODE_CABINET INNER JOIN NODE_ACTIVE ON NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2";

            			 Object[] nodeColumns = AssetLifeCycleCulomns(session,nodeStringQuery ,params);
    			         System.out.println("the RESULT OF THE NODE IS "+ mapper.writeValueAsString(nodeColumns));
    			         if(nodeColumns != null) {
    			        	 for (int i = 0; i < nodeColumns.length; i++) {
    		            		   
        			            transaction = new AssetLifeCycle_Transaction();  

      			        		Object[] nodeColumn = (Object[])nodeColumns[i];
      			        		System.out.println("THE nodeColumn_"+i+" IS "+mapper.writeValueAsString(nodeColumn));
      			        		System.out.println("THE nodeColumn 0 IS "+nodeColumn[0].toString());
      			        		System.out.println("THE nodeColumn 1 IS "+nodeColumn[1]);
      			        		System.out.println("THE nodeColumn 2 IS "+nodeColumn[2].toString());
      			        		System.out.println("THE nodeColumn 3 IS "+nodeColumn[3].toString());
      			        		System.out.println("THE nodeColumn 4 IS "+nodeColumn[4].toString());
      			        		System.out.println("THE nodeColumn 5 IS "+nodeColumn[5].toString());
      			        		System.out.println("THE nodeColumn 6 IS "+nodeColumn[6].toString());
      			        		System.out.println("THE nodeColumn 7 IS "+nodeColumn[7].toString());
      			        		System.out.println("THE nodeColumn 8 IS "+nodeColumn[8].toString());
      			        		System.out.println("THE nodeColumn 9 IS "+nodeColumn[9].toString());
      			        		System.out.println("THE nodeColumn 10 IS "+nodeColumn[10].toString());
      			        		System.out.println("THE nodeColumn 11 IS "+nodeColumn[11].toString());
      			        		/*String transSource = String.valueOf(nodeColumn[1].toString());
      			        		String transType = String.valueOf(nodeColumn[2].toString());
      			        		String transID = String.valueOf(nodeColumn[3].toString());
      			        		String nodeID = String.valueOf(nodeColumn[4].toString());
      			        		String nodeName = String.valueOf(nodeColumn[5].toString()); 
      			        		String nodeSNo = String.valueOf(nodeColumn[6].toString());
      			        		String nodeType = String.valueOf(nodeColumn[7].toString()); 
      			        		String SiteID = String.valueOf(nodeColumn[8].toString());
      			        		String wareID = String.valueOf(nodeColumn[9].toString());
      			        		*/
      			        		
      			        		String transSource = nodeColumn[1].toString();
      			        		String transType = nodeColumn[2].toString();
      			        		String transID = nodeColumn[3].toString();
      			        		String nodeID = nodeColumn[4].toString();
      			        		String nodeName = nodeColumn[5].toString(); 
      			        		String nodeSNo = nodeColumn[6].toString();
      			        		String nodeType = nodeColumn[7].toString(); 
      			        		String SiteID = nodeColumn[8].toString();
      			        		String SiteName = nodeColumn[10].toString();
      			        		String wareID = nodeColumn[9].toString();
      			        		String activeRecord = nodeColumn[11].toString();
    			        	 
      			        		 /*transaction.setCreationDate((String)nodeColumn[0]);
      			        	     transaction.setTransSource(String.valueOf(nodeColumn[1]));
      			        	     transaction.setTransType(String.valueOf(nodeColumn[2]));
      			        	     transaction.setTransID(String.valueOf(nodeColumn[3]));
      			        	     transaction.setCellId(String.valueOf(""));
    				             transaction.setCellName(String.valueOf(""));
      			        	     transaction.setNodeId((String)(nodeColumns[4]));
      			        	     transaction.setNodeName(String.valueOf(nodeColumns[5]));
      			        	     transaction.setNodeSNo(String.valueOf(nodeColumns[6]));
      			        	     transaction.setNodeType(String.valueOf(nodeColumns[7]));
      			        	     //transaction.setSiteId(String.valueOf(nodeColumn[8]));*/
      			        		
      			        		
      	        			    /* siteStringQuery = "SELECT SITE_ID, WARE_NAME FROM WAREHOUSE WHERE WARE_ID LIKE :param_4";	
      	        			     params.add(wareID);
        			             Object[] siteColumns = appConfig.findAllBySQLQueryParamsAsObjectList(siteStringQuery,params);
        			             System.out.println("the RESULT IS SITE "+ mapper.writeValueAsString(siteColumns));
        			       
        			             if(siteColumns != null) {
        		  				       System.out.println("PASS THE SITE ");
        		  				       for (int j = 0; j < siteColumns.length; j++) {
        					        		 Object[] siteColumn = (Object[])siteColumns[i];
        					        		 System.out.println("THE siteColumn"+i+" IS "+mapper.writeValueAsString(siteColumn));
        					        		 System.out.println("THE siteColumn 0 IS "+siteColumn[0].toString());
        					        		 System.out.println("THE siteColumn 1 IS "+siteColumn[1].toString());
        					        		 String siteID = siteColumn[0].toString(); 
        		  				             String siteName = siteColumn[1].toString();
        		  				             transaction.setSiteId(String.valueOf(siteID));
        		  			                 //transaction.setSiteName(mapper.writeValueAsString(siteName));
        		  				             transaction.setSiteName(String.valueOf(siteName));
        		  				       }
        		  				      
        			             }
        			             params.remove(wareID);
        			             */
      			        		
      			        		 transaction.setCreationDate((String)nodeColumn[0]);
     			        	     transaction.setTransSource(transSource);
     			        	     transaction.setTransType(transType);
     			        	     transaction.setTransID(transID);
     			        	     transaction.setActiveRecord(activeRecord);
     			        	     transaction.setCellId(String.valueOf(""));
   				                 transaction.setCellName(String.valueOf(""));
     			        	     transaction.setNodeId(nodeID);
     			        	     transaction.setNodeName(nodeName);
     			        	     transaction.setNodeSNo(nodeSNo);
     			        	     transaction.setNodeType(nodeType);
     			        	     transaction.setSiteId(SiteID);
     			        	    transaction.setSiteName(SiteName);
    			                 //transaction.setNodeTransType(nodeColumns[4].toString());
    			        
    			           transactionList.add(transaction);
        			       System.out.println("THE transaction List IS "+mapper.writeValueAsString(transactionList));
    			          } 
    			         }
    			         
    			        
    				      // params.remove(nodePk);
            		 }
            		 
            		 
            		 if (StringUtils.equalsIgnoreCase(transactionTableOption, "Ce") || StringUtils.equalsIgnoreCase(transactionTableOption, "CeSN")){
            			 System.out.println("THE CELL");
            			 //params.add(nodePk);
            			 Object[] cellColumns = null;
            			 ///cell 
        				 cellStringQuery ="SELECT TO_CHAR(CREATION_DATE, 'DD-MM-YYYY') as CREATION_DATE, TRANS_SOURCE, TRANS_TYPE, TRANS_ID ,CELL_ID, CELL_NAME,  NODE_ID, NODE_NAME, SITE_ID, WARE_NAME FROM (SELECT  NODE_GCELL.CREATION_DATE AS CREATION_DATE,  NODE_GCELL.TRANS_SOURCE AS  TRANS_SOURCE, NODE_GCELL.TRANS_ID AS TRANS_ID , NODE_GCELL.GCELL_ID AS CELL_ID, NODE_GCELL.CELLNAME AS CELL_NAME, NODE_GCELL.TRANS_TYPE AS TRANS_TYPE ,NODE_ACTIVE.NODE_ID AS NODE_ID , NODE_ACTIVE.NODE_NAME AS NODE_NAME, NODE_ACTIVE.SITE_ID AS SITE_ID, NODE_ACTIVE.WARE_NAME AS WARE_NAME FROM NODE_GCELL , NODE_ACTIVE where NODE_GCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_GCELL.ACTIVE_RECORD = '1' AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_PK LIKE :param_3 UNION (SELECT  NODE_LCELL.CREATION_DATE AS CREATION_DATE,  NODE_LCELL.TRANS_SOURCE AS  TRANS_SOURCE, NODE_LCELL.TRANS_ID AS TRANS_ID , NODE_LCELL.LCELL_ID AS CELL_ID, NODE_LCELL.CELLNAME AS CELL_NAME, NODE_LCELL.TRANS_TYPE AS TRANS_TYPE ,NODE_ACTIVE.NODE_ID AS NODE_ID , NODE_ACTIVE.NODE_NAME AS NODE_NAME, NODE_ACTIVE.SITE_ID AS SITE_ID, NODE_ACTIVE.WARE_NAME AS WARE_NAME FROM NODE_LCELL , NODE_ACTIVE where NODE_LCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_LCELL.ACTIVE_RECORD = '1' AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_PK LIKE :param_3) UNION (SELECT  NODE_UCELL.CREATION_DATE AS CREATION_DATE,  NODE_UCELL.TRANS_SOURCE AS  TRANS_SOURCE, NODE_UCELL.TRANS_ID AS TRANS_ID , NODE_UCELL.UCELL_ID AS CELL_ID, NODE_UCELL.CELLNAME AS CELL_NAME, NODE_UCELL.TRANS_TYPE AS TRANS_TYPE,NODE_ACTIVE.NODE_ID AS NODE_ID , NODE_ACTIVE.NODE_NAME AS NODE_NAME, NODE_ACTIVE.SITE_ID AS SITE_ID, NODE_ACTIVE.WARE_NAME AS WARE_NAME FROM NODE_UCELL, NODE_ACTIVE where NODE_UCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_UCELL.ACTIVE_RECORD = '1' AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_PK LIKE :param_3))";
            			 if(!StringUtils.equalsIgnoreCase(cellId,"null") && cellId != null && cellId != ""){
            				 System.out.println("THE CELL is available");
            				 params.add(cellId);
          			         ///cellStringQuery ="SELECT CREATION_DATE, TRANS_SOURCE, TRANS_TYPE, TRANS_ID ,CELL_ID, CELL_NAME,  NODE_ID, NODE_NAME, SITE_ID FROM (SELECT  NODE_GCELL.CREATION_DATE AS CREATION_DATE,  NODE_GCELL.TRANS_SOURCE AS  TRANS_SOURCE, NODE_GCELL.TRANS_ID AS TRANS_ID , NODE_GCELL.GCELL_ID AS CELL_ID, NODE_GCELL.CELLNAME AS CELL_NAME, NODE_GCELL.TRANS_TYPE AS TRANS_TYPE ,NODE_ACTIVE.NODE_PK AS NODE_ID , NODE_ACTIVE.NODE_NAME AS NODE_NAME, NODE_ACTIVE.SITE_ID AS SITE_ID FROM NODE_GCELL , NODE_ACTIVE where NODE_GCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_PK LIKE :param_3 AND NODE_GCELL.GCELL_ID LIKE :param_4 UNION (SELECT  NODE_LCELL.CREATION_DATE AS CREATION_DATE,  NODE_LCELL.TRANS_SOURCE AS  TRANS_SOURCE, NODE_LCELL.TRANS_ID AS TRANS_ID , NODE_LCELL.GCELL_ID AS CELL_ID, NODE_LCELL.CELLNAME AS CELL_NAME, NODE_LCELL.TRANS_TYPE AS TRANS_TYPE,NODE_ACTIVE.NODE_PK AS NODE_ID , NODE_ACTIVE.NODE_NAME AS NODE_NAME, NODE_ACTIVE.SITE_ID AS SITE_ID FROM NODE_LCELL , NODE_ACTIVE where NODE_LCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_PK LIKE :param_3 AND NODE_LCELL.LCELL_ID LIKE :param_4) UNION(SELECT  NODE_UCELL.CREATION_DATE AS CREATION_DATE,  NODE_UCELL.TRANS_SOURCE AS  TRANS_SOURCE, NODE_UCELL.TRANS_ID AS TRANS_ID , NODE_UCELL.GCELL_ID AS CELL_ID, NODE_UCELL.CELLNAME AS CELL_NAME, NODE_UCELL.TRANS_TYPE AS TRANS_TYPE,NODE_ACTIVE.NODE_PK AS NODE_ID , NODE_ACTIVE.NODE_NAME AS NODE_NAME, NODE_ACTIVE.SITE_ID AS SITE_ID FROM NODE_UCELL, NODE_ACTIVE where NODE_UCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_PK LIKE :param_3 AND NODE_UCELL.UCELL_ID LIKE :param_4))";
          			      }
            		 
            			 cellColumns = AssetLifeCycleCulomns(session,cellStringQuery ,params);
      			         System.out.println("the RESULT OF THE CELL IS "+ mapper.writeValueAsString(cellColumns));
      			         if(cellColumns != null) {
      			        	 for (int i = 0; i < cellColumns.length; i++) {
      			        		 transaction = new AssetLifeCycle_Transaction(); 
      			        		 Object[] cellColumn = (Object[])cellColumns[i];
      			        		System.out.println("THE cellColumn_"+i+" IS "+mapper.writeValueAsString(cellColumn));
      			        		System.out.println("THE cellColumn 0 IS "+cellColumn[0].toString());
      			        		System.out.println("THE cellColumn 1 IS "+cellColumn[1]);
      			        		System.out.println("THE cellColumn 2 IS "+cellColumn[2].toString());
      			        		System.out.println("THE cellColumn 3 IS "+cellColumn[3].toString());
      			        		System.out.println("THE cellColumn 4 IS "+cellColumn[4].toString());
      			        		System.out.println("THE cellColumn 5 IS "+cellColumn[5].toString());
      			        		System.out.println("THE cellColumn 6 IS "+cellColumn[6].toString());
      			        		System.out.println("THE cellColumn 7 IS "+cellColumn[7].toString());
      			        		System.out.println("THE cellColumn 8 IS "+cellColumn[8].toString());

      			        		/*String transSource = (String)cellColumn[1];
      			        		String transType = (String)cellColumns[2];
      			        		String transID = (String)cellColumns[3];
      			        		String cellID = (String)cellColumns[4];
      			        		String cellName = (String)cellColumns[5]; 
      			        		String nodeID = (String)cellColumns[6];
      			        		String nodeName = (String)cellColumns[7]; 
      			        		String SiteID = (String)cellColumns[8];*/
      			        		
      			        		String transSource = cellColumn[1].toString();
      			        		String transType = cellColumn[2].toString();
      			        		String transID = cellColumn[3].toString();
      			        		String cellID = cellColumn[4].toString();
      			        		String cellName = cellColumn[5].toString(); 
      			        		String nodeID = cellColumn[6].toString();
      			        		String nodeName = cellColumn[7].toString(); 
      			        		String SiteID = cellColumn[8].toString();
      			        		String SiteName = cellColumn[9].toString();
      			        		
      			        		/*
      			        		String transSource = String.valueOf(cellColumn[1].toString());
      			        		String transType = String.valueOf(cellColumn[2].toString());
      			        		String transID = String.valueOf(cellColumn[3]);
      			        		
      			        		String cellID = String.valueOf(cellColumn[4].toString());
      			        		String cellName = String.valueOf(cellColumn[5].toString()); 
      			        		String nodeID = String.valueOf(cellColumn[6].toString());
      			        		String nodeName = String.valueOf(cellColumn[7].toString()); 
      			        		String SiteID = String.valueOf(cellColumn[8].toString());
      			        		*/
      			        		/*
      			        		Object transSource = cellColumn[1];
       			        	    Object transType = cellColumns[2];
       			        	    Object transID = cellColumns[3];
       			        	    Object cellID = cellColumns[4];
       			        		Object cellName = cellColumns[5]; 
       			        		Object nodeID = cellColumns[6];
       			        		Object nodeName = cellColumns[7]; 
       			        		Object SiteID = cellColumns[8];
       			        		*/
      			        		System.out.println("THE transSource IS "+transSource);
      			        		System.out.println("THE transType IS "+transType);
      			        		System.out.println("THE transID IS "+transID);
      			        		System.out.println("THE cellID IS "+cellID);
      			        		System.out.println("THE cellName IS "+cellName);
      			        		System.out.println("THE nodeID IS "+nodeID);
      			        		System.out.println("THE nodeName IS "+nodeName);
      			        		System.out.println("THE SiteID IS "+SiteID);
      			        		System.out.println("THE SiteName IS "+SiteName);

      			        		
      			        		 transaction.setCreationDate((String)cellColumn[0]);
      			        	     transaction.setTransSource(transSource);
      			        	     transaction.setTransType(transType);
      			        	     transaction.setTransID(transID);
      				             transaction.setCellId(cellID);
      				             transaction.setCellName(cellName);
      				             transaction.setNodeId(nodeID);
      				             transaction.setNodeName(nodeName);
      				             transaction.setSiteId(SiteID);
      				             transaction.setSiteName(SiteName);
      				             transactionList.add(transaction);
         			             System.out.println("THE transaction List IS "+mapper.writeValueAsString(transactionList));
         			           
      				        }
      			        	
      			         }
      			         if(!StringUtils.equalsIgnoreCase(cellId,"null") && cellId != null && cellId != ""){
      			        	 params.remove(cellId);
        			     }
      			     
      			         //params.remove(nodePk);
            		 }
            		 
            		 
            		 if (StringUtils.equalsIgnoreCase(transactionTableOption, "Ca"))  {
            			   System.out.println("THE Cabinet");
            			  // params.add(nodePk);
            		     ///cabinet
 		                ///cabinetStringQuery = "SELECT TO_CHAR(CREATION_DATE, 'DD-MM-YYYY') as CREATION_DATE, TRANS_SOURCE, TRANS_TYPE, TRANS_ID , NODE_ID, NODE_NAME, SERIAL_NUMBER, SITE_ID, CABINET_POSITION,CAPINET_MODEL FROM (SELECT NODE_CABINET.CREATION_DATE AS CREATION_DATE, NODE_CABINET.TRANS_SOURCE AS TRANS_SOURCE , NODE_CABINET.TRANS_TYPE AS TRANS_TYPE, NODE_CABINET.TRANS_ID AS TRANS_ID, NODE_ACTIVE.node_Id AS NODE_ID, NODE_ACTIVE.node_name AS NODE_NAME, NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER ,NODE_ACTIVE.SITE_ID AS SITE_ID, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL AS CAPINET_MODEL FROM NODE_CABINET, NODE_ACTIVE WHERE NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.BOMCODE != '0' AND NODE_CABINET.MANUFACTURERDATA != '0' AND NODE_CABINET.ACTIVE_RECORD = '1' AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_PK =:param_3)";
    		            cabinetStringQuery = "SELECT TO_CHAR( NODE_CABINET.CREATION_DATE, 'DD-MM-YYYY') as CREATION_DATE, NODE_CABINET.FROM_TRANS_SOURCE, NODE_CABINET.TRANS_TYPE, NODE_CABINET.FROM_TRANS_ID, NODE_ACTIVE.NODE_ID, NODE_ACTIVE.NODE_NAME, NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER ,NODE_ACTIVE.SITE_ID, NODE_ACTIVE.WARE_NAME, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL,NODE_CABINET.ACTIVE_RECORD, NODE_ACTIVE.TRANS_TYPE as NODE_TRANS_TYPE FROM NODE_CABINET LEFT JOIN NODE_ACTIVE ON NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.CREATION_DATE >= :param_1 AND NODE_CABINET.UPDATE_DATE <= :param_2 order by NODE_CABINET.CREATION_DATE desc";

            			Object[] cabinetColumns =  AssetLifeCycleCulomns(session,cabinetStringQuery ,params);
 			            System.out.println("the RESULT OF THE CABINET IS "+ mapper.writeValueAsString(cabinetColumns));
 			            if(cabinetColumns != null) {
 			            	 
 			        	  for (int i = 0; i < cabinetColumns.length; i++) {
 			        		 transaction = new AssetLifeCycle_Transaction(); 
  			        		 Object[] cabinetColumn = (Object[])cabinetColumns[i];
  			        		 System.out.println("THE cabinetColumn"+i+" IS "+mapper.writeValueAsString(cabinetColumn));
  			        		 System.out.println("THE cabinetColumn 0 IS "+cabinetColumn[0].toString());
  			        		 System.out.println("THE cabinetColumn 1 IS "+cabinetColumn[1]);
  			        		 System.out.println("THE cabinetColumn 2 IS "+cabinetColumn[2].toString());
  			        		 System.out.println("THE cabinetColumn 3 IS "+cabinetColumn[3].toString());
  			        		 System.out.println("THE cabinetColumn 4 IS "+cabinetColumn[4].toString());
  			        		 System.out.println("THE cabinetColumn 5 IS "+cabinetColumn[5].toString());
  			        		 System.out.println("THE cabinetColumn 6 IS "+cabinetColumn[6].toString());
  			        		 System.out.println("THE cabinetColumn 7 IS "+cabinetColumn[7].toString());
  			        		 System.out.println("THE cabinetColumn 8 IS "+cabinetColumn[8].toString());
  			        		 System.out.println("THE cabinetColumn 9 IS "+cabinetColumn[9].toString());
  			        		 System.out.println("THE cabinetColumn 10 IS "+cabinetColumn[10].toString());
  			        		System.out.println("THE cabinetColumn 11 IS "+cabinetColumn[11].toString());
  			        		
  			        		String transSource = String.valueOf(cabinetColumn[1].toString());
  			        		String transType = String.valueOf(cabinetColumn[2].toString());
  			        		String transID = String.valueOf(cabinetColumn[3].toString());
  			        		String nodeID = String.valueOf(cabinetColumn[4].toString());
  			        		String nodeName = String.valueOf(cabinetColumn[5].toString()); 
  			        		String cabinetSNo = String.valueOf(cabinetColumn[6].toString());
  			        		String SiteID = String.valueOf(cabinetColumn[7].toString());
  			        		String SiteName = String.valueOf(cabinetColumn[8].toString());
  			        		String cabinetPosition = String.valueOf(cabinetColumn[9].toString());
  			        		String cabinetModel = String.valueOf(cabinetColumn[10].toString());
  			        		String activeRecord = String.valueOf(cabinetColumn[11].toString());
  			        		//String nodeTransType = String.valueOf(cabinetColumn[11].toString());
  			        		
  			        		String nodeActiveType = cabinetColumn[12].toString();
  			        		String cabinetTransType = null;
  			        		if(StringUtils.equalsIgnoreCase(transType,"New Hardware")) {
  			        		    System.out.println("It IS new hardware");
  			        		    System.out.println("THE nodeActiveType IS "+nodeActiveType);
  			        		    
  			        		    cabinetTransType = nodeActiveType +" - New Cabinet Hardware"  ;
  			        		}
  			        		else if(StringUtils.equalsIgnoreCase(transType,"Existed Hardware")) {
  			        		    System.out.println("It IS Existed hardware");
  			        		    System.out.println("THE nodeActiveType IS "+nodeActiveType);
  			        		    
  			        		    cabinetTransType = nodeActiveType +" - Existed Cabinet Hardware"  ;
  			        		}
  			        		else {
  			        			cabinetTransType = transType;
  			        		}
  			        		
  			        		
  			        		
  			        		 transaction.setCreationDate((String)cabinetColumn[0]);
 			        	     transaction.setTransSource(transSource);
 			        	     transaction.setTransType(cabinetTransType);
 			        	     transaction.setTransID(transID);
 			        	     transaction.setActiveRecord(activeRecord);
 			        	     transaction.setCellId(String.valueOf(""));
 				             transaction.setCellName(String.valueOf(""));
 			        	     transaction.setNodeId(nodeID);
 			        	     transaction.setNodeName(nodeName);
 			        	     transaction.setSiteId(SiteID);
 			        	     transaction.setSiteName(SiteName);
 			        	     transaction.setCabinetPosition(cabinetPosition);
 					         transaction.setCabinetModel(cabinetModel);
 					         transaction.setCabinetSNo(cabinetSNo);
 					         //transaction.setCabinetTransType(cabinetColumns[3].toString());
 			        
 			               transactionList.add(transaction);
    			           System.out.println("THE transaction List IS "+mapper.writeValueAsString(transactionList));
 			          }
 			        	
 				     
 			        }
 			             //params.remove(nodePk);
            	   }
            		 
            		 if (StringUtils.equalsIgnoreCase(transactionTableOption, "B"))  {
            			 System.out.println("THE BOARD");
            			// params.add(nodePk);
            			///board
     			        //boardStringQuery = "SELECT TO_CHAR(CREATION_DATE, 'DD-MM-YYYY') as CREATION_DATE, TRANS_SOURCE, TRANS_TYPE, TRANS_ID , NODE_ID, NODE_NAME, SERIAL_NUMBER, SITE_ID, CABINET_POSITION, CAPINET_MODEL, CABINETNO, SUBRACKNO, SLOTNO, SLOTPOS, BOARD_MODEL, BOARD_SERIALNO FROM (SELECT  NODE_BOARD.CREATION_DATE AS CREATION_DATE, NODE_BOARD.TRANS_SOURCE AS TRANS_SOURCE ,  NODE_BOARD.TRANS_TYPE AS TRANS_TYPE,  NODE_BOARD.TRANS_ID AS TRANS_ID, NODE_ACTIVE.node_Id AS NODE_ID, NODE_ACTIVE.node_name AS NODE_NAME, NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER ,NODE_ACTIVE.SITE_ID AS SITE_ID, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL AS CAPINET_MODEL , NODE_BOARD.CABINETNO AS CABINETNO,  NODE_BOARD.SUBRACKNO AS SUBRACKNO ,  NODE_BOARD.SLOTNO AS SLOTNO,  NODE_BOARD.SLOTPOS AS SLOTPOS ,  NODE_BOARD.MODEL AS BOARD_MODEL, NODE_BOARD.SERIALNUMBER AS BOARD_SERIALNO FROM NODE_CABINET, NODE_BOARD ,NODE_ACTIVE WHERE  NODE_BOARD.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.BOMCODE != '0' AND NODE_CABINET.MANUFACTURERDATA != '0' AND NODE_CABINET.ACTIVE_RECORD = '1' AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_PK =:param_3)";
     			        boardStringQuery = "SELECT  TO_CHAR(NODE_BOARD.CREATION_DATE , 'DD-MM-YYYY') AS CREATION_DATE, NODE_BOARD.FROM_TRANS_SOURCE ,  NODE_BOARD.TRANS_TYPE,  NODE_BOARD.FROM_TRANS_ID AS TRANS_ID,NODE_ACTIVE.NODE_ID, NODE_ACTIVE.NODE_NAME,NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER ,NODE_ACTIVE.SITE_ID, NODE_ACTIVE.WARE_NAME, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL , NODE_BOARD.CABINETNO,  NODE_BOARD.SUBRACKNO,NODE_BOARD.SLOTNO,  NODE_BOARD.SLOTPOS,  NODE_BOARD.MODEL AS BOARD_MODEL ,NODE_BOARD.SERIALNUMBER, NODE_ACTIVE.TRANS_TYPE AS NODE_ACTIVE_TYPE, NODE_BOARD.ACTIVE_RECORD FROM  ((NODE_BOARD INNER JOIN NODE_ACTIVE ON  NODE_BOARD.NODE_PK = NODE_ACTIVE.NODE_PK) INNER JOIN NODE_CABINET ON NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_BOARD.CREATION_DATE >= :param_1 AND NODE_BOARD.UPDATE_DATE <= :param_2 ) order by NODE_BOARD.CREATION_DATE desc";
     			        
     			        Object[] boardColumns =AssetLifeCycleCulomns(session,boardStringQuery ,params);
     			        System.out.println("the RESULT OF THE BOARD IS "+ mapper.writeValueAsString(boardColumns));
     			        if(boardColumns != null) {
     			        	for (int i = 0; i < boardColumns.length; i++) {
     			        		 transaction = new AssetLifeCycle_Transaction(); 
      			        		 Object[] boardColumn = (Object[])boardColumns[i];
      			        		 /*System.out.println("THE nodeColumn_"+i+" IS "+mapper.writeValueAsString(boardColumn));
      			        		 System.out.println("THE boardColumn 0 IS "+boardColumn[0].toString());
      			        		 System.out.println("THE boardColumn 1 IS "+boardColumn[1]);
      			        		 System.out.println("THE boardColumn 2 IS "+boardColumn[2].toString());
      			        		 System.out.println("THE boardColumn 3 IS "+boardColumn[3].toString());
      			        		 System.out.println("THE boardColumn 4 IS "+boardColumn[4].toString());
      			        		 System.out.println("THE boardColumn 5 IS "+boardColumn[5].toString());
      			        		 System.out.println("THE boardColumn 6 IS "+boardColumn[6].toString());
      			        		 System.out.println("THE boardColumn 7 IS "+boardColumn[7].toString());
      			        		 System.out.println("THE boardColumn 8 IS "+boardColumn[8].toString());
      			        		 System.out.println("THE boardColumn 9 IS "+boardColumn[9].toString());
      			        		 System.out.println("THE boardColumn 10 IS "+boardColumn[10].toString());
      			        		 System.out.println("THE boardColumn 11 IS "+boardColumn[11].toString());
      			        		 System.out.println("THE boardColumn 12 IS "+boardColumn[12].toString());
      			        		 System.out.println("THE boardColumn 13 IS "+boardColumn[13].toString());
      			        		 System.out.println("THE boardColumn 14 IS "+String.valueOf(boardColumn[14].toString()));
     					         System.out.println("THE boardColumn 15 IS "+boardColumn[15].toString());
     					         */
     					         
      			        		String transSource = String.valueOf(boardColumn[1].toString());
      			        		String transType = String.valueOf(boardColumn[2].toString());
      			        		String transID = String.valueOf(boardColumn[3].toString());
      			        		String nodeID = boardColumn[4].toString();
      			        		String nodeName = boardColumn[5].toString(); 
      			        		String cabinetSNo = String.valueOf(boardColumn[6].toString());
      			        		String SiteID = String.valueOf(boardColumn[7].toString());
      			        		String SiteName = String.valueOf(boardColumn[8].toString());
      			        		String cabinetPosition = String.valueOf(boardColumn[9].toString());
      			        		String cabinetModel = String.valueOf(boardColumn[10].toString());
      			        		String boardModel = String.valueOf(boardColumn[15].toString());
      			        		String boardSNo = String.valueOf(boardColumn[16].toString());
    					        String cabinetNo = String.valueOf(boardColumn[11].toString());
    					        String cabinetSubrack = String.valueOf(boardColumn[12].toString());
      			        		String cabinetSlost = String.valueOf(boardColumn[13].toString());
      			        		String cabinetSlostPos= boardColumn[14].toString();
      			        		String activeRecord = String.valueOf(boardColumn[18].toString());
      			        		System.out.println("THE cabinetNo IS "+cabinetNo);
      			        		System.out.println("THE cabinetShelf IS "+cabinetSubrack);
      			        		System.out.println("THE cabinetSlost IS "+cabinetSlost);
      			        		System.out.println("THE cabinetSlostPos IS "+cabinetSlostPos);

      			        		String boardPosition= "Cabinet "+cabinetNo+"/ Subrack "+cabinetSubrack+"/ Slot "+cabinetSlost+"/ Slot Pos "+cabinetSlostPos;
      			        		System.out.println("THE boardPosition IS "+boardPosition);
      			        		
      			        		
      			        		/*Object x = boardColumn[10];
      			        		System.out.println("THE x Object IS "+x);
      			        		
      			        		String boardPosition= "Cabinet "+x.toString();
      			        		System.out.println("THE boardPosition IS "+boardPosition);
      			        		
      			        		String cabinetTest = x.toString();
      			        		System.out.println("THE Test String IS "+cabinetTest);
      			        		*/
      			        		//Object cabinet = boardColumn[10];
      			        		//System.out.println("THE cabinet Object IS "+cabinet);
      			        		
      			        		//String boardPosition= "Cabinet "+cabinet.toString();
      			        		//System.out.println("THE boardPosition IS "+boardPosition);
      			        		
      			        		//String cabinetTest = cabinet.toString();
      			        		//System.out.println("THE Test String IS "+cabinetTest);
      			        		
      			        		String boardSource = null;
      			        		String nodeActiveType = boardColumn[17].toString();
      			        		if(StringUtils.equalsIgnoreCase(nodeActiveType,"New Node")) {
      			        		if(StringUtils.equalsIgnoreCase(transType,"New Hardware")) {
      			        		    System.out.println("It IS new hardware");
      			        			
      			        		    System.out.println("THE nodeActiveType IS "+nodeActiveType);
      			        		    
      			        		    boardSource = nodeActiveType +"\n"+" - New Board Hardware"  ;
      			        		}
      			        		else if(StringUtils.equalsIgnoreCase(transType,"Existed Hardware")) {
      			        		    System.out.println("It IS Existed hardware");
      			        			//String nodeActiveType = boardColumn[16].toString();
      			        		    System.out.println("THE nodeActiveType IS "+nodeActiveType);
      			        		    
      			        		    boardSource = nodeActiveType +"\n"+" - Existed Board Hardware"  ;
      			        		}
      			        		}
      			        		else if(StringUtils.equalsIgnoreCase(nodeActiveType,"Node Disappeared")) {
     			        			 //if(nodeActiveType == "Node Disappeared") {
 			        				 boardSource = nodeActiveType;
      			        		}
      			        			
      			        	    else {
      			        				 boardSource = transType;
      			        			 }
      			        		
      			        		
      			        		
      			        		
      			        		 transaction.setCreationDate((String)boardColumn[0]);
     			        	     transaction.setTransSource(transSource);
     			        	     transaction.setTransType(boardSource);
     			        	     transaction.setTransID(transID);
     			        	     transaction.setActiveRecord(activeRecord);
     			        	     transaction.setCellId(String.valueOf(""));
     				             transaction.setCellName(String.valueOf(""));
     			        	     transaction.setNodeId(nodeID);
     			        	     transaction.setNodeName(nodeName);
     			        	     transaction.setSiteId(SiteID);
     			        	     transaction.setSiteName(SiteName);
     			        	     transaction.setCabinetPosition(cabinetPosition);
     					         transaction.setCabinetModel(cabinetModel);
     					         transaction.setCabinetSNo(cabinetSNo);
     						     transaction.setBoardPosition(boardPosition);
     					         transaction.setBoardModel(boardModel);
     					         transaction.setBoardSNo(boardSNo);
     					         //transaction.setBoardTransType(boardColumns[6].toString());
     			        
     			               transactionList.add(transaction);
        			           System.out.println("THE transaction List IS "+mapper.writeValueAsString(transactionList));
     			          }
     			        	
     			        	
     			       }
     			      // params.remove(nodePk);
            		 }
            		 
            	/*	 if (StringUtils.equalsIgnoreCase(transactionTableOption, "CeS"))  {
          			   System.out.println("THE Cell/SITE");
          			   params.add(siteId);
          			   
			         ///site
			         siteStringQuery = "SELECT SITE_ID, WARE_NAME FROM WAREHOUSE WHERE SITE_ID IN(SELECT SITE_ID FROM NODE_ACTIVE WHERE NODE_PK =:param_3 AND SITE_ID =:param_4) AND CREATION_DATE >= :param_1 AND LAST_MODIFY_DATE <= :param_2";
			         Object[] siteColumns = appConfig.findAllBySQLQueryParamsAsObjectList(siteStringQuery,params);
			         System.out.println("the RESULT IS SITE "+ mapper.writeValueAsString(siteColumns));
			         //System.out.println("PASS THE SITE SIZE "+ siteColumns.size());
			         
			         //if(ArrayUtils.isNotEmpty(siteColumns)) {
			         if(siteColumns != null) {
				       System.out.println("PASS THE SITE ");
				       //Object[] siteColumnsa = siteColumns.toArray();
				       System.out.println("the RESULT as object IS "+ mapper.writeValueAsString(siteColumns[0]));
				       System.out.println("the RESULT as object 1 IS "+ mapper.writeValueAsString(siteColumns[1]));

				       transaction.setSiteId(siteColumns[0].toString());
			           transaction.setSiteName(siteColumns[1].toString());
			           
			         }
			         params.remove(siteId);
			         params.add(cellId);
			         ///cell 
  			         cellStringQuery ="SELECT GCELL_ID AS CELL_ID, CELLNAME , TRANS_TYPE  FROM NODE_GCELL WHERE NODE_PK = :param_3 AND GCELL_ID =:param_4 AND CREATION_DATE >= :param_1 AND UPDATE_DATE <= :param_2 UNION (SELECT LCELL_ID AS CELL_ID, CELLNAME,TRANS_TYPE FROM NODE_LCELL WHERE NODE_PK = :param_3 AND LCELL_ID =:param_4 AND CREATION_DATE >= :param_1 AND UPDATE_DATE <= :param_2) UNION (SELECT UCELL_ID AS CELL_ID, CELLNAME, TRANS_TYPE FROM NODE_UCELL WHERE NODE_PK = :param_3 AND UCELL_ID =:param_4 AND CREATION_DATE >= :param_1 AND UPDATE_DATE <= :param_2)";
                     Object[] cellColumns = appConfig.findAllBySQLQueryParamsAsObjectList(cellStringQuery,params);
  			         System.out.println("the RESULT OF THE CELL IS "+ mapper.writeValueAsString(cellColumns));
  			         if(cellColumns != null) {
  				       transaction.setCellId(cellColumns[0].toString());
  				       transaction.setCellName(cellColumns[1].toString());
  				       transaction.setCellTransType(cellColumns[2].toString());
  			   
  				
  			         }
  			         params.remove(cellId);
  			       if(siteColumns != null && cellColumns != null) {
			           transactionList.add(transaction);
			           System.out.println("THE transaction List IS "+mapper.writeValueAsString(transactionList));
  			       }
			       params.remove(nodePk);
          	 }*/
            		 
              //}
            	   
            	   
		   //}
		   
		   
		   
		   return transactionList;

	}	

	@SuppressWarnings("unused")
	public List<AssetLifeCycle_Transaction> findTransactionListByNode(String transactionTableOption, Timestamp StartDate,Timestamp EndDate, String nodeId, String siteId,String siteName, String cellId) throws Exception{
		
		List<AssetLifeCycle_Transaction> transactionListByNode = new ArrayList<AssetLifeCycle_Transaction>();
		AssetLifeCycle_Transaction transaction = new AssetLifeCycle_Transaction();
		ArrayList<AssetLifeCycle_Transaction> assetLifeTransArray = new ArrayList<AssetLifeCycle_Transaction>();
		String nodePkQuery = null,siteStringQuery  = null, nodeStringQuery = null, cellStringQuery = null, cabinetStringQuery = null, boardStringQuery = null ,antennaStringQuery = null ,hostStringQuery = null  ;
		List<Object> params = new ArrayList<Object>();
		params.add(StartDate);
   	    params.add(EndDate);
   	    params.add(nodeId);
   	    
   	    if (StringUtils.equalsIgnoreCase(transactionTableOption, "N") || StringUtils.equalsIgnoreCase(transactionTableOption, "CeSN"))  {
   	        
   	    	if(!StringUtils.equalsIgnoreCase(nodeId,"null") && nodeId != null && nodeId != ""){
   	    		if( !StringUtils.equalsIgnoreCase(siteId,"null") && siteId != null && siteId != "" ){
   	    			if(!StringUtils.equalsIgnoreCase(siteName,"null") && siteName != null && siteName != "" ){
   			    	   System.out.println("THE Site/SiteName/NODE");
   			    	 
   			    	   nodeStringQuery = "SELECT TO_CHAR(NODE_ACTIVE.CREATION_DATE, 'DD-MM-YYYY') AS CREATION_DATE, NODE_ACTIVE.FROM_TRANS_SOURCE,"
 		  	         		+ " NODE_ACTIVE.TRANS_TYPE, NODE_ACTIVE.FROM_TRANS_ID AS TRANS_ID,NODE_ACTIVE.node_Id, NODE_ACTIVE.node_name, "
 		  	         		+ "NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER,NODE_ACTIVE.NODE_MODEL AS NODE_TYPE ,"
 		  	         		+ "NODE_ACTIVE.SITE_ID,NODE_ACTIVE.WARE_ID, NODE_ACTIVE.WARE_NAME,NODE_ACTIVE.ACTIVE_RECORD FROM NODE_ACTIVE INNER JOIN NODE_CABINET ON"
 		  	         		+ " NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.CABINETNO = '0' AND  "
 		  	         		+ " NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.node_Id =:param_3 AND NODE_ACTIVE.SITE_ID =:param_4 AND NODE_ACTIVE.WARE_NAME =:param_5 order by NODE_ACTIVE.CREATION_DATE desc";
 		    	 
 		    	       params.add(siteId);
 		    	       params.add(siteName);
   	    			
   	    		     }
   	    			else {
   	    				System.out.println("THE Site/NODE");
      			    	 
   	    			    nodeStringQuery = "SELECT TO_CHAR(NODE_ACTIVE.CREATION_DATE, 'DD-MM-YYYY') AS CREATION_DATE, NODE_ACTIVE.FROM_TRANS_SOURCE,"
   		  	         		+ " NODE_ACTIVE.TRANS_TYPE, NODE_ACTIVE.FROM_TRANS_ID AS TRANS_ID,NODE_ACTIVE.node_Id, NODE_ACTIVE.node_name, "
   		  	         		+ "NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER,NODE_ACTIVE.NODE_MODEL AS NODE_TYPE ,"
   		  	         		+ "NODE_ACTIVE.SITE_ID,NODE_ACTIVE.WARE_ID, NODE_ACTIVE.WARE_NAME,NODE_ACTIVE.ACTIVE_RECORD FROM NODE_ACTIVE INNER JOIN NODE_CABINET ON"
   		  	         		+ " NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.CABINETNO = '0' AND  "
   		  	         		+ " NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.node_Id =:param_3 AND NODE_ACTIVE.SITE_ID =:param_4 order by NODE_ACTIVE.CREATION_DATE desc";
   		    	 
   		    	       params.add(siteId);
   			    
   	    			}
   	    				
   	    			}
   	    		else {
   	    			System.out.println("THE NODE only selected");
   	   	    	    System.out.println("THE NODE ID is "+ nodeId);
   	  		    
   	  		            ///node
   	  		            nodeStringQuery = "SELECT TO_CHAR(NODE_ACTIVE.CREATION_DATE, 'DD-MM-YYYY') AS CREATION_DATE, NODE_ACTIVE.FROM_TRANS_SOURCE,"
   	  	         		+ " NODE_ACTIVE.TRANS_TYPE, NODE_ACTIVE.FROM_TRANS_ID AS TRANS_ID,NODE_ACTIVE.node_Id, NODE_ACTIVE.node_name, "
   	  	         		+ "NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER,NODE_ACTIVE.NODE_MODEL AS NODE_TYPE ,"
   	  	         		+ "NODE_ACTIVE.SITE_ID,NODE_ACTIVE.WARE_ID, NODE_ACTIVE.WARE_NAME,NODE_ACTIVE.ACTIVE_RECORD FROM NODE_ACTIVE INNER JOIN NODE_CABINET ON"
   	  	         		+ " NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.CABINETNO = '0' AND  "
   	  	         		+ " NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.node_Id =:param_3 order by NODE_ACTIVE.CREATION_DATE desc";
   	  		
   	    		}
   		    	 
   		    	
   	    		
   	    		
   	    	
   	    	}
   	    	  
   	    	
		    
		   /* if(!StringUtils.equalsIgnoreCase(nodeId,"null") && nodeId != null && nodeId != "" && !StringUtils.equalsIgnoreCase(cellId,"null") && cellId != null && cellId != ""){
		    	System.out.println("THE CELL/NODE");
		    	 
		    	 nodeStringQuery = "SELECT TO_CHAR(NODE_ACTIVE.CREATION_DATE, 'DD-MM-YYYY') AS CREATION_DATE, NODE_ACTIVE.FROM_TRANS_SOURCE,"
		  	         		+ " NODE_ACTIVE.TRANS_TYPE, NODE_ACTIVE.FROM_TRANS_ID AS TRANS_ID,NODE_ACTIVE.node_Id, NODE_ACTIVE.node_name, "
		  	         		+ "NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER,NODE_ACTIVE.NODE_MODEL AS NODE_TYPE ,"
		  	         		+ "NODE_ACTIVE.SITE_ID,NODE_ACTIVE.WARE_ID, NODE_ACTIVE.WARE_NAME,NODE_ACTIVE.ACTIVE_RECORD FROM NODE_ACTIVE INNER JOIN NODE_CABINET ON"
		  	         		+ " NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.CABINETNO = '0' AND  "
		  	         		+ " NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.node_Id =:param_3 AND NODE_ACTIVE.SITE_ID =:param_4";
		    	 
		    	 params.add(cellId);
	        	 
	         }
	         */
		    
		  
		    
   	     Object[] nodeColumns = AssetLifeCycleCulomns(session,nodeStringQuery ,params);
         System.out.println("the RESULT OF THE NODE IS "+ mapper.writeValueAsString(nodeColumns));
         if(nodeColumns != null) {
        	 for (int i = 0; i < nodeColumns.length; i++) {
       		   
	            transaction = new AssetLifeCycle_Transaction();  

	        		Object[] nodeColumn = (Object[])nodeColumns[i];
	        		System.out.println("THE nodeColumn_"+i+" IS "+mapper.writeValueAsString(nodeColumn));
	        		System.out.println("THE nodeColumn 0 IS "+nodeColumn[0].toString());
	        		System.out.println("THE nodeColumn 1 IS "+nodeColumn[1]);
	        		System.out.println("THE nodeColumn 2 IS "+nodeColumn[2].toString());
	        		System.out.println("THE nodeColumn 3 IS "+nodeColumn[3].toString());
	        		System.out.println("THE nodeColumn 4 IS "+nodeColumn[4].toString());
	        		System.out.println("THE nodeColumn 5 IS "+nodeColumn[5].toString());
	        		System.out.println("THE nodeColumn 6 IS "+nodeColumn[6].toString());
	        		System.out.println("THE nodeColumn 7 IS "+nodeColumn[7].toString());
	        		System.out.println("THE nodeColumn 8 IS "+nodeColumn[8].toString());
	        		System.out.println("THE nodeColumn 9 IS "+nodeColumn[9].toString());
	        		System.out.println("THE nodeColumn 10 IS "+nodeColumn[10].toString());
	        		System.out.println("THE nodeColumn 11 IS "+nodeColumn[11].toString());
	        		
	        		String transSource = nodeColumn[1].toString();
	        		String transType = nodeColumn[2].toString();
	        		String transID = nodeColumn[3].toString();
	        		String nodeID = nodeColumn[4].toString();
	        		String nodeName = nodeColumn[5].toString(); 
	        		String nodeSNo = nodeColumn[6].toString();
	        		String nodeType = nodeColumn[7].toString(); 
	        		String SiteID = nodeColumn[8].toString();
	        		String SiteName = nodeColumn[10].toString();
	        		String wareID = nodeColumn[9].toString();
	        		String activeRecord = nodeColumn[11].toString();
        	 
	        		
	        		
	             transaction.setCreationDate((String)nodeColumn[0]);
        	     transaction.setTransSource(transSource);
        	     transaction.setTransType(transType);
        	     transaction.setTransID(transID);
        	     transaction.setActiveRecord(activeRecord);
        	     transaction.setCellId(String.valueOf(""));
	             transaction.setCellName(String.valueOf(""));
        	     transaction.setNodeId(nodeID);
        	     transaction.setNodeName(nodeName);
        	     transaction.setNodeSNo(nodeSNo);
        	     transaction.setNodeType(nodeType);
        	     transaction.setSiteId(SiteID);
        	     transaction.setSiteName(SiteName);
                 
        
        	     transactionListByNode.add(transaction);
	             System.out.println("THE transaction List IS "+mapper.writeValueAsString(transactionListByNode));
         
        	 }
 }		   
}
   	    
   	 if (StringUtils.equalsIgnoreCase(transactionTableOption, "B"))  {
		 System.out.println("THE BOARD");
		 
		 if(!StringUtils.equalsIgnoreCase(nodeId,"null") && nodeId != null && nodeId != ""){
	    		if( !StringUtils.equalsIgnoreCase(siteId,"null") && siteId != null && siteId != "" ){
	    			if(!StringUtils.equalsIgnoreCase(siteName,"null") && siteName != null && siteName != "" ){
			    	   System.out.println("THE Site/SiteName/NODE");
			    	 
					    boardStringQuery = "SELECT  TO_CHAR(NODE_BOARD.CREATION_DATE , 'DD-MM-YYYY') AS CREATION_DATE, NODE_BOARD.FROM_TRANS_SOURCE ,  "
					    		+ "NODE_BOARD.TRANS_TYPE,  NODE_BOARD.FROM_TRANS_ID AS TRANS_ID,NODE_ACTIVE.NODE_ID, NODE_ACTIVE.NODE_NAME,NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER "
					    		+ ",NODE_ACTIVE.SITE_ID, NODE_ACTIVE.WARE_NAME, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL ,"
					    		+ " NODE_BOARD.CABINETNO,  NODE_BOARD.SUBRACKNO,NODE_BOARD.SLOTNO,  NODE_BOARD.SLOTPOS,  NODE_BOARD.MODEL AS BOARD_MODEL "
					    		+ ",NODE_BOARD.SERIALNUMBER, NODE_ACTIVE.TRANS_TYPE AS NODE_ACTIVE_TYPE, NODE_BOARD.ACTIVE_RECORD "
					    		+ "FROM  ((NODE_BOARD INNER JOIN NODE_ACTIVE ON  NODE_BOARD.NODE_PK = NODE_ACTIVE.NODE_PK) INNER JOIN NODE_CABINET ON "
					    		+ "NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_BOARD.CREATION_DATE >= :param_1 AND NODE_BOARD.UPDATE_DATE <= :param_2 "
					    		+ "AND NODE_ACTIVE.node_Id =:param_3 AND NODE_ACTIVE.SITE_ID =:param_4 AND NODE_ACTIVE.WARE_NAME =:param_5) order by NODE_BOARD.CREATION_DATE desc";

		    	        params.add(siteId);
		    	        params.add(siteName);
	    			
	    		     }
	    			else {
	    				System.out.println("THE Site/NODE");
   			    	 
	    			    boardStringQuery = "SELECT  TO_CHAR(NODE_BOARD.CREATION_DATE , 'DD-MM-YYYY') AS CREATION_DATE, NODE_BOARD.FROM_TRANS_SOURCE ,"
	    			    		+ "  NODE_BOARD.TRANS_TYPE,  NODE_BOARD.FROM_TRANS_ID AS TRANS_ID,NODE_ACTIVE.NODE_ID, NODE_ACTIVE.NODE_NAME,NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER "
	    			    		+ ",NODE_ACTIVE.SITE_ID, NODE_ACTIVE.WARE_NAME, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL , "
	    			    		+ "NODE_BOARD.CABINETNO,  NODE_BOARD.SUBRACKNO,NODE_BOARD.SLOTNO,  NODE_BOARD.SLOTPOS,  NODE_BOARD.MODEL AS BOARD_MODEL "
	    			    		+ ",NODE_BOARD.SERIALNUMBER, NODE_ACTIVE.TRANS_TYPE AS NODE_ACTIVE_TYPE, NODE_BOARD.ACTIVE_RECORD "
	    			    		+ "FROM  ((NODE_BOARD INNER JOIN NODE_ACTIVE ON  NODE_BOARD.NODE_PK = NODE_ACTIVE.NODE_PK) INNER JOIN NODE_CABINET ON "
	    			    		+ "NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_BOARD.CREATION_DATE >= :param_1 AND NODE_BOARD.UPDATE_DATE <= :param_2 "
	    			    		+ "AND NODE_ACTIVE.node_Id =:param_3 AND NODE_ACTIVE.SITE_ID =:param_4 ) order by NODE_BOARD.CREATION_DATE desc";

		    	        params.add(siteId);
			    
	    			}
	    				
	    			}
	    		else {
	    			System.out.println("THE NODE only selected");
	   	    	    System.out.println("THE NODE ID is "+ nodeId);
	  		    
	  		           
	   			     boardStringQuery = "SELECT  TO_CHAR(NODE_BOARD.CREATION_DATE , 'DD-MM-YYYY') AS CREATION_DATE, NODE_BOARD.FROM_TRANS_SOURCE , "
	   			     		+ " NODE_BOARD.TRANS_TYPE,  NODE_BOARD.FROM_TRANS_ID AS TRANS_ID,NODE_ACTIVE.NODE_ID, NODE_ACTIVE.NODE_NAME,NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER"
	   			     		+ " ,NODE_ACTIVE.SITE_ID, NODE_ACTIVE.WARE_NAME, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL , "
	   			     		+ "NODE_BOARD.CABINETNO,  NODE_BOARD.SUBRACKNO,NODE_BOARD.SLOTNO,  NODE_BOARD.SLOTPOS,  NODE_BOARD.MODEL AS BOARD_MODEL "
	   			     		+ ",NODE_BOARD.SERIALNUMBER, NODE_ACTIVE.TRANS_TYPE AS NODE_ACTIVE_TYPE, NODE_BOARD.ACTIVE_RECORD "
	   			     		+ "FROM  ((NODE_BOARD INNER JOIN NODE_ACTIVE ON  NODE_BOARD.NODE_PK = NODE_ACTIVE.NODE_PK) INNER JOIN NODE_CABINET "
	   			     		+ "ON NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_BOARD.CREATION_DATE >= :param_1 AND NODE_BOARD.UPDATE_DATE <= :param_2 "
	   			     		+ "AND NODE_ACTIVE.node_Id =:param_3 ) order by NODE_BOARD.CREATION_DATE desc";

	    		}
		    	 
		    	
	    		
	    		
	    	
	    	}
	        
	        Object[] boardColumns = AssetLifeCycleCulomns(session,boardStringQuery ,params);
	        System.out.println("the RESULT OF THE BOARD IS "+ mapper.writeValueAsString(boardColumns));
	        if(boardColumns != null) {
	        	for (int i = 0; i < boardColumns.length; i++) {
	        		 transaction = new AssetLifeCycle_Transaction(); 
	        		 Object[] boardColumn = (Object[])boardColumns[i];
	        		 /*System.out.println("THE nodeColumn_"+i+" IS "+mapper.writeValueAsString(boardColumn));
	        		 System.out.println("THE boardColumn 0 IS "+boardColumn[0].toString());
	        		 System.out.println("THE boardColumn 1 IS "+boardColumn[1]);
	        		 System.out.println("THE boardColumn 2 IS "+boardColumn[2].toString());
	        		 System.out.println("THE boardColumn 3 IS "+boardColumn[3].toString());
	        		 System.out.println("THE boardColumn 4 IS "+boardColumn[4].toString());
	        		 System.out.println("THE boardColumn 5 IS "+boardColumn[5].toString());
	        		 System.out.println("THE boardColumn 6 IS "+boardColumn[6].toString());
	        		 System.out.println("THE boardColumn 7 IS "+boardColumn[7].toString());
	        		 System.out.println("THE boardColumn 8 IS "+boardColumn[8].toString());
	        		 System.out.println("THE boardColumn 9 IS "+boardColumn[9].toString());
	        		 System.out.println("THE boardColumn 10 IS "+boardColumn[10].toString());
	        		 System.out.println("THE boardColumn 11 IS "+boardColumn[11].toString());
	        		 System.out.println("THE boardColumn 12 IS "+boardColumn[12].toString());
	        		 System.out.println("THE boardColumn 13 IS "+boardColumn[13].toString());
	        		 System.out.println("THE boardColumn 14 IS "+String.valueOf(boardColumn[14].toString()));
			         System.out.println("THE boardColumn 15 IS "+boardColumn[15].toString());
			         */
			         
	        		String transSource = String.valueOf(boardColumn[1].toString());
	        		String transType = String.valueOf(boardColumn[2].toString());
	        		String transID = String.valueOf(boardColumn[3].toString());
	        		String nodeID = boardColumn[4].toString();
	        		String nodeName = boardColumn[5].toString(); 
	        		String cabinetSNo = String.valueOf(boardColumn[6].toString());
	        		String SiteID = String.valueOf(boardColumn[7].toString());
	        		String SiteName = String.valueOf(boardColumn[8].toString());
	        		String cabinetPosition = String.valueOf(boardColumn[9].toString());
	        		String cabinetModel = String.valueOf(boardColumn[10].toString());
	        		String boardModel = String.valueOf(boardColumn[15].toString());
	        		String boardSNo = String.valueOf(boardColumn[16].toString());
		            String cabinetNo = String.valueOf(boardColumn[11].toString());
		            String cabinetSubrack = String.valueOf(boardColumn[12].toString());
	        		String cabinetSlost = String.valueOf(boardColumn[13].toString());
	        		String cabinetSlostPos= boardColumn[14].toString();
	        		String activeRecord = String.valueOf(boardColumn[18].toString());
	        		System.out.println("THE cabinetNo IS "+cabinetNo);
	        		System.out.println("THE cabinetShelf IS "+cabinetSubrack);
	        		System.out.println("THE cabinetSlost IS "+cabinetSlost);
	        		System.out.println("THE cabinetSlostPos IS "+cabinetSlostPos);

	        		String boardPosition= "Cabinet "+cabinetNo+"/ Subrack "+cabinetSubrack+"/ Slot "+cabinetSlost+"/ Slot Pos "+cabinetSlostPos;
	        		System.out.println("THE boardPosition IS "+boardPosition);
	        		
	        		String boardSource = null;
	        		String nodeActiveType = boardColumn[17].toString();
	        		if(StringUtils.equalsIgnoreCase(nodeActiveType,"New Node")) {
	        		if(StringUtils.equalsIgnoreCase(transType,"New Hardware")) {
	        		    System.out.println("It IS new hardware");
	        			
	        		    System.out.println("THE nodeActiveType IS "+nodeActiveType);
	        		    
	        		    boardSource = nodeActiveType +"\n"+" - New Board Hardware"  ;
	        		}
	        		else if(StringUtils.equalsIgnoreCase(transType,"Existed Hardware")) {
	        		    System.out.println("It IS Existed hardware");
	        			//String nodeActiveType = boardColumn[16].toString();
	        		    System.out.println("THE nodeActiveType IS "+nodeActiveType);
	        		    
	        		    boardSource = nodeActiveType +"\n"+" - Existed Board Hardware"  ;
	        		}
	        		}
	        		else if(StringUtils.equalsIgnoreCase(nodeActiveType,"Node Disappeared")) {
	        			 //if(nodeActiveType == "Node Disappeared") {
     				 boardSource = nodeActiveType;
	        		}
	        			
	        	    else {
	        				 boardSource = transType;
	        			 }
	        		
	        		
	        		
	        		
	        		 transaction.setCreationDate((String)boardColumn[0]);
	        	     transaction.setTransSource(transSource);
	        	     transaction.setTransType(boardSource);
	        	     transaction.setTransID(transID);
	        	     transaction.setActiveRecord(activeRecord);
	        	     transaction.setCellId(String.valueOf(""));
		             transaction.setCellName(String.valueOf(""));
	        	     transaction.setNodeId(nodeID);
	        	     transaction.setNodeName(nodeName);
	        	     transaction.setSiteId(SiteID);
	        	     transaction.setSiteName(SiteName);
	        	     transaction.setCabinetPosition(cabinetPosition);
			         transaction.setCabinetModel(cabinetModel);
			         transaction.setCabinetSNo(cabinetSNo);
				     transaction.setBoardPosition(boardPosition);
			         transaction.setBoardModel(boardModel);
			         transaction.setBoardSNo(boardSNo);
			         //transaction.setBoardTransType(boardColumns[6].toString());
	        
			         transactionListByNode.add(transaction);
	               System.out.println("THE transaction List IS "+mapper.writeValueAsString(transactionListByNode));
	          }
	        	
	        	
	       }
	      // params.remove(nodePk);
	 }
   	 
   	 if (StringUtils.equalsIgnoreCase(transactionTableOption, "Ca"))  {
		   System.out.println("THE Cabinet");
		   
		   
		   if(!StringUtils.equalsIgnoreCase(nodeId,"null") && nodeId != null && nodeId != ""){
	    		if( !StringUtils.equalsIgnoreCase(siteId,"null") && siteId != null && siteId != "" ){
	    			if(!StringUtils.equalsIgnoreCase(siteName,"null") && siteName != null && siteName != "" ){
			    	   System.out.println("THE Site/SiteName/NODE");
			    	   cabinetStringQuery = "SELECT TO_CHAR( NODE_CABINET.CREATION_DATE, 'DD-MM-YYYY') as CREATION_DATE, NODE_CABINET.FROM_TRANS_SOURCE, "
	    	   			   		+ "NODE_CABINET.TRANS_TYPE, NODE_CABINET.FROM_TRANS_ID, NODE_ACTIVE.NODE_ID, NODE_ACTIVE.NODE_NAME, NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER "
	    	   			   		+ ",NODE_ACTIVE.SITE_ID, NODE_ACTIVE.WARE_NAME, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL,"
	    	   			   		+ "NODE_CABINET.ACTIVE_RECORD, NODE_ACTIVE.TRANS_TYPE as NODE_TRANS_TYPE FROM NODE_CABINET LEFT JOIN NODE_ACTIVE ON"
	    	   			   		+ " NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.CREATION_DATE >= :param_1 AND NODE_CABINET.UPDATE_DATE <= :param_2 "
	    	   			      	+ "AND NODE_ACTIVE.node_Id =:param_3 AND NODE_ACTIVE.SITE_ID =:param_4 AND NODE_ACTIVE.WARE_NAME =:param_5 order by NODE_CABINET.CREATION_DATE desc";
	    	     		 		

		    	        params.add(siteId);
		    	        params.add(siteName);
	    			
	    		     }
	    			else {
	    				System.out.println("THE Site/NODE");
  			    	 
	    				cabinetStringQuery = "SELECT TO_CHAR( NODE_CABINET.CREATION_DATE, 'DD-MM-YYYY') as CREATION_DATE, NODE_CABINET.FROM_TRANS_SOURCE, "
	    	   			   		+ "NODE_CABINET.TRANS_TYPE, NODE_CABINET.FROM_TRANS_ID, NODE_ACTIVE.NODE_ID, NODE_ACTIVE.NODE_NAME, NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER "
	    	   			   		+ ",NODE_ACTIVE.SITE_ID, NODE_ACTIVE.WARE_NAME, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL,"
	    	   			   		+ "NODE_CABINET.ACTIVE_RECORD, NODE_ACTIVE.TRANS_TYPE as NODE_TRANS_TYPE FROM NODE_CABINET LEFT JOIN NODE_ACTIVE ON"
	    	   			   		+ " NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.CREATION_DATE >= :param_1 AND NODE_CABINET.UPDATE_DATE <= :param_2 "
	    	   			   		+ "AND NODE_ACTIVE.node_Id =:param_3 AND NODE_ACTIVE.SITE_ID =:param_4 order by NODE_CABINET.CREATION_DATE desc";
	     		 		

		    	        params.add(siteId);
			    
	    			}
	    				
	    			}
	    		else {
	    			System.out.println("THE NODE only selected");
	   	    	    System.out.println("THE NODE ID is "+ nodeId);
	  		    
	  		           
	   			   cabinetStringQuery = "SELECT TO_CHAR( NODE_CABINET.CREATION_DATE, 'DD-MM-YYYY') as CREATION_DATE, NODE_CABINET.FROM_TRANS_SOURCE, "
	   			   		+ "NODE_CABINET.TRANS_TYPE, NODE_CABINET.FROM_TRANS_ID, NODE_ACTIVE.NODE_ID, NODE_ACTIVE.NODE_NAME, NODE_CABINET.SERIALNUMBER AS SERIAL_NUMBER "
	   			   		+ ",NODE_ACTIVE.SITE_ID, NODE_ACTIVE.WARE_NAME, NODE_CABINET.CABINETNO AS CABINET_POSITION, NODE_CABINET.MODEL,"
	   			   		+ "NODE_CABINET.ACTIVE_RECORD, NODE_ACTIVE.TRANS_TYPE as NODE_TRANS_TYPE FROM NODE_CABINET LEFT JOIN NODE_ACTIVE ON"
	   			   		+ " NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.CREATION_DATE >= :param_1 AND NODE_CABINET.UPDATE_DATE <= :param_2 "
	   			   		+ "AND NODE_ACTIVE.node_Id =:param_3 order by NODE_CABINET.CREATION_DATE desc";
 		

	    		}
		    	 
		    	
	    		
	    		
	    	
	    	}
		   
		   

	 Object[] cabinetColumns = AssetLifeCycleCulomns(session,cabinetStringQuery ,params);
     System.out.println("the RESULT OF THE CABINET IS "+ mapper.writeValueAsString(cabinetColumns));
     if(cabinetColumns != null) {
       	 
   	     for (int i = 0; i < cabinetColumns.length; i++) {
   		     transaction = new AssetLifeCycle_Transaction(); 
    		 Object[] cabinetColumn = (Object[])cabinetColumns[i];
    		 System.out.println("THE cabinetColumn"+i+" IS "+mapper.writeValueAsString(cabinetColumn));
    		 System.out.println("THE cabinetColumn 0 IS "+cabinetColumn[0].toString());
    		 System.out.println("THE cabinetColumn 1 IS "+cabinetColumn[1]);
    		 System.out.println("THE cabinetColumn 2 IS "+cabinetColumn[2].toString());
    		 System.out.println("THE cabinetColumn 3 IS "+cabinetColumn[3].toString());
    		 System.out.println("THE cabinetColumn 4 IS "+cabinetColumn[4].toString());
    		 System.out.println("THE cabinetColumn 5 IS "+cabinetColumn[5].toString());
    		 System.out.println("THE cabinetColumn 6 IS "+cabinetColumn[6].toString());
    		 System.out.println("THE cabinetColumn 7 IS "+cabinetColumn[7].toString());
    		 System.out.println("THE cabinetColumn 8 IS "+cabinetColumn[8].toString());
    		 System.out.println("THE cabinetColumn 9 IS "+cabinetColumn[9].toString());
    		 System.out.println("THE cabinetColumn 10 IS "+cabinetColumn[10].toString());
    		System.out.println("THE cabinetColumn 11 IS "+cabinetColumn[11].toString());
    		
    		String transSource = String.valueOf(cabinetColumn[1].toString());
    		String transType = String.valueOf(cabinetColumn[2].toString());
    		String transID = String.valueOf(cabinetColumn[3].toString());
    		String nodeID = String.valueOf(cabinetColumn[4].toString());
    		String nodeName = String.valueOf(cabinetColumn[5].toString()); 
    		String cabinetSNo = String.valueOf(cabinetColumn[6].toString());
    		String SiteID = String.valueOf(cabinetColumn[7].toString());
    		String SiteName = String.valueOf(cabinetColumn[8].toString());
    		String cabinetPosition = String.valueOf(cabinetColumn[9].toString());
    		String cabinetModel = String.valueOf(cabinetColumn[10].toString());
    		String activeRecord = String.valueOf(cabinetColumn[11].toString());
    		//String nodeTransType = String.valueOf(cabinetColumn[11].toString());
    		
    		String nodeActiveType = cabinetColumn[12].toString();
    		String cabinetTransType = null;
    		if(StringUtils.equalsIgnoreCase(transType,"New Hardware")) {
    		    System.out.println("It IS new hardware");
    		    System.out.println("THE nodeActiveType IS "+nodeActiveType);
    		    
    		    cabinetTransType = nodeActiveType +" - New Cabinet Hardware"  ;
    		}
    		else if(StringUtils.equalsIgnoreCase(transType,"Existed Hardware")) {
    		    System.out.println("It IS Existed hardware");
    		    System.out.println("THE nodeActiveType IS "+nodeActiveType);
    		    
    		    cabinetTransType = nodeActiveType +" - Existed Cabinet Hardware"  ;
    		}
    		else {
    			cabinetTransType = transType;
    		}
    		
    		
    		
         transaction.setCreationDate((String)cabinetColumn[0]);
   	     transaction.setTransSource(transSource);
   	     transaction.setTransType(cabinetTransType);
   	     transaction.setTransID(transID);
   	     transaction.setActiveRecord(activeRecord);
   	     transaction.setCellId(String.valueOf(""));
         transaction.setCellName(String.valueOf(""));
   	     transaction.setNodeId(nodeID);
   	     transaction.setNodeName(nodeName);
   	     transaction.setSiteId(SiteID);
   	     transaction.setSiteName(SiteName);
   	     transaction.setCabinetPosition(cabinetPosition);
	     transaction.setCabinetModel(cabinetModel);
	     transaction.setCabinetSNo(cabinetSNo);
	         //transaction.setCabinetTransType(cabinetColumns[3].toString());
   
	     transactionListByNode.add(transaction);
         System.out.println("THE transaction List IS "+mapper.writeValueAsString(transactionListByNode));
     }
   	
    
   }
        //params.remove(nodePk);
 }
	 
   	   
 /*  	 if (StringUtils.equalsIgnoreCase(transactionTableOption, "SN"))  {
		    System.out.println("THE Site/NODE");
		    
		    if(!StringUtils.equalsIgnoreCase(siteId,"null") && siteId != null && siteId != ""){}
		    ///node
         nodeStringQuery = "SELECT NODE_ACTIVE.node_Id, NODE_ACTIVE.node_name, NODE_CABINET.SERIALNUMBER, NODE_ACTIVE.Node_type, NODE_ACTIVE.trans_type FROM NODE_CABINET, NODE_ACTIVE WHERE  NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.BOMCODE != '0' AND NODE_CABINET.MANUFACTURERDATA != '0' AND NODE_CABINET.ACTIVE_RECORD = '1' AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID =:param_3";
         Object[] nodeColumns = appConfig.findAllBySQLQueryParamsAsObjectList(nodeStringQuery,params);
         System.out.println("the RESULT OF THE NODE IS "+ mapper.writeValueAsString(nodeColumns));
         if(nodeColumns != null) {
         	 transaction.setNodeId(nodeColumns[0].toString());
             transaction.setNodeName(nodeColumns[1].toString());
             transaction.setNodeSNo(nodeColumns[2].toString());
             transaction.setNodeType(nodeColumns[3].toString());
             transaction.setNodeTransType(nodeColumns[4].toString());
         }
         
         //site
         if(!StringUtils.equalsIgnoreCase(siteId,"null") && siteId != null && siteId != ""){
			   System.out.println("THE SITE is available");
			   params.add(siteId);
	          siteStringQuery = "SELECT SITE_ID, WARE_NAME FROM WAREHOUSE WHERE SITE_ID IN(SELECT SITE_ID FROM NODE_ACTIVE WHERE NODE_ID =:param_3 AND SITE_ID =:param_4) AND CREATION_DATE >= :param_1 AND LAST_MODIFY_DATE <= :param_2";

		   }
		   else {
		        siteStringQuery = "SELECT SITE_ID, WARE_NAME FROM WAREHOUSE WHERE SITE_ID IN(SELECT SITE_ID FROM NODE_ACTIVE WHERE NODE_ID =:param_3) AND CREATION_DATE >= :param_1 AND LAST_MODIFY_DATE <= :param_2";
		   }
		   
         Object[] siteColumns = appConfig.findAllBySQLQueryParamsAsObjectList(siteStringQuery,params);
         System.out.println("the RESULT IS SITE "+ mapper.writeValueAsString(siteColumns));
 
         if(siteColumns != null) {
      	   System.out.println("PASS THE SITE ");
             //Object[] siteColumnsa = siteColumns.toArray();
              System.out.println("the RESULT as object IS "+ mapper.writeValueAsString(siteColumns[0]));
              System.out.println("the RESULT as object 1 IS "+ mapper.writeValueAsString(siteColumns[1]));

              transaction.setSiteId(siteColumns[0].toString());
              transaction.setSiteName(siteColumns[1].toString());
   
          }
  
         if(!StringUtils.equalsIgnoreCase(siteId,"null") && siteId != null && siteId != ""){
      	   params.remove(siteId);
         }
             
	     if(siteColumns != null && nodeColumns != null) {
             transactionNodeList.add(transaction);
	         System.out.println("THE transaction Site/Node List IS "+mapper.writeValueAsString(transactionNodeList));
	     }
      
   	 }
   	if (StringUtils.equalsIgnoreCase(transactionTableOption, "CeSN"))  {
	    System.out.println("THE Cell/Site/NODE");
	    ///node
        nodeStringQuery = "SELECT NODE_ACTIVE.node_Id, NODE_ACTIVE.node_name, NODE_CABINET.SERIALNUMBER, NODE_ACTIVE.Node_type, NODE_ACTIVE.trans_type FROM NODE_CABINET, NODE_ACTIVE WHERE  NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.BOMCODE != '0' AND NODE_CABINET.MANUFACTURERDATA != '0' AND NODE_CABINET.ACTIVE_RECORD = '1' AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID =:param_3";
        Object[] nodeColumns = appConfig.findAllBySQLQueryParamsAsObjectList(nodeStringQuery,params);
        System.out.println("the RESULT OF THE NODE IS "+ mapper.writeValueAsString(nodeColumns));
        if(nodeColumns != null) {
     	   transaction.setNodeId(nodeColumns[0].toString());
           transaction.setNodeName(nodeColumns[1].toString());
           transaction.setNodeSNo(nodeColumns[2].toString());
           transaction.setNodeType(nodeColumns[3].toString());
           transaction.setNodeTransType(nodeColumns[4].toString());
     }
     
     
       //site
        if(!StringUtils.equalsIgnoreCase(siteId,"null") && siteId != null && siteId != ""){
			   System.out.println("THE SITE is available");
			   params.add(siteId);
	          siteStringQuery = "SELECT SITE_ID, WARE_NAME FROM WAREHOUSE WHERE SITE_ID IN(SELECT SITE_ID FROM NODE_ACTIVE WHERE NODE_ID =:param_3 AND SITE_ID =:param_4) AND CREATION_DATE >= :param_1 AND LAST_MODIFY_DATE <= :param_2";

		   }
		   else {
		        siteStringQuery = "SELECT SITE_ID, WARE_NAME FROM WAREHOUSE WHERE SITE_ID IN(SELECT SITE_ID FROM NODE_ACTIVE WHERE NODE_ID =:param_3) AND CREATION_DATE >= :param_1 AND LAST_MODIFY_DATE <= :param_2";
		   }
		   
        Object[] siteColumns = appConfig.findAllBySQLQueryParamsAsObjectList(siteStringQuery,params);
        System.out.println("the RESULT IS SITE "+ mapper.writeValueAsString(siteColumns));

        if(siteColumns != null) {
     	   System.out.println("PASS THE SITE ");
            //Object[] siteColumnsa = siteColumns.toArray();
             System.out.println("the RESULT as object IS "+ mapper.writeValueAsString(siteColumns[0]));
             System.out.println("the RESULT as object 1 IS "+ mapper.writeValueAsString(siteColumns[1]));

             transaction.setSiteId(siteColumns[0].toString());
             transaction.setSiteName(siteColumns[1].toString());
  
         }
 
        if(!StringUtils.equalsIgnoreCase(siteId,"null") && siteId != null && siteId != ""){
     	   params.remove(siteId);
        }
        
        ///cell 
        if(!StringUtils.equalsIgnoreCase(cellId,"null") && cellId != null && cellId != ""){
			 System.out.println("THE CELL is available");
			 params.add(cellId);
		         cellStringQuery ="SELECT NODE_GCELL.GCELL_ID AS CELL_ID, NODE_GCELL.CELLNAME , NODE_GCELL.TRANS_TYPE  FROM NODE_GCELL , NODE_ACTIVE where NODE_GCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID LIKE :param_3 AND NODE_GCELL.GCELL_ID LIKE :param_4 UNION (SELECT NODE_LCELL.LCELL_ID AS CELL_ID, NODE_LCELL.CELLNAME ,NODE_LCELL.TRANS_TYPE FROM NODE_LCELL , NODE_ACTIVE where NODE_LCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID LIKE :param_3 AND NODE_LCELL.LCELL_ID LIKE :param_4) UNION (SELECT NODE_UCELL.UCELL_ID AS CELL_ID, NODE_UCELL.CELLNAME , NODE_UCELL.TRANS_TYPE FROM NODE_UCELL, NODE_ACTIVE where NODE_UCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID LIKE :param_3 AND NODE_UCELL.UCELL_ID LIKE :param_4)";

		     }
		 else {
		       cellStringQuery ="SELECT NODE_GCELL.GCELL_ID AS CELL_ID, NODE_GCELL.CELLNAME , NODE_GCELL.TRANS_TYPE  FROM NODE_GCELL , NODE_ACTIVE where NODE_GCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID LIKE :param_3 UNION (SELECT NODE_LCELL.LCELL_ID AS CELL_ID, NODE_LCELL.CELLNAME ,NODE_LCELL.TRANS_TYPE FROM NODE_LCELL , NODE_ACTIVE where NODE_LCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID LIKE :param_3) UNION (SELECT NODE_UCELL.UCELL_ID AS CELL_ID, NODE_UCELL.CELLNAME , NODE_UCELL.TRANS_TYPE FROM NODE_UCELL, NODE_ACTIVE where NODE_UCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID LIKE :param_3)";

		 }
	 
		 Object[] cellColumns = appConfig.findAllBySQLQueryParamsAsObjectList(cellStringQuery,params);
	     System.out.println("the RESULT OF THE CELL IS "+ mapper.writeValueAsString(cellColumns));
	         if(cellColumns != null) {
		       transaction.setCellId(cellColumns[0].toString());
		       transaction.setCellName(cellColumns[1].toString());
		       transaction.setCellTransType(cellColumns[2].toString());
	  
	         }
	         if(!StringUtils.equalsIgnoreCase(cellId,"null") && cellId != null && cellId != ""){
	        	 params.remove(cellId);
	         }
			 
		         if(siteColumns != null && cellColumns != null && nodeColumns != null) {
		         transactionNodeList.add(transaction);
		         System.out.println("THE transaction Cell/Site/Node List IS "+mapper.writeValueAsString(transactionNodeList));
		         }
		        
		 
   	}
   	
   	if (StringUtils.equalsIgnoreCase(transactionTableOption, "CeN"))  {
	    System.out.println("THE Cell/NODE");
	    ///node
        nodeStringQuery = "SELECT NODE_ACTIVE.node_Id, NODE_ACTIVE.node_name, NODE_CABINET.SERIALNUMBER, NODE_ACTIVE.Node_type, NODE_ACTIVE.trans_type FROM NODE_CABINET, NODE_ACTIVE WHERE  NODE_CABINET.NODE_PK =NODE_ACTIVE.NODE_PK AND NODE_CABINET.BOMCODE != '0' AND NODE_CABINET.MANUFACTURERDATA != '0' AND NODE_CABINET.ACTIVE_RECORD = '1' AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID =:param_3";
        Object[] nodeColumns = appConfig.findAllBySQLQueryParamsAsObjectList(nodeStringQuery,params);
        System.out.println("the RESULT OF THE NODE IS "+ mapper.writeValueAsString(nodeColumns));
        if(nodeColumns != null) {
     	   transaction.setNodeId(nodeColumns[0].toString());
           transaction.setNodeName(nodeColumns[1].toString());
           transaction.setNodeSNo(nodeColumns[2].toString());
           transaction.setNodeType(nodeColumns[3].toString());
           transaction.setNodeTransType(nodeColumns[4].toString());
     }
     
     
       
        ///cell 
        if(!StringUtils.equalsIgnoreCase(cellId,"null") && cellId != null && cellId != ""){
			 System.out.println("THE CELL is available");
			 params.add(cellId);
		         cellStringQuery ="SELECT NODE_GCELL.GCELL_ID AS CELL_ID, NODE_GCELL.CELLNAME , NODE_GCELL.TRANS_TYPE  FROM NODE_GCELL , NODE_ACTIVE where NODE_GCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID LIKE :param_3 AND NODE_GCELL.GCELL_ID LIKE :param_4 UNION (SELECT NODE_LCELL.LCELL_ID AS CELL_ID, NODE_LCELL.CELLNAME ,NODE_LCELL.TRANS_TYPE FROM NODE_LCELL , NODE_ACTIVE where NODE_LCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID LIKE :param_3 AND NODE_LCELL.LCELL_ID LIKE :param_4) UNION (SELECT NODE_UCELL.UCELL_ID AS CELL_ID, NODE_UCELL.CELLNAME , NODE_UCELL.TRANS_TYPE FROM NODE_UCELL, NODE_ACTIVE where NODE_UCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID LIKE :param_3 AND NODE_UCELL.UCELL_ID LIKE :param_4)";

		     }
		 else {
		       cellStringQuery ="SELECT NODE_GCELL.GCELL_ID AS CELL_ID, NODE_GCELL.CELLNAME , NODE_GCELL.TRANS_TYPE  FROM NODE_GCELL , NODE_ACTIVE where NODE_GCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID LIKE :param_3 UNION (SELECT NODE_LCELL.LCELL_ID AS CELL_ID, NODE_LCELL.CELLNAME ,NODE_LCELL.TRANS_TYPE FROM NODE_LCELL , NODE_ACTIVE where NODE_LCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID LIKE :param_3) UNION (SELECT NODE_UCELL.UCELL_ID AS CELL_ID, NODE_UCELL.CELLNAME , NODE_UCELL.TRANS_TYPE FROM NODE_UCELL, NODE_ACTIVE where NODE_UCELL.NODE_PK = NODE_ACTIVE.NODE_PK AND NODE_ACTIVE.CREATION_DATE >= :param_1 AND NODE_ACTIVE.UPDATE_DATE <= :param_2 AND NODE_ACTIVE.NODE_ID LIKE :param_3)";

		 }
	 
		 Object[] cellColumns = appConfig.findAllBySQLQueryParamsAsObjectList(cellStringQuery,params);
	     System.out.println("the RESULT OF THE CELL IS "+ mapper.writeValueAsString(cellColumns));
	         if(cellColumns != null) {
		       transaction.setCellId(cellColumns[0].toString());
		       transaction.setCellName(cellColumns[1].toString());
		       transaction.setCellTransType(cellColumns[2].toString());
	  
	         }
	         if(!StringUtils.equalsIgnoreCase(cellId,"null") && cellId != null && cellId != ""){
	        	 params.remove(cellId);
	         }
		   
			   
       
	    if(cellColumns != null && nodeColumns != null) { 
		  transactionNodeList.add(transaction);
		  System.out.println("THE transaction Cell/Node List IS "+mapper.writeValueAsString(transactionNodeList));
	    }       
		        
		 
   	}
 */  	    
   	 return transactionListByNode;
		
	}
	
	@SuppressWarnings("unused")
	public List<AssetLifeCycle_Transaction> findTransactionListBySite(String transactionTableOption, Timestamp StartDate,Timestamp EndDate, String nodeId, String siteId,String siteName, String cellId) throws Exception{
		List<AssetLifeCycle_Transaction> transactionListBySite = new ArrayList<AssetLifeCycle_Transaction>();
		AssetLifeCycle_Transaction transaction = new AssetLifeCycle_Transaction();
		ArrayList<AssetLifeCycle_Transaction> assetLifeTransArray = new ArrayList<AssetLifeCycle_Transaction>();
		String nodePkQuery = null,siteStringQuery  = null, nodeStringQuery = null, cellStringQuery = null, cabinetStringQuery = null, boardStringQuery = null ,antennaStringQuery = null ,hostStringQuery = null  ;
		List<Object> params = new ArrayList<Object>();
		params.add(StartDate);
   	    params.add(EndDate);
		
		
		return transactionListBySite;
	}
	
	
	public String VoucherNbr (Session session,String queryString, List<Object> params ) {
		String vchrNbr = null;
		int i;
		query = session.createQuery(queryString);
		for (Object param : params) {
			i = params.indexOf(param) + 1;
			query.setString( "param_" + i, param.toString());
		}
		if (query.uniqueResult() != null){vchrNbr = query.uniqueResult().toString();}
		

		return vchrNbr;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AssetLifeCycle_Ledger> AssetLifeLedgerItem (Session session, String queryString, List<Object> params){
		List<AssetLifeCycle_Ledger> value = new ArrayList<>();
		int i;
		query = session.createQuery(queryString);
		for (Object param : params) {
		i = params.indexOf(param) + 1;
		query.setParameter("param_" + i, param);
		}
		if (query.list().size() > 0)
		    value = query.setResultTransformer(Transformers.aliasToBean(AssetLifeCycle_Ledger.class)).list();
		else
			System.out.println("There is not any record related to the query: " +query + " in the method: AppConfig.findAllByQueryParams");

		
		return value;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<String> AssetLifeCycleLists (Session session, String queryString, List<Object> params){
		List<String> value = new ArrayList<>();
		int i;
		query = session.createQuery(queryString);
		for (Object param : params) {
		i = params.indexOf(param) + 1;
		query.setParameter("param_" + i, param);
		}
		if (query.list().size() > 0)
		    value = query.list();
		else
			System.out.println("There is not any record related to the query: " +query + " in the method: AppConfig.findAllByQueryParams");

		
		return value;
		
	}
	
	
	public  Object[] AssetLifeCycleCulomns(Session session, String queryString, List<Object> params) {
		Object[] value = null;
		int i;

		query = session.createSQLQuery(queryString);
		for (Object param : params) {
		i = params.indexOf(param) + 1;
		query.setParameter("param_" + i, param);
		}
		if (query.list().size() > 0) {
			value = query.list().toArray();
		}
		else {
			value = null;
		}
		return  value;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> AssetLifeCycleListObject(Session session, String queryString, List<Object> params) {
		List<Object[]> value = new ArrayList<>();
		
		int i;
		query = session.createQuery(queryString);
		for (Object param : params) {
		i = params.indexOf(param) + 1;
		query.setString("param_" + i, param.toString());
		}
		if (query.list().size() > 0)
		    value = query.list();
		else
			System.out.println("There is not any record related to the query: " +query + " in the method: AppConfig.findAllByQueryParams");

		return  value;
	}
	
	
	public <T> Object[] findAllByQueryParamsASObjectListMax(Session session, String queryString, List<Object> params) {
		Object[] value = null;
		 int i;
		
		query = session.createQuery(queryString);
		for (Object param : params) {
		i = params.indexOf(param) + 1;
		query.setParameter("param_" + i, param);
		}
		query.setFirstResult(0);
		query.setMaxResults(1);
		
		if (query.list().size() > 0)
			value = (Object[]) query.list().toArray()[0];
		
		
		return  value;
	}
}








