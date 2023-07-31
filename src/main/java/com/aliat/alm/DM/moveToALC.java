package com.aliat.alm.DM;

import java.io.BufferedInputStream;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliat.alm.common.ALMSessions;

public class moveToALC {

	private static Query seqquery =null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		ALMSessions almsessions = new ALMSessions();
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
				Connection con = DriverManager.getConnection (str,struser,strpwd);
				
				double balanceValue = 0; double netBalanceValue = 0;
				 Statement stmt5 = null; Statement stmt1 = null;

				 Date date = new Date();
				 Calendar calendar = new GregorianCalendar();
					calendar.setTime(date);
					int year = calendar.get(Calendar.YEAR);
				
				 String query = "select * from (select item_code, site_id, ware_id, ware_name, item_name," + 
				 		"USEFULLIFEMONTHS,SUPPLIER_ID,SUPPLIER_NAME," + 
				 		"ROW_NUMBER() over(PARTITION BY SITE_ID, item_code order by site_id, item_code) as ROW_NUM from fixed_asset_registry where item_code != 'null') where ROW_NUM = 1";  
			     stmt5 = con.createStatement();
			     ResultSet rs1 = stmt5.executeQuery(query);
			     
			     String item_code = null;
			     String site_id = null;
			            
		        while (rs1.next()) {
		           	
				item_code = rs1.getString("item_code");
				site_id = rs1.getString("site_id");
				
				
				String query1 = "select count(*) as qty, round(sum(INITIALCOST),2) iniCost, round(sum(NETCOST),2) netCost, round(sum(ACCUMULDEPRECAMNT),2) as accDepr, round(sum(DAILYDEPRECAMNT),2) as dailyDepr from fixed_asset_registry where item_code = '"+item_code+"' and site_id = '"+site_id+"' ";  
			     stmt1 = con.createStatement();
			     ResultSet rs2 = stmt1.executeQuery(query1);
			     
			     int qty = 0; double iniCost = 0; double netCost = 0; double accDepr = 0; double dailyDepr = 0 ;
			     while (rs2.next()) 
			     {
			    	 qty = rs2.getInt("qty");
			    	 iniCost = rs2.getDouble("iniCost");
			    	 netCost = rs2.getDouble("netCost");
			    	 accDepr = rs2.getDouble("accDepr");
			    	 dailyDepr = rs2.getDouble("dailyDepr");
			    	
			    	 System.out.println("Quantity is: "+qty+" and Initial Cost is :"+iniCost+ " and Net Cost is:"+netCost);
			     }
			     rs2.close();
			     stmt1.close();
			     String alcID = null ;
			     Session session = almsessions.getSession();
			     if(session != null && session.isOpen()) {
			    	 Transaction tx =session.beginTransaction();
			    	 try {
			    		alcID = "ALC_" + year + "_" +Integer.parseInt(session.createSQLQuery("SELECT Asset_Life_Cycle FROM SEQ_TABLE").uniqueResult().toString());
			    		seqquery = session.createSQLQuery("UPDATE SEQ_TABLE SET Asset_Life_Cycle = Asset_Life_Cycle + 1 ");
			    		seqquery.executeUpdate();
			    		session.createSQLQuery("commit").executeUpdate();
							
			    		 //alcID = "ALC_" + year + "_" + appConfig.getSequenceNbr(16);
			    	 }catch (Exception e) {
			 			System.out.println("error while getting sequence");
					}

					finally {
						if (session != null && session.isOpen()) {
							tx.commit();
							session.close();
						}
					}
			     }
				 //alcID = "ALC_" + year + "_" + appConfig.getSequenceNbr(16);
			        
			        
			        System.out.println("insert into ASSET_LIFE_CYCLE (ALC_ID,CREATION_DATE,LAST_MODIFIED_DATE,WAREHOUSE,ITEM_CODE,ITEM_NAME,UOM,DOCSTATUS,WARE_NAME,VALUATION_RATE,"
			        		+ "NET_PRICE,INCOMING_RATE,BALANCE_VALUE,NET_BALANCE_VALUE,DAILY_DEPRECIATION,ACCUMULATED_DEPRECIATION,TOTAL_ACCUMULATED_DEPRECIATION,SUPPLIER_ID,SUPPLIER_NAME,SITE_ID,IS_OPENING,ACTUAL_QTY,BALANCE_QTY,IN_VALUE,IN_NET_VALUE) values('"+ alcID +"',sysdate,sysdate,'"+ rs1.getString("ware_id") +"','"+rs1.getString("item_code")+"','"+rs1.getString("item_name")+"','NOS','Approved',"+
							 "'" +rs1.getString("ware_name")+"','"+iniCost+"','" +netCost+"','" + iniCost+"','" + iniCost+"','"+netCost+"','" +dailyDepr+"','" +accDepr+"','"+accDepr+"',"+
							 "'" + rs1.getString("SUPPLIER_ID") +"','"+rs1.getString("SUPPLIER_NAME")+"','"+rs1.getString("SITE_ID")+"','1','"+qty+"','"+qty+"','"+iniCost+"','"+netCost+"')");
					
					//Insert into table ALC 
			        PreparedStatement ALCstmt = con.prepareStatement("insert into ASSET_LIFE_CYCLE (ALC_ID,CREATION_DATE,LAST_MODIFIED_DATE,WAREHOUSE,ITEM_CODE,ITEM_NAME,UOM,DOCSTATUS,WARE_NAME,VALUATION_RATE,"
			        		+ "NET_PRICE,INCOMING_RATE,BALANCE_VALUE,NET_BALANCE_VALUE,DAILY_DEPRECIATION,ACCUMULATED_DEPRECIATION,TOTAL_ACCUMULATED_DEPRECIATION,SUPPLIER_ID,SUPPLIER_NAME,SITE_ID,IS_OPENING,ACTUAL_QTY,BALANCE_QTY,IN_VALUE,IN_NET_VALUE) values('"+ alcID +"',sysdate,sysdate,'"+ rs1.getString("ware_id") +"','"+rs1.getString("item_code")+"','"+rs1.getString("item_name")+"','NOS','Approved',"+
							 "'" +rs1.getString("ware_name")+"','"+iniCost+"','" +netCost+"','" +iniCost+"','" + iniCost+"','"+netCost+"','" +dailyDepr+"','" +accDepr+"','"+accDepr+"',"+
							 "'" + rs1.getString("SUPPLIER_ID") +"','"+rs1.getString("SUPPLIER_NAME")+"','"+rs1.getString("SITE_ID")+"','1','"+qty+"','"+qty+"','"+iniCost+"','"+netCost+"')");
			        ALCstmt.executeUpdate();
			        ALCstmt.close();
			        
			        
			        /// Insert ALL Related SERIALS
			        String getAllSerials = "select item_name, item_code, item_model, item_sn, item_part_number from fixed_asset_registry where item_code = '"+item_code+"' and site_id = '"+site_id+"' ";  
				    stmt1 = con.createStatement();
				    ResultSet queryRes = stmt1.executeQuery(getAllSerials);
				    while (queryRes.next()) 
				     {
				    	System.out.println("insert into ASSET_LIFECYCLE_SERIALS (ITEM_CODE,ITEM_NAME,ITEM_MODEL,ITEM_PARTNO,ITEM_SN,ALC_ID) values('"+ queryRes.getString("ITEM_CODE") +"','"+queryRes.getString("ITEM_NAME") +"','"+queryRes.getString("ITEM_MODEL")+"','"+queryRes.getString("item_part_number")+"',"+
								 "'" +queryRes.getString("item_sn")+"','"+alcID+"')");
						
						
				        PreparedStatement ALCstmt2 = con.prepareStatement("insert into ASSET_LIFECYCLE_SERIALS (ITEM_CODE,ITEM_NAME,ITEM_MODEL,ITEM_PARTNO,ITEM_SN,ALC_ID) values('"+ queryRes.getString("ITEM_CODE") +"','"+queryRes.getString("ITEM_NAME") +"','"+queryRes.getString("ITEM_MODEL")+"','"+queryRes.getString("item_part_number")+"',"+
								 "'" +queryRes.getString("item_sn")+"','"+alcID+"')");
				        
				        ALCstmt2.executeUpdate();
				        ALCstmt2.close();
				        
				     }
				    queryRes.close();
				    stmt1.close();
				     
				     
		        }
				rs1.close();
				stmt5.close();
				
				con.close();
				System.out.println("DM_ASSET_LIFE_CYCLE  COMPLETED");
		}
		catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
	         

	}

}
