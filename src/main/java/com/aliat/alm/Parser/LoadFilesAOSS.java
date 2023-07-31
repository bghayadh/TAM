package com.aliat.alm.Parser;
import org.apache.commons.io.FileUtils;
import java.io.BufferedReader;
import java.io.File;
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
import java.util.HashMap;
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

public class LoadFilesAOSS {
	private static final CopyOption REPLACE_EXISTING = null;
	static Connection con;
	static Connection conalm;
    static String Gyear ;
    static String Fsource;
	static PreparedStatement stmtp;
	static HashMap<String, String> vhmap = new HashMap<String, String>();
	static String[] hider =null;
	static String[] ResultNode =null;
	static String projpath=null;
	static String vadata=null;
	static int totrow=0;
	static int SumRow=0;
	static int totSumRow=0;
	static int totSumCol=0;
	static String filetype =null;
	static String vMODEL=null;
	static String tech2 ="0";
	static String tech3 ="0";
	static String tech4 ="0";
	static String tech5 ="0";
	static String codeid="0";
	static String circleid="0";
	static String attributeTable=null;
	static Logger logger;
	static FileHandler fh;
	static BufferedReader objReader1 = null;
	static String strCurrentLine1;
	static String logpath;
	static String db1path;
	static String db2path;
	static String readfileAIMfrom;
	static String copyfileAIMto;
	static String username1;
	static String password1;
	static String username2;
	static String password2;
	static String Gprovider;
	static String vfolderfrom;
	static String vfolderto;
	static String almposition;
	
	public static void main(String[] args) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

	        ///AIM_GSMBTS_SSB BSC6_RIQ_B6_Pvt_2G_4426.xml     id is not number	
		    /// AIM_eNodeB_FAH-AL-AHM_B1_4G_4533.xml          date has / instead of -
		
		    // Read all required paths ex(DB1,DB2,Log file, Path to read file AIM ,path to copy processed)
		
			System.out.println("Start withh LOAD :" + System.getProperty("user.dir"));
		
