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

public class MoveFin2Itemexclude_IT_PSV_INV {
	
public static void main(String[] args) {

	   long i;
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
	         
	         Statement stmt = null;
	         Statement stmt2 = null;
	         Statement stmtcat = null;
	        
	         
	         //String querycat = "select distinct FA_CATEGORY from (select distinct Assetmodel,FA_CATEGORY from DM_FINANCIAL where fa_category in (select distinct fa_category from DM_financial minus  ((select distinct ITEM_CAT_CODE as fa_category from DM_IT) union (select distinct ITEM_CAT_CODE as fa_category from DM_PASSIVE) union (select distinct fa_category from DM_inventory)))) ";  
	         String querycat = "select distinct fa_category from DM_financial minus  ((select distinct ITEM_CAT_CODE as fa_category from DM_IT) union (select distinct ITEM_CAT_CODE as fa_category from DM_PASSIVE) union (select distinct fa_category from DM_inventory)) ";
	         stmtcat = con.createStatement();
             ResultSet rscat = stmtcat.executeQuery(querycat);   
             while (rscat.next()) {
            	 i=1;

										    System.out.println("MoveFin2Itemexclude_IT_PSV_INV   " +rscat.getString("FA_CATEGORY") );
					
										   // String query = "SELECT  distinct FA_CATEGORY, L4 || ASSTETYPE as L4  ,DESCRIPTION,ASSETMODEL,ASSETPARTNUMBER,ASSTETYPE,ASSETCATEGORY,DEPREC_CODE,ACCUMULDEPRECCODE,USEFUL  from DM_FINANCIAL where FA_CATEGORY='" + rs.getString("FA_CATEGORY") + "' and L4= '" + rs.getString("L4") + "' ";
										    String query = "SELECT  distinct FA_CATEGORY, L4  ,DESCRIPTION,ASSETMODEL,ASSETPARTNUMBER,ASSTETYPE,ASSETCATEGORY,DEPREC_CODE,ACCUMULDEPRECCODE,USEFUL  from DM_FINANCIAL where FA_CATEGORY='" + rscat.getString("FA_CATEGORY") + "' ";
										    stmt = con.createStatement();
								             ResultSet rsitm = stmt.executeQuery(query);   
								             while (rsitm.next()) {
								            	   
													String dd= rsitm.getString("FA_CATEGORY")+'-'+i;
													PreparedStatement stmt3 = con.prepareStatement("insert into DM_ITEM values('" + dd + "','" + rsitm.getString("L4") + "',sysdate,sysdate,'0','" + rsitm.getString("DESCRIPTION") +"','0','" + rsitm.getString("L4") +"','" + rsitm.getString("ASSTETYPE") +"','0','0','0','0','0','0','0','0','0',sysdate+5000,'0','0','DM','0','0','0','0','0','" + rsitm.getString("ASSETMODEL") +"','" + rsitm.getString("ASSETPARTNUMBER") +"','0','" + rsitm.getString("ASSETCATEGORY") +"','" + rsitm.getString("FA_CATEGORY") +"','" + rsitm.getString("DEPREC_CODE") +"','" + rsitm.getString("ACCUMULDEPRECCODE") +"','" + rsitm.getString("USEFUL") +"','TELECOM','0','0','0','0','0','0')");
													System.out.println("insert into DM_ITEM values('" + dd + "','" + rsitm.getString("L4") + "',sysdate,sysdate,'0','" + rsitm.getString("DESCRIPTION") +"','0','" + rsitm.getString("L4") +"','" + rsitm.getString("ASSTETYPE") +"','0','0','0','0','0','0','0','0','0',sysdate+5000,'0','0','DM','0','0','0','0','0','" + rsitm.getString("ASSETMODEL") +"','" + rsitm.getString("ASSETPARTNUMBER") +"','0','0','" + rsitm.getString("ASSETCATEGORY") +"','" + rsitm.getString("FA_CATEGORY") +"','" + rsitm.getString("DEPREC_CODE") +"','" + rsitm.getString("ACCUMULDEPRECCODE") +"','" + rsitm.getString("USEFUL") +"','TELECOM','0','0','0','0','0','0')");
													stmt3.executeUpdate();
											        stmt3.close();
											        i=i+1;
								            	 
								             }
								             rsitm.close();
											 stmt.close();
				       
						
		     			
            	 
             }
             rscat.close();
				stmtcat.close();        
				con.close();
				System.out.println("COMPLETED");
			}
        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
        
        
		}

}
