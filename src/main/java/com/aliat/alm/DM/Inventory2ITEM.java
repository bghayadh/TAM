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

public class Inventory2ITEM {
	
	public static void main(String[] args) {

		   long i=1;
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
		         
		         
		         Statement stmt = null;
		         Statement stmt2 = null;
		         Statement stmtcat = null;
		        
		         
		         //String querycat = "select distinct FA_CATEGORY from DM_Inventory where fa_category is not null and model is not null";  
		         String querycat = "select distinct (FA_CATEGORY) from DM_Inventory where fa_category is not null";
		         stmtcat = con.createStatement();
	             ResultSet rscat = stmtcat.executeQuery(querycat);   
	             while (rscat.next()) {
	            	 i=1;
	            	 String query1 = "select distinct (MODEL) from DM_Inventory where FA_CATEGORY='" + rscat.getString("FA_CATEGORY") + "'";  
		             stmt2 = con.createStatement();
		             ResultSet rs = stmt2.executeQuery(query1);   
	            
	           
		                      
	             
				             while (rs.next()) {
				            	 
											    //System.out.println(rs.getString("FA_CATEGORY") +":"+ rs.getString("MODEL") );
						
											   /* String query = "select distinct * from DM_Inventory  where  FA_CATEGORY ='" + rs.getString("FA_CATEGORY") + "' and MODEL = '" + rs.getString("MODEL") + "' and  rownum = 1";  
											    stmt = con.createStatement();
									             ResultSet rsitm = stmt.executeQuery(query);   
									             while (rsitm.next()) {*/
									            	   
														String dd= rscat.getString("FA_CATEGORY")+'-'+i;
														System.out.println("Inventory2ITEM " + dd  );
														PreparedStatement stmt3 = con.prepareStatement("insert into DM_ITEM ("
										            			+ "select distinct '" + dd +"',NOOENAME,sysdate,sysdate,'0', DESCRIPTION, '0', L4, NODETYPE,\r\n" + 
										            			"0 ,'0','0','0','0','0', '0','0',0,sysdate,INITIALCOST,0,'DM',\r\n" + 
										            			"'0','0','0','0','0',MODEL,PARTNUMBER,VENDOR_NAME,'0',FA_CATEGORY,\r\n" + 
										            			"DEPREC_CODE,ACCUMULDEPRECCODE,USEFULLIFEMONTHS,'TELECOM-INVENTORY','0','0','0','0','0','0'  from DM_Inventory where MODEL='" + rs.getString("MODEL") +"' and FA_CATEGORY ='" + rscat.getString("FA_CATEGORY") +"'  and  rownum = 1" +")"); 

														stmt3.executeUpdate();
												        stmt3.close();
												        i=i+1;
									            	 
									            // }
									          //   rsitm.close();
												// stmt.close();
					       
							}
							rs.close();
							stmt2.close();
			     			
	            	 
	             }
	             rscat.close();
					stmtcat.close();        
					con.close();
					System.out.println("COMPLETED");
				}
	        catch(Exception e){
		  	      System.err.println(e);
		  	      e.printStackTrace();
		  	   }
	        
	        
			}


}