		 	objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+"almconfig.dat"));
			 while ((strCurrentLine1 = objReader1.readLine()) != null){
				 String data = strCurrentLine1;
				 String[] data1 ;
				 String[] data2 ;
				 
				 if (data.contains("projectpath")) {
					 data1=data.split(";",-1);
					 projpath=data1[1];
					 System.out.println("projectpath found :" + projpath);
				 }
				 if (data.contains("logpath")) {
					 data1=data.split(";",-1);
					 logpath=data1[1];
					 System.out.println("logpath found :" + logpath);
				 }
				 if (data.contains("db1path")) {
					 data1=data.split(";",-1);
					 db1path=data1[1];
					 username1=data1[2];
					 password1=data1[3];
					 System.out.println("db1path found :" + db1path);
					 System.out.println("username1 found :" + username1);
					 System.out.println("password1 found :" + password1);
				 }
				 if (data.contains("db2path")) {
					 data1=data.split(";",-1);
					 db2path=data1[1];
					 username2=data1[2];
					 password2=data1[3];
					 System.out.println("db2path found :" + db2path);
					 System.out.println("username2 found :" + username2);
					 System.out.println("password2 found :" + password2);
				 }
				 if (data.contains("readfileAIMfrom")) {
					 data1=data.split(";",-1);
					 readfileAIMfrom=data1[1];
					 System.out.println("readfileAIMfrom found :" + readfileAIMfrom);
					 data2=readfileAIMfrom.split("/",-1);
					 vfolderfrom=data2[data2.length-1];
					 System.out.println("vfolderfrom :" + data2[data2.length-1]);
					 Gprovider=vfolderfrom.substring(0,2);
					 System.out.println("Gprovider2 found :" + Gprovider);
				 }
				 if (data.contains("copyfileAIMto")) {
					 data1=data.split(";",-1);
					 copyfileAIMto=data1[1];
					 System.out.println("copyfileAIMto found :" + copyfileAIMto);
					 data2=copyfileAIMto.split("/",-1);
					 vfolderto=data2[data2.length-1];
					 System.out.println("copyfileAIMto  :" + vfolderto);
				 }
			}
			 objReader1.close();
			 
			 System.out.println("get cricle value  :" + System.getProperty("user.dir")+"/"+"almcircle.dat");
			 objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+"almcircle.dat"));
			 while ((strCurrentLine1 = objReader1.readLine()) != null){
				 String data = strCurrentLine1;
				 String[] data1 ;
				 data1=data.split(";",-1);
				 circleid=data1[1];
			 }
			 objReader1.close();	 
			 System.out.println(" cricle is  :" + circleid);
			 
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			String lofilename="ParserLogAIM-"+dtf.format(now)+".log";
			
			//get only year from today date
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	LocalDateTime now1 = LocalDateTime.now();
        	Gyear=dtf1.format(now1).substring(0,4);
        	System.out.println(Gyear);  
			

			File folder = new File(readfileAIMfrom);
			File[] listOfFiles = folder.listFiles();
			logger = Logger.getLogger("MyLog"); 
			logger.setUseParentHandlers(false);

						
			// This block configure the logger with handler and formatter  and PATH
			System.out.println(logpath+"/"+lofilename);
			System.out.println("start log file");
			try {
	        fh = new FileHandler(logpath+"/"+lofilename);
			}catch (Exception e) {
				System.out.println("start  log file error" +e.toString());
			}
	        System.out.println("start 2 log file");
	        logger.addHandler(fh);
	        System.out.println("start 3 log file");
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
	        System.out.println("start 4 log file");
	        
	        System.out.println("db1path " + db1path + "username1 "+ username1 +"password1 "+ password1);
				DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
				conalm = DriverManager.getConnection (db1path,username1,password1);
				
				 // Connect to almparser DB 
				//String dbURL ="jdbc:oracle:thin:@localhost:1521/almparsing";
				 //username ="almparser";
				 //password ="almparser";
				try {
				    //con= DriverManager.getConnection(dbURL,username,password);
				    con= DriverManager.getConnection(db2path,username2,password2);
				System.out.println("Connected to oracle DB");
				} catch (SQLException e) {
				       System.out.println("Opss, error");
				       e.printStackTrace();
				   }
				
				// delete al data in all table in almparser DB
				//DeletedatafromNodeTables();
				
				System.out.println("start read from DB");
				
				//validate if the same process is running now if yes we cnnot run it twice until finish
				Statement stmtinit2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
		    	 String sqlStmtinit2 = "select * from EXECUTE_DOAMIN_VENDOR_FILES where DOMAIN='Mobile Access Domain' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS'";          
				    ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
				    rsinit2.last();
			 	    int totalrecinit2 = rsinit2.getRow(); 
			 	   rsinit2.beforeFirst();
			 	   if (totalrecinit2 == 0 ) {
			 		  PreparedStatement stmtinit = con.prepareStatement("insert into EXECUTE_DOAMIN_VENDOR_FILES (DOMAIN,VENDOR,CREATION_DATE,STATUS) values ('Mobile Access Domain', '"+ Gprovider +"',sysdate,'IN PROCESS')");
						 stmtinit.executeUpdate();
						 stmtinit.close();
						
						 for (File file : listOfFiles) {
								if (file.isFile()) {
							        //System.out.println(file.getName());
									totSumCol=totSumCol+1;
							        String fichier =file.getName().toString();
									readfile(fichier); 
									
									 File source = new File(readfileAIMfrom+"/"+file.getName());
								     File dest = new File(copyfileAIMto+"/"+file.getName()+".bkp");
								     
								     copyFiles(source,dest);
								     
								     deleteFiles(readfileAIMfrom+"/"+file.getName());
								     
								    
							    }
							}
						 
						   System.out.println("SUM GRAND TOTAL OF ALL ROWS in ALL FILES :  " + totSumRow);
							System.out.println("SUM GRAND TOTAL OF ALL FILES :  " + totSumCol);
							logger.info("SUM GRAND TOTAL OF ALL FILES :  " + totSumCol);
							
							//remove dupliacted processed files
							System.out.println("In process to move Dupliate Data from Tables ...  " );
								GetduplicateFilename("Mobile Access Domain",Gprovider);
							System.out.println("Clean Dupliate Data from Tables Completed  " );
							logger.info("Clean Dupliate Data from Tables Completed  ");
						 
							
							 stmtinit = con.prepareStatement("update EXECUTE_DOAMIN_VENDOR_FILES set STATUS ='COMPLETED' where DOMAIN='Mobile Access Domain' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS'");
							 stmtinit.executeUpdate();
							 stmtinit.close();
			 	   } else {
			 		   System.out.println("A process already is runnig , please wait until process ending");
			 		   logger.info("A process already is runnig , please wait until process ending");
			 	   }
			 	  rsinit2.close();
			 	  stmtinit2.close();
			 	 
			 	   
		
			
			/*  we will separated process to move data from parsing DB to ALM DB called    CopyParsingDataToALM
			System.out.println("In process to move Data to TEMP Tables ...  " );
					// Delete all data from temp node tables in ALM schema
					deleteTempNodeTables();
			        // copy all data from node tables to TEMP Tables
					RunCopydata(); 
			System.out.println("COPYING DATA TO TEMP Tables COMPLETED !" );
			logger.info("COPYING DATA TO TEMP Tables COMPLETED !");
			
			System.out.println("In process to Delete data from Node Tables ...  " );
	        // Delete data from Node Tables
				DeletedatafromNodeTables();
			System.out.println("DELETE DATA FROM NODE Tables COMPLETED !" );
			logger.info("DELETE DATA FROM NODE Tables COMPLETED !");
			*/	
			con.close();
			conalm.close();
	   }
	 public static String readfile (String filename){
	
			BufferedReader objReader = null;
			
	       
	        String[] hider =null;
	        String todaydate = null;
	        try{
	        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        	LocalDateTime now = LocalDateTime.now();
	        	todaydate=dtf.format(now);
	        	System.out.println(todaydate);
	   

					 //logger.info("File Type is: " + filetype);  
					 
		        
	        	
					
					 //String projpath=System.getProperty("user.dir");
					 //projpath=projpath.substring(0, projpath.length()-3);
					
					
					BufferedReader objReader0 = new BufferedReader(new FileReader(projpath+"/"+vfolderfrom+"/"+filename));
					while ((objReader0.readLine()) != null) {
						objReader0.readLine();
						String data = objReader0.readLine();
						vadata=data.trim();
						System.out.println(vadata);
						break;
					}
					objReader0.close();
					
					// function to return node id, name and type
					ResultNode=getnodeidnametype(vadata);
					System.out.println(ResultNode[0]); /// return  Node ID
					System.out.println(ResultNode[1]); /// return  Node Name
					System.out.println(ResultNode[2]); /// return  Node Type
					System.out.println(ResultNode[3]); /// return  0
					
					
					
				    //Check file type BTS, BSC , 3G, 4G,5G,OSS... based on name
			        //System.out.println(filename); 
			        String filename1=ResultNode[1];   //movd node name to filename1
			        String filename2=ResultNode[2];   //movd node type to filename2
				    	tech2 ="0";
				    	tech3 ="0";
				    	tech4 ="0";
				    	tech5 ="0";
				    	
				    	if ((filename1.contains("RNC")) && (filename2.contains("BSC")))  {
				    		 Fsource= "RNC";
				    		 filetype = "3G";
							 tech3 ="1";
							 vMODEL="RNC";
						 } else {
							 
				    	if ((filename1.contains("BSC")) && (filename2.contains("BSC")))  {
				    		 Fsource= "BSC";
				    		 filetype = "2G";
							 tech2 ="1";
							 vMODEL="BSC";
						 } else {
				    	
				    	 if (filename1.contains("_5G"))  {
				    		 Fsource= "5G";
				    		 filetype = "5G";
							 tech5 ="1";
							 vMODEL="5G";
						 } else {
							 if (filename1.contains("OSS"))  {
								 Fsource= "OSS";
								 filetype = "OSS";
								 vMODEL="OSS";
								 tech2 ="1";
							 } else { 
								 if ( (filename2.contains("eNodeB")))  {
									 Fsource= "eNodeB";
								 filetype = "4G";
								 vMODEL="eNodeB";
								 tech4 ="1";
								 }else {if ((filename1.contains("-MPT")))  {
									 Fsource= "MPT";
									 filetype = "MPT";
									 tech2 ="1";
									 tech3 ="1";
									 tech4 ="1";
									 vMODEL=Fsource;
						 				}else {
										 if ((filename2.contains("MICRO"))) {
											 Fsource= "MICRO";
											 if (filename2.contains("PICO")) {Fsource= "PICO";}
											 filetype = "3G-4G";
											 tech3 ="1";
											 tech4 ="1";
											 vMODEL=Fsource;
										 }else {
											if  ((filename2.contains("NodeB")) ) {
												Fsource="NodeB";
												filetype = "3G";
												vMODEL=Fsource;
												tech3 ="1"; 
											} //
											else { if ( (filename1.contains("_Easymacro")) || (filename1.contains("EasyMacro")) ||(filename1.contains("_EM")) ) {
												if (filename1.contains("_Easymacro")) {Fsource= "Easymacro";}
												if (filename1.contains("EasyMacro")) {Fsource= "Easymacro";}
												if (filename1.contains("_EM")) {Fsource= "Easymacro";}
												filetype = "4G";
												vMODEL=Fsource;
												tech4 ="1";
											}else {
												Fsource= "GSM";
												filetype = "2G";
												tech2 ="1";
												vMODEL=Fsource;
												}
											}
										 }
						 			 }
								 }
							 }
						 }
	        		}
	 			}
						 System.out.println(filetype);
						 System.out.println(tech2);
						 System.out.println(tech3);
						 System.out.println(tech4);
						 System.out.println(tech5);
					

					
					 /// get seq id of nodeactive and fillin table NODE_ACTIVE
					 codeid= localgetseqNbr(0);
					 codeid=Gyear+"_"+ "NODE"+'_'+codeid;
 					 PreparedStatement stmt = con.prepareStatement("insert into NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME  )"
 					 		+ "values('" + codeid +"', '"+ Gprovider +"' ||'_'||'" + ResultNode[0] +"' ,'" + ResultNode[0] +"' ,'" + ResultNode[1] +"','" + ResultNode[2] +"','Mobile Access Domain','" + Fsource +"','" + vMODEL +"','" + tech2 +"','" + tech3 +"','" + tech4 +"','" + tech5 +"','0','"+ circleid +"',sysdate,sysdate,'AIM','" + filename +"','0','0','0','0','0','0','1','1','0','"+ Gprovider +"','0','0','0') "); 
	                 stmt.executeUpdate();
				     stmt.close();
					
					//logger.info("Node ID is  : " + ResultNode[0] +" ; " +"Node Name is  : " + ResultNode[1] +" ; " +"Node Type is  : " + ResultNode[2]);
					
					File fXmlFile = new File(projpath+"/"+vfolderfrom+"/"+filename);
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fXmlFile);   
					doc.getDocumentElement().normalize();
					
					//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					
					//Log root name
					//logger.info("Root element : " + doc.getDocumentElement().getNodeName());
									
					NodeList nList = doc.getElementsByTagName("TABLE");

					//System.out.println("----------------------------");

					for (int temp = 0; temp < nList.getLength(); temp++) {
						totrow=0;
						Node nNode = nList.item(temp);

						//System.out.println("\nCurrent Element :" + nNode.getNodeName());

						if (nNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) nNode;
						
							//System.out.println(" \n  TABLE attrname : " + eElement.getAttribute("attrname"));
							totrow=eElement.getElementsByTagName("ROW").getLength();
							//System.out.println("number of ROWS in  "+eElement.getAttribute("attrname") +"  is : "  + totrow + "\n");
							logger.info("number of ROWS in  " + filename + "/  " +eElement.getAttribute("attrname") +"  is : "  + totrow);

							
							/// get Table atribute name based on attrname
							attributeTable=getattributetablename(eElement.getAttribute("attrname"));
							

							/// get seq id of nodeactiveattribute  and fillin table NODE_ACTIVE_ATTRIBUTE
		 					 String codeidattr= localgetseqNbr(1);
		 					 codeidattr=Gyear+"_"+ "ATTRIBUTE"+'_'+codeidattr;
		 					 PreparedStatement stmta = con.prepareStatement("insert into NODE_ACTIVE_ATTRIBUTE (NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR) "
		 					 		+ "values('" + codeidattr +"' ,'" + eElement.getAttribute("attrname") +"','" + attributeTable +"','" + codeid  +"', '" + filetype  +"',sysdate,'" + filename +"','0','0','0','0','0','0','1','" + temp +"', 'Mobile Access Domain','" + Gprovider +"') "); 
		 	                 stmta.executeUpdate();
		 				     stmta.close();
							
							
		 				    ArrayList<String> varaaaray = null;
		 				    String vcodeid=null;
							String strCurrentLine;
							objReader = new BufferedReader(new FileReader(projpath+"/"+vfolderfrom+"/"+filename));
							while ((strCurrentLine = objReader.readLine()) != null) {
								String data = strCurrentLine;
								String str1="TABLE attrname=\""+eElement.getAttribute("attrname")+"\"";
	
							if (data.contains(str1)) {	
								data = objReader.readLine();
				                  
				                  for (int j=0; j< totrow;j++) {
				                	  data = objReader.readLine();
				                	  if (attributeTable == "NODE_RACK") {
					                		   vhmap=getmapcolumnsrack(data);
					                	  }
				                	  if (attributeTable == "NODE_FRAME") {
				                		   vhmap=getmapcolumnsframe(data);
				                	  }
				                	  if (attributeTable == "NODE_SLOT") {
				                		   vhmap=getmapcolumnsslot(data);
				                	  }
				                	  if (attributeTable == "NODE_BOARD") {
				                		   vhmap=getmapcolumnsboard(data);
				                	  }
				                	  if (attributeTable == "NODE_PORT") {
				                		   vhmap=getmapcolumnsport(data);
				                	  }
				                	  if (attributeTable == "NODE_HOSTVER") {
				                		   vhmap=getmapcolumnshostver(data);
				                	  } 
				                	  if (attributeTable == "NODE_CABINET") {
				                		   vhmap=getmapcolumnscabinet(data);
				                	  }
				                	  if (attributeTable == "NODE_ACCESSORY") {
				                		   vhmap=getmapcolumnsaccessory(data);
				                	  }
				                	  if (attributeTable == "NODE_HOST") {
				                		   vhmap=getmapcolumnshost(data);
				                	  }
				                	  if (attributeTable == "NODE_SUBRACK") {
				                		   vhmap=getmapcolumnssubrack(data);
				                	  } 
				                	  if (attributeTable == "NODE_GCELL") {
				                		   vhmap=getmapcolumnsgcell(data);
				                	  } 
				                	  if (attributeTable == "NODE_BTS") {
				                		   vhmap=getmapcolumnsbts(data);
				                	  } 
				                	  if (attributeTable == "NODE_UCELL") {
				                		   vhmap=getmapcolumnsucell(data);
				                	  } 
				                	  if (attributeTable == "NODE_ANTENNA") {
				                		   vhmap=getmapcolumnsantenna(data);
				                	  } 
				                	  if (attributeTable == "NODE_LCELL") {
				                		   vhmap=getmapcolumnslcell(data);
				                	  } 
				                	  if (attributeTable == "NODE_RRN") {
				                		   vhmap=getmapcolumnsrrn(data);
				                	  } 
				                	  if (attributeTable == "NODE_ENODEBCELL") {
				                		   vhmap=getmapcolumnsenodebcell(data);
				                	  } 
				                	  if (attributeTable == "NODE_NODEBCELL") {
				                		   vhmap=getmapcolumnsnodebcell(data);
				                	  } 
				                	  if (attributeTable == "NODE_NBInterfaces") {
				                		   vhmap=getmapcolumnsnbinterfaces(data);
				                	  } 
				                	  				                	  
				                	  //fill in ttribute data into appropriate tabel fetched by table name under parameter attributeTable
				                	   String InsertQuery = null;
				                	   if (attributeTable =="NODE_RACK" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) {vhmap.put("DATEOFMANUFACTURE", todaydate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todaydate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid= localgetseqNbr(2);  /// 2 to select rack_id 
				                		   vcodeid=Gyear+"_"+ "RACK"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  + " (RACK_ID,RACKNO,INVENTORYUNITID,RACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("RACKNO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("RACKTYPE") +"','" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"','" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("MODEL") +"','" + vhmap.get("USERLABEL") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"' ,'"+ j +"','1','Mobile Access Domain','" + Gprovider +"' ) ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_FRAME" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) {vhmap.put("DATEOFMANUFACTURE", todaydate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todaydate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid= localgetseqNbr(3);  /// 3 to select frame_id 
				                		   vcodeid=Gyear+"_"+ "FRAME"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  +" (FRAME_ID,RACKNO,FRAMENO,INVENTORYUNITID,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("RACKNO") +"','" + vhmap.get("FRAMENO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("FRAMETYPE") +"','" + vhmap.get("RACKFRAMENO") +"','" + vhmap.get("MODULENO") +"','" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"', '" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"','" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("MODEL") +"','" + vhmap.get("USERLABEL") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"' ,'0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_SLOT" ) {
				                		   almposition ="0";
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todaydate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todaydate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid= localgetseqNbr(4);  /// 4 to select slot_id 
				                		   vcodeid=Gyear+"_"+ "SLOT"+'_'+vcodeid;
				                		   almposition =vhmap.get("CABINETNO") +'/'+vhmap.get("SUBRACKNO")+'/'+ vhmap.get("RACKNO")+'/'+ vhmap.get("SLOTNO")+'/'+ vhmap.get("SLOTPOS");
				                		   InsertQuery="insert into " + attributeTable  +" (SLOT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("CABINETNO") +"','" + vhmap.get("SUBRACKNO") +"','" + vhmap.get("RACKNO") +"','" + vhmap.get("FRAMENO") +"','" + vhmap.get("SLOTNO") +"','" + vhmap.get("SLOTPOS") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"', '" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"', '" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"' ,'" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','"+ almposition +"','Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_BOARD" ) {   
				                		   almposition ="0";
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todaydate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todaydate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid= localgetseqNbr(5);  /// 5 to select Board_id 
				                		   vcodeid=Gyear+"_"+ "BOARD"+'_'+vcodeid;
				                		   almposition =vhmap.get("CABINETNO") +'/'+vhmap.get("SUBRACKNO")+'/'+ vhmap.get("SLOTNO")+'/'+ vhmap.get("SLOTPOS")+'/'+ vhmap.get("SUBSLOTNO");
				                		   InsertQuery="insert into " + attributeTable  +"(BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,EXTINFO,APDEVINFO,WORKMODE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("CABINETNO") +"','" + vhmap.get("SUBRACKNO") +"','" + vhmap.get("RACKNO") +"','" + vhmap.get("FRAMENO") +"','" + vhmap.get("SLOTNO") +"','" + vhmap.get("SLOTPOS") +"','" + vhmap.get("SUBSLOTNO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("MODULENO") +"','" + vhmap.get("BOARDNAME") +"','" + vhmap.get("BOARDTYPE") +"','" + vhmap.get("INVENTORYUNITTYPE") +"', '" + vhmap.get("VENDORUNITFAMILYTYPE") +"', '" + vhmap.get("VENDORUNITTYPENUMBER") +"', '" + vhmap.get("VENDORNAME") +"', '" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"','" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("SOFTVER") +"','" + vhmap.get("LOGICVER") +"','" + vhmap.get("BIOSVER") +"','" + vhmap.get("BIOSVEREX") +"','" + vhmap.get("LANVER") +"','" + vhmap.get("MBUSVER") +"','" + vhmap.get("ISSUENUMBER") +"','" + vhmap.get("BOMCODE") +"','" + vhmap.get("MODEL") +"','" + vhmap.get("USERLABEL") +"','" + vhmap.get("EXTINFO") +"','" + vhmap.get("APDEVINFO") +"','" + vhmap.get("WORKMODE") +"','" + codeid +"','" + codeidattr +"' ,sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','"+ almposition +"',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_PORT" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todaydate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todaydate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid= localgetseqNbr(6);  /// 6 to select Port_id 
				                		   vcodeid=Gyear+"_"+ "PORT"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  +" (PORT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,VENDORUNITFAMILYTYPE,INVENTORYUNITID,PORTNO,HARDWAREVERSION,SERIALNUMBER,INVENTORYUNITTYPE,VENDORNAME,VENDORUNITTYPENUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MACADDR,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("CABINETNO") +"','" + vhmap.get("SUBRACKNO") +"','" + vhmap.get("RACKNO") +"','" + vhmap.get("FRAMENO") +"','" + vhmap.get("SLOTNO") +"','" + vhmap.get("SLOTPOS") +"','" + vhmap.get("SUBSLOTNO") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("PORTNO") +"','" + vhmap.get("HARDWAREVERSION") +"','" + vhmap.get("SERIALNUMBER") +"', '" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("VENDORNAME") +"', '" + vhmap.get("VENDORUNITTYPENUMBER") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"','" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MACADDR") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + codeid +"','" + codeidattr +"' ,sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_CABINET" ) { 
				                		   almposition ="0";
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todaydate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todaydate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
					                		   vcodeid= localgetseqNbr(7);  /// 7 to select cabinet_id 
					                		   vcodeid=Gyear+"_"+ "CABINET"+'_'+vcodeid;
					                		   almposition =vhmap.get("CABINETNO");
					                		   InsertQuery="insert into " + attributeTable  + " (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
					                		   		+ " values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("CABINETNO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("RACKTYPE") +"','" + vhmap.get("BOMRACKTYPE") +"','" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"', '" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("ISSUENUMBER") +"','" + vhmap.get("BOMCODE") +"','" + vhmap.get("EXTINFO") +"','" + vhmap.get("MODEL") +"','" + vhmap.get("USERLABEL") +"','" + vhmap.get("SHAREMODE") +"','" + vhmap.get("CLEICODE") +"','" + vhmap.get("BOM") +"','" + codeid +"','" + codeidattr +"',sysdate ,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','"+ almposition +"',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_HOSTVER" ) {
				                		   vcodeid= localgetseqNbr(8);  /// 8 to select hostVer_id
				                		   vcodeid=Gyear+"_"+ "HOSTVER"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  +" (HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR)  values('" + vcodeid +"','" + vhmap.get("HOSTVERTYPE") +"','"+ vhmap.get("HOSTVER") +"','"+ vhmap.get("SDESC") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"' ,'0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_ACCESSORY" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todaydate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todaydate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid= localgetseqNbr(9);  /// 9 to select accessory_id
				                		   vcodeid=Gyear+"_"+ "ACCESSORY"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  +" (ACCESSORY_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,ACCESSORYTYPE,ADDTIONALINFORMATION,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("RACKPOSITION") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"','" + vhmap.get("SOFTVER") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"', '" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("ACCESSORYTYPE") +"','" + vhmap.get("ADDTIONALINFORMATION") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"' ,'" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_HOST" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todaydate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todaydate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid= localgetseqNbr(10);  /// 10 to select Host_id
				                		   vcodeid=Gyear+"_"+ "HOST"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  + " (HOST_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,HOSTNAME,NUMBEROFCPU,MEMSIZE,HARDDISKSIZE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ " values('" + vcodeid +"','" + vhmap.get("RACKPOSITION") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"','" + vhmap.get("SOFTVER") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"', '" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("HOSTNAME") +"','" + vhmap.get("NUMBEROFCPU") +"','" + vhmap.get("MEMSIZE") +"','" + vhmap.get("HARDDISKSIZE") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"' ,'0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_SUBRACK" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todaydate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todaydate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid= localgetseqNbr(11);  /// 11 to select subrack_id  
				                		   vcodeid=Gyear+"_"+ "SUBRACK"+'_'+vcodeid;
			                			   InsertQuery="insert into " + attributeTable  +" (SUBRACK_ID,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
			                			   		+ "values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("CABINETNO") +"','" + vhmap.get("SUBRACKNO") +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("RACKTYPE") +"','" + vhmap.get("BOMRACKTYPE") +"','" + vhmap.get("FRAMETYPE") +"','" + vhmap.get("RACKFRAMENO") +"','" + vhmap.get("MODULENO") +"','" + vhmap.get("INVENTORYUNITTYPE") +"', '" + vhmap.get("VENDORUNITFAMILYTYPE") +"', '" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"','" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("USERLABEL") +"','" + vhmap.get("BOMCODE") +"','" + vhmap.get("MODEL") +"','" + vhmap.get("ISSUENUMBER") +"','" + vhmap.get("BOMFRAMETYPE") +"','" + vhmap.get("CLEICODE") +"','" + vhmap.get("BOM") +"','" + vhmap.get("EXTINFO") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"' ,'"+ j +"','1','Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                		   }
				                	   if (attributeTable =="NODE_GCELL" ) {
				                		   vcodeid= localgetseqNbr(12);  /// 12 to select Gcell_id
				                		   vcodeid=Gyear+"_"+ "GCELL"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  +" (GCELL_ID,CELLID,CELLNAME,MCC,MNC,LAC,CI,NCC,BCC,TYPE,BCCHNO,BASEBANDPOLICY,BASEBANDEQMID,GBTSFUNCTIONNAME,GLOCELLID,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("CELLID") +"','" + vhmap.get("CELLNAME") +"','" + vhmap.get("MCC") +"','" + vhmap.get("MNC") +"','" + vhmap.get("LAC") +"','" + vhmap.get("CI") +"','" + vhmap.get("NCC") +"','" + vhmap.get("BCC") +"','" + vhmap.get("TYPE") +"','" + vhmap.get("BCCHNO") +"','" + vhmap.get("BASEBANDPOLICY") +"','" + vhmap.get("BASEBANDEQMID") +"','" + vhmap.get("GBTSFUNCTIONNAME") +"','" + vhmap.get("GLOCELLID") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_BTS" ) {
				                		   vcodeid= localgetseqNbr(13);  /// 13 to select BTS_id
				                		   vcodeid=Gyear+"_"+ "BTS"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  +" (BTS_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("SITENAME") +"','" + vhmap.get("SITETYPE") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"' ,'"+ j +"','1','Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_UCELL" ) {
				                		   vcodeid= localgetseqNbr(14);  /// 14 to select Ucell_id
				                		   vcodeid=Gyear+"_"+ "UCELL"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  +"(UCELL_ID,CELLID,CELLNAME,LOCELL,NODEBFUNCTIONNAME,ULFREQ,DLFREQ,MAXPOWER,USERLABEL,MAXTXPOWER,UARFCNUPLINK,UARFCNDOWNLINK,PSCRAMBCODE,NODEBNAME,LAC,SAC,RAC,MANUFACTURERDATA,RADIUS,HORAD,DI,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("CELLID") +"','" + vhmap.get("CELLNAME") +"','" + vhmap.get("LOCELL") +"','" + vhmap.get("NODEBFUNCTIONNAME") +"','" + vhmap.get("ULFREQ") +"','" + vhmap.get("DLFREQ") +"','" + vhmap.get("MAXPOWER") +"','" + vhmap.get("USERLABEL") +"','" + vhmap.get("MAXTXPOWER") +"','" + vhmap.get("UARFCNUPLINK") +"','" + vhmap.get("UARFCNDOWNLINK") +"','" + vhmap.get("PSCRAMBCODE") +"','" + vhmap.get("NODEBNAME") +"','" + vhmap.get("LAC") +"','" + vhmap.get("SAC") +"','" + vhmap.get("RAC") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("RADIUS") +"','" + vhmap.get("HORAD") +"','" + vhmap.get("DI") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"' ,'0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_ANTENNA" ) {
				                		   if (vhmap.get("DATEOFMANUFACTURE").length()== 1) { vhmap.put("DATEOFMANUFACTURE", todaydate);} else {String res = createDate(vhmap.get("DATEOFMANUFACTURE")); vhmap.put("DATEOFMANUFACTURE", res);}
				                		   if (vhmap.get("DATEOFLASTSERVICE").length()== 1) {vhmap.put("DATEOFLASTSERVICE", todaydate);} else {String res = createDate(vhmap.get("DATEOFLASTSERVICE")); vhmap.put("DATEOFLASTSERVICE", res);}
				                		   vcodeid= localgetseqNbr(15);  /// 15 to select Antena_id 
				                		   vcodeid=Gyear+"_"+ "ANTENNA"+'_'+vcodeid;
				                			   InsertQuery="insert into " + attributeTable  +" (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR) "
				                			   		+ "values('" + vcodeid +"','" + vhmap.get("INVENTORYUNITID") +"','" + vhmap.get("INVENTORYUNITTYPE") +"','" + vhmap.get("ANTENNADEVICENO") +"','" + vhmap.get("PRODNR") +"','" + vhmap.get("VENDORUNITFAMILYTYPE") +"','" + vhmap.get("VENDORUNITTYPENUMBER") +"','" + vhmap.get("VENDORNAME") +"','" + vhmap.get("SERIALNUMBER") +"','" + vhmap.get("HARDWAREVERSION") +"',DATE '" + vhmap.get("DATEOFMANUFACTURE") +"',DATE '" + vhmap.get("DATEOFLASTSERVICE") +"','" + vhmap.get("UNITPOSITION") +"','" + vhmap.get("MANUFACTURERDATA") +"','" + vhmap.get("ANTENNADEVICETYPE") +"','" + vhmap.get("ISSUENUMBER") +"','" + vhmap.get("BOMCODE") +"','" + vhmap.get("EXTINFO") +"','" + vhmap.get("MODEL") +"','" + vhmap.get("SERIALNUMBEREX") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"' ,'0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_LCELL" ) {
				                		   vcodeid= localgetseqNbr(16);  /// 16 to select LCELL_id 
				                		   vcodeid=Gyear+"_"+ "LCELL"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  +" (LCELL_ID,LOCALCELLID,CELLNAME,CELLRADIUS,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,ENODEBFUNCTIONNAME,NBCELLFLAG,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("LOCALCELLID") +"','" + vhmap.get("CELLNAME") +"','" + vhmap.get("CELLRADIUS") +"','" + vhmap.get("FREQBAND") +"','" + vhmap.get("ULEARFCNCFGIND") +"','" + vhmap.get("ULEARFCN") +"','" + vhmap.get("DLEARFCN") +"','" + vhmap.get("ULBANDWIDTH") +"','" + vhmap.get("DLBANDWIDTH") +"','" + vhmap.get("CELLID") +"','" + vhmap.get("PHYCELLID") +"','" + vhmap.get("FDDTDDIND") +"','" + vhmap.get("ENODEBFUNCTIONNAME") +"','" + vhmap.get("NBCELLFLAG") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1',sysdate,'Mobile Access Domain','" + Gprovider +"' ) ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_RRN" ) {
				                		   vcodeid= localgetseqNbr(17);  /// 17 to select RNN_id
				                		   vcodeid=Gyear+"_"+ "RRN"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  +" (RRN_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
					                		   		+ "values('" + vcodeid +"','" + vhmap.get("SITEINDEX") +"','" + vhmap.get("SITENAME") +"','" + vhmap.get("SITETYPE") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"' ,'" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_ENODEBCELL" ) {
				                		   vcodeid= localgetseqNbr(18);  /// 18 to select ENODEBCELL_id 
				                		   vcodeid=Gyear+"_"+ "ENODEBCELL"+'_'+vcodeid;
		                						InsertQuery="insert into " + attributeTable  +"(ENODEBCELL_ID,LOCALCELLID,CELLNAME,SECTORID,CSGIND,CYCLEPREFIX,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,TAC,ADDITIONALSPECTRUMEMISSION,NBCELLFLAG,ENODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR )"
		                								+ "values('" + vcodeid +"','" + vhmap.get("LOCALCELLID") +"','" + vhmap.get("CELLNAME") +"','" + vhmap.get("SECTORID") +"','" + vhmap.get("CSGIND") +"','" + vhmap.get("CYCLEPREFIX") +"','" + vhmap.get("FREQBAND") +"','" + vhmap.get("ULEARFCNCFGIND") +"','" + vhmap.get("ULEARFCN") +"','" + vhmap.get("DLEARFCN") +"','" + vhmap.get("ULBANDWIDTH") +"','" + vhmap.get("DLBANDWIDTH") +"','" + vhmap.get("CELLID") +"','" + vhmap.get("PHYCELLID") +"','" + vhmap.get("FDDTDDIND") +"','" + vhmap.get("TAC") +"','" + vhmap.get("ADDITIONALSPECTRUMEMISSION") +"','" + vhmap.get("NBCELLFLAG") +"','" + vhmap.get("ENODEBFUNCTIONNAME") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_NODEBCELL" ) {
				                		   vcodeid= localgetseqNbr(19);  /// 19 to select NODEBCELL_id
				                		   vcodeid=Gyear+"_"+ "NODEBCELL"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  +" (NODEBCELL_ID,LOCELL,USERLABEL,SITENO,SECNO,RADIUS,HORAD,BBPOOLTYPE,ULGROUPNO,CABINETNO1,SUBRACKNO1,SLOTNO1,CABINETNO2,SUBRACKNO2,SLOTNO2,ULFREQ,DLFREQ,MAXPOWER,HSDPA,DI,HIGHSPEEDMODE,SPEEDRATE,NODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				                		   		+ "values('" + vcodeid +"','" + vhmap.get("LOCELL") +"','" + vhmap.get("USERLABEL") +"','" + vhmap.get("SITENO") +"','" + vhmap.get("SECNO") +"','" + vhmap.get("RADIUS") +"','" + vhmap.get("HORAD") +"','" + vhmap.get("BBPOOLTYPE") +"','" + vhmap.get("ULGROUPNO") +"','" + vhmap.get("CABINETNO1") +"','" + vhmap.get("SUBRACKNO1") +"','" + vhmap.get("SLOTNO1") +"','" + vhmap.get("CABINETNO2") +"','" + vhmap.get("SUBRACKNO2") +"','" + vhmap.get("SLOTNO2") +"','" + vhmap.get("ULFREQ") +"','" + vhmap.get("DLFREQ") +"','" + vhmap.get("MAXPOWER") +"','" + vhmap.get("HSDPA") +"','" + vhmap.get("DI") +"','" + vhmap.get("HIGHSPEEDMODE") +"','" + vhmap.get("SPEEDRATE") +"','" + vhmap.get("NODEBFUNCTIONNAME") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"' ,'0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','Mobile Access Domain','" + Gprovider +"') ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (attributeTable =="NODE_NBInterfaces" ) {
				                		   vcodeid= localgetseqNbr(17);  /// 17 to select RNN_id
				                		   vcodeid=Gyear+"_"+ "NBInterfaces"+'_'+vcodeid;
				                		   InsertQuery="insert into " + attributeTable  +" (NBINTERFACE_ID,INTERFACETYPE,VERSION,ISUSED,NMSVENDOR,NMSNAME,REMARK,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
					                		   		+ "values('" + vcodeid +"','" + vhmap.get("INTERFACETYPE") +"','" + vhmap.get("VERSION") +"','" + vhmap.get("ISUSED") +"','" + vhmap.get("NMSVENDOR") +"','" + vhmap.get("NMSNAME") +"','" + vhmap.get("REMARK") +"','" + codeid +"','" + codeidattr +"',sysdate,'" + filename +"','" + vhmap.get("STATUS") +"','0','0','0','" + vhmap.get("TRANS_ID") +"','" + vhmap.get("TRANS_TYPE") +"','"+ j +"','1','Mobile Access Domain','" + Gprovider +"' ) ";
				                		   //System.out.println(filename + "    "+ InsertQuery);
				                	   }
				                	   if (InsertQuery != null ) {
					                	  PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
					                	  stmtattr.executeUpdate();
					                	  stmtattr.close();
				                	   }
				                	  System.out.println("\n");
				                  }  
				                      //System.out.println("number of PARAMETERS in "  +filename + " / " + eElement.getAttribute("attrname")+ " is : " + vhmap.size());
				                      //logger.info("number of PARAMETERS in "  +filename + " / " + eElement.getAttribute("attrname")+ " is : " + varaaaray.size());
				                      SumRow = SumRow + totrow ;
				                      System.out.println("totrow " + totrow);
				                      System.out.println("SumRow " + SumRow);
				                      //break;
				              }
							}

			             }
					}
				
					totSumRow=totSumRow+SumRow;
					System.out.println("totSumRow " + totSumRow);
					objReader.close();	
					
					

				logger.info("Grand Total number of Rows in  " +filename + " is: " + SumRow);
				logger.info("SUM Grand Total number of Rows in all previous files  is: " + totSumRow);
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
	 
	 
	 public static HashMap getmapcolumnshostver(String str) {
		 System.out.println("first str " +str);
		 String[] pr =str.split("\\s+");
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 hmap.put("HOSTVERTYPE", "0");
		 hmap.put("HOSTVER", "0");
		 hmap.put("SDESC", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		// System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"HostVerType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HostVer")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"sDesc")) {hmap.put( val1.toUpperCase(), val2);}
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;

	 }
	 
	 public static HashMap getmapcolumnsrack(String str) {
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
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"RackNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"RackType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitFamilyType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitTypeNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SerialNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HardwareVersion")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfManufacture")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfLastService")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UnitPosition")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ManufacturerData")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Model")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UserLabel")) {hmap.put( val1.toUpperCase(), val2);}
	 
		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getmapcolumnsframe(String str) {
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

		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		 //System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"RackNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"FrameNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitId")) {hmap.put(val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"FrameType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"RackFrameNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ModuleNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitFamilyType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitTypeNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SerialNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HardwareVersion")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfManufacture")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfLastService")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UnitPosition")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ManufacturerData")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Model")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UserLabel")) {hmap.put( val1.toUpperCase(),val2);}
	 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
		 
		
		return hmap;
				
		
	 }
	 
	 
	 public static HashMap getmapcolumnsslot(String str) {
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
		 
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"SiteIndex")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CabinetNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SubrackNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"RackNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"FrameNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SlotNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SlotPos")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitFamilyType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitTypeNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorName")) {hmap.put( val1.toUpperCase(),val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SerialNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HardwareVersion")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfManufacture")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfLastService")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UnitPosition")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ManufacturerData")) {hmap.put( val1.toUpperCase(), val2);}
	 
		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getmapcolumnsboard(String str) {
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
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"SiteIndex")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CabinetNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SubrackNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"RackNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"FrameNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SlotNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SlotPos")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SubSlotNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ModuleNo")) {hmap.put( val1.toUpperCase(),val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BoardName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BoardType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitFamilyType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitTypeNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SerialNumber")) {hmap.put( val1.toUpperCase(),val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HardwareVersion")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfManufacture")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfLastService")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UnitPosition")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ManufacturerData")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SoftVer")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"LogicVer")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BiosVer")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BiosVerEx")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"LANVer")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"MBUSVer")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"IssueNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BOMCode")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Model")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UserLabel")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ExtInfo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ApDevInfo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"WorkMode")) {hmap.put( val1.toUpperCase(), val2);}
		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getmapcolumnsport(String str) {
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
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"SiteIndex")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CabinetNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SubrackNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"RackNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"FrameNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SlotNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SlotPos")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SubSlotNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitFamilyType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"PortNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HardwareVersion")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SerialNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitTypeNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfManufacture")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfLastService")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UnitPosition")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"MacAddr")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ManufacturerData")) {hmap.put( val1.toUpperCase(), val2);}
	 
		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getmapcolumnscabinet(String str) {
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
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"SiteIndex")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CabinetNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"RackType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitFamilyType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitTypeNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SerialNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HardwareVersion")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfManufacture")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfLastService")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UnitPosition")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ManufacturerData")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BOMCode")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ExtInfo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Model")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UserLabel")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"IssueNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BOMRackType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ShareMode")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Cleicode")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BOM")) {hmap.put( val1.toUpperCase(), val2);}
	 
		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getmapcolumnsaccessory(String str) {
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
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"RackPosition")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitFamilyType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitTypeNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SerialNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HardwareVersion")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SoftVer")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfManufacture")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfLastService")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ManufacturerData")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"AccessoryType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"AddtionalInformation")) {hmap.put( val1.toUpperCase(), val2);}
		 
	 
		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getmapcolumnshost(String str) {
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
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"RackPosition")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitFamilyType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitTypeNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SerialNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HardwareVersion")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SoftVer")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfManufacture")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfLastService")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ManufacturerData")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HostName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"NumberOfCpu")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"MemSize")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HardDiskSize")) {hmap.put( val1.toUpperCase(), val2);}
	 
		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getmapcolumnssubrack(String str) {
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
		 
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"SiteIndex")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CabinetNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"RackType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BOMRackType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitFamilyType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitTypeNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SerialNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HardwareVersion")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfManufacture")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfLastService")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UnitPosition")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ManufacturerData")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"IssueNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BOMCode")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ExtInfo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Model")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BOMFrameType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"RackFrameNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"FrameType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ModuleNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SubrackNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UserLabel")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Cleicode")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BOM")) {hmap.put( val1.toUpperCase(), val2);}

		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 
	 public static HashMap getmapcolumnsgcell(String str) {
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
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"CellId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CellName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"MCC")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"MNC")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"LAC")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CI")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"NCC")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BCC")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"TYPE")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BCCHNO")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"GloCellId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BaseBandPolicy")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BaseBandEqmId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"GbtsFunctionName")) {hmap.put( val1.toUpperCase(), val2);}
		 		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 
	 public static HashMap getmapcolumnsbts(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "SITEINDEX", "0");
		 hmap.put( "SITENAME", "0");
		 hmap.put( "SITETYPE", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"SiteIndex")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SiteName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SiteType")) {hmap.put( val1.toUpperCase(), val2);}
		 		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getmapcolumnsucell(String str) {
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
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"CellId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Locell")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Radius")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HoRad")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DI")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"NodeBFunctionName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UlFreq")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DlFreq")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"MaxPower")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Cellname")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"userlabel")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Maxtxpower")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"LAC")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SAC")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"RAC")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"NodeBname")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"MANUFACTURERDATA")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"PSCRAMBCODE")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UARFCNDOWNLINK")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UARFCNUPLINK")) {hmap.put( val1.toUpperCase(), val2);}
		 		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 
	 public static HashMap getmapcolumnsantenna(String str) {
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
		
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"InventoryUnitType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"AntennaDeviceNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ProdNr")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitFamilyType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorUnitTypeNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"VendorName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SerialNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HardwareVersion")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfManufacture")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DateOfLastService")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UnitPosition")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ManufacturerData")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"AntennaDeviceType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"IssueNumber")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BOMCode")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ExtInfo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Model")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SerialNumberEx")) {hmap.put( val1.toUpperCase(), val2);}
		 
		 		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 
	 public static HashMap getmapcolumnslcell(String str) {
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
		
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"LocalCellId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CellName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CellRadius")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"FreqBand")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UlEarfcnCfgInd")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UlEarfcn")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DlEarfcn")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UlBandWidth")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DlBandWidth")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CellId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"PhyCellId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"FddTddInd")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ENodeBFunctionName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"NbCellFlag")) {hmap.put( val1.toUpperCase(), val2);}
		 
		 		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getmapcolumnsrrn(String str) {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 
		 hmap.put( "SITEINDEX", "0");
		 hmap.put( "SITENAME", "0");
		 hmap.put( "SITETYPE", "0");
		 hmap.put("STATUS", "0");
		 hmap.put("TRANS_TYPE", "0");
		 hmap.put("TRANS_ID", "0");
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"SiteIndex")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SiteName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SiteType")) {hmap.put( val1.toUpperCase(), val2);}
		 		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getmapcolumnsenodebcell(String str) {
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
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"LocalCellId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CellName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SectorId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CsgInd")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CyclePrefix")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"FreqBand")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UlEarfcnCfgInd")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UlEarfcn")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DlEarfcn")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UlBandWidth")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DlBandWidth")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CellId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"PhyCellId")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"FddTddInd")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"TAC")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"AdditionalSpectrumEmission")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"NbCellFlag")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"ENodeBFunctionName")) {hmap.put( val1.toUpperCase(), val2);}
		 		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static HashMap getmapcolumnsnodebcell(String str) {
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
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"Locell")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UserLabel")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SiteNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SecNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Radius")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HoRad")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"BbPoolType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UlgroupNo")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CabinetNo1")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SubRackNo1")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SlotNo1")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"CabinetNo2")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SubRackNo2")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SlotNo2")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"UlFreq")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DlFreq")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"MaxPower")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Hsdpa")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"DI")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"HighSpeedMode")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"SpeedRate")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"NodeBFunctionName")) {hmap.put( val1.toUpperCase(), val2);}
		 
		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 
	 
	 public static HashMap getmapcolumnsnbinterfaces(String str) {
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
		 
		 String col1 = str;
		 String col2 = null;
		 int n = col1.indexOf("<ROW");
		 col1=col1.substring(n+5, col1.length());
		// System.out.println("first space " +col1);
		 
		 
		 String val2=null;
		 n = col1.indexOf("=\"");
		 while (n != -1) {
		 String val1 = col1.substring(0, n).trim();
		 col2=col1.substring(n+2, col1.length());
		 //System.out.println("col2 "+ col2);
		 n = col2.indexOf("\"");
		 if  (n == 0) {val2 = "0";} else {val2 = col2.substring(0, n).trim();}
		 //System.out.println("val1 " +val1  + "   val2: " + val2);
		 
		 if (StringUtils.equalsIgnoreCase (val1,"InterfaceType")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Version")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"IsUsed")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"NMSVendor")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"NMSName")) {hmap.put( val1.toUpperCase(), val2);}
		 if (StringUtils.equalsIgnoreCase (val1,"Remark")) {hmap.put( val1.toUpperCase(), val2);}
		 
		 
		 
		 col1=col2.substring(n+1, col2.length());
		 n = col1.indexOf("=\"");
		 }
 
		return hmap;
				
		
	 }
	 
	 public static String[] getnodeidnametype(String RowLine) throws IOException {
		 String [] result = null;
		 String [] vari;
		 String [] vari2;
		 String vdata = null;
		 String vbts =null;
		 String Vname =null;
		 result =new String[4];
		 
		 Vname=RowLine;
		 result[3]="0";
		 
		// NE=263,BTS=40" found in AIM_GSMBTS_HQB BSC2 to get Node ID
		 int n = Vname.indexOf("BTS=");
		 if (n > -1) { Vname=Vname.substring(n+4, Vname.length());
		 //System.out.println("my first one is " +Vname);
		 } else {
			 // NE= found in AIM files to get Node ID
			 n = Vname.indexOf("NE=");
			 // OS= found in OSS files  to get node ID
			 if (n == -1) { n = Vname.indexOf("OS=");}
			 Vname=Vname.substring(n+3, Vname.length());
			// System.out.println("my second one is " +Vname);
		 }
		  n = Vname.indexOf("\"");
		  result[0]=Vname.substring(0, n);
		 // System.out.println(result[0]);
		 // System.out.println("node_id is   "+result[0]);
		  
		   n = Vname.indexOf("NEName=\"");
		   Vname=Vname.substring(n+8, Vname.length());
		   //System.out.println(Vname);
		   n = Vname.indexOf("\"");
			  result[1]=Vname.substring(0, n);
			 // System.out.println(result[1]);
			  
			  
			  n = Vname.indexOf("NEType=\"");
			   Vname=Vname.substring(n+8, Vname.length());
			  // System.out.println(Vname);
			   n = Vname.indexOf("\"");
				  result[2]=Vname.substring(0, n);
				 // System.out.println(result[2]);
		
		   
			

		 return result;
	 }

	
	 public static String getattributetablename(String str) {
		 
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
		 String vyear=null;
		 String vmonth =null;
		 String vday=null;

		 if (str.contains("/")) {
			 vstr=str;
			 int n = vstr.indexOf("/");
			 vyear=vstr.substring(0, n);
			 vstr=vstr.substring(n+1, vstr.length());
			 n = vstr.indexOf("/");
			 vmonth=vstr.substring(0, n);
			 vday=vstr.substring((n+1), (n+3)).trim();
			 res=vyear + "-"+ vmonth +"-"+ vday;
			 //System.out.println("HERE WE GO NEW DATE " + res);
		 } else {
				 if (str.contains("-")) {
					 //if (str.length() >8) {res=str.substring(0, 10);}
					// else {res=str.substring(0, 8);}
					 vstr=str;
					 int n = vstr.indexOf("-");
					 vyear=vstr.substring(0, n);
					 vstr=vstr.substring(n+1, vstr.length());
					 n = vstr.indexOf("-");
					 vmonth=vstr.substring(0, n);
					 if (vmonth.length() <2) {vmonth= "0"+ vmonth;}
					 
					 //System.out.println("n+3 " + (n+3));
					 //System.out.println("vstr.length " + vstr.length());
					 if ((n+3) > vstr.length()) {vday=vstr.substring((n+1), (n+2)).trim();vday= "0"+ vday;}
					 else {vday=vstr.substring((n+1), (n+3)).trim();}
					 if (vday.length() <2) {vday= "0"+ vday;}
					 res=vyear + "-"+ vmonth +"-"+ vday;
					 //System.out.println("HERE WE GO NEW DATE " + res);
				 } else
					 {
						 res= "20"+str.substring(0, 2) + "-"+ str.substring(2, 4)+ "-"+ str.substring(4, 6);
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
	 
 
 private static void RunCopydata() throws InterruptedException, SQLException, ParseException {
	 Statement stmtp=null;
	 PreparedStatement stmt0;
	 PreparedStatement stmt1;
	 PreparedStatement stmt2;
	 String dat3;
	 String dat4;
	 try {
	 //Insert into TEMP_NODE_ACTIVE
	 String queryp = "select * from NODE_ACTIVE"; 
	 stmtp = con.createStatement();
     ResultSet rsp = stmtp.executeQuery(queryp);
	 while (rsp.next()) {
		 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME  ) "
		 		+ "values ('" + rsp.getString("NODE_PK") +"','" + rsp.getString("UNIQUE_NODE_ID") +"','" + rsp.getString("NODE_ID") +"','" + rsp.getString("NODE_NAME") +"','" + rsp.getString("NODE_TYPE") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("NODE_SOURCE") +"','" + rsp.getString("NODE_MODEL") +"','" + rsp.getString("TECH_2G") +"','" + rsp.getString("TECH_3G") +"','" + rsp.getString("TECH_4G") +"','" + rsp.getString("TECH_5G") +"','" + rsp.getString("SITE_ID") +"','" + rsp.getString("CIRCLE_ID") +"', TIMESTAMP '" + rsp.getString("CREATION_DATE") +"', TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILE_TYPE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("LINE") +"','" + rsp.getString("WARE_ID") +"' ,'" + rsp.getString("VENDOR") +"','" + rsp.getString("SUPPLIER_ID") +"','" + rsp.getString("WARE_NAME") +"','" + rsp.getString("SUPPLIER_NAME") +"')");
	     stmt0.executeUpdate();
	     stmt0.close();
     }
	 rsp.close();
	 stmtp.close();
	 
	 	//Insert into TEMP_NODE_ACTIVE_ATTRIBUTE
		 queryp = "select * from NODE_ACTIVE_ATTRIBUTE"; 
		 stmtp = con.createStatement();
	     rsp = stmtp.executeQuery(queryp);
		 while (rsp.next()) {
			 stmt1 = conalm.prepareStatement("insert into TEMP_NODE_ACTIVE_ATTRIBUTE (NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR)"
			 		+ "values ('" + rsp.getString("NODE_ATTR_PK") +"','" + rsp.getString("ATTRIBUTE") +"','" + rsp.getString("ATTRIBUTE_TABLE") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_TYPE") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("LINE") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
		     stmt1.executeUpdate();
		     stmt1.close();
	     }
		 rsp.close();
		 stmtp.close();
	 
        
		 //Insert into TEMP_NODE_RACK
		 queryp = "select * from NODE_RACK"; 
		 stmtp = con.createStatement();
	     rsp = stmtp.executeQuery(queryp);
		 while (rsp.next()) {
			 dat3=rsp.getString("DATEOFMANUFACTURE");
			 dat3 = dat3.substring(0, 10) ;
			 dat4=rsp.getString("DATEOFLASTSERVICE");
			 dat4 = dat4.substring(0, 10) ;
			 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_RACK (RACK_ID,RACKNO,INVENTORYUNITID,RACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
			 		+ "values ('" + rsp.getString("RACK_ID") +"','" + rsp.getString("RACKNO") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("RACKTYPE") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION") +"',DATE '" + dat3 +"',DATE '" + dat4 +"','" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("MODEL") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"' ,'" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
			 stmt2.executeUpdate();
			 stmt2.close();
	     }
		 rsp.close();
		 stmtp.close();

        
        //Insert into TEMP_NODE_CABINET
		 queryp = "select * from NODE_CABINET"; 
		 stmtp = con.createStatement();
	     rsp = stmtp.executeQuery(queryp);
		 while (rsp.next()) {
			 dat3=rsp.getString("DATEOFMANUFACTURE");
			 dat3 = dat3.substring(0, 10) ;
			 dat4=rsp.getString("DATEOFLASTSERVICE");
			 dat4 = dat4.substring(0, 10) ;
			 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
			 		+ "values ('" + rsp.getString("CABINET_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("CABINETNO") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("RACKTYPE") +"','" + rsp.getString("BOMRACKTYPE") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION") +"',DATE '" + dat3 +"',DATE '" + dat4 +"','" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("ISSUENUMBER") +"','" + rsp.getString("BOMCODE") +"','" + rsp.getString("EXTINFO") +"','" + rsp.getString("MODEL") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("SHAREMODE") +"','" + rsp.getString("CLEICODE") +"','" + rsp.getString("BOM") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("ALM_POSITION") +"',TIEMSTAMP '" + rsp.getString("CREATION_DATE") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
			 stmt0.executeUpdate();
			 stmt0.close();
	     }
		 rsp.close();
		 stmtp.close();
        
      //Insert into TEMP_NODE_HOSTVER
		 queryp = "select * from NODE_HOSTVER"; 
		 stmtp = con.createStatement();
	     rsp = stmtp.executeQuery(queryp);
		 while (rsp.next()) {
			 stmt1 = conalm.prepareStatement("insert into TEMP_NODE_HOSTVER (HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR) "
			 		+ "values ('" + rsp.getString("HOSTVER_ID") +"','" + rsp.getString("HOSTVERTYPE") +"','" + rsp.getString("HOSTVER") +"','" + rsp.getString("SDESC") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"',sysdate,'" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
		     stmt1.executeUpdate();
		     stmt1.close();
	     }
		 rsp.close();
		 stmtp.close();
           
         //Insert into TEMP_NODE_FRAME
		 queryp = "select * from NODE_FRAME"; 
		 stmtp = con.createStatement();
	     rsp = stmtp.executeQuery(queryp);
		 while (rsp.next()) {
			 dat3=rsp.getString("DATEOFMANUFACTURE");
			 dat3 = dat3.substring(0, 10) ;
			 dat4=rsp.getString("DATEOFLASTSERVICE");
			 dat4 = dat4.substring(0, 10) ;
			 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_FRAME (FRAME_ID,RACKNO,FRAMENO,INVENTORYUNITID,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
			 		+ "values ('" + rsp.getString("FRAME_ID") +"','" + rsp.getString("RACKNO") +"','" + rsp.getString("FRAMENO") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("FRAMETYPE") +"','" + rsp.getString("RACKFRAMENO") +"','" + rsp.getString("MODULENO") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION") +"',DATE '" + dat3 +"',DATE '" + dat4 +"','" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("MODEL") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
			 stmt2.executeUpdate();
			 stmt2.close();
	     }
		 rsp.close();
		 stmtp.close();
          
          //Insert into TEMP_NODE_SLOT
		 queryp = "select * from NODE_SLOT"; 
		 stmtp = con.createStatement();
	     rsp = stmtp.executeQuery(queryp);
		 while (rsp.next()) {
			 dat3=rsp.getString("DATEOFMANUFACTURE");
			 dat3 = dat3.substring(0, 10) ;
			 dat4=rsp.getString("DATEOFLASTSERVICE");
			 dat4 = dat4.substring(0, 10) ;
			 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_SLOT (SLOT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,INVENTORYUNITID,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
			 		+ "values ('" + rsp.getString("SLOT_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("CABINETNO") +"','" + rsp.getString("SUBRACKNO") +"','" + rsp.getString("RACKNO") +"','" + rsp.getString("FRAMENO") +"','" + rsp.getString("SLOTNO") +"','" + rsp.getString("SLOTPOS") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION") +"',DATE '" + dat3 +"',DATE '" + dat4 +"','" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
			 stmt0.executeUpdate();
			 stmt0.close();
	     }
		 rsp.close();
		 stmtp.close();
          
        //Insert into TEMP_NODE_BOARD
		 queryp = "select * from NODE_BOARD"; 
		 stmtp = con.createStatement();
	     rsp = stmtp.executeQuery(queryp);
		 while (rsp.next()) {
			 dat3=rsp.getString("DATEOFMANUFACTURE");
			 dat3 = dat3.substring(0, 10) ;
			 dat4=rsp.getString("DATEOFLASTSERVICE");
			 dat4 = dat4.substring(0, 10) ;
			 stmt1 = conalm.prepareStatement("insert into TEMP_NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR)"
			 		+ "values ('" + rsp.getString("BOARD_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("CABINETNO") +"','" + rsp.getString("SUBRACKNO") +"','" + rsp.getString("RACKNO") +"','" + rsp.getString("FRAMENO") +"','" + rsp.getString("SLOTNO") +"','" + rsp.getString("SLOTPOS") +"','" + rsp.getString("SUBSLOTNO") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("MODULENO") +"','" + rsp.getString("BOARDNAME") +"','" + rsp.getString("BOARDTYPE") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION") +"',DATE '" + dat3 +"',DATE '" + dat4 +"','" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("SOFTVER") +"','" + rsp.getString("LOGICVER") +"','" + rsp.getString("BIOSVER") +"','" + rsp.getString("BIOSVEREX") +"','" + rsp.getString("LANVER") +"','" + rsp.getString("MBUSVER") +"','" + rsp.getString("ISSUENUMBER") +"','" + rsp.getString("BOMCODE") +"','" + rsp.getString("MODEL") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("EXTINFO") +"','" + rsp.getString("APDEVINFO") +"','" + rsp.getString("WORKMODE") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("ALM_POSITION") +"',TIMESTAMP '" + rsp.getString("CREATION_DATE") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
			 stmt1.executeUpdate();
			 stmt1.close();
	     }
		 rsp.close();
		 stmtp.close();

           //Insert into TEMP_NODE_PORT
			 queryp = "select * from NODE_PORT"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 dat3=rsp.getString("DATEOFMANUFACTURE");
				 dat3 = dat3.substring(0, 10) ;
				 dat4=rsp.getString("DATEOFLASTSERVICE");
				 dat4 = dat4.substring(0, 10) ;
				 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_PORT (PORT_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,VENDORUNITFAMILYTYPE,INVENTORYUNITID,PORTNO,HARDWAREVERSION,SERIALNUMBER,INVENTORYUNITTYPE,VENDORNAME,VENDORUNITTYPENUMBER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MACADDR,MANUFACTURERDATA,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("PORT_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("CABINETNO") +"','" + rsp.getString("SUBRACKNO") +"','" + rsp.getString("RACKNO") +"','" + rsp.getString("FRAMENO") +"','" + rsp.getString("SLOTNO") +"','" + rsp.getString("SLOTPOS") +"','" + rsp.getString("SUBSLOTNO") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("PORTNO") +"','" + rsp.getString("HARDWAREVERSION") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"',DATE '" + dat3 +"',DATE '" + dat4 +"','" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MACADDR") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt2.executeUpdate();
				 stmt2.close();
		     }
			 rsp.close();
			 stmtp.close();
            
            //Insert into TEMP_NODE_ACCESSORY
			 queryp = "select * from NODE_ACCESSORY"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 dat3=rsp.getString("DATEOFMANUFACTURE");
				 dat3 = dat3.substring(0, 10) ;
				 dat4=rsp.getString("DATEOFLASTSERVICE");
				 dat4 = dat4.substring(0, 10) ;
				 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_ACCESSORY (ACCESSORY_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,ACCESSORYTYPE,ADDTIONALINFORMATION,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("ACCESSORY_ID") +"','" + rsp.getString("RACKPOSITION") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION") +"','" + rsp.getString("SOFTVER") +"',DATE '" + dat3 +"',DATE '" + dat4 +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("ACCESSORYTYPE") +"','" + rsp.getString("ADDTIONALINFORMATION") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt0.executeUpdate();
				 stmt0.close();
		     }
			 rsp.close();
			 stmtp.close();

          
        //Insert into TEMP_NODE_HOST
			 queryp = "select * from NODE_HOST"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 dat3=rsp.getString("DATEOFMANUFACTURE");
				 dat3 = dat3.substring(0, 10) ;
				 dat4=rsp.getString("DATEOFLASTSERVICE");
				 dat4 = dat4.substring(0, 10) ;
				 stmt1 = conalm.prepareStatement("insert into TEMP_NODE_HOST (HOST_ID,RACKPOSITION,INVENTORYUNITID,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,SOFTVER,DATEOFMANUFACTURE,DATEOFLASTSERVICE,MANUFACTURERDATA,HOSTNAME,NUMBEROFCPU,MEMSIZE,HARDDISKSIZE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("HOST_ID") +"','" + rsp.getString("RACKPOSITION") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION") +"','" + rsp.getString("SOFTVER") +"',DATE '" + dat3 +"',DATE '" + dat4 +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("HOSTNAME") +"','" + rsp.getString("NUMBEROFCPU") +"','" + rsp.getString("MEMSIZE") +"','" + rsp.getString("HARDDISKSIZE") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt1.executeUpdate();
				 stmt1.close();
		     }
			 rsp.close();
			 stmtp.close();
			 
             
           //Insert into TEMP_NODE_SUBRACK
			 queryp = "select * from NODE_SUBRACK"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 dat3=rsp.getString("DATEOFMANUFACTURE");
				 dat3 = dat3.substring(0, 10) ;
				 dat4=rsp.getString("DATEOFLASTSERVICE");
				 dat4 = dat4.substring(0, 10) ;
				 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_SUBRACK (SUBRACK_ID,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("SUBRACK_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("CABINETNO") +"','" + rsp.getString("SUBRACKNO") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("RACKTYPE") +"','" + rsp.getString("BOMRACKTYPE") +"','" + rsp.getString("FRAMETYPE") +"','" + rsp.getString("RACKFRAMENO") +"','" + rsp.getString("MODULENO") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION") +"',DATE '" + dat3 +"',DATE '" + dat4 +"','" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("BOMCODE") +"','" + rsp.getString("MODEL") +"','" + rsp.getString("ISSUENUMBER") +"','" + rsp.getString("BOMFRAMETYPE") +"','" + rsp.getString("CLEICODE") +"','" + rsp.getString("BOM") +"','" + rsp.getString("EXTINFO") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt2.executeUpdate();
				 stmt2.close();
		     }
			 rsp.close();
			 stmtp.close();
	            
            //Insert into TEMP_NODE_GCELL
			 queryp = "select * from NODE_GCELL"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_GCELL (GCELL_ID,CELLID,CELLNAME,MCC,MNC,LAC,CI,NCC,BCC,TYPE,BCCHNO,BASEBANDPOLICY,BASEBANDEQMID,GBTSFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,GLOCELLID,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("GCELL_ID") +"','" + rsp.getString("CELLID") +"','" + rsp.getString("CELLNAME") +"','" + rsp.getString("MCC") +"','" + rsp.getString("MNC") +"','" + rsp.getString("LAC") +"','" + rsp.getString("CI") +"','" + rsp.getString("NCC") +"','" + rsp.getString("BCC") +"','" + rsp.getString("TYPE") +"','" + rsp.getString("BCCHNO") +"','" + rsp.getString("BASEBANDPOLICY") +"','" + rsp.getString("BASEBANDEQMID") +"','" + rsp.getString("GBTSFUNCTIONNAME") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("GLOCELLID") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"',sysdate,'" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt0.executeUpdate();
				 stmt0.close();
		     }
			 rsp.close();
			 stmtp.close();
          
     	  	//Insert into TEMP_NODE_BTS
			 queryp = "select * from NODE_BTS"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_BTS (BTS_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("BTS_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("SITENAME") +"','" + rsp.getString("SITETYPE") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt2.executeUpdate();
				 stmt2.close();
		     }
			 rsp.close();
			 stmtp.close();
             
           //Insert into TEMP_NODE_UCELL
			 queryp = "select * from NODE_UCELL"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_UCELL (UCELL_ID,CELLID,CELLNAME,LOCELL,NODEBFUNCTIONNAME,ULFREQ,DLFREQ,MAXPOWER,USERLABEL,MAXTXPOWER,UARFCNUPLINK,UARFCNDOWNLINK,PSCRAMBCODE,NODEBNAME,LAC,SAC,RAC,MANUFACTURERDATA,RADIUS,HORAD,DI,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("UCELL_ID") +"','" + rsp.getString("CELLID") +"','" + rsp.getString("CELLNAME") +"','" + rsp.getString("LOCELL") +"','" + rsp.getString("NODEBFUNCTIONNAME") +"','" + rsp.getString("ULFREQ") +"','" + rsp.getString("DLFREQ") +"','" + rsp.getString("MAXPOWER") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("MAXTXPOWER") +"','" + rsp.getString("UARFCNUPLINK") +"','" + rsp.getString("UARFCNDOWNLINK") +"','" + rsp.getString("PSCRAMBCODE") +"','" + rsp.getString("NODEBNAME") +"','" + rsp.getString("LAC") +"','" + rsp.getString("SAC") +"','" + rsp.getString("RAC") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("RADIUS") +"','" + rsp.getString("HORAD") +"','" + rsp.getString("DI") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"',sysdate,'" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt2.executeUpdate();
				 stmt2.close();
		     }
			 rsp.close();
			 stmtp.close();
            
            //Insert into TEMP_NODE_ANTENNA
			 queryp = "select * from NODE_ANTENNA"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 dat3=rsp.getString("DATEOFMANUFACTURE");
				 dat3 = dat3.substring(0, 10) ;
				 dat4=rsp.getString("DATEOFLASTSERVICE");
				 dat4 = dat4.substring(0, 10) ;
				 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_ANTENNA (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR)"
					 		+ "values ('" + rsp.getString("ANTENNA_ID") +"','" + rsp.getString("INVENTORYUNITID") +"','" + rsp.getString("INVENTORYUNITTYPE") +"','" + rsp.getString("ANTENNADEVICENO") +"','" + rsp.getString("PRODNR") +"','" + rsp.getString("VENDORUNITFAMILYTYPE") +"','" + rsp.getString("VENDORUNITTYPENUMBER") +"','" + rsp.getString("VENDORNAME") +"','" + rsp.getString("SERIALNUMBER") +"','" + rsp.getString("HARDWAREVERSION") +"', DATE '" + dat3 +"',DATE '" + dat4 +"','" + rsp.getString("UNITPOSITION") +"','" + rsp.getString("MANUFACTURERDATA") +"','" + rsp.getString("ANTENNADEVICETYPE") +"','" + rsp.getString("ISSUENUMBER") +"','" + rsp.getString("BOMCODE") +"','" + rsp.getString("EXTINFO") +"','" + rsp.getString("MODEL") +"','" + rsp.getString("SERIALNUMBEREX") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("CREATION_DATE") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt0.executeUpdate();
				 stmt0.close();
		     }
			 rsp.close();
			 stmtp.close();
          
     	  	//Insert into TEMP_NODE_LCELL
			 queryp = "select * from NODE_LCELL"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 stmt1 = conalm.prepareStatement("insert into TEMP_NODE_LCELL (LCELL_ID,LOCALCELLID,CELLNAME,CELLRADIUS,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,ENODEBFUNCTIONNAME,NBCELLFLAG,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("LCELL_ID") +"','" + rsp.getString("LOCALCELLID") +"','" + rsp.getString("CELLNAME") +"','" + rsp.getString("CELLRADIUS") +"','" + rsp.getString("FREQBAND") +"','" + rsp.getString("ULEARFCNCFGIND") +"','" + rsp.getString("ULEARFCN") +"','" + rsp.getString("DLEARFCN") +"','" + rsp.getString("ULBANDWIDTH") +"','" + rsp.getString("DLBANDWIDTH") +"','" + rsp.getString("CELLID") +"','" + rsp.getString("PHYCELLID") +"','" + rsp.getString("FDDTDDIND") +"','" + rsp.getString("ENODEBFUNCTIONNAME") +"','" + rsp.getString("NBCELLFLAG") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"',sysdate,'" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt1.executeUpdate();
				 stmt1.close();
		     }
			 rsp.close();
			 stmtp.close();
             
           //Insert into TEMP_NODE_RRN
			 queryp = "select * from NODE_RRN"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_RRN (RRN_ID,SITEINDEX,SITENAME,SITETYPE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("RRN_ID") +"','" + rsp.getString("SITEINDEX") +"','" + rsp.getString("SITENAME") +"','" + rsp.getString("SITETYPE") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"' ,'" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt2.executeUpdate();
				 stmt2.close();
		     }
			 rsp.close();
			 stmtp.close();
            
            //Insert into TEMP_NODE_ENODEBCELL
			 queryp = "select * from NODE_ENODEBCELL"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 stmt0 = conalm.prepareStatement("insert into TEMP_NODE_ENODEBCELL (ENODEBCELL_ID,LOCALCELLID,CELLNAME,SECTORID,CSGIND,CYCLEPREFIX,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,CELLID,PHYCELLID,FDDTDDIND,TAC,ADDITIONALSPECTRUMEMISSION,NBCELLFLAG,ENODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("ENODEBCELL_ID") +"','" + rsp.getString("LOCALCELLID") +"','" + rsp.getString("CELLNAME") +"','" + rsp.getString("SECTORID") +"','" + rsp.getString("CSGIND") +"','" + rsp.getString("CYCLEPREFIX") +"','" + rsp.getString("FREQBAND") +"','" + rsp.getString("ULEARFCNCFGIND") +"','" + rsp.getString("ULEARFCN") +"','" + rsp.getString("DLEARFCN") +"','" + rsp.getString("ULBANDWIDTH") +"','" + rsp.getString("DLBANDWIDTH") +"','" + rsp.getString("CELLID") +"','" + rsp.getString("PHYCELLID") +"','" + rsp.getString("FDDTDDIND") +"','" + rsp.getString("TAC") +"','" + rsp.getString("ADDITIONALSPECTRUMEMISSION") +"','" + rsp.getString("NBCELLFLAG") +"','" + rsp.getString("ENODEBFUNCTIONNAME") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt0.executeUpdate();
				 stmt0.close();
		     }
			 rsp.close();
			 stmtp.close();
          
     	  	//Insert into TEMP_NODE_NODEBCELL
			 queryp = "select * from NODE_NODEBCELL"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 stmt1 = conalm.prepareStatement("insert into TEMP_NODE_NODEBCELL (NODEBCELL_ID,LOCELL,USERLABEL,SITENO,SECNO,RADIUS,HORAD,BBPOOLTYPE,ULGROUPNO,CABINETNO1,SUBRACKNO1,SLOTNO1,CABINETNO2,SUBRACKNO2,SLOTNO2,ULFREQ,DLFREQ,MAXPOWER,HSDPA,DI,HIGHSPEEDMODE,SPEEDRATE,NODEBFUNCTIONNAME,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("NODEBCELL_ID") +"','" + rsp.getString("LOCELL") +"','" + rsp.getString("USERLABEL") +"','" + rsp.getString("SITENO") +"','" + rsp.getString("SECNO") +"','" + rsp.getString("RADIUS") +"','" + rsp.getString("HORAD") +"','" + rsp.getString("BBPOOLTYPE") +"','" + rsp.getString("ULGROUPNO") +"','" + rsp.getString("CABINETNO1") +"','" + rsp.getString("SUBRACKNO1") +"','" + rsp.getString("SLOTNO1") +"','" + rsp.getString("CABINETNO2") +"','" + rsp.getString("SUBRACKNO2") +"','" + rsp.getString("SLOTNO2") +"','" + rsp.getString("ULFREQ") +"','" + rsp.getString("DLFREQ") +"','" + rsp.getString("MAXPOWER") +"','" + rsp.getString("HSDPA") +"','" + rsp.getString("DI") +"','" + rsp.getString("HIGHSPEEDMODE") +"','" + rsp.getString("SPEEDRATE") +"','" + rsp.getString("NODEBFUNCTIONNAME") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"' ,'" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt1.executeUpdate();
				 stmt1.close();
		     }
			 rsp.close();
			 stmtp.close();
             
           //Insert into TEMP_NODE_NBINTERFACES
			 queryp = "select * from NODE_NBINTERFACES"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_NBINTERFACES (NBINTERFACE_ID,INTERFACETYPE,VERSION,ISUSED,NMSVENDOR,NMSNAME,REMARK,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("NBINTERFACE_ID") +"','" + rsp.getString("INTERFACETYPE") +"','" + rsp.getString("VERSION") +"','" + rsp.getString("ISUSED") +"','" + rsp.getString("NMSVENDOR") +"','" + rsp.getString("NMSNAME") +"','" + rsp.getString("REMARK") +"','" + rsp.getString("NODE_PK") +"','" + rsp.getString("NODE_ATTR_PK") +"',TIMESTAMP '" + rsp.getString("UPDATE_DATE") +"','" + rsp.getString("FILENAME") +"','" + rsp.getString("STATUS") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("LINE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt2.executeUpdate();
				 stmt2.close();
		     }
			 rsp.close();
			 stmtp.close();
			 
			 
			//Insert into TEMP_NODE_CHILD_PARENT
			 queryp = "select * from NODE_CHILD_PARENT"; 
			 stmtp = con.createStatement();
		     rsp = stmtp.executeQuery(queryp);
			 while (rsp.next()) {
				 stmt2 = conalm.prepareStatement("insert into TEMP_NODE_CHILD_PARENT (ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID,CHILD_NAME,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR)"
				 		+ "values ('" + rsp.getString("ID") +"',TIMESTAMP '" + rsp.getString("CREATION_DATE") +"',TIMESTAMP '" + rsp.getString("LAST_MODIFIED_DATE") +"','" + rsp.getString("CHILD_ID") +"','" + rsp.getString("CHILD_NAME") +"','" + rsp.getString("CHILD_TYPE") +"','" + rsp.getString("CHILD_MODEL") +"','" + rsp.getString("PARENT_ID") +"','" + rsp.getString("PARENT_NAME") +"','" + rsp.getString("PARENT_TYPE") +"','" + rsp.getString("PARENT_MODEL") +"','" + rsp.getString("FILE_NAME") +"','" + rsp.getString("FROM_TRANS_SOURCE") +"','" + rsp.getString("TO_TRANS_SOURCE") +"' ,'" + rsp.getString("FROM_TRANS_ID") +"','" + rsp.getString("TO_TRANS_ID") +"','" + rsp.getString("TRANS_TYPE") +"','" + rsp.getString("ACTIVE_RECORD") +"','" + rsp.getString("DOMAIN") +"','" + rsp.getString("VENDOR") +"')");
				 stmt2.executeUpdate();
				 stmt2.close();
		     }
			 rsp.close();
			 stmtp.close();
			 
		 }
		 catch(Exception e)  
			{  
				logger.info("error at RunCopydata is :"+ e.toString());
				System.out.println("error at RunCopydata is :"+ e.toString()); 
			}	
   
 	}
 
private static void DeletedatafromNodeTables(String vdomain, String vvendor) throws InterruptedException, SQLException {
	 
	try {
	 		//Delete from NODE_ACTIVE
	 		PreparedStatement stmt0 = con.prepareStatement("delete  from NODE_ACTIVE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
	 		stmt0.executeUpdate();
	 		stmt0.close();
     
	 		//Delete from NODE_ACTIVE_ATTRIBUTE
   	 		PreparedStatement stmt1 = con.prepareStatement("delete  from NODE_ACTIVE_ATTRIBUTE where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
   	 		stmt1.executeUpdate();
        	stmt1.close();
        
        	//Delete from NODE_RACK
   	 		PreparedStatement stmt2 = con.prepareStatement("delete  from NODE_RACK where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
   	 		stmt2.executeUpdate();
        	stmt2.close();
        
        	//Delete from NODE_CABINET
   	  		stmt0 = con.prepareStatement("delete  from NODE_CABINET where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
   	  		stmt0.executeUpdate();
   	  		stmt0.close();
        
   	  		//Delete from NODE_HOSTVER
   	  		stmt1 = con.prepareStatement("delete  from NODE_HOSTVER where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
   	  		stmt1.executeUpdate();
   	  		stmt1.close();
           
         	//Delete from NODE_FRAME
      	  	stmt2 = con.prepareStatement("delete  from NODE_FRAME where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
      	  	stmt2.executeUpdate();
      	  	stmt2.close();
          
          	//Delete from NODE_SLOT
     	  	stmt0 = con.prepareStatement("delete  from NODE_SLOT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
     	  	stmt0.executeUpdate();
     	  	stmt0.close();
          
     	  	//Delete from NODE_BOARD
        	stmt1 = con.prepareStatement("delete  from NODE_BOARD where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
            stmt1.executeUpdate();
            stmt1.close();
             
           //Delete from NODE_PORT
        	stmt2 = con.prepareStatement("delete  from NODE_PORT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
            stmt2.executeUpdate();
            stmt2.close();
            
            //Delete from NODE_ACCESSORY
     	  	stmt0 = con.prepareStatement("delete  from NODE_ACCESSORY where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
     	  	stmt0.executeUpdate();
     	  	stmt0.close();
          
     	  	//Delete from NODE_HOST
        	stmt1 = con.prepareStatement("delete  from NODE_HOST where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
            stmt1.executeUpdate();
            stmt1.close();
             
            //Delete from NODE_SUBRACK
        	stmt2 = con.prepareStatement("delete  from NODE_SUBRACK where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
            stmt2.executeUpdate();
            stmt2.close();
            
            //Delete from NODE_GCELL
     	  	stmt0 = con.prepareStatement("delete  from NODE_GCELL where DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
     	  	stmt0.executeUpdate();
     	  	stmt0.close();
          
     	  	//Delete from NODE_BTS
        	stmt1 = con.prepareStatement("delete  from NODE_BTS where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
            stmt1.executeUpdate();
            stmt1.close();
             
           //Delete from NODE_UCELL
        	stmt2 = con.prepareStatement("delete  from NODE_UCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
            stmt2.executeUpdate();
            stmt2.close();
            
            //Delete from NODE_ANTENNA
     	  	stmt0 = con.prepareStatement("delete  from NODE_ANTENNA where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
     	  	stmt0.executeUpdate();
     	  	stmt0.close();
          
     	  	//Delete from NODE_LCELL
        	stmt1 = con.prepareStatement("delete  from NODE_LCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
            stmt1.executeUpdate();
            stmt1.close();
             
            //Delete from NODE_RRN
        	stmt2 = con.prepareStatement("delete  from NODE_RRN where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
            stmt2.executeUpdate();
            stmt2.close();
            
            //Delete from NODE_ENODEBCELL
     	  	stmt0 = con.prepareStatement("delete  from NODE_ENODEBCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
     	  	stmt0.executeUpdate();
     	  	stmt0.close();
          
     	  	//Delete from NODE_NODEBCELL
        	stmt1 = con.prepareStatement("delete  from NODE_NODEBCELL where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
            stmt1.executeUpdate();
            stmt1.close();
             
            //Delete from NODE_NBINTERFACES
        	stmt2 = con.prepareStatement("delete  from NODE_NBINTERFACES where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
            stmt2.executeUpdate();
            stmt2.close();
            
            //Delete from NODE_CHILD_PARENT
          	stmt2 = con.prepareStatement("delete  from NODE_CHILD_PARENT where  DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
              stmt2.executeUpdate();
              stmt2.close();
		}
		catch(Exception e)  
		{  
			logger.info("error at DeletedatafromNodeTables is :"+ e.toString());
			System.out.println("error at DeletedatafromNodeTables is :"+ e.toString()); 
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
		
    }
      String query2 = "select "+SeqName+".nextval as nbr from dual";      
          stmttype = conalm.createStatement();
          ResultSet rs2 = stmttype.executeQuery(query2);
         
          while (rs2.next()) {
           min= rs2.getString("nbr");    
          	}
          rs2.close();

          stmttype.close();

			 return min;

  }

private static void GetduplicateFilename(String vdomain , String vvendor) throws SQLException  {
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	Statement stmt4 = null;
	int vcount =0;
	int i=0;
	
	// select all file having duplicata entry and same filename >1
	String query1 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,count(*) counter from NODE_ACTIVE  group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR having count(*) >1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";  
    stmt1 = con.createStatement();
    ResultSet rs1 = stmt1.executeQuery(query1);
    while (rs1.next()) {
    	//try {
                 // select all rows related to a file identified by rs1 having count >=1
				 String query2 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,count(*) counter from (select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR from NODE_ACTIVE where SITE_ID ='"+ rs1.getString("SITE_ID") +"' and CIRCLE_ID ='"+ rs1.getString("CIRCLE_ID") +"' and NODE_ID ='"+ rs1.getString("NODE_ID") +"' and NODE_NAME ='"+ rs1.getString("NODE_NAME") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' ) group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR having count(*) >=1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";  
		         stmt2 = con.createStatement();
		         ResultSet rs2 = stmt2.executeQuery(query2);
		         // get all node_pk found duplicated
		         while (rs2.next()) {
		        	 vcount =0;
		        	 vcount= (int) Long.parseLong (rs2.getString("counter"));
		        	 i=0;
			        	    //Get old creation date of the same file and update rows with old creation date
			        	 	String query3 = "select node_pk,creation_date from NODE_ACTIVE where NODE_ID='"+ rs2.getString("NODE_ID") +"' and SITE_ID='"+ rs2.getString("SITE_ID") +"' and NODE_NAME ='"+ rs2.getString("NODE_NAME") +"' and CIRCLE_ID ='"+ rs2.getString("CIRCLE_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' order by node_pk asc";  
				            stmt3 = con.createStatement();
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
				            		PreparedStatement updtmt8 = con.prepareStatement("update NODE_ACTIVE set creation_date = DATE '"+ vdate +"' where NODE_NAME ='"+ rs2.getString("NODE_NAME") +"' and CIRCLE_ID ='"+ rs2.getString("CIRCLE_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
					    			updtmt8.executeUpdate();
					    			updtmt8.close();
				            		
					    			//Function to delete the filename from all tables  argument field name and field value
					    			deleterowsinALLTABLES("NODE_PK", rs3.getString("node_pk"),vdomain,vvendor );
				            	}  
				            	if ((i >0) && (i< (vcount-1)) ) 
				            	 {  //if i< maxcount just to delete duplicate node_pk from all table 
		
				            		//Function to delete the filename from all tables  argument field name and field value
				            		deleterowsinALLTABLES("NODE_PK", rs3.getString("node_pk"),vdomain,vvendor );
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

 private static void deleterowsinALLTABLES(String fieldname, String fieldValue,String vdomain, String vvendor) throws SQLException  {
	 try {
	 // delete all rows related to node_pk from all nodes tables
	 PreparedStatement stmt = con.prepareStatement("delete from NODE_ACTIVE where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close();
     
     PreparedStatement stmt1 = con.prepareStatement("delete from  NODE_ACTIVE_ATTRIBUTE where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
      
     PreparedStatement stmt2 = con.prepareStatement("delete from  NODE_RACK where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = con.prepareStatement("delete from  NODE_CABINET where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' "); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = con.prepareStatement("delete from  NODE_HOSTVER where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
      
     stmt2 = con.prepareStatement("delete from  NODE_FRAME where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
      
     stmt = con.prepareStatement("delete from  NODE_SLOT where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
      		 
     stmt1 = con.prepareStatement("delete from  NODE_BOARD where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
     stmt2 = con.prepareStatement("delete from  NODE_PORT where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = con.prepareStatement("delete from  NODE_ACCESSORY where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = con.prepareStatement("delete from  NODE_HOST where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
     stmt2 = con.prepareStatement("delete from  NODE_SUBRACK where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = con.prepareStatement("delete from  NODE_GCELL where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = con.prepareStatement("delete from  NODE_BTS where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
     stmt2 = con.prepareStatement("delete from  NODE_UCELL where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = con.prepareStatement("delete from  NODE_ANTENNA where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = con.prepareStatement("delete from  NODE_LCELL where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
     stmt2 = con.prepareStatement("delete from  NODE_RRN where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = con.prepareStatement("delete from  NODE_ENODEBCELL where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = con.prepareStatement("delete from  NODE_NODEBCELL where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
	 stmt = con.prepareStatement("delete from  NODE_NBINTERFACES where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
     stmt.executeUpdate();
     stmt.close();
	 }
	catch(Exception e)  
	{  
		logger.info("error at deleterowsinALLTABLES is :"+ e.toString());
		System.out.println("error at deleterowsinALLTABLES is :"+ e.toString()); 
	}
     
}
 
 private static void deleteTempNodeTables() throws SQLException  {
	 try {
	 // delete all rows related to node_pk from all nodes tables
	 PreparedStatement stmt = conalm.prepareStatement("delete from TEMP_NODE_ACTIVE where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt.executeUpdate();
     stmt.close();
     
     PreparedStatement stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_ACTIVE_ATTRIBUTE where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
      
     PreparedStatement stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_RACK where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = conalm.prepareStatement("delete from  TEMP_NODE_CABINET where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_HOSTVER where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
      
     stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_FRAME where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
      
     stmt = conalm.prepareStatement("delete from  TEMP_NODE_SLOT where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
      		 
     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_BOARD where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
     stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_PORT where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = conalm.prepareStatement("delete from  TEMP_NODE_ACCESSORY where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_HOST where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
     stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_SUBRACK where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = conalm.prepareStatement("delete from  TEMP_NODE_GCELL where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_BTS where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
     stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_UCELL where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = conalm.prepareStatement("delete from  TEMP_NODE_ANTENNA where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_LCELL where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
     stmt2 = conalm.prepareStatement("delete from  TEMP_NODE_RRN where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt2.executeUpdate();
     stmt2.close();
     
     stmt = conalm.prepareStatement("delete from  TEMP_NODE_ENODEBCELL where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt.executeUpdate();
     stmt.close(); 
     		 
     stmt1 = conalm.prepareStatement("delete from  TEMP_NODE_NODEBCELL where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt1.executeUpdate();
     stmt1.close();
     
	 stmt = conalm.prepareStatement("delete from  TEMP_NODE_NBINTERFACES where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt.executeUpdate();
     stmt.close();
     
     stmt = conalm.prepareStatement("delete from  TEMP_NODE_CHILD_PARENT where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
     stmt.executeUpdate();
     stmt.close();
	 }
		catch(Exception e)  
		{  
			logger.info("error at deleteTempNodeTables is :"+ e.toString());
			System.out.println("error at deleteTempNodeTables is :"+ e.toString()); 
		}
     
}
	 	 
}
