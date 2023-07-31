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

public class DM_PO_FROM_PURCHASE_ORDER_ITEM {
	
	public static void main(String[] args) {

		String min = null;
        String itmcode=null;
        Statement stmt4=null;
        Statement stmt5=null;
        Statement stmt6=null;
        Statement stmt7=null;
        Statement stmttype = null;
        ResultSet rs6 = null;
        ResultSet rs7 = null;
        PreparedStatement stmtpo = null;
        Date date = new Date();
        String SuppName= null;
        String SuppID= null;
        String SuppAddrs= null;
        float Sqty= 0; 
        float Stot= 0; 
        float Sdiscamnt= 0; 
        float Sdiscperamnt= 0; 
        String siteID = null;
        String str = null;
	     String pass=null;
	     String strpwd = null;
	     String struser = null;
        
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
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
	         
	     ////Get sequence number for PO Table

	        

	         // CREATE PO from PO_ITEM
	         String query4 = "select distinct PO_ID from PURCHASE_ORDER_ITEM";  
	         stmt4 = con.createStatement();
	         ResultSet rs4 = stmt4.executeQuery(query4);

			while (rs4.next() ) {
				  System.out.println ("ponumber is:" +rs4.getString("PO_ID") );
	  
						  String query5 = "select distinct b.VENDOR_NUMBER,b.SUPPLIER_ID,b.SUPPLIER_NAME ,b.ADDRESS_1 from DM_financial a, SUPPLIER b where ponumber ='"+ rs4.getString("PO_ID") +"'  and b.SUPPLIER_NAME=a.VENDOR_NAME and rownum=1";  
						  stmt5 = con.createStatement();
					         ResultSet rs5 = stmt5.executeQuery(query5);
					         while (rs5.next()) {
					        	 min=null; 
					        	 SuppName=null;
					        	 SuppAddrs=null;
					        	 SuppID=null;
					        	 SuppName= rs5.getString("SUPPLIER_NAME"); 
					        	 SuppAddrs= rs5.getString("ADDRESS_1"); 
					        	 SuppID= rs5.getString("SUPPLIER_ID"); 
					        	 
					         }
					         rs5.close();	
					         
					         String query = "select * from warehouse where rownum = 1";  
							  Statement stmt = con.createStatement();
						         ResultSet rs = stmt.executeQuery(query);
						         String wareID = null;
						         String wareName = null;
						         while (rs.next()) {
						        	 wareID = rs.getString("ware_id");
						        	 wareName = rs.getString("ware_name");
						        	 siteID = rs.getString("site_id");
						         }
						         rs.close();
						         stmt.close();
			        	 
			        			///GET counts
								 String query7 = "select  sum(qty) as Sqty,sum(total) as Stot ,sum(DISCOUNT_AMOUNT) as Sdiscamnt ,sum(DISCOUNT_PERCENT) as Sdiscperamnt from PURCHASE_ORDER_ITEM  where po_id ='"+ rs4.getString("PO_ID") +"'";  
								  stmt7 = con.createStatement();
								   rs7 = stmt7.executeQuery(query7);
						            
						            while (rs7.next()) {
						            	Sqty= Float.parseFloat(rs7.getString("Sqty")); 
						            	Stot= Float.parseFloat(rs7.getString("Stot")); 
						            	Sdiscamnt= Float.parseFloat(rs7.getString("Sdiscamnt")); 
						            	Sdiscperamnt= Float.parseFloat(rs7.getString("Sdiscperamnt")); 
									}
									rs7.close();
									stmt7.close();
			        	 
			        	 
			        	////Get sequence number for POTable
						 String query6 = "select PURCHASEORDER_SEQ.nextval as nbr from dual";  
				            stmt6 = con.createStatement();
				            rs6 = stmt6.executeQuery(query6);
				            
				            while (rs6.next()) {
				            	min= rs6.getString("nbr"); 
				            	min="PO_"+year+"_"+min;
							}
							rs6.close();
							stmt6.close();
							
							/*String query8 = "select * from WAREHOUSE where SITE_ID='"+siteID+"'";  
				            stmt6 = con.createStatement();
				            rs6 = stmt6.executeQuery(query8);
				            
				            String warehouseID = null;
				            while (rs6.next()) {
				            	warehouseID = rs6.getString("WARE_ID");
							}
							rs6.close();
							stmt6.close();*/
				            
			         
							System.out.println("insert into PURCHASE_ORDER (PO_ID,CREATION_DATE,LAST_MODIFICATION_DATE,SUPPLIER,SUPPLIER_ADDRESS,ORDERED_DATE,DELIVERY_DATE"
									+ ",WAREHOUSE,WAREHOUSE_NAME,SITE_ID,TOTAL_AMOUNT,DISCOUNT_AMOUNT,DISCOUNT_PERCENT,PAID_AMOUNT,OUTSTANDING,STATUS,NET_TOTAL_AMOUNT,PONUMBER,TOTAL_QTY,PRQ_ID) values('"+ min +"',sysdate,sysdate,'"+ SuppID +"','"+ SuppAddrs +"',sysdate,sysdate ,\r\n"+
			         				 "'"+wareID+"','"+wareName+"','"+siteID+"'," + Stot +"," + Sdiscamnt +"," + Sdiscperamnt +",0,'"+Stot+"','completed',\r\n"+
			         				 " " + Stot +",'" + rs4.getString("PO_ID") +"'," + Sqty +",'0' " +")");
			         		
			         		//Insert into table PO 
			         		stmtpo= con.prepareStatement("insert into PURCHASE_ORDER (PO_ID,CREATION_DATE,LAST_MODIFICATION_DATE,SUPPLIER,SUPPLIER_ADDRESS,ORDERED_DATE,DELIVERY_DATE" + 
			         				 ",WAREHOUSE,WAREHOUSE_NAME,SITE_ID,TOTAL_AMOUNT,DISCOUNT_AMOUNT,DISCOUNT_PERCENT,PAID_AMOUNT,OUTSTANDING,STATUS,NET_TOTAL_AMOUNT,PONUMBER,TOTAL_QTY,PRQ_ID)  values('"+ min +"',sysdate,sysdate,'"+ SuppID +"','"+ SuppAddrs +"',sysdate,sysdate ,"+
			         				 "'"+wareID+"','"+wareName+"','"+siteID+"'," + Stot +"," + Sdiscamnt +"," + Sdiscperamnt +",0,'"+Stot+"','completed',"+
			         				 " " + Stot +",'" + rs4.getString("PO_ID") +"'," + Sqty +",'0' " +")"); 
			                stmtpo.executeUpdate();
			                stmtpo.close();
			         
			                PreparedStatement updtmt8 = con.prepareStatement("update PURCHASE_ORDER_ITEM set po_id = '"+ min +"' where po_id='"+ rs4.getString("PO_ID") +"'");
			    			updtmt8.executeUpdate();
			    			updtmt8.close();
			    			
			    			System.out.println("update FIXED_ASSET_REGISTRY set po_id = '"+ min +"' where po_id='"+ rs4.getString("PO_ID") +"'");
			    			PreparedStatement updtmt9 = con.prepareStatement("update FIXED_ASSET_REGISTRY set po_id = '"+ min +"' where po_id='"+ rs4.getString("PO_ID") +"'");
				    		updtmt9.executeUpdate();
				    		updtmt9.close();
				    		
				    		
				    		System.out.println("update ASSET_REGISTRY set po_id = '"+ min +"' where po_id='"+ rs4.getString("PO_ID") +"'");
			    			PreparedStatement updtmt10 = con.prepareStatement("update ASSET_REGISTRY set po_id = '"+ min +"' where po_id='"+ rs4.getString("PO_ID") +"'");
			    			updtmt10.executeUpdate();
			    			updtmt10.close();
			
	        }   
			rs4.close();	
            con.close();
            System.out.println("DM_PO_FROM_PURCHASE_ORDER_ITEM  COMPLETED");
		}
        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
	}

}





