package com.aliat.alm.telkom.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;



import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aliat.alm.models.ManHole;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	static HashMap<String, String> vhmap = new HashMap<String, String>();
	



	 
	static String nodeId = null;
	static String nodeType = null;
	static String nodeModel = null;
	static String nodeName = null;
	static String hww;
	static String fileNamess;
	static String unique_Node_ID = null;	
		
	
	
	public static void main(String[] args, String vendor,String domain,String sub_domain) throws IOException, SQLException, InterruptedException {
		
	
		objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almconfig.dat"));
		System.out.println("vendor "+vendor);
		System.out.println("vendor "+domain);
		System.out.println("vendor "+sub_domain);
		
		if(vendor.equalsIgnoreCase("Huawei")) {
			Gprovider="HW";
		}
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
				 //Gprovider=vfolderfrom.substring(4,6);
				 //System.out.println("Gprovider2 found: " + Gprovider);
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
	 	 
		// System.out.println("get circle value  :" + System.getProperty("user.dir")+"/"+"almcircle.dat");
		// objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+"almcircle.dat"));
		 objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almcircle.dat"));

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
		    	//System.out.println(Gyear);

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
			    	 String sqlStmtinit2 = "select * from EXECUTE_DOAMIN_VENDOR_FILES where DOMAIN='Enterprise' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS'";
					    ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
					    rsinit2.last();
				 	    int totalrecinit2 = rsinit2.getRow();
				 	   rsinit2.beforeFirst();
				 	   if (totalrecinit2 == 0 ) {
				 		  PreparedStatement stmtinit = con.prepareStatement("insert into EXECUTE_DOAMIN_VENDOR_FILES (DOMAIN,VENDOR,CREATION_DATE,STATUS) values ('Enterprise', '"+ Gprovider +"',sysdate,'IN PROCESS')");
							 stmtinit.executeUpdate();
							 stmtinit.close();

							 Date before = new Date();
							 System.out.println("LoadFilesEntHW inprocess...");
							 System.out.println("It will take a few minutes..");
							 logger.info("LoadFilesEntHW inprocess...");
								for (File file : listOfFiles) {
									if (file.isFile()) {
										
										
										 

								        String fichier =file.getName().toString();
								        fileNamess = file.getName().toString();
										// reading file from folder
								        readfile(fichier);

										 File source = new File(readfileEntHuaweifrom+"/"+file.getName());
									     File dest = new File(copyfileEntHuaweito+"/"+file.getName()+".bkp");

									     //coypy file after treating it to destination folder
									     copyFiles(source,dest);
                                         //delete file from source folder
									     deleteFiles(readfileEntHuaweifrom+"/"+file.getName());


								    }
								}
                                // remove dupliacte node 
								GetduplicateFilename("Enterprise",Gprovider);
								// update file status to completed
								 stmtinit = con.prepareStatement("update EXECUTE_DOAMIN_VENDOR_FILES set STATUS ='COMPLETED' where DOMAIN='Enterprise' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS'");
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
	public static String readfile (String filename) {

	   	System.out.println("filename "+filename);
		
	   //String unique_Node_ID = null;
	   	
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
		String sheetname=temp[0];
		String fileType=temp[1];
		//System.out.println("sheetname "+sheetname);
		//System.out.println("excelFilePath "+excelFilePath);
		
		long start = System.currentTimeMillis();


		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(excelFilePath);
			Workbook workbook=new XSSFWorkbook(inputStream);
			Sheet firstSheet=workbook.getSheet(sheetname);
			// get the seq of node_active and update it by number of row 
			int rownumb=firstSheet.getLastRowNum();
			//System.out.println("number of row "+rownumb);
			
			String sqlStmtinit3 = "select NODE_ACTIVE from SEQ_TABLE";     
			  stmtp1 = conalm.createStatement();
			  ResultSet rsinit3 = stmtp1.executeQuery(sqlStmtinit3);
			  while(rsinit3.next()) {
				  NodeSeq = rsinit3.getInt("NODE_ACTIVE");
			  	//vcodeid=year+"_NODE_"+rsinit2.getInt("NODE_ACTIVE");
			  	stmtp = conalm.prepareStatement("UPDATE SEQ_TABLE SET NODE_ACTIVE = NODE_ACTIVE +"+(rownumb-3));//-3 to ignore non-data row of the excel sheet
			  	stmtp.executeUpdate();
			  	stmtp.close();
			  }
			  rsinit3.close();
			  stmtp1.close();
			  
			
			Iterator<Row> rowIterator=firstSheet.iterator();
			long end;
			
			Row nextRow = rowIterator.next();
			while(rowIterator.hasNext() ){//&& nextRow.getRowNum() >2) {
				//System.out.println("nextRow.getRowNum()  "+nextRow.getRowNum() );
			vhmap=getexceldata(firstSheet,nextRow,rowIterator);
					 
				//System.out.println("vhmap "+vhmap);	
				if(!vhmap.isEmpty()) {
					
					codeid=Gyear+"_"+ "NODE"+'_'+NodeSeq;
					if(vhmap.get("NodeName").contains("'")) {
						
						Node_Name=vhmap.get("NodeName").replace("'", "''");
					}
					else {
						Node_Name=vhmap.get("NodeName");
					}
					
					String [] Temp1=vhmap.get("NodeName").split("_",-1);
					 // Site_ID=Temp1[0];
					  
					
					//Statement stmtinit2 = conalm.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    	 //String sqlStmtinit2 = "select DISTINCT WARE_ID,LONGITUDE,LATITUDE,WARE_NAME from WAREHOUSE where SITE_ID='"+Site_ID+"'";
					   // ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
					    //rsinit2.last();
					    //int totalrecinit = rsinit2.getRow();
					 	  //rsinit2.beforeFirst();
					 	  
					 	  ///
					 	 if(vhmap.get("NodeName").contains("_")) {
					 		 //System.out.println("here _");
								if(vhmap.get("NodeName").split("_").length > 3) {
									//System.out.println("here >3");
									Site_ID = vhmap.get("NodeName").split("_",-1)[0];
									char charArray[] = Site_ID.toCharArray();
									if(Character.isDigit(charArray[0])) {
										//String sqlStmtinit2 = "select WARE_ID,WARE_NAME,LONGITUDE,LATITUDE from WAREHOUSE WHERE SITE_ID='"+siteID+"'";     
										  //stmtp1 = conalm.createStatement();
										  //ResultSet rsinit2 = stmtp1.executeQuery(sqlStmtinit3);
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
											  rsinit2.close();
										  	
										  }
										    
										  //rsinit2.close();
										  //stmtp1.close();
										  else {
												Ware_ID="";
												Ware_Name ="";
												  Long="";
												  Lat = "";
												  Site_ID = "";
												//System.out.println("site id and site name don't exists");

											}
										
								}
								else {
									Ware_ID="";
									Ware_Name ="";
									  Long="";
									  Lat = "";
									  Site_ID = "";
									//System.out.println("site id and site name don't exists");

								}
								}else {
									Ware_ID="";
									Ware_Name ="";
									  Long="";
									  Lat = "";
									  Site_ID = "";
									
								}
							}else {
								Ware_ID="";
								Ware_Name ="";
								  Long="";
								  Lat ="";
								  Site_ID = "";
								//System.out.println("site id and site name don't exists");

							}
					 	  
					 	
					 	  
					    
					unique_Node_ID=vhmap.get("NodeIPAddr")+"_"+"HW";
					
					PreparedStatement stmt = con.prepareStatement("insert into NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME,IP_ADDRESS,MAC_ADDRESS,SOFTWARE_VERSION,GATEWAY,GATEWAY_TYPE,GATEWAY_IP,STATUS_1,STATUS_2,PATCH_VERSION,LONGITUDE,LATITUDE,PART_NUMBER )"
					 		+ "values('" +codeid +"', '"+unique_Node_ID+"' ,'" + vhmap.get("NodeIPAddr") +"' ,'"+Node_Name+"','"+Node_Type+"','"+Domain+"','0','"+vhmap.get("NodeType")+"','0','0','0','0','"+Site_ID+"','"+ circleid +"',sysdate,sysdate,'"+fileType+"','" + filename +"','"+vhmap.get("CommunicationStatus")+"','0','0','0','0','0','1','0','"+Ware_ID+"','"+ Gprovider +"','0','"+Ware_Name+"','0','"+vhmap.get("NodeIPAddr")+"','"+vhmap.get("NodeMacAddr")+"','"+vhmap.get("SoftwareVersion")+"','"+vhmap.get("Gateway")+"','"+vhmap.get("GatewayType")+"','"+vhmap.get("GatewayIP")+"','"+vhmap.get("AdministrativeStatus")+"','"+vhmap.get("LifeCycleStatus")+"','"+vhmap.get("PatchVerList")+"','"+Long+"','"+Lat+"','"+vhmap.get("NodeSubType")+"') "); 
	                stmt.executeUpdate();
				     stmt.close();
					
					
				     NodeSeq++;
					
				}
					 
					
					}
				
				
				
				
			
			
			end = System.currentTimeMillis();
			System.out.printf("Import done in %d ms\n", (end - start));
			//System.out.printf("HMAP " +hmap);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
		
		
		
	
		
		return null;
	}
	
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
			
		 
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			
			//String excelFilePath="C:\\Users\\User\\Desktop\\Data\\Backbone & Metro & Access-TKL Fiber 2021_Coordinates.xlsx";
			 nextRow = rowIterator.next();
				Iterator<Cell> cellIterator=nextRow.cellIterator();
				int rowIndex = nextRow.getRowNum();
				//System.out.println(" rowInde x"+rowIndex);
				//Row sheetRow = firstSheet.getRow(rowIndex);
				//Cell Str = sheetRow.getCell(1);
				//String subStr = Str.getStringCellValue();
				
			
				if(rowIndex >3) {
				while(cellIterator.hasNext()) {
					Cell nextCell=cellIterator.next();
					int columnIndex=nextCell.getColumnIndex();
					switch (columnIndex) {
					case 0:
						
						NEName=nextCell.getStringCellValue();
						//System.out.println("ne name "+nextCell.getStringCellValue());
						break;
					case 1:
						NEModel=nextCell.getStringCellValue();
						break;
					case 2:
						NEIP=nextCell.getStringCellValue();
						break;
					case 3:
						SoftwareVer=nextCell.getStringCellValue();
						break;
					case 4:
						NEMAC=nextCell.getStringCellValue();
						if(NEMAC.trim().equalsIgnoreCase("--")) {
							NEMAC="";
						}
						break;
					case 5:
						NEID=nextCell.getStringCellValue();
						break;
					case 7:
						SubNet=nextCell.getStringCellValue();
						break;
					case 10:
						NESubType=nextCell.getStringCellValue();
						break;
					case 11:
						ComStatus=nextCell.getStringCellValue();
						break;
					case 12:
						AdminStatus=nextCell.getStringCellValue();
						break;
					case 14:
						crtdata=nextCell.getStringCellValue();
						break;
					case 16:
						Remark=nextCell.getStringCellValue();
						break;
					case 17:
						PatchVerList=nextCell.getStringCellValue();
						break;
					case 19:
						GatewayType=nextCell.getStringCellValue();
						if(GatewayType.trim().equalsIgnoreCase("--")) {
							GatewayType="";
						}
						break;
					case 20:
						Gateway=nextCell.getStringCellValue();
						if(Gateway.trim().equalsIgnoreCase("--")) {
							Gateway="";
						}
						break;
					case 21:
						GatewayIP=nextCell.getStringCellValue();
						if(GatewayIP.trim().equalsIgnoreCase("--")) {
							GatewayIP="";
						}
						break;
					case 23:
						LifeCycleStatus=nextCell.getStringCellValue();
						if(LifeCycleStatus.trim().equalsIgnoreCase("--")){
							LifeCycleStatus="";
						}
						break;
					}
					 
			
			
		 
		 
				}
		
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
				 hmap.put( "AdministrativeStatus", AdminStatus);
				 hmap.put( "CreatedON", crtdata);
				 hmap.put( "Remark", Remark);
				 hmap.put( "PatchVerList", PatchVerList);
				 hmap.put("LifeCycleStatus", LifeCycleStatus);
				 hmap.put("Gateway", Gateway);
				 hmap.put("GatewayType", GatewayType);
				 hmap.put("GatewayIP", GatewayIP);
				
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
			Statement stmt4 = null;
			int vcount =0;
			int i=0;
			
			// select all file having duplicata entry and same filename >1
			//String query1 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,count() counter from NODE_ACTIVE  group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR having count() >1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";  
			String query1 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,count(*) counter from NODE_ACTIVE  group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR having count(*) >1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";  
			stmt1 = con.createStatement();
		    ResultSet rs1 = stmt1.executeQuery(query1);
		    while (rs1.next()) {
		    	 String nodenamee;
	        	 if(rs1.getString("NODE_NAME").contains("'")) {
						
	        		 nodenamee=rs1.getString("NODE_NAME").replace("'", "''");
					}
					else {
						nodenamee=rs1.getString("NODE_NAME");
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
									
				        		 nodename=rs2.getString("NODE_NAME").replace("'", "''");
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
		    
		      
			 }
			catch(Exception e)  
			{  
				logger.info("error at deleterowsinALLTABLES is :"+ e.toString());
				System.out.println("error at deleterowsinALLTABLES is :"+ e.toString()); 
				
			}
		     
		}
	   
	   
}