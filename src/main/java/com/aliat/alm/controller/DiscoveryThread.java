package com.aliat.alm.controller;


import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.models.ArNode;
import com.aliat.alm.models.ArPartNumber;
import com.aliat.alm.models.ArSerialNumber;
import com.aliat.alm.models.ArSite;
import com.aliat.alm.models.AssetRegistry;
import com.aliat.alm.models.FarNode;
import com.aliat.alm.models.FarPartNumber;
import com.aliat.alm.models.FarSerialNumber;
import com.aliat.alm.models.FarSite;
import com.aliat.alm.models.FixedAssetRegistry;
import org.hibernate.Query;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.lang3.StringUtils;

import com.aliat.alm.models.PurchaseOrder;
import com.aliat.alm.models.PurchaseOrderItem;

import java.sql.Timestamp;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DiscoveryThread extends Thread{
	


	
	ALMSessions almsessions = new ALMSessions();
	
    Calendar calendar = new GregorianCalendar();
	 int year = calendar.get(Calendar.YEAR);
	 int i;
	 private static Query query = null;
	
	private static final Logger logger = LoggerFactory.getLogger(DiscoveryThread.class);


	  String trans_Type="";
	  String getApproval=""; 
	  String dnStat="";
	  
	    
	 String PurchaseOrId="";
	 String itmcode="";
	 String itmname="";
	 String WorkOrder="";
     String DniID;
     String toSiteID = "";
     String supplierID = "";
     String supplierName = "";
     String towareID = "";
     String towareName = "";
     String serialnb =""; 
     String ArCode="";
     String AssetRegID="";
  //   ItemParameters  itemParameters;
     
	 float dnRate = 0;   
	 String itemModel="";
	 String itemPartNb ="";
	 String toSite = "";
	 String toSerialNumber = "";
	 String toNodeId = "";
	 String toNodeName = "";
	 String toSlot = "";
	 String nodeID = "";
	 String nodeName = "";
	 String Site = "";
	 String fromSlot = "";
	 String SiteID = "";
	 
	 Query insertAssetRegistry;
	 Query insertAR_Site;
	 Query insertAR_Node;
	 Query insertAR_SerialNb;
	 Query insertAR_MODELPARTNB;
	 Query updateWO_Reconciled;


	 public DiscoveryThread(String trans_Type, String getApproval, String dnStat, String AssetRegID, String ArCode, String PurchaseOrId,String itmcode,String itmname, String WorkOrder, String DniID,String toSiteID,String supplierID,String supplierName, String towareID,String towareName,String serialnb, float dnRate, String itemModel, String itemPartNb,String toSite, String toSerialNumber, String toNodeId, String toNodeName, String toSlot, String nodeID, String nodeName,String Site,String fromSlot,String SiteID) {
		 this.PurchaseOrId = PurchaseOrId;
         this.itmcode=itmcode;
         this.itmname=itmname;
         this.WorkOrder=WorkOrder;
         this.DniID=DniID;
         this.toSiteID=toSiteID;
         this.supplierID=supplierID;
         this.supplierName=supplierName;
         this.towareID=towareID;
         this.towareName=towareName;
         this.serialnb=serialnb;
         this.ArCode=ArCode;
         this.AssetRegID=AssetRegID;
         this.trans_Type=trans_Type;
         this.getApproval=getApproval;
         this.dnStat=dnStat;
         
         this.dnRate = dnRate;
         this.itemModel = itemModel;
         this.itemPartNb = itemPartNb;
         this.toSite = toSite;
         this.toSerialNumber = toSerialNumber;
         this.toNodeId = toNodeId;
         this.toNodeName = toNodeName;
         this.toSlot = toSlot;
         this.nodeID = nodeID;
         this.nodeName = nodeName;
         this.Site = Site;
         this.fromSlot = fromSlot;
         this.SiteID = SiteID;
         
   	 }      
	 
         @SuppressWarnings({ "rawtypes", "unchecked" })
		public void run(){ 


     		Session session = almsessions.getSession();
     		
     		if (session != null && session.isOpen()) {
     			Transaction tx = session.beginTransaction();
     			
     			try {

            	System.out.println("START ITERATION THREAD");
     			// GET THE PURCHASE-ORDER  ITEM CODE AND ITEM ID      
     				
        		Query getpoItemID = session.createSQLQuery("select PO_ITEM_ID from PURCHASE_ORDER_ITEM where ITEM_CODE = '"+itmcode+"' and PO_ID = '"+PurchaseOrId+"'");
				getpoItemID.executeUpdate();
				String poItemID = (String) getpoItemID.uniqueResult();
        		   	
        	if(StringUtils.equalsIgnoreCase(trans_Type, "New Node on New Hardware") || StringUtils.equalsIgnoreCase(trans_Type, "New Hardware on Existed Node") || StringUtils.equalsIgnoreCase(trans_Type, "Replacement") || StringUtils.equalsIgnoreCase(trans_Type, "New Hardware on New Node"))
			{
				
        	if ((StringUtils.equalsIgnoreCase(getApproval, "Project Manager") && StringUtils.equalsIgnoreCase(dnStat, "Approved")) || ((StringUtils.equalsIgnoreCase(getApproval, "Asset Unit") && StringUtils.equalsIgnoreCase(dnStat, "Approved")))) {  
						
        		
				if (AssetRegID == null) {
					String AR_Site_ID;
					synchronized (this) {						
						ArCode = "AR_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET ASSET_REGISTRY = ASSET_REGISTRY + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						AR_Site_ID = "ARSITE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_SITE = AR_SITE + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}
					//ArCode = "AR_" + year + "_" + appConfig.getSequenceNbr(9);
		        	//String AR_Site_ID = "ARSITE_" + year + "_"  + appConfig.getSequenceNbr(30);
				
		        	
					// ADD DiscoveryNewItem TO ASSET REGISTRY TABLE

		        	AssetRegistry assetregistry = new AssetRegistry();
					assetregistry.setArID(ArCode);
					assetregistry.setAritemCode(itmcode);
					assetregistry.setArcreatedDate(new Timestamp(System.currentTimeMillis()));
					assetregistry.setArlastModifiedDate(new Timestamp(System.currentTimeMillis()));
					assetregistry.setAritemName(itmname);
					assetregistry.setArdniID(DniID);
					assetregistry.setPoID(PurchaseOrId);
					assetregistry.setSupplierID(supplierID);
					assetregistry.setSupplierName(supplierName);
					assetregistry.setItemModel(itemModel);
					assetregistry.setItemPartNumber(itemPartNb);
					assetregistry.setItemSN(toSerialNumber);
					assetregistry.setNodeID(toNodeId);
					assetregistry.setNodeName(toNodeName);
					assetregistry.setArStatus("Running");
					assetregistry.setPoItemId(poItemID);
					assetregistry.setArsiteId(AR_Site_ID);
					session.saveOrUpdate(assetregistry);
					
					
					query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1, AR_SITE_ID = :param2 where DNI_ID = :param3");
					query.setParameter("param1", ArCode);
					query.setParameter("param2", AR_Site_ID);
					query.setParameter("param3", DniID);
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
					
		        	if(!StringUtils.equalsIgnoreCase(toNodeId, "") ||  !StringUtils.equalsIgnoreCase(toNodeName, "")) {
		        	
		        		String AR_NodeID;
		        		synchronized (this) {						
		        			AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
		    				query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
		    				query.executeUpdate();
		    				session.createSQLQuery("commit").executeUpdate();
		    				}
		        	
		        	ArNode assetRegNode = new ArNode();
		        	assetRegNode.setNodearId(AR_NodeID);
		        	assetRegNode.setNodeID(toNodeId);
		        	assetRegNode.setNodeName(toNodeName);
		        	assetRegNode.setArID(ArCode);
		        	session.saveOrUpdate(assetRegNode);
		        	}
		        	
		        	
					// Add to AR_SERIAL_NUMBER table
		        	
					if(!StringUtils.equalsIgnoreCase(serialnb, "0") ) {
					
						String AR_SerialNum_ID;
						synchronized (this) {						
							AR_SerialNum_ID = "ARSNUM_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
	        		ArSerialNumber assetRegSerialNumber = new ArSerialNumber();
					assetRegSerialNumber.setSerialId(AR_SerialNum_ID);
					assetRegSerialNumber.setSerialNumber(toSerialNumber);
					assetRegSerialNumber.setModel(itemModel);
					assetRegSerialNumber.setPartNumber(itemPartNb);
					assetRegSerialNumber.setSnodeID(toNodeId);
					assetRegSerialNumber.setSnodeName(toNodeName);
					assetRegSerialNumber.setSite(toSite);
					assetRegSerialNumber.setPosition(toSlot);
					assetRegSerialNumber.setArID(ArCode);
					session.saveOrUpdate(assetRegSerialNumber);
					
					}
					
					// Add to AR_MODEL_PARTNUMBER table
					
					String itemmodel = itemModel;
					
					if(!StringUtils.equalsIgnoreCase(itemmodel, ""))
					{
						String AR_model_partNum;
						synchronized (this) {						
							AR_model_partNum = "ARMP_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_MODEL_PARTNO = AR_MODEL_PARTNO + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
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
				
				query = session.createSQLQuery("update ASSET_REGISTRY SET ITEM_CODE = '"+itmcode+"',LAST_MODIFIED_DATE = sysdate,ITEM_NAME = '"+itmname+"',PO_ID = '"+PurchaseOrId+"',"
						+ "SUPPLIER_ID = '"+supplierID+"', SUPPLIER_NAME = '"+supplierName+"',ITEM_MODEL = '"+itemModel+"',ITEM_PART_NUMBER = '"+itemPartNb+"',ITEM_SN = '"+toSerialNumber+"',NODE_ID = '"+toNodeId+"',NODE_NAME = '"+toNodeName+"',PO_ITEM_ID = '"+poItemID+"' WHERE AR_ID = :param1 ");
				query.setParameter("param1", ArCode);
				query.executeUpdate();
				
				query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
				query.setParameter("param1", ArCode);
				query.setParameter("param2", DniID);
				query.executeUpdate();
				
				
				
				// ADD TO AR_MODEL_PARTNUMBER TABLE
				
				query = session.createQuery("select t.itmId from ArPartNumber t where t.arID = :param1 ");
				query.setParameter("param1", ArCode);
				String ARModelPartNum = (String) query.uniqueResult();
				
				if(ARModelPartNum == null)
				{
					// ARModelPartNum = "itmId_" + year + "_" +  appConfig.getSequenceNbr(26);
					 synchronized (this) {						
						 ARModelPartNum = "ARMP_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_MODEL_PARTNO = AR_MODEL_PARTNO + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
				}
				
				query = session.createSQLQuery("update AR_MODEL_PARTNUMBER SET ITM_ID = '"+ARModelPartNum+"',ITEM_PART_NUMBER = '"+itemPartNb+"',QUANTITY = 1,ITEM_MODEL = '"+itemModel+"',ITEM_CODE = '"+itmcode+"',"
						+ "PRIMARY = '1' WHERE AR_ID = :param1 ");
				query.setParameter("param1", ArCode);
				query.executeUpdate();
				
				
				
				// ADD TO AR_SITE TABLE
				
				query = session.createQuery("select t.arsiteId from ArSite t where t.arID = :param1 ");
				query.setParameter("param1", ArCode);
				
				String AR_SiteID = (String) query.uniqueResult();
				
				if(AR_SiteID == null)
				{
					//AR_SiteID = "ARSITE_" + year + "_"  + appConfig.getSequenceNbr(30);
					synchronized (this) {						
						AR_SiteID = "ARSITE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_SITE = AR_SITE + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}
				}
				
				query = session.createSQLQuery("update AR_SITE SET AR_SITE_ID = '"+AR_SiteID+"',SITE_ID = '"+toSiteID+"',SITE_NAME = '"+towareName+"',WARE_ID = '"+towareID+"'"
						+ " WHERE AR_ID = :param1 ");
				query.setParameter("param1", ArCode);
				query.executeUpdate();
				
				
				
				
				/// ADD TO AR_NODE TABLE                                  
				
				query = session.createQuery("select t.nodearId from ArNode t where t.arID = :param1 and t.nodeID = :param2");

				query.setParameter("param1", ArCode);
				query.setParameter("param2", toNodeId);
				String AR_NodeID = (String) query.uniqueResult();
				
				if(AR_NodeID == null)
				{
					//AR_NodeID = "ARNODE_" + year + "_" + appConfig.getSequenceNbr(28);
					synchronized (this) {						
						AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
						}
				}
				
				query = session.createSQLQuery("update AR_NODE SET NODEAR_ID = '"+AR_NodeID+"',NODE_ID = '"+toNodeId+"',NODE_NAME = '"+toNodeName+"'"
						+ " WHERE AR_ID = :param1 ");
				query.setParameter("param1", ArCode);
				query.executeUpdate();
				
				
				
				// Add to AR_SERIAL_NUMBER table
				
				query = session.createQuery("select t.serialId from ArSerialNumber t where t.arID = :param1 and snodeID = :param2");
				query.setParameter("param1", ArCode);
				query.setParameter("param2", toNodeId);
				String AR_SerialID = (String) query.uniqueResult();
				
				
				if(AR_SerialID == null) {
					 //AR_SerialID = "ARSNUM_" + year + "_" + appConfig.getSequenceNbr(31);
					 synchronized (this) {						
							AR_SerialID = "ARSNUM_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
				}
				
				query = session.createSQLQuery("update AR_SERIAL_NUMBER SET SERIAL_ID = '"+AR_SerialID+"',SERIAL_NUMBER = '"+toSerialNumber+"',MODEL = '"+itemModel+"',PART_NUMBER = '"+itemPartNb+"',NODE_ID = '"+toNodeId+"',NODE_NAME = '"+toNodeName+"',SITE = '"+toSite+"',POSITION = '"+toSlot+"' "
						+ " WHERE AR_ID = :param1 ");
				query.setParameter("param1", ArCode);
				query.executeUpdate();
				
			}
			}
			
			
			if (StringUtils.equalsIgnoreCase(getApproval, "Finance") &&  StringUtils.equalsIgnoreCase(dnStat, "Approved")) { // && substractAction == 1

				
				// check, if this itemCode related to poID, exist in CIP table (get the qty and cipID)
				query = session.createQuery(
						"Select t.TOTALQTY as totalqty, t.cipID as cipID from CapitalInProgress t where t.PoId =:param1 and t.cipitemCode =:param2");

				query.setString("param1", PurchaseOrId);
				query.setString("param2", itmcode);

				List result = query.list();


				String FarCode;

				
				// if exist, get only one row, with CIPqty field and cipID
				if (result != null) {
					

					for (Object obj : result) {
						System.out.println("CIP LOOP START");
						Object[] fields = (Object[]) obj;
						String result1 = fields[0].toString();
						float updatedQty = Float.parseFloat(result1) - 1; // substract 1 from the TotalQty

						
						System.out.println("APPROVAL TYPE IS: " +getApproval);
						System.out.println("CIP updatedQty for item: " +itmcode+" is: "+updatedQty);
						
						if (updatedQty == 0) {

							// DELETE FROM CIP TABLE
							
							query = session.createQuery("delete from CapitalInProgress c where c.cipID =:param1");
							query.setParameter("param1", fields[1]);
							query.executeUpdate();
						}

						else {

							 Query q5 = session.createQuery("update CapitalInProgress c set c.TOTALQTY =:param1 where c.cipID =:param2"); 
							 q5.setFloat("param1", updatedQty); 
							 q5.setParameter("param2", fields[1]); 
							 q5.executeUpdate();
							 query = session.createSQLQuery("commit");
							 query.executeUpdate(); 

						}

						System.out.println("FINANCE APPROVE START");

						//FarCode = "FAR_" + year + "_" + appConfig.getSequenceNbr(10);
						synchronized (this) {						
							FarCode = "FAR_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FIXED_ASSET_REGISTRY FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET FIXED_ASSET_REGISTRY = FIXED_ASSET_REGISTRY + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
						
						float initialCost = dnRate;
						
						query = session.createSQLQuery("select USEFULL_LIFE_MONTHS from ITEM where ITEM_CODE = '"+itmcode+"'");
						query.executeUpdate();
						String item_Usefull_LifeMonths = (String) query.uniqueResult();
						float useful_life_month = 0;

						if (!StringUtils.equalsIgnoreCase(item_Usefull_LifeMonths, "")) { 
							
							useful_life_month = Float.parseFloat(item_Usefull_LifeMonths);
						}

						

						String FAR_Site_ID;
						synchronized (this) {						
							FAR_Site_ID = "FARSITE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FAR_SITE FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET FAR_SITE = FAR_SITE + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
						
						
						
						// ADD DiscoveryNewItem TO ASSET REGISTRY TABLE

						FixedAssetRegistry FixedAssetReg = new FixedAssetRegistry();
						
						FixedAssetReg.setFarID(FarCode);     
						FixedAssetReg.setFaritemCode(itmcode);
						FixedAssetReg.setFarcreatedDate(new Timestamp(System.currentTimeMillis()));
						FixedAssetReg.setFarlastModifiedDate(new Timestamp(System.currentTimeMillis()));
						FixedAssetReg.setFaritemName(itmname);
						FixedAssetReg.setIniCost(initialCost);
						FixedAssetReg.setUsefulLifeMon(useful_life_month);
						FixedAssetReg.setDniID(DniID);
						FixedAssetReg.setSupplierID(supplierID);
						FixedAssetReg.setSupplierName(supplierName);
						FixedAssetReg.setPoId(PurchaseOrId);
						FixedAssetReg.setItemModel(itemModel);
						FixedAssetReg.setItemPartNb(itemPartNb);
						FixedAssetReg.setItemSN(toSerialNumber);
						FixedAssetReg.setNodeID(toNodeId);
						FixedAssetReg.setNodeName(toNodeName);
						FixedAssetReg.setFarStatus("Running");
						FixedAssetReg.setPoItemId(poItemID);
						FixedAssetReg.setFarsiteId(FAR_Site_ID);
						session.saveOrUpdate(FixedAssetReg);
						
						
						
						query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1,  FR_SITE_ID = :param2  where DNI_ID = :param3");
						query.setParameter("param1", FarCode);
						query.setParameter("param2", FAR_Site_ID);
						query.setParameter("param3", DniID);
						query.executeUpdate();
						
						
						
						// ADD TO FAR NODE TABLE

						query = session.createQuery("select t.nodefarId from FarNode t where t.farID = :param1");

						query.setParameter("param1", FarCode);
						String FAR_NodeID = (String) query.uniqueResult();

						if(FAR_NodeID == null)
						{
						//FAR_NodeID = "FARNODE_" + year + "_" + appConfig.getSequenceNbr(27);
						synchronized (this) {						
							FAR_NodeID = "FARNODE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FAR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET FAR_NODE = FAR_NODE + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
						
						FarNode FixedAssetRegNode = new FarNode();
						
						FixedAssetRegNode.setNodefarId(FAR_NodeID);
						FixedAssetRegNode.setNodeID(toNodeId);
						FixedAssetRegNode.setNodeName(toNodeName);
						FixedAssetRegNode.setFarID(FarCode);

						session.saveOrUpdate(FixedAssetRegNode);
						
						}
						
						
						// ADD TO FAR MODEL_PART_NB TABLE
						
						if(!StringUtils.equalsIgnoreCase(itemModel, "") || !StringUtils.equalsIgnoreCase(itemPartNb, ""))
						{
						//String FAR_model_partNum = "ITM_" + year + "_" +  appConfig.getSequenceNbr(25);
						String FAR_model_partNum;
						synchronized (this) {						
							FAR_model_partNum = "FARMP_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FAR_MODEL_PARTNO FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET FAR_MODEL_PARTNO = FAR_MODEL_PARTNO + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
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

						if(!StringUtils.equalsIgnoreCase(serialnb, "0") ) {
						String FAR_SerialNum_ID;
						
						synchronized (this) {						
							FAR_SerialNum_ID = "FARSNUM_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FAR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
							query = session.createSQLQuery("UPDATE SEQ_TABLE SET FAR_SERIALNO = FAR_SERIALNO + 1 ");
							query.executeUpdate();
							session.createSQLQuery("commit").executeUpdate();
							}
						FarSerialNumber fixedAssetRegSerialNumber = new FarSerialNumber();
						
						fixedAssetRegSerialNumber.setSerialId(FAR_SerialNum_ID);
						fixedAssetRegSerialNumber.setInputSerialNb(toSerialNumber);
						fixedAssetRegSerialNumber.setInputModel(itemModel);
						fixedAssetRegSerialNumber.setInputpartNumber(itemPartNb);
						fixedAssetRegSerialNumber.setInputNodeID(toNodeId);
						fixedAssetRegSerialNumber.setInputNodeName(toNodeName);
						fixedAssetRegSerialNumber.setInputsite(toSite);
						fixedAssetRegSerialNumber.setInputPosition(toSlot);
						fixedAssetRegSerialNumber.setFarID(FarCode);
						session.saveOrUpdate(fixedAssetRegSerialNumber);

						}

						
						
						//Add to FAR_SITE Table       
						
						if(!StringUtils.equalsIgnoreCase(toSite, null) ) {
							
						FarSite farSite = new FarSite();
						farSite.setFarsiteId(FAR_Site_ID);
						farSite.setSiteID(toSiteID);
						farSite.setSiteName(towareName);
						farSite.setWareID(towareID);
						farSite.setFarID(FarCode);
						session.saveOrUpdate(farSite);
						}
						
						
						

						System.out.println("CIP LOOP END");
						
						} // END for(Object obj : result) Loop
					
						} // if (result != null)


				String poStat = null;
				List<Object> params= new ArrayList<Object>();
				String getPoStatus = " select ordStatus from PurchaseOrder where ID = :param_1";
				params.add(PurchaseOrId);
				query = session.createQuery(getPoStatus);
				for (Object param : params) {
					i = params.indexOf(param) + 1;
					query.setString("param_" + i, param.toString());
				}
				if (query.uniqueResult() != null) {poStat = query.uniqueResult().toString();}
				params.clear();
				//String poStat = appConfig.findAllByQueryConditionByParam(PurchaseOrder.class,getPoStatus, params);
				
				if (StringUtils.equalsIgnoreCase(poStat, "approved") ) 
				{
					String allGoodsRec = null;
					String getAllGRs = " select count(*) from GoodsReceived where grPOid = :param_1";
					params.add(PurchaseOrId);
					query = session.createQuery(getAllGRs);
					for (Object param : params) {
						i = params.indexOf(param) + 1;
						query.setString("param_" + i, param.toString());
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
						query.setString("param_" + i, param.toString());
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
						//List<Object[]> purchOrdItem = (List<Object[]>) appConfig.findAllByMultiClzQryPrms(itemPO, "param1",PurchaseOrId );
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
							double resQTYGR =findAllByMultiClzMultiQryPrms(session,itemGR,params);
							
							String resStatus="0";
							params.clear();
								if (resQTYGR < qty )
							{
									String itemInFAR = null;
									String getItemsInFAR = " select count(*) from FixedAssetRegistry where faritemCode = :param_1 and PoId = :param_2 ";
									params.add(itemCode);
									params.add(PurchaseOrId);
									query = session.createQuery(getItemsInFAR);
									for (Object param : params) {
										i = params.indexOf(param) + 1;
										query.setString("param_" + i, param.toString());
									}
									if (query.uniqueResult() != null) {itemInFAR = query.uniqueResult().toString();}
									params.clear();
									//String itemInFAR = appConfig.findAllByQueryConditionByParam(FixedAssetRegistry.class,getItemsInFAR, params);
								
									double ItemInFAR = 0;
									if(itemInFAR !=null) { ItemInFAR = Double.parseDouble(itemInFAR);}
									
								if((ItemInFAR + resQTYGR) >= qty)
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



				System.out.println("FINANCE APPROVE END");
				
				
				
			} // END FINANCE APPROVE
			
		} //END TRANSACTION TYPE CONDITION 
		
        	
        	
        	
        	
        	
        	
			else {

                // OTHER TRANSACTION TYPE
				
				if(StringUtils.equalsIgnoreCase(trans_Type, "New Node on Existed Hardware") || StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Slot to Slot") || StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Node to Node") 
						|| StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Site to Site") || StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Maintenance")
						|| StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Transfer") || StringUtils.equalsIgnoreCase(trans_Type, "Maintenance") || StringUtils.equalsIgnoreCase(trans_Type, "Retirement"))
					
								{	
					
					
						// Approved by Operational manager
					
						if (StringUtils.equalsIgnoreCase(getApproval, "Operation Manager") && StringUtils.equalsIgnoreCase(dnStat, "Approved")) {
							
									if(!StringUtils.equalsIgnoreCase(serialnb, "0") ) // serial number exist
									{
										
			
										// TRANS TYPE NEW NODE ON EXISTED HARDWARE
										
										if(StringUtils.equalsIgnoreCase(trans_Type, "New Node on Existed Hardware"))
										{
												
											
											query = session.createQuery("select distinct arID from ArSerialNumber where serialNumber = :param1");
								
											query.setParameter("param1", serialnb);
											String ARid = (String) query.uniqueResult();
											
											
											query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
											query.setParameter("param1", ARid);
											query.setParameter("param2", DniID);
											query.executeUpdate();
											
											
											if (ARid != null) {
												
												//Add to Table AR_SERIAL_NUMBER
												
												query = session.createQuery(" select serialId from ArSerialNumber where arID = :param1 and serialNumber = :param2 "
														+ " and snodeID = :param3 and snodeName = :param4");
												
												query.setParameter("param1", ARid);
												query.setParameter("param2", serialnb);
												query.setParameter("param3", toNodeId);
												query.setParameter("param4", toNodeName);
												String checkIfEx = (String) query.uniqueResult();
												String arserialID="";
												if(checkIfEx != null) {
													 arserialID= checkIfEx;
													}
												else {
													
													 synchronized (this) {						
														 arserialID = "ARSNUM_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
															query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_SERIALNO = AR_SERIALNO + 1 ");
															query.executeUpdate();
															session.createSQLQuery("commit").executeUpdate();
															}
												}
												
											
												 ArSerialNumber assetRegSerialNumber = new ArSerialNumber();
												 
									        		assetRegSerialNumber.setSerialId(arserialID);
													assetRegSerialNumber.setSerialNumber(toSerialNumber);
													assetRegSerialNumber.setModel(itemModel);
													assetRegSerialNumber.setPartNumber(itemPartNb);
													assetRegSerialNumber.setSnodeID(toNodeId);
													assetRegSerialNumber.setSnodeName(toNodeName);
													assetRegSerialNumber.setSite(toSite);
													assetRegSerialNumber.setArID(ARid);
													assetRegSerialNumber.setPosition(toSlot);
													session.saveOrUpdate(assetRegSerialNumber);
									        		
													
												// Add to table AR_Node
													
													query = session.createQuery(" select nodearId from ArNode where arID = :param1  "
														+ " and nodeID = :param2 and nodeName = :param3");
													query.setParameter("param1",ARid);
													query.setParameter("param2", toNodeId);
													query.setParameter("param3", toNodeName);
												String checkIfExist = (String) query.uniqueResult();
												String AR_NodeID = "";
												if(checkIfExist != null) {
													AR_NodeID = checkIfExist;
													}
												else {
													// AR_NodeID = "ARNODE_" + year + "_" + appConfig.getSequenceNbr(28);
													 synchronized (this) {						
														 AR_NodeID = "ARNODE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT AR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
															query = session.createSQLQuery("UPDATE SEQ_TABLE SET AR_NODE = AR_NODE + 1 ");
															query.executeUpdate();
															session.createSQLQuery("commit").executeUpdate();
															}
												}

												
										        	
												    ArNode assetRegNode = new ArNode();
										        	assetRegNode.setNodearId(AR_NodeID);
													assetRegNode.setNodeID(toNodeId);
													assetRegNode.setNodeName(toNodeName);
													assetRegNode.setArID(ARid);
													session.saveOrUpdate(assetRegNode);
											}
											
											
											
											
											query = session.createQuery("select distinct farID from FarSerialNumber where inputSerialNb = :param1");
											query.setParameter("param1", serialnb);
											String FARid = (String) query.uniqueResult();
											
											Query query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
											query.setParameter("param1", FARid);
											query.setParameter("param2", DniID);
											query.executeUpdate();


											if (FARid != null) {

											// Add to FAR_SERIAL_NUMBER table

												query = session.createQuery(" select serialId from FarSerialNumber where farID = :param1 and inputSerialNb = :param2 "
											+ " and inputNodeID = :param3 and inputNodeName = :param4");

												query.setParameter("param1", FARid);
												query.setParameter("param2", serialnb);
												query.setParameter("param3", toNodeId);
												query.setParameter("param4", toNodeName);
											String farSerialID = (String) query.uniqueResult();
											String farserialID="";
											if(farSerialID != null) {
											 farserialID= farSerialID;
											
											}
											else {
											 //farserialID="FARSNUM_" + year + "_" + appConfig.getSequenceNbr(32); 
											 synchronized (this) {						
												 farserialID = "FARSNUM_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FAR_SERIALNO FROM SEQ_TABLE").uniqueResult().toString());	
													query = session.createSQLQuery("UPDATE SEQ_TABLE SET FAR_SERIALNO = FAR_SERIALNO + 1 ");
													query.executeUpdate();
													session.createSQLQuery("commit").executeUpdate();
													}
											}

											

											// Add in Fixed_Asset_Registry
											
											FarSerialNumber fixedAssetRegSerialNumber = new FarSerialNumber();
											
											fixedAssetRegSerialNumber.setSerialId(farserialID);
											fixedAssetRegSerialNumber.setInputSerialNb(toSerialNumber);
											fixedAssetRegSerialNumber.setInputModel(itemModel);
											fixedAssetRegSerialNumber.setInputpartNumber(itemPartNb);
											fixedAssetRegSerialNumber.setInputNodeID(toNodeId);
											fixedAssetRegSerialNumber.setInputNodeName(toNodeName);
											fixedAssetRegSerialNumber.setInputsite(toSite);
											fixedAssetRegSerialNumber.setFarID(FARid);
											fixedAssetRegSerialNumber.setInputPosition(toSlot);
											
											session.saveOrUpdate(fixedAssetRegSerialNumber);

											// Add to table FAR_NODE

											query = session.createQuery(" select nodefarId from FarNode where farID = :param1  "
											+ " and nodeID = :param2 and nodeName = :param3");

											query.setParameter("param1",FARid);
											query.setParameter("param2", toNodeId);
											query.setParameter("param3", toNodeName);
											String farNodeID = (String) query.uniqueResult();
											String FAR_NodeID = "";
											if(farNodeID != null) {
												FAR_NodeID = farNodeID;
												}
											else {
											// FAR_NodeID = "FARNODE_" + year + "_" + appConfig.getSequenceNbr(27);
											 synchronized (this) {						
												 FAR_NodeID = "FARNODE_" + year + "_" + Integer.parseInt(session.createSQLQuery("SELECT FAR_NODE FROM SEQ_TABLE").uniqueResult().toString());	
													query = session.createSQLQuery("UPDATE SEQ_TABLE SET FAR_NODE = FAR_NODE + 1 ");
													query.executeUpdate();
													session.createSQLQuery("commit").executeUpdate();
													}
											}


											FarNode farNode = new FarNode();
											
											farNode.setNodefarId(FAR_NodeID);
											farNode.setNodeID(toNodeId);
											farNode.setNodeName(toNodeName);
											farNode.setFarID(FARid);


											session.saveOrUpdate(farNode);
											}



											}
										
										//get arID
										query = session.createQuery("select distinct arID from ArSerialNumber where serialNumber = :param1 ");
										query.setParameter("param1",serialnb );
										String arID = (String) query.uniqueResult();
										
										
										query = session.createQuery("select distinct farID from FarSerialNumber where inputSerialNb = :param1 ");
										query.setParameter("param1",serialnb );
										String farID = (String) query.uniqueResult();

										if(StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Slot to Slot") || StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Node to Node") ||
										StringUtils.equalsIgnoreCase(trans_Type, "Transfer from Site to Site"))
										{
											
										//Update AR_SERIAL_NUMBER
										Query query = session.createSQLQuery("update AR_SERIAL_NUMBER set NODE_NAME = :param1, NODE_ID = :param2, POSITION = :param3, SITE = :param4 where NODE_ID = :param5 and "
										+ "SERIAL_NUMBER = :param6 and POSITION = :param7 and SITE = :param8");
										query.setParameter("param1", toNodeName);
										query.setParameter("param2", toNodeId);
										query.setParameter("param3", toSlot);
										query.setParameter("param4", toSite);
										query.setParameter("param5", nodeID );
										query.setParameter("param6", serialnb);
										query.setParameter("param7", fromSlot);
										query.setParameter("param8", Site);
										query.executeUpdate();

										//Update In AR_SITE
										
										query = session.createSQLQuery("update AR_SITE SET SITE_ID = '"+toSiteID+"',SITE_NAME = '"+towareName+"',WARE_ID = '"+towareID+"'"
												+ " WHERE AR_ID = :param1 ");
										query.setParameter("param1", arID);
										query.executeUpdate();
										

										//Update FAR_SERIAL_NUMBER
										query = session.createSQLQuery("update FAR_SERIAL_NUMBER set NODE_NAME = :param1, NODE_ID = :param2, POSITION = :param3, SITE = :param4 where NODE_ID = :param5 and "
										+ "SERIAL_NUMBER = :param6 and POSITION = :param7 and SITE = :param8");
										query.setParameter("param1", toNodeName);
										query.setParameter("param2", toNodeId);
										query.setParameter("param3", toSlot);
										query.setParameter("param4", toSite);
										query.setParameter("param5", nodeID );
										query.setParameter("param6", serialnb);
										query.setParameter("param7", fromSlot);
										query.setParameter("param8", Site);
										query.executeUpdate();
											
										//Update In Far_Site

										query = session.createSQLQuery("update FAR_SITE SET SITE_ID = '"+toSiteID+"',SITE_NAME = '"+towareName+"',WARE_ID = '"+towareID+"'"
												+ " WHERE FAR_ID = :param1 ");
										query.setParameter("param1", farID);
										query.executeUpdate();


										//Update AR_NODE
										query = session.createSQLQuery("update AR_NODE set NODE_NAME = :param1, NODE_ID = :param2 where NODE_ID = :param3 and "
										+ "NODE_NAME = :param4 and AR_ID = :param5");
										query.setParameter("param1", toNodeName);
										query.setParameter("param2", toNodeId);
										query.setParameter("param3", nodeID);
										query.setParameter("param4", nodeName);
										query.setParameter("param5", arID);
										query.executeUpdate();
										
										
										//Update FAR_NODE
										query = session.createSQLQuery("update FAR_NODE set NODE_NAME = :param1, NODE_ID = :param2 where NODE_ID = :param3 and "
										+ "NODE_NAME = :param4 and FAR_ID = :param5");
										query.setParameter("param1", toNodeName);
										query.setParameter("param2", toNodeId);
										query.setParameter("param3", nodeID);
										query.setParameter("param4", nodeName);
										query.setParameter("param5", farID);
										query.executeUpdate();
										
										}

										
										
										
										// TRANS TYPE DISAPPEAR FOR TRANSFER

										if (StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Transfer"))

										{
										
											query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
											query.setParameter("param1", arID);
											query.setParameter("param2", DniID);
											query.executeUpdate();
										          
											query = session.createSQLQuery("update ASSET_REGISTRY set AR_STATUS = 'Disappear' where AR_ID =:param1");
											query.setParameter("param1", arID);
											query.executeUpdate();


											query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
											query.setParameter("param1", farID);
											query.setParameter("param2", DniID);
											query.executeUpdate();
										

										    query = session.createSQLQuery("update FIXED_ASSET_REGISTRY set FAR_STATUS ='Disappear' where FAR_ID =:param1");
										    query.setParameter("param1", farID);
										    query.executeUpdate();

										}

										
										
										
										// TRANS TYPE DISAPPEAR FOR MAINTENANCE 

										if (StringUtils.equalsIgnoreCase(trans_Type, "Disappear for Maintenance"))

										{

										
											query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
											query.setParameter("param1", arID);
											query.setParameter("param2", DniID);
											query.executeUpdate();
															             
											query = session.createSQLQuery("update ASSET_REGISTRY set AR_STATUS = 'Maintenance' where AR_ID =:param1");
											query.setParameter("param1", arID);
											query.executeUpdate();

											query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
											query.setParameter("param1", farID);
											query.setParameter("param2", DniID);
											query.executeUpdate();
										
											query = session.createSQLQuery("update FIXED_ASSET_REGISTRY set FAR_STATUS ='Maintenance' where FAR_ID =:param1");
											query.setParameter("param1", farID);
											query.executeUpdate();

										} 

										
										
										
										// TRANS TYPE MAINTENANCE

										if (StringUtils.equalsIgnoreCase(trans_Type, "Maintenance"))

										{
										
											query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set AR_ID = :param1 where DNI_ID = :param2");
											query.setParameter("param1", arID);
											query.setParameter("param2", DniID);
											query.executeUpdate();
										
															             
											query = session.createSQLQuery("update ASSET_REGISTRY set AR_STATUS = 'Running' where AR_ID =:param1");
											query.setParameter("param1", arID);
											query.executeUpdate();

										
											query = session.createSQLQuery("update DISCOVERY_NEW_ITEM set FAR_ID = :param1 where DNI_ID = :param2");
											query.setParameter("param1", farID);
											query.setParameter("param2", DniID);
											query.executeUpdate();
										


											query = session.createSQLQuery("update FIXED_ASSET_REGISTRY set FAR_STATUS ='Running' where FAR_ID =:param1");
											query.setParameter("param1", farID);
											query.executeUpdate();


										} 

										
										
										
// TRANS TYPE RETIREMENT										
										
if (StringUtils.equalsIgnoreCase(trans_Type, "Retirement"))
    										
{
	//DELETE FROM AR_SITE BOQ
	query = session.createSQLQuery("delete AR_SITE where AR_ID = :param1");
	query.setParameter("param1", arID);
	query.executeUpdate();

//DELETE FROM AR_NODE BOQ

	query = session.createSQLQuery("delete AR_NODE where AR_ID = :param1");
	query.setParameter("param1", arID);
	query.executeUpdate();

//DELETE FROM AR_SERIAL_NUMBER
	query = session.createSQLQuery("delete AR_SERIAL_NUMBER where AR_ID = :param1");
	query.setParameter("param1", arID);
	query.executeUpdate();

//DELETE FROM AR_MODEL_PARTNUMBER

	query = session.createSQLQuery("delete AR_MODEL_PARTNUMBER where AR_ID = :param1");
	query.setParameter("param1", arID);
	query.executeUpdate();

// DELETE FROM ASSET_REGISTRY

	query = session.createSQLQuery("delete ASSET_REGISTRY where AR_ID = :param1");
	query.setParameter("param1", arID);
	query.executeUpdate();
	
//DELETE FROM FAR_SITE BOQ

	query = session.createSQLQuery("delete FAR_SITE where FAR_ID = :param1");
	query .setParameter("param1", farID);
	query .executeUpdate();

//DELETE FROM FAR_NODE BOQ

	query = session.createSQLQuery("delete FAR_NODE where FAR_ID = :param1");
	query.setParameter("param1", farID);
	query.executeUpdate();

//DELETE FROM FAR_SERIAL_NUMBER

	query = session.createSQLQuery("delete FAR_SERIAL_NUMBER where FAR_ID = :param1");
	query.setParameter("param1", farID);
	query.executeUpdate();

//DELETE FROM FAR_MODEL_PARTNUMBER

	query = session.createSQLQuery("delete FAR_MODEL_PARTNUMBER where FAR_ID = :param1");
	query.setParameter("param1", farID);
	query.executeUpdate();

//DELETE FROM FIXED_ASSET_REGISTRY

	query = session.createSQLQuery("delete FIXED_ASSET_REGISTRY where FAR_ID = :param1");
	query.setParameter("param1", farID);
	query.executeUpdate();

} // END TRANSACTION TYPE RETIREMENT

										
										
										
										
										
										
									}  // serial number exist 
									
									
					
								} // end approved by operation manager
				
				          }
			     }
        	
        	
        	
        	
        	
        	
if(StringUtils.equalsIgnoreCase(dnStat, "Approved") && StringUtils.equalsIgnoreCase(trans_Type, "New Node on New Hardware")) {
	
	System.out.println("enterddddd if statment for reconciledddd");
	System.out.println(toSerialNumber.getClass().getSimpleName());
	System.out.println("the serial number is "+toSerialNumber);
	
	if(StringUtils.equalsIgnoreCase(toSerialNumber, "0")) {
		System.out.println("update when serial is 0 for source serial");
		Query sn1=session.createSQLQuery("UPDATE WORK_ORDER_DESTINATION_SERIAL SET RECONCILED ='1' WHERE EXISTS(SELECT * FROM WORK_ORDER_DESTINATION_SERIAL WHERE ITEM_MODEL = :param1 and ITEM_PART_NUMBER= :param2 and WO_ID = :param3)");
		sn1.setParameter("param1",itemModel);
		sn1.setParameter("param2", itemPartNb);
		sn1.setParameter("param3","WO_2021_62");
		sn1.executeUpdate();
		
		System.out.println("update when serial is 0 for destination serial");
		Query sn2=session.createSQLQuery("UPDATE WORK_ORDER_SOURCE_SERIAL SET RECONCILED ='1' WHERE EXISTS(SELECT * FROM WORK_ORDER_SOURCE_SERIAL WHERE ITEM_MODEL = :param1 and ITEM_PART_NUMBER= :param2 and WO_ID = :param3)");
		sn2.setParameter("param1",itemModel);
		sn2.setParameter("param2", itemPartNb);
		sn2.setParameter("param3","WO_2021_62");
		sn2.executeUpdate();

	} else if (!StringUtils.equalsIgnoreCase(toSerialNumber, "0")) {
		System.out.println("update when we have a serial");
		
		Query sn1=session.createSQLQuery("SELECT ID FROM WORK_ORDER_SOURCE_SERIAL WHERE SERIAL_NO = :param1");
		sn1.setParameter("param1",toSerialNumber);
		 String sn1Result = (String) sn1.uniqueResult();
			System.out.println("sn1Result"+sn1Result);

		 if (sn1Result !=null) {
			System.out.println("SERIAL IS IN WORK ORDER SOURCE TABLE");
     		updateWO_Reconciled=session.createSQLQuery("update WORK_ORDER_SOURCE_SERIAL set RECONCILED ='1' where SERIAL_NO = :param1");
     		updateWO_Reconciled.setParameter("param1",toSerialNumber);
     		updateWO_Reconciled.executeUpdate();
			System.out.println("SERIAL IS RECONCILED IN WORK ORDER");

		 } else {
			 System.out.println("SERIAL IS NOTTTTTTT IN WORK ORDER SOURCE TABLE");
	     		updateWO_Reconciled=session.createSQLQuery("update WORK_ORDER_SOURCE_SERIAL set RECONCILED ='1', SERIAL_NO = :param1 WHERE EXISTS(SELECT * FROM WORK_ORDER_SOURCE_SERIAL WHERE ITEM_CODE = :param2 and ITEM_MODEL = :param3 and ITEM_PART_NUMBER= :param4 and WO_ID = :param5 and SERIAL_NO = '0' and ROWNUM = 1)"); 
	     		updateWO_Reconciled.setParameter("param1",toSerialNumber);
	     		updateWO_Reconciled.setParameter("param2",itmcode);
	     		updateWO_Reconciled.setParameter("param3",itemModel);
	     		updateWO_Reconciled.setParameter("param4", itemPartNb);
	     		//updateWO_Reconciled.setParameter("param5","WO_2021_62");
	     		updateWO_Reconciled.setParameter("param5",WorkOrder);

	     		updateWO_Reconciled.executeUpdate();
	     	 
				System.out.println("SERIAL IS RECONCILED IN WORK ORDER sourceeeee");
		 }
		 
		 Query sn2=session.createSQLQuery("SELECT ID FROM WORK_ORDER_DESTINATION_SERIAL WHERE SERIAL_NO = :param1");
			sn2.setParameter("param1",toSerialNumber);
			 String sn2Result = (String) sn2.setMaxResults(1).uniqueResult();
				System.out.println("sn1Result"+sn1Result);

			 if (sn2Result !=null) {
				System.out.println("SERIAL IS IN WORK ORDER DESTINATION TABLE");
	     		updateWO_Reconciled=session.createSQLQuery("update WORK_ORDER_DESTINATION_SERIAL set RECONCILED ='1' where SERIAL_NO = :param1");
	     		updateWO_Reconciled.setParameter("param1",toSerialNumber);
	     		updateWO_Reconciled.executeUpdate();
				System.out.println("SERIAL IS RECONCILED IN WORK ORDER");

			 } else {
				 System.out.println("SERIAL IS NOTTTTTTT IN WORK ORDER DESTINATION TABLE");
		     		updateWO_Reconciled=session.createSQLQuery("update WORK_ORDER_DESTINATION_SERIAL set RECONCILED ='1', SERIAL_NO = :param1 WHERE EXISTS(SELECT * FROM WORK_ORDER_SOURCE_SERIAL WHERE ITEM_CODE = :param2 and ITEM_MODEL = :param3 and ITEM_PART_NUMBER= :param4 and WO_ID = :param5 and SERIAL_NO = '0' and ROWNUM = 1)"); 
		     		updateWO_Reconciled.setParameter("param1",toSerialNumber);
		     		updateWO_Reconciled.setParameter("param2",itmcode);
		     		updateWO_Reconciled.setParameter("param3",itemModel);
		     		updateWO_Reconciled.setParameter("param4", itemPartNb);
		     		//updateWO_Reconciled.setParameter("param5","WO_2021_62");
		     		updateWO_Reconciled.setParameter("param5",WorkOrder);

		     		updateWO_Reconciled.setMaxResults(1).executeUpdate();
		     		
					System.out.println("SERIAL IS RECONCILED IN WORK ORDER");
			 }

	}
		       		
        	}



		
        	}catch (Exception e) {
        		logger.info("Error while getting records from table name: " , e);
        		System.out.println("Error in Data Saving");
        	} finally {
    			if (session != null && session.isOpen()) {
    				tx.commit();
    				session.close();
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
	query.setString("param_" + i, param.toString());
	}
	
	if(query.uniqueResult() != null)
	{
		value = (double)query.uniqueResult();
	}
	else 
	System.out.println("There is no any record related to the query: " +query+ "in the method: findAllByMultiClzMultiQryPrms" );
	
	return value;
}
 public  <T> void persistSingleColumn(Session session ,String queryString, List<Object> params) {
		
		int i;
		Transaction tx =session.beginTransaction();
		query = session.createQuery(queryString);
		for (Object param : params) {
			i = params.indexOf(param) + 1;
			query.setString("param_" + i, param.toString());
		}
		query.executeUpdate();
		tx.commit();
	 }			
}         


