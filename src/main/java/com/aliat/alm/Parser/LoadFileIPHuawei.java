package com.aliat.alm.Parser;

import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.File;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class LoadFileIPHuawei {

	static String node_fk;
	static String readfileAIMfrom;
	static String copyfileAIMto;
	static String sqlQueryStr;
	static String logsid="0";
	static String logsDetailsId="0";
	static int totSumRow=0;
	static int totSumCol=0;
	static int NodeSeq;
	static int NodeBoardSeq;
	static int NodeModuleSeq;
	static BufferedReader objReader1 = null;
	static String strCurrentLine1;
	static String projpath = null;
	static String logpath;
	static String db1path;
	static String username1;
	static String password1;
	static String db2path;
	static String username2;
	static String password2,siteID;
	static String vfolderfrom,fileType,fileName;
	static String Gprovider,Domain,subDomain,subDomainType,gateway,gatewayType,gatewayIP,patchVersion,partNumber;
	static String copyfileNokiato;
	static String vfolderto;
	static String circleid="10";
	static String Gyear,wareID,wareName,longi,lat,creationDate,IPaddress,MACaddress,commStatus="0",adminStatus="0",softwareVersion,LCStatus="0";
	static Logger logger;
	static FileHandler fh;
	static Connection conalm;
	static Connection con;
	static String tech2 ="0";
	static String tech3 ="0";
	static String tech4 ="0";
	static String tech5 ="0";
	static String Gcodeattributid="0";
	static String vline="0";
	static String vcodeid ="0";
	static String vBoardcodeid ="0";
	static String vModulecodeid ="0";
	static HashMap<String, String> nodes = new HashMap<String, String>();
	static HashMap<String, String> vhmap = new HashMap<String, String>();
	static HashMap<String, String> vhmap2 = new HashMap<String, String>();
	static HashMap<String, String> vhmap10 = new HashMap<String, String>();
	static HashMap<String, String> vhmap12 = new HashMap<String, String>();
	static PreparedStatement stmtp;
	static Statement stmtp1;

	static HashMap<String, String> nodeIDhmap = new HashMap<String, String>();

	static HashMap<String, HashMap<String, String>>allModules = new HashMap<String, HashMap<String,String>>();
	static HashMap<String, HashMap<String, String>>allCabinets = new HashMap<String, HashMap<String,String>>();
	static HashMap<String, HashMap<String, String>>allFANs = new HashMap<String, HashMap<String,String>>();
	 
	static String nodeId = null;
	static String nodeType = null;
	static String nodeModel = null;
	static String nodeName = null;
	static String hww;
	static String unique_Node_ID = null;	
	
	static String boardName = null;
	static String boardType = null;
	static String hardwareVersion = null;
	static String slotId  = null;
	static String subrackId  = null;
	static String biosVersion = null;
	static String boardStatus = null;
	static String bomCode = null;
	//static String manifacturedDate = null;
	static String serialNumber = null;
	static String model = null;
	static String moduleStatus = null;
	static String nodeIPaddress = null;
	static ArrayList<String> FileName = new ArrayList<String>();
	
	public static void main(String[] args,String vendor,String domain,String sub_domain,String sub_domainType) throws Exception {
		

		//System.out.println("Start withh LOAD :" + System.getProperty("user.dir"));
		
	 	objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+"almconfig.dat"));
		 while ((strCurrentLine1 = objReader1.readLine()) != null){
			 String data = strCurrentLine1;
			 String[] data1 ;
			 String[] data2 ;
			 
			 if (data.contains("projectpath")) {
				 data1=data.split(";",-1);
				 projpath=data1[1];
				// System.out.println("projectpath found :" + projpath);
			 }
			 if (data.contains("logpath")) {
				 data1=data.split(";",-1);
				 logpath=data1[1];
				// System.out.println("logpath found :" + logpath);
			 }
			 if (data.contains("db1path")) {
				 data1=data.split(";",-1);
				 db1path=data1[1];
				 username1=data1[2];
				 password1=data1[3];
				// System.out.println("db1path found :" + db1path);
				// System.out.println("username1 found :" + username1);
				// System.out.println("password1 found :" + password1);
			 }
			 if (data.contains("db2path")) {
				 data1=data.split(";",-1);
				 db2path=data1[1];
				 username2=data1[2];
				 password2=data1[3];
				// System.out.println("db2path found :" + db2path);
				// System.out.println("username2 found :" + username2);
				// System.out.println("password2 found :" + password2);
			 }
			// if (data.contains("readexcelTransIpHuaweifrom")) { 
			 if (data.contains("readfileAIMfrom")) {
			//	 System.out.println("readfileAIMfrom");					
				 data1=data.split(";",-1);
				 readfileAIMfrom = data1[1];
				 data2=readfileAIMfrom.replace("\\","/").split("/",-1);
				 vfolderfrom=readfileAIMfrom;
			//	 System.out.println("vfolderfrom "+ vfolderfrom);
				 Gprovider=vendor;
			//	 System.out.println("Gprovider "+ Gprovider);
				 Domain=domain;
			//	 System.out.println("Domain "+ Domain);
				 subDomain=sub_domain;
			//	 System.out.println("subDomain "+ subDomain);
				 subDomainType = sub_domainType;
			//	 System.out.println("subDomainType "+ subDomainType);
			 }
			 if (data.contains("copyfileAIMto")) {
			//	 System.out.println("copyfileAIMto");									 
				 data1=data.split(";",-1);
				 copyfileAIMto=data1[1];
			//	 System.out.println("copyfileAIMto "+copyfileAIMto);
			 }
			
		}
		 objReader1.close();
		 
		 objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almcircle.dat"));
		 while ((strCurrentLine1 = objReader1.readLine()) != null){
			 String data = strCurrentLine1;
			 String[] data1 ;
			 data1=data.split(";",-1);
			 circleid=data1[1];
		 }
		 objReader1.close();	
		 
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		 LocalDateTime now = LocalDateTime.now();
		 String lofilename="ParserLogIPHW-"+dtf.format(now)+".log";
		 
		 File folder = new File(vfolderfrom);
		 System.out.println("folder....."+folder);
		 File[] listOfFiles = folder.listFiles();
	/*
		 System.out.println("listOfFiles....."+ folder.listFiles());
		 System.out.println("Number of files: " + listOfFiles.length);
		 System.out.println("Number of files0 : " + listOfFiles[0]);
		 System.out.println("Number of files1 : " + listOfFiles[1]); 
	*/
		 String fileName1 = null;
		// System.out.println("fileName1....."+fileName1);
		 logger = Logger.getLogger("MyLog"); 
		 logger.setUseParentHandlers(false);
		
		 // This block configure the logger with handler and formatter  and PATH
			
	        fh = new FileHandler(logpath+"\\"+lofilename);
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
	        
	        
		  DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
		  conalm = DriverManager.getConnection(db1path,username1,password1);
		  
		  try {
			//con= DriverManager.getConnection(dbURL,username,password);
			con= DriverManager.getConnection(db2path,username2,password2);
			} catch (SQLException e) {
			       System.out.println("Opss, error");
			       e.printStackTrace();
			}
		  
		  
		//validate if the same process is running now if yes we cannot run it twice until finish
			Statement stmtinit21 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    	 String sqlStmtinit21 = "select * from EXECUTE_DOAMIN_VENDOR_FILES where DOMAIN='"+Domain+"' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS' and SUB_DOMAIN='"+subDomain+"' and TYPE='"+subDomainType+"'";
			    ResultSet rsinit21 = stmtinit21.executeQuery(sqlStmtinit21);
			    rsinit21.last();
		 	    int totalrecinit2 = rsinit21.getRow();
		 	    rsinit21.beforeFirst();
		 	  if (totalrecinit2 == 0 ) {
		 		  PreparedStatement stmtinit = con.prepareStatement("insert into EXECUTE_DOAMIN_VENDOR_FILES (DOMAIN,VENDOR,CREATION_DATE,STATUS,SUB_DOMAIN,TYPE) values ('"+Domain+"', '"+ Gprovider +"',sysdate,'IN PROCESS','"+subDomain+"','"+subDomainType+"')");
					 stmtinit.executeUpdate();
					 stmtinit.close();
					 logger.info("Load Files inprocess...");
					//looping over all files found in the directory.
				
					 for (File file : listOfFiles) {
						// System.out.println("for loop");
						// System.out.println("listOfFiles >>>>>"+ listOfFiles);
						// System.out.println("file >>>>>"+ file);
						if(file.isFile()) {
							 fileName1 = file.getName().toString();
							// System.out.println("filename1 is : "+fileName1); //BOARD							 						 
							 if(fileName1.contains("Network")) {
						        	FileName.add(0,fileName1);
						        }
						        else {
						        	FileName.add(fileName1);
						        }							 
							 //splitting to get the file name and extension						
							 	//readfile(fileName1);
					             	 String[] data1=fileName1.replace(".","_").split("_");
									// System.out.println(data1.length);
									// System.out.println("data1 ::: "+data1);
									 fileName=data1[0]; 
									 fileType=data1[1];
									// System.out.println("fileName...."+fileName);	
									// System.out.println("Done reading file");						 	 
						 }						
					 }				
					 
					for(int i=0;i<FileName.size();i++) {
						//System.out.println("i........"+i);
						//System.out.println("FileName.get(i)........"+FileName.get(i));
						readfile(FileName.get(i));
						File source = new File(readfileAIMfrom+"\\"+FileName.get(i));
						File dest = new File(copyfileAIMto+"\\"+FileName.get(i)+".bkp");
						copyFiles(source,dest);							     
						deleteFiles(readfileAIMfrom+"\\"+FileName.get(i));
					}
		 	  }
		 	 GetduplicateFilename(Domain,Gprovider,subDomain);
		 	 logger.info("Load Files completed");
		 	// update file status to completed
				 stmtp = con.prepareStatement("update EXECUTE_DOAMIN_VENDOR_FILES set STATUS ='COMPLETED' where DOMAIN='"+Domain+"' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS' and SUB_DOMAIN='"+subDomain+"' ");
				 stmtp.executeUpdate();
				 stmtp.close();
		 
			  conalm.close();
			  con.close();
}	
	
	private static void readfile(String fileName) throws FileNotFoundException, IOException, SQLException {
		 //System.out.println("READ FILE");
		 //System.out.println("fileName..."+fileName);

		 CSVParser csvParser = new CSVParser(new FileReader(vfolderfrom + "\\" + fileName), CSVFormat.DEFAULT);
		  List<CSVRecord> records = new ArrayList<>();
		  for (CSVRecord record : csvParser) {
			  records.add(record);
		  									}
	 
    		  Calendar calendar = new GregorianCalendar();
    		  int year = calendar.get(Calendar.YEAR);
    		
    	
    		  //select the node active sequence from the seq table in alm.
    		  String sqlStmtinit2 = "select NODE_ACTIVE from SEQ_TABLE";     
    		  stmtp1 = conalm.createStatement();
    		  ResultSet rsinit2 = stmtp1.executeQuery(sqlStmtinit2);
    		  while(rsinit2.next()) {
    			  //store the returned result in a variable to be increased each loop instead of accessing the database all the time
    			  //which lead to exceed the maximum number of open cursors.
    			NodeSeq = rsinit2.getInt("NODE_ACTIVE");
    			//update the seq of the node active based on the size of the list filled from the csv file
    		  	stmtp = conalm.prepareStatement("UPDATE SEQ_TABLE SET NODE_ACTIVE = NODE_ACTIVE +"+(records.size()-4));//records.size()-4) is used to remove the unnecessary header rows in the csv file
    		  	stmtp.executeUpdate();
    		  	stmtp.close();
    		  }
    		 if(fileName.contains("Network")){
    		  for(int i=4;i<records.size();i++) {
    			  vcodeid = year+"_NODE_"+Gprovider+"_"+Domain+"_"+NodeSeq;
    			  rsinit2.close();
    			  stmtp1.close();
    			  if(records.get(i).get(0).contains("_")) {// if the cell of the csv file contains _ then it may contain site ID
    					if(records.get(i).get(0).split("_").length >= 3) { // if the number of the elements split in the cell according to _ then it may contain site ID
    						siteID = records.get(i).get(0).split("_")[0];
    						char charArray[] = siteID.toCharArray();
    						if(Character.isDigit(charArray[0]) && !Character.isDigit(charArray[siteID.length()-1])) { // if the first character of the possible site id is number then it is a site ID.
    							siteID = siteID;
    							String sqlStmtinit3 = "select WARE_ID,WARE_NAME,LONGITUDE,LATITUDE from WAREHOUSE WHERE SITE_ID='"+siteID+"'";     
    							  stmtp1 = conalm.createStatement();
    							  ResultSet rsinit3 = stmtp1.executeQuery(sqlStmtinit3);
    							  while(rsinit3.next()) {
    								  wareID=rsinit3.getString("WARE_ID");
    								  wareName = rsinit3.getString("WARE_ID");
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
    					}else {
    						wareID="";
    						  wareName ="";
    						  longi="";
    						  lat = "";
    						  siteID = "";
    						//System.out.println("site id and site name don't exists");
    					}
    				}else {
    					wareID="";
    					  wareName ="";
    					  longi="";
    					  lat = "";
    					//System.out.println("site id and site name don't exists");
    				}
    			  	  nodeName = records.get(i).get(0);
    			  	  nodeType="Router";
    				  nodeModel = records.get(i).get(1);
    				  IPaddress = records.get(i).get(2);
    				  softwareVersion = records.get(i).get(3);
    				  MACaddress = records.get(i).get(4);
    				  partNumber = records.get(i).get(10);
    				  commStatus= records.get(i).get(11);
    				  adminStatus= records.get(i).get(12);
    				  nodeId=records.get(i).get(2);
    				  if(!records.get(i).get(23).toString().trim().equalsIgnoreCase("--")) {LCStatus=records.get(i).get(23);}
    				  gateway=records.get(i).get(20);
    				  gatewayType=records.get(i).get(19);
    				  gatewayIP=records.get(i).get(21);
    				  patchVersion=records.get(i).get(17);
    				  unique_Node_ID = nodeId+"_HW";
    				  //System.out.println("DONE NODE ACTIVEEEEEEEEE");
    				  nodes.put(nodeId, vcodeid);
    				  
    				  
    				  String Others="{\"STATUS_1\":"+"\""+adminStatus+"\","+"\"GATEWAY\":"+"\""+gateway+"\","+"\"GATEWAY_TYPE\":"+"\""+gatewayType+"\","+"\"GATEWAY_IP\":"+"\""+gatewayIP+"\""+"\"STATUS_2\":"+"\""+LCStatus+"\"}";
    			        
    				  	stmtp =  con.prepareStatement("insert into NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,WARE_ID,VENDOR,WARE_NAME,IP_ADDRESS,MAC_ADDRESS,SUB_DOMAIN,SOFTWARE_VERSION,LONGITUDE,LATITUDE,PATCH_VERSION,PART_NUMBER,SUB_DOMAIN_TYPE,OTHERS)"
    					 		+ "values('"+vcodeid+"','"+unique_Node_ID+"','"+nodeId+"','"+nodeName+"','"+nodeType+"','"+Domain+"','"+nodeModel+"','"+tech2+"','"+tech3+"','"+tech4+"','"+tech5+"','"+siteID+"','"+circleid+"',sysdate,sysdate,'"+fileType+"','"+fileName+"','"+commStatus+"','"+wareID+"','"+Gprovider+"','"+wareName+"','"+IPaddress+"','"+MACaddress+"','"+subDomain+"','"+softwareVersion+"','"+longi+"','"+lat+"','"+patchVersion+"','"+partNumber+"','"+subDomainType+"','"+Others+"')"); 
    				  	stmtp.executeUpdate();
    				  	stmtp.close();
    			   
    				  	NodeSeq++;    		  
    		  }
    		 }else{
    		  
    		//select the node board sequence from the seq table in alm.
    		  String sqlStmtinit21M= "select NODE_BOARD from SEQ_TABLE";     
    		  stmtp1 = conalm.createStatement();
    		  ResultSet rsinit21M = stmtp1.executeQuery(sqlStmtinit21M);
    		  while(rsinit21M.next()) {
    			NodeBoardSeq = rsinit21M.getInt("NODE_BOARD");
    		  	stmtp = conalm.prepareStatement("UPDATE SEQ_TABLE SET NODE_BOARD = NODE_BOARD +"+(records.size()-4));
    		  	stmtp.executeUpdate();
    		  	stmtp.close();
    		  }
    		  
    		//select the node module sequence from the seq table in alm.
    		  String sqlStmtinit21B= "select NODE_MODULE from SEQ_TABLE";     
    		  stmtp1 = conalm.createStatement();
    		  ResultSet rsinit21B = stmtp1.executeQuery(sqlStmtinit21B);
    		  while(rsinit21B.next()) {
    			NodeModuleSeq = rsinit21B.getInt("NODE_MODULE");
    		  	stmtp = conalm.prepareStatement("UPDATE SEQ_TABLE SET NODE_MODULE = NODE_MODULE +"+(records.size()-4));
    		  	stmtp.executeUpdate();
    		  	stmtp.close();
    		  }
    		  
    	 for(int i=4;i<records.size();i++) {
    			  //System.out.println("for");   			 
    			  String description = records.get(i).get(24);
    			  boardName = records.get(i).get(1);
    			  //System.out.println("boardName;;;;; "+boardName); 
    			  
    		if (description.contains("fan") || description.contains("module") || description.equals("") && (boardName.contains("fan") || boardName.contains("module"))) {  				  
  			     // The element contains "fan" or "module" --> we should fill the data in table NODE_MODULE
  				 //System.out.println("filling in node module - contains fan/module");  				  	
  	    		  vModulecodeid= year+"_NODE_"+Gprovider+"_"+Domain+"_MOD_"+NodeModuleSeq;  	
  	    		 //System.out.println("vModulecodeid......."+vModulecodeid);
  	    		  nodeIPaddress = records.get(i).get(5);
  	    		  hardwareVersion = records.get(i).get(10);
   				 //System.out.println("hardwareVersion......."+hardwareVersion);
 				  serialNumber=records.get(i).get(12);		
 				  moduleStatus= records.get(i).get(19);
				  softwareVersion = records.get(i).get(11);  
 				 //System.out.println("softwareVersion......."+softwareVersion);
 				 if (softwareVersion.isEmpty() || softwareVersion.equals("--")) {
 					softwareVersion = "null";
 					//System.out.println("softwareVersion a: "+softwareVersion);         				
			    }
 				  
 				String[] partsoft = softwareVersion.split(",");	     		      
		        String bootRomVersion = null;
		       // System.out.println("bootRomVersion....."+bootRomVersion);
		        String bootLoadVersion = null;
		       // System.out.println("bootLoadVersion....."+bootLoadVersion);
		        
  			  if(softwareVersion.contains("BootROM") || softwareVersion.contains("BootLoad")){  
  			    	//System.out.println("parts.length...."+partsoft.length);
  			    	
  			    if(partsoft.length == 2) {  
  			    	//System.out.println("LENGTH 2 ");
  			    	//String softver = partsoft[0];
		            //String romver = partsoft[1];
		            
		            softwareVersion =  partsoft[0].split(" ")[2];
		            //System.out.println("softwareVersion >>>>"+softwareVersion); 
		            
	            	bootRomVersion = partsoft[1].split(" ")[3];
	            	//System.out.println("bootRomVersion >>>>"+bootRomVersion);
	            	
	            	bootLoadVersion = null;
   		        	//System.out.println("bootLoadVersion >>>>"+bootLoadVersion);	   	
		            
  			    }else if(partsoft.length == 3) {
  	   		       System.out.println("LENGTH 3 ");
   		            	String softver = partsoft[0];
   		            	String romver = partsoft[1];
   		            	String loadver = partsoft[2];
  	   		        /*
  	   		           System.out.println("softwareVersionsplit << "+softver); 
	   		           System.out.println("romVersionsplit << "+romver); 
	   		           System.out.println("loadVersionsplit << "+loadver);
	   		           System.out.println("softver.split()[0] << "+softver.split(" ")[0]);
	   		        */
	   		           if(softver.split(" ")[0].equals("Version")){
	   		        	//System.out.println(">> Version << ");
	   		        	 softwareVersion =  softver.split(" ")[1]+ ","+ softver.split(" ")[3];
	   		        	//System.out.println("softwareVersion >>>>"+softwareVersion);   		            		           
	   		           }else if(softver.split(" ")[0].equals("Software")){
	   		        	    //System.out.println(">> Software << ");
		   		        	softwareVersion =  softver.split(" ")[2];
	   		        	    //System.out.println("softwareVersion >>>>"+softwareVersion); 
		   		       }	        	   
  	   		            	bootRomVersion = romver.split(" ")[3];
	   		            	//System.out.println("bootRomVersion >>>>"+bootRomVersion);
  	   		            	
  	   		            	bootLoadVersion = loadver.split(" ")[3].split("(Configure)")[0];
		   		        	//System.out.println("bootLoadVersion >>>>"+bootLoadVersion);	   		       
  	  			      }else if(partsoft.length == 4) {
  	  			    	//System.out.println("LENGTH 4 ");
 		            	//String softver1 = partsoft[0];
 		            	//String softver2 = partsoft[1];
 		            	//String romver = partsoft[2];
 		            	//String loadver = partsoft[3];  		           
	   		           /*
	   		           System.out.println("softwareVersionsplit << "+softver1); 
	   		           System.out.println("softwareVersionsplit << "+softver2);
	   		           System.out.println("romVersionsplit << "+romver); 
	   		           System.out.println("loadVersionsplit << "+loadver);
	   		           System.out.println("softver1.split[0] << "+softver1.split(" ")[0]);
	   		           */
	   		           
	   		           if(partsoft[0].split(" ")[0].equals("Software")){
	   		        	  // System.out.println(">>> Software VER <<< ");
	   		        	   softwareVersion =  partsoft[0].split(" ")[2]+ ": "+partsoft[0].split(" ")[5]+ ","+ partsoft[0].split(" ")[7] +" - "+partsoft[1].split(" ")[1]+ ": "+partsoft[1].split(" ")[4]+ ","+ partsoft[1].split(" ")[6];
	   		        	  // System.out.println("softwareVersion >>>>"+softwareVersion); 
		   		       }else if(partsoft[0].split(" ")[0].equals("FSU")){
		   		    	  // System.out.println(">>> FSU <<<"); 
		   		        	softwareVersion =  partsoft[0].split(" ")[3]+ ","+ partsoft[1].split(" ")[2];
		   		        	//System.out.println("softwareVersion >>>>"+softwareVersion); 
		   		       }
	   		            	bootRomVersion = partsoft[2].split(" ")[3];
	   		            	//System.out.println("bootRomVersion >>>>"+bootRomVersion);
	   		            	
	   		            	bootLoadVersion = partsoft[3].split(" ")[3].split("(Configure)")[0];
		   		        	//System.out.println("bootLoadVersion >>>>"+bootLoadVersion);	   		       
	  			      }
  			     }else if(!softwareVersion.contains("BootROM") && !softwareVersion.contains("BootLoad") && softwareVersion.contains("Version")){  
  			    	//System.out.println("Version");  
  			    	String[] parts = softwareVersion.split(" ");        
  			    	for (int k = 0; k < parts.length - 1; k++) {
	  		            if (parts[k].equalsIgnoreCase("Version")) {
	  		               if (k + 1 < parts.length && !parts[k + 1].isEmpty()) {
	  		                    softwareVersion = parts[k + 1];
	  		                }
	  		            }
	  		        }
  		            //System.out.println("Version number: " + softwareVersion);
  			     }else {
  			    	softwareVersion = softwareVersion;
  			    	//System.out.println("soft Version number: " + softwareVersion);
  			     }
		        //System.out.println("BootROM version: " + bootRomVersion);
		        //System.out.println("BootLoad version: " + bootLoadVersion);
		        //System.out.println("Soft version: " + softwareVersion);	
		      
		        node_fk = nodes.get(records.get(i).get(5));
		        //System.out.println("node_fk: " + node_fk);	
		        
		       String Others="{\"ROMVER\":"+"\""+bootRomVersion+"\","+"\"LOADVER\":"+"\""+bootLoadVersion+"\""+"}";
		        
		        stmtp =  con.prepareStatement("insert into NODE_MODULE (MODULE_ID,SERIALNUMBER,NODE_PK,FILENAME,STATUS,DOMAIN,VENDOR,HARDWAREVERSION,SOFTVER,OTHERS)"
     					 		+ "values('"+ vModulecodeid+"','"+ serialNumber+"','"+node_fk+"','"+ fileName+"','"+moduleStatus+"','"+Domain+"','"+Gprovider+"','"+hardwareVersion+"','"+softwareVersion+"','"+Others+"')"); 
     				  	stmtp.executeUpdate();
     				  	stmtp.close();
     				  	
     				  	NodeModuleSeq++;
     				  	
    			  }else if(!description.contains("fan") && !description.contains("module")){
    				 // --> we should fill the data in table NODE_BOARD
    				  //System.out.println("filling in node board - !contains fan/module");    				  
    	    		vBoardcodeid= year+"_NODE_"+Gprovider+"_"+Domain+"_BRD_"+NodeBoardSeq;   				
     				boardType = records.get(i).get(3);
     				subrackId = records.get(i).get(8);
     				slotId = records.get(i).get(9);
     			    hardwareVersion = records.get(i).get(10);
     				if (hardwareVersion.isEmpty() || hardwareVersion.equals("--")) {
     					hardwareVersion = "null";
     				}
     			    softwareVersion = records.get(i).get(11);
     			    //System.out.println("softwareVersion b: "+softwareVersion);
     				if (softwareVersion.isEmpty() || softwareVersion.equals("--")) {
     					softwareVersion = "null";
     				 }
     				
     				String[] partsoft = softwareVersion.split(",");
     		        String bootRomVersionBoard = null;
  			        //System.out.println("bootRomVersionboard....."+bootRomVersionBoard);
  			        String bootLoadVersionBoard = null;
  			        //System.out.println("bootLoadVersionboard....."+bootLoadVersionBoard);
  			        
  			    if(softwareVersion.contains("BootROM") || softwareVersion.contains("BootLoad")){  
  			    	//System.out.println("softwareVersion CONTAINS");
  			    	//System.out.println("parts.length...."+partsoft.length);
  				    
  			    	 if(partsoft.length == 2) { 
  			    		//System.out.println("LENGTH 2 ");
  	  			    	//String softver = partsoft[0];
  			            //String romver = partsoft[1];
  			            
  			            softwareVersion =  partsoft[0].split(" ")[2];
  			            //System.out.println("softwareVersion >>>>"+softwareVersion); 
  			            
  		            	bootRomVersionBoard = partsoft[1].split(" ")[3];
  		            	//System.out.println("bootRomVersion >>>>"+bootRomVersionBoard);
  		            	
  		            	bootLoadVersionBoard = null;
  	   		        	//System.out.println("bootLoadVersion >>>>"+bootLoadVersionBoard);	   	
  			            
  	  			    }else if(partsoft.length == 3) {
	   		            	//System.out.println("LENGTH 3 ");
	   		            	//String softver = partsoft[0];
	   		            	//String romver = partsoft[1];
	   		            	//String loadver = partsoft[2];
	   		            	/*
	  	   		           System.out.println("softwareVersionboardsplit << "+softver); 
		   		           System.out.println("romVersionboardsplit << "+romver); 
		   		           System.out.println("loadVersionboardsplit << "+loadver);
		   		           System.out.println("softver.split()[0] << "+softver.split(" ")[0]);
		   		           */
		   		        if(partsoft[0].split(" ")[0].equals("Version")){
		   		        	//System.out.println(">> Version <<"); 
	   		        	    softwareVersion =  partsoft[0].split(" ")[1]+ ","+ partsoft[0].split(" ")[3];
	   		        	    //System.out.println("softwareVersion >>>>"+softwareVersion);   		            	
		   		        }else if(partsoft[0].split(" ")[0].equals("Software")){
		   		        	//System.out.println(">> Software<<"); 
		   		        	softwareVersion =  partsoft[0].split(" ")[2];
	   		        	    //System.out.println("softwareVersion >>>>"+softwareVersion); 
		   		        }
	   		            	bootRomVersionBoard = partsoft[1].split(" ")[3];
	   		            	//System.out.println("bootRomVersionBoard >>>>"+bootRomVersionBoard);
	   		            	
	   		            	bootLoadVersionBoard = partsoft[2].split(" ")[3].split("(Configure)")[0];
	   		            	//System.out.println("bootLoadVersionBoard >>>>"+bootLoadVersionBoard);	   		       
	  			      }else if(partsoft.length == 4) {
	  			    	  	//System.out.println("LENGTH 4 ");
	 		            	//String softver1 = partsoft[0];
	 		            	//String softver2 = partsoft[1];
	 		            	//String romver = partsoft[2];
	 		            	//String loadver = partsoft[3];	   		           
		   		           /*
		   		           System.out.println("softwareVersionboardsplit << "+softver1); 
		   		           System.out.println("softwareVersionboardsplit << "+softver2);
		   		           System.out.println("romVersionboardsplit << "+romver); 
		   		           System.out.println("loadVersionboardsplit << "+loadver);
		   		           System.out.println("softver1.split[0] << "+softver1.split(" ")[0]);
		   		          */
		   		           
		   		           if(partsoft[0].split(" ")[0].equals("Software")) {
		   		        	   //System.out.println(">>> Software<<<"); 
			   		        	softwareVersion =  partsoft[0].split(" ")[2]+ ": "+partsoft[0].split(" ")[5]+ ","+ partsoft[0].split(" ")[7] +" - "+partsoft[1].split(" ")[1]+ ": "+partsoft[1].split(" ")[4]+ ","+ partsoft[1].split(" ")[6];
		   		        	   // System.out.println("softwareVersion >>>>"+softwareVersion); 
			   		       }else if(partsoft[0].split(" ")[0].equals("FSU")){
			   		    	   	//System.out.println(">>> FSU <<<"); 
			   		        	softwareVersion =  partsoft[0].split(" ")[3]+ ","+ partsoft[1].split(" ")[2];
		   		        	    //System.out.println("softwareVersion >>>>"+softwareVersion); 
			   		       }	        	   
		   		            	bootRomVersionBoard = partsoft[2].split(" ")[3];
		   		            	//System.out.println("bootRomVersion >>>>"+bootRomVersionBoard);
		   		            	
		   		            	bootLoadVersionBoard = partsoft[3].split(" ")[3].split("(Configure)")[0];
			   		        	//System.out.println("bootLoadVersion >>>>"+bootLoadVersionBoard);	   		       
		  			      }
  			    	
  			     }else if(!softwareVersion.contains("BootROM") && !softwareVersion.contains("BootLoad") && softwareVersion.contains("Version")){  
  			    	//System.out.println("Version");  
  			    	String[] parts = softwareVersion.split(" ");        
	  		        for (int k = 0; k < parts.length - 1; k++) {
	  		            if (parts[k].equalsIgnoreCase("Version")) {
	  		                if (k + 1 < parts.length && !parts[k + 1].isEmpty()) {
	  		                	softwareVersion = parts[k + 1];
	  		                }
	  		            }
	  		        }
  		           // System.out.println("Version number: " + softwareVersion);
  			     }else {
  			    	softwareVersion = softwareVersion;
  			    	//System.out.println("soft Version number: " + softwareVersion);
  			     }
  			        //System.out.println("BootROM versionnnnnn: " + bootRomVersionBoard);
  			        //System.out.println("BootLoad versionnnnnnnn: " + bootLoadVersionBoard);
  			        //System.out.println("Soft versionnnnnn: " + softwareVersion);				  
       			
  			        nodeIPaddress = records.get(i).get(5);
  			        serialNumber=records.get(i).get(12);		
     				model=records.get(i).get(15);
     				biosVersion= records.get(i).get(18);
     				boardStatus= records.get(i).get(19);
     				bomCode=records.get(i).get(22);
   
     				node_fk = nodes.get(records.get(i).get(5));
     				//System.out.println(" node_fkk "+node_fk);  
         			
     				String Others="{\"ROMVER\":"+"\""+bootRomVersionBoard+"\","+"\"LOADVER\":"+"\""+bootLoadVersionBoard+"\""+"}";
     			      
     			// Assuming records1.get(i).get(25) contains the date as a String
     			String manifacturedDate = records.get(i).get(25);
     			//System.out.println("manifacturedDate : "+manifacturedDate);  
     			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as needed    	     			
			       
     			if (manifacturedDate != null && !manifacturedDate.isEmpty() && !manifacturedDate.contains("--")) {
     			    try {
     			        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Define the expected date format
     			        Date manufacturedDate = dateFormat.parse(manifacturedDate); // Parse the date string
     			        java.sql.Date sqlManufacturedDate = new java.sql.Date(manufacturedDate.getTime()); // Convert to java.sql.Date

     			        String sql = "INSERT INTO NODE_BOARD (BOARD_ID, SUBRACKNO, SLOTNO, BOARDNAME, BOARDTYPE, SERIALNUMBER, MODEL, HARDWAREVERSION, DATEOFMANUFACTURE,SOFTVER, BIOSVER, BOMCODE, NODE_PK, FILENAME, STATUS, DOMAIN, VENDOR, OTHERS)"
        			            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?)";

        			        PreparedStatement stmtp = con.prepareStatement(sql);

        			        // Set values for parameters
        			        stmtp.setString(1, vBoardcodeid);
        			        stmtp.setString(2, subrackId);
        			        stmtp.setString(3, slotId);
        			        stmtp.setString(4, boardName);
        			        stmtp.setString(5, boardType);
        			        stmtp.setString(6, serialNumber);
        			        stmtp.setString(7, model);
        			        stmtp.setString(8, hardwareVersion);
        			        stmtp.setDate(9, sqlManufacturedDate); 
        			        stmtp.setString(10, softwareVersion);
        			        stmtp.setString(11, biosVersion);
        			        stmtp.setString(12, bomCode);
        			        stmtp.setString(13, node_fk);
        			        stmtp.setString(14, fileName);
        			        stmtp.setString(15, boardStatus);
        			        stmtp.setString(16, Domain);
        			        stmtp.setString(17, Gprovider);
        			        stmtp.setString(18, Others);
        			     
        			        stmtp.executeUpdate();
        			        stmtp.close();
     			    } catch (Exception e) {
     			        // Handle the case where date parsing fails or other exceptions occur
     			        e.printStackTrace(); // Or log an error
     			    }
     			}else {
     				try {
     				    //System.out.println("manifacturedDateeee : "+manifacturedDate);
     					stmtp =  con.prepareStatement("insert into NODE_BOARD (BOARD_ID,SUBRACKNO,SLOTNO,BOARDNAME,BOARDTYPE,SERIALNUMBER,MODEL,HARDWAREVERSION,DATEOFMANUFACTURE,SOFTVER,BIOSVER,BOMCODE,NODE_PK,FILENAME,STATUS,DOMAIN,VENDOR,OTHERS)"
     					 		+ "values('"+vBoardcodeid+"','"+subrackId+"','"+slotId+"','"+boardName+"','"+ boardType+"','"+ serialNumber+"','"+ model+"','"+hardwareVersion+"','','"+softwareVersion+"','"+biosVersion+"','"+ bomCode+"','"+node_fk+"','"+fileName+"','"+boardStatus+"','"+Domain+"','"+Gprovider+"','"+Others+"')"); 
     					stmtp.executeUpdate();
     				  	stmtp.close();  	
     				} catch (Exception e) {
    			        e.printStackTrace(); // Or log an error
    			    }
     			}	
     			NodeBoardSeq++;
    		}		  
    	 }
    }
}
	
	private static void GetduplicateFilename(String vdomain , String vvendor,String subDomain) throws SQLException  {
		Statement stmt1 = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		Statement stmt4 = null;
		int vcount =0;
		int i=0;
		
		// select all file having duplicata entry and same filename >1
		String query1 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,SUB_DOMAIN,SUB_DOMAIN_TYPE,count(*) counter from NODE_ACTIVE  group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,SUB_DOMAIN,SUB_DOMAIN_TYPE having count(*) >1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' and SUB_DOMAIN='"+subDomain+"' ";  
	    stmt1 = con.createStatement();
	    ResultSet rs1 = stmt1.executeQuery(query1);
	    while (rs1.next()) {
	    	//try {
	                 // select all rows related to a file identified by rs1 having count >=1
					 String query2 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,SUB_DOMAIN,SUB_DOMAIN_TYPE,count(*) counter from (select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,SUB_DOMAIN,SUB_DOMAIN_TYPE from NODE_ACTIVE where (SITE_ID ='"+ rs1.getString("SITE_ID") +"' OR SITE_ID IS NULL) and CIRCLE_ID ='"+ rs1.getString("CIRCLE_ID") +"' and NODE_ID ='"+ rs1.getString("NODE_ID") +"' and NODE_NAME ='"+ rs1.getString("NODE_NAME") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' and SUB_DOMAIN='"+subDomain+"' ) group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,SUB_DOMAIN,SUB_DOMAIN_TYPE having count(*) >=1 and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' and SUB_DOMAIN='"+subDomain+"' ";  
			         stmt2 = con.createStatement();
			         ResultSet rs2 = stmt2.executeQuery(query2);
			         // get all node_pk found duplicated
			         while (rs2.next()) {
			        	 vcount =0;
			        	 vcount= (int) Long.parseLong (rs2.getString("counter"));
			        	 i=0;
				        	    //Get old creation date of the same file and update rows with old creation date
				        	 	String query3 = "select node_pk,creation_date from NODE_ACTIVE where NODE_ID='"+ rs2.getString("NODE_ID") +"' and (SITE_ID='"+ rs2.getString("SITE_ID") +"' OR SITE_ID IS NULL) and NODE_NAME ='"+ rs2.getString("NODE_NAME") +"' and CIRCLE_ID ='"+ rs2.getString("CIRCLE_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' and SUB_DOMAIN='"+subDomain+"' order by creation_date asc";  
					            stmt3 = con.createStatement();
					            //stmt3.setMaxRows(1); 
					            ResultSet rs3 = stmt3.executeQuery(query3);
					            while (rs3.next()) {       
					            	System.out.println(rs3.getString("node_pk") +":"+ rs3.getString("creation_date"));
					            	System.out.println("before formatting : "+rs3.getString("creation_date"));
					            	System.out.println(rs3.getDate("creation_date"));
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
					       			    
					       			 System.out.println("after formatting : "+vdate);
					       			 	// update creation date with old creation date
					            		//System.out.println("update NODE_ACTIVE set creation_date = DATE '"+ vdate +"' where filename ='"+ rs2.getString("filename") +"'");
					            		PreparedStatement updtmt8 = con.prepareStatement("update NODE_ACTIVE set creation_date = TIMESTAMP '" + rs3.getString("creation_date") + "',update_date=sysdate where NODE_NAME ='"+ rs2.getString("NODE_NAME") +"' and CIRCLE_ID ='"+ rs2.getString("CIRCLE_ID") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' and SUB_DOMAIN='"+subDomain+"' ");
						    			updtmt8.executeUpdate();
						    			updtmt8.close();
					            		
						    			//Function to delete the filename from all tables  argument field name and field value
						    			deleterowsinALLTABLES("NODE_PK", rs3.getString("node_pk"),vdomain,vvendor,subDomain);
					            	}  
					            	if ((i >0) && (i< (vcount-1)) ) 
					            	 {  //if i< maxcount just to delete duplicate node_pk from all table 
			
					            		//Function to delete the filename from all tables  argument field name and field value
					            		deleterowsinALLTABLES("NODE_PK", rs3.getString("node_pk"),vdomain,vvendor,subDomain);
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
	
	 private static void deleterowsinALLTABLES(String fieldname, String fieldValue,String vdomain, String vvendor,String subDomain) throws SQLException  {
		 try {
		 // delete all rows related to node_pk from all nodes tables
		 PreparedStatement stmt = con.prepareStatement("delete from NODE_ACTIVE where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' and SUB_DOMAIN='"+subDomain+"' "); 
	     stmt.executeUpdate();
	     stmt.close();
	     
			/*
			 * PreparedStatement stmt1 =
			 * con.prepareStatement("delete from  NODE_ACTIVE_ATTRIBUTE where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * PreparedStatement stmt2 =
			 * con.prepareStatement("delete from  NODE_RACK where " + fieldname +" = '" +
			 * fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
			 * stmt2.executeUpdate(); stmt2.close();
			 * 
			 * stmt = con.prepareStatement("delete from  NODE_CABINET where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"' "); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt1 = con.prepareStatement("delete from  NODE_HOSTVER where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * stmt2 = con.prepareStatement("delete from  NODE_FRAME where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt2.executeUpdate(); stmt2.close();
			 * 
			 * stmt = con.prepareStatement("delete from  NODE_SLOT where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt1 = con.prepareStatement("delete from  NODE_BOARD where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * stmt2 = con.prepareStatement("delete from  NODE_PORT where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt2.executeUpdate(); stmt2.close();
			 * 
			 * stmt = con.prepareStatement("delete from  NODE_ACCESSORY where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt1 = con.prepareStatement("delete from  NODE_HOST where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * stmt2 = con.prepareStatement("delete from  NODE_SUBRACK where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt2.executeUpdate(); stmt2.close();
			 * 
			 * stmt = con.prepareStatement("delete from  NODE_GCELL where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt1 = con.prepareStatement("delete from  NODE_BTS where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * stmt2 = con.prepareStatement("delete from  NODE_UCELL where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt2.executeUpdate(); stmt2.close();
			 * 
			 * stmt = con.prepareStatement("delete from  NODE_ANTENNA where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt1 = con.prepareStatement("delete from  NODE_LCELL where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * stmt2 = con.prepareStatement("delete from  NODE_RRN where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt2.executeUpdate(); stmt2.close();
			 * 
			 * stmt = con.prepareStatement("delete from  NODE_ENODEBCELL where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt1 = con.prepareStatement("delete from  NODE_NODEBCELL where " + fieldname
			 * +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor
			 * +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * stmt = con.prepareStatement("delete from  NODE_NBINTERFACES where " +
			 * fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='"
			 * + vvendor +"'"); stmt.executeUpdate(); stmt.close();
			 */
		 }
		catch(Exception e)  
		{  
			/*
			 * logger.info("error at deleterowsinALLTABLES is :"+ e.toString());
			 * System.out.println("error at deleterowsinALLTABLES is :"+ e.toString());
			 * 
			 * //insert into AUTO_DISCOVERY_LOGS_DETAILS String logsDEtailsid=
			 * localgetseqNbr(22); logsDEtailsid=Gyear+"_"+
			 * "LOGS_DETAILS"+'_'+logsDEtailsid; PreparedStatement insertLogsDEtailsstmt =
			 * conalm.
			 * prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
			 * + "values('"+
			 * logsDEtailsid+"',sysdate ,'ParserLogAIM','error at deleterowsinALLTABLES  ','','','','','"
			 * + Gprovider +"','Mobile Access Domain','"+logsid+"') ");
			 * 
			 * insertLogsDEtailsstmt.executeUpdate(); insertLogsDEtailsstmt.close();
			 * nbOfErrors++;
			 */
			
		}
	     
	}
	 
	 private static void deleteTempNodeTables() throws SQLException  {
		 try {
		 // delete all rows related to node_pk from all nodes tables
		 PreparedStatement stmt = conalm.prepareStatement("delete from TEMP_NODE_ACTIVE where  DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"); 
	     stmt.executeUpdate();
	     stmt.close();
	     
			/*
			 * PreparedStatement stmt1 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_ACTIVE_ATTRIBUTE where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * PreparedStatement stmt2 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_RACK where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt2.executeUpdate(); stmt2.close();
			 * 
			 * stmt = conalm.
			 * prepareStatement("delete from  TEMP_NODE_CABINET where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt1 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_HOSTVER where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * stmt2 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_FRAME where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt2.executeUpdate(); stmt2.close();
			 * 
			 * stmt = conalm.
			 * prepareStatement("delete from  TEMP_NODE_SLOT where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt1 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_BOARD where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * stmt2 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_PORT where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt2.executeUpdate(); stmt2.close();
			 * 
			 * stmt = conalm.
			 * prepareStatement("delete from  TEMP_NODE_ACCESSORY where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt1 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_HOST where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * stmt2 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_SUBRACK where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt2.executeUpdate(); stmt2.close();
			 * 
			 * stmt = conalm.
			 * prepareStatement("delete from  TEMP_NODE_GCELL where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt1 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_BTS where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * stmt2 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_UCELL where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt2.executeUpdate(); stmt2.close();
			 * 
			 * stmt = conalm.
			 * prepareStatement("delete from  TEMP_NODE_ANTENNA where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt1 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_LCELL where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * stmt2 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_RRN where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt2.executeUpdate(); stmt2.close();
			 * 
			 * stmt = conalm.
			 * prepareStatement("delete from  TEMP_NODE_ENODEBCELL where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt1 = conalm.
			 * prepareStatement("delete from  TEMP_NODE_NODEBCELL where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt1.executeUpdate(); stmt1.close();
			 * 
			 * stmt = conalm.
			 * prepareStatement("delete from  TEMP_NODE_NBINTERFACES where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt.executeUpdate(); stmt.close();
			 * 
			 * stmt = conalm.
			 * prepareStatement("delete from  TEMP_NODE_CHILD_PARENT where  DOMAIN='Mobile Access Domain' and VENDOR='"
			 * + Gprovider +"'"); stmt.executeUpdate(); stmt.close();
			 */
		 }
			catch(Exception e)  
			{  
				/*
				 * logger.info("error at deleteTempNodeTables is :"+ e.toString());
				 * System.out.println("error at deleteTempNodeTables is :"+ e.toString());
				 * 
				 * //insert into AUTO_DISCOVERY_LOGS_DETAILS String logsDEtailsid=
				 * localgetseqNbr(22); logsDEtailsid=Gyear+"_"+
				 * "LOGS_DETAILS"+'_'+logsDEtailsid; PreparedStatement insertLogsDEtailsstmt =
				 * conalm.
				 * prepareStatement("insert into AUTO_DISCOVERY_LOGS_DETAILS (LOGS_DETAILS_ID,TIME,ACTIVITY_NAME,ACTIVITY_DESCRIPTION,ATTRIBUTE_NAME,ACTIVITY_TITLE,ACTIVITY_STATUS,QUANTITY,VENDOR,DOMAIN,LOGS_ID)"
				 * + "values('"+
				 * logsDEtailsid+"',sysdate ,'ParserLogAIM','error at deleteTempNodeTables ','','','','','"
				 * + Gprovider +"','Mobile Access Domain','"+logsid+"') ");
				 * 
				 * insertLogsDEtailsstmt.executeUpdate(); insertLogsDEtailsstmt.close();
				 * nbOfErrors++;
				 */
				
			}
	     
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


}
