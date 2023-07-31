package com.aliat.alm.Parser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.lang3.StringUtils;

public class CheckAntennaMovement {

	static Connection con ;
	static String Gyear;
	static Logger logger;
	static FileHandler fh;
	static BufferedReader objReader1 = null;
	static String strCurrentLine1;
	static String logpath;
	static String db1path;
	static String username1;
	static String password1;
	static String gnodepk;   
	static String gnodepkattr;
	static String gnodefilename;
	static int gnodeline;
	static String logsid="0";
	static int nbOfErrors = 0;
	
	public static void main(String[] args) {
		 
		 BufferedReader objReader = null;

		 try	{
		    	
				objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+"almconfig.dat"));
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
					 
				}
				 objReader1.close();

				 	//get only year from today date
				 	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDateTime now = LocalDateTime.now();
					Gyear=dtf.format(now).substring(0,4);
					String lofilename="Antenna-"+dtf.format(now)+".log";
				
				
					logger = Logger.getLogger("MyLog"); 
					logger.setUseParentHandlers(false);
					
					// This block configure the logger with handler and formatter  and PATH
			        fh = new FileHandler(logpath+"/"+lofilename);
			        logger.addHandler(fh);
			        SimpleFormatter formatter = new SimpleFormatter();  
			        fh.setFormatter(formatter);
			        
			        
				    	// Connect to almparser DB 
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
						
						logsid = localgetseqNbr(23);
						 logsid=Gyear+"_"+ "LOGS"+'_'+logsid;
						 
						
						/// select different domain and vendor from temp node active table
						Statement stmtinit2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
				    	 String sqlStmtinit2 = "select distinct DOMAIN, VENDOR from TEMP_NODE_ACTIVE";          
						    ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
						    rsinit2.last();
					 	    int totalrecinit2 = rsinit2.getRow(); 
					 	   rsinit2.beforeFirst();
					 	   if (totalrecinit2 > 0 ) {
						 		  while (rsinit2.next()) {
						 			  
						 			 Timestamp startTime = new Timestamp(System.currentTimeMillis());
						 			  
									logger.info("1-Check Antenna Disappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									System.out.println("1-Check AntennaVersion Disappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
										
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logsDetailsid= localgetseqNbr(24);
									logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
									PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logsDetailsid+"',sysdate ,'CheckAntennaMovement','1-Check AntennaVersion Disappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
				 					 		
									insertLogsDetailsstmt.executeUpdate();
									insertLogsDetailsstmt.close();
									
									GetAntennaDisappearingfromAntennatable(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
									 
									System.out.println("1-Check Antenna Disappearing  COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									logger.info("1-Check Antenna Disappearing COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));	
						 			  
									
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logsDetails_id= localgetseqNbr(24);
									logsDetails_id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_id;
									PreparedStatement insertLogsDetails_stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logsDetails_id+"',sysdate ,'CheckAntennaMovement','1-Check Antenna Disappearing COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
				 					 		
									insertLogsDetails_stmt.executeUpdate();
									insertLogsDetails_stmt.close();
									
								    /////////////////////////////////////////////////////////////	
									logger.info("2-Check Antenna Reappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									System.out.println("2-Check Antenna Reappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logsDetails_Id= localgetseqNbr(24);
									logsDetails_Id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_Id;
									PreparedStatement insertLogsDetails_Stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logsDetails_Id+"',sysdate ,'CheckAntennaMovement','2-Check Antenna Reappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
				 					 		
									insertLogsDetails_Stmt.executeUpdate();
									insertLogsDetails_Stmt.close();
									
									GetAntennaReappearingfromAntannatable(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
								 
									System.out.println("2-Check Antenna Reappearing  COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									logger.info("2-Check Antenna Reappearing COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));	
									
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logsDetails_ID= localgetseqNbr(24);
									logsDetails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_ID;
									PreparedStatement insertLogsDetails_statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logsDetails_ID+"',sysdate ,'CheckAntennaMovement','2-Check Antenna Reappearing COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
				 					 		
									insertLogsDetails_statement.executeUpdate();
									insertLogsDetails_statement.close();
									
									/////////////////////////////////////////////////////////////	
									logger.info("3-Check Antenna Movement for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									System.out.println("3-Check Antenna Movement for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logsDetailsID= localgetseqNbr(24);
									logsDetailsID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsID;
									PreparedStatement insertLogsDetailsstatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logsDetailsID+"',sysdate ,'CheckAntennaMovement','3-Check Antenna Movement for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
				 					 		
									insertLogsDetailsstatement.executeUpdate();
									insertLogsDetailsstatement.close();
									
									// read all child_id means all node_id from child_parent table  tables
									Statement stmt0 = null;
									stmt0 = con.createStatement(); 
									String sqlStmt0 = "select distinct NODE_PK,NODE_ID,NODE_NAME,NODE_TYPE,SITE_ID,CIRCLE_ID,FILENAME  from TEMP_NODE_ACTIVE where DOMAIN='" + rsinit2.getString("DOMAIN") +"' and VENDOR='" + rsinit2.getString("VENDOR") +"'";          
									ResultSet rs0 = stmt0.executeQuery(sqlStmt0);
									while (rs0.next()) {
										GetAntennamovementfromtemptable(rs0.getString("NODE_PK"),rs0.getString("NODE_ID"),rs0.getString("NODE_NAME"),rs0.getString("NODE_TYPE"),rs0.getString("SITE_ID"),rs0.getString("CIRCLE_ID"),rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
									}
									rs0.close();
									stmt0.close();
									System.out.println("3-Check Antenna Movement  COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									logger.info("3-Check Antenna Movement COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									
									
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logsDetailsId= localgetseqNbr(24);
									logsDetailsId=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsId;
									PreparedStatement insertLogsDetailsStatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logsDetailsId+"',sysdate ,'CheckAntennaMovement','3-Check Antenna Movement COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
				 					 		
									insertLogsDetailsStatement.executeUpdate();
									insertLogsDetailsStatement.close();
						 			  
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logs_Details_Id= localgetseqNbr(24);
									logs_Details_Id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_Details_Id;
									PreparedStatement insert_LogsDetailsStatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logs_Details_Id+"',sysdate ,'CheckAntennaMovement','Number Of Errors','','Number Of Errors','','"+nbOfErrors+"','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
				 					 		
									insert_LogsDetailsStatement.executeUpdate();
									insert_LogsDetailsStatement.close();
									
									//insert into AUTO_DISCOVERY_LOGS
								 	  PreparedStatement insertLogsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS (LOGS_ID,START_TIME,ACTIVITY_NAME,VENDOR,DOMAIN,STOP_TIME) "
										 		+ "values('"+logsid+"', ? ,'CheckAntennaMovement','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"',?) ");
										 	
								 	 insertLogsstmt.setTimestamp(1, startTime);
								 	insertLogsstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
									insertLogsstmt.executeUpdate();
									insertLogsstmt.close();
									
									
						 		  }
					 	   }
						
					 	  rsinit2.close();
					 	  stmtinit2.close();
					
					con.close();
					
			}
			catch(Exception e){
			      System.err.println("Error main in CheckAntennaMovement : "+e);
			      e.printStackTrace();
			      logger.info("Error main in CheckAntennaMovement : "+e);
			      
			   }
		 
	}
	
	private static void GetAntennaReappearingfromAntannatable(String vdomain,String vvendor) throws SQLException  {
		 try {
				Statement stmt1 = null;
				Statement stmt2 = null;
				Statement stmt3 = null;
				Statement stmt4 = null;
				PreparedStatement stmtinsert1=null;
				PreparedStatement stmtinsert2=null;
				int totalrec=0;
				
				stmt1 = con.createStatement();   
			     // Get missing row from NODE_ANTENNA 
			    String sqlStmt = "select a.ANTENNA_ID,a.SERIALNUMBEREX,a.BOMCODE,a.ANTENNADEVICETYPE,a.UNITPOSITION,a.SERIALNUMBER,a.HARDWAREVERSION,a.VENDORUNITFAMILYTYPE,b.NODE_TYPE,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,a.LINE,a.CREATION_DATE,a.NODE_PK from NODE_ANTENNA a ,  NODE_ACTIVE b where a.TRANS_TYPE = 'Disappear' and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";          
			    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
			    while (rs1.next()) {
			    	
			    				    	
			    	stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);    
				     // Get  row from TEMP_NODE_ANTENNA 
				    String sqlStmt3 = "select a.ANTENNA_ID, a.SERIALNUMBEREX,a.BOMCODE,a.ANTENNADEVICETYPE,a.UNITPOSITION,a.SERIALNUMBER,a.HARDWAREVERSION,a.VENDORUNITFAMILYTYPE,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,a.LINE,a.CREATION_DATE from TEMP_NODE_ANTENNA a ,  TEMP_NODE_ACTIVE b where b.NODE_ID='" + rs1.getString("NODE_ID") +"' and  b.NODE_NAME='" + rs1.getString("NODE_NAME") +"' and  b.SITE_ID='" + rs1.getString("SITE_ID") +"' and  b.CIRCLE_ID='" + rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and a.VENDORUNITFAMILYTYPE='"+ rs1.getString("VENDORUNITFAMILYTYPE") +"' and a.HARDWAREVERSION='"+ rs1.getString("HARDWAREVERSION") +"' and a.SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"' and a.SERIALNUMBEREX='"+ rs1.getString("SERIALNUMBEREX") +"' and a.BOMCODE='"+ rs1.getString("BOMCODE") +"' and a.ANTENNADEVICETYPE='"+ rs1.getString("ANTENNADEVICETYPE") +"' and a.UNITPOSITION='"+ rs1.getString("UNITPOSITION") +"' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";          
				    ResultSet rs3 = stmt3.executeQuery(sqlStmt3);
				    rs3.last();
			 	    totalrec = rs3.getRow(); 
			 	   if (totalrec > 0 ) {
			 		 
				    		 String transid1=Gyear+"_"+ "ANTENNA"+"_"+localgetseqNbr(21);
				    		 String antennaid1=Gyear+"_"+localgetseqNbr(15);
				    		 GetNodedetails(rs1.getString("NODE_ID"),rs1.getString("NODE_NAME"),rs1.getString("NODE_TYPE"),vdomain,vvendor);
				    		 String vdetails=rs1.getString("SERIALNUMBEREX")+":"+ rs1.getString("BOMCODE")+":"+ rs1.getString("ANTENNADEVICETYPE")+":"+ rs1.getString("SERIALNUMBER")+":"+ rs1.getString("HARDWAREVERSION");
				    		
					    		 stmtinsert1 = con.prepareStatement("insert into  NODE_ANTENNA_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
						 			 		+  "values ('"+ transid1  +"',sysdate, 'Maintenance','Maintenance','0','0','0','0','0','0','" + rs1.getString("NODE_ID") +"','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','"+ rs1.getString("NODE_PK") +"','0','0')  ");
								  stmtinsert1.executeUpdate();
								  stmtinsert1.close();
								  
					    		 stmtinsert1 = con.prepareStatement("update  NODE_ANTENNA set TO_TRANS_SOURCE='ANTENNA', TO_TRANS_ID='" + transid1 +"',TRANS_TYPE='Reappear' ,ACTIVE_RECORD ='1',UPDATE_DATE=sysdate where ANTENNA_ID= '"+ rs1.getString("ANTENNA_ID") +"' and  ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
					    		 stmtinsert1.executeUpdate();
					    		 stmtinsert1.close();
					    		 
					    		 stmtinsert2 = con.prepareStatement("insert into  NODE_ANTENNA (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) select '"+ antennaid1 +"',INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,'"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"',STATUS,'ANTENNA','" + transid1 +"','0','Maintenance','1',LINE, TIMESTAMP '"+ rs1.getString("CREATION_DATE") +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_ANTENNA where ANTENNA_ID= '"+ rs3.getString("ANTENNA_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
						 			 		//+  "values ('"+ antennaid1  +"','" + rs1.getString("INVENTORYUNITID") + "', '" + rs1.getString("INVENTORYUNITTYPE") + "','" + rs1.getString("ANTENNADEVICENO") + "','" + rs1.getString("PRODNR") + "','" + rs1.getString("VENDORUNITFAMILYTYPE") + "','" + rs1.getString("VENDORUNITTYPENUMBER") + "','" + rs1.getString("VENDORNAME") + "','" + rs1.getString("SERIALNUMBER") + "','" + rs1.getString("HARDWAREVERSION") + "','" + rs1.getString("DATEOFMANUFACTURE") + "','" + rs1.getString("DATEOFLASTSERVICE") + "','" + rs1.getString("UNITPOSITION") + "','" + rs1.getString("MANUFACTURERDATA") + "','" + rs1.getString("ANTENNADEVICETYPE") + "','" + rs1.getString("ISSUENUMBER") + "','" + rs1.getString("BOMCODE") + "','" + rs1.getString("EXTINFO") + "','" + rs1.getString("MODEL") + "','" + rs1.getString("SERIALNUMBEREX") + "','"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','ANTENNA','"+ transid1 +"','0','Maintenance','1','"+ rs1.getString("LINE") +"')  ");
								  stmtinsert2.executeUpdate();
								  stmtinsert2.close();
				    		
			 	   }else {  // if not found we will search in all node in temp if found same SN in other NOde and move the Antenna disappear to transfer to other node
			 		   
			 		  stmt4 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);    
					     // GetSN from TEMP_NODE_ANTENNA  to check if transfered to other node
					    String sqlStmt4 = "select  b.ANTENNA_ID,a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,b.SERIALNUMBER,b.LINE,b.CREATION_DATE from TEMP_NODE_ANTENNA b ,  TEMP_NODE_ACTIVE a  where  a.ACTIVE_RECORD='1' and  b.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.SERIALNUMBER <> '0' and b.SERIALNUMBER= '" + rs1.getString("SERIALNUMBER") + "'   and b.CREATION_DATE =(select Max(CREATION_DATE) from TEMP_NODE_ANTENNA where SERIALNUMBER='" + rs1.getString("SERIALNUMBER") + "' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"')";          
					    ResultSet rs4 = stmt4.executeQuery(sqlStmt4);
					    rs4.last();
				 	    totalrec = rs4.getRow(); 
				 	    rs4.beforeFirst();
				 	    String newnode="0";
				 	   if (totalrec > 0 ) {
				 		  while (rs4.next()) {
				 			 newnode=rs4.getString("NODE_ID");
				 		  }
				 		 String transid1=Gyear+"_"+ "ANTENNA"+"_"+localgetseqNbr(21);
			    		 String antennaid1=Gyear+"_"+localgetseqNbr(15);
			    		 GetNodedetails(rs1.getString("NODE_ID"),rs1.getString("NODE_NAME"),rs1.getString("NODE_TYPE"),vdomain,vvendor);
			    		 String vdetails=rs1.getString("SERIALNUMBEREX")+":"+ rs1.getString("BOMCODE")+":"+ rs1.getString("ANTENNADEVICETYPE")+":"+ rs1.getString("SERIALNUMBER")+":"+ rs1.getString("HARDWAREVERSION");
				    		
			    		 stmtinsert1 = con.prepareStatement("insert into  NODE_ANTENNA_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
					 			 		+  "values ('"+ transid1  +"',sysdate, 'ANTENNA transfer to another node','ANTENNA transfer to another node','0','0','0','0','0','0','" + rs1.getString("NODE_ID") +"','" + newnode +"','0','0','"+ vdetails +"','0',sysdate,'0','0','1','"+ gnodepk +"','0','0')  ");
							  stmtinsert1.executeUpdate();
							  stmtinsert1.close();
							  //set active_record for disappear to 0
							  stmtinsert1 = con.prepareStatement("update  NODE_ANTENNA set TO_TRANS_SOURCE='ANTENNA', TO_TRANS_ID='" + transid1 +"',ACTIVE_RECORD ='0',UPDATE_DATE=sysdate where ANTENNA_ID= '"+ rs1.getString("ANTENNA_ID") +"' and  ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
					    		 stmtinsert1.executeUpdate();
					    		 stmtinsert1.close();
					    		//insert new record shwoing transfer to other node
					    		 stmtinsert1 = con.prepareStatement("insert into  NODE_ANTENNA (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE)   select '"+ antennaid1 +"',INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'ANTENNA','" + transid1 +"','0','ANTENNA transfer to another node','1',LINE,TIMESTAMP '"+ rs1.getString("CREATION_DATE") +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_ANTENNA where ANTENNA_ID= '"+ rs1.getString("ANTENNA_ID") +"' and  ACTIVE_RECORD='0' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
					    		 stmtinsert1.executeUpdate();
					    		 stmtinsert1.close();

				 	   }
			 		   
				 	  rs4.close();
					    stmt4.close();
			 		   
			 	   }
			 	   
			 	   
			 	   
			 	   
			 	   
				    rs3.close();
				    stmt3.close();
			    }
			    rs1.close();
			    stmt1.close();
	   }
		catch(Exception e){
		      System.err.println("Error in GetAntennaReappearingfromAntannatable: "+e);
		      e.printStackTrace();
		      logger.info("Error in GetAntennaReappearingfromAntannatable: "+e);
		      
		    //insert into AUTO_DISCOVERY_LOGS_DETAILS
				String logsDetailsId= localgetseqNbr(24);
				logsDetailsId=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsId;
				PreparedStatement insertLogsDetailsStatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsId+"',sysdate ,'CheckAntennaMovement','Error in GetAntennaReappearingfromAntannatable','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
				insertLogsDetailsStatement.executeUpdate();
				insertLogsDetailsStatement.close();
				nbOfErrors++;
		   }
	   
	}
	
	private static void GetAntennaDisappearingfromAntennatable(String vdomain,String vvendor) throws SQLException  {
		try {
				Statement stmt1 = null;
				Statement stmt2 = null;
				Statement stmt3 = null;
				PreparedStatement stmtinsert1=null;
				PreparedStatement stmtinsert2=null;
				int totalrec=0;
				
				stmt1 = con.createStatement();   
			     // Get missing row from NODE_ANTENNA 
			    String sqlStmt = "(select a.SERIALNUMBEREX,a.BOMCODE,a.ANTENNADEVICETYPE,a.UNITPOSITION,a.SERIALNUMBER,a.HARDWAREVERSION,a.VENDORUNITFAMILYTYPE,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID from NODE_ANTENNA a ,  NODE_ACTIVE b where a.TRANS_TYPE not in('Node Disappeared' ,'Disappear') and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"') minus (select a.SERIALNUMBEREX,a.BOMCODE,a.ANTENNADEVICETYPE,a.UNITPOSITION,a.SERIALNUMBER,a.HARDWAREVERSION,a.VENDORUNITFAMILYTYPE,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID from TEMP_NODE_ANTENNA a ,  TEMP_NODE_ACTIVE b where a.TRANS_TYPE not in ('Node Disappeared' ,'Disappear') and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"')";          
	
			    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
			    while (rs1.next()) {
			    	
			    	// verify if the rowdata/line not found in temp if yes keep it to check movement if not found chnage to disappaer
			    	
			    	stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);    
				     // Get missing row from NODE_ANTENNA 
				    String sqlStmt3 = "select a.ANTENNA_ID, a.SERIALNUMBEREX,a.BOMCODE,a.ANTENNADEVICETYPE,a.UNITPOSITION,a.SERIALNUMBER,a.HARDWAREVERSION,a.VENDORUNITFAMILYTYPE,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,a.LINE from TEMP_NODE_ANTENNA a ,  TEMP_NODE_ACTIVE b where b.NODE_ID='" + rs1.getString("NODE_ID") +"' and  b.NODE_NAME='" + rs1.getString("NODE_NAME") +"' and  b.SITE_ID='" + rs1.getString("SITE_ID") +"' and  b.CIRCLE_ID='" + rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and a.VENDORUNITFAMILYTYPE='"+ rs1.getString("VENDORUNITFAMILYTYPE") +"' and a.HARDWAREVERSION='"+ rs1.getString("HARDWAREVERSION") +"' and a.SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"' and a.SERIALNUMBEREX='"+ rs1.getString("SERIALNUMBEREX") +"' and a.BOMCODE='"+ rs1.getString("BOMCODE") +"' and a.ANTENNADEVICETYPE='"+ rs1.getString("ANTENNADEVICETYPE") +"' and a.UNITPOSITION='"+ rs1.getString("UNITPOSITION") +"' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";          
				    ResultSet rs3 = stmt3.executeQuery(sqlStmt3);
				    rs3.last();
			 	    totalrec = rs3.getRow(); 
			 	   if (totalrec == 0 ) {
			 		   
						    	//get missing rowdata and update active_record to 0
						    	stmt2 = con.createStatement(); 
						    	String sqlStmt2 = "select a.ANTENNA_ID,a.TRANS_TYPE, a.SERIALNUMBEREX,a.BOMCODE,a.ANTENNADEVICETYPE,a.UNITPOSITION,a.SERIALNUMBER,a.HARDWAREVERSION,a.VENDORUNITFAMILYTYPE,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,a.LINE,a.CREATION_DATE,a.NODE_PK from NODE_ANTENNA a ,  NODE_ACTIVE b where a.TRANS_TYPE <> 'Disappear' and a.NODE_PK=b.NODE_PK and  a.VENDORUNITFAMILYTYPE='"+ rs1.getString("VENDORUNITFAMILYTYPE") +"' and a.HARDWAREVERSION='"+ rs1.getString("HARDWAREVERSION") +"' and a.SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"' and a.SERIALNUMBEREX='"+ rs1.getString("SERIALNUMBEREX") +"' and a.BOMCODE='"+ rs1.getString("BOMCODE") +"' and a.ANTENNADEVICETYPE='"+ rs1.getString("ANTENNADEVICETYPE") +"' and a.UNITPOSITION='"+ rs1.getString("UNITPOSITION") +"' and b.NODE_NAME='" + rs1.getString("NODE_NAME") +"' and b.NODE_ID='" + rs1.getString("NODE_ID") +"' and b.SITE_ID='" + rs1.getString("SITE_ID") +"' and b.CIRCLE_ID='" + rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";  
						    	ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
						    	while (rs2.next()) {
						    		if (StringUtils.equalsIgnoreCase (rs2.getString("TRANS_TYPE"),"ANTENNA transfer to another node")) {
						    			// in case of transfer do nothing 
						    		} else {
						    			String transid=Gyear+"_"+ "ANTENNA"+"_"+localgetseqNbr(21);
							    		 String antennaid=Gyear+"_"+ "ANTENNA"+"_"+localgetseqNbr(15);
							    		 String vdetails=rs1.getString("SERIALNUMBEREX")+":"+ rs1.getString("BOMCODE")+":"+ rs1.getString("ANTENNADEVICETYPE")+":"+ rs1.getString("SERIALNUMBER")+":"+ rs1.getString("HARDWAREVERSION");
							    		 stmtinsert1 = con.prepareStatement("insert into  NODE_ANTENNA_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
								 			 		+  "values ('"+ transid  +"',sysdate, 'Disappear','Disappear','0','0','0','0','0','0','"+ rs2.getString("NODE_ID") +"','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','"+ rs2.getString("NODE_PK") +"','0','0')  ");
										  stmtinsert1.executeUpdate();
										  stmtinsert1.close();
										  
							    		 stmtinsert1 = con.prepareStatement("update  NODE_ANTENNA set TO_TRANS_SOURCE='ANTENNA', TO_TRANS_ID='" + transid +"', ACTIVE_RECORD ='0',UPDATE_DATE=sysdate where ANTENNA_ID= '"+ rs2.getString("ANTENNA_ID") +"' and  ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'" );
							    		 stmtinsert1.executeUpdate();
							    		 stmtinsert1.close();
							    		 
							    		 stmtinsert2 = con.prepareStatement("insert into  NODE_ANTENNA (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE)   select '"+ antennaid +"',INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'ANTENNA','" + transid +"','0','Disappear','1',LINE,TIMESTAMP '"+ rs2.getString("CREATION_DATE") +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_ANTENNA where ANTENNA_ID= '"+ rs2.getString("ANTENNA_ID") +"' and  ACTIVE_RECORD='0' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
								 			 		
										  stmtinsert2.executeUpdate();
										  stmtinsert2.close();
						    		}
						    		 

						    	}
							    rs2.close();
							    stmt2.close();
			 		   
		
			 	   }
				    rs3.close();
				    stmt3.close();
			    }
			    rs1.close();
			    stmt1.close();
		}
	    catch(Exception e){
		      System.err.println("Error in GetAntennaDisappearingfromAntennatable: "+e);
		      e.printStackTrace();
		      logger.info("Error in GetAntennaDisappearingfromAntennatable: "+e);
		      
		    //insert into AUTO_DISCOVERY_LOGS_DETAILS
				String logsDetailsId= localgetseqNbr(24);
				logsDetailsId=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsId;
				PreparedStatement insertLogsDetailsStatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsId+"',sysdate ,'CheckAntennaMovement','Error in GetAntennaDisappearingfromAntennatable','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
				insertLogsDetailsStatement.executeUpdate();
				insertLogsDetailsStatement.close();
				nbOfErrors++;
		   }
	   
	}
	
	private static void GetAntennamovementfromtemptable(String vnodepk, String vnodeid, String vnodename, String vnodetype,String vsiteid,String vcircleid,String vdomain,String vvendor) throws SQLException  {
		try {
				Statement stmt1 = null;
				Statement stmt2 = null;
				Statement stmt01 = null;
				PreparedStatement stmtinsert1=null;
				PreparedStatement stmtinsert2=null;
				PreparedStatement stmtinsert3=null;
				PreparedStatement stmtinsert4=null;
				int totalrec=0;
				String vstathw="0";
				
				stmt1 = con.createStatement();   
			     // read all SERIALNUMBEREX,BOMCODE,ANTENNADEVICETYPE,UNITPOSITION,SERIALNUMBER,HARDWAREVERSION,VENDORUNITFAMILYTYPE from TEMP_NODE_ANTENNA table  
			    String sqlStmt = "select  a.ANTENNA_ID,a.SERIALNUMBEREX,a.BOMCODE,a.ANTENNADEVICETYPE,a.UNITPOSITION,a.SERIALNUMBER,a.HARDWAREVERSION,a.VENDORUNITFAMILYTYPE,a.NODE_PK,a.LINE,a.FILENAME,b.SITE_ID,b.CIRCLE_ID,b.NODE_ID  from TEMP_NODE_ANTENNA a , TEMP_NODE_ACTIVE b where a.NODE_PK='" + vnodepk + "' and a.ACTIVE_RECORD='1'  and a.NODE_PK=b.NODE_PK and b.NODE_ID='" + vnodeid + "' and b.NODE_NAME='" + vnodename + "' and b.SITE_ID='" +  vsiteid + "' and b.CIRCLE_ID='" + vcircleid + "' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";          
			    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
			    while (rs1.next()) {
			    	
			    	//compare with final  by SERIALNUMBEREX,BOMCODE,ANTENNADEVICETYPE,UNITPOSITION,SERIALNUMBER,HARDWAREVERSION,VENDORUNITFAMILYTYPE and Line
			    	stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			    	//String sqlStmt2 = "select a.ANTENNA_ID, a.SERIALNUMBEREX,a.BOMCODE,a.ANTENNADEVICETYPE,a.UNITPOSITION,a.SERIALNUMBER,a.HARDWAREVERSION,a.VENDORUNITFAMILYTYPE,a.NODE_PK,a.LINE,a.CREATION_DATE,b.NODE_ID,b.NODE_TYPE,b.NODE_NAME from NODE_ANTENNA a , NODE_ACTIVE b  where  a.TRANS_TYPE <> 'Disappear' and a.NODE_PK=b.NODE_PK and a.ACTIVE_RECORD='1'  and b.NODE_ID='"+ vnodeid +"' and b.NODE_TYPE='"+ vnodetype +"' and b.NODE_NAME='"+ vnodename +"' and b.SITE_ID='"+ rs1.getString("SITE_ID") +"' and CIRCLE_ID='"+ rs1.getString("CIRCLE_ID") +"' and a.VENDORUNITFAMILYTYPE='"+ rs1.getString("VENDORUNITFAMILYTYPE") +"' and a.HARDWAREVERSION='"+ rs1.getString("HARDWAREVERSION") +"' and a.SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"' and a.SERIALNUMBEREX='"+ rs1.getString("SERIALNUMBEREX") +"' and a.BOMCODE='"+ rs1.getString("BOMCODE") +"' and a.ANTENNADEVICETYPE='"+ rs1.getString("ANTENNADEVICETYPE") +"' and a.UNITPOSITION='"+ rs1.getString("UNITPOSITION") +"' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";  
			    	String sqlStmt2 = "select a.ANTENNA_ID, a.SERIALNUMBEREX,a.BOMCODE,a.ANTENNADEVICETYPE,a.UNITPOSITION,a.SERIALNUMBER,a.HARDWAREVERSION,a.VENDORUNITFAMILYTYPE,a.NODE_PK,a.LINE,a.CREATION_DATE,b.NODE_ID,b.NODE_TYPE,b.NODE_NAME from NODE_ANTENNA a , NODE_ACTIVE b  where   a.NODE_PK=b.NODE_PK and a.ACTIVE_RECORD='1' and  b.ACTIVE_RECORD='1'  and b.NODE_ID='"+ vnodeid +"' and b.NODE_TYPE='"+ vnodetype +"' and b.NODE_NAME='"+ vnodename +"' and b.SITE_ID='"+ rs1.getString("SITE_ID") +"' and CIRCLE_ID='"+ rs1.getString("CIRCLE_ID") +"' and a.VENDORUNITFAMILYTYPE='"+ rs1.getString("VENDORUNITFAMILYTYPE") +"' and a.HARDWAREVERSION='"+ rs1.getString("HARDWAREVERSION") +"' and a.SERIALNUMBEREX='"+ rs1.getString("SERIALNUMBEREX") +"'  and a.BOMCODE='"+ rs1.getString("BOMCODE") +"' and a.ANTENNADEVICETYPE='"+ rs1.getString("ANTENNADEVICETYPE") +"' and a.UNITPOSITION='"+ rs1.getString("UNITPOSITION") +"' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"' and a.LINE='"+ rs1.getString("LINE") +"'";  

			    	ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
				    rs2.last();
			 	    totalrec = rs2.getRow(); 
			 	    rs2.beforeFirst();
			 	   if (totalrec >0 ) {
			 		  while (rs2.next()) {
			 			     // if we have same line value(same ver dsec line) we will update updatedate field only if not we will set active record to 0 with Software version Change and create new row with new value
			 				
			 			 if (StringUtils.equalsIgnoreCase (rs1.getString("SERIALNUMBEREX"),rs2.getString("SERIALNUMBEREX")) && (StringUtils.equalsIgnoreCase (rs1.getString("BOMCODE"),rs2.getString("BOMCODE"))) && (StringUtils.equalsIgnoreCase (rs1.getString("ANTENNADEVICETYPE"),rs2.getString("ANTENNADEVICETYPE"))) && (StringUtils.equalsIgnoreCase (rs1.getString("UNITPOSITION"),rs2.getString("UNITPOSITION"))) && (StringUtils.equalsIgnoreCase (rs1.getString("SERIALNUMBER"),rs2.getString("SERIALNUMBER"))) && (StringUtils.equalsIgnoreCase (rs1.getString("HARDWAREVERSION"),rs2.getString("HARDWAREVERSION"))) && (StringUtils.equalsIgnoreCase (rs1.getString("VENDORUNITFAMILYTYPE"),rs2.getString("VENDORUNITFAMILYTYPE"))) && (StringUtils.equalsIgnoreCase (rs1.getString("LINE"),rs2.getString("LINE"))) ) {
			 				// record found in final update LAST_MODIFIED_DATE
				 			 stmtinsert1 = con.prepareStatement("update  NODE_ANTENNA set UPDATE_DATE =sysdate where ANTENNA_ID= '"+ rs2.getString("ANTENNA_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				    		 stmtinsert1.executeUpdate();
				    		 stmtinsert1.close();
			 			 } else {
			 				 GetNodedetails(vnodeid,vnodename,vnodetype,vdomain,vvendor);
			 				  String transid1=Gyear+"_"+ "ANTENNA"+"_"+localgetseqNbr(21);
							  String antennaid1=Gyear+"_"+ "ANTENNA"+"_"+localgetseqNbr(15);
			 				// record  in final does not have same data diif in version move active_record to 0 and creat new row
				 			 stmtinsert1 = con.prepareStatement("update  NODE_ANTENNA set TO_TRANS_SOURCE='ANTENNA', TO_TRANS_ID='" + transid1 +"' ,ACTIVE_RECORD ='0' where ANTENNA_ID= '"+ rs2.getString("ANTENNA_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				    		 stmtinsert1.executeUpdate();
				    		 stmtinsert1.close();
				    		 
				    		 String vdetails1=rs1.getString("SERIALNUMBEREX")+":"+ rs1.getString("BOMCODE")+":"+ rs1.getString("ANTENNADEVICETYPE")+":"+ rs1.getString("SERIALNUMBER")+":"+ rs1.getString("HARDWAREVERSION");
				    		 
							  stmtinsert4 = con.prepareStatement("insert into  NODE_ANTENNA_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
					 			 		+  "values ('"+ transid1  +"',sysdate, 'ANTENNA SN Replacement','ANTENNA SN Replacement','0','0','0','0','0','0','"+ rs2.getString("NODE_ID") +"','0','0','0','"+ vdetails1 +"','0',sysdate,'0','"+rs2.getString("SERIALNUMBER")+"','1','"+ rs2.getString("NODE_PK") +"','0','"+rs1.getString("SERIALNUMBER")+"')  ");
							  stmtinsert4.executeUpdate();
							  stmtinsert4.close();
						  
							 // stmtinsert2 = con.prepareStatement("insert into  NODE_ANTENNA (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) select '"+ antennaid1  +"',INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,'"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','ANTENNA','"+ transid1 +"','0','Antenna SN Replacement','1','"+ gnodeline +"',TIMESTAMP '"+ rs2.getString("CREATION_DATE") +"',DOMAIN,VENDOR,TO_TRANS_SOURCE  from  TEMP_NODE_ANTENNA where ANTENNA_ID ='"+ rs1.getString("ANTENNA_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
							  stmtinsert2 = con.prepareStatement("insert into  NODE_ANTENNA (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) select '"+ antennaid1  +"',INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,'"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','ANTENNA','"+ transid1 +"','0','Antenna SN Replacement','1',LINE,TIMESTAMP '"+ rs2.getString("CREATION_DATE") +"',DOMAIN,VENDOR,TO_TRANS_SOURCE  from  TEMP_NODE_ANTENNA where ANTENNA_ID ='"+ rs1.getString("ANTENNA_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");

							  stmtinsert2.executeUpdate();
							  stmtinsert2.close();
			 			   }// end else
			 			  
						  
					    }
			 	   }else {
			 		 
			 		   // record not found in final add it 
			 		  GetNodedetails(vnodeid,vnodename,vnodetype,vdomain,vvendor);
			 		  String transid=Gyear+"_"+ "ANTENNA"+"_"+localgetseqNbr(21);
					  String antennaid=Gyear+"_"+ "ANTENNA"+"_"+localgetseqNbr(15);
					  String vdetails=rs1.getString("SERIALNUMBEREX")+":"+ rs1.getString("BOMCODE")+":"+ rs1.getString("ANTENNADEVICETYPE")+":"+ rs1.getString("SERIALNUMBER")+":"+ rs1.getString("HARDWAREVERSION");
			
					  if(StringUtils.equalsIgnoreCase(rs1.getString("SERIALNUMBER"),"0")) {
						  stmtinsert2 = con.prepareStatement("insert into  NODE_ANTENNA (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) select '"+ antennaid  +"',INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,'"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','ANTENNA','"+ transid +"','0','NEW ANTENNA','1','"+ gnodeline +"',CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from  TEMP_NODE_ANTENNA where ANTENNA_ID ='"+ rs1.getString("ANTENNA_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
					 		 stmtinsert2.executeUpdate();
					 		 stmtinsert2.close();
					  
					  }
					  else {
							stmt01 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
						    String sqlStmt01 = "select ANTENNA_ID,SERIALNUMBER,NODE_PK,CREATION_DATE from NODE_ANTENNA where SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
						    ResultSet rs01 = stmt01.executeQuery(sqlStmt01);
						    rs01.last();
					 	    int totalrec01 = rs01.getRow(); 
					 	   if (totalrec01 >0) {
					 		  vstathw= "Existed Hardware";
					 	   } else {
					 		  vstathw= "New Hardware";
					 	   }
					 	  rs01.close();
					 	  stmt01.close();
					  stmtinsert3 = con.prepareStatement("insert into  NODE_ANTENNA_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
				 			 		+  "values ('"+ transid  +"',sysdate, '"+vstathw+"','"+vstathw+"','0','0','0','0','0','0','"+ rs1.getString("NODE_ID") +"','0','0','0','"+ vdetails +"','0',sysdate,'0','"+rs1.getString("SERIALNUMBER")+"','1','"+ gnodepk +"','0','"+rs1.getString("SERIALNUMBER")+"')  ");
					stmtinsert3.executeUpdate();
					stmtinsert3.close();
					
					stmtinsert2 = con.prepareStatement("insert into  NODE_ANTENNA (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) select '"+ antennaid  +"',INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,'"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','ANTENNA','"+ transid +"','0','"+vstathw+"','1','"+ gnodeline +"',CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from  TEMP_NODE_ANTENNA where ANTENNA_ID ='"+ rs1.getString("ANTENNA_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
			 		 stmtinsert2.executeUpdate();
			 		 stmtinsert2.close();
					  
					  } 
			 	   }
				    rs2.close();
				    stmt2.close();
			    }
			    rs1.close();
			    stmt1.close();
	    
		}
		catch(Exception e){
		      System.err.println("Error in GetAntennamovementfromtemptable: "+e);
		      e.printStackTrace();
		      logger.info("Error in GetAntennamovementfromtemptable: "+e);
		      
		      //insert into AUTO_DISCOVERY_LOGS_DETAILS
				String logsDetailsId= localgetseqNbr(24);
				logsDetailsId=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsId;
				PreparedStatement insertLogsDetailsStatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsId+"',sysdate ,'CheckAntennaMovement','Error in GetAntennamovementfromtemptable','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
				insertLogsDetailsStatement.executeUpdate();
				insertLogsDetailsStatement.close();
				nbOfErrors++;
		   }
	}

	
	
	
	private static void GetNodedetails(String vnodeid, String vnodename, String vnodetype,String vdomain,String vvendor) throws SQLException {
	   try {
				gnodepk = "0" ;
			    gnodepkattr = "0" ;
			    gnodefilename = "0" ;
			    gnodeline=0;
				Statement stmttype = null;
				String query2 = "select b.NODE_PK,b.NODE_ATTR_PK,b.FILENAME from NODE_ACTIVE a, NODE_ANTENNA b where a.NODE_ID='"+ vnodeid +"' and a.NODE_NAME='"+ vnodename +"' and a.NODE_TYPE='"+ vnodetype +"' and b.NODE_PK=a.NODE_PK and b.ACTIVE_RECORD='1' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"'";      
			    stmttype = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			    ResultSet rs2 = stmttype.executeQuery(query2);
			       
			    rs2.last();
		 	    int totalrec = rs2.getRow(); 
		 	    rs2.beforeFirst();
		 	   
		 	  
		 	   if (totalrec >0 ) {
		 		  gnodeline= totalrec;
		 		  while (rs2.next()) {
		 		    	gnodepk= rs2.getString("NODE_PK");   
		 		    	gnodepkattr= rs2.getString("NODE_ATTR_PK"); 
		 		    	gnodefilename= rs2.getString("FILENAME"); 
		
		 		     } 
		 	   }
			     rs2.close();
			     stmttype.close();
	   }
	     catch(Exception e){
		      System.err.println("Error in GetNodedetails CheckAntenna: "+e);
		      e.printStackTrace();
		      logger.info("Error in GetNodedetails CheckAntenna: "+e);
		      
		      //insert into AUTO_DISCOVERY_LOGS_DETAILS
				String logsDetailsId= localgetseqNbr(24);
				logsDetailsId=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsId;
				PreparedStatement insertLogsDetailsStatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsId+"',sysdate ,'CheckAntennaMovement','Error in GetNodedetails CheckAntenna','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
				insertLogsDetailsStatement.executeUpdate();
				insertLogsDetailsStatement.close();
				nbOfErrors++;
		   }
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
			SeqName = "NODEACTIVE_SEQ";
			break;
		case 1:
			SeqName = "NODEACTIVEATTRIBUTE_SEQ";
			break;
		case 2:
			SeqName = "NODERACK_SEQ";
			break;
		case 3:
			SeqName = "NODEFRAME_SEQ";
			break;
		case 4:
			SeqName = "NODESLOT_SEQ";
			break;
		case 5:
			SeqName = "NODEBOARD_SEQ";
			break;
		case 6:
			SeqName = "NODEPORT_SEQ";
			break;
		case 7:
			SeqName = "NODECABINET_SEQ";
			break;
		case 8:
			SeqName = "NODEHOSTVER_SEQ";
			break;
		case 9:
			SeqName = "NODEACCESSORY_SEQ";
			break;
		case 10:
			SeqName = "NODEHOST_SEQ";
			break;
		case 11:
			SeqName = "NODESUBRACK_SEQ";
			break;
		case 12:
			SeqName = "NODEGCELL_SEQ";
			break;
		case 13:
			SeqName = "NODEBTS_SEQ";
			break;
		case 14:
			SeqName = "NODEUCELL_SEQ";
			break;
		case 15:
			SeqName = "NODEANTENNA_SEQ";
			break;
		case 16:
			SeqName = "NODELCELL_SEQ";
			break;
		case 17:
			SeqName = "NODERRN_SEQ";
			break;
		case 18:
			SeqName = "NODEENODEBCELL_SEQ";
			break;
		case 19:
			SeqName = "NODEBCELL_SEQ";
			break;
		case 20:
			SeqName = "NODENBINTERFACE_SEQ";
			break;
		case 21:
			SeqName = "NODETRANSACTION_SEQ";
		case 22:
			SeqName = "NODECHILDPARENT_SEQ";
			break;
			
		case 23:
			SeqName = "AUTO_DISCOVERY_LOGS_SEQ";
			break;
			
		case 24:
			SeqName = "AUTO_DISCOVERY_LOGS_DETAILS_SEQ";
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
