package com.aliat.alm.DM;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.lang.Long;

public class FinancialDM {
	
	public static void main(String[] args) {

		/////////////////////////////////////////////////////// data.split("\\|", -1);
		///////REM to remove all empty fields and to remove ' and , and & from excel sheet  & |
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
			file = new File(projpath+"\\Report_Zain Kuwait\\14-06\\FinancialReport.csv");
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
				Object [] columnDetail ;
				
				columnDetail = data.split(",", -1);

					PreparedStatement stmt = con.prepareStatement("insert into DM_FINANCIAL values('" + columnDetail[0] + "','" + columnDetail[1] + "','" + columnDetail[2] + "','" + columnDetail[3] + "','" + columnDetail[4] + "','" + columnDetail[5] + "','" + columnDetail[6] + "','" + columnDetail[7] + "','" + columnDetail[9] + "','" + columnDetail[10] + "','" + columnDetail[11] + "','" + columnDetail[12] + "','" + columnDetail[13] + "','" + columnDetail[14] + "','" + columnDetail[15] + "','" + columnDetail[17] + "','" + columnDetail[18] + "','" + columnDetail[19] + "','" + columnDetail[20] + "','" + columnDetail[21] + "','" + columnDetail[22] + "','" + columnDetail[23] + "','" + columnDetail[24] + "','" + columnDetail[25] + "','" + columnDetail[26] + "','" + columnDetail[27] +"','" + columnDetail[28] +"')");
					System.out.println("DM_FINANCIAL is :   " + "insert into DM_FINANCIAL values('" + columnDetail[0] + "','" + columnDetail[1] + "','" + columnDetail[2] + "','" + columnDetail[3] + "','" + columnDetail[4] + "','" + columnDetail[5] + "','" + columnDetail[6] + "','" + columnDetail[7] + "','" + columnDetail[9] + "','" + columnDetail[10] + "','" + columnDetail[11] + "','" + columnDetail[12] + "','" + columnDetail[13] + "','" + columnDetail[14] + "','" + columnDetail[15] + "','" + columnDetail[17] + "','" + columnDetail[18] + "','" + columnDetail[19] + "','" + columnDetail[20] + "','" + columnDetail[21] + "','" + columnDetail[22] + "','" + columnDetail[23] + "','" + columnDetail[24] + "','" + columnDetail[25] + "','" + columnDetail[26] + "','" + columnDetail[27] +"','" + columnDetail[28] +"')");
					stmt.executeUpdate();
			        stmt.close();
				
				i=i+1;
				
			}
			
				fis.close();
				bis.close();
				dis.close();
				
				
				PreparedStatement stmt = con.prepareStatement("update DM_FINANCIAL set l1= 'Network', l2 = 'Radio Access network', "
						+ "l3 = 'Antenna', l4 = 'Active Hardware', FA_CATEGORY='NW-RAN-ANT-AH' where L1 = 'Active Hardware'");
				stmt.executeUpdate();
		        stmt.close();
				
				stmt = con.prepareStatement("update DM_FINANCIAL set L4 = 'Video surveillance as a serviceÿ' "
		        		+ "where l4 = 'Video surveillance as a service?ÿ' and FA_CATEGORY = 'NW-SRC-SYS-VSS'");
		        
		        stmt.executeUpdate();
		        stmt.close();
		        
		        
		        stmt = con.prepareStatement("update DM_FINANCIAL set L4 = 'Setup - Installation' where l4 = 'Setup  Installation' "
		        		+ "and FA_CATEGORY = 'NW-SRC-RAS-STP'");
		        
		        stmt.executeUpdate();
		        stmt.close();
		        
		        
		        stmt = con.prepareStatement("update DM_FINANCIAL set L4 = 'Setup - Installation' where l4 = 'Setup  Installation' "
		        		+ "and FA_CATEGORY = 'NW-SRC-TRM-STP'");
		        stmt.executeUpdate();
		        stmt.close();
		        
		        
		        stmt = con.prepareStatement("update DM_FINANCIAL set L4 = 'Setup - Installation' where l4 = 'Setup  Installation' "
		        		+ "and FA_CATEGORY = 'NW-SRC-CNS-STP'");
		        stmt.executeUpdate();
		        stmt.close();
		        
		        
		        stmt = con.prepareStatement("update DM_FINANCIAL set L4 = 'Setup - Installation' where l4 = 'Setup  Installation' "
		        		+ "and FA_CATEGORY = 'NW-RAN-ANT-STP'");
		        stmt.executeUpdate();
		        stmt.close();
		        
		        stmt = con.prepareStatement("update DM_FINANCIAL set L4 = 'Cabinet - Racks' where l4 = 'Cabinet  Racks' "
		        		+ "and FA_CATEGORY = 'NW-RAN-SRN-CAB'");
		        
		        stmt.executeUpdate();
		        stmt.close();
		        
		        
		        stmt = con.prepareStatement("update DM_FINANCIAL set L4 = 'Cabinet - Racks' where l4 = 'Cabinet  Racks' "
		        		+ "and FA_CATEGORY = 'NW-RAN-LAM-CAB'");
		        stmt.executeUpdate();
		        stmt.close();
		        
		        
		        stmt = con.prepareStatement("update DM_FINANCIAL set L4 = 'Cabinet - Racks' where l4 = 'Cabinet  Racks' "
		        		+ "and FA_CATEGORY = 'NW-CON-VEP-CAB'");
		        stmt.executeUpdate();
		        stmt.close();
		        
		        stmt = con.prepareStatement("update DM_FINANCIAL set L4 = 'Cabinet - Racks' where l4 = 'Cabinet  Racks' "
		        		+ "and FA_CATEGORY = 'NW-CON-MGW-CAB'");
		        stmt.executeUpdate();
		        stmt.close();
		        
		        
		        stmt = con.prepareStatement("update DM_FINANCIAL set L3 = 'Radio Access network Services' where L3 like '%----%' "
		        		+ "and FA_CATEGORY ='NW-SRC-RAS-STP'");
		        
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
