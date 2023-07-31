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

public class SupplierDM {
	public static void main(String[] args) {

		
		
        long i=1;
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
	           Statement stmt2 = null;
	                      
             
	             String query1 = "SELECT distinct VENDOR_NUMBER,VENDOR_NAME from DM_FINANCIAL where VENDOR_NUMBER != '0'";  
	             stmt2 = con.createStatement();
	             ResultSet rs = stmt2.executeQuery(query1);
      
	             while (rs.next()) {
	            	 
	            	 	String getRepeatedSuppliers = "select count(*) as count from DM_SUPPLIER where supplier_name = '"+rs.getString("VENDOR_NAME")+"'";
						Statement stmnt = con.createStatement();
			            ResultSet queryRes = stmnt.executeQuery(getRepeatedSuppliers);
			            
			            int Count = 0;
			            while (queryRes.next()) {
			            	
			            	Count = queryRes.getInt("count");
			            	
						}
			            queryRes.close();
			            stmnt.close();
			            
			            if(Count == 0)
			            {
			            	
			            	System.out.println("DM_SUPPLIER " + rs.getString("VENDOR_NUMBER") +":"+ rs.getString("VENDOR_NAME"));
				

							PreparedStatement stmt = con.prepareStatement("insert into DM_SUPPLIER values('" + i + "',sysdate,sysdate,'" + rs.getString("VENDOR_NUMBER") + "','" + rs.getString("VENDOR_NAME") +"','-','-','-','-','-','0','-','DM','-','-','-','-','0')");
							System.out.println("SUPPLIER stament  is :   " + "insert into DM_SUPPLIER values('" + i + "',sysdate,sysdate,'" + rs.getString("VENDOR_NUMBER") + "','" + rs.getString("VENDOR_NAME") +"','-','-','-','-','-','0','-','DM','-','-','-','-','0')");
							stmt.executeUpdate();
					        stmt.close();
					        i=i+1;
			            }
				
				

			}
			rs.close();
			stmt2.close();
			
			
		
				

			con.close();
			System.out.println("DM_SUPPLIER COMPLETED");
	        }   
        		
      
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
			}
     
	   }



