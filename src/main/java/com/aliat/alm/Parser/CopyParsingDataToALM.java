package com.aliat.alm.Parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class CopyParsingDataToALM {
	static Connection con;
	static Connection conalm;
    static String Gyear ;
	static PreparedStatement stmtp;
	static HashMap<String, String> vhmap = new HashMap<String, String>();
	static String[] hider =null;
	static String[] ResultNode =null;
	static String projpath=null;
	static String vadata=null;
	static int totrow=0;
	static int SumRow=0;
	static int totSumRow=0;
	static int totSumCol=0;
	static String filetype =null;
	static String tech2 ="0";
	static String tech3 ="0";
	static String tech4 ="0";
	static String tech5 ="0";
	static String codeid="0";
	static String circleid="0";
	static String attributeTable=null;
	static Logger logger;
	static FileHandler fh;
	static BufferedReader objReader1 = null;
	static String strCurrentLine1;
	static String logpath;
	static String db1path;
	static String db2path;
	static String readfileAIMfrom;
	static String copyfileAIMto;
	static String username1;
	static String password1;
	static String username2;
	static String password2;
	static String Gprovider;
	static String gdomaine;
	static String logsid="0";
	static int nbOfErrors = 0;
	
	public static void main(String[] args) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {
		//try {
		    // Read all required paths ex(DB1,DB2,Log file, Path to read file AIM ,path to copy processed)
					
		//objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almconfig.dat"));
				Resource ConfigResource = new ClassPathResource("almconfig.dat");
				File configfile = ConfigResource.getFile();
				FileReader fr=new FileReader(configfile);  
				objReader1=new BufferedReader(fr);
			 while ((strCurrentLine1 = objReader1.readLine()) != null){
				 String data = strCurrentLine1;
				 String[] data1 ;
				 if (data.contains("logpath")) {
					 data1=data.split(";",-1);
					 logpath=data1[1];
					 //System.out.println("logpath found :" + logpath);
				 }
				 if (data.contains("db1path")) {
					 data1=data.split(";",-1);
					 db1path=data1[1];
					 username1=data1[2];
					 password1=data1[3];
					 //System.out.println("db1path found :" + db1path);
				 }
				 if (data.contains("db2path")) {
					 data1=data.split(";",-1);
					 db2path=data1[1];
					 username2=data1[2];
					 password2=data1[3];
					 //System.out.println("db2path found :" + db2path);
				 }
			
			}
			 objReader1.close();
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			String lofilename="CopyParsingToALM-"+dtf.format(now)+".log";
			
			//get only year from today date
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	LocalDateTime now1 = LocalDateTime.now();
        	Gyear=dtf1.format(now1).substring(0,4);
        	//System.out.println(Gyear);  

			logger = Logger.getLogger("MyLog"); 
			logger.setUseParentHandlers(false);

						
			// This block configure the logger with handler and formatter  and PATH
			
	        fh = new FileHandler(logpath+"/"+lofilename);
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);

				DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
				//conalm = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:alm","alm","alm");
				conalm = DriverManager.getConnection (db1path,username1,password1);

				try {
				    //con= DriverManager.getConnection(dbURL,username,password);
				    con= DriverManager.getConnection(db2path,username2,password2);
				System.out.println("Connected to oracle DB");
				} catch (SQLException e) {
				       System.out.println("Opss, error");
				       e.printStackTrace();
				   }
				
				//logsid = localgetseqNbr(0);
				 //logsid=Gyear+"_"+ "LOGS"+'_'+logsid;
				 
				 
			
				// check if load ending to start copying it
				Statement stmtinit2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
		    	 String sqlStmtinit2 = "select distinct DOMAIN,VENDOR from EXECUTE_DOAMIN_VENDOR_FILES where STATUS='COMPLETED'";          
				    ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
				    rsinit2.last();
			 	    int totalrecinit2 = rsinit2.getRow(); 
			 	   rsinit2.beforeFirst();
			 	   if (totalrecinit2 > 0 ) {
				 		  while (rsinit2.next()) {
				 			 
				 			 Timestamp startTime = new Timestamp(System.currentTimeMillis());
				 			 
				 			logsid = localgetseqNbr(0);
							  logsid=Gyear+"_"+ "LOGS"+'_'+logsid;
							  
				 			 
				 			if (StringUtils.equalsIgnoreCase (rsinit2.getString("DOMAIN"),"Mobile Access Domain CIM")) {
				 				gdomaine="Mobile Access Domain";
				 			}else {
				 				gdomaine=rsinit2.getString("DOMAIN");
				 			}
				 			  
				 			/*PreparedStatement insertLogsstmt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS (LOGS_ID,START_TIME,ACTIVITY_NAME,VENDOR,DOMAIN,STOP_TIME) "
							 		+ "values('"+logsid+"',?,'CopyParsingToALM','"+ rsinit2.getString("VENDOR") +"','"+gdomaine+"',sysdate) ");
				 		
				 		insertLogsstmt.setTimestamp(1, startTime);
						insertLogsstmt.executeUpdate();
						insertLogsstmt.close();*/
				 			
				 			System.out.println("In process to move Data of  " + gdomaine +","+ rsinit2.getString("VENDOR") +"   to TEMP Tables" );
							// Delete all data from temp node tables in ALM schema
							deleteTempNodeTables(gdomaine,rsinit2.getString("VENDOR"));
					        // copy all data from node tables to TEMP Tables
							RunCopydata(gdomaine,rsinit2.getString("VENDOR")); 
							System.out.println("COPYING DATA OF  " + gdomaine +","+ rsinit2.getString("VENDOR") + "   TO TEMP Tables COMPLETED !" );
							logger.info("COPYING DATA OF  " + gdomaine +","+ rsinit2.getString("VENDOR") + "   TO TEMP Tables COMPLETED !");
							
							//insert into AUTO_DISCOVERY_LOGS_DETAILS
							
							  
							String logsDetailsid= localgetseqNbr(1);
							logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
							PreparedStatement insertLogsDetailsstmt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
		 					 		+ "values('"+logsDetailsid+"',sysdate ,'CopyParsingToALM','COPYING DATA OF  " + gdomaine +","+ rsinit2.getString("VENDOR") + "   TO TEMP Tables','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+gdomaine+"','"+logsid+"') ");
		 					 		
							insertLogsDetailsstmt.executeUpdate();
							insertLogsDetailsstmt.close();
							
							System.out.println("In process to Delete data of   " + gdomaine +","+ rsinit2.getString("VENDOR") + "   from Node Tables" );
					        // Delete data from Node Tables
								DeletedatafromNodeTables(gdomaine,rsinit2.getString("VENDOR"));
							System.out.println("DELETE DATA OF   " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") + "  FROM NODE Tables COMPLETED !" );
							logger.info("DELETE DATA OF   " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") + "  FROM NODE Tables COMPLETED !");
							
							//insert into AUTO_DISCOVERY_LOGS_DETAILS
							String logsDetailsId= localgetseqNbr(1);
							logsDetailsId=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsId;
							PreparedStatement insertLogsDetailsStmt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
		 					 		+ "values('"+logsDetailsId+"',sysdate ,'CopyParsingToALM','DELETE DATA OF   " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") + "  FROM NODE Tables','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+gdomaine+"','"+logsid+"') ");
		 					 		
							insertLogsDetailsStmt.executeUpdate();
							insertLogsDetailsStmt.close();
							
							 PreparedStatement stmtinit = con.prepareStatement("update EXECUTE_DOAMIN_VENDOR_FILES set STATUS ='TERMINATED' where DOMAIN= '" + rsinit2.getString("DOMAIN") + "' and VENDOR='" + rsinit2.getString("VENDOR") + "' and STATUS='COMPLETED'");
							 stmtinit.executeUpdate();
							 stmtinit.close();
							 
							 
							//insert into AUTO_DISCOVERY_LOGS_DETAILS
								String logsDetailsID= localgetseqNbr(1);
								logsDetailsID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsID;
								PreparedStatement insertLogsDEtailsStmt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
			 					 		+ "values('"+logsDetailsID+"',sysdate ,'CopyParsingToALM','Number Of Errors','','Number Of Errors','','"+nbOfErrors+"','"+ rsinit2.getString("VENDOR") +"','"+gdomaine+"','"+logsid+"') ");
			 					 		
								insertLogsDEtailsStmt.executeUpdate();
								insertLogsDEtailsStmt.close();
								//logsid = localgetseqNbr(0);
							  //logsid=Gyear+"_"+ "LOGS"+'_'+logsid;
							 PreparedStatement insertLogsstmt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS (LOGS_ID,START_TIME,ACTIVITY_NAME,VENDOR,DOMAIN,STOP_TIME) "
								 		+ "values('"+logsid+"',?,'CopyParsingToALM','"+ rsinit2.getString("VENDOR") +"','"+gdomaine+"',?) ");
					 		
					 		insertLogsstmt.setTimestamp(1, startTime);
					 		insertLogsstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
							insertLogsstmt.executeUpdate();
							insertLogsstmt.close();
							
							//updating the site if found in the final table and not found in the temporary
							UpdatingSiteinTemp(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR") );
							
				 		  }
			 	   } else {
			 		   System.out.println("No completed or loaded data to copy");
			 		  logger.info("No completed or loaded data to copy");
			 		  
			 		  //insert into AUTO_DISCOVERY_LOGS_DETAILS
			 		 String logsDetailsId= localgetseqNbr(1);
						logsDetailsId=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsId;
						PreparedStatement insertLogsDetailsStmt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDetailsId+"',sysdate ,'CopyParsingToALM','No completed or loaded data to copy','','','No Completed','','','"+gdomaine+"','"+logsid+"') ");
	 					 		
						insertLogsDetailsStmt.executeUpdate();
						insertLogsDetailsStmt.close();
			 		  
			 	   }
			 	   
			 	 
				
			 	  rsinit2.close();
			 	  stmtinit2.close();

			
			
				
			con.close();
			conalm.close();
		//} // end try
		//	catch(Exception e)  
		//	{  
		//	logger.info("error at main is :"+ e.toString());
		//	System.out.println("error at main is :"+ e.toString()); 
		//	} 
		
	   }
	
	 private static void deleteTempNodeTables(String vdomain, String vvendor) throws SQLException  {
		 try {
		 // delete all rows related to node_pk from all nodes tables
		 PreparedStatement stmt = conalm.prepareStatement("delete from TEMP_NODE_ACTIVE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' "); 
	     stmt.executeUpdate();
	     stmt.close();
	     
	     PreparedStatement stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_ACTIVE_ATTRIBUTE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt1.executeUpdate();
	     stmt1.close();
	      
	     PreparedStatement stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_RACK where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt2.executeUpdate();
	     stmt2.close();
	     
	     stmt = conalm.prepareStatement("delete from  TEMP_NODE_CABINET where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt.executeUpdate();
	     stmt.close();
	     
	     stmt = conalm.prepareStatement("delete from  TEMP_NODE_SHELF where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt.executeUpdate();
	     stmt.close();
	     		 
	     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_HOSTVER where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt1.executeUpdate();
	     stmt1.close();
	      
	     stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_FRAME where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt2.executeUpdate();
	     stmt2.close();
	      
	     stmt = conalm.prepareStatement("delete from  TEMP_NODE_SLOT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt.executeUpdate();
	     stmt.close(); 
	      		 
	     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_BOARD where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt1.executeUpdate();
	     stmt1.close();
	     
	     stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_PORT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt2.executeUpdate();
	     stmt2.close();
	     
	     stmt = conalm.prepareStatement("delete from  TEMP_NODE_ACCESSORY where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt.executeUpdate();
	     stmt.close(); 
	     		 
	     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_HOST where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt1.executeUpdate();
	     stmt1.close();
	     
	     stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_SUBRACK where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt2.executeUpdate();
	     stmt2.close();
	     
	     stmt = conalm.prepareStatement("delete from  TEMP_NODE_GCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt.executeUpdate();
	     stmt.close(); 
	     		 
	     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_BTS where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt1.executeUpdate();
	     stmt1.close();
	     
	     stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_UCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt2.executeUpdate();
	     stmt2.close();
	     
	     stmt = conalm.prepareStatement("delete from  TEMP_NODE_ANTENNA where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt.executeUpdate();
	     stmt.close(); 
	     		 
	     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_LCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt1.executeUpdate();
	     stmt1.close();
	     
	     stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_RRN where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt2.executeUpdate();
	     stmt2.close();
	     
	     stmt = conalm.prepareStatement("delete from  TEMP_NODE_ENODEBCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt.executeUpdate();
	     stmt.close(); 
	     		 
	     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_NODEBCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt1.executeUpdate();
	     stmt1.close();
	     
		 stmt = conalm.prepareStatement("delete from  TEMP_NODE_NBINTERFACES where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt.executeUpdate();
	     stmt.close();
	     
	     stmt = conalm.prepareStatement("delete from  TEMP_NODE_CHILD_PARENT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt.executeUpdate();
	     stmt.close();
	     
	     stmt = conalm.prepareStatement("delete from  TEMP_NODE_MODULE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt.executeUpdate();
	     stmt.close(); 
	     
	     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_SUBMODULE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt1.executeUpdate();
	     stmt1.close();
	     
	     stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_FUUNIT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt2.executeUpdate();
	     stmt2.close();
		 }
	     catch(Exception e)  
			{  
			logger.info("error at deleteTempNodeTables is :"+ e.toString());
			System.out.println("error at deleteTempNodeTables is :"+ e.toString()); 
			
			//insert into AUTO_DISCOVERY_LOGS_DETAILS
			String logsDetailsId= localgetseqNbr(1);
			logsDetailsId=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsId;
			PreparedStatement insertLogsDetailsStmt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsId+"',sysdate ,'CopyParsingToALM','error at deleteTempNodeTables ','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
			insertLogsDetailsStmt.executeUpdate();
			insertLogsDetailsStmt.close();
			nbOfErrors++;
			} 
	     
	}
	 
	 private static void RunCopydata(String vdomain, String vvendor) throws InterruptedException, SQLException, ParseException {
		 Statement stmtp=null;
		 PreparedStatement stmt0;
		 PreparedStatement stmt1;
		 PreparedStatement stmt2;
		 String dat3;
		 String dat4;
		 
		 int totalNbCopied = 0;
		 //try {
		 //Insert into TEMP_NODE_ACTIVE
		 System.out.println("Copy to TEMP_NODE_ACTIVE in process...");
		 logger.info("Copy to TEMP_NODE_ACTIVE in process...");
		 String queryp = "select * from NODE_ACTIVE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
		 stmtp = con.createStatement();
	     ResultSet rsp = stmtp.executeQuery(queryp);
	     
			int nbOfNodeActive=0;
		 while (rsp.next()) {
			 System.out.println("count : "+rsp.getRow());
			 System.out.println("Node_PK : "+rsp.getString("NODE_PK") );
			 System.out.println("UNIQUE_NODE_ID : "+rsp.getString("UNIQUE_NODE_ID") );
			 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME,TO_TRANS_SOURCE,IP_ADDRESS,MAC_ADDRESS,SUB_DOMAIN,SOFTWARE_VERSION,LONGITUDE,LATITUDE,PATCH_VERSION,PART_NUMBER,SUB_DOMAIN_TYPE,OTHERS,SERIAL_NUMBER) "
				 		+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			 stmt0.setString(1, rsp.getString("NODE_PK"));  			stmt0.setString(25, rsp.getString("LINE")); 			
			 stmt0.setString(2, rsp.getString("UNIQUE_NODE_ID"));    	stmt0.setString(26,rsp.getString("WARE_ID"));			
			 stmt0.setString(3, rsp.getString("NODE_ID")); 			 	stmt0.setString(27, rsp.getString("VENDOR") ); 			
			 stmt0.setString(4, rsp.getString("NODE_NAME"));  		 	stmt0.setString(28, rsp.getString("SUPPLIER_ID") ); 	
			 stmt0.setString(5, rsp.getString("NODE_TYPE"));  		 	stmt0.setString(29, rsp.getString("WARE_NAME")); 		
			 stmt0.setString(6, rsp.getString("DOMAIN"));  				stmt0.setString(30, rsp.getString("SUPPLIER_NAME")); 	
			 stmt0.setString(7, rsp.getString("NODE_SOURCE")); 			stmt0.setString(31, rsp.getString("TO_TRANS_SOURCE")); 	
			 stmt0.setString(8, rsp.getString("NODE_MODEL"));  		 	stmt0.setString(32,rsp.getString("IP_ADDRESS")); 		
			 stmt0.setString(9, rsp.getString("TECH_2G"));  		 	stmt0.setString(33, rsp.getString("MAC_ADDRESS")); 		
			 stmt0.setString(10, rsp.getString("TECH_3G"));  		 	stmt0.setString(34,rsp.getString("SUB_DOMAIN") );
			 stmt0.setString(11, rsp.getString("TECH_4G"));  		 	stmt0.setString(35,rsp.getString("SOFTWARE_VERSION"));
			 stmt0.setString(12, rsp.getString("TECH_5G"));  			stmt0.setString(36,rsp.getString("LONGITUDE"));
			 stmt0.setString(13, rsp.getString("SITE_ID"));				stmt0.setString(37, rsp.getString("LATITUDE"));
			 stmt0.setString(14,rsp.getString("CIRCLE_ID"));			stmt0.setString(38, rsp.getString("PATCH_VERSION"));
			 stmt0.setTimestamp(15, rsp.getTimestamp("CREATION_DATE"));	stmt0.setString(39, rsp.getString("PART_NUMBER"));
			 stmt0.setTimestamp(16, rsp.getTimestamp("UPDATE_DATE"));	stmt0.setString(40, rsp.getString("SUB_DOMAIN_TYPE"));
			 stmt0.setString(17, rsp.getString("FILE_TYPE"));			stmt0.setString(41, rsp.getString("OTHERS"));
			 stmt0.setString(18, rsp.getString("FILENAME"));			stmt0.setString(42, rsp.getString("SERIAL_NUMBER"));		
			 stmt0.setString(19, rsp.getString("STATUS"));				
			 stmt0.setString(20,  rsp.getString("FROM_TRANS_SOURCE"));	
			 stmt0.setString(21,rsp.getString("FROM_TRANS_ID"));		
			 stmt0.setString(22, rsp.getString("TO_TRANS_ID"));
			 stmt0.setString(23, rsp.getString("TRANS_TYPE"));
			 stmt0.setString(24, "1");
			 stmt0.executeUpdate();      
		     stmt0.close();
		     nbOfNodeActive++;
	     }
		  
		 rsp.close();
		 stmtp.close();
		 //totalNbCopied = totalNbCopied + nbOfNodeActive;
		    System.out.println("Copy to TEMP_NODE_ACTIVE COMPLETED");
		    logger.info("Copy to TEMP_NODE_ACTIVE COMPLETED");
		    
		    //insert into AUTO_DISCOVERY_LOGS_DETAILS
		    String logsDetailsid= localgetseqNbr(1);
			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
			PreparedStatement insertLogsDetailsstmt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
			 		+ "values('"+logsDetailsid+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_ACTIVE COMPLETED','NODE_ACTIVE','Number of NODE_ACTIVE','Completed','"+nbOfNodeActive+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
			 		
			insertLogsDetailsstmt.executeUpdate();
			insertLogsDetailsstmt.close();
		    
		 	//Insert into TEMP_NODE_ACTIVE_ATTRIBUTE
		    System.out.println("Copy to TEMP_NODE_ACTIVE_ATTRIBUTE in process...");
			 logger.info("Copy to TEMP_NODE_ACTIVE_ATTRIBUTE in process...");
			
			 
			 queryp = "select * from NODE_ACTIVE_ATTRIBUTE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
		     int nbOfNodeActiveAttribute = 0;
			 while (rsp.next()) {
				 stmt1 = conalm.prepareStatement("insert into TEMP_NODE_ACTIVE_ATTRIBUTE (NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE)"
				 		+ "values ('" + rsp.getString("NODE_ATTR_PK") +"','" + rsp.getString("ATTRIBUTE") +"','" + rsp.getString("ATTRIBUTE_TABLE") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_TYPE") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("LINE") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"','" + rsp.getString("TO_TRANS_SOURCE") +"')");
			     stmt1.executeUpdate();
			     stmt1.close();
			     nbOfNodeActiveAttribute++;
		     }
			 rsp.close();
			 stmtp.close();
			 //totalNbCopied = totalNbCopied + nbOfNodeActiveAttribute;
			 System.out.println("Copy to TEMP_NODE_ACTIVE_ATTRIBUTE COMPLETED");
			 logger.info("Copy to TEMP_NODE_ACTIVE_ATTRIBUTE COMPLETED");
	        
			 //insert into AUTO_DISCOVERY_LOGS_DETAILS
			 String logsDetailsiD= localgetseqNbr(1);
			 logsDetailsiD=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsiD;
				PreparedStatement insertLogsDetailsStatement = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsiD+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_ACTIVE_ATTRIBUTE COMPLETED','ACTIVE_ATTRIBUTE','Number of NODE_ACTIVE_ATTRIBUTE','Completed','"+nbOfNodeActiveAttribute+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
				 		
				insertLogsDetailsStatement.executeUpdate();
				insertLogsDetailsStatement.close();
			 
			 //Insert into TEMP_NODE_RACK
			 System.out.println("Copy to TEMP_NODE_RACK in process...");
			 logger.info("Copy to TEMP_NODE_RACK in process...");
			 
			 
			 queryp = "select * from NODE_RACK where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
		     int nbOfNodeRack =0;
			 while (rsp.next()) {
				 dat3=rsp.getString("DATEOFMANUFACTURE");
				 if(dat3 !=null && dat3 !="null") {
					 dat3 = dat3.substring(0, 10) ;
					 } 
				 else {
					 dat3=""; 
				 }
				 dat4=rsp.getString("DATEOFLASTSERVICE");
				if(dat4 !=null && dat4 !="null") { 
					dat4 = dat4.substring(0, 10) ;
					}
				else {
					 dat4=""; 
				 }
				 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_RACK (RACK_ID,RACKNO,INVENTORYUNITID,RACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
				 		+ "values ('" + rsp.getString("RACK_ID") +"','" + rsp.getString("RACKNO") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("RACKTYPE") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION")  +"',TO_DATE('" +dat3+"','YYYY-MM-DD')"+",TO_DATE('" +dat4+"','YYYY-MM-DD')" +",'" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("MODEL") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' )");
				 stmt2.executeUpdate();
				 stmt2.close();
				 nbOfNodeRack++;
		     }
			 rsp.close();
			 stmtp.close();
			 totalNbCopied = totalNbCopied + nbOfNodeRack;
			 System.out.println("Copy to TEMP_NODE_RACK COMPLETED");
			 logger.info("Copy to TEMP_NODE_RACK COMPLETED");
	        
			 //insert into AUTO_DISCOVERY_LOGS_DETAILS
			 String logsDetailsiDd= localgetseqNbr(1);
			 logsDetailsiDd=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsiDd;
				PreparedStatement insertLogsDetailsStatmnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsiDd+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_RACK COMPLETED','RACK','Number of NODE_RACK','Completed','"+nbOfNodeRack+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
				 		
				insertLogsDetailsStatmnt.executeUpdate();
				insertLogsDetailsStatmnt.close();
			 ///added
			 //Insert into TEMP_NODE_SHELF
				System.out.println("Copy to TEMP_NODE_SHELF in process...");
				 logger.info("Copy to TEMP_NODE_SHELF in process...");
				 
				 
				 queryp = "select * from NODE_SHELF where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     int nbOfNodeSHELF =0;
				 while (rsp.next()) {
					 dat3=rsp.getString("DATEOFMANUFACTURE");
					 if(dat3 !=null && dat3 !="null") {
						 dat3 = dat3.substring(0, 10) ;
						 } 
					 else {
						 dat3=""; 
					 }
					 dat4=rsp.getString("DATEOFLASTSERVICE");
					if(dat4 !=null && dat4 !="null") { 
						dat4 = dat4.substring(0, 10) ;
						}
					else {
						 dat4=""; 
					 }
					 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_SHELF (SHELF_ID,SHELFNO,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORNAME,SERIALNUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR,VENDORUNITTYPENUMBER,HARDWAREVERSION,MODEL,STATUS) "
       		   		+ "values('" + rsp.getString("SHELF_ID") +"','" + rsp.getString("SHELFNO") +"','" + rsp.getString("INVENTORYUNITID")  +"','" + rsp.getString("INVENTORYUNITTYPE")  +"','" + rsp.getString("VENDORUNITFAMILYTYPE")  +"','" +rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"',TO_DATE('" + dat3 +"','YYYY-MM-DD')" +",TO_DATE('" + dat4 +"','YYYY-MM-DD')" +",'" + rsp.getString("UNITPOSITION")  +"','" + rsp.getString("MANUFACTURERDATA")  +"','" + rsp.getString("USERLABEL")  +"','" + rsp.getString("NODE_PK")  +"','" + rsp.getString("NODE_ATTR_PK")  +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','"+ rsp.getString("FILENAME")  +"','"+rsp.getString("FROM_TRANS_SOURCE") +"','"+rsp.getString("TO_TRANS_SOURCE") +"','"+rsp.getString("FROM_TRANS_ID") +"','"+rsp.getString("TO_TRANS_ID") +"','"+rsp.getString("TRANS_TYPE") +"','"+rsp.getString("LINE") +"','"+rsp.getString("ACTIVE_RECORD") +"','"+rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR")+"','"+ rsp.getString("VENDORUNITTYPENUMBER")+"','" + rsp.getString("HARDWAREVERSION")+"','" + rsp.getString("MODEL")+"','" + rsp.getString("STATUS") +"' ) ");
					 
				
					 stmt2.executeUpdate();
					 stmt2.close();
					 nbOfNodeSHELF++;
			     }
				 rsp.close();
				 stmtp.close();
				 totalNbCopied = totalNbCopied + nbOfNodeSHELF;
				 System.out.println("Copy to TEMP_NODE_SHELF COMPLETED");
				 logger.info("Copy to TEMP_NODE_SHELF COMPLETED");
		        
				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logsDetailsiDdD= localgetseqNbr(1);
				 logsDetailsiDdD=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsiDdD;
					PreparedStatement insertLogsDetailsStatmnt1 = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDetailsiDdD+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_SHELF COMPLETED','SHELF','Number of NODE_SHELF','Completed','"+nbOfNodeSHELF+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insertLogsDetailsStatmnt1.executeUpdate();
					insertLogsDetailsStatmnt1.close();
				///END ADDED
				 
	        //Insert into TEMP_NODE_CABINET
			 System.out.println("Copy to TEMP_NODE_CABINET in process...");
			 logger.info("Copy to TEMP_NODE_CABINET in process...");
			
			 
			 queryp = "select * from NODE_CABINET where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
		     
				int nbOfNodeCabinet = 0;
			 while (rsp.next()) {
				 dat3=rsp.getString("DATEOFMANUFACTURE");
				 if(dat3 !=null && dat3 !="null") {
					 dat3 = dat3.substring(0, 10) ;
					 } 
				 else {
					 dat3=""; 
				 }
				 dat4=rsp.getString("DATEOFLASTSERVICE");
				if(dat4 !=null && dat4 !="null") { 
					dat4 = dat4.substring(0, 10) ;
					}
				 else {
					 dat4=""; 
				 }
				 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,OTHERS) "
				 		+ "values ('" + rsp.getString("CABINET_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("CABINETNO") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("RACKTYPE") +"','" + rsp.getString("BOMRACKTYPE") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION") +"',TO_DATE('" +dat3+"','YYYY-MM-DD')"+",TO_DATE('" +dat4+"','YYYY-MM-DD')" +",'" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("ISSUENUMBER") +"','" + rsp.getString("BOMCODE") +"','" + rsp.getString("EXTINFO") +"','" + rsp.getString("MODEL") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("SHAREMODE") +"','" + rsp.getString("CLEICODE") +"','" + rsp.getString("BOM") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("ALM_POSITION") +"',TIMESTAMP '" + rsp.getString("CREATION_DATE") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR")+"','"+rsp.getString("OTHERS") +"')");
				 stmt0.executeUpdate();
				 stmt0.close();
				 nbOfNodeCabinet++;
		     }
			 rsp.close();
			 stmtp.close();
			 totalNbCopied = totalNbCopied + nbOfNodeCabinet;
			 System.out.println("Copy to TEMP_NODE_CABINET COMPLETED");
			 logger.info("Copy to TEMP_NODE_CABINET COMPLETED");
	        
			 //insert into AUTO_DISCOVERY_LOGS_DETAILS
			 String logsDetailsIdd= localgetseqNbr(1);
			 logsDetailsIdd=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsIdd;
				PreparedStatement insertLogsDetailsstatemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsIdd+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_CABINET COMPLETED','CABINET','Number of NODE_CABINET','Completed','"+nbOfNodeCabinet+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
				 		
				insertLogsDetailsstatemnt.executeUpdate();
				insertLogsDetailsstatemnt.close();
			 
	      //Insert into TEMP_NODE_HOSTVER
			 System.out.println("Copy to TEMP_NODE_HOSTVER in process...");
			 logger.info("Copy to TEMP_NODE_HOSTVER in process...");
			 queryp = "select * from NODE_HOSTVER where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
		     
				int nbOfNodeHostver = 0;
			 while (rsp.next()) {
				 stmt1 = conalm.prepareStatement("insert into TEMP_NODE_HOSTVER (HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR) "
				 		+ "values ('" + rsp.getString("HOSTVER_ID") +"','" + rsp.getString("HOSTVERTYPE") +"','" + rsp.getString("HOSTVER") +"','" + rsp.getString("SDESC") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"',sysdate,'" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
			     stmt1.executeUpdate();
			     stmt1.close();
			     nbOfNodeHostver++;
		     }
			 rsp.close();
			 stmtp.close();
			 totalNbCopied = totalNbCopied + nbOfNodeHostver;
			 System.out.println("Copy to TEMP_NODE_HOSTVER COMPLETED");
			 logger.info("Copy to TEMP_NODE_HOSTVER COMPLETED");
			 
			 //insert into AUTO_DISCOVERY_LOGS_DETAILS
			 String logsDetailSIdd= localgetseqNbr(1);
			 logsDetailSIdd=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailSIdd;
				PreparedStatement insertLogsDetailSstatemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailSIdd+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_HOSTVER COMPLETED','HOSTVER','Number of NODE_HOSTVER','Completed','"+nbOfNodeHostver+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
				 		
				insertLogsDetailSstatemnt.executeUpdate();
				insertLogsDetailSstatemnt.close();
	           
	         //Insert into TEMP_NODE_FRAME
			 System.out.println("Copy to TEMP_NODE_FRAME in process...");
			 logger.info("Copy to TEMP_NODE_FRAME in process...");
			 queryp = "select * from NODE_FRAME where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
		     
				int nbOfNodeFrame = 0;
			 while (rsp.next()) {
				 dat3=rsp.getString("DATEOFMANUFACTURE");
				 if(dat3 !=null && dat3 !="null") {
					 dat3 = dat3.substring(0, 10) ;
					 } 
				 else {
					 dat3=""; 
				 }
				 dat4=rsp.getString("DATEOFLASTSERVICE");
				if(dat4 !=null && dat4 !="null") {
					dat4 = dat4.substring(0, 10) ;
							}
				 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_FRAME (FRAME_ID,RACKNO,FRAMENO,INVENTORYUNITID,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				 		+ "values ('" + rsp.getString("FRAME_ID") +"','" + rsp.getString("RACKNO") +"','" + rsp.getString("FRAMENO") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("FRAMETYPE") +"','" + rsp.getString("RACKFRAMENO") +"','" + rsp.getString("MODULENO") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION")  +"',TO_DATE('" +dat3+"','YYYY-MM-DD')"+",TO_DATE('" +dat4+"','YYYY-MM-DD')" +",'" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("MODEL") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt2.executeUpdate();
				 stmt2.close();
				 nbOfNodeFrame++;
		     }
			 rsp.close();
			 stmtp.close();
			 totalNbCopied = totalNbCopied + nbOfNodeFrame;
			 System.out.println("Copy to TEMP_NODE_FRAME COMPLETED");
			 logger.info("Copy to TEMP_NODE_FRAME COMPLETED");
			 
			 //insert into AUTO_DISCOVERY_LOGS_DETAILS
			 String logsDETailSIdd= localgetseqNbr(1);
			 logsDETailSIdd=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETailSIdd;
				PreparedStatement insertLogsDETailSstatemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDETailSIdd+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_FRAME COMPLETED','FRAME','Number of NODE_FRAME','Completed','"+nbOfNodeFrame+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
				 		
				insertLogsDETailSstatemnt.executeUpdate();
				insertLogsDETailSstatemnt.close();
	          
	          //Insert into TEMP_NODE_SLOT
			 System.out.println("Copy to TEMP_NODE_SLOT in process...");
			 logger.info("Copy to TEMP_NODE_SLOT in process...");
			 queryp = "select * from NODE_SLOT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
		     
				int nbOfNodeSlots = 0;
			 while (rsp.next()) {
				 dat3=rsp.getString("DATEOFMANUFACTURE");
				 if(dat3 !=null && dat3 !="null") {
					 dat3 = dat3.substring(0, 10) ;
					 } 
				 else {
					 dat3=""; 
				 }
				 dat4=rsp.getString("DATEOFLASTSERVICE");
				if(dat4 !=null && dat4 !="null") {
					dat4 = dat4.substring(0, 10) ;
					}
				 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_SLOT (SLOT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,DOMAIN,VENDOR) "
				 		+ "values ('" + rsp.getString("SLOT_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("CABINETNO") +"','" + rsp.getString("SUBRACKNO") +"','" + rsp.getString("RACKNO") +"','" + rsp.getString("FRAMENO") +"','" + rsp.getString("SLOTNO") +"','" + rsp.getString("SLOTPOS") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION")  +"',TO_DATE('" +dat3+"','YYYY-MM-DD')"+",TO_DATE('" +dat4+"','YYYY-MM-DD')" +",'" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("ALM_POSITION") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt0.executeUpdate();
				 stmt0.close();
				 nbOfNodeSlots++;
		     }
			 rsp.close();
			 stmtp.close();
			 totalNbCopied = totalNbCopied + nbOfNodeSlots;
			 System.out.println("Copy to TEMP_NODE_SLOT COMPLETED");
			 logger.info("Copy to TEMP_NODE_SLOT COMPLETED");
			 
			 //insert into AUTO_DISCOVERY_LOGS_DETAILS
			 String logsDETAIlSIdd= localgetseqNbr(1);
			 logsDETAIlSIdd=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETAIlSIdd;
				PreparedStatement insertLogsDETAIlSstatemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDETAIlSIdd+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_SLOT COMPLETED','SLOT','Number of NODE_SLOT','Completed','"+nbOfNodeSlots+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
				 		
				insertLogsDETAIlSstatemnt.executeUpdate();
				insertLogsDETAIlSstatemnt.close();
			 
	          
	        //Insert into TEMP_NODE_BOARD
			 System.out.println("Copy to TEMP_NODE_BOARD in process...");
			 logger.info("Copy to TEMP_NODE_BOARD in process...");
			 queryp = "select * from NODE_BOARD where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
		     
				int nbOfNodeBoard = 0;
			 while (rsp.next()) {
				 dat3=rsp.getString("DATEOFMANUFACTURE");
				 //System.out.println("dat3 "+dat3);
				 if(dat3 !=null && dat3 !="null" && dat3 !="(null)") {
					 dat3 = dat3.substring(0, 10) ;
					 } 
				 else {
					 dat3=""; 
				 }
				 dat4=rsp.getString("DATEOFLASTSERVICE");
				// System.out.println("dat4 "+dat4);
				if(dat4 !=null && dat4 !="null" && dat4 !="(null)") { 
					dat4 = dat4.substring(0, 10) ;
					}
				else {
					 dat4=""; 
				 }
				 stmt1 = conalm.prepareStatement("insert into TEMP_NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,OTHERS)"
				 		+ "values ('" + rsp.getString("BOARD_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("CABINETNO") +"','" + rsp.getString("SUBRACKNO") +"','" + rsp.getString("RACKNO") +"','" + rsp.getString("FRAMENO") +"','" + rsp.getString("SLOTNO") +"','" + rsp.getString("SLOTPOS") +"','" + rsp.getString("SUBSLOTNO") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("MODULENO") +"','" + rsp.getString("BOARDNAME") +"','" + rsp.getString("BOARDTYPE") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION")  +"',TO_DATE('" +dat3+"','YYYY-MM-DD')"+",TO_DATE('" +dat4+"','YYYY-MM-DD')" +",'" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("SOFTVER") +"','" + rsp.getString("LOGICVER") +"','" + rsp.getString("BIOSVER") +"','" + rsp.getString("BIOSVEREX") +"','" + rsp.getString("LANVER") +"','" + rsp.getString("MBUSVER") +"','" + rsp.getString("ISSUENUMBER") +"','" + rsp.getString("BOMCODE") +"','" + rsp.getString("MODEL") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("EXTINFO") +"','" + rsp.getString("APDEVINFO") +"','" + rsp.getString("WORKMODE") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("ALM_POSITION") +"',TIMESTAMP '" + rsp.getString("CREATION_DATE") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR")+"','"+rsp.getString("OTHERS") +"' )");
				 stmt1.executeUpdate();
				 stmt1.close();
				 nbOfNodeBoard++;
		     }
			 rsp.close();
			 stmtp.close();
			 totalNbCopied = totalNbCopied + nbOfNodeBoard;
			 System.out.println("Copy to TEMP_NODE_BOARD COMPLETED");
			 logger.info("Copy to TEMP_NODE_BOARD COMPLETED");
			 
			 //insert into AUTO_DISCOVERY_LOGS_DETAILS
			 String logsDETAILSIDd= localgetseqNbr(1);
			 logsDETAILSIDd=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETAILSIDd;
				PreparedStatement insertLogsDETAILSStatemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDETAILSIDd+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_BOARD COMPLETED','BOARD','Number of NODE_BOARD','Completed','"+nbOfNodeBoard+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
				 		
				insertLogsDETAILSStatemnt.executeUpdate();
				insertLogsDETAILSStatemnt.close();

	           //Insert into TEMP_NODE_PORT
			 System.out.println("Copy to TEMP_NODE_PORT in process...");
			 logger.info("Copy to TEMP_NODE_PORT in process...");
				 queryp = "select * from NODE_PORT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
					int nbOfNodePort = 0;
				 while (rsp.next()) {
					 dat3=rsp.getString("DATEOFMANUFACTURE");
					 if(dat3 !=null && dat3 !="null") {
						 dat3 = dat3.substring(0, 10) ;
						 } 
					 else {
						 dat3=""; 
					 }
					 dat4=rsp.getString("DATEOFLASTSERVICE");
					if(dat4 !=null && dat4 !="null") { 
						dat4 = dat4.substring(0, 10) ;
						}
					 else {
						 dat4=""; 
					 }
					 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_PORT (PORT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,VENDORUNITFAMILYTYPE,INVENTORYUNITID,PORTNO,HARDWAREVERSION,SERIALNUMBER,INVENTORYUNITTYPE,VENDORNAME,VENDORUNITTYPENUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MACADDR,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR,OTHERS,PORTTYPE,PORTRATE)"
					 		+ "values ('" + rsp.getString("PORT_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("CABINETNO") +"','" + rsp.getString("SUBRACKNO") +"','" + rsp.getString("RACKNO") +"','" + rsp.getString("FRAMENO") +"','" + rsp.getString("SLOTNO") +"','" + rsp.getString("SLOTPOS") +"','" + rsp.getString("SUBSLOTNO") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("PORTNO") +"','" + rsp.getString("HARDWAREVERSION") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("VENDORUNITTYPENUMBER")  +"',TO_DATE('" +dat3+"','YYYY-MM-DD')"+",TO_DATE('" +dat4+"','YYYY-MM-DD')" +",'" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MACADDR") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR")+"','"+rsp.getString("OTHERS")+"','"+rsp.getString("PORTTYPE")+"','"+rsp.getString("PORTRATE") +"' )");
					 stmt2.executeUpdate();
					 stmt2.close();
					 nbOfNodePort++;
			     }
				 rsp.close();
				 stmtp.close();
				 
				 totalNbCopied = totalNbCopied + nbOfNodePort;
				 System.out.println("Copy to TEMP_NODE_PORT COMPLETED");
				 logger.info("Copy to TEMP_NODE_PORT COMPLETED");
	            
				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logSDETAILSID= localgetseqNbr(1);
				 logSDETAILSID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logSDETAILSID;
					PreparedStatement insertLogSDETAILStatemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logSDETAILSID+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_PORT COMPLETED','PORT','Number of NODE_PORT','Completed','"+nbOfNodePort+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insertLogSDETAILStatemnt.executeUpdate();
					insertLogSDETAILStatemnt.close();
				 
	            //Insert into TEMP_NODE_ACCESSORY
				 System.out.println("Copy to TEMP_NODE_ACCESSORY in process...");
				 logger.info("Copy to TEMP_NODE_ACCESSORY in process...");
				 
				 queryp = "select * from NODE_ACCESSORY where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNodeAccessory = 0;
				 while (rsp.next()) {
					 dat3=rsp.getString("DATEOFMANUFACTURE");
					 if(dat3 !=null && dat3 !="null") {
						 dat3 = dat3.substring(0, 10) ;
						 }
					 else {
						 dat3=""; 
					 }
					 dat4=rsp.getString("DATEOFLASTSERVICE");
					if(dat4 !=null && dat4 !="null") { 
						dat4 = dat4.substring(0, 10) ;
						}
					else {
						 dat4=""; 
					 }
					 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_ACCESSORY (ACCESSORY_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,ACCESSORYTYPE,ADDTIONALINFORMATION,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
					 		+ "values ('" + rsp.getString("ACCESSORY_ID") +"','" + rsp.getString("RACKPOSITION") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION") +"','" + rsp.getString("SOFTVER")  +"',TO_DATE('" +dat3+"','YYYY-MM-DD')"+",TO_DATE('" +dat4+"','YYYY-MM-DD')" +",'" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("ACCESSORYTYPE") +"','" + rsp.getString("ADDTIONALINFORMATION") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"' )");
					 stmt0.executeUpdate();
					 stmt0.close();
					 nbOfNodeAccessory++;
			     }
				 rsp.close();
				 stmtp.close();
				 
				 totalNbCopied = totalNbCopied + nbOfNodeAccessory;
				 System.out.println("Copy to TEMP_NODE_ACCESSORY COMPLETED");
				 logger.info("Copy to TEMP_NODE_ACCESSORY COMPLETED");

				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logsDETAILS_ID= localgetseqNbr(1);
				 logsDETAILS_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETAILS_ID;
					PreparedStatement insertLogsDETAILsStatemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDETAILS_ID+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_ACCESSORY COMPLETED','ACCESSORY','Number of NODE_ACCESSORY','Completed','"+nbOfNodeAccessory+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insertLogsDETAILsStatemnt.executeUpdate();
					insertLogsDETAILsStatemnt.close();
	          
	        //Insert into TEMP_NODE_HOST
				 System.out.println("Copy to TEMP_NODE_HOST in process...");
				 logger.info("Copy to TEMP_NODE_HOST in process...");
					
				 queryp = "select * from NODE_HOST where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNodeHost = 0;
				 while (rsp.next()) {
					 dat3=rsp.getString("DATEOFMANUFACTURE");
					 if(dat3 !=null && dat3 !="null") {
						 dat3 = dat3.substring(0, 10) ;
					 }
					 else {
						 dat3=""; 
					 }
				 dat4=rsp.getString("DATEOFLASTSERVICE");
					 if(dat4 !=null && dat4 !="null") {
						 dat4 = dat4.substring(0, 10) ;
					 }
					 else {
						 dat4=""; 
					 }
					 
					 stmt1 = conalm.prepareStatement("insert into TEMP_NODE_HOST (HOST_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,HOSTNAME,NUMBEROFCPU,MEMSIZE,HARDDISKSIZE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
					 		+ "values ('" + rsp.getString("HOST_ID") +"','" + rsp.getString("RACKPOSITION") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION") +"','" + rsp.getString("SOFTVER")  +"',TO_DATE('" +dat3+"','YYYY-MM-DD')"+",TO_DATE('" +dat4+"','YYYY-MM-DD')" +",'" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("HOSTNAME") +"','" + rsp.getString("NUMBEROFCPU") +"','" + rsp.getString("MEMSIZE") +"','" + rsp.getString("HARDDISKSIZE") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
					 stmt1.executeUpdate();
					 stmt1.close();
					 nbOfNodeHost++;
			     }
				 rsp.close();
				 stmtp.close();
				 
				 totalNbCopied = totalNbCopied + nbOfNodeHost;
				 System.out.println("Copy to TEMP_NODE_HOST COMPLETED");
				 logger.info("Copy to TEMP_NODE_HOST COMPLETED");
	             
				 String logsDETAILS_Id= localgetseqNbr(1);
				 logsDETAILS_Id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETAILS_Id;
					PreparedStatement insertLogsDETAILS_Statemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDETAILS_Id+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_HOST COMPLETED','HOST','Number of NODE_HOST','Completed','"+nbOfNodeHost+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insertLogsDETAILS_Statemnt.executeUpdate();
					insertLogsDETAILS_Statemnt.close();
				 
	           //Insert into TEMP_NODE_SUBRACK
				 System.out.println("Copy to TEMP_NODE_SUBRACK in process...");
				 logger.info("Copy to TEMP_NODE_SUBRACK in process...");
				 
				 queryp = "select * from NODE_SUBRACK where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNodeSubrack = 0;
				 while (rsp.next()) {
					 dat3=rsp.getString("DATEOFMANUFACTURE");
					 if(dat3 !=null && dat3 !="null") {
						 dat3 = dat3.substring(0, 10) ;
						 }
					 else {
						 dat3=""; 
					 }
					 dat4=rsp.getString("DATEOFLASTSERVICE");
					 if(dat4 !=null && dat4 !="null") {
						 dat4 = dat4.substring(0, 10) ;
						 
					 }
					 else {
						 dat4=""; 
					 }
					 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_SUBRACK (SUBRACK_ID,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR,OTHERS)"
					 		+ "values ('" + rsp.getString("SUBRACK_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("CABINETNO") +"','" + rsp.getString("SUBRACKNO") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("RACKTYPE") +"','" + rsp.getString("BOMRACKTYPE") +"','" + rsp.getString("FRAMETYPE") +"','" + rsp.getString("RACKFRAMENO") +"','" + rsp.getString("MODULENO") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION")  +"',TO_DATE('" +dat3+"','YYYY-MM-DD')"+",TO_DATE('" +dat4+"','YYYY-MM-DD')" +",'" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("BOMCODE") +"','" + rsp.getString("MODEL") +"','" + rsp.getString("ISSUENUMBER") +"','" + rsp.getString("BOMFRAMETYPE") +"','" + rsp.getString("CLEICODE") +"','" + rsp.getString("BOM") +"','" + rsp.getString("EXTINFO") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR")+"','"+rsp.getString("OTHERS") +"')");
					 stmt2.executeUpdate();
					 stmt2.close();
					 nbOfNodeSubrack++;
			     }
				 rsp.close();
				 stmtp.close();
				 totalNbCopied = totalNbCopied + nbOfNodeSubrack;
				 System.out.println("Copy to TEMP_NODE_SUBRACK COMPLETED");
				 logger.info("Copy to TEMP_NODE_SUBRACK COMPLETED");
		            
				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logsDetails_iD= localgetseqNbr(1);
				 logsDetails_iD=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_iD;
					PreparedStatement insertLogsDEtails_Statemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDetails_iD+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_SUBRACK COMPLETED','SUBRACK','Number of NODE_SUBRACK','Completed','"+nbOfNodeSubrack+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insertLogsDEtails_Statemnt.executeUpdate();
					insertLogsDEtails_Statemnt.close();
				 
	            //Insert into TEMP_NODE_GCELL
				 System.out.println("Copy to TEMP_NODE_GCELL in process...");
				 logger.info("Copy to TEMP_NODE_GCELL in process...");
				 
				 queryp = "select * from NODE_GCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNodeGcell = 0;
				 while (rsp.next()) {
					 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_GCELL (GCELL_ID,CELLID,CELLNAME,MCC,MNC,LAC,CI,NCC,BCC,TYPE,BCCHNO,BASEBANDPOLICY,BASEBANDEQMID,GBTSFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,GLOCELLID,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR)"
					 		+ "values ('" + rsp.getString("GCELL_ID") +"','" + rsp.getString("CELLID") +"','" + rsp.getString("CELLNAME") +"','" + rsp.getString("MCC") +"','" + rsp.getString("MNC") +"','" + rsp.getString("LAC") +"','" + rsp.getString("CI") +"','" + rsp.getString("NCC") +"','" + rsp.getString("BCC") +"','" + rsp.getString("TYPE") +"','" + rsp.getString("BCCHNO") +"','" + rsp.getString("BASEBANDPOLICY") +"','" + rsp.getString("BASEBANDEQMID") +"','" + rsp.getString("GBTSFUNCTIONNAME") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("GLOCELLID") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"',sysdate,'" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
					 stmt0.executeUpdate();
					 stmt0.close();
					 nbOfNodeGcell++;
			     }
				 rsp.close();
				 stmtp.close();
				 totalNbCopied = totalNbCopied + nbOfNodeGcell;
				 System.out.println("Copy to TEMP_NODE_GCELL COMPLETED");
				 logger.info("Copy to TEMP_NODE_GCELL COMPLETED");
	          
				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logsDETails_iD= localgetseqNbr(1);
				 logsDETails_iD=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETails_iD;
					PreparedStatement insertLogsDETAils_Statemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDETails_iD+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_GCELL COMPLETED','GCELL','Number of NODE_GCELL','Completed','"+nbOfNodeGcell+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insertLogsDETAils_Statemnt.executeUpdate();
					insertLogsDETAils_Statemnt.close();
				 
	     	  	//Insert into TEMP_NODE_BTS
				 System.out.println("Copy to TEMP_NODE_BTS in process...");
				 logger.info("Copy to TEMP_NODE_BTS in process...");
				 
				 queryp = "select * from NODE_BTS where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNodeBTS = 0;
				 while (rsp.next()) {
					 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_BTS (BTS_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
					 		+ "values ('" + rsp.getString("BTS_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("SITENAME") +"','" + rsp.getString("SITETYPE") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
					 stmt2.executeUpdate();
					 stmt2.close();
					 nbOfNodeBTS++;
			     }
				 rsp.close();
				 stmtp.close();
				 
				 totalNbCopied = totalNbCopied + nbOfNodeBTS;
				 System.out.println("Copy to TEMP_NODE_BTS COMPLETED");
				 logger.info("Copy to TEMP_NODE_BTS COMPLETED");
	             
				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logsDETAIls_iD= localgetseqNbr(1);
				 logsDETAIls_iD=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETAIls_iD;
					PreparedStatement insertLogSDETAILS_Statemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDETAIls_iD+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_BTS COMPLETED','BTS','Number of NODE_BTS','Completed','"+nbOfNodeBTS+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insertLogSDETAILS_Statemnt.executeUpdate();
					insertLogSDETAILS_Statemnt.close();
				 
	           //Insert into TEMP_NODE_UCELL
				 System.out.println("Copy to TEMP_NODE_UCELL in process...");
				 logger.info("Copy to TEMP_NODE_UCELL in process...");
				 
				 queryp = "select * from NODE_UCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNodeUCELL = 0;
				 while (rsp.next()) {
					 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_UCELL (UCELL_ID,CELLID,CELLNAME,LOCELL,NODEBFUNCTIONNAME,ULFREQ,DLFREQ,MAXPOWER,USERLABEL,MAXTXPOWER,UARFCNUPLINK,UARFCNDOWNLINK,PSCRAMBCODE,NODEBNAME,LAC,SAC,RAC,MANUFACTURERDATA,RADIUS,HORAD,DI,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR)"
					 		+ "values ('" + rsp.getString("UCELL_ID") +"','" + rsp.getString("CELLID") +"','" + rsp.getString("CELLNAME") +"','" + rsp.getString("LOCELL") +"','" + rsp.getString("NODEBFUNCTIONNAME") +"','" + rsp.getString("ULFREQ") +"','" + rsp.getString("DLFREQ") +"','" + rsp.getString("MAXPOWER") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("MAXTXPOWER") +"','" + rsp.getString("UARFCNUPLINK") +"','" + rsp.getString("UARFCNDOWNLINK") +"','" + rsp.getString("PSCRAMBCODE") +"','" + rsp.getString("NODEBNAME") +"','" + rsp.getString("LAC") +"','" + rsp.getString("SAC") +"','" + rsp.getString("RAC") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("RADIUS") +"','" + rsp.getString("HORAD") +"','" + rsp.getString("DI") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"',sysdate,'" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
					 stmt2.executeUpdate();
					 stmt2.close();
					 nbOfNodeUCELL++;
			     }
				 rsp.close();
				 stmtp.close();
				 
				 totalNbCopied = totalNbCopied + nbOfNodeUCELL;
				 System.out.println("Copy to TEMP_NODE_UCELL COMPLETED");
				 logger.info("Copy to TEMP_NODE_UCELL COMPLETED");
				 
				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logsDetails_ID= localgetseqNbr(1);
				 logsDetails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_ID;
					PreparedStatement insert_logsDetails_Statemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDetails_ID+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_UCELL COMPLETED','UCELL','Number of NODE_UCELL','Completed','"+nbOfNodeUCELL+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insert_logsDetails_Statemnt.executeUpdate();
					insert_logsDetails_Statemnt.close();
	            
	            //Insert into TEMP_NODE_ANTENNA
				 System.out.println("Copy to TEMP_NODE_ANTENNA in process...");
				 logger.info("Copy to TEMP_NODE_ANTENNA in process...");
				 
				 queryp = "select * from NODE_ANTENNA where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNodeAntenna = 0;
				 while (rsp.next()) {
					 dat3=rsp.getString("DATEOFMANUFACTURE");
					 if(dat3 !=null && dat3 !="null") {
						 dat3 = dat3.substring(0, 10) ;
						 } 
					 else {
						 dat3=""; 
					 }
					 dat4=rsp.getString("DATEOFLASTSERVICE");
					if(dat4 !=null && dat4 !="null") {
						dat4 = dat4.substring(0, 10) ;
						}
					else {
						 dat4=""; 
					 }
					System.out.println("antenne date "+dat4);
					 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_ANTENNA (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR)"
						 		+ "values ('" + rsp.getString("ANTENNA_ID") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("ANTENNADEVICENO") +"','" + rsp.getString("PRODNR") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION")  +"',TO_DATE('" +dat3+"','YYYY-MM-DD')"+",TO_DATE('" +dat4+"','YYYY-MM-DD')" +",'" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("ANTENNADEVICETYPE") +"','" + rsp.getString("ISSUENUMBER") +"','" + rsp.getString("BOMCODE") +"','" + rsp.getString("EXTINFO") +"','" + rsp.getString("MODEL") +"','" + rsp.getString("SERIALNUMBEREX") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"', TIMESTAMP '" + rsp.getString("CREATION_DATE") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"' )");
					 stmt0.executeUpdate();
					 stmt0.close();
					 nbOfNodeAntenna++;
			     }
				 rsp.close();
				 stmtp.close();
				 
				 totalNbCopied = totalNbCopied + nbOfNodeAntenna;
				 System.out.println("Copy to TEMP_NODE_ANTENNA COMPLETED");
				 logger.info("Copy to TEMP_NODE_ANTENNA COMPLETED");
				 
				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logsDETails_ID= localgetseqNbr(1);
				 logsDETails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETails_ID;
					PreparedStatement insert_logsDETails_Statemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDETails_ID+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_ANTENNA COMPLETED','ANTENNA','Number of NODE_ANTENNA','Completed','"+nbOfNodeAntenna+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insert_logsDETails_Statemnt.executeUpdate();
					insert_logsDETails_Statemnt.close();
	          
	     	  	//Insert into TEMP_NODE_LCELL
				 System.out.println("Copy to TEMP_NODE_LCELL in process...");
				 logger.info("Copy to TEMP_NODE_LCELL in process...");
				 
				 queryp = "select * from NODE_LCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNodeLCELL = 0;
				 while (rsp.next()) {
					 stmt1 = conalm.prepareStatement("insert into TEMP_NODE_LCELL (LCELL_ID,LOCALCELLID,CELLNAME,CELLRADIUS,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,ENODEBFUNCTIONNAME,NBCELLFLAG,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR)"
					 		+ "values ('" + rsp.getString("LCELL_ID") +"','" + rsp.getString("LOCALCELLID") +"','" + rsp.getString("CELLNAME") +"','" + rsp.getString("CELLRADIUS") +"','" + rsp.getString("FREQBAND") +"','" + rsp.getString("ULEARFCNCFGIND") +"','" + rsp.getString("ULEARFCN") +"','" + rsp.getString("DLEARFCN") +"','" + rsp.getString("ULBANDWIDTH") +"','" + rsp.getString("DLBANDWIDTH") +"','" + rsp.getString("CELLID") +"','" + rsp.getString("PHYCELLID") +"','" + rsp.getString("FDDTDDIND") +"','" + rsp.getString("ENODEBFUNCTIONNAME") +"','" + rsp.getString("NBCELLFLAG") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"',sysdate,'" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
					 stmt1.executeUpdate();
					 stmt1.close();
					 nbOfNodeLCELL++;
			     }
				 rsp.close();
				 stmtp.close();
				 totalNbCopied = totalNbCopied + nbOfNodeLCELL;
				 System.out.println("Copy to TEMP_NODE_LCELL COMPLETED");
				 logger.info("Copy to TEMP_NODE_LCELL COMPLETED");
				 
				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logsDETAiLs_ID= localgetseqNbr(1);
				 logsDETAiLs_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETAiLs_ID;
					PreparedStatement insert_logsDETAiLs_Statemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDETAiLs_ID+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_LCELL COMPLETED','LCELL','Number of NODE_LCELL','Completed','"+nbOfNodeLCELL+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insert_logsDETAiLs_Statemnt.executeUpdate();
					insert_logsDETAiLs_Statemnt.close();
	             
	           //Insert into TEMP_NODE_RRN
				 System.out.println("Copy to TEMP_NODE_RRN in process...");
				 logger.info("Copy to TEMP_NODE_RRN in process...");
				 
				 queryp = "select * from NODE_RRN where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNodeRRN = 0;
				 while (rsp.next()) {
					 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_RRN (RRN_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
					 		+ "values ('" + rsp.getString("RRN_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("SITENAME") +"','" + rsp.getString("SITETYPE") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
					 stmt2.executeUpdate();
					 stmt2.close();
					 nbOfNodeRRN++;
			     }
				 rsp.close();
				 stmtp.close();
				 
				 totalNbCopied = totalNbCopied + nbOfNodeRRN;
				 System.out.println("Copy to TEMP_NODE_RRN COMPLETED");
				 logger.info("Copy to TEMP_NODE_RRN COMPLETED");
				
				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logs_Details_ID= localgetseqNbr(1);
				 logs_Details_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_Details_ID;
					PreparedStatement insert_LogsDETAiLS_Statemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logs_Details_ID+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_RRN COMPLETED','RRN','Number of NODE_RRN','Completed','"+nbOfNodeRRN+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insert_LogsDETAiLS_Statemnt.executeUpdate();
					insert_LogsDETAiLS_Statemnt.close();
	            
	            //Insert into TEMP_NODE_ENODEBCELL
				 System.out.println("Copy to TEMP_NODE_ENODEBCELL in process...");
				 logger.info("Copy to TEMP_NODE_ENODEBCELL in process...");
				 
				 queryp = "select * from NODE_ENODEBCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNodeENODEBCELL = 0;
				 while (rsp.next()) {
					 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_ENODEBCELL (ENODEBCELL_ID,LOCALCELLID,CELLNAME,SECTORID,CSGIND,CYCLEPREFIX,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,TAC,ADDITIONALSPECTRUMEMISSION,NBCELLFLAG,ENODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
					 		+ "values ('" + rsp.getString("ENODEBCELL_ID") +"','" + rsp.getString("LOCALCELLID") +"','" + rsp.getString("CELLNAME") +"','" + rsp.getString("SECTORID") +"','" + rsp.getString("CSGIND") +"','" + rsp.getString("CYCLEPREFIX") +"','" + rsp.getString("FREQBAND") +"','" + rsp.getString("ULEARFCNCFGIND") +"','" + rsp.getString("ULEARFCN") +"','" + rsp.getString("DLEARFCN") +"','" + rsp.getString("ULBANDWIDTH") +"','" + rsp.getString("DLBANDWIDTH") +"','" + rsp.getString("CELLID") +"','" + rsp.getString("PHYCELLID") +"','" + rsp.getString("FDDTDDIND") +"','" + rsp.getString("TAC") +"','" + rsp.getString("ADDITIONALSPECTRUMEMISSION") +"','" + rsp.getString("NBCELLFLAG") +"','" + rsp.getString("ENODEBFUNCTIONNAME") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
					 stmt0.executeUpdate();
					 stmt0.close();
					 nbOfNodeENODEBCELL++;
			     }
				 rsp.close();
				 stmtp.close();
				 totalNbCopied = totalNbCopied + nbOfNodeENODEBCELL;
				 System.out.println("Copy to TEMP_NODE_ENODEBCELL COMPLETED");
				 logger.info("Copy to TEMP_NODE_ENODEBCELL COMPLETED");
				 
				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logs_DETails_ID= localgetseqNbr(1);
				 logs_DETails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_DETails_ID;
					PreparedStatement insert_LogsDEtails_Statemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logs_DETails_ID+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_ENODEBCELL COMPLETED','ENODEBCELL','Number of NODE_ENODEBCELL','Completed','"+nbOfNodeENODEBCELL+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insert_LogsDEtails_Statemnt.executeUpdate();
					insert_LogsDEtails_Statemnt.close();
	          
	     	  	//Insert into TEMP_NODE_NODEBCELL
				 System.out.println("Copy to TEMP_NODE_NODEBCELL in process...");
				 logger.info("Copy to TEMP_NODE_NODEBCELL in process...");
				 
				 queryp = "select * from NODE_NODEBCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNode_NODEBCELL = 0;
				 while (rsp.next()) {
					 stmt1 = conalm.prepareStatement("insert into TEMP_NODE_NODEBCELL (NODEBCELL_ID,LOCELL,USERLABEL,SITENO,SECNO,RADIUS,HORAD,BBPOOLTYPE,ULGROUPNO,CABINETNO1,SUBRACKNO1,SLOTNO1,CABINETNO2,SUBRACKNO2,SLOTNO2,ULFREQ,DLFREQ,MAXPOWER,HSDPA,DI,HIGHSPEEDMODE,SPEEDRATE,NODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
					 		+ "values ('" + rsp.getString("NODEBCELL_ID") +"','" + rsp.getString("LOCELL") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("SITENO") +"','" + rsp.getString("SECNO") +"','" + rsp.getString("RADIUS") +"','" + rsp.getString("HORAD") +"','" + rsp.getString("BBPOOLTYPE") +"','" + rsp.getString("ULGROUPNO") +"','" + rsp.getString("CABINETNO1") +"','" + rsp.getString("SUBRACKNO1") +"','" + rsp.getString("SLOTNO1") +"','" + rsp.getString("CABINETNO2") +"','" + rsp.getString("SUBRACKNO2") +"','" + rsp.getString("SLOTNO2") +"','" + rsp.getString("ULFREQ") +"','" + rsp.getString("DLFREQ") +"','" + rsp.getString("MAXPOWER") +"','" + rsp.getString("HSDPA") +"','" + rsp.getString("DI") +"','" + rsp.getString("HIGHSPEEDMODE") +"','" + rsp.getString("SPEEDRATE") +"','" + rsp.getString("NODEBFUNCTIONNAME") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
					 stmt1.executeUpdate();
					 stmt1.close();
					 nbOfNode_NODEBCELL++;
			     }
				 rsp.close();
				 stmtp.close();
				 totalNbCopied = totalNbCopied + nbOfNode_NODEBCELL;
				 System.out.println("Copy to TEMP_NODE_NODEBCELL COMPLETED");
				 logger.info("Copy to TEMP_NODE_NODEBCELL COMPLETED");
				 
				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logs_DETAiLs_ID= localgetseqNbr(1);
				 logs_DETAiLs_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_DETAiLs_ID;
					PreparedStatement insert_LogsDEtAiLs_Statemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logs_DETAiLs_ID+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_NODEBCELL COMPLETED','NODEBCELL','Number of NODE_NODEBCELL','Completed','"+nbOfNode_NODEBCELL+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insert_LogsDEtAiLs_Statemnt.executeUpdate();
					insert_LogsDEtAiLs_Statemnt.close();
	             
	           //Insert into TEMP_NODE_NBINTERFACES
				 System.out.println("Copy to TEMP_NODE_NBINTERFACES in process...");
				 logger.info("Copy to TEMP_NODE_NBINTERFACES in process...");
				 
				 queryp = "select * from NODE_NBINTERFACES where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNode_NBINTERFACES = 0;
				 while (rsp.next()) {
					 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_NBINTERFACES (NBINTERFACE_ID,INTERFACETYPE,VERSION,ISUSED,NMSVENDOR,NMSNAME,REMARK,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
					 		+ "values ('" + rsp.getString("NBINTERFACE_ID") +"','" + rsp.getString("INTERFACETYPE") +"','" + rsp.getString("VERSION") +"','" + rsp.getString("ISUSED") +"','" + rsp.getString("NMSVENDOR") +"','" + rsp.getString("NMSNAME") +"','" + rsp.getString("REMARK") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"' )");
					 stmt2.executeUpdate();
					 stmt2.close();
					 nbOfNode_NBINTERFACES++;
			     }
				 rsp.close();
				 stmtp.close();
				 
				 totalNbCopied = totalNbCopied + nbOfNode_NBINTERFACES;
				 System.out.println("Copy to TEMP_NODE_NBINTERFACES COMPLETED");
				 logger.info("Copy to TEMP_NODE_NBINTERFACES COMPLETED");
				 
				 //insert into AUTO_DISCOVERY_LOGS_DETAILS
				 String logs_DETAiLS_IDd= localgetseqNbr(1);
				 logs_DETAiLS_IDd=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_DETAiLS_IDd;
					PreparedStatement insert_LogsDEtAiLS_Statemntt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logs_DETAiLS_IDd+"',sysdate ,'CopyParsingToALM','Copy to TEMP_NODE_NBINTERFACES COMPLETED','NBINTERFACES','Number of NODE_NBINTERFACES','Completed','"+nbOfNode_NBINTERFACES+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
					 		
					insert_LogsDEtAiLS_Statemntt.executeUpdate();
					insert_LogsDEtAiLS_Statemntt.close();
				 
				//Insert into TEMP_NODE_CHILD_PARENT
				 System.out.println("Copy to TEMP_NODE_CHILD_PARENT in process...");
				 logger.info("Copy to TEMP_NODE_CHILD_PARENT in process...");
				 
				 queryp = "select * from NODE_CHILD_PARENT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     rsp = stmtp.executeQuery(queryp);
			     
			     int nbOfNodeChildParent = 0;
				 while (rsp.next()) {
					 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_CHILD_PARENT (ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR,TO_TRANS_SOURCE)"
					 		+ "values ('" + rsp.getString("ID") +"',TIMESTAMP '" + rsp.getString("CREATION_DATE") +"',TIMESTAMP '" + rsp.getString("LAST_MODIFIED_DATE") +"','" + rsp.getString("CHILD_ID_1") +"','" + rsp.getString("CHILD_NAME_1") +"','" + rsp.getString("CHILD_ID_2") +"','" + rsp.getString("CHILD_NAME_2") +"','" + rsp.getString("CHILD_TYPE") +"','" + rsp.getString("CHILD_MODEL") +"','" + rsp.getString("PARENT_ID") +"','" + rsp.getString("PARENT_NAME") +"','" + rsp.getString("PARENT_TYPE") +"','" + rsp.getString("PARENT_MODEL") +"','" + rsp.getString("FILE_NAME") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' )");
					 stmt2.executeUpdate();
					 stmt2.close();
					 nbOfNodeChildParent++;
			     }
				 rsp.close();
				 stmtp.close();
				 totalNbCopied = totalNbCopied + nbOfNodeChildParent;
				 System.out.println("Copy to TEMP_NODE_CHILD_PARENT COMPLETED");
				 logger.info("Copy to TEMP_NODE_CHILD_PARENT COMPLETED");
					
					
					 //insert into AUTO_DISCOVERY_LOGS_DETAILS
					 String logs_DEtails_IDD= localgetseqNbr(1);
					 logs_DEtails_IDD=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_DEtails_IDD;
						PreparedStatement insert_LogsDEtails_STatemntt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
						 		+ "values('"+logs_DEtails_IDD+"',sysdate ,'CopyParsingToALM','Total Number of rows','','Total Number of rows','','"+totalNbCopied+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
						 		
						insert_LogsDEtails_STatemntt.executeUpdate();
						insert_LogsDEtails_STatemntt.close();
						
						
						//Insert into TEMP_NODE_MODULE
						 System.out.println("Copy to TEMP_NODE_MODULE in process...");
						 logger.info("Copy to TEMP_NODE_MODULE in process...");
						 
						 queryp = "select * from NODE_MODULE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
						 stmtp = con.createStatement();
					     rsp = stmtp.executeQuery(queryp);
					     
					     int nbOfNodeModule = 0;
						 while (rsp.next()) {
							 stmt2 = conalm.prepareStatement("Insert into TEMP_NODE_MODULE (MODULE_ID,CABINETNO,MODULENO,INVUNITID,IDENTIFICATIONCODE,CONFIGDN,INVUNITTYPE,PARENTDN,RUNTIMEDN,SERIALNUMBER,STATE,UNITPOSITION,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,SUBRACK_SPECIFIC_TYPE,USERLABEL,VENDORNAME,VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION,ANTENNA_STATUS,SOFTVER,HARDWAREVERSION,OTHERS) "
							 		+ "values ('"+rsp.getString("MODULE_ID")+"','"+rsp.getString("CABINETNO")+"','"+rsp.getString("MODULENO")+"','"+rsp.getString("INVUNITID")+"','"+rsp.getString("IDENTIFICATIONCODE")+"','"+rsp.getString("CONFIGDN")+"','"+rsp.getString("INVUNITTYPE")+"','"+rsp.getString("PARENTDN")+"','"+rsp.getString("RUNTIMEDN")+"','"+rsp.getString("SERIALNUMBER")+"','"+rsp.getString("STATE")+"','"+rsp.getString("UNITPOSITION")+"','"+rsp.getString("VENDORUNITFAMILYTYPE")+"','"+rsp.getString("VENDORUNITTYPENUMBER")+"','"+rsp.getString("SUBRACK_SPECIFIC_TYPE")+"','"+rsp.getString("USERLABEL")+"','"+rsp.getString("VENDORNAME")+"','"+rsp.getString("VERSION")+"','"+rsp.getString("DISTNAME")+"','"+rsp.getString("NODE_PK")+"',TIMESTAMP '"+rsp.getString("UPDATE_DATE")+"','"+rsp.getString("FILENAME")+"','"+rsp.getString("STATUS")+"','"+rsp.getString("FROM_TRANS_SOURCE")+"','"+rsp.getString("FROM_TRANS_ID")+"','"+rsp.getString("TO_TRANS_ID")+"','"+rsp.getString("TRANS_TYPE")+"','"+rsp.getString("ACTIVE_RECORD")+"',TIMESTAMP '"+rsp.getString("CREATION_DATE")+"','"+rsp.getString("DOMAIN")+"','"+rsp.getString("VENDOR")+"','"+rsp.getString("TO_TRANS_SOURCE")+"','"+rsp.getString("LINE")+"','"+rsp.getString("NODE_ATTR_PK")+"','"+rsp.getString("ALM_POSITION")+"','"+rsp.getString("ANTENNA_STATUS")+"','"+rsp.getString("SOFTVER")+"','"+rsp.getString("HARDWAREVERSION")+"','"+rsp.getString("OTHERS")+"')");

							 stmt2.executeUpdate();
							 stmt2.close();
							 nbOfNodeModule++;
					     }
						 rsp.close();
						 stmtp.close();
						 totalNbCopied = totalNbCopied + nbOfNodeModule;
						 System.out.println("Copy to TEMP_NODE_MODULE COMPLETED");
						 logger.info("Copy to TEMP_NODE_MODULE COMPLETED");
							
							
							 //insert into AUTO_DISCOVERY_LOGS_DETAILS
							 String logs_Dtls_ID= localgetseqNbr(1);
							 logs_Dtls_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_Dtls_ID;
								PreparedStatement insert_LogsDEtails_STatemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
								 		+ "values('"+logs_Dtls_ID+"',sysdate ,'CopyParsingToALM','Total Number of rows','','Total Number of rows','','"+totalNbCopied+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
								 		
								insert_LogsDEtails_STatemnt.executeUpdate();
								insert_LogsDEtails_STatemnt.close();
								
								
				
								//Insert into TEMP_NODE_SUBMODULE
								 System.out.println("Copy to TEMP_NODE_SUBMODULE in process...");
								 logger.info("Copy to TEMP_NODE_SUBMODULE in process...");
								 
								 queryp = "select * from NODE_SUBMODULE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
								 stmtp = con.createStatement();
							     rsp = stmtp.executeQuery(queryp);
							     
							     int nbOfNodeSUBModule = 0;
								 while (rsp.next()) {
									 
									 stmt2 = conalm.prepareStatement("Insert into TEMP_NODE_SUBMODULE (SUBMODULE_ID,CABINETNO,MODULENO,SUBMODULENO,INVUNITID,IDENTIFICATIONCODE,CONFIGDN,PARENTDN,RUNTIMEDN,SERIALNUMBER,UNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION)"
									 		+ " values ('"+rsp.getString("SUBMODULE_ID")+"','"+rsp.getString("CABINETNO")+"','"+rsp.getString("MODULENO")+"','"+rsp.getString("SUBMODULENO")+"','"+rsp.getString("INVUNITID")+"','"+rsp.getString("IDENTIFICATIONCODE")+"','"+rsp.getString("CONFIGDN")+"','"+rsp.getString("PARENTDN")+"','"+rsp.getString("RUNTIMEDN")+"','"+rsp.getString("SERIALNUMBER")+"','"+rsp.getString("UNITTYPE")+"','"+rsp.getString("VENDORUNITFAMILYTYPE")+"','"+rsp.getString("VENDORUNITTYPENUMBER")+"','"+rsp.getString("VENDORNAME")+"','"+rsp.getString("VERSION")+"','"+rsp.getString("DISTNAME")+"','"+rsp.getString("NODE_PK")+"',TIMESTAMP '"+rsp.getString("UPDATE_DATE")+"','"+rsp.getString("FILENAME")+"','"+rsp.getString("STATUS")+"','"+rsp.getString("FROM_TRANS_SOURCE")+"','"+rsp.getString("FROM_TRANS_ID")+"','"+rsp.getString("TO_TRANS_ID")+"','"+rsp.getString("TRANS_TYPE")+"','"+rsp.getString("ACTIVE_RECORD")+"',TIMESTAMP '"+rsp.getString("CREATION_DATE")+"','"+rsp.getString("DOMAIN")+"','"+rsp.getString("VENDOR")+"','"+rsp.getString("TO_TRANS_SOURCE")+"','"+rsp.getString("LINE")+"','"+rsp.getString("NODE_ATTR_PK")+"','"+rsp.getString("ALM_POSITION")+"')");

									
									 stmt2.executeUpdate();
									 stmt2.close();
									 nbOfNodeSUBModule++;
							     }
								 rsp.close();
								 stmtp.close();
								 totalNbCopied = totalNbCopied + nbOfNodeSUBModule;
								 System.out.println("Copy to TEMP_NODE_SUBMODULE COMPLETED");
								 logger.info("Copy to TEMP_NODE_SUBMODULE COMPLETED");
									
									
									 //insert into AUTO_DISCOVERY_LOGS_DETAILS
									 String logs_DETAils_ID= localgetseqNbr(1);
									 logs_DETAils_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_DETAils_ID;
										PreparedStatement insert_LogsDETails_STatemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
										 		+ "values('"+logs_DETAils_ID+"',sysdate ,'CopyParsingToALM','Total Number of rows','','Total Number of rows','','"+totalNbCopied+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
										 		
										insert_LogsDETails_STatemnt.executeUpdate();
										insert_LogsDETails_STatemnt.close();
										
							//Insert into TEMP_NODE_FUUNIT
							 System.out.println("Copy to TEMP_NODE_FUUNIT in process...");
							 logger.info("Copy to TEMP_NODE_FUUNIT in process...");
							 
							 queryp = "select * from NODE_FUUNIT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
							 stmtp = con.createStatement();
						     rsp = stmtp.executeQuery(queryp);
						     
						     int nbOfNodeFUUNIT = 0;
							 while (rsp.next()) {
								 stmt2 = conalm.prepareStatement("Insert into TEMP_NODE_FUUNIT (FUUNIT_ID,FUUNITNO,FUNCTIONAL_UNIT_TYPE,SUPPORT_BY_UNIT,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION)"
								 		+ " values ('"+rsp.getString("FUUNIT_ID")+"','"+rsp.getString("FUUNITNO")+"','"+rsp.getString("FUNCTIONAL_UNIT_TYPE")+"','"+rsp.getString("SUPPORT_BY_UNIT")+"','"+rsp.getString("DISTNAME")+"','"+rsp.getString("NODE_PK")+"',TIMESTAMP '"+rsp.getString("UPDATE_DATE")+"','"+rsp.getString("FILENAME")+"','"+rsp.getString("STATUS")+"','"+rsp.getString("FROM_TRANS_SOURCE")+"','"+rsp.getString("FROM_TRANS_ID")+"','"+rsp.getString("TO_TRANS_ID")+"','"+rsp.getString("TRANS_TYPE")+"','"+rsp.getString("ACTIVE_RECORD")+"',TIMESTAMP '"+rsp.getString("CREATION_DATE")+"','"+rsp.getString("DOMAIN")+"','"+rsp.getString("VENDOR")+"','"+rsp.getString("TO_TRANS_SOURCE")+"','"+rsp.getString("LINE")+"','"+rsp.getString("NODE_ATTR_PK")+"','"+rsp.getString("ALM_POSITION")+"')");

								
								 stmt2.executeUpdate();
								 stmt2.close();
								 nbOfNodeFUUNIT++;
						     }
							 rsp.close();
							 stmtp.close();
							 totalNbCopied = totalNbCopied + nbOfNodeFUUNIT;
							 System.out.println("Copy to TEMP_NODE_FUUNIT COMPLETED");
							 logger.info("Copy to TEMP_NODE_FUUNIT COMPLETED");
								
								
								 //insert into AUTO_DISCOVERY_LOGS_DETAILS
								 String logs_DETAIls_ID= localgetseqNbr(1);
								 logs_DETAIls_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_DETAIls_ID;
									PreparedStatement insert_LogsDETAils_STatemnt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
									 		+ "values('"+logs_DETAIls_ID+"',sysdate ,'CopyParsingToALM','Total Number of rows','','Total Number of rows','','"+totalNbCopied+"','" + vvendor +"','" + vdomain +"','"+logsid+"') ");
									 		
									insert_LogsDETAils_STatemnt.executeUpdate();
									insert_LogsDETAils_STatemnt.close();
		// }
				// catch(Exception e)  
				//	{  
				//	logger.info("error at RunCopydata is :"+ e.toString());
				//	System.out.println("error at RunCopydata is :"+ e.toString()); 
				//	} 
	   
	 	}

	 
	 private static void DeletedatafromNodeTables(String vdomain, String vvendor) throws InterruptedException, SQLException {
		 try {
	 		//Delete from NODE_ACTIVE
	 		PreparedStatement stmt0 = con.prepareStatement("delete  from NODE_ACTIVE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	 		stmt0.executeUpdate();
	 		stmt0.close();
  
	 		//Delete from NODE_ACTIVE_ATTRIBUTE
	 		PreparedStatement stmt1 = con.prepareStatement("delete  from NODE_ACTIVE_ATTRIBUTE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	 		stmt1.executeUpdate();
     	stmt1.close();
     
     	//Delete from NODE_RACK
	 		PreparedStatement stmt2 = con.prepareStatement("delete  from NODE_RACK where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	 		stmt2.executeUpdate();
     	stmt2.close();
     	
     	//Delete from NODE_SHELF
 		 stmt2 = con.prepareStatement("delete  from NODE_SHELF where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
 		 stmt2.executeUpdate();
 		 stmt2.close();
     
     	//Delete from NODE_CABINET
	  		stmt0 = con.prepareStatement("delete  from NODE_CABINET where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	  		stmt0.executeUpdate();
	  		stmt0.close();
     
	  		//Delete from NODE_HOSTVER
	  		stmt1 = con.prepareStatement("delete  from NODE_HOSTVER where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	  		stmt1.executeUpdate();
	  		stmt1.close();
        
      	//Delete from NODE_FRAME
   	  	stmt2 = con.prepareStatement("delete  from NODE_FRAME where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
   	  	stmt2.executeUpdate();
   	  	stmt2.close();
       
       	//Delete from NODE_SLOT
  	  	stmt0 = con.prepareStatement("delete  from NODE_SLOT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
  	  	stmt0.executeUpdate();
  	  	stmt0.close();
       
  	  	//Delete from NODE_BOARD
     	stmt1 = con.prepareStatement("delete  from NODE_BOARD where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
         stmt1.executeUpdate();
         stmt1.close();
          
        //Delete from NODE_PORT
     	stmt2 = con.prepareStatement("delete  from NODE_PORT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
         stmt2.executeUpdate();
         stmt2.close();
         
         //Delete from NODE_ACCESSORY
  	  	stmt0 = con.prepareStatement("delete  from NODE_ACCESSORY where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
  	  	stmt0.executeUpdate();
  	  	stmt0.close();
       
  	  	//Delete from NODE_HOST
     	stmt1 = con.prepareStatement("delete  from NODE_HOST where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
         stmt1.executeUpdate();
         stmt1.close();
          
         //Delete from NODE_SUBRACK
     	stmt2 = con.prepareStatement("delete  from NODE_SUBRACK where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
         stmt2.executeUpdate();
         stmt2.close();
         
         //Delete from NODE_GCELL
  	  	stmt0 = con.prepareStatement("delete  from NODE_GCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
  	  	stmt0.executeUpdate();
  	  	stmt0.close();
       
  	  	//Delete from NODE_BTS
     	stmt1 = con.prepareStatement("delete  from NODE_BTS where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
         stmt1.executeUpdate();
         stmt1.close();
          
        //Delete from NODE_UCELL
     	stmt2 = con.prepareStatement("delete  from NODE_UCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
         stmt2.executeUpdate();
         stmt2.close();
         
         //Delete from NODE_ANTENNA
  	  	stmt0 = con.prepareStatement("delete  from NODE_ANTENNA where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
  	  	stmt0.executeUpdate();
  	  	stmt0.close();
       
  	  	//Delete from NODE_LCELL
     	stmt1 = con.prepareStatement("delete  from NODE_LCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
         stmt1.executeUpdate();
         stmt1.close();
          
         //Delete from NODE_RRN
     	stmt2 = con.prepareStatement("delete  from NODE_RRN where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
         stmt2.executeUpdate();
         stmt2.close();
         
         //Delete from NODE_ENODEBCELL
  	  	stmt0 = con.prepareStatement("delete  from NODE_ENODEBCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
  	  	stmt0.executeUpdate();
  	  	stmt0.close();
       
  	  	//Delete from NODE_NODEBCELL
     	stmt1 = con.prepareStatement("delete  from NODE_NODEBCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
         stmt1.executeUpdate();
         stmt1.close();
          
         //Delete from NODE_NBINTERFACES
     	stmt2 = con.prepareStatement("delete  from NODE_NBINTERFACES where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
         stmt2.executeUpdate();
         stmt2.close();
         
         //Delete from NODE_CHILD_PARENT
      	stmt2 = con.prepareStatement("delete  from NODE_CHILD_PARENT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
          stmt2.executeUpdate();
          stmt2.close();
         
          //Delete from NODE_MODULE
  	  	stmt0 = con.prepareStatement("delete  from NODE_MODULE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
  	  	stmt0.executeUpdate();
  	  	stmt0.close();
  	  	
    	  	//Delete from NODE_SUBMODULE
       	stmt1 = con.prepareStatement("delete  from NODE_SUBMODULE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
           stmt1.executeUpdate();
           stmt1.close();
           
           //Delete from NODE_FUUNIT
        	stmt2 = con.prepareStatement("delete  from NODE_FUUNIT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
            stmt2.executeUpdate();
            stmt2.close();

	 }
	 catch(Exception e)  
		{  
		logger.info("error at DeletedatafromNodeTables is :"+ e.toString());
		System.out.println("error at DeletedatafromNodeTables is :"+ e.toString()); 
		
		//insert into AUTO_DISCOVERY_LOGS_DETAILS
		String logsDetailsId= localgetseqNbr(1);
		logsDetailsId=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsId;
		PreparedStatement insertLogsDetailsStmt = conalm.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
			 		+ "values('"+logsDetailsId+"',sysdate ,'CopyParsingToALM','error at DeletedatafromNodeTables ','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
			 		
		insertLogsDetailsStmt.executeUpdate();
		insertLogsDetailsStmt.close();
		nbOfErrors++;
		} 
         
	}
	 
	 private static String localgetseqNbr(int n1) throws SQLException {
		    String min = null ;

					
					Statement stmttype = null;
				
		       String SeqName = null;
		    switch(n1)
		    {
		    
			case 0:
				SeqName = "AUTO_DISCOVERY_LOGS_SEQ";
				break;
				
			case 1:
				SeqName = "AUTO_DISCOVERY_LOGS_DETAILS_SEQ";
				break;
				
		    }
		      String query2 = "select "+SeqName+".nextval as nbr from dual";      
		          stmttype = conalm.createStatement();
		          ResultSet rs2 = stmttype.executeQuery(query2);
		         
		          while (rs2.next()) {
		           min= rs2.getString("nbr");    
		          	}
		          rs2.close();

		          stmttype.close();

					 return min;

		  }
	 
	 private static void UpdatingSiteinTemp(String domain,String vendor) throws SQLException {
		 try {
		 Statement stmt = null,stmt1=null;
		 String tmpSiteID = null,tmpWareName=null,tmpWareID=null,vSiteID = null,vWareName=null,vWareID=null,vLong=null,vLat=null,tmpNodeName=null;
		 stmt = conalm.createStatement();  
    	 String sqlStmtinit2 = "select distinct NODE_ID,NODE_NAME,SITE_ID,WARE_ID,WARE_NAME from TEMP_NODE_ACTIVE where DOMAIN='"+domain+"' and VENDOR='"+vendor+"'";          
		 ResultSet rs = stmt.executeQuery(sqlStmtinit2);
	 	 while(rs.next()) {
	 		 if(rs.getString("NODE_NAME") != null) {
	 			if(rs.getString("NODE_NAME").contains("'")) {
	 		 		  tmpNodeName = rs.getString("NODE_NAME").replace("'", " ");
	 		 	  }else {
	 		 		  tmpNodeName = rs.getString("NODE_NAME");
	 		 	  }
	 			  tmpSiteID = rs.getString("SITE_ID");
	 			  tmpWareID = rs.getString("WARE_ID");
	 			  tmpWareName = rs.getString("WARE_NAME");
	 			  
	 			  stmt1 = conalm.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
	 			  String sqlquery = "Select DISTINCT NODE_ID,NODE_NAME,SITE_ID,WARE_ID,WARE_NAME,LONGITUDE,LATITUDE FROM NODE_ACTIVE WHERE NODE_ID='"+rs.getString("NODE_ID")+"' AND NODE_NAME='"+tmpNodeName+"' AND ACTIVE_RECORD='1'";
	 			  ResultSet rs1 = stmt1.executeQuery(sqlquery);
	 			  rs1.last();
	 			  int totalrows=rs1.getRow();
	 			  rs1.beforeFirst();
	 		 	  if(totalrows>0) {
	 		 		 while(rs1.next()) {
	 		 			vSiteID = rs1.getString("SITE_ID");
	 		 			vWareID = rs1.getString("WARE_ID");
	 		 			vWareName = rs1.getString("WARE_NAME");
	 		 			vLong= rs1.getString("LONGITUDE");
	 		 			vLat= rs1.getString("LATITUDE");
	 		 			if((vSiteID != null || !vSiteID.equalsIgnoreCase("0") || !vSiteID.equalsIgnoreCase("null"))
	 		 				|| (vWareID != null || !vWareID.equalsIgnoreCase("0") || !vWareID.equalsIgnoreCase("null")) 
	 		 				|| (vWareName != null || !vWareName.equalsIgnoreCase("0") || !vWareName.equalsIgnoreCase("null"))) {
		 		 			if((tmpSiteID == null || tmpSiteID.equalsIgnoreCase("0") || tmpSiteID.equalsIgnoreCase("null")) 
		 		 			|| (tmpWareID == null || tmpWareID.equalsIgnoreCase("0") || tmpWareID.equalsIgnoreCase("null"))
		 		 			|| (tmpWareName != null || tmpWareName.equalsIgnoreCase("0") || tmpWareName.equalsIgnoreCase("null"))) {
		 		 				PreparedStatement updatetemp = null;
		 		 				updatetemp=conalm.prepareStatement("Update TEMP_NODE_ACTIVE"
		 		 				+ " SET SITE_ID='"+vSiteID+"', WARE_ID='"+vWareID+"', WARE_NAME='"+vWareName+"',"
		 		 				+ "LONGITUDE='"+vLong+"',LATITUDE='"+vLat+"'"
		 		 				+ " WHERE NODE_ID='"+rs.getString("NODE_ID")+"' AND NODE_NAME='"+rs.getString("NODE_NAME")+"'");
		 		 				updatetemp.executeUpdate();
		 		 				updatetemp.close();
		 		 			}
	 		 			}
	 		 			
	 		 		 }
	 		 		 
	 		 		 
	 		 	 }
	 		 	stmt1.close();
		 		rs1.close();
	 	 }
	 		 }
	 		 	  
	 	 stmt.close();
	 	 rs.close();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
}
