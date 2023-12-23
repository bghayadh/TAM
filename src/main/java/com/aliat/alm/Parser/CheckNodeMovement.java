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

public class CheckNodeMovement {
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
						 			  
						 			 logger.info("1-Check if we have New Disappearing HW for  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
										System.out.println("1-Check if we have New Disappearing HW for  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
										
										//insert into AUTO_DISCOVERY_LOGS_DETAILS
										String logsDetailsid= localgetseqNbr(23);
										logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
										PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 					 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','1-Check if we have New Disappearing HW for  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
					 					 		
										insertLogsDetailsstmt.executeUpdate();
										insertLogsDetailsstmt.close();
										
										GetDisappearingNodes(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
										
										logger.info("1-Disappearing Disappearing HW Check COMPLETED for  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									
										//insert into AUTO_DISCOVERY_LOGS_DETAILS
										String logsDetails_idd= localgetseqNbr(23);
										logsDetails_idd=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_idd;
										PreparedStatement insertLogsDetails_Statemenmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 					 		+ "values('"+logsDetails_idd+"',sysdate ,'CheckNodeMovement','1-Disappearing Disappearing HW Check COMPLETED for  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
					 					 		
										insertLogsDetails_Statemenmt.executeUpdate();
										insertLogsDetails_Statemenmt.close();
										
									///////////////////////////////////////////////////////////////////////
																			
									logger.info("2-Check if we have reappearied HW after Disappear for " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									System.out.println("2-Check if we have reappearied HW after Disappear for " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									
									
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logsDetailsId= localgetseqNbr(23);
									logsDetailsId=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsId;
									PreparedStatement insertLogsDetailsStmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logsDetailsId+"',sysdate ,'CheckNodeMovement','2-Check if we have reappearied HW after Disappear for " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
				 					 		
									insertLogsDetailsStmt.executeUpdate();
									insertLogsDetailsStmt.close();
									
									// check all SN reappeared (SN maintenace or SN transferred) and then reappeared nodes (Maintenance or trnasferred) after last disappearing 
									
									GetReappearingSN(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
									GetReappearingNodes(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
									
									logger.info("2-Reappearied HW after Disappear COMPLETED for  " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									System.out.println("2-Reappearied HW after Disappear COMPLETED  for  " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									
									
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logsDetailsID= localgetseqNbr(23);
									logsDetailsID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsID;
									PreparedStatement insertLogsDetails_stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logsDetailsID+"',sysdate ,'CheckNodeMovement','2-Reappearied HW after Disappear COMPLETED  for  " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
				 					 		
									insertLogsDetails_stmt.executeUpdate();
									insertLogsDetails_stmt.close();
									
									///////////////////////////////////////////////////////////////////////
									
									
									
									logger.info("3-Check if we have replacement Transfer or new node HW  for  " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									System.out.println("3-Check if we have replacement Transfer or new node HW for  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logsDetails_ID= localgetseqNbr(23);
									logsDetails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_ID;
									PreparedStatement insertLogsDetails_Stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logsDetails_ID+"',sysdate ,'CheckNodeMovement','3-Check if we have replacement Transfer or new node HW for  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
				 					 		
									insertLogsDetails_Stmt.executeUpdate();
									insertLogsDetails_Stmt.close();
									
									
									GetNewNodesfromtemptable(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
									
									System.out.println("3-check replacement Transfer or new noe HW  COMPLETED for  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									logger.info("3-check replacement Transfer or new node HW  COMPLETED  for  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						 			  
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logsDetails_Id= localgetseqNbr(23);
									logsDetails_Id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_Id;
									PreparedStatement insertLogsDEtails_Stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logsDetails_Id+"',sysdate ,'CheckNodeMovement','3-check replacement Transfer or new node HW  COMPLETED  for  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Completed','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
				 					 		
									insertLogsDEtails_Stmt.executeUpdate();
									insertLogsDEtails_Stmt.close();
									
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logsDetails_id= localgetseqNbr(23);
									logsDetails_id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_id;
									PreparedStatement insert_LogsDetails_Stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logsDetails_id+"',sysdate ,'CheckNodeMovement','Number Of Errors','','Number Of Errors','','"+nbOfErrors+"','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
				 					 		
									insert_LogsDetails_Stmt.executeUpdate();
									insert_LogsDetails_Stmt.close();
									
									//insert into AUTO_DISCOVERY_LOGS
								 	  PreparedStatement insertLogsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS (LOGS_ID,START_TIME,ACTIVITY_NAME,VENDOR,DOMAIN,STOP_TIME) "
										 		+ "values('"+logsid+"', ? ,'CheckNodeMovement','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"',?) ");
										 		
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
	
	private static void GetReappearingSN(String vdomain, String vvendor) throws SQLException  {
		Statement stmt1 = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		Statement stmt4 = null;
		
		String varstatus="-1";
		
		String varnodepk="-1";
		String varnodeattrpk="-1";
		String vnodeid="-1";
		String vsiteid="-1";
		String vcircleid="-1";
		String vnamenode="-1";
		String vcabinteid="-1";
		String varsn="-1";
		
		
		String tmpnodepk="-1";
		String tmpnodeid="-1";
		String tmpsiteid="-1";
		String tmpcircleid="-1";
		String tmpnamenode="-1";
		String vtmpcabinteid="-1";
		String vartmpsn="-1";
		int totalrec=0;
		
		stmt1 = con.createStatement();   
	    // get all nodepk where trans_type ='Node Disappeared' and Active_record=1
	    String sqlStmt = "select NODE_PK,NODE_ID,SITE_ID,CIRCLE_ID,NODE_NAME from NODE_ACTIVE where TRANS_TYPE='Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
	    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
	    while (rs1.next()) {
	    try {
	    	varstatus="-1";
	    	tmpnodepk="-1";
			tmpnodeid="-1";
			tmpsiteid="-1";
			tmpcircleid="-1";
			tmpnamenode="-1";
			vtmpcabinteid="-1";
			
	    	vcabinteid="-1";
	    	varsn="-1";
	    	varnodepk=rs1.getString("NODE_PK");
	    	vnodeid=rs1.getString("NODE_ID");
	    	vsiteid=rs1.getString("SITE_ID");
	    	vcircleid=rs1.getString("CIRCLE_ID");
	    	vnamenode=rs1.getString("NODE_NAME");
	    	
	    	// get cabinet_id and SN based on nodepk and active_record=1
	    	stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
	    	 String sqlStmt2 = "select CABINET_ID,SERIALNUMBER,NODE_PK,NODE_ATTR_PK from NODE_CABINET where NODE_PK='"+ rs1.getString("NODE_PK") +"' and CABINETNO='0' and TRANS_TYPE='Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
			    ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
			    rs2.last();
		 	    totalrec = rs2.getRow(); 
		 	    rs2.beforeFirst();
		 	   if (totalrec >0 ) {
		 		  while (rs2.next()) {
		 			try {
				    	vcabinteid=rs2.getString("CABINET_ID");
				    	varsn=rs2.getString("SERIALNUMBER");
				    	varnodeattrpk=rs2.getString("NODE_ATTR_PK");
		 		  }
		 		 catch(Exception e)  
					{  
						logger.info("error at GetReappearingSN sqlStmt2 :"+ e.toString());
						System.out.println("error at GetReappearingSN  sqlStmt2is :"+ e.toString()); 
						
						//insert into AUTO_DISCOVERY_LOGS_DETAILS
						String logsDetailsid= localgetseqNbr(23);
						logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
						PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetReappearingSN  sqlStmt2','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
	 					 		
						insertLogsDetailsstmt.executeUpdate();
						insertLogsDetailsstmt.close();
						nbOfErrors++;
					} 
				   }
				    rs2.close();
				    stmt2.close();
				    /////////////////////////////////////////////////////
					    	
					    	//Get nodeid , siteid and circleid  of this SN from Temp_node_active  and validate if  same data as in final means maitenance or diff means Transfer
						    stmt4 = con.createStatement();
					    	 String sqlStmt4 = "select a.SERIALNUMBER , b.node_id,b.site_id,b.circle_id,b.node_name from TEMP_NODE_CABINET a , TEMP_NODE_ACTIVE b where a.SERIALNUMBER='"+ varsn +"' and a.ACTIVE_RECORD='1' and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'\r\n" + 
					    	 		"minus \r\n" + 
					    	 		"select SERIALNUMBER , node_id,site_id,circle_id, node_name from (\r\n" + 
					    	 		"select a.CABINET_ID,a.SERIALNUMBER , b.NODE_PK,b.node_id,b.site_id,b.circle_id,b.node_name from NODE_CABINET a , NODE_ACTIVE b where a.SERIALNUMBER='"+ varsn +"' and a.ACTIVE_RECORD='1' and a.trans_type <> 'Node Disappeared'  and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'\r\n" + 
					    	 		")";          
					    	 ResultSet rs4 = stmt4.executeQuery(sqlStmt4);
							    while (rs4.next()) {
	                             try {
							    	tmpnodeid=rs4.getString("NODE_ID");
							    	tmpsiteid=rs4.getString("SITE_ID");
							    	tmpcircleid=rs4.getString("CIRCLE_ID");
							    	tmpnamenode=rs4.getString("NODE_NAME");
							    	vartmpsn=rs4.getString("SERIALNUMBER");
							    	
							    	 //Study cases here start with node_id then site_id then circle_id
								    // if same node site and circle means SN maintenance    if Diff node site and circle means Transfer node
								    if (StringUtils.equalsIgnoreCase (vnodeid,tmpnodeid) && StringUtils.equalsIgnoreCase (vnamenode,tmpnamenode)) {
								    	// validate site and circle
								    	if (StringUtils.equalsIgnoreCase (vsiteid,tmpsiteid) && (StringUtils.equalsIgnoreCase (vcircleid,tmpcircleid))) {
								    			System.out.println("Reappear SN After Maintenance");
									    		varstatus="Reappear SN After Maintenance";
								    	} else {
								    		if (StringUtils.equalsIgnoreCase (vsiteid,tmpsiteid)) {
								    			System.out.println("Reappear SN  After Circle Transfer");
								    			varstatus="Reappear SN  After Circle Transfer";
								    		} else {
								    			if (StringUtils.equalsIgnoreCase (vcircleid,tmpcircleid)) {
								    				System.out.println("Reappear SN  After Site Transfer");
								    				varstatus="Reappear SN  After Site Transfer";
								    			} else {
								    				System.out.println("Reappear SN  After Site & Circle Transfer");
								    				varstatus="Reappear SN  After Site & Circle Transfer";
								    			}
								    		}
								    	}
		
								    } else { // different node means Node transfer
								    	
								    	// here we have to validate if the SN found in node already exist in our final do nothing 
								    	// if the SN found in a node not found in final it is Node transfer
								    	
								    	Statement stmt20 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
								    	 String sqlStmt20 = "select  a.SERIALNUMBER , b.node_id,b.site_id,b.circle_id,b.node_name from NODE_CABINET a , NODE_ACTIVE b where a.SERIALNUMBER='"+ varsn +"' and b.NODE_ID='"+ vnodeid +"' and b.SITE_ID='"+ vsiteid +"'  and b.CIRCLE_ID='"+ vcircleid +"' and b.NODE_NAME='"+ vnamenode +"' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";          
										    ResultSet rs20 = stmt20.executeQuery(sqlStmt20);
										    rs20.last();
									 	    int totalrec20 = rs20.getRow(); 
									 	   if (totalrec >0 ) {
									 		  // in this case the SN is already found in final having same nodeid, siteid. circleid and nodename
									 		  varstatus="-100";
									 	   }else {
									 		  System.out.println("Reappear SN  After Node Transfer");
										      varstatus="Reappear SN  After Node Transfer";  
									 	   }
								    	
								    	
								    }
						  if (StringUtils.equalsIgnoreCase (varstatus,"-100")) {
							  // do nothing SN already exist in final with same node site circle and name
								    	
						   } else {
							   PreparedStatement stmtinsert0=null;
							    PreparedStatement stmtinsert1=null;
							    PreparedStatement stmtinsert2=null;
							    
							    String transid=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(21);
							    String cabid=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(7);
							    
							    /// update in node_cabinet records put old disappeared to active_record = 0 and create same records having trnas_type maintenance or transfer
							    stmtinsert0 = con.prepareStatement("insert into  NODE_CABINET_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
	     	   		 			 		+  "values ('"+ transid  +"',sysdate, '" + varstatus + "','" + varstatus + "','"+ vsiteid +"','"+ tmpsiteid +"','0','0','"+ vcircleid +"','"+ tmpcircleid +"','"+ vnodeid +"','"+ tmpnodeid +"','0','0','0','0',sysdate,'0','"+ varsn +"','1','"+ varnodepk +"','0','"+ varsn +"')  ");
	     	    			stmtinsert0.executeUpdate();
	     	    			stmtinsert0.close();
	     	    			
	     	    			stmtinsert1 = con.prepareStatement("update  NODE_CABINET set TO_TRANS_SOURCE='CABINET', TO_TRANS_ID='"+ transid  +"', ACTIVE_RECORD='0' ,UPDATE_DATE =sysdate where CABINET_ID= '"+ vcabinteid +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	     	    		 	stmtinsert1.executeUpdate();
	     	    		 	stmtinsert1.close();
							
	     	    		 //Add new record in node_cabinet
	     	    		 	stmtinsert2 = con.prepareStatement("insert into  NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) select '"+ cabid  +"',SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,sysdate,FILENAME,STATUS,'CABINET','"+ transid  +"','0','" + varstatus + "','1',LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,'0' from NODE_CABINET where CABINET_ID='"+ vcabinteid +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	     	    		 	stmtinsert2.executeUpdate();
	     	    		 	stmtinsert2.close();
								    	
						     }
								    
							    } // end try
							    catch(Exception e)  
								{  
									logger.info("error at GetReappearingSN sqlStmt4 :"+ e.toString());
									System.out.println("error at GetReappearingSN  sqlStmt4is :"+ e.toString()); 
									
									//insert into AUTO_DISCOVERY_LOGS_DETAILS
									String logsDetailsid= localgetseqNbr(23);
									logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
									PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 					 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetReappearingSN  sqlStmt4','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
				 					 		
									insertLogsDetailsstmt.executeUpdate();
									insertLogsDetailsstmt.close();
									nbOfErrors++;
								} 
							    } // end while
							    rs4.close();
							    stmt4.close();
					    	///end here ///
					    	
					 
				    
		 		  
		 	} //end if  record count >0

	    } // end try
	    catch(Exception e)  
		{  
			logger.info("error at GetReappearingSN is :"+ e.toString());
			System.out.println("error at GetReappearingSN is :"+ e.toString()); 
			
			//insert into AUTO_DISCOVERY_LOGS_DETAILS
			String logsDetailsid= localgetseqNbr(23);
			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetReappearingSN ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
				 		
			insertLogsDetailsstmt.executeUpdate();
			insertLogsDetailsstmt.close();
			nbOfErrors++;
		}	
    } //end while
	        
	    rs1.close();
	    stmt1.close();
		    
		   
		}
	
	
	
	private static void GetReappearingNodes(String vdomain, String vvendor) throws SQLException  {
		Statement stmt1 = null;
		Statement stmt2 = null;
		String varnodepk="0";
		String vartmpnodepk="0";
		String varcreationdate="0";
		String varsnfrom="0";
		String varsnto="0";
        // Get from node_active all nodes having trans_type =Node Disappeared and active_record=1
		stmt1 = con.createStatement();   
	    String sqlStmt = "select distinct NODE_PK,CREATION_DATE,NODE_ID,SITE_ID,CIRCLE_ID,NODE_NAME from NODE_ACTIVE where TRANS_TYPE='Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
	    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
	    while (rs1.next()) {
	    	try {
	    	varsnfrom="0";
	    	varsnto="0";
	    	varnodepk=rs1.getString("NODE_PK");
	    	varcreationdate=rs1.getString("CREATION_DATE");
	    	varsnfrom=GetSN(varnodepk,"NODE_CABINET",vdomain,vvendor);
	    	
	    	//validate if the same node site and circle found in Temp_node_active  if yes then Reappear after maintenance
	    	stmt2 = con.createStatement();   
		    String sqlStmt2 = "select  NODE_PK,NODE_ID,SITE_ID,CIRCLE_ID,NODE_NAME from TEMP_NODE_ACTIVE where NODE_ID='"+ rs1.getString("NODE_ID") + "' and SITE_ID ='"+ rs1.getString("SITE_ID") + "' and CIRCLE_ID='"+ rs1.getString("CIRCLE_ID") + "' and NODE_NAME='"+ rs1.getString("NODE_NAME") + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
		    ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
		    while (rs2.next()) {
		    	try {
		    	// update all disappeared data related to this node in final to active_record =0 and create all data from temp tables
		    	vartmpnodepk=rs2.getString("NODE_PK");
		    	varsnto=GetSN(vartmpnodepk,"TEMP_NODE_CABINET",vdomain,vvendor);
		    	
		    	
		    	// update all data related to vnodepk to active_record =0  then create new node form temp tables
		    	if (StringUtils.equalsIgnoreCase (varsnfrom,varsnto)) {
		    		updatedisapperednodeafterappearing("Node Reappeared", "Reappear After Maintenance",varnodepk,vartmpnodepk,rs1.getString("NODE_ID"),rs1.getString("SITE_ID"),rs1.getString("CIRCLE_ID"),varsnfrom,varsnto,varcreationdate,"NODE","Reappear After Maintenance",vdomain,vvendor);	
		    	} else {
		     	   	updatedisapperednodeafterappearing("Node Reappeared", "Reappear SN New Hardware",varnodepk,vartmpnodepk,rs1.getString("NODE_ID"),rs1.getString("SITE_ID"),rs1.getString("CIRCLE_ID"),varsnfrom,varsnto,varcreationdate,"NODE","New Hardware",vdomain,vvendor);
	    	}
		    	
		    } // end try
		    catch(Exception e)  
			{  
				logger.info("error at GetReappearingNodes sqlStmt2 is :"+ e.toString());
				System.out.println("error at GetReappearingNodes  sqlStmt2 is :"+ e.toString()); 
				
				//insert into AUTO_DISCOVERY_LOGS_DETAILS
				String logsDetailsid= localgetseqNbr(23);
				logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
				PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetReappearingNodes  sqlStmt2 ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
					 		
				insertLogsDetailsstmt.executeUpdate();
				insertLogsDetailsstmt.close();
				nbOfErrors++;
			}
		    } // end while
		    rs2.close();
		    stmt2.close();
	    } // end try
	    catch(Exception e)  
		{  
			logger.info("error at GetReappearingNodesis :"+ e.toString());
			System.out.println("error at GetReappearingNodes is :"+ e.toString()); 
			
			//insert into AUTO_DISCOVERY_LOGS_DETAILS
			String logsDetailsid= localgetseqNbr(23);
			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetReappearingNodes ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
				 		
			insertLogsDetailsstmt.executeUpdate();
			insertLogsDetailsstmt.close();
			nbOfErrors++;
		}
	    } // end while
	    rs1.close();
	    stmt1.close();
	    
	   
	}
	
	private static void updatedisapperednodeafterappearing(String status,String descrip,String nodepk,String tmpnodepk,String nodeid,String siteid,String circelid,String vSN,String vSNto, String vdate,String vsourc,String TransTyp,String vdomain,String vvendor) throws SQLException  {
		PreparedStatement stmtinsert1=null;
		PreparedStatement stmtinsert2=null;
		PreparedStatement stmtinsert3=null;
		PreparedStatement stmtinsertupd=null;
		Statement vstmt=null;
		String crdate=null;
		String crdate2=null;
		
		//try {
	 	String transid=Gyear+"_"+ "NODE"+"_"+localgetseqNbr(21);
	 	
	 	stmtinsert1 = con.prepareStatement("insert into  NODE_ACTIVE_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
		 			+ "values ('"+ transid  +"',sysdate, '"+ status +"','"+ descrip +"','"+ siteid +"','"+ siteid +"','0','0','"+ circelid +"','"+ circelid +"','"+ nodeid +"','"+ nodeid +"','0','0','0','0',sysdate,'0','"+ vSN +"','1','"+ nodepk +"','0','"+ vSNto +"')  ");  
		stmtinsert1.executeUpdate();
		stmtinsert1.close();

		// update all disappeared records set active_record=0
		stmtinsertupd = con.prepareStatement("update  NODE_ACTIVE set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_ACTIVE_ATTRIBUTE set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_RACK set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		// i removed command to update source only in cabinet to keep tracking of Cabinet transactions records i mean to keep SN replacement saved in cabinet
		stmtinsertupd = con.prepareStatement("update  NODE_CABINET set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_HOSTVER set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_FRAME set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_SLOT set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_BOARD set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_PORT set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_ACCESSORY set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_HOST set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_SUBRACK set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1'  and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_2G set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_BTS set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_4G set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_ANTENNA set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_3G set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_RRN set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_ENODEBCELL set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		
		stmtinsertupd = con.prepareStatement("update  NODE_NODEBCELL set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1'  and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
		stmtinsertupd = con.prepareStatement("update  NODE_NBINTERFACES set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		
	    // insert all records from temp into final
		stmtinsert1 = con.prepareStatement("insert into NODE_ACTIVE (select NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,TIMESTAMP '"+ vdate +"' as CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME,TO_TRANS_SOURCE from TEMP_NODE_ACTIVE where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 		
 		
 		stmtinsert2 = con.prepareStatement("insert into NODE_ACTIVE_ATTRIBUTE (select NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_ACTIVE_ATTRIBUTE where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 	
 		stmtinsert3 = con.prepareStatement("insert into NODE_RACK (select RACK_ID,RACKNO,INVENTORYUNITID,RACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_RACK where  NODE_PK  ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();

 		//Validate if it is used SN or completely new if new we will put New hardware if used will keep it as is New node
 		vstmt = con.createStatement();   
	    String vsqlStmt0 = "select CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE from TEMP_NODE_CABINET where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
	    ResultSet rrs0 = vstmt.executeQuery(vsqlStmt0);
	    Statement stmt1=null;
 		int totalrec=0;
	    while (rrs0.next()) {
	    	if (StringUtils.equalsIgnoreCase (rrs0.getString("SERIALNUMBER"),"0") ){
	    		stmtinsert1 = con.prepareStatement("insert into NODE_CABINET (select CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'NEW NODE' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_CABINET where CABINET_ID ='" + rrs0.getString("CABINET_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
	 	 		stmtinsert1.executeUpdate();
	 	 		stmtinsert1.close();
	    	} else {
	    		
	    		//GET CREATION DATE 
		 		stmt1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
  		 		String sqlStmt1 = "select CABINET_ID,SERIALNUMBER,NODE_PK,CREATION_DATE from NODE_CABINET where SERIALNUMBER='"+ rrs0.getString("SERIALNUMBER") +"' and TRANS_TYPE <> 'Node Disappeared' and  NODE_PK='"+ nodepk +"' and  LINE='"+ rrs0.getString("LINE") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";
		 		ResultSet rs1 = stmt1.executeQuery(sqlStmt1);
			    rs1.last();
		 	    totalrec = rs1.getRow(); 
		 	    rs1.beforeFirst();
		 	   if (totalrec >0) {
		 		  while (rs1.next()) {
		 			 crdate =rs1.getString("CREATION_DATE");
		 		  }
		 	     
		 	   }
		 	    rs1.close();
		 	    stmt1.close();
		 	    
		 	    
		 	    // check if SN exist ot not
		 	    String vcsn ="0";
		 	   Statement stmtcsn=null;
		 	   stmtcsn = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
		 		String sqlStmtcsn = "select CABINET_ID,SERIALNUMBER,NODE_PK,CREATION_DATE from NODE_CABINET where SERIALNUMBER='"+ rrs0.getString("SERIALNUMBER") +"' and TRANS_TYPE <> 'Node Disappeared'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";
		 		ResultSet rscsn = stmtcsn.executeQuery(sqlStmtcsn);
		 		rscsn.last();
		 	    int totalcsn = rscsn.getRow(); 
		 	   rscsn.beforeFirst();
		 	   if (totalcsn >0) {
		 		  vcsn="Existed Hardware";     
		 	   } else {
		 		  vcsn="New Hardware"; 
		 	   }
		 	  rscsn.close();
		 	   stmtcsn.close();
		 	    
		 	    
		 	   if (totalrec >0) {
		 			stmtinsert1 = con.prepareStatement("insert into NODE_CABINET (select CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ vcsn +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION, TIMESTAMP '"+ crdate + "',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_CABINET where CABINET_ID ='" + rrs0.getString("CABINET_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
		 	 		stmtinsert1.executeUpdate();
		 	 		stmtinsert1.close();
		 		} else {
		 			stmtinsert1 = con.prepareStatement("insert into NODE_CABINET (select CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ vcsn +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_CABINET where CABINET_ID ='" + rrs0.getString("CABINET_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
		 	 		stmtinsert1.executeUpdate();
		 	 		stmtinsert1.close();
		 		}
	    	}
	    	
	    }
	    rrs0.close();
	    vstmt.close();
 		
 		
 		
 
 		stmtinsert2 = con.prepareStatement("insert into NODE_HOSTVER (select HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_HOSTVER  where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 	
 		stmtinsert3 = con.prepareStatement("insert into NODE_FRAME (select FRAME_ID,RACKNO,FRAMENO,INVENTORYUNITID,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_FRAME where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();

 		stmtinsert1 = con.prepareStatement("insert into NODE_SLOT (select SLOT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TRATO_TRANS_IDNS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_SLOT where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 
 		//Validate if it is used SN or completely new if new we will put New hardware if used will keep it as is New node
 		vstmt = con.createStatement();   
	    String vsqlStmt1 = "select BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'" + vsourc +"',TO_TRANS_SOURCE,'"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR from TEMP_NODE_BOARD where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
	    ResultSet rrs1 = vstmt.executeQuery(vsqlStmt1);
	    while (rrs1.next()) {
	    	Statement stmt2=null;
	 		int totalrec2=0;
	 		stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
		    String sqlStmt2 = "select BOARD_ID,SERIALNUMBER,NODE_PK,CREATION_DATE from NODE_BOARD where SERIALNUMBER='"+ rrs1.getString("SERIALNUMBER") +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and NODE_PK='"+ nodepk +"' and  LINE='"+ rrs1.getString("LINE") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
		    ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
		    rs2.last();
	 	    totalrec2 = rs2.getRow(); 
	 	    rs2.beforeFirst();
	 	   if (totalrec2 >0) {
	 		  while (rs2.next()) {
	 			 crdate2 =rs2.getString("CREATION_DATE");
	 		  }
	 	     
	 	   }
	 	    rs2.close();
	 	    stmt2.close();
	 	    
	 	    
	 	   // check if SN exist ot not
	 	    String vbsn ="0";
	 	   Statement stmtbsn=null;
	 	  stmtbsn = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
	 		String sqlStmtbsn = "select BOARD_ID,SERIALNUMBER,NODE_PK,CREATION_DATE from NODE_BOARD where SERIALNUMBER='"+ rrs1.getString("SERIALNUMBER") +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";
	 		ResultSet rsbsn = stmtbsn.executeQuery(sqlStmtbsn);
	 		rsbsn.last();
	 	    int totalbsn = rsbsn.getRow(); 
	 	   rsbsn.beforeFirst();
	 	   if (totalbsn >0) {
	 		  vbsn="Existed Hardware";     
	 	   } else {
	 		  vbsn="New Hardware"; 
	 	   }
	 	  rsbsn.close();
	 	 stmtbsn.close();
	 	    
	 	    
	 	    
	 	   if (totalrec2 >0) {
	 		   //Reappear After Maintenance   descrip
	 		   if (StringUtils.equalsIgnoreCase (descrip,"Reappear After Maintenance")) {
	 			  stmtinsert2 = con.prepareStatement("insert into NODE_BOARD (select BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'Reappear After Maintenance' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,TIMESTAMP '"+ crdate2 +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BOARD where BOARD_ID ='" + rrs1.getString("BOARD_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')");
	 		   } else {
	 			  stmtinsert2 = con.prepareStatement("insert into NODE_BOARD (select BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'" + vbsn +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,TIMESTAMP '"+ crdate2 +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BOARD where BOARD_ID ='" + rrs1.getString("BOARD_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')");
	 		   }
	  	 		stmtinsert2.executeUpdate();
	  	 		stmtinsert2.close(); 
	  	   } else {
	  		  stmtinsert2 = con.prepareStatement("insert into NODE_BOARD (select BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'" + vbsn +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BOARD where BOARD_ID ='" + rrs1.getString("BOARD_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
	  	 		stmtinsert2.executeUpdate();
	  	 		stmtinsert2.close(); 
	  	   }
	    }
	    rrs1.close();
	    vstmt.close();
 		
 
 		stmtinsert3 = con.prepareStatement("insert into NODE_PORT (select PORT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,VENDORUNITFAMILYTYPE,INVENTORYUNITID,PORTNO,HARDWAREVERSION,SERIALNUMBER,INVENTORYUNITTYPE,VENDORNAME,VENDORUNITTYPENUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MACADDR,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD ,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_PORT where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 
 		stmtinsert1 = con.prepareStatement("insert into NODE_ACCESSORY (select ACCESSORY_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,ACCESSORYTYPE,ADDTIONALINFORMATION,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_ACCESSORY where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 
 		stmtinsert2 = con.prepareStatement("insert into NODE_HOST (select HOST_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,HOSTNAME,NUMBEROFCPU,MEMSIZE,HARDDISKSIZE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_HOST where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 	
 		stmtinsert3 = con.prepareStatement("insert into NODE_SUBRACK (select SUBRACK_ID,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_SUBRACK where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 
 		stmtinsert1 = con.prepareStatement("insert into NODE_2G (select GCELL_ID,CELLID,CELLNAME,MCC,MNC,LAC,CI,NCC,BCC,TYPE,BCCHNO,BASEBANDPOLICY,BASEBANDEQMID,GBTSFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,GLOCELLID,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_GCELL where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 	
 		stmtinsert2 = con.prepareStatement("insert into NODE_BTS (select BTS_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BTS where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();

 		stmtinsert3 = con.prepareStatement("insert into NODE_4G (select UCELL_ID,CELLID,CELLNAME,LOCELL,NODEBFUNCTIONNAME,ULFREQ,DLFREQ,MAXPOWER,USERLABEL,MAXTXPOWER,UARFCNUPLINK,UARFCNDOWNLINK,PSCRAMBCODE,NODEBNAME,LAC,SAC,RAC,MANUFACTURERDATA,RADIUS,HORAD,DI,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_UCELL where NODE_PK ='" + tmpnodepk + "')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 	
 		stmtinsert1 = con.prepareStatement("insert into NODE_ANTENNA (select ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_ANTENNA where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();

 		stmtinsert2 = con.prepareStatement("insert into NODE_3G (select LCELL_ID,LOCALCELLID,CELLNAME,CELLRADIUS,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,ENODEBFUNCTIONNAME,NBCELLFLAG,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_LCELL where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();

 		stmtinsert3 = con.prepareStatement("insert into NODE_RRN (select RRN_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_RRN where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();

 		stmtinsert1 = con.prepareStatement("insert into NODE_ENODEBCELL (select ENODEBCELL_ID,LOCALCELLID,CELLNAME,SECTORID,CSGIND,CYCLEPREFIX,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,TAC,ADDITIONALSPECTRUMEMISSION,NBCELLFLAG,ENODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_ENODEBCELL where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();

 		stmtinsert2 = con.prepareStatement("insert into NODE_NODEBCELL (select NODEBCELL_ID,LOCELL,USERLABEL,SITENO,SECNO,RADIUS,HORAD,BBPOOLTYPE,ULGROUPNO,CABINETNO1,SUBRACKNO1,SLOTNO1,CABINETNO2,SUBRACKNO2,SLOTNO2,ULFREQ,DLFREQ,MAXPOWER,HSDPA,DI,HIGHSPEEDMODE,SPEEDRATE,NODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_NODEBCELL where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();

 		stmtinsert3 = con.prepareStatement("insert into NODE_NBINTERFACES (select NBINTERFACE_ID,INTERFACETYPE,VERSION,ISUSED,NMSVENDOR,NMSNAME,REMARK,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_NBINTERFACES where NODE_PK ='" + tmpnodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
		//}// end try
		//catch(Exception e)  
		//{  
		//	logger.info("error at updatedisapperednodeafterappearing is :"+ e.toString());
		//	System.out.println("error at updatedisapperednodeafterappearing is :"+ e.toString()); 
		//} // end catch
	   
	}
	
		
	private static void GetDisappearingNodes(String vdomain, String vvendor) throws SQLException  {
		Statement stmt1 = null;
        String nodepk ="0";
        String creationDate ="0";
		// select all node_id, site_id and circel_id found in final_node_active and not in temp_node_active
		stmt1 = con.createStatement();   
	    String sqlStmt = "(select distinct NODE_ID,SITE_ID,CIRCLE_ID,NODE_NAME from NODE_ACTIVE where TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"') minus (select distinct NODE_ID,SITE_ID,CIRCLE_ID,NODE_NAME from TEMP_NODE_ACTIVE where ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')";          
	    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
	    while (rs1.next()) {
	    	try {
	    	logger.info("2-Disappear Node #  "+rs1.getString("NODE_ID"));
	    	
	    	//insert into AUTO_DISCOVERY_LOGS_DETAILS
			String logsDetailsid= localgetseqNbr(23);
			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','2-Disappear Node #  "+rs1.getString("NODE_ID")+"','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
				 		
			insertLogsDetailsstmt.executeUpdate();
			insertLogsDetailsstmt.close();
	    	
	    	// get nodepk for each records not found in temp_active_node
	    	nodepk=GetNodePK(rs1.getString("NODE_ID"),rs1.getString("SITE_ID"),rs1.getString("CIRCLE_ID"),rs1.getString("NODE_NAME"),"NODE_ACTIVE",vdomain,vvendor);
	    	
	    	// get creation_date in main active_node to keep it in amy new records
	    	creationDate=GetCreationDate(rs1.getString("NODE_ID"),rs1.getString("SITE_ID"),rs1.getString("CIRCLE_ID"),rs1.getString("NODE_NAME"),"NODE_ACTIVE",vdomain,vvendor);
	    	
	    	// get SN from node_cabinet where cbinteno=0
	    	String varSN=GetSN(nodepk,"NODE_CABINET",vdomain,vvendor);
	    	
	    	//run script to disappear all data related in all table by updating old data by moving active_record =0 and create new one having status NODE_DISAPPEAR and active_record =1
	    	RunDisappearNode_in_all_tables(creationDate,"Node Disappeared","Node Disappeared",nodepk,rs1.getString("NODE_ID"),rs1.getString("SITE_ID"),rs1.getString("CIRCLE_ID"),"NODE","Node Disappeared",varSN,vdomain,vvendor);
	    }// end try
	    catch(Exception e)  
		{  
			logger.info("error at GetDisappearingNodes is :"+ e.toString());
			System.out.println("error at GetDisappearingNodes is :"+ e.toString()); 
			
			//insert into AUTO_DISCOVERY_LOGS_DETAILS
			String logsDetailsid= localgetseqNbr(23);
			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetDisappearingNodes ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
				 		
			insertLogsDetailsstmt.executeUpdate();
			insertLogsDetailsstmt.close();
			nbOfErrors++;
		} 
	    }
	    rs1.close();
	    stmt1.close();
	    
	   
	}
	
	private static void GetNewNodesfromtemptable(String vdomain, String vvendor) throws SQLException  {
		Statement stmt1 = null;
		Statement stmt2 = null;
		int totalrec=0;
		String varSN="0";
		String varSNfinal="0";
		String vcabinetid="0";
		PreparedStatement stmtinsert0=null;
		PreparedStatement stmtinsert1=null;
	 	PreparedStatement stmtinsert2=null;
		
		stmt1 = con.createStatement();   
	     // read all records from temp tables
	    String sqlStmt = "select NODE_PK,CREATION_DATE,NODE_ID,SITE_ID,CIRCLE_ID,NODE_NAME,TRANS_TYPE,ACTIVE_RECORD from TEMP_NODE_ACTIVE where ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
	    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
	    while (rs1.next()) {
         //try {
	    	//Get SN of this node form temp cabinet where cabinetno=0 and active record =1 and having same node_pk
	    	 varSN=GetSN(rs1.getString("NODE_PK"),"TEMP_NODE_CABINET",vdomain,vvendor);
	    	
	    	// validate if same node found in final table
	    	stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    	String sqlStmt2 = "select NODE_PK,CREATION_DATE,NODE_ID,SITE_ID,CIRCLE_ID,TRANS_TYPE,ACTIVE_RECORD from NODE_ACTIVE where NODE_ID= '" + rs1.getString("NODE_ID") +"' and SITE_ID ='" + rs1.getString("SITE_ID") +"' and CIRCLE_ID ='" + rs1.getString("CIRCLE_ID") +"' and NODE_NAME='" + rs1.getString("NODE_NAME") +"' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";           
		    ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
		    rs2.last();
     	    totalrec = rs2.getRow(); 
     	    rs2.beforeFirst();
     	    if (totalrec >0 ) {// if node found in final table
     	    	while (rs2.next()) {
     	    		//try {
     	    		//Get SN of this node from cabinet where cabinetno=0 and active record =1 and having same node_pk
     	    		varSNfinal=GetSN(rs2.getString("NODE_PK"),"NODE_CABINET",vdomain,vvendor);
     	    		
     	    		if (StringUtils.equalsIgnoreCase (varSN,varSNfinal)) {
     	    			//having same SN only update field updatedate in node_active
     	    			System.out.println("Same SN and Node" + rs2.getString("NODE_PK"));
     	    			logger.info("3-Same SN and Node" + rs2.getString("NODE_PK"));
     	    			
     	    			PreparedStatement stmtupd=null;
	     		    	stmtupd = con.prepareStatement("update  NODE_ACTIVE set UPDATE_DATE =sysdate where NODE_PK ='"+ rs2.getString("NODE_PK") +"' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'") ;
	     		    	stmtupd.executeUpdate();
	     		    	stmtupd.close();
	     		    	//having same SN only update field updatedate in node_cabinet
	     		    	stmtupd = con.prepareStatement("update  NODE_CABINET set UPDATE_DATE =sysdate where NODE_PK ='"+ rs2.getString("NODE_PK") +"' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'") ;
	     		    	stmtupd.executeUpdate();
	     		    	stmtupd.close();
     	    			
     	    		}else {
     	    			// having different SN means replacement create new record in cabinet and noted as replacement 
     	    			System.out.println("different SN means replacement from SN:  "+ varSNfinal +"  to SN: " +varSN );
     	    			logger.info("3-different SN means replacement from SN:  "+ varSNfinal +"  to SN: " +varSN );
     	    			
     	    			vcabinetid=GetCabinetID(rs2.getString("NODE_PK"),"NODE_CABINET",varSNfinal,vdomain,vvendor);
     	    			String transid=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(21);
     	    			String cabid=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(7);
     	    			
     	    			stmtinsert0 = con.prepareStatement("insert into  NODE_CABINET_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
     	   		 			 		+  "values ('"+ transid  +"',sysdate, 'Replacement','SN changed','"+ rs2.getString("SITE_ID") +"','"+ rs2.getString("SITE_ID") +"','0','0','"+ rs2.getString("CIRCLE_ID") +"','"+ rs2.getString("CIRCLE_ID") +"','"+ rs2.getString("NODE_ID") +"','"+ rs2.getString("NODE_ID") +"','0','0','0','0',sysdate,'0','"+ varSNfinal +"','1','"+ rs2.getString("NODE_PK") +"','0','"+ varSN +"')  ");
     	    			stmtinsert0.executeUpdate();
     	    			stmtinsert0.close();
     	    			
     	    			stmtinsert1 = con.prepareStatement("update  NODE_CABINET set TO_TRANS_SOURCE='CABINET',TO_TRANS_ID='"+ transid  +"', ACTIVE_RECORD='0' ,UPDATE_DATE =sysdate where CABINET_ID= '"+ vcabinetid +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
     	    		 	stmtinsert1.executeUpdate();
     	    		 	stmtinsert1.close();
     	    		 	
     	    		 	//Add new record in node_cabinet
     	    		 	stmtinsert2 = con.prepareStatement("insert into  NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) select '"+ cabid  +"',SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,'"+ varSN  +"',HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,sysdate,FILENAME,STATUS,'CABINET','"+ transid  +"','0','Replacement','1',LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_CABINET where CABINET_ID='"+ vcabinetid +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
     	    		 	stmtinsert2.executeUpdate();
     	    		 	stmtinsert2.close();
     	    		 	
     	    		 	/* added to update all nodes having this SN  not required now
     	    			// check all cabinet having this SN and replace it 
     	    			//ReplaceALLSN(varSNfinal,varSN,vdomain,vvendor);
     	    			 */
     	    			
     	    		}
     	    	//} // end try
     	    	//	catch(Exception e)  
    			//	{  
    				//	logger.info("error at GetNewNodesfromtemptable sqlStmt2 is :"+ e.toString());
    			//		System.out.println("error at GetNewNodesfromtemptable sqlStmt2 is :"+ e.toString()); 
    			//	}
     	    	}
		    	
		    } else {// new node not found in final add it to Final node

		    	System.out.println("New node Found under : "+rs1.getString("NODE_ID") +"  Add all data related in all tables");
		    	logger.info("3-New node Found under : "+rs1.getString("NODE_ID") +"  Add all data related in all tables");
		    	
		    	//insert into AUTO_DISCOVERY_LOGS_DETAILS
 	   			String logsDetailsid= localgetseqNbr(23);
 	   			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
 	   			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
 	   				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','3-New node Found under : "+rs1.getString("NODE_ID") +"  Add all data related in all tables','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
 	   				 		
 	   			insertLogsDetailsstmt.executeUpdate();
 	   			insertLogsDetailsstmt.close();
		    	
		    	Add_new_node_and_all_tables("New Node","New Node",rs1.getString("NODE_PK"),rs1.getString("NODE_ID"),rs1.getString("SITE_ID"),rs1.getString("CIRCLE_ID"),varSN,rs1.getString("CREATION_DATE"),"NODE","New Node",vdomain,vvendor);
		    }
		    rs2.close();
		    stmt2.close();
	   // }// end try 
	  //  catch(Exception e)  
		//{  
		//	logger.info("error at GetNewNodesfromtemptable is :"+ e.toString());
		//	System.out.println("error at GetNewNodesfromtemptable is :"+ e.toString()); 
		//}
	    }
	    rs1.close();
	    stmt1.close();
	    
	   
	}
	
	//
	private static void DisappearAllSNinAllnodes(String vsnfrom,String vdomain, String vvendor) throws SQLException  {
		Statement stmt1 = null;
		PreparedStatement stmtinsert0=null;
		PreparedStatement stmtinsert1=null;
		PreparedStatement stmtinsert2=null;
		
		if (StringUtils.equalsIgnoreCase (vsnfrom,"0")) {
			   // if SN coming =0 do nothing 
		} else {
		
				stmt1 = con.createStatement();   
			    // Get All Cabinet having SN=vsnfrom and replace it with vsnto  from NODE_CABINET
			    String sqlStmt1 = "select CABINET_ID,SERIALNUMBER,NODE_PK from NODE_CABINET where SERIALNUMBER='"+ vsnfrom +"' and CABINETNO='0' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
			    ResultSet rs1 = stmt1.executeQuery(sqlStmt1);
			    while (rs1.next()) {
			    	
			    	GetNodedetail(rs1.getString("NODE_PK"),vdomain,vvendor);
			    	
			    	String transid=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(21);
		 			String cabid=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(7);
		 			
			    	stmtinsert0 = con.prepareStatement("insert into  NODE_CABINET_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
			 			 		+  "values ('"+ transid  +"',sysdate, 'Replacement','SN changed','"+ Gsiteid +"','"+ Gsiteid +"','0','0','"+ Gcircleid +"','"+ Gcircleid +"','"+ Gnodeid +"','"+ Gnodeid +"','0','0','0','0',sysdate,'0','"+ vsnfrom +"','1','"+ rs1.getString("NODE_PK") +"','0','0')  ");
					stmtinsert0.executeUpdate();
					stmtinsert0.close();
					
				 	stmtinsert1 = con.prepareStatement("insert into NODE_CABINET (select '" + Gyear +"' ||'_CABINET_'|| NODECABINET_SEQ.nextval,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'NODE','"+ transid +"','0','Node Disappeared' ,'1',LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_CABINET where CABINET_ID ='" + rs1.getString("CABINET_ID") + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			  		stmtinsert1.executeUpdate();
			  		stmtinsert1.close();
			  		
			  		stmtinsert2 = con.prepareStatement("update  NODE_CABINET set TO_TRANS_SOURCE='CABINET',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where CABINET_ID ='"+ rs1.getString("CABINET_ID") +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
			  		stmtinsert2.executeUpdate();
			  		stmtinsert2.close();
				 	
			    }
			    rs1.close();
			    stmt1.close();
		}
	}
	
	private static void ReplaceALLSN(String vsnfrom,String vsnto,String vdomain, String vvendor) throws SQLException  {
		Statement stmt1 = null;
		PreparedStatement stmtinsert0=null;
		PreparedStatement stmtinsert1=null;
		PreparedStatement stmtinsert2=null;
		
		if (StringUtils.equalsIgnoreCase (vsnfrom,"0")) {
			    // if vsnfrom = 0 do nothing 
		} else {
				stmt1 = con.createStatement();   
			    // Get All Cabinet having SN=vsnfrom and replace it with vsnto  from NODE_CABINET
			    String sqlStmt1 = "select CABINET_ID,SERIALNUMBER,NODE_PK from NODE_CABINET where SERIALNUMBER='"+ vsnfrom +"' and CABINETNO='0' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
			    ResultSet rs1 = stmt1.executeQuery(sqlStmt1);
			    while (rs1.next()) {
			    	
			    	GetNodedetail(rs1.getString("NODE_PK"),vdomain,vvendor);
			    	
			    	String transid=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(21);
		 			String cabid=Gyear+"_"+ "CABINET"+"_"+localgetseqNbr(7);
		 			
			    	stmtinsert0 = con.prepareStatement("insert into  NODE_CABINET_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
			 			 		+  "values ('"+ transid  +"',sysdate, 'Replacement','SN changed','"+ Gsiteid +"','"+ Gsiteid +"','0','0','"+ Gcircleid +"','"+ Gcircleid +"','"+ Gnodeid +"','"+ Gnodeid +"','0','0','0','0',sysdate,'0','"+ vsnfrom +"','1','"+ rs1.getString("NODE_PK") +"','0','"+ vsnto +"')  ");
					stmtinsert0.executeUpdate();
					stmtinsert0.close();
					
					stmtinsert1 = con.prepareStatement("update  NODE_CABINET set TO_TRANS_SOURCE='CABINET',TO_TRANS_ID='"+ transid  +"', ACTIVE_RECORD='0' ,UPDATE_DATE =sysdate where CABINET_ID= '"+ rs1.getString("CABINET_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				 	stmtinsert1.executeUpdate();
				 	stmtinsert1.close();
				 	
				 	//Add new record in node_cabinet
				 	stmtinsert2 = con.prepareStatement("insert into  NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) select '"+ cabid  +"',SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,'"+ vsnto  +"',HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,sysdate,FILENAME,STATUS,'CABINET','"+ transid  +"','0','Replacement','1',LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_CABINET where CABINET_ID='"+ rs1.getString("CABINET_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				 	stmtinsert2.executeUpdate();
				 	stmtinsert2.close();
			    }
			    rs1.close();
			    stmt1.close();
		}
	}
	
	
	private static void Add_new_node_and_all_tables(String status,String descrip,String nodepk,String nodeid,String siteid,String circelid,String vSN, String vdate,String vsourc,String TransTyp,String vdomain,String vvendor) throws SQLException  {
		PreparedStatement stmtinsert1=null;
		PreparedStatement stmtinsert2=null;
		PreparedStatement stmtinsert3=null;
		Statement vstmt=null;
		String crdate1 = null;
		String crdate4 = null;
		//try {
		
	 	String transid=Gyear+"_"+ "NODE"+"_"+localgetseqNbr(21);
	 	
	 	stmtinsert1 = con.prepareStatement("insert into  NODE_ACTIVE_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
		 			+ "values ('"+ transid  +"',sysdate, '"+ status +"','"+ descrip +"','"+ siteid +"','0','0','0','"+ circelid +"','0','"+ nodeid +"','0','0','0','0','0',sysdate,'0','"+ vSN +"','1','"+ nodepk +"','0','0')  ");  
		stmtinsert1.executeUpdate();
		stmtinsert1.close();

	    
		stmtinsert1 = con.prepareStatement("insert into NODE_ACTIVE (select NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,TIMESTAMP '"+ vdate +"' as CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME,TO_TRANS_SOURCE from TEMP_NODE_ACTIVE where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 		
 		stmtinsert2 = con.prepareStatement("insert into NODE_ACTIVE_ATTRIBUTE (select NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_ACTIVE_ATTRIBUTE where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 	
 		stmtinsert3 = con.prepareStatement("insert into NODE_RACK (select RACK_ID,RACKNO,INVENTORYUNITID,RACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_RACK where  NODE_PK  ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 		
 		//Validate if it is used SN or completely new if new we will put New hardware if used will keep it as is New node
 		vstmt = con.createStatement();  
 		//System.out.println("select CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE from TEMP_NODE_CABINET where NODE_PK ='" + nodepk + "'");
 		String vsqlStmt0 = "select CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"',TO_TRANS_SOURCE,'"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE from TEMP_NODE_CABINET where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
	    ResultSet rrs0 = vstmt.executeQuery(vsqlStmt0);
	    Statement stmt1=null;
 		int totalrec=0;
	    while (rrs0.next()) {
	    	if (StringUtils.equalsIgnoreCase (rrs0.getString("SERIALNUMBER"),"0") ){
	    		stmtinsert1 = con.prepareStatement("insert into NODE_CABINET (select CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ descrip +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_CABINET where CABINET_ID ='" + rrs0.getString("CABINET_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
	 	 		stmtinsert1.executeUpdate();
	 	 		stmtinsert1.close();
	    	} else {
	    		int repons=GetCabinetSNstatus(rrs0.getString("SERIALNUMBER"),vdomain,vvendor);
	    		String vstat="0";
	    		if (repons==1) {
	    			vstat="Existed Hardware";
	    		}else {
	    			vstat="New Hardware";
	    		}
		 		stmt1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
		 		String sqlStmt1 = "select CABINET_ID,SERIALNUMBER,NODE_PK,CREATION_DATE from NODE_CABINET where SERIALNUMBER='"+ rrs0.getString("SERIALNUMBER") +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1'  and NODE_PK='"+ rrs0.getString("NODE_PK") +"' and rownum=1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
			    ResultSet rs1 = stmt1.executeQuery(sqlStmt1);
			    rs1.last();
		 	    totalrec = rs1.getRow(); 
		 	    rs1.beforeFirst();
		 	   if (totalrec >0) {
		 		  while (rs1.next()) {
		 			 crdate1=rs1.getString("CREATION_DATE");
		 		  }
		 	   }
		 	    rs1.close();
		 	    stmt1.close();
		 	   if (totalrec >0) {
		 			stmtinsert1 = con.prepareStatement("insert into NODE_CABINET (select CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'" + vstat + "' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION, TIMESTAMP '" + crdate1 + "',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_CABINET where CABINET_ID ='" + rrs0.getString("CABINET_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
		 	 		stmtinsert1.executeUpdate();
		 	 		stmtinsert1.close();
		 		} else {
		 			stmtinsert1 = con.prepareStatement("insert into NODE_CABINET (select CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'" + vstat + "' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_CABINET where CABINET_ID ='" + rrs0.getString("CABINET_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
		 	 		stmtinsert1.executeUpdate();
		 	 		stmtinsert1.close();
		 		}
	    	}
	    	
	    }
	    rrs0.close();
	    vstmt.close();
 		
 
 		stmtinsert2 = con.prepareStatement("insert into NODE_HOSTVER (select HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_HOSTVER  where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 	
 		stmtinsert3 = con.prepareStatement("insert into NODE_FRAME (select FRAME_ID,RACKNO,FRAMENO,INVENTORYUNITID,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_FRAME where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();

 		stmtinsert1 = con.prepareStatement("insert into NODE_SLOT (select SLOT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_SLOT where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 		
 		
 		//Validate if it is used SN or completely new if new we will put New hardware if used will keep it as is New node
 		vstmt = con.createStatement();   
	    String vsqlStmt1 = "select BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'" + vsourc +"',TO_TRANS_SOURCE,'"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE from TEMP_NODE_BOARD where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
	    ResultSet rrs1 = vstmt.executeQuery(vsqlStmt1);
	    Statement stmt2=null;
 		int totalrec2=0;
	    while (rrs1.next()) {
	    	if (StringUtils.equalsIgnoreCase (rrs1.getString("SERIALNUMBER"),"0") ){
	    		stmtinsert2 = con.prepareStatement("insert into NODE_BOARD (select BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ descrip +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BOARD where BOARD_ID ='" + rrs1.getString("BOARD_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
	  	 		stmtinsert2.executeUpdate();
	  	 		stmtinsert2.close();
	    	} else {
	    		int repons1=GetBoardSNstatus(rrs1.getString("SERIALNUMBER"),vdomain,vvendor);
	    		String vstat1="0";
	    		if (repons1==1) {
	    			vstat1="Existed Hardware";
	    		}else {
	    			vstat1="New Hardware";
	    		}
	    		 

	    		stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
			    String sqlStmt2 = "select BOARD_ID,SERIALNUMBER,NODE_PK,CREATION_DATE,DOMAIN,VENDOR from NODE_BOARD where SERIALNUMBER='"+ rrs1.getString("SERIALNUMBER") +"' and TRANS_TYPE <> 'Board Disappeared' and ACTIVE_RECORD='1' and NODE_PK='"+ rrs1.getString("NODE_PK") +"' and rownum=1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
			    ResultSet rss2 = stmt2.executeQuery(sqlStmt2);
			    rss2.last();
		 	    totalrec2 = rss2.getRow(); 
		 	    rss2.beforeFirst();
		 	   
		 	   if (totalrec2 >0) {
		 		  while (rss2.next()) {
			 	     crdate4=rss2.getString("CREATION_DATE");  
		 		  }
		 		  
		 	   }
		 	    rss2.close();
		 	    stmt2.close();
		 	   if (totalrec2 >0) {
		  		  stmtinsert2 = con.prepareStatement("insert into NODE_BOARD (select BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'" + vstat1 + "' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,TIMESTAMP '" + crdate4 + "',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BOARD where BOARD_ID ='" + rrs1.getString("BOARD_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
		  	 		stmtinsert2.executeUpdate();
		  	 		stmtinsert2.close(); 
		  	   } else {
		  		  stmtinsert2 = con.prepareStatement("insert into NODE_BOARD (select BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'" + vstat1 + "' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BOARD where BOARD_ID ='" + rrs1.getString("BOARD_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
		  	 		stmtinsert2.executeUpdate();
		  	 		stmtinsert2.close(); 
		  	   }
	    	}
	    }
	    rrs1.close();
	    vstmt.close();

 
 		stmtinsert3 = con.prepareStatement("insert into NODE_PORT (select PORT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,VENDORUNITFAMILYTYPE,INVENTORYUNITID,PORTNO,HARDWAREVERSION,SERIALNUMBER,INVENTORYUNITTYPE,VENDORNAME,VENDORUNITTYPENUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MACADDR,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD ,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_PORT where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 
 		stmtinsert1 = con.prepareStatement("insert into NODE_ACCESSORY (select ACCESSORY_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,ACCESSORYTYPE,ADDTIONALINFORMATION,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_ACCESSORY where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 
 		stmtinsert2 = con.prepareStatement("insert into NODE_HOST (select HOST_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,HOSTNAME,NUMBEROFCPU,MEMSIZE,HARDDISKSIZE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_HOST where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 	
 		stmtinsert3 = con.prepareStatement("insert into NODE_SUBRACK (select SUBRACK_ID,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_SUBRACK where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 
 		stmtinsert1 = con.prepareStatement("insert into NODE_2G (select GCELL_ID,CELLID,CELLNAME,MCC,MNC,LAC,CI,NCC,BCC,TYPE,BCCHNO,BASEBANDPOLICY,BASEBANDEQMID,GBTSFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,GLOCELLID,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_GCELL where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 	
 		stmtinsert2 = con.prepareStatement("insert into NODE_BTS (select BTS_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BTS where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();

 		stmtinsert3 = con.prepareStatement("insert into NODE_4G (select UCELL_ID,CELLID,CELLNAME,LOCELL,NODEBFUNCTIONNAME,ULFREQ,DLFREQ,MAXPOWER,USERLABEL,MAXTXPOWER,UARFCNUPLINK,UARFCNDOWNLINK,PSCRAMBCODE,NODEBNAME,LAC,SAC,RAC,MANUFACTURERDATA,RADIUS,HORAD,DI,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_UCELL where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 	
 		//stmtinsert1 = con.prepareStatement("insert into NODE_ANTENNA (select ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE from TEMP_NODE_ANTENNA where NODE_PK ='" + nodepk + "')"); 
 		//stmtinsert1.executeUpdate();
 		//stmtinsert1.close();
 		
 		
 		
 		//Validate if it is used SN or completely new if new we will put New hardware if used will keep it as is New node
 		Statement vstmta=null;
 		vstmta = con.createStatement();   
	    String vsqlStmta = "select ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE from TEMP_NODE_ANTENNA where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
	    ResultSet rrsa = vstmta.executeQuery(vsqlStmta);
	    Statement stmta=null;
 		int totalreca=0;
	    while (rrsa.next()) {
	    	if (StringUtils.equalsIgnoreCase (rrsa.getString("SERIALNUMBER"),"0") ){
	    		stmtinsert2 = con.prepareStatement("insert into NODE_ANTENNA (select  ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ descrip +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_ANTENNA where ANTENNA_ID ='" + rrsa.getString("ANTENNA_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
	  	 		stmtinsert2.executeUpdate();
	  	 		stmtinsert2.close();
	    	} else {
	    		int reponsa=GetAntennaSNstatus(rrsa.getString("SERIALNUMBER"),vdomain,vvendor);
	    		String vstata="0";
	    		if (reponsa==1) {
	    			vstata="Existed Hardware";
	    		}else {
	    			vstata="New Hardware";
	    		}
	    		 
	    		Statement stmt2a=null;
	    		stmt2a = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
			    String sqlStmt2a = "select ANTENNA_ID,SERIALNUMBER,NODE_PK,CREATION_DATE from NODE_ANTENNA where SERIALNUMBER='"+ rrsa.getString("SERIALNUMBER") +"' and TRANS_TYPE <> 'Disappear' and ACTIVE_RECORD='1' and NODE_PK='"+ rrsa.getString("NODE_PK") +"' and rownum=1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
			    ResultSet rss2a = stmt2a.executeQuery(sqlStmt2a);
			    rss2a.last();
		 	    int totalrec2a = rss2a.getRow(); 
		 	    rss2a.beforeFirst();
		 	   
		 	   if (totalrec2a >0) {
		 		  while (rss2a.next()) {
			 	     crdate4=rss2a.getString("CREATION_DATE");  
		 		  }
		 		  
		 	   }
		 	    rss2a.close();
		 	    stmt2a.close();
		 	   if (totalreca >0) {
		  		  stmtinsert2 = con.prepareStatement("insert into NODE_ANTENNA (select ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'" + vstata + "' as TRANS_TYPE,ACTIVE_RECORD,LINE,TIMESTAMP '" + crdate4 + "',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_ANTENNA where ANTENNA_ID ='" + rrsa.getString("ANTENNA_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
		  	 		stmtinsert2.executeUpdate();
		  	 		stmtinsert2.close(); 
		  	   } else {
		  		  stmtinsert2 = con.prepareStatement("insert into NODE_ANTENNA (select ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'" + vstata + "' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_ANTENNA where ANTENNA_ID ='" + rrsa.getString("ANTENNA_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
		  	 		stmtinsert2.executeUpdate();
		  	 		stmtinsert2.close(); 
		  	   }
	    	}
	    }
	    rrsa.close();
	    vstmta.close();
 		
 		
 		
 		
 		
 		
 		

 		stmtinsert2 = con.prepareStatement("insert into NODE_3G (select LCELL_ID,LOCALCELLID,CELLNAME,CELLRADIUS,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,ENODEBFUNCTIONNAME,NBCELLFLAG,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_LCELL where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();

 		stmtinsert3 = con.prepareStatement("insert into NODE_RRN (select RRN_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_RRN where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();

 		stmtinsert1 = con.prepareStatement("insert into NODE_ENODEBCELL (select ENODEBCELL_ID,LOCALCELLID,CELLNAME,SECTORID,CSGIND,CYCLEPREFIX,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,TAC,ADDITIONALSPECTRUMEMISSION,NBCELLFLAG,ENODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_ENODEBCELL where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();

 		stmtinsert2 = con.prepareStatement("insert into NODE_NODEBCELL (select NODEBCELL_ID,LOCELL,USERLABEL,SITENO,SECNO,RADIUS,HORAD,BBPOOLTYPE,ULGROUPNO,CABINETNO1,SUBRACKNO1,SLOTNO1,CABINETNO2,SUBRACKNO2,SLOTNO2,ULFREQ,DLFREQ,MAXPOWER,HSDPA,DI,HIGHSPEEDMODE,SPEEDRATE,NODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_NODEBCELL where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();

 		stmtinsert3 = con.prepareStatement("insert into NODE_NBINTERFACES (select NBINTERFACE_ID,INTERFACETYPE,VERSION,ISUSED,NMSVENDOR,NMSNAME,REMARK,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"',TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_NBINTERFACES where NODE_PK ='" + nodepk + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
		//}
		//catch(Exception e)  
		//{  
		//	logger.info("error at Add_new_node_and_all_tables is :"+ e.toString());
		//	System.out.println("error at Add_new_node_and_all_tables is :"+ e.toString()); 
		//}
	}
	
	
	private static void RunDisappearNode_in_all_tables(String vdate,String status,String descrip,String nodepk,String vnodeid,String vsiteid,String vcircleid,String vsourc,String TransTyp,String vSN,String vdomain,String vvendor) throws SQLException  {
		PreparedStatement stmtinsert1=null;
		PreparedStatement stmtinsert2=null;
		PreparedStatement stmtinsert3=null;
		PreparedStatement stmtinsertupd=null;
		
		try {
		
	 	String transid=Gyear+"_"+ "NODE"+"_"+localgetseqNbr(21);
	 	String vnodepk=Gyear+"_"+ "NODE"+"_"+localgetseqNbr(0);
	 	String vnodeattrpk=Gyear+"_"+ "ATTRIBUTE"+"_"+localgetseqNbr(1);
	 	
	 	stmtinsert1 = con.prepareStatement("insert into  NODE_ACTIVE_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
		 			+ "values ('"+ transid  +"',sysdate, '"+ status +"','"+ descrip +"','"+ vsiteid +"','0','0','0','"+ vcircleid +"','0','"+ vnodeid +"','0','0','0','0','0',sysdate,'0','"+ vSN +"','1','"+ nodepk +"','0','0')  ");  
		stmtinsert1.executeUpdate();
		stmtinsert1.close();
		
		stmtinsert1 = con.prepareStatement("insert into NODE_ACTIVE (select '" + vnodepk +"' ,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,TIMESTAMP '"+ vdate +"' as CREATION_DATE,sysdate,FILE_TYPE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME,TO_TRANS_SOURCE from NODE_ACTIVE where NODE_PK ='" + nodepk + "' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 		stmtinsertupd = con.prepareStatement("update  NODE_ACTIVE set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();

 		stmtinsert2 = con.prepareStatement("insert into NODE_ACTIVE_ATTRIBUTE (select '" + Gyear +"' ||'_ATTRIBUTE_'|| NODEACTIVEATTRIBUTE_SEQ.nextval ,ATTRIBUTE,ATTRIBUTE_TABLE,'" + vnodepk +"',NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_ACTIVE_ATTRIBUTE where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 		stmtinsertupd = con.prepareStatement("update  NODE_ACTIVE_ATTRIBUTE set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1'  and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' ") ;
		stmtinsertupd.executeUpdate();
		stmtinsertupd.close();
		 
		
		Statement stmt1 = null;
		stmt1 = con.createStatement();   
		String sqlStmt = "select NODE_ATTR_PK,ATTRIBUTE_TABLE from NODE_ACTIVE_ATTRIBUTE where NODE_PK='" + vnodepk +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
		ResultSet rs1 = stmt1.executeQuery(sqlStmt);
		    while (rs1.next()) { 
		    	 switch(rs1.getString("ATTRIBUTE_TABLE"))
		 	    {
		 	   case "NODE_RACK":
		 		   	stmtinsert3 = con.prepareStatement("insert into NODE_RACK (select '" + Gyear +"' ||'_RACK_'|| NODERACK_SEQ.nextval,RACKNO,INVENTORYUNITID,RACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_RACK where  NODE_PK  ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
		 	 		stmtinsert3.executeUpdate();
		 	 		stmtinsert3.close();
		 	 		stmtinsertupd = con.prepareStatement("update  NODE_RACK set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
		 			stmtinsertupd.executeUpdate();
		 			stmtinsertupd.close();
					break;
		 	  case "NODE_CABINET":
			 		stmtinsert1 = con.prepareStatement("insert into NODE_CABINET (select '" + Gyear +"' ||'_CABINET_'|| NODECABINET_SEQ.nextval,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_CABINET where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			  		stmtinsert1.executeUpdate();
			  		stmtinsert1.close();
			  		stmtinsertupd = con.prepareStatement("update  NODE_CABINET set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1'  and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' ") ;
			 		stmtinsertupd.executeUpdate();
			 		stmtinsertupd.close();
			 		
			 		//Disappear SN if existing in all other nodes
			 		//DisappearAllSNinAllnodes(vSN,vdomain,vvendor);
			 		
					break;
		 	 case "NODE_HOSTVER":
			 		stmtinsert2 = con.prepareStatement("insert into NODE_HOSTVER (select '" + Gyear +"' ||'_HOSTVER_'|| NODEHOSTVER_SEQ.nextval,HOSTVERTYPE,HOSTVER,SDESC,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_HOSTVER  where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert2.executeUpdate();
			 		stmtinsert2.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_HOSTVER set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
					break;
		 	case "NODE_FRAME":
			 		stmtinsert3 = con.prepareStatement("insert into NODE_FRAME (select '" + Gyear +"' ||'_FRAME_'|| NODEFRAME_SEQ.nextval,RACKNO,FRAMENO,INVENTORYUNITID,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_FRAME where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert3.executeUpdate();
			 		stmtinsert3.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_FRAME set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"'  and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
		 	case "NODE_SLOT":
			 		stmtinsert1 = con.prepareStatement("insert into NODE_SLOT (select '" + Gyear +"' ||'_SLOT_'|| NODESLOT_SEQ.nextval,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,ALM_POSITION,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_SLOT where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert1.executeUpdate();
			 		stmtinsert1.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_SLOT set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
		 	case "NODE_BOARD":
			 		stmtinsert2 = con.prepareStatement("insert into NODE_BOARD (select '" + Gyear +"' ||'_BOARD_'|| NODEBOARD_SEQ.nextval,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_BOARD where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert2.executeUpdate();
			 		stmtinsert2.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_BOARD set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
		 	case "NODE_PORT":
			 		stmtinsert3 = con.prepareStatement("insert into NODE_PORT (select '" + Gyear +"' ||'_PORT_'|| NODEPORT_SEQ.nextval,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,VENDORUNITFAMILYTYPE,INVENTORYUNITID,PORTNO,HARDWAREVERSION,SERIALNUMBER,INVENTORYUNITTYPE,VENDORNAME,VENDORUNITTYPENUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MACADDR,MANUFACTURERDATA,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_PORT where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert3.executeUpdate();
			 		stmtinsert3.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_PORT set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
		 	case "NODE_ACCESSORY":
			 		stmtinsert1 = con.prepareStatement("insert into NODE_ACCESSORY (select '" + Gyear +"' ||'_ACCESSORY_'|| NODEACCESSORY_SEQ.nextval,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,ACCESSORYTYPE,ADDTIONALINFORMATION,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_ACCESSORY where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' )"); 
			 		stmtinsert1.executeUpdate();
			 		stmtinsert1.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_ACCESSORY set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
		 	case "NODE_HOST":
			 		stmtinsert2 = con.prepareStatement("insert into NODE_HOST (select '" + Gyear +"' ||'_HOST_'|| NODEHOST_SEQ.nextval,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,HOSTNAME,NUMBEROFCPU,MEMSIZE,HARDDISKSIZE,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_HOST where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert2.executeUpdate();
			 		stmtinsert2.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_HOST set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1'  and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
		 	case "NODE_SUBRACK":
			 		stmtinsert3 = con.prepareStatement("insert into NODE_SUBRACK (select '" + Gyear +"' ||'_SUBRACK_'|| NODESUBRACK_SEQ.nextval,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_SUBRACK where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert3.executeUpdate();
			 		stmtinsert3.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_SUBRACK set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
		 	case "NODE_2G":
			 		stmtinsert1 = con.prepareStatement("insert into NODE_2G (select '" + Gyear +"' ||'_GCELL_'|| NODEGCELL_SEQ.nextval,CELLID,CELLNAME,MCC,MNC,LAC,CI,NCC,BCC,TYPE,BCCHNO,BASEBANDPOLICY,BASEBANDEQMID,GBTSFUNCTIONNAME,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,GLOCELLID,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_2G where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert1.executeUpdate();
			 		stmtinsert1.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_2G set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
		 	case "NODE_BTS":
			 		stmtinsert2 = con.prepareStatement("insert into NODE_BTS (select '" + Gyear +"' ||'_BTS_'|| NODEBTS_SEQ.nextval,SITEINDEX,SITENAME,SITETYPE,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_BTS where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert2.executeUpdate();
			 		stmtinsert2.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_BTS set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
		 		break;
			case "NODE_4G":
					stmtinsert3 = con.prepareStatement("insert into NODE_4G (select '" + Gyear +"' ||'_UCELL_'|| NODEUCELL_SEQ.nextval,CELLID,CELLNAME,LOCELL,NODEBFUNCTIONNAME,ULFREQ,DLFREQ,MAXPOWER,USERLABEL,MAXTXPOWER,UARFCNUPLINK,UARFCNDOWNLINK,PSCRAMBCODE,NODEBNAME,LAC,SAC,RAC,MANUFACTURERDATA,RADIUS,HORAD,DI,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_UCELL where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert3.executeUpdate();
			 		stmtinsert3.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_4G set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
			case "NODE_ANTENNA":
					stmtinsert1 = con.prepareStatement("insert into NODE_ANTENNA (select '" + Gyear +"' ||'_ANTENNA_'|| NODEANTENNA_SEQ.nextval,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_ANTENNA where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert1.executeUpdate();
			 		stmtinsert1.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_ANTENNA set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
			case "NODE_3G":
					stmtinsert2 = con.prepareStatement("insert into NODE_3G (select '" + Gyear +"' ||'_LCELL_'|| NODELCELL_SEQ.nextval,LOCALCELLID,CELLNAME,CELLRADIUS,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,ENODEBFUNCTIONNAME,NBCELLFLAG,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_LCELL where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert2.executeUpdate();
			 		stmtinsert2.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_3G set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
			case "NODE_RRN":
					stmtinsert3 = con.prepareStatement("insert into NODE_RRN (select '" + Gyear +"' ||'_RRN_'|| NODERRN_SEQ.nextval,SITEINDEX,SITENAME,SITETYPE,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_RRN where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert3.executeUpdate();
			 		stmtinsert3.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_RRN set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
			case "NODE_ENODEBCELL":
					stmtinsert1 = con.prepareStatement("insert into NODE_ENODEBCELL (select '" + Gyear +"' ||'_ENODEBCELL_'|| NODEENODEBCELL_SEQ.nextval,LOCALCELLID,CELLNAME,SECTORID,CSGIND,CYCLEPREFIX,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,TAC,ADDITIONALSPECTRUMEMISSION,NBCELLFLAG,ENODEBFUNCTIONNAME,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_ENODEBCELL where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='0' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert1.executeUpdate();
			 		stmtinsert1.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_ENODEBCELL set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1'  and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
			case "NODE_NODEBCELL":
					stmtinsert2 = con.prepareStatement("insert into NODE_NODEBCELL (select '" + Gyear +"' ||'_NODEBCELL_'|| NODEBCELL_SEQ.nextval,LOCELL,USERLABEL,SITENO,SECNO,RADIUS,HORAD,BBPOOLTYPE,ULGROUPNO,CABINETNO1,SUBRACKNO1,SLOTNO1,CABINETNO2,SUBRACKNO2,SLOTNO2,ULFREQ,DLFREQ,MAXPOWER,HSDPA,DI,HIGHSPEEDMODE,SPEEDRATE,NODEBFUNCTIONNAME,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_NODEBCELL where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert2.executeUpdate();
			 		stmtinsert2.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_NODEBCELL set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
			case "NODE_NBINTERFACES":
					stmtinsert3 = con.prepareStatement("insert into NODE_NBINTERFACES (select '" + Gyear +"' ||'_NBINTERFACES_'|| NODENBINTERFACE_SEQ.nextval,INTERFACETYPE,VERSION,ISUSED,NMSVENDOR,NMSNAME,REMARK,'" + vnodepk +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,STATUS,'" + vsourc +"','"+ transid +"','0','Node Disappeared' ,'1',LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE from NODE_NBINTERFACES where NODE_PK ='" + nodepk + "'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
			 		stmtinsert3.executeUpdate();
			 		stmtinsert3.close();
			 		stmtinsertupd = con.prepareStatement("update  NODE_NBINTERFACES set TO_TRANS_SOURCE='" + vsourc +"',TO_TRANS_ID ='"+ transid +"' , ACTIVE_RECORD='0' ,UPDATE_DATE = sysdate where NODE_PK ='"+ nodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'  ") ;
					stmtinsertupd.executeUpdate();
					stmtinsertupd.close();
				break;
		 	    }
		    }
		    rs1.close();
		    stmt1.close();
		}
		catch(Exception e)  
		{  
			logger.info("error at RunDisappearNode_in_all_tables is :"+ e.toString());
			System.out.println("error at RunDisappearNode_in_all_tables is :"+ e.toString());
			
			//insert into AUTO_DISCOVERY_LOGS_DETAILS
	   			String logsDetailsid= localgetseqNbr(23);
	   			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
	   			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	   				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at RunDisappearNode_in_all_tables ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
	   				 		
	   			insertLogsDetailsstmt.executeUpdate();
	   			insertLogsDetailsstmt.close();
	   			nbOfErrors++;
		}

	}

	
	private static String GetSN(String nodepk, String tablename,String vdomain,String vvendor) throws SQLException {
	    String vsn = null ;

		Statement stmttype = null;
		String query2 = "select SERIALNUMBER from " + tablename + " where CABINETNO='0' and NODE_PK='"+ nodepk +"' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";      
	    stmttype = con.createStatement();
	    ResultSet rs2 = stmttype.executeQuery(query2);
	         
	    while (rs2.next()) {
	    	try {
	          vsn= rs2.getString("SERIALNUMBER");    
	    	}
	    	catch(Exception e)  
			{  
				logger.info("error at GetSN is :"+ e.toString());
				System.out.println("error at GetSN is :"+ e.toString()); 
				
				//insert into AUTO_DISCOVERY_LOGS_DETAILS
	   			String logsDetailsid= localgetseqNbr(23);
	   			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
	   			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	   				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetSN ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
	   				 		
	   			insertLogsDetailsstmt.executeUpdate();
	   			insertLogsDetailsstmt.close();
	   			nbOfErrors++;
			}
	     }
	     rs2.close();
	     stmttype.close();

		return vsn;
	  }

	
	private static String GetCabinetID(String nodepk, String tablename, String vSN,String vdomain,String vvendor) throws SQLException {
	    String vcab = null ;

		Statement stmttype = null;
		String query2 = "select CABINET_ID from " + tablename + " where CABINETNO='0' and NODE_PK='"+ nodepk +"' and SERIALNUMBER ='"+ vSN +"' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";      
	    stmttype = con.createStatement();
	    ResultSet rs2 = stmttype.executeQuery(query2);
	         
	    while (rs2.next()) {
	    	try {
	    	vcab= rs2.getString("CABINET_ID");    
	    	}
	    	catch(Exception e)  
			{  
				logger.info("error at GetCabinetID is :"+ e.toString());
				System.out.println("error at GetCabinetID is :"+ e.toString()); 
				
				//insert into AUTO_DISCOVERY_LOGS_DETAILS
	   			String logsDetailsid= localgetseqNbr(23);
	   			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
	   			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	   				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetCabinetID ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
	   				 		
	   			insertLogsDetailsstmt.executeUpdate();
	   			insertLogsDetailsstmt.close();
	   			nbOfErrors++;
			}
	     }
	     rs2.close();
	     stmttype.close();

		return vcab;
	  }
	
	private static String GetNodePK(String nodeid, String siteid, String circelid, String nodename,String tablename,String vdomain,String vvendor) throws SQLException {
	    String vnodepk = null ;

		Statement stmttype = null;
		String query2 = "select NODE_PK from " + tablename + " where NODE_ID='"+ nodeid +"' and SITE_ID='"+ siteid +"' and CIRCLE_ID ='"+ circelid +"' and NODE_NAME= '"+ nodename +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";      
	    stmttype = con.createStatement();
	    ResultSet rs2 = stmttype.executeQuery(query2);
	         
	    while (rs2.next()) {
	    	try {
	    	vnodepk= rs2.getString("NODE_PK");    
	    	}
	    	catch(Exception e)  
			{  
				logger.info("error at GetNodePK is :"+ e.toString());
				System.out.println("error at GetNodePK is :"+ e.toString()); 
				
				//insert into AUTO_DISCOVERY_LOGS_DETAILS
	   			String logsDetailsid= localgetseqNbr(23);
	   			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
	   			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	   				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetNodePK ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
	   				 		
	   			insertLogsDetailsstmt.executeUpdate();
	   			insertLogsDetailsstmt.close();
	   			nbOfErrors++;
			}
	     }
	     rs2.close();
	     stmttype.close();

		return vnodepk;
	  }
	
	private static String GetCreationDate(String nodeid, String siteid, String circelid,String nodename, String tablename,String vdomain,String vvendor) throws SQLException {
	    String vcreationdate = null ;

		Statement stmttype = null;
		String query2 = "select CREATION_DATE from " + tablename + " where NODE_ID='"+ nodeid +"' and SITE_ID='"+ siteid +"' and CIRCLE_ID ='"+ circelid +"' and NODE_NAME ='"+ nodename +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";      
	    stmttype = con.createStatement();
	    ResultSet rs2 = stmttype.executeQuery(query2);
	         
	    while (rs2.next()) {
	    	try {
	    	vcreationdate= rs2.getString("CREATION_DATE");  
	    	}
	    	catch(Exception e)  
			{  
				logger.info("error at GetCreationDate is :"+ e.toString());
				System.out.println("error at GetCreationDate is :"+ e.toString()); 
				
				//insert into AUTO_DISCOVERY_LOGS_DETAILS
	   			String logsDetailsid= localgetseqNbr(23);
	   			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
	   			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	   				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetCreationDate ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
	   				 		
	   			insertLogsDetailsstmt.executeUpdate();
	   			insertLogsDetailsstmt.close();
	   			nbOfErrors++;
			}
	     }
	     rs2.close();
	     stmttype.close();

		return vcreationdate;
	  }
	
	private static void GetNodedetail(String vnodepk,String vdomain,String vvendor) throws SQLException {
		Statement stmttype = null;
		Gnodeid="0";
		Gsiteid="0";
		Gcircleid="0";
		String query2 = "select NODE_ID,SITE_ID,CIRCLE_ID from NODE_ACTIVE where NODE_PK='"+ vnodepk +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";      
	    stmttype = con.createStatement();
	    ResultSet rs2 = stmttype.executeQuery(query2);
	         
	    while (rs2.next()) {
	    	try {
	    	Gnodeid= rs2.getString("NODE_ID");  
	    	Gsiteid= rs2.getString("SITE_ID");
	    	Gcircleid= rs2.getString("CIRCLE_ID");
	    	}
	    	catch(Exception e)  
			{  
				logger.info("error at GetNodedetail is :"+ e.toString());
				System.out.println("error at GetNodedetail is :"+ e.toString()); 
				
				//insert into AUTO_DISCOVERY_LOGS_DETAILS
	   			String logsDetailsid= localgetseqNbr(23);
	   			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
	   			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	   				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetNodedetail ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
	   				 		
	   			insertLogsDetailsstmt.executeUpdate();
	   			insertLogsDetailsstmt.close();
	   			nbOfErrors++;
			}
	     }
	     rs2.close();
	     stmttype.close();


	  }
	
	private static int GetCabinetSNstatus(String Snbr,String vdomain,String vvendor) throws SQLException {
	    int rponse = 0 ;

		Statement stmttype = null;
		String query2 = "select CABINET_ID,SERIALNUMBER from NODE_CABINET where SERIALNUMBER='"+ Snbr +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";      
	    stmttype = con.createStatement();
	    ResultSet rs2 = stmttype.executeQuery(query2);
	         
	    while (rs2.next()) {
	    	try {
	    		rponse= 1;  
	    	}
	    	catch(Exception e)  
			{  
				logger.info("error at GetCabinetSNstatus is :"+ e.toString());
				System.out.println("error at GetCabinetSNstatus is :"+ e.toString()); 
				
				//insert into AUTO_DISCOVERY_LOGS_DETAILS
	   			String logsDetailsid= localgetseqNbr(23);
	   			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
	   			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	   				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetCabinetSNstatus ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
	   				 		
	   			insertLogsDetailsstmt.executeUpdate();
	   			insertLogsDetailsstmt.close();
	   			nbOfErrors++;
			}
	     }
	     rs2.close();
	     stmttype.close();

		return rponse;
	  }

	private static int GetBoardSNstatus(String Snbr,String vdomain,String vvendor) throws SQLException {
	    int rponse = 0 ;
		Statement stmttype = null;
		String query2 = "select BOARD_ID,SERIALNUMBER from NODE_BOARD where SERIALNUMBER='"+ Snbr +"' and TRANS_TYPE <> 'Board Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";      
	    stmttype = con.createStatement();
	    ResultSet rs2 = stmttype.executeQuery(query2);
	         
	    while (rs2.next()) {
	    	try {
	    		rponse= 1;  
	    	}
	    	catch(Exception e)  
			{  
				logger.info("error at GetBoardSNstatus is :"+ e.toString());
				System.out.println("error at GetBoardSNstatus is :"+ e.toString()); 
				
				//insert into AUTO_DISCOVERY_LOGS_DETAILS
	   			String logsDetailsid= localgetseqNbr(23);
	   			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
	   			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	   				 		+ "values('"+logsDetailsid+"',sysdate ,'CheckNodeMovement','error at GetBoardSNstatus ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
	   				 		
	   			insertLogsDetailsstmt.executeUpdate();
	   			insertLogsDetailsstmt.close();
	   			nbOfErrors++;
			}
	     }
	     rs2.close();
	     stmttype.close();

		return rponse;
	  }
	
	
	private static int GetAntennaSNstatus(String Snbr,String vdomain,String vvendor) throws SQLException {
	    int rponse = 0 ;
		Statement stmttype = null;
		String query2 = "select ANTENNA_ID,SERIALNUMBER from NODE_ANTENNA where SERIALNUMBER='"+ Snbr +"' and TRANS_TYPE <> 'Disappear' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";      
	    stmttype = con.createStatement();
	    ResultSet rs2 = stmttype.executeQuery(query2);
	         
	    while (rs2.next()) {
	    	try {
	    		rponse= 1;  
	    	}
	    	catch(Exception e)  
			{  
				logger.info("error at GetAntennaSNstatus is :"+ e.toString());
				System.out.println("error at GetAntennaSNstatus is :"+ e.toString()); 
				
				//insert into AUTO_DISCOVERY_LOGS_DETAILS
	   			String logsDetails_id= localgetseqNbr(23);
	   			logsDetails_id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_id;
	   			PreparedStatement insertLogsDetailsstatemenmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	   				 		+ "values('"+logsDetails_id+"',sysdate ,'CheckNodeMovement','error at GetAntennaSNstatus ','','','','','"+vvendor+"','"+vdomain+"','"+logsid+"') ");
	   				 		
	   			insertLogsDetailsstatemenmt.executeUpdate();
	   			insertLogsDetailsstatemenmt.close();
	   			nbOfErrors++;
			}
	     }
	     rs2.close();
	     stmttype.close();

		return rponse;
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
