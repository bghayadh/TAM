package com.aliat.alm.DM;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import org.apache.commons.lang3.StringUtils;

import java.lang.Long;

public class Inventory_DM {
	
	public static void main(String[] args) {

		/////////////////////////////////////////////////////// data.split("\\|", -1);
		///////REM to remove all empty fields and to remove ' and , from excel sheet  & |
		////////////////////////////////////////////////////////

        long i=1;
    	String warcode;
        long codefar;
        
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
        	
		    String projpath=System.getProperty("user.dir");
		    projpath=projpath.substring(0, projpath.length()-3);
			file = new File(projpath+"\\Report_Zain Kuwait\\14-06\\InventoryReport.csv");
			fis = null;
			 bis = null;
			 dis = null;

        	fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
	         //Connection con = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:alm","alm","alm");
			Connection con = DriverManager.getConnection (str,struser,strpwd);
     
	         System.out.println(dis.readLine());
			while (dis.available() != 0) {
				String data = dis.readLine();
				System.out.println(data);
				String [] columnDetail ;
				
				columnDetail = data.split(",", -1);
				//System.out.println(columnDetail);
				System.out.println("line id " + i +"  length\t" + "item code:  "+ "   " +columnDetail[31] );

				String category = columnDetail[20];
				String L1 = columnDetail[21];
				String L2 = columnDetail[22];
				String L3 = columnDetail[23];
				String L4 = columnDetail[24];
				
				if (StringUtils.equalsIgnoreCase(category, ""))
				{
					category = "GN-NW-HW-INV";
					L1 = "General";
					L2 = "Network";
					L3 = "Hardware";
					L4 = "Inventory Asset";
				}
					PreparedStatement stmt = con.prepareStatement("insert into DM_INVENTORY values('" + columnDetail[1] + "','" + columnDetail[2] + "','" + columnDetail[3] + "','" + columnDetail[4] + "','" + columnDetail[5] + "','" + columnDetail[6] + "','" + columnDetail[7] + "','" + columnDetail[8] + "','" + columnDetail[9] + "','" + columnDetail[10] + "','" + columnDetail[11] + "','" + columnDetail[12] + "','" + columnDetail[13] + "','" + columnDetail[15] + "','" + columnDetail[16] + "','" + columnDetail[17] + "','" + columnDetail[18] + "','" + category + "','" + L1 + "','" +L2 + "','" + L3 + "','" + L4 + "','" + columnDetail[25] + "','" + columnDetail[26] + "','" + columnDetail[27] + "','" + columnDetail[28] + "','" + columnDetail[29] + "','" + columnDetail[30] +"','" + columnDetail[31] +"', '', '', '', '', '', '', '')");
					System.out.println("DM_INVENTORY  is :   " + "insert into DM_INVENTORY values('" + columnDetail[1] + "','" + columnDetail[2] + "','" + columnDetail[3] + "','" + columnDetail[4] + "','" + columnDetail[5] + "','" + columnDetail[6] + "','" + columnDetail[7] + "','" + columnDetail[8] + "','" + columnDetail[9] + "','" + columnDetail[10] + "','" + columnDetail[11] + "','" + columnDetail[12] + "','" + columnDetail[13] + "','" + columnDetail[15] + "','" + columnDetail[16] + "','" + columnDetail[17] + "','" + columnDetail[18] + "','" + category + "','" + L1 + "','" + L2 + "','" + L3 + "','" + L4 + "','" + columnDetail[25] + "','" + columnDetail[26] + "','" + columnDetail[27] + "','" + columnDetail[28] + "','" + columnDetail[29] + "','" + columnDetail[30] +"','" + columnDetail[31] +"', '', '', '', '', '', '', '')");
					stmt.executeUpdate();
			        stmt.close();
				
				i=i+1;
				
			}
			fis.close();
			bis.close();
			dis.close();
			
			PreparedStatement stmt = con.prepareStatement("update DM_INVENTORY set L4 = 'Cabinet - Racks' where l4 = 'Cabinet  Racks' "
	        		+ "and FA_CATEGORY = 'NW-RAN-SRN-CAB'");
	        
	        stmt.executeUpdate();
	        stmt.close();
	        
	        
	        stmt = con.prepareStatement("update DM_INVENTORY set L4 = 'Cabinet - Racks' where l4 = 'Cabinet  Racks' "
	        		+ "and FA_CATEGORY = 'NW-RAN-LAM-CAB'");
	        stmt.executeUpdate();
	        stmt.close();

	        stmt = con.prepareStatement("update DM_INVENTORY set L4 = 'Cabinet - Racks' where l4 = 'Cabinet  Racks' "
	        		+ "and FA_CATEGORY = 'NW-CON-MGW-CAB'");
	        stmt.executeUpdate();
	        stmt.close();
	        
	        System.out.println("Update Completed");

			con.close();
			System.out.println("i  is :   " + (i-1));
			System.out.println("COMPLETED");
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
