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

public class DM_POITEM_TO_PO_ITEM {
	
	public static void main(String[] args) {

		String min = null;
        String itmcode=null;
        String itmname=null;
        float Sqty=0;
        float Stot=0;
        float Srate=0;
       // String siteID = null;
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
	         PreparedStatement stmt=null;
	         ResultSet rs6 = null;
	         PreparedStatement stmtpo = null;
	         Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
            
				 // Get all distinct ponumber from DM_ POl
	            
	            String query1 = "select distinct po_id from DM_PURCHASE_ORDER_ITEM";  
	            stmttype = con.createStatement();
	            ResultSet rs1 = stmttype.executeQuery(query1);
	 
			while (rs1.next()) {
				itmcode=null;
				min=null;	    
							//Get item_code from DM_PURCHASE_ORDER_ITEM table 
							String query2 = "select distinct item_code,ITEM_NAME  from DM_PURCHASE_ORDER_ITEM  where po_id ='" + rs1.getString("po_id") +"'";  
				            stmt2 = con.createStatement();
				            ResultSet rs2 = stmt2.executeQuery(query2);
				            
				            while (rs2.next()) {
				            	min=null;
				            	Sqty=0;
				            	Stot=0;
				            	Srate=0;
				            	itmcode=null;
				            	itmname=null;
				          
									itmcode= rs2.getString("ITEM_CODE"); 
					            	itmname= rs2.getString("ITEM_NAME"); 
					            	//siteID = rs2.getString("SITE_ID");
				            	
					            	String getItemdetails = "select ITEM_MODEL, ITEM_PART_NUMBER FROM ITEM where item_code ='"+rs2.getString("ITEM_CODE")+"'";
						            Statement stmtQ = con.createStatement();
						            ResultSet rsQuery = stmtQ.executeQuery(getItemdetails);
						            String model = null;
						            String partNo = null;
						            while (rsQuery.next()) {
						            	model = rsQuery.getString("ITEM_MODEL");
						            	partNo = rsQuery.getString("ITEM_PART_NUMBER");
						            }
						            stmtQ.close();
						            rsQuery.close();
						            
					            	System.out.println("PoID is: "+rs1.getString("po_id")+" and Item Code is: "+rs2.getString("ITEM_CODE"));
								////Get count(qty) and sum(total) from DM_ PO_ITEM Table based on Item_code
									 String query5 = "select  count(qty) as Sqty ,sum(total) as Stot , sum(total)/count(qty) as Srate from DM_PURCHASE_ORDER_ITEM  where po_id ='" + rs1.getString("po_id") +"' and item_code='" + rs2.getString("item_code") +"' ";  
							            stmt5 = con.createStatement();
							            ResultSet rs5 = stmt5.executeQuery(query5);
							            
							            while (rs5.next()) {
							            	Sqty= Float.parseFloat(rs5.getString("Sqty")); 
							            	Stot= Float.parseFloat(rs5.getString("Stot")); 
							            	Srate= Float.parseFloat(rs5.getString("Srate")); 
							            	
										}
										rs5.close();
										stmt5.close();	
				            	
				            	
							
				            ////Get sequence number for PO_ITEM Table
								 String query3 = "select PURCHASEORDERITEM_SEQ.nextval as nbr from dual";  
						            stmt3 = con.createStatement();
						            ResultSet rs3 = stmt3.executeQuery(query3);
						            
						            while (rs3.next()) {
						            	min= rs3.getString("nbr");     
									}
									rs3.close();
									stmt3.close();
									min="PRO_ITEM_"+year+"_"+min;


								System.out.println("insert into PURCHASE_ORDER_ITEM (PO_ITEM_ID,CREATION_DATE,LAST_MODIFIED_DATE,ITEM_CODE,ITEM_NAME,RATE,DISCOUNT_AMOUNT"
										+ ",DISCOUNT_PERCENT,NET_RATE,TAX1,TAX2,TOTAL,TOTAL_AT,GR_QTY,PR_QTY,AR_QTY,FAR_QTY,QTY,PO_ID,DAR_QTY,CIP_QTY,ITEM_MODEL,ITEM_PART_NUMBER,PO_ITEM_STATUS) values('"+ min +"',sysdate,sysdate,'"+ itmcode +"','"+ itmname +"'," + Srate +" ,"+
										 "0,0," + Srate +",0,0," + Stot +"," + Stot +",\r\n"+
										 "'DM','DM','0','0'," + Sqty +",'" + rs1.getString("PO_ID") +"','0','0','"+model+"','"+partNo+"','1' )");	
									

				            	
				            	//Insert into table PO_ITEM 
								stmt = con.prepareStatement("insert into PURCHASE_ORDER_ITEM (PO_ITEM_ID,CREATION_DATE,LAST_MODIFIED_DATE,ITEM_CODE,ITEM_NAME,RATE,DISCOUNT_AMOUNT"
										 +",DISCOUNT_PERCENT,NET_RATE,TAX1,TAX2,TOTAL,TOTAL_AT,GR_QTY,PR_QTY,AR_QTY,FAR_QTY,QTY,PO_ID,DAR_QTY,CIP_QTY,ITEM_MODEL,ITEM_PART_NUMBER,PO_ITEM_STATUS) values('"+ min +"',sysdate,sysdate,'"+ itmcode +"','"+ itmname +"'," + Srate +" ,"+
										 "0,0," + Srate +",0,0," + Stot +"," + Stot +",\r\n"+
										 "'DM','DM','0','0'," + Sqty +",'" + rs1.getString("PO_ID") +"','0','0','"+model+"','"+partNo+"','1' )"); 
					            stmt.executeUpdate();
					            stmt.close();
					            
					            
					            System.out.println("update FIXED_ASSET_REGISTRY set PO_ITEM_ID = '"+ min +"' where po_id='"+ rs1.getString("PO_ID") +"' and ITEM_CODE='"+rs2.getString("ITEM_CODE")+"'");
				    			PreparedStatement updtmt9 = con.prepareStatement("update FIXED_ASSET_REGISTRY set PO_ITEM_ID = '"+ min +"' where po_id='"+ rs1.getString("PO_ID") +"' and ITEM_CODE='"+rs2.getString("ITEM_CODE")+"'");
					    		updtmt9.executeUpdate();
					    		updtmt9.close();
					    		
					    		
					    		System.out.println("update ASSET_REGISTRY set PO_ITEM_ID = '"+ min +"' where po_id='"+ rs1.getString("PO_ID") +"' and ITEM_CODE='"+rs2.getString("ITEM_CODE")+"'");
				    			PreparedStatement updtmt10 = con.prepareStatement("update ASSET_REGISTRY set PO_ITEM_ID = '"+ min +"' where po_id='"+ rs1.getString("PO_ID") +"' and ITEM_CODE='"+rs2.getString("ITEM_CODE")+"'");
				    			updtmt10.executeUpdate();
				    			updtmt10.close();




				            
				            
				            }
							rs2.close();
							stmt2.close();
	     
		}
		rs1.close();

		stmttype.close();
			
			con.close();
			System.out.println("DM_POITEM_TO_PO_ITEM COMPLETED");
	        }   
        		
      
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
		}


}
