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

public class CheckHostVersionMovement {
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
					String lofilename="HostVersionMovement-"+dtf.format(now)+".log";
				
				
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
						 			  
						 			  	logger.info("1-Check HostVersion Disappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
										System.out.println("1-Check HostVersion Disappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
											
										//insert into AUTO_DISCOVERY_LOGS_DETAILS
										String logsDetailsid= localgetseqNbr(24);
										logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
										PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 					 		+ "values('"+logsDetailsid+"',sysdate ,'CheckHostVersionMovement','1-Check HostVersion Disappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
					 					 		
										insertLogsDetailsstmt.executeUpdate();
										insertLogsDetailsstmt.close();
										
										GetHostVersionDisappearingfromHostvertable(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
										 
										System.out.println("1-Check HostVersion Disappearing  COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
										logger.info("1-Check HostVersion Disappearing COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));	
						 			  
										//insert into AUTO_DISCOVERY_LOGS_DETAILS
										String logsDetails_id= localgetseqNbr(24);
										logsDetails_id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_id;
										PreparedStatement insertLogsDetails_stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 					 		+ "values('"+logsDetails_id+"',sysdate ,'CheckHostVersionMovement','1-Check HostVersion Disappearing COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
					 					 		
										insertLogsDetails_stmt.executeUpdate();
										insertLogsDetails_stmt.close();
										
										/////////////////////////////////////////////////////////////	
										logger.info("2-Check HostVersion Reappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
										System.out.println("2-Check HostVersion Reappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
											
										//insert into AUTO_DISCOVERY_LOGS_DETAILS
										String logsDetails_Id= localgetseqNbr(24);
										logsDetails_Id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_Id;
										PreparedStatement insertLogsDetails_Stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 					 		+ "values('"+logsDetails_Id+"',sysdate ,'CheckHostVersionMovement','2-Check HostVersion Reappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
					 					 		
										insertLogsDetails_Stmt.executeUpdate();
										insertLogsDetails_Stmt.close();
										
										GetHostVersionReappearingfromHostvertable(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
										 
										System.out.println("2-Check HostVersion Reappearing  COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
										logger.info("2-Check HostVersion Reappearing COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));	
								
										//insert into AUTO_DISCOVERY_LOGS_DETAILS
										String logsDetails_ID= localgetseqNbr(24);
										logsDetails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_ID;
										PreparedStatement insertLogsDetails_statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 					 		+ "values('"+logsDetails_ID+"',sysdate ,'CheckHostVersionMovement','2-Check HostVersion Reappearing COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
					 					 		
										insertLogsDetails_statement.executeUpdate();
										insertLogsDetails_statement.close();
										
										/////////////////////////////////////////////////////////////	
										logger.info("3-Check HostVersion Movement for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
										System.out.println("3-Check HostVersion Movement for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
										
										//insert into AUTO_DISCOVERY_LOGS_DETAILS
										String logsDetailsID= localgetseqNbr(24);
										logsDetailsID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsID;
										PreparedStatement insertLogsDetailsstatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 					 		+ "values('"+logsDetailsID+"',sysdate ,'CheckHostVersionMovement','3-Check HostVersion Movement for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
					 					 		
										insertLogsDetailsstatement.executeUpdate();
										insertLogsDetailsstatement.close();
										
										Statement stmt0 = null;
										stmt0 = con.createStatement(); 
										String sqlStmt0 = "select distinct NODE_PK,NODE_ID,NODE_NAME,NODE_TYPE,SITE_ID,CIRCLE_ID,FILENAME  from TEMP_NODE_ACTIVE where  DOMAIN='" + rsinit2.getString("DOMAIN") +"' and VENDOR='" + rsinit2.getString("VENDOR") +"'";          
										ResultSet rs0 = stmt0.executeQuery(sqlStmt0);
										while (rs0.next()) {
											GetHostVersionmovementfromtemptable(rs0.getString("NODE_PK"),rs0.getString("NODE_ID"),rs0.getString("NODE_NAME"),rs0.getString("NODE_TYPE"),rs0.getString("SITE_ID"),rs0.getString("CIRCLE_ID"),rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
										}
										rs0.close();
										stmt0.close();
										System.out.println("3-Check HostVersion Movement  COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
										logger.info("3-Check HostVersion Movement COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						 			  
										//insert into AUTO_DISCOVERY_LOGS_DETAILS
										String logsDetails_Idd= localgetseqNbr(24);
										logsDetails_Idd=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_Idd;
										PreparedStatement insertLogsDetailsStatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 					 		+ "values('"+logsDetails_Idd+"',sysdate ,'CheckHostVersionMovement','3-Check HostVersion Movement COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
					 					 		
										insertLogsDetailsStatement.executeUpdate();
										insertLogsDetailsStatement.close();
										
										//insert into AUTO_DISCOVERY_LOGS_DETAILS
										String logsDetails_IdD= localgetseqNbr(24);
										logsDetails_IdD=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_IdD;
										PreparedStatement insertLogsDetails_Statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 					 		+ "values('"+logsDetails_IdD+"',sysdate ,'CheckHostVersionMovement','Number Of Errors','','Number Of Errors','','"+nbOfErrors+"','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
					 					 		
										insertLogsDetails_Statement.executeUpdate();
										insertLogsDetails_Statement.close();  
										
							 			//insert into AUTO_DISCOVERY_LOGS
									 	  PreparedStatement insertLogsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS (LOGS_ID,START_TIME,ACTIVITY_NAME,VENDOR,DOMAIN,STOP_TIME) "
											 		+ "values('"+logsid+"', ? ,'CheckHostVersionMovement','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"',?) ");
											 		
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
			      System.err.println("Error min in CheckHostVersionMovement: "+e);
			      e.printStackTrace();
			      logger.info("Error min in CheckHostVersionMovement: "+e);
			   }
		 
	}
	
	private static void GetHostVersionReappearingfromHostvertable(String vdomain, String vvendor) throws SQLException  {
		try {
				Statement stmt1 = null;
				Statement stmt2 = null;
				Statement stmt3 = null;
				PreparedStatement stmtinsert1=null;
				PreparedStatement stmtinsert2=null;
				int totalrec=0;
				
				stmt1 = con.createStatement();   
			     // Get missing row from NODE_HOSTVER 
			    String sqlStmt = "select a.HOSTVER_ID,a.HOSTVERTYPE,a.HOSTVER,a.SDESC,b.NODE_TYPE,b.NODE_NAME,b.NODE_ID,b.NODE_PK,b.SITE_ID,b.CIRCLE_ID,a.LINE,a.CREATION_DATE from NODE_HOSTVER a ,  NODE_ACTIVE b where a.TRANS_TYPE = 'Disappear' and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";          
			    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
			    while (rs1.next()) {
			    	
			    	// verify if the rowdata/line not found in temp if yes keep it to check movement if not found chnage to disappaer
			    	
			    	stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);    
				     // Get missing row from NODE_HOSTVER 
				    String sqlStmt3 = "select a.HOSTVER_ID,a.HOSTVERTYPE,a.HOSTVER,a.SDESC,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,a.LINE from TEMP_NODE_HOSTVER a ,  TEMP_NODE_ACTIVE b where b.NODE_ID='" + rs1.getString("NODE_ID") +"' and  b.NODE_NAME='" + rs1.getString("NODE_NAME") +"' and  b.SITE_ID='" + rs1.getString("SITE_ID") +"' and  b.CIRCLE_ID='" + rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and a.HOSTVERTYPE='" + rs1.getString("HOSTVERTYPE") +"' and a.HOSTVER='" + rs1.getString("HOSTVER") +"' and a.SDESC='" + rs1.getString("SDESC") +"' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";          
				    ResultSet rs3 = stmt3.executeQuery(sqlStmt3);
				    rs3.last();
			 	    totalrec = rs3.getRow(); 
			 	   if (totalrec > 0 ) {
		
				    		 String transid1=Gyear+"_"+ "HOSTVER"+"_"+localgetseqNbr(21);
				    		 String hostid1=Gyear+"_"+ "HOSTVER"+"_"+localgetseqNbr(8);
				    		 GetNodedetails(rs1.getString("NODE_ID"),rs1.getString("NODE_NAME"),rs1.getString("NODE_TYPE"),vdomain,vvendor);
				    		 String vdetails=rs1.getString("HOSTVERTYPE")+":"+ rs1.getString("HOSTVER")+":"+ rs1.getString("SDESC") +":"+ rs1.getString("NODE_NAME");
				    		// stmtinsert1 = con.prepareStatement("insert into  NODE_HOSTVER_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
					 		//	 		+  "values ('"+ transid1  +"',sysdate, 'Disappear','Disappear','0','0','0','0','0','0','"+ rs1.getString("NODE_ID") +"','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','"+ rs1.getString("NODE_PK") +"','0','0')  ");
				    		 stmtinsert1 = con.prepareStatement("insert into  NODE_HOSTVER_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
					 			 		+  "values ('"+ transid1  +"',sysdate, 'Software Maintenance','Software Maintenance','0','0','0','0','0','0','"+ rs1.getString("NODE_ID") +"','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','"+ rs1.getString("NODE_PK") +"','0','0')  ");
				    		 stmtinsert1.executeUpdate();
							  stmtinsert1.close();
							  
							  //remove reappear from trans_type
				    		 stmtinsert1 = con.prepareStatement("update  NODE_HOSTVER set TO_TRANS_SOURCE='HOSTVER', TO_TRANS_ID='" + transid1 +"' ,ACTIVE_RECORD ='0',UPDATE_DATE=sysdate where HOSTVER_ID= '"+ rs1.getString("HOSTVER_ID") +"' and  ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				    		 stmtinsert1.executeUpdate();
				    		 stmtinsert1.close();
				    		 
				    		// stmtinsert2 = con.prepareStatement("insert into  NODE_HOSTVER (HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
					 		//	 		+  "values ('"+ hostid1  +"','" + rs1.getString("HOSTVERTYPE") + "', '" + rs1.getString("HOSTVER") + "','" + rs1.getString("SDESC") + "','"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','HOSTVER','"+ transid1 +"','0','Maintenance','1','"+ rs1.getString("LINE") +"',TIMESTAMP '"+ rs1.getString("CREATION_DATE") +"','" + vdomain +"','" + vvendor +"','0')  ");
				    		 stmtinsert2 = con.prepareStatement("insert into  NODE_HOSTVER (HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
					 			 		+  "values ('"+ hostid1  +"','" + rs1.getString("HOSTVERTYPE") + "', '" + rs1.getString("HOSTVER") + "','" + rs1.getString("SDESC") + "','"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','HOSTVER','"+ transid1 +"','0','Software Maintenance','1','"+ rs1.getString("LINE") +"',TIMESTAMP '"+ rs1.getString("CREATION_DATE") +"','" + vdomain +"','" + vvendor +"','0')  ");
				    		 stmtinsert2.executeUpdate();
							  stmtinsert2.close();
			 	   }
				    rs3.close();
				    stmt3.close();
			    }
			    rs1.close();
			    stmt1.close();
		}
	catch(Exception e){
	      System.err.println("Error in GetHostVersionReappearingfromHostvertable: "+e);
	      e.printStackTrace();
	      logger.info("Error in GetHostVersionReappearingfromHostvertable: "+e);
	      
	    //insert into AUTO_DISCOVERY_LOGS_DETAILS
			String logsDetailsID= localgetseqNbr(24);
			logsDetailsID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsID;
			PreparedStatement insertLogsDetailsstatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
			 		+ "values('"+logsDetailsID+"',sysdate ,'CheckHostVersionMovement','Error in GetHostVersionReappearingfromHostvertable','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
			 		
			insertLogsDetailsstatement.executeUpdate();
			insertLogsDetailsstatement.close();
			nbOfErrors++;
	   }
	   
	}
	
	private static void GetHostVersionDisappearingfromHostvertable(String vdomain, String vvendor) throws SQLException  {
		try {
				Statement stmt1 = null;
				Statement stmt2 = null;
				Statement stmt3 = null;
				PreparedStatement stmtinsert1=null;
				PreparedStatement stmtinsert2=null;
				int totalrec=0;
				
				stmt1 = con.createStatement();   
			     // Get missing row from NODE_HOSTVER 
			    String sqlStmt = "(select a.HOSTVERTYPE,a.HOSTVER,a.SDESC,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID from NODE_HOSTVER a ,  NODE_ACTIVE b where a.TRANS_TYPE <> 'Disappear' and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"') minus (select a.HOSTVERTYPE,a.HOSTVER,a.SDESC,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID from TEMP_NODE_HOSTVER a ,  TEMP_NODE_ACTIVE b where a.TRANS_TYPE <> 'Disappear' and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"')";          
			    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
			    while (rs1.next()) {
			    	
			    	// verify if the rowdata/line not found in temp if yes keep it to check movement if not found chnage to disappaer
			    	
			    	stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);    
				     // Get missing row from NODE_HOSTVER 
				    String sqlStmt3 = "select a.HOSTVERTYPE,a.HOSTVER,a.SDESC,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,a.LINE from TEMP_NODE_HOSTVER a ,  TEMP_NODE_ACTIVE b where b.NODE_ID='" + rs1.getString("NODE_ID") +"' and  b.NODE_NAME='" + rs1.getString("NODE_NAME") +"' and  b.SITE_ID='" + rs1.getString("SITE_ID") +"' and  b.CIRCLE_ID='" + rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and a.HOSTVERTYPE='" + rs1.getString("HOSTVERTYPE") +"' and a.HOSTVER='" + rs1.getString("HOSTVER") +"' and a.SDESC='" + rs1.getString("SDESC") +"' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";          
				    ResultSet rs3 = stmt3.executeQuery(sqlStmt3);
				    rs3.last();
			 	    totalrec = rs3.getRow(); 
			 	   if (totalrec == 0 ) {
				    	//get missing rowdata and update active_record to 0
				    	stmt2 = con.createStatement(); 
				    	String sqlStmt2 = "select a.HOSTVER_ID,a.HOSTVERTYPE,a.HOSTVER,a.SDESC,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,a.LINE,a.CREATION_DATE,a.NODE_PK from NODE_HOSTVER a ,  NODE_ACTIVE b where a.TRANS_TYPE <> 'Disappear' and a.NODE_PK=b.NODE_PK and a.HOSTVERTYPE= '" + rs1.getString("HOSTVERTYPE") +"' and a.HOSTVER='" + rs1.getString("HOSTVER") +"' and a.SDESC='" + rs1.getString("SDESC") +"' and b.NODE_NAME='" + rs1.getString("NODE_NAME") +"' and b.NODE_ID='" + rs1.getString("NODE_ID") +"' and b.SITE_ID='" + rs1.getString("SITE_ID") +"' and b.CIRCLE_ID='" + rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";  
				    	ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
				    	while (rs2.next()) {
				    		 String transid1=Gyear+"_"+ "HOSTVER"+"_"+localgetseqNbr(21);
				    		 String hostid=Gyear+"_"+ "HOSTVER"+"_"+localgetseqNbr(8);
				    		 String vdetails=rs1.getString("HOSTVERTYPE")+":"+ rs1.getString("HOSTVER")+":"+ rs1.getString("SDESC") +":"+ rs1.getString("NODE_NAME");
				    		 stmtinsert1 = con.prepareStatement("insert into  NODE_HOSTVER_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
					 			 		+  "values ('"+ transid1  +"',sysdate, 'Disappear','Disappear','0','0','0','0','0','0','"+  rs2.getString("NODE_ID") +"','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','"+ rs2.getString("NODE_PK") +"','0','0')  ");
							  stmtinsert1.executeUpdate();
							  stmtinsert1.close();
							  
				    		 stmtinsert1 = con.prepareStatement("update  NODE_HOSTVER set TO_TRANS_SOURCE='HOSTVER', TO_TRANS_ID='" + transid1 +"', ACTIVE_RECORD ='0',UPDATE_DATE=sysdate where HOSTVER_ID= '"+ rs2.getString("HOSTVER_ID") +"' and  ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				    		 stmtinsert1.executeUpdate();
				    		 stmtinsert1.close();
				    		 
				    		 stmtinsert2 = con.prepareStatement("insert into  NODE_HOSTVER (HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE)   select '"+ hostid +"',HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'HOSTVER','" + transid1 +"','0','Disappear','1',LINE,TIMESTAMP '"+ rs2.getString("CREATION_DATE") +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_HOSTVER where HOSTVER_ID= '"+ rs2.getString("HOSTVER_ID") +"' and  ACTIVE_RECORD='0' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' ");
					 			 		
							  stmtinsert2.executeUpdate();
							  stmtinsert2.close();
				    		 
				    		 //reorder line sequence in node_hostver
				    		 //stmtinsert2 = con.prepareStatement("update NODE_HOSTVER set line = TO_NUMBER(line)-1 where   TO_NUMBER(line)>0 and ACTIVE_RECORD='1' and NODE_PK='"+ rs2.getString("NODE_PK")+ "'");
				    		 //stmtinsert2.executeUpdate();
				    		 //stmtinsert2.close();
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
		      System.err.println("Error in GetHostVersionDisappearingfromHostvertable: "+e);
		      e.printStackTrace();
		      logger.info("Error in GetHostVersionDisappearingfromHostvertable: "+e);
		      
		    //insert into AUTO_DISCOVERY_LOGS_DETAILS
				String logsDetailsID= localgetseqNbr(24);
				logsDetailsID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsID;
				PreparedStatement insertLogsDetailsstatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsID+"',sysdate ,'CheckHostVersionMovement','Error in GetHostVersionDisappearingfromHostvertable','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
				insertLogsDetailsstatement.executeUpdate();
				insertLogsDetailsstatement.close();
				nbOfErrors++;
		   }
	   
	}
	
	private static void GetHostVersionmovementfromtemptable(String vnodepk, String vnodeid, String vnodename, String vnodetype,String vsiteid,String vcircleid,String vdomain, String vvendor) throws SQLException  {
		try {
				Statement stmt1 = null;
				Statement stmt2 = null;
				PreparedStatement stmtinsert1=null;
				PreparedStatement stmtinsert2=null;
				PreparedStatement stmtinsert3=null;
				PreparedStatement stmtinsert4=null;
				int totalrec=0;
				
				stmt1 = con.createStatement();   
			     // read all HOSTVERTYPE,HOSTVER,SDESC means all node_id from TEMP_NODE_HOSTVER table  
			    String sqlStmt = "select  a.HOSTVERTYPE,a.HOSTVER,a.SDESC,a.NODE_PK,a.LINE,a.FILENAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,a.CREATION_DATE  from TEMP_NODE_HOSTVER a , TEMP_NODE_ACTIVE b where a.NODE_PK='" + vnodepk + "' and a.ACTIVE_RECORD='1'  and a.NODE_PK=b.NODE_PK and b.NODE_ID='" + vnodeid + "' and b.NODE_NAME='" + vnodename + "' and b.SITE_ID='" +  vsiteid + "' and b.CIRCLE_ID='" + vcircleid + "' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";          
			    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
			    while (rs1.next()) {
			    	
			    	//compare with final  by HOSTVERTYPE,HOSTVER,SDESC an Line
			    	stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			    	String sqlStmt2 = "select a.HOSTVER_ID, a.HOSTVERTYPE,a.HOSTVER,a.SDESC,a.NODE_PK,a.LINE,b.NODE_ID,b.NODE_TYPE,b.NODE_NAME,a.CREATION_DATE from NODE_HOSTVER a , NODE_ACTIVE b  where  a.TRANS_TYPE <> 'Disappear' and a.NODE_PK=b.NODE_PK and a.ACTIVE_RECORD='1'  and b.NODE_ID='"+ vnodeid +"' and b.NODE_TYPE='"+ vnodetype +"' and b.NODE_NAME='"+ vnodename +"' and b.SITE_ID='"+ rs1.getString("SITE_ID") +"' and CIRCLE_ID='"+ rs1.getString("CIRCLE_ID") +"' and a.HOSTVERTYPE='"+ rs1.getString("HOSTVERTYPE") +"' and a.HOSTVER='"+ rs1.getString("HOSTVER") +"' and a.SDESC='"+ rs1.getString("SDESC") +"' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";  
			    	ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
				    rs2.last();
			 	    totalrec = rs2.getRow(); 
			 	    rs2.beforeFirst();
			 	   if (totalrec >0 ) {
			 		  while (rs2.next()) {
			 			     // if we have same line value(same ver dsec line) we will update updatedate field only if not we will set active record to 0 with Software version Change and create new row with new value
			 				
			 			 if (StringUtils.equalsIgnoreCase (rs1.getString("HOSTVERTYPE"),rs2.getString("HOSTVERTYPE")) && (StringUtils.equalsIgnoreCase (rs1.getString("HOSTVER"),rs2.getString("HOSTVER"))) && (StringUtils.equalsIgnoreCase (rs1.getString("SDESC"),rs2.getString("SDESC"))) ) {
			 				// record found in final update LAST_MODIFIED_DATE
				 			 stmtinsert1 = con.prepareStatement("update  NODE_HOSTVER set UPDATE_DATE =sysdate where HOSTVER_ID= '"+ rs2.getString("HOSTVER_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				    		 stmtinsert1.executeUpdate();
				    		 stmtinsert1.close();
			 			 } else {
			 				 GetNodedetails(vnodeid,vnodename,vnodetype,vdomain,vvendor);
			 				  String transid1=Gyear+"_"+ "HOSTVER"+"_"+localgetseqNbr(21);
							  String hostid1=Gyear+"_"+ "HOSTVER"+"_"+localgetseqNbr(8);
			 				// record  in final does not have same data diif in version move active_record to 0 and creat new row
				 			 stmtinsert1 = con.prepareStatement("update  NODE_HOSTVER set TO_TRANS_SOURCE='HOSTVER', TO_TRANS_ID='" + transid1 +"',TRANS_TYPE='Software Version Change' ,ACTIVE_RECORD ='0' where HOSTVER_ID= '"+ rs2.getString("HOSTVER_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				    		 stmtinsert1.executeUpdate();
				    		 stmtinsert1.close();
				    		 
				    		 String vdetails1=rs1.getString("HOSTVERTYPE")+":"+ rs1.getString("HOSTVER")+":"+ rs1.getString("SDESC");
				    		 
							  stmtinsert4 = con.prepareStatement("insert into  NODE_HOSTVER_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
					 			 		+  "values ('"+ transid1  +"',sysdate, 'Software Version Change','Software Version Change','0','0','0','0','0','0','" + rs2.getString("NODE_ID") + "','0','0','0','"+ vdetails1 +"','0',sysdate,'0','0','1','"+ gnodepk +"','0','0')  ");
							  stmtinsert4.executeUpdate();
							  stmtinsert4.close();
						  
							  stmtinsert2 = con.prepareStatement("insert into  NODE_HOSTVER (HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
					 			 		+  "values ('"+ hostid1  +"','" + rs1.getString("HOSTVERTYPE") + "', '" + rs1.getString("HOSTVER") + "','" + rs1.getString("SDESC") + "','"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','HOSTVER','"+ transid1 +"','0','Software Version Change','1','"+ rs1.getString("LINE") +"',TIMESTAMP '"+ rs2.getString("CREATION_DATE") +"','" + vdomain +"','" + vvendor +"','0')");
							  stmtinsert2.executeUpdate();
							  stmtinsert2.close();
			 			   }// end else
			 			  
						  
					    }
			 	   }else {
			 		   // record not found in final add it 
			 		  GetNodedetails(vnodeid,vnodename,vnodetype,vdomain,vvendor);
			 		  String transid=Gyear+"_"+ "HOSTVER"+"_"+localgetseqNbr(21);
					  String hostid=Gyear+"_"+ "HOSTVER"+"_"+localgetseqNbr(8);
					  String vdetails=rs1.getString("HOSTVERTYPE")+":"+ rs1.getString("HOSTVER")+":"+ rs1.getString("SDESC");
					  
					  stmtinsert3 = con.prepareStatement("insert into  NODE_HOSTVER_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
				 			 		+  "values ('"+ transid  +"',sysdate, 'Software Version Change','Software Version Change','0','0','0','0','0','0','" + rs1.getString("NODE_ID") + "','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','"+ gnodepk +"','0','0')  ");
					stmtinsert3.executeUpdate();
					stmtinsert3.close();
					  
			 		 stmtinsert2 = con.prepareStatement("insert into  NODE_HOSTVER (HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
				 			 		+  "values ('"+ hostid  +"','" + rs1.getString("HOSTVERTYPE") + "', '" + rs1.getString("HOSTVER") + "','" + rs1.getString("SDESC") + "','"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','HOSTVER','"+ transid +"','0','Software Version Change','1','"+ gnodeline +"',TIMESTAMP '" + rs1.getString("CREATION_DATE") + "','" + vdomain +"','" + vvendor +"','0')");
			 		 stmtinsert2.executeUpdate();
			 		 stmtinsert2.close();
			 	   }
				    rs2.close();
				    stmt2.close();
			    }
			    rs1.close();
			    stmt1.close();
		}
		catch(Exception e){
		      System.err.println("Error in GetHostVersionmovementfromtemptable: "+e);
		      e.printStackTrace();
		      logger.info("Error in GetHostVersionmovementfromtemptable: "+e);
		      
		    //insert into AUTO_DISCOVERY_LOGS_DETAILS
				String logsDetailsID= localgetseqNbr(24);
				logsDetailsID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsID;
				PreparedStatement insertLogsDetailsstatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsID+"',sysdate ,'CheckHostVersionMovement','Error in GetHostVersionmovementfromtemptable','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
				insertLogsDetailsstatement.executeUpdate();
				insertLogsDetailsstatement.close();
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
		String query2 = "select b.NODE_PK,b.NODE_ATTR_PK,b.FILENAME from NODE_ACTIVE a, NODE_HOSTVER b where a.NODE_ID='"+ vnodeid +"' and a.NODE_NAME='"+ vnodename +"' and a.NODE_TYPE='"+ vnodetype +"' and b.NODE_PK=a.NODE_PK and b.ACTIVE_RECORD='1' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"'";      
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
		      System.err.println("Error in GetNodedetails CheckHostver: "+e);
		      e.printStackTrace();
		      logger.info("Error in GetNodedetails CheckHostver: "+e);
		      
		    //insert into AUTO_DISCOVERY_LOGS_DETAILS
				String logsDetailsID= localgetseqNbr(24);
				logsDetailsID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsID;
				PreparedStatement insertLogsDetailsstatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsID+"',sysdate ,'CheckHostVersionMovement','Error in GetNodedetails CheckHostver','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
				insertLogsDetailsstatement.executeUpdate();
				insertLogsDetailsstatement.close();
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
