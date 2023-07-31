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

public class DM_POITEM_FROM_DM_FINANCIAL {
	
	public static void main(String[] args) {

		String min = null;
        String itmcode=null;
        String itmname=null;
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
	         Statement stmt3 = null;
	         Statement stmt4=null;
	         Statement stmt5=null;
	         Statement stmt6=null;
	         Statement stmttype = null;
	         ResultSet rs6 = null;
	         PreparedStatement stmtpo = null;
	         Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
            
				 // Get all distinct POl
	            
	            String query1 = "select distinct ponumber from DM_FINANCIAL";  
	            stmttype = con.createStatement();
	            ResultSet rs1 = stmttype.executeQuery(query1);
	 
			while (rs1.next()) {
				itmcode=null;
				min=null;	    
							//Get item_code from item table if exist based on fa_category and model
							String query2 = "select  a.item_code,a.item_name,b.assetmodel,b.fa_category,b.ponumber,b.VENDOR_NAME,b.INITIALCOST, b.SITEID from item a ,DM_FINANCIAL b  where a.item_model= b.assetmodel and a.item_cat_code= b.fa_category and b.ponumber='" + rs1.getString("PONUMBER") +"'";  
				            stmt2 = con.createStatement();
				            ResultSet rs2 = stmt2.executeQuery(query2);
				            
				            while (rs2.next()) {
				            	min=null;
				            	itmcode= rs2.getString("item_code"); 
				            	itmname= rs2.getString("item_name"); 
				            	System.out.println( itmcode + ":"+rs2.getString("fa_category")+ ":"+rs2.getString("assetmodel") );
							
				            

								System.out.println("insert into DM_PURCHASE_ORDER_ITEM (CREATION_DATE,LAST_MODIFIED_DATE,ITEM_CODE,ITEM_NAME,RATE,DISCOUNT_AMOUNT,DISCOUNT_PERCENT,NET_RATE,"
										+ "TAX1,TAX2,TOTAL,TOTAL_AT,GR_QTY,PR_QTY,AR_QTY,FAR_QTY,QTY,PO_ID,DAR_QTY,CIP_QTY) values(sysdate,sysdate,'"+ itmcode +"','"+ itmname +"'," + rs2.getDouble("INITIALCOST") +" ,\r\n"+
										 "0,0," + rs2.getDouble("INITIALCOST") +",0,0," + rs2.getDouble("INITIALCOST") +"," + rs2.getDouble("INITIALCOST") +",\r\n"+
										 "'DM','DM','0','0',1,'" + rs2.getString("ponumber") +"','0','0' )");	
									

				            	
				            	//Insert into table DM_PO_ITEM 
								PreparedStatement stmt = con.prepareStatement("insert into DM_PURCHASE_ORDER_ITEM (CREATION_DATE,LAST_MODIFIED_DATE,ITEM_CODE,ITEM_NAME,RATE,DISCOUNT_AMOUNT,DISCOUNT_PERCENT,NET_RATE," + 
										"TAX1,TAX2,TOTAL,TOTAL_AT,GR_QTY,PR_QTY,AR_QTY,FAR_QTY,QTY,PO_ID,DAR_QTY,CIP_QTY) values(sysdate,sysdate,'"+ itmcode +"','"+ itmname +"'," + rs2.getDouble("INITIALCOST") +" ,"+
										 "0,0," + rs2.getDouble("INITIALCOST") +",0,0," + rs2.getDouble("INITIALCOST") +"," + rs2.getDouble("INITIALCOST") +","+
										 "'DM','DM','0','0',1,'" + rs2.getString("ponumber") +"','0','0')"); 
					            stmt.executeUpdate();
						        stmt.close();




				            
				            
				            }
							rs2.close();
							stmt2.close();
	     
		}
		rs1.close();

		stmttype.close();
			
			con.close();
			System.out.println("DM_POITEM_FROM_DM_FINANCIAL  COMPLETED");
	        }   
        		
      
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
		}

}
