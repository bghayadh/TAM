package com.aliat.alm.telkom.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class LoadFilesEntHuawei  {

	static BufferedReader objReader1 = null;
	static String strCurrentLine1;
	static String projpath=null;
	static String logpath;
	static String db1path;
	static String username1;
	static String password1;
	static String db2path;
	static String username2;
	static String password2;
	static String readfileEntHuaweifrom;
	static String vfolderfrom;
	static String Gprovider;
	static String copyfileEntHuaweito;
	static String vfolderto;
	static String circleid="0";
	static String Gyear;
	static Logger logger;
	static FileHandler fh;
	static Connection conalm;
	static Connection con;
	static String Gcodeattributid="0";
	static String vline="0";
	static String vcodeid ="0";
	static String Domain;
	static Statement stmtp1;
	static PreparedStatement stmtp;
	static int NodeSeq;
	static int NodePortSeq;
	static int NodeBoardSeq;
	static HashMap<String, String> vhmap = new HashMap<String, String>();
	static String nodeId = null;
	static String nodeType = null;
	static String nodeModel = null;
	static String nodeName = null;
	static String hww;
	static String fileNamess;
	static String unique_Node_ID = null;
	static ArrayList<String> FileName = new ArrayList<String>();
	
	public static void main(String[] args, String vendor,String domain,String sub_domain) throws IOException, SQLException, InterruptedException {

		//objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almconfig.dat"));
		FileName = new ArrayList<String>();
		Resource ConfigResource = new ClassPathResource("almconfig.dat");
		File configfile = ConfigResource.getFile();
		FileReader fr=new FileReader(configfile);  
		BufferedReader objReader1=new BufferedReader(fr);
		
		System.out.println("vendor "+vendor);
		System.out.println("vendor "+domain);
		System.out.println("vendor "+sub_domain);
		
		Gprovider=vendor;
		sub_domain="";
		Domain=domain;

	 	while ((strCurrentLine1 = objReader1.readLine()) != null){
	 		String data = strCurrentLine1;
			 String[] data1;
			 String[] data2;

			 if (data.contains("projectpath")) {
				 data1=data.split(";",-1);
				 projpath=data1[1];
				 System.out.println("projectpath found: " + projpath);
			 }
			 if (data.contains("logpath")) {
				 data1=data.split(";",-1);
				 logpath=data1[1];
				 System.out.println("logpath found: " + logpath);
			 }
			 if (data.contains("db1path")) {
				 data1=data.split(";",-1);
				 db1path=data1[1];
				 username1=data1[2];
				 password1=data1[3];
				 System.out.println("db1path found: " + username1);
			 }
			 if (data.contains("db2path")) {
				 data1=data.split(";",-1);
				 db2path=data1[1];
				 username2=data1[2];
				 password2=data1[3];
				 System.out.println("db2path found: " + username2);
			 }
			 if (data.contains("readfileEntHuaweifrom")) {
				 data1=data.split(";",-1);
				 readfileEntHuaweifrom=data1[1];
				 System.out.println("readfileNokiafrom found: " + readfileEntHuaweifrom);
				 data2=readfileEntHuaweifrom.split("\\\\",-1);
				 vfolderfrom=data2[data2.length-1];
				 System.out.println("data2 found :" + data2[data2.length-1]);
			 }
			 if (data.contains("copyfileEntHuaweito")) {
				 data1=data.split(";",-1);
				 copyfileEntHuaweito=data1[1];
				 System.out.println("copyfileEntHuaweito found: " + copyfileEntHuaweito);
				 data2=copyfileEntHuaweito.split("\\\\",-1);
				 vfolderto=data2[data2.length-1];
			 }
	 	}
	 	 objReader1.close();
	 	 
		 //objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almcircle.dat"));
	 	 Resource CircleRsource = new ClassPathResource("almcircle.dat");
		 File circlefile = CircleRsource.getFile();
		 fr=new FileReader(circlefile);     
		 objReader1=new BufferedReader(fr);
		 
		 while ((strCurrentLine1 = objReader1.readLine()) != null){
			 String data = strCurrentLine1;
			 String[] data1 ;
			 data1=data.split(";",-1);
			 circleid=data1[1];
		 }
		 objReader1.close();	 
		 System.out.println(" circle is  :" + circleid);
		 	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime now = LocalDateTime.now();
				String logfilename="ParserLogEntHW-"+dtf.format(now)+".log";

				//get only year from today date
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    	LocalDateTime now1 = LocalDateTime.now();
		    	Gyear=dtf1.format(now1).substring(0,4);
		    	
		    	File folder = new File(readfileEntHuaweifrom);
				File[] listOfFiles = folder.listFiles();
				logger = Logger.getLogger("MyLog");
				logger.setUseParentHandlers(false);
				
				// This block configure the logger with handler and formatter  and PATH
		        fh = new FileHandler(logpath+"/"+logfilename);
		        logger.addHandler(fh);
		        SimpleFormatter formatter = new SimpleFormatter();
		        fh.setFormatter(formatter);
		        
		        //connect to alm DB
					DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
					conalm = DriverManager.getConnection (db1path,username1,password1);

					try {
						//connect to ALM_PARSER DB
				con= DriverManager.getConnection(db2path,username2,password2);
					System.out.println("Connected to oracle DB");
					} catch (SQLException e) {
					       System.out.println("Opss, error");
					       e.printStackTrace();
					   }
					
					//validate if the same process is running now if yes we cannot run it twice until finish
					Statement stmtinit2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    	 String sqlStmtinit2 = "select * from EXECUTE_DOAMIN_VENDOR_FILES where DOMAIN='"+Domain+"' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS'";
					    ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
					    rsinit2.last();
				 	    int totalrecinit2 = rsinit2.getRow();
				 	   rsinit2.beforeFirst();
				 	   if (totalrecinit2 == 0 ) {
				 		  PreparedStatement stmtinit = con.prepareStatement("insert into EXECUTE_DOAMIN_VENDOR_FILES (DOMAIN,VENDOR,CREATION_DATE,STATUS) values ('"+Domain+"', '"+ Gprovider +"',sysdate,'IN PROCESS')");
							 stmtinit.executeUpdate();
							 stmtinit.close();

							 Date before = new Date();
							 System.out.println("LoadFilesEntHW inprocess...");
							 System.out.println("It will take a few minutes..");
							 logger.info("LoadFilesEntHW inprocess...");
							 
								for (File file : listOfFiles) {
									if (file.isFile()){
										
								        String fichier =file.getName().toString();
								        fileNamess = file.getName().toString();
								        
								       // System.out.println("msan "+fichier);
								        if(fichier.contains("MSAN")) {
								        	System.out.println("msan "+fichier);
								        	FileName.add(0,fichier);
								        }
								        else {
								        	FileName.add(fichier);
								        }
								    }
								}

								for(int i=0;i<=FileName.size()-1;i++) {
									 readfile(FileName.get(i));
									 File source = new File(readfileEntHuaweifrom+"/"+FileName.get(i));
								     File dest = new File(copyfileEntHuaweito+"/"+FileName.get(i)+".bkp");
								     //coypy file after treating it to destination folder
								     copyFiles(source,dest);
                                    //delete file from source folder
								     deleteFiles(readfileEntHuaweifrom+"/"+FileName.get(i));	
								}
								
                                // remove dupliacte node 
								GetduplicateFilename(Domain,Gprovider);
								// update file status to completed
								 stmtinit = con.prepareStatement("update EXECUTE_DOAMIN_VENDOR_FILES set STATUS ='COMPLETED' where DOMAIN='"+Domain+"' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS'");
								 stmtinit.executeUpdate();
								 stmtinit.close();
								 
								 System.out.println("LoadFilesEmtHW completed");
								 logger.info("LoadFilesEntHW completed");
								   	Date after = new Date();
								   	System.out.println("it takes " + (after.getTime() - before.getTime())+ "ms");
								   	logger.info("it takes " + (after.getTime() - before.getTime())+ "ms");
				 	   }

				 	   else {
					 		  System.out.println("A process already is running , please wait until process ending");
					 		   logger.info("A process already is running , please wait until process ending");
					 	   }
					 	  rsinit2.close();
					 	  stmtinit2.close();
					 	  con.close();
						  conalm.close();		
	}
	@SuppressWarnings("unchecked")
	public static String readfile (String filename) {
	   	System.out.println("filename "+filename);
	   	
	  if(StringUtils.containsIgnoreCase(filename, "msan")) {
	   	String codeid="";
	   	String Node_Type="";
	   	String Site_ID="";
	   	String Ware_ID="";
	   	String Ware_Name="";
	   	String Long="";
	   	String Lat="";
	   	String Node_Name;
	  	Node_Type="MSAN";
		String excelFilePath=readfileEntHuaweifrom+"/"+filename;
		String [] temp;
		temp=filename.split("\\.",-1);
		String fileType=temp[1];
		long start = System.currentTimeMillis();
		FileInputStream inputStream;
		
		try {
			inputStream = new FileInputStream(excelFilePath);
			Workbook workbook=new XSSFWorkbook(inputStream);
			//Sheet firstSheet=workbook.getSheet("NE_DB");
			Sheet firstSheet=workbook.getSheetAt(0);
			// get the seq of node_active and update it by number of row 
			int rownumb=firstSheet.getLastRowNum();
			System.out.println("node rownum "+rownumb);
			//System.out.println("number of row "+rownumb);
			
			String sqlStmtinit3 = "select NODE_ACTIVE from SEQ_TABLE";     
			  stmtp1 = conalm.createStatement();
			  ResultSet rsinit3 = stmtp1.executeQuery(sqlStmtinit3);
			  while(rsinit3.next()) {
				  NodeSeq = rsinit3.getInt("NODE_ACTIVE");
			  	//vcodeid=year+"_NODE_"+rsinit2.getInt("NODE_ACTIVE");
			  	stmtp = conalm.prepareStatement("UPDATE SEQ_TABLE SET NODE_ACTIVE = NODE_ACTIVE +"+(rownumb));//-1 to ignore non-data row of the excel sheet
			  	stmtp.executeUpdate();
			  	stmtp.close();
			  }
			  rsinit3.close();
			  stmtp1.close();
			
			Iterator<Row> rowIterator=firstSheet.iterator();
			long end;
			Row nextRow = rowIterator.next();
			
			while(rowIterator.hasNext() ){
			vhmap=getexceldata(firstSheet,nextRow,rowIterator);
			
				if(!vhmap.isEmpty()) {
					codeid=Gyear+"_"+ "NODE"+'_'+NodeSeq;
					if(vhmap.get("NodeName").contains("'")) {
						Node_Name=vhmap.get("NodeName").replace("'", " ");
					}
					else {
						Node_Name=vhmap.get("NodeName");
					}
					
				      Site_ID = vhmap.get("NodeName").split("_")[0];
				     
						if(Site_ID != null) {
							 if(Site_ID.contains("'")) {
								 Site_ID = Site_ID.replace("'", "");
							 }
								Statement stmtinit2 = conalm.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						    	 String sqlStmtinit2 = "select DISTINCT WARE_ID,LONGITUDE,LATITUDE,WARE_NAME from WAREHOUSE where SITE_ID='"+Site_ID+"'";
								   ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
								    rsinit2.last();
								    int totalrecinit = rsinit2.getRow();
								    rsinit2.beforeFirst();
								  //while(rsinit2.next()) {
								    if(totalrecinit ==1) {
								 		  // retrieve site_id, ware_id, long and lat from warehouse table
								    	rsinit2.next();
									  //System.out.println("here while "+rsinit2.getString("WARE_ID"));
									  Ware_ID=rsinit2.getString("WARE_ID");
									  Ware_Name = rsinit2.getString("WARE_NAME");
									  Long=rsinit2.getString("LONGITUDE");
									  Lat = rsinit2.getString("LATITUDE");
									  
								  }else {
										Ware_ID="0";
										Ware_Name ="0";
										Long= "0";
										Lat = "0";
										Site_ID = "0";
									}
								    stmtinit2.close();
								    rsinit2.close();
						}
					unique_Node_ID=vhmap.get("NodeIPAddr")+"_"+"HW";
					
					PreparedStatement stmt = con.prepareStatement("insert into NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME,IP_ADDRESS,MAC_ADDRESS,SOFTWARE_VERSION,PATCH_VERSION,LONGITUDE,LATITUDE,PART_NUMBER,OTHERS)"
					 		+ "values('" +codeid +"', '"+unique_Node_ID+"' ,'" + vhmap.get("NodeIPAddr") +"' ,'"+Node_Name+"','"+Node_Type+"','"+Domain+"','0','"+vhmap.get("NodeType")+"','0','0','0','0','"+Site_ID+"','"+ circleid +"',sysdate,sysdate,'"+fileType+"','" + filename +"','"+vhmap.get("CommunicationStatus")+"','0','0','0','0','0','1','0','"+Ware_ID+"','"+ Gprovider +"','0','"+Ware_Name+"','0','"+vhmap.get("NodeIPAddr")+"','"+vhmap.get("NodeMacAddr")+"','"+vhmap.get("SoftwareVersion")+"','"+vhmap.get("PatchVerList")+"','"+Long+"','"+Lat+"','"+vhmap.get("NodeSubType")+"','"+vhmap.get("Others")+"') "); 
	                stmt.executeUpdate();
				     stmt.close();
					
				     NodeSeq++;
				}
					}
		
			end = System.currentTimeMillis();
			System.out.printf("Import done in %d ms\n", (end - start));
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}else if(StringUtils.containsIgnoreCase(filename, "board")){
		System.out.println(filename);
		String codeid="";
		String excelFilePath=readfileEntHuaweifrom+"/"+filename;
		System.out.println(excelFilePath);
		String sheetname="";
		String [] temp;
		temp=filename.split("\\.",-1);
		sheetname=temp[0];
		String regex = "\\d+$";
		//String resultString = sheetname.replaceAll(regex, "");
	    //System.out.println(resultString);
		long start = System.currentTimeMillis();
		FileInputStream inputStream;
		
		try {
			inputStream = new FileInputStream(excelFilePath);
			Workbook workbook=new XSSFWorkbook(inputStream);
			//Sheet firstSheet=workbook.getSheet(resultString);
			Sheet firstSheet=workbook.getSheetAt(0);
			// get the seq of node_active and update it by number of row 
			int rownumb=firstSheet.getLastRowNum();
			//System.out.println("number of row "+rownumb);
			
			String sqlStmtinit3 = "select NODE_BOARD from SEQ_TABLE";     
			  stmtp1 = conalm.createStatement();
			  ResultSet rsinit3 = stmtp1.executeQuery(sqlStmtinit3);
			  while(rsinit3.next()) {
				  NodeBoardSeq = rsinit3.getInt("NODE_BOARD");
			  	stmtp = conalm.prepareStatement("UPDATE SEQ_TABLE SET NODE_BOARD = NODE_BOARD +"+(rownumb));//-2 to ignore non-data row of the excel sheet
			  	stmtp.executeUpdate();
			  	stmtp.close();
			  }
			  rsinit3.close();
			  stmtp1.close();
			
			Iterator<Row> rowIterator=firstSheet.iterator();
			long end;
			Row nextRow = rowIterator.next();
			
			while(rowIterator.hasNext() ){
			vhmap=getexcelBoarddata(firstSheet,nextRow,rowIterator);
			
				if(!vhmap.isEmpty()) {
					codeid=Gyear+"_"+ "NODE_HW_ENT_BRD"+'_'+NodeBoardSeq;
					stmtp =  con.prepareStatement("insert into NODE_BOARD (BOARD_ID,SUBRACKNO,SLOTNO,BOARDNAME,BOARDTYPE,SERIALNUMBER,HARDWAREVERSION,SOFTVER,BIOSVER,ISSUENUMBER,BOMCODE,MODEL,NODE_PK,UPDATE_DATE,FILENAME,STATUS,CREATION_DATE,DOMAIN,VENDOR,OTHERS,ACTIVE_RECORD)"
						     + "values('"+codeid+"','"+vhmap.get("SubrackID")+"','"+vhmap.get("SlotID")+"','"+vhmap.get("BoardFullName")+"','"+vhmap.get("BoardType")+"','"+vhmap.get("SN")+"','"+vhmap.get("HardwareVersion")+"','"+vhmap.get("SoftwareVersion")+"','"+vhmap.get("BIOSVersion")+"','"+vhmap.get("IssueNumber")+"','"+vhmap.get("BOMCode")+"','"+vhmap.get("BoardType")+"',(Select DISTINCT NODE_PK from node_active where IP_ADDRESS='"+vhmap.get("NEIPAddress")+"' and active_record='1' and domain='"+Domain+"' and vendor='"+Gprovider+"' order by creation_date desc fetch first 1 row only) ,sysdate,'"+filename+"','"+vhmap.get("BoardStatus")+"',sysdate,'"+Domain+"','"+Gprovider+"','"+vhmap.get("others")+"','1')"); 
					  	stmtp.executeUpdate();
					  	stmtp.close();
					NodeBoardSeq++;
				}
					}
		
			end = System.currentTimeMillis();
			System.out.printf("Import done in %d ms\n", (end - start));
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	  else {
	  	String codeid="";
		String excelFilePath=readfileEntHuaweifrom+"/"+filename;
		String sheetname="";
		String [] temp;
		temp=filename.split("\\.",-1);
		sheetname=temp[0];
		String regex = "\\d+$";
		//String resultString = sheetname.replaceAll(regex, "");
		long start = System.currentTimeMillis();
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(excelFilePath);
			Workbook workbook=new XSSFWorkbook(inputStream);
			//Sheet firstSheet=workbook.getSheet(resultString);
			Sheet firstSheet=workbook.getSheetAt(0);
			// get the seq of node_active and update it by number of row 
			int rownumb=firstSheet.getLastRowNum();
			//System.out.println("number of row "+rownumb);
			
			String sqlStmtinit3 = "select NODE_PORT from SEQ_TABLE";     
			  stmtp1 = conalm.createStatement();
			  ResultSet rsinit3 = stmtp1.executeQuery(sqlStmtinit3);
			  while(rsinit3.next()) {
				  NodePortSeq = rsinit3.getInt("NODE_PORT");
			  	stmtp = conalm.prepareStatement("UPDATE SEQ_TABLE SET NODE_PORT = NODE_PORT +"+(rownumb-3));//-3 to ignore non-data row of the excel sheet
			  	stmtp.executeUpdate();
			  	stmtp.close();
			  }
			  rsinit3.close();
			  stmtp1.close();
			
			Iterator<Row> rowIterator=firstSheet.iterator();
			long end;
			Row nextRow = rowIterator.next();
			
			while(rowIterator.hasNext() ){
			vhmap=getexcelPortdata(firstSheet,nextRow,rowIterator);
			
				if(!vhmap.isEmpty()) {
					codeid=Gyear+"_"+ "NODE_HW_ENT_PORT"+'_'+NodePortSeq;
					String node_fk = "";
					String node_attr_fk ="";
					
					PreparedStatement stmt = con.prepareStatement("insert into NODE_PORT(PORT_ID,SITEINDEX,SLOTNO,SUBSLOTNO,PORTNO,VENDORNAME,UNITPOSITION,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR,PORTTYPE,PORTRATE,OTHERS) "
            		   		+ "values('" + codeid +"','0','" + vhmap.get("SlotNO") +"','" + vhmap.get("SubSlotNO") +"','" + vhmap.get("PortNO") +"','"+Gprovider+"','" + vhmap.get("UnitPos") +"',(select NODE_PK from NODE_ACTIVE where NODE_NAME='"+vhmap.get("NE_Name")+"' AND ACTIVE_RECORD='1' and domain='"+Domain+"' and vendor='"+Gprovider+"' order by creation_date desc fetch first 1 row only),'" + node_attr_fk +"' ,sysdate,'" + filename +"','" + vhmap.get("OperationStatus") +"','0','0','0','0','0','0','1','"+Domain+"','" + Gprovider +"','" + vhmap.get("PortType") +"','" + vhmap.get("PortRate") +"','" + vhmap.get("Others") +"') ");
         		    stmt.setEscapeProcessing(false); 
					stmt.executeUpdate();
				     stmt.close();
				     NodePortSeq++;
				}
					}
		
			end = System.currentTimeMillis();
			System.out.printf("Import done in %d ms\n", (end - start));
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		return null;
	}
	
	@SuppressWarnings("unused")
	private static HashMap<String, String> getexcelBoarddata(Sheet firstSheet, Row nextRow, Iterator<Row> rowIterator) {
		HashMap<String, String> hmap = new HashMap<String, String>();
		
		String NEName="",BoardFullName="",BoardName="",BoardType="",NEID="",NEIPAddress="",NEType="",SubrackType="",SubrackID="",SlotID="",HardwareVersion="",SoftwareVersion="",SN="",BoardAlias="",Remarks="",Model="",IssueNumber="",FPGAVersion="",BIOSVersion="",BoardStatus="",ProtectionRole="",PSTQ="",BOMCode="",AdministrativeStatus="",Description="",ManufacturedOn="",CreatedOn="";
		
		nextRow = rowIterator.next();
			Iterator<Cell> cellIterator=nextRow.cellIterator();
			int rowIndex = nextRow.getRowNum();
		
			if(rowIndex >0) {
			while(cellIterator.hasNext()) {
				
				Cell nextCell=cellIterator.next();
				int columnIndex=nextCell.getColumnIndex();
				switch (columnIndex) {
				case 0:
					NEName=nextCell.getStringCellValue();
					break;
				case 1:
					BoardFullName=nextCell.getStringCellValue();
					break;
				case 2:
					BoardName=nextCell.getStringCellValue();
					break;
				case 3:
					BoardType=nextCell.getStringCellValue();
					break;
				case 4:
					NEID=nextCell.getStringCellValue();
					break;
				case 5:
					NEIPAddress=nextCell.getStringCellValue();
					break;
				case 6:
					NEType=nextCell.getStringCellValue();
					break;
				case 7:
					SubrackType=nextCell.getStringCellValue();
					break;
				case 8:
					SubrackID=nextCell.getStringCellValue();
					break;
				case 9:
					SlotID=nextCell.getStringCellValue();
					break;
				case 10:
					HardwareVersion=nextCell.getStringCellValue();
					break;
				case 11:
					SoftwareVersion=nextCell.getStringCellValue();
					break;
				case 12:
					SN=nextCell.getStringCellValue();
					break;
				case 13:
					BoardAlias=nextCell.getStringCellValue();
					break;
				case 14:
					Remarks=nextCell.getStringCellValue();
					break;
				case 15:
					Model=nextCell.getStringCellValue();
					break;
				case 16:
					IssueNumber=nextCell.getStringCellValue();
					break;
				case 17:
					FPGAVersion=nextCell.getStringCellValue();
					break;
				case 18:
					BIOSVersion=nextCell.getStringCellValue();
					break;
				case 19:
					BoardStatus=nextCell.getStringCellValue();
					break;
				case 20:
					ProtectionRole=nextCell.getStringCellValue();
					break;
				case 21:
					PSTQ=nextCell.getStringCellValue();
					break;
				case 22:
					BOMCode=nextCell.getStringCellValue();
					break;
				case 23:
					AdministrativeStatus=nextCell.getStringCellValue();
					break;
				case 24:
					Description=nextCell.getStringCellValue();
					break;
				case 25:
					ManufacturedOn=nextCell.getStringCellValue();
					break;
				case 26:
					CreatedOn=nextCell.getStringCellValue();
					break;
					
					}
				}
			
		    String others = "{\"NE ID\":"+"\""+NEID+"\","+"\"NE IP Address\":"+"\""+NEIPAddress+"\","+"\"NE Type (MPU TYPE)\":"+"\""+NEType+"\","+"\"Subrack Type\":"+"\""+SubrackType+"\","+"\"FPGA Version\":"+"\""+FPGAVersion+"\","+"\"Protection Role\":"+"\""+ProtectionRole+"\","+"\"PSTQ\":"+"\""+PSTQ+"\","+"\"Administrative Status\":"+"\""+AdministrativeStatus+"\","+"\"Description\":"+"\""+Description+"\""+"}";
		    hmap.put( "BoardFullName", BoardFullName);
		    hmap.put( "BoardType", BoardType);
			 hmap.put( "SubrackID", SubrackID);
			 hmap.put( "SlotID", SlotID);
			 hmap.put( "HardwareVersion", HardwareVersion);
			 hmap.put( "SoftwareVersion", SoftwareVersion);
			 hmap.put( "SN", SN);
			 hmap.put( "Model", Model);
			 hmap.put( "IssueNumber", IssueNumber);
			 hmap.put( "BIOSVersion", BIOSVersion);
			 hmap.put( "BoardStatus", BoardStatus);
			 hmap.put( "BOMCode", BOMCode);
			 hmap.put("others", others);
			 hmap.put("NEIPAddress", NEIPAddress);

			}
		
		return hmap;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static HashMap getexceldata(Sheet firstSheet,Row nextRow,Iterator<Row> rowIterator) throws SQLException {
		 HashMap<String, String> hmap = new HashMap<String, String>();

		    String NEName = "";
			String NEModel = "";
			String NEIP = "";
			String SoftwareVer = "";
			String NEMAC = "";
			String NEID = "";
			String SubNet = "";
			String NESubType = "";
			String ComStatus = "";
			String AdminStatus = "";
			String crtdata = "";//maybe change to time 
			String Remark = "";
			String PatchVerList = "";
			String LifeCycleStatus = "";
			String Gateway = "";
			String GatewayType = "";
			String GatewayIP = "";
			String Others = "";
		
			 nextRow = rowIterator.next();
				Iterator<Cell> cellIterator=nextRow.cellIterator();
				int rowIndex = nextRow.getRowNum();
			
				if(rowIndex >0) {
				while(cellIterator.hasNext()) {
					Cell nextCell=cellIterator.next();
					int columnIndex=nextCell.getColumnIndex();
					switch (columnIndex) {
				case 0:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					NEName=nextCell.getStringCellValue();
					}
					break;
				case 1:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
			        	NEModel=nextCell.getStringCellValue();
						}
					break;
				case 2:
			        if (nextCell.getCellTypeEnum() == CellType.STRING) {
		        	NEIP = nextCell.getStringCellValue();
		        
		       }
				break;
				case 3:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {

					SoftwareVer=nextCell.getStringCellValue();
					}
					break;
				case 4:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					NEMAC=nextCell.getStringCellValue();
					if(NEMAC.trim().equalsIgnoreCase("--")) {
						NEMAC="";
					}
					}
					break;
				case 5:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					NEID=nextCell.getStringCellValue();
					}
					break;
				case 7:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					SubNet=nextCell.getStringCellValue();
					}
					break;
				case 10:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					NESubType=nextCell.getStringCellValue();
					}
					break;
				case 11:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					ComStatus=nextCell.getStringCellValue();
					}
					break;
				case 12:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					AdminStatus=nextCell.getStringCellValue();
					}
					break;
				case 14:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					crtdata=nextCell.getStringCellValue();
					}
					break;
				case 16:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					Remark=nextCell.getStringCellValue();
					}
					break;
				case 17:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					PatchVerList=nextCell.getStringCellValue();
					}
					break;
				case 19:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					GatewayType=nextCell.getStringCellValue();
					if(GatewayType.trim().equalsIgnoreCase("--")) {
						GatewayType="";
					}
					}
					break;
				case 20:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					Gateway=nextCell.getStringCellValue();
					if(Gateway.trim().equalsIgnoreCase("--")) {
						Gateway="";
					}
					}
					break;
				case 21:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					GatewayIP=nextCell.getStringCellValue();
					if(GatewayIP.trim().equalsIgnoreCase("--")) {
						GatewayIP="";
					}
					}
					break;
				case 23:
				        if (nextCell.getCellTypeEnum() == CellType.STRING) {
					LifeCycleStatus=nextCell.getStringCellValue();
					if(LifeCycleStatus.trim().equalsIgnoreCase("--")){
						LifeCycleStatus="";
					}
					}
					break;
				}
				}
				
				 Others="{\"AdministrativeStatus\":"+"\""+AdminStatus+"\","+"\"LifeCycleStatus\":"+"\""+LifeCycleStatus+"\","+"\"Gateway\":"+"\""+Gateway+"\","+"\"GatewayType\":"+"\""+GatewayType+"\","+"\"GatewayIP\":"+"\""+GatewayIP+"\""+"}";
				 hmap.put( "NodeName", NEName);
				 hmap.put( "NodeType", NEModel);
				 hmap.put( "NodeIPAddr", NEIP);
				 hmap.put( "SoftwareVersion", SoftwareVer);
				 hmap.put( "NodeMacAddr", NEMAC);
				 hmap.put( "VENDORNAME", Gprovider);
				 hmap.put( "NodeID", NEID);
				 hmap.put( "SubNet", SubNet);
				 hmap.put( "NodeSubType", NESubType);
				 hmap.put( "CommunicationStatus", ComStatus);
				 hmap.put( "CreatedON", crtdata);
				 hmap.put( "Remark", Remark);
				 hmap.put( "PatchVerList", PatchVerList);
				 hmap.put("Others", Others);
				}
				return hmap;
	}
	
	@SuppressWarnings("rawtypes")
	public static HashMap getexcelPortdata(Sheet firstSheet,Row nextRow,Iterator<Row> rowIterator) throws SQLException {
		 HashMap<String, String> hmap = new HashMap<String, String>();

		    String SlotNO = "";
			String SubSlotNO = "";
			String PortNO = "";
			String UnitPos = "";
			String PortType = "";
			String PortRate = "";
			String AdminStatus = "";
			String OperationStatus = "";
			String Others = "";
			String NE_Name="";
			
			 nextRow = rowIterator.next();
				Iterator<Cell> cellIterator=nextRow.cellIterator();
				int rowIndex = nextRow.getRowNum();
			
				if(rowIndex >3) {
				while(cellIterator.hasNext()) {
					Cell nextCell=cellIterator.next();
					int columnIndex=nextCell.getColumnIndex();
					switch (columnIndex) {
					case 0:
						NE_Name=nextCell.getStringCellValue();
						if(NE_Name.contains("'")) {
							NE_Name=NE_Name.replace("'"," ");
						}
					case 3:
						SlotNO=nextCell.getStringCellValue();
						break;
					case 4:
						SubSlotNO= nextCell.getStringCellValue();
						break;
				   case 5:
						PortNO= nextCell.getStringCellValue();
						break;
				   case 7:
					   UnitPos= nextCell.getStringCellValue();
					   if(UnitPos.contains("'")) {
						   UnitPos = UnitPos.replace("'"," ");
					   }
						break;
				   case 9:
					   PortType= nextCell.getStringCellValue();
					   if(PortType.contains("'")) {
						   PortType = PortType.replace("'"," ");
					   }
						break;
				   case 10:
					   PortRate= nextCell.getStringCellValue();
					   if(PortRate.contains("'")) {
						   PortRate = PortRate.replace("'"," ");
					   }
						break;
				   case 12:
					   AdminStatus= nextCell.getStringCellValue();
						break;
				   case 13:
					   OperationStatus= nextCell.getStringCellValue();
					   if(OperationStatus.contains("'")) {
						   OperationStatus = OperationStatus.replace("'"," ");
					   }
						break;
					}
				}
				 Others="{\"AdministrativeStatus\":"+"\""+AdminStatus+"\""+"}";
				 hmap.put("NE_Name",NE_Name);
				 hmap.put( "SlotNO", SlotNO);
				 hmap.put( "SubSlotNO", SubSlotNO);
				 hmap.put( "PortNO", PortNO);
				 hmap.put( "UnitPos", UnitPos);
				 hmap.put( "OperationStatus", OperationStatus);
				 hmap.put( "PortType", PortType);
				 hmap.put( "PortRate", PortRate);
				 hmap.put( "Others", Others);
				}
				return hmap;
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
	 
	 
	 
	 private static void GetduplicateFilename(String vdomain , String vvendor) throws SQLException  {
			Statement stmt1 = null;
			Statement stmt2 = null;
			Statement stmt3 = null;
			int vcount =0;
			int i=0;
			
			// select all file having duplicata entry and same filename >1
			//String query1 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,count() counter from NODE_ACTIVE  group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR having count() >1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";  
			String query1 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,count(*) counter from NODE_ACTIVE  group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR having count(*) >1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";  
			stmt1 = con.createStatement();
		    ResultSet rs1 = stmt1.executeQuery(query1);
		    while (rs1.next()) {
		    	 String nodenamee=null;
		    	 if(rs1.getString("NODE_NAME") != null) {
	        	 if(rs1.getString("NODE_NAME").contains("'")) {
						
	        		 nodenamee=rs1.getString("NODE_NAME").replace("'", "''");
					}
					else {
						nodenamee=rs1.getString("NODE_NAME");
					}
		    	 }
		    	//try {
		                 // select all rows related to a file identified by rs1 having count >=1
						 //String query2 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,count() counter from (select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR from NODE_ACTIVE where (SITE_ID ='"+ rs1.getString("SITE_ID") +"' OR SITE_ID IS NULL) and CIRCLE_ID ='"+ rs1.getString("CIRCLE_ID") +"' and NODE_ID ='"+ rs1.getString("NODE_ID") +"' and NODE_NAME ='"+ rs1.getString("NODE_NAME") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor+"' ) group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR having count() >=1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";  
				 		 String query2 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,count(*) counter from (select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR from NODE_ACTIVE where (SITE_ID ='"+ rs1.getString("SITE_ID") +"' OR SITE_ID IS NULL) and CIRCLE_ID ='"+ rs1.getString("CIRCLE_ID") +"' and NODE_ID ='"+ rs1.getString("NODE_ID") +"' and NODE_NAME ='"+ nodenamee +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' ) group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR having count(*) >=1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";   
		    			 stmt2 = con.createStatement();
				         ResultSet rs2 = stmt2.executeQuery(query2);
				         // get all node_pk found duplicated
				         while (rs2.next()) {
				        	 vcount =0;
				        	 vcount= (int) Long.parseLong (rs2.getString("counter"));
				        	 i=0;
				        	 String nodename;
				        	 if(rs2.getString("NODE_NAME").contains("'")) {
									
				        		 nodename=rs2.getString("NODE_NAME").replace("'", "");
								}
								else {
									nodename=rs2.getString("NODE_NAME");
								}
					        	    //Get old creation date of the same file and update rows with old creation date
					        	 	String query3 = "select node_pk,creation_date from NODE_ACTIVE where NODE_ID='"+ rs2.getString("NODE_ID") +"' and (SITE_ID='"+ rs2.getString("SITE_ID") +"' OR SITE_ID IS NULL) and NODE_NAME ='"+ nodename +"' and CIRCLE_ID ='"+ rs2.getString("CIRCLE_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' order by creation_date asc";  
						            stmt3 = con.createStatement();
						            //stmt3.setMaxRows(1); 
						            ResultSet rs3 = stmt3.executeQuery(query3);
						            while (rs3.next()) {       
						            	//System.out.println(rs3.getString("node_pk") +":"+ rs3.getString("creation_date"));
						            	//System.out.println("before formatting : "+rs3.getString("creation_date"));
						            	//System.out.println(rs3.getDate("creation_date"));
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
						       			    
						       			// System.out.println("after formatting : "+vdate);
						       			 	// update creation date with old creation date
						            		//System.out.println("update NODE_ACTIVE set creation_date = DATE '"+ vdate +"' where filename ='"+ rs2.getString("filename") +"'");
						            		PreparedStatement updtmt8 = con.prepareStatement("update NODE_ACTIVE set creation_date = TIMESTAMP '" + rs3.getString("creation_date") + "',update_date=sysdate where NODE_NAME ='"+ nodename +"' and CIRCLE_ID ='"+ rs2.getString("CIRCLE_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
							    			updtmt8.executeUpdate();
							    			updtmt8.close();
						            		
							    			//Function to delete the filename from all tables  argument field name and field value
							    			deleterowsinALLTABLES("NODE_PK", rs3.getString("node_pk"),vdomain,vvendor);
						            	}  
						            	if ((i >0) && (i< (vcount-1)) ) 
						            	 {  //if i< maxcount just to delete duplicate node_pk from all table 
				
						            		//Function to delete the filename from all tables  argument field name and field value
						            		deleterowsinALLTABLES("NODE_PK", rs3.getString("node_pk"),vdomain,vvendor);
						            		}
						
					      	
						            	i=i+1;
						            }
									rs3.close(); // end of read rows and delete duplicata except the last one
									stmt3.close();

							}
							rs2.close();  // end of dupliacted node_pk
							stmt2.close();
							
				    
		  
		    }
		}
	   
		
		 
		 private static void deleterowsinALLTABLES(String fieldname, String fieldValue,String vdomain, String vvendor) throws SQLException  {
			 try {
				// System.out.println("delete "+fieldname+" "+fieldValue+" "+vdomain+" "+vvendor);
			 // delete all rows related to node_pk from all nodes tables
			 PreparedStatement stmt = con.prepareStatement("delete from NODE_ACTIVE where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
		     stmt.executeUpdate();
		     stmt.close();
		    
		      stmt = con.prepareStatement("delete from NODE_BOARD where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
		     stmt.executeUpdate();
		     stmt.close();
		     
		      stmt = con.prepareStatement("delete from NODE_PORT where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
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