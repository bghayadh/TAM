package com.aliat.alm.telkom.Parser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.Spring;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoadFilesRanHuawei {
	private static final CopyOption REPLACE_EXISTING = null;
	static Connection parserCon;
	static Connection almCon;
    static String currentYear ;
    static String Fsource;
	static PreparedStatement stmtp;
	static Statement stmnt;
	static HashMap<String, String> vhmap = new HashMap<String, String>();
	static String[] ResultNode =null;
	static String projectPath=null;
	static String nodeDetails=null;
	static int totRow=0;
	static String nodeAttrType =null;
	static String tech2 ="0";
	static String tech3 ="0";
	static String tech4 ="0";
	static String tech5 ="0";
	static String codeID="0";
	static String circleId="0";
	static String attributeTable=null;
	static Logger logger;
	static FileHandler fileHand;
	static BufferedReader objReader = null;
	static String strCurLine;
	static String logpath;
	static String almDbPath;
	static String parserDbPath;
	static String readExcelFileFrom;
	static String readBscGSMFileFrom;
	static String readBscUMTSFileFrom;
	static String readBTSFileFrom;
	static String readOSSFileFrom;
	static String copyExcelFileTo;
	static String copyBscGSMFileTo;
	static String copyBscUMTSFileTo;
	static String copyBTSFileTo;
	static String copyOSSFileTo;
	static String almDbUsername;
	static String almDbPassword;
	static String parserDbUsername;
	static String parserDbPassword;
	static String provider;
	static String folderFrom;
	static String bscGsmFolderFrom;
	static String bscUMTSFolderFrom;
	static String btsFolderFrom;
	static String ossFolderFrom;
	static String almPosition;
	static String Domain;
	static String fileName;
	static String fileType;
	static Statement statement;
	static int NodeSeq;
	static String nodePK ="0";
	static String nodeId = null,nodeType = null,nodeModel = null,nodeName=null,unique_Node_ID = null;
	static String siteID,wareID,wareName,longitude,latitude,creationDate,IPaddress,MACaddress,commStatus="0",adminStatus="0",softwareVersion;
	static String subDomain,subDomainType,gateway,partNumber;
	static int NodeRackSeq=0,NodeActiveAttrSeq=0,NodeFrameSeq=0,nodeSlotSeq=0,nodeBoardSeq=0,nodePortSeq=0,nodeHostVerSeq=0,nodeCabinetSeq=0,nodeAccessorySeq=0,nodeHostSeq=0,nodeSubRackSeq=0,nodeGCellSeq=0,nodeBTSSeq=0,nodeUCellSeq=0,nodeAntennaSeq=0,nodeLCellSeq=0,nodeRRNSeq=0,nodeENodeBSeq=0,nodeNodeBSeq=0,nodeNBInterfaceSeq=0;


		public static void main(String[] args,String vendor,String domain,String sub_domain,String sub_domainType) throws Exception {

		
			 // Get all needed project/logs/files paths and alm/parser connections details from almconfig.dat
		 	objReader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almconfig.dat"));
			 while ((strCurLine = objReader.readLine()) != null){
				 String currentLine = strCurLine;
				 String[] splittedStr ;
				 String[] data ;
				 
				 if (currentLine.contains("projectpath")) {
					 splittedStr=currentLine.split(";",-1);
					 projectPath=splittedStr[1];
				 }
				 if (currentLine.contains("logpath")) {
					 splittedStr=currentLine.split(";",-1);
					 logpath=splittedStr[1];
				 }
				 if (currentLine.contains("db1path")) {
					 splittedStr=currentLine.split(";",-1);
					 almDbPath=splittedStr[1];
					 almDbUsername=splittedStr[2];
					 almDbPassword=splittedStr[3];
					
				 }
				 if (currentLine.contains("db2path")) {
					 splittedStr=currentLine.split(";",-1);
					 parserDbPath=splittedStr[1];
					 parserDbUsername=splittedStr[2];
					 parserDbPassword=splittedStr[3];
				 }
				 
				 if (currentLine.contains("readExcelFileRanHWFrom")) {
					 splittedStr=currentLine.split(";",-1);
					 readExcelFileFrom=splittedStr[1];
					 data=readExcelFileFrom.split("/",-1);
					 folderFrom=data[data.length-1];
					 
					 provider=vendor;
					 Domain=domain;
				 }
				 
				 if (currentLine.contains("readBscGSMFileRanHWFrom")) {
					 
					 splittedStr=currentLine.split(";",-1);
					 readBscGSMFileFrom = splittedStr[1];
					 data=readBscGSMFileFrom.replace("\\","/").split("/",-1);
					 bscGsmFolderFrom=readBscGSMFileFrom;
				 }
				 
				 if (currentLine.contains("readBscUMTSFileRanHWFrom")) {
					
					 splittedStr=currentLine.split(";",-1);
					 readBscUMTSFileFrom = splittedStr[1];
					 data=readBscUMTSFileFrom.replace("\\","/").split("/",-1);
					 bscUMTSFolderFrom=readBscUMTSFileFrom;			
				 }
				 
				 if (currentLine.contains("readBTSFileRanHWFrom")) {
					
					 splittedStr=currentLine.split(";",-1);
					 readBTSFileFrom = splittedStr[1];
					 data=readBTSFileFrom.replace("\\","/").split("/",-1);
					 btsFolderFrom=readBTSFileFrom;
				 }
				 
				 if (currentLine.contains("readOSSFileRanHWFrom")) {
						
					 splittedStr=currentLine.split(";",-1);
					 readOSSFileFrom = splittedStr[1];
					 data=readOSSFileFrom.replace("\\","/").split("/",-1);
					 ossFolderFrom=readOSSFileFrom;					 
				 }
				 				 
				 if (currentLine.contains("copyExcelFileRanHWTo")) {
					 splittedStr=currentLine.split(";",-1);
					 copyExcelFileTo=splittedStr[1];
				 }
				 
				 if (currentLine.contains("copyBscGSMFileRanHWTo")) {
					 splittedStr=currentLine.split(";",-1);
					 copyBscGSMFileTo=splittedStr[1];
				 }
				 
				 if (currentLine.contains("copyBscUMTSFileRanHWTo")) {
					 splittedStr=currentLine.split(";",-1);
					 copyBscUMTSFileTo=splittedStr[1];
				 }
				 
				 if (currentLine.contains("copyBTSFileRanHWTo")) {
					 splittedStr=currentLine.split(";",-1);
					 copyBTSFileTo=splittedStr[1];
				 } 
				 if (currentLine.contains("copyOSSFileRanHWTo")) {
					 splittedStr=currentLine.split(";",-1);
					 copyOSSFileTo=splittedStr[1];
				 } 
				 
				 
			}
			 objReader.close();
			 
			 // Get the circle ID from almcircle.dat
			 objReader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almcircle.dat"));
			 while ((strCurLine = objReader.readLine()) != null){
				 String data = strCurLine;
				 String[] circleID ;
				 circleID=data.split(";",-1);
				 circleId=circleID[1];
			 }
			 objReader.close();	 
			 
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			String logsFileName="ParserLogHuawei-"+dateTimeFormatter.format(now)+".log";
			currentYear=dateTimeFormatter.format(now).substring(0,4); // Get the year to use it when creating PK			
        	
			
			// Add excel sheets in an array of file objects to iterate over it and read it
			File excelFolder = new File(readExcelFileFrom);
			File[] listOfExcelFiles = excelFolder.listFiles();
			String fullFileName = null;
			
			// Add BSC GSM xml documents in an array of file objects to iterate over it and read it
			File bscGSMFolder = new File(readBscGSMFileFrom);
			File[] listOfBscGSMFiles = bscGSMFolder.listFiles();
			
			// Add BSC UMTS xml documents in an array of file objects to iterate over it and read it
			File bscUMTSFolder = new File(readBscUMTSFileFrom);
			File[] listOfBscUMTSFiles = bscUMTSFolder.listFiles();
			
			// Add BTS xml documents in an array of file objects to iterate over it and read it
			File btsFolder = new File(readBTSFileFrom);
			File[] listOfBTSFiles = btsFolder.listFiles();
			
			// Add OSS xml documents in an array of file objects to iterate over it and read it
			File ossFolder = new File(readOSSFileFrom);
			File[] listOfOSSFiles = ossFolder.listFiles();
			
			logger = Logger.getLogger("MyLog"); 
			logger.setUseParentHandlers(false);

						
			// This block configure the logger with handler and formatter and PATH
			fileHand = new FileHandler(logpath+"\\"+logsFileName);
		    logger.addHandler(fileHand);
		    SimpleFormatter formatter = new SimpleFormatter();  
		    fileHand.setFormatter(formatter);
		    
		    DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
			almCon = DriverManager.getConnection (almDbPath,almDbUsername,almDbPassword);
			try {
				parserCon= DriverManager.getConnection(parserDbPath,parserDbUsername,parserDbPassword);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println("Start Parsing .... ");
				
		    //Validate if the same process is running now (if yes we cannot run it twice until finish)
			Statement statement = parserCon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
		    String sqlQuery = "select * from EXECUTE_DOAMIN_VENDOR_FILES where DOMAIN='"+Domain+"' and VENDOR='"+ provider +"' and STATUS='IN PROCESS' ";
		    ResultSet resultSet = statement.executeQuery(sqlQuery);
			resultSet.last();
			int totalRowsNum = resultSet.getRow();
			resultSet.beforeFirst();
			
			// if the process is not running now ( IN PROCESS ) , it enters the if condition
			if (totalRowsNum == 0 ) {
				 		 PreparedStatement insertStmt = parserCon.prepareStatement("insert into EXECUTE_DOAMIN_VENDOR_FILES (DOMAIN,VENDOR,CREATION_DATE,STATUS) values ('"+Domain+"', '"+ provider +"',sysdate,'IN PROCESS')");
				 		 insertStmt.executeUpdate();
				 		 insertStmt.close();
						 logger.info("Load RAN HUAWEI Files in process...");

						 //Start reading each excel sheet from the array
						 for (File file : listOfExcelFiles) {
								if (file.isFile()) {
									fullFileName = file.getName().toString();
									
									String[] data=fullFileName.replace(".","_").split("_");
									fileName=data[0];
									fileType=data[1];
									
									readExcelSheetFiles(fullFileName);
									
									
									 File excelFileSource = new File(readExcelFileFrom+"/"+file.getName());
								     File excelFileDest = new File(copyExcelFileTo+"/"+file.getName()+".bkp");
								    
								     copyFiles(excelFileSource,excelFileDest);
								     deleteFiles(readExcelFileFrom+"/"+file.getName());
								     								    
							    }
							}
						 
						 //Start reading each BSC GSM xml document from the array
						 for (File file : listOfBscGSMFiles) {
								if (file.isFile()) {
									fullFileName = file.getName().toString();
									
									String[] data=fullFileName.replace(".","_").split("_");
									fileName=data[0];
									fileType=data[1];
									
									
									 readfile(fullFileName,bscGsmFolderFrom);									
									
									
									 File fileSource = new File(readBscGSMFileFrom+"/"+file.getName());
								     File fileDest = new File(copyBscGSMFileTo+"/"+file.getName()+".bkp");
								    
								     copyFiles(fileSource,fileDest);
								     deleteFiles(readBscGSMFileFrom+"/"+file.getName());
								     								    
							    }
							}
						 
						 //Start reading each BSC UMTS xml document from the array
						 for (File file : listOfBscUMTSFiles) {
								if (file.isFile()) {
									fullFileName = file.getName().toString();
									
									String[] data=fullFileName.replace(".","_").split("_");
									fileName=data[0];
									fileType=data[1];
									
									
									 readfile(fullFileName,bscUMTSFolderFrom);									
									
									 File fileSource = new File(readBscUMTSFileFrom+"/"+file.getName());
								     File fileDest = new File(copyBscUMTSFileTo+"/"+file.getName()+".bkp");
								    
								     copyFiles(fileSource,fileDest);
								     deleteFiles(readBscUMTSFileFrom+"/"+file.getName());
								     								    
							    }
							}
						 
						 //Start reading each BTS xml document from the array
						 for (File file : listOfBTSFiles) {
								if (file.isFile()) {
									fullFileName = file.getName().toString();
									
									String[] data=fullFileName.replace(".","_").split("_");
									fileName=data[0];
									fileType=data[1];
									
									
									 readfile(fullFileName,btsFolderFrom);									
									
									 File fileSource = new File(readBTSFileFrom+"/"+file.getName());
								     File fileDest = new File(copyBTSFileTo+"/"+file.getName()+".bkp");
								    
								     copyFiles(fileSource,fileDest);
								     deleteFiles(readBTSFileFrom+"/"+file.getName());
								     								    
							    }
							}
						 
						 //Start reading each OSS xml document from the array
						 for (File file : listOfOSSFiles) {
								if (file.isFile()) {
									fullFileName = file.getName().toString();
									
									String[] data=fullFileName.replace(".","_").split("_");
									fileName=data[0];
									fileType=data[1];
									
									 readfile(fullFileName,ossFolderFrom);									
								
									 File fileSource = new File(readOSSFileFrom+"/"+file.getName());
								     File fileDest = new File(copyOSSFileTo+"/"+file.getName()+".bkp");
								    
								     copyFiles(fileSource,fileDest);
								     deleteFiles(readOSSFileFrom+"/"+file.getName());
								     								    
							    }
						}
						 
						
							GetDuplicateFileName("RAN",provider);
							System.out.println("Clean Dupliate Data from Tables Completed  " );
							logger.info("Clean Dupliate Data from Tables Completed  ");
						 
							
							insertStmt = parserCon.prepareStatement("update EXECUTE_DOAMIN_VENDOR_FILES set STATUS ='COMPLETED' where DOMAIN='"+Domain+"' and VENDOR='"+ provider +"' and STATUS='IN PROCESS'");
							insertStmt.executeUpdate();
							insertStmt.close();
			 	   } else {
			 		   System.out.println("A process already is runnig , please wait until process ending");
			 		   logger.info("A process already is runnig , please wait until process ending");
			 	   }
			 	 statement.close();
			 	 resultSet.close();			
			
			parserCon.close();
			almCon.close();
	   }
		private static void readExcelSheetFiles(String fileName) throws FileNotFoundException, IOException, SQLException {
		
		
			  CSVParser csvParser = new CSVParser(new FileReader(folderFrom + "\\" + fileName), CSVFormat.DEFAULT);
			  List<CSVRecord> records = new ArrayList<>();
			  for (CSVRecord record : csvParser) {
				  records.add(record);
			}
			  
			  Calendar calendar = new GregorianCalendar();
			  int year = calendar.get(Calendar.YEAR);
			  
			  String sqlStmtinit2 = "select NODE_ACTIVE from SEQ_TABLE";     
			  stmnt = almCon.createStatement();
			  ResultSet rsinit2 = stmnt.executeQuery(sqlStmtinit2);
			  
			  while(rsinit2.next()) {
			  
				NodeSeq = rsinit2.getInt("NODE_ACTIVE");
			  	stmtp = almCon.prepareStatement("UPDATE SEQ_TABLE SET NODE_ACTIVE = NODE_ACTIVE +"+(records.size()-10));//records.size()-10) is used to remove the unnecessary header rows in the csv file
			  	stmtp.executeUpdate();
			  	stmtp.close();
			  }
			  
			  
			  for(int i=10;i<records.size();i++) {
				  nodePK=year+"_NODE_"+NodeSeq;
				  rsinit2.close();
				  stmnt.close();
				  
				  if(records.get(i).get(0).contains("_")) {// if the cell of the csv file contains _ then it may contain site ID
						
					  if(records.get(i).get(0).split("_").length >= 3) { // if the number of the elements split in the cell according to _ then it may contain site ID
							siteID = records.get(i).get(0).split("_")[0];
							char charArray[] = siteID.toCharArray();
							if(Character.isDigit(charArray[0]) && !Character.isDigit(charArray[siteID.length()-1])) { // if the first character of the possible site id is number then it is a site ID.
								siteID = siteID;
								String siteDetailsStatement = "select WARE_ID,WARE_NAME,LONGITUDE,LATITUDE from WAREHOUSE WHERE SITE_ID='"+siteID+"'";     
								stmnt = almCon.createStatement();
								 
								ResultSet siteDetailsResultSet = stmnt.executeQuery(siteDetailsStatement);
								while(siteDetailsResultSet.next()) {
									  wareID=siteDetailsResultSet.getString("WARE_ID");
									  wareName = siteDetailsResultSet.getString("WARE_ID");
									  longitude=siteDetailsResultSet.getString("LONGITUDE");
									  latitude = siteDetailsResultSet.getString("LATITUDE");
								 }
									siteDetailsResultSet.close();
									stmnt.close();
							}
							else {
								  wareID="";
								  wareName ="";
								  longitude="";
								  latitude = "";
								  siteID = "";
							}
								
						}
						else {
							  wareID="";
							  wareName ="";
							  longitude="";
							  latitude = "";
							  siteID = "";
						}
					}
				  else {
						  wareID="";
						  wareName ="";
						  longitude="";
						  latitude = "";
					}
				  
			  	  	  nodeName = records.get(i).get(0);
			  	  	  nodeType=records.get(i).get(1); 			  	  	  
			  	  	  
				    	if ((nodeName.contains("RNC")) && (nodeType.contains("BSC")))  {
							 nodeModel="RNC";
						 } 
				    	else if ((nodeName.contains("BSC")) && (nodeType.contains("BSC")))  {
							nodeModel="BSC";
						} 
				    	else if (nodeType.contains("OSS"))  {
							nodeModel="OSS";
						}
				    	else if (nodeType.contains("BTS"))  {
							nodeModel="BTS";
				    	}
				    	else {
				    		nodeModel=null;
				    	}					    
				    	
					  //nodeModel = records.get(i).get(1);
					  IPaddress = records.get(i).get(2);
					  softwareVersion = records.get(i).get(4);
					  MACaddress = null;
					  partNumber = records.get(i).get(21); 
					  commStatus= records.get(i).get(16);
					  adminStatus= records.get(i).get(22);
					  nodeId=records.get(i).get(2);
					  gateway=null;
					  unique_Node_ID = nodeId+"_HW";
					  
					  	stmtp =  parserCon.prepareStatement("insert into NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,ACTIVE_RECORD,LINE,WARE_ID,VENDOR,WARE_NAME,IP_ADDRESS,MAC_ADDRESS,SUB_DOMAIN,SOFTWARE_VERSION,STATUS_1,GATEWAY,LONGITUDE,LATITUDE,PART_NUMBER,SUB_DOMAIN_TYPE)"
						 		+ "values('"+nodePK+"','"+unique_Node_ID+"','"+nodeId+"','"+nodeName+"','"+nodeType+"','"+Domain+"','"+nodeModel+"','"+tech2+"','"+tech3+"','"+tech4+"','"+tech5+"','"+siteID+"','"+circleId+"',sysdate,sysdate,'"+fileType+"','"+fileName+"','"+commStatus+"','1','1','"+wareID+"','"+provider+"','"+wareName+"','"+IPaddress+"','"+MACaddress+"','"+subDomain+"','"+softwareVersion+"','"+adminStatus+"','"+gateway+"','"+longitude+"','"+latitude+"','"+partNumber+"','"+subDomainType+"')"); 
					  	stmtp.executeUpdate();
					  	stmtp.close();
					  	
					  	NodeSeq++;
				 
			  }	
		
		
		
		
		
		}
	 public static String readfile (String filename, String folderName){
	
			BufferedReader objReader = null;	       
	        String todayDate = null;
	        try{
	        		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        		LocalDateTime now = LocalDateTime.now();
	        		todayDate=dateTimeFormatter.format(now);
	   				
					BufferedReader buffReader = new BufferedReader(new FileReader(folderName+"/"+filename));
					while ((buffReader.readLine()) != null) {
						buffReader.readLine();
						String data = buffReader.readLine();
						nodeDetails=data.trim();
						System.out.println(nodeDetails);
						break;
					}
					buffReader.close();
					
					ResultNode=getNodeIdNameType(nodeDetails);
					
			        String tempNodeName=ResultNode[1];   
			        String tempNodeType=ResultNode[2];   
				    	
				    	if ((tempNodeName.contains("RNC")) && (tempNodeType.contains("BSC")))  {
				    		 nodeAttrType = "3G";
						 } 
				    	else {
							 
				    	if ((tempNodeName.contains("BSC")) && (tempNodeType.contains("BSC")))  {
				    		 nodeAttrType = "2G";
						 } 
				    	else {
				    	
				    	 if (tempNodeName.contains("_5G"))  {
				    		 nodeAttrType = "5G";
						 } 
				    	 else {
							 if (tempNodeName.contains("OSS"))  {
								 nodeAttrType = "OSS";
							 } 
							 else { 
								 if ( (tempNodeType.contains("eNodeB")))  {
									 nodeAttrType = "4G";
								 }
								 else {
									 if ((tempNodeName.contains("-MPT")))  {
										 nodeAttrType = "MPT";
						 			 }
									 else {
										 if ((tempNodeType.contains("MICRO"))) {
											 nodeAttrType = "3G-4G";
										 }
										 else {
											if  ((tempNodeType.contains("NodeB")) ) {
												nodeAttrType = "3G";
											} 
											else { 
												if ( (tempNodeName.contains("_Easymacro")) || (tempNodeName.contains("EasyMacro")) ||(tempNodeName.contains("_EM")) ) {
													nodeAttrType = "4G";
												}
												else {
													nodeAttrType = "2G";
												}
										   }
										}
						 			 }
								 }
							 }
						 }
	        		}
	 			}
						
										
					File fXmlFile = new File(folderName+"/"+filename);
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fXmlFile);   
					doc.getDocumentElement().normalize();
					
						
					NodeList nList = doc.getElementsByTagName("TABLE");

					 String allSeqSelectStmnt = "select NODE_RACK,NODE_ACTIVE_ATTRIBUTE,NODE_FRAME,NODE_SLOT,NODE_BOARD,NODE_PORT,NODE_HOSTVER,NODE_CABINET,NODE_ACCESSORY,NODE_HOST,NODE_SUBRACK,NODE_GCELL,NODE_BTS,NODE_UCELL,NODE_ANTENNA,NODE_LCELL,NODE_RRN,NODE_ENODEBCELL,NODE_NODEBCELL,NODE_NBInterfaces from SEQ_TABLE";     
	 				 stmnt = almCon.createStatement();
	 				 ResultSet allSeqResultSet = stmnt.executeQuery(allSeqSelectStmnt);
	 					  
	 				while(allSeqResultSet.next()) {
	 					  
	 					NodeRackSeq = allSeqResultSet.getInt("NODE_RACK");	
	 					NodeActiveAttrSeq = allSeqResultSet.getInt("NODE_ACTIVE_ATTRIBUTE");
	 					NodeFrameSeq = allSeqResultSet.getInt("NODE_FRAME");	
	 					nodeSlotSeq = allSeqResultSet.getInt("NODE_SLOT");	
	 					nodeBoardSeq = allSeqResultSet.getInt("NODE_BOARD");	
	 					nodePortSeq = allSeqResultSet.getInt("NODE_PORT");	
	 					nodeHostVerSeq = allSeqResultSet.getInt("NODE_HOSTVER");	
	 					nodeCabinetSeq = allSeqResultSet.getInt("NODE_CABINET");	
	 					nodeAccessorySeq = allSeqResultSet.getInt("NODE_ACCESSORY");
	 					nodeHostSeq = allSeqResultSet.getInt("NODE_HOST");	
	 					nodeSubRackSeq = allSeqResultSet.getInt("NODE_SUBRACK");
	 					nodeGCellSeq = allSeqResultSet.getInt("NODE_GCELL");
	 					nodeBTSSeq = allSeqResultSet.getInt("NODE_BTS");
	 					nodeUCellSeq = allSeqResultSet.getInt("NODE_UCELL");
	 					nodeAntennaSeq = allSeqResultSet.getInt("NODE_ANTENNA");
	 					nodeLCellSeq = allSeqResultSet.getInt("NODE_LCELL");
	 					nodeRRNSeq = allSeqResultSet.getInt("NODE_RRN");
	 					nodeENodeBSeq = allSeqResultSet.getInt("NODE_ENODEBCELL");
	 					nodeNodeBSeq = allSeqResultSet.getInt("NODE_NODEBCELL");
	 					nodeNBInterfaceSeq = allSeqResultSet.getInt("NODE_NBInterfaces");

	 				}
	 					 
	 					 
					for (int temp = 0; temp < nList.getLength(); temp++) {
						totRow=0;
						Node nNode = nList.item(temp);


						if (nNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) nNode;
							totRow=eElement.getElementsByTagName("ROW").getLength();
							logger.info("number of ROWS in  " + filename + "/  " +eElement.getAttribute("attrname") +"  is : "  + totRow);

							
							// Get table atribute name based on attrname
							attributeTable=getAttributeTableName(eElement.getAttribute("attrname"));
							

							
		 					 String codeidattr=currentYear+"_"+ "ATTRIBUTE"+'_'+NodeActiveAttrSeq;
		 					 PreparedStatement stmta = parserCon.prepareStatement("insert into NODE_ACTIVE_ATTRIBUTE (NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR) "
		 					 		+ "values('" + codeidattr +"' ,'" + eElement.getAttribute("attrname") +"','" + attributeTable +"','" + codeID  +"', '" + nodeAttrType  +"',sysdate,'" + filename +"','0','0','0','0','0','0','1','" + temp +"', 'RAN','" + provider +"') "); 
		 	                 stmta.executeUpdate();
		 				     stmta.close();
							
							
		 				    NodeActiveAttrSeq++;
		 					  
		 					  
		 					  
		 				    String vcodeid=null;
							String strCurrentLine;
							objReader = new BufferedReader(new FileReader(folderName+"/"+filename));
							while ((strCurrentLine = objReader.readLine()) != null) {
								String data = strCurrentLine;
								String str1="TABLE attrname=\""+eElement.getAttribute("attrname")+"\"";
	
							if (data.contains(str1)) {	
								data = objReader.readLine();
				                  
				                  for (int j=0; j< totRow;j++) {
				                	  data = objReader.readLine();
				                	  if (attributeTable == "NODE_RACK") {
					                		   vhmap=getMapColumnsRack(data);
					                	  }
				                	  if (attributeTable == "NODE_FRAME") {
				                		   vhmap=getMapColumnsFrame(data);
				                	  }
				                	 if (attributeTable == "NODE_SLOT") {
				                		   vhmap=getMapColumnsSlot(data);
				                	  }
				                	  if (attributeTable == "NODE_BOARD") {
				                		   vhmap=getMapColumnsBoard(data);
				                	  }
				                	  if (attributeTable == "NODE_PORT") {
				                		   vhmap=getMapColumnsPort(data);
				                	  }
				                	  if (attributeTable == "NODE_HOSTVER") {
				                		   vhmap=getMapColumnsHostver(data);
				                	  } 
				                	  if (attributeTable == "NODE_CABINET") {
				                		   vhmap=getMapColumnsCabinet(data);
				                	  }
				                	  if (attributeTable == "NODE_ACCESSORY") {
				                		   vhmap=getMapColumnsAccessory(data);
				                	  }
				                	  if (attributeTable == "NODE_HOST") {
				                		   vhmap=getMapColumnsHost(data);
				                	  }
				                	  if (attributeTable == "NODE_SUBRACK") {
				                		   vhmap=getMapColumnsSubrack(data);
				                	  } 
				                	  if (attributeTable == "NODE_GCELL") {
				                		   vhmap=getMapColumnsGcell(data);
				                	  } 
				                	  if (attributeTable == "NODE_BTS") {
				                		   vhmap=getMapColumnsBts(data);
				                	  } 
				                	  if (attributeTable == "NODE_UCELL") {
				                		   vhmap=getMapColumnsUcell(data);
				                	  } 
				                	  if (attributeTable == "NODE_ANTENNA") {
				                		   vhmap=getMapColumnsAntenna(data);
				                	  } 
				                	  if (attributeTable == "NODE_LCELL") {
				                		   vhmap=getMapColumnsLcell(data);
				                	  } 
				                	  if (attributeTable == "NODE_RRN") {
				                		   vhmap=getMapColumnsRRN(data);
				                	  } 
				                	  if (attributeTable == "NODE_ENODEBCELL") {
				                		   vhmap=getMapColumnsENodebCell(data);
				                	  } 
				                	  if (attributeTable == "NODE_NODEBCELL") {
				                		   vhmap=getMapColumnsNodebCell(data);
				                	  } 
				                	  if (attributeTable == "NODE_NBInterfaces") {
				                		   vhmap=getMapColumnsNbInterfaces(data);
				                	  } 
				                	  				                	  
				                	  //fill in ttribute data into appropriate tabel fetched by table name under parameter attributeTable
				                	   String InsertQuery = null;
				                	   if (attributeTable =="NODE_RACK" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) {vhmap.put("DATEOFMANUFACTURE", todayDate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todayDate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid=currentYear+"_Node_HW_RAN_RAC"+'_'+NodeRackSeq;
				                		   InsertQuery="insert into " + attributeTable  + " (RACK_ID,RACKNO,INVENTORYUNITID,RACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("RACKNO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("RACKTYPE") +"','" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"','" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("MODEL") +"','" + vhmap.get("USERLABEL") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"' ,'"+ j +"','1','RAN','" + provider +"' ) ";
				                		   NodeRackSeq++;
				                	   }
				                	  if (attributeTable =="NODE_FRAME" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) {vhmap.put("DATEOFMANUFACTURE", todayDate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todayDate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid=currentYear+"_Node_HW_RAN_FRAME"+'_'+NodeFrameSeq;
				                		   InsertQuery="insert into " + attributeTable  +" (FRAME_ID,RACKNO,FRAMENO,INVENTORYUNITID,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("RACKNO") +"','" + vhmap.get("FRAMENO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("FRAMETYPE") +"','" + vhmap.get("RACKFRAMENO") +"','" + vhmap.get("MODULENO") +"','" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"', '" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"','" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("MODEL") +"','" + vhmap.get("USERLABEL") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"' ,'0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','RAN','" + provider +"') ";
				                		   NodeFrameSeq++;
				                	  }
				                	  if (attributeTable =="NODE_SLOT" ) {
				                		   almPosition ="0";
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todayDate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todayDate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid=currentYear+"_Node_HW_RAN_SLOT"+'_'+nodeSlotSeq;
				                		   almPosition =vhmap.get("CABINETNO") +'/'+vhmap.get("SUBRACKNO")+'/'+ vhmap.get("RACKNO")+'/'+ vhmap.get("SLOTNO")+'/'+ vhmap.get("SLOTPOS");
				                		   InsertQuery="insert into " + attributeTable  +" (SLOT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("CABINETNO") +"','" + vhmap.get("SUBRACKNO") +"','" + vhmap.get("RACKNO") +"','" + vhmap.get("FRAMENO") +"','" + vhmap.get("SLOTNO") +"','" + vhmap.get("SLOTPOS") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"', '" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"', '" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"' ,'" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','"+ almPosition +"','RAN','" + provider +"') ";
				                		   nodeSlotSeq++; 
				                	   }
				                	  if (attributeTable =="NODE_BOARD" ) {   
				                		   almPosition ="0";
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todayDate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todayDate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid=currentYear+"_Node_HW_RAN_BRD"+'_'+nodeBoardSeq;
				                		   almPosition =vhmap.get("CABINETNO") +'/'+vhmap.get("SUBRACKNO")+'/'+ vhmap.get("SLOTNO")+'/'+ vhmap.get("SLOTPOS")+'/'+ vhmap.get("SUBSLOTNO");
				                		   InsertQuery="insert into " + attributeTable  +"(BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,EXTINFO,APDEVINFO,WORKMODE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("CABINETNO") +"','" + vhmap.get("SUBRACKNO") +"','" + vhmap.get("RACKNO") +"','" + vhmap.get("FRAMENO") +"','" + vhmap.get("SLOTNO") +"','" + vhmap.get("SLOTPOS") +"','" + vhmap.get("SUBSLOTNO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("MODULENO") +"','" + vhmap.get("BOARDNAME") +"','" + vhmap.get("BOARDTYPE") +"','" + vhmap.get("INVENTORYUNITTYPE") +"', '" + vhmap.get("VENDORUNITFAMILYTYPE") +"', '" + vhmap.get("VENDORUNITTYPENUMBER") +"', '" + vhmap.get("VENDORNAME") +"', '" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"','" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("SOFTVER") +"','" + vhmap.get("LOGICVER") +"','" + vhmap.get("BIOSVER") +"','" + vhmap.get("BIOSVEREX") +"','" + vhmap.get("LANVER") +"','" + vhmap.get("MBUSVER") +"','" + vhmap.get("ISSUENUMBER") +"','" + vhmap.get("BOMCODE") +"','" + vhmap.get("MODEL") +"','" + vhmap.get("USERLABEL") +"','" + vhmap.get("EXTINFO") +"','" + vhmap.get("APDEVINFO") +"','" + vhmap.get("WORKMODE") +"','" + codeID +"','" + codeidattr +"' ,sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','"+ almPosition +"',sysdate,'RAN','" + provider +"') ";
				                		   nodeBoardSeq++;
				                	   }
				                	  if (attributeTable =="NODE_PORT" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todayDate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todayDate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid=currentYear+"_Node_HW_RAN_PORT"+'_'+nodePortSeq;
				                		   InsertQuery="insert into " + attributeTable  +" (PORT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,VENDORUNITFAMILYTYPE,INVENTORYUNITID,PORTNO,HARDWAREVERSION,SERIALNUMBER,INVENTORYUNITTYPE,VENDORNAME,VENDORUNITTYPENUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MACADDR,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("CABINETNO") +"','" + vhmap.get("SUBRACKNO") +"','" + vhmap.get("RACKNO") +"','" + vhmap.get("FRAMENO") +"','" + vhmap.get("SLOTNO") +"','" + vhmap.get("SLOTPOS") +"','" + vhmap.get("SUBSLOTNO") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("PORTNO") +"','" + vhmap.get("HARDWAREVERSION") +"','" + vhmap.get("SERIALNUMBER") +"', '" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("VENDORNAME") +"', '" + vhmap.get("VENDORUNITTYPENUMBER") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"','" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MACADDR") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + codeID +"','" + codeidattr +"' ,sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','RAN','" + provider +"') ";
				                		   nodePortSeq++;
				                	   }
				                	   if (attributeTable =="NODE_CABINET" ) { 
				                		   almPosition ="0";
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todayDate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todayDate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
					                		   vcodeid=currentYear+"_Node_HW_RAN_CAB"+'_'+nodeCabinetSeq;
					                		   almPosition =vhmap.get("CABINETNO");
					                		   InsertQuery="insert into " + attributeTable  + " (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
					                		   		+ " values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("CABINETNO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("RACKTYPE") +"','" + vhmap.get("BOMRACKTYPE") +"','" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"', '" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("ISSUENUMBER") +"','" + vhmap.get("BOMCODE") +"','" + vhmap.get("EXTINFO") +"','" + vhmap.get("MODEL") +"','" + vhmap.get("USERLABEL") +"','" + vhmap.get("SHAREMODE") +"','" + vhmap.get("CLEICODE") +"','" + vhmap.get("BOM") +"','" + codeID +"','" + codeidattr +"',sysdate ,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','"+ almPosition +"',sysdate,'RAN','" + provider +"') ";
					                		   nodeCabinetSeq++;
				                	   }
				                	   if (attributeTable =="NODE_HOSTVER" ) {
				                		   vcodeid=currentYear+"_Node_HW_RAN_HOSTVER"+'_'+nodeHostVerSeq;
				                		   InsertQuery="insert into " + attributeTable  +" (HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR)  values('" + vcodeid +"','" + vhmap.get("HOSTVERTYPE") +"','"+ vhmap.get("HOSTVER") +"','"+ vhmap.get("SDESC") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"' ,'0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1',sysdate,'RAN','" + provider +"') ";
				                		   nodeHostVerSeq++;
				                	   }
				                	   if (attributeTable =="NODE_ACCESSORY" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todayDate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todayDate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid=currentYear+"_Node_HW_RAN_ACCESSORY"+'_'+nodeAccessorySeq;
				                		   InsertQuery="insert into " + attributeTable  +" (ACCESSORY_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,ACCESSORYTYPE,ADDTIONALINFORMATION,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("RACKPOSITION") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"','" + vhmap.get("SOFTVER") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"', '" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("ACCESSORYTYPE") +"','" + vhmap.get("ADDTIONALINFORMATION") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"' ,'" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','RAN','" + provider +"') ";
				                		   nodeAccessorySeq++;
				                	   }
				                	   if (attributeTable =="NODE_HOST" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todayDate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todayDate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid=currentYear+"_Node_HW_RAN_HOST"+'_'+nodeHostSeq;
				                		   InsertQuery="insert into " + attributeTable  + " (HOST_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,HOSTNAME,NUMBEROFCPU,MEMSIZE,HARDDISKSIZE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ " values('" + vcodeid +"','" + vhmap.get("RACKPOSITION") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"','" + vhmap.get("SOFTVER") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"', '" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("HOSTNAME") +"','" + vhmap.get("NUMBEROFCPU") +"','" + vhmap.get("MEMSIZE") +"','" + vhmap.get("HARDDISKSIZE") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"' ,'0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','RAN','" + provider +"') ";
				                		   nodeHostSeq++;
				                	   }
				                	   if (attributeTable =="NODE_SUBRACK" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todayDate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todayDate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid=currentYear+"_Node_HW_RAN_SUBRAC"+'_'+nodeSubRackSeq;
			                			   InsertQuery="insert into " + attributeTable  +" (SUBRACK_ID,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
			                			   		+ "values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("CABINETNO") +"','" + vhmap.get("SUBRACKNO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("RACKTYPE") +"','" + vhmap.get("BOMRACKTYPE") +"','" + vhmap.get("FRAMETYPE") +"','" + vhmap.get("RACKFRAMENO") +"','" + vhmap.get("MODULENO") +"','" + vhmap.get("INVENTORYUNITTYPE") +"', '" + vhmap.get("VENDORUNITFAMILYTYPE") +"', '" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"','" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("USERLABEL") +"','" + vhmap.get("BOMCODE") +"','" + vhmap.get("MODEL") +"','" + vhmap.get("ISSUENUMBER") +"','" + vhmap.get("BOMFRAMETYPE") +"','" + vhmap.get("CLEICODE") +"','" + vhmap.get("BOM") +"','" + vhmap.get("EXTINFO") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"' ,'"+ j +"','1','RAN','" + provider +"') ";
				                		   nodeSubRackSeq++;

				                		   }
				                	   if (attributeTable =="NODE_GCELL" ) {
				                		   vcodeid=currentYear+"_Node_HW_RAN_GCELL"+'_'+nodeGCellSeq;
				                		   InsertQuery="insert into " + attributeTable  +" (GCELL_ID,CELLID,CELLNAME,MCC,MNC,LAC,CI,NCC,BCC,TYPE,BCCHNO,BASEBANDPOLICY,BASEBANDEQMID,GBTSFUNCTIONNAME,GLOCELLID,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("CELLID") +"','" + vhmap.get("CELLNAME") +"','" + vhmap.get("MCC") +"','" + vhmap.get("MNC") +"','" + vhmap.get("LAC") +"','" + vhmap.get("CI") +"','" + vhmap.get("NCC") +"','" + vhmap.get("BCC") +"','" + vhmap.get("TYPE") +"','" + vhmap.get("BCCHNO") +"','" + vhmap.get("BASEBANDPOLICY") +"','" + vhmap.get("BASEBANDEQMID") +"','" + vhmap.get("GBTSFUNCTIONNAME") +"','" + vhmap.get("GLOCELLID") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1',sysdate,'RAN','" + provider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                		   nodeGCellSeq++;
				                	   }
				                	   if (attributeTable =="NODE_BTS" ) {
				                		   vcodeid=currentYear+"_Node_HW_RAN_BTS"+'_'+nodeBTSSeq;
				                		   InsertQuery="insert into " + attributeTable  +" (BTS_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("SITENAME") +"','" + vhmap.get("SITETYPE") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"' ,'"+ j +"','1','RAN','" + provider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                		   nodeBTSSeq++;
				                	   }
				                	   if (attributeTable =="NODE_UCELL" ) {
				                		   vcodeid=currentYear+"_Node_HW_RAN_UCELL"+'_'+nodeUCellSeq;
				                		   InsertQuery="insert into " + attributeTable  +"(UCELL_ID,CELLID,CELLNAME,LOCELL,NODEBFUNCTIONNAME,ULFREQ,DLFREQ,MAXPOWER,USERLABEL,MAXTXPOWER,UARFCNUPLINK,UARFCNDOWNLINK,PSCRAMBCODE,NODEBNAME,LAC,SAC,RAC,MANUFACTURERDATA,RADIUS,HORAD,DI,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("CELLID") +"','" + vhmap.get("CELLNAME") +"','" + vhmap.get("LOCELL") +"','" + vhmap.get("NODEBFUNCTIONNAME") +"','" + vhmap.get("ULFREQ") +"','" + vhmap.get("DLFREQ") +"','" + vhmap.get("MAXPOWER") +"','" + vhmap.get("USERLABEL") +"','" + vhmap.get("MAXTXPOWER") +"','" + vhmap.get("UARFCNUPLINK") +"','" + vhmap.get("UARFCNDOWNLINK") +"','" + vhmap.get("PSCRAMBCODE") +"','" + vhmap.get("NODEBNAME") +"','" + vhmap.get("LAC") +"','" + vhmap.get("SAC") +"','" + vhmap.get("RAC") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("RADIUS") +"','" + vhmap.get("HORAD") +"','" + vhmap.get("DI") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"' ,'0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1',sysdate,'RAN','" + provider +"') ";
				                		   nodeUCellSeq++;
				                	   }
				                	   if (attributeTable =="NODE_ANTENNA" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todayDate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todayDate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid=currentYear+"_Node_HW_RAN_ANT"+'_'+nodeAntennaSeq;
				                			   InsertQuery="insert into " + attributeTable  +" (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR) "
				                			   		+ "values('" + vcodeid +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("ANTENNADEVICENO") +"','" + vhmap.get("PRODNR") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"','" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("ANTENNADEVICETYPE") +"','" + vhmap.get("ISSUENUMBER") +"','" + vhmap.get("BOMCODE") +"','" + vhmap.get("EXTINFO") +"','" + vhmap.get("MODEL") +"','" + vhmap.get("SERIALNUMBEREX") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"' ,'0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1',sysdate,'RAN','" + provider +"') ";
				                			   nodeAntennaSeq++;
				                	   }
				                	 if (attributeTable =="NODE_LCELL" ) {
				                		   vcodeid=currentYear+"_Node_HW_RAN_LCELL"+'_'+nodeLCellSeq;
				                		   InsertQuery="insert into " + attributeTable  +" (LCELL_ID,LOCALCELLID,CELLNAME,CELLRADIUS,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,ENODEBFUNCTIONNAME,NBCELLFLAG,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("LOCALCELLID") +"','" + vhmap.get("CELLNAME") +"','" + vhmap.get("CELLRADIUS") +"','" + vhmap.get("FREQBAND") +"','" + vhmap.get("ULEARFCNCFGIND") +"','" + vhmap.get("ULEARFCN") +"','" + vhmap.get("DLEARFCN") +"','" + vhmap.get("ULBANDWIDTH") +"','" + vhmap.get("DLBANDWIDTH") +"','" + vhmap.get("CELLID") +"','" + vhmap.get("PHYCELLID") +"','" + vhmap.get("FDDTDDIND") +"','" + vhmap.get("ENODEBFUNCTIONNAME") +"','" + vhmap.get("NBCELLFLAG") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1',sysdate,'RAN','" + provider +"' ) ";
				                		   nodeLCellSeq++;
				                	   }
				                	  if (attributeTable =="NODE_RRN" ) {
				                		   vcodeid=currentYear+"_Node_HW_RAN_RRN"+'_'+nodeRRNSeq;
				                		   InsertQuery="insert into " + attributeTable  +" (RRN_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
					                		   		+ "values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("SITENAME") +"','" + vhmap.get("SITETYPE") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"' ,'" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','RAN','" + provider +"') ";
				                		   nodeRRNSeq++;
				                	   }
				                	   if (attributeTable =="NODE_ENODEBCELL" ) {
				                		   vcodeid=currentYear+"_Node_HW_RAN_ENODEBCELL"+'_'+nodeENodeBSeq;
		                						InsertQuery="insert into " + attributeTable  +"(ENODEBCELL_ID,LOCALCELLID,CELLNAME,SECTORID,CSGIND,CYCLEPREFIX,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,TAC,ADDITIONALSPECTRUMEMISSION,NBCELLFLAG,ENODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR )"
		                								+ "values('" + vcodeid +"','" + vhmap.get("LOCALCELLID") +"','" + vhmap.get("CELLNAME") +"','" + vhmap.get("SECTORID") +"','" + vhmap.get("CSGIND") +"','" + vhmap.get("CYCLEPREFIX") +"','" + vhmap.get("FREQBAND") +"','" + vhmap.get("ULEARFCNCFGIND") +"','" + vhmap.get("ULEARFCN") +"','" + vhmap.get("DLEARFCN") +"','" + vhmap.get("ULBANDWIDTH") +"','" + vhmap.get("DLBANDWIDTH") +"','" + vhmap.get("CELLID") +"','" + vhmap.get("PHYCELLID") +"','" + vhmap.get("FDDTDDIND") +"','" + vhmap.get("TAC") +"','" + vhmap.get("ADDITIONALSPECTRUMEMISSION") +"','" + vhmap.get("NBCELLFLAG") +"','" + vhmap.get("ENODEBFUNCTIONNAME") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','RAN','" + provider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
		                						nodeENodeBSeq++;
				                	   }
				                	   if (attributeTable =="NODE_NODEBCELL" ) {
				                		   vcodeid=currentYear+"_Node_HW_RAN_NODEBCELL"+'_'+nodeNodeBSeq;
				                		   InsertQuery="insert into " + attributeTable  +" (NODEBCELL_ID,LOCELL,USERLABEL,SITENO,SECNO,RADIUS,HORAD,BBPOOLTYPE,ULGROUPNO,CABINETNO1,SUBRACKNO1,SLOTNO1,CABINETNO2,SUBRACKNO2,SLOTNO2,ULFREQ,DLFREQ,MAXPOWER,HSDPA,DI,HIGHSPEEDMODE,SPEEDRATE,NODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("LOCELL") +"','" + vhmap.get("USERLABEL") +"','" + vhmap.get("SITENO") +"','" + vhmap.get("SECNO") +"','" + vhmap.get("RADIUS") +"','" + vhmap.get("HORAD") +"','" + vhmap.get("BBPOOLTYPE") +"','" + vhmap.get("ULGROUPNO") +"','" + vhmap.get("CABINETNO1") +"','" + vhmap.get("SUBRACKNO1") +"','" + vhmap.get("SLOTNO1") +"','" + vhmap.get("CABINETNO2") +"','" + vhmap.get("SUBRACKNO2") +"','" + vhmap.get("SLOTNO2") +"','" + vhmap.get("ULFREQ") +"','" + vhmap.get("DLFREQ") +"','" + vhmap.get("MAXPOWER") +"','" + vhmap.get("HSDPA") +"','" + vhmap.get("DI") +"','" + vhmap.get("HIGHSPEEDMODE") +"','" + vhmap.get("SPEEDRATE") +"','" + vhmap.get("NODEBFUNCTIONNAME") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"' ,'0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','RAN','" + provider +"') ";
				                		   nodeNodeBSeq++;
				                	   }
				                	   if (attributeTable =="NODE_NBInterfaces" ) {
				                		   vcodeid=currentYear+"_Node_HW_RAN_NBInterfaces"+'_'+nodeNBInterfaceSeq;
				                		   InsertQuery="insert into " + attributeTable  +" (NBINTERFACE_ID,INTERFACETYPE,VERSION,ISUSED,NMSVENDOR,NMSNAME,REMARK,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
					                		   		+ "values('" + vcodeid +"','" + vhmap.get("INTERFACETYPE") +"','" + vhmap.get("VERSION") +"','" + vhmap.get("ISUSED") +"','" + vhmap.get("NMSVENDOR") +"','" + vhmap.get("NMSNAME") +"','" + vhmap.get("REMARK") +"','" + codeID +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','RAN','" + provider +"' ) ";
				                		   nodeNBInterfaceSeq++;
				                	   }
				                	   
				                	   if (InsertQuery != null ) {
					                	  PreparedStatement stmtattr = parserCon.prepareStatement(InsertQuery); 
					                	  stmtattr.executeUpdate();
					                	  stmtattr.close();
				                	   }
				                	 // System.out.println("\n");
				                  }  
				                     
				              }
							}

			             }
					}
				
					stmtp = almCon.prepareStatement("UPDATE SEQ_TABLE SET NODE_RACK = '"+NodeRackSeq+"' ,NODE_ACTIVE_ATTRIBUTE = '"+NodeActiveAttrSeq+"' , NODE_FRAME = '"+NodeFrameSeq+"'"
							+ " , NODE_SLOT = '"+nodeSlotSeq+"', NODE_BOARD = '"+nodeBoardSeq+"' , NODE_PORT ='"+nodePortSeq+"' , NODE_HOSTVER='"+nodeHostVerSeq+"' "
									+ " , NODE_CABINET ='"+nodeCabinetSeq+"' , NODE_ACCESSORY ='"+nodeAccessorySeq+"' , NODE_HOST='"+nodeHostSeq+"' , NODE_SUBRACK ='"+nodeSubRackSeq+"' ,"
											+ " NODE_GCELL ='"+nodeGCellSeq+"' , NODE_BTS='"+nodeBTSSeq+"' ,  NODE_UCELL ='"+nodeUCellSeq+"' , NODE_ANTENNA = '"+nodeAntennaSeq+"' , NODE_LCELL ='"+nodeLCellSeq+"' , NODE_RRN = '"+nodeRRNSeq+"' , NODE_ENODEBCELL='"+nodeENodeBSeq+"' , NODE_NODEBCELL='"+nodeNodeBSeq+"' , NODE_NBInterfaces='"+nodeNBInterfaceSeq+"' ");
				  	stmtp.executeUpdate();
				  	stmtp.close();
				  	
				  	
					objReader.close();	
					
					
				System.out.println("Reading Data From " + filename + " COMPLETED");
		        }   
	        		
	        catch (IOException e) {
					e.printStackTrace();
					logger.info("Error readfile: "+ filename +"   "+e);
				} 
		       catch(Exception e) {
		  	      System.err.println(e);
		  	   logger.info("Error : "+ filename +"   "+e);
		  	     e.printStackTrace();
		  	  } 
			return null;
		 
	 }
	 
	 
	 public static HashMap getMapColumnsHostver(String str) {
		 System.out.println("first str " +str);
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 hmap.put("HOSTVERTYPE", "0");
		 hmap.put("HOSTVER", "0");
		 hmap.put("SDESC", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String val=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {val = "0";} else {val = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"HostVerType")) {hmap.put( key.toUpperCase(), val);}
		 if (StringUtils.equalsIgnoreCase (key,"HostVer")) {hmap.put( key.toUpperCase(), val);}
		 if (StringUtils.equalsIgnoreCase (key,"sDesc")) {hmap.put( key.toUpperCase(), val);}
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;

	 }
	 
	 public static HashMap getMapColumnsRack(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "RACKNO", "0");
		 hmap.put( "INVENTORYUNITID", "0");
		 hmap.put( "RACKTYPE", "0");
		 hmap.put( "INVENTORYUNITTYPE", "0");
		 hmap.put( "VENDORUNITFAMILYTYPE", "0");
		 hmap.put( "VENDORUNITTYPENUMBER", "0");
		 hmap.put( "VENDORNAME", "0");
		 hmap.put( "SERIALNUMBER", "0");
		 hmap.put( "HARDWAREVERSION", "0");
		 hmap.put( "DATEOFMANUFACTURE", "0");
		 hmap.put( "DATEOFLASTSERVICE", "0");
		 hmap.put( "UNITPOSITION", "0");
		 hmap.put( "MANUFACTURERDATA", "0");
		 hmap.put( "MODEL", "0");
		 hmap.put( "USERLABEL", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"RackNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"RackType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitFamilyType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitTypeNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SerialNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HardwareVersion")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfManufacture")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfLastService")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UnitPosition")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ManufacturerData")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Model")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UserLabel")) {hmap.put( key.toUpperCase(), value);}
	 
		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getMapColumnsFrame(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "RACKNO", "0");
		 hmap.put( "FRAMENO", "0");
		 hmap.put( "INVENTORYUNITID", "0");
		 hmap.put( "FRAMETYPE", "0");
		 hmap.put( "RACKFRAMENO", "0");
		 hmap.put( "MODULENO", "0");
		 hmap.put( "INVENTORYUNITTYPE", "0");
		 hmap.put( "VENDORUNITFAMILYTYPE", "0");
		 hmap.put( "VENDORUNITTYPENUMBER", "0");
		 hmap.put( "VENDORNAME", "0");
		 hmap.put( "SERIALNUMBER", "0");
		 hmap.put( "HARDWAREVERSION", "0");
		 hmap.put( "DATEOFMANUFACTURE", "0");
		 hmap.put( "DATEOFLASTSERVICE", "0");
		 hmap.put( "UNITPOSITION", "0");
		 hmap.put( "MANUFACTURERDATA", "0");
		 hmap.put( "MODEL", "0");
		 hmap.put( "USERLABEL", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");

		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"RackNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"FrameNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitId")) {hmap.put(key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"FrameType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"RackFrameNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ModuleNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitFamilyType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitTypeNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SerialNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HardwareVersion")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfManufacture")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfLastService")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UnitPosition")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ManufacturerData")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Model")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UserLabel")) {hmap.put( key.toUpperCase(),value);}
	 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
		 
		
		return hmap;
				
		
	 }
	 
	 
	 public static HashMap getMapColumnsSlot(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "SITEINDEX", "0");
		 hmap.put( "CABINETNO", "0");
		 hmap.put( "SUBRACKNO", "0");
		 hmap.put( "RACKNO", "0");
		 hmap.put( "FRAMENO", "0");
		 hmap.put( "SLOTNO", "0");
		 hmap.put( "SLOTPOS", "0");
		 hmap.put( "INVENTORYUNITID", "0");
		 hmap.put( "INVENTORYUNITTYPE", "0");
		 hmap.put( "VENDORUNITFAMILYTYPE", "0");
		 hmap.put( "VENDORUNITTYPENUMBER", "0");
		 hmap.put( "VENDORNAME", "0");
		 hmap.put( "SERIALNUMBER", "0");
		 hmap.put( "HARDWAREVERSION", "0");
		 hmap.put( "DATEOFMANUFACTURE", "0");
		 hmap.put( "DATEOFLASTSERVICE", "0");
		 hmap.put( "UNITPOSITION", "0");
		 hmap.put( "MANUFACTURERDATA", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		// System.out.println("first space " +crntLine);
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 //System.out.println("col "+ col);
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"SiteIndex")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CabinetNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SubrackNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"RackNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"FrameNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SlotNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SlotPos")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitFamilyType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitTypeNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorName")) {hmap.put( key.toUpperCase(),value);}
		 if (StringUtils.equalsIgnoreCase (key,"SerialNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HardwareVersion")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfManufacture")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfLastService")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UnitPosition")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ManufacturerData")) {hmap.put( key.toUpperCase(), value);}
	 
		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getMapColumnsBoard(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "SITEINDEX", "0");
		 hmap.put( "CABINETNO", "0");
		 hmap.put( "SUBRACKNO", "0");
		 hmap.put( "RACKNO", "0");
		 hmap.put( "FRAMENO", "0");
		 hmap.put( "SLOTNO", "0");
		 hmap.put( "SLOTPOS", "0");
		 hmap.put( "SUBSLOTNO", "0");
		 hmap.put( "INVENTORYUNITID", "0");
		 hmap.put( "MODULENO", "0");
		 hmap.put( "BOARDNAME", "0");
		 hmap.put( "BOARDTYPE", "0");
		 hmap.put( "INVENTORYUNITTYPE", "0");
		 hmap.put( "VENDORUNITFAMILYTYPE", "0");
		 hmap.put( "VENDORUNITTYPENUMBER", "0");
		 hmap.put( "VENDORNAME", "0");
		 hmap.put( "SERIALNUMBER", "0");
		 hmap.put( "HARDWAREVERSION", "0");
		 hmap.put( "DATEOFMANUFACTURE", "0");
		 hmap.put( "DATEOFLASTSERVICE", "0");
		 hmap.put( "UNITPOSITION", "0");
		 hmap.put( "MANUFACTURERDATA", "0");
		 hmap.put( "SOFTVER", "0");
		 hmap.put( "LOGICVER", "0");
		 hmap.put( "BIOSVER", "0");
		 hmap.put( "BIOSVEREX", "0");
		 hmap.put( "LANVER", "0");
		 hmap.put( "MBUSVER", "0");
		 hmap.put( "ISSUENUMBER", "0");
		 hmap.put( "BOMCODE", "0");
		 hmap.put( "MODEL", "0");
		 hmap.put( "USERLABEL", "0");
		 hmap.put( "EXTINFO", "0");
		 hmap.put( "APDEVINFO", "0");
		 hmap.put( "WORKMODE", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"SiteIndex")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CabinetNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SubrackNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"RackNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"FrameNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SlotNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SlotPos")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SubSlotNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ModuleNo")) {hmap.put( key.toUpperCase(),value);}
		 if (StringUtils.equalsIgnoreCase (key,"BoardName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BoardType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitFamilyType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitTypeNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SerialNumber")) {hmap.put( key.toUpperCase(),value);}
		 if (StringUtils.equalsIgnoreCase (key,"HardwareVersion")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfManufacture")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfLastService")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UnitPosition")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ManufacturerData")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SoftVer")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"LogicVer")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BiosVer")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BiosVerEx")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"LANVer")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"MBUSVer")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"IssueNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BOMCode")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Model")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UserLabel")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ExtInfo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ApDevInfo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"WorkMode")) {hmap.put( key.toUpperCase(), value);}
		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getMapColumnsPort(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "SITEINDEX", "0");
		 hmap.put( "CABINETNO", "0");
		 hmap.put( "SUBRACKNO", "0");
		 hmap.put( "RACKNO", "0");
		 hmap.put( "FRAMENO", "0");
		 hmap.put( "SLOTNO", "0");
		 hmap.put( "SLOTPOS", "0");
		 hmap.put( "SUBSLOTNO", "0");
		 hmap.put( "VENDORUNITFAMILYTYPE", "0");
		 hmap.put( "INVENTORYUNITID", "0");
		 hmap.put( "PORTNO", "0");
		 hmap.put( "HARDWAREVERSION", "0");
		 hmap.put( "SERIALNUMBER", "0");
		 hmap.put( "INVENTORYUNITTYPE", "0");
		 hmap.put( "VENDORNAME", "0");
		 hmap.put( "VENDORUNITTYPENUMBER", "0");
		 hmap.put( "DATEOFMANUFACTURE", "0");
		 hmap.put( "DATEOFLASTSERVICE", "0");
		 hmap.put( "UNITPOSITION", "0");
		 hmap.put( "MACADDR", "0");
		 hmap.put( "MANUFACTURERDATA", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"SiteIndex")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CabinetNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SubrackNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"RackNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"FrameNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SlotNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SlotPos")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SubSlotNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitFamilyType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"PortNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HardwareVersion")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SerialNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitTypeNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfManufacture")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfLastService")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UnitPosition")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"MacAddr")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ManufacturerData")) {hmap.put( key.toUpperCase(), value);}
	 
		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getMapColumnsCabinet(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "SITEINDEX", "0");
		 hmap.put( "CABINETNO", "0");
		 hmap.put( "INVENTORYUNITID", "0");
		 hmap.put( "RACKTYPE", "0");
		 hmap.put( "BOMRACKTYPE", "0");
		 hmap.put( "INVENTORYUNITTYPE", "0");
		 hmap.put( "VENDORUNITFAMILYTYPE", "0");
		 hmap.put( "VENDORUNITTYPENUMBER", "0");
		 hmap.put( "VENDORNAME", "0");
		 hmap.put( "SERIALNUMBER", "0");
		 hmap.put( "HARDWAREVERSION", "0");
		 hmap.put( "DATEOFMANUFACTURE", "0");
		 hmap.put( "DATEOFLASTSERVICE", "0");
		 hmap.put( "UNITPOSITION", "0");
		 hmap.put( "MANUFACTURERDATA", "0");
		 hmap.put( "ISSUENUMBER", "0");
		 hmap.put( "BOMCODE", "0");
		 hmap.put( "EXTINFO", "0");
		 hmap.put( "MODEL", "0");
		 hmap.put( "USERLABEL", "0");
		 hmap.put( "SHAREMODE", "0");
		 hmap.put( "CLEICODE", "0");
		 hmap.put( "BOM", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"SiteIndex")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CabinetNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"RackType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitFamilyType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitTypeNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SerialNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HardwareVersion")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfManufacture")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfLastService")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UnitPosition")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ManufacturerData")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BOMCode")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ExtInfo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Model")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UserLabel")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"IssueNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BOMRackType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ShareMode")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Cleicode")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BOM")) {hmap.put( key.toUpperCase(), value);}
	 
		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getMapColumnsAccessory(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "RACKPOSITION", "0");
		 hmap.put( "INVENTORYUNITID", "0");
		 hmap.put( "VENDORUNITFAMILYTYPE", "0");
		 hmap.put( "VENDORUNITTYPENUMBER", "0");
		 hmap.put( "VENDORNAME", "0");
		 hmap.put( "SERIALNUMBER", "0");
		 hmap.put( "HARDWAREVERSION", "0");
		 hmap.put( "SOFTVER", "0");
		 hmap.put( "DATEOFMANUFACTURE", "0");
		 hmap.put( "DATEOFLASTSERVICE", "0");
		 hmap.put( "MANUFACTURERDATA", "0");
		 hmap.put( "ACCESSORYTYPE", "0");
		 hmap.put( "ADDTIONALINFORMATION", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"RackPosition")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitFamilyType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitTypeNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SerialNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HardwareVersion")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SoftVer")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfManufacture")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfLastService")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ManufacturerData")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"AccessoryType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"AddtionalInformation")) {hmap.put( key.toUpperCase(), value);}
		 
	 
		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getMapColumnsHost(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "RACKPOSITION", "0");
		 hmap.put( "INVENTORYUNITID", "0");
		 hmap.put( "VENDORUNITFAMILYTYPE", "0");
		 hmap.put( "VENDORUNITTYPENUMBER", "0");
		 hmap.put( "VENDORNAME", "0");
		 hmap.put( "SERIALNUMBER", "0");
		 hmap.put( "HARDWAREVERSION", "0");
		 hmap.put( "SOFTVER", "0");
		 hmap.put( "DATEOFMANUFACTURE", "0");
		 hmap.put( "DATEOFLASTSERVICE", "0");
		 hmap.put( "MANUFACTURERDATA", "0");
		 hmap.put( "HOSTNAME", "0");
		 hmap.put( "NUMBEROFCPU", "0");
		 hmap.put( "MEMSIZE", "0");
		 hmap.put( "HARDDISKSIZE", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"RackPosition")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitFamilyType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitTypeNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SerialNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HardwareVersion")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SoftVer")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfManufacture")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfLastService")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ManufacturerData")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HostName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"NumberOfCpu")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"MemSize")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HardDiskSize")) {hmap.put( key.toUpperCase(), value);}
	 
		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getMapColumnsSubrack(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "SITEINDEX", "0");
		 hmap.put( "CABINETNO", "0");
		 hmap.put( "SUBRACKNO", "0");
		 hmap.put( "INVENTORYUNITID", "0");
		 hmap.put( "FRAMETYPE", "0");
		 hmap.put( "RACKTYPE", "0");
		 hmap.put( "BOMRACKTYPE", "0");
		 hmap.put( "RACKFRAMENO", "0");
		 hmap.put( "MODULENO", "0");
		 hmap.put( "INVENTORYUNITTYPE", "0");
		 hmap.put( "VENDORUNITFAMILYTYPE", "0");
		 hmap.put( "VENDORUNITTYPENUMBER", "0");
		 hmap.put( "VENDORNAME", "0");
		 hmap.put( "SERIALNUMBER", "0");
		 hmap.put( "HARDWAREVERSION", "0");
		 hmap.put( "DATEOFMANUFACTURE", "0");
		 hmap.put( "DATEOFLASTSERVICE", "0");
		 hmap.put( "UNITPOSITION", "0");
		 hmap.put( "MANUFACTURERDATA", "0");
		 hmap.put( "USERLABEL", "0");
		 hmap.put( "BOMCODE", "0");
		 hmap.put( "MODEL", "0");
		 hmap.put( "ISSUENUMBER", "0");
		 hmap.put( "BOMFRAMETYPE", "0");
		 hmap.put( "CLEICODE", "0");
		 hmap.put( "BOM", "0");
		 hmap.put( "EXTINFO", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"SiteIndex")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CabinetNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"RackType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BOMRackType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitFamilyType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitTypeNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SerialNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HardwareVersion")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfManufacture")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfLastService")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UnitPosition")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ManufacturerData")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"IssueNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BOMCode")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ExtInfo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Model")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BOMFrameType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"RackFrameNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"FrameType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ModuleNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SubrackNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UserLabel")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Cleicode")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BOM")) {hmap.put( key.toUpperCase(), value);}

		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 
	 public static HashMap getMapColumnsGcell(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "CELLID", "0");
		 hmap.put( "CELLNAME", "0");
		 hmap.put( "MCC", "0");
		 hmap.put( "MNC", "0");
		 hmap.put( "LAC", "0");
		 hmap.put( "CI", "0");
		 hmap.put( "NCC", "0");
		 hmap.put( "BCC", "0");
		 hmap.put( "TYPE", "0");
		 hmap.put( "BCCHNO", "0");
		 hmap.put( "BASEBANDPOLICY", "0");
		 hmap.put( "BASEBANDEQMID", "0");
		 hmap.put( "GBTSFUNCTIONNAME", "0");
		 hmap.put( "GLOCELLID", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"CellId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CellName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"MCC")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"MNC")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"LAC")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CI")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"NCC")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BCC")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"TYPE")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BCCHNO")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"GloCellId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BaseBandPolicy")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BaseBandEqmId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"GbtsFunctionName")) {hmap.put( key.toUpperCase(), value);}
		 		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 
	 public static HashMap getMapColumnsBts(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "SITEINDEX", "0");
		 hmap.put( "SITENAME", "0");
		 hmap.put( "SITETYPE", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"SiteIndex")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SiteName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SiteType")) {hmap.put( key.toUpperCase(), value);}
		 		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getMapColumnsUcell(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "CELLID", "0");
		 hmap.put( "CELLNAME", "0");
		 hmap.put( "LOCELL", "0");
		 hmap.put( "NODEBFUNCTIONNAME", "0");
		 hmap.put( "ULFREQ", "0");
		 hmap.put( "DLFREQ", "0");
		 hmap.put( "MAXPOWER", "0");
		 hmap.put( "USERLABEL", "0");
		 hmap.put( "MAXTXPOWER", "0");
		 hmap.put( "UARFCNUPLINK", "0");
		 hmap.put( "UARFCNDOWNLINK", "0");
		 hmap.put( "PSCRAMBCODE", "0");
		 hmap.put( "NODEBNAME", "0");
		 hmap.put( "LAC", "0");
		 hmap.put( "SAC", "0");
		 hmap.put( "RAC", "0");
		 hmap.put( "MANUFACTURERDATA", "0");
		 hmap.put( "RADIUS", "0");
		 hmap.put( "HORAD", "0");
		 hmap.put( "DI", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"CellId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Locell")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Radius")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HoRad")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DI")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"NodeBFunctionName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UlFreq")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DlFreq")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"MaxPower")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Cellname")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"userlabel")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Maxtxpower")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"LAC")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SAC")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"RAC")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"NodeBname")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"MANUFACTURERDATA")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"PSCRAMBCODE")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UARFCNDOWNLINK")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UARFCNUPLINK")) {hmap.put( key.toUpperCase(), value);}
		 		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 
	 public static HashMap getMapColumnsAntenna(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "INVENTORYUNITID", "0");
		 hmap.put( "INVENTORYUNITTYPE", "0");
		 hmap.put( "ANTENNADEVICENO", "0");
		 hmap.put( "PRODNR", "0");
		 hmap.put( "VENDORUNITFAMILYTYPE", "0");
		 hmap.put( "VENDORUNITTYPENUMBER", "0");
		 hmap.put( "VENDORNAME", "0");
		 hmap.put( "SERIALNUMBER", "0");
		 hmap.put( "HARDWAREVERSION", "0");
		 hmap.put( "DATEOFMANUFACTURE", "0");
		 hmap.put( "DATEOFLASTSERVICE", "0");
		 hmap.put( "UNITPOSITION", "0");
		 hmap.put( "MANUFACTURERDATA", "0");
		 hmap.put( "ANTENNADEVICETYPE", "0");
		 hmap.put( "ISSUENUMBER", "0");
		 hmap.put( "BOMCODE", "0");
		 hmap.put( "EXTINFO", "0");
		 hmap.put( "MODEL", "0");
		 hmap.put( "SERIALNUMBEREX", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"InventoryUnitType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"AntennaDeviceNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ProdNr")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitFamilyType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorUnitTypeNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"VendorName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SerialNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HardwareVersion")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfManufacture")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DateOfLastService")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UnitPosition")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ManufacturerData")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"AntennaDeviceType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"IssueNumber")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BOMCode")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ExtInfo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Model")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SerialNumberEx")) {hmap.put( key.toUpperCase(), value);}
		 
		 		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 
	 public static HashMap getMapColumnsLcell(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "LOCALCELLID", "0");
		 hmap.put( "CELLNAME", "0");
		 hmap.put( "CELLRADIUS", "0");
		 hmap.put( "FREQBAND", "0");
		 hmap.put( "ULEARFCNCFGIND", "0");
		 hmap.put( "ULEARFCN", "0");
		 hmap.put( "DLEARFCN", "0");
		 hmap.put( "ULBANDWIDTH", "0");
		 hmap.put( "DLBANDWIDTH", "0");
		 hmap.put( "CELLID", "0");
		 hmap.put( "PHYCELLID", "0");
		 hmap.put( "FDDTDDIND", "0");
		 hmap.put( "ENODEBFUNCTIONNAME", "0");
		 hmap.put( "NBCELLFLAG", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"LocalCellId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CellName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CellRadius")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"FreqBand")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UlEarfcnCfgInd")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UlEarfcn")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DlEarfcn")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UlBandWidth")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DlBandWidth")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CellId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"PhyCellId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"FddTddInd")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ENodeBFunctionName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"NbCellFlag")) {hmap.put( key.toUpperCase(), value);}
		 
		 		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getMapColumnsRRN(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "SITEINDEX", "0");
		 hmap.put( "SITENAME", "0");
		 hmap.put( "SITETYPE", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 //System.out.println("col2 "+ col2);
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"SiteIndex")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SiteName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SiteType")) {hmap.put( key.toUpperCase(), value);}
		 		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getMapColumnsENodebCell(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "LOCALCELLID", "0");
		 hmap.put( "CELLNAME", "0");
		 hmap.put( "SECTORID", "0");
		 hmap.put( "CSGIND", "0");
		 hmap.put( "CYCLEPREFIX", "0");
		 hmap.put( "FREQBAND", "0");
		 hmap.put( "ULEARFCNCFGIND", "0");
		 hmap.put( "ULEARFCN", "0");
		 hmap.put( "DLEARFCN", "0");
		 hmap.put( "ULBANDWIDTH", "0");
		 hmap.put( "DLBANDWIDTH", "0");
		 hmap.put( "CELLID", "0");
		 hmap.put( "PHYCELLID", "0");
		 hmap.put( "FDDTDDIND", "0");
		 hmap.put( "TAC", "0");
		 hmap.put( "ADDITIONALSPECTRUMEMISSION", "0");
		 hmap.put( "NBCELLFLAG", "0");
		 hmap.put( "ENODEBFUNCTIONNAME", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		// System.out.println("first space " +col1);
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 //System.out.println("col2 "+ col2);
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (key,"LocalCellId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CellName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SectorId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CsgInd")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CyclePrefix")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"FreqBand")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UlEarfcnCfgInd")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UlEarfcn")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DlEarfcn")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UlBandWidth")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DlBandWidth")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CellId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"PhyCellId")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"FddTddInd")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"TAC")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"AdditionalSpectrumEmission")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"NbCellFlag")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"ENodeBFunctionName")) {hmap.put( key.toUpperCase(), value);}
		 		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getMapColumnsNodebCell(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "LOCELL", "0");
		 hmap.put( "USERLABEL", "0");
		 hmap.put( "SITENO", "0");
		 hmap.put( "SECNO", "0");
		 hmap.put( "RADIUS", "0");
		 hmap.put( "HORAD", "0");
		 hmap.put( "BBPOOLTYPE", "0");
		 hmap.put( "ULGROUPNO", "0");
		 hmap.put( "CABINETNO1", "0");
		 hmap.put( "SUBRACKNO1", "0");
		 hmap.put( "SLOTNO1", "0");
		 hmap.put( "CABINETNO2", "0");
		 hmap.put( "SUBRACKNO2", "0");
		 hmap.put( "SLOTNO2", "0");
		 hmap.put( "ULFREQ", "0");
		 hmap.put( "DLFREQ", "0");
		 hmap.put( "MAXPOWER", "0");
		 hmap.put( "HSDPA", "0");
		 hmap.put( "DI", "0");
		 hmap.put( "HIGHSPEEDMODE", "0");
		 hmap.put( "SPEEDRATE", "0");
		 hmap.put( "NODEBFUNCTIONNAME", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		// System.out.println("first space " +col1);
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 //System.out.println("col2 "+ col2);
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (key,"Locell")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UserLabel")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SiteNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SecNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Radius")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HoRad")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"BbPoolType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UlgroupNo")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CabinetNo1")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SubRackNo1")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SlotNo1")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"CabinetNo2")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SubRackNo2")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SlotNo2")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"UlFreq")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DlFreq")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"MaxPower")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Hsdpa")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"DI")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"HighSpeedMode")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"SpeedRate")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"NodeBFunctionName")) {hmap.put( key.toUpperCase(), value);}
		 
		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 
	 
	 public static HashMap getMapColumnsNbInterfaces(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "INTERFACETYPE", "0");
		 hmap.put( "VERSION", "0");
		 hmap.put( "ISUSED", "0");
		 hmap.put( "NMSVENDOR", "0");
		 hmap.put( "NMSNAME", "0");
		 hmap.put( "REMARK", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String crntLine = str;
		 String col = null;
		 int n = crntLine.indexOf("<ROW");
		 crntLine=crntLine.substring(n+5, crntLine.length());
		// System.out.println("first space " +col1);
		 
		 
		 String value=null;
		 n = crntLine.indexOf("=\"");
		 while (n != -1) {
		 String key = crntLine.substring(0, n).trim();
		 col=crntLine.substring(n+2, crntLine.length());
		 //System.out.println("col2 "+ col2);
		 n = col.indexOf("\"");
		 if  (n == 0) {value = "0";} else {value = col.substring(0, n).trim();}
		 
		 if (StringUtils.equalsIgnoreCase (key,"InterfaceType")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Version")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"IsUsed")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"NMSVendor")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"NMSName")) {hmap.put( key.toUpperCase(), value);}
		 if (StringUtils.equalsIgnoreCase (key,"Remark")) {hmap.put( key.toUpperCase(), value);}
		 
		 crntLine=col.substring(n+1, col.length());
		 n = crntLine.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static String[] getNodeIdNameType(String RowLine) throws IOException {
		 String [] result = null;
		
		 String tempNamee =null;
		 result =new String[4];
		 
		 tempNamee=RowLine;
		 result[3]="0";
		 
		 int n = tempNamee.indexOf("BTS=");
		 if (n > -1) { 
			 tempNamee=tempNamee.substring(n+4, tempNamee.length());
		 } 
		 else {
			 n = tempNamee.indexOf("NE=");
			 if (n == -1) { n = tempNamee.indexOf("OS=");}
			 tempNamee=tempNamee.substring(n+3, tempNamee.length());
		 }
		 
		  n = tempNamee.indexOf("\"");
		  result[0]=tempNamee.substring(0, n);
		 
		   n = tempNamee.indexOf("NEName=\"");
		   tempNamee=tempNamee.substring(n+8, tempNamee.length());
		   n = tempNamee.indexOf("\"");
		   result[1]=tempNamee.substring(0, n);
			  
		   n = tempNamee.indexOf("NEType=\"");
		   tempNamee=tempNamee.substring(n+8, tempNamee.length());
		   n = tempNamee.indexOf("\"");
		   result[2]=tempNamee.substring(0, n);

		 return result;
	 }

	
	 public static String getAttributeTableName(String str) {
		 
		 String localresult=null;
		 
		if (StringUtils.equalsIgnoreCase(str,"Rack")) {	
			 localresult="NODE_RACK";
			 
		 }
		if (StringUtils.equalsIgnoreCase(str,"Frame")) {	
			 localresult="NODE_FRAME";
		 }	
		if (StringUtils.equalsIgnoreCase(str,"Slot")) {	
			 localresult="NODE_SLOT";			 
		 }
		if (StringUtils.equalsIgnoreCase(str,"Board")) {	
			 localresult="NODE_BOARD";
		 }
		if (StringUtils.equalsIgnoreCase(str,"Port")) {	
			 localresult="NODE_PORT";
		 }
		if (StringUtils.equalsIgnoreCase(str,"HostVer")) {	
			 localresult="NODE_HOSTVER";
		 }
		if (StringUtils.equalsIgnoreCase(str,"Cabinet")) {	
			 localresult="NODE_CABINET";
		 }
		if (StringUtils.equalsIgnoreCase(str,"Subrack")) {	
			 localresult="NODE_SUBRACK";
		 }
		if (StringUtils.equalsIgnoreCase(str,"GCELL")) {	
			 localresult="NODE_GCELL";
		 }
		if (StringUtils.equalsIgnoreCase(str,"UCELL")) {	
			 localresult="NODE_UCELL";
		 }
		if (StringUtils.equalsIgnoreCase(str,"LCELL")) {	
			 localresult="NODE_LCELL";
		 }
		if (StringUtils.equalsIgnoreCase(str,"eNodeBCell")) {	
			 localresult="NODE_ENODEBCELL";
		 }
		if (StringUtils.equalsIgnoreCase(str,"NodeBCell")) {	
			 localresult="NODE_NODEBCELL";
		 }
		if (StringUtils.equalsIgnoreCase(str,"BTS")) {	
			 localresult="NODE_BTS";
		 }
		if (StringUtils.equalsIgnoreCase(str,"Antenna")) {	
			 localresult="NODE_ANTENNA";
		 }
		if (StringUtils.equalsIgnoreCase(str,"RRN")) {	
			 localresult="NODE_RRN";
		 }
		if (StringUtils.equalsIgnoreCase(str,"Accessory")) {	
			 localresult="NODE_ACCESSORY";
		 }
		 if (StringUtils.equalsIgnoreCase(str, "Host")) {
			 localresult="NODE_HOST";
		 }
		 if (StringUtils.equalsIgnoreCase(str, "North Bound Interfaces")) {
			 localresult="NODE_NBInterfaces";
		 }
		 
		 return localresult;
				
		
	 }
	 
	 public static String createDate(String str) 
	    { 
		 String res=null;
		 String vstr =null;
		 String year=null;
		 String month =null;
		 String day=null;

		 if (str.contains("/")) {
			 vstr=str;
			 int n = vstr.indexOf("/");
			 year=vstr.substring(0, n);
			 vstr=vstr.substring(n+1, vstr.length());
			 n = vstr.indexOf("/");
			 month=vstr.substring(0, n);
			 day=vstr.substring((n+1), (n+3)).trim();
			 res=year + "-"+ month +"-"+ day;
		 } else {
				 if (str.contains("-")) {
					 //if (str.length() >8) {res=str.substring(0, 10);}
					// else {res=str.substring(0, 8);}
					 vstr=str;
					 int n = vstr.indexOf("-");
					 year=vstr.substring(0, n);
					 vstr=vstr.substring(n+1, vstr.length());
					 n = vstr.indexOf("-");
					 month=vstr.substring(0, n);
					 if (month.length() <2) {month= "0"+ month;}
					 
					 
					 if ((n+3) > vstr.length()) {day=vstr.substring((n+1), (n+2)).trim();day= "0"+ day;}
					 else {day=vstr.substring((n+1), (n+3)).trim();}
					 if (day.length() <2) {day= "0"+ day;}
					 res=year + "-"+ month +"-"+ day;
				 } else
					 {

					 if (str.length() >0) {
						 res= "20"+str.substring(0, 2) + "-"+ str.substring(2, 4)+ "-"+ str.substring(4, 6);

					 }
					 else {
						 DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				        	LocalDateTime now = LocalDateTime.now();
				        	res=dateTimeFormatter.format(now);
					 	}
					 }
				 	
			 
		 }
		 
	        return res; 
	 } 
	 
	 
	 private static void copyFiles(File source, File dest) throws IOException {
		 try {
		 Files.copy(source.toPath(), dest.toPath(),StandardCopyOption.COPY_ATTRIBUTES,StandardCopyOption.REPLACE_EXISTING);
		 }
		 catch(IOException e) {
		 e.printStackTrace();
		 }
		}
 private static void deleteFiles(String source) throws InterruptedException {
	 	 System.gc();//Added this part
	 	 Thread.sleep(150);
		 File telecomfile = new File(source);
		 try {
		 FileUtils.forceDelete(telecomfile);
		 }
		 catch(IOException e) {
		 e.printStackTrace();
		 }
    
	}
	 
 




private static void GetDuplicateFileName(String vdomain , String vvendor) throws SQLException  {
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	int counter =0;
	int i=0;
	
	// select all file having duplicata entry and same filename >1
	String query1 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,count(*) counter from NODE_ACTIVE  group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR having count(*) >1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";  
    stmt1 = parserCon.createStatement();
    ResultSet rs1 = stmt1.executeQuery(query1);
    while (rs1.next()) {
    	//try {
                 // select all rows related to a file identified by rs1 having count >=1
				 String query2 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,count(*) counter from (select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR from NODE_ACTIVE where SITE_ID ='"+ rs1.getString("SITE_ID") +"' and CIRCLE_ID ='"+ rs1.getString("CIRCLE_ID") +"' and NODE_ID ='"+ rs1.getString("NODE_ID") +"' and NODE_NAME ='"+ rs1.getString("NODE_NAME") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' ) group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR having count(*) >=1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";  
		         stmt2 = parserCon.createStatement();
		         ResultSet rs2 = stmt2.executeQuery(query2);
		         // get all node_pk found duplicated
		         while (rs2.next()) {
		        	 counter =0;
		        	 counter= (int) Long.parseLong (rs2.getString("counter"));
		        	 i=0;
			        	    //Get old creation date of the same file and update rows with old creation date
			        	 	String query3 = "select node_pk,creation_date from NODE_ACTIVE where NODE_ID='"+ rs2.getString("NODE_ID") +"' and SITE_ID='"+ rs2.getString("SITE_ID") +"' and NODE_NAME ='"+ rs2.getString("NODE_NAME") +"' and CIRCLE_ID ='"+ rs2.getString("CIRCLE_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' order by node_pk asc";  
				            stmt3 = parserCon.createStatement();
				            //stmt3.setMaxRows(1); 
				            ResultSet rs3 = stmt3.executeQuery(query3);
				            while (rs3.next()) {       
				            	System.out.println(rs3.getString("node_pk") +":"+ rs3.getString("creation_date"));
				            	if (i==0) {  // if i =0 to get old creation date
				            		
				            		String vdate =rs3.getString("creation_date");
				            		
				            		// convert date to yyyy-mm-dd
				            		String vyear;
				            		String vmonth;
				            		String vday;
				            		int n = vdate.indexOf("-");
				            		vyear=vdate.substring(0, n).trim();
				            		vdate=vdate.substring(n+1, vdate.length()).trim();
				            		n = vdate.indexOf("-");
				       			 	vmonth=vdate.substring(0, n).trim();
				       			 	if (vmonth.length() <2) {vmonth= "0"+ vmonth;}
				       			 	vday=vdate.substring(n+1, n+3).trim();
				       			 	if (vday.length() <2) {vday= "0"+ vday;}
				       			 	vdate=vyear + "-"+ vmonth +"-"+ vday;
		                            /// end convert date
				       			    
				       			 	// update creation date with old creation date
				            		//System.out.println("update NODE_ACTIVE set creation_date = DATE '"+ vdate +"' where filename ='"+ rs2.getString("filename") +"'");
				            		PreparedStatement updtmt8 = parserCon.prepareStatement("update NODE_ACTIVE set creation_date = DATE '"+ vdate +"' where NODE_NAME ='"+ rs2.getString("NODE_NAME") +"' and CIRCLE_ID ='"+ rs2.getString("CIRCLE_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
					    			updtmt8.executeUpdate();
					    			updtmt8.close();
				            		
					    			//Function to delete the filename from all tables  argument field name and field value
					    			deleteRowsInAllTables("NODE_PK", rs3.getString("node_pk"),vdomain,vvendor );
				            	}  
				            	if ((i >0) && (i< (counter-1)) ) 
				            	 {  //if i< maxcount just to delete duplicate node_pk from all table 
		
				            		//Function to delete the filename from all tables  argument field name and field value
				            		deleteRowsInAllTables("NODE_PK", rs3.getString("node_pk"),vdomain,vvendor );
				            		}
				
			      	
				            	i=i+1;
				            }
							rs3.close(); // end of read rows and delete duplicata except the last one
							stmt3.close();

					}
					rs2.close();  // end of dupliacted node_pk
					stmt2.close();
					
		    //}
			//catch(Exception e)  
			//{  
			//	logger.info("error at GetduplicateFilename is :"+ e.toString());
			//	System.out.println("error at GetduplicateFilename is :"+ e.toString()); 
			//}
    
    }
}

 private static void deleteRowsInAllTables(String fieldname, String fieldValue,String vdomain, String vvendor) throws SQLException  {
	 try {
	 // delete all rows related to node_pk from all nodes tables
	 PreparedStatement stmt = parserCon.prepareStatement("delete from NODE_ACTIVE where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close();
     
     PreparedStatement stmt1 = parserCon.prepareStatement("delete from  NODE_ACTIVE_ATTRIBUTE where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
      
     PreparedStatement stmt2 = parserCon.prepareStatement("delete from  NODE_RACK where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = parserCon.prepareStatement("delete from  NODE_CABINET where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' "); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = parserCon.prepareStatement("delete from  NODE_HOSTVER where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
      
     stmt2 = parserCon.prepareStatement("delete from  NODE_FRAME where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
      
     stmt = parserCon.prepareStatement("delete from  NODE_SLOT where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
      		 
     stmt1 = parserCon.prepareStatement("delete from  NODE_BOARD where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
     stmt2 = parserCon.prepareStatement("delete from  NODE_PORT where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = parserCon.prepareStatement("delete from  NODE_ACCESSORY where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = parserCon.prepareStatement("delete from  NODE_HOST where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
     stmt2 = parserCon.prepareStatement("delete from  NODE_SUBRACK where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = parserCon.prepareStatement("delete from  NODE_GCELL where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = parserCon.prepareStatement("delete from  NODE_BTS where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
     stmt2 = parserCon.prepareStatement("delete from  NODE_UCELL where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = parserCon.prepareStatement("delete from  NODE_ANTENNA where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = parserCon.prepareStatement("delete from  NODE_LCELL where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
     stmt2 = parserCon.prepareStatement("delete from  NODE_RRN where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = parserCon.prepareStatement("delete from  NODE_ENODEBCELL where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = parserCon.prepareStatement("delete from  NODE_NODEBCELL where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
	 stmt = parserCon.prepareStatement("delete from  NODE_NBINTERFACES where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close();
	 }
	catch(Exception e)  
	{  
		logger.info("error at deleterowsinALLTABLES is :"+ e.toString());
		System.out.println("error at deleterowsinALLTABLES is :"+ e.toString()); 
	}
     
}

	 	 
}
