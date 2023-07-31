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

public class Appliance2Item {
	
	public static void main(String[] args) {

		long i;
		String str = null;
	     String pass=null;
	     String strpwd = null;
	     String struser = null;
        // Move DM_WindowsServer to Item
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
	            
	            
			
        ////Get Model from DM_WindowsServer
            
            String query2 = "select distinct (ITEM_CAT_CODE) from DM_APPLIANCE  where  ITEM_CAT_CODE is not null";  
            stmttype = con.createStatement();
            ResultSet rs2 = stmttype.executeQuery(query2);
 
		while (rs2.next()) {
			    //System.out.println(rs2.getString("ITEM_MODEL") );

			////Get Item_category/name and Model from DM_WindowsServer  
				i=1;
			    String query3 = "select distinct (item_model) from DM_APPLIANCE  where ITEM_CAT_CODE='" + rs2.getString("ITEM_CAT_CODE") + "'";  
			    stmtdetail = con.createStatement();
	            ResultSet rs3 = stmtdetail.executeQuery(query3);
	            while (rs3.next()) {
		                System.out.println(rs3.getString("ITEM_MODEL") +"-"+rs2.getString("ITEM_CAT_CODE") +"-"+ i  );
		            	PreparedStatement stmt = con.prepareStatement("insert into DM_ITEM ("
		            			+ "select distinct item_code ||'-'|| '" + i +"',ITEM_NAME,CREATED_DATE,LAST_MODIFIED_DATE,UNIT, ITEM_DESCRIPTION, DOMAIN, ITEM_CATEGORY, ITEM_TYPE,\r\n" + 
		            			"CABLE_TYPE ,WEIGHT,WEIGHT_UOM,LENGTH,WIDTH,HEIGHT, ITEM_SIZE,SIZE_UOM,SERVICE,END_OF_LIFE,VALUATION_RATE,DISABLED,ITEM_IMAGE,\r\n" + 
		            			"WARRANTY_PERIOD,TECH_2G,TECH_3G,TECH_4G,TECH_5G,ITEM_MODEL,ITEM_PART_NUMBER,ITEM_MANUFACTURER,ITEM_MODE,ITEM_CAT_CODE,\r\n" + 
		            			"DEPREC_CODE,ACCUM_DEPREC_CODE,USEFULLIFEMONTHS,ITEM_KIND,OS,PHYSICAL_RAM,PROCESSOR_TYPE,PROCESSOR_VENDOR,SKU_NUMBER,UUID  from DM_APPLIANCE where ITEM_MODEL='" + rs3.getString("ITEM_MODEL") +"' and ITEM_CAT_CODE ='" + rs2.getString("ITEM_CAT_CODE") +"'  and  rownum = 1" +")"); 
		        
		            	
		                stmt.executeUpdate();
				        stmt.close();
		 	            	
			        i=i+1;
			        
	            }
	            rs3.close();
	            stmtdetail.close();
			
			

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
