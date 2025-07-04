package com.aliat.alm.discoveryNew;


import com.aliat.alm.models.ArNode;
import com.aliat.alm.models.ArPartNumber;
import com.aliat.alm.models.ArSerialNumber;
import com.aliat.alm.models.ArSite;
import com.aliat.alm.models.AssetRegistry;
import com.aliat.alm.models.DNIFormView;
import com.aliat.alm.models.DiscoveryNewItemNode;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.models.FarNode;
import com.aliat.alm.models.FarPartNumber;
import com.aliat.alm.models.FarSerialNumber;
import com.aliat.alm.models.FarSite;
import java.io.IOException;
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
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ManagerApprovals {
	private static final Logger logger = LoggerFactory.getLogger(DiscoveryController.class);
	private static Query query = null;
	private static ObjectMapper mapper = new ObjectMapper();
	int i;
	private static String str = null;
	private static 	Object[] result = null;
 

	Calendar calendar = new GregorianCalendar();
	int year = calendar.get(Calendar.YEAR);


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void ApprovalProjectandAsset(String trans_Type, String getApproval, String dnStatus, String AssetRegID, String ArCode, String PurchaseOrId, String itmcode, String itmname, String WorkOrder, String DniID, String toSiteID, String supplierID, String supplierName, String towareID, String towareName, String serialnb, float dnRate, String itemModel, String itemPartNb, String toSite, String toSerialNumber, String toSlot, String Site, String fromSlot, String SiteID,String macAddress, Session session, Transaction tx) {
		Query getpoItemID = session.createNativeQuery("select PO_ITEM_ID from PURCHASE_ORDER_ITEM where ITEM_CODE = '"+itmcode+"' and PO_ID = '"+PurchaseOrId+"'");
		getpoItemID.executeUpdate();
		String poItemID = (String) getpoItemID.uniqueResult();
		
		AssetNewNode(trans_Type, getApproval, dnStatus,AssetRegID, ArCode, PurchaseOrId,itmcode,itmname,WorkOrder,DniID,toSiteID,supplierID,supplierName,towareID,towareName,serialnb,dnRate,itemModel,itemPartNb,toSite,toSerialNumber,toSlot,Site,fromSlot,SiteID, session, tx);
		

		if (AssetRegID == null) {

			String AR_Site_ID;
			//ArCode = "AR_" + year + "_" + appConfig.getSequenceNbr(9);
			synchronized (this) {						
				ArCode = "AR_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET ASSET_REGISTRY = ASSET_REGISTRY + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
				AR_Site_ID = "ARSITE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_SITE = AR_SITE + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
				}
	    	//String AR_Site_ID = "AR_SITE_" + year + "_"  + appConfig.getSequenceNbr(30);
		
	    	
			// ADD DiscoveryNewItem TO ASSET REGISTRY TABLE

	    	AssetRegistry assetregistry = new AssetRegistry();
			assetregistry.setArID(ArCode);
			assetregistry.setAritemCode(itmcode);
			str = "SELECT item_category, item_catcode FROM Item WHERE item_code = :param1";
			 query = session.createNativeQuery(str)
			                    .setParameter("param1", itmcode);
			result = (Object[]) query.uniqueResult();
	        assetregistry.setItemCat(result[0].toString());
			assetregistry.setItemCatCode(result[1].toString());
			assetregistry.setArcreatedDate(new Timestamp(System.currentTimeMillis()));
			assetregistry.setArlastModifiedDate(new Timestamp(System.currentTimeMillis()));
			assetregistry.setAritemName(itmname);
			assetregistry.setArdniID(DniID);
			assetregistry.setInitialCost(dnRate);
			assetregistry.setPoID(PurchaseOrId);
			assetregistry.setSupplierID(supplierID);
			assetregistry.setSupplierName(supplierName);
			assetregistry.setItemModel(itemModel);
			assetregistry.setItemPartNumber(itemPartNb);
			assetregistry.setItemSN(toSerialNumber);
			assetregistry.setArStatus("Running");
			assetregistry.setPoItemId(poItemID);
			assetregistry.setArsiteId(AR_Site_ID);
			session.saveOrUpdate(assetregistry);


			// UPDATE AR_ID & AR_SITE_ID IN DISCOVERY_NEW_ITEM TABLE
			
			query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1, AR_SITE_ID = :param2 where DNI_ID = :param3");
			query.setParameter("param1", ArCode);
			query.setParameter("param2", AR_Site_ID);
			query.setParameter("param3", DniID);
			session.flush();
			query.executeUpdate();
			
			
			
			// ADD TO AR_SITE TABLE
	    	
	    	ArSite arSite = new ArSite();
	    	arSite.setArsiteId(AR_Site_ID);
	    	arSite.setSiteID(toSiteID);
	    	arSite.setSiteName(towareName);
	    	arSite.setWareID(towareID);  
	    	arSite.setArID(ArCode);
	    	session.saveOrUpdate(arSite);
			
			
			// ADD TO AR_NODE TABLE
	    	String AR_NodeID;
	        query = session.createNativeQuery("select TO_NODE_ID,TO_NODE_NAME,TO_NODE_TYPE FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID=:param1");
	 		query.setParameter("param1", DniID);
	 		query.executeUpdate();
	 		List<Object[]> resultList = query.getResultList();
	 		
	 			
	 		for (Object[] result : resultList) {
	 			if (result[0] != null) {
	 		String NodeId= result[0].toString();
	 	
	 		
	 			    AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
	 				query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
	 				query.executeUpdate();
	 				session.createNativeQuery("commit").executeUpdate();
	 			
	     		
	     	ArNode assetRegNode = new ArNode();
	     	assetRegNode.setNodearId(AR_NodeID);
	     	assetRegNode.setNodeID(NodeId);
	     	assetRegNode.setNodeName(result[1].toString());
	     	assetRegNode.setNodeType(result[2].toString());
	     	assetRegNode.setArID(ArCode);
	     	session.saveOrUpdate(assetRegNode);
	     
	     	
	 		}
	 		}
	    	
	    	
			// Add to AR_SERIAL_NUMBER table
	    	String AR_SerialNum_ID;
			if(!StringUtils.equalsIgnoreCase(serialnb, "0") ||  !StringUtils.equalsIgnoreCase(macAddress, "0")) {
				synchronized (this) {						
					AR_SerialNum_ID = "ARSNUM_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					}
				//String AR_SerialNum_ID = "ARSNUM_" + year + "_" + appConfig.getSequenceNbr(31);
			
			ArSerialNumber assetRegSerialNumber = new ArSerialNumber();
			assetRegSerialNumber.setSerialId(AR_SerialNum_ID);
			assetRegSerialNumber.setSerialNumber(toSerialNumber);
			assetRegSerialNumber.setMacAddress(macAddress);
			assetRegSerialNumber.setModel(itemModel);
			assetRegSerialNumber.setPartNumber(itemPartNb);
			assetRegSerialNumber.setSite(toSite);
			assetRegSerialNumber.setPosition(toSlot);
			assetRegSerialNumber.setArID(ArCode);
			session.saveOrUpdate(assetRegSerialNumber);
			
			}
			
			// Add to AR_MODEL_PARTNUMBER table
			
			String itemmodel = itemModel;
			String AR_model_partNum;
			if(!StringUtils.equalsIgnoreCase(itemmodel, ""))
			{
				synchronized (this) {						
					AR_model_partNum = "ARMP_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_MODEL_PARTNO = AR_MODEL_PARTNO + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					}
				//String AR_model_partNum = "itmId_" + year + "_" +  appConfig.getSequenceNbr(26);
				
				ArPartNumber arPartNum = new ArPartNumber();
				arPartNum.setItmId(AR_model_partNum);
				arPartNum.setItemCode(itmcode);
				arPartNum.setItemPartNb(itemPartNb);
				arPartNum.setItemModel(itemModel);
				arPartNum.setArID(ArCode);
				arPartNum.setPrimary("1");
				arPartNum.setQtyPartNb(1);
				session.saveOrUpdate(arPartNum);
			}
			
			
		}


	// AssetReg Id not null
	if (AssetRegID != null) {
		ArCode = AssetRegID;
		
		query = session.createNativeQuery("update ASSET_REGISTRY SET ITEM_CODE = '"+itmcode+"',LAST_MODIFIED_DATE = sysdate,ITEM_NAME = '"+itmname+"',PO_ID = '"+PurchaseOrId+"',"
				+ "SUPPLIER_ID = '"+supplierID+"', SUPPLIER_NAME = '"+supplierName+"',ITEM_MODEL = '"+itemModel+"',ITEM_PART_NUMBER = '"+itemPartNb+"',ITEM_SN = '"+toSerialNumber+"', PO_ITEM_ID = '"+poItemID+"' WHERE AR_ID = :param1 ");
		query.setParameter("param1", ArCode);
		query.executeUpdate();
		
		query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
		query.setParameter("param1", ArCode);
		query.setParameter("param2", DniID);
		query.executeUpdate();
		
		
		
		// ADD TO AR_MODEL_PARTNUMBER TABLE
		
		query = session.createQuery("select t.itmId from ArPartNumber t where t.arID = :param1 ");
		query.setParameter("param1", ArCode);
		String ARModelPartNum = (String) query.uniqueResult();
		
		if(ARModelPartNum == null)
		{
			synchronized (this) {						
				ARModelPartNum = "ARMP_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_MODEL_PARTNO = AR_MODEL_PARTNO + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
				}
			 //ARModelPartNum = "itmId_" + year + "_" +  appConfig.getSequenceNbr(26);
		}
		
		query = session.createNativeQuery("update AR_MODEL_PARTNUMBER SET ITM_ID = '"+ARModelPartNum+"',ITEM_PART_NUMBER = '"+itemPartNb+"',QUANTITY = 1,ITEM_MODEL = '"+itemModel+"',ITEM_CODE = '"+itmcode+"',"
				+ "PRIMARY = '1' WHERE AR_ID = :param1 ");
		query.setParameter("param1", ArCode);
		query.executeUpdate();
		
		
		
		// ADD TO AR_SITE TABLE
		
		query = session.createQuery("select t.arsiteId from ArSite t where t.arID = :param1 ");
		query.setParameter("param1", ArCode);
		
		String AR_SiteID = (String) query.uniqueResult();
		
		if(AR_SiteID == null)
		{
			synchronized (this) {						
				AR_SiteID = "ARSITE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_SITE = AR_SITE + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
				}
			//AR_SiteID = "AR_SITE_" + year + "_"  + appConfig.getSequenceNbr(30);
		}
		
		query = session.createNativeQuery("update AR_SITE SET ARSITE_ID = '"+AR_SiteID+"',SITE_ID = '"+toSiteID+"',SITE_NAME = '"+towareName+"',WARE_ID = '"+towareID+"'"
				+ " WHERE AR_ID = :param1 ");
		query.setParameter("param1", ArCode);
		query.executeUpdate();
		
		
		
		
		/// ADD TO AR_NODE TABLE                                  

		query = session.createQuery("delete from ArNode t where t.arID = :param1");

		query.setParameter("param1", ArCode);
		query.executeUpdate();
		
		String AR_NodeID;
	    query = session.createNativeQuery("select TO_NODE_ID,TO_NODE_NAME,TO_NODE_TYPE FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID=:param1");
			query.setParameter("param1", DniID);
			query.executeUpdate();
			List<Object[]> resultList = query.getResultList();
				
			for (Object[] result : resultList) {
				if (result[0] != null) {
			String NodeId= result[0].toString();
			String Name= result[1].toString();
			String Type= result[2].toString();
			System.out.println(result);

			
				    AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
				
	 		
	 	ArNode assetRegNode = new ArNode();
	 	assetRegNode.setNodearId(AR_NodeID);
	 	assetRegNode.setNodeID(NodeId);
	 	assetRegNode.setNodeName(result[1].toString());
	 	assetRegNode.setNodeType(result[2].toString());
	 	assetRegNode.setArID(ArCode);
	 	session.saveOrUpdate(assetRegNode);
	 
			}
			}
		
		
		// Add to AR_SERIAL_NUMBER table
		
		query = session.createQuery("select t.serialId from ArSerialNumber t where t.arID = :param1 ");
		query.setParameter("param1", ArCode);
		String AR_SerialID = (String) query.uniqueResult();
		
		
		if(AR_SerialID == null) {
			synchronized (this) {						
				AR_SerialID = "ARSNUM_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
				query.executeUpdate();
				session.createNativeQuery("commit").executeUpdate();
				}
			 //AR_SerialID = "ARSNUM_" + year + "_" + appConfig.getSequenceNbr(31);
		}
		
		query = session.createNativeQuery("update AR_SERIAL_NUMBER SET SERIAL_ID = '"+AR_SerialID+"',SERIAL_NUMBER = '"+toSerialNumber+"',MODEL = '"+itemModel+"',PART_NUMBER = '"+itemPartNb+"', SITE = '"+toSite+"',POSITION = '"+toSlot+"' "
				+ " WHERE AR_ID = :param1 ");
		query.setParameter("param1", ArCode);
		query.executeUpdate();
		
	}
	
	

		
	}



	// APPROVED BY Finance
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void ApprovalFinance(String trans_Type, String getApproval, String dnStatus, String AssetRegID, String ArCode, String PurchaseOrId, String itmcode, String itmname, String WorkOrder, String DniID, String toSiteID, String supplierID, String supplierName, String towareID, String towareName, String serialnb, float dnRate, String itemModel, String itemPartNb, String toSite, String toSerialNumber, String toSlot, String Site, String fromSlot, String SiteID, String MacAddress,Session session, Transaction tx) {
		
	
		Query getpoItemID = session.createNativeQuery("select PO_ITEM_ID from PURCHASE_ORDER_ITEM where ITEM_CODE = '"+itmcode+"' and PO_ID = '"+PurchaseOrId+"'");
		getpoItemID.executeUpdate();
		String poItemID = (String) getpoItemID.uniqueResult();

		// check, if this itemCode related to poID, exist in CIP table (get the qty and cipID)
		query = session.createQuery(
				"Select t.TOTALQTY as totalqty, t.cipID as cipID from CapitalInProgress t where t.PoId =:param1 and t.cipitemCode =:param2");

		query.setParameter("param1", PurchaseOrId);
		query.setParameter("param2", itmcode);

		List result = query.list();


		String FarCode;

		
		// if exist, get only one row, with CIPqty field and cipID
		if (result != null) {
			

			for (Object obj : result) {
				Object[] fields = (Object[]) obj;
				String result1 = fields[0].toString();
				float updatedQty = Float.parseFloat(result1) - 1; // substract 1 from the TotalQty

				
				if (updatedQty == 0) {

					// DELETE FROM CIP TABLE
					
					query = session.createQuery("delete from CapitalInProgress c where c.cipID =:param1");
					query.setParameter("param1", fields[1]);
					query.executeUpdate();
				}

				else {

					 Query q5 = session.createQuery("update CapitalInProgress c set c.TOTALQTY =:param1 where c.cipID =:param2"); 
					 q5.setParameter("param1", updatedQty); 
					 q5.setParameter("param2", fields[1]); 
					 q5.executeUpdate();
					 query = session.createNativeQuery("commit");
					 query.executeUpdate(); 

				}



				//FarCode = "FAR_" + year + "_" + appConfig.getSequenceNbr(10);
				synchronized (this) {						
					FarCode = "FAR_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FIXED_ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET FIXED_ASSET_REGISTRY = FIXED_ASSET_REGISTRY + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					}
				float initialCost = dnRate;
				
				query = session.createNativeQuery("select USEFULL_LIFE_MONTHS from ITEM where ITEM_CODE = '"+itmcode+"'");
				query.executeUpdate();
				String item_Usefull_LifeMonths = (String) query.uniqueResult();
				float useful_life_month = 0;

				if (!StringUtils.equalsIgnoreCase(item_Usefull_LifeMonths, "")) { 
					
					useful_life_month = Float.parseFloat(item_Usefull_LifeMonths);
				}

				

				String FarSiteID;
				synchronized (this) {						
					FarSiteID = "FARSITE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_SITE = FAR_SITE + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					}
				
				
				// ADD DiscoveryNewItem TO ASSET REGISTRY TABLE

				FixedAssetRegistry FixedAssetReg = new FixedAssetRegistry();
				FixedAssetReg.setARID(AssetRegID);
				FixedAssetReg.setFarID(FarCode);     
				FixedAssetReg.setFaritemCode(itmcode);
				query = session.createNativeQuery("SELECT item_category, item_catcode FROM Item WHERE item_code = :param1")
	                    .setParameter("param1", itmcode);
			    Object[] res = (Object[]) query.uniqueResult();
	            FixedAssetReg.setItemCat(res[0].toString());
	            FixedAssetReg.setItemCatCode(res[1].toString());
	     		FixedAssetReg.setFarcreatedDate(new Timestamp(System.currentTimeMillis()));
				FixedAssetReg.setFarlastModifiedDate(new Timestamp(System.currentTimeMillis()));
				FixedAssetReg.setFaritemName(itmname);
				FixedAssetReg.setIniCost(initialCost);
				FixedAssetReg.setNetCost(initialCost);
				FixedAssetReg.setUsefulLifeMon(useful_life_month);
				FixedAssetReg.setDailyPercent(0);
				FixedAssetReg.setAccumPer(0);
				FixedAssetReg.setAccumDeprCode("0");
				FixedAssetReg.setDeprCode("0");
				FixedAssetReg.setDniID(DniID);
				FixedAssetReg.setSupplierID(supplierID);
				FixedAssetReg.setSupplierName(supplierName);
				FixedAssetReg.setPoId(PurchaseOrId);
				FixedAssetReg.setItemModel(itemModel);
				FixedAssetReg.setItemPartNb(itemPartNb);
				FixedAssetReg.setItemSN(toSerialNumber);
			    FixedAssetReg.setFarStatus("Running");
				FixedAssetReg.setPoItemId(poItemID);
				FixedAssetReg.setFarsiteId(FarSiteID);
				session.saveOrUpdate(FixedAssetReg);
				
				
				
				query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1,  FR_SITE_ID = :param2  where DNI_ID = :param3");
				query.setParameter("param1", FarCode);
				query.setParameter("param2", FarSiteID);
				query.setParameter("param3", DniID);
				query.executeUpdate();
				
				
				
				// ADD TO FAR NODE TABLE

				
	            query = session.createNativeQuery("select TO_NODE_ID,TO_NODE_NAME,TO_NODE_TYPE FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID=:param1");
				query.setParameter("param1", DniID);
				query.executeUpdate();
				List<Object[]> resultNList = query.getResultList();


				String FAR_NodeID;
		
				
					
		for (Object[] resultN : resultNList) {
			
			if (resultN[0] != null) {
		FAR_NodeID = "FARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
		query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_NODE = FAR_NODE + 1 ");
		query.executeUpdate();
		session.createNativeQuery("commit").executeUpdate();
		

	FarNode FixedAssetRegNode = new FarNode();

	FixedAssetRegNode.setNodefarId(FAR_NodeID);
	FixedAssetRegNode.setNodeID(resultN[0].toString());
	FixedAssetRegNode.setNodeName(resultN[1].toString());
	FixedAssetRegNode.setNodeType(resultN[2].toString());
	FixedAssetRegNode.setFarID(FarCode);

	session.saveOrUpdate(FixedAssetRegNode);

	}
				}
				
				
				// ADD TO FAR MODEL_PART_NB TABLE
				
				if(!StringUtils.equalsIgnoreCase(itemModel, "") || !StringUtils.equalsIgnoreCase(itemPartNb, ""))
				{
				String FAR_model_partNum;
				synchronized (this) {						
					FAR_model_partNum = "FARMP_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_MODEL_PARTNO = FAR_MODEL_PARTNO + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					}
				
				FarPartNumber farPartNumber = new FarPartNumber();
				
				farPartNumber.setItmId(FAR_model_partNum);
				farPartNumber.setItemCode(itmcode);
				farPartNumber.setItemPartNb(itemPartNb);
				farPartNumber.setItemModel(itemModel);
				farPartNumber.setFarID(FarCode);
				farPartNumber.setPrimary("1");
				farPartNumber.setQtyPartNb(1);
				session.saveOrUpdate(farPartNumber);
				
				}

				
				
				//Add to FAR_SERIAL_NUMBER table

				if(!StringUtils.equalsIgnoreCase(serialnb, "0") || (!StringUtils.equalsIgnoreCase(MacAddress, "0")) ) {
				String FAR_SerialNum_ID;
				synchronized (this) {						
					FAR_SerialNum_ID = "FARSNUM_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_SERIALNO = FAR_SERIALNO + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
					}
				FarSerialNumber fixedAssetRegSerialNumber = new FarSerialNumber();
				
				fixedAssetRegSerialNumber.setSerialId(FAR_SerialNum_ID);
				fixedAssetRegSerialNumber.setInputSerialNb(toSerialNumber);
				fixedAssetRegSerialNumber.setInputModel(itemModel);
				fixedAssetRegSerialNumber.setMacAddress(MacAddress);
				fixedAssetRegSerialNumber.setInputpartNumber(itemPartNb);
				fixedAssetRegSerialNumber.setInputsite(toSite);
				fixedAssetRegSerialNumber.setInputPosition(toSlot);
				fixedAssetRegSerialNumber.setFarID(FarCode);
				session.saveOrUpdate(fixedAssetRegSerialNumber);

				}

				
				
				//Add to FAR_SITE Table       
				
				if(!StringUtils.equalsIgnoreCase(toSite, null) ) {
					
				FarSite farSite = new FarSite();
				farSite.setFarsiteId(FarSiteID);
				farSite.setSiteID(toSiteID);
				farSite.setSiteName(towareName);
				farSite.setWareID(towareID);
				farSite.setFarID(FarCode);
				session.saveOrUpdate(farSite);
				}
				
				
				
				} // END for(Object obj : result) Loop
			
				} // if (result != null)


		
		List<Object> params= new ArrayList<Object>();
		String poStat = null;
		String getPoStatus = " select ordStatus from PurchaseOrder where ID = :param_1";
		params.add(PurchaseOrId);
		query = session.createQuery(getPoStatus);
		for (Object param : params) {
			i = params.indexOf(param) + 1;
			query.setParameter("param_" + i, param.toString());
		}
		if (query.uniqueResult() != null) {poStat = query.uniqueResult().toString();}
		params.clear();
		
		if (StringUtils.equalsIgnoreCase(poStat, "approved") ) 
		{
			String allGoodsRec = null;
			String getAllGRs = " select count(*) from GoodsReceived where grPOid = :param_1";
			params.add(PurchaseOrId);
			query = session.createQuery(getAllGRs);
			for (Object param : params) {
				i = params.indexOf(param) + 1;
				query.setParameter("param_" + i, param.toString());
			}
			if (query.uniqueResult() != null) {allGoodsRec = query.uniqueResult().toString();}
			params.clear();
			//String allGoodsRec = appConfig.findAllByQueryConditionByParam(GoodsReceived.class,getAllGRs, params);
				
			
			String allGRsApproved = null;
			String getAllGrsApproved = " select count(*) from GoodsReceived where grPOid = :param_1 and grStatus = 'completed' ";
			params.add(PurchaseOrId);
			query = session.createQuery(getAllGrsApproved);
			for (Object param : params) {
				i = params.indexOf(param) + 1;
				query.setParameter("param_" + i, param.toString());
			}
			if (query.uniqueResult() != null) {allGRsApproved = query.uniqueResult().toString();}
			params.clear();
			//String allGRsApproved = appConfig.findAllByQueryConditionByParam(GoodsReceived.class,getAllGrsApproved, params);
			
			
			if( Double.parseDouble(allGoodsRec)== Double.parseDouble(allGRsApproved))
			{
				
				String itemPO = "select distinct ItemCode from PurchaseOrderItem where POId like :param1";
				query = session.createQuery(itemPO);
				query.setParameter("param1", PurchaseOrId);
				List<Object[]> purchOrdItem = query.list();
				Object itemCode = null;
				for(Object PurchOrdItem: purchOrdItem ) {
					itemCode = ""+PurchOrdItem;
					String qtyItem= "select sum(Qty) from PurchaseOrderItem where ItemCode like :param_1 and POId like :param_2 ";
					params.add(itemCode);
					params.add(PurchaseOrId);
					double qty = findAllByMultiClzMultiQryPrms(session,qtyItem,params);
					String itemGR = "select sum(a.Qty) from GoodsReceivedItem a, GoodsReceived b where a.ItemCode like :param_1 and b.grPOid like :param_2 and a.GRid = b.ID";
					//List paramsGR = new ArrayList();
					params.add(itemCode);
					params.add(PurchaseOrId);
					double resQTYGR = findAllByMultiClzMultiQryPrms(session,itemGR,params);
					
					String resStatus="0";
					params.clear();
						if (resQTYGR == qty )
					{
							String itemInFAR = null;
							String getItemsInFAR = " select count(*) from FixedAssetRegistry where faritemCode = :param_1 and PoId = :param_2 ";
							params.add(itemCode);
							params.add(PurchaseOrId);
							query = session.createQuery(getItemsInFAR);
							for (Object param : params) {
								i = params.indexOf(param) + 1;
								query.setParameter("param_" + i, param.toString());
							}
							if (query.uniqueResult() != null) {itemInFAR = query.uniqueResult().toString();}
							params.clear();
							//String itemInFAR = appConfig.findAllByQueryConditionByParam(FixedAssetRegistry.class,getItemsInFAR, params);
						
							double ItemInFAR = 0;
							if(itemInFAR !=null) { ItemInFAR = Double.parseDouble(itemInFAR);}
							
						if(ItemInFAR  == qty)
						{
							resStatus = "1";
							
							params.add(resStatus);
							params.add(PurchaseOrId);
							params.add(itemCode);
							
							String Query = "update PurchaseOrderItem set poItemStatus = :param_1  where POId = :param_2 and ItemCode = :param_3";
							persistSingleColumn(session ,Query, params);
							params.clear();
						}
						else
						{
							resStatus = "0";
							
							params.add(resStatus);
							params.add(PurchaseOrId);
							params.add(itemCode);

							String Query = "update PurchaseOrderItem set poItemStatus = :param_1  where POId = :param_2 and ItemCode = :param_3";
							persistSingleColumn(session ,Query, params);
							params.clear();
						}
					}
				
				}
				String statusPoItem = " select distinct ItemCode from PurchaseOrderItem where POId like :param1 and poItemStatus = '1'";
				query = session.createQuery(statusPoItem);
				query.setParameter("param1", PurchaseOrId);
				List<Object[]> purchOrdItemList = query.list();
				//List<Object[]> purchOrdItemList = (List<Object[]>) appConfig.findAllByMultiClzQryPrms(statusPoItem, "param1",PurchaseOrId );
				if(purchOrdItem.size()==purchOrdItemList.size())
				{
					
					params.add("completed");

					params.add(PurchaseOrId);
					String QueryPoStatus = "update PurchaseOrder set ordStatus = :param_1  where ID = :param_2 ";
					persistSingleColumn(session ,QueryPoStatus, params);
					params.clear();

				}
			}
		}


	
		

		
	}



	//APPROVED BY Operation manager
	public void ApprovalOperational(String trans_Type, String getApproval, String dnStatus, String AssetRegID, String ArCode, String PurchaseOrId, String itmcode, String itmname, String WorkOrder, String DniID, String toSiteID, String supplierID, String supplierName, String towareID, String towareName, String serialnb, float dnRate, String itemModel, String itemPartNb, String toSite, String toSerialNumber, String toSlot, String Site, String fromSlot, String SiteID, String FAR,String MacAddress, Session session,Transaction tx) {
	
	String ARid=null;

		if( (!StringUtils.equalsIgnoreCase(serialnb, "0")) ||  (!StringUtils.equalsIgnoreCase(MacAddress, "0")) || (!StringUtils.equalsIgnoreCase(FAR, "0"))) // serial number exist
		{
			if (!StringUtils.equalsIgnoreCase(serialnb, "0")){
				query = session.createQuery("select distinct arID from ArSerialNumber where serialNumber = :param1");

				query.setParameter("param1", serialnb);
				 ARid = (String) query.uniqueResult();
				 query = session.createQuery("select distinct farID from FarSerialNumber where inputSerialNb = :param1");
	            	query.setParameter("param1", serialnb);
					FAR = (String) query.uniqueResult();
			}// serial number exist 
			
			else if (!StringUtils.equalsIgnoreCase(MacAddress, "0")){
				query = session.createQuery("select distinct arID from ArSerialNumber where macAddress = :param1");
	        	query.setParameter("param1", MacAddress);
				 ARid = (String) query.uniqueResult();
				 query = session.createQuery("select distinct farID from FarSerialNumber where macAddress = :param1");
	         	query.setParameter("param1", MacAddress);
					FAR = (String) query.uniqueResult();
		
				} // Mac_Address exist
			else if (!StringUtils.equalsIgnoreCase(FAR, "0")){
				query = session.createQuery("select distinct ARID from FixedAssetRegistry where farID = :param1");

				query.setParameter("param1", FAR);
				 ARid = (String) query.uniqueResult();
				} // FARId exist 
			

			// TRANS TYPE NEW NODE ON EXISTED HARDWARE
			
			if(StringUtils.equalsIgnoreCase(trans_Type, "New Node on Existed Hardware"))
			
			{
					
				query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
				query.setParameter("param1", ARid);
				query.setParameter("param2", DniID);
				query.executeUpdate();
				
				if (ARid != null) {
					
					//Add to Table AR_SERIAL_NUMBER
					
					query = session.createQuery(" select serialId from ArSerialNumber where"
							+ " arID = :param1 and (serialNumber = :param2 OR macAddress = :param3 )");
					
					query.setParameter("param1", ARid);
					query.setParameter("param2", serialnb);
					query.setParameter("param3", MacAddress);
					String checkIfEx = (String) query.uniqueResult();
					String arserialID="";
					if(checkIfEx != null) {
						 arserialID= checkIfEx;
						}
					else {
						synchronized (this) {						
							arserialID = "ARSNUM_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
							}
						// arserialID="ARSNUM_" + year + "_" + appConfig.getSequenceNbr(31); 
					}
					
				
					 ArSerialNumber assetRegSerialNumber = new ArSerialNumber();
					 
		        		assetRegSerialNumber.setSerialId(arserialID);
						assetRegSerialNumber.setSerialNumber(toSerialNumber);
						assetRegSerialNumber.setModel(itemModel);
						assetRegSerialNumber.setPartNumber(itemPartNb);
						assetRegSerialNumber.setSite(toSite);
						assetRegSerialNumber.setArID(ARid);
						assetRegSerialNumber.setMacAddress(MacAddress);
						assetRegSerialNumber.setPosition(toSlot);
						session.saveOrUpdate(assetRegSerialNumber);
		        		
						
					// Add to table AR_Node
						
						query = session.createQuery("delete from ArNode t where t.arID = :param1");

						query.setParameter("param1", ARid);
						query.executeUpdate();
						
						String AR_NodeID;
					    query = session.createNativeQuery("select TO_NODE_ID,TO_NODE_NAME,TO_NODE_TYPE FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID=:param1");
							query.setParameter("param1", DniID);
							query.executeUpdate();
							List<Object[]> resultList = query.getResultList();
								
							for (Object[] result : resultList) {
								if (result[0] != null) {
							String NodeId= result[0].toString();
							
							
								    AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
									query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
									query.executeUpdate();
									session.createNativeQuery("commit").executeUpdate();
								
					 		
					 	ArNode assetRegNode = new ArNode();
					 	assetRegNode.setNodearId(AR_NodeID);
					 	assetRegNode.setNodeID(NodeId);
					 	assetRegNode.setNodeName(result[1].toString());
					 	assetRegNode.setNodeType(result[2].toString());
					 	assetRegNode.setArID(ARid);
					 	session.saveOrUpdate(assetRegNode);
							}
							}
				
				}
				
				
				
			
				
				query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
				query.setParameter("param1", FAR);
				query.setParameter("param2", DniID);
				query.executeUpdate();


				if (FAR != null) {

				// Add to FAR_SERIAL_NUMBER table

					query = session.createQuery(" select serialId from FarSerialNumber where farID = :param1 and ( inputSerialNb = :param2 OR macAddress = :param3 ) "
				);

					query.setParameter("param1", FAR);
					query.setParameter("param2", serialnb);
					query.setParameter("param3", MacAddress);
					String farSerialID = (String) query.uniqueResult();
				String farserialID="";
				if(farSerialID != null) {
				 farserialID= farSerialID;
				
				}
				else {
				 //farserialID="FARSNUM_" + year + "_" + appConfig.getSequenceNbr(32); 
				 synchronized (this) {						
					 farserialID = "FARSNUM_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_SERIALNO = FAR_SERIALNO + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						}
				}

				

				// Add in Fixed_Asset_Registry
				
				FarSerialNumber fixedAssetRegSerialNumber = new FarSerialNumber();
				
				fixedAssetRegSerialNumber.setSerialId(farserialID);
				fixedAssetRegSerialNumber.setInputSerialNb(toSerialNumber);
				fixedAssetRegSerialNumber.setInputModel(itemModel);
				fixedAssetRegSerialNumber.setMacAddress(MacAddress);
				fixedAssetRegSerialNumber.setInputpartNumber(itemPartNb);
				fixedAssetRegSerialNumber.setInputsite(toSite);
				fixedAssetRegSerialNumber.setFarID(FAR);
				fixedAssetRegSerialNumber.setInputPosition(toSlot);
				
				session.saveOrUpdate(fixedAssetRegSerialNumber);

				// Add to table FAR_NODE

				query = session.createQuery(" select nodefarId from FarNode where farID = :param1  "
				);

				query.setParameter("param1",FAR);
				String farNodeID = (String) query.uniqueResult();
				String FAR_NodeID = "";
				if(farNodeID != null) {
					FAR_NodeID = farNodeID;
					}
				else {
				 //FAR_NodeID = "FARNODE_" + year + "_" + appConfig.getSequenceNbr(27);
				 synchronized (this) {						
					 FAR_NodeID = "FARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_NODE = FAR_NODE + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
						}
				}


				FarNode farNode = new FarNode();
				
				farNode.setNodefarId(FAR_NodeID);
				farNode.setFarID(FAR);


				session.saveOrUpdate(farNode);
				}



				}
			
			//get arID
			
			
			
			if(StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Slot to Slot") || StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Node to Node") ||
			StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Site to Site"))
			{
				
			//Update AR_SERIAL_NUMBER
			query = session.createNativeQuery("update AR_SERIAL_NUMBER set  POSITION = :param3, SITE = :param4 where  "
			+ "SERIAL_NUMBER = :param6 and POSITION = :param7 and SITE = :param8");
			query.setParameter("param3", toSlot);
			query.setParameter("param4", toSite);
			query.setParameter("param6", serialnb);
			query.setParameter("param7", fromSlot);
			query.setParameter("param8", Site);
			query.executeUpdate();

			//Update In AR_SITE
			
			query = session.createNativeQuery("update AR_SITE SET SITE_ID = '"+toSiteID+"',SITE_NAME = '"+towareName+"',WARE_ID = '"+towareID+"'"
					+ " WHERE AR_ID = :param1 ");
			query.setParameter("param1", ARid);
			query.executeUpdate();
			

			//Update FAR_SERIAL_NUMBER
			query = session.createNativeQuery("update FAR_SERIAL_NUMBER set POSITION = :param3, SITE = :param4 where  "
			+ "SERIAL_NUMBER = :param6 and POSITION = :param7 and SITE = :param8");
			query.setParameter("param3", toSlot);
			query.setParameter("param4", toSite);
			query.setParameter("param6", serialnb);
			query.setParameter("param7", fromSlot);
			query.setParameter("param8", Site);
			query.executeUpdate();
				
			//Update In Far_Site

			query = session.createNativeQuery("update FAR_SITE SET SITE_ID = '"+toSiteID+"',SITE_NAME = '"+towareName+"',WARE_ID = '"+towareID+"'"
					+ " WHERE FAR_ID = :param1 ");
			query.setParameter("param1", FAR);
			query.executeUpdate();


			//Update AR_NODE
			
			query = session.createQuery("delete from ArNode t where t.arID = :param1");
	     	query.setParameter("param1", ARid);
	    	query.executeUpdate();
		
		String AR_NodeID;
	    query = session.createNativeQuery("select TO_NODE_ID,TO_NODE_NAME,TO_NODE_TYPE FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID=:param1");
			query.setParameter("param1", DniID);
			query.executeUpdate();
			List<Object[]> resultList = query.getResultList();

				 
			for (Object[] result : resultList) {
				if ( result[0] != null) {
					
			String NodeId= result[0].toString();
		
			
				    AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createNativeQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
				
	 		
	 	ArNode assetRegNode = new ArNode();
	 	assetRegNode.setNodearId(AR_NodeID);
	 	assetRegNode.setNodeID(NodeId);
	 	assetRegNode.setNodeName(result[1].toString());
	 	assetRegNode.setNodeType(result[2].toString());
	 	assetRegNode.setArID(ArCode);
	 	session.saveOrUpdate(assetRegNode);
			}
			}
			
			
		
		
		//Update FAR_NODE 
			
			query = session.createNativeQuery("delete FAR_NODE where FAR_ID = :param1");
			query.setParameter("param1", FAR);
			query.executeUpdate();
			  query = session.createNativeQuery("select TO_NODE_ID,TO_NODE_NAME,TO_NODE_TYPE FROM DISCOVERY_NEW_ITEM_NODE WHERE DNI_ID=:param1");
				query.setParameter("param1", DniID);
				query.executeUpdate();
				List<Object[]> resultNList = query.getResultList();


				String FAR_NodeID;
					
		
		for (Object[] resultN : resultNList) {
			if (resultN[0] != null) {
				
		
		FAR_NodeID = "FARNODE_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT FAR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
		query = session.createNativeQuery("UPDATE SEQ_TABLE SET FAR_NODE = FAR_NODE + 1 ");
		query.executeUpdate();
		session.createNativeQuery("commit").executeUpdate();
		

	FarNode FixedAssetRegNode = new FarNode();

	FixedAssetRegNode.setNodefarId(FAR_NodeID);
	FixedAssetRegNode.setNodeID(resultN[0].toString());
	FixedAssetRegNode.setNodeName(resultN[1].toString());
	FixedAssetRegNode.setNodeType(resultN[2].toString());
	FixedAssetRegNode.setFarID(FAR);

	session.saveOrUpdate(FixedAssetRegNode);

	}
		}
			}

			
			
			
			// TRANS TYPE DISAPPEAR FOR TRANSFER

			if (StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Transfer"))

			{
			
				query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
				query.setParameter("param1", FAR);
				query.setParameter("param2", DniID);
				query.executeUpdate();
			          
				query = session.createNativeQuery("update ASSET_REGISTRY set AR_STATUS = 'Disappear' where AR_ID =:param1");
				query.setParameter("param1", FAR);
				query.executeUpdate();


				query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
				query.setParameter("param1", FAR);
				query.setParameter("param2", DniID);
				query.executeUpdate();
			

			    query = session.createNativeQuery("update FIXED_ASSET_REGISTRY set FAR_STATUS ='Disappear' where FAR_ID =:param1");
			    query.setParameter("param1", FAR);
			    query.executeUpdate();

			}

			
			
			
			// TRANS TYPE DISAPPEAR FOR MAINTENANCE 

			if (StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Maintenance"))

			{

			
				query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
				query.setParameter("param1", ARid);
				query.setParameter("param2", DniID);
				query.executeUpdate();
								             
				query = session.createNativeQuery("update ASSET_REGISTRY set AR_STATUS = 'Maintenance' where AR_ID =:param1");
				query.setParameter("param1", ARid);
				query.executeUpdate();

				query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
				query.setParameter("param1", FAR);
				query.setParameter("param2", DniID);
				query.executeUpdate();
			
				query = session.createNativeQuery("update FIXED_ASSET_REGISTRY set FAR_STATUS ='Maintenance' where FAR_ID =:param1");
				query.setParameter("param1", FAR);
				query.executeUpdate();

			} 

			
			
			
			// TRANS TYPE MAINTENANCE

			if (StringUtils.equalsIgnoreCase(trans_Type, "Maintenance"))

			{
			
				query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
				query.setParameter("param1", ARid);
				query.setParameter("param2", DniID);
				query.executeUpdate();
			
								             
				query = session.createNativeQuery("update ASSET_REGISTRY set AR_STATUS = 'Running' where AR_ID =:param1");
				query.setParameter("param1", ARid);
				query.executeUpdate();

			
				query = session.createNativeQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
				query.setParameter("param1", FAR);
				query.setParameter("param2", DniID);
				query.executeUpdate();
			


				query = session.createNativeQuery("update FIXED_ASSET_REGISTRY set FAR_STATUS ='Running' where FAR_ID =:param1");
				query.setParameter("param1", FAR);
				query.executeUpdate();


			} 

		
			
	//TRANS TYPE RETIREMENT										

		if (StringUtils.equalsIgnoreCase(trans_Type, "Retirement"))
			
		{
			
		
		
		//DELETE FROM AR_SITE BOQ
		query = session.createNativeQuery("delete AR_SITE where AR_ID = :param1");
		query.setParameter("param1", ARid);
		query.executeUpdate();

		//DELETE FROM AR_NODE BOQ

		query = session.createNativeQuery("delete AR_NODE where AR_ID = :param1");
		query.setParameter("param1", ARid);
		query.executeUpdate();

		//DELETE FROM AR_SERIAL_NUMBER
		query = session.createNativeQuery("delete AR_SERIAL_NUMBER where AR_ID = :param1");
		query.setParameter("param1", ARid);
		query.executeUpdate();

		//DELETE FROM AR_MODEL_PARTNUMBER

		query = session.createNativeQuery("delete AR_MODEL_PARTNUMBER where AR_ID = :param1");
		query.setParameter("param1", ARid);
		query.executeUpdate();

		//DELETE FROM ASSET_REGISTRY

		query = session.createNativeQuery("delete ASSET_REGISTRY where AR_ID = :param1");
		query.setParameter("param1", ARid);
		query.executeUpdate();

		//DELETE FROM FAR_SITE BOQ

		query = session.createNativeQuery("delete FAR_SITE where FAR_ID = :param1");
		query .setParameter("param1", FAR);
		query .executeUpdate();

		//DELETE FROM FAR_NODE BOQ

		query = session.createNativeQuery("delete FAR_NODE where FAR_ID = :param1");
		query.setParameter("param1", FAR);
		query.executeUpdate();

		//DELETE FROM FAR_SERIAL_NUMBER

		query = session.createNativeQuery("delete FAR_SERIAL_NUMBER where FAR_ID = :param1");
		query.setParameter("param1", FAR);
		query.executeUpdate();

		//DELETE FROM FAR_MODEL_PARTNUMBER

		query = session.createNativeQuery("delete FAR_MODEL_PARTNUMBER where FAR_ID = :param1");
		query.setParameter("param1", FAR);
		query.executeUpdate();

		//DELETE FROM FIXED_ASSET_REGISTRY

		query = session.createNativeQuery("delete FIXED_ASSET_REGISTRY where FAR_ID = :param1");
		query.setParameter("param1", FAR);
		query.executeUpdate();

		} // END TRANSACTION TYPE RETIREMENT 
				
		
	}
	
		
	}



	//APPROVED BY project manager or asset unit
	public void AssetNewNode(String trans_Type, String getApproval, String dnStatus, String AssetRegID, String ArCode, String PurchaseOrId, String itmcode, String itmname, String WorkOrder, String DniID, String toSiteID, String supplierID, String supplierName, String towareID, String towareName, String serialnb, float dnRate, String itemModel, String itemPartNb, String toSite, String toSerialNumber, String toSlot, String Site, String fromSlot, String SiteID, Session session, Transaction tx) {
		
	
	if(StringUtils.equalsIgnoreCase(dnStatus, "Approved") && StringUtils.equalsIgnoreCase(trans_Type, "New Node on New Hardware")) {
		
		System.out.println("the serial number is "+toSerialNumber);
		
		if(StringUtils.equalsIgnoreCase(toSerialNumber, "0")) {
			System.out.println("update when serial is 0 for source serial");
			Query sn1=session.createNativeQuery("UPDATE WORK_ORDER_DESTINATION_SERIAL SET RECONCILED ='1' WHERE EXISTS(SELECT * FROM WORK_ORDER_DESTINATION_SERIAL WHERE ITEM_MODEL = :param1 and ITEM_PART_NUMBER= :param2 and WO_ID = :param3)");
			sn1.setParameter("param1",itemModel);
			sn1.setParameter("param2", itemPartNb);
			sn1.setParameter("param3","WO_2021_62");
			sn1.executeUpdate();
			
			System.out.println("update when serial is 0 for destination serial");
			Query sn2=session.createNativeQuery("UPDATE WORK_ORDER_SOURCE_SERIAL SET RECONCILED ='1' WHERE EXISTS(SELECT * FROM WORK_ORDER_SOURCE_SERIAL WHERE ITEM_MODEL = :param1 and ITEM_PART_NUMBER= :param2 and WO_ID = :param3)");
			sn2.setParameter("param1",itemModel);
			sn2.setParameter("param2", itemPartNb);
			sn2.setParameter("param3","WO_2021_62");
			sn2.executeUpdate();

		} else if (!StringUtils.equalsIgnoreCase(toSerialNumber, "0")) {
			System.out.println("update when we have a serial");
			
			Query sn1=session.createNativeQuery("SELECT ID FROM WORK_ORDER_SOURCE_SERIAL WHERE SERIAL_NO = :param1");
			sn1.setParameter("param1",toSerialNumber);
			 String sn1Result = (String) sn1.uniqueResult();
				System.out.println("sn1Result"+sn1Result);

			 if (sn1Result !=null) {
				System.out.println("SERIAL IS IN WORK ORDER SOURCE TABLE");
	     		query=session.createNativeQuery("update WORK_ORDER_SOURCE_SERIAL set RECONCILED ='1' where SERIAL_NO = :param1");
	     		query.setParameter("param1",toSerialNumber);
	     		query.executeUpdate();
				System.out.println("SERIAL IS RECONCILED IN WORK ORDER");

			 } else {
				 System.out.println("SERIAL IS NOTTTTTTT IN WORK ORDER SOURCE TABLE");
				 query=session.createNativeQuery("update WORK_ORDER_SOURCE_SERIAL set RECONCILED ='1', SERIAL_NO = :param1 WHERE EXISTS(SELECT * FROM WORK_ORDER_SOURCE_SERIAL WHERE ITEM_CODE = :param2 and ITEM_MODEL = :param3 and ITEM_PART_NUMBER= :param4 and WO_ID = :param5 and SERIAL_NO = '0' and ROWNUM = 1)"); 
				 query.setParameter("param1",toSerialNumber);
				 query.setParameter("param2",itmcode);
				 query.setParameter("param3",itemModel);
				 query.setParameter("param4", itemPartNb);
				 query.setParameter("param5",WorkOrder);

				 query.executeUpdate();
		     	 
					System.out.println("SERIAL IS RECONCILED IN WORK ORDER sourceeeee");
			 }
			 
			  query=session.createNativeQuery("SELECT ID FROM WORK_ORDER_DESTINATION_SERIAL WHERE SERIAL_NO = :param1");
			  query.setParameter("param1",toSerialNumber);
				 String sn2Result = (String) query.setMaxResults(1).uniqueResult();
					System.out.println("sn1Result"+sn1Result);

				 if (sn2Result !=null) {
					System.out.println("SERIAL IS IN WORK ORDER DESTINATION TABLE");
					query=session.createNativeQuery("update WORK_ORDER_DESTINATION_SERIAL set RECONCILED ='1' where SERIAL_NO = :param1");
					query.setParameter("param1",toSerialNumber);
					query.executeUpdate();
					System.out.println("SERIAL IS RECONCILED IN WORK ORDER");

				 } else {
					 System.out.println("SERIAL IS NOTTTTTTT IN WORK ORDER DESTINATION TABLE");
					 query=session.createNativeQuery("update WORK_ORDER_DESTINATION_SERIAL set RECONCILED ='1', SERIAL_NO = :param1 WHERE EXISTS(SELECT * FROM WORK_ORDER_SOURCE_SERIAL WHERE ITEM_CODE = :param2 and ITEM_MODEL = :param3 and ITEM_PART_NUMBER= :param4 and WO_ID = :param5 and SERIAL_NO = '0' and ROWNUM = 1)"); 
					 query.setParameter("param1",toSerialNumber);
					 query.setParameter("param2",itmcode);
					 query.setParameter("param3",itemModel);
					 query.setParameter("param4", itemPartNb);
					 query.setParameter("param5",WorkOrder);

					 query.setMaxResults(1).executeUpdate();
			     		
						System.out.println("SERIAL IS RECONCILED IN WORK ORDER");
				 }

		}
			       		
	        	}
	
	
	}
	
	public <T>double findAllByMultiClzMultiQryPrms(Session session, String queryString, List<Object> params) {
		  
		double value=0;
	query = session.createQuery(queryString);
	int i;
	for (Object param : params) {
		i = params.indexOf(param) + 1;
		query.setParameter("param_" + i, param.toString());
	}

	if(query.uniqueResult() != null)
	{
		value = (double)query.uniqueResult();
	}
	else 
		System.out.println("There is no any record related to the query: " +query+ "in the method: findAllByMultiClzMultiQryPrms" );

	return value;
	}

		
		
		
			
		
		public void transactionUpdate(String transId,String transType, String approvalType, Session session, Transaction tx) {

			
	
			  String[] transIdArray = transId.split(",");
		        
		        for (String id : transIdArray) {
		        	
		        	query = session.createNativeQuery("UPDATE Network_TRANSACTION set "
		        			+ "ALM_TRANS_TYPE =:param1 , ALM_APPROVAL_STATUS=:param2 "
		        			+ "where Trans_Id=:param3 ");
		        	query.setParameter("param1", transType);
		        	query.setParameter("param2", approvalType);
		        	query.setParameter("param3", id);
					query.executeUpdate();
					session.createNativeQuery("commit").executeUpdate();
		        }
			
		
		}
		public  <T> void persistSingleColumn(Session session ,String queryString, List<Object> params) {
			
			int i;
			//Transaction tx =session.beginTransaction();
			query = session.createQuery(queryString);
			for (Object param : params) {
				i = params.indexOf(param) + 1;
				query.setParameter("param_" + i, param.toString());
			}
			query.executeUpdate();
			//tx.commit();
		 }
}

