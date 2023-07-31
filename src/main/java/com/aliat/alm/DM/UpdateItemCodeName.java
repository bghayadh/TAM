package com.aliat.alm.DM;

import java.io.BufferedInputStream;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.aliat.alm.common.ALMSessions;

public class UpdateItemCodeName {
	
	public static void main(String[] args) {

		ALMSessions almsessions = new ALMSessions();
		long i=1;
		  String str = null;
	        String pass=null;
	        String strpwd = null;
	        String struser = null;
        // Move DM_Hypervisor to Item
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
	         
  
        ////Get all item_cat_code from DM_ITEM
            
				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				
				String query2 = "select distinct ITEM_CAT_CODE from((select ITEM_CAT_CODE , ITEM_MODEL from DM_ITEM )minus (select ITEM_CAT_CODE , ITEM_MODEL from ITEM ))";
            Statement stmttype = con.createStatement();
            ResultSet rs2 = stmttype.executeQuery(query2);
 
            while (rs2.next()) {
            	
					System.out.println("UpdateItemCodeName  " + rs2.getString("item_cat_code"));
					String query20 = "(select item_name,unit,ITEM_DESCRIPTION,DOMAIN,ITEM_CATEGORY,ITEM_TYPE,CABLE_TYPE,WEIGHT,WEIGHT_UOM,LENGTH,WIDTH,HEIGHT,ITEM_SIZE,SIZE_UOM,SERVICE,VALUATION_RATE,DISABLED,ITEM_IMAGE,WARRANTY_PERIOD,TECH_2G,\r\n" + 
							"TECH_3G,TECH_4G,TECH_5G,ITEM_MODEL,ITEM_PART_NUMBER,ITEM_MANUFACTURER,ITEM_MODE,ITEM_CAT_CODE,DEPREC_CODE,ACCUM_DEPREC_CODE,USEFULL_LIFE_MONTHS,ITEM_KIND,\r\n" + 
							"OS, PHYSICAL_RAM,PROCESSOR_TYPE,PROCESSOR_VENDOR,SKU_NUMBER, UUID from DM_ITEM where item_cat_code= '"  + rs2.getString("item_cat_code") + "') \r\n" + 
							"minus \r\n" + 
							"(select item_name,unit,ITEM_DESCRIPTION,DOMAIN,ITEM_CATEGORY,ITEM_TYPE,CABLE_TYPE,WEIGHT,WEIGHT_UOM,LENGTH,WIDTH,HEIGHT,ITEM_SIZE,SIZE_UOM,SERVICE,VALUATION_RATE,DISABLED,ITEM_IMAGE,WARRANTY_PERIOD,TECH_2G,\r\n" + 
							"TECH_3G,TECH_4G,TECH_5G,ITEM_MODEL,ITEM_PART_NUMBER,ITEM_MANUFACTURER,ITEM_MODE,ITEM_CAT_CODE,DEPREC_CODE,ACCUM_DEPREC_CODE,USEFULL_LIFE_MONTHS,ITEM_KIND,\r\n" + 
							"OS, PHYSICAL_RAM,PROCESSOR_TYPE,PROCESSOR_VENDOR,SKU_NUMBER, UUID from ITEM where item_cat_code= '"  + rs2.getString("item_cat_code") + "') ";  
					Statement stmttype20 = con.createStatement();
					ResultSet rs20 = stmttype20.executeQuery(query20);
					
						            
		            Statement s = con.createStatement();
		            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount from Item where item_cat_code= '" + rs2.getString("item_cat_code") +"'");
		            r.next();
		            int count = r.getInt("rowcount") ;
		            r.close() ;
		            System.out.println("MyTable has " + count + " row(s).");
		            i=count+1;
		            System.out.println("max sife of"+rs2.getString("item_cat_code")  +":"+ i);
					while (rs20.next()) {
						
						String dd= rs20.getString("item_cat_code")+'-'+i;
						System.out.println(dd);
						
						String desc = rs20.getString("ITEM_DESCRIPTION");
						if(rs20.getString("ITEM_DESCRIPTION") == null)
							desc = " ";
						
						System.out.println("insert into ITEM values ("+
		            			 "'" + dd +"','" + rs20.getString("ITEM_NAME") +"',sysdate,sysdate,'" + rs20.getString("UNIT") +"',\r\n" +
		            			 " '" + desc +"', '" + rs20.getString("DOMAIN") +"', '" + rs20.getString("ITEM_CATEGORY") +"', '" + rs20.getString("ITEM_TYPE") +"',\r\n" + 
		            			" '" + rs20.getString("CABLE_TYPE") +"' ,'" + rs20.getString("WEIGHT") +"','" + rs20.getString("WEIGHT_UOM") +"','" + rs20.getString("LENGTH") +"','" + rs20.getString("WIDTH") +"',\r\n" + 
		            			"'" + rs20.getString("HEIGHT") +"', '" + rs20.getString("ITEM_SIZE") +"','" + rs20.getString("SIZE_UOM") +"','" + rs20.getString("SERVICE") +"',sysdate,\r\n" +
		            			" " + rs20.getString("VALUATION_RATE") +",'" + rs20.getString("DISABLED") +"','" + rs20.getString("ITEM_IMAGE") +"','" + rs20.getString("WARRANTY_PERIOD") +"','" + rs20.getString("TECH_2G") +"',\r\n" + 
		            			"'" + rs20.getString("TECH_3G") +"','" + rs20.getString("TECH_4G") +"','" + rs20.getString("TECH_5G") +"','" + rs20.getString("ITEM_MODEL") +"','" + rs20.getString("ITEM_PART_NUMBER") +"',\r\n" + 
		            			" '" + rs20.getString("ITEM_MANUFACTURER") +"','" + rs20.getString("ITEM_MODE") +"','" + rs20.getString("ITEM_CAT_CODE") +"','" + rs20.getString("DEPREC_CODE") +"',\r\n" +
		            			"'" + rs20.getString("ACCUM_DEPREC_CODE") +"','" + rs20.getString("USEFULL_LIFE_MONTHS") +"','" + rs20.getString("ITEM_KIND") +"','" + rs20.getString("OS") +"','" + rs20.getString("PHYSICAL_RAM") +"' ,\r\n" +
		            			"'" + rs20.getString("PROCESSOR_TYPE") +"','" + rs20.getString("PROCESSOR_VENDOR") +"','" + rs20.getString("SKU_NUMBER") +"','" + rs20.getString("UUID") +"' " +")");
								
								
								
								
						PreparedStatement stmt4 = con.prepareStatement("insert into ITEM values ("+
		            			 "'" + dd +"','" + rs20.getString("ITEM_NAME") +"',sysdate,sysdate,'" + rs20.getString("UNIT") +"',\r\n" +
		            			 " '" + desc +"', '" + rs20.getString("DOMAIN") +"', '" + rs20.getString("ITEM_CATEGORY") +"', '" + rs20.getString("ITEM_TYPE") +"',\r\n" + 
		            			" '" + rs20.getString("CABLE_TYPE") +"' ,'" + rs20.getString("WEIGHT") +"','" + rs20.getString("WEIGHT_UOM") +"','" + rs20.getString("LENGTH") +"','" + rs20.getString("WIDTH") +"',\r\n" + 
		            			"'" + rs20.getString("HEIGHT") +"', '" + rs20.getString("ITEM_SIZE") +"','" + rs20.getString("SIZE_UOM") +"','" + rs20.getString("SERVICE") +"',sysdate,\r\n" +
		            			" " + rs20.getString("VALUATION_RATE") +",'" + rs20.getString("DISABLED") +"','" + rs20.getString("ITEM_IMAGE") +"','" + rs20.getString("WARRANTY_PERIOD") +"','" + rs20.getString("TECH_2G") +"',\r\n" + 
		            			"'" + rs20.getString("TECH_3G") +"','" + rs20.getString("TECH_4G") +"','" + rs20.getString("TECH_5G") +"','" + rs20.getString("ITEM_MODEL") +"','" + rs20.getString("ITEM_PART_NUMBER") +"',\r\n" + 
		            			" '" + rs20.getString("ITEM_MANUFACTURER") +"','" + rs20.getString("ITEM_MODE") +"','" + rs20.getString("ITEM_CAT_CODE") +"','" + rs20.getString("DEPREC_CODE") +"',\r\n" +
		            			"'" + rs20.getString("ACCUM_DEPREC_CODE") +"','" + rs20.getString("USEFULL_LIFE_MONTHS") +"','" + rs20.getString("ITEM_KIND") +"','" + rs20.getString("OS") +"','" + rs20.getString("PHYSICAL_RAM") +"' ,\r\n" +
		            			"'" + rs20.getString("PROCESSOR_TYPE") +"','" + rs20.getString("PROCESSOR_VENDOR") +"','" + rs20.getString("SKU_NUMBER") +"','" + rs20.getString("UUID") +"' " +")"); 
						
						stmt4.executeUpdate();
				        stmt4.close();
				        i=i+1;
				        
				        String itmId="";
				        String model = rs20.getString("ITEM_MODEL");
				        String partnb = rs20.getString("ITEM_PART_NUMBER");
						if(rs20.getString("ITEM_MODEL") == null)
							model = "";
						if(rs20.getString("ITEM_PART_NUMBER") == null)
							partnb = "";
						Session session = almsessions.getSession();
					     if(session != null && session.isOpen()) {
					    	 Transaction tx =session.beginTransaction();
					    	 try {
					    		itmId = "ITM_" + year + "_" +Integer.parseInt(session.createSQLQuery("SELECT ITEM_ID FROM SEQ_TABLE").uniqueResult().toString());						
					    		Query query = session.createSQLQuery("UPDATE SEQ_TABLE SET ITEM_ID = ITEM_ID + 1 ");
								query.executeUpdate();
								session.createSQLQuery("commit").executeUpdate();
									
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
				        System.out.println("insert into ITEM_MODEL_PARTNUMBER values("+
				        		"'" +itmId+ "', '"+partnb+"', '1', '"+model+"', '"+dd+"', '1'"+")");
				        PreparedStatement stmt5 = con.prepareStatement("insert into ITEM_MODEL_PARTNUMBER values("+
				        		"'" +itmId+ "', '"+partnb+"', '1', '"+model+"', '"+dd+"', '1'"+")");
				        stmt5.executeUpdate();
				        stmt5.close();
				        
					}
					rs20.close();
					stmttype20.close();
					
			    }
				rs2.close();
				stmttype.close();
		       con.close();	
		       System.out.println("COMPLETED");
	        }   
        
      
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
		}

	private static int parseFloat(int fetchSize) {
		// TODO Auto-generated method stub
		return 0;
	}


}
