package com.aliat.alm.DM;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdItemManufacturer_nodeType {
	
public static int getRepeated(String str)
{     
	int count = 0;  
	int startFrom = 0;  
	for(; ;)  
	{  
		int index = str.indexOf('/', startFrom); 
		if(index >= 0)  
		{  
			// match found. Hence, increment the count  
			count = count + 1;  
	  
			// start looking after the searched index  
			startFrom = index + 1;  
		} 
		else  
		{  
			// the value of index is - 1 here. Therefore, terminate the loop  
		break;  
		}
	}  
	  
	System.out.println("In the String: "+ str);   
	System.out.println("The '/' character has come "+ count + " times"); 
	return count;
}
	
public static void main(String[] args) {

        // update Item Manufacturer and Node Type
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
	         Statement stmttype = null;
	         Statement stmtdetail = null; 
	         Statement stmtn = null;
	            
	             String query1 = "SELECT distinct  a.ASSETMODEL as ASSETMODEL ,b.MANUFACTURER as MANUFACTURER from DM_FINANCIAL a, DM_INVENTORY b  where a.ASSETMODEL =b.MODEL and a.SITEID=b.SITE";  
	             stmt2 = con.createStatement();
	             ResultSet rs = stmt2.executeQuery(query1);
      
			while (rs.next()) {
				    System.out.println("UpdItemManufacturer_nodeType  " + rs.getString("ASSETMODEL") +":"+ rs.getString("MANUFACTURER"));
                   ////update ITEM_MANUFACTURER field in ITEM table
					PreparedStatement stmt = con.prepareStatement("update DM_ITEM set ITEM_MANUFACTURER = '" + rs.getString("MANUFACTURER") + "'  where ITEM_MODEL ='" + rs.getString("ASSETMODEL") + "'");
				    System.out.println("update DM_ITEM set ITEM_MANUFACTURER = '" + rs.getString("MANUFACTURER") + "' where ITEM_MODEL ='" + rs.getString("ASSETMODEL") + "'");
					stmt.executeUpdate();
			        stmt.close();
				

			}
			rs.close();

            stmt2.close();
			
			///update NodeType field in table Item
            
            /*String query2 = "select  distinct FA_CATEGORY,NETYPE,ASSETMODEL from DM_FINANCIAL";  
            stmttype = con.createStatement();
            ResultSet rs2 = stmttype.executeQuery(query2);*/
            
            ////////////////////////// Liliane update //////////////////////////////////////////
            String query2 = "select distinct NODEID from DM_INVENTORY";  
            stmttype = con.createStatement();
            ResultSet rs2 = stmttype.executeQuery(query2);
            
 
		while (rs2.next()) {
			
			   /* String nodetyp = rs2.getString("NETYPE");
			    System.out.println("update NodeType  " + nodetyp +" : "+rs2.getString("ASSETMODEL") +":"+ rs2.getString("FA_CATEGORY"));
              ////update node Type field in ITEM table
			    
			    //String query3 = "select  ITEM_CODE,ITEM_MODEL,ITEM_CAT_CODE from item where ITEM_CAT_CODE='" + rs2.getString("FA_CATEGORY") + "' and ITEM_MODEL='" + rs2.getString("ASSETMODEL") + "'";  
			    String query3 = "select  distinct ITEM_CODE,ITEM_MODEL,ITEM_CAT_CODE from DM_ITEM where ITEM_CAT_CODE='" + rs2.getString("FA_CATEGORY") + "' and ITEM_MODEL='" + rs2.getString("ASSETMODEL") + "'";  
			    stmtdetail = con.createStatement();
	            ResultSet rs3 = stmtdetail.executeQuery(query3);
	            while (rs3.next()) {
	            	//PreparedStatement stmt = con.prepareStatement("insert into ITEM_NODE_TYPE values('" + rs3.getString("ITEM_CODE") + "','" + nodetyp + "','" + rs3.getString("ITEM_MODEL") +"','" + rs3.getString("ITEM_CAT_CODE") +"')");
	            	PreparedStatement stmt = con.prepareStatement("insert into DM_ITEM_NODE_TYPE values('" + rs3.getString("ITEM_CODE") +"','" + nodetyp + "','" + rs3.getString("ITEM_MODEL") +"','" + rs3.getString("ITEM_CAT_CODE") +"')");
				    System.out.println("insert into DM_ITEM_NODE_TYPE values('" + rs3.getString("ITEM_CODE") +"','" + nodetyp + "','" + rs3.getString("ITEM_MODEL") +"','" + rs3.getString("ITEM_CAT_CODE") +"')");
					stmt.executeUpdate();
			        stmt.close();*/
					String query10 = "select NODEID, NOOENAME, NODETYPE, SITE, VENDOR_NUMBER, VENDOR_NAME from DM_INVENTORY where NODEID='"+rs2.getNString("NODEID")+"'";  
		            stmttype = con.createStatement();
		            ResultSet rs10 = stmttype.executeQuery(query10);
		            String nodeName = null;
		            String nodeType = null;
		            String nodeSite = null;
		            String nodeVendorNo = null;
		            String nodeVendorName = null;
		            while (rs10.next()) {
		            	nodeName = rs10.getString("NOOENAME");
		            	nodeType = rs10.getString("NODETYPE");
		            	nodeSite = rs10.getString("SITE");
		            	nodeVendorNo = rs10.getString("VENDOR_NUMBER");
		            	nodeVendorName = rs10.getString("VENDOR_NAME");
		            	
		            }
		            rs10.close();
		            stmttype.close();
			        ////////////////////////////////Liliane update //////////////////////////////// 
					String query6 = "select SITE_ID FROM DM_WAREHOUSE where WARE_NAME='"+nodeSite+"'";  
			        stmtn = con.createStatement();
			        ResultSet rs_site = stmtn.executeQuery(query6);
			        String siteID = null;
			        while (rs_site.next()) {
			        	siteID = rs_site.getString("SITE_ID");
			        }
			        stmtn.setMaxRows(1);
			        rs_site.close();
				    stmtn.close(); 
			        
					PreparedStatement stmt = con.prepareStatement("insert into DM_NODE_ACTIVE (NODE_ID,NODE_NAME,NODE_TYPE,SITE_ID,WARE_NAME,VENDOR,SUPPLIER_NAME,TRANS_TYPE) VALUES ('"+rs2.getString("NODEID")+"','"+nodeName+"','"+nodeType+"','"+siteID+"','"+nodeSite+"','"+nodeVendorNo+"','"+nodeVendorName+"','Data Migration')");  
				    System.out.println("insert into DM_NODE_ACTIVE (NODE_ID,NODE_NAME,NODE_TYPE,SITE_ID,WARE_NAME,VENDOR,SUPPLIER_NAME,TRANS_TYPE) VALUES ('" + rs2.getString("NODEID") +"','" + nodeName+"','"+nodeType+"','"+siteID+"','"+nodeSite+"','"+nodeVendorNo+"','"+nodeVendorName+"','Data Migration')");
					stmt.executeUpdate();
			        stmt.close();
			        
			        
				    String query3 = "select * from DM_INVENTORY where NODEID = '"+rs2.getString("NODEID")+"' order by ELEMENT ASC";  
				    System.out.println("select * from DM_INVENTORY where NODEID = '"+rs2.getString("NODEID")+"' order by ELEMENT ASC");
				    stmtdetail = con.createStatement();
		            ResultSet rs3 = stmtdetail.executeQuery(query3);
		            while (rs3.next()) {
		            	
		            	String nodeElemet = rs3.getString("ELEMENT");
		            	if(nodeElemet.contains("/") == false)
		            	{
		            		int value = Integer.parseInt(rs3.getString("ELEMENT").replaceAll("[^0-9]", ""));
		            		System.out.println("Cabinet no is: "+value);
		            		PreparedStatement stmt1 = con.prepareStatement("insert into DM_NODE_CABINET (CABINETNO,SERIALNUMBER,NODEID,VENDORNAME,VENDOR,TRANS_TYPE) VALUES ('"+value+"','"+rs3.getString("SN")+"','"+rs2.getString("NODEID")+"','"+nodeVendorName+"','"+nodeVendorNo+"','Data Migration')");  
						    System.out.println("insert into DM_NODE_CABINET (CABINETNO,SERIALNUMBER,NODEID,VENDOR,VENDOR_NUMBER,TRANS_TYPE) VALUES ('"+value+"','"+rs3.getString("SN")+"','" +rs2.getString("NODEID")+"','"+nodeVendorName+"','"+nodeVendorNo+"','Data Migration')");
							stmt1.executeUpdate();
					        stmt1.close();
		            	}
		            	else
		            	{
		            		int repeatedtimes = getRepeated(nodeElemet);
		            		if(repeatedtimes == 1)
		            		{  
		            			String[] parts = nodeElemet.split("/");
		            			String part1 = parts[0]; 
		            			String part2 = parts[1]; 
		            			
		            			
		            			int cabinetNo = Integer.parseInt(part1.replaceAll("[^0-9]", ""));
		            			int subrackNo = Integer.parseInt(part2.replaceAll("[^0-9]", ""));
		            			
		            			System.out.println("the cabinet No is : "+cabinetNo);
		            	        System.out.println("the subrack No is : "+subrackNo);
			            		PreparedStatement stmt1 = con.prepareStatement("insert into DM_NODE_SUBRACK (CABINETNO,SUBRACKNO,NODEID,SERIALNUMBER) VALUES ('"+cabinetNo+"','"+subrackNo+"','"+rs2.getString("NODEID")+"','"+rs3.getString("SN")+"')");  
							    System.out.println("insert into DM_NODE_SUBRACK (CABINETNO,SERIALNUMBER,NODEID,SERIALNUMBER) VALUES ('"+cabinetNo+"','"+subrackNo+"','" +rs2.getString("NODEID")+"','"+rs3.getString("SN")+"')");
								stmt1.executeUpdate();
						        stmt1.close();
		            		}
		            		else if(repeatedtimes == 3)
		            		{
		            			String[] parts = nodeElemet.split("/");
		            			String part1 = parts[0]; 
		            			String part2 = parts[1]; 
		            			String part3 = parts[2]; 
		            			
		            			int cabinetNo = Integer.parseInt(part1.replaceAll("[^0-9]", ""));
		            			int subrackNo = Integer.parseInt(part2.replaceAll("[^0-9]", ""));
		            			int slotNo = Integer.parseInt(part3.replaceAll("[^0-9]", ""));
		            			
		            			System.out.println("the cabinet No is : "+cabinetNo);
		            	        System.out.println("the subrack No is : "+subrackNo);
		            	        System.out.println("the slot No is : "+slotNo);
		            	        
			            		PreparedStatement stmt1 = con.prepareStatement("insert into DM_NODE_SLOT (CABINETNO,SUBRACKNO,SLOTNO,NODEID) VALUES ('"+cabinetNo+"','"+subrackNo+"','"+slotNo+"','"+rs2.getString("NODEID")+"')");  
							    System.out.println("insert into DM_NODE_SLOT (CABINETNO,SERIALNUMBER,SLOTNO,NODEID) VALUES ('"+cabinetNo+"','"+subrackNo+"','"+slotNo+"','" +rs2.getString("NODEID")+"')");
								stmt1.executeUpdate();
						        stmt1.close();
						        
						        PreparedStatement stmt3 = con.prepareStatement("insert into DM_NODE_BOARD (CABINETNO,SUBRACKNO,SLOTNO,NODEID,SERIALNUMBER) VALUES ('"+cabinetNo+"','"+subrackNo+"','"+slotNo+"','"+rs2.getString("NODEID")+"','"+rs3.getString("SN")+"')");  
							    System.out.println("insert into DM_NODE_BOARD (CABINETNO,SERIALNUMBER,SLOTNO,NODEID,SERIALNUMBER) VALUES ('"+cabinetNo+"','"+subrackNo+"','"+slotNo+"','" +rs2.getString("NODEID")+"','"+rs3.getString("SN")+"')");
								stmt3.executeUpdate();
						        stmt3.close();
		            			
		            		}
		            	}
		            }
		            rs3.close();
			        stmtdetail.close();
			    
			        
					
			        
			       
	            //}
	            //rs3.close();
	           // stmtdetail.close();
	            
	            
	            
	            
			
			

		}
		rs2.close();

		stmttype.close();
			
			con.close();
			System.out.println("COMPLETED");
	        }   
        		
      
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
		}

}
