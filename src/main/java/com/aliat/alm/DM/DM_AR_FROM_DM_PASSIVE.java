package com.aliat.alm.DM;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

public class DM_AR_FROM_DM_PASSIVE {
	
	public static void main(String[] args) {

		String min = null;
        String itmcode=null;
		String str = null;
	     String pass=null;
	     String strpwd = null;
	     String struser = null;
	     String wareID = null;
	     String wareName = null;
	     String siteID = null;
	     String suppID = null;
	     String suppName = null;
	     String itemName = null;
	     
	     String query = null;
         Statement stmnt = null;
         ResultSet getRes = null;
         PreparedStatement prstmnt;
         String arSiteID = null;
         String arModelNo = null;
         String arNode = null;
         String arSNID = null;
        
         
        try{

        	pass=System.getProperty("user.dir")+"\\target\\classes\\hibernate.cfg.xml";
		    File file = new File(pass);
		  FileInputStream fis = null;
		  BufferedInputStream bis = null;
		  DataInputStream dis = null;
		     
		  fis = new FileInputStream(file);
		
		bis = new BufferedInputStream(fis);
		dis = new DataInputStream(bis);
		String sVerifyText="jdbc:oracle:thin:";
		String spasswrd="hibernate.connection.password";
		String susename="hibernate.connection.username";
		while (dis.available() != 0) {
		//System.out.println(dis.readLine());
		String data = dis.readLine();

              if (data.contains(sVerifyText)) {	
                  int index = data.indexOf('>');
                  str=data.substring(index+1, data.length());
                  index = str.indexOf('<');
                  str=str.substring(0, index);
                  // System.out.println(str);  
              }
              if (data.contains(spasswrd)) {	
                  int index = data.indexOf('>');
                  strpwd=data.substring(index+1, data.length());
                  index = strpwd.indexOf('<');
                  strpwd=strpwd.substring(0, index);
                  //System.out.println(strpwd);  
              }
              if (data.contains(susename)) {	
                  int index = data.indexOf('>');
                  struser=data.substring(index+1, data.length());
                  index = struser.indexOf('<');
                  struser=struser.substring(0, index);
                  //System.out.println(struser);  
              }          

		}
        	
		    	DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
		         //Connection con = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:alm","alm","alm");
				Connection con = DriverManager.getConnection (str,struser,strpwd);
	         
	         Statement stmt2 = null;
	         Statement stmt3 = null;
	         Statement stmttype = null;
	         Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
	            
	            
			
	      // Move DM_IT to AR where ITEM_CAT_CODE and MODEL is not null
            
            //String query1 = "select * from DM_PASSIVE";  
            String query1 = "select * from DM_PASSIVE where (ITEM_MODEL,ITEM_CAT_CODE) IN (select item_model, item_cat_code from item)";
            stmttype = con.createStatement();
            ResultSet rs1 = stmttype.executeQuery(query1);
 
		while (rs1.next()) {
			
			itmcode=null;
			min=null;
			String nodeid = null;
	        String nodename = null;
	        String nodetype = null;
	        
		        		
		        String query20 = "select count(*) as SNcount from asset_registry where ITEM_SN = '"+rs1.getString("ITEM_SN")+"' "
			 			+ "and ITEM_SN != 'Not Classified' and ITEM_SN != 'NA' and ITEM_SN is not null and ITEM_SN != '0'";  
	            stmt2 = con.createStatement();
	            ResultSet rs20 = stmt2.executeQuery(query20);
	            int SNcount = 0;
	            while (rs20.next()) {
	            	SNcount = rs20.getInt("SNcount");
				}
				rs20.close();
				stmt2.close();
					
				if(SNcount == 0)
				{
					////Get sequence number for AR Table
					 String query2 = "select ASSETREGISTRY_SEQ.nextval as nbr from dual";  
			            stmt2 = con.createStatement();
			            ResultSet rs2 = stmt2.executeQuery(query2);
			            
			            while (rs2.next()) {
			            	min= rs2.getString("nbr"); 
			            	min="AR_"+year+"_"+min;
						}
						rs2.close();
						stmt2.close();
						
						String query4 = "select SUPPLIER_ID from SUPPLIER where SUPPLIER_NAME ='" + rs1.getString("SUPPLIER_NAME") +"' and SUPPLIER_NAME IS NOT NULL and rownum =1";  
					    Statement stmt4 = con.createStatement();
					      ResultSet rs4 = stmt4.executeQuery(query4);
					            
					        while (rs4.next()) {
					           	suppID= rs4.getString("SUPPLIER_ID"); 
					           	suppName = rs1.getString("SUPPLIER_NAME"); 
							}
							rs4.close();
							stmt4.close();
						
					 ////Get SITE ID
					 String query5 = "select WARE_ID, WARE_NAME from WAREHOUSE where SITE_ID ='" + rs1.getString("SITE_ID") +"' and rownum = 1";  
				     Statement stmt5 = con.createStatement();
				      ResultSet rs5 = stmt5.executeQuery(query5);
				            
				        while (rs5.next()) {
				        	wareID= rs5.getString("WARE_ID"); 
				        	wareName = rs5.getString("WARE_NAME");
						}
						rs5.close();
						stmt5.close();

						//System.out.println("SN is :"+rs1.getString("ITEM_SN")+" , Model is: "+rs1.getString("ITEM_MODEL")+" , PartNo is: "+rs1.getString("ITEM_PART_NUMBER"));
						
						
						////Get NodeID and NodeName
						query = "select NODEID, NOOENAME, NODETYPE, ITEM_SN FROM DM_INVENTORY a, DM_PASSIVE b where b.item_SN = a.SN and b.item_SN ='"+rs1.getString("ITEM_SN")+"' and b.item_SN is not null"; 
					    stmnt = con.createStatement();
					    getRes = stmnt.executeQuery(query);
					            
				        while (getRes.next()) {
				           	nodeid= getRes.getString("NODEID"); 
				           	nodename= getRes.getString("NOOENAME"); 
				           	nodetype = getRes.getString("NODETYPE"); 
						}
				        getRes.close();
						stmnt.close();
						
						if(nodeid == null)
						{
							nodeid =  rs1.getString("OBJECT_ID");
							nodename = rs1.getString("OBJECT_ID");
							nodetype = "";
						}
						
	
						//Get item_code from item table if exist
						String query3 = "select item_code, item_name from item where item_model='" + rs1.getString("ITEM_MODEL") +"' and item_cat_code ='" + rs1.getString("ITEM_CAT_CODE") +"'";  
			            stmt3 = con.createStatement();
			            ResultSet rs3 = stmt3.executeQuery(query3);
			            
			            while (rs3.next()) {
			            	itmcode= rs3.getString("item_code");
			            	itemName = rs3.getString("item_name");
						}
						rs3.close();
						stmt3.close();
						System.out.println("seq Value = " + min +"     and fa category is :   "+ rs1.getString("ITEM_CAT_CODE") + "    and item code is :   " + itmcode);
			
						System.out.println("insert into ASSET_REGISTRY (AR_ID,ITEM_CODE,CREATED_DATE,LAST_MODIFIED_DATE,ITEM_NAME,UNIT,ITEM_DESCRIPTION,DOMAIN,ITEM_CATEGORY," + 
								"ITEM_TYPE,CABLE_TYPE,WEIGHT,WEIGHT_UOM,LENGTH,WIDTH,HEIGHT,SIZE_UOM,SERVICE,END_OF_LIFE,VALUATION_RATE,DISABLED,"
								+ "ITEM_IMAGE,WARRANTY_PERIOD,TECH_2G,TECH_3G,TECH_4G,TECH_5G,PO_ID,NODE_ID,NODE_NAME,SITE_ID,SUPPLIER_ID,OWNERSHIP,"
								+ "SCRAPDATE,SCRAPSTATUS,NOTES,SHELTER_VENDOR,SHELTER_ROOM_ID,LOCATION_SUB_TYPE,LOCATION_CLASSIFICATION,LOCATION_ADDRESS,LAST_MODIFIED_USER,"
								+ "OBJECT_ID,ITEM_SN,ITEM_MODEL,ITEM_PART_NUMBER,SUPPLIER_NAME,WARE_ID,WARE_NAME,POSITION,AR_STATUS)"+
								 "values('"+ min +"','"+ itmcode +"', sysdate,sysdate,'" + itemName +"','" + rs1.getString("unit") +"',"+
								 "'" + rs1.getString("item_description") +"','" + rs1.getString("domain") +"','" + rs1.getString("item_category") +"','" + rs1.getString("item_type") +"','" + rs1.getString("CABLE_TYPE") +"','" + rs1.getString("WEIGHT") +"','" + rs1.getString("WEIGHT_UOM") +"',"+
								 "'" + rs1.getString("LENGTH") +"','" + rs1.getString("WIDTH") +"','" + rs1.getString("HEIGHT") +"','" + rs1.getString("SIZE_UOM") +"','" + rs1.getString("SERVICE") +"',sysdate," + rs1.getDouble("VALUATION_RATE") +",\r\n"+
								 "'" + rs1.getString("DISABLED") +"','" + rs1.getString("ITEM_IMAGE") +"','" + rs1.getString("WARRANTY_PERIOD") +"','" + rs1.getString("TECH_2G") +"','" + rs1.getString("TECH_3G") +"','" + rs1.getString("TECH_4G") +"','" + rs1.getString("TECH_5G") +"',"+
								 "'" + rs1.getString("PO_ID") +"','" + nodeid +"','" +nodename+"','" + rs1.getString("SITE_ID") +"','" + suppID+"','" + rs1.getString("OWNERSHIP") +"',\r\n"+
								 "sysdate,'" + rs1.getString("SCRAPSTATUS") +"','" + rs1.getString("NOTES") +"'	,'" + rs1.getString("SHELTER_VENDOR") +"'	,'" + rs1.getString("SHELTER_ROOM_ID") +"'	,'" + rs1.getString("LOCATION_SUB_TYPE") +"'	,'" + rs1.getString("LOCATION_CLASSIFICATION") +"'	,\r\n"+
								 "'" + rs1.getString("LOCATION_ADDRESS") +"','" + rs1.getString("LAST_MODIFIED_USER") +"','" + rs1.getString("OBJECT_ID") +"','" + rs1.getString("ITEM_SN") +"','" + rs1.getString("ITEM_MODEL") +"','" + rs1.getString("ITEM_PART_NUMBER") +"','" +suppName+ "', '" 
							 	 + wareID +"','" + wareName +"','" + rs1.getString("POSITION") +"','Running' )"); 
						
						//Insert into table AR 
						PreparedStatement stmt = con.prepareStatement("insert into ASSET_REGISTRY (AR_ID,ITEM_CODE,CREATED_DATE,LAST_MODIFIED_DATE,ITEM_NAME,UNIT,ITEM_DESCRIPTION,DOMAIN,ITEM_CATEGORY," + 
								"ITEM_TYPE,CABLE_TYPE,WEIGHT,WEIGHT_UOM,LENGTH,WIDTH,HEIGHT,SIZE_UOM,SERVICE,END_OF_LIFE,VALUATION_RATE,DISABLED,"
								+ "ITEM_IMAGE,WARRANTY_PERIOD,TECH_2G,TECH_3G,TECH_4G,TECH_5G,PO_ID,NODE_ID,NODE_NAME,SITE_ID,SUPPLIER_ID,OWNERSHIP,"
								+ "SCRAPDATE,SCRAPSTATUS,NOTES,SHELTER_VENDOR,SHELTER_ROOM_ID,LOCATION_SUB_TYPE,LOCATION_CLASSIFICATION,LOCATION_ADDRESS,LAST_MODIFIED_USER,"
								+ "OBJECT_ID,ITEM_SN,ITEM_MODEL,ITEM_PART_NUMBER,SUPPLIER_NAME,WARE_ID,WARE_NAME,POSITION,AR_STATUS)"+
								 "values('"+ min +"','"+ itmcode +"', sysdate,sysdate,'" + itemName +"','" + rs1.getString("unit") +"',"+
								 "'" + rs1.getString("item_description") +"','" + rs1.getString("domain") +"','" + rs1.getString("item_category") +"','" + rs1.getString("item_type") +"','" + rs1.getString("CABLE_TYPE") +"','" + rs1.getString("WEIGHT") +"','" + rs1.getString("WEIGHT_UOM") +"',"+
								 "'" + rs1.getString("LENGTH") +"','" + rs1.getString("WIDTH") +"','" + rs1.getString("HEIGHT") +"','" + rs1.getString("SIZE_UOM") +"','" + rs1.getString("SERVICE") +"',sysdate," + rs1.getDouble("VALUATION_RATE") +",\r\n"+
								 "'" + rs1.getString("DISABLED") +"','" + rs1.getString("ITEM_IMAGE") +"','" + rs1.getString("WARRANTY_PERIOD") +"','" + rs1.getString("TECH_2G") +"','" + rs1.getString("TECH_3G") +"','" + rs1.getString("TECH_4G") +"','" + rs1.getString("TECH_5G") +"',"+
								 "'" + rs1.getString("PO_ID") +"','" + nodeid +"','" + nodename +"','" + rs1.getString("SITE_ID") +"','" + suppID+"','" + rs1.getString("OWNERSHIP") +"',\r\n"+
								 "sysdate,'" + rs1.getString("SCRAPSTATUS") +"','" + rs1.getString("NOTES") +"'	,'" + rs1.getString("SHELTER_VENDOR") +"'	,'" + rs1.getString("SHELTER_ROOM_ID") +"'	,'" + rs1.getString("LOCATION_SUB_TYPE") +"'	,'" + rs1.getString("LOCATION_CLASSIFICATION") +"'	,\r\n"+
								 "'" + rs1.getString("LOCATION_ADDRESS") +"','" + rs1.getString("LAST_MODIFIED_USER") +"','" + rs1.getString("OBJECT_ID") +"','" + rs1.getString("ITEM_SN") +"','" + rs1.getString("ITEM_MODEL") +"','" + rs1.getString("ITEM_PART_NUMBER") +"','" +suppName+ "', '" 
							 	 + wareID +"','" + wareName +"','" + rs1.getString("POSITION") +"','Running' )");
						
			            stmt.executeUpdate();
				        stmt.close();
				        
				        
				        
				        ////Get sequence number for AR_SITE Table
				        query = "select AR_SITE_SEQ.nextval as nbr from dual";  
			            stmnt = con.createStatement();
			            getRes = stmnt.executeQuery(query);
			            
			            while (getRes.next()) {
			            	arSiteID= getRes.getString("nbr"); 
			            	arSiteID="ARSITE_"+year+"_"+arSiteID;
						}
			            getRes.close();
			            stmnt.close();
						
						
			            prstmnt = con.prepareStatement("insert into AR_SITE (ARSITE_ID,SITE_ID,SITE_NAME,AR_ID,WARE_ID) "
								+ "values('"+ arSiteID +"','"+ rs1.getString("SITE_ID") +"','"+wareName+"','"+ min +"','"+wareID+"')");
								
			            prstmnt.executeUpdate();
			            prstmnt.close();
						
				        if(rs1.getString("ITEM_MODEL") != null && !StringUtils.equalsIgnoreCase(rs1.getString("ITEM_MODEL"),"NA") &&
				        		!StringUtils.equalsIgnoreCase(rs1.getString("ITEM_MODEL"),"0")
				        		&& !StringUtils.equalsIgnoreCase(rs1.getString("ITEM_MODEL"),"N/A") && !StringUtils.equalsIgnoreCase(rs1.getString("ITEM_MODEL"),"None") && !StringUtils.equalsIgnoreCase(rs1.getString("ITEM_MODEL"),"Unknown")
				        		)
				        {
				            ////Get sequence number for AR_MODEL_PARTNUMBER Table
					        query = "select AR_MODEL_PARTNO_SEQ.nextval as nbr from dual";  
				            stmnt = con.createStatement();
				            getRes = stmnt.executeQuery(query);
				            
				            while (getRes.next()) {
				            	arModelNo= getRes.getString("nbr"); 
				            	arModelNo="ARMP_"+year+"_"+arModelNo;
							}
				            getRes.close();
				            stmnt.close();
							
							
				            prstmnt = con.prepareStatement("insert into AR_MODEL_PARTNUMBER (ITM_ID,ITEM_PART_NUMBER,QUANTITY,ITEM_MODEL,ITEM_CODE,PRIMARY,AR_ID) "
									+ "values('"+ arModelNo +"','"+ rs1.getString("ITEM_PART_NUMBER") +"','1','"+rs1.getString("ITEM_MODEL")+"','"+ itmcode +"','1','"+min+"')");
									
				            prstmnt.executeUpdate();
				            prstmnt.close();
				        }
			            
			            
			            ////Get sequence number for AR_NODE Table
				        query = "select AR_NODE_SEQ.nextval as nbr from dual";  
			            stmnt = con.createStatement();
			            getRes = stmnt.executeQuery(query);
			            
			            while (getRes.next()) {
			            	arNode= getRes.getString("nbr"); 
			            	arNode="ARNODE_"+year+"_"+arNode;
						}
			            getRes.close();
			            stmnt.close();
						
						
			            prstmnt = con.prepareStatement("insert into AR_NODE (NODEAR_ID,NODE_ID,NODE_NAME,AR_ID,NODE_TYPE) "
								+ "values('"+ arNode +"','"+ nodeid +"','"+nodename+"','"+min+"','"+nodetype+"')");
								
			            prstmnt.executeUpdate();
			            prstmnt.close();
			            
			            
			            if(rs1.getString("ITEM_SN") != null && !StringUtils.equalsIgnoreCase(rs1.getString("ITEM_SN"), "NA") && !StringUtils.equalsIgnoreCase(rs1.getString("ITEM_SN"),"0")
			            		&& !StringUtils.equalsIgnoreCase(rs1.getString("ITEM_SN"),"Not Classified")
			            		&& !StringUtils.equalsIgnoreCase(rs1.getString("ITEM_SN"),"N/A") && !StringUtils.equalsIgnoreCase(rs1.getString("ITEM_SN"),"None") && !StringUtils.equalsIgnoreCase(rs1.getString("ITEM_SN"),"Unknown"))
			            {
				            ////Get sequence number for AR_SERIAL_NUMBER Table
					        query = "select AR_SERIAL_NUMBER_SEQ.nextval as nbr from dual";  
				            stmnt = con.createStatement();
				            getRes = stmnt.executeQuery(query);
				            
				            while (getRes.next()) {
				            	arSNID= getRes.getString("nbr"); 
				            	arSNID="ARSNUM_"+year+"_"+arSNID;
							}
				            getRes.close();
				            stmnt.close();
							
							
				            prstmnt = con.prepareStatement("insert into AR_SERIAL_NUMBER (SERIAL_ID,SERIAL_NUMBER,MODEL,PART_NUMBER,NODE_ID,NODE_NAME,AR_ID,SITE,POSITION) "
									+ "values('"+ arSNID +"','"+ rs1.getString("ITEM_SN")+"','"+rs1.getString("ITEM_MODEL")+"','"+rs1.getString("ITEM_PART_NUMBER")+"','"+nodeid+"','"+nodename+"','"+min+"','"+rs1.getString("SITE_ID")+"','')");
									
				            prstmnt.executeUpdate();
				            prstmnt.close();
			            }
				}

		}
		rs1.close();

		stmttype.close();
			
			con.close();
			System.out.println("DM_AR_FROM_DM_PASSIVE  COMPLETED");
	        }   
        		
      
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
		}


}
