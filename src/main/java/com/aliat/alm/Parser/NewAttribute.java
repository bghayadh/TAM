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

public class NewAttribute {
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
					DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        	LocalDateTime now1 = LocalDateTime.now();
		        	Gyear=dtf1.format(now1).substring(0,4);
		        	String lofilename="NewAttribute-"+dtf1.format(now1)+".log";
				
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
						 			  
						 			// Check if we have new disappeared Attribute 
									   GetAttributeDisappearing(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
						 			  
						 			// Check if we have new Reappeared Attribute 
										GetAttributeReappearing(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
										
										
										
										
											System.out.println("Check if we have new Attribute added to existing nodes for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
											logger.info("Check if we have new Attribute added to existing nodes for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
											
											//insert into AUTO_DISCOVERY_LOGS_DETAILS
											String logsDetailsid= localgetseqNbr(23);
											logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
											PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
						 					 		+ "values('"+logsDetailsid+"',sysdate ,'NewAttribute','Check if we have new Attribute added to existing nodes for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
						 					 		
											insertLogsDetailsstmt.executeUpdate();
											insertLogsDetailsstmt.close();
											
										    // Check if we have new Attribute added to existing nodes
											stmt0 = con.createStatement();   
										    // Get distinct filename with attribute table name from temp_node_attribute
											String sqlStmt = "select distinct a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,b.NODE_PK from TEMP_NODE_ACTIVE a ,TEMP_NODE_ACTIVE_ATTRIBUTE b where a.NODE_PK=b.NODE_PK and b.ACTIVE_RECORD='1' and a.DOMAIN='" + rsinit2.getString("DOMAIN") +"' and a.VENDOR='" + rsinit2.getString("VENDOR") +"'";          
											ResultSet rs0 = stmt0.executeQuery(sqlStmt);
										    while (rs0.next()) {
											checkattributesofALLnodes(rs0.getString("NODE_ID"),rs0.getString("NODE_NAME"),rs0.getString("SITE_ID"),rs0.getString("CIRCLE_ID"),rs0.getString("NODE_PK"),rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
										    }
										    rs0.close();
										    stmt0.close(); 
										    
										    
										  //insert into AUTO_DISCOVERY_LOGS_DETAILS
											String logsDetailsId= localgetseqNbr(23);
											logsDetailsId=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsId;
											PreparedStatement insertLogsDetails_stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
						 					 		+ "values('"+logsDetailsId+"',sysdate ,'NewAttribute','Number Of Errors','','Number Of Errors','','"+nbOfErrors+"','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
						 					 		
											insertLogsDetails_stmt.executeUpdate();
											insertLogsDetails_stmt.close();
										    
										  //insert into AUTO_DISCOVERY_LOGS
										 	  PreparedStatement insertLogsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS (LOGS_ID,START_TIME,ACTIVITY_NAME,VENDOR,DOMAIN,STOP_TIME) "
												 		+ "values('"+logsid+"', ? ,'NewAttribute','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"',?) ");
												 		
										 	insertLogsstmt.setTimestamp(1, startTime); 
										 	insertLogsstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
											insertLogsstmt.executeUpdate();
											insertLogsstmt.close();
											
						 		  }
					 	   }
					 	  rsinit2.close();
					 	 stmtinit2.close();
					 	 
					 	
						
							
							System.out.println("Check Attributes COMPLETED");
							logger.info("Check Attributes COMPLETED");
							
							
							 //insert into AUTO_DISCOVERY_LOGS_DETAILS
							String logsDetailsid= localgetseqNbr(23);
							logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
							PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
							 		+ "values('"+logsDetailsid+"',sysdate ,'NewAttribute','Check Attributes COMPLETED','','','Completed','','','','"+logsid+"') ");
							 		
							insertLogsDetailsstmt.executeUpdate();
							insertLogsDetailsstmt.close();
		
							con.close();
			}
			catch(Exception e){
			      System.err.println(e);
			      e.printStackTrace();
			      logger.info("Error : "+e);
			   }
				
 }
		
