package com.aliat.alm.models;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.lang.Long;

public class readStreamFile
 {

	public static void main(String[] args) {

		///////////////////////////////////////////////////////
		///////REM to remove all , and all ' from excel sheet and to extend filed size of item code and name 300 Varchar in DB
		////////////////////////////////////////////////////////
		File file = new File("C:\\parser\\F11.csv");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
        long i=1;
    	String farcode;
        long codefar;
        try{
        	fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
	         Connection con = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:alm","alm","alm");
	         
	         Statement stmtfar = null;
	         String query = "select FIXED_ASSET_REG_ID from TABLE_IDS ";
	             
	             // get FAR CODE 
	        	 stmtfar = con.createStatement();
	             ResultSet rs = stmtfar.executeQuery(query);
	             rs.next();
	             farcode = rs.getString("FIXED_ASSET_REG_ID");  
	             //System.out.println("FAR CODE IS :  " + farcode);
	             codefar = Long.parseLong(farcode.substring(9, farcode.length()));
	             farcode =farcode.substring(0, 9);
	            // System.out.println("FAR SUBSTRING CODE IS :  " + farcode);
	             stmtfar.close();
	             
	            
	     	         
	         System.out.println(dis.readLine());
			while (dis.available() != 0) {
				String data = dis.readLine();
				System.out.println(data);
				Object [] columnDetail ;
				
				columnDetail = data.split(",");
				System.out.println("line id " + i +"  length\t" + columnDetail.length + "  columns 2: " + columnDetail[2] + "  columns 3: " + columnDetail[3] + "  columns 9: " + columnDetail[9]);
				
					Object a=columnDetail[2];
					Object b=columnDetail[3];
					Object cc=columnDetail[9];
					String d= farcode + codefar;
					PreparedStatement stmt = con.prepareStatement("insert into Test values('" + a + "','" + b + "','" + cc +"','" + d +"')");
					System.out.println("stament  is :   " + "insert into Test values('" + a + "','" + b + "','" + cc +"','" + d +"')");
					stmt.executeUpdate();
			        stmt.close();
				
				i=i+1;
				codefar= codefar +1;
			}
			
			 // update FAR CODE 
			query = "update TABLE_IDS set FIXED_ASSET_REG_ID = '"+ farcode + codefar +"'";
			//System.out.println(query);
       	 	stmtfar = con.createStatement();
            ResultSet rs2 = stmtfar.executeQuery(query);
            stmtfar.close();
			
			
			
			con.close();
			System.out.println("i  is :   " + i);
	        }   
        		
        catch (IOException e) {
				e.printStackTrace();
			} 
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
           finally {
				try {
					fis.close();
					bis.close();
					dis.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
     
	   }
	   
        
		
}