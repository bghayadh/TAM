package com.aliat.alm.Parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Date;
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

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class FirstParsing {
	static Connection con ;
	static Logger logger;
	static FileHandler fh;
	static BufferedReader objReader1 = null;
	static String strCurrentLine1;
	static String logpath;
	static String db1path;
	static String username1;
	static String password1;
	static String logsid="0";
	static String Gyear;
	static int nbOfErrors = 0;
	static Timestamp parsingDate;
	public static void main(String[] args) {
		
	try	{
	    	
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
			 
		}
		 objReader1.close();
		   
		 	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			Gyear=dtf.format(now).substring(0,4);
			String lofilename="FirstParsing-"+dtf.format(now)+".log";
		
		
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
				
				//logsid = localgetseqNbr(0);
				 //logsid=Gyear+"_"+ "LOGS"+'_'+logsid;
				Date date = new Date();
				parsingDate = new Timestamp(date.getTime());
				
				
				/// select different domain and vendor from temp node active table
				Statement stmtinit2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
		    	 String sqlStmtinit2 = "select distinct DOMAIN,SUB_DOMAIN_TYPE,SUB_DOMAIN,VENDOR from TEMP_NODE_ACTIVE";          
				    ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
				    rsinit2.last();
				    System.out.println(" rsinit2.last() : "+rsinit2.last());
			 	    int totalrecinit2 = rsinit2.getRow(); 
			 	   rsinit2.beforeFirst();
			 	   System.out.println(" rsinit2.beforeFirst() : "+totalrecinit2);
			 	   if (totalrecinit2 > 0 ) {
				 		  while (rsinit2.next()) {
				 			 
				 			 Timestamp startTime = new Timestamp(System.currentTimeMillis());
				 			 
				 			logsid= localgetseqNbr(0);
							 logsid=Gyear+"_"+ "LOGS"+'_'+logsid;
				 			 
				 			    logger.info("Check if it is a new DB to run First Parsing of  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
								System.out.println("Check if it is a new DB to run First Parsing of  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+","+ rsinit2.getString("SUB_DOMAIN_TYPE") +","+ rsinit2.getString("SUB_DOMAIN"));
								
								//insert into AUTO_DISCOVERY_LOGS_DETAILS
						 		String logs_DETAIls_ID= localgetseqNbr(1);
						 		logs_DETAIls_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_DETAIls_ID;
								PreparedStatement insert_LogsDETAIls_Statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
									 		+ "values('"+logs_DETAIls_ID+"',sysdate ,'FirstParsing','Check if it is a new DB to run First Parsing of  "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR")+"','','','','','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
									 		
								insert_LogsDETAIls_Statement.executeUpdate();
								insert_LogsDETAIls_Statement.close();
								
							    // Check if new DB to run First Parsing 
								GetDatafromTempNodeActive(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"),rsinit2.getString("SUB_DOMAIN"),rsinit2.getString("SUB_DOMAIN_TYPE")); 
								
								
								//insert into AUTO_DISCOVERY_LOGS_DETAILS
						 		String logs_DETAIls_Id= localgetseqNbr(1);
						 		logs_DETAIls_Id=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_DETAIls_Id;
								PreparedStatement insert_LogsDETAIlsStatement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
									 		+ "values('"+logs_DETAIls_Id+"',sysdate ,'FirstParsing','Number Of Errors','','Number Of Errors','','"+nbOfErrors+"','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"','"+logsid+"') ");
									 		
								insert_LogsDETAIlsStatement.executeUpdate();
								insert_LogsDETAIlsStatement.close();
								
								//insert into AUTO_DISCOVERY_LOGS
								//logsid= localgetseqNbr(1);
								logsid= localgetseqNbr(0);
								logsid=Gyear+"_"+ "LOGS"+'_'+logsid;
								PreparedStatement insertLogsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS (LOGS_ID,START_TIME,ACTIVITY_NAME,VENDOR,DOMAIN,STOP_TIME) "
								 		+ "values('"+logsid+"',? ,'FirstParsing','"+ rsinit2.getString("VENDOR") +"','"+rsinit2.getString("DOMAIN")+"',?) ");
								 		
					        	insertLogsstmt.setTimestamp(1, startTime);
					        	insertLogsstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
					        	insertLogsstmt.executeUpdate();
					        	insertLogsstmt.close();
					        	
					        	
					        	//Updating Site id from lookup table
						 		UpdatingSitefromLookUP(rsinit2.getString("DOMAIN"),rsinit2.getString("SUB_DOMAIN"),rsinit2.getString("SUB_DOMAIN_TYPE"),rsinit2.getString("VENDOR"));
					 		
				 		  }
				 			System.out.println("Calling truncating temp tables method");
						 	//All temporary tables to be truncated here.
						 	TruncateTempTables();
						 	System.out.println("After Truncating temp tables.");
			 	   } else {
			 		  System.out.println("No data in Temp  to check or run First Parsing");
			 		 logger.info("No data in Temp  to check or run First Parsing");
			 		 
			 		//insert into AUTO_DISCOVERY_LOGS_DETAILS
				 		String logs_DETAIls_ID= localgetseqNbr(1);
				 		logs_DETAIls_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_DETAIls_ID;
						PreparedStatement insert_LogsDETAIlsStatements = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
							 		+ "values('"+logs_DETAIls_ID+"',sysdate ,'FirstParsing','No data in Temp  to check or run First Parsing','','','','','','','') ");
							 		
						insert_LogsDETAIlsStatements.executeUpdate();
						insert_LogsDETAIlsStatements.close();
			 		 
			 	   }
			 	  rsinit2.close();
			 	  stmtinit2.close();
				
					
					System.out.println("COMPLETED");
					logger.info("COMPLETED");
					
					//insert into AUTO_DISCOVERY_LOGS_DETAILS
					String logsDetailsid= localgetseqNbr(1);
					logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
					PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
						 		+ "values('"+logsDetailsid+"',sysdate ,'FirstParsing','COMPLETED','','','','','','','"+logsid+"') ");
						 		
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
	
	private static void GetDatafromTempNodeActive(String vdomain, String vvendor, String subdomain, String subdomaintype) throws SQLException  {
		Statement stmt1 = null;
	
		int totalrec=0;
		String sqlStmt = null;
		stmt1 = con.createStatement();   
	    // Verify if no records in Node_active get data from Temp tables as first parsing
		if(subdomain != null && subdomaintype !=null) {
			sqlStmt = "select count(1) as countnbr from NODE_ACTIVE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' "
		    		+ "and SUB_DOMAIN='"+subdomain+"' and SUB_DOMAIN_TYPE='"+subdomaintype+"'";  
		}else if(subdomain != null && subdomaintype == null) {
			sqlStmt = "select count(1) as countnbr from NODE_ACTIVE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' "
		    		+ "and SUB_DOMAIN='"+subdomain+"'";
		}else if(subdomain == null && subdomaintype == null) {
			sqlStmt = "select count(1) as countnbr from NODE_ACTIVE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";
		}
	            
	    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
	    while (rs1.next()) {
	    	try {
	    	totalrec=(int) Long.parseLong (rs1.getString("countnbr"));
	        if (totalrec > 0) { // if >0 means we have data no need to run first parsing
	        	System.out.println("No need for first parsing for "+ vdomain +","+ vvendor);
	        	logger.info("No need for first parsing for  "+ vdomain +","+ vvendor);
	        	
	        	//insert into AUTO_DISCOVERY_LOGS_DETAILS
				String logsDetailsid= localgetseqNbr(1);
				logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
				PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
					 		+ "values('"+logsDetailsid+"',sysdate ,'FirstParsing','No need for first parsing for  "+ vdomain +","+ vvendor +"','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
					 		
				insertLogsDetailsstmt.executeUpdate();
				insertLogsDetailsstmt.close();
	        	
	        }else {
	        	System.out.println("Run Fist parsing for  "+ vdomain +","+ vvendor);
	        	logger.info("Run First parsing for  "+ vdomain +","+ vvendor);
	        	addAllNodeDatafromTemptoNodes("First Parsing",vdomain,vvendor);
	        	
			
			//insert into AUTO_DISCOVERY_LOGS_DETAILS
			String logsDetailsid= localgetseqNbr(1);
			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsid+"',sysdate ,'FirstParsing','Run First parsing for  " + vdomain +","+ vvendor + " ','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
			insertLogsDetailsstmt.executeUpdate();
			insertLogsDetailsstmt.close();
	        }
	        
	    } //end try
	    catch(Exception e)  
		{  
			logger.info("error at GetDatafromTempNodeActive is :"+ e.toString());
			System.out.println("error at GetDatafromTempNodeActive is :"+ e.toString()); 
			
			//insert into AUTO_DISCOVERY_LOGS_DETAILS
			String logsDetailsid= localgetseqNbr(1);
			logsDetailsid=Gyear+"_"+ "LOGS_DETAILS"+'_'+logsDetailsid;
			PreparedStatement insertLogsDetailsstmt = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logsDetailsid+"',sysdate ,'FirstParsing','error at GetDatafromTempNodeActive  ','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
			insertLogsDetailsstmt.executeUpdate();
			insertLogsDetailsstmt.close();
			nbOfErrors++;
		} 
	        
	    } // end while
	    rs1.close();
	    stmt1.close();
	}
	
	
	private static void addAllNodeDatafromTemptoNodes(String TransTyp,String vdomain, String vvendor) throws SQLException  {
	
		PreparedStatement stmtinsert1=null;
		PreparedStatement stmtinsert2=null;
		PreparedStatement stmtinsert3=null;
		try {
		//stmtinsert1 = con.prepareStatement("insert into NODE_ACTIVE (select * from TEMP_NODE_ACTIVE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
		stmtinsert1 = con.prepareStatement("insert into NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME,IP_ADDRESS,MAC_ADDRESS,SOFTWARE_VERSION,PATCH_VERSION,LONGITUDE,LATITUDE,PART_NUMBER,OTHERS,SUB_DOMAIN_TYPE,SUB_DOMAIN,PARSING_DATE,SERIAL_NUMBER)"
				+ "(select NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+TransTyp+"' as TRANS_TYPE,'1' as ACTIVE_RECORD,LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME,IP_ADDRESS,MAC_ADDRESS,SOFTWARE_VERSION,PATCH_VERSION,LONGITUDE,LATITUDE,PART_NUMBER,OTHERS,SUB_DOMAIN_TYPE,SUB_DOMAIN,TIMESTAMP '"+parsingDate+"',SERIAL_NUMBER from TEMP_NODE_ACTIVE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')");
		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 		System.out.println("insert into NODE_ACTIVE COMPLETED");
 		logger.info("insert into NODE_ACTIVE COMPLETED");
 		
 		stmtinsert2 = con.prepareStatement("insert into NODE_ACTIVE_ATTRIBUTE (NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR) "
 				+ "(select NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR from TEMP_NODE_ACTIVE_ATTRIBUTE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"') "); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 		System.out.println("insert into NODE_ACTIVE_ATTRIBUTE COMPLETED");
 		logger.info("insert into NODE_ACTIVE_ATTRIBUTE COMPLETED");
 		
 		stmtinsert3 = con.prepareStatement("insert into NODE_RACK (RACK_ID,RACKNO,INVENTORYUNITID,RACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR)"
 				+ "(select RACK_ID,RACKNO,INVENTORYUNITID,RACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR  from TEMP_NODE_RACK where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 		System.out.println("insert into NODE_RACK COMPLETED");
 		logger.info("insert into NODE_RACK COMPLETED");
 		
 		stmtinsert1 = con.prepareStatement("insert into NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,OTHERS)"
 				+ "(select CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,OTHERS from TEMP_NODE_CABINET where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 		System.out.println("insert into NODE_CABINET COMPLETED");
 		logger.info("insert into NODE_CABINET COMPLETED");
 		
 		stmtinsert2 = con.prepareStatement("insert into NODE_HOSTVER (HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR)"
 				+ "(select HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR from TEMP_NODE_HOSTVER where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 		System.out.println("insert into NODE_HOSTVER COMPLETED");
 		logger.info("insert into NODE_HOSTVER COMPLETED");
 		
 		stmtinsert3 = con.prepareStatement("insert into NODE_FRAME (FRAME_ID,RACKNO,FRAMENO,INVENTORYUNITID,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR)"
 				+ "(select FRAME_ID,RACKNO,FRAMENO,INVENTORYUNITID,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR from TEMP_NODE_FRAME where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 		System.out.println("insert into NODE_FRAME COMPLETED");
 		logger.info("insert into NODE_FRAME COMPLETED");
 		
 		stmtinsert1 = con.prepareStatement("insert into NODE_SLOT (SLOT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,DOMAIN,VENDOR)"
 				+ "(select SLOT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,DOMAIN,VENDOR from TEMP_NODE_SLOT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 		System.out.println("insert into NODE_SLOT COMPLETED");
 		logger.info("insert into NODE_SLOT COMPLETED");
 		
 		stmtinsert2 = con.prepareStatement("insert into NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,OTHERS) "
 				+ "(select BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,OTHERS from TEMP_NODE_BOARD where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 		System.out.println("insert into NODE_BOARD COMPLETED");
 		logger.info("insert into NODE_BOARD COMPLETED");
 		
 		stmtinsert3 = con.prepareStatement("insert into NODE_PORT (PORT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,VENDORUNITFAMILYTYPE,INVENTORYUNITID,PORTNO,HARDWAREVERSION,SERIALNUMBER,INVENTORYUNITTYPE,VENDORNAME,VENDORUNITTYPENUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MACADDR,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,OTHERS,PORTTYPE,PORTRATE)"
 				+ "(select PORT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,VENDORUNITFAMILYTYPE,INVENTORYUNITID,PORTNO,HARDWAREVERSION,SERIALNUMBER,INVENTORYUNITTYPE,VENDORNAME,VENDORUNITTYPENUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MACADDR,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,OTHERS,PORTTYPE,PORTRATE from TEMP_NODE_PORT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 		System.out.println("insert into NODE_PORT COMPLETED");
 		logger.info("insert into NODE_PORT COMPLETED");
 		
 		stmtinsert1 = con.prepareStatement("insert into NODE_ACCESSORY (ACCESSORY_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,ACCESSORYTYPE,ADDTIONALINFORMATION,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR) "
 				+ "(select ACCESSORY_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,ACCESSORYTYPE,ADDTIONALINFORMATION,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR from TEMP_NODE_ACCESSORY where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 		System.out.println("insert into NODE_ACCESSORY COMPLETED");
 		logger.info("insert into NODE_ACCESSORY COMPLETED");
 		
 		stmtinsert2 = con.prepareStatement("insert into NODE_HOST (HOST_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,HOSTNAME,NUMBEROFCPU,MEMSIZE,HARDDISKSIZE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR) "
 				+ "(select HOST_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,HOSTNAME,NUMBEROFCPU,MEMSIZE,HARDDISKSIZE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR from TEMP_NODE_HOST where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 		System.out.println("insert into NODE_HOST COMPLETED");
 		logger.info("insert into NODE_HOST COMPLETED");
 		
 		stmtinsert3 = con.prepareStatement("insert into NODE_SUBRACK (SUBRACK_ID,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,OTHERS) "
 				+ "(select SUBRACK_ID,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,OTHERS from TEMP_NODE_SUBRACK where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 		System.out.println("insert into NODE_SUBRACK COMPLETED");
 		logger.info("insert into NODE_SUBRACK COMPLETED");
 		
 		stmtinsert1 = con.prepareStatement("insert into NODE_2G (GCELL_ID,CELLID,CELLNAME,MCC,MNC,LAC,CI,NCC,BCC,TYPE,BCCHNO,BASEBANDPOLICY,BASEBANDEQMID,GBTSFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,GLOCELLID,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR) "
 				+ "(select GCELL_ID,CELLID,CELLNAME,MCC,MNC,LAC,CI,NCC,BCC,TYPE,BCCHNO,BASEBANDPOLICY,BASEBANDEQMID,GBTSFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,GLOCELLID,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR from TEMP_NODE_GCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 		System.out.println("insert into NODE_2G COMPLETED");
 		logger.info("insert into NODE_2G COMPLETED");
 		
 		stmtinsert2 = con.prepareStatement("insert into NODE_BTS (BTS_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR) "
 				+ "(select BTS_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR from TEMP_NODE_BTS where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 		System.out.println("insert into NODE_BTS COMPLETED");
 		logger.info("insert into NODE_BTS COMPLETED");
 		
 		stmtinsert3 = con.prepareStatement("insert into NODE_4G (UCELL_ID,CELLID,CELLNAME,LOCELL,NODEBFUNCTIONNAME,ULFREQ,DLFREQ,MAXPOWER,USERLABEL,MAXTXPOWER,UARFCNUPLINK,UARFCNDOWNLINK,PSCRAMBCODE,NODEBNAME,LAC,SAC,RAC,MANUFACTURERDATA,RADIUS,HORAD,DI,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR)"
 				+ "(select UCELL_ID,CELLID,CELLNAME,LOCELL,NODEBFUNCTIONNAME,ULFREQ,DLFREQ,MAXPOWER,USERLABEL,MAXTXPOWER,UARFCNUPLINK,UARFCNDOWNLINK,PSCRAMBCODE,NODEBNAME,LAC,SAC,RAC,MANUFACTURERDATA,RADIUS,HORAD,DI,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR from TEMP_NODE_UCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 		System.out.println("insert into NODE_4G COMPLETED");
 		logger.info("insert into NODE_4G COMPLETED");
 		
 		stmtinsert1 = con.prepareStatement("insert into NODE_ANTENNA (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR)"
 				+ "(select ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR from TEMP_NODE_ANTENNA where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 		System.out.println("insert into NODE_ANTENNA COMPLETED");
 		logger.info("insert into NODE_ANTENNA COMPLETED");
 		
 		stmtinsert2 = con.prepareStatement("insert into NODE_3G (LCELL_ID,LOCALCELLID,CELLNAME,CELLRADIUS,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,ENODEBFUNCTIONNAME,NBCELLFLAG,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR) "
 				+ "(select LCELL_ID,LOCALCELLID,CELLNAME,CELLRADIUS,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,ENODEBFUNCTIONNAME,NBCELLFLAG,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR from TEMP_NODE_LCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 		System.out.println("insert into NODE_3G COMPLETED");
 		logger.info("insert into NODE_3G COMPLETED");
 		
 		stmtinsert3 = con.prepareStatement("insert into NODE_RRN (RRN_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR)"
 				+ "(select RRN_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR from TEMP_NODE_RRN where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 		System.out.println("insert into NODE_RRN COMPLETED");
 		logger.info("insert into NODE_RRN COMPLETED");
 		
 		stmtinsert1 = con.prepareStatement("insert into NODE_ENODEBCELL (ENODEBCELL_ID,LOCALCELLID,CELLNAME,SECTORID,CSGIND,CYCLEPREFIX,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,TAC,ADDITIONALSPECTRUMEMISSION,NBCELLFLAG,ENODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR)"
 				+ "(select ENODEBCELL_ID,LOCALCELLID,CELLNAME,SECTORID,CSGIND,CYCLEPREFIX,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,TAC,ADDITIONALSPECTRUMEMISSION,NBCELLFLAG,ENODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR from TEMP_NODE_ENODEBCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 		System.out.println("insert into NODE_ENODEBCELL COMPLETED");
 		logger.info("insert into NODE_ENODEBCELL COMPLETED");
 		
 		stmtinsert2 = con.prepareStatement("insert into NODE_NODEBCELL (NODEBCELL_ID,LOCELL,USERLABEL,SITENO,SECNO,RADIUS,HORAD,BBPOOLTYPE,ULGROUPNO,CABINETNO1,SUBRACKNO1,SLOTNO1,CABINETNO2,SUBRACKNO2,SLOTNO2,ULFREQ,DLFREQ,MAXPOWER,HSDPA,DI,HIGHSPEEDMODE,SPEEDRATE,NODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR)"
 				+ "(select NODEBCELL_ID,LOCELL,USERLABEL,SITENO,SECNO,RADIUS,HORAD,BBPOOLTYPE,ULGROUPNO,CABINETNO1,SUBRACKNO1,SLOTNO1,CABINETNO2,SUBRACKNO2,SLOTNO2,ULFREQ,DLFREQ,MAXPOWER,HSDPA,DI,HIGHSPEEDMODE,SPEEDRATE,NODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR from TEMP_NODE_NODEBCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 		System.out.println("insert into NODE_NODEBCELL COMPLETED");
 		logger.info("insert into NODE_NODEBCELL COMPLETED");
 		
 		stmtinsert3 = con.prepareStatement("insert into NODE_NBINTERFACES (NBINTERFACE_ID,INTERFACETYPE,VERSION,ISUSED,NMSVENDOR,NMSNAME,REMARK,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR)"
 				+ "(select NBINTERFACE_ID,INTERFACETYPE,VERSION,ISUSED,NMSVENDOR,NMSNAME,REMARK,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR from TEMP_NODE_NBINTERFACES where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 		System.out.println("insert into NODE_NBINTERFACES COMPLETED");
 		logger.info("insert into NODE_NBINTERFACES COMPLETED");
 		
 		stmtinsert3 = con.prepareStatement("insert into NODE_CHILD_PARENT (ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR)"
 				+ "(select ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+ TransTyp +"' as TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR from TEMP_NODE_CHILD_PARENT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 		System.out.println("insert into NODE_CHILD_PARENT COMPLETED");
 		logger.info("insert into NODE_CHILD_PARENT COMPLETED");
 		
 		stmtinsert1 = con.prepareStatement("Insert into NODE_MODULE (MODULE_ID,CABINETNO,MODULENO,INVUNITID,IDENTIFICATIONCODE,CONFIGDN,INVUNITTYPE,PARENTDN,RUNTIMEDN,SERIALNUMBER,STATE,UNITPOSITION,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,SUBRACK_SPECIFIC_TYPE,USERLABEL,VENDORNAME,VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION,OTHERS,SOFTVER,HARDWAREVERSION,ANTENNA_STATUS)"
 				+ "(select MODULE_ID,CABINETNO,MODULENO,INVUNITID,IDENTIFICATIONCODE,CONFIGDN,INVUNITTYPE,PARENTDN,RUNTIMEDN,SERIALNUMBER,STATE,UNITPOSITION,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,SUBRACK_SPECIFIC_TYPE,USERLABEL,VENDORNAME,VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+TransTyp+"',ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION,OTHERS,SOFTVER,HARDWAREVERSION,ANTENNA_STATUS from TEMP_NODE_MODULE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert1.executeUpdate();
 		stmtinsert1.close();
 		System.out.println("insert into NODE_MODULE COMPLETED");
 		logger.info("insert into NODE_MODULE COMPLETED");

 		stmtinsert2 = con.prepareStatement("Insert into NODE_SUBMODULE (SUBMODULE_ID,CABINETNO,MODULENO,SUBMODULENO,INVUNITID,IDENTIFICATIONCODE,CONFIGDN,PARENTDN,RUNTIMEDN,SERIALNUMBER,UNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION)"
 				+ "(select SUBMODULE_ID,CABINETNO,MODULENO,SUBMODULENO,INVUNITID,IDENTIFICATIONCODE,CONFIGDN,PARENTDN,RUNTIMEDN,SERIALNUMBER,UNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+TransTyp+"'as TRANS_TYPE ,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION from TEMP_NODE_SUBMODULE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert2.executeUpdate();
 		stmtinsert2.close();
 		System.out.println("insert into NODE_SUBMODULE COMPLETED");
 		logger.info("insert into NODE_SUBMODULE COMPLETED");
 		
 		stmtinsert3 = con.prepareStatement("insert into NODE_SHELF (SHELF_ID,SHELFNO,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORNAME,SERIALNUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR,VENDORUNITTYPENUMBER,HARDWAREVERSION,MODEL,STATUS)"
 				+ "(select SHELF_ID,SHELFNO,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORNAME,SERIALNUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+TransTyp+"' as TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR,VENDORUNITTYPENUMBER,HARDWAREVERSION,MODEL,STATUS  from TEMP_NODE_SHELF where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 		System.out.println("insert into NODE_SHELF COMPLETED");
 		logger.info("insert into NODE_SHELF COMPLETED");

 		stmtinsert3 = con.prepareStatement("Insert into NODE_FUUNIT (FUUNIT_ID,FUUNITNO,FUNCTIONAL_UNIT_TYPE,SUPPORT_BY_UNIT,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION)"
 				+ "(select FUUNIT_ID,FUUNITNO,FUNCTIONAL_UNIT_TYPE,SUPPORT_BY_UNIT,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,'"+TransTyp+"',ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION from TEMP_NODE_FUUNIT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"')"); 
 		stmtinsert3.executeUpdate();
 		stmtinsert3.close();
 		System.out.println("insert into NODE_FUUNIT COMPLETED");
 		logger.info("insert into NODE_FUUNIT COMPLETED");
 		
		}
		catch(Exception e)  
		{  
			logger.info("error at addAllNodeDatafromTemptoNodes is :"+ e.toString());
			System.out.println("error at addAllNodeDatafromTemptoNodes is :"+ e.toString()); 
			e.printStackTrace();
			//insert into AUTO_DISCOVERY_LOGS_DETAILS
	 		String logs_DETAIls_ID= localgetseqNbr(1);
	 		logs_DETAIls_ID=Gyear+"_"+ "LOGS_DETAILS"+'_'+logs_DETAIls_ID;
			PreparedStatement insert_LOgsDETAIls_Statement = con.prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 		+ "values('"+logs_DETAIls_ID+"',sysdate ,'FirstParsing','error at addAllNodeDatafromTemptoNodes ','','','','','"+ vvendor +"','"+vdomain+"','"+logsid+"') ");
				 		
			insert_LOgsDETAIls_Statement.executeUpdate();
			insert_LOgsDETAIls_Statement.close();
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
		          stmttype = con.createStatement();
		          ResultSet rs2 = stmttype.executeQuery(query2);
		         
		          while (rs2.next()) {
		           min= rs2.getString("nbr");    
		          	}
		          rs2.close();

		          stmttype.close();

					 return min;

		  }
	 
	 
private static void UpdatingSitefromLookUP(String vdomain,String vsubdomain, String vtype,String vvendor) throws SQLException{
		 
		 String query = null,vSiteID = null,vWareName=null,vWareID=null,updatequery=null;
		 Statement stmt = null,stmt1=null;
		 PreparedStatement updatestmt = null;
		 
		 query = "Select NODE_ID,NODE_NAME,SITE_ID,WARE_ID,WARE_NAME From NODE_ACTIVE Where ACTIVE_RECORD='1' AND DOMAIN='"+vdomain+"' and VENDOR='"+vvendor+"'";
		 query = CheckSubDomain_Type(vsubdomain,vtype,query);
		 
		 stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 while(rs.next()) {
			 	vSiteID = rs.getString("SITE_ID");
	 			vWareID = rs.getString("WARE_ID");
	 			vWareName = rs.getString("WARE_NAME");
	 			
	 			if((vSiteID == null || vSiteID.equalsIgnoreCase("0") || vSiteID.equalsIgnoreCase("null"))
 		 				|| (vWareID == null || vWareID.equalsIgnoreCase("0") || vWareID.equalsIgnoreCase("null")) 
 		 				|| (vWareName == null || vWareName.equalsIgnoreCase("0") || vWareName.equalsIgnoreCase("null"))) {
	 				stmt1 = con.createStatement();
	 				query = "Select NODE_ID,NODE_NAME,SITE_ID,WARE_ID,WARE_NAME,LONGITUDE,LATITUDE FROM SITE_NODE_LOOKUP"
	 						+ " WHERE NODE_ID='"+rs.getString("NODE_ID")+"' AND NODE_NAME='"+rs.getString("NODE_ID")+"'";
	 				ResultSet rs1 = stmt1.executeQuery(query);
	 				while(rs1.next()) {
	 					updatequery = "UPDATE NODE_ACTIVE set SITE_ID='"+rs1.getString("SITE_ID")+"',WARE_ID='"+rs1.getString("WARE_ID")+"',"
	 								+ "WARE_NAME='"+rs1.getString("WARE_NAME")+"',LONGITUDE='"+rs1.getString("LONGITUDE")+"', LATITUDE='"+rs1.getString("LATITUDE")+"'"
	 								+ " WHERE NODE_ID='"+rs1.getString("NODE_ID")+"' AND NODE_NAME='"+rs1.getString("NODE_NAME")+"' AND ACTICE_RECORD='1'";
	 					updatestmt = con.prepareStatement(updatequery);
	 					updatestmt.executeUpdate();
	 					updatestmt.close();
	 				}
	 				rs1.close();
	 				stmt1.close();
	 			}
		 }
		 rs.close();
		 stmt.close();
	 }

private static String CheckSubDomain_Type(String subdomain,String type,String updateStatement) {
	if(subdomain != null) {
		updateStatement = updateStatement +" AND SUB_DOMAIN='"+subdomain+"'";
	}
	if(type != null) {
		updateStatement = updateStatement +" AND SUB_DOMAIN_TYPE='"+type+"'";
	}
	return updateStatement;
}

private static void TruncateTempTables() throws SQLException {
	Statement stmt = con.createStatement();
	System.out.println("********Truncating Temp Table****************");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_ACTIVE");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_ACTIVE_ATTRIBUTE");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_RACK");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_CABINET");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_HOSTVER");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_FRAME");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_SLOT");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_BOARD");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_PORT");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_ACCESSORY");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_HOST");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_SUBRACK");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_GCELL");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_BTS");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_UCELL");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_ANTENNA");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_LCELL");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_RRN");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_ENODEBCELL");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_NODEBCELL");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_NBINTERFACES");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_CHILD_PARENT");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_SUBMODULE");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_MODULE");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_FUUNIT");
	stmt.execute("TRUNCATE TABLE TEMP_NODE_SHELF");
	stmt.execute("COMMIT");
	stmt.close();
	System.out.println("********Truncating Executed****************");
}
}