		private static void GetAttributeReappearing(String vdomain,String vvendor) throws SQLException  {
			try {
					Statement stmt1 = null;
					Statement stmt2 = null;
					Statement stmt3 = null;
					PreparedStatement stmtinsert1=null;
					PreparedStatement stmtinsert2=null;
					int totalrec=0;
					
					stmt1 = con.createStatement();   
				     // Get missing row from NODE_ACTIVE_ATTRIBUTE 
				    String sqlStmt = "select a.NODE_ATTR_PK,a.ATTRIBUTE,a.ATTRIBUTE_TABLE,b.NODE_TYPE,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,a.LINE from NODE_ACTIVE_ATTRIBUTE a ,  NODE_ACTIVE b where a.TRANS_TYPE = 'Disappear' and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";          
				    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
				    while (rs1.next()) {
				    	
				    	// verify if the rowdata/line not found in temp if yes keep it to check movement if not found chnage to disappaer
				    	
				    	stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);    
					     // Get missing row from NODE_ACTIVE_ATTRIBUTE 
					    String sqlStmt3 = "select a.NODE_ATTR_PK,a.ATTRIBUTE,a.ATTRIBUTE_TABLE,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,a.LINE from TEMP_NODE_ACTIVE_ATTRIBUTE a ,  TEMP_NODE_ACTIVE b where b.NODE_ID='" + rs1.getString("NODE_ID") +"' and  b.NODE_NAME='" + rs1.getString("NODE_NAME") +"' and  b.SITE_ID='" + rs1.getString("SITE_ID") +"' and  b.CIRCLE_ID='" + rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and a.ATTRIBUTE='" + rs1.getString("ATTRIBUTE") +"' and a.ATTRIBUTE_TABLE='" + rs1.getString("ATTRIBUTE_TABLE") +"' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";          
					    ResultSet rs3 = stmt3.executeQuery(sqlStmt3);
					    rs3.last();
				 	    totalrec = rs3.getRow(); 
				 	   if (totalrec > 0 ) {
			
					    		 String transid1=Gyear+"_"+ "ATTRIBUTE"+"_"+localgetseqNbr(21);
					    		 String attrid1=Gyear+"_"+ "ATTRIBUTE"+"_"+localgetseqNbr(1);
					    		 GetNodedetails(rs1.getString("NODE_ID"),rs1.getString("NODE_NAME"),rs1.getString("NODE_TYPE"),vdomain,vvendor);
					    		 String vdetails=rs1.getString("ATTRIBUTE")+":"+ rs1.getString("ATTRIBUTE_TABLE") +":"+ rs1.getString("NODE_NAME");
					    		 stmtinsert1 = con.prepareStatement("insert into  NODE_ACTIVE_ATTR_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
						 			 		+  "values ('"+ transid1  +"',sysdate, 'Disappear','Disappear','"+ rs1.getString("NODE_ATTR_PK") +"','0','0','0','0','0','0','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','0','0','0')  ");
								  stmtinsert1.executeUpdate();
								  stmtinsert1.close();
								  
					    		 stmtinsert1 = con.prepareStatement("update  NODE_ACTIVE_ATTRIBUTE set TO_TRANS_SOURCE='ATTRIBUTE', TO_TRANS_ID='" + transid1 +"',TRANS_TYPE='Reappear' ,ACTIVE_RECORD ='0',UPDATE_DATE=sysdate where NODE_ATTR_PK= '"+ rs1.getString("NODE_ATTR_PK") +"' and  ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
					    		 stmtinsert1.executeUpdate();
					    		 stmtinsert1.close();
					    		 
					    		 stmtinsert2 = con.prepareStatement("insert into  NODE_ACTIVE_ATTRIBUTE (NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
						 			 		+  "values ('"+ attrid1  +"','" + rs1.getString("ATTRIBUTE") + "', '" + rs1.getString("ATTRIBUTE_TABLE") + "','"+ gnodepk +"','" + gnodepkattr + "',sysdate,'"+ gnodefilename +"','0','ATTRIBUTE','"+ transid1 +"','0','Maintenance','1','"+ rs1.getString("LINE") +"','" + vdomain +"','" + vvendor +"','0')  ");
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
		      System.err.println("Error in GetAttributeReappearing: "+e);
		      e.printStackTrace();
		      logger.info("Error in GetAttributeReappearing: "+e);
		      
		    //insert into AUTO_DISCOVERY_LOGS_DETAILS
				String logsDetailsid= localgetseqNbr(23);
				logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
				PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsid+"',sysdate ,'NewAttribute','Error in GetAttributeReappearing','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
				insertLogsDetailsstmt.executeUpdate();
				insertLogsDetailsstmt.close();
				nbOfErrors++;
		   }
		   
		}
		
		
		
		private static void GetAttributeDisappearing(String vdomain,String vvendor) throws SQLException  {
			try {
					Statement stmt1 = null;
					Statement stmt2 = null;
					Statement stmt3 = null;
					PreparedStatement stmtinsert1=null;
					PreparedStatement stmtinsert2=null;
					int totalrec=0;
					
					stmt1 = con.createStatement();   
				     // Get missing row from NODE_ACTIVE_ATTRIBUTE 
				    String sqlStmt = "(select a.ATTRIBUTE,a.ATTRIBUTE_TABLE,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID from NODE_ACTIVE_ATTRIBUTE a ,  NODE_ACTIVE b where a.TRANS_TYPE <> 'Node Disappeared' and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"') \r\n" + 
				    		"minus \r\n" + 
				    		"(select a.ATTRIBUTE,a.ATTRIBUTE_TABLE,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID from TEMP_NODE_ACTIVE_ATTRIBUTE a ,  TEMP_NODE_ACTIVE b where a.TRANS_TYPE <> 'Node Disappeared' and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"') ";
				    		        
				    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
				    while (rs1.next()) {
				    	
				    	// verify if the rowdata/line not found in temp if yes keep it to check movement if not found chnage to disappaer
				    	
				    	stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);    
					     // Get missing row from NODE_ACTIVE_ATTRIBUTE 
					    String sqlStmt3 = "select a.ATTRIBUTE,a.ATTRIBUTE_TABLE,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,a.LINE from TEMP_NODE_ACTIVE_ATTRIBUTE a ,  TEMP_NODE_ACTIVE b where b.NODE_ID='" + rs1.getString("NODE_ID") +"' and  b.NODE_NAME='" + rs1.getString("NODE_NAME") +"' and  b.SITE_ID='" + rs1.getString("SITE_ID") +"' and  b.CIRCLE_ID='" + rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and a.ATTRIBUTE_TABLE='" + rs1.getString("ATTRIBUTE_TABLE") +"' and a.ATTRIBUTE='" + rs1.getString("ATTRIBUTE") +"' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";          
					    ResultSet rs3 = stmt3.executeQuery(sqlStmt3);
					    rs3.last();
				 	    totalrec = rs3.getRow(); 
				 	   if (totalrec == 0 ) {
					    	//get missing rowdata and update active_record to 0
					    	stmt2 = con.createStatement(); 
					    	String sqlStmt2 = "select a.NODE_ATTR_PK,a.ATTRIBUTE,a.ATTRIBUTE_TABLE,b.NODE_NAME,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,a.LINE from NODE_ACTIVE_ATTRIBUTE a ,  NODE_ACTIVE b where a.TRANS_TYPE <> 'Node Disappeared' and a.NODE_PK=b.NODE_PK and a.ATTRIBUTE= '" + rs1.getString("ATTRIBUTE") +"' and a.ATTRIBUTE_TABLE='" + rs1.getString("ATTRIBUTE_TABLE") +"' and b.NODE_NAME='" + rs1.getString("NODE_NAME") +"' and b.NODE_ID='" + rs1.getString("NODE_ID") +"' and b.SITE_ID='" + rs1.getString("SITE_ID") +"' and b.CIRCLE_ID='" + rs1.getString("CIRCLE_ID") +"' and  a.ACTIVE_RECORD='1' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";  
					    	ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
					    	while (rs2.next()) {
					    		 String transid1=Gyear+"_"+ "ATTRIBUTE"+"_"+localgetseqNbr(21);
					    		 String attrid=Gyear+"_"+ "ATTRIBUTE"+"_"+localgetseqNbr(1);
					    		 String vdetails=rs1.getString("ATTRIBUTE")+":"+ rs1.getString("ATTRIBUTE_TABLE") +":"+ rs1.getString("NODE_NAME");
					    		 stmtinsert1 = con.prepareStatement("insert into  NODE_ACTIVE_ATTR_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
						 			 		+  "values ('"+ transid1  +"',sysdate, 'Disappear','Disappear','"+ rs2.getString("NODE_ATTR_PK") +"','0','0','0','0','0','0','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','0','0','0')  ");
								  stmtinsert1.executeUpdate();
								  stmtinsert1.close();
								  
					    		 stmtinsert1 = con.prepareStatement("update  NODE_ACTIVE_ATTRIBUTE set TO_TRANS_SOURCE='ATTRIBUTE', TO_TRANS_ID='" + transid1 +"', ACTIVE_RECORD ='0',UPDATE_DATE=sysdate where NODE_ATTR_PK= '"+ rs2.getString("NODE_ATTR_PK") +"' and  ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
					    		 stmtinsert1.executeUpdate();
					    		 stmtinsert1.close();
					    		 
					    		 stmtinsert1 = con.prepareStatement("update  " + rs1.getString("ATTRIBUTE_TABLE") +" set TO_TRANS_SOURCE='ATTRIBUTE', TO_TRANS_ID='" + transid1 +"', ACTIVE_RECORD ='0',UPDATE_DATE=sysdate where NODE_ATTR_PK= '"+ rs2.getString("NODE_ATTR_PK") +"' and  ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
					    		 stmtinsert1.executeUpdate();
					    		 stmtinsert1.close();
					    		 
					    		 
					    		 stmtinsert2 = con.prepareStatement("insert into  NODE_ACTIVE_ATTRIBUTE (NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE)   select '"+ attrid +"',ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'ATTRIBUTE','" + transid1 +"','0','Disappear','1',LINE,DOMAIN,VENDOR,'0' from NODE_ACTIVE_ATTRIBUTE where NODE_ATTR_PK= '"+ rs2.getString("NODE_ATTR_PK") +"' and  ACTIVE_RECORD='0' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
						 			 		
								  stmtinsert2.executeUpdate();
								  stmtinsert2.close();
					    		 
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
			      System.err.println("Error in GetAttributeDisappearing: "+e);
			      e.printStackTrace();
			     logger.info("Error in GetAttributeDisappearing: "+e);
			     
			   //insert into AUTO_DISCOVERY_LOGS_DETAILS
					String logsDetailsid= localgetseqNbr(23);
					logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
					PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDetailsid+"',sysdate ,'NewAttribute','Error in GetAttributeDisappearing','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
					 		
					insertLogsDetailsstmt.executeUpdate();
					insertLogsDetailsstmt.close();
					nbOfErrors++;
			   }
		   
		}

		private static void checkattributesofALLnodes(String vnodeid,String vnodename,String vsiteid,String vcircleid,String vnodepk,String vdomain,String vvendor) throws SQLException  {
			Statement stmt1 = null;
			Statement stmt2 = null;
			int totalrec=0;
			int totalline=0;
			stmt1 = con.createStatement();   
		    // Get distinct filename with attribute table name from temp_node_attribute
			String sqlStmt = "select a.NODE_ATTR_PK,a.NODE_PK, a.FILENAME,a.ATTRIBUTE_TABLE,b.NODE_ID,b.NODE_NAME,b.SITE_ID,b.CIRCLE_ID from TEMP_NODE_ACTIVE_ATTRIBUTE a,TEMP_NODE_ACTIVE b where a.NODE_PK=b.NODE_PK and b.NODE_ID ='" + vnodeid + "' and b.NODE_NAME ='" + vnodename + "' and b.SITE_ID ='" + vsiteid + "' and b.CIRCLE_ID ='" + vcircleid + "' and a.ACTIVE_RECORD='1' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'"; 
			ResultSet rs1 = stmt1.executeQuery(sqlStmt);
		    while (rs1.next()) {
		    	try {
		    	totalrec=0;
		    	// validate if all attribute found in temp_node attribute and  in final node_atribute tables
		    	String sqlStmt2 = "select a.ATTRIBUTE_TABLE,b.SITE_ID,b.NODE_NAME,b.SITE_ID,b.CIRCLE_ID from NODE_ACTIVE_ATTRIBUTE a ,NODE_ACTIVE b where a.NODE_PK=b.NODE_PK and a.ACTIVE_RECORD='1' and a.ATTRIBUTE_TABLE ='" + rs1.getString("ATTRIBUTE_TABLE") + "' and b.NODE_ID ='" + rs1.getString("NODE_ID") + "' and b.NODE_NAME ='" + rs1.getString("NODE_NAME") + "' and b.SITE_ID ='" + rs1.getString("SITE_ID") + "' and b.CIRCLE_ID ='" + rs1.getString("CIRCLE_ID") + "' and a.UPDATE_DATE =(select MAX(a.UPDATE_DATE) as UPDATE_DATE from NODE_ACTIVE_ATTRIBUTE a,NODE_ACTIVE b  where a.ATTRIBUTE_TABLE ='" + rs1.getString("ATTRIBUTE_TABLE") + "' and a.NODE_PK =b.NODE_PK and a.ACTIVE_RECORD='1' and b.NODE_ID ='" + rs1.getString("NODE_ID") + "' and b.NODE_NAME ='" + rs1.getString("NODE_NAME") + "' and b.SITE_ID ='" + rs1.getString("SITE_ID") + "' and b.CIRCLE_ID ='" + rs1.getString("CIRCLE_ID") + "' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"')";
		    	stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		    	ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
		    	rs2.last();
        	    totalrec = rs2.getRow(); 
        	    if (totalrec > 0) {
        	    	// attribute found in both tables no needed action
        	    }else  { // a new attribute added to node not added before we need to add attribute and missing data in appropriate attribute table
        	    	System.out.println("missing  " + rs1.getString("ATTRIBUTE_TABLE") + "  in file " + rs1.getString("NODE_NAME") );
        	    	logger.info("missing  " + rs1.getString("ATTRIBUTE_TABLE") + "  in file " + rs1.getString("NODE_NAME"));
        	    	
        	    	//Get count line to add missing attirbute with the corresponding line counter 
        	    	String sqlStmtc = "select a.ATTRIBUTE_TABLE,b.SITE_ID,b.NODE_NAME,b.SITE_ID,b.CIRCLE_ID from TEMP_NODE_ACTIVE_ATTRIBUTE a ,TEMP_NODE_ACTIVE b where a.NODE_PK=b.NODE_PK and a.ACTIVE_RECORD='1' and b.NODE_ID ='"+ rs1.getString("NODE_ID") +"' and b.NODE_NAME ='"+ rs1.getString("NODE_NAME") +"' and b.SITE_ID ='"+ rs1.getString("SITE_ID") +"' and b.CIRCLE_ID ='"+ rs1.getString("CIRCLE_ID") +"' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";
        	    	Statement stmtc = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    		    	ResultSet rsc = stmtc.executeQuery(sqlStmtc);
    		    	rsc.last();
            	    totalline = rsc.getRow(); 
            	    totalline=totalline-1;
            	    rsc.close();
            	    stmtc.close(); 
            	    
            	    
        	    	//Add to node_attr_transactions new Row
        	    	addAttributeToTransactions(vnodeid,vnodename,vsiteid,vcircleid,rs1.getString("ATTRIBUTE_TABLE"),"NODE_ACTIVE_ATTR_TRANSACTIONS",rs1.getString("NODE_ATTR_PK"),totalline,vdomain,vvendor);
        	    }
        	    	
		    	rs2.close();
			    stmt2.close(); // close rs2
		    }
			    catch(Exception e)  
				{  
				logger.info("error at checkattributesofALLnodes is :"+ e.toString());
				System.out.println("error at checkattributesofALLnodes is :"+ e.toString()); 
				
				//insert into AUTO_DISCOVERY_LOGS_DETAILS
				String logsDetailsid= localgetseqNbr(23);
				logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
				PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsid+"',sysdate ,'NewAttribute','error at checkattributesofALLnodes ','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
				insertLogsDetailsstmt.executeUpdate();
				insertLogsDetailsstmt.close();
				nbOfErrors++;
				} 
		    	
		    }
		    rs1.close();   // close rs1
		    stmt1.close();
		    logger.info("Check Attributes Of"+ vnodename +" Completed" );
		}
		
		private static void addAttributeToTransactions(String vnodeid,String vnodename,String vsiteid,String vcircleid,String attribut,String Tblname,String vnodeattributpk,int vline,String vdomain,String vvendor) throws SQLException  {
		 	PreparedStatement stmtinsert1=null;
		 	PreparedStatement stmtinsert2=null;
		 	Statement stmt1 = null;
		 	String vnodepk=null;
		 	String vupddate=null;
			String addatribut ;
			
			try {
		 	
		 	addatribut = "Adding  " +attribut;
		 	
		 	stmt1 = con.createStatement();   
		 	String sqlStmt = "select distinct a.UPDATE_DATE,a.NODE_PK from NODE_ACTIVE_ATTRIBUTE a,NODE_ACTIVE b  where a.NODE_PK=b.NODE_PK and a.ACTIVE_RECORD='1' and b.NODE_ID ='"+ vnodeid +"' and b.NODE_NAME ='"+ vnodename +"' and b.SITE_ID ='"+ vsiteid +"' and b.CIRCLE_ID ='"+ vcircleid +"' and  a.UPDATE_DATE =(select MAX(a.UPDATE_DATE) as UPDATE_DATE from NODE_ACTIVE_ATTRIBUTE a,NODE_ACTIVE b where a.NODE_PK=b.NODE_PK and a.ACTIVE_RECORD='1' and b.NODE_ID ='"+ vnodeid +"' and b.NODE_NAME ='"+ vnodename +"' and b.SITE_ID ='"+ vsiteid +"' and b.CIRCLE_ID ='"+ vcircleid +"'  and rownum=1 and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"')";          
		    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
		    while (rs1.next()) { 
		    vnodepk=rs1.getString("NODE_PK");
		    vupddate=rs1.getString("UPDATE_DATE");
		    System.out.println(vnodepk);
		    System.out.println(vupddate);
		    }
		    rs1.close();
		    stmt1.close();
  
		    if (StringUtils.equalsIgnoreCase (vupddate,null)) {
		    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-DD");
			LocalDateTime now = LocalDateTime.now();
			 vupddate =now.toString();
			 System.out.println(vupddate);
		    }
		    
		    if (StringUtils.equalsIgnoreCase (vnodepk,null)) { 
		        // if no node_pk found we cannot add any records
		    }else
		    {
		    	// add new record in transcation mentioning to add missing attribute
		    	logger.info("add new record in transcation"+  addatribut );
		    	
		    	String minval="0";
			 	minval= localgetseqNbr(21);
			 	minval=Gyear+"_"+ "ATTRIBUTE"+"_"+minval;
			 	stmtinsert1 = con.prepareStatement("insert into "+ Tblname +" (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER \r\n" + ")"
			 			+ "values ('"+ minval +"',TIMESTAMP '"+ vupddate +"','New Attribute','"+ addatribut +"','0','0','0','0','0','0','0','0','0','0','0','0',sysdate,'0','0','0','"+ vnodepk +"','"+ vnodeattributpk +"','0')"); 
			 	stmtinsert1.executeUpdate();
			 	stmtinsert1.close();
			 	
			 	//adding row in node attribute table
			 	logger.info("adding row in node attribute table having primary Key "+  vnodeattributpk );
			 	
			 	stmtinsert1 = con.prepareStatement("insert into  NODE_ACTIVE_ATTRIBUTE  (select NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE, '"+ vnodepk +"' as NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,'ATTRIBUTE','"+ minval +"' ,'0','New Attribute' as TRANS_TYPE,ACTIVE_RECORD,'" + vline + "',DOMAIN,VENDOR,'0' from TEMP_NODE_ACTIVE_ATTRIBUTE where  NODE_ATTR_PK ='" + vnodeattributpk +"' and ATTRIBUTE_TABLE='"+ attribut +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' )"); 
			 	stmtinsert1.executeUpdate();
			 	stmtinsert1.close();
			 	
			 	
			 	// add all rows from temp_node_missing table into final node_missing table ex(from temp_node_hostver into node_hostver)
			 	logger.info("add all rows from missing table into final node table ");
			 	
			 	String vartable="TEMP_"+attribut;
			 	stmtinsert1 = con.prepareStatement("insert into  "+ attribut +"  (select * from  "+ vartable +" where  NODE_ATTR_PK ='" + vnodeattributpk +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' )"); 
			 	stmtinsert1.executeUpdate();
			 	stmtinsert1.close();
			 	
			 	//update fields trans_source, transid in final node_missing table 
			  	stmtinsert1 = con.prepareStatement("update  "+ attribut +"  set NODE_PK= '"+ vnodepk + "', FROM_TRANS_SOURCE='ATTRIBUTE',  FROM_TRANS_ID = '" + minval +"' ,TRANS_TYPE = 'New Attribute',UPDATE_DATE=sysdate where  NODE_ATTR_PK ='" + vnodeattributpk +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
			 	stmtinsert1.executeUpdate();
			 	stmtinsert1.close();
			 	
			 	/*
			 	// Add to node missing transcations and update filed node_pk and transid in final node_missing table to fit data in node-attribut
			 	
			 	logger.info("add rows in transaction missing table ");
			 	//Add to node missing transcation
			 	String vartranstable =attribut +"_TRANSACTIONS";
			 	stmtinsert1 = con.prepareStatement("insert into  "+ vartranstable +"  (select 'Gyear_'||NODETRANSACTION_SEQ.nextval,UPDATE_DATE,'New Attribute','New Attribute','0','0','0','0','0','0','0','0','0','0','0' ,'0',sysdate,'0','0','0','"+ vnodepk +"','"+ vnodeattributpk +"','0' from "+vartable+" where  NODE_ATTR_PK ='" + vnodeattributpk +"')  "); 
			 	stmtinsert1.executeUpdate();
			 	stmtinsert1.close();
			 	
			 	//update filed node_pk and transid in final node_missing table to fit data in node-attribut
			  	stmtinsert1 = con.prepareStatement("update  "+ attribut +"  set NODE_PK ='" + vnodepk +"' , TRANS_ID = '" + minval +"' ,TRANS_TYPE = 'New Attribute' where  NODE_ATTR_PK ='" + vnodeattributpk +"' "); 
			 	stmtinsert1.executeUpdate();
			 	stmtinsert1.close();
			 	*/
		    }
			}
		    catch(Exception e)  
			{  
			logger.info("error at addAttributeToTransactions is :"+ e.toString());
			System.out.println("error at addAttributeToTransactions is :"+ e.toString()); 
			
			//insert into AUTO_DISCOVERY_LOGS_DETAILS
			String logsDetails_Id= localgetseqNbr(23);
			logsDetails_Id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_Id;
			PreparedStatement insertLogsDetails_Stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
			 		+ "values('"+logsDetails_Id+"',sysdate ,'NewAttribute','error at addAttributeToTransactions ','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
			 		
			insertLogsDetails_Stmt.executeUpdate();
			insertLogsDetails_Stmt.close();
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
			String query2 = "select b.NODE_PK,b.NODE_ATTR_PK,b.FILENAME from NODE_ACTIVE a, NODE_ACTIVE_ATTRIBUTE b where a.NODE_ID='"+ vnodeid +"' and a.NODE_NAME='"+ vnodename +"' and a.NODE_TYPE='"+ vnodetype +"' and b.NODE_PK=a.NODE_PK and b.ACTIVE_RECORD='1' and a.DOMAIN='" + vdomain +"' and a.VENDOR='" + vvendor +"'";      
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
					String logsDetails_Id= localgetseqNbr(23);
					logsDetails_Id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_Id;
					PreparedStatement insertLogsDetails_Stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDetails_Id+"',sysdate ,'NewAttribute','Error in GetNodedetails CheckHostver','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
					 		
					insertLogsDetails_Stmt.executeUpdate();
					insertLogsDetails_Stmt.close();
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
