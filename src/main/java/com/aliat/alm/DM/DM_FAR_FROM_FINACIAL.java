package com.aliat.alm.DM;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

public class DM_FAR_FROM_FINACIAL {
	
	public static void main(String[] args) {

		String min = null;
        String itmcode=null;
        String itmname=null;
        String itmpartnbr=null;
        String suppid=null;
        String suppName=null;
        String wareID=null;
        String wareName=null;
    	String str = null;
	     String pass=null;
	     String strpwd = null;
	     String struser = null;
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
	         Statement stmt4 = null;
	         Statement stmt5 = null;
	         Statement stmttype = null;
	         
	         String query = null;
	         Statement stmnt = null;
	         ResultSet getRes = null;
	         PreparedStatement prstmnt;
	         String farSiteID = null;
	         String farModelNo = null;
	         String farNode = null;
	         String farSNID = null;
	         String assetName = null;
	         
	         String nodeid = null;
	         String	nodename= null;
	         String	nodetype= null;
	         Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
	            
	            
			
	      // Move DM_IT to AR where ITEM_CAT_CODE and MODEL is not null
            
            //String query1 = "select * from DM_FINANCIAL";  
            String query1 = "select * from DM_FINANCIAL where (ASSETMODEL,FA_CATEGORY) IN (select item_model, item_cat_code from item)";
            stmttype = con.createStatement();
            ResultSet rs1 = stmttype.executeQuery(query1);
 
		while (rs1.next()) {
			itmcode=null;
			min=null;
			suppid=null;
			nodeid=null;
			nodename=null;
			nodetype = null;
					
						
						
						////Get Supplier Details
						query = "select SUPPLIER_ID from SUPPLIER where SUPPLIER_NAME ='" + rs1.getString("VENDOR_NAME") +"' and SUPPLIER_NAME IS NOT NULL and rownum =1";  
					    stmnt = con.createStatement();
					    getRes = stmnt.executeQuery(query);
					            
				        while (getRes.next()) {
				           	suppid= getRes.getString("SUPPLIER_id"); 
				           	suppName = rs1.getString("VENDOR_NAME");
						}
					    getRes.close();
					    stmnt.close();
								
						// Get Warehouse Details	
						query = "select WARE_ID, WARE_NAME from WAREHOUSE where SITE_ID='" + rs1.getString("SITEID") +"'";  
					    stmnt = con.createStatement();
					    getRes = stmnt.executeQuery(query);
					            
				        while (getRes.next()) {
				           	wareID= getRes.getString("WARE_ID"); 
				           	wareName = getRes.getString("WARE_NAME");
						}
				        getRes.close();
						stmnt.close();
								
						////Get NodeID and NodeName
						assetName = rs1.getString("ASSETNAME");	
						if(assetName.contains("/") == true)
						{
							String arr[] = assetName.split("/");
							assetName = arr[0];
						}
									
						query = "select NODEID , NOOENAME, NODETYPE from DM_inventory where NODEID like '" +assetName+"'";  
					    stmnt = con.createStatement();
					    getRes = stmnt.executeQuery(query);
					            
				        while (getRes.next()) {
				           	nodeid= getRes.getString("NODEID"); 
				           	nodename= getRes.getString("NOOENAME"); 
				           	nodetype = getRes.getString("NODETYPE"); 
						}
				        getRes.close();
						stmnt.close();
						
						System.out.println("NodeID is: "+nodeid+" and NodeName is: "+nodename);
								
										
								
					    //System.out.println("select item_code,item_name,ITEM_PART_NUMBER, VALUATION_RATE from item where item_model='" + rs1.getString("ASSETMODEL") +"' and item_cat_code ='" + rs1.getString("FA_CATEGORY") +"'");
						String query3 = "select item_code,item_name,ITEM_PART_NUMBER, VALUATION_RATE from item where item_model='" + rs1.getString("ASSETMODEL") +"' and item_cat_code ='" + rs1.getString("FA_CATEGORY") +"'";  
			            stmt3 = con.createStatement();
			            ResultSet rs3 = stmt3.executeQuery(query3);
			            
			            String itemModel = "";
			            while (rs3.next()) {
			            	itmcode= rs3.getString("item_code");   
			            	itmname=rs3.getString("item_name");  
			            	itmpartnbr=rs3.getString("ITEM_PART_NUMBER");
			            	itemModel=rs1.getString("ASSETMODEL");
			            	//valuationRate = rs3.getString("VALUATION_RATE");
			            	
						}
						rs3.close();
						stmt3.close();
						
						
							
						////Get sequence number for FAR Table
						String query2 = "select FIXEDASSETREGISTRY_SEQ.nextval as nbr from dual";  
			            stmt2 = con.createStatement();
			            ResultSet rs2 = stmt2.executeQuery(query2);
			            
			            while (rs2.next()) {
			            	min= rs2.getString("nbr"); 
			            	min="FAR_"+year+"_"+min;
						}
						rs2.close();
						stmt2.close();
								
						System.out.println("seq Value = " + min +"     and fa category is :   "+ rs1.getString("FA_CATEGORY") + "    and item code is :   " + itmcode);
					
						System.out.println("insert into FIXED_ASSET_REGISTRY (FAR_ID,ITEM_CODE,CREATED_DATE,LAST_MODIFIED_DATE,ITEM_NAME,INITIALCOST,DEPRECIATIONPER,DAILYDEPRECAMNT,ACCUMULDEPRECAMNT,NETCOST,"
								+ "SALVAGEVALUE,PO_ID,FA_CATEGORY,ACCUMULDEPRECCODE,DEPREC_CODE,USEFULLIFEMONTHS,SUPPLIER_ID,VENDOR_NUMBER,PROJ_NUMBER,SITE_ID,ITEM_PART_NUMBER,ASSET_NUMBER,ITEM_MODEL,ITEM_NAME_REGISTER,"
								+ "NODE_ID,NODE_NAME,SCRAPDATE,ITEM_SN,SUPPLIER_NAME,WARE_ID,WARE_NAME,FAR_STATUS) "
								+ "values('"+ min +"','"+ itmcode +"', sysdate,sysdate,'"+ itmname +"',"+
								 "'" + rs1.getDouble("INITIALCOST") +"','" + rs1.getDouble("DEPRECIATIONPER") +"','" + rs1.getDouble("DAILYDEPRECAMNT") +"','" + rs1.getDouble("ACCUMULDEPRECAMNT") +"','" + rs1.getDouble("NETCOST") +"','" + rs1.getDouble("SALVAGEVALUE") +"','" + rs1.getString("PONUMBER") +"',"+
								 "'" + rs1.getString("FA_CATEGORY") +"','" + rs1.getString("ACCUMULDEPRECCODE") +"','" + rs1.getString("DEPREC_CODE") +"','" + rs1.getDouble("USEFUL") +"','" + suppid +"','" + rs1.getString("VENDOR_NUMBER") +"','" + rs1.getString("PROJ_NUMBER") +"',"+
								 "'" + rs1.getString("SITEID") +"','" + itmpartnbr +"','" + rs1.getString("ASSETSN") +"','" +itemModel +"','" + rs1.getString("ASSETNAME") +"','" + nodeid +"','" + nodename +"',"+
								 "sysdate,'" + rs1.getString("ASSETSN") +"','"+suppName +"','"+wareID+"','"+wareName+"','Running')");
			
						
						PreparedStatement stmt = con.prepareStatement("insert into FIXED_ASSET_REGISTRY (FAR_ID,ITEM_CODE,CREATED_DATE,LAST_MODIFIED_DATE,ITEM_NAME,INITIALCOST,DEPRECIATIONPER,DAILYDEPRECAMNT,ACCUMULDEPRECAMNT,NETCOST,"
								+ "SALVAGEVALUE,PO_ID,FA_CATEGORY,ACCUMULDEPRECCODE,DEPREC_CODE,USEFULLIFEMONTHS,SUPPLIER_ID,VENDOR_NUMBER,PROJ_NUMBER,SITE_ID,ITEM_PART_NUMBER,ASSET_NUMBER,ITEM_MODEL,ITEM_NAME_REGISTER,"
								+ "NODE_ID,NODE_NAME,SCRAPDATE,ITEM_SN,SUPPLIER_NAME,WARE_ID,WARE_NAME,FAR_STATUS) "
								+ "values('"+ min +"','"+ itmcode +"', sysdate,sysdate,'"+ itmname +"',"+
								 "'" + rs1.getDouble("INITIALCOST") +"','" + rs1.getDouble("DEPRECIATIONPER") +"','" + rs1.getDouble("DAILYDEPRECAMNT") +"','" + rs1.getDouble("ACCUMULDEPRECAMNT") +"','" + rs1.getDouble("NETCOST") +"','" + rs1.getDouble("SALVAGEVALUE") +"','" + rs1.getString("PONUMBER") +"',"+
								 "'" + rs1.getString("FA_CATEGORY") +"','" + rs1.getString("ACCUMULDEPRECCODE") +"','" + rs1.getString("DEPREC_CODE") +"','" + rs1.getDouble("USEFUL") +"','" + suppid +"','" + rs1.getString("VENDOR_NUMBER") +"','" + rs1.getString("PROJ_NUMBER") +"',"+
								 "'" + rs1.getString("SITEID") +"','" + itmpartnbr +"','" + rs1.getString("ASSETSN") +"','" + itemModel+"','" + rs1.getString("ASSETNAME") +"','" + nodeid +"','" + nodename +"',"+
								 "sysdate,'" + rs1.getString("ASSETSN") +"','"+suppName +"','"+wareID+"','"+wareName+"','Running')");
						
			            stmt.executeUpdate();
				        stmt.close();
				        
				        
				        
				        ////Get sequence number for FAR_SITE Table
				        query = "select FAR_SITE_SEQ.nextval as nbr from dual";  
			            stmnt = con.createStatement();
			            getRes = stmnt.executeQuery(query);
			            
			            while (getRes.next()) {
			            	farSiteID= getRes.getString("nbr"); 
			            	farSiteID="FARSITE_"+year+"_"+farSiteID;
						}
			            getRes.close();
			            stmnt.close();
						
						
			            prstmnt = con.prepareStatement("insert into FAR_SITE (FARSITE_ID,SITE_ID,SITE_NAME,FAR_ID,WARE_ID) "
								+ "values('"+ farSiteID +"','"+ rs1.getString("SITEID") +"','"+wareName+"','"+ min +"','"+wareID+"')");
								
			            prstmnt.executeUpdate();
			            prstmnt.close();
						
			            if(itemModel != null && !StringUtils.equalsIgnoreCase(itemModel,"NA") &&
				        		!StringUtils.equalsIgnoreCase(itemModel,"0")
				        		&& !StringUtils.equalsIgnoreCase(itemModel,"N/A") && !StringUtils.equalsIgnoreCase(itemModel,"None") && !StringUtils.equalsIgnoreCase(itemModel,"Unknown")
				        		)
				        {
			            	
				            ////Get sequence number for FAR_MODEL_PARTNUMBER Table
					        query = "select FAR_MODEL_PARTNO_SEQ.nextval as nbr from dual";  
				            stmnt = con.createStatement();
				            getRes = stmnt.executeQuery(query);
				            
				            while (getRes.next()) {
				            	farModelNo= getRes.getString("nbr"); 
				            	farModelNo="FARMP_"+year+"_"+farModelNo;
							}
				            getRes.close();
				            stmnt.close();
							
							
				            prstmnt = con.prepareStatement("insert into FAR_MODEL_PARTNUMBER (ITM_ID,ITEM_PART_NUMBER,QUANTITY,ITEM_MODEL,ITEM_CODE,PRIMARY,FAR_ID) "
									+ "values('"+ farModelNo +"','"+ itmpartnbr +"','1','"+itemModel+"','"+ itmcode +"','1','"+min+"')");
									
				            prstmnt.executeUpdate();
				            prstmnt.close();
				        }
			            
			            
			            ////Get sequence number for FAR_NODE Table
				        query = "select FAR_NODE_SEQ.nextval as nbr from dual";  
			            stmnt = con.createStatement();
			            getRes = stmnt.executeQuery(query);
			            
			            while (getRes.next()) {
			            	farNode= getRes.getString("nbr"); 
			            	farNode="FARNODE_"+year+"_"+farNode;
						}
			            getRes.close();
			            stmnt.close();
						
						
			            prstmnt = con.prepareStatement("insert into FAR_NODE (NODEFAR_ID,NODE_ID,NODE_NAME,FAR_ID,NODE_TYPE) "
								+ "values('"+ farNode +"','"+ nodeid +"','"+nodename+"','"+min+"','"+nodetype+"')");
								
			            prstmnt.executeUpdate();
			            prstmnt.close();
			            
			            
			          
			            if(rs1.getString("ASSETSN") != null  && !StringUtils.equalsIgnoreCase(rs1.getString("ASSETSN"), "NA") && !StringUtils.equalsIgnoreCase(rs1.getString("ASSETSN"),"0")
			            		&& !StringUtils.equalsIgnoreCase(rs1.getString("ASSETSN"),"Not Classified")
			            		&& !StringUtils.equalsIgnoreCase(rs1.getString("ASSETSN"),"N/A") && !StringUtils.equalsIgnoreCase(rs1.getString("ASSETSN"),"None") && !StringUtils.equalsIgnoreCase(rs1.getString("ASSETSN"),"Unknown"))
			            {
				            ////Get sequence number for FAR_SERIAL_NUMBER Table
					        query = "select FAR_SN_SEQ.nextval as nbr from dual";  
				            stmnt = con.createStatement();
				            getRes = stmnt.executeQuery(query);
				            
				            while (getRes.next()) {
				            	farSNID= getRes.getString("nbr"); 
				            	farSNID="FARSNUM_"+year+"_"+farSNID;
							}
				            getRes.close();
				            stmnt.close();
							
							
				            prstmnt = con.prepareStatement("insert into FAR_SERIAL_NUMBER (SERIAL_ID,SERIAL_NUMBER,MODEL,PART_NUMBER,NODE_ID,NODE_NAME,FAR_ID,SITE,POSITION) "
									+ "values('"+ farSNID +"','"+ rs1.getString("ASSETSN")+"','"+itemModel+"','"+itmpartnbr+"','"+ nodeid +"','"+nodename+"','"+min+"','"+rs1.getString("SITEID")+"','')");
									
				            prstmnt.executeUpdate();
				            prstmnt.close();
			            }
						

		}
		rs1.close();

		stmttype.close();
			
			con.close();
			System.out.println("DM_FAR_FROM_FINACIAL  COMPLETED");
	        }   
        		
      
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
		}

}
