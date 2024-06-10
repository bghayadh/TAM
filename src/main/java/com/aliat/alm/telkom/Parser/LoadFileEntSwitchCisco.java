package com.aliat.alm.telkom.Parser;

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
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class LoadFileEntSwitchCisco {

	
	static String readfileEntSwitchCiscofrom;
	static String copyfileEntSwitchCiscoto;
	static String logsid="0";
	static String logsDetailsId="0";
	static int NodeSeq,BoardSeq,ModuleSeq,NodeCount;
	static BufferedReader objReader1;
	static String strCurrentLine1;
	static String projpath ;
	static String logpath;
	static String db1path;
	static String username1;
	static String password1;
	static String db2path;
	static String username2;
	static String password2,siteID;
	static String vfolderfrom,fileType,FileName;
	static String Gprovider,Domain,subDomain,subDomainType,patchVersion,partNumber;
	static String circleid="10";
	static String Gyear,wareID,wareName,longi,lat,IPaddress,MACaddress,commStatus="0",adminStatus="0",softwareVersion,LCStatus="0";
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
	static String vcodeid ="0",boardpk="0",modulepk="0",fanpk="0";
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
	
	static List<List<String>> moduleList = new ArrayList<List<String>>();
	static List<List<String>> boardList = new ArrayList<List<String>>();
	static List<String> tempList = new ArrayList<String>();
	 
	static String nodeId ;
	static String nodeType ;
	static String nodeModel,nodeSN;
	static String nodeName ;
	static String unique_Node_ID;	
	static String Others;
	static String boardName;
	static String boardType ;
	static String boardStatus ;
	static String bomCode;
	static String unitType;
	//static String manifacturedDate = null;
	static String moduleStatus;
	
	public static void main(String[] args,String vendor,String domain,String sub_domain,String sub_domainType) throws Exception {
		
		
		Resource ConfigResource = new ClassPathResource("almconfig.dat");
		File configfile = ConfigResource.getFile();
		FileReader fr=new FileReader(configfile);  
		BufferedReader objReader1=new BufferedReader(fr);
		
		while ((strCurrentLine1 = objReader1.readLine()) != null){
			 String data = strCurrentLine1;
			 String[] data1 ;
			 
			 if (data.contains("projectpath")) {
				 data1=data.split(";",-1);
				 projpath=data1[1];
			 }
			 if (data.contains("logpath")) {
				 data1=data.split(";",-1);
				 logpath=data1[1];
			 }
			 if (data.contains("db1path")) {
				 data1=data.split(";",-1);
				 db1path=data1[1];
				 username1=data1[2];
				 password1=data1[3];
			 }
			 if (data.contains("db2path")) {
				 data1=data.split(";",-1);
				 db2path=data1[1];
				 username2=data1[2];
				 password2=data1[3];
				
			 }
			 if (data.contains("readfileEntSwitchCiscofrom")) {
				 data1=data.split(";",-1);
				 readfileEntSwitchCiscofrom = data1[1];
				 vfolderfrom=readfileEntSwitchCiscofrom;
				 Gprovider=vendor;
				 Domain=domain;
				 subDomain=sub_domain;
				 subDomainType = sub_domainType;
			 }
			 if (data.contains("copyfileEntSwitchCiscoto")) {
				 data1=data.split(";",-1);
				 copyfileEntSwitchCiscoto=data1[1];
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
		 
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		 LocalDateTime now = LocalDateTime.now();
		 String lofilename="ParserLogEntSwCisco-"+dtf.format(now)+".log";
		 
		 File folder = new File(vfolderfrom);
		 System.out.println("folder....."+folder);
		 File[] listOfFiles = folder.listFiles();
		 System.out.println("Number of files: " + listOfFiles.length);
		 String fileName1 = null;
		 logger = Logger.getLogger("MyLog"); 
		 logger.setUseParentHandlers(false);
		
		 // This block configure the logger with handler and formatter  and PATH
	        fh = new FileHandler(logpath+"/"+lofilename);
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
							System.out.println("Inside loop.");
								System.out.println("File name is : "+file.getName().toString());
								String fichier = file.getName().toString();
								// reading file from folder
								readfile(fichier);

								File source = new File(readfileEntSwitchCiscofrom + "/" + file.getName());
								File dest = new File(copyfileEntSwitchCiscoto + "/" + file.getName() + ".bkp");

								// coypy file after treating it to destination folder
								copyFiles(source, dest);
								// delete file from source folder
								deleteFiles(readfileEntSwitchCiscofrom + "/" + file.getName());

							
						}
							
		 	  }
		 	 GetduplicateFilename(Domain,Gprovider);
		 	 logger.info("Load Files completed");
		 	// update file status to completed
				 stmtp = con.prepareStatement("update EXECUTE_DOAMIN_VENDOR_FILES set STATUS ='COMPLETED' where DOMAIN='"+Domain+"' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS' and SUB_DOMAIN='"+subDomain+"' ");
				 stmtp.executeUpdate();
				 stmtp.close();
		 
			  conalm.close();
			  con.close();
}	
	
	private static void readfile(String fileName) throws FileNotFoundException, IOException, SQLException {
		
		FileReader fileReader=new FileReader(readfileEntSwitchCiscofrom + "/" +fileName);  
		BufferedReader dataobjReader=new BufferedReader(fileReader);
		
		moduleList = new ArrayList<List<String>>();
		boardList = new ArrayList<List<String>>();
		NodeCount=0;
		//this counter to prevent inserting duplicate node from the same file  
		int duplicate_node=0;
		
		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		
		//get all needed sequence 
		String sqlStmtinit2 = "select NODE_ACTIVE,NODE_BOARD,NODE_MODULE from SEQ_TABLE";
		stmtp1 = conalm.createStatement();
		ResultSet rsinit2 = stmtp1.executeQuery(sqlStmtinit2);
		while (rsinit2.next()) {
			// store the returned result in a variable to be increased each loop instead of
			// accessing the database all the time
			// which lead to exceed the maximum number of open cursors.
			NodeSeq = rsinit2.getInt("NODE_ACTIVE");
			BoardSeq = rsinit2.getInt("NODE_BOARD");
			ModuleSeq=rsinit2.getInt("NODE_MODULE");
			//FanSeq=rsinit2.getInt("NODE_BOARD");

		}
		rsinit2.close();
		stmtp1.close();
		
		FileName=fileName.replace(".cfg", "");
		fileType="cfg";
		
		String tempname,tempmodal,tempSN,tempdesc;
		//start reading the .cfg files
		while ((strCurrentLine1 = dataobjReader.readLine()) != null){
			 String data = strCurrentLine1;
			 String data2="";
			 String data3="";
			 
			//node data
			 if(duplicate_node<2) {
				 if (data.contains("show inventory")) {
					duplicate_node++;
					 if(duplicate_node<=1) {//this ensures that only 1 node per file is inserted  
						nodeName=data.split("#",-1)[0];
						siteID=data.split("-",-1)[0];
					 	wareID = "";
						wareName = "";
						longi = "";
						lat = "";
						 
						 char charArray[] = siteID.toCharArray();
							//check if the string starts with number and ends with character then it is site id.
							if (Character.isDigit(charArray[0]) && !Character.isDigit(charArray[siteID.length() - 1])) { 
								String sqlStmtinit3 = "select WARE_ID,WARE_NAME,LONGITUDE,LATITUDE from WAREHOUSE WHERE SITE_ID='"
										+ siteID + "'";
								stmtp1 = conalm.createStatement();
								ResultSet rsinit3 = stmtp1.executeQuery(sqlStmtinit3);
								while (rsinit3.next()) {
									wareID = rsinit3.getString("WARE_ID");
									wareName = rsinit3.getString("WARE_NAME");
									longi = rsinit3.getString("LONGITUDE");
									lat = rsinit3.getString("LATITUDE");
								}
								rsinit3.close();
								stmtp1.close();
							} else {
								wareID = "";
								wareName = "";
								longi = "";
								lat = "";
								siteID = "";
							}
						 
						vcodeid =  year+"_NODE_"+Gprovider+"_"+Domain+"_"+NodeSeq;
						nodeType="Switch";
						nodeId=fileName.split("-",-1)[0];
						unique_Node_ID=nodeId+"_Cisco";
						IPaddress=nodeId;
						
						 //to read 3 lines that contains node data(modeland serial number)
						  data2=dataobjReader.readLine();
						  data3=dataobjReader.readLine();
						 nodeModel=(data3.split(":",-1)[1]).split(",",-1)[0].strip();
						 nodeSN=data3.split(":",-1)[3].strip();
						
						 //insert into node_active 
						 PreparedStatement stmt = con.prepareStatement(
									"insert into NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,SUB_DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME,IP_ADDRESS,MAC_ADDRESS,SERIAL_NUMBER,SOFTWARE_VERSION,PATCH_VERSION,LONGITUDE,LATITUDE,PART_NUMBER,OTHERS )"
											+ "values('" + vcodeid + "', '" + unique_Node_ID + "' ,'" + nodeId + "' ,'" + nodeName
											+ "','" + nodeType + "','" + Domain +"','"+subDomain+ "','0','" + nodeModel + "','0','0','0','0','"
											+ siteID + "','" + circleid + "',sysdate,sysdate,'" + fileType + "','" + FileName
											+ "','','0','0','0','0','0','1','0','" + wareID + "','" + Gprovider
											+ "','0','" + wareName + "','0','" + IPaddress + "','" + MACaddress + "','"+nodeSN+ "','"
											+ softwareVersion + "','" + patchVersion + "','" + longi + "','" + lat + "',''"
													+ ",'" + Others + "') ");
							stmt.executeUpdate();
							stmt.close();
							NodeSeq++;
							NodeCount++;
					 }
							
				 }
			 }
			 if(duplicate_node<2) {//this ensures that elements of the node(boards and module) are inserted once only 
				 //extraction of board, module and fan data 
				 if (data.split(",",-1)[0].contains("module") || data.split(",",-1)[0].contains("Module") || data.split(",",-1)[0].contains("subslot") || data.split(",",-1)[0].contains("Fan") || data.split(",",-1)[0].contains("fan")|| data.split(",",-1)[0].contains("power") ||  data.split(",",-1)[0].contains("Power")) {
					 data2=dataobjReader.readLine();
					 
					 tempmodal="";tempSN="";tempname="";tempdesc="";
					tempList=new ArrayList<String>();
					
					tempname=data.split("\"",-1)[1];
					tempdesc=data.split("\"",-1)[3];
					tempmodal=(data2.split(":",-1)[1]).split(",",-1)[0].strip();
					tempSN=data2.split(":",-1)[3].strip();
					
					tempList.add(tempname);
					tempList.add(tempmodal);
					tempList.add(tempSN); 
					tempList.add(tempdesc);
					
					if (data.split(",",-1)[0].contains("module") || data.split(",",-1)[0].contains("Module") || data.split(",",-1)[0].contains("Fan") || data.split(",",-1)[0].contains("fan")|| data.split(",",-1)[0].contains("power") ||  data.split(",",-1)[0].contains("Power")) {
						moduleList.add(tempList);
					}
					if (data.split(",",-1)[0].contains("subslot") ) {
						boardList.add(tempList);
					}
					
				 }
			 }
			 
			 
			
			 
		}
		 dataobjReader.close();
		
		for(int i=0;i<boardList.size();i++) {
			
			boardpk = year + "_NODE_"+Gprovider+"_"+Domain+"_BRD_" + BoardSeq;
			PreparedStatement stmtboard = con.prepareStatement(
					"insert into NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,"
							+ "BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,"
							+ "UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,"
							+ "EXTINFO,APDEVINFO,WORKMODE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,"
							+ "TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,OTHERS) "
							+ "values('" + boardpk + "','0','0','0','','0','','0','0','','0',"
							+ "'board','0','','', '0','', '" +boardList.get(i).get(2) + "','','"+boardList.get(i).get(0)+"','" 
							+ boardList.get(i).get(3) + "','','','0'," + "'0','0','0','0'," + "'0','"+boardList.get(i).get(1)+"',''"
							+ ",'0','0','0','" + vcodeid + "','',sysdate,'" + fileName + "'," + "'0','0','0','0','0','0',"
							+ "'0','1','0',sysdate,'" + Domain + "','" + Gprovider + "','') ");
			stmtboard.executeUpdate();
			stmtboard.close();
			BoardSeq++;
		}
		
		for(int i=0;i<moduleList.size();i++) {
			
			modulepk= year+"_NODE_"+Gprovider+"_"+Domain+"_MOD_"+ModuleSeq;  
			
			if(moduleList.get(i).get(0).contains("module") || moduleList.get(i).get(0).contains("Module")) {
				unitType="module";
			}
			if(moduleList.get(i).get(0).contains("Fan") || moduleList.get(i).get(0).contains("fan")) {
				unitType="fan";
			}
			if(moduleList.get(i).get(0).contains("Power") || moduleList.get(i).get(0).contains("power")) {
				unitType="power-supply";
			}
			PreparedStatement stmtmodule =  con.prepareStatement("insert into NODE_MODULE (MODULE_ID,SERIALNUMBER,NODE_PK,FILENAME,STATUS,CREATION_DATE,DOMAIN,UPDATE_DATE,VENDOR,MODEL,INVUNITTYPE,UNITPOSITION,SOFTVER,OTHERS,ACTIVE_RECORD)"
			 		+ "values('"+ modulepk+"','"+ moduleList.get(i).get(2)+"','"+vcodeid+"','"+ fileName+"','"+moduleStatus+"'"
			 		+ ",sysdate,'"+Domain+"',sysdate,'"+Gprovider+"','"+moduleList.get(i).get(1)+"','"+unitType+"','"+moduleList.get(i).get(0)+"','','"
			 		+Others+"','1')"); 
			stmtmodule.executeUpdate();
			stmtmodule.close();
			ModuleSeq++;
		}

		int BoardCount =boardList.size(),ModuleCount=moduleList.size();
	
		 stmtp = conalm.prepareStatement("UPDATE SEQ_TABLE SET NODE_ACTIVE = NODE_ACTIVE +"+NodeCount 
					+ ",NODE_BOARD=NODE_BOARD +" + BoardCount + ",NODE_MODULE=NODE_MODULE +" + ModuleCount);
			stmtp.executeUpdate();
			stmtp.close();
		 
	}
	/*
	private static void GetduplicateFilename(String vdomain , String vvendor,String subDomain) throws SQLException  {
		Statement stmt1 = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
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
	}*/
	
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
	     
	      stmt = con.prepareStatement("delete from NODE_MODULE where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
	     stmt.executeUpdate();
	     stmt.close();
	      
		 }
		catch(Exception e)  
		{  
			logger.info("error at deleterowsinALLTABLES is :"+ e.toString());
			System.out.println("error at deleterowsinALLTABLES is :"+ e.toString()); 
			
		}
	     
	}
	/*
	 private static void deleterowsinALLTABLES(String fieldname, String fieldValue,String vdomain, String vvendor,String subDomain) throws SQLException  {
		 try {
		 // delete all rows related to node_pk from all nodes table
		 PreparedStatement stmt = con.prepareStatement("delete from NODE_ACTIVE where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"' and SUB_DOMAIN='"+subDomain+"' "); 
	     stmt.executeUpdate();
	     stmt.close();
	     
	     stmt = con.prepareStatement("delete from  NODE_BOARD where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor+"' and SUB_DOMAIN='"+subDomain+"'"); 
	     stmt.executeUpdate(); 
		 stmt.close();
		 
		 stmt = con.prepareStatement("delete from  NODE_MODULE where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor+ "' and SUB_DOMAIN='"+subDomain+"'"); 
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
		/* }
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
			
		//}
	     
	//}
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
