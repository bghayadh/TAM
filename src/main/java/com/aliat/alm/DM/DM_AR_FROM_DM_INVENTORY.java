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

public class DM_AR_FROM_DM_INVENTORY {
	
	public static void main(String[] args) {

		String min = null;
        String itmcode=null;
        String itemName=null;
        String suppid=null;
        String wareID=null;
        String siteID = null;
        String suppName = null;
        String str = null;
	     String pass=null;
	     String strpwd = null;
	     String struser = null;
	     
	     String query = null;
         Statement stmnt = null;
         ResultSet getRes = null;
         PreparedStatement prstmnt;
         String arSiteID = null;
         String arModelNo = null;
         String arNode = null;
         String arSNID = null;
         String wareName = null;
         
         
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
	         Statement stmt4 = null;
	         Statement stmt5 = null;
	         Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
	            
	            
			
	      // Move DM_IT to AR where ITEM_CAT_CODE and MODEL is not null
            
            //String query1 = "select * from DM_INVENTORY";  
            String query1 = "select * from DM_INVENTORY where (MODEL,FA_CATEGORY) IN (select item_model, item_cat_code from item)";
            stmttype = con.createStatement();
            ResultSet rs1 = stmttype.executeQuery(query1);
 
		while (rs1.next()) {
			itmcode=null; 
			min=null;
			
				String query20 = "select count(*) as SNcount from asset_registry where ITEM_SN = '"+rs1.getString("SN")+"' "
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
						
						
						////Get Supplier ID
						String query4 = "select SUPPLIER_ID from SUPPLIER where SUPPLIER_NAME ='" + rs1.getString("VENDOR_NAME") +"' and SUPPLIER_NAME IS NOT NULL and rownum =1";  
					     stmt4 = con.createStatement();
					      ResultSet rs4 = stmt4.executeQuery(query4);
					            
					        while (rs4.next()) {
					           	suppid= rs4.getString("SUPPLIER_ID"); 
					           	suppName = rs1.getString("VENDOR_NAME"); 
							}
							rs4.close();
							stmt4.close();
							
							
						////Get SITE ID
						 String query5 = "select WARE_ID, SITE_ID from WAREHOUSE where ware_name ='" + rs1.getString("SITE") +"' and rownum = 1";  
					     stmt5 = con.createStatement();
					      ResultSet rs5 = stmt5.executeQuery(query5);
					            
					        while (rs5.next()) {
					        	wareID= rs5.getString("WARE_ID"); 
					        	siteID = rs5.getString("SITE_ID");
					        	wareName = rs1.getString("SITE");
							}
							rs5.close();
							stmt5.close();
						
						System.out.println("Item model: "+rs1.getString("MODEL")+" item_cat_code: "+rs1.getString("FA_CATEGORY"));
						//Get item_code from item table if exist
						String query3 = "select item_code, item_name from item where ITEM_MODEL='" + rs1.getString("MODEL") +"' and ITEM_CAT_CODE ='" + rs1.getString("FA_CATEGORY") +"'";  
			            stmt3 = con.createStatement();
			            ResultSet rs3 = stmt3.executeQuery(query3);
			            
			            while (rs3.next()) {
			            	itmcode= rs3.getString("item_code");  
			            	itemName = rs3.getString("item_name"); 
						}
						rs3.close();
						stmt3.close();
						
						System.out.println("seq Value = " + min +"     and fa category is :   "+ rs1.getString("FA_CATEGORY") + "    and item code is :   " + itmcode);
			
						System.out.println("insert into ASSET_REGISTRY (AR_ID,ITEM_CODE,CREATED_DATE,LAST_MODIFIED_DATE,ITEM_NAME,ITEM_DESCRIPTION,ITEM_CATEGORY,"
								+ "ITEM_TYPE,END_OF_LIFE,VALUATION_RATE,ITEM_NAME_REGISTER,PO_ID,NODE_ID,NODE_NAME,SITE_ID,SUPPLIER_ID,SCRAPDATE,ITEM_SN,ITEM_MODEL,ITEM_PART_NUMBER,WARE_ID,WARE_NAME,SUPPLIER_NAME,AR_STATUS) "
								+ "values('"+ min +"','"+ itmcode +"', sysdate,sysdate,'"+itemName+"',"+
								 "'" + rs1.getString("DESCRIPTION") +"','" + rs1.getString("TYPECATEGORY") +"','" + rs1.getString("NODETYPE") +"',"+
								 "sysdate," + rs1.getDouble("INITIALCOST") +",'"
								 + rs1.getString("ELEMENT") +"','" + rs1.getString("PO") +"','" + rs1.getString("NODEID") +"','" + rs1.getString("NOOENAME") +"','" +siteID +"','" +suppid +"' ,"+
								  "sysdate,'" + rs1.getString("SN") +"','" + rs1.getString("MODEL") +"','" + rs1.getString("PARTNUMBER") +"','"+wareID+"','"+rs1.getString("SITE")+"','"+suppName+"','Running' " +")"); 
						
						
						//Insert into table AR 
						PreparedStatement stmt = con.prepareStatement("insert into ASSET_REGISTRY (AR_ID,ITEM_CODE,CREATED_DATE,LAST_MODIFIED_DATE,ITEM_NAME,ITEM_DESCRIPTION,ITEM_CATEGORY,"
								+ "ITEM_TYPE,END_OF_LIFE,VALUATION_RATE,ITEM_NAME_REGISTER,PO_ID,NODE_ID,NODE_NAME,SITE_ID,SUPPLIER_ID,SCRAPDATE,ITEM_SN,ITEM_MODEL,ITEM_PART_NUMBER,WARE_ID,WARE_NAME,SUPPLIER_NAME,AR_STATUS) "
								+ "values('"+ min +"','"+ itmcode +"', sysdate,sysdate,'"+itemName+"',"+
								 "'" + rs1.getString("DESCRIPTION") +"','" + rs1.getString("TYPECATEGORY") +"','" + rs1.getString("NODETYPE") +"',"+
								 "sysdate," + rs1.getDouble("INITIALCOST") +",'"
								 + rs1.getString("ELEMENT") +"','" + rs1.getString("PO") +"','" + rs1.getString("NODEID") +"','" + rs1.getString("NOOENAME") +"','" +siteID +"','" +suppid +"' ,"+
								  "sysdate,'" + rs1.getString("SN") +"','" + rs1.getString("MODEL") +"','" + rs1.getString("PARTNUMBER") +"','"+wareID+"','"+rs1.getString("SITE")+"','"+suppName+"','Running' " +")"); 
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
								+ "values('"+ arSiteID +"','"+ siteID +"','"+wareName+"','"+ min +"','"+wareID+"')");
								
			            prstmnt.executeUpdate();
			            prstmnt.close();
						
			            if(rs1.getString("MODEL") != null && !StringUtils.equalsIgnoreCase(rs1.getString("MODEL"),"NA") &&
				        		!StringUtils.equalsIgnoreCase(rs1.getString("MODEL"),"0")
				        		&& !StringUtils.equalsIgnoreCase(rs1.getString("MODEL"),"N/A") && !StringUtils.equalsIgnoreCase(rs1.getString("MODEL"),"None") && !StringUtils.equalsIgnoreCase(rs1.getString("MODEL"),"Unknown"))
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
									+ "values('"+ arModelNo +"','"+ rs1.getString("PARTNUMBER") +"','1','"+rs1.getString("MODEL")+"','"+ itmcode +"','1','"+min+"')");
									
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
								+ "values('"+ arNode +"','"+ rs1.getString("NODEID") +"','"+rs1.getString("NOOENAME")+"','"+min+"','"+rs1.getString("NODETYPE")+"')");
								
			            prstmnt.executeUpdate();
			            prstmnt.close();
			            
			            
			            if(rs1.getString("SN") != null  && !StringUtils.equalsIgnoreCase(rs1.getString("SN"), "NA") && !StringUtils.equalsIgnoreCase(rs1.getString("SN"),"0")
			            		&& !StringUtils.equalsIgnoreCase(rs1.getString("SN"),"Not Classified")
			            		&& !StringUtils.equalsIgnoreCase(rs1.getString("SN"),"N/A") && !StringUtils.equalsIgnoreCase(rs1.getString("SN"),"None") && !StringUtils.equalsIgnoreCase(rs1.getString("SN"),"Unknown"))
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
									+ "values('"+ arSNID +"','"+ rs1.getString("SN")+"','"+rs1.getString("MODEL")+"','"+rs1.getString("PARTNUMBER")+"','"+rs1.getString("NODEID")+"','"+rs1.getString("NOOENAME")+"','"+min+"','"+rs1.getString("SITE")+"','')");
									
				            prstmnt.executeUpdate();
				            prstmnt.close();
			            }
				}

		}
		rs1.close();

		stmttype.close();
			
			con.close();
			System.out.println("DM_AR_FROM_DM_INVENTORY  COMPLETED");
	        }   
        		
      
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
		}

}
