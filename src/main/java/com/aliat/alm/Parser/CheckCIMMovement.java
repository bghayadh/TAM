package com.aliat.alm.Parser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.lang3.StringUtils;

public class CheckCIMMovement {
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
					String lofilename="CIMMovement-"+dtf.format(now)+".log";
				
				
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
						
						
						
						/// select different domain and vendor from temp node active table
						Statement stmtinit2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
				    	 String sqlStmtinit2 = "select distinct DOMAIN, VENDOR from TEMP_NODE_ACTIVE";          
						    ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
						    rsinit2.last();
					 	    int totalrecinit2 = rsinit2.getRow(); 
					 	   rsinit2.beforeFirst();
					 	   if (totalrecinit2 > 0 ) {
						 		  while (rsinit2.next()) {
									/////////////////////////////////////////////////////////////	
									logger.info("1-Check Reappeared CIM for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									System.out.println("1-Check Reappeared CIM for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));	
									
									GetReappearedCIM(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR")
);
										
									System.out.println("1-Check Reappeared CIM COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									logger.info("1-Check Reappeared CIM COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));	
									/////////////////////////////////////////////////////////////
									logger.info("2-Check Disappeared CIM for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									System.out.println("2-Check Disappeared CIM for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));	
										
									stmt0 = con.createStatement();   
									// read all child_id means all node_id from child_parent table  tables
									String sqlStmt0 = "select CHILD_ID_1 from (select distinct CHILD_ID_1,CHILD_NAME_1,CHILD_TYPE FROM NODE_CHILD_PARENT where TRANS_TYPE <> 'Disappeared CIM' and ACTIVE_RECORD='1' and DOMAIN='" + rsinit2.getString("DOMAIN") +"' and VENDOR='" + rsinit2.getString("VENDOR") +"')";          
									ResultSet rs0 = stmt0.executeQuery(sqlStmt0);
									while (rs0.next()) {
										GetDisappearedCIM(rs0.getString("CHILD_ID_1"),rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR")
);
									}
									rs0.close();
									stmt0.close();
										
									System.out.println("2-Check Disappeared CIM COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									logger.info("2-Check Disappeared CIM COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));	
									/////////////////////////////////////////////////////////////	
									logger.info("3-Check CIM Movement for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									System.out.println("3-Check CIM Movement for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									
									stmt0 = con.createStatement();   
									// read all child_id means all node_id from temp_child_parent table  tables
									sqlStmt0 = "select CHILD_ID_1 from (select distinct CHILD_ID_1,CHILD_NAME_1,CHILD_TYPE FROM TEMP_NODE_CHILD_PARENT where DOMAIN='" + rsinit2.getString("DOMAIN") +"' and VENDOR='" + rsinit2.getString("VENDOR") +"')";          
									rs0 = stmt0.executeQuery(sqlStmt0);
									while (rs0.next()) {
										GetCIMmovementfromtemptable(rs0.getString("CHILD_ID_1"),rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR")
);
									}
									rs0.close();
									stmt0.close();
									
									System.out.println("3-Check CIM Movement  COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
									logger.info("3-Check CIM Movement COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
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
	
	private static void GetCIMmovementfromtemptable(String vchildid,String vdomain,String vvendor) throws SQLException  {
		Statement stmt1 = null;
		Statement stmt2 = null;
		PreparedStatement stmtinsert1=null;
		PreparedStatement stmtinsert2=null;
		PreparedStatement stmtinsert3=null;
		int totalrec=0;
		
		stmt1 = con.createStatement();   
	     // read all child_id means all node_id from temp_child_parent table  tables
	    String sqlStmt = "select ID,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME from  TEMP_NODE_CHILD_PARENT  where CHILD_ID_1='" + vchildid + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
	    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
	    while (rs1.next()) {
	    	
	    	//compare with final  CHILD_ID,CHILD_NAME,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME
	    	stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
	    	
	    	String sqlStmt2 = "select ID,CHILD_ID_1,CHILD_NAME_1,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME from  NODE_CHILD_PARENT  where CHILD_ID_1='" + rs1.getString("CHILD_ID_1") + "'  and CHILD_NAME_1='" + rs1.getString("CHILD_NAME_1") + "' and CHILD_TYPE='" + rs1.getString("CHILD_TYPE") + "' and CHILD_MODEL='" + rs1.getString("CHILD_MODEL") + "' and PARENT_ID='" + rs1.getString("PARENT_ID") + "' and PARENT_NAME='" + rs1.getString("PARENT_NAME") + "' and PARENT_TYPE='" + rs1.getString("PARENT_TYPE") + "' and PARENT_MODEL='" + rs1.getString("PARENT_MODEL") + "' and FILE_NAME='" + rs1.getString("FILE_NAME") + "' and TRANS_TYPE <> 'Disappeared CIM' and ACTIVE_RECORD ='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
		    ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
		    rs2.last();
	 	    totalrec = rs2.getRow(); 
	 	    rs2.beforeFirst();
	 	   if (totalrec >0 ) {
	 		  while (rs2.next()) {
	 				// record found in final update LAST_MODIFIED_DATE
		 			 stmtinsert1 = con.prepareStatement("update  NODE_CHILD_PARENT set LAST_MODIFIED_DATE =sysdate where ID= '"+ rs2.getString("ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
		    		 stmtinsert1.executeUpdate();
		    		 stmtinsert1.close();
			    }
	 	   }else {
	 		   // record not found in final add it 
	 		  String transid=Gyear+"_"+ "CHILDPARENT"+"_"+localgetseqNbr(21);
			  String childid=Gyear+"_"+ "CHILDPARENT"+"_"+localgetseqNbr(22);
			  String vdetails=rs1.getString("CHILD_NAME_1")+":"+ rs1.getString("CHILD_TYPE")+":"+ rs1.getString("CHILD_MODEL");
			  
			  stmtinsert3 = con.prepareStatement("insert into  NODE_CHILD_PARENT_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
		 			 		+  "values ('"+ transid  +"',sysdate, 'NEW RECORD','NEW RECORD','" + rs1.getString("CHILD_ID_1") + "','0','0','0','0','0','0','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','0','0','0')  ");
			stmtinsert3.executeUpdate();
			stmtinsert3.close();
			  
	 		 stmtinsert2 = con.prepareStatement("insert into  NODE_CHILD_PARENT (ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,TRANS_SOURCE,FROM_TRANS_ID,TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR) "
		 			 		+  "values ('"+ childid  +"',sysdate, sysdate,'" + rs1.getString("CHILD_ID_1") + "','"+ rs1.getString("CHILD_NAME_1") +"','" + rs1.getString("CHILD_ID_2") + "','"+ rs1.getString("CHILD_NAME_2") +"','"+ rs1.getString("CHILD_TYPE") +"','"+ rs1.getString("CHILD_MODEL") +"','"+ rs1.getString("PARENT_ID") +"','"+ rs1.getString("PARENT_NAME") +"','"+ rs1.getString("PARENT_TYPE") +"','"+ rs1.getString("PARENT_MODEL") +"','"+ rs1.getString("FILE_NAME") +"','CHILDPARENT','"+ transid +"','0','NEW RECORD','1','" + vdomain +"','" + vvendor +"')  ");
	 		 stmtinsert2.executeUpdate();
	 		 stmtinsert2.close();
	 	   }
		    rs2.close();
		    stmt2.close();
	    }
	    rs1.close();
	    stmt1.close();
	    
	   
	}

	private static void GetDisappearedCIM(String vchildid,String vdomain,String vvendor) throws SQLException  {
		Statement stmt1 = null;
		Statement stmt2 = null;
		PreparedStatement stmtinsert1=null;
		PreparedStatement stmtinsert2=null;
		PreparedStatement stmtinsert3=null;
		int totalrec=0;
		
		stmt1 = con.createStatement();   
	     // read all child_id means all node_id from child_parent table  tables
	    String sqlStmt = "select ID,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME from  NODE_CHILD_PARENT  where CHILD_ID_1='" + vchildid + "'  and TRANS_TYPE <> 'Disappeared CIM' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
	    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
	    while (rs1.next()) {
	    	
	    	//compare with temp  CHILD_ID,CHILD_NAME,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME
	    	stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
	    	String sqlStmt2 = "select ID,CHILD_ID_1,CHILD_NAME_1,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME from  TEMP_NODE_CHILD_PARENT  where CHILD_ID_1='" + rs1.getString("CHILD_ID_1") + "'  and CHILD_NAME_1='" + rs1.getString("CHILD_NAME_1") + "' and CHILD_TYPE='" + rs1.getString("CHILD_TYPE") + "' and CHILD_MODEL='" + rs1.getString("CHILD_MODEL") + "' and PARENT_ID='" + rs1.getString("PARENT_ID") + "' and PARENT_NAME='" + rs1.getString("PARENT_NAME") + "' and PARENT_TYPE='" + rs1.getString("PARENT_TYPE") + "' and PARENT_MODEL='" + rs1.getString("PARENT_MODEL") + "' and FILE_NAME='" + rs1.getString("FILE_NAME") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
		    ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
		    rs2.last();
	 	    totalrec = rs2.getRow(); 
	 	    rs2.beforeFirst();
	 	   if (totalrec >0 ) {
	 		  while (rs2.next()) {
	 				//nothing to do found in final and temp
			    }
	 	   }else {
	 		   // record not found in final add it 
	 		  String transid=Gyear+"_"+ "CHILDPARENT"+"_"+localgetseqNbr(21);
			  String childid=Gyear+"_"+ "CHILDPARENT"+"_"+localgetseqNbr(22);
			  String vdetails=rs1.getString("CHILD_NAME_1")+":"+ rs1.getString("CHILD_TYPE")+":"+ rs1.getString("CHILD_MODEL");
			  
			  stmtinsert3 = con.prepareStatement("insert into  NODE_CHILD_PARENT_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
		 			 		+  "values ('"+ transid  +"',sysdate, 'Disappeared CIM','Disappeared CIM','" + rs1.getString("CHILD_ID_1") + "','0','0','0','0','0','0','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','0','0','0')  ");
			stmtinsert3.executeUpdate();
			stmtinsert3.close();
			  
			// update LAST_MODIFIED_DATE, transaction and active record in final
			 stmtinsert1 = con.prepareStatement("update  NODE_CHILD_PARENT set LAST_MODIFIED_DATE =sysdate,TRANS_ID = '" + transid + "',ACTIVE_RECORD='0' where ID= '"+ rs1.getString("ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
			 stmtinsert1.executeUpdate();
			 stmtinsert1.close();
			
	 		 stmtinsert2 = con.prepareStatement("insert into  NODE_CHILD_PARENT (ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,TRANS_SOURCE,FROM_TRANS_ID,TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR) "
		 			 		+  "values ('"+ childid  +"',sysdate, sysdate,'" + rs1.getString("CHILD_ID_1") + "','"+ rs1.getString("CHILD_NAME_1") +"','" + rs1.getString("CHILD_ID_2") + "','"+ rs1.getString("CHILD_NAME_2") +"','"+ rs1.getString("CHILD_TYPE") +"','"+ rs1.getString("CHILD_MODEL") +"','"+ rs1.getString("PARENT_ID") +"','"+ rs1.getString("PARENT_NAME") +"','"+ rs1.getString("PARENT_TYPE") +"','"+ rs1.getString("PARENT_MODEL") +"','"+ rs1.getString("FILE_NAME") +"','CHILDPARENT','"+ transid +"','0','Disappeared CIM','1','" + vdomain +"','" + vvendor +"')  ");
	 		 stmtinsert2.executeUpdate();
	 		 stmtinsert2.close();
	 	   }
		    rs2.close();
		    stmt2.close();
	    }
	    rs1.close();
	    stmt1.close();
	    
	   
	}
	
	private static void GetReappearedCIM(String vdomain,String vvendor) throws SQLException  {
		Statement stmt1 = null;
		Statement stmt2 = null;
		PreparedStatement stmtinsert1=null;
		PreparedStatement stmtinsert2=null;
		PreparedStatement stmtinsert3=null;
		int totalrec=0;
		
		stmt1 = con.createStatement();   
	     // read all child_id means all node_id from child_parent table having trans_type = Disappeared CIM and active_record=1 
	    String sqlStmt = "select ID,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME from  NODE_CHILD_PARENT  where TRANS_TYPE='Disappeared CIM'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
	    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
	    while (rs1.next()) {
	    	
	    	stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
	    	String sqlStmt2 = "select ID,CHILD_ID_1,CHILD_NAME_1,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME from  TEMP_NODE_CHILD_PARENT  where CHILD_ID_1='" + rs1.getString("CHILD_ID_1") + "'  and CHILD_NAME_1='" + rs1.getString("CHILD_NAME_1") + "' and CHILD_TYPE='" + rs1.getString("CHILD_TYPE") + "' and CHILD_MODEL='" + rs1.getString("CHILD_MODEL") + "' and PARENT_ID='" + rs1.getString("PARENT_ID") + "' and PARENT_NAME='" + rs1.getString("PARENT_NAME") + "' and PARENT_TYPE='" + rs1.getString("PARENT_TYPE") + "' and PARENT_MODEL='" + rs1.getString("PARENT_MODEL") + "' and FILE_NAME='" + rs1.getString("FILE_NAME") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
		    ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
		    rs2.last();
	 	    totalrec = rs2.getRow(); 
	 	    rs2.beforeFirst();
	 	   if (totalrec >0 ) {// if we found it we will add it to CIM 
	 		  while (rs2.next()) {
	 			  
	 			 // record not found in final add it 
		 		  String transid=Gyear+"_"+ "CHILDPARENT"+"_"+localgetseqNbr(21);
				  String childid=Gyear+"_"+ "CHILDPARENT"+"_"+localgetseqNbr(22);
				  String vdetails=rs1.getString("CHILD_NAME_1")+":"+ rs1.getString("CHILD_TYPE")+":"+ rs1.getString("CHILD_MODEL");
				  
				  stmtinsert3 = con.prepareStatement("insert into  NODE_CHILD_PARENT_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
			 			 		+  "values ('"+ transid  +"',sysdate, 'Reappeared CIM','Reappeared CIM','" + rs1.getString("CHILD_ID_1") + "','0','0','0','0','0','0','0','0','0','"+ vdetails +"','0',sysdate,'0','0','1','0','0','0')  ");
				stmtinsert3.executeUpdate();
				stmtinsert3.close();
				  
				// update LAST_MODIFIED_DATE, transaction and active record in final
				 stmtinsert1 = con.prepareStatement("update  NODE_CHILD_PARENT set LAST_MODIFIED_DATE =sysdate,TRANS_ID = '" + transid + "',ACTIVE_RECORD='0' where ID= '"+ rs1.getString("ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				 stmtinsert1.executeUpdate();
				 stmtinsert1.close();
				
		 		 stmtinsert2 = con.prepareStatement("insert into  NODE_CHILD_PARENT (ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,TRANS_SOURCE,FROM_TRANS_ID,TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR) "
			 			 		+  "values ('"+ childid  +"',sysdate, sysdate,'" + rs1.getString("CHILD_ID_1") + "','"+ rs1.getString("CHILD_NAME_1") +"','" + rs1.getString("CHILD_ID_2") + "','"+ rs1.getString("CHILD_NAME_2") +"','"+ rs1.getString("CHILD_TYPE") +"','"+ rs1.getString("CHILD_MODEL") +"','"+ rs1.getString("PARENT_ID") +"','"+ rs1.getString("PARENT_NAME") +"','"+ rs1.getString("PARENT_TYPE") +"','"+ rs1.getString("PARENT_MODEL") +"','"+ rs1.getString("FILE_NAME") +"','CHILDPARENT','"+ transid +"','0','Reappeared CIM','1','" + vdomain +"','" + vvendor +"')  ");
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
