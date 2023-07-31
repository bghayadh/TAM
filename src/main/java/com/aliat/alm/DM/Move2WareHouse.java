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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Move2WareHouse {
	
	public static void main(String[] args) {

		/////////////////////////////////////////////////////// data.split("\\|", -1);
		///////REM to remove all empty fields and to remove ' and , from excel sheet and to extend filed size of warehouse name 300 Varchar in DB
		////////////////////////////////////////////////////////
        long i=1;
    	String warcode;
        long codefar;
        String min = null;
        String str = null;
        String pass=null;
        String strpwd = null;
        String struser = null;
        
        try{
        	
        	Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
        	
        	
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

		    // insert into warhouse
			String query2 = "(select WARE_NAME,CITY,ADDRESS,LONGITUDE,LATITUDE,SITE,SITE_ID,HUB_SITE,LOCATION_NOTES,SITE_HEIGHT,SHELTER,TOWER_HEIGHT,SHELTER_ID,SHELTER_TYPE,SHELTER_WITH_WITHOUT,SHELTER_VENDOR from DM_WAREHOUSE)\r\n" + 
					"minus \r\n" + 
					"(select a.WARE_NAME,a.CITY,a.ADDRESS,a.LONGITUDE,a.LATITUDE,a.SITE,a.SITE_ID,"
					+ "b.HUB_SITE,b.LOCATION_NOTES,b.BUILDING_HEIGHT,b.SHELTER,b.TOWER_HEIGHT,b.SHELTER_ID,b.SHELTER_TYPE,b.SHELTER_WITH_WITHOUT,b.SHELTER_VENDOR from WAREHOUSE a, WAREHOUSE_PASSIVE b "
					+ "where a.WARE_ID = b.WARE_ID)";

			Statement stmttype = con.createStatement();
            ResultSet rs2 = stmttype.executeQuery(query2);
 
            while (rs2.next()) {
            ////Get sequence number for warehouse seq
		         Statement stmtfar = null;
		         String query = "select WAREHOUSE_SEQ.nextval as nbr from dual";  
				 stmtfar = con.createStatement();
		         ResultSet rs20 = stmtfar.executeQuery(query);
		            
		            while (rs20.next()) {
		            	min= rs20.getString("nbr");   
		            	min=  "WARE_"+ year +"_"+ min;
					}
					rs20.close();
					stmtfar.close();
            
		            
					//PreparedStatement stmt1 = con.prepareStatement("insert into WAREHOUSE values('" + min + "',sysdate,sysdate,'" + rs2.getString("WARE_NAME") +"' ,'" + rs2.getString("CITY") +"','" + rs2.getString("ADDRESS1") +"','" + rs2.getString("ADDRESS2") +"','" + rs2.getString("LONGITUDE") +"' ,'" + rs2.getString("LATITUDE") +"','1', '" + rs2.getString("SITE_ID") +"','" + rs2.getString("HUB_SITE") +"','" + rs2.getString("LOCATION_NOTES") +"','" + rs2.getString("SITE_HEIGHT") +"','" + rs2.getString("SHELTER") +"','" + rs2.getString("TOWER_HEIGHT") +"','" + rs2.getString("SHELTER_ID") +"','" + rs2.getString("SHELTER_TYPE") +"','" + rs2.getString("SHELTER_WITH_WITHOUT") +"','" + rs2.getString("SHELTER_VENDOR") +"')");
					PreparedStatement stmt1 = con.prepareStatement("insert into WAREHOUSE (WARE_ID,CREATION_DATE,LAST_MODIFY_DATE,WARE_NAME,CITY,LONGITUDE,LATITUDE,SITE,SITE_ID,TECH_2G,TECH_3G,TECH_4G,TECH_5G,ADDRESS,STATUS) values('" + min + "',sysdate,sysdate,'" + rs2.getString("WARE_NAME") +"' ,'" + rs2.getString("CITY") +"','"+ rs2.getString("LONGITUDE") +"' ,'" + rs2.getString("LATITUDE") +"','1', '" + rs2.getString("SITE_ID")+"', '0', '0', '0', '0','" + rs2.getString("ADDRESS")+"','completed')");
					System.out.println("insert into WAREHOUSE (WARE_ID,CREATION_DATE,LAST_MODIFY_DATE,WARE_NAME,CITY,LONGITUDE,LATITUDE,SITE,SITE_ID,TECH_2G,TECH_3G,TECH_4G,TECH_5G,ADDRESS,STATUS) values('" + min + "',sysdate,sysdate,'" + rs2.getString("WARE_NAME") +"' ,'" + rs2.getString("CITY") +"','"+ rs2.getString("LONGITUDE") +"' ,'" + rs2.getString("LATITUDE") +"','1', '" + rs2.getString("SITE_ID")+"', '0', '0', '0', '0','" + rs2.getString("ADDRESS")+"','completed')");
					stmt1.executeUpdate();
			        stmt1.close();
			        
			        
			        PreparedStatement stmt2 = con.prepareStatement("insert into WAREHOUSE_PASSIVE (WARE_ID,WARE_NAME,SITE_ID,SITE_ADDRESS,HUB_SITE,LOCATION_NOTES,BUILDING_HEIGHT,SHELTER,TOWER_HEIGHT,SHELTER_ID,SHELTER_TYPE,SHELTER_WITH_WITHOUT,SHELTER_VENDOR,CREATION_DATE,LAST_MODIFY_DATE) values('" + min + "','" + rs2.getString("WARE_NAME") +"' ,'" + rs2.getString("SITE_ID") +"','" + rs2.getString("ADDRESS") +"','"+ rs2.getString("HUB_SITE") +"' ,'" + rs2.getString("LOCATION_NOTES") +"','" +rs2.getString("SITE_HEIGHT") +"','" + rs2.getString("SHELTER") +"','" + rs2.getString("TOWER_HEIGHT") +"','" + rs2.getString("SHELTER_ID") +"','" + rs2.getString("SHELTER_TYPE") +"','" + rs2.getString("SHELTER_WITH_WITHOUT") +"','" + rs2.getString("SHELTER_VENDOR") +"',sysdate,sysdate)");
			        System.out.println("insert into WAREHOUSE_PASSIVE (WARE_ID,WARE_NAME,SITE_ID,SITE_ADDRESS,HUB_SITE,LOCATION_NOTES,BUILDING_HEIGHT,SHELTER,TOWER_HEIGHT,SHELTER_ID,SHELTER_TYPE,SHELTER_WITH_WITHOUT,SHELTER_VENDOR,CREATION_DATE,LAST_MODIFY_DATE) values ('" + min + "','" + rs2.getString("WARE_NAME") +"' ,'" + rs2.getString("SITE_ID") +"','" + rs2.getString("ADDRESS") +"','"+ rs2.getString("HUB_SITE") +"' ,'" + rs2.getString("LOCATION_NOTES") +"','" +rs2.getString("SITE_HEIGHT") +"','" + rs2.getString("SHELTER") +"','" + rs2.getString("TOWER_HEIGHT") +"','" + rs2.getString("SHELTER_ID") +"','" + rs2.getString("SHELTER_TYPE") +"','" + rs2.getString("SHELTER_WITH_WITHOUT") +"','" + rs2.getString("SHELTER_VENDOR") +"')");
			        stmt2.executeUpdate();
			        stmt2.close();
            }
			
            rs2.close();
			
			
			con.close();
			System.out.println("Insert WAREHOUSE COMPLETED");
	        }   
        		
        catch (IOException e) {
				e.printStackTrace();
			} 
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
     
	   }

}
