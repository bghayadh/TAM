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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aliat.alm.models.ManHole;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadFilesRANZTE  {
	
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
	static String readfileRANZTEfrom;
	static String vfolderfrom;
	static String Gprovider;
	static String copyfileRANZTEto;
	static String vfolderto;
	static String circleid="0";
	static String Gyear;
	static String Gcodeattributid;
	static Logger logger;
	static FileHandler fh;
	static Connection conalm;
	static Connection con;
	static String vline="0";
	static String vcodeid ="";
	static String Domain;
	static String node_fk,node_atrr_fk;
	static Statement stmtp1,stmtp2,stmtp3;
	static PreparedStatement stmtp;
	static int NodeSeq;
	static int NodeCount,RackCount,AttributeCount,SubRackCount,HostCount,AccessoryCount,CabinetCount,BoardCount,ShelfCount;
	static int AttributeSeq,RackSeq,CabinetSeq,SubrackSeq,HostSeq,AccessorySeq,ShelfSeq,BoardSeq;
	static String siteID,wareID,wareName,nodePartNumber,longi,lat,creationDate,IPaddress,MACaddress,Status="0",softwareVersion,patchVersion,partNumber,Others;
	static HashMap<String, String> vhmap = new HashMap<String, String>();
	static HashMap<String, String> nodeID_PK = new HashMap<String, String>();
	static ArrayList<String> NodeIDArr = new ArrayList<String>();
	static ArrayList<String> NodeCabinetArr = new ArrayList<String>();
	//static List<List<String>> NodeID = new ArrayList<List<String>>();
	



	 
	static String nodeId = "";
	static String nodeType = "";
	static String nodeModel = "";
	static String nodeName = "";
	static String InvUnitType;
	static String BoardCond1,BoardCond2; 
	static String unique_Node_ID = "";	
		
	
	
	public static void main(String[] args, String vendor,String domain,String sub_domain) throws IOException, SQLException, InterruptedException {
		
	
		objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almconfig.dat"));
		System.out.println("vendor "+vendor);
		System.out.println("domain "+domain);
		System.out.println("subdomain "+sub_domain);
		
	
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
			 if (data.contains("readfileRANZTEfrom")) {
				 data1=data.split(";",-1);
				 readfileRANZTEfrom=data1[1];
				 System.out.println("readfileNokiafrom found: " + readfileRANZTEfrom);
				 data2=readfileRANZTEfrom.split("\\\\",-1);
				 vfolderfrom=data2[data2.length-1];
				 System.out.println("data2 found :" + data2[data2.length-1]);
				 //Gprovider=vfolderfrom.substring(4,6);
				 //System.out.println("Gprovider2 found: " + Gprovider);
			 }
			 if (data.contains("copyfileRANZTEto")) {
				 data1=data.split(";",-1);
				 copyfileRANZTEto=data1[1];
				 System.out.println("copyfileRANZTEto found: " + copyfileRANZTEto);
				 data2=copyfileRANZTEto.split("\\\\",-1);
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
				String logfilename="ParserLogRANZTE-"+dtf.format(now)+".log";

				//get only year from today date
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    	LocalDateTime now1 = LocalDateTime.now();
		    	Gyear=dtf1.format(now1).substring(0,4);
		    	//System.out.println(Gyear);

		    	File folder = new File(readfileRANZTEfrom);
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
			    	 String sqlStmtinit2 = "select * from EXECUTE_DOAMIN_VENDOR_FILES where DOMAIN='Ran' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS'";
					    ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
					    rsinit2.last();
				 	    int totalrecinit2 = rsinit2.getRow();
				 	   rsinit2.beforeFirst();
				 	   if (totalrecinit2 == 0 ) {
				 		  PreparedStatement stmtinit = con.prepareStatement("insert into EXECUTE_DOAMIN_VENDOR_FILES (DOMAIN,VENDOR,CREATION_DATE,STATUS) values ('Ran', '"+ Gprovider +"',sysdate,'IN PROCESS')");
							 stmtinit.executeUpdate();
							 stmtinit.close();

							 Date before = new Date();
							 System.out.println("LoadFilesRANZTE inprocess...");
							 System.out.println("It will take a few minutes..");
							 logger.info("LoadFilesRANZTE inprocess...");
								for (File file : listOfFiles) {
									if (file.isFile()) {
										
										
										 

								        String fichier =file.getName().toString();
										// reading file from folder
								        readfile(fichier);

										 File source = new File(readfileRANZTEfrom+"/"+file.getName());
									     File dest = new File(copyfileRANZTEto+"/"+file.getName()+".bkp");

									     //coypy file after treating it to destination folder
									     copyFiles(source,dest);
                                         //delete file from source folder
									     deleteFiles(readfileRANZTEfrom+"/"+file.getName());


								    }
								}
                                // remove dupliacte node 
								GetduplicateFilename("Ran",Gprovider);
								// update file status to completed
								 stmtinit = con.prepareStatement("update EXECUTE_DOAMIN_VENDOR_FILES set STATUS ='COMPLETED' where DOMAIN='Ran' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS'");
								 stmtinit.executeUpdate();
								 stmtinit.close();
								 
								 System.out.println("LoadFilesRANZTE completed");
								 logger.info("LoadFilesRANZTE completed");
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
	public static String readfile (String filename) throws FileNotFoundException, IOException, SQLException {
		CSVParser csvParser = new CSVParser(new FileReader(readfileRANZTEfrom + "\\" + filename), CSVFormat.DEFAULT);
		  List<CSVRecord> records = new ArrayList<>();
		  for (CSVRecord record : csvParser) {
			  records.add(record);
		}
		  //System.out.println("records "+records);
		  NodeCount =0;RackCount=0;AttributeCount=0;SubRackCount=0; HostCount=0;AccessoryCount=0;CabinetCount=0;BoardCount=0;ShelfCount=0;
		  node_atrr_fk="";
		  String [] temp;
		  temp=filename.split("\\.",-1);
		  String FileName=temp[0];
		  String fileType=temp[1];
		  
		  Calendar calendar = new GregorianCalendar();
		  int year = calendar.get(Calendar.YEAR);
		  
		  //select the node active sequence from the seq table in alm.
		  String sqlStmtinit2 = "select NODE_ACTIVE,NODE_ACTIVE_ATTRIBUTE,NODE_RACK,NODE_SUBRACK,NODE_CABINET,NODE_BOARD,NODE_HOST,NODE_SHELF,NODE_ACCESSORY from SEQ_TABLE";     
		  stmtp1 = conalm.createStatement();
		  ResultSet rsinit2 = stmtp1.executeQuery(sqlStmtinit2);
		  while(rsinit2.next()) {
			  //store the returned result in a variable to be increased each loop instead of accessing the database all the time
			  //which lead to exceed the maximum number of open cursors.
			NodeSeq = rsinit2.getInt("NODE_ACTIVE");
			AttributeSeq = rsinit2.getInt("NODE_ACTIVE_ATTRIBUTE");
			RackSeq = rsinit2.getInt("NODE_RACK");
			CabinetSeq = rsinit2.getInt("NODE_CABINET");
			SubrackSeq = rsinit2.getInt("NODE_SUBRACK");
			HostSeq = rsinit2.getInt("NODE_HOST");
			AccessorySeq = rsinit2.getInt("NODE_ACCESSORY");
			ShelfSeq = rsinit2.getInt("NODE_SHELF");
			BoardSeq = rsinit2.getInt("NODE_BOARD");
		  	
		  }
		  for(int i=2;i<records.size();i++) {
			  rsinit2.close();
			  stmtp1.close();
			  
			  InvUnitType=records.get(i).get(3);
			  BoardCond1=records.get(i).get(26);
			  BoardCond2=records.get(i).get(27);
			 // System.out.println("node_fk "+node_fk);
			 // System.out.println("InvUnitType "+InvUnitType);
			  
			  siteID="";
			  if(!NodeIDArr.contains(records.get(i).get(7))) {
				  //SITE INFO EXTRACTION
				  if(records.get(i).get(6).contains("_")) {// if the cell of the csv file contains _ then it may contain site ID
						//	if(records.get(i).get(6).split("_").length >= 3) { // if the number of the elements split in the cell according to _ then it may contain site ID
								siteID = records.get(i).get(6).split("_")[0];
								char charArray[] = siteID.toCharArray();
								if(Character.isDigit(charArray[0]) && !Character.isDigit(charArray[siteID.length()-1])) { // if the first character of the possible site id is number then it is a site ID.
									siteID = siteID;
									String sqlStmtinit3 = "select WARE_ID,WARE_NAME,LONGITUDE,LATITUDE from WAREHOUSE WHERE SITE_ID='"+siteID+"'";     
									  stmtp1 = conalm.createStatement();
									  ResultSet rsinit3 = stmtp1.executeQuery(sqlStmtinit3);
									  while(rsinit3.next()) {
										  wareID=rsinit3.getString("WARE_ID");
										  wareName = rsinit3.getString("WARE_NAME");
										  longi=rsinit3.getString("LONGITUDE");
										  lat = rsinit3.getString("LATITUDE");
									  }
									  rsinit3.close();
									  stmtp1.close();
								}else {
									wareID="";
									  wareName ="";
									  longi="";
									  lat = "";
									  siteID = "";
								}
									
							/*}else {
								wareID="";
								  wareName ="";
								  longi="";
								  lat = "";
								  siteID = "";
								//System.out.println("site id and site name don't exists");

							}*/
					}else {
							wareID="";
							  wareName ="";
							  longi="";
							  lat = "";
						}
				  
				  //
				  
				  vcodeid=year+"_NODE_ZTE_RAN_"+NodeSeq;
				  NodeIDArr.add(records.get(i).get(7));
				  nodeID_PK.put(records.get(i).get(7), vcodeid);
				  
				  
				  nodeType=records.get(i).get(10);
				  nodeId=records.get(i).get(7);
				  unique_Node_ID=records.get(i).get(7)+"_ZTE";
				  nodeName=records.get(i).get(6);
				  IPaddress=records.get(i).get(7);
				  nodeModel="";
				  patchVersion="";
				  Status=records.get(i).get(11);
				  MACaddress="";
				  softwareVersion="";
				  nodePartNumber="";
				  Others="{\"Active_Passive\":"+"\""+records.get(i).get(15)+"\"}";
				  
				  
				  PreparedStatement stmt = con.prepareStatement("insert into NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME,IP_ADDRESS,MAC_ADDRESS,SOFTWARE_VERSION,PATCH_VERSION,LONGITUDE,LATITUDE,PART_NUMBER,OTHERS )"
					 		+ "values('" +vcodeid +"', '"+unique_Node_ID+"' ,'" + nodeId +"' ,'"+nodeName+"','"+nodeType+"','"+Domain+"','0','"+nodeModel+"','0','0','0','0','"+siteID+"','"+ circleid +"',sysdate,sysdate,'"+fileType+"','" + FileName +"','"+Status+"','0','0','0','0','0','1','0','"+wareID+"','"+ Gprovider +"','0','"+wareName+"','0','"+IPaddress+"','"+MACaddress+"','"+softwareVersion+"','"+patchVersion+"','"+longi+"','"+lat+"','"+nodePartNumber+"','"+Others+"') "); 
	                stmt.executeUpdate();
				     stmt.close();
				     NodeSeq++;
				     NodeCount++;
				  
			  }
			 
			  node_fk = nodeID_PK.get(records.get(i).get(7));// get node_pk to used as foreign key in content tables
			  //
			  if( StringUtils.equalsIgnoreCase(InvUnitType, "rack")){
				  vhmap=getrackcolumns(records.get(i));
				  
				  addnewattribut("NODE_RACK","RACK",node_fk,vhmap.get("NODETYPE"),FileName,Gprovider );
				  vcodeid=year+"_NODE_ZTE_RAN_RAC_"+RackSeq;
				  PreparedStatement stmtrack = con.prepareStatement("insert into NODE_RACK (RACK_ID,RACKNO,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORNAME,SERIALNUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
          		   		+ "values('" + vcodeid +"','" + vhmap.get("RACKNO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" +vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER")+"',TO_DATE('" + vhmap.get("DATEOFMANUFACTURE")+"','YYYY-MM-DD')" +",TO_DATE('" + vhmap.get("DATEOFLASTSERVICE")+"','YYYY-MM-DD')" +",'" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("USERLABEL") +"','" + node_fk +"','" + node_atrr_fk +"',sysdate,'" + FileName +"','0','0','0','0','0','0','1','"+Domain+"','" + Gprovider +"' ) ");
				  stmtrack.executeUpdate();
				  stmtrack.close();
				  
				  RackCount++;
				  RackSeq++;
			  }
			  
			  if(StringUtils.equalsIgnoreCase(InvUnitType,"pack")) {
				 if(!NodeCabinetArr.contains(records.get(i).get(7))) { 
					 vhmap=getpackcolumns(records.get(i));
					 addnewattribut("NODE_CABINET","CABINET",node_fk,vhmap.get("NODETYPE"),FileName,Gprovider );
					  vcodeid=year+"_NODE_ZTE_RAN_CAB_"+CabinetSeq;
					 String cabinetnb="0";
					 PreparedStatement stmtcabinet = con.prepareStatement( "insert into NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,"
				   		   		+ "VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,"
				   		   		+ "BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,"
				   		   		+ "TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
				   		   		+ " values('" + vcodeid +"','"+ vhmap.get("SITEINDEX") +"','"+cabinetnb+"','"+vhmap.get("INVENTORYUNITID")+"',"
				   		   				+ "'0','0','"+vhmap.get("INVENTORYUNITTYPE")+"',"
				   		   				+ "'"+vhmap.get("VENDORUNITFAMILYTYPE")+"','0',"
				   		   				+ "'" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','0',"
				   		   				+"TO_DATE('" + vhmap.get("DATEOFMANUFACTURE")+"','YYYY-MM-DD')" +",TO_DATE('" + vhmap.get("DATEOFLASTSERVICE")+"','YYYY-MM-DD')" +","
				   		   				+ " '"+vhmap.get("UNITPOSITION")+"','"+vhmap.get("MANUFACTURERDATA")+"','0',"
				   		   				+ "'0','0','0',"
				   		   				+ "'"+vhmap.get("USERLABEL")+"','0','0',"
				   		   				+ "'0','"+node_fk+"','"+ node_atrr_fk +"',sysdate,'" + FileName +"',"
				   		   				+ "'0','0','0','0','0','0','"+ vline +"','1',"
				   		   						+ "'0',sysdate,'"+Domain+"','" + Gprovider +"') ");
					 stmtcabinet.executeUpdate();
					 stmtcabinet.close();
					 NodeCabinetArr.add(records.get(i).get(7));
					  CabinetSeq++;
					  CabinetCount++;
				  
				  }
				  
			  }
			  
			  if(StringUtils.equalsIgnoreCase(InvUnitType,"RRU")) {
				  vhmap=getsubrackcolumns(records.get(i));
				  addnewattribut("NODE_SUBRACK","SUBRACK",node_fk,vhmap.get("NODETYPE"),FileName,Gprovider );
				  vcodeid=year+"_NODE_ZTE_RAN_SUBRAC_"+SubrackSeq;
				  String cabNumber="0";
				  String subrackNb="";
				  PreparedStatement stmtsubrack = con.prepareStatement( "insert into NODE_SUBRACK (SUBRACK_ID,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,"
					   		+ "INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,"
					   		+ "UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,"
					   		+ "STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
					   		+ "values('" + vcodeid +"','"+vhmap.get("SITEINDEX")+"','" + cabNumber +"','" + subrackNb +"',"
					   		+ "'"+vhmap.get("INVENTORYUNITID")+"','0','0',"
					   		+ "'0','0','"+vhmap.get("INVENTORYUNITTYPE")+"','"+vhmap.get("VENDORUNITFAMILYTYPE")+"',"
					   		+ " '0','" + vhmap.get("VENDORNAME") +"','"+vhmap.get("SERIALNUMBER")+"','0',"
					   		+"TO_DATE('" + vhmap.get("DATEOFMANUFACTURE")+"','YYYY-MM-DD')" +",TO_DATE('" + vhmap.get("DATEOFLASTSERVICE")+"','YYYY-MM-DD')" +",'"+vhmap.get("UNITPOSITION")+"',"
					   		+ "'"+vhmap.get("MANUFACTURERDATA")+"','"+vhmap.get("USERLABEL")+"','0','0',"
					   		+ "'0','0','0','0',"
					   		+ "'0','" + node_fk +"','"+ node_atrr_fk +"',sysdate,'" + FileName +"','0','0','0','0',"
					   		+ "'0','0' ,'"+ vline +"','1','"+Domain+"','" + Gprovider +"') ");
				  stmtsubrack.executeUpdate();
				  stmtsubrack.close();
				  
				  SubrackSeq++;
				  SubRackCount++;
				  
			  }
			  
			  if(StringUtils.equalsIgnoreCase(InvUnitType,"shelf")) {
				  vhmap=getshelfcolumns(records.get(i));
				  
				  addnewattribut("NODE_SHELF","SHELF",node_fk,vhmap.get("NODETYPE"),FileName,Gprovider );
				  vcodeid=year+"_NODE_ZTE_RAN_SHLF_"+ShelfSeq;
				  PreparedStatement stmtshelf = con.prepareStatement("insert into NODE_SHELF (SHELF_ID,SHELFNO,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORNAME,SERIALNUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
          		   		+ "values('" + vcodeid +"','" + vhmap.get("SHELFNO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" +vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER")+"',TO_DATE('" + vhmap.get("DATEOFMANUFACTURE")+"','YYYY-MM-DD')" +",TO_DATE('" + vhmap.get("DATEOFLASTSERVICE")+"','YYYY-MM-DD')" +",'" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("USERLABEL") +"','" + node_fk +"','" + node_atrr_fk +"',sysdate,'" + FileName +"','0','0','0','0','0','0','1','"+Domain+"','" + Gprovider +"' ) ");
				  stmtshelf.executeUpdate();
				  stmtshelf.close();
				  
				  ShelfCount++;
				  ShelfSeq++;
				  
			  }
			  
				  if(StringUtils.equalsIgnoreCase(InvUnitType,"accessory")) {
					 vhmap=getaccessorycolumns(records.get(i));
					 
					 addnewattribut("NODE_ACCESSORY","ACCESSORY",node_fk,vhmap.get("NODETYPE"),FileName,Gprovider );
					  vcodeid=year+"_NODE_ZTE_RAN_ACCESSORY_"+AccessorySeq;
					 
					 PreparedStatement stmtaccessory = con.prepareStatement("insert into NODE_ACCESSORY (ACCESSORY_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,ACCESSORYTYPE,ADDTIONALINFORMATION,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
			      		   		+ "values('" + vcodeid +"','" + vhmap.get("RACKPOSITION") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','0','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','0','" + vhmap.get("SOFTVER") +"',TO_DATE('" + vhmap.get("DATEOFMANUFACTURE")+"','YYYY-MM-DD')" +",TO_DATE('" + vhmap.get("DATEOFLASTSERVICE")+"','YYYY-MM-DD')" +", '" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("ACCESSORYTYPE") +"','" + vhmap.get("ADDTIONALINFORMATION") +"','" + node_fk +"','" + node_atrr_fk +"',sysdate,'" + FileName +"' ,'" + vhmap.get("STATUS") +"','0','0','0','0','0','0','1','"+Domain+"','" + Gprovider +"') ");
					 stmtaccessory.executeUpdate();
					 stmtaccessory.close();
					 
					 AccessorySeq++;
					 AccessoryCount++;
					 
			  }
				  
			  
			  
			  if(StringUtils.equalsIgnoreCase(InvUnitType,"host")) {
				  vhmap=gethostcolumns(records.get(i));
				  addnewattribut("NODE_HOST","HOST",node_fk,vhmap.get("NODETYPE"),FileName,Gprovider );
				  vcodeid=year+"_NODE_ZTE_RAN_HOST_"+HostSeq;
				  
				  PreparedStatement stmthost = con.prepareStatement("insert into NODE_HOST (HOST_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,HOSTNAME,NUMBEROFCPU,MEMSIZE,HARDDISKSIZE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
			  		   		+ " values('" + vcodeid +"','" + vhmap.get("RACKNO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','0','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','0','" + vhmap.get("SOFTVER") +"',TO_DATE('" + vhmap.get("DATEOFMANUFACTURE")+"','YYYY-MM-DD')" +",TO_DATE('" + vhmap.get("DATEOFLASTSERVICE")+"','YYYY-MM-DD')" +", '" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("HOSTNAME") +"','" + vhmap.get("NUMBEROFCPU") +"','" + vhmap.get("MEMSIZE") +"','" + vhmap.get("HARDDISKSIZE") +"','" + node_fk +"','" + node_atrr_fk +"',sysdate,'" + FileName +"','" + vhmap.get("STATUS") +"' ,'0','0','0','0','0','0','1','"+Domain+"','" + Gprovider +"') ");
					  stmthost.executeUpdate();
					  stmthost.close();
					  
					  HostCount++;
					  HostSeq++;
					  
			  }
			  //board
			  
			  if(StringUtils.containsIgnoreCase(BoardCond1, "board") || StringUtils.containsIgnoreCase(BoardCond2, "board")) {
				  vhmap=getboardcolumns(records.get(i));
				  addnewattribut("NODE_BOARD","BOARD",node_fk,vhmap.get("NODETYPE"),FileName,Gprovider );
				  vcodeid=year+"_NODE_ZTE_RAN_BRD_"+BoardSeq;
				  
				  PreparedStatement stmthost = con.prepareStatement("insert into NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,"
					 		+ "BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,"
					 		+ "DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,"
					 		+ "EXTINFO,APDEVINFO,WORKMODE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,"
					 		+ "TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,OTHERS) "
	         		   		+ "values('" + vcodeid +"','0','0','0',"
	         		   				+ "'"+vhmap.get("RACKNO")+"','0','"+vhmap.get("SLOTNO")+"','0',"
	         		   				+ "'0','"+vhmap.get("INVENTORYUNITID")+"','0',"
	         		   				+ "'"+vhmap.get("BOARDNAME")+"','0','" + vhmap.get("INVENTORYUNITTYPE") +"',"
	         		   				+ " '"+vhmap.get("VENDORUNITFAMILYTYPE")+"', '0', "
	         		   				+ "'"+vhmap.get("VENDORNAME")+"', '" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',"
	         		   				+"TO_DATE('" + vhmap.get("DATEOFMANUFACTURE")+"','YYYY-MM-DD')" +",TO_DATE('" + vhmap.get("DATEOFLASTSERVICE")+"','YYYY-MM-DD')" +",'"+vhmap.get("UNITPOSITION")+"',"
	         		   				+ "'"+vhmap.get("MANUFACTURERDATA")+"','"+vhmap.get("SOFTWAREVERSION")+"','"+vhmap.get("LOGICVERSION")+"','0',"
	         		   				+ "'0','0','0','0',"
	         		   				+ "'0','0','"+vhmap.get("USERLABEL")+"','0',"
	         		   				+ "'0','0','" + node_fk +"','"+ node_atrr_fk +"' ,sysdate,'" + FileName +"',"
	         		   				+ "'0','0','0','0','0','0',"
	         		   				+ "'0','1','0',sysdate,'"+Domain+"','" + Gprovider+"','"+vhmap.get("OTHERS")+"') ");
					  stmthost.executeUpdate();
					  stmthost.close();
					  
					  BoardCount++;
					  BoardSeq++;
					  
			  }
				  
			  
		  }
		  
		 stmtp = conalm.prepareStatement("UPDATE SEQ_TABLE SET NODE_ACTIVE = NODE_ACTIVE +"+NodeCount +",NODE_ACTIVE_ATTRIBUTE = NODE_ACTIVE_ATTRIBUTE +"+AttributeCount+",NODE_RACK = NODE_RACK +"+RackCount+",NODE_SUBRACK=NODE_SUBRACK +"+SubRackCount+",NODE_HOST=NODE_HOST +"+HostCount+",NODE_ACCESSORY=NODE_ACCESSORY +"+AccessoryCount+",NODE_CABINET=NODE_CABINET +"+CabinetCount+",NODE_BOARD=NODE_BOARD +"+BoardCount+",NODE_SHELF=NODE_SHELF +"+ShelfCount);//records.size()-2) is used to remove the unnecessary header rows in the csv file
		 stmtp.executeUpdate();
		 stmtp.close();
		// System.out.println("siteid "+siteID+" counter "+NodeCount +" id "+vcodeid);
		 //System.out.println("nodeid " +NodeIDArr.size());
		 //System.out.println("nodeid " +NodeIDArr);
		
		return null;
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
	 
	 public static HashMap getrackcolumns(CSVRecord Record) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 Record.get(6);
		
		 
		 String[] data3;
		 data3=Record.get(1).split("=",-1);
		 String racknb=data3[1];
		 
		 String serialnb;
		 if(StringUtils.containsIgnoreCase(BoardCond1, "board") || StringUtils.containsIgnoreCase(BoardCond2, "board")) {
			 serialnb="";
		 }
		 else {
			 serialnb=Record.get(5);
		 }
		 
		// System.out.println("testing manfdate 6"+ Record.get(12));
		// System.out.println("testing manfdate 6"+ Record.get(13));
		 hmap.put( "RACKNO", racknb);
		 hmap.put( "INVENTORYUNITID", Record.get(16));
		 hmap.put( "INVENTORYUNITTYPE", Record.get(3));
		 hmap.put( "VENDORUNITFAMILYTYPE", Record.get(4));
		 hmap.put( "SERIALNUMBER",  serialnb);
		 hmap.put( "UNITPOSITION",  Record.get(1));
		 hmap.put( "USERLABEL",  Record.get(6));
		 hmap.put("STATUS", "0");
		 hmap.put( "DATEOFMANUFACTURE", Record.get(12));
		 hmap.put( "DATEOFLASTSERVICE", Record.get(13));
		 hmap.put( "MANUFACTURERDATA", Record.get(2));
		 hmap.put( "NODETYPE", Record.get(10));
		 hmap.put( "VENDORNAME", Record.get(20));
		 
		 return hmap;
	 }
	 
	 public static HashMap getsubrackcolumns(CSVRecord Record) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 String[] data3;
		 String[] data4;
		 String[] data5;
		 data3=Record.get(1).split("=",-1);
		 String slotnb=data3[3];
		 data4=data3[1].split(",",-1);
		 data5=data3[2].split(",",-1);
		 String racknb=data4[0];
		 String shelfnb=data5[0];
		 //System.out.println("rack nb "+racknb);
		 
		 String serialnb;
		 if(StringUtils.containsIgnoreCase(BoardCond1, "board") || StringUtils.containsIgnoreCase(BoardCond2, "board")) {
			 serialnb="";
		 }
		 else {
			 serialnb=Record.get(5);
		 }
		 
		 String siteindex="";
		 
		 
		 hmap.put( "RACKNO", racknb);
		 hmap.put( "SHELFNO", shelfnb);
		 hmap.put( "SLOTNO", slotnb);
		 hmap.put( "SITEINDEX", siteindex);
		 hmap.put( "INVENTORYUNITID", Record.get(16));
		// hmap.put( "RACKTYPE", "0");
		 hmap.put( "INVENTORYUNITTYPE", Record.get(3));
		 hmap.put( "VENDORUNITFAMILYTYPE", Record.get(4));
		 //hmap.put( "VENDORUNITTYPENUMBER", "0");
		 hmap.put( "SERIALNUMBER",  serialnb);
		 //hmap.put( "HARDWAREVERSION", "0");
		 hmap.put( "UNITPOSITION",  Record.get(1));
		 //hmap.put( "MODEL", "0");
		 hmap.put( "USERLABEL",  Record.get(6));
		 hmap.put("STATUS", "0");
		 hmap.put( "DATEOFMANUFACTURE", Record.get(12));
		 hmap.put( "DATEOFLASTSERVICE", Record.get(13));
		 hmap.put( "MANUFACTURERDATA", Record.get(2));
		 hmap.put( "NODETYPE", Record.get(10));
		 hmap.put( "VENDORNAME", Record.get(20));
		 
		 
		 return hmap;
	 }
	 
	 public static HashMap getshelfcolumns(CSVRecord Record) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 String[] data3;
		 data3=Record.get(1).split("=",-1);
		 String shelfnb=data3[2];
		 
		 String serialnb;
		 if(StringUtils.containsIgnoreCase(BoardCond1, "board") || StringUtils.containsIgnoreCase(BoardCond2, "board")) {
			 serialnb="";
		 }
		 else {
			 serialnb=Record.get(5);
		 }
		 
		 
		 hmap.put( "SHELFNO", shelfnb);
		 hmap.put( "INVENTORYUNITID", Record.get(16));
		 hmap.put( "INVENTORYUNITTYPE", Record.get(3));
		 hmap.put( "VENDORUNITFAMILYTYPE", Record.get(4));
		 hmap.put( "SERIALNUMBER",  serialnb);
		 hmap.put( "UNITPOSITION",  Record.get(1));
		 hmap.put( "USERLABEL",  Record.get(6));
		 hmap.put("STATUS", "0");
		 hmap.put( "DATEOFMANUFACTURE", Record.get(12));
		 hmap.put( "DATEOFLASTSERVICE", Record.get(13));
		 hmap.put( "MANUFACTURERDATA", Record.get(2));
		 hmap.put( "NODETYPE", Record.get(10));
		 hmap.put( "VENDORNAME", Record.get(20));
		 
		 return hmap;
	 }
	 
	 public static HashMap getpackcolumns(CSVRecord Record) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		
		 String serialnb;
		 if(StringUtils.containsIgnoreCase(BoardCond1, "board") || StringUtils.containsIgnoreCase(BoardCond2, "board")) {
			 serialnb="";
		 }
		 else {
			 serialnb=Record.get(5);
		 }
		 
		 String siteindex="";
		 
		 String manfdate=Record.get(12);
		 String lastservicedate=Record.get(13);
		 if(!manfdate.contains("-")) {
			 manfdate="";
		 }
		 if(!lastservicedate.contains("-")) {
			 lastservicedate="";
		 }
		 
		
		 hmap.put( "SITEINDEX", siteindex);
		 hmap.put( "INVENTORYUNITID", Record.get(16));
		 hmap.put( "INVENTORYUNITTYPE", Record.get(3));
		 hmap.put( "VENDORUNITFAMILYTYPE", Record.get(4));
		 hmap.put( "SERIALNUMBER",  serialnb);
		 hmap.put( "UNITPOSITION",  Record.get(1));
		 hmap.put( "USERLABEL",  Record.get(6));
		 hmap.put("STATUS", "0");
		 hmap.put( "DATEOFMANUFACTURE", manfdate);
		 hmap.put( "DATEOFLASTSERVICE", lastservicedate);
		 hmap.put( "MANUFACTURERDATA", Record.get(2));
		 hmap.put( "NODETYPE", Record.get(10));
		 hmap.put( "VENDORNAME", Record.get(20));
		 
		 return hmap;
	 }
	 
	 public static HashMap getboardcolumns(CSVRecord Record) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 String[] data3;
		 String[] data4;
		 data3=Record.get(1).split("=",-1);
		 String slotnb=data3[3];
		 data4=data3[1].split(",",-1);
		 String racknb=data4[0];
		 data4=data3[2].split(",",-1);
		 String shelfnb=data4[0];
		 
		 String siteindex="";
		 
		 String manfdate=Record.get(12);
		 String lastservicedate=Record.get(13);
		 if(!manfdate.contains("-")) {
			 manfdate="";
		 }
		 if(!lastservicedate.contains("-")) {
			 lastservicedate="";
		 }
		 String bootversion="";
		 String softwareversion="";
		 String MMCBootVersion="";
		 String LogicVersion="";
		 if(Record.get(26).contains("Board-Boot Version")) {
			 String[] data5;
			 data5=Record.get(26).split("#",-1);
			 String[] data6;
			// data5=Record.get(26).split("#",-1);
			 if(data5.length==2) {
				 data6=data5[0].split("=",-1); 
				 LogicVersion=data6[1];
				 data6=data5[1].split("=",-1); 
				 bootversion=data6[1];
				 MMCBootVersion="";
			 }
			 else if(data5.length==3) {
				 data6=data5[0].split("=",-1); 
				 LogicVersion=data6[1];
				 data6=data5[1].split("=",-1); 
				 bootversion=data6[1];
				 data6=data5[2].split("=",-1); 
				 MMCBootVersion=data6[1];;
			 }
		 }
		 else if(!(Record.get(26).contains("Board-Boot Version")) && Record.get(26).contains("Mmc-Boot Version")) {
			 String[] data7=Record.get(26).split("#",-1);
			 String[] data8;
			 data8=data7[0].split("=",-1); 
			 LogicVersion=data8[1];
			 data8=data7[1].split("=",-1); 
			 MMCBootVersion=data8[1];
		 }
		 
		 if(Record.get(27).contains("Board-Software Version")) {
			 String[] data7;
			 data7=Record.get(27).split("=",-1);
			 softwareversion=data7[1];
			 
		 }
		String Others="{\"BootVersion\":"+"\""+bootversion+"\","+"\"MMCBootVersion\":"+"\""+MMCBootVersion+"\""+"}";
		
		 hmap.put( "RACKNO", racknb);
		 hmap.put( "SHELFNO", shelfnb);
		 hmap.put( "SLOTNO", slotnb);
		 hmap.put( "SITEINDEX", siteindex);
		 hmap.put( "INVENTORYUNITID", Record.get(16));
		 hmap.put( "INVENTORYUNITTYPE", Record.get(3));
		 hmap.put( "VENDORUNITFAMILYTYPE", Record.get(4));
		 hmap.put( "SERIALNUMBER",Record.get(5));
		 hmap.put( "UNITPOSITION",  Record.get(1));
		 hmap.put( "BOARDNAME", Record.get(18));
		 hmap.put( "USERLABEL",  Record.get(6));
		 hmap.put("STATUS", "0");
		 hmap.put( "DATEOFMANUFACTURE", manfdate);
		 hmap.put( "DATEOFLASTSERVICE", lastservicedate);
		 hmap.put( "MANUFACTURERDATA", Record.get(2));
		 hmap.put( "NODETYPE", Record.get(10));
		 hmap.put( "VENDORNAME", Record.get(20));
		 hmap.put( "BOOTVERSION", bootversion);
		 hmap.put( "SOFTWAREVERSION", softwareversion);
		 hmap.put( "HARDWAREVERSION", "");
		 hmap.put( "LOGICVERSION", LogicVersion);
		 hmap.put( "OTHERS", Others);
		 
		 
		 
		 return hmap;
	 }
	 
	 public static HashMap gethostcolumns(CSVRecord Record) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 String[] data3;
		 String[] data4;
		 data3=Record.get(24).split("#",-1);
		 data4=data3[1].split("=",-1);
		 String hostname=data4[1];
		 data4=data3[2].split("=",-1);
		 String nbfCPU=data4[1];
		 data4=data3[3].split("=",-1);
		 String memSize=data4[1];
		 data4=data3[4].split("=",-1);
		 String harddiskSize=data4[1];
		 
		 String serialnb;
		 if(StringUtils.containsIgnoreCase(BoardCond1, "board") || StringUtils.containsIgnoreCase(BoardCond2, "board")) {
			 serialnb="";
		 }
		 else {
			 serialnb=Record.get(5);
		 }
		
		 hmap.put( "HOSTNAME", hostname);
		 hmap.put( "NUMBEROFCPU", nbfCPU);
		 hmap.put( "MEMSIZE", memSize);
		 hmap.put( "HARDDISKSIZE", harddiskSize);
		 hmap.put( "RACKNO", "");
		 hmap.put( "INVENTORYUNITID", Record.get(16));
		 hmap.put( "INVENTORYUNITTYPE", Record.get(3));
		 hmap.put( "VENDORUNITFAMILYTYPE", Record.get(4));
		 hmap.put( "SERIALNUMBER",  serialnb);
		 hmap.put( "UNITPOSITION",  Record.get(1));
		 hmap.put( "USERLABEL",  Record.get(6));
		 hmap.put("STATUS", "0");
		 hmap.put( "DATEOFMANUFACTURE", Record.get(12));
		 hmap.put( "DATEOFLASTSERVICE", Record.get(13));
		 hmap.put( "MANUFACTURERDATA", Record.get(2));
		 hmap.put( "NODETYPE", Record.get(10));
		 hmap.put( "SOFTVER", Record.get(9));
		 hmap.put( "VENDORNAME", Record.get(20));
		 
		 return hmap;
	 }
	 
	 public static HashMap getaccessorycolumns(CSVRecord Record) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 String[] data3;
		 String[] data4;
		 data3=Record.get(24).split("#",-1);
		 
		 data4=data3[0].split("=",-1);
		 String rackposition=data4[1];
		 
		 data4=data3[1].split("=",-1);
		 String accessoryname=data4[1];
		 
		 data4=data3[2].split("=",-1);
		 String accessorytype=data4[1];
		 
		 data4=data3[3].split("=",-1);
		 String additionalinfo=data4[1];
		 
		 String serialnb;
		 if(StringUtils.containsIgnoreCase(BoardCond1, "board") || StringUtils.containsIgnoreCase(BoardCond2, "board")) {
			 serialnb="";
		 }
		 else {
			 serialnb=Record.get(5);
		 }
		
		 hmap.put( "ACCESSORYNAME", accessoryname);
		 hmap.put( "ACCESSORYTYPE", accessorytype);
		 hmap.put( "ADDTIONALINFORMATION", additionalinfo);
		 hmap.put( "RACKPOSITION", rackposition);
		 hmap.put( "INVENTORYUNITID", Record.get(16));
		 hmap.put( "INVENTORYUNITTYPE", Record.get(3));
		 hmap.put( "VENDORUNITFAMILYTYPE", Record.get(4));
		 hmap.put( "SERIALNUMBER",  serialnb);
		 hmap.put( "UNITPOSITION",  Record.get(1));
		 hmap.put( "USERLABEL",  Record.get(6));
		 hmap.put("STATUS", "0");
		 hmap.put( "DATEOFMANUFACTURE", Record.get(12));
		 hmap.put( "DATEOFLASTSERVICE", Record.get(13));
		 hmap.put( "MANUFACTURERDATA", Record.get(2));
		 hmap.put( "NODETYPE", Record.get(10));
		 hmap.put( "SOFTVER", Record.get(9));
		 hmap.put( "VENDORNAME", Record.get(20));
	 
		 
		 return hmap;
	 }
	 
	 
	 
	 
	   public static void addnewattribut (String tablename,String attribat, String codeid,String nodetype,String filename,String vendor ) throws SQLException {
	    	
		   PreparedStatement stmtinsert1=null;
		   /*Statement stmt1 = null;
	    	
	    	int localcounter=0;
	    	stmt1 = con.createStatement();   
		 	String sqlStmt = "select node_attr_pk,ATTRIBUTE from node_active_attribute where node_pk='"+ codeid +"'";   
		    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
		    vline= "0";
		    while (rs1.next()) { 
		    	if (vline.equalsIgnoreCase("0")) {
		    		if (attribat.equalsIgnoreCase(rs1.getString("ATTRIBUTE"))) {
		    			
		    			Gcodeattributid=rs1.getString("node_attr_pk");
			    		vline="FOUND";
		    		}
		    	}
		    	localcounter=localcounter+1;
		    }
		    rs1.close();
		    stmt1.close();
		*/
		   // if (vline.equalsIgnoreCase("0")) {
		    	//Gcodeattributid= localgetseqNbr(1);  /// 7 to select cabinet_id 
		    	Gcodeattributid=Gyear+"_"+ "ATTRIBUTE_ZTE_RAN"+'_'+AttributeSeq;
		    	node_atrr_fk=Gcodeattributid;
		    	
		    	//vline  = Integer.toString(localcounter);
		    	 stmtinsert1 = con.prepareStatement("insert into  NODE_ACTIVE_ATTRIBUTE (NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
		 			 		+  "values ('"+ Gcodeattributid  +"','"+ attribat +"', '"+ tablename +"','"+ codeid +"','" + nodetype + "',sysdate,'"+ filename +"','0','0','0','0','0','1','"+ vline +"','"+Domain+"','" + vendor +"','0')  ");
				  stmtinsert1.executeUpdate();
				  stmtinsert1.close();
				  AttributeCount++;
				  AttributeSeq++;
		   // } 
				  
			
	   		
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
		    	 if(rs1.getString("NODE_NAME") != null && rs1.getString("NODE_NAME") !="(null)") {
		        	 if(rs1.getString("NODE_NAME").contains("'")) {
							
		        		 nodenamee=rs1.getString("NODE_NAME").replace("'", "''");
						}
						else {
							nodenamee=rs1.getString("NODE_NAME");
						}
		    	 }
		    	 else {
		    		 nodenamee="";
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