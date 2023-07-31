package com.aliat.alm.DM;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DMCumulPassive {
	
	public static void main(String[] args) {

        // move DM DM_APPLIANCE,DM_DEVICEINSTACK ,DM_FIREWALL to ITEM
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
	
                    //Passive move DM_AUXILIARYINSTALLATION to DM_PASSIVE table
					PreparedStatement stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_AUXILIARYINSTALLATION)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("1-Passive- DM_AUXILIARYINSTALLATION moved to DM_PASSIVE");
			        
			        //Passive  move DM_CivilElements to DM_PASSIVE table 
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_CivilElements)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("2-Passive- DM_CivilElements moved to DM_PASSIVE");
			        
			        //Passive move DM_Cloudservices to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_Cloudservices)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("3-Passive- DM_Cloudservices moved to DM_PASSIVE");
			        
			        //Passive move DM_Esim to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_Esim)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("4-Passive- DM_Esim moved to DM_PASSIVE");

			        //Passive move DM_FireFightingSystem to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_FireFightingSystem)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("5-Passive- DM_FireFightingSystem moved to DM_PASSIVE");
			        
			        //Passive move DM_FROEquipment to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_FROEquipment)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("6-Passive- DM_FROEquipment moved to DM_PASSIVE");

			       //Passive move DM_Furniture to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_Furniture)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("7-Passive- DM_Furniture moved to DM_PASSIVE");
			        
			        //Passive   move DM_Hayat to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_Hayat)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("8-Passive- DM_Hayat moved to DM_PASSIVE");
			        
			      //Passive move DM_ITEquipment to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_ITEquipment)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("9-Passive- DM_ITEquipment moved to DM_PASSIVE");
			        
			     // Passive move DM_NOPO to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_NOPO)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("10-Passive- DM_NOPO moved to DM_PASSIVE");
			        
			     // Passive move DM_NotClassified to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_NotClassified)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("11-Passive- DM_NotClassified moved to DM_PASSIVE");
			        
			      // Passive move DM_OfficeEquipment to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_OfficeEquipment)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("12-Passive- DM_OfficeEquipment moved to DM_PASSIVE");
			        
			     // Passive move DM_SecuritySystem to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_SecuritySystem)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("13-Passive- DM_SecuritySystem moved to DM_PASSIVE");
			        
			        // Passive move DM_FixedFiberNetworkDARKFIBER to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_FixedFiberNetworkDARKFIBER)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("14-Passive- DM_FixedFiberNetworkDARKFIBER moved to DM_PASSIVE");
			        
			        // Passive move DM_PowerElements to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_PowerElements)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("15-Passive- DM_PowerElements moved to DM_PASSIVE");
			        
			        // Passive move DM_RealEstate to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_RealEstate)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("16-Passive- DM_RealEstate moved to DM_PASSIVE");
			        
			        // Passive move DM_Transmission to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_Transmission)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("17-Passive- DM_Transmission moved to DM_PASSIVE");
			        
			        // Passive move DM_VASservices to DM_PASSIVE table
			        stmt = con.prepareStatement("insert into DM_PASSIVE (select * from DM_VASservices)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("18-Passive- DM_VASservices moved to DM_PASSIVE");
					
					
					stmt = con.prepareStatement("update DM_PASSIVE set L3 = 'Radio Access network Services' where L3 is null "
			        		+ "and FA_CATEGORY ='NW-SRC-RAS-STP'");
			        
			        
			        stmt.executeUpdate();
			        stmt.close();
			        
			        System.out.println("Update Completed");
	
			con.close();
			
	        }   
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
		}

}
