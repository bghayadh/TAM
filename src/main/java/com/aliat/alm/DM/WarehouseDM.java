package com.aliat.alm.DM;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.lang.Long;


public class WarehouseDM {
	
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
        	
		    String projpath=System.getProperty("user.dir");
		    projpath=projpath.substring(0, projpath.length()-3);
			file = new File(projpath+"\\Report_Zain Kuwait\\14-06\\newSite.csv");
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
				//System.out.println(columnDetail);
				System.out.println("line id " + i +"  length\t" + columnDetail.length + "  columns 26: " + columnDetail[26] + "  columns 33: " + columnDetail[33]  + "  columns 34: " + columnDetail[34]  + "  columns 38: " + columnDetail[38] );
		
					Object a=columnDetail[0];
					Object b=columnDetail[1];  // Warehouse Name
					Object b1=columnDetail[2];
					Object b2=columnDetail[3];
					Object cc=columnDetail[4];
					Object c5=columnDetail[11];
					Object c6=columnDetail[26];
					Object c7=columnDetail[33];
					Object c8=columnDetail[34];
					Object c9=columnDetail[38];
					Object cvend=columnDetail[25];
					Object cid=columnDetail[37];
					Object ctype=columnDetail[32];
					Object cwith=columnDetail[34];
					
				
				
					String dd=(String )cwith;	
					//System.out.println("Shelter is "+ dd.length());
					if (dd.length() >0 ) {
						dd="1";
						} else {dd="0";}
					
					
					String getRepeatedWare = "select count(*) as count from DM_WAREHOUSE where ware_name = '"+b+"'";
					Statement stmnt = con.createStatement();
		            ResultSet queryRes = stmnt.executeQuery(getRepeatedWare);
		            
		            int Count = 0;
		            while (queryRes.next()) {
		            	
		            	Count = queryRes.getInt("count");
		            	System.out.println("Warehouse Count is DM_WAREHOUSE is: "+Count);
		            	
					}
		            queryRes.close();
		            stmnt.close();
		            
		            if(Count == 0)
		            {
						PreparedStatement stmt = con.prepareStatement("insert into DM_WAREHOUSE values('" + i + "',sysdate,sysdate,'" + b + "','-','" + cc +"','-','" + b1 +"' ,'" + b2 +"','1', '" + a +"','" + c5 +"','" + c6 +"','" + c7 +"','" + c9 +"','" + c9 +"','" + cid + "','" + ctype +"','" + dd +"','" + cvend +"')");
						System.out.println("WarehouseDM stament  is :   " + "insert into DM_WAREHOUSE values('" + i + "',sysdate,sysdate,'" + b + "','-','" + cc +"','-','" + b1 +"' ,'" + b2 +"','1', '" + a +"','" + c5 +"','" + c6 +"','" + c7 +"','" + c9 +"','" + c9 +"','" + cid + "','" + ctype +"','" + dd +"','" + cvend +"')");
						stmt.executeUpdate();
				        stmt.close();
				        
				        i=i+1;
		            }

			}
			fis.close();
			bis.close();
			dis.close();
			
			
			PreparedStatement updtmt = con.prepareStatement("update DM_WAREHOUSE set ADDRESS ='-' where ADDRESS is null");
			updtmt.executeUpdate();
			updtmt.close();
			
			PreparedStatement updtmt2 = con.prepareStatement("update DM_WAREHOUSE set LOCATION_NOTES ='-' where LOCATION_NOTES is null");
			updtmt2.executeUpdate();
			updtmt2.close();
			
			PreparedStatement updtmt3 = con.prepareStatement("update DM_WAREHOUSE set SITE_HEIGHT ='-' where SITE_HEIGHT is null");
			updtmt3.executeUpdate();
			updtmt3.close();
			
			PreparedStatement updtmt4 = con.prepareStatement("update DM_WAREHOUSE set SHELTER ='-' where SHELTER is null");
			updtmt4.executeUpdate();
			updtmt4.close();
			
			PreparedStatement updtmt5 = con.prepareStatement("update DM_WAREHOUSE set TOWER_HEIGHT ='-' where TOWER_HEIGHT is null");
			updtmt5.executeUpdate();
			updtmt5.close();
			
			PreparedStatement updtmt6 = con.prepareStatement("update DM_WAREHOUSE set SHELTER_ID ='-' where SHELTER_ID is null");
			updtmt6.executeUpdate();
			updtmt6.close();
			
			PreparedStatement updtmt7 = con.prepareStatement("update DM_WAREHOUSE set SHELTER_TYPE ='-' where SHELTER_TYPE is null");
			updtmt7.executeUpdate();
			updtmt7.close();
			
			PreparedStatement updtmt8 = con.prepareStatement("update DM_WAREHOUSE set SHELTER_VENDOR ='-' where SHELTER_VENDOR is null");
			updtmt8.executeUpdate();
			updtmt8.close();
	
			con.close();
			System.out.println("Insert into DM_WAREHOUSE COMPLETED");
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
