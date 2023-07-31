package com.aliat.alm.DM;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DMCumulIt {
	
	public static void main(String[] args) {
		
		   String str = null;
	        String pass=null;
	        String strpwd = null;
	        String struser = null;

        // move DM DM_APPLIANCE,DM_DEVICEINSTACK ,DM_FIREWALL to ITEM
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
	
                    //move DM_APPLIANCE to ITEM table
					PreparedStatement stmt = con.prepareStatement("insert into DM_IT (select * from DM_Appliance)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("1-IT- DM_APPLIANCE moved to DM_IT");
			        
			        //move DM_DEVICEINSTACK to ITEM table 
			        stmt = con.prepareStatement("insert into DM_IT (select * from DM_DeviceinStack)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("2-IT- DM_DEVICEINSTACK moved to DM_IT");
			        
			        //move DM_FIREWALL to ITEM table
			        stmt = con.prepareStatement("insert into DM_IT (select * from DM_Firewall)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("3-IT- DM_FIREWALL moved to DM_IT");
			        
			        //move DM_Hypervisor to ITEM table
			        stmt = con.prepareStatement("insert into DM_IT (select * from DM_Hypervisor)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("4-IT- DM_Hypervisor moved to DM_IT");

			        //move DM_Layer3Switch to ITEM table
			        stmt = con.prepareStatement("insert into DM_IT (select * from DM_Layer3Switch)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("5-IT- DM_Layer3Switch moved to DM_IT");
			        
			        //move DM_Monitors to ITEM table
			        stmt = con.prepareStatement("insert into DM_IT (select * from DM_Monitors)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("6-IT- DM_Monitors moved to DM_IT");

			       //move DM_NetworkInfrastructure to ITEM table
			        stmt = con.prepareStatement("insert into DM_IT (select * from DM_NetworkInfrastructure)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("7-IT- DM_NetworkInfrastructure moved to DM_IT");
			        
			        //  move DM_SANSwitchSwitch to ITEM table
			        stmt = con.prepareStatement("insert into DM_IT (select * from DM_SANSwitchSwitch)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("8-IT- DM_SANSwitchSwitch moved to DM_IT");
			        
			      //move DM_Storage to ITEM table
			        stmt = con.prepareStatement("insert into DM_IT (select * from DM_Storage)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("9-IT- DM_Storage moved to DM_IT");
			        
			     // move DM_Switch to ITEM table
			        stmt = con.prepareStatement("insert into DM_IT (select * from DM_Switch)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("10-IT- DM_Switch moved to DM_IT");
			        
			     // move DM_SwitchSANSwitch to ITEM table
			        stmt = con.prepareStatement("insert into DM_IT (select * from DM_SwitchSANSwitch)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("11-IT- DM_SwitchSANSwitch moved to DM_IT");
			        
			      // move DM_WindowsDesktop to ITEM table
			        stmt = con.prepareStatement("insert into DM_IT (select * from DM_WindowsDesktop)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("12-IT- DM_WindowsDesktop moved to DM_IT");
			        
			     // move DM_WindowsServer to ITEM table
			        stmt = con.prepareStatement("insert into DM_IT (select * from DM_WindowsServer)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("13-IT- DM_WindowsServer moved to DM_IT");
	
			con.close();
			
	        }   
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
		}


}
