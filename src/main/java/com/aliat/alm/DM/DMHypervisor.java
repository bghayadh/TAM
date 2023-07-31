package com.aliat.alm.DM;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.commons.lang3.StringUtils;

public class DMHypervisor {
	
	public static void main(String[] args) {

		/////////////////////////////////////////////////////// data.split("\\|", -1);
		///////REM to remove all empty fields and to remove ' and , and & from excel sheet 
		////////////////////////////////////////////////////////
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
        	
		    String projpath=System.getProperty("user.dir");
		    projpath=projpath.substring(0, projpath.length()-3);
			file = new File(projpath+"\\Report_Zain Kuwait\\14-06\\IT Asset Category wise\\DMHypervisor.csv");
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
				System.out.println("line id " + i +"  length\t" + "category:  "+ "   " +columnDetail[0] );
				String cat= columnDetail[1];
				//System.out.println("ID LENGTH IS : "+cat.length());
				
				if (cat.length()== 0 ) {cat="DMHypervisor";}
			    else
			    	{cat= "H-"+cat;}
				
				String category = columnDetail[11];
				String L1 = columnDetail[12];
				String L2 = columnDetail[13];
				String L3 = columnDetail[14];
				String L4 = columnDetail[15];
				
				if (StringUtils.equalsIgnoreCase(category, ""))
				{
					category = "GN-NW-HW-IT";
					L1 = "General";
					L2 = "Network";
					L3 = "Hardware";
					L4 = "IT Asset";
				}
						           
				    
				     
					PreparedStatement stmt = con.prepareStatement("insert into DM_Hypervisor values('" + cat  + "','" + columnDetail[1] + "',sysdate,sysdate,'0','DMHypervisor','" + columnDetail[29] + "','" + columnDetail[1] + "','0','0','0','0','0','0','0','0','0','0',sysdate,'" + columnDetail[6] + "','0','DM','" + columnDetail[61] + "','0','0','0','0','" + columnDetail[46] + "','0','" + columnDetail[38] + "','0','" + category + "','" + columnDetail[17] + "','" + columnDetail[16] + "','" + columnDetail[18] + "','IT','" + columnDetail[51] + "','" + columnDetail[52] + "','" + columnDetail[53] + "','" + columnDetail[54] + "','" + columnDetail[55] + "',\r\n"+
							 "'" + columnDetail[58] + "','" + columnDetail[9] + "','0','0','" + columnDetail[3] + "','" + columnDetail[20] + "','0',sysdate,'0','0','0','0','0','0','0','0','" + columnDetail[0] + "','" + columnDetail[39] + "', '"+columnDetail[19]+"', '','','','','','','','"+category+"','"+L1+"','"+L2+"','"+L3+"','"+L4+"')");
					
					
					System.out.println("insert into DM_Hypervisor values('" + cat  + "','" + columnDetail[1] + "',sysdate,sysdate,'0','DMHypervisor','" + columnDetail[29] + "','" + columnDetail[1] + "','0','0','0','0','0','0','0','0','0','0',sysdate,'" + columnDetail[6] + "','0','DM','" + columnDetail[61] + "','0','0','0','0','" + columnDetail[46] + "','0','" + columnDetail[38] + "','0','" + category + "','" + columnDetail[17] + "','" + columnDetail[16] + "','" + columnDetail[18] + "','IT','" + columnDetail[51] + "','" + columnDetail[52] + "','" + columnDetail[53] + "','" + columnDetail[54] + "','" + columnDetail[55] + "',\r\n"+
							 "'" + columnDetail[58] + "','" + columnDetail[9] + "','0','0','" + columnDetail[3] + "','" + columnDetail[20] + "','0',sysdate,'0','0','0','0','0','0','0','0','" + columnDetail[0] + "','" + columnDetail[39] + "', '"+columnDetail[19]+"', '','','','','','','','"+category+"','"+L1+"','"+L2+"','"+L3+"','"+L4+"')");
					stmt.executeUpdate();
			        stmt.close();
				
				i=i+1;
				
			}
			
			fis.close();
			bis.close();
			dis.close();
			
			
			
			con.close();
			System.out.println("i  is :   " + i);
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
