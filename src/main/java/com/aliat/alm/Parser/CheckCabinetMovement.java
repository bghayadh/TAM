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


public class CheckCabinetMovement {
	
	static Connection con ;
	static String Gnodeid="0";
	static String Gsiteid="0";
	static String Gcircleid="0";
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
		 Statement stmt0 = null;
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
					String lofilename="DisappearMoveHW-"+dtf.format(now)+".log";
				
				
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
						
						logsid = localgetseqNbr(22);
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
						
						logger.info("1-Check if we have New Disappearing Cabinet for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						System.out.println("1-Check if we have New Disappearing Cabinet for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						
						//insert into AUTO_DISCOVERY_LOGS_DETAILS
						String logsDetailsid= localgetseqNbr(23);
						logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
						PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDetailsid+"',sysdate ,'CheckCabinetMovement','1-Check if we have New Disappearing Cabinet for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
	 					 		
						insertLogsDetailsstmt.executeUpdate();
						insertLogsDetailsstmt.close();
						
						GetDisappearingCabinets(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
						
						logger.info("1-Disappearing Disappearing Cabinet Check COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						System.out.println("1-Disappearing Disappearing Cabinet Check COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));    
						
						//insert into AUTO_DISCOVERY_LOGS_DETAILS
						String logsDetails_id= localgetseqNbr(23);
						logsDetails_id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_id;
						PreparedStatement insertLogsDetailsStmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDetails_id+"',sysdate ,'CheckCabinetMovement','1-Disappearing Disappearing Cabinet Check COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
	 					 		
						insertLogsDetailsStmt.executeUpdate();
						insertLogsDetailsStmt.close();
						 		
						///////////////////////////////////////////////////////////////////////
												
						logger.info("2-Check if we have reappearied Cabinet after Disappear for  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						System.out.println("2-Check if we have reappearied Cabinet after Disappear for  " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						
						//insert into AUTO_DISCOVERY_LOGS_DETAILS
						String logsDetails_Id= localgetseqNbr(23);
						logsDetails_Id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_Id;
						PreparedStatement insertLogsDetails_Stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDetails_Id+"',sysdate ,'CheckCabinetMovement','2-Check if we have reappearied Cabinet after Disappear for  " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
	 					 		
						insertLogsDetails_Stmt.executeUpdate();
						insertLogsDetails_Stmt.close();
						
						GetReappearingCabinets(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
						
						logger.info("2-Reappearied Cabinet after Disappear COMPLETED for  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						System.out.println("2-Reappearied Cabinet after Disappear COMPLETED for " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						
						//insert into AUTO_DISCOVERY_LOGS_DETAILS
						String logsDetails_ID= localgetseqNbr(23);
						logsDetails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_ID;
						PreparedStatement insertLogsDetails_Statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDetails_ID+"',sysdate ,'CheckCabinetMovement','2-Reappearied Cabinet after Disappear COMPLETED for " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
	 					 		
						insertLogsDetails_Statement.executeUpdate();
						insertLogsDetails_Statement.close();
						
						///////////////////////////////////////////////////////////////////////
						
					
						
						logger.info("3-Check if we have replacement Transfer or new Cabinet for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						System.out.println("3-Check if we have replacement Transfer or new Cabinet for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						
						//insert into AUTO_DISCOVERY_LOGS_DETAILS
						String logsDEtails_ID= localgetseqNbr(23);
						logsDEtails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDEtails_ID;
						PreparedStatement insertLogsDetails_statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDEtails_ID+"',sysdate ,'CheckCabinetMovement','3-Check if we have replacement Transfer or new Cabinet for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
	 					 		
						insertLogsDetails_statement.executeUpdate();
						insertLogsDetails_statement.close();
						
						Statement stmtc = null;
						stmtc = con.createStatement(); 
						String sqlStmtc = "select distinct NODE_PK,NODE_ID,NODE_NAME,NODE_TYPE,SITE_ID,CIRCLE_ID,FILENAME  from TEMP_NODE_ACTIVE where  DOMAIN='" + rsinit2.getString("DOMAIN") +"' and VENDOR='" + rsinit2.getString("VENDOR") +"'";          
						ResultSet rsc = stmtc.executeQuery(sqlStmtc);
						while (rsc.next()) {
						GetNewCabinetsfromtemptable(rsc.getString("NODE_PK"),rsc.getString("NODE_ID"),rsc.getString("NODE_NAME"),rsc.getString("NODE_TYPE"),rsc.getString("SITE_ID"),rsc.getString("CIRCLE_ID"),rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
						}
						rsc.close();
						stmtc.close();
						
						System.out.println("3-check replacement Transfer or new Cabinet COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						logger.info("3-check replacement Transfer or new Cabinet COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						
						//insert into AUTO_DISCOVERY_LOGS_DETAILS
						String logsDETails_ID= localgetseqNbr(23);
						logsDETails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETails_ID;
						PreparedStatement insertLogsDEtails_statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDETails_ID+"',sysdate ,'CheckCabinetMovement','3-check replacement Transfer or new Cabinet COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
	 					 		
						insertLogsDEtails_statement.executeUpdate();
						insertLogsDEtails_statement.close();
						
						//insert into AUTO_DISCOVERY_LOGS_DETAILS
						String logsDETails_Id= localgetseqNbr(23);
						logsDETails_Id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETails_Id;
						PreparedStatement insertLogsDEtails_Statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDETails_Id+"',sysdate ,'CheckCabinetMovement','Number Of Errors','','Number Of Errors','','"+nbOfErrors+"','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
	 					 		
						insertLogsDEtails_Statement.executeUpdate();
						insertLogsDEtails_Statement.close();
						
						//insert into AUTO_DISCOVERY_LOGS
					 	  PreparedStatement insertLogsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS (LOGS_ID,START_TIME,ACTIVITY_NAME,VENDOR,DOMAIN,STOP_TIME) "
							 		+ "values('"+logsid+"', ? ,'CheckCabinetMovement','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"',?) ");
						
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
			      System.err.println(e);
			      e.printStackTrace();
			      logger.info("Error : "+e);
			   }
		 
	}
	
		
	
	private static void GetReappearingCabinets(String vdomain, String vvendor) throws SQLException  {
		try {
			Statement stmt1 = null;
			Statement stmt2 = null;
			Statement stmt3 = null;
			Statement stmt4 = null;
			PreparedStatement stmtinsert1=null;
			PreparedStatement stmtinsert2=null;
			int totalrec=0;
			String crdate =null;
			
			stmt1 = con.createStatement();   
		     // Get missing row from NODE_CABINET 
		    String sqlStmt = "select  b.CABINET_ID,a.NODE_ID,a.NODE_NAME,a.NODE_TYPE,a.SITE_ID,a.CIRCLE_ID,b.CABINETNO,b.SERIALNUMBER,b.LINE,b.CREATION_DATE from NODE_ACTIVE a , NODE_CABINET b where b.TRANS_TYPE = 'Cabinet Disappeared' and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.ACTIVE_RECORD='1' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"'";          
		    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
		    while (rs1.next()) {
		    	crdate=rs1.getString("CREATION_DATE");
		    	
		    	// verify if the rowdata/line not found in temp if yes keep it to check movement if not found chnage to disappaer
		    	
		    	stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);    
			     // Get missing row from TEMP_NODE_CABINET 
			    String sqlStmt3 = "select  b.CABINET_ID,a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,b.CABINETNO,b.SERIALNUMBER,b.LINE from TEMP_NODE_CABINET b ,  TEMP_NODE_ACTIVE a  where a.NODE_ID='" + rs1.getString("NODE_ID") + "' and  a.NODE_NAME='" + rs1.getString("NODE_NAME") + "' and  a.SITE_ID='" + rs1.getString("SITE_ID") + "' and  a.CIRCLE_ID='" + rs1.getString("CIRCLE_ID") + "' and  a.ACTIVE_RECORD='1' and  b.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.CABINETNO='" + rs1.getString("CABINETNO") + "' and b.SERIALNUMBER='" + rs1.getString("SERIALNUMBER") + "' and b.LINE='" + rs1.getString("LINE") + "' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"'";          
			    ResultSet rs3 = stmt3.executeQuery(sqlStmt3);
			    rs3.last();
		 	    totalrec = rs3.getRow(); 
		 	   if (totalrec > 0 ) {
	
			    		 String transid1=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(21);
			    		 String cabinetid1=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(7);
			    		 
			    		 GetNodedetails(rs1.getString("NODE_ID"),rs1.getString("NODE_NAME"),rs1.getString("NODE_TYPE"),vdomain,vvendor);
			    		 String vdetails=rs1.getString("NODE_ID") +":"+ rs1.getString("NODE_NAME") +":"+rs1.getString("CABINETNO")+":"+ rs1.getString("SERIALNUMBER");
			    		 
			    		 stmtinsert1 = con.prepareStatement("insert into  NODE_CABINET_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
				 			 		+  "values ('"+ transid1  +"',sysdate, 'Cabinet Reappeared','Cabinet Reappeared','0','0','0','0','0','0','" + rs1.getString("NODE_ID") +"','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','"+ gnodepk +"','0','0')  ");
						  stmtinsert1.executeUpdate();
						  stmtinsert1.close();
						  
			    		 stmtinsert1 = con.prepareStatement("update  NODE_CABINET set TO_TRANS_SOURCE='CABINET', TO_TRANS_ID='" + transid1 +"',ACTIVE_RECORD ='0',UPDATE_DATE=sysdate where CABINET_ID= '"+ rs1.getString("CABINET_ID") +"' and  ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
			    		 stmtinsert1.executeUpdate();
			    		 stmtinsert1.close();
			    		 
			    		 stmtinsert2 = con.prepareStatement("insert into  NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) select '"+ cabinetid1  +"',SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,'"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','CABINET','"+ transid1 +"','0','Maintenance','1',LINE,ALM_POSITION, TIMESTAMP '"+ crdate +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_CABINET where CABINET_ID='" + rs3.getString("CABINET_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'") ;
							 
			    		 stmtinsert2.executeUpdate();
						  stmtinsert2.close();
		 	   } else {  // if not found we will search in all node in temp if ound same SN in other NOde and move the Board disappear to transfer to other node
		 		   
		 		  stmt4 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);    
				     // GetSN from TEMP_NODE_CABINET  to caekc if transfered to other node
				    String sqlStmt4 = "select  b.CABINET_ID,a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,b.CABINETNO,b.SERIALNUMBER,b.LINE,b.CREATION_DATE from TEMP_NODE_CABINET b ,  TEMP_NODE_ACTIVE a  where  a.ACTIVE_RECORD='1' and  b.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.SERIALNUMBER <> '0' and b.SERIALNUMBER= '" + rs1.getString("SERIALNUMBER") + "'   and b.CREATION_DATE =(select Max(CREATION_DATE) from TEMP_NODE_CABINET where SERIALNUMBER='" + rs1.getString("SERIALNUMBER") + "' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"')";          
				    ResultSet rs4 = stmt4.executeQuery(sqlStmt4);
				    rs4.last();
			 	    totalrec = rs4.getRow(); 
			 	    rs4.beforeFirst();
			 	    String newnode="0";
			 	   if (totalrec > 0 ) {
			 		  while (rs4.next()) {
			 			 newnode=rs4.getString("NODE_ID");
			 		  }
			 		  String transid1=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(21);
			    		 String cabinetid1=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(7);
			    		 
			    		 GetNodedetails(rs1.getString("NODE_ID"),rs1.getString("NODE_NAME"),rs1.getString("NODE_TYPE"),vdomain,vvendor);
			    		 String vdetails=rs1.getString("NODE_ID") +":"+ rs1.getString("NODE_NAME") +":"+rs1.getString("CABINETNO")+":"+ rs1.getString("SERIALNUMBER");
			    		 
			    		 stmtinsert1 = con.prepareStatement("insert into  NODE_CABINET_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
				 			 		+  "values ('"+ transid1  +"',sysdate, 'Cabinet transfer to another node','Cabinet transfer to another node','0','0','0','0','0','0','" + rs1.getString("NODE_ID") +"','" + newnode +"','0','0','"+ vdetails +"','0',sysdate,'0','0','1','"+ gnodepk +"','0','0')  ");
						  stmtinsert1.executeUpdate();
						  stmtinsert1.close();
						  
						  // set disappear active_record to 0				  
						  stmtinsert1 = con.prepareStatement("update  NODE_CABINET set TO_TRANS_SOURCE='CABINET', TO_TRANS_ID='" + transid1 +"',ACTIVE_RECORD ='0',UPDATE_DATE=sysdate where CABINET_ID= '"+ rs1.getString("CABINET_ID") +"' and  ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				    		 stmtinsert1.executeUpdate();
				    		 stmtinsert1.close();
				    		 // create new record showing transfer to other Node
				    		 stmtinsert1 = con.prepareStatement("insert into  NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE)   select '"+ cabinetid1 +"',SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'CABINET','" + transid1 +"','0','Cabinet transfer to another node','1',LINE,ALM_POSITION, TIMESTAMP '" + crdate +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_CABINET where CABINET_ID= '"+ rs1.getString("CABINET_ID") +"' and  ACTIVE_RECORD='0' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
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
      System.err.println("Error in GetReappearingCabinets: "+e);
      e.printStackTrace();
      logger.info("Error in GetReappearingCabinets: "+e);
      
    //insert into AUTO_DISCOVERY_LOGS_DETAILS
		String logsDETails_ID= localgetseqNbr(23);
		logsDETails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETails_ID;
		PreparedStatement insertLogsDEtails_statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
		 		+ "values('"+logsDETails_ID+"',sysdate ,'CheckCabinetMovement','Error in GetReappearingCabinets','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
		 		
		insertLogsDEtails_statement.executeUpdate();
		insertLogsDEtails_statement.close();
		nbOfErrors++;
      }
	    
	   
}
	
	
	private static void GetDisappearingCabinets(String vdomain, String vvendor) throws SQLException  {
		try {
		Statement stmt1 = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		PreparedStatement stmtinsert1=null;
		PreparedStatement stmtinsert2=null;
		int totalrec=0;
		String crdate =null;
		
		stmt1 = con.createStatement();   
	     // Get missing row from NODE_CABINET 
	    String sqlStmt = "(select a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,b.CABINETNO,b.SERIALNUMBER,b.LINE from NODE_CABINET b ,  NODE_ACTIVE a where b.TRANS_TYPE NOT IN ('Node Disappeared', 'Cabinet Disappeared') and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and  b.ACTIVE_RECORD='1' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"') \r\n" + 
	    		"minus  \r\n" + 
	    		"(select a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,b.CABINETNO,b.SERIALNUMBER,b.LINE from TEMP_NODE_CABINET b ,  TEMP_NODE_ACTIVE a where b.TRANS_TYPE NOT IN ('Node Disappeared', 'Cabinet Disappeared') and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and  b.ACTIVE_RECORD='1' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"')";  
	    		        
	    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
	    while (rs1.next()) {
	    	// verify if the rowdata/line not found in temp if yes keep it to check movement if not found chnage to disappaer
	    	
	    	stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);    
		     // Get missing row from NODE_CABINET 
		    String sqlStmt3 = "select a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,b.CABINETNO,b.SERIALNUMBER,b.LINE from TEMP_NODE_ACTIVE a , TEMP_NODE_CABINET b where a.NODE_ID='"+ rs1.getString("NODE_ID") +"' and  a.NODE_NAME='"+ rs1.getString("NODE_NAME") +"' and  a.SITE_ID='"+ rs1.getString("SITE_ID") +"' and  a.CIRCLE_ID='"+ rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and  b.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.CABINETNO='"+ rs1.getString("CABINETNO") +"' and b.SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"' and b.LINE='"+ rs1.getString("LINE") +"' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"'";          
		    ResultSet rs3 = stmt3.executeQuery(sqlStmt3);
		    rs3.last();
	 	    totalrec = rs3.getRow(); 
	 	   if (totalrec == 0 ) {
		    	//get missing rowdata and update active_record to 0
		    	stmt2 = con.createStatement(); 
		    	String sqlStmt2 = "select b.CABINET_ID,b.TRANS_TYPE,a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,b.CABINETNO,b.SERIALNUMBER,b.NODE_PK,b.LINE,b.CREATION_DATE from NODE_ACTIVE a , NODE_CABINET b where a.NODE_ID='"+ rs1.getString("NODE_ID") +"' and  a.NODE_NAME='"+ rs1.getString("NODE_NAME") +"' and  a.SITE_ID='"+ rs1.getString("SITE_ID") +"' and  a.CIRCLE_ID='"+ rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and  b.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.CABINETNO='"+ rs1.getString("CABINETNO") +"' and b.SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"' and b.LINE='"+ rs1.getString("LINE") +"' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"'";  
		    	ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
		    	while (rs2.next()) {
		    		if (StringUtils.equalsIgnoreCase (rs2.getString("TRANS_TYPE"),"Cabinet transfer to another node")) {
		    			// in case of transfer do nothing 
		    		} else {
		    			crdate=rs2.getString("CREATION_DATE");
			    		 String transid1=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(21);
			    		 String cabinetid=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(7);
			    		 String vdetails= rs1.getString("NODE_ID") +":"+ rs1.getString("NODE_NAME") +":"+rs1.getString("CABINETNO")+":"+ rs1.getString("SERIALNUMBER");
			    		 stmtinsert1 = con.prepareStatement("insert into  NODE_CABINET_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
				 			 		+  "values ('"+ transid1  +"',sysdate, 'Disappear','Disappear','0','0','0','0','0','0','" + rs1.getString("NODE_ID") + "','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','" + rs2.getString("NODE_PK") + "','0','0')  ");
						  stmtinsert1.executeUpdate();
						  stmtinsert1.close();
						  
			    		 stmtinsert1 = con.prepareStatement("update  NODE_CABINET set TO_TRANS_SOURCE='CABINET', TO_TRANS_ID='" + transid1 +"', ACTIVE_RECORD ='0',UPDATE_DATE=sysdate where CABINET_ID= '"+ rs2.getString("CABINET_ID") +"' and  ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
			    		 stmtinsert1.executeUpdate();
			    		 stmtinsert1.close();
			    		 
			    		 stmtinsert2 = con.prepareStatement("insert into  NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE)   select '"+ cabinetid +"',SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'CABINET','" + transid1 +"','0','Cabinet Disappeared','1',LINE,ALM_POSITION, TIMESTAMP '" + crdate +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_CABINET where CABINET_ID= '"+ rs2.getString("CABINET_ID") +"' and  ACTIVE_RECORD='0' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				 			 		
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
			System.err.println("Error in GetDisappearingCabinets: "+e);
			e.printStackTrace();
			logger.info("Error in GetDisappearingCabinets: "+e);
			
			//insert into AUTO_DISCOVERY_LOGS_DETAILS
			String logsDETails_ID= localgetseqNbr(23);
			logsDETails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETails_ID;
			PreparedStatement insertLogsDEtails_statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
			 		+ "values('"+logsDETails_ID+"',sysdate ,'CheckCabinetMovement','Error in GetDisappearingCabinets','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
			 		
			insertLogsDEtails_statement.executeUpdate();
			insertLogsDEtails_statement.close();
			nbOfErrors++;
		}
    
	    
	   
	}
	
	private static void GetNewCabinetsfromtemptable(String vnodepk, String vnodeid, String vnodename, String vnodetype,String vsiteid,String vcircleid,String vdomain, String vvendor) throws SQLException  {
		try {
			Statement stmt1 = null;
			Statement stmt2 = null;
			Statement stmt01 = null;
			PreparedStatement stmtinsert1=null;
			PreparedStatement stmtinsert2=null;
			PreparedStatement stmtinsert3=null;
			PreparedStatement stmtinsert4=null;
			int totalrec=0;
			String crdate =null;
			String vstathw="0";
			
			stmt1 = con.createStatement();   
		     // read all CABINETNO,SERIALNUMBER,LINE means all node_id from TEMP_NODE_CABINET table  
	
		    String sqlStmt = "select  b.CABINET_ID,a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,b.CABINETNO,b.SERIALNUMBER,b.LINE from TEMP_NODE_ACTIVE a , TEMP_NODE_CABINET b where a.NODE_PK='" + vnodepk + "' and a.NODE_ID='" + vnodeid + "' and  a.NODE_NAME='" + vnodename + "' and  a.SITE_ID='" +  vsiteid + "' and  a.CIRCLE_ID='" + vcircleid + "' and  a.ACTIVE_RECORD='1' and  b.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"'";          
		    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
		    while (rs1.next()) {
		    	
		    	//compare with final  by CABINETNO,SERIALNUMBER and Line
		    	stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
		    	//String sqlStmt2 = "select  b.CABINET_ID,a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,b.CABINETNO,b.SERIALNUMBER,b.LINE,b.CREATION_DATE from NODE_ACTIVE a , NODE_CABINET b where  a.TRANS_TYPE not in ('Node Disappeared', 'Cabinet Disappeared') and a.NODE_ID='"+ vnodeid +"' and  a.NODE_NAME='"+ vnodename +"' and  a.SITE_ID='"+ rs1.getString("SITE_ID") +"' and  a.CIRCLE_ID='"+ rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and  b.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.CABINETNO='"+ rs1.getString("CABINETNO") +"' and b.SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"' and b.LINE='"+ rs1.getString("LINE") +"' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"'";
		    	String sqlStmt2 = "select  b.CABINET_ID,a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,b.CABINETNO,b.SERIALNUMBER,b.LINE,b.CREATION_DATE from NODE_ACTIVE a , NODE_CABINET b where  a.NODE_ID='"+ vnodeid +"' and  a.NODE_NAME='"+ vnodename +"' and  a.SITE_ID='"+ rs1.getString("SITE_ID") +"' and  a.CIRCLE_ID='"+ rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and  b.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.CABINETNO='"+ rs1.getString("CABINETNO") +"'  and b.LINE='"+ rs1.getString("LINE") +"' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"'";

		    	ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
			    rs2.last();
		 	    totalrec = rs2.getRow(); 
		 	    rs2.beforeFirst();
		 	   if (totalrec >0 ) {
		 		  while (rs2.next()) {
		 			  crdate=rs2.getString("CREATION_DATE");
		 			     // if we have same line value we will update updatedate field only if not we will set active record to 0 and create new row 
		 				
		 			 if (StringUtils.equalsIgnoreCase (rs1.getString("CABINETNO"),rs2.getString("CABINETNO")) && (StringUtils.equalsIgnoreCase (rs1.getString("SERIALNUMBER"),rs2.getString("SERIALNUMBER"))) && (StringUtils.equalsIgnoreCase (rs1.getString("LINE"),rs2.getString("LINE"))) ) {
		 				// record found in final update LAST_MODIFIED_DATE
			 			 stmtinsert1 = con.prepareStatement("update  NODE_CABINET set UPDATE_DATE =sysdate where CABINET_ID= '"+ rs2.getString("CABINET_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
			    		 stmtinsert1.executeUpdate();
			    		 stmtinsert1.close();
		 			 } else {
		 				 GetNodedetails(vnodeid,vnodename,vnodetype,vdomain,vvendor);
		 				  String transid1=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(21);
						  String cabinetid1=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(7);
		 				// record  in final does not have same data move active_record to 0 and create new row
			 			 stmtinsert1 = con.prepareStatement("update  NODE_CABINET set TO_TRANS_SOURCE='CABINET', TO_TRANS_ID='" + transid1 +"' ,ACTIVE_RECORD ='0' where CABINET_ID= '"+ rs2.getString("CABINET_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' ");
			    		 stmtinsert1.executeUpdate();
			    		 stmtinsert1.close();
			    		 
			    		 String vdetails1=rs1.getString("CABINETNO")+":"+ rs1.getString("SERIALNUMBER")+":"+ rs1.getString("LINE");
			    		 
						  stmtinsert4 = con.prepareStatement("insert into  NODE_CABINET_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
				 			 		+  "values ('"+ transid1  +"',sysdate, 'CABINET SN Replacement','CABINET SN Replacement','0','0','0','0','0','0','" + rs2.getString("NODE_ID") + "','0','0','0','"+ vdetails1 +"','0',sysdate,'0','"+rs2.getString("SERIALNUMBER")+"','1','"+ gnodepk +"','0','"+rs1.getString("SERIALNUMBER")+"')  ");
						  stmtinsert4.executeUpdate();
						  stmtinsert4.close();
					  
						  stmtinsert2 = con.prepareStatement("insert into  NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) select '"+ cabinetid1  +"',SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,'"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','CABINET','"+ transid1 +"','0','CABINET SN Replacement','1',LINE,ALM_POSITION, TIMESTAMP '"+ crdate +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_CABINET where CABINET_ID='" + rs1.getString("CABINET_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'") ;
						  stmtinsert2.executeUpdate();
						  stmtinsert2.close();
		 			   }// end else
		 			  
					  
				    }
		 	   }else {
		 		   // record not found in final add it 
		 		  GetNodedetails(vnodeid,vnodename,vnodetype,vdomain,vvendor);
		 		  String transid=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(21);
				  String cabinetid=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(7);
				  String vdetails=rs1.getString("CABINETNO")+":"+ rs1.getString("SERIALNUMBER")+":"+ rs1.getString("LINE");
				  
				 
				  
				if (StringUtils.equalsIgnoreCase (rs1.getString("SERIALNUMBER"),"0") ){
					
					stmtinsert2 = con.prepareStatement("insert into  NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) select '"+ cabinetid  +"',SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,'"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','CABINET','"+ transid +"','0','NEW CABINET','1',LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_CABINET where CABINET_ID='" + rs1.getString("CABINET_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'") ;
			 		 stmtinsert2.executeUpdate();
			 		 stmtinsert2.close();
					
				} else {
				
						stmt01 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
					    String sqlStmt01 = "select CABINET_ID,SERIALNUMBER,NODE_PK,CREATION_DATE from NODE_CABINET where SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"'  and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
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
				 	  
				 	 stmtinsert3 = con.prepareStatement("insert into  NODE_CABINET_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
			 			 		+  "values ('"+ transid  +"',sysdate, '"+vstathw+"','"+vstathw+"','0','0','0','0','0','0','" + rs1.getString("NODE_ID") + "','0','0','0','"+ vdetails +"','0',sysdate,'0','"+rs1.getString("SERIALNUMBER")+"','1','"+ gnodepk +"','0','"+rs1.getString("SERIALNUMBER")+"')  ");
				stmtinsert3.executeUpdate();
				stmtinsert3.close();
				 	  
				 	 stmtinsert2 = con.prepareStatement("insert into  NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) select '"+ cabinetid  +"',SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,'"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','CABINET','"+ transid +"','0','" + vstathw + "','1',LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_CABINET where CABINET_ID='" + rs1.getString("CABINET_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'") ;
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
	      System.err.println("Error in GetNewCabinetsfromtemptable: "+e);
	      e.printStackTrace();
	      logger.info("Error in GetNewCabinetsfromtemptable: "+e);
	      
	    //insert into AUTO_DISCOVERY_LOGS_DETAILS
			String logsDETails_ID= localgetseqNbr(23);
			logsDETails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETails_ID;
			PreparedStatement insertLogsDEtails_statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
			 		+ "values('"+logsDETails_ID+"',sysdate ,'CheckCabinetMovement','Error in GetNewCabinetsfromtemptable','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
			 		
			insertLogsDEtails_statement.executeUpdate();
			insertLogsDEtails_statement.close();
			nbOfErrors++;
	     }
	   
	}

	
	private static void GetNodedetails(String vnodeid, String vnodename, String vnodetype,String vdomain, String vvendor) throws SQLException {
	    try {
		gnodepk = "0" ;
	    gnodepkattr = "0" ;
	    gnodefilename = "0" ;
	    gnodeline=0;
		Statement stmttype = null;
		String query2 = "select b.NODE_PK,b.NODE_ATTR_PK,b.FILENAME from NODE_ACTIVE a, NODE_CABINET b where a.NODE_ID='"+ vnodeid +"' and a.NODE_NAME='"+ vnodename +"' and a.NODE_TYPE='"+ vnodetype +"' and b.NODE_PK=a.NODE_PK and b.ACTIVE_RECORD='1' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"' ";      
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
		      System.err.println("Error in GetNodedetails CheckCabinet: "+e);
		      e.printStackTrace();
		      logger.info("Error in GetNodedetails CheckCabinet: "+e);
		      
		    //insert into AUTO_DISCOVERY_LOGS_DETAILS
				String logsDETails_ID= localgetseqNbr(23);
				logsDETails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDETails_ID;
				PreparedStatement insertLogsDEtails_statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDETails_ID+"',sysdate ,'CheckCabinetMovement','Error in GetNodedetails CheckCabinet','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
				insertLogsDEtails_statement.executeUpdate();
				insertLogsDEtails_statement.close();
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
			break;
			
		case 22:
			SeqName = "AUTO_DISCOVERY_LOGS_SEQ";
			break;
			
		case 23:
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
