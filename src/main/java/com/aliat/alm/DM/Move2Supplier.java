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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Move2Supplier {
	
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
                     

			
			
			// insert into Supplier
						String query2= "(select VENDOR_NUMBER,SUPPLIER_NAME,SUPPLIER_DESCRIPTION,SUPPLIER_CATEGORY,WEBSITE,COUNTRY,TAX_ID,CREDIT_LIMIT,ADDRESS_1,ADDRESS_2,PHONE,MOBILE,EMAIL,CONTACT_PERSON,DISABLED from DM_SUPPLIER)\r\n" + 
								"minus \r\n" + 
								"(select VENDOR_NUMBER,SUPPLIER_NAME,SUPPLIER_DESCRIPTION,SUPPLIER_CATEGORY,WEBSITE,COUNTRY,TAX_ID,CREDIT_LIMIT,ADDRESS_1,ADDRESS_2,PHONE,MOBILE,EMAIL,CONTACT_PERSON,DISABLED from SUPPLIER)";
			            Statement stmt20 = con.createStatement();
			            ResultSet rs2 = stmt20.executeQuery(query2);
			 
			            while (rs2.next()) {
			            	
			            ////Get sequence number for Supplier Table
					         Statement stmtfar = null;
					         String query = "select SUPPLIER_SEQ.nextval as nbr from dual";  
							 stmtfar = con.createStatement();
					            ResultSet rs3 = stmtfar.executeQuery(query);
					            
					            while (rs3.next()) {
					            	min= rs3.getString("nbr");   
					            	min=  "SUPP_"+ year +"_"+ min;
								}
								rs3.close();
								stmtfar.close();
					
					
								PreparedStatement stmt4 = con.prepareStatement("insert into SUPPLIER (SUPPLIER_ID,CREATION_DATE,LAST_MODIFIED_DATE,VENDOR_NUMBER,SUPPLIER_NAME,SUPPLIER_DESCRIPTION,SUPPLIER_CATEGORY,WEBSITE,COUNTRY,TAX_ID,CREDIT_LIMIT,ADDRESS_1,ADDRESS_2,PHONE,MOBILE,EMAIL,CONTACT_PERSON,DISABLED) "
										+ " values ('" + min + "',sysdate,sysdate,'" + rs2.getString("VENDOR_NUMBER") +"' ,'" + rs2.getString("SUPPLIER_NAME") +"','" + rs2.getString("SUPPLIER_DESCRIPTION") +"','" + rs2.getString("SUPPLIER_CATEGORY") +"','" + rs2.getString("WEBSITE") +"' ,'" + rs2.getString("COUNTRY") +"','" + rs2.getString("TAX_ID") +"'," + rs2.getString("CREDIT_LIMIT") +",'" + rs2.getString("ADDRESS_1") +"','" + rs2.getString("ADDRESS_2") +"','" + rs2.getString("PHONE") +"','" + rs2.getString("MOBILE") +"','" + rs2.getString("EMAIL") +"','" + rs2.getString("CONTACT_PERSON") +"','" + rs2.getString("DISABLED") +"' )");

								System.out.println("insert into SUPPLIER (SUPPLIER_ID,CREATION_DATE,LAST_MODIFIED_DATE,VENDOR_NUMBER,SUPPLIER_NAME,SUPPLIER_DESCRIPTION,SUPPLIER_CATEGORY,WEBSITE,COUNTRY,TAX_ID,CREDIT_LIMIT,ADDRESS_1,ADDRESS_2,PHONE,MOBILE,EMAIL,CONTACT_PERSON,DISABLED) values ('" + min + "',sysdate,sysdate,'" + rs2.getString("VENDOR_NUMBER") +"' ,'" + rs2.getString("SUPPLIER_NAME") +"','" + rs2.getString("SUPPLIER_DESCRIPTION") +"','" + rs2.getString("SUPPLIER_CATEGORY") +"','" + rs2.getString("WEBSITE") +"' ,'" + rs2.getString("COUNTRY") +"','" + rs2.getString("TAX_ID") +"'," + rs2.getString("CREDIT_LIMIT") +",'" + rs2.getString("ADDRESS_1") +"','" + rs2.getString("ADDRESS_2") +"','" + rs2.getString("PHONE") +"','" + rs2.getString("MOBILE") +"','" + rs2.getString("EMAIL") +"','" + rs2.getString("CONTACT_PERSON") +"','" + rs2.getString("DISABLED") +"' )");
								stmt4.executeUpdate();
						        stmt4.close();
			            	
			            }
			            rs2.close();
						stmt20.close();
				

			con.close();
			System.out.println("SUPPLIER COMPLETED");
	        }   
        		
      
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
			}

}
