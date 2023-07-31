package com.aliat.alm.Reports;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Get_PAYG_Revenue_WIN {
	
		static Connection con,connectionsql ;
		static Logger logger;
		static FileHandler fh;
		static BufferedReader objReader1 = null;
		static String strCurrentLine1;
		static String logpath;
		static String db1path;
		static String username1;
		static String password1;
		static String sqlpath="0";
		static String logsid="0";
		static String Gyear;
		static String Gcellid="0";
		static int nbOfErrors = 0;
		static String bundledate=null;
	    static float voicerev=0;
	    static float smsrev=0;
	    static float datarev=0;
	    static float vasrev=0;
	    static String vcellname=null;
	    static String vsiteid=null;
	    static String vsitename=null;
	    static String vcommreg=null;
	    static String vsalesarea=null;
	    static String vlng=null;
	    static String vlat=null;
	    static String vtechreg=null;
	    static String vkynprov=null;
	    static String vsitecluster=null;
	    static String vclustertype=null;
	    static String vruralsitetype=null;
	    static String vurban=null;
	    static String vmetrocluster=null;
	    static String vsublocation=null;
	    static String vinoudoor=null;
	    static String vtech2g="0";
	    static String vtech3g="0";
	    static String vtech4g="0";
	    static String vtech5g="0";
	    static String VPARTKEY="0";
	    
	    	   
	    public static void main(String[] args) throws Exception {
	    ResultSet resultSet = null;
	    ResultSet resultSetcell = null;
	    
	    
	  //get only year from today date
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	LocalDateTime now1 = LocalDateTime.now();
    	Gyear=dtf1.format(now1).substring(0,4);
    	System.out.println(Gyear);
	    
	    
        //connect to oracle
			try {
				objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almconfig.dat"));
			while ((strCurrentLine1 = objReader1.readLine()) != null){
			String data = strCurrentLine1;
			String[] data1 ;
			if (data.contains("logpath")) {
			data1=data.split(";",-1);
			logpath=data1[1];
			//System.out.println("logpath found :" + logpath);
			}
			if (data.contains("db3path")) {
			data1=data.split(";",-1);
			db1path=data1[1];
			username1=data1[2];
			password1=data1[3];
			//System.out.println("db1path found :" + db1path);
			}
			if (data.contains("sqlpath::")) {
				data1=data.split("::",-1);
				sqlpath=data1[1];
				//System.out.println("sqlpath found :" + sqlpath);
				}
			
			}
			objReader1.close();
			}
			catch(Exception e){
			    System.err.println(e);
			    e.printStackTrace();
			    logger.info("Error : "+e);
			 }
			
			//Connect to almparser DB
			String dbURL =db1path;
			String username =username1;
			String password =password1;
			try {
			  con= DriverManager.getConnection(dbURL,username,password);
			System.out.println("Connected to oracle DB");
			} catch (SQLException e) {
			      System.out.println("Opss, error");
			      e.printStackTrace();
			      logger.info("Error : "+e);
			  }
			
			
			
	   //conect to sqlserver
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://192.168.56.1\\SQLALM:1433;databaseName=test;user=sa;password=admin1234");) {
        // try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://BISSO\\SQLALM:1433;databaseName=test;user=sa;password=admin1234");) {
       
	
	try
        {
        //System.out.println("Inside try and before connecting to MSSQL");
		
        //String url = "jdbc:sqlserver://10.22.28.23\\FSTSQLBDD:1433;DatabaseName=dwh_tkl;encrypt = true;trustServerCertificate=true;user=katelecomuser;password=K@t3l4comuser@2021";  
        
        
        
        //String url = "jdbc:sqlserver://192.168.0.170\\SQLVALM:1433;DatabaseName=vtest;encrypt = true;trustServerCertificate=true;user=alm;password=alm12345";
        //String url = "jdbc:sqlserver://192.168.0.192\\SQLALM:1433;DatabaseName=test1;encrypt = true;trustServerCertificate=true;user=alm;password=alm";
        
        System.out.println("Read sqlserver from almconfig");
        String url=sqlpath.toString();
        connectionsql = DriverManager.getConnection(url);
        System.out.println(" connected to MS SQLServer");
        
        
        
        // search date to run mugration if first time select all to migrate else system will read last date in oracle revenue and call till sysdate -2
       GetMissingdateToMigrate();
    
    
     
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
        System.out.println(e.toString());
            e.printStackTrace();
            System.out.println(" Error: Not connected to MS SQLServer");
        }

 
		

		
	
		

		System.out.println("migration completed");


		connectionsql.close();
		con.close();
     
    }

	    
	    // get VOICE_REVENUE by CEllid and LAC
	    private static float Getvoice_cell_revenue(String cel,String lac,String VPARTKEY) throws SQLException  {
	    	 ResultSet resultSet1 = null;
	    	 float vrevenue=0;
	   //try {
		   Statement statement1 = connectionsql.createStatement();
	        // Create and execute a SELECT SQL statement.
	                String selectSql1="select (sum((t.TOTAL_VOICE_REVENUE)/100) /1.334) TOTAL_VOICE_REVENUE from (\r\n" + 
	                		" (SELECT sum(o.CHARGE1)  AS TOTAL_VOICE_REVENUE\r\n" + 
	                		"FROM [dbo].[RATED_EVENTS_NEW_COPY] o    \r\n" + 
	                		"INNER JOIN [dbo].[WALLET] i\r\n" + 
	                		"ON o.ACCT_RES_ID1 = i.ID_WALLET and o.ACCT_RES_ID1 =1\r\n" + 
	                		"and o.CELL_A='"+ cel +"' and o.LAC_A='"+ lac +"'\r\n" + 
	                		"and o.ID_SERVICE_TYPE=1  and o.CHARGE1 > 0  and o.PARTKEY='" + VPARTKEY + "'\r\n" + 
	                		"and i.IS_CURRENCY='Y')\r\n" + 
	                		"union \r\n" + 
	                		" (SELECT sum(o.CHARGE2)  AS TOTAL_VOICE_REVENUE\r\n" + 
	                		"FROM [dbo].[RATED_EVENTS_NEW_COPY] o    \r\n" + 
	                		"INNER JOIN [dbo].[WALLET] i\r\n" + 
	                		"ON o.ACCT_RES_ID2 = i.ID_WALLET and o.ACCT_RES_ID2 =1\r\n" + 
	                		"and o.CELL_A='"+ cel +"' and o.LAC_A='"+ lac +"' \r\n" + 
	                		"and o.ID_SERVICE_TYPE=1  and o.CHARGE2 > 0  and o.PARTKEY='" + VPARTKEY + "'\r\n" + 
	                		"and i.IS_CURRENCY='Y')\r\n" + 
	                		"union \r\n" + 
	                		" (SELECT sum(o.CHARGE3)  AS TOTAL_VOICE_REVENUE\r\n" + 
	                		"FROM [dbo].[RATED_EVENTS_NEW_COPY] o    \r\n" + 
	                		"INNER JOIN [dbo].[WALLET] i\r\n" + 
	                		"ON o.ACCT_RES_ID3 = i.ID_WALLET and o.ACCT_RES_ID3 =1\r\n" + 
	                		"and o.CELL_A='"+ cel +"' and o.LAC_A='"+ lac +"'\r\n" + 
	                		"and o.ID_SERVICE_TYPE=1  and o.CHARGE3 > 0  and o.PARTKEY='" + VPARTKEY + "'\r\n" + 
	                		"and i.IS_CURRENCY='Y')\r\n" + 
	                		"union \r\n" + 
	                		" (SELECT sum(o.CHARGE4)  AS TOTAL_VOICE_REVENUE\r\n" + 
	                		"FROM [dbo].[RATED_EVENTS_NEW_COPY] o    \r\n" + 
	                		"INNER JOIN [dbo].[WALLET] i\r\n" + 
	                		"ON o.ACCT_RES_ID4 = i.ID_WALLET and o.ACCT_RES_ID4 =1\r\n" + 
	                		"and o.CELL_A='"+ cel +"' and o.LAC_A='"+ lac +"'\r\n" + 
	                		"and o.ID_SERVICE_TYPE=1  and o.CHARGE4 > 0  and o.PARTKEY='" + VPARTKEY + "'\r\n" + 
	                		"and i.IS_CURRENCY='Y')) t;";
	                
	                System.out.println(selectSql1);
	                
	                resultSet1 = statement1.executeQuery(selectSql1);
	                while (resultSet1.next()) {
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(1),null)) {
	                		vrevenue=0;
	                	} else {
	                		System.out.println(Float.parseFloat(resultSet1.getString(1)));
		                	vrevenue=Float.parseFloat(resultSet1.getString(1));
	                	}
	                	
	                	
	                }
	                resultSet1.close();
	    	
			  // }  catch (Exception e) {
			  //     System.out.println(e.toString());
			   //    System.out.println(" Error: Getvoice_cell_revenue ");
			   //}
	   		return vrevenue;
	    }
 
	    
	    // get SMS_REVENUE by CEllid 
	    private static float Getsms_cell_revenue(String cel,String lac,String VPARTKEY) throws SQLException  {
	    	 ResultSet resultSet1 = null;
	    	 float srevenue=0;
	   //try {
		   Statement statement1 = connectionsql.createStatement();
	        // Create and execute a SELECT SQL statement.
	                String selectSql1="select (sum((t.TOTAL_VOICE_REVENUE)/100) /1.334) TOTAL_VOICE_REVENUE from (\r\n" + 
	                		" (SELECT sum(o.CHARGE1)  AS TOTAL_VOICE_REVENUE\r\n" + 
	                		"FROM [dbo].[RATED_EVENTS_NEW_COPY] o    \r\n" + 
	                		"INNER JOIN [dbo].[WALLET] i\r\n" + 
	                		"ON o.ACCT_RES_ID1 = i.ID_WALLET and o.ACCT_RES_ID1 =1\r\n" + 
	                		"and o.CELL_A='"+ cel +"' and o.LAC_A='"+ lac +"' \r\n" + 
	                		"and o.ID_SERVICE_TYPE=3  and o.CHARGE1 > 0  and o.PARTKEY='" + VPARTKEY + "'\r\n" + 
	                		"and i.IS_CURRENCY='Y')\r\n" + 
	                		"union \r\n" + 
	                		" (SELECT sum(o.CHARGE2)  AS TOTAL_VOICE_REVENUE\r\n" + 
	                		"FROM [dbo].[RATED_EVENTS_NEW_COPY] o    \r\n" + 
	                		"INNER JOIN [dbo].[WALLET] i\r\n" + 
	                		"ON o.ACCT_RES_ID2 = i.ID_WALLET and o.ACCT_RES_ID2 =1\r\n" + 
	                		"and o.CELL_A='"+ cel +"' and o.LAC_A='"+ lac +"'\r\n" + 
	                		"and o.ID_SERVICE_TYPE=3  and o.CHARGE2 > 0  and o.PARTKEY='" + VPARTKEY + "'\r\n" + 
	                		"and i.IS_CURRENCY='Y')\r\n" + 
	                		"union \r\n" + 
	                		" (SELECT sum(o.CHARGE3)  AS TOTAL_VOICE_REVENUE\r\n" + 
	                		"FROM [dbo].[RATED_EVENTS_NEW_COPY] o    \r\n" + 
	                		"INNER JOIN [dbo].[WALLET] i\r\n" + 
	                		"ON o.ACCT_RES_ID3 = i.ID_WALLET and o.ACCT_RES_ID3 =1\r\n" + 
	                		"and o.CELL_A='"+ cel +"' and o.LAC_A='"+ lac +"'\r\n" + 
	                		"and o.ID_SERVICE_TYPE=3  and o.CHARGE3 > 0  and o.PARTKEY='" + VPARTKEY + "'\r\n" + 
	                		"and i.IS_CURRENCY='Y')\r\n" + 
	                		"union \r\n" + 
	                		" (SELECT sum(o.CHARGE4)  AS TOTAL_VOICE_REVENUE\r\n" + 
	                		"FROM [dbo].[RATED_EVENTS_NEW_COPY] o    \r\n" + 
	                		"INNER JOIN [dbo].[WALLET] i\r\n" + 
	                		"ON o.ACCT_RES_ID4 = i.ID_WALLET and o.ACCT_RES_ID4 =1\r\n" + 
	                		"and o.CELL_A='"+ cel +"' and o.LAC_A='"+ lac +"'\r\n" + 
	                		"and o.ID_SERVICE_TYPE=3  and o.CHARGE4 > 0  and o.PARTKEY='" + VPARTKEY + "'\r\n" + 
	                		"and i.IS_CURRENCY='Y')) t";
	                
	                System.out.println(selectSql1);
	                
	                resultSet1 = statement1.executeQuery(selectSql1);
	                while (resultSet1.next()) {

	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(1),null)) {
	                		srevenue=0;
	                	} else {
	                		System.out.println(Float.parseFloat(resultSet1.getString(1)));
		                	srevenue=Float.parseFloat(resultSet1.getString(1));
	                	}
	                	
	                }
	                resultSet1.close();
	    	
			   //}  catch (Exception e) {
			    //   System.out.println(e.toString());
			   //    System.out.println(" Error: Getsms_cell_revenue ");
			  // }
	   		return srevenue;
	    }
 

	    // get DATA_REVENUE by CEllid 
	    private static float Getdata_cell_revenue(String cel,String lac,String VPARTKEY) throws SQLException  {
	    	 ResultSet resultSet1 = null;
	    	 float drevenue=0;
	   //try {
		   Statement statement1 = connectionsql.createStatement();
	        // Create and execute a SELECT SQL statement.
	                String selectSql1="select (sum((t.TOTAL_VOICE_REVENUE)/100) /1.334) TOTAL_VOICE_REVENUE from (\r\n" + 
	                		" (SELECT sum(o.CHARGE1)  AS TOTAL_VOICE_REVENUE\r\n" + 
	                		"FROM [dbo].[RATED_EVENTS_NEW_COPY] o    \r\n" + 
	                		"INNER JOIN [dbo].[WALLET] i\r\n" + 
	                		"ON o.ACCT_RES_ID1 = i.ID_WALLET and o.ACCT_RES_ID1 =1\r\n" + 
	                		"and o.CELL_A='"+ cel +"' and o.LAC_A='"+ lac +"'\r\n" + 
	                		"and o.ID_SERVICE_TYPE=2  and o.CHARGE1 > 0  and o.PARTKEY='" + VPARTKEY + "'\r\n" + 
	                		"and i.IS_CURRENCY='Y')\r\n" + 
	                		"union \r\n" + 
	                		" (SELECT sum(o.CHARGE2)  AS TOTAL_VOICE_REVENUE\r\n" + 
	                		"FROM [dbo].[RATED_EVENTS_NEW_COPY] o    \r\n" + 
	                		"INNER JOIN [dbo].[WALLET] i\r\n" + 
	                		"ON o.ACCT_RES_ID2 = i.ID_WALLET and o.ACCT_RES_ID2 =1\r\n" + 
	                		"and o.CELL_A='"+ cel +"' and o.LAC_A='"+ lac +"'\r\n" + 
	                		"and o.ID_SERVICE_TYPE=2  and o.CHARGE2 > 0  and o.PARTKEY='" + VPARTKEY + "'\r\n" + 
	                		"and i.IS_CURRENCY='Y')\r\n" + 
	                		"union \r\n" + 
	                		" (SELECT sum(o.CHARGE3)  AS TOTAL_VOICE_REVENUE\r\n" + 
	                		"FROM [dbo].[RATED_EVENTS_NEW_COPY] o    \r\n" + 
	                		"INNER JOIN [dbo].[WALLET] i\r\n" + 
	                		"ON o.ACCT_RES_ID3 = i.ID_WALLET and o.ACCT_RES_ID3 =1\r\n" + 
	                		"and o.CELL_A='"+ cel +"' and o.LAC_A='"+ lac +"'\r\n" + 
	                		"and o.ID_SERVICE_TYPE=2  and o.CHARGE3 > 0  and o.PARTKEY='" + VPARTKEY + "'\r\n" + 
	                		"and i.IS_CURRENCY='Y')\r\n" + 
	                		"union \r\n" + 
	                		" (SELECT sum(o.CHARGE4)  AS TOTAL_VOICE_REVENUE\r\n" + 
	                		"FROM [dbo].[RATED_EVENTS_NEW_COPY] o    \r\n" + 
	                		"INNER JOIN [dbo].[WALLET] i\r\n" + 
	                		"ON o.ACCT_RES_ID4 = i.ID_WALLET and o.ACCT_RES_ID4 =1\r\n" + 
	                		"and o.CELL_A='"+ cel +"' and o.LAC_A='"+ lac +"'\r\n" + 
	                		"and o.ID_SERVICE_TYPE=2  and o.CHARGE4 > 0  and o.PARTKEY='" + VPARTKEY + "'\r\n" + 
	                		"and i.IS_CURRENCY='Y')) t";
	                
	                System.out.println(selectSql1);
	                
	                resultSet1 = statement1.executeQuery(selectSql1);
	                while (resultSet1.next()) {
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(1),null)) {
	                		drevenue=0;
	                	} else {
	                		System.out.println(Float.parseFloat(resultSet1.getString(1)));
		                	drevenue=Float.parseFloat(resultSet1.getString(1));
	                	}
	                	
	                }
	                resultSet1.close();
	    	
			   //}  catch (Exception e) {
			     //  System.out.println(e.toString());
			    //   System.out.println(" Error: Getdata_cell_revenue ");
			  // }
	   		return drevenue;
	    }
	    
	    
	    
	 // get cell info by CEllid from gsm_cell_id table
	    private static void Getcellinfo_cell_revenue(String cel,String lac,String VPARTKEY) throws SQLException  {
	    	 ResultSet resultSet1 = null;
	    	 vcellname=null;
	    	 vsiteid=null;
	    	 vsitename=null;
	    	 vcommreg=null;
	    	 vsalesarea=null;
	    	 vlng=null;
	    	 vlat=null;
	    	 vtechreg=null;
	    	 vkynprov=null;
	    	 vsitecluster=null;
	    	 vclustertype=null;
	    	 vruralsitetype=null;
	    	 vurban=null;
	    	 vmetrocluster=null;
	    	 vsublocation=null;
	    	 vinoudoor=null;
	    	 vtech2g="0";
	    	 vtech3g="0";
	    	 vtech4g="0";
	    	 vtech5g="0";
	    	 
	    	 if (StringUtils.equalsIgnoreCase (cel,null) || StringUtils.equalsIgnoreCase (lac,null)) {
	    		 
	    	 } else { 
	    	 
	 //  try {
		   Statement statement1 = connectionsql.createStatement();
	        // Create and execute a SELECT SQL statement.
                    // here we might found many records for one cell id what we do is to take last cellname and info from last row and we count all tech found like 2G/3G/$G
	                String selectSql1="select distinct [Cell Name],[Site_ID],[SITE_NAME],[DATA_CELL_TYPE],[COMMERCIAL_REGION],[SALES_AREA],[LONGITUDE],[LATITUDE],[Technology_Regions],[Kenya Provinces ],[Site_Cluster],[Cluster Type],[Rural Subclass],[Urban_Rural],[Commercial Sites]\r\n" + 
	                		"from [dbo].[GSM_CELL_ID_NEW] where [Cell ID]='"+ cel +"' and [LAC]='"+ lac +"'";
	                
	               
	                System.out.println(selectSql1);
	                
	                
	                resultSet1 = statement1.executeQuery(selectSql1);
	                while (resultSet1.next()) {
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(1),null)) {
	                		vcellname=null;
	                	} else {
		                	vcellname=resultSet1.getString(1);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(2),null)) {
	                		vsiteid=null;
	                	} else {
	                		vsiteid=resultSet1.getString(2);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(3),null)) {
	                		vsitename=null;
	                	} else {
	                		vsitename=resultSet1.getString(3);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(4),null)) {

	                	} else {
	                		if (StringUtils.equalsIgnoreCase (resultSet1.getString(4),"2G")) {
	                			vtech2g="1";
	                		}
	                			
	                		if (StringUtils.equalsIgnoreCase (resultSet1.getString(4),"3G")) {
	                			vtech3g="1";
	                		}
	                		if (StringUtils.equalsIgnoreCase (resultSet1.getString(4),"4G")) {
	                			vtech4g="1";
	                		}
	                		if (StringUtils.equalsIgnoreCase (resultSet1.getString(4),"5G")) {
	                			vtech5g="1";
	                		}
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(5),null)) {
	                		vcommreg=null;
	                	} else {
	                		vcommreg=resultSet1.getString(5);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(6),null)) {
	                		vsalesarea=null;
	                	} else {
	                		vsalesarea=resultSet1.getString(6);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(7),null)) {
	                		vlng=null;
	                	} else {
	                		vlng=resultSet1.getString(7);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(8),null)) {
	                		vlat=null;
	                	} else {
	                		vlat=resultSet1.getString(8);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(9),null)) {
	                		vtechreg=null;
	                	} else {
	                		vtechreg=resultSet1.getString(9);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(10),null)) {
	                		vkynprov=null;
	                	} else {
	                		vkynprov=resultSet1.getString(10);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(11),null)) {
	                		vsitecluster=null;
	                	} else {
	                		vsitecluster=resultSet1.getString(11);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(12),null)) {
	                		vclustertype=null;
	                	} else {
	                		vclustertype=resultSet1.getString(12);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(13),null)) {
	                		vruralsitetype=null;
	                	} else {
	                		vruralsitetype=resultSet1.getString(13);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(14),null)) {
	                		vurban=null;
	                	} else {
	                		vurban=resultSet1.getString(14);
	                	}
	                	
	                	if (StringUtils.equalsIgnoreCase (resultSet1.getString(15),null)) {
	                		vmetrocluster=null;
	                	} else {
	                		vmetrocluster=resultSet1.getString(15);
	                	}
	                	
	                }
	                resultSet1.close();
	       }  // end cell and lac not null
	    	
			  // }  catch (Exception e) {
			  //     System.out.println(e.toString());
			  //     System.out.println(" Error: Getcellinfo_cell_revenue ");
			 //  }

	    }
	    
	    
    // Call to migrate data
    private static void GetDatafrombundle_revenue(String vid ,String vbundledate,String celid,String celname,String lac,String gsmcel,String nodeid,String siteid,String sitename,String areaid,String areaname,String regionid,String regionname,float voicerev,float smsrev,float datarev,float vasrev,String tec2,String tec3,String tec4,String tec5,String commreg,String salester,String lng,String lat,String techreg,String knyprov,String siteclstr,String clstrtype,String ruralsitetype,String urban,String metroclstr,String subloc,String inoutdoor) throws SQLException  {
   //try {
    	
    	if (StringUtils.equalsIgnoreCase (sitename,null)) {
    		
    	} else {
    		//remove ' from sitename
            String vsitename=sitename;
            String newsitename="0";
            String finalsitename=sitename;
          int  m = 0;
          m = vsitename.indexOf("'");
          while (m>0) {
    //System.out.println(""+clusterid.substring(0, m));
    //System.out.println(""+clusterid.substring(m+1, clusterid.length()));
        	  newsitename=vsitename.substring(0, m) +vsitename.substring(m+1, vsitename.length());
        	  sitename=vsitename.substring(0, m) +"''"+vsitename.substring(m+1, vsitename.length());
    vsitename=newsitename;
    //System.out.println(clusterid);
    m = vsitename.indexOf("'");
          }	
    	}
    	
    	
if (StringUtils.equalsIgnoreCase (siteclstr,null)) {
    		
    	} else {
    		//remove ' from siteclstr
            String vsiteclstr=siteclstr;
            String newsiteclstr="0";
            String finalsiteclstr=siteclstr;
          int  m = 0;
          m = vsiteclstr.indexOf("'");
          while (m>0) {
    //System.out.println(""+clusterid.substring(0, m));
    //System.out.println(""+clusterid.substring(m+1, clusterid.length()));
        	  newsiteclstr=vsiteclstr.substring(0, m) +vsiteclstr.substring(m+1, vsiteclstr.length());
        	  siteclstr=vsiteclstr.substring(0, m) +"''"+vsiteclstr.substring(m+1, vsiteclstr.length());
        	  vsiteclstr=newsiteclstr;
    //System.out.println(clusterid);
    m = vsiteclstr.indexOf("'");
          }	
    	}
    	
    	
    	
    	
       
	   
	 System.out.println("insert into PREPAID_PAYG_REVENUE (ID,REVENUE_DATE,CELL_ID,CELL_NAME,LAC,GSM_CELL_ID,NODE_ID,SITE_ID,SITE_NAME,AREA_ID,AREA_NAME,REGION_ID,REGION_NAME,VOICE_REVENUE,SMS_REVENUE,DATA_REVENUE,VAS_REVENUE,TECH_2G,TECH_3G,TECH_4G,TECH_5G,COMMERCIAL_REGION,SALES_TERRITORY,LONGITUDE,LATITUDE,TECHNOLOGY_REGIONS,KENYA_PROVINCES,SITE_CLUSTER,CLUSTER_TYPE,RURAL_SITE_TYPE,URBAN_RURAL,METRO_CLUSTERS,SUBLOCATIONS,INDOOR_OUTDOOR) "
			 + "values( '" + vid +"',TO_DATE('" + vbundledate +"', 'YYYY-MM-DD'),'" + celid +"','" + celname +"','" + lac +"','" + gsmcel +"','" + nodeid +"','" + siteid +"','" + sitename +"','" + areaid +"','" + areaname +"','" + regionid +"','" + regionname +"','" + voicerev +"','" + smsrev +"','" + datarev +"','" + vasrev +"','" + tec2 +"','" + tec3 +"','" + tec4 +"','" + tec5 +"','" + commreg +"','" + salester +"','" + lng +"','" + lat +"','" + techreg +"','" + knyprov +"','" + siteclstr +"','" + clstrtype +"','" + ruralsitetype +"','" + urban +"','" + metroclstr +"','" + subloc +"','" + inoutdoor +"')");
    
	   
	   PreparedStatement insert_bundlerevenue = con.prepareStatement("insert into PREPAID_PAYG_REVENUE (ID,REVENUE_DATE,CELL_ID,CELL_NAME,LAC,GSM_CELL_ID,NODE_ID,SITE_ID,SITE_NAME,AREA_ID,AREA_NAME,REGION_ID,REGION_NAME,VOICE_REVENUE,SMS_REVENUE,DATA_REVENUE,VAS_REVENUE,TECH_2G,TECH_3G,TECH_4G,TECH_5G,COMMERCIAL_REGION,SALES_TERRITORY,LONGITUDE,LATITUDE,TECHNOLOGY_REGIONS,KENYA_PROVINCES,SITE_CLUSTER,CLUSTER_TYPE,RURAL_SITE_TYPE,URBAN_RURAL,METRO_CLUSTERS,SUBLOCATIONS,INDOOR_OUTDOOR) "
    		+ "values( '" + vid +"',TO_DATE('" + vbundledate +"', 'YYYY-MM-DD'),'" + celid +"','" + celname +"','" + lac +"','" + gsmcel +"','" + nodeid +"','" + siteid +"','" + sitename +"','" + areaid +"','" + areaname +"','" + regionid +"','" + regionname +"','" + voicerev +"','" + smsrev +"','" + datarev +"','" + vasrev +"','" + tec2 +"','" + tec3 +"','" + tec4 +"','" + tec5 +"','" + commreg +"','" + salester +"','" + lng +"','" + lat +"','" + techreg +"','" + knyprov +"','" + siteclstr +"','" + clstrtype +"','" + ruralsitetype +"','" + urban +"','" + metroclstr +"','" + subloc +"','" + inoutdoor +"')");
    insert_bundlerevenue.executeUpdate();
    insert_bundlerevenue.close();
	  // }  catch (Exception e) {
	  //     System.out.println(e.toString());
	   //    System.out.println(" Error: GetDatafrombundle_revenue insert to Oracle PREPAID_PAYG_REVENUE");
	  // }
   }
   
 // validate missing date to migrate until sysdate -2
    private static void GetMissingdateToMigrate() throws SQLException  {
	    Statement stmt3 = null;
	    ResultSet resultSet = null;
	    String vqdatefrom=null;
	    String vqdateto=null;
	   	   
	    
	    //try {
        // get last date insterted into revenue table
    	String query3 = "select to_char(Max(REVENUE_DATE),'YYYYMMDD') as Fdate from PREPAID_PAYG_REVENUE";  
        stmt3 = con.createStatement();
        ResultSet rs3 = stmt3.executeQuery(query3);
        while (rs3.next()) {      
       
        vqdatefrom=rs3.getString("Fdate");
        System.out.println("get date from   " + vqdatefrom);
        }
        rs3.close();  
        stmt3.close();


 
         // get date sysdate-2
        query3 = "select to_char(sysdate-2,'YYYYMMDD') as Qdate from dual";  
        stmt3 = con.createStatement();
         rs3 = stmt3.executeQuery(query3);
        while (rs3.next()) {      
        	if (StringUtils.equalsIgnoreCase (vqdatefrom,null)) {
        		System.out.println("start date is null");
        		vqdatefrom =rs3.getString("Qdate");
        	}
        vqdateto=rs3.getString("Qdate");
        System.out.println("get sysdate -2   " + vqdateto);
        }
        rs3.close();  
        stmt3.close();


        //query data from sqlserver between datefrom and dateto
        String selectSql=null;
        Statement statement = connectionsql.createStatement();
        // Create and execute a SELECT SQL statement.
        if (StringUtils.equalsIgnoreCase (vqdatefrom,vqdateto)) {
        	System.out.println("get start date  " + vqdatefrom);
        	selectSql="select distinct Partkey from [dbo].[RATED_EVENTS_NEW_COPY] order by Partkey asc";
        } else {
        	System.out.println("get sysdate  vqdatefrom  and vqdateto  " + vqdatefrom +":"+ vqdateto);
        	selectSql="select distinct Partkey from [dbo].[RATED_EVENTS_NEW_COPY] where Partkey > '" + vqdatefrom + "' and Partkey <= '" + vqdateto + "' order by Partkey asc";
        }
        
       resultSet = statement.executeQuery(selectSql);

       // Print results from select statement
       while (resultSet.next()) {
    	   System.out.println("Start retrieve data of: "+ resultSet.getString(1));
    	   getdata(resultSet.getString(1).toString());
     
       }
       resultSet.close();
       
	  //  }  catch (Exception e) {
		//       System.out.println(e.toString());
		//       System.out.println(" Error: GetMissingdateToMigrate");
		//   }
   
       
    }
    
    private static void  getdata(String VPARTKEY) throws SQLException {
    	ResultSet resultSetcell = null;
    	ResultSet resultSet = null;
    	
    //	try {
    	   // Get all distinct cell id,lac and run same function based on cell id and Lac found in DB
        Statement statementcell = connectionsql.createStatement();
        
        //                    SELECT distinct PARTKEY,CELL_A,'0',LAC_A ,'0','0','0',0,0,0,0,'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'\r\n" + 
        String selectSqlcell="select distinct t.CELL_A,'0',t.LAC_A,'0','0','0',0,0,0,0,'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0' from (SELECT o.CELL_A,o.LAC_A,o.CHARGE1 FROM [dbo].[RATED_EVENTS_NEW_COPY] o  INNER JOIN [dbo].[WALLET] i ON o.ACCT_RES_ID1 = i.ID_WALLET and o.ACCT_RES_ID1 =1 and (o.ID_SERVICE_TYPE=1 or o.ID_SERVICE_TYPE=2 or o.ID_SERVICE_TYPE=3)  and (o.CHARGE1 > 0 or o.CHARGE2 > 0 or o.CHARGE3 > 0 or o.CHARGE4 > 0) and o.PARTKEY='"+  VPARTKEY +"' and i.IS_CURRENCY='Y') t";
        System.out.println(selectSqlcell);         
        resultSetcell = statementcell.executeQuery(selectSqlcell);
        while (resultSetcell.next()) {
 
                           // convert date from number to date in oracle
                           String vdate,vyear,vmonth,vday;
                           vdate=VPARTKEY;
                           vyear=vdate.substring(0,4);
                           vmonth=vdate.substring(4,6);
                           vday=vdate.substring(6,8);
                           System.out.println("vdate " + vdate);
                           System.out.println("vyear " + vyear);
                           System.out.println("vmonth " + vmonth);
                           System.out.println("vday " + vday);
                           bundledate=vyear + "-"+ vmonth +"-"+ vday;
                           vcellname="0";
                           
                           // call to get voice revenue by cell id
                           voicerev= Getvoice_cell_revenue(resultSetcell.getString(1).toString(),resultSetcell.getString(3).toString(),VPARTKEY);
                           
                           
                          // call to get sms revenue by cell id
                           smsrev=Getsms_cell_revenue(resultSetcell.getString(1).toString(),resultSetcell.getString(3).toString(),VPARTKEY);
                          
                           
                           // call to get data revenue by cell id
                           datarev=Getdata_cell_revenue(resultSetcell.getString(1).toString(),resultSetcell.getString(3).toString(),VPARTKEY);
                          
                           
                           vasrev=0;
                           
                           // call to get cell info by cell id
                           
                           if (StringUtils.equalsIgnoreCase (resultSetcell.getString(1),null)) {
                        	   vcellname="PSTN";
                       	} else {
                       		
                       		
                       		
                       		Getcellinfo_cell_revenue(resultSetcell.getString(1).toString(),resultSetcell.getString(3).toString(),VPARTKEY);
                       	}
                          
                           
                           //fill data in table PREPAID_PAYG_REVENUE
                           
                           // Get cellprimary key from sequence
                           Gcellid=localgetseqNbr(0);
                           Gcellid=Gyear+"_"+Gcellid;
                           String vareaname="0";
                           String vregionid="0";
                           String vregionname="0";
 
                           GetDatafrombundle_revenue(Gcellid,bundledate,resultSetcell.getString(1),vcellname,resultSetcell.getString(3),"0",resultSetcell.getString(4),vsiteid,vsitename,resultSetcell.getString(6),vareaname,vregionid,vregionname,voicerev,smsrev,datarev,vasrev,vtech2g,vtech3g,vtech4g,vtech5g,vcommreg,vsalesarea,vlng,vlat,vtechreg,vkynprov,vsitecluster,vclustertype,vruralsitetype,vurban,vmetrocluster,vsublocation,vinoudoor);

                       }  // end save data ib PREAPID PAYG REVENUE TABLE

        resultSetcell.close();
        
    	//  }  catch (Exception e) {
		//       System.out.println(e.toString());
		 //      System.out.println(" Error: getdata");
		//   }
    	
    }
    
    private static String localgetseqNbr(int n1) throws SQLException {
        String min = null ;

    			Statement stmt2 = null;
    			Statement stmttype = null;
    			Statement stmtdetail = null;  
         
             
           String SeqName = null;
        switch(n1)
        {
        case 0:
    		SeqName = "PREPAID_PAYG_REVENUE_SEQ";
    		break;

        }
          String query2 = "select "+SeqName+".nextval as nbr from dual";      
              stmttype = con.createStatement();
              ResultSet rs2 = stmttype.executeQuery(query2);
             
              while (rs2.next()) {
               min= rs2.getString("nbr");    
              	}
              rs2.close();

              stmttype.close();

    			 return min;

      }

}
