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


public class CheckBoardMovement_WIN {
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
	static String logsid="0";
	
	public static void main(String[] args) throws SQLException {
		 BufferedReader objReader = null;
		 Statement stmt0 = null;
		 Statement rstmt0 = null;
		 
		 try	{
		    	
				objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almconfig.dat"));
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
					String lofilename="CheckBoardMovement-"+dtf.format(now)+".log";
				
				
					logger = Logger.getLogger("MyLog"); 
					logger.setUseParentHandlers(false);
					
					// This block configure the logger with handler and formatter  and PATH
			        fh = new FileHandler(logpath+"\\"+lofilename);
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
						
		 } // end main try 
			catch(Exception e){
			      System.err.println(e);
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
					
					logger.info("1-Check Disappeared Board for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") );
					System.out.println("1-Check Disappeared Board for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
					
					//insert into AUTO_DISCOVERY_LOGS_DETAILS
					String logsDetailsid= localgetseqNbr(23);
					logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
					PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
 					 		+ "values('"+logsDetailsid+"',sysdate ,'CheckBoardMovement','1-Check Disappeared Board for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
 					 		
					insertLogsDetailsstmt.executeUpdate();
					insertLogsDetailsstmt.close();
					
					GetDisappearingBoards(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
					
					logger.info("1-Check Disappeared Board COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") );
					System.out.println("1-Check Disappeared Board COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
					
					//insert into AUTO_DISCOVERY_LOGS_DETAILS
					String logsDetails_id= localgetseqNbr(23);
					logsDetails_id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_id;
					PreparedStatement insertLogsDetails_stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
 					 		+ "values('"+logsDetails_id+"',sysdate ,'CheckBoardMovement','1-Check Disappeared Board COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Compeleted','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
 					 		
					insertLogsDetails_stmt.executeUpdate();
					insertLogsDetails_stmt.close();
					
					///////////////////////////////////////////////////////////////////////
						///////////////////////////////////////////////////////////////////////
											
						logger.info("2-Check if we have reappearing Board SN after Disappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						System.out.println("2-Check if we have reappearing Board SN after Disappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						
						//insert into AUTO_DISCOVERY_LOGS_DETAILS
						String logsDetails_Id= localgetseqNbr(23);
						logsDetails_Id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_Id;
						PreparedStatement insertLogsDetails_Stmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDetails_Id+"',sysdate ,'CheckBoardMovement','2-Check if we have reappearing Board SN after Disappearing for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
	 					 		
						insertLogsDetails_Stmt.executeUpdate();
						insertLogsDetails_Stmt.close();
						
						// check all SN reappeared (if same node SN maintenance /Board movement  or different node SN transferred to other node /site/circle) 
						
						GetReappearingBoardSN(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
						
						logger.info("2-Check Reappearing Board SN after Disappearing COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						System.out.println("2-Check Reappearing Board SN after Disappearing COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						
						//insert into AUTO_DISCOVERY_LOGS_DETAILS
						String logsDetails_ID= localgetseqNbr(23);
						logsDetails_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetails_ID;
						PreparedStatement insertLogsDetails_statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDetails_ID+"',sysdate ,'CheckBoardMovement','2-Check Reappearing Board SN after Disappearing COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Compeleted','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
	 					 		
						insertLogsDetails_statement.executeUpdate();
						insertLogsDetails_statement.close();
						
						///////////////////////////////////////////////////////////////////////
					
						
						logger.info("3-Check Board movement for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") );
						System.out.println("3-Check Board movement for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						
						//insert into AUTO_DISCOVERY_LOGS_DETAILS
						String logsDetailsID= localgetseqNbr(23);
						logsDetailsID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsID;
						PreparedStatement insertLogsDetails_Statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDetailsID+"',sysdate ,'CheckBoardMovement','3-Check Board movement for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
	 					 		
						insertLogsDetails_Statement.executeUpdate();
						insertLogsDetails_Statement.close();
						
						rstmt0 = con.createStatement();   
						// Get distinct NODE_PK,NODE_ID,SITE_ID,CIRCLE_ID  from temp_NODE_ACTIVE
						String sqlStmt1 = "select distinct  NODE_PK,NODE_ID,SITE_ID,CIRCLE_ID,NODE_NAME from temp_node_active  where TRANS_TYPE <> 'Node Disappeared' and  ACTIVE_RECORD='1' and DOMAIN='" + rsinit2.getString("DOMAIN") +"' and VENDOR='" + rsinit2.getString("VENDOR") +"'";          
						ResultSet rrs1 = rstmt0.executeQuery(sqlStmt1);
						while (rrs1.next()) {
						// try {
						// Validate Board Movment
						CheckBoarMovment(rrs1.getString("NODE_PK"),rrs1.getString("NODE_ID"),rrs1.getString("SITE_ID"),rrs1.getString("CIRCLE_ID"),rrs1.getString("NODE_NAME"),rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
						
						// }
						//catch(Exception e)  
						//{  
						//logger.info("error at CheckBoarMovment is :"+ e.toString());
						//System.out.println("error at CheckBoarMovment is :"+ e.toString()); 
						//}
						}
						rrs1.close();
						rstmt0.close();
						
						/// validate if we have rwos in final not in temp pust active_record =0 and set status to baord disappear
						SetAllBoardAbortedToZero(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));
						
						logger.info("3-Check Board movement COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						System.out.println("3-Check Board movement COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						
						//insert into AUTO_DISCOVERY_LOGS_DETAILS
						String logsDetailsId= localgetseqNbr(23);
						logsDetailsId=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsId;
						PreparedStatement insertLogsDEtails_Statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
	 					 		+ "values('"+logsDetailsId+"',sysdate ,'CheckBoardMovement','3-Check Board movement COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','Compeleted','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
	 					 		
						insertLogsDEtails_Statement.executeUpdate();
						insertLogsDEtails_Statement.close();
						
						///////////////////////////////////////////////////////////////////////
						
						
						//update all reords having Board Transfer to another Node After Disappear to 0 if reappear with same node sn and slot
						Statement stmttrsnfr = null;
						Statement stmttrsnfr2 = null;
						stmttrsnfr = con.createStatement(); 
						PreparedStatement stmtinsert=null;
						String sqlStmttrsnfr = "select * from NODE_BOARD where TRANS_TYPE='Board Transfer to another Node After Disappear' and ACTIVE_RECORD='1' and DOMAIN='" + rsinit2.getString("DOMAIN") +"' and VENDOR='" + rsinit2.getString("VENDOR") +"'";          
						ResultSet rstrsnfr = stmttrsnfr.executeQuery(sqlStmttrsnfr);
						while (rstrsnfr.next()) {
						stmttrsnfr2 = con.createStatement(); 
						String sqlStmttrsnfr2 = "select * from NODE_BOARD where SERIALNUMBER='" + rstrsnfr.getString("SERIALNUMBER") + "' and CABINETNO ='" + rstrsnfr.getString("CABINETNO") + "' and SLOTNO='" + rstrsnfr.getString("SLOTNO") + "' and SUBRACKNO='" + rstrsnfr.getString("SUBRACKNO") + "' and RACKNO ='" + rstrsnfr.getString("RACKNO") + "' and SLOTPOS='" + rstrsnfr.getString("SLOTPOS") + "' and SUBSLOTNO ='" + rstrsnfr.getString("SUBSLOTNO") + "' and ACTIVE_RECORD='1' and NODE_PK='" + rstrsnfr.getString("NODE_PK") + "' and TRANS_TYPE <> 'Board Transfer to another Node After Disappear' and DOMAIN='" + rsinit2.getString("DOMAIN") +"' and VENDOR='" + rsinit2.getString("VENDOR") +"'";          
						ResultSet rstrsnfr2 = stmttrsnfr2.executeQuery(sqlStmttrsnfr2);
						while (rstrsnfr2.next()) {
						
						stmtinsert = con.prepareStatement("update NODE_BOARD Set UPDATE_DATE=sysdate, ACTIVE_RECORD='0' where  BOARD_ID ='" + rstrsnfr.getString("BOARD_ID") + "' and DOMAIN='" + rsinit2.getString("DOMAIN") +"' and VENDOR='" + rsinit2.getString("VENDOR") +"'");
						stmtinsert.executeUpdate();
						stmtinsert.close();
						}
						rstrsnfr2.close();
						stmttrsnfr2.close();
	
						
						}
						rstrsnfr.close();
						stmttrsnfr.close();
					
						//insert into AUTO_DISCOVERY_LOGS
					 	  PreparedStatement insertLogsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS (LOGS_ID,START_TIME,ACTIVITY_NAME,VENDOR,DOMAIN,STOP_TIME) "
							 		+ "values('"+logsid+"', ? ,'CheckBoardMovement','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"',?) ");
							 	
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
	
	private static void SetAllBoardAbortedToZero(String vdomain, String vvendor) throws SQLException  {
		Statement stmt1 = null;
		Statement stmt2 = null;
		PreparedStatement stmtinsert1=null;

	 	stmt1 = con.createStatement();   
	    String sqlStmt = "(select a.LINE, a.SERIALNUMBER,b.NODE_ID,b.NODE_NAME,b.SITE_ID,b.CIRCLE_ID from NODE_BOARD a, NODE_ACTIVE b where a.NODE_PK=b.NODE_PK and b.ACTIVE_RECORD='1' and a.ACTIVE_RECORD='1' and a.TRANS_TYPE not in ('Board Transfer to another Node After Disappear') and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"')\r\n" + 
	    		"minus\r\n" + 
	    		"(select a.LINE, a.SERIALNUMBER,b.NODE_ID,b.NODE_NAME,b.SITE_ID,b.CIRCLE_ID from TEMP_NODE_BOARD a, TEMP_NODE_ACTIVE b where a.NODE_PK=b.NODE_PK and b.ACTIVE_RECORD='1' and a.ACTIVE_RECORD='1' and a.TRANS_TYPE not in ('Board Transfer to another Node After Disappear') and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"')";          
	    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
	    while (rs1.next()) {
	    	
	    	//Get Board_id
	    	stmt2 = con.createStatement();   
		    String sqlStmt2 = "select a.BOARD_ID  from NODE_BOARD a,NODE_ACTIVE b where a.NODE_PK=b.NODE_PK and a.SERIALNUMBER ='" + rs1.getString("SERIALNUMBER") + "' and b.NODE_ID='" + rs1.getString("NODE_ID") + "' and b.NODE_NAME='" + rs1.getString("NODE_NAME") + "' and b.SITE_ID='" + rs1.getString("SITE_ID") + "' and b.CIRCLE_ID='" + rs1.getString("CIRCLE_ID") + "' and a.LINE='" + rs1.getString("LINE") + "' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";
		    ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
		    while (rs2.next()) {
		    	
		    	stmtinsert1 = con.prepareStatement("update NODE_BOARD Set UPDATE_DATE=sysdate, ACTIVE_RECORD='0',TRANS_TYPE='Board Disappeared' where BOARD_ID ='" + rs2.getString("BOARD_ID") + "' and TRANS_TYPE <>'Board Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
			 	stmtinsert1.executeUpdate();
			 	stmtinsert1.close();
		    }
		    rs2.close();
		    stmt2.close();
	    }
	    rs1.close();
	    stmt1.close();
	    
	


	}

	private static void CheckBoarMovment(String nodepk, String nodeid,String siteid,String circleid,String nodename,String vdomain, String vvendor) throws SQLException  {
		Statement stmt1 = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		String crdate= null;
		String tmpcrdate= null;
		String vnodepk=null;
		String vnodeatrpk=null;
		int totalrec=0;
		String stempslot="0";
		String sfinalslot="0";
		String stempsn="0";
		String sfinalsn="0";
		
		//Get nodepk from node_baord based on nodeid siteid circelid comes from temp_node_borad
		vnodepk=GetNodePK(nodeid,siteid,circleid,nodename,"NODE_ACTIVE",vdomain,vvendor);
		//Get nodepk from node_baord based on vnodepk comes from node_board above
		vnodeatrpk=GetNodePKATTRIBUTE(vnodepk,"NODE_BOARD",vdomain,vvendor);
		
		stmt1 = con.createStatement();   
	    String sqlStmt = "select BOARD_ID,CABINETNO,SUBRACKNO,RACKNO,SLOTNO,SLOTPOS,SUBSLOTNO,SERIALNUMBER,UPDATE_DATE,NODE_PK,NODE_ATTR_PK,LINE,CREATION_DATE  from TEMP_NODE_BOARD where  ACTIVE_RECORD='1'  and NODE_PK ='"+ nodepk +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
	    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
	    while (rs1.next()) {
	    	stempsn=rs1.getString("SERIALNUMBER");
	    	stempslot=rs1.getString("CABINETNO") +"-"+ rs1.getString("SUBRACKNO") +"-"+  rs1.getString("RACKNO")  +"-"+ rs1.getString("SLOTNO") +"-"+ rs1.getString("SLOTPOS") +"-"+ rs1.getString("SUBSLOTNO");
	    	tmpcrdate=rs1.getString("CREATION_DATE");
	    	
	    	// check if we have record in node_board having same SN , slot position and line , if yes validate SN and position
	    	// if have same SN and position and line then update update_date field
	    	// if diff SN only then replacement SN
	    	//if diff position only then Board movement 
	    	//if diff SN and Position then New Hardware with New SN and Position
	    	// if not found in node_board means it is a new board to be added in node_board
	    	
	    	stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    	String sqlStmt2 = "select BOARD_ID,CABINETNO,SUBRACKNO,RACKNO,SLOTNO,SLOTPOS,SUBSLOTNO,SERIALNUMBER,NODE_PK,NODE_ATTR_PK,TRANS_TYPE,LINE,CREATION_DATE  from NODE_BOARD where  ACTIVE_RECORD='1' and LINE='" + rs1.getString("LINE") +"' and NODE_PK ='"+ vnodepk +"' and TRANS_TYPE <> 'Board Transfer to another Node After Disappear' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
		    ResultSet rs2 = stmt2.executeQuery(sqlStmt2);
		    rs2.last();
     	    totalrec = rs2.getRow(); 
     	    rs2.beforeFirst();
     	    if (totalrec >0 ) {// if board found in final table
     	    	while (rs2.next()) {
     	    		crdate=rs2.getString("CREATION_DATE");
     	    		sfinalsn=rs2.getString("SERIALNUMBER");
     	    		sfinalslot=rs2.getString("CABINETNO") +"-"+ rs2.getString("SUBRACKNO") +"-"+  rs2.getString("RACKNO")  +"-"+ rs2.getString("SLOTNO") +"-"+ rs2.getString("SLOTPOS") +"-"+ rs2.getString("SUBSLOTNO");
     	    		
     	    		if (StringUtils.equalsIgnoreCase (stempsn,sfinalsn)) { // if SN are both the same 
     	    			
     	    			if (StringUtils.equalsIgnoreCase (stempslot,sfinalslot)) {// if position are both the same 
     	    				// Same SN and position only update update_date field
     	    				System.out.println("Same SN and Position");
     	    				PreparedStatement stmtinsert2=null;
				    		stmtinsert2 = con.prepareStatement("update NODE_BOARD Set UPDATE_DATE=sysdate where BOARD_ID ='"+ rs2.getString("BOARD_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				    	  	stmtinsert2.executeUpdate();
				    	 	stmtinsert2.close();
     	    			} else {
     	    			// Same SN and different position Board Movement
     	    				System.out.println("Same SN and different Position , Board Movement");
     	    				UpdateBoardMovement(rs1.getString("UPDATE_DATE"),"Board Movement","Position Changed",siteid,siteid,"0","0",circleid,circleid,nodeid,nodeid,sfinalslot,stempslot,"0","0","0",rs1.getString("SERIALNUMBER"),"1",vnodepk,vnodeatrpk,rs1.getString("SERIALNUMBER"),rs1.getString("LINE"),rs2.getString("BOARD_ID"),rs1.getString("BOARD_ID"),crdate,vdomain,vvendor);
     	    			}
     	    			
     	    		} else { // SN are not the same
     	    			
     	    			if (StringUtils.equalsIgnoreCase (stempsn,"0") && StringUtils.equalsIgnoreCase (rs2.getString("TRANS_TYPE"),"Board Disappeared") ) { //if SN final disappeared and SN temp =0 do nothing 
	     	    			// as long SNfianl <>0 and SNtemp =0 then do nothing
     	    			} else {//end if stempslo="0" && StringUtils.equalsIgnoreCase (rs2.getString("TRANS_TYPE"),"Board Disappeared")

     	    				if (StringUtils.equalsIgnoreCase (stempslot,sfinalslot)) {// if position are both the same 
	     	    				// Different SN and Same position , replacement SN
	     	    				System.out.println("Different SN and Same Position, Repalcement SN");
	     	    				UpdateSNMovement(rs1.getString("UPDATE_DATE"),"Replacement SN","SN Changed",siteid,siteid,"0","0",circleid,circleid,nodeid,nodeid,sfinalslot,stempslot,"0","0","0",sfinalsn,"1",vnodepk,vnodeatrpk,stempsn,rs1.getString("LINE"),rs2.getString("BOARD_ID"),rs1.getString("BOARD_ID"),crdate,vdomain,vvendor);
	     	    			} else {
	     	    			// Different SN and Different position ,New Hardware
	     	    				if (StringUtils.equalsIgnoreCase (stempsn,"0")) {
	     	    					System.out.println("Different SN and Different position, SN not Found it is a New Hardware");
	     	    	     	    	NewHardwareMovement(rs1.getString("UPDATE_DATE"),"New Hardware","SN and Position Changed",siteid,siteid,"0","0",circleid,circleid,nodeid,nodeid,sfinalslot,stempslot,"0","0","0",sfinalsn,"1",vnodepk,vnodeatrpk,stempsn,rs1.getString("LINE"),rs2.getString("BOARD_ID"),rs1.getString("BOARD_ID"),crdate,vdomain,vvendor);
	     	    				}else {
	     	    					//verify if SN exist or not if exist put Existed Hardware if not New Hardware
		     	    				stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		     	    				String sqlStmt3 = "select BOARD_ID,NODE_PK  from NODE_BOARD where  SERIALNUMBER ='" + stempsn + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";
		     	    				 ResultSet rs3 = stmt3.executeQuery(sqlStmt3);
		     	    			    rs3.last();
		     	    	     	    int totalrec3 = rs3.getRow(); 
		     	    	     	    if (totalrec3 >0 ) {
		     	    	     	    	System.out.println("Different SN and Different position, SN Found it is an Existed Hardware");
		     	    	     	    	NewHardwareMovement(rs1.getString("UPDATE_DATE"),"Existed Hardware","SN and Position Changed",siteid,siteid,"0","0",circleid,circleid,nodeid,nodeid,sfinalslot,stempslot,"0","0","0",sfinalsn,"1",vnodepk,vnodeatrpk,stempsn,rs1.getString("LINE"),rs2.getString("BOARD_ID"),rs1.getString("BOARD_ID"),crdate,vdomain,vvendor);
		     	    	     	    }else {
		     	    	     	    	System.out.println("Different SN and Different position, SN not Found it is a New Hardware");
		     	    	     	    	NewHardwareMovement(rs1.getString("UPDATE_DATE"),"New Hardware","SN and Position Changed",siteid,siteid,"0","0",circleid,circleid,nodeid,nodeid,sfinalslot,stempslot,"0","0","0",sfinalsn,"1",vnodepk,vnodeatrpk,stempsn,rs1.getString("LINE"),rs2.getString("BOARD_ID"),rs1.getString("BOARD_ID"),crdate,vdomain,vvendor);
		     	    	     	    }
		     	    	     	    rs3.close();
		     	       		    	stmt3.close();
	     	    				}
	     	    				
	     	    			   
	     	       		    	
	     	    				
	     	    				
	     	    			}

     	    			}
     	    			
     	    		} // end Else SN  are not the same
     	    		
     	    		
     	    		
     	    	}
     	    	rs2.close();
     		    stmt2.close();
     	    } else {// board not found in node_board to be added as New Board
     	    	
     	    	if (StringUtils.equalsIgnoreCase (stempsn,"0")) {
     	    		System.out.println("New Board");
 	     	    	addNewToNodeBoardTransactions(rs1.getString("UPDATE_DATE"),"New Board","New Board",siteid,siteid,"0","0",circleid,circleid,nodeid,nodeid,"0","0","0","0","0",rs1.getString("SERIALNUMBER"),"1",vnodepk,vnodeatrpk,rs1.getString("SERIALNUMBER"),rs1.getString("LINE"),rs1.getString("BOARD_ID"),tmpcrdate,vdomain,vvendor);
     	    	} else {
     	    		//verify if SN exist or not if exist put Existed Hardware if not New Hardware
     				stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
     				String sqlStmt3 = "select BOARD_ID,NODE_PK  from NODE_BOARD where  SERIALNUMBER ='" + stempsn + "'  and TRANS_TYPE <> 'Board Disappeared' and ACTIVE_RECORD <> '0' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";
     				 ResultSet rs3 = stmt3.executeQuery(sqlStmt3);
     			    rs3.last();
     	     	    int totalrec3 = rs3.getRow(); 
     	     	    if (totalrec3 >0 ) {
     	     	    	System.out.println("Existed Hardware");
     	     	    	addNewToNodeBoardTransactions(rs1.getString("UPDATE_DATE"),"Existed Hardware","SN Board Existed",siteid,siteid,"0","0",circleid,circleid,nodeid,nodeid,"0","0","0","0","0",rs1.getString("SERIALNUMBER"),"1",vnodepk,vnodeatrpk,rs1.getString("SERIALNUMBER"),rs1.getString("LINE"),rs1.getString("BOARD_ID"),tmpcrdate,vdomain,vvendor);
     	     	    }else {
     	     	    	System.out.println("New Board");
     	     	    	addNewToNodeBoardTransactions(rs1.getString("UPDATE_DATE"),"New Board","New Board",siteid,siteid,"0","0",circleid,circleid,nodeid,nodeid,"0","0","0","0","0",rs1.getString("SERIALNUMBER"),"1",vnodepk,vnodeatrpk,rs1.getString("SERIALNUMBER"),rs1.getString("LINE"),rs1.getString("BOARD_ID"),tmpcrdate,vdomain,vvendor);
     	     	    }
     	     	  rs3.close();
     	 	      stmt3.close();
     	    	}
     	    	
     	    }
	    	
	    }
	    rs1.close();
	    stmt1.close();
	}

	private static void addNewToNodeBoardTransactions(String vdate,String moveaction,String movedescrip, String vfromsite,String vtosite,String vfromtech,String vtotech,String vfromcircle,String vtocircle,String vfromnode,String vtonode,String fromslot, String toslot, String vnotes, String valm, String vupdbyalm, String vSN,String vsreqalm,String vnodepk,String vnodeatrpk,String vtoSN,String vline,String tmpboardid,String crdate,String vdomain,String vvendor) throws SQLException  {
	 	PreparedStatement stmtinsert1=null;
	 	PreparedStatement stmtinsert2=null;
	 	String transid=Gyear+"_"+ "BOARD"+"_"+localgetseqNbr(21);
	 	//Add to NODE_BOARD_TRANSACTIONS
	 	stmtinsert1 = con.prepareStatement("insert into  NODE_BOARD_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
	 			+ "values ('"+ transid  +"',TIMESTAMP '"+ vdate +"', '"+ moveaction +"','"+ movedescrip +"','"+ vfromsite +"','"+ vtosite +"','"+ vfromtech +"','"+ vtotech +"','"+ vfromcircle +"','"+ vtocircle +"','"+ vfromnode +"','"+ vtonode +"','"+ fromslot +"','"+ toslot +"','"+ vnotes +"','"+ valm +"',sysdate,'"+ vupdbyalm +"','"+ vSN +"','"+ vsreqalm +"','"+ vnodepk +"','"+ vnodeatrpk +"','"+ vtoSN +"')  ");  
	 	stmtinsert1.executeUpdate();
	 	stmtinsert1.close();
	 	
	 	
	 	stmtinsert2 = con.prepareStatement("insert into  NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
		 			+ "select '" + Gyear +"' ||'_BOARD_'|| NODEBOARD_SEQ.nextval,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,'"+ vnodepk +"','"+ vnodeatrpk +"',UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'BOARD','"+ transid  +"','0' ,'"+ moveaction +"','1',LINE,ALM_POSITION,TIMESTAMP '" + crdate + "',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BOARD  where BOARD_ID='"+ tmpboardid +"' and LINE='"+ vline +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
		 			  
		 	stmtinsert2.executeUpdate();
		 	stmtinsert2.close();

	}
	
	private static void UpdateBoardMovement(String vdate,String moveaction,String movedescrip, String vfromsite,String vtosite,String vfromtech,String vtotech,String vfromcircle,String vtocircle,String vfromnode,String vtonode,String fromslot, String toslot, String vnotes, String valm, String vupdbyalm, String vSN,String vsreqalm,String vnodepk,String vnodeatrpk,String vtoSN,String vline,String finalboardid, String tmpboardid,String crdate,String vdomain, String vvendor) throws SQLException  {
	 	PreparedStatement stmtinsert1=null;
	 	PreparedStatement stmtinsert2=null;
	 	PreparedStatement stmtinsert3=null;
	 	
	 	String transid=Gyear+"_"+ "BOARD"+"_"+localgetseqNbr(21);
	 	//Add to NODE_BOARD_TRANSACTIONS
	 	stmtinsert1 = con.prepareStatement("insert into  NODE_BOARD_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
	 			+ "values ('"+ transid  +"',TIMESTAMP '"+ vdate +"', '"+ moveaction +"','"+ movedescrip +"','"+ vfromsite +"','"+ vtosite +"','"+ vfromtech +"','"+ vtotech +"','"+ vfromcircle +"','"+ vtocircle +"','"+ vfromnode +"','"+ vtonode +"','"+ fromslot +"','"+ toslot +"','"+ vnotes +"','"+ valm +"',sysdate,'"+ vupdbyalm +"','"+ vSN +"','"+ vsreqalm +"','"+ vnodepk +"','"+ vnodeatrpk +"','"+ vtoSN +"')  ");  
	 	stmtinsert1.executeUpdate();
	 	stmtinsert1.close();
	 	
	 	
		stmtinsert2 = con.prepareStatement("update NODE_BOARD Set UPDATE_DATE=sysdate, ACTIVE_RECORD='0',TO_TRANS_SOURCE='BOARD',TO_TRANS_ID='"+ transid  +"' where BOARD_ID ='"+ finalboardid +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	 	stmtinsert2.executeUpdate();
	 	stmtinsert2.close();
	 	

		stmtinsert3 = con.prepareStatement("insert into  NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
	 			+ "select '" + Gyear +"' ||'_BOARD_'|| NODEBOARD_SEQ.nextval,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,'"+ vnodepk +"','"+ vnodeatrpk +"',UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'BOARD','"+ transid  +"','0' ,'" + moveaction +"','1',LINE,ALM_POSITION,TIMESTAMP '" + crdate +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BOARD  where BOARD_ID='"+ tmpboardid +"' and LINE='"+ vline +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	 			  
	 	stmtinsert3.executeUpdate();
	 	stmtinsert3.close();

	}

	private static void UpdateSNMovement(String vdate,String moveaction,String movedescrip, String vfromsite,String vtosite,String vfromtech,String vtotech,String vfromcircle,String vtocircle,String vfromnode,String vtonode,String fromslot, String toslot, String vnotes, String valm, String vupdbyalm, String vSN,String vsreqalm,String vnodepk,String vnodeatrpk,String vtoSN,String vline,String finalboardid, String tmpboardid,String crdate,String vdomain, String vvendor) throws SQLException  {
	 	PreparedStatement stmtinsert1=null;
	 	PreparedStatement stmtinsert2=null;
	 	PreparedStatement stmtinsert3=null;
	 	
	 	String transid=Gyear+"_"+ "BOARD"+"_"+localgetseqNbr(21);
	 	//Add to NODE_BOARD_TRANSACTIONS
	 	stmtinsert1 = con.prepareStatement("insert into  NODE_BOARD_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
	 			+ "values ('"+ transid  +"',TIMESTAMP '"+ vdate +"', '"+ moveaction +"','"+ movedescrip +"','"+ vfromsite +"','"+ vtosite +"','"+ vfromtech +"','"+ vtotech +"','"+ vfromcircle +"','"+ vtocircle +"','"+ vfromnode +"','"+ vtonode +"','"+ fromslot +"','"+ toslot +"','"+ vnotes +"','"+ valm +"',sysdate,'"+ vupdbyalm +"','"+ vSN +"','"+ vsreqalm +"','"+ vnodepk +"','"+ vnodeatrpk +"','"+ vtoSN +"')  ");  
	 	stmtinsert1.executeUpdate();
	 	stmtinsert1.close();
	 	
	 	
		stmtinsert2 = con.prepareStatement("update NODE_BOARD Set UPDATE_DATE=sysdate, ACTIVE_RECORD='0',TO_TRANS_SOURCE='BOARD',TO_TRANS_ID='"+ transid  +"' where BOARD_ID ='"+ finalboardid +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	 	stmtinsert2.executeUpdate();
	 	stmtinsert2.close();
	 	

		stmtinsert3 = con.prepareStatement("insert into  NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
	 			+ "select '" + Gyear +"' ||'_BOARD_'|| NODEBOARD_SEQ.nextval,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,'"+ vnodepk +"','"+ vnodeatrpk +"',UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'BOARD','"+ transid  +"','0' , '" + moveaction +"','1',LINE,ALM_POSITION,TIMESTAMP '" + crdate +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BOARD  where BOARD_ID='"+ tmpboardid +"' and LINE='"+ vline +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	 			  
	 	stmtinsert3.executeUpdate();
	 	stmtinsert3.close();

	}

	private static void NewHardwareMovement(String vdate,String moveaction,String movedescrip, String vfromsite,String vtosite,String vfromtech,String vtotech,String vfromcircle,String vtocircle,String vfromnode,String vtonode,String fromslot, String toslot, String vnotes, String valm, String vupdbyalm, String vSN,String vsreqalm,String vnodepk,String vnodeatrpk,String vtoSN,String vline,String finalboardid, String tmpboardid,String crdate,String vdomain,String vvendor) throws SQLException  {
	 	PreparedStatement stmtinsert1=null;
	 	PreparedStatement stmtinsert2=null;
	 	PreparedStatement stmtinsert3=null;
	 	
	 	String transid=Gyear+"_"+ "BOARD"+"_"+localgetseqNbr(21);
	 	//Add to NODE_BOARD_TRANSACTIONS
	 	stmtinsert1 = con.prepareStatement("insert into  NODE_BOARD_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
	 			+ "values ('"+ transid  +"',TIMESTAMP '"+ vdate +"', '"+ moveaction +"','"+ movedescrip +"','"+ vfromsite +"','"+ vtosite +"','"+ vfromtech +"','"+ vtotech +"','"+ vfromcircle +"','"+ vtocircle +"','"+ vfromnode +"','"+ vtonode +"','"+ fromslot +"','"+ toslot +"','"+ vnotes +"','"+ valm +"',sysdate,'"+ vupdbyalm +"','"+ vSN +"','"+ vsreqalm +"','"+ vnodepk +"','"+ vnodeatrpk +"','"+ vtoSN +"')  ");  
	 	stmtinsert1.executeUpdate();
	 	stmtinsert1.close();
	 	
	 	
		stmtinsert2 = con.prepareStatement("update NODE_BOARD Set UPDATE_DATE=sysdate, ACTIVE_RECORD='0',TO_TRANS_SOURCE='BOARD',TO_TRANS_ID='"+ transid  +"' where BOARD_ID ='"+ finalboardid +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	 	stmtinsert2.executeUpdate();
	 	stmtinsert2.close();
	 	

		stmtinsert3 = con.prepareStatement("insert into  NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
	 			+ "select '" + Gyear +"' ||'_BOARD_'|| NODEBOARD_SEQ.nextval,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,'"+ vnodepk +"','"+ vnodeatrpk +"',UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'BOARD','"+ transid  +"','0' , '" + moveaction +"','1',LINE,ALM_POSITION,TIMESTAMP '" + crdate +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BOARD  where BOARD_ID='"+ tmpboardid +"' and LINE='"+ vline +"'  and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	 			  
	 	stmtinsert3.executeUpdate();
	 	stmtinsert3.close();

	}
	

	private static void GetDisappearingBoards(String vdomain, String vvendor) throws SQLException  {
		Statement stmttype1 = null;
		Statement stmttype2 = null;
		     
		String query1 = "(select c.NODE_ID,c.SITE_ID,c.CIRCLE_ID,c.NODE_NAME,a.SERIALNUMBER from NODE_BOARD a,NODE_ACTIVE c where a.NODE_PK in ((select distinct b.NODE_PK from NODE_ACTIVE b where b.TRANS_TYPE <> 'Node Disappeared' and b.ACTIVE_RECORD='1')) and a.TRANS_TYPE not in ('Node Disappeared' ,'Board Disappeared','Board Transfer to another Node After Disappear') and a.ACTIVE_RECORD='1' and a.NODE_PK=c.NODE_PK and c.DOMAIN='" + vdomain +"' and c.VENDOR='" + vvendor +"')\r\n" + 
				"minus \r\n" + 
				"(select c.NODE_ID,c.SITE_ID,c.CIRCLE_ID,c.NODE_NAME,a.SERIALNUMBER from TEMP_NODE_BOARD a,TEMP_NODE_ACTIVE c where a.NODE_PK in ((select distinct b.NODE_PK from TEMP_NODE_ACTIVE b where b.TRANS_TYPE <> 'Node Disappeared' and b.ACTIVE_RECORD='1')) and a.TRANS_TYPE not in ('Node Disappeared' ,'Board Disappeared','Board Transfer to another Node After Disappear') and a.ACTIVE_RECORD='1' and a.NODE_PK=c.NODE_PK and c.DOMAIN='" + vdomain +"' and c.VENDOR='" + vvendor +"')";
		stmttype1 = con.createStatement();
	    ResultSet rs1 = stmttype1.executeQuery(query1);
	    while (rs1.next()) {
	    	// if we found SN in node_board and not in temp_node_board means we have  SN in node_board disappeared
	    	// update existing record in node_board to active_record to 0 and create new one with SN =0 where trans_type =Board Disaapeared
	    	
	    	
	    	String query2 ="select c.NODE_ID,c.SITE_ID,c.CIRCLE_ID,c.NODE_NAME,a.BOARD_ID,a.SERIALNUMBER ,a.NODE_PK,a.NODE_ATTR_PK from NODE_BOARD a,NODE_ACTIVE c where a.SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"' and a.NODE_PK in ((select distinct b.NODE_PK from NODE_ACTIVE b where b.NODE_ID = '"+ rs1.getString("NODE_ID") +"' and SITE_ID='"+ rs1.getString("SITE_ID") +"' and CIRCLE_ID='"+ rs1.getString("CIRCLE_ID") +"'  and NODE_NAME = '"+ rs1.getString("NODE_NAME") +"' and b.ACTIVE_RECORD='1')) and a.TRANS_TYPE not in ('Node Disappeared' ,'Board Disappeared') and a.ACTIVE_RECORD='1' and a.NODE_PK=c.NODE_PK and c.DOMAIN='" + vdomain +"' and c.VENDOR='" + vvendor +"'";
	    	stmttype2 = con.createStatement();
		    ResultSet rs2 = stmttype2.executeQuery(query2);
		    while (rs2.next()) {
		    	
		    	PreparedStatement stmtinsert1=null;
			 	PreparedStatement stmtinsert2=null;
			 	PreparedStatement stmtinsert3=null;
			 	String transid=Gyear+"_"+ "BOARD"+"_"+localgetseqNbr(21);
			 	String boardid=Gyear+"_"+ "BOARD"+"_"+localgetseqNbr(5);
			 	
			 	//Add to NODE_BOARD_TRANSACTIONS
			 	stmtinsert1 = con.prepareStatement("insert into  NODE_BOARD_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
			 			+ "values ('"+ transid  +"',sysdate, 'Board Disappeared','Board found in Final Tables and not in new parsing','"+  rs2.getString("SITE_ID") + "','0','0','0','"+  rs2.getString("CIRCLE_ID") + "','0','"+  rs2.getString("NODE_ID") + "','0','0','0','0','0',sysdate,'0','"+ rs2.getString("SERIALNUMBER") +"','1','"+ rs2.getString("NODE_PK") +"','"+ rs2.getString("NODE_ATTR_PK") +"','0')  ");  
			 	stmtinsert1.executeUpdate();
			 	stmtinsert1.close();
		    	
		    	//update existing record to active_record =0 and and add trans_id
			 	stmtinsert2 = con.prepareStatement("update  NODE_BOARD set UPDATE_DATE=sysdate, TO_TRANS_SOURCE='BOARD',ACTIVE_RECORD='0' , TO_TRANS_ID='"+ transid  +"' where BOARD_ID='"+  rs2.getString("BOARD_ID") + "' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
			 	stmtinsert2.executeUpdate();
			 	stmtinsert2.close();
		    	
		    	//create new record having trans_type='Board Disappeared' active_record =1 and SN =0
			 	stmtinsert1 = con.prepareStatement("insert into  NODE_BOARD  (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) " 
			 			+ " select '" + boardid +"',SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,TO_TRANS_SOURCE,'"+ transid  +"','0','Board Disappeared','1',LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,'0' from NODE_BOARD where BOARD_ID='"+  rs2.getString("BOARD_ID") + "' and ACTIVE_RECORD='0' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
			 	stmtinsert1.executeUpdate();
			 	stmtinsert1.close();
		    	
		    }
		    rs2.close();
		    stmttype2.close();
	    }
	    rs1.close();
	    stmttype1.close();
	}
	

	private static void GetReappearingBoardSN(String vdomain, String vvendor) throws SQLException  {
		Statement stmttype1 = null;
		Statement stmttype2 = null;
		String sfromslot;
		String stoslot;
		String vstatus;
		String crdate;
		
		String slottmp= "0";
		String cabinettmp= "0";
		String subracktmp= "0";
		String racknotmp= "0";
		String slotpostmp = "0";
		String subslotnotmp= "0";
		String varsn= "0";
		
		String slotfnl= null;
		String cabinetfnl= null;
		String subrackfnl= null;
		String racknofnl= null;
		String slotposfnl = null;
		String subslotnofnl= null;
		String vartmpsn= "0";
		
		
		String query1 = "select c.NODE_ID,c.SITE_ID,c.CIRCLE_ID,c.NODE_NAME,a.BOARD_ID,a.CABINETNO,a.SUBRACKNO,a.RACKNO,a.SLOTNO,a.SLOTPOS,a.SUBSLOTNO,a.SERIALNUMBER,a.NODE_PK,a.NODE_ATTR_PK,a.LINE,a.CREATION_DATE from NODE_BOARD a,NODE_ACTIVE c where a.NODE_PK in ((select distinct b.NODE_PK from NODE_ACTIVE b where b.TRANS_TYPE <> 'Node Disappeared' and b.ACTIVE_RECORD='1')) and a.TRANS_TYPE in ('Board Disappeared') and a.ACTIVE_RECORD='1' and a.NODE_PK=c.NODE_PK and c.DOMAIN='" + vdomain +"' and c.VENDOR='" + vvendor +"'";      
		stmttype1 = con.createStatement();
	    ResultSet rs1 = stmttype1.executeQuery(query1);
	    while (rs1.next()) {
	    	 crdate=rs1.getString("CREATION_DATE");
	    	 slotfnl= rs1.getString("SLOTNO");
			 cabinetfnl= rs1.getString("CABINETNO");
			 subrackfnl= rs1.getString("SUBRACKNO");
			 racknofnl= rs1.getString("RACKNO");
			 slotposfnl = rs1.getString("SLOTPOS");
			 subslotnofnl= rs1.getString("SUBSLOTNO");
			 varsn= rs1.getString("SERIALNUMBER");
			 
			 sfromslot=cabinetfnl +"-"+ subrackfnl +"-"+  racknofnl  +"-"+ slotfnl +"-"+ slotposfnl +"-"+ subslotnofnl;
				
			 vstatus="0";
			 slottmp= "0";
			 cabinettmp= "0";
			 subracktmp= "0";
			 racknotmp= "0";
			 slotpostmp = "0";
			 subslotnotmp= "0";
			 vartmpsn="0";
			 String query2;
            // get record from TEMP_NODE_BOARD having  Board Disaapeared SN  in NODE_BOARD and active _record =1
			 int response =checkifnodeidandnodenamechnaged(rs1.getString("SERIALNUMBER"),rs1.getString("NODE_ID"),rs1.getString("NODE_NAME"),vdomain,vvendor);
			 if (response ==0 ) {
				// if change both nodei and node name
		    	// query2 = "select a.SERIALNUMBER,a.NODE_PK,a.NODE_ATTR_PK,a.BOARD_ID,a.CABINETNO,a.SUBRACKNO,a.RACKNO,a.SLOTNO,a.SLOTPOS,a.SUBSLOTNO,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,b.NODE_NAME from TEMP_NODE_BOARD a,TEMP_NODE_ACTIVE b  where a.SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"'  and a.NODE_PK=b.NODE_PK and a.ACTIVE_RECORD='1' and b.ACTIVE_RECORD='1' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"' "; 
		    	 query2 = "select a.SERIALNUMBER,a.NODE_PK,a.NODE_ATTR_PK,a.BOARD_ID,a.CABINETNO,a.SUBRACKNO,a.RACKNO,a.SLOTNO,a.SLOTPOS,a.SUBSLOTNO,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,b.NODE_NAME from TEMP_NODE_BOARD a,TEMP_NODE_ACTIVE b  where a.SERIALNUMBER <> '0' and a.SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"'  and a.NODE_PK=b.NODE_PK and a.ACTIVE_RECORD='1' and b.ACTIVE_RECORD='1' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"' "; 

			 }else {
				// use this if one of 2 chnaged nodeid or nodename
				// query2 = "select a.SERIALNUMBER,a.NODE_PK,a.NODE_ATTR_PK,a.BOARD_ID,a.CABINETNO,a.SUBRACKNO,a.RACKNO,a.SLOTNO,a.SLOTPOS,a.SUBSLOTNO,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,b.NODE_NAME from TEMP_NODE_BOARD a,TEMP_NODE_ACTIVE b  where a.SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"' and (b.NODE_NAME= '"+ rs1.getString("NODE_NAME") +"' or b.NODE_ID= '"+ rs1.getString("NODE_ID") +"' ) and a.NODE_PK=b.NODE_PK and a.ACTIVE_RECORD='1' and b.ACTIVE_RECORD='1' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'"; 
				 query2 = "select a.SERIALNUMBER,a.NODE_PK,a.NODE_ATTR_PK,a.BOARD_ID,a.CABINETNO,a.SUBRACKNO,a.RACKNO,a.SLOTNO,a.SLOTPOS,a.SUBSLOTNO,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,b.NODE_NAME from TEMP_NODE_BOARD a,TEMP_NODE_ACTIVE b  where a.SERIALNUMBER <> '0' and a.SERIALNUMBER='"+ rs1.getString("SERIALNUMBER") +"' and (b.NODE_NAME= '"+ rs1.getString("NODE_NAME") +"' or b.NODE_ID= '"+ rs1.getString("NODE_ID") +"' ) and a.NODE_PK=b.NODE_PK and a.ACTIVE_RECORD='1' and b.ACTIVE_RECORD='1' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'"; 

			 }
	    	 stmttype2 = con.createStatement();
		    ResultSet rs2 = stmttype2.executeQuery(query2);
		    while (rs2.next()) {
		    	 slottmp= rs2.getString("SLOTNO");
				 cabinettmp= rs2.getString("CABINETNO");
				 subracktmp= rs2.getString("SUBRACKNO");
				 racknotmp= rs2.getString("RACKNO");
				 slotpostmp = rs2.getString("SLOTPOS");
				 subslotnotmp= rs2.getString("SUBSLOTNO");
				 vartmpsn= rs2.getString("SERIALNUMBER");
				 
				 stoslot=cabinettmp +"-"+ subracktmp +"-"+  racknotmp  +"-"+ slottmp +"-"+ slotpostmp +"-"+ subslotnotmp;
				 
				 // validate if they have same nodeid/siteid/circelid  if no means transfer Board SN into (Node/Site/circel)
				 // if they have same node/site/circel check if same position means maintenance after Reappear if not Board Move after reappear
				 if (StringUtils.equalsIgnoreCase (rs1.getString("NODE_ID"),rs2.getString("NODE_ID")) && StringUtils.equalsIgnoreCase (rs1.getString("NODE_NAME"),rs2.getString("NODE_NAME")) && StringUtils.equalsIgnoreCase (rs1.getString("SITE_ID"),rs2.getString("SITE_ID")) && StringUtils.equalsIgnoreCase (rs1.getString("CIRCLE_ID"),rs2.getString("CIRCLE_ID")) ) {
					 // if they have same position means Maintenance
					 if (StringUtils.equalsIgnoreCase (sfromslot,stoslot)) {
							 System.out.println("Maintenance After Disappear");
							 vstatus="Maintenance After Disappear";

					 } else  { // means Board Movement
						 System.out.println("Board movement After Disappear");
						 vstatus="Board movement After Disappear";
					 }

				 } else {// if not the same means different node/site/circle it is Transfer
					 if (StringUtils.equalsIgnoreCase (rs1.getString("NODE_ID"),rs2.getString("NODE_ID")) && StringUtils.equalsIgnoreCase (rs1.getString("NODE_NAME"),rs2.getString("NODE_NAME"))) {
				    		if (StringUtils.equalsIgnoreCase (rs1.getString("SITE_ID"),rs2.getString("SITE_ID"))) {
				    			if (StringUtils.equalsIgnoreCase (rs1.getString("CIRCLE_ID"),rs2.getString("CIRCLE_ID"))) {
				    				
				    			} else {// not the same circle
				    				System.out.println("Board Transfer to another circle After Disappear");
				    				vstatus="Board Transfer to new circle After Disappear";
				    			}
				    			
				    		} else { // not the same site
				    			System.out.println("Board Transfer to another Site After Disappear");
				    			vstatus="Board Transfer to new Site After Disappear";
				    		}
				    		
					 } else { // not the same node
						 System.out.println("Board Transfer to another Node After Disappear");
						 vstatus="Board Transfer to another Node After Disappear";
					 }
						 
				 }
				 
				    PreparedStatement stmtinsert1=null;
				 	PreparedStatement stmtinsert2=null;
				 	PreparedStatement stmtinsert3=null;
				 	PreparedStatement stmtinsert4=null;
				    String transid=Gyear+"_"+ "BOARD"+"_"+localgetseqNbr(21);
				 	String boardid=Gyear+"_"+ "BOARD"+"_"+localgetseqNbr(5);
				 	
				 	//Add to NODE_BOARD_TRANSACTIONS
				 					 	
				 	stmtinsert1 = con.prepareStatement("insert into  NODE_BOARD_TRANSACTIONS (TRANS_ID,TRANS_DATE,TRANS_TYPE,TRANS_DESCRIPTION,FROM_SITE,TO_SITE,FROM_TECH,TO_TECH,FROM_CIRCLE,TO_CIRCLE,FROM_NODE_ID,TO_NODE_ID,FROM_SLOT,TO_SLOT,NOTES,SENT_TO_ALM,LAST_MODIFIED_DATE,UPDATED_BY_ALM,FROM_SERIAL_NUMBER,REQ_FROM_ALM,NODE_PK,NODE_ATTR_PK,TO_SERIAL_NUMBER) "
				 			+ "values ('"+ transid  +"',sysdate, '"+ vstatus +"','"+ vstatus +"','"+  rs1.getString("SITE_ID") + "','"+  rs2.getString("SITE_ID") + "','0','0','"+  rs2.getString("CIRCLE_ID") + "','0','"+  rs1.getString("NODE_ID") + "','"+  rs2.getString("NODE_ID") + "','"+ sfromslot +"','"+ stoslot +"','0','0',sysdate,'0','"+ rs2.getString("SERIALNUMBER") +"','1','"+ rs1.getString("NODE_PK") +"','"+ rs1.getString("NODE_ATTR_PK") +"','0')  ");  
				 	stmtinsert1.executeUpdate();
				 	stmtinsert1.close();
			    	
			    	//update existing record to active_record =0 and and add trans_id
				 	stmtinsert2 = con.prepareStatement("update  NODE_BOARD set UPDATE_DATE=sysdate, TO_TRANS_SOURCE='BOARD',ACTIVE_RECORD='0' , TO_TRANS_ID='"+ transid  +"' where BOARD_ID='"+  rs1.getString("BOARD_ID") + "' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				 	stmtinsert2.executeUpdate();
				 	stmtinsert2.close();
			    	
				 	//create new record having trans_type='Board Disappeared' active_record =1 and SN =0  in case of maintenance AND Board Movement   active_record =1 because SN still persists in same node
				 	if (StringUtils.equalsIgnoreCase (vstatus,"Maintenance After Disappear") || StringUtils.equalsIgnoreCase (vstatus,"Board movement After Disappear")) {
				 		//create new record having trans_type='Board Disappeared' active_record =1 and SN =0
					 	stmtinsert1 = con.prepareStatement("insert into  NODE_BOARD  (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) " 
					 			+ " select '" + boardid +"',SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,'" + rs1.getString("NODE_PK") +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'BOARD','"+ transid  +"','0','"+ vstatus +"','1',LINE,ALM_POSITION, TIMESTAMP '" + crdate +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BOARD where BOARD_ID='"+  rs2.getString("BOARD_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
					 	stmtinsert1.executeUpdate();
					 	stmtinsert1.close();
				 	} else {
			    	//create new record having trans_type='Board Disappeared' active_record =0 and SN =0 in case of transfer we put active_record 0 because SN will reappeared  in new node now and SN is unique
				 	stmtinsert4 = con.prepareStatement("insert into  NODE_BOARD  (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) " 
				 			+ " select '" + boardid +"',SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,'" + rs1.getString("NODE_PK") +"','" + rs1.getString("NODE_ATTR_PK") +"',UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,'BOARD','"+ transid  +"','0','"+ vstatus +"','1',LINE,ALM_POSITION, TIMESTAMP '" + crdate +"',DOMAIN,VENDOR,TO_TRANS_SOURCE from TEMP_NODE_BOARD where BOARD_ID='"+  rs2.getString("BOARD_ID") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
				 	stmtinsert4.executeUpdate();
				 	stmtinsert4.close();
				 	}
				 
				 
				 
		    }
		    rs2.close();
		    stmttype2.close();
	    }
	    rs1.close();
	    stmttype1.close();
	}
	
	private static String GetNodePK(String nodeid, String siteid, String circelid, String nodename, String tablename,String vdomain,String vvendor) throws SQLException {
	    String vnodepk = null ;

		Statement stmttype = null;
		String query2 = "select NODE_PK from " + tablename + " where NODE_ID='"+ nodeid +"' and SITE_ID='"+ siteid +"' and CIRCLE_ID ='"+ circelid +"' and NODE_NAME ='"+ nodename +"' and TRANS_TYPE <> 'Node Disappeared' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' ";      
	    stmttype = con.createStatement();
	    ResultSet rs2 = stmttype.executeQuery(query2);
	         
	    while (rs2.next()) {
	    	//try {
	    	vnodepk= rs2.getString("NODE_PK");    
	    	}
	    	//catch(Exception e)  
			//{  
			//	logger.info("error at GetNodePK is :"+ e.toString());
			//	System.out.println("error at GetNodePK is :"+ e.toString()); 
			//}
	     //}
	     rs2.close();
	     stmttype.close();

		return vnodepk;
	  }

	private static String GetNodePKATTRIBUTE(String nodepk, String tablename,String vdomain,String vvendor) throws SQLException {
	    String vnodepkattr = null ;

		Statement stmttype = null;
		String query2 = "select distinct NODE_ATTR_PK from " + tablename + " where NODE_PK='"+ nodepk +"' and ACTIVE_RECORD='1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";      
	    stmttype = con.createStatement();
	    ResultSet rs2 = stmttype.executeQuery(query2);
	         
	    while (rs2.next()) {
	    	//try {
	    		vnodepkattr= rs2.getString("NODE_ATTR_PK");    
	    	}
	    	//catch(Exception e)  
			//{  
			//	logger.info("error at GetNodePKATTRIBUTE is :"+ e.toString());
			//	System.out.println("error at GetNodePKATTRIBUTE is :"+ e.toString()); 
			//}
	    // }
	     rs2.close();
	     stmttype.close();

		return vnodepkattr;
	  }
	
	private static int checkifnodeidandnodenamechnaged(String vsn, String vnodeid, String vnodename,String vdomain,String vvendor) throws SQLException {
	    int resp = 0 ;

		Statement stmttype = null;
		String query2 = "select a.SERIALNUMBER,a.NODE_PK,a.NODE_ATTR_PK,a.BOARD_ID,a.CABINETNO,a.SUBRACKNO,a.RACKNO,a.SLOTNO,a.SLOTPOS,a.SUBSLOTNO,b.NODE_ID,b.SITE_ID,b.CIRCLE_ID,b.NODE_NAME from TEMP_NODE_BOARD a,TEMP_NODE_ACTIVE b  where a.SERIALNUMBER='" + vsn + "' and (b.NODE_NAME= '" + vnodename + "' or b.NODE_ID= '" + vnodeid + "' ) and a.NODE_PK=b.NODE_PK and a.ACTIVE_RECORD='1' and b.ACTIVE_RECORD='1' and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'";      
	    stmttype = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
	    ResultSet rs2 = stmttype.executeQuery(query2);
	    rs2.last();
	    resp = rs2.getRow();      
	     rs2.close();
	     stmttype.close();

		return resp;
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

private static  boolean checksn(String n1)  {
	String a = n1;
	boolean rep=false;
	for (int i=0; i < a.length(); i++)
	    if ( a.charAt(i) != '0' && a.length() >1) {
	    	rep=true;
	    	break;
	    }
	return rep;
}

}
